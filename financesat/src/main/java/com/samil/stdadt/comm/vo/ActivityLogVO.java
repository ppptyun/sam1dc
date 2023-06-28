package com.samil.stdadt.comm.vo;

import java.sql.Date;

public class ActivityLogVO {
	private String appCd;
	private String emplNo;
	private String inteId;
	private String pageUri;
	private String queryStr;
	private String actTy;
	private Date creDt;
	public String getAppCd() {
		return appCd;
	}
	public String getEmplNo() {
		return emplNo;
	}
	public String getInteId() {
		return inteId;
	}
	public String getPageUri() {
		return pageUri;
	}
	public String getQueryStr() {
		return queryStr;
	}
	public String getActTy() {
		return actTy;
	}
	public Date getCreDt() {
		return creDt;
	}
	public void setAppCd(String appCd) {
		this.appCd = appCd;
	}
	public void setEmplNo(String emplNo) {
		this.emplNo = emplNo;
	}
	public void setInteId(String inteId) {
		this.inteId = inteId;
	}
	public void setPageUri(String pageUri) {
		this.pageUri = pageUri;
	}
	public void setQueryStr(String queryStr) {
		this.queryStr = queryStr;
	}
	public void setActTy(String actTy) {
		this.actTy = actTy;
	}
	public void setCreDt(Date creDt) {
		this.creDt = creDt;
	}
}
