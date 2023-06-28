package com.samil.stdadt.retain.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.samil.stdadt.retain.service.RetainService;
import com.samil.stdadt.util.Constant;
import com.samil.stdadt.vo.ResultVO;

@Controller
@RequestMapping(path="/json/retain")
public class RetainController {
	
	@Autowired
	RetainService retainService;
	
	@RequestMapping(path="/run/schedule-tran-retain")
	public ModelAndView schedSaveRetain(HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		ResultVO result = new ResultVO();
		
		retainService.schedSaveRetain();
		result.setStatus(Constant.RESULT.SUCCESS);
		mv.addObject("result", result);
		return mv;
	}
}
