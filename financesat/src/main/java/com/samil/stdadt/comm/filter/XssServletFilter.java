package com.samil.stdadt.comm.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.samil.stdadt.comm.util.XssRequestWrapper;

public class XssServletFilter implements Filter{

	private static final Logger logger = LoggerFactory.getLogger(XssServletFilter.class);
	private List<String> exceptUrl = new ArrayList<String>();
	
	
	@Override
	public void init(FilterConfig config) throws ServletException {
		String[] tmpExceptUrl = config.getInitParameter("exceptUrl").split("[\r\n]|,");
		for(int i=0; i<tmpExceptUrl.length; i++) {
			String url = tmpExceptUrl[i].trim();
			if(!url.equals("")) exceptUrl.add(url);
		}
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if(excludeUrl(request)){
			chain.doFilter(request, response); //예외 URI일 경우 요청값 그대로 처리
		}else{
			chain.doFilter(new XssRequestWrapper((HttpServletRequest) request), response);
		}
	}
	
	@Override
	public void destroy() {
		
	}

	
	private boolean excludeUrl(ServletRequest request) {
		String uri = ((HttpServletRequest) request).getRequestURI().toString().trim();
		String contextRoot = ((HttpServletRequest) request).getContextPath();
		
		boolean returnValue = false;
		for(String url : this.exceptUrl) {
			if(uri.startsWith(contextRoot + url)){
				logger.debug("Xss 예외 처리 : {}", uri);				
				returnValue = true;
			}
		}
		return returnValue;
	}
}
