package geso.dms.distributor.servlets.dondathang;

import geso.dms.distributor.beans.dondathang.IErpDuyetddhNppList;
import geso.dms.distributor.beans.dondathang.imp.ErpDuyetddhNppList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpDuyetddhNppSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpDuyetddhNppSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpDuyetddhNppList obj;
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
	    obj = new ErpDuyetddhNppList();
	    
    	if(action.equals("duyet"))
    	{
    		String msg = this.Chot(lsxId, userId);
    		obj.setMsg(msg);
    	}
	    
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
			
		String nextJSP = "/AHF/pages/Distributor/ErpDonDatHangNppDuyet.jsp";
		response.sendRedirect(nextJSP);
	    
	}

	private String Chot(String id, String userId) 
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			//CHECK THANG KHOA SO
			String query = "select top(1) NAM as namMax, THANGKS as thangMax, " +
							"	( select ngaydonhang from ERP_DondathangNPP where pk_seq = '" + id + "' ) as ngaylapphieu " +
							"from KHOASOTHANG a where NPP_FK = ( select npp_fk from ERP_DondathangNPP where pk_seq = '" + id + "' ) " +
							"order by NAM desc, THANGKS desc ";
			System.out.println("1.Khoi tao thang: " + query);
			ResultSet rs = db.get(query);
			
			try
			{
				if(rs != null)
				{
					while(rs.next())
					{
						String thangHL = "";
						String namHL = "";
						
						String thangKs = rs.getString("thangMax");
						String namKs = rs.getString("namMax"); 
				
						String nam = rs.getString("ngaylapphieu").substring(0, 4);
						String thang = rs.getString("ngaylapphieu").substring(5, 7);
						
						if(thangKs.equals("12"))
						{
							thangHL = "1";
							namHL = Integer.toString(Integer.parseInt(namKs) + 1);
						}
						else
						{
							thangHL = Integer.toString(Integer.parseInt(thangKs) + 1);
							namHL = namKs;
						}
						
						if(thangHL.trim().length() < 2)
							thangHL = "0" + thangHL;
						
						if(	!thangHL.equals(thang) || !namHL.equals(nam) )
						{
							msg = "Bạn chỉ được phép duyệt đơn hàng sau tháng khóa sổ 1 tháng";
							rs.close();
							return msg;
						}
						
					}
					rs.close();
				}
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				return "Lỗi khi duyệt đơn hàng " + e.getMessage();
			}
			
			db.getConnection().setAutoCommit(false);
			
			query = "select khachhang_fk, a.kbh_fk, a.npp_fk, a.npp_dat_fk, " +
						"( select priandsecond from NHAPHANPHOI where pk_seq = a.npp_fk ) as priandsecond, " +
						"( select loaiNPP from NHAPHANPHOI where pk_seq = a.npp_fk ) as loaiNPP, " +
						"( select tructhuoc_fk from NHAPHANPHOI where pk_seq = a.npp_fk ) as tructhuoc_fk  " +
					"from ERP_DondathangNPP a where a.pk_seq = '" + id + "' order by pk_seq desc";
			String khachhangID = "";
			String loaiNPP = "";
			String tructhuoc = "";
			String nppId = "";
			String npp_dat_fk = "";
			String kbh_fk = "";
			String tuquanlyKHO = "0";
			
			rs = db.get(query);
			if(rs != null)
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
					
					kbh_fk = rs.getString("kbh_fk");
					
					if(rs.getString("priandsecond") != null)
						tuquanlyKHO = rs.getString("priandsecond");
					
				}
				rs.close();
			}
			
			//Nếu là khách hàng ETC của CN2, đối tác thì cấp trên xuât kho  (---> neu co check la tu quan ly kho thi tru luon cua no)
			if( khachhangID.trim().length() > 0 && ( loaiNPP.equals("1") || loaiNPP.equals("4") ) && tuquanlyKHO.equals("0") )
			{
				if(tructhuoc.trim().length() >= 5) //TRỰC THUỘC NPP
				{
					msg = this.TaoPhieuXuatKho_CapTren_NPP(db, id, userId, "100000", nppId, tructhuoc, kbh_fk );
					if(msg.trim().length() > 0)
					{
						msg = "Khong the chot: " + query;
						db.getConnection().rollback();
						return msg;
					}
				}
				else  //PHIẾU YCXK của HO
				{
					msg = this.TaoPhieuXuatKho_CapTren_HO(db, id, userId, "100001", nppId, tructhuoc, kbh_fk );
					if(msg.trim().length() > 0)
					{
						msg = "Khong the chot: " + query;
						db.getConnection().rollback();
						return msg;
					}
				}
			}
			else  //Tạo PXK cho NPP
			{
				msg = this.TaoPhieuXuatKhoNPP(db, id, userId, "100000", nppId, npp_dat_fk, kbh_fk );
				if(msg.trim().length() > 0)
				{
					msg = "Khong the chot: " + query;
					db.getConnection().rollback();
					return msg;
				}
			}
			
			
			query = "update ERP_DondathangNPP set trangthai = '4', NPP_DACHOT = '1', nguoisua = '" + userId + "' where pk_seq = '" + id + "'";
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
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			db.shutDown();
			return "Exception: " + e.getMessage();
		}
		
		return "";
	}

	private String TaoPhieuXuatKho_CapTren_NPP(dbutils db, String id, String userId, String khoId, String nppId, String tructhuoc, String kbh_fk) 
	{
		String query = "";
		String msg = "";
		
		try
		{
			//Tu dong tao YCXK  --> VA CHOT YCXK NAY LUON
			query = " insert ERP_YCXUATKHONPP(NgayYeuCau, ghichu, trangthai, npp_fk, kho_fk, xuatcho, npp_dat_fk, khachhang_fk, kbh_fk, ngaytao, nguoitao, ngaysua, nguoisua) " +
					" select '" + this.getDateTime() + "', N'Phiếu xuất kho xuất dùm cho khách hàng ETC của CN cấp 2 / đối tác (đơn hàng số: " + id + ")', '2', '" + tructhuoc + "', '100000', " +
							" '0' as xuatcho, '" + nppId + "' as npp_dat_fk, khachhang_fk, kbh_fk, '" + getDateTime() + "', '" + userId + "', '" + getDateTime() + "', '" + userId + "' " +
					" from ERP_DONDATHANGNPP where pk_seq = '" + id + "' ";
			
			System.out.println("1.Insert YCXUATKHO: " + query);
			if(!db.update(query))
			{
				msg = "Không thể tạo mới ERP_YCXUATKHONPP " + query;
				//db.getConnection().rollback();
				return msg;
			}
			
			String ycxkId = "";
			ResultSet rsYCXK = db.get("select IDENT_CURRENT('ERP_YCXUATKHONPP') as ycxkId");
			if(rsYCXK.next())
			{
				ycxkId = rsYCXK.getString("ycxkId");
			}
			rsYCXK.close();
			
			query = "Insert ERP_YCXUATKHONPP_DDH(ycxk_fk, ddh_fk) " +
					"select IDENT_CURRENT('ERP_YCXUATKHONPP'), pk_seq from ERP_DONDATHANGNPP where pk_seq in ( " + id + " )  ";
			if(!db.update(query))
			{
				msg = "Không thể tạo mới ERP_YCXUATKHONPP_DDH " + query;
				//db.getConnection().rollback();
				return msg;
			}
			
			query = "insert ERP_YCXUATKHONPP_SANPHAM( ycxk_fk, sanpham_fk, soluongDAT, tonkho, daxuat, soluongXUAT, LOAI, SCHEME ) " +
					"select IDENT_CURRENT('ERP_YCXUATKHONPP'), sp.PK_SEQ, SUM(dathang.soluong) as soluongDAT, ISNULL( ( select AVAILABLE from ERP_KHOTT_SANPHAM where khott_fk = '" + id + "' and sanpham_fk = sp.PK_SEQ ), 0)  as tonkho, 0, SUM(dathang.soluong) as soluongXUAT, loai, scheme  " +
					"from    " +
					"(    " +
					"		select a.sanpham_fk, b.DVDL_FK as dvCHUAN,    " +
					"				case when a.dvdl_fk IS null then a.soluong     " +
					"					 when a.dvdl_fk = b.DVDL_FK then a.soluong    " +
					"					 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )     " +
					"									 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )	 end as soluong, 0 as loai, ' ' as scheme   " +
					"		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ    " +
					"		where a.dondathang_fk in ( '" + id + "' )   " +
					")    " +
					"dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ     " +
					"group by sp.PK_SEQ, scheme, loai  " ;
			
			System.out.println("1.1.Insert YCXK - SP: " + query);
			if(!db.update(query))
			{
				msg = "Khong the tao moi ERP_YCXUATKHO_SANPHAM: " + query;
				//db.getConnection().rollback();
				return msg;
			}
			
			//CHECK TON KHO
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
					"	where a.dondathang_fk in ( " + id + " )     " +
					")     " +
					"dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ   " +
					"group by khoxuat_fk, npp_fk, kbh_fk, sp.PK_SEQ, sp.TEN  ";
			
			System.out.println("--CHECK TON KHO: " + query);
			
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				while(rs.next())
				{
					String khoID = rs.getString("kho_fk");
					String kbhID = rs.getString("kbh_fk");
					String nppID = rs.getString("npp_fk");
					String spID = rs.getString("PK_SEQ");
					
					double soluong = rs.getDouble("soluongXUAT");
					double tonkho = rs.getDouble("tonkho");
					
					if(soluong > tonkho)
					{
						msg = "Sản phẩm ( " + rs.getString("TEN") + " ) với số lượng yêu cầu ( " + rs.getString("soluongXUAT") + " ) không đủ tồn kho ( " + rs.getString("tonkho") + " ). Vui lòng liên hệ với chi nhánh cấp trên để xử lý.";
						//db.getConnection().rollback();
						rs.close();
						return msg;
					}
					
					//CAP NHAT KHO XUAT TONG
					query = "Update NHAPP_KHO set soluong = soluong - '" + soluong + "', AVAILABLE = AVAILABLE - '" + soluong + "' " +
							"where KHO_FK = '"+khoID+"' and KBH_FK = '" + kbhID + "' and NPP_FK = '" + nppID + "' and SANPHAM_FK = '" + spID + "' ";
					if(db.updateReturnInt(query)!=1)
					{
						msg = "Khong the cap nhat NHAPP_KHO: " + query;
						//db.getConnection().rollback();
						rs.close();
						return msg;
					}
					
					
					//CAP NHAT KHO CHI TIET
					query = "select AVAILABLE, SOLO, ngayhethan from NHAPP_KHO_CHITIET " +
							"where AVAILABLE > 0 and KHO_FK = '" + khoID + "'  and SANPHAM_FK = '" + spID + "' and NPP_FK = '" + nppID + "' and KBH_FK = '" + kbhID + "' order by ngayhethan asc ";
					
					ResultSet rsTK = db.get(query);
					double avai = 0;
					double totalXUAT = 0;
					while(rsTK.next())
					{
						double soluongCT = 0;
						String solo = rsTK.getString("SOLO");
						String ngayhethan = rsTK.getString("ngayhethan");
						
						avai = rsTK.getDouble("AVAILABLE");
						totalXUAT += avai;
						
						if(totalXUAT <= soluong)
						{
							soluongCT = avai;
							
							query = "insert ERP_YCXUATKHONPP_SANPHAM_CHITIET( ycxk_fk, SANPHAM_FK, solo, soluong, ngayhethan ) " +
									"select '" + ycxkId + "', '" + spID + "', N'" + solo + "', '" + soluongCT  + "', '" + ngayhethan + "' ";
							
							System.out.println("1.2.Insert YCXK - SP - CT: " + query);
							if(!db.update(query))
							{
								msg = "Khong the tao moi ERP_YCXUATKHONPP_SANPHAM_CHITIET: " + query;
								//db.getConnection().rollback();
								rs.close();
								return msg;
							}
							
							query = "Update NHAPP_KHO_CHITIET set soluong = soluong - '" + soluongCT + "', AVAILABLE = AVAILABLE - '" + soluongCT + "' " +
									"where KHO_FK = '" + khoID + "' and SOLO = '" + solo + "' and SANPHAM_FK = '" + spID + "' adn KBH_FK = '" + kbhID + "' and NPP_FK = '" + nppID + "' and ngayHetHan='"+ngayhethan+"' ";
							if(db.updateReturnInt(query)!=1)
							{
								msg = "Khong the cap nhat NHAPP_KHO_CHITIET: " + query;
								//db.getConnection().rollback();
								rs.close();
								return msg;
							}
					
						}
						else
						{
							soluongCT = soluong - ( totalXUAT - avai );
							
							query = "insert ERP_YCXUATKHONPP_SANPHAM_CHITIET( ycxk_fk, SANPHAM_FK, solo, soluong, ngayhethan ) " +
									"select '" + ycxkId + "', '" + spID + "', N'" + solo + "', '" + soluongCT + "', '" + ngayhethan + "' ";
							
							System.out.println("1.2.Insert YCXK - SP - CT: " + query);
							if(!db.update(query))
							{
								msg = "Khong the tao moi ERP_YCXUATKHONPP_SANPHAM_CHITIET: " + query;
								//db.getConnection().rollback();
								rs.close();
								return msg;
							}
							
							query = "Update NHAPP_KHO_CHITIET set soluong = soluong - '" + soluongCT + "', AVAILABLE = AVAILABLE - '" + soluongCT + "' " +
									"where KHO_FK = '" + khoID + "' and SOLO = '" + solo + "' and SANPHAM_FK = '" + spID + "' and KBH_FK = '" + kbhID + "' and NPP_FK = '" + nppID + "' and ngayhethan='"+ngayhethan+"' ";
							if(db.updateReturnInt(query)!=1)
							{
								msg = "Khong the cap nhat NHAPP_KHO_CHITIET: " + query;
								//db.getConnection().rollback();
								rs.close();
								return msg;
							}
						
							break;
						}
					}
					rsTK.close();
				}
				rs.close();
			}
		} 
		catch (Exception e) {
			
			e.printStackTrace();
			return "Không thể duyệt đơn hàng " + e.getMessage();
		}
		
		return "";
	}

	private String TaoPhieuXuatKho_CapTren_HO(dbutils db, String id, String userId, String khoNhanId, String nppId, String tructhuoc, String kbh_fk) 
	{
		String msg = "";
		String query = "";
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			//CHECK TON KHO
			query = "select sp.PK_SEQ, sp.TEN, SUM(dathang.soluong) as soluongXUAT, " +
					"	ISNULL( ( select AVAILABLE from ERP_KHOTT_SANPHAM where khott_fk = '" + khoNhanId + "' and sanpham_fk = sp.PK_SEQ ), 0) as tonkho " +
					"from    " +
					"(    " +
					"		select a.sanpham_fk, b.DVDL_FK as dvCHUAN,    " +
					"				case when a.dvdl_fk IS null then a.soluong     " +
					"					 when a.dvdl_fk = b.DVDL_FK then a.soluong    " +
					"					 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )     " +
					"									 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )	 end as soluong, 0 as loai, ' ' as scheme   " +
					"		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ    " +
					"		where a.dondathang_fk in ( '" + id + "' )   " +
					")    " +
					"dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ  " +
					"group by sp.PK_SEQ, sp.TEN " +
					"having  SUM(dathang.soluong) > ISNULL( ( select AVAILABLE from ERP_KHOTT_SANPHAM where khott_fk = '" + khoNhanId + "' and sanpham_fk = sp.PK_SEQ ), 0) " ;
			
			System.out.println("--CHECK TON KHO: " + query);
			
			ResultSet rsCHECK = db.get(query);
			if(rsCHECK != null)
			{
				while(rsCHECK.next())
				{
					msg = "Sản phẩm ( " + rsCHECK.getString("TEN") + " ) với số lượng yêu cầu ( " + rsCHECK.getString("soluongXUAT") + " ) không đủ tồn kho ( " + rsCHECK.getString("tonkho") + " ) của HO. Vui lòng liên hệ với HO để xử lý.";
					//db.getConnection().rollback();
					rsCHECK.close();
					return msg;
				}
				rsCHECK.close();
			}
			
			//Tu dong tao YCXK  --> VA CHOT YCXK NAY LUON
			query = " insert ERP_YCXUATKHO(NgayYeuCau, ghichu, trangthai, npp_fk, kho_fk, ngaytao, nguoitao, ngaysua, nguoisua) " +
					" values('" + this.getDateTime() + "', N'Phiếu xuất kho xuất dùm cho khách hàng ETC của CN cấp 2 / đối tác (đơn hàng số: " + id + ")', '2', '" + nppId + "', " + khoNhanId + ", '" + getDateTime() + "', '" + userId + "', '" + getDateTime() + "', '" + userId + "' )";
			
			System.out.println("1.Insert YCXUATKHO: " + query);
			if(!db.update(query))
			{
				msg = "Không thể tạo mới ERP_YCXUATKHO " + query;
				//db.getConnection().rollback();
				return msg;
			}
			
			query = "Insert ERP_YCXUATKHO_DDH(ycxk_fk, ddh_fk) " +
					"select IDENT_CURRENT('ERP_YCXUATKHO'), pk_seq from ERP_DONDATHANG where pk_seq in ( " + id + " )  ";
			if(!db.update(query))
			{
				msg = "Không thể tạo mới ERP_YCXUATKHO_DDH " + query;
				//db.getConnection().rollback();
				return msg;
			}
			
			query = "insert ERP_YCXUATKHO_SANPHAM( ycxk_fk, sanpham_fk, soluongDAT, tonkho, daxuat, soluongXUAT, LOAI, SCHEME ) " +
					"select IDENT_CURRENT('ERP_YCXUATKHO'), sp.PK_SEQ, SUM(dathang.soluong) as soluongDAT, ISNULL( ( select AVAILABLE from ERP_KHOTT_SANPHAM where khott_fk = '" + khoNhanId + "' and sanpham_fk = sp.PK_SEQ ), 0)  as tonkho, 0, SUM(dathang.soluong) as soluongXUAT, loai, scheme  " +
					"from    " +
					"(    " +
					"		select a.sanpham_fk, b.DVDL_FK as dvCHUAN,    " +
					"				case when a.dvdl_fk IS null then a.soluong     " +
					"					 when a.dvdl_fk = b.DVDL_FK then a.soluong    " +
					"					 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )     " +
					"									 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )	 end as soluong, 0 as loai, ' ' as scheme   " +
					"		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ    " +
					"		where a.dondathang_fk in ( '" + id + "' )   " +
					")    " +
					"dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ     " +
					"group by sp.PK_SEQ, scheme, loai  " ;
			
			System.out.println("1.1.Insert YCXK - SP: " + query);
			if(!db.update(query))
			{
				msg = "Khong the tao moi ERP_YCXUATKHO_SANPHAM: " + query;
				//db.getConnection().rollback();
				return msg;
			}
			
			query = "select sp.PK_SEQ, sp.TEN, LOAI, SCHEME, SUM(dathang.soluong) as soluongXUAT " +
					"from    " +
					"(    " +
					"		select a.sanpham_fk, b.DVDL_FK as dvCHUAN,    " +
					"				case when a.dvdl_fk IS null then a.soluong     " +
					"					 when a.dvdl_fk = b.DVDL_FK then a.soluong    " +
					"					 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )     " +
					"									 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )	 end as soluong, 0 as loai, ' ' as scheme   " +
					"		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ    " +
					"		where a.dondathang_fk in ( '" + id + "' )   " +
					")    " +
					"dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ  " +
					"group by sp.PK_SEQ, sp.TEN, LOAI, SCHEME ";
			System.out.println("--CHECK KHO CHI TIET: " + query);
			rsCHECK = db.get(query);
			if(rsCHECK != null)
			{
				while(rsCHECK.next())
				{
					String sanpham_fk = rsCHECK.getString("PK_SEQ");
					String LOAI = rsCHECK.getString("LOAI");
					String SCHEME = rsCHECK.getString("SCHEME");
					double soLUONG = rsCHECK.getDouble("soluongXUAT");
					
					query = "Update ERP_KHOTT_SANPHAM set soluong = soluong - '" + soLUONG + "', AVAILABLE = AVAILABLE - '" + soLUONG + "' " +
							"where KHOTT_FK = '" + khoNhanId + "' and SANPHAM_FK = '" + sanpham_fk + "'  ";
					if(!db.update(query))
					{
						msg = "Khong the cap nhat ERP_KHOTT_SANPHAM: " + query;
						//db.getConnection().rollback();
						return msg;
					}
					
					//CAP NHAT KHO CHI TIET
					query = "select AVAILABLE, SOLO from ERP_KHOTT_SP_CHITIET " +
							"where KHOTT_FK = '" + khoNhanId + "'  and SANPHAM_FK = '" + sanpham_fk + "' order by ngayhethan asc ";
					
					ResultSet rsTK = db.get(query);
					double avai = 0;
					double totalXUAT = 0;
					while(rsTK.next())
					{
						double soluongCT = 0;
						String solo = rsTK.getString("SOLO");
						
						avai = rsTK.getDouble("AVAILABLE");
						totalXUAT += avai;
						
						if(totalXUAT <= soLUONG)
						{
							soluongCT = avai;
							
							query = "insert ERP_YCXUATKHO_SANPHAM_CHITIET( ycxk_fk, SANPHAM_FK, solo, soluong, loai, scheme ) " +
									"select IDENT_CURRENT('ERP_YCXUATKHO'), '" + sanpham_fk + "', N'" + solo + "', '" + soluongCT + "', '" + LOAI + "', '" + SCHEME + "' ";
							
							System.out.println("1.2.Insert YCXK - SP - CT: " + query);
							if(!db.update(query))
							{
								msg = "Khong the tao moi ERP_YCXUATKHO_SANPHAM_CHITIET: " + query;
								//db.getConnection().rollback();
								return msg;
							}
							
							query = "Update ERP_KHOTT_SP_CHITIET set soluong = soluong - '" + soluongCT + "', AVAILABLE = AVAILABLE - '" + soluongCT + "' " +
									"where KHOTT_FK = '" + khoNhanId + "' and SOLO = '" + solo + "' and SANPHAM_FK = '" + sanpham_fk + "'  ";
							if(!db.update(query))
							{
								msg = "Khong the cap nhat ERP_KHOTT_SP_CHITIET: " + query;
								//db.getConnection().rollback();
								return msg;
							}
							
						}
						else
						{
							soluongCT = soLUONG - ( totalXUAT - avai );
							
							query = "insert ERP_YCXUATKHO_SANPHAM_CHITIET( ycxk_fk, SANPHAM_FK, solo, soluong, loai, scheme ) " +
									"select IDENT_CURRENT('ERP_YCXUATKHO'), '" + sanpham_fk + "', N'" + solo + "', '" + soluongCT + "', '" + LOAI + "', '" + SCHEME + "' ";
							
							System.out.println("1.2.Insert YCXK - SP - CT: " + query);
							if(!db.update(query))
							{
								msg = "Khong the tao moi ERP_YCXUATKHO_SANPHAM_CHITIET: " + query;
								//db.getConnection().rollback();
								return msg;
							}
							
							query = "Update ERP_KHOTT_SP_CHITIET set soluong = soluong - '" + soluongCT + "', AVAILABLE = AVAILABLE - '" + soluongCT + "' " +
									"where KHOTT_FK = '" + khoNhanId + "' and SOLO = '" + solo + "' and SANPHAM_FK = '" + sanpham_fk + "'  ";
							if(!db.update(query))
							{
								msg = "Khong the cap nhat ERP_KHOTT_SP_CHITIET: " + query;
								//db.getConnection().rollback();
								return msg;
							}
							
							break;
						}
					}
					rsTK.close();

				}
				rsCHECK.close();
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

	private String TaoPhieuXuatKhoNPP(dbutils db, String id, String userId, String khoNhanId, String nppId, String npp_dat_fk, String kbh_fk)
	{
		
		System.out.println("______________TaoPhieuXuatKhoNPP______________");
		
		String query = "";
		String msg = "";
		
		try
		{
			//Tu dong tao YCXK  --> VA CHOT YCXK NAY LUON
			query = " insert ERP_YCXUATKHONPP(NgayYeuCau, ghichu, trangthai, npp_fk, kho_fk, xuatcho, npp_dat_fk, khachhang_fk, kbh_fk, ngaytao, nguoitao, ngaysua, nguoisua) " +
					" select '" + this.getDateTime() + "', N'Phiếu xuất kho tạo tự động từ duyệt đơn đặt hàng " + id + "', '2', '" + nppId + "', " + khoNhanId + ", " +
							" case when npp_dat_fk is not null then '0' else '1' end as xuatcho, npp_dat_fk, khachhang_fk, kbh_fk, '" + getDateTime() + "', '" + userId + "', '" + getDateTime() + "', '" + userId + "' " +
					" from ERP_DONDATHANGNPP where pk_seq = '" + id + "' ";
			
			System.out.println("1.Insert YCXUATKHO: " + query);
			if(!db.update(query))
			{
				msg = "Không thể tạo mới ERP_YCXUATKHONPP " + query;
				return msg;
			}
			
			String ycxkId = "";
			ResultSet rsYCXK = db.get("select IDENT_CURRENT('ERP_YCXUATKHONPP') as ycxkId");
			if(rsYCXK.next())
			{
				ycxkId = rsYCXK.getString("ycxkId");
			}
			rsYCXK.close();
			
			query = "Insert ERP_YCXUATKHONPP_DDH(ycxk_fk, ddh_fk) " +
					"select IDENT_CURRENT('ERP_YCXUATKHONPP'), pk_seq from ERP_DONDATHANGNPP where pk_seq in ( " + id + " )  ";
			if(!db.update(query))
			{
				msg = "Không thể tạo mới ERP_YCXUATKHONPP_DDH " + query;
				return msg;
			}
			
			query = "insert ERP_YCXUATKHONPP_SANPHAM( ycxk_fk, sanpham_fk, soluongDAT, tonkho, daxuat, soluongXUAT, LOAI, SCHEME ) " +
					"select IDENT_CURRENT('ERP_YCXUATKHONPP'), sp.PK_SEQ, SUM(dathang.soluong) as soluongDAT, ISNULL( ( select AVAILABLE from ERP_KHOTT_SANPHAM where khott_fk = '" + id + "' and sanpham_fk = sp.PK_SEQ ), 0)  as tonkho, 0, SUM(dathang.soluong) as soluongXUAT, loai, scheme  " +
					"from    " +
					"(    " +
					"		select a.sanpham_fk, b.DVDL_FK as dvCHUAN,    " +
					"				case when a.dvdl_fk IS null then a.soluong     " +
					"					 when a.dvdl_fk = b.DVDL_FK then a.soluong    " +
					"					 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )     " +
					"									 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )	 end as soluong, 0 as loai, ' ' as scheme   " +
					"		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ    " +
					"		where a.dondathang_fk in ( '" + id + "' )   " +
					")    " +
					"dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ     " +
					"group by sp.PK_SEQ, scheme, loai  " ;
			
			System.out.println("1.1.Insert YCXK - SP: " + query);
			if(!db.update(query))
			{
				msg = "Khong the tao moi ERP_YCXUATKHO_SANPHAM: " + query;
				return msg;
			}
			
			//CHECK TON KHO
			query =  "select khoxuat_fk as kho_fk, npp_fk, kbh_fk, sp.PK_SEQ, sp.TEN, SUM(dathang.soluong) as soluongXUAT,  " +
					"	ISNULL( ( select AVAILABLE from NHAPP_KHO where kho_fk = dathang.khoxuat_fk and sanpham_fk = sp.PK_SEQ and kbh_fk = dathang.kbh_fk and npp_fk = dathang.npp_fk ), 0) as tonkho  " +
					"from     " +
					"(     " +
					"	select c.kho_fk as khoxuat_fk, c.npp_fk,case when d.dungchungkenh=1 then '100025' else c.kbh_fk end  as kbh_fk , a.sanpham_fk, b.DVDL_FK as dvCHUAN,     " +
					"			case when a.dvdl_fk IS null then a.soluong      " +
					"				 when a.dvdl_fk = b.DVDL_FK then a.soluong     " +
					"				 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and dvdl1_fk = b.dvdl_fk )      " +
					"								 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and dvdl1_fk = b.dvdl_fk )	 end as soluong   " +
					"	from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ  " +
					"			inner join ERP_DONDATHANGNPP c on a.dondathang_fk = c.pk_seq " +
					"     inner join nhaphanphoi d on d.pk_Seq=c.npp_fk                                       " +
					"	where a.dondathang_fk in ( " + id + " )     " +
					")     " +
					"dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ   " +
					"group by khoxuat_fk, npp_fk, kbh_fk, sp.PK_SEQ, sp.TEN  ";
			
			System.out.println("--CHECK TON KHO: " + query);
			
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				while(rs.next())
				{
					String khoID = rs.getString("kho_fk");
					String kbhID = rs.getString("kbh_fk");
					String nppID = rs.getString("npp_fk");
					String spID = rs.getString("PK_SEQ");
					
					double soluong = rs.getDouble("soluongXUAT");
					double tonkho = rs.getDouble("tonkho");
					
					if(soluong > tonkho)
					{
						msg = "Sản phẩm ( " + rs.getString("TEN") + " ) với số lượng yêu cầu ( " + rs.getString("soluongXUAT") + " ) không đủ tồn kho ( " + rs.getString("tonkho") + " ). Vui lòng kiểm tra lại.";
						rs.close();
						return msg;
					}
					
					//CAP NHAT KHO XUAT TONG
					query = "Update NHAPP_KHO set soluong = soluong - '" + soluong + "', BOOKED = BOOKED - '" + soluong + "' " +
							"where KHO_FK = '"+khoID+"' and KBH_FK = '" + kbhID + "' and NPP_FK = '" + nppID + "' and SANPHAM_FK = '" + spID + "' ";
					if(db.updateReturnInt(query)!=1)
					{
						msg = "Khong the cap nhat NHAPP_KHO: " + query;
						rs.close();
						return msg;
					}
					
					
					//CAP NHAT KHO CHI TIET
					query = "select AVAILABLE, SOLO, ngayhethan from NHAPP_KHO_CHITIET " +
							"where AVAILABLE > 0 and KHO_FK = '" + khoID + "'  and SANPHAM_FK = '" + spID + "' and NPP_FK = '" + nppID + "' and KBH_FK = '" + kbhID + "' order by ngayhethan asc ";
					
					ResultSet rsTK = db.get(query);
					double avai = 0;
					double totalXUAT = 0;
					while(rsTK.next())
					{
						double soluongCT = 0;
						String solo = rsTK.getString("SOLO");
						String ngayhethan = rsTK.getString("ngayhethan");
						
						avai = rsTK.getDouble("AVAILABLE");
						totalXUAT += avai;
						
						if(totalXUAT <= soluong)
						{
							soluongCT = avai;
							
							query = "insert ERP_YCXUATKHONPP_SANPHAM_CHITIET( ycxk_fk, SANPHAM_FK, solo, soluong, ngayhethan ) " +
									"select '" + ycxkId + "', '" + spID + "', N'" + solo + "', '" + soluongCT  + "', '" + ngayhethan + "' ";
							
							System.out.println("1.2.Insert YCXK - SP - CT: " + query);
							if(!db.update(query))
							{
								msg = "Khong the tao moi ERP_YCXUATKHONPP_SANPHAM_CHITIET: " + query;
								rs.close();
								return msg;
							}
							
							query = "Update NHAPP_KHO_CHITIET set soluong = soluong - '" + soluongCT + "', AVAILABLE = AVAILABLE - '" + soluongCT + "' " +
									"where KHO_FK = '" + khoID + "' and SOLO = '" + solo + "' and SANPHAM_FK = '" + spID + "' adn KBH_FK = '" + kbhID + "' and NPP_FK = '" + nppID + "' and ngayhethan='"+ngayhethan+"' ";
							if(db.updateReturnInt(query)!=1)
							{
								msg = "Khong the cap nhat NHAPP_KHO_CHITIET: " + query;
								rs.close();
								return msg;
							}
						}
						else
						{
							soluongCT = soluong - ( totalXUAT - avai );
							
							query = "insert ERP_YCXUATKHONPP_SANPHAM_CHITIET( ycxk_fk, SANPHAM_FK, solo, soluong, ngayhethan ) " +
									"select '" + ycxkId + "', '" + spID + "', N'" + solo + "', '" + soluongCT + "', '" + ngayhethan + "' ";
							
							System.out.println("1.2.Insert YCXK - SP - CT: " + query);
							if(!db.update(query))
							{
								msg = "Khong the tao moi ERP_YCXUATKHONPP_SANPHAM_CHITIET: " + query;
								//db.getConnection().rollback();
								rs.close();
								return msg;
							}
							
							query = "Update NHAPP_KHO_CHITIET set soluong = soluong - '" + soluongCT + "', AVAILABLE = AVAILABLE - '" + soluongCT + "' " +
									"where KHO_FK = '" + khoID + "' and SOLO = '" + solo + "' and SANPHAM_FK = '" + spID + "' and KBH_FK = '" + kbhID + "' and NPP_FK = '" + nppID + "' and ngayhethan='"+ngayhethan+"' ";
							if(db.updateReturnInt(query)!=1)
							{
								msg = "Khong the cap nhat NHAPP_KHO_CHITIET: " + query;
								//db.getConnection().rollback();
								rs.close();
								return msg;
							}
						
							break;
						}
					}
					rsTK.close();
				}
				rs.close();
			}
			
			if(npp_dat_fk.trim().length() > 0)
			{
				//Tu dong tao nhan hang
				query = " insert NHAPHANG(NGAYNHAN, NGAYCHUNGTU, NPP_FK, NCC_FK, GSBH_FK, ASM_FK, BM_FK, DVKD_FK, KBH_FK, YCXKNPP_FK, TRANGTHAI, NGUOITAO, NGAYTAO, NGUOISUA, NGAYSUA) " +
						" select distinct NgayYeuCau, NgayYeuCau, NPP_DAT_FK,  " +
						" 			( select top(1) NCC_FK from NHACUNGCAP_DVKD where PK_SEQ in ( select NCC_DVKD_FK from NHAPP_NHACC_DONVIKD where NPP_FK = a.NPP_DAT_FK ) ), " +
						"			( select top(1) GSBH_FK from NHAPP_GIAMSATBH where NPP_FK = a.NPP_DAT_FK ), " +
						"			( select top(1) ASM_FK from ASM_KHUVUC where KHUVUC_FK in ( select KHUVUC_FK from NHAPHANPHOI where PK_SEQ = a.NPP_DAT_FK )), " +
						"			( select top(1) BM_FK from BM_CHINHANH where VUNG_FK in ( select VUNG_FK from KHUVUC where PK_SEQ in (  select KHUVUC_FK from NHAPHANPHOI where PK_SEQ = a.NPP_DAT_FK ) ) ), " +
						" 	   '100001' as DVKD_FK, a.KBH_FK, '" + ycxkId + "', '0', '" + userId + "', '" + getDateTime() + "', '" + userId + "', '" + getDateTime() + "' " +
						" from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM b on a.PK_SEQ = b.ycxk_fk " +
						" where a.PK_SEQ = '" + ycxkId + "' ";
				
				System.out.println("---INSERT DON DAT HANG: " + query );
				if(!db.update(query))
				{
					msg = "Không tạo mới NHAPHANG " + query;
					return msg;
				}
				
				query = " insert NHAPHANG_SP(NHAPHANG_FK, SANPHAM_FK, SOLUONG, soluongNHAN, DONGIA, CHIETKHAU, DVDL_FK, LOAI, SCHEME, SOLO, NGAYHETHAN) " +
						" select ( select pk_seq from NHAPHANG where YCXKNPP_FK = a.PK_SEQ  ),  " +
						" 		b.sanpham_fk, b.soluong, NULL, b.dongia, 0 as chietkhau, c.DVDL_FK, b.LOAI, b.SCHEME, b.solo, b.ngayhethan " +
						" from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.PK_SEQ = b.ycxk_fk " +
						" 		inner join SANPHAM c on b.sanpham_fk = c.PK_SEQ   " +
						" where a.PK_SEQ = '" + ycxkId + "' and b.soluong > 0 ";
				if(!db.update(query))
				{
					msg = "Không tạo mới NHAPHANG_SP " + query;
					return msg;
				}
				
				query = "insert NHAPHANG_DDH(nhaphang_fk, ddh_fk)  " +
						"select ( select PK_SEQ from NHAPHANG where YCXKNPP_FK = '" + id + "' ) as nhID, ddh_fk  " +
						"from ERP_YCXUATKHONPP_DDH where ycxk_fk = '" + ycxkId + "'";
				if(!db.update(query))
				{
					msg = "Không tạo mới NHAPHANG_DDH " + query;
					return msg;
				}
			}
		} 
		catch (Exception e) {
			
			e.printStackTrace();
			return "Không thể duyệt đơn hàng " + e.getMessage();
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
	    
		IErpDuyetddhNppList obj = new ErpDuyetddhNppList();
	
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"))); 
	    obj.setUserId(userId);
	    
    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
    	{
	    	String search = getSearchQuery(request, obj);
	    	obj.setNxtApprSplitting(Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
	    	obj.setUserId(userId);
	    	obj.init(search);
	    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    	session.setAttribute("obj", obj);
	    	
	    	String nextJSP = "/AHF/pages/Distributor/ErpDonDatHangNppDuyet.jsp";
			response.sendRedirect(nextJSP);
	    }
    	else
    	{
	    	String search = getSearchQuery(request, obj);
	    	obj.setUserId(userId);
	    	obj.init(search);

	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	
    		String nextJSP = "/AHF/pages/Distributor/ErpDonDatHangNppDuyet.jsp";
    		response.sendRedirect(nextJSP);
    	}
	    
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpDuyetddhNppList obj)
	{
		Utility util = new Utility();
		String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
		if(nppId == null)
			nppId = "";
		obj.setNppTen(nppId);
		
		String query = "select a.PK_SEQ, a.trangthai, a.ngaydonhang, isnull( c.ten, d.ten) as nppTEN, a.loaidonhang, NV.TEN as nguoitao, b.ten as khonhan, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, isnull(a.NOTE, '') as NOTE, isnull(SOTIENTHU, 0) as SOTIENTHU, a.hopdong_fk    " +
						"from ERP_DondathangNPP a inner join KHO b on a.kho_fk = b.pk_seq " +
						"	left join NHAPHANPHOI c on a.NPP_DAT_FK = c.pk_seq " +
						"	left join KHACHHANG d on a.khachhang_fk = d.pk_seq  " +
						"inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
						"inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.npp_fk = '" + nppId + "' and a.pk_seq > 0  AND A.TRANGTHAI=1 and a.kho_fk in "+util.quyen_kho(obj.getUserId());
				
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
		
				
		String sodh = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sodh"));
		if(sodh == null)
			sodh = "";
		obj.setSodh(sodh);
		
		String khId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khId"));
		if(khId == null)
			khId = "";
		obj.setKhTen(khId);
		
		if(tungay.length() > 0)
			query += " and a.ngaydonhang >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.ngaydonhang <= '" + denngay + "'";
	
		if(trangthai.length() > 0)
			query += " and a.LoaiDonHang = '" + trangthai + "'";
		
		if(nppId.length() > 0){
			query += " and a.NPP_FK= '" + nppId + "'";
		}
		
		if(sodh.length()>0){
			query+= " and a.PK_SEQ LIKE '%"+sodh+"%'";
		}
		
		if(khId.length()>0){
			query += " and ( a.khachhang_fk= '" + khId + "' or a.npp_dat_fk = '" + khId + "' ) ";
		}
		System.out.print("caau tim kiem " + query);
		return query;
	}
		
	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	
}
