package com.samil.dc.service.worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcAdminFieldLogDAO;
import com.samil.dc.domain.AdminFieldLogListBean;

public class DcAdminFieldLogSearchServiceWorker extends AbstractServiceWorker {

	private static final String PARAM_PAGING_PAGE = "page";
	private static final String PARAM_PAGING_ROWS = "rows";
	private static final String PARAM_PROJECT_CODE = "prjtcd";
	private static final String PARAM_LOG_CODE = "logcd";
	private static final String PARAM_ALT_CODE = "altcd";
	private static final String PARAM_EMP_NO = "empno";
	private static final String DEFAULT_PAGING_ROWS = "20";

	public DcAdminFieldLogSearchServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
		super(request, response, connection);
	}

	@Override
	public ValidationResult validate() throws Exception {
		// 사용자 검증
		ValidationResult validationResult = ServiceHelper.validateUser(request);
		if (!validationResult.isValid()) {
			return validationResult;
		}

		// required [page | ]
		validationResult = ServiceHelper.validateParameterBlank(request, PARAM_PAGING_PAGE);
		if (!validationResult.isValid()) {
			return validationResult;
		}

		// required [rows | ]
		validationResult = ServiceHelper.validateParameterBlank(request, PARAM_PAGING_ROWS);
		if (!validationResult.isValid()) {
			return validationResult;
		}

		// required [prjtcd | ]
		validationResult = ServiceHelper.validateParameterExist(request, PARAM_PROJECT_CODE);
		if (!validationResult.isValid()) {
			return validationResult;
		}

		// required [logcd | ]
		validationResult = ServiceHelper.validateParameterExist(request, PARAM_LOG_CODE);
		if (!validationResult.isValid()) {
			return validationResult;
		}
		
		// required [altcd | ]
		validationResult = ServiceHelper.validateParameterExist(request, PARAM_ALT_CODE);
		if (!validationResult.isValid()) {
			return validationResult;
		}

		// required [empno | ]
		validationResult = ServiceHelper.validateParameterExist(request, PARAM_EMP_NO);
		if (!validationResult.isValid()) {
			return validationResult;
		}

		return new ValidationResult(true);
	}

	@Override
	public Object process() throws Exception {
		String page = StringUtils.defaultIfBlank(ServiceHelper.getParameter(request, PARAM_PAGING_PAGE), "1");
		String rows = StringUtils.defaultIfBlank(ServiceHelper.getParameter(request, PARAM_PAGING_ROWS), DEFAULT_PAGING_ROWS);
		String prjtcd = StringUtils.defaultIfBlank(ServiceHelper.getParameter(request, PARAM_PROJECT_CODE), "");
		String logcd = StringUtils.defaultIfBlank(ServiceHelper.getParameter(request, PARAM_LOG_CODE), "");
		String altcd = StringUtils.defaultIfBlank(ServiceHelper.getParameter(request, PARAM_ALT_CODE), "");
		String empno = StringUtils.defaultIfBlank(ServiceHelper.getParameter(request, PARAM_EMP_NO), "");

		int iPage = Integer.valueOf(page);
		int iRows = Integer.valueOf(rows);
		int posStart = (iPage - 1) * iRows;
		int count = iRows;

		DcAdminFieldLogDAO fieldLogDAO = new DcAdminFieldLogDAO(connection);
		Integer totalCount = 0;
		List<AdminFieldLogListBean> list = new ArrayList<AdminFieldLogListBean>();
		try {
			totalCount = fieldLogDAO.sqlSelectFieldLogListCount(prjtcd, logcd, altcd, empno);
			list = fieldLogDAO.sqlSelectFieldLogList(posStart, count, prjtcd, logcd, altcd, empno);
			if (list == null) {
				list = new ArrayList<AdminFieldLogListBean>();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("total", totalCount);
		data.put("list", list);

		return data;
	}
}
