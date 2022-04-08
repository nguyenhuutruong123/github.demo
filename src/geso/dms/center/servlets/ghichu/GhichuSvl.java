package geso.dms.center.servlets.ghichu;

import geso.dms.center.beans.ghichu.*;
import geso.dms.center.beans.ghichu.imp.*;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class GhichuSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
	
    public GhichuSvl()
    {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		IGhichuList obj;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    
	    obj = new GhichuList();
	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = Utility.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String kvId = util.getId(querystring);

	    String param = "";
		
		if (Utility.CheckSessionUser( session,  request, response))
		{
			//obj.DBClose();
			Utility.RedireactLogin(session, request, response);
		}
		if (Utility.CheckRuleUser( session,  request, response, "GhichuSvl", param, Utility.XEM ))
		{
			//obj.DBClose();
			Utility.RedireactLogin(session, request, response);
		}
		
	    if (action.equals("delete")) {	   		  	    	
	    	Delete(kvId);
	    	out.print(kvId);
	    }
	   	
	   
	    obj.setUserId(userId);
	    obj.init("");
		session.setAttribute("obj", obj);
				
		String nextJSP = "/AHF/pages/Center/GhiChu.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		IGhichuList obj = new GhichuList();
		Utility util = new Utility();
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out = response.getWriter();
	    
		HttpSession session = request.getSession();
	    String userId = Utility.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null) {
	    	action = "";
	    }
	    out.println(action); 
	       
	    String param = "";
		
		if (Utility.CheckSessionUser( session,  request, response))
		{
			//obj.DBClose();
			Utility.RedireactLogin(session, request, response);
		}
		if (Utility.CheckRuleUser( session,  request, response, "GhichuSvl", param, Utility.XEM ))
		{
			//obj.DBClose();
			Utility.RedireactLogin(session, request, response);
		}
	    
	    if (action.equals("new")) {
	    	IGhichu kvBean = (IGhichu) new Ghichu("");
	    	kvBean.setUserId(userId);
	    	session.setAttribute("kvBean", kvBean);    		
    		String nextJSP = "/AHF/pages/Center/GhiChuNew.jsp";
    		response.sendRedirect(nextJSP);    		
	    }	    
	    else if (action.equals("search")) {
	    	String search = getSearchQuery(request, obj);	    	
	    	obj.init(search);
			obj.setUserId(userId);			
			obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
			obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
    		session.setAttribute("abc", search);	    		
    		response.sendRedirect("/AHF/pages/Center/GhiChu.jsp");	   	
	    }
	    else if (action.equals("view") || action.equals("next") || action.equals("prev")) {
			String search = getSearchQuery(request, obj);
			obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
			obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
			obj.setUserId(userId);
			obj.init(search);
			session.setAttribute("obj", obj);
			response.sendRedirect("/AHF/pages/Center/GhiChu.jsp");
		}
	    else {	    
	    	String search = "";
	    	obj.init(search);
			obj.setUserId(userId);			
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
    		session.setAttribute("abc", search);	    		
    		response.sendRedirect("/AHF/pages/Center/GhiChu.jsp");	   	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request,IGhichuList obj)
	{
		obj.getDataSearch().clear();
		Utility util = new Utility();

		String trangthai = Utility.antiSQLInspection(request.getParameter("TrangThai"));   	
		if (trangthai == null)
			trangthai = "";    	

		if (trangthai.equals("2"))
			trangthai = "";
		obj.setTrangthai(trangthai);

		String query = "\n SELECT PX.pk_Seq, PX.DIENGIAI AS TENPX, NT.TEN AS NGUOITAO, " +
		"\n NS.TEN AS NGUOISUA, PX.NGAYTAO, PX.NGAYSUA, PX.TRANGTHAI " + 
		"\n FROM GHICHU PX " + 
		"\n INNER JOIN NHANVIEN NT ON NT.PK_SEQ = PX.NGUOITAO " + 
		"\n INNER JOIN NHANVIEN NS ON NS.PK_SEQ = PX.NGUOISUA " + 
		"\n where 1 = 1 ";

		if(trangthai.length() > 0) {
			query += " and px.trangthai = ? ";	
			obj.getDataSearch().add(trangthai);
		}

		System.out.println("Search query: ");
		dbutils.viewQuery(query, obj.getDataSearch());
		
		return query;
	}	
	
	boolean kiemtra(String sql)
	{
		dbutils db = new dbutils();
		ResultSet rs = db.get(sql);
		try {
			while(rs.next())
			{ 
				if(rs.getString("num").equals("0"))
				return true;
			}
		} catch(Exception e) {	
			e.printStackTrace();
		}
		
		return false;
	}

	private void Delete(String id)
	{	
		
		IGhichuList obj = new GhichuList();
		
		dbutils db = new dbutils();
		try {
		String sql = " SELECT COUNT(T.PK_SEQ) NUM FROM GHICHU T WHERE T.PK_SEQ = '"+ id +"' AND EXISTS ( SELECT 1 FROM DONHANG DH WHERE DH.GHICHU_FK IS NOT NULL AND DH.TRANGTHAI <> '2' AND DH.GHICHU_FK = T.PK_SEQ ) ";
		if(!Utility.KiemTra_PK_SEQ_HopLe( id, "GHICHU", db))
		{
			db.getConnection().rollback();
			db.getConnection().setAutoCommit(true);
			obj.setMsg("Định danh không hợp lệ!");
		}
		if(kiemtra(sql))
		{
			db.update("DELETE FROM GHICHU WHERE PK_SEQ = '" + id + "'");
			db.shutDown();
		}
		else
			obj.setMsg("Thông tin ghi chú này đã được khai báo trong đơn hàng. Không thể xóa !");
	
	
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
}
