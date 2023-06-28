<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="layCont">
	<div id="adtr-grid" class="gridbox" style="height:350px;">그리드 영역</div>
	<div>&nbsp;</div>
	<div>
		<div class='frmInp'>
			<input id="popup-adtrNmYn" type='checkbox' value="Y" class='frmInp'><label for=popup-adtrNmYn>&nbsp;직접 추가</label>
		</div>
		<input id="popup-adtrNm" disabled  style="margin-left:20px;width:250px;height:28px;padding:2px;" placeholder="감사인을 입력하세요."/>
	</div>
	<div class="btnArea">
		<button id="popBtnCancle" type="button" class="btnPwc btnL">취소</button>
		<button id="popBtnOk" type="button" class="btnPwc btnL action">확인</button>
	</div>
</div>

<script type="text/javascript">

$(document).ready(function(){
	// 이벤트 바인딩
	$("#layPopup-${popupInfo.id} #popBtnCancle").off('click').on('click', function(){Helper.closePopup("${popupInfo.id}");});
	$("#layPopup-${popupInfo.id} #popBtnOk").off('click').on('click', onClickBtnOk);
	$("#layPopup-${popupInfo.id} input[name='searchText']").off('keyup').on('keyup', function(e){
		if(e.keyCode == 13){
			search();
		}
	});
	
	// grid
	const adtrGrid = {
		parent: "adtr-grid",
		skin: THEME_INFO.THEME,
		image_path: THEME_INFO.THEME_IMG_PATH,
		columns: [
			{id:"nm", label:["감사인", "#text_filter"], type:"ro", sort:"str"}			
		]
	};
	
	let adtrGridObj = new dhtmlXGridObject(adtrGrid);
	adtrGridObj.setStyle("text-align:center", "", "", "");
	adtrGridObj.attachEvent('onRowDblClicked', onClickBtnOk);
	
	Helper.post("/popup/auditor/list")
	.done(function(list){
		console.log(list)
		adtrGridObj.parse(list, 'js');
	});
	
	$("#layPopup-${popupInfo.id} #popup-adtrNmYn").off('click').on('click', function(){
		console.log($(this).prop('checked'))
		if($(this).prop('checked')){
			$("#layPopup-${popupInfo.id} #popup-adtrNm").prop('disabled', false)
		}else{
			$("#layPopup-${popupInfo.id} #popup-adtrNm").prop('disabled', true)
		}
	})
	function onClickBtnOk(){
		const rid = adtrGridObj.getSelectedRowId();
		const callback = $(document).data("${popupInfo.callbackId}");
		let isClose = true;
		
		if($("#popup-adtrNmYn").prop("checked")){
			let nm = $.trim($("#popup-adtrNm").val());
			if($.trim($("#popup-adtrNm").val()) === ""){
				Helper.MessageBox("감사인 이름을 입력하세요.")
				return;
			}else{
				Helper.post("/popup/auditor/add", {nm: nm})
				.done(function(data){
					callback({
						cd: data.cd,
						nm: nm
					})
					Helper.closePopup("${popupInfo.id}");
				});
			}
		}else{
			callback({cd: rid, nm: adtrGridObj.cells(rid, 0).getValue()})
			Helper.closePopup("${popupInfo.id}");
		}
		
	}
});
</script>
