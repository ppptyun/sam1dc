package com.samil.stdadt.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhncorp.lucy.security.xss.XssPreventer;
import com.samil.stdadt.comm.vo.CamelMap;

public class SHConverter {
	
	static final Logger logger = LoggerFactory.getLogger(SHConverter.class);
	
	
	public static String convertObjectToJsonString(Object data) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(data);
	}
	
	public static Map<String, Object> convertObjectToMap(Object data){
		 ObjectMapper oMapper = new ObjectMapper();
		 return oMapper.convertValue(data, Map.class);
	}
	
	/**
	 * <pre>
	 * JSON String을 Map 형태로 변환
	 * </pre>
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public static HashMap<String, Object> convertJSONstringToMap(String json) throws Exception {
		String data = XssPreventer.unescape(json);
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map = mapper.readValue(data, new TypeReference<Map<String, Object>>() {});
        for(String id : map.keySet()){
        	if(map.get(id) instanceof String){
        		map.put(id, (String) map.get(id));
        		map.put(id, XssPreventer.escape((String) map.get(id)));	
        	}
        }
        return map;
    }
	
	public static CamelMap camelMapFactory(Object... params) {
		CamelMap tmp = new CamelMap();
		for(int i=0; i<params.length; i+=2) {
			tmp.put((String) params[i], params[i+1]);
		}
		return tmp;
	}
}
