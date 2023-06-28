package com.samil.dc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.samil.dc.access.DBConnection;
import com.samil.dc.domain.AdminEditAuthListBean;
import com.samil.dc.domain.DcLtBizBean;
import com.samil.dc.domain.DcLtBuyBean;
import com.samil.dc.domain.DcLtBuyListBean;
import com.samil.dc.domain.DcLtDealListBean;
import com.samil.dc.domain.DcLtMnaBean;
import com.samil.dc.domain.DcLtMnaListBean;
import com.samil.dc.domain.DcLtProjectListBean;
import com.samil.dc.domain.DcLtRealBean;
import com.samil.dc.domain.DcLtRealListBean;
import com.samil.dc.domain.DcLtSetupConsultListBean;
import com.samil.dc.service.worker.ServiceHelper;
import com.samil.dc.sql.DcAdminEditAuthSQL;
import com.samil.dc.sql.DcLeagueTableSQL;
import com.samil.dc.sql.SQLManagement;
import com.samil.dc.util.Constants;

public class DcLeagueTableDAO {

	private static final Logger logger = Logger.getRootLogger();
	private DBConnection m_dbcon = null;

	public DcLeagueTableDAO(DBConnection _con) {
		m_dbcon = _con;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 자문 형태 지정 목록 조회
	 * ====================================================================================
	 * </pre>
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<DcLtSetupConsultListBean> sqlSelectSetupConsultTypeList(String sqlCondition, String key, String value) throws SQLException {
		List<DcLtSetupConsultListBean> list = new ArrayList<DcLtSetupConsultListBean>();
		SQLManagement sqlmng = new SQLManagement(DcLeagueTableSQL.SELECT_SETUP_CONSULT_TYPE_LIST);
		if(sqlCondition != null){
			sqlmng.addSubSQLReplacement("SQL_CONDITION", sqlCondition, key, value);	
		}
		sqlmng.generate(true);
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		ResultSet rs = m_dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs != null) {
			while (rs.next()) {
				DcLtSetupConsultListBean league = new DcLtSetupConsultListBean();
				league.setPrjtcd(StringUtils.defaultIfBlank(rs.getString("PRJTCD"), ""));
				league.setYearly(StringUtils.defaultIfBlank(rs.getString("YEARLY"), ""));
				league.setConscd(StringUtils.defaultIfBlank(rs.getString("CONSCD"), ""));
				league.setCtgcd(StringUtils.defaultIfBlank(rs.getString("CTGCD"), ""));
				league.setCtgnm(StringUtils.defaultIfBlank(rs.getString("CTGNM"), ""));
				league.setPdtcd(StringUtils.defaultIfBlank(rs.getString("PDTCD"), ""));
				league.setPdtnm(StringUtils.defaultIfBlank(rs.getString("PDTNM"), ""));
				league.setPrjtnm(StringUtils.defaultIfBlank(rs.getString("PRJTNM"), ""));
				String hqcd = StringUtils.defaultIfBlank(rs.getString("HQCD"), "");
				league.setHqcd(hqcd);
				league.setHqnm(hqcd + "/" + StringUtils.defaultIfBlank(rs.getString("HQNM"), ""));
				league.setPtrempno(StringUtils.defaultIfBlank(rs.getString("PTREMPNO"), ""));
				league.setPtrempnm(StringUtils.defaultIfBlank(rs.getString("PTREMPNM"), ""));
				league.setMgrempno(StringUtils.defaultIfBlank(rs.getString("MGREMPNO"), ""));
				league.setMgrempnm(StringUtils.defaultIfBlank(rs.getString("MGREMPNM"), ""));
				league.setChrempnm(StringUtils.defaultIfBlank(rs.getString("CHREMPNM"), ""));
				league.setUpddt(StringUtils.defaultIfBlank(rs.getString("UPDDT"), ""));
				list.add(league);
			}
		}

		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 인수/매각 자문 목록 조회
	 * ====================================================================================
	 * </pre>
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<DcLtBuyListBean> sqlSelectConsultBuyList(String sqlCondition, HashMap<String, Object> keyValueMap) throws SQLException {
		//----------- [2019.01.24 남웅주] -----------------------------------------------	
		//String _sqlCondition = sqlCondition == null ? "" : String.valueOf(sqlCondition);
		List<DcLtBuyListBean> list = new ArrayList<DcLtBuyListBean>();

		SQLManagement sqlmng = new SQLManagement(DcLeagueTableSQL.SELECT_CONSULT_BUY_LIST);

		if(sqlCondition != null){
			sqlmng.addSubSQLReplacement("SQL_CONDITION", sqlCondition, keyValueMap);	
		}

		sqlmng.generate(true);

		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
			logger.debug(sqlmng.getParameter());
		}

		ResultSet rs = m_dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs != null) {
			while (rs.next()) {
				DcLtBuyListBean item = new DcLtBuyListBean();
				item.setPrjtcd(StringUtils.defaultIfBlank(rs.getString("PRJTCD"), ""));
				item.setYearly(StringUtils.defaultIfBlank(rs.getString("YEARLY"), ""));
				item.setChrgconfcd(StringUtils.defaultIfBlank(rs.getString("CHRGCONFCD"), ""));
				item.setChrgconfdt(StringUtils.defaultIfBlank(rs.getString("CHRGCONFDT"), ""));
				
				String statuscd = StringUtils.defaultIfBlank(rs.getString("STATUSCD"), "");
				String etconfdt = StringUtils.defaultIfBlank(rs.getString("ETCONFDT"), "");
				item.setEtconfyn(ServiceHelper.isValidETConfdt(statuscd, etconfdt));
				item.setEtconfdt(etconfdt);
				item.setEtconfempnm(StringUtils.defaultIfBlank(rs.getString("ETCONFEMPNM"), ""));
				
				item.setConfnm(StringUtils.defaultIfBlank(rs.getString("CONFNM"), ""));
				item.setPrjtnm(StringUtils.defaultIfBlank(rs.getString("PRJTNM"), ""));
				String hqcd = StringUtils.defaultIfBlank(rs.getString("HQCD"), "");
				item.setHqcd(hqcd);
				item.setHqnm(hqcd + "/" + StringUtils.defaultIfBlank(rs.getString("HQNM"), ""));
				item.setPtrempno(StringUtils.defaultIfBlank(rs.getString("PTREMPNO"), ""));
				item.setPtrempnm(StringUtils.defaultIfBlank(rs.getString("PTREMPNM"), ""));
				item.setMgrempno(StringUtils.defaultIfBlank(rs.getString("MGREMPNO"), ""));
				item.setMgrempnm(StringUtils.defaultIfBlank(rs.getString("MGREMPNM"), ""));
				item.setChrempnm(StringUtils.defaultIfBlank(rs.getString("CHREMPNM"), ""));
				item.setStatusnm(StringUtils.defaultIfBlank(rs.getString("STATUSNM"), ""));
				item.setUpddt(StringUtils.defaultIfBlank(rs.getString("UPDDT"), ""));
				item.setTcomphannm(StringUtils.defaultIfBlank(rs.getString("TCOMPHANNM"), ""));
				item.setTindunm(StringUtils.defaultIfBlank(rs.getString("TINDUNM"), ""));
				item.setTdealnm(StringUtils.defaultIfBlank(rs.getString("TDEALNM"), ""));
				item.setScomphannm(StringUtils.defaultIfBlank(rs.getString("SCOMPHANNM"), ""));
				item.setSindunm(StringUtils.defaultIfBlank(rs.getString("SINDUNM"), ""));
				item.setBcomphannm(StringUtils.defaultIfBlank(rs.getString("BCOMPHANNM"), ""));
				item.setBindunm(StringUtils.defaultIfBlank(rs.getString("BINDUNM"), ""));
				item.setAmt(StringUtils.defaultIfBlank(rs.getString("AMT"), ""));

				list.add(item);
			}
		}

		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 합병 자문 목록 조회
	 * ====================================================================================
	 * </pre>
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<DcLtMnaListBean> sqlSelectConsultMnaList(String sqlCondition, String key, String value) throws SQLException {
		//----------- [2019.01.24 남웅주] -----------------------------------------------	
		//String _sqlCondition = sqlCondition == null ? "" : String.valueOf(sqlCondition);
		List<DcLtMnaListBean> list = new ArrayList<DcLtMnaListBean>();

		SQLManagement sqlmng = new SQLManagement(DcLeagueTableSQL.SELECT_CONSULT_MNA_LIST);
		if(sqlCondition != null){
			sqlmng.addSubSQLReplacement("SQL_CONDITION", sqlCondition, key, value);	
		}
		sqlmng.generate(true);
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		ResultSet rs = m_dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs != null) {
			while (rs.next()) {
				DcLtMnaListBean item = new DcLtMnaListBean();
				item.setPrjtcd(StringUtils.defaultIfBlank(rs.getString("PRJTCD"), ""));
				item.setYearly(StringUtils.defaultIfBlank(rs.getString("YEARLY"), ""));
				item.setChrgconfcd(StringUtils.defaultIfBlank(rs.getString("CHRGCONFCD"), ""));
				item.setChrgconfdt(StringUtils.defaultIfBlank(rs.getString("CHRGCONFDT"), ""));
				
				String statuscd = StringUtils.defaultIfBlank(rs.getString("STATUSCD"), "");
				String etconfdt = StringUtils.defaultIfBlank(rs.getString("ETCONFDT"), "");
				item.setEtconfyn(ServiceHelper.isValidETConfdt(statuscd, etconfdt));
				item.setEtconfdt(etconfdt);
				item.setEtconfempnm(StringUtils.defaultIfBlank(rs.getString("ETCONFEMPNM"), ""));
				
				item.setConfnm(StringUtils.defaultIfBlank(rs.getString("CONFNM"), ""));
				item.setPrjtnm(StringUtils.defaultIfBlank(rs.getString("PRJTNM"), ""));
				String hqcd = StringUtils.defaultIfBlank(rs.getString("HQCD"), "");
				item.setHqcd(hqcd);
				item.setHqnm(hqcd + "/" + StringUtils.defaultIfBlank(rs.getString("HQNM"), ""));
				item.setPtrempno(StringUtils.defaultIfBlank(rs.getString("PTREMPNO"), ""));
				item.setPtrempnm(StringUtils.defaultIfBlank(rs.getString("PTREMPNM"), ""));
				item.setMgrempno(StringUtils.defaultIfBlank(rs.getString("MGREMPNO"), ""));
				item.setMgrempnm(StringUtils.defaultIfBlank(rs.getString("MGREMPNM"), ""));
				item.setChrempnm(StringUtils.defaultIfBlank(rs.getString("CHREMPNM"), ""));
				item.setStatusnm(StringUtils.defaultIfBlank(rs.getString("STATUSNM"), ""));
				item.setUpddt(StringUtils.defaultIfBlank(rs.getString("UPDDT"), ""));
				item.setScomphannm(StringUtils.defaultIfBlank(rs.getString("SCOMPHANNM"), ""));
				item.setSindunm(StringUtils.defaultIfBlank(rs.getString("SINDUNM"), ""));
				item.setBcomphannm(StringUtils.defaultIfBlank(rs.getString("BCOMPHANNM"), ""));
				item.setBindunm(StringUtils.defaultIfBlank(rs.getString("BINDUNM"), ""));
				item.setAmt(StringUtils.defaultIfBlank(rs.getString("AMT"), ""));

				list.add(item);
			}
		}

		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 부동산 자문 목록 조회
	 * ====================================================================================
	 * </pre>
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<DcLtRealListBean> sqlSelectConsultRealList(String sqlCondition, String key, String value) throws SQLException {
		//----------- [2019.01.24 남웅주] -----------------------------------------------	
		//String _sqlCondition = sqlCondition == null ? "" : String.valueOf(sqlCondition);
		List<DcLtRealListBean> list = new ArrayList<DcLtRealListBean>();

		SQLManagement sqlmng = new SQLManagement(DcLeagueTableSQL.SELECT_CONSULT_REAL_LIST);
		if(sqlCondition != null){
			sqlmng.addSubSQLReplacement("SQL_CONDITION", sqlCondition, key, value);	
		}
		sqlmng.generate(true);
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		ResultSet rs = m_dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs != null) {
			while (rs.next()) {
				DcLtRealListBean item = new DcLtRealListBean();
				item.setPrjtcd(StringUtils.defaultIfBlank(rs.getString("PRJTCD"), ""));
				item.setYearly(StringUtils.defaultIfBlank(rs.getString("YEARLY"), ""));
				item.setChrgconfcd(StringUtils.defaultIfBlank(rs.getString("CHRGCONFCD"), ""));
				item.setChrgconfdt(StringUtils.defaultIfBlank(rs.getString("CHRGCONFDT"), ""));
				
				String statuscd = StringUtils.defaultIfBlank(rs.getString("STATUSCD"), "");
				String etconfdt = StringUtils.defaultIfBlank(rs.getString("ETCONFDT"), "");
				item.setEtconfyn(ServiceHelper.isValidETConfdt(statuscd, etconfdt));
				item.setEtconfdt(etconfdt);
				item.setEtconfempnm(StringUtils.defaultIfBlank(rs.getString("ETCONFEMPNM"), ""));
				
				item.setConfnm(StringUtils.defaultIfBlank(rs.getString("CONFNM"), ""));
				item.setPrjtnm(StringUtils.defaultIfBlank(rs.getString("PRJTNM"), ""));
				String hqcd = StringUtils.defaultIfBlank(rs.getString("HQCD"), "");
				item.setHqcd(hqcd);
				item.setHqnm(hqcd + "/" + StringUtils.defaultIfBlank(rs.getString("HQNM"), ""));
				item.setPtrempno(StringUtils.defaultIfBlank(rs.getString("PTREMPNO"), ""));
				item.setPtrempnm(StringUtils.defaultIfBlank(rs.getString("PTREMPNM"), ""));
				item.setMgrempno(StringUtils.defaultIfBlank(rs.getString("MGREMPNO"), ""));
				item.setMgrempnm(StringUtils.defaultIfBlank(rs.getString("MGREMPNM"), ""));
				item.setChrempnm(StringUtils.defaultIfBlank(rs.getString("CHREMPNM"), ""));
				item.setStatusnm(StringUtils.defaultIfBlank(rs.getString("STATUSNM"), ""));
				item.setUpddt(StringUtils.defaultIfBlank(rs.getString("UPDDT"), ""));
				item.setTdealnm(StringUtils.defaultIfBlank(rs.getString("TDEALNM"), ""));
				item.setScomphannm(StringUtils.defaultIfBlank(rs.getString("SCOMPHANNM"), ""));
				item.setSindunm(StringUtils.defaultIfBlank(rs.getString("SINDUNM"), ""));
				item.setBcomphannm(StringUtils.defaultIfBlank(rs.getString("BCOMPHANNM"), ""));
				item.setBindunm(StringUtils.defaultIfBlank(rs.getString("BINDUNM"), ""));
				item.setAmt(StringUtils.defaultIfBlank(rs.getString("AMT"), ""));

				list.add(item);
			}
		}

		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 대상 프로젝트 관리 목록 조회
	 * ====================================================================================
	 * </pre>
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<DcLtProjectListBean> sqlSelectLeagueProjectList(String sqlCondition, String key, String value) throws SQLException {
		//----------- [2019.01.24 남웅주] -----------------------------------------------	
		//String _sqlCondition = sqlCondition == null ? "" : String.valueOf(sqlCondition);
		List<DcLtProjectListBean> list = new ArrayList<DcLtProjectListBean>();

		SQLManagement sqlmng = new SQLManagement(DcLeagueTableSQL.SELECT_LEAGUE_PROJECT_LIST);
		if(sqlCondition != null){
			sqlmng.addSubSQLReplacement("SQL_CONDITION", sqlCondition, key, value);	
		}
		sqlmng.generate(true);
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		ResultSet rs = m_dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs != null) {
			while (rs.next()) {
				DcLtProjectListBean item = new DcLtProjectListBean();
				item.setPrjtcd(StringUtils.defaultIfBlank(rs.getString("PRJTCD"), ""));
				item.setYearly(StringUtils.defaultIfBlank(rs.getString("YEARLY"), ""));
				item.setLttgtcd(StringUtils.defaultIfBlank(rs.getString("LTTGTCD"), ""));
				item.setLttgtnm(StringUtils.defaultIfBlank(rs.getString("LTTGTNM"), ""));
				item.setConscd(StringUtils.defaultIfBlank(rs.getString("CONSCD"), ""));
				item.setConsnm(StringUtils.defaultIfBlank(rs.getString("CONSNM"), ""));
				item.setCtgcd(StringUtils.defaultIfBlank(rs.getString("CTGCD"), ""));
				item.setCtgnm(StringUtils.defaultIfBlank(rs.getString("CTGNM"), ""));
				item.setPdtcd(StringUtils.defaultIfBlank(rs.getString("PDTCD"), ""));
				item.setPdtnm(StringUtils.defaultIfBlank(rs.getString("PDTNM"), ""));
				item.setPrjtnm(StringUtils.defaultIfBlank(rs.getString("PRJTNM"), ""));
				String hqcd = StringUtils.defaultIfBlank(rs.getString("HQCD"), "");
				item.setHqcd(hqcd);
				item.setHqnm(hqcd + "/" + StringUtils.defaultIfBlank(rs.getString("HQNM"), ""));
				item.setPtrempno(StringUtils.defaultIfBlank(rs.getString("PTREMPNO"), ""));
				item.setPtrempnm(StringUtils.defaultIfBlank(rs.getString("PTREMPNM"), ""));
				item.setMgrempno(StringUtils.defaultIfBlank(rs.getString("MGREMPNO"), ""));
				item.setMgrempnm(StringUtils.defaultIfBlank(rs.getString("MGREMPNM"), ""));
				item.setChrempnm(StringUtils.defaultIfBlank(rs.getString("CHREMPNM"), ""));
				item.setUpddt(StringUtils.defaultIfBlank(rs.getString("UPDDT"), ""));
				item.setCisdt1(ServiceHelper.doConvertStrictDateFormat(StringUtils.defaultIfBlank(rs.getString("CISDT1"), ""), "-"));
				item.setCisdt2(ServiceHelper.doConvertStrictDateFormat(StringUtils.defaultIfBlank(rs.getString("CISDT2"), ""), "-"));
				item.setCisdt3(ServiceHelper.doConvertStrictDateFormat(StringUtils.defaultIfBlank(rs.getString("CISDT3"), ""), "-"));
				item.setCisdt4(ServiceHelper.doConvertStrictDateFormat(StringUtils.defaultIfBlank(rs.getString("CISDT4"), ""), "-"));
				item.setClosdt1(ServiceHelper.doConvertStrictDateFormat(StringUtils.defaultIfBlank(rs.getString("CLOSDT1"), ""), "-"));
				item.setClosdt2(ServiceHelper.doConvertStrictDateFormat(StringUtils.defaultIfBlank(rs.getString("CLOSDT2"), ""), "-"));
				item.setClosdt3(ServiceHelper.doConvertStrictDateFormat(StringUtils.defaultIfBlank(rs.getString("CLOSDT3"), ""), "-"));
				list.add(item);
			}
		}

		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 인수/매각 상세 정보 조회
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param prjtcd
	 * @return
	 * @throws SQLException
	 */
	public DcLtBuyBean sqlSelectConsultBuy(String prjtcd) throws SQLException {
		DcLtBuyBean item = null;

		SQLManagement sqlmng = new SQLManagement(DcLeagueTableSQL.SELECT_LEAGUE_BUY);
		sqlmng.addValueReplacement("PRJTCD", prjtcd);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		ResultSet rs = m_dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs != null) {
			while (rs.next()) {
				item = new DcLtBuyBean();
				item.setPrjtcd(StringUtils.defaultIfBlank(rs.getString("PRJTCD"), ""));
				item.setPrjtnm(StringUtils.defaultIfBlank(rs.getString("PRJTNM"), ""));
				item.setHqcd(StringUtils.defaultIfBlank(rs.getString("HQCD"), ""));
				item.setHqnm(StringUtils.defaultIfBlank(rs.getString("HQNM"), ""));
				item.setPtrempno(StringUtils.defaultIfBlank(rs.getString("PTREMPNO"), ""));
				item.setPtrempnm(StringUtils.defaultIfBlank(rs.getString("PTREMPNM"), ""));
				item.setPtrhqcd(StringUtils.defaultIfBlank(rs.getString("PTRHQCD"), ""));
				item.setMgrempno(StringUtils.defaultIfBlank(rs.getString("MGREMPNO"), ""));
				item.setMgrempnm(StringUtils.defaultIfBlank(rs.getString("MGREMPNM"), ""));
				item.setCtgcd(StringUtils.defaultIfBlank(rs.getString("CTGCD"), ""));
				item.setCtgnm(StringUtils.defaultIfBlank(rs.getString("CTGNM"), ""));
				item.setPdtcd(StringUtils.defaultIfBlank(rs.getString("PDTCD"), ""));
				item.setPdtnm(StringUtils.defaultIfBlank(rs.getString("PDTNM"), ""));
				item.setConscd(StringUtils.defaultIfBlank(rs.getString("CONSCD"), ""));
				item.setConsnm(StringUtils.defaultIfBlank(rs.getString("CONSNM"), ""));
				item.setStatuscd(StringUtils.defaultIfBlank(rs.getString("STATUSCD"), ""));
				item.setStatusnm(StringUtils.defaultIfBlank(rs.getString("STATUSNM"), ""));
				item.setConfcd(StringUtils.defaultIfBlank(rs.getString("CONFCD"), ""));
				item.setConfnm(StringUtils.defaultIfBlank(rs.getString("CONFNM"), ""));
				item.setChrgconfcd(StringUtils.defaultIfBlank(rs.getString("CHRGCONFCD"), ""));
				item.setChrgconfnm(StringUtils.defaultIfBlank(rs.getString("CHRGCONFNM"), ""));
				item.setChrgconfempno(StringUtils.defaultIfBlank(rs.getString("CHRGCONFEMPNO"), ""));
				item.setChrgconfempnm(StringUtils.defaultIfBlank(rs.getString("CHRGCONFEMPNM"), ""));
				item.setChrgconfdt(StringUtils.defaultIfBlank(rs.getString("CHRGCONFDT"), ""));
				item.setComnm(StringUtils.defaultIfBlank(rs.getString("COMNM"), ""));
				item.setTrgtetc(StringUtils.defaultIfBlank(rs.getString("TRGTETC"), ""));
				item.setBuyoutcd(StringUtils.defaultIfBlank(rs.getString("BUYOUTCD"), ""));
				item.setBuyoutnm(StringUtils.defaultIfBlank(rs.getString("BUYOUTNM"), ""));
				item.setConsdt(StringUtils.defaultIfBlank(rs.getString("CONSDT"), ""));
				item.setSpadt(StringUtils.defaultIfBlank(rs.getString("SPADT"), ""));
				item.setMoudt(StringUtils.defaultIfBlank(rs.getString("MOUDT"), ""));
				item.setClosdt(StringUtils.defaultIfBlank(rs.getString("CLOSDT"), ""));
				item.setCurrcd(StringUtils.defaultIfBlank(rs.getString("CURRCD"), ""));
				item.setCurrnm(StringUtils.defaultIfBlank(rs.getString("CURRNM"), ""));
				item.setRate(StringUtils.defaultIfBlank(rs.getString("RATE"), ""));
				item.setAmt(StringUtils.defaultIfBlank(rs.getString("AMT"), ""));
				item.setRateamt(StringUtils.defaultIfBlank(rs.getString("RATEAMT"), ""));
				item.setBorddealcd(StringUtils.defaultIfBlank(rs.getString("BORDDEALCD"), ""));
				item.setBorddealnm(StringUtils.defaultIfBlank(rs.getString("BORDDEALNM"), ""));
				item.setDealcd2(StringUtils.defaultIfBlank(rs.getString("DEALCD2"), ""));
				item.setDealnm2(StringUtils.defaultIfBlank(rs.getString("DEALNM2"), ""));
				item.setDealcd3(StringUtils.defaultIfBlank(rs.getString("DEALCD3"), ""));
				item.setDealnm3(StringUtils.defaultIfBlank(rs.getString("DEALNM3"), ""));
				item.setDealcd4(StringUtils.defaultIfBlank(rs.getString("DEALCD4"), ""));
				item.setDealnm4(StringUtils.defaultIfBlank(rs.getString("DEALNM4"), ""));
				item.setUpddt(StringUtils.defaultIfBlank(rs.getString("UPDDT"), ""));
			}
		}

		return item;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 합병 상세 정보 조회
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param prjtcd
	 * @return
	 * @throws SQLException
	 */
	public DcLtMnaBean sqlSelectConsultMna(String prjtcd) throws SQLException {
		DcLtMnaBean item = null;

		SQLManagement sqlmng = new SQLManagement(DcLeagueTableSQL.SELECT_LEAGUE_MNA);
		sqlmng.addValueReplacement("PRJTCD", prjtcd);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		ResultSet rs = m_dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs != null) {
			while (rs.next()) {
				item = new DcLtMnaBean();
				item.setPrjtcd(StringUtils.defaultIfBlank(rs.getString("PRJTCD"), ""));
				item.setPrjtnm(StringUtils.defaultIfBlank(rs.getString("PRJTNM"), ""));
				item.setHqcd(StringUtils.defaultIfBlank(rs.getString("HQCD"), ""));
				item.setHqnm(StringUtils.defaultIfBlank(rs.getString("HQNM"), ""));
				item.setPtrempno(StringUtils.defaultIfBlank(rs.getString("PTREMPNO"), ""));
				item.setPtrempnm(StringUtils.defaultIfBlank(rs.getString("PTREMPNM"), ""));
				item.setPtrhqcd(StringUtils.defaultIfBlank(rs.getString("PTRHQCD"), ""));
				item.setMgrempno(StringUtils.defaultIfBlank(rs.getString("MGREMPNO"), ""));
				item.setMgrempnm(StringUtils.defaultIfBlank(rs.getString("MGREMPNM"), ""));
				item.setCtgcd(StringUtils.defaultIfBlank(rs.getString("CTGCD"), ""));
				item.setCtgnm(StringUtils.defaultIfBlank(rs.getString("CTGNM"), ""));
				item.setPdtcd(StringUtils.defaultIfBlank(rs.getString("PDTCD"), ""));
				item.setPdtnm(StringUtils.defaultIfBlank(rs.getString("PDTNM"), ""));
				item.setConscd(StringUtils.defaultIfBlank(rs.getString("CONSCD"), ""));
				item.setConsnm(StringUtils.defaultIfBlank(rs.getString("CONSNM"), ""));
				item.setStatuscd(StringUtils.defaultIfBlank(rs.getString("STATUSCD"), ""));
				item.setStatusnm(StringUtils.defaultIfBlank(rs.getString("STATUSNM"), ""));
				item.setConfcd(StringUtils.defaultIfBlank(rs.getString("CONFCD"), ""));
				item.setConfnm(StringUtils.defaultIfBlank(rs.getString("CONFNM"), ""));
				item.setChrgconfcd(StringUtils.defaultIfBlank(rs.getString("CHRGCONFCD"), ""));
				item.setChrgconfnm(StringUtils.defaultIfBlank(rs.getString("CHRGCONFNM"), ""));
				item.setChrgconfempno(StringUtils.defaultIfBlank(rs.getString("CHRGCONFEMPNO"), ""));
				item.setChrgconfempnm(StringUtils.defaultIfBlank(rs.getString("CHRGCONFEMPNM"), ""));
				item.setChrgconfdt(StringUtils.defaultIfBlank(rs.getString("CHRGCONFDT"), ""));
				item.setComnm(StringUtils.defaultIfBlank(rs.getString("COMNM"), ""));
				item.setConsdt(StringUtils.defaultIfBlank(rs.getString("CONSDT"), ""));
				item.setDirtdt(StringUtils.defaultIfBlank(rs.getString("DIRTDT"), ""));
				item.setStkhdt(StringUtils.defaultIfBlank(rs.getString("STKHDT"), ""));
				item.setMnadt(StringUtils.defaultIfBlank(rs.getString("MNADT"), ""));
				item.setCurrcd(StringUtils.defaultIfBlank(rs.getString("CURRCD"), ""));
				item.setCurrnm(StringUtils.defaultIfBlank(rs.getString("CURRNM"), ""));
				item.setRate(StringUtils.defaultIfBlank(rs.getString("RATE"), ""));
				item.setAmt(StringUtils.defaultIfBlank(rs.getString("AMT"), ""));
				item.setRateamt(StringUtils.defaultIfBlank(rs.getString("RATEAMT"), ""));
				item.setBorddealcd(StringUtils.defaultIfBlank(rs.getString("BORDDEALCD"), ""));
				item.setBorddealnm(StringUtils.defaultIfBlank(rs.getString("BORDDEALNM"), ""));
				item.setDealcd1(StringUtils.defaultIfBlank(rs.getString("DEALCD1"), ""));
				item.setDealnm1(StringUtils.defaultIfBlank(rs.getString("DEALNM1"), ""));
				item.setDealcd2(StringUtils.defaultIfBlank(rs.getString("DEALCD2"), ""));
				item.setDealnm2(StringUtils.defaultIfBlank(rs.getString("DEALNM2"), ""));
				item.setDealcd3(StringUtils.defaultIfBlank(rs.getString("DEALCD3"), ""));
				item.setDealnm3(StringUtils.defaultIfBlank(rs.getString("DEALNM3"), ""));
				item.setUpddt(StringUtils.defaultIfBlank(rs.getString("UPDDT"), ""));
			}
		}

		return item;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 부동산 상세 정보 조회
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param prjtcd
	 * @return
	 * @throws SQLException
	 */
	public DcLtRealBean sqlSelectConsultReal(String prjtcd) throws SQLException {
		DcLtRealBean item = null;

		SQLManagement sqlmng = new SQLManagement(DcLeagueTableSQL.SELECT_LEAGUE_REAL);
		sqlmng.addValueReplacement("PRJTCD", prjtcd);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		ResultSet rs = m_dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs != null) {
			while (rs.next()) {
				item = new DcLtRealBean();
				item.setPrjtcd(StringUtils.defaultIfBlank(rs.getString("PRJTCD"), ""));
				item.setPrjtnm(StringUtils.defaultIfBlank(rs.getString("PRJTNM"), ""));
				item.setHqcd(StringUtils.defaultIfBlank(rs.getString("HQCD"), ""));
				item.setHqnm(StringUtils.defaultIfBlank(rs.getString("HQNM"), ""));
				item.setPtrempno(StringUtils.defaultIfBlank(rs.getString("PTREMPNO"), ""));
				item.setPtrempnm(StringUtils.defaultIfBlank(rs.getString("PTREMPNM"), ""));
				item.setPtrhqcd(StringUtils.defaultIfBlank(rs.getString("PTRHQCD"), ""));
				item.setMgrempno(StringUtils.defaultIfBlank(rs.getString("MGREMPNO"), ""));
				item.setMgrempnm(StringUtils.defaultIfBlank(rs.getString("MGREMPNM"), ""));
				item.setCtgcd(StringUtils.defaultIfBlank(rs.getString("CTGCD"), ""));
				item.setCtgnm(StringUtils.defaultIfBlank(rs.getString("CTGNM"), ""));
				item.setPdtcd(StringUtils.defaultIfBlank(rs.getString("PDTCD"), ""));
				item.setPdtnm(StringUtils.defaultIfBlank(rs.getString("PDTNM"), ""));
				item.setConscd(StringUtils.defaultIfBlank(rs.getString("CONSCD"), ""));
				item.setConsnm(StringUtils.defaultIfBlank(rs.getString("CONSNM"), ""));
				item.setStatuscd(StringUtils.defaultIfBlank(rs.getString("STATUSCD"), ""));
				item.setStatusnm(StringUtils.defaultIfBlank(rs.getString("STATUSNM"), ""));
				item.setConfcd(StringUtils.defaultIfBlank(rs.getString("CONFCD"), ""));
				item.setConfnm(StringUtils.defaultIfBlank(rs.getString("CONFNM"), ""));
				item.setChrgconfcd(StringUtils.defaultIfBlank(rs.getString("CHRGCONFCD"), ""));
				item.setChrgconfnm(StringUtils.defaultIfBlank(rs.getString("CHRGCONFNM"), ""));
				item.setChrgconfempno(StringUtils.defaultIfBlank(rs.getString("CHRGCONFEMPNO"), ""));
				item.setChrgconfempnm(StringUtils.defaultIfBlank(rs.getString("CHRGCONFEMPNM"), ""));
				item.setChrgconfdt(StringUtils.defaultIfBlank(rs.getString("CHRGCONFDT"), ""));
				item.setComnm(StringUtils.defaultIfBlank(rs.getString("COMNM"), ""));
				item.setConsdt(StringUtils.defaultIfBlank(rs.getString("CONSDT"), ""));
				item.setSpadt(StringUtils.defaultIfBlank(rs.getString("SPADT"), ""));
				item.setMoudt(StringUtils.defaultIfBlank(rs.getString("MOUDT"), ""));
				item.setClosdt(StringUtils.defaultIfBlank(rs.getString("CLOSDT"), ""));
				item.setCurrcd(StringUtils.defaultIfBlank(rs.getString("CURRCD"), ""));
				item.setCurrnm(StringUtils.defaultIfBlank(rs.getString("CURRNM"), ""));
				item.setRate(StringUtils.defaultIfBlank(rs.getString("RATE"), ""));
				item.setAmt(StringUtils.defaultIfBlank(rs.getString("AMT"), ""));
				item.setRateamt(StringUtils.defaultIfBlank(rs.getString("RATEAMT"), ""));
				item.setBorddealcd(StringUtils.defaultIfBlank(rs.getString("BORDDEALCD"), ""));
				item.setBorddealnm(StringUtils.defaultIfBlank(rs.getString("BORDDEALNM"), ""));
				item.setUpddt(StringUtils.defaultIfBlank(rs.getString("UPDDT"), ""));
			}
		}

		return item;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table 기업정보 목록 조회
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param prjtcd
	 * @return
	 * @throws SQLException
	 */
	public List<DcLtBizBean> sqlSelectLeagueBizList(String prjtcd) throws SQLException {
		List<DcLtBizBean> list = new ArrayList<DcLtBizBean>();

		SQLManagement sqlmng = new SQLManagement(DcLeagueTableSQL.SELECT_LEAGUE_BIZ_LIST);
		sqlmng.addValueReplacement("PRJTCD", prjtcd);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		ResultSet rs = m_dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs != null) {
			while (rs.next()) {
				DcLtBizBean item = new DcLtBizBean();
				item.setPrjtcd(StringUtils.defaultIfBlank(rs.getString("PRJTCD"), ""));
				item.setActcd(StringUtils.defaultIfBlank(rs.getString("ACTCD"), ""));
				item.setBizcd(StringUtils.defaultIfBlank(rs.getString("BIZCD"), ""));
				item.setSeq(StringUtils.defaultIfBlank(rs.getString("SEQ"), ""));
				item.setBizdivcd(StringUtils.defaultIfBlank(rs.getString("BIZDIVCD"), ""));
				item.setCompcd(StringUtils.defaultIfBlank(rs.getString("COMPCD"), ""));
				item.setComphannm(StringUtils.defaultIfBlank(rs.getString("COMPHANNM"), ""));
				item.setCompengnm(StringUtils.defaultIfBlank(rs.getString("COMPENGNM"), ""));
				item.setInducd(StringUtils.defaultIfBlank(rs.getString("INDUCD"), ""));
				item.setIndunm(StringUtils.defaultIfBlank(rs.getString("INDUNM"), ""));
				item.setNatcd(StringUtils.defaultIfBlank(rs.getString("NATCD"), ""));
				item.setNatnm(StringUtils.defaultIfBlank(rs.getString("NATNM"), ""));
				item.setCity(StringUtils.defaultIfBlank(rs.getString("CITY"), ""));
				item.setAddr(StringUtils.defaultIfBlank(rs.getString("ADDR"), ""));
				item.setChrgempno1(StringUtils.defaultIfBlank(rs.getString("CHRGEMPNO1"), ""));
				item.setChrgempnm1(StringUtils.defaultIfBlank(rs.getString("CHRGEMPNM1"), ""));
				item.setChrgempno2(StringUtils.defaultIfBlank(rs.getString("CHRGEMPNO2"), ""));
				item.setChrgempnm2(StringUtils.defaultIfBlank(rs.getString("CHRGEMPNM2"), ""));
				item.setDealnm(StringUtils.defaultIfBlank(rs.getString("DEALNM"), ""));

				list.add(item);
			}
		}

		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 인수/매각 거래형태 1 목록 조회
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param prjtcd
	 * @return
	 * @throws SQLException
	 */
	public List<DcLtDealListBean> sqlSelectLeagueBuyDeal1List(String prjtcd) throws SQLException {
		List<DcLtDealListBean> list = new ArrayList<DcLtDealListBean>();

		SQLManagement sqlmng = new SQLManagement(DcLeagueTableSQL.SELECT_LEAGUE_BUY_DEAL1_LIST);
		sqlmng.addValueReplacement("PRJTCD", prjtcd);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		ResultSet rs = m_dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs != null) {
			while (rs.next()) {
				DcLtDealListBean item = new DcLtDealListBean();
				item.setPrjtcd(StringUtils.defaultIfBlank(rs.getString("PRJTCD"), ""));
				item.setDealitemcd(StringUtils.defaultIfBlank(rs.getString("DEALITEMCD"), ""));
				item.setDealitemnm(StringUtils.defaultIfBlank(rs.getString("DEALITEMNM"), ""));

				list.add(item);
			}
		}

		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table 편집 가능 롤 목록 조회
	 * ====================================================================================
	 * </pre>
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<String> sqlSelectLeagueEditableRoleList() throws SQLException {
		List<String> list = new ArrayList<String>();
		
		SQLManagement sqlmng = new SQLManagement(DcLeagueTableSQL.SELECT_LEAGUE_EDITALBE_ROLE_LIST);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		ResultSet rs = m_dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs != null) {
			while (rs.next()) {
				String item = StringUtils.defaultIfBlank(rs.getString("ROLECD"), "");
				list.add(item);
			}
		}
		
		return list;
	}

	/**
	 * <pre>
	 * ====================================================================================
	 * 자문 형태 지정 업데이트
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param prjtcd
	 * @param conscd
	 * @return
	 * @throws SQLException
	 */
	public int sqlUpdateSetupConsultType(String prjtcd, String conscd) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcLeagueTableSQL.UPDATE_SETUP_CONSULT_TYPE);
		sqlmng.addValueReplacement("PRJTCD", prjtcd);
		sqlmng.addValueReplacement("CONSCD", conscd);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		return m_dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}

	/**
	 * <pre>
	 * ====================================================================================
	 * 인수/매각 자문 등록
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param prjtcd
	 * @return
	 * @throws SQLException
	 */
	public int sqlInsertSetupConsultBuy(String prjtcd) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcLeagueTableSQL.INSERT_SETUP_CONSULT_BUY);
		sqlmng.addValueReplacement("PRJTCD", prjtcd);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		return m_dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}

	/**
	 * <pre>
	 * ====================================================================================
	 * 합병 자문 등록
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param prjtcd
	 * @return
	 * @throws SQLException
	 */
	public int sqlInsertSetupConsultMna(String prjtcd) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcLeagueTableSQL.INSERT_SETUP_CONSULT_MNA);
		sqlmng.addValueReplacement("PRJTCD", prjtcd);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		return m_dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}

	/**
	 * <pre>
	 * ====================================================================================
	 * 부동산 자문 등록
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param prjtcd
	 * @return
	 * @throws SQLException
	 */
	public int sqlInsertSetupConsultReal(String prjtcd) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcLeagueTableSQL.INSERT_SETUP_CONSULT_REAL);
		sqlmng.addValueReplacement("PRJTCD", prjtcd);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		return m_dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 인수/매각 자문 타겟 등록
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param prjtcd
	 * @return
	 * @throws SQLException
	 */
	public int sqlInsertSetupConsultBuyTarget(String prjtcd) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcLeagueTableSQL.INSERT_SETUP_CONSULT_BUY_TARGET);
		sqlmng.addValueReplacement("PRJTCD", prjtcd);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		return m_dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 부동산 자문 타겟 등록
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param prjtcd
	 * @return
	 * @throws SQLException
	 */
	public int sqlInsertSetupConsultRealTarget(String prjtcd) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcLeagueTableSQL.INSERT_SETUP_CONSULT_REAL_TARGET);
		sqlmng.addValueReplacement("PRJTCD", prjtcd);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		return m_dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 자문 삼일 클라이언트 등록
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param prjtcd
	 * @return
	 * @throws SQLException
	 */
	public int sqlInsertSetupConsultSamil(String prjtcd) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcLeagueTableSQL.INSERT_SETUP_CONSULT_SAMIL);
		sqlmng.addValueReplacement("PRJTCD", prjtcd);
		sqlmng.addValueReplacement("COMPCD", Constants.Samil.COMP_CD);
		sqlmng.addValueReplacement("COMPHANNM", Constants.Samil.HAN_NM);
		sqlmng.addValueReplacement("COMPENGNM", Constants.Samil.ENG_NM);
		sqlmng.addValueReplacement("NATCD", Constants.Samil.NAT_CD);
		sqlmng.addValueReplacement("INDUCD", Constants.Samil.INDU_CD);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		return m_dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 자문 담당자 확인 YES
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param prjtcd
	 * @param chrgconfcd
	 * @param empno
	 * @return
	 * @throws SQLException
	 */
	public int sqlUpdateConfirmConsultYes(String prjtcd, String chrgconfcd, String empno) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcLeagueTableSQL.UPDATE_CONFIRM_CONSULT_YES);
		sqlmng.addValueReplacement("PRJTCD", prjtcd);
		sqlmng.addValueReplacement("CHRGCONFCD", chrgconfcd);
		sqlmng.addValueReplacement("EMPNO", empno);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		return m_dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 자문 담당자 확인 NO
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param prjtcd
	 * @param chrgconfcd
	 * @return
	 * @throws SQLException
	 */
	public int sqlUpdateConfirmConsultNo(String prjtcd, String chrgconfcd) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcLeagueTableSQL.UPDATE_CONFIRM_CONSULT_NO);
		sqlmng.addValueReplacement("PRJTCD", prjtcd);
		sqlmng.addValueReplacement("CHRGCONFCD", chrgconfcd);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		return m_dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 자문 ET 확인
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param prjtcd
	 * @param chrgconfcd
	 * @param empno
	 * @return
	 * @throws SQLException
	 */
	public int sqlUpdateETConfirmConsult(String prjtcd, String empno) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcLeagueTableSQL.UPDATE_ET_CONFIRM_CONSULT);
		sqlmng.addValueReplacement("PRJTCD", prjtcd);
		sqlmng.addValueReplacement("EMPNO", empno);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		return m_dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 대상 프로젝트 업데이트
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param prjtcd
	 * @param conscd
	 * @return
	 * @throws SQLException
	 */
	public int sqlUpdateProjectTarget(String prjtcd, String lttgtcd) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcLeagueTableSQL.UPDATE_PROJECT_TARGET);
		sqlmng.addValueReplacement("PRJTCD", prjtcd);
		sqlmng.addValueReplacement("LTTGTCD", lttgtcd);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		return m_dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table Base 업데이트
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param project
	 * @return
	 * @throws SQLException
	 */
	public int sqlUpdateLeagueTableBase(DcLtBuyBean project) throws SQLException {
		return sqlUpdateLeagueTableBase(project.getPrjtcd(), project.getStatuscd(), project.getConfcd(), project.getComnm());
	}
	
	public int sqlUpdateLeagueTableBase(DcLtMnaBean project) throws SQLException {
		return sqlUpdateLeagueTableBase(project.getPrjtcd(), project.getStatuscd(), project.getConfcd(), project.getComnm());
	}
	
	public int sqlUpdateLeagueTableBase(DcLtRealBean project) throws SQLException {
		return sqlUpdateLeagueTableBase(project.getPrjtcd(), project.getStatuscd(), project.getConfcd(), project.getComnm());
	}
	
	private int sqlUpdateLeagueTableBase(String prjtcd, String statuscd, String confcd, String comnm) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcLeagueTableSQL.UPDATE_LEAGUE_BASE);
		sqlmng.addValueReplacement("PRJTCD", prjtcd);
		sqlmng.addValueReplacement("STATUSCD", statuscd);
		sqlmng.addValueReplacement("CONFCD", confcd);
		sqlmng.addValueReplacement("COMNM", comnm);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		return m_dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table Buy 업데이트
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param project
	 * @return
	 * @throws SQLException
	 */
	public int sqlUpdateLeagueTableBuy(DcLtBuyBean project) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcLeagueTableSQL.UPDATE_LEAGUE_BUY);
		sqlmng.addValueReplacement("PRJTCD", project.getPrjtcd());
		sqlmng.addValueReplacement("TRGTETC", project.getTrgtetc());
		sqlmng.addValueReplacement("BUYOUTCD", project.getBuyoutcd());
		sqlmng.addValueReplacement("CONSDT", project.getConsdt());
		sqlmng.addValueReplacement("SPADT", project.getSpadt());
		sqlmng.addValueReplacement("MOUDT", project.getMoudt());
		sqlmng.addValueReplacement("CLOSDT", project.getClosdt());
		sqlmng.addValueReplacement("CURRCD", project.getCurrcd());
		sqlmng.addValueReplacement("AMT", project.getAmt());
		sqlmng.addValueReplacement("RATE", project.getRate());
		sqlmng.addValueReplacement("BORDDEALCD", project.getBorddealcd());
		sqlmng.addValueReplacement("DEALCD2", project.getDealcd2());
		sqlmng.addValueReplacement("DEALCD3", project.getDealcd3());
		sqlmng.addValueReplacement("DEALCD4", project.getDealcd4());
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		return m_dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table Mna 업데이트
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param project
	 * @return
	 * @throws SQLException
	 */
	public int sqlUpdateLeagueTableMna(DcLtMnaBean project) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcLeagueTableSQL.UPDATE_LEAGUE_MNA);
		sqlmng.addValueReplacement("PRJTCD", project.getPrjtcd());
		sqlmng.addValueReplacement("CONSDT", project.getConsdt());
		sqlmng.addValueReplacement("DIRTDT", project.getDirtdt());
		sqlmng.addValueReplacement("STKHDT", project.getStkhdt());
		sqlmng.addValueReplacement("MNADT", project.getMnadt());
		sqlmng.addValueReplacement("CURRCD", project.getCurrcd());
		sqlmng.addValueReplacement("AMT", project.getAmt());
		sqlmng.addValueReplacement("RATE", project.getRate());
		sqlmng.addValueReplacement("BORDDEALCD", project.getBorddealcd());
		sqlmng.addValueReplacement("DEALCD1", project.getDealcd1());
		sqlmng.addValueReplacement("DEALCD2", project.getDealcd2());
		sqlmng.addValueReplacement("DEALCD3", project.getDealcd3());
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		return m_dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table Real 업데이트
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param project
	 * @return
	 * @throws SQLException
	 */
	public int sqlUpdateLeagueTableReal(DcLtRealBean project) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcLeagueTableSQL.UPDATE_LEAGUE_REAL);
		sqlmng.addValueReplacement("PRJTCD", project.getPrjtcd());
		sqlmng.addValueReplacement("CONSDT", project.getConsdt());
		sqlmng.addValueReplacement("SPADT", project.getSpadt());
		sqlmng.addValueReplacement("MOUDT", project.getMoudt());
		sqlmng.addValueReplacement("CLOSDT", project.getClosdt());
		sqlmng.addValueReplacement("CURRCD", project.getCurrcd());
		sqlmng.addValueReplacement("AMT", project.getAmt());
		sqlmng.addValueReplacement("RATE", project.getRate());
		sqlmng.addValueReplacement("BORDDEALCD", project.getBorddealcd());
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		return m_dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table Buy 거래형태1 목록 삭제
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param project
	 * @return
	 * @throws SQLException
	 */
	public int sqlDeleteLeagueTableBuyDeal1(DcLtBuyBean project) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcLeagueTableSQL.DELETE_LEAGUE_BUY_DEAL1);
		sqlmng.addValueReplacement("PRJTCD", project.getPrjtcd());
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		return m_dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table Buy 거래형태1 목록 삭제
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param project
	 * @return
	 * @throws SQLException
	 */
	public int sqlDeleteLeagueTableBuyDeal1(String prjtcd) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcLeagueTableSQL.DELETE_LEAGUE_BUY_DEAL1);
		sqlmng.addValueReplacement("PRJTCD", prjtcd);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		return m_dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table Buy 거래형태1 항목 등록
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param project
	 * @return
	 * @throws SQLException
	 */
	public void sqlInsertLeagueTableBuyDeal1Batch(DcLtBuyBean project) throws SQLException {
		if (project.getDeal1() == null || project.getDeal1().size() == 0) {
			return;
		}
		for (DcLtDealListBean deal : project.getDeal1()) {
			SQLManagement sqlmng = new SQLManagement(DcLeagueTableSQL.INSERT_LEAGUE_BUY_DEAL1);
			sqlmng.addValueReplacement("PRJTCD", project.getPrjtcd());
			sqlmng.addValueReplacement("DEALITEMCD", deal.getDealitemcd());
			sqlmng.generate();
			if (logger.isDebugEnabled()) {
				logger.debug(sqlmng.getSql());
			}

			m_dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
		}
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table 기업정보 등록
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param list
	 * @throws SQLException
	 */
	public int sqlInsertLeagueTableBiz(List<DcLtBizBean> list) throws SQLException {
		if (list == null || list.size() == 0) {
			return 0;
		}
		int count = 0;
		for (DcLtBizBean biz : list) {
			SQLManagement sqlmng = new SQLManagement(DcLeagueTableSQL.INSERT_LEAGUE_BIZ);
			sqlmng.addValueReplacement("PRJTCD", biz.getPrjtcd());
			sqlmng.addValueReplacement("ACTCD", biz.getActcd());
			sqlmng.addValueReplacement("BIZCD", biz.getBizcd());
			sqlmng.addValueReplacement("PRJTCD", biz.getPrjtcd());
			sqlmng.addValueReplacement("ACTCD", biz.getActcd());
			sqlmng.addValueReplacement("BIZCD", biz.getBizcd());
			sqlmng.addValueReplacement("BIZDIVCD", biz.getBizdivcd());
			sqlmng.addValueReplacement("COMPCD", biz.getCompcd());
			sqlmng.addValueReplacement("COMPHANNM", biz.getComphannm());
			sqlmng.addValueReplacement("COMPENGNM", biz.getCompengnm());
			sqlmng.addValueReplacement("INDUCD", biz.getInducd());
			sqlmng.addValueReplacement("NATCD", biz.getNatcd());
			sqlmng.addValueReplacement("NATNM", biz.getNatnm());
			sqlmng.addValueReplacement("CHRGEMPNO1", biz.getChrgempno1());
			sqlmng.addValueReplacement("CHRGEMPNM1", biz.getChrgempnm1());
			sqlmng.addValueReplacement("DEALNM", biz.getDealnm());
			sqlmng.generate();
			if (logger.isDebugEnabled()) {
				logger.debug(sqlmng.getSql());
			}

			if (m_dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter()) == 1) {
				count++;
			}
		}
		
		return count;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table 기업정보 업데이트
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param list
	 * @throws SQLException
	 */
	public void sqlUpdateLeagueTableBiz(List<DcLtBizBean> list) throws SQLException {
		if (list == null || list.size() == 0) {
			return;
		}
		for (DcLtBizBean biz : list) {
			SQLManagement sqlmng = new SQLManagement(DcLeagueTableSQL.UPDATE_LEAGUE_BIZ);
			sqlmng.addValueReplacement("BIZDIVCD", biz.getBizdivcd());
			sqlmng.addValueReplacement("COMPCD", biz.getCompcd());
			sqlmng.addValueReplacement("COMPHANNM", biz.getComphannm());
			sqlmng.addValueReplacement("COMPENGNM", biz.getCompengnm());
			sqlmng.addValueReplacement("INDUCD", biz.getInducd());
			sqlmng.addValueReplacement("NATCD", biz.getNatcd());
			sqlmng.addValueReplacement("NATNM", biz.getNatnm());
			sqlmng.addValueReplacement("CHRGEMPNO1", biz.getChrgempno1());
			sqlmng.addValueReplacement("CHRGEMPNM1", biz.getChrgempnm1());
			sqlmng.addValueReplacement("DEALNM", biz.getDealnm());
			sqlmng.addValueReplacement("PRJTCD", biz.getPrjtcd());
			sqlmng.addValueReplacement("ACTCD", biz.getActcd());
			sqlmng.addValueReplacement("BIZCD", biz.getBizcd());
			sqlmng.addValueReplacement("SEQ", biz.getSeq());
			sqlmng.generate();
			if (logger.isDebugEnabled()) {
				logger.debug(sqlmng.getSql());
			}

			m_dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
		}
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table 기업정보 삭제
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param list
	 * @throws SQLException
	 */
	public void sqlDeleteLeagueTableBiz(List<DcLtBizBean> list) throws SQLException {
		if (list == null || list.size() == 0) {
			return;
		}
		for (DcLtBizBean biz : list) {
			SQLManagement sqlmng = new SQLManagement(DcLeagueTableSQL.DELETE_LEAGUE_BIZ);
			sqlmng.addValueReplacement("PRJTCD", biz.getPrjtcd());
			sqlmng.addValueReplacement("ACTCD", biz.getActcd());
			sqlmng.addValueReplacement("BIZCD", biz.getBizcd());
			sqlmng.addValueReplacement("SEQ", biz.getSeq());
			sqlmng.generate();
			if (logger.isDebugEnabled()) {
				logger.debug(sqlmng.getSql());
			}

			m_dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
		}
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table Buy 프로젝트 삭제
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param prjtcd
	 * @return
	 * @throws SQLException
	 */
	public int sqlDeleteLeagueTableBuy(String prjtcd) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcLeagueTableSQL.DELETE_LEAGUE_BUY);
		sqlmng.addValueReplacement("PRJTCD", prjtcd);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		return m_dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table M&A 프로젝트 삭제
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param prjtcd
	 * @return
	 * @throws SQLException
	 */
	public int sqlDeleteLeagueTableMna(String prjtcd) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcLeagueTableSQL.DELETE_LEAGUE_MNA);
		sqlmng.addValueReplacement("PRJTCD", prjtcd);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		return m_dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table 부동산 프로젝트 삭제
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param prjtcd
	 * @return
	 * @throws SQLException
	 */
	public int sqlDeleteLeagueTableReal(String prjtcd) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcLeagueTableSQL.DELETE_LEAGUE_REAL);
		sqlmng.addValueReplacement("PRJTCD", prjtcd);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		return m_dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table 기업정보 모두 삭제
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param prjtcd
	 * @return
	 * @throws SQLException
	 */
	public int sqlDeleteLeagueTableBizAll(String prjtcd) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcLeagueTableSQL.DELETE_LEAGUE_BIZ_ALL);
		sqlmng.addValueReplacement("PRJTCD", prjtcd);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		return m_dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
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
		rs = m_dbcon.executeQuery(sqlmng.getSql());
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
	 * 파트너 관리 본부 코드 조회
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param prjtcd
	 * @return
	 * @throws SQLException
	 */
	public String sqlSelectPtrEmpHQ(String ptrempno) throws SQLException {
		String ptrhqcd = "";

		SQLManagement sqlmng = new SQLManagement(DcLeagueTableSQL.SELECT_PTREMP_HQCD);
		sqlmng.addValueReplacement("PRTEMPNO", ptrempno);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		ResultSet rs = m_dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs != null) {
			while (rs.next()) {
				ptrhqcd = StringUtils.defaultIfBlank(rs.getString("PTRHQCD"), "");
			}
		}

		return ptrhqcd;
	}
}
