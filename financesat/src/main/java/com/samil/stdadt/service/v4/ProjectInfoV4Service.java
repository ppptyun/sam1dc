package com.samil.stdadt.service.v4;

import java.util.List;
import java.util.Map;

import com.samil.stdadt.dto.ProjectInfoV4Dto;
import com.samil.stdadt.dto.SubProjectV4Dto;
import com.samil.stdadt.vo.ProjectInfoV4VO;
import com.samil.stdadt.vo.ProjectVersionVO;

public interface ProjectInfoV4Service {

	// 프로젝트 기본정보 가져오기
	public List<ProjectInfoV4VO> getProjectInfoList(Map<String, Object> param) throws Exception;
	public int addProject(ProjectInfoV4Dto param) throws Exception;
	public int updateProject(ProjectInfoV4Dto param) throws Exception;
	public boolean deleteProject(Map<String, Object> param) throws Exception;
	public boolean restoreProject(Map<String, Object> param) throws Exception;
	public boolean deletePermanentlyProject(Map<String, Object> param, ProjectVersionVO versionParam) throws Exception;
}
