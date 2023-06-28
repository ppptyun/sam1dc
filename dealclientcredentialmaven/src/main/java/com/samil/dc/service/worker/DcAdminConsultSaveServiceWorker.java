package com.samil.dc.service.worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcAdminConsultDAO;
import com.samil.dc.dao.DcLogDAO;
import com.samil.dc.domain.AdminConsultListBean;
import com.samil.dc.domain.DcLogBean;
import com.samil.dc.util.Constants;
import com.samil.dc.util.SessionUtil;

/**
 * <pre>
 * ====================================================================================
 * 자문사 관리 저장
 * ====================================================================================
 * </pre>
 */
public class DcAdminConsultSaveServiceWorker extends AbstractServiceWorker {

	public DcAdminConsultSaveServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
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
		DcAdminConsultDAO consultDAO = new DcAdminConsultDAO(connection);
		DcLogDAO logDAO = new DcLogDAO(connection);
		
		// 파라미터
		List<AdminConsultListBean> list = ParameterHelper.getConsultBizList(request);
		String creempno = SessionUtil.getUserSession(request).getUserBeans().getEMPLNO();
		
		if (list == null || list.size() == 0) {
			return false;
		}
		for (AdminConsultListBean biz : list) {
			if("new".equals(biz.getCompcd())) {
				consultDAO.sqlInsertConsult(biz, creempno);
			} else {
				consultDAO.sqlUpdateConsult(biz);
			}
		}
		
		// 로그 저장
		List<AdminConsultListBean> befList = consultDAO.sqlSelectConsultList();
		List<DcLogBean> logList = new ArrayList<DcLogBean>();
		
		for (AdminConsultListBean befData : befList) {
			for(AdminConsultListBean aftData : list) {
				if(befData.getCompcd().equals(aftData.getCompcd())) {
					DcLogBean log = new DcLogBean(Constants.LogType.CONSULT_MANAGE);
					log.setLogcd(Constants.LogType.CONSULT_MANAGE);
					log.setAltcd(Constants.LogMethod.UPDATE);
					log.setFildnm("");
					log.setFildcd("");
					log.setBefval(befData.toString());
					log.setAltval(aftData.toString());
					log.setPrjtcd("");
					log.setBigo("자문사 관리 변경");
					log.setAltempno(creempno);
					logList.add(log);
				}
			}
		}
		
		for(AdminConsultListBean aftData : list) {
			if("new".equals(aftData.getCompcd())) {
				DcLogBean log = new DcLogBean(Constants.LogType.CONSULT_MANAGE);
				log.setLogcd(Constants.LogType.CONSULT_MANAGE);
				log.setAltcd(Constants.LogMethod.INSERT);
				log.setFildnm("");
				log.setFildcd("");
				log.setBefval("");
				log.setAltval(aftData.toString());
				log.setPrjtcd("");
				log.setBigo("자문사 관리 추가");
				log.setAltempno(creempno);
				logList.add(log);
			}
		}
		
		logDAO.sqlLogCommon(logList);
		
		Map<String, Object> data = new HashMap<String, Object>();
		return data;
	}

}
