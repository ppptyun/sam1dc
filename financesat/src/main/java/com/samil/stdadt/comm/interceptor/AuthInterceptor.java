package com.samil.stdadt.comm.interceptor;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samil.stdadt.comm.service.AppCommService;
import com.samil.stdadt.comm.util.AppHelper;
import com.samil.stdadt.comm.vo.ActivityLogVO;
import com.samil.stdadt.comm.vo.AppInfoVO;
import com.samil.stdadt.comm.vo.ApplicationConfigVO;
import com.samil.stdadt.comm.vo.UserSessionVO;

/**
 * 
 * @author shyunchoi
 * <pre>
 * 인증 및 요청 URI 권한 체크
 * </pre>
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {
	
	static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
	
	@Autowired
	AppCommService commService;
	
	@Autowired
	ApplicationConfigVO appConfig;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String appCd = appConfig.getAppCd();
		String uri = request.getRequestURI().replace(request.getContextPath(), "");	// Context Root 제거
		String loginId = StringUtils.defaultIfBlank(appConfig.getDefaultLoginId(), AppHelper.getLoginId(request));
				
		ActivityLogVO actLog = new ActivityLogVO();
		actLog.setAppCd(appCd);
		actLog.setPageUri(uri.equals("")?"/":uri);
		
		//########################################################################################
		// [시작] PWC 인증 수정 2021-09-06
		//######################################################################################## 
		AppInfoVO appInfo = commService.getAppInfo(appConfig.getAppCd());
		URL url = new URL(request.getScheme() 
					+ "://" + request.getServerName()
					+ ":" + request.getServerPort() 
					+ request.getContextPath());
		
		//1.세션확인
		UserSessionVO userSession= AppHelper.getSession(request);
		//2.세션 정보가 없을 경우
		if(userSession == null) {
			//2.사용자ID 가 있는지 확인 --> 로컬환경에서는 기본ID가 존재한다.
			if(loginId == null){
				//3.쿠키 정보 확인
				if(request.getCookies() != null) {
					for(Cookie cookie : request.getCookies()) {
						System.out.println("------- cookie ------");
						System.out.println(cookie.getName() + ": " + cookie.getValue());
						if(cookie.getName().equals("workplace_token")) {
							loginId = cookie.getValue();
						}
					}				
					//3.2 쿠키는 존재하지만 workplace_token 가 없는 경우
					if(loginId == null){
						//3.3.Pwc 인증 시작		
						logger.info("Pwc 인증 시작 >>> ");
						//response.sendRedirect("https://api-t.samil.com/auth/authorize?redirect_uri=" + url.toString());
						response.sendRedirect("https://login.samil.com/api/authorize?redirect_uri=" + url.toString());
						return false;					
					}
				} else {
					//5.Pwc 인증 시작				
					logger.info("Pwc 인증 시작 >>> ");
					//response.sendRedirect("https://api-t.samil.com/auth/authorize?redirect_uri=" + url.toString());
					response.sendRedirect("https://login.samil.com/api/authorize?redirect_uri=" + url.toString());
					return false;					
				}				
			}
			//여기까지 오면 쿠키에서 가져오던 서버에서 인증하고 오던 Login ID가 존재하는 상태임.
			//5.Login ID를 기준으로 세션 생성			
			userSession = commService.getUserSessionInfo(appCd, loginId);
			if(userSession == null) {
				logger.info("어플리케이션 접근 권한 없음: " + loginId);
				// 만약 Login Id로 세션을 생성하지 못하면 인증 실패 처리
				response.sendError(HttpServletResponse.SC_FORBIDDEN); // 권한 없음.
				return false;
			}else {				
				logger.info("세션 생성 성공: " + loginId);
				actLog.setActTy("login");
				actLog.setEmplNo(userSession.getEmplNo());
				actLog.setInteId(userSession.getInteId());
				commService.insertActivityLog(actLog);
				
				AppHelper.setSession(request, userSession);
			}				
		}		
		//########################################################################################
		// [종료] PWC 인증 수정 2021-09-06
		//######################################################################################## 
		
		//======================================================================================================
		/* 이전 포탈인증 백업 2021-09-06
		 * 
		// 세션 체크(어플리케이션에 접근 권한이 있어야 세션 생성 가능)
		UserSessionVO userSession= AppHelper.getSession(request);
		if(loginId == null){
			// Login Id가 없을 경우, Portal Login Page로 이동
			response.sendRedirect(appConfig.getPortalUrl() + "?next="+ request.getRequestURL().toString());
			
			if(userSession != null) {
				AppHelper.removeSession(request);
			}
			
			return false;
		}
		
		if(userSession == null) {	// 세션 정보가 없을 경우, 세션 생성
			// Login ID를 기준으로 세션 생성
			userSession = commService.getUserSessionInfo(appCd, loginId);
			if(userSession == null) {
				logger.info("어플리케이션 접근 권한 없음: " + loginId);
				// 만약 Login Id로 세션을 생성하지 못하면 인증 실패 처리
				response.sendError(HttpServletResponse.SC_FORBIDDEN); // 권한 없음.
				return false;
			}else {
				
				logger.info("세션 생성 성공: " + loginId);
				actLog.setActTy("login");
				actLog.setEmplNo(userSession.getEmplNo());
				actLog.setInteId(userSession.getInteId());
				commService.insertActivityLog(actLog);
				
				AppHelper.setSession(request, userSession);
			}
		}
		*/
		//======================================================================================================
		
		// 요청 URI에 대한 권한 체크
		if(uri.equals("/") || uri.equals("")) {
			// root로 접근시 별도 권한 체크하지 않고, role 별 home url로 Redirect
			// comm.web.HomeWeb.java 파일 참조
			return super.preHandle(request, response, handler);
		}else {
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("appCd", appCd);
			param.put("uri", uri);
			param.put("roleCd", userSession.getRoleCd());
			
			Boolean hasAuth = commService.checkAuthUri(param);	// 요청 URI 접근 권한 체크 
			if(!hasAuth) {
				// 요청 URL에 대한 접근 권한없음 처리
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				logger.info("호출 실패: 요청 URI(" + request.getRequestURI() + ")");
				return false;
			}else {
				if(uri.startsWith("/pages")) {
					actLog.setActTy("access");
					actLog.setEmplNo(userSession.getEmplNo());
					actLog.setInteId(userSession.getInteId());
					
					Map<String, Object> parameterMap = AppHelper.convertReqeustToMap(request);
					actLog.setQueryStr((new ObjectMapper()).writeValueAsString(parameterMap));
					commService.insertActivityLog(actLog);
				}		
				return super.preHandle(request, response, handler);
			}
		}
	}
}
