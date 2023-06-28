package com.samil.dc.domain;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class CommonCodeListBean implements Serializable {

	private static final long serialVersionUID = 5065963152432195579L;

	private String ctgcd = "";
	private String itemcd = "";
	private String itemnm = "";
	
	public CommonCodeListBean() {
		
	}
	
	public CommonCodeListBean(CommonCodeListBean bean) {
		this.ctgcd = StringUtils.defaultIfBlank(bean.getCtgcd(), "");
		this.itemcd = StringUtils.defaultIfBlank(bean.getItemcd(), "");
		this.itemnm = StringUtils.defaultIfBlank(bean.getItemnm(), "");
	}

	public String getCtgcd() {
		return ctgcd;
	}

	public void setCtgcd(String ctgcd) {
		this.ctgcd = ctgcd;
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
