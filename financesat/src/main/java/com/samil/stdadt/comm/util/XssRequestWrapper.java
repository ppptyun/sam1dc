package com.samil.stdadt.comm.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.nhncorp.lucy.security.xss.XssPreventer;

public class XssRequestWrapper extends HttpServletRequestWrapper {
	public XssRequestWrapper(HttpServletRequest servletRequest) {
		super(servletRequest);
	}

	public String[] getParameterValues(String parameter) {

		String[] values = super.getParameterValues(parameter);

		if (values == null) {
			return null;
		}

		int count = values.length;
		String[] encodedValues = new String[count];

		for (int i = 0; i < count; i++) {
			encodedValues[i] = XssPreventer.escape(values[i]);
		}
		
		return encodedValues;
	}

	public String getParameter(String parameter) {
		
		String value = super.getParameter(parameter);

		if (value == null) {
			return null;
		}

		return XssPreventer.escape(value);
	}

	public String getHeader(String name) {
		
		String value = super.getHeader(name);

		if (value == null) {
			return null;
		}

		return XssPreventer.escape(value);
	}
}