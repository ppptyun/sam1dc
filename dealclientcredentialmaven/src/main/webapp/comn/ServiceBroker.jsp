<%@ page language="java"%>
<%@ page import="com.samil.dc.core.ServiceLoader"%>

<%
	try{
		out.clear();
		out = pageContext.pushBody();
	}catch(Exception e){
		
	}
	request.setCharacterEncoding( "UTF-8" );
	response.setCharacterEncoding( "UTF-8" );
	
	ServiceLoader sl = new ServiceLoader();
	sl.doPost(request, response);
%>

