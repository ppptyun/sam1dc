//################################################################################
// 공통 정보
//################################################################################
const commonMixin = {
	data: {
		formDiv:'v2',
		canEdit: false,
		canDelete: false,
		stat: null,
		creby: null,		// 작성자 사번
		crebyNm: null,		// 작성자 이름
		moddt: null,		// 수정일
		modby: null,		// 수정자 사번
		modbyNm: null,		// 수정자 이름
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
		rcrdMgr1: {},			// Record Manager1
		rcrdMgr2: {},			// Record Manager2
		rcrdMgr3: {},			// Record Manager3
	}
}

//################################################################################
// 프로젝트 기정보
//################################################################################
const projectInfoMixin = {
	data: {
		calcOnlyYn:'N',			// 계산하기 전용 여부
		prjtCd: null,
		prjtCd:null,			// 프로젝트 코드 00000-00-000
		prflId:null,			// 프로파일 id
		prjtCd1:null, 			// 프로젝트 코드1(Client Code) 00000
		prjtCd2:null, 			// 프로젝트 코드2(Service Code) 00
		prjtCd3:null, 			// 프로젝트 코드3(순번) 000
		prjtNm:null,			// 프로젝트 명: Client명/용역명
		clntNm:null,			// Client명
		shrtNm:null,			// 용역명
		cntrtFee:null,			// 계약 보수 (원)
		chargPtr: {},			// 프로젝트 담당 파트너
		chargMgr: {},			// 프로젝트 담당 매니저
		rcrdMgr1: {},			// Record Manager1
		rcrdMgr2: {},			// Record Manager2
		rcrdMgr3: {},			// Record Manager3
		prjtFrdt:null,			// 프로젝트 시작일 2019-01-01
		prjtTodt:null,			// 프로젝트 시작일 2019-12-31
		bizFrdt: null,			// 사업연도 시작
		bizTodt: null,			// 사업연도 종료
		
		desigAdtYn: null, 	desigAdtYnNm: null, 	// 지정감사 여부
		satTrgtYn: null,	satTrgtYnNm: null,		// 표준감사 대상 여부
		
		subPrjt:[], 			// 합산 대상 프로젝트 정보
	},
	computed: {
		totCntrtFee: function(){
			return this.cntrtFee + subPrjt.map(function(data){return data.cntrtFee}).reduce(function(acc, curr){return acc + curr;}) 
		}
	}
}

//################################################################################
// 그룹 정보 영역
//################################################################################
const groupInfoMixin = {
	data: {
		indusCd: null, 			indusNm: null,			// 표준산업 분류
		listDvCd: null, 		listDvNm: null,			// 상장 구분
		indivAsset: null,								// 개별 자산
		bizRprtYn: null,		bizRprtYnNm: null,		// 사업보고서 제출대상 여부
		consoAsset: null,								// 연결 자산
		satgrp1ExptYn: null,	satgrp1ExptYnNm: null,	// 표준감사그룹1 예외사항 여부
		consoSales: null,								// 연결 매출
		consoAccntReceiv: null,							// 연결 매출 채권
		rprtScdlDt: null,								// 보고서 발행 예정일 
		consoInvnt: null,								// 연결 재고자산
		satgrpCd: null, 		satgrpNm:null,			// 표준감사그룹 코드
		firstBizDt: null,								// 최초적용 사업연도
		contiAdtCnt: null, 								// 삼일회계법인 연속감사 횟수
		intrAdtYcnt: null,								// 내부회계감사 시행연차
		revwCnt: null,									// 검토 횟수
		sameAdtrSbsidYn: null,							// 동일감사인이 감사하는 종속회사 또는 관계회사 존재 여부
		intrTranAssetSales: null,						// 연결 조정시 제거된 내부거래(매출+자산) 
		sbsidAssetWithIntrTran: null,					// 내부거래제거전 자회사 자산 
		relatCompAsset: null,							// 관계회사의 자산금액 
		sbsidSalesWithIntrTran: null,					// 내부거래제거전 자회사 매출
		
		satgrpList: [],									// 연결감사 그룹 Options
	},
	computed: {
		isExistSbsid: function(){return this.sameAdtrSbsidYn === 'Y'},
		compSize:function(){return (Number(this.consoSales) + Number(this.consoAsset))/2;},		// 기업 규모
	},
	methods: {
		setDefault: function(){
			if(this.satgrpCd === null || this.satgrpCd === ''){
				this.etcFctr = 1;
				this.setSelect('ifrsYn', '');
				this.setSelect('kamYn', '');
			}else{
				if(this.listDvCd ==='LISTED'){
					this.setSelect('ifrsYn', 'Y');
					this.setSelect('kamYn', 'Y');
					this.etcFctr = 1;
				}else if(this.listDvCd==='TOBE_LISTED'){
					this.setSelect('ifrsYn', 'N');
					this.setSelect('kamYn', 'N');
				}else{
					this.setSelect('ifrsYn', 'N');
					this.setSelect('kamYn', 'N');
					this.etcFctr = 1
				}
			}
		}
	},
	watch: {
		listDvCd: function(value){
			this.setDefault();
		},
		satgrpCd: function(value){
			this.setDefault();
			
			if(value === ''){
				this.setIfrsFctr(1);
				this.setHoldingsFctr(1);	
				this.setFirstAdtFctr(1);
				this.setUsaListFctr(1);
				this.setRiskFctr(1);
				this.setKamFctr(1);
				this.setConsoFinstatFctr(1);
				this.setSbsidFctr(1);
				this.setCurrAdtopinFctr(1);
				this.setCurrConsoLossFctr(1);
				this.setPriorAdtopinChgFctr(1);
				this.setPriorLossFctr(1);
			}else{
				this.setIfrsFctr();
				this.setHoldingsFctr();	
				this.setFirstAdtFctr();
				this.setUsaListFctr();
				this.setRiskFctr();
				this.setKamFctr();
				this.setConsoFinstatFctr();
				this.setSbsidFctr();
				this.setCurrAdtopinFctr();
				this.setCurrConsoLossFctr();
				this.setPriorAdtopinChgFctr();
				this.setPriorLossFctr();
			}
		},
	}
}

//################################################################################
// 가감 계수  영역
//################################################################################
const factorMixin = {
	data: {
		ifrsYn: null, 				ifrsFctr: 1,				// IFRS 여부
		holdingsDivCd: null, 		holdingsFctr: 1,			// 지주사 분류
		firstAdtYn: null,			firstAdtFctr: 1,			// 초도 감사 여부
		usaListYn: null,			usaListFctr: 1,				// 미국 상장 여부
									riskFctr: 1,				// 위험계정 비중
		kamYn: null,				kamFctr: 1,					// KAM
		consoFinstatYn: null,		consoFinstatFctr: 1,		// 연결 작성 여부
		sbsidCnt: null,				sbsidCntFctr: 1,			// 자회사 수
		currAdtopinCd: null,		currAdtopinFctr: 1,			// 당기 예상 감사의견 비적정 여부
		currConsoLossYn: null,		currConsoLossFctr: 1,		// 당기 연결 당기순손실 예상 여부
		priorAdtopinChgYn: null,	priorAdtopinChgFctr: 1,		// 전기 의견변형 여부
		priorLossYn: null,			priorLossFctr: 1,			// 전기손실여부
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
	methods : {
		updateFactorValue: function(name, param, initValue){	// DB로부터 계수 값을 가져와 업데이트 하기
			let thisObj = this;
			Helper.post("/calcSat/getFactorValue", param)
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
		setIfrsFctr : function(fctrVal) {	// IFRS 여부 계수
			if (fctrVal !== undefined) {this.ifrsFctr = fctrVal;return;}
			if (this.ifrsYn === '') {this.ifrsFctr = 1;return;}
			if (this.satgrpCd === null || this.satgrpCd === '') {
				this.setSelect('ifrsYn');
				this.ifrsFctr = 1;
				Helper.MessageBox("표준 감사 시간 그룹을 선택하세요.");
				return;
			}
			
			if (this.ifrsYn == 'Y') {
				let param = {
					prflId : this.prflId,
					satgrpCd : this.satgrpCd,
					factorCd : 'IFRS'
				}
				this.updateFactorValue('ifrsFctr', param)
			} else {
				this.ifrsFctr = 1
			}
		},
		setHoldingsFctr : function(fctrVal) {	// 지주사 분류 계수
			if (fctrVal !== undefined) {this.holdingsFctr = fctrVal;return;}
			if (this.holdingsDivCd === '') {this.holdingsFctr = 1;return;}
			if (this.satgrpCd === null || this.satgrpCd === '') {
				this.setSelect('holdingsDivCd');
				this.holdingsFctr = 1
				Helper.MessageBox("표준 감사 시간 그룹을 선택하세요.")
				return;
			}

			let param = {
				prflId : this.prflId,
				satgrpCd : this.satgrpCd
			}
			if (this.holdingsDivCd === 'FIN_HLD') {
				param.factorCd = 'FINHLD'
				this.updateFactorValue('holdingsFctr', param)
			} else if (this.holdingsDivCd === 'NFIN_HLD') {
				param.factorCd = 'NFINHLD'
				this.updateFactorValue('holdingsFctr', param)
			} else {
				this.holdingsFctr = 1
			}
		},
		setFirstAdtFctr : function(fctrVal) {	// 초도 감사 여부 계수
			console.log('fctrVal', fctrVal, 'firstAdtYn', this.firstAdtYn, 'satgrpCd', this.satgrpCd)
			if (fctrVal !== undefined) {this.firstAdtFctr = fctrVal;return;}
			if (this.firstAdtYn === '') {this.firstAdtFctr = 1;return;}
			if (this.satgrpCd === null || this.satgrpCd === '') {
				this.setSelect('firstAdtYn');
				this.firstAdtFctr = 1
				Helper.MessageBox("표준 감사 시간 그룹을 선택하세요.")
				return;
			}

			if (this.firstAdtYn == 'Y') {
				let param = {
					prflId : this.prflId,
					satgrpCd : this.satgrpCd,
					factorCd : 'FIRSTADT'
				}
				this.updateFactorValue('firstAdtFctr', param, 1.05)
			} else {
				this.firstAdtFctr = 1
			}
		},
		setUsaListFctr : function(fctrVal) {	// 미국 상장 여부 계수
			if (fctrVal !== undefined) {this.usaListFctr = fctrVal;return;}
			if (this.usaListYn === '') {this.usaListFctr = 1;return;}
			if (this.satgrpCd === null || this.satgrpCd === '') {
				this.setSelect('usaListYn');
				this.usaListFctr = 1
				Helper.MessageBox("표준 감사 시간 그룹을 선택하세요.")
				return;
			}

			if (this.usaListYn == 'Y') {
				let param = {
					prflId : this.prflId,
					satgrpCd : this.satgrpCd,
					factorCd : 'USALST'
				}
				this.updateFactorValue('usaListFctr', param)
			} else {
				this.usaListFctr = 1
			}
		},
		setRiskFctr: function(fctrVal){
			if (fctrVal !== undefined) {this.riskFctr = fctrVal;return;}
			if (this.riskBase === null) {this.riskFctr = 1;return;}
			if (this.satgrpCd === null || this.satgrpCd === '') {
				this.riskFctr = 1
				return;
			}
			
			let param = {
				prflId : this.prflId,
				satgrpCd : this.satgrpCd,
				factorCd : 'RSKACNT',
				grpFactorDiv:Math.round(this.riskBase*100)
			}
			this.updateFactorValue('riskFctr', param)
		},
		setKamFctr: function(fctrVal){
			if (fctrVal !== undefined) {this.kamFctr = fctrVal;return;}
			if (this.kamYn === '') {this.kamFctr = 1;return;}
			if (this.satgrpCd === null || this.satgrpCd === '') {
				this.setSelect('kamYn');
				this.kamFctr = 1
				Helper.MessageBox("표준 감사 시간 그룹을 선택하세요.")
				return;
			}
			
			if (this.kamYn == 'Y') {
				let param = {
					prflId : this.prflId,
					satgrpCd : this.satgrpCd,
					factorCd : 'KAM'
				}
				this.updateFactorValue('kamFctr', param)
			} else {
				this.kamFctr = 1
			}
		},
		setConsoFinstatFctr : function(fctrVal) {	
			if (fctrVal !== undefined) {this.consoFinstatFctr = fctrVal;return;}
			if (this.consoFinstatYn === '') {this.consoFinstatFctr = 1;return;}
			if (this.satgrpCd === null || this.satgrpCd === '') {
				this.setSelect('consoFinstatYn');
				this.consoFinstatFctr = 1
				Helper.MessageBox("표준 감사 시간 그룹을 선택하세요.")
				return;
			}

			if (this.consoFinstatYn == 'Y') {
				let param = {
					prflId : this.prflId,
					satgrpCd : this.satgrpCd,
					factorCd : 'CONSO'
				}
				this.updateFactorValue('consoFinstatFctr', param)
			} else {
				this.consoFinstatFctr = 1
			}
		},
		setSbsidFctr : function(fctrVal) {
			if (fctrVal !== undefined) {this.sbsidCntFctr = fctrVal;return;}
			if (this.sbsidCnt === null){this.sbsidCntFctr = 1;return;}
			if (this.satgrpCd === null || this.satgrpCd === '') {
				this.setNumber('sbsidCnt', null);
				this.sbsidCntFctr = 1
				Helper.MessageBox("표준 감사 시간 그룹을 선택하세요.")
				return;
			}

			let param = {
				prflId : this.prflId,
				satgrpCd : this.satgrpCd,
				factorCd : 'SBSID',
				grpFactorDiv : this.sbsidCnt
			}
			this.updateFactorValue('sbsidCntFctr', param)
		},
		setCurrAdtopinFctr: function(fctrVal) {
			if (fctrVal !== undefined) {this.currAdtopinFctr = fctrVal;return;}
			if (this.currAdtopinCd === '') {this.currAdtopinFctr = 1;return;}
			if (this.satgrpCd === null || this.satgrpCd === '') {
				this.setSelect('currAdtopinCd');
				this.currAdtopinFctr = 1
				Helper.MessageBox("표준 감사 시간 그룹을 선택하세요.")
				return;
			}

			if (this.currAdtopinCd == 'Y') {
				let param = {
					prflId : this.prflId,
					satgrpCd : this.satgrpCd,
					factorCd : 'ADTOPIN'
				}
				this.updateFactorValue('currAdtopinFctr', param)
			} else {
				this.currAdtopinFctr = 1
			}
		},
		setCurrConsoLossFctr: function(fctrVal) {
			if (fctrVal !== undefined) {this.currConsoLossFctr = fctrVal;return;}
			if (this.currConsoLossYn === '') {this.currConsoLossFctr = 1;return;}
			if (this.satgrpCd === null || this.satgrpCd === '') {
				this.setSelect('currConsoLossYn');
				this.currConsoLossFctr = 1
				Helper.MessageBox("표준 감사 시간 그룹을 선택하세요.")
				return;
			}

			if (this.currConsoLossYn == 'Y') {
				let param = {
					prflId : this.prflId,
					satgrpCd : this.satgrpCd,
					factorCd : 'LOSS'
				}
				this.updateFactorValue('currConsoLossFctr', param)
			} else {
				this.currConsoLossFctr = 1
			}
			
		},
		setPriorAdtopinChgFctr: function(fctrVal) {
			if (fctrVal !== undefined) {this.priorAdtopinChgFctr = fctrVal;return;}
			if (this.priorAdtopinChgYn === '') {this.priorAdtopinChgFctr = 1;return;}
			if (this.satgrpCd === null || this.satgrpCd === '') {
				this.setSelect('priorAdtopinChgYn');
				this.priorAdtopinChgFctr = 1
				Helper.MessageBox("표준 감사 시간 그룹을 선택하세요.")
				return;
			}

			if (this.priorAdtopinChgYn == 'Y') {
				let param = {
					prflId : this.prflId,
					satgrpCd : this.satgrpCd,
					factorCd : 'PRIORADTOPINCHG'
				}
				this.updateFactorValue('priorAdtopinChgFctr', param)
			} else {
				this.priorAdtopinChgFctr = 1
			}
		},
		setPriorLossFctr: function(fctrVal) {
			if (fctrVal !== undefined) {this.priorLossFctr = fctrVal;return;}
			if (this.priorLossYn === '') {this.priorLossFctr = 1;return;}
			if (this.satgrpCd === null || this.satgrpCd === '') {
				this.setSelect('priorLossYn');
				this.priorLossFctr = 1
				Helper.MessageBox("표준 감사 시간 그룹을 선택하세요.")
				return;
			}

			if (this.priorLossYn == 'Y') {
				let param = {
					prflId : this.prflId,
					satgrpCd : this.satgrpCd,
					factorCd : 'PRIORLOSS'
				}
				this.updateFactorValue('priorLossFctr', param)
			} else {
				this.priorLossFctr = 1
			}
		}
	},
	watch: {
		ifrsYn: function(value){this.setIfrsFctr()},
		holdingsDivCd: function(value){this.setHoldingsFctr()},
		firstAdtYn: function(value){this.setFirstAdtFctr()},
		usaListYn: function(value){this.setUsaListFctr()},
		riskBase: function(value){this.setRiskFctr()},
		kamYn: function(value){this.setKamFctr()},
		consoFinstatYn: function(value){this.setConsoFinstatFctr()},
		sbsidCnt: function(value){this.setSbsidFctr()},
		currAdtopinCd: function(value){this.setCurrAdtopinFctr()},
		currConsoLossYn: function(value){this.setCurrConsoLossFctr()},
		priorAdtopinChgYn: function(value){this.setPriorAdtopinChgFctr()},
		priorLossYn: function(value){this.setPriorLossFctr()},
	}
}

//################################################################################
// 상하한(기준숙련도 기준) 영역
//################################################################################
const minMaxMixin = {
	data: {
		minMaxYn: null,				// 상하한 적용 여부
		minMaxReason: null,			// 상하한 미적용시 근거
		minMaxReason: null,			// 상하한 미적용시 근거 - 직접 기술
		priorAdtTm:null,			// 전기 감사 시간
		intrAdtYnNm:null, 			// 내부회계감사(2조 이상) 여부 명
		intrAdtTm:null,				// 내부 감사시간
		fstBizAdtTm: null,			// 최초적용 직전 감사시간
		fstWkmnsp: null,			// 최초적용 직전 감사팀의 숙려도
		fstBizAdtTmReason: null,	// 최초적용 직전 사업연도 감사시간 수정시 근거 
		fstWkmnspReason: null,		// 최초적용 직전 사업연도 감사시간 수정시 근거 - 직접서술
	},
	computed: {
		pFirstBizDt:	function(){
			if(!this.firstBizDt) return null
			
			let dt = new Date(this.firstBizDt);
			dt.setFullYear(dt.getFullYear() - 1);
			return dt.toISOString().substr(0, 7)
		},
		pFirstBizDtAft3: function(){
			if(!this.pFirstBizDt) return null
			
			return Number(this.pFirstBizDt.split("-")[0]) + 3; 
		},
		pFirstMaxYear3: function(){
			if(!this.pFirstBizDt && !this.bizTodt) return null
		
			let fDt = new Date(this.firstBizDt);
			let bDt = new Date(this.bizTodt);
			
			fDt.setFullYear(fDt.getFullYear() + 3)
			
			if(fDt >= bDt){
				return this.fstBizAdtTm * 2 ;
			}else{
				return 0
			}
		},
	}
}


//################################################################################
// 표준감사시간 산출 영역
//################################################################################
const satCalculateMixin = {
	data: {
		baseWkmnsp:1,				// 기준 숙련도
		calAdtTm:null,				// 산출된 감사시간(a)
		calSat:null,				// 계산식에 의해 산출된 기준 표준감사시간(숙련도 반영 전)
		etDfnSat:null,				// 한공회 표준감사시간(기본산식 외 요소를 고려한 최종 표준감사시간)
		etTrgtAdtTm: null,			// 감사계약시 합의 시간 (표준감사시간 대상)
	},
	computed: {
		wkmnspSat: 		function(){return Number(this.etDfnSat) * Number(this.baseWkmnsp);},
	},
	methods: {
		onCalcSat: function(){
			alert('계산하기');
		}
	}
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
		totPrjtBdgt: function(){return this.etTrgtAdtTm + this.etcBdgtTm;}, // 감사계약시 합의 시간
		remainBdgt: function(){return this.totPrjtBdgt - this.raBdgtTm - this.flcmBdgtTm;}, // 잔여 예산 시간
	}
}


