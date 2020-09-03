package cn.tenmg.sqltool.sql.meta;

import java.util.List;

public class EntityMeta {

	private String tableName;

	private List<FieldMeta> fieldMetas;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<FieldMeta> getFieldMetas() {
		return fieldMetas;
	}

	public void setFieldMetas(List<FieldMeta> fieldMetas) {
		this.fieldMetas = fieldMetas;
	}

}
