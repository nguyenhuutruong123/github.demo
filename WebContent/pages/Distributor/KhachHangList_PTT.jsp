<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.distributor.db.sql.dbutils" %>

<% String userId = (String) session.getAttribute("userId"); %>
<% String nvgnId = (String) session.getAttribute("nvgnId"); %>
<% dbutils db = new dbutils(); %>
<%
	
	String khId = "";
	String khTen = "";
	
	String command="";
	
	 command = "SELECT KH.PK_SEQ AS KHID, KH.TEN AS KHTEN FROM KHACHHANG KH " + 
	 		   "INNER JOIN NVGN_KH NVGN_KH ON KH.PK_SEQ = NVGN_KH.KHACHHANG_FK " +
	 		   "WHERE NVGN_FK = '" + nvgnId +"' ORDER BY KH.PK_SEQ, KH.TEN";

	ResultSet kh = db.get(command);
	System.out.println(command);
	String query = (String)geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("q"));
	response.setHeader("Content-Type", "text/html");
	
	if(kh != null)
	{
		int m = 0;
		try
		{  String khb;
			while(kh.next())
			{  
				khId = kh.getString("khId") ;
				khTen = kh.getString("khTen");
               // if(!khBgst.equals("0"))
                //{
				if(khTen.toUpperCase().startsWith(query.toUpperCase()) || khTen.toUpperCase().indexOf(query.toUpperCase()) >=0 
						|| khId.toUpperCase().startsWith(query.toUpperCase()) || khId.toUpperCase().indexOf(query.toUpperCase()) >=0)
				{
					String khachhang = khId + "--[" + khTen + "]";
					out.println(khachhang + "\n");
					
					m++;
					if(m >= 30)
						break;
				}
			}
			kh.close();
		}
		catch(SQLException e){}
		kh.close();
		db.shutDown();
	}
		
%>