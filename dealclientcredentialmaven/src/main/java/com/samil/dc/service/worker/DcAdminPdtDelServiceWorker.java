package com.samil.dc.service.worker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcAdminConsultDAO;
import com.samil.dc.dao.DcAdminPdtDAO;
import com.samil.dc.dao.DcLogDAO;
import com.samil.dc.domain.DcAdminPdtListBean;
import com.samil.dc.domain.DcLogBean;
import com.samil.dc.util.Constants;
import com.samil.dc.util.SessionUtil;

/**
 * <pre>
 * ====================================================================================
 * PDT 삭제
 * ====================================================================================
 * </pre>
 */
public class DcAdminPdtDelServiceWorker extends AbstractServiceWorker {

	private static final String PARAM_YEAR = "year";

	public DcAdminPdtDelServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
		super(request, response, connection);
	}

	@Override
	public ValidationResult validate() throws Exception {
		// 사용자 검증
		ValidationResult validationResult = ServiceHelper.validateUser(request);
		if (!validationResult.isValid()) {
			return validationResult;
		}

		// required [year | 연도]
		validationResult = ServiceHelper.validateParameterBlank(request, PARAM_YEAR);
		if (!validationResult.isValid()) {
			return validationResult;
		}
				
		return new ValidationResult(true);
	}

	@Override
	public Object process() throws Exception {
		// 파라미터
		String empno = SessionUtil.getUserSession(request).getUserBeans().getEMPLNO();
		String year = ServiceHelper.getParameter(request, PARAM_YEAR);
		
		DcAdminPdtDAO pdtDAO = new DcAdminPdtDAO(connection);
		DcLogDAO logDAO = new DcLogDAO(connection);
		
		Calendar cal = Calendar.getInstance();
		int currYear = cal.get(Calendar.YEAR);
		int currMonth = cal.get(Calendar.MONTH) + 1;
		
		if(currMonth < 7) {
			currYear = currYear - 1;
		}
		
		try {
			if(Integer.parseInt(year) <= currYear) {
				return new ServiceError(Constants.ErrorCode.NOT_VALID_PARAMETER_TYPE, Constants.ErrorMessage.NOT_VALID_PARAMETER_TYPE);
			}
			
			int result = pdtDAO.sqlDeletePdtctg(year);
			if (result == 0) {
				return new ServiceError(Constants.ErrorCode.FAIL_SQL_DELETE, Constants.ErrorMessage.FAIL_SQL_DELETE);
			}
			
			result = pdtDAO.sqlDeletePdt(year);
			if (result == 0) {
				return new ServiceError(Constants.ErrorCode.FAIL_SQL_DELETE, Constants.ErrorMessage.FAIL_SQL_DELETE);
			}

			// 로그 저장
			DcLogBean log = new DcLogBean(Constants.LogType.PRODUCT_MANAGE);
			log.setLogcd(Constants.LogType.PRODUCT_MANAGE);
			log.setAltcd(Constants.LogMethod.DELETE);
			log.setFildnm("참조년도");
			log.setFildcd("year");
			log.setBefval(year);
			log.setAltval("");
			log.setPrjtcd("");
			log.setBigo("참조년도 Product 관리 삭제");
			log.setAltempno(empno);
			
			logDAO.sqlLogCommon(log);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Map<String, Object> data = new HashMap<String, Object>();
		return data;
	}
	
}
