<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<c:set var="roleCd" value="${sessionScope.SAMIL_SESSION_KEY.roleCd}" />

<h1 class="hidden">Project List</h1>
			
<div id="calcSatList" class="boxWhite">
	<div class="btnArea">
		<button @click.prevent="onNew" id="btnNew" type="button" class="btnPwc btnM action">New</button>
		<button @click.prevent="onDelete" id="btnDelete" type="button" class="btnPwc btnM">Delete</button>
		<button @click.prevent="onExcelExport" id="btnExcelExport" type="button"  class="btnPwc btnM">Excel Export</button>
	</div>
	
	<div id="project-grid" class="gridbox gridHp01" style="width:100%;height:100%"></div>
</div>

<script>
// grid 버튼 이벤트 처리
function openPrjt(prjtCd){Helper.goPage("/pages/satcalc/v2/read", {prjtCd:prjtCd})}
const calcSatGrid = {
		parent: "project-grid",
		skin: THEME_INFO.THEME,
		image_path: THEME_INFO.THEME_IMG_PATH,
		columns: [
			{id:"chk", label:["#master_checkbox", "#rspan"], type:"ch", width:"40", align:"center"},
			{id:"compNm", label:["회사명", "#text_filter"], type:"ro", align:"left", sort:"str"},
			{id:"bizFrdt", label:["사업연도<br>시작", "#select_filter"], type:"ro", align:"center", sort:"str", width:"80"},
			{id:"bizTodt", label:["사업연도<br>종료", "#select_filter"], type:"ro", align:"center", sort:"str", width:"80"},
			{id:"listDvNm", label:['상장구분', "#select_filter"], type:"ro", align:"left", sort:"str", width:"80"},
			{id:"satgrpNm", label:['그룹', "#select_filter"], type:"ro", align:"center", sort:"str", width:"80"},
			{id:"calRateSat", label:['산식결과*적용요율', "#number_filter"], type:"ron", align:"right", sort:"int", width:"120"},
			{id:"calAdtTm", label:['산출된<br>감사시간(a)', "#number_filter"], type:"ron", align:"right", sort:"int", width:"120"},
			{id:"calSat", label:['숙련도 반영전 표준감사시간(a*b)', "#number_filter"], type:"ron", align:"right", sort:"int", width:"120"},
			{id:"etDfnSat", label:['한공회 표준감사시간', "#number_filter"], type:"ron", align:"right", sort:"int", width:"105"},
			{id:"crebyNm", label:["작성자", "#rspan"], type:"ro", align:"center", sort:"str", width:"80"},
			{id:"btn", label:["바로가기", "#rspan"], type:"btnOpen2", align:"center", width:"90"}
		]
};

new Vue({
	el: "#calcSatList",
	data: {
		year: {cd: null, nm: null},
		calcSatGridObj: null
	},
	mounted: function(){
		
		this.calcSatGridObj = new dhtmlXGridObject(calcSatGrid);
		this.calcSatGridObj.setImagesPath(THEME_INFO.THEME_IMG_PATH);
		this.calcSatGridObj.enableSmartRendering(true, 100);
		this.calcSatGridObj.setStyle("text-align:center", "", "", "");
		this.calcSatGridObj.setNumberFormat("0,000",this.calcSatGridObj.getColIndexById("calRateSat"),".",",");
		this.calcSatGridObj.setNumberFormat("0,000",this.calcSatGridObj.getColIndexById("calAdtTm"),".",",");
		this.calcSatGridObj.setNumberFormat("0,000",this.calcSatGridObj.getColIndexById("calSat"),".",",");
		this.calcSatGridObj.setNumberFormat("0,000",this.calcSatGridObj.getColIndexById("etDfnSat"),".",",");
		this.calcSatGridObj.attachEvent("onRowDblClicked", function(rId, cInd){
			Helper.goPage("/pages/satcalc/v2/read", {prjtCd: rId});
		});
		
		
		/* let today = new Date();
		let year = today.getFullYear()
		let month = today.getMonth() + 1;
		
		if(month < 4){this.setSelect('year', String(year))}
		else{this.setSelect('year', String(year + 1))}
		
		if(this.year.cd == null){
			this.setSelect('year', '')
		} */
		
		this.loadData();
		
	},
	beforeDestroy: function(){
		this.calcSatGridObj.destructor();
	},
	methods: {
		loadData: function(year){
			let thisObj = this;
			grid_noData("project-grid", false);
			thisObj.calcSatGridObj.clearAll();
			Helper.post("/calcSat/list", {year: year}).done(function(data){
				if(data.length > 0){
					thisObj.calcSatGridObj.parse(data, "js");
				}else{
					grid_noData("project-grid", true, "조회 결과가 없습니다.");
				}
			});
		},
		//========================================================================================
		//	[시작] 20220224 남웅주  
		//========================================================================================		
		onNew: function(){Helper.goPage("/pages/satcalc/v4/new")},
		//========================================================================================
		//	[종료] 20220224 남웅주  
		//========================================================================================		
		onExcelExport: function(){
			this.calcSatGridObj.toExcel(EXCEL_WRITER_URL+"?" + $.param({filename:'프로젝트 목록'}));
		},
		onDelete: function(){
			let thisObj = this;
			let prjtCdListStr = this.calcSatGridObj.getCheckedRows(this.calcSatGridObj.getColIndexById("chk"));
			
			if(prjtCdListStr == ""){
				Helper.MessageBox("삭제할 대상을 선택하세요.")
				return;
			}
			
			Helper.ConfirmBox(["삭제하시겠습니까?", "[참고] 선택된 대상 중 본인이 생성한 것만 삭제 됩니다."], function(flag){
				if(flag){
					Helper.post("/calcSat/delete", {prjtCdList: prjtCdListStr.split(",")})
					.done(function(){
						thisObj.loadData();
					})		
				}
			})
		}
	},
	watch: {
		year: function(value){
			this.loadData(value.cd)
		}
	}
});
</script>