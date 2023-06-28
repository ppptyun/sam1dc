<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="now" value="<%=new java.util.Date()%>" />
<c:set var="isDraft" value="${auth.stat == 'DR'}" />
<c:set var="roleCd" value="${sessionScope.SAMIL_SESSION_KEY.roleCd}" />
<c:set var="canEdit" value="${auth.canEdit==null?false:auth.canEdit}" />
<c:set var="canDelete" value="${auth.canDelete==null?false:auth.canDelete}" />

<!-- 컨텐츠 시작 -->
<h1 class="hidden">Project Form</h1>
<div id="calSatEdit" :class="{ieBody: isIE, boxWhite: true, readmode:isRead}" style="display:none">
	<div class="pageFixed">
		<div class="inner">
			<ul class="tabType01">
				<li id="tabPrjtInfo" class="on"><span>표준감사시간 계산</span></li>
			</ul>
			<div class="btnArea">
				<button v-if="(mode == 'read' || mode == 'edit') && canEdit && !isDraft" @click="onCopy" type="button" class="btnPwc btnM action">Copy</button>
				<button v-if="mode == 'new' || (mode == 'edit' && isDraft)" @click="onSaveAsDraft" type="button" class="btnPwc btnM action">Save as Draft</button>
				<button v-if="mode == 'read' && canEdit" @click="onEdit" type="button" class="btnPwc btnM action">Edit</button>
				<button v-if="mode == 'read' && canDelete" @click="onDelete" class="btnPwc btnM action">Delete</button>
				<button v-if="mode == 'new' || (mode == 'edit' && canEdit)" @click="onSave" type="button" class="btnPwc btnM action">Save</button>
				<button @click="onCancel" type="button" class="btnPwc btnM action">{{mode=="edit"?"Cancel":"Close"}}</button>
			</div>
		</div>
	</div>

	<div class="titArea" style="margin-bottom:15px;">
		<h2 class="titDep2">개요<span v-if="roleCd==='sysadmin' && mode != 'new'">({{prjtCd}})</span></h2>
	</div>
	<table class="tblForm">
		<caption>프로젝트 생성 테이블</caption>
		<colgroup><col style="width:25%"><col style="width:25%"><col style="width:25%"><col style="width:25%"></colgroup>
		<tbody>
			<tr>
				<th scope="row">회사명<span class="ness">*</span></th>
				<td>
					<div v-if="!isRead" class="frmNowrap">
						<input type="text" v-model.lazy="compNm" id="compNm" />
					</div>
					<div v-else>{{compNm}}</div>
				</td>
				<th scope="row"></th>
				<td>
					
				</td>
			</tr>
			<tr>
				<th scope="row">사업연도 시작일<span class="ness">*</span>
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">대상업무기간이 아닌 회사의 사업연도 입력</div>
					</div>
				</th>
				<td>
					<calendar :is-read="isRead" id="bizFrdt" v-model="bizFrdt" format="yyyy-mm" min-date="2018-01"></calendar>
				</td>
				<th scope="row">사업연도 종료일<span class="ness">*</span></th>
				<td>
					<calendar :is-read="isRead" id="bizTodt" v-model="bizTodt" format="yyyy-mm" :min-date="bizFrdt"></calendar>
				</td>
			</tr>
			<tr>
				<th scope="row">Record Manager</th>
				<td>
					<emp :is-read="isRead" id="rcrdMgr1" v-model="rcrdMgr1"></emp>
				</td>
				<td>
					<emp :is-read="isRead" id="rcrdMgr2" v-model="rcrdMgr2"></emp>
				</td>
				<td>
					<emp :is-read="isRead" id="rcrdMgr3" v-model="rcrdMgr3"></emp>
				</td>
			</tr>
		</tbody>                     
	</table>
	
	
	<div class="titArea" style="margin-top:30px;margin-bottom:15px;">
		<h2 class="titDep2">그룹 정보</h2>
		<div class="btnArea side">
			<button type="button" class="btnPwc btnM" onclick="javascript:window.open('https://sites.google.com/pwc.com/sta')">규정, FAQ</button>
			<a class="btnPwc btnM" href="https://drive.google.com/drive/folders/1FMx6pfnpybbOjXNZJ9UwU3KyrvGXIphx?usp=sharing" target="_blank">Simulation File Download</a>
		</div>
	</div>
	
	
	<table  class="tblForm">
		<caption>표준감사시간 산출 테이블</caption>
		<colgroup><col style="width:25%"><col style="width:25%"><col style="width:25%"><col style="width:25%"></colgroup>
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
					<selectbox :is-read="isRead"  id="indusDv" v-model="indusDv" title="표준산업 분류">
						<c:forEach var="option" items="${selOption.INDUS}">
						<option value="${option.cd}" >${option.nm}</option>
						</c:forEach>
					</selectbox>
				</td>
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
										<td class="tleft">or Yes</td>
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
					<selectbox :is-read="isRead"  id="satgrp" v-model="satgrp" :options="_satgrpList" title="표준 감사 시간 그룹 선택" :disabled="satgrp1ExptYn.cd==='Y'"></selectbox>
				</td>
			</tr>
			<tr>
				<th scope="row">개별 자산<span class="ness">*</span></th>
				<td>
					<div class="frmNowrap">
						<number :is-read="isRead"  id="indivAsset" v-model="indivAsset"></number><span class="unit">원</span>
					</div>
				</td>
				<th scope="row">상장 구분<span class="ness">*</span></th>
				<td class="tdSel">
					<selectbox :is-read="isRead"  id="listDv" v-model="listDv" title="상장 구분 선택">
						<c:forEach var="option" items="${selOption.LISTDV_V2}">
						<option value="${option.cd}" >${option.nm}</option>
						</c:forEach>
					</selectbox>
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
				<td>
					<div class="frmNowrap">
						<number :is-read="isRead"  id="consoAsset" v-model="consoAsset" ></number><span class="unit">원</span>
					</div>
				</td>
				<th scope="row">사업보고서 제출 대상<span class="ness">*</span></th>
				<td class="tdSel">
					<selectbox :is-read="isRead"  id="bizRprtYn" v-model="bizRprtYn" title="사업보고서 제출 대상">
						<c:forEach var="option" items="${selOption.BIZRPRT_YN}">
						<option value="${option.cd}">${option.nm}</option>
						</c:forEach>
					</selectbox>
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
				<td>
					<div class="frmNowrap">
						<number :is-read="isRead"  id="consoSales" v-model="consoSales" ></number><span class="unit">원</span>
					</div>
				</td>
				<th scope="row">그룹1의 예외사항 여부<span class="ness">*</span>
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							 <c:out value="${tooltip.satgrp1Expt}" />
						</div>
					</div>
				</th>
				<td class="tdSel">
					<selectbox :is-read="isRead"  id="satgrp1ExptYn" v-model="satgrp1ExptYn" title="그룹1의 예외사항 여부">
						<c:forEach var="option" items="${selOption.SATGRP1_EXPT_YN}">
						<option value="${option.cd}">${option.nm}</option>
						</c:forEach>
					</selectbox>					
				</td>
				
			</tr>
			<tr>
				<th scope="row">연결 매출채권<span class="ness">*</span>
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							 <c:out value="${tooltip.consoAccntReciev}" />
						</div>
					</div>
				</th>
				<td>
					<div class="frmNowrap">
						<number :is-read="isRead"  id="consoAccntReceiv" v-model="consoAccntReceiv" ></number><span class="unit">원</span>
					</div>
				</td>
				<th scope="row">기업규모
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							 <c:out value="${tooltip.compSize}" />
						</div>
					</div>
				</th>
				<td>
					<div class="frmNowrap">
						<span>{{compSize | formatNumberFilter}}</span><span class="unit">원</span>
					</div>
				</td>
			</tr>
			<tr>
				<th scope="row">연결 재고자산<span class="ness">*</span>
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							 <c:out value="${tooltip.consoInvnt}" /> 
						</div>
					</div>
				</th>
				<td>
					<div class="frmNowrap">
						<number :is-read="isRead"  id="consoInvnt" v-model="consoInvnt" ></number><span class="unit">원</span>
					</div>
				</td>
				<th scope="row"><!-- 보고서 발행 예정일<span class="ness">*</span> --></th>
				<td>
					<%-- <calendar :is-read="isRead" id="rprtScdlDt" v-model="rprtScdlDt"></calendar> --%>
				</td>
			</tr>
			<tr>
				<th scop="row">최초적용 사업연도 종료월<span class="ness">*</span>
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							 <table class="tblH">
							 	<caption>최초적용 사업연도 종료월</caption>
								<colgroup><col style="width:12em"><col style="width:12em"></colgroup>
								<thead>
									<tr>
										<th scope="col">그룹 구분</th>
										<th scope="col">최초적용 사업연도 종료월</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>그룹1</td>
										<td>2019-12</td>
									</tr>
									<tr>
										<td>그룹2</td>
										<td>2019-12</td>
									</tr>
									<tr>
										<td>그룹3</td>
										<td>2019-12</td>
									</tr>
									<tr>
										<td>그룹4</td>
										<td>2019-12</td>
									</tr>
									<tr>
										<td>그룹5</td>
										<td>2019-12</td>
									</tr>
									<tr>
										<td>그룹6</td>
										<td>2019-12</td>
									</tr>
									<tr>
										<td>그룹7</td>
										<td>2019-12</td>
									</tr>
									<tr>
										<td>그룹8</td>
										<td>2019-12</td>
									</tr>
									<tr>
										<td>그룹9</td>
										<td>2020-12</td>
									</tr>
									<tr>
										<td>그룹10</td>
										<td>2021-12</td>
									</tr>
									<tr>
										<td>그룹11</td>
										<td>적용 대상 아님</td>
									</tr>
								</tbody>
							 </table>
						</div>
					</div>
				</th>
				<td>
					<calendar :is-read="isRead" id="firstBizDt" v-model="firstBizDt" format="yyyy-mm" min-date="2019-12"></calendar>
				</td>
				<th scop="row">삼일회계법인 연속감사 횟수<span class="ness">*</span>
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							2년차인 경우에는 전기감사시간에서 초도효과를 적절히 고려
						</div>
					</div>
				</th>
				<td class="tdSel">
					<selectbox :is-read="isRead"  id="contiAdtCnt" v-model="contiAdtCnt" >
						<c:forEach var="option" items="${selOption.CONTI_ADT_CNT}">
							<option value="${option.cd}">${option.nm}</option>
						</c:forEach>
					</selectbox>
				</td>
			</tr>
			<tr>
				<th scop="row">내부회계감사 시행연차
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont tipW02">
							 <table class="tblH">
							 	<caption>내부회계감사 시행연차 설명</caption>
								<colgroup><col style="width:12em"><col style="width:12em"><col style="width:12em"></colgroup>
								<thead>
									<tr>
										<th scope="col">직전사업연도말 자산총액</th>
										<th scope="col">적용시기</th>
										<th scope="col">연결 적용시기</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>2조원 이상</td>
										<td>2019 회계연도부터</td>
										<td>2022 회계연도</td>
									</tr>
									<tr>
										<td>5천억원 이상</td>
										<td>2020 회계연도부터</td>
										<td>2023 회계연도</td>
									</tr>
									<tr>
										<td>1천억원 이상</td>
										<td>2022 회계연도부터</td>
										<td>2024 회계연도</td>
									</tr>
									<tr>
										<td>1천억원 미만</td>
										<td>2023 회계연도부터</td>
										<td>2024 회계연도</td>
									</tr>
								</tbody>
							 </table>
						</div>
					</div>
				</th>
				<td class="tdSel">
					<selectbox :is-read="isRead"  id="intrAdtYcnt" v-model="intrAdtYcnt" :default-text="{isDisplay: true, text: '대상 아님'}">
						<c:forEach var="option" items="${selOption.INTR_ADT_YCNT}">
							<option value="${option.cd}">${option.nm}</option>
						</c:forEach>
					</selectbox>
				</td>
				<th scop="row">검토 횟수
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							회사의 상황과 일치하지 않는 상장예정 및 그룹7(비상장 금융회사) 경우 ‘분기검토조정계수 등 기타’에서 계수조정 확인이 필요하며 코넥스의 경우에는 조정이 필요하지 않습니다.
						</div>
					</div>
				</th>
				<td>
					<div class="frmNowrap">
						{{revwCnt | formatNumberFilter(0)}}<span class="unit">회</span>
					</div>
				</td>
			</tr>
			<tr>
				<th scop="row">동일감사인이 감사하는 종속회사 또는 관계회사 </th>
				<td colspan="3">
					<div class='frmInp'>
						<input id="sameAdtrSbsidYn" v-model="sameAdtrSbsidYn" type='checkbox' value="Y" class='frmInp' :disabled="isRead"><label for="sameAdtrSbsidYn">&nbsp;&nbsp;존재</label>
					</div>
				</td>
			</tr>
		</tbody>
		<tbody v-show="sameAdtrSbsidYn" class="fadein">
			<tr>
				<th scope="row">연결 조정시 제거된 내부거래(매출+자산)
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							 <c:out value="${tooltip.intrTranAmt}" />
						</div>
					</div>
				</th>
				<td>
					<div class="frmNowrap">
						<number :is-read="isRead"  id="intrTranAssetSales" v-model="intrTranAssetSales" ></number><span class="unit">원</span>
					</div>
				</td>
				<th scope="row">내부거래제거전 자회사 자산
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							<c:out value="${tooltip.sbsidAsset}" />
						</div>
					</div>
				</th>
				<td>
					<div class="frmNowrap">
						<number :is-read="isRead"  id="sbsidAssetWithIntrTran" v-model="sbsidAssetWithIntrTran" ></number><span class="unit">원</span>
					</div>
				</td>
			</tr>
			<tr>
				<th scope="row">관계회사의 자산금액
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							 <c:out value="${tooltip.relatCompAsset}" />
						</div>
					</div>
				</th>
				<td>
					<div class="frmNowrap">
						<number :is-read="isRead"  id="relatCompAsset" v-model="relatCompAsset" ></number><span class="unit">원</span>
					</div>
				</td>
				<th scope="row">내부거래제거전 자회사 매출
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							<c:out value="${tooltip.sbsidSales}" />
						</div>
					</div>
				</th>
				<td>
					<div class="frmNowrap">
						<number :is-read="isRead"  id="sbsidSalesWithIntrTran" v-model="sbsidSalesWithIntrTran" ></number><span class="unit">원</span>
					</div>
				</td>
			</tr>
		</tbody>
	</table>
	
	<div class="titArea" style="margin-top:30px;margin-bottom:15px;">
		<h2 class="titDep2">가감계수</h2>
	</div>
	<table  class="tblForm">
		<caption>표준감사시간 산출 테이블</caption>
		<colgroup><col style="width:25%"><col style="width:15%"><col style="width:10%"><col style="width:25%"><col style="width:15%"><col style="width:10%"></colgroup>
		<tbody>
			<tr>
				<th scope="row">IFRS 여부<span class="ness">*</span></th>
				<td class="tdSel fill borderR">
					<selectbox :is-read="isRead"  id="ifrsYn" v-model="ifrsYn" title="IFRS 여부">
						<c:forEach var="option" items="${selOption.IFRS_YN}">
						<option value="${option.cd}">${option.nm}</option>
						</c:forEach>
					</selectbox>
				</td>
				<td class="tright">
					{{ifrsFctr | formatNumberFilter(2)}}
				</td>
				<th scope="row">지주사 분류<span class="ness">*</span>
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">표준산업분류에 따라 금융/ 비금융 지주를 판단하되 실질에따라 선택이 가능함.</div>
					</div>
				</th>
				<td class="tdSel fill borderR">
					<selectbox :is-read="isRead"  id="holdingsDv" v-model="holdingsDv" title="지주사 분류 선택">
						<c:forEach var="option" items="${selOption.HOLDINGS_DV}">
						<option value="${option.cd}">${option.nm}</option>
						</c:forEach>
					</selectbox>
				</td>
				<td class="tright">
					{{holdingsFctr | formatNumberFilter(2)}}
				</td>
			</tr>
			<tr>
				<th scope="row">초도 감사 여부<span class="ness">*</span></th>
				<td class="tdSel fill borderR">
					<selectbox :is-read="isRead"  id="firstAdtYn" v-model="firstAdtYn" title="초도감사 여부">
						<c:forEach var="option" items="${selOption.FIRST_YN}">
						<option value="${option.cd}">${option.nm}</option>
						</c:forEach>
					</selectbox>
				</td>
				<td>
					<div class="frmNowrap fill">
						<number :is-read="isRead"  	   id="firstAdtFctr"
									   v-model="firstAdtFctr" 
									   v-show="satgrp.cd == 'SATGRP01' && firstAdtYn.cd == 'Y'" 
									   decimal="2"
									   :min="firstAdtFctrRange?firstAdtFctrRange.min:null" 
									   :max="firstAdtFctrRange?firstAdtFctrRange.max:null"  
									   :placeholder="firstAdtFctrRange?firstAdtFctrRange.description:null"
									   ></number>
									   
						<span v-show="!(satgrp.cd == 'SATGRP01' && firstAdtYn.cd == 'Y')" >{{firstAdtFctr | formatNumberFilter(2)}}</span>
					</div>
				</td>
				<th scope="row">미국 상장 여부<span class="ness">*</span></th>
				<td class="tdSel fill borderR"><!-- 2019-03-07 수정 -->
					<selectbox :is-read="isRead"  id="usaListYn" v-model="usaListYn" title="미국 상장 여부">
						<c:forEach var="option" items="${selOption.USALIST_YN}">
						<option value="${option.cd}">${option.nm}</option>
						</c:forEach>
					</selectbox>
				</td>
				<td class="tright">
					{{usaListFctr | formatNumberFilter(2)}}
				</td>
			</tr>
			<tr>
				<th scope="row">위험계정비중</th>
				<td class="tright borderR">
					{{riskBase | formatNumberFilter(4)}}
				</td>
				<td class="tright">
					{{riskFctr | formatNumberFilter(2)}}
				</td>
				<th scope="row">핵심 감사 사항
					<div class="tipArea tipLeft">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont tipW02">
							 <table class="tblH">
							 	<caption>핵심 감사 사항 설명</caption>
								<colgroup><col style="width:25em"><col style="width:15em"></colgroup>
								<thead>
									<tr>
										<th scope="col">적용 대상</th>
										<th scope="col">시행일(시행일 이후 종료하는 보고기간의 재무제표부터 적용)</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td class="tleft">자산 2조이상 유가증권시장 및 코스닥 상장기업</td>
										<td>2018년 12월 15일</td>
									</tr>
									<tr>
										<td class="tleft">자산 1천억이상 2조미만 유가증권시장 및 코스닥 상장기업</td>
										<td>2019년 12월 15일</td>
									</tr>
									<tr>
										<td class="tleft">자산 1천억미만 유가증권시장 및 코스닥 상장기업</td>
										<td>2020년 12월 15일</td>
									</tr>
								</tbody>
							 </table>
						</div>
					</div>
				</th>
				<td class="tdSel fill borderR"><!-- 2019-03-07 수정 -->
					<selectbox :is-read="isRead"  id="kamYn" v-model="kamYn" title="KAM 여부">
						<c:forEach var="option" items="${selOption.KAM_YN}">
						<option value="${option.cd}">${option.nm}</option>
						</c:forEach>
					</selectbox>
				</td>
				<td class="tright">
					{{kamFctr | formatNumberFilter(2)}}
				</td>
			</tr>
			<tr>
				<th scope="row">연결 작성 여부<span class="ness">*</span></th>
				<td class="tdSel fill borderR"><!-- 2019-03-07 수정 -->
					<selectbox :is-read="isRead"  id="consoFinstatYn" v-model="consoFinstatYn" title="연결 작성 여부">
						<c:forEach var="option" items="${selOption.CONSO_YN}">
						<option value="${option.cd}">${option.nm}</option>
						</c:forEach>
					</selectbox>
				</td>
				<td class="tright">
					{{consoFinstatFctr | formatNumberFilter(2)}}
				</td>
				<th scope="row">자회사 수<span class="ness">*</span>
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							 <c:out value="${tooltip.sbsidCnt}" />
						</div>
					</div>
				</th>
				<td class="borderR">
					<div class="frmNowrap">
						<number v-show="consoFinstatYn.cd === 'Y'" :is-read="isRead"  id="sbsidCnt" v-model="sbsidCnt" max="380"></number>
						<span v-show="consoFinstatYn.cd !== 'Y'">{{sbsidCnt}}</span>
						<span class="unit">개</span>
					</div>
				</td>
				<td class="tright">
					{{sbsidCntFctr | formatNumberFilter(2)}}
				</td>
			</tr>
			<tr>
				<th scope="row">당기 예상 감사의견 비적정 여부<span class="ness">*</span></th>
				<td class="tdSel fill borderR">
					<selectbox :is-read="isRead"  id="currAdtopin" v-model="currAdtopin" title="당기 예상 감사의견 비적정 여부">
						<c:forEach var="option" items="${selOption.ADTOPIN_YN}">
						<option value="${option.cd}">${option.nm}</option>
						</c:forEach>
					</selectbox>
				</td>
				<td class="tright">
					{{currAdtopinFctr | formatNumberFilter(2)}}
				</td>
				<th scope="row">당기 연결 당기순손실 예상 여부<span class="ness">*</span></th>
				<td class="tdSel fill borderR">
					<selectbox :is-read="isRead"  id="currConsoLossYn" v-model="currConsoLossYn" title="당기 연결 당기순손실 예상 여부">
						<c:forEach var="option" items="${selOption.LOSS_YN}">
						<option value="${option.cd}">${option.nm}</option>
						</c:forEach>
					</selectbox>
				</td>
				<td class="tright">
					{{currConsoLossFctr | formatNumberFilter(2)}}
				</td>
			</tr>
			
			<tr>
				<th scope="row">전기 의견변형 여부<span class="ness">*</span>
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont tipW02">
							<p>당기순손실 또는 비적정의견 가산율 이내에서 적절히 가산하여 적용할 수 있음</p>
							<table class="tblH">
							 	<caption></caption>
								<colgroup><col style="width:8em"><col style="width:8em"><col style="width:8em"></colgroup>
								<thead>
									<tr>
										<th scope="col">구분</th>
										<th scope="col">전기 순손실</th>
										<th scope="col">전기의견 변형계수</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>그룹1</td>
										<td>1 ~ 1.3</td>
										<td>1 ~ 1.45</td>
									</tr>
									<tr>
										<td>그룹2</td>
										<td>1 ~ 1.2</td>
										<td>1 ~ 1.45</td>
									</tr>
									<tr>
										<td>그룹3</td>
										<td>1 ~ 1.05</td>
										<td>1 ~ 1.45</td>
									</tr>
									<tr>
										<td>그룹4</td>
										<td>1 ~ 1.15</td>
										<td>1 ~ 1.45</td>
									</tr>
									<tr>
										<td>그룹5</td>
										<td>1 ~ 1.1</td>
										<td>1 ~ 1.45</td>
									</tr>
									<tr>
										<td>그룹6</td>
										<td>1 ~ 1.3</td>
										<td>1 ~ 1.45</td>
									</tr>
									<tr>
										<td>그룹7</td>
										<td>1 ~ 1.2</td>
										<td>1 ~ 1.45</td>
									</tr>
									<tr>
										<td>그룹8</td>
										<td>1 ~ 1.3</td>
										<td>1 ~ 1.45</td>
									</tr>
									<tr>
										<td>그룹9</td>
										<td>1 ~ 1.3</td>
										<td>1 ~ 1.45</td>
									</tr>
									<tr>
										<td>그룹10</td>
										<td>1 ~ 1.05</td>
										<td>1 ~ 1.45</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</th>
				<td class="tdSel fill borderR">
					<selectbox :is-read="isRead"  id="priorAdtopinChgYn" v-model="priorAdtopinChgYn" title="전기 의견변형 여부">
						<c:forEach var="option" items="${selOption.PRIOR_ADTOPIN_CHG_YN}">
						<option value="${option.cd}">${option.nm}</option>
						</c:forEach>
					</selectbox>
				</td>
				<td>
					<div class="frmNowrap fill">
						<number :is-read="isRead"  	   id="priorAdtopinChgFctr"
									   v-model="priorAdtopinChgFctr" 
									   v-show="priorAdtopinChgYn.cd == 'Y'" 
									   decimal="2"
									   :min="priorAdtopinChgFctrRange?priorAdtopinChgFctrRange.min:null" 
									   :max="priorAdtopinChgFctrRange?priorAdtopinChgFctrRange.max:null"  
									   :placeholder="priorAdtopinChgFctrRange?priorAdtopinChgFctrRange.description:null"
									   ></number>
									   
						<span v-show="!(priorAdtopinChgYn.cd == 'Y')" >{{priorAdtopinChgFctr | formatNumberFilter(2)}}</span>
					</div>
				</td>
				<th scope="row">전기손실여부<span class="ness">*</span>
					<div class="tipArea tipLeft">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont tipW02">
							<p>당기순손실 또는 비적정의견 가산율 이내에서 적절히 가산하여 적용할 수 있음</p>
							<table class="tblH">
							 	<caption></caption>
								<colgroup><col style="width:8em"><col style="width:8em"><col style="width:8em"></colgroup>
								<thead>
									<tr>
										<th scope="col">구분</th>
										<th scope="col">전기 순손실</th>
										<th scope="col">전기의견 변형계수</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>그룹1</td>
										<td>1 ~ 1.3</td>
										<td>1 ~ 1.45</td>
									</tr>
									<tr>
										<td>그룹2</td>
										<td>1 ~ 1.2</td>
										<td>1 ~ 1.45</td>
									</tr>
									<tr>
										<td>그룹3</td>
										<td>1 ~ 1.05</td>
										<td>1 ~ 1.45</td>
									</tr>
									<tr>
										<td>그룹4</td>
										<td>1 ~ 1.15</td>
										<td>1 ~ 1.45</td>
									</tr>
									<tr>
										<td>그룹5</td>
										<td>1 ~ 1.1</td>
										<td>1 ~ 1.45</td>
									</tr>
									<tr>
										<td>그룹6</td>
										<td>1 ~ 1.3</td>
										<td>1 ~ 1.45</td>
									</tr>
									<tr>
										<td>그룹7</td>
										<td>1 ~ 1.2</td>
										<td>1 ~ 1.45</td>
									</tr>
									<tr>
										<td>그룹8</td>
										<td>1 ~ 1.3</td>
										<td>1 ~ 1.45</td>
									</tr>
									<tr>
										<td>그룹9</td>
										<td>1 ~ 1.3</td>
										<td>1 ~ 1.45</td>
									</tr>
									<tr>
										<td>그룹10</td>
										<td>1 ~ 1.05</td>
										<td>1 ~ 1.45</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</th>
				<td class="tdSel fill borderR">
					<selectbox :is-read="isRead"  id="priorLossYn" v-model="priorLossYn" title="전기손실여부">
						<c:forEach var="option" items="${selOption.PRIOR_LOSS_YN}">
						<option value="${option.cd}">${option.nm}</option>
						</c:forEach>
					</selectbox>
				</td>
				<td>
					<div class="frmNowrap fill">
						<number :is-read="isRead"  	   id="priorLossFctr"
									   v-show="priorLossYn.cd == 'Y'" 
									   v-model="priorLossFctr" 
									   decimal="2"
									   :min="priorLossFctrRange?priorLossFctrRange.min:null" 
									   :max="priorLossFctrRange?priorLossFctrRange.max:null"  
									   :placeholder="priorLossFctrRange?priorLossFctrRange.description:null"
									   ></number>
									   
						<span v-show="!(priorLossYn.cd == 'Y')" >{{priorLossFctr | formatNumberFilter(2)}}</span>
					</div>
				</td>
			</tr>
			
			<tr>
				<th scope="row">분기검토조정계수 등 기타<span class="ness">*</span>
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont tipW02">
							<p>일반적인 회사의 경우 분기검토계수를 조정하실 필요가 없으나<br>
단, 상장예정, 그룹7(비상장금융회사)의 경우만 분기검토조정계수를 확인하시기 바랍니다.</p>
							<table class="tblH">
							 	<caption></caption>
								<colgroup><col style="width:4em"><col style="width:8em"><col style="width:8em"></colgroup>
								<thead>
									<tr>
										<th scope="col">그룹</th>
										<th scope="col">조건</th>
										<th scope="col">분기검토조정계수 등 기타</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>그룹1</td>
										<td class="tleft">상장예정</td>
										<td class="tleft">0.451 ~ 0.414</td>
									</tr>
									<tr>
										<td>그룹2</td>
										<td class="tleft">상장예정</td>
										<td class="tleft">0.451 ~ 0.414</td>
									</tr>
									<tr>
										<td>그룹3</td>
										<td class="tleft">상장예정</td>
										<td class="tleft">0.451 ~ 0.414</td>
									</tr>
									<tr>
										<td>그룹4</td>
										<td class="tleft">상장예정</td>
										<td class="tleft">0.714 ~ 0.741</td>
									</tr>
									<tr>
										<td>그룹5</td>
										<td class="tleft">상장예정</td>
										<td class="tleft">0.714</td>
									</tr>
									<tr>
										<td>그룹6</td>
										<td class="tleft">상장예정</td>
										<td class="tleft">0.741</td>
									</tr>
									<tr>
										<td style="vertical-align:top">그룹7</td>
										<td class="tleft">비상장<br/>금융회사(분기검토대상)</td>
										<td class="tleft">1.7 ~ 1.8(권고)</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</th>
				<td></td>
				<td class="tright">
					<div class="frmNowrap fill">
						<number :is-read="isRead"  id="etcFctr" v-model="etcFctr"  decimal="3" min="0.001"></number>
					</div>
				</td>
				<th scope="row">가감요인계</th>
				<td class="tright" colspan="2">
					{{factorVal | formatNumberFilter(2)}}
				</td>
			</tr>
		</tbody>
	</table>			
	
	
	
	<div class="titArea" style="margin-top:30px;margin-bottom:15px;">
		<h2 class="titDep2">상하한(기준숙련도 기준)</h2>
	</div>
	<table  class="tblForm">
		<caption>상하한(기준숙련도 기준)</caption>
		<colgroup><col style="width:5%"><col style="width:20%"><col style="width:25%"><col style="width:25%"><col style="width:25%"></colgroup>
		<tbody>
		 	<tr>
		 		<th colspan="2">상하한 적용 여부<span class="ness">*</span></th>
		 		<td>
		 			<selectbox :is-read="isRead"  id="minMaxYn" v-model="minMaxYn" title="상하한 적용 여부">
						<c:forEach var="option" items="${selOption.MIN_MAX_YN}">
						<option value="${option.cd}">${option.nm}</option>
						</c:forEach>
					</selectbox>
		 		</td>
		 		<th><span v-show="minMaxYn.cd === 'N'">미적용시 근거<span class="ness">*</span></span></th>
		 		<td>
		 			<selectbox :is-read="isRead"  v-show="minMaxYn.cd === 'N'" id="minMaxReason" v-model="minMaxReason" title="상하한 미적용 근거">
						<c:forEach var="option" items="${selOption.MIN_MAX_REASON}">
						<option value="${option.cd}">${option.nm}</option>
						</c:forEach>
					</selectbox>
		 		</td>
		 	</tr>
		 	<tr v-show="minMaxYn.cd === 'N' && minMaxReason.cd === '99'"  class="fadein">
		 		<th colspan="2">미적용 근거 서술<span class="ness">*</span></th>
		 		<td colspan="3">
		 			<div v-if="!isRead" class="frmNowrap fill"><input type="text" v-model="minMaxReasonDscrt"></div>
		 			<div v-else>{{minMaxReasonDscrt}}</div>
		 		</td>
		 	</tr>
		</tbody>
		<tbody v-show="minMaxYn.cd === 'Y'" class="fadein">
			<tr>
		 		<th rowspan="3" class="borderR">최초적용 상한</th>
		 		<th>최초적용 직전 사업연도</th>
		 		<td>{{fstBizDt}}</td>
		 		<th>3년간 상한({{fstBizDt3year}}까지)
		 			<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							 표준감사시간은 3년간 최초 적용 직전 사업연도 감사시간 대비 200%를 한도로 함
						</div>
					</div>
		 		</th>
		 		<td><div class="frmNowrap">{{fstMaxTm | formatNumberFilter}}<span class="unit">hr</span></div></td>
		 	</tr>
		 	<tr>
		 		<th>{{fstBizDt}} 감사시간<span class="ness">*</span></th>
		 		<td>
		 			<div class="frmNowrap">
						<number :is-read="isRead"  id="fstAdtTm" v-model="fstAdtTm" min="0"></number><span class="unit">hr</span>
					</div>
		 		</td>
		 		
		 		<th>{{fstBizDt}} 감사팀의 숙련도<span class="ness">*</span><br>(
					<div class='frmInp'>
						<input id="fstAdtrBaseWkmnspYn" v-model="fstAdtrBaseWkmnspYn" type='checkbox' value="Y" class='frmInp'  :disabled="isRead">
						<label for="fstAdtrBaseWkmnspYn">&nbsp;기준숙련도 적용</label>
					</div>
				)</th>
		 		<td>
		 			<div class="frmNowrap">
		 				<number :is-read="isRead"  v-if="!fstAdtrBaseWkmnspYn" id="fstAdtrWkmnsp" v-model="fstAdtrWkmnsp" decimal="3" ></number>
		 				<button v-if="!fstAdtrBaseWkmnspYn && !isRead" type="button" class="btnCalc gapL" @click="onSimpleCalc('fstAdtrWkmnsp', baseWkmnsp, '[표준 감사 시간 그룹 정보]를 먼저 선택하세요.')">계산기</button>
		 				<span v-if="fstAdtrBaseWkmnspYn">{{fstAdtrWkmnsp | formatNumberFilter(3)}}</span>
					</div>
		 		</td>
		 	</tr>
		 	<tr>
		 		<th>{{fstBizDt}} 감사시간 근거<span class="ness">*</span></th>
		 		<td v-if="!isRead" class="tdSel">
		 			<selectbox id="fstAdtTmReason" v-model="fstAdtTmReason" title="최초적용 직전 사업연도 감사시간 근거">
						<c:forEach var="option" items="${selOption.FST_ADT_TM_REASON}">
						<option value="${option.cd}">${option.nm}</option>
						</c:forEach>
					</selectbox>
					<div v-show="fstAdtTmReason.cd == '99'" class="frmNowrap fill" style="margin-top:5px">
						<input type="text" v-model="fstAdtTmReasonDscrt" placeholder="사유를 입력하세요." />
					</div>
		 		</td>
		 		<td v-else>
		 			<span>{{fstAdtTmReason.cd=='99'?fstAdtTmReasonDscrt:fstAdtTmReason.nm}}</span>
		 		</td>
		 		<th>{{fstBizDt}} 숙련도 근거<span class="ness">*</span></th>
		 		<td v-if="!isRead" class="tdSel">
		 			<selectbox id="fstWkmnspReason" v-model="fstWkmnspReason" title="숙련도 근거">
						<c:forEach var="option" items="${selOption.FST_WKMNSP_REASON}">
						<option value="${option.cd}">${option.nm}</option>
						</c:forEach>
					</selectbox>
					<div v-show="fstWkmnspReason.cd == '99'" class="frmNowrap fill" style="margin-top:5px">
						<input type="text" v-model="fstWkmnspReasonDscrt" placeholder="사유를 입력하세요." />
					</div>
		 		</td>
		 		<td v-else>
		 			<span>{{fstWkmnspReason.cd=='99'?fstWkmnspReasonDscrt:fstWkmnspReason.nm}}</span>
		 		</td>
		 	</tr>
		 	<tr>
		 		<th rowspan="3" class="borderR">전기기준 상하한</th>
		 		<th>{{priorBizDt}} 감사인<span class="ness">*</span></th>
		 		<td>
		 			<div v-if="!isRead" class="frmNowrap">
						<input type="text" v-model="priorAdtr.nm" class="frmW01"  readonly>
						<button @click.prevent="onDeleteAdtr" type="button" class="inpDel">삭제</button>
						<button type="button" class="btIco btnSearch gapL" @click.prevent="onPopupAdtr">검색</button>
					</div>
					<div v-else>{{priorAdtr.nm}}</div>
		 		</td>
		 		<th>{{priorBizDt}} 그룹 기준 숙련도<span class="ness">*</span></th>
		 		<td>
		 			<div class="frmNowrap">
		 			 	<number :is-read="isRead"  id="priorBaseWkmnsp" v-model="priorBaseWkmnsp" decimal="3" ></number>
		 			 </div>
		 		</td>
		 	</tr>
		 	<tr>
		 		<th scope="row">{{priorBizDt}} 감사 시간<span class="ness">*</span>
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							 <c:out value="${tooltip.priorAdtTm}" />
						</div>
					</div>
				</th>
		 		<td>
		 			<div class="frmNowrap">
		 				<number :is-read="isRead"  id="priorAdtTm" v-model="priorAdtTm" min="0"></number><span class="unit">hr</span>
		 			</div>
		 		</td>
		 		<th>{{priorBizDt}} 감사팀의 숙련도<span class="ness">*</span><br>(
					<div class='frmInp'>
						<input id="priorAdtrBaseWkmnspYn" v-model="priorAdtrBaseWkmnspYn" type='checkbox' value="Y" class='frmInp'  :disabled="isRead">
						<label for="priorAdtrBaseWkmnspYn">&nbsp;기준숙련도 적용</label>
					</div>)
				</th>
		 		<td>
		 			<div class="frmNowrap">
		 				<number :is-read="isRead"  v-if="!priorAdtrBaseWkmnspYn" id="priorAdtrWkmnsp" v-model="priorAdtrWkmnsp" decimal="3" ></number>
		 				<button v-if="!priorAdtrBaseWkmnspYn && !isRead" type="button" class="btnCalc gapL" @click="onSimpleCalc('priorAdtrWkmnsp', priorBaseWkmnsp, '[' + priorBizDt + ' 그룹 기준 숙련도]를 입력하세요')">계산기</button>
		 				<span v-if="priorAdtrBaseWkmnspYn">{{priorAdtrWkmnsp | formatNumberFilter(3)}}</span>
					</div>
		 		</td>
		 	</tr>
		 	<tr>
		 		<th>{{priorBizDt}} 감사시간 근거<span class="ness">*</span></th>
		 		<td v-if="!isRead" class="tdSel">
		 			<selectbox id="priorAdtTmReason" v-model="priorAdtTmReason" title="전기 감사시간 근거">
						<c:forEach var="option" items="${selOption.PRIOR_ADT_TM_REASON}">
						<option value="${option.cd}">${option.nm}</option>
						</c:forEach>
					</selectbox>
					<div v-show="priorAdtTmReason.cd == '99'" class="frmNowrap fill" style="margin-top:5px">
						<input type="text" v-model="priorAdtTmReasonDscrt" placeholder="사유를 입력하세요." />
					</div>
		 		</td>
		 		<td v-else>
		 			<span>{{priorAdtTmReason.cd=='99'?priorAdtTmReasonDscrt:priorAdtTmReason.nm}}</span>
		 		</td>
		 		<th>{{priorBizDt}} 숙련도 근거<span class="ness">*</span></th>
		 		<td v-if="!isRead" class="tdSel">
		 			<selectbox :is-read="isRead"  id="priorWkmnspReason" v-model="priorWkmnspReason" title="숙련도 근거">
						<c:forEach var="option" items="${selOption.PRIOR_WKMNSP_REASON}">
						<option value="${option.cd}">${option.nm}</option>
						</c:forEach>
					</selectbox>
					<div v-show="priorWkmnspReason.cd == '99'" class="frmNowrap fill" style="margin-top:5px">
						<input type="text" v-model="priorWkmnspReasonDscrt" placeholder="사유를 입력하세요." />
					</div>
		 		</td>
		 		<td v-else>
		 			<span>{{priorWkmnspReason.cd=='99'?priorWkmnspReasonDscrt:priorWkmnspReason.nm}}</span>
		 		</td>
		 	</tr>
		 	
		 	<tr>
		 		<th scope="row" class="borderR">내부회계<br>감사</th>
		 		<th scope="row">{{priorBizDt}} 내부회계감사시간포함여부<span class="ness">*</span></th>
				<td class="tdSel">
					<selectbox :is-read="isRead"  id="intrAdtYn" v-model="intrAdtYn" title="전기 내부회계감사시간포함여부">
						<c:forEach var="option" items="${selOption.INTR_ADT_YN}">
						<option value="${option.cd}">${option.nm}</option>
						</c:forEach>
					</selectbox>
				</td>
				<th scope="row">{{priorBizDt}} 내부회계감사시간</th>
				<td>
					<div class="frmNowrap">
						<b class="colPoint01">{{ intrAdtTm | formatNumberFilter(2) }}</b><span class="unit">hr</span>
					</div>
				</td>
		 	</tr>
		 	<tr>
		 		<th class="borderR">상하한</th>
		 		<th>하한</th>
		 		<td><div class="frmNowrap">{{minTm | formatNumberFilter(2)}}<span class="unit">hr</span></div></td>
		 		<th>상한</th>
		 		<td><div class="frmNowrap">{{maxTm | formatNumberFilter(2)}}<span class="unit">hr</span></div></td>
		 	</tr>
		</tbody>
	</table>			
	
	
	<div class="titArea" style="margin-top: 30px; margin-bottom: 15px;">
		<h2 class="titDep2">표준감사시간 산출</h2>
		<div class="btnArea side">
			<div class="tipArea" v-if="!isRead">
				<button type="button" class="btnPwc btnS action" @click="onCalcSat(true)">표준 감사시간 계산하기</button>
				<div class="tipCont">
					<c:out value="${tooltip.btnCalcSat}" />
				</div>
			</div>
		</div>
	</div>
	
	<table  class="tblForm">
		<caption>한공회 표준감사시간(기준숙련도 가정)</caption>
		<colgroup><col style="width:25%"><col style="width:25%"><col style="width:25%"><col style="width:25%"></colgroup>
		<tbody>
			<tr>
				<th scop="row">표준감사시간표(보간법) (가)</th>
				<td>
					<div class="frmNowrap">{{intplSat | formatNumberFilter(2)}}<span class="unit">hr</span></div>				
				</td>
				<th scope="row">가감률 (나)</th>
				<td>
					<div class="frmNowrap">{{factorVal | formatNumberFilter(2)}}</div>
				</td>
			</tr>
			<tr>
				<th scop="row">산식 결과 (다 = 가 * 나)</th>
				<td>
					<div class="frmNowrap">{{calResult | formatNumberFilter(2)}}<span class="unit">hr</span></div>				
				</td>
				<th scop="row">적용요율 (라)</th>
				<td>
					<div class="frmNowrap">{{yearRate * 100}}<span class="unit">%</span></div>				
				</td>
			</tr>	
			<tr>
				<th scop="row">산식결과 * 적용요율 (다 * 라)</th>
				<td>
					<div class="frmNowrap">{{calRateSat | formatNumberFilter(2)}}<span class="unit">hr</span></div>				
				</td>
				<th scop="row">산출된 감사시간(상하한 적용 후) (a)
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							<c:out value="${tooltip.tmpSat}" />
						</div>
					</div>
				</th>
				<td>
					<div class="frmNowrap">
						{{calAdtTm | formatNumberFilter(2)}}<span class="unit">hr</span>
					</div>
				</td>
			</tr>
			<tr>
				<th scop="row">내부회계감사시간 계수 (b)</th>
				<td>
					<div class="frmNowrap">{{intrAdtFctr | formatNumberFilter(3)}}</div>				
				</td>
				<th scope="row">숙련도 반영전 표준감사시간 (a * b)
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							<c:out value="${tooltip.calSat}" />
						</div>
					</div>
				</th>
				<td>
					<div class="frmNowrap">
						<b class="colPoint01">{{calSat | formatNumberFilter(2)}}</b><span class="unit">hr</span>
					</div>
				</td>
			</tr>
			<tr>
				<th scope="row">기준 숙련도
					<div class="tipArea">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont">
							<c:out value="${tooltip.baseWkmnsp}" /> 
						</div>
					</div>
				</th>
				<td>
					<div class="frmNowrap">
						{{baseWkmnsp | formatNumberFilter(3)}}
					</div>
				</td>
				<th scope="row">한공회 표준감사시간<span class="ness">*</span><br>(기본산식 외 요소를 고려한 최종 표준감사시간)
					<div class="tipArea tipTopLeft">
						<button type="button" class="btnTip">도움말</button>
						<div class="tipCont tipW02">
							<p>일반적으로 숙련도 반영전 표준감사시간과 동일합니다. 다만, 다음과 같은  상황의 경우 기본산식 외에 ET의 판단하에 적절한 가감 후 표준감사시간을 입력하여야 합니다.</p>
							<p></p>
							<ul>
								<li>보간법이 아닌 가장근접한 위험계정비중에 해당하는 가산율을 적용하려는 경우</li>
								<li>표준감사시간표에 제시되지 않은 기업규모를 보간법이 아닌 다른 가정으로 사용하려는 경우(FAQ 참조)</li>
								<li>감사인이 회계감사기준을 충실히 준수하고 적정한 감사품질을 유지하기 위해 투입해야 하는 감사시간과 괴리가 있다고 판단되는 경우 (한공회통보필요)</li>
							</ul>
						</div>
					</div>
				</th>
				<td>
					<div class="frmNowrap">
						<number :is-read="isRead"  id="etDfnSat" v-model="etDfnSat" decimal="2" min="0"></number><span class="unit">hr</span>
					</div>
				</td>
			</tr>
		</tbody>
	</table>
	
	<div class="titArea" style="margin-top:30px;margin-bottom:15px;">
		<h2 class="titDep2">비고</h2>
		<div class="tipArea">
			<button type="button" class="btnTip">도움말</button>
			<div class="tipCont">합의숙련도, 재무정보의 기준일 등 계산식에서 예외적인 사항을 적용한 경우 관련내용 기재</div>
		</div>
	</div>
	<div style="border-top:1px solid #464646">
		<textarea rows="10" v-model="remark" :style="{width: '100%', backgroundColor: mode === 'read' ? '#f7f7f7' : 'white', cursor:'text !important'}" :readonly="mode === 'read'"></textarea>
		<div v-if="['edit', 'new'].includes(mode)" style="text-align:right"><span :style="{color: curRemarkCnt>maxRemarkCnt ? 'red' : 'inherit' }">{{curRemarkCnt}}</span> / {{maxRemarkCnt}}자</div>
	</div>
</div><!-- //boxWhite -->


<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/v2/ProjectInfo.js?version=<fmt:formatDate value="${now}" pattern="yyyyMMddHHmmss" />"></script>
<script>
// Default Data


// 프로젝트 코드가 있을 경우, Data를 Load 후 Edit 모드로 열기
// 없으면 New 모드로 열기
function fetchData(){
	const $deferred = $.Deferred();
	if('${prjtCd}'){
		Helper.post('/calcSat/info', {prjtCd: '${prjtCd}', isDraft: "${isDraft}".bool})
		.done(function(prjtInfo){
			$deferred.resolve(prjtInfo);
		})
	}else{
		$deferred.resolve({});
	}
	return $deferred.promise();
}

// Vue Instance 생성
fetchData().done(function(prjtInfo){
	let oData = Object.assign({
		mode: '${mode}',
		isDraft: "${isDraft}".bool,
		canEdit: false,
		canDelete: false,
		prflId: <c:out value="${profileId}"/>,		// 프로파일 버전 
		newStfWkmnsp: <c:out value="${wkmnsp.newStfWkmnsp}"/>,	// New Staff 기준 숙련도
		otherWkmnsp: <c:out value="${wkmnsp.otherWkmnsp}"/>,	// Others 기준 숙련도
		roleCd: '${roleCd}',
		remark: '',
		maxRemarkCnt: 500,
	}, prjtInfo)
	
	
	let computedFields = ['compSize', 'factorVal', 'fstBizDt', 'fstMaxTm', 'wkmnspSat', 'minTm', 'maxTm', 'totCntrtFee', 'totPrjtBdgt', 'calSat']
	for(let i=0; i<computedFields.length; i++) delete oData[computedFields[i]];
	
	//console.log(oData)
	// 사용 데이터
	let data = Object.assign({}, oData) // 사용 데이터
	
	new Vue({
		el: '#calSatEdit',
		data: data,
		mixins: [
			commonMixin, 		// 공통 정보
			satCalcInfoMixin,	// 표준감사시간 계산 전용 헤더 정보
			groupInfoMixin,		// 그룹정보 
			factorMixin, 		// 가감계수
			minMaxMixin, 		// 상하한(기준숙련도 기준)
			satCalculateMixin,	// 표준감사시간 산출
			validationMixin		// 유효성 검사를 위함
		],
		mounted: function(){
			$("#calSatEdit").show();
			
		},
		computed:{
			isRead: function(){return this.mode == "read"},
			_satgrpList: function(){
				if(this.satgrpList){
					return this.satgrpList.map(function(item){
						return {cd:item.satgrpCd, nm:item.satgrpNm}	
					})	
				}else{
					return []
				}
			},
			curRemarkCnt: function(){
				return this.remark == null ? 0 : this.remark.length;
			}
		},
		methods:{
			onSave: function(e){
				let thisObj = this;
				let msg = this.validCheckCalcOnly();
				
				if(msg && msg.length > 0){
					Helper.MessageBox(msg)
				}else{
					// 표준감사시간 재계산 하기
					thisObj.onCalcSat(false).done(function(){
						let param = thisObj.getParam();
						if(thisObj.isDraft) {
							param.stat = 'RG';
						}else{
							param.stat = param.stat||'RG';
						}
						
						if(thisObj.mode === "new"){
							Helper.post("/calcSat/add", param)
							.done(function(prjtCd){
								Helper.MessageBox("저장 완료 되었습니다.")
								if(!thisObj.prjtCd) thisObj.prjtCd = prjtCd
								thisObj.mode = 'read';
							});	
						}else if(thisObj.mode === "edit"){
							Helper.post("/calcSat/update", param)
							.done(function(){
								Helper.MessageBox("저장 완료 되었습니다.")
								thisObj.mode = 'read';
							});
						}	
					})
				}
			},
			onSaveAsDraft: function(){
				let thisObj = this;
				if(thisObj.compNm === null || thisObj.compNm === ''){
					Helper.MessageBox(["최소한 회사명은 입력해야 합니다."]);
					return;
				}
				let param = thisObj.getParam();
				param.stat = 'DR';
				
				if(thisObj.mode === "new"){
					Helper.post("/calcSat/add", param)
					.done(function(prjtCd){
						Helper.MessageBox("저장 완료 되었습니다.")
						if(!thisObj.prjtCd) thisObj.prjtCd = prjtCd
						thisObj.mode = 'read';
					});	
				}else if(thisObj.mode === "edit"){
					Helper.post("/calcSat/update", param)
					.done(function(){
						Helper.MessageBox("저장 완료 되었습니다.")
						thisObj.mode = 'read';
					});
				}
			},
			onCancel: function(){
				if(this.mode == "edit"){
					if(this.isDraft){
						Helper.goPage("/pages/satcalc/v2/draftlist");	
					}else{
						Object.assign(data, oData);
						this.mode = 'read';	
					}
				}else{
					Helper.goPage("/pages/satcalc/v2/list");
				}
			},
			onEdit: function(){
				this.mode = "edit"
			},
			onDelete: function(){
				let thisObj = this;
				if(this.mode === 'read'){
					Helper.ConfirmBox('삭제 하시겠습니까?', function(flag){
						if(flag){							
							Helper.post("/calcSat/delete", {prjtCd: thisObj.prjtCd})
							.done(function(){
								Helper.MessageBox("삭제 되었습니다.", function(){						
									Helper.goPage("/pages/satcalc/v2/list")
								})
							})	
						}
					})
					
				}
			},
			onCopy: function(){
				let thisObj = this;
				Helper.ConfirmBox("복사하시겠습니까?", function(flag){
					if(flag){
						thisObj.prjtCd = '';
						thisObj.mode = 'new';
						thisObj.compNm = thisObj.compNm + ' - 복사본' 
					}
				})
			},
			refreshFctr: function(id){
				if((this.satgrp.cd === null || this.satgrp.cd === '') && this.onMsg){
					Helper.MessageBox("표준 감사 시간 그룹을 선택하세요");
				}
				this.refresh(id);
			},
		},
		watch:{
			bizFrdt: function(){
				this.loadSatgrpList();
				this.refresh('bizTodt');
			},
			listDv: function(value){
				if(value.cd === 'TOBE_LISTED' || value.cd === 'KONEX') {
					Helper.MessageBox("상장예정, 그룹7(금융)의 경우 분기검토조정계수를 확인하시기 바라며, 코넥스의 경우 검토로 인한 조정은 필요하지 않습니다.")
				}
				this.refresh('intrAdtYcnt', 'etcFctr', 'kamYn', 'ifrsYn', 'bizRprtYn')
			},
			satgrp: function(value){
				let thisObj = this;
				thisObj.onMsg = false;
				thisObj.refresh('maxRatio', 'baseWkmnsp', 'revwCnt', 'intrAdtYcnt', 'etcFctr', 'kamYn', 'bizRprtYn', 'ifrsFctr', 'holdingsFctr', 'firstAdtFctr'
						, 'usaListFctr', 'riskFctr', 'kamFctr', 'consoFinstatFctr' , 'sbsidCntFctr'
						, 'currAdtopinFctr', 'currConsoLossFctr', 'priorAdtopinChgFctr', 'priorLossFctr'
				);
			},
			contiAdtCnt: function(value){
				this.refresh('firstAdtYn')
			},
			firstBizDt: function(value){
				if( value !== null && value !== ''){
					if(value < '2019-12'){
						Helper.MessageBox("[그룹정보][최초적용 사업연도]는 2019-12월 이후부터 입력 가능합니다.")
						this.firstBizDt = null;
					}
				} 
			},
			minMaxYn: function(value){
				if(value.cd === 'Y'){
					if(this.satgrp.cd === null || this.satgrp.cd === ''){
						Helper.MessageBox("[그룹정보][표준 감사 시간 그룹 정보]를 선택하세요")
						this.setSelect('minMaxYn', '');
						return;
					}
					
					if(!this.firstBizDt){
						Helper.MessageBox("[그룹정보][최초적용 사업연도]를 입력하세요.")
						this.setSelect('minMaxYn', '');
						return;
					}
					
					
				}
			},
			fstAdtrBaseWkmnspYn: function(value){
				if(value) {
					this.fstAdtrWkmnsp = this.baseWkmnsp
					this.setSelect('fstWkmnspReason', '01');
				}else{
					this.setSelect('fstWkmnspReason', '');
				}
			},
			priorAdtrBaseWkmnspYn: function(value){
				if(value) {
					this.priorAdtrWkmnsp = this.baseWkmnsp
					this.setSelect('priorWkmnspReason', '01');
				}else{
					this.setSelect('priorWkmnspReason', '');
				}
			},
			indivAsset: function(){this.refresh('intrAdtYcnt')},
			intrAdtYcnt: function(){this.refresh('intrAdtTm')},
			holdingsDv: function(){this.refresh('intrAdtTm')},
			intrAdtYn: function(){this.refresh('intrAdtTm')},
			satgrp1ExptYn: function(){this.refresh('satgrp');},
			ifrsYn: function(value){this.refreshFctr('ifrsFctr');},
			holdingsDv: function(value){this.refreshFctr('holdingsFctr');},
			firstAdtYn: function(value){this.refreshFctr('firstAdtFctr');},
			usaListYn: function(value){this.refreshFctr('usaListFctr');},
			riskBase: function(value){this.refreshFctr('riskFctr');},
			kamYn: function(value){this.refreshFctr('kamFctr');},
			consoFinstatYn: function(value){this.refreshFctr('consoFinstatFctr');this.refresh('sbsidCnt')},
			sbsidCnt: function(value){this.refreshFctr('sbsidCntFctr');},
			currAdtopin: function(value){this.refreshFctr('currAdtopinFctr');},
			currConsoLossYn: function(value){this.refreshFctr('currConsoLossFctr');},
			priorAdtopinChgYn: function(value){this.refreshFctr('priorAdtopinChgFctr');},
			priorLossYn: function(value){this.refreshFctr('priorLossFctr');},
		}
	});
});
</script>