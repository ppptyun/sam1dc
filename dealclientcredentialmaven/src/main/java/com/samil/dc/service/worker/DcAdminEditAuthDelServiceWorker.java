package com.samil.dc.service.worker;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcAdminEditAuthDAO;
import com.samil.dc.dao.DcLogDAO;
import com.samil.dc.util.Constants;
import com.samil.dc.util.SessionUtil;

/**
 * League Table 편집 관리 삭제
 *
 */
public class DcAdminEditAuthDelServiceWorker extends AbstractServiceWorker {

	private static final String PARAM_ROLECD = "rolecd";

	public DcAdminEditAuthDelServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
		super(request, response, connection);
	}

	@Override
	public ValidationResult validate() throws Exception {
		// 사용자 검증
		ValidationResult validationResult = ServiceHelper.validateUser(request);
		if (!validationResult.isValid()) {
			return validationResult;
		}

		// 파라미터 rolecd
		validationResult = ServiceHelper.validateParameterBlank(request, PARAM_ROLECD);
		if (!validationResult.isValid()) {
			return validationResult;
		}

		return new ValidationResult(true);
	}

	@Override
	public Object process() throws Exception {
		// 파라미터
		String rolecd = ServiceHelper.getParameter(request, PARAM_ROLECD);
		String creempno = SessionUtil.getUserSession(request).getUserBeans().getEMPLNO();

		DcAdminEditAuthDAO editauthDAO = new DcAdminEditAuthDAO(connection);
		DcLogDAO logDAO = new DcLogDAO(connection);
		try {
			int result = editauthDAO.sqlDeleteEditAuth(rolecd);
			if (result != 1) {
				return new ServiceError(Constants.ErrorCode.FAIL_SQL_DELETE, Constants.ErrorMessage.FAIL_SQL_DELETE);
			}
			logDAO.sqlLogEditAuthDel(rolecd, creempno);

		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> data = new HashMap<String, Object>();
		return data;
	}
}
