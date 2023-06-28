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
 * League Table 자문 ET 확인
 * ====================================================================================
 * </pre>
 */
public class DcLtConsultETConfirmServiceWorker extends AbstractServiceWorker {

	private static final String PARAM_ITEMS = "items";
	private static final String SPLIT_ITEMS = "|";

	public DcLtConsultETConfirmServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
		super(request, response, connection);
	}

	@Override
	public ValidationResult validate() throws Exception {
		// 사용자 검증
		ValidationResult validationResult = ServiceHelper.validateUser(request);
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
		String items = ServiceHelper.getParameter(request, PARAM_ITEMS);
		String empno = SessionUtil.getUserSession(request).getUserBeans().getEMPLNO();

		// 업데이트
		String[] arrItems = StringUtils.split(items, SPLIT_ITEMS);
		for (String prjtcd : arrItems) {
			if (StringUtils.isBlank(prjtcd)) {
				return new ServiceError(Constants.ErrorCode.NOT_FOUND_PROJECT, Constants.ErrorMessage.NOT_FOUND_PROJECT);
			}
			
			int result = 0;
			String bigo = "ET확인 등록";
			logDAO.sqlLogConsultETConfirmSave(prjtcd, bigo, empno);
			result = leagueDAO.sqlUpdateETConfirmConsult(prjtcd, empno);
			if (result != 1) {
				return new ServiceError(Constants.ErrorCode.FAIL_SQL_UPDATE, Constants.ErrorMessage.FAIL_SQL_UPDATE);
			}
		}

		Map<String, Object> data = new HashMap<String, Object>();
		return data;
	}
}
