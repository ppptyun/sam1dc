package com.samil.stdadt.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.samil.stdadt.comm.util.AppHelper;
import com.samil.stdadt.comm.vo.ApplicationConfigVO;
import com.samil.stdadt.comm.vo.UserSessionVO;
import com.samil.stdadt.service.ApprovalService;
import com.samil.stdadt.service.ProjectInfoService;
import com.samil.stdadt.util.Constant;
import com.samil.stdadt.vo.PageStatusVO;
import com.samil.stdadt.vo.ProjectBudgetSummaryVO;
import com.samil.stdadt.vo.ResultVO;

@Controller
@RequestMapping("/pages/approval")
public class ApprovalWeb {

	@Autowired
	ApprovalService aprvService;
	
	@Autowired
	ApplicationConfigVO appConfig;
	
	@Autowired
	ProjectInfoService prjtInfoService;
	
	
	@ModelAttribute(name="tooltip")
	public Map<String, Object> tooltip() throws Exception{
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("appCd", appConfig.getAppCd());
		return prjtInfoService.getTooltip(param);
	}
	
	@RequestMapping("/list")
	public String approvalList(@ModelAttribute PageStatusVO pageStatus, Model model) {
		model.addAttribute("pageStatus", pageStatus);
		return "/pages/approval/aprvList";
	}
	
	@RequestMapping("/list/{stat}")
	public String getProjectList(@ModelAttribute PageStatusVO pageStatus, Model model, @PathVariable("stat") String stat) throws Exception {
		model.addAttribute("STAT", stat);
		model.addAttribute("pageStatus", pageStatus);
		return "/pages/approval/aprvList";
	}
	
	// Budget 화면
	@RequestMapping(path="/details", method=RequestMethod.POST)
	public String approvalDetails(HttpServletRequest request, @ModelAttribute PageStatusVO pageStatus, RedirectAttributes redirectAttr, Model model) throws Exception {
		String prjtCd = request.getParameter("prjtCd");
		
		if(prjtCd == null) {
			redirectAttr.addFlashAttribute(pageStatus);
			return "redirect:/pages/approval/list";
		}else {
			UserSessionVO userSession= AppHelper.getSession(request);
			ResultVO result = new ResultVO();	
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("appCd", appConfig.getAppCd());
			param.put("prjtCd", prjtCd);
			param.put(Constant.PARAM_NAME.SESSION, userSession);
			
			Map<String, Object> auth = prjtInfoService.getPrjtMetaInfo(param);
			
			Object data;
			if(auth.get("formDiv").equals("v3")) {
				data = aprvService.getApprovalDetailsV3(param);
			}else if(auth.get("formDiv").equals("v4")) {	// 20230216 추가
				//-------------------------------------------------
				System.out.println(" BUDGET_INPUT_CYCLE >> " + auth.get("budgetInputCycle"));
				//-------------------------------------------------
				//Budget 입력주기가 없으면 기본값 Weekly 로 한다.
				String budgetInputCycle = "Weekly";
				if(auth.get("budgetInputCycle") != null && !"".equals(auth.get("budgetInputCycle"))) {
					budgetInputCycle = (String) auth.get("budgetInputCycle");
				}
				param.put("budgetInputCycle", budgetInputCycle);
							
				data = aprvService.getApprovalDetailsV4(param);				
			} else { // v1, v2는 동일
				data = aprvService.getApprovalDetails(param);
			}
			
			if(data == null) {
				result.setStatus(Constant.RESULT.FAIL);
				result.setMsg(Constant.RESULT_MSG.UNATUTHORIZED);
			}else {				
				result.setStatus(Constant.RESULT.SUCCESS);
				result.setData(data);
			}
			
			model.addAttribute("result", result);
			model.addAttribute("pageStatus", pageStatus);
						
			if(auth.get("formDiv").equals("v3")) {
				return "/pages/approval/v3/approvalBudgetRead";
			}else if(auth.get("formDiv").equals("v4")) { // 20230216 추가
					return "/pages/approval/v4/approvalBudgetRead";
			} else { // v1, v2는 동일
				return "/pages/approval/approvalDetails";
			}
		}		
	}
	
	// 프로젝트 정보화면 (표준감사시간 화면)
	@RequestMapping(path="/info", method=RequestMethod.POST)
	public String approvalInfo(HttpServletRequest request, @ModelAttribute PageStatusVO pageStatus, RedirectAttributes redirectAttr, Model model) throws Exception {
		String prjtCd = request.getParameter("prjtCd");
		UserSessionVO userSession= AppHelper.getSession(request);
		ResultVO result = new ResultVO();	
		
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put(Constant.PARAM_NAME.SESSION, userSession);
		param.put("prjtCd", prjtCd);
		
		result.setStatus(Constant.RESULT.SUCCESS);
		
		Map<String, Object> auth = prjtInfoService.getPrjtMetaInfo(param);
		model.addAttribute("auth", auth);
		model.addAttribute("prjtCd", prjtCd);
		model.addAttribute("pageStatus", pageStatus);
		
		if(auth.get("formDiv").equals("v1")) {
			ProjectBudgetSummaryVO data = aprvService.getApprovalDetails(param);
			result.setData(data);
			model.addAttribute("result", result);
			return "/pages/approval/approvalProjectRead";
		}else if(auth.get("formDiv").equals("v2")) {
			model.addAttribute("mode", "read");
			return "/pages/approval/v2/approvalProject";
		}else if(auth.get("formDiv").equals("v3")) {
			model.addAttribute("mode", "read");
			return "/pages/approval/v3/approvalProjectInfo";
		}else if(auth.get("formDiv").equals("v4")) { // 20230216 추가
			model.addAttribute("mode", "read");
			return "/pages/approval/v3/approvalProjectInfo";
		}else {
			return "/";
		}
	}
}
