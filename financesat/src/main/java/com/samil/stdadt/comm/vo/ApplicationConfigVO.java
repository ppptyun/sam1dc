package com.samil.stdadt.comm.vo;

public class ApplicationConfigVO {
	private String env;
	private String appCd;
	private String portalUrl;
	private String admUrl;
	private String defaultLoginId;
	private String uploadPath; 
	private String excelTemplatePath;
	
	public String getPortalUrl() {
		return portalUrl;
	}
	public String getDefaultLoginId() {
		return defaultLoginId;
	}
	public void setPortalUrl(String portalUrl) {
		this.portalUrl = portalUrl;
	}
	public void setDefaultLoginId(String defaultLoginId) {
		this.defaultLoginId = defaultLoginId;
	}
	public String getAppCd() {
		return appCd;
	}
	public void setAppCd(String appCd) {
		this.appCd = appCd;
	}
	public String getAdmUrl() {
		return admUrl;
	}
	public void setAdmUrl(String admUrl) {
		this.admUrl = admUrl;
	}
	public String getUploadPath() {
		return uploadPath;
	}
	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}
	public String getExcelTemplatePath() {
		return excelTemplatePath;
	}
	public void setExcelTemplatePath(String excelTemplatePath) {
		this.excelTemplatePath = excelTemplatePath;
	}
	public String getEnv() {
		return env;
	}
	public void setEnv(String env) {
		this.env = env;
	}
	
}
