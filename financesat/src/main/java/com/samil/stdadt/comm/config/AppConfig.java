package com.samil.stdadt.comm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.samil.stdadt.comm.mapper.CommMapper;
import com.samil.stdadt.comm.vo.AppInfoVO;
import com.samil.stdadt.comm.vo.ApplicationConfigVO;


@Configuration
public class AppConfig {

	@Autowired
	CommMapper commMapper;

	@Autowired
	ApplicationConfigVO appConfig;
	
	@Bean(name="appInfo")
	public AppInfoVO appInfo() throws Exception {
		AppInfoVO appInfo = commMapper.getAppInfo(appConfig.getAppCd());
		return appInfo;
	}
	
	public AppConfig() {}
}
