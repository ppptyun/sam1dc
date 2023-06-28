package com.samil.stdadt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface MonitoringMapper {
	
	public List<Map<String, Object>> getStandardAuditTimeList(Map<String, Object> param) throws Exception;
	
	public List<String> getMonitoringYearList() throws Exception;
}
