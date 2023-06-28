package com.samil.dc.service.worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcLeagueTableDAO;
import com.samil.dc.domain.DcLtBuyListBean;
import com.samil.dc.domain.UserSessionBeans;
import com.samil.dc.util.Constants;
import com.samil.dc.util.SessionUtil;

/**
 * <pre>
 * ====================================================================================
 * 인수/매각 자문 목록 조회
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
public class DcLtConsultBuyListServiceWorker extends AbstractServiceWorker {
	//----------- [2019.01.24 남웅주] -----------------------------------------------
	private final static String PARAM_ACTCDBIZCD = "actcdbizcd";
	//----------- [2019.01.24 남웅주] -----------------------------------------------	
	public DcLtConsultBuyListServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
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
		//----------- [2019.01.24 남웅주] -----------------------------------------------
		String actcdbizcd = ServiceHelper.getParameter(request, PARAM_ACTCDBIZCD);
		HashMap<String, Object> keyValueMap = new HashMap<String, Object>();
		StringBuffer searchSubSQL = new StringBuffer();
		String[] actcdbizcdList = null; 
		//----------- [2019.01.24 남웅주] -----------------------------------------------
		
		DcLeagueTableDAO leagueDAO = new DcLeagueTableDAO(connection);
		List<DcLtBuyListBean> list = new ArrayList<DcLtBuyListBean>();
		try {
			int roleLevel = ServiceHelper.getUserLeagueRoleLevel(userSession.getUserRoleList());
			
			//----------- [2019.01.24 남웅주] -----------------------------------------------
			if(!"".equals(actcdbizcd)) {
				actcdbizcdList = actcdbizcd.split("-");
				keyValueMap.put("ACTCD", actcdbizcdList[0]);
				keyValueMap.put("BIZCD", actcdbizcdList[1]);				
				keyValueMap.put("COMPCD", actcdbizcdList[2]);
				
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

			switch (roleLevel) {
			case Constants.LeagueRoleLevel.ROLE_MANAGER:
				//----------- [2019.01.24 남웅주] -----------------------------------------------				
				if(searchSubSQL.length() > 0) {
					list = leagueDAO.sqlSelectConsultBuyList(searchSubSQL.toString(), keyValueMap);					
				}else {
					list = leagueDAO.sqlSelectConsultBuyList(null, null);	
				}
				//----------- [2019.01.24 남웅주] -----------------------------------------------				
				break;
			case Constants.LeagueRoleLevel.ROLE_HQ_ET:
				StringBuffer hqetCond = new StringBuffer();
				//----------- [2019.01.24 남웅주] -----------------------------------------------				
				if(searchSubSQL.length() > 0) {
					hqetCond.append(searchSubSQL.toString());
				}
				//----------- [2019.01.24 남웅주] -----------------------------------------------				
				keyValueMap.put("EMPNO", userEmpNo);
				
				hqetCond.append("AND ( ");
				hqetCond.append("     ( ");
				hqetCond.append("          HQCHR.PTRHQCD IN ( ");
				hqetCond.append("              SELECT HE.HQCD FROM WEB_DCHQEMP HE ");
				hqetCond.append("               WHERE HE.EMPNO = <#EMPNO#>");
				hqetCond.append("          ) ");
				hqetCond.append("     ) ");
				hqetCond.append("     OR ");
				hqetCond.append("     ( ");
				hqetCond.append("          LT.PTREMPNO =  <#EMPNO#>");
				hqetCond.append("          OR");
				hqetCond.append("          LT.MGREMPNO =  <#EMPNO#>");
				hqetCond.append("     ) ");
				hqetCond.append(" ) ");
				//list = leagueDAO.sqlSelectConsultBuyList(hqetCond.toString(), "EMPNO", userEmpNo);
				list = leagueDAO.sqlSelectConsultBuyList(hqetCond.toString(), keyValueMap);
				break;
			case Constants.LeagueRoleLevel.ROLE_HQ:
				StringBuffer hqCond = new StringBuffer();
				//----------- [2019.01.24 남웅주] -----------------------------------------------				
				if(searchSubSQL.length() > 0) {
					hqCond.append(searchSubSQL.toString());
				}
				//----------- [2019.01.24 남웅주] -----------------------------------------------				
				keyValueMap.put("EMPNO", userEmpNo);
				
				hqCond.append("AND HQCHR.PTRHQCD IN ( ");
				hqCond.append("     SELECT HE.HQCD FROM WEB_DCHQEMP HE ");
				hqCond.append("      WHERE HE.EMPNO = <#EMPNO#>");
				hqCond.append("    ) ");
				list = leagueDAO.sqlSelectConsultBuyList(hqCond.toString(), keyValueMap);
				break;
			case Constants.LeagueRoleLevel.ROLE_ET:
				StringBuffer etCond = new StringBuffer();
				//----------- [2019.01.24 남웅주] -----------------------------------------------				
				if(searchSubSQL.length() > 0) {
					etCond.append(searchSubSQL.toString());
				}
				//----------- [2019.01.24 남웅주] -----------------------------------------------				
				keyValueMap.put("EMPNO", userEmpNo);

				etCond.append("AND ( ");
				etCond.append("       LT.PTREMPNO = <#EMPNO#>");
				etCond.append("       OR");
				etCond.append("       LT.MGREMPNO = <#EMPNO#>");
				etCond.append("    ) ");
				list = leagueDAO.sqlSelectConsultBuyList(etCond.toString(), keyValueMap);
				break;
			case Constants.LeagueRoleLevel.ROLE_ETC:
				list = new ArrayList<DcLtBuyListBean>();
				break;
			}
			if (list == null) {
				list = new ArrayList<DcLtBuyListBean>();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("list", list);

		return data;
	}
}
