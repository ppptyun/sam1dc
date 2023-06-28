package com.samil.stdadt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.samil.stdadt.dto.ProjectInfoV2Dto;
import com.samil.stdadt.vo.ProjectInfoV2VO;
import com.samil.stdadt.vo.SubProjectVO;

@Mapper
public interface ProjectInfoV2Mapper {

	// CRUD
	// 프로젝트 양식 정보	
	public List<ProjectInfoV2VO> getProjectInfo(Map<String, Object> param) throws Exception;
	public int addProject(ProjectInfoV2Dto param) throws Exception;
	public int updateProject(ProjectInfoV2Dto param) throws Exception;
	
	public List<SubProjectVO> getSubProject(ProjectInfoV2Dto param) throws Exception;
	public List<SubProjectVO> getSubProject(Map<String, Object> param) throws Exception;
	public int addSubProject(ProjectInfoV2Dto param) throws Exception;
	public int delSubProject(ProjectInfoV2Dto param) throws Exception;
	public int addSubProject(Map<String, Object> param) throws Exception;
	public int delSubProject(Map<String, Object> param) throws Exception;
}
