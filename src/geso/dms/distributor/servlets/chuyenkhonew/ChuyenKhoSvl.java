package geso.dms.distributor.servlets.chuyenkhonew;

import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.chuyenkhonew.IChuyenKho;
import geso.dms.distributor.beans.chuyenkhonew.IChuyenKhoList;
import geso.dms.distributor.beans.chuyenkhonew.imp.ChuyenKho;
import geso.dms.distributor.beans.chuyenkhonew.imp.ChuyenKhoList;
import geso.dms.distributor.db.sql.dbutils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@WebServlet("/ChuyenKhoSvl")
public class ChuyenKhoSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ChuyenKhoSvl() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session
				.getAttribute("util");
		if (!cutil.check(userId, userTen, sum)) {
			response.sendRedirect("/AHF/index.jsp");
		} else {

			IChuyenKhoList obj;

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			userId = (String) session.getAttribute("userId");
			userTen = (String) session.getAttribute("userTen");
			sum = (String) session.getAttribute("sum");
			Utility util = (Utility) session.getAttribute("util");

			String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
			if (view == null) {
				view = "";
			}
			
			if (!util.check(userId, userTen, sum)) {
				response.sendRedirect("/index.jsp");
			} else {
				session.setMaxInactiveInterval(1200);
				String querystring = request.getQueryString();
				String action = util.getAction(querystring);
				String ddhId = util.getId(querystring);

				geso.dms.distributor.util.Utility dutil = new geso.dms.distributor.util.Utility();
				String nppId = dutil.getIdNhapp(userId);
				String nppTen = dutil.getTenNhaPP();
				
				obj = new ChuyenKhoList();
				obj.setView(view);
				obj.setMsg("");
				if (action.equals("delete")) {
					obj.setMsg(Delete(ddhId, nppId));
				} else if (action.equals("GoiLenTrungTam")) {
					obj.setMsg(GoiLenTrungTam(ddhId));
				} else if (action.equals("Chot")) {
					obj.setMsg(Chot(ddhId, userId));
				}
				System.out.println("in ra đây __+____"+obj.getMsg());
				obj.setUserId(userId);
				obj.setNppTen(nppTen);
				obj.createDdhlist("");
				System.out.println("in ra đây __+____"+obj.getMsg());
				
				session.setAttribute("obj", obj);
				String nextJSP = "/AHF/pages/Distributor/ChuyenKho.jsp";
				response.sendRedirect(nextJSP);
			}
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session
				.getAttribute("util");

		if (!cutil.check(userId, userTen, sum)) {
			response.sendRedirect("/AHF/index.jsp");
		} else {
			IChuyenKhoList obj;
			
			String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
			if (view == null) {
				view = "";
			}

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			userId = (String) session.getAttribute("userId");
			userTen = (String) session.getAttribute("userTen");
			sum = (String) session.getAttribute("sum");
			Utility util = (Utility) session.getAttribute("util");

			if (!util.check(userId, userTen, sum)) {
				response.sendRedirect("index.jsp");
			} else {
				session.setMaxInactiveInterval(1200);
				obj = new ChuyenKhoList();
				obj.setView(view);
				String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
				if (action == null) {
					action = "";
				}

				if (action.equals("new")) {
					IChuyenKho dnhBean = (IChuyenKho) new ChuyenKho();
					dnhBean.setView(view);
					dnhBean.setUserId(userId);
					dnhBean.setNgayDc(Utility.getNgayHienTai());
					session.setAttribute("ckBean", dnhBean);
					dnhBean.createRs("");
					String nextJSP = "/AHF/pages/Distributor/ChuyenKhoNew.jsp";
					response.sendRedirect(nextJSP);
				} else if (action.equals("view") || action.equals("next")
						|| action.equals("prev")) {

					String nppId = "";

					geso.dms.distributor.util.Utility dutil = new geso.dms.distributor.util.Utility();

					nppId = dutil.getIdNhapp(userId);

					String search = getSearchQuery(request, util, nppId, obj);

					obj.setNxtApprSplitting(Integer.parseInt(request
							.getParameter("nxtApprSplitting")));

					obj.setUserId(userId);
					session.setAttribute("userId", userId);
					obj.createDdhlist(search);
					session.setAttribute("obj", obj);
					obj.setAttribute(request, action, "list",
							"crrApprSplitting", "nxtApprSplitting");
					String nextJSP = "/AHF/pages/Distributor/ChuyenKho.jsp";
					System.out.print(search);
					response.sendRedirect(nextJSP);
				} else {

					String nppId = "";

					geso.dms.distributor.util.Utility dutil = new geso.dms.distributor.util.Utility();
					nppId = dutil.getIdNhapp(userId);

					String search = getSearchQuery(request, util, nppId, obj);
					obj.setUserId(userId);
					session.setAttribute("userId", userId);

					obj.createDdhlist(search);

					session.setAttribute("obj", obj);

					String nextJSP = "/AHF/pages/Distributor/ChuyenKho.jsp";
					response.sendRedirect(nextJSP);
				}
			}
		}
	}

	private String getSearchQuery(HttpServletRequest request, Utility util,
			String nppId, IChuyenKhoList obj) {
		String tungay = util.ValidateParam(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"))));
		if (tungay == null)
			tungay = "";
		obj.setTungay(tungay);

		String denngay = util.ValidateParam(util.antiSQLInspection(request
				.getParameter("denngay")));
		if (denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		String nguoitao = util.ValidateParam(util.antiSQLInspection(request
				.getParameter("nguoitao")));
		if (nguoitao == null)
			nguoitao = "";
		obj.setNguoitao(nguoitao);
		String sochungtu = util.ValidateParam(util.antiSQLInspection(request
				.getParameter("sochungtu")));
		if (sochungtu == null)
			sochungtu = "";
		obj.setSochungtu(sochungtu);
		String trangthai = util.ValidateParam(util.antiSQLInspection(request
				.getParameter("trangthai")));
		if (trangthai == null)
			trangthai = "";

		obj.setTrangthai(trangthai);

		String query = "	select case when dh.TRANGTHAI=0 then 0  when dh.TRANGTHAI=1 then 1  when dh.TRANGTHAI=2 then 3  when dh.TRANGTHAI=3 then 2 end sapxep,"
				+ " dh.npp_fk as nppId,dh.ngaydieuchinh, dh.NgayTTXacNhan, dh.pk_seq as nhaphangId,dh.NgayGoiLenTT ,nt.TEN as NguoiTao,dh.ngaytao,ns.TEN as NguoiSua,dh.ngaysua, nd.ten as nguoiduyet, dh.ngaychot, dh.TrangThai "
				+ "	from ChuyenKho dh inner join NHANVIEN nt on  dh.NguoiTao=nt.PK_SEQ "
				+ "		inner join NHANVIEN ns on ns.PK_SEQ=dh.NguoiSua "
				+ "		left join NHANVIEN nd on nd.PK_SEQ=dh.Nguoiduyet ";
		if (nppId.length() > 0) {
			query += "where dh.npp_fk='" + nppId + "' ";
		} else {
			query += " where dh.trangthai <> 0";
		}

		if (tungay.length() > 0) {
			query = query + " and dh.ngaysua >= '" + tungay + "'";
		}
		if (nguoitao.length() > 0) {
			query = query + " and dh.nguoitao = '" + nguoitao + "'";
		}
		if (sochungtu.length() > 0) {
			query = query + " and dh.pk_seq like N'%" + sochungtu + "%'";
		}
		if (denngay.length() > 0) {
			query = query + " and dh.ngaysua <= '" + denngay + "'";
		}

		if (trangthai.length() > 0) {
			query = query + " and dh.trangthai = '" + trangthai + "'";
		}
		System.out.println("tim kiem chuyen kho " + query);
		return query;
	}

	private String Delete(String id, String nppId) {
		dbutils db = new dbutils();
		String query = "";
		try {
			
			db.update("SET TRANSACTION ISOLATION LEVEL SNAPSHOT;");
			db.update("SET LOCK_TIMEOUT 60000;");
			db.getConnection().setAutoCommit(false);
			
			
			String msgKS = geso.dms.center.util.Utility.Check_Huy_NghiepVu_KhoaSo("ChuyenKho", id, "ngaydieuchinh", db);
			if( msgKS.length() > 0)
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return msgKS;
			}
			
			
			
			query = "update  ChuyenKho set nguoisua=nguoisua where pk_seq='" + id
					+ "'  and trangthai in (1,0) ";
			if ( db.updateReturnInt(query)!=1) {
				db.getConnection().rollback();
				return "Không thể xóa do trạng thái không hợp lệ! ";
			}
			
			/*query = "		UPDATE KHO SET BOOKED=BOOKED-CKSP.SOLUONG,AVAILABLE =AVAILABLE +CKSP.SOLUONG "
					+ "			FROM CHUYENKHO CK INNER JOIN CHUYENKHO_SANPHAM CKSP ON CKSP.CHUYENKHO_FK=CK.PK_sEQ "
					+ "		INNER JOIN NHAPP_KHO KHO ON KHO.NPP_FK=CK.NPP_FK AND KHO.KBH_FK=CK.KBH_FK AND KHO.SANPHAM_FK =CKSP.SANPHAM_FK "
					+ "		AND KHO.KHO_FK=CK.KHOCHUYEN_FK "
					+ "      WHERE CK.PK_SEQ='" + id + "'      ";

			if (!db.update(query)) {
				db.getConnection().rollback();
				return "Không thể xóa chuyển kho TT ! " + query;
			}*/
			geso.dms.center.util.Utility util = new geso.dms.center.util.Utility();
			query=	  " SELECT CK.ngaydieuchinh,cksp.SANPHAM_FK,CK.KHOCHUYEN_FK,CK.NPP_FK,CK.KBH_FK,CKSP.SOLUONG   "
					+ "FROM CHUYENKHO CK INNER JOIN CHUYENKHO_SANPHAM CKSP ON CKSP.CHUYENKHO_FK=CK.PK_sEQ   "
					+ "      WHERE CK.PK_SEQ='" + id + "'      ";
				String msg;	
				System.out.println("UPDATE NPP KHO: " + query  );
		    ResultSet ckRs = db.get(query);
		    while(ckRs.next())
		    {
		    	nppId=ckRs.getString("NPP_FK");
		    	String kho_fk=ckRs.getString("KHOCHUYEN_FK");
				String kenh =ckRs.getString("kbh_fk");
				String sanpham_fk=ckRs.getString("sanpham_fk");
				String ngaynhap  =ckRs.getString("ngaydieuchinh");
				//String tensp  =ckRs.getString("tensp");
				Double soluong = ckRs.getDouble("soluong");


				msg=util.Update_NPP_Kho_Sp(ngaynhap,id, "delete chuyen kho_1454552", db, kho_fk, sanpham_fk, nppId, kenh, 0.0, (-1)* soluong, soluong, 0.0);
				if(msg.length()>0)
				{
					db.getConnection().rollback();
					msg = "Lỗi kho khi tạo đơn hàng: " + msg;
					return msg;
				}		
		    }			
		    
		    query=	 " SELECT CK.ngaydieuchinh,cksp.SANPHAM_FK,CK.KHOCHUYEN_FK,CK.NPP_FK,CK.KBH_FK,CKSP.SOLUONG ,CKSP.ngaynhapkho  "
		    		+ "FROM CHUYENKHO CK INNER JOIN CHUYENKHO_SANPHAM_chitiet CKSP ON CKSP.CHUYENKHO_FK=CK.PK_sEQ  "
					+ "      WHERE CK.PK_SEQ='" + id + "'      ";
					
				System.out.println("UPDATE NPP KHO: " + query  );
			    ckRs = db.get(query);
			    while(ckRs.next())
			    {
			    	nppId=ckRs.getString("NPP_FK");
			    	
			    	String kho_fk=ckRs.getString("KHOCHUYEN_FK");
					String kenh =ckRs.getString("kbh_fk");
					String sanpham_fk=ckRs.getString("sanpham_fk");
					String ngaynhap  =ckRs.getString("ngaydieuchinh");
					//String tensp  =ckRs.getString("tensp");
					Double soluong = ckRs.getDouble("soluong");
			    	String ngaynhapkho=ckRs.getString("NGAYNHAPKHO");    	
						String msg1=util.Update_NPP_Kho_Sp_Chitiet(ngaynhap, "delete chuyenkhochitiet DHID: "+id ,  db, kho_fk, sanpham_fk, nppId, kenh, ngaynhapkho, 0,(-1)*soluong, soluong, 0, 0);
						if (msg1.length()> 0) 
						{
							msg=msg1;
							db.getConnection().rollback();
							return msg;
						}
			    }		
			query = "update  ChuyenKho set  TrangThai = 2  where pk_seq='" + id
					+ "'    and trangthai in (1,0) ";
			if ( db.updateReturnInt(query)!=1) {
				db.getConnection().rollback();
				return "Không thể xóa do trạng thái không hợp lệ! ";
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e) {
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			return "Exception xóa chuyển kho " + e.getMessage() + " " + query;
		} finally {
			db.shutDown();
		}
		return "";
	}

	private String Chot(String id, String userid) {
		dbutils db = new dbutils();
		String query = "";
		geso.dms.center.util.Utility util = new geso.dms.center.util.Utility();
		String msg ="";
		db.update("SET TRANSACTION ISOLATION LEVEL SNAPSHOT;");
		db.update("SET LOCK_TIMEOUT 60000;");
		try {
			
			
			String msgKS = geso.dms.center.util.Utility.Check_Huy_NghiepVu_KhoaSo("ChuyenKho", id, "ngaydieuchinh", db);
			if( msgKS.length() > 0)
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return msgKS;
			}
			
			query = " update CHUYENKHO_SANPHAM_chitiet set ngaynhapkho_nhan = ( select ngaydieuchinh from chuyenkho where pk_seq = CHUYENKHO_SANPHAM_chitiet.chuyenkho_fk  ) where  chuyenkho_fk  =  " + id;
			if (db.updateReturnInt(query) <=0 ) {
				db.getConnection().rollback();
				return "Không thể ghi nhận ngay nhận kho! ";
			}
			
			query = "	SELECT KHOCHUYEN_FK,KHONHAN_FK,NPP_FK,KBH_FK,SANPHAM_FK,SOLUONG,CHUYENKHO_FK,CKSP.DONGIA, ck.ngaydieuchinh  "
					+ "	FROM "
					+ "	CHUYENKHO CK INNER JOIN CHUYENKHO_SANPHAM CKSP ON CKSP.CHUYENKHO_FK=CK.PK_sEQ "
					+ "	WHERE CK.PK_SEQ = '" + id + "' and ck.trangthai =1	";
			db.getConnection().setAutoCommit(false);
			System.out.println("query ___+"+query);
			ResultSet rs = db.get(query);

			while (rs.next()) {
				String nppId = rs.getString("npp_Fk");
				String spId = rs.getString("SANPHAM_FK");
				String khoId = rs.getString("KHOCHUYEN_FK");
				String kbhId = rs.getString("KBH_FK");
				String ngaydieuchinh = rs.getString("ngaydieuchinh");
				Double soluong = rs.getDouble("soluong");
				String khonhanId = rs.getString("khonhan_Fk");
				String dongia = rs.getString("dongia");

				msg=util.Update_NPP_Kho_Sp(ngaydieuchinh,id, "chot chuyen kho_1454552", db, khoId, spId, nppId, kbhId,(-1)* soluong, (-1)* soluong,0.0 , 0.0);
				System.out.println("wwwwwwwwwwwr33_______-"+msg);
				if(msg.length()>0)
				{
					db.getConnection().rollback();
					return "Lỗi kho khi tạo đơn hàng: " + msg;
				}	

				msg=util.Update_NPP_Kho_Sp(ngaydieuchinh,id, "chot chuyen kho_nhan", db, khonhanId, spId, nppId, kbhId,soluong,0.0, soluong , 0.0);
				if(msg.length()>0)
				{
					db.getConnection().rollback();
					msg = "Lỗi kho khi tạo đơn hàng: " + msg;
					return msg;
				}	
			}

			query = "	SELECT KHOCHUYEN_FK,KHONHAN_FK,NPP_FK,KBH_FK,SANPHAM_FK,SOLUONG,CHUYENKHO_FK,CKSP.DONGIA, ck.ngaydieuchinh, cksp.ngaynhapkho "
					+ "	FROM "
					+ "	CHUYENKHO CK INNER JOIN CHUYENKHO_SANPHAM_chitiet CKSP ON CKSP.CHUYENKHO_FK=CK.PK_sEQ "
					+ "	WHERE CK.PK_SEQ = '" + id + "'	";
			System.out.println("___+_chitiet"+query);
			rs = db.get(query);

			while (rs.next()) {
				String nppId = rs.getString("npp_Fk");
				String spId = rs.getString("SANPHAM_FK");
				String khoId = rs.getString("KHOCHUYEN_FK");
				String kbhId = rs.getString("KBH_FK");
				String ngaydieuchinh = rs.getString("ngaydieuchinh");
				Double soluong = rs.getDouble("soluong");
				String khonhanId = rs.getString("khonhan_Fk");
				String dongia = rs.getString("dongia");
				String ngaynhapkho = rs.getString("ngaynhapkho");
				
				String msg1=util.Update_NPP_Kho_Sp_Chitiet(ngaydieuchinh, "chot chuyen kho DHID: "+id ,  db, khoId, spId, nppId, kbhId, ngaynhapkho, (-1)*soluong,(-1)*soluong, 0, 0, 0);
				if (msg1.length()> 0) 
				{
					msg=msg1;
					msg = "Lỗi kho khi tạo đơn hàng: " + msg;
					db.getConnection().rollback();
					return msg;
				}
 
				
				 msg1=util.Update_NPP_Kho_Sp_Chitiet(ngaydieuchinh, "chot chuyenkhochitiet DHID: "+id ,  db, khonhanId, spId, nppId, kbhId, ngaydieuchinh, soluong, 0,soluong, 0, 0);
				if (msg1.length()> 0) 
				{
					msg=msg1;
					msg = "Lỗi kho khi tạo đơn hàng: " + msg;
					db.getConnection().rollback();
					return msg;
				}
				
			}
			String ngayksthem1 = "";
			query = "select Replace(convert(char(10), DATEADD(day, 1, cast(max(ngayks) as datetime)) , 102) , '.', '-' ) as ngay from khoasongay where npp_fk=(select npp_fk from ChuyenKho where pk_Seq="
					+ id + ")";
			System.out.println("query +"+query);
			ResultSet rs1 = db.get(query);

			if (rs1 != null) {
				if (rs1.next()) {
					ngayksthem1 = rs1.getString("ngay");
				} else {
					db.getConnection().rollback();
					return "Không thể chốt chuyển kho " + query;
				}
			} else {
				db.getConnection().rollback();
				return "Không thể chốt chuyển kho " + query;
			}
			
			query = " update  ChuyenKho set TrangThai= 3, NgayChot = '"
					+ getDateTime() + "', nguoiduyet = " + userid
					+ " ,ngaygiochot = CONVERT(char(10),GETDATE(),121) + ' ' + CONVERT(CHAR( 5),GETDATE(),114)  where pk_seq='" + id + "' and trangthai = 1 ";
			System.out.println("___+_____"+query);
			if (db.updateReturnInt(query) != 1) {
				db.getConnection().rollback();
				return "Không thể chốt chuyển kho do trạng thái không hợp lệ! ";
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return "";

		} catch (Exception e) {
			e.printStackTrace();
			return "Exception xóa chuyển kho " + e.getMessage() + " " + query;
		}
	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	private String GoiLenTrungTam(String id) {
		System.out.println("===GoiLenTrungTam==query===");
		dbutils db = new dbutils();
		try {
			db.getConnection().setAutoCommit(false);

			
			String msgKS = geso.dms.center.util.Utility.Check_Huy_NghiepVu_KhoaSo("ChuyenKho", id, "ngaydieuchinh", db);
			if( msgKS.length() > 0)
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return msgKS;
			}
			
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();

			String query = " update  ChuyenKho set TrangThai = 1, ngayGoiLenTT='"
					+ dateFormat.format(date)+ "'  where pk_seq= "+ id+ " and trangthai = '0' ";
			System.out.println("===GoiLenTrungTam==query===" + query);
			if (db.updateReturnInt(query) != 1) {
				db.getConnection().rollback();
				return "Không thể gởi lên trung tâm kho do trạng thái không hợp lệ!";
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return "";
		} catch (Exception e) {
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			return "Exception xóa chuyển kho " + e.getMessage() + " ";
		} finally {
			db.shutDown();
		}
	}
}
