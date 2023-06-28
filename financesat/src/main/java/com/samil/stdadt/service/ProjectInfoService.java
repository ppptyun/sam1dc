package com.samil.stdadt.service;

import java.util.List;
import java.util.Map;

import com.samil.stdadt.vo.ProjectInfoVO;
import com.samil.stdadt.vo.ProjectVersionVO;
import com.samil.stdadt.vo.SubProjectVO;

public interface ProjectInfoService {

	public Map<String, Object> getPrjtInfoSimple(Map<String, Object> param) throws Exception;
	
	public Map<String, Object> getPrjtMetaInfo(Map<String, Object> param) throws Exception;
	
	public ProjectInfoVO getProjectInfo(Map<String, Object> param) throws Exception;		// 프로젝트 기본정보 가져오기
	
	public List<ProjectInfoVO> getProjectInfoList(Map<String, Object> param) throws Exception;		// 프로젝트 기본정보 가져오기
	
	public int addProjectInfo(Map<String, Object> param) throws Exception;
	
	public int updateProjectInfo(Map<String, Object> param) throws Exception;
	
	public boolean delPrjt(Map<String, Object> param, ProjectVersionVO versionParam) throws Exception;
	
	public List<SubProjectVO> getSubProject(Map<String, Object> param) throws Exception;	// 합산 프로젝트
	
	public Map<String, Object> getInterpolation(Map<String, Object> param)  throws Exception;
	
	public Map<String, Object> getSatgrpInfo(Map<String, Object> param)  throws Exception;
	
	public List<Map<String, Object>> getFactorVal(Map<String, Object> param)  throws Exception;
	
	public Map<String, List<Map<String, Object>>> getSelOption(Map<String, Object> param) throws Exception;
	
	public Map<String, Object> getTooltip(Map<String, Object> param) throws Exception;
	
	public double getYearRate(Map<String, Object> param) throws Exception;
	
	public int getProfileId() throws Exception;
	
	public List<Map<String, Object>> getSatgrpList(Map<String, Object> param) throws Exception;
	
	public List<Map<String, Object>> getSatgrpListByPrjtCd(Map<String, Object> param) throws Exception;
	
	public Map<String, Object> getWkmnsp(String appCd) throws Exception;
	
}
