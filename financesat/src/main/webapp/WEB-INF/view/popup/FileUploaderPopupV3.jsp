<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="layCont">
	<p class="txtInfo">엑셀 파일 유형만 업로드 가능합니다.</p>
	<p class="txtInfo">현재 등록된 Budget정보는 모두 삭제되고 엑셀 데이터 기준으로 재입력 됩니다.</p>
	<div class="fileAttach">
		<input type="file" id="file_attach" > <span id="dsp_file" class="file"></span>
		<button type="button" class="btnPwc btnM">찾아보기</button>
	</div>

	<div class="btnArea">
		<button id="popBtnCancel" type="button" class="btnPwc btnL">Cancel</button>
		<button id="popBtnOk" type="button" class="btnPwc btnL action">OK</button>
	</div>
</div>

<script type="text/javascript">
(function(){
	let $input = $("#layPopup-${popupInfo.id} #file_attach");
	let $span = $("#layPopup-${popupInfo.id} #dsp_file");
	let accept = {
		xlsx: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
		xls: "application/vnd.ms-excel",
		csv: ".csv"
	}
	const callback = $(document).data("${popupInfo.callbackId}");
	
	let allowExtensions = [<c:forEach items="${popupInfo.allowExtensions}" var="extension">"${extension}",</c:forEach>];
	$input.attr("accept", allowExtensions.map(function(item){return accept[item]}).join(","))
	
	$input.off('change.upload').on('change.upload', function(){
		 let extension = Helper.getExtension($input.val());
		 $span.text($input.val());

		 if(allowExtensions.length > 0 && !allowExtensions.includes(extension)){
			Helper.MessageBox(["허용되지 않는 확장자입니다.", "업로드 가능 파일 확장자 : " + allowExtensions.join(", ")]);
			$input.val(null);
			$span.text(null);
			return;
		}
	});
	
	$("#layPopup-${popupInfo.id} #popBtnOk").off('click.pop-ok').on('click.pop-ok', function(){
		if($input.val() === null || $input.val() === undefined || $input.val() === ""){
			Helper.MessageBox("파일을 선택하세요.");
			return;
		}
		
		let formData = new FormData();
		formData.append("prjtCd", "${popupInfo.prjtCd}");
		formData.append("file", $input[0].files[0]);
		
		//console.log("${popupInfo.prjtCd}", "${popupInfo.importUrl}", formData)
		Helper.uploadFile("${popupInfo.importUrl}", formData)
		.done(function(){
			Helper.MessageBox("완료 되었습니다.", function(){
				Helper.closePopup("${popupInfo.id}");
				if(callback) callback();
			})
		})
	})
	
	$("#layPopup-${popupInfo.id} #popBtnCancel").off('click.pop-cancel').on('click.pop-cancel', function(){
		Helper.closePopup("${popupInfo.id}");
	})
})()
	
</script>
