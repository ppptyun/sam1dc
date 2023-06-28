package com.samil.dc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 세션 사용자 정보 저장 클래스
 *
 */
public class UserSessionBeans implements Serializable {

	private static final long serialVersionUID = 7881273660749565883L;

	private String userId;
	private UserBeans userBeans;
	private List<UserRoleBeans> userRoleList = new ArrayList<UserRoleBeans>();
	private List<UserMenuBeans> userMenuList = new ArrayList<UserMenuBeans>();
	private String authJson = "";
	
	// 본인의 본부를 포함하여 관리할 수 있는 모든 본부 목록
	private List<String> userHQList = new ArrayList<String>();
	
	
	public UserSessionBeans() {
		
	}
	
	public UserSessionBeans(String userId) {
		this.userId = userId;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public UserBeans getUserBeans() {
		return userBeans;
	}

	public void setUserBeans(UserBeans userBeans) {
		this.userBeans = userBeans;
	}

	public List<UserRoleBeans> getUserRoleList() {
		return userRoleList;
	}

	public void setUserRoleList(List<UserRoleBeans> userRoleList) {
		this.userRoleList = userRoleList;
	}

	public List<UserMenuBeans> getUserMenuList() {
		return userMenuList;
	}

	public void setUserMenuList(List<UserMenuBeans> userMenuList) {
		this.userMenuList = userMenuList;
	}
	
	public void addUserRole(UserRoleBeans userRoleBeans) {
		this.userRoleList.add(userRoleBeans);
	}
	
	public void addUserMenu(UserMenuBeans userMenuBeans) {
		this.userMenuList.add(userMenuBeans);
	}

	public List<String> getUserHQList() {
		return userHQList;
	}

	public void setUserHQList(List<String> userHQList) {
		this.userHQList = userHQList;
	}
	
	public void addUserHQ(String hqcd) {
		this.userHQList.add(hqcd);
	}

	public String getAuthJson() {
		return authJson;
	}

	public void setAuthJson(String authJson) {
		this.authJson = authJson;
	}
}
