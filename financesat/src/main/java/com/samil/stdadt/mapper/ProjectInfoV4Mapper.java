package com.samil.stdadt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.samil.stdadt.dto.ProjectInfoV4Dto;
import com.samil.stdadt.vo.ProjectInfoV4VO;
import com.samil.stdadt.vo.SubProjectVO;

@Mapper
public interface ProjectInfoV4Mapper {

	// CRUD
	// 프로젝트 양식 정보	
	public List<ProjectInfoV4VO> getProjectInfo(Map<String, Object> param) throws Exception;
	public int addProject(ProjectInfoV4Dto param) throws Exception;
	public int updateProject(ProjectInfoV4Dto param) throws Exception;
	
	public List<SubProjectVO> getSubProject(ProjectInfoV4Dto param) throws Exception;
	public List<SubProjectVO> getSubProject(Map<String, Object> param) throws Exception;
	public int addSubProject(ProjectInfoV4Dto param) throws Exception;
	public int delSubProject(ProjectInfoV4Dto param) throws Exception;
	public int addSubProject(Map<String, Object> param) throws Exception;
	public int delSubProject(Map<String, Object> param) throws Exception;
	
	public int deleteProjectStat(Map<String, Object> param) throws Exception;
	public int restoreProjectStat(Map<String, Object> param) throws Exception;
}
