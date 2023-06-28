package com.samil.dc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.samil.dc.access.DBConnection;
import com.samil.dc.domain.AdminRoleListBean;
import com.samil.dc.domain.AdminRoleMemberListBean;
import com.samil.dc.sql.DcAdminRoleSQL;
import com.samil.dc.sql.SQLManagement;

public class DcAdminRoleDAO {

	private static final Logger logger = Logger.getRootLogger();
	private DBConnection dbcon = null;

	public DcAdminRoleDAO(DBConnection _dbcon) {
		dbcon = _dbcon;
	}

	/**
	 * <pre>
	 * ====================================================================================
	 * 롤 목록 조회
	 * ====================================================================================
	 * </pre>
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<AdminRoleListBean> sqlSelectRoleList() throws SQLException {
		List<AdminRoleListBean> list = new ArrayList<AdminRoleListBean>();

		SQLManagement sqlmng = new SQLManagement(DcAdminRoleSQL.SELECT_ROLE_LIST);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		ResultSet rs = null;
		rs = dbcon.executeQuery(sqlmng.getSql());
		if (rs != null) {
			while (rs.next()) {
				AdminRoleListBean role = new AdminRoleListBean();
				role.setOrirolecd(StringUtils.defaultIfBlank(rs.getString("ROLECD"), ""));
				role.setRolecd(StringUtils.defaultIfBlank(rs.getString("ROLECD"), ""));
				role.setRolenm(StringUtils.defaultIfBlank(rs.getString("ROLENM"), ""));
				role.setSort(StringUtils.defaultIfBlank(rs.getString("SORT"), ""));
				list.add(role);
			}
		}

		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 롤 멤버 목록 조회
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param roleCd
	 * @return
	 * @throws SQLException
	 */
	public List<AdminRoleMemberListBean> sqlSelectRoleMemberList(String roleCd) throws SQLException {
		List<AdminRoleMemberListBean> list = new ArrayList<AdminRoleMemberListBean>();

		SQLManagement sqlmng = new SQLManagement(DcAdminRoleSQL.SELECT_ROLE_MEMBER_LIST);
		sqlmng.addValueReplacement("ROLECD", roleCd);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		ResultSet rs = null;
		rs = dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs != null) {
			while (rs.next()) {
				AdminRoleMemberListBean member = new AdminRoleMemberListBean();
				member.setRolecd(StringUtils.defaultIfBlank(rs.getString("ROLECD"), ""));
				member.setEmpno(StringUtils.defaultIfBlank(rs.getString("EMPNO"), ""));
				member.setEmpnm(StringUtils.defaultIfBlank(rs.getString("EMPNM"), ""));
				member.setTeamcd(StringUtils.defaultIfBlank(rs.getString("TEAMCD"), ""));
				member.setTeamnm(StringUtils.defaultIfBlank(rs.getString("TEAMNM"), ""));
				member.setGradcd(StringUtils.defaultIfBlank(rs.getString("GRADCD"), ""));
				member.setGradnm(StringUtils.defaultIfBlank(rs.getString("GRADNM"), ""));
				list.add(member);
			}
		}

		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 롤 신규 등록
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param roleCd
	 * @param roleNm
	 * @param sort
	 * @throws SQLException
	 */
	public void sqlInsertRole(String roleCd, String roleNm, Integer sort) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcAdminRoleSQL.INSERT_ROLE);
		sqlmng.addValueReplacement("ROLECD", roleCd);
		sqlmng.addValueReplacement("ROLENM", roleNm);
		sqlmng.addValueReplacement("SORT", sort);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}
		
		dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 롤 수정
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param roleCd
	 * @param roleNm
	 * @param sort
	 * @param oriRoleCd
	 * @throws SQLException
	 */
	public void sqlUpdateRole(String roleCd, String roleNm, Integer sort, String oriRoleCd) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcAdminRoleSQL.UPDATE_ROLE);
		sqlmng.addValueReplacement("ORIROLECD", oriRoleCd);
		sqlmng.addValueReplacement("ROLECD", roleCd);
		sqlmng.addValueReplacement("ROLENM", roleNm);
		sqlmng.addValueReplacement("SORT", sort);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}
		
		dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 롤 삭제
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param roleCd
	 * @throws SQLException
	 */
	public void sqlDeleteRole(String roleCd) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcAdminRoleSQL.DELETE_ROLE);
		sqlmng.addValueReplacement("ROLECD", roleCd);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}
		
		dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 롤 멤버 신규 등록
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param roleCd
	 * @param empNo
	 * @throws SQLException
	 */
	public void sqlInsertRoleMember(String roleCd, String empNo) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcAdminRoleSQL.INSERT_ROLE_MEMBER);
		sqlmng.addValueReplacement("ROLECD", roleCd);
		sqlmng.addValueReplacement("EMPNO", empNo);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}
		
		dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 롤 멤버 삭제
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param roleCd
	 * @throws SQLException
	 */
	public void sqlDeleteRoleMemberByRole(String roleCd) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcAdminRoleSQL.DELETE_ROLE_MEMBER_BY_ROLE);
		sqlmng.addValueReplacement("ROLECD", roleCd);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}
		
		dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 롤 메뉴 삭제
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param roleCd
	 * @throws SQLException
	 */
	public void sqlDeleteRoleMenuByRole(String roleCd) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcAdminRoleSQL.DELETE_ROLE_MENU_BY_ROLE);
		sqlmng.addValueReplacement("ROLECD", roleCd);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}
		
		dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 롤 필드 삭제
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param roleCd
	 * @throws SQLException
	 */
	public void sqlDeleteRoleFieldByRole(String roleCd) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcAdminRoleSQL.DELETE_ROLE_FIELD_BY_ROLE);
		sqlmng.addValueReplacement("ROLECD", roleCd);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}
		
		dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
	}	
	
	
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 롤 멤버 삭제
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param roleCd
	 * @param empNo
	 * @throws SQLException
	 */
	public void sqlDeleteRoleMemberByEmp(String roleCd, String empNo) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcAdminRoleSQL.DELETE_ROLE_MEMBER_BY_EMP);
		sqlmng.addValueReplacement("ROLECD", roleCd);
		sqlmng.addValueReplacement("EMPNO", empNo);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}
		
		dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
	}
}
