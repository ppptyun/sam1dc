package com.samil.dc.service.worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcAdminMailProfileDAO;
import com.samil.dc.domain.AdminMailRecipientBean;

/*
 * 메일 프로파일에 등록된 수신자 리스트 가져오기 (담당자, 참조 수신자 등)
 */
public class DcAdminMailProfileRcptListServiceWorker extends AbstractServiceWorker   {

	public DcAdminMailProfileRcptListServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
		super(request, response, connection);
	}

	@Override
	public ValidationResult validate() throws Exception {
		ValidationResult validationResult = ServiceHelper.validateParameterExist(request, "mailid");
		if (!validationResult.isValid()) {
			return validationResult;
		}
		
		return new ValidationResult(true);
	}

	@Override
	public Object process() throws Exception {
		String mailid = ServiceHelper.getParameter(request, "mailid");
		
		List<AdminMailRecipientBean> list = new ArrayList<AdminMailRecipientBean>();
		DcAdminMailProfileDAO mpdao = new DcAdminMailProfileDAO(connection);
		try {
			list = mpdao.sqlSelectRcptList(mailid);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("list", list);
		
		return data;
	}

}
