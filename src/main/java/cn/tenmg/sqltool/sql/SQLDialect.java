package cn.tenmg.sqltool.sql;

import java.io.Serializable;

import cn.tenmg.sqltool.dsql.JdbcSql;

public interface SQLDialect extends Serializable {
	/**
	 * 获取软保存数据（插入或更新）操作对象。软保存是只仅对属性值不为null的执行保存操作
	 * 
	 * @param obj
	 *            实体对象
	 * @return 返回软保存数据（插入或更新）操作对象
	 */
	<T> JdbcSql save(T obj);

	/**
	 * 
	 * 获取部分硬保存数据操作对象。硬保存是指无论数值型是不是null均会执行保存操作
	 * 
	 * @param obj
	 *            实体对象
	 * @param hardFields
	 *            硬保存字段名
	 * @return 返回部分硬保存数据操作对象
	 */
	<T> JdbcSql save(T obj, String[] hardFields);

	/**
	 * 获取硬保存数据操作对象。硬保存是指无论数值型是不是null均会执行保存操作
	 * 
	 * @param obj
	 *            实体对象
	 * @return 返回获取硬保存数据操作对象
	 */
	<T> JdbcSql hardSave(T obj);
}
