package com.samil.dc.service.worker;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcLeagueTableDAO;
import com.samil.dc.domain.DcLtRealBean;
import com.samil.dc.util.Constants;

/**
 * <pre>
 * ====================================================================================
 * 부동산 자문 상세 정보 조회
 * ====================================================================================
 * </pre>
 */
public class DcLtConsultRealDetailServiceWorker extends AbstractServiceWorker {
	
	private static final String PARAM_PRJTCD = "prjtcd";

	public DcLtConsultRealDetailServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
		super(request, response, connection);
	}

	@Override
	public ValidationResult validate() throws Exception {
		// 사용자 검증
		ValidationResult validationResult = ServiceHelper.validateUser(request);
		if (!validationResult.isValid()) {
			return validationResult;
		}
		
		// 파라미터 prjtcd
		validationResult = ServiceHelper.validateParameterBlank(request, PARAM_PRJTCD);
		if (!validationResult.isValid()) {
			return validationResult;
		}
		
		return new ValidationResult(true);
	}

	@Override
	public Object process() throws Exception {
		String prjtcd = ServiceHelper.getParameter(request, PARAM_PRJTCD);
		
		DcLeagueTableDAO leagueDAO = new DcLeagueTableDAO(connection);
		DcLtRealBean project = null;
		Boolean authBtnEdit = false;
		
		try {
			// 프로젝트 정보 조회
			Object rtnObj = ServiceHelper.getLeagueTableRealProject(leagueDAO, prjtcd);
			if (rtnObj instanceof ServiceError) {
				return (ServiceError) rtnObj;
			} else if (rtnObj instanceof DcLtRealBean) {
				project = (DcLtRealBean) rtnObj;
			} else {
				return new ServiceError(Constants.ErrorCode.INTERNAL, Constants.ErrorMessage.INTERNAL);
			}
			
			// '수정' 버튼 권한
			String ptrhqcd = leagueDAO.sqlSelectPtrEmpHQ(project.getPtrempno());
			project.setPtrhqcd(ptrhqcd);
			authBtnEdit = ServiceHelper.isAuthLeagueDetailEdit(request, leagueDAO, project);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> auth = new HashMap<String, Object>();
		auth.put("btnEdit", authBtnEdit);

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("project", project);
		data.put("auth", auth);

		return data;
	}
}
