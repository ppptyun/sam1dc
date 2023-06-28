package com.samil.dc.service.worker;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcAdminMenuDAO;

/**
 * 메뉴상세 저장
 *
 */
public class DcAdminMenuSaveServiceWorker extends AbstractServiceWorker {
	
	private static final String UP_MENU_ID = "upmenuid";
	private static final String MENU_ID = "menuid";
	private static final String MENU_NM = "menunm";
	private static final String URL = "url";
	private static final String SORT = "sort";
	private static final String SAVE_STATE = "saveState";
	

	public DcAdminMenuSaveServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
		super(request, response, connection);
	}

	@Override
	public ValidationResult validate() throws Exception {
		
		ValidationResult validationResult = ServiceHelper.validateUser(request);
		validationResult = ServiceHelper.validateParameterBlank(request, MENU_ID);
		if (!validationResult.isValid()) {
			return validationResult;
		}
		// 사용자 검증
		if (!validationResult.isValid()) {
			return validationResult;
		}
		return new ValidationResult(true);
	}

	@Override
	public Object process() throws Exception {
		
		
		DcAdminMenuDAO menuDAO = new DcAdminMenuDAO(connection);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put(UP_MENU_ID, ServiceHelper.getParameter(request, UP_MENU_ID));
		data.put(MENU_ID, ServiceHelper.getParameter(request, MENU_ID));
		data.put(MENU_NM, ServiceHelper.getParameter(request, MENU_NM));
		data.put(URL, ServiceHelper.getParameter(request, URL));
		data.put(SORT, ServiceHelper.getParameter(request, SORT));
		
		String saveState = ServiceHelper.getParameter(request, SAVE_STATE);
		
		try {
			menuDAO.sqlSaveMenu(data, saveState);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return data;
	}
}
