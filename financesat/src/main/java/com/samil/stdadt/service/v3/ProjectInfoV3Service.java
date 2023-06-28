package com.samil.stdadt.service.v3;

import java.util.List;
import java.util.Map;

import com.samil.stdadt.dto.ProjectInfoV3Dto;
import com.samil.stdadt.dto.SubProjectV3Dto;
import com.samil.stdadt.vo.ProjectInfoV3VO;
import com.samil.stdadt.vo.ProjectVersionVO;

public interface ProjectInfoV3Service {

	// 프로젝트 기본정보 가져오기
	public List<ProjectInfoV3VO> getProjectInfoList(Map<String, Object> param) throws Exception;
	public int addProject(ProjectInfoV3Dto param) throws Exception;
	public int updateProject(ProjectInfoV3Dto param) throws Exception;
	public boolean deleteProject(Map<String, Object> param) throws Exception;
	public boolean restoreProject(Map<String, Object> param) throws Exception;
	public boolean deletePermanentlyProject(Map<String, Object> param, ProjectVersionVO versionParam) throws Exception;
}
