package geso.dms.distributor.servlets.donhangtrave;

import geso.dms.center.db.sql.Idbutils;
import geso.dms.center.util.Webservice_UTN;
import geso.dms.distributor.beans.donhangtrave.IDonhangtrave;
import geso.dms.distributor.beans.donhangtrave.IDonhangtraveList;
import geso.dms.distributor.beans.donhangtrave.imp.Donhangtrave;
import geso.dms.distributor.beans.donhangtrave.imp.DonhangtraveList;
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

public class DonhangtraveNguyenDonSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	IDonhangtraveList obj;
	PrintWriter out; 
	String userId;
	
    public DonhangtraveNguyenDonSvl() 
    {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		this.out  = response.getWriter();

		HttpSession session = request.getSession();	    

		Utility util = new Utility();
		out = response.getWriter();

		String querystring = request.getQueryString();
		userId = util.getUserId(querystring);
		out.println(userId);

		if (userId.length()==0)
			userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));

		String action = util.getAction(querystring);
		out.println(action);	
		
		String dhtvId = util.getId(querystring);
		obj = new DonhangtraveList();
		obj.setUserId(userId);
		
		String msg = "";
		
		String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
		if (!obj.checkNull(view))
			view = "";
		obj.setView(view);
	
		System.out.println("Get Action: " + action);
		if (action.equals("delete"))
		{	   		  	    	
			msg = HuyDonHang(dhtvId, userId);
			if(msg.trim().length()>0)
				obj.setMsg(msg);
			else
				obj.setMsg("Huỷ ("+dhtvId+") thành công.");
		}
		else if (action.equals("chot"))
		{
			/*msg = ChotDonHang(dhtvId, userId);
			if (msg == null || msg.trim().length() <= 0)
			obj.setMsg("Chốt ("+dhtvId+") thành công.");*/
			// 2019-03-27 ko chốt HO nữa chỉ chốt từ NPP là trừ kho
			msg = HO_ChotDonHang(dhtvId, userId);
			obj.setMsg(msg);
			
		}
		else if (action.equals("HOchot"))
		{
			msg = HO_ChotDonHang(dhtvId, userId);
			if (msg == null || msg.trim().length() <= 0)
			obj.setMsg("Chốt HO ("+dhtvId+") thành công.");
		}
		else if (action.equals("HOtuchoi"))
		{
			msg = HO_HuyDonHang(dhtvId, userId);
			if (msg == null || msg.trim().length() <= 0)
				obj.setMsg("Huỷ HO ("+dhtvId+") thành công.");
		}
		
		obj.init_TraNguyenDonList("");
		session.setAttribute("obj", obj);
		session.setAttribute("tbhId", "");
		session.setAttribute("khId", "");

		String nextJSP = "/AHF/pages/Distributor/DonHangTraVeNguyenDon.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		this.out = response.getWriter();

		HttpSession session = request.getSession();
		userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));

		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action1"));
		if (action == null){
			action = "";
		}
		out.println(action); 
		
		obj = new DonhangtraveList();
		
		String userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")); 
		obj.setUserId(userId);

		String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
		if ( nppId == null)
			nppId = "";
		obj.setNppId(nppId);

		String ddkdId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdTen"));
		if ( ddkdId == null)
			ddkdId = "";
		obj.setDdkdId(ddkdId);
		
		String trangthainguyendon = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthainguyendon"));
		if (trangthainguyendon == null)
			trangthainguyendon = "";
		obj.setTrangthaiNguyenDon(trangthainguyendon);

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
		
		String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
		if (!obj.checkNull(view))
			view = "";
		obj.setView(view);

		session.setAttribute("dhtvBean", obj);

		if (action.equals("taomoi")){
			IDonhangtrave dhtvBean = (IDonhangtrave) new Donhangtrave("");
			dhtvBean.setUserId(userId);
			dhtvBean.createRs_TraNguyenDon();
			// Save Data into session

			session.setAttribute("nppId", dhtvBean.getNppId());
			session.setAttribute("dhtvBean", dhtvBean);
			String nextJSP = "/AHF/pages/Distributor/DonHangTraVeNguyenDonNew.jsp";
			response.sendRedirect(nextJSP);
		}
		else{
			String search = getSearchQuery(request, obj);
			obj.setUserId(userId);
			obj.init_TraNguyenDonList(search);
			session.setAttribute("obj", obj);  	
			session.setAttribute("userId", userId);
			response.sendRedirect("/AHF/pages/Distributor/DonHangTraVeNguyenDon.jsp");	    
		}
	}
	
	private String getSearchQuery(HttpServletRequest request, IDonhangtraveList obj) 
	{		
		String query = "\n select isnull(a.ghichu,'') as lydo,a.trangthai, a.pk_seq, a.donhang_fk, a.ngaynhap, kh.smartid makh,   " +
		"\n case when a.trangthai = 0 then N'Chưa chốt' " +
		"\n when a.trangthai = 2 then N'Đã huỷ' " +
		"\n when a.trangthai = 3 then N'Đã duyệt' " +
		"\n else N'Không xác định' end trangthaihienthi, a.ngaytao, a.ngaysua,nv1.ten nguoitao, nv2.ten nguoisua " +
		"\n from donhangtrave a " +
		"\n inner join khachhang kh on kh.pk_seq = a.khachhang_fk " +
		"\n inner join nhanvien nv1 on nv1.pk_seq = a.nguoitao " +
		"\n inner join nhanvien nv2 on nv2.pk_seq = a.nguoisua " +
		"\n where 1 = 1 and isnull(donhang_fk,0) != 0 ";
		
		String nppId = obj.getNppId();
		if (obj.checkNull(nppId)) {
			query += "\n and a.npp_fk = "+nppId;
		}

		String ddkdId = obj.getDdkdId();
		if (obj.checkNull(ddkdId)) {
			query += "\n and a.ddkd_fk = "+ddkdId;
		}
		
		String trangthainguyendon = obj.getTrangthaiNguyenDon();
		if (obj.checkNull(trangthainguyendon)) {
			query += "\n and a.trangthai = "+trangthainguyendon;
		}
		
		String tungay = obj.getTungay();
		if (obj.checkNull(tungay)) {
			query += "\n and a.ngaynhap >= '"+tungay+"'";
		}
		
		String denngay = obj.getDenngay();
		if (obj.checkNull(denngay)) {
			query += "\n and a.ngaynhap <= '"+denngay+"'";
		}
	
		return query;
	}

	private String Delete(String id,String nppId)
	{
		dbutils db = new dbutils();
		try
		{
			db.getConnection().setAutoCommit(false);
			String query ="DELETE FROM DONHANGTRAVE_sANPHAM WHERE DONHANGTRAVE_FK='"+id+"'";
			if(db.updateReturnInt(query)<=0)
			{
				db.getConnection().rollback();
				return "Không thể xóa đơn hàng trả về "+query;
			}
			
			query ="DELETE FROM DONHANGTRAVE_sANPHAM_chitiet WHERE DONHANGTRAVE_FK='"+id+"'";
			if(db.updateReturnInt(query)<=0)
			{
				db.getConnection().rollback();
				return "Không thể xóa đơn hàng trả về "+query;
			} 
			query =
			"DELETE FROM DONHANGTRAVE WHERE PK_SEQ='"+id+"' AND TRANGTHAI=0 ";
			if(db.updateReturnInt(query)!=1)
			{
				db.getConnection().rollback();
				return "Không thể xóa đơn hàng trả về do trạng thái không hợp lệ ";
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return "";
		} catch (Exception e)
		{
			try
			{
				db.getConnection().rollback();
			} catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}
		return "";
		
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private String ChotDonHang(String id, String userId) 
	{
		String msg = "";
		dbutils db = new dbutils();
		try 
		{
			db.getConnection().setAutoCommit(false);	

			String query = "\n update donhangtrave set trangthai = 1, nguoiduyet = '" + userId + "', ngayduyet = '" + this.getDateTime() + "' " +
			"\n where pk_seq = '" + id + "' and trangthai = 0 ";
			if (db.updateReturnInt(query) !=1)
			{
				db.getConnection().rollback();
				msg = "Trạng thái không hợp lệ vui lòng thử lại.";
				return msg;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
		} 
		catch(Exception e) 
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			db.shutDown();
			msg = "Exception: " + e.getMessage();
			return msg;
		}
		
		return "";
	}
	
	private String HuyDonHang(String id, String userId) 
	{
		String msg = "";
		dbutils db = new dbutils();
		try 
		{
			db.getConnection().setAutoCommit(false);	
			
			msg = geso.dms.center.util.Utility.Check_Huy_NghiepVu_KhoaSo("donhangtrave", id, "ngaynhap", db);
			if( msg.length() > 0)
			{
				db.getConnection().rollback();
				return msg;
			}

			String que="select DONHANG_FK from donhangtrave where pk_seq="+id;
			ResultSet rsdh=db.get(que);
			rsdh.next();
			String dhid=rsdh.getString("DONHANG_FK");
			rsdh.close();
			
			msg = geso.dms.center.util.Utility.Check_Huy_NghiepVu_KhoaSo("donhang",  dhid, "ngaynhap", db);
			if( msg.length() > 0)
			{
				db.getConnection().rollback();
				return msg;
			}
			
			String query = "\n update donhangtrave set trangthai = 2, nguoiduyet = '" + userId + "', ngayduyet = '" + this.getDateTime() + "' " +
			"\n where pk_seq = '" + id + "' and trangthai = 0 ";
			if (db.updateReturnInt(query) !=1)
			{
				db.getConnection().rollback();
				msg = "Trạng thái không hợp lệ vui lòng thử lại.";
				return msg;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
		} 
		catch(Exception e) 
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			db.shutDown();
			msg = "Exception: " + e.getMessage();
			return msg;
		}
		
		return "";
	}
	
	private String HO_HuyDonHang(String id, String userId) 
	{
		String msg = "";
		dbutils db = new dbutils();
		try 
		{
			db.getConnection().setAutoCommit(false);	
			
			msg = geso.dms.center.util.Utility.Check_Huy_NghiepVu_KhoaSo("donhangtrave", id, "ngaynhap", db);
			if( msg.length() > 0)
			{
				db.getConnection().rollback();
				return msg;
			}
			
			String que="select DONHANG_FK from donhangtrave where pk_seq="+id;
			ResultSet rsdh=db.get(que);
			rsdh.next();
			String dhid=rsdh.getString("DONHANG_FK");
			rsdh.close();
			
			msg = geso.dms.center.util.Utility.Check_Huy_NghiepVu_KhoaSo("donhang",  dhid, "ngaynhap", db);
			if( msg.length() > 0)
			{
				db.getConnection().rollback();
				return msg;
			}
			
			String query = "\n update donhangtrave set trangthai = 4, nguoiduyet = '" + userId + "', ngayduyet = '" + this.getDateTime() + "' " +
			"\n where pk_seq = '" + id + "' and trangthai = 1 ";
			if (db.updateReturnInt(query) !=1)
			{
				db.getConnection().rollback();
				msg = "Trạng thái không hợp lệ vui lòng thử lại.";
				return msg;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
		} 
		catch(Exception e) 
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			db.shutDown();
			msg = "Exception: " + e.getMessage();
			return msg;
		}
		
		return "";
	}
	
	private String HO_ChotDonHang(String id, String userId) 
	{
		String msg = "";
		dbutils db = new dbutils();
		try 
		{
			db.getConnection().setAutoCommit(false);	
			
			msg = geso.dms.center.util.Utility.Check_Huy_NghiepVu_KhoaSo("donhangtrave", id, "ngaynhap", db);
			if( msg.length() > 0)
			{
				db.getConnection().rollback();
				return msg;
			}
			
			String que="select DONHANG_FK from donhangtrave where pk_seq="+id;
			ResultSet rsdh1=db.get(que);
			rsdh1.next();
			String dhid=rsdh1.getString("DONHANG_FK");
			rsdh1.close();
			
			msg = geso.dms.center.util.Utility.Check_Huy_NghiepVu_KhoaSo("donhang",  dhid, "ngaynhap", db);
			if( msg.length() > 0)
			{
				db.getConnection().rollback();
				return msg;
			}
			
			String qu="select DONHANG_FK from donhangtrave where pk_seq="+id;
			ResultSet rsdhoa=db.get(qu);
			rsdhoa.next();
			String dhid1=rsdhoa.getString("DONHANG_FK");
			rsdhoa.close();
			
			msg = geso.dms.center.util.Utility.Check_Huy_NghiepVu_KhoaSo("donhang",  dhid1, "ngaynhap", db);
			if( msg.length() > 0)
			{
				db.getConnection().rollback();
				return msg;
			}
			
			String query = "\n update donhangtrave set trangthai = 3, nguoiduyet = '" + userId + "', ngayduyet = '" + this.getDateTime() + "', ngaygiochot = CONVERT(char(10),GETDATE(),121) + ' ' + CONVERT(CHAR( 5),GETDATE(),114) " +
			"\n where pk_seq = '" + id + "' and trangthai in (0,1) ";
			if (db.updateReturnInt(query) !=1) {
				db.getConnection().rollback();
				db.shutDown();
				msg = "Trạng thái không hợp lệ vui lòng thử lại.";
				return msg;
			}
			
			String dhId = "", nppId = "";String ngaytrahang = "";
			query = "\n select a.NGAYNHAP as ngaytrahang ,a.donhang_fk, a.npp_fk " +
			"\n from donhangtrave a inner join donhang b on a.donhang_fk = b.pk_seq " +
			"\n where a.trangthai = 3 and a.pk_seq = "+id;
			ResultSet rs = db.get(query);
			while (rs.next()) {
				dhId = rs.getString("donhang_fk");
				nppId = rs.getString("npp_fk");
				ngaytrahang = rs.getString("ngaytrahang");
			}
			rs.close();			
			
			msg = TangKhoDonTraHangNguyenDon(db, dhId, userId, nppId,ngaytrahang);
			if (msg != null && msg.length() > 0) {
				db.getConnection().rollback();
				db.shutDown();
				msg = "Lỗi không thể tăng kho NPP: "+msg;
				return msg;
			}
			String sql="select isnulL(a.donhang_fk,0) as donhang_fk from donhangtrave a inner join donhang b on b.pk_seq=a.donhang_fk where a.pk_seq="+id+" and b.isdongbo=1 ";
			ResultSet getdataRs=db.get(sql);
			if(getdataRs!=null){
				if(getdataRs.next()){
			String iddh=getdataRs.getString("donhang_fk");
			if(nppId.equals("1000580") && !dhId.equals("0")){
				Webservice_UTN UTN = new Webservice_UTN();
				msg = UTN.call_api_salesInvoice("DONTRAHANG", db, id,iddh,"0");
				if(msg.trim().length()<=0)
				{
					query = " update donhangtrave set isdongbo = 1 where  pk_seq =  "+ id;
					if(db.updateReturnInt(query) <=0)
					{
						db.getConnection().rollback();
						db.shutDown();
						return "Lỗi cập nhật ngày giao hàng!";
					}
					}else{
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					return "Lỗi đồng bộ";
				}
			}
				}
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			return "Chốt thành công";
		} 
		catch(Exception e) 
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			db.shutDown();
			msg = "Exception: " + e.getMessage();
			e.printStackTrace();
			return msg;
		}
		
	}
	
	public String TangKhoDonTraHangNguyenDon(Idbutils db ,String dhId, String userId, String nppId,String ngaytrahang)
	{
		String query = "", msg = "";
		try
		{			
			query = "\n select c.ngaynhap , 0 trakmID,0 CTKMID,1 loai,c.kho_fk as khoId, " +
			"\n case when (select dungchungkenh from nhaphanphoi where pk_seq = c.npp_fk) = 1 then 100025 else c.kbh_fk end kbhId, " +
			"\n  b.pk_seq as spId, b.ten as spTEN, sum(a.soluong) as soluong " +
			"\n  from donhang_sanpham_chitiet a inner join sanpham b on a.sanpham_fk = b.pk_seq " +
			"\n inner join donhang c on a.donhang_fk = c.pk_seq" +
			"\n  where c.trangthai != 2 and a.donhang_fk = "+dhId+
			"\n  group by c.kho_fk, c.kbh_fk, b.pk_seq, b.ten,c.npp_fk,c.kbh_fk, c.ngaynhap " +
			"\n  union all " +
			"\n  select  c.ngaynhap, a.trakmID,a.CTKMID,2,ctkm.kho_fk as khoId, " +
			"\n  case when (select dungchungkenh from nhaphanphoi where pk_seq = c.npp_fk) = 1 then 100025 else c.kbh_fk end kbhId, " +
			"\n  b.pk_seq as spId, b.ten as spTEN, sum(a.soluong) as soluong " +
			"\n  from DONHANG_CTKM_TRAKM_CHITIET a inner join sanpham b on a.SPMA = b.ma inner join donhang c on a.DONHANGID = c.pk_seq " + 
			"\n  inner join CTKHUYENMAI ctkm on ctkm.PK_SEQ = a.CTKMID " +
			"\n  where c.trangthai != 2 and a.DONHANGID = "+dhId+
			"\n  group by a.trakmID,a.CTKMID,ctkm.kho_fk, c.kbh_fk, b.pk_seq, b.ten,c.npp_fk,c.kbh_fk,  c.ngaynhap ";
			ResultSet rsKHO = db.get(query);
			while (rsKHO.next())
			{
				String khoId = rsKHO.getString("khoId");
				String kbhId = rsKHO.getString("kbhId");
				String spId = rsKHO.getString("spId");
				double soluong = rsKHO.getDouble("soluong");
				String  ngaynhapkho = ngaytrahang;

				geso.dms.center.util.Utility utiCen = new geso.dms.center.util.Utility();
				msg = utiCen.Update_NPP_Kho_Sp_Chitiet("", "HO chốt trả hàng nguyên đơn chi tiết: "+dhId, db, khoId, spId, nppId, kbhId, ngaynhapkho, soluong, 0, soluong, 0, 0);
				if (msg.length()> 0) 
				{
					return msg;
				}
				
				msg = utiCen.Update_NPP_Kho_Sp("",dhId, "HO chốt trả hàng nguyên đơn tổng: "+dhId, db, khoId, spId, nppId, kbhId, soluong, 0, soluong, 0.0);
				if (msg.length()>0)
				{
					return msg;
				}		
			}

			return msg;
		}
		catch (Exception e) {
			e.printStackTrace();
			return "Exception: "+ e.getMessage();
		}
	}

}

