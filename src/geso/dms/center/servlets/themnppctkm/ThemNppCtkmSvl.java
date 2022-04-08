package geso.dms.center.servlets.themnppctkm;


import geso.dms.center.beans.themnppctkm.IThemNppCtkm;
import geso.dms.center.beans.themnppctkm.IThemNppCtkmList;
import geso.dms.center.beans.themnppctkm.imp.ThemNppCtkm;
import geso.dms.center.beans.themnppctkm.imp.ThemNppCtkmList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

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

@WebServlet("/ThemNppCtkmSvl")
public class ThemNppCtkmSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	public ThemNppCtkmSvl()
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
		
		//--- check user
	    String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
		if (view == null) {
			view = "";
		}
		String param = "";
		if(view.trim().length() > 0) param ="&view="+view;
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "ThemNppCtkmSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}
		 //--- check user 

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		out.println(userId);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

		IThemNppCtkmList obj = new ThemNppCtkmList();
		obj.setUserId(userId);

		String action = util.getAction(querystring);
		String khlId = util.getId(querystring);

		String msg = "";

		if (action.equals("delete"))
	    {
				obj.setMsg(Delete(khlId));
	    }else if(action.equals("chot"))
	    {
	    	obj.setMsg(Chot(khlId,userId));
	    }
		obj.init("");
		obj.setMsg(msg);
		session.setAttribute("obj", obj);

		String nextJSP = "/AHF/pages/Center/ThemNppCtkm.jsp";
		response.sendRedirect(nextJSP);
	}

	private String Chot(String ddhId, String userId)
	{
		dbutils db = new dbutils();
		try
		{
			String msg="";
			String query="";
			
			db.getConnection().setAutoCommit(false);
		
			
			query = 
			"		INSERT INTO CTKM_NPP(CTKM_FK,NPP_FK,CHON,NGUOITAO,NGUOISUA,NGAYTAO,NGAYSUA) "+
			"   SELECT distinct  ctkm_fk,npp_fk,1,'"+userId+"','"+userId+"','"+getDateTime()+"','"+getDateTime()+"'  " +
			"   FROM THEMNPPCTKM_NPP aa WHERE THEMNPPCTKM_FK='"+ddhId+"' and not exists (select 1 from CTKM_NPP where npp_fk=aa.npp_fk and ctkm_fk=aa.ctkm_Fk)   ";
			System.out.println("1.HD_HangMau"+query);
			if(!db.update(query))
			{
				System.out.println("loi 1.1");
				db.getConnection().rollback();
				return "Lỗi hệ thống vui lòng liên hệ "+query;
			}
			
			query=
					"update CTKM_NPP set CHON=1,NGUOISUA='"+userId+"',NgaySua='"+getDateTime()+"'  " +
					" from CTKM_NPP inner join THEMNPPCTKM_NPP THEM on THEM.npp_fk  =  CTKM_NPP.npp_fk  and THEM.ctkm_Fk  = CTKM_NPP.npp_fk  " +
					"where  THEMNPPCTKM_FK='"+ddhId+"'  ";
			
			System.out.println("1.HD_HangMau"+query);
			if(!db.update(query))
			{
				System.out.println("loi 1.2");
				db.getConnection().rollback();
				return "Lỗi hệ thống vui lòng liên hệ "+query;
			}
			
			query=
			"		insert into PHANBOKHUYENMAI(CTKM_FK,NPP_FK,NGANSACH,DASUDUNG,TINHTRANG) "+
			"   SELECT ctkm_fk,npp_fk,case when loaingansach=0 then 99999999999 else NganSach end ,0 ,0   " +
			"   FROM THEMNPPCTKM_NPP aa WHERE THEMNPPCTKM_FK='"+ddhId+"' and not exists (select 1 from PHANBOKHUYENMAI where npp_fk=aa.npp_fk and ctkm_fk=aa.ctkm_Fk)   ";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				System.out.println("loi 1.3");
				return "Lỗi hệ thống vui lòng liên hệ "+query;
			}
			
			query="Update ThemNppCtkm set Trangthai=1,NguoiSua='"+userId+"' where pk_seq='"+ddhId+"' and trangthai=0 ";
			if(!db.update(query))
			{
				System.out.println("loi 1.4");
				db.getConnection().rollback();
				return "Lỗi hệ thống vui lòng liên hệ "+query;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return msg;
		}catch(Exception er)
		{
			er.printStackTrace();
			try
			{
				db.getConnection().rollback();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			er.printStackTrace();
			return "Exeption "+er.getMessage()+" ";
		}
		finally
		{
			db.shutDown();
		}
	}

	private String Delete(String ddhId)
	{
		dbutils db = new dbutils();
		try
		{
			db.getConnection().setAutoCommit(false);
			String query = " DELETE FROM ThemNppCtkm_npp WHERE ThemNppCtkm_fk='"+ddhId+"'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Lỗi hệ thống vui lòng liên hệ "+query;
			}
			query=" DELETE FROM  ThemNppCtkm WHERE PK_SEQ='"+ddhId+"' AND TRANGTHAI=0 ";
			if(db.updateReturnInt(query)!=1)
			{
				db.getConnection().rollback();
				return "Lỗi hệ thống vui lòng liên hệ "+query;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return "";
		}catch(Exception er)
		{
			er.printStackTrace();
			try
			{
				db.getConnection().rollback();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			return "Exeption "+er.getMessage()+" ";
		}
		finally
		{
			db.shutDown();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		this.out = response.getWriter();

		HttpSession session = request.getSession();

		out = response.getWriter();
		Utility util = new Utility();
		
		//--- check user
	    String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
		if (view == null) {
			view = "";
		}
		String param = "";
		if(view.trim().length() > 0) param ="&view="+view;
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "ThemNppCtkmSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}
		 //--- check user 

		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

		IThemNppCtkmList obj;

		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		if (action == null)
		{
			action = "";
		}

		if(action.equals("new"))
		{
			IThemNppCtkm khl = new ThemNppCtkm();
			khl.setUserId(userId);
			khl.createRs();

			session.setAttribute("fileBean", khl);
			session.setAttribute("userId", userId);

			response.sendRedirect("/AHF/pages/Center/ThemNppCtkmNew.jsp");
		}
		else
		{
			obj = new ThemNppCtkmList();
			obj.setUserId(userId);

			String search = getSearchQuery(request, obj);

			obj.setUserId(userId);
			obj.init(search);

			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);

			response.sendRedirect("/AHF/pages/Center/ThemNppCtkm.jsp");
		}
	}

	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	
	private String getSearchQuery(HttpServletRequest request, IThemNppCtkmList obj)
	{

		String Tungay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Tungay"));
		if(Tungay == null)
			Tungay = "";
		obj.setTungay(Tungay);

		String Denngay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Denngay"));
		if(Denngay == null)
			Denngay = "";
		obj.setDenngay(Denngay);
		
		String trangthai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"));
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);

		String  sql = 
				"		select a.pk_Seq ,a.GhiChu,nt.TEN as NguoiTao,ns.TEN as NguoiSua,a.NgayTao,a.NgaySua,a.TrangThai "+    
						"			, STUFF   "+      
						"		(        "+
						"			(      "+
						"				select DISTINCT TOP 100 PERCENT ' , ' + chd.Scheme  "+     
						"				from ThemNppCtkm_Npp  chd   "+
						"				where chd.ThemNppCtkm_FK=a.pk_Seq  "+  
						"				ORDER BY ' , ' + chd.Scheme  "+      
						"				FOR XML PATH('')    "+    
						"			 ), 1, 2, ''       "+
						"		) + ' '  AS HoaDon    "+
						"		from ThemNppCtkm a inner join NHANVIEN nt on nt.PK_SEQ=a.NguoiTao  "+   
						"		inner join NHANVIEN ns on ns.PK_SEQ=a.NguoiSua  "+    
						"		where 1=1  "  ;

		obj.getDataSearch().clear();
		
		if(Tungay.length() > 0) {
//			sql += " and a.NgayTao >='" + Tungay + "' ";
			
			sql = sql + "\n and a.NgayTao >= ? ";	
			obj.getDataSearch().add( "" + Tungay + "" );
		}

		if(Denngay.length() > 0) {
//			sql += " and a.NgaySua  <= '%" + Denngay + "%' ";
			
			sql = sql + "\n and a.NgaySua  <= ? ";	
			obj.getDataSearch().add( "" + Denngay + "" );
		}

		if(trangthai.length() > 0) {
//			sql += " and a.trangthai like N'%" + trangthai + "%' ";
		
			sql = sql + "\n and a.trangthai like ? ";	
			obj.getDataSearch().add( "%" + trangthai + "%" );
		}

		return sql;
	}
	
}
