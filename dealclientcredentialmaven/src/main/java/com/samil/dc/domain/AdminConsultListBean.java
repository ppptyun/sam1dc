package com.samil.dc.domain;

import java.io.Serializable;

public class AdminConsultListBean implements Serializable {

	private static final long serialVersionUID = 7691878092569747104L;
	
	private String compcd = "";
	private String comphannm = "";
	private String compengnm = "";
	private String financeyn = "";
	private String audityn = "";
	private String lawyn = "";
	private String useyn = "";
	
	public AdminConsultListBean() {
		
	}

	public String getCompcd() {
		return compcd;
	}

	public void setCompcd(String compcd) {
		this.compcd = compcd;
	}

	public String getComphannm() {
		return comphannm;
	}

	public void setComphannm(String comphannm) {
		this.comphannm = comphannm;
	}

	public String getCompengnm() {
		return compengnm;
	}

	public void setCompengnm(String compengnm) {
		this.compengnm = compengnm;
	}

	public String getFinanceyn() {
		return financeyn;
	}

	public void setFinanceyn(String financeyn) {
		this.financeyn = financeyn;
	}

	public String getAudityn() {
		return audityn;
	}

	public void setAudityn(String audityn) {
		this.audityn = audityn;
	}

	public String getLawyn() {
		return lawyn;
	}

	public void setLawyn(String lawyn) {
		this.lawyn = lawyn;
	}

	public String getUseyn() {
		return useyn;
	}

	public void setUseyn(String useyn) {
		this.useyn = useyn;
	}
	
	@Override
	public String toString() {
		return "[compcd=" + getCompcd() + ", comphannm=" + getComphannm() + ", compengnm=" + getCompengnm() + ", financeyn=" + getFinanceyn() + ", audityn=" + getAudityn() + ", lawyn=" + getLawyn() + ", useyn=" + getUseyn() + "]";
	}
}
