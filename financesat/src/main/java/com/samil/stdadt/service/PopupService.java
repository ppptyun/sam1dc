package com.samil.stdadt.service;

import java.util.List;
import java.util.Map;

import com.samil.stdadt.vo.ConflictListVO;
import com.samil.stdadt.vo.ProfitabilityV3VO;
import com.samil.stdadt.vo.ProfitabilityVO;

public interface PopupService {
	
	// 프로젝트 검색용
	public List<Map<String, Object>> getProject(Map<String, Object> param) throws Exception;
	
	// 프로젝트 검색용
	public List<Map<String, Object>> getProjectV3(Map<String, Object> param) throws Exception;

	// 임직원 검색 용
	public List<Map<String, Object>> getEmp(Map<String, Object> param) throws Exception;
	
	// Conflict 조회
	public List<ConflictListVO> getBudgetConflict(Map<String, Object> param) throws Exception;		
		
	// 수익성 분석
	public ProfitabilityVO getProfit(Map<String, Object> param) throws Exception;
	
	// 수익성 분석
	public ProfitabilityV3VO getProfitV3(Map<String, Object> param) throws Exception;
	
	// 결재완료 시점의 예산 정보 가져오기
	public Map<String, Object> getBdgtHist(Map<String, Object> param) throws Exception;
	
	//프로젝트 반려 의견 보기
	public String rejectReasonRead(Map<String, Object> param) throws Exception;
	
	
	// 감사인 리스트
	public List<Map<String, Object>> getAuditorList(Map<String, Object> param) throws Exception;
	
	// 감사인 동명 존재 여부 판단
	public Boolean isExistAuditor(Map<String, Object> param) throws Exception;
		
	// 감사인 추가
	public int addAuditor(Map<String, Object> param) throws Exception;
}
