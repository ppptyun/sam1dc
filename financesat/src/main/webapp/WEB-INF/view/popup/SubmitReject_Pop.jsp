<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="maxBytesLength" value="400" />

<div class="layCont">
	<div class="textArea">
		<textarea col="10" row="10" class="rejectArea" placeholder="반려 의견을 입력하세요." id="rejectReason"></textarea>
		<div style="text-align:right">
			<span id="bytesLength">0</span>/${maxBytesLength}(bytes)
		</div>
	</div>
	<div class="btnArea">
		<button id="popBtnCancel" type="button" class="btnPwc btnL">Cancel</button>
		<button id="popBtnReject" type="button" class="btnPwc btnL action">Save</button>
	</div>	
</div>
<div id ="popup-dim"></div>

<script type="text/javascript">
(function($){
	// 이벤트 바인딩
	$("#layPopup-${popupInfo.id} #popBtnCancel").off("click").on("click", function(){Helper.closePopup("${popupInfo.id}");});
	$("#layPopup-${popupInfo.id} #popBtnReject").off('click').on('click', rejectInsert);
	
	$("#layPopup-${popupInfo.id} #rejectReason").on('keyup', function(){
		let bytesLength = Helper.getBytesLength($(this).val());
		
		$("#layPopup-${popupInfo.id} #bytesLength").text(bytesLength);
		if(bytesLength > ${maxBytesLength}){
			$("#layPopup-${popupInfo.id} #bytesLength").css("color", "red");
		}else{
			$("#layPopup-${popupInfo.id} #bytesLength").css("color", "inherit");
		}
	});
	
	function rejectInsert(){
		var rejectReason = $.trim($("#layPopup-${popupInfo.id} #rejectReason").val());
		let bytesLength = Helper.getBytesLength(rejectReason);
		
		if(rejectReason === ''){			
			Helper.MessageBox("반려 사유를 입력하세요.");
		}else if(bytesLength > ${maxBytesLength}){
			Helper.MessageBox("반려의견은 ${maxBytesLength}bytes를 넘을 수 없습니다.");
		}else{
			Helper.ConfirmBox("반려하시겠습니까?", "", function(isOK){				
				if(isOK) {
					Helper.post("/approval/reject", {prjtCd: "${popupInfo.prjtCd}", prjtNm:"${popupInfo.prjtNm}", rejectReason: rejectReason})
					.done(function(data){
						Helper.MessageBox("반려되었습니다.", function(){						
							Helper.goPage("/pages/approval/list");
						});
					})			
				}
			});	
		}	
	}
})(jQuery)
</script>
