package com.samil.dc.service.worker;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcAdminPdtDAO;
import com.samil.dc.dao.DcLogDAO;
import com.samil.dc.domain.DcLogBean;
import com.samil.dc.util.Constants;
import com.samil.dc.util.SessionUtil;

public class DcAdminPdtCopyServiceWorker extends AbstractServiceWorker {

	private final static String PARAM_REFYEARLY = "refyearly";

	public DcAdminPdtCopyServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
		super(request, response, connection);
	}

	@Override
	public ValidationResult validate() throws Exception {
		// 사용자 검증
		ValidationResult validationResult = ServiceHelper.validateUser(request);
		if (!validationResult.isValid()) {
			return validationResult;
		}

		validationResult = ServiceHelper.validateParameterBlank(request, PARAM_REFYEARLY);
		if (!validationResult.isValid()) {
			return validationResult;
		}

		return new ValidationResult(true);
	}

	@Override
	public Object process() throws Exception {
		String refyearly = ServiceHelper.getParameter(request, PARAM_REFYEARLY);
		String creempno = SessionUtil.getUserSession(request).getUserBeans().getEMPLNO();

		DcAdminPdtDAO pdtDAO = new DcAdminPdtDAO(connection);
		String nrefyearly = refyearly;
		try {
			// 복사
			nrefyearly = pdtDAO.sqlSelectNextRefyearly();
			pdtDAO.sqlCopyCategoryList(refyearly, nrefyearly, creempno);
			pdtDAO.sqlCopyPdtList(refyearly, nrefyearly, creempno);
			
			// 로그
			DcLogBean log = new DcLogBean(Constants.LogType.PRODUCT_MANAGE);
			log.setAltcd(Constants.LogMethod.INSERT);
			log.setFildnm("참조년도");
			log.setFildcd("refyearly");
			log.setBefval(refyearly);
			log.setAltval(nrefyearly);
			log.setPrjtcd("");
			log.setBigo("[" + refyearly + " -> " + nrefyearly + "] PDT 목록 복사");
			log.setAltempno(creempno);
			
			DcLogDAO logDAO = new DcLogDAO(connection);
			logDAO.sqlLogCommon(log);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("nrefyearly", nrefyearly);
		
		return data;
	}
}
