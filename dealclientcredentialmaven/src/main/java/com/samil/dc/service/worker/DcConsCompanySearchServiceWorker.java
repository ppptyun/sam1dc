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
import com.samil.dc.domain.ConsCompanySearchListBean;
import com.samil.dc.util.Constants;

/**
 * 자문사 검색
 *
 */
public class DcConsCompanySearchServiceWorker extends AbstractServiceWorker {

	public DcConsCompanySearchServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
		super(request, response, connection);
	}

	@Override
	public ValidationResult validate() throws Exception {
		// 사용자 검증
		ValidationResult validationResult = ServiceHelper.validateUser(request);
		if (!validationResult.isValid()) {
			return validationResult;
		}

		String type = ServiceHelper.getParameter(request, "type");
		if (StringUtils.isBlank(type)) {
			validationResult = new ValidationResult(Constants.ErrorCode.EMPTY_PARAMETER, Constants.ErrorMessage.EMPTY_PARAMETER);
			validationResult.setErrorParameterAndValue("type", "");
			return validationResult;
		}
		
		return new ValidationResult(true);
	}

	@Override
	public Object process() throws Exception {
		// 파라미터 조합
		String comNm = ServiceHelper.getParameter(request, "comnm");
		String type = ServiceHelper.getParameter(request, "type");
		
		// 임직원 목록 검색
		DcSupportDAO supportDAO = new DcSupportDAO(connection);
		List<ConsCompanySearchListBean> list = new ArrayList<ConsCompanySearchListBean>();
		try {
			list = supportDAO.sqlSelectConsCompanySearchList(comNm, type);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("list", list);

		return data;
	}
}
