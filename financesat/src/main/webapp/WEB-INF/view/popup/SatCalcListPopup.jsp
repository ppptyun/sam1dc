<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="layCont">
	<div id="pop-satcalc-grid" class="gridbox gridHp08">그리드 영역</div>

	<div class="btnArea">
		<button id="popBtnCancle" type="button" class="btnPwc btnL">취소</button>
		<button id="popBtnOk" type="button" class="btnPwc btnL action">선택</button>
	</div>
</div>

<script type="text/javascript">
$(document).ready(function(){
	// 이벤트 바인딩
	$("#layPopup-${popupInfo.id} #popBtnCancle").off('click').on('click', function(){Helper.closePopup("${popupInfo.id}");});
	$("#layPopup-${popupInfo.id} #popBtnOk").off('click').on('click', onClickBtnOk);
	
	// grid
	const satCalcGrid = {
		parent: "pop-satcalc-grid",
		skin: THEME_INFO.THEME,
		image_path: THEME_INFO.THEME_IMG_PATH,
		columns: [
			{id:"compNm", label:["회사명", "#text_filter"], type:"ro", align:"left", sort:"str"},
			{id:"bizFrdt", label:["사업연도 시작", "#select_filter"], type:"ro", align:"center", sort:"str", width:"100"},
			{id:"bizTodt", label:["사업연도 종료", "#select_filter"], type:"ro", align:"center", sort:"str", width:"100"},
			{id:"listDvNm", label:['상장구분', "#select_filter"], type:"ron", align:"left", sort:"str", width:"105"},
			{id:"satgrpNm", label:['그룹', "#select_filter_strict"], type:"ron", align:"center", sort:"str", width:"105"},
			{id:"calRateSat", label:['산식결과*적용요율', "#number_filter"], type:"ron", align:"right", sort:"int", width:"120"},
			{id:"calAdtTm", label:['산출된<br>감사시간(a)', "#number_filter"], type:"ron", align:"right", sort:"int", width:"120"},
			{id:"calSat", label:['숙련도 반영전 표준감사시간(a*b)', "#number_filter"], type:"ron", align:"right", sort:"int", width:"120"},
			{id:"etDfnSat", label:['한공회 표준감사시간', "#number_filter"], type:"ron", align:"right", sort:"int", width:"105"},
			{id:"crebyNm", label:["작성자", "#rspan"], type:"ro", align:"center", sort:"str", width:"80"},
		]
	};
	let satCalcGridObj = new dhtmlXGridObject(satCalcGrid);
	satCalcGridObj.enableSmartRendering(true, 100);
	satCalcGridObj.setNumberFormat("0,000",satCalcGridObj.getColIndexById("calRateSat"),".",",");
	satCalcGridObj.setNumberFormat("0,000",satCalcGridObj.getColIndexById("calAdtTm"),".",",");
	satCalcGridObj.setNumberFormat("0,000",satCalcGridObj.getColIndexById("calSat"),".",",");
	satCalcGridObj.setNumberFormat("0,000",satCalcGridObj.getColIndexById("etDfnSat"),".",",");
	
	satCalcGridObj.attachEvent('onRowDblClicked', onClickBtnOk);
	satCalcGridObj.setStyle("text-align:center", "", "", "");
	

	grid_noData("pop-satcalc-grid", false);
	Helper.post("/calcSat/list").done(function(data){
		if(data && data.length > 0){			
			satCalcGridObj.parse(data, "js")
		}else{
			grid_noData("pop-satcalc-grid", true, "조회 결과가 없습니다.");
		}
	})
	
	$("#pop-satcalc-grid .xhdr table tbody tr").children("td:eq(0)").css("text-align", "left");
	
	function onClickBtnOk(){
		const rid = satCalcGridObj.getSelectedRowId();
		const callback = $(document).data("${popupInfo.callbackId}");
		
		if(!rid){
			Helper.MessageBox("대상을 선택하세요.");
			return;
		}
		
		Helper.post('/calcSat/info', {prjtCd: rid})
		.done(function(data){
			let filter = ['bizFrdt', 'bizTodt','indusDv','listDv','indivAsset','bizRprtYn','consoAsset','satgrp1ExptYn','consoSales'
				 ,'consoAccntReceiv','rprtScdlDt','consoInvnt','satgrp','firstBizDt','contiAdtCnt','intrAdtYcnt'
				 ,'revwCnt','sameAdtrSbsidYn','intrTranAssetSales','sbsidAssetWithIntrTran','relatCompAsset'
				 ,'sbsidSalesWithIntrTran','satgrpList','ifrsYn','holdingsDv','firstAdtYn','usaListYn','kamYn'
				 ,'consoFinstatYn','sbsidCnt','currAdtopin','currConsoLossYn','priorAdtopinChgYn','priorLossYn'
				 ,'firstAdtFctrRange','priorAdtopinChgFctrRange','priorLossFctrRange','minMaxYn','minMaxReason'
				 ,'minMaxReasonDscrt','fstAdtTm','fstAdtrBaseWkmnspYn','fstAdtrWkmnsp','fstAdtTmReason','fstAdtTmReasonDscrt'
				 ,'fstWkmnspReason','fstWkmnspReasonDscrt','priorAdtr','priorBaseWkmnsp','priorAdtTm','priorAdtrBaseWkmnspYn'
				 ,'priorAdtrWkmnsp','priorAdtTmReason','priorAdtTmReasonDscrt','priorWkmnspReason','priorWkmnspReasonDscrt'
				 ,'intrAdtYn','intrAdtTm','maxRatio','intplSat','calResult','yearRate','calAdtTm','intrAdtFctr', 'etcFctr', 'calSat'
				 ,'baseWkmnsp','etDfnSat','etTrgtAdtTm', 'remark']
	
			let retData = {};
			for(let i in filter) retData[filter[i]] = data[filter[i]];
			if(callback) callback(retData)	
		})
		
		Helper.closePopup("${popupInfo.id}");
	}
});
</script>


