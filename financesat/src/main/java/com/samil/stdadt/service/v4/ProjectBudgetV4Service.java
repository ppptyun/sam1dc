package com.samil.stdadt.service.v4;

import java.util.List;
import java.util.Map;

import com.samil.stdadt.retain.vo.RetainVO;
import com.samil.stdadt.vo.ProjectBudgetSummaryVO;
import com.samil.stdadt.vo.ProjectBudgetCisV4VO;
import com.samil.stdadt.vo.ProjectBudgetMemberV4VO;
import com.samil.stdadt.vo.ProjectBudgetSummaryV4VO;
import com.samil.stdadt.vo.ResultVO;
import com.samil.stdadt.vo.WeekVO;

public interface ProjectBudgetV4Service {
	
	public ProjectBudgetSummaryV4VO getBudgetSummary(Map<String, Object> param) throws Exception;
	public List<ProjectBudgetMemberV4VO> getBudgetList(Map<String, Object> param) throws Exception;
	
	public List<WeekVO> getBudgetWeek(Map<String, Object> param) throws Exception;
	
	public ResultVO saveMemberBudget(Map<String, Object> param) throws Exception;
	
	public List<Map<String, Object>> getCMByGradCd(Map<String, Object> param) throws Exception;
	
	public boolean reqELAprv(Map<String, Object> param) throws Exception;
		
	public int saveRetainSchedule(Map<String, Object> param) throws Exception;
	
	//------------------------------------------------------
	// 20200428 추가
	//------------------------------------------------------
	public List<ProjectBudgetCisV4VO> getBudgetCisList(Map<String, Object> param) throws Exception;
	public ProjectBudgetSummaryVO getProjectFormInfo(Map<String, Object> param) throws Exception;
		
	// Main 또는 Sub 프로젝트 업데이트 전 체크
	public List<Map<String, Object>> checkDuplPrjt(Map<String, Object> param) throws Exception;
	public List<Map<String, Object>> selectDftBdgtRoleConfig(Map<String, Object> param)  throws Exception;
	
	/* ===========================================================
	[시작] 20230207 Budget 입력 주기 : Budget v4에서 저장시에 최도 
	        BUDGET_INPUT_CYCLE 가 비어 있을때만 업데이트 한다.
		매주 Weekly
		월별 Monthly
		분기별 Quarterly
		매년 Annually
	=========================================================== */	
	public List<WeekVO> getBudgetMonthly(Map<String, Object> param) throws Exception;
	public List<WeekVO> getBudgetQuarterly(Map<String, Object> param) throws Exception;
	public List<WeekVO> getBudgetAnnually(Map<String, Object> param) throws Exception;
	/* ===========================================================
	[종료] 20230207 Budget 입력 주기 : Budget v4에서 저장시에 최도 
	        BUDGET_INPUT_CYCLE 가 비어 있을때만 업데이트 한다.
		매주 Weekly
		월별 Monthly
		분기별 Quarterly
		매년 Annually
	=========================================================== */	
}
