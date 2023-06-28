package com.samil.stdadt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.samil.stdadt.dto.ProjectInfoV3Dto;
import com.samil.stdadt.vo.ProjectInfoV3VO;
import com.samil.stdadt.vo.SubProjectVO;

@Mapper
public interface ProjectInfoV3Mapper {

	// CRUD
	// 프로젝트 양식 정보	
	public List<ProjectInfoV3VO> getProjectInfo(Map<String, Object> param) throws Exception;
	public int addProject(ProjectInfoV3Dto param) throws Exception;
	public int updateProject(ProjectInfoV3Dto param) throws Exception;
	
	public List<SubProjectVO> getSubProject(ProjectInfoV3Dto param) throws Exception;
	public List<SubProjectVO> getSubProject(Map<String, Object> param) throws Exception;
	public int addSubProject(ProjectInfoV3Dto param) throws Exception;
	public int delSubProject(ProjectInfoV3Dto param) throws Exception;
	public int addSubProject(Map<String, Object> param) throws Exception;
	public int delSubProject(Map<String, Object> param) throws Exception;
	
	public int deleteProjectStat(Map<String, Object> param) throws Exception;
	public int restoreProjectStat(Map<String, Object> param) throws Exception;
}
