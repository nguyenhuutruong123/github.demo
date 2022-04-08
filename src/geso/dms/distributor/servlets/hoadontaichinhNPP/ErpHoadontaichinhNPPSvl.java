package geso.dms.distributor.servlets.hoadontaichinhNPP;

import geso.dms.distributor.beans.hoadontaichinhNPP.IErpHoadontaichinhNPP;
import geso.dms.distributor.beans.hoadontaichinhNPP.IErpHoadontaichinhNPPList;
import geso.dms.distributor.beans.hoadontaichinhNPP.imp.ErpHoadontaichinhNPP;
import geso.dms.distributor.beans.hoadontaichinhNPP.imp.ErpHoadontaichinhNPPList;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpHoadontaichinhNPPSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpHoadontaichinhNPPSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpHoadontaichinhNPPList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = util.getAction(querystring);
	    
    	String lsxId = util.getId(querystring);
	    obj = new ErpHoadontaichinhNPPList();
	    
	    String loaidonhang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaidonhang"));
	    if(loaidonhang == null)
	    	loaidonhang = "0";
	    obj.setLoaidonhang(loaidonhang);
	    System.out.println("---LOAI DON HANG: " + loaidonhang);
	    
    	if(action.equals("chot"))
    	{
    		String msg = this.Chot(lsxId,userId);
			obj.setMsg(msg);
    	}
    	else if(action.equals("delete"))
    	{
    		String msg = this.Delete(lsxId,userId);
			obj.setMsg(msg);
    	}
    	else if(action.equals("huy"))
    	{
    		String msg = this.Huy(lsxId,userId);
			obj.setMsg(msg);
    	}
	    
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
		String nextJSP = "/AHF/pages/Distributor/ErpHoaDonTaiChinhNPP.jsp";
		response.sendRedirect(nextJSP);
	    
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
	    
	    
		IErpHoadontaichinhNPPList obj = new ErpHoadontaichinhNPPList();
		obj.setLoaidonhang(loaidonhang);
	 
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"))); 
	    
	    if(action.equals("Tao moi"))
	    {
	    	IErpHoadontaichinhNPP lsxBean = new ErpHoadontaichinhNPP();
	    	lsxBean.setUserId(userId);
	    	lsxBean.createRs();
	    	session.setAttribute("dvkdId", "");
			session.setAttribute("kbhId", "");
			session.setAttribute("nppId", "");
    		
	    	session.setAttribute("lsxBean", lsxBean);
	    	
    		String nextJSP = "/AHF/pages/Distributor/ErpHoaDonTaiChinhNPPNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
		    	String search = getSearchQuery(request, obj);
		    	obj.setNxtApprSplitting(Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
		    	obj.setUserId(userId);
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	
		    	String nextJSP = "/AHF/pages/Distributor/ErpHoaDonTaiChinhNPP.jsp";
				response.sendRedirect(nextJSP);
		    }
	    	else
	    	{
		    	String search = getSearchQuery(request, obj);
		    	obj.setUserId(userId);
		    	String sumqr = getSumQuery(request, obj);
		    	obj.getSumBySearch(sumqr);
		    	obj.init(search);		    	
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);		
	    		String nextJSP = "/AHF/pages/Distributor/ErpHoaDonTaiChinhNPP.jsp";
	    		response.sendRedirect(nextJSP);
	    	}
	    }
	}

	private String Huy(String lsxId, String userId) 
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{	
			db.getConnection().setAutoCommit(false);
			String query = "";
			
			
			Utility util = new Utility();
		/*	msg= util.Check_Huy_NghiepVu_KhoaSo("ERP_HOADONNPP", lsxId, "ngayxuatHD");
			if(msg.length()>0)
				return msg;
		*/	
			
			// Kiểm tra import =1 thì k cho hủy
			query = "select isnull(import, 0) as import from ERP_DONDATHANGNPP where pk_seq in (select ddh_fk from ERP_HOADONNPP_DDH where HOADONNPP_FK = '" + lsxId + "')";
			ResultSet rsKT = db.get(query);
			int ktra = 0;
			if(rsKT!= null)
			{
				while(rsKT.next())
				{
					ktra = rsKT.getInt("import");
				}
				rsKT.close();
			}
			
			if(ktra == 1)
			{
				msg = "Đơn hàng này GESO import nên không hủy được. ";
				db.getConnection().rollback();
				return msg;
			}
			else
			{
				query = "update ERP_HOADONNPP set trangthai = '5',NgaySua='"+getDateTime()+"' where pk_seq = '" + lsxId + "' and TrangThai!=5 ";
				System.out.println(query);
				if(db.updateReturnInt(query)!=1)
				{
					msg = "Hóa đơn đã hủy rồi "+query;
					db.getConnection().rollback();
					return msg;
				}
				
				// Lấy mã đơn đặt hàng

				// Lấy mã đơn đặt hàng
				query = "select DDH_FK,  " +
						"	( select top 1  YCXK_FK from ERP_YCXUATKHONPP_DDH where ddh_fk = a.DDH_FK order by ycxk_fk desc) as soPXK   " +
						"from ERP_HOADONNPP_DDH a where HOADONNPP_FK = '" + lsxId + "' ";
				ResultSet rsDDH = db.get(query);
				String dondathang_fk1 = "";
				String pxk_fk = "";
				{
									
					if(rsDDH.next())
					{
						dondathang_fk1 = rsDDH.getString("DDH_FK");	
						pxk_fk = rsDDH.getString("soPXK");	
					}
	
					query = "select khachhang_fk, a.kbh_fk, a.npp_fk, a.npp_dat_fk, " +
							"( select priandsecond from NHAPHANPHOI where pk_seq = a.npp_fk ) as tuxuatOTC,  " +
							"( select tuxuatETC from NHAPHANPHOI where pk_seq = a.npp_fk ) as tuxuatETC,  " +
							"( select loaiNPP from NHAPHANPHOI where pk_seq = a.npp_fk ) as loaiNPP, " +
							"( select tructhuoc_fk from NHAPHANPHOI where pk_seq = a.npp_fk ) as tructhuoc_fk,  " +
							" ISNULL( ( select dungchungkenh from NHAPHANPHOI where pk_seq = a.npp_fk ), 0 ) as dungchungkenh  " +
							"from ERP_DondathangNPP a where a.pk_seq = '" + dondathang_fk1 + "' order by pk_seq desc";
					
					System.out.println(dondathang_fk1);
					
					String khachhangID = "";
					String loaiNPP = "";
					String tructhuoc = "";
					String nppId = "";
					String npp_dat_fk = "";
					String kbh_fk1 = "";
					
					String tuquanlyKHO_OTC = "0";
					String tuquanlyKHO_ETC = "0";			
					
					ResultSet rs = db.get(query);
					{
						if(rs.next())
						{
							if(rs.getString("khachhang_fk") != null)
								khachhangID = rs.getString("khachhang_fk");
							
							loaiNPP = rs.getString("loaiNPP");
							tructhuoc = rs.getString("tructhuoc_fk");
							nppId = rs.getString("npp_fk");
							
							if(rs.getString("npp_dat_fk") != null)
								npp_dat_fk = rs.getString("npp_dat_fk");
							
							if(rs.getString("dungchungkenh").equals("1"))
								kbh_fk1 = "100025";
							else
								kbh_fk1 = rs.getString("kbh_fk");
							
							if(rs.getString("tuxuatOTC") != null)
								tuquanlyKHO_OTC = rs.getString("tuxuatOTC");
							
							if(rs.getString("tuxuatETC") != null)
								tuquanlyKHO_ETC = rs.getString("tuxuatETC");
							
						}
						rs.close();
					}
					
				if( ( khachhangID.trim().length() > 0 && tuquanlyKHO_ETC.equals("0") )  )
					{
						if (tructhuoc.trim().length() >= 5) // TRỰC THUỘC NPP
						{
							// check tồn kho
							msg = HuyPhieuXuatKho_CapTren_NPP(db,dondathang_fk1, tructhuoc);
							if(msg.trim().length() > 0)
							{
								msg = "Khong the huy: " + msg;
								db.getConnection().rollback();
								return msg;
							}
						}
						else
						// PHIẾU YCXK của HO
						{
							msg = HuyPhieuXuatKho_CapTren_HO(dondathang_fk1, tructhuoc);
							if(msg.trim().length() > 0)
							{
								msg = "Khong the huy: " + msg;
								db.getConnection().rollback();
								return msg;
							}
						}
					}
				else
					// Tạo PXK cho NPP (NPP TU QUAN LY TON KHO)
					{
						msg = HuyPhieuXuatKhoNPP( pxk_fk, npp_dat_fk, db );
						if(msg.trim().length() > 0)
						{
							msg = "Khong the huy: " + msg;
							db.getConnection().rollback();
							return msg;
						}
					}
				 } 
			}
			
		/*	msg= util.Check_Kho_Tong_VS_KhoCT(util.getIdNhapp(userId));
			if(msg.length()>0)
			{
				db.getConnection().rollback();
				return msg;
			}*/
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			try
      {
	      db.getConnection().rollback();
      } catch (SQLException e1)
      {
	      e1.printStackTrace();
      }
			return "Exception: " + e.getMessage();
		}
		finally
		{
			db.shutDown();
		}
		return "";
	}
	
	private String Delete(String lsxId, String userId) 
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{	
			db.getConnection().setAutoCommit(false);
			String query = "";
			
		/*	Utility util = new Utility();
			msg= util.Check_Huy_NghiepVu_KhoaSo("ERP_HOADONNPP", lsxId, "ngayxuatHD");
			if(msg.length()>0)
				return msg;*/
			
			// Kiểm tra import =1 thì k cho hủy
			query = "select isnull(import, 0) as import from ERP_DONDATHANGNPP where pk_seq in (select ddh_fk from ERP_HOADONNPP_DDH where HOADONNPP_FK = '" + lsxId + "')";
			ResultSet rsKT = db.get(query);
			int ktra = 0;
			if(rsKT!= null)
			{
				while(rsKT.next())
				{
					ktra = rsKT.getInt("import");
				}
				rsKT.close();
			}
			
			if(ktra == 1)
			{
				msg = "Đơn hàng này GESO import nên không hủy được. ";
				db.getConnection().rollback();
				return msg;
			}
			else
			{
				query = "update ERP_HOADONNPP set trangthai = '3' where pk_seq = '" + lsxId + "' ";
				System.out.println(query);
				if(!db.update(query))
				{
					msg = "Không thể hủy ERP_HOADONNPP " + query;
					db.getConnection().rollback();
					return msg;
				}
				
				// Lấy mã đơn đặt hàng
				query = "select DDH_FK,  " +
						"	( select top 1  YCXK_FK from ERP_YCXUATKHONPP_DDH where ddh_fk = a.DDH_FK order by ycxk_fk desc) as soPXK   " +
						"from ERP_HOADONNPP_DDH a where HOADONNPP_FK = '" + lsxId + "' ";
				ResultSet rsDDH = db.get(query);
				String dondathang_fk1 = "";
				String pxk_fk = "";
				/*if(rsDDH != null)*/
				{
									
					if(rsDDH.next())
					{
						dondathang_fk1 = rsDDH.getString("DDH_FK");	
						pxk_fk = rsDDH.getString("soPXK");	
					}
	
					query = "select khachhang_fk, a.kbh_fk, a.npp_fk, a.npp_dat_fk, " +
							"( select priandsecond from NHAPHANPHOI where pk_seq = a.npp_fk ) as tuxuatOTC,  " +
							"( select tuxuatETC from NHAPHANPHOI where pk_seq = a.npp_fk ) as tuxuatETC,  " +
							"( select loaiNPP from NHAPHANPHOI where pk_seq = a.npp_fk ) as loaiNPP, " +
							"( select tructhuoc_fk from NHAPHANPHOI where pk_seq = a.npp_fk ) as tructhuoc_fk,  " +
							" ISNULL( ( select dungchungkenh from NHAPHANPHOI where pk_seq = a.npp_fk ), 0 ) as dungchungkenh  " +
							"from ERP_DondathangNPP a where a.pk_seq = '" + dondathang_fk1 + "' order by pk_seq desc";
					
					System.out.println(dondathang_fk1);
					
					String khachhangID = "";
					String loaiNPP = "";
					String tructhuoc = "";
					String nppId = "";
					String npp_dat_fk = "";
					String kbh_fk1 = "";
					
					String tuquanlyKHO_OTC = "0";
					String tuquanlyKHO_ETC = "0";			
					
					ResultSet rs = db.get(query);
					/*if(rs != null)*/
					{
						if(rs.next())
						{
							if(rs.getString("khachhang_fk") != null)
								khachhangID = rs.getString("khachhang_fk");
							
							loaiNPP = rs.getString("loaiNPP");
							tructhuoc = rs.getString("tructhuoc_fk");
							nppId = rs.getString("npp_fk");
							
							if(rs.getString("npp_dat_fk") != null)
								npp_dat_fk = rs.getString("npp_dat_fk");
							
							if(rs.getString("dungchungkenh").equals("1"))
								kbh_fk1 = "100025";
							else
								kbh_fk1 = rs.getString("kbh_fk");
							
							if(rs.getString("tuxuatOTC") != null)
								tuquanlyKHO_OTC = rs.getString("tuxuatOTC");
							
							if(rs.getString("tuxuatETC") != null)
								tuquanlyKHO_ETC = rs.getString("tuxuatETC");
							
						}
						rs.close();
					}
					
				if( ( khachhangID.trim().length() > 0 && tuquanlyKHO_ETC.equals("0") )  )
					{
						if (tructhuoc.trim().length() >= 5) // TRỰC THUỘC NPP
						{
							// check tồn kho
							msg = HuyPhieuXuatKho_CapTren_NPP(db,dondathang_fk1, tructhuoc);
							if(msg.trim().length() > 0)
							{
								msg = "Khong the huy: " + msg;
								db.getConnection().rollback();
								return msg;
							}
						}
						else
						// PHIẾU YCXK của HO
						{
							msg = HuyPhieuXuatKho_CapTren_HO(dondathang_fk1, tructhuoc);
							if(msg.trim().length() > 0)
							{
								msg = "Khong the huy: " + msg;
								db.getConnection().rollback();
								return msg;
							}
						}
					}
				else
					// Tạo PXK cho NPP (NPP TU QUAN LY TON KHO)
					{
						msg = HuyPhieuXuatKhoNPP( pxk_fk, npp_dat_fk, db );
						if(msg.trim().length() > 0)
						{
							msg = "Khong the huy: " + msg;
							db.getConnection().rollback();
							return msg;
						}
					}
				 } 
			}
			
		/*	msg= util.Check_Kho_Tong_VS_KhoCT(util.getIdNhapp(userId));
			if(msg.length()>0)
			{
				db.getConnection().rollback();
				return msg;
			}*/
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			db.shutDown();
			return "Exception: " + e.getMessage();
		}		
		
		return "";
	}
	
	private String Chot(String lsxId,String userId) 
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			
			//CHECK KHOA SO THANG
			String query = "";
			System.out.println("1.Khoi tao thang: " + query);
			Utility util = new Utility();
		/*	msg= util.Check_Huy_NghiepVu_KhoaSo("ERP_HOADONNPP", lsxId, "ngayxuatHD");
			if(msg.length()>0)
				return msg;
			*/
			
			query = "update ERP_HOADONNPP set trangthai = '2',NgaySua='"+getDateTime()+"',created_Date=dbo.GetLocalDate(DEFAULT)  where pk_seq = '" + lsxId + "'  ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_HOADONNPP " + query;
				db.getConnection().rollback();
				return msg;
			}

			//CHEN VAO BANG ERP_HOADONNPP_SP_CHITIET
			
			/*
			 * 
			 *ERP_YCXUATKHONPP_SANPHAM_CHITIET:Số lượng theo ĐV Chuẩn đã quy đổi từ ĐV đặt hàng
			 *ERP_DONDATHANGNPP_SANPHAM :Số lượng theo ĐV đặt
			 *
			 */
			query="delete from ERP_HOADONNPP_SP_CHITIET where hoadon_fk='"+lsxId+"'";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_HOADONNPP " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = 
		" insert ERP_HOADONNPP_SP_CHITIET(hoadon_fk, donhang_fk, KBH_FK, Kho_FK, MA, TEN, DONVI, DVCHUAN, DVDATHANG, SOLUONG, SOLO, NGAYHETHAN, CHIETKHAU, THUEVAT, DONGIA,SoLuong_Chuan,DonGia_Chuan,SoLuong_DatHang)  " +
		"		select '" + lsxId + "' as hoadon_fk, dh.pk_seq as donhang_fk, a.KBH_FK, a.KHO_FK, c.MA, isnull(dhsp.sanphamTEN ,c.TEN ) as TEN, (select donvi from DONVIDOLUONG where pk_seq = dhsp.dvdl_fk ) as donvi, d.pk_seq as dvCHUAN, dhsp.dvdl_fk  as dvDATHANG, "+    
		"	case when d.pk_seq = dhsp.dvdl_fk then b.soluong  "+    
		"			else b.soluong * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as soluong, "+      
		"	case solo when 'NA' then ' ' else b.solo end as solo,     "+
		"	case solo when 'NA' then ' ' else isnull(b.NGAYHETHAN,'') end as NGAYHETHAN, " +
		"		CASE WHEN ROW_NUMBER() OVER(PARTITION BY c.ma ORDER BY c.ma DESC) = 1 then dhsp.chietkhau else 0 end as chietKhau "+
		", dhsp.thuevat,    "+
		"	( select dongia from ERP_HOADONNPP_SP where hoadon_fk ='" + lsxId + "' and sanpham_fk = b.sanpham_fk ) as dongia  	, "+   
		"		b.soluong  as SoLuong_Chuan,   "+
		"			case when d.pk_seq = dhsp.dvdl_fk then dhsp.DONGIA "+     
		"	else dhsp.DONGIA *    "+
		"	( select SOLUONG2 / SOLUONG1 "+
		"	from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as DonGia_Chuan ,   "+
		"		CASE WHEN ROW_NUMBER() OVER(PARTITION BY c.ma ORDER BY c.ma DESC) = 1 THEN dhsp.soluong else 0 end as SoLuong_DatHang "+  
		" from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk "+	   
		"     inner join 	ERP_YCXUATKHONPP_DDH e on e.ycxk_fk = a.PK_SEQ									       "+ 									   
		"     inner join ERP_DONDATHANGNPP dh on dh.PK_SEQ = e.ddh_fk    									    "+
		"     inner join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk = dh.PK_SEQ and dhsp.sanpham_fk = b.sanpham_fk "+	   
		"     inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ   "+						   
		"     inner join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk "+ 	   
		" where dh.trangthai != '3' and a.PK_SEQ in    "+
		"  ( select ycxk_fk from ERP_YCXUATKHONPP_DDH   "+
		"  where ddh_fk = ( select DDH_FK from ERP_HOADONNPP_DDH where hoadonnpp_fk = '" + lsxId + "' ) ) and b.soluong > 0 and a.TRANGTHAI != '3' " ;  
			
			System.out.println("---CHAY HOA DON CHI TIET: " + query );
			if(!db.update(query) )
			{
				msg = "Không thể cập nhật ERP_HOADONNPP_SP_CHITIET " + query;
				db.getConnection().rollback();
				System.out.println("---4.LOI ROI..." + query);
				return msg;
			}
			
			//CHECK BANG TONG PHAI BANG BANG CHI TIET
		/*	query = "select count(*) as sodong  " +
					"from " +
					"( " +
					"	select b.pk_seq as sanpham_fk, sum(soluong) as soluong  " +
					"	from ERP_HOADONNPP_SP a inner join SANPHAM b on a.sanpham_fk = b.pk_seq " +
					"	where a.hoadon_fk = '" + lsxId + "' " +
					"	group by b.pk_seq " +
					") " +
					"dh left join " +
					"( " +
					"	select b.pk_seq as sanpham_fk, sum(soluong) as soluong  " +
					"	from ERP_HOADONNPP_SP_CHITIET a inner join SANPHAM b on a.MA = b.MA " +
					"	where a.hoadon_fk = '" + lsxId + "' " +
					"	group by b.pk_seq " +
					") " +
					"xk on dh.sanpham_fk = xk.sanpham_fk " +
					"where dh.soluong != isnull(xk.soluong, 0) ";
			System.out.println("---CHECK HOA DON: " + query);
			int soDONG = 0;
			ResultSet rsCHECK = db.get(query);
			if(rsCHECK != null)
			{
				if(rsCHECK.next())
				{
					soDONG = rsCHECK.getInt("sodong");
				}
				rsCHECK.close();
			}
			
			if(soDONG > 0)
			{
				msg = "3.Số lượng trong đơn hàng không khớp với hóa đơn. Vui lòng liên hệ Admin để xử lý ";
				db.getConnection().rollback();
				return msg;
			}*/
			
			/*msg= util.Check_Kho_Tong_VS_KhoCT(util.getIdNhapp(userId));
			if(msg.length()>0)
			{
				db.getConnection().rollback();
				return msg;
			}	*/		
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return "Exception: " + e.getMessage();
		}
		finally
		{
			if(db!=null)db.shutDown();
		}
		return "";
	}
	
	private String HuyPhieuXuatKhoNPP(String ycxkId, String npp_dat_fk, dbutils db)
	{
		String msg = "";
		try
		{			
			//CHECK KHOA SO THANG
			String query ="";
			//CHECK XEM DUOI NPP DA NHAN HANG CHUA
			if (npp_dat_fk.trim().length() > 0)
			{
				//Tu dong tao nhan hang
				query = " select count(*) as soDONG from NHAPHANG where YCXKNPP_FK = '" + ycxkId + "' and trangthai = '1' ";
				
				int soDONG = 0;
				ResultSet rs = db.get(query);
				{
					if(rs.next())
						soDONG = rs.getInt("soDONG");
					rs.close();
				}
				
				if(soDONG > 0)
				{
					msg = "Hóa đơn đã có nhận hàng, bạn không thể hủy / xóa ";
					return msg;
				}
				
				//XOA NHAN HANG
				query = " delete NHAPHANG_SP where nhaphang_fk in ( select pk_seq from NHAPHANG where YCXKNPP_FK = '" + ycxkId + "' )  ";
				if(!db.update(query))
				{
					msg = "Không thể cập nhật NHAPHANG_SP:  " + query;
					return msg;
				}
				
				query = " delete NHAPHANG where YCXKNPP_FK = '" + ycxkId + "'   ";
				if(!db.update(query))
				{
					msg = "Không thể cập nhật NHAPHANG_SP:  " + query;
					return msg;
				}
			}
			
			//KHO TONG DOI LAI TANG BOOKED, TANG SO LUONG
			query = 
					"update kho set kho.BOOKED = kho.BOOKED + xuat.soluong,  " +
					"			   kho.SOLUONG = kho.SOLUONG + xuat.soluong	  " +
					"from NHAPP_KHO kho inner join  " +
					"(  " +
					"	select a.kho_fk, a.NPP_FK, b.SANPHAM_FK, case when isnull(c.dungchungkenh, 0) = 0 then a.kbh_fk else 100025 end as kbh_fk,  SUM(b.soluongXUAT) as soluong  " +
					"	from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM b on a.PK_SEQ = b.YCXK_FK " +
					"			inner join NHAPHANPHOI c on a.npp_FK = c.pk_seq  " +
					"	where a.PK_SEQ = '" + ycxkId + "' and b.soluongXUAT > 0 " +
					"	group by a.kho_fk, a.kbh_fk, c.dungchungkenh, a.NPP_FK, b.SANPHAM_FK  " +
					")  " +
					"xuat on kho.SANPHAM_FK = xuat.SANPHAM_FK and kho.NPP_FK = xuat.NPP_FK and kho.KBH_FK = xuat.kbh_fk and kho.KHO_FK = xuat.kho_fk ";
			System.out.println("TANG KHO NGUOC LAI: " + query);
			if(db.updateReturnInt(query)<=0)
			{
				msg = "Không thể cập nhật NHAPP_KHO:  " + query;
				return msg;
			}
			
		/*	query = "update kho set kho.SOLUONG = kho.SOLUONG + xuat.soluong,  " +
					"			   kho.AVAILABLE = kho.AVAILABLE + xuat.soluong	  " +
					"from NHAPP_KHO_CHITIET kho inner join  " +
					"(  " +
					"	select a.kho_fk, a.NPP_FK, b.SANPHAM_FK, case when isnull(c.dungchungkenh, 0) = 0 then a.kbh_fk else 100025 end as kbh_fk, b.solo,  SUM(b.soluong) as soluong,b.NgayHetHan  " +
					"	from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.PK_SEQ = b.YCXK_FK " +
					"			inner join NHAPHANPHOI c on a.npp_FK = c.pk_seq  " +
					"	where a.PK_SEQ = '" + ycxkId + "' and b.soluong > 0 " +
					"	group by a.kho_fk, a.kbh_fk, c.dungchungkenh, a.NPP_FK, b.SANPHAM_FK, b.solo ,b.NgayHetHan " +
					")  " +
					"xuat on kho.SANPHAM_FK = xuat.SANPHAM_FK and kho.NPP_FK = xuat.NPP_FK and kho.KBH_FK = xuat.kbh_fk and kho.KHO_FK = xuat.kho_fk and kho.solo = xuat.solo  and kho.NgayHetHan=xuat.NgayHetHan " ;
			System.out.println("TANG KHO CHI TIET NGUOC LAI: " + query);
			if(db.updateReturnInt(query)<=0)
			{
				msg = "Không thể cập nhật NHAPP_KHO_CHITIET:  " + query;
				return msg;
			}*/
			
			//MO LAI TRANG THAI DON HANG
			query = "update ERP_DONDATHANGNPP set trangthai = '0' where pk_seq in ( select DDH_FK from ERP_YCXUATKHONPP_DDH where ycxk_fk = '" + ycxkId + "' ) and TrangThai!=0 ";
			if(db.updateReturnInt(query)!=1)
			{
				msg = "Đơn đặt hàng này đã mở trạng thái rồi "+query;
				return msg;
			}
			
			query = "Update ERP_YCXUATKHONPP set TRANGTHAI= '3' where pk_seq = '" + ycxkId + "' and TrangThai!=3 ";
			if(db.updateReturnInt(query)!=1)
			{
				msg = "Đơn đặt hàng này đã mở trạng thái rồi "+query;
				return msg;
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return "Không thể hủy hóa đơn " + e.getMessage();
		}
		return "";
	}
	
	private String HuyPhieuXuatKho_CapTren_HO (String dondathang_fk1, String tructhuoc)
	{
		String msg="";
		dbutils db = new dbutils();
		try 
		{			
		
			String	query = "select sp.PK_SEQ, sp.TEN, LOAI, SCHEME, SUM(dathang.soluong) as soluongXUAT " +
				"from    " +
				"(    " +
				"		select a.sanpham_fk, b.DVDL_FK as dvCHUAN,    " +
				"				case when a.dvdl_fk IS null then a.soluong     " +
				"					 when a.dvdl_fk = b.DVDL_FK then a.soluong    " +
				"					 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )     " +
				"									 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )	 end as soluong, 0 as loai, ' ' as scheme   " +
				"		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ    " +
				"		where a.dondathang_fk in ( '" + dondathang_fk1 + "' )   " +
				")    " +
				"dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ  " +
				"group by sp.PK_SEQ, sp.TEN, LOAI, SCHEME ";
				System.out.println("--CHECK KHO CHI TIET: " + query);
				
				ResultSet rsCHECK = db.get(query);
				
				
					db.getConnection().setAutoCommit(false);
					
					if(rsCHECK != null)
					{
						while(rsCHECK.next())
						{
							String sanpham_fk = rsCHECK.getString("PK_SEQ");
							String LOAI = rsCHECK.getString("LOAI");
							String SCHEME = rsCHECK.getString("SCHEME");
							double soLUONG = rsCHECK.getDouble("soluongXUAT");
						
							query = "Update ERP_KHOTT_SANPHAM set soluong = soluong + '" + soLUONG + "', AVAILABLE = AVAILABLE + '" + soLUONG + "' " +
							"where KHOTT_FK = '" + 100001 + "' and SANPHAM_FK = '" + sanpham_fk + "'  ";
							
							//CAP NHAT KHO CHI TIET
							query = "select AVAILABLE, SOLO from ERP_KHOTT_SP_CHITIET " +
									"where KHOTT_FK = '" + 100001 + "'  and SANPHAM_FK = '" + sanpham_fk + "' order by ngayhethan asc ";
							
							ResultSet rsTK = db.get(query);
							double avai = 0;
							double totalXUAT = 0;
							while(rsTK.next())
							{
								double soluongCT = 0;
								String solo = rsTK.getString("SOLO");						
								avai = rsTK.getDouble("AVAILABLE");
								
								soluongCT = avai;
								
								query = "Update ERP_KHOTT_SP_CHITIET set soluong = soluong - '" + soluongCT + "', AVAILABLE = AVAILABLE - '" + soluongCT + "' " +
								"where KHOTT_FK = '" + 100001 + "' and SOLO = '" + solo + "' and SANPHAM_FK = '" + sanpham_fk + "'  ";
								
								if(!db.update(query))
								{
									msg = "Khong the cap nhat ERP_KHOTT_SP_CHITIET: " + query;
									//db.getConnection().rollback();
									return msg;
								}
							}
							
							rsTK.close();
		
						}
						rsCHECK.close();
					}
					
					query = "update ERP_DONDATHANGNPP set trangthai = '0' where pk_seq = '" + dondathang_fk1 + "' ";
					System.out.println(query);
					if(!db.update(query))
					{
				msg = "Không thể cập nhật trạng thái ERP_DONDATHANGNPP:  " + query;
						db.getConnection().rollback();
						return msg;
					}
					
					query = "select * from ERP_YCXUATKHONPP_DDH  WHERE ddh_fk='"+dondathang_fk1+"'";
					ResultSet RS_PhieuXUATKHO = db.get(query);			
					String PhieuXUATKHO="";
					if(RS_PhieuXUATKHO!=null)
						PhieuXUATKHO = RS_PhieuXUATKHO.getString("ycxk_fk"); 
					
			// hủy phiếu xuất kho
					query = "Update ERP_YCXUATKHONPP set TRANGTHAI= '3' where pk_seq = '" + PhieuXUATKHO + "' ";
					System.out.println(query);
					if(!db.update(query))
					{
				msg = "Không thể cập nhật trạng thái ERP_YCXUATKHONPP:  " + query;
						db.getConnection().rollback();
						return msg;
					}	
			
		}
		catch (Exception e) 
		{
			msg = "--LOI DUYET: " + e.getMessage();
			e.printStackTrace();
			return msg;
		}
				
		return "";
	}
	
	private String HuyPhieuXuatKho_CapTren_NPP(dbutils db ,String dondathang_fk1, String tructhuoc)
	{
		// check tồn kho
		String msg="";
		String query = "";
		query =  "select khoxuat_fk as kho_fk, npp_fk, kbh_fk, sp.PK_SEQ, sp.TEN, SUM(dathang.soluong) as soluongXUAT,  " +
				"	ISNULL( ( select AVAILABLE from NHAPP_KHO where kho_fk = dathang.khoxuat_fk and sanpham_fk = sp.PK_SEQ and kbh_fk = dathang.kbh_fk and npp_fk = '" + tructhuoc + "' ), 0) as tonkho  " +
				"from     " +
				"(     " +
				"	select c.kho_fk as khoxuat_fk, '" + tructhuoc + "' as npp_fk, c.kbh_fk, a.sanpham_fk, b.DVDL_FK as dvCHUAN,     " +
				"			case when a.dvdl_fk IS null then a.soluong      " +
				"				 when a.dvdl_fk = b.DVDL_FK then a.soluong     " +
				"				 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and dvdl1_fk = b.dvdl_fk )      " +
				"								 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and dvdl1_fk = b.dvdl_fk )	 end as soluong   " +
				"	from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ  " +
				"			inner join ERP_DONDATHANGNPP c on a.dondathang_fk = c.pk_seq    " +
				"	where a.dondathang_fk in ( " + dondathang_fk1 + " )     " +
				")     " +
				"dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ   " +
				"group by khoxuat_fk, npp_fk, kbh_fk, sp.PK_SEQ, sp.TEN  ";		
		System.out.println("Câu query ở đây: " + query);
		ResultSet rs_tonkho = db.get(query);
		try
		{
			System.out.println("vao day: ");
			if(rs_tonkho != null)
			{
				System.out.println("vao day1: ");
				while(rs_tonkho.next())
				{
					System.out.println("vao day2: ");
					String npp_fk = rs_tonkho.getString("npp_fk");					
					String kbh_fk = rs_tonkho.getString("kbh_fk");
					String kho_fk = rs_tonkho.getString("kho_fk");
					String sanpham_fk = rs_tonkho.getString("PK_SEQ");
					
					double soluong = rs_tonkho.getDouble("soluongXUAT");
					double tonkho = rs_tonkho.getDouble("tonkho");							
					
					//CAP NHAT KHO XUAT TONG
					query = "Update NHAPP_KHO set soluong = soluong + '" + soluong + "', BOOKED = BOOKED + '" + soluong + "' " +
							"where KHO_FK = '"+kho_fk+"' and KBH_FK = '" + kbh_fk + "' and NPP_FK = '" + npp_fk + "' and SANPHAM_FK = '" + sanpham_fk + "' ";
					System.out.println(query);
					if(db.updateReturnInt(query)!=1)
					{
						msg = "Khong the cap nhat NHAPP_KHO: " + query;
						rs_tonkho.close();
						return msg;
					}
					
					//CAP NHAT KHO CHI TIET
					query = "select AVAILABLE, SOLO, ngayhethan from NHAPP_KHO_CHITIET " +
							"where AVAILABLE > 0 and KHO_FK = '" + kho_fk + "'  and SANPHAM_FK = '" + sanpham_fk  + "' and NPP_FK = '" + npp_fk + "' and KBH_FK = '" + kbh_fk + "' order by ngayhethan asc ";
					
					System.out.println(query);
					
					ResultSet rsTK = db.get(query);
					double avai = 0;
					double totalXUAT = 0;
					while(rsTK.next())
					{
						double soluongCT = 0;
						String solo = rsTK.getString("SOLO");
						String ngayhethan = rsTK.getString("ngayhethan");
						
						avai = rsTK.getDouble("AVAILABLE");
						//totalXUAT += avai;
						soluongCT = avai;
						query = "Update NHAPP_KHO_CHITIET set soluong = soluong + '" + soluongCT + "', AVAILABLE = AVAILABLE + '" + soluongCT + "' " +
						"where KHO_FK = '" + kho_fk + "' and SOLO = '" + solo + "' and SANPHAM_FK = '" + sanpham_fk + "' adn KBH_FK = '" + kbh_fk + "' and NPP_FK = '" + npp_fk + "' and NgayHetHan='"+ngayhethan+"' ";
						System.out.println(query);
						
						if(db.updateReturnInt(query)!=1)
						{
							msg = "Khong the cap nhat NHAPP_KHO_CHITIET: " + query;
							rsTK.close();
							return msg;
						}
					}
					rsTK.close();
				}
			rs_tonkho.close();
			
			
			query = "update ERP_DONDATHANGNPP set trangthai = '0' where pk_seq = '" + dondathang_fk1 + "' ";
			System.out.println(query);
			if(!db.update(query))
			{
					msg = "Không thể cập nhật trạng thái ERP_DONDATHANGNPP:  " + query;
				return msg;
			}
			
			query = "select * from ERP_YCXUATKHONPP_DDH  WHERE ddh_fk='"+dondathang_fk1+"'";
			ResultSet RS_PhieuXUATKHO = db.get(query);
			//----
			String PhieuXUATKHO="";
			if(RS_PhieuXUATKHO!=null)
				PhieuXUATKHO = RS_PhieuXUATKHO.getString("ycxk_fk"); //////////////////
			
				// hủy phiếu xuất kho
			query = "Update ERP_YCXUATKHONPP set TRANGTHAI= '3' where pk_seq = '" + PhieuXUATKHO + "' ";
			System.out.println(query);
			if(!db.update(query))
			{
					msg = "Không thể cập nhật trạng thái ERP_YCXUATKHONPP:  " + query;
				return msg;
			}	
		}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return "Exception  HuyPhieuXuatKho_CapTren_NPP ";
		}
		return msg;
	}
	
	
	
	private String getSearchQuery(HttpServletRequest request, IErpHoadontaichinhNPPList obj)
	{
		
		String query= "select distinct a.PK_SEQ, a.trangthai, a.ngayxuatHD, a.sohoadon + a.kyhieu as sohoadon,isnull( kb.ten,'') as kenhbanhang, NV.TEN as nguoitao, a.tongtienavat as tongtien , " +
					" case when a.NPP_DAT_FK is not null then npp.TEN else KH.TEN end as khTEN, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua  " +
					"from ERP_HOADONNPP a " +
					"left join KHACHHANG KH on a.KHACHHANG_FK=KH.PK_SEQ  " +
					"left join NHAPHANPHOI npp on a.NPP_DAT_FK=npp.PK_SEQ  " +
					"inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
					"inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ " +
					" left join kenhbanhang kb on kb.pk_seq=a.kbh_fk "+
					"where a.pk_seq > 0 ";
		
		/*String 	query = "select a.PK_SEQ, a.trangthai, a.ngayxuatHD, NV.TEN as nguoitao, a.tongtienavat as tongtien , " +
		" case when a.NPP_DAT_FK is not null then npp.TEN else KH.TEN end as khTEN, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua  " +
		"from ERP_HOADONNPP a " +
		"left join KHACHHANG KH on a.KHACHHANG_FK=KH.PK_SEQ  " +
		"left join NHAPHANPHOI npp on a.NPP_DAT_FK=npp.PK_SEQ  " +
		"inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
		"inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.pk_seq > 0 ";*/
		
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
		
		String khten = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khTen"));
		String[] loai = khten.split("--");   	
    	obj.setKhTen(khten);
		
		String sohoadon = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sohoadon"));
		if(sohoadon == null)
			sohoadon = "";
		obj.setSohoadon(sohoadon);
		
		String sodonhang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sodonhang"));
		if(sodonhang == null)
			sodonhang = "";
		obj.setSodonhang(sodonhang);
		
		if(sohoadon.length()>0)
			query += " and a.sohoadon LIKE '%" + sohoadon + "%'";
		
		if(tungay.length() > 0)
			query += " and a.ngayxuatHD >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.ngayxuatHD <= '" + denngay + "'";
	
		if(trangthai.length() > 0)
		{
			if(trangthai.equals("3")||trangthai.equals("5") )
				query += " and a.TrangThai in (3,5) ";
			else
				query += " and a.TrangThai = '" + trangthai + "'";
		}
		
		if(khten.length() > 0)
			query += " and ( kh.pk_seq ='" + loai[1] + "' or a.npp_dat_fk = '" + loai[1] + "' ) ";
		
		if(sodonhang.length()>0)
			query += " and a.pk_seq in ( select HOADONNPP_FK from Erp_HoaDonNPP_DDH WHERE Cast(DDH_fK as varchar(20))  LIKE '%"+sodonhang+"%' ) ";
		
		System.out.print("câu tìm kiếm " + query);
		return query;
	}
		
	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private String getSumQuery(HttpServletRequest request, IErpHoadontaichinhNPPList obj) 
	{
		String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
    	if ( nppId == null)
    		nppId = "";
    	obj.setnppId(nppId);
    	
    	String trangthai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"));
    	if (trangthai == null)
    		trangthai = "";    	
    	obj.setTrangthai(trangthai);
    	
    	String tungay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"));
    	if (tungay == null)
    		tungay = "";    	
    	obj.setTungay(tungay);
    	
    	String denngay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay"));
    	if (denngay == null)
    		denngay = "";    	
    	obj.setDenngay(denngay);
    	
    	String sohoadon = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sohoadon"));
		if(sohoadon == null)
			sohoadon = "";
		obj.setSohoadon(sohoadon);
		
		String sodonhang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sodonhang"));
		if(sodonhang == null)
			sodonhang = "";
		obj.setSodonhang(sodonhang);
    	
    	String khachhang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khTen"));
    	String[] loai = khachhang.split("--");   	
    	obj.setKhTen(khachhang);
    	    	
    	if ( nppId.length() == 0 && trangthai.length() == 0 && tungay.length() == 0 && denngay.length() == 0 && sodonhang.length() == 0 && khachhang.length()==0)
    		obj.setIsSearch(false);
    	else
    		obj.setIsSearch(true);
    	
    	String query =
    		" select " +
    		" sum(hd_npp.tongtienbvat) as doanhso,  sum(hd_npp.vat) as thue, sum(tongtienbvat + vat - chietkhau) as doanhthu "+
    		" from	ERP_HOADONNPP hd_npp inner join ERP_HOADONNPP_DDH hd_ddh on hd_npp.PK_SEQ = hd_ddh.HOADONNPP_FK "+
    		" where	1=1 ";     	
    	if (nppId.length() > 0)
    	{
			query = query + " and hd_npp.NPP_FK = '" + nppId + "'";		
    	} 
    	    	
    	if (trangthai.length() > 0)
    	{    		
    		if(trangthai.equals("3")||trangthai.equals("5") )
				query += " and hd_npp.TrangThai in (3,5) ";
			else
				query += " and hd_npp.TrangThai = '" + trangthai + "'";
    		
    	}
    	else
    		query += " and hd_npp.TRANGTHAI not in (3,5) ";
    	
    	if (tungay.length() > 0)
    	{
			query = query + " and hd_npp.NGAYXUATHD >= '" + tungay + "'";			
    	}    	
    	if (denngay.length() > 0)
    	{
			query = query + " and hd_npp.NGAYXUATHD <= '" + denngay + "'";	
    	}
    	if (sodonhang.length() > 0)
    	{
    		query = query + " and hd_npp.pk_seq like '%" + sodonhang + "%'";	
    	}
    	
    	if (khachhang.length() > 0)
    	{
    		if(loai[0].equals("ETC")){
    			query = query + " and hd_npp.KHACHHANG_FK='"+loai[1]+"' ";
    		}
    		else{
    			query = query + " and hd_npp.NPP_DAT_FK='"+loai[1]+"' ";
    		}
    	}
    	
    	if(sohoadon.length()>0)
    	{
    		query= query+ " and hd_npp.SOHOADON like '%"+sohoadon+"%' ";
    	}
    	    	
    	System.out.println("\nQuery cua ban: " + query);
    	return query;
	}
	
}
