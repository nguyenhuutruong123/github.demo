<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@page import="java.sql.SQLException"%>
<%@ page  import = "geso.dms.distributor.db.sql.dbutils" %>
<% dbutils db = new dbutils(); %>
<%
	String maSP = "";
	String tenSP = "";
	String dongia = "";
		
	String command = "select a.ma, a.ten, b.giamuanpp as gblc from sanpham a inner join bgmuanpp_sanpham b on a.pk_seq = b.sanpham_fk ";
	ResultSet sp = db.get(command);
	
	response.setHeader("Content-Type", "text/html");
	String query = (String)geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("letters"));
	
	if(sp != null)
	{
		int m = 0;
		try
		{
			while(sp.next())
			{
				maSP = sp.getString("ma");
				tenSP = sp.getString("ten");
				dongia = sp.getString("gblc");
			
				if(maSP.trim().toUpperCase().startsWith(query.trim().toUpperCase()) || maSP.trim().toUpperCase().indexOf(query.trim().toUpperCase()) >= 0 ||
						tenSP.trim().toUpperCase().startsWith(query.trim().toUpperCase()) || tenSP.trim().toUpperCase().indexOf(query.toUpperCase()) >= 0)
				{
					if(tenSP.length() > 50)
						tenSP = tenSP.substring(0, 50);
					out.print("###" + maSP + " - " + tenSP + " [" + dongia + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
					
					m++;
					if(m >= 30)
						break;
				}
			}
		}
		catch(SQLException e){}
	}
	
	
%>