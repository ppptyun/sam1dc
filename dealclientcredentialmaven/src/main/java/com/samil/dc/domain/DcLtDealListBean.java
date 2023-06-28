package com.samil.dc.domain;

import java.io.Serializable;

public class DcLtDealListBean implements Serializable {
	
	private static final long serialVersionUID = 9087888116764599248L;

	private String prjtcd = "";
	private String dealitemcd = "";
	private String dealitemnm = "";

	public DcLtDealListBean() {
		
	}

	public String getPrjtcd() {
		return prjtcd;
	}

	public void setPrjtcd(String prjtcd) {
		this.prjtcd = prjtcd;
	}

	public String getDealitemcd() {
		return dealitemcd;
	}

	public void setDealitemcd(String dealitemcd) {
		this.dealitemcd = dealitemcd;
	}

	public String getDealitemnm() {
		return dealitemnm;
	}

	public void setDealitemnm(String dealitemnm) {
		this.dealitemnm = dealitemnm;
	}
}
