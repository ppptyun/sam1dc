package com.samil.dc.domain;

import java.io.Serializable;

public class AdminPEListBean implements Serializable {

	private static final long serialVersionUID = 5042346260169918038L;

	private String glinducd = "";
	private String glindunmk = "";
	private String glindunm = "";
	private String inducd = "";
	private String indunm = "";
	private String indunme = "";
	private String chkval = "";
	
	public AdminPEListBean() {
		
	}

	public String getGlinducd() {
		return glinducd;
	}

	public void setGlinducd(String glinducd) {
		this.glinducd = glinducd;
	}

	public String getGlindunmk() {
		return glindunmk;
	}

	public void setGlindunmk(String glindunmk) {
		this.glindunmk = glindunmk;
	}

	public String getGlindunm() {
		return glindunm;
	}

	public void setGlindunm(String glindunm) {
		this.glindunm = glindunm;
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

	public String getIndunme() {
		return indunme;
	}

	public void setIndunme(String indunme) {
		this.indunme = indunme;
	}

	public String getChkval() {
		return chkval;
	}

	public void setChkval(String chkval) {
		this.chkval = chkval;
	}
}
