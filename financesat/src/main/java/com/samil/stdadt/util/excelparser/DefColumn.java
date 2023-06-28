package com.samil.stdadt.util.excelparser;

import com.samil.stdadt.util.Constant;

public class DefColumn {
	private String code;
	private String title;
	private Object refData;
	private int type = Constant.COLUMN_TYPE.STRING;
	private String grpCode;
	private String format;
	private ValidatorInterface[] validator;
	
	
	public String checkValidation(Object value) {
		String msg = "";
		if(validator == null) {
			return "";
		}else {	
			for(int i=0; i<validator.length; i++) {
				if(! validator[i].isValid(value).equals("")) {
					msg = validator[i].isValid(value);
					break;
				}
			}
		}
		return msg;
	}
	
	public DefColumn(String code) {
		this.code = code;
	}
	
	public DefColumn(String code, ValidatorInterface[] validator) {
		this.code = code;
		this.validator = validator;
	}
	
	public DefColumn(String code, int type) {
		this.code = code;
		this.type = type;
	}
	public DefColumn(String code, int type, ValidatorInterface[] validator) {
		this.code = code;
		this.type = type;
		this.validator = validator;
	}
	
	public DefColumn(String code, int type, Object refData, ValidatorInterface[] validator) {
		this.code = code;
		this.type = type;
		this.validator = validator;
		this.refData = refData;
	}
	
	
	public DefColumn(String code, boolean required, int type) {
		this.code = code;
		this.type = type;
	}
	
	public DefColumn(String code, boolean required, int type, ValidatorInterface[] validator) {
		this.code = code;
		this.type = type;
		this.validator = validator;
	}
	
	
	public DefColumn(String code, int type, String grpCode) {
		this.code = code;
		this.type = type;
		this.grpCode = grpCode;
	}
	
	public DefColumn(String code, int type, String grpCode, ValidatorInterface[] validator) {
		this.code = code;
		this.type = type;
		this.grpCode = grpCode;
		this.validator = validator;
	}

	public String getTitle() {
		return title;
	}


	public int getType() {
		return type;
	}

	public String getGrpCode() {
		return grpCode;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public void setType(int type) {
		this.type = type;
	}

	public void setGrpCode(String grpCode) {
		this.grpCode = grpCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public ValidatorInterface[] getValidator() {
		return validator;
	}

	public void setValidator(ValidatorInterface[] validator) {
		this.validator = validator;
	}

	public Object getRefData() {
		return refData;
	}

	public void setRefData(Object refData) {
		this.refData = refData;
	}

	public String getFormat() {
		return format;
	}

	public DefColumn setFormat(String format) {
		this.format = format;
		return this;
	}
}
