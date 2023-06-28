package com.samil.stdadt.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.samil.stdadt.comm.service.CommCodeService;
import com.samil.stdadt.comm.util.AppHelper;
import com.samil.stdadt.comm.vo.ApplicationConfigVO;
import com.samil.stdadt.comm.vo.CommCodeVO;
import com.samil.stdadt.comm.vo.UserSessionVO;
import com.samil.stdadt.service.HistoryService;
import com.samil.stdadt.service.PopupService;
import com.samil.stdadt.service.ProjectBudgetService;
import com.samil.stdadt.util.Constant;
import com.samil.stdadt.vo.ProfitabilityV3VO;
import com.samil.stdadt.vo.ProfitabilityVO;
import com.samil.stdadt.vo.ProjectBudgetSummaryVO;
import com.samil.stdadt.vo.ProjectVersionVO;
import com.samil.stdadt.vo.ResultVO;
import com.samil.stdadt.vo.WeekVO;

@Controller
@RequestMapping("/popup")
public class PopupWeb {
	@Autowired
	PopupService popService;
	
	@Autowired 
	HistoryService histService;
	
	@Autowired
	ApplicationConfigVO appConfig;
	
	@Autowired
	ProjectBudgetService bdgtService;
	
	@Autowired
	CommCodeService codeService;
	
	@RequestMapping(path="/org", method=RequestMethod.POST)
	public String getPopupOrg(@RequestBody Map<String, Object> param, Model model) {
		model.addAttribute(Constant.PARAM_NAME.POPUP_INFO, param);
		return "/popup/EmployeePopup";
	}
	
	@RequestMapping(path="/project", method=RequestMethod.POST)
	public String getProject(@RequestBody Map<String, Object> param, Model model) {
		model.addAttribute(Constant.PARAM_NAME.POPUP_INFO, param);
		return "/popup/SearchProjectPopupV1";
	}
	
	@RequestMapping(path="/project-v2", method=RequestMethod.POST)
	public String getProjectV2(@RequestBody Map<String, Object> param, Model model) {
		model.addAttribute(Constant.PARAM_NAME.POPUP_INFO, param);
		return "/popup/SearchProjectPopupV2";
	}

	@RequestMapping(path="/project-v3", method=RequestMethod.POST)
	public String getProjectV3(@RequestBody Map<String, Object> param, Model model) {
		model.addAttribute(Constant.PARAM_NAME.POPUP_INFO, param);
		return "/popup/SearchProjectPopupV3";
	}
	
	//=====================================================
	//	[시작] 20220210 남웅주  2022년도 표준감사시간 개정
	//=====================================================
	@RequestMapping(path="/project-v4", method=RequestMethod.POST)
	public String getProjectV4(@RequestBody Map<String, Object> param, Model model) {
		model.addAttribute(Constant.PARAM_NAME.POPUP_INFO, param);
		return "/popup/SearchProjectPopupV4";
	}	
	//=====================================================
	//	[종료] 20220210 남웅주  2022년도 표준감사시간 개정
	//=====================================================
		
	@ModelAttribute("locaList") 
	public List<CommCodeVO> locaList() throws Exception{
		return codeService.getCodeList(appConfig.getAppCd(), "LOCA");
	}
	
	@RequestMapping(path="/profitability", method=RequestMethod.POST)
	public String getProfitability(HttpServletRequest request, @RequestBody Map<String, Object> param, Model model) throws Exception {
		ResultVO result = new ResultVO();

		UserSessionVO userSession= AppHelper.getSession(request);
		param.put(Constant.PARAM_NAME.SESSION, userSession);
		
		ProfitabilityVO profit = popService.getProfit(param);
		
		if(profit == null) {
			result.setStatus(Constant.RESULT.UNAUTHORIZED);
			result.setMsg(Constant.RESULT_MSG.UNATUTHORIZED);
		}else {
			result.setStatus(Constant.RESULT.SUCCESS);
			result.setData(profit);	
		}
		
		model.addAttribute(Constant.PARAM_NAME.RESULT, result);
		model.addAttribute(Constant.PARAM_NAME.POPUP_INFO, param);
		return "/popup/ProfitabilityPopup";
	}
	
	@RequestMapping(path="/profitabilityV3", method=RequestMethod.POST)
	public String getProfitabilityV3(HttpServletRequest request, @RequestBody Map<String, Object> param, Model model) throws Exception {
		ResultVO result = new ResultVO();

		UserSessionVO userSession= AppHelper.getSession(request);
		param.put(Constant.PARAM_NAME.SESSION, userSession);
		
		ProfitabilityV3VO profit = popService.getProfitV3(param);
		
		if(profit == null) {
			result.setStatus(Constant.RESULT.UNAUTHORIZED);
			result.setMsg(Constant.RESULT_MSG.UNATUTHORIZED);
		}else {
			result.setStatus(Constant.RESULT.SUCCESS);
			result.setData(profit);	
		}
		
		model.addAttribute(Constant.PARAM_NAME.RESULT, result);
		model.addAttribute(Constant.PARAM_NAME.POPUP_INFO, param);
		return "/popup/ProfitabilityPopupV3";
	}
	
	@RequestMapping(path="/conflict", method=RequestMethod.POST)
	public String getConflict(HttpServletRequest request, @RequestBody Map<String, Object> param, Model model) throws Exception {
		model.addAttribute(Constant.PARAM_NAME.POPUP_INFO, param);
		return "/popup/ConflictPopup";
	}
	
	@RequestMapping(path="/rjopinion", method=RequestMethod.POST)
	public String submitRejectOpinion(HttpServletRequest request, @RequestBody Map<String, Object> param, Model model) throws Exception {
		model.addAttribute(Constant.PARAM_NAME.POPUP_INFO, param);
		return "/popup/SubmitReject_Pop";
	}
	
	@RequestMapping(path="/rjopinionread", method=RequestMethod.POST)
	public String readRejectOpinion(HttpServletRequest request, @RequestBody Map<String, Object> param, Model model) throws Exception {
		ResultVO result = new ResultVO();

		UserSessionVO userSession= AppHelper.getSession(request);
		param.put(Constant.PARAM_NAME.SESSION, userSession);
		
		String rejectReason = popService.rejectReasonRead(param);
		if(rejectReason != null && !rejectReason.equals(""))
			rejectReason = rejectReason.toString().replaceAll("(\r\n|\r|\n|\n\r)", "<br/>").replaceAll(" ", "&nbsp;");
				
		if(rejectReason == null) {
			result.setStatus(Constant.RESULT.UNAUTHORIZED);
			result.setMsg(Constant.RESULT_MSG.UNATUTHORIZED);
		}else {
			result.setStatus(Constant.RESULT.SUCCESS);				
		}
		
		model.addAttribute("rejectReason", rejectReason);
		model.addAttribute(Constant.PARAM_NAME.RESULT, result);
		model.addAttribute(Constant.PARAM_NAME.POPUP_INFO, param);
		return "/popup/ReasonReject_Pop";
	}
	
	
	@RequestMapping(path="/history", method=RequestMethod.POST)
	public String history(@RequestBody Map<String, Object> param, Model model) throws Exception{
		ResultVO result = new ResultVO();
		ProjectBudgetSummaryVO data;
		
		String prjtCd = (String) param.get("prjtCd");
		
		ProjectVersionVO paramVo = new ProjectVersionVO();
		paramVo.setAppCd(appConfig.getAppCd());
		paramVo.setPrjtCd(prjtCd);
		Map<String, Object> version = histService.getVersionAprvComplete(paramVo);
		
		if(version == null) {
			result.setStatus(Constant.RESULT.FAIL);
			result.setMsg(Constant.RESULT_MSG.RESULT_NULL);
		}else {
			paramVo.setVersion(Long.parseLong(version.get("version").toString()));
			data = histService.getProjectBudgetSummaryHist(paramVo);
			if(data == null) {
				result.setStatus(Constant.RESULT.FAIL);
				result.setMsg(Constant.RESULT_MSG.RESULT_NULL);	
			}else {
				result.setStatus(Constant.RESULT.SUCCESS);
				result.setData(data);	
			}
		}
		
		model.addAttribute(Constant.PARAM_NAME.RESULT, result);
		model.addAttribute(Constant.PARAM_NAME.POPUP_INFO, param);
		return "/popup/HistoryPopup";
	}
	
	@RequestMapping(path="/fileUploader", method=RequestMethod.POST)
	public String fileUploader(@RequestBody Map<String, Object> param, Model model) throws Exception{
		model.addAttribute(Constant.PARAM_NAME.POPUP_INFO, param);
		return "/popup/FileUploaderPopup";
	}
	
	// 2020.05.11 v3버전 추가
	@RequestMapping(path="/v3/fileUploader", method=RequestMethod.POST)
	public String fileUploaderV3(@RequestBody Map<String, Object> param, Model model) throws Exception{
		model.addAttribute(Constant.PARAM_NAME.POPUP_INFO, param);
		return "/popup/FileUploaderPopupV3";
	}

	//=====================================================
	//	[시작]  20230210 남웅주  2023년도 표준감사시간 개정
	//=====================================================
	// 2023.02.10 v4버전 추가
	@RequestMapping(path="/v4/fileUploader", method=RequestMethod.POST)
	public String fileUploaderV4(@RequestBody Map<String, Object> param, Model model) throws Exception{
		model.addAttribute(Constant.PARAM_NAME.POPUP_INFO, param);
		return "/popup/FileUploaderPopupV4";
	}
	//=====================================================
	//	[종료]  20230210 남웅주  2023년도 표준감사시간 개정
	//=====================================================
	
	@RequestMapping(path="/retain", method=RequestMethod.POST)
	public String retainPopup(@RequestBody Map<String, Object> param, Model model) throws Exception{
		ResultVO result = new ResultVO();
		List<WeekVO> weeks = bdgtService.getBudgetWeek(param);
		
		
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		
		result.setStatus(Constant.RESULT.SUCCESS);
		String selected = "";
		
		for(int i=0; i< weeks.size(); i++) {
			if(weeks.get(i).getStartDate().compareTo(format.format(now)) >= 0){
				if(i==0) {
					selected = weeks.get(0).getStartDate();
				}else {					
					selected = weeks.get(i-1).getStartDate();
				}
				break;
			}
		}
		if(selected.equals("")) selected = weeks.get(0).getStartDate();
		
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("list", weeks);
		data.put("selected", selected);
		result.setData(data);
		
		model.addAttribute(Constant.PARAM_NAME.RESULT, result);
		model.addAttribute(Constant.PARAM_NAME.POPUP_INFO, param);
		return "/popup/RetainPopup";
	}
	

	
	@RequestMapping(path="/download-by-auth", method=RequestMethod.POST)
	public String downloadByAuthV1(@RequestBody Map<String, Object> param, Model model) throws Exception{
		model.addAttribute(Constant.PARAM_NAME.POPUP_INFO, param);
		return "/popup/ExcelDownloadByAuthV1";
	}
	
	@RequestMapping(path="/download-by-auth-v2", method=RequestMethod.POST)
	public String downloadByAuthV2(@RequestBody Map<String, Object> param, Model model) throws Exception{
		model.addAttribute(Constant.PARAM_NAME.POPUP_INFO, param);
		return "/popup/ExcelDownloadByAuthV2";
	}
	
	@RequestMapping(path="/download-by-auth-v3", method=RequestMethod.POST)
	public String downloadByAuthV3(@RequestBody Map<String, Object> param, Model model) throws Exception{
		model.addAttribute(Constant.PARAM_NAME.POPUP_INFO, param);
		return "/popup/ExcelDownloadByAuthV3";
	}
	
	@RequestMapping(path="/auditorList", method=RequestMethod.POST)
	public String popupAuditorList(@RequestBody Map<String, Object> param, Model model) throws Exception{
		model.addAttribute(Constant.PARAM_NAME.POPUP_INFO, param);
		return "/popup/AuditorInfoPopup";
	}
	
	
	@RequestMapping(path="/simplewkmnsp", method=RequestMethod.POST)
	public String popupSimpleWkmnspPopup(@RequestBody Map<String, Object> param, Model model) throws Exception{
		model.addAttribute(Constant.PARAM_NAME.POPUP_INFO, param);
		return "/popup/SimpleWkmnspPopup";
	}
	
	
	@RequestMapping(path="/satcalc", method=RequestMethod.POST)
	public String popupSatCalcPopup(@RequestBody Map<String, Object> param, Model model) throws Exception{
		model.addAttribute(Constant.PARAM_NAME.POPUP_INFO, param);
		return "/popup/SatCalcListPopup";
	}
	
	@RequestMapping(path="/reasonModify", method=RequestMethod.POST) 
	public String popupReasonModify(@RequestBody Map<String, Object> param, Model model) throws Exception {
		ResultVO result = new ResultVO();
		
		List<CommCodeVO> reasonList = codeService.getCodeList(appConfig.getAppCd(), "APRV_CHG_REASON");
		
		if(reasonList.size() > 0) {
			result.setStatus(Constant.RESULT.SUCCESS);
			result.setData(reasonList);
		}else{
			result.setStatus(Constant.RESULT.FAIL);
			result.setMsg("변경 사유 목록이 정상적으로 Load되지 않았습니다. 다시 시도해 보시기 바랍니다.");
		}
		
		model.addAttribute(Constant.PARAM_NAME.RESULT, result);
		model.addAttribute(Constant.PARAM_NAME.POPUP_INFO, param);
		return "/popup/ReasonModify_Pop";
	}
}
