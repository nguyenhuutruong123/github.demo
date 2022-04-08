package geso.dms.center.servlets.phuongxa;

import geso.dms.center.beans.phuongxa.*;
import geso.dms.center.beans.phuongxa.imp.Phuongxa;
import geso.dms.center.beans.phuongxa.imp.PhuongxaList;
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


public class PhuongxaSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
	
    public PhuongxaSvl()
    {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		IPhuongxaList obj;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    
	    obj = new PhuongxaList();
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
		if (Utility.CheckRuleUser( session,  request, response, "PhuongxaSvl", param, Utility.XEM ))
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
				
		String nextJSP = "/AHF/pages/Center/PhuongXa.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		IPhuongxaList obj = new PhuongxaList();
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
		if (Utility.CheckRuleUser( session,  request, response, "PhuongxaSvl", param, Utility.XEM ))
		{
			//obj.DBClose();
			Utility.RedireactLogin(session, request, response);
		}
	    
	    if (action.equals("new")) {
	    	IPhuongxa kvBean = (IPhuongxa) new Phuongxa("");
	    	kvBean.setUserId(userId);
	    	session.setAttribute("kvBean", kvBean);    		
    		String nextJSP = "/AHF/pages/Center/PhuongXaNew.jsp";
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
    		response.sendRedirect("/AHF/pages/Center/PhuongXa.jsp");	   	
	    }
	    else if (action.equals("view") || action.equals("next") || action.equals("prev")) {
			String search = getSearchQuery(request, obj);
			obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
			obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
			obj.setUserId(userId);
			obj.init(search);
			session.setAttribute("obj", obj);
			response.sendRedirect("/AHF/pages/Center/PhuongXa.jsp");
		}
	    else {	    
	    	String search = "";
	    	obj.init(search);
			obj.setUserId(userId);			
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
    		session.setAttribute("abc", search);	    		
    		response.sendRedirect("/AHF/pages/Center/PhuongXa.jsp");	   	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request,IPhuongxaList obj)
	{
		obj.getDataSearch().clear();
		Utility util = new Utility();

		String ttId = Utility.antiSQLInspection(request.getParameter("tinhthanh"));
		if (ttId == null)
			ttId = "";
		obj.setTinhthanhId(ttId);

		String qhId = Utility.antiSQLInspection(request.getParameter("quanhuyen"));
		if (qhId == null)
			qhId = "";
		obj.setQuanhuyenId(qhId);

		String trangthai = Utility.antiSQLInspection(request.getParameter("TrangThai"));   	
		if (trangthai == null)
			trangthai = "";    	

		if (trangthai.equals("2"))
			trangthai = "";
		obj.setTrangthai(trangthai);

		String query = "\n SELECT PX.pk_Seq, PX.Ten AS TENPX, TT.TEN AS TENTT, QH.TEN AS TENQH, NT.TEN AS NGUOITAO, " +
		"\n NS.TEN AS NGUOISUA, PX.NGAYTAO, PX.NGAYSUA, PX.TRANGTHAI " + 
		"\n FROM PHUONGXA PX " + 
		"\n INNER JOIN TINHTHANH TT ON TT.PK_SEQ = PX.TinhThanh_FK " + 
		"\n INNER JOIN QUANHUYEN QH ON QH.PK_SEQ = PX.QuanHuyen_FK " + 
		"\n INNER JOIN NHANVIEN NT ON NT.PK_SEQ = PX.NGUOITAO " + 
		"\n INNER JOIN NHANVIEN NS ON NS.PK_SEQ = PX.NGUOISUA " + 
		"\n where 1 = 1 ";

		if (ttId.length() > 0) {
			query += " and tt.pk_seq = ? ";
			obj.getDataSearch().add(ttId);
		}

		if (qhId.length() > 0) {
			query += " and qh.pk_seq = ? ";
			obj.getDataSearch().add(qhId);
		}

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
		
		IPhuongxaList obj = new PhuongxaList();
		
		dbutils db = new dbutils();
		try {
		String sql ="SELECT COUNT(PHUONGXA_FK) AS NUM FROM KHACHHANG WHERE PHUONGXA_FK = '"+ id +"' ";
		
		if(!Utility.KiemTra_PK_SEQ_HopLe( id, "PHUONGXA", db))
		{
			
			db.getConnection().rollback();
			db.getConnection().setAutoCommit(true);
			obj.setMsg("Định danh không hợp lệ!");
		}
		if(kiemtra(sql))
		{
			db.update("DELETE FROM PHUONGXA WHERE PK_SEQ = '" + id + "'");
			db.shutDown();
		}
		else
			obj.setMsg("Thông tin phường xã này đã được khai báo trong dữ liệu khách hàng. Không thể xóa !");
	
	
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
}
