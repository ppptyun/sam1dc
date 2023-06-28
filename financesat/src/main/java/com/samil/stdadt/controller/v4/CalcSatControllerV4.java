package com.samil.stdadt.controller.v4;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.samil.stdadt.comm.service.CommCodeService;
import com.samil.stdadt.comm.util.AppHelper;
import com.samil.stdadt.comm.vo.ApplicationConfigVO;
import com.samil.stdadt.comm.vo.CamelMap;
import com.samil.stdadt.comm.vo.UserSessionVO;
import com.samil.stdadt.dto.ProjectInfoV4Dto;
import com.samil.stdadt.service.v4.CalcSatV4Service;
import com.samil.stdadt.service.HistoryService;
import com.samil.stdadt.service.ProjectInfoService;
import com.samil.stdadt.util.CalculateSat;
import com.samil.stdadt.util.Constant;
import com.samil.stdadt.vo.ProjectInfoV4VO;
import com.samil.stdadt.vo.ProjectInfoVO;
import com.samil.stdadt.vo.ResultVO;

@Controller
@RequestMapping("/json/calcSat/v4")
public class CalcSatControllerV4 {
	
	@Autowired
	ApplicationConfigVO appConf;
	
	@Autowired
	CommCodeService commService;
	
	@Autowired
	HistoryService histService;
	
	
	@Autowired
	ProjectInfoService prjtInfoService;
	
	@Autowired
	CalcSatV4Service calcSatService;
	
	
	@RequestMapping(path="/basewkmnsp", method=RequestMethod.POST)
	public ModelAndView getBaseWkmnsp(HttpServletRequest request, @RequestBody Map<String, Object> param) throws Exception {
		System.out.println("------------------------------------------------------------------");
		System.out.println("#CALL INFO :: [" + Arrays.toString(this.getClass().getAnnotations()) + "]");
		System.out.println("#CALL METHOD :: [" + this.getClass().getName() + "." + new Object() {}.getClass().getEnclosingMethod().getName() + "]");		
		System.out.println("------------------------------------------------------------------");		

		ModelAndView mv = new ModelAndView("jsonView");
		ResultVO result = new ResultVO();
		param.put(Constant.PARAM_NAME.APP_CD, appConf.getAppCd());
		
		Map<String, Object> grpPrflInfo = calcSatService.getGrpPrflInfo(param);
		
		if(grpPrflInfo == null) {
			result.setStatus(Constant.RESULT.FAIL);
			result.setMsg(Constant.RESULT_MSG.UNATUTHORIZED);
		}else {
			result.setStatus(Constant.RESULT.SUCCESS);
			result.setData(grpPrflInfo.get("baseWkmnsp"));
		}
		mv.addObject("result", result);
		return mv;
	}
	
	@RequestMapping(path="/grpPrfl", method=RequestMethod.POST)
	public ModelAndView getGrpPrfl(HttpServletRequest request, @RequestBody Map<String, Object> param) throws Exception {
		System.out.println("------------------------------------------------------------------");
		System.out.println("#CALL INFO :: [" + Arrays.toString(this.getClass().getAnnotations()) + "]");
		System.out.println("#CALL METHOD :: [" + this.getClass().getName() + "." + new Object() {}.getClass().getEnclosingMethod().getName() + "]");		
		System.out.println("------------------------------------------------------------------");		

		ModelAndView mv = new ModelAndView("jsonView");
		ResultVO result = new ResultVO();
		param.put(Constant.PARAM_NAME.APP_CD, appConf.getAppCd());
		
		Map<String, Object> grpPrflInfo = calcSatService.getGrpPrflInfo(param);
		
		if(grpPrflInfo == null) {
			result.setStatus(Constant.RESULT.FAIL);
			result.setMsg(Constant.RESULT_MSG.UNATUTHORIZED);
		}else {
			result.setStatus(Constant.RESULT.SUCCESS);
			result.setData(grpPrflInfo);
		}
		mv.addObject("result", result);
		return mv;
	}
	
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
		
		List<Map<String, Object>> prjtList = calcSatService.getCalcSatList(param);
		ResultVO result = new ResultVO();
		
		result.setStatus(Constant.RESULT.SUCCESS);
		result.setData(prjtList);
		mv.addObject("result", result);
		return mv;
	}
	
	@RequestMapping("/draftlist")
	public ModelAndView getProjectDraftList(HttpServletRequest request) throws Exception {
		System.out.println("------------------------------------------------------------------");
		System.out.println("#CALL INFO :: [" + Arrays.toString(this.getClass().getAnnotations()) + "]");
		System.out.println("#CALL METHOD :: [" + this.getClass().getName() + "." + new Object() {}.getClass().getEnclosingMethod().getName() + "]");		
		System.out.println("------------------------------------------------------------------");		

		ModelAndView mv = new ModelAndView("jsonView");
		UserSessionVO userSession= AppHelper.getSession(request);
		HashMap<String, Object> param = new HashMap<String, Object>();
		
		param.put("isDraft", true);
		param.put(Constant.PARAM_NAME.APP_CD, appConf.getAppCd());
		param.put(Constant.PARAM_NAME.SESSION, userSession);
		
		List<Map<String, Object>> prjtList = calcSatService.getCalcSatList(param);
		ResultVO result = new ResultVO();
		
		result.setStatus(Constant.RESULT.SUCCESS);
		result.setData(prjtList);
		mv.addObject("result", result);
		return mv;
	}
	
	@RequestMapping(path="/info", method=RequestMethod.POST)
	public ModelAndView getProject(HttpServletRequest request, @RequestBody Map<String, Object> param) throws Exception {
		System.out.println("------------------------------------------------------------------");
		System.out.println("#CALL INFO :: [" + Arrays.toString(this.getClass().getAnnotations()) + "]");
		System.out.println("#CALL METHOD :: [" + this.getClass().getName() + "." + new Object() {}.getClass().getEnclosingMethod().getName() + "]");		
		System.out.println("------------------------------------------------------------------");		

		ModelAndView mv = new ModelAndView("jsonView");
		ResultVO result = new ResultVO();
		UserSessionVO userSession= AppHelper.getSession(request);
		
		param.put(Constant.PARAM_NAME.APP_CD, appConf.getAppCd());
		param.put(Constant.PARAM_NAME.SESSION, userSession);
		
		ProjectInfoV4VO prjt = calcSatService.getCalcSatInfo(param).get(0);
		ProjectInfoV4Dto prjtDto = new ProjectInfoV4Dto();
		
		try {
			if(prjt == null) {
				result.setStatus(Constant.RESULT.FAIL);
				result.setMsg(Constant.RESULT_MSG.UNATUTHORIZED);
			}else {
				prjtDto.init(prjt);
				
				if(prjtDto.getBizFrdt() != null) {
					Map<String, Object> param2 = new HashMap<String, Object>();
					param2.put(Constant.PARAM_NAME.APP_CD, appConf.getAppCd());
					param2.put("year", prjtDto.getBizFrdt().substring(0, 4));
					param2.put("prflId", prjtDto.getPrflId());
					
					Map<String, Object> tmp;
					CamelMap fctrRange;
					
					Map<String, Object> paramMap;
					if(prjtDto.getSatgrp() != null && prjtDto.getFirstAdtYn() != null && "SATGRP01".equals(prjtDto.getSatgrp().get("cd")) && "Y".equals(prjtDto.getFirstAdtYn())){
						paramMap = new HashMap<String, Object>();
						paramMap.put("satgrpCd", (String) prjtDto.getSatgrp().get("cd"));
						paramMap.put("factorCd", "FIRSTADT");
						paramMap.put("grpFactorDiv", "Y");
						paramMap.put("divType", "1");
						
						tmp = calcSatService.getFactorValue(paramMap);
						fctrRange = new CamelMap();
						fctrRange.put("min", tmp.get("val1"));
						fctrRange.put("max", tmp.get("val2"));
						fctrRange.put("description", tmp.get("val1") + " ~ " +  tmp.get("val2"));
						prjtDto.setFirstAdtFctrRange(fctrRange);
					}
					if(prjtDto.getPriorAdtopinChgYn()!= null && "Y".equals(prjtDto.getPriorAdtopinChgYn().get("cd"))) {
						paramMap = new HashMap<String, Object>();
						paramMap.put("satgrpCd", (String) prjtDto.getSatgrp().get("cd"));
						paramMap.put("factorCd", "PRIORADTOPINCHG");
						paramMap.put("grpFactorDiv", "Y");
						paramMap.put("divType", "1");
						
						tmp = calcSatService.getFactorValue(paramMap);
						fctrRange = new CamelMap();
						fctrRange.put("min", tmp.get("val1"));
						fctrRange.put("max", tmp.get("val2"));
						fctrRange.put("description", tmp.get("val1") + " ~ " +  tmp.get("val2"));
						prjtDto.setFirstAdtFctrRange(fctrRange);
					}
					if(prjtDto.getPriorLossFctrRange()!= null && "Y".equals(prjtDto.getPriorLossFctrRange().get("cd"))) {
						paramMap = new HashMap<String, Object>();
						paramMap.put("satgrpCd", (String) prjtDto.getSatgrp().get("cd"));
						paramMap.put("factorCd", "PRIORLOSS");
						paramMap.put("grpFactorDiv", "Y");
						paramMap.put("divType", "1");
						
						tmp = calcSatService.getFactorValue(paramMap);
						fctrRange = new CamelMap();
						fctrRange.put("min", tmp.get("val1"));
						fctrRange.put("max", tmp.get("val2"));
						fctrRange.put("description", tmp.get("val1") + " ~ " +  tmp.get("val2"));
						prjtDto.setFirstAdtFctrRange(fctrRange);
					}
					
					List<Map<String, Object>> satgrpList = calcSatService.getSatgrpList(param2);
					prjtDto.setSatgrpList(satgrpList);
				}
				
				result.setStatus(Constant.RESULT.SUCCESS);
				result.setData(prjtDto);	
			}
		}catch(Exception e) {
			e.printStackTrace();
			result.setStatus(Constant.RESULT.FAIL);
			result.setMsg(Constant.RESULT_MSG.ERROR);
		}
		
		mv.addObject("result", result);
		return mv;
	}
	
	
	
	/*@RequestMapping(path="/info", method=RequestMethod.POST)
	public ModelAndView getProject(HttpServletRequest request, @RequestBody Map<String, Object> param) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		ResultVO result = new ResultVO();
		UserSessionVO userSession= AppHelper.getSession(request);
		
		param.put(Constant.PARAM_NAME.APP_CD, appConf.getAppCd());
		param.put(Constant.PARAM_NAME.SESSION, userSession);
		
		ProjectInfoVO prjt = calcSatService.getCalcSatInfo(param).get(0);
		
		if(prjt == null) {
			result.setStatus(Constant.RESULT.FAIL);
			result.setMsg(Constant.RESULT_MSG.UNATUTHORIZED);
		}else {
			result.setStatus(Constant.RESULT.SUCCESS);
			result.setData(prjt);	
		}
		mv.addObject("result", result);
		return mv;
	}*/
	
	@RequestMapping(path="/calculateSat", method=RequestMethod.POST)
	public ModelAndView calculateSat(HttpServletRequest request, @RequestBody ProjectInfoVO prjt) throws Exception {
		System.out.println("------------------------------------------------------------------");
		System.out.println("#CALL INFO :: [" + Arrays.toString(this.getClass().getAnnotations()) + "]");
		System.out.println("#CALL METHOD :: [" + this.getClass().getName() + "." + new Object() {}.getClass().getEnclosingMethod().getName() + "]");		
		System.out.println("------------------------------------------------------------------");		

		ModelAndView mv = new ModelAndView("jsonView");
		ResultVO result = calculateSat(prjt);
		mv.addObject("result", result);
		return mv;
	}
	
	private ResultVO calculateSat(ProjectInfoVO prjt) throws Exception {
		System.out.println("------------------------------------------------------------------");
		System.out.println("#CALL INFO :: [" + Arrays.toString(this.getClass().getAnnotations()) + "]");
		System.out.println("#CALL METHOD :: [" + this.getClass().getName() + "." + new Object() {}.getClass().getEnclosingMethod().getName() + "]");		
		System.out.println("------------------------------------------------------------------");		

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
	
	
	@RequestMapping(path="/add", method=RequestMethod.POST)
	public ModelAndView addCalSat(HttpServletRequest request, @RequestBody ProjectInfoV4Dto param) throws Exception{
		System.out.println("------------------------------------------------------------------");
		System.out.println("#CALL INFO :: [" + Arrays.toString(this.getClass().getAnnotations()) + "]");
		System.out.println("#CALL METHOD :: [" + this.getClass().getName() + "." + new Object() {}.getClass().getEnclosingMethod().getName() + "]");		
		System.out.println("------------------------------------------------------------------");		

		ModelAndView mv = new ModelAndView("jsonView");
		ResultVO result = new ResultVO();
		UserSessionVO userSession= AppHelper.getSession(request);
		param.setSession(userSession);
		param.setPrjtCd(RandomStringUtils.randomAlphanumeric(12));
		
		// 표준감사 후 저장하기
		int ret = calcSatService.addCalcSatInfo(param);
		
		if(ret == 0) {
			result.setStatus(Constant.RESULT.FAIL);
			result.setMsg(Constant.RESULT_MSG.SAVE_ERROR);
		}else {
			result.setStatus(Constant.RESULT.SUCCESS);
			result.setData(param.getPrjtCd());
		}
		
		mv.addObject("result", result);
		return mv;
	}
	
	@RequestMapping(path="/update", method=RequestMethod.POST)
	public ModelAndView updateCalSat(HttpServletRequest request, @RequestBody ProjectInfoV4Dto param) throws Exception{
		System.out.println("------------------------------------------------------------------");
		System.out.println("#CALL INFO :: [" + Arrays.toString(this.getClass().getAnnotations()) + "]");
		System.out.println("#CALL METHOD :: [" + this.getClass().getName() + "." + new Object() {}.getClass().getEnclosingMethod().getName() + "]");		
		System.out.println("------------------------------------------------------------------");		

		ModelAndView mv = new ModelAndView("jsonView");
		ResultVO result = new ResultVO();
		UserSessionVO userSession= AppHelper.getSession(request);
		param.setSession(userSession);
		
		// 표준감사 계산후 저장하기
		int ret = calcSatService.updateCalcSatInfo(param);
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
	public ModelAndView delelteCalSat(HttpServletRequest request, @RequestBody Map<String, Object> param) throws Exception{
		System.out.println("------------------------------------------------------------------");
		System.out.println("#CALL INFO :: [" + Arrays.toString(this.getClass().getAnnotations()) + "]");
		System.out.println("#CALL METHOD :: [" + this.getClass().getName() + "." + new Object() {}.getClass().getEnclosingMethod().getName() + "]");		
		System.out.println("------------------------------------------------------------------");		

		ModelAndView mv = new ModelAndView("jsonView");
		ResultVO result = new ResultVO();
		UserSessionVO userSession= AppHelper.getSession(request);
		param.put(Constant.PARAM_NAME.SESSION, userSession);
		
		// 표준감사 계산후 저장하기
		int ret = calcSatService.deleteCalcSatInfo(param);
		if(ret == 0) {
			result.setStatus(Constant.RESULT.FAIL);
			result.setMsg(Constant.RESULT_MSG.UNAUTHORIZED_DELETE);
		}else {
			result.setStatus(Constant.RESULT.SUCCESS);
		}
		mv.addObject("result", result);
		return mv;
	}
	
	@RequestMapping(path="/calcsat", method=RequestMethod.POST)
	public ModelAndView calcSat(HttpServletRequest request, @RequestBody ProjectInfoV4Dto param) throws Exception{
		System.out.println("------------------------------------------------------------------");
		System.out.println("#CALL INFO :: [" + Arrays.toString(this.getClass().getAnnotations()) + "]");
		System.out.println("#CALL METHOD :: [" + this.getClass().getName() + "." + new Object() {}.getClass().getEnclosingMethod().getName() + "]");		
		System.out.println("------------------------------------------------------------------");		

		ModelAndView mv = new ModelAndView("jsonView");
		ResultVO result = new ResultVO();
		
		Map<String, Object> satgrp		= param.getSatgrp();
		Map<String, Object> indusDv		= param.getIndusDv();
		Map<String, Object> holdingsDv	= param.getHoldingsDv();
		Map<String, Object> intrAdtYcnt	= param.getIntrAdtYcnt();
		String minMaxYn	= param.getMinMaxYn();
		Boolean sameAdtrSbsidYn 		= param.getSameAdtrSbsidYn();
		
		String prflId					= param.getPrflId();
		String bizFrdt					= param.getBizFrdt();
		Long compSize					= param.getCompSize();
		
		// 동일감사인이 감사하는 종속회사 또는 관계회사 존재여부 체크 해제 시 계산에서 제외 
		Long intrTranAssetSales 		= sameAdtrSbsidYn?param.getIntrTranAssetSales():0L;
		Long sbsidAssetWithIntrTran 	= sameAdtrSbsidYn?param.getSbsidAssetWithIntrTran():0L;
		Long sbsidSalesWithIntrTran 	= sameAdtrSbsidYn?param.getSbsidSalesWithIntrTran():0L;
		Long relatCompAsset 			= sameAdtrSbsidYn?param.getRelatCompAsset():0L;
		Double minTm					= param.getMinTm();
		Double maxTm					= param.getMaxTm();
		
		//2022-03-10 남웅주
		//최초 표준감사시간 대상 직전 감사시간 
		Double fstSatTrgtBftm = param.getFstSatTrgtBftm();
		
		// 표준감사시간표 적용 기업규모
		Long tmpCompSize = (long) Math.floor((compSize + (intrTranAssetSales/2) - ((sbsidAssetWithIntrTran + sbsidSalesWithIntrTran + relatCompAsset)/2) )/100000000);
		
		// 표준감사시간표 적용 기업규모
		Integer intplCompSize = getIntplCompSize((String) satgrp.get("cd"), tmpCompSize);
		
		System.out.println("------------------------------------------------------------------");
		System.out.println(">> 표준감사시간표 적용 기업규모 :: [" + intplCompSize + "]");
		System.out.println("------------------------------------------------------------------");
		
		Map<String, Object> param2 = new HashMap<String, Object>();
		param2.put(Constant.PARAM_NAME.APP_CD, appConf.getAppCd());
		param2.put("holdingsDvCd", holdingsDv.get("cd"));
		param2.put("intrAdtYcnt", intrAdtYcnt.get("cd"));
		param2.put("prflId", prflId);
		param2.put("satgrpCd", satgrp.get("cd"));
		param2.put("indusCd", indusDv.get("cd"));
		param2.put("baseCompSize", intplCompSize);
		param2.put("year", bizFrdt.substring(0, 4));
				
		Map<String, Object> grpIndus 	= calcSatService.getGrpIndusPrfl(param2);
		Map<String, Object> grpYear 	= calcSatService.getGrpYearPrfl(param2);
		
		// 보간법
		Double intplVal = grpIndus.get("intplVal")==null?0.0d:((BigDecimal) grpIndus.get("intplVal")).doubleValue();
		// 표준감사시간표
		Integer baseSat = grpIndus.get("baseSat")==null?0:((BigDecimal) grpIndus.get("baseSat")).intValue();		
		// 보간법에 따른 가감
		Double IntplModVal = intplVal == 0.0d?0.0d:(tmpCompSize - intplCompSize) * intplVal;		
		//  표준감사시간표(보간법) 
		Integer intplSat = (int) Math.round(baseSat + IntplModVal);		
		// 가감율
		Double factorVal = param.getFactorVal();
		
		System.out.println("------------------------------------------------------------------");
		System.out.println(">> 보간법 :: [" + intplVal + "]");
		System.out.println(">> 표준감사시간표 :: [" + baseSat + "]");
		System.out.println(">> 보간법에 따른 가감 :: [" + IntplModVal + "]");
		System.out.println(">> 표준감사시간표(보간법) :: [" + intplSat + "]");
		System.out.println(">> 가감율 :: [" + factorVal + "]");
		System.out.println("------------------------------------------------------------------");
		System.out.println(">> 상하한 여부 :: [" + minMaxYn + "]");
		
		// 산식에 따른 결과
		Double calResult =  intplSat * factorVal;		
		// 적용 요율
		Double yearRate = grpYear.get("aplyRate") == null?0.0d:((BigDecimal) grpYear.get("aplyRate")).doubleValue();		
		// 산식결과 * 적용요율
		Double tmpCaldtTm = calResult * yearRate;
		
		// 산출된 감사시간(상하한 적용 후)(a) 
		Double calAdtTm;
		if("O".equals(minMaxYn)) {
			if(minTm > tmpCaldtTm) {
				calAdtTm = minTm;
			}else if(maxTm < tmpCaldtTm) {
				calAdtTm = maxTm;
			}else {
				calAdtTm = tmpCaldtTm;	
			}
		}else {
			// X 인 경우 
			if(fstSatTrgtBftm > tmpCaldtTm) {
				calAdtTm = fstSatTrgtBftm;
			}else {
				calAdtTm = tmpCaldtTm;	
			}
		}
		
		// 내부회계감사 계수(b)
		//Double intrAdtFctr = calcSatService.getIntrAdtRatio(param2);
		Double intrAdtFctr = param.getIntrAdtFctr();
		
		System.out.println("------------------------------------------------------------------");
		System.out.println(">> 산식에 따른 결과 :: [" + calResult + "]");
		System.out.println(">> 적용 요율 :: [" + yearRate + "]");
		System.out.println(">> 산식결과 * 적용요율 :: [" + tmpCaldtTm + "]");
		System.out.println(">> 산출된 감사시간(상하한 적용 후)(a)  :: [" + calAdtTm + "]");
		System.out.println(">> 내부회계감사 계수(b) :: [" + intrAdtFctr + "]");
		System.out.println("------------------------------------------------------------------");		
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("intplSat", intplSat);
		data.put("calResult", calResult);
		data.put("yearRate", yearRate);
		data.put("calAdtTm", calAdtTm);
		data.put("intrAdtFctr", intrAdtFctr);
		
		result.setStatus(Constant.RESULT.SUCCESS);
		result.setData(data);
		mv.addObject("result", result);
		
		System.out.println("------------------------------------------------------------------");
		System.out.println("# data :: [" + data.toString() + "]");
		System.out.println("# result :: [" + result.toString() + "]");
		System.out.println("------------------------------------------------------------------");		
		
		return mv;
	}
	
	private Integer getIntplCompSize(String satgrpCd, Long compSize) {
		Integer intplCompSize = 0;	// 보간 적용 기업 규모
		
		if("SATGRP01".equals(satgrpCd)){
			intplCompSize = compSize>=3000000?3000000:compSize>=2000000?2000000:compSize>=1000000?1000000:compSize>=500000?500000:compSize>=200000?200000:compSize>=100000?100000:compSize>=50000?50000:0;
		}else if("SATGRP02".equals(satgrpCd)){
			intplCompSize = compSize>=40000?40000:compSize>=30000?30000:compSize>=20000?20000:compSize>=10000?10000:0;
		}else if("SATGRP03".equals(satgrpCd)){
			intplCompSize = compSize>=50000?50000:compSize>=20000?20000:compSize>=15000?15000:compSize>=10000?10000:compSize>=8000?8000:compSize>=5000?5000:compSize>=3000?3000:0;
		}else if("SATGRP04".equals(satgrpCd)){
			intplCompSize = compSize>=10000?10000:compSize>=5000?5000:compSize>=4000?4000:compSize>=3000?3000:compSize>=2000?2000:compSize>=1000?1000:compSize>=500?500:0;
		}else if("SATGRP05".equals(satgrpCd)){			
			intplCompSize = compSize>=2000?2000:compSize>=1500?1500:compSize>=1000?1000:compSize>=800?800:compSize>=500?500:compSize>=300?300:0;			
		}else if("SATGRP06".equals(satgrpCd)){
			intplCompSize = compSize>=1000?1000:compSize>=500?500:compSize>=400?400:compSize>=300?300:compSize>=200?200:compSize>=100?100:0;
		}else if("SATGRP07".equals(satgrpCd)){
			intplCompSize = compSize>=500000?500000:compSize>=100000?100000:compSize>=50000?50000:compSize>=10000?10000:compSize>=5000?5000:compSize>=1000?1000:compSize>=500?500:compSize>=200?200:compSize>=100?100:0;
		}else if("SATGRP08".equals(satgrpCd)){
			intplCompSize = compSize>=500000?500000:compSize>=100000?100000:compSize>=50000?50000:compSize>=20000?20000:compSize>=10000?10000:compSize>=5000?5000:compSize>=2000?2000:compSize>=1000?1000:compSize>=500?500:0;
		}else if("SATGRP09".equals(satgrpCd)){
			intplCompSize = compSize>=2000?2000:compSize>=1000?1000:compSize>=900?900:compSize>=800?800:compSize>=700?700:compSize>=600?600:compSize>=500?500:compSize>=200?200:0;
		}else if("SATGRP10".equals(satgrpCd)){
			intplCompSize = compSize>=1000?1000:compSize>=500?500:compSize>=400?400:compSize>=300?300:compSize>=200?200:compSize>=100?100:0;
		}else{
			intplCompSize = 0;
		}
		
		return intplCompSize;
	}
	
	@RequestMapping(path="/getFactorValue", method=RequestMethod.POST)
	public ModelAndView getFactorValue(HttpServletRequest request, @RequestBody Map<String, Object> param) throws Exception {
		System.out.println("------------------------------------------------------------------");		
		System.out.println("#CALL INFO :: [" + Arrays.toString(this.getClass().getAnnotations()) + "]");
		System.out.println("#CALL METHOD :: [" + this.getClass().getName() + "." + new Object() {}.getClass().getEnclosingMethod().getName() + "]");		
		System.out.println("------------------------------------------------------------------");		
		System.out.println("param :: " + param.toString());
		System.out.println("------------------------------------------------------------------");		
		
		ModelAndView mv = new ModelAndView("jsonView");
		ResultVO result = new ResultVO();
		
		Map<String, Object> data = calcSatService.getFactorValue(param);
		Map<String, Object> retVal = new HashMap<String, Object>();
		
		if(data == null) {
			result.setStatus(Constant.RESULT.FAIL);
			result.setMsg(Constant.RESULT_MSG.RESULT_NULL);
		}else {
			
			String val1 = data.get("val1") == null ? "0.0" : data.get("val1").toString();
			String val2 = data.get("val2") == null ? null : data.get("val2").toString();
			
			retVal.put("val1", Double.parseDouble(val1));
			if(val2 == null) {
				retVal.put("div", "value");
			}else {
				retVal.put("div", "range");
				retVal.put("val2", Double.parseDouble(val2));
			}
			
			result.setStatus(Constant.RESULT.SUCCESS);
			result.setData(retVal);
		}
		
		mv.addObject("result", result);
		return mv;
	}

	@RequestMapping(path="/getSatgrpList", method=RequestMethod.POST)
	public ModelAndView getSatgrpList(HttpServletRequest request, @RequestBody Map<String, Object> param) throws Exception {
		System.out.println("------------------------------------------------------------------");
		System.out.println("#CALL INFO :: [" + Arrays.toString(this.getClass().getAnnotations()) + "]");
		System.out.println("#CALL METHOD :: [" + this.getClass().getName() + "." + new Object() {}.getClass().getEnclosingMethod().getName() + "]");		
		System.out.println("------------------------------------------------------------------");		

		ModelAndView mv = new ModelAndView("jsonView");
		ResultVO result = new ResultVO();
		
		param.put(Constant.PARAM_NAME.APP_CD, appConf.getAppCd());
		
		List<Map<String, Object>> list = calcSatService.getSatgrpList(param);
		Map<String, Object> retVal = new HashMap<String, Object>();
		
		if(list == null) {
			result.setStatus(Constant.RESULT.FAIL);
			result.setMsg(Constant.RESULT_MSG.RESULT_NULL);
		}else {
			result.setStatus(Constant.RESULT.SUCCESS);
			result.setData(list);
		}
		
		mv.addObject("result", result);
		return mv;
	}
}

