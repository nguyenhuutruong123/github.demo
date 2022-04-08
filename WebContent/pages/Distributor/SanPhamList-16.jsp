<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.distributor.db.sql.dbutils" %>
<% String userId = (String) session.getAttribute("userId"); %>
<% String khId = (String) session.getAttribute("khId"); %>

<%@ page import = "geso.dms.distributor.beans.donhang.ISanpham" %>
<%@ page import = "geso.dms.distributor.beans.donhang.imp.Sanpham" %>

<% //dbutils db = new dbutils(); %>
<%
	String maSP = "";
	String tenSP = "";
	String dvtSP = "";
	String dongiaSP = "";
	
	//Lay Ma Nha Phan Phoi Tu userId
	dbutils db =new dbutils();
	String nppId = "";
	ResultSet rs = db.get("select a.pk_seq from nhaphanphoi a, nhanvien b where b.dangnhap = a.ma and b.pk_seq ='" + userId + "'");
	if(rs != null)
	{
		rs.next();
		nppId = rs.getString("pk_seq");
		rs.close();
	}
	
		String command ="";
	command = "select DISTINCT a.ma, a.ten, c.giabanlenpp as dongia, isnull(b.donvi, 'Chua xac dinh') as donvi from sanpham a left join donvidoluong b on a.dvdl_fk = b.pk_seq ";
	command = command + "inner join bgbanlenpp_sanpham c on a.pk_seq = c.sanpham_fk ";
	command = command + "where c.bgbanlenpp_fk in (select distinct pk_seq from banggiabanlenpp where npp_fk = '" + nppId + "') and c.giabanlenpp > '0'";
	
	String bgstId = (String)session.getAttribute("bgstId");
	if(bgstId.length() >2)
	{
	command =" select sp.ma, sp.ten, dvdl.donvi,spbg.giasieuthi as dongia "+ 
			 " from BANGGIAST_SANPHAM spbg "+ 
			 " inner join (select * from BANGGIASIEUTHI where pk_seq in (select banggiasieuthi_fk from BGST_KHACHHANG where khachhang_fk='"+ khId +"') ) bg on bg.pk_seq = spbg.bgst_fk "+ 
			 " inner join sanpham sp on spbg.sanpham_fk = sp.pk_seq "+
			 " inner join donvidoluong dvdl on dvdl.pk_seq = sp.dvdl_fk ";
	}
	

	System.out.println("San pham:"+ command);
	
	ResultSet sp = db.get(command);
	if(sp != null)
	{
		while(sp.next())
		{
			maSP = maSP + sp.getString("ma") + ",";
			tenSP = tenSP + sp.getString("ten") + ",";
			dvtSP = dvtSP + sp.getString("donvi") + ",";
			dongiaSP = dongiaSP + sp.getString("dongia") + ",";
		}

		if(maSP.length() > 0)
		{
			maSP = maSP.substring(0, maSP.length() - 1);
			tenSP = tenSP.substring(0, tenSP.length() - 1);
			dvtSP = dvtSP.substring(0, dvtSP.length() - 1);
			dongiaSP = dongiaSP.substring(0, dongiaSP.length() - 1);
			
			String[] maspList = maSP.split(",");
			String[] tenspList = tenSP.split(",");
			String[] dvtspList = dvtSP.split(",");
			String[] dongiaspList = dongiaSP.split(",");
					
			response.setHeader("Content-Type", "text/html");
			String query = (String)geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("letters"));
			for(int i = 0;i< maspList.length;i++)
			{
				//countries[i] = countries[i].toLowerCase();
				if(maspList[i].toUpperCase().startsWith(query.toUpperCase()) ||
					maspList[i].toUpperCase().indexOf(query.toUpperCase()) >= 0 || tenspList[i].toUpperCase().indexOf(query.toUpperCase()) >= 0 ||
					dvtspList[i].toUpperCase().indexOf(query.toUpperCase()) >= 0 )
				{
					if(tenspList[i].length() > 50)
						tenspList[i] = tenspList[i].substring(0, 50);
					out.print("###" + maspList[i] + " - " + tenspList[i] +" [" + dvtspList[i] + "] ["+ dongiaspList[i] + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
				}
			}
		}	
		
		
	}
	sp.close();
	db.shutDown();
%>

