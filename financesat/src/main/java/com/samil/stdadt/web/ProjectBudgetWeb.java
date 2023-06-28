package com.samil.stdadt.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.samil.stdadt.comm.service.CommCodeService;
import com.samil.stdadt.comm.util.AppHelper;
import com.samil.stdadt.comm.vo.ApplicationConfigVO;
import com.samil.stdadt.comm.vo.CommCodeVO;
import com.samil.stdadt.comm.vo.UserSessionVO;
import com.samil.stdadt.service.ProjectBudgetService;
import com.samil.stdadt.service.ProjectInfoService;
import com.samil.stdadt.service.v3.ProjectBudgetV3Service;
import com.samil.stdadt.service.v4.ProjectBudgetV4Service;
import com.samil.stdadt.util.Constant;
import com.samil.stdadt.vo.PageStatusVO;
import com.samil.stdadt.vo.ProjectBudgetSummaryV3VO;
import com.samil.stdadt.vo.ProjectBudgetSummaryV4VO;
import com.samil.stdadt.vo.ProjectBudgetSummaryVO;
import com.samil.stdadt.vo.ResultVO;

@Controller
@RequestMapping("/pages/project/budget")
public class ProjectBudgetWeb {

	@Autowired
	ProjectBudgetService bdgtService;

	@Autowired
	ProjectBudgetV3Service bdgtV3Service;
	
	//---------------------------------------
	// 20230127 추가
	//---------------------------------------
	@Autowired
	ProjectBudgetV4Service bdgtV4Service;

	@Autowired
	ApplicationConfigVO appConfig;
	
	@Autowired
	CommCodeService codeService;
	
	//---------------------------------------
	// 20230131 추가
	//---------------------------------------	
	@Autowired
	ProjectInfoService prjtInfoService;
	
	// actvList
	@ModelAttribute("actvList") 
	public List<CommCodeVO> actvList() throws Exception{
		return codeService.getCodeList(appConfig.getAppCd(), "ACTV");
	}
	
	// actvList V3 2020.05.11 추가
	@ModelAttribute("actvListV3") 
	public List<CommCodeVO> actvListV3() throws Exception{
		return codeService.getCodeList(appConfig.getAppCd(), "ACTV_V3");
	}

	@ModelAttribute("locaList") 
	public List<CommCodeVO> locaList() throws Exception{
		return codeService.getCodeList(appConfig.getAppCd(), "LOCA");
	}
	
	//---------------------------------------
	// 20230131 추가 (Budget 입력 주기 용)
	//---------------------------------------	
	@ModelAttribute(name="selOption")
	public Map<String, List<Map<String, Object>>> selOption() throws Exception{
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Constant.PARAM_NAME.APP_CD, appConfig.getAppCd());
		return prjtInfoService.getSelOption(param);
	}
	
	@RequestMapping(path="/read", method=RequestMethod.POST)
	public String budgetHourReadMode(HttpServletRequest request, @ModelAttribute PageStatusVO pageStatus, RedirectAttributes redirectAttr, Model model) throws Exception {
		String forwardPage = "";
		String prjtCd = request.getParameter("prjtCd");
		String pBudgetInputCycle = request.getParameter("budgetInputCycle");
		
		if(prjtCd == null) {
			redirectAttr.addFlashAttribute(pageStatus);
			return "redirect:/pages/project/v2/list";
		}else {
			UserSessionVO userSession= AppHelper.getSession(request);
			ResultVO result = new ResultVO();	
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put(Constant.PARAM_NAME.APP_CD, appConfig.getAppCd());
			param.put(Constant.PARAM_NAME.SESSION, userSession);
			param.put("prjtCd", prjtCd);
			
			// Form 버전
			// 20230201 Budget 입력 주기 추가
			ProjectBudgetSummaryVO frmData = bdgtV3Service.getProjectFormInfo(param);

			if("v2".equals(frmData.getFormDiv())) {
				forwardPage = "/pages/project/projectBudgetRead";
			}else if("v3".equals(frmData.getFormDiv())) {
//				forwardPage = "/pages/project/v3/projectBudgetRead";
				forwardPage = "/pages/project/v3/projectBudget";
				
			//=====================================================
			//	[시작] 20220427 남웅주  2022년도 표준감사시간 개정
			//	      20230127 남웅주  2023년도 표준감사시간 개정
			//=====================================================
			}else if("v4".equals(frmData.getFormDiv())) {
				//forwardPage = "/pages/project/v3/projectBudget";
				forwardPage = "/pages/project/v4/projectBudget";
			//=====================================================
			//	[종료] 20220427 남웅주  2022년도 표준감사시간 개정
			//		  20230127 남웅주  2023년도 표준감사시간 개정
			//=====================================================				
			}else {
				forwardPage = "/pages/project/projectBudgetRead";
			}
			
			if("v3".equals(frmData.getFormDiv())) {
				ProjectBudgetSummaryV3VO data = bdgtV3Service.getBudgetSummary(param);
				if(data == null) {
					result.setStatus(Constant.RESULT.FAIL);
					result.setMsg(Constant.RESULT_MSG.UNATUTHORIZED);
				}else {				
					data.setMode("read");
					result.setStatus(Constant.RESULT.SUCCESS);
					result.setData(data);
				}
				model.addAttribute("bdgtRoleConfig", AppHelper.convertObjectToJsonString(bdgtV3Service.selectDftBdgtRoleConfig(param)));
			
			//=====================================================
			//	[시작] 20220427 남웅주  2022년도 표준감사시간 개정
			//		  20230127 남웅주  2023년도 표준감사시간 개정
			//=====================================================
			}else if("v4".equals(frmData.getFormDiv())) {
				//-------------------------------------------------
				System.out.println(" BUDGET_INPUT_CYCLE (DB)>> " + frmData.getBudgetInputCycle());
				System.out.println(" BUDGET_INPUT_CYCLE (request.getParameter)>> " + pBudgetInputCycle);
				//-------------------------------------------------
				
				//20230127 추가
				//Budget 입력주기가 없으면 기본값 Weekly 로 한다.
				String budgetInputCycle = "Weekly";
				if(frmData.getBudgetInputCycle() != null && !"".equals(frmData.getBudgetInputCycle())) {
					budgetInputCycle = frmData.getBudgetInputCycle();
				}
				//20230209 request.getParameter 로 값이 있으면 우선적용한다.
				if(pBudgetInputCycle != null && !"".equals(pBudgetInputCycle)) {
					budgetInputCycle = pBudgetInputCycle;
				}
				
				//-------------------------------------------------
				System.out.println(" BUDGET_INPUT_CYCLE (최종) >> " + budgetInputCycle);
				//-------------------------------------------------
				
				param.put("budgetInputCycle", budgetInputCycle);
				
				ProjectBudgetSummaryV4VO data = bdgtV4Service.getBudgetSummary(param);
				if(data == null) {
					result.setStatus(Constant.RESULT.FAIL);
					result.setMsg(Constant.RESULT_MSG.UNATUTHORIZED);
				}else {	
					//Budget 입력주기
					data.setBudgetInputCycle(budgetInputCycle);
					data.setMode("read");
					result.setStatus(Constant.RESULT.SUCCESS);
					result.setData(data);
				}
				model.addAttribute("bdgtRoleConfig", AppHelper.convertObjectToJsonString(bdgtV4Service.selectDftBdgtRoleConfig(param)));
			//=====================================================
			//	[종료] 20220427 남웅주  2022년도 표준감사시간 개정
			//		  20230127 남웅주  2023년도 표준감사시간 개정
			//=====================================================			
			}else {
				ProjectBudgetSummaryVO data = bdgtService.getBudgetSummary(param);
				if(data == null) {
					result.setStatus(Constant.RESULT.FAIL);
					result.setMsg(Constant.RESULT_MSG.UNATUTHORIZED);
				}else {				
					result.setStatus(Constant.RESULT.SUCCESS);
					result.setData(data);
				}				
			}
			
			model.addAttribute("result", result);
			model.addAttribute("appConfig", appConfig);
			model.addAttribute("pageStatus", pageStatus);
			
			return forwardPage;
		}		
	}
	
	@RequestMapping(path="/edit", method=RequestMethod.POST)
	public String budgetHourEditMode(HttpServletRequest request, @ModelAttribute PageStatusVO pageStatus, RedirectAttributes redirectAttr, Model model) throws Exception {
		String prjtCd = request.getParameter("prjtCd");
		String forwardPage = "";
		String pBudgetInputCycle = request.getParameter("budgetInputCycle");
		
		if(prjtCd == null) {
			redirectAttr.addFlashAttribute(pageStatus);
			return "redirect:/pages/project/v2/list";
		}else {
			UserSessionVO userSession= AppHelper.getSession(request);
			ResultVO result = new ResultVO();	
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put(Constant.PARAM_NAME.APP_CD, appConfig.getAppCd());
			param.put(Constant.PARAM_NAME.SESSION, userSession);
			param.put("prjtCd", request.getParameter("prjtCd"));
			
			// Form 버전
			// 20230201 Budget 입력 주기 추가
			ProjectBudgetSummaryVO frmData = bdgtV3Service.getProjectFormInfo(param);

			if("v2".equals(frmData.getFormDiv())) {
				forwardPage = "/pages/project/projectBudgetEdit";
			}else if("v3".equals(frmData.getFormDiv())) {
				// forwardPage = "/pages/project/v3/projectBudgetEdit";
				forwardPage = "/pages/project/v3/projectBudget";
			//=====================================================
			//	[시작] 20220427 남웅주  2022년도 표준감사시간 개정
			//		 20230127 남웅주  2023년도 표준감사시간 개정
			//=====================================================				
			}else if("v4".equals(frmData.getFormDiv())) {
				//forwardPage = "/pages/project/v3/projectBudget";
				forwardPage = "/pages/project/v4/projectBudget";
			//=====================================================
			//	[종료] 20220427 남웅주  2022년도 표준감사시간 개정
			//		 20230127 남웅주  2023년도 표준감사시간 개정
			//=====================================================								
			}else {
				forwardPage = "/pages/project/projectBudgetEdit";
			}
			
			if("v3".equals(frmData.getFormDiv())) {
				ProjectBudgetSummaryV3VO data = bdgtV3Service.getBudgetSummary(param);
				if(data == null) {
					result.setStatus(Constant.RESULT.FAIL);
					result.setMsg(Constant.RESULT_MSG.UNATUTHORIZED);
				}else {
					data.setMode("edit");
					result.setStatus(Constant.RESULT.SUCCESS);
					result.setData(data);
				}
				model.addAttribute("bdgtRoleConfig", AppHelper.convertObjectToJsonString(bdgtV3Service.selectDftBdgtRoleConfig(param)));
			//=====================================================
			//	[시작] 20220427 남웅주  2022년도 표준감사시간 개정
			//		 20230127 남웅주  2023년도 표준감사시간 개정
			//=====================================================
			}else if("v4".equals(frmData.getFormDiv())) {
				//-------------------------------------------------
				System.out.println(" BUDGET_INPUT_CYCLE >> " + frmData.getBudgetInputCycle());
				System.out.println(" BUDGET_INPUT_CYCLE (request.getParameter)>> " + pBudgetInputCycle);
				System.out.println(" returnUrl >> " + request.getParameter("returnUrl"));
				//-------------------------------------------------
				
				//20230127 추가
				//Budget 입력주기가 없으면 기본값 Weekly 로 한다.
				String budgetInputCycle = "Weekly";
				if(frmData.getBudgetInputCycle() != null && !"".equals(frmData.getBudgetInputCycle())) {
					budgetInputCycle = frmData.getBudgetInputCycle();
				}
				//20230209 request.getParameter 로 값이 있으면 우선적용한다.
				if(pBudgetInputCycle != null && !"".equals(pBudgetInputCycle)) {
					budgetInputCycle = pBudgetInputCycle;
				}
				
				//-------------------------------------------------
				System.out.println(" BUDGET_INPUT_CYCLE (최종) >> " + budgetInputCycle);
				//-------------------------------------------------
				
				param.put("budgetInputCycle", budgetInputCycle);
				
				ProjectBudgetSummaryV4VO data = bdgtV4Service.getBudgetSummary(param);
				if(data == null) {
					result.setStatus(Constant.RESULT.FAIL);
					result.setMsg(Constant.RESULT_MSG.UNATUTHORIZED);
				}else {
					//결재상세페이지에서 들어온경우 되돌아갈 페이지 주소...
					data.setReturnUrl(request.getParameter("returnUrl"));   
					//Budget 입력주기
					data.setBudgetInputCycle(budgetInputCycle);
					data.setMode("edit");
					result.setStatus(Constant.RESULT.SUCCESS);
					result.setData(data);
				}
				model.addAttribute("bdgtRoleConfig", AppHelper.convertObjectToJsonString(bdgtV4Service.selectDftBdgtRoleConfig(param)));
			//=====================================================
			//	[종료] 20220427 남웅주  2022년도 표준감사시간 개정
			//		 20230127 남웅주  2023년도 표준감사시간 개정
			//=====================================================			
			}else {
				ProjectBudgetSummaryVO data = bdgtService.getBudgetSummary(param);
				if(data == null) {
					result.setStatus(Constant.RESULT.FAIL);
					result.setMsg(Constant.RESULT_MSG.UNATUTHORIZED);
				}else {
					result.setStatus(Constant.RESULT.SUCCESS);
					result.setData(data);
				}				
			}
						
			model.addAttribute("result", result);
			model.addAttribute("appConfig", appConfig);
			model.addAttribute("pageStatus", pageStatus);
			
			return forwardPage;
			
		}
		
	}
	
	
	
	/*@RequestMapping(path="/read", method=RequestMethod.POST)
	public String budgetHourReadMode(HttpServletRequest request, @ModelAttribute PageStatusVO pageStatus, RedirectAttributes redirectAttr, Model model) throws Exception {
		String forwardPage = "";
		String prjtCd = request.getParameter("prjtCd");
		
		if(prjtCd == null) {
			redirectAttr.addFlashAttribute(pageStatus);
			return "redirect:/pages/project/v2/list";
		}else {
			UserSessionVO userSession= AppHelper.getSession(request);
			ResultVO result = new ResultVO();	
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put(Constant.PARAM_NAME.APP_CD, appConfig.getAppCd());
			param.put(Constant.PARAM_NAME.SESSION, userSession);
			param.put("prjtCd", prjtCd);
			
			// Form 버전
			ProjectBudgetSummaryVO frmData = bdgtV3Service.getProjectFormInfo(param);

			if("v2".equals(frmData.getFormDiv())) {
				forwardPage = "/pages/project/projectBudgetRead";
			}else if("v3".equals(frmData.getFormDiv())) {
				forwardPage = "/pages/project/v3/projectBudgetRead";
			}else {
				forwardPage = "/pages/project/projectBudgetRead";
			}
			
			if("v3".equals(frmData.getFormDiv())) {
				ProjectBudgetSummaryV3VO data = bdgtV3Service.getBudgetSummary(param);
				if(data == null) {
					result.setStatus(Constant.RESULT.FAIL);
					result.setMsg(Constant.RESULT_MSG.UNATUTHORIZED);
				}else {				
					result.setStatus(Constant.RESULT.SUCCESS);
					result.setData(data);
				}
			}else {
				ProjectBudgetSummaryVO data = bdgtService.getBudgetSummary(param);
				if(data == null) {
					result.setStatus(Constant.RESULT.FAIL);
					result.setMsg(Constant.RESULT_MSG.UNATUTHORIZED);
				}else {				
					result.setStatus(Constant.RESULT.SUCCESS);
					result.setData(data);
				}				
			}
			
			model.addAttribute("result", result);
			model.addAttribute("appConfig", appConfig);
			model.addAttribute("pageStatus", pageStatus);
			
			return forwardPage;
		}		
	}
	
	@RequestMapping(path="/edit", method=RequestMethod.POST)
	public String budgetHourEditMode(HttpServletRequest request, @ModelAttribute PageStatusVO pageStatus, RedirectAttributes redirectAttr, Model model) throws Exception {
		String prjtCd = request.getParameter("prjtCd");
		String forwardPage = "";
		
		if(prjtCd == null) {
			redirectAttr.addFlashAttribute(pageStatus);
			return "redirect:/pages/project/v2/list";
		}else {
			UserSessionVO userSession= AppHelper.getSession(request);
			ResultVO result = new ResultVO();	
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put(Constant.PARAM_NAME.APP_CD, appConfig.getAppCd());
			param.put(Constant.PARAM_NAME.SESSION, userSession);
			param.put("prjtCd", request.getParameter("prjtCd"));
			
			// Form 버전
			ProjectBudgetSummaryVO frmData = bdgtV3Service.getProjectFormInfo(param);

			if("v2".equals(frmData.getFormDiv())) {
				forwardPage = "/pages/project/projectBudgetEdit";
			}else if("v3".equals(frmData.getFormDiv())) {
				forwardPage = "/pages/project/v3/projectBudgetEdit";
			}else {
				forwardPage = "/pages/project/projectBudgetEdit";
			}
			
			if("v3".equals(frmData.getFormDiv())) {
				ProjectBudgetSummaryV3VO data = bdgtV3Service.getBudgetSummary(param);
				if(data == null) {
					result.setStatus(Constant.RESULT.FAIL);
					result.setMsg(Constant.RESULT_MSG.UNATUTHORIZED);
				}else {				
					result.setStatus(Constant.RESULT.SUCCESS);
					result.setData(data);
				}
				
				model.addAttribute("bdgtRoleConfig", AppHelper.convertObjectToJsonString(bdgtV3Service.selectDftBdgtRoleConfig(param)));
			}else {
				ProjectBudgetSummaryVO data = bdgtService.getBudgetSummary(param);
				if(data == null) {
					result.setStatus(Constant.RESULT.FAIL);
					result.setMsg(Constant.RESULT_MSG.UNATUTHORIZED);
				}else {				
					result.setStatus(Constant.RESULT.SUCCESS);
					result.setData(data);
				}				
			}
						
			model.addAttribute("result", result);
			model.addAttribute("appConfig", appConfig);
			model.addAttribute("pageStatus", pageStatus);
			
			return forwardPage;
			
		}
		
	}*/
}
