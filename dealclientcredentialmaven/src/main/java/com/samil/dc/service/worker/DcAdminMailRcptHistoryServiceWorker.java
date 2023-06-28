package com.samil.dc.service.worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.IllegalBlockSizeException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcAdminMailProfileDAO;
import com.samil.dc.domain.AdminMailRcptHistoryBean;
import com.samil.dc.util.Constants;

/*
 * 발송된 메일을 수신한 대상자 리스트 가져오기
 * */
public class DcAdminMailRcptHistoryServiceWorker extends AbstractServiceWorker  {

	public DcAdminMailRcptHistoryServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
		super(request, response, connection);
	}

	@Override
	public ValidationResult validate() throws Exception {
		ValidationResult validationResult = ServiceHelper.validateParameterBlank(request, "mid");
		if (!validationResult.isValid()) {
			return validationResult;
		}
		
		return new ValidationResult(true);
	}

	@Override
	public Object process() throws Exception {
		String mid 	= ServiceHelper.getParameter(request, "mid");
		DcAdminMailProfileDAO mpdao = new DcAdminMailProfileDAO(connection);
		List<AdminMailRcptHistoryBean> list = new ArrayList<AdminMailRcptHistoryBean>();
		
		try {
			list = mpdao.sqlSelectMailRcptHistory(mid);
			if (list == null) {
				list = new ArrayList<AdminMailRcptHistoryBean>();
			}
		} catch (IllegalBlockSizeException e){
			return new ServiceError(Constants.ErrorCode.FAIL_MAIL_RCPT_DECRYPTION, Constants.ErrorMessage.FAIL_MAIL_RCPT_DECRYPTION);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("list", list);
		
		return data;		
	}

}
