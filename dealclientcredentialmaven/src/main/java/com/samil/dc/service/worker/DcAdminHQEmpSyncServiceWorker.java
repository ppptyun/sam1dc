package com.samil.dc.service.worker;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcAdminHQEmpDAO;
import com.samil.dc.dao.DcLogDAO;
import com.samil.dc.util.SessionUtil;

/**
 * Deal 본부 목록 동기화(From View)
 *
 */
public class DcAdminHQEmpSyncServiceWorker extends AbstractServiceWorker {

	public DcAdminHQEmpSyncServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
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
		String creempno = SessionUtil.getUserSession(request).getUserBeans().getEMPLNO();
		
		DcAdminHQEmpDAO hqempDAO = new DcAdminHQEmpDAO(connection);
		DcLogDAO logDAO = new DcLogDAO(connection);
		try {
			hqempDAO.sqlMergetHQEmpSync(creempno);
			logDAO.sqlLogHQEmpSync(creempno);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> data = new HashMap<String, Object>();
		return data;
	}
}
