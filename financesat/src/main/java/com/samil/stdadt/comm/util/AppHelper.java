package com.samil.stdadt.comm.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.samil.stdadt.comm.vo.CamelMap;
import com.samil.stdadt.comm.vo.UserSessionVO;

public class AppHelper {
	public static UserSessionVO getSession(HttpServletRequest request) {
		return (UserSessionVO) request.getSession().getAttribute(AppConstant.sesseionKey);
	}
	
	public static void setSession(HttpServletRequest request, UserSessionVO userSession) {
		request.getSession().setAttribute(AppConstant.sesseionKey, userSession);
	}
	
	public static void removeSession(HttpServletRequest request) {
		request.getSession().removeAttribute(AppConstant.sesseionKey);
	}
	
	public static Map<String, Object> toCamelKey(Map<String, Object> map){
		Iterator<String> i = map.keySet().iterator();
		CamelMap retVal = new CamelMap();
		while(i.hasNext()) {
			String key = i.next();
			retVal.put(key, map.get(key));
		}
		return retVal;
	}
	
	public static String getLoginId(HttpServletRequest request) throws Exception {
		String loginId = null;
		if(request.getUserPrincipal() != null){
			loginId = request.getUserPrincipal().getName(); 
		}
		return loginId;
	}
	
	public static String toCamelString(String str) {
		StringBuilder builder = new StringBuilder(str.toLowerCase());
	    int len = builder.length();
	    String separator = "_";

	    for (int idx = builder.indexOf(separator); idx > 0 && idx < len; idx = builder.indexOf(separator, idx)) {
	        builder = builder.replace(idx, idx + 2, (String.valueOf(builder.charAt(idx + 1)).toUpperCase()));
	    }

	    builder.setCharAt(0, Character.toLowerCase(builder.charAt(0)));
	    return builder.toString();
	}
	
	public static Map<String, Object> convertReqeustToMap(HttpServletRequest request){
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		Enumeration<String> enums = request.getParameterNames();
		while(enums.hasMoreElements()){
			String paramName = (String)enums.nextElement();
			String[] parameters = request.getParameterValues(paramName);
			
			if(parameters.length == 1) {
				parameterMap.put(paramName, request.getParameter(paramName));
			}else if(parameters.length > 1) {
				Vector<Object> v = new Vector<Object>();
				for (int i = 0; i < parameters.length; i++) {
		              v.add(v.size(), parameters[i]);
		          }
				parameterMap.put(paramName, v);
			}
		}
		return parameterMap;
	}
	
	public static Object convertMapToObject(Map map, Object objClass){
		String keyAttribute = null;
		String setMethodString = "set";
		String methodString = null;
		Iterator itr = map.keySet().iterator();
		while(itr.hasNext()){
			keyAttribute = (String) itr.next();
			methodString = setMethodString+keyAttribute.substring(0,1).toUpperCase()+keyAttribute.substring(1);
			try {
				Method[] methods = objClass.getClass().getDeclaredMethods();
				for(int i=0;i<=methods.length-1;i++){
					if(methodString.equals(methods[i].getName())){
						methods[i].invoke(objClass, map.get(keyAttribute));
					}
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return objClass;
	}
	
	public static String convertObjectToJsonString(Object data) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(data);
	}
}
