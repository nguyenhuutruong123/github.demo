package geso.dms.center.servlets.thuongdauthung;

import geso.dms.center.beans.thuongdauthung.IThuongdauthung;
import geso.dms.center.beans.thuongdauthung.imp.Thuongdauthung;
import geso.dms.center.util.Utility;
import geso.dms.distributor.db.sql.dbutils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ThuongdauthungSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ThuongdauthungSvl() {
		super();
		// TODO Auto-generated constructor stub
	}
	private String gettenuser(String userId_){
		dbutils db=new dbutils();
		String sql_getnam="select ten from nhanvien where pk_seq="+ userId_;
		ResultSet rs_tenuser=db.get(sql_getnam);
		if(rs_tenuser!= null){
			try{
				while(rs_tenuser.next()){
					return rs_tenuser.getString("ten");
				}
			}catch(Exception er){
				return "";
			}
		}
		return "";
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Utility util = new Utility();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		IThuongdauthung obj = new Thuongdauthung();
		
		String view = Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
		if (view == null) 
			view = "";

		String param = "";
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		obj.setUserId(userId);
		
		if (userId.length()==0)
			userId = Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		
		if (view.trim().length() > 0) {
			param ="&view="+view;
		}
		
		if ( Utility.CheckSessionUser( session,  request, response)) {
			Utility.RedireactLogin(session, request, response);
		}
		
		if( Utility.CheckRuleUser( session,  request, response, "ThuongdauthungSvl", param, Utility.XEM )) {
			Utility.RedireactLogin(session, request, response);
		}	

		session.setAttribute("userId", userId); 
		session.setAttribute("userTen", gettenuser(userId));
		String action = util.getAction(querystring); 
		String idlist = util.getId(querystring); 
		
		try{
			obj.setID(Double.parseDouble(idlist));
		}
		catch(Exception er)
		{
			er.printStackTrace();
		}

		String nextJSP = "/AHF/pages/Center/Thuongdauthung.jsp";

		if(action.equals("delete")){
			System.out.println("heheheheeeeeeeeeeeeeeee        delete");
			
			if(view.trim().length() > 0) param = "&view=" + view;
			int[] quyen = Utility.Getquyen("ThuongdauthungSvl", param, userId);
			if(quyen[Utility.XOA] != 1) {
				return;
			}
			
			obj.DeleteLuongKhac();
			obj.setLuongkhacRs("");
			session.setAttribute("obj", obj);
		}else if(action.equals("chot")){
			System.out.println("heheheheeeeeeeeeeeeeeee");
			obj = new Thuongdauthung(idlist);	
			obj.chotLuongKhac();
			obj.setLuongkhacRs("");
			session.setAttribute("obj", obj);
		}
		else if(action.equals("new")){// circumstance add new 
			nextJSP= "/AHF/pages/Center/ThuongdauthungUpdate.jsp";
		}
		else if(action.equals("unchot")){// circumstance add new 
			obj.setID(Double.parseDouble(idlist));			
			//obj.UnChotChiTieu_Sec();	
			obj.UnchotLuongKhac();
			obj.setLuongkhacRs("");
			session.setAttribute("obj", obj);
		}
		else{
			session.setAttribute("thang",0);
			session.setAttribute("nam",0);
			obj.setLuongkhacRs("");
			session.setAttribute("obj", obj);
		}
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Utility util = new Utility();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		String username=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userTen")));

		String thangtk=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thang")));
		String namtk=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nam")));
		String action =geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));

		IThuongdauthung thuongdauthung =new Thuongdauthung();
		thuongdauthung.setUserId(userId);
		HttpSession session=request.getSession();
		
		String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
		if (view == null) 
			view = "";
		thuongdauthung.setView(view);

		String param = "";
		if(view.trim().length() > 0) param ="&view="+view;
		if ( Utility.CheckSessionUser( session,  request, response)) {
			thuongdauthung.DbClose();
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "ThuongdauthungSvl", param, Utility.XEM )) {
			thuongdauthung.DbClose();
			Utility.RedireactLogin(session, request, response);
		}

		session.setAttribute("nam",Integer.parseInt(namtk));
		session.setAttribute("thang",Integer.parseInt(thangtk));

		Utility Ult = new Utility(); 
		if(action.equals("search"))
		{
			String  sql_getdata="";

			sql_getdata="SELECT    c.trangthai,C.PK_SEQ, C.tungay, C.denngay, C.DIENGIAI,  C.NGAYTAO, C.NGAYSUA,NT.TEN AS NGUOITAO "+ 
			",NS.TEN AS NGUOISUA FROM thuongdauthung AS C INNER JOIN "+
			"dbo.NHANVIEN AS NT ON C.NGUOITAO = NT.PK_SEQ INNER JOIN dbo.NHANVIEN AS NS ON C.NGUOISUA = NS.PK_SEQ " +
			"where 1=1 ";

			String where ="";
			if(!thangtk.equals("0")){
				where=" and month(C.denngay)=? ";	
				thuongdauthung.getDataSearch().add(thangtk);
			}
			if(!namtk.equals("0")){
				where=where + "and year(C.denngay)=? ";
				thuongdauthung.getDataSearch().add(namtk);
			}

			if(where !=""){//if have search condition
				sql_getdata=sql_getdata+ where;	
			}
			sql_getdata += "\n order by  C.PK_SEQ desc";

			thuongdauthung.setLuongkhacRs(sql_getdata);
			session.setAttribute("obj", thuongdauthung);
			String nextJSP = "/AHF/pages/Center/Thuongdauthung.jsp";//default
			response.sendRedirect(nextJSP);
		}
		else if(action.equals("new"))
		{
			IThuongdauthung obj = new Thuongdauthung();
			obj.setUserId(userId);
			session.setAttribute("userId",userId);
			session.setAttribute("userTen", username);
			session.setAttribute("obj", thuongdauthung);
			session.setAttribute("check", "0");
			String	nextJSP= "/AHF/pages/Center/ThuongdauthungUpdate.jsp";
			response.sendRedirect(nextJSP);

		}
	}

}
