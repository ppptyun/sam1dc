<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div id="popup-simple-wkmnsp" class="layCont">
	<table  class="tblForm tblD">
		<colgroup>
			<col style="width:30%"><col style="width:20%"><col style="width:auto">
		</colgroup>
		<thead>
			<tr>
				<th style="text-align: center">구분</th>
				<th style="text-align: center">숙련도</th>
				<th style="text-align: center">시간입력</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>담당이사 투입시간</td>
				<td><div class="frmNowrap"><span>{{dirWkmnsp | formatNumberFilter(3)}}</span></div></td>
				<td>
					<div class="frmNowrap">
						<number id="dirTm" v-model="dirTm"></number><span class="unit">hr(s)</span>
					</div>
				</td>
			</tr>
			<tr>
				<td>등록회계사 투입시간</td>
				<td><div class="frmNowrap"><span>{{mgrWkmnsp | formatNumberFilter(3)}}</span></div></td>
				<td>
					<div class="frmNowrap">
						<number id="mgrTm" v-model="mgrTm"></number><span class="unit">hr(s)</span>
					</div>
				</td>
			</tr>
			<tr>
				<td>수습회계사 투입시간</td>
				<td><div class="frmNowrap"><span>{{jrWkmnsp | formatNumberFilter(3)}}</span></div></td>
				<td>
					<div class="frmNowrap">
						<number id="jrTm" v-model="jrTm"></number><span class="unit">hr(s)</span>
					</div>
				</td>
			</tr>
			<tr>
				<td>기타전문가 투입시간</td>
				<td><div class="frmNowrap"><span>{{wkmnsp | formatNumberFilter(3)}}</span></div></td>
				<td>
					<div class="frmNowrap">
						<number id="etcTm" v-model="etcTm"></number><span class="unit">hr(s)</span>
					</div>
				</td>
			</tr>
		</tbody>
		<tbody>
			<tr>
				<th colspan="2">숙련도 계산 결과</th>
				<td><div class="frmNowrap"><b class="colPoint01">{{calWkmnsp}}</b></div></td>
			</tr>
		</tbody>
	</table>
	
	<div class="btnArea">
		<button type="button" class="btnPwc btnL" @click="onClose">닫기</button>
		<button type="button" class="btnPwc btnL action"  @click="onOk">숙련도 계산 결과 입력</button>
	</div>
</div>
<script>
setTimeout(function(){
	new Vue({
		el: '#popup-simple-wkmnsp',
		data: {
			dirTm: null,
			mgrTm: null,
			jrTm: null,
			etcTm: null,
			dirWkmnsp: 1.2,
			mgrWkmnsp: 1,
			jrWkmnsp: 0.4,
			wkmnsp: Number('${popupInfo.wkmnsp}'),
		},
		computed: {
			calWkmnsp: function(){
				let wkmnsp = Helper.round((this.dirTm*this.dirWkmnsp + this.mgrTm*this.mgrWkmnsp + this.jrTm*this.jrWkmnsp + this.etcTm*this.wkmnsp)
					       / (this.dirTm + this.mgrTm + this.jrTm + this.etcTm), 3);
				return isNaN(wkmnsp)?'':wkmnsp;
			}
		},
		methods: {
			onOk: function(){
				const callback = $(document).data("${popupInfo.callbackId}");
				
				if(this.wkmnsp === ''){
					Helper.MessageBox('계산 결과 값이 없습니다.')
					return;
				}
				
				if(callback) callback(this.calWkmnsp)
				Helper.closePopup("${popupInfo.id}");
			},
			onClose: function(){
				Helper.closePopup("${popupInfo.id}");
			}
		}
	})
}, 5)
</script>

