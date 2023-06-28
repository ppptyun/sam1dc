package com.samil.dc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.samil.dc.access.DBConnection;
import com.samil.dc.domain.AdminHQEmpListBean;
import com.samil.dc.sql.DcAdminHQEmpSQL;
import com.samil.dc.sql.SQLManagement;

public class DcAdminHQEmpDAO {

	private static final Logger logger = Logger.getRootLogger();
	private DBConnection dbcon = null;

	public DcAdminHQEmpDAO(DBConnection _dbcon) {
		dbcon = _dbcon;
	}

	/**
	 * <pre>
	 * ====================================================================================
	 * 본부 담당자 목록 조회
	 * ====================================================================================
	 * </pre>
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<AdminHQEmpListBean> sqlSelectHQEmpList() throws SQLException {
		final String ETC_MANAGE_HQCD = "999";
		List<AdminHQEmpListBean> list = new ArrayList<AdminHQEmpListBean>();

		SQLManagement sqlmng = new SQLManagement(DcAdminHQEmpSQL.SELECT_HQ_EMP_LIST);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		ResultSet rs = null;
		rs = dbcon.executeQuery(sqlmng.getSql());
		if (rs != null) {
			while (rs.next()) {
				AdminHQEmpListBean hqemp = new AdminHQEmpListBean();
				String hqcd = StringUtils.defaultIfBlank(rs.getString("HQCD"), "");
				String hqnm = StringUtils.defaultIfBlank(rs.getString("HQNM"), "");
				hqemp.setHqcd(hqcd);
				hqemp.setHqnm(hqnm);
				hqemp.setGrpnm(StringUtils.defaultIfBlank(rs.getString("GRPNM"), ""));
				if (ETC_MANAGE_HQCD.equals(hqcd)) {
					hqemp.setGrpnm(hqnm);	
				}
				hqemp.setEmpno(StringUtils.defaultIfBlank(rs.getString("EMPNO"), ""));
				hqemp.setEmpnm(StringUtils.defaultIfBlank(rs.getString("EMPNM"), ""));
				hqemp.setGradnm(StringUtils.defaultIfBlank(rs.getString("GRADNM"), ""));
				list.add(hqemp);
			}
		}

		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * Deal 본부 목록 동기화(From View)
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param hqcd
	 * @param empno
	 * @param creempno
	 * @return
	 * @throws SQLException
	 */
	public int sqlMergetHQEmpSync(String creempno) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcAdminHQEmpSQL.MERGE_DEAL_HQ_SYNC_FROM_VIEW);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}
		
		return dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 본부 담당자 등록
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param hqcd
	 * @param empno
	 * @param creempno
	 * @return
	 * @throws SQLException
	 */
	public int sqlInsertHQEmp(String hqcd, String empno, String creempno) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcAdminHQEmpSQL.INSERT_HQ_EMP);
		sqlmng.addValueReplacement("HQCD", hqcd);
		sqlmng.addValueReplacement("EMPNO", empno);
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
	 * 본부 담당자 삭제
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param hqcd
	 * @param empno
	 * @return
	 * @throws SQLException
	 */
	public int sqlDeleteHQEmp(String hqcd, String empno) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcAdminHQEmpSQL.DELETE_HQ_EMP);
		sqlmng.addValueReplacement("HQCD", hqcd);
		sqlmng.addValueReplacement("EMPNO", empno);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}
		
		return dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}
}
