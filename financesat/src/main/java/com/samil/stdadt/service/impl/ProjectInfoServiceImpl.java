package com.samil.stdadt.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samil.stdadt.mapper.HistoryMapper;
import com.samil.stdadt.mapper.ProjectBudgetMapper;
import com.samil.stdadt.mapper.ProjectInfoMapper;
import com.samil.stdadt.mapper.ProjectListMapper;
import com.samil.stdadt.service.ProjectInfoService;
import com.samil.stdadt.util.Constant;
import com.samil.stdadt.vo.ProjectInfoVO;
import com.samil.stdadt.vo.ProjectVersionVO;
import com.samil.stdadt.vo.SubProjectVO;

@Service
public class ProjectInfoServiceImpl implements ProjectInfoService {

	@Autowired
	HistoryMapper histMapper;
	
	@Autowired
	ProjectInfoMapper prjtInfoMapper;
	
	@Autowired
	ProjectListMapper prjtListMapper;
	
	@Autowired
	ProjectBudgetMapper bdgtMapper;
	
	@Override
	public ProjectInfoVO getProjectInfo(Map<String, Object> param) throws Exception {
		// 프로젝트 기본 정보 가져오기
		ProjectInfoVO prjt = null;
		
		List<ProjectInfoVO> prjtList = prjtInfoMapper.getProjectInfo(param);
		
		// 합산 대상 프로젝트 추가
		if(prjtList != null) {
			prjt = prjtList.get(0);
			List<SubProjectVO> prjtAdd = prjtInfoMapper.getSubProject(param);
			for(SubProjectVO tmp: prjtAdd) prjt.addPrjtAdd(tmp);
		}
		return prjt;
	}
	
	@Override
	public List<ProjectInfoVO> getProjectInfoList(Map<String, Object> param) throws Exception {
		// 프로젝트 기본 정보 가져오기
		List<ProjectInfoVO> prjtList = prjtInfoMapper.getProjectInfo(param);
		
		// 합산 대상 프로젝트 추가 - 표준감사시간 재계산을 위한것이면 불필요한 작업임.
		/*if(prjtList != null) {
			for(ProjectInfoVO prjt: prjtList) {				
				List<SubProjectVO> prjtAdd = prjtInfoMapper.getSubProject(param);
				for(SubProjectVO tmp: prjtAdd) prjt.addPrjtAdd(tmp);
			}
		}*/
		return prjtList;
	}
	
	@Override
	public List<SubProjectVO> getSubProject(Map<String, Object> param) throws Exception {
		return prjtInfoMapper.getSubProject(param);
	}
	
	@Override
	public List<Map<String, Object>> getFactorVal(Map<String, Object> param) throws Exception {
		return prjtInfoMapper.getFactorVal(param);
	}
	@Override
	public Map<String, List<Map<String, Object>>> getSelOption(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		List<Map<String, Object>> options = prjtInfoMapper.getSelOption(param);
		Map<String, List<Map<String, Object>>> retVal = new HashMap<String, List<Map<String, Object>>>();
		if(options != null) {
			for(int i=0; i<options.size(); i++) {
				String key = (String) options.get(i).get("grpCd");
				Map<String, Object> tmpOption = new HashMap<String, Object>();
				if(!retVal.containsKey(key)) {
					List<Map<String, Object>> tmpList = new ArrayList<Map<String, Object>>();
					tmpOption.put("cd", options.get(i).get("cd"));
					tmpOption.put("nm", options.get(i).get("nm"));
					tmpList.add(tmpOption);
					
					retVal.put(key, tmpList);
				}else {
					tmpOption.put("cd", options.get(i).get("cd"));
					tmpOption.put("nm", options.get(i).get("nm"));
					retVal.get(key).add(tmpOption);
				}
			}
		}
		return retVal;
	}
	
	@Override
	public Map<String, Object> getTooltip(Map<String, Object> param) throws Exception {
		Map<String, Object> data = prjtInfoMapper.getToolTipColumnStr(param);
		param.put("columnStr", data.get("colStr"));
		return prjtInfoMapper.getTooltip(param); 
	}
	
	@Override
	public int getProfileId() throws Exception {
		Map<String, Object> data = prjtInfoMapper.getProfileId();
		return ((BigDecimal) data.get("prflId")).intValue();
	}
	@Override
	public Map<String, Object> getInterpolation(Map<String, Object> param) throws Exception {
		return prjtInfoMapper.getInterpolation(param);
	}
	@Override
	public Map<String, Object> getSatgrpInfo(Map<String, Object> param) throws Exception {
		return prjtInfoMapper.getSatgrpInfo(param);
	}
	@Override
	public double getYearRate(Map<String, Object> param) throws Exception {
		Map<String, Object> data = prjtInfoMapper.getYearRate(param);
		if(data == null) {
			return -1;
		}else {			
			return ((BigDecimal) data.get("aplyRate")).doubleValue();
		}
	}
	
	@Override
	public int addProjectInfo(Map<String, Object> param) throws Exception {
		int result = 0;
		Map<String, Object> chkPrjt = prjtInfoMapper.checkExistProject(param);
		
		if(chkPrjt == null) {
			// 프로젝트 정보 저장
			result = prjtInfoMapper.addProjectInfo(param);
			
			// Sub 프로젝트 저장
			if(param.get("subPrjt") != null) {
				List<Map<String, Object>> subPrjtList = (List<Map<String, Object>>) param.get("subPrjt");
				for(int i=0; i<subPrjtList.size(); i++) {
					String prjtCd = StringUtils.defaultIfBlank((String) subPrjtList.get(i).get("prjtCd"), "");					
					if(!prjtCd.equals("")){
						subPrjtList.get(i).put("pPrjtCd", param.get("prjtCd"));
						subPrjtList.get(i).put(Constant.PARAM_NAME.SESSION, param.get(Constant.PARAM_NAME.SESSION));
						subPrjtList.get(i).put("ordby", i);
						prjtInfoMapper.addSubProject(subPrjtList.get(i));
					}
				}
 			}
			
		}
		
		return result;
	}
	
	@Override
	public int updateProjectInfo(Map<String, Object> param) throws Exception {
		
		// 프로젝트 기간변경으로 인한 Budget 정보 초기화 여부
		if("Y".equals(param.get("clearBdgt"))) bdgtMapper.deleteAllMemberBudget(param);
		// 결재완료된 프로젝트 정보를 수정할 경우, 재승인 받기 위함. ==> stat을 등록상태로 변경 함.
		if("Y".equals(param.get("reAprv"))) param.put("stat", "RG"); 
		
		
		// 프로젝트 정보 저장
		int result = prjtInfoMapper.updateProjectInfo(param);
		
		// Sub 프로젝트 변경사항이 있을 경우 저장
		if("Y".equals(param.get("isUpdatedSubPrjt"))) {
			// 기 등록된 Sub Project 모두 삭제
			prjtInfoMapper.delSubPrjt(param);

			// 재입력
			if(param.get("subPrjt") != null) {
				List<Map<String, Object>> subPrjtList = (List<Map<String, Object>>) param.get("subPrjt");
				for(int i=0; i<subPrjtList.size(); i++) {
					String prjtCd = StringUtils.defaultIfBlank((String) subPrjtList.get(i).get("prjtCd"), "");					
					if(!prjtCd.equals("")){
						subPrjtList.get(i).put("pPrjtCd", param.get("prjtCd"));
						subPrjtList.get(i).put(Constant.PARAM_NAME.SESSION, param.get(Constant.PARAM_NAME.SESSION));
						subPrjtList.get(i).put("ordby", i);
						prjtInfoMapper.addSubProject(subPrjtList.get(i));
					}
				}
			}
		}
		return result; 
	}
	
	@Override
	public boolean delPrjt(Map<String, Object> param, ProjectVersionVO versionParam) throws Exception {
		// History 저장
		histMapper.addHistVersion(versionParam);
		histMapper.addPrjtHist(versionParam);
		histMapper.addSubPrjtHist(versionParam);
		histMapper.addMembHist(versionParam);
		histMapper.addMembBdgtHist(versionParam);
		
		// Data 삭제
		prjtInfoMapper.delMembBdgt(param);
		prjtInfoMapper.delMemb(param);
		prjtInfoMapper.delSubPrjt(param);
		prjtInfoMapper.delPrjt(param);
		
		// Audit Dashboard에서 표준감사 관련 컬럼 초기화
		prjtInfoMapper.initPrjtOfAuditDashboard(param);
		
		return true;
	}
	
	@Override
	public Map<String, Object> getPrjtMetaInfo(Map<String, Object> param) throws Exception {
		Map<String, Object> tmp = prjtInfoMapper.getPrjtMetaInfo(param);
		if(tmp != null) {
			tmp.put("IS_El", "1".equals(tmp.get("isEl")));
			tmp.put("CAN_EDIT", "1".equals(tmp.get("canEdit")));
			tmp.put("CAN_DELETE", "1".equals(tmp.get("canDelete")));
			tmp.put("CAN_RESTORE", "1".equals(tmp.get("canRestore")));
			tmp.put("CAN_DELETE_PERMANENTLY", "1".equals(tmp.get("canDeletePermanently")));
		}
		return tmp;
	}

	@Override
	public Map<String, Object> getPrjtInfoSimple(Map<String, Object> param) throws Exception {
		return prjtInfoMapper.getPrjtInfoSimple(param);
	}
	@Override
	public List<Map<String, Object>> getSatgrpList(Map<String, Object> param) throws Exception {
		return prjtInfoMapper.getSatgrpList(param);
	}
	@Override
	public List<Map<String, Object>> getSatgrpListByPrjtCd(Map<String, Object> param) throws Exception {
		return prjtInfoMapper.getSatgrpListByPrjtCd(param);
	}
	@Override
	public Map<String, Object> getWkmnsp(String appCd) throws Exception {
		return prjtInfoMapper.getWkmnsp(appCd);
	}

	

}
