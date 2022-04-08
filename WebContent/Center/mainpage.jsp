<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>
			
<html>
<head>
<title></title>
</head>
<frameset rows="60,*" frameborder="0" border="0" framespacing="0">
  <frame name="top" src="../pages/Center/top.jsp" scrolling="no">
<frameset cols="13%,*" frameborder="0" border="0" framespacing="0">
	<frame name="menu" src="../pages/Center/leftMenu.jsp" marginheight="0" marginwidth="0" scrolling="no" noresize>
	<frame name="content" src="../pages/Center/home.jsp" marginheight="0" marginwidth="0" scrolling="auto" noresize>

<noframes>

</noframes>

</frameset>
</frameset>
</html>
<%}%>