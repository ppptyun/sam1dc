package com.samil.stdadt.comm.service;

import java.util.HashMap;
import java.util.List;

import com.samil.stdadt.comm.vo.ActivityLogVO;
import com.samil.stdadt.comm.vo.AppInfoVO;
import com.samil.stdadt.comm.vo.AuthMenuVO;
import com.samil.stdadt.comm.vo.UserSessionVO;

public interface AppCommService {
	
	public AppInfoVO getAppInfo(String appCd) throws Exception;
	public String getHomeURI(String appCd, String roleCd) throws Exception;
	public UserSessionVO getUserSessionInfo(String appCd, String inteId)  throws Exception;
	public UserSessionVO getUserSessionInfo(String appCd, String inteId, String originEmplNo)  throws Exception;
	
	public List<AuthMenuVO> getAuthMenu(String appCd, String roleCd, String URI)  throws Exception;
	
	public Boolean checkAuthUri(HashMap<String, Object> param) throws Exception;
	
	public int insertActivityLog(ActivityLogVO param) throws Exception;
	
	public HashMap<String, Object> getUserInfo(HashMap<String, Object> param) throws Exception;
	
	
	public Object getUserInfoByAccessToken(String accessToken)  throws Exception;
}
