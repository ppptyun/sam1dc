package com.samil.dc.domain;

import java.io.Serializable;

public class DcAdminPdtCategoryListBean implements Serializable {
	
	private static final long serialVersionUID = -9173720933083981901L;

	private String refyearly = "";
	private String ctgcd = "";
	private String ctgnm = "";
	private String useyn = "";

	public DcAdminPdtCategoryListBean() {
		
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

	public String getUseyn() {
		return useyn;
	}

	public void setUseyn(String useyn) {
		this.useyn = useyn;
	}

	@Override
	public String toString() {
		return "[refyearly=" + refyearly + ", ctgcd=" + ctgcd + ", ctgnm=" + ctgnm + ", useyn=" + useyn + "]";
	}
}
