<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.distributor.db.sql.dbutils" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page import = "java.net.URLDecoder" %>
<%  
	NumberFormat formatter2 = new DecimalFormat("#,###,###.###");
	String nhappid = (String) session.getAttribute("erpKhId");
	String kenhid=(String)session.getAttribute("kenhid");
	String dvkdid=(String)session.getAttribute("dvkdid");
	String donhangid=(String)session.getAttribute("donhangid");
	String khoId=(String)session.getAttribute("khoId");
%>

<% dbutils db = new dbutils(); %>
<%
	String maSP = "";
	String tenSP = "";
	String dvtSP = "";
	String dongiaSP = "";
	response.setHeader("Content-Type", "text/html");
	request.setCharacterEncoding("UTF-8");
   	String query = (String)request.getQueryString(); 	
   	query = new String(query.substring(query.indexOf("&letters=") + 9, query.length()).getBytes("UTF-8"), "UTF-8");
   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
   	
   	Utility Ult = new Utility();
   	query = Ult.replaceAEIOU(query);
	String command = "";
	int j=0;
	String chuoi=" sp.timkiem  like '%"+query+"%' ";
	   if(query.length() <5 && (query.substring(0,1).equals("M") || query.substring(0,1).equals("W")))
	   {
		   chuoi=" sp.ma  like '"+query+"%' ";
	   }		
	
 	command=
 	"	select  sp.*, dvdl.DONVI as dvdlTen \n"+ 
 	"	from   \n"+
 	"	(	  \n"+
 	"		select PK_SEQ as spId ,ma as spMa,TEN as spTen,DVDL_FK as dvdlId ,TIMKIEM from  dbo.sanpham sp  where "+chuoi  + "  \n"+ 
 	"	)sp  \n"+
 	"	inner join DONVIDOLUONG dvdl on dvdl.PK_SEQ=sp.dvdlId  \n";
 	

	System.out.println("Sql : ListSpbangGiaMuaNhaPP DON HANG New ; "+command);
	ResultSet sp = db.get(command);	
	if(sp != null)
	{
		int m = 0;
		try
		{
			while(sp.next())
			{
				if(sp.getString("spMa") != null)
				{
						tenSP=sp.getString("spTen");
						out.print("###" + sp.getString("spMa") + " - " + tenSP+" ["+ sp.getString("spId") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
						j++;
				}
			}
			sp.close();
		}
		catch(SQLException e){e.printStackTrace();}
	}
	
	if(j==1)
	{
		command=
				
			"	select  sp.*, dvdl.DONVI as dvdlTen \n"+ 
		 	"	from   \n"+
		 	"	(	  \n"+
		 		 	"		select PK_SEQ as spId ,ma as spMa,TEN as spTen,DVDL_FK as dvdlId ,TIMKIEM from sanpham  where  substring(ten,0,charindex(' ',ten,0 ) ) \n"+ 
		 			"	in (  select    substring(ten,0,charindex(' ',ten,0 ) )  from sanpham where ma =  '"+query+"' ) and ma <>'"+query+"'  \n"+ 
		 	"	)sp  \n"+
		 	"	inner join DONVIDOLUONG dvdl on dvdl.PK_SEQ=sp.dvdlId  \n";
				
			System.out.println("Sql : ListSpbangGiaMuaNhaPP DON HANG New ; "+command);
	 		sp = db.get(command);	
	 		if(sp != null)
	 		{
	 			int m = 0;
	 			try
	 			{
	 				while(sp.next())
	 				{
	 					if(sp.getString("spMa") != null)
	 					{
	 						tenSP=sp.getString("spTen");
	 						out.print("###" + sp.getString("spMa") + " - " + tenSP+" ["+ sp.getString("spId") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
							j++;
	 					}
	 				}
	 				sp.close();
	 			}
	 			catch(SQLException e){e.printStackTrace();}
	 		}
	}
	if(db!=null)db.shutDown();
	
%>