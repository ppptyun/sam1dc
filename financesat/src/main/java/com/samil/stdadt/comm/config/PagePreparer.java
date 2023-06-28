package com.samil.stdadt.comm.config;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.tiles.Attribute;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.preparer.ViewPreparer;
import org.apache.tiles.request.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.samil.stdadt.comm.service.AppCommService;
import com.samil.stdadt.comm.util.AppConstant;
import com.samil.stdadt.comm.vo.AppInfoVO;
import com.samil.stdadt.comm.vo.ApplicationConfigVO;
import com.samil.stdadt.comm.vo.AuthMenuVO;
import com.samil.stdadt.comm.vo.UserSessionVO;


/**
 * 
 * @author shyunchoi
 * <pre>
 * 페이지 로드시 필요한 정보를 셋팅
 * </pre>
 */
public class PagePreparer implements ViewPreparer {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(PagePreparer.class);
	
	
	@Autowired
	AppCommService commService;
	
	@Autowired
	AppInfoVO appInfo;
	
	@Autowired
	ApplicationConfigVO appConfig;
	
	@Override
	public void execute(Request request, AttributeContext attrContext) {
		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		UserSessionVO userSession = (UserSessionVO) req.getSession().getAttribute(AppConstant.sesseionKey);
		List<AuthMenuVO> authMenuList;
		
		// Application Title, Home URI 정보 		
		attrContext.putAttribute("appInfo", new Attribute(appInfo), true);
		attrContext.putAttribute("appConfig", new Attribute(appConfig), true);
		
		try {
			// 권한을 가지고 있는 메뉴 정보
//			String uri = req.getRequestURI().replaceFirst(req.getContextPath(), "").replaceFirst(";jsessionid=[0-9A-Z]+", "");
			String uri = req.getRequestURI(); // 현재 요청된 페이지 URI정보와 메뉴에 등록된 URI 정보를 비교하여 선택된 메뉴를 표기하기 위함.
			authMenuList = commService.getAuthMenu(appConfig.getAppCd(), userSession.getRoleCd(), uri);
			attrContext.putAttribute("authMenuList", new Attribute(authMenuList), true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
