package com.samil.dc.service.worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcAdminPEDAO;
import com.samil.dc.dao.DcLogDAO;
import com.samil.dc.domain.AdminPEListBean;
import com.samil.dc.domain.DcLogBean;
import com.samil.dc.util.Constants;
import com.samil.dc.util.SessionUtil;

/**
 * <pre>
 * ====================================================================================
 * PE 관리 저장
 * ====================================================================================
 * </pre>
 */
public class DcAdminPESaveServiceWorker extends AbstractServiceWorker {

	private static final String PARAM_ITEMS = "items";
	private static final String SPLIT_ITEMS = "|";

	public DcAdminPESaveServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
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
		validationResult = ServiceHelper.validateParameterExist(request, PARAM_ITEMS);
		if (!validationResult.isValid()) {
			return validationResult;
		}

		return new ValidationResult(true);
	}

	@Override
	public Object process() throws Exception {
		DcAdminPEDAO peDAO = new DcAdminPEDAO(connection);
		DcLogDAO logDAO = new DcLogDAO(connection);

		// 파라미터
		String items = StringUtils.defaultString(ServiceHelper.getParameter(request, PARAM_ITEMS), "");
		String creempno = SessionUtil.getUserSession(request).getUserBeans().getEMPLNO();

		// 신규 등록 및 삭제
		List<AdminPEListBean> befList = peDAO.sqlSelectPEList();
		String[] altList = StringUtils.split(items, SPLIT_ITEMS);
		
		List<String> newInducdList = getNewItemList(befList, altList);
		List<String> delInducdList = getDelItemList(befList, altList);
		List<DcLogBean> logList = new ArrayList<DcLogBean>();
		
		for (String inducd : newInducdList) {
			peDAO.sqlInsertPE(inducd, creempno);
			
			DcLogBean log = new DcLogBean(Constants.LogType.CREDENTIAL);
			log.setLogcd(Constants.LogType.PE_MANAGE);
			log.setAltcd(Constants.LogMethod.INSERT);
			log.setFildnm("업종코드");
			log.setFildcd("inducd");
			log.setBefval("");
			log.setAltval(inducd);
			log.setPrjtcd("");
			log.setBigo("PE 관리 신규");
			log.setAltempno(creempno);
			logList.add(log);
		}
		for (String inducd : delInducdList) {
			peDAO.sqlDeletePE(inducd);
			
			DcLogBean log = new DcLogBean(Constants.LogType.CREDENTIAL);
			log.setLogcd(Constants.LogType.PE_MANAGE);
			log.setAltcd(Constants.LogMethod.DELETE);
			log.setFildnm("업종코드");
			log.setFildcd("inducd");
			log.setBefval(inducd);
			log.setAltval("");
			log.setPrjtcd("");
			log.setBigo("PE 관리 삭제");
			log.setAltempno(creempno);
			logList.add(log);
		}
		
		// 로그 저장
		logDAO.sqlLogCommon(logList);
		
		Map<String, Object> data = new HashMap<String, Object>();
		return data;
	}
	
	// 신규 항목 찾기
	private List<String> getNewItemList(List<AdminPEListBean> befList, String[] altList) {
		List<String> list = new ArrayList<String>();
		if (altList == null) {
			return list;
		}
		
		for (String inducd : altList) {
			boolean exist = false;
			for (AdminPEListBean item : befList) {
				if ("1".equals(item.getChkval()) && inducd.equals(item.getInducd())) {
					exist = true;
					break;
				}
			}
			if (!exist) {
				list.add(inducd);
			}
		}
		
		return list;
	}
	
	// 삭제 항목 찾기
	private List<String> getDelItemList(List<AdminPEListBean> befList, String[] altList) {
		List<String> list = new ArrayList<String>();
		if (altList == null) {
			return list;
		}
		
		for (AdminPEListBean item : befList) {
			if ("1".equals(item.getChkval())) {
				boolean exist = false;
				for (String inducd : altList) {
					if (inducd.equals(item.getInducd())) {
						exist = true;
						break;
					}
				}
				if (!exist) {
					list.add(item.getInducd());					
				}
			}
		}
		
		return list;
	}
}
