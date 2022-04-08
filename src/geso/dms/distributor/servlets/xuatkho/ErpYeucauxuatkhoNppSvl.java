package geso.dms.distributor.servlets.xuatkho;

import geso.dms.distributor.beans.xuatkho.IErpYeucauxuatkhoNpp;
import geso.dms.distributor.beans.xuatkho.IErpYeucauxuatkhoNppList;
import geso.dms.distributor.beans.xuatkho.imp.ErpYeucauxuatkhoNpp;
import geso.dms.distributor.beans.xuatkho.imp.ErpYeucauxuatkhoNppList;
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

public class ErpYeucauxuatkhoNppSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpYeucauxuatkhoNppSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpYeucauxuatkhoNppList obj;
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
	    obj = new ErpYeucauxuatkhoNppList();
	    
	    String loaidonhang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaidonhang"));
	    if(loaidonhang == null)
	    	loaidonhang = "0";
	    obj.setLoaidonhang(loaidonhang);
	    
	    String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
	    if(nppId == null)
	    	nppId = "";
	    //obj.setNppId(nppId);
	    System.out.println("---NPP ID: " + nppId);
	    
    	if(action.equals("chot"))
    	{
    		String msg = this.Chot(lsxId, userId, nppId);
			obj.setMsg(msg);
    	}
    	else if(action.equals("delete"))
    	{
    		String msg = this.Delete(lsxId, nppId);
			obj.setMsg(msg);
    	}
	    
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
			
		String nextJSP = "/AHF/pages/Distributor/ErpYeuCauXuatKhoNpp.jsp";
		response.sendRedirect(nextJSP);
	    
	}

	private String Delete(String lsxId, String nppId) 
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "";
			
			query = " update kho set kho.AVAILABLE = kho.AVAILABLE + CT.tongxuat, " +
					" 			   kho.BOOKED = kho.BOOKED - CT.tongxuat " +
					" from " +
					" ( " +
					" 	select a.kho_fk, a.kbh_fk, b.sanpham_fk, b.solo, SUM(b.soluong) as tongxuat,b.NgayHetHan  " +
					" 	from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk " +
					" 	where ycxk_fk = '" + lsxId + "' " +
					" 	group by a.kho_fk, a.kbh_fk, b.solo, b.sanpham_fk,b.NgayHetHan " +
					" ) " +
					" CT inner join NHAPP_KHO_CHITIET kho on CT.kho_fk = kho.KHO_FK  " +
					" 	and CT.sanpham_fk = kho.SANPHAM_FK and CT.solo = kho.SOLO and CT.KBH_FK = kho.KBH_FK and kho.NPP_FK = '" + nppId + "' and CT.NgayHetHan=kho.NgayHetHan ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_KHOTT_SP_CHITIET " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = " update kho set kho.AVAILABLE = kho.AVAILABLE + CT.tongxuat, " +
					" 			   kho.BOOKED = kho.BOOKED - CT.tongxuat " +
					" from " +
					" ( " +
					" 	select a.kho_fk, a.kbh_fk, b.sanpham_fk, SUM(b.soluong) as tongxuat  " +
					" 	from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk " +
					" 	where ycxk_fk = '" + lsxId + "' " +
					" 	group by a.kho_fk, a.kbh_fk, b.sanpham_fk " +
					" ) " +
					" CT inner join NHAPP_KHO kho on CT.kho_fk = kho.KHO_FK  " +
					" 	and CT.sanpham_fk = kho.SANPHAM_FK and CT.KBH_FK = kho.KBH_FK and kho.NPP_FK = '" + nppId + "'  ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_KHOTT_SANPHAM " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = "delete ERP_YCXUATKHONPP_DDH where ycxk_fk = '" + lsxId + "' ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_YCXUATKHONPP_DDH " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = "delete ERP_YCXUATKHONPP_SANPHAM where ycxk_fk = '" + lsxId + "' ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_YCXUATKHONPP_SANPHAM " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = "delete ERP_YCXUATKHONPP_SANPHAM_CHITIET where ycxk_fk = '" + lsxId + "' ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_YCXUATKHO_SANPHAM " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = "delete ERP_YCXUATKHONPP  where pk_seq = '" + lsxId + "' ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_YCXUATKHONPP " + query;
				db.getConnection().rollback();
				return msg;
			}
			
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
	
	private String Chot(String lsxId, String userId, String nppId) 
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "";
			
			//KHONG CAP NHAT KHO LUC NAY, LUC XAC NHAN XUAT KHO MOI TRU
			query = " update kho set kho.SOLUONG = kho.SOLUONG - CT.tongxuat, " +
					" 			   kho.BOOKED = kho.BOOKED - CT.tongxuat " +
					" from " +
					" ( " +
					" 	select a.kho_fk, a.kbh_fk, b.sanpham_fk, b.solo, SUM(b.soluong) as tongxuat  " +
					" 	from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk " +
					" 	where ycxk_fk = '" + lsxId + "' " +
					" 	group by a.kho_fk, a.kbh_fk, b.solo, b.sanpham_fk " +
					" ) " +
					" CT inner join NHAPP_KHO_CHITIET kho on CT.kho_fk = kho.KHO_FK  " +
					" 	and CT.sanpham_fk = kho.SANPHAM_FK and CT.solo = kho.SOLO and CT.KBH_FK = kho.KBH_FK and kho.NPP_FK = '" + nppId + "' ";
			System.out.println("---1 CAP NHAT KHO: " + query);
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_KHOTT_SP_CHITIET " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = " update kho set kho.SOLUONG = kho.SOLUONG - CT.tongxuat, " +
					" 			   kho.BOOKED = kho.BOOKED - CT.tongxuat " +
					" from " +
					" ( " +
					" 	select a.kho_fk, a.kbh_fk, b.sanpham_fk, SUM(b.soluong) as tongxuat  " +
					" 	from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk " +
					" 	where ycxk_fk = '" + lsxId + "' " +
					" 	group by a.kho_fk, a.kbh_fk, b.sanpham_fk " +
					" ) " +
					" CT inner join NHAPP_KHO kho on CT.kho_fk = kho.KHO_FK  " +
					" 	and CT.sanpham_fk = kho.SANPHAM_FK and CT.KBH_FK = kho.KBH_FK and kho.NPP_FK = '" + nppId + "'  ";
			System.out.println("---1 CAP NHAT KHO: " + query);
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_KHOTT_SANPHAM " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			//GOP CHUNG BUOC YC VA XUAT THANH 1
			query = "update ERP_YCXUATKHONPP set trangthai = '2'  where pk_seq = '" + lsxId + "' ";
			System.out.println("---CAP NHAT TRANG THAI: " + query);
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_YCXUATKHONPP " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			//TU DONG HOAN TAT CAC DON DAT HANG TU CU TOI MOI
			query = "select ddh_fk, ( select xuatcho from ERP_YCXUATKHONPP where pk_seq = a.ycxk_fk ) as xuatcho " +
					"from ERP_YCXUATKHONPP_DDH a where ycxk_fk = '" + lsxId + "' order by ddh_fk asc";
			ResultSet rsDDH = db.get(query);
			String ddhID = "";
			String xuatCHO = "";
			if(rsDDH != null)
			{
				while(rsDDH.next())
				{
					ddhID += rsDDH.getString("ddh_fk") + ",";
					xuatCHO = rsDDH.getString("xuatcho");
					
					query = "  select COUNT(*) as soDONG,   " +
							" 		(   select count(distinct sanpham_fk) as soSP      " +
							"   			from     " +
							"   			(     " +
							"   					select a.sanpham_fk " +
							"   					from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ     " +
							"   					where a.dondathang_fk in (    " + ( ddhID.substring(0, ddhID.length() - 1) ) + "   )    " +
							"   			)     " +
							"   			dathang  )	 as soSP  " +
							"  from  " +
							"  (  " +
							"  	select sanpham_fk, sum(soluongXUAT) as soluongXUAT  " +
							"  	from ERP_YCXUATKHONPP_SANPHAM  " +
							" 	where ycxk_fk in ( select ycxk_fk from ERP_YCXUATKHONPP_DDH where ddh_fk in ( " + ( ddhID.substring(0, ddhID.length() - 1) ) + " ) ) " +
							"  	group by sanpham_fk  " +
							"  )   " +
							"  XUAT inner join   " +
							"  (  " +
							"   	select dathang.sanpham_fk, SUM(dathang.soluong) as soluongDAT      " +
							"   	from     " +
							"   	(     " +
							"   			select a.sanpham_fk, b.DVDL_FK as dvCHUAN,     " +
							"   					case when a.dvdl_fk IS null then a.soluong      " +
							"   						 when a.dvdl_fk = b.DVDL_FK then a.soluong     " +
							"   						 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )      " +
							"   										 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )	 end as soluong, 0 as loai, ' ' as scheme    " +
							"   			from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ     " +
							"   			where a.dondathang_fk in (    " + ( ddhID.substring(0, ddhID.length() - 1) ) + "   )    " +
							"   	)     " +
							"   	dathang   " +
							"   	group by dathang.sanpham_fk  " +
							"  )  " +
							"  DDH on XUAT.sanpham_fk = DDH.sanpham_fk  " +
							"  where XUAT.soluongXUAT >= DDH.soluongDAT ";
					
					System.out.println("CHECK HOAN TAT: " + query);
					ResultSet rsCHECK = db.get(query);
					if(rsCHECK.next())
					{
						String trangthai = "";
						if(rsCHECK.getInt("soDONG") == rsCHECK.getInt("soSP"))
							trangthai = "4";  //HOAN TAT
						else
							trangthai = "2";  //KE TOAN DUYET
						
						query = " UPDATE ERP_DONDATHANGNPP set trangthai = '" + trangthai + "' where pk_seq in ( " + ( rsDDH.getString("ddh_fk") ) + " ) ";
						if(!db.update(query))
						{
							msg = "Không thể chốt ERP_YCXUATKHO " + query;
							db.getConnection().rollback();
							return msg;
						}
					}
				}
				rsDDH.close();
			}
			
			System.out.println("---XUAT CHO: " + xuatCHO);
			if(xuatCHO.equals("0"))  //XUẤT CHO ĐỐI TÁC THÌ TẠO NHẬP HÀNG CHO ĐỐI TÁC
			{
				query = " insert NHAPHANG(NGAYNHAN, NGAYCHUNGTU, NPP_FK, NCC_FK, GSBH_FK, ASM_FK, BM_FK, DVKD_FK, KBH_FK, YCXKNPP_FK, TRANGTHAI, NGUOITAO, NGAYTAO, NGUOISUA, NGAYSUA) " +
						" select distinct NgayYeuCau, NgayYeuCau, NPP_DAT_FK,  " +
						" 			( select top(1) NCC_FK from NHACUNGCAP_DVKD where PK_SEQ in ( select NCC_DVKD_FK from NHAPP_NHACC_DONVIKD where NPP_FK = a.NPP_DAT_FK ) ), " +
						"			( select top(1) GSBH_FK from NHAPP_GIAMSATBH where NPP_FK = a.NPP_DAT_FK ), " +
						"			( select top(1) ASM_FK from ASM_KHUVUC where KHUVUC_FK in ( select KHUVUC_FK from NHAPHANPHOI where PK_SEQ = a.NPP_DAT_FK )), " +
						"			( select top(1) BM_FK from BM_CHINHANH where VUNG_FK in ( select VUNG_FK from KHUVUC where PK_SEQ in (  select KHUVUC_FK from NHAPHANPHOI where PK_SEQ = a.NPP_DAT_FK ) ) ), " +
						" 	   '100001' as DVKD_FK, a.KBH_FK, '" + lsxId + "', '0', '" + userId + "', '" + getDateTime() + "', '" + userId + "', '" + getDateTime() + "' " +
						" from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM b on a.PK_SEQ = b.ycxk_fk " +
						" where a.PK_SEQ = '" + lsxId + "' ";
				
				System.out.println("---INSERT NHAN HANG: " + query );
				if(!db.update(query))
				{
					msg = "Không tạo mới NHAPHANG " + query;
					db.getConnection().rollback();
					return msg;
				}
				
				query = " insert NHAPHANG_SP(NHAPHANG_FK, SANPHAM_FK, SOLUONG, soluongNHAN, DONGIA, CHIETKHAU, DVDL_FK, LOAI, SCHEME, SOLO, NGAYHETHAN) " +
						" select ( select pk_seq from NHAPHANG where YCXKNPP_FK = a.PK_SEQ  ),  " +
						" 		b.sanpham_fk, b.soluong, NULL, b.dongia, 0 as chietkhau, c.DVDL_FK, b.LOAI, b.SCHEME, b.solo, b.ngayhethan " +
						" from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.PK_SEQ = b.ycxk_fk " +
						" 		inner join SANPHAM c on b.sanpham_fk = c.PK_SEQ   " +
						" where a.PK_SEQ = '" + lsxId + "' and b.soluong > 0 ";
				System.out.println("---INSERT NHAN HANG - SP: " + query );
				if(db.updateReturnInt(query) < 1 )
				{
					msg = "Không tạo mới NHAPHANG_SP " + query;
					db.getConnection().rollback();
					return msg;
				}
				
				query = "insert NHAPHANG_DDH(nhaphang_fk, ddh_fk)  " +
						"select ( select PK_SEQ from NHAPHANG where YCXKNPP_FK = '" + lsxId + "' ) as nhID, ddh_fk  " +
						"from ERP_YCXUATKHONPP_DDH where ycxk_fk = '" + lsxId + "'";
				if(!db.update(query))
				{
					msg = "Không tạo mới NHAPHANG_DDH " + query;
					db.getConnection().rollback();
					return msg;
				}
			}
			
			
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
	    
	    
		IErpYeucauxuatkhoNppList obj = new ErpYeucauxuatkhoNppList();
		obj.setLoaidonhang(loaidonhang);
		
		
	 
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"))); 
	    
	    obj.setUserId(userId);
	    
	    
	    String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
	    if(nppId == null)
	    	nppId = "";
	    obj.setNppId(nppId);
	    
	    
	    String khId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khId"));
	    if(khId == null)
	    	khId = "";
	    obj.setNppTen(khId);
	    
	    
	    System.out.println("---NPP ID: " + nppId);
	    
	    if(action.equals("Tao moi"))
	    {
	    	IErpYeucauxuatkhoNpp lsxBean = new ErpYeucauxuatkhoNpp();
	    	lsxBean.setUserId(userId);
	    	lsxBean.createRs();
	    	
	    	session.setAttribute("dvkdId", "");
			session.setAttribute("kbhId", "");
			session.setAttribute("nppId", "");
    		
	    	session.setAttribute("lsxBean", lsxBean);
	    	
    		String nextJSP = "/AHF/pages/Distributor/ErpYeuCauXuatKhoNppNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
		    	String search = getSearchQuery(request, obj);
		    	obj.setNxtApprSplitting(Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
		    	
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	
		    	String nextJSP = "/AHF/pages/Distributor/ErpYeuCauXuatKhoNpp.jsp";
				response.sendRedirect(nextJSP);
		    }
	    	else
	    	{
		    	String search = getSearchQuery(request, obj);
		    	obj.init(search);
				obj.setUserId(userId);
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		String nextJSP = "/AHF/pages/Distributor/ErpYeuCauXuatKhoNpp.jsp";
	    		response.sendRedirect(nextJSP);
	    	}
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpYeucauxuatkhoNppList obj)
	{
		Utility util = new Utility();
		String query =
				"select a.PK_SEQ, a.trangthai, a.ngayyeucau, isnull(c.ten, d.ten) as nppTEN, b.ten as khoTEN, NV.TEN as nguoitao, b.ten as khonhan, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, " +
						"	 (	Select cast(YCXK1.DDH_FK as varchar(10)) + ',' AS [text()]  " +
						"		From ERP_YCXUATKHONPP_DDH YCXK1   " +
						"		Where YCXK1.ycxk_fk = a.pk_seq  " +
						"		For XML PATH ('') )  as ddhIds    " +
						"from ERP_YCXUATKHONPP a inner join KHO b on a.kho_fk = b.pk_seq " +
						"	left join NHAPHANPHOI c on a.NPP_DAT_FK = c.pk_seq " +
						"	left join KHACHHANG d on a.khachhang_fk = d.pk_seq  " +
						"	inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
						"	inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.npp_fk = '" + obj.getNppId() + "' and a.kho_fk in "+util.quyen_kho(obj.getUserId()); 
		
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
		
	
		
		String khId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khId"));
		if(khId == null)
			khId = "";
		obj.setNppTen(khId);
		
		if(tungay.length() > 0)
			query += " and a.ngayyeucau >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.ngayyeucau <= '" + denngay + "'";
	
		if(trangthai.length() > 0)
		{
			if(trangthai.equals("0"))
				query += " and a.TrangThai = '" + trangthai + "'";
			else
				query += " and a.TrangThai >= '" + trangthai + "'";
		}
		
		if(khId.length() > 0)
		{
			query += " and isnull(a.npp_Dat_fk,a.khachhang_Fk) = '" + khId + "'";
		}
		
		System.out.print("Query tim kiem Dat: "+query);
		return query;
	}
		
	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	
}
