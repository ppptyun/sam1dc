<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="now" value="<%=new java.util.Date()%>" />
<%   
/* response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.   
response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
response.setDateHeader("Expires", 0); // Proxies. */
%>
<tiles:importAttribute name="appInfo"/>
<c:set var="theme" value="web" />
<!doctype html>
<html lang="ko">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>${appInfo.title}</title>
	<link href="${pageContext.request.contextPath}/resources/lib/jqueryui/plugins/datepicker/datepicker.min.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/resources/lib/dhtmlx/skins/${theme}/dhtmlx.css?version=<fmt:formatDate value="${now}" pattern="yyyyMMddHHmmss" />" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/resources/css/pwc.css?version=<fmt:formatDate value="${now}" pattern="yyyyMMddHHmmss" />" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/resources/css/pwc_mediaS.css?version=<fmt:formatDate value="${now}" pattern="yyyyMMddHHmmss" />" rel="stylesheet" type="text/css" media="(max-width:1600px)">
	<link href="${pageContext.request.contextPath}/resources/lib/vue/components/perfect-scrollbar.css" rel="stylesheet" type="text/css">
	
    <style type="text/css">
    .hdrcell input[type=checkbox]{-webkit-appearance:none;appearance:none;width:24px;height:24px;border:none;background:url(${pageContext.request.contextPath}/resources/images/spr_form.png) no-repeat 0 -58px}
	.hdrcell input[type=checkbox]:checked{background-position:0px -87px}
    </style>
    
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/lib/babel-polyfill/polyfill.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/lib/fontawesome/js/all.js"></script>
        <c:choose>
    	<c:when test="${appConfig.env == 'development'}">
    	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/lib/vue/vue.js"></script>
    	</c:when>
    	<c:otherwise>
    	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/lib/vue/vue.min.js"></script>	
    	</c:otherwise>
    </c:choose>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/lib/vue/components/perfect-scrollbar.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/lib/jquery/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/lib/jquery/plugins/jquery-number/jquery.number.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/lib/jquery/plugins/jquery-fileDownload/jquery.fileDownload.js"></script>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/lib/jqueryui/jquery-ui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/lib/jqueryui/jquery.jscrollpane.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/lib/jqueryui/plugins/datepicker/datepicker.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/lib/jqueryui/plugins/datepicker/i18n/datepicker.ko-KR.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/lib/dhtmlx/codebase/dhtmlx.js"></script>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/dhtmlxCustom.js?version=<fmt:formatDate value="${now}" pattern="yyyyMMddHHmmss" />"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/Helper.js?version=<fmt:formatDate value="${now}" pattern="yyyyMMddHHmmss" />"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/SessionStorageCtrl.js?version=<fmt:formatDate value="${now}" pattern="yyyyMMddHHmmss" />"></script>

	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/v2/selectbox_pwc.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/v2/pwc_ui.js"></script>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js//VueCommon.js"></script>
	<!-- Global site tag (gtag.js) - Google Analytics -->
	<script async src="https://www.googletagmanager.com/gtag/js?id=UA-136934052-1"></script>
	<script>
	  window.dataLayer = window.dataLayer || [];
	  function gtag(){dataLayer.push(arguments);}
	  gtag('js', new Date());
	
	  gtag('config', 'UA-136934052-1');
	</script>

	<script type="text/javascript">
		//백 버튼 방지: html 로드되면 실행
		$(document).ready(function() {cfHistoryNoBack();});
		//오페라, 사파리 뒤로가기 막기
		history.navigationMode = 'compatible';
		function cfHistoryNoBack() {if (window.history) {window.history.forward(1);}}
		//파이어 폭스에서만 동작
		if (navigator.userAgent.indexOf("Firefox") != -1) {window.onunload = function() {void (0);}}
		
		
		const THEME_INFO = {
			THEME : "dhx_${theme}",
			THEME_IMG_PATH : "${pageContext.request.contextPath}/resources/lib/dhtmlx/skins/${theme}/imgs/",
			COMM_IMG_PATH : "${pageContext.request.contextPath}/resources/lib/dhtmlx/codebase/imgs/",
		}
	</script>
</head>
<!--[if ie]><body class="lt-ie"><![endif]-->
<body>
	<div id="wrapper" class="wrapper <c:if test="${pageStatus.isNavClose=='1'}">navClose</c:if>"><!-- 햄버거 메뉴 클릭시 navClose 클래스 추가 -->
		<tiles:insertAttribute name="header" />
		<hr>
		<section id="container" class="container">
			<div id="contents" class="contents">
	        	<tiles:insertAttribute name="content" />
			</div>
	    </section>
	</div>    
	
	
	<div id="popInner"></div><!-- 레이어팝업 들어가는 부분 -->
	<div class="dimmed"></div>
	<!-- loading -->
	<div class="loadingA" style="display:none">
		<div class="inner">
			<p class="txtT">Loading...</p>
			<img src="${pageContext.request.contextPath}/resources/images/loading.gif" alt="로딩중">
			<p class="txtB">Please wait!!</p>
		</div>
	</div>	
</body>
</html>