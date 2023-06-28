package com.samil.stdadt.service.v4.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samil.stdadt.dto.ProjectInfoV4Dto;
import com.samil.stdadt.mapper.CalcSatV4Mapper;
import com.samil.stdadt.service.v4.CalcSatV4Service;
import com.samil.stdadt.vo.ProjectInfoV4VO;

@Service
public class CalcSatV4ServiceImpl implements CalcSatV4Service {
	
	@Autowired
	CalcSatV4Mapper calcSatMapper;

	@Override
	public List<Map<String, Object>> getCalcSatList(Map<String, Object> param) throws Exception {
		System.out.println("------------------------------------------------------------------");
		System.out.println("#CALL INFO :: [" + Arrays.toString(this.getClass().getAnnotations()) + "]");
		System.out.println("#CALL METHOD :: [" + this.getClass().getName() + "." + new Object() {}.getClass().getEnclosingMethod().getName() + "]");		
		System.out.println("------------------------------------------------------------------");		
		return calcSatMapper.getCalcSatList(param);
	}

	@Override
	public List<ProjectInfoV4VO> getCalcSatInfo(Map<String, Object> param) throws Exception {
		System.out.println("------------------------------------------------------------------");
		System.out.println("#CALL INFO :: [" + Arrays.toString(this.getClass().getAnnotations()) + "]");
		System.out.println("#CALL METHOD :: [" + this.getClass().getName() + "." + new Object() {}.getClass().getEnclosingMethod().getName() + "]");		
		System.out.println("------------------------------------------------------------------");		
		return calcSatMapper.getCalcSatInfo(param);
	}

	@Override
	public int addCalcSatInfo(ProjectInfoV4Dto param) throws Exception {
		System.out.println("------------------------------------------------------------------");
		System.out.println("#CALL INFO :: [" + Arrays.toString(this.getClass().getAnnotations()) + "]");
		System.out.println("#CALL METHOD :: [" + this.getClass().getName() + "." + new Object() {}.getClass().getEnclosingMethod().getName() + "]");		
		System.out.println("------------------------------------------------------------------");		
		return calcSatMapper.addCalcSatInfo(param);
	}

	@Override
	public int updateCalcSatInfo(ProjectInfoV4Dto param) throws Exception {
		System.out.println("------------------------------------------------------------------");
		System.out.println("#CALL INFO :: [" + Arrays.toString(this.getClass().getAnnotations()) + "]");
		System.out.println("#CALL METHOD :: [" + this.getClass().getName() + "." + new Object() {}.getClass().getEnclosingMethod().getName() + "]");		
		System.out.println("------------------------------------------------------------------");		
		return calcSatMapper.updateCalcSatInfo(param);
	}

	@Override
	public Map<String, Object> getFactorValue(Map<String, Object> param) throws Exception {
		System.out.println("------------------------------------------------------------------");
		System.out.println("#CALL INFO :: [" + Arrays.toString(this.getClass().getAnnotations()) + "]");
		System.out.println("#CALL METHOD :: [" + this.getClass().getName() + "." + new Object() {}.getClass().getEnclosingMethod().getName() + "]");		
		System.out.println("------------------------------------------------------------------");		
		return calcSatMapper.getFactorValue(param);
	}
	
	@Override
	public Map<String, Object> getFactorValue(String prflId, String satgrpCd, String factorCd, String grpFactorDiv) throws Exception {
		System.out.println("------------------------------------------------------------------");
		System.out.println("#CALL INFO :: [" + Arrays.toString(this.getClass().getAnnotations()) + "]");
		System.out.println("#CALL METHOD :: [" + this.getClass().getName() + "." + new Object() {}.getClass().getEnclosingMethod().getName() + "]");		
		System.out.println("------------------------------------------------------------------");		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("prflId", prflId);
		param.put("satgrpCd", satgrpCd);
		param.put("factorCd", factorCd);
		param.put("grpFactorDiv", grpFactorDiv);
		return calcSatMapper.getFactorValue(param);
	}

	@Override
	public List<Map<String, Object>> getSatgrpList(Map<String, Object> param) throws Exception {
		System.out.println("------------------------------------------------------------------");
		System.out.println("#CALL INFO :: [" + Arrays.toString(this.getClass().getAnnotations()) + "]");
		System.out.println("#CALL METHOD :: [" + this.getClass().getName() + "." + new Object() {}.getClass().getEnclosingMethod().getName() + "]");		
		System.out.println("------------------------------------------------------------------");		
		return calcSatMapper.getSatgrpList(param);
	}

	@Override
	public Map<String, Object> getGrpPrflInfo(Map<String, Object> param) throws Exception {
		System.out.println("------------------------------------------------------------------");
		System.out.println("#CALL INFO :: [" + Arrays.toString(this.getClass().getAnnotations()) + "]");
		System.out.println("#CALL METHOD :: [" + this.getClass().getName() + "." + new Object() {}.getClass().getEnclosingMethod().getName() + "]");		
		System.out.println("------------------------------------------------------------------");		
		return calcSatMapper.getGrpPrflInfo(param);
	}

	@Override
	public int deleteCalcSatInfo(Map<String, Object> param) throws Exception {
		System.out.println("------------------------------------------------------------------");
		System.out.println("#CALL INFO :: [" + Arrays.toString(this.getClass().getAnnotations()) + "]");
		System.out.println("#CALL METHOD :: [" + this.getClass().getName() + "." + new Object() {}.getClass().getEnclosingMethod().getName() + "]");		
		System.out.println("------------------------------------------------------------------");		
		return calcSatMapper.deleteCalcSatInfo(param);
	}

	@Override
	public Map<String, Object> getGrpIndusPrfl(Map<String, Object> param) throws Exception {
		System.out.println("------------------------------------------------------------------");
		System.out.println("#CALL INFO :: [" + Arrays.toString(this.getClass().getAnnotations()) + "]");
		System.out.println("#CALL METHOD :: [" + this.getClass().getName() + "." + new Object() {}.getClass().getEnclosingMethod().getName() + "]");		
		System.out.println("------------------------------------------------------------------");		
		return calcSatMapper.getGrpIndusPrfl(param);
	}

	@Override
	public Map<String, Object> getGrpYearPrfl(Map<String, Object> param) throws Exception {
		System.out.println("------------------------------------------------------------------");
		System.out.println("#CALL INFO :: [" + Arrays.toString(this.getClass().getAnnotations()) + "]");
		System.out.println("#CALL METHOD :: [" + this.getClass().getName() + "." + new Object() {}.getClass().getEnclosingMethod().getName() + "]");		
		System.out.println("------------------------------------------------------------------");		
		return calcSatMapper.getGrpYearPrfl(param);
	}

	@Override
	public Map<String, Object> getCalcSatMetaInfo(Map<String, Object> param) throws Exception {
		System.out.println("------------------------------------------------------------------");
		System.out.println("#CALL INFO :: [" + Arrays.toString(this.getClass().getAnnotations()) + "]");
		System.out.println("#CALL METHOD :: [" + this.getClass().getName() + "." + new Object() {}.getClass().getEnclosingMethod().getName() + "]");		
		System.out.println("------------------------------------------------------------------");		
		return calcSatMapper.getCalcSatMetaInfo(param);
	}

	@Override
	public List<String> getYear() throws Exception {
		System.out.println("------------------------------------------------------------------");
		System.out.println("#CALL INFO :: [" + Arrays.toString(this.getClass().getAnnotations()) + "]");
		System.out.println("#CALL METHOD :: [" + this.getClass().getName() + "." + new Object() {}.getClass().getEnclosingMethod().getName() + "]");		
		System.out.println("------------------------------------------------------------------");		
		return calcSatMapper.getYear();
	}
}
