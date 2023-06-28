package com.samil.dc.service.worker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcCredentialDAO;
import com.samil.dc.dao.DcSupportDAO;
import com.samil.dc.domain.CommonCodeListBean;
import com.samil.dc.domain.DcCredentialBean;
import com.samil.dc.util.Constants;

/**
 * <pre>
 * ====================================================================================
 * Credential 상세 조회
 * ====================================================================================
 * </pre>
 */
public class DcCredentialDetailServiceWorker extends AbstractServiceWorker {

	private final static String PARAM_PRJTCD = "prjtcd";

	public DcCredentialDetailServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
		super(request, response, connection);
	}

	@Override
	public ValidationResult validate() throws Exception {
		// 사용자 검증
		ValidationResult validationResult = ServiceHelper.validateUser(request);
		if (!validationResult.isValid()) {
			return validationResult;
		}
		
		validationResult = ServiceHelper.validateParameterBlank(request, PARAM_PRJTCD);
		if (!validationResult.isValid()) {
			return validationResult;
		}
		
		return new ValidationResult(true);
	}

	@Override
	public Object process() throws Exception {
		String prjtcd = ServiceHelper.getParameter(request, PARAM_PRJTCD);
		
		DcCredentialDAO credDAO = new DcCredentialDAO(connection);
		DcSupportDAO supportDAO = new DcSupportDAO(connection);
		DcCredentialBean project = null;
		Map<String, List<CommonCodeListBean>> codes = new HashMap<String, List<CommonCodeListBean>>();
		
		try {
			// 프로젝트 정보 조회
			Object rtnObj = ServiceHelper.getCredentialProject(credDAO, prjtcd);
			if (rtnObj instanceof ServiceError) {
				return (ServiceError) rtnObj;
			} else if (rtnObj instanceof DcCredentialBean) {
				project = (DcCredentialBean) rtnObj;
			} else {
				return new ServiceError(Constants.ErrorCode.INTERNAL, Constants.ErrorMessage.INTERNAL);
			}
			
			// 필요한 공통코드
			// BRS 채권종류-담보/무담보[122/126]
			codes.put("122", supportDAO.sqlSelectCommonCodeList("122"));
			codes.put("126", supportDAO.sqlSelectCommonCodeList("126"));
			// BRS 매각방식[123]
			codes.put("123", supportDAO.sqlSelectCommonCodeList("123"));
			// RCF 구분/레져/인프라[124/130/131]
			codes.put("124", supportDAO.sqlSelectCommonCodeList("124"));
			codes.put("130", supportDAO.sqlSelectCommonCodeList("130"));
			codes.put("131", supportDAO.sqlSelectCommonCodeList("131"));
			// 대상여부[132]
			codes.put("132", supportDAO.sqlSelectCommonCodeList("132"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("project", project);
		data.put("codes", codes);

		return data;
	}
}
