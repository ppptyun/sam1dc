package com.samil.dc.domain;

import java.io.Serializable;

public class DcCredentialAddBean implements Serializable {
	
	private static final long serialVersionUID = 9198479610123111715L;

	private String prjtcd = "";
	private String itemcd = "";
	private String itemnm = "";
	private String ctgcd = "";
	private String bigo = "";

	public DcCredentialAddBean() {
		
	}

	public String getPrjtcd() {
		return prjtcd;
	}

	public void setPrjtcd(String prjtcd) {
		this.prjtcd = prjtcd;
	}

	public String getItemcd() {
		return itemcd;
	}

	public void setItemcd(String itemcd) {
		this.itemcd = itemcd;
	}

	public String getItemnm() {
		return itemnm;
	}

	public void setItemnm(String itemnm) {
		this.itemnm = itemnm;
	}

	public String getCtgcd() {
		return ctgcd;
	}

	public void setCtgcd(String ctgcd) {
		this.ctgcd = ctgcd;
	}

	public String getBigo() {
		return bigo;
	}

	public void setBigo(String bigo) {
		this.bigo = bigo;
	}
}
