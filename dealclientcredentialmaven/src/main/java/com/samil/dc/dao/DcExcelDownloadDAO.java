package com.samil.dc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.samil.dc.access.DBConnection;
import com.samil.dc.domain.DcCredentialSearchConditionBean;
import com.samil.dc.domain.UserRoleBeans;
import com.samil.dc.domain.UserSessionBeans;
import com.samil.dc.service.worker.ServiceHelper;
import com.samil.dc.sql.DcExcelDownloadSQL;
import com.samil.dc.sql.SQLManagement;
import com.samil.dc.util.Constants;
import com.samil.dc.util.SessionUtil;


public class DcExcelDownloadDAO {
	private static final Logger logger = Logger.getRootLogger();
	
	private final static int ROLE_MANAGER = 1;
	private final static int ROLE_HQ_ET = 2;
	private final static int ROLE_HQ = 3;
	private final static int ROLE_ET = 4;
	private final static int ROLE_ETC = 9;
	
	public ResultSet sqlSelectCredential(HttpServletRequest request, DBConnection dbcon) throws SQLException {
		ResultSet rs = null;
		DcCredentialSearchConditionBean searchCondition = ServiceHelper.parseSearchCondition(request);
		SQLManagement sqlmng = ServiceHelper.getCredentialListSQL(DcExcelDownloadSQL.SELECT_CREDENTIAL_LIST, searchCondition);
		logger.debug(sqlmng.getSql());
		rs = dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		return rs;
	}
	
	
	public ResultSet sqlSelectBuyLeagueTable(HttpServletRequest request, DBConnection dbcon) throws SQLException {
		//----------- [2019.01.24 남웅주] -----------------------------------------------		
		String actcdBizcd = request.getParameter("ActcdBizcd");
		HashMap<String, Object> keyValueMap = new HashMap<String, Object>();
		StringBuffer searchSubSQL = new StringBuffer();
		String[] actcdBizcdList = null; 

		if(actcdBizcd != null && !"".equals(actcdBizcd)) {
			actcdBizcdList = actcdBizcd.split("-");
			keyValueMap.put("ACTCD", actcdBizcdList[0]);
			keyValueMap.put("BIZCD", actcdBizcdList[1]);				
			keyValueMap.put("COMPCD", actcdBizcdList[2]);
			
			searchSubSQL.append("AND EXISTS 						");
			searchSubSQL.append("	(SELECT 1 						");
			searchSubSQL.append("      FROM WEB_DCLTBIZ Z 			");
			searchSubSQL.append("     WHERE BU.PRJTCD = Z.PRJTCD 	");
			searchSubSQL.append("       AND Z.ACTCD = <#ACTCD#>		");
			searchSubSQL.append("		AND Z.BIZCD = <#BIZCD#>		");
			searchSubSQL.append("		AND Z.COMPCD = <#COMPCD#>	");
			searchSubSQL.append("	)  ");
		}
		//----------- [2019.01.24 남웅주] -----------------------------------------------				
		
		ResultSet rs = null;
		SQLManagement sqlmng = new SQLManagement(DcExcelDownloadSQL.SELECT_CONSULT_BUY_LIST);
		
		//----------- [2019.01.24 남웅주] -----------------------------------------------						
		if(searchSubSQL.length() > 0){
			sqlmng.addSubSQLReplacement("SQL_CONDITION", searchSubSQL.toString(), keyValueMap);	
		}	
		sqlmng.generate(true);		
		rs = dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		//----------- [2019.01.24 남웅주] -----------------------------------------------				
		return rs;
	}
	
	public ResultSet sqlSelectMnaLeagueTable(HttpServletRequest request, DBConnection dbcon) throws SQLException {
		ResultSet rs = null;
		SQLManagement sqlmng = new SQLManagement(DcExcelDownloadSQL.SELECT_CONSULT_MNA_LIST);
		sqlmng.generate();
		rs = dbcon.executeQuery(sqlmng.getSql());
		return rs;
	}
	
	public ResultSet sqlSelectRealLeagueTable(HttpServletRequest request, DBConnection dbcon) throws SQLException {
		ResultSet rs = null;
		SQLManagement sqlmng = new SQLManagement(DcExcelDownloadSQL.SELECT_CONSULT_REAL_LIST);
		sqlmng.generate();
		rs = dbcon.executeQuery(sqlmng.getSql());
		return rs;
	}
	
	//----------- [2020.03.23 남웅주] -----------------------------------------------	
	public ResultSet sqlSelectProjectList(HttpServletRequest request, DBConnection dbcon) throws SQLException {
		UserSessionBeans userSession = SessionUtil.getUserSession(request);
		String userEmpNo = userSession.getUserBeans().getEMPLNO();
		ResultSet rs = null;
		SQLManagement sqlmng = new SQLManagement(DcExcelDownloadSQL.SELECT_PROJECT_LIST);

		try {
			int roleLevel = getUserRoleLevel(userSession.getUserRoleList());
			switch (roleLevel) {
			case ROLE_MANAGER:
				sqlmng.addSubSQLReplacement("SQL_CONDITION", "", null, null);
				sqlmng.generate();
				
				System.out.println(sqlmng.getSql());
				
				rs = dbcon.executeQuery(sqlmng.getSql());				
				break;
			case ROLE_HQ_ET:
				StringBuffer hqetCond = new StringBuffer();
				hqetCond.append("AND ( ");
				hqetCond.append("     ( ");
				hqetCond.append("          HQCHR.PTRHQCD IN ( ");
				hqetCond.append("              SELECT HE.HQCD FROM WEB_DCHQEMP HE ");
				hqetCond.append("               WHERE HE.EMPNO = <#EMPNO#>");
				hqetCond.append("          ) ");
				hqetCond.append("     ) ");
				hqetCond.append("     OR ");
				hqetCond.append("     ( ");
				hqetCond.append("          LT.PTREMPNO = <#EMPNO#>");
				hqetCond.append("          OR");
				hqetCond.append("          LT.MGREMPNO = <#EMPNO#>");
				hqetCond.append("     ) ");
				hqetCond.append(" ) ");
				hqetCond.append("AND ( ");
				hqetCond.append("       LT.LTTGTCD = '101001'");
				hqetCond.append("       OR");
				hqetCond.append("       LT.LTTGTCD = '101002'");
				hqetCond.append("    ) ");
				sqlmng.addSubSQLReplacement("SQL_CONDITION", hqetCond.toString(), "EMPNO", userEmpNo);
				sqlmng.generate();
				rs = dbcon.executeQuery(sqlmng.getSql());
				break;
			case ROLE_HQ:
				StringBuffer hqCond = new StringBuffer();
				hqCond.append("AND HQCHR.PTRHQCD IN ( ");
				hqCond.append("     SELECT HE.HQCD FROM WEB_DCHQEMP HE ");
				hqCond.append("      WHERE HE.EMPNO = <#EMPNO#>");
				hqCond.append("    ) ");
				hqCond.append("AND ( ");
				hqCond.append("       LT.LTTGTCD = '101001'");
				hqCond.append("       OR");
				hqCond.append("       LT.LTTGTCD = '101002'");
				hqCond.append("    ) ");
				sqlmng.addSubSQLReplacement("SQL_CONDITION", hqCond.toString(), "EMPNO", userEmpNo);
				sqlmng.generate();
				rs = dbcon.executeQuery(sqlmng.getSql());				
				break;
			case ROLE_ET:
				StringBuffer etCond = new StringBuffer();
				etCond.append("AND ( ");
				etCond.append("       LT.PTREMPNO = <#EMPNO#>");
				etCond.append("       OR");
				etCond.append("       LT.MGREMPNO = <#EMPNO#>");
				etCond.append("    ) ");
				etCond.append("AND ( ");
				etCond.append("       LT.LTTGTCD = '101001'");
				etCond.append("       OR");
				etCond.append("       LT.LTTGTCD = '101002'");
				etCond.append("    ) ");
				sqlmng.addSubSQLReplacement("SQL_CONDITION", etCond.toString(), "EMPNO", userEmpNo);
				sqlmng.generate();
				rs = dbcon.executeQuery(sqlmng.getSql());				
				break;
			case ROLE_ETC:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	/**
	 * 
	 * 목록 조회를 위한 사용자 롤 결정
	 * 
	 */
	private int getUserRoleLevel(List<UserRoleBeans> roleList) {
		// 관리자 여부[IT/System 포함]
		for (UserRoleBeans role : roleList) {
			String rolecd = role.getRolecd();
			if (Constants.Role.R03.equals(rolecd) || Constants.Role.IT.equals(rolecd) || Constants.Role.SYSTEM.equals(rolecd)) {
				return ROLE_MANAGER;
			}
		}

		// 본부 담당자 && Engagement Team
		boolean r04 = false;
		boolean r05 = false;
		for (UserRoleBeans role : roleList) {
			String rolecd = role.getRolecd();
			if (Constants.Role.R04.equals(rolecd)) {
				r04 = true;
			}
			if (Constants.Role.R05.equals(rolecd)) {
				r05 = true;
			}
		}
		if (r04 && r05) {
			return ROLE_HQ_ET;
		}

		// 본부 담당자
		if (!r04 && r05) {
			return ROLE_HQ;
		}

		// Engagement Team
		if (r04 && !r05) {
			return ROLE_ET;
		}

		// 기타
		return ROLE_ETC;
	}	
	//----------- [2020.03.23 남웅주] -----------------------------------------------	
	
}
