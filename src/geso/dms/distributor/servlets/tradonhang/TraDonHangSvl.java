package geso.dms.distributor.servlets.tradonhang;


import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.tradonhang.ITraDonHang;
import geso.dms.distributor.beans.tradonhang.ITraDonHangList;
import geso.dms.distributor.beans.tradonhang.imp.TraDonHang;
import geso.dms.distributor.beans.tradonhang.imp.TraDonHangList;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/TraDonHangSvl")
public class TraDonHangSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public TraDonHangSvl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/AHF/index.jsp");
		} else
		{

			ITraDonHangList obj;

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();

			userId = (String) session.getAttribute("userId");
			userTen = (String) session.getAttribute("userTen");
			sum = (String) session.getAttribute("sum");
			Utility util = (Utility) session.getAttribute("util");
			if (!util.check(userId, userTen, sum))
			{
				response.sendRedirect("/index.jsp");
			} else
			{
				session.setMaxInactiveInterval(1200);
				String querystring = request.getQueryString();
				String action = util.getAction(querystring);
				String ddhId = util.getId(querystring);
				String nppId=util.getIdNhapp(userId);
				obj = new TraDonHangList();
				if (action.equals("delete"))
				{
					obj.setMsg( Delete(ddhId,nppId));
				}else if(action.equals("chot"))
				{
					obj.setMsg( Chot(ddhId,nppId));
				}
			
				obj.setUserId(userId);
				obj.createDdhlist("");
				session.setAttribute("obj", obj);
				String nextJSP = "/AHF/pages/Distributor/TraDonHang.jsp";
				response.sendRedirect(nextJSP);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/AHF/index.jsp");
		} else
		{
			ITraDonHangList obj;

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			userId = (String) session.getAttribute("userId");
			userTen = (String) session.getAttribute("userTen");
			sum = (String) session.getAttribute("sum");
			Utility util = (Utility) session.getAttribute("util");
			if (!util.check(userId, userTen, sum))
			{
				response.sendRedirect("index.jsp");
			} else
			{
				session.setMaxInactiveInterval(1200);
				obj = new TraDonHangList();

				String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
				if (action == null)
				{
					action = "";
				}

				if (action.equals("new"))
				{

					ITraDonHang dnhBean = (ITraDonHang) new TraDonHang("");
					dnhBean.setUserId(userId);
					dnhBean.createRS();
					session.setAttribute("dtdhBean", dnhBean);
					String nextJSP = "/AHF/pages/Distributor/TraDonHangNew.jsp";
					response.sendRedirect(nextJSP);
				} else if (action.equals("view") || action.equals("next") || action.equals("prev"))
				{

					String nppId = "";

					geso.dms.distributor.util.Utility dutil = new geso.dms.distributor.util.Utility();
					nppId = dutil.getIdNhapp(userId);
					String search = getSearchQuery(request, util, nppId, obj);
					obj.setUserId(userId);
					session.setAttribute("userId", userId);
					obj.createDdhlist(search);
					session.setAttribute("obj", obj);
					String nextJSP = "/AHF/pages/Distributor/TraDonHang.jsp";
					System.out.print(search);
					response.sendRedirect(nextJSP);
				} else
				{

					String nppId = "";

					geso.dms.distributor.util.Utility dutil = new geso.dms.distributor.util.Utility();
					nppId = dutil.getIdNhapp(userId);

					String search = getSearchQuery(request, util, nppId, obj);
					obj.setUserId(userId);
					session.setAttribute("userId", userId);

					obj.createDdhlist(search);

					session.setAttribute("obj", obj);

					String nextJSP = "/AHF/pages/Distributor/TraDonHang.jsp";
					System.out.print(search);
					response.sendRedirect(nextJSP);
				}
			}
		}
	}
	private String getSearchQuery(HttpServletRequest request, Utility util, String nppId, ITraDonHangList obj)
	{
		String tungay = util.ValidateParam(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"))));
		if (tungay == null)
			tungay = "";
		obj.setTungay(tungay);

		String denngay = util.ValidateParam(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay"))));
		if (denngay == null)
			denngay = "";
		obj.setDenngay(denngay);

		String trangthai = util.ValidateParam(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"))));
		if (trangthai == null)
			trangthai = "";

		obj.setTrangthai(trangthai);

		String query =
		"	SELECT DH.DONHANG_FK ,DH.PK_SEQ AS NHAPHANGID,DH.NGAYNHAP,NT.TEN AS NGUOITAO,DH.NGAYTAO, "+ 
		"		NS.TEN AS NGUOISUA,DH.NGAYSUA,DH.TRANGTHAI,ISNULL(DH.NGAYNHAP,'') AS NGAYCHOT,KH.DIENTHOAI,KH.TEN   AS KHTEN "+
		"	FROM DONHANGTRAVE DH INNER JOIN KHACHHANG KH ON KH.PK_SEQ=DH.KHACHHANG_FK    "+
		"		INNER JOIN NHANVIEN NT ON  DH.NGUOITAO=NT.PK_SEQ   "+
		"		INNER JOIN NHANVIEN NS ON NS.PK_SEQ=DH.NGUOISUA  "+
		" where dh.npp_fk='"+nppId+"' AND DH.DONHANG_FK IS NOT NULL ";

		if (tungay.length() > 0)
		{
			query = query + " and dh.NGAYNHAP >= '" + tungay + "'";
		}

		if (denngay.length() > 0)
		{
			query = query + " and dh.NGAYNHAP <= '" + denngay + "'";
		}

		if (trangthai.length() > 0)
		{
			query = query + " and dh.trangthai = '" + trangthai + "'";
		}
		return query;
	}

	private String Delete(String id,String nppId)
	{
		dbutils db = new dbutils();
		try
		{
			db.getConnection().setAutoCommit(false);
			String query =
			"DELETE FROM DONHANGTRAVE WHERE PK_SEQ='"+id+"' AND TRANGTHAI=1 ";
			if(db.updateReturnInt(query)!=1)
			{
				db.getConnection().rollback();
				return "Không thể xóa đơn hàng trả về "+query;
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
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return "";
	}
	
	private String Chot(String id,String nppId)
	{
		Utility util = new Utility();
		ITraDonHang tdhBean = new TraDonHang(id);
		tdhBean.init();
		tdhBean.duyetSptrave(id,nppId);
		return tdhBean.getMessage();
	
	}
}
