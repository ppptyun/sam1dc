package com.samil.stdadt.service.v3.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samil.stdadt.comm.service.CommCodeService;
import com.samil.stdadt.comm.vo.CommCodeVO;
import com.samil.stdadt.mapper.ProjectBudgetV3Mapper;
import com.samil.stdadt.mapper.ProjectInfoMapper;
import com.samil.stdadt.service.v3.ProjectBudgetV3Service;
import com.samil.stdadt.util.Constant;
import com.samil.stdadt.vo.ProjectBudgetCisV3VO;
import com.samil.stdadt.vo.ProjectBudgetMemberV3VO;
import com.samil.stdadt.vo.ProjectBudgetSummaryV3VO;
import com.samil.stdadt.vo.ProjectBudgetSummaryVO;
import com.samil.stdadt.vo.ResultVO;
import com.samil.stdadt.vo.WeekVO;

@Service
public class ProjectBudgetV3ServiceImpl implements ProjectBudgetV3Service {
	
	Logger logger = LoggerFactory.getLogger(ProjectBudgetV3ServiceImpl.class);

	@Autowired
	ProjectInfoMapper prjtMapper;
	
	@Autowired
	ProjectBudgetV3Mapper bdgtMapper;
	
	@Autowired
	CommCodeService commService;
	
	@Override
	public ProjectBudgetSummaryV3VO getBudgetSummary(Map<String, Object> param) throws Exception {
		// 프로젝트 Summary 정보 가져오기
		ProjectBudgetSummaryV3VO summary = bdgtMapper.getProjectBudgetV3Summary(param);
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
	public List<ProjectBudgetMemberV3VO> getBudgetList(Map<String, Object> param) throws Exception {
		List<WeekVO> weeks = this.getBudgetWeek(param);
		param.put("weeks", weeks);
		return bdgtMapper.getProjectBudgetMember(param);
	}


	@Override
	public ResultVO saveMemberBudget(Map<String, Object> param) throws Exception {
		ResultVO result = new ResultVO();
		
		// 결재 완료된 것을 수정하면 등록상태로 변경한다.
		if("CO".equals(param.get("stat"))) {
			Map<String, Object> statusParam = new HashMap<String, Object>();
			statusParam.put("prjtCd", param.get("prjtCd"));
			statusParam.put("stat", "RG");
			statusParam.put("session", param.get("session"));
			prjtMapper.updateStatus(statusParam);
		}
		
		List<Map<String, Object>> dftBdgtRoleConfig = bdgtMapper.selectDftBdgtRoleConfig(param); // role정보
		Map<String, String> roleGradCd = new HashMap<String, String>();
		for(int i=0; i<dftBdgtRoleConfig.size(); i++) {
			roleGradCd.put( (String) dftBdgtRoleConfig.get(i).get("cd"), (String) dftBdgtRoleConfig.get(i).get("gradcd"));
		}

		// Cis 그리드 정보 업데이트
		List<Map<String, Object>> cisList = (List<Map<String, Object>>) param.get("cislist");
		if (cisList.size() > 0) {
			for (Map<String, Object> cisData : cisList) {
				cisData.put(Constant.PARAM_NAME.SESSION, param.get(Constant.PARAM_NAME.SESSION));

				String div = (String) cisData.get("div");
				if ("main".equals(div)) {
					bdgtMapper.updateCisMainBudget(cisData);
				} else {
					cisData.put("p_prjtCd", param.get("prjtCd"));
					String prjtCd = cisData.get("prjtcd").toString();
					String[] prjtCds = prjtCd.split("-");
					cisData.put("prjtCd1", prjtCds[0]);
					cisData.put("prjtCd2", prjtCds[1]);
					cisData.put("prjtCd3", prjtCds[2]);
					bdgtMapper.mergeCisSubBudget(cisData);
				}
			}
		}
		
		// 멤버, 멤버별 Budget 정보 초기화
		bdgtMapper.deleteAllMemberBudget((String) param.get("prjtCd"));
		bdgtMapper.deleteAllMember((String) param.get("prjtCd"));
		
		// Member 정보 저장
		List<Map<String, Object>> members = (List<Map<String, Object>>) param.get("list");
		
		if(members.size() > 0) {
			bdgtMapper.inertMember(param);	// Member 저장
			
			List<Map<String, Object>> bdgtList = new ArrayList<Map<String, Object>>(); // Member Budget 저장
			
			for(Map<String, Object> member : members) {
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> bdgts = (List<Map<String, Object>>)  member.get("list");
				for(Map<String, Object> bdgt : bdgts) {
					bdgtList.add(new HashMap<String, Object>(){
						private static final long serialVersionUID = -2082442897453073455L;
					{
						// 2020-05-17 추가
						put("prjtcd", member.get("prjtcd"));
						put("membEmplNo", member.get("membEmplNo"));
						put("actvCd", member.get("actvCd"));
						put("locaCd", member.get("locaCd"));
						put("weekFrdt", bdgt.get("weekFrdt"));
						put("asgnTm", bdgt.get("asgnTm"));
					}});
				}
			}
			
			if(bdgtList.size() > 0) {					
				Map<String, Object> param2 = new HashMap<String, Object>();
				param2.put(Constant.PARAM_NAME.SESSION, param.get(Constant.PARAM_NAME.SESSION));
				param2.put("prjtCd", param.get("prjtCd"));
				param2.put("list", bdgtList);
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
	
	//--------------------------------------------------
	// 20200428 추가
	//--------------------------------------------------
	@Override
	public List<ProjectBudgetCisV3VO> getBudgetCisList(Map<String, Object> param) throws Exception {
		return bdgtMapper.getProjectBudgetCis(param);
	}
	
	@Override
	public ProjectBudgetSummaryVO getProjectFormInfo(Map<String, Object> param) throws Exception {
		// 프로젝트 Form 버전 정보 가져오기
		return bdgtMapper.getProjectFormInfo(param);
	}

	@Override
	public List<Map<String, Object>> checkDuplPrjt(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return bdgtMapper.checkDuplPrjt(param);
	}

	@Override
	public List<Map<String, Object>> selectDftBdgtRoleConfig(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return bdgtMapper.selectDftBdgtRoleConfig(param);
	}
	
	
}
