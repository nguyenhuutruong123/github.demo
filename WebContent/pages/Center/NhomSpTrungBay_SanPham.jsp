<%@page import="geso.dms.center.util.Utility"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.distributor.db.sql.dbutils" %>
<% dbutils db = new dbutils(); %>
<%
	String maSP = "";
	String tenSP = "";
	String userId = (String) session.getAttribute("userId");
	Utility Ult = new Utility();
	String command = "select ma, ten from sanpham where trangthai = '1' and pk_seq in "+Ult.quyen_sanpham(userId);
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
					maSP =  sp.getString("ma");
					tenSP = sp.getString("ten");
					
					if(maSP.trim().toUpperCase().startsWith(query.trim().toUpperCase()) ||
							maSP.trim().toUpperCase().indexOf(query.trim().toUpperCase()) >= 0 || tenSP.toUpperCase().indexOf(query.toUpperCase()) >= 0)
					{
						if(tenSP.length() > 50)
							tenSP = tenSP.substring(0, 50);
						out.print("###" + maSP + " - " + tenSP + "|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
						
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
	
	db.shutDown();
%>