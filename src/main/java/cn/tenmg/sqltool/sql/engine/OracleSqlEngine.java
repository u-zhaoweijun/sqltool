package cn.tenmg.sqltool.sql.engine;

import java.sql.Timestamp;
import java.util.Date;

import cn.tenmg.sqltool.utils.DateUtils;

public class OracleSqlEngine extends AbstractSqlEngine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3721597636422726675L;

	private static final String TO_DATE = "TO_DATE('%s', 'yyyy-MM-dd HH24:mi:ss')";

	private static final String TO_TIMESTAMP = "TO_DATE('%s', 'yyyy-MM-dd HH24:mi:ss')";

	private static class InstanceHolder {
		private static final OracleSqlEngine INSTANCE = new OracleSqlEngine();
	}

	public static final OracleSqlEngine getInstance() {
		return InstanceHolder.INSTANCE;
	}

	@Override
	String parse(Date date) {
		if (date instanceof Timestamp) {
			return String.format(TO_TIMESTAMP, DateUtils.format(date, TIMESTAMP_FORMAT));
		} else {
			return String.format(TO_DATE, DateUtils.format(date, DATE_FORMAT));
		}
	}

}
