package geso.dms.distributor.servlets.tuyenbanhang;

import geso.dms.distributor.beans.tuyenbanhang.*;
import geso.dms.distributor.beans.tuyenbanhang.imp.*;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TuyenbanhangSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
   
    public TuyenbanhangSvl()
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
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
		ITuyenbanhangList obj;
		PrintWriter out; 

		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out  = response.getWriter();
  

	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String tbhId = util.getId(querystring);
	    String msg="";
	    
/*	    if (action.equals("delete")){
	    	System.out.println("Vao day");
	    	msg= Delete(tbhId, userId);
	    	out.print(tbhId);
	    }
	   	    
	    obj = new TuyenbanhangList();
	    obj.setMsg(msg);
	    
	    String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
	    if (view == null) {
	    	view = "";
	    }
	    obj.setView(view);
	    System.out.println("View: "+view);
	    
	    if (view != null && view.equals("TT")) {
	    	obj.setMsg("Vui lòng chọn Chi nhánh/Nhà phân phối trước!");
	    }
	    
	    obj.setRequestObj(request);
	    obj.setUserId(userId);
	    
	    obj.init("");
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/AHF/pages/Distributor/TuyenBanHang.jsp";
		response.sendRedirect(nextJSP);*/
 
	    obj = new TuyenbanhangList();
	    obj.setMsg(msg);
	    
	    System.out.println(" request.getParameter(view) : "+ request.getParameter("view"));
	    String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
	    if (view == null) {
	    	view = "";
	    }
	    obj.setView(view);
	    System.out.println("View: "+view);
	    
	    if (view != null && view.equals("TT")) {
	    	obj.setMsg("Vui lòng chọn Chi nhánh/Nhà phân phối trước!");
	    }
	    
	    obj.setRequestObj(request);
	    obj.setUserId(userId);
	    String param="";
	    if (action.equals("delete")){
	    	if(view.trim().length() > 0) { param ="&view="+view; }
			if ( Utility.CheckSessionUser( session,  request, response))
			{
				Utility.RedireactLogin(session, request, response);
			}
			else if( Utility.CheckRuleUser( session,  request, response, "TuyenbanhangSvl", param, Utility.XOA ))
			{
				System.out.println("check : "+ Utility.CheckRuleUser( session,  request, response, "TuyenbanhangSvl", param, Utility.XOA ));
				Utility.RedireactLogin(session, request, response);
			}
			else{
				System.out.println("Vao day");
		    	msg= Delete(tbhId, userId);
		    	obj.setMsg(msg);
		    	obj.init("");
				session.setAttribute("obj", obj);
				String nextJSP = "/AHF/pages/Distributor/TuyenBanHang.jsp";
				response.sendRedirect(nextJSP);
			}
	    	
	    }
	    else{
	    	obj.init("");
		    
			session.setAttribute("obj", obj);
					
			String nextJSP = "/AHF/pages/Distributor/TuyenBanHang.jsp";
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
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
		ITuyenbanhangList obj = new TuyenbanhangList(); 
		PrintWriter out; 

		ITuyenbanhang tbhBean;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out = response.getWriter();
	    Utility util = new Utility();
		
	    userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    obj.setUserId(userId);
	    
	    String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
	    if (view == null) {
	    	view = "";
	    }
	    obj.setView(view);
	    
	    String npp_fk = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("npp_fk")));
	    if (npp_fk == null) {
	    	npp_fk = "";
	    }
	    obj.setNpp_fk_ho(npp_fk);
	    
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = "";
	    }
	    out.println(action); 
	    String nextJSP = "";

	    obj.setRequestObj(request);
	    String param="";
	    if (action.equals("new")){
	    	if(view.trim().length() > 0) param ="&view="+view;
			if ( Utility.CheckSessionUser( session,  request, response))
			{
				Utility.RedireactLogin(session, request, response);
			}
			else if( Utility.CheckRuleUser( session,  request, response, "TuyenbanhangSvl", param, Utility.THEM ))
			{
				Utility.RedireactLogin(session, request, response);
			}
			else{
				// Empty Bean for distributor
		    	tbhBean = (ITuyenbanhang) new Tuyenbanhang("");
			    tbhBean.setView(view);
			    if (npp_fk != null && npp_fk.length() > 0) {
			    	tbhBean.setNppId(npp_fk);
			    }
		    	tbhBean.setUserId(userId);
			    tbhBean.createRS();
		
		    	session.setAttribute("tbhBean", tbhBean);
	    		
	    		nextJSP = "/AHF/pages/Distributor/TuyenBanHangNew.jsp";
	    		response.sendRedirect(nextJSP);
			}
	    	
	    }
	    
	    else  if(action.equals("view") || action.equals("next") || action.equals("prev")){
	    	System.out.println("action : "+ action);
	    	String search = getSearchQuery(request, obj);
	    	int i = Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting")));    	
	    	obj.setNxtApprSplitting(i);
	    	obj.init(search);
	    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    	nextJSP = "/AHF/pages/Distributor/TuyenBanHang.jsp";
	    	session.setAttribute("obj", obj);  	
			session.setAttribute("userId", userId);
	    	response.sendRedirect(nextJSP);
	    }
/*	    else{
	    	String search = getSearchQuery(request, obj);
	    	obj.setUserId(userId);
	    	obj.init(search);
	    	nextJSP = "/AHF/pages/Distributor/TuyenBanHang.jsp";
	    }*/
	    else if (action.equals("search")){
	    	String search = getSearchQuery(request, obj);
	    	//search = search + " and a.npp_fk='" + userId + "' order by a.ten";
	    	System.out.print("sql: "+search+"\n");
	    	//obj = new KhachhangList(search);
	    	obj.setUserId(userId);
	    	
	    	obj.init(search);
	    	nextJSP = "/AHF/pages/Distributor/TuyenBanHang.jsp";
	    	session.setAttribute("obj", obj);  	
			session.setAttribute("userId", userId);
	    		
			response.sendRedirect(nextJSP);
	    	   		    	
	    	}
	    else{
	    	String search = getSearchQuery(request, obj);
	    	obj.setUserId(userId);
	    	obj.init(search);
	    	nextJSP = "/AHF/pages/Distributor/TuyenBanHang.jsp";
	    	session.setAttribute("obj", obj);  	
			session.setAttribute("userId", userId);
	    		
			response.sendRedirect(nextJSP);
	    }
		}
	}
	
	private String getSearchQuery(HttpServletRequest request, ITuyenbanhangList obj)
	{		
		Utility util = new Utility();
		String tbhTen = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tbhTen")));
    	if ( tbhTen == null)
    		tbhTen = "";
    	obj.setTuyenbh(tbhTen);
    	
    	String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
    	if ( nppId == null)
    		nppId = "";
    	obj.setNppId(nppId);
    	
    	String ddkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdTen")));
    	if (ddkdId == null)
    		ddkdId = "";    	
    	obj.setDdkdId(ddkdId);
    	
    	String view = obj.getView();
    	String npp_fk_ho = obj.getNpp_fk_ho();
    	if (view != null && view.equals("TT")) {
			nppId = npp_fk_ho;
		}
	    	
    	String query = "\n select ROW_NUMBER() OVER(ORDER BY a.pk_seq DESC) AS stt, a.pk_seq as tbhId, a.NGAYID, a.diengiai as tbhTen, a.ngaytao, a.ngaysua, a.ngaylamviec, b.ten as nguoitao, c.ten as nguoisua,";
		query = query +	"\n d.pk_seq as ddkdId, d.ten as ddkdTen, e.ten as nppTen, e.pk_seq as nppId";
		query = query + "\n from tuyenbanhang a inner join nhanvien b on a.nguoitao = b.pk_seq inner join nhanvien c on a.nguoisua = c.pk_seq inner join daidienkinhdoanh d on a.ddkd_fk = d.pk_seq"; 
	    query = query + "\n inner join nhaphanphoi e on a.npp_fk = e.pk_seq where a.npp_fk = ?";
    	obj.getDatasearch().clear();
    	obj.getDatasearch().add(nppId);
    	if (tbhTen.length()>0)
    	{
			query = query + " and upper(dbo.ftBoDau(a.diengiai)) like upper(?)";
			obj.getDatasearch().add("%"+util.replaceAEIOU(tbhTen)+"%");
    	}
    	
    	if (ddkdId.length()>0)
    	{
			query = query + " and a.ddkd_fk = ? ";
			obj.getDatasearch().add(ddkdId);
    	}
    	 	
    	//query = query + "  order by a.diengiai";
    	dbutils.viewQuery(query, obj.getDatasearch());
    	return query;
	}	
	
	private String Delete(String id, String userId)
	{
		dbutils db = new dbutils();
		try{
		String query = "select count(*) as num from khachhang_tuyenBH where tbh_fk='" + id + "'";	
		ResultSet rs = db.get(query);
		int a = 0;
		try
		{
			if(rs.next())
			{
				a = rs.getInt("num");
				
			}rs.close();
		} 
		catch(Exception e) {System.out.println("Loi : "+e.toString());}
		
		if(a == 0)
		{

			String sql = "delete from tuyenbanhang where pk_seq = '" + id + "' and pk_seq not in (select tbh_fk from khachhang_tuyenBH ) ";
			/*System.out.println("Xoa bang cha : "+sql);*/
			db.update(sql);
		}else{
			return "Không thể xóa tuyến này, vì đang có khách hàng trong tuyến";
		}
		db.shutDown();
		}catch(Exception err){
			return err.toString();
		}
		return "Xoá thành công.";
	}

}
