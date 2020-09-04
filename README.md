# sqltool
## 关于（About）
一个给分布式环境提供动态结构化查询语言（DSQL）解析的框架，如Spark、Spring Cloud、Dubbo等等

A framework provides Dynamic Structured Query Language(DSQL) Parsing for distributed environment, such as spark, spring cloud, Dubbo and so on

## 什么是动态结构化查询语言（What is Dynamic Structured Query Language）？
动态结构化查询语言(DSQL)是一种使用特殊字符#[]标记动态片段的结构化查询语言(SQL)，当实际执行查询时，判断实际传入参数值是否为空（null）决定是否保留该片段，同时保留片段的特殊字符会被自动去除。以此来避免程序员手动拼接繁杂的SQL，使得程序员能从繁杂的业务逻辑中解脱出来。

Dynamic Structured Query Language (DSQL) is a kind of Structured Query Language (SQL) which uses special character #[] to mark dynamic fragment. When the query is actually executed, whether the actual input parameter value is null determines whether to keep the fragment or not. At the same time, the special characters reserved in the fragment will be automatically removed. In order to avoid programmers manually splicing complicated SQL, programmers can be free from the complex business logic.

## 例子（Example）
	SELECT
	  *
	FROM STAFF_INFO S
	WHERE S.STATUS = 'VALID'
	#[AND S.STAFF_ID = :staffId]
	#[AND S.STAFF_NAME LIKE :staffName]
1. 参数staffId为空（null），而staffName为非空（非null）时，实际执行的语句为：

		SELECT
		  *
		FROM STAFF_INFO S
		WHERE S.STATUS = 'VALID'
		AND S.STAFF_NAME LIKE :staffName
2. 相反，参数staffName为空（null），而staffId为非空（非null）时，实际执行的语句为：

		SELECT
		  *
		FROM STAFF_INFO S
		WHERE S.STATUS = 'VALID'
		AND S.STAFF_ID = :staffId
3. 或者，参数staffId、staffName均为空（null）时，实际执行的语句为：

		SELECT
		  *
		FROM STAFF_INFO S
		WHERE S.STATUS = 'VALID'
4. 最后，参数staffId、staffName均为非空（非null）时，实际执行的语句为：

		SELECT
		  *
		FROM STAFF_INFO S
		WHERE S.STATUS = 'VALID'
		AND S.STAFF_ID = :staffId
		AND S.STAFF_NAME LIKE :staffName
通过上面这个小例子，我们看到了动态结构化查询语言（DSQL）的魔力。这种魔力的来源是巧妙的运用了一个值：空(null)，因为该值往往在结构化查询语言(SQL)中很少用到，而且即使使用也是往往作为特殊的常量使用，比如：NVL(EMAIL,'无')，WHERE EMAIL IS NOT NULL等。

1. When the parameter staffId is null and staffName is not null, the actual executed statement is as follows:

		SELECT
		  *
		FROM STAFF_INFO S
		WHERE S.STATUS = 'VALID'
		AND S.STAFF_NAME LIKE :staffName
2. On the contrary, when the parameter staffName is null and staffId is not null, the actual executed statement is:

		SELECT
		  *
		FROM STAFF_INFO S
		WHERE S.STATUS = 'VALID'
		AND S.STAFF_ID = :staffId
3. Or, when the parameters staffId and staffName are null, the actual executed statement is:

		SELECT
		  *
		FROM STAFF_INFO S
		WHERE S.STATUS = 'VALID'
4. Finally, when the parameters staffId and staffName are not null, the actual executed statement is as follows：

		SELECT
		  *
		FROM STAFF_INFO S
		WHERE S.STATUS = 'VALID'
		AND S.STAFF_ID = :staffId
		AND S.STAFF_NAME LIKE :staffName
Through the above simple example, we can see the magic of Dynamic Structured Query Language (DSQL). The source of this magic is the clever use of a value: null, because the value is often rarely used in Structured Query Language (SQL), and even if used, it is often used as a special constant, such as: NVL(email, 'none'), WHERE EMAIL IS NOT NULL, etc.
## 提供了几乎你能想到的所有结构化数据库交互方法（It provides almost all kinds of structured database interaction methods you can imagine）
	/**
	 * 插入操作
	 * 
	 * @param options
	 *            数据库配置
	 * @param obj
	 *            实体对象（不能为null）
	 * @return 返回受影响行数
	 */
	int insert(Map<String, String> options, Object obj);

	/**
	 * 插入操作（实体对象集为空则直接返回null）
	 * 
	 * @param options
	 *            数据库配置
	 * @param rows
	 *            实体对象集
	 * @return 返回每批的受影响行数
	 */
	int[] insert(Map<String, String> options, List<Object> rows);

	/**
	 * 使用默认批容量执行批量插入操作
	 * 
	 * @param options
	 *            数据库配置
	 * @param rows
	 *            实体对象集
	 */
	void insertBatch(Map<String, String> options, List<Object> rows);

	/**
	 * 
	 * 批量插入操作
	 * 
	 * @param options
	 *            数据库配置
	 * @param rows
	 *            实体对象集
	 * @param batchSize
	 *            批容量
	 */
	void insertBatch(Map<String, String> options, List<Object> rows, int batchSize);

	/**
	 * 软保存。仅对属性值不为null的字段执行插入/更新操作
	 * 
	 * @param options
	 *            数据库配置
	 * @param obj
	 *            实体对象
	 * @return 返回受影响行数
	 */
	<T extends Serializable> int save(Map<String, String> options, T obj);

	/**
	 * 软保存。仅对属性值不为null的字段执行插入/更新操作
	 * 
	 * @param options
	 *            数据库配置
	 * @param rows
	 *            实体对象集
	 */
	<T extends Serializable> void save(Map<String, String> options, List<T> rows);

	/**
	 * 使用默认批容量批量软保存。仅对属性值不为null的字段执行插入/更新操作
	 * 
	 * @param options
	 *            数据库配置
	 * @param rows
	 *            实体对象集
	 */
	<T extends Serializable> void saveBatch(Map<String, String> options, List<T> rows);

	/**
	 * 
	 * 批量软保存。仅对属性值不为null的字段执行插入/更新操作
	 * 
	 * @param options
	 *            数据库配置
	 * @param rows
	 *            实体对象集
	 * @param batchSize
	 *            批容量
	 */
	<T extends Serializable> void saveBatch(Map<String, String> options, List<T> rows, int batchSize);

	/**
	 * 部分硬保存。仅对属性值不为null或硬保存的字段执行插入/更新操作
	 * 
	 * @param options
	 *            数据库配置
	 * @param obj
	 *            实体对象
	 * @param hardFields
	 *            硬保存属性
	 * @return 返回受影响行数
	 */
	<T extends Serializable> int save(Map<String, String> options, T obj, String[] hardFields);

	/**
	 * 部分硬保存。仅对属性值不为null或硬保存的字段执行插入/更新操作
	 * 
	 * @param options
	 *            数据库配置
	 * @param rows
	 *            实体对象集
	 * @param hardFields
	 *            硬保存属性
	 */
	<T extends Serializable> void save(Map<String, String> options, List<T> rows, String[] hardFields);

	/**
	 * 使用默认批容量批量部分硬保存。仅对属性值不为null或硬保存的字段执行插入/更新操作
	 * 
	 * @param options
	 *            数据库配置
	 * @param rows
	 *            实体对象集
	 * @param hardFields
	 *            硬保存属性
	 */
	<T extends Serializable> void saveBatch(Map<String, String> options, List<T> rows, String[] hardFields);

	/**
	 * 
	 * 批量部分硬保存。仅对属性值不为null或硬保存的字段执行插入/更新操作
	 * 
	 * @param options
	 *            数据库配置
	 * @param rows
	 *            实体对象集
	 * @param hardFields
	 *            硬保存属性
	 * @param batchSize
	 *            批容量
	 */
	<T extends Serializable> void saveBatch(Map<String, String> options, List<T> rows, String[] hardFields,
			int batchSize);

	/**
	 * 硬保存。对所有字段执行插入/更新操作
	 * 
	 * @param options
	 *            数据库配置
	 * @param obj
	 *            实体对象
	 * @return 返回受影响行数
	 */
	<T extends Serializable> int hardSave(Map<String, String> options, T obj);

	/**
	 * 硬保存。对所有字段执行插入/更新操作
	 * 
	 * @param options
	 *            数据库配置
	 * @param rows
	 *            实体对象集
	 */
	<T extends Serializable> void hardSave(Map<String, String> options, List<T> rows);

	/**
	 * 使用默认批容量批量硬保存。对所有字段执行插入/更新操作
	 * 
	 * @param options
	 *            数据库配置
	 * @param rows
	 *            实体对象集
	 */
	<T extends Serializable> void hardSaveBatch(Map<String, String> options, List<T> rows);

	/**
	 * 
	 * 批量硬保存。对所有字段执行插入/更新操作
	 * 
	 * @param options
	 *            数据库配置
	 * @param rows
	 *            实体对象集
	 * @param batchSize
	 *            批容量
	 */
	<T extends Serializable> void hardSaveBatch(Map<String, String> options, List<T> rows, int batchSize);

	/**
	 * 从数据库查询并组装实体对象
	 * 
	 * @param options
	 *            数据库配置
	 * @param obj
	 *            实体对象
	 * @return 返回查询到的实体对象
	 */
	@SuppressWarnings("unchecked")
	<T extends Serializable> T get(Map<String, String> options, T obj);

	/**
	 * 使用动态结构化查询语言（DSQL）并组装对象，其中类型可以是实体对象，也可以是String、Number、
	 * Date、BigDecimal类型，这事将返回结果集中的第1行第1列的值
	 * 
	 * @param options
	 *            数据库配置
	 * @param type
	 *            对象类型
	 * @param dsql
	 *            动态结构化查询语言
	 * @param params
	 *            查询参数键值集
	 * @return 返回查询到的对象
	 */
	<T extends Serializable> T get(Map<String, String> options, Class<T> type, String dsql, Object... params);

	/**
	 * 使用动态结构化查询语言（DSQL）并组装对象，其中类型可以是实体对象，也可以是String、Number、
	 * Date、BigDecimal类型，这时将返回结果集中的第1行第1列的值
	 * 
	 * @param options
	 *            数据库配置
	 * @param type
	 *            对象类型
	 * @param dsql
	 *            动态结构化查询语言
	 * @param params
	 *            查询参数键值集
	 * @return 返回查询到的对象
	 */
	<T extends Serializable> T get(Map<String, String> options, Class<T> type, String dsql,
			Map<String, Object> params);

	/**
	 * 使用动态结构化查询语言（DSQL）并组装对象列表，其中类型可以是实体对象，也可以是String、Number、
	 * Date、BigDecimal类型，这时将返回结果集中的第1行的值
	 * 
	 * @param options
	 *            数据库配置
	 * @param type
	 *            对象类型
	 * @param dsql
	 *            动态结构化查询语言
	 * @param params
	 *            查询参数键值集
	 * @return 返回查询到的对象列表
	 */
	<T extends Serializable> List<T> select(Map<String, String> options, Class<T> type, String dsql,
			Object... params);

	/**
	 * 使用动态结构化查询语言（DSQL）并组装对象列表，其中类型可以是实体对象，也可以是String、Number、
	 * Date、BigDecimal类型，这时将返回结果集中的第1行的值
	 * 
	 * @param options
	 *            数据库配置
	 * @param type
	 *            对象类型
	 * @param dsql
	 *            动态结构化查询语言
	 * @param params
	 *            查询参数键值集
	 * @return 返回查询到的对象列表
	 */
	<T extends Serializable> List<T> select(Map<String, String> options, Class<T> type, String dsql,
			Map<String, Object> params);

	/**
	 * 使用动态结构化查询语言（DSQL）执行插入、修改、删除操作
	 * 
	 * @param options
	 *            数据库配置
	 * @param type
	 *            对象类型
	 * @param dsql
	 *            动态结构化查询语言
	 * @param params
	 *            查询参数键值集
	 * @return 如果第一个结果是ResultSet对象，则为true；如果第一个结果是更新计数或没有结果，则为false
	 */
	boolean execute(Map<String, String> options, String dsql, Object... params);

	/**
	 * 使用动态结构化查询语言（DSQL）执行插入、修改、删除操作
	 * 
	 * @param options
	 *            数据库配置
	 * @param type
	 *            对象类型
	 * @param dsql
	 *            动态结构化查询语言
	 * @param params
	 *            查询参数键值集
	 * @return 如果第一个结果是ResultSet对象，则为true；如果第一个结果是更新计数或没有结果，则为false
	 */
	boolean execute(Map<String, String> options, String dsql, Map<String, Object> params);

	/**
	 * 使用动态结构化查询语言（DSQL）执行插入、修改、删除操作
	 * 
	 * @param options
	 *            数据库配置
	 * @param type
	 *            对象类型
	 * @param dsql
	 *            动态结构化查询语言
	 * @param params
	 *            查询参数键值集
	 * @return 返回受影响行数
	 */
	int executeUpdate(Map<String, String> options, String dsql, Object... params);

	/**
	 * 使用动态结构化查询语言（DSQL）执行插入、修改、删除操作
	 * 
	 * @param options
	 *            数据库配置
	 * @param type
	 *            对象类型
	 * @param dsql
	 *            动态结构化查询语言
	 * @param params
	 *            查询参数键值集
	 * @return 返回受影响行数
	 */
	int executeUpdate(Map<String, String> options, String dsql, Map<String, Object> params);

	/**
	 * 开始事务
	 * 
	 * @param options
	 *            数据库配置
	 */
	void beginTransaction(Map<String, String> options);

	/**
	 * 使用动态结构化查询语言（DSQL）执行插入、修改、删除操作。该方法不自动提交事务，且调用前需要先调用beginTransaction方法开启事务，之后在合适的时机还需要调用commit方法提交事务。
	 * 
	 * @param dsql
	 *            动态结构化查询语言
	 * @param params
	 *            查询参数键值集
	 * @return 如果第一个结果是ResultSet对象，则为true；如果第一个结果是更新计数或没有结果，则为false
	 */
	boolean execute(String dsql, Object... params);

	/**
	 * 使用动态结构化查询语言（DSQL）执行插入、修改、删除操作。该方法不自动提交事务，且调用前需要先调用beginTransaction方法开启事务，之后在合适的时机还需要调用commit方法提交事务。
	 * 
	 * @param dsql
	 *            动态结构化查询语言
	 * @param params
	 *            查询参数键值集
	 * @return 如果第一个结果是ResultSet对象，则为true；如果第一个结果是更新计数或没有结果，则为false
	 */
	boolean execute(String dsql, Map<String, Object> params);

	/**
	 * 使用动态结构化查询语言（DSQL）执行插入、修改、删除操作。该方法不自动提交事务，且调用前需要先调用beginTransaction方法开启事务，之后在合适的时机还需要调用commit方法提交事务。
	 * 
	 * @param dsql
	 *            动态结构化查询语言
	 * @param params
	 *            查询参数键值集
	 * @return 返回受影响行数
	 */
	int executeUpdate(String dsql, Object... params);

	/**
	 * 使用动态结构化查询语言（DSQL）执行插入、修改、删除操作。该方法不自动提交事务，且调用前需要先调用beginTransaction方法开启事务，之后在合适的时机还需要调用commit方法提交事务。
	 * 
	 * @param dsql
	 *            动态结构化查询语言
	 * @param params
	 *            查询参数键值集
	 * @return 返回受影响行数
	 */
	int executeUpdate(String dsql, Map<String, Object> params);

	/**
	 * 事务回滚。在业务方法发生异常时调用。
	 */
	void rollback();

	/**
	 * 提交事务
	 */
	void commit();

	/**
	 * 执行一个事务操作
	 * @param options 数据库配置
	 * @param transaction 事务对象
	 */
	void execute(Map<String, String> options, Transaction transaction);
