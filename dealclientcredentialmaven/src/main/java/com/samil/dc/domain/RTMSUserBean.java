package com.samil.dc.domain;

import java.io.Serializable;

public class RTMSUserBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5123483734303193442L;
	private String id = "";		// 사번
	private String name = "";	// 이름
	private String mail = "";	// 메일 주소
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	
	@Override
	public int hashCode() {
		return this.mail.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RTMSUserBean) {
			RTMSUserBean temp = (RTMSUserBean) obj;
			if (this.mail.equals(temp.mail)) {
				return true;
			}
		}
		return false;
	}
}
