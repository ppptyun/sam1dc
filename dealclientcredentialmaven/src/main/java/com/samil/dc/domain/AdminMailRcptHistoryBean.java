package com.samil.dc.domain;

import java.io.Serializable;

public class AdminMailRcptHistoryBean implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 656759162976810302L;
	
	private String rmail 		= null;	// 메일 ID(필수)
	private String kornm	 	= null;	// 발송인 사번
	private String emplno 		= null;	// 발송인 이름
	private String gradnm 		= null;	// 메일 제목
	private String team 		= null; // 메일 본문
	
	public String getRmail() {
		return rmail;
	}
	public void setRmail(String rmail) {
		this.rmail = rmail;
	}
	public String getKornm() {
		return kornm;
	}
	public void setKornm(String kornm) {
		this.kornm = kornm;
	}
	public String getEmplno() {
		return emplno;
	}
	public void setEmplno(String emplno) {
		this.emplno = emplno;
	}
	public String getGradnm() {
		return gradnm;
	}
	public void setGradnm(String gradnm) {
		this.gradnm = gradnm;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	
}
