<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.distributor.db.sql.dbutils" %>
<%  
	String nhappid = (String) session.getAttribute("nhappid");
	String kenhid=(String)session.getAttribute("kenhid");
	String dvkdid=(String)session.getAttribute("dvkdid");
	String donhangid=(String)session.getAttribute("donhangid");
%>

<% dbutils db = new dbutils(); %>
<%
	String maSP = "";
	String tenSP = "";
	String dvtSP = "";
	String dongiaSP = "";
	
	//Lay Ma Nha Phan Phoi Tu userId
	

	String command = "";
			
		/* command="SELECT    b.PK_SEQ, b.MA, b.TEN, isnull(a.dongia ,'0') as dongia, isnull(d.DONVI,'') as donvi  FROM       "+
			 "  dbo.SANPHAM AS b INNER JOIN  (select SANPHAM_FK, giamuanpp AS dongia  FROM "+     
					  "   dbo.BGMUANPP_SANPHAM  d inner join BangGiaMuaNPP p on p.pk_seq=d.BGMuaNPP_fk "+ 
					 " inner join BANGGIAMUANPP_NPP npp on npp.Banggiamuanpp_fk=p.pk_seq where kenh_fk="+kenhid+" and dvkd_fk="+dvkdid+" and npp.npp_fk="+nhappid+" and p.trangthai=1 ) "+
					   " AS a ON a.SANPHAM_FK = b.PK_SEQ LEFT OUTER JOIN  dbo.DONVIDOLUONG AS d ON d.PK_SEQ = b.DVDL_FK WHERE     (b.TRANGTHAI = '1') ";
 */
 	command=" SELECT    b.PK_SEQ, b.MA, b.TEN, isnull(a.dongia ,'0') as dongia, isnull(d.DONVI,'') as donvi ,(c.available+ isnull(ddh_sp.soluong,0)) as available     FROM "+
 		" dbo.SANPHAM AS b INNER JOIN  ( select SANPHAM_FK, giamuanpp AS dongia  FROM     "+
        	" 	dbo.BGMUANPP_SANPHAM  d inner join BangGiaMuaNPP p on p.pk_seq=d.BGMuaNPP_fk  inner join BANGGIAMUANPP_NPP "+
        		"  npp on npp.Banggiamuanpp_fk=p.pk_seq where kenh_fk="+kenhid+" and dvkd_fk="+dvkdid+" and npp.npp_fk="+nhappid+" and p.trangthai=1 ) "+
 		"  AS a ON a.SANPHAM_FK = b.PK_SEQ "+ 
        " 		inner join ( "+
        	" 	select kho.sanpham_fk,kho.available from nhaphanphoi npp "+  
        		" inner join "+
        		" erp_khott_sanpham kho  "+
        		" on  kho.khott_fk=npp.khosap "+ 
        		" where pk_seq=" +nhappid +
        	" 	) c on c.sanpham_fk=b.pk_seq "+
        		" LEFT  JOIN  dbo.DONVIDOLUONG AS d ON d.PK_SEQ = b.DVDL_FK "+
        		" left join (select soluong ,sanpham_fk from dondathang_sp where dondathang_fk="+donhangid+" ) ddh_sp  on ddh_sp.sanpham_fk=b.pk_seq  "+
        		" WHERE     (b.TRANGTHAI = '1') ";
 
	System.out.println("Sql : ListSpbangGiaMuaNhaPP DON HANG New ; "+command);
	ResultSet sp = db.get(command);
	response.setHeader("Content-Type", "text/html");
	String query = (String)geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("letters"));
	
	if(sp != null)
	{
		int m = 0;
		try
		{
			while(sp.next())
			{
				if(sp.getString("ma") != null)
				{
				
					if(sp.getString("ma").toUpperCase().startsWith(query.toUpperCase()) ||sp.getString("ma").toUpperCase().indexOf(query.toUpperCase()) >= 0 
							|| sp.getString("ten").toUpperCase().indexOf(query.toUpperCase()) >= 0 || sp.getString("donvi").toUpperCase().indexOf(query.toUpperCase()) >= 0 )
					{
					tenSP=sp.getString("ten");
						if(tenSP.length() > 30)
							tenSP = tenSP.substring(0, 30);
						out.print("###" + sp.getString("ma") + " - " + tenSP+" [" +  sp.getString("donvi") + "] ["+ sp.getString("dongia") + "] ["+ sp.getString("available") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
						m++;
						/* if(m > 20)
							break; */
					}
				}
			}
			sp.close();
			db.shutDown();
		}
		catch(SQLException e){}
	}
	
	
%>