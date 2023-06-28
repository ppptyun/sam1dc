package com.samil.dc.domain;

import java.io.Serializable;

public class AdminRoleMemberListBean implements Serializable {
	
	private static final long serialVersionUID = 1685072951030799868L;

	private String rolecd = "";
	private String empno = "";
	private String empnm = "";
	private String teamcd = "";
	private String teamnm = "";
	private String gradcd = "";
	private String gradnm = "";

	public AdminRoleMemberListBean() {
		
	}

	public String getRolecd() {
		return rolecd;
	}

	public void setRolecd(String rolecd) {
		this.rolecd = rolecd;
	}

	public String getEmpno() {
		return empno;
	}

	public void setEmpno(String empno) {
		this.empno = empno;
	}

	public String getEmpnm() {
		return empnm;
	}

	public void setEmpnm(String empnm) {
		this.empnm = empnm;
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
