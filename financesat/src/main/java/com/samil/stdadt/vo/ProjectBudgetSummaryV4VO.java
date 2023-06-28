package com.samil.stdadt.vo;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author wjnam
 *
 */
public class ProjectBudgetSummaryV4VO {
	private String mode;			// read, edit
	private String prjtcd;			// [Header] 프로젝트 코드
	private String prjtnm;			// [Header] 프로젝트 명
	private String chargptr;		// EL 사번
	private String chargmgr;		// PM 사번
	private String satgrpCd;		// 표준감사 그룹 코드
	private String prjtFrdt;		// [Header] 프로젝트 시작일
	private String prjtTodt;		// [Header] 프로젝트 종료일
	private long   cntrtFee;		// [Header] CIS 예산 - 일반
	private long   totCntrtFee;		// [Header] CIS 예산 - 표감대상
	private String bizTodt;			// [Header] 결산 년월
	private String rprtScdlDt;		// [Header] 예상 보고서일
	private double etDfnSat;		// [표준감사대상 대상] 한공회표준감사시간	
	private double etTrgtAdtTm;		// [표준감사대상 대상] 감사계약시합의시간
	private double baseWkmnsp;		// [표준감사대상 대상] 기준숙련도
	private double expWkmnsp;		// 예상 팀 숙련도
	private double satExpWkmnsp;	// [표준감사대상 대상] 예상 팀 숙련도
	private double cntrtAdtTm;		// [표준감사대상 대상] 감사계약시 합의시간
	private double planSat;			// [표준감사대상 대상] 계획단계
	private long   netRvnu;			// Net Revenue
	private long   expCm;			// [Header] 예상 CM
	private double satBdgtTm;		// 
	private double exbudgettime;	// [표준감사대상 대상] Budgeting hours
	private double totalTm;			// 전체
	private double elTm;			// [Budget요약] 담당이사
	private double pmTm;			// [Budget요약] PM
	private double qcTm;			// [Budget요약] QC
	private double qrpTm;			// [Budget요약] QC
	private double raTm;			// [Budget요약] RA
	private double fulcrumTm;		// [Budget요약] Fulcrum
	private double newstaffTm;		// [Budget요약] New Staff
	private String satgrpNm;		// [표준감사대상 대상] 그룹명
	private double adtteamTm;		// [Budget요약] 감사팀
	private String chargptrnm;		// [Header] EL 이름
	private String chargmgrnm;		// [Header] PM 이름
	private Boolean canEdit;
	private Boolean canDelete;
	private String prflId;
	private String prjtcd1;
	private String prjtcd2;
	private String prjtcd3;
	private String satgrp1ExptYn;
	private String indusCd;
	private String listDvCd;
	private String indivAsset;
	private String consoSales;
	private String consoAsset;
	private String desigAdtYn;
	private String bizRprtYn;
	private double priorAdtTm;
	private String usaListYn;
	private String holdingsDivCd;
	private String consoFinstatYn;
	private long   sbsidCnt;
	private String firstAdtYn;
	private String firstAdtFctr;
	private String ifrsYn;
	private String consoInvnt;
	private String consoAccntReceiv;
	private String currConsoLossYn;
	private String currAdtopinCd;
	private String intrTranAssetSales;
	private String relatCompAsset;
	private String sbsidAssetWithIntrTran;
	private String sbsidSalesWithIntrTran;
	private double raBdgtTm;
	private String raBdgtMgr;
	private double flcmBdgtTm;
	private String flcmBdgtMgr;
	private double newStfBdgtTm;
	private double otherBdgtTm;
	private String rjctCmnt;
	private String aprvReqEmplno;
	private String aprvReqDt;
	private String aprvCmpltDt;
	private String stat;
	private String retainTranYn;
	private String retainTranDt;
	private String retainTranEmplno;
	private String satTrgtYn;
	private String newStfWkmnsp;
	private String otherWkmnsp;
	private String factorVal;
	private String intrAdtYn;
	private double intrAdtTm;
	private double calAdtTm;
	private String retainScdlYn;
	private String retainScdlDt;
	private String retainScdlBaseDt;
	private long   cmpltCnt;
	private String rcrdMgr2;
	private String rcrdMgr3;
	private String formDiv;
	private String calcOnlyYn;
	private String compNm;
	private String bizFrdt;
	private String firstBizDt;
	private long   contiAdtCnt;
	private String intrAdtYcnt;
	private long   revwCnt;
	private String kamYn;
	private String priorAdtopinChgYn;
	private String priorAdtopinChgFctr;
	private String priorLossYn;
	private String priorLossFctr;
	private String etcFctr;
	private String minMaxYn;
	private String minMaxReasonCd;
	private String minMaxReasonDscrt;
	private double fstAdtTm;
	private String fstAdtrBaseWkmnspYn;
	private double fstAdtrWkmnsp;
	private String fstAdtTmReasonCd;
	private String fstAdtTmReasonDscrt;
	private String fstWkmnspReasonCd;
	private String fstWkmnspReasonDscrt;
	private String priorAdtrCd;
	private double priorBaseWkmnsp;
	private String priorAdtrBaseWkmnspYn;
	private double priorAdtrWkmnsp;
	private String priorAdtTmReasonCd;
	private String priorAdtTmReasonDscrt;
	private String priorWkmnspReasonCd;
	private String priorWkmnspReasonDscrt;
	private double minTm;
	private double maxTm;
	private double intplSat;
	private String calResult;
	private String yearRate;
	private String intrAdtFctr;
	private String ifrsFctr;
	private String holdingsFctr;
	private String usaListFctr;
	private String riskFctr;
	private String kamFctr;
	private String consoFinstatFctr;
	private String sbsidCntFctr;
	private String currAdtopinFctr;
	private String currConsoLossFctr;
	private String sameAdtrSbsidYn;
	private double fstMaxTm;
	private double wkmnspSat;
	private double calSat;
	private String clntNm;
	private String shrtnm;
	private String prdtcd;
	private String chargbon;
	private String chargptrNm;
	private String chargptrGradNm;
	private String chargptrTeamcd;
	private String chargptrBonbcd;
	private String chargmgrNm;
	private String chargmgrGradNm;
	private String rcrdMgrNm;
	private String rcrdMgrGradNm;
	private String rcrdMgrNm2;
	private String rcrdMgrGradNm2;
	private String rcrdMgrNm3;
	private String rcrdMgrGradNm3;

	List<WeekVO> sDtOfWeek;			// 프로젝트 기간 동안의 주 시작일 목록 (테이블 컬럼 명을 정하기 위함)
	private String profRetainTranYn; 	// 프로파일에 등록된 리테인 전송 허용 여부
	private String profBdgtYn; 	// 프로파일에 등록된 Budget 수정 허용 여부
	private String profBdgtReason;	// 프로파일에 등록된 표준감사시간 수정 허용 여부
	private String profSatYn;		// 프로파일에 등록된 표준감사시간 수정 허용 여부
	private String profSatReason;	// 표준감사시간 수정불가시 사유 - LOCKBIZTODT(LOCK적용 결산연도), NEARBIZTODT(본부별 Lock 적용 기준 개월수)
	
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
	
	//=====================================================
	//	[시작] 20230216 수정 후 결재상세페이지로 되돌아가기위한 페이지 정보
	//=====================================================	
	private String returnUrl;
	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	//=====================================================
	//	[종료] 20230216 수정 후 결재상세페이지로 되돌아가기위한 페이지 정보
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
	
	public long getTotCntrtFee100() {
		return totCntrtFee/1000000;
	}
	
	public double getExpComplRate() {
		return (planSat == 0. ? 0. : etDfnSat / planSat) * 100;
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

	public String getChargptr() {
		return chargptr;
	}

	public void setChargptr(String chargptr) {
		this.chargptr = chargptr;
	}

	public String getChargmgr() {
		return chargmgr;
	}

	public void setChargmgr(String chargmgr) {
		this.chargmgr = chargmgr;
	}

	public String getSatgrpCd() {
		return satgrpCd;
	}

	public void setSatgrpCd(String satgrpCd) {
		this.satgrpCd = satgrpCd;
	}

	public String getPrjtFrdt() {
		return prjtFrdt;
	}

	public void setPrjtFrdt(String prjtFrdt) {
		this.prjtFrdt = prjtFrdt;
	}

	public String getPrjtTodt() {
		return prjtTodt;
	}

	public void setPrjtTodt(String prjtTodt) {
		this.prjtTodt = prjtTodt;
	}

	public long getCntrtFee() {
		return cntrtFee;
	}

	public void setCntrtFee(long cntrtFee) {
		this.cntrtFee = cntrtFee;
	}

	public String getBizTodt() {
		return bizTodt;
	}

	public void setBizTodt(String bizTodt) {
		this.bizTodt = bizTodt;
	}

	public String getRprtScdlDt() {
		return rprtScdlDt;
	}

	public void setRprtScdlDt(String rprtScdlDt) {
		this.rprtScdlDt = rprtScdlDt;
	}

	public double getEtDfnSat() {
		return etDfnSat;
	}

	public void setEtDfnSat(double etDfnSat) {
		this.etDfnSat = etDfnSat;
	}

	public double getEtTrgtAdtTm() {
		return etTrgtAdtTm;
	}

	public void setEtTrgtAdtTm(double etTrgtAdtTm) {
		this.etTrgtAdtTm = etTrgtAdtTm;
	}

	public double getBaseWkmnsp() {
		return baseWkmnsp;
	}

	public void setBaseWkmnsp(double baseWkmnsp) {
		this.baseWkmnsp = baseWkmnsp;
	}

	public double getCntrtAdtTm() {
		return cntrtAdtTm;
	}

	public void setCntrtAdtTm(double cntrtAdtTm) {
		this.cntrtAdtTm = cntrtAdtTm;
	}

	public long getExpCm() {
		return expCm;
	}

	public void setExpCm(long expCm) {
		this.expCm = expCm;
	}

	public double getSatBdgtTm() {
		return satBdgtTm;
	}

	public void setSatBdgtTm(double satBdgtTm) {
		this.satBdgtTm = satBdgtTm;
	}

	public double getTotalTm() {
		return totalTm;
	}

	public void setTotalTm(double totalTm) {
		this.totalTm = totalTm;
	}

	public double getElTm() {
		return elTm;
	}

	public void setElTm(double elTm) {
		this.elTm = elTm;
	}

	public double getPmTm() {
		return pmTm;
	}

	public void setPmTm(double pmTm) {
		this.pmTm = pmTm;
	}

	public double getQcTm() {
		return qcTm;
	}

	public void setQcTm(double qcTm) {
		this.qcTm = qcTm;
	}

	public double getRaTm() {
		return raTm;
	}

	public void setRaTm(double raTm) {
		this.raTm = raTm;
	}

	public double getFulcrumTm() {
		return fulcrumTm;
	}

	public void setFulcrumTm(double fulcrumTm) {
		this.fulcrumTm = fulcrumTm;
	}

	public double getNewstaffTm() {
		return newstaffTm;
	}

	public void setNewstaffTm(double newstaffTm) {
		this.newstaffTm = newstaffTm;
	}

	public String getSatgrpNm() {
		return satgrpNm;
	}

	public void setSatgrpNm(String satgrpNm) {
		this.satgrpNm = satgrpNm;
	}

	public double getAdtteamTm() {
		return adtteamTm;
	}

	public void setAdtteamTm(double adtteamTm) {
		this.adtteamTm = adtteamTm;
	}

	public String getChargptrnm() {
		return chargptrnm;
	}

	public void setChargptrnm(String chargptrnm) {
		this.chargptrnm = chargptrnm;
	}

	public String getChargmgrnm() {
		return chargmgrnm;
	}

	public void setChargmgrnm(String chargmgrnm) {
		this.chargmgrnm = chargmgrnm;
	}

	public Boolean getCanEdit() {
		return canEdit;
	}

	public void setCanEdit(Boolean canEdit) {
		this.canEdit = canEdit;
	}

	public Boolean getCanDelete() {
		return canDelete;
	}

	public void setCanDelete(Boolean canDelete) {
		this.canDelete = canDelete;
	}

	public String getPrflId() {
		return prflId;
	}

	public void setPrflId(String prflId) {
		this.prflId = prflId;
	}

	public String getPrjtcd1() {
		return prjtcd1;
	}

	public void setPrjtcd1(String prjtcd1) {
		this.prjtcd1 = prjtcd1;
	}

	public String getPrjtcd2() {
		return prjtcd2;
	}

	public void setPrjtcd2(String prjtcd2) {
		this.prjtcd2 = prjtcd2;
	}

	public String getPrjtcd3() {
		return prjtcd3;
	}

	public void setPrjtcd3(String prjtcd3) {
		this.prjtcd3 = prjtcd3;
	}

	public String getSatgrp1ExptYn() {
		return satgrp1ExptYn;
	}

	public void setSatgrp1ExptYn(String satgrp1ExptYn) {
		this.satgrp1ExptYn = satgrp1ExptYn;
	}

	public String getIndusCd() {
		return indusCd;
	}

	public void setIndusCd(String indusCd) {
		this.indusCd = indusCd;
	}

	public String getListDvCd() {
		return listDvCd;
	}

	public void setListDvCd(String listDvCd) {
		this.listDvCd = listDvCd;
	}

	public String getIndivAsset() {
		return indivAsset;
	}

	public void setIndivAsset(String indivAsset) {
		this.indivAsset = indivAsset;
	}

	public String getConsoSales() {
		return consoSales;
	}

	public void setConsoSales(String consoSales) {
		this.consoSales = consoSales;
	}

	public String getConsoAsset() {
		return consoAsset;
	}

	public void setConsoAsset(String consoAsset) {
		this.consoAsset = consoAsset;
	}

	public String getDesigAdtYn() {
		return desigAdtYn;
	}

	public void setDesigAdtYn(String desigAdtYn) {
		this.desigAdtYn = desigAdtYn;
	}

	public String getBizRprtYn() {
		return bizRprtYn;
	}

	public void setBizRprtYn(String bizRprtYn) {
		this.bizRprtYn = bizRprtYn;
	}

	public double getPriorAdtTm() {
		return priorAdtTm;
	}

	public void setPriorAdtTm(double priorAdtTm) {
		this.priorAdtTm = priorAdtTm;
	}

	public String getUsaListYn() {
		return usaListYn;
	}

	public void setUsaListYn(String usaListYn) {
		this.usaListYn = usaListYn;
	}

	public String getHoldingsDivCd() {
		return holdingsDivCd;
	}

	public void setHoldingsDivCd(String holdingsDivCd) {
		this.holdingsDivCd = holdingsDivCd;
	}

	public String getConsoFinstatYn() {
		return consoFinstatYn;
	}

	public void setConsoFinstatYn(String consoFinstatYn) {
		this.consoFinstatYn = consoFinstatYn;
	}

	public long getSbsidCnt() {
		return sbsidCnt;
	}

	public void setSbsidCnt(long sbsidCnt) {
		this.sbsidCnt = sbsidCnt;
	}

	public String getFirstAdtYn() {
		return firstAdtYn;
	}

	public void setFirstAdtYn(String firstAdtYn) {
		this.firstAdtYn = firstAdtYn;
	}

	public String getFirstAdtFctr() {
		return firstAdtFctr;
	}

	public void setFirstAdtFctr(String firstAdtFctr) {
		this.firstAdtFctr = firstAdtFctr;
	}

	public String getIfrsYn() {
		return ifrsYn;
	}

	public void setIfrsYn(String ifrsYn) {
		this.ifrsYn = ifrsYn;
	}

	public String getConsoInvnt() {
		return consoInvnt;
	}

	public void setConsoInvnt(String consoInvnt) {
		this.consoInvnt = consoInvnt;
	}

	public String getConsoAccntReceiv() {
		return consoAccntReceiv;
	}

	public void setConsoAccntReceiv(String consoAccntReceiv) {
		this.consoAccntReceiv = consoAccntReceiv;
	}

	public String getCurrConsoLossYn() {
		return currConsoLossYn;
	}

	public void setCurrConsoLossYn(String currConsoLossYn) {
		this.currConsoLossYn = currConsoLossYn;
	}

	public String getCurrAdtopinCd() {
		return currAdtopinCd;
	}

	public void setCurrAdtopinCd(String currAdtopinCd) {
		this.currAdtopinCd = currAdtopinCd;
	}

	public String getIntrTranAssetSales() {
		return intrTranAssetSales;
	}

	public void setIntrTranAssetSales(String intrTranAssetSales) {
		this.intrTranAssetSales = intrTranAssetSales;
	}

	public String getRelatCompAsset() {
		return relatCompAsset;
	}

	public void setRelatCompAsset(String relatCompAsset) {
		this.relatCompAsset = relatCompAsset;
	}

	public String getSbsidAssetWithIntrTran() {
		return sbsidAssetWithIntrTran;
	}

	public void setSbsidAssetWithIntrTran(String sbsidAssetWithIntrTran) {
		this.sbsidAssetWithIntrTran = sbsidAssetWithIntrTran;
	}

	public String getSbsidSalesWithIntrTran() {
		return sbsidSalesWithIntrTran;
	}

	public void setSbsidSalesWithIntrTran(String sbsidSalesWithIntrTran) {
		this.sbsidSalesWithIntrTran = sbsidSalesWithIntrTran;
	}

	public double getRaBdgtTm() {
		return raBdgtTm;
	}

	public void setRaBdgtTm(double raBdgtTm) {
		this.raBdgtTm = raBdgtTm;
	}

	public String getRaBdgtMgr() {
		return raBdgtMgr;
	}

	public void setRaBdgtMgr(String raBdgtMgr) {
		this.raBdgtMgr = raBdgtMgr;
	}

	public double getFlcmBdgtTm() {
		return flcmBdgtTm;
	}

	public void setFlcmBdgtTm(double flcmBdgtTm) {
		this.flcmBdgtTm = flcmBdgtTm;
	}

	public String getFlcmBdgtMgr() {
		return flcmBdgtMgr;
	}

	public void setFlcmBdgtMgr(String flcmBdgtMgr) {
		this.flcmBdgtMgr = flcmBdgtMgr;
	}

	public double getNewStfBdgtTm() {
		return newStfBdgtTm;
	}

	public void setNewStfBdgtTm(double newStfBdgtTm) {
		this.newStfBdgtTm = newStfBdgtTm;
	}

	public double getOtherBdgtTm() {
		return otherBdgtTm;
	}

	public void setOtherBdgtTm(double otherBdgtTm) {
		this.otherBdgtTm = otherBdgtTm;
	}

	public String getRjctCmnt() {
		return rjctCmnt;
	}

	public void setRjctCmnt(String rjctCmnt) {
		this.rjctCmnt = rjctCmnt;
	}

	public String getAprvReqEmplno() {
		return aprvReqEmplno;
	}

	public void setAprvReqEmplno(String aprvReqEmplno) {
		this.aprvReqEmplno = aprvReqEmplno;
	}

	public String getAprvReqDt() {
		return aprvReqDt;
	}

	public void setAprvReqDt(String aprvReqDt) {
		this.aprvReqDt = aprvReqDt;
	}

	public String getAprvCmpltDt() {
		return aprvCmpltDt;
	}

	public void setAprvCmpltDt(String aprvCmpltDt) {
		this.aprvCmpltDt = aprvCmpltDt;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public String getRetainTranYn() {
		return retainTranYn;
	}

	public void setRetainTranYn(String retainTranYn) {
		this.retainTranYn = retainTranYn;
	}

	public String getRetainTranDt() {
		return retainTranDt;
	}

	public void setRetainTranDt(String retainTranDt) {
		this.retainTranDt = retainTranDt;
	}

	public String getRetainTranEmplno() {
		return retainTranEmplno;
	}

	public void setRetainTranEmplno(String retainTranEmplno) {
		this.retainTranEmplno = retainTranEmplno;
	}

	public String getSatTrgtYn() {
		return satTrgtYn;
	}

	public void setSatTrgtYn(String satTrgtYn) {
		this.satTrgtYn = satTrgtYn;
	}

	public String getNewStfWkmnsp() {
		return newStfWkmnsp;
	}

	public void setNewStfWkmnsp(String newStfWkmnsp) {
		this.newStfWkmnsp = newStfWkmnsp;
	}

	public String getOtherWkmnsp() {
		return otherWkmnsp;
	}

	public void setOtherWkmnsp(String otherWkmnsp) {
		this.otherWkmnsp = otherWkmnsp;
	}

	public String getFactorVal() {
		return factorVal;
	}

	public void setFactorVal(String factorVal) {
		this.factorVal = factorVal;
	}

	public String getIntrAdtYn() {
		return intrAdtYn;
	}

	public void setIntrAdtYn(String intrAdtYn) {
		this.intrAdtYn = intrAdtYn;
	}

	public double getIntrAdtTm() {
		return intrAdtTm;
	}

	public void setIntrAdtTm(double intrAdtTm) {
		this.intrAdtTm = intrAdtTm;
	}

	public double getCalAdtTm() {
		return calAdtTm;
	}

	public void setCalAdtTm(double calAdtTm) {
		this.calAdtTm = calAdtTm;
	}

	public String getRetainScdlYn() {
		return retainScdlYn;
	}

	public void setRetainScdlYn(String retainScdlYn) {
		this.retainScdlYn = retainScdlYn;
	}

	public String getRetainScdlDt() {
		return retainScdlDt;
	}

	public void setRetainScdlDt(String retainScdlDt) {
		this.retainScdlDt = retainScdlDt;
	}

	public String getRetainScdlBaseDt() {
		return retainScdlBaseDt;
	}

	public void setRetainScdlBaseDt(String retainScdlBaseDt) {
		this.retainScdlBaseDt = retainScdlBaseDt;
	}

	public long getCmpltCnt() {
		return cmpltCnt;
	}

	public void setCmpltCnt(long cmpltCnt) {
		this.cmpltCnt = cmpltCnt;
	}

	public String getRcrdMgr2() {
		return rcrdMgr2;
	}

	public void setRcrdMgr2(String rcrdMgr2) {
		this.rcrdMgr2 = rcrdMgr2;
	}

	public String getRcrdMgr3() {
		return rcrdMgr3;
	}

	public void setRcrdMgr3(String rcrdMgr3) {
		this.rcrdMgr3 = rcrdMgr3;
	}

	public String getFormDiv() {
		return formDiv;
	}

	public void setFormDiv(String formDiv) {
		this.formDiv = formDiv;
	}

	public String getCalcOnlyYn() {
		return calcOnlyYn;
	}

	public void setCalcOnlyYn(String calcOnlyYn) {
		this.calcOnlyYn = calcOnlyYn;
	}

	public String getCompNm() {
		return compNm;
	}

	public void setCompNm(String compNm) {
		this.compNm = compNm;
	}

	public String getBizFrdt() {
		return bizFrdt;
	}

	public void setBizFrdt(String bizFrdt) {
		this.bizFrdt = bizFrdt;
	}

	public String getFirstBizDt() {
		return firstBizDt;
	}

	public void setFirstBizDt(String firstBizDt) {
		this.firstBizDt = firstBizDt;
	}

	public long getContiAdtCnt() {
		return contiAdtCnt;
	}

	public void setContiAdtCnt(long contiAdtCnt) {
		this.contiAdtCnt = contiAdtCnt;
	}

	public String getIntrAdtYcnt() {
		return intrAdtYcnt;
	}

	public void setIntrAdtYcnt(String intrAdtYcnt) {
		this.intrAdtYcnt = intrAdtYcnt;
	}

	public long getRevwCnt() {
		return revwCnt;
	}

	public void setRevwCnt(long revwCnt) {
		this.revwCnt = revwCnt;
	}

	public String getKamYn() {
		return kamYn;
	}

	public void setKamYn(String kamYn) {
		this.kamYn = kamYn;
	}

	public String getPriorAdtopinChgYn() {
		return priorAdtopinChgYn;
	}

	public void setPriorAdtopinChgYn(String priorAdtopinChgYn) {
		this.priorAdtopinChgYn = priorAdtopinChgYn;
	}

	public String getPriorAdtopinChgFctr() {
		return priorAdtopinChgFctr;
	}

	public void setPriorAdtopinChgFctr(String priorAdtopinChgFctr) {
		this.priorAdtopinChgFctr = priorAdtopinChgFctr;
	}

	public String getPriorLossYn() {
		return priorLossYn;
	}

	public void setPriorLossYn(String priorLossYn) {
		this.priorLossYn = priorLossYn;
	}

	public String getPriorLossFctr() {
		return priorLossFctr;
	}

	public void setPriorLossFctr(String priorLossFctr) {
		this.priorLossFctr = priorLossFctr;
	}

	public String getEtcFctr() {
		return etcFctr;
	}

	public void setEtcFctr(String etcFctr) {
		this.etcFctr = etcFctr;
	}

	public String getMinMaxYn() {
		return minMaxYn;
	}

	public void setMinMaxYn(String minMaxYn) {
		this.minMaxYn = minMaxYn;
	}

	public String getMinMaxReasonCd() {
		return minMaxReasonCd;
	}

	public void setMinMaxReasonCd(String minMaxReasonCd) {
		this.minMaxReasonCd = minMaxReasonCd;
	}

	public String getMinMaxReasonDscrt() {
		return minMaxReasonDscrt;
	}

	public void setMinMaxReasonDscrt(String minMaxReasonDscrt) {
		this.minMaxReasonDscrt = minMaxReasonDscrt;
	}

	public double getFstAdtTm() {
		return fstAdtTm;
	}

	public void setFstAdtTm(double fstAdtTm) {
		this.fstAdtTm = fstAdtTm;
	}

	public String getFstAdtrBaseWkmnspYn() {
		return fstAdtrBaseWkmnspYn;
	}

	public void setFstAdtrBaseWkmnspYn(String fstAdtrBaseWkmnspYn) {
		this.fstAdtrBaseWkmnspYn = fstAdtrBaseWkmnspYn;
	}

	public double getFstAdtrWkmnsp() {
		return fstAdtrWkmnsp;
	}

	public void setFstAdtrWkmnsp(double fstAdtrWkmnsp) {
		this.fstAdtrWkmnsp = fstAdtrWkmnsp;
	}

	public String getFstAdtTmReasonCd() {
		return fstAdtTmReasonCd;
	}

	public void setFstAdtTmReasonCd(String fstAdtTmReasonCd) {
		this.fstAdtTmReasonCd = fstAdtTmReasonCd;
	}

	public String getFstAdtTmReasonDscrt() {
		return fstAdtTmReasonDscrt;
	}

	public void setFstAdtTmReasonDscrt(String fstAdtTmReasonDscrt) {
		this.fstAdtTmReasonDscrt = fstAdtTmReasonDscrt;
	}

	public String getFstWkmnspReasonCd() {
		return fstWkmnspReasonCd;
	}

	public void setFstWkmnspReasonCd(String fstWkmnspReasonCd) {
		this.fstWkmnspReasonCd = fstWkmnspReasonCd;
	}

	public String getFstWkmnspReasonDscrt() {
		return fstWkmnspReasonDscrt;
	}

	public void setFstWkmnspReasonDscrt(String fstWkmnspReasonDscrt) {
		this.fstWkmnspReasonDscrt = fstWkmnspReasonDscrt;
	}

	public String getPriorAdtrCd() {
		return priorAdtrCd;
	}

	public void setPriorAdtrCd(String priorAdtrCd) {
		this.priorAdtrCd = priorAdtrCd;
	}

	public double getPriorBaseWkmnsp() {
		return priorBaseWkmnsp;
	}

	public void setPriorBaseWkmnsp(double priorBaseWkmnsp) {
		this.priorBaseWkmnsp = priorBaseWkmnsp;
	}

	public String getPriorAdtrBaseWkmnspYn() {
		return priorAdtrBaseWkmnspYn;
	}

	public void setPriorAdtrBaseWkmnspYn(String priorAdtrBaseWkmnspYn) {
		this.priorAdtrBaseWkmnspYn = priorAdtrBaseWkmnspYn;
	}

	public double getPriorAdtrWkmnsp() {
		return priorAdtrWkmnsp;
	}

	public void setPriorAdtrWkmnsp(double priorAdtrWkmnsp) {
		this.priorAdtrWkmnsp = priorAdtrWkmnsp;
	}

	public String getPriorAdtTmReasonCd() {
		return priorAdtTmReasonCd;
	}

	public void setPriorAdtTmReasonCd(String priorAdtTmReasonCd) {
		this.priorAdtTmReasonCd = priorAdtTmReasonCd;
	}

	public String getPriorAdtTmReasonDscrt() {
		return priorAdtTmReasonDscrt;
	}

	public void setPriorAdtTmReasonDscrt(String priorAdtTmReasonDscrt) {
		this.priorAdtTmReasonDscrt = priorAdtTmReasonDscrt;
	}

	public String getPriorWkmnspReasonCd() {
		return priorWkmnspReasonCd;
	}

	public void setPriorWkmnspReasonCd(String priorWkmnspReasonCd) {
		this.priorWkmnspReasonCd = priorWkmnspReasonCd;
	}

	public String getPriorWkmnspReasonDscrt() {
		return priorWkmnspReasonDscrt;
	}

	public void setPriorWkmnspReasonDscrt(String priorWkmnspReasonDscrt) {
		this.priorWkmnspReasonDscrt = priorWkmnspReasonDscrt;
	}

	public double getMinTm() {
		return minTm;
	}

	public void setMinTm(double minTm) {
		this.minTm = minTm;
	}

	public double getMaxTm() {
		return maxTm;
	}

	public void setMaxTm(double maxTm) {
		this.maxTm = maxTm;
	}

	public double getIntplSat() {
		return intplSat;
	}

	public void setIntplSat(double intplSat) {
		this.intplSat = intplSat;
	}

	public String getCalResult() {
		return calResult;
	}

	public void setCalResult(String calResult) {
		this.calResult = calResult;
	}

	public String getYearRate() {
		return yearRate;
	}

	public void setYearRate(String yearRate) {
		this.yearRate = yearRate;
	}

	public String getIntrAdtFctr() {
		return intrAdtFctr;
	}

	public void setIntrAdtFctr(String intrAdtFctr) {
		this.intrAdtFctr = intrAdtFctr;
	}

	public String getIfrsFctr() {
		return ifrsFctr;
	}

	public void setIfrsFctr(String ifrsFctr) {
		this.ifrsFctr = ifrsFctr;
	}

	public String getHoldingsFctr() {
		return holdingsFctr;
	}

	public void setHoldingsFctr(String holdingsFctr) {
		this.holdingsFctr = holdingsFctr;
	}

	public String getUsaListFctr() {
		return usaListFctr;
	}

	public void setUsaListFctr(String usaListFctr) {
		this.usaListFctr = usaListFctr;
	}

	public String getRiskFctr() {
		return riskFctr;
	}

	public void setRiskFctr(String riskFctr) {
		this.riskFctr = riskFctr;
	}

	public String getKamFctr() {
		return kamFctr;
	}

	public void setKamFctr(String kamFctr) {
		this.kamFctr = kamFctr;
	}

	public String getConsoFinstatFctr() {
		return consoFinstatFctr;
	}

	public void setConsoFinstatFctr(String consoFinstatFctr) {
		this.consoFinstatFctr = consoFinstatFctr;
	}

	public String getSbsidCntFctr() {
		return sbsidCntFctr;
	}

	public void setSbsidCntFctr(String sbsidCntFctr) {
		this.sbsidCntFctr = sbsidCntFctr;
	}

	public String getCurrAdtopinFctr() {
		return currAdtopinFctr;
	}

	public void setCurrAdtopinFctr(String currAdtopinFctr) {
		this.currAdtopinFctr = currAdtopinFctr;
	}

	public String getCurrConsoLossFctr() {
		return currConsoLossFctr;
	}

	public void setCurrConsoLossFctr(String currConsoLossFctr) {
		this.currConsoLossFctr = currConsoLossFctr;
	}

	public String getSameAdtrSbsidYn() {
		return sameAdtrSbsidYn;
	}

	public void setSameAdtrSbsidYn(String sameAdtrSbsidYn) {
		this.sameAdtrSbsidYn = sameAdtrSbsidYn;
	}

	public double getFstMaxTm() {
		return fstMaxTm;
	}

	public void setFstMaxTm(double fstMaxTm) {
		this.fstMaxTm = fstMaxTm;
	}

	public double getWkmnspSat() {
		return wkmnspSat;
	}

	public void setWkmnspSat(double wkmnspSat) {
		this.wkmnspSat = wkmnspSat;
	}

	public double getCalSat() {
		return calSat;
	}

	public void setCalSat(double calSat) {
		this.calSat = calSat;
	}

	public String getClntNm() {
		return clntNm;
	}

	public void setClntNm(String clntNm) {
		this.clntNm = clntNm;
	}

	public String getShrtnm() {
		return shrtnm;
	}

	public void setShrtnm(String shrtnm) {
		this.shrtnm = shrtnm;
	}

	public String getPrdtcd() {
		return prdtcd;
	}

	public void setPrdtcd(String prdtcd) {
		this.prdtcd = prdtcd;
	}

	public String getChargbon() {
		return chargbon;
	}

	public void setChargbon(String chargbon) {
		this.chargbon = chargbon;
	}

	public String getChargptrNm() {
		return chargptrNm;
	}

	public void setChargptrNm(String chargptrNm) {
		this.chargptrNm = chargptrNm;
	}

	public String getChargptrGradNm() {
		return chargptrGradNm;
	}

	public void setChargptrGradNm(String chargptrGradNm) {
		this.chargptrGradNm = chargptrGradNm;
	}

	public String getChargptrTeamcd() {
		return chargptrTeamcd;
	}

	public void setChargptrTeamcd(String chargptrTeamcd) {
		this.chargptrTeamcd = chargptrTeamcd;
	}

	public String getChargptrBonbcd() {
		return chargptrBonbcd;
	}

	public void setChargptrBonbcd(String chargptrBonbcd) {
		this.chargptrBonbcd = chargptrBonbcd;
	}

	public String getChargmgrNm() {
		return chargmgrNm;
	}

	public void setChargmgrNm(String chargmgrNm) {
		this.chargmgrNm = chargmgrNm;
	}

	public String getChargmgrGradNm() {
		return chargmgrGradNm;
	}

	public void setChargmgrGradNm(String chargmgrGradNm) {
		this.chargmgrGradNm = chargmgrGradNm;
	}

	public String getRcrdMgrNm() {
		return rcrdMgrNm;
	}

	public void setRcrdMgrNm(String rcrdMgrNm) {
		this.rcrdMgrNm = rcrdMgrNm;
	}

	public String getRcrdMgrGradNm() {
		return rcrdMgrGradNm;
	}

	public void setRcrdMgrGradNm(String rcrdMgrGradNm) {
		this.rcrdMgrGradNm = rcrdMgrGradNm;
	}

	public String getRcrdMgrNm2() {
		return rcrdMgrNm2;
	}

	public void setRcrdMgrNm2(String rcrdMgrNm2) {
		this.rcrdMgrNm2 = rcrdMgrNm2;
	}

	public String getRcrdMgrGradNm2() {
		return rcrdMgrGradNm2;
	}

	public void setRcrdMgrGradNm2(String rcrdMgrGradNm2) {
		this.rcrdMgrGradNm2 = rcrdMgrGradNm2;
	}

	public String getRcrdMgrNm3() {
		return rcrdMgrNm3;
	}

	public void setRcrdMgrNm3(String rcrdMgrNm3) {
		this.rcrdMgrNm3 = rcrdMgrNm3;
	}

	public String getRcrdMgrGradNm3() {
		return rcrdMgrGradNm3;
	}

	public void setRcrdMgrGradNm3(String rcrdMgrGradNm3) {
		this.rcrdMgrGradNm3 = rcrdMgrGradNm3;
	}

	public List<WeekVO> getsDtOfWeek() {
		return sDtOfWeek;
	}

	public void setsDtOfWeek(List<WeekVO> sDtOfWeek) {
		this.sDtOfWeek = sDtOfWeek;
	}

	public long getNetRvnu() {
		return netRvnu;
	}

	public void setNetRvnu(long netRvnu) {
		this.netRvnu = netRvnu;
	}

	public double getExpWkmnsp() {
		return expWkmnsp;
	}

	public void setExpWkmnsp(double expWkmnsp) {
		this.expWkmnsp = expWkmnsp;
	}

	public double getPlanSat() {
		return planSat;
	}

	public void setPlanSat(double planSat) {
		this.planSat = planSat;
	}

	public double getExbudgettime() {
		return exbudgettime;
	}

	public void setExbudgettime(double exbudgettime) {
		this.exbudgettime = exbudgettime;
	}

	public String getProfRetainTranYn() {
		return profRetainTranYn;
	}

	public void setProfRetainTranYn(String profRetainTranYn) {
		this.profRetainTranYn = profRetainTranYn;
	}

	public double getSatExpWkmnsp() {
		return satExpWkmnsp;
	}

	public void setSatExpWkmnsp(double satExpWkmnsp) {
		this.satExpWkmnsp = satExpWkmnsp;
	}

	public long getTotCntrtFee() {
		return totCntrtFee;
	}
	
	public void setTotCntrtFee(long totCntrtFee) {
		this.totCntrtFee = totCntrtFee;
	}

	public double getQrpTm() {
		return qrpTm;
	}

	public void setQrpTm(double qrpTm) {
		this.qrpTm = qrpTm;
	}

	public String getProfBdgtYn() {
		return profBdgtYn;
	}

	public void setProfBdgtYn(String profBdgtYn) {
		this.profBdgtYn = profBdgtYn;
	}

	public String getProfSatYn() {
		return profSatYn;
	}

	public void setProfSatYn(String profSatYn) {
		this.profSatYn = profSatYn;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getProfBdgtReason() {
		return profBdgtReason;
	}

	public String getProfSatReason() {
		return profSatReason;
	}

	public void setProfBdgtReason(String profBdgtReason) {
		this.profBdgtReason = profBdgtReason;
	}

	public void setProfSatReason(String profSatReason) {
		this.profSatReason = profSatReason;
	}	
}
