package com.samil.stdadt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.samil.stdadt.retain.vo.RetainVO;
import com.samil.stdadt.vo.ProjectBudgetCisV4VO;
import com.samil.stdadt.vo.ProjectBudgetMemberV4VO;
import com.samil.stdadt.vo.ProjectBudgetSummaryV4VO;
import com.samil.stdadt.vo.ProjectBudgetSummaryVO;
import com.samil.stdadt.vo.WeekVO;

@Mapper
public interface ProjectBudgetV4Mapper {
	// Summary 정보 및 Table Column 정보
	public ProjectBudgetSummaryVO getProjectBudgetSummary(Map<String, Object> param) throws Exception;
	
	// 프로젝트 멤버 별 예산 시간 정보
	public List<ProjectBudgetMemberV4VO> getProjectBudgetMember(Map<String, Object> param) throws Exception;
	
	// 프로젝트 기간의 주차 정보
	public List<WeekVO> getWeekInfo(Map<String, Object> param) throws Exception;
	
	// 프로젝트 기간의 공휴일 정보
	public List<Map<String, Object>> getHolidayInfo(Map<String, Object> param) throws Exception;
	
	// 전체 프로젝트 멤버 삭제
	public int deleteAllMember(@Param("prjtCd") String param) throws Exception;
	
	// 전체 프로젝트 멤버 Budget 삭제
	public int deleteAllMemberBudget(@Param("prjtCd") String param) throws Exception;
	
	// New Staff, Other Budget 업데이트
	public int updateBdgt(Map<String, Object> param) throws Exception;
	
	// 프로젝트 멤버 추가
	public int inertMember(Map<String, Object> param) throws Exception;
	
	// 프로젝트 멤버 Budget 추가
	public int inertMemberBudget(Map<String, Object> param) throws Exception;
	
	// 직급별 CM 값 가져오기
	public List<Map<String, Object>> getCMByGradCd(Map<String, Object> param) throws Exception;
			
	// 스케쥴로 전송할 Retain Data 가져오기 ==> 스케쥴러에서 실행
	public List<RetainVO> selectScheduledRetainData() throws Exception;
	
	// Retain 전송 여부 업데이트(Retain 전숑 여부, 전송 일시 업데이트) ==> 스케쥴러에서 실행
	public int updateRetainTran(Map<String, Object> param) throws Exception;
	
	// 스케쥴로 전송할 대상 저장하기 ==> 사용자가 Retain Data 전송 버튼 클릭 시 실행
	public int saveRetainSchedule(Map<String, Object> param) throws Exception;
	
	//--------------------------------------------------
	// 20200428 추가
	//--------------------------------------------------
	public List<ProjectBudgetCisV4VO> getProjectBudgetCis(Map<String, Object> param) throws Exception;
	// Summary 정보 및 Table Column 정보
	public ProjectBudgetSummaryV4VO getProjectBudgetV4Summary(Map<String, Object> param) throws Exception;
	// Form_Div 정보 조회
	public ProjectBudgetSummaryVO getProjectFormInfo(Map<String, Object> param) throws Exception;
		
	// Cis 그리드 업데이트
	public int updateCisMainBudget(Map<String, Object> param) throws Exception;
	public int mergeCisSubBudget(Map<String, Object> param) throws Exception;
	
	
	public List<Map<String, Object>> checkDuplPrjt(Map<String, Object> param) throws Exception;
	public List<Map<String, Object>> selectDftBdgtRoleConfig(Map<String, Object> param)  throws Exception;
	
	public List<Map<String, Object>> getGradList() throws Exception;
	
	/* ===========================================================
		[시작] 20230207 Budget 입력 주기 : Budget v4에서 저장시에 최도 
		        BUDGET_INPUT_CYCLE 가 비어 있을때만 업데이트 한다.
			매주 Weekly
			월별 Monthly
			분기별 Quarterly
			매년 Annually
		=========================================================== */
	public List<WeekVO> getMonthlyInfo(Map<String, Object> param) throws Exception;
	public List<WeekVO> getQuarterlyInfo(Map<String, Object> param) throws Exception;
	public List<WeekVO> getAnnuallyInfo(Map<String, Object> param) throws Exception;
	
	// 프로젝트 멤버 별 예산 시간 정보 Monthly
	public List<ProjectBudgetMemberV4VO> getProjectBudgetMemberMonthly(Map<String, Object> param) throws Exception;
	// 프로젝트 멤버 별 예산 시간 정보 Quarterly
	public List<ProjectBudgetMemberV4VO> getProjectBudgetMemberQuarterly(Map<String, Object> param) throws Exception;
	// 프로젝트 멤버 별 예산 시간 정보 Annually
	public List<ProjectBudgetMemberV4VO> getProjectBudgetMemberAnnually(Map<String, Object> param) throws Exception;
	
	/* ===========================================================
		[종료] 20230207 Budget 입력 주기 : Budget v4에서 저장시에 최도 
		        BUDGET_INPUT_CYCLE 가 비어 있을때만 업데이트 한다.
			매주 Weekly
			월별 Monthly
			분기별 Quarterly
			매년 Annually
		=========================================================== */
	
	
}
