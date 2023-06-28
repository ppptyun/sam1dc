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
import com.samil.dc.domain.CompanySearchListBean;
import com.samil.dc.util.Constants;

/**
 * 회사 검색
 *
 */
public class DcCompanySearchServiceWorker extends AbstractServiceWorker {

	public DcCompanySearchServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
		super(request, response, connection);
	}

	@Override
	public ValidationResult validate() throws Exception {
		// 사용자 검증
		ValidationResult validationResult = ServiceHelper.validateUser(request);
		if (!validationResult.isValid()) {
			return validationResult;
		}

		String comNm = ServiceHelper.getParameter(request, "comnm");
		if (StringUtils.isBlank(comNm)) {
			validationResult = new ValidationResult(Constants.ErrorCode.EMPTY_PARAMETER, Constants.ErrorMessage.EMPTY_PARAMETER);
			validationResult.setErrorParameterAndValue("comNm", "");
			return validationResult;
		}

		return new ValidationResult(true);
	}

	@Override
	public Object process() throws Exception {
		// 파라미터 조합
		String comNm = ServiceHelper.getParameter(request, "comnm");

		// 임직원 목록 검색
		DcSupportDAO supportDAO = new DcSupportDAO(connection);
		List<CompanySearchListBean> list = new ArrayList<CompanySearchListBean>();
		try {
			list = supportDAO.sqlSelectCompanySearchList(comNm);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("list", list);

		return data;
	}
}
