package com.samil.dc.domain;

import java.io.Serializable;

public class AdminMenuDetailBean implements Serializable {

	private static final long serialVersionUID = -1377799821638849976L;
	
	private String upmenuid = "";
	private String menuid = "";
	private String menunm = "";
	private String url = "";
	private String sort = "";	
	
	public AdminMenuDetailBean() {
		
	}

	public String getUpmenuid() {
		return upmenuid;
	}

	public void setUpmenuid(String upmenuid) {
		this.upmenuid = upmenuid;
	}

	public String getMenuid() {
		return menuid;
	}

	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}

	public String getMenunm() {
		return menunm;
	}

	public void setMenunm(String menunm) {
		this.menunm = menunm;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
}
