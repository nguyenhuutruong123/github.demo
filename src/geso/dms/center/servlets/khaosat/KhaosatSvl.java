package geso.dms.center.servlets.khaosat;

import geso.dms.center.beans.khaosat.IKhaosat;
import geso.dms.center.beans.khaosat.IKhaosatList;
import geso.dms.center.beans.khaosat.imp.Khaosat;
import geso.dms.center.beans.khaosat.imp.KhaosatList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class KhaosatSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	
    public KhaosatSvl() {
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
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    IKhaosatList obj = new KhaosatList();
	    obj.setUserId(userId);
	    
	    String action = util.getAction(querystring);
	    String khlId = util.getId(querystring);
	    String msg = "";
	    
	    if(action.trim().equals("delete"))
	    {
	    	dbutils db = new dbutils();
	    	if(!db.update("update KhaoSat set trangthai = '2' where pk_seq = '" + khlId + "'"))
	    	{
	    		msg = "Không thể xóa KhaoSat";
	    	}
	    	db.shutDown();
	    }
	    
	    if(action.trim().equals("chot"))
	    {
	    	dbutils db = new dbutils();
	    	if(!db.update("update KhaoSat set trangthai = '1' where pk_seq = '" + khlId + "'"))
	    	{
	    		msg = "Không thể chốt KhaoSat";
	    	}
	    	db.shutDown();
	    }
	    
	    obj.init("");
	    obj.setMsg(msg);
		session.setAttribute("obj", obj);
	    
	    String nextJSP = "/AHF/pages/Center/KhaoSat.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    
	    HttpSession session = request.getSession();	
	    
	    out = response.getWriter();
	    Utility util = new Utility();
	    
	    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));  
	    
	    IKhaosatList obj;
	    
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = "";
	    }
	    
	    if(action.equals("new"))
	    {
    		IKhaosat khl = new Khaosat();
    		khl.setUserId(userId);
    		khl.createRs();

	    	session.setAttribute("csxBean", khl);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/AHF/pages/Center/KhaoSatNew.jsp");
	    }
	    else
	    {
	    	obj = new KhaosatList();
		    obj.setUserId(userId);

	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/AHF/pages/Center/KhaoSat.jsp");	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IKhaosatList obj) 
	{
		String ma = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("manguongoc"));
		if(ma == null)
			ma = "";
		obj.setMa(ma);
		
		String diengiai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai"));
		if(diengiai == null)
			diengiai = "";
		obj.setDiengiai(diengiai);
		
		String trangthai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"));
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String  sql = "select a.pk_seq, a.tieude, a.diengiai, a.bophan, a.socauhoi, a.trangthai, b.ten as nguoitao, a.ngaytao, c.ten as nguoisua, a.ngaysua    " +
						  "from KhaoSat a inner join NhanVien b on a.nguoitao = b.pk_seq    " +
					  		"inner join nhanvien c on a.nguoisua = c.pk_seq  " +
					  "where a.pk_seq > 0";
		
		/*if(ma.length() > 0)
			sql += " and a.ma like N'%" + ma + "%' ";*/
		
		if(diengiai.length() > 0)
			sql += " and a.diengiai like N'%" + diengiai + "%' ";
		
		if(trangthai.length() > 0)
			sql += " and a.trangthai = '" + trangthai + "' ";
		
		sql += " order by a.pk_seq desc ";
		
		return sql;
	}
	
	

}
