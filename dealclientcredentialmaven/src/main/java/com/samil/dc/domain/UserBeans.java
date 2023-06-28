package com.samil.dc.domain;

import java.io.Serializable;

import org.json.simple.JSONObject;

public class UserBeans implements Serializable {
	
	private static final long serialVersionUID = 6969527119972980450L;

	private static String APP_CODE = "";	// Application Code: ApplicationContext.xml에서 정의함
	private static String APP_NAME = "";	// Application Name
	
	private String AUTH_TYPE = "";	// APP_AUTHORITY의 읽기/쓰기 권한 구분 (R/E)
	private String AUTH_CLASS = "";	// APP_AUTHORITY의 권한 종류 (0, 1, 2, 3)
	private String AUTH_APP = "";	// HRUSER의 권한 종류	(0, 1, 2, 4)
	private String PTRDV = "";	// HREMP의 파트너 여부
	
	private String INTEID = "";	// ID
	private String EMAIL = "";	// pwc.com 이메일
	
	private String EMPLNO = "";	// 사번
	private String KORNM = "";	// 성명
	private String ENGNM = "";	// 영문성명
	
	private String GRADCD = "";	// 직급코드
	private String GRADNM = "";	// 직급명

	private String LOSCD = "";	// LoS코드
	private String LOSNM = "";	// LoS명
	
	private String TEAMCD = "";	// 본부/팀 Code
	private String TEAMNM = "";	// 본부/팀 명
	
	private String APPCD = ""; // 보직코드
	private String APPNM = ""; // 보직

	private String TEL;	// 내선 번호
	
	public UserBeans() {
		
	}
	
	public static String getAPP_CODE() {
		return APP_CODE;
	}
	public static void setAPP_CODE(String aPP_CODE) {
		APP_CODE = aPP_CODE;
	}
	public static String getAPP_NAME() {
		return APP_NAME;
	}
	public static void setAPP_NAME(String aPP_NAME) {
		APP_NAME = aPP_NAME;
	}
	public String getAUTH_TYPE() {
		return AUTH_TYPE;
	}
	public void setAUTH_TYPE(String aUTH_TYPE) {
		AUTH_TYPE = aUTH_TYPE;
	}
	public String getAUTH_CLASS() {
		return AUTH_CLASS;
	}
	public void setAUTH_CLASS(String aUTH_CLASS) {
		AUTH_CLASS = aUTH_CLASS;
	}
	public String getAUTH_APP() {
		return AUTH_APP;
	}
	public void setAUTH_APP(String aUTH_APP) {
		AUTH_APP = aUTH_APP;
	}
	public String getPTRDV() {
		return PTRDV;
	}
	public void setPTRDV(String pTRDV) {
		PTRDV = pTRDV;
	}
	public String getEMPLNO() {
		return EMPLNO;
	}
	public void setEMPLNO(String eMPLNO) {
		EMPLNO = eMPLNO;
	}
	public String getINTEID() {
		return INTEID;
	}
	public void setINTEID(String iNTEID) {
		INTEID = iNTEID;
	}
	public String getEMAIL() {
		return EMAIL;
	}
	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}
	public String getKORNM() {
		return KORNM;
	}
	public void setKORNM(String kORNM) {
		KORNM = kORNM;
	}
	public String getENGNM() {
		return ENGNM;
	}
	public void setENGNM(String eNGNM) {
		ENGNM = eNGNM;
	}
	public String getGRADCD() {
		return GRADCD;
	}
	public void setGRADCD(String gRADCD) {
		GRADCD = gRADCD;
	}
	public String getGRADNM() {
		return GRADNM;
	}
	public void setGRADNM(String gRADNM) {
		GRADNM = gRADNM;
	}
	public String getLOSCD() {
		return LOSCD;
	}
	public void setLOSCD(String lOSCD) {
		LOSCD = lOSCD;
	}
	public String getLOSNM() {
		return LOSNM;
	}
	public void setLOSNM(String lOSNM) {
		LOSNM = lOSNM;
	}
	public String getTEAMCD() {
		return TEAMCD;
	}
	public void setTEAMCD(String tEAMCD) {
		TEAMCD = tEAMCD;
	}
	public String getTEAMNM() {
		return TEAMNM;
	}
	public void setTEAMNM(String tEAMNM) {
		TEAMNM = tEAMNM;
	}
	public String getAPPCD() {
		return APPCD;
	}
	public void setAPPCD(String aPPCD) {
		APPCD = aPPCD;
	}
	public String getAPPNM() {
		return APPNM;
	}
	public void setAPPNM(String aPPNM) {
		APPNM = aPPNM;
	}
	public String getTEL() {
		return TEL;
	}
	public void setTEL(String tEL) {
		TEL = tEL;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject getUserInfoJSON(){
		JSONObject userJson = new JSONObject();
		userJson.put("auth_type", AUTH_TYPE);
		userJson.put("auth_class", AUTH_CLASS);
		userJson.put("auth_app", AUTH_APP);
		userJson.put("ptrdv", PTRDV);
		userJson.put("inteid", INTEID);
		userJson.put("email", EMAIL);
		userJson.put("emplno", EMPLNO);
		userJson.put("kornm", KORNM);
		userJson.put("engnm", ENGNM);
		userJson.put("gradcd", GRADCD);
		userJson.put("gradnm", GRADNM);
		userJson.put("loscd", LOSCD);
		userJson.put("losnm", LOSNM);
		userJson.put("teamcd", TEAMCD);
		userJson.put("teamnm", TEAMNM);
		userJson.put("appcd", APPCD);
		userJson.put("appnm", APPNM);
		userJson.put("tel", TEL);
		return userJson;
	}
}