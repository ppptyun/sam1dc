package com.samil.stdadt.web;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.samil.stdadt.comm.util.AppHelper;
import com.samil.stdadt.comm.vo.ApplicationConfigVO;
import com.samil.stdadt.comm.vo.UserSessionVO;
import com.samil.stdadt.service.ProjectInfoService;
import com.samil.stdadt.service.ProjectListService;
import com.samil.stdadt.util.Constant;
import com.samil.stdadt.vo.PageStatusVO;


@Controller
@RequestMapping(path="/pages/project")
public class ProjectInfoWeb {
	
	
	@Autowired
	ApplicationConfigVO appConf;
	
	@Autowired
	ProjectInfoService prjtInfoService;
	
	@Autowired
	ProjectListService prjtListService;
	
	@ModelAttribute(name="selOption")
	public Map<String, List<Map<String, Object>>> selOption() throws Exception{
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Constant.PARAM_NAME.APP_CD, appConf.getAppCd());
		return prjtInfoService.getSelOption(param);
	}
	
	@ModelAttribute(name="tooltip")
	public Map<String, Object> tooltip() throws Exception{
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Constant.PARAM_NAME.APP_CD, appConf.getAppCd());
		return prjtInfoService.getTooltip(param);
	}
	
	@ModelAttribute(name="wkmnsp")
	public Map<String, Object> wkmnsp() throws Exception{
		return prjtInfoService.getWkmnsp(appConf.getAppCd());
	}
	
	@ModelAttribute(name="profileId")
	public int profileId() throws Exception{
		return prjtInfoService.getProfileId();
	}
	
	@ModelAttribute(name="yearList")
	public List<String> getYearList() throws Exception{
		return prjtListService.getYear(null);
	}
	@ModelAttribute(name="yearListV1")
	public List<String> getYearListV1() throws Exception{
		return prjtListService.getYear(Arrays.asList("v1"));
	}
	
	@ModelAttribute(name="yearListV2")
	public List<String> getYearListV2() throws Exception{
		return prjtListService.getYear(Arrays.asList("v2", "v3"));
	}
	
	@ModelAttribute(name="initYear")
	public String getInitYear() throws Exception{
		Date date = new Date();
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int year = localDate.getYear();
		int month = localDate.getMonthValue();
		if(month < 4) {
			year -= 1;
		}
		return String.valueOf(year);
	}
	
	@RequestMapping(path="/v1/list", method= {RequestMethod.POST, RequestMethod.GET} )
	public String projectListV1(@ModelAttribute PageStatusVO pageStatus, Model model) {
		model.addAttribute("pageStatus", pageStatus);
		model.addAttribute("env", appConf.getEnv());
		return "/pages/project/v1/projectList";
	}
	
	@RequestMapping(path="/v2/list", method= {RequestMethod.POST, RequestMethod.GET} )
	public String projectListV2(@ModelAttribute PageStatusVO pageStatus, Model model) {
		model.addAttribute("pageStatus", pageStatus);
		model.addAttribute("env", appConf.getEnv());
		return "/pages/project/v2/projectList";
	}
	
	@RequestMapping(path="/v3/list", method= {RequestMethod.POST, RequestMethod.GET} )
	public String projectListV3(@ModelAttribute PageStatusVO pageStatus, Model model) {
		model.addAttribute("pageStatus", pageStatus);
		model.addAttribute("env", appConf.getEnv());
		return "/pages/project/v3/projectList";
	}
	
	@RequestMapping(path="/draftlist", method= {RequestMethod.POST, RequestMethod.GET} )
	public String projectDraftList(@ModelAttribute PageStatusVO pageStatus, Model model) {
		model.addAttribute("pageStatus", pageStatus);
		return "/pages/project/projectDraftList";
	}
	
	
	
	@RequestMapping(path="/read", method= RequestMethod.POST)
	public String projectInfoReadMode(HttpServletRequest request, @ModelAttribute PageStatusVO pageStatus,  @RequestParam Map<String, Object> param, Model model) throws Exception {
		UserSessionVO userSession= AppHelper.getSession(request);
		param.put(Constant.PARAM_NAME.SESSION, userSession);
		param.put(Constant.PARAM_NAME.APP_CD, appConf.getAppCd());
		
		Map<String, Object> auth = prjtInfoService.getPrjtMetaInfo(param);
		model.addAttribute("auth", auth);
		model.addAttribute("prjtCd", param.get("prjtCd"));
		model.addAttribute("pageStatus", pageStatus);
		if(auth.get("formDiv").equals("v1")) {			
			return "/pages/project/projectInfoRead";	
		}else if(auth.get("formDiv").equals("v2")) {
			model.addAttribute("mode", "read");
			return "/pages/project/v2/projectInfo";			
		//=====================================================
		//	[시작] 20220214 남웅주  2022년도 표준감사시간 개정
		//=====================================================
		}else if(auth.get("formDiv").equals("v3")) {
			// 202020423
			model.addAttribute("mode", "read");
			return "/pages/project/v3/projectInfo";			
		}else { 
			model.addAttribute("mode", "read");
			return "/pages/project/v4/projectInfo";
		}
		//=====================================================
		//	[종료] 20220214 남웅주  2022년도 표준감사시간 개정
		//=====================================================		
	}
	
	@RequestMapping(path="/edit", method= RequestMethod.POST)
	public String projectInfoEditMode(HttpServletRequest request, @ModelAttribute PageStatusVO pageStatus, @RequestParam Map<String, Object> param, Model model) throws Exception {
		boolean isDraft = Boolean.parseBoolean(StringUtils.defaultIfBlank(request.getParameter("isDraft"), "false"));
		UserSessionVO userSession= AppHelper.getSession(request);
		param.put(Constant.PARAM_NAME.SESSION, userSession);
		param.put(Constant.PARAM_NAME.APP_CD, appConf.getAppCd());
		param.put("isDraft", isDraft);
		
		Map<String, Object> auth = prjtInfoService.getPrjtMetaInfo(param);
		model.addAttribute("auth", auth);
		model.addAttribute("prjtCd", param.get("prjtCd"));
		model.addAttribute("pageStatus", pageStatus);
		
		if(auth.get("formDiv").equals("v1")) {			
			model.addAttribute("satGrpList", prjtInfoService.getSatgrpListByPrjtCd(param));
			return "/pages/project/projectInfoEdit";
		}else if(auth.get("formDiv").equals("v2")) {	
			model.addAttribute("mode", "edit");
			return "/pages/project/v2/projectInfo";			
		//=====================================================
		//	[시작] 20220214 남웅주  2022년도 표준감사시간 개정
		//=====================================================
		}else if(auth.get("formDiv").equals("v3")) {	
			// 202020423
			model.addAttribute("mode", "edit");
			return "/pages/project/v3/projectInfo";
		}else {	
			model.addAttribute("mode", "edit");
			return "/pages/project/v4/projectInfo";
		}
		//=====================================================
		//	[종료] 20220214 남웅주  2022년도 표준감사시간 개정
		//=====================================================		
	}
	
	@RequestMapping("/new")
	public String projectInfoNewMode(HttpServletRequest request, @ModelAttribute PageStatusVO pageStatus, Model model) throws Exception {
		model.addAttribute("pageStatus", pageStatus);
		model.addAttribute("mode", "new");
		
		//=====================================================
		//	[시작] 20220214 남웅주  2022년도 표준감사시간 개정
		//=====================================================
		//return "/pages/project/v3/projectInfo";
		return "/pages/project/v4/projectInfo";
		//=====================================================
		//	[종료] 20220214 남웅주  2022년도 표준감사시간 개정
		//=====================================================		
	}
	
	//=====================================================
	//	[시작] 20220214 남웅주  2022년도 표준감사시간 개정
	//=====================================================
	@RequestMapping("/v3/new")
	public String projectInfoNewModeV3(HttpServletRequest request, @ModelAttribute PageStatusVO pageStatus, Model model) throws Exception {
		model.addAttribute("pageStatus", pageStatus);
		model.addAttribute("mode", "new");
		return "/pages/project/v3/projectInfo";
	}
	//=====================================================
	//	[종료] 20220214 남웅주  2022년도 표준감사시간 개정
	//=====================================================		
	
}
