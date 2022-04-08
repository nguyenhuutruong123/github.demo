<%@page import="java.sql.SQLException"%>
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
	String smartId = "";
	String khId = "";
	String khTen = "";
	String khChietKhau = "";
	String khBgst = "";
	
	String command="";
	
	 command = "select distinct substring(b.smartid, charindex('_', b.smartid)+1, len(b.smartid)) as smartid, b.pk_seq as khId, b.ten as khTen, isnull(c.chietkhau, '0') as chietkhau, isnull(bgst_fk, '0') as bgstId "+ 
					 "from khachhang_tuyenbh a inner join khachhang b on a.khachhang_fk = b.pk_seq "+ 
					 "inner join tuyenbanhang d on a.tbh_fk = d.pk_seq "+
					 "left join mucchietkhau c on b.chietkhau_fk = c.pk_seq "+ 
					 "where b.npp_fk = (select a.pk_seq from nhaphanphoi a, nhanvien b where b.dangnhap = a.ma and b.pk_seq ='" + userId + "') and d.ddkd_fk = '"+ ddkdId +"' order by smartid, khId, khTen, chietkhau";

	ResultSet kh = db.get(command);
	
	String query = (String)geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("q"));
	response.setHeader("Content-Type", "text/html");
	
	if(kh != null)
	{
		int m = 0;
		try
		{  String khb;
			while(kh.next())
			{   khb =  kh.getString("khId");
				khId = kh.getString("smartid") + "-" +khb;
				khTen = kh.getString("khTen");
				khChietKhau = kh.getString("chietkhau");
				khBgst = "0";//kh.getString("bgstId");
               // if(!khBgst.equals("0"))
                //{
                String sql =" select BANGGIASIEUTHI_FK from BGST_KHACHHANG where khachhang_fk ='"+ khb +"' ";
                ResultSet rs = db.get(sql);
                String st ="";
                if(rs!=null)
                {
                	while(rs.next())
                	{
                		st = st + rs.getString("BANGGIASIEUTHI_FK") +"-";
                	}
                	if(st.length()>0)
                	khBgst = st;
                }
             
				if(khTen.toUpperCase().startsWith(query.toUpperCase()) || khTen.toUpperCase().indexOf(query.toUpperCase()) >=0 
						|| khId.toUpperCase().startsWith(query.toUpperCase()) || khId.toUpperCase().indexOf(query.toUpperCase()) >=0)
				{
					String khachhang = khId + "-->[" + khTen + "][" + khChietKhau + "][" + khBgst + "]";
					out.println(khachhang + "\n");
					
					m++;
					if(m >= 30)
						break;
				}
			}
			kh.close();
		}
		catch(SQLException e){}
	}
		
%>