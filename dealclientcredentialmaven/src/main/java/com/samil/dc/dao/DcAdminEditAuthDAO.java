package com.samil.dc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.samil.dc.access.DBConnection;
import com.samil.dc.domain.AdminEditAuthListBean;
import com.samil.dc.sql.DcAdminEditAuthSQL;
import com.samil.dc.sql.SQLManagement;

public class DcAdminEditAuthDAO {

	private static final Logger logger = Logger.getRootLogger();
	private DBConnection dbcon = null;

	public DcAdminEditAuthDAO(DBConnection _dbcon) {
		dbcon = _dbcon;
	}

	/**
	 * <pre>
	 * ====================================================================================
	 * League Table 편집 관리 목록 조회
	 * ====================================================================================
	 * </pre>
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<AdminEditAuthListBean> sqlSelectEditAuthList() throws SQLException {
		List<AdminEditAuthListBean> list = new ArrayList<AdminEditAuthListBean>();

		SQLManagement sqlmng = new SQLManagement(DcAdminEditAuthSQL.SELECT_EDIT_AUTH_LIST);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		ResultSet rs = null;
		rs = dbcon.executeQuery(sqlmng.getSql());
		if (rs != null) {
			while (rs.next()) {
				AdminEditAuthListBean item = new AdminEditAuthListBean();
				item.setRolecd(StringUtils.defaultIfBlank(rs.getString("ROLECD"), ""));
				item.setRolenm(StringUtils.defaultIfBlank(rs.getString("ROLENM"), ""));
				item.setEdityn(StringUtils.defaultIfBlank(rs.getString("EDITYN"), ""));
				item.setUpddt(StringUtils.defaultIfBlank(rs.getString("UPDDT"), ""));
				list.add(item);
			}
		}

		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table 편집 관리 조회
	 * ====================================================================================
	 * </pre>
	 * 
	 * @return
	 * @throws SQLException
	 */
	public AdminEditAuthListBean sqlSelectEditAuthDetail(String rolecd) throws SQLException {
		AdminEditAuthListBean bean = null;

		SQLManagement sqlmng = new SQLManagement(DcAdminEditAuthSQL.SELECT_EDIT_AUTH_DETAIL);
		sqlmng.addValueReplacement("ROLECD", rolecd);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		ResultSet rs = null;
		rs = dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs != null) {
			while (rs.next()) {
				bean = new AdminEditAuthListBean();
				bean.setRolecd(StringUtils.defaultIfBlank(rs.getString("ROLECD"), ""));
				bean.setRolenm(StringUtils.defaultIfBlank(rs.getString("ROLENM"), ""));
				bean.setEdityn(StringUtils.defaultIfBlank(rs.getString("EDITYN"), ""));
				bean.setUpddt(StringUtils.defaultIfBlank(rs.getString("UPDDT"), ""));
			}
		}

		return bean;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table 편집 관리 등록
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param rolecd
	 * @return
	 * @throws SQLException
	 */
	public int sqlInsertEditAuth(String rolecd, String edityn) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcAdminEditAuthSQL.INSERT_EDIT_AUTH);
		sqlmng.addValueReplacement("ROLECD", rolecd);
		sqlmng.addValueReplacement("EDITYN", edityn);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}
		
		return dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table 편집 관리 수정
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param rolecd
	 * @return
	 * @throws SQLException
	 */
	public int sqlUpdateEditAuth(String originrolecd, String rolecd, String edityn) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcAdminEditAuthSQL.UPDATE_EDIT_AUTH);
		sqlmng.addValueReplacement("ORIGINROLECD", originrolecd);
		sqlmng.addValueReplacement("ROLECD", rolecd);
		sqlmng.addValueReplacement("EDITYN", edityn);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}
		
		return dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table 편집 관리 삭제
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param rolecd
	 * @return
	 * @throws SQLException
	 */
	public int sqlDeleteEditAuth(String rolecd) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcAdminEditAuthSQL.DELETE_EDIT_AUTH);
		sqlmng.addValueReplacement("ROLECD", rolecd);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}
		
		return dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}
}
