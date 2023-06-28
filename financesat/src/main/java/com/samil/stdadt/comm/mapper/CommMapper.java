package com.samil.stdadt.comm.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.samil.stdadt.comm.vo.ActivityLogVO;
import com.samil.stdadt.comm.vo.AppInfoVO;
import com.samil.stdadt.comm.vo.AuthMenuVO;
import com.samil.stdadt.comm.vo.CommCodeVO;
import com.samil.stdadt.comm.vo.UserSessionVO;

@Mapper
public interface CommMapper {
	// 어플리케이션 기본 정보
	public AppInfoVO getAppInfo(String appCd) throws Exception;
	
	// Role에 Home URI
	public String getHomeURI(HashMap<String, Object> param) throws Exception;
	
	// 사용자 정보
	public HashMap<String, Object> getUserInfo(HashMap<String, Object> param) throws Exception;
	
	// 로그인 사용자 정보(Role 권한 테이블 참조)
	public UserSessionVO getUserSessionInfo(HashMap<String, Object> param)  throws Exception;
	
	// Role에 따른 메뉴목록
	public List<AuthMenuVO> getAuthMenu(HashMap<String, Object> param)  throws Exception;
	
	// 자원 등록 여부 확인용
	public int getResourceCount(HashMap<String, Object> param)  throws Exception;
	
	// 자원 접근 권한 존재 여부 확인 
	public int getAuthResourceCount(HashMap<String, Object> param)  throws Exception;
	
	
	public List<CommCodeVO> getCodeList(HashMap<String, Object> param) throws Exception;
	
	public Map<String, String> getCodeMap(HashMap<String, Object> param) throws Exception;
	
	public CommCodeVO getCode(HashMap<String, Object> param) throws Exception;
	
	public int insertActivityLog(ActivityLogVO param) throws Exception;
	
	
	
}
