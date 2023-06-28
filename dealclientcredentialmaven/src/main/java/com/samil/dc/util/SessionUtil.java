package com.samil.dc.util;

import java.security.Principal;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.samil.dc.core.ContextLoader;
import com.samil.dc.domain.UserSessionBeans;
import com.samil.dc.service.ApplicationGlobal;

/**
 * 세션 정보 유틸 클래스
 *
 */
public final class SessionUtil {

	private static final String DEAL_CLIENT_CREDENTIAL_SESSION_KEY = "_deal_client_credential_session_";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 사용자 세션 저장
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @param userSessionBeans
	 */
	public static void saveUserSession(HttpServletRequest request, UserSessionBeans userSessionBeans) {
		HttpSession session = request.getSession(true);
		session.setAttribute(DEAL_CLIENT_CREDENTIAL_SESSION_KEY, userSessionBeans);
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 사용자 세션 조회
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	public static UserSessionBeans getUserSession(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			Object sessionObject = session.getAttribute(DEAL_CLIENT_CREDENTIAL_SESSION_KEY);
			if (sessionObject != null) {
				UserSessionBeans userSessionBeans = (UserSessionBeans) sessionObject;
				return userSessionBeans;
			}
		}
		
		return null;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 사용자 세션 삭제
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 */
	public static void removeUserSession(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		session.removeAttribute(DEAL_CLIENT_CREDENTIAL_SESSION_KEY);
		session.invalidate();
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 쿠키를 통해 사용자 세션 갱신
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	public static UserSessionBeans refreshUserSession(HttpServletRequest request) {
		Principal pr = request.getUserPrincipal();
		String userId = null;

		///////////////////////////////////////////////////////////////////////////////

		String cookieName;
		String cookieValue;
		String debug_userid = null;
		Cookie[] cookie = request.getCookies();

		if (cookie != null) {
			for (Cookie tmp : cookie) {
				cookieName = tmp.getName();
				cookieValue = tmp.getValue();
				//#################################################################################
				// [시작] PWC 인증으로 수정함. 2021-09-02 남웅주 
				//#################################################################################								
//				if ("debug_userid".equals(cookieName)) {
//					debug_userid = cookieValue;
//				}
				
				System.out.println("#### [시작] PWC 인증 후 생성된 Cookie 확인 ####");
				
				System.out.println(" cookieName :: " + cookieName);
				System.out.println(" cookieValue :: " + cookieValue);
				
				if("workplace_token".equals(cookieName)){
					debug_userid = cookieValue;
					
					System.out.println(debug_userid);					
				}				
				System.out.println("#### [종료] PWC 인증 후 생성된 Cookie 확인 ####");
				//#################################################################################
				// [종료] PWC 인증으로 수정함. 2021-09-02 남웅주 
				//#################################################################################
			}
		}

		///////////////////////////////////////////////////////////////////////////////
		
		// 쿠키에 사용자 정보가 없으면 로그아웃으로 간주
		if(!(debug_userid == null || "".equals(debug_userid))){
			userId = debug_userid;
		}else if ("Y".equals(ContextLoader.getContext("DebugMode"))) {
			userId = ContextLoader.getContext("DebugUserId");
		} else {			
			// 2021-09-02 남웅주
			//이미 위에서 쿠키확인을 하기 때문에 불필요함.
			//userId = ApplicationGlobal.checkCookie(request);
			if (userId == null) {
				if (pr == null) {
					return null;
				}
				userId = pr.getName();
			}
		}
		if (StringUtils.isBlank(userId)) {
			SessionUtil.removeUserSession(request);
			return null;
		}

		// 사용자 정보 조회
		UserSessionBeans userSessionBeans = ApplicationGlobal.getUserInfo(userId);
		if (userSessionBeans == null || userSessionBeans.getUserBeans() == null) {
			// 사용자 정보가 없으면 null 리턴
			return null;
		}

		// 세션에 사용자 정보 저장
		saveUserSession(request, userSessionBeans);

		return userSessionBeans;
	}
}
