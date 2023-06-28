package com.samil.dc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.samil.dc.access.DBConnection;
import com.samil.dc.domain.DcCredentialSearchConditionBean;
import com.samil.dc.service.worker.ServiceHelper;
import com.samil.dc.sql.DcExcelDownloadSQL;
import com.samil.dc.sql.SQLManagement;


public class DcPptDownloadDAO {
	private static final Logger logger = Logger.getRootLogger();
	
	public ResultSet sqlSelectCredential(HttpServletRequest request, DBConnection dbcon) throws SQLException {
		ResultSet rs = null;
		DcCredentialSearchConditionBean searchCondition = ServiceHelper.parseSearchCondition(request);
		SQLManagement sqlmng = ServiceHelper.getCredentialListSQL(DcExcelDownloadSQL.SELECT_CREDENTIAL_LIST, searchCondition);
		logger.debug(sqlmng.getSql());
		rs = dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		return rs;
	}
}
