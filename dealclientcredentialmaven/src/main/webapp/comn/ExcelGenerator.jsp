<%@ page import="java.io.IOException"%>
<%@ page import="java.net.URLDecoder"%>
<%@ page import="com.samil.dc.util.excel.ExcelWriter"%>
<%
try{
	out.clear();
	out = pageContext.pushBody();
}catch(Exception e){
	
}
request.setCharacterEncoding( "UTF-8" );
response.setCharacterEncoding( "UTF-8" );
String xml = request.getParameter("grid_xml");
xml = URLDecoder.decode(xml, "UTF-8");
(new ExcelWriter()).generate(xml, response);
%>
