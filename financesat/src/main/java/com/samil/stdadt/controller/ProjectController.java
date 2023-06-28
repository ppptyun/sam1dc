package com.samil.stdadt.controller;

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

import com.samil.stdadt.comm.service.CommCodeService;
import com.samil.stdadt.comm.util.AppHelper;
import com.samil.stdadt.comm.vo.ApplicationConfigVO;
import com.samil.stdadt.comm.vo.UserSessionVO;
import com.samil.stdadt.service.HistoryService;
import com.samil.stdadt.service.ProjectInfoService;
import com.samil.stdadt.service.ProjectListService;
import com.samil.stdadt.util.CalculateSat;
import com.samil.stdadt.util.Constant;
import com.samil.stdadt.vo.ProjectInfoVO;
import com.samil.stdadt.vo.ProjectListVO;
import com.samil.stdadt.vo.ProjectVersionVO;
import com.samil.stdadt.vo.ResultVO;

@Controller
@RequestMapping("/json/project")
public class ProjectController {
	
	@Autowired
	ApplicationConfigVO appConf;
	
	@Autowired
	CommCodeService commService;
	
	@Autowired
	HistoryService histService;
	
	@Autowired
	ProjectListService prjtListService;
	
	@Autowired
	ProjectInfoService prjtInfoService;
	
	//1. 프로젝트 목록 가져오기
	@RequestMapping("/list")
	public ModelAndView getProjectList(HttpServletRequest request, @RequestBody(required=false) Map<String, Object> param) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		UserSessionVO userSession= AppHelper.getSession(request);
		
		if(param == null) param = new HashMap<String, Object>();
		param.put(Constant.PARAM_NAME.APP_CD, appConf.getAppCd());
		param.put(Constant.PARAM_NAME.SESSION, userSession);
		param.put("formDiv", Arrays.asList("v1"));
		
		List<ProjectListVO> prjtList = prjtListService.getProjectList(param);
		ResultVO result = new ResultVO();
		
		result.setStatus(Constant.RESULT.SUCCESS);
		result.setData(prjtList);
		mv.addObject("result", result);
		return mv;
	}
	@RequestMapping("/draftlist")
	public ModelAndView getProjectDraftList(HttpServletRequest request, @RequestBody(required=false) Map<String, Object> param) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		UserSessionVO userSession= AppHelper.getSession(request);
		
		if(param == null) param = new HashMap<String, Object>();
		param.put("isDraft", true);
		param.put(Constant.PARAM_NAME.APP_CD, appConf.getAppCd());
		param.put(Constant.PARAM_NAME.SESSION, userSession);
		
		List<Map<String, Object>> prjtList = prjtListService.getProjectDraftList(param);
		ResultVO result = new ResultVO();
		
		result.setStatus(Constant.RESULT.SUCCESS);
		result.setData(prjtList);
		mv.addObject("result", result);
		return mv;
	}
	@RequestMapping("/saveReCalculate")
	public ModelAndView saveReCalculate(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		ResultVO result = new ResultVO();
		UserSessionVO userSession= AppHelper.getSession(request);
		
		Map<String, Object> param = AppHelper.convertReqeustToMap(request);
		param.put("satTrgtYn", "Y");
		param.put(Constant.PARAM_NAME.APP_CD, appConf.getAppCd());
		param.put(Constant.PARAM_NAME.SESSION, userSession);
		
		result.setStatus(Constant.RESULT.SUCCESS);
		
		
		List<ProjectInfoVO> prjtList = prjtInfoService.getProjectInfoList(param);
		
		if(prjtList != null) {
			boolean flag = true;
			for(ProjectInfoVO prjt : prjtList) {
				// 표준감사시간 대상이고, 계산된 값이 없을경우 계산한 값으로 표현하기
				if(prjt.getSatTrgtYn().equals("Y") && prjt.getIntrAdtYn() == null) {
					ResultVO calculateSat = calculateSat(prjt);
					if(calculateSat.getStatus() == Constant.RESULT.SUCCESS) {						
						setCalculatedData(prjt, (Map<String, Object>) calculateSat.getData());
					}else {
						calculateSat.setMsg(calculateSat.getMsg() + "(" + prjt.getPrjtCd() + ")");
						result = calculateSat;
						flag = false;
						break;
					}
				}
			}
			
			if(flag) {				
				param.put("list", prjtList);
				prjtListService.saveReCalculate(param);
			}
		}
		
		mv.addObject("result", result);
		return mv;
	}
	
	@RequestMapping(path="/info", method=RequestMethod.POST)
	public ModelAndView getProject(HttpServletRequest request, @RequestBody Map<String, Object> param) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		ResultVO result = new ResultVO();
		UserSessionVO userSession= AppHelper.getSession(request);
		
		param.put(Constant.PARAM_NAME.APP_CD, appConf.getAppCd());
		param.put(Constant.PARAM_NAME.SESSION, userSession);
		
		ProjectInfoVO prjt = prjtInfoService.getProjectInfo(param);
		
		if(prjt == null) {
			result.setStatus(Constant.RESULT.FAIL);
			result.setMsg(Constant.RESULT_MSG.UNATUTHORIZED);
		}else {
			// 표준감사시간 대상이고, 계산된 값이 없을경우 계산한 값으로 표현하기
			if(prjt.getSatTrgtYn() != null && prjt.getSatTrgtYn().equals("Y") && prjt.getIntrAdtYn() == null) {
				ResultVO calculateSat = calculateSat(prjt);
				setCalculatedData(prjt, (Map<String, Object>) calculateSat.getData());
			}
			result.setStatus(Constant.RESULT.SUCCESS);
			result.setData(prjt);	
		}
		
		mv.addObject("result", result);
		return mv;
	}
	
	@RequestMapping(path="/calculateSat", method=RequestMethod.POST)
	public ModelAndView calculateSat(HttpServletRequest request, @RequestBody ProjectInfoVO prjt) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		ResultVO result = calculateSat(prjt);
		mv.addObject("result", result);
		return mv;
	}
	
	private ResultVO calculateSat(ProjectInfoVO prjt) throws Exception {
		ResultVO result = new ResultVO();
		CalculateSat calcSat = new CalculateSat(prjt);
		
		Map<String, Object> param = calcSat.getParam();
		Map<String, Object> interpolation	= prjtInfoService.getInterpolation(param);
		Map<String, Object> satgrpInfo 		= prjtInfoService.getSatgrpInfo(param);
		List<Map<String, Object>> factors 	= prjtInfoService.getFactorVal(param);
		double yearRate 					= prjtInfoService.getYearRate(param);
		Map<String, Object> wkmnsp			= prjtInfoService.getWkmnsp(appConf.getAppCd());
		
		if(interpolation == null) {
			interpolation = new HashMap<String, Object>();
			interpolation.put("baseSat", 0);
			interpolation.put("intplVal", 0);
		}
		
		if(satgrpInfo == null) {
			result.setStatus(Constant.RESULT.FAIL);
			result.setMsg(Constant.RESULT_MSG.RESULT_NULL + "(기준숙련도 및 상한율)");
			return result;
		}
		
		if(yearRate == -1) {
			result.setStatus(Constant.RESULT.FAIL);
			result.setMsg(Constant.RESULT_MSG.RESULT_NULL + "(연도별 적용 요율)");
			return result;
		}
		
		calcSat.setBaseSat(Long.parseLong(String.valueOf(interpolation.get("baseSat"))));
		calcSat.setIntplVal(Double.parseDouble(String.valueOf(interpolation.get("intplVal"))));
		calcSat.setBaseWkmnsp(Double.parseDouble(String.valueOf(satgrpInfo.get("baseWkmnsp"))));
		calcSat.setNewStfWkmnsp(Double.parseDouble(String.valueOf(wkmnsp.get("newStfWkmnsp"))));
		calcSat.setOtherWkmnsp(Double.parseDouble(String.valueOf(wkmnsp.get("otherWkmnsp"))));
		calcSat.setMaxRatio(Double.parseDouble(String.valueOf(satgrpInfo.get("maxRatio"))));
		calcSat.setYearRate(yearRate);
		calcSat.setFactorVal(factors);
		result = calcSat.calculate();
		
		Map<String, Object> data = (Map<String, Object>) result.getData();
		data.put("intrAdtYnNm", commService.getCode(appConf.getAppCd(), "INTR_ADT_YN", data.get("intrAdtYn").toString()).getNm());
		return result;
	}
	
	private void setCalculatedData(ProjectInfoVO prjt, Map<String, Object> satData) {
		for(String key : satData.keySet()) {
			switch(key) {
			case "intrAdtYn":
				prjt.setIntrAdtYn(String.valueOf(satData.get(key)));
				break;
			case "intrAdtYnNm":
				prjt.setIntrAdtYnNm(String.valueOf(satData.get(key)));
				break;
			case "intrAdtTm":
				prjt.setIntrAdtTm(Double.parseDouble(String.valueOf(satData.get(key))));
				break;
			case "factorVal":
				prjt.setFactorVal(Double.parseDouble(String.valueOf(satData.get(key))));
				break;
			case "calAdtTm":
				prjt.setCalAdtTm(Double.parseDouble(String.valueOf(satData.get(key))));
				break;
			case "calSat":
				prjt.setCalSat(Double.parseDouble(String.valueOf(satData.get(key))));
				break;
			case "baseWkmnsp":
				prjt.setBaseWkmnsp(Double.parseDouble(String.valueOf(satData.get(key))));
				break;
			case "newStfWkmnsp":
				prjt.setNewStfWkmnsp(Double.parseDouble(String.valueOf(satData.get(key))));
				break;
			case "otherWkmnsp":
				prjt.setOtherWkmnsp(Double.parseDouble(String.valueOf(satData.get(key))));
				break;
			}
		}
	}
	
	@RequestMapping(path="/factor", method=RequestMethod.POST)
	public ModelAndView getFactorVal(@RequestBody Map<String, Object> param) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		ResultVO result = new ResultVO();
		
		List<Map<String, Object>> factors = prjtInfoService.getFactorVal(param);
		
		if(factors == null) {
			result.setStatus(Constant.RESULT.FAIL);
			result.setStatus(Constant.RESULT_MSG.RESULT_NULL + "(계수 값)");
		}else {
			result.setStatus(Constant.RESULT.SUCCESS);
			result.setData(factors);	
		}
		mv.addObject("result", result);
		return mv;
	}
	
	
	@RequestMapping(path="/interpolation", method=RequestMethod.POST)
	public ModelAndView getInterplo(@RequestBody Map<String, Object> param) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		ResultVO result = new ResultVO();
		
		Map<String, Object> interpolation = prjtInfoService.getInterpolation(param);
		
		if(interpolation == null) {
			interpolation = new HashMap<String, Object>();
			interpolation.put("baseSat", 0);
			interpolation.put("intplVal", 0);
			result.setStatus(Constant.RESULT.SUCCESS);
			result.setData(interpolation);
		}else {
			result.setStatus(Constant.RESULT.SUCCESS);
			result.setData(interpolation);	
		}
		mv.addObject("result", result);
		return mv;
	}
	
	@RequestMapping(path="/satgrpinfo", method=RequestMethod.POST)
	public ModelAndView getSatGrpInfo(@RequestBody Map<String, Object> param) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		ResultVO result = new ResultVO();
		
		Map<String, Object> satgrpInfo = prjtInfoService.getSatgrpInfo(param);
		
		if(satgrpInfo == null) {
			result.setStatus(Constant.RESULT.FAIL);
			result.setMsg(Constant.RESULT_MSG.RESULT_NULL + "(기준숙련도 및 상한율)");
		}else {
			result.setStatus(Constant.RESULT.SUCCESS);
			result.setData(satgrpInfo);	
		}
		mv.addObject("result", result);
		return mv;
	}
	
	@RequestMapping(path="/year_rate", method=RequestMethod.POST)
	public ModelAndView getYearRate(@RequestBody Map<String, Object> param) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		ResultVO result = new ResultVO();
		
		double rate = prjtInfoService.getYearRate(param);
		
		if(rate == -1) {
			result.setStatus(Constant.RESULT.FAIL);
			result.setMsg(Constant.RESULT_MSG.RESULT_NULL + "(연도별 적용 요율)");
		}else {
			result.setStatus(Constant.RESULT.SUCCESS);
			result.setData(rate);	
		}
		mv.addObject("result", result);
		return mv;
	}
	
	
	@RequestMapping(path="/add", method=RequestMethod.POST)
	public ModelAndView addProject(HttpServletRequest request, @RequestBody Map<String, Object> param) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		ResultVO result = new ResultVO();
		UserSessionVO userSession= AppHelper.getSession(request);
		
		param.put(Constant.PARAM_NAME.SESSION, userSession);
		
		int ret = prjtInfoService.addProjectInfo(param);
		
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
	
	@RequestMapping(path="/update", method=RequestMethod.POST)
	public ModelAndView updateProject(HttpServletRequest request, @RequestBody Map<String, Object> param) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		ResultVO result = new ResultVO();
		UserSessionVO userSession= AppHelper.getSession(request);
		
		param.put(Constant.PARAM_NAME.SESSION, userSession);
		int ret = prjtInfoService.updateProjectInfo(param);
		
		if(ret == 0) {
			result.setStatus(Constant.RESULT.FAIL);
			result.setMsg(Constant.RESULT_MSG.SAVE_ERROR);
		}else {
			result.setStatus(Constant.RESULT.SUCCESS);
		}
		mv.addObject("result", result);
		return mv;
	}
	
	@RequestMapping(path="/delete", method=RequestMethod.POST)
	public ModelAndView deleteProject(HttpServletRequest request, @RequestBody Map<String, Object> param) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		ResultVO result = new ResultVO();
		UserSessionVO userSession= AppHelper.getSession(request);
		
		param.put(Constant.PARAM_NAME.SESSION, userSession);
		Map<String, Object> canDelete = prjtInfoService.getPrjtMetaInfo(param);
		
		if( userSession.getRoleCd().equals("sysadmin") 
				|| (canDelete.get("retainTranYn").equals("N") && 
					(userSession.getRoleCd().equals("admin") 
					|| Boolean.parseBoolean(canDelete.get("isEl").toString()) 
					|| (Boolean.parseBoolean(canDelete.get("canDelete").toString()) && !canDelete.get("stat").equals("CO"))))
		) {
			// 삭제 전 History 저장을 위한 Parameter
			ProjectVersionVO versionParam = new ProjectVersionVO();
			versionParam.setAppCd(appConf.getAppCd());
			versionParam.setPrjtCd(param.get("prjtCd").toString());
			versionParam.setCreBy(userSession.getEmplNo());
			versionParam.setVersionTy("DE");
			
			// History 저장 & 데이터 삭제
			prjtInfoService.delPrjt(param, versionParam);
			result.setStatus(Constant.RESULT.SUCCESS);
		}else {
			result.setStatus(Constant.RESULT.FAIL);
			result.setMsg(Constant.RESULT_MSG.UNAUTHORIZED_DELETE);
		}
		mv.addObject("result", result);
		return mv;
	}
	
	@RequestMapping(path="/delete/draftlist", method=RequestMethod.POST)
	public ModelAndView deleteProjectDraft(HttpServletRequest request, @RequestBody Map<String, Object> param) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		ResultVO result = new ResultVO();
		UserSessionVO userSession= AppHelper.getSession(request);
		param.put(Constant.PARAM_NAME.SESSION, userSession);
		
		List<String> prjtCdList = (List<String>) param.get("prjtCdList");
		if(prjtCdList.size() == 0) {
			result.setStatus(Constant.RESULT.FAIL);
			result.setMsg("삭제할 프로젝트 코드가 없습니다.");
		}else {
			int cnt = prjtListService.delDraftPrjtList(param);
			if(cnt == 0) {
				result.setStatus(Constant.RESULT.FAIL);
				result.setMsg("Draft 상태의 프로젝트는 생성인 본인만 삭제할 수 있습니다.");	
			}else {
				result.setStatus(Constant.RESULT.SUCCESS);
			}
		}
		
		mv.addObject("result", result);
		return mv;
	}
	
	@RequestMapping(path="/satgrpList", method=RequestMethod.POST)
	public ModelAndView getSatgrpList(@RequestBody Map<String, Object> param) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		ResultVO result = new ResultVO();
		
		param.put(Constant.PARAM_NAME.APP_CD, appConf.getAppCd());
		List<Map<String, Object>> satgrpList = prjtInfoService.getSatgrpList(param);
		
		if(satgrpList == null || (satgrpList != null && satgrpList.size() == 0)) {
			result.setStatus(Constant.RESULT.FAIL);
			result.setMsg("해당 연도에 대한 표준감사 시간 적용 요율이 정의 되어있지 않습니다. 담당자에게 문의 바랍니다.");
		}else {
			result.setStatus(Constant.RESULT.SUCCESS);
			result.setData(satgrpList);
		}
		
		mv.addObject("result", result);
		return mv;
	}
	
	
	
}

