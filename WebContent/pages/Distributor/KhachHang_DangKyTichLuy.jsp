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
 	{
 		command =  " select distinct top(30) substring(b.smartid, charindex('_', b.smartid)+1, len(b.smartid)) as smartid, b.pk_seq as khId, b.ten as khTen, b.diachi, isnull(c.chietkhau, '0') as chietkhau ";
		command += " from khachhang b left join mucchietkhau c on b.chietkhau_fk = c.pk_seq where b.trangthai = '1' and b.npp_fk = '" + nppId + "' ";
		command += " and ( b.smartid = '" + query + "' or upper(dbo.ftBoDau(b.ten)) like upper( (N'%" + query + "%') ) )   ";
		command += " and b.pk_seq in (select distinct khachhang_fk from khachhang_tuyenbh ";
			command += "	where tbh_fk in (select tbh.pk_seq from tuyenbanhang tbh inner join daidienkinhdoanh dd on dd.pk_seq=tbh.ddkd_fk  where dd.trangthai=1  and tbh.npp_fk = '" + nppId + "' ) ) ";
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
				khChietKhau = kh.getString("chietkhau");
				khBgst = "0";
                String sql =" select BANGGIASIEUTHI_FK from BGST_KHACHHANG where khachhang_fk ='"+ khb +"' ";
                ResultSet rs = db.get(sql);
                String st ="";
                if(rs!=null)
                {
                	while(rs.next())
                	{
                		st = st + rs.getString("BANGGIASIEUTHI_FK") + "-";
                	}
                	rs.close();
                	if(st.length()>0)
                	{
          				st = st.substring(0, st.length() - 1);
                		khBgst = st;
                	}
                }
             
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