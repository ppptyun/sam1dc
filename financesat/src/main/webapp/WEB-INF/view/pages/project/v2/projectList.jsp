<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="roleCd" value="${sessionScope.SAMIL_SESSION_KEY.roleCd}" />
<c:set var="development" value="${(env == 'development')}" />

<h1 class="hidden">Project List</h1>

<style>
#wrapper > #container{height:100% !important}
#prjtList {height: 100%;}
.gridHp01 {
	height: calc( 100% - 40px ) !important;
}
</style>	
<div id="prjtList" class="boxWhite">
	<div style="display:flex;justify-content:space-between">
		<div>
			<span>결산년도: </span>
			<div class="selectbox" style="width:8em">
				<selectbox id="year" v-model="year" title="결산년도" default-text="전체">
					<c:forEach items="${yearList}"	var="year">
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
				<!-- <button id="btnRetain" @click.prevent="onTransAllRetain" type="button"  class="btnPwc btnM">Retain 일괄 전송<span style="color:red">(TEST용)</span></button> -->
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
			{id:"cntrtAdtTm", label:["프로젝트<br>합의시간", "#numeric_filter"], type:"ron", align:"right", sort:"int", width:"90"},
			{id:"totBdgtTm", label:["Budget<br>Total", "#numeric_filter"], type:"ron", align:"right", sort:"int", width:"90"},
			{id:"spaBdgtTm", label:["SPA<br>배부예산", "#numeric_filter"], type:"ron", align:"right", sort:"int", width:"90"},
			{id:"flcmBdgtTm", label:["Fulcrum<br>배부예산", "#numeric_filter"], type:"ron", align:"right", sort:"int", width:"90"},
			{id:"newStfBdgtTm", label:["New Staff(TBD)", "#numeric_filter"], type:"ron", align:"right", sort:"int", width:"90"},
			{id:"statNm", label:["Status", "#select_filter"], type:"ro", align:"left", sort:"str", width:"70"},
			{id:"btn", label:["바로가기", "#rspan"], type:"btnOpen", align:"center", width:"150"}
		]
};
const popDownloadV3 = Helper.Popup("excelDownloadByAuth", "기간별 다운로드", "/popup/download-by-auth-v3", {size:'L', formDv:'v3'});
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
		year: {cd:'${initYear}', nm: '${initYear}년'},
		satTrgtYn: {cd: '', nm: ''},
		prjtGridObj: null,
		isInit: false,
	},
	mounted: function(){
		pageConfig = getSessionStorageData();
		const thisObj = this;
		this.prjtGridObj = new dhtmlXGridObject(prjtGrid);
		this.prjtGridObj.setImagesPath(THEME_INFO.THEME_IMG_PATH);
		this.prjtGridObj.enableSmartRendering(true);
		this.prjtGridObj.setAwaitedRowHeight(45);
		this.prjtGridObj.setStyle("text-align:center", "", "", "");
		this.prjtGridObj.setNumberFormat("0,000",this.prjtGridObj.getColIndexById("cntrtAdtTm"),".",",");
		this.prjtGridObj.setNumberFormat("0,000",this.prjtGridObj.getColIndexById("totBdgtTm"),".",",");
		this.prjtGridObj.setNumberFormat("0,000",this.prjtGridObj.getColIndexById("spaBdgtTm"),".",",");
		this.prjtGridObj.setNumberFormat("0,000",this.prjtGridObj.getColIndexById("flcmBdgtTm"),".",",");
		this.prjtGridObj.setNumberFormat("0,000",this.prjtGridObj.getColIndexById("newStfBdgtTm"),".",",");
		this.prjtGridObj.attachEvent("onRowDblClicked", function(rId, cInd){Helper.goPage("/pages/project/read", {prjtCd: rId})});
		
		if(pageConfig.year) this.setSelect('year', pageConfig.year)
		if(pageConfig.satTrgtYn) this.setSelect('satTrgtYn', pageConfig.satTrgtYn);
		
		$(document).on('click.gridResize', 'nav > button, .sumArea > .btnTog', function(e) {
	         e.preventDefault();
	         thisObj.prjtGridObj.setSizes();
	    });
		
		setTimeout(function(){	// Select Box 선택된 후에 실행하기위해 약간의 지연시간 
			thisObj.isInit = true;
			thisObj.onChangedCondition();
		}, 1)
		
	},
	beforeDestroy: function(){
		this.prjtGridObj.destructor();
	},
	methods: {
		loadData: function(year, satTrgtYn){
			let thisObj = this;
			grid_noData("project-grid", false);
			this.prjtGridObj.clearAll();
			Helper.post("/project/v3/list", {
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
		onExcelExportByPeriod: function(){
			popDownloadV3.openPopup("downloadByAuthV3");
		},
		onTransAllRetain: function(){
			Helper.ConfirmBox("Retain으로 예약 전송 할 프로젝트의 Budget정보를 바로 전송하시겠습니까?\n(주의: 스케쥴링 동작 시간에 실행할 경우 BKG_ID값이 충돌할 수 있습니다.)", function(flag){
				if(flag){
					Helper.post("/retain/run/schedule-tran-retain")
					.done(function(){
						Helper.MessageBox("완료 되었습니다.");
					});		
				}
			})
		},
		onChangedCondition: function(){
			pageConfig.year = this.year.cd;
			pageConfig.satTrgtYn = this.satTrgtYn.cd;
			setSessionStorageData(pageConfig);
			this.loadData(this.year.cd, this.satTrgtYn.cd);
		}
	},
	watch: {
		year: function(nValue, oValue){
			if(this.isInit){
				this.onChangedCondition();
			}
		},
		satTrgtYn: function(nValue, oValue) {
			if(this.isInit){
				this.onChangedCondition();
			}
		}
	}
});
</script>