package com.samil.stdadt.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.samil.stdadt.service.MonitoringService;
import com.samil.stdadt.vo.PageStatusVO;

@Controller
@RequestMapping("/pages/monitor")
public class MonitoringWeb {
	
	@Autowired
	MonitoringService monitorService;
	
	@ModelAttribute(name="yearMonitoringList")
	public List<String> getMonitoringYearList() throws Exception{
		return monitorService.getMonitoringYearList();
	}
	
//	@RequestMapping("/progress")
//	public String pageProgressReview(@ModelAttribute PageStatusVO pageStatus, Model model) {
//		model.addAttribute("pageStatus", pageStatus);
//		return "/pages/monitoring/progressReview";
//	}
//	
//	@RequestMapping("/retain_tlink")
//	public String pageRetainTLink(@ModelAttribute PageStatusVO pageStatus, Model model) {
//		model.addAttribute("pageStatus", pageStatus);
//		return "/pages/monitoring/retainTLink";
//	}
	
	@RequestMapping("/std_audit")
	public String pageStdAdtTime(@ModelAttribute PageStatusVO pageStatus, Model model) {
		model.addAttribute("pageStatus", pageStatus);
		return "/pages/monitoring/stdAdtTime";
	}
}
