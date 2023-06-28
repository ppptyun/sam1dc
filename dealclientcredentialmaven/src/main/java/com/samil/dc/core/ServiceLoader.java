package com.samil.dc.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.dhtmlx.connector.ThreadSafeConnectorServlet;

import com.samil.dc.service.AppLogMngService;
import com.samil.dc.access.DBConnection;
import com.samil.dc.access.OracleConnection;
import com.samil.dc.service.DealClientCredentialService;

@SuppressWarnings("serial")
public class ServiceLoader extends ThreadSafeConnectorServlet {

	private static final Logger logger = Logger.getRootLogger();

	@Override
	protected void configure(HttpServletRequest req, HttpServletResponse res) {
		Object instance = null;
		Method method = null;
		DBConnection dbcon = null;
		
		String className;
		String methodName;
		
		try {
			
			className = req.getParameter("SystemCode");
			methodName = req.getParameter("Method");

			if(className == null || methodName == null){
				logger.error("Class Name or Method Name is not defined");
				return;
			}
			
			dbcon = new OracleConnection();
			Object[] args = { req, res, dbcon };

			if(className.equals("dealcredential")){
				instance = new DealClientCredentialService();
			}else if(className.equals("AppLogMngService")){
				instance = new AppLogMngService();
			}
				
			
			Enumeration<String> params = (Enumeration<String>)req.getParameterNames();
			
			String key = null;
			String param = null;
			
			while(params.hasMoreElements()){
				key = params.nextElement();
				
				if(param == null){
					param = key + ":" + req.getParameter(key);
				}else{
					param = param + ", " + key + ":" + req.getParameter(key);
				}
			}
			
			logger.info("<" + className + ":" + methodName + "> Request{" + param + "}");
			
			if (instance != null) {
				method = instance.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class, DBConnection.class);
				method.invoke(instance, args);
			}

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(dbcon != null) dbcon.close();
		}
	}
}