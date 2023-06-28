'use strict'
// 프로젝트 정보 초기 값
const prjtDef = {
	// 프로젝트 기본 정보
	prjtCd:null,			// 프로젝트 코드 00000-00-000
	prflId:null,			// 프로파일 id
	prjtCd1:null, 			// 프로젝트 코드1(Client Code) 00000
	prjtCd2:null, 			// 프로젝트 코드2(Service Code) 00
	prjtCd3:null, 			// 프로젝트 코드3(순번) 000
	prjtNm:null,			// 프로젝트 명: Client명/용역명
	clntNm:null,			// Client명
	shrtNm:null,			// 용역명
	cntrtFee:null,			// 계약 보수 (원)
	chargPtr:null,			// 프로젝트 담당 파트너 사번
	chargPtrBdgt:null,		// EL 투입 시간
	chargPtrNm:null,		// 프로젝트 담당 파트너 이름
	chargPtrGradNm:null,	// 프로젝트 담당 파트너 직급명
	dspChargPtr:null,		// display용 프로젝트 담당 파트너
	chargMgr:null,			// 프로젝트 담당 매니저 사번
	chargMgrNm:null,		// 프로젝트 담당 매니저 이름
	chargMgrGradNm:null,	// 프로젝트 담당 매니저 직급명
	dspChargMgr:"",			// display용 프로젝트 담당 매니저
	rcrdMgr:null,			// 프로젝트 Record 매니저 사번
	rcrdMgrNm:null,			// 프로젝트 Record 매니저 이름
	rcrdMgrGradNm:null,		// 프로젝트 Record 매니저 직급명
	dspRcrdMgr:null,		// display용 Record 매니저
	desigAdtYn:null, 		// 지정감사 여부
	desigAdtYnNm:null, 		// 지정감사 여부
	prjtFrdt:null,			// 프로젝트 시작일 2019-01-01
	prjtTodt:null,			// 프로젝트 시작일 2019-12-31

	// 합산대상 프로젝트
	subPrjt:[], 					// 합산 대상 프로젝트 정보
	totCntrtFee:null,				// 총 계약 보수 (원) - 합산대상프로젝트의 계약 보수까지의 총합계
	
	satTrgtYn: null,				// 표준감사 대상 여부
	satTrgtYnNm: null,				// 표준감사 대상 여부 명
	
	indusCd:null,					// 산업 분류 코드
	indusNm:null,					// 산업 분류 이름(제조업, 서비스업, 건설업, 금융업, 도소매업, 기타)	
	indivAsset:null,				// 개별 자산
	consoSales:null,				// 연결 매출
	consoAsset:null,				// 연결 자산
	compSize:null,					// 기업 규모 : (연결 자산 + 연결 매출)/2
	listDvCd:null,					// 상장 구분 코드
	listDvNm:null,					// 상장 구분 이름(상장, 코넥스, 비상장)
	bizRprtYn:null,					// 사업보고서 제출대상 여부
	bizRprtYnNm:null,				// 사업보고서 제출대상 display 명
	priorAdtTm:null,				// 전기 감사 시간
	satgrp1ExptYn:null,				// 표준감사그룹1 예외사항 여부 : Y:무조건 그룹1로 선택
	satgrp1ExptYnNm:null,			// 표준감사그룹1 예외사항 여부 : Y:무조건 그룹1로 선택
	
	maxSatgrpCd:null,				// 표준감사그룹 코드(validation Check를 위함)
	satgrpCd:null,					// 표준감사그룹 코드 
	satgrpNm:null,					// 표준감사그룹 명(그룹1 ~ 그룹 11)
	usaListYn:null,					// 미국 상장 여부
	usaListYnNm:null,				// 미국 상장 여부 이름
	holdingsDivCd:null,				// 지주사 구분 코드 (금융지주사, 비금융지주사, 일반)
	holdingsDivNm:null,				// 지주사 구분 명
	consoFinstatYn:null,			// 연결 재무제표 작성 여부
	consoFinstatYnNm:null,			// 연결 재무제표 작성 여부 이름
	sbsidCnt:null,					// 자회사(종속회사) 수
	rprtScdlDt:null,				// 보고서 발행 예정일 
	firstAdtYn:null,				// 초도감사여부
	firstAdtFctr:null,				// 초도 감사 계수 - 그룹1일때 사용
	ifrsYn:null,					// IFRS 여부
	ifrsYnNm:null,					// IFRS 여부  display 명
	consoInvnt:null,				// 연결 재고자산
	consoAccntReceiv:null,			// 연결 매출 채권
	currConsoLossYn:null,			// 당기 연결 당기순손실예상여부
	currConsoLossYnNm:null,			// 당기 연결 당기순손실예상여부 display 명
	currAdtopinCd:null,				// 당기예상감사의견 코드
	currAdtopinNm:null,				// 당기예상감사의견  display 명
	intrTranAssetSales:null,		// 연결조정시 제거된 내부거래(매출+자산)
	relatCompAsset:null,			// 관계회사의 자산 금액
	sbsidSalesWithIntrTran:null,	// 내부거래전 자회사 매출
	sbsidAssetWithIntrTran:null,	// 내부거래 제거전 자회사 자산
	factorVal:1,					// 가감률

	// 표준감사시간 
	calSat:null,				// 계산식에 의해 산출된 기준 표준감사시간(숙련도 반영 전)
	etDfnSat:null,				// 기본산식 외 요소를 고려한 최종 표준감사시간
	etTrgtAdtTm:null,			// ET 목표 감사 투입시간(표준감사시간 대상)
	wkmnspSat:null,				// 숙련도 반영된 표준 감사시간	(etDfnSat * 기준 숙련도)
	etcBdgtTm:null,				// 표준감사시간외 감사등 예산 시간

	// 분배 예산
	totPrjtBdgt:null,			// 총 프로젝트 예산 (etTrgtAdtTm + etcBdgtTm)
	raBdgtTm:null,				// RA 배분 예산 시간
	raBdgtMgr:null,				// RA 예산 담당자 사번
	raBdgtMgrNm:null,			// RA 예산 담당자 이름
	raBdgtMgrGradNm:null,		// RA 예산 담당자 직급
	dspRaBdgtMgr:null,			// RA 예산 담당자 Display
	flcmBdgtTm:null,			// Fulcrum 배분 예산 시간
	flcmBdgtMgr:null,			// Fulcrum 예산 담당자 사번
	flcmBdgtMgrNm:null,			// Fulcrum 예산 담당자 이름
	flcmBdgtMgrGradNm:null,		// Fulcrum 예산 담당자 직급
	dspFlcmBdgtMgr:null,		// Fulcrum 예산 담당자 Display
	otherBdgtTm: null,			// Other 예산
	remainBdgt: null,			// 잔여 예산
	calAdtTm:null,				// 산출된 감사시간(a)
	intrAdtYn:null, 				// 내부회계감사(2조 이상) 여부
	intrAdtYnNm:null, 			// 내부회계감사(2조 이상) 여부 명
	intrAdtTm:null,				// 내부 감사시간
	baseWkmnsp:1,				// 기준 숙련도
	
	// 기타
	stat:null,					// Status : RG: 등록(Regist), RQ: 승인 요청(Request), RJ: 반려(Reject), CO: 승인 완료(Complete), DE: 삭제(Delete)
	rjctCmnt:null,				// 반려의견
	isUpdatedSubPrjt: "N",		// 합산 프로젝트 수정 여부 플래그
}


//합산대상 프로젝트
//합산대상 프로젝트
function SubPrjt(){
	this.pPrjtCd="";			// 상위 프로젝트 코드 00000-00-000
	this.prjtCd="";			// 프로젝트 코드 00000-00-000
	this.prjtCd1="";			
	this.prjtCd2="";			
	this.prjtCd3="";			
	this.prjtNm="";			// Client명/용역명
	this.chargPtr="";			// 프로젝트 담당 파트너 사번
	this.chargPtrNm="";		// 프로젝트 담당 파트너 이름
	this.chargPtrGradNm="";	// 프로젝트 담당 파트너 직급명
	this.dspChargPtr="",
	this.chargMgr="";			// 프로젝트 담당 매니저 사번
	this.chargMgrNm="";		// 프로젝트 담당 매니저 이름
	this.chargMgrGradNm="";	// 프로젝트 담당 매니저 직급명
	this.dspChargMgr="",
	this.cntrtFee=0;		// 계약 보수 (원)
}

function ProjectInfo(data){
	let thisObj = this;
	SHView.call(thisObj, data);
}
ProjectInfo.prototype = Object.create(SHView.prototype);
ProjectInfo.constructor = ProjectInfo;

ProjectInfo.prototype._makeSubPrjtObj = function(subPrjtObj){
	return new Helper.ObjectProxy(Object.assign(new SubPrjt(), subPrjtObj), {
		get: function(target, key){
			if(key == "dspChargPtr"){
				return target.chargPtr?target.chargPtrNm + "(" + target.chargPtr + ") " + target.chargPtrGradNm:"";
			}else if(key == "dspChargMgr"){
				return target.chargMgr?target.chargMgrNm + "(" + target.chargMgr + ") " + target.chargMgrGradNm:"";
			}else if(key == "prjtCd1"){
				return target.prjtCd.split("-")[0];
			}else if(key == "prjtCd2"){
				return target.prjtCd.split("-")[1];
			}else if(key == "prjtCd3"){
				return target.prjtCd.split("-")[2];
			}else{
				return target[key];
			}
		},
		set: function(target, key, value){
			if(!["dspChargPtr", "dspChargMgr", "prjtCd1", "prjtCd2", "prjtCd3"].includes(key)) target[key] = value;
		},
		enumerable: true,
	});
}
ProjectInfo.prototype.initSubPrjt = function(){
	let thisObj = this;
	let subPrjt = thisObj.data.subPrjt;
	if(subPrjt){
		for(let i=0; i<subPrjt.length; i++){
			subPrjt[i] = thisObj._makeSubPrjtObj(subPrjt[i]);
		}
		thisObj.data.subPrjt = subPrjt;
	}
}
ProjectInfo.prototype.addSubPrjt = function(subPrjtObj){
	let thisObj = this;
	
	//합산대상 프로젝트
	let tmpSubPrjt = thisObj._makeSubPrjtObj(subPrjtObj); 
	
	if(thisObj.data.subPrjt){
		if(thisObj.data.subPrjt.length >= 6){
			Helper.MessageBox(['합산 대상 코드는 최대 6개까지 추가 가능합니다.']);
			return;
		}
		thisObj.data.subPrjt = thisObj.data.subPrjt.concat(tmpSubPrjt);
	}else{
		thisObj.data.subPrjt = [].concat(tmpSubPrjt);
	}
}

ProjectInfo.prototype.updSubPrjt = function(idx, subPrjtObj){
	let thisObj = this;
	if(0<= idx && idx < thisObj.data.subPrjt.length){
		thisObj.data.subPrjt[idx] =  thisObj._makeSubPrjtObj(subPrjtObj);
		thisObj.data.subPrjt = [].concat(thisObj.data.subPrjt);
	}
}

ProjectInfo.prototype.rmSubPrjt = function(idx){	
	let thisObj = this;
	let tmp = [].concat(thisObj.data.subPrjt);
	tmp.splice(idx, 1);
	thisObj.data.subPrjt = tmp;
},

ProjectInfo.prototype.calcSat = function(){
	let thisObj = this;
	let dfd = $.Deferred();
	
	
	if(!thisObj._validCheckCalcSat()) return;
	Helper.post("/project/calculateSat", thisObj.data)
	.done(function(data){
		console.log(data)
		thisObj.data.intrAdtYn = data.intrAdtYn;	// 내부감사 여부
		thisObj.data.intrAdtTm = data.intrAdtTm;	// 내부감사시간
		thisObj.data.factorVal = data.factorVal;	// 가감률
		thisObj.data.calAdtTm = data.calAdtTm;	// 산출된 감사시간(a)
		thisObj.data.calSat = data.calSat;			// 숙련도 반영전 표준 감사시간
		thisObj.data.baseWkmnsp = data.baseWkmnsp;	// 기준 숙련도(B)
		dfd.resolve();
	})
	.fail(function(){
		dfd.reject();
	});
	return dfd.promise();
};
/*
ProjectInfo.prototype.calcSat = function(){	// 표준감사시간 계산하기
	let dfd = $.Deferred();
	
	let thisObj = this;
	let prjt = thisObj.data;

	// #step 1. 유효성 검사 
	if(!thisObj._validCheckCalcSat()) return;
	
	// #step 2. 표준 감사 시간 도출을 위한 계산
	let compSize = Number(prjt.compSize);
	let intrTranAssetSales = Number(prjt.intrTranAssetSales)
	let sbsidAssetWithIntrTran = Number(prjt.sbsidAssetWithIntrTran)
	let sbsidSalesWithIntrTran = Number(prjt.sbsidSalesWithIntrTran)
	let relatCompAsset = Number(prjt.relatCompAsset)
	
	// Excel: 기업규모 - 표준감사시간표 적용 기업규모
	let tmpCompSize = Math.floor((compSize + (intrTranAssetSales/2) - ((sbsidAssetWithIntrTran + sbsidSalesWithIntrTran + relatCompAsset)/2) )/100000000);	// 보간법 적용을 위한 기업 규모
	
	// Excel: 기업규모보간법기준
	let intpCompSize = Number(thisObj._getIntplCompSize(tmpCompSize));
	
	// 위험계정 비중 : (연결 재고자산 + 연결 매출 채권) / 연결 자산
	let rskacnt = Math.round(((prjt.consoInvnt + prjt.consoAccntReceiv) / prjt.consoAsset) * 100, 0);
	
	// 프로젝트 시작일 준으로 적용 요율 적용
	const year = this.data.prjtFrdt.substr(0, 4);
	
	// 계산을 위한 상수값 가져오기(DB 쿼리)
	$.when(
			Helper.post("/project/interpolation", {prflId: prjt.prflId, satgrpCd: prjt.satgrpCd, indusCd: prjt.indusCd, intpCompSize: intpCompSize,}),
			Helper.post("/project/satgrpinfo", {prflId: prjt.prflId, satgrpCd: prjt.satgrpCd}),
			Helper.post("/project/factor", {satgrpCd: prjt.satgrpCd, sbsid: prjt.sbsidCnt,rskacnt: rskacnt,prflId: prjt.prflId,}),
			Helper.post("/project/year_rate", {satgrpCd: prjt.satgrpCd, year: year})
	).done(function(_inpl, _satGrpInfo, _factorVal, _yearRate){
		let inpl 		= $.extend({baseSat:0, inplVal:1}, _inpl);
		let satGrpInfo 	= $.extend({baseWkmnsp: 1, maxRatio: 1}, _satGrpInfo);
		let factorVal 	= Number(thisObj._getCalFactorVal(_factorVal));
		let yearRate 	= Number(_yearRate);
		
		// Excel: 가감요인 고려전 감사시간
		let tmpSat = Number(inpl.baseSat) + ((Number(tmpCompSize)-Number(intpCompSize)) * Number(inpl.intplVal))
		// Excel: 산식에 따른 결과 (A)
		let calSat = tmpSat * factorVal;
		// Excel: A * B
		let calRateSat = calSat * yearRate;
		
		// Excel: 하한
		let minSat = Number(prjt.priorAdtTm);
		// Excel: 상한
		let maxSat = minSat * Number(satGrpInfo.maxRatio);
		// Excel: 2차 * 적용율, 상하한 적용 후 (C) == 양식: 산출된 감사시간(a)
		let calSat2 = (minSat==0?calRateSat:minSat>calRateSat?minSat:prjt.satgrpCd=="SATGRP11"?minSat:Math.min(calRateSat, maxSat));
				
		// #step 3. 계산된 결과값 저장
		let intrAdtYn;
 		let intrAdtTm = 0;
		if(prjt.listDvCd == 'LISTED' && prjt.indivAsset >= 2000000000000) {
			intrAdtYn = 'Y'
			if(prjt.holdingsDivCd == 'FIN_HLD' || prjt.holdingsDivCd == 'NFIN_HLD'){
				intrAdtTm = calSat2 * 0.15;
			}else{
				intrAdtTm = calSat2 * 0.3;
			}
		}else{
			intrAdtYn = 'N'
		}
		
		thisObj.data.intrAdtYn = intrAdtYn;  				// 내부감사 여부
		thisObj.data.intrAdtTm = intrAdtTm;				// 내부감사시간
		thisObj.data.factorVal = factorVal;					// 가감률
		thisObj.data.calAdtTm = calSat2;					// 산출된 감사시간(a)
		thisObj.data.calSat = calSat2 + intrAdtTm;			// 숙련도 반영전 표준 감사시간
		thisObj.data.baseWkmnsp = satGrpInfo.baseWkmnsp;	// 기준 숙련도(B)
		
		dfd.resolve();
	}).fail(function(){
		dfd.reject();
	})
	return dfd.promise();
}*/


ProjectInfo.prototype._validCheckCalcSat = function(){
	let thisObj = this;
	let prjt = this.data;
	
	function isNull(value){
		return (value === null || value === undefined || value === '')
	}
	
	if(prjt.prjtCd2 != '01') {
		Helper.MessageBox("표준감사시간대상 코드가 아닙니다. 표준감사대상 여부에 \"X\"를 선택해주세요");
		return false;
	}
	
	let chkFields = [
		{cd:'prjtFrdt', nm:'Project 시작일', valid:function(value){return !isNull(value);}},
		{cd:'indusCd', nm:'표준산업 분류', valid:function(value){return !isNull(value);}},
		{cd:'listDvCd', nm:'상장 구분', valid:function(value){return  !isNull(value);}},
		{cd:'indivAsset', nm:'개별 자산', valid:function(value){return isNull(value)?false:(value >=0);}},
		{cd:'bizRprtYn', nm:'사업보고서 제출 대상', valid:function(value){return !isNull(value);}},
		{cd:'consoSales', nm:'연결 매출', valid:function(value){return isNull(value)?false:(value >=0);}},
		{cd:'satgrp1ExptYn', nm:'그룹1의 예외사항 여부', valid:function(value){return !isNull(value);}},
		{cd:'consoAsset', nm:'연결 자산', valid:function(value){return isNull(value)?false:(value >0);}},
		{cd:'priorAdtTm', nm:'전기 감사 시간', valid:function(value){return isNull(value)?false:(value >=0);}},
		{cd:'satgrpCd', nm:'표준 감사 시간 그룹 정보', valid:function(value){return !isNull(value);}},
		{cd:'usaListYn', nm:'미국 상장 여부', valid:function(value){return !isNull(value);}},
		{cd:'holdingsDivCd', nm:'지주사 분류', valid:function(value){return !isNull(value);}},
		{cd:'consoFinstatYn', nm:'연결 작성 여부', valid:function(value){return !isNull(value);}},
		{cd:'sbsidCnt', nm:'자회사 수', valid:function(value){return isNull(value)?false:(value >=0) && /[0-9]+/.test(value)}},
		{cd:'rprtScdlDt', nm:'보고서 발행 예정일', valid:function(value){return !isNull(value);}},
		{cd:'firstAdtYn', nm:'초도 감사 여부', valid:function(value){return !isNull(value);}},
		{cd:'firstAdtFctr', nm:'초도 감사 가산', valid:function(value){return !isNull(value);}},
		{cd:'ifrsYn', nm:'IFRS 여부', valid:function(value){return !isNull(value);}},
		{cd:'consoAccntReceiv', nm:'연결 매출채권', valid:function(value){return isNull(value)?false:(value >=0);}},
		{cd:'consoInvnt', nm:'연결 재고자산', valid:function(value){return isNull(value)?false:(value >=0);}},
		{cd:'currConsoLossYn', nm:'당기 연결 당기순손실 예상 여부', valid:function(value){return !isNull(value);}},
		{cd:'currAdtopinCd', nm:'당기 예상 감사의견 비적정 여부', valid:function(value){return !isNull(value);}},
	];
	
	let msg=[];
	for(let i=0; i<chkFields.length; i++){
		if(chkFields[i].cd == 'firstAdtFctr' && !(prjt.satgrpCd == "SATGRP01" && prjt.firstAdtYn=='Y')){
			continue;
		}else{
			if(!chkFields[i].valid(prjt[chkFields[i].cd])){
				msg.push(chkFields[i].nm)
			}	
		}
	}
	
	
	if(msg.length > 0){
		Helper.MessageBox(['표준감사시간 산출을 위한 필수 값을 입력하여 주시기 바랍니다', '&nbsp;','<b>[미입력 필드]</b>'].concat(msg));
		return false;
	}
	
	let dateCtrl = new Helper.DateCtrl();
	let dateStr = Helper.replaceAll(prjt.prjtFrdt, "-", "");
	if(dateCtrl.toTimeObject(dateStr) < dateCtrl.toTimeObject("20180101")){
		msg.push("Project 시작일은 2018-01-01 이후 날짜만 지정 가능합니다.");
	}
	if(prjt.satgrpCd == "SATGRP01" && prjt.firstAdtYn=='Y' && !(1.05 <= prjt.firstAdtFctr && prjt.firstAdtFctr <= 1.2)){
		msg.push('초도 감사 가산은 1.05 ~ 1.2 사이 값을 입력해야 합니다.');
	} 
	
	if(msg.length > 0){
		Helper.MessageBox(msg);
		return false;
	}
	
	
	
	//	그룹1:  상장, 기업규모 >= 5조, 개별자산 >= 2조
	//	그룹3:  상장 or 상장예정, 개별자산 >= 2조
	//	그룹3:  상장 or 상장예정, 개별자산 >= 5,000억
	//	그룹4:  상장 or 상장예정, 개별자산 >= 1,000억
	//	그룹5:  상장 or 상장예정, 개별자산 >= 500억
	//	그룹6:  상장 or 상장예정, 개별자산 < 500억
	//	그룹7:  사업보고서 제출대상 or 코넥스 상장
	//	그룹8:  비상장, 개별자산 >= 1,000억
	//	그룹9:  비상장, 개별자산 >= 500억
	//	그룹10: 비상장, 개별자산 >= 200억
	//	그룹11: 그외
	let tmpSatGrpCd;
	if(prjt.satgrp1ExptYn != "Y"){
		let tmpMsg = "";
		if(prjt.listDvCd == "LISTED" && prjt.compSize >= 5000000000000 && prjt.indivAsset >= 2000000000000){
			// 그룹 1
			tmpSatGrpCd = "SATGRP01";
			tmpMsg = "상장사이고 기업규모가 5조 이상이며 개별 자산이 2조 이상이면 '그룹 1'";
		}else if(prjt.listDvCd == "LISTED" && prjt.indivAsset >= 2000000000000){
			// 그룹 2
			tmpSatGrpCd = "SATGRP02";
			tmpMsg = "상장사이고 개별 자산이 2조 이상이면 '그룹 2'";
		}else if(prjt.listDvCd == "LISTED" && prjt.indivAsset >=  500000000000){
			// 그룹 3
			tmpSatGrpCd = "SATGRP03";
			tmpMsg = "상장사이고 개별 자산이 5000억 이상이면 '그룹 3'";
		}else if(prjt.listDvCd == "LISTED" && prjt.indivAsset >=  100000000000){
			// 그룹 4
			tmpSatGrpCd = "SATGRP04";
			tmpMsg = "상장사이고 개별 자산이 1000억 이상이면 '그룹 4'";
		}else if(prjt.listDvCd == "LISTED" && prjt.indivAsset >=   50000000000){
			// 그룹 5
			tmpSatGrpCd = "SATGRP05";
			tmpMsg = "상장사이고 개별 자산이 500억 이상이면 '그룹 5'";
		}else if(prjt.listDvCd == "LISTED" && prjt.indivAsset <    50000000000){
			// 그룹 6
			tmpSatGrpCd = "SATGRP06";
			tmpMsg = "상장사이고 개별 자산이 500억 미만이면 '그룹 6'";
		}else if(prjt.bizRprtYn == "Y" || prjt.listDvCd == "KONEX"){
			// 그룹 7
			tmpSatGrpCd = "SATGRP07";
			tmpMsg = "사업보고서 제출 대상이고, 코넥스 상장이면 '그룹 7'";
		}else if(prjt.listDvCd == "UNLISTED" && prjt.indivAsset >= 100000000000){
			// 그룹 8
			tmpSatGrpCd = "SATGRP08";
			tmpMsg = "비 상장사이고 개별 자산이 1000억 이상이면 '그룹 8'";
		}else if(prjt.listDvCd == "UNLISTED" && prjt.indivAsset >=  50000000000){
			// 그룹 9
			tmpSatGrpCd = "SATGRP09";
			tmpMsg = "비 상장사이고 개별 자산이 500억 이상이면 '그룹 9'";
		}else if(prjt.listDvCd == "UNLISTED" && prjt.indivAsset >=  20000000000){
			// 그룹 10
			tmpSatGrpCd = "SATGRP10";
			tmpMsg = "비 상장사이고 개별 자산이 200억 이상이면 '그룹 10'";
		}else{
			// 그룹 11
			tmpSatGrpCd = "SATGRP11";
			tmpMsg = "그룹1 ~ 그룹10에 해당하는 조건을 충족하지 못하였으므로 '그룹 11'";
		}
		
		if(prjt.maxSatgrpCd){
			if(tmpSatGrpCd > prjt.maxSatgrpCd){
				console.log(tmpSatGrpCd, prjt.maxSatgrpCd, prjt.prjtFrdt)
				tmpMsg = "표준감사시간대상 코드가 아니거나 " + prjt.prjtFrdt.substr(0, 4) + "년 제외 그룹에 해당합니다. 표준감사대상 여부에 \"X\"를 선택해주세요";	
			}
		}
		
		if(tmpSatGrpCd != prjt.satgrpCd){
			Helper.MessageBox(['[참고] 한공회 기준에 의한 표준감사시간 그룹과 현재 선택된 표준감사시간 그룹이 불일치 합니다.', '한공회 기준: ' + tmpMsg]);
		}
	}
	
	
	return true;
}


/*ProjectInfo.prototype._getIntplCompSize = function(compSize){
	//  기업규모보간법기준
	const prjt = this.data;
	
	let intplCompSize;
	if(prjt.satgrpCd == 'SATGRP01'){
		intplCompSize = compSize>=3000000?3000000:compSize>=2000000?2000000:compSize>=1000000?1000000:compSize>=500000?500000:compSize>=200000?200000:compSize>=100000?100000:compSize>=50000?50000:0;
	}else if(prjt.satgrpCd == 'SATGRP02'){
		intplCompSize = compSize>=40000?40000:compSize>=30000?30000:compSize>=20000?20000:compSize>=10000?10000:0;
	}else if(prjt.satgrpCd == 'SATGRP03'){
		intplCompSize = compSize>=50000?50000:compSize>=20000?20000:compSize>=15000?15000:compSize>=10000?10000:compSize>=8000?8000:compSize>=5000?5000:compSize>=3000?3000:0;
	}else if(prjt.satgrpCd == 'SATGRP04'){
		intplCompSize = compSize>=10000?10000:compSize>=5000?5000:compSize>=4000?4000:compSize>=3000?3000:compSize>=2000?2000:compSize>=1000?1000:compSize>=500?500:0;
	}else if(prjt.satgrpCd == 'SATGRP05'){			
		intplCompSize = compSize>=2000?2000:compSize>=1500?1500:compSize>=1000?1000:compSize>=800?800:compSize>=500?500:compSize>=300?300:0;			
	}else if(prjt.satgrpCd == 'SATGRP06'){
		intplCompSize = compSize>=1000?1000:compSize>=500?500:compSize>=400?400:compSize>=300?300:compSize>=200?200:compSize>=100?100:0;
	}else if(prjt.satgrpCd == 'SATGRP07'){
		intplCompSize = compSize>=500000?500000:compSize>=100000?100000:compSize>=50000?50000:compSize>=10000?10000:compSize>=5000?5000:compSize>=1000?1000:compSize>=500?500:compSize>=200?200:compSize>=100?100:0;
	}else if(prjt.satgrpCd == 'SATGRP08'){
		intplCompSize = compSize>=500000?500000:compSize>=100000?100000:compSize>=50000?50000:compSize>=20000?20000:compSize>=10000?10000:compSize>=5000?5000:compSize>=2000?2000:compSize>=1000?1000:compSize>=500?500:0;
	}else if(prjt.satgrpCd == 'SATGRP09'){
		intplCompSize = compSize>=2000?2000:compSize>=1000?1000:compSize>=900?900:compSize>=800?800:compSize>=700?700:compSize>=600?600:compSize>=500?500:compSize>=200?200:0;
	}else if(prjt.satgrpCd == 'SATGRP10'){
		intplCompSize = compSize>=1000?1000:compSize>=500?500:compSize>=400?400:compSize>=300?300:compSize>=200?200:compSize>=100?100:0;
	}else{
		intplCompSize = 0;
	}
	
	return intplCompSize;
}
ProjectInfo.prototype._getCalFactorVal = function(factors){
	let thisObj = this;
	let prjt = thisObj.data;
	
	// default 계수 값
	let factorVal = {
		CONSO: 1,		// 연결 계수
		SBSID: 1, 		// 자회사계수
		RSKACNT: 1,		// 위험계정 계수
		FINHLD: 1,		// 금융지주사 계수    (지주사 구분: 금융 지주사)
		NFINHLD: 1,		// 비금융지주사 계수 (지수사 구분: 비금융 지주사)
		USALST: 1,		// 미상장계수
		KAM: 1,			// KAM 계수
		IFRS: 1,		// IFRS계수
		LOSS: 1,		// 당기순손실계수
		HARVW : 1,		// 반기검토 계수
		QURVW : 1,		// 분기검토 계수
		ADTOPIN: 1,		// 비적정의견 계수
		FIRSTADT: 1		// 초도감사 계수
	}	
	
	// 계수 조건에 따른 값 설정
	for(let i=0; i<factors.length; i++){
		console.log("factorCd: " + factors[i].factorCd + ", value: " + factors[i].val1);
		
		switch(factors[i].factorCd){
			case "CONSO" : 
				if(thisObj.data.consoFinstatYn == 'Y') {
					factorVal[factors[i].factorCd] = factors[i].val1; 
					console.log("factorVal : " + factorVal[factors[i].factorCd]);
				}
				break;
			case "FINHLD" : 
				if(thisObj.data.holdingsDivCd == 'FIN_HLD') {
					factorVal[factors[i].factorCd] = factors[i].val1;
					console.log("factorVal : " + factorVal[factors[i].factorCd]);
				}
				break;
			case "NFINHLD" : 
				if(thisObj.data.holdingsDivCd == 'NFIN_HLD') {
					factorVal[factors[i].factorCd] = factors[i].val1;
					console.log("factorVal : " + factorVal[factors[i].factorCd]);
				}
				break;
			case "USALST" : 
				if(thisObj.data.usaListYn == 'Y') {
					factorVal[factors[i].factorCd] = factors[i].val1;
					console.log("factorVal : " + factorVal[factors[i].factorCd]);
				}
				break;
			case "KAM" : 
				// 상장사이고, 개별자산이 1,000억 이상
				if(thisObj.data.listDvCd == 'LISTED' && this.data.indivAsset >= 100000000000) {
					factorVal[factors[i].factorCd] = factors[i].val1;
					console.log("factorVal : " + factorVal[factors[i].factorCd]);
				}
				break;
			case "IFRS" :
				if(thisObj.data.ifrsYn == 'Y') {
					factorVal[factors[i].factorCd] = factors[i].val1;
					console.log("factorVal : " + factorVal[factors[i].factorCd]);
				}
				break;
			case "LOSS" : 
				if(thisObj.data.currConsoLossYn == 'Y') {
					factorVal[factors[i].factorCd] = factors[i].val1;
					console.log("factorVal : " + factorVal[factors[i].factorCd]);
				}
				break;
			case "ADTOPIN" :
				if(thisObj.data.currAdtopinCd == 'Y') {
					factorVal[factors[i].factorCd] = factors[i].val1;
					console.log("factorVal : " + factorVal[factors[i].factorCd]);
				}
				break;
			case "FIRSTADT" :
				if(thisObj.data.satgrpCd == 'SATGRP01'){
					if(this.data.firstAdtYn == 'Y'){
						factorVal[factors[i].factorCd] = this.data.firstAdtFctr;	
					}
				}else{
					if(this.data.firstAdtYn == 'Y'){
						factorVal[factors[i].factorCd] = factors[i].val1;	
					}
				}
				console.log("factorVal : " + factorVal[factors[i].factorCd]);
				break;
			default:
				// 위험계정 계수, 자회사 계수
				factorVal[factors[i].factorCd] = factors[i].val1;
				console.log("factorVal : " + factorVal[factors[i].factorCd]);
				break;
		}
	}
	
	let retVal = 1;
	for(let key in factorVal) retVal *= factorVal[key];
	
	return retVal;
}*/
