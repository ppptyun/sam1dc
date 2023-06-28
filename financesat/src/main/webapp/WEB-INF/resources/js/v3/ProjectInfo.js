if (!String.prototype.bool) {
    Object.defineProperty(String.prototype, 'bool', {
        get: function() {
           return (/^(true|1)$/i).test(this);
        }
    });
}

//################################################################################
// 공통 정보
//################################################################################
const commonMixin = {
	data: {
		formDiv:'v3',
		canEdit: false,
		canDelete: false,
		stat: null,
		cretedInfo: {dt: null, emplno: null, kornm: null},
		modifyInfo: {dt: null, emplno: null, kornm: null},
		onMsg: true
	},
	computed: {
		isIE: function(){
			var agent = navigator.userAgent.toLowerCase();
            if ((navigator.appName == 'Netscape' && agent.indexOf('trident') != -1) || (agent.indexOf("msie") != -1)) {
                return true
            } else {
                return false
            }
		}
	},
	methods: {
		getParam: function(){
			let computed = {}
			for(let id in this.$options.computed){if(!id.startsWith("_")) computed[id] = this[id]}
			return Object.assign({}, this.$data, computed) // data와 computed 필드 모두 추가하기 위함.
		},
		loadSatgrpList: function(){		// 표준 감사시간 그룹 정보 load
			let thisObj = this;
			if(this.bizFrdt){				
				Helper.post("/calcSat/getSatgrpList", {year: this.bizFrdt.substr(0, 4), prflId: this.prflId})
				.done(function(list){
					thisObj.satgrpList = list;
				});
			}else{
				thisObj.satgrpList = [];
			}
		},
		getGrpPrfl: function(){
			let $deferred = $.Deferred();
			Helper.post('/calcSat/grpPrfl', {prflId: this.prflId, satgrpCd: this.satgrp.cd})
			.done(function(prfl){
				$deferred.resolve(prfl)
			})
			return $deferred.promise();
		},
		getBaseWkmnsp: function(){
			let $deferred = $.Deferred();
			Helper.post('/calcSat/basewkmnsp', {prflId: this.prflId, satgrpCd: this.satgrp.cd})
			.done(function(wkmnsp){
				$deferred.resolve(wkmnsp)
			})
			return $deferred.promise(); 
		},
		onDeleteAdtr: function(){
			this.priorAdtr = {cd:null, nm: null};
		},
		onPopupAdtr: function(){
			let thisObj = this;
			popAdtr.openPopup("prior-auditor", function(data){
				thisObj.priorAdtr = data;
			});
		},
		onSimpleCalc: function(target, wkmnsp, msg){
			let thisObj = this;
			if(wkmnsp===null || wkmnsp===0){
				Helper.MessageBox(msg)
				return;
			}
			popSimpleWkmnsp.openPopup("simple-calc-wkmnsp",{wkmnsp: wkmnsp}, function(data){
				thisObj[target] = data;
			});
		},
		onCalcSat: function(isUpdate){
			let $deferred = $.Deferred();
			let thisObj = this;
			if(thisObj.indusDv.cd === "INDUS_FIN" && thisObj.satgrp.cd === "SATGRP01" && thisObj.listDv === "UNLISTED" && thisObj.bizRprtYn.cd === "Y" && thisObj.etcFctr >= 1.7 && thisObj.etcFctr <= 1.8){
				console.log("분기검토조정계수 예외 적용: 금융업, 그룹7, 비상장, 사업보고서제출대상, 1.7~1.8");
			}else if(thisObj.satgrp.cd === null || thisObj.satgrp.cd === ''
				|| thisObj.listDv.cd === null || thisObj.listDv.cd === ''
				|| thisObj.listDv.cd !== 'TOBE_LISTED' ){
				
				if(thisObj.etcFctr !== 1){
					Helper.MessageBox("상장예정이 아닌 회사의 분기검토조정계수는 1.000으로 초기화 됩니다.");
					thisObj.etcFctr = 1;
				}
			}
			Helper.post("/calcSat/calcsat", this.getParam())
			.done(function(data){
				Object.assign(thisObj, data);
				if(isUpdate) thisObj.etDfnSat = thisObj.calSat;
				$deferred.resolve();
			})
			.fail(function(){
				$deferred.reject();
			})
			return $deferred.promise();
		},
		updateFactorValue: function(name, param, initValue){	// DB로부터 계수 값을 가져와 업데이트 하기
			let thisObj = this;
			
			Helper.post("/calcSat/getFactorValue", Object.assign({
				prflId : this.prflId,
				satgrpCd : this.satgrp.cd,
			}, param))
			.done(function(data){
				if(data.div === 'value'){
					thisObj[name] = Number(data.val1);
				}else if(data.div === 'range'){
					let range = {
						min: Number(data.val1),
						max: Number(data.val2),
						description: data.val1 + " ~ " + data.val2
					}
					thisObj[name+'Range'] = range;
					
					if(initValue === undefined){
						thisObj.setNumber(name, thisObj[name])						
					}else{
						thisObj.setNumber(name, initValue);
					}
				}
			})
		},
		refresh: function(){ // 종속성이 있는 항목 업데이트
			let thisObj = this;
			
			let func = {
				satgrp: function(){
					if(thisObj.satgrp1ExptYn.cd === "Y"){
						thisObj.setSelect('satgrp', 'SATGRP01');
					}
				},
				maxRatio: function(){
					thisObj.satgrpList
					if(thisObj.satgrp.cd === null || thisObj.satgrp.cd === ''){
						thisObj.maxRatio = 0
					}else{
						let tmp = thisObj.satgrpList.filter(function(item){
							return item.satgrpCd === thisObj.satgrp.cd
						})[0];
						
						if(tmp){
							thisObj.maxRatio = Number(tmp.maxRatio)	
						}else{
							thisObj.maxRatio = 0
						}
					}
				},
				revwCnt: function(){
					if( thisObj.satgrp.cd === null || thisObj.satgrp.cd === ''){
						thisObj.revwCnt = 0;
					}else{
						thisObj.updateFactorValue('revwCnt', {factorCd : 'HARVWCNT'})						
					}
				},
				intrAdtYcnt: function(){
					// 내부회계감사 시행연차
					if(    thisObj.satgrp.cd === null || thisObj.satgrp.cd === '' 
						|| thisObj.listDv.cd === null || thisObj.listDv.cd === ''
						|| thisObj.bizFrdt === null){
						thisObj.setSelect('intrAdtYcnt', "")
					}else{
						let indivAsset = Number(thisObj.indivAsset);
						let tmp = Number(thisObj.bizFrdt.substr(0, 4)) - 2018;
						
						// 엑셀에서 결산월및적용과구분 시트 계산
						if(thisObj.listDv.cd ==='LISTED'){
							if(indivAsset >= 2000000000000 ){
								thisObj.setSelect('intrAdtYcnt', tmp)
							}else if(indivAsset >= 500000000000){
								tmp -= 1;
							}else if(indivAsset >= 100000000000){
								tmp -= 3; 
							}else{
								tmp -= 4;
							}
							
							if(tmp<=0){
								tmp = ""
							}else if(tmp>4){
								tmp = "4"
							}else{
								tmp = ""+tmp;
							}
							
							thisObj.setSelect('intrAdtYcnt', tmp)
						}else{
							thisObj.setSelect('intrAdtYcnt', "")	
						}
					}
				},
				etcFctr: function(){
					if(thisObj.satgrp.cd === null || thisObj.satgrp.cd === ''
						|| thisObj.listDv.cd === null || thisObj.listDv.cd === ''
						|| thisObj.listDv.cd !== 'TOBE_LISTED' ){
						thisObj.etcFctr = 1;
					}
				},
				kamYn: function(){
					if(thisObj.satgrp.cd === null || thisObj.satgrp.cd === ''
						|| thisObj.listDv.cd === null || thisObj.listDv.cd === ''){
						thisObj.setSelect('kamYn', "");
					}else if(thisObj.listDv.cd==="LISTED"){
						thisObj.setSelect('kamYn', "Y");
					}else{
						thisObj.setSelect('kamYn', "N");
					}
				},
				bizTodt: function(){
					if(thisObj.bizFrdt){
						let dt = new Date(thisObj.bizFrdt)
						thisObj.bizTodt = (new Date(dt.setMonth(dt.getMonth()+11))).toISOString().substr(0, 7);
					}
				},
				bizRprtYn: function(){
					if(thisObj.satgrp.cd === null || thisObj.satgrp.cd === ''
						|| thisObj.listDv.cd === null || thisObj.listDv.cd === ''){
						thisObj.setSelect('bizRprtYn', "");
					}else if(thisObj.listDv.cd === "LISTED"){
						thisObj.setSelect('bizRprtYn', 'Y');
					}
				},
				ifrsYn: function(){
					if(thisObj.satgrp.cd === null || thisObj.satgrp.cd === ''
						|| thisObj.listDv.cd === null || thisObj.listDv.cd === ''){
						thisObj.setSelect('ifrsYn', "");
					}else if(thisObj.listDv.cd === "LISTED"){
						thisObj.setSelect('ifrsYn', "Y");
					}
				},
				ifrsFctr : function() {	// IFRS 여부 계수
					if(thisObj.satgrp.cd === null || thisObj.satgrp.cd === ''
						|| thisObj.ifrsYn.cd === null || thisObj.ifrsYn.cd === ''){
						thisObj.ifrsFctr = 1
					}else if(thisObj.ifrsYn.cd === 'Y'){
						thisObj.updateFactorValue('ifrsFctr', {factorCd : 'IFRS'})
					}else{
						thisObj.ifrsFctr = 1
					}
				},
				holdingsFctr : function() {	// 지주사 분류 계수
					if(thisObj.satgrp.cd === null || thisObj.satgrp.cd === ''
						|| thisObj.holdingsDv.cd === null || thisObj.holdingsDv.cd === ''){
						thisObj.holdingsFctr = 1
					}else if(thisObj.holdingsDv.cd === 'FIN_HLD'){
						thisObj.updateFactorValue('holdingsFctr', {factorCd : 'FINHLD'})
					}else if(thisObj.holdingsDv.cd === 'NFIN_HLD'){
						thisObj.updateFactorValue('holdingsFctr', {factorCd : 'NFINHLD'})
					}else{
						thisObj.holdingsFctr = 1
					}
				},
				firstAdtYn: function(){
					if(thisObj.satgrp.cd === null || thisObj.satgrp.cd === ''
						|| thisObj.contiAdtCnt.cd === null || thisObj.contiAdtCnt.cd === ''){
						thisObj.setSelect('firstAdtYn', "");
					}else if(thisObj.contiAdtCnt.cd === "1"){
						thisObj.setSelect('firstAdtYn', "Y");
					}
				},
				firstAdtFctr : function() {	// 초도 감사 여부 계수
					if(thisObj.satgrp.cd === null || thisObj.satgrp.cd === ''
						|| thisObj.firstAdtYn.cd === null || thisObj.firstAdtYn.cd === ''){
						thisObj.firstAdtFctr = 1
					}else if(thisObj.firstAdtYn.cd === 'Y'){
						thisObj.updateFactorValue('firstAdtFctr', {factorCd : 'FIRSTADT'}, 1.05)
					}else{
						thisObj.firstAdtFctr = 1
					}
				},
				usaListFctr : function() {	// 미국 상장 여부 계수
					if(thisObj.satgrp.cd === null || thisObj.satgrp.cd === ''
						|| thisObj.usaListYn.cd === null || thisObj.usaListYn.cd === ''){
						thisObj.usaListFctr = 1
					}else if(thisObj.usaListYn.cd === 'Y'){
						thisObj.updateFactorValue('usaListFctr', {factorCd : 'USALST'})
					}else{
						thisObj.usaListFctr = 1
					}
				},
				riskFctr: function(){
					if(thisObj.satgrp.cd === null || thisObj.satgrp.cd === '' || thisObj.riskBase === null){
						thisObj.riskFctr = 1
					}else{
						thisObj.updateFactorValue('riskFctr', {
							factorCd : 'RSKACNT',
							grpFactorDiv:Math.round(Number(thisObj.riskBase)*100)
						})
					}
				},
				kamFctr: function(){
					if(thisObj.satgrp.cd === null || thisObj.satgrp.cd === ''
						|| thisObj.kamYn.cd === null || thisObj.kamYn.cd === ''){
						thisObj.kamFctr = 1
					}else if(thisObj.kamYn.cd === 'Y'){
						thisObj.updateFactorValue('kamFctr', {factorCd : 'KAM'})
					}else{
						thisObj.kamFctr = 1
					}
				},
				consoFinstatFctr : function() {
					if(thisObj.satgrp.cd === null || thisObj.satgrp.cd === ''
						|| thisObj.consoFinstatYn.cd === null || thisObj.consoFinstatYn.cd === ''){
						thisObj.consoFinstatFctr = 1
					}else if(thisObj.consoFinstatYn.cd === 'Y'){
						thisObj.updateFactorValue('consoFinstatFctr', {factorCd : 'CONSO'})
					}else{
						thisObj.consoFinstatFctr = 1
					}
				},
				sbsidCnt: function(){
					if(thisObj.satgrp.cd === null || thisObj.satgrp.cd === ''
						|| thisObj.consoFinstatYn.cd === null || thisObj.consoFinstatYn.cd === ''){
						thisObj.sbsidCnt = null
					}else if(thisObj.consoFinstatYn.cd === 'N'){
						thisObj.sbsidCnt = 0
					}
				},
				sbsidCntFctr : function() {
					if(thisObj.satgrp.cd === null || thisObj.satgrp.cd === '' || thisObj.sbsidCnt === null){
						thisObj.sbsidCntFctr = 1
					}else{
						thisObj.updateFactorValue('sbsidCntFctr', {
							factorCd : 'SBSID',
							grpFactorDiv : thisObj.sbsidCnt
						})
					}
				},
				currAdtopinFctr: function() {
					if(thisObj.satgrp.cd === null || thisObj.satgrp.cd === ''
						|| thisObj.currAdtopin.cd === null || thisObj.currAdtopin.cd === ''){
						thisObj.currAdtopinFctr = 1
					}else if(thisObj.currAdtopin.cd === 'Y'){
						thisObj.updateFactorValue('currAdtopinFctr', {factorCd : 'ADTOPIN'})
					}else{
						thisObj.currAdtopinFctr = 1
					}
				},
				currConsoLossFctr: function() {
					if(thisObj.satgrp.cd === null || thisObj.satgrp.cd === ''
						|| thisObj.currConsoLossYn.cd === null || thisObj.currConsoLossYn.cd === ''){
						thisObj.currConsoLossFctr = 1
					}else if(thisObj.currConsoLossYn.cd === 'Y'){
						thisObj.updateFactorValue('currConsoLossFctr', {factorCd : 'LOSS'})
					}else{
						thisObj.currConsoLossFctr = 1
					}
				},
				priorAdtopinChgFctr: function() {
					if(thisObj.satgrp.cd === null || thisObj.satgrp.cd === ''
						|| thisObj.priorAdtopinChgYn.cd === null || thisObj.priorAdtopinChgYn.cd === ''){
						thisObj.priorAdtopinChgFctr = 1
					}else if(thisObj.priorAdtopinChgYn.cd === 'Y'){
						thisObj.updateFactorValue('priorAdtopinChgFctr', {factorCd : 'PRIORADTOPINCHG'})
					}else{
						thisObj.priorAdtopinChgFctr = 1
					}
				},
				priorLossFctr: function() {
					if(thisObj.satgrp.cd === null || thisObj.satgrp.cd === ''
						|| thisObj.priorLossYn.cd === null || thisObj.priorLossYn.cd === ''){
						thisObj.priorLossFctr = 1
					}else if(thisObj.priorLossYn.cd === 'Y'){
						thisObj.updateFactorValue('priorLossFctr', {factorCd : 'PRIORLOSS'})
					}else{
						thisObj.priorLossFctr = 1
					}
				},
				baseWkmnsp: function(){
					if(thisObj.satgrp.cd === null || thisObj.satgrp.cd === ''){
						thisObj.baseWkmnsp = 1
						thisObj.maxRatio = 1.5
					}else{
						thisObj.getGrpPrfl().done(function(data){
							thisObj.baseWkmnsp = data.baseWkmnsp;
							thisObj.priorBaseWkmnsp = data.baseWkmnsp;
							if(thisObj.fstAdtrBaseWkmnspYn) thisObj.fstAdtrWkmnsp = data.baseWkmnsp;
							if(thisObj.priorAdtrBaseWkmnspYn) thisObj.priorAdtrWkmnsp = data.baseWkmnsp;
							thisObj.maxRatio = data.maxRatio
							
							/*thisObj.baseWkmnsp = wkmnsp;
							thisObj.priorBaseWkmnsp = wkmnsp;
							if(thisObj.fstAdtrBaseWkmnspYn) thisObj.fstAdtrWkmnsp = wkmnsp;
							if(thisObj.priorAdtrBaseWkmnspYn) thisObj.priorAdtrWkmnsp = wkmnsp;*/
						})
					}
				},
				intrAdtTm: function(){
					if(thisObj.holdingsDv.cd === null || thisObj.holdingsDv.cd === '' || thisObj.intrAdtYn.cd !== 'Y'){
						thisObj.intrAdtTm = 0
					}else{
						let intrRate = {
							"NORMAL":[1, 1.300, 1.350, 1.400, 1.400],
							"FIN_HLD":[1, 1.150, 1.175, 1.200, 1.200],
							"NFIN_HLD":[1, 1.150, 1.175, 1.200, 1.200]
						}
						let idx = Number(thisObj.intrAdtYcnt.cd) - 1;
						let rate = intrRate[thisObj.holdingsDv.cd][idx < 0 ? 0 : idx]
						thisObj.intrAdtTm = Helper.round(thisObj.priorAdtTm / rate * (rate-1), 2)
					}
				}
			}
			
			for(let i in arguments){
				if(typeof arguments[i] === 'string') {
					func[arguments[i]]();
				}
			}
			
			setTimeout(function(){thisObj.onMsg = true;}, 100)
		},
	}
}

//################################################################################
// 표준감사시간 계산 전용 기본 정보
//################################################################################
const satCalcInfoMixin = {
	data: {
		calcOnlyYn:'Y',			// 계산하기 전용 여부
		
		compNm: null,			// 회사명
		bizFrdt: null,			// 사업연도 시작
		bizTodt: null,			// 사업연도 종료
		rcrdMgr1: {emplno:null, kornm:null, gradnm:null},			// Record Manager1
		rcrdMgr2: {emplno:null, kornm:null, gradnm:null},			// Record Manager2
		rcrdMgr3: {emplno:null, kornm:null, gradnm:null},			// Record Manager3
	}
}

//################################################################################
// 프로젝트 기본 정보
//################################################################################
const projectInfoMixin = {
	data: {
		calcOnlyYn:'N',			// 계산하기 전용 여부
		prflId:null,			// 프로파일 id
		
		prjtCd:null,			// 프로젝트 코드 00000-00-000
		prjtCd1:null, 			// 프로젝트 코드1(Client Code) 00000
		prjtCd2:null, 			// 프로젝트 코드2(Service Code) 00
		prjtCd3:null, 			// 프로젝트 코드3(순번) 000
		prjtNm:null,			// 프로젝트 명: Client명/용역명
		clntNm:null,			// Client명
		shrtNm:null,			// 용역명
		cntrtFee:null,			// 계약 보수 (원)
		chargPtr: {emplno:null, kornm:null, gradnm:null},			// 프로젝트 담당 파트너
		chargMgr: {emplno:null, kornm:null, gradnm:null},			// 프로젝트 담당 매니저
		rcrdMgr1: {emplno:null, kornm:null, gradnm:null},			// Record Manager1
		rcrdMgr2: {emplno:null, kornm:null, gradnm:null},			// Record Manager2
		rcrdMgr3: {emplno:null, kornm:null, gradnm:null},			// Record Manager3
		
		prjtFrdt:null,			// 프로젝트 시작일 2019-01-01
		prjtTodt:null,			// 프로젝트 시작일 2019-12-31
		bizFrdt: null,			// 사업연도 시작
		bizTodt: null,			// 사업연도 종료
		
		desigAdtYn: {cd:null, nm:null},		// 지정감사 여부
		satTrgtYn: {cd:null, nm:null},		// 표준감사 대상 여부
	},
	computed: {
		dspChargPtr: 	function(){return this.chargPtr.emplno?this.chargPtr.kornm + "(" + this.chargPtr.emplno + ") " + this.chargPtr.gradnm:"";},
		dspChargMgr: 	function(){return this.chargMgr.emplno?this.chargMgr.kornm + "(" + this.chargMgr.emplno + ") " + this.chargMgr.gradnm:"";},
	}
}

//################################################################################
// 그룹 정보 영역
//################################################################################
const groupInfoMixin = {
	data: {
		indusDv: {cd:null, nm:null},		// 표준산업 분류
		listDv: {cd:null, nm:null},			// 상장 구분
		indivAsset: null,					// 개별 자산
		bizRprtYn: {cd:null, nm:null}, 		// 사업보고서 제출대상 여부
		consoAsset: null,					// 연결 자산
		satgrp1ExptYn: {cd:null, nm:null},	// 표준감사그룹1 예외사항 여부
		consoSales: null,					// 연결 매출
		consoAccntReceiv: null,				// 연결 매출 채권
		rprtScdlDt: null,					// 보고서 발행 예정일 
		consoInvnt: null,					// 연결 재고자산
		satgrp: {cd:null, nm:null},			// 표준감사그룹 코드
		firstBizDt: null,					// 최초적용 사업연도
		contiAdtCnt: {cd:null, nm:null}, 	// 삼일회계법인 연속감사 횟수
		intrAdtYcnt: {cd:null, nm:null},	// 내부회계감사 시행연차
		revwCnt: null,						// 검토 횟수
		sameAdtrSbsidYn: false,				// 동일감사인이 감사하는 종속회사 또는 관계회사 존재 여부
		intrTranAssetSales: null,			// 연결 조정시 제거된 내부거래(매출+자산) 
		sbsidAssetWithIntrTran: null,		// 내부거래제거전 자회사 자산 
		relatCompAsset: null,				// 관계회사의 자산금액 
		sbsidSalesWithIntrTran: null,		// 내부거래제거전 자회사 매출
		
		satgrpList: [],						// 연결감사 그룹 Options
	},
	computed: {
		compSize:function(){return (Number(this.consoSales) + Number(this.consoAsset))/2;},		// 기업 규모
	},
	methods: {
		
	},
}

//################################################################################
// 가감 계수  영역
//################################################################################
const factorMixin = {
	data: {
		ifrsYn: {cd: null, nm: null}, 				ifrsFctr: 1,				// IFRS 여부
		holdingsDv: {cd: null, nm: null}, 			holdingsFctr: 1,			// 지주사 분류
		firstAdtYn: {cd: null, nm: null},			firstAdtFctr: 1,			// 초도 감사 여부
		usaListYn: {cd: null, nm: null},			usaListFctr: 1,				// 미국 상장 여부
													riskFctr: 1,				// 위험계정 비중
		kamYn: {cd: null, nm: null},				kamFctr: 1,					// KAM
		consoFinstatYn: {cd: null, nm: null},		consoFinstatFctr: 1,		// 연결 작성 여부
		sbsidCnt: null,								sbsidCntFctr: 1,				// 자회사 수
		currAdtopin: {cd: null, nm: null},			currAdtopinFctr: 1,			// 당기 예상 감사의견 비적정 여부
		currConsoLossYn: {cd: null, nm: null},		currConsoLossFctr: 1,		// 당기 연결 당기순손실 예상 여부
		priorAdtopinChgYn: {cd: null, nm: null},	priorAdtopinChgFctr: 1,		// 전기 의견변형 여부
		priorLossYn: {cd: null, nm: null},			priorLossFctr: 1,			// 전기손실여부
													etcFctr: 1,					// 분기검토조정계수 등 기타
									
		firstAdtFctrRange:{			// 그룹 1이고, 초도 감사이면 직접 입력 --> 범위 내 계수 직접 입력 
			min: null,
			max: null,
			description: null
		},
		priorAdtopinChgFctrRange:{	// 전기 의견변형 Y이면 --> 범위 내 계수 직접 입력
			min: null,
			max: null,
			description: null
		},
		priorLossFctrRange:{		// 전기손실여부 Y이면 --> 범위 내 계수 직접 입력
			min: null,
			max: null,
			description: null
		},
	},
	computed: {
		riskBase: function(){// 위험계정비중 = (연결매출채권 + 연결재고자산) / 연결 자산
			return this.consoAsset === null || this.consoAsset === 0 ? 0: (this.consoAccntReceiv + this.consoInvnt) / this.consoAsset;
		},
		factorVal: function(){// 모든 계수 값의 곱
			return this.ifrsFctr * this.firstAdtFctr * this.riskFctr * this.consoFinstatFctr * this.currAdtopinFctr
				 * this.priorAdtopinChgFctr * this.etcFctr * this.holdingsFctr * this.usaListFctr * this.kamFctr
				 * this.sbsidCntFctr * this.currConsoLossFctr * this.priorLossFctr
		}
	},
}

//################################################################################
// 상하한(기준숙련도 기준) 영역
//################################################################################
const minMaxMixin = {
	data: {
		minMaxYn: {cd:null, nm:null},			// 상하한 적용 여부
		minMaxReason: {cd:null, nm:null},		// 상하한 미적용시 근거 - 직접 기술
		minMaxReasonDscrt: null,				// 상하한 미적용시 근거 - 직접 기술
		fstAdtTm: null, 						// 최초 적용 상한 - 최초 적용 직전 감사시간
		fstAdtrBaseWkmnspYn: false,				// 최초 적용 상한 - 기준숙련도 사용여부
		fstAdtrWkmnsp: null,					// 최초 적용 상한 - 감사팀의 숙련도
		fstAdtTmReason: {cd:null, nm:null},		// 최초 적용 상한 - 감사시간 수정시 근거 (일치/초도감사/직접서술)
		fstAdtTmReasonDscrt: null,				// 최초 적용 상한 - 감사시간 수정시 근거 직접 서술
		fstWkmnspReason: {cd:null, nm:null},	// 최초 적용 상한 - 숙련도 근거 (기준숙련도 적용(Big 4) / 실제숙련도 / 외감실시내용을 기준으로 간단계산 / 감사계약시 합의내용 / 직접서술)
		fstWkmnspReasonDscrt: null,				// 최초 적용 상한 - 숙련도 근거 직접 서술
		priorAdtr: {cd:null, nm:null},			// 전기기준 상하한 - 전기감사인
		priorBaseWkmnsp: null,					// 전기기준 상하한 - 전기 기준 숙련도
		priorAdtTm: null,						// 전기기준 상하한 - 전기 감사 시간
		priorAdtrBaseWkmnspYn: null, 			// 전기기준 상하한 - 기준숙련도 사용여부
		priorAdtrWkmnsp: null,					// 전기기준 상하한 - 전기 감사팀 숙련도
		priorAdtTmReason: {cd:null, nm:null},	// 전기기준 상하한 - 감사시간 근거 (일치/초도감사/직접서술)
		priorAdtTmReasonDscrt: null,			// 전기기준 상하한 - 감사시간 수정시 근거 직접 서술
		priorWkmnspReason: {cd:null, nm:null},	// 전기기준 상하한 - 숙련도 근거 (기준숙련도 적용(Big 4) / 실제숙련도 / 외감실시내용을 기준으로 간단계산 / 감사계약시 합의내용 / 직접서술)
		priorWkmnspReasonDscrt: null,			// 전기기준 상하한 - 숙련도 근거 직접 서술
		intrAdtYn: {cd:null, nm:null},			// 내부감사시간 - 전기 내부회계감사시간 포함 여부
		intrAdtTm: null,						// 내부감사시간 - 전기 내부회계감사시간
		maxRatio: null,							// 상한선
	},
	computed: {
		fstBizDt:	function(){		// 최초적용 직전 사업연도
			if(!this.firstBizDt) return null
			
			if(this.bizFrdt >= '2022-01') return '2019-12';
			
			let dt = new Date(this.firstBizDt);
			dt.setFullYear(dt.getFullYear() - 1);
			return dt.toISOString().substr(0, 7)
		},
		fstBizDt3year: function(){	// 최초적용 직전 사업연도 + 3년
			if(!this.fstBizDt) return null
			let splitDt = this.fstBizDt.split("-");
			return '' + (Number(splitDt[0]) + 3) + '-' + splitDt[1]; 
		},
		fstMaxTm: function(){
			if(!this.firstBizDt && !this.bizTodt) return null
		
			let fDt = new Date(this.firstBizDt); 	// 최초적용 사업연도
			let bDt = new Date(this.bizTodt);		// 현재 사업연도
			
			fDt.setFullYear(fDt.getFullYear() + 3)	// 최초 + 3년
			
			let wkmnsp = 1;
			
			if(this.fstAdtrBaseWkmnspYn){
				wkmnsp = this.priorBaseWkmnsp;
			}else{
				wkmnsp = this.fstAdtrWkmnsp;
			}
			
			if(fDt >= bDt){
				return Number(this.priorBaseWkmnsp)==0?0:this.fstAdtTm * 2 * wkmnsp / this.priorBaseWkmnsp;			// 3년이상시 2배를 초화할 수 없다.
			}else{
				return 0
			}
		},
		minTm: function(){
			return (this.priorAdtTm - this.intrAdtTm) * this.priorAdtrWkmnsp / this.priorBaseWkmnsp
		},
		maxTm: function(){
			let maxRatio = (this.bizTodt!==null && this.bizTodt === this.firstBizDt) ? Number(this.maxRatio) : 1.5
			
			if(this.priorAdtTm === null || this.priorAdtTm === 0){
				return this.fstMaxTm
			}else if(this.fstMaxTm === null || this.fstMaxTm === 0){
				return this.minTm * maxRatio
			}else{
				return this.minTm * maxRatio > this.fstMaxTm ? this.fstMaxTm : this.minTm * maxRatio
			}
		},
		priorBizDt: function(){
			if(!this.bizTodt) return null
			return this.shiftDate(this.bizTodt, "-1Y").substr(0, 7);
		}
	}
}


//################################################################################
// 표준감사시간 산출 영역
//################################################################################
const satCalculateMixin = {
	data: {
		intplSat: null,				// 표준감사시간표(보간법)
		calResult: null,			// 산식 결과
		yearRate: null, 			// 적용요율
		calAdtTm:null,				// 산출된 감사시간(상하한 적용 후)(a) 
		intrAdtFctr: null,			// 내부회계감사시간 계수(b)
		baseWkmnsp:1,				// 기준 숙련도
		etDfnSat:null,				// 한공회 표준감사시간(기본산식 외 요소를 고려한 최종 표준감사시간)
		etTrgtAdtTm: null,			// 감사계약시 합의 시간 (표준감사시간 대상)	
	},
	computed: {
		calRateSat: function(){return Number(this.calResult) * Number(this.yearRate)},	// 산식결과 * 적용요율
		calSat: function(){return Number(this.calAdtTm) * Number(this.intrAdtFctr)}, //숙련도 반영전 표준감사시간(a*b) - (구)계산식에 의해 산출된 기준 표준감사시간(숙련도 반영 전)
		wkmnspSat: 	function(){return Number(this.etDfnSat) * Number(this.baseWkmnsp);}
	},
}


//################################################################################
// 표준감사시간외 예산입력 & 예산 배분
//################################################################################
const bdgtMixin = {
	data: {
		etcBdgtTm: null,	// 표준감사시간외 예산 시간
		raBdgtTm: null,		// RA 배부 예산 시간	
		flcmBdgtTm: null,	// Fulcrum 배부 예산 시간
	},
	computed:{
		totPrjtBdgt: function(){return Number(this.etTrgtAdtTm) + Number(this.etcBdgtTm);}, // 감사계약시 합의 시간
		remainBdgt: function(){return Number(this.totPrjtBdgt) - Number(this.raBdgtTm) - Number(this.flcmBdgtTm);}, // 잔여 예산 시간
	}
}



//################################################################################
// 유효성 검사
//################################################################################
const validationMixin = {
	data: {
		_isCalcOnly: null
	},
	methods:{
		_checkSelect: function(id){
			if(this[id] === undefined || this[id] === null || this[id].cd === null || this[id].cd === '') return false;
			return true;
		},
		_checkField: function(id){
			if(this[id] === undefined || this[id] == null || this[id] === '') return false;
			return true;
		},
		validCheckProject: function(){
			this._isCalcOnly = false;
			
			let msg = []
			if(!this._checkField('prjtCd')){msg.push("[프로젝트]를 선택하세요.")}
			if(this.cntrtFee == null){
				msg.push("[계약 보수]를 입력하세요.")
			}else if(this.cntrtFee <= 0){
				msg.push("[계약 보수]에는 0 이상의 숫자를 입력해야 합니다.");
			}
//			if(!this._checkSelect('desigAdtYn')){msg.push('[지정감사 여부]를 선택하세요.');}
			if(!this._checkSelect('satTrgtYn')){msg.push('[표준감사시간 대상 여부]를 선택하세요');}
			
			if(!this._checkField('bizFrdt') || !this._checkField('bizTodt')){
				if(!this._checkField('bizFrdt')) msg.push('[사업연도 시작월]을 입력하세요.');
				if(!this._checkField('bizTodt')) msg.push('[사업연도 종료월]을 입력하세요.');
			}else{
				if(this.bizFrdt < "2018-01") msg.push('[사업연도 시작월] 2018-01 이후 월만 가능합니다.');
				if(this.bizFrdt > this.bizTodt) msg.push('[사업연도 시작월]은 [사업연도 종료월]보다 작아야합니다.');
			}
			
			if(!this._checkField('prjtFrdt') || !this._checkField('prjtTodt')){
				if(!this._checkField('prjtFrdt')) msg.push('[Project 시작일]을 입력하세요.');
				if(!this._checkField('prjtTodt')) msg.push('[Project 종료일]을 입력하세요.');
			}else{
				if(this.prjtFrdt < "2018-01-01") {
					msg.push('[Project 시작일] 2018-01-01 이후날짜만 가능합니다.');
				}
				if(this.prjtFrdt > this.prjtTodt) {
					msg.push('[Project 시작일]은 [Project 종료일]보다 작아야합니다.');
				}else{
					let tmpDt = this.shiftDate(this.prjtFrdt, '+420D');
					if(this.prjtTodt > tmpDt){
						msg.push('[Project 종료일]은 '+ tmpDt+ '까지 선택가능합니다.');
					}
				}
			}
			
			// 표준감사시간 대상일 경우 
			if(this.satTrgtYn.cd === "Y"){
				msg = msg.concat(this._validCheckSat());		// 표준 감사시간 계산을 위한 준비 
				msg = msg.concat(this._validCheckCalcResult());	// 표준 감사시간 계산 결과				
			}
			
			
			return msg;
		},
		validCheckCalcOnly: function(){
			this._isCalcOnly = true;
			
			let msg = [];
			if(!this._checkField('compNm')){msg.push('[회사명]을 입력하세요.');}
			if(!this._checkField('bizFrdt') || !this._checkField('bizTodt')){
				if(!this._checkField('bizFrdt')) msg.push('[사업연도 시작월]을 입력하세요.');
				if(!this._checkField('bizTodt')) msg.push('[사업연도 종료월]을 입력하세요.');
			}else{
				if(this.bizFrdt < "2018-01") msg.push('[사업연도 시작월] 2018-01 이후 월만 가능합니다.');
				if(this.bizFrdt > this.bizTodt) msg.push('[사업연도 시작월]은 [사업연도 종료월]보다 작아야합니다.');
			}
			
			msg = msg.concat(this._validCheckSat());
			
			return msg;
		},
		_validCheckSat: function(){
			let msg = [];
			//============
			// 그룹 정보
			//============
			if(!this._checkSelect('indusDv')){msg.push('[표준산업 분류]를 선택하세요.')}
			if(!this._checkSelect('listDv')){msg.push('[상장 구분]을 선택하세요.')}
			if(!this._checkField('indivAsset')){
				msg.push('[개별 자산]을 입력하세요.');
			}else if(this.indivAsset < 0){
				msg.push('[개별 자산]에는 0 이상의 숫자를 입력해야 합니다.');
			}
			if(!this._checkSelect('bizRprtYn')){msg.push('[사업보고서 제출 대상]을 선택하세요.')}
			if(!this._checkField('consoAsset')){
				msg.push('[연결 자산]을 입력하세요.');
			}else if(this.consoAsset < 1){
				msg.push('[연결 자산]에는 1 이상의 숫자를 입력해야 합니다.');
			}
			if(!this._checkSelect('satgrp1ExptYn')){msg.push('[그룹1의 예외사항 여부]를 선택하세요.')}
			if(!this._checkField('consoSales')){
				msg.push('[연결 매출]을 입력하세요.');
			}else if(this.consoSales < 0){
				msg.push('[연결 매출]에는 0 이상의 숫자를 입력해야 합니다.');
			}
			if(!this._checkField('consoAccntReceiv')){
				msg.push('[연결 매출채권]을 입력하세요.');
			}else if(this.consoAccntReceiv < 0){
				msg.push('[연결 매출채권]에는 0 이상의 숫자를 입력해야 합니다.');
			}
			
			if(!this._isCalcOnly && !this._checkField('rprtScdlDt')){msg.push('[보고서 발행 예정일]을 입력하세요.')}
			
			if(!this._checkField('consoInvnt')){
				msg.push('[연결 재고자산]을 입력하세요.');
			}else if(this.consoInvnt < 0){
				msg.push('[연결 재고자산]에는 0 이상의 숫자를 입력해야 합니다.');
			}
			if(!this._checkSelect('satgrp')){msg.push('[표준 감사 시간 그룹 정보]를 선택하세요.')}
			if(!this._checkField('firstBizDt')){
				msg.push('[최초적용 사업연도]를 입력하세요.')
			}else if(this.firstBizDt < '2019-12'){
				msg.push("[그룹정보][최초적용 사업연도]는 2019-12월 이후부터 입력 가능합니다.")
			}
			if(!this._checkSelect('contiAdtCnt')){msg.push('[삼일회계법인 연속감사 횟수]를 선택하세요.')}
			
			//============
			// 가감계수
			//============
			if(!this._checkSelect('ifrsYn')){msg.push('[IFRS 여부]를 선택하세요.')}
			if(!this._checkSelect('holdingsDv')){msg.push('[지주사 분류]를 선택하세요.')}
			if(!this._checkSelect('firstAdtYn')){msg.push('[초도 감사 여부]를 선택하세요.')}
			if(this.satgrp.cd==="SATGRP01" && this.firstAdtYn.cd === "Y" && this.firstAdtFctrRange){
				if(!(this.firstAdtFctrRange.min <= this.firstAdtFctr && this.firstAdtFctr  <= this.firstAdtFctrRange.max))
					msg.push("[초도 감사 계수]에는 "+this.firstAdtFctrRange.min+" ~ "+this.firstAdtFctrRange.max+" 사이값을 입력하세요.")
			}
			if(!this._checkSelect('usaListYn')){msg.push('[미국 상장 여부]를 선택하세요.')}
			if(!this._checkSelect('kamYn')){msg.push('[핵심 감사 사항 여부]를 선택하세요.')}
			if(!this._checkSelect('consoFinstatYn')){msg.push('[연결 작성 여부]를 선택하세요.')}
			if(!this._checkField('sbsidCnt')){
				msg.push('[자회사 수]을 입력하세요.');
			}else if(this.sbsidCnt < 0){
				msg.push('[자회사 수]에는 0 이상의 숫자를 입력해야 합니다.');
			}
			if(!this._checkSelect('currAdtopin')){msg.push('[당기 예상 감사의견 비적정 여부]를 선택하세요.')}
			if(!this._checkSelect('currConsoLossYn')){msg.push('[당기 연결 당기순손실 예상 여부]를 선택하세요.')}
			if(!this._checkSelect('priorAdtopinChgYn')){msg.push('[전기 의견변형 여부]를 선택하세요.')}
			if(this.priorAdtopinChgYn.cd === "Y" && this.priorAdtopinChgFctrRange){
				if(!(this.priorAdtopinChgFctrRange.min <= this.priorAdtopinChgFctr && this.priorAdtopinChgFctr  <= this.priorAdtopinChgFctrRange.max))
					msg.push("[전기 의견변형 계수]에는 "+this.priorAdtopinChgFctrRange.min+" ~ "+this.priorAdtopinChgFctrRange.max+" 사이값을 입력하세요.")
			}
			if(!this._checkSelect('priorLossYn')){msg.push('[전기손실여부]를 선택하세요.')}
			if(this.priorLossYn.cd === "Y" && this.priorLossFctrRange){
				if(!(this.priorLossFctrRange.min <= this.priorLossFctr && this.priorLossFctr  <= this.priorLossFctrRange.max))
					msg.push("[전기손실 계수]에는 "+this.priorLossFctrRange.min+" ~ "+this.priorLossFctrRange.max+" 사이값을 입력하세요.")
			}
			if(!this._checkField('etcFctr')){
				msg.push("[분기검토조정계수 등 기타]를 입력하세요.")
			}else if(this.listDv.cd === "TOBE_LISTED" && this.etcFctr < 0.01){
				msg.push('[분기검토조정계수 등 기타]에는 0.01 이상의 숫자를 입력해야 합니다.');
			}
			
			//============
			// 상하한(기준숙련도 기준)
			//============
			if(!this._checkSelect('minMaxYn')){
				msg.push('[상하한 적용 여부]를 선택하세요.')
			}else if(this.minMaxYn.cd === "N"){
				if(!this._checkSelect('minMaxReason')){
					msg.push("[상하한 미적용시 근거]를 선택하세요.")
				}else if(this.minMaxReason.cd === "99" && this.minMaxReasonDscrt.trim() === ''){
					msg.push("[상하한 미적용 근거 서술]을 입력하세요.")
				}
			}else if(this.minMaxYn.cd === "Y"){
				if(!this._checkField('fstAdtTm')){
					msg.push("[최초적용 직전 감사시간]를 입력하세요.")
				}else if(this.fstAdtTm < 0){
					msg.push('[최초적용 직전 감사시간]에는 0 이상의 숫자를 입력해야 합니다.');
				}
				if(!this._checkField('fstAdtrWkmnsp')){
					msg.push("[최초적용 직전 감사팀의 숙련도]를 입력하세요.")
				}else if(this.fstAdtrWkmnsp < 0){
					msg.push('[최초적용 직전 감사팀의 숙련도]에는 0 이상의 숫자를 입력해야 합니다.');
				}
				
				if(!this._checkSelect('fstAdtTmReason')){
					msg.push("[최초적용 직전 사업연도 감사시간 근거]를 선택하세요.")
				}else if(this.fstAdtTmReason.cd === "99" && this.fstAdtTmReasonDscrt.trim() === ''){
					msg.push("[최초적용 직전 사업연도 감사시간 근거]을 입력하세요.")
				}
				
				if(!this._checkSelect('fstWkmnspReason')){
					msg.push("[최초적용 상한 - 숙련도 근거]를 선택하세요.")
				}else if(this.fstWkmnspReason.cd === "99" && this.fstWkmnspReasonDscrt.trim() === ''){
					msg.push("[최초적용 상한 - 숙련도 근거]을 입력하세요.")
				}
				
				if(!this._checkSelect('priorAdtr')){
					msg.push("[전기기준 상하한 - 전기 감사인]을 선택하세요.")
				}
				if(!this._checkField('priorBaseWkmnsp')){
					msg.push("[전기기준 상하한 - 전기 그룹 기준 숙련도]를 입력하세요.")
				}else if(this.priorBaseWkmnsp < 0){
					msg.push('[전기기준 상하한 - 전기 그룹 기준 숙련도]에는 0 이상의 숫자를 입력해야 합니다.');
				}
				
				if(!this._checkField('priorAdtTm')){
					msg.push("[전기기준 상하한 - 전기 감사 시간]를 입력하세요.")
				}else if(this.priorAdtTm < 0){
					msg.push('[전기기준 상하한 - 전기 감사 시간]에는 0 이상의 숫자를 입력해야 합니다.');
				}
				if(!this._checkField('priorAdtrWkmnsp')){
					msg.push("[전기기준 상하한 - 전기 감사팀의 숙련도]를 입력하세요.")
				}else if(this.priorAdtrWkmnsp < 0){
					msg.push('[전기기준 상하한 - 전기 감사팀의 숙련도]에는 0 이상의 숫자를 입력해야 합니다.');
				}
				
				
				if(!this._checkSelect('priorAdtTmReason')){
					msg.push("[전기기준 상하한 - 전기 감사시간 근거]를 선택하세요.")
				}else if(this.priorAdtTmReason.cd === "99" && this.priorAdtTmReasonDscrt.trim() === ''){
					msg.push("[전기기준 상하한 - 전기 감사시간 근거]을 입력하세요.")
				}
				
				if(!this._checkSelect('priorWkmnspReason')){
					msg.push("[전기기준 상하한 - 숙련도 근거]를 선택하세요.")
				}else if(this.priorWkmnspReason.cd === "99" && this.priorWkmnspReasonDscrt.trim() === ''){
					msg.push("[전기기준 상하한 - 숙련도 근거]을 입력하세요.")
				}
				if(!this._checkSelect('intrAdtYn')){
					msg.push("[내부회계감사 - 전기 내부회계감사시간포함여부]를 선택하세요.")
				}	
			}
			return msg;
		},
		_validCheckCalcResult: function(){  // 표준감사시간 산출
			let msg = []
			if(!this._checkField('etDfnSat')){
				msg.push("[한공회 표준감사시간(기본산식 외 요소를 고려한 최종 표준감사시간)]를 입력하세요.")
			}else if(this.etDfnSat < 0){
				msg.push('[한공회 표준감사시간(기본산식 외 요소를 고려한 최종 표준감사시간)]에는 0 이상의 숫자를 입력해야 합니다.');
			}
			/*if(!this._checkField('etTrgtAdtTm')){
				msg.push("[감사계약시 합의 시간 (표준감사시간 대상)]를 입력하세요.")
			}else if(this.etTrgtAdtTm < 0){
				msg.push('[감사계약시 합의 시간 (표준감사시간 대상)]에는 0 이상의 숫자를 입력해야 합니다.');
			}*/
			return msg;
		},
	}
}
