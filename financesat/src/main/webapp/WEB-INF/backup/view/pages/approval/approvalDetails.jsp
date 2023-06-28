<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="now" value="<%=new java.util.Date()%>" />
<c:set var="roleCd" value="${sessionScope.SAMIL_SESSION_KEY.roleCd}" />

<c:if test="${result.status == 'fail'}">
<script>Helper.MessageBox("${result.msg}", function(){Helper.goPage("/");});</script>
</c:if>

<c:if test="${result.status == 'success'}">
<!-- 컨텐츠 시작 -->
<h1 class="hidden">Approval List</h1>
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
	<button id="btnProfit" type="button" class="btnPwc btnM action">수익성 분석</button>
	
	<c:if test="${result.data.stat == 'RJ'}">
		<button id="btnRjReason" type="button" class="btnPwc btnM action">반려의견 보기</button>
	</c:if>		
	<c:if test="${result.data.canEdit && (result.data.stat == 'RQ') }">
		<button id="btnApprove" type="button" class="btnPwc btnM action">승인</button>
		<button id="btnReject" type="button" class="btnPwc btnM action">반려</button>
	</c:if>	
	<button id="btnClose" type="button" class="btnPwc btnM action">Close</button>
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
		<button type="button" class="btnTog" aria-expanded="false">열기</button>
	</div>
	
	
	<div class="titArea marT20">
		<h2 class="titDep2">Budget Hour</h2>
		<ul class="txtInfo02">
			<li><em>New Staff 예측 투입시간</em> : <fmt:formatNumber value="${result.data.newStfBdgtTm}" pattern="#,##0.00" /> hr * <fmt:formatNumber value="${result.data.newStfWkmnsp}"/> (숙련도) = <span><fmt:formatNumber value="${result.data.newStfBdgtTm * result.data.newStfWkmnsp}" pattern="#,##0.00"/></span> hr </li>
			<li><em>Other(QC 등) 투입시간</em> : <fmt:formatNumber value="${result.data.otherBdgtTm}" pattern="#,##0.00" />  hr * <fmt:formatNumber value="${result.data.otherWkmnsp}"/> (숙련도) = <span><fmt:formatNumber value="${result.data.otherBdgtTm * result.data.otherWkmnsp}" pattern="#,##0.00"/></span> hr </li>
			<li><em>RA 배부예산</em> : <fmt:formatNumber value="${result.data.raBdgtTm}" pattern="#,##0.00" /> hr * <fmt:formatNumber value="${result.data.baseWkmnsp}"/> (<c:if test="${result.data.baseWkmnsp != 1}">기준 </c:if>숙련도) = <span><fmt:formatNumber value="${result.data.raBdgtTm * result.data.baseWkmnsp}" pattern="#,##0.00"/></span> hr </li>
			<li><em>Fulcrum 배부예산</em> : <fmt:formatNumber value="${result.data.flcmBdgtTm}" pattern="#,##0.00" /> hr * <fmt:formatNumber value="${result.data.baseWkmnsp}"/> (<c:if test="${result.data.baseWkmnsp != 1}">기준 </c:if>숙련도) = <span><fmt:formatNumber value="${result.data.flcmBdgtTm * result.data.baseWkmnsp}" pattern="#,##0.00"/></span> hr </li>
			<li class="optional-expand" style="display:none"><em>Total Budget Hour</em> : <fmt:formatNumber value="${result.data.totMembBdgt}" pattern="#,##0.00" /> hr</li>
		</ul>
		
		<div class="btnArea side">
			<button id="btnExpand" type="button" class="btnPwc btnS">확대</button>
		</div>
	</div>
	
	<div id="budget-grid" class="gridbox gridHp07" style="width:100%;height:100%;"></div>
</div><!-- //boxWhite -->
<script type="text/javascript">
const profitPopup = Helper.Popup("profit", "Budget Hour기준 Project 예상수익", "/popup/profitability", {prjtCd:"${result.data.prjtCd}", totBdgtHour:${result.data.totMembBdgt}, netRvnu:${result.data.netRvnu}, expCm:${result.data.expCm}});
const rejectOpinionPopupInput = Helper.Popup("rjOpinion", "반려의견 등록", "/popup/rjopinion", {prjtCd:"${result.data.prjtCd}", prjtNm:"${result.data.prjtNm}"});
const rejectOpinionPopupRead = Helper.Popup("rjOpinionRead", "반려의견 확인", "/popup/rjopinionread", {prjtCd:"${result.data.prjtCd}"});


$(document).ready(function(){
	const budgetGrid = {
			parent: "budget-grid",
			skin: THEME_INFO.THEME,
			image_path: THEME_INFO.THEME_IMG_PATH,
			columns: [
				{id:"tbd", label:["TBD", "#select_filter_strict"], type:"toggle", align:"center", sort:"str", width:"50"},
				{id:"actvNm", label:["Activity", "#select_filter_strict"], type:"ro", align:"left", sort:"str", width:"130"},
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
		budgetGridObj.splitAt(8);
		budgetGridObj.setNumberFormat("0,000.00",budgetGridObj.getColIndexById("totWkmnspAsgnTm"),".",",");
		<c:forEach var="sDt" items="${result.data.sDtOfWeek}" varStatus="weekStatus">
		budgetGridObj.setNumberFormat("0,000.00",budgetGridObj.getColIndexById("week${weekStatus.count}"),".",",");
		</c:forEach>
	
	loadData();
	bindEvent();
	
	
	function bindEvent(){
		$(document).on('click.gridResize', 'nav > button, .sumArea > .btnTog', function(e) {
	         e.preventDefault();
	         budgetGridObj.setSizes();
	    });
		
		$("#tabPrjtInfo").off('click').on('click', function(e){
			e.preventDefault();
			Helper.goPage("/pages/approval/info", {prjtCd:"${result.data.prjtCd}"});
		});
		
		$("#btnApprove").off("click").on("click", function(){
			
			
			Helper.ConfirmBox("승인하시겠습니까?", "", function(isOK){
				if(isOK) {
					Helper.post("/approval/approve", {prjtCd:"${result.data.prjtCd}", prjtNm:"${result.data.prjtNm}"})
					.done(function(){
						Helper.MessageBox(["승인 완료되었습니다."]);
						Helper.goPage("/pages/approval/list");
					});
				}
			});
			
		});
		
		$("#btnReject").off("click").on("click", function(){
			rejectOpinionPopupInput.openPopup("rjopinionsubmit");	
		});
		
		$("#btnClose").off("click").on("click", function(){
			Helper.goPage("/pages/approval/list");
		});		
		
		$("#btnRjReason").off("click").on("click", function(){
			rejectOpinionPopupRead.openPopup("rjopinion");	
		})
		
		$("#btnProfit").off("click").on("click", function(){
			profitPopup.openPopup("profitability");	
		})
		
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
				let $box = $("<div id='test-box' style='background-color:white;position:absolute;top:0px;left:30px;width: calc(100% - 60px);height: calc(100% - 130px);z-index:99999'></div>");
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
	
	
	},100)
	
	
});
</script>

<!-- //컨텐츠 끝 -->
</c:if>   
