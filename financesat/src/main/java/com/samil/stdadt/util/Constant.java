package com.samil.stdadt.util;

import java.util.Arrays;
import java.util.List;

public class Constant {	
	public static class RESULT{
		public static final String SUCCESS = "success";
		public static final String FAIL = "fail";
		public static final String UNAUTHORIZED = "unauthorized";
		public static final String UNAUTHORIZED_MODIFY = "unauthorized_modify";
	}
	
	public static class RESULT_MSG{
		public static final String NO_PARAM = "파라메터가 없습니다.";
		public static final String ERROR = "데이터 처리 중 오류가 발생하였습니다.";
		public static final String SAVE_ERROR = "데이터 저장이 되지 않았습니다. 재시도 해 보시기 바랍니다.";
		public static final String UNATUTHORIZED = "데이터 접근 권한이 없습니다.";
		public static final String UNAUTHORIZED_MODIFY = "데이터 수정 권한이 없습니다.";
		public static final String UNAUTHORIZED_DELETE = "데이터 삭제 권한이 없습니다.";
		public static final String EMPTY_FILE = "업로드할 파일입니다.";
		public static final String NOT_ALLOW_FILE = "허용되지 않는 파일입니다.";
		public static final String RESULT_NULL = "데이터가 존재 하지 않습니다.";
	}
	
	public static class PARAM_NAME{
		public static final String SESSION = "session";
		public static final String APP_CD = "appCd";
		public static final String RESULT = "result";
		public static final String POPUP_INFO = "popupInfo";
	}
	
	public static class COLUMN_TYPE {
		public static final int STRING 		= 1;
		public static final int NUMERIC		= 2;
		public static final int CODE 		= 3;
		public static final int CODE_NAME 	= 4;
	}
	
	public static final String ALLOW_EXTENSION[] = new String[]{"xlsx", "xls"};
	public static final int maxWeekNum = 61;
	
	public static final List<String> BUDGET_ROLE_CD = Arrays.asList("09", "12", "13", "14", "15", "16");
}
