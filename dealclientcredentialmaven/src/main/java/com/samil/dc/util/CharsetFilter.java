package com.samil.dc.util;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CharsetFilter implements javax.servlet.Filter {

	 private String encoding;

	 public void init(FilterConfig config) throws ServletException {
	  encoding = config.getInitParameter("requestEncoding");
	  if (encoding == null)
	   encoding = "UTF-8";
	 }

	 public void doFilter(ServletRequest request, ServletResponse response, FilterChain next) throws IOException, ServletException, IllegalStateException {
	  request.setCharacterEncoding(encoding);
	  response.setCharacterEncoding(encoding);
	  next.doFilter(request, response);
	 }


	 public void destroy() {

	 }

}
