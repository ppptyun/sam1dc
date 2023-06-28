package com.samil.stdadt.controller.v4;

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
import com.samil.stdadt.dto.ProjectInfoV4Dto;
import com.samil.stdadt.service.CalcSatService;
import com.samil.stdadt.service.ProjectListService;
import com.samil.stdadt.service.v4.ProjectInfoV4Service;
import com.samil.stdadt.util.Constant;
import com.samil.stdadt.vo.ProjectInfoV4VO;
import com.samil.stdadt.vo.ProjectListV3VO;
import com.samil.stdadt.vo.ProjectVersionVO;
import com.samil.stdadt.vo.ResultVO;

@Controller
@RequestMapping("/json/project/v4")
public class ProjectControllerV4 {
	@Autowired
	ApplicationConfigVO appConf;
	
	@Autowired
	ProjectListService prjtListService;
	
	@Autowired
	CalcSatService calcSatService;
	
	@Autowired
	ProjectInfoV4Service prjtInfoService;
	
	//1. 프로젝트 목록 가져오기
	@RequestMapping("/list")
	public ModelAndView getProjectList(HttpServletRequest request, @RequestBody(required=false) Map<String, Object> param) throws Exception {
		System.out.println("------------------------------------------------------------------");
		System.out.println("#CALL INFO :: [" + Arrays.toString(this.getClass().getAnnotations()) + "]");
		System.out.println("#CALL METHOD :: [" + this.getClass().getName() + "." + new Object() {}.getClass().getEnclosingMethod().getName() + "]");		
		System.out.println("------------------------------------------------------------------");		

		ModelAndView mv = new ModelAndView("jsonView");
		UserSessionVO userSession= AppHelper.getSession(request);
		
		if(param == null) param = new HashMap<String, Object>();
		param.put(Constant.PARAM_NAME.APP_CD, appConf.getAppCd());
		param.put(Constant.PARAM_NAME.SESSION, userSession);
		param.put("formDiv", Arrays.asList("v1", "v2", "v3", "v4"));
		
		List<ProjectListV3VO> prjtList = prjtListService.getProjectListV3(param);
		ResultVO result = new ResultVO();
		
		result.setStatus(Constant.RESULT.SUCCESS);
		result.setData(prjtList);
		mv.addObject("result", result);
		return mv;
	}
	
	//2 Draft로 저장된 프로젝트 목록 가져오기
	@RequestMapping("/draftlist")
	public ModelAndView getProjectDraftList(HttpServletRequest request, @RequestBody(required=false) Map<String, Object> param) throws Exception {
		System.out.println("------------------------------------------------------------------");
		System.out.println("#CALL INFO :: [" + Arrays.toString(this.getClass().getAnnotations()) + "]");
		System.out.println("#CALL METHOD :: [" + this.getClass().getName() + "." + new Object() {}.getClass().getEnclosingMethod().getName() + "]");		
		System.out.println("------------------------------------------------------------------");		

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
	public ModelAndView addProject(HttpServletRequest request, @RequestBody ProjectInfoV4Dto param) throws Exception{
		System.out.println("------------------------------------------------------------------");
		System.out.println("#CALL INFO :: [" + Arrays.toString(this.getClass().getAnnotations()) + "]");
		System.out.println("#CALL METHOD :: [" + this.getClass().getName() + "." + new Object() {}.getClass().getEnclosingMethod().getName() + "]");		
		System.out.println("------------------------------------------------------------------");		

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
	public ModelAndView updateProject(HttpServletRequest request, @RequestBody ProjectInfoV4Dto param) throws Exception{
		System.out.println("------------------------------------------------------------------");
		System.out.println("#CALL INFO :: [" + Arrays.toString(this.getClass().getAnnotations()) + "]");
		System.out.println("#CALL METHOD :: [" + this.getClass().getName() + "." + new Object() {}.getClass().getEnclosingMethod().getName() + "]");		
		System.out.println("------------------------------------------------------------------");		
		System.out.println("PARAM :: " + param.toString());
		System.out.println("------------------------------------------------------------------");		

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
	
	//프로젝트 삭제 - 상태값 변경
	@RequestMapping(path="/delete", method=RequestMethod.POST)
	public ModelAndView deleteProject(HttpServletRequest request, @RequestBody Map<String, Object> param) throws Exception{
		System.out.println("------------------------------------------------------------------");
		System.out.println("#CALL INFO :: [" + Arrays.toString(this.getClass().getAnnotations()) + "]");
		System.out.println("#CALL METHOD :: [" + this.getClass().getName() + "." + new Object() {}.getClass().getEnclosingMethod().getName() + "]");		
		System.out.println("------------------------------------------------------------------");		

		ModelAndView mv = new ModelAndView("jsonView");
		ResultVO result = new ResultVO();
		UserSessionVO userSession= AppHelper.getSession(request);
		param.put("session", userSession);
		
		List<ProjectInfoV4VO> prjtInfoList = prjtInfoService.getProjectInfoList(param);
		ProjectInfoV4VO prjtInfo = prjtInfoList==null?null:prjtInfoList.get(0);
		
		if(prjtInfo != null) {
			if( userSession.getRoleCd().equals("sysadmin") // System Admin 이거나
				|| (
					(userSession.getRoleCd().equals("admin") || userSession.getEmplNo().equals(prjtInfo.getChargPtr()) || prjtInfo.getCanDelete()) // Admin, 파트너, 그외 일반 사용자 중 삭제권한 있을 경우
					 && Arrays.asList("RG", "CO", "RJ").contains(prjtInfo.getStat()) // RG, CO, RJ 만 삭제 가능
				)
			) {
				if(prjtInfoService.deleteProject(param)) {
					result.setStatus(Constant.RESULT.SUCCESS);
				} else {
					result.setStatus(Constant.RESULT.FAIL);
					result.setMsg("삭제 대상이 없습니다.");
				}
			}else {
				result.setStatus(Constant.RESULT.UNAUTHORIZED);
				result.setMsg("삭제 권한이 없습니다.");
			}
			
		} else {
			result.setStatus(Constant.RESULT.FAIL);
			result.setMsg("삭제 대상이 없습니다.");
		}
		
		mv.addObject("result", result);
		return mv;
	}
	
	@RequestMapping(path="/restore", method=RequestMethod.POST)
	public ModelAndView restoreProject(HttpServletRequest request, @RequestBody Map<String, Object> param) throws Exception{
		System.out.println("------------------------------------------------------------------");
		System.out.println("#CALL INFO :: [" + Arrays.toString(this.getClass().getAnnotations()) + "]");
		System.out.println("#CALL METHOD :: [" + this.getClass().getName() + "." + new Object() {}.getClass().getEnclosingMethod().getName() + "]");		
		System.out.println("------------------------------------------------------------------");		

		ModelAndView mv = new ModelAndView("jsonView");
		ResultVO result = new ResultVO();
		UserSessionVO userSession= AppHelper.getSession(request);
		param.put("session", userSession);
		
		List<ProjectInfoV4VO> prjtInfoList = prjtInfoService.getProjectInfoList(param);
		ProjectInfoV4VO prjtInfo = prjtInfoList==null?null:prjtInfoList.get(0);
		
		if(prjtInfo != null) {
			if( prjtInfo.getCanRestore()) {
				if(prjtInfoService.restoreProject(param)) {
					result.setStatus(Constant.RESULT.SUCCESS);
				} else {
					result.setStatus(Constant.RESULT.FAIL);
					result.setMsg("복구 대상이 없습니다.");
				}
			}else {
				result.setStatus(Constant.RESULT.UNAUTHORIZED);
				result.setMsg("복구 권한이 없습니다.");
			}
			
		} else {
			result.setStatus(Constant.RESULT.FAIL);
			result.setMsg("복구 대상이 없습니다.");
		}
		
		mv.addObject("result", result);
		return mv;
	}
		
	//프로젝트 영구 삭제
	@RequestMapping(path="/delete/permanently", method=RequestMethod.POST)
	public ModelAndView deletePermanentlyProject(HttpServletRequest request, @RequestBody Map<String, Object> param) throws Exception{
		System.out.println("------------------------------------------------------------------");
		System.out.println("#CALL INFO :: [" + Arrays.toString(this.getClass().getAnnotations()) + "]");
		System.out.println("#CALL METHOD :: [" + this.getClass().getName() + "." + new Object() {}.getClass().getEnclosingMethod().getName() + "]");		
		System.out.println("------------------------------------------------------------------");		

		ModelAndView mv = new ModelAndView("jsonView");
		ResultVO result = new ResultVO();
		UserSessionVO userSession= AppHelper.getSession(request);
		param.put("session", userSession);
		
		List<ProjectInfoV4VO> prjtInfoList = prjtInfoService.getProjectInfoList(param);
		ProjectInfoV4VO prjtInfo = prjtInfoList==null?null:prjtInfoList.get(0);
		
		if(prjtInfo != null) {
			if( userSession.getRoleCd().equals("sysadmin") // System Admin 이거나
				|| (
					(userSession.getRoleCd().equals("admin") 
							|| userSession.getEmplNo().equals(prjtInfo.getChargPtr()) 
							|| userSession.getEmplNo().equals(prjtInfo.getChargMgr()) 
							|| prjtInfo.getCanDelete()) // Admin, 파트너, 매니저, 그외 일반 사용자 중 삭제권한 있을 경우
					&& "N".equals(prjtInfo.getRetainTranYn()) // 리테인 전송 하지 않은 것만 삭제 가능
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
				prjtInfoService.deletePermanentlyProject(param, versionParam);
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
		System.out.println("------------------------------------------------------------------");
		System.out.println("#CALL INFO :: [" + Arrays.toString(this.getClass().getAnnotations()) + "]");
		System.out.println("#CALL METHOD :: [" + this.getClass().getName() + "." + new Object() {}.getClass().getEnclosingMethod().getName() + "]");		
		System.out.println("------------------------------------------------------------------");		

		ModelAndView mv = new ModelAndView("jsonView");
		ResultVO result = new ResultVO();
		UserSessionVO userSession= AppHelper.getSession(request);
		param.put(Constant.PARAM_NAME.APP_CD, appConf.getAppCd());
		param.put(Constant.PARAM_NAME.SESSION, userSession);
		
		List<ProjectInfoV4VO> prjtInfo = prjtInfoService.getProjectInfoList(param);
		
		if(prjtInfo.size() == 1) {
			// Main Project
			ProjectInfoV4Dto data = new ProjectInfoV4Dto();
			data.init(prjtInfo.get(0));
			// Sub Project
			// data.setSubPrjt(prjtInfoService.getSubProjectList(data));
			
			// 해당 연도에 해당하는 표준감사시간 그룹 리스트 정보
			if(data.getBizFrdt() != null) {
				Map<String, Object> param2 = new HashMap<String, Object>();
				param2.put("year", data.getBizFrdt().substring(0, 4));
				param2.put(Constant.PARAM_NAME.APP_CD, appConf.getAppCd());
				List<Map<String, Object>> satgrpList = calcSatService.getSatgrpList(param2);
				
				Map<String, Object> tmp;
				CamelMap fctrRange;
				if(data.getSatgrp() != null && data.getFirstAdtYn() != null && "SATGRP01".equals(data.getSatgrp().get("cd")) && "Y".equals(data.getFirstAdtYn())){
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
