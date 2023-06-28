package com.samil.dc.service.worker;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcAdminMailProfileDAO;
import com.samil.dc.util.SessionUtil;

public class DcAdminMailProfileSaveServiceWorker extends AbstractServiceWorker{
	private static final Logger logger = Logger.getRootLogger();
	
	public DcAdminMailProfileSaveServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
		super(request, response, connection);
	}

	@Override
	public ValidationResult validate() throws Exception {
	
		ValidationResult validationResult = ServiceHelper.validateParameterBlank(request, "mailid");
		if (!validationResult.isValid()) {
			return validationResult;
		}
		
		validationResult = ServiceHelper.validateParameterBlank(request, "subject");
		if (!validationResult.isValid()) {
			return validationResult;
		}
		
		validationResult = ServiceHelper.validateParameterBlank(request, "useyn");
		if (!validationResult.isValid()) {
			return validationResult;
		}
		
		validationResult = ServiceHelper.validateParameterJSON(request, "rcpt");
		if (!validationResult.isValid()) {
			return validationResult;
		}
		
		return new ValidationResult(true);
	}

	@Override
	public Object process() throws Exception {

		String mailid 	= ServiceHelper.getParameter(request, "mailid");
		String subject 	= ServiceHelper.getParameter(request, "subject");
		String contents	= URLDecoder.decode(ServiceHelper.getParameter(request, "contents").replaceAll("%", "%25"), "utf-8").replaceAll("(\r\n|\n)", "");
		String des 		= URLDecoder.decode(ServiceHelper.getParameter(request, "des"), "utf-8");
		String useyn 	= ServiceHelper.getParameter(request, "useyn");
		String rcpt 	= URLDecoder.decode(ServiceHelper.getParameter(request, "rcpt"), "utf-8");
		String emplno	= SessionUtil.getUserSession(request).getUserBeans().getEMPLNO();
		
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObj = (JSONObject) jsonParser.parse(rcpt);
		
		DcAdminMailProfileDAO mpdao = new DcAdminMailProfileDAO(connection);
		
		// 메일 프로파일 추가 및 업데이트
		mpdao.sqlMergeMailProfile(mailid, subject, contents, des, useyn, emplno);
		
		// 메일 수신자 추가 및 삭제
		JSONArray jsonArr = (JSONArray) jsonObj.get("list");
		for(int i=0; i<jsonArr.size(); i++){
			JSONObject rcptObj = (JSONObject) jsonArr.get(i);
			if("new".equals(rcptObj.get("status"))){
				mpdao.sqlInsertRcpt(mailid, (String) rcptObj.get("rid"), (String) rcptObj.get("rname"), (String) rcptObj.get("rmail"), emplno);
			}else if("delete".equals(rcptObj.get("status"))){
				mpdao.sqlDeleteRcpt(mailid, (String) rcptObj.get("rmail"));
			}
		}
		
		Map<String, Object> data = new HashMap<String, Object>();
		return data;
	}

}
