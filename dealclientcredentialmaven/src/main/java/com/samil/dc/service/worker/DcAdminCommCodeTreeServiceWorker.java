package com.samil.dc.service.worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcAdminCommCodeDAO;
import com.samil.dc.domain.CommCodeTreeBean;

/**
 * 공통코드 트리뷰
 *
 */
public class DcAdminCommCodeTreeServiceWorker extends AbstractServiceWorker {

	public DcAdminCommCodeTreeServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
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

		DcAdminCommCodeDAO treeDAO = new DcAdminCommCodeDAO(connection);
		List<CommCodeTreeBean> list = new ArrayList<CommCodeTreeBean>();
		try {
			list = treeDAO.sqlSelectCommCodeTree();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("list", list);

		return data;
	}
}
