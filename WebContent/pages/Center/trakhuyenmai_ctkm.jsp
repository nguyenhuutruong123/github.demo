<%@page import="geso.dms.center.util.Utility"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.distributor.db.sql.dbutils" %>

<% String dkkmDiengiai = (String) session.getAttribute("dkkmDiengiai"); %>
<% String dkkmTungay = (String) session.getAttribute("dkkmTungay"); %>
<% String dkkmDenngay = (String) session.getAttribute("dkkmDenngay"); %>
<% //String type = (String) session.getAttribute("type"); %>

<% dbutils db = new dbutils(); %>
<%
	String maDkkm = "";
	String diengiai = "";
	String tongluong = "";
	String tongtien = "";
	String command;
	String userId = (String) session.getAttribute("userId");
	Utility Ult = new Utility();

    command = "select top(200) pk_seq as dkkmId, diengiai, isnull(tongluong, 0) as tongluong, isnull(tongtien, 0) as tongtien ";
    command = command + " from dieukienkhuyenmai where pk_seq in (select dieukienkhuyenmai_fk from DIEUKIENKM_SANPHAM where sanpham_fk in "+ Ult.quyen_sanpham(userId) +")";
		
	if(dkkmDiengiai != null)
	{
		if(dkkmDiengiai.length() > 0)
			command = command + " and Upper(diengiai) like Upper('%" + dkkmDiengiai + "%') ";
	}
	if(dkkmTungay != null)
	{
		if(dkkmTungay.length() > 0)
			command = command + " and ngaytao >= '" + dkkmTungay + "'";
	}
	if(dkkmDenngay != null)
	{
		if(dkkmDenngay.length() > 0)
			command = command + " and ngaytao <= '" + dkkmDenngay + "'";
	}
	
	ResultSet dkkm = db.get(command);
	
	response.setHeader("Content-Type", "text/html");
	String query = (String)geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("letters"));
	
	if(dkkm != null)
	{
		int m = 0;
		try
		{
			while(dkkm.next())
			{
				if(dkkm.getString("dkkmId") != null)
				{
					maDkkm =  dkkm.getString("dkkmId") ;
					diengiai = dkkm.getString("diengiai") ;
					tongluong =  dkkm.getString("tongluong") ;
					tongtien =  dkkm.getString("tongtien") ;
				
					if(maDkkm.trim().toUpperCase().startsWith(query.trim().toUpperCase()) ||
							maDkkm.trim().toUpperCase().indexOf(query.trim().toUpperCase()) >= 0 || diengiai.toUpperCase().indexOf(query.toUpperCase()) >= 0)
					{
						if(diengiai.length() > 50)
							diengiai = diengiai.substring(0, 50);
						out.print("###" + maDkkm + " - " + diengiai + " [" +tongluong + "] [" + tongtien + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
						
						m++;
						if(m > 30)
							break;
					}
				}
			}
			dkkm.close();
		}
		catch(SQLException e){}
	}	
	db.shutDown();
%>