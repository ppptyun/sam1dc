package com.samil.stdadt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samil.stdadt.mapper.ProjectBudgetMapper;
import com.samil.stdadt.mapper.ProjectInfoMapper;
import com.samil.stdadt.retain.vo.RetainVO;
import com.samil.stdadt.service.ProjectBudgetService;
import com.samil.stdadt.util.Constant;
import com.samil.stdadt.vo.ProjectBudgetMemberVO;
import com.samil.stdadt.vo.ProjectBudgetSummaryVO;
import com.samil.stdadt.vo.ResultVO;
import com.samil.stdadt.vo.WeekVO;

@Service
public class ProjectBudgetServiceImpl implements ProjectBudgetService {
	
	Logger logger = LoggerFactory.getLogger(ProjectBudgetServiceImpl.class);

	@Autowired
	ProjectInfoMapper prjtMapper;
	
	@Autowired
	ProjectBudgetMapper bdgtMapper;
	
	@Override
	public ProjectBudgetSummaryVO getBudgetSummary(Map<String, Object> param) throws Exception {
		// 프로젝트 Summary 정보 가져오기
		ProjectBudgetSummaryVO summary = bdgtMapper.getProjectBudgetSummary(param);
		if(summary == null) return null;
		param.put("prjtFrdt", summary.getPrjtFrdt().replaceAll("-", ""));
		param.put("prjtTodt", summary.getPrjtTodt().replaceAll("-", ""));
		
		// 프로젝트 기간별 주 시작일 가져오기(Grid의 컬럼 정보 생성을 위함)
		List<WeekVO> weeks = this.getBudgetWeek(param);
		if(weeks != null) summary.setsDtOfWeek(weeks);
		
		return summary;
	}
	
	@Override
	public List<WeekVO> getBudgetWeek(Map<String, Object> param) throws Exception{
		Map<String, Object> weekParam = new HashMap<String, Object>();
		if(param.get("prjtFrdt") == null || param.get("prjtTodt") == null) {
			Map<String, Object> prjtInfo = prjtMapper.getPrjtInfoSimple(param);
			weekParam.put("prjtFrdt", prjtInfo.get("prjtFrdt"));
			weekParam.put("prjtTodt", prjtInfo.get("prjtTodt"));	
		}else {
			weekParam = param;
		}
		weekParam.put("maxWeekNum", Constant.maxWeekNum);
		return bdgtMapper.getWeekInfo(weekParam);
	}


	@Override
	public List<ProjectBudgetMemberVO> getBudgetList(Map<String, Object> param) throws Exception {
		List<WeekVO> weeks = this.getBudgetWeek(param);
		param.put("weeks", weeks);
		return bdgtMapper.getProjectBudgetMember(param);
	}


	@Override
	public ResultVO saveMemberBudget(Map<String, Object> param) throws Exception {
		ResultVO result = new ResultVO();
		
		// 프로젝트 정보에서 New Staff, Other Budget 부분 저장
		bdgtMapper.updateBdgt(param);
		
		// 멤버, 멤버별 Budget 정보 초기화
		bdgtMapper.deleteAllMemberBudget(param);
		bdgtMapper.deleteAllMember(param);
		
		// Member 정보 저장
		List<Map<String, Object>> members = (List<Map<String, Object>>) param.get("list");
		if(members.size() > 0) {				
			bdgtMapper.inertMember(param);
			
			// Member Budget 저장
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();			
			for(Map<String, Object> member : members) {
				
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> bdgts = (List<Map<String, Object>>)  member.get("list");
				for(Map<String, Object> bdgt : bdgts) {
					list.add(new HashMap<String, Object>(){
						private static final long serialVersionUID = -2082442897453073455L;
					{
						put("membEmplNo", member.get("membEmplNo"));
						put("actvCd", member.get("actvCd"));
						put("locaCd", member.get("locaCd"));
						put("weekFrdt", bdgt.get("weekFrdt"));
						put("asgnTm", bdgt.get("asgnTm"));
					}});
				}
			}
			
			if(list.size() > 0) {					
				Map<String, Object> param2 = new HashMap<String, Object>();
				param2.put("prjtCd", param.get("prjtCd"));
				param2.put(Constant.PARAM_NAME.SESSION, param.get(Constant.PARAM_NAME.SESSION));
				param2.put("list", list);
				bdgtMapper.inertMemberBudget(param2);
			}
		}
		
		result.setStatus(Constant.RESULT.SUCCESS);
		return result;
	}


	@Override
	public List<Map<String, Object>> getCMByGradCd(Map<String, Object> param) throws Exception {
		return bdgtMapper.getCMByGradCd(param);
	}


	@Override
	public boolean reqELAprv(Map<String, Object> param) throws Exception {
		param.put("stat", "RQ");
		int result = prjtMapper.updateStatus(param);
		
		if(result == 1) return true;
		else return false;
	}

	@Override
	public int saveRetainSchedule(Map<String, Object> param) throws Exception {
		return bdgtMapper.saveRetainSchedule(param);
	}

}
