<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.distributor.db.sql.dbutils" %>

<% String userId = (String) session.getAttribute("userId"); %>
<% String ddkdId = (String) session.getAttribute("ddkdId"); %>
<% dbutils db = new dbutils(); %>
<%
	//thay bang cac gia tri lay tu ResualSet
	String smartId = "";
	String khId = "";
	String khTen = "";
	String khChietKhau = "";
	System.out.println("I am here");
	//Lay Ma Nha Phan Phoi Tu userId
	String nppId = "";	ResultSet rs = db.get("select a.pk_seq from nhaphanphoi a, nhanvien b where b.dangnhap = a.ma and b.pk_seq ='" + userId + "'");
	if(rs != null)
	{
		rs.next();
		nppId = rs.getString("pk_seq");
		rs.close();
	}
	String command="";
	//if(ddkdId !=null)
	//{
	 command = "select substring(b.smartid, charindex('_', b.smartid)+1, len(b.smartid)) as smartid, b.pk_seq as khId, b.ten as khTen, c.chietkhau "+ 
					 "from khachhang_tuyenbh a inner join khachhang b on a.khachhang_fk = b.pk_seq "+ 
					 "inner join tuyenbanhang d on a.tbh_fk = d.pk_seq "+
					 "left join mucchietkhau c on b.chietkhau_fk = c.pk_seq "+ 
					 "where b.npp_fk='"+ nppId +"' and d.ddkd_fk= '"+ ddkdId +"' order by smartid, khId, khTen, chietkhau";
					 //"where b.npp_fk='102433' and d.ddkd_fk= '100784' order by smartid, khId, khTen, chietkhau";
/*	}
	else
	{
		 command = "select substring(b.smartid, charindex('_', b.smartid)+1, len(b.smartid)) as smartid, b.pk_seq as khId, b.ten as khTen, c.chietkhau "+ 
		 "from khachhang_tuyenbh a inner join khachhang b on a.khachhang_fk = b.pk_seq "+ 
		 "inner join tuyenbanhang d on a.tbh_fk = d.pk_seq "+
		 "left join mucchietkhau c on b.chietkhau_fk = c.pk_seq "+ 
		 "where b.npp_fk='"+ nppId +"' order by smartid, khId, khTen, chietkhau ";

	}*/
//	String command = "select a.pk_seq as khId, a.ten as khTen, b.chietkhau from khachhang a left join mucchietkhau b on a.chietkhau_fk = b.pk_seq where a.npp_fk='" + nppId + "'";
//	if(tbhId != null && tbhId != "")
//		command = "select b.pk_seq as khId, b.ten as khTen, c.chietkhau from khachhang_tuyenbh a inner join khachhang b on a.khachhang_fk = b.pk_seq left join mucchietkhau c on b.chietkhau_fk = c.pk_seq where b.npp_fk='" + nppId + "' and a.tbh_fk='" + tbhId + "'";
	
	System.out.println(command);
	ResultSet kh = db.get(command);
	
	if(kh != null)
	{
		while(kh.next())
		{
			
			khId = khId + kh.getString("smartid")+ "-"+ kh.getString("khId") + ",";
			System.out.println(khId);
			khTen = khTen + kh.getString("khTen") + ",";
			
			if (kh.getString("chietkhau") != null && !kh.wasNull())
				khChietKhau = khChietKhau + kh.getString("chietkhau") + ",";
			else
				khChietKhau = khChietKhau + "0,";
			
		}
	}
	khId = khId.substring(0, khId.length() - 1);
	khTen = khTen.substring(0, khTen.length() - 1);
	khChietKhau = khChietKhau.substring(0, khChietKhau.length() - 1);
		
	String[] khIdList = khId.split(",");
	String[] khTenList = khTen.split(",");
	String[] khChietKhauList = khChietKhau.split(",");
	
	String query = (String)geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("q"));
	//System.out.println("1"+request.getParameterNames().nextElement());
	response.setHeader("Content-Type", "text/html");
	int cnt=1;
	for(int i=0; i<khTenList.length;i++)
	{
		if(khTenList[i].toUpperCase().startsWith(query.toUpperCase()) || khTenList[i].toUpperCase().indexOf(query.toUpperCase()) >=0 
				|| khIdList[i].toUpperCase().startsWith(query.toUpperCase()) || khIdList[i].toUpperCase().indexOf(query.toUpperCase()) >=0)
		{
			String khachhang = khIdList[i] + "-->[" + khTenList[i] + "][" + khChietKhauList[i] + "]";
			out.println(khachhang + "\n");
			if(cnt >= 10)
				break;
			cnt++;
		}
	}	
%>