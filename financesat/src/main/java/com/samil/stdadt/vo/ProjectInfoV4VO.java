package com.samil.stdadt.vo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.samil.stdadt.comm.vo.CamelMap;

/**
 * 프로젝트 정보
 * @author shyunchoi
 *
 */
public class ProjectInfoV4VO {
	//################################################################################
	// 데이터 메타정보
	//################################################################################
	private String prflId;			// 프로파일 Id
	private String calcOnlyYn;		// 계산하기 전용 여부
	private Boolean canEdit;		// 수정 가능 여부
	private Boolean canDelete;		// 삭제 가능 여부
	private Boolean canRestore;		// 복구 가능 여부
	private Boolean canDeletePermanently;		// 영구 삭제 가능 여부
	private double otherWkmnsp;		// Other 기준 숙련도
	private double newStfWkmnsp;	// New Staff 기준 숙련도
	private double maxRatio; 		// 그룹별 상한율
	private String stat;			// Status : RG: 등록(Regist), RQ: 승인 요청(Request), RJ: 반려(Reject), CO: 승인 완료(Complete), DE: 삭제(Delete)
	private Date credt;				// 작성일
	private String creby;			// 작성자 사번
	private String crebyNm;			// 작성자 이름
	private Date moddt;				// 수정일
	private String modby;			// 수정자 사번
	private String modbyNm;			// 수정자 이름
	
	
	//################################################################################
	// 프로젝트 기본 정보
	//################################################################################
	private String compNm; 			// 회사명 -> 계산하기 전용 필드
	private String prjtCd;			// 프로젝트 코드 00000-00-000
	private String prjtCd1; 		// 프로젝트 코드1(Client Code) 00000
	private String prjtCd2; 		// 프로젝트 코드2(Service Code) 00
	private String prjtCd3; 		// 프로젝트 코드3(순번) 000
	private String prjtNm;			// 프로젝트 명: Client명/용역명
	private String clntNm;			// Client명
	private String shrtNm;			// 용역명
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
	private long cntrtFee;			// 계약 보수 (원)
	private String prjtFrdt;		// 프로젝트 시작일 2019-01-01
	private String prjtTodt;		// 프로젝트 시작일 2019-12-31
	private String bizFrdt;			// 사업연도 시작월 2019-01
	private String bizTodt;			// 사업연도 종료월 2019-12
	private String desigAdtYn; 		// 지정감사 여부
	private String desigAdtYnNm;	// 지정감사 여부
	private String satTrgtYn;		// 표준감사 대상 여부
	private String satTrgtYnNm;		// 표준감사 대상 여부 명
	private long totCntrtFee;		// 총 계약 보수 (원) - 합산대상프로젝트의 계약 보수까지의 총합계	
	
	
	
	//################################################################################
	// 그룹 정보 영역
	//################################################################################
	private String indusCd;					// 산업 분류 코드
	private String indusNm;					// 산업 분류 이름(제조업, 서비스업, 건설업, 금융업, 도소매업, 기타)	
	private String listDvCd;				// 상장 구분 코드
	private String listDvNm;				// 상장 구분 이름(상장, 코넥스, 비상장)
	private long indivAsset;				// 개별 자산
	private String bizRprtYn;				// 사업보고서 제출대상
	private String bizRprtYnNm;				// 사업보고서 제출대상
	private long consoAsset;				// 연결 자산
	private String satgrp1ExptYn;			// 표준감사그룹1 예외사항 여부 코드
	private String satgrp1ExptYnNm; 		// 표준감사그룹1 예외사항 여부 이름
	private long consoSales;				// 연결 매출
	private long compSize;					// 기업 규모 : (연결 자산 + 연결 매출)/2
	private long consoAccntReceiv;			// 연결 매출 채권
	private String rprtScdlDt;				// 보고서 발행 예정일
	private long consoInvnt;				// 연결 재고자산
	private String satgrpCd;				// 표준감사그룹 코드 
	private String satgrpNm;				// 표준감사그룹 명(그룹1 ~ 그룹 11)
	private String firstBizDt;				// 최초적용 사업연도
	private String contiAdtCnt;				// 삼일회계법인 연속감사 횟수
	private String contiAdtCntNm;			// 삼일회계법인 연속감사 횟수
	private String intrAdtYcnt;				// 내부회계감사 시행연차
	private String intrAdtYcntNm;			// 내부회계감사 시행연차
	private int revwCnt;					// 검토 횟수
	private String sameAdtrSbsidYn;			// 동일감사인이 감사하는 종속회사 또는 관계회사 존재 여부
	private long intrTranAssetSales;		// 연결조정시 제거된 내부거래(매출+자산)
	private long sbsidSalesWithIntrTran;	// 내부거래전 자회사 자산
	private long relatCompAsset;			// 관계회사의 자산 금액
	private long sbsidAssetWithIntrTran;	// 내부거래 제거전 자회사 매출
	
	//2022-02-16 남웅주
	private String etcFctrYn;				// 비상장금융회사로서 외감법 또는 자본시장법상 1,3분기 검토 의무가 있는지 여부
	private String etcFctrYnNm;				// 비상장금융회사로서 외감법 또는 자본시장법상 1,3분기 검토 의무가 있는지 여부

	//################################################################################
	// 가감 계수  영역
	//################################################################################
	private String ifrsYn;					// IFRS 여부
	private String ifrsYnNm;				// IFRS 여부
	private double ifrsFctr;				// IFRS 계수
	private String holdingsDivCd;			// 지주사 구분 코드 (금융지주사, 비금융지주사, 일반)
	private String holdingsDivNm;			// 지주사 구분 이름 
	private double holdingsFctr;			// 지주사 구분 계수
	private String firstAdtYn;				// 초조감사여부
	private String firstAdtYnNm;			// 초조감사여부
	private double firstAdtFctr;			// 초도 감사 계수 
	private String usaListYn;				// 미국 상장 여부
	private String usaListYnNm;				// 미국 상장 여부 이름
	private double usaListFctr;				// 미국 상장 계수
	private double riskFctr;				// 위험 계정 비중
	private String kamYn;					// KAM 여부 코드
	private String kamYnNm;					// KAM 여부 이름
	private double kamFctr;					// KAM 계수
	private String consoFinstatYn;			// 연결 재무제표 작성 여부
	private String consoFinstatYnNm;		// 연결 재무제표 작성 여부
	private double consoFinstatFctr;		// 연결 작성 계수
	private int sbsidCnt;					// 자회사(종속회사) 수
	private double sbsidCntFctr;			// 자회사 계수
	private String currAdtopinCd;			// 당기 예상 감사의견 코드
	private String currAdtopinNm;			// 당기 예상 감사의견 이름
	private double currAdtopinFctr;			// 당기 예상 감사의견 계수
	private String currConsoLossYn;			// 당기 연결 당기순손실예상여부
	private String currConsoLossYnNm;		// 당기 연결 당기순손실예상여부
	private double currConsoLossFctr;		// 당기 연결 당기 순손실 계수
	private String priorAdtopinChgYn;		// 전기 의견변형 여부
	private String priorAdtopinChgYnNm;		// 전기 의견변형 여부
	private double priorAdtopinChgFctr;		// 전기 의견변형 계수
	private String priorLossYn;				// 전기 손실 여부
	private String priorLossYnNm;			// 전기 손실 여부
	private double priorLossFctr;			// 전기 손실 계수
	private double etcFctr;					// 분기검토조정계수 등 기타
	private double factorVal;				// 가감요인계, 가감률
	
	//2022-02-16 남웅주
	private String satTrgtYcnt;				// 표준감사시간대상 적용 연차
	//private String satTrgtYcntNm;			// 표준감사시간대상 적용 연차
	private double fstSatTrgtBftm;			// 최초 표준감사시간 대상 직전 감사시간

	//################################################################################
	// 상하한(기준숙련도 기준) 영역
	//################################################################################
	private String minMaxYn;				// 상하한 적용 여부
	private String minMaxYnNm;				// 상하한 적용 여부
	private String minMaxReasonCd;			// 상하한 미적용시 근거 코드
	private String minMaxReasonNm;			// 상하한 미적용시 근거 이름
	private String minMaxReasonDscrt;		// 상하한 미적용시 근거 - 직접 기술
	private String fstBizDt;				// 최초적용  상한 - 최초 적용 직전 사업연도  
	private String fstMaxTm;				// 최초적용  상한 - 3년 상한
	private double fstAdtTm;				// 최초 적용 상한 - 최초 적용 직전 감사시간
	private String fstAdtrBaseWkmnspYn;     // 최초 적용 상한 - 감사팀의 숙련도 - 기준숙련도 적용 여부
	private double fstAdtrWkmnsp;			// 최초 적용 상한 - 감사팀의 숙련도
	private String fstAdtTmReasonCd;		// 최초 적용 상한 - 감사시간 수정시 근거 (일치/초도감사/직접서술)
	private String fstAdtTmReasonNm;		// 최초 적용 상한 - 감사시간 수정시 근거 (일치/초도감사/직접서술)
	private String fstAdtTmReasonDscrt;		// 최초 적용 상한 - 감사시간 수정시 근거 직접 서술
	private String fstWkmnspReasonCd;		// 최초 적용 상한 - 숙련도 근거 (기준숙련도 적용(Big 4) / 실제숙련도 / 외감실시내용을 기준으로 간단계산 / 감사계약시 합의내용 / 직접서술)
	private String fstWkmnspReasonNm;		// 최초 적용 상한 - 숙련도 근거 (기준숙련도 적용(Big 4) / 실제숙련도 / 외감실시내용을 기준으로 간단계산 / 감사계약시 합의내용 / 직접서술)
	private String fstWkmnspReasonDscrt;	// 최초 적용 상한 - 숙련도 근거 직접 서술
	
	
	private String priorAdtrCd;				// 전기기준 상하한 - 전기감사인
	private String priorAdtrNm;				// 전기기준 상하한 - 전기감사인
	private double priorBaseWkmnsp;			// 전기기준 상하한 - 전기 기준 숙련도
	private double priorAdtTm;				// 전기기준 상하한 - 전기 감사 시간
	private String priorAdtrBaseWkmnspYn;   // 전기기준 상하한 - 감사팀의 숙련도 - 기준숙련도 적용 여부
	private double priorAdtrWkmnsp;			// 전기기준 상하한 - 전기 감사팀 숙련도
	private String priorAdtTmReasonCd;		// 전기기준 상하한 - 감사시간 근거 (일치/초도감사/직접서술)
	private String priorAdtTmReasonNm;		// 전기기준 상하한 - 감사시간 근거 (일치/초도감사/직접서술)
	private String priorAdtTmReasonDscrt;	// 전기기준 상하한 - 감사시간 수정시 근거 직접 서술
	private String priorWkmnspReasonCd;		// 전기기준 상하한 - 숙련도 근거 (기준숙련도 적용(Big 4) / 실제숙련도 / 외감실시내용을 기준으로 간단계산 / 감사계약시 합의내용 / 직접서술)
	private String priorWkmnspReasonNm;		// 전기기준 상하한 - 숙련도 근거 (기준숙련도 적용(Big 4) / 실제숙련도 / 외감실시내용을 기준으로 간단계산 / 감사계약시 합의내용 / 직접서술)
	private String priorWkmnspReasonDscrt;	// 전기기준 상하한 - 숙련도 근거 직접 서술
	private String intrAdtYn;				// 내부감사시간 - 전기 내부회계감사시간 포함 여부
	private String intrAdtYnNm;				// 내부감사시간 - 전기 내부회계감사시간 포함 여부
	private double intrAdtTm;				// 내부감사시간 - 전기 내부회계감사시간
	private double minTm;					// 상하한 - 하한
	private double maxTm;					// 상하한 - 상한
	
	
	//################################################################################
	// 표준감사시간 산출 영역
	//################################################################################
	private double intplSat;		// 표준감사시간표(보간법)
	private double calResult;		// 산식결과
	private double yearRate;		// 적용요율
	private double calAdtTm;		// 산출된 감사시간(상하한 적용 후)(a)
	private double intrAdtFctr;		// 내부회계감사시간 계수(b)
	private double calSat;			// 숙련도 반영전 표준감사시간(a*b)
	private double baseWkmnsp;		// 기준 숙련도
	private double etDfnSat;		// 한공회 표준감사시간((기본산식 외 요소를 고려한 최종 표준감사시간)
	private double etTrgtAdtTm;		// 감사계약시 합의 시간(표준감사시간 대상)
	
	private double wkmnspSat;		// 숙련도 반영된 표준 감사시간	(etDfnSat * 숙련도)
	
	
	//################################################################################
	// 표준감사시간외 예산입력 & 예산 배분
	//################################################################################
	// 표준감사시간외 예산
	private double etcBdgtTm;		// 표준감사시간외 감사등 예산 시간
	// 분배 예산
	private double totPrjtBdgt;		// 총 프로젝트 예산 (etTrgtAdtTm + etcBdgtTm)
	private double raBdgtTm;
	private double flcmBdgtTm;
	private double otherBdgtTm;	
	
	
	//################################################################################
	// 등록 승인/반려 & 리테인 전송 관련
	//################################################################################
	private String rjctCmnt;		// 반려의견
	private String aprvReqEmplno;	// 결재 요청자 사번
	private Date aprvReqDt;			// 결재 요청일 
	private Date aprvCmpltDt;		// 결재 완료(or 반려)일  
	private int cmpltCnt;			// 결재 완료 횟수
	private String retainTranYn;	// 리테인 전송 여부
	
	private String profSatYn;		// 프로파일에 등록된 표준감사시간 수정 허용 여부
	private String profSatReason;	// 표준감사시간 수정불가시 사유 - LOCKBIZTODT(LOCK적용 결산연도), NEARBIZTODT(본부별 Lock 적용 기준 개월수)
	private String profBdgtYn;		// 프로파일에 등록된 Budget 수정 허용 여부
	private String profBdgtReason;	// 프로파일에 등록된 표준감사시간 수정 허용 여부
	
	private String remark;			// 비고
	
	//################################################################################
	// Getter, Setter
	//################################################################################
	public String getPrflId() {
		return prflId;
	}
	public Boolean getCanEdit() {
		return canEdit;
	}
	public Boolean getCanDelete() {
		return canDelete;
	}
	public double getOtherWkmnsp() {
		return otherWkmnsp;
	}
	public double getNewStfWkmnsp() {
		return newStfWkmnsp;
	}
	public String getStat() {
		return stat;
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
	public String getCompNm() {
		return compNm;
	}
	public String getCalcOnlyYn() {
		return calcOnlyYn;
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
	public long getCntrtFee() {
		return cntrtFee;
	}
	public String getPrjtFrdt() {
		return prjtFrdt;
	}
	public String getPrjtTodt() {
		return prjtTodt;
	}
	public String getBizFrdt() {
		return bizFrdt;
	}
	public String getBizTodt() {
		return bizTodt;
	}
	public String getDesigAdtYn() {
		return desigAdtYn;
	}
	public String getDesigAdtYnNm() {
		return desigAdtYnNm;
	}
	public String getSatTrgtYn() {
		return satTrgtYn;
	}
	public String getSatTrgtYnNm() {
		return satTrgtYnNm;
	}
	public long getTotCntrtFee() {
		return totCntrtFee;
	}
	public String getIndusCd() {
		return indusCd;
	}
	public String getIndusNm() {
		return indusNm;
	}
	public String getListDvCd() {
		return listDvCd;
	}
	public String getListDvNm() {
		return listDvNm;
	}
	public long getIndivAsset() {
		return indivAsset;
	}
	public String getBizRprtYn() {
		return bizRprtYn;
	}
	public String getBizRprtYnNm() {
		return bizRprtYnNm;
	}
	public long getConsoAsset() {
		return consoAsset;
	}
	public String getSatgrp1ExptYn() {
		return satgrp1ExptYn;
	}
	public String getSatgrp1ExptYnNm() {
		return satgrp1ExptYnNm;
	}
	public long getConsoSales() {
		return consoSales;
	}
	public long getCompSize() {
		return compSize;
	}
	public long getConsoAccntReceiv() {
		return consoAccntReceiv;
	}
	public String getRprtScdlDt() {
		return rprtScdlDt;
	}
	public long getConsoInvnt() {
		return consoInvnt;
	}
	public String getSatgrpCd() {
		return satgrpCd;
	}
	public String getSatgrpNm() {
		return satgrpNm;
	}
	public String getFirstBizDt() {
		return firstBizDt;
	}
	
	public int getRevwCnt() {
		return revwCnt;
	}
	public String getSameAdtrSbsidYn() {
		return sameAdtrSbsidYn;
	}
	public long getIntrTranAssetSales() {
		return intrTranAssetSales;
	}
	public long getSbsidSalesWithIntrTran() {
		return sbsidSalesWithIntrTran;
	}
	public long getRelatCompAsset() {
		return relatCompAsset;
	}
	public long getSbsidAssetWithIntrTran() {
		return sbsidAssetWithIntrTran;
	}
	public String getIfrsYn() {
		return ifrsYn;
	}
	public String getIfrsYnNm() {
		return ifrsYnNm;
	}
	public double getIfrsFctr() {
		return ifrsFctr;
	}
	public String getHoldingsDivCd() {
		return holdingsDivCd;
	}
	public String getHoldingsDivNm() {
		return holdingsDivNm;
	}
	public double getHoldingsFctr() {
		return holdingsFctr;
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
	public String getUsaListYn() {
		return usaListYn;
	}
	public String getUsaListYnNm() {
		return usaListYnNm;
	}
	public double getUsaListFctr() {
		return usaListFctr;
	}
	public double getRiskFctr() {
		return riskFctr;
	}
	public String getKamYn() {
		return kamYn;
	}
	public String getKamYnNm() {
		return kamYnNm;
	}
	public double getKamFctr() {
		return kamFctr;
	}
	public String getConsoFinstatYn() {
		return consoFinstatYn;
	}
	public String getConsoFinstatYnNm() {
		return consoFinstatYnNm;
	}
	public double getConsoFinstatFctr() {
		return consoFinstatFctr;
	}
	public int getSbsidCnt() {
		return sbsidCnt;
	}
	public double getSbsidCntFctr() {
		return sbsidCntFctr;
	}
	public String getCurrAdtopinCd() {
		return currAdtopinCd;
	}
	public String getCurrAdtopinNm() {
		return currAdtopinNm;
	}
	public double getCurrAdtopinFctr() {
		return currAdtopinFctr;
	}
	public String getCurrConsoLossYn() {
		return currConsoLossYn;
	}
	public String getCurrConsoLossYnNm() {
		return currConsoLossYnNm;
	}
	public double getCurrConsoLossFctr() {
		return currConsoLossFctr;
	}
	public String getPriorAdtopinChgYn() {
		return priorAdtopinChgYn;
	}
	public String getPriorAdtopinChgYnNm() {
		return priorAdtopinChgYnNm;
	}
	public double getPriorAdtopinChgFctr() {
		return priorAdtopinChgFctr;
	}
	public String getPriorLossYn() {
		return priorLossYn;
	}
	public String getPriorLossYnNm() {
		return priorLossYnNm;
	}
	public double getPriorLossFctr() {
		return priorLossFctr;
	}
	public double getEtcFctr() {
		return etcFctr;
	}
	public double getFactorVal() {
		return factorVal;
	}
	public String getMinMaxYn() {
		return minMaxYn;
	}
	public String getMinMaxYnNm() {
		return minMaxYnNm;
	}
	public String getMinMaxReasonCd() {
		return minMaxReasonCd;
	}
	public String getMinMaxReasonNm() {
		return minMaxReasonNm;
	}
	public String getMinMaxReasonDscrt() {
		return minMaxReasonDscrt;
	}
	public String getFstBizDt() {
		return fstBizDt;
	}
	public String getFstMaxTm() {
		return fstMaxTm;
	}
	public double getFstAdtTm() {
		return fstAdtTm;
	}
	public double getFstAdtrWkmnsp() {
		return fstAdtrWkmnsp;
	}
	public String getFstAdtTmReasonCd() {
		return fstAdtTmReasonCd;
	}
	public String getFstAdtTmReasonNm() {
		return fstAdtTmReasonNm;
	}
	public String getFstAdtTmReasonDscrt() {
		return fstAdtTmReasonDscrt;
	}
	public String getFstWkmnspReasonCd() {
		return fstWkmnspReasonCd;
	}
	public String getFstWkmnspReasonNm() {
		return fstWkmnspReasonNm;
	}
	public String getFstWkmnspReasonDscrt() {
		return fstWkmnspReasonDscrt;
	}
	public String getPriorAdtrCd() {
		return priorAdtrCd;
	}
	public String getPriorAdtrNm() {
		return priorAdtrNm;
	}
	public double getPriorBaseWkmnsp() {
		return priorBaseWkmnsp;
	}
	public double getPriorAdtTm() {
		return priorAdtTm;
	}
	public double getPriorAdtrWkmnsp() {
		return priorAdtrWkmnsp;
	}
	public String getPriorAdtTmReasonCd() {
		return priorAdtTmReasonCd;
	}
	public String getPriorAdtTmReasonNm() {
		return priorAdtTmReasonNm;
	}
	public String getPriorAdtTmReasonDscrt() {
		return priorAdtTmReasonDscrt;
	}
	public String getPriorWkmnspReasonCd() {
		return priorWkmnspReasonCd;
	}
	public String getPriorWkmnspReasonNm() {
		return priorWkmnspReasonNm;
	}
	public String getPriorWkmnspReasonDscrt() {
		return priorWkmnspReasonDscrt;
	}
	public double getMinTm() {
		return minTm;
	}
	public double getMaxTm() {
		return maxTm;
	}
	public double getIntplSat() {
		return intplSat;
	}
	public double getCalResult() {
		return calResult;
	}
	public double getYearRate() {
		return yearRate;
	}
	public double getCalAdtTm() {
		return calAdtTm;
	}
	public double getIntrAdtFctr() {
		return intrAdtFctr;
	}
	public double getCalSat() {
		return calSat;
	}
	public double getBaseWkmnsp() {
		return baseWkmnsp;
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
	public double getFlcmBdgtTm() {
		return flcmBdgtTm;
	}
	public double getOtherBdgtTm() {
		return otherBdgtTm;
	}
	public String getRjctCmnt() {
		return rjctCmnt;
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
	public int getCmpltCnt() {
		return cmpltCnt;
	}
	public String getRetainTranYn() {
		return retainTranYn;
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
	public void setOtherWkmnsp(double otherWkmnsp) {
		this.otherWkmnsp = otherWkmnsp;
	}
	public void setNewStfWkmnsp(double newStfWkmnsp) {
		this.newStfWkmnsp = newStfWkmnsp;
	}
	public void setStat(String stat) {
		this.stat = stat;
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
	public void setCompNm(String compNm) {
		this.compNm = compNm;
	}
	public void setCalcOnlyYn(String calcOnlyYn) {
		this.calcOnlyYn = calcOnlyYn;
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
	public void setCntrtFee(long cntrtFee) {
		this.cntrtFee = cntrtFee;
	}
	public void setPrjtFrdt(String prjtFrdt) {
		this.prjtFrdt = prjtFrdt;
	}
	public void setPrjtTodt(String prjtTodt) {
		this.prjtTodt = prjtTodt;
	}
	public void setBizFrdt(String bizFrdt) {
		this.bizFrdt = bizFrdt;
	}
	public void setBizTodt(String bizTodt) {
		this.bizTodt = bizTodt;
	}
	public void setDesigAdtYn(String desigAdtYn) {
		this.desigAdtYn = desigAdtYn;
	}
	public void setDesigAdtYnNm(String desigAdtYnNm) {
		this.desigAdtYnNm = desigAdtYnNm;
	}
	public void setSatTrgtYn(String satTrgtYn) {
		this.satTrgtYn = satTrgtYn;
	}
	public void setSatTrgtYnNm(String satTrgtYnNm) {
		this.satTrgtYnNm = satTrgtYnNm;
	}
	public void setTotCntrtFee(long totCntrtFee) {
		this.totCntrtFee = totCntrtFee;
	}
	public void setIndusCd(String indusCd) {
		this.indusCd = indusCd;
	}
	public void setIndusNm(String indusNm) {
		this.indusNm = indusNm;
	}
	public void setListDvCd(String listDvCd) {
		this.listDvCd = listDvCd;
	}
	public void setListDvNm(String listDvNm) {
		this.listDvNm = listDvNm;
	}
	public void setIndivAsset(long indivAsset) {
		this.indivAsset = indivAsset;
	}
	public void setBizRprtYn(String bizRprtYn) {
		this.bizRprtYn = bizRprtYn;
	}
	public void setBizRprtYnNm(String bizRprtYnNm) {
		this.bizRprtYnNm = bizRprtYnNm;
	}
	public void setConsoAsset(long consoAsset) {
		this.consoAsset = consoAsset;
	}
	public void setSatgrp1ExptYn(String satgrp1ExptYn) {
		this.satgrp1ExptYn = satgrp1ExptYn;
	}
	public void setSatgrp1ExptYnNm(String satgrp1ExptYnNm) {
		this.satgrp1ExptYnNm = satgrp1ExptYnNm;
	}
	public void setConsoSales(long consoSales) {
		this.consoSales = consoSales;
	}
	public void setCompSize(long compSize) {
		this.compSize = compSize;
	}
	public void setConsoAccntReceiv(long consoAccntReceiv) {
		this.consoAccntReceiv = consoAccntReceiv;
	}
	public void setRprtScdlDt(String rprtScdlDt) {
		this.rprtScdlDt = rprtScdlDt;
	}
	public void setConsoInvnt(long consoInvnt) {
		this.consoInvnt = consoInvnt;
	}
	public void setSatgrpCd(String satgrpCd) {
		this.satgrpCd = satgrpCd;
	}
	public void setSatgrpNm(String satgrpNm) {
		this.satgrpNm = satgrpNm;
	}
	public void setFirstBizDt(String firstBizDt) {
		this.firstBizDt = firstBizDt;
	}
	public void setRevwCnt(int revwCnt) {
		this.revwCnt = revwCnt;
	}
	public void setSameAdtrSbsidYn(String sameAdtrSbsidYn) {
		this.sameAdtrSbsidYn = sameAdtrSbsidYn;
	}
	public void setIntrTranAssetSales(long intrTranAssetSales) {
		this.intrTranAssetSales = intrTranAssetSales;
	}
	public void setSbsidSalesWithIntrTran(long sbsidSalesWithIntrTran) {
		this.sbsidSalesWithIntrTran = sbsidSalesWithIntrTran;
	}
	public void setRelatCompAsset(long relatCompAsset) {
		this.relatCompAsset = relatCompAsset;
	}
	public void setSbsidAssetWithIntrTran(long sbsidAssetWithIntrTran) {
		this.sbsidAssetWithIntrTran = sbsidAssetWithIntrTran;
	}
	public void setIfrsYn(String ifrsYn) {
		this.ifrsYn = ifrsYn;
	}
	public void setIfrsYnNm(String ifrsYnNm) {
		this.ifrsYnNm = ifrsYnNm;
	}
	public void setIfrsFctr(double ifrsFctr) {
		this.ifrsFctr = ifrsFctr;
	}
	public void setHoldingsDivCd(String holdingsDivCd) {
		this.holdingsDivCd = holdingsDivCd;
	}
	public void setHoldingsDivNm(String holdingsDivNm) {
		this.holdingsDivNm = holdingsDivNm;
	}
	public void setHoldingsFctr(double holdingsFctr) {
		this.holdingsFctr = holdingsFctr;
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
	public void setUsaListYn(String usaListYn) {
		this.usaListYn = usaListYn;
	}
	public void setUsaListYnNm(String usaListYnNm) {
		this.usaListYnNm = usaListYnNm;
	}
	public void setUsaListFctr(double usaListFctr) {
		this.usaListFctr = usaListFctr;
	}
	public void setRiskFctr(double riskFctr) {
		this.riskFctr = riskFctr;
	}
	public void setKamYn(String kamYn) {
		this.kamYn = kamYn;
	}
	public void setKamYnNm(String kamYnNm) {
		this.kamYnNm = kamYnNm;
	}
	public void setKamFctr(double kamFctr) {
		this.kamFctr = kamFctr;
	}
	public void setConsoFinstatYn(String consoFinstatYn) {
		this.consoFinstatYn = consoFinstatYn;
	}
	public void setConsoFinstatYnNm(String consoFinstatYnNm) {
		this.consoFinstatYnNm = consoFinstatYnNm;
	}
	public void setConsoFinstatFctr(double consoFinstatFctr) {
		this.consoFinstatFctr = consoFinstatFctr;
	}
	public void setSbsidCnt(int sbsidCnt) {
		this.sbsidCnt = sbsidCnt;
	}
	public void setSbsidCntFctr(double sbsidCntFctr) {
		this.sbsidCntFctr = sbsidCntFctr;
	}
	public void setCurrAdtopinCd(String currAdtopinCd) {
		this.currAdtopinCd = currAdtopinCd;
	}
	public void setCurrAdtopinNm(String currAdtopinNm) {
		this.currAdtopinNm = currAdtopinNm;
	}
	public void setCurrAdtopinFctr(double currAdtopinFctr) {
		this.currAdtopinFctr = currAdtopinFctr;
	}
	public void setCurrConsoLossYn(String currConsoLossYn) {
		this.currConsoLossYn = currConsoLossYn;
	}
	public void setCurrConsoLossYnNm(String currConsoLossYnNm) {
		this.currConsoLossYnNm = currConsoLossYnNm;
	}
	public void setCurrConsoLossFctr(double currConsoLossFctr) {
		this.currConsoLossFctr = currConsoLossFctr;
	}
	public void setPriorAdtopinChgYn(String priorAdtopinChgYn) {
		this.priorAdtopinChgYn = priorAdtopinChgYn;
	}
	public void setPriorAdtopinChgYnNm(String priorAdtopinChgYnNm) {
		this.priorAdtopinChgYnNm = priorAdtopinChgYnNm;
	}
	public void setPriorAdtopinChgFctr(double priorAdtopinChgFctr) {
		this.priorAdtopinChgFctr = priorAdtopinChgFctr;
	}
	public void setPriorLossYn(String priorLossYn) {
		this.priorLossYn = priorLossYn;
	}
	public void setPriorLossYnNm(String priorLossYnNm) {
		this.priorLossYnNm = priorLossYnNm;
	}
	public void setPriorLossFctr(double priorLossFctr) {
		this.priorLossFctr = priorLossFctr;
	}
	public void setEtcFctr(double etcFctr) {
		this.etcFctr = etcFctr;
	}
	public void setFactorVal(double factorVal) {
		this.factorVal = factorVal;
	}
	public void setMinMaxYn(String minMaxYn) {
		this.minMaxYn = minMaxYn;
	}
	public void setMinMaxYnNm(String minMaxYnNm) {
		this.minMaxYnNm = minMaxYnNm;
	}
	public void setMinMaxReasonCd(String minMaxReasonCd) {
		this.minMaxReasonCd = minMaxReasonCd;
	}
	public void setMinMaxReasonNm(String minMaxReasonNm) {
		this.minMaxReasonNm = minMaxReasonNm;
	}
	public void setMinMaxReasonDscrt(String minMaxReasonDscrt) {
		this.minMaxReasonDscrt = minMaxReasonDscrt;
	}
	public void setFstBizDt(String fstBizDt) {
		this.fstBizDt = fstBizDt;
	}
	public void setFstMaxTm(String fstMaxTm) {
		this.fstMaxTm = fstMaxTm;
	}
	public void setFstAdtTm(double fstAdtTm) {
		this.fstAdtTm = fstAdtTm;
	}
	public void setFstAdtrWkmnsp(double fstAdtrWkmnsp) {
		this.fstAdtrWkmnsp = fstAdtrWkmnsp;
	}
	public void setFstAdtTmReasonCd(String fstAdtTmReasonCd) {
		this.fstAdtTmReasonCd = fstAdtTmReasonCd;
	}
	public void setFstAdtTmReasonNm(String fstAdtTmReasonNm) {
		this.fstAdtTmReasonNm = fstAdtTmReasonNm;
	}
	public void setFstAdtTmReasonDscrt(String fstAdtTmReasonDscrt) {
		this.fstAdtTmReasonDscrt = fstAdtTmReasonDscrt;
	}
	public void setFstWkmnspReasonCd(String fstWkmnspReasonCd) {
		this.fstWkmnspReasonCd = fstWkmnspReasonCd;
	}
	public void setFstWkmnspReasonNm(String fstWkmnspReasonNm) {
		this.fstWkmnspReasonNm = fstWkmnspReasonNm;
	}
	public void setFstWkmnspReasonDscrt(String fstWkmnspReasonDscrt) {
		this.fstWkmnspReasonDscrt = fstWkmnspReasonDscrt;
	}
	public void setPriorAdtrCd(String priorAdtrCd) {
		this.priorAdtrCd = priorAdtrCd;
	}
	public void setPriorAdtrNm(String priorAdtrNm) {
		this.priorAdtrNm = priorAdtrNm;
	}
	public void setPriorBaseWkmnsp(double priorBaseWkmnsp) {
		this.priorBaseWkmnsp = priorBaseWkmnsp;
	}
	public void setPriorAdtTm(double priorAdtTm) {
		this.priorAdtTm = priorAdtTm;
	}
	public void setPriorAdtrWkmnsp(double priorAdtrWkmnsp) {
		this.priorAdtrWkmnsp = priorAdtrWkmnsp;
	}
	public void setPriorAdtTmReasonCd(String priorAdtTmReasonCd) {
		this.priorAdtTmReasonCd = priorAdtTmReasonCd;
	}
	public void setPriorAdtTmReasonNm(String priorAdtTmReasonNm) {
		this.priorAdtTmReasonNm = priorAdtTmReasonNm;
	}
	public void setPriorAdtTmReasonDscrt(String priorAdtTmReasonDscrt) {
		this.priorAdtTmReasonDscrt = priorAdtTmReasonDscrt;
	}
	public void setPriorWkmnspReasonCd(String priorWkmnspReasonCd) {
		this.priorWkmnspReasonCd = priorWkmnspReasonCd;
	}
	public void setPriorWkmnspReasonNm(String priorWkmnspReasonNm) {
		this.priorWkmnspReasonNm = priorWkmnspReasonNm;
	}
	public void setPriorWkmnspReasonDscrt(String priorWkmnspReasonDscrt) {
		this.priorWkmnspReasonDscrt = priorWkmnspReasonDscrt;
	}
	public void setMinTm(double minTm) {
		this.minTm = minTm;
	}
	public void setMaxTm(double maxTm) {
		this.maxTm = maxTm;
	}
	public void setIntplSat(double intplSat) {
		this.intplSat = intplSat;
	}
	public void setCalResult(double calResult) {
		this.calResult = calResult;
	}
	public void setYearRate(double yearRate) {
		this.yearRate = yearRate;
	}
	public void setCalAdtTm(double calAdtTm) {
		this.calAdtTm = calAdtTm;
	}
	public void setIntrAdtFctr(double intrAdtFctr) {
		this.intrAdtFctr = intrAdtFctr;
	}
	public void setCalSat(double calSat) {
		this.calSat = calSat;
	}
	public void setBaseWkmnsp(double baseWkmnsp) {
		this.baseWkmnsp = baseWkmnsp;
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
	public void setFlcmBdgtTm(double flcmBdgtTm) {
		this.flcmBdgtTm = flcmBdgtTm;
	}
	public void setOtherBdgtTm(double otherBdgtTm) {
		this.otherBdgtTm = otherBdgtTm;
	}
	public void setRjctCmnt(String rjctCmnt) {
		this.rjctCmnt = rjctCmnt;
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
	public void setCmpltCnt(int cmpltCnt) {
		this.cmpltCnt = cmpltCnt;
	}
	public void setRetainTranYn(String retainTranYn) {
		this.retainTranYn = retainTranYn;
	}
	public String getIntrAdtYn() {
		return intrAdtYn;
	}
	public String getIntrAdtYnNm() {
		return intrAdtYnNm;
	}
	public double getIntrAdtTm() {
		return intrAdtTm;
	}
	public void setIntrAdtYn(String intrAdtYn) {
		this.intrAdtYn = intrAdtYn;
	}
	public void setIntrAdtYnNm(String intrAdtYnNm) {
		this.intrAdtYnNm = intrAdtYnNm;
	}
	public void setIntrAdtTm(double intrAdtTm) {
		this.intrAdtTm = intrAdtTm;
	}
	public String getContiAdtCnt() {
		return contiAdtCnt;
	}
	public String getContiAdtCntNm() {
		return contiAdtCntNm;
	}
	public String getIntrAdtYcnt() {
		return intrAdtYcnt;
	}
	public String getIntrAdtYcntNm() {
		return intrAdtYcntNm;
	}
	public void setContiAdtCnt(String contiAdtCnt) {
		this.contiAdtCnt = contiAdtCnt;
	}
	public void setContiAdtCntNm(String contiAdtCntNm) {
		this.contiAdtCntNm = contiAdtCntNm;
	}
	public void setIntrAdtYcnt(String intrAdtYcnt) {
		this.intrAdtYcnt = intrAdtYcnt;
	}
	public void setIntrAdtYcntNm(String intrAdtYcntNm) {
		this.intrAdtYcntNm = intrAdtYcntNm;
	}
	public String getFstAdtrBaseWkmnspYn() {
		return fstAdtrBaseWkmnspYn;
	}
	public String getPriorAdtrBaseWkmnspYn() {
		return priorAdtrBaseWkmnspYn;
	}
	public void setFstAdtrBaseWkmnspYn(String fstAdtrBaseWkmnspYn) {
		this.fstAdtrBaseWkmnspYn = fstAdtrBaseWkmnspYn;
	}
	public void setPriorAdtrBaseWkmnspYn(String priorAdtrBaseWkmnspYn) {
		this.priorAdtrBaseWkmnspYn = priorAdtrBaseWkmnspYn;
	}
	public double getMaxRatio() {
		return maxRatio;
	}
	public void setMaxRatio(double maxRatio) {
		this.maxRatio = maxRatio;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Boolean getCanDeletePermanently() {
		return canDeletePermanently;
	}
	public void setCanDeletePermanently(Boolean canDeletePermanently) {
		this.canDeletePermanently = canDeletePermanently;
	}
	public Boolean getCanRestore() {
		return canRestore;
	}
	public void setCanRestore(Boolean canRestore) {
		this.canRestore = canRestore;
	}
	public String getProfSatYn() {
		return profSatYn;
	}
	public void setProfSatYn(String profSatYn) {
		this.profSatYn = profSatYn;
	}
	public String getProfBdgtYn() {
		return profBdgtYn;
	}
	public void setProfBdgtYn(String profBdgtYn) {
		this.profBdgtYn = profBdgtYn;
	}
	public String getProfSatReason() {
		return profSatReason;
	}
	public String getProfBdgtReason() {
		return profBdgtReason;
	}
	public void setProfSatReason(String profSatReason) {
		this.profSatReason = profSatReason;
	}
	public void setProfBdgtReason(String profBdgtReason) {
		this.profBdgtReason = profBdgtReason;
	}
	//2022-02-16 남웅주
	public String getEtcFctrYn() {
		return etcFctrYn;
	}
	public void setEtcFctrYn(String etcFctrYn) {
		this.etcFctrYn = etcFctrYn;
	}
	public String getSatTrgtYcnt() {
		return satTrgtYcnt;
	}
	public void setSatTrgtYcnt(String satTrgtYcnt) {
		this.satTrgtYcnt = satTrgtYcnt;
	}
	public double getFstSatTrgtBftm() {
		return fstSatTrgtBftm;
	}
	public void setFstSatTrgtBftm(double fstSatTrgtBftm) {
		this.fstSatTrgtBftm = fstSatTrgtBftm;
	}
	public String getEtcFctrYnNm() {
		return etcFctrYnNm;
	}
	public void setEtcFctrYnNm(String etcFctrYnNm) {
		this.etcFctrYnNm = etcFctrYnNm;
	}
//	public String getSatTrgtYcntNm() {
//		return satTrgtYcntNm;
//	}
//	public void setSatTrgtYcntNm(String satTrgtYcntNm) {
//		this.satTrgtYcntNm = satTrgtYcntNm;
//	}
}	
