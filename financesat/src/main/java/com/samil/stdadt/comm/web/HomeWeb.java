package com.samil.stdadt.comm.web;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.samil.stdadt.comm.service.AppCommService;
import com.samil.stdadt.comm.util.AppConstant;
import com.samil.stdadt.comm.util.AppHelper;
import com.samil.stdadt.comm.vo.ApplicationConfigVO;
import com.samil.stdadt.comm.vo.UserSessionVO;

@Controller
@RequestMapping("/")
public class HomeWeb {
	
	@Autowired
	AppCommService commService;
	
	@Autowired
	ApplicationConfigVO appConfig;
	
	@RequestMapping("/")	
	public String pageHome(HttpServletRequest request) throws Exception {
		String appCd = appConfig.getAppCd();
		UserSessionVO userSession= (UserSessionVO) request.getSession().getAttribute(AppConstant.sesseionKey);
		String homeUri = commService.getHomeURI(appCd, userSession.getRoleCd());
		
		return "redirect:" + homeUri;
	}
	
	@RequestMapping("/error/{errorCode}")
	public String redirectError(@PathVariable("errorCode") String errorCode) {
		List<String> list = Arrays.asList("401", "403", "404", "500");
		if(!list.contains(errorCode)) errorCode = "500"; 
		return "/redirect/err" + errorCode;
	}
	
	@RequestMapping(path="/auth/login", method=RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, String> param) {
		DataOutputStream out = null;
		BufferedReader in = null;
		HttpsURLConnection conn = null;
		
		try {
			if (param != null) {
				System.out.println(AppHelper.convertObjectToJsonString(param));
				String accessToken = param.get("access_token");
//				String expire = param.get("expire");
				
				Map<String, Object> userInfo = (Map<String, Object>) commService.getUserInfoByAccessToken(accessToken);
				if(userInfo == null) return "redirect:/error/401";
				
				UserSessionVO userSession = commService.getUserSessionInfo(appConfig.getAppCd(), (String) userInfo.get("uid"));
				
				if(userSession != null) {
					AppHelper.setSession(request, userSession);
					return "redirect:/";	
				}
				else {					
					return "redirect:/error/403";
				}
			}
			return "redirect:/error/401";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/error/500";
		} finally {
			try {if(out != null) out.close();} catch (Exception e) {}
			try {if(in != null) in.close();} catch(Exception e) {}
			try {if(conn != null) conn.disconnect();} catch(Exception e) {}
		}
	}
}
