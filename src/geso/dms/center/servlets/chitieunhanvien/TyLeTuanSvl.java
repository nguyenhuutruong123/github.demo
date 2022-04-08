package geso.htp.center.servlets.chitieunhanvien;

import geso.htp.center.beans.chitieunhanvien.ITyLeTuan;
import geso.htp.center.beans.chitieunhanvien.imp.TyLeTuan;
import geso.htp.center.util.Utility;
import geso.htp.distributor.db.sql.dbutils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TyLeTuanSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TyLeTuanSvl() {
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
		// TODO Auto-generated method stub
		Utility util = new Utility();

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		ITyLeTuan obj = new TyLeTuan();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);  
		obj.setUserId(userId);
		if (userId.length()==0)
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		session.setAttribute("userId", userId); 
		session.setAttribute("userTen", gettenuser(userId));
		String action = util.getAction(querystring);
		//out.println(action);   
		String idlist = util.getId(querystring); 
		try{
			obj.setID(idlist);
		}catch(Exception er){};

		String nextJSP = "/HTP/pages/Center/TyLeTuan.jsp";//default

		String loaict="1";
		if(action.equals("delete")){
			obj.Delete();
			obj.setListChiTieu("");
			session.setAttribute("obj", obj);
		}else if(action.equals("chot")){

			obj.Chot();
			obj.setListChiTieu("");
			session.setAttribute("obj", obj);
		}
		else if(action.equals("new")){// circumstance add new 
			nextJSP= "/HTP/pages/Center/TyLeTuanUpdate.jsp";
			
		}
		else if(action.equals("unchot")){// circumstance add new 
			obj.setID(idlist);			
			obj.UnChot();							
			obj.setListChiTieu("");
			session.setAttribute("obj", obj);
		}
		else{
			session.setAttribute("thang",0);
			session.setAttribute("nam",0);
			obj.setListChiTieu("");
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

		ITyLeTuan chitieu=new TyLeTuan();
		chitieu.setUserId(userId);
		HttpSession session=request.getSession();

		session.setAttribute("nam",Integer.parseInt(namtk));
		session.setAttribute("thang",Integer.parseInt(thangtk));

		Utility Ult = new Utility(); 
		if(action.equals("search"))
		{
			String  sql_getdata="";

			sql_getdata="SELECT   c.trangthai,C.PK_SEQ, C.THANG, C.NAM, C.DIENGIAI, C.NGAYTAO, C.NGAYSUA,NT.TEN AS NGUOITAO, "+ 
			"NS.TEN AS NGUOISUA FROM chitieunhanvien AS C INNER JOIN "+
			"dbo.NHANVIEN AS NT ON C.NGUOITAO = NT.PK_SEQ INNER JOIN dbo.NHANVIEN AS NS ON C.NGUOISUA = NS.PK_SEQ " +
			"where 1=1 and c.trangthai != 2";

			String where ="";
			if(!thangtk.equals("0")){
				where=" and C.THANG="+ thangtk + " ";	
			}
			if(!namtk.equals("0")){
				where=where + "and C.NAM="+namtk +" ";
			}
			if(where !=""){//if have search condition
				sql_getdata=sql_getdata+ where;	
			}
			chitieu.setListChiTieu(sql_getdata);
			session.setAttribute("obj", chitieu);
			String nextJSP = "/HTP/pages/Center/TyLeTuan.jsp";//default
			response.sendRedirect(nextJSP);
		}
	}

}
