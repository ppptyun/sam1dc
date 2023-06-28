package com.samil.dc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.samil.dc.access.DBConnection;
import com.samil.dc.service.excel.bean.ImportBizBean;
import com.samil.dc.service.excel.bean.ImportBuyBean;
import com.samil.dc.service.excel.bean.ImportExceptCredentialBean;
import com.samil.dc.service.excel.bean.ImportMnaBean;
import com.samil.dc.service.excel.bean.ImportRealBean;
import com.samil.dc.sql.DcExcelImportSQL;
import com.samil.dc.sql.SQLManagement;

public class DcExcelImportDAO {

	private static final Logger logger = Logger.getRootLogger();
	private DBConnection dbcon = null;
	
	private final static String PRJTDIVCD = "102501";
	private final static String SQL_NULL = "";

	public DcExcelImportDAO(DBConnection _dbcon) {
		dbcon = _dbcon;
	}

	/**
	 * <pre>
	 * ====================================================================================
	 * 프로젝트 정보 조회
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param project
	 * @return
	 * @throws SQLException
	 */
	public ImportBuyBean sqlSelectProjectInfo(ImportBuyBean project) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcExcelImportSQL.SELECT_PROJECT_INFO);
		sqlmng.addValueReplacement("PRJTCD1", project.getPrjtcd1());
		sqlmng.addValueReplacement("PRJTCD2", project.getPrjtcd2());
		sqlmng.addValueReplacement("PRJTCD3", project.getPrjtcd3());
		sqlmng.generate();
				
		ResultSet rs = dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs.next()){
			project.setPrjtnm(StringUtils.defaultIfBlank(rs.getString("PRJTNM"), ""));
			project.setYearly(StringUtils.defaultIfBlank(rs.getString("YEARLY"), ""));
			project.setRefyearly(StringUtils.defaultIfBlank(rs.getString("REFYEARLY"), ""));
			project.setPdtcd(StringUtils.defaultIfBlank(rs.getString("PDTCD"), ""));
			project.setHqcd(StringUtils.defaultIfBlank(rs.getString("HQCD"), ""));
			project.setPtrempno(StringUtils.defaultIfBlank(rs.getString("PTREMPNO"), ""));
			project.setMgrempno(StringUtils.defaultIfBlank(rs.getString("MGREMPNO"), ""));
			project.setPaycd(StringUtils.defaultIfBlank(rs.getString("PAYCD"), ""));
			project.setProcessnm(StringUtils.defaultIfBlank(rs.getString("PROCESSNM"), ""));
			project.setContdt(StringUtils.defaultIfBlank(rs.getString("CONTDT"), ""));
			project.setTermidt(StringUtils.defaultIfBlank(rs.getString("TERMIDT"), ""));
			project.setChamtw(StringUtils.defaultIfBlank(rs.getString("CHAMTW"), ""));
			project.getClient().setCompcd(StringUtils.defaultIfBlank(rs.getString("COMPCD"), ""));
			project.getClient().setInducd(StringUtils.defaultIfBlank(rs.getString("INDUCD"), ""));
			project.setCisno(StringUtils.defaultIfBlank(rs.getString("CISNO"), ""));
			project.setCisno1(StringUtils.defaultIfBlank(rs.getString("CISNO1"), ""));
			project.setCisno2(StringUtils.defaultIfBlank(rs.getString("CISNO2"), ""));
			project.setCisno3(StringUtils.defaultIfBlank(rs.getString("CISNO3"), ""));
		}
		
		return project;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 프로젝트 정보 조회
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param project
	 * @return
	 * @throws SQLException
	 */
	public ImportMnaBean sqlSelectProjectInfo(ImportMnaBean project) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcExcelImportSQL.SELECT_PROJECT_INFO);
		sqlmng.addValueReplacement("PRJTCD1", project.getPrjtcd1());
		sqlmng.addValueReplacement("PRJTCD2", project.getPrjtcd2());
		sqlmng.addValueReplacement("PRJTCD3", project.getPrjtcd3());
		sqlmng.generate();
				
		ResultSet rs = dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs.next()){
			project.setPrjtnm(StringUtils.defaultIfBlank(rs.getString("PRJTNM"), ""));
			project.setYearly(StringUtils.defaultIfBlank(rs.getString("YEARLY"), ""));
			project.setRefyearly(StringUtils.defaultIfBlank(rs.getString("REFYEARLY"), ""));
			project.setPdtcd(StringUtils.defaultIfBlank(rs.getString("PDTCD"), ""));
			project.setHqcd(StringUtils.defaultIfBlank(rs.getString("HQCD"), ""));
			project.setPtrempno(StringUtils.defaultIfBlank(rs.getString("PTREMPNO"), ""));
			project.setMgrempno(StringUtils.defaultIfBlank(rs.getString("MGREMPNO"), ""));
			project.setPaycd(StringUtils.defaultIfBlank(rs.getString("PAYCD"), ""));
			project.setProcessnm(StringUtils.defaultIfBlank(rs.getString("PROCESSNM"), ""));
			project.setContdt(StringUtils.defaultIfBlank(rs.getString("CONTDT"), ""));
			project.setTermidt(StringUtils.defaultIfBlank(rs.getString("TERMIDT"), ""));
			project.setChamtw(StringUtils.defaultIfBlank(rs.getString("CHAMTW"), ""));
			project.getClient().setCompcd(StringUtils.defaultIfBlank(rs.getString("COMPCD"), ""));
			project.getClient().setInducd(StringUtils.defaultIfBlank(rs.getString("INDUCD"), ""));
			project.setCisno(StringUtils.defaultIfBlank(rs.getString("CISNO"), ""));
			project.setCisno1(StringUtils.defaultIfBlank(rs.getString("CISNO1"), ""));
			project.setCisno2(StringUtils.defaultIfBlank(rs.getString("CISNO2"), ""));
			project.setCisno3(StringUtils.defaultIfBlank(rs.getString("CISNO3"), ""));
		}
		
		return project;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 프로젝트 정보 조회
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param project
	 * @return
	 * @throws SQLException
	 */
	public ImportRealBean sqlSelectProjectInfo(ImportRealBean project) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcExcelImportSQL.SELECT_PROJECT_INFO);
		sqlmng.addValueReplacement("PRJTCD1", project.getPrjtcd1());
		sqlmng.addValueReplacement("PRJTCD2", project.getPrjtcd2());
		sqlmng.addValueReplacement("PRJTCD3", project.getPrjtcd3());
		sqlmng.generate();
				
		ResultSet rs = dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs.next()){
			project.setPrjtnm(StringUtils.defaultIfBlank(rs.getString("PRJTNM"), ""));
			project.setYearly(StringUtils.defaultIfBlank(rs.getString("YEARLY"), ""));
			project.setRefyearly(StringUtils.defaultIfBlank(rs.getString("REFYEARLY"), ""));
			project.setPdtcd(StringUtils.defaultIfBlank(rs.getString("PDTCD"), ""));
			project.setHqcd(StringUtils.defaultIfBlank(rs.getString("HQCD"), ""));
			project.setPtrempno(StringUtils.defaultIfBlank(rs.getString("PTREMPNO"), ""));
			project.setMgrempno(StringUtils.defaultIfBlank(rs.getString("MGREMPNO"), ""));
			project.setPaycd(StringUtils.defaultIfBlank(rs.getString("PAYCD"), ""));
			project.setProcessnm(StringUtils.defaultIfBlank(rs.getString("PROCESSNM"), ""));
			project.setContdt(StringUtils.defaultIfBlank(rs.getString("CONTDT"), ""));
			project.setTermidt(StringUtils.defaultIfBlank(rs.getString("TERMIDT"), ""));
			project.setChamtw(StringUtils.defaultIfBlank(rs.getString("CHAMTW"), ""));
			project.getClient().setCompcd(StringUtils.defaultIfBlank(rs.getString("COMPCD"), ""));
			project.getClient().setInducd(StringUtils.defaultIfBlank(rs.getString("INDUCD"), ""));
			project.setCisno(StringUtils.defaultIfBlank(rs.getString("CISNO"), ""));
			project.setCisno1(StringUtils.defaultIfBlank(rs.getString("CISNO1"), ""));
			project.setCisno2(StringUtils.defaultIfBlank(rs.getString("CISNO2"), ""));
			project.setCisno3(StringUtils.defaultIfBlank(rs.getString("CISNO3"), ""));
		}
		
		return project;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * Credential 프로젝트 존재 여부 조회
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param prjtcd
	 * @return
	 * @throws SQLException
	 */
	public boolean sqlSelectExistCredentialProject(String prjtcd) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcExcelImportSQL.SELECT_CREDENTIAL_COUNT);
		sqlmng.addValueReplacement("PRJTCD", prjtcd);
		sqlmng.generate();
		
		int count = 0;
		ResultSet rs = dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs.next()){
			count = rs.getInt("CNT");
		}
		
		return count > 0;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table 프로젝트 존재 여부 조회
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param prjtcd
	 * @return
	 * @throws SQLException
	 */
	public boolean sqlSelectExistLeagueProject(String prjtcd) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcExcelImportSQL.SELECT_LEAGUE_COUNT);
		sqlmng.addValueReplacement("PRJTCD", prjtcd);
		sqlmng.generate();
		
		int count = 0;
		ResultSet rs = dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs.next()){
			count = rs.getInt("CNT");
		}
		
		return count > 0;
	}

	/**
	 * <pre>
	 * ====================================================================================
	 * Credential 등록
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param project
	 * @return
	 * @throws SQLException
	 */
	public int sqlInsertCredential(Object project) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcExcelImportSQL.INSERT_CREDENTIAL);
		if (project instanceof ImportBuyBean) {
			ImportBuyBean credential = (ImportBuyBean) project;
			sqlmng.addValueReplacement("PRJTCD", credential.getPrjtcd());
			sqlmng.addValueReplacement("PRJTCD1", credential.getPrjtcd1());
			sqlmng.addValueReplacement("PRJTCD2", credential.getPrjtcd2());
			sqlmng.addValueReplacement("PRJTCD3", credential.getPrjtcd3());
			sqlmng.addValueReplacement("CISNO", credential.getCisno());
			sqlmng.addValueReplacement("CISNO1", credential.getCisno1());
			sqlmng.addValueReplacement("CISNO2", credential.getCisno2());
			sqlmng.addValueReplacement("CISNO3", credential.getCisno3());
			sqlmng.addValueReplacement("PRJTDIVCD", PRJTDIVCD);
			sqlmng.addValueReplacement("PRJTNM", credential.getPrjtnm());
			sqlmng.addValueReplacement("YEARLY", credential.getYearly());
			sqlmng.addValueReplacement("REFYEARLY", credential.getRefyearly());
			sqlmng.addValueReplacement("PDTCD", credential.getPdtcd());
			sqlmng.addValueReplacement("HQCD", credential.getHqcd());
			sqlmng.addValueReplacement("PTREMPNO", credential.getPtrempno());
			sqlmng.addValueReplacement("MGREMPNO", credential.getMgrempno());
			sqlmng.addValueReplacement("PAYCD", credential.getPaycd());
			sqlmng.addValueReplacement("CONTDT", credential.getContdt());
			sqlmng.addValueReplacement("TERMIDT", credential.getTermidt());
			sqlmng.addValueReplacement("CHAMTW", credential.getChamtw());
			sqlmng.addValueReplacement("TRGTETC", credential.getTrgtetc());
			sqlmng.addValueReplacement("CREDTGTCD", credential.getCredtgtcd());
			
		} else if (project instanceof ImportMnaBean) {
			ImportMnaBean credential = (ImportMnaBean) project;
			sqlmng.addValueReplacement("PRJTCD", credential.getPrjtcd());
			sqlmng.addValueReplacement("PRJTCD1", credential.getPrjtcd1());
			sqlmng.addValueReplacement("PRJTCD2", credential.getPrjtcd2());
			sqlmng.addValueReplacement("PRJTCD3", credential.getPrjtcd3());
			sqlmng.addValueReplacement("CISNO", credential.getCisno());
			sqlmng.addValueReplacement("CISNO1", credential.getCisno1());
			sqlmng.addValueReplacement("CISNO2", credential.getCisno2());
			sqlmng.addValueReplacement("CISNO3", credential.getCisno3());
			sqlmng.addValueReplacement("PRJTDIVCD", PRJTDIVCD);
			sqlmng.addValueReplacement("PRJTNM", credential.getPrjtnm());
			sqlmng.addValueReplacement("YEARLY", credential.getYearly());
			sqlmng.addValueReplacement("REFYEARLY", credential.getRefyearly());
			sqlmng.addValueReplacement("PDTCD", credential.getPdtcd());
			sqlmng.addValueReplacement("HQCD", credential.getHqcd());
			sqlmng.addValueReplacement("PTREMPNO", credential.getPtrempno());
			sqlmng.addValueReplacement("MGREMPNO", credential.getMgrempno());
			sqlmng.addValueReplacement("PAYCD", credential.getPaycd());
			sqlmng.addValueReplacement("CONTDT", credential.getContdt());
			sqlmng.addValueReplacement("TERMIDT", credential.getTermidt());
			sqlmng.addValueReplacement("CHAMTW", credential.getChamtw());
			sqlmng.addValueReplacement("TRGTETC", SQL_NULL);
			sqlmng.addValueReplacement("CREDTGTCD", credential.getCredtgtcd());
			
		} else if (project instanceof ImportRealBean) {
			ImportRealBean credential = (ImportRealBean) project;
			sqlmng.addValueReplacement("PRJTCD", credential.getPrjtcd());
			sqlmng.addValueReplacement("PRJTCD1", credential.getPrjtcd1());
			sqlmng.addValueReplacement("PRJTCD2", credential.getPrjtcd2());
			sqlmng.addValueReplacement("PRJTCD3", credential.getPrjtcd3());
			sqlmng.addValueReplacement("CISNO", credential.getCisno());
			sqlmng.addValueReplacement("CISNO1", credential.getCisno1());
			sqlmng.addValueReplacement("CISNO2", credential.getCisno2());
			sqlmng.addValueReplacement("CISNO3", credential.getCisno3());
			sqlmng.addValueReplacement("PRJTDIVCD", PRJTDIVCD);
			sqlmng.addValueReplacement("PRJTNM", credential.getPrjtnm());
			sqlmng.addValueReplacement("YEARLY", credential.getYearly());
			sqlmng.addValueReplacement("REFYEARLY", credential.getRefyearly());
			sqlmng.addValueReplacement("PDTCD", credential.getPdtcd());
			sqlmng.addValueReplacement("HQCD", credential.getHqcd());
			sqlmng.addValueReplacement("PTREMPNO", credential.getPtrempno());
			sqlmng.addValueReplacement("MGREMPNO", credential.getMgrempno());
			sqlmng.addValueReplacement("PAYCD", credential.getPaycd());
			sqlmng.addValueReplacement("CONTDT", credential.getContdt());
			sqlmng.addValueReplacement("TERMIDT", credential.getTermidt());
			sqlmng.addValueReplacement("CHAMTW", credential.getChamtw());
			sqlmng.addValueReplacement("TRGTETC", SQL_NULL);
			sqlmng.addValueReplacement("CREDTGTCD", credential.getCredtgtcd());
		}
		
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		return dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}

	/**
	 * <pre>
	 * ====================================================================================
	 * Credential BIZ 등록
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param biz
	 * @return
	 * @throws SQLException
	 */
	public int sqlInsertCredentialBiz(ImportBizBean biz) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcExcelImportSQL.INSERT_CREDENTIAL_BIZ);
		sqlmng.addValueReplacement("PRJTCD", biz.getPrjtcd());
		sqlmng.addValueReplacement("BIZCD", biz.getBizcd());
		sqlmng.addValueReplacement("SEQ", biz.getSeq());
		sqlmng.addValueReplacement("BIZDIVCD", biz.getBizdivcd());
		sqlmng.addValueReplacement("COMPCD", biz.getCompcd());
		sqlmng.addValueReplacement("COMPHANNM", biz.getComphannm());
		sqlmng.addValueReplacement("COMPENGNM", biz.getCompengnm());
		sqlmng.addValueReplacement("INDUCD", biz.getInducd());
		sqlmng.addValueReplacement("NATCD", biz.getNatcd());
		sqlmng.addValueReplacement("NATNM", biz.getNatnm());
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		return dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}

	/**
	 * <pre>
	 * ====================================================================================
	 * League Table Base + Buy/M&A/Real 등록
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param project
	 * @return
	 * @throws SQLException
	 */
	public int sqlInsertLeagueTable(Object project) throws SQLException {
		SQLManagement sqlbase = new SQLManagement(DcExcelImportSQL.INSERT_LEAGUE_BASE);
		SQLManagement sqlsub = null;
		if (project instanceof ImportBuyBean) {
			ImportBuyBean league = (ImportBuyBean) project;

			sqlbase.addValueReplacement("PRJTCD", league.getPrjtcd());
			sqlbase.addValueReplacement("PRJTCD1", league.getPrjtcd1());
			sqlbase.addValueReplacement("PRJTCD2", league.getPrjtcd2());
			sqlbase.addValueReplacement("PRJTCD3", league.getPrjtcd3());
			sqlbase.addValueReplacement("CISNO", league.getCisno());
			sqlbase.addValueReplacement("CISNO1", league.getCisno1());
			sqlbase.addValueReplacement("CISNO2", league.getCisno2());
			sqlbase.addValueReplacement("CISNO3", league.getCisno3());
			sqlbase.addValueReplacement("PRJTNM", league.getPrjtnm());
			sqlbase.addValueReplacement("YEARLY", league.getYearly());
			sqlbase.addValueReplacement("REFYEARLY", league.getRefyearly());
			sqlbase.addValueReplacement("PDTCD", league.getPdtcd());
			sqlbase.addValueReplacement("HQCD", league.getHqcd());
			sqlbase.addValueReplacement("PTREMPNO", league.getPtrempno());
			sqlbase.addValueReplacement("MGREMPNO", league.getMgrempno());
			sqlbase.addValueReplacement("CONSCD", league.getConscd());
			sqlbase.addValueReplacement("STATUSCD", league.getStatuscd());
			sqlbase.addValueReplacement("COMNM", league.getComnm());
			
			sqlsub = new SQLManagement(DcExcelImportSQL.INSERT_LEAGUE_BUY);
			sqlsub.addValueReplacement("PRJTCD", league.getPrjtcd());
			sqlsub.addValueReplacement("TRGTETC", league.getTrgtetc());
			sqlsub.addValueReplacement("BUYOUTCD", league.getBuyoutcd());
			sqlsub.addValueReplacement("CONSDT", league.getConsdt());
			sqlsub.addValueReplacement("SPADT", league.getSpadt());
			sqlsub.addValueReplacement("MOUDT", league.getMoudt());
			sqlsub.addValueReplacement("CLOSDT", league.getClosdt());
			sqlsub.addValueReplacement("CURRCD", league.getCurrcd());
			sqlsub.addValueReplacement("AMT", league.getAmt());
			sqlsub.addValueReplacement("RATE", league.getRate());
			sqlsub.addValueReplacement("BORDDEALCD", league.getBorddealcd());
			sqlsub.addValueReplacement("DEALCD2", league.getDealcd2());
			sqlsub.addValueReplacement("DEALCD3", league.getDealcd3());

		} else if (project instanceof ImportMnaBean) {
			ImportMnaBean league = (ImportMnaBean) project;

			sqlbase.addValueReplacement("PRJTCD", league.getPrjtcd());
			sqlbase.addValueReplacement("PRJTCD1", league.getPrjtcd1());
			sqlbase.addValueReplacement("PRJTCD2", league.getPrjtcd2());
			sqlbase.addValueReplacement("PRJTCD3", league.getPrjtcd3());
			sqlbase.addValueReplacement("CISNO", league.getCisno());
			sqlbase.addValueReplacement("CISNO1", league.getCisno1());
			sqlbase.addValueReplacement("CISNO2", league.getCisno2());
			sqlbase.addValueReplacement("CISNO3", league.getCisno3());
			sqlbase.addValueReplacement("PRJTNM", league.getPrjtnm());
			sqlbase.addValueReplacement("YEARLY", league.getYearly());
			sqlbase.addValueReplacement("REFYEARLY", league.getRefyearly());
			sqlbase.addValueReplacement("PDTCD", league.getPdtcd());
			sqlbase.addValueReplacement("HQCD", league.getHqcd());
			sqlbase.addValueReplacement("PTREMPNO", league.getPtrempno());
			sqlbase.addValueReplacement("MGREMPNO", league.getMgrempno());
			sqlbase.addValueReplacement("CONSCD", league.getConscd());
			sqlbase.addValueReplacement("STATUSCD", league.getStatuscd());
			sqlbase.addValueReplacement("COMNM", league.getComnm());
			
			sqlsub = new SQLManagement(DcExcelImportSQL.INSERT_LEAGUE_MNA);
			sqlsub.addValueReplacement("PRJTCD", league.getPrjtcd());
			sqlsub.addValueReplacement("CONSDT", league.getConsdt());
			sqlsub.addValueReplacement("DIRTDT", league.getDirtdt());
			sqlsub.addValueReplacement("STKHDT", league.getStkhdt());
			sqlsub.addValueReplacement("MNADT", league.getMnadt());
			sqlsub.addValueReplacement("CURRCD", league.getCurrcd());
			sqlsub.addValueReplacement("AMT", league.getAmt());
			sqlsub.addValueReplacement("RATE", league.getRate());
			sqlsub.addValueReplacement("BORDDEALCD", league.getBorddealcd());
			sqlsub.addValueReplacement("DEALCD1", league.getDealcd1());
			sqlsub.addValueReplacement("DEALCD2", league.getDealcd2());
			sqlsub.addValueReplacement("DEALCD3", league.getDealcd3());

		} else if (project instanceof ImportRealBean) {
			ImportRealBean league = (ImportRealBean) project;

			sqlbase.addValueReplacement("PRJTCD", league.getPrjtcd());
			sqlbase.addValueReplacement("PRJTCD1", league.getPrjtcd1());
			sqlbase.addValueReplacement("PRJTCD2", league.getPrjtcd2());
			sqlbase.addValueReplacement("PRJTCD3", league.getPrjtcd3());
			sqlbase.addValueReplacement("CISNO", league.getCisno());
			sqlbase.addValueReplacement("CISNO1", league.getCisno1());
			sqlbase.addValueReplacement("CISNO2", league.getCisno2());
			sqlbase.addValueReplacement("CISNO3", league.getCisno3());
			sqlbase.addValueReplacement("PRJTNM", league.getPrjtnm());
			sqlbase.addValueReplacement("YEARLY", league.getYearly());
			sqlbase.addValueReplacement("REFYEARLY", league.getRefyearly());
			sqlbase.addValueReplacement("PDTCD", league.getPdtcd());
			sqlbase.addValueReplacement("HQCD", league.getHqcd());
			sqlbase.addValueReplacement("PTREMPNO", league.getPtrempno());
			sqlbase.addValueReplacement("MGREMPNO", league.getMgrempno());
			sqlbase.addValueReplacement("CONSCD", league.getConscd());
			sqlbase.addValueReplacement("STATUSCD", league.getStatuscd());
			sqlbase.addValueReplacement("COMNM", SQL_NULL);
			
			sqlsub = new SQLManagement(DcExcelImportSQL.INSERT_LEAGUE_REAL);
			sqlsub.addValueReplacement("PRJTCD", league.getPrjtcd());
			sqlsub.addValueReplacement("CONSDT", league.getConsdt());
			sqlsub.addValueReplacement("SPADT", league.getSpadt());
			sqlsub.addValueReplacement("MOUDT", league.getMoudt());
			sqlsub.addValueReplacement("CLOSDT", league.getClosdt());
			sqlsub.addValueReplacement("CURRCD", league.getCurrcd());
			sqlsub.addValueReplacement("AMT", league.getAmt());
			sqlsub.addValueReplacement("RATE", league.getRate());
			sqlsub.addValueReplacement("BORDDEALCD", league.getBorddealcd());
		}
		
		sqlbase.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlbase.getSql());
		}
		sqlsub.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlsub.getSql());
		}

		int result1 = dbcon.executeUpdate(sqlbase.getSql(), sqlbase.getParameter());
		int result2 = dbcon.executeUpdate(sqlsub.getSql(), sqlsub.getParameter());

		return result1 + result2;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table Base 수정
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param project
	 * @return
	 * @throws SQLException
	 */
	public int sqlUpdateLeagueBase(Object project) throws SQLException {
		SQLManagement sqlbase = new SQLManagement(DcExcelImportSQL.UPDATE_LEAGUE_BASE);
		if (project instanceof ImportBuyBean) {
			ImportBuyBean league = (ImportBuyBean) project;

			sqlbase.addValueReplacement("PRJTCD", league.getPrjtcd());
			sqlbase.addValueReplacement("PRJTNM", league.getPrjtnm());
			sqlbase.addValueReplacement("YEARLY", league.getYearly());
			sqlbase.addValueReplacement("REFYEARLY", league.getRefyearly());
			sqlbase.addValueReplacement("PDTCD", league.getPdtcd());
			sqlbase.addValueReplacement("HQCD", league.getHqcd());
			sqlbase.addValueReplacement("PTREMPNO", league.getPtrempno());
			sqlbase.addValueReplacement("MGREMPNO", league.getMgrempno());
			sqlbase.addValueReplacement("CONSCD", league.getConscd());
			sqlbase.addValueReplacement("STATUSCD", league.getStatuscd());
			sqlbase.addValueReplacement("COMNM", league.getComnm());
			
		} else if (project instanceof ImportMnaBean) {
			ImportMnaBean league = (ImportMnaBean) project;

			sqlbase.addValueReplacement("PRJTCD", league.getPrjtcd());
			sqlbase.addValueReplacement("PRJTNM", league.getPrjtnm());
			sqlbase.addValueReplacement("YEARLY", league.getYearly());
			sqlbase.addValueReplacement("REFYEARLY", league.getRefyearly());
			sqlbase.addValueReplacement("PDTCD", league.getPdtcd());
			sqlbase.addValueReplacement("HQCD", league.getHqcd());
			sqlbase.addValueReplacement("PTREMPNO", league.getPtrempno());
			sqlbase.addValueReplacement("MGREMPNO", league.getMgrempno());
			sqlbase.addValueReplacement("CONSCD", league.getConscd());
			sqlbase.addValueReplacement("STATUSCD", league.getStatuscd());
			sqlbase.addValueReplacement("COMNM", league.getComnm());
			
		} else if (project instanceof ImportRealBean) {
			ImportRealBean league = (ImportRealBean) project;

			sqlbase.addValueReplacement("PRJTCD", league.getPrjtcd());
			sqlbase.addValueReplacement("PRJTNM", league.getPrjtnm());
			sqlbase.addValueReplacement("YEARLY", league.getYearly());
			sqlbase.addValueReplacement("REFYEARLY", league.getRefyearly());
			sqlbase.addValueReplacement("PDTCD", league.getPdtcd());
			sqlbase.addValueReplacement("HQCD", league.getHqcd());
			sqlbase.addValueReplacement("PTREMPNO", league.getPtrempno());
			sqlbase.addValueReplacement("MGREMPNO", league.getMgrempno());
			sqlbase.addValueReplacement("CONSCD", league.getConscd());
			sqlbase.addValueReplacement("STATUSCD", league.getStatuscd());
			sqlbase.addValueReplacement("COMNM", SQL_NULL);
		}
		
		sqlbase.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlbase.getSql());
		}

		int result1 = dbcon.executeUpdate(sqlbase.getSql(), sqlbase.getParameter());
		return result1;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table Buy/M&A/Real 등록
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param project
	 * @return
	 * @throws SQLException
	 */
	public int sqlInsertLeagueTableSub(Object project) throws SQLException {
		SQLManagement sqlsub = null;
		if (project instanceof ImportBuyBean) {
			ImportBuyBean league = (ImportBuyBean) project;
			
			sqlsub = new SQLManagement(DcExcelImportSQL.INSERT_LEAGUE_BUY);
			sqlsub.addValueReplacement("PRJTCD", league.getPrjtcd());
			sqlsub.addValueReplacement("TRGTETC", league.getTrgtetc());
			sqlsub.addValueReplacement("BUYOUTCD", league.getBuyoutcd());
			sqlsub.addValueReplacement("CONSDT", league.getConsdt());
			sqlsub.addValueReplacement("SPADT", league.getSpadt());
			sqlsub.addValueReplacement("MOUDT", league.getMoudt());
			sqlsub.addValueReplacement("CLOSDT", league.getClosdt());
			sqlsub.addValueReplacement("CURRCD", league.getCurrcd());
			sqlsub.addValueReplacement("AMT", league.getAmt());
			sqlsub.addValueReplacement("RATE", league.getRate());
			sqlsub.addValueReplacement("BORDDEALCD", league.getBorddealcd());
			sqlsub.addValueReplacement("DEALCD2", league.getDealcd2());
			sqlsub.addValueReplacement("DEALCD3", league.getDealcd3());

		} else if (project instanceof ImportMnaBean) {
			ImportMnaBean league = (ImportMnaBean) project;

			sqlsub = new SQLManagement(DcExcelImportSQL.INSERT_LEAGUE_MNA);
			sqlsub.addValueReplacement("PRJTCD", league.getPrjtcd());
			sqlsub.addValueReplacement("CONSDT", league.getConsdt());
			sqlsub.addValueReplacement("DIRTDT", league.getDirtdt());
			sqlsub.addValueReplacement("STKHDT", league.getStkhdt());
			sqlsub.addValueReplacement("MNADT", league.getMnadt());
			sqlsub.addValueReplacement("CURRCD", league.getCurrcd());
			sqlsub.addValueReplacement("AMT", league.getAmt());
			sqlsub.addValueReplacement("RATE", league.getRate());
			sqlsub.addValueReplacement("BORDDEALCD", league.getBorddealcd());
			sqlsub.addValueReplacement("DEALCD1", league.getDealcd1());
			sqlsub.addValueReplacement("DEALCD2", league.getDealcd2());
			sqlsub.addValueReplacement("DEALCD3", league.getDealcd3());

		} else if (project instanceof ImportRealBean) {
			ImportRealBean league = (ImportRealBean) project;

			sqlsub = new SQLManagement(DcExcelImportSQL.INSERT_LEAGUE_REAL);
			sqlsub.addValueReplacement("PRJTCD", league.getPrjtcd());
			sqlsub.addValueReplacement("CONSDT", league.getConsdt());
			sqlsub.addValueReplacement("SPADT", league.getSpadt());
			sqlsub.addValueReplacement("MOUDT", league.getMoudt());
			sqlsub.addValueReplacement("CLOSDT", league.getClosdt());
			sqlsub.addValueReplacement("CURRCD", league.getCurrcd());
			sqlsub.addValueReplacement("AMT", league.getAmt());
			sqlsub.addValueReplacement("RATE", league.getRate());
			sqlsub.addValueReplacement("BORDDEALCD", league.getBorddealcd());
		}
		
		sqlsub.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlsub.getSql());
		}

		int result2 = dbcon.executeUpdate(sqlsub.getSql(), sqlsub.getParameter());
		return result2;
	}

	/**
	 * <pre>
	 * ====================================================================================
	 * League Table BIZ 등록(비자문회사)
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param biz
	 * @return
	 * @throws SQLException
	 */
	public int sqlInsertLeagueBizCommon(ImportBizBean biz) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcExcelImportSQL.INSERT_LEAGUE_BIZ_COMMON);
		sqlmng.addValueReplacement("PRJTCD", biz.getPrjtcd());
		sqlmng.addValueReplacement("ACTCD", biz.getActcd());
		sqlmng.addValueReplacement("BIZCD", biz.getBizcd());
		sqlmng.addValueReplacement("SEQ", biz.getSeq());
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

		return dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table BIZ 등록(자문회사)
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param biz
	 * @return
	 * @throws SQLException
	 */
	public int sqlInsertLeagueBizConsult(ImportBizBean biz) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcExcelImportSQL.INSERT_LEAGUE_BIZ_CONSULT);
		sqlmng.addValueReplacement("PRJTCD", biz.getPrjtcd());
		sqlmng.addValueReplacement("ACTCD", biz.getActcd());
		sqlmng.addValueReplacement("BIZCD", biz.getBizcd());
		sqlmng.addValueReplacement("SEQ", biz.getSeq());
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

		return dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}

	/**
	 * <pre>
	 * ====================================================================================
	 * League Table Buy 거래유형1 등록
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param biz
	 * @return
	 * @throws SQLException
	 */
	public int sqlInsertLeagueDeal1(ImportBizBean biz) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcExcelImportSQL.INSERT_LEAGUE_DEAL1);
		sqlmng.addValueReplacement("PRJTCD", biz.getPrjtcd());
		sqlmng.addValueReplacement("DEALITEMCD", biz.getDealitemcd());
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		return dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}
	
	public ImportExceptCredentialBean sqlSelectCredentialCREDTGTCD(ImportExceptCredentialBean prjt) throws SQLException{
		SQLManagement sqlmng = new SQLManagement(DcExcelImportSQL.SELECT_CREDENTIAL_CREDTGTCD);
		sqlmng.addValueReplacement("PRJTCD", prjt.getPrjtcd());
		sqlmng.generate();
		ResultSet rs =  dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		ImportExceptCredentialBean data = null;
		if(rs.next()){
			data = new ImportExceptCredentialBean(prjt.getRow(), prjt.getPrjtcd());
			data.setCredtgtcd(rs.getString("CREDTGTCD"));
		}
		return data;
	}
	
	public int sqlUpdateCredentialCREDTGTCD(ImportExceptCredentialBean prjt) throws SQLException{
		SQLManagement sqlmng = new SQLManagement(DcExcelImportSQL.UPDATE_CREDENTIAL_CREDTGTCD);
		sqlmng.addValueReplacement("PRJTCD", prjt.getPrjtcd());
		sqlmng.addValueReplacement("CREDTGTCD", prjt.getCredtgtcd());
		sqlmng.generate();
		return dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}
	
	public void sqlDeleteLeagueForUpdate(Object project) throws SQLException {
		SQLManagement sqlmng = null;
		String prjtcd = "";
		if (project instanceof ImportBuyBean) {
			ImportBuyBean league = (ImportBuyBean) project;
			prjtcd = league.getPrjtcd();
			
		} else if (project instanceof ImportMnaBean) {
			ImportMnaBean league = (ImportMnaBean) project;
			prjtcd = league.getPrjtcd();
			
		} else if (project instanceof ImportRealBean) {
			ImportRealBean league = (ImportRealBean) project;
			prjtcd = league.getPrjtcd();
		}
		
		// Buy 관련 테이블 삭제
		sqlmng = new SQLManagement(DcExcelImportSQL.DELETE_LEAGUE_BUY);
		sqlmng.addValueReplacement("PRJTCD", prjtcd);
		sqlmng.generate();
		dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
		
		sqlmng = new SQLManagement(DcExcelImportSQL.DELETE_LEAGUE_BUY_DEAL1);
		sqlmng.addValueReplacement("PRJTCD", prjtcd);
		sqlmng.generate();
		dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
		
		// M&A 관련 테이블 삭제
		sqlmng = new SQLManagement(DcExcelImportSQL.DELETE_LEAGUE_MNA);
		sqlmng.addValueReplacement("PRJTCD", prjtcd);
		sqlmng.generate();
		dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
		
		// Real 관련 테이블 삭제
		sqlmng = new SQLManagement(DcExcelImportSQL.DELETE_LEAGUE_REAL);
		sqlmng.addValueReplacement("PRJTCD", prjtcd);
		sqlmng.generate();
		dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
		
		// Biz 관련 테이블 삭제
		sqlmng = new SQLManagement(DcExcelImportSQL.DELETE_LEAGUE_BIZ_ALL);
		sqlmng.addValueReplacement("PRJTCD", prjtcd);
		sqlmng.generate();
		dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}
	
	/*
	public void sqlDeleteLeagueForUpdate(Object project) throws SQLException {
		SQLManagement sqlmng = null;
		String prjtcd = "";
		if (project instanceof ImportBuyBean) {
			ImportBuyBean league = (ImportBuyBean) project;
			prjtcd = league.getPrjtcd();
			
			sqlmng = new SQLManagement(DcExcelImportSQL.DELETE_LEAGUE_BUY);
			sqlmng.addValueReplacement("PRJTCD", prjtcd);
			sqlmng.generate();
			dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
			
			sqlmng = new SQLManagement(DcExcelImportSQL.DELETE_LEAGUE_BUY_DEAL1);
			sqlmng.addValueReplacement("PRJTCD", prjtcd);
			sqlmng.generate();
			dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
			
		} else if (project instanceof ImportMnaBean) {
			ImportMnaBean league = (ImportMnaBean) project;
			prjtcd = league.getPrjtcd();
			
			sqlmng = new SQLManagement(DcExcelImportSQL.DELETE_LEAGUE_MNA);
			sqlmng.addValueReplacement("PRJTCD", prjtcd);
			sqlmng.generate();
			dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
			
		} else if (project instanceof ImportRealBean) {
			ImportRealBean league = (ImportRealBean) project;
			prjtcd = league.getPrjtcd();
			
			sqlmng = new SQLManagement(DcExcelImportSQL.DELETE_LEAGUE_REAL);
			sqlmng.addValueReplacement("PRJTCD", prjtcd);
			sqlmng.generate();
			dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
		}
		
		sqlmng = new SQLManagement(DcExcelImportSQL.DELETE_LEAGUE_BIZ_ALL);
		sqlmng.addValueReplacement("PRJTCD", prjtcd);
		sqlmng.generate();
		dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}
	*/
}
