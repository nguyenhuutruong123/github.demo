package geso.dms.distributor.servlets.dondathang;

import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.dondathang.IErpDenghidathangNpp;
import geso.dms.distributor.beans.dondathang.IErpDenghidathangNppList;
import geso.dms.distributor.beans.dondathang.imp.ErpDenghidathangNpp;
import geso.dms.distributor.beans.dondathang.imp.ErpDenghidathangNppList;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpDenghidathangNppSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpDenghidathangNppSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpDenghidathangNppList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    
	    
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();	  
	    
	 // kiểm tra quyền xem menu
	    String param="";
		if( Utility.CheckRuleUser( session,  request, response, "ErpDenghidathangNppSvl", param, Utility.XEM ) || Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
			return;
		}
		Utility util = new Utility();
	    
	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    
	    
	    String action = util.getAction(querystring);
	    
    	String lsxId = util.getId(querystring);
    	
	    obj = new ErpDenghidathangNppList();
	    obj.setUserId(userId);
	    		
	    
	    String loaidonhang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaidonhang"));
	    if(loaidonhang == null)
	    	loaidonhang = "0";
	    obj.setLoaidonhang(loaidonhang);
	    System.out.println("---LOAI DON HANG: " + loaidonhang);
	    
	    
	    if (action.equals("delete") )
	    {	
	    	String msg = this.DeleteChuyenKho(lsxId,obj);
			obj.setMsg(msg);
	    }
	    else if(action.equals("chot"))
    	{
    		String msg = this.Chot(lsxId, obj);
			obj.setMsg(msg);
    	}
	    else if(action.equals("convert"))
    	{
    		String msg = this.ConvertPO(lsxId, obj);
			obj.setMsg(msg);
    	}
	    
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
			
		String nextJSP = "/AHF/pages/Distributor/ErpDeNghiDatHangNpp.jsp";
		response.sendRedirect(nextJSP);

	    
	    
	}

	private String ConvertPO(String lsxId, IErpDenghidathangNppList obj)
	{
		// Kiểm tra id có hợp lệ không
	    if(lsxId != null && lsxId.trim().length() > 0 
	    		&& !Utility.KiemTra_PK_SEQ_HopLe(lsxId, "ERP_Denghidathang", obj.getDb())){
	    	obj.DBclose();
    		return "Id không hợp lệ";
    	}
		dbutils db = new dbutils();
		String msg = "";
		String param = "";
		
		int[] quyen = Utility.Getquyen("ErpDenghidathangNppSvl", param,obj.getUserId());
		if(quyen[Utility.THEM]!=1)
		{
			
			return "";
		}
		try
		{
			db.getConnection().setAutoCommit(false);
		
			String query = "update ERP_Denghidathang set trangthai = '2' where pk_seq = ?";
			if(db.updateQueryByPreparedStatement(query, new Object[]{lsxId}) < 1)
			{
				msg = "Khong the chot ";
				db.getConnection().rollback();
				return msg;
			}
			
			query = " insert ERP_DonDatHang(ngaydonhang, ngaydenghi, loaidonhang, ghichu, trangthai, dvkd_fk, kbh_fk, npp_fk, kho_fk, chietkhau, vat, ngaytao, nguoitao, ngaysua, nguoisua, NOTE, NPP_DACHOT) " +
					" select ngaydenghi, ngaydenghi, 0, ghichu, 0 as trangthai, dvkd_fk, kbh_fk, npp_fk, kho_fk, chietkhau, vat, ngaytao, nguoitao, ngaysua, nguoisua, N'Convert từ đề nghị số  "+lsxId+"', '0'  " +
					" from ERP_DeNghiDatHang where pk_seq = ? ";
			
			System.out.println("1.Insert DDH ");
			dbutils.viewQuery(query, new Object[]{lsxId});
			if(db.updateQueryByPreparedStatement(query, new Object[]{lsxId}) < 1)
			{
				msg = "Không thể tạo mới ERP_DeNghiDatHang ";
				db.getConnection().rollback();
				return msg;
			} 
			
			query = "insert ERP_DONDATHANG_SANPHAM(dondathang_fk, sanpham_fk, soluong, dongia, dvdl_fk)  " +
					"select IDENT_CURRENT('ERP_DONDATHANG'), sanpham_fk, denghi, dongia, dvdl_fk   " +
					"from ERP_DeNghiDatHang_SANPHAM  " +
					"where denghidathang_fk = ? and denghi != 0";
			System.out.println("1.Insert DDH - SP: " + query);
			if(db.updateQueryByPreparedStatement(query, new Object[]{lsxId}) < 1)
			{
				msg = "Không thể tạo mới ERP_DeNghiDatHang ";
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
		
		return "Chuyển thành đơn đặt hàng thành công ";
	}

	private String Chot(String lsxId, IErpDenghidathangNppList obj) 
	{
		// Kiểm tra id có hợp lệ không
	    if(lsxId != null && lsxId.trim().length() > 0 
	    		&& !Utility.KiemTra_PK_SEQ_HopLe(lsxId, "ERP_Denghidathang", obj.getDb())){
	    	obj.DBclose();
    		return "Id không hợp lệ";
    	}
		dbutils db = new dbutils();
		String param = "";
		String msg = "";
		
		int[] quyen = Utility.Getquyen("ErpDenghidathangNppSvl", param,obj.getUserId());
		if(quyen[Utility.CHOT]!=1)
		{
			
			return "";
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
		
			String query = "update ERP_Denghidathang set trangthai = '1' where pk_seq = ?";
			if(db.updateQueryByPreparedStatement(query, new Object[]{lsxId}) < 1)
			{
				msg = "Khong the chot";
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

	private String DeleteChuyenKho(String lsxId, IErpDenghidathangNppList obj)
	{
		// Kiểm tra id có hợp lệ không
	    if(lsxId != null && lsxId.trim().length() > 0 
	    		&& !Utility.KiemTra_PK_SEQ_HopLe(lsxId, "ERP_Denghidathang", obj.getDb())){
	    	obj.DBclose();
    		return "Id không hợp lệ";
    	}
		dbutils db = new dbutils();
		String msg = "";
		String param = "";
		
		//if(obj.getView().trim().length() > 0) param ="&view="+obj.getView();
		int[] quyen = Utility.Getquyen("ErpDenghidathangNppSvl", param,obj.getUserId());
		if(quyen[Utility.XOA]!=1)
		{
			
			return "";
		}
		try
		{
			db.getConnection().setAutoCommit(false);

			String query = "Delete ERP_Denghidathang where pk_seq = ?";
			if(db.updateQueryByPreparedStatement(query, new Object[]{lsxId}) < 1)
			{
				msg = "1.Khong the xoa ";
				db.getConnection().rollback();
				return msg;
			}
			
			query = "Delete ERP_Denghidathang_SanPham where denghidathang_fk = ?";
			if(db.updateQueryByPreparedStatement(query, new Object[]{lsxId}) < 1)
			{
				msg = "2.Khong the xoa ";
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
	    
	    
		IErpDenghidathangNppList obj = new ErpDenghidathangNppList();
		obj.setLoaidonhang(loaidonhang);
	 
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"))); 
	    obj.setUserId(userId);
	    
	    String param="";
		if( Utility.CheckRuleUser( session,  request, response, "ErpDenghidathangNppSvl", param, Utility.XEM ) || Utility.CheckSessionUser( session,  request, response))
		{
			obj.DBclose();
			Utility.RedireactLogin(session, request, response);
			return;
		}
		
		String msg = "";
	    
	    /*if (action.equals("Xoa") )
	    {	
	    	obj.setUserId(userId);
	    	String idXoa = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("IdXoa")));
	    	if(idXoa == null){
	    		idXoa = "";
	    	}
	    	if(idXoa.trim().length() > 0){
	    		msg = this.DeleteChuyenKho(idXoa, obj);
	    		obj.setMsg(msg);
	    	}
	    	else{
	    		obj.setMsg("Lỗi dữ liệu");
	    	}
			
	    }*/
	    
	    if(action.equals("Tao moi"))
	    {
	    	int[] quyen = Utility.Getquyen("ErpDenghidathangNppSvl", "",obj.getUserId());
			if(quyen[Utility.THEM]==1)
			{
				System.out.println("GOGOGOGOGO");
				IErpDenghidathangNpp lsxBean = new ErpDenghidathangNpp();
		    	lsxBean.setLoaidonhang(loaidonhang);
		    	lsxBean.setUserId(userId);
		    	
		    	lsxBean.createRs();
		    	session.setAttribute("dvkdId", lsxBean.getDvkdId());
				session.setAttribute("kbhId", lsxBean.getKbhId());
				session.setAttribute("nppId", lsxBean.getNppId());
	    		
		    	session.setAttribute("lsxBean", lsxBean);
		    	
	    		String nextJSP = "/AHF/pages/Distributor/ErpDeNghiDatHangNppNew.jsp";
	    		response.sendRedirect(nextJSP);
			}
			else{
				msg = "User không được phân quyền thêm";
				obj.setLoaidonhang(loaidonhang);
				obj.setUserId(userId);
			    obj.init("");
			    
				session.setAttribute("obj", obj);
					
				String nextJSP = "/AHF/pages/Distributor/ErpDeNghiDatHangNpp.jsp";
				response.sendRedirect(nextJSP);
			}
			
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
		    	
		    	String nextJSP = "/AHF/pages/Distributor/ErpDeNghiDatHangNpp.jsp";
				response.sendRedirect(nextJSP);
		    }
	    	else
	    	{
		    	String search = getSearchQuery(request, obj);
		    	obj.init(search);
				obj.setUserId(userId);
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		String nextJSP = "/AHF/pages/Distributor/ErpDeNghiDatHangNpp.jsp";
	    		response.sendRedirect(nextJSP);
	    	}
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpDenghidathangNppList obj)
	{
		String query = "select a.PK_SEQ, a.trangthai, a.ngaydenghi as ngaydonhang, c.ten as nppTEN, b.ten as khoTEN, NV.TEN as nguoitao, b.ten as khonhan, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua   " +
						"from ERP_Denghidathang a inner join ERP_KHOTT b on a.kho_fk = b.pk_seq inner join NHAPHANPHOI c on a.NPP_FK = c.pk_seq  " +
						"inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
						"inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.pk_seq > 0 ";
		
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
		
		if(tungay.length() > 0)
		{
			query += " and a.ngaydenghi >=? ";
			obj.getDataSearch().add(tungay);
		}
			
		
		
		if(denngay.length() > 0)
		{
			query += " and a.ngaydenghi <= ?";
			obj.getDataSearch().add(denngay);
		}
			
	
		if(trangthai.length() > 0)
		{
			query += " and a.TrangThai = ?";
			obj.getDataSearch().add(trangthai);
		}
			
		
		if(nppId.length() > 0){
			query += " and a.NPP_FK= ?";
			obj.getDataSearch().add(nppId);
		}
		
		System.out.print(query);
		return query;
	}
	
	/*private String getSearchQuery(HttpServletRequest request, IErpDenghidathangNppList obj)
	{
		String query = "select a.PK_SEQ, a.trangthai, a.ngaydenghi as ngaydonhang, c.ten as nppTEN, b.ten as khoTEN, NV.TEN as nguoitao, b.ten as khonhan, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua   " +
						"from ERP_Denghidathang a inner join ERP_KHOTT b on a.kho_fk = b.pk_seq inner join NHAPHANPHOI c on a.NPP_FK = c.pk_seq  " +
						"inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
						"inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.pk_seq > 0 ";
		
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
		
		if(tungay.length() > 0)
			query += " and a.ngaydenghi >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.ngaydenghi <= '" + denngay + "'";
	
		if(trangthai.length() > 0)
			query += " and a.TrangThai = '" + trangthai + "'";
		
		if(nppId.length() > 0){
			query += " and a.NPP_FK= '" + nppId + "'";
		}
		
		System.out.print(query);
		return query;
	}*/
		
	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	
}
