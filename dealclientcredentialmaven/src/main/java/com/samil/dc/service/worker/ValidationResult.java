package com.samil.dc.service.worker;

import com.samil.dc.util.Constants;

public class ValidationResult {
	
	private boolean valid = true;
	private String errorCode = Constants.ErrorCode.INTERNAL;
	private String errorMessage = Constants.ErrorMessage.INTERNAL;
	private String errorParameter = "";
	private String errorParameterValue = "";

	public ValidationResult() {
		valid = true;
	}
	
	public ValidationResult(boolean valid) {
		this.valid = valid;
	}
	
	public ValidationResult(String errorCode, String errorMessage) {
		this.valid = false;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	public void setErrorParameterAndValue(String errorParameter, String errorParameterValue) {
		this.errorParameter = errorParameter;
		this.errorParameterValue = errorParameterValue;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorParameter() {
		return errorParameter;
	}

	public void setErrorParameter(String errorParameter) {
		this.errorParameter = errorParameter;
	}

	public String getErrorParameterValue() {
		return errorParameterValue;
	}

	public void setErrorParameterValue(String errorParameterValue) {
		this.errorParameterValue = errorParameterValue;
	}
}
