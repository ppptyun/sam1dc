<%@ page language="java"%>
<%@ page import="com.samil.dc.util.FileUpload"%>
<%
	request.setCharacterEncoding( "UTF-8" );
	response.setCharacterEncoding( "UTF-8" );
	response.setContentType("text;charset=utf-8");
	FileUpload fileUpload = new FileUpload();
	String filepath = fileUpload.upload(request);	
%>
<%=filepath %>
