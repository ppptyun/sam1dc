package com.samil.stdadt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExcelImportMapper {
	
	public List<Map<String, Object>> selectEmp(Map<String, Object> param) throws Exception;
	
	public int inertMemberByExcel(Map<String, Object> param) throws Exception;
	
	public int inertMemberBudgetByExcel(Map<String, Object> param) throws Exception;

	public List<String> getBudgetTargetPrjtCd(String prjtCd) throws Exception;
	
	public int inertMemberByExcelV3(Map<String, Object> param) throws Exception;
	
	public int inertMemberBudgetByExcelV3(Map<String, Object> param) throws Exception;
	
	public Map<String, Object> getWkmnspMinMax() throws Exception;
}
