<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	System.out.println("userId : "+userId);
	System.out.println("user Ten: "+userTen);
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
</head>


<frameset rows="60,*" frameborder="0" border="0" framespacing="0">
  <frame name="top" src="../pages/Distributor/top.jsp" scrolling="no">
<frameset cols="14%,*" frameborder="0" border="0" framespacing="0">
	<frame name="menu" src="../pages/Distributor/leftMenu.jsp" marginheight="0" marginwidth="0" scrolling="no" noresize>
	<frame name="content" src="../pages/Distributor/home.jsp" marginheight="0" marginwidth="0" scrolling="auto" noresize>

<noframes>

</noframes>

</frameset>
</html>
<%}%>
