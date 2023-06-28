package com.samil.stdadt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.samil.stdadt.vo.WeekVO;

@Mapper
public interface ExcelExportMapper {
	public List<WeekVO> getWeeks(Map<String, Object> param) throws Exception;
	
	public List<Map<String, Object>> getBdgtByMemb(Map<String, Object> param) throws Exception;
	
	public List<Map<String, Object>> getSatBdgtInfo(Map<String, Object> param) throws Exception;
	
	public List<Map<String, Object>> getSatPrjtInfo(Map<String, Object> param) throws Exception;
	
	public List<Map<String, Object>> getSatPrjtInfoV2(Map<String, Object> param) throws Exception;
	
	
	
	public List<Map<String, Object>> getSatPrjtInfoV3(Map<String, Object> param) throws Exception;
	public List<Map<String, Object>> getSatBdgtInfoV3(Map<String, Object> param) throws Exception;
	public List<Map<String, Object>> getBdgtByMembV3(Map<String, Object> param) throws Exception;
}

