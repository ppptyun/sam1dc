package com.samil.dc.service.worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcLeagueTableDAO;
import com.samil.dc.domain.DcLtSetupConsultListBean;
import com.samil.dc.domain.UserRoleBeans;
import com.samil.dc.domain.UserSessionBeans;
import com.samil.dc.sql.DcLeagueTableSQL;
import com.samil.dc.sql.SQLManagement;
import com.samil.dc.util.Constants;
import com.samil.dc.util.SessionUtil;

/**
 * <pre>
 * ====================================================================================
 * 자문 형태 지정 목록 조회
 * 
 *  * 권한에 따라 조회 범위가 다름
 *   - Engagement Team
 *     : 본인이 담당하는 것들 조회
 *     : LT대상여부 Yes/No만 조회
 *   - 본부 담당자
 *     : 권한이 있는 본부의 프로젝트를 조회
 *     : LT대상여부 Yes/No만 조회
 *   - 관리자
 *     : 전체 프로젝트 조회
 *     : LT대상여부 Yes/No/미분류 조회
 * ====================================================================================
 * </pre>
 */
public class DcLtSetupConsultTypeListServiceWorker extends AbstractServiceWorker {
	
	private final static int ROLE_MANAGER = 1;
	private final static int ROLE_HQ_ET = 2;
	private final static int ROLE_HQ = 3;
	private final static int ROLE_ET = 4;
	private final static int ROLE_ETC = 9;

	public DcLtSetupConsultTypeListServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
		super(request, response, connection);
	}

	@Override
	public ValidationResult validate() throws Exception {
		// 사용자 검증
		ValidationResult validationResult = ServiceHelper.validateUser(request);
		if (!validationResult.isValid()) {
			return validationResult;
		}
		
		return new ValidationResult(true);
	}

	@Override
	public Object process() throws Exception {
		UserSessionBeans userSession = SessionUtil.getUserSession(request);
		String userEmpNo = userSession.getUserBeans().getEMPLNO();
		
		DcLeagueTableDAO leagueDAO = new DcLeagueTableDAO(connection);
		List<DcLtSetupConsultListBean> list = new ArrayList<DcLtSetupConsultListBean>();
		try {
			int roleLevel = getUserRoleLevel(userSession.getUserRoleList());
			switch (roleLevel) {
			case ROLE_MANAGER:
				list = leagueDAO.sqlSelectSetupConsultTypeList(null, null, null);
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
				hqetCond.append("          LT.PTREMPNO  = <#EMPNO#>");
				hqetCond.append("          OR");
				hqetCond.append("          LT.MGREMPNO  = <#EMPNO#>");
				hqetCond.append("     ) ");
				hqetCond.append(" ) ");
				list = leagueDAO.sqlSelectSetupConsultTypeList(hqetCond.toString(), "EMPNO", userEmpNo);
//				list = leagueDAO.sqlSelectSetupConsultTypeList(hqetCond.toString());
				break;
			case ROLE_HQ:
				StringBuffer hqCond = new StringBuffer();
				hqCond.append("AND HQCHR.PTRHQCD IN ( ");
				hqCond.append("     SELECT HE.HQCD FROM WEB_DCHQEMP HE ");
				hqCond.append("      WHERE HE.EMPNO = <#EMPNO#>");
				hqCond.append("    ) ");
				list = leagueDAO.sqlSelectSetupConsultTypeList(hqCond.toString(), "EMPNO", userEmpNo);
//				list = leagueDAO.sqlSelectSetupConsultTypeList(hqCond.toString());
				break;
			case ROLE_ET:
				StringBuffer etCond = new StringBuffer();
				etCond.append("AND ( ");
				etCond.append("       LT.PTREMPNO = <#EMPNO#> ");
				etCond.append("       OR");
				etCond.append("       LT.MGREMPNO = <#EMPNO#> ");
				etCond.append("    ) ");
				list = leagueDAO.sqlSelectSetupConsultTypeList(etCond.toString(), "EMPNO", userEmpNo);
//				list = leagueDAO.sqlSelectSetupConsultTypeList(etCond.toString());
				break;
			case ROLE_ETC:
				list = new ArrayList<DcLtSetupConsultListBean>();
				break;
			}
			
			
			if (list == null) {
				list = new ArrayList<DcLtSetupConsultListBean>();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("list", list);

		return data;
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
}
