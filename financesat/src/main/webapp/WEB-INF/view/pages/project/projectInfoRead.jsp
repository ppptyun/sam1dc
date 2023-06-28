<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="now" value="<%=new java.util.Date()%>" />
<c:set var="roleCd" value="${sessionScope.SAMIL_SESSION_KEY.roleCd}" />

<c:set var="canEdit" value="${auth.canEdit}" />
<c:set var="canDelete" value="${auth.canDelete}" />


<h1 class="hidden">Project Form</h1>

<div id="projectRead" class="boxWhite">
	<div class="pageFixed">
		<div class="inner">
			<ul class="tabType01">
				<li id="tabPrjtInfo" class="on"><span>프로젝트 정보</span></li>
				<li sh-click="goBdgt" id="tabBdgt"><a href="function:void(0)">Budgeting</a></li>
			</ul>
			<div class="btnArea">
			<c:if test="${canEdit}">
				<!-- <button sh-click="edit" id="btnEdit" type="button" class="btnPwc btnM action">Edit</button> -->
			</c:if>
			<c:if test="${canDelete}">
				<!-- <button sh-click="delete" id="btnDelete" type="button" class="btnPwc btnM action">Delete</button> -->
			</c:if>
				<button sh-click="close" id="btnClose" type="button" class="btnPwc btnM action">Close</button>
			</div>
		</div>
	</div>

	<h2 class="titDep2">Project 생성 </h2>
		
	<table class="tblForm">
		<caption>프로젝트 생성 테이블</caption>
		<colgroup><col style="width:auto"><col style="width:27%"><col style="width:24%"><col style="width:27%"></colgroup>
		<tbody>
			<tr>
				<th scope="row">Project Name</th>
				<td colspan="3"><span class="readText" sh-name="prjtNm"></span></td>
			</tr>
			<tr>
				<th scope="row">Client Name</th>
				<td><span sh-name="clntNm"></span></td>
				<th scope="row">Project Code</th>
				<td><span sh-name="prjtCd"></span></td>
			</tr>
			<tr>
				<th scope="row">용역명</th>
				<td><span sh-name="shrtNm"></span></td>
				<th scope="row">계약보수
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">계약 보수(CIS 상 금액)</div>
					</div>
				</th>
				<td class="tright"><span sh-name="cntrtFee" sh-format="#,##0"></span> 원</td>
			</tr>
			<tr>
				<th scope="row">Engagement Leader</th>
				<td><span sh-name="dspChargPtr"></span></td>
				<th scope="row">Project Manager</th>
				<td><span sh-name="dspChargMgr"></span></td>
			</tr>
			<tr>
				<th scope="row">Record Manager</th>
				<td>
					<div><span sh-name="dspRcrdMgr"></span></div>
					<div><span sh-name="dspRcrdMgr2"></span></div>
					<div><span sh-name="dspRcrdMgr3"></span></div>
				</td>
				<th scope="row"></th>
				<td></td>
			</tr>
			<tr>
				<th scope="row">Project 시작일</th>
				<td><span sh-name="prjtFrdt"></span></td>
				<th scope="row">Project 종료일
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">모니터링(Dashboard 포함)시간 집계 종료일</div>
					</div>
				</th>
				<td><span sh-name="prjtTodt"></span></td>
			</tr>
			<tr>
				<th scope="row">지정감사 여부</th>
				<td><span sh-name="desigAdtYnNm"></span></td>
				<th scope="row">표준감사시간 대상 여부
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">자산 1천억원 미만 비상장사(그룹 9,10), US 감사 등 임의감사 및 검토업무, 외감대상이지만 표준감사시간 적용제외 대상(SPAC 등)은 표준감사시간 대상 여부에 "X"를 선택함</div>
					</div>
				</th>
				<td><span sh-name="satTrgtYnNm"></span></td>
			</tr>
		</tbody>                     
	</table>
	
	<div id="subPrjtControllerLayer" class="titArea" style="display:none">
		<h3 class="titDep3">합산 대상 코드</h3>
	</div>
	
	<table class="tblForm tblAdd">
		<caption>합산 대상 코드 테이블</caption>
		<colgroup><col style="width:50px"><col style="width:19%"><col style="width:auto"><col style="width:24%"><col style="width:27%"></colgroup><!-- 2019-03-06 수정 -->
		<!-- loof 영역 -->
		<tfoot>
			<tr>
				<th scope="row" colspan="2">총 보수</th>
				<td colspan="3"><span sh-name="totCntrtFee" sh-format="#,##0"></span> 원</td>
			</tr>
		</tfoot>   
	</table>
	
	<div sh-test=" $satTrgtYn != 'Y' " sh-dotype="hide" class="titArea" style="display:none;margin-top:30px;margin-bottom:15px;">
		<h2 class="titDep2">표준감사시간 산출</h2>
		<div class="btnArea side">
			<button type="button" class="btnPwc btnM" onclick="javascript:window.open('https://sites.google.com/pwc.com/sta')">규정, FAQ</button>
		</div>
	</div>

	<table  sh-test="  $satTrgtYn != 'Y' " sh-dotype="hide"  style="display:none" class="tblForm" >
		<caption>표준감사시간 산출 테이블</caption>
		<colgroup><col style="width:auto"><col style="width:27%"><col style="width:24%"><col style="width:27%"></colgroup><!-- 2019-03-06 수정 -->
		<tbody>
			
			<tr>
				<th scope="row">표준산업 분류
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							 표준산업분류에 따라  판단하되 실질에따라 선택이 가능함.
						</div>
					</div>
				</th>
				<td><span sh-name="indusNm"></span></td>
				<th scope="row">상장 구분</th>
				<td><span sh-name="listDvNm"></span></td>
			</tr>
			<tr>
				<th scope="row">개별 자산</th>
				<td class="tright"><span sh-name="indivAsset" sh-format="#,##0"></span> 원</td>
				<th scope="row">사업보고서 제출 대상</th>
				<td><span sh-name="bizRprtYnNm"></span></td>
			</tr>
			<tr>
				<th scope="row">연결 매출
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							 연결 작성을 하지 않는 경우 개별매출을 입력
						</div>
					</div>
				</th>
				<td class="tright"><span sh-name="consoSales" sh-format="#,##0"></span> 원</td>
				<th scope="row">그룹1의 예외사항 여부
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							 <pre><c:out value="${tooltip.satgrp1Expt}" /></pre>
						</div>
					</div>
				</th>
				<td><span sh-name="satgrp1ExptYnNm"></span></td>
			</tr>
			<tr>
				<th scope="row">연결 자산
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							 연결 작성을 하지 않는 경우 개별자산을 입력
						</div>
					</div>
				</th>
				<td class="tright"><span sh-name="consoAsset" sh-format="#,##0"></span> 원</td>
				<th scope="row">전기 감사 시간
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							 <pre><c:out value="${tooltip.priorAdtTm}" /></pre>
						</div>
					</div>
				</th>
				<td class="tright" ><span sh-name="priorAdtTm" sh-format="#,##0.00"></span> hr</td>
			</tr>
			<tr>
				<th scope="row">기업 규모
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							 <pre><c:out value="${tooltip.compSize}" /></pre>
						</div>
					</div>
				</th>
				<td class="tright"><span sh-name="compSize" sh-format="#,##0"></span> 원</td>
				<th scope="row">표준 감사 시간 그룹 정보
					<div class="tipArea tipLeft">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont tipW02">
							 <table class="tblH">
							 	<caption>표준 감사 시간 그룹 정보 설명 테이블입니다.</caption>
								<colgroup><col style="width:12%"><col style="width:17%"><col style="width:20%" span="2"><col style="width:auto"></colgroup>
								<thead>
									<tr>
										<th scope="col">구분</th>
										<th scope="col">기업구분</th>
										<th scope="col">기업규모</th>
										<th scope="col">개별자산</th>
										<th scope="col">사업보고서 제출대상</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>그룹1</td>
										<td class="tleft">상장</td>
										<td class="tright">5조원 이상</td>
										<td class="tright">2조원 이상</td>
										<td class="tleft">-</td>
									</tr>
									<tr>
										<td>그룹2</td>
										<td class="tleft">상장</td>
										<td class="tright">-</td>
										<td class="tright">2조원 이상</td>
										<td class="tleft">-</td>
									</tr>
									<tr>
										<td>그룹3</td>
										<td class="tleft">상장</td>
										<td class="tright">-</td>
										<td class="tright">5,000억원 이상</td>
										<td class="tleft">-</td>
									</tr>
									<tr>
										<td>그룹4</td>
										<td class="tleft">상장</td>
										<td class="tright">-</td>
										<td class="tright">1,000억원 이상</td>
										<td class="tleft">-</td>
									</tr>
									<tr>
										<td>그룹5</td>
										<td class="tleft">상장</td>
										<td class="tright">-</td>
										<td class="tright">500억원 이상</td>
										<td class="tleft">-</td>
									</tr>
									<tr>
										<td>그룹6</td>
										<td class="tleft">상장</td>
										<td class="tright">-</td>
										<td class="tright">500억원 미만</td>
										<td class="tleft">-</td>
									</tr>
									<tr>
										<td>그룹7</td>
										<td class="tleft">코넥스상장</td>
										<td class="tright">-</td>
										<td class="tright">-</td>
										<td class="tleft">Yes</td>
									</tr>
									<tr>
										<td>그룹8</td>
										<td class="tleft">비상장</td>
										<td class="tright">-</td>
										<td class="tright">1,000억원 이상</td>
										<td class="tleft">-</td>
									</tr>
									<tr>
										<td>그룹9</td>
										<td class="tleft">비상장</td>
										<td class="tright">-</td>
										<td class="tright">500억원 이상</td>
										<td class="tleft">-</td>
									</tr>
									<tr>
										<td>그룹10</td>
										<td class="tleft">비상장</td>
										<td class="tright">-</td>
										<td class="tright">200억원 이상</td>
										<td class="tleft">-</td>
									</tr>
									<tr>
										<td>그룹11</td>
										<td colspan="4">1~10 그룹에 포함되지 않으면 11그룹임</td>
									</tr>
								</tbody>
							 </table>
						</div>
					</div>
				</th>
				<td><span sh-name="satgrpNm"></span></td>
			</tr>
			<tr>
				<th scope="row">미국 상장 여부 </th>
				<td><span sh-name="usaListYnNm"></span></td>
				<th scope="row">지주사 분류
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">표준산업분류에 따라 금융/ 비금융 지주를 판단하되 실질에따라 선택이 가능함.</div>
					</div> 
				</th>
				<td><span sh-name="holdingsDivNm"></span></td>
			</tr>
			<tr>
				<th scope="row">연결 작성 여부</th>
				<td><span sh-name="consoFinstatYnNm"></span></td>
				<th scope="row">자회사 수
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							 <pre><c:out value="${tooltip.sbsidCnt}" /></pre>
						</div>
					</div>
				</th>
				<td class="tright"><span sh-name="sbsidCnt" sh-format="#,##0"></span> 개</td>
			</tr>
			<tr>
				<th scope="row">보고서 발행 예정일 </th>
				<td><span sh-name="rprtScdlDt"></span></td>
				<th scope="row">초도 감사 여부</th>
				<td class=""><span sh-name="firstAdtYnNm"></span></td>
			</tr>
			<tr>
				<th scope="row">IFRS 여부</th>
				<td><span sh-name="ifrsYnNm"></span></td>
				<th scope="row"><span sh-dotype="hide" sh-test="!($satgrpCd == 'SATGRP01' and $firstAdtYn == 'Y')" style="display:none" >초도 감사 가산</span></th>
				<td class="tright"><span sh-name="firstAdtFctr" sh-dotype="hide" sh-test="!($satgrpCd == 'SATGRP01' and $firstAdtYn == 'Y')" style="display:none"></span></td>
			</tr>
			<tr>
				<th scope="row">연결 매출채권
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							 <pre><c:out value="${tooltip.consoAccntReciev}" /></pre>
						</div>
					</div>
				</th>
				<td class="tright"><span sh-name="consoAccntReceiv" sh-format="#,##0"></span> 원</td>
				<th scope="row">연결 재고자산
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							 <pre><c:out value="${tooltip.consoInvnt}" /></pre> 
						</div>
					</div>
				</th>
				<td class="tright"><span sh-name="consoInvnt" sh-format="#,##0"></span> 원</td>
			</tr>
			<tr>
				<th scope="row">당기 연결 당기순손실 예상 여부 </th>
				<td><span sh-name="currConsoLossYnNm"></span></td>
				<th scope="row">당기 예상 감사의견 비적정 여부</th>
				<td><span sh-name="currAdtopinNm"></span></td>
			</tr>
			<tr>
				<th scope="row">연결 조정시 제거된 내부거래(매출+자산)
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							 <pre><c:out value="${tooltip.intrTranAmt}" /></pre>
						</div>
					</div>
				</th>
				<td class="tright"><span sh-name="intrTranAssetSales" sh-format="#,##0"></span> 원</td>
				<th scope="row">관계회사의 자산금액
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							 <pre><c:out value="${tooltip.relatCompAsset}" /></pre>
						</div>
					</div>
				</th>
				<td class="tright"><span sh-name="relatCompAsset" sh-format="#,##0"></span> 원</td>
			</tr>
			<tr>
				<th scope="row">내부거래제거전 자회사 자산
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							<pre><c:out value="${tooltip.sbsidAsset}" /></pre>
						</div>
					</div>
				</th>
				<td class="tright"><span sh-name="sbsidAssetWithIntrTran" sh-format="#,##0"></span> 원</td>
				<th scope="row">내부거래제거전 자회사 매출
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							<pre><c:out value="${tooltip.sbsidSales}" /></pre>
						</div>
					</div>
				</th>
				<td class="tright"><span sh-name="sbsidSalesWithIntrTran" sh-format="#,##0"></span> 원</td>
			</tr>
		</tbody>
	</table>
				
	<table  sh-test="  $satTrgtYn != 'Y' " sh-dotype="hide"  style="display:none"  class="tblForm marT40 lineT">
		<caption>표준감사시간 산출 테이블</caption>
		<colgroup><col style="width:auto"><col style="width:27%"><col style="width:24%"><col style="width:27%"></colgroup>
		<tbody>
			<tr>
				<th scope="row">가감률</th>
				<td class="tright"><span sh-name="factorVal" sh-format="#,##0.00"></span></td>
				<th scope="row">산출된 감사시간(a)
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							<pre><c:out value="${tooltip.tmpSat}" /></pre>
						</div>
					</div>
				</th>
				<td class="tright"><span sh-name="calAdtTm" sh-format="#,##0.00"></span> hr</td>
			</tr>
			<tr>
				<th scope="row">내부회계감사(2조 이상) 여부</th>
				<td><span sh-name="intrAdtYnNm"></span></td>
				<th scope="row">내부 감사시간(b)</th>
				<td class="tright"><b class="colPoint01"><span sh-name="intrAdtTm" sh-format="#,##0.00"></span></b> hr</td>
			</tr>
			<tr>
				<th scope="row">숙련도 반영전 표준감사시간(a+b)
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							<pre><c:out value="${tooltip.calSat}" /></pre>
						</div>
					</div>
				</th>
				<td class="tright"><span sh-name="calSat" sh-format="#,##0.00"></span> hr</td>
				<th scope="row">한공회 표준감사시간<span class="ness">*</span><br>(기본산식 외 요소를 고려한 최종 표준감사시간)
					<div class="tipArea tipLeft">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont tipW02">
							<p>일반적으로 숙련도 반영전 표준감사시간과 동일합니다. 다만, 다음과 같은  상황의 경우 기본산식 외에 ET의 판단하에 적절한 가감 후 표준감사시간을 입력하여야 합니다.</p>
							<p></p>
							<ul>
								<li>상장예정회사의 경우 상장으로 분류되어 반기검토시간이 기본적으로 포함되므로 조정이 필요</li>
								<li>전기에 순손실이 발생하였거나 비적정의견이 표명된 경우</li>
								<li>보간법이 아닌 가장근접한 위험계정비중에 해당하는 가산율을 적용하려는 경우</li>
								<li>표준감사시간표에 제시되지 않은 기업규모를 보간법이 아닌 다른 가정으로 사용하려는 경우(FAQ 참조)</li> 
								<li>전기 감사시간이 비합리적인 경우(전전기 순손실, 의견변형에서 전기 순이익, 적정으로 변경 등)</li>
								<li>감사인이 회계감사기준을 충실히 준수하고 적정한 감사품질을 유지하기 위해 투입해야 하는 감사시간과 괴리가 있다고 판단되는 경우 (한공회통보필요)</li>
							</ul>
						</div>
					</div>
				</th>
				<td class="tright"><b class="colPoint01"><span sh-name="etDfnSat" sh-format="#,##0.00"></span></b> hr</td>
			</tr>
			<tr>
				<th scope="row">기준 숙련도(B)
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							<pre><c:out value="${tooltip.baseWkmnsp}" /></pre>
						</div>
					</div>
				</th>
				<td class="tright"><span sh-name="baseWkmnsp"></span></td>
				<th scope="row">감사계약시 합의 시간 (표준감사시간 대상)
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							최종표준감사시간을 기초로 ET가 Client와 보수 협의 단계에서 실제 합의한 시간(예: 표준감사시간의 95% 로 합의한 경우, 최종표준감사시간 * 95% 를 입력)
						</div>
					</div>
				<td class="tright"><b class="colPoint01"><span sh-name="etTrgtAdtTm" sh-format="#,##0.00"></span></b> hr</td>
				<%-- <th scope="row"><b>기준숙련도 기준 표준감사시간 (A X B)</b>
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							<pre><c:out value="${tooltip.wkmnspSat}" /></pre>
						</div>
					</div> 
				</th>
				<td class="tright"><span sh-name="wkmnspSat" sh-format="#,##0.00"></span> hr</td> --%>
			</tr>
		</tbody>
	</table>
	
	<h2 class="titDep2">표준감사시간외 예산입력(작성된 시간은 계획단계 표준감사시간 계산시에 포함되지 않습니다)</h2>
	<table class="tblForm">
		<caption>표준감사시간외 예산입력 테이블</caption>
		<colgroup><col style="width:auto"><col style="width:78%"></colgroup><!-- 2019-03-06 수정 -->
		<tbody>
			<tr>
				<th scope="row">표준감사시간외 감사등 예산 시간(작성된 시간은 계획단계 표준감사시간 계산시에 포함되지 않습니다)</th>
				<td class="tright">
					<span sh-name="etcBdgtTm" sh-format="#,##0.00"></span> hr
				</td>
			</tr>
		</tbody>
	</table>
	<div>
		<p class="txtInfo">대상 프로젝트가 표준감사시간 대상이 아닌 프로젝트이거나, 합산 코드를 통하여 표준감사시간 대상 프로젝트와 표준감사시간 대상이 아닌 프로젝트를 하나로 Budgeting하는 경우에 표준감사시간 외 예산 시간을 입력합니다. 이 경우 Budgeting tab 상 표준감사시간 충족 여부는 개별적으로 확인해야 합니다.</p>
	</div>
	
	<h2 class="titDep2">예산 배분</h2>
		
	<table class="tblForm">
		<caption>프로젝트 생성 테이블</caption>
		<colgroup><col style="width:auto"><col style="width:27%"><col style="width:24%"><col style="width:27%"></colgroup>
		<tbody>
			<tr>
				<th scope="row">감사계약시 합의 시간
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							<pre><c:out value="${tooltip.totPrjtBdgt}" /></pre>
						</div>
					</div>
				</th>
				<td colspan="3" class="tright"><span sh-name="totPrjtBdgt" sh-format="#,##0.00"></span> hr</td>
			</tr>
			<tr>
				<th scope="row">RA 배부 예산</th>
				<td class="tright"><span sh-name="raBdgtTm" sh-format="#,##0.00"></span> hr</td>
				<th scope="row">Fulcrum 배부 예산
					<div class="tipArea tipTopLeft">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont tipW02" >
							<p>PPA/손상/공정가치/법인세에 대한 감사 수행 예상 시간(*하기 표준시간 참고)을 입력</p>
							<p><strong>&lt;Topic 별 표준시간 참고자료&gt;</strong><br>Topic별 업무 예상시간으로 상황에 따라 변동될 수 있습니다.</p>
							 <table class="tblH">
							 	<caption>Fulcrum 배부 예산</caption>
								<colgroup><col style="width:80%"><col style="width:20%"></colgroup>
								<thead>
									<tr>
										<th scope="col">Topic</th>
										<th scope="col">표준시간</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td class="tleft">PPA</td>
										<td class="tright">72</td>
									</tr>
									<tr>
										<td class="tleft">보통주-FVPL/FVOCI</td>
										<td class="tright">50</td>
									</tr>
									<tr>
										<td class="tleft">CB, RCPS, BW, CPS, SO</td>
										<td class="tright">52</td>
									</tr>
									<tr>
										<td class="tleft">(손상)영업권, 종속기업 및 관계기업투자, 유무형자산</td>
										<td class="tright">72</td>
									</tr>
									<tr>
										<td class="tleft">법인세</td>
										<td class="tright">20~30</td>
									</tr>
								</tbody>
							 </table>
						</div>
					</div>
				</th>
				<td class="tright"><span sh-name="flcmBdgtTm" sh-format="#,##0.00"></span> hr</td>
			</tr>
			<tr style="display:none">
				<th scope="row">RA Manager</th>
				<td><span sh-name="dspRaBdgtMgr"></span></td>
				<th scope="row">Fulcrum Manager</th>
				<td><span sh-name="dspFlcmBdgtMgr"></span></td>
			</tr>
			<tr>
				<th scope="row">잔여 예산</th>
				<td class="tright"><span sh-name="remainBdgt" sh-format="#,##0.00"></span> hr</td>
				<th scope="row"></th>
				<td></td>
			</tr>
		</tbody>
	</table>
</div><!-- //boxWhite -->
<!-- //컨텐츠 끝 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/SHView.js?version=<fmt:formatDate value="${now}" pattern="yyyyMMddHHmmss" />"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/ProjectInfo.js?version=<fmt:formatDate value="${now}" pattern="yyyyMMddHHmmss" />"></script>
<script type="text/javascript">
$(document).ready(function(){
	let prjtInfo;

	
	setTimeout(function(){
		Helper.post("/project/info", {prjtCd:"${prjtCd}"})
		.done(function(data){
			let initData =  Object.assign({}, prjtDef, data);
			let prjtCd = data.prjtCd;
			
			prjtInfo = new ProjectInfo({
				id:"projectRead",
				data: initData,
				computed:{
					totCntrtFee: function(){
						let cntrtFee = Number(this.cntrtFee);
						if(this.subPrjt) for(let i=0; i<this.subPrjt.length; i++) cntrtFee += Number(this.subPrjt[i].cntrtFee);
						return Number(cntrtFee);
					},
					dspChargPtr: 	function(){return this.chargPtr?this.chargPtrNm + "(" + this.chargPtr + ") " + this.chargPtrGradNm:"";},
					dspChargMgr: 	function(){return this.chargMgr?this.chargMgrNm + "(" + this.chargMgr + ") " + this.chargMgrGradNm:"";},
					dspRcrdMgr: 	function(){return this.rcrdMgr?this.rcrdMgrNm + "(" + this.rcrdMgr + ") " + this.rcrdMgrGradNm:"";},
					dspRcrdMgr2: 	function(){return this.rcrdMgr2?this.rcrdMgrNm2 + "(" + this.rcrdMgr2 + ") " + this.rcrdMgrGradNm2:"";},
					dspRcrdMgr3: 	function(){return this.rcrdMgr3?this.rcrdMgrNm3 + "(" + this.rcrdMgr3 + ") " + this.rcrdMgrGradNm3:"";},
					dspRaBdgtMgr: 	function(){return this.raBdgtMgr?this.raBdgtMgrNm + "(" + this.raBdgtMgr + ") " + this.raBdgtMgrGradNm:"";},
					dspFlcmBdgtMgr: function(){return this.flcmBdgtMgr?this.flcmBdgtMgrNm + "(" + this.flcmBdgtMgr + ") " + this.flcmBdgtMgrGradNm:"";},
					compSize: 		function(){return (Number(this.consoSales) + Number(this.consoAsset))/2;},
					wkmnspSat: 		function(){return Number(this.etDfnSat) * Number(this.baseWkmnsp);},
					totPrjtBdgt: 	function(){return Number(this.etTrgtAdtTm) + Number(this.etcBdgtTm);},
					remainBdgt: 	function(){return Number(this.etTrgtAdtTm) + Number(this.etcBdgtTm) - Number(this.raBdgtTm) - Number(this.flcmBdgtTm);}
				},
				event:{
					click:{
						goBdgt: function(e){
							e.preventDefault();
							Helper.goPage("/pages/project/budget/read", {prjtCd: this.prjtCd});
						},
						close: function(){
							Helper.goPage("/pages/project/v2/list");
						},
						<c:if test="${canEdit}">
						edit: function(){
							Helper.goPage("/pages/project/edit", {prjtCd: "${prjtCd}"});
						},
						</c:if>
						<c:if test="${canDelete}">
						'delete': function(){
							Helper.ConfirmBox("삭제 하시겠습니까?", function(flag){
								if(flag){
									Helper.post("/project/delete", {prjtCd: "${prjtCd}"})
									.done(function(){
										Helper.MessageBox("삭제 되었습니다.", function(){
											Helper.goPage("/pages/project/v2/list");
										})
									});	
								}
							});
						},
						</c:if>
					},
				}
			})
			
			prjtInfo.init(function(){
				prjtInfo.initSubPrjt();
				setSubPrjtHtml();
			});
			
			function setSubPrjtHtml(){
				$(".tblAdd").find('tbody').remove();
				let subPrjt = prjtInfo.data.subPrjt;
				if(subPrjt){
					$("#subPrjtControllerLayer").show();
					let html = [];
					for(let i=0; i<subPrjt.length; i++){
						html.push('<tbody>');
						html.push('<tr>');
						html.push('<th scope="row" rowspan="3" class="lineR thDel">' + (i+1));
						html.push('</th>');
						html.push('<th scope="row">Projedt Name</th>');
						html.push('<td>'+ subPrjt[i].prjtNm + '</td>');
						html.push('<th scope="row">Project Code</th>');
						html.push('<td>'+ subPrjt[i].prjtCd + '</td>');
						html.push('</tr>');
						html.push('<tr>');
						html.push('<th scope="row">Engagement Leader(합산코드)</th>');
						html.push('<td>'+ subPrjt[i].dspChargPtr + '</td>');
						html.push('<th scope="row">Project Manager</th>');
						html.push('<td>'+ subPrjt[i].dspChargMgr + '</td>');
						html.push('</tr>');
						html.push('<tr>');
						html.push('<th scope="row">보수</th>');
						html.push('<td colspan="3" class="tdRight">'+ $.number(subPrjt[i].cntrtFee) + ' 원</td>');
						html.push('</tr>');
						html.push('</tbody>');
					}
					$(".tblAdd").find('colgroup').after($(html.join('')));
					
					for(let i=0; i<subPrjt.length; i++){
						let idx = i;
						$("#subPrjtNm" + i).val(subPrjt[i].prjtNm)
						$("#subPrjtCd" + i).text(subPrjt[i].prjtCd)
						$("#subPtr" + i).text(subPrjt[i].dspChargPtr)
						$("#subMgr" + i).text(subPrjt[i].dspChargMgr)
						$("#subCntrtFee" + i).val(subPrjt[i].cntrtFee)
					}
				}
			}
			
		})
		.fail(function(msg){
			Helper.MessageBox(msg, function(){				
				Helper.goPage('/');
			})
		});
	}, 10);
})
</script>