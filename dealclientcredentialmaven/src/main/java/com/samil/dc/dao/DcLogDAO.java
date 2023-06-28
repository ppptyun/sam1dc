package com.samil.dc.dao;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.samil.dc.access.DBConnection;
import com.samil.dc.domain.DcLogBean;
import com.samil.dc.sql.DcLogSQL;
import com.samil.dc.sql.SQLManagement;
import com.samil.dc.util.Constants;

public class DcLogDAO {

	private static final Logger logger = Logger.getRootLogger();
	private DBConnection m_dbcon = null;

	public DcLogDAO(DBConnection _con) {
		m_dbcon = _con;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 일반 로그
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param logList
	 */
	public void sqlLogCommon(List<DcLogBean> logList) {
		try {
			for (DcLogBean log : logList) {
				SQLManagement sqlmng = new SQLManagement(DcLogSQL.INSERT_PUBLIC_LOG);
				sqlmng.addValueReplacement("LOGCD", StringUtils.defaultString(log.getLogcd()));
				sqlmng.addValueReplacement("ALTCD", StringUtils.defaultString(log.getAltcd()));
				sqlmng.addValueReplacement("FILDNM", StringUtils.defaultString(log.getFildnm()));
				sqlmng.addValueReplacement("FILDCD", StringUtils.defaultString(log.getFildcd()));
				sqlmng.addValueReplacement("BEFVAL", StringUtils.defaultString(log.getBefval()));
				sqlmng.addValueReplacement("ALTVAL", StringUtils.defaultString(log.getAltval()));
				sqlmng.addValueReplacement("PRJTCD", StringUtils.defaultString(log.getPrjtcd()));
				sqlmng.addValueReplacement("BIGO", StringUtils.defaultString(log.getBigo()));
				sqlmng.addValueReplacement("ALTEMPNO", StringUtils.defaultString(log.getAltempno()));
				sqlmng.generate();
				if (logger.isDebugEnabled()) {
					logger.debug(sqlmng.getSql());
				}
	
				m_dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 일반 로그
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param log
	 */
	public void sqlLogCommon(DcLogBean log) {
		try {
			SQLManagement sqlmng = new SQLManagement(DcLogSQL.INSERT_PUBLIC_LOG);
			sqlmng.addValueReplacement("LOGCD", StringUtils.defaultString(log.getLogcd()));
			sqlmng.addValueReplacement("ALTCD", StringUtils.defaultString(log.getAltcd()));
			sqlmng.addValueReplacement("FILDNM", StringUtils.defaultString(log.getFildnm()));
			sqlmng.addValueReplacement("FILDCD", StringUtils.defaultString(log.getFildcd()));
			sqlmng.addValueReplacement("BEFVAL", StringUtils.defaultString(log.getBefval()));
			sqlmng.addValueReplacement("ALTVAL", StringUtils.defaultString(log.getAltval()));
			sqlmng.addValueReplacement("PRJTCD", StringUtils.defaultString(log.getPrjtcd()));
			sqlmng.addValueReplacement("BIGO", StringUtils.defaultString(log.getBigo()));
			sqlmng.addValueReplacement("ALTEMPNO", StringUtils.defaultString(log.getAltempno()));
			sqlmng.generate();
			if (logger.isDebugEnabled()) {
				logger.debug(sqlmng.getSql());
			}

			m_dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * <pre>
	 * ====================================================================================
	 * 일반 로그
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param logcd
	 * @param altcd
	 * @param fildnm
	 * @param fildcd
	 * @param befval
	 * @param altval
	 * @param prjtcd
	 * @param bigo
	 * @param altempno
	 */
	public void sqlLogCommon(String logcd, String altcd, String fildnm, String fildcd, String befval, String altval, String prjtcd, String bigo, String altempno) {
		try {
			SQLManagement sqlmng = new SQLManagement(DcLogSQL.INSERT_PUBLIC_LOG);
			sqlmng.addValueReplacement("LOGCD", StringUtils.isBlank(logcd) ? "" : logcd);
			sqlmng.addValueReplacement("ALTCD", StringUtils.isBlank(altcd) ? "" : altcd);
			sqlmng.addValueReplacement("FILDNM", StringUtils.isBlank(fildnm) ? "" : fildnm);
			sqlmng.addValueReplacement("FILDCD", StringUtils.isBlank(fildcd) ? "" : fildcd);
			sqlmng.addValueReplacement("BEFVAL", StringUtils.isBlank(befval) ? "" : befval);
			sqlmng.addValueReplacement("ALTVAL", StringUtils.isBlank(altval) ? "" : altval);
			sqlmng.addValueReplacement("PRJTCD", StringUtils.isBlank(prjtcd) ? "" : prjtcd);
			sqlmng.addValueReplacement("BIGO", StringUtils.isBlank(bigo) ? "" : bigo);
			sqlmng.addValueReplacement("ALTEMPNO", StringUtils.isBlank(altempno) ? "" : altempno);
			sqlmng.generate();
			if (logger.isDebugEnabled()) {
				logger.debug(sqlmng.getSql());
			}

			m_dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 자문 형태 지정 로그
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param prjtcd
	 * @param conscd
	 * @param empno
	 */
	public void sqlLogConsultTypeSave(String prjtcd, String conscd, String empno) {
		try {
			SQLManagement sqlmng = new SQLManagement(DcLogSQL.INSERT_LEAGUE_CONSULT_TYPE_CHANGE_LOG);
			sqlmng.addValueReplacement("PRJTCD", prjtcd);
			sqlmng.addValueReplacement("LOGCD", Constants.LogType.SETUP_CONSULT_TYPE);
			sqlmng.addValueReplacement("ALTCD", Constants.LogMethod.UPDATE);
			sqlmng.addValueReplacement("ALTVAL", conscd);
			sqlmng.addValueReplacement("ALTEMPNO", empno);
			sqlmng.generate();
			if (logger.isDebugEnabled()) {
				logger.debug(sqlmng.getSql());
			}

			m_dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League 기업 정보 등록 로그
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param prjtcd
	 * @param bigo
	 * @param altempno
	 * @param actcd
	 * @param bizcd
	 */
	public void sqlLogLeagueBiz(String prjtcd, String bigo, String altempno, String actcd, String bizcd) {
		try {
			SQLManagement sqlmng = new SQLManagement(DcLogSQL.INSERT_LEAGUE_BIZ_LOG);
			
			sqlmng.addValueReplacement("LOGCD", Constants.LogType.SETUP_CONSULT_TYPE);
			sqlmng.addValueReplacement("ALTCD", Constants.LogMethod.INSERT);
			sqlmng.addValueReplacement("PRJTCD", StringUtils.isBlank(prjtcd) ? "" : prjtcd);
			sqlmng.addValueReplacement("BIGO", StringUtils.isBlank(bigo) ? "" : bigo);
			sqlmng.addValueReplacement("ALTEMPNO", StringUtils.isBlank(altempno) ? "" : altempno);
			sqlmng.addValueReplacement("ACTCD", actcd);
			sqlmng.addValueReplacement("BIZCD", bizcd);
			sqlmng.generate();
			if (logger.isDebugEnabled()) {
				logger.debug(sqlmng.getSql());
			}

			m_dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League 담당자 확인 로그
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param prjtcd
	 * @param lttgtcd
	 * @param empno
	 */
	public void sqlLogConsultConfirmSave(String prjtcd, String chrgconfcd, String bigo, String empno) {
		try {
			SQLManagement sqlmng = new SQLManagement(DcLogSQL.INSERT_LEAGUE_CONSULT_CONFIRM_LOG);
			sqlmng.addValueReplacement("PRJTCD", prjtcd);
			sqlmng.addValueReplacement("LOGCD", Constants.LogType.LEAGUE_TABLE);
			sqlmng.addValueReplacement("ALTCD", Constants.LogMethod.UPDATE);
			sqlmng.addValueReplacement("ALTVAL", chrgconfcd);
			sqlmng.addValueReplacement("BIGO", bigo);
			sqlmng.addValueReplacement("ALTEMPNO", empno);
			sqlmng.generate();
			if (logger.isDebugEnabled()) {
				logger.debug(sqlmng.getSql());
			}

			m_dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League ET 확인 로그
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param prjtcd
	 * @param lttgtcd
	 * @param empno
	 */
	public void sqlLogConsultETConfirmSave(String prjtcd, String bigo, String empno) {
		try {
			SQLManagement sqlmng = new SQLManagement(DcLogSQL.INSERT_LEAGUE_CONSULT_ET_CONFIRM_LOG);
			sqlmng.addValueReplacement("PRJTCD", prjtcd);
			sqlmng.addValueReplacement("LOGCD", Constants.LogType.LEAGUE_TABLE);
			sqlmng.addValueReplacement("ALTCD", Constants.LogMethod.UPDATE);
			sqlmng.addValueReplacement("BIGO", bigo);
			sqlmng.addValueReplacement("ALTEMPNO", empno);
			sqlmng.generate();
			if (logger.isDebugEnabled()) {
				logger.debug(sqlmng.getSql());
			}

			m_dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League 프로젝트 대상 변경 로그
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param prjtcd
	 * @param lttgtcd
	 * @param empno
	 */
	public void sqlLogProjectTargetSave(String prjtcd, String lttgtcd, String empno) {
		try {
			SQLManagement sqlmng = new SQLManagement(DcLogSQL.INSERT_LEAGUE_PROJECT_TARGET_LOG);
			sqlmng.addValueReplacement("PRJTCD", prjtcd);
			sqlmng.addValueReplacement("LOGCD", Constants.LogType.SETUP_PROJECT_TARGET);
			sqlmng.addValueReplacement("ALTCD", Constants.LogMethod.UPDATE);
			sqlmng.addValueReplacement("ALTVAL", lttgtcd);
			sqlmng.addValueReplacement("ALTEMPNO", empno);
			sqlmng.generate();
			if (logger.isDebugEnabled()) {
				logger.debug(sqlmng.getSql());
			}

			m_dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * Deal 본부 목록 동기화(From View) 로그
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param log
	 */
	public void sqlLogHQEmpSync(String creempno) {
		DcLogBean log = new DcLogBean(Constants.LogType.HQ_EMP_MANAGE);
		log.setAltcd(Constants.LogMethod.UPDATE);
		log.setFildnm("");
		log.setFildcd("");
		log.setBefval("");
		log.setAltval("");
		log.setPrjtcd("");
		log.setBigo("본부목록 동기화");
		log.setAltempno(creempno);
		sqlLogCommon(log);
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 본부 담당자 등록 로그
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param log
	 */
	public void sqlLogHQEmpAdd(String hqcd, String empno, String creempno) {
		DcLogBean log = new DcLogBean(Constants.LogType.HQ_EMP_MANAGE);
		log.setAltcd(Constants.LogMethod.INSERT);
		log.setFildnm("본부담당자사번");
		log.setFildcd("empno");
		log.setBefval("");
		log.setAltval(empno);
		log.setPrjtcd("");
		log.setBigo("본부[" + hqcd +"] 담당자 신규");
		log.setAltempno(creempno);
		sqlLogCommon(log);
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 본부 담당자 삭제 로그
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param log
	 */
	public void sqlLogHQEmpDel(String hqcd, String empno, String creempno) {
		DcLogBean log = new DcLogBean(Constants.LogType.HQ_EMP_MANAGE);
		log.setAltcd(Constants.LogMethod.DELETE);
		log.setFildnm("본부담당자사번");
		log.setFildcd("empno");
		log.setBefval(empno);
		log.setAltval("");
		log.setPrjtcd("");
		log.setBigo("본부[" + hqcd +"] 담당자 삭제");
		log.setAltempno(creempno);
		sqlLogCommon(log);
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table 편집 관리 등록 로그
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param log
	 */
	public void sqlLogEditAuthNew(String rolecd, String edityn, String creempno) {
		DcLogBean log = new DcLogBean(Constants.LogType.LT_EDIT_MANAGE);
		log.setAltcd(Constants.LogMethod.INSERT);
		log.setFildnm("편집롤코드|여부");
		log.setFildcd("rolecd|edityn");
		log.setBefval("");
		log.setAltval(rolecd + "|" + edityn);
		log.setPrjtcd("");
		log.setBigo("League Table 편집 관리 신규");
		log.setAltempno(creempno);
		sqlLogCommon(log);
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table 편집 관리 수정 로그
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param log
	 */
	public void sqlLogEditAuthUpd(String bef_rolecd, String bef_edityn, String alt_rolecd, String alt_edityn, String creempno) {
		DcLogBean log = new DcLogBean(Constants.LogType.LT_EDIT_MANAGE);
		log.setAltcd(Constants.LogMethod.UPDATE);
		log.setFildnm("편집롤코드|여부");
		log.setFildcd("rolecd|edityn");
		log.setBefval(bef_rolecd + "|" + bef_edityn);
		log.setAltval(alt_rolecd + "|" + alt_edityn);
		log.setPrjtcd("");
		log.setBigo("League Table 편집 관리 수정");
		log.setAltempno(creempno);
		sqlLogCommon(log);
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table 편집 관리 삭제 로그
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param log
	 */
	public void sqlLogEditAuthDel(String rolecd, String creempno) {
		DcLogBean log = new DcLogBean(Constants.LogType.LT_EDIT_MANAGE);
		log.setAltcd(Constants.LogMethod.DELETE);
		log.setFildnm("편집롤코드");
		log.setFildcd("rolecd");
		log.setBefval(rolecd);
		log.setAltval("");
		log.setPrjtcd("");
		log.setBigo("League Table 편집 관리 삭제");
		log.setAltempno(creempno);
		sqlLogCommon(log);
	}
}
