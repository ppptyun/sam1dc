<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<style>
#profilePage .calenWrap input{width: 260px;}
</style>
<h1 class="hidden">기초항목</h1>

<div class="boxWhite" id="profilePage">
	<c:if test="false"><!--
	<div class="titArea">
		<h2 class="titDep2">데이터 다운로드</h2>
	</div>
	
	<table class="tblH">
		<colgroup><col style="width:200px"><col style="width:auto"><col style="width:150px" /></colgroup>
		<thead>		
			<tr>
				<th scope="col">종류</th>
				<th scope="col">조건</th>
				<th scope="col">버튼</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>인원별 전체 Budget</td>
				<td id="bdgtByEmp" style="text-align:left">
					<div style="text-align:left"><span style="display:inline-block;width:60px">기&nbsp;간&nbsp;</span>: <div class="calenWrap"><input name="sdt" type="text" readonly></div>&nbsp;&nbsp;~&nbsp;&nbsp;<div class="calenWrap"><input name="edt" type="text" readonly></div></div>
					<div style="text-align:left">선택된 기간에 포함된 인원별 Budget 상세정보를 다운로드 합니다.</div>
				</td>
				<td>
					<button type="button" id="btnDownloadBdgtByEmp" class="btnPwc btnM action">Download</button>
				</td>
				<script type="text/javascript">
					$("#btnDownloadBdgtByEmp").off('click').on('click', function(){
						let sdt = $("#bdgtByEmp input[name='sdt']").val();
						let edt = $("#bdgtByEmp input[name='edt']").val();
						
						if(Helper.isNull(sdt) || Helper.isNull(edt)){
							Helper.MessageBox("시작일과 종료일을 입력하세요");
							return false;
						} 
						
						COMMON.showLoading();
						$.fileDownload("${pageContext.request.contextPath}/excel/export/bdgtByEmp", {
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
						return false;
					});
				</script>
			</tr>
			<tr>
				<td>표준감사시간 Summary</td>
				<td  id="satBdgtInfo">
					<div style="text-align:left"><span style="display:inline-block;width:60px">기준일</span>: <div class="calenWrap"><input name="baseDt" type="text" readonly></div></div>
					<div style="text-align:left">프로젝트 시작일이 기준일 이후 프로젝트를 다운로드 합니다.</div>
				</td>
				<td>
					<button type="button" id="btnDownloadSatBdgtInfo" class="btnPwc btnM action">Download</button>
				</td>
				<script type="text/javascript">
					$("#btnDownloadSatBdgtInfo").off('click').on('click', function(){
						let baseDt = $("#satBdgtInfo input[name='baseDt']").val();
						if(Helper.isNull(baseDt)){
							Helper.MessageBox('기준일을 선택하세요');
							return false;
						}
						
						COMMON.showLoading();
						$.fileDownload("${pageContext.request.contextPath}/excel/export/satBdgtInfo", {
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
						return false;
					});
				</script>
			</tr>
			<tr>
				<td>프로젝트 상세 정보</td>
				<td  id="satPrjtInfo">
					<div style="text-align:left"><span style="display:inline-block;width:60px">기준일</span>: <div class="calenWrap"><input name="baseDt" type="text" readonly></div></div>
					<div style="text-align:left">프로젝트 시작일이 기준일 이후 프로젝트를 다운로드 합니다.</div>
				</td>
				<td>
					<button type="button" id="btnDownloadSatPrjtInfo" class="btnPwc btnM action">Download</button>
				</td>
				<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/ExcelExport.js" ></script>
				
				<script type="text/javascript">
					$("#btnDownloadSatPrjtInfo").off('click').on('click', function(){
						let baseDt = $("#satPrjtInfo input[name='baseDt']").val();
						if(Helper.isNull(baseDt)){
							Helper.MessageBox('기준일을 선택하세요');
							return false;
						}
						
						COMMON.showLoading();
						$.fileDownload("${pageContext.request.contextPath}/excel/export/satPrjtInfo", {
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
						return false;
					});
				</script>
				
			</tr>
		</tbody>
	</table>
	
	
	<div class="titArea">
		<h2 class="titDep2  marT30">본부별 리테인 전송 허용 여부</h2>
		<div class="btnArea side">
			<button type="button" id="btnrRetainTranSave" class="btnPwc btnM action">Save</button>
		</div>
	</div>
	
	<table id="retain-grid" class="tblH">
		<caption></caption>
		<colgroup><col style="width:auto"><col style="width:40%" span="11"></colgroup>
		<thead>		
			<tr>
				<th scope="col">본부</th>
				<th scope="col">허용 여부</th>
			</tr>
		</thead>
		<tbody>
		</tbody>					
	</table>
	<p class="lineDiv"></p>
	--></c:if>
	
	<div class="titArea">
		<h2 class="titDep2 marT30">적용 요율</h2>
		<div class="btnArea side">
			<button type="button" id="btnRateSave" class="btnPwc btnM action">Save</button>
		</div>
	</div>
	
	<table id="rate-grid" class="tblH">
		<caption>적용 요율 테이블 입니다.</caption>
		<colgroup><col style="width:auto"><col style="width:8.7%" span="11"></colgroup>
		<thead>		
			<tr>
				<th scope="col">구분</th>
				<!-- 그룹 목록 -->
			</tr>
		</thead>
		<tbody>
			<!-- 년도별 그룹별 요율 데이터 그리드 -->
		</tbody>					
	</table>
	
	<p class="lineDiv"></p>
	
	<h2 class="titDep2 marT30">숙련도 및 계수 설정</h2>
	
	<div class="searchBox">   
		<dl class="item">
			<dt>Version</dt>
			<dd>
				<div class="selectbox" style="width:120px">
					<select id="cbo_prfl" title="">
					<!-- option 목록
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="4">4</option>
					 -->	
					</select>
				</div>
			</dd>
		</dl>
		<p class="txtDesc"><em>Description</em> : XXXXXXX 가 대상입니다.</p>
	</div>
	
	<div class="titArea">
		<h2 class="titDep3">숙련도</h2>
		<div class="btnArea side">
			<button type="button" id="btnProfSave" class="btnPwc btnM action">Save</button>
		</div>
	</div>
	
	<table id="prof-grid" class="tblH">
		<caption>숙련도 테이블 입니다.</caption>
		<colgroup><col style="width:auto"><col style="width:15%" span="6"></colgroup>
		<thead>
			<tr>
				<th scope="col">자격 구분</th>
				<th scope="col">수습 회계사</th>
				<th scope="col" colspan="5">등록 회계사</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>경력</td>
				<td><div class="frmNowrap etc02"><input type="number" class="tright"><span class="unit">년</span></div></td>
				<td><div class="frmNowrap etc02"><input type="number" class="tright"><span class="unit">년</span></div></td>
				<td><div class="frmNowrap etc02"><input type="number" class="tright"><span class="unit">년</span></div></td>
				<td><div class="frmNowrap etc02"><input type="number" class="tright"><span class="unit">년</span></div></td>
				<td><div class="frmNowrap etc02"><input type="number" class="tright"><span class="unit">년</span></div></td>
				<td><div class="frmNowrap etc02"><input type="number" class="tright"><span class="unit">년</span></div></td>
			</tr>
			<tr>
				<td>숙련도</td>
				<td><div class="frmNowrap etc02"><input type="number" class="tright"></div></td>
				<td><div class="frmNowrap etc02"><input type="number" class="tright"></div></td>
				<td><div class="frmNowrap etc02"><input type="number" class="tright"></div></td>
				<td><div class="frmNowrap etc02"><input type="number" class="tright"></div></td>
				<td><div class="frmNowrap etc02"><input type="number" class="tright"></div></td>
				<td><div class="frmNowrap etc02"><input type="number" class="tright"></div></td>
			</tr>
		</tbody>
	</table>
	
	<h2 class="titDep3 marT20">계수</h2>
	
	<table id="fctr-grid" class="tblH tblFilter">
		<caption>계수 테이블 입니다.</caption>
		<colgroup><col style="width:auto"><col style="width:9%" span="10"></colgroup>
		<thead>
			<tr>
				<th scope="col">구분</th>
				<th scope="col">연결</th>
				<th scope="col">금융지주사</th>
				<th scope="col">미국 상장</th>
				<th scope="col">KAM</th>
				<th scope="col">당기 순손실</th>
				<th scope="col">IFRS</th>
				<th scope="col">반기 경로</th>
				<th scope="col">분기 경로</th>
				<th scope="col">비적정 의견</th>
				<th scope="col">조도감사</th>
			</tr>
		</thead>
		<tbody class="tdFilter">
				<!-- 컨텐츠 --> 
		</tbody>
	</table>
					
</div><!-- //boxWhite -->
         <!-- //컨텐츠 끝 --> 
<script>

$(document).ready(function(){
setTimeout(function(){
	
	$(".loadingA").show();
	
	initRetainTable();

	var paramData = {};
	var formData = {};
	$.ajax({
		async : false, 
		type: "POST",
			url: "<%=request.getContextPath()%>/json/admin/rateList",
			data: {formData:JSON.stringify(paramData)},
		success:function( data ) {
			if(data.grpList!=null){
				for(var i=0;i<data.grpList.length;i++){
					$('#rate-grid > thead > tr').append("<th scope=\"col\">" + data.grpList[i].NM + "</th>\n");
				}				
			}

			if(data.dataList!=null){
				var str = "";
				for(var i=0;i<data.yearList.length;i++){
					str += "<tr>\n";
					str += "	<td>" + data.yearList[i].APLY_YEAR + "년</td>\n";
					
					for(var j=(i*data.grpList.length);j<((i+1)*data.grpList.length);j++){
						str += "	<td><div class=\"frmNowrap etc02\">";
						str += "<input type=\"number\" name=\"num_aplyRate\" value=\"" + data.dataList[j].APLY_RATE + "\" class=\"tright\">";						
						str += "<input type=\"hidden\" name=\"hid_aplyYear\" value=\"" + data.dataList[j].APLY_YEAR + "\">";
						str += "<input type=\"hidden\" name=\"hid_grpCd\" value=\"" + data.dataList[j].CD + "\">";
						str += "<input type=\"hidden\" name=\"hid_aplyRate\" value=\"" + data.dataList[j].APLY_RATE + "\">";
						str += "<span class=\"unit\">%</span></div></td>\n";
					}					
					str += "<\tr>\n";
				}
				$('#rate-grid > tbody:first').append(str);
			}

			//버전 정보 콤보 박스 재 설정			
	        $.SelectBoxSet('div.selectbox select', {
	            height: 200
	        });

			var instance = $('div.selectbox > a').getInstance();	
			$('.selectbox select option').remove();
			if(data.prflList!=null){
				var strOption = "";
				for(var i=0;i<data.prflList.length;i++){
					strOption += "<option value=\"" + data.prflList[i].PRFL_ID + "\">" + data.prflList[i].PRFL_ID + "</option>\n";
				}
			}
			$('.selectbox select').append(strOption);
			//새로 생성된 option 을 적용하기 위해서 호출함.
			instance.resetSB();

	      	//숙련도 및 계수 설정 그리드 조회
			selectProfFctrList();	

		},
		complete:function(){
			$(".loadingA").hide(); 
		} 
	});	

    //적용요율 저장    
    $("#btnRateSave").click(function(){
    	var rowCnt = $("input[name='num_aplyRate']").length;
		var rateJsonArray = new Array();
		var rateJson;
		var oldRate;
		var newRate;
		for(var i=0;i<rowCnt;i++){
			oldRate = $("input[name='hid_aplyRate']").eq(i).val();
			newRate = $("input[name='num_aplyRate']").eq(i).val();
			if(oldRate != newRate){
				rateJson = new Object();
				rateJson.aplyRate = newRate;
				rateJson.aplyYear = $("input[name='hid_aplyYear']").eq(i).val();
				rateJson.grpCd    = $("input[name='hid_grpCd']").eq(i).val();
				rateJsonArray.push(rateJson);
			}
		}
		
		$(".loadingA").show(); 

		var paramData = new Object();
		paramData.datalist = rateJsonArray;
		
		var formData = {};
		$.ajax({
			type: "POST",
				url: "<%=request.getContextPath()%>/json/admin/aplyRateSave",
				data: {formData:JSON.stringify(paramData)},
			success:function( data ) {
				if(data.result!=null){
					//alert("저장 되었습니다.");
					Helper.MessageBox("저장 되었습니다.");
				}		
			},
			complete:function(){
				$(".loadingA").hide(); 
			} 
		});	
    });
    
    
    $("#btnProfSave").click(function(){
    	var rowCnt = $("input[name='txt_profYear']").length;
		var profJsonArray = new Array();
		var profJson;
		var oldCertYcnt;
		var newCertYcnt;
		var oldWkmnsp;
		var newWkmnsp;

		for(var i=0;i<rowCnt;i++){
			oldCertYcnt = $("input[name='hid_certYcnt']").eq(i).val();
			newCertYcnt = $("input[name='txt_profYear']").eq(i).val();
			oldWkmnsp   = $("input[name='hid_wkmnsp']").eq(i).val();
			newWkmnsp   = $("input[name='txt_wkmnsp']").eq(i).val();
			
			//alert('[' + oldCertYcnt + '][' + newCertYcnt + '][' + oldWkmnsp + '][' + newWkmnsp + ']');
			
			if((oldCertYcnt != newCertYcnt) || (oldWkmnsp != newWkmnsp)){
				profJson = new Object();
				profJson.certYcnt = newCertYcnt;
				profJson.oCertYcnt = oldCertYcnt;
				profJson.wkmnsp = newWkmnsp;
				profJson.prflId = $("input[name='hid_prflId']").eq(i).val();
				profJson.certDv = $("input[name='hid_certDv']").eq(i).val();
				profJsonArray.push(profJson);
			}
		}
		$(".loadingA").show(); 

		var paramData = new Object();
		paramData.datalist = profJsonArray;
		
		var formData = {};
		$.ajax({
			type: "POST",
				url: "<%=request.getContextPath()%>/json/admin/updateFctrWkmnsp",
				data: {formData:JSON.stringify(paramData)},
			success:function( data ) {
				if(data.result!=null){
					//alert("저장 되었습니다.");
					Helper.MessageBox("저장 되었습니다.");
				}		
			},
			complete:function(){
				$(".loadingA").hide(); 
			} 
		});	
    	
    });

	//콤보 박스 이벤트 설정
    $('.selectbox').on('change.selectbox', function(e, param) {
        if (param === undefined) {
            return;
        }
        
        if(param.value == '' || param.value == undefined){
        	return;
        }
    });    
    
}, 10);
	
});

function initRetainTable(list){
	let $retainGrid = $("#retain-grid tbody");
	
	$("#btnrRetainTranSave").off('click').on('click', function(){
		Helper.ConfirmBox('본부별 리테인 전송 허용 여부를 저장 하시겠습니까?', function(flag){
			if(flag){
				
				let list = [];
				
				$retainGrid.find("input:radio:checked").each(function(obj, idx){
					let $input = $(this);
					list.push({
						cd: $input.attr("name"),
						nm: $input.closest('tr').find('td:first').text(),
						val: $input.val()
					}) 
				}).promise().done(function(){
					if(list.length == 0){
						Helper.MessageBox("저장할 데이터가 없습니다.");	
					}else{
						Helper.post("/admin/save/retainProf", {list: list})
						.done(function(){
							Helper.MessageBox("저장 완료 되었습니다.");
						})	
					}
				})		
			}
		})
	});
	
	Helper.post("/admin/get/retainProf")
	.done(function(list){
		for(let i=0; i<list.length; i++){
			let $tr = $("<tr></tr>");
			$tr.append("<td>"+ list[i].cd + "/" + list[i].nm +"</td>");
			$tr.append("<td>"
							+ "<span><input type='radio' name='" + list[i].cd + "' id='"+ list[i].cd +"1' value='Y' " + (list[i].val === 'Y'?"checked":"") + "><label for='"+ list[i].cd +"1' style='margin-left:5px;'>Y</label></span>"
							+ "<span style='display:inline-block;margin-left:20px;'><input type='radio' name='" + list[i].cd + "' id='"+ list[i].cd +"2' value='N' " + (list[i].val !== 'Y'?"checked":"") + "><label  for='"+ list[i].cd +"2' style='margin-left:5px;'>N</label></span>"
						+"</td>");
			$retainGrid.append($tr);
		}	
	})
}


function selectProfFctrList(){
	if($("#cbo_prfl").val()==""){
		//alert('버전을 선택해 주세요.');
		Helper.MessageBox("버전을 선택해 주세요.");
		return;
	}

	$(".loadingA").show(); 
	var paramData = { prflId : $("#cbo_prfl").val() };
	var formData = {};
	$.ajax({
		type: "POST",
			url: "<%=request.getContextPath()%>/json/admin/profFctrList",
			data: {formData:JSON.stringify(paramData)},
		success:function( data ) {			
			$('.txtDesc').empty();
			$('.txtDesc').append("<em>Description</em> : " + data.prflList[0].DSCRT);
			
			$('#prof-grid > colgroup').empty();
			$('#prof-grid > colgroup').append("<col style=\"width:auto\"><col style=\"width:" + 100 / parseInt(data.profDataList.length) + "%\" span=\"" + data.profDataList.length + "\"></colgroup>\n");
			
			var str = "";
			if(data.profTitList != null){
				$('#prof-grid > thead > tr').empty();
				str = "<th scope=\"col\">자격 구분</th>\n";				
				for(var i=0;i<data.profTitList.length;i++){
					if(data.profTitList[i].CERT_CNT > 1){
						str += "<th scope=\"col\" colspan=\"" + data.profTitList[i].CERT_CNT + "\">" + data.profTitList[i].CERT_NM + " 회계사</th>\n";
					}else{
						str += "<th scope=\"col\">" + data.profTitList[i].CERT_NM + " 회계사</th>\n";	
					}					
				}
				$('#prof-grid > thead > tr').append(str);
			}
			
			if(data.profDataList != null){
				$('#prof-grid > tbody').empty();
				str = "<tr>\n";
				str += "	<td>경력</td>\n";
				for(var i=0;i<data.profDataList.length;i++){
					str += "<td><div class=\"frmNowrap etc02\"><input type=\"number\" name=\"txt_profYear\" value=\"" + data.profDataList[i].CERT_YCNT + "\" class=\"tright\"><span class=\"unit\">년</span></div></td>\n";
				}
				str += "</tr>\n";
				str += "<tr>\n";
				str += "	<td>숙련도</td>\n";
				for(var i=0;i<data.profDataList.length;i++){
					str += "<td><div class=\"frmNowrap etc02\">";   
					str += "<input type=\"number\" name=\"txt_wkmnsp\" value=\"" + data.profDataList[i].WKMNSP + "\" class=\"tright\">";					
					str += "<input type=\"hidden\" name=\"hid_wkmnsp\" value=\"" + data.profDataList[i].WKMNSP + "\">";
					str += "<input type=\"hidden\" name=\"hid_prflId\" value=\"" + data.profDataList[i].PRFL_ID + "\">";
					str += "<input type=\"hidden\" name=\"hid_certDv\" value=\"" + data.profDataList[i].CERT_DV + "\">";
					str += "<input type=\"hidden\" name=\"hid_certYcnt\" value=\"" + data.profDataList[i].CERT_YCNT + "\">";
					str += "</div></td>\n";
				}
				str += "</tr>\n";
				$('#prof-grid > tbody').append(str);
			}
			
			var fctrColCnt = 0;
			var fctrGrpCnt = 0;
			if(data.fctrDataList!=null){
				$('#fctr-grid > thead > tr').empty();
				str = "<th scope=\"col\">구분</th>\n";
				for(var i=0;i<data.fctrDataList.length;i++){
					if(data.fctrDataList[i].GRP_ORDBY != '1'){
						break;
					}
					str += "<th scope=\"col\">" + data.fctrDataList[i].FACTOR_NM + "</th>\n";
					fctrColCnt++;
				}
				$('#fctr-grid > thead > tr').append(str);
				
				$('#fctr-grid > tbody').empty();
				fctrGrpCnt = data.fctrDataList.length / fctrColCnt;
				str = "";
				for(var i=0;i<fctrGrpCnt;i++){
					str += "<tr>\n";					
					for(var j=(i*fctrColCnt);j<((i+1)*fctrColCnt);j++){
						if(j==(i*fctrColCnt)){
							str += "<td>" + data.fctrDataList[j].GRP_NM + "</td>\n";
						}
						//값이 둘다 있을 때~~
						if(emptyToBlank(data.fctrDataList[j].VAL1) != '' && emptyToBlank(data.fctrDataList[j].VAL2) != ''){
							str += "<td><span class=\"txtRead\">" + emptyToBlank(data.fctrDataList[j].VAL1) + "~" + emptyToBlank(data.fctrDataList[j].VAL2) + "</span>\n";
						}else if(emptyToBlank(data.fctrDataList[j].VAL1) != ''){
							str += "<td><span class=\"txtRead\">" + emptyToBlank(data.fctrDataList[j].VAL1) + "</span>\n";
						}else{
							str += "<td><span class=\"txtRead\"></span>\n";
						}
						str += "<div class=\"tipClick tipFilter\"><a href=\"#\" class=\"btIco btFilter\">입력</a>\n";
						str += "	<div class=\"tipCont\">\n";
						str += "		<ul class=\"filList\">\n";
						str += "			<li><b>설정 방법</b>\n";
						str += " 				<div class=\"frmArea\">\n";
						//값이 둘다 있다면 Range
						if(emptyToBlank(data.fctrDataList[j].VAL1) != '' && emptyToBlank(data.fctrDataList[j].VAL2) != ''){
							str += " 					<span class=\"frmInp\"><input type=\"radio\" id=\"radSetting_" + j + "_01\" name=\"radSetting_" + j + "\"><label for=\"radSetting_" + j + "_01\">Fixed</label></span>\n";
							str += " 					<span class=\"frmInp\"><input type=\"radio\" id=\"radSetting_" + j + "_02\" name=\"radSetting_" + j + "\" checked><label for=\"radSetting_" + j + "_02\">Range</label></span>\n";
						}else if(emptyToBlank(data.fctrDataList[j].VAL1) != ''){
							str += " 					<span class=\"frmInp\"><input type=\"radio\" id=\"radSetting_" + j + "_01\" name=\"radSetting_" + j + "\" checked><label for=\"radSetting_" + j + "_01\">Fixed</label></span>\n";
							str += " 					<span class=\"frmInp\"><input type=\"radio\" id=\"radSetting_" + j + "_02\" name=\"radSetting_" + j + "\"><label for=\"radSetting_" + j + "_02\">Range</label></span>\n";
						}else{//둘 다 없어~~
							str += " 					<span class=\"frmInp\"><input type=\"radio\" id=\"radSetting_" + j + "_01\" name=\"radSetting_" + j + "\"><label for=\"radSetting_" + j + "_01\">Fixed</label></span>\n";
							str += " 					<span class=\"frmInp\"><input type=\"radio\" id=\"radSetting_" + j + "_02\" name=\"radSetting_" + j + "\"><label for=\"radSetting_" + j + "_02\">Range</label></span>\n";							
						}
						str += "				</div>\n";
						str += "			</li>\n";
						//값이 둘다 있다면 Range
						if(emptyToBlank(data.fctrDataList[j].VAL1) != '' && emptyToBlank(data.fctrDataList[j].VAL2) != ''){
							str += " 		<li><b>정보 입력</b><input name=\"num_fctrData1\" value=\"" + emptyToBlank(data.fctrDataList[j].VAL1) + "\" type=\"number\" style=\"width:84px;\"><span class=\"dash\">~</span><input name=\"num_fctrData2\" value=\"" + emptyToBlank(data.fctrDataList[j].VAL2) + "\" type=\"number\" style=\"width:84px;\">";						
							str += " 			<input name=\"hid_fctrData1\" value=\"" + emptyToBlank(data.fctrDataList[j].VAL1) + "\" type=\"hidden\"><input name=\"hid_fctrData2\" value=\"" + emptyToBlank(data.fctrDataList[j].VAL2) + "\" type=\"hidden\">";
							str += "		</li>\n";
						}else{
							str += " 		<li><b>정보 입력</b><input name=\"num_fctrData1\" value=\"" + emptyToBlank(data.fctrDataList[j].VAL1) + "\" type=\"number\" style=\"width:188px;\"><span class=\"dash\" style=\"display:none\">~</span><input name=\"num_fctrData2\" value=\"\" type=\"number\" style=\"display:none\">";						
							str += " 			<input name=\"hid_fctrData1\" value=\"" + emptyToBlank(data.fctrDataList[j].VAL1) + "\" type=\"hidden\"><input name=\"hid_fctrData2\" value=\"\" type=\"hidden\">";
							str += "		</li>\n";
						}						
						str += " 			<input name=\"hid_prflId\" value=\"" + data.fctrDataList[j].PRFL_ID + "\" type=\"hidden\">";
						str += " 			<input name=\"hid_grpCd2\" value=\"" + data.fctrDataList[j].GRP_CD + "\" type=\"hidden\">";
						str += " 			<input name=\"hid_factorCd\" value=\"" + data.fctrDataList[j].FACTOR_CD + "\" type=\"hidden\">";
						str += " 		</ul>\n";
						str += " 		<div class=\"btnArea\">\n";
						str += " 			<button type=\"button\" class=\"btnPwc btnS\">Cancel</button>\n";
						str += " 			<button type=\"button\" class=\"btnPwc btnS action\">Save</button>\n";
						str += " 		</div>\n";						
						str += " 		<button type=\"button\" class=\"close\">닫기</button>\n";
						str += " 	</div>\n";
						str += "</div>\n";
						str += "</td>\n";
					}				
					str += "</tr>\n";
				}
				$('#fctr-grid > tbody').append(str);
				
				buttonBindEvent();
			}					
		},
		complete:function(){
			$(".loadingA").hide(); 
		} 
	});	
}

function buttonBindEvent(){    
	//저장
    $("button[class='btnPwc btnS action']").each(function(i){
		$(this).click(function(e){
			e.preventDefault();

			var val1, val2, prflId, grpCd, factorCd;
			prflId   = $("input[name='hid_prflId']").eq(i).val();
			grpCd    = $("input[name='hid_grpCd2']").eq(i).val();
			factorCd = $("input[name='hid_factorCd']").eq(i).val();
			if($("input[name='radSetting_" + i + "']").eq(0).prop("checked")){
				if(emptyToBlank($("input[name='num_fctrData1']").eq(i).val()) == ''){
					//alert("정보 입력이 비어 있습니다.");
					Helper.MessageBox("정보 입력이 비어 있습니다.");
					$("input[name='num_fctrData1']").eq(i).focus();
					return;
				}
				
				//Fixed
				val1 = $("input[name='num_fctrData1']").eq(i).val();
				val2 = '';
			}else{
				if(emptyToBlank($("input[name='num_fctrData1']").eq(i).val()) == ''){
					//alert("정보 입력(Min)이 비어 있습니다.");
					Helper.MessageBox("정보 입력(Min)이 비어 있습니다.");
					$("input[name='num_fctrData1']").eq(i).focus();
					return;
				}
				if(emptyToBlank($("input[name='num_fctrData2']").eq(i).val()) == ''){
					//alert("정보 입력(Max)가 비어 있습니다.");
					Helper.MessageBox("정보 입력(Max)가 비어 있습니다.");
					$("input[name='num_fctrData2']").eq(i).focus();
					return;
				}

				//Range	
				val1 = $("input[name='num_fctrData1']").eq(i).val();
				val2 = $("input[name='num_fctrData2']").eq(i).val();
			}
			
			$(".loadingA").show(); 

			var paramData = { prflId : prflId  
					        , grpCd : grpCd
					        , factorCd : factorCd
					        , val1 : val1
					        , val2 : val2	
			};
			var formData = {};
			$.ajax({
				type: "POST",
					url: "<%=request.getContextPath()%>/json/admin/updatePrflGrpFctr",
					data: {formData:JSON.stringify(paramData)},
				success:function( data ) {
					if(data.result!=null){
						//alert("저장 되었습니다.");
						Helper.MessageBox("저장 되었습니다.");

						if($("input[name='radSetting_" + i + "']").eq(0).prop("checked")){
							$("span[class='txtRead']").eq(i).html( $("input[name='num_fctrData1']").eq(i).val() );
						}else{
							$("span[class='txtRead']").eq(i).html( $("input[name='num_fctrData1']").eq(i).val() + "~" + $("input[name='num_fctrData2']").eq(i).val() );
						}
						$('#fctr-grid > tbody div .tipCont').eq(i).hide();
					}		
				},
				complete:function(){
					$(".loadingA").hide(); 
				} 
			});	
		});

	});	
    
    //Cancel 처리
    $("button[class='btnPwc btnS']").each(function(i){
		$(this).click(function(e){
			e.preventDefault();
			//alert(i+'cancel 버튼 '+this.style.background);			
			$('#fctr-grid > tbody div .tipCont').eq(i).hide();
		});

	});	
    
    $("input:radio").each(function(i){
		$(this).click(function(e){
			//e.preventDefault();

			var radioGrpNum;
			var selectedRadio;
			var str;
			if(i > 0){
				radioGrpNum = parseInt(i/2)
				selectedRadio = parseInt(i%2)
			}else{
				radioGrpNum = 0;
				selectedRadio = 0;
			}
			//기존값 보관 			
			var oldFctrData1 = emptyToBlank($("input[name='hid_fctrData1']").eq(radioGrpNum).val());
			var oldFctrData2 = emptyToBlank($("input[name='hid_fctrData2']").eq(radioGrpNum).val());

			if(selectedRadio == 0){
				$("input[name='num_fctrData1']").eq(radioGrpNum).css("width", "188px");				
				$("input[name='num_fctrData2']").eq(radioGrpNum).hide();
				$("span[class='dash']").eq(radioGrpNum).hide();
			}else{
				$("input[name='num_fctrData1']").eq(radioGrpNum).css("width", "84px");
				$("input[name='num_fctrData2']").eq(radioGrpNum).css("width", "84px");
				$("input[name='num_fctrData2']").eq(radioGrpNum).show();
				$("span[class='dash']").eq(radioGrpNum).show();
			}				
			if($("input[name='radSetting_" + radioGrpNum + "']").eq(selectedRadio).prop("checked", true));

		});
	});	
    /*
    0,1 - 0   
    2,3 - 1  
    4,5 - 2
    6,7 - 3
    8,9 - 4
    */       
}

function emptyToBlank(obj){
	var result; 
	if(obj == 'undefined' || obj == undefined){
		result = '';
	}else{
		result = obj;
	}
	return result;		
}

</script>	
         
                