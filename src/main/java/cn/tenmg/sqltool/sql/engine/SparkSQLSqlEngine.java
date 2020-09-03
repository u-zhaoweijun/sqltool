package cn.tenmg.sqltool.sql.engine;

import java.util.Calendar;
import java.util.Date;

import cn.tenmg.sqltool.utils.DateUtils;

public class SparkSQLSqlEngine extends AbstractSqlEngine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4709716690186443192L;

	private static final String CALENDAR_FORMAT = "yyyy-MM-dd HH:mm:ss.S";

	private static class InstanceHolder {
		private static final SparkSQLSqlEngine INSTANCE = new SparkSQLSqlEngine();
	}

	public static final SparkSQLSqlEngine getInstance() {
		return InstanceHolder.INSTANCE;
	}

	@Override
	String parse(Date date) {
		return DateUtils.format(date, CALENDAR_FORMAT);
	}

	@Override
	String parse(Calendar calendar) {
		return DateUtils.format(calendar.getTime(), CALENDAR_FORMAT);
	}

}
