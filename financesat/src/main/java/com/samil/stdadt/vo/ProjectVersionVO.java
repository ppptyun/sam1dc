package com.samil.stdadt.vo;

import java.util.List;

public class ProjectVersionVO {
	private String appCd;
	private String prjtCd;
	private long version;
	private String versionTy;
	private String creBy;
	private List<WeekVO> weeks;
	
	public String getPrjtCd() {
		return prjtCd;
	}
	public long getVersion() {
		return version;
	}
	public String getVersionTy() {
		return versionTy;
	}
	public void setPrjtCd(String prjtCd) {
		this.prjtCd = prjtCd;
	}
	public void setVersion(long version) {
		this.version = version;
	}
	public void setVersionTy(String versionTy) {
		this.versionTy = versionTy;
	}
	public String getCreBy() {
		return creBy;
	}
	public void setCreBy(String creBy) {
		this.creBy = creBy;
	}
	public String getAppCd() {
		return appCd;
	}
	public void setAppCd(String appCd) {
		this.appCd = appCd;
	}
	public List<WeekVO> getWeeks() {
		return weeks;
	}
	public void setWeeks(List<WeekVO> weeks) {
		this.weeks = weeks;
	}
	
}
