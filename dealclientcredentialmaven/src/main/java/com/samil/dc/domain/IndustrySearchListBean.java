package com.samil.dc.domain;

import java.io.Serializable;

public class IndustrySearchListBean implements Serializable {

	private static final long serialVersionUID = 3568048908126211410L;

	private String indugcd = "";
	private String indugnm = "";
	private String inducd = "";
	private String indunm = "";
	
	
	public IndustrySearchListBean() {
		
	}

	public String getInducd() {
		return inducd;
	}

	public void setInducd(String inducd) {
		this.inducd = inducd;
	}

	public String getIndunm() {
		return indunm;
	}

	public void setIndunm(String indunm) {
		this.indunm = indunm;
	}

	public String getIndugcd() {
		return indugcd;
	}

	public void setIndugcd(String indugcd) {
		this.indugcd = indugcd;
	}

	public String getIndugnm() {
		return indugnm;
	}

	public void setIndugnm(String indugnm) {
		this.indugnm = indugnm;
	}

}
