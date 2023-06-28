package com.samil.dc.service.worker;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcAdminEditAuthDAO;
import com.samil.dc.dao.DcLogDAO;
import com.samil.dc.domain.AdminEditAuthListBean;
import com.samil.dc.util.Constants;
import com.samil.dc.util.SessionUtil;

/**
 * <pre>
 * ====================================================================================
 * League Table 편집 관리 저장
 * ====================================================================================
 * </pre>
 */
public class DcAdminEditAuthSaveServiceWorker extends AbstractServiceWorker {

	private static final String PARAM_ITEMS = "items";
	private static final String SPLIT_ITEMS = "|";
	private static final String SPLIT_DATAS = "^";
	private static final String NEW_ITEM = "new";

	public DcAdminEditAuthSaveServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
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
		DcAdminEditAuthDAO editauthDAO = new DcAdminEditAuthDAO(connection);
		DcLogDAO logDAO = new DcLogDAO(connection);

		// 파라미터
		String items = ServiceHelper.getParameter(request, PARAM_ITEMS);
		String creempno = SessionUtil.getUserSession(request).getUserBeans().getEMPLNO();

		// 업데이트 및 등록
		String[] arrItems = StringUtils.split(items, SPLIT_ITEMS);
		for (String datas : arrItems) {
			String[] arrDatas = StringUtils.split(datas, SPLIT_DATAS);
			String originrolecd = arrDatas[0];
			String rolecd = arrDatas[1];
			String edityn = arrDatas[2];

			int result = 0;
			if (NEW_ITEM.equals(originrolecd)) {
				result = editauthDAO.sqlInsertEditAuth(rolecd, edityn);
				if (result != 1) {
					return new ServiceError(Constants.ErrorCode.FAIL_SQL_INSERT, Constants.ErrorMessage.FAIL_SQL_INSERT);
				}
				logDAO.sqlLogEditAuthNew(rolecd, edityn, creempno);

			} else {
				AdminEditAuthListBean befEditAuth = editauthDAO.sqlSelectEditAuthDetail(originrolecd);
				result = editauthDAO.sqlUpdateEditAuth(originrolecd, rolecd, edityn);
				if (result != 1) {
					return new ServiceError(Constants.ErrorCode.FAIL_SQL_UPDATE, Constants.ErrorMessage.FAIL_SQL_UPDATE);
				}
				logDAO.sqlLogEditAuthUpd(befEditAuth.getRolecd(), befEditAuth.getEdityn(), rolecd, edityn, creempno);
			}
		}

		Map<String, Object> data = new HashMap<String, Object>();
		return data;
	}
}
