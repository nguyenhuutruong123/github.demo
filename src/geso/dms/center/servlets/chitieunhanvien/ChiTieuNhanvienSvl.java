package geso.dms.center.servlets.chitieunhanvien;

import geso.dms.center.beans.chitieunhanvien.IChiTieuNhanvien;
import geso.dms.center.beans.chitieunhanvien.imp.ChiTieuNhanvien;
import geso.dms.center.util.Utility;
import geso.dms.distributor.db.sql.dbutils;
import java.io.IOException;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChiTieuNhanvienSvl extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public ChiTieuNhanvienSvl() {
		super();
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Utility util = new Utility();

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		//--- check user
//		Utility util = new Utility();
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
		if( Utility.CheckRuleUser( session,  request, response, "ChiTieuNhanvienSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}
		 //--- check user 
		
		ChiTieuNhanvien obj = new ChiTieuNhanvien();

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

		String nextJSP = "/AHF/pages/Center/ChiTieuNhanvien.jsp";//default

		String loaict = "1";
		if(action.equals("delete"))
		{
			obj.DeleteChiTieuLuongThuong();
			obj.setListChiTieu("");
			session.setAttribute("obj", obj);
		}
		else if(action.equals("chot"))
		{

			obj.ChotChiTieuLuongThuong();
			obj.setListChiTieu("");
			session.setAttribute("obj", obj);
		}
		else if(action.equals("new")){// circumstance add new 
			nextJSP= "/AHF/pages/Center/ChiTieuNhanvienUpdate.jsp";
			
		}
		else if(action.equals("unchot")){// circumstance add new 
			obj.setID(idlist);			
			obj.UnChotChiTieuLuongThuong();							
			obj.setListChiTieu("");
			session.setAttribute("obj", obj);
		}
		else{
			session.setAttribute("thang",0);
			session.setAttribute("nam",0);
			obj.setListChiTieu("");
			session.setAttribute("obj", obj);
		}
		System.out.println("page: "+nextJSP);
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Utility util = new Utility();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		String username=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userTen")));

		String thangtk=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thang")));
		String namtk=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nam")));
		String action =geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		
		HttpSession session=request.getSession();
		//--- check user
//		Utility util = new Utility();
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
		if( Utility.CheckRuleUser( session,  request, response, "ChiTieuNhanvienSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}
		 //--- check user

		IChiTieuNhanvien chitieu=new ChiTieuNhanvien();
		chitieu.setUserId(userId);

		session.setAttribute("nam",Integer.parseInt(namtk));
		session.setAttribute("thang",Integer.parseInt(thangtk));

		Utility Ult = new Utility(); 
		if(action.equals("search"))
		{
			String  sql_getdata="";

			sql_getdata="SELECT   c.trangthai,C.PK_SEQ, C.THANG, C.NAM, C.DIENGIAI, C.NGAYTAO, C.NGAYSUA,NT.TEN AS NGUOITAO, "+ 
			"NS.TEN AS NGUOISUA FROM ChiTieuNhanVien AS C INNER JOIN "+
			"dbo.NHANVIEN AS NT ON C.NGUOITAO = NT.PK_SEQ INNER JOIN dbo.NHANVIEN AS NS ON C.NGUOISUA = NS.PK_SEQ " +
			"where 1=1 ";

			chitieu.getDataSearch().clear();
			String where ="";
			if(!thangtk.equals("0")){
//				where=" and C.THANG="+ thangtk + " ";	
				
				where = "\n and C.THANG= ? ";	
				chitieu.getDataSearch().add( "" + thangtk + "" );
			}
			if(!namtk.equals("0")){
//				where=where + "and C.NAM="+namtk +" ";
				
				where = "\n and C.NAM= ? ";	
				chitieu.getDataSearch().add( "" + namtk + "" );
			}
			if(where !=""){//if have search condition
				sql_getdata=sql_getdata+ where;	
			}
            
			chitieu.setListChiTieu(sql_getdata);
			session.setAttribute("obj", chitieu);
			String nextJSP = "/AHF/pages/Center/ChiTieuNhanvien.jsp";
			response.sendRedirect(nextJSP);
		}
		else if(action.equals("new"))
		{
			IChiTieuNhanvien obj = new ChiTieuNhanvien();
			obj.setID("");
			obj.setIsDisplay("0");
			obj.setUserId(userId);
			session.setAttribute("userId",userId);
			session.setAttribute("userTen", username);
			session.setAttribute("obj", chitieu);
			session.setAttribute("check", "0");
			String	nextJSP= "/AHF/pages/Center/ChiTieuNhanvienUpdate.jsp";
			response.sendRedirect(nextJSP);
		}
	}

}
