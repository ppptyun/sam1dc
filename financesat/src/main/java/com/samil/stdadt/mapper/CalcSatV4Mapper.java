package com.samil.stdadt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.samil.stdadt.dto.ProjectInfoV4Dto;
import com.samil.stdadt.vo.ProjectInfoV4VO;

@Mapper
public interface CalcSatV4Mapper {
	
	public Map<String, Object> getCalcSatMetaInfo(Map<String, Object> param)  throws Exception;
	// 프로젝트 리스트 정보	
	public List<Map<String, Object>> getCalcSatList(Map<String, Object> param) throws Exception;
	// 프로젝트 양식 정보	
	public List<ProjectInfoV4VO> getCalcSatInfo(Map<String, Object> param) throws Exception;
	
	// 계수 값 가져오기
	public Map<String, Object> getFactorValue(Map<String, Object> param) throws Exception;
	
	// 그룹 리스트 가져오기
	public List<Map<String, Object>> getSatgrpList(Map<String, Object> param) throws Exception;
	
	public Map<String, Object> getGrpIndusPrfl(Map<String, Object> param) throws Exception;
	
	public Map<String, Object> getGrpYearPrfl(Map<String, Object> param) throws Exception;
	
	public Double getIntrAdtRatio(Map<String, Object> param) throws Exception;
	
	
	
	public int addCalcSatInfo(ProjectInfoV4Dto param) throws Exception;
	public int updateCalcSatInfo(ProjectInfoV4Dto param) throws Exception;
	public int deleteCalcSatInfo(Map<String, Object> param) throws Exception;
	
	public Map<String, Object> getGrpPrflInfo(Map<String, Object> param) throws Exception;
	
	public List<String> getYear() throws Exception;
}
