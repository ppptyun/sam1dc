package com.samil.dc.service.worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcCredentialDAO;
import com.samil.dc.domain.DcCredentialListBean;
import com.samil.dc.domain.DcCredentialSearchConditionBean;
import com.samil.dc.util.Constants.CredentialParam;

/**
 * <pre>
 * ====================================================================================
 * Credential 목록 조회
 * ====================================================================================
 * </pre>
 */
public class DcCredentialListServiceWorker extends AbstractServiceWorker {
	private static final Logger logger = Logger.getRootLogger();
	
	public DcCredentialListServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
		super(request, response, connection);
	}

	@Override
	public ValidationResult validate() throws Exception {
		// 사용자 검증
		ValidationResult validationResult = ServiceHelper.validateUser(request);
		if (!validationResult.isValid()) {
			return validationResult;
		}
		validationResult = ServiceHelper.validateParameterBlank(request, CredentialParam.PARAM_CREDTGTCD);
		if (!validationResult.isValid()) {
			return validationResult;
		}
		
		return new ValidationResult(true);
	}

	@Override
	public Object process() throws Exception {
		// 검색 조건
		DcCredentialSearchConditionBean searchCondition = ServiceHelper.parseSearchCondition(request);
		
		DcCredentialDAO credDAO = new DcCredentialDAO(connection);
		List<DcCredentialListBean> list = new ArrayList<DcCredentialListBean>();
		try {
			list = credDAO.sqlSelectSetupConsultTypeList(searchCondition);
			if (list == null) {
				list = new ArrayList<DcCredentialListBean>();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("list", list);

		return data;
	}
}
