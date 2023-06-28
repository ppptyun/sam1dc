package com.samil.dc.service.worker;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcAdminHQEmpDAO;
import com.samil.dc.dao.DcLogDAO;
import com.samil.dc.util.Constants;
import com.samil.dc.util.SessionUtil;

/**
 * 본부 담당자 등록
 *
 */
public class DcAdminHQEmpAddServiceWorker extends AbstractServiceWorker {
	
	private static final String PARAM_HQCD = "hqcd";
	private static final String PARAM_EMPNO = "empno";

	public DcAdminHQEmpAddServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
		super(request, response, connection);
	}

	@Override
	public ValidationResult validate() throws Exception {
		// 사용자 검증
		ValidationResult validationResult = ServiceHelper.validateUser(request);
		if (!validationResult.isValid()) {
			return validationResult;
		}
		
		validationResult = ServiceHelper.validateParameterBlank(request, PARAM_HQCD);
		if (!validationResult.isValid()) {
			return validationResult;
		}
		
		validationResult = ServiceHelper.validateParameterBlank(request, PARAM_EMPNO);
		if (!validationResult.isValid()) {
			return validationResult;
		}
		
		return new ValidationResult(true);
	}

	@Override
	public Object process() throws Exception {
		String hqcd = ServiceHelper.getParameter(request, PARAM_HQCD);
		String empno = ServiceHelper.getParameter(request, PARAM_EMPNO);
		String creempno = SessionUtil.getUserSession(request).getUserBeans().getEMPLNO();
		
		DcAdminHQEmpDAO hqempDAO = new DcAdminHQEmpDAO(connection);
		DcLogDAO logDAO = new DcLogDAO(connection);
		try {
			int result = hqempDAO.sqlInsertHQEmp(hqcd, empno, creempno);
			if (result != 1) {
				return new ServiceError(Constants.ErrorCode.FAIL_SQL_INSERT, Constants.ErrorMessage.FAIL_SQL_INSERT);
			}
			logDAO.sqlLogHQEmpAdd(hqcd, empno, creempno);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> data = new HashMap<String, Object>();
		return data;
	}
}
