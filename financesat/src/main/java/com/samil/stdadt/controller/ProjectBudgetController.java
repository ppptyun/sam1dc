package com.samil.stdadt.controller;

import java.util.HashMap;
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
import com.samil.stdadt.service.ProjectBudgetService;
import com.samil.stdadt.util.Constant;
import com.samil.stdadt.vo.ProjectBudgetCisV3VO;
import com.samil.stdadt.vo.ProjectBudgetMemberVO;
import com.samil.stdadt.vo.ProjectBudgetSummaryVO;
import com.samil.stdadt.vo.ResultVO;

@Controller
@RequestMapping("/json/project/budget")
public class ProjectBudgetController {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ProjectBudgetController.class);
	
	@Autowired
	ProjectBudgetService bdgtService;
	
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
		
		ProjectBudgetSummaryVO data = bdgtService.getBudgetSummary(param);
		
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
		
		List<ProjectBudgetMemberVO> data = bdgtService.getBudgetList(param);
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

}
