<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:choose>
	<c:when test="${result.status == 'success'}">
	
	<c:set var="now" 	value="<%=new java.util.Date()%>" />
	<c:set var="roleCd" value="${sessionScope.SAMIL_SESSION_KEY.roleCd}" />
	<c:set var="authLv" value="${sessionScope.SAMIL_SESSION_KEY.authLv}" />
	
	<c:set var="flagEdit" 			value="${result.data.canEdit && result.data.stat != 'RQ'}" />
	<c:set var="flagHistory" 		value="${result.data.stat == 'CO'}" />
	<c:set var="flagReqAprv" 		value="${result.data.canEdit && (result.data.stat == 'RG' || result.data.stat == 'RJ') }" />
	<c:set var="flagRjReason" 		value="${result.data.canEdit && result.data.stat == 'RJ'}" />
	<c:set var="flagExcelImport" 	value="${result.data.canEdit && result.data.stat != 'RQ'}" />
	<c:set var="flagRetain" 		value="${(result.data.canEdit || roleCd=='bonb' || authLv=='10') 
												&& result.data.stat == 'CO' 
												&& result.data.retainTranYn == 'N' 
												&& result.data.retainScdlYn == 'N'}" />


<!-- 컨텐츠 시작 -->
<h1 class="hidden">Project Form</h1>
<style>
<!--
.gridbox table td{border-left:1px solid #ddd !important}
.gridbox table td:first-child{border-left:none !important}
-->
</style>
<div class="boxWhite">
	
	<ul class="tabType01">
		<li id="tabPrjtInfo"><a href="function:void(0)">표준감사시간</a></li>
		<li id="tabBdgt" class="on"><span>Budgeting</span></li>
	</ul>
	
	<div class="btnArea">
	<c:if test="${flagEdit}">
		<button id="btnEdit" type="button" class="btnPwc btnM action">Edit</button>
	</c:if>
		<button id="btnClose" type="button" class="btnPwc btnM action">Close</button>
	<c:if test="${flagReqAprv}">
		<div class="tipArea tipBtn">
			<button id="btnReqAprv" type="button" class="btnPwc btnM action">EL 승인 요청</button>
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
	</c:if>
	
	<!-- <c:if test="${flagRjReason}"> -->
		<button id="btnRjReason" type="button" class="btnPwc btnM action">반려 의견보기</button>
	<!-- </c:if> -->
	<c:if test="${result.data.canEdit}">
		<!-- <button id="btnConflict" type="button" class="btnPwc btnM action">Conflict 조회</button> -->
		<button id="btnProfit" type="button" class="btnPwc btnM action">수익성 분석</button>
	</c:if>
		<a id="flinkUpd" type="button" class="btnPwc btnM action" href="https://flink.samil.com/ssologin.jsp?SystemId=AA&PGM_NO=01463" target="_blank">F-link Update</a>
	</div>

	<div class="boxLine sumArea">
		<p class="proTitle">${result.data.prjtnm}<span>${result.data.prjtcd} (EL: ${result.data.chargptrnm}, PM: ${result.data.chargmgrnm}) </span></p>
		
		<div class="sumList">
			<ul>
				<li><b>프로젝트 시작일</b>${result.data.prjtFrdt}</li>
				<li><b>프로젝트 종료일</b>${result.data.prjtTodt}</li>
				<li><b>결산 년월</b>${result.data.bizTodt}</li>
				<li><b>예상 보고서일</b>${result.data.rprtScdlDt}</li>				
			</ul>
		</div>
	</div>
	
	<!-- ########################################################################################################################################## -->
	<!-- 20200424 추가 -->	
	<div class="titArea marT20">
		<h2 class="titDep2">CIS 예산 및 Code별 예산</h2>
	</div>
	
	<div id="cis-grid" class="gridbox" style="width:100%;"></div>

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
							<td rowspan="4" style="text-align:center;line-height:1.875em;">Core<br>Team</td>
							<td class="tleft">담당이사</td>
							<td class="tright"><fmt:formatNumber value="${result.data.elTm}" pattern="#,##0" /></td>
						</tr>
						<tr>
							<td class="tleft">PM</td>
							<td class="tright"><fmt:formatNumber value="${result.data.pmTm}" pattern="#,##0" /></td>
						</tr>
						<tr>
							<td class="tleft">감사팀</td>
							<td class="tright"><fmt:formatNumber value="${result.data.adtteamTm}" pattern="#,##0" /></td>
						</tr>
						<tr>
							<td class="tleft">New Staff(TBD)
								<div class="tipArea">
									<button type="button" class="btnTip">도움말</button>
									<div class="tipCont">New Staff(미입사자) 추정 투입시간</div>
								</div> 
							</td>
							<td class="tright"><fmt:formatNumber value="${result.data.newstaffTm}" pattern="#,##0" /></td>
						</tr>
						<tr>
							<td rowspan="4" style="text-align:center;line-height:1.875em;">Support<br>Team</td>
							<td class="tleft">QRP
								<div class="tipArea">
									<button type="button" class="btnTip">도움말</button>
									<div class="tipCont">EL 시간을 고려한 최소투입시간 고려하여 입력</div>
								</div> 
							</td>
							<td class="tright"><fmt:formatNumber value="${result.data.qrpTm}" pattern="#,##0" /></td>
						</tr>
						<tr>
							<td class="tleft">QC-(RM, ACS, M&amp;T 등)</td>
							<td class="tright"><fmt:formatNumber value="${result.data.qcTm}" pattern="#,##0" /></td>
						</tr>
						<tr>
							<td class="tleft">SPA</td>
							<td class="tright"><fmt:formatNumber value="${result.data.raTm}" pattern="#,##0" /></td>
						</tr>
						<tr>
							<td class="tleft">Fulcrum</td>
							<td class="tright"><fmt:formatNumber value="${result.data.fulcrumTm}" pattern="#,##0" /></td>
						</tr>
						<tr>
							<td class="tleft" colspan="2">Budget Total</td>
							<td class="tright"><fmt:formatNumber value="${result.data.totalTm}" pattern="#,##0" /></td>
						</tr>
					</tbody>		
				</table>
			</div>		
		</div>
		
		<c:if test="${result.data.satTrgtYn == 'Y'}">	
		<div class="div-col">
			<div class="titArea marT20">
				<h2 class="titDep2">표준감사시간 대상 (${result.data.satgrpNm})</h2>
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
							<td class="tright"><fmt:formatNumber value="${result.data.etDfnSat}" pattern="#,##0" /></td>
						</tr>
						<tr>
							<td class="tleft">기준 숙련도</td>
							<td class="tright"><fmt:formatNumber value="${result.data.baseWkmnsp}" pattern="#,##0.000" /></td>
						</tr>
						<tr>
							<td class="tleft">예상 팀 숙련도</td>
							<td class="tright"><fmt:formatNumber value="${result.data.satExpWkmnsp}" pattern="#,##0.000" /></td>
						</tr>
						<tr>
							<td class="tleft">계획단계표준감사시간</td>
							<td class="tright"><fmt:formatNumber value="${result.data.planSat}" pattern="#,##0" /></td>
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
							<td class="tright"><fmt:formatNumber value="${result.data.cntrtAdtTm}" pattern="#,##0" /></td>
						</tr>
						<tr>
							<td class="tleft">Budget Total(외감)</td>
							<td class="tright"><fmt:formatNumber value="${result.data.satBdgtTm}" pattern="#,##0" /></td>
						</tr>
					</tbody>		
				</table>
			</div>
		</div>	
		</c:if>
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

	
	<!-- ########################################################################################################################################## -->
	
	<div class="titArea marT20">
		<h2 class="titDep2">Budget Hour</h2>
		
		<div class="btnArea side">
		<%--
			result.data.retainTranYn : Retain 전송 여부(Y/N)
			result.data.profRetainTranYn : 프로파일에 등록된 Retain 전송 허용 여부 (Y/N)
		--%>
		<c:if test="${flagRetain}">
			<button id="btnTranToRetain" type="button" class="btnPwc btnS">Retain 등록</button>
	    </c:if>
	
		<c:if test="${flagHistory}">
			<button id="btnHistory" type="button" class="btnPwc btnS">History 보기</button>
		</c:if>
		<c:if test="${flagEdit}">
			<button id="btnExcelImport" type="button" class="btnPwc btnS">Excel Import</button>
		</c:if>
		<button id="btnExcelExport" type="button" class="btnPwc btnS">Excel Export</button>
		</div>
	</div>
	
	<div id="budget-grid" class="gridbox gridHp07" style="width:100%;height:100%"></div>
</div><!-- //boxWhite -->
<script type="text/javascript">

<c:if test="${result.data.canEdit}">
const profitPopup = Helper.Popup("profit", "Budget Hour기준 Project 예상수익", "/popup/profitabilityV3", {prjtCd:"${result.data.prjtcd}", totBdgtHour:Number('${result.data.totalTm}'), netRvnu:Number('${result.data.netRvnu}'), expCm:Number('${result.data.expCm}')});
const conflictPopup = Helper.Popup("conflict", "Conflict 조회", "/popup/conflict", {size:"L", prjtCd:"${result.data.prjtcd}", sDt:"${result.data.prjtFrdt}", eDt:"${result.data.prjtTodt}"});
</c:if>
<c:if test="${flagHistory}">
const historyPopup = Helper.Popup("history", "History 보기", "/popup/history", {size:"L", prjtCd:"${result.data.prjtcd}"})
</c:if>
<c:if test="${flagRjReason}">
const rejectOpinionPopupRead = Helper.Popup("rjOpinionRead", "반려의견 확인", "/popup/rjopinionread", {prjtCd:"${result.data.prjtcd}"});
</c:if>
<c:if test="${flagEdit}">
const excelImportPopup = Helper.Popup("excelImport", "Import Excel", "/popup/v3/fileUploader", {size:"S", importUrl:"/excel/import/v3/budgetHours", prjtCd:"${result.data.prjtcd}", allowExtensions:["xlsx", "xls"]});
</c:if>

<c:if test="${flagRetain && result.data.profRetainTranYn == 'Y' }">
const retainPopup = Helper.Popup('transToRetain', "Retain 등록", "/popup/retain", {size:"S", prjtCd:"${result.data.prjtcd}", prjtFrdt:"${result.data.prjtFrdt}", prjtTodt:"${result.data.prjtTodt}"});
</c:if>

$(document).ready(function(){
	const budgetGrid = {
			parent: "budget-grid",
			skin: THEME_INFO.THEME,
			image_path: THEME_INFO.THEME_IMG_PATH, 
			columns: [
				{id:"prjtcd", label:["Project Code", "#select_filter_strict"], type:"ro", align:"center", sort:"str", width:"100"},
				{id:"prjtnm", label:["Project Name", "#select_filter_strict"], type:"ro", align:"center", sort:"str", width:"100"},
				{id:"tbd", label:[Helper.getGridTitleAndInfo("TBD", "미정인 경우(New staff 등) Check(주 80시간 초과하여 일괄로 입력 가능)", 'left'), "#select_filter_strict"], type:"toggle", align:"center", sort:"str", width:"65"},
				{id:"actvNm", label:[Helper.getGridTitleAndInfo("Activity", "SPA, Fulcrum, New STaff(TBD), QC(QRP 포함) 예산 배정시 반드시 해당 Activity로 기입 필요 ", 'left'), "#select_filter_strict"], type:"ro", align:"left", sort:"str", width:"180", resize:false},
				{id:"locaNm", label:[Helper.getGridTitleAndInfo("Location", "Retain 반영시 팀별로 field 만 선택하여 반영 가능", 'left'), "#select_filter_strict"], type:"ro", align:"left", sort:"str", width:"90"},
				{id:"korNm", label:["Name", "#text_filter"], type:"ro", align:"left", sort:"str", width:"70"},
				{id:"emplNo", label:["사번", "#text_filter"], type:"ro", align:"center", sort:"str", width:"70"},
				{id:"gradNm", label:["Grade", "#select_filter_strict"], type:"ro", align:"left", sort:"str", width:"70"},
				{id:"wkmnsp", label:[Helper.getGridTitleAndInfo("숙련도", "예상감사보고서일과 수습종료일을 기준으로 자동계산되며, 수정 가능합니다.", 'right'), "#numeric_filter"], type:"ron", align:"right", sort:"int", width:"80"},
				{id:"totWkmnspAsgnTm", label:["숙련도 고려<br>투입시간", "#numeric_filter"], type:"ron", align:"right", sort:"int", width:"80"},
				{id:"totAsgnTm", label:["Total<br>Time", "#numeric_filter"], type:"ron", align:"right", sort:"int", width:"80"},
			<c:forEach var="sDt" items="${result.data.sDtOfWeek}" varStatus="weekStatus">
				{id:"week${weekStatus.count}", label:["${sDt.startDate}", "${sDt.workDay} days"], type:"ron", align:"right", width:"85"},
			</c:forEach>
			]
	};
	//----------------------------------------------
	// 20200424 추가 
	//----------------------------------------------
	const cisGrid = {
			parent: "cis-grid",
			skin: THEME_INFO.THEME,
			image_path: THEME_INFO.THEME_IMG_PATH,
			columns: [
				{id:"bdgtTrgtYn", label:[Helper.getGridTitleAndInfo("Budget<br>대상", "Budgeting이 필요한 경우 Check", 'left')], type:"toggle", align:"center", sort:"str", width:"90"},
				{id:"prjtcd", label:[Helper.getGridTitleAndInfo("프로젝트 코드", "지원코드는 자동으로 보여지며, 주관코드 추가가 필요한 경우에는 R&Q 담당자에게 요청(F-link 상 추가)", 'left')], type:"ro", align:"center", sort:"str", width:"120"},
				{id:"prjtnm", label:["프로젝트 명"], type:"ro", align:"left", sort:"str"},
				{id:"inadjustnm", label:["협업<br>구분"], type:"ro", align:"center", sort:"str", width:"70", resize:false},
				{id:"createdvnm", label:["생성<br>구분"], type:"ro", align:"center", sort:"str", width:"70"},
				{id:"auditgbnm", label:[Helper.getGridTitleAndInfo("외감<br>구분", 'F-link 에서 변경 가능하며, "01"코드인 경우 최초 외감으로 인식', 'left')], type:"ro", align:"center", sort:"str", width:"70"},
				{id:"amtw", label:["계약 금액"], type:"ron", align:"right", sort:"int", width:"150"},
				{id:"exbudgettime", label:[Helper.getGridTitleAndInfo("시간예산<br>(F-Link)", 'F-link 조회값, 생성 후 5개월내 변경가능', 'right')], type:"ron", align:"right", sort:"int", width:"100"},
				{id:"cntrtAdtTm", label:[Helper.getGridTitleAndInfo("감사계약시<br>합의시간", '보수에 따라 회사와 합의한 시간', 'right')], type:"ron", align:"right", sort:"int", width:"100"},
				{id:"bdgt", label:[Helper.getGridTitleAndInfo("Budgeting<br>Hours", '코드별 Budgeting 총 합계', 'right')], type:"ron", align:"right", sort:"int", width:"100"},
				{id:"bdgtWkmnsp", type:"ron"}
			]
	};

	setTimeout(function(){
		let budgetGridObj = new dhtmlXGridObject(budgetGrid);
		budgetGridObj.setNumberFormat("0,000.000",budgetGridObj.getColIndexById("wkmnsp"),".",",");
		budgetGridObj.setNumberFormat("0,000.00",budgetGridObj.getColIndexById("totAsgnTm"),".",",");
		budgetGridObj.setNumberFormat("0,000.00",budgetGridObj.getColIndexById("totWkmnspAsgnTm"),".",",");
		<c:forEach var="sDt" items="${result.data.sDtOfWeek}" varStatus="weekStatus">
		budgetGridObj.setNumberFormat("0,000.00",budgetGridObj.getColIndexById("week${weekStatus.count}"),".",",");
		</c:forEach>
		budgetGridObj.enableSmartRendering(true);
		budgetGridObj.enableResizing("false");
		budgetGridObj.splitAt(11);

		// 20200427 추가 
		let cisGridObj = new dhtmlXGridObject(cisGrid);
		cisGridObj.setColumnHidden(cisGridObj.getColIndexById("bdgtWkmnsp"),true);
		cisGridObj.setNumberFormat("0,000",cisGridObj.getColIndexById("amtw"),".",",");
		cisGridObj.setNumberFormat("0,000",cisGridObj.getColIndexById("bdgt"),".",",");
		cisGridObj.setNumberFormat("0,000",cisGridObj.getColIndexById("cntrtAdtTm"),".",",");
		cisGridObj.setNumberFormat("0,000",cisGridObj.getColIndexById("exbudgettime"),".",",");
		cisGridObj.setNumberFormat("0,000.000",cisGridObj.getColIndexById("expWkmnsp"),".",",");
		cisGridObj.attachFooter( 
				"Summary,#cspan,#cspan,#cspan,#cspan,#cspan" 
					+ ",<div id='sumAmtw'>0</div>"
					+ ",<div id='sumExbudgettime'>0</div>" 
					+ ",<div id='sumCntrtAdtTm'>0</div>"
					+ ",<div id='sumBdgt'>0</div>"
					/* + ",<div id='aveExpWkmnsp'>0</div>" */
					+ "<div></div>",		
				[
					 "text-align:right;font-size:14px;font-family:arial,notokr;font-style:inherit;background-color:#F7F7F7;height: 40px;padding: 1px 5px 0;border-left:0;border-right:0;"
					,"border-right:0; border-left:0"
					,"border-right:0; border-left:0"
					,"border-right:0; border-left:0"
					,"border-right:0; border-left:0"
					,"border-right:0; border-left:0"
					,"text-align:right;font-size:14px;font-family:arial,notokr;font-style:inherit;background-color:#FFFFFF;border-left:1px solid #ddd !important;border-right:0"
					,"text-align:right;font-size:14px;font-family:arial,notokr;font-style:inherit;background-color:#FFFFFF;border-left:1px solid #ddd !important;border-right:0"
					,"text-align:right;font-size:14px;font-family:arial,notokr;font-style:inherit;background-color:#FFFFFF;border-left:1px solid #ddd !important;border-right:0"
					,"text-align:right;font-size:14px;font-family:arial,notokr;font-style:inherit;background-color:#FFFFFF;border-left:1px solid #ddd !important;border-right:0"
					/* ,"text-align:right;font-size:14px;font-family:arial,notokr;font-style:inherit;background-color:#FFFFFF;border-left:1px solid #ddd !important;border-right:0" */
				    ,""
				]
		);	
		
		cisGridObj.enableResizing("false");
		cisGridObj.enableAutoHeight(true);
		
		bindEvent();
		loadData();
		
		
		function bindEvent(){
			$(document).on('click.gridResize', 'nav > button, .sumArea > .btnTog', function(e) {
		         e.preventDefault();
		         budgetGridObj.setSizes();
		         cisGridObj.setSizes();
		    });
			
			$("#tabPrjtInfo").off('click').on('click', function(e){
				e.preventDefault();
				Helper.goPage("/pages/project/read", {prjtCd:"${result.data.prjtcd}"});
			});
			
			<c:if test="${flagEdit}">
			$("#btnEdit").off("click").on("click", function(){
				if("${result.data.profBdgtYn}" === "Y") {					
					Helper.goPage("/pages/project/budget/edit", {prjtCd:"${result.data.prjtcd}"});
				}else{
					Helper.MessageBox('본부별 정책에 의해 Budgeting은 수정할 수 없습니다.');
				}
			})
			</c:if>
			
			$("#btnClose").off("click").on("click", function(){
				/* Helper.goPage("/pages/project/${result.data.formDiv}/list"); */
				Helper.goPage("/pages/project/v2/list");
			})
			
			<c:if test="${result.data.canEdit}">
			$("#btnProfit").off("click").on("click", function(){
				profitPopup.openPopup("profitability");	
			})
			</c:if>
			
			<c:if test="${flagRjReason}">
			$("#btnRjReason").off("click").on("click", function(){
				rejectOpinionPopupRead.openPopup("rjopinion");
			});
			</c:if>
			
			<c:if test="${flagReqAprv}">
			$("#btnReqAprv").off("click").on("click", function(){
				Helper.ConfirmBox("EL 승인 요청을 하시겠습니까?", function(flag){
					if(flag){						
						Helper.post("/project/v3/budget/reqELAprv", {prjtCd:"${result.data.prjtcd}"})
						.done(function(){
							Helper.goPage("/pages/project/budget/read", {prjtCd: "${result.data.prjtcd}"});
						});
					}
				})
			})
			</c:if>
			
			<c:if test="${flagRetain}">
			$("#btnTranToRetain").off("click").on("click", function(){
				<c:choose>
				<c:when test="${result.data.profRetainTranYn == 'Y'}">
					retainPopup.openPopup('retainpopup', function(){
						Helper.MessageBox("Retain 등록이 완료되었습니다.", function(){										
							Helper.goPage("/pages/project/budget/read", {prjtCd: "${result.data.prjtcd}"});
						});
					});	
				</c:when>
				<c:otherwise>
					Helper.MessageBox(["본부별 정책에 의해 본 시스템을 통한 Retain 등록은 제한 되어있습니다."]);
				</c:otherwise>
			</c:choose>
			})
			</c:if>
			
			<c:if test="${result.data.canEdit}">
			$("#btnConflict").off("click").on("click", function(){
				conflictPopup.openPopup("conflict");	
			})
			</c:if>
			
			<c:if test="${flagHistory}">
			$("#btnHistory").off('click').on('click', function(){
				historyPopup.openPopup('history');
			});
			</c:if>
			
			<c:if test="${flagEdit}">
			$("#btnExcelImport").off('click').on('click', function(){
				excelImportPopup.openPopup('import_budget_hours', function(){
					Helper.goPage("/pages/project/budget/read", {prjtCd: "${result.data.prjtcd}"});
				})
			});
			</c:if>
			
			$("#btnExcelExport").off('click').on('click', function(){
				Helper.ConfirmBox(["엑셀 다운로드 하시겠습니까?", "[참고] Import 목적으로 다운로드 시 Activity ~ Total Time 까지의 컬럼은 수정하지 마세요."], function(isOk){
					let param = $.param({
						prjtCd: "${result.data.prjtcd}",
						prjtNm: "${result.data.prjtnm}",
						filename:"표준감사시간_Budget_Hour(${result.data.prjtcd})",
						writerType: "budget"
					});
					
					if(isOk) budgetGridObj.toExcel(EXCEL_WRITER_URL_V3+"?"+param);	
				});
			});
		}
		
		function loadData(){
			budgetGridObj.clearAll();
			Helper.post("/project/v3/budget/list", {prjtCd:"${result.data.prjtcd}"})
			.done(function(data){
				budgetGridObj.parse(data, "js");
			});

			// 20200427 추가
			cisGridObj.clearAll();
			Helper.post("/project/v3/budget/cislist", {prjtCd:"${result.data.prjtcd}"})
			.done(function(data){				
				cisGridObj.parse(data, "js");
				
				let sumAmtw = 0;
				let sumExbudgettime = 0;
				let sumCntrtAdtTm = 0;
				let sumBdgt = 0;
				let sumExpWkmnsp = 0;
				let sumBdgtWkmnsp = 0;
				let aveExpWkmnsp = 0;
				
				cisGridObj.forEachRow(function(rid){
					const rowData = cisGridObj.getRowData(rid);
					sumAmtw += rowData.createdvnm === '정상' || rowData.createdvnm === '이관' ? Number(rowData.amtw) : 0;
					sumExbudgettime += Number(rowData.exbudgettime);
					sumCntrtAdtTm += Number(rowData.cntrtAdtTm);
					sumBdgt += Number(rowData.bdgt);
					sumExpWkmnsp += Number(rowData.expWkmnsp);
					sumBdgtWkmnsp += Number(rowData.bdgtWkmnsp);
				});
				aveExpWkmnsp = sumBdgt === 0 ? 0 : sumBdgtWkmnsp / sumBdgt;
				
				$("#sumAmtw").html(sumAmtw.toLocaleString(undefined));
				$("#sumExbudgettime").html(sumExbudgettime.toLocaleString(undefined));
				$("#sumCntrtAdtTm").html(sumCntrtAdtTm.toLocaleString(undefined));
				$("#sumBdgt").html(sumBdgt.toLocaleString(undefined));
				$("#aveExpWkmnsp").html(aveExpWkmnsp.toLocaleString(undefined, {minimumFractionDigits: 3, maximumFractionDigits: 3}));
				
			});		
		}
		
	},10);
	
});
</script>
<!-- //컨텐츠 끝 -->   
	</c:when>
	<c:otherwise>
	<script>Helper.MessageBox("${result.msg}", function(){Helper.goPage("/");});</script>
	</c:otherwise>
</c:choose>
