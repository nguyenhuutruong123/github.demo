package geso.dms.center.servlets.dondathang;

import geso.dms.center.beans.dondathang.IErpDondathang;
import geso.dms.center.beans.dondathang.IErpDondathangList;
import geso.dms.center.beans.dondathang.imp.ErpDondathang;
import geso.dms.center.beans.dondathang.imp.ErpDondathangList;
import geso.dms.center.db.sql.db_Sync;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.OracleConnUtils;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

public class ErpDondathangSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	public ErpDondathangSvl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpDondathangList obj;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");        

		HttpSession session = request.getSession();	    
		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);


		if (userId.length()==0)
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

		//--- check user
//		Utility util = new Utility();
	    String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
		if (view == null) {
			view = "";
		}
		String param = "";
		if(view.trim().length() > 0) param ="&view="+view;
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "ErpDondathangSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}
		 //--- check user 
		
		String action = util.getAction(querystring);

		String type = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("type")==null?"":request.getParameter("type")));
		System.out.println("type : "+type);
		if(type.equals("GetDonGia"))
		{
			NumberFormat formatter = new DecimalFormat("#,###,###.####");
			Gson gson = new Gson();

			String spMa = "";
			String dvdlId ="";
			String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));

			String kbhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kbhId")));  			

			String dvkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")));

			String query = (String)request.getQueryString();
			spMa = new String(query.substring(query.indexOf("&spMa=") + 6, query.indexOf("&dvdlId=")).getBytes("UTF-8"), "UTF-8");
			spMa = URLDecoder.decode(spMa, "UTF-8").replace("+", " ");

			dvdlId = new String(query.substring(query.indexOf("&dvdlId=") + 8, query.indexOf("&nppId=")).getBytes("UTF-8"), "UTF-8");
			dvdlId = URLDecoder.decode(dvdlId, "UTF-8").replace("+", " ");

			dbutils db = new dbutils();

			query = 
					" select a.DVDL_FK as dvCHUAN, ( select PK_SEQ from DONVIDOLUONG where DONVI = N'" + dvdlId + "' ) as dvNEW, " + 
					"	case when a.DVDL_FK =( select PK_SEQ from DONVIDOLUONG where DONVI = N'" + dvdlId + "' ) then 1  "+
					"	else (select soluong1/soluong2 from QUYCACH where SANPHAM_FK=a.PK_SEQ and DVDL1_FK=a.DVDL_FK and DVDL2_FK=  "+
					"		( select PK_SEQ from DONVIDOLUONG where DONVI = N'" + dvdlId + "' ) ) end as TyLe,  "+
					"	(select soluong1/soluong2 from QUYCACH where SANPHAM_FK=a.PK_SEQ and DVDL1_FK=a.DVDL_FK and DVDL2_FK=  "+
					"	( select PK_SEQ from DONVIDOLUONG where DONVI =  N'" + dvdlId + "' ) ) as QuyCach_THG ,a.TRONGLUONG,a.THETICH, " +  
					" isnull( ( select GIAMUANPP * ( 1 - isnull( ( select chietkhau from BANGGIAMUANPP_NPP where banggiamuaNPP_FK = bg_sp.bgmuaNPP_FK and NPP_FK = '"+nppId+"' ), 0) / 100 )  				" +
					"			from BGMUANPP_SANPHAM bg_sp 			    where SANPHAM_FK = a.pk_seq  " +
					"							and BGMUANPP_FK in ( select top(1) PK_SEQ from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK where bg.TRANGTHAI = '1'" +
					" 							and bg_npp.NPP_FK = '"+nppId+"' and bg.DVKD_FK = '"+dvkdId+"' and bg.KENH_FK = '"+kbhId+"' order by bg.TUNGAY desc ) ), 0) as GiaGoc, "+
					"	isnull( ( select GIAMUANPP * ( 1 - (isnull( ( select chietkhau from BANGGIAMUANPP_NPP "+
					"	where banggiamuaNPP_FK = bg_sp.bgmuaNPP_FK and NPP_FK = '"+nppId+"' ), 0) + isnull(bg_sp.ptChietKhau, 0)) / 100 )  "+ 							
					"	from BGMUANPP_SANPHAM bg_sp where SANPHAM_FK = a.pk_seq  "+							
					"	and BGMUANPP_FK in ( select top(1) PK_SEQ from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK "+ 
					"	where bg.TRANGTHAI = '1' and bg_npp.NPP_FK = '"+nppId+"' and bg.DVKD_FK = '"+dvkdId+"' and bg.KENH_FK = '"+kbhId+"' order by bg.TUNGAY desc ) ), 0) as GiaMua "+
					" from SANPHAM a where a.MA = '" + spMa + "'  ";

			System.out.println("[Sql]"+query);

			ResultSet rs = db.get(query);
			double TheTich =0;		
			double TrongLuong =0;
			double DonGia =0;		
			double GiaGoc = 0;
			double QuyCach=0;
			double TyLe = 0;


			if(rs != null)
			{
				try 
				{
					if(rs.next())
					{
						TheTich=rs.getDouble("thetich");
						TrongLuong= rs.getDouble("trongluong");
						DonGia =rs.getDouble("giamua");
						GiaGoc =rs.getDouble("giagoc");
						QuyCach = rs.getDouble("QuyCach_THG");
						TyLe = rs.getDouble("TyLe");

						SanPham sp = new SanPham();
						sp.setDongia( formatter.format(DonGia*TyLe));
						sp.setGiagoc( formatter.format(GiaGoc*TyLe));
						sp.setTrongluong(formatter.format(TrongLuong*TyLe));
						sp.setThetich(formatter.format(TheTich*TyLe));
						sp.setQuycach(formatter.format(QuyCach) );
						response.setContentType("application/json");
						response.setCharacterEncoding("UTF-8");
						response.getWriter().write(gson.toJson(sp));

					}
					rs.close();
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
				finally
				{
					if(db!=null)db.shutDown();
				}
			}
		}
		else
		{
			String lsxId = util.getId(querystring);
			obj = new ErpDondathangList();			    	

			String loaidonhang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaidonhang"));
			if(loaidonhang == null)
				loaidonhang = "0";
			obj.setLoaidonhang(loaidonhang);
			System.out.println("---LOAI DON HANG: " + loaidonhang);

			if (action.equals("delete") )
			{	
				String msg = this.DeleteChuyenKho(lsxId);
				obj.setMsg(msg);
			}
			else
			{
				if(action.equals("chot"))
				{
					String msg = this.Chot(lsxId,userId);
					obj.setMsg(msg);
				}

				//Mo trang thai de npp sua lai thong tin.
				else	if(action.equals("openOrder"))
				{
					String msg = this.openOrder(lsxId,userId);
					obj.setMsg(msg);
				}
				else 
					if(action.equals("DMS_ChietKhauThanhToan"))
					{
						String msg = this.DMS_ChietKhauThanhToan();
						obj.setMsg(msg);
					}else if(action.equals("MoChot"))
					{
						// Kt mở chốt đơn đặt hàng trung tâm khi chưa có PXN từ ERP hoặc DMS
					
						
						
							dbutils db = new dbutils();

								String query = "update ERP_Dondathang set trangthai = '0' where pk_seq = '" + lsxId + "' and TrangThai= '2' ";


								System.out.println("[ERP_Dondathang]"+ query);
								if(db.updateReturnInt(query)!=1)
								{
									String	msg = "Không thể mở chốt đơn hàng vì đã có phiếu xuất kho" + query;
									obj.setMsg(msg);
								}
								
							
							
							db.shutDown();
							
						
					}
				//GIU NGUYEN TIM KIEM		   		   
				String tungaytk = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
				System.out.println("tungay : "+tungaytk);
				if(tungaytk == null)
					tungaytk = "";	    	   	    
				obj.setTungay(tungaytk);

				String denngaytk = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));

				if(denngaytk == null)
					denngaytk = "";	    	   	    
				obj.setDenngay(denngaytk);

				String vungtk = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")));
				if(vungtk == null)
					vungtk = "";	    	   	    
				obj.setVungId(vungtk);

				String khuvuctk = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")));
				if(khuvuctk == null)
					khuvuctk = "";	    	   	    
				obj.setKhuvucId(khuvuctk);

				String npptk = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
				System.out.println("nppId : "+npptk);
				if(npptk == null)
					npptk = "";	    	   	    
				obj.setNppTen(npptk);

				String kbhtk = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")));
				if(kbhtk == null)
					kbhtk = "";	    	   	    
				obj.setKbhId(kbhtk);		    		   

				String trangthaitk = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));		    
				if(trangthaitk == null)
					trangthaitk = "";	    	   	    
				obj.setTrangthai(trangthaitk);		 
				
				
				String sodonhang = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sodonhang")));		    
				if(sodonhang == null)
					sodonhang = "";	    	   	    
				obj.setSoDonDathang(sodonhang);

				//--------------------------------------------------------
			}

			obj.setUserId(userId);
			String search = "";
			if(action.equals("chot") || action.equals("delete"))
			{
				search = getSearchQuery(request, obj);				 
			}

			obj.init(search);		   		   
			session.setAttribute("obj", obj);

			String nextJSP = "/AHF/pages/Center/ErpDonDatHang.jsp";
			if(loaidonhang.equals("4"))
				nextJSP = "/AHF/pages/Center/ErpDonDatHangKhac.jsp";
			else if(loaidonhang.equals("2"))
				nextJSP = "/AHF/pages/Center/ErpDonHangKMTichLuy.jsp";
			else if(loaidonhang.equals("1"))
				nextJSP = "/AHF/pages/Center/ErpDonHangKMUngHang.jsp";
			else if(loaidonhang.equals("3"))
				nextJSP = "/AHF/pages/Center/ErpDonHangTrungBay.jsp";

			response.sendRedirect(nextJSP);
		}
	}

	private String openOrder(String lsxId, String userId)
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);

			String query = "update ERP_DONDATHANG set NPP_DACHOT=0,TT_DuyetLai=1 where pk_seq = '" + lsxId + "'";
			if(!db.update(query))
			{
				msg = "Khong the chot: " + query;
				db.getConnection().rollback();
				return msg;
			}

			db.getConnection().commit();
			db.shutDown();
		}
		catch (Exception e) 
		{
			db.update("roolback");
			db.shutDown();
			return "Exception: " + e.getMessage();
		}

		return "";
	}


	private String DMS_ChietKhauThanhToan()
	{
		String msg="";
		dbutils db = new dbutils();
		db_Sync SYNC = new db_Sync();
		String query=
				"	select a.PK_SEQ as DMS_PO,b.ma  as Khachhang,  "+
						"		isnull(	( "+
						"				select GIATRI/100.0  "+ 
						"				from dbo.ERP_DONDATHANG_CHIETKHAU  "+
						"				where DONDATHANG_FK=a.pk_seq and LOAI=1 "+
						"			),0)	as ck	"+
						"	from ERP_DONDATHANG a inner join NHAPHANPHOI b on b.PK_SEQ=a.NPP_FK "+
						"	where a.TRANGTHAI=2 and a.PK_SEQ not in  "+
						"	( "+
						"		select SoPO from "+SYNC.DBNAME+".dbo.DMS_ChietKhauThanhToan "+
						"	) " +
						" and	isnull(	( "+
						"				select GIATRI/100.0  "+ 
						"				from dbo.ERP_DONDATHANG_CHIETKHAU  "+
						"				where DONDATHANG_FK=a.pk_seq and LOAI=1 "+
						"			),0) >0 ";
		
		ResultSet rs = db.get(query);
		System.out.println("[DMS_ChietKhauThanhToan]"+query);
		try 
		{
			while(rs.next())
			{

				db.getConnection().setAutoCommit(false);
				SYNC.getConnection().setAutoCommit(false);

				String PO_Number=rs.getString("DMS_PO");
				double ck =rs.getDouble("ck");
				query=	
						"		iNSERT INTO DMS_ChietKhauThanhToan(NgayChungTu,NgayHoachToan,SoPO,LoaiTien,TyGia,SoTien,Khachhang) "+
								"		select	 "+
								"			(	select top(1)InVoiceDate from SAP_Invoice_Header aa  "+ 
								"				where hd.PO_Number=aa.PO_Number order by Created_Date asc)as NgayChungTu	, "+
								"			(	select top(1)InVoiceDate from SAP_Invoice_Header aa  "+
								"				where hd.PO_Number=aa.PO_Number order by Created_Date asc) as NgayHoachToan ,hd.PO_Number as SoPO,'VND' as LoaiTien, "+
								"			1 as TyGia, "+
								"		sum(InVoice_BVAT )*"+ck+" as SoTien,hd.CustomerId as Khachhang "+
								"		from SAP_Invoice_Header hd "+
								"		where PO_Number='"+PO_Number+"' and InvoiceId not in  "+
								"		(select Invoice_RevertFor from SAP_Invoice_Header where Invoice_RevertFor is not null)  "+
								"		group by PO_Number,CustomerId  ";

				System.out.println("[DMS_ChietKhauThanhToan]"+query);
				if(!SYNC.update(query))
				{
					msg = "Không thể đẩy đơn hàng sang SAP!Vui lòng kiểm tra lại kết nối !"+query;
					SYNC.getConnection().rollback();
					db.getConnection().rollback();
					return msg;
				}
				
				query=" select SoTien AS tienCK  from DMS_ChietKhauThanhToan where SoPO='"+PO_Number+"' ";
				double tienCK =0;
				ResultSet rsCK = SYNC.get(query);
				while(rsCK.next())
				{
					tienCK=rsCK.getDouble("tienCK");
				}
				rsCK.close();
				
				query =" update b set ckThanhToan='"+tienCK+"' from  ERP_DONDATHANG b  " +
						" where b.pk_seq='"+PO_Number+"'  ";
				System.out.println("[DMS_ChietKhauThanhToan]"+query);
				if(!db.update(query))
				{
					msg = "Không thể đẩy đơn hàng sang SAP!Vui lòng kiểm tra lại kết nối !"+query;
					SYNC.getConnection().rollback();
					db.getConnection().rollback();
					return msg;
				}

				SYNC.getConnection().commit();
				SYNC.getConnection().setAutoCommit(true);

				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
			}
		} catch (Exception e) 
		{
			try
			{
				SYNC.getConnection().rollback();
			} catch (SQLException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try
			{
				db.getConnection().rollback();
			} catch (SQLException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			return "Exception: " + e.getMessage();
		}
		finally
		{
			db.shutDown();
			SYNC.shutDown();
		}

		return msg;
	}

	private String Chot(String lsxId,String userId) 
	{
		dbutils db = new dbutils();

		String msg = "";
		String query="";
		try
		{
			db.getConnection().setAutoCommit(false);


			query = "update ERP_Dondathang set trangthai = '1' where pk_seq = '" + lsxId + "' and TrangThai=0 ";
			System.out.println("[ERP_Dondathang]"+ query);
			if(db.updateReturnInt(query)!=1)
			{
				msg = "Đơn hàng đã chốt rồi : " + query;
				db.getConnection().rollback();
				db.shutDown();
				return msg;
			}

			
			
			query = "select a.npp_fk, b.loainpp, b.nppc1, a.kbh_fk from erp_dondathang a inner join nhaphanphoi b on a.npp_fk = b.pk_seq where a.pk_seq = " + lsxId;
			ResultSet	rs = db.get(query);
			String nppid = "", loainpp = "", nppc1 = "", kbhid = "";
			if(rs != null)
			{
				rs.next();
				nppid = rs.getString("npp_fk");
				loainpp = rs.getString("loainpp");
				nppc1 = rs.getString("nppc1");
				kbhid = rs.getString("kbh_fk");
				rs.close();
			}
			System.out.println("loainpp: "+loainpp);

			query = "insert into nhaphang(NGAYNHAN, NGUOITAO, NGUOISUA, NGAYTAO, NGAYSUA, TRANGTHAI, NPP_FK, NCC_FK, SOTIENBVAT, VAT, SOTIENAVAT, "+
					"DVKD_FK, KBH_FK, NGAYCHUNGTU, KHO_FK, CHUNGTU, HDTAICHINH, DONDATHANG_FK, Created_Date)"+
					"select '"+getDateTime()+"', "+userId+", "+userId+", '"+getDateTime()+"', '"+getDateTime()+"', 0, NPP_FK, "+nppc1+", BVAT, Vat, AVAT, "+
					"DVKD_FK, KBH_FK, '"+getDateTime()+"', Kho_FK as Khodat, PK_SEQ, PK_SEQ, PK_SEQ, dbo.GetLocalDate(DEFAULT) "+
					"from ERP_DONDATHANG where  pk_seq = " + lsxId;
			
			System.out.println("[nhaphang]"+ query);
			if(db.updateReturnInt(query)!=1)
			{
				msg = "Không thể tạo nhận hàng: " + query;
				db.getConnection().rollback();
				db.shutDown();
				return msg;
			}
			
			String NhapHangId ="";
			ResultSet rsNH = db.get("select scope_identity() as ID ");
			if(rsNH.next())
			{
				NhapHangId = rsNH.getString("ID");
			}
			rsNH.close();
			if(NhapHangId.trim().length() <=0)
			{
				msg = "Không lấy được id nhận hàng: " ;
				db.getConnection().rollback();
				db.shutDown();
				return msg;
			}
			
			String spid = "";
			String spma = "";
			double soluong = 0;
			double tyle = 0;
			double soluongqd = 0;
			String donvi = "";
			double dongia = 0;
			double thuevat = 10;
			double bvat = 0;
			double vat = 0;
			double avat = 0;
			String khoid = "";
			
			query = "select b.MA, a.sanpham_fk, soluongttduyet, c.DONVI, dongia, thuevat, 1 as tyle "+
					"from ERP_DONDATHANG_SANPHAM a inner join sanpham b on a.sanpham_fk = b.PK_SEQ "+
					"inner join DONVIDOLUONG c on b.dvdl_fk = c.PK_SEQ where a.dondathang_fk = "+lsxId;
			System.out.println("[ERP_DONDATHANG_SANPHAM] " + query);
			ResultSet rsSP = db.get(query);
			while(rsSP.next())
			{
				spid = rsSP.getString("sanpham_fk");
				spma = rsSP.getString("ma");
				soluong = rsSP.getDouble("soluongttduyet");
				tyle = rsSP.getDouble("tyle");
				donvi = rsSP.getString("donvi");
				dongia = rsSP.getDouble("dongia");
				thuevat = rsSP.getDouble("thuevat");
				bvat = soluong * dongia;
				vat = bvat * thuevat / 100;
				avat = bvat + vat;
				soluongqd = soluong * tyle;
				
				query = "insert into nhaphang_sp (nhaphang_fk, sanpham_fk, soluong, soluongnhan, donvi, gianet, tienbvat, vat, tienavat, scheme, kho_fk) "+
						"select "+NhapHangId+", "+spid+", "+soluong+", "+soluong+", N'"+donvi+"', "+dongia+", "+bvat+", "+vat+", "+avat+", '', 100000"; 
				System.out.println("[nhaphang_sp]"+ query);
				if(db.updateReturnInt(query)!=1)
				{
					msg = "Không thể tạo nhận hàng chi tiết: " + query;
					db.getConnection().rollback();
					db.shutDown();
					return msg;
				}
				
				
			}
			rsSP.close();

			query = "select b.pk_seq as spid, SPMA, SOLUONG, c.DONVI, d.kho_fk from ERP_DONDATHANG_CTKM_TRAKM a inner join sanpham b on a.SPMA = b.MA "+
					"inner join DONVIDOLUONG c on b.dvdl_fk = c.PK_SEQ inner join CTKHUYENMAI d on a.CTKMID = d.PK_SEQ  "+
					"where dondathangid = "+lsxId+" and soluong > 0";
			ResultSet rsKM = db.get(query);
			while(rsKM.next())
			{
				spid = rsKM.getString("spid");
				spma = rsKM.getString("spma");
				soluong = rsKM.getDouble("soluong");
				donvi = rsKM.getString("donvi");
				khoid = rsKM.getString("kho_fk");
				
				query = "insert into nhaphang_sp (nhaphang_fk, sanpham_fk, soluong, soluongnhan, donvi, gianet, tienbvat, vat, tienavat, scheme, kho_fk) "+
						"select "+NhapHangId+", "+spid+", "+soluong+", "+soluong+", N'"+donvi+"', 0, 0, 0, 0, '', "+khoid; 
				System.out.println("[nhaphang_sp]"+ query);
				if(db.updateReturnInt(query)!=1)
				{
					msg = "Không thể tạo nhận hàng chi tiết: " + query;
					db.getConnection().rollback();
					db.shutDown();
					return msg;
				}
			
			}
			query = "update ERP_Dondathang set SendingDate=dbo.GetLocalDate(DEFAULT), TrangThai=2, NGUOICHOT = "+userId+" where pk_seq = '" + lsxId + "' ";
			System.out.println("[ERP_Dondathang]"+ query);
			if(!db.update(query))
			{
				msg = "Lỗi cập nhật thông tin đơn hàng " + query;
				db.getConnection().rollback();
				db.shutDown();
				return msg;
			}

			db.getConnection().commit();
			db.shutDown();
			return "";
		
		}
		catch (Exception e) 
		{
			
			try
			{
				db.getConnection().rollback();
			} catch (SQLException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			db.shutDown();
			e.printStackTrace();
			return "Exception: " + e.getMessage()+" [Query]"+query;
		}
		
		
	}

	private static String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);	
	}
	
	private String DeleteChuyenKho(String lsxId)
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);

			String query = "update ERP_Dondathang set trangthai = '3' where pk_seq = '" + lsxId + "'";
			if(!db.update(query))
			{
				msg = "1.Khong the xoa: " + query;
				db.getConnection().rollback();
				return msg;
			}

			db.getConnection().commit();
			db.shutDown();
		}
		catch (Exception e) 
		{
			db.update("roolback");
			db.shutDown();
			return "Exception: " + e.getMessage();
		}

		return "";

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		if (action == null)
		{
			action = "";
		}

		String loaidonhang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaidonhang"));
		if(loaidonhang == null)
			loaidonhang = "0";

		//--- check user
		Utility util = new Utility();
		HttpSession session = request.getSession();
	    String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
		if (view == null) {
			view = "";
		}
		String param = "";
		if(view.trim().length() > 0) param ="&view="+view;
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "ErpDondathangSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}
		 //--- check user 

		IErpDondathangList obj = new ErpDondathangList();
		obj.setLoaidonhang(loaidonhang);

//		Utility util = new Utility();

//		HttpSession session = request.getSession();
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"))); 
		obj.setUserId(userId);


		//GIU NGUYEN TIM KIEM		   		   
		String tungaytk = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
		System.out.println("tungay : "+tungaytk);
		if(tungaytk == null)
			tungaytk = "";	    	   	    
		obj.setTungay(tungaytk);

		String denngaytk = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));

		if(denngaytk == null)
			denngaytk = "";	    	   	    
		obj.setDenngay(denngaytk);

		String vungtk = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")));
		if(vungtk == null)
			vungtk = "";	    	   	    
		obj.setVungId(vungtk);

		String khuvuctk = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")));
		if(khuvuctk == null)
			khuvuctk = "";	    	   	    
		obj.setKhuvucId(khuvuctk);

		String npptk = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
		System.out.println("nppId : "+npptk);
		if(npptk == null)
			npptk = "";	    	   	    
		obj.setNppTen(npptk);

		String kbhtk = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")));
		if(kbhtk == null)
			kbhtk = "";	    	   	    
		obj.setKbhId(kbhtk);		    		   

		String trangthaitk = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));		    
		if(trangthaitk == null)
			trangthaitk = "";	    	   	    
		obj.setTrangthai(trangthaitk);		    		  

		session.setAttribute("lsxBeanList", obj);
		//--------------------------------------------------------


		if(action.equals("Tao moi"))
		{
			IErpDondathang lsxBean = new ErpDondathang();

			lsxBean.setUserId(userId);

			lsxBean.setLoaidonhang(loaidonhang);

			lsxBean.createRs();
			session.setAttribute("dvkdId", lsxBean.getDvkdId());
			session.setAttribute("kbhId", lsxBean.getKbhId());
			session.setAttribute("nppId", lsxBean.getNppId());
			session.setAttribute("khoId", lsxBean.getKhoNhapId());
			session.setAttribute("ngaydonhang", lsxBean.getNgayyeucau());

			session.setAttribute("lsxBean", lsxBean);
			obj.DBclose();
			String nextJSP = "/AHF/pages/Center/ErpDonDatHangNew.jsp";
			if(loaidonhang.equals("4"))
				nextJSP = "/AHF/pages/Center/ErpDonDatHangKhacNew.jsp";
			else if(loaidonhang.equals("2"))
				nextJSP = "/AHF/pages/Center/ErpDonHangKMTichLuyNew.jsp";
			else if(loaidonhang.equals("1"))
				nextJSP = "/AHF/pages/Center/ErpDonHangKMUngHangNew.jsp";
			else if(loaidonhang.equals("3"))
				nextJSP = "/AHF/pages/Center/ErpDonHangTrungBayNew.jsp";

			response.sendRedirect(nextJSP);
		}
		else
		{
			if(action.equals("view") || action.equals("next") || action.equals("prev"))
			{
				obj.setUserId(userId);
				String search = getSearchQuery(request, obj);
				obj.setNxtApprSplitting(Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));

				obj.init(search);
				obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
				session.setAttribute("obj", obj);

				String nextJSP = "/AHF/pages/Center/ErpDonDatHang.jsp";
				if(loaidonhang.equals("4"))
					nextJSP = "/AHF/pages/Center/ErpDonDatHangKhac.jsp";
				else if(loaidonhang.equals("2"))
					nextJSP = "/AHF/pages/Center/ErpDonHangKMTichLuy.jsp";
				else if(loaidonhang.equals("1"))
					nextJSP = "/AHF/pages/Center/ErpDonHangKMUngHang.jsp";
				else if(loaidonhang.equals("3"))
					nextJSP = "/AHF/pages/Center/ErpDonHangTrungBay.jsp";

				response.sendRedirect(nextJSP);
			} else 
				if(action.equals("DMS_ChietKhauThanhToan"))
				{
					String msg = this.DMS_ChietKhauThanhToan();
					obj.setMsg(msg);

					String search = getSearchQuery(request, obj);
					obj.setUserId(userId);
					obj.init(search);										   

					String nextJSP = "/AHF/pages/Center/ErpDonDatHang.jsp";
					if(loaidonhang.equals("4"))
						nextJSP = "/AHF/pages/Center/ErpDonDatHangKhac.jsp";
					else if(loaidonhang.equals("2"))
						nextJSP = "/AHF/pages/Center/ErpDonHangKMTichLuy.jsp";
					else if(loaidonhang.equals("1"))
						nextJSP = "/AHF/pages/Center/ErpDonHangKMUngHang.jsp";
					else if(loaidonhang.equals("3"))
						nextJSP = "/AHF/pages/Center/ErpDonHangTrungBay.jsp";

					session.setAttribute("obj", obj);  	
					session.setAttribute("userId", userId);
					response.sendRedirect(nextJSP);
				}
				else
				{
					String search = getSearchQuery(request, obj);
					obj.setUserId(userId);
					obj.init(search);										   

					String nextJSP = "/AHF/pages/Center/ErpDonDatHang.jsp";
					if(loaidonhang.equals("4"))
						nextJSP = "/AHF/pages/Center/ErpDonDatHangKhac.jsp";
					else if(loaidonhang.equals("2"))
						nextJSP = "/AHF/pages/Center/ErpDonHangKMTichLuy.jsp";
					else if(loaidonhang.equals("1"))
						nextJSP = "/AHF/pages/Center/ErpDonHangKMUngHang.jsp";
					else if(loaidonhang.equals("3"))
						nextJSP = "/AHF/pages/Center/ErpDonHangTrungBay.jsp";

					session.setAttribute("obj", obj);  	
					session.setAttribute("userId", userId);
					response.sendRedirect(nextJSP);
				}
		}
	}

	private String getSearchQuery(HttpServletRequest request, IErpDondathangList obj)
	{

		Utility util = new Utility();

		String query =  
				"select a.PK_SEQ, a.trangthai, a.ngaydonhang, c.ten as nppTEN, b.ten + '-' + b.diengiai as khoTEN, NV.TEN as nguoitao, b.ten as khonhan, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, isnull(a.NOTE, '') as NOTE ,  " +
						"   	(	SELECT COUNT(*) "+   
						"   		FROM ERP_YCXUATKHO_DDH "+   
						"   			INNER JOIN ERP_YCXUATKHO ON ERP_YCXUATKHO_DDH.ycxk_fk=ERP_YCXUATKHO.PK_SEQ "+   
						"   		WHERE ERP_YCXUATKHO_DDH.ddh_fk=a.PK_SEQ AND ERP_YCXUATKHO.TRANGTHAI=2 "+   
						"   	) as DaXuatKho,TT_DuyetLai,a.ckThanhToan, a.soso,isnull(a.avat,0) as avat, (select ten from vung where pk_seq in (select vung_fk from khuvuc where pk_seq = c.khuvuc_fk)) as vungTen "+	
						"from ERP_Dondathang a left join KHOTT b on a.kho_fk = b.pk_seq inner join NHAPHANPHOI c on a.NPP_FK = c.pk_seq  " +
						"inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
						"inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.pk_seq > 0 "
						+ " and a.NPP_DACHOT = '1' and a.kbh_fk in "+util.quyen_kenh(obj.getUserId());//AND A.NPP_FK IN  "+util.quyen_npp(obj.getUserId()) +"  ";

		String tungay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"));
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);

		String denngay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay"));
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);

		String trangthai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"));
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);

		String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
		if(nppId == null)
			nppId = "";
		obj.setNppTen(nppId);
		
		String sodonhang = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sodonhang")));		    
		if(sodonhang == null)
			sodonhang = "";	    	   	    
		obj.setSoDonDathang(sodonhang);

		String kbhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")));
		obj.setKbhId(kbhId);
		String vungId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")));
		obj.setVungId(vungId);
		String kvId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")));
		obj.setKhuvucId(kvId);
		
		obj.getDataSearch().clear();

		if(sodonhang.length() > 0) {
//			query += " and a.pk_seq = '" + sodonhang + "'";
			
			query = query + "\n and a.pk_seq = ? ";	
			obj.getDataSearch().add( "" + sodonhang + "" );
		}
		
		if(tungay.length() > 0) {
//			query += " and a.ngaydonhang >= '" + tungay + "'";
			
			query = query + "\n and a.ngaydonhang >= ? ";	
			obj.getDataSearch().add( "" + tungay + "" );
		}

		if(denngay.length() > 0) {
//			query += " and a.ngaydonhang <= '" + denngay + "'";
			
			query = query + "\n and a.ngaydonhang <= ? ";	
			obj.getDataSearch().add( "" + denngay + "" );
		}

		if(trangthai.length() > 0) {
//			query += " and a.TrangThai = '" + trangthai + "'";
		
			query = query + "\n and a.TrangThai = ? ";	
			obj.getDataSearch().add( "" + trangthai + "" );
		}

		if(nppId.length() > 0){
//			query += " and a.NPP_FK= '" + nppId + "'";
			
			query = query + "\n and a.NPP_FK= ? ";	
			obj.getDataSearch().add( "" + nppId + "" );
		}
		if(kvId.length() > 0)
		{
//			query += " and c.KHUVUC_FK = '" + kvId + "'";
			
			query = query + "\n and c.KHUVUC_FK = ? ";	
			obj.getDataSearch().add( "" + kvId + "" );
		}
		
		if(vungId.length() > 0)
		{
//			query += " and c.KHUVUC_FK in  (select pk_seq from khuvuc where vung_fk = '" +vungId+ "' )";
			
			query = query + "\n and c.KHUVUC_FK in  (select pk_seq from khuvuc where vung_fk = ? ";	
			obj.getDataSearch().add( "" + vungId + "" );
		}
		

		System.out.print(query);
		return query;
	}

	class SanPham
	{
		String dongia;
		String giagoc;
		String quycach ;
		String trongluong;
		String thetich;

		public String getTrongluong()
		{
			return trongluong;
		}
		public void setTrongluong(String trongluong)
		{
			this.trongluong = trongluong;
		}
		public String getThetich()
		{
			return thetich;
		}
		public void setThetich(String thetich)
		{
			this.thetich = thetich;
		}
		public String getQuycach()
		{
			return quycach;
		}
		public void setQuycach(String quycach)
		{
			this.quycach = quycach;
		}
		public String getDongia() 
		{
			return dongia;
		}
		public void setDongia(String dongia) 
		{
			this.dongia = dongia;
		}
		public String getGiagoc() 
		{
			return giagoc;
		}
		public void setGiagoc(String giagoc) 
		{
			this.giagoc = giagoc;
		}

	}

}
