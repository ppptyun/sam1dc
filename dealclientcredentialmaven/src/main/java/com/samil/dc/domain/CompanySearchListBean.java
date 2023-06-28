package com.samil.dc.domain;

import java.io.Serializable;

public class CompanySearchListBean implements Serializable {
	
	private static final long serialVersionUID = 5820219944233376653L;
	
	private String sourcetype = "";
	private String client = "";
	private String upchecd = "";
	private String hangnm = "";
	private String englnm = "";
	private String bubinno = "";
	private String korreprnm = "";
	private String addr = "";
	private String inducd = "";
	private String indunm = "";
	private String glinducd = "";
	private String glindunmk = "";
	
	public CompanySearchListBean() {
		
	}

	public String getSourcetype() {
		return sourcetype;
	}

	public void setSourcetype(String sourcetype) {
		this.sourcetype = sourcetype;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getUpchecd() {
		return upchecd;
	}

	public void setUpchecd(String upchecd) {
		this.upchecd = upchecd;
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

	public String getBubinno() {
		return bubinno;
	}

	public void setBubinno(String bubinno) {
		this.bubinno = bubinno;
	}

	public String getKorreprnm() {
		return korreprnm;
	}

	public void setKorreprnm(String korreprnm) {
		this.korreprnm = korreprnm;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
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

}
