package com.samil.dc.domain;

import java.io.Serializable;

public class UserSimpleBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2226466342323500423L;
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

	public String getEMPLNO() {
		return EMPLNO;
	}

	public void setEMPLNO(String eMPLNO) {
		EMPLNO = eMPLNO;
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
}