package com.samil.dc.service.worker;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcAdminCommCodeDAO;
import com.samil.dc.util.SessionUtil;

/**
 * 공통코드 저장
 *
 */
public class DcAdminCommCodeSaveServiceWorker extends AbstractServiceWorker {
	
	private static final String PARENT_CODE = "parcd";
	private static final String ITEM_CODE = "itemcd";
	private static final String ITEM_NAME = "itemnm";
	private static final String DESCRIPT = "descp";
	private static final String SORT = "sort";
	private static final String USE_YN = "useyn";
	private static final String SAVE_STATE = "saveState";
	
	public DcAdminCommCodeSaveServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
		super(request, response, connection);
	}

	@Override
	public ValidationResult validate() throws Exception {
		
		ValidationResult validationResult = ServiceHelper.validateUser(request);
		validationResult = ServiceHelper.validateParameterBlank(request, ITEM_CODE);
		if (!validationResult.isValid()) {
			return validationResult;
		}
		validationResult = ServiceHelper.validateParameterBlank(request, ITEM_NAME);
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
		
		DcAdminCommCodeDAO commDAO = new DcAdminCommCodeDAO(connection);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put(PARENT_CODE, ServiceHelper.getParameter(request, PARENT_CODE));
		data.put(ITEM_CODE, ServiceHelper.getParameter(request, ITEM_CODE));
		data.put(ITEM_NAME, ServiceHelper.getParameter(request, ITEM_NAME));
		data.put(USE_YN, ServiceHelper.getParameter(request, USE_YN));
		data.put(SORT, ServiceHelper.getParameter(request, SORT));
		data.put(DESCRIPT, ServiceHelper.getParameter(request, DESCRIPT));
		data.put("updempno", SessionUtil.getUserSession(request).getUserBeans().getEMPLNO());
		
		String saveState = ServiceHelper.getParameter(request, SAVE_STATE);
		
		try {
			commDAO.sqlSaveCommCode(data, saveState);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return data;
	}
}
