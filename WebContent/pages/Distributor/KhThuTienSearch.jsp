<%@page import="java.sql.SQLException"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.ResultSet" %>
<%@page import="geso.dms.distributor.db.sql.dbutils" %>
<%@page import="java.net.URLDecoder" %>

<%@ page  import = "geso.dms.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>


<% String tbhId = (String) session.getAttribute("tbhId"); %>

<% 
  	request.setCharacterEncoding("UTF-8");
   
   	String query = request.getQueryString();  
   	query = new String(query.substring(query.indexOf("letters=")+ 8, query.length()).getBytes("UTF-8"), "UTF-8");
   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
   	
   	dbutils db = new dbutils();    	
   	String khId = "";
	String khTen = "";
	
	String command = "select a.pk_seq as khId, a.ten as khTen from khachhang a where a.npp_fk = (select a.pk_seq from nhaphanphoi a, nhanvien b where b.dangnhap = a.ma and b.pk_seq ='" + userId + "')";
	if(tbhId != null && tbhId.length() > 0)
		command = "select b.pk_seq as khId, b.ten as khTen from khachhang_tuyenbh a inner join khachhang b on a.khachhang_fk = b.pk_seq where a.tbh_fk='" + tbhId + "'";
	ResultSet kh = db.get(command);
	
	response.setHeader("Content-Type", "text/html");
	
	if(kh != null)
	{
		int m = 0;
		try
		{
			while(kh.next())
			{
				if(kh.getString("khId") != null)
				{
					khId = kh.getString("khId");
					khTen = kh.getString("khTen");
				
					if(khId.toUpperCase().startsWith(query.toUpperCase()) || khId.toUpperCase().indexOf(query.toUpperCase()) >= 0 
						|| khTen.toUpperCase().indexOf(query.toUpperCase()) >= 0 || khTen.toUpperCase().indexOf(query.toUpperCase()) >= 0 )
					{
						out.print("###" + khId + " -- " + khTen + "|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
						
						m++;
						if(m >= 30)
							break;
					}
				}
			}
			kh.close();
		}
		catch(SQLException e){}
	}
	}
	
%>