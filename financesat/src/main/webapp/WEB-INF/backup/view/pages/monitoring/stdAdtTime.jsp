<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<h1 class="hidden">표준감사시간 관리</h1>
<div id="stdAdtTime" class="boxWhite">
	
	<div class="titArea">
		<!-- <p class="txtInfo">예상 표준감사시간은 조회기준일 현재 팀의 숙련도에 기초하여 산정되므로, 연차별 발생시간의 정도에 따라 변경될 수 있습니다. 최종 표준감사시간은 외감실시내역 작성단계에서 확정됩니다.</p> -->
		<div>
			<span>결산년도: </span>
			<div class="selectbox" style="width:6.25em">
				<select name="year">
					<option value="">전체</option>
					<c:forEach items="${yearMonitoringList}" var="year"  varStatus ="stat" >
						<option value="${year}" ${stat.index == 0 ? 'selected' : ''}>${year}년</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="btnArea side">
			<button type="button" id="btnExcelExport" class="btnPwc btnM">Excel Export</button>
		</div>
	</div>

	<div id="std-mng-grid" class="gridbox gridHp05" style="width:100%;height:100%"></div>
</div>

<form id="">
	<input type="hidden" name="empno"/>
	<input type="hidden" name="kornm"/>
	<input type="hidden" name="gradnm"/>
</form>

<script>

$(document).ready(function(){setTimeout(function(){
	let yearSelector = $('div.selectbox > a').getInstance();
	let year = yearSelector.getValueSB();
	
	/* Grid Define*/
	const stdMngGridDefinition = {
			parent: "std-mng-grid",
			skin: THEME_INFO.THEME,
			image_path: THEME_INFO.THEME_IMG_PATH,
			columns: [
				{id:"prjtnm", label:["Project Name", "#text_filter"], type:"ro", sort:"str", align: "left"},
				{id:"prjtcd", label:["Project Code", "#text_filter"], type:"ro", sort:"str", align: "center", width: "125"},
				{id:"chargptrNm", label:["EL", "#text_filter"], type:"ro", sort:"str", align: "center", width: "100"},
				{id:"chargmgrNm", label:["PM", "#text_filter"], type:"ro", sort:"str", align: "center", width: "100"},
				{id:"etDfnSat", label:[Helper.getGridTitleAndInfo("한공회<br>표준감사시간", GRID_INFO_STA_ET_DFN_SAT), "#number_filter"], type:"ron", sort:"int", align: "right", width: "115"},
				{id:"cntrtAdtTm", label:["합의시간(외감)", "#number_filter"], type:"ron", sort:"int", align: "right", width: "115"},
				{id:"actualTime", label:[Helper.getGridTitleAndInfo("Actual Time", GRID_INFO_STA_ACTUAL_TIME, 'right'), "#number_filter"], type:"ron", sort:"int", align: "right", width: "115"},
				{id:"complRate", label:[Helper.getGridTitleAndInfo("달성률", GRID_INFO_STA_PROGRESS, 'right'), "#number_filter"], type:"ron", sort:"int", align: "right", width: "115"},
				{id:"baseWkmnsp", type:"ron", align: "right"},
				{id:"teamWkmnsp", type:"ron", align: "right"},				
			]
	};
	
	let stdMngGrid = new dhtmlXGridObject(stdMngGridDefinition);
	stdMngGrid.enableSmartRendering(true);
	stdMngGrid.setAwaitedRowHeight(45);
	stdMngGrid.setColumnHidden(stdMngGrid.getColIndexById("baseWkmnsp"),true);
	stdMngGrid.setColumnHidden(stdMngGrid.getColIndexById("teamWkmnsp"),true);

	stdMngGrid.setStyle("text-align:center", "", "", "");
	stdMngGrid.setNumberFormat("0,000",stdMngGrid.getColIndexById("etDfnSat"),".",",");
	stdMngGrid.setNumberFormat("0,000",stdMngGrid.getColIndexById("cntrtAdtTm"),".",",");
	stdMngGrid.setNumberFormat("0,000",stdMngGrid.getColIndexById("actualTime"),".",",");
	stdMngGrid.setNumberFormat("0,000.00 %",stdMngGrid.getColIndexById("complRate"),".",",");	
	stdMngGrid.setNumberFormat("0,000.000",stdMngGrid.getColIndexById("baseWkmnsp"),".",",");
	stdMngGrid.setNumberFormat("0,000.000",stdMngGrid.getColIndexById("teamWkmnsp"),".",",");
	
	stdMngGrid.attachEvent("onXLE",function(){$(".loadingA").hide();});
	
	
	loadData(year);
	
	/* Function */
	function loadData(year) {
		stdMngGrid.clearAll();
		$("#UPDATE_DATE").text("기준일자: ");
		
		COMMON.showLoading();
		
		Helper.ajax({
			url: "/monitoring/sat",
			data: {
				year: year
			},
			success:function(data){
				if(data.length > 0){
					grid_noData("std-mng-grid", false);
					stdMngGrid.parse(data, "js");
				}else{
					grid_noData("std-mng-grid", true, "모니터링 대상 프로젝트가 없습니다.<br>결재가 완료된 프로젝트만 조회 가능합니다.");
					COMMON.hideLoading();
				}
				
				
			}
		});
	}
	
	/* Event Binding */
	$("[name='year']").closest('.selectbox').on('change.selectbox', function(e, param) {
           if (param === undefined) {
               return;
           }
           if(year != param.value){
        	   year = param.value;
               loadData(year);
           }
       });
	$(document).on('click.gridResize', 'nav > button', function(e) { e.preventDefault(); stdMngGrid.setSizes();});
	
	$("#btnExcelExport").off('click').on('click', function(){
		let param = $.param({
			filename:'표준감사시간 Monitoring'
		});
		stdMngGrid.setColumnHidden(stdMngGrid.getColIndexById("baseWkmnsp"),false);
		stdMngGrid.setColumnHidden(stdMngGrid.getColIndexById("teamWkmnsp"),false);
		stdMngGrid.setColLabel(stdMngGrid.getColIndexById("baseWkmnsp"), "기준 숙련도");
		stdMngGrid.setColLabel(stdMngGrid.getColIndexById("teamWkmnsp"), "팀 숙련도");
		stdMngGrid.toExcel(EXCEL_WRITER_URL+"?" + param);
		stdMngGrid.setColumnHidden(stdMngGrid.getColIndexById("baseWkmnsp"),true);
		stdMngGrid.setColumnHidden(stdMngGrid.getColIndexById("teamWkmnsp"),true);
		stdMngGrid.setColLabel(stdMngGrid.getColIndexById("baseWkmnsp"), "");
		stdMngGrid.setColLabel(stdMngGrid.getColIndexById("teamWkmnsp"), "");
	});
}, 10)});
</script>