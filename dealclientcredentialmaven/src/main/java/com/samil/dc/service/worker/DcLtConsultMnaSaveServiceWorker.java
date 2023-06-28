package com.samil.dc.service.worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcLeagueTableDAO;
import com.samil.dc.domain.DcLtBizBean;
import com.samil.dc.domain.DcLtMnaBean;
import com.samil.dc.util.Constants;
import com.samil.dc.util.SessionUtil;

/**
 * <pre>
 * ====================================================================================
 * 합병 자문 상세 정보 저장
 * ====================================================================================
 * </pre>
 */
public class DcLtConsultMnaSaveServiceWorker extends AbstractServiceWorker {

	private static final String PARAM_PRJTCD = "prjtcd";

	public DcLtConsultMnaSaveServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
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
		String empno = SessionUtil.getUserSession(request).getUserBeans().getEMPLNO();

		DcLeagueTableDAO leagueDAO = new DcLeagueTableDAO(connection);
		DcLtMnaBean befProject = null;

		try {
			// 기존 프로젝트 정보 조회
			Object rtnObj = ServiceHelper.getLeagueTableMnaProject(leagueDAO, prjtcd);
			if (rtnObj instanceof ServiceError) {
				return (ServiceError) rtnObj;
			} else if (rtnObj instanceof DcLtMnaBean) {
				befProject = (DcLtMnaBean) rtnObj;
			} else {
				return new ServiceError(Constants.ErrorCode.INTERNAL, Constants.ErrorMessage.INTERNAL);
			}
			// 날짜 포맷 조정
			befProject.setConsdt(ServiceHelper.stripForDateFormat(befProject.getConsdt()));
			befProject.setDirtdt(ServiceHelper.stripForDateFormat(befProject.getDirtdt()));
			befProject.setStkhdt(ServiceHelper.stripForDateFormat(befProject.getStkhdt()));
			befProject.setMnadt(ServiceHelper.stripForDateFormat(befProject.getMnadt()));
			
			// 사용자 입력 파라미터 정보 조회
			DcLtMnaBean altProject = ParameterHelper.getParameterMnaBean(request);
			
			// 정보 변경 수행
			int result = 0;
			result = leagueDAO.sqlUpdateLeagueTableBase(altProject);
			if (result != 1) {
				return new ServiceError(Constants.ErrorCode.FAIL_SQL_UPDATE, Constants.ErrorMessage.FAIL_SQL_UPDATE);
			}
			result = leagueDAO.sqlUpdateLeagueTableMna(altProject);
			if (result != 1) {
				return new ServiceError(Constants.ErrorCode.FAIL_SQL_UPDATE, Constants.ErrorMessage.FAIL_SQL_UPDATE);
			}
			
			// 정보 변경에 의한 담당자 확인 자동 철회
			leagueDAO.sqlUpdateConfirmConsultNo(prjtcd, Constants.ChargeConfirm.NO);
			
			// 기업정보
			List<DcLtBizBean> newBizList = new ArrayList<DcLtBizBean>();
			List<DcLtBizBean> updBizList = new ArrayList<DcLtBizBean>();
			List<DcLtBizBean> delBizList = ParameterHelper.getParameterRemoveBiz(request);
			ServiceHelper.doParseNewUpdBizList(altProject, newBizList, updBizList);
			leagueDAO.sqlInsertLeagueTableBiz(newBizList);
			leagueDAO.sqlUpdateLeagueTableBiz(updBizList);
			leagueDAO.sqlDeleteLeagueTableBiz(delBizList);
			
			// 변경 로그
			LogHelper logHelper = new LogHelper(connection);
			logHelper.doLogLeagueTableMna(befProject, altProject, delBizList, empno);

		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> data = new HashMap<String, Object>();
		return data;
	}
}