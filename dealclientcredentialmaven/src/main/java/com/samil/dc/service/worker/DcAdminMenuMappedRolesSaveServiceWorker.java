package com.samil.dc.service.worker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcAdminMenuDAO;

public class DcAdminMenuMappedRolesSaveServiceWorker extends AbstractServiceWorker {
	
	private static final String PARAM_ITEMS = "items";
	private static final String SEPARATOR_ITEM = "|";
	private static final String SEPARATOR_DATA = "^";
	private static final String SAVE_DIV_INSERT = "I";
	private static final String SAVE_DIV_UPDATE = "U";

	public DcAdminMenuMappedRolesSaveServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
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
		DcAdminMenuDAO menuDAO = new DcAdminMenuDAO(connection);
		
		String strItems = StringUtils.trim(ServiceHelper.getParameter(request, PARAM_ITEMS));
		List<String> items = ServiceHelper.convertStringToList(strItems, SEPARATOR_ITEM);
		try {
			for (String item : items) {
				List<String> data = ServiceHelper.convertStringToList(item, SEPARATOR_DATA);
				String div = StringUtils.defaultIfBlank(StringUtils.trim(data.get(0)), "");
				String menuid = StringUtils.defaultIfBlank(StringUtils.trim(data.get(1)), "");
				String rolecd = StringUtils.defaultIfBlank(StringUtils.trim(data.get(2)), "");
				String originrolecd = "";
				if (SAVE_DIV_UPDATE.equals(div)) {
					originrolecd = StringUtils.defaultIfBlank(StringUtils.trim(data.get(3)), "");
				}
				if (SAVE_DIV_INSERT.equals(div)) {
					menuDAO.sqlInsertMenuMap(menuid, rolecd);
				} else if (SAVE_DIV_UPDATE.equals(div)) {
					menuDAO.sqlUpdateMenuMap(menuid, rolecd, originrolecd);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> data = new HashMap<String, Object>();
		return data;
	}
}
