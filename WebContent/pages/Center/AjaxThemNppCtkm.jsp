<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.distributor.db.sql.dbutils" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page import = "java.net.URLDecoder" %>


<% dbutils db = new dbutils(); %>
<%
	String maSP = "";
	String tenSP = "";
	String dvtSP = "";
	String dongiaSP = "";
	
	//Lay Ma Nha Phan Phoi Tu userId
	

	String command = "";
	
	
 	command=
 	"	select PK_SEQ as ctkmId,SCHEME,TUNGAY,DENNGAY,DIENGIAI,LOAINGANSACH from CTKHUYENMAI a where 1=1 ";
 
	response.setHeader("Content-Type", "text/html");
	request.setCharacterEncoding("UTF-8");
   	String query = (String)request.getQueryString(); 
   	
   	query = new String(query.substring(query.indexOf("&letters=") + 9, query.length()).getBytes("UTF-8"), "UTF-8");
   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
   	
   	Utility Ult = new Utility();
   	query = Ult.replaceAEIOU(query);
   	
   //	command += " and a.SCHEME+ a.diengiai like '%"+query+"%'  ";
   	
   	System.out.println("Sql : ListSpbangGiaMuaNhaPP DON HANG New ; "+command);
   	
	ResultSet sp = db.get(command);
	
	if(sp != null)
	{
		int m = 0;
		try
		{
			while(sp.next())
			{
				if(sp.getString("ctkmId") != null)
				{
					String masp = Ult.replaceAEIOU(sp.getString("scheme"));
					String tensp = Ult.replaceAEIOU( masp + sp.getString("diengiai")  );
					String donvi = Ult.replaceAEIOU(sp.getString("loaingansach"));					
				/* 	if(masp.toUpperCase().startsWith(query.toUpperCase()) ||masp.toUpperCase().indexOf(query.toUpperCase()) >= 0 
							|| tensp.toUpperCase().indexOf(query.toUpperCase()) >= 0 || donvi.toUpperCase().indexOf(query.toUpperCase()) >= 0 )
					{ */
						tenSP=sp.getString("diengiai");
						out.print("###" + sp.getString("scheme") + " - " + tenSP+" [" +  sp.getString("loaingansach") + "] ["+ sp.getString("ctkmId") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
						//System.out.println("###" + sp.getString("scheme") + " - " + tenSP+" [" +  sp.getString("loaingansach") + "] ["+ sp.getString("ctkmId") + "]|");
						m++;
				//	}
				}
			}
			sp.close();
			db.shutDown();
		}
		catch(SQLException e){e.printStackTrace();}
	}
	
	
%>