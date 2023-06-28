package com.samil.stdadt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samil.stdadt.mapper.HistoryMapper;
import com.samil.stdadt.mapper.ProjectBudgetMapper;
import com.samil.stdadt.service.HistoryService;
import com.samil.stdadt.util.Constant;
import com.samil.stdadt.vo.ProjectBudgetMemberVO;
import com.samil.stdadt.vo.ProjectBudgetSummaryVO;
import com.samil.stdadt.vo.ProjectVersionVO;
import com.samil.stdadt.vo.WeekVO;

@Service
public class HisotryServiceImpl implements HistoryService {
	
	@Autowired
	HistoryMapper histMapper;
	
	
	@Autowired
	ProjectBudgetMapper bdgtMapper;
	
	@Override
	public boolean addHistory(ProjectVersionVO param) throws Exception {
		histMapper.addHistVersion(param);	// selectKey로 version 값 생성
		histMapper.addPrjtHist(param);
		histMapper.addSubPrjtHist(param);
		histMapper.addMembHist(param);
		histMapper.addMembBdgtHist(param);
		return true;
	}
	
	@Override
	public boolean delHistory(ProjectVersionVO param) throws Exception {
		histMapper.delMembBdgtHist(param);
		histMapper.delMembHist(param);
		histMapper.delSubPrjtHist(param);		
		histMapper.delPrjtHist(param);
		histMapper.delHistVersion(param);
		return true;
	}

	
	@Override
	public Map<String, Object> getVersionAprvComplete(ProjectVersionVO param) throws Exception {
		return histMapper.getVersionAprvComplete(param);
	}
	
	
	@Override
	public List<WeekVO> getBudgetWeekHist(ProjectVersionVO param) throws Exception{
		Map<String, Object> prjtInfo = histMapper.getPrjtInfoSimpleHist(param);
		Map<String, Object> weekParam = new HashMap<String, Object>();
		weekParam.put("prjtFrdt", prjtInfo.get("prjtFrdt"));
		weekParam.put("prjtTodt", prjtInfo.get("prjtTodt"));
		weekParam.put("maxWeekNum", Constant.maxWeekNum);
		
		return bdgtMapper.getWeekInfo(weekParam);
	}
	
	
	@Override
	public ProjectBudgetSummaryVO getProjectBudgetSummaryHist(ProjectVersionVO param) throws Exception {
		ProjectBudgetSummaryVO summary = histMapper.getProjectBudgetSummaryHist(param);
		// 프로젝트 기간별 주 시작일 가져오기(Grid의 컬럼 정보 생성을 위함) 
		List<WeekVO> weeks = this.getBudgetWeekHist(param);
		if(weeks != null) summary.setsDtOfWeek(weeks);
				
		return summary;
	}

	@Override
	public List<ProjectBudgetMemberVO> getProjectBudgetMemberHist(ProjectVersionVO param) throws Exception {
		// TODO Auto-generated method stub
		List<WeekVO> weeks = this.getBudgetWeekHist(param);
		param.setWeeks(weeks);
		return histMapper.getProjectBudgetMemberHist(param);
	}	
}
