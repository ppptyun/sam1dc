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
import com.samil.dc.domain.DcAdminPdtCategoryListBean;
import com.samil.dc.domain.DcLogBean;
import com.samil.dc.util.Constants;
import com.samil.dc.util.SessionUtil;

/**
 * <pre>
 * ====================================================================================
 * PDT 카테고리 저장
 * ====================================================================================
 * </pre>
 */
public class DcAdminPdtCategorySaveServiceWorker extends AbstractServiceWorker {

	private static final String PARAM_ITEMS = "items";

	public DcAdminPdtCategorySaveServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
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
			List<DcAdminPdtCategoryListBean> altList = getParameterList(request);
			for (DcAdminPdtCategoryListBean category : altList) {
				pdtDAO.sqlMergeCategory(category, empno);
			}
			
			// 변경 로그
			String refyearly = "";
			if (altList != null && altList.size() > 0) {
				refyearly = altList.get(0).getRefyearly();
				List<DcAdminPdtCategoryListBean> befList = pdtDAO.sqlSelectPdtCategoryList(refyearly, Constants.YES, Constants.NO);
				if (befList == null) {
					befList = new ArrayList<DcAdminPdtCategoryListBean>();
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
	
	private List<DcAdminPdtCategoryListBean> getParameterList(HttpServletRequest request) {
		List<DcAdminPdtCategoryListBean> list = new ArrayList<DcAdminPdtCategoryListBean>();
		
		int count = ParameterHelper.getCategoryCount(request, PARAM_ITEMS);
		for (int i = 0; i < count; i++) {
			DcAdminPdtCategoryListBean item = new DcAdminPdtCategoryListBean();
			item.setRefyearly(StringUtils.defaultString(ParameterHelper.getParameter(request, PARAM_ITEMS, i, "refyearly"), ""));
			item.setCtgcd(StringUtils.defaultString(ParameterHelper.getParameter(request, PARAM_ITEMS, i, "ctgcd"), ""));
			item.setCtgnm(StringUtils.defaultString(ParameterHelper.getParameter(request, PARAM_ITEMS, i, "ctgnm"), ""));
			item.setUseyn(Constants.YES);
			list.add(item);
		}
		
		return list;
	}
	
	private List<DcLogBean> parseAltLogList(List<DcAdminPdtCategoryListBean> befList, List<DcAdminPdtCategoryListBean> altList, String empno) {
		List<DcLogBean> logList = new ArrayList<DcLogBean>();
		
		for (DcAdminPdtCategoryListBean altCategory : altList) {
			DcAdminPdtCategoryListBean befCategory = existItem(altCategory, befList);
			DcLogBean log = new DcLogBean(Constants.LogType.PRODUCT_MANAGE);
			if (befCategory == null) {
				log.setAltcd(Constants.LogMethod.INSERT);
				log.setBefval("");
				log.setAltval(altCategory.toString());
				log.setBigo("PDT 카테고리 신규");
			} else {
				log.setAltcd(Constants.LogMethod.UPDATE);
				log.setBefval(befCategory.toString());
				log.setAltval(altCategory.toString());
				log.setBigo("PDT 카테고리 수정");
			}
			log.setFildnm("");
			log.setFildcd("");
			log.setPrjtcd("");
			log.setAltempno(empno);
			
			logList.add(log);
		}
		
		return logList;
	}
	
	private DcAdminPdtCategoryListBean existItem(DcAdminPdtCategoryListBean search, List<DcAdminPdtCategoryListBean> pool) {
		for (DcAdminPdtCategoryListBean item : pool) {
			if (item.getCtgcd().equals(search.getCtgcd())) {
				return item;
			}
		}
		return null;
	}
}
