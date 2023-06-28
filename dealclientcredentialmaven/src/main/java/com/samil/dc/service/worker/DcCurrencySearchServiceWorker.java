package com.samil.dc.service.worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcSupportDAO;
import com.samil.dc.domain.CurrencySearchListBean;

/**
 * 통화(Currency) 목록
 *
 */
public class DcCurrencySearchServiceWorker extends AbstractServiceWorker {

	public DcCurrencySearchServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
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
		// 통화 목록 검색
		DcSupportDAO supportDAO = new DcSupportDAO(connection);
		List<CurrencySearchListBean> list = new ArrayList<CurrencySearchListBean>();
		try {
			list = supportDAO.sqlSelectCurrencySearchList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("list", list);

		return data;
	}
}
