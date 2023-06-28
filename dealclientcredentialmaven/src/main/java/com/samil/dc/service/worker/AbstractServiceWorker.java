package com.samil.dc.service.worker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.samil.dc.access.DBConnection;

public abstract class AbstractServiceWorker {
	
	protected HttpServletRequest request = null;
	protected HttpServletResponse response = null;
	protected DBConnection connection = null;

	/**
	 * <pre>
	 * ====================================================================================
	 * 생성자
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @param response
	 * @param connection
	 */
	public AbstractServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
		this.request = request;
		this.response = response;
		this.connection = connection;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 서비스 유효성 체크
	 * ====================================================================================
	 * </pre>
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract ValidationResult validate() throws Exception;
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 서비스 로직 수행
	 * ====================================================================================
	 * </pre>
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract Object process() throws Exception;
}
