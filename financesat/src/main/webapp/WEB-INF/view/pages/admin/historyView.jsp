<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="roleCd" value="${sessionScope.SAMIL_SESSION_KEY.roleCd}" />
<c:set var="development" value="${(env == 'development')}" />

<h1 class="hidden">Project List</h1>

<style>
#wrapper > #container{height:100% !important}
#prjtList {height: 100%;}
.gridHp01 {
	height: calc(100vh - 200px) !important;
}
.search-box > * {
  display: inline-block;
  vertical-align: middle;
}
</style>	
<div id="historyList" class="boxWhite">
	<div style="display:flex;justify-content:space-between">
		<div class="search-box">
			<span>결재일: </span>
			<calendar :is-read="false" v-model="frdt" :min-date="compareFrdt" :max-date="todt" style="width: 150px"></calendar>
			<span style="margin: 0 10px">~</span>
			<calendar :is-read="false" v-model="todt" :min-date="frdt" :max-date="compareTodt" style="width: 150px"></calendar>
			<span class="txtInfo" style="margin-top: 10px; " >{{minDt}}부터 최대 1년까지 조회 가능합니다.</span>
		</div>
		<div class="btnArea">
			<button @click.prevent="onExcelExportByGrid" type="button"  class="btnPwc btnM">Excel Export</button>
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
			{id:"prjtcd", label:["Project<br>Code", "#text_filter"], type:"ro", align:"center", sort:"str", width:"100"},
			{id:"prjtnm", label:["프로젝트 명", "#text_filter"], type:"ro", align:"left", sort:"str"},
			{id:"chargptrnm", label:["EL", "#text_filter"], type:"ro", align:"left", sort:"str", width:"80"},
			{id:"chargmgrnm", label:["PM", "#text_filter"], type:"ro", align:"left", sort:"str", width:"80"},
			{id:"bizTodt", label:["사업연도 종료월", "#text_filter"], type:"ro", align:"center", sort:"str", width:"80"},
			{id:"aprvReqNm", label:["결재 요청자", "#text_filter"], type:"ro", align:"left", sort:"str", width:"80"},
			{id:"aprvReqDt", label:["결재 요청일", "#text_filter"], type:"ro", align:"center", sort:"str", width:"80"},
			{id:"aprvCmpltDt", label:['결재일', "#text_filter"], type:"ro", align:"center", sort:"str", width:"80"},
			{id:"modReasonCmnt", label:["수정 사유", "#text_filter"], type:"ro", align:"left", sort:"str", width:"250"}
		]
};

const interval = 1;	// 최대 조회기간 (년단위)
new Vue({
	el: '#historyList',
	data: {
		isInit: false,
		minDt: '',
		maxDt: '',
		frdt: '',
		todt: '',
		prjtCd: '',
		dateCtrl: new Helper.DateCtrl(),
		gridObj: null
	},
	computed: {
		compareFrdt: function() {
			const compareDt = this.dateCtrl.toFormatDate(this.dateCtrl.toTimeObject(this.dateCtrl.shiftTime(this.todt.replace(/\-/g, ''), -interval, 0, 1)));
			return this.minDt <= compareDt ? compareDt : this.minDt;
		},
		compareTodt: function() {
			const compareDt = this.dateCtrl.toFormatDate(this.dateCtrl.toTimeObject(this.dateCtrl.shiftTime(this.frdt.replace(/\-/g, ''), interval, 0, -1)));
			return this.maxDt >= compareDt ? compareDt : this.maxDt;
		},
	},
	created: function() {
		
		const today = this.dateCtrl.toFormatedTimeString(new Date()).substr(0, 10);
		const compareDt = this.dateCtrl.toFormatDate(this.dateCtrl.toTimeObject(this.dateCtrl.shiftTime(today.replace(/\-/g, ''), -interval, 0, +1)));
		
		this.minDt = '2020-01-01';
		this.maxDt = today
		
		this.frdt = this.minDt <= compareDt ? compareDt : this.minDt;
		this.todt = today;
	},
	mounted: function(){
		const thisObj = this;
		
		this.gridObj = new dhtmlXGridObject(prjtGrid);
		this.gridObj.setImagesPath(THEME_INFO.THEME_IMG_PATH);
		this.gridObj.setAwaitedRowHeight(45);
		this.gridObj.setStyle("text-align:center", "", "", "");
		this.gridObj.enableSmartRendering(true);
		
		$(document).on('click.gridResize', 'nav > button, .sumArea > .btnTog', function(e) {
	         e.preventDefault();
	         thisObj.gridObj.setSizes();
	    });
	},
	beforeDestroy: function(){
		this.prjtGridObj.destructor();
	},
	methods: {
		loadData: function(){
			const thisObj = this;
			this.gridObj.clearAll();
			Helper.post("/admin/get/history", {
				frdt: this.frdt,
				todt: this.todt,
				prjtCd: this.prjtCd
			}).done(function(data){
				thisObj.gridObj.parse(data, 'js');
			});
		},
		onExcelExportByGrid: function(){
			this.gridObj.toExcel(EXCEL_WRITER_URL+"?" + $.param({filename:'결재완료 History'}));
		},
	},
	watch: {
		frdt: function() {
			if(this.frdt < this.compareFrdt) {
				Helper.MessageBox(["최대 1년까지 조회 가능합니다.", this.compareFrdt + "부터 조회 가능합니다."])
				return;
			}
			this.loadData()
		},
		todt: function() {
			if(this.todt > this.compareTodt) {
				Helper.MessageBox(["최대 1년까지 조회 가능합니다.", this.compareTodt + "까지 조회 가능합니다."])
				return;
			}
			this.loadData()
		}
	}
});
</script>