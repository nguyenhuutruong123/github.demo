<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isErrorPage = "true"%>
<%@ page import = "java.io.*" %>

<HTML>
    <HEAD>
        <TITLE> Lỗi... </TITLE>
    </head>
<body>
	<h2>Ứng dụng bạn truy xuất là phát sinh lỗi</h2>
	<h3>Vui lòng Refesh lại ứng dụng từ trình duyệt, nếu vẫn gặp lỗi vui lòng liên hệ trung tâm để xử lý.</h3>
	<b>Exception:</b><br> 
	<font color="red"><%= exception.toString() %></font>
</body>
</html>