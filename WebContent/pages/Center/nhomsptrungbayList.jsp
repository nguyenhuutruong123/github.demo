<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.distributor.db.sql.dbutils" %>

<% String nsptbDiengiai = (String) session.getAttribute("nsptbDiengiai"); %>
<% String nsptbTungay = (String) session.getAttribute("nsptbTungay"); %>
<% String nsptbDenngay = (String) session.getAttribute("nsptbDenngay"); %>

<% dbutils db = new dbutils(); %>
<%
		
	String command = "select top(200) pk_seq as nsptbId, diengiai, isnull(tongluong, 0) as tongluong, isnull(tongtien, 0) as tongtien ";
	command = command + " from nhomsptrungbay where pk_seq > '0' ";
	if(nsptbDiengiai != null)
	{
		if(nsptbDiengiai.length() > 0)
			command = command + " and Upper(diengiai) like Upper('%" + nsptbDiengiai + "%') ";
	}
	if(nsptbTungay != null)
	{
		if(nsptbTungay.length() > 0)
			command = command + " and ngaytao >= '" + nsptbTungay + "'";
	}
	if(nsptbDenngay != null)
	{
		if(nsptbDenngay.length() > 0)
			command = command + " and ngaytao <= '" + nsptbDenngay + "'";
	}
	
	ResultSet nsptb = db.get(command);
	
	if(nsptb != null)
	{
		while(nsptb.next())
		{
			response.setHeader("Content-Type", "text/html");
			String query = (String)geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("letters"));
			
				//countries[i] = countries[i].toLowerCase();
				if(nsptb.getString("nsptbId").trim().toUpperCase().startsWith(query.trim().toUpperCase()) ||
						nsptb.getString("nsptbId").trim().toUpperCase().indexOf(query.trim().toUpperCase()) >= 0 || nsptb.getString("diengiai").toUpperCase().indexOf(query.toUpperCase()) >= 0)
				{
					String diengiai = nsptb.getString("diengiai");
					if(diengiai.length() > 50)
						diengiai = diengiai.substring(0, 50);
					out.print("###" + nsptb.getString("nsptbId") + " - " + nsptb.getString("diengiai") + " [" + nsptb.getString("tongluong") + "] [" + nsptb.getString("tongtien") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
				}
		}	
	}	
%>