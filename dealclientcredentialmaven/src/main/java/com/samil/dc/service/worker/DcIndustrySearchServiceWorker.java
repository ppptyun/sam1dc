package com.samil.dc.service.worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcSupportDAO;
import com.samil.dc.domain.IndustrySearchListBean;
import com.samil.dc.util.Constants;

/**
 * 산업 검색
 *
 */
public class DcIndustrySearchServiceWorker extends AbstractServiceWorker {

	public DcIndustrySearchServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
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
		String induNm = ServiceHelper.getParameter(request, "indunm");

		// 임직원 목록 검색
		DcSupportDAO supportDAO = new DcSupportDAO(connection);
		List<IndustrySearchListBean> list = new ArrayList<IndustrySearchListBean>();
		try {
			list = supportDAO.sqlSelectIndustrySearchList(induNm);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("list", list);

		return data;
	}
}
