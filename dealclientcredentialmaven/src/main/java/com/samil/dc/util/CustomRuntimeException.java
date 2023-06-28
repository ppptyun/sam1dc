package com.samil.dc.util;

public class CustomRuntimeException extends RuntimeException{
	private static final long serialVersionUID = 6613946977949850226L;
	public CustomRuntimeException(String msg) {super(msg);}
	public static final CustomRuntimeException DUPLICATE_KEY = new CustomRuntimeException("키 값이 중복되었습니다.");
	public static final CustomRuntimeException INVALID_PARAM_JSON = new CustomRuntimeException("파라미터가 json형이 아닙니다.");
}
