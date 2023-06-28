package com.samil.stdadt.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samil.stdadt.mapper.MonitoringMapper;
import com.samil.stdadt.service.MonitoringService;

@Service
public class MonitoringServiceImpl implements MonitoringService {
	@Autowired
	MonitoringMapper mMonitoring;
	
	@Override
	public List<Map<String, Object>> getStandardAuditTimeList(Map<String, Object> param) throws Exception {
		return mMonitoring.getStandardAuditTimeList(param);
	}

	@Override
	public List<String> getMonitoringYearList() throws Exception {
		// TODO Auto-generated method stub
		return mMonitoring.getMonitoringYearList();
	}
}
