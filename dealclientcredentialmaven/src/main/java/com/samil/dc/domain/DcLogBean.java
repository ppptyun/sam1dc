package com.samil.dc.domain;

import java.io.Serializable;

public class DcLogBean implements Serializable {

	private static final long serialVersionUID = 1419283091017080455L;

	private String seq = "";
	private String logcd = "";
	private String altcd = "";
	private String fildnm = "";
	private String fildcd = "";
	private String befval = "";
	private String altval = "";
	private String prjtcd = "";
	private String bigo = "";
	private String altempno = "";
	
	public DcLogBean() {
		
	}
	
	public DcLogBean(String logcd) {
		this.logcd = logcd;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getLogcd() {
		return logcd;
	}

	public void setLogcd(String logcd) {
		this.logcd = logcd;
	}

	public String getAltcd() {
		return altcd;
	}

	public void setAltcd(String altcd) {
		this.altcd = altcd;
	}

	public String getFildnm() {
		return fildnm;
	}

	public void setFildnm(String fildnm) {
		this.fildnm = fildnm;
	}

	public String getFildcd() {
		return fildcd;
	}

	public void setFildcd(String fildcd) {
		this.fildcd = fildcd;
	}

	public String getBefval() {
		return befval;
	}

	public void setBefval(String befval) {
		this.befval = befval;
	}

	public String getAltval() {
		return altval;
	}

	public void setAltval(String altval) {
		this.altval = altval;
	}

	public String getPrjtcd() {
		return prjtcd;
	}

	public void setPrjtcd(String prjtcd) {
		this.prjtcd = prjtcd;
	}

	public String getBigo() {
		return bigo;
	}

	public void setBigo(String bigo) {
		this.bigo = bigo;
	}

	public String getAltempno() {
		return altempno;
	}

	public void setAltempno(String altempno) {
		this.altempno = altempno;
	}
}
