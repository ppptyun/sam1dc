package com.samil.dc.service.worker;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcAdminMailProfileDAO;

public class DcAdminMailProfileDeleteServiceWorker extends AbstractServiceWorker{

	public DcAdminMailProfileDeleteServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
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
		String mailid 	= ServiceHelper.getParameter(request, "mailid");
		DcAdminMailProfileDAO mpdao = new DcAdminMailProfileDAO(connection);
		
		//1. 전체 수신자 리스트 삭제
		//2. 메일 프로파일 삭제
		mpdao.sqlDeleteAllRcpt(mailid);
		mpdao.sqlDeleteMailProfile(mailid);
		
		Map<String, Object> data = new HashMap<String, Object>();
		return data;
	}

}
