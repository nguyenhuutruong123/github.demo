package geso.dms.distributor.servlets.denghitratb;

import geso.dms.distributor.beans.denghitratb.IDenghitratb;
import geso.dms.distributor.beans.denghitratb.IDenghitratbList;
import geso.dms.distributor.beans.denghitratb.imp.Denghitratb;
import geso.dms.distributor.beans.denghitratb.imp.DenghitratbList;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DenghitratbSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
       
    public DenghitratbSvl() 
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
		IDenghitratbList obj;
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
	    
	    String dnttbId = util.getId(querystring);

	    if (action.equals("delete"))
	    {	   		  	    	
	    	Delete(dnttbId);
	    	out.print(dnttbId);
	    }
	   	    
	    obj = new DenghitratbList();
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/AHF/pages/Distributor/DeNghiTraTrungBay.jsp";
		response.sendRedirect(nextJSP);
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
		IDenghitratbList obj = new DenghitratbList();
		PrintWriter out; 
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out = response.getWriter();
	    
		Utility util = new Utility();
	    userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = "";
	    }
	    out.println(action); 
	          
	    if (action.equals("Tao moi"))
	    {
	    	// Empty Bean for distributor
	    	IDenghitratb dnttbBean = (IDenghitratb) new Denghitratb("");
	    	dnttbBean.setUserId(userId);
	    	dnttbBean.createRS();
	    	
	    	// Save Data into session
	    	session.setAttribute("dnttbBean", dnttbBean);
    		
    		String nextJSP = "/AHF/pages/Distributor/DeNghiTraTbNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }else
	    {
	    	String search = this.getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/AHF/pages/Distributor/DeNghiTraTrungBay.jsp");	    		    	
	    }
		}
	}
	
	private String getSearchQuery(HttpServletRequest request, IDenghitratbList obj) 
	{	
		Utility util = new Utility();
		String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
    	if ( nppId == null)
    		nppId = "";
    	obj.setNppId(nppId);
    	
    	String sophieu = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sophieu")));
    	if ( sophieu== null)
    		sophieu = "";
    	obj.setScheme(sophieu);
    	
    	String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
    	if ( tungay== null)
    		tungay = "";
    	obj.setTungay(tungay);
    	
    	String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
    	if ( denngay == null)
    		denngay = "";
    	obj.setDenngay(denngay);
    	
    	String query = "select a.pk_seq as dnttbId, b.pk_seq as cttbId, b.diengiai as cttbTen, a.ngaydenghi, a.ngaysua, a.trangthai, c.ten as nguoitao, d.ten as nguoisua ";
		query = query + "from denghitratrungbay a inner join cttrungbay b on a.cttrungbay_fk = b.pk_seq inner join nhanvien c on a.nguoitao = c.pk_seq inner join nhanvien d on a.nguoisua = d.pk_seq ";
		query = query + "where a.npp_fk = '" + nppId + "'";
		
		if(sophieu.length() > 0)
			query =  query + " and a.pk_seq like '%" + sophieu + "%'";
		if(tungay.length() > 0)
			query = query + " and a.ngaydenghi >= '" + convertDate(tungay) + "'";
		if(denngay.length() > 0)
			query = query + " and a.ngaydenghi <= '" + convertDate(denngay) + "'";
    	
    	return query + " order by a.ngaydenghi DESC";
	}

	private void Delete(String dnttbId) 
	{
		dbutils db = new dbutils(); 
		try 
		{
			db.getConnection().setAutoCommit(false);
			String sql="Delete from from denghitratb_khachhang where denghitratb_fk='" + dnttbId + "'";
			if(!db.update(sql)){
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return;
			}
			sql="Delete from denghitratrungbay where pk_seq='" + dnttbId + "'";
			
			if(!db.update(sql)){
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
		} 
		catch(Exception e) {
			geso.dms.center.util.Utility.rollback_throw_exception(db);
		}
	}
	
	private String convertDate(String date) 
	{		
		//chuyen dinh dang dd-MM-yyyy sang dinh dang yyyy-MM-dd
		if(!date.contains("-"))
			return "";
		String[] arr = date.split("-");
		if(arr[0].length() < arr[2].length())
			return arr[2] + "-" + arr[1] + "-" + arr[0];
		return date;
	}
	

}
