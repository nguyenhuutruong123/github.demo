
package geso.dms.distributor.servlets.donhangsam;

import geso.dms.center.beans.doctien.doctienrachu;
import geso.dms.distributor.beans.donhangsam.IDonhangsamList;
import geso.dms.distributor.beans.donhangsam.ISanpham;
import geso.dms.distributor.beans.donhangsam.imp.DonhangsamList;
import geso.dms.distributor.beans.donhangsam.IDonhangsam;
import geso.dms.distributor.beans.donhangsam.imp.Donhangsam;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.FixData;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.buf.UEncoder;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class DonhangSamplingSvl extends javax.servlet.http.HttpServlet implements
		javax.servlet.Servlet {
	private static final long serialVersionUID = 1L;

	PrintWriter out;

	private int items = 50;

	private int splittings = 20;

	public DonhangSamplingSvl() {
		super();
	}

	private void settingPage(IDonhangsamList obj) {
		Utility util = new Utility();
		if (getServletContext().getInitParameter("items") != null) {
			String i = getServletContext().getInitParameter("items").trim();
			if (util.isNumeric(i))
				items = Integer.parseInt(i);
		}

		if (getServletContext().getInitParameter("splittings") != null) {
			String i = getServletContext().getInitParameter("splittings")
					.trim();
			if (util.isNumeric(i))
				splittings = Integer.parseInt(i);
		}

		obj.setItems(items);
		obj.setSplittings(splittings);
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

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			out = response.getWriter();

			session.setMaxInactiveInterval(30000);

			Utility util = new Utility();
			out = response.getWriter();

			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);
			// out.println(userId);
			System.out.println();
			Date date = new Date();
			System.out.println("DonhangSampingSvl user :" + userId + "  ,sessionId: "
					+ session.getId());

			if (userId.length() == 0)
				userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
			String nppId;
			if (geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")) != null)
				nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));

			// Lay Nha PP Theo Dang Nhap Moi
			nppId = util.getIdNhapp(userId);

			String action = util.getAction(querystring);

			String dhId = util.getId(querystring);
			  String msg1="";
			if (action.equals("delete")) {
				if (querystring.contains("nppId")) {
					nppId = querystring.trim().substring(
							querystring.lastIndexOf("&") + 7,
							querystring.length());
					if (!Delete2(dhId, userId, nppId)) {
						out.print("Khong the xoa don hang nay...");
						return;
					}

				} else {
					  msg1= Delete(dhId, nppId, userId);
					
					if (msg1.length()> 0) {
						out.print("Khong the xoa don hang nay :" + dhId +" Lỗi: "+msg1);
						System.out.println("Khong the xoa don hang nay...");
					} else {
						out.print("Da xoa don hang nay :" + dhId);
						System.out.println("Da xoa don hang nay...");
					}
				}
			}

			IDonhangsamList obj;
			obj = new DonhangsamList();
			obj.setUserId(userId);
			settingPage(obj);
			obj.setMsg(msg1);
			obj.init("");
			

			session.setAttribute("obj", obj);
			session.setAttribute("khId", "");

			String nextJSP = "/AHF/pages/Distributor/DonHangSam.jsp";
			response.sendRedirect(nextJSP);
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

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			// out = response.getWriter();

			session.setMaxInactiveInterval(30000);

			userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
			String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
			System.out.println();
			System.out.println("DonhangSvl user :" + userId + "  ,sessionId: "
					+ session.getId());
			String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
			if (action == null) {
				action = "";
			}
			String loi = "";
			IDonhangsamList obj;
			obj = new DonhangsamList();
			String qtbh = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("qtbh"));
			obj.setqtbh(qtbh == null ? "" : qtbh);
			settingPage(obj);
			if (action.equals("new")) {
				// Empty Bean for distributor
				IDonhangsam dhBean = (IDonhangsam) new Donhangsam("");
				dhBean.setUserId(userId);
				dhBean.createRS();

				// Save Data into session
				session.setAttribute("dhBean", dhBean);// truyen vao session mot
														// doi tuong donhang co
														// gia tri rong khi khoi
														// tao de ben form don
														// hang nhan dc
				session.setAttribute("khId", "");
				session.setAttribute("ddkdId", "");
				session.setAttribute("nppId", dhBean.getNppId());

				String nextJSP = "/AHF/pages/Distributor/DonHangSamNew.jsp";
				response.sendRedirect(nextJSP);
			} else if (action.equals("XK")) {

				String dhId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dhId"));
				System.out.println("NPP: " + nppId + " DHID: " + dhId);
				if (nppId != null && dhId != null) {
					// loi = KiemTraSoLuongDonHang(dhId);
					System.out.println("Kiem tra loi " + loi);
					if (loi.trim().length() <= 0) {

						loi = CreatePxk(dhId, userId, nppId);

					}
				}

				String search = getSearchQuery(request, obj);
				System.out.println("Đây là lôi : "+loi);
				obj.setMsg(loi);
				obj.setUserId(userId);
				obj.setNxtApprSplitting(Integer.parseInt(request
						.getParameter("nxtApprSplitting")));
				obj.setUserId(userId);

				obj.init(search);
				session.setAttribute("obj", obj);
				response.sendRedirect("/AHF/pages/Distributor/DonHangSam.jsp");

			} else if (action.equals("duyetALL")) {
				String dhId[] = request.getParameterValues("donhangIds");
				if (dhId != null && nppId != null) {
					for (int i = 0; i < dhId.length; i++) {
						loi += createxuatkho_ALL(dhId[i], userId, nppId);
						/*
						 * if(loi.length() > 0) { break; }
						 */
					}
				} else
					loi = "Không thể chốt đơn hang !";

				String search = getSearchQuery(request, obj);
				obj.setMsg(loi);
				obj.setUserId(userId);
				// obj.init(search);
				session.setAttribute("userId", userId);
				// session.setAttribute("obj", obj);

				obj.setNxtApprSplitting(Integer.parseInt(request
						.getParameter("nxtApprSplitting")));
				// obj.setUserId(userId);

				obj.init(search);
				session.setAttribute("obj", obj);
				response.sendRedirect("/AHF/pages/Distributor/DonHangSam.jsp");

			} else if (action.equals("InALL")) {
				String dhId[] = request.getParameterValues("IndonhangIds");
				if (dhId != null && nppId != null) {

					response.setContentType("application/pdf");
					response.setHeader("Content-Disposition",
							" inline; filename=HoaDonGTGT.pdf");
					Document document = new Document(PageSize.A5.rotate(), 10,
							20, 10, 20);

					dbutils db = new dbutils();
					ServletOutputStream outstream = response.getOutputStream();
					IDonhangsam dhBean = null;
					try {
						PdfWriter.getInstance(document, outstream);
					} catch (DocumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					document.open();
					for (int i = 0; i < dhId.length; i++) {

						dhBean = new Donhangsam(dhId[i]);
						dhBean.setUserId(userId); // phai co UserId truoc khi
													// Init
						dhBean.init();
						dhBean.setKhId(dhBean.getKhId());
						List<ISanpham> splist = (List<ISanpham>) dhBean
								.getSpList();
						List<ISanpham> spkmlist = (List<ISanpham>) dhBean
								.getScheme_SpList();
						Hashtable<String, Float> scheme_tongtien = dhBean
								.getScheme_Tongtien();
						Hashtable<String, Float> scheme_chietkhau = dhBean
								.getScheme_Chietkhau();

						createDonHangPdf(document, dhId[i], db, userId);

						document.newPage();
						splist.clear();
						spkmlist.clear();
						scheme_tongtien.clear();
						scheme_chietkhau.clear();

					}
					dhBean.DBclose();
					db.shutDown();
					document.close();
				} else
					loi = "Không thể in đơn hang !";

				String search = getSearchQuery(request, obj);
				obj.setMsg(loi);
				obj.DBclose();
				/*
				 * obj.setUserId(userId); obj.init(search);
				 * session.setAttribute("userId", userId);
				 * session.setAttribute("obj", obj);
				 * response.sendRedirect("/AHF/pages/Distributor/DonHang.jsp");
				 */
			} else if (action.equals("search")) {

				String search = getSearchQuery(request, obj);
				System.out.println("cau lenh chay:" + search);
				obj.setUserId(userId);
				obj.init(search);
				// System.out.println("tu ngay"+ obj.getTungay());
				session.setAttribute("userId", userId);
				session.setAttribute("obj", obj);

				response.sendRedirect("/AHF/pages/Distributor/DonHangSam.jsp");
			} else if (action.equals("view") || action.equals("next")
					|| action.equals("prev")) {

				String search = getSearchQuery(request, obj);

				obj.setNxtApprSplitting(Integer.parseInt(request
						.getParameter("nxtApprSplitting")));
				obj.setUserId(userId);

				obj.init(search);
				obj.setAttribute(request, action, "list", "crrApprSplitting",
						"nxtApprSplitting");
				session.setAttribute("obj", obj);
				response.sendRedirect("/AHF/pages/Distributor/DonHangSam.jsp");
			}

		}
	}

	private String getSearchQuery(HttpServletRequest request, IDonhangsamList obj) {
		Utility util = new Utility();
		String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
		if (nppId == null)
			nppId = "";
		obj.setNppId(nppId);

		String ddkdId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdTen"));
		if (ddkdId == null)
			ddkdId = "";
		obj.setDdkdId(ddkdId);

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

		String sodonhang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sodonhang"));
		if (sodonhang == null)
			sodonhang = "";
		obj.setSohoadon(sodonhang);

		String khachhang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khachhang"));
		if (khachhang == null)
			khachhang = "";
		obj.setKhachhang(khachhang);

		String ttdh = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ttdh"));
		if (ttdh == null)
			ttdh = "";
		obj.setTtdh(ttdh);

		String tumuc = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tumuc"));
		if (tumuc == null)
			tumuc = "";
		obj.setTumuc(tumuc);

		String denmuc = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denmuc"));
		if (denmuc == null)
			denmuc = "";
		obj.setDenmuc(denmuc);

		String ttin = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthaiin"));
		if (ttin == null)
			ttin = "";
		obj.setTrangthaiin(ttin);

		String query = "select a.pk_seq as dhId, a.ngaynhap,a.trangthaiin, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua, d.ten as khTen, d.pk_seq as khId, e.pk_seq as ddkdId, e.ten as ddkdTen, f.ten as nppTen,a.FlagModified as ttdh,a.FlagModified  , isnull(a.tonggiatri,0) as tonggiatri, a.IsPDA,a.ddkdtao_fk, a.ddkdTao,( select isnull(quytrinhbanhang,0) from NHAPHANPHOI where pk_seq = "
				+ nppId + ") as qtbh ";
		query = query
				+ " from donhang a  with (updlock,readpast)  inner join nhanvien b on a.nguoitao = b.pk_seq inner join nhanvien c on a.nguoisua = c.pk_seq inner join khachhang d on a.khachhang_fk = d.pk_seq inner join daidienkinhdoanh e on a.ddkd_fk = e.pk_seq inner join nhaphanphoi f on a.npp_fk = f.pk_seq";
		query = query + " where a.npp_fk = '" + nppId + "' and isnull(a.issam,0) = 1";

		if (ddkdId.length() > 0) {
			query = query + " and e.pk_seq = '" + ddkdId + "'";
		}
		if (trangthai.length() > 0) {
			query = query + " and a.trangthai ='" + trangthai + "'";
		}
		if (ttin.length() > 0) {
			query = query + " and a.trangthaiin ='" + ttin + "'";
		}
		if (tungay.length() > 0) {
			query = query + " and a.ngaynhap >= '" + tungay + "'";
		}
		if (denngay.length() > 0) {
			query = query + " and a.ngaynhap <= '" + denngay + "'";
		}
		if (sodonhang.length() > 0) {
			query = query + " and a.pk_seq like '%"
					+ util.replaceAEIOU(sodonhang) + "%'";
		}
		if (khachhang.length() > 0) {

			query = query + " and (d.smartid like N'%" + khachhang
					+ "%' or dbo.ftBoDau(d.pk_seq) like (N'%"
					+ util.replaceAEIOU(khachhang)
					+ "%') or upper(dbo.ftBoDau(d.ten)) like upper(N'%"
					+ util.replaceAEIOU(khachhang) + "%') )";
		}
		if (tumuc.length() > 0) {
			query = query + " and a.tonggiatri >= '" + util.replaceAEIOU(tumuc)
					+ "'";
		}
		if (denmuc.length() > 0) {
			query = query + " and a.tonggiatri <= '"
					+ util.replaceAEIOU(denmuc) + "'";
		}

		if (ttdh.length() > 0) {
			query = query + " and a.FlagModified ='" + ttdh + "' ";
		}
		System.out.print("\nQuery cua ban: " + query);
		return query;
	}

	private String Delete(String id, String nppId, String userId) {
		dbutils db = new dbutils();
		try{
			db.getConnection().setAutoCommit(false);
			
			//vào trong hàm xóa có hàm update đơn hàng trạng thái 2
			String query = " update donhang set trangthai='0',ngayhuy=dbo.GetLocalDate(DEFAULT),NGUOIXOA='"
					+ userId + "' where pk_seq = '" + id + "' and trangthai = 0";
			
			if(db.updateReturnInt(query)!=1 ){
				db.getConnection().rollback();
				return "Không thể xóa đơn hàng: Vui lòng reload lại phiếu để xóa.";
				
			}
			
			
		  query = id + "&" + nppId;
		String param[] = query.split("&");

		int kq = db.execProceduce("DeleteDonHang", param);
		if (kq == -1){
			db.getConnection().rollback();
			return "Không thể xóa đơn hàng:lỗi xảy ra trong quá trình xóa đơn hàng ";
			
		}

		
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			er.printStackTrace();
			return "Xảy ra lỗi trong quá trình xóa : "+er.getMessage();
		}
		return "";
	}

	private String DeletePxk(String pxkId, dbutils db) {

		// khi moi tao phieuxuatkho chua lam gi het, nen co the xoa thang
		// String query =
		// "update phieuxuatkho set trangthai = '2' where pxk_fk = '" + pxkId +
		// "'";
		// db.update(query);
		try {
			db.getConnection().setAutoCommit(false);
			String query = "delete phieuxuatkho_tienkm where pxk_fk = '"
					+ pxkId + "'";
			if (!db.update(query)) {
				db.getConnection().rollback();
				return query;
			}

			// System.out.println("delete phieuxuatkho_tienkm where pxk_fk = '"
			// + pxkId + "'");
			query = "delete phieuxuatkho_sanpham where pxk_fk = '" + pxkId
					+ "'";
			System.out.println(query);
			if (!db.update(query)) {
				db.getConnection().rollback();
				return query;
			}
			// System.out.println("delete phieuxuatkho_sanpham where pxk_fk = '"
			// + pxkId + "'");
			query = "delete phieuxuatkho_spkm where pxk_fk = '" + pxkId + "'";
			System.out.println(query);
			if (!db.update(query)) {
				db.getConnection().rollback();
				return query;
			}
			query = "delete PHIEUXUATKHO_DONHANG where pxk_fk='" + pxkId + "'";
			System.out.println(query);
			if (!db.update(query)) {
				db.getConnection().rollback();
				return query;
			}

			query = "delete phieuxuatkho where pk_seq = '" + pxkId
					+ "' and trangthai = '0'";
			System.out.println(query);
			if (db.updateReturnInt(query) != 1) {
				db.getConnection().rollback();
				return query;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);

		} catch (Exception er) {
			System.out.println(er);
		}

		return "";

	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public String CreatePxk(String dhid, String userid, String nppId) {
		dbutils db = new dbutils();
		String ngaytao = getDateTime();
		String nguoitao = userid;
		String msg = "";
		ResultSet rsPxk = null;
		try {

			System.out.println(":::::THOI GIAN BAT DAU chot  1 "
					+ this.getDateTimeTEST());
			String sql = "";
			String query = "select ngaynhap,trangthai,kho_FK,kbh_FK from donhang where pk_seq ="
					+ dhid;
			ResultSet rs = db.get(query);
			rs.next();
			String ngaydonhang = rs.getString("Ngaynhap");
			String trangthai = rs.getString("trangthai");
			String kho = rs.getString("kho_fk");
			String kenh = rs.getString("kbh_FK");
			rs.close();
			System.out.println(":::::THOI GIAN BAT DAU chot 2 "
					+ this.getDateTimeTEST());

			query = "select max(ngayks) as ngayks from khoasongay where npp_fk = '"
					+ nppId + "' ";
			System.out.println("NgayKS: " + query);
			String ngayks = "";
			rs = db.get(query);
			if (rs != null) {
				if (rs.next())
					ngayks = rs.getString("ngayks") == null ? "" : rs
							.getString("ngayks");
				rs.close();
			} else {
				msg += "NPP chưa có ngày khóa sổ. Không chốt được đơn hàng! \n";
				return msg;
			}
			if (ngayks.length() <= 0) {
				msg += "NPP chưa có ngày khóa sổ. Không chốt được đơn hàng! \n";
				return msg;
			}
			if (ngayks.compareTo(ngaydonhang) >= 0) {
				msg += "Đơn hàng số "
						+ dhid
						+ " không được phép chốt trước ngày khóa sổ.Vui lòng bỏ chọn \n";
				return msg;
			}

			if (getDateTime().compareTo(ngaydonhang) >= 0) {
				msg += "Đơn hàng số " + dhid
						+ " không được phép chốt trước ngày đơn hàng \n";
			}
			System.out.println(":::::THOI GIAN BAT DAU chot 3 "
					+ this.getDateTimeTEST());

			query = "select count(*) as dhth from  PHIEUTHUHOI a inner join PHIEUTHUHOI_DONHANG "
					+ " b on a.PK_SEQ = b.pth_fk where b.donhang_fk = '"
					+ dhid
					+ "' and a.trangthai = '0' ";
			rs = db.get(query);
			if (rs != null) {
				if (rs.next()) {
					if (rs.getInt("dhth") > 0) {
						msg += "Đơn hàng số "
								+ dhid
								+ " không được phép chốt,Vui lòng chốt phiếu thu hồi trước khi chốt đơn hàng !\n";
						return msg;
					}
				}
				rs.close();
			}

			System.out.println(":::::THOI GIAN BAT DAU chot 4 "
					+ this.getDateTimeTEST());

			db.update("SET TRANSACTION ISOLATION LEVEL SNAPSHOT;");
			db.update("SET LOCK_TIMEOUT 100000;");
			
			db.getConnection().setAutoCommit(false);

			query = "select TRANGTHAI FROM DONHANG where pk_seq='" + dhid
					+ "' ";
			System.out.println(" check trang thai:" + query);
			ResultSet rsCheck = db.get(query);
			rsCheck.next();
			// if(rsCheck.next())
			// {
			if (rsCheck.getInt("TRANGTHAI") == 1
					|| rsCheck.getInt("TRANGTHAI") == 3) {
				System.out.println("vao check");
				db.getConnection().rollback();
				return "Không thể chốt đơn hàng này: phiếu này đã được chốt hoặc trạng thái không hợp lệ! ";
			}
			// }
			rsCheck.close();

			// Booked cho đơn PDA

			if (!trangthai.equals("2") && !trangthai.equals("1")
					&& !trangthai.equals("3")) {


				query=" update donhang WITH (ROWLOCK) set NGAYCHOT = dbo.GetLocalDate(DEFAULT)"+
					  "		where pk_seq = "+dhid+" and trangthai =0  ";
				if(db.updateReturnInt(query) !=1 )
				{
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					return "Không thể chốt đơn hàng, vui lòng reload lại phiếu để chốt lại ";
				}
				


				query = "select DonHang_fk,pxk_Fk from phieuxuatkho_donhang a inner join phieuxuatkho b on a.pxk_FK = b.pk_seq  where donhang_fk = "
						+ dhid + " and b.trangthai <> 2 ";
				System.out.println("Check PXK: " + query);
				rs = db.get(query);
				while (rs.next()) {
					msg += "Đơn hàng số " + rs.getString("donhang_fk")
							+ " đã tồn tại trong phiếu xuất kho "
							+ rs.getString("pxk_fk")
							+ " vui lòng bỏ chọn ... \n";
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					return msg;

				}
				rs.close();
				System.out.println(":::::THOI GIAN BAT DAU chot 6 "
						+ this.getDateTimeTEST());

				// BOOKED cho đơn hàng PDA
				if (trangthai.equals("0")) {
					query = "select b.TEN, b.MA, a.available, a.available + isnull(dh_sp.soluong, 0) as available "
							+ "from NHAPP_KHO a inner join SANPHAM b on a.sanpham_fk = b.pk_seq "
							+ "	inner join "
							+ "	( "
							+ "		select sanpham_fk, soluong from DONHANG_SANPHAM where donhang_fk = '"
							+ dhid
							+ "' "
							+ "	) "
							+ "	dh_sp on b.pk_seq = dh_sp.sanpham_fk "
							+ " where a.NPP_FK = '"
							+ nppId
							+ "' and a.kho_fk = '"
							+ kho
							+ "' and KBH_FK = '"
							+ kenh
							+ "' "
							+ "	and a.available  + isnull(dh_sp.soluong, 0) > isnull(a.soluong, 0) ";

					System.out.println("---CHECK TON KHO1: " + query);
					ResultSet rsChheck = db.get(query);
					msg = "";
					if (rsChheck != null) {
						while (rsChheck.next()) {
							msg += "\n Đơn hàng " + dhid + ". Sản phẩm ( "
									+ rsChheck.getString("TEN")
									+ " ) còn tối đa ( "
									+ rsChheck.getString("available")
									+ " ) . Vui lòng kiểm tra lại ";

						}
						rsChheck.close();
					}
					if (msg.trim().length() > 0) {
						db.getConnection().rollback();
						return msg;
					}
					System.out.println(":::::THOI GIAN BAT DAU chot 7 "
							+ this.getDateTimeTEST());

					query = "\n select b.TEN, b.MA, a.available, a.available + isnull(dh_sp.soluong, 0) as available, kh.DIENGIAI "
							+ "\n from NHAPP_KHO a inner join SANPHAM b on a.sanpham_fk = b.pk_seq "
							+ "\n	inner join "
							+ "\n	( "
							+ "\n		select spma, soluong,ctkmid from DONHANG_CTKM_TRAKM where donhangid = '"
							+ dhid
							+ "' "
							+ "\n	) "
							+ "\n	dh_sp on b.ma = dh_sp.spma "
							+ "\n  inner join CTKHUYENMAI ctkm on ctkm.PK_SEQ = dh_sp.CTKMID and a.kho_fk = ctkm.kho_fk"
							+ "\n inner join KHO kh on a.KHO_FK = kh.PK_SEQ"
							+ "\n where a.NPP_FK = '"
							+ nppId
							+ "'  and a.KBH_FK = '"
							+ kenh
							+ "' "
							+ "\n		and a.available + isnull(dh_sp.soluong, 0)  > isnull(a.soluong, 0) ";

					System.out.println("---CHECK TON KHO KM2: " + query);
					rsChheck = db.get(query);
					msg = "";
					if (rsChheck != null) {
						while (rsChheck.next()) {
							msg += "\n Đơn hàng " + dhid + ". Sản phẩm ( "
									+ rsChheck.getString("TEN")
									+ " ) còn tối đa ( "
									+ rsChheck.getString("available")
									+ " ) trong "
									+ rsChheck.getString("DIENGIAI")
									+ " . Vui lòng kiểm tra lại ";

						}
						rsChheck.close();
					}
					if (msg.trim().length() > 0) {
						db.getConnection().rollback();
						db.getConnection().setAutoCommit(true);
						return msg;
					}
				}
				System.out.println(":::::THOI GIAN BAT DAU chot 8 "
						+ this.getDateTimeTEST());

			

				query = "insert into Phieuxuatkho(nvgn_fk, trangthai, ngaytao, ngaysua, nguoitao, nguoisua, npp_fk, ngaylapphieu) ";
				query = query
						+ " select case "
						+ " when len((select top(1) NVGN_FK from  DONHANG where PK_SEQ = "
						+ dhid
						+ " )) > 1 "
						+ " then "
						+ " (select top(1) NVGN_FK from  DONHANG where PK_SEQ = "
						+ dhid
						+ " ) "
						+ " when len((select top(1) NVGN_FK from  NVGN_KH where KHACHHANG_FK =(select top(1) NVGN_FK from  DONHANG where PK_SEQ = "
						+ dhid
						+ ") )) > 1 "
						+ " then "
						+ " (select top(1) NVGN_FK from  NVGN_KH where KHACHHANG_FK =(select top(1) NVGN_FK from  DONHANG where PK_SEQ = "
						+ dhid
						+ ") )"
						+ " else "
						+ " (select top(1) pk_seq from  NHANVIENGIAONHAN where NPP_FK = '"
						+ nppId + "') " + " end,'0','" + ngaytao + "','"
						+ ngaytao + "','" + nguoitao + "','" + nguoitao + "','"
						+ nppId + "','" + ngaytao + "'";
				if (!db.update(query)) {
					db.getConnection().rollback();
					msg += "\n Đơn hàng " + dhid + ". Lỗi: " + query;
					return msg;
				}

				query = "select SCOPE_IDENTITY() as pxkId";

				rsPxk = db.get(query);
				rsPxk.next();
				String id = rsPxk.getString("pxkId");
				rsPxk.close();

				// bang phieuxuatkho_donhang
				System.out.println("So don hang la: " + dhid);

				float tonggiatri = 0.0f;

				// chua can luu tonggiatri o buoc nay
				sql = "Insert into phieuxuatkho_donhang(pxk_fk, donhang_fk, tonggiatri) values('"
						+ id + "', '" + dhid + "', null)";

				if (db.updateReturnInt(sql) <= 0) {
					db.getConnection().rollback();
					msg += "\n Đơn hàng " + dhid
							+ ". Không thể tạo phiếu xuất kho: " + sql;
					return msg;
				}
				System.out.println(":::::THOI GIAN BAT DAU chot 11 "
						+ this.getDateTimeTEST());

				msg = Chotphieuxuat(id, nppId, ngaydonhang, db);
				if (msg.length() > 0) {
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					return msg;
				}
				System.out.println(":::::THOI GIAN BAT DAU chot 12 "
						+ this.getDateTimeTEST());

				msg = chotDonhang(nppId, dhid, userid, db);
				if (msg.length() > 0) {
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					return msg;
				}
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);


			} else if (trangthai.equals("3")) {
				// db.getConnection().setAutoCommit(false);
				
				query=" update donhang WITH (ROWLOCK) set NGAYCHOT = dbo.GetLocalDate(DEFAULT)"+
						  "		where pk_seq = "+dhid+" and trangthai =3  ";
					if(db.updateReturnInt(query)<=0)
					{
						db.getConnection().rollback();
						db.getConnection().setAutoCommit(true);
						return "Đơn hàng đang xử lý";
					}
					
				msg = chotDonhang(nppId, dhid, userid, db);
				if (msg.length() > 0) {
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					return msg;
				}
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
			}else{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return "Vui lòng reload lại đơn hàng, trạng thái đơn hàng không hợp lệ";
			}
			db.shutDown();
		} catch (Exception e) {
			db.shutDown();
			e.printStackTrace();
			return msg;
		}

		return "";
	}

	public String KiemTraSoLuongDonHang(String dhid) {

		dbutils db = new dbutils();

		String msg = "";

		{
			try {

				int sodong = 0;
				String query = "select ISNULL(SOLUONG,0) SOLUONG ,SANPHAM_FK from DONHANG_SANPHAM where DONHANG_FK="
						+ dhid;

				ResultSet rs = db.get(query);
				System.out
						.println("Check don hang chua cap nhat san pham tra trung bay "
								+ query);
				if (rs != null) {
					while (rs.next()) {
						sodong = Integer.parseInt(rs.getString("SOLUONG"));
						if (sodong <= 0) {

							return "Đơn hàng không thể chốt vui lòng kiểm tra số lượng sản phẩm";
						}
					}
					rs.close();
				}

			} catch (Exception e) {

				msg = "Lỗi không chốt được đơn hàng : " + e.toString();
				return msg;
			}
		}
		return "";
	}

	private String Chotphieuxuat(String pxkId, String nppId, String ngaylap,
			dbutils db) {

		String msg = "";
		String ngaytiep = "";
		int songay = 0;

		// check ton kho

		String query = "select pxk_sp.spMa "
				+ "from "
				+ "("
				+ "select khoId, kbhId, spId, spMa, sum(soluong) as soluong "
				+ "from( "
				+ "select c.kho_fk as khoId, c.kbh_fk as kbhId, b.pk_seq as spId, b.ma as spMa, sum(a.soluong) as soluong "
				+ "from donhang_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq inner join donhang c on a.donhang_fk = c.pk_seq "
				+ "where c.trangthai != 2 and a.donhang_fk in (select donhang_fk from phieuxuatkho_donhang where pxk_fk = "
				+ pxkId
				+ ") "
				+ "group by c.kho_fk, c.kbh_fk, b.pk_seq, b.ma "
				+ "union all "
				+ "select b.kho_fk as khoId, e.kbh_fk as kbhId, d.pk_seq as spId, a.spMa, sum(a.soluong) as soluong "
				+ "from donhang_ctkm_trakm a inner join ctkhuyenmai b on a.ctkmid = b.pk_seq "
				+ "	inner join sanpham d on a.spMa = d.ma inner join donhang e on a.donhangId = e.pk_seq "
				+ "where e.trangthai != 2 and a.spMa is not null and a.donhangId in (select donhang_fk from phieuxuatkho_donhang where pxk_fk = "
				+ pxkId
				+ ") "
				+ "group by b.kho_fk, e.kbh_fk, a.ctkmId, a.spMa, d.pk_seq "
				+ " ) a "
				+ " group by khoId, kbhId, spId, spMa "
				+ ") pxk_sp "
				+ "left join "
				+ "( "
				+ "	select kho_fk, npp_fk, sanpham_fk, kbh_fk, soluong from nhapp_kho where npp_fk = '"
				+ nppId
				+ "' "
				+ ") kho_npp on pxk_sp.khoId = kho_npp.kho_fk and pxk_sp.kbhId = kho_npp.kbh_fk and pxk_sp.spId = kho_npp.sanpham_fk "
				+ "where (isnull(kho_npp.soluong,0) - pxk_sp.soluong) < 0";

		System.out.println("Query check chot phieu xuat kho: " + query + "\n");
		ResultSet sosp = db.get(query);
		String spMa = "";
		if (sosp != null) {
			try {
				while (sosp.next()) {
					spMa += sosp.getString("spMa") + ",";
					System.out.println("sp mã "+spMa);
				}
				sosp.close();
			} catch (Exception e1) {
			}
		}

		if (spMa.length() > 0) {
			msg = "Các mã sản phẩm sau: "
					+ spMa
					+ " không đủ số lượng trong kho \nVui lòng kiểm tra lại số lượng trước khi chốt phiếu xuất kho";
			return msg;
		}
		
		
		
		// check ton kho chi tiết 

				query = "select pxk_sp.spMa "
						+ "from "
						+ "("
						+ "select khoId, kbhId, spId, spMa, sum(soluong) as soluong "
						+ "from( "
						+ "select c.kho_fk as khoId, c.kbh_fk as kbhId, b.pk_seq as spId, b.ma as spMa, sum(a.soluong) as soluong, c.ngaynhap "
						+ "from donhang_sanpham_chitiet a inner join sanpham b on a.sanpham_fk = b.pk_seq inner join donhang c on a.donhang_fk = c.pk_seq "
						+ "where c.trangthai != 2 and a.donhang_fk in (select donhang_fk from phieuxuatkho_donhang where pxk_fk = "
						+ pxkId+ " and a.ngaynhapkho >= c.ngaynhap ) "
						+ "group by c.kho_fk, c.kbh_fk, b.pk_seq, b.ma, c.ngaynhap "
						+ "union all "
						+ "select b.kho_fk as khoId, e.kbh_fk as kbhId, d.pk_seq as spId, a.spMa, sum(a.soluong) as soluong "
						+ "from donhang_ctkm_trakm_chitiet a inner join ctkhuyenmai b on a.ctkmid = b.pk_seq "
						+ "	inner join sanpham d on a.spMa = d.ma inner join donhang e on a.donhangId = e.pk_seq "
						+ "where e.trangthai != 2 and a.spMa is not null and a.donhangId in (select donhang_fk from phieuxuatkho_donhang where pxk_fk = "
						+ pxkId + " and a.ngaynhapkho >= e.ngaynhap) "
						+ "group by b.kho_fk, e.kbh_fk, a.ctkmId, a.spMa, d.pk_seq "
						+ " ) a "
						+ " group by khoId, kbhId, spId, spMa "
						+ ") pxk_sp "
						+ "left join "
						+ "( "
						+ "	select kho_fk, npp_fk, sanpham_fk, kbh_fk, soluong, ngaynhapkho from nhapp_kho_chitiet where npp_fk = '"
						+ nppId
						+ "' "
						+ ") kho_npp on pxk_sp.khoId = kho_npp.kho_fk and pxk_sp.kbhId = kho_npp.kbh_fk and pxk_sp.spId = kho_npp.sanpham_fk and kho_npp.ngaynhapkho <=  pxk_sp.ngaynhap "
						+ "where (isnull(kho_npp.soluong,0) - pxk_sp.soluong) < 0";

				System.out.println("Query check chot phieu xuat kho: " + query + "\n");
				sosp = db.get(query);
				spMa = "";
				if (sosp != null) {
					try {
						while (sosp.next()) {
							spMa += sosp.getString("spMa") + ",";
							System.out.println("sp mã chi tiết"+spMa);
						}
						sosp.close();
					} catch (Exception e1) {
					}
				}

				if (spMa.length() > 0) {
					msg = "Các mã sản phẩm sau: "
							+ spMa+ " không đủ số lượng trong kho chi tiết \nVui lòng kiểm tra lại số lượng trước khi chốt phiếu xuất kho";
					return msg;
				}


		query = "select DonHang_fk,pxk_Fk from phieuxuatkho_donhang where  pxk_Fk="+ pxkId
				+ " and  donhang_Fk in (select donhang_Fk from  phieuxuatkho_donhang where pxk_fk!="+ pxkId + " )";
		ResultSet rs = db.get(query);
		try {
			while (rs.next()) {
				msg += "Đơn hàng số " + rs.getString("donhang_fk")
						+ " đã tồn tại trong phiếu xuất kho "+ rs.getString("pxk_fk")+ " vui lòng bỏ chọn ............ \n";
			}
			rs.close();
			if (msg.length() > 0) {
				return msg;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		query = pxkId + "&" + nppId + "&" + ngaylap;
		String param[] = query.split("&");

		int kq = db.execProceduce("Chot_Phieu_Xuat_Kho", param);
		if (kq == -1)
			return "Không thể chốt phiếu xuất kho !...";

		query = "insert into logfile (pxk_fk,nguoisua,LOAI) values('" + pxkId+ "','" + nppId + "','1')";
		if (!db.update(query))
			return "error log file !!!";

		return "";
	}

	private String TangNgayKs(String ngayksgn) {
		String ngayks = ngayksgn;

		if (ngayks.equals(""))
			ngayks = this.getDateTime();

		String[] ngay = ngayks.split("-");

		Calendar c2 = Calendar.getInstance();

		// trong java thang bat dau bang 0 (thang rieng)
		c2.set(Integer.parseInt(ngay[0]), Integer.parseInt(ngay[1]) - 1,
				Integer.parseInt(ngay[2]));
		c2.add(Calendar.DATE, 1);

		String month = Integer.toString(c2.get(Calendar.MONTH) + 1);
		if ((c2.get(Calendar.MONTH) + 1) < 10) {
			month = "0" + Integer.toString(c2.get(Calendar.MONTH) + 1);
		}
		String date = Integer.toString(c2.get(Calendar.DATE));
		if ((c2.get(Calendar.DATE)) < 10) {
			date = "0" + Integer.toString(c2.get(Calendar.DATE));
		}

		System.out.println("ngay chon:"
				+ Integer.toString(c2.get(Calendar.YEAR)) + "-" + month + "-"
				+ date);
		return Integer.toString(c2.get(Calendar.YEAR)) + "-" + month + "-"
				+ date;
	}

	public String chotDonhang(String nppId, String dhid, String userid,
			dbutils db) {
		Utility util = new Utility();
		String ngayksgn = util.ngaykhoaso(nppId);
		String msg = "";

		{
			try {
				String query = "update donhang WITH (ROWLOCK) set NGAYCHOT = dbo.GetLocalDate(DEFAULT)"
						+ " where pk_seq = '"+ dhid
						+ "' and trangthai <> 1 "
						+ " and exists (select 1 from phieuxuatkho_donhang a inner join phieuxuatkho b on a.pxk_fk = b.pk_seq where a.donhang_fk = donhang.pk_seq and b.trangthai = '1' )";
				if (db.updateReturnInt(query) <= 0) {
					msg = "Không chốt được đơn hàng " + dhid
							+ ". Vì chưa có phiếu xuất kho";
					return msg;
				}

				int sodong = 0;
				// Kiem tra xem con don hang nao thoa CTTB ma chua cap nhat san
				// pham tra trung bay hay khong!
				/*
				 * String query = "select COUNT(*) as numb " +
				 * "from donhang a inner join phieuxuatkho_donhang c on a.pk_seq = c.donhang_fk "
				 * + "inner join phieuxuatkho d on c.pxk_fk = d.pk_seq " +
				 * "inner join DENGHITRATB_DONHANG dk on dk.donhang_fk=a.pk_seq  "
				 * + "where a.npp_fk = '" + nppId +
				 * "' and a.trangthai = '3' and a.ngaynhap > '" + ngayksgn +
				 * "' and a.ngaynhap <= '" + TangNgayKs() +
				 * "' and a.pk_seq not in(select donhang_fk from donhang_cttb_tratb) and dk.donhang_fk not in "
				 * + "(   \n "+ "	select donhang_fk      \n "+
				 * "	from DENGHITRATB_DONHANG      \n "+
				 * "	where DKTRUNGBAY_FK       \n "+ "	in     \n "+
				 * "	(     \n "+
				 * "		select pk_Seq from DENGHITRATRUNGBAY      \n "+
				 * "		where  CTTRUNGBAY_FK in      \n "+ "		(      \n "+
				 * "			select  CTTRUNGBAY_FK      \n "+
				 * "			from CTTB_TRATB      \n "+
				 * "			where TRATRUNGBAY_FK in ( select PK_SEQ from TRATRUNGBAY where LOAI = '1')     \n "
				 * + "		)     \n "+ "	)     \n "+ ")";
				 */

				query = "select COUNT(*) as numb  "
						+ "from donhang a inner join DENGHITRATB_DONHANG dk on dk.donhang_fk = a.pk_seq    "
						+ "where a.npp_fk = '"
						+ nppId
						+ "' and a.trangthai = '3' and a.ngaynhap > '"
						+ ngayksgn
						+ "' and a.ngaynhap <= '"
						+ TangNgayKs(ngayksgn)
						+ "' "
						+ "	and a.PK_SEQ not in ( select DONHANG_FK from DONHANG_CTTB_TRATB )";

				ResultSet rs = db.get(query);
				System.out
						.println("Check don hang chua cap nhat san pham tra trung bay "
								+ query);
				if (rs != null) {
					while (rs.next()) {
						sodong = rs.getInt(1);
					}
					rs.close();
				}
				if (sodong > 0) {
					msg = "Còn "
							+ sodong
							+ " đơn hàng chưa cập nhật trả trưng bày !Cập nhật trả trưng bày trước khi chốt đơn hàng !";
					return msg;
				}

				query = "\n delete a from donhang_ctkm_dkkm a "
						+ "\n inner join sanpham b on a.sanpham_fk = b.MA "
						+ "\n inner join DONHANG_SANPHAM c on a.donhang_fk = c.DONHANG_FK and c.SANPHAM_FK = b.PK_SEQ "
						+ "\n where not exists (select 1 from DONHANG_CTKM_TRAKM d where a.ctkm_fk = d.CTKMID and DONHANGID = c.DONHANG_FK ) "
						+ "\n and c.donhang_fk ='" + dhid + "' ";

				System.out.println(query);
				if (!db.update(query)) {
					msg = "Lỗi cập nhật đơn hàng, donhang_ctkm_dkkm";
					return msg;
				}

				query = "select (select count(distinct ctkm_fk) as ctkmid  from donhang_ctkm_dkkm a"
						+ " where donhang_fk  = "
						+ dhid
						+ "  ) as sl, (select count(distinct ctkmid) as ctkmid  from donhang_ctkm_trakm a"
						+ " where donhangid  = " + dhid + "  ) as sl2 ";
				rs = db.get(query);
				while (rs.next()) {
					String msg1 = "";
					if (rs.getDouble("sl") < rs.getDouble("sl2")) {
						msg1 = "vui lòng check lại khuyến mãi ";
					}
					if (msg1.length() > 0) {
						return msg1;
					}

				}
				rs.close();
				query = "\n select d.SANPHAM_FK from donhang_ctkm_dkkm a "
						+ "\n inner join DONHANG_CTKM_TRAKM b on a.donhang_fk = b.DONHANGID and a.ctkm_fk = b.CTKMID "
						+ "\n inner join donhang c on c.PK_SEQ = a.donhang_fk "
						+ "\n inner join sanpham e on e.ma = a.sanpham_fk "
						+ "\n inner join DIEUKIENKM_SANPHAM d on a.dkkm_fk = d.DIEUKIENKHUYENMAI_FK and e.PK_SEQ = d.SANPHAM_FK "
						+ "\n where d.sanpham_fk not in (select sanpham_fk from DONHANG_SANPHAM where donhang_fk = a.donhang_fk and donhang_fk ="
						+ dhid + "  ) and a.donhang_fk = " + dhid + " ";

				rs = db.get(query);
				System.out.println("Check don hang sản phẩm mua  " + query);
				if (rs != null) {
					while (rs.next()) {
						sodong = rs.getInt(1);
					}
					rs.close();
				}
				if (sodong > 0) {
					msg = "đơn hàng lỗi do k có sản phẩm mua trong điều kiện khuyến mãi mà có trả km !";
					return msg;
				}

			/*	query = "select sanpham_fk, soluong, b.npp_fk, b.kbh_fk, b.kho_fk, b.ngaynhap  "
						+ "  from donhang_sanpham a inner join donhang b"
						+ " on a.donhang_fk = b.pk_seq where b.pk_seq = '"+ dhid+ "'  "
						+ " union  all "
						+ "  select d.pk_seq, sum(a.soluong) soluong, e.npp_fk, e.kbh_fk, b.kho_fk, e.ngaynhap  "
						// +
						// " b.kho_fk as khoId, e.kbh_fk as kbhId, a.ctkmId, d.pk_seq as spId, "
						// + " sum(a.soluong) as soluong  "
						+ "from donhang_ctkm_trakm a "
						+ " inner join ctkhuyenmai b on a.ctkmid = b.pk_seq  "
						+ "inner join sanpham d on a.spMa = d.ma inner join donhang e on a.donhangId = e.pk_seq  "
						+ "where e.trangthai != 2 and a.spMa is not null "
						+ " and a.donhangId in ('"
						+ dhid
						+ "')  group by b.kho_fk, e.kbh_fk,   d.pk_seq  ,e.npp_fk,  e.ngaynhap   ";
				ResultSet rssp = db.get(query);
				System.out.println("______+______" + query);
				while (rssp.next()) {

					query = " select soluong ,(select ma from sanpham where pk_seq="
							+ rssp.getString("sanpham_fk")
							+ ") as ten from [ufn_XNT_TuNgay_DenNgay_V2018] "
							+ "("
							+ rssp.getString("npp_fk")
							+ ","
							+ rssp.getString("kbh_fk")
							+ ","
							+ rssp.getString("kho_fk")
							+ ","
							+ rssp.getString("sanpham_fk")
							+ ",'"
							+ rssp.getString("ngaynhap")
							+ "','"
							+ rssp.getString("ngaynhap")
							+ "')  a where a.soluong< 0 ";
					System.out.println("____+_________" + query);
					ResultSet rsck = db.get(query);
					String msg1 = "";
					if (rsck.next()) {

						msg1 = "Sản phẩm : "+ rsck.getString("ten")
								+ " Xuất nhập tồn tới thời điểm :"
								+ rssp.getString("ngaynhap")
								+ " chỉ còn :"+ ((rssp.getDouble("soluong") * -1) + rsck.getDouble("soluong")) + "";

					}
					rsck.close();
					if (msg1.length() > 0) {
						return msg1;
					}

				}
				
				
*/
				
				query = "\n select * from donhang_sanpham a  "+
						 "\n full outer join   "+
						 "\n ( select sanpham_fk, sum(soluong) as soluong from donhang_sanpham_chitiet where donhang_fk = '" + dhid+ "'  group by sanpham_fk ) b   "+
						 "\n on a.sanpham_fk = b.sanpham_fk   "+
						 "\n where a.donhang_fk ='" + dhid	+ "'  and a.soluong <> isnull(b.soluong,0)  ";  
				ResultSet rschk = db.get(query);
				if (rschk != null) {
					while (rschk.next()) {
						msg += "Lỗi:Số lượng kho tổng lệch với kho chi tiết trên đơn hàng!";
					}
					rschk.close();
				}
				if (msg.trim().length() > 0) {
					return msg;
				}
				
				
				query = "\n select a.npp_fk, a.sanpham_fk, a.soluong, a.booked, a.available, b.soluong, b.booked, b.available from NHAPP_KHO a "+
						"\n full outer join (select sanpham_fk, kho_fk, npp_fk, kbh_fk, sum(soluong) as soluong, sum(booked) as booked, sum(available) as available  "+
						"\n from NHAPP_KHO_CHITIET b group by sanpham_fk, kho_fk, npp_fk, kbh_fk ) b on a.sanpham_fk = b.sanpham_fk "+
						"\n and a.npp_fk = b.npp_fk and a.kho_fk = b.kho_fk and a.kbh_fk = b.kbh_fk  "+
						"\n where (a.soluong <> b.soluong or a.booked <> b.booked or a.available <> b.available) and a.npp_fk = '"+nppId+"' ";  
				ResultSet rschkkho = db.get(query);
				if (rschkkho != null) {
					while (rschkkho.next()) {
						msg += "Lỗi:Số lượng sản phẩm kho tổng lệch với kho chi tiết!";
					}
					rschkkho.close();
				}
				if (msg.trim().length() > 0) {
					return msg;
				}
				
				query = "update donhang set trangthai='1',FlagModified='1',chuyen ='1',NGAYCHOT = dbo.GetLocalDate(DEFAULT),NGUOICHOT = "
						+ (userid.length() <= 0 ? null : userid)
						+ ""
						+ " where pk_seq = '"+ dhid	+ "' and trangthai <> 1 "
						+ " and exists (select 1 from phieuxuatkho_donhang a inner join phieuxuatkho b on a.pxk_fk = b.pk_seq where a.donhang_fk = donhang.pk_seq and b.trangthai = '1' )";
				if (db.updateReturnInt(query) <= 0) {
					msg = "Không chốt được đơn hàng " + dhid+ ". Vì chưa có phiếu xuất kho";
					return msg;
				}
				
				/* String query = dhid + "&" + nppId + "&" + userid + "&" + ngayksgn +
				  "&" + TangNgayKs(ngayksgn); String param[] =
				  query.split("&"); int kq = db.execProceduce("Chot_Don_Hang",
				  param); if (kq == -1) return "Không thể chốt đơn hàng !...";*/
				 

			} catch (Exception e) {
				msg = "Lỗi không chốt được đơn hàng : " + e.toString();
				return msg;
			}
		}
		return "";
	}

	private void createDonHangPdf(Document document, String id, dbutils db,
			String userId) {
		try {
			IDonhangsam dhBean;
			dhBean = new Donhangsam(id);
			dhBean.setUserId(userId); // phai co UserId truoc khi Init
			dhBean.init();
			dhBean.setKhId(dhBean.getKhId());

			// dhBean.CreateSpDisplay();

			List<ISanpham> spList = (List<ISanpham>) dhBean.getSpList();
			List<ISanpham> spkmlist = (List<ISanpham>) dhBean
					.getScheme_SpList();
			Hashtable<String, Float> scheme_tongtien = dhBean
					.getScheme_Tongtien();
			Hashtable<String, Float> scheme_chietkhau = dhBean
					.getScheme_Chietkhau();

			System.out.println("----So san pham: " + spList.size());

			String tennpp = "";
			String diachi = "";
			String dienthoai = "";
			String masothue = "";

			String sql_getinfodis = "select ten,isnull(diachi,'') as diachi, isnull( masothue, '') as masothue,"
					+ "isnull( dienthoai,'') as dienthoai from nhaphanphoi where pk_seq='"
					+ dhBean.getNppId() + "'";

			ResultSet rs = db.get(sql_getinfodis);
			try {
				if (rs.next()) {
					tennpp = rs.getString("ten");
					diachi = rs.getString("diachi");
					dienthoai = rs.getString("dienthoai");
					masothue = rs.getString("masothue");
				}
				rs.close();
			} catch (Exception er) {
			}

			NumberFormat formatter = new DecimalFormat("#,###,###");
			NumberFormat formatter1 = new DecimalFormat("#,###,###");

			// chi dinh BaseFont.IDENTITY_H de co the go tieng viet
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf",
					BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 15, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			Font fontnomar = new Font(bf, 10, Font.NORMAL);
			// font2.setColor(BaseColor.GREEN);
			// KHAI BAO 1 BANG CO BAO NHIEU COT

			PdfPTable tableheader = new PdfPTable(2);
			tableheader.setWidthPercentage(90);// chieu dai cua báº£ng
			tableheader.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			float[] withsheader = { 35.0f, 65.0f };// SET DO RONG CAC COT
			tableheader.setWidths(withsheader);

			Image hinhanh = Image.getInstance(getServletContext()
					.getInitParameter("path") + "/images/logosgp.png");
			hinhanh.setAlignment(Element.ALIGN_LEFT);

			PdfPCell cellslogo = new PdfPCell(hinhanh);

			System.out.println("PATH: "
					+ getServletContext().getInitParameter("path")
					+ "/images/logosgp.png");
			// hinhanh.setAbsolutePosition(1.0f * CONVERT,
			// document.getPageSize().getHeight() - 2.0f * CONVERT);

			cellslogo.setPadding(2);
			cellslogo.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			cellslogo.setVerticalAlignment(Element.ALIGN_MIDDLE);

			tableheader.addCell(cellslogo);

			/*
			 * cellslogo.setPaddingTop(1); cellslogo.setFixedHeight(78.4f);
			 * cellslogo.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * 
			 * tableheader.addCell(cellslogo);
			 */

			Paragraph pxk = new Paragraph("PHIẾU GIAO HÀNG", font);
			pxk.setSpacingAfter(2);
			pxk.setAlignment(Element.ALIGN_CENTER);
			PdfPCell celltieude = new PdfPCell();
			celltieude.addElement(pxk);

			Paragraph dvbh = new Paragraph("Nhà phân phối: " + tennpp,
					fontnomar);
			dvbh.setSpacingAfter(2);
			dvbh.setAlignment(Element.ALIGN_LEFT);
			celltieude.addElement(dvbh);

			dvbh = new Paragraph("ĐT: " + dienthoai + "- ĐC: " + diachi,
					fontnomar);
			dvbh.setSpacingAfter(1);
			dvbh.setAlignment(Element.ALIGN_LEFT);
			celltieude.addElement(dvbh);

			/*
			 * dvbh = new Paragraph("Địa chỉ: " + diachi, fontnomar);
			 * dvbh.setSpacingAfter(3); dvbh.setAlignment(Element.ALIGN_LEFT);
			 * celltieude.addElement(dvbh);
			 */

			// tableheader.addCell(celltieude);

			/* PdfPCell cellinfo = new PdfPCell(); */
			String ngay = dhBean.getNgaygiaodich().substring(8, 10) + "-"
					+ dhBean.getNgaygiaodich().substring(5, 7) + "-"
					+ dhBean.getNgaygiaodich().substring(0, 4);
			celltieude.addElement(new Paragraph("Số chứng từ    : "
					+ dhBean.getId() + " - Ngày chứng từ: " + ngay, fontnomar));

			tableheader.addCell(celltieude);

			/*
			 * PdfPCell cellinfo = new PdfPCell();
			 * 
			 * cellinfo.addElement(new Paragraph("Số chứng từ    : " +
			 * dhBean.getId(),fontnomar));
			 * 
			 * String ngay = dhBean.getNgaygiaodich().substring(8,10)+ "-"
			 * +dhBean
			 * .getNgaygiaodich().substring(5,7)+"-"+dhBean.getNgaygiaodich
			 * ().substring(0,4); cellinfo.addElement(new
			 * Paragraph("Ngày chứng từ: " + ngay,fontnomar));
			 */
			// tableheader.addCell(cellinfo);

			// Add bang vao document
			document.add(tableheader);

			PdfPTable table_info = new PdfPTable(1);
			float[] with3 = { 50.0f };// SET DO RONG CAC COT
			table_info.setWidthPercentage(90);
			table_info.setWidths(with3);
			PdfPCell cell111 = new PdfPCell();

			String tenkh = "";
			String diachikh = "";
			String dienthoaikh = "";
			String masothuekh = "";
			String quanhuyen = "";
			String tinhthanh = "";
			String phuongxa = "";
			String sql_getinfokh = "select khachhang.ten,isnull(khachhang.dienthoai,'') as dienthoai,isnull(khachhang.diachi,'') as diachi,isnull(q.TEN,'')as quan,isnull(t.TEN,'')as tinhthanh,isnull(px.ten,'')as phuongxa,isnull(khachhang.masothue,'') as masothue "
					+ "from khachhang left join QUANHUYEN q ON q.pk_seq=khachhang.quanhuyen_fk left join TINHTHANH t on t.pk_seq=khachhang.tinhthanh_fk left join phuongxa px on px.Pk_seq = khachhang.phuongxa_fk where khachhang.pk_seq="
					+ dhBean.getKhId();
			System.out.println(sql_getinfokh);
			ResultSet rs_kh = db.get(sql_getinfokh);
			try {
				if (rs_kh.next()) {
					tenkh = rs_kh.getString("ten");
					diachikh = rs_kh.getString("diachi");
					dienthoaikh = rs_kh.getString("dienthoai");
					masothuekh = rs_kh.getString("masothue");
					quanhuyen = rs_kh.getString("quan");
					tinhthanh = rs_kh.getString("tinhthanh");
					phuongxa = rs_kh.getString("phuongxa");
				}
				rs_kh.close();
			} catch (Exception er) {
				er.printStackTrace();
				System.out.println("Loi Khach Hang: " + er.toString());
			}
			// lay dai dien kinh doanh
			String ddkdten = "";
			String dienthoaiDDKD = "";
			String sql_getddkd = "select pk_seq, ten, isnull(dienthoai, '') as dienthoai "
					+ "from daidienkinhdoanh where pk_seq="
					+ dhBean.getDdkdId();
			ResultSet rs_getddkd = db.get(sql_getddkd);
			System.out.println(sql_getddkd);
			try {
				if (rs_getddkd.next()) {
					ddkdten = rs_getddkd.getString("ten");
					dienthoaiDDKD = rs_getddkd.getString("dienthoai");
				}
				rs_getddkd.close();
			} catch (Exception er) {
				System.out.println("Loi DDkD : " + er.toString());
			}

			String ddkd = "Đại diện kinh doanh: " + ddkdten;
			if (dienthoaiDDKD.trim().equals("NA"))
				dienthoaiDDKD = "";
			if (dienthoaiDDKD.trim().equals("N/A"))
				dienthoaiDDKD = "";
			if (dienthoaiDDKD.length() > 0)
				ddkd += " (ĐT:" + dienthoaiDDKD + ")";

			cell111.addElement(new Paragraph(ddkd, fontnomar));

			String nocu = "";
			if (dhBean.getNoCu() == 0)
				nocu = "";
			else
				nocu = formatter1.format(dhBean.getNoCu());
			String dd_khachhang = "Mã khách hàng: " + dhBean.getSmartId()
					+ "     Tên khách hàng : " + tenkh;
			if (dienthoaikh.length() > 0)
				dd_khachhang += " (" + dienthoaikh + ")";

			cell111.addElement(new Paragraph(dd_khachhang, fontnomar));
			if (phuongxa.length() != 0)
				phuongxa = ", phường " + phuongxa;
			if (quanhuyen.length() != 0)
				quanhuyen = ", quận " + quanhuyen;
			if (tinhthanh.length() != 0)
				tinhthanh = ", Tỉnh/Tp " + tinhthanh;
			cell111.addElement(new Paragraph("Địa chỉ: " + diachikh + phuongxa
					+ quanhuyen + tinhthanh, fontnomar));
			cell111.setPaddingBottom(8);
			table_info.addCell(cell111);

			document.add(table_info);
			// Table Content
			PdfPTable table = new PdfPTable(8);// Chu y nhe 6 cot o day, thi
												// xuong duoi nho set withs la 6
												// cot
			table.setWidthPercentage(90);// chieu dai cua báº£ng
			table.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			float[] withs = { 5.0f, 30.0f, 8.0f, 8.0f, 10.0f, 5.0f, 0.0f, 12.0f };
			table.setWidths(withs);
			String[] th = new String[] { "STT", "Tên hàng", "Số lượng", "ĐVT",
					"Đơn giá", "%CK", "", "Thành tiền" };
			PdfPCell[] cell = new PdfPCell[8];
			for (int i = 0; i < 8; i++) {
				cell[i] = new PdfPCell(new Paragraph(th[i], fontnomar));
				cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				// cell[i].setBackgroundColor(BaseColor.LIGHT_GRAY);
				table.addCell(cell[i]);
			}
			float size = 8.5f;
			Font font4 = new Font(bf, size);
			PdfPCell cells_detail = new PdfPCell();
			double totalle = 0;

			String query = "	select d.ten as khoTen,c.donvi as spDonVi, b.ma as spMa,b.ten as spTen,a.SoLuong,a.GiaMua,a.DonGiaGoc, isnull(ptCKDH,0) as ckDH  "
					+ "	,isnull(ptCKTT,0) as ckTT,isnull(ptCKDLN,0) as ckDLN,isnull(ptCKTT,0)+isnull(ptCKDH,0)+isnull(ptCKDLN,0) as TongCK, (select isnull(chietkhau,0) from donhang where pk_seq = a.donhang_fk )as chietkhau ,(select isnull(Sotiengiam,0) from donhang where pk_seq = a.donhang_fk )as sotiengiam  "
					+ " from DonHang_SanPham a inner join SanPham b on b.pk_Seq=a.sanpham_fk  "
					+ "   inner join DonViDoLuong c on c.pk_Seq=b.DVDL_FK  "
					+ "  INNER JOIN Kho d on d.pk_Seq=a.Kho_FK  "
					+ " where a.donhang_fk='" + dhBean.getId() + "'  ";

			rs = db.get(query);
			int i = 0;
			double totalTienCK = 0;
			double sotiengiam = 0;
			double ckdh = 0;
			try {
				while (rs.next()) {
					i++;
					// String spMa = rs.getString("spMa");
					String spTen = rs.getString("spTen");
					String spDonVi = rs.getString("spDonVi");
					// String khoTen = rs.getString("khoTen");
					double SoLuong = rs.getDouble("SoLuong");
					double GiaSauCK = rs.getDouble("GiaMua");
					double DonGiaGoc = rs.getDouble("DonGiaGoc");
					double totalCK = rs.getDouble("TongCK");
					double thanhTien = SoLuong * (DonGiaGoc);
					double tienSauCK = SoLuong
							* (DonGiaGoc * (1 - totalCK / 100));
					double tienCK = thanhTien - tienSauCK;
					totalTienCK += tienCK;
					ckdh = rs.getDouble("chietkhau");
					sotiengiam = rs.getDouble("sotiengiam");

					totalle += thanhTien;
					String[] arr = new String[] {
							Integer.toString(i), // spMa,
							spTen, formatter.format(SoLuong), spDonVi,
							formatter1.format(DonGiaGoc),
							formatter1.format(totalCK), "",
							formatter1.format(thanhTien) };
					for (int j = 0; j < 8; j++) {
						cells_detail = new PdfPCell(
								new Paragraph(arr[j], font4));
						if (j == 2) {
							cells_detail
									.setHorizontalAlignment(Element.ALIGN_LEFT);
						} else {
							cells_detail
									.setHorizontalAlignment(Element.ALIGN_CENTER);
						}
						cells_detail.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cells_detail);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			if (spkmlist.size() > 0) {
				PdfPCell cellkhuyenmai = new PdfPCell();
				cellkhuyenmai.setHorizontalAlignment(Element.ALIGN_CENTER);
				Paragraph km1 = new Paragraph("Hàng khuyến mại", fontnomar);
				km1.setAlignment(Element.ALIGN_LEFT);
				cellkhuyenmai.addElement(km1);
				cellkhuyenmai.setColspan(8);
				table.addCell(cellkhuyenmai);
			}
			// hang khuyen mai
			int demtt = 0;
			for (i = 0; i < spkmlist.size(); i++) {
				ISanpham sanpham = (ISanpham) spkmlist.get(i);
				double dongia = Double.parseDouble(sanpham.getDongia());

				double chietkhau = Double.parseDouble(sanpham.getSoluong())
						* Double.parseDouble(sanpham.getDongia()) / 100
						* Double.parseDouble(sanpham.getChietkhau());
				String sql_getkho = "select kho_fk,kho.ten  from ctkhuyenmai inner join kho on  kho.pk_Seq=kho_fk  where scheme ='"
						+ sanpham.getCTKM() + "'";
				rs = db.get(sql_getkho);
				String TenKho = "Khuyến mại";
				try {
					if (rs.next()) {
						TenKho = rs.getString("ten");
					}
					rs.close();
				} catch (Exception er) {
					System.out.println("Loi tai day :" + er.toString());
				}
				demtt = demtt + 1;
				String[] arr = new String[] {
						"" + demtt,
						// sanpham.getMasanpham(),
						sanpham.getTensanpham(),
						// TenKho,
						sanpham.getSoluong(),
						sanpham.getDonvitinh(),
						formatter1.format(dongia),
						formatter1.format(Double.parseDouble(sanpham
								.getChietkhau())),
						formatter.format(chietkhau),
						formatter.format(Double.parseDouble(sanpham
								.getSoluong())
								* Double.parseDouble(sanpham.getDongia())
								- chietkhau) };

				for (int j = 0; j < 8; j++) {
					cells_detail = new PdfPCell(new Paragraph(arr[j], font4));
					if (j == 2) {
						cells_detail.setHorizontalAlignment(Element.ALIGN_LEFT);
					} else {
						cells_detail
								.setHorizontalAlignment(Element.ALIGN_CENTER);
					}

					cells_detail.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cells_detail);
				}
			}
			// Khai bao 1 bien luu tien khuyen mai
			float tongtienkhuyenmai = 0;
			// Xuat ra khuyen mai tra tien
			if (scheme_tongtien.size() > 0) {
				Enumeration<String> keys = scheme_tongtien.keys();
				while (keys.hasMoreElements()) {
					String key = keys.nextElement();
					tongtienkhuyenmai = tongtienkhuyenmai
							+ scheme_tongtien.get(key);
					String tongtien = Float.toString(scheme_tongtien.get(key));
					String sql_tenschem = "select diengiai from ctkhuyenmai where scheme='"
							+ key + "'";
					rs = db.get(sql_tenschem);
					String Tenschem = "";
					try {
						if (rs.next()) {
							Tenschem = rs.getString("diengiai");
						}
						rs.close();
					} catch (Exception er) {
						System.out.println("Loi tai day :" + er.toString());
					}
					demtt = demtt + 1;
					String[] arr = new String[] { "" + demtt// , key
					, Tenschem, "", "", "", "", "", "-" + tongtien };
					for (int j = 0; j < 8; j++) {
						cells_detail = new PdfPCell(
								new Paragraph(arr[j], font4));
						if (j == 2) {
							cells_detail
									.setHorizontalAlignment(Element.ALIGN_LEFT);
						} else {
							cells_detail
									.setHorizontalAlignment(Element.ALIGN_CENTER);
						}

						cells_detail.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cells_detail);

					}
				}

			}

			if (scheme_chietkhau.size() > 0) {

				Enumeration<String> keys = scheme_chietkhau.keys();
				while (keys.hasMoreElements()) {
					String key = keys.nextElement();
					String sql_tenschem = "select diengiai from ctkhuyenmai where scheme='"
							+ key + "'";
					rs = db.get(sql_tenschem);
					String Tenschem = "";
					try {
						if (rs.next()) {
							Tenschem = rs.getString("diengiai");
						}
						rs.close();
					} catch (Exception er) {
						System.out.println("Loi tai day :" + er.toString());
					}

					tongtienkhuyenmai = tongtienkhuyenmai
							+ scheme_chietkhau.get(key);
					String tienchietkhau = Float.toString(scheme_chietkhau
							.get(key));
					demtt = demtt + 1;
					String[] arr = new String[] { "" + demtt// , key
					, Tenschem, "", "", "", "", "-" + tienchietkhau, "" };
					for (int j = 0; j < 8; j++) {
						cells_detail = new PdfPCell(
								new Paragraph(arr[j], font4));
						if (j == 2) {
							cells_detail
									.setHorizontalAlignment(Element.ALIGN_LEFT);
						} else {
							cells_detail
									.setHorizontalAlignment(Element.ALIGN_CENTER);
						}
						cells_detail.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cells_detail);

					}
				}

			}

			PdfPCell cell11 = new PdfPCell();
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			Paragraph km = new Paragraph("Cộng tiền hàng ", fontnomar);
			km.setAlignment(Element.ALIGN_RIGHT);
			cell11.addElement(km);
			cell11.setColspan(7);
			table.addCell(cell11);

			PdfPCell cell12 = new PdfPCell();
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			Paragraph tongtien = new Paragraph(formatter1.format(totalle),
					fontnomar);
			tongtien.setAlignment(Element.ALIGN_RIGHT);
			cell12.addElement(tongtien);
			table.addCell(cell12);

			// Cộng thêm chiết khấu tổng đơn hàng
			totalTienCK += (totalle * (ckdh / 100.0));
			if (totalTienCK > 0) {

				cell11 = new PdfPCell();
				cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
				km = new Paragraph("Tiền chiết khấu ", fontnomar);
				km.setAlignment(Element.ALIGN_RIGHT);
				cell11.addElement(km);
				cell11.setColspan(7);
				table.addCell(cell11);

				cell12 = new PdfPCell();
				cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
				tongtien = new Paragraph(formatter1.format(totalTienCK),
						fontnomar);
				tongtien.setAlignment(Element.ALIGN_RIGHT);
				cell12.addElement(tongtien);
				table.addCell(cell12);

			}

			if (sotiengiam > 0) {

				cell11 = new PdfPCell();
				cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
				km = new Paragraph("Số tiền giảm ", fontnomar);
				km.setAlignment(Element.ALIGN_RIGHT);
				cell11.addElement(km);
				cell11.setColspan(7);
				table.addCell(cell11);

				cell12 = new PdfPCell();
				cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
				tongtien = new Paragraph(formatter1.format(sotiengiam),
						fontnomar);
				tongtien.setAlignment(Element.ALIGN_RIGHT);
				cell12.addElement(tongtien);
				table.addCell(cell12);

			}

			if (tongtienkhuyenmai > 0) {
				cell11 = new PdfPCell();
				cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
				km = new Paragraph("Tổng số tiền chiết khấu khuyến mại ",
						fontnomar);
				km.setAlignment(Element.ALIGN_RIGHT);
				cell11.addElement(km);
				cell11.setColspan(7);
				table.addCell(cell11);

				cell12 = new PdfPCell();
				cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
				tongtien = new Paragraph(formatter1.format(tongtienkhuyenmai),
						fontnomar);
				tongtien.setAlignment(Element.ALIGN_RIGHT);
				cell12.addElement(tongtien);
				table.addCell(cell12);

			}

			//
			cell11 = new PdfPCell();
			cell11.setHorizontalAlignment(Element.ALIGN_RIGHT);
			km = new Paragraph("Tổng tiền thanh toán(đã làm tròn) ", fontnomar);
			km.setAlignment(Element.ALIGN_RIGHT);
			cell11.addElement(km);
			cell11.setColspan(7);
			table.addCell(cell11);

			//
			// double tongtiensauthue = totalle / 100 * 10 + totalle;
			double tongtiensauthue = totalle;
			cell12 = new PdfPCell();
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			tongtien = new Paragraph(formatter.format(tongtiensauthue
					- tongtienkhuyenmai - totalTienCK), fontnomar);
			tongtien.setAlignment(Element.ALIGN_RIGHT);
			cell12.addElement(tongtien);
			table.addCell(cell12);

			// doc tien bang chu
			doctienrachu doctien = new doctienrachu();
			cell11 = new PdfPCell();
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			Long tongtienst = Math.round(tongtiensauthue - tongtienkhuyenmai
					- totalTienCK);
			System.out.println("[tongtiensauthue]" + tongtiensauthue
					+ "[tongtienkhuyenmai]" + tongtienkhuyenmai);
			km = new Paragraph("Số tiền bằng chữ : "
					+ doctien.tranlate(tongtienst + ""), fontnomar);
			km.setAlignment(Element.ALIGN_CENTER);
			cell11.addElement(km);
			cell11.setColspan(7);
			table.addCell(cell11);

			document.add(table);

			PdfPTable tablefooter = new PdfPTable(5);
			tablefooter.setWidthPercentage(90);// chieu dai cua báº£ng
			tablefooter.setHorizontalAlignment(Element.ALIGN_CENTER);
			float[] withfooterder = { 30.0f, 30.0f, 30.0f, 30.0f, 30.0f };// SET
																			// DO
																			// RONG
																			// CAC
																			// COT
			tablefooter.setWidths(withfooterder);

			// nguoimua hang
			Paragraph para = new Paragraph("Khách hàng", fontnomar);

			para.setAlignment(Element.ALIGN_CENTER);
			cell11.addElement(para);
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell11.setBorderWidthRight(0);
			tablefooter.addCell(para);

			// nvgn
			para = new Paragraph("Giao nhận", fontnomar);
			para.setAlignment(Element.ALIGN_CENTER);
			cell11.addElement(para);
			cell11.setBorderWidthLeft(0);
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell11.setBorderWidthRight(0);
			tablefooter.addCell(para);

			// o giua
			para = new Paragraph("Thủ kho", fontnomar);
			para.setAlignment(Element.ALIGN_CENTER);
			cell11.addElement(para);
			cell11.setBorderWidthLeft(0);
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell11.setBorderWidthRight(0);
			tablefooter.addCell(para);

			// Káº¿ toÃ¡n
			para = new Paragraph("Kế toán", fontnomar);
			para.setAlignment(Element.ALIGN_CENTER);
			cell11.addElement(para);
			cell11.setBorderWidthLeft(0);
			cell11.setBorderWidthRight(0);
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablefooter.addCell(para);

			// nguoi nhan hang
			para = new Paragraph("Người lập phiếu", fontnomar);
			para.add("\n");
			para.add("\n");
			para.add("\n");
			para.add("\n");
			/*
			 * para.add("\n"); para.add("\n"); para.add("\n");
			 */

			para.setAlignment(Element.ALIGN_CENTER);
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell11.addElement(para);
			cell11.setBorderWidthLeft(0);
			cell11.setBorder(0);

			tablefooter.addCell(para);

			document.add(tablefooter);

			document.newPage();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("115.Exception: " + e.getMessage());
		}

	}

	// ham delete co phat sinh don hang thu hoi
	private boolean Delete2(String dhId, String userId, String nppId) {
		// neu da xuat kho ma xoa, thi phai tao ra phieu thu hoi
		try {
			dbutils db = new dbutils();
			IDonhangsam donhang = new Donhangsam(dhId);
			donhang.setUserId(userId);
			donhang.setNppId(nppId);

			donhang.DeleteDonHangDxk();
			FixData fixed = new FixData();
			fixed.ProcessBOOKED(nppId, "", db);
			return true;

		} catch (Exception e) {
			return false;
		}
	}

	private String getDateTimeTEST() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public String createxuatkho_ALL(String dhid, String userid, String nppId)
	{

		dbutils db = new dbutils();
		String ngaytao = getDateTime();
		String nguoitao = userid;
		String msg = "";
		ResultSet rsPxk = null;
		try {
			db.getConnection().setAutoCommit(false);
			String query=" update donhang  set NGAYCHOT = dbo.GetLocalDate(DEFAULT),trangthaichot=1 "+
					  "		where pk_seq = "+dhid+" and trangthai =0 and isnull(trangthaichot,0) =0  ";
				if(db.updateReturnInt(query)<=0)
				{
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					return "Đơn hàng đang xử lý";
				}
			String sql = "";
			 query = "select ngaynhap,trangthai,kho_FK,kbh_FK from donhang where pk_seq ="+ dhid;
			ResultSet rs = db.get(query);
			rs.next();
			String ngaydonhang = rs.getString("Ngaynhap");
			String trangthai = rs.getString("trangthai");
			String kho = rs.getString("kho_fk");
			String kenh = rs.getString("kbh_FK");
			rs.close();
		
			query = "select max(ngayks) as ngayks from khoasongay where npp_fk = '"
					+ nppId + "' ";
			String ngayks = "";
			rs = db.get(query);
			if (rs != null) {
				if (rs.next())
					ngayks = rs.getString("ngayks") == null ? "" : rs.getString("ngayks");
				rs.close();
			} else {
				msg += "NPP chưa có ngày khóa sổ. Không chốt được đơn hàng! \n";
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				db.shutDown();
				return msg;
			}
			if (ngayks.length() <= 0) {
				msg += "NPP chưa có ngày khóa sổ. Không chốt được đơn hàng! \n";
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				db.shutDown();
				return msg;
			}
			if (ngayks.compareTo(ngaydonhang) >= 0) {
				msg += "Đơn hàng số "+ dhid+ " không được phép chốt trước ngày khóa sổ.Vui lòng bỏ chọn \n";
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				db.shutDown();
				return msg;
			}

			if (getDateTime().compareTo(ngaydonhang) >= 0) {
				msg += "Đơn hàng số " + dhid+ " không được phép chốt trước ngày đơn hàng \n";
			}
			
			query = "select count(*) as dhth from  PHIEUTHUHOI a inner join PHIEUTHUHOI_DONHANG "
					+ " b on a.PK_SEQ = b.pth_fk where b.donhang_fk = '"+ dhid+ "' and a.trangthai = '0' ";
			rs = db.get(query);
			if (rs != null) {
				if (rs.next()) {
					if (rs.getInt("dhth") > 0) {
						msg += "Đơn hàng số "+ dhid+ " không được phép chốt,Vui lòng chốt phiếu thu hồi trước khi chốt đơn hàng !\n";
						db.getConnection().rollback();
						db.getConnection().setAutoCommit(true);
						db.shutDown();
						return msg;
					}
				}
				rs.close();
			}			
			query = "select TRANGTHAI FROM DONHANG where pk_seq='" + dhid+ "' ";
			ResultSet rsCheck = db.get(query);
			rsCheck.next();
			if (rsCheck.getInt("TRANGTHAI") == 1|| rsCheck.getInt("TRANGTHAI") == 3) {
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				db.shutDown();
				return "Không thể chốt đơn hàng này: phiếu này đã được chốt hoặc trạng thái không hợp lệ! ";
			}
			rsCheck.close();

			// Booked cho đơn PDA

			if (!trangthai.equals("2") && !trangthai.equals("1")&& !trangthai.equals("3") && !trangthai.equals("9")) {
				query = "select DonHang_fk,pxk_Fk from phieuxuatkho_donhang a inner join phieuxuatkho b on a.pxk_FK = b.pk_seq  where donhang_fk = "
						+ dhid + " and b.trangthai <> 2 ";
				System.out.println("Check PXK: " + query);
				rs = db.get(query);
				while (rs.next()) {
					msg += "Đơn hàng số " + rs.getString("donhang_fk")+ " đã tồn tại trong phiếu xuất kho "+ rs.getString("pxk_fk")+ " vui lòng bỏ chọn ... \n";
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					db.shutDown();
					return msg;

				}
				rs.close();	
				
				// check cac dieu kien
				

				query = "\n delete a from donhang_ctkm_dkkm a "
						+ "\n inner join sanpham b on a.sanpham_fk = b.MA "
						+ "\n inner join DONHANG_SANPHAM c on a.donhang_fk = c.DONHANG_FK and c.SANPHAM_FK = b.PK_SEQ "
						+ "\n where not exists (select 1 from DONHANG_CTKM_TRAKM d where a.ctkm_fk = d.CTKMID and DONHANGID = c.DONHANG_FK ) "
						+ "\n and c.donhang_fk ='" + dhid + "' ";

				System.out.println(query);
				if (!db.update(query)) {
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					db.shutDown();
					msg = "Lỗi cập nhật đơn hàng, donhang_ctkm_dkkm";
					return msg;
				}

				String msg1 = "";
				query = "select (select count(distinct ctkm_fk) as ctkmid  from donhang_ctkm_dkkm a"
						+ " where donhang_fk  = "
						+ dhid
						+ "  ) as sl, (select count(distinct ctkmid) as ctkmid  from donhang_ctkm_trakm a"
						+ " where donhangid  = " + dhid + "  ) as sl2 ";
				rs = db.get(query);
				while (rs.next()) {
					if (rs.getDouble("sl") < rs.getDouble("sl2")) {
						msg1 = "vui lòng check lại khuyến mãi ";
					}
				}
				rs.close();
				if (msg1.length() > 0) {
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					db.shutDown();
					return msg1;
				}

				query = "\n select d.SANPHAM_FK from donhang_ctkm_dkkm a "
						+ "\n inner join DONHANG_CTKM_TRAKM b on a.donhang_fk = b.DONHANGID and a.ctkm_fk = b.CTKMID "
						+ "\n inner join donhang c on c.PK_SEQ = a.donhang_fk "
						+ "\n inner join sanpham e on e.ma = a.sanpham_fk "
						+ "\n inner join DIEUKIENKM_SANPHAM d on a.dkkm_fk = d.DIEUKIENKHUYENMAI_FK and e.PK_SEQ = d.SANPHAM_FK "
						+ "\n where d.sanpham_fk not in (select sanpham_fk from DONHANG_SANPHAM where donhang_fk = a.donhang_fk and donhang_fk ="
						+ dhid + "  ) and a.donhang_fk = " + dhid + " ";

				rs = db.get(query);
				int sodong=0;
				System.out.println("Check don hang sản phẩm mua  " + query);
				if (rs != null) {
					while (rs.next()) {
						sodong = rs.getInt(1);
					}
					rs.close();
				}
				if (sodong > 0) {
					msg = "đơn hàng lỗi do k có sản phẩm mua trong điều kiện khuyến mãi mà có trả km !";
					return msg;
				}
				
				/// khuc nay tru kho lun 
				geso.dms.center.util.Utility util = new geso.dms.center.util.Utility();
				query="select c.ngaynhap,c.npp_fk,c.kho_fk as khoId, c.kbh_fk as kbhId, b.pk_seq as spId, sum(a.soluong) as soluong from donhang_sanpham a "
						+ " inner join sanpham b on a.sanpham_fk = b.pk_seq "
						+ " inner join donhang c on a.donhang_fk = c.pk_seq  "
						+ " where c.trangthai = 0 and a.donhang_fk ="+dhid+" group by c.kho_fk, c.kbh_fk, b.pk_seq,c.ngaynhap,c.npp_fk";
				rs=db.get(query);
				while(rs.next())
				{
					String _kho_fk=rs.getString("khoId");
			    	String _kbh_fk=rs.getString("kbhId");
			    	String _sanpham_fk=rs.getString("spId");
			    	String _npp_fk=rs.getString("npp_fk");
			    	String _ngaynhap=rs.getString("ngaynhap");
			    	Double _soluong = rs.getDouble("soluong");
			    	msg=util.Update_NPP_Kho_Sp(_ngaynhap,dhid, "duyet don hang", db, _kho_fk, _sanpham_fk, _npp_fk, _kbh_fk,  _soluong*(-1.0),(-1)*_soluong , 0, 0.0);
			    	if(msg.length()>0)
			    	{
			    		db.getConnection().rollback();
						db.getConnection().setAutoCommit(true);
						db.shutDown();
			    		return msg;
			    	}	
				}
				
				query=	 " SELECT DH.NGAYNHAP,SP.SANPHAM_FK,DH.KHO_FK,DH.NPP_FK,DH.KBH_FK,SP.SOLUONG, a.ten as tensp, sp.ngaynhapkho " 
						+ "\n from DONHANG dh inner join DONHANG_SANPHAM_chitiet sp on dh.PK_SEQ = sp.DONHANG_FK    "
						+ "\n inner join sanpham a on a.pk_seq = sp.sanpham_fk  "
						+ "\n where dh.PK_SEQ = "+ dhid+ " and dh.trangthai=0";
						
					System.out.println("UPDATE NPP KHO: " + query  );
				  ResultSet  ckRs = db.get(query);
				    while(ckRs.next())
				    {
				    	String kho_fk=ckRs.getString("kho_fk");
						String _npp_fk=ckRs.getString("npp_fk");	
						String _kbh_fk =ckRs.getString("kbh_fk");
						String sanpham_fk=ckRs.getString("sanpham_fk");
						String ngaynhap  =ckRs.getString("ngaynhap");
						String tensp  =ckRs.getString("tensp");
						Double soluong = ckRs.getDouble("soluong");
				    	String ngaynhapkho=ckRs.getString("NGAYNHAPKHO");    	
							 msg1=util.Update_NPP_Kho_Sp_Chitiet(ngaynhap, "chot DHID: "+dhid ,  db, kho_fk, sanpham_fk, _npp_fk, _kbh_fk, ngaynhapkho, (-1)*soluong,(-1)*soluong, 0, 0, 0);
							if (msg1.length()> 0) 
							{
								db.getConnection().rollback();
								db.getConnection().setAutoCommit(true);
								db.shutDown();
					    		return msg1;
							}
				    }	
					
				  // hang khuyen mai
				    
				    query="	select c.ngaynhap,c.npp_fk,c.kho_fk as khoId, c.kbh_fk as kbhId, b.pk_seq as spId, sum(a.soluong) as soluong from donhang_ctkm_trakm a \n"+
				    	  "	 inner join sanpham b on a.SPMA = b.Ma \n"+
				    	  "	 inner join donhang c on a.DONHANGID = c.pk_seq   \n"+
				          "		 where c.trangthai = 0 and a.DONHANGID ="+dhid+" \n"+
				    	  "	  group by c.kho_fk, c.kbh_fk, b.pk_seq,c.ngaynhap,c.npp_fk";
					rs=db.get(query);
					while(rs.next())
					{
						String _kho_fk=rs.getString("khoId");
				    	String _kbh_fk=rs.getString("kbhId");
				    	String _sanpham_fk=rs.getString("spId");
				    	String _npp_fk=rs.getString("npp_fk");
				    	String _ngaynhap=rs.getString("ngaynhap");
				    	Double _soluong = rs.getDouble("soluong");
				    	msg=util.Update_NPP_Kho_Sp(_ngaynhap,dhid, "duyet don hang km ", db, _kho_fk, _sanpham_fk, _npp_fk, _kbh_fk,  _soluong*(-1.0),(-1)*_soluong , 0, 0.0);
				    	if(msg.length()>0)
				    	{
				    		db.getConnection().rollback();
							db.getConnection().setAutoCommit(true);
							db.shutDown();
				    		return msg;
				    	}	
					}
				    
					
					query=  " SELECT DH.NGAYNHAP,a.PK_SEQ as sanpham_fk,DH.KHO_FK,DH.NPP_FK,DH.KBH_FK,SP.SOLUONG, a.ten as tensp, sp.ngaynhapkho \n"+
							"  from DONHANG dh inner join DONHANG_CTKM_TRAKM_CHITIET sp on dh.PK_SEQ = sp.DONHANGID     \n"+
							" inner join sanpham a on a.MA = sp.SPMA \n"+
							" where dh.PK_SEQ = "+dhid+" and dh.trangthai=0\n";
							
						System.out.println("UPDATE NPP KHO: " + query  );
					    ckRs = db.get(query);
					    while(ckRs.next())
					    {
					    	String kho_fk=ckRs.getString("kho_fk");
							String _npp_fk=ckRs.getString("npp_fk");	
							String _kbh_fk =ckRs.getString("kbh_fk");
							String sanpham_fk=ckRs.getString("sanpham_fk");
							String ngaynhap  =ckRs.getString("ngaynhap");
							String tensp  =ckRs.getString("tensp");
							Double soluong = ckRs.getDouble("soluong");
					    	String ngaynhapkho=ckRs.getString("NGAYNHAPKHO");    	
								 msg1=util.Update_NPP_Kho_Sp_Chitiet(ngaynhap, "chot DHID: km  "+dhid ,  db, kho_fk, sanpham_fk, _npp_fk, _kbh_fk, ngaynhapkho, (-1)*soluong,(-1)*soluong, 0, 0, 0);
								if (msg1.length()> 0) 
								{
									db.getConnection().rollback();
									db.getConnection().setAutoCommit(true);
									db.shutDown();
						    		return msg1;
								}
					    }
					
					    ckRs.close();
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
				
				db.getConnection().setAutoCommit(false);
				String mssg=taoxuatkho_chot(db, dhid, ngaytao, nguoitao, nppId);
				if(mssg.trim().length()>0)
				{
					query="select c.ngaynhap,c.npp_fk,c.kho_fk as khoId, c.kbh_fk as kbhId, b.pk_seq as spId, sum(a.soluong) as soluong from donhang_sanpham a "
							+ " inner join sanpham b on a.sanpham_fk = b.pk_seq "
							+ " inner join donhang c on a.donhang_fk = c.pk_seq  "
							+ " where c.trangthai = 0 and a.donhang_fk ="+dhid+" group by c.kho_fk, c.kbh_fk, b.pk_seq,c.ngaynhap,c.npp_fk";
					rs=db.get(query);
					while(rs.next())
					{
						String _kho_fk=rs.getString("khoId");
				    	String _kbh_fk=rs.getString("kbhId");
				    	String _sanpham_fk=rs.getString("spId");
				    	String _npp_fk=rs.getString("npp_fk");
				    	String _ngaynhap=rs.getString("ngaynhap");
				    	Double _soluong = rs.getDouble("soluong");
				    	msg=util.Update_NPP_Kho_Sp(_ngaynhap,dhid, "duyet don hang", db, _kho_fk, _sanpham_fk, _npp_fk, _kbh_fk,  _soluong*(1.0),(1)*_soluong , 0, 0.0);
				    	if (msg.length()> 0) 
						{
							db.getConnection().rollback();
							db.getConnection().setAutoCommit(true);
							db.shutDown();
				    		return msg1;
						}
					}
					
					query=	 " SELECT DH.NGAYNHAP,SP.SANPHAM_FK,DH.KHO_FK,DH.NPP_FK,DH.KBH_FK,SP.SOLUONG, a.ten as tensp, sp.ngaynhapkho " 
							+ "\n from DONHANG dh inner join DONHANG_SANPHAM_chitiet sp on dh.PK_SEQ = sp.DONHANG_FK    "
							+ "\n inner join sanpham a on a.pk_seq = sp.sanpham_fk  "
							+ "\n where dh.PK_SEQ = "+ dhid+ " and dh.trangthai=0";
							
						System.out.println("UPDATE NPP KHO: " + query  );
					    ckRs = db.get(query);
					    while(ckRs.next())
					    {
					    	String kho_fk=ckRs.getString("kho_fk");
							String _npp_fk=ckRs.getString("npp_fk");	
							String _kbh_fk =ckRs.getString("kbh_fk");
							String sanpham_fk=ckRs.getString("sanpham_fk");
							String ngaynhap  =ckRs.getString("ngaynhap");
							String tensp  =ckRs.getString("tensp");
							Double soluong = ckRs.getDouble("soluong");
					    	String ngaynhapkho=ckRs.getString("NGAYNHAPKHO");    	
								 msg1=util.Update_NPP_Kho_Sp_Chitiet(ngaynhap, "chot DHID: "+dhid ,  db, kho_fk, sanpham_fk, _npp_fk, _kbh_fk, ngaynhapkho, (1)*soluong,(1)*soluong, 0, 0, 0);
								if (msg1.length()> 0) 
								{
									db.getConnection().rollback();
									db.getConnection().setAutoCommit(true);
									db.shutDown();
						    		return msg1;
								}
					    }	
					
					    
					    
					    // hang khuyen mai
					    
					    query="	select c.ngaynhap,c.npp_fk,c.kho_fk as khoId, c.kbh_fk as kbhId, b.pk_seq as spId, sum(a.soluong) as soluong from donhang_ctkm_trakm a \n"+
					    	  "	 inner join sanpham b on a.SPMA = b.Ma \n"+
					    	  "	 inner join donhang c on a.DONHANGID = c.pk_seq   \n"+
					          "		 where c.trangthai = 0 and a.DONHANGID ="+dhid+" \n"+
					    	  "	  group by c.kho_fk, c.kbh_fk, b.pk_seq,c.ngaynhap,c.npp_fk";
						rs=db.get(query);
						while(rs.next())
						{
							String _kho_fk=rs.getString("khoId");
					    	String _kbh_fk=rs.getString("kbhId");
					    	String _sanpham_fk=rs.getString("spId");
					    	String _npp_fk=rs.getString("npp_fk");
					    	String _ngaynhap=rs.getString("ngaynhap");
					    	Double _soluong = rs.getDouble("soluong");
					    	msg=util.Update_NPP_Kho_Sp(_ngaynhap,dhid, "duyet don hang", db, _kho_fk, _sanpham_fk, _npp_fk, _kbh_fk,  _soluong*(1.0),(1)*_soluong , 0, 0.0);
					    	if (msg.length()> 0) 
							{
								db.getConnection().rollback();
								db.getConnection().setAutoCommit(true);
								db.shutDown();
					    		return msg1;
							}
						}
					    
						
						query=  " SELECT DH.NGAYNHAP,a.PK_SEQ as sanpham_fk,DH.KHO_FK,DH.NPP_FK,DH.KBH_FK,SP.SOLUONG, a.ten as tensp, sp.ngaynhapkho \n"+
								"  from DONHANG dh inner join DONHANG_CTKM_TRAKM_CHITIET sp on dh.PK_SEQ = sp.DONHANGID     \n"+
								" inner join sanpham a on a.MA = sp.SPMA \n"+
								" where dh.PK_SEQ = "+dhid+" and dh.trangthai=0\n";
								
							System.out.println("UPDATE NPP KHO: " + query  );
						    ckRs = db.get(query);
						    while(ckRs.next())
						    {
						    	String kho_fk=ckRs.getString("kho_fk");
								String _npp_fk=ckRs.getString("npp_fk");	
								String _kbh_fk =ckRs.getString("kbh_fk");
								String sanpham_fk=ckRs.getString("sanpham_fk");
								String ngaynhap  =ckRs.getString("ngaynhap");
								String tensp  =ckRs.getString("tensp");
								Double soluong = ckRs.getDouble("soluong");
						    	String ngaynhapkho=ckRs.getString("NGAYNHAPKHO");    	
						    	 msg1=util.Update_NPP_Kho_Sp_Chitiet(ngaynhap, "chot DHID: "+dhid ,  db, kho_fk, sanpham_fk, _npp_fk, _kbh_fk, ngaynhapkho, (1)*soluong,(1)*soluong, 0, 0, 0);
									if (msg1.length()> 0) 
									{
										db.getConnection().rollback();
										db.getConnection().setAutoCommit(true);
										db.shutDown();
							    		return msg1;
									}
						    }
						
						    ckRs.close();
					    
					    
					
					query="update donhang set trangthaichot=0 where pk_Seq="+dhid;
					db.update(query);
					
					db.shutDown();
					return "Lỗi chốt đơn hàng "+dhid;
				}
				
				query = "\n select * from donhang_sanpham a  "+
						 "\n full outer join   "+
						 "\n ( select sanpham_fk, sum(soluong) as soluong from donhang_sanpham_chitiet where donhang_fk = '" + dhid+ "'  group by sanpham_fk ) b   "+
						 "\n on a.sanpham_fk = b.sanpham_fk   "+
						 "\n where a.donhang_fk ='" + dhid	+ "'  and isnull(a.soluong,0) <> isnull(b.soluong,0)  ";  
				ResultSet rschk = db.get(query);
				
					while (rschk.next()) {
						msg += "Lỗi:Số lượng kho tổng lệch với kho chi tiết trên đơn hàng!";
					}
					rschk.close();
				
				if (msg.trim().length() > 0) {
					db.getConnection().rollback();
					db.shutDown();
					return "Kho tổng và chi tiết không bằng nhau"+dhid;
				}
				
				query = "\n select a.npp_fk, a.sanpham_fk, a.soluong, a.booked, a.available, b.soluong, b.booked, b.available from NHAPP_KHO a "+
						"\n full outer join (select sanpham_fk, kho_fk, npp_fk, kbh_fk, sum(soluong) as soluong, sum(booked) as booked, sum(available) as available  "+
						"\n from NHAPP_KHO_CHITIET b group by sanpham_fk, kho_fk, npp_fk, kbh_fk ) b on a.sanpham_fk = b.sanpham_fk "+
						"\n and a.npp_fk = b.npp_fk and a.kho_fk = b.kho_fk and a.kbh_fk = b.kbh_fk  "+
						"\n where (a.soluong <> b.soluong or a.booked <> b.booked or a.available <> b.available) and a.npp_fk = '"+nppId+"' ";  
				ResultSet rschkkho = db.get(query);
				if (rschkkho != null) {
					while (rschkkho.next()) {
						msg += "Lỗi:Số lượng sản phẩm kho tổng lệch với kho chi tiết!";
					}
					rschkkho.close();
				}
				if (msg.trim().length() > 0) {
					db.getConnection().rollback();
					db.shutDown();
					return "Kho tổng và chi tiết không bằng nhau"+dhid;
				}
				
				
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
				db.shutDown();
				

			} else if (trangthai.equals("3")) {
				// db.getConnection().setAutoCommit(false);
				
				query=" update donhang WITH (ROWLOCK) set NGAYCHOT = dbo.GetLocalDate(DEFAULT)"+
						  "		where pk_seq = "+dhid+" and trangthai =3  ";
					if(db.updateReturnInt(query)<=0)
					{
						db.getConnection().rollback();
						db.getConnection().setAutoCommit(true);
						return "Đơn hàng đang xử lý";
					}
					
				msg = chotDonhang(nppId, dhid, userid, db);
				if (msg.length() > 0) {
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					return msg;
				}
				
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
				db.shutDown();
			}
			
		} catch (Exception e) {
			db.shutDown();
			e.printStackTrace();
			msg = "Đơn hàng hiện tại không thể chốt do quá tải, vui lòng chốt sau ";
			return msg;
		}

		return "";
	
	}
	
	
	public String taoxuatkho_chot(dbutils db,String dhid,String ngaytao,String nguoitao,String nppId)
	{
		
		try
		{
			
			String msg="";
			String query = "insert into Phieuxuatkho(nvgn_fk, trangthai, ngaytao, ngaysua, nguoitao, nguoisua, npp_fk, ngaylapphieu) ";
			query = query
					+ " select case "
					+ " when len((select top(1) NVGN_FK from  DONHANG where PK_SEQ = "
					+ dhid
					+ " )) > 1 "
					+ " then "
					+ " (select top(1) NVGN_FK from  DONHANG where PK_SEQ = "
					+ dhid
					+ " ) "
					+ " when len((select top(1) NVGN_FK from  NVGN_KH where KHACHHANG_FK =(select top(1) NVGN_FK from  DONHANG where PK_SEQ = "
					+ dhid
					+ ") )) > 1 "
					+ " then "
					+ " (select top(1) NVGN_FK from  NVGN_KH where KHACHHANG_FK =(select top(1) NVGN_FK from  DONHANG where PK_SEQ = "
					+ dhid
					+ ") )"
					+ " else "
					+ " (select top(1) pk_seq from  NHANVIENGIAONHAN where NPP_FK = '"
					+ nppId + "') " + " end,'0','" + ngaytao + "','"
					+ ngaytao + "','" + nguoitao + "','" + nguoitao + "','"
					+ nppId + "','" + ngaytao + "'";
			if (db.updateReturnInt(query)<=0) {
				msg += "\n Đơn hàng " + dhid + ". Lỗi: " + query;
				return msg;
			}

			query = "select SCOPE_IDENTITY() as pxkId";

			ResultSet rsPxk = db.get(query);
			rsPxk.next();
			String id = rsPxk.getString("pxkId");
			rsPxk.close();

			float tonggiatri = 0.0f;

			// chua can luu tonggiatri o buoc nay
			String	sql = "Insert into phieuxuatkho_donhang(pxk_fk, donhang_fk, tonggiatri) values('"
					+ id + "', '" + dhid + "', null)";

			if (db.updateReturnInt(sql) <= 0) {
				msg += "\n Đơn hàng " + dhid	+ ". Không thể tạo phiếu xuất kho: " + sql;
				return msg;
			}
			
			   query = id + "&" + nppId + "&" + ngaytao + "&" + nguoitao + "&" + dhid; 
			   String param[] =	  query.split("&"); 
					  int kq = db.execProceduce("tao_chot_xuatkho",param);
					  if (kq == -1) 
						 return "Không thể chốt đơn hàng !...";
			
				query=" update donhang set trangthai='1',trangthaichot=2,FlagModified='1',chuyen ='1',NGAYCHOT = dbo.GetLocalDate(DEFAULT),NGUOICHOT = "+ nguoitao +" "+
					  "	 where pk_seq ="+ dhid +" and trangthaichot=1	 "+
					 "	  and exists (select 1 from phieuxuatkho_donhang a  "+
					"	  inner join phieuxuatkho b on a.pxk_fk = b.pk_seq where a.donhang_fk = donhang.pk_seq and b.trangthai = '1' ) ";
				if (db.updateReturnInt(query) <= 0) {
					System.out.println("error la "+query);
					msg += "\n Đơn hàng " + dhid	+ ". Không thể tạo phiếu xuất kho: " + sql;
					return msg;
				}	   
					  
		}
		catch(Exception e)
		{
			return "loi tao pxk";
		
		}
		return"";
	}
	
}
