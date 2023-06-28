<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="roleCd" value="${sessionScope.SAMIL_SESSION_KEY.roleCd}" />

<h1 class="hidden">Project List</h1>
			
<div class="boxWhite">
	<div class="btnArea">
		<button id="btnDelete" type="button" class="btnPwc btnM action">Delete</button>
	</div>
	<div id="project-grid" class="gridbox gridHp01" style="width:100%;height:100%"></div>
</div>

<script type="text/javascript">
const initPageConfig = {
	selectedRowId: ""
}
function setSessionStorageData(data){
	if(!data) data = Object.assign({}, initPageConfig);
	SessionStorageCtrl.setData("prjtdraftlist-pageConfig", data);
}

function getSessionStorageData(){
	let data = SessionStorageCtrl.getData("prjtdraftlist-pageConfig");
	if(data) return data; 
	return Object.assign({}, initPageConfig);
}

// grid 버튼 이벤트 처리
function openPrjt(prjtCd){
	let pageConfig = getSessionStorageData()
	pageConfig.selectedRowId = prjtCd
	setSessionStorageData(pageConfig);
	Helper.goPage("/pages/project/edit", {prjtCd:prjtCd, isDraft:true});
}

$(document).ready(function(){setTimeout(function(){
	let pageConfig = getSessionStorageData();
	
	const prjtGrid = {
			parent: "project-grid",
			skin: THEME_INFO.THEME,
			image_path: THEME_INFO.THEME_IMG_PATH,
			columns: [
				{id:"chk", label:["#master_checkbox", "#rspan"], type:"ch", width:"40", align:"center"},
				{id:"prjtCd", label:["Project<br>Code", "#text_filter"], type:"ro", align:"center", sort:"str", width:"120"},
				{id:"prjtNm", label:["프로젝트 명", "#text_filter"], type:"ro", align:"left", sort:"str"},
				{id:"chargPtrNm", label:["EL", "#select_filter"], type:"ro", align:"left", sort:"str", width:"80"},
				{id:"chargMgrNm", label:["PM", "#select_filter"], type:"ro", align:"left", sort:"str", width:"80"},
				{id:"crebyNm", label:["생성인", "#rspan"], type:"ro", align:"left", sort:"str", width:"70"},
				{id:"credt", label:["생성일시", "#rspan"], type:"ro", align:"left", sort:"str", width:"150"},
				{id:"modbyNm", label:["수정인", "#rspan"], type:"ro", align:"left", sort:"str", width:"70"},
				{id:"moddt", label:["수정일시", "#rspan"], type:"ro", align:"left", sort:"str", width:"150"},
				{id:"btn", label:["바로가기", "#rspan"], type:"btnOpen2", align:"center", width:"90"}
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
	
	bindEvent();
	loadData(pageConfig);
	
	function bindEvent(){
		$(document).on('click.gridResize', 'nav > button', function(e) { e.preventDefault(); prjtGridObj.setSizes();});
		
		prjtGridObj.attachEvent("onRowDblClicked", function(rId, cInd){openPrjt(rId);});
		
		$("#btnDelete").off('click').on('click', function(){
			let prjtCdListStr = prjtGridObj.getCheckedRows(prjtGridObj.getColIndexById("chk"));
			
			if(prjtCdListStr == ""){
				Helper.MessageBox("삭제할 프로젝트를 선택하세요.")
				return;
			}
			
			Helper.ConfirmBox(["삭제하시겠습니까?", "[참고] 선택된 프로젝트 중 생성인이 본인인 것만 삭제 됩니다."], function(flag){
				if(flag){
					let prjtCdList = prjtCdListStr.split(",");
					Helper.post("/project/delete/draftlist", {prjtCdList:prjtCdList})
					.done(function(){
						loadData(pageConfig);
					})		
				}
			})
		})
	}
	
	function loadData(pageConfig){
		grid_noData("project-grid", false);
		prjtGridObj.clearAll();
		Helper.post("/project/draftlist").done(function(data){
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