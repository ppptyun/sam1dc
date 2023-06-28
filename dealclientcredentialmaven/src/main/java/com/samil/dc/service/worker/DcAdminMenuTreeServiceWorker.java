package com.samil.dc.service.worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcAdminMenuDAO;
import com.samil.dc.domain.AdminMenuTreeBean;

/**
 * 메뉴 트리뷰
 *
 */
public class DcAdminMenuTreeServiceWorker extends AbstractServiceWorker {

	public DcAdminMenuTreeServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
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

		DcAdminMenuDAO treeDAO = new DcAdminMenuDAO(connection);
		List<AdminMenuTreeBean> list = new ArrayList<AdminMenuTreeBean>();
		try {
			list = treeDAO.sqlSelectMenuTree();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("list", list);

		return data;
	}
}
