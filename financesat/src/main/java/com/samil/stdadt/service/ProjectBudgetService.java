package com.samil.stdadt.service;

import java.util.List;
import java.util.Map;

import com.samil.stdadt.retain.vo.RetainVO;
import com.samil.stdadt.vo.ProjectBudgetMemberVO;
import com.samil.stdadt.vo.ProjectBudgetSummaryVO;
import com.samil.stdadt.vo.ResultVO;
import com.samil.stdadt.vo.WeekVO;

public interface ProjectBudgetService {
	
	public ProjectBudgetSummaryVO getBudgetSummary(Map<String, Object> param) throws Exception;
	public List<ProjectBudgetMemberVO> getBudgetList(Map<String, Object> param) throws Exception;
	
	public List<WeekVO> getBudgetWeek(Map<String, Object> param) throws Exception;
	
	public ResultVO saveMemberBudget(Map<String, Object> param) throws Exception;
	
	public List<Map<String, Object>> getCMByGradCd(Map<String, Object> param) throws Exception;
	
	public boolean reqELAprv(Map<String, Object> param) throws Exception;
		
	public int saveRetainSchedule(Map<String, Object> param) throws Exception;
}
