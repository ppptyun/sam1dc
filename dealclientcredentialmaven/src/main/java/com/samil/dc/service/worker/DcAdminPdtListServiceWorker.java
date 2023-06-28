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
import com.samil.dc.domain.DcAdminPdtListBean;
import com.samil.dc.util.Constants;

public class DcAdminPdtListServiceWorker extends AbstractServiceWorker {
	
	private final static String PARAM_REFYEARLY = "refyearly";

	public DcAdminPdtListServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
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
		List<DcAdminPdtListBean> list = new ArrayList<DcAdminPdtListBean>();
		List<DcAdminPdtCategoryListBean> category = new ArrayList<DcAdminPdtCategoryListBean>();
		try {
			// PDT 목록 조회
			list = pdtDAO.sqlSelectPdtList(refyearly);
			if (list == null) {
				list = new ArrayList<DcAdminPdtListBean>();
			}
			
			// 카테고리 목록 조회
			category = pdtDAO.sqlSelectPdtCategoryList(refyearly, Constants.YES, Constants.NO);
			if (category == null) {
				category = new ArrayList<DcAdminPdtCategoryListBean>();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("list", list);
		data.put("category", category);

		return data;
	}
}
