package com.samil.dc.service.worker;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcAdminCommCodeDAO;

/**
 * 공통코드 상세보기/수정
 *
 */
public class DcAdminCommCodeDeleteServiceWorker extends AbstractServiceWorker {

	public DcAdminCommCodeDeleteServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
		super(request, response, connection);
	}

	@Override
	public ValidationResult validate() throws Exception {
		// 사용자 검증
		ValidationResult validationResult = ServiceHelper.validateUser(request);
		if (!validationResult.isValid()) {
			return validationResult;
		}
		
		validationResult = ServiceHelper.validateParameterBlank(request, "prtcd");
		if (!validationResult.isValid()) {
			return validationResult;
		}
		
		validationResult = ServiceHelper.validateParameterBlank(request, "itemcd");
		if (!validationResult.isValid()) {
			return validationResult;
		}
		
		return new ValidationResult(true);
	}

	@Override
	public Object process() throws Exception {
		
		String parcd = ServiceHelper.getParameter(request, "prtcd");
		String itemcd = ServiceHelper.getParameter(request, "itemcd");

		DcAdminCommCodeDAO cmmDAO = new DcAdminCommCodeDAO(connection);
		try {
			cmmDAO.sqlDeleteCommCode(parcd, itemcd);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("parcd", parcd);
		data.put("itemcd", itemcd);

		return data;
	}
}
