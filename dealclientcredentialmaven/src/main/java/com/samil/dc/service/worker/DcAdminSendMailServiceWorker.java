package com.samil.dc.service.worker;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.samil.dc.access.DBConnection;
import com.samil.dc.domain.RTMSUserBean;
import com.samil.dc.util.rtms.RTMSMail;
import com.samil.dc.util.SessionUtil;

public class DcAdminSendMailServiceWorker extends AbstractServiceWorker  {

	public DcAdminSendMailServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
		super(request, response, connection);
	}

	@Override
	public ValidationResult validate() throws Exception {
		
		ValidationResult validationResult = ServiceHelper.validateParameterBlank(request, "mailid");
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
		String rcpt 	= URLDecoder.decode(ServiceHelper.getParameter(request, "rcpt"), "utf-8");
		String emplno	= SessionUtil.getUserSession(request).getUserBeans().getEMPLNO();
		/*String mail		= SessionUtil.getUserSession(request).getUserBeans().getINTEID() + "@samil.com";
		String kornm	= SessionUtil.getUserSession(request).getUserBeans().getKORNM();*/
		
		RTMSUserBean sender = new RTMSUserBean();
		sender.setId(emplno);
		/*sender.setMail(mail);
		sender.setName(kornm);*/
		// 2017-04-13 최지원 요청
		// sender.setMail("DealBusinessLT@samil.com");
		// sender.setName("Deal Business LT");
		// 2022-02-13 이지민 samil.com 도메인 미사용으로 발신 메일 변경
		sender.setMail("kr_deal_leaguetable@pwc.com");
		sender.setName("KR Deal LeagueTable");
		
		
		List<RTMSUserBean> rcptList = new ArrayList<RTMSUserBean>();
		JSONParser jsonParser = new JSONParser();
		RTMSUserBean rcptObj;
		JSONObject jsonObj;
		
		JSONArray jsonArr = (JSONArray) jsonParser.parse(rcpt);
		for(int i=0; i<jsonArr.size(); i++){
			rcptObj = new RTMSUserBean();
			jsonObj = (JSONObject) jsonArr.get(i);
			rcptObj.setId((String) jsonObj.get("rid"));
			rcptObj.setMail((String) jsonObj.get("rmail"));
			rcptObj.setName((String) jsonObj.get("rname"));
			rcptList.add(rcptObj);
		}
		
		
		RTMSMail.sendMail(mailid, sender, rcptList, connection, emplno);
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("msg", "메일 발송이 완료되었습니다.");
		return data;
	}

}
