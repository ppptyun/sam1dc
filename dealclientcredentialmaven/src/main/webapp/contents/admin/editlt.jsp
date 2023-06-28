<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.samil.dc.domain.CommonCodeListBean"%>
<%@ page import="com.samil.dc.service.ApplicationGlobal"%>
<%
//롤
List<CommonCodeListBean> roleCodeList = ApplicationGlobal.getRoleCodeList();
request.setAttribute("roleCodeList", roleCodeList);
%>
<script>
	var __role_code_list = [];
	<c:forEach var="code" items="${roleCodeList}" varStatus="status">
		__role_code_list.push({code:'${code.itemcd}', name:'${code.itemnm}'});
	</c:forEach>
</script>
<script src="js/contents/admin/editlt.js" charset='utf-8'></script>
<body>
	<div class="dc_con_area">
		<div class="dc_btn_area">
			<div class="dc_btn_area_rgt">
				<button type="button" class="btn_red_wide" id="btn_add">추가</button>
				<button type="button" class="btn_red_wide" id="btn_del">삭제</button>
				<button type="button" class="btn_red_wide" id="btn_save">저장</button>
			</div>
		</div>
		<div class="dc_scroll_table_area" id="div-grid-area"></div>
	</div>
</body>
