package cn.tenmg.sqltool.sql.executer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cn.tenmg.sqltool.sql.SqlExecuter;

public abstract class AbstractExecuteSqlExecuter<T> implements SqlExecuter<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5664359106430145890L;

	@Override
	public ResultSet execute(PreparedStatement ps) throws SQLException {
		return null;
	}

}
