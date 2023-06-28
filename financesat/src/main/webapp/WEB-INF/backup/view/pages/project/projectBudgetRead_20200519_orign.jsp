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
		<li id="tabPrjtInfo"><a href="function:void(0)">프로젝트 정보</a></li>
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
	
	<c:if test="${flagRjReason}">
		<button id="btnRjReason" type="button" class="btnPwc btnM action">반려 의견보기</button>
	</c:if>
	<c:if test="${result.data.canEdit}">
		<button id="btnConflict" type="button" class="btnPwc btnM action">Conflict 조회</button>
		<button id="btnProfit" type="button" class="btnPwc btnM action">수익성 분석</button>
	</c:if>
		<a id="flinkUpd" type="button" class="btnPwc btnM action" href="https://flink.samil.com/ssologin.jsp?SystemId=AA&PGM_NO=01463" target="_blank">F-link Update</a>
	</div>

	<div class="boxLine sumArea toggleOpen">
		<p class="proTitle">${result.data.prjtNm}<span>${result.data.prjtCd}</span></p>
		
		<div class="sumList">
			<ul>
				<li class="info01"><b>계약금액(백만원)</b><fmt:formatNumber value="${result.data.totCntrtFee}" pattern="#,##0" /></li>
				<li class="info02"><b>감사계약시 합의 시간</b><fmt:formatNumber value="${result.data.totPrjtBdgt}" pattern="#,##0" /> hr</li>
				<li class="info03"><b>Total Budget Hour
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							Engagement Team에서 입력한 시간이 집계됩니다.<br>(Fulcrum, RA 배부 시간 등이 포함)
						</div>
					</div>
				</b><span class="colPoint01"><fmt:formatNumber value="${result.data.totMembBdgt}" pattern="#,##0" /></span> hr</li>
				<li class="info04"><b>예상 팀 숙련도
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							숙련도 고려후 Total Budget Hour/ Total Budget Hour
						</div>
					</div>
				</b><span class="colPoint01"><fmt:formatNumber value="${result.data.totMembBdgt==0?null:result.data.totMembBdgtWkmnsp/result.data.totMembBdgt}" pattern="#,##0.000" /></span></li>
				<li class="info05"><b>계획단계 <br>표준감사시간
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							기본산식 외 요소를 고려한 최종 표준감사시간 * 기준숙련도 / 예상 팀 숙련도 
						</div>
					</div>
				</b><fmt:formatNumber value="${result.data.totMembBdgt==0?null:result.data.wkmnspSat/(result.data.totMembBdgtWkmnsp/result.data.totMembBdgt)}" pattern="#,##0" /> hr</li>
				<li class="info06"><b>예상 CM(백만원)</b><fmt:formatNumber value="${result.data.expCm}" pattern="#,##0" /></li>
			</ul>
		</div>
		<button type="button" class="btnTog" aria-expanded="true">닫기</button>
	</div>
	
	
	<div class="titArea marT20">
		<h2 class="titDep2">Budget Hour</h2>
		<ul class="txtInfo02">
			<li><em>New Staff 예측 투입시간</em> : <fmt:formatNumber value="${result.data.newStfBdgtTm}" pattern="#,##0.00" /> hr * <fmt:formatNumber value="${result.data.newStfWkmnsp}"/> (숙련도) = <span><fmt:formatNumber value="${result.data.newStfBdgtTm * result.data.newStfWkmnsp}" pattern="#,##0.00"/></span> hr </li>
			<li><em>Other(QC 등) 투입시간</em> : <fmt:formatNumber value="${result.data.otherBdgtTm}" pattern="#,##0.00" />  hr * <fmt:formatNumber value="${result.data.otherWkmnsp}"/> (숙련도) = <span><fmt:formatNumber value="${result.data.otherBdgtTm * result.data.otherWkmnsp}" pattern="#,##0.00"/></span> hr </li>
			<li><em>RA 배부예산</em> : <fmt:formatNumber value="${result.data.raBdgtTm}" pattern="#,##0.00" /> hr * <fmt:formatNumber value="${result.data.baseWkmnsp}"/> (<c:if test="${result.data.satTrgtYn == 'Y'}">기준 </c:if>숙련도) = <span><fmt:formatNumber value="${result.data.raBdgtTm * result.data.baseWkmnsp}" pattern="#,##0.00"/></span> hr </li>
			<li><em>Fulcrum 배부예산</em> : <fmt:formatNumber value="${result.data.flcmBdgtTm}" pattern="#,##0.00" /> hr * <fmt:formatNumber value="${result.data.baseWkmnsp}"/> (<c:if test="${result.data.satTrgtYn == 'Y'}">기준 </c:if>숙련도) = <span><fmt:formatNumber value="${result.data.flcmBdgtTm * result.data.baseWkmnsp}" pattern="#,##0.00"/></span> hr </li>
			<li class="optional-expand" style="display:none"><em>Total Budget Hour</em> : <fmt:formatNumber value="${result.data.totMembBdgt}" pattern="#,##0.00" /> hr</li>
		</ul>
		
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
			<button id="btnExpand" type="button" class="btnPwc btnS">확대</button>
		</div>
	</div>
	
	<div id="budget-grid" class="gridbox gridHp07" style="width:100%;height:100%"></div>
</div><!-- //boxWhite -->
<script type="text/javascript">

<c:if test="${result.data.canEdit}">
const profitPopup = Helper.Popup("profit", "Budget Hour기준 Project 예상수익", "/popup/profitability", {prjtCd:"${result.data.prjtCd}", totBdgtHour:${result.data.totMembBdgt}, netRvnu:${result.data.netRvnu}, expCm:${result.data.expCm}});
const conflictPopup = Helper.Popup("conflict", "Conflict 조회", "/popup/conflict", {size:"L", prjtCd:"${result.data.prjtCd}", sDt:"${result.data.prjtFrdt}", eDt:"${result.data.prjtTodt}"});
</c:if>
<c:if test="${flagHistory}">
const historyPopup = Helper.Popup("history", "History 보기", "/popup/history", {size:"L", prjtCd:"${result.data.prjtCd}"})
</c:if>
<c:if test="${flagRjReason}">
const rejectOpinionPopupRead = Helper.Popup("rjOpinionRead", "반려의견 확인", "/popup/rjopinionread", {prjtCd:"${result.data.prjtCd}"});
</c:if>
<c:if test="${flagEdit}">
const excelImportPopup = Helper.Popup("excelImport", "Import Excel", "/popup/fileUploader", {size:"S", importUrl:"/excel/import/budgetHours", prjtCd:"${result.data.prjtCd}", allowExtensions:["xlsx", "xls"]});
</c:if>

<c:if test="${flagRetain && result.data.profRetainTranYn == 'Y' }">
const retainPopup = Helper.Popup('transToRetain', "Retain 등록", "/popup/retain", {size:"S", prjtCd:"${result.data.prjtCd}", prjtFrdt:"${result.data.prjtFrdt}", prjtTodt:"${result.data.prjtTodt}"});
</c:if>

$(document).ready(function(){
	const budgetGrid = {
			parent: "budget-grid",
			skin: THEME_INFO.THEME,
			image_path: THEME_INFO.THEME_IMG_PATH,
			columns: [
				{id:"tbd", label:["TBD", "#select_filter_strict"], type:"toggle", align:"center", sort:"str", width:"50"},
				{id:"actvNm", label:["Activity", "#select_filter_strict"], type:"ro", align:"left", sort:"str", width:"130", resize:false},
				{id:"locaNm", label:["Location", "#select_filter_strict"], type:"ro", align:"left", sort:"str", width:"130"},
				{id:"korNm", label:["Name", "#text_filter"], type:"ro", align:"left", sort:"str", width:"70"},
				{id:"emplNo", label:["사번", "#text_filter"], type:"ro", align:"center", sort:"str", width:"70"},
				{id:"gradNm", label:["Grade", "#select_filter_strict"], type:"ro", align:"left", sort:"str", width:"70"},
				{id:"wkmnsp", label:["숙련도" + GRID_INFO_WKMNSP, "#numeric_filter"], type:"ron", align:"right", sort:"int", width:"100"},
				{id:"totWkmnspAsgnTm", label:["숙련도 고려<br>투입시간", "#numeric_filter"], type:"ron", align:"right", sort:"int", width:"80"},
				{id:"totAsgnTm", label:["Total<br>Time", "#numeric_filter"], type:"ron", align:"right", sort:"int", width:"80"},
			<c:forEach var="sDt" items="${result.data.sDtOfWeek}" varStatus="weekStatus">
				{id:"week${weekStatus.count}", label:["${sDt.startDate}", "${sDt.workDay} days"], type:"ron", align:"right", width:"85"},
			</c:forEach>
			]
	};
	setTimeout(function(){
		let budgetGridObj = new dhtmlXGridObject(budgetGrid);
		budgetGridObj.setNumberFormat("0,000.00",budgetGridObj.getColIndexById("totAsgnTm"),".",",");
		budgetGridObj.setNumberFormat("0,000.00",budgetGridObj.getColIndexById("totWkmnspAsgnTm"),".",",");
		<c:forEach var="sDt" items="${result.data.sDtOfWeek}" varStatus="weekStatus">
		budgetGridObj.setNumberFormat("0,000.00",budgetGridObj.getColIndexById("week${weekStatus.count}"),".",",");
		</c:forEach>
		
		budgetGridObj.enableResizing("false");
		budgetGridObj.splitAt(9);
		
		bindEvent();
		loadData();
		
		
		function bindEvent(){
			$(document).on('click.gridResize', 'nav > button, .sumArea > .btnTog', function(e) {
		         e.preventDefault();
		         budgetGridObj.setSizes();
		    });
			
			$("#tabPrjtInfo").off('click').on('click', function(e){
				e.preventDefault();
				Helper.goPage("/pages/project/read", {prjtCd:"${result.data.prjtCd}"});
			});
			
			<c:if test="${flagEdit}">
			$("#btnEdit").off("click").on("click", function(){
				Helper.goPage("/pages/project/budget/edit", {prjtCd:"${result.data.prjtCd}"});
			})
			</c:if>
			
			$("#btnClose").off("click").on("click", function(){
				Helper.goPage("/pages/project/${result.data.formDiv}/list");
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
						Helper.post("/project/budget/reqELAprv", {prjtCd:"${result.data.prjtCd}"})
						.done(function(){
							Helper.goPage("/pages/project/budget/read", {prjtCd: "${result.data.prjtCd}"});
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
							Helper.goPage("/pages/project/budget/read", {prjtCd: "${result.data.prjtCd}"});
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
					Helper.goPage("/pages/project/budget/read", {prjtCd: "${result.data.prjtCd}"});
				})
			});
			</c:if>
			
			$("#btnExcelExport").off('click').on('click', function(){
				Helper.ConfirmBox(["엑셀 다운로드 하시겠습니까?", "[참고] Import 목적으로 다운로드 시 Activity ~ Total Time 까지의 컬럼은 수정하지 마세요."], function(isOk){
					let param = $.param({
						prjtCd: "${result.data.prjtCd}",
						prjtNm: "${result.data.prjtNm}",
						filename:"표준감사시간_Budget_Hour(${result.data.prjtCd})",
						writerType: "budget"
					});
					
					if(isOk) budgetGridObj.toExcel(EXCEL_WRITER_URL+"?"+param);	
				});
			});

			$("#btnExpand").on("click", function(e){
				const boxSelector = "#test-box";
				let $container = $("#container");
				let $contents = $("#contents");
				let $boxWhite = $(".boxWhite");
				let $titArea = $(".titArea");
				let $grid = $("#budget-grid");
				if($(boxSelector).length > 0){
					$container.css('height', '');
					$contents.css('height','');
					$boxWhite.append($titArea, $grid);
					$grid.addClass("gridHp07");
					$(boxSelector).remove();
					$(".optional-expand").hide();
					$(e.target).text("확대")
				}else{
					let $box = $("<div id='test-box' class='expandLayer'></div>");
					$container.css('height', '100%');
					$contents.css('height','100%');
					$boxWhite.append($box);
					$box.append($titArea , $grid);
					$box.append($grid);
					$grid.removeClass("gridHp07");
					$(".optional-expand").show();
					$(e.target).text("축소")
				}
				
				budgetGridObj.setSizes();
			});
		}
		
		function loadData(){
			budgetGridObj.clearAll();
			Helper.post("/project/budget/list", {prjtCd:"${result.data.prjtCd}"})
			.done(function(data){
				budgetGridObj.parse(data, "js");
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
