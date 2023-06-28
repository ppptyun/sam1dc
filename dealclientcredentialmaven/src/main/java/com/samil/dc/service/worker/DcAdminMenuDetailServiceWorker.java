package com.samil.dc.service.worker;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcAdminMenuDAO;
import com.samil.dc.domain.AdminMenuDetailBean;

/**
 * 메뉴 상세
 *
 */
public class DcAdminMenuDetailServiceWorker extends AbstractServiceWorker {

	public DcAdminMenuDetailServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
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
		
		String upmenuid = ServiceHelper.getParameter(request, "upmenuid");
		String menuid = ServiceHelper.getParameter(request, "menuid");

		DcAdminMenuDAO menuDAO = new DcAdminMenuDAO(connection);
		AdminMenuDetailBean vo = new AdminMenuDetailBean();
		try {
			vo = menuDAO.sqlSelectMenuDetail(upmenuid, menuid);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("vo", vo);

		return data;
	}
}
