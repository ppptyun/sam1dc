package com.samil.dc.domain;

import java.io.Serializable;

public class UserMenuBeans implements Serializable {
	
	private static final long serialVersionUID = -2492805096960611708L;
	
	private int level = 0;
	private String upmenuid = "";
	private String menuid = "";
	private String menunm = "";
	private String url = "";
	private int sort = 0;

	public UserMenuBeans() {
		
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
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

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}
}
