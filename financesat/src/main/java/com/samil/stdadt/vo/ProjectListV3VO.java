package com.samil.stdadt.vo;

public class ProjectListV3VO {
	private String id;				// rowId (project Code)
	private String btn;				// button에 click event에 넘겨줄 데이터 (project code)
	
	// 프로젝트 기본 정보
	private String prjtCd;			// 프로젝트 코드 00000-00-000
	private String prjtNm;			// 프로젝트 명: Client명/용역명
	
	// 담당자
	private String chargPtr;		// 프로젝트 담당 파트너 사번
	private String chargPtrNm;		// 프로젝트 담당 파트너 이름
	private String chargMgr;		// 프로젝트 담당 매니저 사번
	private String chargMgrNm;		// 프로젝트 담당 매니저 이름
	
	
	private double cntrtAdtTm;		// 프로젝트 합의 시간
	private double totBdgtTm; 		// Total Budget Hour
	private double spaBdgtTm;		// SPA 배부 예산(Hr)
	private double flcmBdgtTm;		// Fulcrum 배부 예산(Hr)
	private double newStfBdgtTm;	// New Staff 배부 예산(Hr)
		
	private String stat;
	private String statNm;
	
	public String getId() {
		return id;
	}
	public String getBtn() {
		return btn;
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
	public String getChargMgr() {
		return chargMgr;
	}
	public String getChargMgrNm() {
		return chargMgrNm;
	}
	public double getCntrtAdtTm() {
		return cntrtAdtTm;
	}
	public double getSpaBdgtTm() {
		return spaBdgtTm;
	}
	public double getFlcmBdgtTm() {
		return flcmBdgtTm;
	}
	public double getNewStfBdgtTm() {
		return newStfBdgtTm;
	}
	public String getStat() {
		return stat;
	}
	public String getStatNm() {
		return statNm;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setBtn(String btn) {
		this.btn = btn;
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
	public void setChargMgr(String chargMgr) {
		this.chargMgr = chargMgr;
	}
	public void setChargMgrNm(String chargMgrNm) {
		this.chargMgrNm = chargMgrNm;
	}
	public void setCntrtAdtTm(double cntrtAdtTm) {
		this.cntrtAdtTm = cntrtAdtTm;
	}
	public void setSpaBdgtTm(double spaBdgtTm) {
		this.spaBdgtTm = spaBdgtTm;
	}
	public void setFlcmBdgtTm(double flcmBdgtTm) {
		this.flcmBdgtTm = flcmBdgtTm;
	}
	public void setNewStfBdgtTm(double newStfBdgtTm) {
		this.newStfBdgtTm = newStfBdgtTm;
	}
	public void setStat(String stat) {
		this.stat = stat;
	}
	public void setStatNm(String statNm) {
		this.statNm = statNm;
	}
	public double getTotBdgtTm() {
		return totBdgtTm;
	}
	public void setTotBdgtTm(double totBdgtTm) {
		this.totBdgtTm = totBdgtTm;
	}
}
