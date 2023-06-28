<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.samil.dc.domain.CommonCodeListBean"%>
<%@ page import="com.samil.dc.service.ApplicationGlobal"%>
<%
// League Table 대상 여부
String objectType = "110";
List<CommonCodeListBean> objectCodeList = ApplicationGlobal.getCommonCodeList(objectType);
request.setAttribute("objectCodeList", objectCodeList);
%>
<script>
var __object_code_list = [];
<c:forEach var="code" items="${objectCodeList}" varStatus="status">
__object_code_list.push({code:'${code.itemcd}', name:'${code.itemnm}'});
</c:forEach>
</script>
<script src="js/contents/client/ltSetupLeagueTable.js" charset='utf-8'></script>
<body>
	<div class="dc_con_area">
		<div class="dc_btn_area">
			<div class="dc_btn_area_rgt">
				<!--  20200323 남웅주 엑셀다운로드 추가 -->
				<button type="button" class="btn btn_red ico_down" id="btn_excel_download">Download</button>
													
				<button type="button" class="btn btn_red" id="btn_guidance">Guidance</button>
				<button type="button" class="btn btn_red" id="btn_league_include">대상
					지정</button>
				<button type="button" class="btn btn_red" id="btn_league_exclude">대상
					제외</button>
			</div>
		</div>
		<div class="dc_scroll_table_area" id="div-grid-area"></div>
	</div>
</body>
