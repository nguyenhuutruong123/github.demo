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
		
		String	command =
					"\n select a.ma, a.ten, b.donvi, d.AVAILABLE  as hienhuu,kh.chietkhau as ckkh "+
					"\n		, dg.dongia , bg.bgID ,a.ptThue  "+
					"\n from SANPHAM a inner join DONVIDOLUONG b on a.dvdl_fk = b.pk_seq "+
					"\n inner join khachhang kh on kh.pk_seq =  '"+khId+"'  "+
					"\n  cross apply" +
                    "\n  ( 	select KHO_FK,NPP_FK,SANPHAM_FK,sum(AVAILABLE) as AVAILABLE, KBH_FK " +
                    "\n 		from NHAPP_KHO_CHITIET " +
                    "\n  	where  SANPHAM_FK = a.pk_seq and  NPP_FK =" + nppId + " and ngaynhapkho <='" + ngaydonhang + "'  and kho_fk =  '"+khoId+"'  and KBH_FK = kh.KBH_FK" +
                    "\n   	group by KHO_FK,NPP_FK,SANPHAM_FK, KBH_FK " +
                    "\n  ) d " +					
					"\n	cross apply ( select [dbo].[GiaCkBanLeNppSanPham]("+nppId+","+khId+",a.pk_seq,'"+ngaydonhang+"' ) dongia  )dg " + 
					"\n cross apply ( select  [dbo].[BangGiaNppSanPham](kh.pk_seq,"+nppId+" ,'"+ngaydonhang+"' ) bgID  )bg " +
					"\n left join BGBANLENPP_SANPHAM bgsp on bgsp.BGBANLENPP_FK = bg.bgid and bgsp.sanpham_fk = a.pk_seq "+
					"\n where ((isnull(dg.dongia,0) + isnull(bgsp.luonhien,0) > 0 ) or isnull(bgsp.checkban,0)=1 ) and a.trangthai = 1  " ;

		System.out.println("San pham:"+ command);
		String bgstId = (String)session.getAttribute("bgstId");
		if(bgstId.length() > 2)
		{
				
			command = "\n select sp.ma, sp.ten, dvdl.donvi, spbg.giasieuthi as dongia, e.AVAILABLE as hienhuu, (select top(1) chietkhau from khachhang where pk_seq = '"+khId+"') as ckkh " +
					  "\n	from BANGGIAST_SANPHAM spbg inner join (select * from BANGGIASIEUTHI where pk_seq in (select banggiasieuthi_fk from BGST_KHACHHANG where khachhang_fk='" + khId + "') ) bg on bg.pk_seq = spbg.bgst_fk " +
					  "\n	inner join sanpham sp on spbg.sanpham_fk = sp.pk_seq inner join donvidoluong dvdl on dvdl.pk_seq = sp.dvdl_fk inner join NHAPP_KHO e on sp.PK_SEQ =  e.SANPHAM_FK "+
					  "\n	where e.NPP_FK = '" + nppId + "'   and e.KHO_FK = '" + khoId + "' and e.kbh_fk in (select kbh_fk from khachhang where pk_seq='"+khId+"')";
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
				double ptChietKhau =0;
				double ptChietKhauDLN =0;
				double ptChietKhauTT =0;
				double DonGia =sp.getDouble("dongia");
				if(masp.toUpperCase().startsWith(query.toUpperCase()) ||masp.toUpperCase().indexOf(query.toUpperCase()) >= 0 
						|| tensp.toUpperCase().indexOf(query.toUpperCase()) >= 0 || donvi.toUpperCase().indexOf(query.toUpperCase()) >= 0 )
				{
					if(TENSP.length() > 50)
						TENSP = TENSP.substring(0, 50);
					out.print("###" + sp.getString("ma") + " - " + TENSP +" [" + sp.getString("donvi") + "] ["+ DonGia + "] [" + Integer.toString(hienhuu) + "] ["+ DonGia + "] ["+ sp.getString("bgID") + "] ["+ ckkh1 + "] [" + ptChietKhauDLN + "] [" + ptChietKhauTT + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
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

