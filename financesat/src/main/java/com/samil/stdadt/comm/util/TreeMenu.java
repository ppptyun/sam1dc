package com.samil.stdadt.comm.util;

import java.util.ArrayList;
import java.util.List;

import com.samil.stdadt.comm.vo.AuthMenuVO;

public class TreeMenu {
	private List<AuthMenuVO> tMenu = new ArrayList<AuthMenuVO>();
	
	public List<AuthMenuVO> getTreeMenu(){
		return tMenu;
	}
	
	public void makeTree(String parent, AuthMenuVO menu) {
		if(parent.equals("root")) {
			tMenu.add(menu);
		}else {
			AuthMenuVO parentMenu = findMenu(parent, tMenu);
			if(parentMenu != null) parentMenu.addChildren(menu);
		}
	}
	
	private AuthMenuVO findMenu(String menuCd, List<AuthMenuVO> menuList) {
		if(menuList == null || menuList.size() == 0) return null;
		for(int i=0; i<menuList.size(); i++) {
			if(menuList.get(i).getMenuCd().equals(menuCd)) {
				return menuList.get(i);
			}else {
				AuthMenuVO tmp = findMenu(menuCd, menuList.get(i).getChildren());
				if(tmp != null) return tmp;
			}
		}
		return null;
	}
}
