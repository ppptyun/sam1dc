package com.samil.stdadt.util.excelparser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class Validator {
	Pattern essencePattern = Pattern.compile("^[0-9]+$");
	Pattern empnoPattern = Pattern.compile("^[0-9]{5,6}$");
	
	public boolean isNull(Object value) {
		return StringUtils.defaultIfBlank((String) value, "").equals("");
	}
	
	public ValidatorInterface isNotNull = (Object value) -> {
		return !isNull(value)?"":"필수 값 입니다.";
	};
	
	public ValidatorInterface isLong = (Object value) -> {
		if(!isNull(value)) {
			Matcher m = essencePattern.matcher(StringUtils.defaultIfBlank(value.toString().replaceAll(",", ""), ""));
			if(!m.find()) {
				return "정수를 입력해야 합니다.";
			}
		}
		return "";	
	};
	
	public ValidatorInterface isEmpNo = (Object value) -> {
		if(!isNull(value)) {
			Matcher m = empnoPattern.matcher(StringUtils.defaultIfBlank(value.toString().replaceAll(",", ""), ""));
			if(!m.find()) {
				return "사번 포맷은 6자리의 숫자입니다.";
			}
		}
		return "";
	};
}


