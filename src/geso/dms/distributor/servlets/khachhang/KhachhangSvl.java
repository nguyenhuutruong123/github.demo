package geso.dms.distributor.servlets.khachhang;

import geso.dms.distributor.beans.khachhang.*;
import geso.dms.distributor.beans.khachhang.imp.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.HorizontalAlignmentType;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class KhachhangSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int items = 50;
	private int splittings = 20;

	public KhachhangSvl() {
		super();
	}

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		System.out.println(userTen);
		System.out.println(sum);
		System.out.println(userId);

		if (!cutil.check(userId, userTen, sum)) {
			response.sendRedirect("/AHF/index.jsp");
		} else {

			IKhachhangList obj;
			PrintWriter out;

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			out = response.getWriter();

			Utility util = new Utility();
			out = response.getWriter();

			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);
			out.println(userId);

			if (userId.length() == 0)
				userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));

			String action = util.getAction(querystring);
			String khId = util.getId(querystring);
			obj = new KhachhangList();
			String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
			if (view == null)
				view = "";
			obj.setView(view);
			//System.out.println("________"+view);
			//System.out.println("________"+action);
			String param="";
			if (action.equals("delete")) {
				if(view.trim().length() > 0) param ="&view="+view;
				if ( Utility.CheckSessionUser( session,  request, response))
				{
					Utility.RedireactLogin(session, request, response);
				}
				else if( Utility.CheckRuleUser( session,  request, response, "KhachhangSvl", param, Utility.XOA ))
				{
					Utility.RedireactLogin(session, request, response);
				}
				else{
					Delete(khId, userId, obj);
					out.print(khId);
					settingPage(obj);
					obj.setUserId(userId);
					obj.init("");
					session.setAttribute("obj", obj);

					String nextJSP = "/AHF/pages/Distributor/KhachHang.jsp";
					response.sendRedirect(nextJSP);
				}
				
			} else if (action.equals("deactivate")) {
				if(view.trim().length() > 0) param ="&view="+view;
				if ( Utility.CheckSessionUser( session,  request, response))
				{
					Utility.RedireactLogin(session, request, response);
				}
				else if( Utility.CheckRuleUser( session,  request, response, "KhachhangSvl", param, Utility.XOA ))
				{
					Utility.RedireactLogin(session, request, response);
				}
				else{
					obj.setMsg(Deactivate(khId, userId, obj));
					settingPage(obj);
					obj.setUserId(userId);
					obj.setView(view);
					obj.init("");
					session.setAttribute("obj", obj);

					String nextJSP = "/AHF/pages/Distributor/KhachHang.jsp";
					response.sendRedirect(nextJSP);
				}
			} else if (action.equals("duyet") && (view != null && view.equals("TT"))) {
				if(view.trim().length() > 0) param ="&view="+view;
				if ( Utility.CheckSessionUser( session,  request, response))
				{
					Utility.RedireactLogin(session, request, response);
				}
				else if( Utility.CheckRuleUser( session,  request, response, "KhachhangSvl", param, Utility.CHOT ))
				{
					Utility.RedireactLogin(session, request, response);
				}
				else{
					String _msg = "";
					try {
						_msg = Duyet(khId);
					} catch (SQLException e) {
						e.printStackTrace();
						_msg = "Exception: " + e.getMessage();
					}
					obj.setMsg(_msg);
					settingPage(obj);
					obj.setUserId(userId);
					obj.setView(view);
					obj.init("");
					session.setAttribute("obj", obj);

					String nextJSP = "/AHF/pages/Distributor/KhachHang.jsp";
					response.sendRedirect(nextJSP);
				}
			}
			else{
				//System.out.println("View: " + view);
				settingPage(obj);
				obj.setUserId(userId);
				obj.init("");
				session.setAttribute("obj", obj);

				String nextJSP = "/AHF/pages/Distributor/KhachHang.jsp";
				response.sendRedirect(nextJSP);
			}
			
		}
	}

	private boolean DeleteHinhDD(String id, String userId) {
		dbutils db = new dbutils();
		try {

			String sql = "update khachhang set ANHCUAHANG = null,nguoisua = "
					+ userId + "  where pk_seq ='" + id + "'";
			if (!db.update(sql)) {
				//System.out.println("Loi xoa hinh DD: " + sql);
				return false;
			}

			db.shutDown();
		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println("Loi " + e.toString());
			return false;
		}
		return true;
	}

	private void settingPage(IKhachhangList obj) {
		Utility util = new Utility();
		if (getServletContext().getInitParameter("items") != null) {
			String i = getServletContext().getInitParameter("items").trim();
			if (util.isNumeric(i)) {
				//items = Integer.parseInt(i);
			}
		}

		if (getServletContext().getInitParameter("splittings") != null) {
			String i = getServletContext().getInitParameter("splittings").trim();
			if (util.isNumeric(i))
				splittings = Integer.parseInt(i);
		}

		obj.setItems(items);
		obj.setSplittings(splittings);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum)) {
			response.sendRedirect("/AHF/index.jsp");
		} else {

			IKhachhangList obj = new KhachhangList();
			PrintWriter out;

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			Utility util = new Utility();
			userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));

			String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
			if (view == null)
				view = "";
			obj.setView(view);
			
			String param = "";
			if(view.trim().length() > 0) param ="&view="+view;
			if ( Utility.CheckSessionUser( session,  request, response)) {
				obj.DBclose();
				Utility.RedireactLogin(session, request, response);
			}
			if( Utility.CheckRuleUser( session,  request, response, "KhachhangSvl", param, Utility.XEM )) {
				obj.DBclose();
				Utility.RedireactLogin(session, request, response);
			}
			
			String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
			if (action == null) {
				action = "";
			}
			
			String khId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khId"));
			if (khId == null)
				khId = "";
			
			String nppSearch = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppSearch"));
			if (nppSearch == null)
				nppSearch = "";
			obj.setNpp_search(nppSearch);
			
			String kvId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kvId"));
			if (kvId == null)
				kvId = "";
			obj.setKvId(kvId);
			
			String vungId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"));
			if (vungId == null)
				vungId = "";
			obj.setVungId(vungId);
			
			String kbhId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kbhId"));
			//System.out.println("kbhId : "+ kbhId);
			if (kbhId == null)
				kbhId = "";
			obj.setKbhId(kbhId);
			
			String gsbhId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("gsbhId"));
			if (gsbhId == null)
				gsbhId = "";
			obj.setGsbhId(gsbhId);
			String mathamchieu = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("mathamchieu"));
			if (mathamchieu == null)
				mathamchieu = "";
			obj.setMathamchieu(mathamchieu); 
			
			String ddkdId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdId"));
			if (ddkdId == null)
				ddkdId = "";
			obj.setDdkdId(ddkdId);
			
			String isDuyet = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("isDuyet"));
			if (isDuyet == null)
				isDuyet = "";
			obj.setIsDuyet(isDuyet);
			
			String isToado = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("isToado"));
			if (isToado == null)
				isToado = "";
			obj.setIsToado(isToado);
			
			String tinhthanhId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tinhthanhId"));
			if (tinhthanhId == null)
				tinhthanhId = "";
			obj.setTinhthanhId(tinhthanhId);
			
			String trangthai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"));
			if (trangthai == null)
				trangthai = "";
			obj.setTrangthai(trangthai);
			
			String coderoute = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("coderoute"));
			if (coderoute == null)
				coderoute = "";
			obj.setCoderoute(coderoute);

			if (action != null && !action.equals("excel")) {
				out = response.getWriter();
				out.println(action);
			}
			
			String[] checkValue = util.antiSQLInspection_Array(request.getParameterValues(("checkValue")));

			if (action.equals("duyetmulti")) {
				String msg = duyetMulti(checkValue);
				obj.setMsg(msg);
				obj.setUserId(userId);
				obj.init("");
				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);
				response.sendRedirect("/AHF/pages/Distributor/KhachHang.jsp");
			}
			if (action.equals("ngungmulti")) {
				String msg = ngungMulti(checkValue);
				obj.setMsg(msg);
				obj.setUserId(userId);
				obj.init("");
				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);
				response.sendRedirect("/AHF/pages/Distributor/KhachHang.jsp");
			}
			if (action.equals("xoatoadomulti")) {
				String msg = xoatoadoMulti(checkValue);
				obj.setMsg(msg);
				obj.setUserId(userId);
				obj.init("");
				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);
				response.sendRedirect("/AHF/pages/Distributor/KhachHang.jsp");
			}
			if (action.equals("new")) {
				// Empty Bean for distributor
				param = "";
				if(view.trim().length() > 0) param ="&view="+view;
				if ( Utility.CheckSessionUser( session,  request, response))
				{
					Utility.RedireactLogin(session, request, response);
				}
				if( Utility.CheckRuleUser( session,  request, response, "KhachhangSvl", param, Utility.THEM ))
				{
					Utility.RedireactLogin(session, request, response);
				}
				else{
				IKhachhang khBean = (IKhachhang) new Khachhang("");
				khBean.setUserId(userId);
				khBean.createRS();
				// Save Data into session
				session.setAttribute("khBean", khBean);
				String nextJSP = "/AHF/pages/Distributor/KhachHangNew.jsp";
				response.sendRedirect(nextJSP);
				}
			}
			if (action.equals("excel")) {
				obj.setUserId(userId);
				OutputStream outstream = response.getOutputStream();
				String query = getSearchQuery_Excel(request, obj);

				try {
					response.setContentType("application/vnd.ms-excel");
					//response.setContentType("application/xlsm");
					response.setHeader("Content-Disposition","attachment; filename=DanhSachKhachHang_"+ getDateTime() + ".xlsm");
					Workbook workbook = new Workbook();
					workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

					CreateStaticHeader(workbook, obj.getUserId());
					CreateStaticData(workbook, query, obj.getDatasearch());

					// Saving the Excel file
					workbook.save(outstream);
				} catch (Exception ex) {
					ex.printStackTrace();
					obj.setMsg("Exception : " + ex.getMessage());
				}

				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);
				return;

			}
			
			settingPage(obj);

			//System.out.println("action : "+ action);
			if (action.equals("search")) {
				//System.out.println("kbhId sau search 0 : "+ obj.getKbhId());
				String search = getSearchQuery(request, obj);
				obj.setUserId(userId);
				//System.out.println("kbhId sau search 1 : "+ obj.getKbhId());
				obj.init(search);
				//System.out.println("kbhId sau search 2 : "+ obj.getKbhId());
				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);

				response.sendRedirect("/AHF/pages/Distributor/KhachHang.jsp");
			}

			else if (action.equals("view") || action.equals("next") || action.equals("prev"))
			{
				String search = getSearchQuery(request, obj);
				obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
				obj.setUserId(userId);
				obj.init(search);
				obj.setAttribute(request, action, "list", "crrApprSplitting","nxtApprSplitting");
				session.setAttribute("obj", obj);
				response.sendRedirect("/AHF/pages/Distributor/KhachHang.jsp");
			}			
		}
	}
	
	private String getSearchQuery(HttpServletRequest request, IKhachhangList obj) {
		Utility util = new Utility();
		String ten = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khTen"));
		if (ten == null)
			ten = "";
		obj.setTen(ten);		

		String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
		if (nppId == null)
			nppId = "";
		obj.setNppId(nppId);

		String routename = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("route_coderoute"));
		if (routename == null)
			routename = "";
		obj.setRoute(routename);

		String hchId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("hchTen"));
		if (hchId == null)
			hchId = "";
		obj.setHchId(hchId);
		
		String sodt = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sodt"));
		if (sodt == null)
			sodt = "";
		obj.setSodienthoai(sodt);

		String kbhId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kbhId"));
		if (kbhId == null)
			kbhId = "";
		obj.setKbhId(kbhId);

		String vtchId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vtchTen"));
		if (vtchId == null)
			vtchId = "";
		obj.setVtId(vtchId);
		String mathamchieu = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("mathamchieu"));
		if (mathamchieu == null)
			mathamchieu = "";
		obj.setMathamchieu(mathamchieu); 
		String lchId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("lchTen"));
		if (lchId == null)
			lchId = "";
		obj.setLchId(lchId);

		String nppSearch = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppSearch"));
		if (nppSearch == null)
			nppSearch = "";
		obj.setNpp_search(nppSearch);
		
		String kvId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kvId"));
		if (kvId == null)
			kvId = "";
		obj.setKvId(kvId);
		
		String vungId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"));
		if (vungId == null)
			vungId = "";
		obj.setVungId(vungId);
		
		String gsbhId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("gsbhId"));
		if (gsbhId == null)
			gsbhId = "";
		obj.setGsbhId(gsbhId);
		
		String ddkdId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdId"));
		if (ddkdId == null)
			ddkdId = "";
		obj.setDdkdId(ddkdId);
		
		String isDuyet = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("isDuyet"));
		if (isDuyet == null)
			isDuyet = "";
		obj.setIsDuyet(isDuyet);
		
		String isToado = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("isToado"));
		if (isToado == null)
			isToado = "";
		obj.setIsToado(isToado);
		
		String tinhthanhId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tinhthanhId"));
		if (tinhthanhId == null)
			tinhthanhId = "";
		obj.setTinhthanhId(tinhthanhId);
		
		String trangthai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"));
		if (trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String coderoute = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("coderoute"));
		if (coderoute == null)
			coderoute = "";
		obj.setCoderoute(coderoute);
		
		String query = "\n select  isnull(a.mathamchieu,'') as mathamchieu, a.trangthai khtrangthai, qh.ten qhten, tt.ten ttten, v.ten vten, kv.ten kvten, " +
		"\n isnull(a.lat,0)lat, isnull(a.long,0)long, " +
		"\n ddkd.ten ddkdten, ddkd.smartid ddkdma, gs.smartid gsbhma, gs.ten gsbhten, " +
		"\n isnull(route.coderoute,'')coderoute, " +
		"\n 	isnull(route.ten,'')routename, isnull(a.daduyet,0)daduyet, " +
		"\n  	--ROW_NUMBER() OVER(ORDER BY a.pk_seq DESC) AS 'stt', " +
		"\n		a.pk_seq as khId, " +
		"\n  	a.smartid, a.ten as khTen, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, " +
		"\n  	c.ten as nguoisua, d.chietkhau, d.pk_seq as mckId,isnull(a.ANHCUAHANG,'') as ANHCUAHANG, " +
		"\n  	e.diengiai as kbhTen, e.pk_seq as kbhId, f.hang as hchTen, f.pk_seq as hchId, " +
		"\n  	g.DIENGIAI as lchTen, g.pk_seq as lchId, h.ten as nppTen, h.pk_seq as nppId, h.manpp manpp, " +
		"\n  	k.sotienno as ghcnTen, k.pk_seq as ghcnId, l.ten as bgstTen, l.pk_seq as bgstId,  " +
		"\n  	m.vitri as vtchTen, m.pk_seq as vtchId, a.dienthoai, a.diachi, " +
		"\n  	--isnull((select top(1) ten from DAIDIENKINHDOANH where pk_seq = a.DDKD_FK),'') as ddkdtao, " +
		"\n  	--isnull((select top(1) ten from DAIDIENKINHDOANH where pk_seq = a.DDKDTAO_FK),'') as ddkdsua, " +
		"\n  	isnull(isPDASua,'') as  isPDASua  " +
		"\n  from khachhang a inner join nhanvien b on a.nguoitao = b.pk_seq  " +
		"\n  inner join nhanvien c on a.nguoisua = c.pk_seq  " +
		"\n  left join mucchietkhau d on a.chietkhau_fk = d.pk_seq  " +
		"\n  left join kenhbanhang e on a.kbh_fk = e.pk_seq  " +
		"\n  left join hangcuahang f on a.hch_fk = f.pk_seq  " +
		"\n  left join loaicuahang g on a.lch_fk = g.pk_seq " +
		"\n  inner join nhaphanphoi h on a.npp_fk = h.pk_seq  " +
		"\n  left join gioihancongno k on a.ghcn_fk = k.pk_seq  " +
		"\n  left join banggiasieuthi l on a.bgst_fk = l.pk_seq  " +
		"\n  left join vitricuahang m on a.vtch_fk = m.pk_seq  " +
		"\n  left join DMS_Route route on route.pk_seq = a.route_fk " +
		"\n  outer apply " +
		"\n  ( " +
		"\n		SELECT KHACHHANG_FK,BB.DDKD_FK, CC.GSBH_FK " +
		"\n		FROM KHACHHANG_TUYENBH AA " +
		"\n		INNER JOIN TUYENBANHANG BB ON BB.PK_SEQ = AA.TBH_FK " +
		"\n		INNER JOIN DDKD_GSBH CC ON CC.DDKD_FK = BB.DDKD_FK " +
		"\n		where KHACHHANG_FK = a.pk_seq " +
		"\n		GROUP BY KHACHHANG_FK, BB.DDKD_FK, CC.GSBH_FK " +
		"\n  ) DDKD_GSBH " +
		"\n  left join daidienkinhdoanh ddkd on ddkd.pk_seq = DDKD_GSBH.ddkd_fk " +
		"\n  left join giamsatbanhang gs on gs.pk_seq = DDKD_GSBH.GSBH_FK " +
		"\n  left join quanhuyen qh on qh.pk_seq = a.quanhuyen_fk " +
		"\n  left join tinhthanh tt on tt.pk_seq = a.tinhthanh_fk " +
		"\n  left join khuvuc kv on kv.pk_seq = h.khuvuc_fk " +
		"\n  left join vung v on v.pk_seq = kv.vung_fk " +
		"\n  where 1=1 ";
		//System.out.println("nppId: " + nppId);		
		obj.getDatasearch().clear();
		
		if (obj.getView() != null && !obj.getView().equals("TT")) {
			if (nppId != null && nppId.length() > 0) {
				query += "\n and h.pk_seq = ? ";
				obj.getDatasearch().add(nppId);
			}
		}
		
		if(sodt.length() > 0) {
			query += "\n and a.dienthoai like ?";
			obj.getDatasearch().add("%"+sodt+"%");
		}
		
		if(kvId.length() > 0) {
			query += "\n and kv.pk_seq = ?";
			obj.getDatasearch().add(kvId);
		}
		if(vungId.length() > 0) {
			query += "\n and v.pk_seq = ?";
			obj.getDatasearch().add(vungId);
		}
		if(mathamchieu.length() > 0) {
			query += "\n and a.mathamchieu = ?";
			obj.getDatasearch().add(mathamchieu);
		}
		if(gsbhId.length() > 0) {
			query += "\n and gs.pk_seq = ?";
			obj.getDatasearch().add(gsbhId);
		}
		if(ddkdId.length() > 0) {
			query += "\n and ddkd.pk_seq = ?";
			obj.getDatasearch().add(ddkdId);
		}
		if(isDuyet.length() > 0) {
			query += "\n and isnull(a.daduyet,0) = ?";
			obj.getDatasearch().add(isDuyet);
		}
		if(isToado.length() > 0) {
			if (isToado.equals("1"))
				query += "\n and (a.lat+a.long) is not null";
			else 
				query += "\n and (a.lat+a.long) is  null";
			
			//obj.getDatasearch().add(isToado);
		}
		if(tinhthanhId.length() > 0) {
			query += "\n and tt.pk_seq = ?";
			obj.getDatasearch().add(tinhthanhId);
		}
		if(trangthai.length() > 0) {
			query += "\n and a.trangthai = ?";
			obj.getDatasearch().add(trangthai);
		}
		if(coderoute.length() > 0) {
			query += "\n and a.coderoute like ?";
			obj.getDatasearch().add("%"+coderoute+"%");
		}	
		if (routename.length() > 0) {
			query = query + "\n and ( upper(n.TEN) like upper(?) or upper((n.CodeRoute)) like upper(?)) ";
			obj.getDatasearch().add("%"+util.replaceAEIOU(routename)+"%");
			obj.getDatasearch().add("%"+util.replaceAEIOU(routename)+"%");
		}		
		if (ten.length() > 0) {
//			query = query + "\n and ( upper(a.smartid) like upper(?) or upper((a.timkiem)) like upper(?)) ";
//			obj.getDatasearch().add("%"+util.replaceAEIOU(ten)+"%");
//			obj.getDatasearch().add("%"+util.replaceAEIOU(ten)+"%");
			query += "\n and (a.smartid like N'%"+ten+"%' or a.ten like N'%"+ten+"%' or a.timkiem like N'%"+ten+"%' )";
		}
		if (kbhId.length() > 0) {
			query = query + "\n and a.kbh_fk =? ";
			obj.getDatasearch().add(kbhId);
		}
		if (hchId.length() > 0) {
			query = query + "\n and a.hch_fk=? ";
			obj.getDatasearch().add(hchId);
		}
		if (vtchId.length() > 0) {
			query = query + "\n and a.vtch_fk=? ";
			obj.getDatasearch().add(vtchId);
		}
		if (lchId.length() > 0) {
			query = query + "\n and a.lch_fk=? ";
			obj.getDatasearch().add(lchId);
		}		
		if (nppSearch != null && nppSearch.length() > 0) {
			query = query + "\n and h.pk_seq =? ";
			obj.getDatasearch().add(nppSearch);
		}

		return query;
	}
	
	private String getSearchQuery_Excel(HttpServletRequest request, IKhachhangList obj) {
		Utility util = new Utility();
		String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
		if (view == null)
			view = "";
		obj.setView(view);
		String ten = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khTen"));
		if (ten == null)
			ten = "";
		obj.setTen(ten);		

		String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
		if (nppId == null)
			nppId = "";
		obj.setNppId(nppId);
		System.out.println("nppId: " + nppId);

		String routename = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("route_coderoute"));
		if (routename == null)
			routename = "";
		obj.setRoute(routename);

		String hchId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("hchTen"));
		if (hchId == null)
			hchId = "";
		obj.setHchId(hchId);
		String mathamchieu = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("mathamchieu"));
		if (mathamchieu == null)
			mathamchieu = "";
		obj.setMathamchieu(mathamchieu); 
		String kbhId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kbhId"));
		if (kbhId == null)
			kbhId = "";
		obj.setKbhId(kbhId);

		String vtchId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vtchTen"));
		if (vtchId == null)
			vtchId = "";
		obj.setVtId(vtchId);

		String lchId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("lchTen"));
		if (lchId == null)
			lchId = "";
		obj.setLchId(lchId);

		String nppSearch = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppSearch"));
		if (nppSearch == null)
			nppSearch = "";
		obj.setNpp_search(nppSearch);
		
		String kvId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kvId"));
		if (kvId == null)
			kvId = "";
		obj.setKvId(kvId);
		
		String vungId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"));
		if (vungId == null)
			vungId = "";
		obj.setVungId(vungId);
		
		String gsbhId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("gsbhId"));
		if (gsbhId == null)
			gsbhId = "";
		obj.setGsbhId(gsbhId);
		
		String ddkdId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdId"));
		if (ddkdId == null)
			ddkdId = "";
		obj.setDdkdId(ddkdId);
		
		String isDuyet = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("isDuyet"));
		if (isDuyet == null)
			isDuyet = "";
		obj.setIsDuyet(isDuyet);
		
		String isToado = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("isToado"));
		if (isToado == null)
			isToado = "";
		obj.setIsToado(isToado);
		
		String tinhthanhId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tinhthanhId"));
		if (tinhthanhId == null)
			tinhthanhId = "";
		obj.setTinhthanhId(tinhthanhId);
		
		String trangthai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"));
		if (trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String coderoute = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("coderoute"));
		if (coderoute == null)
			coderoute = "";
		obj.setCoderoute(coderoute);
		
		String query = "\n select a.trangthai khtrangthai, mathamchieu, tbh.ngayid, tbh.tanso, " +
		"\n 'LAT: '+convert(varchar,isnull(a.lat,0))+' - LNG: '++convert(varchar,isnull(a.long,0))toado, " +
		"\n qh.ten qhten, tt.ten ttten, v.ten vten, kv.ten kvten, " +
		"\n isnull(a.lat,0)lat, isnull(a.long,0)long, " +
		"\n ddkd.ten ddkdten, ddkd.smartid ddkdma, gs.smartid gsbhma, gs.ten gsbhten, " +
		"\n isnull(route.coderoute,'')coderoute, " +
		"\n 	isnull(route.ten,'')routename, isnull(a.daduyet,0)daduyet, " +
		"\n  	--ROW_NUMBER() OVER(ORDER BY a.pk_seq DESC) AS 'stt', " +
		"\n		a.pk_seq as khId, " +
		"\n  	a.smartid, a.ten as khTen, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, " +
		"\n  	c.ten as nguoisua, d.chietkhau, d.pk_seq as mckId,isnull(a.ANHCUAHANG,'') as ANHCUAHANG, " +
		"\n  	e.diengiai as kbhTen, e.pk_seq as kbhId, f.hang as hchTen, f.pk_seq as hchId, " +
		"\n  	g.DIENGIAI as lchTen, g.pk_seq as lchId, h.ten as nppTen, h.pk_seq as nppId, h.manpp manpp, " +
		"\n  	k.sotienno as ghcnTen, k.pk_seq as ghcnId, l.ten as bgstTen, l.pk_seq as bgstId,  " +
		"\n  	m.vitri as vtchTen, m.pk_seq as vtchId, a.dienthoai, a.diachi, " +
		"\n  	--isnull((select top(1) ten from DAIDIENKINHDOANH where pk_seq = a.DDKD_FK),'') as ddkdtao, " +
		"\n  	--isnull((select top(1) ten from DAIDIENKINHDOANH where pk_seq = a.DDKDTAO_FK),'') as ddkdsua, " +
		"\n  	isnull(isPDASua,'') as  isPDASua, isnull(ddkdTao.Ten,'')ddkdTaoTen  " +
		"\n  from khachhang a inner join nhanvien b on a.nguoitao = b.pk_seq" +
		"\n	 left join daidienkinhdoanh ddkdTao on ddkdTao.pk_seq = a.DDKDTAO_FK   " +
		"\n  inner join nhanvien c on a.nguoisua = c.pk_seq  " +
		"\n  left join mucchietkhau d on a.chietkhau_fk = d.pk_seq  " +
		"\n  left join kenhbanhang e on a.kbh_fk = e.pk_seq  " +
		"\n  left join hangcuahang f on a.hch_fk = f.pk_seq  " +
		"\n  left join loaicuahang g on a.lch_fk = g.pk_seq " +
		"\n  inner join nhaphanphoi h on a.npp_fk = h.pk_seq  " +
		"\n  left join gioihancongno k on a.ghcn_fk = k.pk_seq  " +
		"\n  left join banggiasieuthi l on a.bgst_fk = l.pk_seq  " +
		"\n  left join vitricuahang m on a.vtch_fk = m.pk_seq  " +
		"\n  left join DMS_Route route on route.pk_seq = a.route_fk " +
		"\n  outer apply " +
		"\n  ( " +
		"\n		SELECT KHACHHANG_FK,BB.DDKD_FK, CC.GSBH_FK " +
		"\n		FROM KHACHHANG_TUYENBH AA " +
		"\n		INNER JOIN TUYENBANHANG BB ON BB.PK_SEQ = AA.TBH_FK " +
		"\n		INNER JOIN DDKD_GSBH CC ON CC.DDKD_FK = BB.DDKD_FK " +
		"\n		where KHACHHANG_FK = a.pk_seq " +
		"\n		GROUP BY KHACHHANG_FK, BB.DDKD_FK, CC.GSBH_FK " +
		"\n  ) DDKD_GSBH " +
		"\n  inner join daidienkinhdoanh ddkd on ddkd.pk_seq = DDKD_GSBH.ddkd_fk " +
		"\n  inner join giamsatbanhang gs on gs.pk_seq = DDKD_GSBH.GSBH_FK " +
		"\n  left join quanhuyen qh on qh.pk_seq = a.quanhuyen_fk " +
		"\n  left join tinhthanh tt on tt.pk_seq = a.tinhthanh_fk " +
		"\n  left join khuvuc kv on kv.pk_seq = h.khuvuc_fk " +
		"\n  left join vung v on v.pk_seq = kv.vung_fk " +
		"\n  outer apply " + 
		"\n  ( " +
		"\n		select ngayid, tanso " +
		"\n		from  " +
		"\n		( " +
		"\n			select  " +
		"\n			STUFF((SELECT ';' + convert(varchar,tanso) FROM KHACHHANG_TUYENBH where khachhang_fk = a.pk_seq FOR XML PATH ('')), 1, 1, '' ) tanso, " +
		"\n			STUFF((SELECT ';' + convert(varchar,ngayid) FROM tuyenbanhang aaa inner join khachhang_tuyenbh bbb on aaa.pk_seq = bbb.tbh_fk " +
		"\n			where bbb.khachhang_fk = a.pk_seq FOR XML PATH ('')), 1, 1, '' ) ngayid " +
		"\n		) tbh " +
		"\n 	GROUP BY tbh.ngayid, tbh.tanso " +
		"\n	 )tbh " +
		"\n  where 1=1 ";
		//System.out.println("Query Excel: "+query);
		////System.out.println("nppId: " + nppId);		
//		obj.getDatasearch().clear();
		System.out.println("obj.getView():" + obj.getView() + "" );
		if (obj.getView() != null && !obj.getView().equals("TT")) {
			if (nppId != null && nppId.length() > 0) {
				query += "\n and h.pk_seq = '"+nppId+"' ";
//				obj.getDatasearch().add(nppId);
			}
		}
		if (obj.getView() != null && obj.getView().equals("TT")) {
			query += "\n and a.kbh_fk in ("+Utility.PhanQuyenKBH(obj.getUserId())+") ";
			query += "\n and a.npp_fk in ("+Utility.PhanQuyenNPP(obj.getUserId())+") ";
		}
		System.out.println("kenhbhid: " + kbhId);
		if(kvId.length() > 0) {
			query += "\n and kv.pk_seq = '"+kvId+"'";
//			obj.getDatasearch().add(kvId);
		}
		if(vungId.length() > 0) {
			query += "\n and v.pk_seq = '"+vungId+"'";
//			obj.getDatasearch().add(vungId);
		}
		if(gsbhId.length() > 0) {
			query += "\n and gs.pk_seq = '"+gsbhId+"'";
//			obj.getDatasearch().add(gsbhId);
		}
		if(ddkdId.length() > 0) {
			query += "\n and ddkd.pk_seq = '"+ddkdId+"'";
//			obj.getDatasearch().add(ddkdId);
		}
		if(isDuyet.length() > 0) {
			query += "\n and isnull(a.daduyet,0) = '"+isDuyet+"'";
//			obj.getDatasearch().add(isDuyet);
		}
		if(isToado.length() > 0) {
			if (isToado.equals("1"))
				query += "\n and (a.lat+a.long) is not null";
			else 
				query += "\n and (a.lat+a.long) is null";
			
			//obj.getDatasearch().add(isToado);
		}
		if(tinhthanhId.length() > 0) {
			query += "\n and tt.pk_seq = '"+tinhthanhId+"'";
//			obj.getDatasearch().add(tinhthanhId);
		}
		if(trangthai.length() > 0) {
			query += "\n and a.trangthai = '"+trangthai+"'";
//			obj.getDatasearch().add(trangthai);
		}
		if(coderoute.length() > 0) {
			query += "\n and a.coderoute like N'%"+coderoute+"%'";
//			obj.getDatasearch().add("%"+coderoute+"%");
		}	
		if (routename.length() > 0) {
			query = query + "\n and ( upper(n.TEN) like upper('%"+routename+"%') or upper((n.CodeRoute)) like upper('%"+routename+"%')) ";
//			obj.getDatasearch().add("%"+util.replaceAEIOU(routename)+"%");
//			obj.getDatasearch().add("%"+util.replaceAEIOU(routename)+"%");
		}		
		if (ten.length() > 0) {
			query = query + "\n and ( upper(a.smartid) like upper(N'%"+ten+"%') or upper((a.timkiem)) like upper(N'%"+ten+"%')) ";
//			obj.getDatasearch().add("%"+util.replaceAEIOU(ten)+"%");
//			obj.getDatasearch().add("%"+util.replaceAEIOU(ten)+"%");
		}
		if (kbhId.length() > 0) {
			query = query + "\n and a.kbh_fk ='"+kbhId+"' ";
//			obj.getDatasearch().add(kbhId);
		}
		if (hchId.length() > 0) {
			query = query + "\n and a.hch_fk='"+hchId+"' ";
//			obj.getDatasearch().add(hchId);
		}
		if (vtchId.length() > 0) {
			query = query + "\n and a.vtch_fk='"+vtchId+"' ";
			obj.getDatasearch().add(vtchId);
		}
		if (mathamchieu.length() > 0) {
			query = query + "\n and a.mathamchieu='"+mathamchieu+"' ";
			obj.getDatasearch().add(mathamchieu);
		}
		if (lchId.length() > 0) {
			query = query + "\n and a.lch_fk='"+lchId+"' ";
//			obj.getDatasearch().add(lchId);
		}		
		if (nppSearch != null && nppSearch.length() > 0) {
			query = query + "\n and h.pk_seq ='"+nppSearch+"' ";
//			obj.getDatasearch().add(nppSearch);
		}
		System.out.println("query excel: " + query);
		return query;
	}
	
	private void Delete(String id, String userId, IKhachhangList obj) {
		dbutils db = new dbutils();

		db.update("SET TRANSACTION ISOLATION LEVEL SNAPSHOT;");
		db.update("SET LOCK_TIMEOUT 60000;");

		// Lay ID nhapp tu UserId
		String query = "";
		String view = obj.getView();

		if (view != null && !view.equals("TT")) {
			query = "select a.pk_seq from nhaphanphoi a, nhanvien b where b.CONVSITECODE = a.SITECODE and b.pk_seq ='"
					+ userId + "'";
		} else {
			query = "select npp_fk pk_seq from khachhang where pk_seq = " + id;
		}

		ResultSet rs = db.get(query);
		//System.out.println("Lay dl : " + query);
		String nppId = "";
		try {
			rs.next();
			nppId = rs.getString("pk_seq");
			rs.close();

			db.getConnection().setAutoCommit(false);

			int check = -1;
			query = "select count(*) c from donhang where khachhang_fk = " + id;
			ResultSet temprs = db.get(query);
			try {
				while (temprs.next()) {
					check = temprs.getInt("c");
				}
				temprs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (check > 0) {
				String msg = "Không thể xoá khách hàng đã phát sinh dữ liệu trong đơn hàng bán";
				obj.setMsg(msg);
				db.getConnection().rollback();
				return;
			}

			query = "delete from khachhang_nkhachhang where khachhang_fk='"
					+ id + "'";
			db.update(query);

			query = "delete from NHOMKHACHHANG_KHACHHANG where KH_fk='" + id
					+ "'";
			db.update(query);

			query = "delete from khachhang_tuyenBH where khachhang_fk='" + id
					+ "'";
			db.update(query);

			query = "delete from nvgn_kh where khachhang_fk = '" + id + "'";
			db.update(query);

			query = "delete from ddkd_khachhang where khachhang_fk = '" + id
					+ "'";
			db.update(query);

			query = "delete from KHACHHANG_SANPHAMCK where khachhang_fk = '"
					+ id + "'";
			db.update(query);

			query = "delete from KHACHHANG_CHUNGLOAISPCK where khachhang_fk = '"
					+ id + "'";
			db.update(query);

			query = "delete from khachhang_htkd where khachhang_fk = '" + id
					+ "'";
			db.update(query);

			query = "delete from makhachhang where khachhang_fk = '" + id + "'";
			db.update(query);

			query = "delete from khachhang where pk_seq = '" + id
					+ "' and npp_fk='" + nppId + "'";
			db.update(query);

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e) {
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			//System.out.println("Loi : " + e.toString());
		}
		db.shutDown();
	}

	private String Deactivate(String id, String userId, IKhachhangList obj) {
		dbutils db = new dbutils();
		String query = "";
		try {

			query = "update khachhang set trangthai = 0 where trangthai = 1 and pk_seq = "
					+ id;
			//System.out.println("deactive: "+query);
			if (!db.update(query)) {
				String msg = "Lỗi không thể ngưng hoạt động khách hàng này!";
				obj.setMsg(msg);
				db.getConnection().rollback();
				return msg;
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			return "Ngưng hoạt động khách hàng thành công!";
		} catch (Exception e) {
			e.printStackTrace();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			db.shutDown();
			return "Exception: " + e.getMessage();
		}
	}

	private String Duyet(String id) throws SQLException {
		dbutils db = new dbutils();

		try {
			db.getConnection().setAutoCommit(false);

			String query = "";
			query = "update khachhang set daduyet =1 where isnull(daduyet,0) = 0 and pk_Seq ='"+ id + "'";
			if (!db.update(query)) {
				db.getConnection().rollback();
				return "Không thể duyệt khách Hàng " + query;
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();

			return "Duyệt thành công!";
		} catch (Exception e) {
			db.getConnection().rollback();
			e.printStackTrace();
			db.shutDown();
			return "Lỗi ngoại lệ: " + e.getMessage();
		}
	}

	private void CreateStaticData(Workbook workbook, String query, List<Object> data) {
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();

		dbutils db = new dbutils();
//		ResultSet rs = db.get_v2(query, data);
		ResultSet rs = db.get(query);
		//System.out.println("Query Excel: " + query);
		NumberFormat formatter = new DecimalFormat("#,###,###");
		int i = 5;
		if (rs != null) {
			try {
				cells.setColumnWidth(0, 10f);
				cells.setColumnWidth(1, 15.0f);
				cells.setColumnWidth(2, 10.0f);
				cells.setColumnWidth(3, 10.0f);
				cells.setColumnWidth(4, 10.0f);
				cells.setColumnWidth(5, 20.0f);
				cells.setColumnWidth(6, 25.0f);
				cells.setColumnWidth(7, 10.0f);
				cells.setColumnWidth(8, 10.0f);
				cells.setColumnWidth(8, 25.0f);
				cells.setColumnWidth(10, 10.0f);
				cells.setColumnWidth(11, 5.0f);	
				cells.setColumnWidth(12, 5.0f);	

				for (int j = 0; j < 11; j++) {
					if (j == 0)
						cells.setRowHeight(j, 23.0f);
					else
						cells.setRowHeight(j, 13.0f);
				}

				Cell cell = null;

				Style style;
				Font font2 = new Font();
				font2.setName("Calibri");
				font2.setSize(11);

				int count = 1;
				while (rs.next()) {
					int cot = 0;
					String url = "http://1.53.252.173:9999/AnhChupPDA/AnhDaiDien/";
					
					cell = cells.getCell(i, cot++);
					cell.setValue(rs.getString("vten"));
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);					
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell(i, cot++);
					cell.setValue(rs.getString("kvten"));
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);					
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell(i, cot++);
					cell.setValue(rs.getString("ttten"));
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);					
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell(i, cot++);
					cell.setValue(rs.getString("qhten"));
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell(i, cot++);
					cell.setValue(rs.getString("manpp"));
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell(i, cot++);
					cell.setValue(rs.getString("nppten"));
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell(i, cot++);
					cell.setValue(rs.getString("khten"));
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell(i, cot++);
					cell.setValue(rs.getString("smartid"));
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
										
					cell = cells.getCell(i, cot++);
					cell.setValue(rs.getString("mathamchieu"));
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell(i, cot++);
					cell.setValue(rs.getString("diachi"));
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell(i, cot++);
					cell.setValue(rs.getString("routename"));
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell(i, cot++);
					cell.setValue(rs.getString("ngayid"));
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell(i, cot++);
					cell.setValue(rs.getString("tanso"));
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell(i, cot++);
					cell.setValue(rs.getString("ddkdten"));
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell(i, cot++);
					cell.setValue(rs.getString("ddkdma"));
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell(i, cot++);
					cell.setValue(rs.getString("gsbhma"));
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell(i, cot++);
					cell.setValue(rs.getString("gsbhten"));
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);
					String trangthaiduyet = (rs.getString("daduyet") != null && rs.getString("daduyet").equals("1"))?"Duyệt":"Chưa";
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell(i, cot++);
					cell.setValue(trangthaiduyet);
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);					
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell(i, cot++);
					cell.setValue(rs.getString("ngaytao"));
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);					
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell(i, cot++);
					cell.setValue(rs.getString("ddkdTaoTen"));
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);					
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					
					cell = cells.getCell(i, cot++);
					cell.setValue(rs.getString("nguoitao"));
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);					
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell(i, cot++);
					cell.setValue(rs.getString("ngaysua"));
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);					
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell(i, cot++);
					cell.setValue(rs.getString("nguoisua"));
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);					
					String anhcuahang = (rs.getString("anhcuahang")!=null&&rs.getString("anhcuahang").length()>0)?rs.getString("anhcuahang"):"";
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell(i, cot++);
					cell.setValue(anhcuahang!=null&&anhcuahang.length()>0?url+anhcuahang:"");
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);					
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell(i, cot++);
					cell.setValue(rs.getString("lchten"));
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell(i, cot++);
					cell.setValue(rs.getString("toado"));
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);					
					
					String trangthai = (rs.getString("khtrangthai") != null && rs.getString("khtrangthai").equals("1"))?"Hoạt động":"Không hoạt động";
					cell = cells.getCell(i, cot++);
					cell.setValue(trangthai);
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
									
					i++;
					count++;
				}
				rs.close();
				db.shutDown();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	private void setCellBorderStyle(Cell cell, short align) {
		Style style = cell.getStyle();
		style.setHAlignment(align);
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);
		cell.setStyle(style);
	}

	private void CreateStaticHeader(Workbook workbook, String UserName) {
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		Cells cells = worksheet.getCells();
		Style style;

		Cell cell = cells.getCell("A1");
		cells.merge(0, 0, 0, 11);
		cell.setValue("DANH SÁCH KHÁCH HÀNG");
		style = cell.getStyle();
		Font font2 = new Font();
		font2.setName("Calibri");
		font2.setColor(Color.NAVY);
		font2.setSize(18);
		font2.setBold(true);
		style.setFont(font2);
		style.setHAlignment(TextAlignmentType.CENTER);
		cell.setStyle(style);

		font2 = new Font();
		font2.setName("Calibri");
		font2.setBold(true);
		font2.setSize(11);

		cell = cells.getCell("A3");
		cell.setValue("Ngày tạo: " + this.getDateTime());
		style = cell.getStyle();
		style.setFont(font2);
		cell.setStyle(style);

		// tieu de
		int cot = 0;
		cell = cells.getCell(4, cot++);
		
		cell.setValue("Vùng");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Khu Vực");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Tỉnh Thành");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Quận Huyện");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Mã NPP");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Tên NPP");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Tên KH");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Mã KH");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);		
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Mã KH tham thiếu");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Địa chỉ KH");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Route");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Tuyến thứ");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Tần suất");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Tên NVBH");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Mã NVBH");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Mã SS");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Tên SS");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Trạng thái duyệt");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Ngày tạo");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		cell = cells.getCell(4, cot++);
		cell.setValue("NVBH tạo");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Người tạo");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Ngày sửa");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Người sửa");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Ảnh đại diện");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Loại khách hàng");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Toạ độ");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Trạng thái");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);

		worksheet.setName("DSKH");
	}
	
	public String duyetMulti(String[] khList) {
		
		String msg = "";
		String query = "";
		String khids = "";
		String khdaduyet = "";
		dbutils db = new dbutils();
		ResultSet rs;
		
		if (khList == null || khList.length <= 0) {
			msg = "Danh sách khách hàng không hợp lệ!";
			return msg;
		}
		
		for (String temp:khList) {
			khids += ","+temp;
		}
		
		if (khids != null && khids.length() > 0) {
			khids = khids.substring(1);
			query = "select smartid from khachhang where isnull(daduyet,0) = 1 and pk_seq in ("+khids+")";
			rs = db.get(query);
			try {
				while (rs.next()) {
					khdaduyet += ","+rs.getString("smartid");
				}
				rs.close();
				
				if (khdaduyet != null && khdaduyet.length() > 0) 
					khdaduyet = khdaduyet.substring(1);
				
				query = "update khachhang set daduyet = 1 where isnull(daduyet,0) = 0 and pk_seq in ("+khids+")";
				int temp = db.updateReturnInt(query);
				if (temp < 0) {
					msg = "Lỗi duyệt nhiều khách hàng";
				}
				else {
					msg = "Duyệt "+temp+" khách hàng thành công! "+(khdaduyet!=null&&khdaduyet.length()>0?"\nCác khách hàng đã duyệt từ trước: "+khdaduyet:"");
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				msg = "Lỗi ngoại lệ 1";
			}
		}
		
		db.shutDown();
		return msg;
	}
public String ngungMulti(String[] khList) {
		
		String msg = "";
		String query = "";
		String khids = "";
		String khdaduyet = "";
		dbutils db = new dbutils();
		ResultSet rs;
		
		if (khList == null || khList.length <= 0) {
			msg = "Danh sách khách hàng không hợp lệ!";
			return msg;
		}
		
		for (String temp:khList) {
			khids += ","+temp;
		}
		
		if (khids != null && khids.length() > 0) {
			khids = khids.substring(1);
			rs = db.get(query);
			try {

				query = "update khachhang set trangthai = 0 where trangthai = 1 and pk_seq in ("+khids+")";
				int temp = db.updateReturnInt(query);
				if (temp < 0) {
					msg = "Lỗi Ngưng nhiều khách hàng";
				}
				else {
					msg = "Ngưng "+temp+" khách hàng thành công! ";
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				msg = "Lỗi ngoại lệ 1";
			}
		}
		
		db.shutDown();
		return msg;
	}
	public String xoatoadoMulti(String[] khList) {
		String msg = "";
		String query = "";
		String khids = "";
		dbutils db = new dbutils();
		
		if (khList == null || khList.length <= 0) {
			msg = "Danh sách khách hàng không hợp lệ!";
			return msg;
		}
		
		for (String temp:khList) {
			khids += ","+temp;
		}
		
		if (khids != null && khids.length() > 0) {
			khids = khids.substring(1);				
			query = "\n update khachhang set lat = null, long = null, anhcuahang = null " +
			"\n where pk_seq in ("+khids+")";
			if (!db.update(query)) {
				msg = "Lỗi xoá toạ độ nhiều khách hàng";
			}
		}
		
		msg = "Xoá độ khách hàng thành công! ";
		db.shutDown();
		return msg;
	}
	
	
}
