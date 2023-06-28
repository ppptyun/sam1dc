package com.samil.stdadt.vo;

/**
 * 합산대상 프로젝트 정보
 * @author shyunchoi
 *
 */
public class SubProjectVO {
	
	private String pPrjtCd;			// 상위 프로젝트 코드 00000-00-000
	private String prjtCd;			// 프로젝트 코드 00000-00-000
	private String prjtNm;			// Client명/용역명
	
	private String chargPtr;		// 프로젝트 담당 파트너 사번
	private String chargPtrNm;		// 프로젝트 담당 파트너 이름
	private String chargPtrGradNm;	// 프로젝트 담당 파트너 직급명
	
	private String chargMgr;		// 프로젝트 담당 매니저 사번
	private String chargMgrNm;		// 프로젝트 담당 매니저 이름
	private String chargMgrGradNm;	// 프로젝트 담당 매니저 직급명
	
	private long cntrtFee;			// 계약 보수 (원)
	
	private int ordby;

	public String getpPrjtCd() {
		return pPrjtCd;
	}

	public String getPrjtCd() {
		return prjtCd;
	}

	public String getPrjtNm() {
		return prjtNm;
	}

	public String getChargPtr() {
		return chargPtr;
	}

	public String getChargPtrNm() {
		return chargPtrNm;
	}

	public String getChargPtrGradNm() {
		return chargPtrGradNm;
	}

	public String getChargMgr() {
		return chargMgr;
	}

	public String getChargMgrNm() {
		return chargMgrNm;
	}

	public String getChargMgrGradNm() {
		return chargMgrGradNm;
	}

	public long getCntrtFee() {
		return cntrtFee;
	}

	public int getOrdby() {
		return ordby;
	}

	public void setpPrjtCd(String pPrjtCd) {
		this.pPrjtCd = pPrjtCd;
	}

	public void setPrjtCd(String prjtCd) {
		this.prjtCd = prjtCd;
	}

	public void setPrjtNm(String prjtNm) {
		this.prjtNm = prjtNm;
	}

	public void setChargPtr(String chargPtr) {
		this.chargPtr = chargPtr;
	}

	public void setChargPtrNm(String chargPtrNm) {
		this.chargPtrNm = chargPtrNm;
	}

	public void setChargPtrGradNm(String chargPtrGradNm) {
		this.chargPtrGradNm = chargPtrGradNm;
	}

	public void setChargMgr(String chargMgr) {
		this.chargMgr = chargMgr;
	}

	public void setChargMgrNm(String chargMgrNm) {
		this.chargMgrNm = chargMgrNm;
	}

	public void setChargMgrGradNm(String chargMgrGradNm) {
		this.chargMgrGradNm = chargMgrGradNm;
	}

	public void setCntrtFee(long cntrtFee) {
		this.cntrtFee = cntrtFee;
	}

	public void setOrdby(int ordby) {
		this.ordby = ordby;
	}
}
