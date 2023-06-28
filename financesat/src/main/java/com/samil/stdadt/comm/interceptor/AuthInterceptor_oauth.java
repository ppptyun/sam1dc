package com.samil.stdadt.comm.interceptor;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.samil.stdadt.comm.service.AppCommService;
import com.samil.stdadt.comm.util.AppHelper;
import com.samil.stdadt.comm.vo.AppInfoVO;
import com.samil.stdadt.comm.vo.ApplicationConfigVO;
import com.samil.stdadt.comm.vo.UserSessionVO;

/**
 * 
 * @author shyunchoi
 * <pre>
 * Oauth 인증을 테스트하 위해 만든 인터셉터로 테스트용으로 만든것임.
 * 즉 실제로 사용하지는 않음
 * 실제로 테스트 해보고 싶으면 mvc-config.xml 파일에서 interceptor를 수정하면 된다.
 * <bean class="com.samil.stdadt.comm.interceptor.AuthInterceptor_oauth" ></bean>
 * </pre>
 */
public class AuthInterceptor_oauth extends HandlerInterceptorAdapter {
	
	static final Logger logger = LoggerFactory.getLogger(AuthInterceptor_oauth.class);
	
	@Autowired
	AppCommService commService;
	
	@Autowired
	ApplicationConfigVO appConfig;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		UserSessionVO userSession= AppHelper.getSession(request);
		String appCd = appConfig.getAppCd();
		
		if(userSession == null) {
			String accessToken = null;
			if(request.getCookies() != null) {
				for(Cookie cookie : request.getCookies()) {
					System.out.println("------- cookie ------");
					System.out.println(cookie.getName() + ": " + cookie.getValue());
					if(cookie.getName().equals("access_token")) {
						accessToken = cookie.getValue();
					}
				}
			}
			
			AppInfoVO appInfo = commService.getAppInfo(appConfig.getAppCd());
			URL url = new URL(request.getScheme() 
						+ "://" + request.getServerName()
						+ ":" + request.getServerPort() 
						+ request.getContextPath()
						+ appInfo.getLoginUri());
			
			if(accessToken == null) {
				System.out.println("access token is null");
				response.sendRedirect("https://api-t.samil.com/auth/authorize?redirect_uri=" + url.toString());
				return false;	
			} else {
				System.out.println("===> access token: " + accessToken);
				Map<String, String> userInfo = (Map<String, String>) commService.getUserInfoByAccessToken(accessToken);
				
				if(userInfo == null || userInfo.get("uid") == null) {
					System.out.println("userinfo == null || userinfo.get(uid) == null");
					response.sendRedirect("https://api-t.samil.com/auth/authorize?redirect_uri=" + url.toString());
					return false;
				}
				userSession = commService.getUserSessionInfo(appConfig.getAppCd(), (String) userInfo.get("uid"));
				
				if(userSession == null) {
					response.sendRedirect("/error/401");
					return false;
				} else {					
					AppHelper.setSession(request, userSession);
					return super.preHandle(request, response, handler);
				}
				
			}
		}
		
		// 요청 URI에 대한 권한 체크
		String uri = request.getRequestURI().replace(request.getContextPath(), "");	// Context Root 제거
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
				response.sendRedirect("/error/403");
				logger.info("호출 실패: 요청 URI(" + request.getRequestURI() + ")");
				return false;
			}else {
				return super.preHandle(request, response, handler);
			}
		}
	}
}
