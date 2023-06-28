package com.samil.stdadt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.samil.stdadt.vo.ProjectBudgetMemberVO;
import com.samil.stdadt.vo.ProjectBudgetSummaryVO;
import com.samil.stdadt.vo.ProjectVersionVO;

@Mapper
public interface HistoryMapper {
	public int addHistVersion(ProjectVersionVO param) throws Exception;
	public int addPrjtHist(ProjectVersionVO param) throws Exception;
	public int addSubPrjtHist(ProjectVersionVO param) throws Exception;
	public int addMembHist(ProjectVersionVO param) throws Exception;
	public int addMembBdgtHist(ProjectVersionVO param) throws Exception;
	
	public int delHistVersion(ProjectVersionVO param) throws Exception;
	public int delPrjtHist(ProjectVersionVO param) throws Exception;
	public int delSubPrjtHist(ProjectVersionVO param) throws Exception;
	public int delMembHist(ProjectVersionVO param) throws Exception;
	public int delMembBdgtHist(ProjectVersionVO param) throws Exception;
	
	public Map<String, Object> getPrjtInfoSimpleHist(ProjectVersionVO param) throws Exception;
	public Map<String, Object> getVersionAprvComplete(ProjectVersionVO param) throws Exception;
	public ProjectBudgetSummaryVO getProjectBudgetSummaryHist(ProjectVersionVO param) throws Exception;
	public List<ProjectBudgetMemberVO> getProjectBudgetMemberHist(ProjectVersionVO param) throws Exception;
}
