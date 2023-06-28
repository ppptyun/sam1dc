package com.samil.stdadt.service.v3.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samil.stdadt.dto.ProjectInfoV3Dto;
import com.samil.stdadt.mapper.HistoryMapper;
import com.samil.stdadt.mapper.ProjectBudgetV3Mapper;
import com.samil.stdadt.mapper.ProjectInfoMapper;
import com.samil.stdadt.mapper.ProjectInfoV3Mapper;
import com.samil.stdadt.service.v3.ProjectInfoV3Service;
import com.samil.stdadt.vo.ProjectInfoV3VO;
import com.samil.stdadt.vo.ProjectVersionVO;

@Service
public class ProjectInfoV3ServiceImpl implements ProjectInfoV3Service{
	
	@Autowired
	HistoryMapper histMapper;
	
	@Autowired
	ProjectInfoMapper prjtMapperV1;
	
	@Autowired
	ProjectInfoV3Mapper prjtMapper;
	
	@Autowired
	ProjectBudgetV3Mapper bdgtMapper;
	
	@Override
	public List<ProjectInfoV3VO> getProjectInfoList(Map<String, Object> param) throws Exception {
		return prjtMapper.getProjectInfo(param);
	}

	@Override
	public int addProject(ProjectInfoV3Dto param) throws Exception {
		prjtMapper.delSubProject(param);
		return prjtMapper.addProject(param);
	}

	@Override
	public int updateProject(ProjectInfoV3Dto param) throws Exception {
		// 프로젝트 기간변경으로 인한 Budget 정보 초기화 여부
		if("Y".equals(param.getClearBdgt())) {
			bdgtMapper.deleteAllMemberBudget(param.getPrjtCd());
		}
		if("Y".equals(param.getReAprv())) {
			param.setStat("RG");
		}
		return prjtMapper.updateProject(param);
	}

	@Override
	public boolean deleteProject(Map<String, Object> param) throws Exception {
		// 상태값 변경
		int result = prjtMapper.deleteProjectStat(param);
		
		if(result == 1) return true;
		else return false;
	}
	@Override
	public boolean restoreProject(Map<String, Object> param) throws Exception {
		// 상태값 변경
		int result = prjtMapper.restoreProjectStat(param);
		
		if(result == 1) return true;
		else return false;
	}
	
	@Override
	public boolean deletePermanentlyProject(Map<String, Object> param, ProjectVersionVO versionParam) throws Exception {
		// History 저장
		histMapper.addHistVersion(versionParam);
		histMapper.addPrjtHist(versionParam);
		histMapper.addSubPrjtHist(versionParam);
		histMapper.addMembHist(versionParam);
		histMapper.addMembBdgtHist(versionParam);
		
		// Data 삭제
		prjtMapperV1.delMembBdgt(param);
		prjtMapperV1.delMemb(param);
		prjtMapperV1.delSubPrjt(param);
		prjtMapperV1.delPrjt(param);
		
		// Audit Dashboard에서 표준감사 관련 컬럼 초기화
		prjtMapperV1.initPrjtOfAuditDashboard(param);
		return true;
	}
}
