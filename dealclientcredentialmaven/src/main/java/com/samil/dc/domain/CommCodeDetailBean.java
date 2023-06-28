package com.samil.dc.domain;

import java.io.Serializable;

public class CommCodeDetailBean implements Serializable {

	private static final long serialVersionUID = -1377799821638849976L;
	
	private String parcd = "";
	private String itemcd = "";
	private String itemnm = "";
	private String descp = "";
	private String useyn = "";
	private String sort = "";
	private String creempno = "";
	private String credt = "";
	private String updempno = "";
	private String upddt = "";
	
	public CommCodeDetailBean() {
		
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

	public String getDescp() {
		return descp;
	}

	public void setDescp(String descp) {
		this.descp = descp;
	}

	public String getUseyn() {
		return useyn;
	}

	public void setUseyn(String useyn) {
		this.useyn = useyn;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
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
	
}
