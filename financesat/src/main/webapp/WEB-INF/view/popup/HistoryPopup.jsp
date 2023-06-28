<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:choose>
	<c:when test="${result.status == 'success'}">
	<div class="layCont">
		<ul class="txtInfo02">
			<li><em>New Staff 예측 투입시간</em> : <fmt:formatNumber value="${result.data.newStfBdgtTm}" pattern="#,##0.00" /> hr * <fmt:formatNumber value="${result.data.newStfWkmnsp}"/> (숙련도) = <span><fmt:formatNumber value="${result.data.newStfBdgtTm * result.data.newStfWkmnsp}" pattern="#,##0.00"/></span> hr </li>
			<li><em>Other 투입시간</em> : <fmt:formatNumber value="${result.data.otherBdgtTm}" pattern="#,##0.00" />  hr * <fmt:formatNumber value="${result.data.otherWkmnsp}"/> (숙련도) = <span><fmt:formatNumber value="${result.data.otherBdgtTm * result.data.baseWkmnsp}" pattern="#,##0.00"/></span> hr </li>
			<li><em>RA 배부예산</em> : <fmt:formatNumber value="${result.data.raBdgtTm}" pattern="#,##0.00" /> hr * <fmt:formatNumber value="${result.data.baseWkmnsp}"/> (숙련도) = <span><fmt:formatNumber value="${result.data.raBdgtTm * result.data.baseWkmnsp}" pattern="#,##0.00"/></span> hr </li>
			<li><em>Fulcrum 배부예산</em> : <fmt:formatNumber value="${result.data.flcmBdgtTm}" pattern="#,##0.00" /> hr * <fmt:formatNumber value="${result.data.baseWkmnsp}"/> (숙련도) = <span><fmt:formatNumber value="${result.data.flcmBdgtTm * result.data.baseWkmnsp}" pattern="#,##0.00"/></span> hr </li>
		</ul>
		<div class="layBtn">
			<button id="popBtnExcelExport" type="button" class="btnPwc btnM action">Excel Export</button>
		</div>
		<div id="pop-history-grid" class="gridbox gridHp04">그리드 영역</div>
	</div>
	<script type="text/javascript">
	$(document).ready(function(){
		// grid
		const histGrid = {
			parent: "pop-history-grid",
			skin: THEME_INFO.THEME,
			image_path: THEME_INFO.THEME_IMG_PATH,
			columns: [
				{id:"actvNm", label:["Activity", "#select_filter_strict"], type:"ro", align:"left", sort:"str", width:"130"},
				{id:"locaNm", label:["Location", "#select_filter_strict"], type:"ro", align:"left", sort:"str", width:"130"},
				{id:"korNm", label:["Name", "#text_filter"], type:"ro", align:"left", sort:"str", width:"70"},
				{id:"emplNo", label:["사번", "#text_filter"], type:"ro", align:"center", sort:"str", width:"70"},
				{id:"gradNm", label:["Grade", "#select_filter_strict"], type:"ro", align:"left", sort:"str", width:"70"},
				{id:"wkmnsp", label:["숙련도", "#numeric_filter"], type:"ron", align:"right", sort:"int", width:"80"},
				{id:"totWkmnspAsgnTm", label:["숙련도 고려<br>투입시간", "#numeric_filter"], type:"ron", align:"right", sort:"int", width:"80"},
				{id:"totAsgnTm", label:["Total<br>Time", "#numeric_filter"], type:"ron", align:"right", sort:"int", width:"80"},
			<c:forEach var="sDt" items="${result.data.sDtOfWeek}" varStatus="weekStatus">
				{id:"week${weekStatus.count}", label:["${sDt.startDate}", "${sDt.workDay} days"], type:"ron", align:"right", width:"85"},
			</c:forEach>
			]
		};
		
		let histGridObj = new dhtmlXGridObject(histGrid);
		histGridObj.splitAt(8);
		histGridObj.setNumberFormat("0,000.00",histGridObj.getColIndexById("totWkmnspAsgnTm"),".",",");
		<c:forEach var="sDt" items="${result.data.sDtOfWeek}" varStatus="weekStatus">
		histGridObj.setNumberFormat("0,000.00",histGridObj.getColIndexById("week${weekStatus.count}"),".",",");
		</c:forEach>
		
		bindEvent();
		loadData();
		
		function bindEvent(){
			$("#popBtnExcelExport").off('click').on('click', function(){
				let paramStr = $.param({
					filename: '표준감사시간_Budget_History(${result.data.prjtCd})'
				});
				
				histGridObj.toExcel(EXCEL_WRITER_URL+"?" + paramStr);
			});
		}
		function loadData(){
			histGridObj.clearAll();
			Helper.post("/popup/history/budget/list", {prjtCd:"${result.data.prjtCd}"})
			.done(function(data){
				histGridObj.parse(data, "js");
			});
		}
		
		
	});
	</script>
	</c:when>
	<c:otherwise>
	<div class="layCont">
		<div class="gridHp02">데이터가 존재 하지 않습니다.</div>
	</div>
	</c:otherwise>
</c:choose>





