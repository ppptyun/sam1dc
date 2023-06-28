<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.samil.dc.domain.CommonCodeListBean"%>
<%@ page import="com.samil.dc.service.ApplicationGlobal"%>
<%
// 로그유형
String logTypeCd = "128";
List<CommonCodeListBean> logTypeCodeList = ApplicationGlobal.getCommonCodeList(logTypeCd);
request.setAttribute("logTypeCodeList", logTypeCodeList);

//변경유형
String logMethodCd = "129";
List<CommonCodeListBean> logMethodCodeList = ApplicationGlobal.getCommonCodeList(logMethodCd);
request.setAttribute("logMethodCodeList", logMethodCodeList);
%>
<script src="js/contents/admin/fieldLog.js" charset='utf-8'></script>
<body>
	<div class="dc_con_area"
		style="width: 100%; height: 100%; box-sizing: border-box">
		<div class="dc_search_area">
			<div class="search_head">
				<dl>
					<dt>열/페이지</dt>
					<dd>
						<select class=".dc_top_box select" id="search_rowcount"
							name="search_rowcount">
						</select>
					</dd>
				</dl>
				<dl>
					<dt>프로젝트코드</dt>
					<dd>
						<input type="text" id="search_prjtcd" name="search_prjtcd" />
					</dd>
				</dl>
				<dl>
					<dt>로그유형</dt>
					<dd>
						<select class=".dc_top_box select" id="search_logcd"
							name="search_logcd">
							<option value="">전체</option>
							<c:forEach var="code" items="${logTypeCodeList}"
								varStatus="status">
								<option value="${code.itemcd}">${code.itemnm}</option>
							</c:forEach>
						</select>
					</dd>
				</dl>
				<dl>
					<dt>변경유형</dt>
					<dd>
						<select class=".ad_top_box select" id="search_altcd"
							name="search_altcd">
							<option value="">전체</option>
							<c:forEach var="code" items="${logMethodCodeList}"
								varStatus="status">
								<option value="${code.itemcd}">${code.itemnm}</option>
							</c:forEach>
						</select>
					</dd>
				</dl>
				<dl>
					<dt>변경자사번</dt>
					<dd>
						<input type="text" id="search_empno" name="search_empno"
							maxlength="6" />
					</dd>
				</dl>
				<button type="button" class="btn btn_red btn_search" id="btnSearch"
					title="검색">검색</button>
			</div>
		</div>
		<div id="div-grid"></div>
	</div>
	<div class="dc_paging_area" id="div-grid-paging">
		<div id="page_box">
			<!-- <button type="button" class="btn_first" title="첫페이지로">1페이지</button>
			<button type="button" class="btn_prev" title="이전페이지로">이전</button>
			<div class="btn_paging">
				<button type="button" class="on">1</button>
				<button type="button">2</button>
				<button type="button">3</button>
				<button type="button">4</button>
				<button type="button">5</button>
				<button type="button">6</button>
				<button type="button">7</button>
				<button type="button">8</button>
				<button type="button">9</button>
				<button type="button">10</button>
			</div>
			<button type="button" class="btn_next" title="다음페이지로">다음</button>
			<button type="button" class="btn_last" title="마지막 페이지로">100페이지</button> -->
		</div>
	</div>
	<%-- <div class="ad_top_area non_border" id="div-toolbar">
		<div class="ad_top_box">
			
			<div class="ad_top_box_lft" id="div-searchBox">
				<div class="box_row" >
					<span>
						열/페이지&nbsp;
						<select class=".ad_top_box select" id="search_rowcount" name="search_rowcount">
						</select>
					</span>
					<span>
						프로젝트 코드&nbsp;
						<input type="text" id="search_prjtcd" name="search_prjtcd" />&nbsp;
					</span>
					<span>
						로그 유형&nbsp;
						<select class=".ad_top_box select" id="search_logcd" name="search_logcd">
							<option value="">전체</option>
							<c:forEach var="code" items="${logTypeCodeList}" varStatus="status">
							<option value="${code.itemcd}">${code.itemnm}</option>
							</c:forEach>
						</select>
					</span>
					<span>
						변경 유형&nbsp;
						<select class=".ad_top_box select" id="search_altcd" name="search_altcd">
							<option value="">전체</option>
							<c:forEach var="code" items="${logMethodCodeList}" varStatus="status">
							<option value="${code.itemcd}">${code.itemnm}</option>
							</c:forEach>
						</select>
					</span>
					<span>
						변경자 사번&nbsp;
						<input type="text" id="search_empno" name="search_empno" maxlength="6" />&nbsp;
					</span>
				</div>
			</div>
			<div class="ad_top_box_rgt">
				<button type="button" class="btn_red_wide" id="btnSearch" title="검색"><i class="fa fa-search" aria-hidden="true"></i> 검색</button>
			</div>				
		</div>
	</div> --%>
	<!-- <div class="ad_con_area" id="div-grid-area">
		<div id="div-grid" style="height:350px;"></div>
		<div id="div-grid-paging">
			<div id="page_box"></div>
		</div>
	</div> -->
</body>
