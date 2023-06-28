package com.samil.dc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.samil.dc.access.DBConnection;
import com.samil.dc.domain.AdminConsultListBean;
import com.samil.dc.sql.DcAdminConsultSQL;
import com.samil.dc.sql.SQLManagement;

public class DcAdminConsultDAO {

	private static final Logger logger = Logger.getRootLogger();
	private DBConnection dbcon = null;

	public DcAdminConsultDAO(DBConnection _dbcon) {
		dbcon = _dbcon;
	}

	/**
	 * <pre>
	 * ====================================================================================
	 * 자문사 관리 목록 조회
	 * ====================================================================================
	 * </pre>
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<AdminConsultListBean> sqlSelectConsultList() throws SQLException {
		List<AdminConsultListBean> list = new ArrayList<AdminConsultListBean>();

		SQLManagement sqlmng = new SQLManagement(DcAdminConsultSQL.SELECT_CONSULT_LIST);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		ResultSet rs = null;
		rs = dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs != null) {
			while (rs.next()) {
				AdminConsultListBean item = new AdminConsultListBean();
				item.setCompcd(StringUtils.defaultIfBlank(rs.getString("COMPCD"), ""));
				item.setComphannm(StringUtils.defaultIfBlank(rs.getString("COMPHANNM"), ""));
				item.setCompengnm(StringUtils.defaultIfBlank(rs.getString("COMPENGNM"), ""));
				item.setFinanceyn(StringUtils.defaultIfBlank(rs.getString("FINANCEYN"), ""));
				item.setAudityn(StringUtils.defaultIfBlank(rs.getString("AUDITYN"), ""));
				item.setLawyn(StringUtils.defaultIfBlank(rs.getString("LAWYN"), ""));
				item.setUseyn(StringUtils.defaultIfBlank(rs.getString("USEYN"), ""));
				list.add(item);
			}
		}

		return list;
	}

	/**
	 * <pre>
	 * ====================================================================================
	 * 자문사 관리 목록 저장 (Update)
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param list
	 * @throws SQLException
	 */
	public void sqlUpdateConsult(AdminConsultListBean biz) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcAdminConsultSQL.UPDATE_CONSULT);
		sqlmng.addValueReplacement("COMPCD", biz.getCompcd());
		sqlmng.addValueReplacement("COMPHANNM", biz.getComphannm());
		sqlmng.addValueReplacement("COMPENGNM", biz.getCompengnm());
		sqlmng.addValueReplacement("FINANCEYN", "0".equals(biz.getFinanceyn()) ? "N" : "Y");
		sqlmng.addValueReplacement("AUDITYN", "0".equals(biz.getAudityn()) ? "N" : "Y");
		sqlmng.addValueReplacement("LAWYN", "0".equals(biz.getLawyn()) ? "N" : "Y");
		sqlmng.addValueReplacement("USEYN", "0".equals(biz.getUseyn()) ? "N" : "Y");
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}

	/**
	 * <pre>
	 * ====================================================================================
	 * 자문사 관리 목록 저장 (Insert)
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param list
	 * @throws SQLException
	 */
	public void sqlInsertConsult(AdminConsultListBean biz, String creempno) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcAdminConsultSQL.INSERT_CONSULT);
		sqlmng.addValueReplacement("COMPHANNM", biz.getComphannm());
		sqlmng.addValueReplacement("COMPENGNM", biz.getCompengnm());
		sqlmng.addValueReplacement("CREEMPNO", creempno);
		sqlmng.addValueReplacement("FINANCEYN", "0".equals(biz.getFinanceyn()) ? "N" : "Y");
		sqlmng.addValueReplacement("AUDITYN", "0".equals(biz.getAudityn()) ? "N" : "Y");
		sqlmng.addValueReplacement("LAWYN", "0".equals(biz.getLawyn()) ? "N" : "Y");
		sqlmng.addValueReplacement("USEYN", "0".equals(biz.getUseyn()) ? "N" : "Y");
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}

	/**
	 * <pre>
	 * ====================================================================================
	 * 자문사 관리 목록 삭제 (사용안함처리)
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param list
	 * @throws SQLException
	 */
	public int sqlDeleteConsult(String compcd) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcAdminConsultSQL.DELETE_CONSULT);
		sqlmng.addValueReplacement("COMPCD", compcd);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}
		
		return dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}

}
