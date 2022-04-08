<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="geso.dms.distributor.db.sql.dbutils"%>
<%@ page import="geso.dms.center.util.*"%>
<%@ page import="java.net.URLDecoder"%>

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
		String chuoi ="";
		String cktt = "";
		int khut = 0;
		cktt +=  " 0 as cktt, ";	
		chuoi+= " ,0 as ptChietKhau ";
		String makh = "";
		String query = " select smartid from khachhang where pk_seq  ="+khId;
		System.out.println("KH: "+query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			if(rs.next())
			{
				makh = rs.getString("smartid");
			}
			rs.close();
		}
		if(makh.length() >0 )
		{
		
			 OracleConnUtils dbsys=new OracleConnUtils();
			 query = "select ITEM_CODE,sum(NVL(COUNT_LOT,0)) as SoLuong from apps.SGP_IP_ONHAND where CUSTOMER_NUMBER = '"+makh+"' or    ASSIGN_CUS IS NULL group by ITEM_CODE "; 
			
	 	ResultSet rssys=dbsys.get(query); 
		
		/* 	query = "select ITEM_CODE,COUNT_LOT,CUSTOMER_NUMBER,ASSIGN_CUS from TonKhoIPTEST  ";
			ResultSet rssys = db.get(query); */
		/* 	if(rssys != null)
			{	 */	
			 
			/*  query="IF OBJECT_ID('tempdb.dbo.#TonKhoIP') IS NOT NULL "
						+"\n DROP TABLE #TonKhoIP "
						+"\n CREATE TABLE #TonKhoIP ("
						+"\n COUNT_LOT numeric(18,0),  "
						+"\n ITEM_CODE varchar(100),"
						+"\n CUSTOMER_NUMBER varchar(100),"
						+ "\n ASSIGN_CUS varchar(100)"
						+ " )";
			 	db.update(query);
				query=	 " INSERT INTO #TonKhoIP "
					 	+ "select COUNT_LOT,ITEM_CODE,CUSTOMER_NUMBER,ASSIGN_CUS from TonKhoIPTEST "
						+ "  "	;
			 	db.update(query);
			 	
			 	
			 	

				query="IF OBJECT_ID('tempdb.dbo.#ERP_TonKhoSANPHAM') IS NOT NULL "
						+"\n DROP TABLE #ERP_TonKhoSANPHAM "
						+"\n CREATE TABLE #ERP_TonKhoSANPHAM ("
						+"\n sanpham_fk numeric(18,0),  "
						+"\n AVAILABLE numeric(18,0) )";
							
				db.update(query);
			 	
				query = 	"INSERT INTO #ERP_TonKhoSANPHAM "
						+ "select (select pk_seq from sanpham where ma = ITEM_CODE),sum(COUNT_LOT) as SoLuong from #TonKhoIP "
						+ " where (CUSTOMER_NUMBER = '"+makh+"' or ASSIGN_CUS is null) "
						+ " and (select pk_seq from sanpham where ma = ITEM_CODE) is not null "
						+ " group by ITEM_CODE ";
				System.out.println("LayTonKho: "+query);
				if(db.updateReturnInt(query)<=0)
				{
					System.out.println("Khong co Ton Kho #ERP_TonKhoSANPHAM: "+ query);
				}
				
				query=	 " INSERT INTO TonKhoSANPHAM "
						+ "select (select pk_seq from sanpham where ma = ITEM_CODE),sum(COUNT_LOT) as SoLuong from #TonKhoIP "
						+ " where (CUSTOMER_NUMBER = '"+makh+"' or ASSIGN_CUS is null) "
						+ "  "
						+ " group by ITEM_CODE ";
				if(db.updateReturnInt(query)<=0)
				{
					System.out.println("Khong co ton kho TonKhoSANPHAM: "+ query);
				} */
			/*	 try {
					while(rssys.next())
						{
						query=	 " INSERT INTO #TonKhoIP "
							 	+ " select '"+rssys.getString("COUNT_LOT")+"',  '"+rssys.getString("ITEM_CODE")+"','"+rssys.getString("CUSTOMER_NUMBER")+"','"+rssys.getString("ASSIGN_CUS")+"' "
								+ "  "	;
						if(db.updateReturnInt(query)<=0)
						{
							System.out.println("Khong co ton kho: "+ query);
						}
						}
				} catch (SQLException e) {
				
					e.printStackTrace();
				}*/
			//}
			
			 if(rssys != null)
			{
			 query="IF OBJECT_ID('tempdb.dbo.#ERP_TonKhoSANPHAM') IS NOT NULL "
						+"\n DROP TABLE #ERP_TonKhoSANPHAM "
						+"\n CREATE TABLE #ERP_TonKhoSANPHAM ("
						+"\n sanpham_fk numeric(18,0),  "
						+"\n AVAILABLE numeric(18,0) )";
			 			
			 db.update(query);
			while(rssys.next())
			{
				
				query=	 " INSERT INTO #ERP_TonKhoSANPHAM "
					 	+ " select (select pk_seq from sanpham where ma = '"+rssys.getString("ITEM_CODE")+"') as sanpham_fk,  '"+rssys.getString("SoLuong")+"' "
						+ " where  (select pk_seq from sanpham where ma = '"+rssys.getString("ITEM_CODE")+"') is not null "	;
				if(db.updateReturnInt(query)<=0)
				{
					System.out.println("Khong co ton kho: "+ query);
				}
				
			  /* 	query=	 " INSERT INTO TonKhoSANPHAM "
					 	+ " select (select pk_seq from sanpham where ma = '"+rssys.getString("ITEM_CODE")+"') as sanpham_fk,  '"+rssys.getString("SoLuong")+"'"
					 	+	 "where  (select pk_seq from sanpham where ma = '"+rssys.getString("ITEM_CODE")+"') is not null "	;
				if(db.updateReturnInt(query)<=0)
				{
					System.out.println("Khong co ton kho: "+ query);
				}   */
					
			}
			rssys.close();
			
			
			 query = "select NVL(ASSIGN_CUS,'0') as Uutien from apps.SGP_IP_ONHAND where CUSTOMER_NUMBER = '"+makh+"'   ";
			 rssys=dbsys.get(query); 
			 if(rssys.next())
			 {
				
						if(!rssys.getString("Uutien").equals("0"))
							khut = 1;
						rssys.close();
			 }
			 System.out.println("QRTKCT: "+query);
			
			dbsys.shutDown();
		}
		} 
		query = "select count(*) as so from #ERP_TonKhoSANPHAM ";
		rs = db.get(query);
		if(rs != null)
			if(rs.next())
			{
				System.out.println("Ton kho: "+ rs.getString("so"));
			}

		if(khut == 0)
		{
			// trường hợp kh không ưu tiên, chỉ trừ kho của những khách hàng không ưu tiên
				query = "update a set a.AVAILABLE = case when a.AVAILABLE - b.SL > 0 then   a.AVAILABLE - b.SL "
							+ " else 0 end  "+
							" from #ERP_TonKhoSANPHAM a inner join ( "+
							"select SUM(b.SOLUONG) as SL,SANPHAM_FK "+ 
							"\n from  DONHANGIP a inner join  DONHANG_SANPHAMIP_CHITIET b on a.PK_SEQ = b.DONHANG_SANPHAMIP_FK "+
							"\n where isnull(KHACHHANGUUTIEN,'0') = 0  and a.trangthai in (0,9) "+
							"\n group by SANPHAM_FK ) b on a.sanpham_fk = b.SANPHAM_FK ";
			System.out.println("GiamTon1: "+query);
			if(db.updateReturnInt(query)<=0)
			{
				System.out.println("Khong cập nhật được tồn kho: "+ query);
			}
			
			// Trừ số lượng cần sản xuất 
			query = "update a set a.AVAILABLE =  a.AVAILABLE - b.SL   "
					+ "  "+
					" from #ERP_TonKhoSANPHAM a inner join ( "+
					"select SUM(abs(b.SLCSX)) as SL,SANPHAM_FK "+ 
					"\n from  DONHANGIP a inner join  DONHANG_SANPHAMSPIP b on a.PK_SEQ = b.DONHANG_FK "+
					"\n where  a.trangthai in (0,9) "+
					"\n group by SANPHAM_FK ) b on a.sanpham_fk = b.SANPHAM_FK ";
					System.out.println("TruSLCSX: "+query);
			db.update(query);
		}
		else
		{
			query = "update a set a.AVAILABLE =  a.AVAILABLE - b.SL  "
					+ " "+
					" from #ERP_TonKhoSANPHAM a inner join ( "+
					"select SUM(b.SOLUONG) as SL,SANPHAM_FK "+ 
					"\n from  DONHANGIP a inner join  DONHANG_SANPHAMSPIP b on a.PK_SEQ = b.DONHANG_FK "+
					"\n where  a.trangthai in (0,9)"+
					 "\n group by SANPHAM_FK ) b on a.sanpham_fk = b.SANPHAM_FK ";
			System.out.println("GiamTon2: "+query);
			if(db.updateReturnInt(query)<=0)
			{
				System.out.println("Khong cập nhật được tồn kho: "+ query);
			}
			
		}
		
		
		String command ="";	
 		/* command =
		"\n select a.ma, a.ten, isnull((select top(1) dv.DONVI from DONVIDOLUONG dv inner join  QUYCACH  qc on dv.PK_SEQ = qc.DVDL2_FK  where DVDL2_FK = (select PK_SEQ from DONVIDOLUONG where DONVI = 'Cuo') and qc.SANPHAM_FK = a.pk_seq),b.donvi) as donvi, isnull(d.AVAILABLE,0)  as hienhuu,   "+
		"\n	 isnull( ( select top(1) GIABANLECHUAN *( 1-isnull((select top(1) chietkhau from BangGiaBanLeChuan_KhachHang where BANGGIABANLECHUAN_FK=sp.BGBLC_FK and KHACHHANG_fk='"+khId+"' ),0))  "+
		"\n from BANGGIABLC_SANPHAM sp	where SANPHAM_FK = a.pk_seq "+  
		"\n		and BGBLC_FK in ( select top(1) PK_SEQ   "+
		"\n					from BANGGIABANLECHUAN bg inner join BangGiaBanLeChuan_KhachHang bg_npp on bg.PK_SEQ = bg_npp.BANGGIABANLECHUAN_FK "+ 
		"\n		where bg.TRANGTHAI = '1'  and  bg.TUNGAY <= '" + ngaydonhang + "'   "+
		"\n		and bg_npp.Khachhang_FK = '"+khId+"' and bg.DVKD_FK = '100069' and bg.KENH_FK = ( select kbh_fk from khachhang where pk_seq='"+khId+"' ) "+ 
		"\n		order by bg.TUNGAY desc ) ), 0)*isnull((select top(1) qc.SOLUONG1 from  QUYCACH  qc where DVDL2_FK = (select PK_SEQ from DONVIDOLUONG where DONVI = 'Cuo') and qc.SANPHAM_FK = a.pk_seq),1)  as dongia,isnull((select top(1) qc.SOLUONG1 from  QUYCACH  qc where DVDL2_FK = (select PK_SEQ from DONVIDOLUONG where DONVI = 'Cuo') and qc.SANPHAM_FK = a.pk_seq),1)  as quycachkg, "+
		"\n	isnull((select ptChietKhau from KHACHHANG_CHUNGLOAISPCK where khachhang_fk='"+khId+"' and CHUNGLOAI_FK=a.CHUNGLOAI_FK ),0)	+ "+
		"\n	isnull((select ptChietKhau from KHACHHANG_SANPHAMCK where khachhang_fk='"+khId+"' and SANPHAM_FK=a.PK_SEQ ),0)	  "+chuoi+
		
		"\n	, isnull((select ptChietKhau from KHACHHANG_CHUNGLOAISPCK where khachhang_fk='"+khId+"' and CHUNGLOAI_FK=a.CHUNGLOAI_FK ),0)	+ "+
		"\n	isnull((select ptChietKhau from KHACHHANG_SANPHAMCK where khachhang_fk='"+khId+"' and SANPHAM_FK=a.PK_SEQ ),0) as ckdln, "+cktt+"   "+
		"\n ( select top(1) PK_SEQ   "+
		"\n					from BANGGIABANLECHUAN bg inner join BangGiaBanLeChuan_KhachHang bg_npp on bg.PK_SEQ = bg_npp.BANGGIABANLECHUAN_FK "+ 
		"\n		where bg.TRANGTHAI = '1'  and bg.TUNGAY <= '" + ngaydonhang + "'   "+
		"\n		and bg_npp.Khachhang_FK = '"+khId+"' and bg.DVKD_FK = '100069' and bg.KENH_FK = ( select kbh_fk from khachhang where pk_seq='"+khId+"' ) "+ 
		"\n		order by bg.TUNGAY desc ) as bgID ,a.ptThue  "+
		"\n from SANPHAM a inner join DONVIDOLUONG b on a.dvdl_fk = b.pk_seq "+   
		"\n	left join #ERP_TonKhoSANPHAM d on a.PK_SEQ = d.SANPHAM_FK   "+
		"\n where a.pk_seq > 0 and a.DVKD_FK = '100069'  "+
		"\n and isnull( ( select top(1) GIABANLECHUAN *( 1-isnull((select top(1) chietkhau from BangGiaBanLeChuan_KhachHang where BANGGIABANLECHUAN_FK=sp.BGBLC_FK and KHACHHANG_fk='"+khId+"' ),0))  "+
		"\n from BANGGIABLC_SANPHAM sp	where SANPHAM_FK = a.pk_seq "+  
		"\n		and BGBLC_FK in ( select top(1) PK_SEQ   "+
		"\n					from BANGGIABANLECHUAN bg inner join BangGiaBanLeChuan_KhachHang bg_npp on bg.PK_SEQ = bg_npp.BANGGIABANLECHUAN_FK "+ 
		"\n		where bg.TRANGTHAI = '1'  and  bg.TUNGAY <= '" + ngaydonhang + "'   "+
		"\n		and bg_npp.Khachhang_FK = '"+khId+"' and bg.DVKD_FK = '100069' and bg.KENH_FK = ( select kbh_fk from khachhang where pk_seq='"+khId+"' ) "+ 
		"\n and (select top(1) dv.DONVI from DONVIDOLUONG dv inner join  QUYCACH  qc on dv.PK_SEQ = qc.DVDL2_FK  where DVDL2_FK = (select PK_SEQ from DONVIDOLUONG where DONVI = 'Cuo') and qc.SANPHAM_FK = a.pk_seq) is not null "+
		"\n		order by bg.TUNGAY desc ) ), 0) > 0";
		 */
		
		
		
		
		command =
				"\n select a.ma, a.ten, isnull((select top(1) dv.DONVI from DONVIDOLUONG dv inner join  QUYCACH  qc on dv.PK_SEQ = qc.DVDL2_FK  where DVDL2_FK = (select PK_SEQ from DONVIDOLUONG where DONVI = 'Cuo') and qc.SANPHAM_FK = a.pk_seq),b.donvi) as donvi, isnull(d.AVAILABLE,0)  as hienhuu,   "+
				" 0  as dongia,isnull((select top(1) qc.SOLUONG1 from  QUYCACH  qc where DVDL2_FK = (select PK_SEQ from DONVIDOLUONG where DONVI = 'Cuo') and qc.SANPHAM_FK = a.pk_seq),1)  as quycachkg, "+
				"\n	0	  "+chuoi+
				
				"\n	, 0	+ "+
				"\n	0 as ckdln, "+cktt+"   "+
				"\n 0 as bgID ,a.ptThue  "+
				"\n from SANPHAM a inner join DONVIDOLUONG b on a.dvdl_fk = b.pk_seq "+   
				"\n	left join #ERP_TonKhoSANPHAM d on a.PK_SEQ = d.SANPHAM_FK   "+
				"\n where a.pk_seq > 0 and a.DVKD_FK = '100069' and a.trangthai = '1' ";
				

		System.out.println("San pham:"+ command);
		response.setHeader("Content-Type", "text/html");
		request.setCharacterEncoding("UTF-8");
		
	   	 query = (String)request.getQueryString(); 
	   	
	   	query = new String(query.substring(query.indexOf("&letters=") + 9, query.length()).getBytes("UTF-8"), "UTF-8");
	   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
	   	
	   	Utility Ult = new Utility();
	   	query = Ult.replaceAEIOU(query);
		
		ResultSet sp = db.get(command);
		int j = 0;
		if(sp != null)
		{
			System.out.println("VOOO");
			while(sp.next())
			{
				int hienhuu = sp.getInt("hienhuu");
				String MASP = sp.getString("ma");
				String TENSP = sp.getString("ten");
				
				String masp = Ult.replaceAEIOU(sp.getString("ma"));
				String tensp = Ult.replaceAEIOU(sp.getString("ten"));
				String donvi = Ult.replaceAEIOU(sp.getString("donvi"));
				
				double ptThue =sp.getDouble("ptThue");
				double ptChietKhau =sp.getDouble("ptChietKhau");
				double ptChietKhauDLN =sp.getDouble("ckdln");
				double ptChietKhauTT =sp.getDouble("cktt");
				//double DonGia =sp.getDouble("dongia")*(1+ptThue/100);
				//double GiaSauCK = (sp.getDouble("dongia")*(1-ptChietKhau/100.0))*(1+ptThue/100);
				double DonGia =sp.getDouble("dongia");
				double GiaSauCK = (sp.getDouble("dongia")*(1-ptChietKhau/100.0));
				
				String quycachkg = sp.getString("quycachkg");
				
					
				if(masp.toUpperCase().startsWith(query.toUpperCase()) ||masp.toUpperCase().indexOf(query.toUpperCase()) >= 0 
						|| tensp.toUpperCase().indexOf(query.toUpperCase()) >= 0 || donvi.toUpperCase().indexOf(query.toUpperCase()) >= 0 )
				{
					if(TENSP.length() > 50)
						TENSP = TENSP.substring(0, 50);
					out.print("###" + sp.getString("ma") + " - " + TENSP +" [" + sp.getString("donvi") + "] ["+ GiaSauCK + "] [" + Integer.toString(hienhuu) + "] ["+ DonGia + "] ["+ sp.getString("bgID") + "] [" + ptChietKhauDLN + "] ["+quycachkg+"] [" + ptChietKhauTT + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
				
					
				}
			
				
				
			}	
		}
		System.out.println("HET");
		sp.close();
		db.shutDown();
		db=null;
		khId=null;
		khId=null;
		khoId=null;
	
	}	
	catch(Exception ex){
		if(db!=null){
			ex.printStackTrace();
			db.shutDown();
			db=null;
		}
	}
%>

