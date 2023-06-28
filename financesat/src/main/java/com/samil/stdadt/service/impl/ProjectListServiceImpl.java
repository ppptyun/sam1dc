package com.samil.stdadt.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;

import com.samil.stdadt.mapper.ProjectInfoMapper;
import com.samil.stdadt.mapper.ProjectListMapper;
import com.samil.stdadt.service.ProjectListService;
import com.samil.stdadt.vo.ProjectListV3VO;
import com.samil.stdadt.vo.ProjectListVO;

@Service
public class ProjectListServiceImpl implements ProjectListService {

	@Autowired
	ProjectInfoMapper prjtMapper;
	
	@Autowired
	ProjectListMapper prjtList;
	
	@Autowired
	DataSourceTransactionManager txManager;
	
	@Override
	public List<ProjectListVO> getProjectList(Map<String, Object> param) throws Exception {
		return prjtList.getProjectList(param);
	}

	@Override
	public List<Map<String, Object>> getProjectDraftList(Map<String, Object> param) throws Exception {
		return prjtList.getProjectDraftList(param);
	}
	@Override
	public int saveReCalculate(Map<String, Object> param) throws Exception {
		return prjtList.saveReCalculate(param) ;
	}

	@Override
	public List<String> getYear(List<String> formDiv) throws Exception {
		return prjtList.getYear(formDiv);
	}

	@Override
	public int delDraftPrjtList(Map<String, Object> param) throws Exception {
		prjtMapper.delSubPrjt(param);
		return prjtList.delDraftPrjtList(param);
	}

	@Override
	public List<ProjectListV3VO> getProjectListV3(Map<String, Object> param) throws Exception {
		return prjtList.getProjectListV3(param);
	}
}
