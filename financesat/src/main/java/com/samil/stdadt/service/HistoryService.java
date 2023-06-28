package com.samil.stdadt.service;

import java.util.List;
import java.util.Map;

import com.samil.stdadt.vo.ProjectBudgetMemberVO;
import com.samil.stdadt.vo.ProjectBudgetSummaryVO;
import com.samil.stdadt.vo.ProjectVersionVO;
import com.samil.stdadt.vo.WeekVO;

public interface HistoryService {
	public boolean addHistory(ProjectVersionVO param) throws Exception;
	public boolean delHistory(ProjectVersionVO param) throws Exception;

	
	public List<WeekVO> getBudgetWeekHist(ProjectVersionVO param) throws Exception;
	public Map<String, Object> getVersionAprvComplete(ProjectVersionVO param) throws Exception;
	public ProjectBudgetSummaryVO getProjectBudgetSummaryHist(ProjectVersionVO param) throws Exception;
	public List<ProjectBudgetMemberVO> getProjectBudgetMemberHist(ProjectVersionVO param) throws Exception;
}
