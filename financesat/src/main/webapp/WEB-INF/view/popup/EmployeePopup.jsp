<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="layCont">
	<div class="searchBox marT5">
		<input type="text" name="searchText">
		<button id="popBtnSearch" type="button" class="btIco btnSearch gapL">검색</button>
	</div>

	<div id="emp-grid" class="gridbox gridHp08">그리드 영역</div>
	
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
	const empGrid = {
		parent: "emp-grid",
		skin: THEME_INFO.THEME,
		image_path: THEME_INFO.THEME_IMG_PATH,
		columns: [
			{id:"kornm", label:["이름"], type:"ro", align:"left", width:"120"},
			{id:"emplno", label:["사번"], type:"ro", align:"center", sort:"str", width:"160"},
			{id:"gradnm", label:["직급"], type:"ro", align:"left", sort:"str"},
			{id:"team", label:["소속"], type:"ro", align:"left", sort:"str"}			
		]
	};
	let empGridObj = new dhtmlXGridObject(empGrid);
	
	empGridObj.setStyle("text-align:left", "", "", "");
	$("#emp-grid .xhdr table tbody tr").children("td:eq(1)").css("text-align", "center");
	
	
	empGridObj.attachEvent('onRowDblClicked', onClickBtnOk);
	
	$("input[name='searchText']").focus();
	
	function search(){
		const searchText = $.trim($("#layPopup-${popupInfo.id} input[name='searchText']").val());
		if(searchText === ''){
			Helper.MessageBox("사번 또는 이름을 입력하세요");
			return;
		}
		
		empGridObj.clearAll();
		Helper.post("/popup/emp", {
			searchText: searchText,
			prjtCd: "${popupInfo.prjtCd}",
			satgrpCd: "${popupInfo.satgrpCd}"
		}).done(function(data){
			if(data.length > 0){
				grid_noData("emp-grid", false);
				empGridObj.parse(data, 'js');
			}else{
				$("input[name='searchText']").val("");
				grid_noData("emp-grid", true, "검색 결과가 없습니다.");
			}
			
		});
	}
	
	function onClickBtnOk(){
		const rid = empGridObj.getSelectedRowId();
		const callback = $(document).data("${popupInfo.callbackId}");
		if(callback){
			callback({
				kornm:empGridObj.getUserData(rid, 'kornm'),
				emplno:empGridObj.getUserData(rid, 'emplno'),
				gradnm:empGridObj.getUserData(rid, 'gradnm'),
				gradcd:empGridObj.getUserData(rid, 'gradcd'),
				wkmnsp:empGridObj.getUserData(rid, 'wkmnsp')
			})
		}
		
		Helper.closePopup("${popupInfo.id}");
	}
});
</script>
