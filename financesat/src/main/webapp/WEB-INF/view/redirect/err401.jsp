<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<% response.setStatus(200); %>
<c:set var="now" value="<%=new java.util.Date()%>" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Unauthorized</title>
<link href="${pageContext.request.contextPath}/resources/css/error.css?version=<fmt:formatDate value="${now}" pattern="yyyyMMddHHmmss" />" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="err-page">
	<div class="err-container">
		<div class="err-code">401</div>
		<div class="err-msg">
			<p>사용자 인증처리가 되지 않았습니다.</p>
			<p>서비스 데스크로 문의하시기 바랍니다.(02-3781-9999, #9999)</p>
		</div>
	</div>
</div>
</body>
</html>