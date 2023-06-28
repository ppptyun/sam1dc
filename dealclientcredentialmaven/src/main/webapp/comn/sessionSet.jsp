<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.samil.dc.core.ContextLoader"%>
<%@ page import="com.samil.dc.domain.UserSessionBeans"%>
<%@ page import="com.samil.dc.domain.UserBeans"%>
<%@ page import="com.samil.dc.domain.UserRoleBeans"%>
<%@ page import="com.samil.dc.domain.UserMenuBeans"%>
<%@ page import="com.samil.dc.service.ApplicationGlobal"%>
<%@ page import="com.samil.dc.service.worker.ServiceHelper"%>
<%@ page import="com.samil.dc.config.DcServiceConfigParser"%>
<%@ page import="org.json.JSONObject"%>
<%@ page import="org.json.JSONArray"%>

<%

	String ContextRoot 				= request.getContextPath();
	String redirectPortalUri		= "";
	String redirectPortalFullUri 	= "";
	JSONObject serviceConfig  		= null;

	//#################################################################################
	// [시작] PWC 인증으로 수정함. 2021-09-02 남웅주 
	//#################################################################################
	/*
	if(ContextLoader.getContext("ConnectToOper").equals("Y")){
		//운영 서버 접속 시
		redirectPortalUri	= "https://portal.samil.com";
	}else{
		//테스트 서버 접속 시
		redirectPortalUri	= "http://tportal.samil.com";	
	}
	*/
	
	//redirectPortalFullUri = redirectPortalUri + "?next=" + request.getRequestURL();
	//redirectPortalFullUri = "https://api-t.samil.com/auth/authorize?redirect_uri=" + request.getRequestURL();
	redirectPortalFullUri = "https://login.samil.com/api/authorize?redirect_uri=" + request.getRequestURL();

	//################################################################################# 
	// [종료] PWC 인증으로 수정함. 2021-09-02 남웅주 
	//#################################################################################
		
	UserSessionBeans userSessionBeans = ApplicationGlobal.getUser(request);
	if (userSessionBeans == null) {
		response.sendRedirect(redirectPortalFullUri);
	}else{
		request.setAttribute("userInfo", userSessionBeans.getUserBeans());
		request.setAttribute("userRoles", userSessionBeans.getUserRoleList());
		request.setAttribute("userMenus", userSessionBeans.getUserMenuList());
		request.setAttribute("serviceConfig", DcServiceConfigParser.getInstance().getConfigByJSON().getJSONObject("data"));
	}
%>
