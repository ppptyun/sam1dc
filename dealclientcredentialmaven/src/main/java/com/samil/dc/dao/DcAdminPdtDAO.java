package com.samil.dc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.samil.dc.access.DBConnection;
import com.samil.dc.domain.DcAdminPdtCategoryListBean;
import com.samil.dc.domain.DcAdminPdtListBean;
import com.samil.dc.sql.DcAdminPdtSQL;
import com.samil.dc.sql.SQLManagement;

public class DcAdminPdtDAO {

	private static final Logger logger = Logger.getRootLogger();
	private DBConnection dbcon = null;

	public DcAdminPdtDAO(DBConnection _dbcon) {
		dbcon = _dbcon;
	}

	/**
	 * <pre>
	 * ====================================================================================
	 * PDT 참조년도 목록 조회
	 * ====================================================================================
	 * </pre>
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<String> sqlSelectPdtRefyearList() throws SQLException {
		List<String> list = new ArrayList<String>();

		SQLManagement sqlmng = new SQLManagement(DcAdminPdtSQL.SELECT_PDT_REFYEARLY_LIST);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		ResultSet rs = null;
		rs = dbcon.executeQuery(sqlmng.getSql());
		if (rs != null) {
			while (rs.next()) {
				String refyearly = StringUtils.defaultIfBlank(rs.getString("REFYEARLY"), "");
				list.add(refyearly);
			}
		}

		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * PDT 카테고리 목록 조회
	 * ====================================================================================
	 * </pre>
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<DcAdminPdtCategoryListBean> sqlSelectPdtCategoryList(String refyearly, String useyn_a, String useyn_b) throws SQLException {
		List<DcAdminPdtCategoryListBean> list = new ArrayList<DcAdminPdtCategoryListBean>();

		SQLManagement sqlmng = new SQLManagement(DcAdminPdtSQL.SELECT_CATEGORY_LIST);
		sqlmng.addValueReplacement("REFYEARLY", refyearly);
		sqlmng.addValueReplacement("USEYN_A", useyn_a);
		sqlmng.addValueReplacement("USEYN_B", useyn_b);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		ResultSet rs = null;
		rs = dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs != null) {
			while (rs.next()) {
				DcAdminPdtCategoryListBean item = new DcAdminPdtCategoryListBean();
				item.setRefyearly(StringUtils.defaultIfBlank(rs.getString("REFYEARLY"), ""));
				item.setCtgcd(StringUtils.defaultIfBlank(rs.getString("CTGCD"), ""));
				item.setCtgnm(StringUtils.defaultIfBlank(rs.getString("CTGNM"), ""));
				item.setUseyn(StringUtils.defaultIfBlank(rs.getString("USEYN"), ""));
				list.add(item);
			}
		}

		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * PDT 등록된 카테고리를 제외한 시스템 카테고리 목록 조회
	 * ====================================================================================
	 * </pre>
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<DcAdminPdtCategoryListBean> sqlSelectPdtSystemCategoryList(String refyearly) throws SQLException {
		List<DcAdminPdtCategoryListBean> list = new ArrayList<DcAdminPdtCategoryListBean>();

		SQLManagement sqlmng = new SQLManagement(DcAdminPdtSQL.SELECT_SYSTEM_CATEGORY_LIST);
		sqlmng.addValueReplacement("REFYEARLY", refyearly);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		ResultSet rs = null;
		rs = dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs != null) {
			while (rs.next()) {
				DcAdminPdtCategoryListBean item = new DcAdminPdtCategoryListBean();
				item.setRefyearly(StringUtils.defaultIfBlank(rs.getString("REFYEARLY"), ""));
				item.setCtgcd(StringUtils.defaultIfBlank(rs.getString("CTGCD"), ""));
				item.setCtgnm(StringUtils.defaultIfBlank(rs.getString("CTGNM"), ""));
				item.setUseyn("");
				list.add(item);
			}
		}

		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * PDT SYSTEM 추가 가능 목록 조회
	 * ====================================================================================
	 * </pre>
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<DcAdminPdtListBean> sqlSelectPdtSystemAddableList(String refyearly) throws SQLException {
		List<DcAdminPdtListBean> list = new ArrayList<DcAdminPdtListBean>();

		SQLManagement sqlmng = new SQLManagement(DcAdminPdtSQL.SELECT_SYSTEM_PDT_ADDABLE_LIST);
		sqlmng.addValueReplacement("REFYEARLY", refyearly);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		ResultSet rs = null;
		rs = dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs != null) {
			while (rs.next()) {
				DcAdminPdtListBean pdt = new DcAdminPdtListBean();
				pdt.setRefyearly(StringUtils.defaultIfBlank(rs.getString("REFYEARLY"), ""));
				pdt.setCtgcd(StringUtils.defaultIfBlank(rs.getString("CTGCD"), ""));
				pdt.setCtgnm(StringUtils.defaultIfBlank(rs.getString("CTGNM"), ""));
				pdt.setPdtcd(StringUtils.defaultIfBlank(rs.getString("PDTCD"), ""));
				pdt.setPdtnm(StringUtils.defaultIfBlank(rs.getString("PDTNM"), ""));
				pdt.setComnm(StringUtils.defaultIfBlank(rs.getString("COMNM"), ""));
				list.add(pdt);
			}
		}

		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * PDT 목록 조회
	 * ====================================================================================
	 * </pre>
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<DcAdminPdtListBean> sqlSelectPdtList(String refyearly) throws SQLException {
		List<DcAdminPdtListBean> list = new ArrayList<DcAdminPdtListBean>();

		SQLManagement sqlmng = new SQLManagement(DcAdminPdtSQL.SELECT_PDT_LIST);
		sqlmng.addValueReplacement("REFYEARLY", refyearly);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		ResultSet rs = null;
		rs = dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs != null) {
			while (rs.next()) {
				DcAdminPdtListBean pdt = new DcAdminPdtListBean();
				pdt.setRefyearly(StringUtils.defaultIfBlank(rs.getString("REFYEARLY"), ""));
				pdt.setCtgcd(StringUtils.defaultIfBlank(rs.getString("CTGCD"), ""));
				pdt.setCtgnm(StringUtils.defaultIfBlank(rs.getString("CTGNM"), ""));
				pdt.setPdtcd(StringUtils.defaultIfBlank(rs.getString("PDTCD"), ""));
				pdt.setPdtnm(StringUtils.defaultIfBlank(rs.getString("PDTNM"), ""));
				pdt.setComnm(StringUtils.defaultIfBlank(rs.getString("COMNM"), ""));
				pdt.setCredcd(StringUtils.defaultIfBlank(rs.getString("CREDCD"), ""));
				pdt.setCrednm(StringUtils.defaultIfBlank(rs.getString("CREDNM"), ""));
				pdt.setConscd1(StringUtils.defaultIfBlank(rs.getString("CONSCD1"), ""));
				pdt.setConsnm1(StringUtils.defaultIfBlank(rs.getString("CONSNM1"), ""));
				pdt.setConscd2(StringUtils.defaultIfBlank(rs.getString("CONSCD2"), ""));
				pdt.setConsnm2(StringUtils.defaultIfBlank(rs.getString("CONSNM2"), ""));
				pdt.setConscd3(StringUtils.defaultIfBlank(rs.getString("CONSCD3"), ""));
				pdt.setConsnm3(StringUtils.defaultIfBlank(rs.getString("CONSNM3"), ""));
				pdt.setSamactcd(StringUtils.defaultIfBlank(rs.getString("SAMACTCD"), ""));
				pdt.setSamactnm(StringUtils.defaultIfBlank(rs.getString("SAMACTNM"), ""));
				pdt.setSambizcd(StringUtils.defaultIfBlank(rs.getString("SAMBIZCD"), ""));
				pdt.setSambiznm(StringUtils.defaultIfBlank(rs.getString("SAMBIZNM"), ""));
				pdt.setUseyn(StringUtils.defaultIfBlank(rs.getString("USEYN"), ""));

				list.add(pdt);
			}
		}

		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * PDT 복사해서 만들 참조년도 조회
	 * ====================================================================================
	 * </pre>
	 * 
	 * @return
	 * @throws SQLException
	 */
	public String sqlSelectNextRefyearly() throws SQLException {
		String refyearly = "";

		SQLManagement sqlmng = new SQLManagement(DcAdminPdtSQL.SELECT_NEXT_REFYEARLY);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		ResultSet rs = null;
		rs = dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs != null) {
			while (rs.next()) {
				refyearly = StringUtils.defaultIfBlank(rs.getString("REFYEARLY"), "");
			}
		}

		return refyearly;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 카테고리 저장
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param category
	 * @param empno
	 * @throws SQLException
	 */
	public void sqlMergeCategory(DcAdminPdtCategoryListBean category, String empno) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcAdminPdtSQL.MERGE_CAGEGORY);
		sqlmng.addValueReplacement("REFYEARLY", category.getRefyearly());
		sqlmng.addValueReplacement("CTGCD", category.getCtgcd());
		sqlmng.addValueReplacement("CTGNM", category.getCtgnm());
		sqlmng.addValueReplacement("EMPNO", empno);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}
		
		dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * PDT 저장
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param category
	 * @param empno
	 * @throws SQLException
	 */
	public void sqlMergePdt(DcAdminPdtListBean pdt, String empno) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcAdminPdtSQL.MERGE_PDT);
		sqlmng.addValueReplacement("REFYEARLY", pdt.getRefyearly());
		sqlmng.addValueReplacement("CTGCD", pdt.getCtgcd());
		sqlmng.addValueReplacement("PDTCD", pdt.getPdtcd());
		sqlmng.addValueReplacement("PDTNM", pdt.getPdtnm());
		sqlmng.addValueReplacement("COMNM", pdt.getComnm());
		sqlmng.addValueReplacement("CREDCD", pdt.getCredcd());
		sqlmng.addValueReplacement("CONSCD1", pdt.getConscd1());
		sqlmng.addValueReplacement("CONSCD2", pdt.getConscd2());
		sqlmng.addValueReplacement("CONSCD3", pdt.getConscd3());
		sqlmng.addValueReplacement("SAMACTCD", pdt.getSamactcd());
		sqlmng.addValueReplacement("SAMBIZCD", pdt.getSambizcd());
		sqlmng.addValueReplacement("USEYN", pdt.getUseyn());
		sqlmng.addValueReplacement("EMPNO", empno);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * PDT 카테고리 테이블 복사하기
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param refyearly
	 * @param nrefyearly
	 * @param creempno
	 * @throws SQLException
	 */
	public void sqlCopyCategoryList(String refyearly, String nrefyearly, String creempno) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcAdminPdtSQL.COPY_CATEGORY_LIST);
		sqlmng.addValueReplacement("REFYEARLY", refyearly);
		sqlmng.addValueReplacement("NREFYEARLY", nrefyearly);
		sqlmng.addValueReplacement("CREEMPNO", creempno);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}
		
		dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * PDT 테이블 복사하기
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param refyearly
	 * @param nrefyearly
	 * @param creempno
	 * @throws SQLException
	 */
	public void sqlCopyPdtList(String refyearly, String nrefyearly, String creempno) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcAdminPdtSQL.COPY_PDT_LIST);
		sqlmng.addValueReplacement("REFYEARLY", refyearly);
		sqlmng.addValueReplacement("NREFYEARLY", nrefyearly);
		sqlmng.addValueReplacement("CREEMPNO", creempno);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}
		
		dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}

	/**
	 * <pre>
	 * ====================================================================================
	 * PDTCTG 참조년도 삭제
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param year
	 * @throws SQLException
	 */
	public int sqlDeletePdtctg(String year) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcAdminPdtSQL.DELETE_PDTCTG);
		sqlmng.addValueReplacement("YEAR", year);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}
		
		return dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * PDT 참조년도 삭제
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param year
	 * @throws SQLException
	 */
	public int sqlDeletePdt(String year) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcAdminPdtSQL.DELETE_PDT);
		sqlmng.addValueReplacement("YEAR", year);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}
		
		return dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}
	
}
