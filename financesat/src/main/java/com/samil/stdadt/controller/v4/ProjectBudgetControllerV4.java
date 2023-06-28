package com.samil.stdadt.controller.v4;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.samil.stdadt.comm.service.CommCodeService;
import com.samil.stdadt.comm.util.AppHelper;
import com.samil.stdadt.comm.vo.ApplicationConfigVO;
import com.samil.stdadt.comm.vo.UserSessionVO;
import com.samil.stdadt.retain.service.RetainService;
import com.samil.stdadt.service.v4.ProjectBudgetV4Service;
import com.samil.stdadt.util.Constant;
import com.samil.stdadt.vo.ProjectBudgetCisV4VO;
import com.samil.stdadt.vo.ProjectBudgetMemberV4VO;
import com.samil.stdadt.vo.ProjectBudgetSummaryV4VO;
import com.samil.stdadt.vo.ResultVO;

@Controller
@RequestMapping("/json/project/v4/budget")
public class ProjectBudgetControllerV4 {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ProjectBudgetControllerV4.class);
	
	@Autowired
	ProjectBudgetV4Service bdgtService;
	
	@Autowired
	ApplicationConfigVO appConfig;
	
	@Autowired
	CommCodeService commCdService;
	
	@Autowired
	RetainService retainService;
	
	
	@RequestMapping(path="/summary", method=RequestMethod.POST)
	public ModelAndView getProjectBudgetSummary(HttpServletRequest request, @RequestBody Map<String, Object> param) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		UserSessionVO userSession= AppHelper.getSession(request);
		
		param.put(Constant.PARAM_NAME.APP_CD, appConfig.getAppCd());
		param.put(Constant.PARAM_NAME.SESSION, userSession);
		
		ProjectBudgetSummaryV4VO data = bdgtService.getBudgetSummary(param);
		
		ResultVO result = new ResultVO();
		if(data == null) {
			result.setStatus(Constant.RESULT.FAIL);
			result.setMsg(Constant.RESULT_MSG.RESULT_NULL + "(Budget Hour Summary)");
		}else {			
			result.setStatus(Constant.RESULT.SUCCESS);
			result.setData(data);
		}
		mv.addObject("result", result);
		return mv;
	}
	
	@RequestMapping(path="/list", method=RequestMethod.POST)
	public ModelAndView getProjectBudgetList(HttpServletRequest request, @RequestBody Map<String, Object> param) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		UserSessionVO userSession= AppHelper.getSession(request);
		
		param.put(Constant.PARAM_NAME.APP_CD, appConfig.getAppCd());
		param.put(Constant.PARAM_NAME.SESSION, userSession);
		
		//-----------------------------------------------------------------------------------------
		System.out.println("화면 budgetInputCycle >> " + param.get("budgetInputCycle").toString());		
		//-----------------------------------------------------------------------------------------
		
		List<ProjectBudgetMemberV4VO> data = bdgtService.getBudgetList(param);
		ResultVO result = new ResultVO();
		result.setStatus(Constant.RESULT.SUCCESS);
		result.setData(data);
		mv.addObject("result", result);
		return mv;
	}
		
	//------------------------------------------------------------------
	// 20200427 추가
	//------------------------------------------------------------------
	@RequestMapping(path="/cislist", method=RequestMethod.POST)
	public ModelAndView getProjectBudgetCisList(HttpServletRequest request, @RequestBody Map<String, Object> param) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		UserSessionVO userSession= AppHelper.getSession(request);
		
		param.put(Constant.PARAM_NAME.APP_CD, appConfig.getAppCd());
		param.put(Constant.PARAM_NAME.SESSION, userSession);
		
		String prjtCd =	param.get("prjtCd").toString();					
		String[] prjtCds = prjtCd.split("-");
		
	    param.put("PRJTCD1", prjtCds[0]);
	    param.put("PRJTCD2", prjtCds[1]);
	    param.put("PRJTCD3", prjtCds[2]);
		
		List<ProjectBudgetCisV4VO> data = bdgtService.getBudgetCisList(param);
		
		ResultVO result = new ResultVO();
		result.setStatus(Constant.RESULT.SUCCESS);
		result.setData(data);
		mv.addObject("result", result);
		return mv;
	}
	
	@RequestMapping(path="/save", method=RequestMethod.POST)
	public ModelAndView saveMemberBudget(HttpServletRequest request, @RequestBody Map<String, Object> param) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		UserSessionVO userSession= AppHelper.getSession(request);
		
		param.put(Constant.PARAM_NAME.APP_CD, appConfig.getAppCd());
		param.put(Constant.PARAM_NAME.SESSION, userSession);
		
		// 기존 데이터 삭제 후 재생성.
		mv.addObject("result", bdgtService.saveMemberBudget(param));
		return mv;
	}
	
	@RequestMapping(path="/expcm", method=RequestMethod.POST)
	public ModelAndView getCMByGradCd(HttpServletRequest request, @RequestBody Map<String, Object> param) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		List<Map<String, Object>> data = bdgtService.getCMByGradCd(param);
		
		ResultVO result = new ResultVO();
		result.setStatus(Constant.RESULT.SUCCESS);
		result.setData(data);
		
		mv.addObject("result", result);
		return mv;
	}
	
	@RequestMapping(path="/reqELAprv", method=RequestMethod.POST)
	public ModelAndView reqELAprv(HttpServletRequest request, @RequestBody Map<String, Object> param) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		UserSessionVO userSession= AppHelper.getSession(request);
		
		param.put(Constant.PARAM_NAME.APP_CD, appConfig.getAppCd());
		param.put(Constant.PARAM_NAME.SESSION, userSession);
		
		boolean isSuccess = bdgtService.reqELAprv(param);
		ResultVO result = new ResultVO();
		
		if(isSuccess) {
			result.setStatus(Constant.RESULT.SUCCESS);
		}else {
			result.setStatus(Constant.RESULT.FAIL);
			result.setMsg(Constant.RESULT_MSG.ERROR);
		}
		
		mv.addObject("result", result);
		return mv;
	}
	
	@RequestMapping(path="/saveRetainSchedule", method=RequestMethod.POST)
	public ModelAndView saveRetainSchedule(HttpServletRequest request, @RequestBody Map<String, Object> param) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		UserSessionVO userSession= AppHelper.getSession(request);
		ResultVO result = new ResultVO();
		
		param.put(Constant.PARAM_NAME.APP_CD, appConfig.getAppCd());
		param.put(Constant.PARAM_NAME.SESSION, userSession);
		
		int isSuccess = bdgtService.saveRetainSchedule(param);
		
		if(isSuccess == 1) {
			result.setStatus(Constant.RESULT.SUCCESS);
		}else {
			result.setStatus(Constant.RESULT.FAIL);
			result.setMsg(Constant.RESULT_MSG.ERROR);
		}
		mv.addObject("result", result);
		return mv;
	}
	
	@RequestMapping(path="/getDftBdgtRoleConfig", method=RequestMethod.POST)
	public ModelAndView getDftBdgtRoleConfig(@RequestBody Map<String, Object> param) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		param.put(Constant.PARAM_NAME.APP_CD, appConfig.getAppCd());
		List<Map<String, Object>> data = bdgtService.selectDftBdgtRoleConfig(param);
		ResultVO result = new ResultVO();
		
		result.setStatus(Constant.RESULT.SUCCESS);
		result.setData(data);
		mv.addObject("result", result);
		
		return mv;
	}
	
	
	
	@RequestMapping(path="/checkDuplPrjt", method=RequestMethod.POST)
	public ModelAndView checkDuplPrjt(@RequestBody Map<String, Object> param) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		ResultVO result = new ResultVO();
		List<Map<String, Object>> resultData = bdgtService.checkDuplPrjt(param);
		result.setStatus(Constant.RESULT.SUCCESS);
		result.setData(resultData);
		mv.addObject("result", result);
		return mv;
	}

}
