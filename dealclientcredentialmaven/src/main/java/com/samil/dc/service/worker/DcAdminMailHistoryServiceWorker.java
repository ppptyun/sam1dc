package com.samil.dc.service.worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcAdminMailProfileDAO;
import com.samil.dc.domain.AdminMailHistoryBean;

public class DcAdminMailHistoryServiceWorker extends AbstractServiceWorker {

	public DcAdminMailHistoryServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
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
		String mid 		= null;
		ValidationResult validationResult = ServiceHelper.validateParameterBlank(request, "mid");
		if (validationResult.isValid()) {
			mid = ServiceHelper.getParameter(request, "mid"); 
		}
		
		
		DcAdminMailProfileDAO mpdao = new DcAdminMailProfileDAO(connection);
		List<AdminMailHistoryBean> list = new ArrayList<AdminMailHistoryBean>();
		
		
		try {
			if(mid == null){
				list = mpdao.sqlSelectMailHistory(mailid);	
			}else{
				list = mpdao.sqlSelectMailHistory(mailid, mid);
			}
			if (list == null) {
				list = new ArrayList<AdminMailHistoryBean>();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("list", list);
		
		return data;
	}

}
