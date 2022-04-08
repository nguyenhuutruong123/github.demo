package geso.dms.distributor.servlets.xuatkho;

import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.xuatkho.IXuatKho;
import geso.dms.distributor.beans.xuatkho.IXuatKhoList;
import geso.dms.distributor.beans.xuatkho.imp.XuatKho;
import geso.dms.distributor.beans.xuatkho.imp.XuatKhoList;
import geso.dms.distributor.db.sql.dbutils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/XuatKhoSvl")
public class XuatKhoSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public XuatKhoSvl()
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
			response.sendRedirect("/TheGioiNuocHoa/index.jsp");
		} else
		{

			IXuatKhoList obj;

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
				obj = new XuatKhoList();
				if (action.equals("delete"))
				{
					obj.setMsg( Delete(ddhId,nppId));
				}else if(action.equals("chot"))
				{
					obj.setMsg( Chot(ddhId));
				}
				obj.setUserId(userId);
				obj.createDdhlist("");
				session.setAttribute("obj", obj);
				String nextJSP = "/TheGioiNuocHoa/pages/Distributor/XuatKho.jsp";
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
			response.sendRedirect("/TheGioiNuocHoa/index.jsp");
		} else
		{
			IXuatKhoList obj;

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
				obj = new XuatKhoList();

				String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
				if (action == null)
				{
					action = "";
				}

				if (action.equals("new"))
				{

					IXuatKho dnhBean = (IXuatKho) new XuatKho();
					dnhBean.setUserId(userId);
					session.setAttribute("xkBean", dnhBean);
					dnhBean.createRs();
					String nextJSP = "/TheGioiNuocHoa/pages/Distributor/XuatKhoNew.jsp";
					response.sendRedirect(nextJSP);
				} 			
				else if (action.equals("view") || action.equals("next") || action.equals("prev"))
				{

					String nppId = "";
					geso.dms.distributor.util.Utility dutil = new geso.dms.distributor.util.Utility();
					obj.setNxtApprSplitting(Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
			    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
					nppId = dutil.getIdNhapp(userId);
					String search = getSearchQuery(request, util, nppId, obj);
					obj.setUserId(userId);
					session.setAttribute("userId", userId);
					obj.createDdhlist(search);
					session.setAttribute("obj", obj);					
					String nextJSP = "/TheGioiNuocHoa/pages/Distributor/XuatKho.jsp";
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

					String nextJSP = "/TheGioiNuocHoa/pages/Distributor/XuatKho.jsp";
					System.out.print(search);
					response.sendRedirect(nextJSP);
				}
			}
		}
	}

	private String getSearchQuery(HttpServletRequest request, Utility util, String nppId, IXuatKhoList obj)
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
				"	select dh.pk_seq as nhaphangId,dh.NgayXuat ,nt.TEN as NguoiTao,dh.ngaytao,ns.TEN as NguoiSua,dh.ngaysua,dh.TrangThai "+
						"	from XuatKho dh inner join NHANVIEN nt on  dh.NguoiTao=nt.PK_SEQ "+
						"		inner join NHANVIEN ns on ns.PK_SEQ=dh.NguoiSua "+
						" where dh.npp_fk='"+nppId+"' ";

		if (tungay.length() > 0)
		{
			query = query + " and dh.NgayNhap >= '" + tungay + "'";
		}

		if (denngay.length() > 0)
		{
			query = query + " and dh.NgayNhap <= '" + denngay + "'";
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
			Utility util = new Utility();
			if(util.KiemKeChuaDuyet(nppId))
			{
				return "Tồn tại kiểm kê chưa duyệt,vui lòng liên hệ trung tâm để giải quyết!";
			}			
			
			db.getConnection().setAutoCommit(false);
			String query =
			"		UPDATE KHO SET BOOKED=BOOKED-CKSP.SOLUONG,AVAILABLE =AVAILABLE +CKSP.SOLUONG "+
			"			FROM XUATKHO CK INNER JOIN XUATKHO_SANPHAM CKSP ON CKSP.XUATKHO_FK=CK.PK_sEQ "+
			"		INNER JOIN NHAPP_KHO KHO ON KHO.NPP_FK=CK.NPP_FK AND KHO.KBH_FK=CK.KBH_FK AND KHO.SANPHAM_FK =CKSP.SANPHAM_FK "+
			"		AND KHO.KHO_FK=CK.KHO_FK " +
			"      WHERE CK.PK_SEQ='"+id+"'      ";
			if(db.updateReturnInt(query)<=0)
			{
				db.getConnection().rollback();
				return "Không thể xóa chuyển kho "+query;
			}
				
			query="update  XuatKho set TrangThai=3  where pk_seq='"+id+"' and trangthai=0 ";
			if(db.updateReturnInt(query)<=0)
			{
				db.getConnection().rollback();
				return "Không thể xóa chuyển kho "+query;
			}	
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return "";
		} catch (Exception e)
		{
			try
			{
				db.getConnection().rollback();
				return "Không thể xóa chuyển kho ";
			} catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}
		return "";
	}
	
	private String Chot(String id)
	{
		dbutils db = new dbutils();
		try
		{
			Utility util = new Utility();
			if(util.IsKiemKho()>0)
			{
				return "Bạn phải duyệt kiểm kho TT trước khi thực hiện nghiệp vụ";
			}
			String	query="update  XuatKho set TrangThai=1  where pk_seq='"+id+"' and trangthai=0 ";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Không thể chốt xuất kho "+query;
			}	
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return "";
			} catch (Exception e)
			{
				e.printStackTrace();
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return "Không thể chốt chuyển kho ";
			}
		finally
		{
			db.shutDown();
		}
	}
	

}
