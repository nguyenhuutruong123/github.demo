<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.db.sql.dbutils" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page import="java.net.URLDecoder"%>
<%@ page import="geso.dms.center.util.*"%>
<%
	String dvkdId = "";
	if(session.getAttribute("dvkdId") != null )
		dvkdId = (String) session.getAttribute("dvkdId");
	
	String kbhId = "";
	if(session.getAttribute("kbhId") != null )
		kbhId = (String) session.getAttribute("kbhId");
	
	String nppId = "";
	if(session.getAttribute("nppId") != null )
		nppId = (String) session.getAttribute("nppId");
	
	String khoId = "";
	if(session.getAttribute("khoId") != null )
		khoId = (String) session.getAttribute("khoId");
	

String ngaydonhang = (String)session.getAttribute("ngaychungtu"); 
if(ngaydonhang == null)
	ngaydonhang = "";

DecimalFormat df2 = new DecimalFormat( "#,###,###,###" );
	
	
	if( dvkdId.trim().length() > 0 && kbhId.trim().length() > 0 && nppId.trim().length() > 0 )
	{
		dbutils db = new dbutils();
		try
		{	
			String query = "";
			String command = 	
			"		select a.ma, a.ten, isnull((select top 1 d.donvi from QUYCACH c inner join donvidoluong d on c.dvdl2_fk = d.pk_seq "+ 
					"			where c.sanpham_fk = a.pk_seq and c.DVDL2_FK in (100018, 1200532)), b.donvi) as donvi, a.ptThue as thuexuat,  "+
			"			isnull( ( select GIAMUANPP from BGMUANPP_SANPHAM sp where SANPHAM_FK = a.pk_seq and BGMUANPP_FK in "+
			"						(	select top(1) PK_SEQ from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK "+
			"									where bg.TRANGTHAI = '1' and bg_npp.NPP_FK = '"+nppId+"' and bg.DVKD_FK = '"+dvkdId+"' and bg.KENH_FK = '"+kbhId+"'  "+
			"										and bg.TUNGAY <='"+ngaydonhang+"' order by bg.TUNGAY desc )  "+
			"					),0 ) as giamua, "+
			"			isnull( ( select ptChietKhau from BGMUANPP_SANPHAM sp where SANPHAM_FK = a.pk_seq and BGMUANPP_FK in "+
			"						(	select top(1) PK_SEQ from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK "+
			"									where bg.TRANGTHAI = '1' and bg_npp.NPP_FK = '"+nppId+"' and bg.DVKD_FK = '"+dvkdId+"' and bg.KENH_FK = '"+kbhId+"'  "+
			"										and bg.TUNGAY <='"+ngaydonhang+"' order by bg.TUNGAY desc )  "+
			"					),0 ) as ptChietKhau, "+
			"				(	 "+
			"					select top(1) PK_SEQ from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK "+
			"									where bg.TRANGTHAI = '1' and bg_npp.NPP_FK = '"+nppId+"' and bg.DVKD_FK = '"+dvkdId+"' and bg.KENH_FK = '"+kbhId+"' "+ 
			"										and bg.TUNGAY <='"+ngaydonhang+"' order by bg.TUNGAY desc  "+
			"				) as bgId";
			
			command += "		from SANPHAM a inner join DONVIDOLUONG b on a.dvdl_fk = b.pk_seq ";
			
			command += "		where a.pk_seq > 0 and a.DVKD_FK = '"+dvkdId+"' ";
			
			System.out.println("[command] " + command);
			
			response.setHeader("Content-Type", "text/html");
			request.setCharacterEncoding("UTF-8");
			
		   	query = (String)request.getQueryString(); 
		   	
		   	query = new String(query.substring(query.indexOf("&letters=") + 9, query.length()).getBytes("UTF-8"), "UTF-8");
		   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
		   	
		   	Utility Ult = new Utility();
		   	query = Ult.replaceAEIOU(query);
		   	
		   	System.out.println("[query] "+query);
			ResultSet sp = db.get(command);
			int j = 0;
			if(sp != null)
			{
				while(sp.next())
				{
					
					String spTen = sp.getString("ten");
					
					String masp = Ult.replaceAEIOU(sp.getString("ma"));
					String tensp = Ult.replaceAEIOU(sp.getString("ten"));
					String donvi = Ult.replaceAEIOU(sp.getString("donvi"));
					if(sp.getString("ma").toUpperCase().contains(query.toUpperCase()) || sp.getString("ten").toUpperCase().contains(query.toUpperCase()) )
					{
						tensp = sp.getString("ten");
						float ptChietKhau = sp.getFloat("ptChietKhau");
						float DonGia = sp.getFloat("giamua");
						float GiaSauCK = DonGia*(1-ptChietKhau/100);
						
						out.print("###" + sp.getString("ma") + " - " + tensp + " [" + sp.getString("donvi") + "] [" + GiaSauCK + "] [" + sp.getDouble("thuexuat") + "] [" + sp.getString("bgId") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
					}	
				}	
				sp.close();
			}
			
			db.shutDown();
		}
		catch(Exception ex){ ex.printStackTrace(); System.out.println("Xay ra exception roi ban..."); }
	}
%>

