package com.samil.stdadt.comm.vo;

import org.apache.commons.collections4.map.ListOrderedMap;

public class CamelMap extends ListOrderedMap<String, Object> {
	private static final long serialVersionUID = 3318640143893032032L;


	public Object put(String key, Object value) {
        return super.put(toCamel(key), value);
    }
	
	
	public String toCamel(String src) {
	    StringBuilder builder = new StringBuilder(src.toLowerCase());
	    int len = builder.length();
	    String separator = "_";

	    for (int idx = builder.indexOf(separator); idx > 0 && idx < len; idx = builder.indexOf(separator, idx)) {
	        builder = builder.replace(idx, idx + 2, (String.valueOf(builder.charAt(idx + 1)).toUpperCase()));
	    }

	    builder.setCharAt(0, Character.toLowerCase(builder.charAt(0)));
	    return builder.toString();
	}
}
