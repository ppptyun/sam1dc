<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="roleCd" value="${sessionScope.SAMIL_SESSION_KEY.roleCd}" />
<c:set var="development" value="${(env == 'development')}" />

<h1 class="hidden">Project List</h1>

<style>
.gridHp01 {
	height: calc( 100vh - 225px ) !important;
}
</style>			
<div id="prjtList" class="boxWhite">
	<div style="display:flex;justify-content:space-between">
		<div>
			<span>결산년도: </span>
			<div class="selectbox" style="width:8em">
				<selectbox id="year" v-model="year" title="결산년도" default-text="전체">
					<c:forEach items="${yearListV2}"	var="year">
						<option value="${year}">${year}년</option>
					</c:forEach>
				</selectbox>
			</div>
			
			<span style="margin-left: 20px;">표감대상여부:  </span>
			<div class="selectbox" style="width:8em">
				<selectbox id="satTrgtYn" v-model="satTrgtYn" title="표감대상 여부" default-text="전체">
					<option value="Y">대상</option>
					<option value="N">대상아님</option>
				</selectbox>
			</div>
		</div>
		<div class="btnArea">
			<button id="btnNew" @click.prevent="onNew" type="button" class="btnPwc btnM action">New</button>
			<button id="btnExcelExport" @click.prevent="onExcelExportByGrid" type="button"  class="btnPwc btnM">Excel Export</button>
			<button id="btnExcelExport2" @click.prevent="onExcelExportByPeriod" type="button"  class="btnPwc btnM">기간별 다운로드</button>
		<c:if test="${roleCd=='sysadmin'}">
			<c:if test="${development}">
				<button id="btnRetain" @click.prevent="onTransAllRetain" type="button"  class="btnPwc btnM">Retain 일괄 전송<span style="color:red">(TEST용)</span></button>
			</c:if>
		</c:if>
		</div>
	</div>
	<div id="project-grid" class="gridbox gridHp01" style="width:100%;height:100%"></div>
</div>

<script type="text/javascript">
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
			{id:"formDiv", label:["폼구분", "#select_filter"], type:"ro", align:"left", sort:"str", width:"0"},			
			{id:"btn", label:["바로가기", "#rspan"], type:"btnOpen", align:"center", width:"150"}			
		]
};
const popDownload = Helper.Popup("excelDownloadByAuth", "기간별 다운로드", "/popup/download-by-auth-v2", {size:'L', formDv:'v2'});
let pageConfig;
const initPageConfig = {year: "", satTrgtYn:"", selectedRowId: ""}

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


new Vue({
	el: '#prjtList',
	data: {
		year: {cd: '${initYear}', nm: '${initYear}년'},
		satTrgtYn: {cd: '', nm: ''},
		prjtGridObj: null,
	},
	mounted: function(){
		pageConfig = getSessionStorageData();
		
		this.prjtGridObj = new dhtmlXGridObject(prjtGrid);
		this.prjtGridObj.setImagesPath(THEME_INFO.THEME_IMG_PATH);
		this.prjtGridObj.enableSmartRendering(true);
		this.prjtGridObj.setAwaitedRowHeight(45);
		this.prjtGridObj.setStyle("text-align:center", "", "", "");
		this.prjtGridObj.setNumberFormat("0,000",this.prjtGridObj.getColIndexById("planSat"),".",",");
		this.prjtGridObj.setNumberFormat("0,000",this.prjtGridObj.getColIndexById("totBdgt"),".",",");
		this.prjtGridObj.setNumberFormat("0,000",this.prjtGridObj.getColIndexById("etTrgtAdtTm"),".",",");
		this.prjtGridObj.setNumberFormat("0,000",this.prjtGridObj.getColIndexById("raBdgtTm"),".",",");
		this.prjtGridObj.setNumberFormat("0,000",this.prjtGridObj.getColIndexById("flcmBdgtTm"),".",",");
		this.prjtGridObj.setNumberFormat("0,000.00 %",this.prjtGridObj.getColIndexById("achvRate"),".",",");
		this.prjtGridObj.attachEvent("onRowDblClicked", function(rId, cInd){Helper.goPage("/pages/project/read", {prjtCd: rId})});
		
		
		let isChanged = false;
		if(pageConfig.year){
			this.setSelect('year', pageConfig.year)
			isChanged = true;
		}
		
		if(pageConfig.satTrgtYn){
			this.setSelect('satTrgtYn', pageConfig.satTrgtYn);
			isChanged = true;
		}
		
		if(!isChanged) this.loadData(this.year.cd, this.satTrgtYn.cd);
	},
	beforeDestroy: function(){
		this.prjtGridObj.destructor();
	},
	methods: {
		loadData: function(year, satTrgtYn){
			let thisObj = this;
			grid_noData("project-grid", false);
			this.prjtGridObj.clearAll();
			Helper.post("/project/v2/list", {
				year: year,
				satTrgtYn: satTrgtYn
			}).done(function(data){
				if(data.length > 0){
					thisObj.prjtGridObj.parse(data, "js");
					thisObj.prjtGridObj.selectRowById(pageConfig.selectedRowId)
				}else{
					grid_noData("project-grid", true, "조회 결과가 없습니다.");
				}
			});
		},
		onNew: function(){Helper.goPage("/pages/project/new")},
		onExcelExportByGrid: function(){this.prjtGridObj.toExcel(EXCEL_WRITER_URL+"?" + $.param({filename:'프로젝트 목록'}));},
		onExcelExportByPeriod: function(){popDownload.openPopup("downloadByAuth");},
		onTransAllRetain: function(){
			Helper.ConfirmBox("Retain으로 예약 전송 할 프로젝트의 Budget정보를 바로 전송하시겠습니까?\n(주의: 스케쥴링 동작 시간에 실행할 경우 BKG_ID값이 충돌할 수 있습니다.)", function(flag){
				if(flag){
					Helper.post("/retain/run/schedule-tran-retain")
					.done(function(){
						Helper.MessageBox("완료 되었습니다.");
					});		
				}
			})
		}
	},
	watch: {
		year: function(value){
			/* pageConfig.year = value.cd;
			pageConfig.satTrgtYn = this.satTrgtYn.cd;
			setSessionStorageData(pageConfig);
			this.loadData(value.cd, this.satTrgtYn.cd); */
			pageConfig.year = this.year.cd;
			pageConfig.satTrgtYn = this.satTrgtYn.cd;
			setSessionStorageData(pageConfig);
			this.loadData(this.year.cd, this.satTrgtYn.cd);
		},
		satTrgtYn: function() {
			pageConfig.year = this.year.cd;
			pageConfig.satTrgtYn = this.satTrgtYn.cd;
			setSessionStorageData(pageConfig);
			this.loadData(this.year.cd, this.satTrgtYn.cd);
		}
	}
});
</script>