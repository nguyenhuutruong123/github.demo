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
	
	String trakmId = (String) session.getAttribute("trakmId")==null?"":(String)session.getAttribute("trakmId");
	
	String dkkmId = (String) session.getAttribute("dkkmId")==null?"":(String)session.getAttribute("dkkmId");
	
	String userId = (String) session.getAttribute("userId");  
	String command = "select pk_seq,ma, ten from sanpham where trangthai = '1'  ";
	
	if(trakmId.length()>0)
		command+=" and pk_seq not in (select SANPHAM_FK from TRAKHUYENMAI_SANPHAM where TRAKHUYENMAI_FK='"+trakmId+"' )" ;
	
	if(dkkmId.length()>0)
		command+=" and pk_seq not in (select SANPHAM_FK from DIEUKIENKM_SANPHAM where DIEUKIENKHUYENMAI_FK='"+dkkmId+"' )" ;
	
	//System.out.println("[SpThem vao Km]"+command);
	
	ResultSet sp = db.get(command);
	
	
	response.setHeader("Content-Type", "text/html");
	request.setCharacterEncoding("UTF-8");
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
					
					String MASP = Ult.replaceAEIOU(sp.getString("ma"));
					String TENSP = Ult.replaceAEIOU(sp.getString("ten"));
					String spId = Ult.replaceAEIOU(sp.getString("pk_seq"));
					
					if(MASP.trim().toUpperCase().startsWith(query.trim().toUpperCase()) || MASP.trim().toUpperCase().indexOf(query.trim().toUpperCase()) >= 0 || TENSP.toUpperCase().indexOf(query.toUpperCase()) >= 0)
					{
						out.print("###" + maSP + " - " + tenSP + " - " + spId + "|");
						m++;
					}
				}
			}
			sp.close();
		}
		catch(SQLException e){e.printStackTrace();}
	}
	
	db.shutDown();
	
%>