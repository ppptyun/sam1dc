package com.samil.dc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.samil.dc.access.DBConnection;
import com.samil.dc.core.ContextLoader;
import com.samil.dc.domain.AdminMenuDetailBean;
import com.samil.dc.domain.AdminMenuRoleBean;
import com.samil.dc.domain.AdminMenuTreeBean;
import com.samil.dc.sql.DcAdminMenuSQL;
import com.samil.dc.sql.SQLManagement;


public class DcAdminMenuDAO {
	String PRGCD = "";
	DBConnection dbcon = null;
	
	private static final Logger logger = Logger.getRootLogger();
	
	public DcAdminMenuDAO(DBConnection _dbcon){
		dbcon = _dbcon;
		PRGCD = ContextLoader.getContext("ProgramCode");
	}
	/**
	 * <pre>
	 * ====================================================================================
	 * 트리 검색
	 * ====================================================================================
	 * </pre>
	 * 
	 * @throws SQLException
	 */
	public List<AdminMenuTreeBean> sqlSelectMenuTree() throws SQLException{
		List<AdminMenuTreeBean> list = new ArrayList<AdminMenuTreeBean>();
		
		String sql = DcAdminMenuSQL.SELECT_MENU_TREE;
		SQLManagement sqlmng = new SQLManagement(sql);
		ResultSet rs = null;
		
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}
		rs = dbcon.executeQuery(sqlmng.getSql());
		
		if (rs != null) {
			while(rs.next()){
				AdminMenuTreeBean menuVo = new AdminMenuTreeBean();
				menuVo.setUpmenuid(rs.getString("UPMENUID"));
				menuVo.setMenuid(rs.getString("MENUID"));
				menuVo.setMenunm(rs.getString("MENUNM"));
				list.add(menuVo);
			}
		}
		
		return list;
	}
	/**
	 * <pre>
	 * ====================================================================================
	 * 메뉴 상세 조회
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param upmenuid
	 * @param menuid
	 * @throws SQLException
	 */	
	public AdminMenuDetailBean sqlSelectMenuDetail(String upmenuid, String menuid) throws SQLException{
		AdminMenuDetailBean menuVo = new AdminMenuDetailBean();
		
		String sql = DcAdminMenuSQL.SELECT_MENU_DETAIL;
		SQLManagement sqlmng = new SQLManagement(sql);
		ResultSet rs = null;
		
		sqlmng.addValueReplacement("UPMENUID", upmenuid);
		sqlmng.addValueReplacement("MENUID", menuid);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}		
		rs = dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		
		if (rs.next()){
			menuVo.setUpmenuid(rs.getString("UPMENUID"));
			menuVo.setMenuid(rs.getString("MENUID"));
			menuVo.setMenunm(rs.getString("MENUNM"));
			menuVo.setUrl(rs.getString("URL"));
			menuVo.setSort(rs.getString("SORT"));
		}
		
		return menuVo;
	}
	/**
	 * <pre>
	 * ====================================================================================
	 * 메뉴저장
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param Map
	 * @throws SQLException
	 */		
	public void sqlSaveMenu(Map<String, Object> map, String saveState) throws SQLException{
		
		String sql = DcAdminMenuSQL.INSERT_MENU;
		if("U".equals(saveState)){
			sql = DcAdminMenuSQL.UPDATE_MENU;
		}
		
		SQLManagement sqlmng = new SQLManagement(sql);
		for(String key : map.keySet()){
			sqlmng.addValueReplacement(key, String.valueOf(map.get(key)));
		}
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}
		dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		
	}
	/**
	 * <pre>
	 * ====================================================================================
	 * 메뉴삭제
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param menuid
	 * @throws SQLException
	 */		
	public void sqlDeleteMenu(String menuid) throws SQLException{
		String sql = DcAdminMenuSQL.DELETE_MENU;
		SQLManagement sqlmng = new SQLManagement(sql);
		sqlmng.addValueReplacement("MENUID", menuid);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}
		dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
	}
	/**
	 * <pre>
	 * ====================================================================================
	 * 메뉴-롤 맵핑 삭제
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param menuid
	 * @throws SQLException
	 */	
	public void sqlDeleteMenuMapByMenu(String menuid) throws SQLException{
		String sql = DcAdminMenuSQL.DELETE_MENU_MAP_BY_MENU;
		SQLManagement sqlmng = new SQLManagement(sql);
		sqlmng.addValueReplacement("MENUID", menuid);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}
		dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		
	}
	/**
	 * <pre>
	 * ====================================================================================
	 * 메뉴-롤 맵핑 리스트 조회
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param menuid
	 * @throws SQLException
	 */	
	public List<AdminMenuRoleBean> sqlSelectMenuMappedRolesList(String menuid) throws SQLException {
		
		List<AdminMenuRoleBean> list = new ArrayList<AdminMenuRoleBean>();
		
		String sql = DcAdminMenuSQL.SELECT_MENU_MAPPED_ROLE_LIST;
		SQLManagement sqlmng = new SQLManagement(sql);
		ResultSet rs = null;
		
		sqlmng.addValueReplacement("MENUID", menuid);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}
		
		rs = dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		
		if (rs != null) {
			while(rs.next()){
				AdminMenuRoleBean menuVo = new AdminMenuRoleBean();
				menuVo.setRolecd(rs.getString("ROLECD"));
				menuVo.setOriginrolecd(rs.getString("ROLECD"));
				list.add(menuVo);
			}
		}
		
		return list;
	}
	/**
	 * <pre>
	 * ====================================================================================
	 * 메뉴-롤 맵핑 입력
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param menuid
	 * @param rolecd
	 * @throws SQLException
	 */	
	public void sqlInsertMenuMap(String menuid, String rolecd) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcAdminMenuSQL.INSERT_MENU_MAP);
		sqlmng.addValueReplacement("MENUID", menuid);
		sqlmng.addValueReplacement("ROLECD", rolecd);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
	}
	/**
	 * <pre>
	 * ====================================================================================
	 * 메뉴-롤 맵핑 수정
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param menuid
	 * @param rolecd
	 * @throws SQLException
	 */
	public void sqlUpdateMenuMap(String menuid, String rolecd, String originrolecd) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcAdminMenuSQL.UPDATE_MENU_MAP);
		sqlmng.addValueReplacement("MENUID", menuid);
		sqlmng.addValueReplacement("ROLECD", rolecd);
		sqlmng.addValueReplacement("ORIGINROLECD", originrolecd);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
	}
	/**
	 * <pre>
	 * ====================================================================================
	 * 메뉴-롤 맵핑 삭제
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param menuid
	 * @param rolecd
	 * @throws SQLException
	 */	
	public void sqlDeleteMenuMap(String menuid, String rolecd) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcAdminMenuSQL.DELETE_MENU_MAP);
		sqlmng.addValueReplacement("MENUID", menuid);
		sqlmng.addValueReplacement("ROLECD", rolecd);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}
		dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
	}
}
