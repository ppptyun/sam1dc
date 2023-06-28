package com.samil.dc.service.worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcAdminConsultDAO;
import com.samil.dc.domain.AdminConsultListBean;

/**
 * 자문사 관리 목록 조회
 *
 */
public class DcAdminConsultListServiceWorker extends AbstractServiceWorker {

	public DcAdminConsultListServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
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
		DcAdminConsultDAO consultDAO = new DcAdminConsultDAO(connection);
		List<AdminConsultListBean> list = new ArrayList<AdminConsultListBean>();
		try {
			list = consultDAO.sqlSelectConsultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("list", list);

		return data;
	}
}
