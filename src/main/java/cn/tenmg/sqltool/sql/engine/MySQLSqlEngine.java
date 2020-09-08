package cn.tenmg.sqltool.sql.engine;

/**
 * MySQL方言的SQL引擎
 * 
 * @author 赵伟均
 *
 */
public class MySQLSqlEngine extends BasicSqlEngine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3906596407170164697L;

	private static class InstanceHolder {
		private static final MySQLSqlEngine INSTANCE = new MySQLSqlEngine();
	}

	public static final MySQLSqlEngine getInstance() {
		return InstanceHolder.INSTANCE;
	}

}
