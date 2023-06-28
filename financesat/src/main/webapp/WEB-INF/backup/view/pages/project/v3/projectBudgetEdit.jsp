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
		<li><a id="tabPrjtInfo" href="javascript:void(0)" sh-click="tabPrjtInfo">표준감사시간</a></li>
		<li class="on"><span>Budgeting</span></li>
	</ul>

	<div class="btnArea">
		<button id="btnSave" type="button" class="btnPwc btnM action" sh-click="btnSave">Save</button>
		<button id="btnCancel" type="button" class="btnPwc btnM action" sh-click="btnCancel">Cancel</button>
	</div>

	<div class="boxLine sumArea">
		<p class="proTitle">${result.data.prjtnm}<span>${result.data.prjtcd} (EL: ${result.data.chargptrnm}, PM: ${result.data.chargmgrnm}) </span></p>
		<div class="sumList">
			<ul>
				<li><b>프로젝트 시작일</b><span sh-name="prjtFrdt"></span></li>
				<li><b>프로젝트 종료일</b><span sh-name="prjtTodt"></span></li>
				<li><b>결산 년월</b><span sh-name="bizTodt"></span></li>
				<li><b>예상 보고서일</b><span sh-name="rprtScdlDt"></span></li>				
			</ul>
		</div>
	</div>

	<!-- ########################################################################################################################################## -->
	<!-- 20200512 추가 -->	
	<div class="titArea marT20">
		<h2 class="titDep2">CIS 예산 및 Code별 예산</h2>
	</div>
	
	<div id="cis-grid" class="gridbox" style="width:100%;"></div>
	
	<div class="div-row">
		<div class="div-col">
			<div class="titArea marT20">		
				<h2 class="titDep2">Budget 요약</h2>	    
			</div>			
			
			<div>	
				<table class="tblForm">
					<caption>Budget 요약</caption>
					<colgroup><col style="width:25%"><col style="width:55%"><col style="width:30%"></colgroup>
					<tbody>
						<tr>
							<td rowspan="4" style="text-align:center;line-height:1.875em">Core<br>Team</td>
							<td class="tleft">담당이사</td>
							<td class="tright"><span sh-name="elTm" sh-format="#,##0"></span></td>
						</tr>
						<tr>
							<td class="tleft">PM</td>
							<td class="tright"><span sh-name="pmTm" sh-format="#,##0"></span></td>
						</tr>
						<tr>
							<td class="tleft">감사팀</td>
							<td class="tright"><span sh-name="adtteamTm" sh-format="#,##0"></span></td>
						</tr>
						<tr>
							<td class="tleft">New Staff(TBD)
								<div class="tipArea">
									<button type="button" class="btnTip">도움말</button>
									<div class="tipCont">New Staff(미입사자) 추정 투입시간</div>
								</div> 
							</td>
							<td class="tright"><span sh-name="newstaffTm" sh-format="#,##0"></span></td>
						</tr>
						<tr>
							<td rowspan="4" style="text-align:center;line-height:1.875em">Support<br>Team</td>
							<td class="tleft">QRP
								<div class="tipArea">
									<button type="button" class="btnTip">도움말</button>
									<div class="tipCont">EL 시간을 고려한 최소투입시간 고려하여 입력</div>
								</div> 
							</td>
							<td class="tright"><span sh-name="qrpTm" sh-format="#,##0"></span></td>
						</tr>
						<tr>
							<td class="tleft">QC-(RM, ACS, M&amp;T 등)</td>
							<td class="tright"><span sh-name="qcTm" sh-format="#,##0"></span></td>
						</tr>						
						<tr>
							<td class="tleft">SPA</td>
							<td class="tright"><span sh-name="raTm" sh-format="#,##0"></span></td>
						</tr>
						<tr>
							<td class="tleft">Fulcrum</td>
							<td class="tright"><span sh-name="fulcrumTm" sh-format="#,##0"></span></td>
						</tr>
						<tr>
							<td class="tleft" colspan="2">Budget Total</td>
							<td class="tright"><span sh-name="totalTm" sh-format="#,##0"></span></td>
						</tr>
					</tbody>		
				</table>
			</div>		
		</div>
		
		<c:if test="${result.data.satTrgtYn == 'Y'}">
		<div class="div-col">
			<div class="titArea marT20">
				<h2 class="titDep2">표준감사시간 대상 (${result.data.satgrpNm})</h2>
			</div>
			
			<div>
				<table class="tblForm">
					<caption>표준감사시간 대상 (${result.data.satgrpNm})</caption>
					<colgroup><col style="width:auto"><col style="width:35%"></colgroup>
					<tbody>
						<tr>
							<th class="tleft">구분</th>
							<th class="tright">값</th>
						</tr>
						<tr>
							<td class="tleft">한공회표준감사시간</td>
							<td class="tright"><fmt:formatNumber value="${result.data.etDfnSat}" pattern="#,##0" /></td>
						</tr>
						<tr>
							<td class="tleft">기준 숙련도</td>
							<td class="tright"><fmt:formatNumber value="${result.data.baseWkmnsp}" pattern="#,##0.000" /></td>
						</tr>
						<tr>
							<td class="tleft">예상 팀 숙련도</td>
							<td class="tright"><span sh-name="satExpWkmnsp" sh-format="#,##0.000"></span></td>
						</tr>
						<tr>
							<td class="tleft">계획단계표준감사시간</td>
							<td class="tright"><span sh-name="planSat" sh-format="#,##0"></span></td>
						</tr>
						<tr>
							<td class="tleft">&nbsp;</td>
							<td class="tright">&nbsp;</td>
						</tr>
						<tr>
							<td class="tleft">&nbsp;</td>
							<td class="tright">&nbsp;</td>
						</tr>
						<tr>
							<td class="tleft">감사계약시합의시간</td>
							<td class="tright"><span sh-name="cntrtAdtTm" sh-format="#,##0"></span></td>
						</tr>
						<tr>
							<td class="tleft">Budget Total(외감)</td>
							<td class="tright"><span sh-name="satBdgtTm" sh-format="#,##0"></span></td>
						</tr>
					</tbody>		
				</table>
			</div>
		</div>	
		</c:if>
		<div class="div-col">
			<div class="titArea marT20">
				<h2 class="titDep2">&nbsp;</h2>
			</div>
			
			<div>
				<table class="bdgtTblForm">
					<tbody>
						<tr>
							<td class="tleft"> * 감사계약시 제시한 숙련도에 따라 준수해야하는 감사시간은 달라질 수 있습니다.</td>
						</tr>
					</tbody>		
				</table>
			</div>
		</div>	
	</div>

	
	<!-- ########################################################################################################################################## -->

	<div class="titArea marT20">
		<h2 class="titDep2">Budget Hour</h2>

		<div class="btnArea side">
			<button id="btnAddRow" type="button" class="btnPwc btnS action" sh-click="btnAddRow">Add Row</button>
			<button id="btnCalculate" type="button" class="btnPwc btnS" sh-click="btnCalculate">계산하기</button>
		</div>
	</div>
	<div id="budget-grid" class="gridbox gridHp07" style="width:100%;height:100%;"></div>


</div>
<!-- //boxWhite -->
<!-- //컨텐츠 끝 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/SHView.js?version=<fmt:formatDate value="${now}" pattern="yyyyMMddHHmmss" />"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/v3/ProjectBudgetV3.js?version=<fmt:formatDate value="${now}" pattern="yyyyMMddHHmmss" />"></script>

<script type="text/javascript">
const empPopup = Helper.Popup("employee", "임직원 선택", "/popup/org", {prjtCd:"${result.data.prjtcd}", satgrpCd:"${result.data.satgrpCd}"});
// const prjtPopup = Helper.Popup("project", "Project Select", "/popup/project-v3", {size:'M', prjtCd:"${result.data.prjtcd}"});

$(document).ready(function(){
	const bdgtRoleConfig = JSON.parse('${bdgtRoleConfig}');
	const budgetGrid = {
			parent: "budget-grid",
			skin: THEME_INFO.THEME,
			image_path: THEME_INFO.THEME_IMG_PATH,
			columns: [
				{id:"btnDel", label:["", ""], type:"btnDel", align:"center", width:"40"},
				{id:"prjtcd", label:["Project Code", "#select_filter_strict"], type:"ro", align:"center", sort:"str", width:"100"},
				{id:"prjtnm", label:["Project Name", "#select_filter_strict"], type:"ro", align:"center", sort:"str", width:"100"},
				{id:"tbd", label:[Helper.getGridTitleAndInfo("TBD", "미정인 경우(New staff 등) Check(주 80시간 초과하여 일괄로 입력 가능)", 'left'), "#select_filter_strict"], type:"toggle", align:"center", sort:"str", width:"65"},
				{id:"actvCd", label:[Helper.getGridTitleAndInfo("Activity", "SPA, Fulcrum, New STaff(TBD), QC(QRP 포함) 예산 배정시 반드시 해당 Activity로 기입 필요 ", 'left'), "#select_filter_strict"], type:"coro", align:"left", sort:"str", width:"180"},
				{id:"locaCd", label:[Helper.getGridTitleAndInfo("Location", "Retain 반영시 팀별로 field 만 선택하여 반영 가능", 'left'), "#select_filter_strict"], type:"coro", align:"left", sort:"str", width:"90"},
				{id:"btnOrg", label:["", ""], type:"btnOrg", align:"center", width:"40"},
				{id:"korNm", label:["Name", "#text_filter"], type:"ro", align:"left", sort:"str", width:"70"},
				{id:"emplNo", label:["사번", "#text_filter"], type:"ro", align:"center", sort:"str", width:"70"},
				{id:"gradNm", label:["Grade", "#select_filter_strict"], type:"ro", align:"left", sort:"str", width:"70"},
				{id:"gradCd", label:["", ""], width:"0"},
				{id:"wkmnsp", label:[Helper.getGridTitleAndInfo("숙련도", "예상감사보고서일과 수습종료일을 기준으로 자동계산되며, 수정 가능합니다.", 'right'), "#numeric_filter"], type:"edn", align:"right", sort:"int", width:"80"},
				{id:"totWkmnspAsgnTm", label:["숙련도 고려<br>투입시간", "#numeric_filter"], type:"ron", align:"right", sort:"int", width:"70"},
				{id:"totAsgnTm", label:["Total<br>Time", "#numeric_filter"], type:"ron", align:"right", sort:"int", width:"70"},
			<c:forEach var="sDt" items="${result.data.sDtOfWeek}" varStatus="weekStatus">
				{id:"week${weekStatus.count}", label:["${sDt.startDate}", "${sDt.workDay} days"], type:"edn", align:"right", width:"85"},</c:forEach>
			]
	};
	
	//----------------------------------------------
	// 20200512 추가 
	//----------------------------------------------
	const cisGrid = {
			parent: "cis-grid",
			skin: THEME_INFO.THEME,
			image_path: THEME_INFO.THEME_IMG_PATH,
			columns: [
				{id:"bdgtTrgtYn", label:[Helper.getGridTitleAndInfo("Budget<br>대상", "Budgeting이 필요한 경우 Check", 'left')], type:"toggle", align:"center", width:"90"},
				{id:"prjtcd", label:[Helper.getGridTitleAndInfo("프로젝트 코드", "지원코드는 자동으로 보여지며, 주관코드 추가가 필요한 경우에는 R&Q 담당자에게 요청(F-link 상 추가)", 'left')], type:"ro", align:"center", width:"120"},
				{id:"prjtnm", label:["프로젝트 명"], type:"ro", align:"left"},
				{id:"inadjustnm", label:["협업<br>구분"], type:"ro", align:"center", width:"70", resize:false},
				{id:"createdvnm", label:["생성<br>구분"], type:"ro", align:"center", width:"70"},
				{id:"auditgbnm", label:[Helper.getGridTitleAndInfo("외감<br>구분", 'F-link 에서 변경 가능하며, "01"코드인 경우 최초 외감으로 인식', 'left')], type:"ro", align:"center", width:"70"},
				{id:"amtw", label:["계약 금액"], type:"ron", align:"right", width:"150"},
				{id:"exbudgettime", label:[Helper.getGridTitleAndInfo("시간예산<br>(F-Link)", 'F-link 조회값, 생성 후 5개월내 변경가능', 'right')], type:"ron", align:"right", width:"100"},
				{id:"cntrtAdtTm", label:[Helper.getGridTitleAndInfo("감사계약시<br>합의시간", '보수에 따라 회사와 합의한 시간', 'right')], type:"edn", align:"right", width:"100"},
				{id:"bdgt", label:[Helper.getGridTitleAndInfo("Budgeting<br>Hours", '코드별 Budgeting 총 합계', 'right')], type:"ron", align:"right", width:"100"},
				{id:"bdgtWkmnsp", type:"ron"},
				{id:"div"}
			]
	};
	
	let budgetGridObj = new dhtmlXGridObject(budgetGrid);
	budgetGridObj.enableEditEvents(true,false,true);
	budgetGridObj.setColumnHidden(budgetGridObj.getColIndexById("gradCd"), true);
	budgetGridObj.setNumberFormat("0,000.000",budgetGridObj.getColIndexById("wkmnsp"),".",",");
	budgetGridObj.setNumberFormat("0,000.00",budgetGridObj.getColIndexById("totAsgnTm"),".",",");
	budgetGridObj.setNumberFormat("0,000.00",budgetGridObj.getColIndexById("totWkmnspAsgnTm"),".",",");
	<c:forEach var="sDt" items="${result.data.sDtOfWeek}" varStatus="weekStatus">
	budgetGridObj.setNumberFormat("0,000.00",budgetGridObj.getColIndexById("week${weekStatus.count}"),".",",");</c:forEach>
	//budgetGridObj.enableSmartRendering(true);
	budgetGridObj.enableResizing("false");
	budgetGridObj.splitAt(14);
	
	// 20200427 추가 
	let cisGridObj = new dhtmlXGridObject(cisGrid);
	cisGridObj.enableEditEvents(true,false,true);
	cisGridObj.setColumnHidden(cisGridObj.getColIndexById("div"),true);
	cisGridObj.setColumnHidden(cisGridObj.getColIndexById("bdgtWkmnsp"),true);
	cisGridObj.setNumberFormat("0,000",cisGridObj.getColIndexById("amtw"),".",",");
	cisGridObj.setNumberFormat("0,000",cisGridObj.getColIndexById("bdgt"),".",",");
	cisGridObj.setNumberFormat("0,000",cisGridObj.getColIndexById("cntrtAdtTm"),".",",");
	cisGridObj.setNumberFormat("0,000",cisGridObj.getColIndexById("exbudgettime"),".",",");
	cisGridObj.setNumberFormat("0,000.000",cisGridObj.getColIndexById("expWkmnsp"),".",",");
	cisGridObj.attachFooter( 
			"Summary,#cspan,#cspan,#cspan,#cspan,#cspan" 
				+ ",<div id='sumAmtw'>0</div>"
				+ ",<div id='sumExbudgettime'>0</div>" 
				+ ",<div id='sumCntrtAdtTm'>0</div>"
				+ ",<div id='sumBdgt'>0</div>"
				/* + ",<div id='aveExpWkmnsp'>0</div>" */
				+ "<div></div>",		
			[
				 "text-align:right;font-size:14px;font-family:arial,notokr;font-style:inherit;background-color:#F7F7F7;height: 40px;padding: 1px 5px 0;border-left:0;border-right:0;"
				,"border-right:0; border-left:0"
				,"border-right:0; border-left:0"
				,"border-right:0; border-left:0"
				,"border-right:0; border-left:0"
				,"border-right:0; border-left:0"
				,"text-align:right;font-size:14px;font-family:arial,notokr;font-style:inherit;background-color:#FFFFFF;border-left:1px solid #ddd !important;border-right:0"
				,"text-align:right;font-size:14px;font-family:arial,notokr;font-style:inherit;background-color:#FFFFFF;border-left:1px solid #ddd !important;border-right:0"
				,"text-align:right;font-size:14px;font-family:arial,notokr;font-style:inherit;background-color:#FFFFFF;border-left:1px solid #ddd !important;border-right:0"
				,"text-align:right;font-size:14px;font-family:arial,notokr;font-style:inherit;background-color:#FFFFFF;border-left:1px solid #ddd !important;border-right:0"
				,"text-align:right;font-size:14px;font-family:arial,notokr;font-style:inherit;background-color:#FFFFFF;border-left:1px solid #ddd !important;border-right:0"
			    ,""
			]
	);	
	cisGridObj.enableResizing("false");	
	cisGridObj.enableAutoHeight(true);
	
	let actvCombo = budgetGridObj.getCombo(budgetGridObj.getColIndexById("actvCd"));
	<c:forEach var="actv" items="${actvListV3}">
	actvCombo.put("${actv.cd}", "${actv.nm}");</c:forEach>
	
	
	let locaCombo = budgetGridObj.getCombo(budgetGridObj.getColIndexById("locaCd"));
	<c:forEach var="loca" items="${locaList}">
	locaCombo.put("${loca.cd}", "${loca.nm}");</c:forEach>
	
	let weekLength = ${fn:length(result.data.sDtOfWeek)};
	
	
	let initData =  Object.assign({}, initPrjtBdgt, ${result.data}, {prflId:${result.data.prflId}});
	
	let prjtBdgt = new ProjectBudget({
		id: 'prjtBdgtEdit',
		data: initData,
		computed:{},
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
						let membEmplNo; 
						if(gridData[i].tbd == 'Y' && ['09','12','13','14','15','16'].includes(gridData[i].actvCd))
						{
							membEmplNo = '999999';
						}else{
							membEmplNo = gridData[i].emplNo;								
						}
						
						// Member 정보 
						let obj = {
							prjtcd: gridData[i].prjtcd,	// 2020-05-17 추가
							membEmplNo: membEmplNo, //gridData[i].emplNo,								
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
					 		
					//----------------------------------------------------
					//Cis 그리드 변경사항 확인
					//----------------------------------------------------
					let cislist = [];
					cisGridObj.forEachRow(function(rid){
						let rowData = cisGridObj.getRowData(rid); 
						cislist.push(rowData);	
					});					
					//----------------------------------------------------
					
					Helper.post("/project/v3/budget/save", {prjtCd:"${result.data.prjtcd}", newStfBdgtTm: data.newStfBdgtTm, otherBdgtTm: data.otherBdgtTm, list:list, cislist:cislist})
					.done(function(){
						Helper.goPage("/pages/project/budget/read", {prjtCd:"${result.data.prjtcd}", isDraft:${isDraft}})	
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
						Helper.goPage("/pages/project/budget/read", {prjtCd:"${result.data.prjtcd}"});
						</c:otherwise>
					</c:choose>
				},
				btnAddRow: function(){
					
					
					let ids = cisGridObj.getAllRowIds();
					ids = ids.split(',');
					let list = [];
					for(let i=0; i<ids.length; i++) {
						let tmp = cisGridObj.getRowData(ids[i]);	
						if(tmp['bdgtTrgtYn'] == 'Y') list.push(tmp);
					}
					if(list.length > 0) {
						const prjtPopup = Helper.Popup("project", "Project Select", "/popup/project-v3", {size:'M', prjtCd:"${result.data.prjtcd}", list: JSON.stringify(list)});
						prjtPopup.openPopup("subPrjt" + budgetGridObj.uid(), function(data){
							//budgetGridObj.addRow(budgetGridObj.uid(), [,'N',,,,], 0);
							budgetGridObj.addRow(budgetGridObj.uid(), [,data.prjtCd,data.prjtNm,'N',,,,], 0);
						});	
					} else {
						Helper.MessageBox("[CIS 에산 및 Code별 예산]에서 Budget 대상이 없습니다.");
					}
						
					return;
					
					prjtPopup.openPopup("subPrjt" + budgetGridObj.uid(), function(data){
						//budgetGridObj.addRow(budgetGridObj.uid(), [,'N',,,,], 0);
						budgetGridObj.addRow(budgetGridObj.uid(), [,data.prjtCd,data.prjtNm,'N',,,,], 0);
					});	
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
							if(prjtBdgt.data.profSatYn === 'Y')
								Helper.goPage("/pages/project/edit", {prjtCd:"${result.data.prjtcd}"});
							else
								Helper.MessageBox(['본부별 정책에 의해 표준감사시간은 수정할 수 없습니다.'], function(){
									Helper.goPage("/pages/project/read", {prjtCd:"${result.data.prjtcd}"});	
								});
						}
					});
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
			let actvCd = budgetGridObj.cells(_rid, budgetGridObj.getColIndexById("actvCd")).getValue(); 
			
			if(idx == budgetGridObj.getColIndexById("btnDel")){
	 			budgetGridObj.deleteRow(rid);
			}else if(idx == budgetGridObj.getColIndexById("btnOrg")){
				let tbdYn = budgetGridObj.cells(_rid, budgetGridObj.getColIndexById("tbd")).getValue();

				if (tbdYn == 'Y' && ['09','12','13','14','15','16'].includes(actvCd)) {
					let roleConfig = bdgtRoleConfig.filter(function(data){return data.cd === actvCd});
					if(roleConfig.length > 0){
						roleConfig = roleConfig[0];
						budgetGridObj.cellById(rid, budgetGridObj.getColIndexById("gradNm")).setValue(roleConfig.gradnm); 			//직급명
						budgetGridObj.cellById(rid, budgetGridObj.getColIndexById("gradCd")).setValue(roleConfig.gradcd);			//직급코드
						budgetGridObj.cellById(rid, budgetGridObj.getColIndexById("wkmnsp")).setValue(Number(roleConfig.wkmnsp));	//숙련도
						budgetGridObj.cellById(rid, budgetGridObj.getColIndexById("korNm")).setValue(budgetGridObj.cells(rid, budgetGridObj.getColIndexById("actvCd")).getTitle());
						budgetGridObj.cellById(rid, budgetGridObj.getColIndexById("emplNo")).setValue("");	
					}else{
						Helper.MessageBox("프로파일에 등록 되어있지 않는 Activity를 선택하였습니다.");
						budgetGridObj.cellById(rid, budgetGridObj.getColIndexById("gradNm")).setValue('');
						budgetGridObj.cellById(rid, budgetGridObj.getColIndexById("gradCd")).setValue('');
						budgetGridObj.cellById(rid, budgetGridObj.getColIndexById("wkmnsp")).setValue('');
						budgetGridObj.cellById(rid, budgetGridObj.getColIndexById("korNm")).setValue('');
						budgetGridObj.cellById(rid, budgetGridObj.getColIndexById("emplNo")).setValue('');
						return;
					}
				}else{
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
				}
			}else if(idx == budgetGridObj.getColIndexById("tbd")){

				//Activity, Name, 사번, 직급, 숙련도 초기화
				budgetGridObj.cellById(rid, budgetGridObj.getColIndexById("gradNm")).setValue(""); //직급
				budgetGridObj.cellById(rid, budgetGridObj.getColIndexById("gradCd")).setValue(""); //직급코드
				budgetGridObj.cellById(rid, budgetGridObj.getColIndexById("wkmnsp")).setValue(""); //숙련도   							  
				budgetGridObj.cellById(rid, budgetGridObj.getColIndexById("korNm")).setValue("");  //사원명
				budgetGridObj.cellById(rid, budgetGridObj.getColIndexById("emplNo")).setValue(""); //사번					
				
				budgetGridObj.cells(_rid, idx).setValue(budgetGridObj.cells(_rid, idx).getValue() == "Y"?"N":"Y");
				
				calculateByRow(rid);
				budgetGridObj.refreshFilters();
			}
		});
		
		// 토글 처리
		cisGridObj.attachEvent("onRowSelect", function(_rid, idx){
			if(idx == cisGridObj.getColIndexById("bdgtTrgtYn")){
				cisGridObj.cells(_rid, idx).setValue(cisGridObj.cells(_rid, idx).getValue() == "Y"?"N":"Y");
				cisGridObj.refreshFilters();
			}
		});

		//편집 이벤트 처리 2020-05-16
		cisGridObj.attachEvent("onEditCell", function(stage,rId,cInd,nValue,oValue){
			console.log(stage, rId, cInd, nValue, oValue);
			if(stage == 2){
				//감사계약시합의시간 이 변경
				if(cInd == cisGridObj.getColIndexById("cntrtAdtTm")){
					let regExp = /^[0-9]+(\.[0-9]+)?$/g;
					if(nValue !== ""){
						if(regExp.test(nValue)){
							//Footer 에 값 변경
							let sumCntrtAdtTm = 0;
							let satCntrtAdtTm = 0; // [표준감사시간] 감사 계약합의시간
							let auditgbnm = 0;
							let cntrtAdtTm = 0;
							for(var i=0;i<cisGridObj.getRowsNum();i++){
								if(cisGridObj.cells2(i, cisGridObj.getColIndexById("cntrtAdtTm")).getValue() != ''){
									auditgbnm = cisGridObj.cells2(i, cisGridObj.getColIndexById("auditgbnm")).getValue();
									cntrtAdtTm = Number(cisGridObj.cells2(i, cisGridObj.getColIndexById("cntrtAdtTm")).getValue());
									
									sumCntrtAdtTm += cntrtAdtTm;
									satCntrtAdtTm += Number(auditgbnm === '외감' ? cntrtAdtTm : 0);
								}				
							}
							
							$("#sumCntrtAdtTm").html(sumCntrtAdtTm.toLocaleString(undefined));
							prjtBdgt.setValue('cntrtAdtTm', satCntrtAdtTm);
							return true;
						}else{
							Helper.MessageBox("숫자만 입력 가능합니다.");
							return false;
						}
					}
				}
			}
			return true;
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
	 						let tbdYn = budgetGridObj.cells(rId, budgetGridObj.getColIndexById("tbd")).getValue();
	 						//--------------------------------------------------------
	 						// TBD 가 Y 이면 숫자 범위 제한 금지  2020-05-16
	 						//--------------------------------------------------------
	 						if(tbdYn != 'Y'){
		 						if(Number(nValue) > maxWorkTm){
		 							Helper.MessageBox("해당 주에는 최대 " + maxWorkTm + "시간까지만 입력 가능합니다.");	
		 							return false;
		 						}else{
		 							return true;
		 						}	 							
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
	         cisGridObj.setSizes();
	    });
	}
	
	
	function loadData(){
		// 20200512 추가
		cisGridObj.clearAll();
		Helper.post("/project/v3/budget/cislist", {prjtCd:"${result.data.prjtcd}"})
		.done(function(data){			
			
			for(let i in data){
				data[i].cntrtAdtTm = {
					class: "editable",
					value: data[i].cntrtAdtTm
				}
			}
			
			cisGridObj.parse(data, "js");
			
			let sumAmtw = 0;
			let sumExbudgettime = 0;
			let sumCntrtAdtTm = 0;
			let sumBdgt = 0;
			let sumExpWkmnsp = 0;
			let sumBdgtWkmnsp = 0;
			let aveExpWkmnsp = 0;
			
			cisGridObj.forEachRow(function(rid){
				const rowData = cisGridObj.getRowData(rid);
				sumAmtw += rowData.createdvnm === '정상' || rowData.createdvnm === '이관' ? Number(rowData.amtw) : 0;
				sumExbudgettime += Number(rowData.exbudgettime);
				sumCntrtAdtTm += Number(rowData.cntrtAdtTm);
				sumBdgt += Number(rowData.bdgt);
				sumExpWkmnsp += Number(rowData.expWkmnsp);
				sumBdgtWkmnsp += Number(rowData.bdgtWkmnsp);
			});
			aveExpWkmnsp = sumBdgt === 0 ? 0 : sumBdgtWkmnsp / sumBdgt;
			
			$("#sumAmtw").html(sumAmtw.toLocaleString(undefined));
			$("#sumExbudgettime").html(sumExbudgettime.toLocaleString(undefined));
			$("#sumCntrtAdtTm").html(sumCntrtAdtTm.toLocaleString(undefined));
			$("#sumBdgt").html(sumBdgt.toLocaleString(undefined));
			$("#aveExpWkmnsp").html(aveExpWkmnsp.toLocaleString(undefined, {minimumFractionDigits: 3, maximumFractionDigits: 3}));
			
			budgetGridObj.clearAll();
			Helper.post("/project/v3/budget/list", {prjtCd:"${result.data.prjtcd}", isDraft:${isDraft}})
			.done(function(data){
				budgetGridObj.parse(data, "js");
			});
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
		
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Budget 대상으로 지정된 것만 Budget Hour에 등록되어있는지 여부 확인
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		// Budget 대상 프로젝트 코드 리스트 추출
		let bdgtTrgtlist = [];
		let checkDuplePrjt = [];
		cisGridObj.forEachRow(function(rid) {
			let data = cisGridObj.getRowData(rid);
			if(data['bdgtTrgtYn'] == 'Y') {
				bdgtTrgtlist.push(data.prjtcd);
				checkDuplePrjt.push({
					prjtcd: data.prjtcd,
					div: data.div
				});
			}
		});
		
		let existDuple = false; 
		let duplePrjt = [];
		// Budget 대상으로 지정가능 한 프로젝트인지 확인 (중복 등록 체크)
		if(bdgtTrgtlist.length > 0) {
			let retData = Helper.postSync('/project/v3/budget/checkDuplPrjt', {mainPrjtCd: '${result.data.prjtcd}',prjtList: checkDuplePrjt});
			if(retData.status === 'success') {
				if(retData.data && retData.data.length > 0){
					duplePrjt = data;
					existDuple = true;
				}
			} else {
				return ["Budget 대상 중복확인 중 오류가 발생하였습니다."]
			}
		}
		
		if(existDuple){
			return ["Budget 대상 프로젝트 중 중복이 발생하였습니다."];	
		}
		
		
		// Budget대상이 아닌 프로젝트 코드 추출
		let invalidPrjtCds = [];	 
		if(bdgtTrgtlist.length > 0){
			
			budgetGridObj.forEachRow(function(rid) {
				const bdgtData = budgetGridObj.getRowData(rid);
				if(!bdgtTrgtlist.includes(bdgtData.prjtcd)) {
					invalidPrjtCds.push(bdgtData.prjtcd);
				}
			});
			
			if(invalidPrjtCds.length > 0) {
				invalidPrjtCds = invalidPrjtCds.filter(function(data, idx, self){
					return self.indexOf(data) === idx;
				});
				msg.push("[" + invalidPrjtCds.join(", ") + "]는 CIS 예산 및 Code별 예산에서 Budget 대상으로 지정 되어 있지 않습니다.");
				msg.push("[Buget Hour]에서 해당 프로젝트코드를 삭제하거나 [CIS 예산 및 Code별 예산]에서 Budget 대상으로 지정하시기 바랍니다. ");
			}			
		} else {
			if(budgetGridObj.getRowsNum() > 0) {
				msg.push("[CIS 예산 및 Code별 예산]에 Budget 대상이 없습니다.");	
			}
		}
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					
		// Grid에서 필수 값 체크
		budgetGridObj.forEachRow(function(rid){
			/* let actvCd = budgetGridObj.getUserData(rid, "actvCd");
			let locaCd = budgetGridObj.getUserData(rid, "locaCd"); */
			let prjtCd = budgetGridObj.cellById(rid, budgetGridObj.getColIndexById("prjtcd")).getValue();
			let actvCd = budgetGridObj.cellById(rid, budgetGridObj.getColIndexById("actvCd")).getValue();
			let locaCd = budgetGridObj.cellById(rid, budgetGridObj.getColIndexById("locaCd")).getValue();
			let emplNo = budgetGridObj.cellById(rid, budgetGridObj.getColIndexById("emplNo")).getValue();
			let korNm = budgetGridObj.cellById(rid, budgetGridObj.getColIndexById("korNm")).getValue();
			
			let tbdYn = budgetGridObj.cellById(rid, budgetGridObj.getColIndexById("tbd")).getValue();
			
			let tmpMsg = [];
			if(!actvCd) tmpMsg.push("Activity");
			if(!locaCd) tmpMsg.push("Location");
			if(!korNm) tmpMsg.push("임직원");
			
			/*
			if (tbdYn == 'Y'){
				if(actvCd != '09' && actvCd != '12' && actvCd != '13' && actvCd != '14' && actvCd != '15'){
					if(!emplNo) tmpMsg.push("임직원");
				}
			} else {
				if(!emplNo) tmpMsg.push("임직원");
			}
			*/
			if(tmpMsg.length == 0){
				let key = actvCd + "_" + locaCd + "_" + emplNo + "_" + korNm + "_" + prjtCd;
				if(!keys[key]) keys[key] = 0;
				keys[key]++;
			}else{
				msg.unshift((budgetGridObj.getRowIndex(rid) + 1) + "줄: [" + tmpMsg.join("], [") + "]을 입력해 주세요")
			}
			
			// 그리드 편집시에 TBD 가 'Y' 인 경우 숫자범위제한을 금지 했다.
			// TBD 를 'Y' 로 설정하고 숫자범위제한이 금지된 상태로 제한없이 숫자 편집을 하고 나서 TBD 를 'N' 로 변경하면 잘못된 값이 그대로 유지된다.    
			// 여기서 TBD 가 Y 가 아닌 경우 숫자범위를 다시 확인한다.
			let nValue;
			let workDay;
			let maxWorkTm;
			if (tbdYn != 'Y'){
			<c:forEach var="sDt" items="${result.data.sDtOfWeek}" varStatus="weekStatus">
				nValue = budgetGridObj.cellById(rid, budgetGridObj.getColIndexById("week${weekStatus.count}")).getValue();				
				workDay = workDayList[${weekStatus.index}];
 				maxWorkTm = workDay * 16; // 업무시간을 08:00를 시작으로 24시까지 
 				
 				if(Number(nValue) > maxWorkTm){
 					msg.push((budgetGridObj.getRowIndex(rid) + 1) + "줄: week${weekStatus.count} 주에는 최대 " + maxWorkTm + "시간까지만 입력 가능합니다.");	
				}					
			</c:forEach>
			}
		});
	
		// 중복 인원 존재 여부 체크
		for(let key in keys){
			if(keys[key] > 1){
				let tmpKey = key.split("_");
				msg.push("[" + tmpKey[4] + "] " +tmpKey[3] + "("+ tmpKey[2] + ")님은 Activity, Location, Name 중복건이 존재합니다.");
			}
		}
		return msg;
	}
	
	function calculate(){
		// validation Check
		let msg = validCheck();
		if(msg.length > 0){
			Helper.MessageBox(msg);
			return false;
		}
		
		// 계산하기
		try {
			let sumEL = 0;					// [Budget 요약] 담당이사
			let sumPM = 0;					// [Budget 요약] PM
			let sumTeam = 0;				// [Budget 요약] 감사팀
			let sumRA = 0;					// [Budget 요약] RA
			let sumFulcrum = 0;				// [Budget 요약] Fulcrum
			let sumQC = 0;					// [Budget 요약] QC
			let sumQRP = 0;					// [Budget 요약] QRP
			let sumNewStaff = 0;			// [Budget 요약] New Staff
			
			let satBudgetingHours = 0;		// [표준감사시간 대상] Budgeting Hours
			let satBdgtWkmnsp = 0;			// [표준감사시간 대상] Budgeting Hours * 예상 팀 숙련도
			let satExpWkmnsp = 0;			// [표준감사시간 대상] 예상 팀 숙련도
			let satCntrtAdtTm = 0;			// [표준감사시간 대상] 감사계약시 합의시간
			
			let summaryCntrtAdtTm = 0;		// [CIS 예산 및 Code별 예산 Summary] 감사계약시 합의시간
			let summaryBugetingHour = 0;	// [CIS 예산 및 Code별 예산 Summary] Budgeting Hours
			let summaryBdgtWkmnsp = 0;		// [CIS 예산 및 Code별 예산 Summary] Budgeting Hours * 예상 팀 숙련도  
			let summaryExpWkmnsp = 0;		// [CIS 예산 및 Code별 예산 Summary] 예상 팀 숙련도
			
			
			// [CIS 예산 및 Code별 예산] 데이터 초기화
			let cisDataList = [];
			cisGridObj.forEachRow(function(rid) {
				let data = cisGridObj.getRowData(rid);
				data.id = rid;
				data.bdgt = 0;
				data.expWkmnsp = 0;
				data.bdgtWkmnsp = 0;
				cisDataList.push(data);
			});
			
			// 데이터 합산
			const weekRegExp = /^week\d+$/;		// week 열 이름.
			budgetGridObj.forEachRow(function(rid) {
				let row = budgetGridObj.getRowData(rid);
				
				// row 용 Summary 데이터 초기화
				let sumMembBdgt = 0;
				let sumMembBdgtWkmnsp = 0;
				
				// week 컬럼 탐색하여 합산
				for(let key in row) {
					if(weekRegExp.test(key) && Number(row[key]) > 0){
						sumMembBdgt += Number(row[key]);
					}
				}
				sumMembBdgtWkmnsp = sumMembBdgt * Number(row.wkmnsp);
				
				// [Budget Hour]의 Row Summary 입력
				budgetGridObj.cells(rid, budgetGridObj.getColIndexById("totAsgnTm")).setValue(sumMembBdgt);
				budgetGridObj.cells(rid, budgetGridObj.getColIndexById("totWkmnspAsgnTm")).setValue(sumMembBdgtWkmnsp);
				
				// [Budget 요약]
				if (row.emplNo === '${result.data.chargptr}') sumEL += sumMembBdgt;
				else if (row.emplNo === '${result.data.chargmgr}') sumPM += sumMembBdgt;
				else if(row.actvCd === '12') sumQC += sumMembBdgt;
				else if(row.actvCd === '16') sumQRP += sumMembBdgt;
				else if(row.actvCd === '14') sumFulcrum += sumMembBdgt;
				else if(row.actvCd === '15' && row.tbd === 'Y') sumNewStaff += sumMembBdgt;
				else if(row.actvCd === '09' || row.actvCd === '13') sumRA += sumMembBdgt;
				else sumTeam += sumMembBdgt;
				
				
				let cisProjectList = cisDataList.filter(function(data) { return data.prjtcd === row.prjtcd;});
				// [표준감사시간 대상]
				if(cisProjectList[0].auditgbnm === '외감') {
					satBudgetingHours += sumMembBdgt;
					satBdgtWkmnsp += sumMembBdgtWkmnsp;
				}
				// [CIS 예산 및 Code별 예산] - Budgeting Hour와 Budgting Hour * 숙련도 계산
				for(let i=0; i<cisProjectList.length; i++){
					cisProjectList[i].bdgt += sumMembBdgt;
					cisProjectList[i].bdgtWkmnsp += sumMembBdgtWkmnsp;
				}
			})
			satExpWkmnsp = satBudgetingHours === 0 ? 0 : satBdgtWkmnsp / satBudgetingHours;		// [표준감사시간 대상] 예상 팀 숙련도 계산
			
			// CIS 예산 및 Code별 예산 - 팀 숙련도 계산
			for(let i=0; i<cisDataList.length; i++) {
				cisDataList[i].expWkmnsp = cisDataList[i].bdgt === 0 ? 0 : cisDataList[i].bdgtWkmnsp/cisDataList[i].bdgt;
				summaryBugetingHour += cisDataList[i].bdgt;
				summaryBdgtWkmnsp += cisDataList[i].bdgtWkmnsp;
				summaryCntrtAdtTm += Number(cisDataList[i].cntrtAdtTm);
				satCntrtAdtTm += Number(cisDataList[i].auditgbnm === '외감' ? cisDataList[i].cntrtAdtTm : 0);
			}
			summaryExpWkmnsp = summaryBugetingHour === 0 ? 0 : summaryBdgtWkmnsp/summaryBugetingHour;
			
			// [표준감사시간 대상] 계획단계 표준감사시간 계산 (한공회 표준감사시간 * 기준숙련도 / 예상 팀 숙련도)
			let planSat = satExpWkmnsp === 0 ? 0 : Number('${result.data.etDfnSat}') * Number('${result.data.baseWkmnsp}') / satExpWkmnsp
			
			// Grid Data 업데이트
			cisGridObj.clearAll();
			cisGridObj.parse(cisDataList, 'js');
					
			prjtBdgt.setValue("totalTm", summaryBugetingHour);
			prjtBdgt.setValue("elTm", sumEL);
			prjtBdgt.setValue("pmTm", sumPM);
			prjtBdgt.setValue("adtteamTm", sumTeam);		
			prjtBdgt.setValue("raTm", sumRA);
			prjtBdgt.setValue("fulcrumTm", sumFulcrum);
			prjtBdgt.setValue("qcTm", sumQC);
			prjtBdgt.setValue("qrpTm", sumQRP);
			prjtBdgt.setValue("newstaffTm", sumNewStaff);
			
			prjtBdgt.setValue("satBdgtTm", satBudgetingHours);
			prjtBdgt.setValue("satExpWkmnsp", satExpWkmnsp);
			prjtBdgt.setValue("planSat", planSat);
			prjtBdgt.setValue("cntrtAdtTm", satCntrtAdtTm);
			
			
			prjtBdgt.setValue("expComplRate", (planSat == 0 ? 0 : Number('${result.data.etDfnSat}') / planSat) * 100);
			
			// [CIS 예산 및 Code별 예산] Summary 부분 업데이트
			
			$("#sumCntrtAdtTm").html(summaryCntrtAdtTm.toLocaleString(undefined));
			$("#sumBdgt").html(summaryBugetingHour.toLocaleString(undefined));
			
			return true
		} catch (e) {
			console.error(e);
			Helper.MessageBox("계산 중 오류가 발생하였습니다.");
			return false;
		}
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
		
		return {totTime: totTime, totWkmnspAsgnTm: totWkmnspAsgnTm }
	}
	
});
</script>