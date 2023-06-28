<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<h1 class="hidden">Retain / T-Link</h1>

<div class="boxWhite">
	<div class="searchBox">
		<dl class="item">
			<dt>EL/PM/RM</dt>
			<dd class="frmNowrap"><input type="text" id="targetEmp" readonly><button type="button" id="btnSearchEmployee" class="btIco btnSearch02 gapL">임직원 검색</button></dd>
		</dl>
		<dl class="item">
			<dt>Period</dt>
			<dd class="calendarBox">
				<div class="calenWrap">
					<input type="text" readonly title="시작일">
				</div>
				<span class="dash">~</span>
				<div class="calenWrap">
					<input type="text" readonly title="종료일">
				</div>
			</dd>
		</dl>
		<p class="txtInfo">Engagement Leader / Project Manager / Record Manager (퇴직자 포함)로 등록된 Project를 검색할 수 있습니다.</p>
		<div class="btns">
			<button type="button" id="btnSearchRetainLnkList" class="btnPwc btnM action">Search</button>
		</div>
	</div>
	<div id="retain-tlink-grid" class="gridbox gridHp05">그리드 영역</div>
</div><!-- //boxWhite -->

<form id="">
	<input type="hidden" name="empno"/>
	<input type="hidden" name="kornm"/>
	<input type="hidden" name="gradnm"/>
</form>

<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/dhtmlxCustom.js"></script> --%>

<script>
$(document).ready(function(){

	const retainTlinkGridDefinition = {
			parent: "retain-tlink-grid",
			skin: THEME_INFO.THEME,
			image_path: THEME_INFO.THEME_IMG_PATH,
			columns: [
				{id:"PRJTNM", label:["Project Name", "#rspan", "#text_filter"], type:"ro", align: "left", width: "300"},
				{id:"PRJTCD", label:["Project Code", "#rspan", "#text_filter"], type:"ro", align: "center", width: "95"},
				{id:"C3", label:["Total<br>Budget Hour(A)", "#rspan", "#text_filter"], type:"ron", align: "right", width: "105"},
				{id:"C4", label:["Daily Total<br>Retain Hour(B)", "#rspan", "#text_filter"], type:"ron", align: "right", width: "105"},
				{id:"C5", label:["차이(B-A)", "#rspan", "#text_filter"], type:"ron", align: "right", width: "105"},
				{id:"C6", label:["Retain 반영률 (B/A)", "#rspan", "#text_filter"], type:"ron", align: "right", width: "105"},
				{id:"C7", label:["조회기간", "#rspan", "#text_filter"], type:"ron", align: "right", width: "105"},
				{id:"C8", label:["조회기간", "Budget Hour (C )", "#text_filter"], type:"ron", align: "right", width: "105"},
				{id:"C9", label:["#cspan", "Retain Hour (D)", "#text_filter"], type:"ron", align: "right", width: "105"},
				{id:"ACTUAL_TIME", label:["#cspan", "Actual Time (E )", "#text_filter"], type:"ron", align: "right", width: "105"},
				{id:"C11", label:["#cspan", "Retain / Budget (%)", "#text_filter"], type:"ron", align: "right", width: "95"},
				{id:"C12", label:["#cspan", "Actual / Retain (%)", "#text_filter"], type:"ron", align: "right", width: "95"}
			]
	};
	
	const popEmp = Helper.Popup("employee", "임직원 선택", "/popup/org");
	
	let retainTlinkGrid = new dhtmlXGridObject(retainTlinkGridDefinition);
	
	retainTlinkGrid.setNumberFormat("0,000.00",retainTlinkGrid.getColIndexById("ACTUAL_TIME"),".",",");
	retainTlinkGrid.setNumberFormat("0,000.00 %",retainTlinkGrid.getColIndexById("C6"),".",",");
	retainTlinkGrid.attachEvent("onXLE",function(){$(".loadingA").hide();});
	
	let RS = new ResizeSensor($('#contents'), function(){retainTlinkGrid.setSizes();});
	
	loadData = function() {
		retainTlinkGrid.clearAll();
		COMMON.showLoading();
		
		Helper.ajax({
			url: "/monitoring/sat",
			success:function(data){
				retainTlinkGrid.parse(data, "js");
			}
		});
	}
	
	$("#btnSearchRetainLnkList").off('click').on('click', function(){
		loadData();
	});
	
	$("#btnSearchEmployee").off('click').on('click', function(){
		popEmp.openPopup('empSearch', function(data){
			$('input[name="empno"]').val(data.emplno);
			$('input[name="kornm"]').val(data.kornm);
			$('input[name="gradnm"]').val(data.gradnm);
			$("#targetEmp").val(data.kornm + "(" + data.emplno + ") / " + data.gradnm);
		});
	});
});
</script>