package com.samil.stdadt.vo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * 프로젝트 정보
 * @author shyunchoi
 *
 */
public class ProjectInfoVO {

	private String prflId;			// 프로파일 Id
	private Boolean canEdit;			// 수정 가능 여부
	private Boolean canDelete;			// 삭제 가능 여부
	
	// 프로젝트 기본 정보
	private String prjtCd;			// 프로젝트 코드 00000-00-000
	private String prjtCd1; 		// 프로젝트 코드1(Client Code) 00000
	private String prjtCd2; 		// 프로젝트 코드2(Service Code) 00
	private String prjtCd3; 		// 프로젝트 코드3(순번) 000
	private String prjtNm;			// 프로젝트 명: Client명/용역명
	private String clntNm;			// Client명
	private String shrtNm;			// 용역명
	
	private long cntrtFee;			// 계약 보수 (원)
	
	private String chargPtr;		// 프로젝트 담당 파트너 사번
	private String chargPtrNm;		// 프로젝트 담당 파트너 이름
	private String chargPtrGradNm;	// 프로젝트 담당 파트너 직급명
	private String chargMgr;		// 프로젝트 담당 매니저 사번
	private String chargMgrNm;		// 프로젝트 담당 매니저 이름
	private String chargMgrGradNm;	// 프로젝트 담당 매니저 직급명
	private String rcrdMgr;			// 프로젝트 Record 매니저 사번
	private String rcrdMgrNm;		// 프로젝트 Record 매니저 이름
	private String rcrdMgrGradNm;	// 프로젝트 Record 매니저 직급명
	private String rcrdMgr2;		// 프로젝트 Record 매니저 사번2
	private String rcrdMgrNm2;		// 프로젝트 Record 매니저 이름2
	private String rcrdMgrGradNm2;	// 프로젝트 Record 매니저 직급명2
	private String rcrdMgr3;		// 프로젝트 Record 매니저 사번3
	private String rcrdMgrNm3;		// 프로젝트 Record 매니저 이름3
	private String rcrdMgrGradNm3;	// 프로젝트 Record 매니저 직급명3
	
	private String desigAdtYn; 		// 지정감사 여부
	private String desigAdtYnNm;	// 지정감사 여부
	private String prjtFrdt;		// 프로젝트 시작일 2019-01-01
	private String prjtTodt;		// 프로젝트 시작일 2019-12-31
	
	// 합산대상 프로젝트
	private List<SubProjectVO> subPrjt; // 합산 대상 프로젝트 정보
	private long totCntrtFee;		// 총 계약 보수 (원) - 합산대상프로젝트의 계약 보수까지의 총합계	
	
	
	private String satTrgtYn;		// 표준감사 대상 여부
	private String satTrgtYnNm;		// 표준감사 대상 여부 명
	
	private String indusCd;			// 산업 분류 코드
	private String indusNm;			// 산업 분류 이름(제조업, 서비스업, 건설업, 금융업, 도소매업, 기타)	
	private long indivAsset;		// 개별 자산
	private long consoSales;		// 연결 매출
	private long consoAsset;		// 연결 자산
	private long compSize;			// 기업 규모 : (연결 자산 + 연결 매출)/2
	
	private String listDvCd;		// 상장 구분 코드
	private String listDvNm;		// 상장 구분 이름(상장, 코넥스, 비상장)
	private String bizRprtYn;		// 사업보고서 제출대상
	private String bizRprtYnNm;		// 사업보고서 제출대상
	
	private double priorAdtTm;		// 전기 감사 시간
	
	private String satgrp1ExptYn;	// 표준감사그룹1 예외사항 여부 : Y:무조건 그룹1로 선택
	private String satgrp1ExptYnNm;
	
	private String maxSatgrpCd;		// 해당 연도의 마지막 표준감사그룹 코드
	private String satgrpCd;		// 표준감사그룹 코드 
	private String satgrpNm;		// 표준감사그룹 명(그룹1 ~ 그룹 11)

	
	private String usaListYn;		// 미국 상장 여부
	private String usaListYnNm;		// 미국 상장 여부 이름
	private String holdingsDivCd;	// 지주사 구분 코드 (금융지주사, 비금융지주사, 일반)
	private String holdingsDivNm;
	private String consoFinstatYn;	// 연결 재무제표 작성 여부
	private String consoFinstatYnNm;	// 연결 재무제표 작성 여부
	private int sbsidCnt;			// 자회사(종속회사) 수
	
	private String rprtScdlDt;		// 보고서 발행 예정일
	private String firstAdtYn;		// 초조감사여부
	private String firstAdtYnNm;	// 초조감사여부
	private double firstAdtFctr;	// 초도 감사 계수 - 그룹1일때 사용
	private String ifrsYn;			// IFRS 여부
	private String ifrsYnNm;		// IFRS 여부
	

	private long consoInvnt;		// 연결 재고자산
	private long consoAccntReceiv;	// 연결 매출 채권
	private String currConsoLossYn;	// 당기 연결 당기순손실예상여부
	private String currConsoLossYnNm;	// 당기 연결 당기순손실예상여부
	private String currAdtopinCd;	// 당기예상감사의견 코드
	private String currAdtopinNm;	// 당기예상감사의견 이름
	
	
	private long intrTranAssetSales;		// 연결조정시 제거된 내부거래(매출+자산)
	private long relatCompAsset;			// 관계회사의 자산 금액
	private long sbsidSalesWithIntrTran;	// 내부거래전 자회사 자산
	private long sbsidAssetWithIntrTran;	// 내부거래 제거전 자회사 매출
	
	// 숙련도
	private double baseWkmnsp;		// 기준 숙련도
	private double otherWkmnsp;		// Other 기준 숙련도
	private double newStfWkmnsp;	// New Staff 기준 숙련도
	
	// 표준감사시간
	private double factorVal;		// 가감률
	private String intrAdtYn;		// 내부회계감사(2조 이상) 여부
	private String intrAdtYnNm;		// 내부회계감사(2조 이상) 여부 명
	private double calAdtTm;		// 산출된 감사시간(a)
	private double intrAdtTm;		// 내부 감사시간(b)
	
	private double calSat;			// 숙련도 반영전 표준감사시간(a+b) 계산식에 의해 산출된 기준 표준감사시간(숙련도 반영 전)
	private double etDfnSat;		// 한공회 표준감사시간
	private double etTrgtAdtTm;		// 감사계약시 합의 시간(표준감사시간 대상)
	
	private double wkmnspSat;		// 숙련도 반영된 표준 감사시간	(etDfnSat * 숙련도)
	private double etcBdgtTm;		// 표준감사시간외 감사등 예산 시간
	
	
	
	// 분배 예산
	private double totPrjtBdgt;		// 총 프로젝트 예산 (etDfnSat + etcBdgtTm)
	private double raBdgtTm;
	private String raBdgtMgr;
	private String raBdgtMgrNm;
	private String raBdgtMgrGradNm;
	private double flcmBdgtTm;
	private String flcmBdgtMgr;
	private String flcmBdgtMgrNm;
	private String flcmBdgtMgrGradNm;
	private double otherBdgtTm;

	
	// 기타
	private String stat;			// Status : RG: 등록(Regist), RQ: 승인 요청(Request), RJ: 반려(Reject), CO: 승인 완료(Complete), DE: 삭제(Delete)
	private String rjctCmnt;		// 반려의견
	private Date credt;				// 작성일
	private String creby;				// 작성자 사번
	private String crebyNm;			// 작성자 이름
	private Date moddt;				// 수정일
	private String modby;				// 수정자 사번
	private String modbyNm;			// 수정자 이름
	
	private String aprvReqEmplno;	// 결재 요청자 사번
	private Date aprvReqDt;			// 결재 요청일 
	private Date aprvCmpltDt;		// 결재 완료(or 반려)일  
	private int cmpltCnt;			// 결재 완료 횟수
	
	private String retainTranYn;	// 리테인 전송 여부
	
	private String jsonStr;
	
	public void addPrjtAdd(SubProjectVO subPrjt) {
		if(this.subPrjt == null) {
			this.subPrjt = new ArrayList<SubProjectVO>();
		}
		this.subPrjt.add(subPrjt);
	}

	public String getPrflId() {
		return prflId;
	}

	public Boolean getCanEdit() {
		return canEdit;
	}

	public Boolean getCanDelete() {
		return canDelete;
	}

	public String getPrjtCd() {
		return prjtCd;
	}

	public String getPrjtCd1() {
		return prjtCd1;
	}

	public String getPrjtCd2() {
		return prjtCd2;
	}

	public String getPrjtCd3() {
		return prjtCd3;
	}

	public String getPrjtNm() {
		return prjtNm;
	}

	public String getClntNm() {
		return clntNm;
	}

	public String getShrtNm() {
		return shrtNm;
	}

	public long getCntrtFee() {
		return cntrtFee;
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

	public String getRcrdMgr() {
		return rcrdMgr;
	}

	public String getRcrdMgrNm() {
		return rcrdMgrNm;
	}

	public String getRcrdMgrGradNm() {
		return rcrdMgrGradNm;
	}

	public String getDesigAdtYn() {
		return desigAdtYn;
	}

	public String getDesigAdtYnNm() {
		return desigAdtYnNm;
	}

	public String getPrjtFrdt() {
		return prjtFrdt;
	}

	public String getPrjtTodt() {
		return prjtTodt;
	}

	public List<SubProjectVO> getSubPrjt() {
		return subPrjt;
	}

	public long getTotCntrtFee() {
		return totCntrtFee;
	}

	public String getSatTrgtYn() {
		return satTrgtYn;
	}

	public String getSatTrgtYnNm() {
		return satTrgtYnNm;
	}

	public String getIndusCd() {
		return indusCd;
	}

	public String getIndusNm() {
		return indusNm;
	}

	public long getIndivAsset() {
		return indivAsset;
	}

	public long getConsoSales() {
		return consoSales;
	}

	public long getConsoAsset() {
		return consoAsset;
	}

	public long getCompSize() {
		return compSize;
	}

	public String getListDvCd() {
		return listDvCd;
	}

	public String getListDvNm() {
		return listDvNm;
	}

	public String getBizRprtYn() {
		return bizRprtYn;
	}

	public String getBizRprtYnNm() {
		return bizRprtYnNm;
	}

	public double getPriorAdtTm() {
		return priorAdtTm;
	}

	public String getSatgrp1ExptYn() {
		return satgrp1ExptYn;
	}

	public String getSatgrp1ExptYnNm() {
		return satgrp1ExptYnNm;
	}

	public String getMaxSatgrpCd() {
		return maxSatgrpCd;
	}

	public String getSatgrpCd() {
		return satgrpCd;
	}

	public String getSatgrpNm() {
		return satgrpNm;
	}

	public String getUsaListYn() {
		return usaListYn;
	}

	public String getUsaListYnNm() {
		return usaListYnNm;
	}

	public String getHoldingsDivCd() {
		return holdingsDivCd;
	}

	public String getHoldingsDivNm() {
		return holdingsDivNm;
	}

	public String getConsoFinstatYn() {
		return consoFinstatYn;
	}

	public String getConsoFinstatYnNm() {
		return consoFinstatYnNm;
	}

	public int getSbsidCnt() {
		return sbsidCnt;
	}

	public String getRprtScdlDt() {
		return rprtScdlDt;
	}

	public String getFirstAdtYn() {
		return firstAdtYn;
	}

	public String getFirstAdtYnNm() {
		return firstAdtYnNm;
	}

	public double getFirstAdtFctr() {
		return firstAdtFctr;
	}

	public String getIfrsYn() {
		return ifrsYn;
	}

	public String getIfrsYnNm() {
		return ifrsYnNm;
	}

	public long getConsoInvnt() {
		return consoInvnt;
	}

	public long getConsoAccntReceiv() {
		return consoAccntReceiv;
	}

	public String getCurrConsoLossYn() {
		return currConsoLossYn;
	}

	public String getCurrConsoLossYnNm() {
		return currConsoLossYnNm;
	}

	public String getCurrAdtopinCd() {
		return currAdtopinCd;
	}

	public String getCurrAdtopinNm() {
		return currAdtopinNm;
	}

	public long getIntrTranAssetSales() {
		return intrTranAssetSales;
	}

	public long getRelatCompAsset() {
		return relatCompAsset;
	}

	public long getSbsidSalesWithIntrTran() {
		return sbsidSalesWithIntrTran;
	}

	public long getSbsidAssetWithIntrTran() {
		return sbsidAssetWithIntrTran;
	}

	public double getFactorVal() {
		return factorVal;
	}

	public double getBaseWkmnsp() {
		return baseWkmnsp;
	}

	public String getIntrAdtYn() {
		return intrAdtYn;
	}

	public String getIntrAdtYnNm() {
		return intrAdtYnNm;
	}

	public double getCalAdtTm() {
		return calAdtTm;
	}

	public double getIntrAdtTm() {
		return intrAdtTm;
	}

	public double getCalSat() {
		return calSat;
	}

	public double getEtDfnSat() {
		return etDfnSat;
	}

	public double getEtTrgtAdtTm() {
		return etTrgtAdtTm;
	}

	public double getWkmnspSat() {
		return wkmnspSat;
	}

	public double getEtcBdgtTm() {
		return etcBdgtTm;
	}

	public double getTotPrjtBdgt() {
		return totPrjtBdgt;
	}

	public double getRaBdgtTm() {
		return raBdgtTm;
	}

	public String getRaBdgtMgr() {
		return raBdgtMgr;
	}

	public String getRaBdgtMgrNm() {
		return raBdgtMgrNm;
	}

	public String getRaBdgtMgrGradNm() {
		return raBdgtMgrGradNm;
	}

	public double getFlcmBdgtTm() {
		return flcmBdgtTm;
	}

	public String getFlcmBdgtMgr() {
		return flcmBdgtMgr;
	}

	public String getFlcmBdgtMgrNm() {
		return flcmBdgtMgrNm;
	}

	public String getFlcmBdgtMgrGradNm() {
		return flcmBdgtMgrGradNm;
	}

	public double getOtherBdgtTm() {
		return otherBdgtTm;
	}

	public String getStat() {
		return stat;
	}

	public String getRjctCmnt() {
		return rjctCmnt;
	}

	public Date getCredt() {
		return credt;
	}

	public String getCreby() {
		return creby;
	}

	public String getCrebyNm() {
		return crebyNm;
	}

	public Date getModdt() {
		return moddt;
	}

	public String getModby() {
		return modby;
	}

	public String getModbyNm() {
		return modbyNm;
	}

	public String getAprvReqEmplno() {
		return aprvReqEmplno;
	}

	public Date getAprvReqDt() {
		return aprvReqDt;
	}

	public Date getAprvCmpltDt() {
		return aprvCmpltDt;
	}

	public String getRetainTranYn() {
		return retainTranYn;
	}

	public String getJsonStr() {
		return jsonStr;
	}

	public void setPrflId(String prflId) {
		this.prflId = prflId;
	}

	public void setCanEdit(Boolean canEdit) {
		this.canEdit = canEdit;
	}

	public void setCanDelete(Boolean canDelete) {
		this.canDelete = canDelete;
	}

	public void setPrjtCd(String prjtCd) {
		this.prjtCd = prjtCd;
	}

	public void setPrjtCd1(String prjtCd1) {
		this.prjtCd1 = prjtCd1;
	}

	public void setPrjtCd2(String prjtCd2) {
		this.prjtCd2 = prjtCd2;
	}

	public void setPrjtCd3(String prjtCd3) {
		this.prjtCd3 = prjtCd3;
	}

	public void setPrjtNm(String prjtNm) {
		this.prjtNm = prjtNm;
	}

	public void setClntNm(String clntNm) {
		this.clntNm = clntNm;
	}

	public void setShrtNm(String shrtNm) {
		this.shrtNm = shrtNm;
	}

	public void setCntrtFee(long cntrtFee) {
		this.cntrtFee = cntrtFee;
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

	public void setRcrdMgr(String rcrdMgr) {
		this.rcrdMgr = rcrdMgr;
	}

	public void setRcrdMgrNm(String rcrdMgrNm) {
		this.rcrdMgrNm = rcrdMgrNm;
	}

	public void setRcrdMgrGradNm(String rcrdMgrGradNm) {
		this.rcrdMgrGradNm = rcrdMgrGradNm;
	}

	public void setDesigAdtYn(String desigAdtYn) {
		this.desigAdtYn = desigAdtYn;
	}

	public void setDesigAdtYnNm(String desigAdtYnNm) {
		this.desigAdtYnNm = desigAdtYnNm;
	}

	public void setPrjtFrdt(String prjtFrdt) {
		this.prjtFrdt = prjtFrdt;
	}

	public void setPrjtTodt(String prjtTodt) {
		this.prjtTodt = prjtTodt;
	}

	public void setSubPrjt(List<SubProjectVO> subPrjt) {
		this.subPrjt = subPrjt;
	}

	public void setTotCntrtFee(long totCntrtFee) {
		this.totCntrtFee = totCntrtFee;
	}

	public void setSatTrgtYn(String satTrgtYn) {
		this.satTrgtYn = satTrgtYn;
	}

	public void setSatTrgtYnNm(String satTrgtYnNm) {
		this.satTrgtYnNm = satTrgtYnNm;
	}

	public void setIndusCd(String indusCd) {
		this.indusCd = indusCd;
	}

	public void setIndusNm(String indusNm) {
		this.indusNm = indusNm;
	}

	public void setIndivAsset(long indivAsset) {
		this.indivAsset = indivAsset;
	}

	public void setConsoSales(long consoSales) {
		this.consoSales = consoSales;
	}

	public void setConsoAsset(long consoAsset) {
		this.consoAsset = consoAsset;
	}

	public void setCompSize(long compSize) {
		this.compSize = compSize;
	}

	public void setListDvCd(String listDvCd) {
		this.listDvCd = listDvCd;
	}

	public void setListDvNm(String listDvNm) {
		this.listDvNm = listDvNm;
	}

	public void setBizRprtYn(String bizRprtYn) {
		this.bizRprtYn = bizRprtYn;
	}

	public void setBizRprtYnNm(String bizRprtYnNm) {
		this.bizRprtYnNm = bizRprtYnNm;
	}

	public void setPriorAdtTm(double priorAdtTm) {
		this.priorAdtTm = priorAdtTm;
	}

	public void setSatgrp1ExptYn(String satgrp1ExptYn) {
		this.satgrp1ExptYn = satgrp1ExptYn;
	}

	public void setSatgrp1ExptYnNm(String satgrp1ExptYnNm) {
		this.satgrp1ExptYnNm = satgrp1ExptYnNm;
	}

	public void setMaxSatgrpCd(String maxSatgrpCd) {
		this.maxSatgrpCd = maxSatgrpCd;
	}

	public void setSatgrpCd(String satgrpCd) {
		this.satgrpCd = satgrpCd;
	}

	public void setSatgrpNm(String satgrpNm) {
		this.satgrpNm = satgrpNm;
	}

	public void setUsaListYn(String usaListYn) {
		this.usaListYn = usaListYn;
	}

	public void setUsaListYnNm(String usaListYnNm) {
		this.usaListYnNm = usaListYnNm;
	}

	public void setHoldingsDivCd(String holdingsDivCd) {
		this.holdingsDivCd = holdingsDivCd;
	}

	public void setHoldingsDivNm(String holdingsDivNm) {
		this.holdingsDivNm = holdingsDivNm;
	}

	public void setConsoFinstatYn(String consoFinstatYn) {
		this.consoFinstatYn = consoFinstatYn;
	}

	public void setConsoFinstatYnNm(String consoFinstatYnNm) {
		this.consoFinstatYnNm = consoFinstatYnNm;
	}

	public void setSbsidCnt(int sbsidCnt) {
		this.sbsidCnt = sbsidCnt;
	}

	public void setRprtScdlDt(String rprtScdlDt) {
		this.rprtScdlDt = rprtScdlDt;
	}

	public void setFirstAdtYn(String firstAdtYn) {
		this.firstAdtYn = firstAdtYn;
	}

	public void setFirstAdtYnNm(String firstAdtYnNm) {
		this.firstAdtYnNm = firstAdtYnNm;
	}

	public void setFirstAdtFctr(double firstAdtFctr) {
		this.firstAdtFctr = firstAdtFctr;
	}

	public void setIfrsYn(String ifrsYn) {
		this.ifrsYn = ifrsYn;
	}

	public void setIfrsYnNm(String ifrsYnNm) {
		this.ifrsYnNm = ifrsYnNm;
	}

	public void setConsoInvnt(long consoInvnt) {
		this.consoInvnt = consoInvnt;
	}

	public void setConsoAccntReceiv(long consoAccntReceiv) {
		this.consoAccntReceiv = consoAccntReceiv;
	}

	public void setCurrConsoLossYn(String currConsoLossYn) {
		this.currConsoLossYn = currConsoLossYn;
	}

	public void setCurrConsoLossYnNm(String currConsoLossYnNm) {
		this.currConsoLossYnNm = currConsoLossYnNm;
	}

	public void setCurrAdtopinCd(String currAdtopinCd) {
		this.currAdtopinCd = currAdtopinCd;
	}

	public void setCurrAdtopinNm(String currAdtopinNm) {
		this.currAdtopinNm = currAdtopinNm;
	}

	public void setIntrTranAssetSales(long intrTranAssetSales) {
		this.intrTranAssetSales = intrTranAssetSales;
	}

	public void setRelatCompAsset(long relatCompAsset) {
		this.relatCompAsset = relatCompAsset;
	}

	public void setSbsidSalesWithIntrTran(long sbsidSalesWithIntrTran) {
		this.sbsidSalesWithIntrTran = sbsidSalesWithIntrTran;
	}

	public void setSbsidAssetWithIntrTran(long sbsidAssetWithIntrTran) {
		this.sbsidAssetWithIntrTran = sbsidAssetWithIntrTran;
	}

	public void setFactorVal(double factorVal) {
		this.factorVal = factorVal;
	}

	public void setBaseWkmnsp(double baseWkmnsp) {
		this.baseWkmnsp = baseWkmnsp;
	}

	public void setIntrAdtYn(String intrAdtYn) {
		this.intrAdtYn = intrAdtYn;
	}

	public void setIntrAdtYnNm(String intrAdtYnNm) {
		this.intrAdtYnNm = intrAdtYnNm;
	}

	public void setCalAdtTm(double calAdtTm) {
		this.calAdtTm = calAdtTm;
	}

	public void setIntrAdtTm(double intrAdtTm) {
		this.intrAdtTm = intrAdtTm;
	}

	public void setCalSat(double calSat) {
		this.calSat = calSat;
	}

	public void setEtDfnSat(double etDfnSat) {
		this.etDfnSat = etDfnSat;
	}

	public void setEtTrgtAdtTm(double etTrgtAdtTm) {
		this.etTrgtAdtTm = etTrgtAdtTm;
	}

	public void setWkmnspSat(double wkmnspSat) {
		this.wkmnspSat = wkmnspSat;
	}

	public void setEtcBdgtTm(double etcBdgtTm) {
		this.etcBdgtTm = etcBdgtTm;
	}

	public void setTotPrjtBdgt(double totPrjtBdgt) {
		this.totPrjtBdgt = totPrjtBdgt;
	}

	public void setRaBdgtTm(double raBdgtTm) {
		this.raBdgtTm = raBdgtTm;
	}

	public void setRaBdgtMgr(String raBdgtMgr) {
		this.raBdgtMgr = raBdgtMgr;
	}

	public void setRaBdgtMgrNm(String raBdgtMgrNm) {
		this.raBdgtMgrNm = raBdgtMgrNm;
	}

	public void setRaBdgtMgrGradNm(String raBdgtMgrGradNm) {
		this.raBdgtMgrGradNm = raBdgtMgrGradNm;
	}

	public void setFlcmBdgtTm(double flcmBdgtTm) {
		this.flcmBdgtTm = flcmBdgtTm;
	}

	public void setFlcmBdgtMgr(String flcmBdgtMgr) {
		this.flcmBdgtMgr = flcmBdgtMgr;
	}

	public void setFlcmBdgtMgrNm(String flcmBdgtMgrNm) {
		this.flcmBdgtMgrNm = flcmBdgtMgrNm;
	}

	public void setFlcmBdgtMgrGradNm(String flcmBdgtMgrGradNm) {
		this.flcmBdgtMgrGradNm = flcmBdgtMgrGradNm;
	}

	public void setOtherBdgtTm(double otherBdgtTm) {
		this.otherBdgtTm = otherBdgtTm;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public void setRjctCmnt(String rjctCmnt) {
		this.rjctCmnt = rjctCmnt;
	}

	public void setCredt(Date credt) {
		this.credt = credt;
	}

	public void setCreby(String creby) {
		this.creby = creby;
	}

	public void setCrebyNm(String crebyNm) {
		this.crebyNm = crebyNm;
	}

	public void setModdt(Date moddt) {
		this.moddt = moddt;
	}

	public void setModby(String modby) {
		this.modby = modby;
	}

	public void setModbyNm(String modbyNm) {
		this.modbyNm = modbyNm;
	}

	public void setAprvReqEmplno(String aprvReqEmplno) {
		this.aprvReqEmplno = aprvReqEmplno;
	}

	public void setAprvReqDt(Date aprvReqDt) {
		this.aprvReqDt = aprvReqDt;
	}

	public void setAprvCmpltDt(Date aprvCmpltDt) {
		this.aprvCmpltDt = aprvCmpltDt;
	}

	public void setRetainTranYn(String retainTranYn) {
		this.retainTranYn = retainTranYn;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}

	public double getOtherWkmnsp() {
		return otherWkmnsp;
	}

	public double getNewStfWkmnsp() {
		return newStfWkmnsp;
	}

	public void setOtherWkmnsp(double otherWkmnsp) {
		this.otherWkmnsp = otherWkmnsp;
	}

	public void setNewStfWkmnsp(double newStfWkmnsp) {
		this.newStfWkmnsp = newStfWkmnsp;
	}

	public int getCmpltCnt() {
		return cmpltCnt;
	}

	public void setCmpltCnt(int cmpltCnt) {
		this.cmpltCnt = cmpltCnt;
	}

	public String getRcrdMgr2() {
		return rcrdMgr2;
	}

	public String getRcrdMgrNm2() {
		return rcrdMgrNm2;
	}

	public String getRcrdMgrGradNm2() {
		return rcrdMgrGradNm2;
	}

	public String getRcrdMgr3() {
		return rcrdMgr3;
	}

	public String getRcrdMgrNm3() {
		return rcrdMgrNm3;
	}

	public String getRcrdMgrGradNm3() {
		return rcrdMgrGradNm3;
	}

	public void setRcrdMgr2(String rcrdMgr2) {
		this.rcrdMgr2 = rcrdMgr2;
	}

	public void setRcrdMgrNm2(String rcrdMgrNm2) {
		this.rcrdMgrNm2 = rcrdMgrNm2;
	}

	public void setRcrdMgrGradNm2(String rcrdMgrGradNm2) {
		this.rcrdMgrGradNm2 = rcrdMgrGradNm2;
	}

	public void setRcrdMgr3(String rcrdMgr3) {
		this.rcrdMgr3 = rcrdMgr3;
	}

	public void setRcrdMgrNm3(String rcrdMgrNm3) {
		this.rcrdMgrNm3 = rcrdMgrNm3;
	}

	public void setRcrdMgrGradNm3(String rcrdMgrGradNm3) {
		this.rcrdMgrGradNm3 = rcrdMgrGradNm3;
	}


}	
