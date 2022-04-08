<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.distributor.db.sql.dbutils" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page import = "java.net.URLDecoder" %>


<%
	dbutils db = new dbutils();
	try
	{
		/* response.setHeader("Content-Type", "text/html");
		String query = "";
		if(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("letters")) != null)
			query = (String)geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("letters"));
		
		System.out.println("Query: " + query); */
		
		request.setCharacterEncoding("UTF-8");	
		response.setHeader("Content-Type", "text/html; charset=UTF-8");			
	 	
		/* String query = (String)request.getQueryString(); 
	   	
	   	query = new String(query.substring(query.indexOf("&letters=") + 9, query.length()).getBytes("UTF-8"), "UTF-8");
	   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
	   	
	   	Utility Ult = new Utility();
	   	query = Ult.replaceAEIOU(query); */

	   	String query = (String)request.getQueryString(); 
	   	
	   	query = new String(query.substring(query.indexOf("&letters=") + 9, query.length()).getBytes("UTF-8"), "UTF-8");
	   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
	   	
	   	Utility Ult = new Utility();
	   	query = Ult.replaceAEIOU(query);
		String command = " SELECT top 50  SP.PK_SEQ, isnull(SP.MA, '') as MA, isnull(SP.MACHITIET, '') as MACHITIET, isnull(SP.TEN, '')  + ' ' "+ 
		                 "       + case when substring(LTRIM(isnull(SP.QUYCACH,'')), 1, 1 ) = 'x' then STUFF(LTRIM(isnull(SP.QUYCACH,'')),1,1,'') else isnull(SP.QUYCACH,'') end + "+
		                 "       ' x ' + isnull(sp.mau,'') + ' x ' +DV.DONVI AS TEN " +
		 				 " FROM ERP_SANPHAM SP "+
		 				 " INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = SP.DVDL_FK "+
		 				 " WHERE SP.CONGTY_FK = '" + (String) session.getAttribute("congtyId") + "' "+
		 				 " and sp.trangthai='1' and sp.timkiem like N'%"+query+"%' "; 
		 
		
		ResultSet sp = db.get(command);
		int j = 0;
		if(sp != null)
		{
			while(sp.next())
			{
				String masp = Ult.replaceAEIOU(sp.getString("MA"));
				String mactsp = Ult.replaceAEIOU(sp.getString("MACHITIET"));
				String tensp = Ult.replaceAEIOU(sp.getString("TEN"));
 
					out.print("###" + sp.getString("MACHITIET") + " - " + sp.getString("ten")  + "] [" + sp.getString("pk_seq") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
 
			}	
			sp.close();
		}
		
		db.shutDown();
		
	}
	catch(Exception ex)
	{ 
		System.out.println("Xay ra exception roi ban..." + ex.getMessage()); 
	}
%>

