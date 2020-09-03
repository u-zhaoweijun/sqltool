package cn.tenmg.sqltool.sql.engine;

import java.util.Calendar;
import java.util.Date;

import cn.tenmg.sqltool.utils.DateUtils;

/**
 * MySQL方言的SQL引擎
 * 
 * @author 赵伟均
 *
 */
public class MySQLSqlEngine extends AbstractSqlEngine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3906596407170164697L;

	private static final String CALENDAR_FORMAT = "yyyy-MM-dd HH:mm:ss.S";

	private static class InstanceHolder {
		private static final MySQLSqlEngine INSTANCE = new MySQLSqlEngine();
	}

	public static final MySQLSqlEngine getInstance() {
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
