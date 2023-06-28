<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
.radio-container {
	display: inline-block;
	margin-left: -20px;
}
.radio-container label{
	margin-left: 5px;
}
.radio-container input {
	cursor: pointer;
	margin-left: 15px;
}
</style>
<div class="boxWhite" id="profilePage">
	<div class="titArea">
		<h2 class="titDep2  marT30">본부별 프로파일</h2>
		<div class="btnArea side">
			<button type="button" @click="btnSave" class="btnPwc btnM action">Save</button>
		</div>
	</div>
	
	<table id="retain-grid" class="tblH">
		<caption></caption>
		<colgroup>
			<col style="width:250px;">
			<col style="width:auto;">
			<col style="width:150px;">
			<col style="width:150px;">
			<col style="width:150px;">
		</colgroup>
		<thead>		
			<tr>
				<th scope="col">본부</th>
				<th scope="col">연관 팀코드</th>
				<th scope="col">Retain 전송<br>허용 여부</th>
				<th scope="col">표준감사시간 수정<br>허용 여부</th>
				<th scope="col">Budget 수정<br>허용 여부</th>
				<!-- 그룹 목록 -->
			</tr>
		</thead>
		<tbody>
			<tr v-for="item in list">
				<td style="text-align:left"><span class='bonbNm'>{{item.cd + '/' + item.nm}}</span></td>
				<td style="text-align:left"><span class='teamList'>{{item.teamList}}</span></td>
				<td style="text-align:center"><span class="radio-container">
					<input :id="item.cd + '_retainYn_Y'" :name="item.cd + '_retainYn'" type="radio" value="Y" v-model="item.retainYn" /><label :for="item.cd + '_retainYn_Y'">Y</label>
					<input :id="item.cd + '_retainYn_N'" :name="item.cd + '_retainYn'"  type="radio" value="N" v-model="item.retainYn" /><label :for="item.cd + '_retainYn_N'">N</label>
				</span></td>
				<td style="text-align:center"><span class="radio-container">
					<input :id="item.cd + '_satYn_Y'" :name="item.cd + '_satYn'" type="radio" value="Y"  v-model="item.satYn" /><label :for="item.cd + '_satYn_Y'">Y</label>
					<input :id="item.cd + '_satYn_N'" :name="item.cd + '_satYn'"  type="radio" value="N"  v-model="item.satYn" /><label :for="item.cd + '_satYn_N'">N</label>
				</span></td>
				<td style="text-align:center"><span class="radio-container">
					<input :id="item.cd + '_bdgtYn_Y'" :name="item.cd + '_bdgtYnYn'" type="radio" value="Y"  v-model="item.bdgtYn" /><label :for="item.cd + '_bdgtYn_Y'">Y</label>
					<input :id="item.cd + '_bdgtYn_N'" :name="item.cd + '_bdgtYnYn'"  type="radio" value="N"  v-model="item.bdgtYn" /><label :for="item.cd + '_bdgtYn_N'">N</label>
				</span></td>
			</tr>
		</tbody>					
	</table>
</div>
<script type="text/javascript">
let oData = {};
const divisionProfileObj = new Vue({
	el: '#profilePage',
	data: {
		isInit: false,
		list: [],
	},
	mounted: function(){
		this.loadData();
	},
	methods: {
		btnSave: function() {
			const thisObj = this;
			const saveList = this.list.filter(function(data, idx){
				console.log('retainYn : ', data.retainYn , oData[idx].retainYn );
				console.log('satYn : ', data.satYn , oData[idx].satYn );
				console.log('bdgtYn : ', data.bdgtYn , oData[idx].bdgtYn );
				return data.retainYn !== oData[idx].retainYn || data.satYn !== oData[idx].satYn || data.bdgtYn !== oData[idx].bdgtYn;
			});
			
			if(saveList.length === 0) {
				Helper.MessageBox('변경된 항목이 없습니다.');
			} else {
				Helper.post("/admin/save/retainProf", {list: saveList})
				.done(function(){
					Helper.MessageBox('저장 완료 되었습니다.')
					thisObj.loadData();
				})
			}
		},
		isNull: function(value) {
			return value === null || value === undefined || $.trim(value) === '';
		},
		loadData: function() {
			let thisObj = this;
			Helper.post("/admin/get/retainProf").done(function(list){
				oData = thisObj.deepFreeze(JSON.parse(JSON.stringify(list)));
				thisObj.list = list;
				thisObj.isInit = false;
			});	
		},
		deepFreeze : function(obj) {
			const thisObj = this;
			const props = Object.getOwnPropertyNames(obj);
			props.forEach(function(name) {
				const prop = obj[name];
				if (typeof prop === 'object' && prop !== null) {
					thisObj.deepFreeze(prop);
				}
			});
			return Object.freeze(obj);
		}
	}
});

	/* 
	 $(document).ready(function(){setTimeout(function(){
	 let initRetainProfList = [];
	
	 let $retainGrid = $("#retain-grid tbody");
	
	 // 이벤트 바인딩
	 bindEvent();
	
	 // 최초 데이터 load
	 $(".loadingA").show();
	 $.when(loadRetainProfData())
	 .done(function(retainProfList){
	 initRetainProfList = retainProfList;
	
	 // Retain 전송 Profile
	 for(let i=0; i<retainProfList.length; i++){
	 let $tr = $("<tr></tr>");
	 $tr.append("<td style='text-align:left'><span class='bonbNm'>"+ retainProfList[i].cd + "/" + retainProfList[i].nm +"</span></td>");
	 $tr.append("<td style='text-align:left'><span class='teamList'>"+retainProfList[i].teamList+"</span></td>");
	 $tr.append("<td>"
	 + "<span><input type='radio' name='" + retainProfList[i].cd + "' id='"+ retainProfList[i].cd +"1' value='Y' " + (retainProfList[i].val === 'Y'?"checked":"") + "><label for='"+ retainProfList[i].cd +"1' style='margin-left:5px;'>Y</label></span>"
	 + "<span style='display:inline-block;margin-left:20px;'><input type='radio' name='" + retainProfList[i].cd + "' id='"+ retainProfList[i].cd +"2' value='N' " + (retainProfList[i].val !== 'Y'?"checked":"") + "><label  for='"+ retainProfList[i].cd +"2' style='margin-left:5px;'>N</label></span>"
	 +"</td>");
	 $retainGrid.append($tr);
	 }
	
	 })
	 .always(function(){
	 $(".loadingA").hide();	
	 })
	
	
	
	 // Retain 전송 Profile 데이터 Load
	 function loadRetainProfData(){
	 let dfd = $.Deferred();
	 Helper.post("/admin/get/retainProf")
	 .done(function(list){
	 dfd.resolve(list);
	 })
	 .fail(function(){dfd.reject();});
	 return dfd.promise();
	 }
	
	 // 버튼 이벤트 
	 function bindEvent(){
	 $("#btnrRetainTranSave").off('click').on('click', function(){
	 Helper.ConfirmBox('본부별 리테인 전송 허용 여부를 저장 하시겠습니까?', function(flag){
	 if(flag){
	 let list = [];
	
	 $retainGrid.find("input:radio:checked").each(function(obj, idx){
	 let $input = $(this);
	
	 list.push({
	 cd: $input.attr("name"),
	 nm: $input.closest('tr').find('.bonbNm').text(),
	 val: $input.val()
	 }) 
	 }).promise().done(function(){
	 let saveList = $.grep(list, function(data){
	 // 비교대상 데이터
	 let initRetainProfData = $.grep(initRetainProfList, function(data2){return data2.cd == data.cd})[0];
	 // value가 다를 경우에만 저장
	 return initRetainProfData.val != data.val
	 });
	
	 if(saveList.length == 0){
	 Helper.MessageBox("변경된 데이터가 없습니다.");	
	 }else{
	 Helper.post("/admin/save/retainProf", {list: saveList})
	 .done(function(){
	 initRetainProfList = list;	// 변경된 내역 반영
	 Helper.MessageBox("저장 완료 되었습니다.");
	 });
	 }
	 })		
	 }
	 })
	 });
	 }
	 }, 10);}); */
</script>
