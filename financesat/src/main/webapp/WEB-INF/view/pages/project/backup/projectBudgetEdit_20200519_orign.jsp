<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="now" value="<%=new java.util.Date()%>" />
<c:set var="roleCd" value="${sessionScope.SAMIL_SESSION_KEY.roleCd}" />
<c:set var="isDraft" value="${result.data.stat == 'DR'}" />


<c:if test="${result.status == 'fail'}">
<script>Helper.MessageBox("${result.msg}", function(){Helper.goPage("/");});</script>
</c:if>


<!-- 컨텐츠 시작 -->
<h1 class="hidden">Project Form</h1>

<div id="prjtBdgtEdit" class="boxWhite">

	<ul class="tabType01">
		<li><a id="tabPrjtInfo" href="javascript:void(0)" sh-click="tabPrjtInfo">프로젝트 정보</a></li>
		<li class="on"><span>Budgeting</span></li>
	</ul>

	<div class="btnArea">
		<button id="btnSave" type="button" class="btnPwc btnM action" sh-click="btnSave">Save</button>
		<button id="btnCancel" type="button" class="btnPwc btnM action" sh-click="btnCancel">Cancel</button>
	</div>

	<div class="boxLine sumArea toggleOpen">
		<p class="proTitle">${result.data.prjtNm}<span>${result.data.prjtCd}</span></p>
		<div class="sumList">
			<ul>
				<li class="info01"><b>계약금액(백만원)</b><span sh-name="totCntrtFee" sh-format="#,##0"></span></li>
				<li class="info02"><b>감사계약시 합의 시간</b><span sh-name="totPrjtBdgt" sh-format="#,##0"></span> hr</li>
				<li class="info03"><b>Total Budget Hour
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							Engagement Team에서 입력한 시간이 집계됩니다.<br>(Fulcrum, RA 배부 시간 등이 포함)
						</div>
					</div>
				</b><span class="colPoint01" sh-name="totMembBdgt" sh-format="#,##0"></span> hr</li>
				<li class="info04"><b>예상 팀 숙련도
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							숙련도 고려후 Total Budget Hour/ Total Budget Hour
						</div>
					</div>
				</b><span class="colPoint01" sh-name="expTeamWkmnsp" sh-format="#,##0.000"></span></li>
				<li class="info05"><b>계획단계 <br>표준감사시간
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							기본산식 외 요소를 고려한 최종 표준감사시간 * 기준숙련도 / 예상 팀 숙련도
						</div>
					</div>
				</b><span sh-name="planSat" sh-format="#,##0"></span> hr</li>
				<li class="info06"><b>예상 CM(백만원)</b><span sh-name="expCm" sh-format="#,##0"></span></li>
			</ul>
		</div>
		<button type="button" class="btnTog" aria-expanded="true">닫기</button>
	</div>

	<div class="titArea marT20">
		<h2 class="titDep2">Budget Hour</h2>
		<ul class="txtInfo02 timeType02">
			<li><em>New Staff 예측 투입시간</em> : <input name="newStfBdgtTm" type="number" title="예측 시간 입력" sh-name="newStfBdgtTm"> hr * <span sh-name="newStfWkmnsp"></span> (숙련도) = <span sh-name="newStfBdgtTmWkmnsp" sh-format="#,##0.00"></span> hr </li>
			<li><em>Other(QC 등) 투입시간</em> : <input name="otherBdgtTm" type="number" title="Other 투입시간 입력" sh-name="otherBdgtTm"> hr * <span sh-name="otherWkmnsp"></span> (숙련도) = <span sh-name="otherBdgtTmWkmnsp" sh-format="#,##0.00"></span> hr </li>
			<li><em>RA 배부예산</em> : <span sh-name="raBdgtTm" sh-format="#,##0.00"></span> hr * <span sh-name="baseWkmnsp"></span> (<c:if test="${result.data.satTrgtYn == 'Y'}">기준 </c:if>숙련도) = <span sh-name="raBdgtTmWkmnsp" sh-format="#,##0.00"></span> hr </li>
			<li><em>Fulcrum 배부예산</em> : <span sh-name="flcmBdgtTm" sh-format="#,##0.00"></span> hr * <span sh-name="baseWkmnsp"></span> (<c:if test="${result.data.satTrgtYn == 'Y'}">기준 </c:if>숙련도) = <span sh-name="flcmBdgtTmWkmnsp" sh-format="#,##0.00"></span> hr </li>
			<li class="optional-expand" style="display:none"><em>Total Budget Hour</em> : <span sh-name="totMembBdgt" sh-format="#,##0.00"></span> hr</li>
		</ul>
		<div class="btnArea side">
			<button id="btnAddRow" type="button" class="btnPwc btnS action" sh-click="btnAddRow">Add Row</button>
			<button id="btnCalculate" type="button" class="btnPwc btnS" sh-click="btnCalculate">계산하기</button>
			<button id="btnExpand" type="button" class="btnPwc btnS" sh-name="btnExpandText" sh-click="btnExpand">확대</button>
		</div>
	</div>
	<div id="budget-grid" class="gridbox gridHp07" style="width:100%;height:100%;"></div>


</div>
<!-- //boxWhite -->
<!-- //컨텐츠 끝 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/SHView.js?version=<fmt:formatDate value="${now}" pattern="yyyyMMddHHmmss" />"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/ProjectBudget.js?version=<fmt:formatDate value="${now}" pattern="yyyyMMddHHmmss" />"></script>

<script type="text/javascript">
const empPopup = Helper.Popup("employee", "임직원 선택", "/popup/org", {prjtCd:"${result.data.prjtCd}", satgrpCd:"${result.data.satgrpCd}"});

$(document).ready(function(){
	const budgetGrid = {
			parent: "budget-grid",
			skin: THEME_INFO.THEME,
			image_path: THEME_INFO.THEME_IMG_PATH,
			columns: [
				{id:"btnDel", label:["", ""], type:"btnDel", align:"center", width:"40"},
				{id:"tbd", label:["TBD", "#select_filter_strict"], type:"toggle", align:"center", sort:"str", width:"50"},
				{id:"actvCd", label:["Activity", "#select_filter_strict"], type:"coro", align:"left", sort:"str", width:"130"},
				{id:"locaCd", label:["Location", "#select_filter_strict"], type:"coro", align:"left", sort:"str", width:"130"},
				{id:"btnOrg", label:["", ""], type:"btnOrg", align:"center", width:"40"},
				{id:"korNm", label:["Name", "#text_filter"], type:"ro", align:"left", sort:"str", width:"70"},
				{id:"emplNo", label:["사번", "#text_filter"], type:"ro", align:"center", sort:"str", width:"70"},
				{id:"gradNm", label:["Grade", "#select_filter_strict"], type:"ro", align:"left", sort:"str", width:"70"},
				{id:"gradCd", label:["", ""], width:"0"},
				{id:"wkmnsp", label:["숙련도" + GRID_INFO_WKMNSP, "#numeric_filter"], type:"edn", align:"right", sort:"int", width:"100"},
				{id:"totWkmnspAsgnTm", label:["숙련도 고려<br>투입시간", "#numeric_filter"], type:"ron", align:"right", sort:"int", width:"70"},
				{id:"totAsgnTm", label:["Total<br>Time", "#numeric_filter"], type:"ron", align:"right", sort:"int", width:"70"},
			<c:forEach var="sDt" items="${result.data.sDtOfWeek}" varStatus="weekStatus">
				{id:"week${weekStatus.count}", label:["${sDt.startDate}", "${sDt.workDay} days"], type:"edn", align:"right", width:"85"},</c:forEach>
			]
	};
	let budgetGridObj = new dhtmlXGridObject(budgetGrid);
	budgetGridObj.enableEditEvents(true,false,true);
	budgetGridObj.setColumnHidden(budgetGridObj.getColIndexById("gradCd"), true);
	budgetGridObj.setNumberFormat("0,000.00",budgetGridObj.getColIndexById("totAsgnTm"),".",",");
	budgetGridObj.setNumberFormat("0,000.00",budgetGridObj.getColIndexById("totWkmnspAsgnTm"),".",",");
	<c:forEach var="sDt" items="${result.data.sDtOfWeek}" varStatus="weekStatus">
	budgetGridObj.setNumberFormat("0,000.00",budgetGridObj.getColIndexById("week${weekStatus.count}"),".",",");</c:forEach>
	budgetGridObj.enableResizing("false");
	budgetGridObj.splitAt(12);
	
	let actvCombo = budgetGridObj.getCombo(budgetGridObj.getColIndexById("actvCd"));
	<c:forEach var="actv" items="${actvList}">
	actvCombo.put("${actv.cd}", "${actv.nm}");</c:forEach>
	
	
	let locaCombo = budgetGridObj.getCombo(budgetGridObj.getColIndexById("locaCd"));
	<c:forEach var="loca" items="${locaList}">
	locaCombo.put("${loca.cd}", "${loca.nm}");</c:forEach>
	
	let weekLength = ${fn:length(result.data.sDtOfWeek)};
	
	
	let initData =  Object.assign({}, initPrjtBdgt, ${result.data}, {prflId:${result.data.prflId}});
	
	let prjtBdgt = new ProjectBudget({
		id: 'prjtBdgtEdit',
		data: initData,
		computed:{
			totMembBdgt: 		function(){return Number(this.newStfBdgtTm) + Number(this.otherBdgtTm) + Number(this.raBdgtTm) + Number(this.flcmBdgtTm) + Number(this.sumMembBdgt);},
			newStfBdgtTmWkmnsp: function(){return Number(this.newStfBdgtTm) *  Number(this.newStfWkmnsp);},
			otherBdgtTmWkmnsp: 	function(){return Number(this.otherBdgtTm) *  Number(this.otherWkmnsp);},
			raBdgtTmWkmnsp: 	function(){return Number(this.raBdgtTm) *  Number(this.baseWkmnsp);},
			flcmBdgtTmWkmnsp: 	function(){return Number(this.flcmBdgtTm) *  Number(this.baseWkmnsp);},
			totMembBdgtWkmnsp: 	function(){
				this.newStfBdgtTm;
				this.otherBdgtTm;
				this.sumMembBdgt;
				
				return this.newStfBdgtTmWkmnsp + this.otherBdgtTmWkmnsp + this.raBdgtTmWkmnsp + this.flcmBdgtTmWkmnsp + Number(this.sumMembBdgtWkmnsp)
			},
			planSat: 			function(){
				this.newStfBdgtTm;
				this.otherBdgtTm;
				this.sumMembBdgt;
				this.sumMembBdgtWkmnsp;
				
				return   this.totMembBdgt==0?null:Number(this.wkmnspSat) / (this.totMembBdgtWkmnsp / this.totMembBdgt);
			},
			expTeamWkmnsp: function(){
				this.newStfBdgtTm;
				this.otherBdgtTm;
				this.sumMembBdgt;
				this.sumMembBdgtWkmnsp;
				
				return this.totMembBdgt == 0 ? null : this.totMembBdgtWkmnsp / this.totMembBdgt
			},
			btnExpandText: function(){
				return this.isExpand?'축소':'확대';
			}
		},
		event: {
			click: {
				btnSave: function(e){
					let data = this;
					if(!calculate()){
						return;
					}
					
					const gridData = getGridData();
					
					let list = [];
					
					for(let i=0; i<gridData.length; i++){
						// Member 정보 
						let obj = {
							membEmplNo: gridData[i].emplNo,
							tbd: gridData[i].tbd,
							actvCd: gridData[i].actvCd,
							locaCd: gridData[i].locaCd,
							gradCd: gridData[i].gradCd,
							wkmnsp: gridData[i].wkmnsp,
							totAsgnTm: gridData[i].totAsgnTm
						}
						
						// Member Budget 정보
						let tmpList = [];
						for(let j=1;j<=weekLength; j++){
							let key = "week"+j;
							let asgnTm = gridData[i][key];
							if(asgnTm != "" && asgnTm != 0){
								let startWeek = budgetGridObj.getColumnLabel(budgetGridObj.getColIndexById(key), 0);
								tmpList.push({ asgnTm: asgnTm, weekFrdt: startWeek.replace(/[\/]/g, '-')});
							}
						}
						list.push( $.extend(obj, {list:tmpList}));	
					}
					 
					
					Helper.post("/project/budget/save", {prjtCd:"${result.data.prjtCd}", newStfBdgtTm: data.newStfBdgtTm, otherBdgtTm: data.otherBdgtTm, list:list})
					.done(function(){
						Helper.goPage("/pages/project/budget/read", {prjtCd:"${result.data.prjtCd}", isDraft:${isDraft}})	
					})
					.fail(function(){
						
					})
				},
				btnCancel: function(e){
					<c:choose>
						<c:when test="${isDraft}">
						Helper.goPage("/pages/project/draftlist");
						</c:when>
						<c:otherwise>
						Helper.goPage("/pages/project/budget/read", {prjtCd:"${result.data.prjtCd}"});
						</c:otherwise>
					</c:choose>
				},
				btnAddRow: function(){
					budgetGridObj.addRow(budgetGridObj.uid(), [,'N',,,,], 0);
				},
				btnCalculate: function(){
					calculate();
				},
				tabPrjtInfo: function(e){
					e.preventDefault();
					let msg = [];
					msg.push("Budgeting 정보가 저장되지 않았습니다");
					msg.push("저장하지 않고 프로젝트 정보로 이동하시겠습니까?");
					Helper.ConfirmBox(msg, function(flag){
						if(flag){
							Helper.goPage("/pages/project/edit", {prjtCd:"${result.data.prjtCd}"})
						}
					});
				},
				btnExpand: function(){
					const boxSelector = "#test-box";
					let $container = $("#container");
					let $contents = $("#contents");
					let $boxWhite = $(".boxWhite");
					let $titArea = $(".titArea");
					let $grid = $("#budget-grid");
					if($(boxSelector).length > 0){
						$container.css('height', '');
						$contents.css('height','');
						$boxWhite.append($titArea, $grid);
						$grid.addClass("gridHp07");
						$(boxSelector).remove();
						$(".optional-expand").hide();
						this.isExpand = false;
					}else{
						let $box = $("<div id='test-box' class='expandLayer'></div>");
						$container.css('height', '100%');
						$contents.css('height','100%');
						$boxWhite.append($box);
						$box.append($titArea , $grid);
						$box.append($grid);
						$grid.removeClass("gridHp07");
						$(".optional-expand").show();
						this.isExpand = true;
					}
					budgetGridObj.setSizes();
				}
			}
		}
	});
	
	let week1Idx = budgetGridObj.getColIndexById("week1");
	let workDayList = [];
	
	prjtBdgt.initPrjtBdgt(function(){
		workDayList = prjtBdgt.data.sDtOfWeek.map(function(item){return item.workDay});
		bindEvent();
		loadData();
	});
	
	
	function bindEvent(){
		budgetGridObj.attachEvent("onRowSelect", function(_rid, idx){
			let rid = _rid;
			
			if(idx == budgetGridObj.getColIndexById("btnDel")){
	 			budgetGridObj.deleteRow(rid);
			}else if(idx == budgetGridObj.getColIndexById("btnOrg")){
				empPopup.openPopup(rid, function(data){
					budgetGridObj.cellById(rid, budgetGridObj.getColIndexById("korNm")).setValue(data.kornm);
					budgetGridObj.cellById(rid, budgetGridObj.getColIndexById("emplNo")).setValue(data.emplno);
					budgetGridObj.cellById(rid, budgetGridObj.getColIndexById("gradNm")).setValue(data.gradnm);
					budgetGridObj.cellById(rid, budgetGridObj.getColIndexById("gradCd")).setValue(data.gradcd);
					budgetGridObj.cellById(rid, budgetGridObj.getColIndexById("wkmnsp")).setValue(data.wkmnsp);
					// 인원이 변경되면 숙련도 값이 달라지기때문에 재계산 해야 함.
					calculateByRow(rid);
					budgetGridObj.refreshFilters();
				});	
			}else if(idx == budgetGridObj.getColIndexById("tbd")){
				budgetGridObj.cells(_rid, idx).setValue(budgetGridObj.cells(_rid, idx).getValue() == "Y"?"N":"Y")
				budgetGridObj.refreshFilters();
			}
		});
		
		budgetGridObj.attachEvent("onEditCell", function(stage,rId,cInd,nValue,oValue){
		
			if(stage == 2){
				if(cInd == budgetGridObj.getColIndexById("wkmnsp")){
					let regExp = /^[0-9]+(\.[0-9]+)?$/g;
					if(nValue !== ""){
						if(regExp.test(nValue)){
							return true;
						}else{
							Helper.MessageBox("숫자만 입력 가능합니다.");
							return false;
						}
					}
					
	 			}else if(cInd >= budgetGridObj.getColIndexById("week1")){
	 				let regExp = /^[0-9]+(\.[0-9]+)?$/g;
	 				
	 				let workDay = workDayList[cInd - week1Idx];
	 				let maxWorkTm = workDay * 16; // 업무시간을 08:00를 시작으로 24시까지 
	 				
	 				if (nValue !== '') {
	 					if(regExp.test(nValue)){
	 						if(Number(nValue) > maxWorkTm){
	 							Helper.MessageBox("해당 주에는 최대 " + maxWorkTm + "시간까지만 입력 가능합니다.");	
	 							return false;
	 						}else{
	 							return true;
	 						}
						}else{
							Helper.MessageBox("숫자만 입력 가능합니다.");
							return false;
						}
					}
	 				
	 				/* if(nValue !== ""){					
						let regExp = /^[0-9]+(\.(00?|25|50?|75))?$/g;
						if(regExp.test(nValue)){
							return true;
						}else{
							Helper.MessageBox("숫자만 입력 가능하며, 소수점 이하는 0.25, 0.5, 0.75(15분 단위)만 입력가능합니다.");
							return false;
						}
					} */
				}
			}
			return true;
		});
		
		$(document).on('click.gridResize', 'nav > button, .sumArea > .btnTog', function(e) {
	         e.preventDefault();
	         budgetGridObj.setSizes();
	    });
	}
	
	
	function loadData(){
		budgetGridObj.clearAll();
		Helper.post("/project/budget/list", {prjtCd:"${result.data.prjtCd}", isDraft:${isDraft}})
		.done(function(data){
			budgetGridObj.parse(data, "js");
			calculate();
		});
	}
	
	function getGridData(){
		let list = [];
		budgetGridObj.forEachRow(function(rid){
			let rowData = budgetGridObj.getRowData(rid); 
			delete rowData.btnDel;
			delete rowData.btnOrg;
			list.push(rowData);	
		});
		return list;
	}
	
	
	function validCheck(){
		let keys = {};
		let msg=[];
		const numRegExp = /^(0|[1-9][0-9]+)(\.[0-9]+)?$/g;
		
					
		// Grid에서 필수 값 체크
		budgetGridObj.forEachRow(function(rid){
			/* let actvCd = budgetGridObj.getUserData(rid, "actvCd");
			let locaCd = budgetGridObj.getUserData(rid, "locaCd"); */
			let actvCd = budgetGridObj.cellById(rid, budgetGridObj.getColIndexById("actvCd")).getValue();
			let locaCd = budgetGridObj.cellById(rid, budgetGridObj.getColIndexById("locaCd")).getValue();
			let emplNo = budgetGridObj.cellById(rid, budgetGridObj.getColIndexById("emplNo")).getValue();
			let korNm = budgetGridObj.cellById(rid, budgetGridObj.getColIndexById("korNm")).getValue();
			
			let tmpMsg = [];
			if(!actvCd) tmpMsg.push("Activity");
			if(!locaCd) tmpMsg.push("Location");
			if(!emplNo) tmpMsg.push("임직원");
			
			if(tmpMsg.length == 0){
				let key = actvCd + "_" + locaCd + "_" + emplNo + "_" + korNm;
				if(!keys[key]) keys[key] = 0;
				keys[key]++;
			}else{
				msg.unshift((budgetGridObj.getRowIndex(rid) + 1) + "줄: [" + tmpMsg.join("], [") + "]을 입력해 주세요")
			}
		});
		
		// 중복 인원 존재 여부 체크
		for(let key in keys){
			if(keys[key] > 1){
				let tmpKey = key.split("_");
				msg.push(tmpKey[3] + "("+ tmpKey[2] + ")님은 Activity, Location, Name 중복건이 존재합니다.");
			}
		}
		return msg;
	}
	
	function calculate(){
		let sumMembBdgt = 0;
		let sumMembBdgtWkmnsp = 0;
		
		let msg = validCheck();
		
		if(msg.length > 0){
			Helper.MessageBox(msg);
			return false;
		}
		
		budgetGridObj.forEachRow(function(rid, idx){
			let result = calculateByRow(rid);
			sumMembBdgt += result.totTime;
			sumMembBdgtWkmnsp += result.totWkmnspAsgnTm;
		});
		
		prjtBdgt.setValue("sumMembBdgt", sumMembBdgt);
		prjtBdgt.setValue("sumMembBdgtWkmnsp", sumMembBdgtWkmnsp);
		
		return true;
	}
	
	function calculateByRow(rid){
		let bdgtTmList = [];
		<c:forEach var="sDt" items="${result.data.sDtOfWeek}" varStatus="weekStatus">
		bdgtTmList.push(budgetGridObj.cellById(rid, budgetGridObj.getColIndexById("week${weekStatus.count}")).getValue());</c:forEach>
		
		let totTime = 0;
		for(let i =0; i<bdgtTmList.length; i++){
			totTime += Number(bdgtTmList[i] ? bdgtTmList[i] : 0);
		}
		
		let wkmnsp = budgetGridObj.cellById(rid, budgetGridObj.getColIndexById("wkmnsp")).getValue();
		let totWkmnspAsgnTm = totTime * wkmnsp;
		
		budgetGridObj.cellById(rid, budgetGridObj.getColIndexById("totAsgnTm")).setValue(totTime);
		budgetGridObj.cellById(rid, budgetGridObj.getColIndexById("totWkmnspAsgnTm")).setValue(totWkmnspAsgnTm);
		
		return {totTime:totTime, totWkmnspAsgnTm:totWkmnspAsgnTm}
	}
	
});
</script>