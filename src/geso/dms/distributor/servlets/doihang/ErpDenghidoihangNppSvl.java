package geso.dms.distributor.servlets.doihang;

import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.doihang.IErpDenghidoihangNpp;
import geso.dms.distributor.beans.doihang.IErpDenghidoihangNppList;
import geso.dms.distributor.beans.doihang.imp.ErpDenghidoihangNpp;
import geso.dms.distributor.beans.doihang.imp.ErpDenghidoihangNppList;

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

public class ErpDenghidoihangNppSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	public ErpDenghidoihangNppSvl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpDenghidoihangNppList obj;
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
		obj = new ErpDenghidoihangNppList();

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
		else if(action.equals("chot"))
		{
			String msg = this.Chot(lsxId);
			obj.setMsg(msg);
		}

		obj.setUserId(userId);
		obj.init("");

		session.setAttribute("obj", obj);

		String nextJSP = "/AHF/pages/Distributor/ErpDeNghiDoiHangNpp.jsp";
		response.sendRedirect(nextJSP);

	}

	private String Chot(String lsxId) 
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);

			//TANG KHO NGUOC LAI
			String query =  " update kho  " +
					" 	set kho.BOOKED = kho.BOOKED - doihang.denghi, " +
					" 		kho.SOLUONG = kho.SOLUONG - doihang.denghi " +
					" from " +
					" ( " +
					" 	select a.kho_fk, a.npp_fk, a.kbh_fk, b.sanpham_fk, b.solo, b.denghi  " +
					" 	from ERP_DENGHIDOIHANG a inner join ERP_DENGHIDOIHANG_SANPHAM b on a.pk_seq = b.denghidoihang_fk " +
					" 	where a.pk_seq = '" + lsxId + "' " +
					" ) " +
					" doihang inner join NHAPP_KHO kho on doihang.kho_fk = kho.KHO_FK and doihang.NPP_FK = kho.NPP_FK " +
					" 		and doihang.KBH_FK = kho.KBH_FK and doihang.sanpham_fk = kho.SANPHAM_FK ";
			System.out.println("2.update DDH: " + query);
			if(!db.update(query))
			{
				msg = "Không thể cập nhật NHAPP_KHO " + query;
				db.getConnection().rollback();
				return msg;
			}

			String nppId="";
			query="select npp_fk from ERP_Denghidoihang where pk_Seq='"+lsxId+"' ";
			ResultSet rs =db.get(query);
			while(rs.next())
			{
				nppId=rs.getString("npp_fk");
			}
			if(rs!=null)rs.close();


			Utility util = new Utility();
			String NgayKs_CongMot="";
			NgayKs_CongMot=util.getNgayKs_CongMot(nppId, db);
			System.out.println("[NGAYCHOT]"+NgayKs_CongMot);
			if(NgayKs_CongMot.length()<=0)
			{
				msg = "Không thể xác định ngày KS của npp " ;
				db.getConnection().rollback();
				return msg;
			}

			query = "update ERP_Denghidoihang set NgayDeNghi='"+NgayKs_CongMot+"',trangthai = '1' where pk_seq = '" + lsxId + "'";
			if(!db.update(query))
			{
				msg = "2.Không thể cập nhật ERP_Denghidoihang:" + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query=
			"  update ERP_DENGHIDOIHANG_SANPHAM set DUYET=denghi,SANPHAM_DUYET_FK=sanpham_fk  "+
			"		where denghidoihang_fk ='"+lsxId+"' ";
			if(!db.update(query))
			{
				msg = "2.Không thể cập nhật ERP_DENGHIDOIHANG_SANPHAM:" + query;
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

	private String DeleteChuyenKho(String lsxId)
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);

			//TANG KHO NGUOC LAI
			String query =  " update kho  " +
					" 	set kho.BOOKED = kho.BOOKED - doihang.denghi, " +
					" 		kho.AVAILABLE = kho.AVAILABLE + doihang.denghi " +
					" from " +
					" ( " +
					" 	select a.kho_fk, a.npp_fk, a.kbh_fk, b.sanpham_fk, b.solo, b.denghi  " +
					" 	from ERP_DENGHIDOIHANG a inner join ERP_DENGHIDOIHANG_SANPHAM b on a.pk_seq = b.denghidoihang_fk " +
					" 	where a.pk_seq = '" + lsxId + "' " +
					" ) " +
					" doihang inner join NHAPP_KHO kho on doihang.kho_fk = kho.KHO_FK and doihang.NPP_FK = kho.NPP_FK " +
					" 		and doihang.KBH_FK = kho.KBH_FK and doihang.sanpham_fk = kho.SANPHAM_FK ";
			System.out.println("2.update DDH: " + query);
			if(!db.update(query))
			{
				msg = "Không thể cập nhật NHAPP_KHO " + query;
				db.getConnection().rollback();
				return msg;
			}

			/*query = " update kho  " +
					" 	set kho.BOOKED = kho.BOOKED - doihang.denghi, " +
					" 		kho.AVAILABLE = kho.AVAILABLE + doihang.denghi " +
					" from " +
					" ( " +
					" 	select a.kho_fk, a.npp_fk, a.kbh_fk, b.sanpham_fk, b.solo, b.denghi  " +
					" 	from ERP_DENGHIDOIHANG a inner join ERP_DENGHIDOIHANG_SANPHAM b on a.pk_seq = b.denghidoihang_fk " +
					" 	where a.pk_seq = '" + lsxId + "' " +
					" ) " +
					" doihang inner join NHAPP_KHO_CHITIET kho on doihang.kho_fk = kho.KHO_FK and doihang.NPP_FK = kho.NPP_FK " +
					" 		and doihang.KBH_FK = kho.KBH_FK and doihang.sanpham_fk = kho.SANPHAM_FK and doihang.solo = kho.SOLO ";
			System.out.println("3.update DDH: " + query);
			if(!db.update(query))
			{
				msg = "Không thể cập nhật NHAPP_KHO_CHITIET " + query;
				db.getConnection().rollback();
				return msg;
			}*/

			query = "Delete ERP_Denghidoihang_SanPham where denghidoihang_fk = '" + lsxId + "'";
			if(!db.update(query))
			{
				msg = "2.Khong the xoa: " + query;
				db.getConnection().rollback();
				return msg;
			}

			query = "Delete ERP_Denghidoihang where pk_seq = '" + lsxId + "'";
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


		IErpDenghidoihangNppList obj = new ErpDenghidoihangNppList();
		obj.setLoaidonhang(loaidonhang);

		Utility util = new Utility();

		HttpSession session = request.getSession();
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"))); 


		if(action.equals("timkiem")){
			String tungay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"));
			String denngay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay"));
			session.setAttribute("tungay", tungay);
		}
		if(action.equals("Tao moi"))
		{
			IErpDenghidoihangNpp lsxBean = new ErpDenghidoihangNpp();
			lsxBean.setLoaidonhang(loaidonhang);
			lsxBean.setUserId(userId);

			lsxBean.createRs();

			session.setAttribute("dvkdId", lsxBean.getDvkdId());
			session.setAttribute("kbhId", lsxBean.getKbhId());
			session.setAttribute("nppId", lsxBean.getNppId());
			session.setAttribute("ngaydonhang", lsxBean.getNgayyeucau());
			session.setAttribute("lsxBean", lsxBean);

			String nextJSP = "/AHF/pages/Distributor/ErpDeNghiDoiHangNppNew.jsp";
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

				String nextJSP = "/AHF/pages/Distributor/ErpDeNghiDoiHangNpp.jsp";
				response.sendRedirect(nextJSP);
			}
			else
			{
				String search = getSearchQuery(request, obj);
				obj.init(search);
				obj.setUserId(userId);

				session.setAttribute("obj", obj);  	
				session.setAttribute("userId", userId);

				String nextJSP = "/AHF/pages/Distributor/ErpDeNghiDoiHangNpp.jsp";
				response.sendRedirect(nextJSP);
			}
		}
	}

	private String getSearchQuery(HttpServletRequest request, IErpDenghidoihangNppList obj)
	{
		String query = "select a.PK_SEQ, a.trangthai, a.ngaydenghi as ngaydonhang, c.ten as nppTEN, b.ten as khoTEN, NV.TEN as nguoitao, b.ten as khonhan, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua   " +
				"from ERP_Denghidoihang a inner join KHO b on a.kho_fk = b.pk_seq inner join NHAPHANPHOI c on a.NPP_FK = c.pk_seq  " +
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

		System.out.print("TRuy van DAt: "+query);
		return query;
	}

	public String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);	
	}


}
