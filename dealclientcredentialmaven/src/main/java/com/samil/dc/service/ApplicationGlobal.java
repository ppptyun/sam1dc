package com.samil.dc.service;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.samil.dc.access.DBConnection;
import com.samil.dc.access.OracleConnection;
import com.samil.dc.core.ContextLoader;
import com.samil.dc.dao.DcSupportDAO;
import com.samil.dc.domain.CommonCodeListBean;
import com.samil.dc.domain.UserBeans;
import com.samil.dc.domain.UserMenuBeans;
import com.samil.dc.domain.UserRoleBeans;
import com.samil.dc.domain.UserSessionBeans;
import com.samil.dc.service.worker.ServiceHelper;
import com.samil.dc.sql.SQLManagement;
import com.samil.dc.util.Constants;
import com.samil.dc.util.SessionUtil;

import sun.misc.BASE64Decoder;

public class ApplicationGlobal {
	private static final Logger logger = Logger.getRootLogger();
	public static UserSessionBeans getUser(HttpServletRequest request) {
		if("Y".equals(ContextLoader.getContext("ConnectToOper"))){
			logger.debug("운영서버 접속!!");
		}else{
			logger.debug("테스트서버 접속!!");
		}
		UserSessionBeans userSessionBeans = SessionUtil.refreshUserSession(request);
		return userSessionBeans;
	}
	
	public static UserSessionBeans getUserInfo(String userId) {
		UserSessionBeans userSessionBeans = new UserSessionBeans();
		userSessionBeans.setUserId(userId);
		
		DBConnection dbcon = new OracleConnection();
		
		try{
			// ###################################################################
			// 사용자 계정 정보
			// 2021-09-02 남웅주 Pwc Identity 인증 된 GUID 적용함.
			// ###################################################################
			String sql =  "SELECT "
					+ "		  H.PRTDV AS PTRDV,"
					+ "			 CASE "
					+ "       		WHEN E.TAG2 = '0' OR E.TAG2 = '1' THEN E.TAG2"
					+ "          WHEN DECODE((SELECT 1 FROM nmsp.CMCMOFCD CM WHERE USESDV='Y' AND REPTEMP = H.EMPLNO), NULL, 'N', 'Y') = 'Y' "
					+ "           		 OR(regexp_like(H.GRADCD, '016|020|022|024|025|030|101') AND E.TAG2 = '2') "
					+ "          THEN '2' "
					+ "       		ELSE '4' "
					+ "       END AS AUTH_APP,  "
					+ "       H.INTEID, "
					+ "       H.EMAIL, "
					+ "       H.EMPLNO, "
					+ "       H.KORNM, "
					+ "       H.ENGNM, "
					+ "       H.GRADCD, "
					+ "       H.LOSCD, "
					+ "       H.TEAMCD, "
					+ "       H.APPCD, "
					+ "       T.TEAMNM, "
					+ "       G.GRADNM, "
					+ "       W.APP AS APPNM, "
					+ "       C.ETCDNM AS LOSNM, "
					+ "       H.PHOEXT"
					+ "     FROM CMTEAMCD T, "
					+ "          HRGRAD G, "
					+ "          CMETCOCD C, "
					+ "          HRTRANWRK W, "
					+ "          HREMP H, "
					+ "          HRUSER E, "
					+ "			 WEB_HTIF_V V "
					+ "    WHERE H.OFFIST = '001' "
					+ "          AND H.GRADCD = G.GRADCD "
					+ "          AND H.TEAMCD = T.TEAMCD "
					+ "          AND H.LOSCD = C.ETCOCD "
					+ "          AND C.SYTMDV = 'CM' "
					+ "          AND H.OFFIST = '001' "
					+ "          AND C.CLASCD = '06' "
					+ "          AND H.EMPLNO = W.EMSABUCD "
					+ "          AND E.EMPLNO = H.EMPLNO "			
					+ "          AND H.INTEID = V.INTEID "
					+ "          AND H.EMPLNO = V.EMPLNO "
					+ "          AND (V.INTEID = <#INTEID#> OR V.GU_ID = <#INTEID#>) ";
			
			

			// 사번
			String empNo = "";
			// 로스[DealLoS: 30]
			String losCd = "";
			// 본부장[2]
			String authApp = "";
			
			SQLManagement sqlmng = new SQLManagement(sql);
			sqlmng.addValueReplacement("INTEID", userId);
			sqlmng.generate();
			
			ResultSet rs = dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
			UserBeans uVo = new UserBeans();
			if(rs != null){
				rs.next();
				uVo.setAUTH_APP(rs.getString("AUTH_APP"));
				uVo.setPTRDV(rs.getString("PTRDV"));
				uVo.setINTEID(rs.getString("INTEID"));
				uVo.setEMAIL(rs.getString("EMAIL"));
				uVo.setEMPLNO(rs.getString("EMPLNO"));
				uVo.setKORNM(rs.getString("KORNM"));
				uVo.setENGNM(rs.getString("ENGNM"));
				uVo.setGRADCD(rs.getString("GRADCD"));
				uVo.setGRADNM(rs.getString("GRADNM"));
				uVo.setLOSCD(rs.getString("LOSCD"));
				uVo.setLOSNM(rs.getString("LOSNM"));
				uVo.setTEAMCD(rs.getString("TEAMCD"));
				uVo.setTEAMNM(rs.getString("TEAMNM"));
				uVo.setAPPCD(rs.getString("APPCD"));
				uVo.setAPPNM(rs.getString("APPNM"));
				uVo.setTEL(rs.getString("PHOEXT"));
				// 결과 저장				
				empNo = uVo.getEMPLNO();
				losCd = uVo.getLOSCD();
				authApp = uVo.getAUTH_APP();
				userSessionBeans.setUserBeans(uVo);
			}
			if (logger.isDebugEnabled()) {
				StringBuffer sb = new StringBuffer();
				sb.append("INTEID[").append(userId).append("] ");
				sb.append("NAME[").append(uVo.getKORNM()).append("] ");
				sb.append("EMPNO[").append(empNo).append("] ");
				sb.append("EMAIL[").append(uVo.getEMAIL()).append("] ");
				sb.append("LOSCD[").append(losCd).append("] ");
				sb.append("AUTHAPP[").append(authApp).append("]");
				logger.debug(sb.toString());
			}
			
			// ###################################################################
			// 사용자 롤/본부 목록
			// ###################################################################
			// 사용자 롤/본부 저장
			List<UserRoleBeans> userRoleList = new ArrayList<UserRoleBeans>();
			List<String> userHQList = new ArrayList<String>();
			// 현재 본인의 소속 본부 저장 -> 본부관리자 설정된 본부만 대상이 된다.
			//userHQList.add(uVo.getTEAMCD());
			
			
			// Deal LoS 롤 추가
			if ("30".equals(losCd)) {
				userRoleList.add(new UserRoleBeans(Constants.Role.R04));
			}
			// 본부장 롤 추가
			if (("0".equals(authApp) || "1".equals(authApp) || "2".equals(authApp)) && "30".equals(losCd)) {
				userRoleList.add(new UserRoleBeans(Constants.Role.R06));
			}
			// 서비스에서 관리하는 롤 조회
			// A4 Insecure Direct Object References[empNo]
			if (!StringUtils.isBlank(empNo) && ServiceHelper.isValidEmpno(empNo)) {
				// 사용자 롤 조회
				sql = "  SELECT DISTINCT RL.ROLECD "
					+ "       , RL.ROLENM "
					+ "       , RL.SORT "
					+ "    FROM WEB_DCROLEMBR RM "
					+ "       , WEB_DCROLE RL "
					+ "   WHERE RM.EMPNO = <#EMPLNO#> "
					+ "     AND RL.ROLECD = RM.ROLECD ";
				
				sqlmng.init(sql);
				sqlmng.addValueReplacement("EMPLNO", empNo);
				sqlmng.generate();
				rs = dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
				if (rs != null) {
					while (rs.next()) {
						UserRoleBeans userRoleBeans = new UserRoleBeans();
						userRoleBeans.setRolecd(rs.getString("ROLECD"));
						userRoleBeans.setRolenm(rs.getString("ROLENM"));
						userRoleList.add(userRoleBeans);
					}
				}
				
				// 본부 담당자 관리에서 롤/본부 조회
				List<String> tempUserHQList = new ArrayList<String>();
				sql = "  SELECT DISTINCT HQCD "
						+ "    FROM WEB_DCHQEMP "
						+ "   WHERE EMPNO = <#EMPLNO#> ";
				
				sqlmng.init(sql);
				sqlmng.addValueReplacement("EMPLNO", empNo);
				sqlmng.generate();
				rs = dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
				if (rs != null) {
					while (rs.next()) {
						String hqcd = rs.getString("HQCD");
						tempUserHQList.add(hqcd);
					}
				}
				
				// 관리하는 본부가 있으면 '본부 담당자' 롤을 추가
				if (tempUserHQList.size() > 0) {
					userRoleList.add(new UserRoleBeans(Constants.Role.R05));
					userHQList.addAll(tempUserHQList);
				}
			}
			
			// 사용자 롤 중복 제거
			userRoleList = ServiceHelper.getUniqueRoleList(userRoleList);
			userHQList = ServiceHelper.getUniqueHQList(userHQList);
			// 사용자 세션에 저장
			userSessionBeans.setUserRoleList(userRoleList);
			userSessionBeans.setUserHQList(userHQList);
			
			// ###################################################################
			// 사용자 메뉴 목록
			// ###################################################################
			// 서비스에서 사용 가능한 메뉴 목록 조회
			if (userRoleList.size() > 0) {
				// 메뉴조회시 사용할 롤 저장
				int roleSize = userSessionBeans.getUserRoleList().size();
				String roles[] = new String[roleSize];
				for(int i=0; i < roleSize; i++){
					roles[i] = userSessionBeans.getUserRoleList().get(i).getRolecd();
					logger.debug(roles[i]);
				}
				
				// 사용자 메뉴 목록 조회
				sql = "  SELECT LEVEL "
					+ "       , A.MENUID "	
					+ "       , A.UPMENUID "
					+ "       , A.MENUNM "
					+ "       , A.URL "
					+ "       , A.SORT "
					+ "    FROM WEB_DCMENU A "
					+ "       , ( "
					+ "              SELECT DISTINCT ME.MENUID "
					+ "                FROM WEB_DCMENUMAP MM "
					+ "                   , WEB_DCMENU ME "
					+ "               WHERE MM.ROLECD IN ('"+ StringUtils.join(roles, "','")  +"')"
					+ "                 AND ME.MENUID = MM.MENUID "
					+ "         ) B "
					+ "   WHERE A.MENUID = B.MENUID "
					+ "   START WITH A.UPMENUID = 'ROOT' "
					+ " CONNECT BY PRIOR A.MENUID = A.UPMENUID "
					+ "   ORDER BY LEVEL ASC, SORT ASC ";
				
				sqlmng.init(sql);
				sqlmng.generate();
				rs = dbcon.executeQuery(sqlmng.getSql());
				if (rs != null) {
					while (rs.next()) {
						UserMenuBeans userMenuBeans = new UserMenuBeans();
						userMenuBeans.setLevel(rs.getInt("LEVEL"));
						userMenuBeans.setMenuid(rs.getString("MENUID"));
						userMenuBeans.setUpmenuid(rs.getString("UPMENUID"));
						userMenuBeans.setMenunm(rs.getString("MENUNM"));
						userMenuBeans.setUrl(rs.getString("URL"));
						userSessionBeans.addUserMenu(userMenuBeans);
					}
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (dbcon != null) {
				dbcon.close();
			}
		}
		return userSessionBeans;
	}
	
	/**
	 * 공통 코드 조회
	 * 
	 * @param parCd
	 * @return
	 */
	public static List<CommonCodeListBean> getCommonCodeList(String parCd) {
		DBConnection dbcon = new OracleConnection();

		List<CommonCodeListBean> list = new ArrayList<CommonCodeListBean>();
		DcSupportDAO dao = new DcSupportDAO(dbcon);
		try {
			list = dao.sqlSelectCommonCodeList(parCd);
			if (list == null) {
				list = new ArrayList<CommonCodeListBean>();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (dbcon != null) {
				dbcon.close();
			}
		}

		return list;
	}
	
	/**
	 * 롤 코드 조회
	 * 
	 * @param parCd
	 * @return
	 */
	public static List<CommonCodeListBean> getRoleCodeList() {
		DBConnection dbcon = new OracleConnection();

		List<CommonCodeListBean> list = new ArrayList<CommonCodeListBean>();
		DcSupportDAO dao = new DcSupportDAO(dbcon);
		try {
			list = dao.sqlSelectRoleCodeList();
			if (list == null) {
				list = new ArrayList<CommonCodeListBean>();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (dbcon != null) {
				dbcon.close();
			}
		}

		return list;
	}
	
	public static String checkCookie(HttpServletRequest req) {
		String cookieName = null;
		String cookieValue = null;
		String targetUserId = null;

		BASE64Decoder decode = new BASE64Decoder();
		Cookie[] cookie = req.getCookies();

		if (cookie != null) {
			for (Cookie tmp : cookie) {
				cookieName = tmp.getName();
				cookieValue = tmp.getValue();

				if (cookieName.equals("prjtid")) {

					try {
						targetUserId = new String(decode.decodeBuffer(cookieValue));
						logger.info("Admin Login Success: " + targetUserId);

						return targetUserId;
					} catch (IOException e) {
						logger.info("Admin Login Failed - Target ID decode error: " + cookieValue);
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}
}
