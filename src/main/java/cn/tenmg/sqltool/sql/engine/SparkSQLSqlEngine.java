package cn.tenmg.sqltool.sql.engine;

public class SparkSQLSqlEngine extends BasicSqlEngine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4709716690186443192L;

	private static class InstanceHolder {
		private static final SparkSQLSqlEngine INSTANCE = new SparkSQLSqlEngine();
	}

	public static final SparkSQLSqlEngine getInstance() {
		return InstanceHolder.INSTANCE;
	}

}
