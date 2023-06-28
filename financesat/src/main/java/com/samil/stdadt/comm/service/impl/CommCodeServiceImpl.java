package com.samil.stdadt.comm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samil.stdadt.comm.mapper.CommMapper;
import com.samil.stdadt.comm.service.CommCodeService;
import com.samil.stdadt.comm.vo.CommCodeVO;

@Service
public class CommCodeServiceImpl implements CommCodeService {
	
	@Autowired
	CommMapper commMapper;

	@Override
	public List<CommCodeVO> getCodeList(String appCd, String grpCd) throws Exception {
		return this.getCodeList(appCd, grpCd, "Y");
	}

	@Override
	public List<CommCodeVO> getCodeList(String appCd, String grpCd, String useYn) throws Exception {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("appCd", appCd);
		param.put("grpCd", grpCd);
		param.put("useYn", useYn);
		
		return commMapper.getCodeList(param);
	}
	
	@Override
	public CommCodeVO getCode(String appCd, String grpCd, String cd) throws Exception {
		return this.getCode(appCd, grpCd, cd, "Y");
	}
	
	@Override
	public CommCodeVO getCode(String appCd, String grpCd, String cd, String useYn) throws Exception {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("appCd", appCd);
		param.put("grpCd", grpCd);
		param.put("cd", cd);
		param.put("useYn", useYn);
		
		return commMapper.getCode(param);
	}

	@Override
	public Map<String, String> getCodeMap(String appCd, String grpCd, String valColumn, List<String> properties) throws Exception {
		return this.getCodeMap(appCd, grpCd, valColumn, "Y", properties);
	}

	@Override
	public Map<String, String> getCodeMap(String appCd, String grpCd, String valColumn, String useYn, List<String> properties) throws Exception {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("appCd", appCd);
		param.put("grpCd", grpCd);
		param.put("useYn", useYn);
		param.put("valColumn", valColumn);
		param.put("properties", properties);
		return commMapper.getCodeMap(param);
	}

}
