package com.samil.stdadt.vo;

public class ProfitabilityVO {
	
	private String prjtCd;				// 프로젝트 코드
	private String clntNm;				// Client 이름
	private String shrtNm;				// 용역명
	private String prdtCd;				// Product 코드
	private String creMm;				// 생성연월
	
	private double elBdgtTm;			// EL 투입 시간
	private double newStfBdgtTm;		// New Staff 예산 시간
	private double otherBdgtTm;			// Other 예산 시간
	private double raBdgtTm;			// RA 예산 시간
	private double flcmBdgtTm;			// Fulcrum 예산 시간
	private long totCntrtFee;			// 총 계약 금액(합산대상 코드 포함)
	
	private double bdgtP;				// Partner 총 배정 시간 
	private double bdgtD;				// Director 총 배정 시간
	private double bdgtSm;				// Senior Manager 총 배정 시간
	private double bdgtM;				// Manager 총 배정 시간
	private double bdgtSa;				// Senior Associate 총 배정 시간
	private double bdgtA;				// Associate 총 배정 시간
	
	private long rateP;					// Partner 시간당 단가
	private long rateD;					// Director 시간당 단가
	private long rateSm;				// Senior Manager 시간당 단가
	private long rateM;					// Manager 시간당 단가
	private long rateSa;				// Senior Associate 시간당 단가
	private long rateA;					// Associate 시간당 단가
	
	private double totBdgtFee = 0 ;			// 총계
	private double feeDiff;				// 프로젝트 손익 (totCntrtFee - totBdgtFee)
	private double realization;			// 이익률( totCntrtFee / totBdgtFee * 100)
	private double etcBdgtTm;
	
	public void calculate() {
		this.totBdgtFee = (this.bdgtP * this.rateP) 
						+ (this.bdgtD * this.rateD) 
						+ (this.bdgtSm * this.rateSm) 
						+ (this.bdgtM * this.rateM) 
						+ (this.bdgtSa * this.rateSa) 
						+ (this.bdgtA * this.rateA)
						+ (this.newStfBdgtTm * this.rateA)
						+ (this.raBdgtTm * this.rateSa)
						+ (this.flcmBdgtTm * this.rateSa)
						+ (this.otherBdgtTm * this.rateD);
		
		this.feeDiff = this.totCntrtFee - this.totBdgtFee;
		
		this.realization = this.totBdgtFee == 0?0:(this.totCntrtFee / this.totBdgtFee * 100); 
		
		this.etcBdgtTm = this.flcmBdgtTm + this.raBdgtTm + this.otherBdgtTm;
	}

	public String getPrjtCd() {
		return prjtCd;
	}

	public String getClntNm() {
		return clntNm;
	}

	public String getShrtNm() {
		return shrtNm;
	}

	public String getPrdtCd() {
		return prdtCd;
	}

	public String getCreMm() {
		return creMm;
	}

	public double getNewStfBdgtTm() {
		return newStfBdgtTm;
	}

	public double getOtherBdgtTm() {
		return otherBdgtTm;
	}

	public double getRaBdgtTm() {
		return raBdgtTm;
	}

	public double getFlcmBdgtTm() {
		return flcmBdgtTm;
	}

	public long getTotCntrtFee() {
		return totCntrtFee;
	}

	public double getBdgtP() {
		return bdgtP;
	}

	public double getBdgtD() {
		return bdgtD;
	}

	public double getBdgtSm() {
		return bdgtSm;
	}

	public double getBdgtM() {
		return bdgtM;
	}

	public double getBdgtSa() {
		return bdgtSa;
	}

	public double getBdgtA() {
		return bdgtA;
	}

	public long getRateP() {
		return rateP;
	}

	public long getRateD() {
		return rateD;
	}

	public long getRateSm() {
		return rateSm;
	}

	public long getRateM() {
		return rateM;
	}

	public long getRateSa() {
		return rateSa;
	}

	public long getRateA() {
		return rateA;
	}

	public double getTotBdgtFee() {
		return totBdgtFee;
	}

	public double getFeeDiff() {
		return feeDiff;
	}

	public double getRealization() {
		return realization;
	}

	public void setPrjtCd(String prjtCd) {
		this.prjtCd = prjtCd;
	}

	public void setClntNm(String clntNm) {
		this.clntNm = clntNm;
	}

	public void setShrtNm(String shrtNm) {
		this.shrtNm = shrtNm;
	}

	public void setPrdtCd(String prdtCd) {
		this.prdtCd = prdtCd;
	}

	public void setCreMm(String creMm) {
		this.creMm = creMm;
	}

	public void setNewStfBdgtTm(double newStfBdgtTm) {
		this.newStfBdgtTm = newStfBdgtTm;
	}

	public void setOtherBdgtTm(double otherBdgtTm) {
		this.otherBdgtTm = otherBdgtTm;
	}

	public void setRaBdgtTm(double raBdgtTm) {
		this.raBdgtTm = raBdgtTm;
	}

	public void setFlcmBdgtTm(double flcmBdgtTm) {
		this.flcmBdgtTm = flcmBdgtTm;
	}

	public void setTotCntrtFee(long totCntrtFee) {
		this.totCntrtFee = totCntrtFee;
	}

	public void setBdgtP(double bdgtP) {
		this.bdgtP = bdgtP;
	}

	public void setBdgtD(double bdgtD) {
		this.bdgtD = bdgtD;
	}

	public void setBdgtSm(double bdgtSm) {
		this.bdgtSm = bdgtSm;
	}

	public void setBdgtM(double bdgtM) {
		this.bdgtM = bdgtM;
	}

	public void setBdgtSa(double bdgtSa) {
		this.bdgtSa = bdgtSa;
	}

	public void setBdgtA(double bdgtA) {
		this.bdgtA = bdgtA;
	}

	public void setRateP(long rateP) {
		this.rateP = rateP;
	}

	public void setRateD(long rateD) {
		this.rateD = rateD;
	}

	public void setRateSm(long rateSm) {
		this.rateSm = rateSm;
	}

	public void setRateM(long rateM) {
		this.rateM = rateM;
	}

	public void setRateSa(long rateSa) {
		this.rateSa = rateSa;
	}

	public void setRateA(long rateA) {
		this.rateA = rateA;
	}

	public void setTotBdgtFee(double totBdgtFee) {
		this.totBdgtFee = totBdgtFee;
	}

	public void setFeeDiff(double feeDiff) {
		this.feeDiff = feeDiff;
	}

	public void setRealization(double realization) {
		this.realization = realization;
	}

	public double getEtcBdgtTm() {
		return etcBdgtTm;
	}

	public void setEtcBdgtTm(double etcBdgtTm) {
		this.etcBdgtTm = etcBdgtTm;
	}

	public double getElBdgtTm() {
		return elBdgtTm;
	}

	public void setElBdgtTm(double elBdgtTm) {
		this.elBdgtTm = elBdgtTm;
	}
}	
