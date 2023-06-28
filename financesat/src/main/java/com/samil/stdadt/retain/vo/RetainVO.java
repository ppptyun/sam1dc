package com.samil.stdadt.retain.vo;

import com.samil.stdadt.util.Constant;

public class RetainVO {
	Long bkgId;
	Long resId;
	Integer bkgBtyId;
	String notes;
	
	String mainPrjtCd;
	String prjtCd;
	String locaCd;
	String membEmplNo;
	
	String fromDt;
	String toDt;
	Integer workDay;		// 주별 근무일수
	Double bdgtByDay;		// 일별 예산 시간(Hour 단위)
	
	String retainTranEmplNo;
	String retainTranInteId;
	String retainTranKorNm;

	public void generateData() {
		this.bkgBtyId 	= "02".equals(this.locaCd)?15:null;		// Non-Field일 경우 After Work Activity로 표기 하기 위함.
		this.notes 		= "01".equals(this.locaCd)?"Field":"02".equals(this.locaCd)?"Non-Field":"";
	}


	public Long getResId() {
		return resId;
	}

	public Integer getBkgBtyId() {
		return bkgBtyId;
	}

	public String getNotes() {
		return notes;
	}

	public String getPrjtCd() {
		return prjtCd;
	}

	public String getLocaCd() {
		return locaCd;
	}

	public String getMembEmplNo() {
		return membEmplNo;
	}

	public String getFromDt() {
		return fromDt;
	}

	public String getToDt() {
		return toDt;
	}

	public Integer getWorkDay() {
		return workDay;
	}

	public Double getBdgtByDay() {
		return bdgtByDay;
	}

	public void setResId(Long resId) {
		this.resId = resId;
	}

	public void setBkgBtyId(Integer bkgBtyId) {
		this.bkgBtyId = bkgBtyId;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setPrjtCd(String prjtCd) {
		this.prjtCd = prjtCd;
	}

	public void setLocaCd(String locaCd) {
		this.locaCd = locaCd;
	}

	public void setMembEmplNo(String membEmplNo) {
		this.membEmplNo = membEmplNo;
	}

	public void setFromDt(String fromDt) {
		this.fromDt = fromDt;
	}

	public void setToDt(String toDt) {
		this.toDt = toDt;
	}

	public void setWorkDay(Integer workDay) {
		this.workDay = workDay;
	}

	public void setBdgtByDay(Double bdgtByDay) {
		this.bdgtByDay = bdgtByDay;
	}

	public String getRetainTranEmplNo() {
		return retainTranEmplNo;
	}

	public String getRetainTranInteId() {
		return retainTranInteId;
	}

	public String getRetainTranKorNm() {
		return retainTranKorNm;
	}

	public void setRetainTranEmplNo(String retainTranEmplNo) {
		this.retainTranEmplNo = retainTranEmplNo;
	}

	public void setRetainTranInteId(String retainTranInteId) {
		this.retainTranInteId = retainTranInteId;
	}

	public void setRetainTranKorNm(String retainTranKorNm) {
		this.retainTranKorNm = retainTranKorNm;
	}
	
	public Long getBkgId() {
		return bkgId;
	}


	public void setBkgId(Long bkgId) {
		this.bkgId = bkgId;
	}


	public String getMainPrjtCd() {
		return mainPrjtCd;
	}


	public void setMainPrjtCd(String mainPrjtCd) {
		this.mainPrjtCd = mainPrjtCd;
	}
}
