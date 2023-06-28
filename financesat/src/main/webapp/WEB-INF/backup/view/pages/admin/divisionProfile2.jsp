<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="boxWhite" id="profilePage">
	<div class="titArea">
		<h2 class="titDep2  marT30">본부별 프로파일</h2>
		<div class="btnArea side">
			<button type="button" id="btnrRetainTranSave" class="btnPwc btnM action">Save</button>
		</div>
	</div>
	
	<table id="retain-grid" class="tblH">
		<caption></caption>
		<colgroup>
			<col style="width:auto;" span="2">
			<col style="width:200px;">
		</colgroup>
		<thead>		
			<tr>
				<th scope="col">본부</th>
				<th scope="col">연관 팀코드</th>
				<th scope="col">Retain 전송 허용 여부</th>
				<th scope="col">표준감사시간 수정 여부</th>
				<th scope="col">Budget 수정 여부</th>
				<!-- 그룹 목록 -->
			</tr>
		</thead>
		<tbody>
			<!-- 년도별 그룹별 요율 데이터 그리드 -->
		</tbody>					
	</table>
</div>
<script type="text/javascript">





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
			$tr.append("<td>"
					+ "<span><input type='radio' name='" + retainProfList[i].cd + "' id='"+ retainProfList[i].cd +"1' value='Y' " + (retainProfList[i].val === 'Y'?"checked":"") + "><label for='"+ retainProfList[i].cd +"1' style='margin-left:5px;'>Y</label></span>"
					+ "<span style='display:inline-block;margin-left:20px;'><input type='radio' name='" + retainProfList[i].cd + "' id='"+ retainProfList[i].cd +"2' value='N' " + (retainProfList[i].val !== 'Y'?"checked":"") + "><label  for='"+ retainProfList[i].cd +"2' style='margin-left:5px;'>N</label></span>"
				+"</td>");
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
}, 10);});
</script>
