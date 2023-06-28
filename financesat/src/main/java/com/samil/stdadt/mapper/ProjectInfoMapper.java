package com.samil.stdadt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.samil.stdadt.vo.ProjectInfoVO;
import com.samil.stdadt.vo.SubProjectVO;

@Mapper
public interface ProjectInfoMapper {
	
	// 수정 및 삭제 권한
	public Map<String, Object> getPrjtMetaInfo(Map<String, Object> param) throws Exception;
	
	// New Staff, Ohter 기준 숙련도 가져오기
	public Map<String, Object> getWkmnsp(String appCd) throws Exception;
	
	public Map<String, Object> getPrjtInfoSimple(Map<String, Object> param) throws Exception;
	// 프로젝트 양식 정보	
	public List<ProjectInfoVO> getProjectInfo(Map<String, Object> param) throws Exception;
	
	// 프로젝트 존재 여부 판단하기 위함.
	public Map<String, Object> checkExistProject(Map<String, Object> param) throws Exception;
	// 프로젝트 추가 	
	public int addProjectInfo(Map<String, Object> param) throws Exception;
	// 프로젝트 업데이트
	public int updateProjectInfo(Map<String, Object> param) throws Exception;
	// 합산 프로젝트 목록
	public List<SubProjectVO> getSubProject(Map<String, Object> param) throws Exception;
	// 합산 프로젝트 추가
	public int addSubProject(Map<String, Object> param) throws Exception;
	
	// 보간법
	public Map<String, Object> getInterpolation(Map<String, Object> param) throws Exception;
	// 그룹별 기준숙련도 및 상한율 정보
	public Map<String, Object> getSatgrpInfo(Map<String, Object> param) throws Exception;
	// 그룹 연도별 적용 요율
	public Map<String, Object> getYearRate(Map<String, Object> param) throws Exception;
	// 그룹 별 계수값
	public List<Map<String, Object>> getFactorVal(Map<String, Object> param) throws Exception;
	// 양식에서 선택 옵션리스트
	public List<Map<String, Object>> getSelOption(Map<String, Object> param) throws Exception;
	// Tooltip 쿼리에 필요한 컬럼 String 정보 가져오기
	public Map<String, Object> getToolTipColumnStr(Map<String, Object> param) throws Exception;
	// Tooltip 정보 가져오기
	public Map<String, Object> getTooltip(Map<String, Object> param) throws Exception;
	// 현재일 기준으로 유효한 프로파일 ID 가져오기 (New Project 시 사용)	
	public Map<String, Object> getProfileId() throws Exception;
	
	public int updateStatus(Map<String, Object> param) throws Exception;
	
	public int delPrjt(Map<String, Object> param) throws Exception;
	public int delSubPrjt(Map<String, Object> param) throws Exception;
	public int delMemb(Map<String, Object> param) throws Exception;
	public int delMembBdgt(Map<String, Object> param) throws Exception;
	public int initPrjtOfAuditDashboard(Map<String, Object> param) throws Exception;
	
	public List<Map<String, Object>> getSatgrpList(Map<String, Object> param) throws Exception;
	public List<Map<String, Object>> getSatgrpListByPrjtCd(Map<String, Object> param) throws Exception;
	
	// --------------------------------------------
	// 20230207 프로젝트에 Budget 입력 주기 업데이트
	// --------------------------------------------
	public int updateBudgetInputCycle(Map<String, Object> param) throws Exception;

}
