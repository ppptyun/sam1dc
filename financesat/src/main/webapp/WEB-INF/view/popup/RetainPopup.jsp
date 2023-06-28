<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="layCont">
	<div style="">
		<ul>
			<li>- Retain 등록은 1회만 가능합니다.</li>
			<li>- TBD는 Retain에 등록되지 않습니다.</li>
			<li>- 아래 기준일 이후 Budget 정보를 바탕으로 Retain에 등록 됩니다.(기준일 포함)</li>
		</ul>
	</div>
	<div style="display:flex;align-items:center; margin-top:1em;margin-bottom:1em;">
		<div style="margin-left:2em; width: 120px"><strong>전송 기준일</strong></div>
		<div class="selectbox">
			<select id="popup-retain-base-date"  title="Retain 전송 기준일">
				<c:forEach var="week" items="${result.data.list}">
				<option value="${week.startDate}" ${result.data.selected == week.startDate?"selected":""}>${week.startDate}</option>
				</c:forEach>
			</select>
		</div>
	</div>
	<div style="display:flex;align-items:center; margin-top:1em;margin-bottom:1em;">
		<div style="margin-left:2em; width: 120px"><strong>전송 Location</strong></div>
		<c:forEach var="loca" items="${locaList}" varStatus="stat">
		<div style="display: inline-block; margin-right: 10px;">
			<input id="popup-loca-cd-${stat.index}" name="popup-loca-cd" type='checkbox' value="${loca.cd}" class='frmInp' checked } />
			<label for="popup-loca-cd-${stat.index}">&nbsp;${loca.nm}</label>
		</div>
		</c:forEach>
	</div>
	<div style="margin-top: 1em">
		<ul>
			<li>Retain 등록 하시겠습니까?</li>
			<li class="colPoint01">[주의] Retain 등록 후에는 본 시스템에서 되돌릴 수 없으니 주의 하시기 바랍니다.</li>
		</ul>
	</div>
	<div class="btnArea">
		<button id="popBtnCancle" type="button" class="btnPwc btnL">취소</button>
		<button id="popBtnOk" type="button" class="btnPwc btnL action">Retain 등록</button>
	</div>
</div>
	
<script type="text/javascript">
$(document).ready(function(){
	
	let retainSelectBox = new SelectBox($("#popup-retain-base-date"), {
	    height: 140,
	    multiText: '|',
	    direction: "down"
	});
	
	retainSelectBox.initSB();
	
	$("#layPopup-${popupInfo.id} #popBtnCancle").off('click').on('click', function(){Helper.closePopup("${popupInfo.id}");});
	$("#layPopup-${popupInfo.id} #popBtnOk").off('click').on('click', function(){
		let baseDt = $("#popup-retain-base-date").val();
		let location = [];
		$("input:checkbox[name='popup-loca-cd']:checked").each(function(value, b, c){
			location.push(this.value);
		})
		if(!location.includes('01')) {
			Helper.MessageBox('Field는 반드시 포함되어야 합니다.');
			return;
		}
		Helper.post("/project/budget/saveRetainSchedule", 
			{
				prjtCd:"${popupInfo.prjtCd}", 
				baseDt: Helper.replaceAll(baseDt, '/', '-'),
				location: location.join(',')
			}
		).done(function(){
			Helper.MessageBox("Retain Data 전송 예약이 완료하였습니다.", function(){										
				Helper.goPage("/pages/project/budget/read", {prjtCd: "${popupInfo.prjtCd}"});
			})
		});
	});
})

</script>
