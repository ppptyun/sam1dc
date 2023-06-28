package com.samil.dc.domain;

import java.io.Serializable;

public class AdminRoleListBean implements Serializable {
	
	private static final long serialVersionUID = -955650572818175949L;

	private String orirolecd = "";
	private String rolecd = "";
	private String rolenm = "";
	private String sort = "";

	public AdminRoleListBean() {
		
	}

	public String getOrirolecd() {
		return orirolecd;
	}

	public void setOrirolecd(String orirolecd) {
		this.orirolecd = orirolecd;
	}

	public String getRolecd() {
		return rolecd;
	}

	public void setRolecd(String rolecd) {
		this.rolecd = rolecd;
	}

	public String getRolenm() {
		return rolenm;
	}

	public void setRolenm(String rolenm) {
		this.rolenm = rolenm;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
}
