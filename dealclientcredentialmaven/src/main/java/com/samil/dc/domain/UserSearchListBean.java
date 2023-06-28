package com.samil.dc.domain;

import java.io.Serializable;

public class UserSearchListBean implements Serializable {

	private static final long serialVersionUID = -5431106506428666459L;

	private String inteid = "";
	private String email = "";
	private String emplno = "";
	private String kornm = "";
	private String teamcd = "";
	private String teamnm = "";
	private String gradcd = "";
	private String gradnm = "";
	
	public UserSearchListBean() {
		
	}

	public String getInteid() {
		return inteid;
	}

	public void setInteid(String inteid) {
		this.inteid = inteid;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmplno() {
		return emplno;
	}

	public void setEmplno(String emplno) {
		this.emplno = emplno;
	}

	public String getKornm() {
		return kornm;
	}

	public void setKornm(String kornm) {
		this.kornm = kornm;
	}

	public String getTeamcd() {
		return teamcd;
	}

	public void setTeamcd(String teamcd) {
		this.teamcd = teamcd;
	}

	public String getTeamnm() {
		return teamnm;
	}

	public void setTeamnm(String teamnm) {
		this.teamnm = teamnm;
	}

	public String getGradcd() {
		return gradcd;
	}

	public void setGradcd(String gradcd) {
		this.gradcd = gradcd;
	}

	public String getGradnm() {
		return gradnm;
	}

	public void setGradnm(String gradnm) {
		this.gradnm = gradnm;
	}
}
