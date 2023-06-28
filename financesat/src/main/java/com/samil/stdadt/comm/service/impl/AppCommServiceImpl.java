package com.samil.stdadt.comm.service.impl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samil.stdadt.comm.mapper.CommMapper;
import com.samil.stdadt.comm.service.AppCommService;
import com.samil.stdadt.comm.util.TreeMenu;
import com.samil.stdadt.comm.vo.ActivityLogVO;
import com.samil.stdadt.comm.vo.AppInfoVO;
import com.samil.stdadt.comm.vo.AuthMenuVO;
import com.samil.stdadt.comm.vo.UserSessionVO;

@Service("commService")
public class AppCommServiceImpl implements AppCommService {

	@Autowired
	private CommMapper commMapper; 
	
	@Override
	public AppInfoVO getAppInfo(String appCd) throws Exception {
		return commMapper.getAppInfo(appCd);
	}

	@Override
	public UserSessionVO getUserSessionInfo(String appCd, String inteId) throws Exception {
		return commMapper.getUserSessionInfo(new HashMap<String, Object>(){
			private static final long serialVersionUID = -6246604277796236986L;
			{
				put("appCd", appCd);
				put("inteId", inteId);
			}
		});
	}
	
	@Override
	public UserSessionVO getUserSessionInfo(String appCd, String inteId, String originEmplNo) throws Exception {
		UserSessionVO userSession = commMapper.getUserSessionInfo(new HashMap<String, Object>(){
			private static final long serialVersionUID = 4988474603990114555L;
			{
				put("appCd", appCd);
				put("inteId", inteId);
			}
		});
		if(userSession != null) userSession.setOriginEmplNo(originEmplNo);
		return userSession;
	}

	
	@Override
	public List<AuthMenuVO> getAuthMenu(String appCd, String roleCd, String URI) throws Exception {
		TreeMenu treeMenu = new TreeMenu();
		
		List<AuthMenuVO> menuList = commMapper.getAuthMenu(new HashMap<String, Object>(){
			private static final long serialVersionUID = -6304696723064738494L;
			{
				put("appCd", appCd);
				put("roleCd", roleCd);
				put("uri", URI);
			}
		});
		
		for(int i=0; i< menuList.size(); i++) {
			treeMenu.makeTree(menuList.get(i).getpMenuCd(), menuList.get(i));
		}
		
		return treeMenu.getTreeMenu();
	}

	@Override
	public Boolean checkAuthUri(HashMap<String, Object> param) throws Exception {
		int cnt = commMapper.getResourceCount(param);
		// 자원 등록 여부 판단 - 등록이 안되어 있으면 권한체크 불필요
		if(cnt > 0) {
			// 자원 등록이 되어 있을 경우, 권한테이블에 등록 되었는지 확인
			cnt = commMapper.getAuthResourceCount(param);
			if(cnt == 0) {
				return false;	
			}
		}
		return true;
	}

	@Override
	public String getHomeURI(String appCd, String roleCd) throws Exception {
		return commMapper.getHomeURI(new HashMap<String, Object>(){
			private static final long serialVersionUID = 3617912234152787418L;
			{
				put("appCd", appCd);
				put("roleCd", roleCd);
			}
		});
	}

	@Override
	public HashMap<String, Object> getUserInfo(HashMap<String, Object> param) throws Exception {
		return commMapper.getUserInfo(param);
	}

	@Override
	public int insertActivityLog(ActivityLogVO param) throws Exception {
		return commMapper.insertActivityLog(param);
	}

	
	@Override
	public Object getUserInfoByAccessToken(String accessToken) throws Exception {
		System.out.println("getUserInfoByAccessToken Start");
		URL url = null;
		DataOutputStream out = null;
		BufferedReader in = null;
		HttpsURLConnection conn = null;
		List<String> verifiedHosts = Arrays.asList("api-t.samil.com");
		
		try {
			System.out.println("accessToken: " + accessToken);
			
			url = new URL("https://api-t.samil.com/auth/userinfo?access_token=" + accessToken);
			conn = (HttpsURLConnection) url.openConnection();
			conn.setHostnameVerifier(new HostnameVerifier() {
				@Override
				public boolean verify(String hostname, SSLSession session) {
					return verifiedHosts.contains(hostname);
				}
			});
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Cache-Control", "no-cache");
			conn.connect();
			
			// 성공 아니면 Null 리턴
			if(conn.getResponseCode() != 200) return null;
			
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), Charset.forName("UTF-8")));
			StringBuffer result = new StringBuffer();
			String readLine = "";
			while ((readLine = in.readLine()) != null) result.append(readLine);
			
			ObjectMapper oMapper = new ObjectMapper();
			return oMapper.readValue(result.toString(), Object.class);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {if(out != null) out.close();} catch (Exception e) {}
			try {if(in != null) in.close();} catch(Exception e) {}
			try {if(conn != null) conn.disconnect();} catch(Exception e) {}
		}
		return null;
	}
	
}
