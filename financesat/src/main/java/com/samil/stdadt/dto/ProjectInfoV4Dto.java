package com.samil.stdadt.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.samil.stdadt.comm.vo.CamelMap;
import com.samil.stdadt.comm.vo.UserSessionVO;
import com.samil.stdadt.util.SHConverter;
import com.samil.stdadt.vo.ProjectInfoV4VO;

/**
 * 프로젝트 정보
 * @author shyunchoi
 *
 */
public class ProjectInfoV4Dto {

	//################################################################################
	// 데이터 메타정보
	//################################################################################
	private Boolean canEdit;		// 수정 가능 여부
	private String calcOnlyYn;		// 계산하기 전용 여부
	private Boolean canDelete;		// 삭제 가능 여부
	private Boolean canRestore;		// 복구 가능 여부
	private Boolean canDeletePermanently;		// 영구 삭제 가능 여부
	private String prflId;			// 프로파일 Id
	private double otherWkmnsp;		// Other 기준 숙련도
	private double newStfWkmnsp;	// New Staff 기준 숙련도
	private double maxRatio; 		// 그룹별 상한율
	private String stat;			// Status : RG: 등록(Regist), RQ: 승인 요청(Request), RJ: 반려(Reject), CO: 승인 완료(Complete), DE: 삭제(Delete)
	private CamelMap createdInfo;	// 최초 작성 정보
	private CamelMap modifyInfo;	// 최근 수정 정보
	private UserSessionVO session;
	private String clearBdgt;		// budget 정보 삭제 여부 판단 ('Y'면 삭제)
	private String reAprv;			// 'CO' -> 'RG' 변경여부 판단
	
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
	private CamelMap chargPtr;	// 프로젝트 담당 파트너
	private CamelMap chargMgr;	// 프로젝트 담당 매니저
	private CamelMap rcrdMgr1;	// 프로젝트 Record 매니저 1
	private CamelMap rcrdMgr2;	// 프로젝트 Record 매니저 2
	private CamelMap rcrdMgr3;	// 프로젝트 Record 매니저 3
	private long cntrtFee;			// 계약 보수 (원)
	private String prjtFrdt;		// 프로젝트 시작일 2019-01-01
	private String prjtTodt;		// 프로젝트 시작일 2019-12-31
	private String bizFrdt;			// 사업연도 시작월 2019-01
	private String bizTodt;			// 사업연도 종료월 2019-12
	private CamelMap desigAdtYn;	// 지정여부
	private CamelMap satTrgtYn;		// 표준감사 대상 여부
	private List<SubProjectV3Dto> subPrjt; // 합산 대상 프로젝트 정보
	private long totCntrtFee;		// 총 계약 보수 (원) - 합산대상프로젝트의 계약 보수까지의 총합계
	
	private List<Map<String, Object>> satgrpList;
	
	
	//################################################################################
	// 그룹 정보 영역
	//################################################################################
	private CamelMap indusDv;		// 산업 분류 코드
	private CamelMap listDv;		// 상장 구분
	private long indivAsset;		// 개별 자산
	private CamelMap bizRprtYn;		// 사업보고서 제출대상
	private long consoAsset;		// 연결 자산
	private CamelMap satgrp1ExptYn;	// 표준감사그룹1 예외사항 여부 : Y:무조건 그룹1로 선택
	private long consoSales;		// 연결 매출
	private long compSize;			// 기업 규모 : (연결 자산 + 연결 매출)/2
	private long consoAccntReceiv;	// 연결 매출 채권
	private String rprtScdlDt;		// 보고서 발행 예정일
	private long consoInvnt;		// 연결 재고자산
	private CamelMap satgrp;		// 표준감사그룹
	private String firstBizDt;		// 최초적용 사업연도
	private CamelMap contiAdtCnt;		// 삼일회계법인 연속감사 횟수
	private CamelMap intrAdtYcnt;		// 내부회계감사 시행연차
	private int revwCnt;			// 검토 횟수
	private Boolean sameAdtrSbsidYn;	// 동일감사인이 감사하는 종속회사 또는 관계회사 존재 여부
	private String sameAdtrSbsidYnNm;	// 동일감사인이 감사하는 종속회사 또는 관계회사 존재 여부
	private long intrTranAssetSales;		// 연결조정시 제거된 내부거래(매출+자산)
	private long sbsidSalesWithIntrTran;	// 내부거래전 자회사 자산
	private long relatCompAsset;			// 관계회사의 자산 금액
	private long sbsidAssetWithIntrTran;	// 내부거래 제거전 자회사 매출
	
	//2022-02-16 남웅주 
	private CamelMap etcFctrYn;				// 비상장금융회사로서 외감법 또는 자본시장법상 1,3분기 검토 의무가 있는지 여부
	
	//################################################################################
	// 가감 계수  영역
	//################################################################################
	private CamelMap ifrsYn;			// IFRS 여부
	private double ifrsFctr;			// IFRS 계수
	private CamelMap holdingsDv;		// 지주사 분류
	private double holdingsFctr;		// IFRS 계수
	//2022-03-08 남웅주
	//private CamelMap firstAdtYn;		// 초도 감사 여부
	private String firstAdtYn;			// 초도 감사 여부
	private double firstAdtFctr;		// 초도 감사 계수 
	private CamelMap usaListYn;			// 미국 상장 여부
	private double usaListFctr;			// 미국 상장 계수
	private double riskFctr;			// 위험 계정 비중
	private CamelMap kamYn;				// KAM 여부
	private double kamFctr;				// KAM 계수
	private CamelMap consoFinstatYn;	// 연결 작성 여부
	private double consoFinstatFctr;	// 연결 작성 계수
	private int sbsidCnt;				// 자회사(종속회사) 수
	private double sbsidCntFctr;		// 자회사 계수
	private CamelMap currAdtopin;		// 당기 예상 감사의견(비적정 여부)
	private double currAdtopinFctr;		// 당기 예상 감사의견 계수
	private CamelMap currConsoLossYn;	// 당기 연결 당기 순손실 예상 여부
	private double currConsoLossFctr;	// 당기 연결 당기 순손실 계수
	private CamelMap priorAdtopinChgYn;	// 전기 의견변형 여부
	private double priorAdtopinChgFctr;	// 전기 의견변형 계수
	private CamelMap priorLossYn;		// 전기 손실 여부
	private double priorLossFctr;		// 전기 손실 계수
	private double etcFctr;				// 분기검토조정계수 등 기타
	private double factorVal;			// 가감요인계, 가감률
	private CamelMap firstAdtFctrRange;			// 초도감사 계수 Min, Max 정보 {min:null, max:null, description:null}
	private CamelMap priorAdtopinChgFctrRange;	// 전기 의견변형 여부 Min, Max 정보 {min:null, max:null, description:null}
	private CamelMap priorLossFctrRange;		// 전기 손실 여부 Min, Max 정보 {min:null, max:null, description:null}
	
	//2022-02-16 남웅주
	//private CamelMap satTrgtYcnt;		// 표준감사시간대상 적용 연차
	private String satTrgtYcnt;			// 표준감사시간대상 적용 연차
	private double fstSatTrgtBftm;		// 최초 표준감사시간 대상 직전 감사시간
	
	//################################################################################
	// 상하한(기준숙련도 기준) 영역
	//################################################################################
	private String minMaxYn;				// 상하한 적용 여부
	private CamelMap minMaxReason;			// 상하한 미적용시 근거 - 직접 기술
	private String minMaxReasonDscrt;		// 상하한 미적용시 근거 - 직접 기술
	private String fstBizDt;				// 최초적용  상한 - 최초 적용 직전 사업연도  
	private String fstMaxTm;				// 최초적용  상한 - 3년 상한
	private double fstAdtTm;				// 최초 적용 상한 - 최초 적용 직전 감사시간
	private Boolean fstAdtrBaseWkmnspYn;	// 최초 적용 상한 - 감사팀의 숙련도 - 기준숙련도 적용 여부
	private String fstAdtrBaseWkmnspYnNm;	// 최초 적용 상한 - 감사팀의 숙련도 - 기준숙련도 적용 여부 
	private double fstAdtrWkmnsp;			// 최초 적용 상한 - 감사팀의 숙련도
	private CamelMap fstAdtTmReason;		// 최초 적용 상한 - 감사시간 수정시 근거 (일치/초도감사/직접서술)
	private String fstAdtTmReasonDscrt;		// 최초 적용 상한 - 감사시간 수정시 근거 직접 서술
	private CamelMap fstWkmnspReason;		// 최초 적용 상한 - 숙련도 근거 (기준숙련도 적용(Big 4) / 실제숙련도 / 외감실시내용을 기준으로 간단계산 / 감사계약시 합의내용 / 직접서술)
	private String fstWkmnspReasonDscrt;	// 최초 적용 상한 - 숙련도 근거 직접 서술
	private CamelMap priorAdtr;				// 전기기준 상하한 - 전기감사인
	private double priorBaseWkmnsp;			// 전기기준 상하한 - 전기 기준 숙련도
	private double priorAdtTm;				// 전기기준 상하한 - 전기 감사 시간
	private Boolean priorAdtrBaseWkmnspYn;  // 전기기준 상하한 - 감사팀의 숙련도 - 기준숙련도 적용 여부
	private String priorAdtrBaseWkmnspYnNm;  // 전기기준 상하한 - 감사팀의 숙련도 - 기준숙련도 적용 여부
	private double priorAdtrWkmnsp;			// 전기기준 상하한 - 전기 감사팀 숙련도
	private CamelMap priorAdtTmReason;		// 전기기준 상하한 - 감사시간 근거 (일치/초도감사/직접서술)
	private String priorAdtTmReasonDscrt;	// 전기기준 상하한 - 감사시간 수정시 근거 직접 서술
	private CamelMap priorWkmnspReason;		// 전기기준 상하한 - 숙련도 근거 (기준숙련도 적용(Big 4) / 실제숙련도 / 외감실시내용을 기준으로 간단계산 / 감사계약시 합의내용 / 직접서술)
	private String priorWkmnspReasonDscrt;	// 전기기준 상하한 - 숙련도 근거 직접 서술
	private CamelMap intrAdtYn;		// 내부감사시간 - 전기 내부회계감사시간 포함 여부
	private double intrAdtTm;			// 내부감사시간 - 전기 내부회계감사시간
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
	
	
	public ProjectInfoV4Dto() {}
	public void init(ProjectInfoV4VO prjtInfo) throws Exception{
		//################################################################################
		// 데이터 메타정보
		//################################################################################
		this.canEdit 		= prjtInfo.getCanEdit();
		this.canDelete 		= prjtInfo.getCanDelete();
		this.canRestore 	= prjtInfo.getCanRestore();
		this.canDeletePermanently 		= prjtInfo.getCanDeletePermanently();
		this.prflId 		= prjtInfo.getPrflId();
		this.otherWkmnsp 	= prjtInfo.getOtherWkmnsp();
		this.newStfWkmnsp 	= prjtInfo.getNewStfWkmnsp();
		this.maxRatio	 	= prjtInfo.getMaxRatio();
		this.stat 			= prjtInfo.getStat();
		this.createdInfo	= SHConverter.camelMapFactory("dt", prjtInfo.getCredt(),"emplno", prjtInfo.getCreby(),"kornm", prjtInfo.getCrebyNm());
		this.modifyInfo		= SHConverter.camelMapFactory("dt", prjtInfo.getModdt(),"emplno", prjtInfo.getModby(),"kornm", prjtInfo.getModbyNm());
		this.calcOnlyYn		= prjtInfo.getCalcOnlyYn();
		
		//################################################################################
		// 프로젝트 기본 정보
		//################################################################################
		this.compNm			= prjtInfo.getCompNm();
		this.prjtCd			= prjtInfo.getPrjtCd();
		this.prjtCd1		= prjtInfo.getPrjtCd1();
		this.prjtCd2		= prjtInfo.getPrjtCd2();
		this.prjtCd3		= prjtInfo.getPrjtCd3();
		this.prjtNm			= prjtInfo.getPrjtNm();
		this.clntNm			= prjtInfo.getClntNm();
		this.shrtNm			= prjtInfo.getShrtNm();
		this.chargPtr		= SHConverter.camelMapFactory("emplno", prjtInfo.getChargPtr(), "kornm", prjtInfo.getChargPtrNm(), "gradnm", prjtInfo.getChargPtrGradNm());
		this.chargMgr		= SHConverter.camelMapFactory("emplno", prjtInfo.getChargMgr(), "kornm", prjtInfo.getChargMgrNm(), "gradnm", prjtInfo.getChargMgrGradNm());
		this.rcrdMgr1		= SHConverter.camelMapFactory("emplno", prjtInfo.getRcrdMgr(), "kornm", prjtInfo.getRcrdMgrNm(), "gradnm", prjtInfo.getRcrdMgrGradNm());
		this.rcrdMgr2		= SHConverter.camelMapFactory("emplno", prjtInfo.getRcrdMgr2(), "kornm", prjtInfo.getRcrdMgrNm2(), "gradnm", prjtInfo.getRcrdMgrGradNm2());
		this.rcrdMgr3		= SHConverter.camelMapFactory("emplno", prjtInfo.getRcrdMgr3(), "kornm", prjtInfo.getRcrdMgrNm3(), "gradnm", prjtInfo.getRcrdMgrGradNm3());
		this.cntrtFee		= prjtInfo.getCntrtFee();
		this.prjtFrdt		= prjtInfo.getPrjtFrdt();
		this.prjtTodt		= prjtInfo.getPrjtTodt();
		this.bizFrdt		= prjtInfo.getBizFrdt();
		this.bizTodt		= prjtInfo.getBizTodt();
		this.desigAdtYn		= SHConverter.camelMapFactory("cd", prjtInfo.getDesigAdtYn(), "nm", prjtInfo.getDesigAdtYnNm());
		this.satTrgtYn		= SHConverter.camelMapFactory("cd", prjtInfo.getSatTrgtYn(), "nm", prjtInfo.getSatTrgtYnNm());
		this.totCntrtFee	= prjtInfo.getTotCntrtFee();
		
		//################################################################################
		// 그룹 정보 영역
		//################################################################################
		this.indusDv				= SHConverter.camelMapFactory("cd", prjtInfo.getIndusCd(), "nm", prjtInfo.getIndusNm());
		this.listDv					= SHConverter.camelMapFactory("cd", prjtInfo.getListDvCd(), "nm", prjtInfo.getListDvNm());
		this.indivAsset				= prjtInfo.getIndivAsset();
		this.bizRprtYn				= SHConverter.camelMapFactory("cd", prjtInfo.getBizRprtYn(), "nm", prjtInfo.getBizRprtYnNm());
		this.consoAsset				= prjtInfo.getConsoAsset();
		this.satgrp1ExptYn			= SHConverter.camelMapFactory("cd", prjtInfo.getSatgrp1ExptYn(), "nm", prjtInfo.getSatgrp1ExptYnNm());
		this.consoSales				= prjtInfo.getConsoSales();
		this.compSize				= prjtInfo.getCompSize();
		this.consoAccntReceiv		= prjtInfo.getConsoAccntReceiv();
		this.rprtScdlDt				= prjtInfo.getRprtScdlDt();
		this.consoInvnt				= prjtInfo.getConsoInvnt();
		this.satgrp					= SHConverter.camelMapFactory("cd", prjtInfo.getSatgrpCd(), "nm", prjtInfo.getSatgrpNm());
		this.firstBizDt				= prjtInfo.getFirstBizDt();
		this.contiAdtCnt			= SHConverter.camelMapFactory("cd", prjtInfo.getContiAdtCnt(), "nm", prjtInfo.getContiAdtCntNm());
		this.intrAdtYcnt			= SHConverter.camelMapFactory("cd", prjtInfo.getIntrAdtYcnt(), "nm", prjtInfo.getIntrAdtYcntNm());
		this.revwCnt				= prjtInfo.getRevwCnt();
		this.sameAdtrSbsidYn		= "Y".equals(prjtInfo.getSameAdtrSbsidYn())?true:false;
		this.intrTranAssetSales		= prjtInfo.getIntrTranAssetSales();
		this.sbsidSalesWithIntrTran	= prjtInfo.getSbsidSalesWithIntrTran();
		this.relatCompAsset			= prjtInfo.getRelatCompAsset();
		this.sbsidAssetWithIntrTran	= prjtInfo.getSbsidAssetWithIntrTran();
		
		//2022-02-16 남웅주
		this.etcFctrYn				= SHConverter.camelMapFactory("cd", prjtInfo.getEtcFctrYn(), "nm", prjtInfo.getEtcFctrYnNm());		
		
		//################################################################################
		// 가감 계수  영역
		//################################################################################
		this.ifrsYn = SHConverter.camelMapFactory("cd", prjtInfo.getIfrsYn(), "nm", prjtInfo.getIfrsYnNm());
		this.ifrsFctr 			= prjtInfo.getIfrsFctr();
		this.holdingsDv 			= SHConverter.camelMapFactory("cd", prjtInfo.getHoldingsDivCd(), "nm", prjtInfo.getHoldingsDivNm());
		this.holdingsFctr 			= prjtInfo.getHoldingsFctr();
		//2022-03-08 남웅주
		//this.firstAdtYn 			= SHConverter.camelMapFactory("cd", prjtInfo.getFirstAdtYn(), "nm", prjtInfo.getFirstAdtYnNm());
		this.firstAdtYn 			= prjtInfo.getFirstAdtYn();
		this.firstAdtFctr 			= prjtInfo.getFirstAdtFctr(); 
		this.usaListYn 				= SHConverter.camelMapFactory("cd", prjtInfo.getUsaListYn(), "nm", prjtInfo.getUsaListYnNm());
		this.usaListFctr 			= prjtInfo.getUsaListFctr();
		this.riskFctr 				= prjtInfo.getRiskFctr();
		this.kamYn 					= SHConverter.camelMapFactory("cd", prjtInfo.getKamYn(), "nm", prjtInfo.getKamYnNm());
		this.kamFctr 				= prjtInfo.getKamFctr();
		this.consoFinstatYn 		= SHConverter.camelMapFactory("cd", prjtInfo.getConsoFinstatYn(), "nm", prjtInfo.getConsoFinstatYnNm());
		this.consoFinstatFctr 		= prjtInfo.getConsoFinstatFctr();
		this.sbsidCnt 				= prjtInfo.getSbsidCnt();
		this.sbsidCntFctr 			= prjtInfo.getSbsidCntFctr();
		this.currAdtopin 			= SHConverter.camelMapFactory("cd", prjtInfo.getCurrAdtopinCd(), "nm", prjtInfo.getCurrAdtopinNm());
		this.currAdtopinFctr 		= prjtInfo.getCurrAdtopinFctr();
		this.currConsoLossYn 		= SHConverter.camelMapFactory("cd", prjtInfo.getCurrConsoLossYn(), "nm", prjtInfo.getCurrConsoLossYnNm());
		this.currConsoLossFctr 		= prjtInfo.getCurrConsoLossFctr();
		this.priorAdtopinChgYn 		= SHConverter.camelMapFactory("cd", prjtInfo.getPriorAdtopinChgYn(), "nm", prjtInfo.getPriorAdtopinChgYnNm());
		this.priorAdtopinChgFctr	= prjtInfo.getPriorAdtopinChgFctr();
		this.priorLossYn 			= SHConverter.camelMapFactory("cd", prjtInfo.getPriorLossYn(), "nm", prjtInfo.getPriorLossYnNm());
		this.priorLossFctr 			= prjtInfo.getPriorLossFctr();
		this.etcFctr 				= prjtInfo.getEtcFctr();
		this.factorVal 				= prjtInfo.getFactorVal();
		
		//2022-02-16 남웅주
		//this.satTrgtYcnt 			= SHConverter.camelMapFactory("cd", prjtInfo.getSatTrgtYcnt(), "nm", prjtInfo.getSatTrgtYcntNm());
		this.satTrgtYcnt 			= prjtInfo.getSatTrgtYcnt();
		this.fstSatTrgtBftm 		= prjtInfo.getFstSatTrgtBftm();

		//################################################################################
		// 상하한(기준숙련도 기준) 영역
		//################################################################################
		//2022-03-08 남웅주		
		//this.minMaxYn				= SHConverter.camelMapFactory("cd", prjtInfo.getMinMaxYn(), "nm", prjtInfo.getMinMaxYnNm());
		this.minMaxYn				= prjtInfo.getMinMaxYnNm();
		this.minMaxReason			= SHConverter.camelMapFactory("cd", prjtInfo.getMinMaxReasonCd(), "nm", prjtInfo.getMinMaxReasonNm());
		this.minMaxReasonDscrt		= prjtInfo.getMinMaxReasonDscrt();
		this.fstBizDt				= prjtInfo.getFstBizDt();  
		this.fstMaxTm				= prjtInfo.getFstMaxTm();
		this.fstAdtTm				= prjtInfo.getFstAdtTm();
		this.fstAdtrBaseWkmnspYn	= "Y".equals(prjtInfo.getFstAdtrBaseWkmnspYn())?true:false;
		this.fstAdtrWkmnsp			= prjtInfo.getFstAdtrWkmnsp();
		this.fstAdtTmReason			= SHConverter.camelMapFactory("cd", prjtInfo.getFstAdtTmReasonCd(), "nm", prjtInfo.getFstAdtTmReasonNm());
		this.fstAdtTmReasonDscrt	= prjtInfo.getFstAdtTmReasonDscrt();
		this.fstWkmnspReason		= SHConverter.camelMapFactory("cd", prjtInfo.getFstWkmnspReasonCd(), "nm", prjtInfo.getFstWkmnspReasonNm());
		this.fstWkmnspReasonDscrt	= prjtInfo.getFstWkmnspReasonDscrt();
		this.priorAdtr				= SHConverter.camelMapFactory("cd", prjtInfo.getPriorAdtrCd(), "nm", prjtInfo.getPriorAdtrNm());
		this.priorBaseWkmnsp		= prjtInfo.getPriorBaseWkmnsp();
		this.priorAdtTm				= prjtInfo.getPriorAdtTm();	
		this.priorAdtrBaseWkmnspYn	= "Y".equals(prjtInfo.getPriorAdtrBaseWkmnspYn())?true:false;
		this.priorAdtrWkmnsp		= prjtInfo.getPriorAdtrWkmnsp();
		this.priorAdtTmReason		= SHConverter.camelMapFactory("cd", prjtInfo.getPriorAdtTmReasonCd(), "nm", prjtInfo.getPriorAdtTmReasonNm());
		this.priorAdtTmReasonDscrt	= prjtInfo.getPriorAdtTmReasonDscrt();
		this.priorWkmnspReason		= SHConverter.camelMapFactory("cd", prjtInfo.getPriorWkmnspReasonCd(), "nm", prjtInfo.getPriorWkmnspReasonNm());
		this.priorWkmnspReasonDscrt	= prjtInfo.getPriorWkmnspReasonDscrt();
		this.intrAdtYn				= SHConverter.camelMapFactory("cd", prjtInfo.getIntrAdtYn(), "nm", prjtInfo.getIntrAdtYnNm());
		this.intrAdtTm				= prjtInfo.getIntrAdtTm();
		this.minTm					= prjtInfo.getMinTm();
		this.maxTm					= prjtInfo.getMaxTm();
		
		
		//################################################################################
		// 표준감사시간 산출 영역
		//################################################################################
		this.intplSat		= prjtInfo.getIntplSat();	
		this.calResult		= prjtInfo.getCalResult();	
		this.yearRate		= prjtInfo.getYearRate();	
		this.calAdtTm		= prjtInfo.getCalAdtTm();	
		this.intrAdtFctr	= prjtInfo.getIntrAdtFctr();
		this.calSat			= prjtInfo.getCalSat();	
		this.baseWkmnsp		= prjtInfo.getBaseWkmnsp();
		this.etDfnSat		= prjtInfo.getEtDfnSat();
		this.etTrgtAdtTm	= prjtInfo.getEtTrgtAdtTm();
		this.wkmnspSat		= prjtInfo.getWkmnspSat();
		
		
		//################################################################################
		// 표준감사시간외 예산입력 & 예산 배분
		//################################################################################
		this.etcBdgtTm		= prjtInfo.getEtcBdgtTm();
		this.totPrjtBdgt	= prjtInfo.getTotPrjtBdgt();
		this.raBdgtTm		= prjtInfo.getRaBdgtTm();
		this.flcmBdgtTm		= prjtInfo.getFlcmBdgtTm();
		this.otherBdgtTm	= prjtInfo.getOtherBdgtTm();

		
		//################################################################################
		// 등록 승인/반려 & 리테인 전송 관련
		//################################################################################
		this.rjctCmnt		= prjtInfo.getRjctCmnt();		
		this.aprvReqEmplno	= prjtInfo.getAprvReqEmplno();	
		this.aprvReqDt		= prjtInfo.getAprvReqDt();	 
		this.aprvCmpltDt	= prjtInfo.getAprvCmpltDt(); 
		this.cmpltCnt		= prjtInfo.getCmpltCnt();	
		this.retainTranYn	= prjtInfo.getRetainTranYn();
		
		this.profSatYn		= prjtInfo.getProfSatYn();
		this.profSatReason  = prjtInfo.getProfSatReason();
		this.profBdgtYn		= prjtInfo.getProfBdgtYn();
		this.profBdgtReason = prjtInfo.getProfBdgtReason();
				
		this.remark 		= prjtInfo.getRemark();
		
	}
	
	public void addPrjtAdd(SubProjectV3Dto subPrjt) {
		if(this.subPrjt == null) {
			this.subPrjt = new ArrayList<SubProjectV3Dto>();
		}
		this.subPrjt.add(subPrjt);
	}
	public Boolean getCanEdit() {
		return canEdit;
	}
	public String getCalcOnlyYn() {
		return calcOnlyYn;
	}
	public Boolean getCanDelete() {
		return canDelete;
	}
	public String getPrflId() {
		return prflId;
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
	public CamelMap getCreatedInfo() {
		return createdInfo;
	}
	public CamelMap getModifyInfo() {
		return modifyInfo;
	}
	public String getCompNm() {
		return compNm;
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
	public CamelMap getChargPtr() {
		return chargPtr;
	}
	public CamelMap getChargMgr() {
		return chargMgr;
	}
	public CamelMap getRcrdMgr1() {
		return rcrdMgr1;
	}
	public CamelMap getRcrdMgr2() {
		return rcrdMgr2;
	}
	public CamelMap getRcrdMgr3() {
		return rcrdMgr3;
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
	public CamelMap getDesigAdtYn() {
		return desigAdtYn;
	}
	public CamelMap getSatTrgtYn() {
		return satTrgtYn;
	}
	public List<SubProjectV3Dto> getSubPrjt() {
		return subPrjt;
	}
	public long getTotCntrtFee() {
		return totCntrtFee;
	}
	public CamelMap getIndusDv() {
		return indusDv;
	}
	public CamelMap getListDv() {
		return listDv;
	}
	public long getIndivAsset() {
		return indivAsset;
	}
	public CamelMap getBizRprtYn() {
		return bizRprtYn;
	}
	public long getConsoAsset() {
		return consoAsset;
	}
	public CamelMap getSatgrp1ExptYn() {
		return satgrp1ExptYn;
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
	public CamelMap getSatgrp() {
		return satgrp;
	}
	public String getFirstBizDt() {
		return firstBizDt;
	}
	public int getRevwCnt() {
		return revwCnt;
	}
	public Boolean getSameAdtrSbsidYn() {
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
	public CamelMap getIfrsYn() {
		return ifrsYn;
	}
	public double getIfrsFctr() {
		return ifrsFctr;
	}
	public CamelMap getHoldingsDv() {
		return holdingsDv;
	}
	public double getHoldingsFctr() {
		return holdingsFctr;
	}
	public String getFirstAdtYn() {
		return firstAdtYn;
	}
	public double getFirstAdtFctr() {
		return firstAdtFctr;
	}
	public CamelMap getUsaListYn() {
		return usaListYn;
	}
	public double getUsaListFctr() {
		return usaListFctr;
	}
	public double getRiskFctr() {
		return riskFctr;
	}
	public CamelMap getKamYn() {
		return kamYn;
	}
	public double getKamFctr() {
		return kamFctr;
	}
	public CamelMap getConsoFinstatYn() {
		return consoFinstatYn;
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
	public CamelMap getCurrAdtopin() {
		return currAdtopin;
	}
	public double getCurrAdtopinFctr() {
		return currAdtopinFctr;
	}
	public CamelMap getCurrConsoLossYn() {
		return currConsoLossYn;
	}
	public double getCurrConsoLossFctr() {
		return currConsoLossFctr;
	}
	public CamelMap getPriorAdtopinChgYn() {
		return priorAdtopinChgYn;
	}
	public double getPriorAdtopinChgFctr() {
		return priorAdtopinChgFctr;
	}
	public CamelMap getPriorLossYn() {
		return priorLossYn;
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
	public CamelMap getMinMaxReason() {
		return minMaxReason;
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
	public CamelMap getFstAdtTmReason() {
		return fstAdtTmReason;
	}
	public String getFstAdtTmReasonDscrt() {
		return fstAdtTmReasonDscrt;
	}
	public CamelMap getFstWkmnspReason() {
		return fstWkmnspReason;
	}
	public String getFstWkmnspReasonDscrt() {
		return fstWkmnspReasonDscrt;
	}
	public CamelMap getPriorAdtr() {
		return priorAdtr;
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
	public CamelMap getPriorAdtTmReason() {
		return priorAdtTmReason;
	}
	public String getPriorAdtTmReasonDscrt() {
		return priorAdtTmReasonDscrt;
	}
	public CamelMap getPriorWkmnspReason() {
		return priorWkmnspReason;
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
	public void setCanEdit(Boolean canEdit) {
		this.canEdit = canEdit;
	}
	public void setCalcOnlyYn(String calcOnlyYn) {
		this.calcOnlyYn = calcOnlyYn;
	}
	public void setCanDelete(Boolean canDelete) {
		this.canDelete = canDelete;
	}
	public void setPrflId(String prflId) {
		this.prflId = prflId;
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
	public void setCreatedInfo(CamelMap createdInfo) {
		this.createdInfo = createdInfo;
	}
	public void setModifyInfo(CamelMap modifyInfo) {
		this.modifyInfo = modifyInfo;
	}
	public void setCompNm(String compNm) {
		this.compNm = compNm;
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
	public void setChargPtr(CamelMap chargPtr) {
		this.chargPtr = chargPtr;
	}
	public void setChargMgr(CamelMap chargMgr) {
		this.chargMgr = chargMgr;
	}
	public void setRcrdMgr1(CamelMap rcrdMgr1) {
		this.rcrdMgr1 = rcrdMgr1;
	}
	public void setRcrdMgr2(CamelMap rcrdMgr2) {
		this.rcrdMgr2 = rcrdMgr2;
	}
	public void setRcrdMgr3(CamelMap rcrdMgr3) {
		this.rcrdMgr3 = rcrdMgr3;
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
	public void setDesigAdtYn(CamelMap desigAdtYn) {
		this.desigAdtYn = desigAdtYn;
	}
	public void setSatTrgtYn(CamelMap satTrgtYn) {
		this.satTrgtYn = satTrgtYn;
	}
	public void setSubPrjt(List<SubProjectV3Dto> subPrjt) {
		this.subPrjt = subPrjt;
	}
	public void setTotCntrtFee(long totCntrtFee) {
		this.totCntrtFee = totCntrtFee;
	}
	public void setIndusDv(CamelMap indusDv) {
		this.indusDv = indusDv;
	}
	public void setListDv(CamelMap listDv) {
		this.listDv = listDv;
	}
	public void setIndivAsset(long indivAsset) {
		this.indivAsset = indivAsset;
	}
	public void setBizRprtYn(CamelMap bizRprtYn) {
		this.bizRprtYn = bizRprtYn;
	}
	public void setConsoAsset(long consoAsset) {
		this.consoAsset = consoAsset;
	}
	public void setSatgrp1ExptYn(CamelMap satgrp1ExptYn) {
		this.satgrp1ExptYn = satgrp1ExptYn;
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
	public void setSatgrp(CamelMap satgrp) {
		this.satgrp = satgrp;
	}
	public void setFirstBizDt(String firstBizDt) {
		this.firstBizDt = firstBizDt;
	}
	public void setRevwCnt(int revwCnt) {
		this.revwCnt = revwCnt;
	}
	public void setSameAdtrSbsidYn(Boolean sameAdtrSbsidYn) {
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
	public void setIfrsYn(CamelMap ifrsYn) {
		this.ifrsYn = ifrsYn;
	}
	public void setIfrsFctr(double ifrsFctr) {
		this.ifrsFctr = ifrsFctr;
	}
	public void setHoldingsDv(CamelMap holdingsDv) {
		this.holdingsDv = holdingsDv;
	}
	public void setHoldingsFctr(double holdingsFctr) {
		this.holdingsFctr = holdingsFctr;
	}
	public void setFirstAdtYn(String firstAdtYn) {
		this.firstAdtYn = firstAdtYn;
	}
	public void setFirstAdtFctr(double firstAdtFctr) {
		this.firstAdtFctr = firstAdtFctr;
	}
	public void setUsaListYn(CamelMap usaListYn) {
		this.usaListYn = usaListYn;
	}
	public void setUsaListFctr(double usaListFctr) {
		this.usaListFctr = usaListFctr;
	}
	public void setRiskFctr(double riskFctr) {
		this.riskFctr = riskFctr;
	}
	public void setKamYn(CamelMap kamYn) {
		this.kamYn = kamYn;
	}
	public void setKamFctr(double kamFctr) {
		this.kamFctr = kamFctr;
	}
	public void setConsoFinstatYn(CamelMap consoFinstatYn) {
		this.consoFinstatYn = consoFinstatYn;
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
	public void setCurrAdtopin(CamelMap currAdtopin) {
		this.currAdtopin = currAdtopin;
	}
	public void setCurrAdtopinFctr(double currAdtopinFctr) {
		this.currAdtopinFctr = currAdtopinFctr;
	}
	public void setCurrConsoLossYn(CamelMap currConsoLossYn) {
		this.currConsoLossYn = currConsoLossYn;
	}
	public void setCurrConsoLossFctr(double currConsoLossFctr) {
		this.currConsoLossFctr = currConsoLossFctr;
	}
	public void setPriorAdtopinChgYn(CamelMap priorAdtopinChgYn) {
		this.priorAdtopinChgYn = priorAdtopinChgYn;
	}
	public void setPriorAdtopinChgFctr(double priorAdtopinChgFctr) {
		this.priorAdtopinChgFctr = priorAdtopinChgFctr;
	}
	public void setPriorLossYn(CamelMap priorLossYn) {
		this.priorLossYn = priorLossYn;
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
	public void setMinMaxReason(CamelMap minMaxReason) {
		this.minMaxReason = minMaxReason;
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
	public void setFstAdtTmReason(CamelMap fstAdtTmReason) {
		this.fstAdtTmReason = fstAdtTmReason;
	}
	public void setFstAdtTmReasonDscrt(String fstAdtTmReasonDscrt) {
		this.fstAdtTmReasonDscrt = fstAdtTmReasonDscrt;
	}
	public void setFstWkmnspReason(CamelMap fstWkmnspReason) {
		this.fstWkmnspReason = fstWkmnspReason;
	}
	public void setFstWkmnspReasonDscrt(String fstWkmnspReasonDscrt) {
		this.fstWkmnspReasonDscrt = fstWkmnspReasonDscrt;
	}
	public void setPriorAdtr(CamelMap priorAdtr) {
		this.priorAdtr = priorAdtr;
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
	public void setPriorAdtTmReason(CamelMap priorAdtTmReason) {
		this.priorAdtTmReason = priorAdtTmReason;
	}
	public void setPriorAdtTmReasonDscrt(String priorAdtTmReasonDscrt) {
		this.priorAdtTmReasonDscrt = priorAdtTmReasonDscrt;
	}
	public void setPriorWkmnspReason(CamelMap priorWkmnspReason) {
		this.priorWkmnspReason = priorWkmnspReason;
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
	public CamelMap getIntrAdtYn() {
		return intrAdtYn;
	}
	public double getIntrAdtTm() {
		return intrAdtTm;
	}
	public void setIntrAdtYn(CamelMap intrAdtYn) {
		this.intrAdtYn = intrAdtYn;
	}
	public void setIntrAdtTm(double intrAdtTm) {
		this.intrAdtTm = intrAdtTm;
	}
	public List<Map<String, Object>> getSatgrpList() {
		return satgrpList;
	}
	public void setSatgrpList(List<Map<String, Object>> satgrpList) {
		this.satgrpList = satgrpList;
	}
	public CamelMap getContiAdtCnt() {
		return contiAdtCnt;
	}
	public CamelMap getIntrAdtYcnt() {
		return intrAdtYcnt;
	}
	public void setContiAdtCnt(CamelMap contiAdtCnt) {
		this.contiAdtCnt = contiAdtCnt;
	}
	public void setIntrAdtYcnt(CamelMap intrAdtYcnt) {
		this.intrAdtYcnt = intrAdtYcnt;
	}
	public Boolean getFstAdtrBaseWkmnspYn() {
		return fstAdtrBaseWkmnspYn;
	}
	public Boolean getPriorAdtrBaseWkmnspYn() {
		return priorAdtrBaseWkmnspYn;
	}
	public void setFstAdtrBaseWkmnspYn(Boolean fstAdtrBaseWkmnspYn) {
		this.fstAdtrBaseWkmnspYn = fstAdtrBaseWkmnspYn;
	}
	public void setPriorAdtrBaseWkmnspYn(Boolean priorAdtrBaseWkmnspYn) {
		this.priorAdtrBaseWkmnspYn = priorAdtrBaseWkmnspYn;
	}
	public String getSameAdtrSbsidYnNm() {
		return this.sameAdtrSbsidYn!=null && this.sameAdtrSbsidYn?"Y":"N";
	}
	public String getFstAdtrBaseWkmnspYnNm() {
		return this.fstAdtrBaseWkmnspYn!=null && this.fstAdtrBaseWkmnspYn?"Y":"N";
	}
	public String getPriorAdtrBaseWkmnspYnNm() {
		return this.priorAdtrBaseWkmnspYn!=null && this.priorAdtrBaseWkmnspYn?"Y":"N";
	}
	public UserSessionVO getSession() {
		return session;
	}
	public void setSession(UserSessionVO session) {
		this.session = session;
	}
	public CamelMap getFirstAdtFctrRange() {
		return firstAdtFctrRange;
	}
	public CamelMap getPriorAdtopinChgFctrRange() {
		return priorAdtopinChgFctrRange;
	}
	public CamelMap getPriorLossFctrRange() {
		return priorLossFctrRange;
	}
	public void setFirstAdtFctrRange(CamelMap firstAdtFctrRange) {
		this.firstAdtFctrRange = firstAdtFctrRange;
	}
	public void setPriorAdtopinChgFctrRange(CamelMap priorAdtopinChgFctrRange) {
		this.priorAdtopinChgFctrRange = priorAdtopinChgFctrRange;
	}
	public void setPriorLossFctrRange(CamelMap priorLossFctrRange) {
		this.priorLossFctrRange = priorLossFctrRange;
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
	public String getClearBdgt() {
		return clearBdgt;
	}
	public String getReAprv() {
		return reAprv;
	}
	public void setClearBdgt(String clearBdgt) {
		this.clearBdgt = clearBdgt;
	}
	public void setReAprv(String reAprv) {
		this.reAprv = reAprv;
	}
	@Override
	public String toString() {
		return "ProjectInfoV4Dto [canEdit=" + canEdit + ", calcOnlyYn=" + calcOnlyYn + ", canDelete=" + canDelete
				+ ", canRestore=" + canRestore + ", canDeletePermanently=" + canDeletePermanently + ", prflId=" + prflId
				+ ", otherWkmnsp=" + otherWkmnsp + ", newStfWkmnsp=" + newStfWkmnsp + ", maxRatio=" + maxRatio
				+ ", stat=" + stat + ", createdInfo=" + createdInfo + ", modifyInfo=" + modifyInfo + ", session="
				+ session + ", clearBdgt=" + clearBdgt + ", reAprv=" + reAprv + ", compNm=" + compNm + ", prjtCd="
				+ prjtCd + ", prjtCd1=" + prjtCd1 + ", prjtCd2=" + prjtCd2 + ", prjtCd3=" + prjtCd3 + ", prjtNm="
				+ prjtNm + ", clntNm=" + clntNm + ", shrtNm=" + shrtNm + ", chargPtr=" + chargPtr + ", chargMgr="
				+ chargMgr + ", rcrdMgr1=" + rcrdMgr1 + ", rcrdMgr2=" + rcrdMgr2 + ", rcrdMgr3=" + rcrdMgr3
				+ ", cntrtFee=" + cntrtFee + ", prjtFrdt=" + prjtFrdt + ", prjtTodt=" + prjtTodt + ", bizFrdt="
				+ bizFrdt + ", bizTodt=" + bizTodt + ", desigAdtYn=" + desigAdtYn + ", satTrgtYn=" + satTrgtYn
				+ ", subPrjt=" + subPrjt + ", totCntrtFee=" + totCntrtFee + ", satgrpList=" + satgrpList + ", indusDv="
				+ indusDv + ", listDv=" + listDv + ", indivAsset=" + indivAsset + ", bizRprtYn=" + bizRprtYn
				+ ", consoAsset=" + consoAsset + ", satgrp1ExptYn=" + satgrp1ExptYn + ", consoSales=" + consoSales
				+ ", compSize=" + compSize + ", consoAccntReceiv=" + consoAccntReceiv + ", rprtScdlDt=" + rprtScdlDt
				+ ", consoInvnt=" + consoInvnt + ", satgrp=" + satgrp + ", firstBizDt=" + firstBizDt + ", contiAdtCnt="
				+ contiAdtCnt + ", intrAdtYcnt=" + intrAdtYcnt + ", revwCnt=" + revwCnt + ", sameAdtrSbsidYn="
				+ sameAdtrSbsidYn + ", sameAdtrSbsidYnNm=" + sameAdtrSbsidYnNm + ", intrTranAssetSales="
				+ intrTranAssetSales + ", sbsidSalesWithIntrTran=" + sbsidSalesWithIntrTran + ", relatCompAsset="
				+ relatCompAsset + ", sbsidAssetWithIntrTran=" + sbsidAssetWithIntrTran + ", etcFctrYn=" + etcFctrYn
				+ ", ifrsYn=" + ifrsYn + ", ifrsFctr=" + ifrsFctr + ", holdingsDv=" + holdingsDv + ", holdingsFctr="
				+ holdingsFctr + ", firstAdtYn=" + firstAdtYn + ", firstAdtFctr=" + firstAdtFctr + ", usaListYn="
				+ usaListYn + ", usaListFctr=" + usaListFctr + ", riskFctr=" + riskFctr + ", kamYn=" + kamYn
				+ ", kamFctr=" + kamFctr + ", consoFinstatYn=" + consoFinstatYn + ", consoFinstatFctr="
				+ consoFinstatFctr + ", sbsidCnt=" + sbsidCnt + ", sbsidCntFctr=" + sbsidCntFctr + ", currAdtopin="
				+ currAdtopin + ", currAdtopinFctr=" + currAdtopinFctr + ", currConsoLossYn=" + currConsoLossYn
				+ ", currConsoLossFctr=" + currConsoLossFctr + ", priorAdtopinChgYn=" + priorAdtopinChgYn
				+ ", priorAdtopinChgFctr=" + priorAdtopinChgFctr + ", priorLossYn=" + priorLossYn + ", priorLossFctr="
				+ priorLossFctr + ", etcFctr=" + etcFctr + ", factorVal=" + factorVal + ", firstAdtFctrRange="
				+ firstAdtFctrRange + ", priorAdtopinChgFctrRange=" + priorAdtopinChgFctrRange + ", priorLossFctrRange="
				+ priorLossFctrRange + ", satTrgtYcnt=" + satTrgtYcnt + ", fstSatTrgtBftm=" + fstSatTrgtBftm
				+ ", minMaxYn=" + minMaxYn + ", minMaxReason=" + minMaxReason + ", minMaxReasonDscrt="
				+ minMaxReasonDscrt + ", fstBizDt=" + fstBizDt + ", fstMaxTm=" + fstMaxTm + ", fstAdtTm=" + fstAdtTm
				+ ", fstAdtrBaseWkmnspYn=" + fstAdtrBaseWkmnspYn + ", fstAdtrBaseWkmnspYnNm=" + fstAdtrBaseWkmnspYnNm
				+ ", fstAdtrWkmnsp=" + fstAdtrWkmnsp + ", fstAdtTmReason=" + fstAdtTmReason + ", fstAdtTmReasonDscrt="
				+ fstAdtTmReasonDscrt + ", fstWkmnspReason=" + fstWkmnspReason + ", fstWkmnspReasonDscrt="
				+ fstWkmnspReasonDscrt + ", priorAdtr=" + priorAdtr + ", priorBaseWkmnsp=" + priorBaseWkmnsp
				+ ", priorAdtTm=" + priorAdtTm + ", priorAdtrBaseWkmnspYn=" + priorAdtrBaseWkmnspYn
				+ ", priorAdtrBaseWkmnspYnNm=" + priorAdtrBaseWkmnspYnNm + ", priorAdtrWkmnsp=" + priorAdtrWkmnsp
				+ ", priorAdtTmReason=" + priorAdtTmReason + ", priorAdtTmReasonDscrt=" + priorAdtTmReasonDscrt
				+ ", priorWkmnspReason=" + priorWkmnspReason + ", priorWkmnspReasonDscrt=" + priorWkmnspReasonDscrt
				+ ", intrAdtYn=" + intrAdtYn + ", intrAdtTm=" + intrAdtTm + ", minTm=" + minTm + ", maxTm=" + maxTm
				+ ", intplSat=" + intplSat + ", calResult=" + calResult + ", yearRate=" + yearRate + ", calAdtTm="
				+ calAdtTm + ", intrAdtFctr=" + intrAdtFctr + ", calSat=" + calSat + ", baseWkmnsp=" + baseWkmnsp
				+ ", etDfnSat=" + etDfnSat + ", etTrgtAdtTm=" + etTrgtAdtTm + ", wkmnspSat=" + wkmnspSat
				+ ", etcBdgtTm=" + etcBdgtTm + ", totPrjtBdgt=" + totPrjtBdgt + ", raBdgtTm=" + raBdgtTm
				+ ", flcmBdgtTm=" + flcmBdgtTm + ", otherBdgtTm=" + otherBdgtTm + ", rjctCmnt=" + rjctCmnt
				+ ", aprvReqEmplno=" + aprvReqEmplno + ", aprvReqDt=" + aprvReqDt + ", aprvCmpltDt=" + aprvCmpltDt
				+ ", cmpltCnt=" + cmpltCnt + ", retainTranYn=" + retainTranYn + ", profSatYn=" + profSatYn
				+ ", profSatReason=" + profSatReason + ", profBdgtYn=" + profBdgtYn + ", profBdgtReason="
				+ profBdgtReason + ", remark=" + remark + "]";
	}
	public CamelMap getEtcFctrYn() {
		return etcFctrYn;
	}
	public void setEtcFctrYn(CamelMap etcFctrYn) {
		this.etcFctrYn = etcFctrYn;
	}
	public String getSatTrgtYcnt() {
		return satTrgtYcnt;
	}
	public void setSatTrgtYcnt(String satTrgtYcnt) {
		this.satTrgtYcnt = satTrgtYcnt;
	}	
//	public CamelMap getSatTrgtYcnt() {
//		return satTrgtYcnt;
//	}
//	public void setSatTrgtYcnt(CamelMap satTrgtYcnt) {
//		this.satTrgtYcnt = satTrgtYcnt;
//	}
	public double getFstSatTrgtBftm() {
		return fstSatTrgtBftm;
	}
	public void setFstSatTrgtBftm(double fstSatTrgtBftm) {
		this.fstSatTrgtBftm = fstSatTrgtBftm;
	}
}	
