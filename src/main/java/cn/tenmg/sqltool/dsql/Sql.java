package cn.tenmg.sqltool.dsql;

import java.io.Serializable;
import java.util.Map;

/**
 * SQL对象模型
 * 
 * @author 赵伟均
 *
 */
public class Sql implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3876821753500865601L;

	/**
	 * SQL
	 */
	private String script;

	/**
	 * 参数
	 */
	private Map<String, Object> params;

	public Sql() {
		super();
	}

	public Sql(String script) {
		super();
		this.script = script;
	}

	public Sql(String script, Map<String, Object> params) {
		super();
		this.script = script;
		this.params = params;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

}
