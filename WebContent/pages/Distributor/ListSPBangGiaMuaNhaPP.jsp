<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.distributor.db.sql.dbutils" %>

<%@ page  import = "geso.dms.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>



<% dbutils db = new dbutils(); %>
<%
	String maSP = "";
	String tenSP = "";
	String dvtSP = "";
	String dongiaSP = "";

	String command = "";	
	//Sua lai cach ket de lay bang gia cho ket nhan vien voi nha phan phoi,ket convsitecode cua nhan vien void sitecode cua nha phan phoi
	command="SELECT    b.PK_SEQ, b.MA, b.TEN, a.dongia, isnull(d.DONVI,'Chưa xác đinh') as DonVi  FROM       "+
	 "  dbo.SANPHAM AS b INNER JOIN  (select SANPHAM_FK, giamuanpp AS dongia  FROM "+     
			  "   dbo.BGMUANPP_SANPHAM  d inner join BangGiaMuaNPP p on p.pk_seq=d.BGMuaNPP_fk "+ 
			 " inner join BANGGIAMUANPP_NPP npp on npp.Banggiamuanpp_fk=p.pk_seq where  npp.npp_fk = (select a.pk_seq from nhaphanphoi a, nhanvien b where b.convsitecode = a.sitecode and b.pk_seq ='"+userId+"') and p.trangthai=1 ) "+
			   " AS a ON a.SANPHAM_FK = b.PK_SEQ LEFT  JOIN  dbo.DONVIDOLUONG AS d ON d.PK_SEQ = b.DVDL_FK WHERE     (b.TRANGTHAI = '1') ";
	
	ResultSet sp = db.get(command);
	System.out.println(command);
	response.setHeader("Content-Type", "text/html");
	String query = (String)geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("letters"));
	
	if(sp != null)
	{
		int m = 0;
		try
		{
			while(sp.next())
			{
				maSP = sp.getString("ma");
				tenSP = sp.getString("ten");
				dvtSP = sp.getString("donvi");
				dongiaSP = sp.getString("dongia");
			
				if(maSP.toUpperCase().startsWith(query.toUpperCase()) || maSP.toUpperCase().indexOf(query.toUpperCase()) >= 0 
					|| tenSP.toUpperCase().indexOf(query.toUpperCase()) >= 0 || dvtSP.toUpperCase().indexOf(query.toUpperCase()) >= 0 )
				{
					if(tenSP.length() > 50)
						tenSP = tenSP.substring(0, 50);
					
					out.print("###" + maSP + " - " + tenSP +" [" + dvtSP + "] ["+ dongiaSP + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
					
					if(m >= 30)
						break;
				}
			}
			sp.close();
			db.shutDown();
		}
		catch(SQLException e){}
	}
	}
	
	
%>