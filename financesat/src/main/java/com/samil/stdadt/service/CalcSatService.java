package com.samil.stdadt.service;

import java.util.List;
import java.util.Map;

import com.samil.stdadt.dto.ProjectInfoV3Dto;
import com.samil.stdadt.vo.ProjectInfoV3VO;

public interface CalcSatService {
	public List<Map<String, Object>> getCalcSatList(Map<String, Object> param) throws Exception;
	public List<ProjectInfoV3VO> getCalcSatInfo(Map<String, Object> param) throws Exception;
	public Map<String, Object> getCalcSatMetaInfo(Map<String, Object> param)  throws Exception;
	
	// 계수 값 가져오기
	public Map<String, Object> getFactorValue(Map<String, Object> param) throws Exception;
	
	// 계수 값 가져오기
	public Map<String, Object> getFactorValue(String prflId, String satgrpCd, String factorCd, String grpFactorDiv) throws Exception;
	
	// 그룹 리스트 가져오기
	public List<Map<String, Object>> getSatgrpList(Map<String, Object> param) throws Exception;
	
	
	// 그룹-산업별 프로파일 가져오기
	public Map<String, Object> getGrpIndusPrfl(Map<String, Object> param) throws Exception; 
	
	public Map<String, Object> getGrpYearPrfl(Map<String, Object> param) throws Exception;
	
	public Double getIntrAdtRatio(Map<String, Object> param) throws Exception;
	
	public int addCalcSatInfo(ProjectInfoV3Dto param) throws Exception;
	public int updateCalcSatInfo(ProjectInfoV3Dto param) throws Exception;
	public int deleteCalcSatInfo(Map<String, Object> param) throws Exception;
	
	public Map<String, Object> getGrpPrflInfo(Map<String, Object> param) throws Exception;
	
	public List<String> getYear() throws Exception;
}
