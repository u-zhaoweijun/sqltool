package cn.tenmg.sqltool.sql.engine;

public class PostgreSQLSqlEngine extends BasicSqlEngine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1203948960319093811L;

	private static class InstanceHolder {
		private static final PostgreSQLSqlEngine INSTANCE = new PostgreSQLSqlEngine();
	}

	public static final PostgreSQLSqlEngine getInstance() {
		return InstanceHolder.INSTANCE;
	}

}
