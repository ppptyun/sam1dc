<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="isDraft" value="${result.data.stat == 'DR'}" />
<c:set var="flagEdit" 			value="${result.data.canEdit && result.data.stat != 'RQ'}" />
<c:set var="flagHistory" 		value="${result.data.stat == 'CO'}" />
<c:set var="flagReqAprv" 		value="${result.data.canEdit && (result.data.stat == 'RG' || result.data.stat == 'RJ') }" />
<c:set var="flagRjReason" 		value="${result.data.canEdit && result.data.stat == 'RJ'}" />
<c:set var="flagExcelImport" 	value="${result.data.canEdit && result.data.stat != 'RQ'}" />
<c:set var="flagRetain" 		value="${(result.data.canEdit || roleCd=='bonb' || authLv=='10') 
											&& result.data.stat == 'CO' 
											&& result.data.retainTranYn == 'N' 
											&& result.data.retainScdlYn == 'N'}" />

<div id="budgetInfo">
	<h1 class="hidden">Budget Form</h1>
	<div v-if="!isInit">
		<div class="loadingA">
		<div class="inner">
			<p class="txtT">Loading...</p>
			<img src="${pageContext.request.contextPath}/resources/images/loading.gif" alt="로딩중">
			<p class="txtB">Please wait!!</p>
		</div>
	</div>
	</div>
	<div class="boxWhite" :class="{ieBody: isIE, boxWhite: true, readmode:mode=='read'}" v-show="isInit" style="display: none">
		<ul class="tabType01">
			<li><a @click="onClickSatTab">표준감사시간</a></li>
			<li class="on"><span>Budgeting</span></li>
		</ul>
		<div class="btnArea">
			<button v-if="mode === 'read' && ${flagEdit}" @click="onEdit" type="button" class="btnPwc btnM action">Edit</button>
			<button v-if="mode === 'read'" @click="onClose" type="button" class="btnPwc btnM action">Close</button>
			<button v-if="mode === 'edit'" @click="onSave" type="button" class="btnPwc btnM action">Save</button>
			<button v-if="mode === 'edit'" @click="onCancel" type="button" class="btnPwc btnM action">Cancel</button>
			<div v-if="mode === 'read' && ${flagReqAprv}" class="tipArea tipBtn">
				<button @click="onRequestApproval" type="button" class="btnPwc btnM action">EL 승인 요청</button>
				<div class="tipCont">
					<h3 class="titDep2">EL, QRP 최소 감사 투입시간</h3>
					<table class="tblH">
						<caption></caption>
						<colgroup><col style="width:30%"><col style="width:33%"><col style="width:auto"></colgroup>
						<thead>
							<tr>
								<th scope="col">총 시간</th>
								<th scope="col">EL</th>
								<th scope="col">QRP/SR</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="tleft">5,000 이상</td>
								<td class="tleft">Min (총 시간  x 2.0%, 200 시간)</td>
								<td class="tleft">Min (EL표준시간 X 20%, 40 시간)</td>
							</tr>
							<tr>
								<td class="tleft">3,000 이상 ~ 5,000 미만</td>
								<td class="tleft">Min (총 시간  x 3.0%, 100 시간)</td>
								<td class="tleft">Min (EL표준시간 X 20%, 20 시간)</td>
							</tr>
							<tr>
								<td class="tleft">2,000 이상 ~ 3,000 미만</td>
								<td class="tleft">Min (총 시간  x 3.0%, 70 시간)</td>
								<td class="tleft">Min (EL표준시간 X 20%, 14 시간)</td>
							</tr>
							<tr>
								<td class="tleft">1,000 이상 ~ 2,000 미만</td>
								<td class="tleft">Min (총 시간  x 3.0%, 50 시간)</td>
								<td class="tleft">Min (EL표준시간 X 20%, 10 시간)</td>
							</tr>
							<tr>
								<td class="tleft">1,000 미만</td>
								<td class="tleft">Min (총 시간  x 3.0%, 30 시간)</td>
								<td class="tleft">Min (EL표준시간 X 20%, 8 시간)</td>
							</tr>
							<tr>
								<td class="tleft">선박투자회사 등 <sup style="vertical-align:super">1)</sup></td>
								<td class="tleft">최소 2</td>
								<td class="tleft">최소 1</td>
							</tr>
						</tbody>
					</table>
					<p class="txtInfo"><sup>1)</sup> EL 또는 QRP가 선박투자회사 등 동일한 유형의 SPC 감사를 다수 수행하는 경우 업무특성을 고려하여 하기 기준 적용</p>
					
					<h3 class="titDep2">PCAOB Inspection 대상 업무의 최소 감사 투입시간</h3>
					<table class="tblH">
						<caption></caption>
						<colgroup><col style="width:30%"><col style="width:33%"><col style="width:auto"></colgroup>
						<thead>
							<tr>
								<th scope="col">총 시간</th>
								<th scope="col">EL</th>
								<th scope="col">QRP/SR</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="tleft">10,000 이상</td>
								<td class="tleft">Min (총 시간  x 2.5%, 300 시간)</td>
								<td class="tleft">EL표준시간 X 15%</td>
							</tr>
							<tr>
								<td class="tleft">5,000 이상 ~ 10,000 미만</td>
								<td class="tleft">Min (총 시간  x 3.0%, 250 시간)</td>
								<td class="tleft">EL표준시간 X 15%</td>
							</tr>
							<tr>
								<td class="tleft">5,000 미만</td>
								<td class="tleft">총 시간 X 3.5%</td>
								<td class="tleft">EL표준시간 X 20%</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<button v-if="mode === 'read' && ${flagRjReason}" @click="showRjReason" type="button" class="btnPwc btnM action">반려 의견보기</button>
			<button v-if="mode === 'read'" @click="showProfit" type="button" class="btnPwc btnM action">수익성 분석</button>
			
			<div v-if="mode === 'read'"  class="tipArea tipLeft">
				<a type="button" class="btnPwc btnM" href="javascript:void(0);">
					<span style="margin-right: 10px; display: inline-block;">F-link Update</span>
					<button type="button" class="btnTip" style="top: 5px; right: 0px; display: inline-block; position: absolute;">도움말</button>
				</a>
				<div class="tipCont" style="width:350px">
					CIS등록 승인 후 5개월내에는 본부빌링담당자분들을 통해 (w_pm1110 화면) 조정할 수 있습니다.
				</div>
			</div>
			
		</div>
		<div class="boxLine sumArea">
			<p class="proTitle">${result.data.prjtnm}<span>${result.data.prjtcd} (EL: ${result.data.chargptrnm}, PM: ${result.data.chargmgrnm}) </span></p>
			<div class="sumList">
				<ul>
					<li><b>프로젝트 시작일</b><span>${result.data.prjtFrdt}</span></li>
					<li><b>프로젝트 종료일</b><span>${result.data.prjtTodt}</span></li>
					<li><b>결산 년월</b><span>${result.data.bizTodt}</span></li>
					<li><b>예상 보고서일</b><span>${result.data.rprtScdlDt}</span></li>				
				</ul>
			</div>
		</div>
		
		<div class="titArea marT20">
			<h2 class="titDep2">CIS 예산 및 Code별 예산</h2>
		</div>
		<cis-dhx-grid 
			:mode="mode" 
			:grid-data="cisData" 
			@change-cntrt-adt-tm="changeCntrtAdtTm">
		</cis-dhx-grid>
		
		<div class="div-row">
			<div class="div-col">
				<div class="titArea marT20">		
					<h2 class="titDep2">Budget 요약</h2>	    
				</div>			
				
				<div>	
					<table class="tblForm">
						<caption>Budget 요약</caption>
						<colgroup><col style="width:25%"><col style="width:55%"><col style="width:30%"></colgroup>
						<tbody>
							<tr>
								<td rowspan="4" style="text-align:center;line-height:1.875em">Core<br>Team</td>
								<td class="tleft">담당이사</td>
								<td class="tright"><span>{{toFormat(data.elTm)}}</span></td>
							</tr>
							<tr>
								<td class="tleft">PM</td>
								<td class="tright"><span>{{toFormat(data.pmTm)}}</span></td>
							</tr>
							<tr>
								<td class="tleft">감사팀</td>
								<td class="tright"><span>{{toFormat(data.adtteamTm)}}</span></td>
							</tr>
							<tr>
								<td class="tleft">New Staff(TBD)
									<div class="tipArea">
										<button type="button" class="btnTip">도움말</button>
										<div class="tipCont">New Staff(미입사자) 추정 투입시간</div>
									</div> 
								</td>
								<td class="tright"><span>{{toFormat(data.newstaffTm)}}</span></td>
							</tr>
							<tr>
								<td rowspan="4" style="text-align:center;line-height:1.875em">Support<br>Team</td>
								<td class="tleft">QRP
									<div class="tipArea">
										<button type="button" class="btnTip">도움말</button>
										<div class="tipCont">EL 시간을 고려한 최소투입시간 고려하여 입력</div>
									</div> 
								</td>
								<td class="tright"><span>{{toFormat(data.qrpTm)}}</span></td>
							</tr>
							<tr>
								<td class="tleft">QC-(RM, ACS, M&amp;T 등)</td>
								<td class="tright"><span>{{toFormat(data.qcTm)}}</span></td>
							</tr>						
							<tr>
								<td class="tleft">SPA</td>
								<td class="tright"><span>{{toFormat(data.raTm)}}</span></td>
							</tr>
							<tr>
								<td class="tleft">Fulcrum</td>
								<td class="tright"><span>{{toFormat(data.fulcrumTm)}}</span></td>
							</tr>
							<tr>
								<td class="tleft" colspan="2">Budget Total</td>
								<td class="tright"><span>{{toFormat(data.totalTm)}}</span></td>
							</tr>
						</tbody>		
					</table>
				</div>		
			</div>
			
			<div class="div-col" v-if="data.satTrgtYn === 'Y'">
				<div class="titArea marT20">
					<h2 class="titDep2">표준감사시간 대상 ({{data.satgrpNm}})</h2>
				</div>
				
				<div>
					<table class="tblForm">
						<caption>표준감사시간 대상 (${result.data.satgrpNm})</caption>
						<colgroup><col style="width:auto"><col style="width:35%"></colgroup>
						<tbody>
							<tr>
								<th class="tleft">구분</th>
								<th class="tright">값</th>
							</tr>
							<tr>
								<td class="tleft">한공회표준감사시간</td>
								<td class="tright">{{toFormat(data.etDfnSat)}}</td>
							</tr>
							<tr>
								<td class="tleft">기준 숙련도</td>
								<td class="tright">{{toFormat(data.baseWkmnsp, 3)}}</td>
							</tr>
							<tr>
								<td class="tleft">예상 팀 숙련도</td>
								<td class="tright">{{toFormat(data.satExpWkmnsp, 3)}}</td>
							</tr>
							<tr>
								<td class="tleft">계획단계표준감사시간</td>
								<td class="tright">{{toFormat(data.planSat)}}</td>
							</tr>
							<tr>
								<td class="tleft">&nbsp;</td>
								<td class="tright">&nbsp;</td>
							</tr>
							<tr>
								<td class="tleft">&nbsp;</td>
								<td class="tright">&nbsp;</td>
							</tr>
							<tr>
								<td class="tleft">감사계약시합의시간</td>
								<td class="tright">{{toFormat(data.cntrtAdtTm)}}</td>
							</tr>
							<tr>
								<td class="tleft">Budget Total(외감)</td>
								<td class="tright">{{toFormat(data.satBdgtTm)}}</td>
							</tr>
						</tbody>		
					</table>
				</div>
			</div>
			
			<div class="div-col">
				<div class="titArea marT20">
					<h2 class="titDep2">&nbsp;</h2>
				</div>
				
				<div>
					<table class="bdgtTblForm">
						<tbody>
							<tr>
								<td class="tleft"> * 감사계약시 제시한 숙련도에 따라 준수해야하는 감사시간은 달라질 수 있습니다.</td>
							</tr>
						</tbody>		
					</table>
				</div>
			</div>	
		</div>
		
		<div class="titArea marT20">
			<h2 class="titDep2">Budget Hour</h2>
			<div class="btnArea side" v-if="mode==='read'">
				<button v-if="${flagRetain}" @click="onTranToRetain" type="button" class="btnPwc btnS">Retain 등록</button>
				<button v-if="${flagHistory}" @click="onViewHistory" type="button" class="btnPwc btnS">History 보기</button>
				<button v-if="${flagEdit}" @click="onExcelImport" type="button" class="btnPwc btnS">Excel Import</button>
				<button @click="onExcelExport" type="button" class="btnPwc btnS">Excel Export</button>
			</div>
			<div class="btnArea side" v-else>
				<button @click="onAddRow" type="button" class="btnPwc btnS action" sh-click="btnAddRow">Add Row</button>
				<button @click="calculate" type="button" class="btnPwc btnS" sh-click="btnCalculate">계산하기</button>
			</div>
		</div>
		<budget-dhx-grid 
			:mode="mode" 
			:week-info="weekInfo" 
			:options="budgetGridOptions"
			:grid-data="budgetData"
		></budget-dhx-grid>
	</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/v3/DhxGrid.js"></script>
<script>
const weekInfo = [];
const workDayList = [];
<c:forEach var="sDt" items="${result.data.sDtOfWeek}" varStatus="weekStatus">
weekInfo[${weekStatus.index}] = {id: "week${weekStatus.count}", workDay: ${sDt.workDay}, startDate: "${sDt.startDate}"}; workDayList[${weekStatus.index}] = ${sDt.workDay};</c:forEach>

/* Popup 정의 */
const profitPopup = function(){return Helper.Popup("profit", "Budget Hour기준 Project 예상수익", "/popup/profitabilityV3", {prjtCd:"${result.data.prjtcd}", totBdgtHour:Number('${result.data.totalTm}'), netRvnu:Number('${result.data.netRvnu}'), expCm:Number('${result.data.expCm}')});};
const conflictPopup = function(){return Helper.Popup("conflict", "Conflict 조회", "/popup/conflict", {size:"L", prjtCd:"${result.data.prjtcd}", sDt:"${result.data.prjtFrdt}", eDt:"${result.data.prjtTodt}"});};
const historyPopup = function(){return Helper.Popup("history", "History 보기", "/popup/history", {size:"L", prjtCd:"${result.data.prjtcd}"});};
const rejectOpinionPopupRead = function(){return Helper.Popup("rjOpinionRead", "반려의견 확인", "/popup/rjopinionread", {prjtCd:"${result.data.prjtcd}"});};
const excelImportPopup = function(){return Helper.Popup("excelImport", "Import Excel", "/popup/v3/fileUploader", {size:"S", importUrl:"/excel/import/v3/budgetHours", prjtCd:"${result.data.prjtcd}", allowExtensions:["xlsx", "xls"]});};
const retainPopup = function(){return Helper.Popup('transToRetain', "Retain 등록", "/popup/retain", {size:"S", prjtCd:"${result.data.prjtcd}", prjtFrdt:"${result.data.prjtFrdt}", prjtTodt:"${result.data.prjtTodt}"});};
const empPopup = function(){return Helper.Popup("employee", "임직원 선택", "/popup/org", {prjtCd:"${result.data.prjtcd}", satgrpCd:"${result.data.satgrpCd}"});};
const reasonModifyPop = function(){return Helper.Popup("modifyReason", "결재 요청 (수정사유 입력)", "/popup/reasonModify", {size:"S", prjtCd:"${result.data.prjtcd}"});};


const msgManager = new MessageManager();

let initData = null;
let initCisData = null;
let initBudgetData = null;
const budgetInfo = new Vue({
	el: "#budgetInfo",
	data: {
		isInit: false,
		mode: '',
		data: {},
		cisData: [],
		budgetData: [],
		weekInfo: weekInfo,
		cisGridObj: null,		// dhxGrid
		budgetGridOptions: {},
	},
	created: function() {
		const thisObj = this;
		// fetch Data
		this.data = ${result.data};
		this.isInit = true;
		this.loadCisData();
		this.loadBudgetData();
		
		this.mode = this.data.mode;
		initData = JSON.stringify(this.data);
		
		console.log(this.mode)
		
		this.budgetGridOptions.bdgtRoleConfig = ${bdgtRoleConfig};
		this.budgetGridOptions.empPopup = empPopup;
		this.budgetGridOptions.prjtCd = '${result.data.prjtcd}';
		this.budgetGridOptions.actvList = [];
		this.budgetGridOptions.locaList = [];
		<c:forEach var="actv" items="${actvListV3}" varStatus="status">
		this.budgetGridOptions.actvList[${status.index}] = {cd:"${actv.cd}", nm:"${actv.nm}"};</c:forEach>
		<c:forEach var="loca" items="${locaList}" varStatus="status">
		this.budgetGridOptions.locaList[${status.index}] = {cd:"${loca.cd}", nm:"${loca.nm}"};</c:forEach>
	},
	mounted: function() {},
	computed: {
		isIE: function(){
			var agent = navigator.userAgent.toLowerCase();
            if ((navigator.appName == 'Netscape' && agent.indexOf('trident') != -1) || (agent.indexOf("msie") != -1)) {
                return true
            } else {
                return false
            }
		},
	},
	methods: {
		loadData: function() {
			let dfd = $.Deferred();
			const thisObj = this;
			Helper.post("/project/v3/budget/summary", {
				prjtCd: this.data.prjtcd
			}, true)
			.fail(function(){
				dfd.reject();
			})
			.done(function(data){
				thisObj.data = data;
				initData = JSON.stringify(data);
				dfd.resolve();
			});
			return dfd.promise();
		},
		loadCisData: function() {
			let dfd = $.Deferred();
			const thisObj = this;
			$.promise
			Helper.post("/project/v3/budget/cislist", {
				prjtCd: this.data.prjtcd
			}, true)
			.fail(function(){
				dfd.reject();
			})
			.done(function(list){
				thisObj.cisData = list;
				initCisData = JSON.stringify(list);
				dfd.resolve();
			});
			return dfd.promise(); 
		},
		loadCisDataFromGrid: function() {
			const thisObj = this;
			EVENT_BUS.$emit('getDataFromCisGrid', function(data){
				thisObj.cisData = data;
			});
		},
		loadBudgetData: function() {
			let dfd = $.Deferred();
			const thisObj = this;
			Helper.post("/project/v3/budget/list", {
				prjtCd: this.data.prjtcd
			}, true)
			.fail(function(){
				dfd.reject();
			})
			.done(function(list){
				thisObj.budgetData = list;
				initBudgetData = JSON.stringify(list);
				dfd.resolve();
			})
			return dfd.promise();
		},
		loadBudgetDataFromGrid: function() {
			const thisObj = this;
			EVENT_BUS.$emit('getDataFromBudgetGrid', function(data){
				thisObj.budgetData = data;
			});
		},
		toFormat: function(value, pos) {
			let _val = Number(value);
			let _pos = Number(pos || 0);
			
			if(isNaN(_val)) {
				return null;
			}else {
				return _val.toLocaleString(undefined, {minimumFractionDigits: _pos, maximumFractionDigits: _pos})
			}
		},
		onClickSatTab: function() {
			Helper.goPage("/pages/project/read", {prjtCd:this.data.prjtcd});
		},
		onExcelImport: function() {
			if("${result.data.profBdgtYn}" === "Y") {
				excelImportPopup().openPopup('import_budget_hours', function(){
					Helper.goPage("/pages/project/budget/read", {prjtCd: "${result.data.prjtcd}"});
				})
			}else{
				if(this.data.profBdgtReason) {
					const msg = this.data.profBdgtReason.split('_');
					msgManager.getMessage(msg[0], msg[1])
					if(msg[0]) {
						Helper.MessageBox(msgManager.getMessage(msg[0], msg[1]))
					} else {
						Helper.MessageBox('본부별 정책에 의해 표준감사시간은 수정할 수 없습니다.');
					}
				} else {
					Helper.MessageBox('본부별 정책에 의해 표준감사시간은 수정할 수 없습니다.');
				}
			}
		},
		onExcelExport: function() {
			Helper.ConfirmBox(["엑셀 다운로드 하시겠습니까?", "[참고] Import 목적으로 다운로드 시 Activity ~ Total Time 까지의 컬럼은 수정하지 마세요."], function(isOk){
				let param = {
					prjtCd: "${result.data.prjtcd}",
					prjtNm: "${result.data.prjtnm}",
					filename:"표준감사시간_Budget_Hour(${result.data.prjtcd})",
					writerType: "budget"
				};
				
				if(isOk) EVENT_BUS.$emit('excelExportBudgetGrid', param);
			});
		},
		onEdit: function() {
			if("${result.data.profBdgtYn}" === "Y") {					
				// this.mode = 'edit';
				Helper.goPage("/pages/project/budget/edit", {prjtCd:"${result.data.prjtcd}"});
			}else{
				console.log(this.data.profBdgtReason)
				if(this.data.profBdgtReason) {
					const msg = this.data.profBdgtReason.split('_');
					msgManager.getMessage(msg[0], msg[1])
					if(msg[0]) {
						Helper.MessageBox(msgManager.getMessage(msg[0], msg[1]))
					} else {
						Helper.MessageBox('본부별 정책에 의해 표준감사시간은 수정할 수 없습니다.');
					}
				} else {
					Helper.MessageBox('본부별 정책에 의해 표준감사시간은 수정할 수 없습니다.');
				}
			}
		},
		onClose: function() {
			Helper.goPage("/pages/project/v2/list");
		},
		onSave: function() {
			const thisObj = this;
			if(!this.calculate()) return;
			
			const budgetList = this.budgetData.map(function(item){
				let list = [];
				for(let i=0; i<weekInfo.length; i++){
					if(Number(item['week'+(i+1)]) > 0) {
						list.push({
							asgnTm: Number(item['week'+(i+1)]),
							weekFrdt: weekInfo[i].startDate.replace(/[\/]/g, '-')
						})
					} 
				}
				return {
					prjtcd: item.prjtcd,
					membEmplNo: item.tbd === 'Y' && ['09','12','13','14','15','16'].includes(item.actvCd) ? '999999' : item.emplNo,
					tbd: item.tbd,
					actvCd: item.actvCd,
					locaCd: item.locaCd,
					gradCd: item.gradCd,
					wkmnsp: item.wkmnsp,
					totAsgnTm: item.totAsgnTm,
					list: list
				}
			});
			
			Helper.showLoading();
			Helper.post("/project/v3/budget/save", {prjtCd:"${result.data.prjtcd}", list: budgetList, cislist: this.cisData})
			.done(function(){
				Helper.goPage("/pages/project/budget/read", {prjtCd:"${result.data.prjtcd}"});
				// 저장 후 relaoad
				/* $.when(thisObj.loadData(), thisObj.loadCisData(), thisObj.loadBudgetData())
				.then(function(){
					thisObj.mode = 'read';
					Helper.hideLoading();
				}); */
			})
			.fail(function(){
				
			})
		},
		onCancel: function() {
			/* this.mode = 'read';
			this.data = JSON.parse(initData);
			this.cisData = JSON.parse(initCisData);
			this.budgetData = JSON.parse(initBudgetData); */
			Helper.goPage("/pages/project/budget/read", {prjtCd:"${result.data.prjtcd}"});
		},
		changeCntrtAdtTm: function(){
			const thisObj = this;
			EVENT_BUS.$emit('getDataFromCisGrid', function(data) {
				tmp = data.filter(function(data){ return data.auditgbnm === '외감'});
				thisObj.data.cntrtAdtTm = tmp.map(function(data){return Number(data.cntrtAdtTm);}).reduce(function(acc, curr) {return acc + curr}, 0); 
			});
		},
		onTranToRetain: function() {
			if(this.data.profRetainTranYn === 'Y') {
				retainPopup().openPopup('retainpopup', function(){
					Helper.MessageBox("Retain 등록이 완료되었습니다.", function(){										
						Helper.goPage("/pages/project/budget/read", {prjtCd: "${result.data.prjtcd}"});
					});
				});
			} else {
				Helper.MessageBox(["본부별 정책에 의해 본 시스템을 통한 Retain 등록은 제한 되어있습니다."]);
			}
		},
		onViewHistory: function() {
			historyPopup().openPopup('history');
		},
		onAddRow: function() {
			this.loadCisDataFromGrid();
			let list = this.cisData.filter(function(item){return item.bdgtTrgtYn === 'Y';});
			if(list.length > 0){
				EVENT_BUS.$emit('addRowToBudgetGrid', list);
			} else {
				Helper.MessageBox("[CIS 에산 및 Code별 예산]에서 Budget 대상이 없습니다.");	
			}
		},
		onRequestApproval: function() {
			if(this.data.aprvCmpltDt !== null) {
				// 한번 승인요청 있었을 경우, 
				reasonModifyPop().openPopup('reasonModifyPop', function(data) {
					data.prjtCd = "${result.data.prjtcd}";
					
					Helper.post("/project/v3/budget/reqELAprv", data)
					.done(function(){
						Helper.goPage("/pages/project/budget/read", {prjtCd: "${result.data.prjtcd}"});
					});
				})
			}else {				
				Helper.ConfirmBox("EL 승인 요청을 하시겠습니까?", function(flag){
					if(flag){						
						Helper.post("/project/v3/budget/reqELAprv", {prjtCd:"${result.data.prjtcd}"})
						.done(function(){
							Helper.goPage("/pages/project/budget/read", {prjtCd: "${result.data.prjtcd}"});
						});
					}
				})
			}
			
		},
		showProfit: function(){
			profitPopup().openPopup("profitability");	
		},
		showRjReason: function() {
			rejectOpinionPopupRead().openPopup("rjopinion");
		},
		validCheck: function() {
			// 1. Budget 대상으로 지정된 프로젝트 코드가 중복인지 확인
			// 2. Budget 대상이 아닌 프로젝트가 Budget Hour에 존재하는지 확인
			// 3. Budget Hour 필수 값 체크(키 값으로 사용하는 데이터) 
			// 4. Budget Hour 키값이 중복인지 확인
			// 5. 하루 최대 시간 이상으로 Budget Hour로 작성했는지 확인 (tbd=='Y'는 제외)
			
			const thisObj = this;
			let msg=[];
			let checkBudgetKey = {};
			const numRegExp = /^(0|[1-9][0-9]+)(\.[0-9]+)?$/g;
			this.loadCisDataFromGrid();
			this.loadBudgetDataFromGrid();
			
			const bdgtTrgtlist = this.cisData.filter(function(data){return data.bdgtTrgtYn === 'Y';});
			const bdgtTrgtPrjtCd = bdgtTrgtlist.map(function(data){return data.prjtcd;}); 
			if (bdgtTrgtlist.length > 0) {
				// 1. Budget 대상으로 지정된 프로젝트 코드가 중복인지 확인
				const retData = Helper.postSync('/project/v3/budget/checkDuplPrjt', {mainPrjtCd: this.data.prjtcd ,prjtList: bdgtTrgtlist});	
				if(retData.status === 'success') {
					if(retData.data && retData.data.length > 0){
						return ["Budget 대상 프로젝트 중 중복이 발생하였습니다."];	
					}
				} else {
					return ["Budget 대상 중복확인 중 오류가 발생하였습니다."]
				}
				
				// 2. Budget 대상이 아닌 프로젝트가 Budget Hour에 존재하는지 확인
				const notAllowedPrjtCd = this.budgetData
					.filter(function(data){return !bdgtTrgtPrjtCd.includes(data.prjtcd);})
					.map(function(data){return data.prjtcd})
				
				if(notAllowedPrjtCd.length > 0) {
					msg.push("[" + notAllowedPrjtCd.join(', ') + "]는 CIS 예산 및 Code별 예산에서 Budget 대상으로 지정 되어 있지 않습니다.");
					msg.push("[Buget Hour]에서 해당 프로젝트코드를 삭제하거나 [CIS 예산 및 Code별 예산]에서 Budget 대상으로 지정하시기 바랍니다. ");
				}
			} else {
				if(this.cisData.length > 0) {
					msg.push("[CIS 예산 및 Code별 예산]에 Budget 대상이 없습니다.");	
				}
			}
			
			
			
			this.budgetData.forEach(function(data, index) {
				// 3. Budget Hour 필수 값 체크(키 값으로 사용하는 데이터)
				let tmpMsg = [];
				let rowNum = data.rowIndex + 1;
				if(!data.prjtcd) tmpMsg.push('프로젝트 코드');
				if(!data.actvCd) tmpMsg.push('Activity');
				if(!data.locaCd) tmpMsg.push('Location');
				if(!data.korNm) tmpMsg.push('임직원');
				if(tmpMsg.length > 0){
					msg.push((data.rowIndex + 1) + '줄: [' + tmpMsg.join(', ') + ']을(를) 입력하세요.');	
				} else {
					let key = data.actvCd + "_" + data.locaCd + "_" + data.emplNo + "_" + data.korNm + "_" + data.prjtcd;
					checkBudgetKey[key] = (checkBudgetKey[key]||0) + 1
				}
				
				
				// 5. 하루 최대 시간 이상으로 Budget Hour로 작성했는지 확인 (tbd=='Y'는 제외)
				if(data.tbd !== 'Y') {
					for(let i=1; i<=weekInfo.length; i++) {
						let bdgtTm = data['week' + i];
						let label = weekInfo[i-1].startDate;
						let workDay = weekInfo[i-1].workDay;
						let maxWorkTm = workDay * 16
						
						if(bdgtTm > maxWorkTm){
							msg.push(rowNum + "줄: [" + label + "]에는 최대 " + maxWorkTm + "시간까지만 입력 가능합니다.");
 						}else{
							let regExp = /^([0-9]+(\.(00?|25|50?|75))?)?$/g;
							if(!regExp.test(bdgtTm.toString())){
								msg.push(rowNum + "줄: [" + label + "]에는 숫자만 입력 가능하며, 소수점 이하는 0.25, 0.5, 0.75(15분 단위)만 입력가능합니다.");
							}
 						}
					}
				}
			})
			
			// 4. Budget Hour 키값이 중복인지 확인
			for(let key in checkBudgetKey) {
				if(checkBudgetKey[key] > 1){
					let tmpKey = key.split("_");
					msg.push("[" + tmpKey[4] + "] " +tmpKey[3] + "("+ tmpKey[2] + ")님은 Project Code, Activity, Location, Name 중복건이 존재합니다.");
				}
			}
			
			return msg;
		},
		calculate: function() {
			try{
				const thisObj = this;
				let msg = this.validCheck();
				if(msg.length > 0) {
					Helper.MessageBox(msg);
					return false;
				}
				
				// CIS 적용
				this.cisData.forEach(function(item){
					item.bdgt = thisObj.budgetData
					.filter(function(bdgt){
						return item.prjtcd === bdgt.prjtcd;
					})
					.map(function(bdgt){
						let totTm = 0;
						for(let i=1; i<=weekInfo.length; i++) {
							totTm += Number(bdgt['week' + i]);
						}
						return totTm;
					})
					.reduce(function(acc, curr){
						return acc + curr;
					}, 0)
				});

				// Summary 적용
				const satPrjts = this.cisData.filter(function(item){return item.auditgbnm === '외감'});
				const satPrjtCds = satPrjts.map(function(item){return item.prjtcd});
				const defaultSummary = {
						elTm: 0,
						pmTm: 0,
						adtteamTm: 0,
						newstaffTm: 0,
						qrpTm: 0,
						qcTm: 0,
						raTm: 0,
						fulcrumTm: 0,
						totalTm: 0,
						satBdgtTm: 0,
						satBdgtWkmnspTm: 0
					  } 
				
				const summary = this.budgetData
				.map(function(item){
					let totTm = 0;
					
					for(let i=1; i<= weekInfo.length; i++) {
						totTm += Number(item['week'+i]);
					}
					return {
						isSat: satPrjtCds.indexOf(item.prjtcd) !== -1,
						role: item.emplNo === '${result.data.chargptr}'
							  	? 'el'
							  	: item.emplNo === '${result.data.chargmgr}'
								? 'pm'
							  	: item.actvCd === '12'
							  	? 'qc'
					  			: item.actvCd === '16'
								? 'qrp'
								: item.actvCd === '14'
								? 'fulcrum'
								: item.actvCd === '15' && item.tbd === 'Y'
								? 'newstaff'
								: item.actvCd === '09' || item.actvCd === '13'
								? 'ra'
								: 'adtteam',
						wkmnsp: item.wkmnsp,
						totTm: totTm,
					}
				})
				.reduce(function(acc, item, index, array){
					acc.totalTm += item.totTm;
					acc.satBdgtTm += (item.isSat ? item.totTm : 0);
					acc.satBdgtWkmnspTm += (item.isSat ? item.totTm * item.wkmnsp : 0);
					switch(item.role) {
					case 'el': 		acc.elTm += item.totTm; break;
					case 'pm': 		acc.pmTm += item.totTm; break;
					case 'adtteam': acc.adtteamTm += item.totTm; break;
					case 'newstaff':acc.newstaffTm += item.totTm; break;
					case 'qrp': 	acc.qrpTm += item.totTm; break;
					case 'qc': 		acc.qcTm += item.totTm; break;
					case 'ra': 		acc.raTm += item.totTm; break;
					case 'fulcrum': acc.fulcrumTm += item.totTm; break;
					default: 
						console.warn('허용되지 않는 타입이 존재합니다.', acc.role)
					}
					return acc;	
				}, defaultSummary);
				// 예상 팀 숙련도
				summary.satExpWkmnsp = summary.totalTm === 0 ? 0 : summary.satBdgtWkmnspTm / summary.satBdgtTm;
				delete summary.satBdgtWkmnspTm	// 불필 요항목 삭제(중간 계산하기 위한것)
				
				// [표준감사시간 대상] 계획단계 표준감사시간 계산 (한공회 표준감사시간 * 기준숙련도 / 예상 팀 숙련도)
				summary.planSat = summary.satExpWkmnsp === 0 ? 0 : Number('${result.data.etDfnSat}') * Number('${result.data.baseWkmnsp}') / summary.satExpWkmnsp; //
				
				// 적용
				Object.assign(this.data, summary);
				return true;
			}catch(e){
				console.error(e);
				Helper.MessageBox(['계산하는 중 스크립트 오류가 발생하였습니다.', '새로 고침 후 재작성 해보시고 반복될 경우 서비스데스크로 문의 하시기 바랍니다.']);
				return false;
			}
		},
	},
});
</script>