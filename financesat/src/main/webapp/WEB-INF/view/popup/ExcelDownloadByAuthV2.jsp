<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
#EXCEL_DOWNLOAD_BY_AUTH .calenWrap input{width: 260px;}
#EXCEL_DOWNLOAD_BY_AUTH input::placeholder { /* Chrome, Firefox, Opera, Safari 10.1+ */
  color: #7D7D7D;
  opacity: 1; /* Firefox */
}
#EXCEL_DOWNLOAD_BY_AUTH input:-ms-input-placeholder { /* Internet Explorer 10-11 */
  color: #7D7D7D;
}
#EXCEL_DOWNLOAD_BY_AUTH input::-ms-input-placeholder { /* Microsoft Edge */
  color: #7D7D7D;
}
#EXCEL_DOWNLOAD_BY_AUTH .tblH tbody tr td:first-child{text-align:left}
</style>
<div id="${popupInfo.id}" class="layCont" id="EXCEL_DOWNLOAD_BY_AUTH">
	<table class="tblH">
		<colgroup><col style="width:200px"><col style="width:auto"><col style="width:150px" /></colgroup>
		<thead>		
			<tr>
				<th scope="col">종류</th>
				<th scope="col">조건</th>
				<th scope="col">버튼</th>
				<!-- 그룹 목록 -->
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>인원별 전체 Budget</td>
				<td id="bdgtByEmp" style="text-align:left">
					<div style="text-align:left">
						<calendar id="bdgtByEmpSDt" v-model="bdgtByEmpSDt" placeholder="시작일" z-index="10000" :has-del-btn="true"></calendar>
						<span>&nbsp;&nbsp;~&nbsp;&nbsp;</span>
						<calendar id="bdgtByEmpEDt" v-model="bdgtByEmpEDt" placeholder="종료일" z-index="10000" :has-del-btn="true"></calendar>
					</div>
					<div style="text-align:left">입력된 기간에 포함된 인원별 Budget 정보를 다운로드 합니다.</div>
				</td>
				<td>
					<button type="button" @click.prevent="onDownloadBdgtByEmp" class="btnPwc btnM action">Download</button>
				</td>
			</tr>
			<tr>
				<td>표준감사시간 Summary</td>
				<td  id="satBdgtInfo">
					<div style="text-align:left">
						<span style="display:inline-block;">프로젝트 시작일&nbsp;>=&nbsp;</span>
						<calendar id="satBdgtInfoBaseDt" v-model="satBdgtInfoBaseDt" placeholder="기준일" z-index="10000" :has-del-btn="true"></calendar>
					</div>
				</td>
				<td>
					<button type="button" @click.prevent="onDownloadSatBdgtInfo" class="btnPwc btnM action">Download</button>
				</td>
			</tr>
			<tr>
				<td>프로젝트 상세 정보</td>
				<td  id="satPrjtInfo">
					<div style="text-align:left">
						<span style="display:inline-block;">프로젝트 시작일&nbsp;>=&nbsp;</span>
						<calendar id="satPrjtInfoBaseDt" v-model="satPrjtInfoBaseDt" placeholder="기준일" z-index="10000" :has-del-btn="true"></calendar>
					</div>
				</td>
				<td>
					<button type="button" @click.prevent="onDownloadSatPrjtInfo" class="btnPwc btnM action">Download</button>
				</td>
			</tr>
		</tbody>
	</table>
	
	
	<ul style="min-height:110px;padding-top:2rem;color:#e0301e">
		<li>접근 가능한 프로젝트만 다운로드 됩니다.</li>
	</ul>
	
	<div class="btnArea">
		<button @click.prevent="onClose" type="button" class="btnPwc btnL">닫기</button>
	</div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/ExcelExport.js" ></script>
<script type="text/javascript">
new Vue({
	el: "#${popupInfo.id}",
	data: {
		bdgtByEmpSDt: null,
		bdgtByEmpEDt: null,
		satBdgtInfoBaseDt: null,
		satPrjtInfoBaseDt: null,
	},
	methods:{
		onClose: function(){
			this.$destroy();
			Helper.closePopup("${popupInfo.id}");
		},
		onDownloadBdgtByEmp: function(){
			let sdt = this.bdgtByEmpSDt;
			let edt = this.bdgtByEmpEDt;
			
			if(Helper.isNull(sdt) || Helper.isNull(edt)){
				Helper.MessageBox("시작일과 종료일을 입력하세요");
				return false;
			}else if(sdt>edt){
				Helper.MessageBox("시작일은 종료일보다 작아야합니다.");
				return false;
			}
			
			COMMON.showLoading();
			$.fileDownload("${pageContext.request.contextPath}/excel/export/bdgtByEmpV2", {
				httpMethod: "POST", 
			    data: $.param({
			    	sdt: sdt,
			    	edt: edt,
			    	filename: "[표준감사시간] 인원별 전체 Budget(" + sdt + "~" + edt + ")"
			    }),
			    successCallback: function (url) { 
			    	COMMON.hideLoading();
		       },
		       failCallback: function (responseHtml, url, error) {
		    	   COMMON.hideLoading();
		       } 
			});
		},
		onDownloadSatBdgtInfo: function(){
			let baseDt = this.satBdgtInfoBaseDt;
			if(Helper.isNull(baseDt)){
				Helper.MessageBox('기준일을 입력하세요.');
				return false;
			}
			
			COMMON.showLoading();
			$.fileDownload("${pageContext.request.contextPath}/excel/export/satBdgtInfoV2", {
				httpMethod: "POST", 
			    data: $.param({
			    	baseDt: baseDt,
			    	filename: "[표준감사시간] 표준감사시간 Summary(" + baseDt + " 이후)"
			    }),
			    successCallback: function (url) { 
			    	COMMON.hideLoading();
		       },
		       failCallback: function (responseHtml, url, error) {
		    	   COMMON.hideLoading();
		       } 
			});
		},
		onDownloadSatPrjtInfo: function(){
			let baseDt = this.satPrjtInfoBaseDt;
			if(Helper.isNull(baseDt)){
				Helper.MessageBox('기준일을 입력하세요.');
				return false;
			}
			
			COMMON.showLoading();
			$.fileDownload("${pageContext.request.contextPath}/excel/export/satPrjtInfoV2", {
				httpMethod: "POST", 
			    data: $.param({
			    	baseDt: baseDt,
			    	filename: "[표준감사시간] 프로젝트 상세 정보(" + baseDt + " 이후)"
			    }),
			    successCallback: function (url) { 
			    	COMMON.hideLoading();
		       },
		       failCallback: function (responseHtml, url, error) {
		    	   COMMON.hideLoading();
		       } 
			});
		}
	},
	watch: {
		
	}
})
</script>