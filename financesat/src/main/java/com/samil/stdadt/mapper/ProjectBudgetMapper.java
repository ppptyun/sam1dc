package com.samil.stdadt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.samil.stdadt.retain.vo.RetainVO;
import com.samil.stdadt.vo.ProjectBudgetMemberVO;
import com.samil.stdadt.vo.ProjectBudgetSummaryVO;
import com.samil.stdadt.vo.WeekVO;

@Mapper
public interface ProjectBudgetMapper {
	
	// Summary 정보 및 Table Column 정보
	public ProjectBudgetSummaryVO getProjectBudgetSummary(Map<String, Object> param) throws Exception;
	
	// 프로젝트 멤버 별 예산 시간 정보
	public List<ProjectBudgetMemberVO> getProjectBudgetMember(Map<String, Object> param) throws Exception;
	
	// 프로젝트 기간의 주차 정보
	public List<WeekVO> getWeekInfo(Map<String, Object> param) throws Exception;
	
	// 프로젝트 기간의 공휴일 정보
	public List<Map<String, Object>> getHolidayInfo(Map<String, Object> param) throws Exception;
	
	// 전체 프로젝트 멤버 삭제
	public int deleteAllMember(Map<String, Object> param) throws Exception;
	
	// 전체 프로젝트 멤버 Budget 삭제
	public int deleteAllMemberBudget(Map<String, Object> param) throws Exception;
	
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
}
