package cn.tenmg.sqltool.utils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.tenmg.sqltool.exception.DataAccessException;

/**
 * JDBC工具类
 * 
 * @author v-zhaoweijun
 *
 */
public abstract class JdbcUtils {

	private static final Logger log = LoggerFactory.getLogger(JdbcUtils.class);

	private JdbcUtils() {
	}

	public static void close(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException ex) {
				if (log.isErrorEnabled()) {
					log.error("Could not close JDBC Connection", ex);
				}
				ex.printStackTrace();
			} catch (Throwable ex) {
				if (log.isErrorEnabled()) {
					log.error("Unexpected exception on closing JDBC Connection", ex);
				}
				ex.printStackTrace();
			}
		}
	}

	public static void close(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException ex) {
				if (log.isErrorEnabled()) {
					log.error("Could not close JDBC Statement", ex);
				}
				ex.printStackTrace();
			} catch (Throwable ex) {
				if (log.isErrorEnabled()) {
					log.error("Unexpected exception on closing JDBC Statement", ex);
				}
				ex.printStackTrace();
			}
		}
	}

	public static void close(ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException ex) {
				if (log.isErrorEnabled()) {
					log.error("Could not close JDBC ResultSet", ex);
				}
			} catch (Throwable ex) {
				if (log.isErrorEnabled()) {
					log.error("Unexpected exception on closing JDBC ResultSet", ex);
				}
			}
		}
	}

	public static <T> List<Object> getParams(T obj, List<Field> fields) {
		List<Object> params = new ArrayList<Object>();
		for (int i = 0, size = fields.size(); i < size; i++) {
			try {
				params.add(fields.get(i).get(obj));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new DataAccessException(e);
			}
		}
		return params;
	}

	/**
	 * 设置查询参数
	 * 
	 * @param ps
	 *            SQL声明对象
	 * @param params
	 *            查询参数
	 * @throws SQLException
	 */
	public static void setParams(PreparedStatement ps, List<Object> params) throws SQLException {
		if (!CollectionUtils.isEmpty(params)) {
			for (int i = 0, size = params.size(); i < size; i++) {
				ps.setObject(i + 1, params.get(i));
			}
		}
	}
}
