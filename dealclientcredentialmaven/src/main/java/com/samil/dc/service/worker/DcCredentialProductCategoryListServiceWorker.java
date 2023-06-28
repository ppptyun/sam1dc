package com.samil.dc.service.worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcCredentialDAO;
import com.samil.dc.domain.DcCredentialProductCategoryBean;

/**
 * <pre>
 * ====================================================================================
 * Product Category 목록 조회
 * ====================================================================================
 * </pre>
 */
public class DcCredentialProductCategoryListServiceWorker extends AbstractServiceWorker {

	public DcCredentialProductCategoryListServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
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
		DcCredentialDAO credDAO = new DcCredentialDAO(connection);
		List<DcCredentialProductCategoryBean> list = new ArrayList<DcCredentialProductCategoryBean>();
		try {
			list = credDAO.sqlSelectProductCategoryList();
			if (list == null) {
				list = new ArrayList<DcCredentialProductCategoryBean>();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("list", list);

		return data;
	}
}
