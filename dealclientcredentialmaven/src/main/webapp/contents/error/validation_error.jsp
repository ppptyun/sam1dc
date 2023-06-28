<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.samil.dc.service.worker.ValidationResult"%>
<%
String errorCode = "";
String errorMessage = "";
String errorParameter = "";
String errorParameterValue = "";

ValidationResult validationResult = (ValidationResult) request.getAttribute("error");
if (validationResult != null) {
	errorCode = validationResult.getErrorCode();
	errorMessage = validationResult.getErrorMessage();
	errorParameter = validationResult.getErrorParameter();
	errorParameterValue = validationResult.getErrorParameterValue();
}
%>
<!doctype html>
<html>
<head>
</head>
<body>
	<div>
		Error Code:
		<%=errorCode%>
	</div>
	<div>
		Error Message:
		<%=errorMessage%>
	</div>
	<div>
		Parameter Name:
		<%=errorParameter%>
	</div>
	<div>
		Parameter Value:
		<%=errorParameterValue%>
	</div>
</body>
</html>