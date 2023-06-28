package com.samil.dc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.samil.dc.access.DBConnection;
import com.samil.dc.domain.AdminFieldLogListBean;
import com.samil.dc.sql.DcAdminFieldLogSQL;
import com.samil.dc.sql.SQLManagement;

public class DcAdminFieldLogDAO {

	private static final Logger logger = Logger.getRootLogger();
	private DBConnection m_dbcon = null;

	public DcAdminFieldLogDAO(DBConnection _con) {
		m_dbcon = _con;
	}

	public Integer sqlSelectFieldLogListCount(String prjtcd, String logcd, String altcd, String empno) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcAdminFieldLogSQL.SELECT_FIELD_LOG_LIST_COUNT);
		sqlmng = doMakeSearchQuery(sqlmng, prjtcd, logcd, altcd, empno);
		sqlmng.generate(true);
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		Integer cnt = 0;
		ResultSet rs = m_dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs != null) {
			while (rs.next()) {
				cnt = Integer.valueOf(StringUtils.defaultIfBlank(rs.getString("CNT"), "0"));
			}
		}

		return cnt;
	}

	public List<AdminFieldLogListBean> sqlSelectFieldLogList(int posStart, int count, String prjtcd, String logcd, String altcd, String empno) throws SQLException {
		List<AdminFieldLogListBean> list = new ArrayList<AdminFieldLogListBean>();

		SQLManagement sqlmng = new SQLManagement(DcAdminFieldLogSQL.SELECT_FIELD_LOG_LIST);
		sqlmng = doMakeSearchQuery(sqlmng, prjtcd, logcd, altcd, empno);
		sqlmng.addValueReplacement("PAGE_START", posStart);
		sqlmng.addValueReplacement("PAGE_END", posStart + count);
		sqlmng.generate(true);
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		ResultSet rs = m_dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs != null) {
			while (rs.next()) {
				AdminFieldLogListBean log = new AdminFieldLogListBean();
				log.setSeq(StringUtils.defaultIfBlank(rs.getString("SEQ"), ""));
				log.setPrjtcd(StringUtils.defaultIfBlank(rs.getString("PRJTCD"), ""));
				log.setFildcd(StringUtils.defaultIfBlank(rs.getString("FILDCD"), ""));
				log.setFildnm(StringUtils.defaultIfBlank(rs.getString("FILDNM"), ""));
				log.setLogcd(StringUtils.defaultIfBlank(rs.getString("LOGCD"), ""));
				log.setLognm(StringUtils.defaultIfBlank(rs.getString("LOGNM"), ""));
				log.setAltcd(StringUtils.defaultIfBlank(rs.getString("ALTCD"), ""));
				log.setAltnm(StringUtils.defaultIfBlank(rs.getString("ALTNM"), ""));
				log.setBefval(StringUtils.defaultIfBlank(rs.getString("BEFVAL"), ""));
				log.setAltval(StringUtils.defaultIfBlank(rs.getString("ALTVAL"), ""));
				log.setAltempno(StringUtils.defaultIfBlank(rs.getString("ALTEMPNO"), ""));
				log.setAltempnm(StringUtils.defaultIfBlank(rs.getString("ALTEMPNM"), ""));
				log.setAltgradnm(StringUtils.defaultIfBlank(rs.getString("ALTGRADNM"), ""));
				log.setAltdt(StringUtils.defaultIfBlank(rs.getString("ALTDT"), ""));
				log.setBigo(StringUtils.defaultIfBlank(rs.getString("BIGO"), ""));
				log.setAltemp(log.getAltempnm() + "(" + log.getAltempno() + ") / " + log.getAltgradnm());
				list.add(log);
			}
		}

		return list;
	}

	private SQLManagement doMakeSearchQuery(SQLManagement sqlmng, String prjtcd, String logcd, String altcd, String empno) {
		if (!StringUtils.isBlank(prjtcd)) {
			sqlmng.addSubSQLReplacement("SEARCH_CONDITION", " AND A.PRJTCD LIKE <#PRJTCD#>", "PRJTCD", prjtcd + "%");
		}
		if (!StringUtils.isBlank(logcd)) {
			sqlmng.addSubSQLReplacement("SEARCH_CONDITION", " AND A.LOGCD = <#LOGCD#>", "LOGCD", logcd);
		}
		if (!StringUtils.isBlank(altcd)) {
			sqlmng.addSubSQLReplacement("SEARCH_CONDITION", " AND A.ALTCD = <#ALTCD#>", "ALTCD", altcd);
		}
		if (!StringUtils.isBlank(empno)) {
			sqlmng.addSubSQLReplacement("SEARCH_CONDITION", " AND A.ALTEMPNO = <#ALTEMPNO#>", "ALTEMPNO", empno);
		}
		return sqlmng;
	}
}
