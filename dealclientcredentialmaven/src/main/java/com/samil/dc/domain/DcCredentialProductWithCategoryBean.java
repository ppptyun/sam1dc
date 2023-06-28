package com.samil.dc.domain;

import java.io.Serializable;

public class DcCredentialProductWithCategoryBean implements Serializable {
	
	private static final long serialVersionUID = 7856494465829439021L;
	
	private String refyearly = "";
	private String ctgcd = "";
	private String ctgnm = "";
	private String pdtcd = "";
	private String pdtnm = "";
	private String comnm = "";

	public DcCredentialProductWithCategoryBean() {
		
	}

	public String getPdtcd() {
		return pdtcd;
	}

	public void setPdtcd(String pdtcd) {
		this.pdtcd = pdtcd;
	}

	public String getPdtnm() {
		return pdtnm;
	}

	public void setPdtnm(String pdtnm) {
		this.pdtnm = pdtnm;
	}

	public String getRefyearly() {
		return refyearly;
	}

	public void setRefyearly(String refyearly) {
		this.refyearly = refyearly;
	}

	public String getCtgcd() {
		return ctgcd;
	}

	public void setCtgcd(String ctgcd) {
		this.ctgcd = ctgcd;
	}

	public String getCtgnm() {
		return ctgnm;
	}

	public void setCtgnm(String ctgnm) {
		this.ctgnm = ctgnm;
	}

	public String getComnm() {
		return comnm;
	}

	public void setComnm(String comnm) {
		this.comnm = comnm;
	}
	
}
