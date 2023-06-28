package com.samil.stdadt.comm.service;

import java.util.List;
import java.util.Map;

import com.samil.stdadt.comm.vo.CommCodeVO;

public interface CommCodeService {
	public List<CommCodeVO> getCodeList(String appCd, String grpCd) throws Exception;
	public List<CommCodeVO> getCodeList(String appCd, String grpCd, String useYn) throws Exception;
	
	public Map<String, String> getCodeMap(String appCd, String grpCd, String valColumn, List<String> properties) throws Exception;
	public Map<String, String> getCodeMap(String appCd, String grpCd, String valColumn, String useYn, List<String> properties) throws Exception;
	
	public CommCodeVO getCode(String appCd, String grpCd, String cd) throws Exception;
	public CommCodeVO getCode(String appCd, String grpCd, String cd, String useYn) throws Exception;
}
