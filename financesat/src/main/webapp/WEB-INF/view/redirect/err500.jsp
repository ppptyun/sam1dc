<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<% response.setStatus(200); %>
<c:set var="now" value="<%=new java.util.Date()%>" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Something went wrong</title>
<link href="${pageContext.request.contextPath}/resources/css/error.css?version=<fmt:formatDate value="${now}" pattern="yyyyMMddHHmmss" />" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="err-page">
	<div class="err-container">
		<div class="err-code">500</div>
		<div class="err-msg">
			<p>데이터 처리 중 오류가 발생하였습니다.(<a href="${pageContext.request.contextPath}">홈으로 돌아가기</a>)</p>
			<p>잠시후 재시도 해보시고 반복적으로 발생 할 경우, 서비스 데스크(02-3781-9999, #9999)로 문의 주시기 바랍니다.</p>
		</div>
	</div>
</div>
</body>
</html>