package com.samil.dc.domain;

import java.io.Serializable;

public class AdminMenuRoleBean implements Serializable {

	private static final long serialVersionUID = 3047586035379069131L;

	private String originrolecd = "";
	private String rolecd = "";
	
	public String getOriginrolecd() {
		return originrolecd;
	}
	public void setOriginrolecd(String originrolecd) {
		this.originrolecd = originrolecd;
	}
	public String getRolecd() {
		return rolecd;
	}
	public void setRolecd(String rolecd) {
		this.rolecd = rolecd;
	}
}
