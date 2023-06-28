package com.samil.dc.domain;

import java.io.Serializable;

public class UserRoleBeans implements Serializable {
	
	private static final long serialVersionUID = 8931126325765319244L;

	private String rolecd = "";
	private String rolenm = "";

	public UserRoleBeans() {
		
	}
	
	public UserRoleBeans(String rolecd) {
		this.rolecd = rolecd;
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
}
