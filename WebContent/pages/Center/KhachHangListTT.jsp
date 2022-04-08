<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.distributor.db.sql.dbutils" %>
<%@ page import = "java.net.URLDecoder" %>
<%@ page import="geso.dms.center.util.Utility"%>

<%
    String nppId = (String) session.getAttribute("nppId");
	String ddkdId = (String) session.getAttribute("ddkdId"); 
	
	try
	{  
	
	dbutils db = new dbutils();
	
	String smartId = "";
	String khId = "";
	String khTen = "";
	String khChietKhau = "";
	String khBgst = "";
	
	String command="";
	request.setCharacterEncoding("UTF-8");
   
	Utility Ult = new Utility();
	
   	String query = (String)request.getQueryString(); 
   	query = new String(query.substring(query.indexOf("q=") + 2, query.indexOf("&limit=")).getBytes("UTF-8"), "UTF-8");
   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
	query = Ult.replaceAEIOU(query);
	
	response.setHeader("Content-Type", "text/html; charset=UTF-8");
	
 	//thay bang truy van long xem thu co nhanh hon khong.
 	if(ddkdId.length() > 0)
 	{
	 	command = "select  substring(b.smartid, charindex('_', b.smartid)+1, len(b.smartid)) as smartid, b.pk_seq as khId, b.ten as khTen, b.diachi "+
		 "from khachhang b  where b.trangthai = '1'  "+
		 " and ( b.smartid = '%" + query + "%' or upper(b.TimKiem) like upper( (N'%" + query + "%') ) )   "+
		 " ";

		command +=  "order by smartid, khId, khTen, chietkhau";
 	}
 	else//lay khoang 30 khach hang gan giong ky tu nguoi su dung nhap vao
 	{
 		command =  " select  top(30) substring(b.smartid, charindex('_', b.smartid)+1, len(b.smartid)) as smartid, b.pk_seq as khId, b.ten as khTen, b.diachi ";
		command += " from khachhang b  where b.trangthai = '1'  ";
		command += " and ( b.smartid like '%" + query + "%' or upper(b.TimKiem) like upper( (N'%" + query + "%') ) )   ";
			command += " and isnull(b.ismt,'0') = '1' ";
			command += " order by smartid, khId, khTen, chietkhau";
 	}
 	
 	System.out.println("11.KHACH HANG LA: " + command);
  	
 	ResultSet kh = db.get(command);
 	
	if(kh != null)
	{
		int m = 0;
			String khb;
			while(kh.next())
			{   
				khb =  kh.getString("khId");
				khId = kh.getString("smartid") + "-" + khb;
				khTen = kh.getString("khTen") + " - " + kh.getString("diachi");
			
				khBgst = "0";
				if(Ult.replaceAEIOU(khTen).toUpperCase().startsWith(query.toUpperCase()) || Ult.replaceAEIOU(khTen).toUpperCase().indexOf(query.toUpperCase()) >=0 
						|| khId.toUpperCase().startsWith(query.toUpperCase()) || khId.toUpperCase().indexOf(query.toUpperCase()) >=0)
				{
					String khachhang = khId + "-->[" + khTen + "][" + khChietKhau + "][" + khBgst + "]";
					out.println(khachhang + "\n");
					
					m++;
					if(m >= 50)
						break;
				}
			}
			kh.close();
		
	}
	db.shutDown();
	db=null;
	ddkdId=null;
	nppId=null;
	}
	catch(Exception e){e.printStackTrace();}
		
%>