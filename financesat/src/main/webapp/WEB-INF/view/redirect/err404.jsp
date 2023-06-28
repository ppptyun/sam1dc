<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<% 
response.setStatus(200);
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.   
response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
response.setDateHeader("Expires", 0); // Proxies. */
%>
<c:set var="now" value="<%=new java.util.Date()%>" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Page Not Found</title>
<link href="${pageContext.request.contextPath}/resources/css/error.css?version=<fmt:formatDate value="${now}" pattern="yyyyMMddHHmmss" />" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="err-page">
	<div class="err-container">
		<div class="err-code">404</div>
		<div class="err-msg">
			<p>존재하지 않는 페이지 입니다.(<a href="${pageContext.request.contextPath}">홈으로 돌아가기</a>)</p>
		</div>
	</div>
</div>
</body>
</html>