<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<h1 class="hidden">표준감사시간 관리</h1>
<div id="stdAdtTime" class="boxWhite">
	<div style="display:flex;justify-content:space-between">
		<div>
			<span>결산년도: </span>
			<div id="year_selectbox" class="selectbox" style="width:6.25em">
				<select name="year">
					<c:forEach items="${yearMonitoringList}" var="year">
						<option value="${year}">${year}년</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="btnArea">
			<button type="button" id="btnExcelExport" class="btnPwc btnM">Excel Export</button>
		</div>
	</div>
	<p class="txtInfo">예상 표준감사시간은 조회기준일 현재 팀의 숙련도에 기초하여 산정되므로, 연차별 발생시간의 정도에 따라 변경될 수 있습니다. 최종 표준감사시간은 외감실시내역 작성단계에서 확정됩니다.</p>
	<div id="std-mng-grid" class="gridbox gridHp05" style="width:100%;height:100%"></div>
</div>

<form id="">
	<input type="hidden" name="empno"/>
	<input type="hidden" name="kornm"/>
	<input type="hidden" name="gradnm"/>
</form>

<script>
$(document).ready(function(){setTimeout(function(){
	/* Grid Define*/
	const stdMngGridDefinition = {
			parent: "std-mng-grid",
			skin: THEME_INFO.THEME,
			image_path: THEME_INFO.THEME_IMG_PATH,
			columns: [
				{id:"PRJTNM", label:["Project Name", "#text_filter"], type:"ro", sort:"str", align: "left"},
				{id:"PRJTCD", label:["Project Code", "#text_filter"], type:"ro", sort:"str", align: "center", width: "125"},
				{id:"EL", label:["EL", "#select_filter"], type:"ro", sort:"str", align: "center", width: "100"},
				{id:"PM", label:["PM", "#select_filter"], type:"ro", sort:"str", align: "center", width: "100"},
				{id:"ET_DFN_SAT", label:[Helper.getGridTitleAndInfo("한공회<br>표준감사시간", GRID_INFO_STA_ET_DFN_SAT), "#number_filter"], type:"ron", sort:"int", align: "right", width: "115"},
				{id:"BASE_WKMNSP", label:[Helper.getGridTitleAndInfo("기준숙련도", GRID_INFO_STA_BASE_WKMNSP), "#number_filter"], type:"ron", sort:"int", align: "right", width: "115"},
				{id:"WKMNSP_TEAM", label:[Helper.getGridTitleAndInfo("팀의숙련도", GRID_INFO_STA_WKMNSP_TEAM, 'right'), "#number_filter"], type:"ron", sort:"int", align: "right", width: "115"},
				{id:"WKMNSP_SAT_TEAM", label:[Helper.getGridTitleAndInfo("예상<br>표준감사시간" , GRID_INFO_STA_WKMNSP_SAT_TEAM, 'right'), "#number_filter"], type:"ron", sort:"int", align: "right", width: "115"},
				{id:"ACTUAL_TIME", label:[Helper.getGridTitleAndInfo("Actual Time", GRID_INFO_STA_ACTUAL_TIME, 'right'), "#number_filter"], type:"ron", sort:"int", align: "right", width: "115"},
				{id:"PROGRESS", label:[Helper.getGridTitleAndInfo("달성률", GRID_INFO_STA_PROGRESS, 'right'), "#number_filter"], type:"ron", sort:"int", align: "right", width: "115"}
// 				{id:"WKMNSP_SAT", label:["기준숙련도 기준 <br>표준감사시간", "#number_filter"], type:"ron", sort:"int", align: "right", width: "150"},
// 				{id:"REGIST_RETIAN_TIME", label:["잔여 Retain Hour <br>(팀의 숙련도 기준)", "#number_filter"], type:"ron", align: "right", width: "135"},
// 				{id:"LACK_RETAIN_TIME", label:["추가 Retain <br>반영 필요시간<br>(팀의 숙련도 기준)", "#number_filter"], type:"ron", align: "right", width: "135"}
			]
	};
	
	let stdMngGrid = new dhtmlXGridObject(stdMngGridDefinition);
	stdMngGrid.enableSmartRendering(true);
	stdMngGrid.setAwaitedRowHeight(45);
	stdMngGrid.setStyle("text-align:center", "", "", "");
	stdMngGrid.setNumberFormat("0,000",stdMngGrid.getColIndexById("ET_DFN_SAT"),".",",");
	stdMngGrid.setNumberFormat("0,000.000",stdMngGrid.getColIndexById("BASE_WKMNSP"),".",",");
	stdMngGrid.setNumberFormat("0,000.000",stdMngGrid.getColIndexById("WKMNSP_TEAM"),".",",");
	stdMngGrid.setNumberFormat("0,000",stdMngGrid.getColIndexById("WKMNSP_SAT_TEAM"),".",",");
	stdMngGrid.setNumberFormat("0,000",stdMngGrid.getColIndexById("ACTUAL_TIME"),".",",");
	stdMngGrid.setNumberFormat("0,000.00 %",stdMngGrid.getColIndexById("PROGRESS"),".",",");

	
	stdMngGrid.attachEvent("onXLE",function(){$(".loadingA").hide();});
	
    //Grid Information
    /* $("#std-mng-grid .xhdr table tbody tr td").eq(4).children("div").css('padding-right', '20px');
    $("#std-mng-grid .xhdr table tbody tr td").eq(5).children("div").css('padding-right', '20px');
    $("#std-mng-grid .xhdr table tbody tr td").eq(6).children("div").css('padding-right', '20px');
    $("#std-mng-grid .xhdr table tbody tr td").eq(7).children("div").css('padding-right', '20px');
    $("#std-mng-grid .xhdr table tbody tr td").eq(8).children("div").css('padding-right', '20px');
    $("#std-mng-grid .xhdr table tbody tr td").eq(9).children("div").css('padding-right', '20px'); */
//     $("#std-mng-grid .xhdr table tbody tr td").eq(10).children("div").css('padding-right', '30px');
//     $("#std-mng-grid .xhdr table tbody tr td").eq(11).children("div").css('padding-right', '30px');
    
    /* $("#std-mng-grid .xhdr table tbody tr td").eq(4).children("div").append(GRID_INFO_BOX_LEFT.replace(GRID_INFO, GRID_INFO_STA_ET_DFN_SAT));
    $("#std-mng-grid .xhdr table tbody tr td").eq(5).children("div").append(GRID_INFO_BOX_LEFT.replace(GRID_INFO, GRID_INFO_STA_BASE_WKMNSP));
    $("#std-mng-grid .xhdr table tbody tr td").eq(6).children("div").append(GRID_INFO_BOX_RIGHT.replace(GRID_INFO, GRID_INFO_STA_WKMNSP_TEAM));
    $("#std-mng-grid .xhdr table tbody tr td").eq(7).children("div").append(GRID_INFO_BOX_RIGHT.replace(GRID_INFO, GRID_INFO_STA_WKMNSP_SAT_TEAM));
    $("#std-mng-grid .xhdr table tbody tr td").eq(8).children("div").append(GRID_INFO_BOX_RIGHT.replace(GRID_INFO, GRID_INFO_STA_ACTUAL_TIME));
    $("#std-mng-grid .xhdr table tbody tr td").eq(9).children("div").append(GRID_INFO_BOX_RIGHT.replace(GRID_INFO, GRID_INFO_STA_PROGRESS)); */

    /* $("#std-mng-grid .xhdr table tbody tr").eq(2).children("td").addClass("lineL"); */
    
    /* var yearSelect = $('#year_selectbox > a').getInstance();
	loadData(yearSelect.getValueSB()); */
	loadData();
	
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
	$(document).on('click.gridResize', 'nav > button', function(e) { e.preventDefault(); stdMngGrid.setSizes();});
	
	/* $("#year_selectbox").on('change.selectbox', function(e, param) {
        if (param === undefined) return;
        loadData(param.value);
    }); */
	
	$("#btnExcelExport").off('click').on('click', function(){
		let param = $.param({
			filename:'표준감사시간'
		});
		stdMngGrid.toExcel(EXCEL_WRITER_URL+"?" + param);
	});
}, 10)});
</script>