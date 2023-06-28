'use strict'
// 프로젝트 정보 초기 값
const prjtDef = {
	// 프로젝트 기본 정보
	prjtCd:null,			// 프로젝트 코드 00000-00-000
	prflId:null,			// 프로파일 id
	prjtCd1:null, 			// 프로젝트 코드1(Client Code) 00000
	prjtCd2:null, 			// 프로젝트 코드2(Service Code) 00
	prjtCd3:null, 			// 프로젝트 코드3(순번) 000
	rjctCmnt:null,			// 반려의견 -> 표준감사시간 계산에서는 Subject 로 사용
	
	
	prjtFrdt:null,			// 프로젝트 시작일 2019-01-01
	
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
	calAdtTm:null,				// 산출된 감사시간(a)
	intrAdtYn:null, 			// 내부회계감사(2조 이상) 여부
	intrAdtYnNm:null, 			// 내부회계감사(2조 이상) 여부 명
	intrAdtTm:null,				// 내부 감사시간
	
	// 기준 숙련도
	baseWkmnsp:1,				// 기준 숙련도
	
	// 기타
	stat:'CA',					// Status : CA: Calculate
}


function CalcSat(data){
	let thisObj = this;
	SHView.call(thisObj, data);
}
CalcSat.prototype = Object.create(SHView.prototype);
CalcSat.constructor = CalcSat;

CalcSat.prototype.calcSat = function(){
	let thisObj = this;
	let dfd = $.Deferred();
	
	
	if(!thisObj._validCheckCalcSat()) return;
	Helper.post("/project/calculateSat", thisObj.data)
	.done(function(data){
		thisObj.data.intrAdtYn 		= data.intrAdtYn;	// 내부감사 여부
		thisObj.data.intrAdtYnNm 	= data.intrAdtYnNm;	// 내부감사 여부 이름
		thisObj.data.intrAdtTm 		= data.intrAdtTm;	// 내부감사시간
		thisObj.data.factorVal 		= data.factorVal;	// 가감률
		thisObj.data.calAdtTm 		= data.calAdtTm;	// 산출된 감사시간(a)
		thisObj.data.calSat 		= data.calSat;			// 숙련도 반영전 표준 감사시간
		thisObj.data.baseWkmnsp 	= data.baseWkmnsp;	// 기준 숙련도(B)
		dfd.resolve();
	})
	.fail(function(){
		dfd.reject();
	});
	return dfd.promise();
};

CalcSat.prototype._validCheckCalcSat = function(){
	let thisObj = this;
	let prjt = this.data;
	
	function isNull(value){
		return (value === null || value === undefined || value === '')
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
		
		if(prjt.maxSatgrpCd && (tmpSatGrpCd > prjt.maxSatgrpCd)){
			Helper.MessageBox("표준감사시간대상 코드가 아니거나 " + prjt.prjtFrdt.substr(0, 4) + "년 제외 그룹에 해당합니다. 표준감사대상 여부에 \"X\"를 선택해주세요");
		}else{
			if(tmpSatGrpCd != prjt.satgrpCd){
				Helper.MessageBox(['한공회 기준에 의한 표준감사시간 그룹과 현재 선택된 표준감사시간 그룹이 불일치 합니다.', '(' + tmpMsg + ')']);
			}
		}
	}
	
	return true;
}
