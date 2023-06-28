package com.samil.stdadt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.samil.stdadt.comm.util.AppHelper;
import com.samil.stdadt.comm.vo.ApplicationConfigVO;
import com.samil.stdadt.comm.vo.UserSessionVO;
import com.samil.stdadt.service.MonitoringService;
import com.samil.stdadt.util.Constant;
import com.samil.stdadt.vo.ResultVO;

@Controller
@RequestMapping("/json/monitoring")
public class MonitoringController {

	@Autowired
	MonitoringService monitoringService;
	
	@Autowired
	ApplicationConfigVO appConf;
	
	@RequestMapping("/sat")
	public ModelAndView getStandardAuditTimeList(HttpServletRequest request, @RequestBody(required=false) Map<String, Object> param) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		UserSessionVO userSession= AppHelper.getSession(request);
		if(param == null) param = new HashMap<String, Object>();
		param.put(Constant.PARAM_NAME.APP_CD, appConf.getAppCd());
		param.put(Constant.PARAM_NAME.SESSION, userSession);
		
		List<Map<String, Object>> progressList = monitoringService.getStandardAuditTimeList(param);
		ResultVO result = new ResultVO();
		
		if(progressList == null) {
			result.setStatus(Constant.RESULT.FAIL);
		}else {
			for(Map<String, Object> data : progressList) {
				data.put("EL", data.get("EL_KORNM") + "(" + data.get("EL_EMPNO") + ")");
				data.put("PM", data.get("PM_KORNM") + "(" + data.get("PM_EMPNO") + ")");
			}
			
			result.setStatus(Constant.RESULT.SUCCESS);
			result.setData(progressList);
		}

		mv.addObject("result", result);
		
		return mv;
	}
	
}
