<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="layCont">
	<table class="tblForm tblD">
		<caption>Project 기본정보</caption>
		<colgroup><col style="width:23%"><col style="width:auto"><col style="width:23%"><col style="width:27%"></colgroup>
		<tbody>
			<tr>
				<th scope="row">Client Name </th>
				<td><span id="pop-clntNm">${result.data.clntNm}</span></td>
				<th scope="row">Project Code</th>
				<td><span id="pop-prjtCd">${result.data.prjtCd}</span></td>
			</tr>
			<tr>
				<th scope="row">용역명</th>
				<td><span id="pop-shrtNm">${result.data.shrtNm}</span></td>
				<th scope="row">EL 투입 시간</th>
				<td class="tright"><span id="pop-elBdgtTm" sh-format="#,##0.00"><fmt:formatNumber value="${result.data.elBdgtTm}"  pattern="#,##0.00" /></span> hr</td>
			</tr>
		</tbody>
		<tfoot class="type02">
			<tr>
				<th scope="row">Net Revenue</th>
				<td colspan="3"><span id="pop-netRvnu" sh-format="#,##0" ><fmt:formatNumber value="${popupInfo.netRvnu}"  pattern="#,##0" /></span> 원</td>
			</tr>
		</tfoot>
	</table>
	
	<table class="tblForm tblD marT20">
		<caption>Project 기본정보</caption>
		<colgroup><col style="width:23%"><col style="width:auto"><col style="width:23%"><col style="width:27%"></colgroup>
		<tbody>
			<tr>
				<th scope="row">Partner</th>
				<td class="tright"><span id="pop-bdgtP" sh-format="#,##0.00"><fmt:formatNumber value="${result.data.bdgtP}"  pattern="#,##0.00" /></span> hr</td>
				<th scope="row">Director</th>
				<td class="tright"><span id="pop-bdgtD" sh-format="#,##0.00"><fmt:formatNumber value="${result.data.bdgtD}"  pattern="#,##0.00" /></span> hr</td>
			</tr>
			<tr>
				<th scope="row">Senior-Manager</th>
				<td class="tright"><span id="pop-bdgtSm" sh-format="#,##0.00"><fmt:formatNumber value="${result.data.bdgtSm}"  pattern="#,##0.00" /></span> hr</td>
				<th scope="row">Manager</th>
				<td class="tright"><span id="pop-bdgtM" sh-format="#,##0.00"><fmt:formatNumber value="${result.data.bdgtM}"  pattern="#,##0.00" /></span> hr</td>
			</tr>
			<tr>
				<th scope="row">Senior-Associate</th>
				<td class="tright"><span id="pop-bdgtSa" sh-format="#,##0.00"><fmt:formatNumber value="${result.data.bdgtSa}"  pattern="#,##0.00" /></span> hr</td>
				<th scope="row">Associate</th>
				<td class="tright"><span id="pop-bdgtA" sh-format="#,##0.00"><fmt:formatNumber value="${result.data.bdgtA}"  pattern="#,##0.00" /></span> hr</td>
			</tr>
			<tr>
				<th scope="row">New Staff</th>
				<td class="tright"><span id="pop-newStfBdgtTm" sh-format="#,##0.00"><fmt:formatNumber value="${result.data.newStfBdgtTm}"  pattern="#,##0.00" /></span> hr</td>
				<th scope="row">Fulcrum/RA/Other</th>
				<td class="tright"><span id="pop-etcBdgtTm" sh-format="#,##0.00"><fmt:formatNumber value="${result.data.etcBdgtTm}"  pattern="#,##0.00" /></span> hr</td>
			</tr>
		</tbody>
		<tfoot class="type02">
			<tr>
				<th scope="row">예상 표준수익</th>
				<td colspan="3"><span id="pop-totBdgtFee" sh-format="#,##0"><fmt:formatNumber value="${result.data.totBdgtFee}"  pattern="#,##0" /></span> 원</td>
			</tr>
		</tfoot>
	</table>
	<span class="txtInfo"><b>예상 표준수익</b>: 직급별 표준단가를 적용한 표준보수입니다. 단, Fulcrum 및 RA 배부 예산은 SA Grade, Other (QC 등) 투입시간은 Director Grade를 가정하여 계산하였습니다. F-Link 에서 적정 Grade로 조정하여 정확한 예상수익 계산하시기를 권고드립니다. </span>
	<table class="tblForm tblF marT20">
		<caption>Project 기본정보</caption>
		<colgroup><col style="width:25%" span="4"></colgroup>
		<tbody>
			<tr>
				<th scope="row">시간당 Revenue</th>
				<td><span id="pop-feeDiff" sh-format="#,##0"><fmt:formatNumber value="${(popupInfo.totBdgtHour == null || popupInfo.totBdgtHour == 0)?0:popupInfo.netRvnu / popupInfo.totBdgtHour}"  pattern="#,##0" /></span> 원</td>
				<th scope="row">Realization</th>
				<td><span id="pop-feeRatio" sh-format="#,##0"><fmt:formatNumber value="${(result.data.totBdgtFee == null || result.data.totBdgtFee == 0)?0:popupInfo.netRvnu / result.data.totBdgtFee * 100}"  pattern="#,##0.00" /></span> %</td>
			</tr>
			<tr>
				<th scope="row">예상 CM</th>
				<td><span id="pop-feeDiff" sh-format="#,##0"><fmt:formatNumber value="${popupInfo.expCm}"  pattern="#,##0" /></span> 백만원</td>
				<th scope="row"></th>
				<td></td>
			</tr>
		</tbody>
	</table>

</div>


