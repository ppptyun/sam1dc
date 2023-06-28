package com.samil.dc.service.worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcAdminPdtDAO;
import com.samil.dc.domain.DcAdminPdtCategoryListBean;
import com.samil.dc.util.Constants;

public class DcAdminPdtCategoryListServiceWorker extends AbstractServiceWorker {
	
	private final static String PARAM_REFYEARLY = "refyearly";

	public DcAdminPdtCategoryListServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
		super(request, response, connection);
	}

	@Override
	public ValidationResult validate() throws Exception {
		// 사용자 검증
		ValidationResult validationResult = ServiceHelper.validateUser(request);
		if (!validationResult.isValid()) {
			return validationResult;
		}
		
		validationResult = ServiceHelper.validateParameterBlank(request, PARAM_REFYEARLY);
		if (!validationResult.isValid()) {
			return validationResult;
		}
		
		return new ValidationResult(true);
	}

	@Override
	public Object process() throws Exception {
		String refyearly = ServiceHelper.getParameter(request, PARAM_REFYEARLY);
		
		DcAdminPdtDAO pdtDAO = new DcAdminPdtDAO(connection);
		List<DcAdminPdtCategoryListBean> list = new ArrayList<DcAdminPdtCategoryListBean>();
		List<DcAdminPdtCategoryListBean> system = new ArrayList<DcAdminPdtCategoryListBean>();
		try {
			// 카테고리 목록 조회
			list = pdtDAO.sqlSelectPdtCategoryList(refyearly, Constants.YES, Constants.NO);
			if (list == null) {
				list = new ArrayList<DcAdminPdtCategoryListBean>();
			}
			
			system = pdtDAO.sqlSelectPdtSystemCategoryList(refyearly);
			if (system == null) {
				system = new ArrayList<DcAdminPdtCategoryListBean>();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("list", list);
		data.put("system", system);

		return data;
	}
}
