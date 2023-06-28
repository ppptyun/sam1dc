package com.samil.stdadt.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.text.StringEscapeUtils;
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
import com.samil.stdadt.service.ApprovalService;
import com.samil.stdadt.service.HistoryService;
import com.samil.stdadt.service.ProjectInfoService;
import com.samil.stdadt.util.CalculateSat;
import com.samil.stdadt.util.Constant;
import com.samil.stdadt.util.Helper;
import com.samil.stdadt.vo.ApprovalListVO;
import com.samil.stdadt.vo.ProjectInfoVO;
import com.samil.stdadt.vo.ResultVO;

@Controller
@RequestMapping("/json/approval")
public class ApprovalController {
	
	@Autowired
	CommCodeService commService;
	
	@Autowired
	ApprovalService aprvService;
	
	@Autowired
	ProjectInfoService prjtInfoService;
	
	@Autowired
	HistoryService histService;
	
	@Autowired
	ApplicationConfigVO appConfig;
	
	//1. 프로젝트 목록 가져오기
	@RequestMapping("/list")
	public ModelAndView getProjectList(HttpServletRequest request, @RequestBody Map<String, Object> param) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		UserSessionVO userSession= AppHelper.getSession(request);
		
		param.put(Constant.PARAM_NAME.APP_CD, appConfig.getAppCd());
		param.put(Constant.PARAM_NAME.SESSION, userSession);
		
		List<ApprovalListVO> prjtList = aprvService.getApprovalList(param);
		ResultVO result = new ResultVO();
		
		result.setStatus(Constant.RESULT.SUCCESS);
		result.setData(prjtList);
		mv.addObject("result", result);
		return mv;
	}
	
	@RequestMapping(path="/info", method=RequestMethod.POST)
	public ModelAndView getProject(HttpServletRequest request, @RequestBody Map<String, Object> param) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		ResultVO result = new ResultVO();
		UserSessionVO userSession= AppHelper.getSession(request);
		
		param.put(Constant.PARAM_NAME.APP_CD, appConfig.getAppCd());
		param.put(Constant.PARAM_NAME.SESSION, userSession);
		
		ProjectInfoVO prjt = prjtInfoService.getProjectInfo(param);
		
		if(prjt == null) {
			result.setStatus(Constant.RESULT.FAIL);
			result.setMsg(Constant.RESULT_MSG.UNATUTHORIZED);
		}else {
			
			if(prjt.getSatTrgtYn().equals("Y")) {
				ResultVO calculateSat = calculateSat(prjt);
				Map<String, Object> sat = (Map<String, Object>) calculateSat.getData();
				for(String key : sat.keySet()) {
					switch(key) {
					case "intrAdtYn":
						prjt.setIntrAdtYn(String.valueOf(sat.get(key)));
						break;	
					case "intrAdtYnNm":
						prjt.setIntrAdtYnNm(String.valueOf(sat.get(key)));
						break;
					case "intrAdtTm":
						prjt.setIntrAdtTm(Double.parseDouble(String.valueOf(sat.get(key))));
						break;
					case "factorVal":
						prjt.setFactorVal(Double.parseDouble(String.valueOf(sat.get(key))));
						break;
					case "calAdtTm":
						prjt.setCalAdtTm(Double.parseDouble(String.valueOf(sat.get(key))));
						break;
					case "calSat":
						prjt.setCalSat(Double.parseDouble(String.valueOf(sat.get(key))));
						break;
					case "baseWkmnsp":
						prjt.setBaseWkmnsp(Double.parseDouble(String.valueOf(sat.get(key))));
						break;
					}
				}
			}
			
			result.setStatus(Constant.RESULT.SUCCESS);
			result.setData(prjt);	
		}
		
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
		calcSat.setMaxRatio(Double.parseDouble(String.valueOf(satgrpInfo.get("maxRatio"))));
		calcSat.setYearRate(yearRate);
		calcSat.setFactorVal(factors);
		result = calcSat.calculate();
		((Map<String, Object>) result.getData()).put("intrAdtYnNm", commService.getCode(appConfig.getAppCd(), "INTR_ADT_YN", prjt.getIntrAdtYn()).getNm());
		
		return result;
	}
	
	@RequestMapping(path="/approve", method=RequestMethod.POST)
	public ModelAndView approveProject(HttpServletRequest request, @RequestBody Map<String, Object> param) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		UserSessionVO userSession= AppHelper.getSession(request);
		SimpleDateFormat dateNow = new SimpleDateFormat("yyyyMMddHHmm");
		ResultVO result = new ResultVO();
		
		URL urlparser = new URL(request.getRequestURL().toString());
		String url = urlparser.getProtocol() + "://" + urlparser.getHost() + request.getContextPath() + "/pages/approval/list/CO";
		String mailID = appConfig.getAppCd() + dateNow.format(new Date()) + param.get("prjtCd") + "CO";
		
		// 메일 제목 길이 초과 방지를 위함.		
		Map<String, Object> prjtInfo = prjtInfoService.getPrjtInfoSimple(param);
		String subject = Helper.getMailSubject("[표준감사시간관리] ", (String) prjtInfo.get("prjtpnm"), (String) prjtInfo.get("shrtnm"), " - 승인 완료 안내");
		
		StringBuffer sb = new StringBuffer();
		sb.append("<br />");
		sb.append("<div style=\"font-size: 11pt\">표준감사시간관리에 승인 요청하신 <b>“%s”</b> 프로젝트가 승인되었습니다</div><br />");
		sb.append("<div style=\"font-size: 11pt\">아래 Link를 눌러 승인된 내용을 확인하실 수 있습니다.</div><br />");
		sb.append("<ul style=\"font-size: 11pt;\">");
		sb.append("<li style=\"font-size: 11pt;padding-bottom:20px;\"> 승인 내역 확인 : <a href=\"%s\">LINK</a></li>");
		sb.append("</ul><br />");
		sb.append("<div style=\"font-size: 11pt\">감사합니다.</div>");
		String contents = String.format(sb.toString(), param.get("prjtNm"), url);
		
		param.put(Constant.PARAM_NAME.SESSION, userSession);
		param.put("appCd", appConfig.getAppCd());
		param.put("mailID", mailID);
		param.put("subject", subject);
		param.put("contents", contents);
				
		if(aprvService.approve(param) == 0) {
			result.setStatus(Constant.RESULT.FAIL);
			result.setMsg(Constant.RESULT_MSG.SAVE_ERROR);
		}else {
			result.setStatus(Constant.RESULT.SUCCESS);
		}
		mv.addObject("result", result);
		return mv;
	}
	
	@RequestMapping(path="/reject", method=RequestMethod.POST)
	public ModelAndView rejectProject(HttpServletRequest request, @RequestBody Map<String, Object> param) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		UserSessionVO userSession= AppHelper.getSession(request);
		SimpleDateFormat dateNow = new SimpleDateFormat("yyyyMMddHHmm");
		ResultVO result = new ResultVO();
		//String rejectReason = StringEscapeUtils.escapeHtml4(param.get("rejectReason").toString()).replaceAll("(\r\n|\r|\n|\n\r)", "<br/>");
		String rejectReason = StringEscapeUtils.escapeHtml4(param.get("rejectReason").toString());
		
		URL urlparser = new URL(request.getRequestURL().toString());
		String url = urlparser.getProtocol() + "://" + urlparser.getHost() + request.getContextPath() + "/pages/approval/list/RJ";
		String mailID = appConfig.getAppCd() + dateNow.format(new Date()) + param.get("prjtCd") + "RJ";
		
		// 메일 제목 길이 초과 방지를 위함.		
		Map<String, Object> prjtInfo = prjtInfoService.getPrjtInfoSimple(param);
		String subject = Helper.getMailSubject("[표준감사시간관리] ", (String) prjtInfo.get("prjtpnm"), (String) prjtInfo.get("shrtnm"), " - 반려 안내");
		StringBuffer sb = new StringBuffer();
		sb.append("<br />");
		sb.append("<div style=\"font-size: 11pt\">표준감사시간관리에 승인 요청하신 <b>“%s”</b> 프로젝트가 반려되었습니다.</div><br />");
		sb.append("<div style=\"font-size: 11pt\">아래 Link를 눌러 반려 된 내용 확인 후 재상신 하시기 바랍니다.</div><br />");
		sb.append("<ul style=\"font-size: 11pt;\">");
		sb.append("<li style=\"font-size: 11pt;padding-bottom:20px;\"> 반려의견 : %s</li>");
		sb.append("<li style=\"font-size: 11pt;padding-bottom:20px;\"> 반려 내역 확인 : <a href=\"%s\">LINK</a></li>");
		sb.append("</ul><br />");
		sb.append("<div style=\"font-size: 11pt\">감사합니다.</div>");
		String contents = String.format(sb.toString(), param.get("prjtNm"), rejectReason, url);
		
		param.put(Constant.PARAM_NAME.SESSION, userSession);
		param.put("mailID", mailID);
		param.put("subject", subject);
		param.put("contents", contents);
		param.put("rejectReason", rejectReason);
				
		if(aprvService.reject(param) == 0) {
			result.setStatus(Constant.RESULT.FAIL);
			result.setMsg(Constant.RESULT_MSG.SAVE_ERROR);
		}else {
			result.setStatus(Constant.RESULT.SUCCESS);
		}
		mv.addObject("result", result);
		return mv;
	}
	
}

