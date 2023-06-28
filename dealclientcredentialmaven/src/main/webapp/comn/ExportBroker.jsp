<%@ page import="com.samil.dc.util.ExcelExport"%>
<%
try{
	out.clear();
	out = pageContext.pushBody();
}catch(Exception e){
	
}
request.setCharacterEncoding( "UTF-8" );
response.setCharacterEncoding( "UTF-8" );
if("excel".equalsIgnoreCase(request.getParameter("filetype"))){
	(new ExcelExport()).doPost(request, response);	
}
%>