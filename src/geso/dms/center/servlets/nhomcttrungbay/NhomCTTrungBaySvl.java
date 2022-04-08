package geso.dms.center.servlets.nhomcttrungbay;


import geso.dms.center.beans.nhomcttrungbay.INhomCTTrungBay;
import geso.dms.center.beans.nhomcttrungbay.INhomCTTrungBayList;
import geso.dms.center.beans.nhomcttrungbay.imp.NhomCTTrungBay;
import geso.dms.center.beans.nhomcttrungbay.imp.NhomCTTrungBayList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/NhomCTTrungBaySvl")
public class NhomCTTrungBaySvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	public NhomCTTrungBaySvl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		INhomCTTrungBayList obj;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		this.out = response.getWriter();

		HttpSession session = request.getSession();

		Utility util = new Utility();
		out = response.getWriter();
		obj = new NhomCTTrungBayList();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		out.println(userId);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

		String action = util.getAction(querystring);
		out.println(action);

		String nppId = util.getId(querystring);
		if(action!=null)
		{
			if (action.equals("delete"))
			{
				obj.setMsg(Delete(nppId));
			}
			obj.setUserId(userId);
			obj.init("");
			session.setAttribute("obj", obj);
			String nextJSP = "/AHF/pages/Center/NhomCTTrungBay.jsp";
			response.sendRedirect(nextJSP);	
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		INhomCTTrungBayList obj = new NhomCTTrungBayList();

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");


		Utility util = new Utility();

		HttpSession session = request.getSession();
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

		String ten = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppTen")));
		if (ten == null)
			ten = "";
		obj.setTen(ten);

		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		if (action == null)
		{
			action = "";
		}
		String nextJSP = "";

		if (action.equals("new"))
		{
			INhomCTTrungBay ncttbBean = (INhomCTTrungBay) new NhomCTTrungBay();
			ncttbBean.setUserId(userId);
			ncttbBean.createRs();
			session.setAttribute("ncttbBean", ncttbBean);
			nextJSP = "/AHF/pages/Center/NhomCTTrungBayNew.jsp";
			session.setAttribute("userId", userId);
			response.sendRedirect(nextJSP);

		} 
		else if (action.equals("search"))
		{
			obj.setUserId(userId);
			String search = getSearchQuery(request,obj);
			obj.init(search);
			nextJSP = "/AHF/pages/Center/NhomCTTrungBay.jsp";
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			response.sendRedirect(nextJSP);
		} 
		else if (action.equals("view") || action.equals("next") || action.equals("prev"))
		{

			obj.setUserId(userId);
			obj.setNxtApprSplitting(Integer.parseInt(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting")))));
			obj.init("");
			obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
			session.setAttribute("obj", obj);
			nextJSP = "/AHF/pages/Center/NhomCTTrungBay.jsp";
			response.sendRedirect(nextJSP);
		}
	}

	private String Delete(String id)
	{
		dbutils db = new dbutils();
		try
		{
			String query=
			"	SELECT COUNT(*)AS SODONG  FROM CTTRUNGBAY  WHERE NHOMCTTB_FK= "+id+"";
			ResultSet rs=db.get(query);
			int sodong=0;
			while(rs.next())
			{
				sodong=rs.getInt(1);
			}
			if(sodong>0)
			{
				return "Nhóm CT đã được sử dụng !";
			}
			if(rs!=null)rs.close();
			db.getConnection().setAutoCommit(false);
			query="delete from  NHOMCTTRUNGBAY WHERE PK_SEQ='"+id+"'";
			if(!db.update(query))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return "Lỗi hệ thống "+query;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
		} catch (Exception e)
		{ 
			e.printStackTrace();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return "Lỗi hệ thống "+e.getMessage();
		}
		db.shutDown();
		return "";
	}

	private String getSearchQuery(HttpServletRequest request,INhomCTTrungBayList obj)
	{
		//INhomCTTrungBayList obj = new NhomCTTrungBayList();

		Utility util = new Utility();

		String ma = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ma")));
		if (ma == null)
			ma = "";
		obj.setMa(ma);

	

		String ten = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ten")));
		if (ten == null)
			ten = "";
		obj.setTen(ten);
		if(ten == "" && ma == "")
			return "";
		else
		{
		String query = "SELECT a.pk_seq as id,a.ma,a.ten,nt.TEN as NguoiTao,a.ngaytao,ns.TEN as NguoiSua,a.ngaysua "+
				"FROM NHOMCTTRUNGBAY a inner join NHANVIEN nt on nt.PK_SEQ=a.nguoitao "+
				"	inner join NHANVIEN ns on ns.PK_SEQ=a.nguoisua WHERE ";
		if(ma.length()>0)
				query+=" a.MA LIKE N'%"+ma+"%'";
		if(ten.length()>0 && ma.length()>0)
				query+="or a.TEN LIKE N'%"+ten+"%'";
		else if(ten.length()>0)	
				query+=" a.TEN LIKE N'%"+ten+"%'";
		System.out.println(ma + " " + ma.length() +" "+ten+ " "+ten.length()+" "+query);
		return query;
		}
	}
}

