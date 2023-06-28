<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.samil.dc.domain.CommonCodeListBean"%>
<%@ page import="com.samil.dc.service.ApplicationGlobal"%>
<%
// Credential 분류
request.setAttribute("credTypeCodeList", ApplicationGlobal.getCommonCodeList("101"));
// 자문형태 분류
request.setAttribute("consultTypeCodeList", ApplicationGlobal.getCommonCodeList("103"));
// 기업Actor분류
request.setAttribute("bizActorTypeCodeList", ApplicationGlobal.getCommonCodeList("104"));
// 자문기업분류
request.setAttribute("bizConsultTypeCodeList", ApplicationGlobal.getCommonCodeList("105"));
%>
<script>
var __cred_code_list = [];
<c:forEach var="code" items="${credTypeCodeList}" varStatus="status">
__cred_code_list.push({code:'${code.itemcd}', name:'${code.itemnm}'});
</c:forEach>

var __consult_code_list = [];
<c:forEach var="code" items="${consultTypeCodeList}" varStatus="status">
__consult_code_list.push({code:'${code.itemcd}', name:'${code.itemnm}'});
</c:forEach>

var __bizactor_code_list = [];
<c:forEach var="code" items="${bizActorTypeCodeList}" varStatus="status">
__bizactor_code_list.push({code:'${code.itemcd}', name:'${code.itemnm}'});
</c:forEach>

var __bizconsult_code_list = [];
<c:forEach var="code" items="${bizConsultTypeCodeList}" varStatus="status">
__bizconsult_code_list.push({code:'${code.itemcd}', name:'${code.itemnm}'});
</c:forEach>
</script>
<script src="js/contents/admin/pdt.js" charset='utf-8'></script>
<body>
	<div class="dc_con_area">
		<div class="dc_btn_area">
			<label>참조년도</label> <select id="sel_refyearly" name="sel_refyearly"></select>
			<div class="dc_btn_area_rgt">
				<button type="button" class="btn_red_wide" id="btn_pdt_category">카테고리관리</button>
				<button type="button" class="btn_red_wide" id="btn_pdt_copy">PDT복사</button>
				<button type="button" class="btn_red_wide" id="btn_pdt_add">PDT추가</button>
				<button type="button" class="btn_red_wide" id="btn_pdt_del">PDT삭제</button>
				<button type="button" class="btn_red_wide" id="btn_pdt_save">저장</button>
			</div>
		</div>
		<div class="dc_scroll_table_area" id="div-grid-area"></div>
	</div>
</body>




