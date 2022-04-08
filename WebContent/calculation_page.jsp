<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- include custom error page in this jsp code --%>
<%@ page errorPage="error_page.jsp" %>

<html>
    <head>
        <title>calculation page</title>
    </head>
    <body>
       <% 
	         int i = 10;

			 // This line will create an error so error page will be called
             i = i / 0;
	   %>
    </body>
</html>