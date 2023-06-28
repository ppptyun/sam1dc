package com.samil.dc.domain;

import java.io.Serializable;

public class DcCredentialProductCategoryBean implements Serializable {

	private static final long serialVersionUID = -6038435449733493177L;
	
	private String refyearly = "";
	private String ctgcd = "";
	private String ctgnm = "";
	private String creempno = "";
	private String credt = "";
	private String updempno = "";
	private String upddt = "";
	private String useyn = "";

	public DcCredentialProductCategoryBean() {
		
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

	public String getCreempno() {
		return creempno;
	}

	public void setCreempno(String creempno) {
		this.creempno = creempno;
	}

	public String getCredt() {
		return credt;
	}

	public void setCredt(String credt) {
		this.credt = credt;
	}

	public String getUpdempno() {
		return updempno;
	}

	public void setUpdempno(String updempno) {
		this.updempno = updempno;
	}

	public String getUpddt() {
		return upddt;
	}

	public void setUpddt(String upddt) {
		this.upddt = upddt;
	}

	public String getUseyn() {
		return useyn;
	}

	public void setUseyn(String useyn) {
		this.useyn = useyn;
	}
}
