package com.samil.stdadt.comm.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.samil.stdadt.comm.service.AppCommService;
import com.samil.stdadt.comm.util.AppConstant;
import com.samil.stdadt.comm.util.AppHelper;
import com.samil.stdadt.comm.vo.ApplicationConfigVO;
import com.samil.stdadt.comm.vo.UserSessionVO;
import com.samil.stdadt.vo.ResultVO;

@Controller
@RequestMapping("/sysadmin")
public class ChangeUserRedirectWeb {
	
	@Autowired
	AppCommService commService;
	
	@Autowired
	ApplicationConfigVO appConfig;
	
	@RequestMapping("/change/user")
	public String changeUser(HttpServletRequest request, HttpServletResponse response, @RequestParam("inteId") String inteId, RedirectAttributes redirectAttributes) throws Exception {
		ResultVO result = new ResultVO();
		String homeUri = "/";
		
		UserSessionVO userSession= AppHelper.getSession(request);
		
		// 로그인 시도한 ID, 사번으로 인원이 존재하는지 체크
		Map<String, Object> chkUser = commService.getUserInfo(new HashMap<String, Object>(){private static final long serialVersionUID = -928485968597721886L;{put("inteId", inteId);}});
		if(chkUser == null) {
			result.setStatus("fail");
			result.setMsg("로그인 변경 시도한 사용자가 존재하지 않습니다.");
			homeUri = commService.getHomeURI(appConfig.getAppCd(), userSession.getRoleCd());
		}else {

			UserSessionVO chUserSession = commService.getUserSessionInfo(appConfig.getAppCd(), inteId, userSession.getEmplNo());	
			if(chUserSession == null) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				return null;
			}else {
				result.setStatus("success");
				result.setMsg(chUserSession.getKorNm() + "으로 세션 정보를 변경하였습니다.");
				request.getSession().setAttribute(AppConstant.sesseionKey, chUserSession);
				homeUri = commService.getHomeURI(appConfig.getAppCd(), chUserSession.getRoleCd());
			}	
		}
		redirectAttributes.addFlashAttribute("result", result);
		return "redirect:" + homeUri;
	}
	
	@RequestMapping("/init/user")
	public String initUser(HttpServletRequest request) throws Exception {
		UserSessionVO userSession= AppHelper.getSession(request);
		UserSessionVO chUserSession = commService.getUserSessionInfo(appConfig.getAppCd(), userSession.getOriginEmplNo());
		
		// 원상태의 세션으로 재설정
		request.getSession().setAttribute(AppConstant.sesseionKey, chUserSession);
		
		String homeUri = commService.getHomeURI(appConfig.getAppCd(), chUserSession.getRoleCd());
		return "redirect:" + homeUri;
	}
}
