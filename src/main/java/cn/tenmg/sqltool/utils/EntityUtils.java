package cn.tenmg.sqltool.utils;

import cn.tenmg.sqltool.config.annotion.Table;

public abstract class EntityUtils {

	public static final String getTableName(Class<?> type) {
		Table table = type.getAnnotation(Table.class);
		String tableName;
		if (table != null) {
			tableName = table.name();
		} else {
			tableName = StringUtils.camelToUnderline(type.getSimpleName(), true);
		}
		return tableName;
	}
}
