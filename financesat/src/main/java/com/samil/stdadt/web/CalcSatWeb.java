package com.samil.stdadt.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.samil.stdadt.comm.util.AppHelper;
import com.samil.stdadt.comm.vo.ApplicationConfigVO;
import com.samil.stdadt.comm.vo.UserSessionVO;
import com.samil.stdadt.service.CalcSatService;
import com.samil.stdadt.service.ProjectInfoService;
import com.samil.stdadt.util.Constant;
import com.samil.stdadt.vo.PageStatusVO;

//=====================================================================
// 20220224 남웅주  v4 와 공유하기 위해서 /v2 를  하위  각각의 @RequestMapping 으로  이전함. 
//=====================================================================
@Controller
//@RequestMapping(path="/pages/satcalc/v2")
@RequestMapping(path="/pages/satcalc")
public class CalcSatWeb {
	
	
	@Autowired
	ApplicationConfigVO appConf;
	
	@Autowired
	ProjectInfoService prjtInfoService;
	
	@Autowired
	CalcSatService calcSatService;
	
	@ModelAttribute(name="selOption")
	public Map<String, List<Map<String, Object>>> selOption() throws Exception{
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Constant.PARAM_NAME.APP_CD, appConf.getAppCd());
		return prjtInfoService.getSelOption(param);
	}
	
	@ModelAttribute(name="tooltip")
	public Map<String, Object> tooltip() throws Exception{
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Constant.PARAM_NAME.APP_CD, appConf.getAppCd());
		return prjtInfoService.getTooltip(param);
	}
	
	@ModelAttribute(name="wkmnsp")
	public Map<String, Object> wkmnsp() throws Exception{
		return prjtInfoService.getWkmnsp(appConf.getAppCd());
	}
	
	@ModelAttribute(name="profileId")
	public int profileId() throws Exception{
		return prjtInfoService.getProfileId();
	}
	
	@ModelAttribute(name="yearListV2")
	public List<String> getYearListV2() throws Exception{
		return calcSatService.getYear();
	}
	
	@RequestMapping(path="/v2/list", method= {RequestMethod.POST, RequestMethod.GET} )
	public String projectList(@ModelAttribute PageStatusVO pageStatus, Model model) {
		System.out.println("------------------------------------------------------------------");
		System.out.println("#CALL INFO :: [" + Arrays.toString(this.getClass().getAnnotations()) + "]");
		System.out.println("#CALL METHOD :: [" + this.getClass().getName() + "." + new Object() {}.getClass().getEnclosingMethod().getName() + "]");		
		System.out.println("------------------------------------------------------------------");		
		
		model.addAttribute("pageStatus", pageStatus);
		return "/pages/satCalc/v2/satCalcList";
	}
	
	@RequestMapping(path="/v2/draftlist", method= {RequestMethod.POST, RequestMethod.GET} )
	public String projectDraftList(@ModelAttribute PageStatusVO pageStatus, Model model) {
		System.out.println("------------------------------------------------------------------");
		System.out.println("#CALL INFO :: [" + Arrays.toString(this.getClass().getAnnotations()) + "]");
		System.out.println("#CALL METHOD :: [" + this.getClass().getName() + "." + new Object() {}.getClass().getEnclosingMethod().getName() + "]");		
		System.out.println("------------------------------------------------------------------");		
		
		model.addAttribute("pageStatus", pageStatus);
		return "/pages/satCalc/v2/satCalcDraftList";
	}
	
	@RequestMapping(path="/v2/read", method= RequestMethod.POST)
	public String projectInfoReadMode(HttpServletRequest request, @ModelAttribute PageStatusVO pageStatus, @RequestParam Map<String, Object> param, Model model) throws Exception {
		System.out.println("------------------------------------------------------------------");
		System.out.println("#CALL INFO :: [" + Arrays.toString(this.getClass().getAnnotations()) + "]");
		System.out.println("#CALL METHOD :: [" + this.getClass().getName() + "." + new Object() {}.getClass().getEnclosingMethod().getName() + "]");		
		System.out.println("------------------------------------------------------------------");		
		
		UserSessionVO userSession= AppHelper.getSession(request);
		param.put(Constant.PARAM_NAME.SESSION, userSession);

		//=====================================================
		//	[시작] 20220224 남웅주  2022년도 표준감사시간 개정
		//=====================================================		
		//model.addAttribute("auth", calcSatService.getCalcSatMetaInfo(param));
		Map<String, Object> auth = calcSatService.getCalcSatMetaInfo(param);
		model.addAttribute("auth", auth);		
		model.addAttribute("mode", "read");
		model.addAttribute("prjtCd", (String) param.get("prjtCd"));
		model.addAttribute("pageStatus", pageStatus);
		
		if(auth.get("formDiv").equals("v4")) {
			return "/pages/satCalc/v4/satCalcInfo";	
		}else {
			return "/pages/satCalc/v2/satCalcInfo";
		}
		//=====================================================
		//	[종료] 20220224 남웅주  2022년도 표준감사시간 개정
		//=====================================================		
	}
	
	@RequestMapping(path="/v2/edit", method= RequestMethod.POST)
	public String projectInfoEditMode(HttpServletRequest request, @ModelAttribute PageStatusVO pageStatus, @RequestParam Map<String, Object> param, Model model) throws Exception {
		System.out.println("------------------------------------------------------------------");
		System.out.println("#CALL INFO :: [" + Arrays.toString(this.getClass().getAnnotations()) + "]");
		System.out.println("#CALL METHOD :: [" + this.getClass().getName() + "." + new Object() {}.getClass().getEnclosingMethod().getName() + "]");		
		System.out.println("------------------------------------------------------------------");		
		
		UserSessionVO userSession= AppHelper.getSession(request);
		param.put(Constant.PARAM_NAME.SESSION, userSession);
		
		//=====================================================
		//	[시작] 20220224 남웅주  2022년도 표준감사시간 개정
		//=====================================================		
		//model.addAttribute("auth", calcSatService.getCalcSatMetaInfo(param));
		Map<String, Object> auth = calcSatService.getCalcSatMetaInfo(param);
		model.addAttribute("auth", auth);	
		model.addAttribute("mode", "edit");
		model.addAttribute("prjtCd", (String) param.get("prjtCd"));
		model.addAttribute("pageStatus", pageStatus);
		
		if(auth.get("formDiv").equals("v4")) {
			return "/pages/satCalc/v4/satCalcInfo";	
		}else {
			return "/pages/satCalc/v2/satCalcInfo";
		}
		//=====================================================
		//	[종료] 20220224 남웅주  2022년도 표준감사시간 개정
		//=====================================================		
	}
	
	@RequestMapping(path="/v2/new", method= RequestMethod.POST)
	public String projectInfoNewMode(HttpServletRequest request, @ModelAttribute PageStatusVO pageStatus, @RequestParam Map<String, Object> param, Model model) throws Exception {
		System.out.println("------------------------------------------------------------------");
		System.out.println("#CALL INFO :: [" + Arrays.toString(this.getClass().getAnnotations()) + "]");
		System.out.println("#CALL METHOD :: [" + this.getClass().getName() + "." + new Object() {}.getClass().getEnclosingMethod().getName() + "]");		
		System.out.println("------------------------------------------------------------------");		
		
		model.addAttribute("mode", "new");
		model.addAttribute("pageStatus", pageStatus);
		return "/pages/satCalc/v2/satCalcInfo";
	}
	
	//=====================================================
	//	[시작] 20220224 남웅주  2022년도 표준감사시간 개정
	//=====================================================		
	@RequestMapping(path="/v4/new", method= RequestMethod.POST)
	public String projectInfoNewModeV4(HttpServletRequest request, @ModelAttribute PageStatusVO pageStatus, @RequestParam Map<String, Object> param, Model model) throws Exception {
		System.out.println("------------------------------------------------------------------");
		System.out.println("#CALL INFO :: [" + Arrays.toString(this.getClass().getAnnotations()) + "]");
		System.out.println("#CALL METHOD :: [" + this.getClass().getName() + "." + new Object() {}.getClass().getEnclosingMethod().getName() + "]");		
		System.out.println("------------------------------------------------------------------");		
		
		model.addAttribute("mode", "new");
		model.addAttribute("pageStatus", pageStatus);
		return "/pages/satCalc/v4/satCalcInfo";
	}	
	//=====================================================
	//	[종료] 20220224 남웅주  2022년도 표준감사시간 개정
	//=====================================================		
}
