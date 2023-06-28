package com.samil.stdadt.vo;

public class ProjectBudgetCisV4VO {
	
	private String bdgtTrgtYn;		// Budget 대상 여부
	private String prjtcd;			// 프로젝트 코드
	private String prjtnm;			// 프로젝트 명
	private String inadjustnm;		// 협업구분
	private String createdvnm; 		// 생성구분
	private String auditgbnm;       // 외감구분               
	private double amtw;         	// CIS예산
	private double bdgt;          	// Budgeting Hours
	private double cntrtAdtTm;		// 감사계약시 합의 시간
	private double exbudgettime;	// 시간예산
	private double bdgtWkmnsp;		// Budgeting Hours * 예상팀숙련도
	private double expWkmnsp;		// 예상 팀 숙련도(bdgtWkmnsp / bdgt)
	private double wkmnsp;			// 팀숙련도
	private String div;          	// 구분
	
	public String getBdgtTrgtYn() {
		return bdgtTrgtYn;
	}
	public void setBdgtTrgtYn(String bdgtTrgtYn) {
		this.bdgtTrgtYn = bdgtTrgtYn;
	}
	public String getPrjtcd() {
		return prjtcd;
	}
	public void setPrjtcd(String prjtcd) {
		this.prjtcd = prjtcd;
	}
	public String getPrjtnm() {
		return prjtnm;
	}
	public void setPrjtnm(String prjtnm) {
		this.prjtnm = prjtnm;
	}
	public String getInadjustnm() {
		return inadjustnm;
	}
	public void setInadjustnm(String inadjustnm) {
		this.inadjustnm = inadjustnm;
	}
	public String getCreatedvnm() {
		return createdvnm;
	}
	public void setCreatedvnm(String createdvnm) {
		this.createdvnm = createdvnm;
	}
	public String getAuditgbnm() {
		return auditgbnm;
	}
	public void setAuditgbnm(String auditgbnm) {
		this.auditgbnm = auditgbnm;
	}
	public double getAmtw() {
		return amtw;
	}
	public void setAmtw(double amtw) {
		this.amtw = amtw;
	}
	public double getBdgt() {
		return bdgt;
	}
	public void setBdgt(double bdgt) {
		this.bdgt = bdgt;
	}
	public double getCntrtAdtTm() {
		return cntrtAdtTm;
	}
	public void setCntrtAdtTm(double cntrtAdtTm) {
		this.cntrtAdtTm = cntrtAdtTm;
	}
	public double getExbudgettime() {
		return exbudgettime;
	}
	public void setExbudgettime(double exbudgettime) {
		this.exbudgettime = exbudgettime;
	}
	public double getBdgtWkmnsp() {
		return bdgtWkmnsp;
	}
	public void setBdgtWkmnsp(double bdgtWkmnsp) {
		this.bdgtWkmnsp = bdgtWkmnsp;
	}
	public double getWkmnsp() {
		return wkmnsp;
	}
	public void setWkmnsp(double wkmnsp) {
		this.wkmnsp = wkmnsp;
	}
	public String getDiv() {
		return div;
	}
	public void setDiv(String div) {
		this.div = div;
	}
	@Override
	public String toString() {
		return "ProjectBudgetCisV3VO [bdgtTrgtYn=" + bdgtTrgtYn + ", prjtcd=" + prjtcd + ", prjtnm=" + prjtnm
				+ ", inadjustnm=" + inadjustnm + ", createdvnm=" + createdvnm + ", auditgbnm=" + auditgbnm + ", amtw="
				+ amtw + ", bdgt=" + bdgt + ", cntrtAdtTm=" + cntrtAdtTm + ", exbudgettime=" + exbudgettime
				+ ", bdgtWkmnsp=" + bdgtWkmnsp + ", wkmnsp=" + wkmnsp + ", div=" + div + "]";
	}
	public double getExpWkmnsp() {
		return expWkmnsp;
	}
	public void setExpWkmnsp(double expWkmnsp) {
		this.expWkmnsp = expWkmnsp;
	}
	
}
