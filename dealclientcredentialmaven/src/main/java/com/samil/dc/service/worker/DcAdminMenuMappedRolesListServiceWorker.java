package com.samil.dc.service.worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcAdminMenuDAO;
import com.samil.dc.domain.AdminMenuRoleBean;

/**
 * 메뉴에 맵핑된 롤 리스트
 *
 */
public class DcAdminMenuMappedRolesListServiceWorker extends AbstractServiceWorker {

	public DcAdminMenuMappedRolesListServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
		super(request, response, connection);
	}

	@Override
	public ValidationResult validate() throws Exception {
		// 사용자 검증
		ValidationResult validationResult = ServiceHelper.validateUser(request);
		if (!validationResult.isValid()) {
			return validationResult;
		}
		
		validationResult = ServiceHelper.validateParameterBlank(request, "menuid");
		if (!validationResult.isValid()) {
			return validationResult;
		}
		
		return new ValidationResult(true);
	}

	@Override
	public Object process() throws Exception {
		
		String menuid = ServiceHelper.getParameter(request, "menuid");

		DcAdminMenuDAO menuDAO = new DcAdminMenuDAO(connection);
		List<AdminMenuRoleBean> list = new ArrayList<AdminMenuRoleBean>();
		try {
			list = menuDAO.sqlSelectMenuMappedRolesList(menuid);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("list", list);

		return data;
	}
}
