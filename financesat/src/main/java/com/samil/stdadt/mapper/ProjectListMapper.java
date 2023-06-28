package com.samil.stdadt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.samil.stdadt.vo.ProjectListV3VO;
import com.samil.stdadt.vo.ProjectListVO;

@Mapper
public interface ProjectListMapper {
	
	// 프로젝트 리스트 화면 정보
	public List<ProjectListVO> getProjectList(Map<String, Object> param) throws Exception;
	
	public List<ProjectListV3VO> getProjectListV3(Map<String, Object> param) throws Exception;
	
	public List<Map<String, Object>> getProjectDraftList(Map<String, Object> param) throws Exception;
	
	
	public List<String> getYear(@Param("formDiv") List<String> formDiv) throws Exception;
	
	public int saveReCalculate(Map<String, Object> param) throws Exception;
	
	public int delDraftPrjtList(Map<String, Object> param) throws Exception;
	
}
