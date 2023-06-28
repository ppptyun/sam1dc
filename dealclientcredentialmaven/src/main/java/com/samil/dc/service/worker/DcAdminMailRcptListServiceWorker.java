package com.samil.dc.service.worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcAdminMailProfileDAO;
import com.samil.dc.domain.UserSimpleBean;

/*
 * 메일 수신 대상자 가져오기 
 * - 프로파일에 등록된 대상자와 다름
 * - 로직상 해당 메일을 받을 대상자를 가져온다.
 */
public class DcAdminMailRcptListServiceWorker extends AbstractServiceWorker  {

	public DcAdminMailRcptListServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
		super(request, response, connection);
	}

	@Override
	public ValidationResult validate() throws Exception {
		ValidationResult validationResult = ServiceHelper.validateParameterBlank(request, "mailid");
		if (!validationResult.isValid()) {
			return validationResult;
		}
		
		return new ValidationResult(true);
	}

	@Override
	public Object process() throws Exception {
		String mid 	= ServiceHelper.getParameter(request, "mailid");
		DcAdminMailProfileDAO mpdao = new DcAdminMailProfileDAO(connection);
		List<UserSimpleBean> list = new ArrayList<UserSimpleBean>();
		
		try {
			list = mpdao.sqlSelectMailRcptList(mid);
			if (list == null) {
				list = new ArrayList<UserSimpleBean>();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("list", list);
		
		return data;
	}

}
