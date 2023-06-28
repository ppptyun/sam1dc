<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="roleCd" value="${sessionScope.SAMIL_SESSION_KEY.roleCd}" />
<c:set var="development" value="${(env == 'development')}" />

<h1 class="hidden">Project List</h1>
<style>
#wrapper > #container{height:100% !important}
#prjtList {height: 100%;}
#project-grid {
	height: calc( 100% - 40px ) !important;
}
</style>
<div id="prjtList" class="boxWhite">
	<div style="display:flex;justify-content:space-between">
		<div>
			<c:if test="${development}">
			<span><span style="color:red">(개발중)</span>결산년도: </span>
			<div class="selectbox" style="width:6.25em">
				<select name="year">
					<option value="">전체</option>
					<c:forEach items="${yearListV1}"	var="year">
						<option value="${year}">${year}년</option>
					</c:forEach>
				</select>
			</div>
			</c:if>
		</div>
		<div class="btnArea">
			<button id="btnExcelExport" type="button"  class="btnPwc btnM">Excel Export</button>
			<button id="btnExcelExport2" type="button"  class="btnPwc btnM">기간별 다운로드</button>
		<c:if test="${roleCd=='sysadmin'}">
			<button id="btnReCalculate" type="button"  class="btnPwc btnM">표준감사시간 재계산</button>
			<c:if test="${development}">
				<button id="btnRetain" type="button"  class="btnPwc btnM">Retain 일괄 전송<span style="color:red">(TEST용)</span></button>
			</c:if>
		</c:if>
		</div>
	</div>
	<div id="project-grid" class="gridbox gridHp01" style="width:100%;height:100%"></div>
</div>

<script type="text/javascript">
const popDownload = Helper.Popup("excelDownloadByAuth", "기간별 다운로드", "/popup/download-by-auth", {size:'L'});
const initPageConfig = {
	year: "",
	selectedRowId: ""
}
function setSessionStorageData(data){
	if(!data) data = Object.assign({}, initPageConfig);
	SessionStorageCtrl.setData("prjtlist-pageConfig", data);
}

function getSessionStorageData(){
	let data = SessionStorageCtrl.getData("prjtlist-pageConfig");
	if(data) return data; 
	return Object.assign({}, initPageConfig);
}

// grid 버튼 이벤트 처리
function openPrjt(prjtCd){
	let pageConfig = getSessionStorageData()
	pageConfig.selectedRowId = prjtCd
	setSessionStorageData(pageConfig);
	Helper.goPage("/pages/project/read", {prjtCd:prjtCd});
}
function openBdgt(prjtCd){
	let pageConfig = getSessionStorageData()
	pageConfig.selectedRowId = prjtCd
	setSessionStorageData(pageConfig);
	Helper.goPage("/pages/project/budget/read", {prjtCd:prjtCd})
}

$(document).ready(function(){setTimeout(function(){
	let pageConfig = getSessionStorageData();
	
	let instance = $("[name='year']").closest('.selectbox').find('a').getInstance();
	if(instance && instance.getValueSB() != pageConfig.year){
		instance.setValueSB(pageConfig.year);	
	}
	
	const prjtGrid = {
			parent: "project-grid",
			skin: THEME_INFO.THEME,
			image_path: THEME_INFO.THEME_IMG_PATH,
			columns: [
				{id:"prjtCd", label:["Project<br>Code", "#text_filter"], type:"ro", align:"center", sort:"str", width:"95"},
				{id:"prjtNm", label:["프로젝트 명", "#text_filter"], type:"ro", align:"left", sort:"str"},
				{id:"chargPtrNm", label:["EL", "#select_filter"], type:"ro", align:"left", sort:"str", width:"60"},
				{id:"chargMgrNm", label:["PM", "#select_filter"], type:"ro", align:"left", sort:"str", width:"60"},
				{id:"planSat", label:[Helper.getGridTitleAndInfo("계획단계<br>표준감사<br>시간(a)", GRID_INFO_PLAN_SAT), "#numeric_filter"], type:"ron", align:"right", sort:"int", width:"105"},
				{id:"totBdgt", label:[Helper.getGridTitleAndInfo("Total Budget<br>Hours(b)", GRID_INFO_TOT_BUDGET), "#numeric_filter"], type:"ron", align:"right", sort:"int", width:"120"},
				{id:"etTrgtAdtTm", label:[Helper.getGridTitleAndInfo("감사계약시<br>합의 시간<br>(표준감사시간대상)", GRID_INFO_ET_TRGT_ADT_TM), "#numeric_filter"], type:"ron", align:"right", sort:"int", width:"150"},
				{id:"raBdgtTm", label:["RA<br>배부예산", "#numeric_filter"], type:"ron", align:"right", sort:"int", width:"70"},
				{id:"flcmBdgtTm", label:["Fulcrum<br>배부예산", "#numeric_filter"], type:"ron", align:"right", sort:"int", width:"75"},
				{id:"achvRate", label:["달성률<br>(b/a)", "#numeric_filter"], type:"ron", align:"right", sort:"int", width:"66"},
				{id:"statNm", label:["Status", "#select_filter"], type:"ro", align:"left", sort:"str", width:"70"},
				{id:"btn", label:["바로가기", "#rspan"], type:"btnOpen", align:"center", width:"150"}
			]
	};
	
	let prjtGridObj = new dhtmlXGridObject(prjtGrid);
	prjtGridObj.setImagesPath(THEME_INFO.THEME_IMG_PATH);
	prjtGridObj.enableSmartRendering(true);
	prjtGridObj.setAwaitedRowHeight(45);
	prjtGridObj.setStyle("text-align:center", "", "", "");
	prjtGridObj.setNumberFormat("0,000",prjtGridObj.getColIndexById("planSat"),".",",");
	prjtGridObj.setNumberFormat("0,000",prjtGridObj.getColIndexById("totBdgt"),".",",");
	prjtGridObj.setNumberFormat("0,000",prjtGridObj.getColIndexById("etTrgtAdtTm"),".",",");
	prjtGridObj.setNumberFormat("0,000",prjtGridObj.getColIndexById("raBdgtTm"),".",",");
	prjtGridObj.setNumberFormat("0,000",prjtGridObj.getColIndexById("flcmBdgtTm"),".",",");
	prjtGridObj.setNumberFormat("0,000.00 %",prjtGridObj.getColIndexById("achvRate"),".",",");
	
	prjtGridObj.attachEvent("onRowDblClicked", function(rId, cInd){
		var prjtCd = prjtGridObj.cells(rId, 0).getValue();
		Helper.goPage("/pages/project/read", {prjtCd:prjtCd})
	});
	
	bindEvent();
	loadData(pageConfig);
	
	function bindEvent(){
		$(document).on('click.gridResize', 'nav > button', function(e) { e.preventDefault(); prjtGridObj.setSizes();});
		<c:if test="${development}">
		//selectBox onChange
        $("[name='year']").closest('.selectbox').on('change.selectbox', function(e, param) {
            if (param === undefined) {
                return;
            }
            
            if(pageConfig.year != param.value){
            	pageConfig.year = param.value;
            	pageConfig.selectedRowId = "";
                loadData(pageConfig);
                setSessionStorageData(pageConfig);
            }
        });
		</c:if>
		$("#btnExcelExport").off('click').on('click', function(){
			let param = $.param({
				filename:'프로젝트 목록'
			});
			prjtGridObj.toExcel(EXCEL_WRITER_URL+"?" + param);
		});
		
		$("#btnExcelExport2").off('click').on('click', function(){
			popDownload.openPopup("downloadByAuth");	
		});
		
		<c:if test="${roleCd=='sysadmin'}">
		$("#btnReCalculate").off('click').on('click', function(){
			Helper.ConfirmBox("표준감사시간을 재계산 하시겠습니까?", function(flag){
				if(flag){
					Helper.post("/project/saveReCalculate")
					.done(function(){
						Helper.MessageBox("표준감사시간이 모두 재계산 완료 되었습니다.");
					});		
				}
			})
		});
		
			<c:if test="${env == 'development' || env == 'test' }">
			$("#btnRetain").off('click').on('click', function(){
				Helper.ConfirmBox("Retain으로 예약 전송 할 프로젝트의 Budget정보를 바로 전송하시겠습니까?\n(주의: 스케쥴링 동작 시간에 실행할 경우 BKG_ID값이 충돌할 수 있습니다.)", function(flag){
					if(flag){
						Helper.post("/retain/run/schedule-tran-retain")
						.done(function(){
							Helper.MessageBox("완료 되었습니다.");
						});		
					}
				})
			});
			</c:if>
		</c:if>
	}
	
	function loadData(pageConfig){
		grid_noData("project-grid", false);
		prjtGridObj.clearAll();
		Helper.post("/project/list", {
			year:pageConfig.year
		}).done(function(data){
			if(data.length > 0){
				prjtGridObj.parse(data, "js");
				prjtGridObj.selectRowById(pageConfig.selectedRowId)
			}else{
				grid_noData("project-grid", true, "조회 결과가 없습니다.");
			}
		});
	}
}, 10)});
</script>