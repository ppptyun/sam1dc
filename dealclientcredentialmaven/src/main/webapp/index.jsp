<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html>
<head>
<title>Deals Client Credential</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<%@ include file="comn/sessionSet.jsp"%>

<!-- 공통 : CSS Loading -->
<link rel="stylesheet" type="text/css"
	href="external-library/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css"
	href="js/dhtmlx/codebase/dhtmlx.css">
<link rel="stylesheet" type="text/css"
	href="js/dhtmlx/codebase/dhtmlxcalendar.css">
<link rel="stylesheet" type="text/css"
	href="js/dhtmlx/sources/dhtmlxMessage/codebase/skins/dhtmlxmessage_dhx_skyblue.css">
<link rel="stylesheet" type="text/css"
	href="js/dhtmlx/sources/dhtmlxCombo/codebase/skins/dhtmlxcombo_dhx_skyblue.css">
<link rel="stylesheet" type="text/css"
	href="js/dhtmlx/sources/dhtmlxSlider/codebase/skins/dhtmlxslider_dhx_skyblue.css">

<link rel="stylesheet" type="text/css"
	href="js/dhtmlx/codebase/dhtmlx_custom.css">
<link rel="stylesheet" type="text/css" href="css/credential.css">
<!-- <link rel="stylesheet" type="text/css" href="css/manage.css"> -->
<style>
</style>

<!-- 공통 : JS Loading -->
<script src="js/dhtmlx/codebase/dhtmlx.js"></script>
<script src="js/dhtmlx/sources/dhtmlxSlider/codebase/dhtmlxslider.js"></script>
<script src="js/dhtmlx/dhtmlx_custom.js"></script>
<script src="js/dhtmlx/sources/dhtmlxMessage/codebase/dhtmlxmessage.js"></script>
<script src="js/jquery.core.js"></script>
<script src="js/comn/comn.js"></script>
<script src="js/comn/UserInfo.js"></script>
<script src="js/comn/DcComn.js"></script>
<script src="js/comn/DcPageNavigation.js"></script>

<!-- 공통 : 프로그램 정보 및 사용자 정보 초기화 -->
<script>
	// 프로그램 정보
	var CURR_INFO = {
		contextPath		: "<%=ContextRoot%>",
		contentPath		: "<%=ContextLoader.getContext("ContentPath")%>",
		systemCode		: "<%=ContextLoader.getContext("ProgramCode")%>",
		serviceBroker	: "comn/ServiceBroker.jsp",
		portalUri		: "<%=redirectPortalFullUri%>"
	};
	
	var USERROLE = [];
	<c:forEach items="${userRoles}" var="role" varStatus="status">
		if($.trim("${role.rolecd}") != ""){
			USERROLE.push({rolecd:"${role.rolecd}", rolenm:"${role.rolenm}"});	
		}
	</c:forEach>
	
	var SERVICE_CONFIG = ${serviceConfig};
	
	//	사용자 정보
	USERINFO.init({
		"korname"	:"${userInfo.KORNM}",
		"engname"	:"${userInfo.ENGNM}",
		"inteid"	:"${userInfo.INTEID}",
		"email"		:"${userInfo.EMAIL}",
		"emplno"	:"${userInfo.EMPLNO}",
		"gradnm"	:"${userInfo.GRADNM}",
		"teamcd"	:"${userInfo.TEAMCD}",
		"teamnm"	:"${userInfo.TEAMNM}"
	});
	
	var selected_menu = {};
	var user_Menus = [];
	<c:forEach var="menu" items="${userMenus}" varStatus="status">
		user_Menus.push({level:'${menu.level}', upmenuid:'${menu.upmenuid}', menuid:'${menu.menuid}', url:'${menu.url}', menunm:'${menu.menunm}'});
	</c:forEach>
	</script>

<script src="js/comn/ExportController.js"></script>
<script src="js/comn/FileUpload.js"></script>
<script src="js/contents/client/excelColumnDef.js"></script>
<script src="js/contents/client/popup.js"></script>
<script src="js/comn/AppLogMng.js"></script>
<script src="js/index.js"></script>
<!-- <script src="js/contents/client/ltDetailLeagueTable.js" charset='utf-8'></script> -->

</head>
<body onload="INDEX_OBJ.init()">
	<div class="wrap">
		<!-- Header -->
		<div class="dc_header">
			<h1 class="logo">
				<span id="title"><span style="font-size: 30px"></span> Deals Client Credential</span>
			</h1>
			<ul class="util">
				<li><span id="user" onclick="INDEX_OBJ.showPopup(this);"></span></li>
			</ul>
		</div>
		<!-- container -->
		<div class="dc_container" id="container">
			<!-- mian_tab_area -->
			<div class="dc_tab_area">
				<ul class="dc_tab_list" id="dc_tab_list">
				</ul>
			</div>
			<!-- main_contents -->
			<div class="dc_contents">
				<div class="dc_con_body">
					<div class="dc_con_menu" id="dc_con_menu">
						<div class="menu_list_area" id="menu_list_area">
							<ul class="menu_list" id="menu_list">
							</ul>
						</div>
					</div>
					<div class="dc_con_article" id="dc_con_article"></div>
				</div>
			</div>
		</div>
		<!-- Footer -->
		<div class="dc_footer">
			<p class="copy">&copy; 2016-2022 Samil
				PricewaterhouseCoopers. All right reserved</p>
			<div class="footer_info">
				<p>
					<strong class="fc_red">Authorised Restricted Use
						-Confidential</strong> 본 시스템에 저장되어 있거나 처리된 정보는 삼일회계법인 자산이며, 그 권한이 삼일 내부
					사용자들로 엄격하게 접근이 제한되어 있습니다. 본 시스템에 접속 또는 사용시에는 법률적인 근거와 기타 특수 목적에 의한
					시스템 모니터링을 승인한 것입니다. 시스템 자원에 대한 승인되지 않은 불법적인 접근이나 사용은 형사고발 및 처벌의 대상이
					됩니다.
				</p>
			</div>
		</div>

		<%@ include file="contents/client/popup.jsp"%>

	</div>
</body>
</html>