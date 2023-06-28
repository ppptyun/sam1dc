<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<h1 class="hidden">Approval List</h1>
			
			<div class="boxWhite">
				
				<div class="searchBox">
					<dl class="item">
						<dt>Status</dt>
						<dd>
							<div class="frmArea">
								<span class="frmInp"><input type="radio" id="radStatus01" value="all" name="radStatus"><label for="radStatus01">전체</label></span>
								<span class="frmInp"><input type="radio" id="radStatus02" value="RQ" name="radStatus" checked><label for="radStatus02">결재 요청</label></span>
								<span class="frmInp"><input type="radio" id="radStatus03" value="RJ" name="radStatus"><label for="radStatus03">반려</label></span>
								<span class="frmInp"><input type="radio" id="radStatus04" value="CO" name="radStatus"><label for="radStatus04">결재 완료</label></span>
							</div>
						</dd>
					</dl>
					<div class="btns">
						<!-- button id="btnSearch" type="button" class="btnPwc btnM action">Search</button-->
					</div>
				</div>
				
				<div id="std-aprv-grid" class="gridbox gridHp06" style="width:100%;height:100%"> </div>
					
			
			</div><!-- //boxWhite -->

<script>

function openPrjt(prjtCd){
	Helper.goPage("/pages/approval/details", {prjtCd:prjtCd})
}

/* $.urlParam = function(name){
	var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
	return results[1] || 0;
} */

	
$(document).ready(function(){
	const prjtGrid = {
			parent: "std-aprv-grid",
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
				{id:"aprvReqDt", label:["상신일", "#select_filter"], type:"ro", align:"center", sort:"str", width:"80"},
				{id:"btn", label:["바로가기", "#rspan"], type:"btnAprv", align:"center", width:"120"}
			]
	};
	
	//const urlPar = decodeURIComponent($.urlParam('STAT'));
	const urlPar = "${stat}"||"RQ";
	if(urlPar){
		switch(urlPar){
		case 'all' :
			$('input:radio[name=radStatus]:input[value="all"]').attr("checked", true);
			break;
		case 'RQ' :
			$('input:radio[name=radStatus]:input[value="RQ"]').attr("checked", true);
			break;
		case 'RJ' :
			$('input:radio[name=radStatus]:input[value="RJ"]').attr("checked", true);
			break;
		case 'CO' :
			$('input:radio[name=radStatus]:input[value="CO"]').attr("checked", true);
			break;
		default :
			$('input:radio[name=radStatus]:input[value="RQ"]').attr("checked", true);
		}
	}
	
	let prjtGridObj = new dhtmlXGridObject(prjtGrid);
	prjtGridObj.setImagesPath(THEME_INFO.THEME_IMG_PATH);
	prjtGridObj.enableSmartRendering(true);
	prjtGridObj.setAwaitedRowHeight(45);
	prjtGridObj.setStyle("text-align:center", "", "", "");
	prjtGridObj.setNumberFormat("0,000",prjtGridObj.getColIndexById("cntrtAdtTm"),".",",");
	prjtGridObj.setNumberFormat("0,000",prjtGridObj.getColIndexById("totBdgtTm"),".",",");
	prjtGridObj.setNumberFormat("0,000",prjtGridObj.getColIndexById("spaBdgtTm"),".",",");
	prjtGridObj.setNumberFormat("0,000",prjtGridObj.getColIndexById("flcmBdgtTm"),".",",");
	prjtGridObj.setNumberFormat("0,000",prjtGridObj.getColIndexById("newStfBdgtTm"),".",",");
	
	prjtGridObj.enableCollSpan(true);
	prjtGridObj.attachEvent("onRowDblClicked", function(rId, cInd){
		var prjtCd = prjtGridObj.cells(rId, 0).getValue();
		Helper.goPage("/pages/approval/details", {prjtCd:prjtCd})
	});
	prjtGridObj.attachEvent("onXLE", function(){ 
		if (!prjtGridObj.getRowsNum()) {
			prjtGridObj.addRow(prjtGridObj.uid(), "조회 결과가 없습니다."); 
			prjtGridObj.enableMultiline(true);
			var rowID = prjtGridObj.getRowId(0);
			prjtGridObj.setColspan(rowID, 0, 12);
		}
		else {
			prjtGridObj.enableMultiline(false);
		}
	});
	
	bindEvent();
	loadData();
	
	function bindEvent(){
		$(document).on('click.gridResize', 'nav > button', function(e) { e.preventDefault(); prjtGridObj.setSizes();});
		$("#btnSearch").off('click').on('click', function(){			
			loadData();
		});
		
		prjtGridObj.attachEvent('onRowSelect', function(id){
			$(document).data("selectedPrjtCd", id)
		})
	}
	
	function loadData(){
		grid_noData("std-aprv-grid", false);
		prjtGridObj.clearAll();
		var st = $(":input:radio[name=radStatus]:checked").val();
		// $('input[name="radStatus"][value="'+ st + '"] + label').text()
		
		Helper.post("/approval/list", {STAT:st})
		.done(function(data){
			if(data.length > 0){
				prjtGridObj.parse(data, "js");
			}else{
				grid_noData("std-aprv-grid", true, "조회 결과가 없습니다.");
			}
		});
		
	}
	
	$(":input:radio[name=radStatus]").change(loadData);
	
	
});
</script>