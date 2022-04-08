<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.distributor.db.sql.dbutils" %>

<%@ page  import = "geso.dms.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>


<% String tbhId = (String) session.getAttribute("tbhId"); %>
<% dbutils db = new dbutils(); %>
<%
	//thay bang cac gia tri lay tu ResualSet
	String khId = "";
	String khTen = "";
	
	String command = "select pk_seq as khId, ten as khTen from khachhang where npp_fk = (select a.pk_seq from nhaphanphoi a, nhanvien b where b.dangnhap = a.ma and b.pk_seq ='" + userId + "')";
	if(tbhId != null && tbhId != "")
		command = "select kh.pk_seq as khId, kh.ten as khTen from khachhang_tuyenbh a inner join khachhang kh on a.khachhang_fk = kh.pk_seq where a.tbh_fk = '" + tbhId + "'";
	
	ResultSet kh = db.get(command);
	
	String query = (String)geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("q"));
	response.setHeader("Content-Type", "text/html");
	
	if(kh != null)
	{
		int cnt = 1;
		try
		{
			while(kh.next())
			{
				khId = kh.getString("khId");
				khTen = kh.getString("khTen");
			
				if(khTen.toUpperCase().startsWith(query.toUpperCase()) || khTen.toUpperCase().indexOf(query.toUpperCase()) >=0 
						|| khId.toUpperCase().startsWith(query.toUpperCase()) || khId.toUpperCase().indexOf(query.toUpperCase()) >=0)
				{
					String khachhang = "[" + khId + "][" + khTen + "]";
					out.print(khachhang + "\n");
					
					if(cnt >= 30)
						break;
					cnt++;
				}
			}
			kh.close();
			db.shutDown();
		}
		catch(SQLException e){}
	}
	}
	
%>