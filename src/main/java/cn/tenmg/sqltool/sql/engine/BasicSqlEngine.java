package cn.tenmg.sqltool.sql.engine;

import java.sql.Timestamp;
import java.util.Date;

import cn.tenmg.sqltool.utils.DateUtils;

/**
 * 基本SQL引擎
 * 
 * @author 赵伟均
 *
 */
public class BasicSqlEngine extends AbstractSqlEngine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3122690010713609511L;

	@Override
	String parse(Date date) {
		if (date instanceof Timestamp) {
			return DateUtils.format(date, TIMESTAMP_FORMAT);
		} else {
			return DateUtils.format(date, DATE_FORMAT);
		}
	}

}
