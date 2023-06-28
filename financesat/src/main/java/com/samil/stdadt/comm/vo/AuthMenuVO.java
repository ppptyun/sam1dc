package com.samil.stdadt.comm.vo;

import java.util.ArrayList;
import java.util.List;

public class AuthMenuVO {
	private String menuCd;
	private String menuNm;
	private String menuOpenTy;
	private String uri;
	private String menuIcon;
	private int ordby;
	private String pMenuCd;
	private List<AuthMenuVO> children;
	private String selected;
	
	public String getMenuCd() {
		return menuCd;
	}
	public String getMenuNm() {
		return menuNm;
	}
	public String getMenuOpenTy() {
		return menuOpenTy;
	}
	public String getUri() {
		return uri;
	}
	public void setMenuCd(String menuCd) {
		this.menuCd = menuCd;
	}
	public void setMenuNm(String menuNm) {
		this.menuNm = menuNm;
	}
	public void setMenuOpenTy(String menuOpenTy) {
		this.menuOpenTy = menuOpenTy;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public int getOrdby() {
		return ordby;
	}
	public String getpMenuCd() {
		return pMenuCd;
	}
	public void setOrdby(int ordby) {
		this.ordby = ordby;
	}
	public void setpMenuCd(String pMenuCd) {
		this.pMenuCd = pMenuCd;
	}
	
	public List<AuthMenuVO> getChildren() {
		return children;
	}
	public void setChildren(List<AuthMenuVO> children) {
		this.children = children;
	}
	public void addChildren(AuthMenuVO child) {
		if(this.children == null) {
			this.children = new ArrayList<AuthMenuVO>();
		}
		this.children.add(child);
	}
	public String getMenuIcon() {
		return menuIcon;
	}
	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}
	public String getSelected() {
		return selected;
	}
	public void setSelected(String selected) {
		this.selected = selected;
	}
}
