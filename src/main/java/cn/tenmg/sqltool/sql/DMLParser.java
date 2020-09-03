package cn.tenmg.sqltool.sql;

import java.io.Serializable;

public interface DMLParser extends Serializable {
	<T> DML parse(Class<T> type);
}