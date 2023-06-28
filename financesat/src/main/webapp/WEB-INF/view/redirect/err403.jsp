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
		<div class="err-code">403</div>
		<div class="err-msg">
			<p>접근 권한이 없습니다.(<a href="${pageContext.request.contextPath}">홈으로 돌아가기</a>)</p>
			<p>어플리케이션 담당자에게 권한 요청을 하시기 바랍니다.</p>
		</div>
	</div>
</div>
</body>
</html>