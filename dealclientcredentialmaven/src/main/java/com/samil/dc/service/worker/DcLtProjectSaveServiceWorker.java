package com.samil.dc.service.worker;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcLeagueTableDAO;
import com.samil.dc.dao.DcLogDAO;
import com.samil.dc.util.Constants;
import com.samil.dc.util.SessionUtil;

/**
 * <pre>
 * ====================================================================================
 * 대상 프로젝트 관리 - 대상 지정
 * ====================================================================================
 * </pre>
 */
public class DcLtProjectSaveServiceWorker extends AbstractServiceWorker {

	private static final String PARAM_LTTGT = "lttgtcd";
	private static final String PARAM_ITEMS = "items";
	private static final String SPLIT_ITEMS = "|";

	public DcLtProjectSaveServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
		super(request, response, connection);
	}

	@Override
	public ValidationResult validate() throws Exception {
		// 사용자 검증
		ValidationResult validationResult = ServiceHelper.validateUser(request);
		if (!validationResult.isValid()) {
			return validationResult;
		}

		// 파라미터 lttgtcd
		validationResult = ServiceHelper.validateParameterBlank(request, PARAM_LTTGT);
		if (!validationResult.isValid()) {
			return validationResult;
		}

		// 파라미터 items
		validationResult = ServiceHelper.validateParameterBlank(request, PARAM_ITEMS);
		if (!validationResult.isValid()) {
			return validationResult;
		}

		return new ValidationResult(true);
	}

	@Override
	public Object process() throws Exception {
		DcLeagueTableDAO leagueDAO = new DcLeagueTableDAO(connection);
		DcLogDAO logDAO = new DcLogDAO(connection);

		// 파라미터
		String lttgtcd = ServiceHelper.getParameter(request, PARAM_LTTGT);
		String items = ServiceHelper.getParameter(request, PARAM_ITEMS);
		String empno = SessionUtil.getUserSession(request).getUserBeans().getEMPLNO();

		// 업데이트
		String[] arrItems = StringUtils.split(items, SPLIT_ITEMS);
		for (String prjtcd : arrItems) {
			// 로그 기록
			logDAO.sqlLogProjectTargetSave(prjtcd, lttgtcd, empno);
			
			int result = leagueDAO.sqlUpdateProjectTarget(prjtcd, lttgtcd);
			if (result != 1) {
				return new ServiceError(Constants.ErrorCode.FAIL_SQL_UPDATE, Constants.ErrorMessage.FAIL_SQL_UPDATE);
			}
		}

		Map<String, Object> data = new HashMap<String, Object>();
		return data;
	}
}
