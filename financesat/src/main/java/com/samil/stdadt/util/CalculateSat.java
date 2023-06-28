package com.samil.stdadt.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.samil.stdadt.vo.ProjectInfoVO;
import com.samil.stdadt.vo.ResultVO;

public class CalculateSat {
	
	ProjectInfoVO prjt;
	
	Double baseWkmnsp = 1.0;	// 기준 숙련도
	double newStfWkmnsp = 0.0;	// New Staff 기준 숙련도
	double otherWkmnsp = 0.0;	// Other 기준 숙련도
	
	Double maxRatio = 1.0;		// 표준감사시간 최대 값 적용율
	Long baseSat = 0L;			// 그룹-산업별 기준 표준감사시간
	Double intplVal = 0.0;		// 보간 적용율
	Double factorVal = 1.0;	 	// 가감률
	double yearRate = 0;		// 적용 요율
	
	Long tmpCompSize = 0L;
	Integer intpCompSize = 0;
	Integer rskacnt = 0;
	
	public CalculateSat(ProjectInfoVO prjt) {
		this.prjt = prjt;
		this.tmpCompSize = (long) Math.floor((prjt.getCompSize() + (prjt.getIntrTranAssetSales()/2) - ((prjt.getSbsidAssetWithIntrTran() + prjt.getSbsidSalesWithIntrTran() + prjt.getRelatCompAsset())/2) )/100000000);
		this.rskacnt =  prjt.getConsoAsset()==0?0:Math.round((prjt.getConsoInvnt() + prjt.getConsoAccntReceiv()) * 100 / prjt.getConsoAsset());
		this.intpCompSize = getIntplCompSize(tmpCompSize);
	}
	
	public Map<String, Object> getParam(){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("satgrpCd", prjt.getSatgrpCd());
		param.put("indusCd", prjt.getIndusCd());
		param.put("prflId", prjt.getPrflId());
		param.put("sbsid", prjt.getSbsidCnt());
		param.put("year", prjt.getPrjtFrdt().substring(0, 4));
		param.put("intpCompSize", intpCompSize);
		param.put("rskacnt", rskacnt);
		return param;
	}
	
	public void setFactorVal(List<Map<String, Object>> factors) {
		this.factorVal = getCalcFactorVal(factors);
	}
	
	public ResultVO calculate() throws Exception {
		ResultVO result = new ResultVO();
		
		// Simulation Excel: 가감요인 고려전 감사시간
		Double tmpSat = baseSat + ((tmpCompSize-intpCompSize) * intplVal);
				
		// Excel: 산식에 따른 결과 (A)
		Double calSat = tmpSat * factorVal;
		
		// Excel: A * B
		Double calRateSat = calSat * yearRate;
		
		// Excel: 하한
		Double minSat = prjt.getPriorAdtTm();
		// Excel: 상한
		Double maxSat = minSat * maxRatio;
		// Excel: 2차 * 적용율, 상하한 적용 후 (C) == 양식: 산출된 감사시간(a)
		Double calSat2 = (minSat==0?calRateSat:minSat>calRateSat?minSat:"SATGRP11".equals(prjt.getSatgrpCd())?minSat:Math.min(calRateSat, maxSat));
		
		String intrAdtYn;
		Double intrAdtTm = 0.0;
		if("LISTED".equals(prjt.getListDvCd()) && prjt.getIndivAsset() >= 2000000000000L) {
			intrAdtYn = "Y";
			if("FIN_HLD".equals(prjt.getHoldingsDivCd()) || "NFIN_HLD".equals(prjt.getHoldingsDivCd())){
				intrAdtTm = calSat2 * 0.15;
			}else{
				intrAdtTm = calSat2 * 0.3;
			}
		}else {
			intrAdtYn = "N";
		}
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("intrAdtYn", intrAdtYn);
		data.put("intrAdtTm", intrAdtTm);
		data.put("factorVal", factorVal);
		data.put("calAdtTm", calSat2);
		data.put("calSat", calSat2 + intrAdtTm);
		data.put("baseWkmnsp", baseWkmnsp);
		data.put("newStfWkmnsp", newStfWkmnsp);
		data.put("otherWkmnsp", otherWkmnsp);
		
		result.setStatus(Constant.RESULT.SUCCESS);
		result.setData(data);
		
		return result;
	}
	
	private Integer getIntplCompSize(Long compSize) {
		Integer intplCompSize = 0;	// 보간 적용 기업 규모
		String satgrpCd = prjt.getSatgrpCd();
		
		if("SATGRP01".equals(satgrpCd)){
			intplCompSize = compSize>=3000000?3000000:compSize>=2000000?2000000:compSize>=1000000?1000000:compSize>=500000?500000:compSize>=200000?200000:compSize>=100000?100000:compSize>=50000?50000:0;
		}else if("SATGRP02".equals(satgrpCd)){
			intplCompSize = compSize>=40000?40000:compSize>=30000?30000:compSize>=20000?20000:compSize>=10000?10000:0;
		}else if("SATGRP03".equals(satgrpCd)){
			intplCompSize = compSize>=50000?50000:compSize>=20000?20000:compSize>=15000?15000:compSize>=10000?10000:compSize>=8000?8000:compSize>=5000?5000:compSize>=3000?3000:0;
		}else if("SATGRP04".equals(satgrpCd)){
			intplCompSize = compSize>=10000?10000:compSize>=5000?5000:compSize>=4000?4000:compSize>=3000?3000:compSize>=2000?2000:compSize>=1000?1000:compSize>=500?500:0;
		}else if("SATGRP05".equals(satgrpCd)){			
			intplCompSize = compSize>=2000?2000:compSize>=1500?1500:compSize>=1000?1000:compSize>=800?800:compSize>=500?500:compSize>=300?300:0;			
		}else if("SATGRP06".equals(satgrpCd)){
			intplCompSize = compSize>=1000?1000:compSize>=500?500:compSize>=400?400:compSize>=300?300:compSize>=200?200:compSize>=100?100:0;
		}else if("SATGRP07".equals(satgrpCd)){
			intplCompSize = compSize>=500000?500000:compSize>=100000?100000:compSize>=50000?50000:compSize>=10000?10000:compSize>=5000?5000:compSize>=1000?1000:compSize>=500?500:compSize>=200?200:compSize>=100?100:0;
		}else if("SATGRP08".equals(satgrpCd)){
			intplCompSize = compSize>=500000?500000:compSize>=100000?100000:compSize>=50000?50000:compSize>=20000?20000:compSize>=10000?10000:compSize>=5000?5000:compSize>=2000?2000:compSize>=1000?1000:compSize>=500?500:0;
		}else if("SATGRP09".equals(satgrpCd)){
			intplCompSize = compSize>=2000?2000:compSize>=1000?1000:compSize>=900?900:compSize>=800?800:compSize>=700?700:compSize>=600?600:compSize>=500?500:compSize>=200?200:0;
		}else if("SATGRP10".equals(satgrpCd)){
			intplCompSize = compSize>=1000?1000:compSize>=500?500:compSize>=400?400:compSize>=300?300:compSize>=200?200:compSize>=100?100:0;
		}else{
			intplCompSize = 0;
		}
		
		return intplCompSize;
	}
	
	private double getCalcFactorVal(List<Map<String, Object>> factors) {
		Double factorVal = 1.0;														// 가감률
		
		for(Map<String, Object> factor : factors) {
			String factorCd = (String) factor.get("factorCd");
			Double value = Double.parseDouble(String.valueOf(factor.get("val1")));
			
			switch(factorCd) {
			case "CONSO" : 
				if("Y".equals(prjt.getConsoFinstatYn())) {factorVal *= value;}
				break;
			case "FINHLD" : 
				if("FIN_HLD".equals(prjt.getHoldingsDivCd())) {factorVal *= value;}
				break;
			case "NFINHLD" : 
				if("NFIN_HLD".equals(prjt.getHoldingsDivCd())) {factorVal *= value;}
				break;
			case "USALST" : 
				if("Y".equals(prjt.getUsaListYn())) {factorVal *= value;}
				break;
			case "KAM" : 
				// 상장사이고, 개별자산이 1,000억 이상
				if("LISTED".equals(prjt.getListDvCd()) && prjt.getIndivAsset() >= 100000000000L) {factorVal *= value;}
				break;
			case "IFRS" :
				if("Y".equals(prjt.getIfrsYn())) {factorVal *= value;}
				break;
			case "LOSS" : 
				if("Y".equals(prjt.getCurrConsoLossYn())) {factorVal *= value;}
				break;
			case "ADTOPIN" :
				if("Y".equals(prjt.getCurrAdtopinCd())) {factorVal *= value;}
				break;
			case "FIRSTADT" :
				if("Y".equals(prjt.getFirstAdtYn())){
					if("SATGRP01".equals(prjt.getSatgrpCd())) {
						factorVal *= prjt.getFirstAdtFctr();
					}else {
						factorVal *= value;
					}
				}
				break;
			case "SBSID":
				factorVal *= value;
				break;
			case "RSKACNT":
				factorVal *= value;
				break;
			default:
			}
		}
		return factorVal;
	}
	
	public void setBaseWkmnsp(Double baseWkmnsp) {
		this.baseWkmnsp = baseWkmnsp;
	}

	public void setMaxRatio(Double maxRatio) {
		this.maxRatio = maxRatio;
	}

	public void setBaseSat(Long baseSat) {
		this.baseSat = baseSat;
	}

	public void setIntplVal(Double intplVal) {
		this.intplVal = intplVal;
	}

	public void setYearRate(double yearRate) {
		this.yearRate = yearRate;
	}

	public Double getFactorVal() {
		return factorVal;
	}

	public void setFactorVal(Double factorVal) {
		this.factorVal = factorVal;
	}

	public double getNewStfWkmnsp() {
		return newStfWkmnsp;
	}

	public double getOtherWkmnsp() {
		return otherWkmnsp;
	}

	public void setNewStfWkmnsp(double newStfWkmnsp) {
		this.newStfWkmnsp = newStfWkmnsp;
	}

	public void setOtherWkmnsp(double otherWkmnsp) {
		this.otherWkmnsp = otherWkmnsp;
	}

	
}
