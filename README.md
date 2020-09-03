# sqltool
一个给分布式环境提供动态结构化查询语言（DSQL）解析的框架，如Spark、Spring Cloud、Dubbo等等

A framework provides Dynamic Structured Query Language(DSQL) Parsing for distributed environment, such as spark, spring cloud, Dubbo and so on

# 什么是动态结构化查询语言（What is Dynamic Structured Query Language）？
动态结构化查询语言(DSQL)是一种使用特殊字符#[]标记动态片段的结构化查询语言(SQL)，当实际执行查询时，判断实际传入参数值是否为空（null）决定是否保留该片段，同时保留片段的特殊字符会被自动去除。以此来避免程序员手动拼接繁杂的SQL，使得程序员能从繁杂的业务逻辑中解脱出来。

Dynamic Structured Query Language (DSQL) is a kind of Structured Query Language (SQL) which uses special character #[] to mark dynamic fragment. When the query is actually executed, whether the actual input parameter value is null determines whether to keep the fragment or not. At the same time, the special characters reserved in the fragment will be automatically removed. In order to avoid programmers manually splicing complicated SQL, programmers can be free from the complex business logic.

# 例子（Example）
SELECT
  *
FROM STAFF_INFO S
WHERE S.STATUS = 'VALID'
#[AND S.STAFF_ID = :staffId]
#[AND S.STAFF_NAME LIKE :staffName]
