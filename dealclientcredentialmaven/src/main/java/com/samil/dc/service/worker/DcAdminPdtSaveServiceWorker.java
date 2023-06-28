package com.samil.dc.service.worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcAdminPdtDAO;
import com.samil.dc.dao.DcLogDAO;
import com.samil.dc.domain.DcAdminPdtListBean;
import com.samil.dc.domain.DcLogBean;
import com.samil.dc.util.Constants;
import com.samil.dc.util.SessionUtil;

/**
 * <pre>
 * ====================================================================================
 * PDT 저장
 * ====================================================================================
 * </pre>
 */
public class DcAdminPdtSaveServiceWorker extends AbstractServiceWorker {

	private static final String PARAM_ITEMS = "items";

	public DcAdminPdtSaveServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
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
		String empno = SessionUtil.getUserSession(request).getUserBeans().getEMPLNO();

		DcAdminPdtDAO pdtDAO = new DcAdminPdtDAO(connection);
		try {
			List<DcAdminPdtListBean> altList = getParameterList(request);
			for (DcAdminPdtListBean pdt : altList) {
				pdtDAO.sqlMergePdt(pdt, empno);
			}
			
			// 변경 로그
			String refyearly = "";
			if (altList != null && altList.size() > 0) {
				refyearly = altList.get(0).getRefyearly();
				List<DcAdminPdtListBean> befList = pdtDAO.sqlSelectPdtList(refyearly);
				if (befList == null) {
					befList = new ArrayList<DcAdminPdtListBean>();
				}

				DcLogDAO logDAO = new DcLogDAO(connection);
				logDAO.sqlLogCommon(parseAltLogList(befList, altList, empno));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> data = new HashMap<String, Object>();
		return data;
	}
	
	private List<DcAdminPdtListBean> getParameterList(HttpServletRequest request) {
		List<DcAdminPdtListBean> list = new ArrayList<DcAdminPdtListBean>();
		
		int count = ParameterHelper.getCategoryCount(request, PARAM_ITEMS);
		for (int i = 0; i < count; i++) {
			DcAdminPdtListBean item = new DcAdminPdtListBean();
			item.setRefyearly(StringUtils.defaultString(ParameterHelper.getParameter(request, PARAM_ITEMS, i, "refyearly"), ""));
			item.setCtgcd(StringUtils.defaultString(ParameterHelper.getParameter(request, PARAM_ITEMS, i, "ctgcd"), ""));
			item.setPdtcd(StringUtils.defaultString(ParameterHelper.getParameter(request, PARAM_ITEMS, i, "pdtcd"), ""));
			item.setPdtnm(StringUtils.defaultString(ParameterHelper.getParameter(request, PARAM_ITEMS, i, "pdtnm"), ""));
			item.setComnm(StringUtils.defaultString(ParameterHelper.getParameter(request, PARAM_ITEMS, i, "comnm"), ""));
			item.setCredcd(StringUtils.defaultString(ParameterHelper.getParameter(request, PARAM_ITEMS, i, "credcd"), ""));
			item.setConscd1(StringUtils.defaultString(ParameterHelper.getParameter(request, PARAM_ITEMS, i, "conscd1"), ""));
			item.setConscd2(StringUtils.defaultString(ParameterHelper.getParameter(request, PARAM_ITEMS, i, "conscd2"), ""));
			item.setConscd3(StringUtils.defaultString(ParameterHelper.getParameter(request, PARAM_ITEMS, i, "conscd3"), ""));
			item.setSamactcd(StringUtils.defaultString(ParameterHelper.getParameter(request, PARAM_ITEMS, i, "samactcd"), ""));
			item.setSambizcd(StringUtils.defaultString(ParameterHelper.getParameter(request, PARAM_ITEMS, i, "sambizcd"), ""));
			item.setUseyn(StringUtils.defaultString(ParameterHelper.getParameter(request, PARAM_ITEMS, i, "useyn"), ""));
			list.add(item);
		}
		
		return list;
	}
	
	private List<DcLogBean> parseAltLogList(List<DcAdminPdtListBean> befList, List<DcAdminPdtListBean> altList, String empno) {
		List<DcLogBean> logList = new ArrayList<DcLogBean>();
		
		for (DcAdminPdtListBean altPdt : altList) {
			DcAdminPdtListBean befPdt = existItem(altPdt, befList);
			DcLogBean log = new DcLogBean(Constants.LogType.PRODUCT_MANAGE);
			if (befPdt == null) {
				log.setAltcd(Constants.LogMethod.INSERT);
				log.setBefval("");
				log.setAltval(altPdt.toString());
				log.setBigo("PDT 신규");
			} else {
				log.setAltcd(Constants.LogMethod.UPDATE);
				log.setBefval(befPdt.toString());
				log.setAltval(altPdt.toString());
				log.setBigo("PDT 수정");
			}
			log.setFildnm("");
			log.setFildcd("");
			log.setPrjtcd("");
			log.setAltempno(empno);
			
			logList.add(log);
		}
		
		return logList;
	}
	
	private DcAdminPdtListBean existItem(DcAdminPdtListBean search, List<DcAdminPdtListBean> pool) {
		for (DcAdminPdtListBean item : pool) {
			if (item.getPdtcd().equals(search.getPdtcd())) {
				return item;
			}
		}
		return null;
	}
}
