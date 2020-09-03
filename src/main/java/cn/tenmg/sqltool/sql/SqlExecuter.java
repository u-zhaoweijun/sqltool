package cn.tenmg.sqltool.sql;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface SqlExecuter<T> extends Serializable {

	ResultSet execute(PreparedStatement ps) throws SQLException;

	T execute(PreparedStatement ps, ResultSet rs) throws SQLException;
}
