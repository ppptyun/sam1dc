package com.samil.stdadt.service.v3;

import java.util.List;
import java.util.Map;

import com.samil.stdadt.retain.vo.RetainVO;
import com.samil.stdadt.vo.ProjectBudgetSummaryVO;
import com.samil.stdadt.vo.ProjectBudgetCisV3VO;
import com.samil.stdadt.vo.ProjectBudgetMemberV3VO;
import com.samil.stdadt.vo.ProjectBudgetSummaryV3VO;
import com.samil.stdadt.vo.ResultVO;
import com.samil.stdadt.vo.WeekVO;

public interface ProjectBudgetV3Service {
	
	public ProjectBudgetSummaryV3VO getBudgetSummary(Map<String, Object> param) throws Exception;
	public List<ProjectBudgetMemberV3VO> getBudgetList(Map<String, Object> param) throws Exception;
	
	public List<WeekVO> getBudgetWeek(Map<String, Object> param) throws Exception;
	
	public ResultVO saveMemberBudget(Map<String, Object> param) throws Exception;
	
	public List<Map<String, Object>> getCMByGradCd(Map<String, Object> param) throws Exception;
	
	public boolean reqELAprv(Map<String, Object> param) throws Exception;
		
	public int saveRetainSchedule(Map<String, Object> param) throws Exception;
	
	//------------------------------------------------------
	// 20200428 추가
	//------------------------------------------------------
	public List<ProjectBudgetCisV3VO> getBudgetCisList(Map<String, Object> param) throws Exception;
	public ProjectBudgetSummaryVO getProjectFormInfo(Map<String, Object> param) throws Exception;
	
	
	// Main 또는 Sub 프로젝트 업데이트 전 체크
	public List<Map<String, Object>> checkDuplPrjt(Map<String, Object> param) throws Exception;
	public List<Map<String, Object>> selectDftBdgtRoleConfig(Map<String, Object> param)  throws Exception;
}
