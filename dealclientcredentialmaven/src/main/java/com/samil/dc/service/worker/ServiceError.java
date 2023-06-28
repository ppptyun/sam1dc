package com.samil.dc.service.worker;

import com.samil.dc.util.Constants;

public class ServiceError {
	
	private String errorCode = Constants.ErrorCode.INTERNAL;
	private String errorMessage = Constants.ErrorMessage.INTERNAL;

	public ServiceError() {
		
	}
	
	public ServiceError(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
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
}
