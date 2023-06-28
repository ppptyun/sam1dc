package com.samil.stdadt.service;

import java.util.List;
import java.util.Map;


public interface MonitoringService {
	public List<String> getMonitoringYearList() throws Exception;
	
	public List<Map<String, Object>> getStandardAuditTimeList(Map<String, Object> param) throws Exception;
}
