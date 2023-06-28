package com.samil.stdadt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.samil.stdadt.vo.ConflictListVO;
import com.samil.stdadt.vo.ProfitabilityV3VO;
import com.samil.stdadt.vo.ProfitabilityVO;

@Mapper
public interface PopupMapper {

	public List<Map<String, Object>> getEmp(Map<String, Object> param) throws Exception;
	
	public List<Map<String, Object>> getProject(Map<String, Object> param) throws Exception;
	
	public List<Map<String, Object>> getProjectV3(Map<String, Object> param) throws Exception;

	public ProfitabilityVO getProfitProject(Map<String, Object> param) throws Exception;
	
	public ProfitabilityV3VO getProfitProjectV3(Map<String, Object> param) throws Exception;
	
	public List<Map<String, Object>> getProfitMember(ProfitabilityVO param) throws Exception;
	
	public List<Map<String, Object>> getProfitMemberV3(ProfitabilityV3VO param) throws Exception;
	
	// Conflict 조회
	public List<ConflictListVO> getBudgetConflict(Map<String, Object> param) throws Exception;
	
	//프로젝트 반려 사유
	public String rejectReasonRead(Map<String, Object> param) throws Exception;
	
	
	// 감사인 리스트
	public List<Map<String, Object>> getAuditorList(Map<String, Object> param) throws Exception;
	
	// 감사인 동명 존재 여부 판단
	public Boolean isExistAuditor(Map<String, Object> param) throws Exception;
	
	// 감사인 추가
	public int addAuditor(Map<String, Object> param) throws Exception;
}
