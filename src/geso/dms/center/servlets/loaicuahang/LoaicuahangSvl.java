package geso.dms.center.servlets.loaicuahang;

import geso.dms.center.beans.loaicuahang.*;
import geso.dms.center.beans.loaicuahang.imp.*;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LoaicuahangSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
	
    public LoaicuahangSvl()
    {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		ILoaicuahangList obj;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    
	    obj = new LoaicuahangList();
	    Utility util = new Utility();
	    out = response.getWriter();
	    
	    String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
	    if (view == null) 
	    	view = "";

	    String param = "";
	    if(view.trim().length() > 0) param ="&view="+view;
	    if ( Utility.CheckSessionUser( session,  request, response)) {
	    	Utility.RedireactLogin(session, request, response);
	    }
	    if( Utility.CheckRuleUser( session,  request, response, "LoaicuahangSvl", param, Utility.XEM )) {
	    	Utility.RedireactLogin(session, request, response);
	    }
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String hchId = util.getId(querystring);

	    if (action.equals("delete")){	   		  	    	
	    	if(view.trim().length() > 0) param = "&view=" + view;
	    	int[] quyen = Utility.Getquyen("LoaicuahangSvl", param, userId);
	    	if(quyen[Utility.XOA] != 1) {
	    		return;
	    	}
	    	Delete(hchId,obj);
	    	out.print(hchId);
	    }
	   	
	    
	    obj.setUserId(userId);
	    obj.init("");
		session.setAttribute("obj", obj);
				
		String nextJSP = "/AHF/pages/Center/LoaiCuaHang.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		ILoaicuahangList obj;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out = response.getWriter();
	    obj = new LoaicuahangList();
		HttpSession session = request.getSession();
	    String userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
	    Utility util = new Utility();
	    
	    String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
	    if (view == null) 
	    	view = "";
	    obj.setView(view);

	    String param = "";
	    if(view.trim().length() > 0) param ="&view="+view;
	    if ( Utility.CheckSessionUser( session,  request, response)) {
	    	obj.closeDB();
	    	Utility.RedireactLogin(session, request, response);
	    }
	    if( Utility.CheckRuleUser( session,  request, response, "LoaicuahangSvl", param, Utility.XEM )) {
	    	obj.closeDB();
	    	Utility.RedireactLogin(session, request, response);
	    }
	    
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = "";
	    }
	    out.println(action); 
	        
	    
	    if (action.equals("new")){
	    	// Empty Bean for distributor
	    	ILoaicuahang lchBean = (ILoaicuahang) new Loaicuahang("");
	    	lchBean.setUserId(userId);
	    	// Save Data into session
	    	session.setAttribute("lchBean", lchBean);
    		
    		String nextJSP = "/AHF/pages/Center/LoaiCuaHangNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }
	    if (action.equals("search")){	    
	    	String search = getSearchQuery(request,obj);
	    		
	    	obj.init(search);
			obj.setUserId(userId);
			
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/AHF/pages/Center/LoaiCuaHang.jsp");	    	
	    	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request,ILoaicuahangList obj){
		   // PrintWriter out = response.getWriter();
		
		//ILoaicuahangList obj = new LoaicuahangList();
			
			String loai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("LoaiCuaHang"));
	    	if ( loai == null)
	    		loai = "";
	    	obj.setLoaicuahang(loai);
	    	
	    	String diengiai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("DienGiai"));
	    	if (diengiai == null)
	    		diengiai = "";    	
	    	obj.setDiengiai(diengiai);
	    	
	    	String trangthai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("TrangThai"));   	
	    	if (trangthai == null)
	    		trangthai = "";    	
		
	    	if (trangthai.equals("2"))
	    		trangthai = "";
	    	
	    	obj.setTrangthai(trangthai);
	    	
	    	String query;
	    	query = "select a.pk_seq as id, a.loai, a.diengiai, a.trangthai, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua from loaicuahang a, nhanvien b, nhanvien c where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq"; 

	    	Utility util = new Utility();
	    	if (loai.length()>0){
				query = query + " and dbo.ftBoDau(upper(a.loai)) like upper(?)";
				obj.getDataSearch().add( "%" + util.replaceAEIOU(loai) + "%" );
	    	}
	    	
	    	if (diengiai.length()>0){
				query = query + " and dbo.ftBoDau(upper(a.diengiai)) like upper(?)";
				obj.getDataSearch().add( "%" + util.replaceAEIOU(diengiai) + "%" );
	    	}
	  
	    	if(trangthai.length() > 0){
	    		query = query + " and a.trangthai = ?";
	    		obj.getDataSearch().add(trangthai);
	    	}
	    	query = query + " order by loai";
	    	//System.out.println("Tim kiem : "+query);
	    	return query;
		}	
	
	private void Delete(String id,ILoaicuahangList obj)
	{
		
		//ILoaicuahangList obj = new LoaicuahangList();
		
		dbutils db = new dbutils();
		ResultSet rs1 = db.get("select count(lch_fk) as num from khachhang where lch_fk='"+ id + "'");
		try{
			rs1.next();			
			if (rs1.getString("num").equals("0")){
				db.update("delete from loaicuahang where pk_seq = '" + id + "'");
				db.shutDown();
			}
			else
				obj.setMsg("Loại cửa hàng này đã có khách hàng rồi, nên không xóa được");
		}catch(Exception e){}
		
	}

}
