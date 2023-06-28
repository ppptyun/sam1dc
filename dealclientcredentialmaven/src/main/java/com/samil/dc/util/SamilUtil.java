package com.samil.dc.util;

import java.io.Writer;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SamilUtil {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map getParameterMap(HttpServletRequest request) {
		Map parameterMap = new HashMap();
		Enumeration enums = request.getParameterNames();
		while (enums.hasMoreElements()) {
			String paramName = (String) enums.nextElement();
			String[] parameters = request.getParameterValues(paramName);

			if (parameters.length > 1) {
				// Parameter가 배열일 경우
				parameterMap.put(paramName, parameters);
			} else {
				// Parameter가 배열이 아닌 경우
				if(parameters[0]==null || parameters[0].length() <= 0){
					// 값이 없을 때는 파라메터로 추가하지 않음.
				}else{
					parameterMap.put(paramName, parameters[0]);	
				}
			}
		}
		return parameterMap;
	}
	
	public static Object getAlternativeNullValue(Object src, Object dest){
		if(src == null){
			return dest;
		}else if(src instanceof String){
			return ((String) src).length() > 0 ? src : dest;  
		}
		return src;
	}
	
	/**
	 * JSON 내보내기
	 * 
	 * @param res
	 * @param data
	 * @throws Exception
	 */
	public static void outputResponseJson(HttpServletResponse res, Map<String, Object> data) throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		String outStr = mapper.writeValueAsString(data);
		
		res.setCharacterEncoding("utf-8");
		res.setContentType("text/json;charset=utf-8");
		
		Writer out = res.getWriter();
		out.write(outStr);
		res.flushBuffer();
		out.close();
	}
	
	public static String escapeFileName(String filename){
		String[] invalidName = {"\\\\","/",":","[*]","[?]","\"","<",">","[|]"};
		for(int i=0; i<invalidName.length; i++){
			filename = filename.replaceAll(invalidName[i], "_");
		}
		return filename;
	}
}
