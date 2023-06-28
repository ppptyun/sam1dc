<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="layCont">
	<div class="searchBox marT5">
		<input type="text" name="searchText">
		<button id="popBtnSearch" type="button" class="btIco btnSearch gapL">검색</button>
	</div>
	
	<div id="pop-project-grid" class="gridbox gridHp08">그리드 영역</div>

	<div class="btnArea">
		<button id="popBtnCancle" type="button" class="btnPwc btnL">취소</button>
		<button id="popBtnOk" type="button" class="btnPwc btnL action">선택</button>
	</div>
</div>

<script type="text/javascript">
$(document).ready(function(){
	// 이벤트 바인딩
	$("#layPopup-${popupInfo.id} #popBtnCancle").off('click').on('click', function(){Helper.closePopup("${popupInfo.id}");});
	$("#layPopup-${popupInfo.id} #popBtnSearch").off('click').on('click', search);
	$("#layPopup-${popupInfo.id} #popBtnOk").off('click').on('click', onClickBtnOk);
	$("#layPopup-${popupInfo.id} input[name='searchText']").off('keyup').on('keyup', function(e){
		if(e.keyCode == 13){
			search();
		}
	});
	
	// grid
	const prjtGrid = {
		parent: "pop-project-grid",
		skin: THEME_INFO.THEME,
		image_path: THEME_INFO.THEME_IMG_PATH,
		columns: [
			{id:"prjtnm", label:["Project"], type:"ro", align:"left"},
			{id:"prjtcd", label:["Code"], type:"ro", align:"center", sort:"str", width:"120"},
			{id:"chargbon", label:["DIV"], type:"ro", align:"center", sort:"str", width:"70"},
			{id:"dspChargptr", label:["Partner"], type:"ro", align:"left", sort:"str", width:"120"},
			{id:"dspChargmgr", label:["Manager"], type:"ro", align:"left", sort:"str", width:"120"}
		]
	};
	let prjtGridObj = new dhtmlXGridObject(prjtGrid);
	prjtGridObj.attachEvent('onRowDblClicked', onClickBtnOk);
	
	prjtGridObj.setStyle("text-align:left", "", "", "");
	$("#pop-project-grid .xhdr table tbody tr").children("td:eq(1)").css("text-align", "center");
	$("#pop-project-grid .xhdr table tbody tr").children("td:eq(2)").css("text-align", "center");
	
	
	$("input[name='searchText']").focus();
	
	function search(){
		const searchText = $.trim($("#layPopup-${popupInfo.id} input[name='searchText']").val());
		if(searchText === ''){
			Helper.MessageBox("프로젝트 이름 또는 프로젝트 코드를 입력하세요.");
			return;
		}else if(searchText.length < 2){
			Helper.MessageBox("검색어는 최소 2자 이상 입력하세요.");
			return;
		}
	
		prjtGridObj.clearAll();
		Helper.post("/popup/project", {searchText: searchText, isMain:"${popupInfo.isMain}"})
		.done(function(data){
			if(data.length > 0){
				grid_noData("pop-project-grid", false);
				prjtGridObj.parse(data, 'js');
			}else{
				$("input[name='searchText']").val("");
				grid_noData("pop-project-grid", true, "검색 결과가 없습니다.");
			}
		});
	}
	
	function onClickBtnOk(){
		const rid = prjtGridObj.getSelectedRowId();
		
		if(!rid){
			Helper.MessageBox("프로젝트를 선택하세요.");
			return;
		}
		
		const isMainExist = prjtGridObj.getUserData(rid, 'isMainExist');
		const isSubExist = prjtGridObj.getUserData(rid, 'isSubExist');
		
		if(isMainExist === 'Y' || isSubExist === 'Y'){
			Helper.MessageBox('선택하신 Project는 시스템에 등록되어 있습니다.(합산 대상 코드를 포함)');
			return;
		}

		
		const callback = $(document).data("${popupInfo.callbackId}");
		if(callback){
			callback({
				prjtCd: prjtGridObj.getUserData(rid, 'prjtcd'),
				prjtCd1: prjtGridObj.getUserData(rid, 'prjtcd1'),
				prjtCd2: prjtGridObj.getUserData(rid, 'prjtcd2'),
				prjtCd3: prjtGridObj.getUserData(rid, 'prjtcd3'),
				prjtNm: prjtGridObj.getUserData(rid, 'prjtnm'),
				prjtFrdt: prjtGridObj.getUserData(rid, 'prjtFrdt'),
				prjtTodt: prjtGridObj.getUserData(rid, 'prjtTodt'),
				bizFrdt: prjtGridObj.getUserData(rid, 'bizFrdt'),
				bizTodt: prjtGridObj.getUserData(rid, 'bizTodt'),
				clntNm: prjtGridObj.getUserData(rid, 'clntnm'),
				shrtNm: prjtGridObj.getUserData(rid, 'shrtnm'),
				chargPtr:{
					emplno: prjtGridObj.getUserData(rid, 'chargptr'),
					kornm: prjtGridObj.getUserData(rid, 'chargptrNm'),
					gradnm: prjtGridObj.getUserData(rid, 'chargptrGradNm'),
				},
				chargMgr:{
					emplno: prjtGridObj.getUserData(rid, 'chargmgr'),
					kornm: prjtGridObj.getUserData(rid, 'chargmgrNm'),
					gradnm: prjtGridObj.getUserData(rid, 'chargmgrGradNm'),
				},
				cntrtFee: prjtGridObj.getUserData(rid, 'cntrtFee'),
				rprtScdlDt: prjtGridObj.getUserData(rid, 'rprtScdlDt'),
				listDvCd: prjtGridObj.getUserData(rid, 'listDvCd'),
				bizRprtYn: prjtGridObj.getUserData(rid, 'bizRprtYn'),
				ifrsYn: prjtGridObj.getUserData(rid, 'ifrsYn'),
			})
		}
		
		Helper.closePopup("${popupInfo.id}");
	}
});
</script>


