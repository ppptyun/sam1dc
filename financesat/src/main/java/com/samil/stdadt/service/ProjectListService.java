package com.samil.stdadt.service;

import java.util.List;
import java.util.Map;

import com.samil.stdadt.vo.ProjectListV3VO;
import com.samil.stdadt.vo.ProjectListVO;

public interface ProjectListService {
	public List<ProjectListVO> getProjectList(Map<String, Object> param) throws Exception;
	
	public List<ProjectListV3VO> getProjectListV3(Map<String, Object> param) throws Exception;
	
	public List<Map<String, Object>> getProjectDraftList(Map<String, Object> param) throws Exception;
	
	public List<String> getYear(List<String> formDiv) throws Exception;
	
	public int saveReCalculate(Map<String, Object> param) throws Exception;
	
	public int delDraftPrjtList(Map<String, Object> param) throws Exception;
}
