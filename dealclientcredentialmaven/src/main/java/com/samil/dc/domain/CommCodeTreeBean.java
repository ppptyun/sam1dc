package com.samil.dc.domain;

import java.io.Serializable;

public class CommCodeTreeBean implements Serializable {

	private static final long serialVersionUID = -1377799821638849976L;
	
	private String parcd = "";
	private String itemcd = "";
	private String itemnm = "";
	
	public CommCodeTreeBean() {
		
	}

	public String getParcd() {
		return parcd;
	}


	public void setParcd(String parcd) {
		this.parcd = parcd;
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
	
}
