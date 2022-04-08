<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="geso.dms.distributor.db.sql.dbutils"%>
<%@ page import="geso.dms.center.util.*"%>
<%@ page import="java.net.URLDecoder"%>

<% String nppId = (String) session.getAttribute("nppId"); %>
<% String khId = (String) session.getAttribute("khId"); %>
<% String khoId = (String) session.getAttribute("khoId"); 

String ngaydonhang = (String)session.getAttribute("ngaydonhang"); 
if (ngaydonhang == null)
	ngaydonhang = "";


%>



<%
	dbutils db =new dbutils();
	try
	{
		/* String command = "\n select a.ma, a.ten, b.donvi, d.AVAILABLE  as hienhuu, " +
		"\n isnull(( " +
		"\n     select GIABANLECHUAN * " + 
		"\n         (1-isnull((select chietkhau from BANGGIABANLECHUAN_NPP where BANGGIABANLECHUAN_FK = sp.BGBLC_FK and NPP_FK = '" + nppId + "' " +
		"\n     ),0)) " +
		"\n     from BANGGIABLC_SANPHAM sp " +
		"\n     where SANPHAM_FK = a.pk_seq " +  
		"\n     and BGBLC_FK in (select top(1) PK_SEQ " +
		"\n         from BANGGIABANLECHUAN bg inner join BANGGIABANLECHUAN_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIABANLECHUAN_FK " + 
		"\n         where bg.TRANGTHAI = '1' and  bg.TUNGAY <= '" + ngaydonhang + "' " +
		"\n         and bg_npp.NPP_FK = '" + nppId + "' and bg.DVKD_FK in (select pk_seq from donvikinhdoanh) " +
		"\n         and bg.KENH_FK = (select kbh_fk from khachhang where pk_seq = '" + khId + "') " + 
		"\n         order by bg.TUNGAY desc) " + 
		"\n ), 0) as dongia, " +
		"\n isnull(( " +
		"\n     select ptChietKhau from BANGGIABLC_SANPHAM sp " +
		"\n     where SANPHAM_FK = a.pk_seq " +  
		"\n     and BGBLC_FK in (select top(1) PK_SEQ   " +
		"\n         from BANGGIABANLECHUAN bg inner join BANGGIABANLECHUAN_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIABANLECHUAN_FK " + 
		"\n         where bg.TRANGTHAI = '1'  and bg.TUNGAY <= '" + ngaydonhang + "' " +
		"\n         and bg_npp.NPP_FK = '" + nppId + "' and bg.DVKD_FK in (select pk_seq from donvikinhdoanh) " + 
		"\n         and bg.KENH_FK = (select kbh_fk from khachhang where pk_seq = '" + khId + "') " + 
		"\n         order by bg.TUNGAY desc) " + 
		"\n ), 0)as ptChietKhau, " +
		"\n ( " +
		"\n     select top(1) PK_SEQ   " +
		"\n     from BANGGIABANLECHUAN bg inner join BANGGIABANLECHUAN_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIABANLECHUAN_FK " + 
		"\n     where bg.TRANGTHAI = '1'  and bg.TUNGAY <= '" + ngaydonhang + "'   " +
		"\n     and bg_npp.NPP_FK = '" + nppId + "' and bg.DVKD_FK in (select pk_seq from donvikinhdoanh) " + 
		"\n     and bg.KENH_FK = ( select kbh_fk from khachhang where pk_seq='" + khId + "' ) " + 
		"\n     order by bg.TUNGAY desc " +
		"\n ) as bgID " +
		"\n from SANPHAM a inner join DONVIDOLUONG b on a.dvdl_fk = b.pk_seq " +   
		"\n inner join NHAPP_KHO d on a.PK_SEQ = d.SANPHAM_FK " +
		"\n where a.pk_seq > 0 and a.DVKD_FK in (select pk_seq from donvikinhdoanh) " +
		"\n and d.NPP_FK = '" + nppId + "' and d.kho_fk = '" + khoId + "' and d.KBH_FK =  " +
		"\n ( select kbh_fk from khachhang where pk_seq = '" + khId + "' ) ";  */
		
		String command = "\n select a.ma, a.ten, b.donvi, d.AVAILABLE  as hienhuu, " +
		"\n dg.DONGIA as dongia, 0 ptChietKhau, null as bgID " +
		"\n from SANPHAM a inner join DONVIDOLUONG b on a.dvdl_fk = b.pk_seq " +   
		"\n inner join NHAPP_KHO d on a.PK_SEQ = d.SANPHAM_FK " +
				"\n	cross apply ( select [dbo].[GiaCkBanLeNppSanPham]("+nppId+","+khId+",a.pk_seq,'"+ngaydonhang+"' ) dongia  )dg " + 
				"\n cross apply ( select  [dbo].[BangGiaNppSanPham]("+khId+","+nppId+" ,'"+ngaydonhang+"' ) bgID  )bg " +
				"\n left join BGBANLENPP_SANPHAM bgsp on bgsp.BGBANLENPP_FK = bg.bgid and bgsp.sanpham_fk = a.pk_seq "+
		"\n where ((isnull(dg.dongia,0) + isnull(bgsp.luonhien,0) > 0 ) or isnull(bgsp.checkban,0)=1 ) and a.pk_seq > 0 and a.DVKD_FK in (select pk_seq from donvikinhdoanh) " +
		"\n and d.NPP_FK = '" + nppId + "' and d.kho_fk = '" + khoId + "' and d.KBH_FK =  " +
		"\n (select kbh_fk from khachhang where pk_seq = '" + khId + "') "; 
		System.out.println("Query get SanPhamTraVe: " + command);
		
		response.setHeader("Content-Type", "text/html");
		request.setCharacterEncoding("UTF-8");		
	   	String query = (String)request.getQueryString(); 	   	
	   	query = new String(query.substring(query.indexOf("&letters=") + 9, query.length()).getBytes("UTF-8"), "UTF-8");
	   	query = URLDecoder.decode(query, "UTF-8").replace(" +", " ");	   	
	   	Utility Ult = new Utility();
	   	query = Ult.replaceAEIOU(query);		
		ResultSet sp = db.get(command);
		int j = 0;
		
		if (sp != null)
		{
			while(sp.next())
			{
				int hienhuu = sp.getInt("hienhuu");
				if (hienhuu <= 0)
					hienhuu = 0;
				
				String MASP = sp.getString("ma");
				String TENSP = sp.getString("ten");
				System.out.println("khoId"+khoId);
				String masp = Ult.replaceAEIOU(sp.getString("ma"));
				String tensp = Ult.replaceAEIOU(sp.getString("ten"));
				String donvi = Ult.replaceAEIOU(sp.getString("donvi"));
		        String dongiakm="";
		        if(khoId.equals("100001")){
		        	dongiakm="0";
		        }else{
		        	dongiakm=sp.getString("dongia");
		        }
				double ptChietKhau =sp.getDouble("ptChietKhau");
				double DonGia =sp.getDouble("dongia");
				double GiaSauCK = DonGia*(1-ptChietKhau/100.0);
				
				
				if (masp.toUpperCase().startsWith(query.toUpperCase()) ||masp.toUpperCase().indexOf(query.toUpperCase()) >= 0 
						|| tensp.toUpperCase().indexOf(query.toUpperCase()) >= 0 || donvi.toUpperCase().indexOf(query.toUpperCase()) >= 0)
				{
					if (TENSP.length() > 50)
						TENSP = TENSP.substring(0, 50);
					out.print("###" + sp.getString("ma") + " - " + TENSP + " [" + sp.getString("donvi") + "] [" 
						+ dongiakm + "] [" + Integer.toString(hienhuu) + "] [" 
						+ GiaSauCK + "] [" + sp.getString("bgID") + "]|"); 
					//luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
				}	
			}	
		}
		sp.close();
		nppId = null;
		khId = null;
		khoId = null;
	
	}	
	catch(Exception ex){
		ex.printStackTrace();
	}
	finally {
		if (db!= null)
			db.shutDown();
	}
%>

