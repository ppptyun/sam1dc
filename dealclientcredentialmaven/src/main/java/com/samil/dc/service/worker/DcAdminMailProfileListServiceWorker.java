package com.samil.dc.service.worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcAdminMailProfileDAO;
import com.samil.dc.domain.AdminMailProfileBean;

public class DcAdminMailProfileListServiceWorker extends AbstractServiceWorker  {
	private static final Logger logger = Logger.getRootLogger();
	
	public DcAdminMailProfileListServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
		super(request, response, connection);
	}
	
	@Override
	public ValidationResult validate() throws Exception {
		ValidationResult validationResult = ServiceHelper.validateUser(request);
		if (!validationResult.isValid()) {
			return validationResult;
		}
		
		return new ValidationResult(true);
	}
	
	@Override
	public Object process() throws Exception {
		List<AdminMailProfileBean> list = new ArrayList<AdminMailProfileBean>();
		DcAdminMailProfileDAO mpdao = new DcAdminMailProfileDAO(connection);
		try {
			list = mpdao.sqlSelectMailProfileList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("list", list);
		
		return data;
	}
	
	
	
}
