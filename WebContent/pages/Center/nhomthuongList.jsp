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
	String maNhom = "";
	String tenNhom = "";
	
	Utility Ult = new Utility();
	
	response.setHeader("Content-Type", "text/html");
	request.setCharacterEncoding("UTF-8");
	
   	String query = (String)request.getQueryString(); 
   	String view = query;
   	System.out.println("Query String: " + query);
   	
   	query = new String(query.substring(query.indexOf("&letters=") + 9, query.length()).getBytes("UTF-8"), "UTF-8");
   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
   	query = Ult.replaceAEIOU(query);
    
   	String command = "select pk_seq, ten from nhomthuong where trangthai = '1' ";

    System.out.println("Lay Nhom SP: " + command + " - Query la: " + query);
	
	ResultSet dkkm = db.get(command);
	
	if(dkkm != null)
	{
		int m = 0;
		try
		{
			while(dkkm.next())
			{
				String maDkkm =  dkkm.getString("pk_seq");
				String ten = dkkm.getString("ten") ;
				
				String madkkm = Ult.replaceAEIOU(maDkkm);
				String tennhom = Ult.replaceAEIOU(ten);
			
				if(madkkm.trim().toUpperCase().startsWith(query.trim().toUpperCase()) ||
					madkkm.trim().toUpperCase().indexOf(query.trim().toUpperCase()) >= 0 || tennhom.toUpperCase().indexOf(query.toUpperCase()) >= 0)
				{
					System.out.println("Nhom: " + ten);
					out.print("###" + "[" + maDkkm + "] - " + ten + "|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
					m++;
				} 
			}
			dkkm.close();
		}
		catch(SQLException e){}
	}	
     
	db.shutDown();
%>