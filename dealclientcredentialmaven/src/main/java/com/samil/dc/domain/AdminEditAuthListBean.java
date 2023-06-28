package com.samil.dc.domain;

import java.io.Serializable;

public class AdminEditAuthListBean implements Serializable {
	
	private static final long serialVersionUID = 4400382645111644529L;

	private String rolecd = "";
	private String rolenm = "";
	private String edityn = "";
	private String upddt = "";

	public AdminEditAuthListBean() {
		
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

	public String getEdityn() {
		return edityn;
	}

	public void setEdityn(String edityn) {
		this.edityn = edityn;
	}

	public String getUpddt() {
		return upddt;
	}

	public void setUpddt(String upddt) {
		this.upddt = upddt;
	}
}
