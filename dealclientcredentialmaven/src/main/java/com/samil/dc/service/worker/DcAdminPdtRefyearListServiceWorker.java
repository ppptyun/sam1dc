package com.samil.dc.service.worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcAdminPdtDAO;

/**
 * PDT 관리 참조년도 목록 조회
 *
 */
public class DcAdminPdtRefyearListServiceWorker extends AbstractServiceWorker {

	public DcAdminPdtRefyearListServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
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
		DcAdminPdtDAO pdtDAO = new DcAdminPdtDAO(connection);
		List<String> list = new ArrayList<String>();
		try {
			list = pdtDAO.sqlSelectPdtRefyearList();
			if (list == null) {
				list = new ArrayList<String>();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("list", list);

		return data;
	}
}
