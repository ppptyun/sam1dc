<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<h1 class="hidden">Progress Review</h1>

<div class="boxWhite">
	<div class="searchBox">
		<dl class="item">
			<dt>EL/PM/RM/</dt>
			<dd class="frmNowrap"><input type="text" id="targetEmp" readonly><button type="button" id="btnSearchEmployee" class="btIco btnSearch02 gapL">임직원 검색</button></dd>
		</dl>
		<p class="txtInfo">Engagement Leader / Project Manager / Record Manager (퇴직자 포함)로 등록된 Project를 검색할 수 있습니다.</p>
		<div class="btns">
			<button type="button" id="btnSearchReviewList" class="btnPwc btnM action">Search</button>
		</div>
	</div>
	<div id="progress-review-grid" class="gridbox gridHp05">그리드 영역</div>
</div><!-- //boxWhite -->

<form id="">
	<input type="hidden" name="empno"/>
	<input type="hidden" name="kornm"/>
	<input type="hidden" name="gradnm"/>
</form>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/Monitoring.js"></script>
<script>
$(document).ready(function(){
	
	const progressGridDefinition = {
			parent: "progress-review-grid",
			skin: THEME_INFO.THEME,
			image_path: THEME_INFO.THEME_IMG_PATH,
			columns: [
 				{id:"C0", label:["", "", ""], type:"ro", align: "left", width: "0"},
				
				{id:"PRJTNM", label:["Project Name", "#rspan", "#text_filter"], type:"ro", align: "left", width: "300"},
				{id:"PRJTCD", label:["Project Code", "#rspan", "#text_filter"], type:"ro", align: "center", width: "95"},
				{id:"BUDGETTIME", label:["Total<br>Budget Hour (C)", "#rspan", "#number_filter"], type:"ron", align: "right", width: "110"},
				{id:"ACTUAL_TIME", label:["Actual<br>누적 Time", "#rspan", "#number_filter"], type:"ron", align: "right", width: "110"},
				{id:"PROGRESS", label:["Progress", "#rspan", "#number_filter"], type:"ron", align: "right", width: "85"},
				
				{id:"C6", label:["배부 예산 실적집계", "RA<br>배부예산 (E)", "#number_filter"], type:"ron", align: "right", width: "110"},
				{id:"C7", label:["#cspan", "RA<br>Actual Time (F)", "#number_filter"], type:"ron", align: "right", width: "110"},
				{id:"C8", label:["#cspan", "Progress<br>(F/E)", "#number_filter"], type:"ron", align: "right", width: "85"},
				{id:"C9", label:["#cspan", "Fulcrum<br>배부예산 (G)", "#number_filter"], type:"ron", align: "right", width: "110"},
				{id:"C10", label:["#cspan", "Fulcrum<br>Actual Time (H)", "#number_filter"], type:"ron", align: "right", width: "110"},
				{id:"C11", label:["#cspan", "Progress<br>(H/G)", "#number_filter"], type:"ron", align: "right", width: "85"},
				{id:"C12", label:["#cspan", "Other<br>배부예산 (I)", "#number_filter"], type:"ron", align: "right", width: "110"},
				{id:"C13", label:["#cspan", "Other<br>Actual Time (J)", "#number_filter"], type:"ron", align: "right", width: "110"},
				{id:"C14", label:["#cspan", "잔여예산<br>(J-I)", "#number_filter"], type:"ron", align: "right", width: "110"},
				{id:"C15", label:["#cspan", "Progress<br>(J/I)", "#number_filter"], type:"ron", align: "right", width: "85"}
			]
	};
	
	const popEmp = Helper.Popup("employee", "임직원 선택", "/popup/org");
	
	let progressGrid = new dhtmlXGridObject(progressGridDefinition);
	
	progressGrid.setNumberFormat("0,000.00",progressGrid.getColIndexById("BUDGETTIME"),".",",");
	progressGrid.setNumberFormat("0,000.00",progressGrid.getColIndexById("ACTUAL_TIME"),".",",");
	progressGrid.setNumberFormat("0,000.00 %",progressGrid.getColIndexById("PROGRESS"),".",",");
	
	progressGrid.splitAt(6);
 	progressGrid.attachEvent("onXLE",function(){$(".loadingA").hide();});
	
	let RS = new ResizeSensor($('#contents'), function(){progressGrid.setSizes();});

	loadData = function() {
		progressGrid.clearAll();
		COMMON.showLoading();
		
		Helper.ajax({
			url: "/monitoring/list",
			success:function(data){
				progressGrid.parse(data, "js");
			}
		});
	}
	
	$("#btnSearchReviewList").off('click').on('click', function(){
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