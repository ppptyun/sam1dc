
<%
String debug_userid = request.getParameter("userid");
if(!(debug_userid == null || "".equals(debug_userid))){
	Cookie cookie = new Cookie("debug_userid", debug_userid);
	response.addCookie(cookie);	
}
%>
<script>
location.href = "<%=request.getContextPath()%>";
</script>
