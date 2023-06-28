package com.samil.dc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.samil.dc.access.DBConnection;
import com.samil.dc.domain.AdminPEListBean;
import com.samil.dc.sql.DcAdminPESQL;
import com.samil.dc.sql.SQLManagement;

public class DcAdminPEDAO {

	private static final Logger logger = Logger.getRootLogger();
	private DBConnection dbcon = null;

	public DcAdminPEDAO(DBConnection _dbcon) {
		dbcon = _dbcon;
	}

	/**
	 * <pre>
	 * ====================================================================================
	 * PE 관리 목록 조회
	 * ====================================================================================
	 * </pre>
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<AdminPEListBean> sqlSelectPEList() throws SQLException {
		List<AdminPEListBean> list = new ArrayList<AdminPEListBean>();

		SQLManagement sqlmng = new SQLManagement(DcAdminPESQL.SELECT_PE_LIST);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		ResultSet rs = null;
		rs = dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs != null) {
			while (rs.next()) {
				AdminPEListBean item = new AdminPEListBean();
				item.setGlinducd(StringUtils.defaultIfBlank(rs.getString("GLINDUCD"), ""));
				item.setGlindunmk(StringUtils.defaultIfBlank(rs.getString("GLINDUNMK"), ""));
				item.setGlindunm(StringUtils.defaultIfBlank(rs.getString("GLINDUNM"), ""));
				item.setInducd(StringUtils.defaultIfBlank(rs.getString("INDUCD"), ""));
				item.setIndunm(StringUtils.defaultIfBlank(rs.getString("INDUNM"), ""));
				item.setIndunme(StringUtils.defaultIfBlank(rs.getString("INDUNME"), ""));
				item.setChkval(StringUtils.defaultIfBlank(rs.getString("CHKVAL"), ""));
				list.add(item);
			}
		}

		return list;
	}

	/**
	 * <pre>
	 * ====================================================================================
	 * PE 관리 목록 등록
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param inducd
	 * @param creempno
	 * @return
	 * @throws SQLException
	 */
	public int sqlInsertPE(String inducd, String creempno) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcAdminPESQL.INSERT_PE);
		sqlmng.addValueReplacement("INDUCD", inducd);
		sqlmng.addValueReplacement("CREEMPNO", creempno);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		return dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}

	/**
	 * <pre>
	 * ====================================================================================
	 * PE 관리 목록 삭제
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param inducd
	 * @return
	 * @throws SQLException
	 */
	public int sqlDeletePE(String inducd) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcAdminPESQL.DELETE_PE);
		sqlmng.addValueReplacement("INDUCD", inducd);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		return dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}
}
