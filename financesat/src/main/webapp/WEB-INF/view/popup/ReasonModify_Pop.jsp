<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="popup-reason-modify" style="padding-bottom: 20px">
	<div class="layCont" style="height: 300px;">
		<div>
			<strong>수정 사유</strong>
		</div>
		<div class="marT10">
			<div class="selectbox frmW01">
				<select ref="reasonModify" title="수정 사유">
					<option value="">선택하세요.</option>
					<c:forEach var="code" items="${result.data}">
						<option value="${code.cd}">${code.nm}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<hr />
		<div class="marT30">
			<strong>기타(직접 서술)</strong>
		</div>
		<div class="marT10">
			<textarea v-model="modReasonCmnt" rows="6" style="width: 100%" :disabled="modReasonCd !== '99'"
				:style="{backgroundColor: modReasonCd !== '99' ? '#f7f7f7' : 'white'}"
			></textarea>
			<div style="text-align: right; margin-top: 5px;">
				<span :style="{color: curLengh > maxLength ? 'red' : 'inherit'}">{{curLengh}}</span>&nbsp;/&nbsp;{{maxLength}}
			</div>
		</div>
	
	</div>
	<div class="btnArea">
		<button @click="onCancel" type="button" class="btnPwc btnL">취소</button>
		<button @click="onReqApprove" type="button" class="btnPwc btnL action">결재 요청</button>
	</div>
</div>

<script>
new Vue({
	el: "#popup-reason-modify",
	data: {
		selectbox: null,
		modReasonCd: '',
		modReasonNm: '',
		modReasonCmnt: '',
		maxLength: 200,
	},
	computed: {
		curLengh: function() {
			return this.modReasonCmnt.length
		},
	},
	mounted: function() {
		const thisObj = this;
		this.selectbox = new SelectBox($(this.$refs.reasonModify), {
		    height: 140,
		    multiText: '|',
		    direction: "down",
		    
		});
		this.selectbox.initSB();
		
		$(this.$refs.reasonModify).on('change.selectbox', function (e) {
			thisObj.modReasonCd = e.target.value; 
			thisObj.modReasonNm = e.target.options[e.target.selectedIndex].text;
		})
	},
	methods: {
		onCancel: function() {
			Helper.closePopup("${popupInfo.id}");
		},
		onReqApprove: function() {
			if(this.modReasonCd === '99') {				
				if(this.modReasonCmnt.length < 10) {
					Helper.MessageBox('최소 10자 이상 입력하세요.')
					return;
				}
				if(this.modReasonCmnt.length > this.maxLength) {
					Helper.MessageBox('최대 '+ this.maxLength + '자 까지 입력 가능합니다.')
					return;
				}
			}
			const callback = $(document).data("${popupInfo.callbackId}");
			if(callback){
				callback({
					modReasonCd: this.modReasonCd,
					modReasonCmnt: this.modReasonCd === '99' ? this.modReasonCmnt : this.modReasonNm,
				})
			}
			Helper.closePopup("${popupInfo.id}");
		}
	}
})
</script>