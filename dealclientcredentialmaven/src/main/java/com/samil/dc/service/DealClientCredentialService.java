package com.samil.dc.service;

import java.io.Writer;
import java.lang.reflect.Constructor;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samil.dc.access.DBConnection;
import com.samil.dc.service.worker.AbstractServiceWorker;
import com.samil.dc.service.worker.ServiceError;
import com.samil.dc.service.worker.ServiceHelper;
import com.samil.dc.service.worker.ValidationResult;
import com.samil.dc.util.Constants;
import com.samil.dc.util.ppt.AbstractPptProcessor;

public class DealClientCredentialService {
	
	private static final Logger logger = Logger.getRootLogger();
	private static final String PARAM_CONTENT_TYPE = "ContentType";
	private static final String PARAM_SERVICE_TASK = "ServiceTask";
	private static final String CONTENT_TYPE_HTML = "html";
	private static final String CONTENT_TYPE_JSON = "json";
	private static final String CONTENT_TYPE_DOWNLOAD_PPT = "download_ppt";
	//private static final String JSP_PREFIX = "/WEB-INF/jsp/";
	private static final String JSP_PREFIX = "/contents/";
	private static final String JSP_SUFFIX = ".jsp";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 빈 생성자
	 * ====================================================================================
	 * </pre>
	 * 
	 */
	public DealClientCredentialService() {
		
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 서비스 로직 호출 메쏘드
	 * 
	 * 필수 파라미터
	 *     SystemCode
	 *     Method
	 *     ServiceTask
	 *     ContentType
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @param response
	 * @param connection
	 * @throws Exception
	 */
	public void service(HttpServletRequest request, HttpServletResponse response, DBConnection connection) throws Exception {
		// TODO 보안 강화 수정
		// A5 Security Misconfiguration - Access Specifier Manipulation [ Constructor.setAccessible(true); ]
		/*System.setSecurityManager(new SecurityManager() {
			@Override
			public void checkPermission(Permission perm) {
				if (perm instanceof ReflectPermission && "suppressAccessChecks".equals(perm.getName())) {
                    for (StackTraceElement elem : Thread.currentThread().getStackTrace()) {
                    	if ("DealClientCredentialService".equals(elem.getClassName()) && !"runServiceWorker".equals(elem.getMethodName())) {
                            throw new SecurityException();
                        }
                    }
                }
			}
		});*/
		
		String contentType = ServiceHelper.getParameter(request, PARAM_CONTENT_TYPE);
		if (contentType == null || "".equals(contentType)) {
			contentType = CONTENT_TYPE_JSON;
		}
//		if (!(CONTENT_TYPE_HTML.equals(contentType) || CONTENT_TYPE_JSON.equals(contentType))) {
//			contentType = CONTENT_TYPE_JSON;
//		}
		
		if (CONTENT_TYPE_JSON.equals(contentType)) {
			runServiceWorker(request, response, connection);
		} else if (CONTENT_TYPE_HTML.equals(contentType)) {
			forwardJsp(request, response, connection);
		} else if (CONTENT_TYPE_DOWNLOAD_PPT.equals(contentType)) {
			downloadPPT(request, response, connection);
		} 
		
		
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 서비스 워커 실행
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @param response
	 * @param connection
	 * @throws Exception
	 */
	private void runServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) throws Exception {
		ValidationResult validationResult = ServiceHelper.validateParameterBlank(request, PARAM_SERVICE_TASK);
		if (!validationResult.isValid()) {
			outputResponseJson(response, ServiceHelper.generateServiceResult(false, validationResult));
			return;
		}

		// 서비스 로직을 수행할 클래스 찾기
		// 클래스 명은 "Dc~ServiceWorker.java" 형식을 갖춰야 한다.
		String classNamePrefix = ServiceHelper.getParameter(request, PARAM_SERVICE_TASK);
		String className = getClass().getPackage().getName() + ".worker.Dc" + classNamePrefix + "ServiceWorker";
		if (logger.isDebugEnabled()) {
			logger.debug("ServiceWorker<" + className + ">");
		}
		
		Constructor<?> workerConstructor = null;
		try {
			//SecurityManager.
			workerConstructor = Class.forName(className).getDeclaredConstructor(HttpServletRequest.class, HttpServletResponse.class, DBConnection.class);
			workerConstructor.setAccessible(true);
		} catch (Exception e) {
			ServiceError serviceError = new ServiceError(Constants.ErrorCode.NOT_FOUND_SERVICE, Constants.ErrorMessage.NOT_FOUND_SERVICE);
			outputResponseJson(response, ServiceHelper.generateServiceResult(false, serviceError));
			return;
		}
		
		// 서비스 클래스
		AbstractServiceWorker worker = null;
		try {
			worker = (AbstractServiceWorker) workerConstructor.newInstance(request, response, connection);
		} catch (Exception e) {
			ServiceError serviceError = new ServiceError(Constants.ErrorCode.NOT_INIT_SERVICE, Constants.ErrorMessage.NOT_INIT_SERVICE);
			outputResponseJson(response, ServiceHelper.generateServiceResult(false, serviceError));
			return;
		}
		
		// 유효성 체크
		validationResult = worker.validate();
		if (validationResult == null) {
			validationResult = new ValidationResult(true);
		}
		if (!validationResult.isValid()) {
			outputResponseJson(response, ServiceHelper.generateServiceResult(false, validationResult));
			return;
		}
		
		// 서비스 로직 수행
		Object data = worker.process();
		
		// 결과 내보내기
		if (data instanceof ServiceError) {
			outputResponseJson(response, ServiceHelper.generateServiceResult(false, data));
		} else {
			outputResponseJson(response, ServiceHelper.generateServiceResult(true, data));
		}
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * JSP 포워드
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @param response
	 * @param connection
	 * @throws Exception
	 */
	private void forwardJsp(HttpServletRequest request, HttpServletResponse response, DBConnection connection) throws Exception {
		ValidationResult validationResult = ServiceHelper.validateParameterBlank(request, PARAM_SERVICE_TASK);
		if (!validationResult.isValid()) {
			// Service erro JSP 호출
			String serviceErrorJspPath = JSP_PREFIX + "error/validation_error" + JSP_SUFFIX;
			request.setAttribute("error", validationResult);
			RequestDispatcher requestDispatcher = request.getServletContext().getRequestDispatcher(serviceErrorJspPath);
			requestDispatcher.forward(request, response);
			return;
		}
		
		String jspInnerPath = ServiceHelper.getParameter(request, PARAM_SERVICE_TASK);
		String jspPath = JSP_PREFIX + jspInnerPath + JSP_SUFFIX;
		if (logger.isDebugEnabled()) {
			logger.debug("JSP<" + jspPath + ">");
		}
		
		// JSP 호출
		RequestDispatcher requestDispatcher = request.getServletContext().getRequestDispatcher(jspPath);
		requestDispatcher.forward(request, response);
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * JSON 내보내기
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param res
	 * @param result
	 * @throws Exception
	 */
	private void outputResponseJson(HttpServletResponse response, Map<String, Object> result) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String outStr = mapper.writeValueAsString(result);
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/json;charset=utf-8");
		
		Writer out = response.getWriter();
		out.write(outStr);
		response.flushBuffer();
		out.close();
	}
	
	private void downloadPPT(HttpServletRequest request, HttpServletResponse response, DBConnection connection) throws Exception{
		ValidationResult validationResult = ServiceHelper.validateParameterBlank(request, PARAM_SERVICE_TASK);
		if (!validationResult.isValid()) {
			outputResponseJson(response, ServiceHelper.generateServiceResult(false, validationResult));
			return;
		}

		// 서비스 로직을 수행할 클래스 찾기
		// 클래스 명은 "Dc~ServiceWorker.java" 형식을 갖춰야 한다.
		String classNamePrefix = ServiceHelper.getParameter(request, PARAM_SERVICE_TASK);
		String className = getClass().getPackage().getName() + ".worker.Dc" + classNamePrefix + "ServiceWorker";
		if (logger.isDebugEnabled()) {
			logger.debug("ServiceWorker<" + className + ">");
		}
		
		Constructor<?> workerConstructor = null;
		try {
			//SecurityManager.
			workerConstructor = Class.forName(className).getDeclaredConstructor(HttpServletRequest.class, HttpServletResponse.class, DBConnection.class);
			workerConstructor.setAccessible(true);
		} catch (Exception e) {
			ServiceError serviceError = new ServiceError(Constants.ErrorCode.NOT_FOUND_SERVICE, Constants.ErrorMessage.NOT_FOUND_SERVICE);
			outputResponseJson(response, ServiceHelper.generateServiceResult(false, serviceError));
			return;
		}
		
		// 서비스 클래스
		AbstractServiceWorker worker = null;
		try {
			worker = (AbstractServiceWorker) workerConstructor.newInstance(request, response, connection);
		} catch (Exception e) {
			ServiceError serviceError = new ServiceError(Constants.ErrorCode.NOT_INIT_SERVICE, Constants.ErrorMessage.NOT_INIT_SERVICE);
			outputResponseJson(response, ServiceHelper.generateServiceResult(false, serviceError));
			return;
		}
		
		// 유효성 체크
		validationResult = worker.validate();
		if (validationResult == null) {
			validationResult = new ValidationResult(true);
		}
		if (!validationResult.isValid()) {
			outputResponseJson(response, ServiceHelper.generateServiceResult(false, validationResult));
			return;
		}
		
		// 서비스 로직 수행
		AbstractPptProcessor data = (AbstractPptProcessor) worker.process();
		data.output(response);
		
		response.flushBuffer();
		response.getOutputStream().close();
	}
}
