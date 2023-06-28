package com.samil.dc.domain;

import java.io.Serializable;

public class AdminHQEmpListBean implements Serializable {

	private static final long serialVersionUID = -2244460563578178621L;

	private String hqcd = "";
	private String hqnm = "";
	private String grpnm = "";
	private String empno = "";
	private String empnm = "";
	private String gradnm = "";
	
	public AdminHQEmpListBean() {
		
	}

	public String getHqcd() {
		return hqcd;
	}

	public void setHqcd(String hqcd) {
		this.hqcd = hqcd;
	}

	public String getHqnm() {
		return hqnm;
	}

	public void setHqnm(String hqnm) {
		this.hqnm = hqnm;
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

	public String getGradnm() {
		return gradnm;
	}

	public void setGradnm(String gradnm) {
		this.gradnm = gradnm;
	}

	public String getGrpnm() {
		return grpnm;
	}

	public void setGrpnm(String grpnm) {
		this.grpnm = grpnm;
	}
}
