package com.samil.stdadt.vo;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author shyunchoi
 *
 */
public class ProjectBudgetSummaryVO {
	private String prjtCd;			// 프로젝트 코드 00000-00-000
	private Boolean canEdit;		// 수정 가능 여부
	private String formDiv;			// 양식 구분
	private String prflId;			// 프로파일 ID
	private String prjtNm;			// 프로젝트 명: Client명/용역명
	private String prjtFrdt;		// 프로젝트 시작일
	private String prjtTodt;		// 프로젝트 종료일
	private String satgrpCd;		// 표준감사그룹 코드
	private String satgrpNm;		// 표준감사그룹 명(그룹1 ~ 그룹 11)
	private String retainTranYn;	// 리테인 전송 완료 여부
	private String retainScdlYn;  	// 리테인 전송 예약 여부
	private long cntrtFee;			// 롱 계약 금액
	private long totCntrtFee;		// 롱 계약 금액
	private String satTrgtYn;		// 표준감사 대상 여부
	private double calSat;			// 숙련도 반영전 표준감사시간(a+b)
	private double etDfnSat;		// ET에서 판단한 숙련도 반영전 표준감사시간
	private double etcBdgtTm;		// 표준감사시간외 감사등 예산 시간
	private double wkmnspSat;		// 기준 숙련도 기준 표준감사시간
	private double raBdgtTm;		// RA 배부 예산
	private double flcmBdgtTm;		// Fulcrum 배부 예산
	private double newStfBdgtTm;	// New Staff 예산
	private double otherBdgtTm;		// Other 예산
	private double baseWkmnsp;		// 그룹별 기준 숙련도(RA, Fulcrum 숙련도) 
	private double newStfWkmnsp;	// New Staff 숙련도
	private double otherWkmnsp;		// Other 숙련도
	private double totPrjtBdgt;			// 프로젝트 총 예산 (ET에서 판단한 숙련도 반영전 표준감사시간 + 표준감사시간외 감사등 예산 시간)
	private double totMembBdgt;			// 프로젝트 멤버에 배정된 시간 합산
	private double totMembBdgtWkmnsp;	// 연차별 가중치 고려된 프로젝트 멤버에 배정된 시간 합산
	private long netRvnu;			// Net Revenue
	private long expCm;				// 예상 CM
	private String stat;			// project Status
	private String profRetainTranYn; 	// 프로파일에 등록된 리테인 전송 허용 여부
	
	List<WeekVO> sDtOfWeek;			// 프로젝트 기간 동안의 주 시작일 목록 (테이블 컬럼 명을 정하기 위함)

	//=====================================================
	//	[시작] 20230201 Budget 입력 주기 추가
	//=====================================================
	private String budgetInputCycle; 
	
	public String getBudgetInputCycle() {
		return budgetInputCycle;
	}

	public void setBudgetInputCycle(String budgetInputCycle) {
		this.budgetInputCycle = budgetInputCycle;
	}
	//=====================================================
	//	[종료] 20230201 Budget 입력 주기 추가
	//=====================================================

	@Override
	public String toString() {
		try {
			return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String getPrjtCd() {
		return prjtCd;
	}

	public String getPrflId() {
		return prflId;
	}

	public String getPrjtNm() {
		return prjtNm;
	}

	public String getPrjtFrdt() {
		return prjtFrdt;
	}

	public String getPrjtTodt() {
		return prjtTodt;
	}

	public String getSatgrpCd() {
		return satgrpCd;
	}

	public String getSatgrpNm() {
		return satgrpNm;
	}

	public long getCntrtFee() {
		return cntrtFee;
	}

	public long getTotCntrtFee() {
		return totCntrtFee;
	}

	public double getCalSat() {
		return calSat;
	}

	public double getEtDfnSat() {
		return etDfnSat;
	}

	public double getEtcBdgtTm() {
		return etcBdgtTm;
	}

	public double getWkmnspSat() {
		return wkmnspSat;
	}

	public double getRaBdgtTm() {
		return raBdgtTm;
	}

	public double getFlcmBdgtTm() {
		return flcmBdgtTm;
	}

	public double getNewStfBdgtTm() {
		return newStfBdgtTm;
	}

	public double getOtherBdgtTm() {
		return otherBdgtTm;
	}

	public double getBaseWkmnsp() {
		return baseWkmnsp;
	}

	public double getNewStfWkmnsp() {
		return newStfWkmnsp;
	}

	public double getOtherWkmnsp() {
		return otherWkmnsp;
	}

	public double getTotPrjtBdgt() {
		return totPrjtBdgt;
	}

	public double getTotMembBdgt() {
		return totMembBdgt;
	}

	public double getTotMembBdgtWkmnsp() {
		return totMembBdgtWkmnsp;
	}

	public long getExpCm() {
		return expCm;
	}

	public String getStat() {
		return stat;
	}

	public List<WeekVO> getsDtOfWeek() {
		return sDtOfWeek;
	}

	public void setPrjtCd(String prjtCd) {
		this.prjtCd = prjtCd;
	}

	public void setPrflId(String prflId) {
		this.prflId = prflId;
	}

	public void setPrjtNm(String prjtNm) {
		this.prjtNm = prjtNm;
	}

	public void setPrjtFrdt(String prjtFrdt) {
		this.prjtFrdt = prjtFrdt;
	}

	public void setPrjtTodt(String prjtTodt) {
		this.prjtTodt = prjtTodt;
	}

	public void setSatgrpCd(String satgrpCd) {
		this.satgrpCd = satgrpCd;
	}

	public void setSatgrpNm(String satgrpNm) {
		this.satgrpNm = satgrpNm;
	}

	public void setCntrtFee(long cntrtFee) {
		this.cntrtFee = cntrtFee;
	}

	public void setTotCntrtFee(long totCntrtFee) {
		this.totCntrtFee = totCntrtFee;
	}

	public void setCalSat(double calSat) {
		this.calSat = calSat;
	}

	public void setEtDfnSat(double etDfnSat) {
		this.etDfnSat = etDfnSat;
	}

	public void setEtcBdgtTm(double etcBdgtTm) {
		this.etcBdgtTm = etcBdgtTm;
	}

	public void setWkmnspSat(double wkmnspSat) {
		this.wkmnspSat = wkmnspSat;
	}

	public void setRaBdgtTm(double raBdgtTm) {
		this.raBdgtTm = raBdgtTm;
	}

	public void setFlcmBdgtTm(double flcmBdgtTm) {
		this.flcmBdgtTm = flcmBdgtTm;
	}

	public void setNewStfBdgtTm(double newStfBdgtTm) {
		this.newStfBdgtTm = newStfBdgtTm;
	}

	public void setOtherBdgtTm(double otherBdgtTm) {
		this.otherBdgtTm = otherBdgtTm;
	}

	public void setBaseWkmnsp(double baseWkmnsp) {
		this.baseWkmnsp = baseWkmnsp;
	}

	public void setNewStfWkmnsp(double newStfWkmnsp) {
		this.newStfWkmnsp = newStfWkmnsp;
	}

	public void setOtherWkmnsp(double otherWkmnsp) {
		this.otherWkmnsp = otherWkmnsp;
	}

	public void setTotPrjtBdgt(double totPrjtBdgt) {
		this.totPrjtBdgt = totPrjtBdgt;
	}

	public void setTotMembBdgt(double totMembBdgt) {
		this.totMembBdgt = totMembBdgt;
	}

	public void setTotMembBdgtWkmnsp(double totMembBdgtWkmnsp) {
		this.totMembBdgtWkmnsp = totMembBdgtWkmnsp;
	}

	public void setExpCm(long expCm) {
		this.expCm = expCm;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public void setsDtOfWeek(List<WeekVO> sDtOfWeek) {
		this.sDtOfWeek = sDtOfWeek;
	}

	public Boolean getCanEdit() {
		return canEdit;
	}

	public void setCanEdit(Boolean canEdit) {
		this.canEdit = canEdit;
	}

	public String getRetainTranYn() {
		return retainTranYn;
	}

	public void setRetainTranYn(String retainTranYn) {
		this.retainTranYn = retainTranYn;
	}

	public long getNetRvnu() {
		return netRvnu;
	}

	public void setNetRvnu(long netRvnu) {
		this.netRvnu = netRvnu;
	}

	public String getProfRetainTranYn() {
		return profRetainTranYn;
	}

	public void setProfRetainTranYn(String profRetainTranYn) {
		this.profRetainTranYn = profRetainTranYn;
	}

	public String getSatTrgtYn() {
		return satTrgtYn;
	}

	public void setSatTrgtYn(String satTrgtYn) {
		this.satTrgtYn = satTrgtYn;
	}

	public String getRetainScdlYn() {
		return retainScdlYn;
	}

	public void setRetainScdlYn(String retainScdlYn) {
		this.retainScdlYn = retainScdlYn;
	}

	public String getFormDiv() {
		return formDiv;
	}

	public void setFormDiv(String formDiv) {
		this.formDiv = formDiv;
	}
}
