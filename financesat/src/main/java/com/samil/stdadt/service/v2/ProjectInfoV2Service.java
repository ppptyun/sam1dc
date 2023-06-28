package com.samil.stdadt.service.v2;

import java.util.List;
import java.util.Map;

import com.samil.stdadt.dto.ProjectInfoV2Dto;
import com.samil.stdadt.dto.SubProjectV2Dto;
import com.samil.stdadt.vo.ProjectInfoV2VO;
import com.samil.stdadt.vo.ProjectVersionVO;

public interface ProjectInfoV2Service {

	// 프로젝트 기본정보 가져오기
	public List<ProjectInfoV2VO> getProjectInfoList(Map<String, Object> param) throws Exception;
	public int addProject(ProjectInfoV2Dto param) throws Exception;
	public int updateProject(ProjectInfoV2Dto param) throws Exception;
	public boolean deleteProject(Map<String, Object> param, ProjectVersionVO versionParam) throws Exception;
	
	public List<SubProjectV2Dto> getSubProjectList(ProjectInfoV2Dto param) throws Exception;
}
