<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.samil.dc.service.worker.ServiceHelper"%>
<%@ page import="com.samil.dc.domain.CommonCodeListBean"%>
<%@ page import="com.samil.dc.service.ApplicationGlobal"%>
<%
//버튼 권한
boolean authSave = ServiceHelper.isAuthLeagueListSave(request);
request.setAttribute("authSave", authSave);

// 자문형태
String consultType = "103";
List<CommonCodeListBean> consultCodeList = ApplicationGlobal.getCommonCodeList(consultType);
request.setAttribute("consultCodeList", consultCodeList);
%>
<script>
var __consult_code_list = [];
<c:forEach var="code" items="${consultCodeList}" varStatus="status">
__consult_code_list.push({code:'${code.itemcd}', name:'${code.itemnm}'});
</c:forEach>
</script>
<script src="js/contents/client/ltSetupConsultType.js" charset='utf-8'></script>
<script>
$(document).ready(function() {
	
});
</script>
<body>
	<div class="dc_con_area">
		<div class="dc_btn_area">
			<div class="dc_btn_area_rgt">
				<button type="button" class="btn btn_red" id="btn_guidance">Guidance</button>
				<c:if test="${authSave}">
					<button type="button" class="btn btn_red ico_save"
						id="btn_consult_save">저장</button>
				</c:if>
			</div>
		</div>
		<div class="dc_scroll_table_area" id="div-grid-area"></div>
	</div>
</body>
