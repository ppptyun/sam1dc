<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.samil.dc.service.worker.ServiceHelper"%>
<%@ page import="com.samil.dc.domain.CommonCodeListBean"%>
<%@ page import="com.samil.dc.service.ApplicationGlobal"%>
<%
//버튼 권한
boolean authDownload = ServiceHelper.isAuthLeagueListDownload(request);
boolean authSave = ServiceHelper.isAuthLeagueListSave(request);
request.setAttribute("authDownload", authDownload);
request.setAttribute("authSave", authSave);

// 담당자확인여부
String confirmType = "108";
List<CommonCodeListBean> confirmCodeList = ApplicationGlobal.getCommonCodeList(confirmType);
request.setAttribute("confirmCodeList", confirmCodeList);
%>
<script>
var __confirm_code_list = [];
<c:forEach var="code" items="${confirmCodeList}" varStatus="status">
__confirm_code_list.push({code:'${code.itemcd}', name:'${code.itemnm}'});
</c:forEach>
</script>
<script src="js/contents/client/ltRealLeagueTable.js" charset='utf-8'></script>
<script>
$(document).ready(function() {
	
});
</script>
<body>
	<div class="dc_con_area" id="dc_con_list">
		<div class="dc_btn_area">
			<div class="dc_btn_area_rgt">
				<button type="button" class="btn btn_red" id="btn_guidance">Guidance</button>
				<c:if test="${authDownload}">
					<button type="button" class="btn btn_red ico_down"
						id="btn_excel_download">Download</button>
				</c:if>
				<c:if test="${authSave}">
					<button type="button" class="btn btn_red ico_save"
						id="btn_consult_save">저장</button>
				</c:if>
			</div>
		</div>
		<div class="dc_scroll_table_area" id="div-grid-area"></div>
	</div>
	<div id="dc_con_list_detail"></div>
</body>