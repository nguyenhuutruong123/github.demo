<%@page import="geso.dms.center.db.sql.dbutils"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.servlets.count.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Refresh" content="60" >
<title>Insert title here</title>

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.dev_mode) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</head>
<%
	dbutils db =new dbutils();
	String songuoi="30";
	ResultSet rs = db.get("select COUNT(PK_SEQ) as soluong from NHANVIEN where ISLOGIN='1'");		
	try{
	rs.next();
	songuoi = rs.getString("soluong");
	System.out.println("So nguoi truy cap: " + songuoi + "\n");
	}
	catch(Exception er)
	{
		System.out.println(er.toString());	
	}
	db.shutDown();
%>
<body>
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.dev_mode){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

  <P style="margin-top: -1px;font-size: 11px;margin-left: 5px"> Hiện có <%= songuoi %> người đang dùng hệ thống  </P> 
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>