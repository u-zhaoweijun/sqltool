package cn.tenmg.sqltool.utils;

import cn.tenmg.sqltool.exception.NosuitableSqlEngineException;
import cn.tenmg.sqltool.sql.SqlEngine;
import cn.tenmg.sqltool.sql.engine.MySQLSqlEngine;
import cn.tenmg.sqltool.sql.engine.OracleSqlEngine;
import cn.tenmg.sqltool.sql.engine.PostgreSQLSqlEngine;
import cn.tenmg.sqltool.sql.engine.SparkSQLSqlEngine;

/**
 * SQL引擎工具
 * 
 * @author 赵伟均
 *
 */
public abstract class SqlEngineUtils {
	/**
	 * 根据驱动类名获取SQL引擎
	 * 
	 * @param driver
	 *            访问数据库的驱动类名
	 * @return 返回驱动所对应的SQL引擎。如果无法找到合适的引擎，将抛出NosuitableSqlEngineException异常
	 */
	public static final SqlEngine getSqlEngine(String driver) {
		if (driver == null) {
			return SparkSQLSqlEngine.getInstance();
		} else {
			if (driver.contains("mysql")) {
				return MySQLSqlEngine.getInstance();
			} else if (driver.contains("oracle")) {
				return OracleSqlEngine.getInstance();
			} else if (driver.contains("postgresql")) {
				return PostgreSQLSqlEngine.getInstance();
			} else {
				throw new NosuitableSqlEngineException(
						String.format("There is no suitable SQL engine here for driver: %s!", driver));
			}
		}
	}
}
