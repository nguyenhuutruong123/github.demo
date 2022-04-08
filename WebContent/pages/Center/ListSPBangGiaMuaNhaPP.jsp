<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.distributor.db.sql.dbutils" %>
<%  String nhappid = (String) session.getAttribute("nhappid");
	String kenhid=(String)session.getAttribute("kenhid");
	String dvkdid=(String)session.getAttribute("dvkdid");
%>

<% dbutils db = new dbutils(); %>
<%
	String maSP = "";
	String tenSP = "";
	String dvtSP = "";
	String dongiaSP = "";
	
	//Lay Ma Nha Phan Phoi Tu userId
	

	String command = "";
			
	command="SELECT    b.PK_SEQ, b.MA, b.TEN, a.dongia, d.DONVI  FROM       "+
		 "  dbo.SANPHAM AS b INNER JOIN  (select SANPHAM_FK, giamuanpp AS dongia  FROM "+     
				  "   dbo.BGMUANPP_SANPHAM  d inner join BangGiaMuaNPP p on p.pk_seq=d.BGMuaNPP_fk "+ 
				 " inner join BANGGIAMUANPP_NPP npp on npp.Banggiamuanpp_fk=p.pk_seq where kenh_fk="+kenhid+" and dvkd_fk="+dvkdid+" and npp.npp_fk="+nhappid+" and p.trangthai=1 ) "+
				   " AS a ON a.SANPHAM_FK = b.PK_SEQ LEFT OUTER JOIN  dbo.DONVIDOLUONG AS d ON d.PK_SEQ = b.DVDL_FK WHERE     (b.TRANGTHAI = '1') ";
	
	//System.out.println("Sql : ListSpbangGiaMuaNhaPP HoaDonNew ; "+command);
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
					maSP = sp.getString("ma");
					tenSP = sp.getString("ten");
					dvtSP = sp.getString("donvi");
					dongiaSP = sp.getString("dongia");
		
					if(maSP.toUpperCase().startsWith(query.toUpperCase()) ||
						maSP.toUpperCase().indexOf(query.toUpperCase()) >= 0 || tenSP.toUpperCase().indexOf(query.toUpperCase()) >= 0 ||
						dvtSP.toUpperCase().indexOf(query.toUpperCase()) >= 0 )
					{
						if(tenSP.length() > 50)
							tenSP = tenSP.substring(0, 50);
						out.print("###" + maSP + " - " + tenSP +" [" + dvtSP + "] ["+ dongiaSP + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
						
						m++;
						if(m > 30)
							break;
					}
				}
			}
			sp.close();
		}
		catch(SQLException e){}
	}
	
	
%>