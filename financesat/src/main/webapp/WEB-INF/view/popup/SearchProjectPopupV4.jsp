<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="layCont">
	<div style="margin-bottom:10px;">Budget 대상에 체크한 프로젝트만 선택이 가능합니다.</div>
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
	$("#layPopup-${popupInfo.id} #popBtnOk").off('click').on('click', onClickBtnOk);
	// grid
	const prjtGrid = {
		parent: "pop-project-grid",
		skin: THEME_INFO.THEME,
		image_path: THEME_INFO.THEME_IMG_PATH,
		columns: [
			{id:"prjtnm", label:["Project"], type:"ro", align:"left"},
			{id:"prjtcd", label:["Code"], type:"ro", align:"center", sort:"str", width:"120"}
		]
	};
	let prjtGridObj = new dhtmlXGridObject(prjtGrid);
	prjtGridObj.attachEvent('onRowDblClicked', onClickBtnOk);
	
	prjtGridObj.setStyle("text-align:left", "", "", "");
	$("#pop-project-grid .xhdr table tbody tr").children("td:eq(1)").css("text-align", "center");
	$("#pop-project-grid .xhdr table tbody tr").children("td:eq(2)").css("text-align", "center");
	
	loadData();
	
	
	function loadData(){
		prjtGridObj.clearAll();
		let list = ${popupInfo.list};
		if(list.length > 0){
			grid_noData("pop-project-grid", false);
			prjtGridObj.parse(list, 'js');
		} else {
			grid_noData("pop-project-grid", true, "검색 결과가 없습니다.");
		}
	}
	function onClickBtnOk(){
		const rid = prjtGridObj.getSelectedRowId();
		
		if(!rid){
			Helper.MessageBox("프로젝트를 선택하세요.");
			return;
		}
		
		const callback = $(document).data("${popupInfo.callbackId}");
		const data = prjtGridObj.getRowData(rid);
		
		if(callback){
			callback({
				prjtCd: data.prjtcd,
				prjtNm: data.prjtnm,
			})
		}
		
		Helper.closePopup("${popupInfo.id}");
	}
});
</script>


