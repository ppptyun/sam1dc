package com.samil.stdadt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.samil.stdadt.comm.util.AppHelper;
import com.samil.stdadt.comm.vo.ApplicationConfigVO;
import com.samil.stdadt.comm.vo.UserSessionVO;
import com.samil.stdadt.service.HistoryService;
import com.samil.stdadt.service.PopupService;
import com.samil.stdadt.util.Constant;
import com.samil.stdadt.vo.ConflictListVO;
import com.samil.stdadt.vo.ProfitabilityV3VO;
import com.samil.stdadt.vo.ProfitabilityVO;
import com.samil.stdadt.vo.ProjectBudgetMemberVO;
import com.samil.stdadt.vo.ProjectVersionVO;
import com.samil.stdadt.vo.ResultVO;


@Controller
@RequestMapping("/json/popup")
public class PopupController {
	
	@Autowired
	PopupService popService;
	
	@Autowired
	HistoryService histService;
	
	@Autowired
	ApplicationConfigVO appConfig;
	
	@RequestMapping(path="/emp", method=RequestMethod.POST)
	public ModelAndView getEmp(HttpServletRequest request, @RequestBody Map<String, Object> param) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		ResultVO result = new ResultVO();
		
		List<Map<String, Object>> empList = popService.getEmp(param);
		
		if(empList == null) {
			result.setStatus(Constant.RESULT.FAIL);
		}else {
			
			for(Map<String, Object> item : empList) {
				Map<String, Object> userdata = new HashMap<String, Object>();
				userdata.put("emplno", item.get("emplno"));
				userdata.put("kornm", item.get("kornm"));
				userdata.put("gradnm", item.get("gradnm"));
				userdata.put("gradcd", item.get("gradcd"));
				userdata.put("wkmnsp", item.get("wkmnsp"));
				item.put("userdata", userdata);
			}
			
			result.setStatus(Constant.RESULT.SUCCESS);
			result.setData(empList);
		}
		
		mv.addObject("result", result);
		return mv;
	}
	
	
	@RequestMapping(path="/project", method=RequestMethod.POST)
	public ModelAndView getProject(HttpServletRequest request, @RequestBody Map<String, Object> param) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		ResultVO result = new ResultVO();
		
		List<Map<String, Object>> list = popService.getProject(param);
		
		if(list == null) {
			result.setStatus(Constant.RESULT.FAIL);
		}else {
			for(Map<String, Object> data : list) {
				Map<String, Object> userdata = new HashMap<String, Object>();
				userdata.putAll(data);
				data.put("userdata", userdata);
			}
			result.setStatus(Constant.RESULT.SUCCESS);
			result.setData(list);
		}
		
		mv.addObject("result", result);
		return mv;
	}
	

	@RequestMapping(path="/projectV3", method=RequestMethod.POST)
	public ModelAndView getProjectV3(HttpServletRequest request, @RequestBody Map<String, Object> param) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		ResultVO result = new ResultVO();
		
		String prjtCd =	param.get("prjtCd").toString();					
		String[] prjtCds = prjtCd.split("-");
		
	    param.put("PRJTCD1", prjtCds[0]);
	    param.put("PRJTCD2", prjtCds[1]);
	    param.put("PRJTCD3", prjtCds[2]);
		
		List<Map<String, Object>> list = popService.getProjectV3(param);
		
		if(list == null) {
			result.setStatus(Constant.RESULT.FAIL);
		}else {
			for(Map<String, Object> data : list) {
				Map<String, Object> userdata = new HashMap<String, Object>();
				userdata.putAll(data);
				data.put("userdata", userdata);
			}
			result.setStatus(Constant.RESULT.SUCCESS);
			result.setData(list);
		}
		
		mv.addObject("result", result);
		return mv;
	}
	
	
	
	@RequestMapping(path="/profitability", method=RequestMethod.POST)
	public ModelAndView getProfitability(HttpServletRequest request, @RequestBody Map<String, Object> param) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		ResultVO result = new ResultVO();
		
		ProfitabilityVO data = popService.getProfit(param);
		if(data == null) {
			result.setStatus(Constant.RESULT.UNAUTHORIZED);
			result.setMsg(Constant.RESULT_MSG.UNATUTHORIZED);
		}else {
			result.setStatus(Constant.RESULT.SUCCESS);
			result.setData(data);	
		}
		
		mv.addObject("result", result);
		return mv;
	}
	
	@RequestMapping(path="/profitabilityV3", method=RequestMethod.POST)
	public ModelAndView getProfitabilityV3(HttpServletRequest request, @RequestBody Map<String, Object> param) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		ResultVO result = new ResultVO();
		
		ProfitabilityV3VO data = popService.getProfitV3(param);
		if(data == null) {
			result.setStatus(Constant.RESULT.UNAUTHORIZED);
			result.setMsg(Constant.RESULT_MSG.UNATUTHORIZED);
		}else {
			result.setStatus(Constant.RESULT.SUCCESS);
			result.setData(data);	
		}
		
		mv.addObject("result", result);
		return mv;
	}
	
	@RequestMapping(path="/conflict", method=RequestMethod.POST)
	public ModelAndView getBudgetConflict(HttpServletRequest request, @RequestBody Map<String, Object> param) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		param.put(Constant.PARAM_NAME.APP_CD, appConfig.getAppCd());
		List<ConflictListVO> data = popService.getBudgetConflict(param);
		
		ResultVO result = new ResultVO();
		result.setStatus(Constant.RESULT.SUCCESS);
		result.setData(data);
		
		mv.addObject("result", result);
		return mv;
	}
	
	
	@RequestMapping(path="/history/budget/list", method=RequestMethod.POST)
	public ModelAndView getProjectBudgetList(HttpServletRequest request, @RequestBody ProjectVersionVO param) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		ResultVO result = new ResultVO();
		
		param.setAppCd(appConfig.getAppCd());
		
		Map<String, Object> version = histService.getVersionAprvComplete(param);
		if(version.get("version") == null){
			result.setStatus(Constant.RESULT.FAIL);
			result.setMsg(Constant.RESULT_MSG.RESULT_NULL + "(Budget Hour Member History)");
		}else {
			param.setVersion(Long.parseLong(version.get("version").toString()));
			List<ProjectBudgetMemberVO> data = histService.getProjectBudgetMemberHist(param);
			result.setStatus(Constant.RESULT.SUCCESS);
			result.setData(data);
		}
		
		mv.addObject("result", result);
		return mv;
	}
	
	@RequestMapping(path="/auditor/list", method=RequestMethod.POST)
	public ModelAndView getAuditorList(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		ResultVO result = new ResultVO();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Constant.PARAM_NAME.APP_CD, appConfig.getAppCd());
		List<Map<String, Object>> auditorList= popService.getAuditorList(param);
		
		if(auditorList == null || auditorList.size() == 0){
			result.setStatus(Constant.RESULT.FAIL);
			result.setMsg(Constant.RESULT_MSG.RESULT_NULL + "(감사인 정보)");
		}else {
			result.setStatus(Constant.RESULT.SUCCESS);
			result.setData(auditorList);
		}
		mv.addObject("result", result);
		return mv;
	}
	
	@RequestMapping(path="/auditor/add", method=RequestMethod.POST)
	public ModelAndView addAuditor(HttpServletRequest request, @RequestBody Map<String, Object> param) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		ResultVO result = new ResultVO();
		UserSessionVO userSession= AppHelper.getSession(request);
		param.put(Constant.PARAM_NAME.SESSION, userSession);
		param.put(Constant.PARAM_NAME.APP_CD, appConfig.getAppCd());
		
		if(popService.isExistAuditor(param)) {
			result.setStatus(Constant.RESULT.FAIL);
			result.setMsg("동일한 이름의 감사인이 존재합니다. 리스트에선 선택하여 입력하세요.");
		}else {
			int tmp = popService.addAuditor(param);
			if(tmp == 0){
				result.setStatus(Constant.RESULT.FAIL);
				result.setMsg(Constant.RESULT_MSG.SAVE_ERROR + "(감사인 정보)");
			}else {
				result.setStatus(Constant.RESULT.SUCCESS);
				result.setData(param);
			}
		}
		mv.addObject("result", result);
		return mv;
	}
}


