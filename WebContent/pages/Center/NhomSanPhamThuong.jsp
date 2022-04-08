<%@ page import="geso.dms.center.util.Utility"%>
<%@ page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page import = "java.net.URLDecoder" %>
<%@ page  import = "geso.dms.distributor.db.sql.dbutils" %>

<% dbutils db = new dbutils(); %>
<%	
	String command;
	String userId = (String) session.getAttribute("userId");
	Utility Ult = new Utility();
	
	response.setHeader("Content-Type", "text/html");
	request.setCharacterEncoding("UTF-8");
	
   	String query = (String)request.getQueryString(); 
   	String view = query;
   	System.out.println("Query String: " + query);
   	
   	query = new String(query.substring(query.indexOf("&letters=") + 9, query.length()).getBytes("UTF-8"), "UTF-8");
   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
   	query = Ult.replaceAEIOU(query);
    
    
	command = "select ma, ten from sanpham where trangthai = '1'";
	System.out.println("___Lay SanPham: " + command);
	ResultSet sp = db.get(command);
	
	if(sp != null)
	{
		try
		{
			int m = 0;
			while(sp.next())
			{
				if(sp.getString("ma") != null)
				{
					String maSP =  sp.getString("ma");
					String tenSP = sp.getString("ten");
					
					String masp =  Ult.replaceAEIOU(sp.getString("ma"));
					String tensp = Ult.replaceAEIOU(sp.getString("ten"));
					
					if(masp.trim().toUpperCase().startsWith(query.trim().toUpperCase()) ||
							masp.trim().toUpperCase().indexOf(query.trim().toUpperCase()) >= 0 || tensp.toUpperCase().indexOf(query.toUpperCase()) >= 0)
					{
						if(tenSP.length() > 50)
							tenSP = tenSP.substring(0, 50);
						out.print("###" + maSP + " - " + tenSP + "|"); 
						
						m++;
						if(m > 40)
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