package com.samil.dc.service.worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcCredentialDAO;
import com.samil.dc.domain.DcCredentialProductBean;
import com.samil.dc.domain.NationSearchListBean;

/**
 * <pre>
 * ====================================================================================
 * Company 타입별 나라 조회
 * ====================================================================================
 * </pre>
 */
public class DcCredentialCompanyTypeNationListServiceWorker extends AbstractServiceWorker {

	public DcCredentialCompanyTypeNationListServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
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
		// 파라미터 조합
		String bizcd = ServiceHelper.getParameter(request, "bizcd");
		
		DcCredentialDAO credDAO = new DcCredentialDAO(connection);
		List<NationSearchListBean> list = new ArrayList<NationSearchListBean>();
		try {
			list = credDAO.sqlSelectCompanyTypeNationList(bizcd);
			if (list == null) {
				list = new ArrayList<NationSearchListBean>();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("list", list);

		return data;
	}
}
