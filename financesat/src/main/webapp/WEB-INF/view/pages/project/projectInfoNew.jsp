<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="now" value="<%=new java.util.Date()%>" />
<c:set var="roleCd" value="${sessionScope.SAMIL_SESSION_KEY.roleCd}" />
<c:set var="maxPrjtPeriod" value="420" />

<!-- 컨텐츠 시작 -->
<h1 class="hidden">Project Form</h1>

<div id="projectNew" class="boxWhite">
	<div class="pageFixed">
		<div class="inner">
			<ul class="tabType01">
				<li id="tabPrjtInfo" class="on"><span>프로젝트 정보</span></li>
				<li sh-click="goBdgt" id="tabBdgt"><a href="function:void(0)">Budgeting</a></li>
			</ul>
			
			<div class="btnArea">
				<!-- <button sh-click="saveAsDraft" id="btnSaveAsDraft" type="button" class="btnPwc btnM action">Save as Draft</button>
				<button sh-click="save" id="btnSave" type="button" class="btnPwc btnM action">Save</button> -->
				<button sh-click="cancel" id="btnCancle" type="button" class="btnPwc btnM action">Cancel</button>
			</div>
		</div>
	</div>

	<h2 class="titDep2">Project 생성</h2>
		
	<table class="tblForm">
		<caption>프로젝트 생성 테이블</caption>
		<colgroup><col style="width:auto"><col style="width:27%"><col style="width:24%"><col style="width:27%"></colgroup>
		<tbody>
			<tr>
				<th scope="row">Project Name<span class="ness">*</span></th>
				<td colspan="3">
					<div class="searchAreaS">
						<span class="readText" sh-name="prjtNm"></span>
						<button sh-click="searchPrjt" id="btnSearchPrjt" type="button" class="btIco btnSearch">Search</button>
					</div>
				</td>
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
				<th scope="row">계약보수<span class="ness">*</span>
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">계약 보수(CIS 상 금액)</div>
					</div>
				</th>
				<td><div class="frmNowrap"><input sh-name="cntrtFee" sh-type="number" sh-format="#,##0"  type="text" min="0" class="tright"><span class="unit">원</span></div></td>
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
					<div class="frmNowrap">
						<input sh-name="dspRcrdMgr" type="text" class="frmW01" readonly>
						<button sh-click="delRcrdMgr" id="btnDelRaMgr" type="button" class="inpDel">삭제</button>
						<button sh-click="setRcrdMgr" type="button" class="btIco btnSearch02 gapL">임직원 검색</button>
					</div>
					<div class="frmNowrap">
						<input sh-name="dspRcrdMgr2" type="text" class="frmW01" readonly>
						<button sh-click="delRcrdMgr2" id="btnDelRaMgr2" type="button" class="inpDel">삭제</button>
						<button sh-click="setRcrdMgr2" type="button" class="btIco btnSearch02 gapL">임직원 검색</button>
					</div>
					<div class="frmNowrap">
						<input sh-name="dspRcrdMgr3" type="text" class="frmW01" readonly>
						<button sh-click="delRcrdMgr3" id="btnDelRaMgr3" type="button" class="inpDel">삭제</button>
						<button sh-click="setRcrdMgr3" type="button" class="btIco btnSearch02 gapL">임직원 검색</button>
					</div>
				</td>
				<th scope="row"></th>
				<td></td>
			</tr>
			<tr>
				<th scope="row">Project 시작일<span class="ness">*</span></th>
				<td>
					<div class="calenWrap">
						<input id="prjtFrdt" type="text" readonly sh-name="prjtFrdt" sh-change="prjtFrdt">
					</div>
				</td>
				<th scope="row">Project 종료일<span class="ness">*</span>
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">모니터링(Dashboard 포함)시간 집계 종료일</div>
					</div>
				</th>
				<td>
					<div class="calenWrap">
						<input id="prjtTodt" type="text" readonly sh-name="prjtTodt" sh-change="prjtTodt">
					</div>
				</td>
			</tr>
			
			
			<tr>
				<th scope="row">지정감사 여부<span class="ness">*</span></th>
				<td class="tdSel">
					<div class="selectbox frmW01">
						<select title="지정감사 여부 선택" sh-name="desigAdtYn" sh-select="desigAdtYn">
							<option value="">선택하세요.</option>
							<c:forEach var="option" items="${selOption.DESIG_ADT_YN}">
							<option value="${option.cd}">${option.nm}</option>
							</c:forEach>
						</select>
					</div>
				</td>
				<th scope="row">표준감사시간 대상 여부<span class="ness">*</span>
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">자산 1천억원 미만 비상장사(그룹 9,10), US 감사 등 임의감사 및 검토업무, 외감대상이지만 표준감사시간 적용제외 대상(SPAC 등)은 표준감사시간 대상 여부에 "X"를 선택함</div>
					</div>
				</th>
				<td class="tdSel">
					<div class="selectbox frmW01">
						<select title="표준감사시간 대상 여부" sh-name="satTrgtYn" sh-select="satTrgtYn">
							<option value="">선택하세요.</option>
							<c:forEach var="option" items="${selOption.SAT_TRGT_YN}">
							<option value="${option.cd}">${option.nm}</option>
							</c:forEach>
						</select>
					</div>
				</td>
			</tr>
		</tbody>                     
	</table>
	
	<div class="titArea">
		<h3 class="titDep3">합산 대상 코드 추가</h3>
		<button sh-click="addSubPrjt" id="addSubPrjt" type="button" class="btIco btnAdd">추가</button>
		<p class="txtInfo side">합산 대상 코드는 최대 6개까지 추가 가능합니다.</p>
	</div>
	
	<table class="tblForm tblAdd">
		<caption>합산 대상 코드 추가 테이블</caption>
		<colgroup><col style="width:50px"><col style="width:19%"><col style="width:auto"><col style="width:24%"><col style="width:27%"></colgroup>
		<!-- loof 영역 -->
		<tfoot class="lineF">
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
	
	<table  sh-test="  $satTrgtYn != 'Y' " sh-dotype="hide" class="tblForm"  style="display:none">
		<caption>표준감사시간 산출 테이블</caption>
		<colgroup><col style="width:auto"><col style="width:27%"><col style="width:24%"><col style="width:27%"></colgroup>
		<tbody>
			<tr>
				<th scope="row">표준산업 분류<span class="ness">*</span>
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							 표준산업분류에 따라  판단하되 실질에따라 선택이 가능함.
						</div>
					</div>
				</th>
				<td class="tdSel">
					<div class="selectbox frmW01">
						<select title="표준산업 분류 선택" sh-name="indusCd">
							<option value="">선택하세요.</option>
							<c:forEach var="option" items="${selOption.INDUS}">
							<option value="${option.cd}">${option.nm}</option>
							</c:forEach>
						</select>
					</div>
				</td>
				<th scope="row">상장 구분<span class="ness">*</span></th>
				<td class="tdSel">
					<div class="selectbox frmW01">
						<select title="상장 구분 선택" sh-name="listDvCd">
							<option value="">선택하세요.</option>
							<c:forEach var="option" items="${selOption.LISTDV}">
							<option value="${option.cd}" >${option.nm}</option>
							</c:forEach>
						</select>
					</div>
				</td>
			</tr>
			<tr>
				<th scope="row">개별 자산<span class="ness">*</span></th>
				<td><div class="frmNowrap"><input sh-name="indivAsset" sh-type="number" sh-format="#,##0"  type="text" class="tright"><span class="unit">원</span></div></td>
				<th scope="row">사업보고서 제출 대상<span class="ness">*</span></th>
				<td class="tdSel">
					<div class="selectbox frmW01">
						<select title="사업보고서 제출 대상 선택" sh-name="bizRprtYn">
							<option value="">선택하세요.</option>
							<c:forEach var="option" items="${selOption.BIZRPRT_YN}">
							<option value="${option.cd}">${option.nm}</option>
							</c:forEach>
						</select>
					</div>
				</td>
			</tr>
			<tr>
				<th scope="row">연결 매출<span class="ness">*</span>
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							 연결 작성을 하지 않는 경우 개별매출을 입력
						</div>
					</div>
				</th>
				<td><div class="frmNowrap"><input sh-type="number" sh-format="#,##0"  type="text" class="tright" sh-name="consoSales"><span class="unit">원</span></div></td>
				<th scope="row">그룹1의 예외사항 여부<span class="ness">*</span>
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							 <pre><c:out value="${tooltip.satgrp1Expt}" /></pre>
						</div>
					</div>
				</th>
				<td class="tdSel">
					<div class="selectbox frmW01">
						<select sh-select="satgrp1ExptYn" title="그룹1의 예외사항 여부"  sh-name="satgrp1ExptYn">
							<option value="">선택하세요.</option>
							<c:forEach var="option" items="${selOption.SATGRP1_EXPT_YN}">
							<option value="${option.cd}">${option.nm}</option>
							</c:forEach>
						</select>
					</div>
				</td>
			</tr>
			<tr>
				<th scope="row">연결 자산<span class="ness">*</span>
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							 연결 작성을 하지 않는 경우 개별자산을 입력
						</div>
					</div>
				</th>
				<td><div class="frmNowrap"><input sh-type="number" sh-format="#,##0"  type="text" class="tright" sh-name="consoAsset"><span class="unit">원</span></div></td>
				<th scope="row">전기 감사 시간<span class="ness">*</span>
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							<pre><c:out value="${tooltip.priorAdtTm}" /></pre>
						</div>
					</div>
				</th>
				<td><div class="frmNowrap"><input sh-type="number" sh-format="#,##0.00"  type="text" class="tright" sh-name="priorAdtTm"><span class="unit">hr</span></div></td>
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
				<td class="tright"><span sh-name="compSize" sh-format="#,##0"></span><span class="unit">원</span></td>
				<th scope="row">표준 감사 시간 그룹 정보<span class="ness">*</span>
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
				<td class="tdSel">
					<div id="satgrp_select_box" class="selectbox frmW01">
						<select title="표준 감사 시간 그룹 선택" sh-name="satgrpCd" sh-dotype="disable" sh-test="$satgrp1ExptYn == 'Y'">
							<option value="">선택하세요.</option>
						</select>
					</div>
				</td>
				
			</tr>
			<tr>
				<th scope="row">미국 상장 여부<span class="ness">*</span></th>
				<td class="tdSel">
					<div class="selectbox frmW01">
						<select title="미국 상장 여부 선택" sh-name="usaListYn">
							<option value="">선택하세요.</option>
							<c:forEach var="option" items="${selOption.USALIST_YN}">
							<option value="${option.cd}">${option.nm}</option>
							</c:forEach>
						</select>
					</div>
				</td>
				<th scope="row">지주사 분류<span class="ness">*</span>
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">표준산업분류에 따라 금융/ 비금융 지주를 판단하되 실질에따라 선택이 가능함.</div>
					</div>
				</th>
				<td class="tdSel">
					<div class="selectbox frmW01">
						<select title="지주사 분류 선택" sh-name="holdingsDivCd">
							<option value="">선택하세요.</option>
							<c:forEach var="option" items="${selOption.HOLDINGS_DV}">
							<option value="${option.cd}">${option.nm}</option>
							</c:forEach>
						</select>
					</div>
				</td>
			</tr>
			<tr>
				<th scope="row">연결 작성 여부<span class="ness">*</span></th>
				<td class="tdSel">
					<div class="selectbox frmW01">
						<select title="연결 작성 여부" sh-name="consoFinstatYn">
							<option value="">선택하세요.</option>
							<c:forEach var="option" items="${selOption.CONSO_YN}">
							<option value="${option.cd}">${option.nm}</option>
							</c:forEach>
						</select>
					</div>
				</td>
				<th scope="row">자회사 수<span class="ness">*</span>
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							 <pre><c:out value="${tooltip.sbsidCnt}" /></pre>
						</div>
					</div>
				</th>
				<td><div class="frmNowrap"><input sh-type="number" sh-format="#,##0"  type="text" class="tright" sh-name="sbsidCnt"><span class="unit">개</span></div></td>
			</tr>
			<tr>
				<th scope="row">보고서 발행 예정일<span class="ness">*</span></th>
				<td>
					<div class="calenWrap">
						<input id="rprtScdlDt" type="text" readonly sh-name="rprtScdlDt">
					</div>
				</td>
				<th scope="row">초도 감사 여부<span class="ness">*</span></th>
				<td class="tdSel">
					<div class="selectbox frmW01">
						<select title="초도감사 여부" sh-name="firstAdtYn"  sh-select="firstAdtYn">
							<option value="">선택하세요.</option>
							<c:forEach var="option" items="${selOption.FIRST_YN}">
							<option value="${option.cd}">${option.nm}</option>
							</c:forEach>
						</select>
					</div>
				</td>
			</tr>
			<tr>
				<th scope="row">IFRS 여부<span class="ness">*</span></th>
				<td class="tdSel">
					<div class="selectbox frmW01">
						<select title="IFRS 여부 선택" sh-name="ifrsYn">
							<option value="">선택하세요.</option>
							<c:forEach var="option" items="${selOption.IFRS_YN}">
							<option value="${option.cd}">${option.nm}</option>
							</c:forEach>
						</select>
					</div>
				</td>
				<th scope="row"><span sh-dotype="hide" sh-test="!($satgrpCd == 'SATGRP01' and $firstAdtYn == 'Y')" style="display:none" >초도 감사 가산(1.05 ~ 1.2)<span class="ness">*</span></span></th>
				<td><div class="frmNowrap"><input sh-type="number" type="text" class="frmW01 tright" sh-name="firstAdtFctr" sh-dotype="hide" sh-test="!($satgrpCd == 'SATGRP01' and $firstAdtYn == 'Y')" style="display:none"></div></td>
			</tr>
			<tr>
				<th scope="row">연결 매출채권<span class="ness">*</span>
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							 <pre><c:out value="${tooltip.consoAccntReciev}" /></pre>
						</div>
					</div>
				</th>
				<td><div class="frmNowrap"><input sh-type="number" sh-format="#,##0"  type="text" class="tright" sh-name="consoAccntReceiv"><span class="unit">원</span></div></td>
				<th scope="row">연결 재고자산<span class="ness">*</span>
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							 <pre><c:out value="${tooltip.consoInvnt}" /></pre> 
						</div>
					</div>
				</th>
				<td><div class="frmNowrap"><input sh-type="number" sh-format="#,##0"  type="text" class="tright" sh-name="consoInvnt"><span class="unit">원</span></div></td>
			</tr>
			<tr>
				<th scope="row">당기 연결 당기순손실 예상 여부<span class="ness">*</span></th>
				<td class="tdSel">
					<div class="selectbox frmW01">
						<select title="당기 연결 당기순손실 예상 여부 선택" sh-name="currConsoLossYn">
							<option value="">선택하세요.</option>
							<c:forEach var="option" items="${selOption.LOSS_YN}">
							<option value="${option.cd}">${option.nm}</option>
							</c:forEach>
						</select>
					</div>
				</td>
				<th scope="row">당기 예상 감사의견 비적정 여부<span class="ness">*</span></th>
				<td class="tdSel">
					<div class="selectbox frmW01">
						<select title="당기 예상 감사의견 비적정 여부 선택" sh-name="currAdtopinCd">
							<option value="">선택하세요.</option>
							<c:forEach var="option" items="${selOption.ADTOPIN_YN}">
							<option value="${option.cd}">${option.nm}</option>
							</c:forEach>
						</select>
					</div>
				</td>
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
				<td><div class="frmNowrap"><input sh-type="number" sh-format="#,##0"  type="text" class="tright" sh-name="intrTranAssetSales"><span class="unit">원</span></div></td>
				<th scope="row">관계회사의 자산금액
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							 <pre><c:out value="${tooltip.relatCompAsset}" /></pre>
						</div>
					</div>
				</th>
				<td><div class="frmNowrap"><input sh-type="number" sh-format="#,##0"  type="text" class="tright" sh-name="relatCompAsset"><span class="unit">원</span></div></td>
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
				<td><div class="frmNowrap"><input sh-type="number" sh-format="#,##0"  type="text" class="tright" sh-name="sbsidAssetWithIntrTran"><span class="unit">원</span></div></td>
				<th scope="row">내부거래제거전 자회사 매출
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							<pre><c:out value="${tooltip.sbsidSales}" /></pre>
						</div>
					</div>
				</th>
				<td><div class="frmNowrap"><input sh-type="number" sh-format="#,##0"  type="text" class="tright" sh-name="sbsidSalesWithIntrTran"><span class="unit">원</span></div></td>
			</tr>
			<tr>
				<!-- 2019-03-18 수정 -->
				<td colspan="4" class="tdBtn tright">
					<div class="tipArea">
						<button sh-click="calcSat" id="btnCalcSat" type="button" class="btnPwc btnS action">표준 감사시간  계산하기</button>
						<div class="tipCont">
							<pre><c:out value="${tooltip.btnCalcSat}" /></pre>
						</div>
					</div>
					<a class="btnPwc btnS" href="https://drive.google.com/drive/folders/1FMx6pfnpybbOjXNZJ9UwU3KyrvGXIphx?usp=sharing" target="_blank">Simulation File Download</a>
				</td>
			</tr>
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
				<td class="tright"><b class="colPoint01"><span sh-name="calSat" sh-format="#,##0.00"></span></b> hr</td>
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
				<td><div class="frmNowrap"><input sh-type="number" sh-format="#,##0.00"  type="text" class="tright" sh-name="etDfnSat"><span class="unit">hr</span></div></td>
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
				<th scope="row">감사계약시 합의 시간 (표준감사시간 대상)<span class="ness">*</span>
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							최종표준감사시간을 기초로 ET가 Client와 보수 협의 단계에서 실제 합의한 시간(예: 표준감사시간의 95% 로 합의한 경우, 최종표준감사시간 * 95% 를 입력)
						</div>
					</div>
				<td><div class="frmNowrap"><input sh-type="number" sh-format="#,##0.00"  type="text" class="tright inpPoint" sh-name="etTrgtAdtTm"><span class="unit">hr</span></div></td>
				<%-- <b>기준숙련도 기준 표준감사시간 (A X B)</b>
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							<pre><c:out value="${tooltip.wkmnspSat}" /></pre>
						</div>
					</div>
				</th>
				<td class="tright">
					<!-- <span sh-name="wkmnspSat" sh-format="#,##0.00"></span> hr -->
				</td> --%>
			</tr>
		</tbody>
	</table>
	
	<h2 class="titDep2">표준감사시간외 예산입력(작성된 시간은 계획단계 표준감사시간 계산시에 포함되지 않습니다)</h2>
	<table class="tblForm">
		<caption>표준감사시간외 예산입력 테이블</caption>
		<colgroup><col style="width:auto"><col style="width:78%"></colgroup>
		<tbody>
			<tr>
				<th scope="row">표준감사시간외 감사등 예산 시간</th>
				<td class="tright">
					<div class="frmNowrap etc"><input sh-type="number" sh-format="#,##0" type="text" sh-name="etcBdgtTm" class="tright"><span class="unit">hr</span></div>
				</td>
			</tr>
		</tbody>
	</table>
	<div>
		<p class="txtInfo">대상 프로젝트가 표준감사시간 대상이 아닌 프로젝트이거나, 합산 코드를 통하여 표준감사시간 대상 프로젝트와 표준감사시간 대상이 아닌 프로젝트를 하나로 Budgeting하는 경우에 표준감사시간 외 예산 시간을 입력합니다. 이 경우 Budgeting tab 상 표준감사시간 충족 여부는 개별적으로 확인해야 합니다.</p>
	</div>
	
	<h2 class="titDep2">예산 배분</h2>
		
	<table class="tblForm">
		<caption>예산 배분 테이블</caption>
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
				<th scope="row">RA 배부 예산<span class="ness">*</span></th>
				<td><div class="frmNowrap"><input sh-type="number" sh-format="#,##0.00"  type="text" class="tright" sh-name="raBdgtTm"><span class="unit">hr</span></div></td>
				<th scope="row">Fulcrum 배부 예산<span class="ness">*</span>
					<div class="tipArea tipTopLeft">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont tipW02">
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
				<td><div class="frmNowrap"><input sh-type="number" sh-format="#,##0.00"  type="text" class="frmW01 tright" sh-name="flcmBdgtTm"><span class="unit">hr</span></div></td>
			</tr>
			<tr style="display:none">
				<th scope="row">RA Manager</th>
				<td>
					<div class="frmNowrap">
						<input sh-name="dspRaBdgtMgr" type="text" class="frmW01" readonly><button sh-click="delRaMgr" id="btnDelRaMgr" type="button" class="inpDel">삭제</button>
						<button sh-click="setRaMgr" id="btnSearchRaMgr" type="button" class="btIco btnSearch02 gapL">임직원 검색</button>
					</div>
				</td>
				<th scope="row">Fulcrum Manager</th>
				<td>
					<div class="frmNowrap">
						<input sh-name="dspFlcmBdgtMgr" type="text" class="frmW01" readonly><button sh-click="delFlcmMgr" id="btnDelFlcmMgr" type="button" class="inpDel">삭제</button>
						<button sh-click="setFlcmMgr" id="btnSearchFlcmMgr" type="button" class="btIco btnSearch02 gapL">임직원 검색</button>
					</div>
				</td>
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

<script type="text/javascript" >
const popMainProject = Helper.Popup("main-project", "Project Select", "/popup/project", {size:'M', isMain:"Yes"});
const popProject = Helper.Popup("project", "Project Select", "/popup/project", {size:'M'});
const popEmp = Helper.Popup("employee", "임직원 선택", "/popup/org");

$(document).ready(function(){
	let prjtInfo;
	
	setTimeout(function(){
		let initData =  Object.assign({}, prjtDef, {
				prflId:<c:out value="${profileId}"/>, 
				newStfWkmnsp:<c:out value="${wkmnsp.newStfWkmnsp}"/>,
				otherWkmnsp:<c:out value="${wkmnsp.otherWkmnsp}"/>
			});
		
		
		prjtInfo = new ProjectInfo({
			id:"projectNew",
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
				remainBdgt: 	function(){return Number(this.etTrgtAdtTm) + Number(this.etcBdgtTm) - Number(this.raBdgtTm) - Number(this.flcmBdgtTm);},
			},
			event:{
				click:{
					goBdgt: function(e){
						e.preventDefault();
						let msg = [];
						msg.push("먼저 Save 버튼을 클릭하여 프로젝트 정보를 등록하시 바랍니다.");
						Helper.MessageBox(msg);
					},
					cancel: function(){
						Helper.goPage("/pages/project/v1/list");
					},
					save: function(){
						if(!prjtInfo.validCheck()){
							return;
						}
						
						if(!(this.satgrpCd == 'SATGRP01' && this.firstAdtYn == 'Y')){
							this.firstAdtFctr = null;
						}

						let thisObj = this;
						
						Helper.post("/project/add", thisObj)
						.done(function(){
							let msg = [];
							msg.push("저장 완료 되었습니다.");
							Helper.MessageBox(msg, function(){
								Helper.goPage("/pages/project/read", {prjtCd: thisObj.prjtCd})	
							});
						})
						.fail(function(){});
					},
					saveAsDraft: function(){
						let thisObj = this;
						
						if(thisObj.prjtCd === null){
							Helper.MessageBox(["최소한 프로젝트는 입력해야 합니다."])
							return;
						}
						
						if(!(thisObj.satgrpCd == 'SATGRP01' && thisObj.firstAdtYn == 'Y')){
							thisObj.firstAdtFctr = null;
						}
						
						Helper.post("/project/add/", Object.assign(thisObj, {stat:'DR'}))
						.done(function(){
							Helper.MessageBox("임시 저장 되었습니다.", function(){
								Helper.goPage("/pages/project/draftlist")	
							});
						})
						.fail(function(){});
					},
					searchPrjt: function(){
						let thisObj = this;
						popMainProject.openPopup("prjt", function(data){
							thisObj.prjtCd1 = data.prjtCd1;
							thisObj.prjtCd2 = data.prjtCd2;
							thisObj.prjtCd3 = data.prjtCd3;
							thisObj.prjtCd = data.prjtCd;
							thisObj.prjtNm = data.prjtNm;
							thisObj.clntNm = data.clntNm;
							thisObj.shrtNm = data.shrtNm;
							thisObj.chargPtrNm = data.chargPtrNm;
							thisObj.chargPtrGradNm = data.chargPtrGradNm;
							thisObj.chargMgrNm = data.chargMgrNm;
							thisObj.chargMgrGradNm = data.chargMgrGradNm;
							
							thisObj.chargPtr = data.chargPtr;
							thisObj.chargMgr = data.chargMgr;
							
							thisObj.cntrtFee = data.cntrtFee;
							thisObj.rprtScdlDt = data.rprtScdlDt;
							thisObj.listDvCd =  data.listDvCd;
							thisObj.bizRprtYn = data.bizRprtYn;
							thisObj.ifrsYn = data.ifrsYn;
							
						});	
					},
					calcSat: function(){
						prjtInfo.calcSat();
					},
					setRcrdMgr: function(){
						let thisObj = this;
						popEmp.openPopup('recordMgr', function(data){
							thisObj.rcrdMgrNm 		= data.kornm;
							thisObj.rcrdMgrGradNm 	= data.gradnm;
							thisObj.rcrdMgr 		= data.emplno;
						});
					},
					delRcrdMgr: function(){
						this.rcrdMgrNm 		= '';
						this.rcrdMgrGradNm 	= '';
						this.rcrdMgr 		= '';
					},
					setRcrdMgr2: function(){
						let thisObj = this;
						popEmp.openPopup('recordMgr', function(data){
							thisObj.rcrdMgrNm2 		= data.kornm;
							thisObj.rcrdMgrGradNm2 	= data.gradnm;
							thisObj.rcrdMgr2 		= data.emplno;
						});
					},
					delRcrdMgr2: function(){
						this.rcrdMgrNm2 		= '';
						this.rcrdMgrGradNm2 	= '';
						this.rcrdMgr2 			= '';
					},
					setRcrdMgr3: function(){
						let thisObj = this;
						popEmp.openPopup('recordMgr', function(data){
							thisObj.rcrdMgrNm3 		= data.kornm;
							thisObj.rcrdMgrGradNm3 	= data.gradnm;
							thisObj.rcrdMgr3 		= data.emplno;
						});
					},
					delRcrdMgr3: function(){
						this.rcrdMgrNm3 		= '';
						this.rcrdMgrGradNm3 	= '';
						this.rcrdMgr3 			= '';
					},
					setRaMgr: function(){
						let thisObj = this;
						popEmp.openPopup('raMgr', function(data){
							thisObj.raBdgtMgrNm 	= data.kornm;
							thisObj.raBdgtMgrGradNm = data.gradnm;
							thisObj.raBdgtMgr 		= data.emplno;
						});
					},
					delRaMgr: function(){
						this.raBdgtMgrNm 		= '';
						this.raBdgtMgrGradNm 	= '';
						this.raBdgtMgr 			= '';
					},
					setFlcmMgr: function(){
						let thisObj = this;
						popEmp.openPopup('flcmMgr', function(data){
							thisObj.flcmBdgtMgrNm 		= data.kornm;
							thisObj.flcmBdgtMgrGradNm 	= data.gradnm;
							thisObj.flcmBdgtMgr			= data.emplno;
						});
					},
					delFlcmMgr: function(){
						this.flcmBdgtMgrNm 		= '';
						this.flcmBdgtMgrGradNm 	= '';
						this.flcmBdgtMgr 		= '';
					},
					addSubPrjt: function(){
						prjtInfo.addSubPrjt();
						setSubPrjtHtml(this.subPrjt);
					}
				},
				select: {
					desigAdtYn: 	function(value){
						if(value== 'Y'){
							Helper.MessageBox("상장예정 법인의 경우, 상장사 기준 표준감사시간이 계산되며 동일 그룹 상장사의 분/반기검토 시간이 포함되므로 이를 고려하여 [기본산식 외 요소를 고려한 표준감사시간]을 조정합니다.");
						}
					},
					satgrp1ExptYn: 	function(value){if(value == 'Y') this.satgrpCd = 'SATGRP01';},
					satgrpCd: 		function(value){if(value != 'SATGRP01') this.firstAdtFctr = null;},
					firstAdtYn: 	function(value){
						if(this.satgrpCd == 'SATGRP01'){
							if(value == 'Y'){
								this.firstAdtFctr = null;	
							}else{
								this.firstAdtFctr = 1;
							}
						}
					},
					satTrgtYn: function(value){
						let thisObj = this;
						
						if(value == 'Y' && this.prjtCd == null){
							Helper.MessageBox('먼저 프로젝트를 선택하세요.');
							this.satTrgtYn = '';
						}else if(value == 'Y' && this.prjtFrdt == null){
							Helper.MessageBox('먼저 Project 시작일을 입력하세요');
							this.satTrgtYn = '';
						}else if(value == 'N'){
							this.etTrgtAdtTm = null;
						}else{
							if(this.prjtFrdt){								
								// 여기에서 그룹정보 가져오자
								let satgrpCd = this.satgrpCd;
								Helper.post("/project/satgrpList", {year: this.prjtFrdt.substr(0, 4)})
								.done(function(list){
						            thisObj.maxSatgrpCd = list[list.length-1].satgrpCd;
						            
									var instance = $('#satgrp_select_box > a').getInstance();
						            $('.selectbox select option').remove();
						            let tmp = [];
						            tmp.push('<option value="">선택하세요.</option>');
						            $('.selectbox select').append(tmp.concat(list.map(function(satgrp){return '<option value="'+ satgrp.satgrpCd + '">' + satgrp.satgrpNm + '</option>'})).join(''));
						            instance.resetSB();
						            instance.setValueSB(satgrpCd);
								});
							}
						}
					}
				},
				change: {
					prjtFrdt: function(value){
						$("input[name='prjtTodt']").datepicker('option', {
							maxDate: "+${maxPrjtPeriod}D"
						});
						
						if(this.satTrgtYn == 'Y' && this.prjtFrdt){
							let thisObj = this;
							let satgrpCd = this.satgrpCd;
							
							// 여기에서 그룹정보 가져오자
							Helper.post("/project/satgrpList", {year: this.prjtFrdt.substr(0, 4)})
							.done(function(list){
					            thisObj.maxSatgrpCd = list[list.length-1].satgrpCd;
					            
					            
								var instance = $('#satgrp_select_box > a').getInstance();
					            $('.selectbox select option').remove();
					            let tmp = [];
					            tmp.push('<option value="">선택하세요.</option>');
					            $('.selectbox select').append(tmp.concat(list.map(function(satgrp){return '<option value="'+ satgrp.satgrpCd + '">' + satgrp.satgrpNm + '</option>'})).join(''));
					            
					            instance.resetSB();
					            instance.setValueSB(satgrpCd);
							});
						}
					},
					prjtTodt: function(value){
						/* $("#rprtScdlDt").datepicker('setDate', new Date(value)) */
					},
				}
			},
			validation: {
				rules:{
					prjtCd:"required",
					cntrtFee:{required:true, min: 0},
					desigAdtYn:"required",
					//setDt:{required: true},
					prjtFrdt:{
						required: true, 
						minDate: function(value){
							if(value){								
								let dateCtrl = new Helper.DateCtrl();
								let dateStr = Helper.replaceAll(value, "-", "");
								return dateCtrl.toTimeObject(dateStr) >= dateCtrl.toTimeObject("20180101");
							}else{
								return false;
							}
						}
					},
					prjtTodt:{required: true},
					satTrgtYn:{required: true},
					indusCd:{
						required: function(){
							if(prjtInfo.data.satTrgtYn=='Y') {
								return true;		
							}else{								
								return false;
							}
						}
					},
					indivAsset:{
						required: function(){
							if(prjtInfo.data.satTrgtYn=='Y') return true;		
							return false;
						}
						,min: 0,
					},
					consoSales:{
						required: function(){
							if(prjtInfo.data.satTrgtYn=='Y') return true;		
							return false;
						}
						,min: 0
					},
					consoAsset:{
						required: function(){
							if(prjtInfo.data.satTrgtYn=='Y') return true;		
							return false;
						}
						,min: 1
					},
					listDvCd:{
						required: function(){
							if(prjtInfo.data.satTrgtYn=='Y') return true;		
							return false;
						}
					},
					bizRprtYn:{
						required: function(){
							if(prjtInfo.data.satTrgtYn=='Y') return true;		
							return false;
						}
					},
					satgrp1ExptYn:{
						required: function(){
							if(prjtInfo.data.satTrgtYn=='Y') return true;		
							return false;
						}
					},
					satgrpCd:{
						required: function(){
							if(prjtInfo.data.satTrgtYn=='Y') return true;		
							return false;
						}
					},
					priorAdtTm:{
						required: function(){
							if(prjtInfo.data.satTrgtYn=='Y') return true;		
							return false;
						}
						,min: 0,
					},
					usaListYn:{
						required: function(){
							if(prjtInfo.data.satTrgtYn=='Y') return true;		
							return false;
						}
					},
					holdingsDivCd:{
						required: function(){
							if(prjtInfo.data.satTrgtYn=='Y') return true;		
							return false;
						}
					},
					consoFinstatYn:{
						required: function(){
							if(prjtInfo.data.satTrgtYn=='Y') return true;		
							return false;
						}
					},
					sbsidCnt:{
						required: function(){
							if(prjtInfo.data.satTrgtYn=='Y') return true;		
							return false;
						},
						min: 0
					},
					rprtScdlDt:{
						required: function(){
							if(prjtInfo.data.satTrgtYn=='Y') return true;		
							return false;
						}
					},
					firstAdtYn:{
						required: function(){
							if(prjtInfo.data.satTrgtYn=='Y') return true;		
							return false;
						}
					},
					firstAdtFctr:{
						required: function(value){
							if(prjtInfo.data.satTrgtYn=='Y' && prjtInfo.data.satgrpCd == 'SATGRP01' && prjtInfo.data.firstAdtYn == 'Y'){
								return true; 
							}else{
								return false;
							}
						},
						min: function(value){
							if(prjtInfo.data.satTrgtYn=='Y' && prjtInfo.data.satgrpCd == 'SATGRP01' && prjtInfo.data.firstAdtYn == 'Y'){
								return Number(value) >= 1.05; 
							}else{
								return true;
							}
						},
						max: function(value){
							if(prjtInfo.data.satTrgtYn=='Y' && prjtInfo.data.satgrpCd == 'SATGRP01' && prjtInfo.data.firstAdtYn == 'Y'){
								return Number(value) <= 1.2; 
							}else{
								return true;
							}
						},
					},
					ifrsYn:{
						required: function(){
							if(prjtInfo.data.satTrgtYn=='Y') return true;		
							return false;
						}
					},
					consoInvnt:{
						required: function(){
							if(prjtInfo.data.satTrgtYn=='Y') return true;		
							return false;
						},
						min: 0
					},
					consoAccntReceiv:{
						required: function(){
							if(prjtInfo.data.satTrgtYn=='Y') return true;		
							return false;
						},
						min: 0
					},
					currConsoLossYn:{
						required: function(){
							if(prjtInfo.data.satTrgtYn=='Y') return true;		
							return false;
						}
					},
					currAdtopinCd:{
						required: function(){
							if(prjtInfo.data.satTrgtYn=='Y') return true;		
							return false;
						}
					}, 
					calSat:{
						required: function(){
							if(prjtInfo.data.satTrgtYn=='Y') return true;		
							return false;
						}
					},
					etDfnSat:{
						required: function(){
							if(prjtInfo.data.satTrgtYn=='Y') return true;		
							return false;
						},
						min: 0
					},
					etTrgtAdtTm: {
						required: function(){
							if(prjtInfo.data.satTrgtYn=='Y') return true;		
							return false;
						},
						min: 0
					}, 
					raBdgtTm: {required: true, min: 0},
					flcmBdgtTm:{required: true, min: 0},
				},
				messages:{
					prjtCd:{required: "[프로젝트]를 입력하세요."},
					cntrtFee:{required: "[계약 보수]를 입력하세요.",min: "[계약 보수]는 0 이상의 숫자를 입력해야 합니다.",},
					desigAdtYn:{required: "[지정감사 여부]를 선택하세요."},
					//setDt:{required: "[결산일]을 입력하세요"},
					prjtFrdt:{required: "[Project 시작일]을 입력하세요.", minDate: "[Project 시작일] 2018-01-01 이후날짜만 가능합니다."},
					prjtTodt:{required: "[Project 종료일]을 입력하세요."},
					satTrgtYn:{required: "[표준감사시간 대상 여부]를 선택하세요"},
					indusCd:{required: "[표준산업 분류]를 선택하세요."},
					listDvCd:{required: "[상장 구분]을 선택하세요."},
					indivAsset:{required: "[개별 자산]을 입력하세요.",min: "[개별 자산]은 0 이상의 숫자를 입력해야 합니다.",},
					bizRprtYn:{required: "[사업보고서 제출 대상]을 선택하세요."},
					consoSales:{required: "[연결 매출]을 입력하세요.",min: "[연결 매출]은 0 이상의 숫자를 입력해야 합니다.",},
					satgrp1ExptYn:{required: "[그룹1의 예외사항 여부]를 선택하세요."},
					consoAsset:{required: "[연결 자산]을 입력하세요.",min: "[연결 자산]은 1 이상의 숫자를 입력해야 합니다.",},
					priorAdtTm:{required: "[전기 감사 시간]을 입력하세요.",min: "[전기 감사 시간]은 0 이상의 숫자를 입력해야 합니다.",},
					satgrpCd:{required: "[표준 감사 시간 그룹 정보]를 선택하세요.",},
					usaListYn:{required: "[미국 상장 여부]를 선택하세요.",},
					holdingsDivCd:{required: "[지주사 분류]를 선택하세요."},
					consoFinstatYn:{required: "[연결 작성 여부]를 선택하세요."},
					sbsidCnt:{required: "[자회사 수]를 입력하세요.",min: "[자회사 수]는 0 이상의 숫자를 입력해야 합니다.",},
					rprtScdlDt:{required: "[보고서 발행 예정일]을 입력하세요."},
					firstAdtYn:{required: "[초도 감사 여부]를 선택하세요."},
					firstAdtFctr:{required: "[초도 감사 가산]을 입력하세요.",min: "[초도 감사 가산]에 1.05 이상 값을 입력하세요.(1.05 ~ 1.2)",max: "[초도 감사 가산]에 1.2 이하 값을 입력하세요.(1.05 ~ 1.2)"},
					ifrsYn:{required: "[IFRS 여부]를 선택하세요."},
					consoAccntReceiv:{required: "[연결 매출채권]을 입력하세요.",min: "[연결 매출채권]은 0 이상의 숫자를 입력해야 합니다.",},
					consoInvnt:{required: "[연결 재고자산]을 입력하세요.",min: "[연결 재고자산]은 0 이상의 숫자를 입력해야 합니다.",},
					currConsoLossYn:{required: "[당기 연결 당기순손실 예상 여부]를 선택하세요."},
					currAdtopinCd:{required: "[당기 예상 감사의견 비적정 여부]를 선택하세요."},
					calSat:{required: "[표준 감사시간 계산하기] 버튼을 클릭하여 표준감사시간을 구해야 합니다. "},
					etDfnSat:{required: "[한공회 표준감사시간(기본산식 외 요소를 고려한 최종 표준감사시간)]을 입력하세요.",min: "[한공회 표준감사시간(기본산식 외 요소를 고려한 최종 표준감사시간)]은 0 이상의 숫자를 입력해야 합니다.",},
					etTrgtAdtTm:{required: "[감사계약시 합의 시간 (표준감사시간 대상)]을 입력하세요.",min: "[감사계약시 합의 시간 (표준감사시간 대상)]은 0 이상의 숫자를 입력해야 합니다.",}, 
					raBdgtTm: {required: "[RA 배부 예산]을 입력하세요.",min: "[RA 배부 예산]은 0 이상의 숫자를 입력해야 합니다.",},
					flcmBdgtTm:{required: "[Fulcrum 배부 예산]을 입력하세요.",min: "[Fulcrum 배부 예산]은 0 이상의 숫자를 입력해야 합니다.",},
				}
			}
		});
		
		prjtInfo.init(function(){
			$("#prjtFrdt").datepicker('option', {
				minDate: new Date('2018-01-01'),
				onClose: function(selectedDate, ins){
					ins.input.closest('.calenWrap').find('.calendarWrap').fadeOut();
					$("#prjtTodt").datepicker('option', { 
						minDate: new Date(selectedDate),
						maxDate: new Date((new Date(selectedDate)).valueOf() + ${maxPrjtPeriod}*24*60*60*1000) 
					})	
				}
			});
			
			$('#ui-datepicker-div').hide();
		});
		
		function setSubPrjtHtml(){
			$(".tblAdd").find('tbody').remove();
			let subPrjt = prjtInfo.data.subPrjt;
			if(subPrjt){
				let html = [];
				for(let i=0; i<subPrjt.length; i++){
					html.push('<tbody>');
					html.push('<tr>');
					html.push('<th scope="row" rowspan="3" class="lineR thDel">' + (i+1));
					html.push('<button id="btnDelSubPrjtRow'+ i+ '" type="button" class="btIco btnDel">삭제</button>');
					html.push('</th>');
					html.push('<th scope="row">Projedt Name</th>');
					html.push('<td>');
					html.push('<div class="frmNowrap">');
					html.push('<input id="subPrjtNm'+ i+ '" type="text" class="frmW01" readonly><button  id="btnDelSubPrjt'+ i+ '"  type="button" class="inpDel">삭제</button>');
					html.push('<button id="btnSearchSubPrjt'+ i+ '" type="button" class="btIco btnSearch gapL">검색</button>');
					html.push('</div>');
					html.push('</td>');
					html.push('<th scope="row">Project Code</th>');
					html.push('<td><span id="subPrjtCd'+ i+ '"></span></td>');
					html.push('</tr>');
					html.push('<tr>');
					html.push('<th scope="row">Engagement Leader(합산코드)</th>');
					html.push('<td><span id="subPtr'+ i+ '"></span></td>');
					html.push('<th scope="row">Project Manager</th>');
					html.push('<td><span id="subMgr'+ i+ '"></span></td>');
					html.push('</tr>');
					html.push('<tr>');
					html.push('<th scope="row">보수</th>');
					html.push('<td colspan="3"	class="tright"><div class="frmNowrap etc"><input id="subCntrtFee'+ i+ '" type="text" class="frmW01 tright"><span class="unit">원</span></div></td>');
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
					
					let num = subPrjt[i].cntrtFee.toString().match(/\-?\d+(?=\.)?/g);		// 정수영역
					let num2 = subPrjt[i].cntrtFee.toString().match(/\.\d+/g);			// 소수점 영역	
					$("#subCntrtFee" + i).val($.number(num[0]) + (num2?num2[0]:''));
					
					$("#btnDelSubPrjtRow" + i).off('click').on('click', function(){
						prjtInfo.data.isUpdatedSubPrjt = "Y";
						prjtInfo.rmSubPrjt(idx);
						setSubPrjtHtml();
					})
					
					$("#btnDelSubPrjt" + i).off('click').on('click', function(){
						prjtInfo.data.isUpdatedSubPrjt = "Y";
						prjtInfo.updSubPrjt(idx, {});
						setSubPrjtHtml();
					})
					
					$("#btnSearchSubPrjt" + i).off('click').on('click', function(){
						popProject.openPopup("subPrjt" + idx, function(data){
							prjtInfo.data.isUpdatedSubPrjt = "Y";
							prjtInfo.updSubPrjt(idx, {
								prjtCd: data.prjtCd,
								prjtNm: data.prjtNm,
								chargPtr: data.chargPtr,
								chargPtrNm: data.chargPtrNm,
								chargPtrGradNm: data.chargPtrGradNm,
								chargMgr: data.chargMgr,
								chargMgrNm: data.chargMgrNm,
								chargMgrGradNm: data.chargMgrGradNm,
							})
							setSubPrjtHtml();
						});	
					});
					$("#subCntrtFee" + i).off('change').on('change', function(){
						prjtInfo.data.isUpdatedSubPrjt = "Y";
						
						let valStr = $.trim($(this).val());
						let value = valStr == ''?null:Number(valStr.toString().replace(/,|\s/g, '')); 
						
						if(isNaN(value)){
							Helper.MessageBox("숫자만 입력 가능합니다.");
							prjtInfo.updSubPrjt(Object.assign(prjtInfo.data.subPrjt[idx], {cntrtFee: null}));
							$("#subCntrtFee" + idx).val("");
						}else{
							prjtInfo.updSubPrjt(Object.assign(prjtInfo.data.subPrjt[idx], {cntrtFee: value}));
							
							let num = value.toString().match(/\-?\d+(?=\.)?/g);		// 정수영역
							let num2 = value.toString().match(/\.\d+/g);			// 소수점 영역
							
							$("#subCntrtFee" + idx).val($.number(num[0]) + (num2?num2[0]:''));
						}
						prjtInfo.__refreshView('totCntrtFee', prjtInfo.data.totCntrtFee);
					});
				}
			}
		}
	}, 10);
	
});
</script>