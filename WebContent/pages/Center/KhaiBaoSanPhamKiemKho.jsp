<%@page import="geso.dms.center.util.Utility"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.distributor.db.sql.dbutils" %>
<%@ page import = "java.net.URLDecoder" %>

<%
	dbutils db = new dbutils();
	String maSP = "";
	String tenSP = "";
	Utility Ult = new Utility();
	String userId = (String) session.getAttribute("userId");  
	String command = "select a.pk_seq as id, a.ma, a.ten,b.donvi from sanpham a left join DONVIDOLUONG b on a.dvdl_fk = b.pk_seq  where a.trangthai = '1' ";
	ResultSet sp = db.get(command);
	System.out.println("Lay san pham "+command);
	
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
		try
		{
			int m = 0;
			while(sp.next())
			{
				if(sp.getString("ma") != null)
				{
					maSP =  sp.getString("ma");
					tenSP = sp.getString("ten");
					String id = Ult.replaceAEIOU(sp.getString("id"));
					String MASP = Ult.replaceAEIOU(sp.getString("ma"));
					String TENSP = Ult.replaceAEIOU(sp.getString("ten"));
					String donvi = sp.getString("donvi");
					
					if(MASP.trim().toUpperCase().startsWith(query.trim().toUpperCase()) ||
							MASP.trim().toUpperCase().indexOf(query.trim().toUpperCase()) >= 0 || TENSP.toUpperCase().indexOf(query.toUpperCase()) >= 0)
					{
						//if(tenSP.length() > 50)
							//tenSP = tenSP.substring(0, 50);
						out.print("###"+ id + " - " + maSP + " - " + tenSP + " - " + donvi +  "|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
						
						m++;
					}
				}
			}
			sp.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	db.shutDown();
	
%>