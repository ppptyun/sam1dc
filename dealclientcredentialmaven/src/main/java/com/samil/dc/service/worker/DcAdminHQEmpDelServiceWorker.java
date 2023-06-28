package com.samil.dc.service.worker;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcAdminHQEmpDAO;
import com.samil.dc.dao.DcLogDAO;
import com.samil.dc.util.Constants;
import com.samil.dc.util.SessionUtil;

/**
 * 본부 담당자 삭제
 *
 */
public class DcAdminHQEmpDelServiceWorker extends AbstractServiceWorker {
	
	private static final String PARAM_ITEMS = "items";
	private static final String SPLIT_ITEMS = "|";
	private static final String SPLIT_DATAS = "^";

	public DcAdminHQEmpDelServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
		super(request, response, connection);
	}

	@Override
	public ValidationResult validate() throws Exception {
		// 사용자 검증
		ValidationResult validationResult = ServiceHelper.validateUser(request);
		if (!validationResult.isValid()) {
			return validationResult;
		}
		
		// 파라미터 items
		validationResult = ServiceHelper.validateParameterBlank(request, PARAM_ITEMS);
		if (!validationResult.isValid()) {
			return validationResult;
		}
		
		return new ValidationResult(true);
	}

	@Override
	public Object process() throws Exception {
		// 파라미터
		String items = ServiceHelper.getParameter(request, PARAM_ITEMS);
		String creempno = SessionUtil.getUserSession(request).getUserBeans().getEMPLNO();
		
		DcAdminHQEmpDAO hqempDAO = new DcAdminHQEmpDAO(connection);
		DcLogDAO logDAO = new DcLogDAO(connection);
		try {
			String[] arrItems = StringUtils.split(items, SPLIT_ITEMS);
			for (String datas : arrItems) {
				String[] arrDatas = StringUtils.split(datas, SPLIT_DATAS);
				String hqcd = arrDatas[0];
				String empno = arrDatas[1];
				int result = hqempDAO.sqlDeleteHQEmp(hqcd, empno);
				if (result != 1) {
					return new ServiceError(Constants.ErrorCode.FAIL_SQL_DELETE, Constants.ErrorMessage.FAIL_SQL_DELETE);
				}
				logDAO.sqlLogHQEmpDel(hqcd, empno, creempno);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> data = new HashMap<String, Object>();
		return data;
	}
}
