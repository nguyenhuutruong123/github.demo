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
if(ngaydonhang == null)
	ngaydonhang = "";


%>



<%
	dbutils db =new dbutils();
	try
	{
		int sudungcktt =0;
		String sql = "select SUDUNGCKTT from khachhang where pk_seq = "+khId;
		ResultSet rs = db.get(sql);
		if(rs.next())
		{
			sudungcktt = rs.getInt("SUDUNGCKTT");
		}
		String chuoi ="";
		String cktt = "";
		if(sudungcktt==1)
		{
			chuoi += "	+	isnull( ( select ptChietKhau from BANGGIABLC_SANPHAM sp	where SANPHAM_FK = a.pk_seq "+  
					"		and BGBLC_FK in ( select top(1) PK_SEQ   "+
					"					from BANGGIABANLECHUAN bg inner join BANGGIABANLECHUAN_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIABANLECHUAN_FK "+ 
					"		where bg.TRANGTHAI = '1'  and bg.TUNGAY <= '" + ngaydonhang + "'   "+
					"		and bg_npp.NPP_FK = '"+nppId+"' and bg.DVKD_FK = '100068' and bg.KENH_FK = ( select kbh_fk from khachhang where pk_seq='"+khId+"' ) "+ 
					"		order by bg.TUNGAY desc ) ), 0)  ";
			
			
			cktt += "		isnull( ( select ptChietKhau from BANGGIABLC_SANPHAM sp	where SANPHAM_FK = a.pk_seq "+  
					"		and BGBLC_FK in ( select top(1) PK_SEQ   "+
					"					from BANGGIABANLECHUAN bg inner join BANGGIABANLECHUAN_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIABANLECHUAN_FK "+ 
					"		where bg.TRANGTHAI = '1'  and bg.TUNGAY <= '" + ngaydonhang + "'   "+
					"		and bg_npp.NPP_FK = '"+nppId+"' and bg.DVKD_FK = '100068' and bg.KENH_FK = ( select kbh_fk from khachhang where pk_seq='"+khId+"' ) "+ 
					"		order by bg.TUNGAY desc ) ), 0) as cktt, ";	
			
		}else
		{
			cktt +=  " 0 as cktt, ";	
		}
		chuoi+= " as ptChietKhau ";
		
		System.out.println("Chiet khau: "+ chuoi);
		String command ="";	
 	command =
		"\n select a.ma, a.ten, b.donvi, d.AVAILABLE  as hienhuu,(select top(1) chietkhau from khachhang where pk_seq = '"+khId+"') as ckkh,   "+
		"\n	0 as dongia, "+
		"\n	isnull((select ptChietKhau from KHACHHANG_CHUNGLOAISPCK where khachhang_fk='"+khId+"' and CHUNGLOAI_FK=a.CHUNGLOAI_FK ),0)	+ "+
		"\n	isnull((select ptChietKhau from KHACHHANG_SANPHAMCK where khachhang_fk='"+khId+"' and SANPHAM_FK=a.PK_SEQ ),0)	  "+chuoi+
		
		"\n	, isnull((select ptChietKhau from KHACHHANG_CHUNGLOAISPCK where khachhang_fk='"+khId+"' and CHUNGLOAI_FK=a.CHUNGLOAI_FK ),0)	+ "+
		"\n	isnull((select ptChietKhau from KHACHHANG_SANPHAMCK where khachhang_fk='"+khId+"' and SANPHAM_FK=a.PK_SEQ ),0) as ckdln, "+cktt+"   "+
		"\n ( select top(1) PK_SEQ   "+
		"\n					from BANGGIABANLECHUAN bg inner join BANGGIABANLECHUAN_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIABANLECHUAN_FK "+ 
		"\n		where bg.TRANGTHAI = '1'  and bg.TUNGAY <= '" + ngaydonhang + "'   "+
		"\n		and bg_npp.NPP_FK = '"+nppId+"' and bg.DVKD_FK = '100068' and bg.KENH_FK = ( select kbh_fk from khachhang where pk_seq='"+khId+"' ) "+ 
		"\n		order by bg.TUNGAY desc ) as bgID ,a.ptThue  "+
		"\n from SANPHAM a inner join DONVIDOLUONG b on a.dvdl_fk = b.pk_seq "+   
		"\n 	inner join (select KHO_FK,NPP_FK,SANPHAM_FK,sum(AVAILABLE) as AVAILABLE, KBH_FK from NHAPP_KHO_CHITIET  where ngaynhapkho <= '"+ngaydonhang+"'  group by KHO_FK,NPP_FK,SANPHAM_FK, KBH_FK) d on a.PK_SEQ = d.SANPHAM_FK   "+
		"\n and a.pk_seq > 0 and a.DVKD_FK = '100068' and d.NPP_FK = '"+nppId+"' and d.kho_fk = '"+khoId+"' and d.KBH_FK =  "+
		"\n  ( select kbh_fk from khachhang where pk_seq='"+khId+"' ) "+
		"\n  and isnull( ( select top(1) GIABANLECHUAN *( 1-isnull((select top(1) chietkhau from BANGGIABANLECHUAN_NPP where BANGGIABANLECHUAN_FK=sp.BGBLC_FK and NPP_FK='"+nppId+"' ),0))  "+
		"\n  from BANGGIABLC_SANPHAM sp	where SANPHAM_FK = a.pk_seq "+  
		"\n		and BGBLC_FK in ( select top(1) PK_SEQ   "+
		"\n					from BANGGIABANLECHUAN bg inner join BANGGIABANLECHUAN_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIABANLECHUAN_FK "+ 
		"\n		where bg.TRANGTHAI = '1'  and  bg.TUNGAY <= '" + ngaydonhang + "'   "+
		"\n		and bg_npp.NPP_FK = '"+nppId+"' and bg.DVKD_FK = '100068' and bg.KENH_FK = ( select kbh_fk from khachhang where pk_seq='"+khId+"' ) "+ 
		"\n		order by bg.TUNGAY desc ) ), 0) > 0";

		System.out.println("San pham:"+ command);
		String bgstId = (String)session.getAttribute("bgstId");
		if(bgstId.length() > 2)
		{
			
		command = "select sp.ma, sp.ten, dvdl.donvi, 0 as dongia, e.AVAILABLE as hienhuu, (select top(1) chietkhau from khachhang where pk_seq = '"+khId+"') as ckkh " +
				  "	from BANGGIAST_SANPHAM spbg inner join (select * from BANGGIASIEUTHI where pk_seq in (select banggiasieuthi_fk from BGST_KHACHHANG where khachhang_fk='" + khId + "') ) bg on bg.pk_seq = spbg.bgst_fk " +
				  "	inner join sanpham sp on spbg.sanpham_fk = sp.pk_seq inner join donvidoluong dvdl on dvdl.pk_seq = sp.dvdl_fk inner join NHAPP_KHO e on sp.PK_SEQ =  e.SANPHAM_FK "+
				  "	where e.NPP_FK = '" + nppId + "'   and e.KHO_FK = '" + khoId + "' and e.kbh_fk in (select kbh_fk from khachhang where pk_seq='"+khId+"')";
		}
		
		System.out.println("San pham1:"+ command);
		
		response.setHeader("Content-Type", "text/html");
		request.setCharacterEncoding("UTF-8");
		
	   	String query = (String)request.getQueryString(); 
	   	
	   	query = new String(query.substring(query.indexOf("&letters=") + 9, query.length()).getBytes("UTF-8"), "UTF-8");
	   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
	   	
	   	Utility Ult = new Utility();
	   	query = Ult.replaceAEIOU(query);
		
		ResultSet sp = db.get(command);
		int j = 0;
		if(sp != null)
		{
			while(sp.next())
			{
				int hienhuu = sp.getInt("hienhuu");
				if(hienhuu <= 0)
					hienhuu = 0;
				
				String MASP = sp.getString("ma");
				String TENSP = sp.getString("ten");
				String ckkh1 = sp.getString("ckkh");
				if(ckkh1 == null)
				{
					ckkh1 = "0";
				}
				String masp = Ult.replaceAEIOU(sp.getString("ma"));
				String tensp = Ult.replaceAEIOU(sp.getString("ten"));
				String donvi = Ult.replaceAEIOU(sp.getString("donvi"));
				double ckkh = sp.getDouble("ckkh");
				double ptThue =sp.getDouble("ptThue");
				double ptChietKhau =sp.getDouble("ptChietKhau");
				double ptChietKhauDLN =sp.getDouble("ckdln");
				double ptChietKhauTT =sp.getDouble("cktt");
				//double DonGia =sp.getDouble("dongia")*(1+ptThue/100);
				//double GiaSauCK = (sp.getDouble("dongia")*(1-ptChietKhau/100.0))*(1+ptThue/100);
				double DonGia =sp.getDouble("dongia");
				double GiaSauCK = (sp.getDouble("dongia")*(1-ckkh/100.0));
								
				if(masp.toUpperCase().startsWith(query.toUpperCase()) ||masp.toUpperCase().indexOf(query.toUpperCase()) >= 0 
						|| tensp.toUpperCase().indexOf(query.toUpperCase()) >= 0 || donvi.toUpperCase().indexOf(query.toUpperCase()) >= 0 )
				{
					if(TENSP.length() > 50)
						TENSP = TENSP.substring(0, 50);
					out.print("###" + sp.getString("ma") + " - " + TENSP +" [" + sp.getString("donvi") + "] ["+ GiaSauCK + "] [" + Integer.toString(hienhuu) + "] ["+ DonGia + "] ["+ sp.getString("bgID") + "] ["+ ckkh1 + "] [" + ptChietKhauDLN + "] [" + ptChietKhauTT + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
				}	
			}	
		}
		sp.close();
		db.shutDown();
		db=null;
		nppId=null;
		khId=null;
		khoId=null;
	
	}	
	catch(Exception ex){
		if(db!=null){
			
			db.shutDown();
			db=null;
		}
	}
%>

