<%@page import="geso.dms.center.util.Utility"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.distributor.db.sql.dbutils" %>
<%@ page import = "java.net.URLDecoder" %>
<% dbutils db = new dbutils(); %>
<%
	String maSP = "";
	String tenSP = "";
	String dongia = "";
	String userId = (String) session.getAttribute("userId");  
	Utility Ult = new Utility();
	String command = "\n select ma, ten from sanpham " +
	"\n where trangthai = '1' " +
	"\n and pk_seq in " + Ult.quyen_sanpham(userId) ;
	System.out.println("Chuoi san pham tra khuyen mai la: " + command + "\n");
	
	ResultSet sp = db.get(command);
	
	/* response.setHeader("Content-Type", "text/html");
	String query = (String)geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("letters")); */
	
	response.setHeader("Content-Type", "text/html");
	request.setCharacterEncoding("UTF-8");
	
	//String query = (String)geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("letters")); 
   	String query = (String)request.getQueryString(); 
   	
   	query = new String(query.substring(query.indexOf("&letters=") + 9, query.length()).getBytes("UTF-8"), "UTF-8");
   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");

   	query = Ult.replaceAEIOU(query);
	
	if(sp != null)
	{
		int m = 0;
		try
		{
			while(sp.next())
			{
				maSP =  sp.getString("ma");
				tenSP = sp.getString("ten");
				
				String MASP = Ult.replaceAEIOU(sp.getString("ma"));
				String TENSP = Ult.replaceAEIOU(sp.getString("ten"));
				
				if(MASP.trim().toUpperCase().startsWith(query.trim().toUpperCase()) ||
						MASP.trim().toUpperCase().indexOf(query.trim().toUpperCase()) >= 0 || TENSP.toUpperCase().indexOf(query.toUpperCase()) >= 0)
				{
					if(tenSP.length() > 50)
						tenSP = tenSP.substring(0, 50);
					out.print("###" + maSP + " - " + tenSP + "|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
					
					m++;
				}
			}
			sp.close();
		}
		catch(SQLException e){}
	}
	db.shutDown();
	
%>