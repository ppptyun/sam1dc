package com.samil.dc.service.worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcLeagueTableDAO;
import com.samil.dc.domain.DcLtRealListBean;
import com.samil.dc.domain.UserSessionBeans;
import com.samil.dc.util.Constants;
import com.samil.dc.util.SessionUtil;

/**
 * <pre>
 * ====================================================================================
 * 부동산 자문 목록 조회
 * 
 * 권한에 따라 조회 범위가 다름
 *   - Engagement Team
 *     : 본인이 담당하는 것들 조회
 *   - 본부 담당자
 *     : 권한이 있는 본부의 프로젝트를 조회
 *   - 관리자
 *     : 전체 프로젝트 조회
 * ====================================================================================
 * </pre>
 */
public class DcLtConsultRealListServiceWorker extends AbstractServiceWorker {

	public DcLtConsultRealListServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
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
		List<DcLtRealListBean> list = new ArrayList<DcLtRealListBean>();
		try {
			int roleLevel = ServiceHelper.getUserLeagueRoleLevel(userSession.getUserRoleList());
			switch (roleLevel) {
			case Constants.LeagueRoleLevel.ROLE_MANAGER:
				list = leagueDAO.sqlSelectConsultRealList(null, null, null);
				break;
			case Constants.LeagueRoleLevel.ROLE_HQ_ET:
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
				list = leagueDAO.sqlSelectConsultRealList(hqetCond.toString(), "EMPNO", userEmpNo);
				break;
			case Constants.LeagueRoleLevel.ROLE_HQ:
				StringBuffer hqCond = new StringBuffer();
				hqCond.append("AND HQCHR.PTRHQCD IN ( ");
				hqCond.append("     SELECT HE.HQCD FROM WEB_DCHQEMP HE ");
				hqCond.append("      WHERE HE.EMPNO = <#EMPNO#>");
				hqCond.append("    ) ");
				list = leagueDAO.sqlSelectConsultRealList(hqCond.toString(), "EMPNO", userEmpNo);
				break;
			case Constants.LeagueRoleLevel.ROLE_ET:
				StringBuffer etCond = new StringBuffer();
				etCond.append("AND ( ");
				etCond.append("       LT.PTREMPNO = <#EMPNO#>");
				etCond.append("       OR");
				etCond.append("       LT.MGREMPNO = <#EMPNO#>");
				etCond.append("    ) ");
				list = leagueDAO.sqlSelectConsultRealList(etCond.toString(), "EMPNO", userEmpNo);
				break;
			case Constants.LeagueRoleLevel.ROLE_ETC:
				list = new ArrayList<DcLtRealListBean>();
				break;
			}
			if (list == null) {
				list = new ArrayList<DcLtRealListBean>();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("list", list);

		return data;
	}
}
