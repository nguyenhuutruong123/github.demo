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
	String nhappid = (String) session.getAttribute("nhappid");
	String kenhid=(String)session.getAttribute("kenhid");
	String dvkdid=(String)session.getAttribute("dvkdid");
	String donhangid=(String)session.getAttribute("donhangid");
	String khoId=(String)session.getAttribute("khoId");
	String ngaydc =(String)session.getAttribute("ngaydc");
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
			
 	command =
 	"	select top(10) sp.pk_seq as spId,sp.MA as spMa, sp.TEN as spTen,sp.DVDL_FK as dvdlId,dvdl.DONVI as dvdlTen,kho.available as TonKho,qc.soluong1,qc.soluong2 \n"+
	"		, isnull( ( select GIAMUANPP from BGMUANPP_SANPHAM bgsp  \n"+
	"				where SANPHAM_FK = sp.pk_seq and BGMUANPP_FK in   \n"+
	"			(		select top(1) PK_SEQ from BANGGIAMUANPP bg   \n"+
	"					inner join BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK 		 \n"+							
	"				where bg.TRANGTHAI = '1' and bg_npp.NPP_FK = '"+nhappid+"'  and bg.DVKD_FK = sp.dvkd_Fk   \n"+
	"				and bg.KENH_FK = '"+kenhid+"'    \n"+
	"			order by bg.TUNGAY desc )   \n"+
	"		),0 )  as dongia		  \n"+
 	"		from  \n"+
 	"		dbo.sanpham as sp  \n"+  
 	"	LEFT join DONVIDOLUONG dvdl on dvdl.PK_SEQ=sp.DVDL_FK \n"+ 
 	"   LEFT join quycach qc on qc.sanpham_fk=sp.pk_seq and qc.dvdl1_fk=sp.dvdl_fk	and qc.dvdl2_fk =100018 \n"+
 	
 	"\n  cross apply" +
    "\n  ( 	select KHO_FK,NPP_FK,SANPHAM_FK,sum(AVAILABLE) as AVAILABLE, KBH_FK " +
    "\n 		from NHAPP_KHO_CHITIET " +
    "\n  	where  SANPHAM_FK = sp.pk_seq and  NPP_FK =" + nhappid + " and ngaynhapkho <='" + ngaydc + "'  and kho_fk =  '"+khoId+"'  and KBH_FK = " + kenhid +
    "\n   	group by KHO_FK,NPP_FK,SANPHAM_FK, KBH_FK " +
    "\n  ) kho " +		
 	
 	
 	"  where (sp.ma like '%"+Ult.replaceAEIOU(query)+"%' or dbo.ftBoDau( sp.ten) like  N'%"+Ult.replaceAEIOU(query)+"%') and sp.dvkd_fk = '"+dvkdid+"'  ";
 
 	
 	
	System.out.println("Sql : ListSpbangGiaMuaNhaPP DON HANG New ; "+command);
	System.out.println("==============");
 	System.out.println("&&&&&&&&&&&&&&" + query);
 	System.out.println("**************");
	ResultSet sp = db.get(command);
	if(sp != null)
	{
		int m = 0;
		try
		{
			while(sp.next())
			{				
				System.out.println("=====spTen======" + sp.getString("spMa"));
				System.out.println("=====spTen======" + sp.getString("spTen"));
				System.out.println("&&&&&&&&&&&&&&" + query);
				if(sp.getString("spMa") != null)
				{
				
					
					if(sp.getString("spMa").toUpperCase().startsWith( query.toUpperCase()) || sp.getString("spMa").toUpperCase().indexOf(query.toUpperCase()) >= 0 
							|| Ult.replaceAEIOU(sp.getString("spTen")).toUpperCase().startsWith( query.toUpperCase())  || Ult.replaceAEIOU(sp.getString("spTen")).toUpperCase().indexOf(query.toUpperCase()) >= 0 )
					{
						
						
					tenSP=sp.getString("spTen");
					int soluong1 = sp.getInt("soluong1")==0?1:sp.getInt("soluong1");
					int soluong2 = sp.getInt("soluong2")==0?1:sp.getInt("soluong2");
					int quycach =soluong1/soluong2;
					
						if(tenSP.length() > 30)
							tenSP = tenSP.substring(0, 30);
						out.print("###" + sp.getString("spMa") + " - " + tenSP+" [" +  sp.getString("dvdlId") + "] ["+ sp.getString("dongia") + "] ["+ sp.getString("spId") + "] ["+ formatter2.format(sp.getDouble("tonkho")) + "] [" + Integer.toString(quycach) + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
						m++;
					}
				} 
			}
			sp.close();
			db.shutDown();
		}
		catch(SQLException e){e.printStackTrace();}
	}
%>