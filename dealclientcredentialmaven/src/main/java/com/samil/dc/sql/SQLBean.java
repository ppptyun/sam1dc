package com.samil.dc.sql;

import java.util.LinkedHashMap;
import java.util.Map;

public class SQLBean {
	private String sql="";
	private Map<String, Object> valueMap=new LinkedHashMap<String, Object>();
	
	public String getSql() {
		return sql;
	}
	public void addSql(String sql){
		this.sql = this.sql + sql;
	}
	public Map<String, Object> getValue() {
		return valueMap;
	}
	public void addValue(String key, Object value) {
		this.valueMap.put(key, value);
	}
}
