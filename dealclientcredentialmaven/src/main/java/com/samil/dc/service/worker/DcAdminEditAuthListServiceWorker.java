package com.samil.dc.service.worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcAdminEditAuthDAO;
import com.samil.dc.domain.AdminEditAuthListBean;

/**
 * League Table 편집 관리 목록 조회
 *
 */
public class DcAdminEditAuthListServiceWorker extends AbstractServiceWorker {

	public DcAdminEditAuthListServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
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
		DcAdminEditAuthDAO editauthDAO = new DcAdminEditAuthDAO(connection);
		List<AdminEditAuthListBean> list = new ArrayList<AdminEditAuthListBean>();
		try {
			list = editauthDAO.sqlSelectEditAuthList();
			if (list == null) {
				list = new ArrayList<AdminEditAuthListBean>();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("list", list);

		return data;
	}
}
