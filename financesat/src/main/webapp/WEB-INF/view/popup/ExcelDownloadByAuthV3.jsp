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

.button-group {
	display: inline-flex;
	padding-left: 20px;
	margin-right: -8px;
}
.button-wrap {
	display: inline-block;
	padding-left: 8px;
	padding-right: 8px;
}
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
						<div class="frmInp button-group">
							<span class="button-wrap">
								<input id="bdgtByEmpTrgtSat" type="radio" v-model="bdgtByEmpTrgt" class="frmInp" value="Y">
								<label for="bdgtByEmpTrgtSat">&nbsp;&nbsp;표감 대상</label>
							</span>
							<span class="button-wrap">
								<input id="bdgtByEmpTrgtNoSat" type="radio" v-model="bdgtByEmpTrgt" class="frmInp" value="N">
								<label for="bdgtByEmpTrgtNoSat">&nbsp;&nbsp;표감 대상 아님</label>
							</span>
						</div>
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
						<calendar id="satBdgtInfoSdt" v-model="satBdgtInfoSdt" placeholder="시작일" z-index="10000" format="yyyy-mm" :has-del-btn="true"></calendar>
						<span>&nbsp;&nbsp;~&nbsp;&nbsp;</span>
						<calendar id="satBdgtInfoEdt" v-model="satBdgtInfoEdt" placeholder="종료일" z-index="10000" format="yyyy-mm" :has-del-btn="true"></calendar>
						<div class="frmInp button-group">
							<span class="button-wrap">
								<input id="bdgtInfoTrgtSat" type="radio" v-model="bdgtInfoTrgt" class="frmInp" value="Y">
								<label for="bdgtInfoTrgtSat">&nbsp;&nbsp;표감 대상</label>
							</span>
							<span class="button-wrap">
								<input id="bdgtInfoTrgtNoSat" type="radio" v-model="bdgtInfoTrgt" class="frmInp" value="N">
								<label for="bdgtInfoTrgtNoSat">&nbsp;&nbsp;표감 대상 아님</label>
							</span>
						</div>
					</div>
					<div style="text-align:left">입력된 기간 내에  프로젝트 사업연도 종료월(결산월)이 포함할 경우 Budget Summary정보를 다운로드 합니다.</div>
				</td>
				<td>
					<button type="button" @click.prevent="onDownloadSatBdgtInfo" class="btnPwc btnM action">Download</button>
				</td>
			</tr>
			<tr>
				<td>프로젝트 상세 정보</td>
				<td  id="satPrjtInfo">
					<div style="text-align:left">
						<calendar id="satPrjtInfoSdt" v-model="satPrjtInfoSdt" placeholder="시작일" z-index="10000" format="yyyy-mm" :has-del-btn="true"></calendar>
						<span>&nbsp;&nbsp;~&nbsp;&nbsp;</span>
						<calendar id="satPrjtInfoEdt" v-model="satPrjtInfoEdt" placeholder="종료일" z-index="10000" format="yyyy-mm" :has-del-btn="true"></calendar>
						<div class="frmInp button-group">
							<span class="button-wrap">
								<input id="satPrjtInfoTrgtSat" type="radio" v-model="satPrjtInfoTrgt" class="frmInp" value="Y">
								<label for="satPrjtInfoTrgtSat">&nbsp;&nbsp;표감 대상</label>
							</span>
							<span class="button-wrap">
								<input id="satPrjtInfoNoSat" type="radio" v-model="satPrjtInfoTrgt" class="frmInp" value="N">
								<label for="satPrjtInfoNoSat">&nbsp;&nbsp;표감 대상 아님</label>
							</span>
						</div>
					</div>
					<div style="text-align:left">입력된 기간 내에  프로젝트 사업연도 종료월(결산월)이 포함할 경우 Project 정보를 다운로드 합니다.</div>
				</td>
				<td>
					<button type="button" @click.prevent="onDownloadSatPrjtInfo" class="btnPwc btnM action">Download</button>
				</td>
			</tr>
		</tbody>
	</table>
	
	
	<ul style="min-height:110px;padding-top:2rem;color:#e0301e">
		<li>접근 가능한 프로젝트만 다운로드 됩니다.</li>
		<li>프로젝트의 사업연도 종료월(결산월)이 2020년 이후만 다운로드 됩니다.</li>
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
		bdgtByEmpTrgt: 'Y',
		satBdgtInfoSdt: null,
		satBdgtInfoEdt: null,
		bdgtInfoTrgt: 'Y',
		satPrjtInfoSdt: null,
		satPrjtInfoEdt: null,
		satPrjtInfoTrgt: 'Y',
	},
	methods:{
		onClose: function(){
			this.$destroy();
			Helper.closePopup("${popupInfo.id}");
		},
		onDownloadBdgtByEmp: function(){
			let sdt = this.bdgtByEmpSDt;
			let edt = this.bdgtByEmpEDt;
			let satTrgtYn = this.bdgtByEmpTrgt;
			
			if(Helper.isNull(sdt) || Helper.isNull(edt)){
				Helper.MessageBox("시작일과 종료일을 입력하세요");
				return false;
			}else if(sdt>edt){
				Helper.MessageBox("시작일은 종료일보다 작아야합니다.");
				return false;
			}
			
			COMMON.showLoading();
			$.fileDownload("${pageContext.request.contextPath}/excel/export/bdgtByEmpV3", {
				httpMethod: "POST", 
			    data: $.param({
			    	sdt: sdt,
			    	edt: edt,
			    	satTrgtYn: satTrgtYn,
			    	filename: "[표준감사시간] 인원별 전체 Budget(" + sdt + "~" + edt + "): " + (satTrgtYn === 'Y' ? '표감대상' : '표감대상 아님')
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
			let sdt = this.satBdgtInfoSdt;
			let edt = this.satBdgtInfoEdt;
			let satTrgtYn = this.bdgtInfoTrgt;
			
			if(Helper.isNull(sdt) || Helper.isNull(edt)){
				Helper.MessageBox("시작일과 종료일을 입력하세요");
				return false;
			}else if(sdt>edt){
				Helper.MessageBox("시작일은 종료일보다 작아야합니다.");
				return false;
			}
			
			COMMON.showLoading();
			$.fileDownload("${pageContext.request.contextPath}/excel/export/satBdgtInfoV3", {
				httpMethod: "POST", 
			    data: $.param({
			    	sdt: sdt,
			    	edt: edt,
			    	satTrgtYn: satTrgtYn,
			    	filename: "[표준감사시간] 표준감사시간 Summary(결산월 기준 " + sdt + "~" + edt + "): " + (satTrgtYn === 'Y' ? '표감대상' : '표감대상 아님')
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
			let sdt = this.satPrjtInfoSdt;
			let edt = this.satPrjtInfoEdt;
			let satTrgtYn = this.satPrjtInfoTrgt;
			
			if(Helper.isNull(sdt) || Helper.isNull(edt)){
				Helper.MessageBox("시작일과 종료일을 입력하세요");
				return false;
			}else if(sdt>edt){
				Helper.MessageBox("시작일은 종료일보다 작아야합니다.");
				return false;
			}
			
			COMMON.showLoading();
			$.fileDownload("${pageContext.request.contextPath}/excel/export/satPrjtInfoV3", {
				httpMethod: "POST", 
			    data: $.param({
			    	sdt: sdt,
			    	edt: edt,
			    	satTrgtYn: satTrgtYn,
			    	filename: "[표준감사시간] 프로젝트 상세 정보(결산월 기준 " + sdt + "~" + edt + "): " + (satTrgtYn === 'Y' ? '표감대상' : '표감대상 아님')
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