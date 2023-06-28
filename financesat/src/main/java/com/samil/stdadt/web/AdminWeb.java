package com.samil.stdadt.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.samil.stdadt.comm.vo.ApplicationConfigVO;
import com.samil.stdadt.service.AdminService;
import com.samil.stdadt.vo.PageStatusVO;

@Controller
@RequestMapping(path="/pages/admin", method= {RequestMethod.POST, RequestMethod.GET})
public class AdminWeb {
	@Autowired
	ApplicationConfigVO appConf;
	
	@Autowired
	AdminService adminService;
	
	@RequestMapping(path="/profile")
	public String profile(@ModelAttribute PageStatusVO pageStatus, Model model) throws Exception {
		model.addAttribute("pageStatus", pageStatus);
		return "/pages/admin/profile";
	}
	
	@RequestMapping(path="/divprof")
	public String divisionProfile(@ModelAttribute PageStatusVO pageStatus, Model model) throws Exception {
		model.addAttribute("pageStatus", pageStatus);
		return "/pages/admin/divisionProfile";
	}
	
	@RequestMapping(path="/history")
	public String historyView(@ModelAttribute PageStatusVO pageStatus, Model model) throws Exception {
		model.addAttribute("pageStatus", pageStatus);
		return "/pages/admin/historyView";
	}
}
