package com.samil.stdadt.controller.v2;

import java.util.Arrays;
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
import com.samil.stdadt.comm.vo.CamelMap;
import com.samil.stdadt.comm.vo.UserSessionVO;
import com.samil.stdadt.dto.ProjectInfoV2Dto;
import com.samil.stdadt.service.CalcSatService;
import com.samil.stdadt.service.ProjectListService;
import com.samil.stdadt.service.v2.ProjectInfoV2Service;
import com.samil.stdadt.util.Constant;
import com.samil.stdadt.vo.ProjectInfoV2VO;
import com.samil.stdadt.vo.ProjectListVO;
import com.samil.stdadt.vo.ProjectVersionVO;
import com.samil.stdadt.vo.ResultVO;

@Controller
@RequestMapping("/json/project/v2")
public class ProjectControllerV2 {
	@Autowired
	ApplicationConfigVO appConf;
	
	@Autowired
	ProjectListService prjtListService;
	
	@Autowired
	CalcSatService calcSatService;
	
	@Autowired
	ProjectInfoV2Service prjtInfoService;
	
	//1. 프로젝트 목록 가져오기
	@RequestMapping("/list")
	public ModelAndView getProjectList(HttpServletRequest request, @RequestBody(required=false) Map<String, Object> param) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		UserSessionVO userSession= AppHelper.getSession(request);
		
		if(param == null) param = new HashMap<String, Object>();
		param.put(Constant.PARAM_NAME.APP_CD, appConf.getAppCd());
		param.put(Constant.PARAM_NAME.SESSION, userSession);
		param.put("formDiv", Arrays.asList("v2", "v3"));
		
		List<ProjectListVO> prjtList = prjtListService.getProjectList(param);
		ResultVO result = new ResultVO();
		
		result.setStatus(Constant.RESULT.SUCCESS);
		result.setData(prjtList);
		mv.addObject("result", result);
		return mv;
	}
	
	//2 Draft로 저장된 프로젝트 목록 가져오기
	@RequestMapping("/draftlist")
	public ModelAndView getProjectDraftList(HttpServletRequest request, @RequestBody(required=false) Map<String, Object> param) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		UserSessionVO userSession= AppHelper.getSession(request);
		
		if(param == null) param = new HashMap<String, Object>();
		param.put(Constant.PARAM_NAME.APP_CD, appConf.getAppCd());
		param.put(Constant.PARAM_NAME.SESSION, userSession);
		
		List<Map<String, Object>> prjtList = prjtListService.getProjectDraftList(param);
		ResultVO result = new ResultVO();
		
		result.setStatus(Constant.RESULT.SUCCESS);
		result.setData(prjtList);
		mv.addObject("result", result);
		return mv;
	}
	
	//프로젝트 추가
	@RequestMapping(path="/add", method=RequestMethod.POST)
	public ModelAndView addProject(HttpServletRequest request, @RequestBody ProjectInfoV2Dto param) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		ResultVO result = new ResultVO();
		UserSessionVO userSession= AppHelper.getSession(request);
		param.setSession(userSession);
		if(param.getStat() == null || param.getStat() == "") param.setStat("RG");
		int ret = prjtInfoService.addProject(param);
		if(ret == 0) {
			result.setStatus(Constant.RESULT.FAIL);
			result.setMsg(Constant.RESULT_MSG.ERROR);
			result.setMsg(Constant.RESULT_MSG.SAVE_ERROR);
		}else {
			result.setStatus(Constant.RESULT.SUCCESS);
		}
		
		mv.addObject("result", result);
		return mv;
	}
	
	//프로젝트 추가
	@RequestMapping(path="/update", method=RequestMethod.POST)
	public ModelAndView updateProject(HttpServletRequest request, @RequestBody ProjectInfoV2Dto param) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		ResultVO result = new ResultVO();
		UserSessionVO userSession= AppHelper.getSession(request);
		param.setSession(userSession);
		
		int ret = prjtInfoService.updateProject(param);
		
		if(ret == 0) {
			result.setStatus(Constant.RESULT.FAIL);
			result.setMsg(Constant.RESULT_MSG.ERROR);
			result.setMsg(Constant.RESULT_MSG.SAVE_ERROR);
		}else {
			result.setStatus(Constant.RESULT.SUCCESS);
		}
		
		mv.addObject("result", result);
		return mv;
	}
	
	//프로젝트 삭제
	@RequestMapping(path="/delete", method=RequestMethod.POST)
	public ModelAndView deleteProject(HttpServletRequest request, @RequestBody Map<String, Object> param) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		ResultVO result = new ResultVO();
		UserSessionVO userSession= AppHelper.getSession(request);
		param.put("session", userSession);
		
		List<ProjectInfoV2VO> prjtInfoList = prjtInfoService.getProjectInfoList(param);
		ProjectInfoV2VO prjtInfo = prjtInfoList==null?null:prjtInfoList.get(0);
		
		if(prjtInfo != null) {
			if( userSession.getRoleCd().equals("sysadmin") // System Admin 이거나
				|| ("N".equals(prjtInfo.getRetainTranYn()) // 리테인 전송 안했고..
					&& !"CO".equals(prjtInfo.getStat())  // 완료가 아닌 프로젝트  중에
					&& (userSession.getRoleCd().equals("admin") 			   // Admin이거나
					 || userSession.getEmplNo().equals(prjtInfo.getChargPtr()) // 파트너가 본인이거나 
					 || prjtInfo.getCanDelete())							   // 삭제 권한이 있을경우
				  )
			) 
			{
				// 삭제 전 History 저장을 위한 Parameter
				ProjectVersionVO versionParam = new ProjectVersionVO();
				versionParam.setAppCd(appConf.getAppCd());
				versionParam.setPrjtCd(param.get("prjtCd").toString());
				versionParam.setCreBy(userSession.getEmplNo());
				versionParam.setVersionTy("DE");
				
				// History 저장 & 데이터 삭제
				prjtInfoService.deleteProject(param, versionParam);
				result.setStatus(Constant.RESULT.SUCCESS);
			}else {
				result.setStatus(Constant.RESULT.FAIL);
				result.setMsg(Constant.RESULT_MSG.UNAUTHORIZED_DELETE);
			}
		}else {
			result.setStatus(Constant.RESULT.FAIL);
			result.setMsg(Constant.RESULT_MSG.UNAUTHORIZED_DELETE);
		}

				
		mv.addObject("result", result);
		return mv;
	}
	
	@RequestMapping(path="/info", method=RequestMethod.POST)
	public ModelAndView getProjectInfo(HttpServletRequest request, @RequestBody Map<String, Object> param) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		ResultVO result = new ResultVO();
		UserSessionVO userSession= AppHelper.getSession(request);
		param.put(Constant.PARAM_NAME.APP_CD, appConf.getAppCd());
		param.put(Constant.PARAM_NAME.SESSION, userSession);
		
		List<ProjectInfoV2VO> prjtInfo = prjtInfoService.getProjectInfoList(param);
		
		if(prjtInfo.size() == 1) {
			// Main Project
			ProjectInfoV2Dto data = new ProjectInfoV2Dto();
			data.init(prjtInfo.get(0));
			// Sub Project
			data.setSubPrjt(prjtInfoService.getSubProjectList(data));
			
			// 해당 연도에 해당하는 표준감사시간 그룹 리스트 정보
			if(data.getBizFrdt() != null) {
				Map<String, Object> param2 = new HashMap<String, Object>();
				param2.put("year", data.getBizFrdt().substring(0, 4));
				param2.put(Constant.PARAM_NAME.APP_CD, appConf.getAppCd());
				List<Map<String, Object>> satgrpList = calcSatService.getSatgrpList(param2);
				
				Map<String, Object> tmp;
				CamelMap fctrRange;
				if(data.getSatgrp() != null && data.getFirstAdtYn() != null && "SATGRP01".equals(data.getSatgrp().get("cd")) && "Y".equals(data.getFirstAdtYn().get("cd"))){
					tmp = calcSatService.getFactorValue(data.getPrflId(), (String) data.getSatgrp().get("cd"), "FIRSTADT", null);
					fctrRange = new CamelMap();
					fctrRange.put("min", tmp.get("val1"));
					fctrRange.put("max", tmp.get("val2"));
					fctrRange.put("description", tmp.get("val1") + " ~ " +  tmp.get("val2"));
					data.setFirstAdtFctrRange(fctrRange);
				}
				if(data.getPriorAdtopinChgYn()!= null && "Y".equals(data.getPriorAdtopinChgYn().get("cd"))) {
					tmp = calcSatService.getFactorValue(data.getPrflId(), (String) data.getSatgrp().get("cd"), "PRIORADTOPINCHG", null);
					fctrRange = new CamelMap();
					fctrRange.put("min", tmp.get("val1"));
					fctrRange.put("max", tmp.get("val2"));
					fctrRange.put("description", tmp.get("val1") + " ~ " +  tmp.get("val2"));
					data.setFirstAdtFctrRange(fctrRange);
				}
				if(data.getPriorLossFctrRange()!= null && "Y".equals(data.getPriorLossFctrRange().get("cd"))) {
					tmp = calcSatService.getFactorValue(data.getPrflId(), (String) data.getSatgrp().get("cd"), "PRIORLOSS", null);
					fctrRange = new CamelMap();
					fctrRange.put("min", tmp.get("val1"));
					fctrRange.put("max", tmp.get("val2"));
					fctrRange.put("description", tmp.get("val1") + " ~ " +  tmp.get("val2"));
					data.setFirstAdtFctrRange(fctrRange);
				}
				
				data.setSatgrpList(satgrpList);
			}
			
			result.setStatus(Constant.RESULT.SUCCESS);
			result.setData(data);
		}else{
			result.setStatus(Constant.RESULT.FAIL);
			result.setMsg(Constant.RESULT_MSG.UNATUTHORIZED);
		}
		
		mv.addObject("result", result);
		return mv;
	}
	
}
