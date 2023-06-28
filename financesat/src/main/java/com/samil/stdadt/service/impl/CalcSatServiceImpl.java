package com.samil.stdadt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samil.stdadt.dto.ProjectInfoV3Dto;
import com.samil.stdadt.mapper.CalcSatMapper;
import com.samil.stdadt.service.CalcSatService;
import com.samil.stdadt.vo.ProjectInfoV3VO;

@Service
public class CalcSatServiceImpl implements CalcSatService {
	
	@Autowired
	CalcSatMapper calcSatMapper;

	@Override
	public List<Map<String, Object>> getCalcSatList(Map<String, Object> param) throws Exception {
		return calcSatMapper.getCalcSatList(param);
	}

	@Override
	public List<ProjectInfoV3VO> getCalcSatInfo(Map<String, Object> param) throws Exception {
		return calcSatMapper.getCalcSatInfo(param);
	}

	@Override
	public int addCalcSatInfo(ProjectInfoV3Dto param) throws Exception {
		return calcSatMapper.addCalcSatInfo(param);
	}

	@Override
	public int updateCalcSatInfo(ProjectInfoV3Dto param) throws Exception {
		return calcSatMapper.updateCalcSatInfo(param);
	}

	@Override
	public Map<String, Object> getFactorValue(Map<String, Object> param) throws Exception {
		return calcSatMapper.getFactorValue(param);
	}
	
	@Override
	public Map<String, Object> getFactorValue(String prflId, String satgrpCd, String factorCd, String grpFactorDiv) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("prflId", prflId);
		param.put("satgrpCd", satgrpCd);
		param.put("factorCd", factorCd);
		param.put("grpFactorDiv", grpFactorDiv);
		return calcSatMapper.getFactorValue(param);
	}

	@Override
	public List<Map<String, Object>> getSatgrpList(Map<String, Object> param) throws Exception {
		return calcSatMapper.getSatgrpList(param);
	}

	@Override
	public Map<String, Object> getGrpPrflInfo(Map<String, Object> param) throws Exception {
		return calcSatMapper.getGrpPrflInfo(param);
	}

	@Override
	public int deleteCalcSatInfo(Map<String, Object> param) throws Exception {
		return calcSatMapper.deleteCalcSatInfo(param);
	}

	@Override
	public Map<String, Object> getGrpIndusPrfl(Map<String, Object> param) throws Exception {
		return calcSatMapper.getGrpIndusPrfl(param);
	}

	@Override
	public Map<String, Object> getGrpYearPrfl(Map<String, Object> param) throws Exception {
		return calcSatMapper.getGrpYearPrfl(param);
	}

	@Override
	public Double getIntrAdtRatio(Map<String, Object> param) throws Exception {
		return calcSatMapper.getIntrAdtRatio(param);
	}

	@Override
	public Map<String, Object> getCalcSatMetaInfo(Map<String, Object> param) throws Exception {
		return calcSatMapper.getCalcSatMetaInfo(param);
	}

	@Override
	public List<String> getYear() throws Exception {
		return calcSatMapper.getYear();
	}
}
