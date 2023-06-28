package com.samil.stdadt.vo;

public class ProjectListVO {
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
	private String rcrdMgr;			// 프로젝트 Record 매니저 사번
	private String rcrdMgrNm;		// 프로젝트 Record 매니저 이름
	private String rcrdMgr2;			// 프로젝트 Record 매니저 사번
	private String rcrdMgrNm2;		// 프로젝트 Record 매니저 이름
	private String rcrdMgr3;			// 프로젝트 Record 매니저 사번
	private String rcrdMgrNm3;		// 프로젝트 Record 매니저 이름
	
	
	// 프로젝트 관련 기타 정보
	private String listDvCd;		// 법인 구분 코드
	private String listDvNm;		// 법인 구분 이름(상장, 상장예정, 코넥스, 비상장)
	
	// 표준감사시간 
	private int etDfnSat;			// Engagement Team에서 정의한 표준감사시간
	private double etTrgtAdtTm;		// 감사계약시 합의 시간 (표준감사시간 대상)
	private double wkmnspSat;		// 기준 숙련도 기준 표준 감사시간(hr)
	private double raBdgtTm;		// RA 배부 예산(Hr)
	private double flcmBdgtTm;		// Fulcrum 배부 예산(Hr)
	private double achvRate;		// 달성률
	
	private double planSat;			// 계획 단계 표준감사시간
	private double totBdgt;			// member budget + ra + fulcrum, new staff, other 합산
	private double totBdgtWkmnsp;	// member budget + ra + fulcrum, new staff, other 합산 (숙련도 고려)
		
	private String stat;
	private String statNm;

	// 폼 구분 20200423 추가
	private String formDiv;
	
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
	public String getRcrdMgr() {
		return rcrdMgr;
	}
	public String getRcrdMgrNm() {
		return rcrdMgrNm;
	}
	public String getListDvCd() {
		return listDvCd;
	}
	public String getListDvNm() {
		return listDvNm;
	}
	public int getEtDfnSat() {
		return etDfnSat;
	}
	public double getWkmnspSat() {
		return wkmnspSat;
	}
	public double getAchvRate() {
		return achvRate;
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
	public void setRcrdMgr(String rcrdMgr) {
		this.rcrdMgr = rcrdMgr;
	}
	public void setRcrdMgrNm(String rcrdMgrNm) {
		this.rcrdMgrNm = rcrdMgrNm;
	}
	public void setListDvCd(String listDvCd) {
		this.listDvCd = listDvCd;
	}
	public void setListDvNm(String listDvNm) {
		this.listDvNm = listDvNm;
	}
	public void setEtDfnSat(int etDfnSat) {
		this.etDfnSat = etDfnSat;
	}
	public void setWkmnspSat(double wkmnspSat) {
		this.wkmnspSat = wkmnspSat;
	}
	public void setAchvRate(double achvRate) {
		this.achvRate = achvRate;
	}
	public void setStat(String stat) {
		this.stat = stat;
	}
	public void setStatNm(String statNm) {
		this.statNm = statNm;
	}
	public double getRaBdgtTm() {
		return raBdgtTm;
	}
	public double getFlcmBdgtTm() {
		return flcmBdgtTm;
	}
	public void setRaBdgtTm(double raBdgtTm) {
		this.raBdgtTm = raBdgtTm;
	}
	public void setFlcmBdgtTm(double flcmBdgtTm) {
		this.flcmBdgtTm = flcmBdgtTm;
	}
	public double getEtTrgtAdtTm() {
		return etTrgtAdtTm;
	}
	public void setEtTrgtAdtTm(double etTrgtAdtTm) {
		this.etTrgtAdtTm = etTrgtAdtTm;
	}
	public double getTotBdgt() {
		return totBdgt;
	}
	public double getTotBdgtWkmnsp() {
		return totBdgtWkmnsp;
	}
	public void setTotBdgt(double totBdgt) {
		this.totBdgt = totBdgt;
	}
	public void setTotBdgtWkmnsp(double totBdgtWkmnsp) {
		this.totBdgtWkmnsp = totBdgtWkmnsp;
	}
	public double getPlanSat() {
		return planSat;
	}
	public void setPlanSat(double planSat) {
		this.planSat = planSat;
	}
	public String getRcrdMgr2() {
		return rcrdMgr2;
	}
	public String getRcrdMgrNm2() {
		return rcrdMgrNm2;
	}
	public String getRcrdMgr3() {
		return rcrdMgr3;
	}
	public String getRcrdMgrNm3() {
		return rcrdMgrNm3;
	}
	public void setRcrdMgr2(String rcrdMgr2) {
		this.rcrdMgr2 = rcrdMgr2;
	}
	public void setRcrdMgrNm2(String rcrdMgrNm2) {
		this.rcrdMgrNm2 = rcrdMgrNm2;
	}
	public void setRcrdMgr3(String rcrdMgr3) {
		this.rcrdMgr3 = rcrdMgr3;
	}
	public void setRcrdMgrNm3(String rcrdMgrNm3) {
		this.rcrdMgrNm3 = rcrdMgrNm3;
	}
	public String getFormDiv() {
		return formDiv;
	}
	public void setFormDiv(String formDiv) {
		this.formDiv = formDiv;
	}
	
}
