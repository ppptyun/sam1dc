package com.samil.dc.service.worker;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcAdminConsultDAO;
import com.samil.dc.dao.DcLogDAO;
import com.samil.dc.domain.DcLogBean;
import com.samil.dc.util.Constants;
import com.samil.dc.util.SessionUtil;

/**
 * <pre>
 * ====================================================================================
 * 자문사 관리 삭제 (사용 안함 처리)
 * ====================================================================================
 * </pre>
 */
public class DcAdminConsultDelServiceWorker extends AbstractServiceWorker {

	private static final String PARAM_COMPCD = "compcd";
	
	public DcAdminConsultDelServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
		super(request, response, connection);
	}

	@Override
	public ValidationResult validate() throws Exception {
		// 사용자 검증
		ValidationResult validationResult = ServiceHelper.validateUser(request);
		if (!validationResult.isValid()) {
			return validationResult;
		}

		// 파라미터 compcd
		validationResult = ServiceHelper.validateParameterBlank(request, PARAM_COMPCD);
		if (!validationResult.isValid()) {
			return validationResult;
		}
				
		return new ValidationResult(true);
	}

	@Override
	public Object process() throws Exception {
		DcAdminConsultDAO consultDAO = new DcAdminConsultDAO(connection);
		DcLogDAO logDAO = new DcLogDAO(connection);
		
		// 파라미터
		String compcd = ServiceHelper.getParameter(request, PARAM_COMPCD);
		String creempno = SessionUtil.getUserSession(request).getUserBeans().getEMPLNO();
		
		try {
			int result = consultDAO.sqlDeleteConsult(compcd);
			if (result != 1) {
				return new ServiceError(Constants.ErrorCode.FAIL_SQL_DELETE, Constants.ErrorMessage.FAIL_SQL_DELETE);
			}

			// 로그 저장
			DcLogBean log = new DcLogBean(Constants.LogType.CONSULT_MANAGE);
			log.setLogcd(Constants.LogType.CONSULT_MANAGE);
			log.setAltcd(Constants.LogMethod.DELETE);
			log.setFildnm("기업코드");
			log.setFildcd("compcd");
			log.setBefval(compcd);
			log.setAltval("");
			log.setPrjtcd("");
			log.setBigo("자문사 관리 사용 안함");
			log.setAltempno(creempno);
			
			logDAO.sqlLogCommon(log);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Map<String, Object> data = new HashMap<String, Object>();
		return data;
	}

}
