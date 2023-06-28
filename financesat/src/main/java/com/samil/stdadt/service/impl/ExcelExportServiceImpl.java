package com.samil.stdadt.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samil.stdadt.mapper.ExcelExportMapper;
import com.samil.stdadt.service.ExcelExportService;
import com.samil.stdadt.vo.WeekVO;

@Service
public class ExcelExportServiceImpl implements ExcelExportService {

	@Autowired
	ExcelExportMapper expMapper;
	
	@Override
	public List<WeekVO> getWeeks(Map<String, Object> param) throws Exception {
		return expMapper.getWeeks(param);
	}

	@Override
	public List<Map<String, Object>> getBdgtByMemb(Map<String, Object> param) throws Exception {
		return expMapper.getBdgtByMemb(param);
	}

	@Override
	public List<Map<String, Object>> getSatBdgtInfo(Map<String, Object> param) throws Exception {
		return expMapper.getSatBdgtInfo(param);
	}

	@Override
	public List<Map<String, Object>> getSatPrjtInfo(Map<String, Object> param) throws Exception {
		return expMapper.getSatPrjtInfo(param);
	}

	@Override
	public List<Map<String, Object>> getSatPrjtInfoV2(Map<String, Object> param) throws Exception {
		return expMapper.getSatPrjtInfoV2(param);
	}
	
	
	@Override
	public List<Map<String, Object>> getBdgtByMembV3(Map<String, Object> param) throws Exception {
		return expMapper.getBdgtByMembV3(param);
	}

	@Override
	public List<Map<String, Object>> getSatBdgtInfoV3(Map<String, Object> param) throws Exception {
		return expMapper.getSatBdgtInfoV3(param);
	}

	@Override
	public List<Map<String, Object>> getSatPrjtInfoV3(Map<String, Object> param) throws Exception {
		return expMapper.getSatPrjtInfoV3(param);
	}
}
