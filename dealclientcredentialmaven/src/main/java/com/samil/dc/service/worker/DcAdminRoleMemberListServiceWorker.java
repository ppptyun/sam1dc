package com.samil.dc.service.worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcAdminRoleDAO;
import com.samil.dc.domain.AdminRoleMemberListBean;

public class DcAdminRoleMemberListServiceWorker extends AbstractServiceWorker {

	private static final String ROLE_CD = "rolecd";

	public DcAdminRoleMemberListServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
		super(request, response, connection);
	}

	@Override
	public ValidationResult validate() throws Exception {
		// 사용자 검증
		ValidationResult validationResult = ServiceHelper.validateUser(request);
		if (!validationResult.isValid()) {
			return validationResult;
		}

		// required [rolecd | 롤 코드]
		validationResult = ServiceHelper.validateParameterBlank(request, ROLE_CD);
		if (!validationResult.isValid()) {
			return validationResult;
		}

		return new ValidationResult(true);
	}

	@Override
	public Object process() throws Exception {
		String roleCd = ServiceHelper.getParameter(request, ROLE_CD);

		DcAdminRoleDAO roleDAO = new DcAdminRoleDAO(connection);
		List<AdminRoleMemberListBean> list = new ArrayList<AdminRoleMemberListBean>();
		try {
			list = roleDAO.sqlSelectRoleMemberList(roleCd);
			if (list == null) {
				list = new ArrayList<AdminRoleMemberListBean>();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("list", list);

		return data;
	}
}
