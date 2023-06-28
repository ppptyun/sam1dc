package com.samil.stdadt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.samil.stdadt.comm.service.CommCodeService;
import com.samil.stdadt.comm.util.AppHelper;
import com.samil.stdadt.comm.vo.ApplicationConfigVO;
import com.samil.stdadt.comm.vo.CommCodeVO;
import com.samil.stdadt.comm.vo.UserSessionVO;
import com.samil.stdadt.service.AdminService;
import com.samil.stdadt.util.Constant;
import com.samil.stdadt.util.SHConverter;
import com.samil.stdadt.vo.DivisionProfileVO;
import com.samil.stdadt.vo.ResultVO;

@Controller
@RequestMapping("/json/admin") 
public class AdminController {
	
	@Autowired
	ApplicationConfigVO appConf;
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	CommCodeService commCodeService;
	
	@RequestMapping("/rateList")
	public ModelAndView selectRateList(HttpServletRequest request) throws Exception {
		HashMap<String, Object> param = new HashMap<String, Object>();
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		param.put("appCd", appConf.getAppCd());
		
		resultMap.put("yearList", adminService.selectYearList(param));
		resultMap.put("grpList", adminService.selectGrpList(param));
		resultMap.put("dataList", adminService.selectDataList(param));
		
		resultMap.put("prflList", adminService.selectPrflList(param));

		ModelAndView mv = new ModelAndView("jsonView", resultMap);
		return mv;
	}
	
	@RequestMapping("/profFctrList")
	public ModelAndView selectProfFctrList(HttpServletRequest request) throws Exception {
		HashMap<String, Object> map = SHConverter.convertJSONstringToMap(request.getParameter("formData"));
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		map.put("appCd", appConf.getAppCd());
		
		resultMap.put("prflList", adminService.selectPrflList(map));
		resultMap.put("desc", "버전 내용");
		//표준감사시간 > 숙련도(타이틀) 조회
		resultMap.put("profTitList", adminService.selectProfTitList(map));
		//표준감사시간 > 숙련도(데이터) 조회
		resultMap.put("profDataList", adminService.selectProfDataList(map));
		//표준감사시간 > 기초항목 숙련도 계수 조회
		resultMap.put("fctrDataList", adminService.selectFctrDataList(map));

		ModelAndView mv = new ModelAndView("jsonView", resultMap);
		return mv;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/aplyRateSave")
	public ModelAndView savePrflGrpYear(HttpServletRequest request) throws Exception {
		HashMap<String, Object> map = SHConverter.convertJSONstringToMap(request.getParameter("formData"));
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		UserSessionVO userSession= AppHelper.getSession(request);

		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> aplyRateList = (List<HashMap<String, Object>>)map.get("datalist");
		
		String aplyRate;
		String aplyYear;
		String grpCd;
		for(HashMap<String, Object> aplyRateMap : aplyRateList) {
			aplyRate = (String)aplyRateMap.get("aplyRate");
			aplyYear = (String)aplyRateMap.get("aplyYear");
			grpCd    = (String)aplyRateMap.get("grpCd");
			
			paramMap.clear();
			//값이 비어있다면 삭제
			if("".equals(aplyRate)){
				paramMap.put("grpCd", grpCd);
				paramMap.put("aplyYear", aplyYear);
				if(adminService.deletePrflGrpYear(paramMap) > 0) {
					resultMap.put("result", "SUCCESS");
				}
			}else{
				paramMap.put("emplno", userSession.getEmplNo());
				paramMap.put("grpCd", grpCd);
				paramMap.put("aplyYear", aplyYear);
				paramMap.put("aplyRate", Double.valueOf(aplyRate)/100);
				//저장
				if(adminService.savePrflGrpYear(paramMap) > 0) {
					resultMap.put("result", "SUCCESS");
				}
			}
		}
		ModelAndView mv = new ModelAndView("jsonView", resultMap);
		return mv;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/updateFctrWkmnsp")
	public ModelAndView updateFctrWkmnsp(HttpServletRequest request) throws Exception {
		HashMap<String, Object> map = SHConverter.convertJSONstringToMap(request.getParameter("formData"));
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		UserSessionVO userSession= AppHelper.getSession(request);

		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> fctrList = (List<HashMap<String, Object>>)map.get("datalist");
		
		String certYcnt;
		String oCertYcnt;
		String wkmnsp;
		String prflId;
		String certDv;
		
		for(HashMap<String, Object> fctrMap : fctrList) {
			certYcnt  = (String)fctrMap.get("certYcnt");
			oCertYcnt = (String)fctrMap.get("oCertYcnt");
			wkmnsp    = (String)fctrMap.get("wkmnsp");
			prflId    = (String)fctrMap.get("prflId");
			certDv    = (String)fctrMap.get("certDv");

			paramMap.clear();
			paramMap.put("emplno", userSession.getEmplNo());
			paramMap.put("certYcnt", certYcnt);
			paramMap.put("oCertYcnt", oCertYcnt);
			paramMap.put("wkmnsp", wkmnsp);
			paramMap.put("prflId", prflId);
			paramMap.put("certDv", certDv);

			//저장
			if(adminService.updateFctrWkmnsp(paramMap) > 0) {
				resultMap.put("result", "SUCCESS");
			}
		}
		ModelAndView mv = new ModelAndView("jsonView", resultMap);
		return mv;
	}	

	@RequestMapping("/updatePrflGrpFctr")
	public ModelAndView updatePrflGrpFctr(HttpServletRequest request) throws Exception {
		HashMap<String, Object> map = SHConverter.convertJSONstringToMap(request.getParameter("formData"));
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		UserSessionVO userSession= AppHelper.getSession(request);

		map.put("emplno", userSession.getEmplNo());
		//저장
		if(adminService.updatePrflGrpFctr(map) > 0) {
			resultMap.put("result", "SUCCESS");
		}
		
		ModelAndView mv = new ModelAndView("jsonView", resultMap);
		return mv;
	}	
	
	
	@RequestMapping(path="/get/divisionProfile")
	public ModelAndView getDivisionProfile(HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		
		UserSessionVO userSession = AppHelper.getSession(request);
		Map<String, Object> param = new HashMap<String, Object>();
		
		
		param.put("appCd", appConf.getAppCd());
		param.put(Constant.PARAM_NAME.SESSION, userSession);
		
		DivisionProfileVO divisionProfile = new DivisionProfileVO();
		
		List<CommCodeVO> profLock = commCodeService.getCodeList(appConf.getAppCd(), "PROF_LOCK");
		List<CommCodeVO> statList = commCodeService.getCodeList(appConf.getAppCd(), "PRJT_STAT");
		divisionProfile.setStatList(statList.stream().filter(item->"PROF_LOCK".equals(item.getVal1())).collect(Collectors.toList()));  // PROF_LOCK으로 지정된것만 Filter
		List<DivisionProfileVO.ProfAuth> profAuthByBonb = adminService.getProfileByBonb(param);
		
		for(int i=0; i<profLock.size(); i++) {
			if(profLock.get(i).getCd().equals("LOCK_PROF_YYMM")) {
				divisionProfile.setLockProfYymm(profLock.get(i).getVal1());
			}else if(profLock.get(i).getCd().equals("LOCK_PROF_MONTHS")) {
				divisionProfile.setLockProfMonths(Integer.parseInt(profLock.get(i).getVal1()));
			}else if(profLock.get(i).getCd().equals("LOCK_TRGT_STAT")) {
				divisionProfile.setLockTrgtStat(profLock.get(i).getVal1());
			}
		}
		divisionProfile.setProfAuthByBonb(profAuthByBonb);
		
		
		ResultVO result = new ResultVO();
		result.setStatus(Constant.RESULT.SUCCESS);
		result.setData(divisionProfile);
		
		mv.addObject("result", result);
		return mv;
	}
	
	@RequestMapping(path="/save/divisionProfile")
	public ModelAndView saveDivisionProfile(HttpServletRequest request, @RequestBody DivisionProfileVO divisionProfile) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		
		UserSessionVO userSession = AppHelper.getSession(request);
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("appCd", appConf.getAppCd());
		param.put(Constant.PARAM_NAME.SESSION, userSession);
		param.put("divisionProfile", divisionProfile.convertCodeList());
		
		ResultVO result = new ResultVO();
		
		int cnt = adminService.saveDivisionProfile(param);
		if(cnt > 0) {
			result.setStatus(Constant.RESULT.SUCCESS);	
		}else {
			result.setStatus(Constant.RESULT.FAIL);
			result.setMsg(Constant.RESULT_MSG.SAVE_ERROR);
		}
		mv.addObject("result", result);
		return mv;
	}
	
	
	@RequestMapping(path="/get/retainProf")
	public ModelAndView getRetainProfile(HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		
		UserSessionVO userSession = AppHelper.getSession(request);
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("appCd", appConf.getAppCd());
		param.put(Constant.PARAM_NAME.SESSION, userSession);
		
		List<Map<String, Object>> data = adminService.getRetainProfile(param);
		
		ResultVO result = new ResultVO();
		result.setStatus(Constant.RESULT.SUCCESS);
		result.setData(data);
		
		mv.addObject("result", result);
		return mv;
	}
	
	@RequestMapping(path="/save/retainProf")
	public ModelAndView saveRetainProfile(HttpServletRequest request, @RequestBody Map<String, Object> param) throws Exception{
		UserSessionVO userSession = AppHelper.getSession(request);
		
		ModelAndView mv = new ModelAndView("jsonView");
		
		param.put("appCd", appConf.getAppCd());
		param.put(Constant.PARAM_NAME.SESSION, userSession);
		
		int cnt = adminService.saveRetainProfile(param);
		
		ResultVO result = new ResultVO();
		if(cnt > 0) {
			result.setStatus(Constant.RESULT.SUCCESS);	
		}else {
			result.setStatus(Constant.RESULT.FAIL);
			result.setMsg(Constant.RESULT_MSG.SAVE_ERROR);
		}
		
		mv.addObject("result", result);
		return mv;
	}
	
	@RequestMapping(path="/get/history")
	public ModelAndView getHistory(HttpServletRequest request, @RequestBody Map<String, Object> param) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		
		List<Map<String, Object>> data = adminService.getHistory(param);
		
		ResultVO result = new ResultVO();
		result.setStatus(Constant.RESULT.SUCCESS);
		result.setData(data);
		
		mv.addObject("result", result);
		return mv;
	} 
}
