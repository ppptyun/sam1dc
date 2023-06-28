package com.samil.dc.service.worker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcAdminRoleDAO;

public class DcAdminRoleSaveServiceWorker extends AbstractServiceWorker {
	
	private static final String PARAM_ITEMS = "items";
	private static final String SEPARATOR_ITEM = "|";
	private static final String SEPARATOR_DATA = "^";
	private static final String SAVE_DIV_INSERT = "I";
	private static final String SAVE_DIV_UPDATE = "U";

	public DcAdminRoleSaveServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
		super(request, response, connection);
	}

	@Override
	public ValidationResult validate() throws Exception {
		// 사용자 검증
		ValidationResult validationResult = ServiceHelper.validateUser(request);
		if (!validationResult.isValid()) {
			return validationResult;
		}
		
		// required [items | 저장 데이터]
		validationResult = ServiceHelper.validateParameterBlank(request, PARAM_ITEMS);
		if (!validationResult.isValid()) {
			return validationResult;
		}
		
		return new ValidationResult(true);
	}

	@Override
	public Object process() throws Exception {
		DcAdminRoleDAO roleDAO = new DcAdminRoleDAO(connection);
		
		String strItems = StringUtils.trim(ServiceHelper.getParameter(request, PARAM_ITEMS));
		List<String> items = ServiceHelper.convertStringToList(strItems, SEPARATOR_ITEM);
		try {
			for (String item : items) {
				List<String> data = ServiceHelper.convertStringToList(item, SEPARATOR_DATA);
				String div = StringUtils.defaultIfBlank(StringUtils.trim(data.get(0)), "");
				String code = StringUtils.defaultIfBlank(StringUtils.trim(data.get(1)), "");
				String name = StringUtils.defaultIfBlank(StringUtils.trim(data.get(2)), "");
				String sort = StringUtils.defaultIfBlank(StringUtils.trim(data.get(3)), "99");
				String oricode = "";
				if (SAVE_DIV_UPDATE.equals(div)) {
					oricode = StringUtils.defaultIfBlank(StringUtils.trim(data.get(4)), "");
				}
				if (!StringUtils.isNumeric(sort)) {
					continue;
				}
				if (SAVE_DIV_INSERT.equals(div)) {
					roleDAO.sqlInsertRole(code, name, Integer.valueOf(sort));
				} else if (SAVE_DIV_UPDATE.equals(div)) {
					roleDAO.sqlUpdateRole(code, name, Integer.valueOf(sort), oricode);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> data = new HashMap<String, Object>();
		return data;
	}
}
