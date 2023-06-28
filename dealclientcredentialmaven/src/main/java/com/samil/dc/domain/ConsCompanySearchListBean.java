package com.samil.dc.domain;

import java.io.Serializable;

public class ConsCompanySearchListBean implements Serializable {
	
	private static final long serialVersionUID = 3177768820732400774L;
	
	private String compcd = "";
	private String hangnm = "";
	private String englnm = "";
	
	public ConsCompanySearchListBean() {
		
	}

	public String getCompcd() {
		return compcd;
	}

	public void setCompcd(String compcd) {
		this.compcd = compcd;
	}

	public String getHangnm() {
		return hangnm;
	}

	public void setHangnm(String hangnm) {
		this.hangnm = hangnm;
	}

	public String getEnglnm() {
		return englnm;
	}

	public void setEnglnm(String englnm) {
		this.englnm = englnm;
	}

}
