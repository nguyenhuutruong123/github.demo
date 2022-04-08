<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.distributor.db.sql.dbutils" %>
<% String userId = (String) session.getAttribute("userId"); %>

<% dbutils db = new dbutils(); %>
<%
	String maSP = "";
	String tenSP = "";
	String dvtSP = "";	
	String command = "";
			
	command = "select a.pk_seq, ma, ten, donvi from sanpham a inner join donvidoluong b on a.dvdl_fk = b.pk_seq  where a.trangthai = '1'";	
	ResultSet sp = db.get(command);
	
	response.setHeader("Content-Type", "text/html");
	String query = (String)geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("letters"));
	
	if(sp != null)
	{
		try
		{
			int m = 0;
			while(sp.next())
			{
				if(sp.getString("ma") != null)
				{
					maSP = sp.getString("ma");
					tenSP = sp.getString("ten");
					dvtSP = sp.getString("donvi");
					
					if( maSP.toUpperCase().startsWith(query.toUpperCase()) ||
							maSP.toUpperCase().indexOf(query.toUpperCase()) >= 0 || tenSP.toUpperCase().indexOf(query.toUpperCase()) >= 0 ||
						dvtSP.toUpperCase().indexOf(query.toUpperCase()) >= 0 )
					{
						if(tenSP.length() > 50)
							tenSP = tenSP.substring(0, 50);
						out.print("###" + maSP + " - " + tenSP +" [" + dvtSP + "] |"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
						
						m++;
						if(m >= 30)
							break;
					}
				}
			}
			sp.close();
		}
		catch(SQLException e){}
	}
	
	
%>