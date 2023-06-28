package com.samil.stdadt.service.v2.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samil.stdadt.dto.ProjectInfoV2Dto;
import com.samil.stdadt.dto.SubProjectV2Dto;
import com.samil.stdadt.mapper.HistoryMapper;
import com.samil.stdadt.mapper.ProjectInfoMapper;
import com.samil.stdadt.mapper.ProjectInfoV2Mapper;
import com.samil.stdadt.service.v2.ProjectInfoV2Service;
import com.samil.stdadt.vo.ProjectInfoV2VO;
import com.samil.stdadt.vo.ProjectVersionVO;
import com.samil.stdadt.vo.SubProjectVO;

@Service
public class ProjectInfoV2ServiceImpl implements ProjectInfoV2Service{
	
	@Autowired
	HistoryMapper histMapper;
	
	@Autowired
	ProjectInfoMapper prjtMapperV1;
	
	@Autowired
	ProjectInfoV2Mapper prjtMapper;
	
	@Override
	public List<ProjectInfoV2VO> getProjectInfoList(Map<String, Object> param) throws Exception {
		return prjtMapper.getProjectInfo(param);
	}

	@Override
	public int addProject(ProjectInfoV2Dto param) throws Exception {
		prjtMapper.delSubProject(param);
		if(param.getSubPrjt().size()>0) prjtMapper.addSubProject(param);
		return prjtMapper.addProject(param);
	}

	@Override
	public int updateProject(ProjectInfoV2Dto param) throws Exception {
		prjtMapper.delSubProject(param);
		if(param.getSubPrjt().size()>0) prjtMapper.addSubProject(param);
		return prjtMapper.updateProject(param);
	}

	@Override
	public List<SubProjectV2Dto> getSubProjectList(ProjectInfoV2Dto param) throws Exception {
		List<SubProjectV2Dto> ret = new ArrayList<SubProjectV2Dto>();
		List<SubProjectVO> list = prjtMapper.getSubProject(param);
		for(SubProjectVO subPrjt : list) {
			SubProjectV2Dto item = new SubProjectV2Dto();
			item.init(subPrjt);
			ret.add(item);
		}
		return ret;
	}

	@Override
	public boolean deleteProject(Map<String, Object> param, ProjectVersionVO versionParam) throws Exception {
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
