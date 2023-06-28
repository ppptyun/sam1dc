package com.samil.stdadt.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samil.stdadt.comm.vo.ApplicationConfigVO;
import com.samil.stdadt.mapper.PopupMapper;
import com.samil.stdadt.service.PopupService;
import com.samil.stdadt.vo.ConflictListVO;
import com.samil.stdadt.vo.ProfitabilityV3VO;
import com.samil.stdadt.vo.ProfitabilityVO;

@Service
public class PopupServiceImpl implements PopupService {

	@Autowired
	PopupMapper popMapper;
	
	@Autowired
	ApplicationConfigVO appConfig;

	@Override
	public List<Map<String, Object>> getProject(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return popMapper.getProject(param);
	}

	@Override
	public List<Map<String, Object>> getProjectV3(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return popMapper.getProjectV3(param);
	}
	
	@Override
	public List<Map<String, Object>> getEmp(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return popMapper.getEmp(param);
	}

	@Override
	public ProfitabilityVO getProfit(Map<String, Object> param) throws Exception {
		
		ProfitabilityVO profit = popMapper.getProfitProject(param);
		
		if(profit != null) {
			List<Map<String, Object>> list = popMapper.getProfitMember(profit);
			if(list != null) {				
				for(Map<String, Object> data : list) {
					String div = data.get("div").toString();
					
					switch(div) {
						case "bdgtP": 
							profit.setBdgtP(Double.parseDouble(data.get("totAsgnTm").toString()));
							profit.setRateP(Long.parseLong(data.get("rates").toString()));
							break;
						case "bdgtD": 
							profit.setBdgtD(Double.parseDouble(data.get("totAsgnTm").toString()));
							profit.setRateD(Long.parseLong(data.get("rates").toString()));
							break;
						case "bdgtSm": 
							profit.setBdgtSm(Double.parseDouble(data.get("totAsgnTm").toString()));
							profit.setRateSm(Long.parseLong(data.get("rates").toString()));
							break;
						case "bdgtM": 
							profit.setBdgtM(Double.parseDouble(data.get("totAsgnTm").toString()));
							profit.setRateM(Long.parseLong(data.get("rates").toString()));
							break;
						case "bdgtSa": 
							profit.setBdgtSa(Double.parseDouble(data.get("totAsgnTm").toString()));
							profit.setRateSa(Long.parseLong(data.get("rates").toString()));
							break;
						case "bdgtA": 
							profit.setBdgtA(Double.parseDouble(data.get("totAsgnTm").toString()));
							profit.setRateA(Long.parseLong(data.get("rates").toString()));
							break;
					}
				}
			}
			profit.calculate();
		}
		
		
		// TODO Auto-generated method stub
		return profit;
	}

	@Override
	public ProfitabilityV3VO getProfitV3(Map<String, Object> param) throws Exception {
		
		ProfitabilityV3VO profit = popMapper.getProfitProjectV3(param);
		profit.setAppCd(appConfig.getAppCd());
		
		if(profit != null) {
			List<Map<String, Object>> list = popMapper.getProfitMemberV3(profit);
			if(list != null) {				
				for(Map<String, Object> data : list) {
					String div = data.get("div").toString();
					
					switch(div) {
						case "qc":
							profit.setQcBdgtTm(Double.parseDouble(data.get("totAsgnTm").toString()));
							profit.setRateQc(Long.parseLong(data.get("rates").toString()));
							break;
						case "qrp":
							profit.setQrpBdgtTm(Double.parseDouble(data.get("totAsgnTm").toString()));
							profit.setRateQrp(Long.parseLong(data.get("rates").toString()));
							break;
						case "fulcrum":
							profit.setFlcmBdgtTm(Double.parseDouble(data.get("totAsgnTm").toString()));
							profit.setRateFlcm(Long.parseLong(data.get("rates").toString()));
							break;
						case "ra":
							profit.setRaBdgtTm(Double.parseDouble(data.get("totAsgnTm").toString()));
							profit.setRateRa(Long.parseLong(data.get("rates").toString()));
							break;
						case "newstaff":
							profit.setNewStfBdgtTm(Double.parseDouble(data.get("totAsgnTm").toString()));
							profit.setRateNewStf(Long.parseLong(data.get("rates").toString()));
							break;
						case "other":
							profit.setOtherBdgtTm(Double.parseDouble(data.get("totAsgnTm").toString()));
							profit.setRateOther(Long.parseLong(data.get("rates").toString()));
							break;
						case "bdgtP": 
							profit.setBdgtP(Double.parseDouble(data.get("totAsgnTm").toString()));
							profit.setRateP(Long.parseLong(data.get("rates").toString()));
							break;
						case "bdgtD": 
							profit.setBdgtD(Double.parseDouble(data.get("totAsgnTm").toString()));
							profit.setRateD(Long.parseLong(data.get("rates").toString()));
							break;
						case "bdgtSm": 
							profit.setBdgtSm(Double.parseDouble(data.get("totAsgnTm").toString()));
							profit.setRateSm(Long.parseLong(data.get("rates").toString()));
							break;
						case "bdgtM": 
							profit.setBdgtM(Double.parseDouble(data.get("totAsgnTm").toString()));
							profit.setRateM(Long.parseLong(data.get("rates").toString()));
							break;
						case "bdgtSa": 
							profit.setBdgtSa(Double.parseDouble(data.get("totAsgnTm").toString()));
							profit.setRateSa(Long.parseLong(data.get("rates").toString()));
							break;
						case "bdgtA": 
							profit.setBdgtA(Double.parseDouble(data.get("totAsgnTm").toString()));
							profit.setRateA(Long.parseLong(data.get("rates").toString()));
							break;
					}
				}
			}
			profit.calculate();
		}
		
		
		// TODO Auto-generated method stub
		return profit;
	}
	
	@Override
	public Map<String, Object> getBdgtHist(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ConflictListVO> getBudgetConflict(Map<String, Object> param) throws Exception {
		return popMapper.getBudgetConflict(param);
	}

	@Override
	public String rejectReasonRead(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		String result = popMapper.rejectReasonRead(param);
		return result;
	}

	@Override
	public List<Map<String, Object>> getAuditorList(Map<String, Object> param) throws Exception {
		return popMapper.getAuditorList(param);
	}

	@Override
	public int addAuditor(Map<String, Object> param) throws Exception {
		return popMapper.addAuditor(param); 
	}

	@Override
	public Boolean isExistAuditor(Map<String, Object> param) throws Exception {
		return popMapper.isExistAuditor(param); 
	}
}
