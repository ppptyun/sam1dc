package com.samil.dc.domain;

import java.io.Serializable;

public class AdminFieldLogListBean implements Serializable {

	private static final long serialVersionUID = 5677177697114982550L;

	private String seq = "";
	private String prjtcd = "";
	private String fildcd = "";
	private String fildnm = "";
	private String logcd = "";
	private String lognm = "";
	private String altcd = "";
	private String altnm = "";
	private String befval = "";
	private String altval = "";
	private String altempno = "";
	private String altempnm = "";
	private String altgradnm = "";
	private String altdt = "";
	private String bigo = "";
	private String altemp = "";
	
	public AdminFieldLogListBean() {
		
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getPrjtcd() {
		return prjtcd;
	}

	public void setPrjtcd(String prjtcd) {
		this.prjtcd = prjtcd;
	}

	public String getFildcd() {
		return fildcd;
	}

	public void setFildcd(String fildcd) {
		this.fildcd = fildcd;
	}

	public String getLogcd() {
		return logcd;
	}

	public void setLogcd(String logcd) {
		this.logcd = logcd;
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

	public String getAltempno() {
		return altempno;
	}

	public void setAltempno(String altempno) {
		this.altempno = altempno;
	}

	public String getAltdt() {
		return altdt;
	}

	public void setAltdt(String altdt) {
		this.altdt = altdt;
	}

	public String getBigo() {
		return bigo;
	}

	public void setBigo(String bigo) {
		this.bigo = bigo;
	}

	public String getLognm() {
		return lognm;
	}

	public void setLognm(String lognm) {
		this.lognm = lognm;
	}

	public String getAltempnm() {
		return altempnm;
	}

	public void setAltempnm(String altempnm) {
		this.altempnm = altempnm;
	}

	public String getAltgradnm() {
		return altgradnm;
	}

	public void setAltgradnm(String altgradnm) {
		this.altgradnm = altgradnm;
	}

	public String getAltemp() {
		return altemp;
	}

	public void setAltemp(String altemp) {
		this.altemp = altemp;
	}

	public String getFildnm() {
		return fildnm;
	}

	public void setFildnm(String fildnm) {
		this.fildnm = fildnm;
	}

	public String getAltcd() {
		return altcd;
	}

	public void setAltcd(String altcd) {
		this.altcd = altcd;
	}

	public String getAltnm() {
		return altnm;
	}

	public void setAltnm(String altnm) {
		this.altnm = altnm;
	}
}
