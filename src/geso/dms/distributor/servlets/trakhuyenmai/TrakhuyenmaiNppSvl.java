package geso.dms.distributor.servlets.trakhuyenmai;

import geso.dms.distributor.util.Utility;
import geso.dms.distributor.beans.quanlykhuyenmai.*;
import geso.dms.distributor.beans.quanlykhuyenmai.imp.TrakhuyenmaiNppList;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TrakhuyenmaiNppSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
   
    public TrakhuyenmaiNppSvl()
    {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		ITrakhuyenmaiNppList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out  = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    obj = new TrakhuyenmaiNppList();
	    obj.setUserId(userId);
	    obj.init("");
		session.setAttribute("obj", obj);
				
		String nextJSP = "/AHF/pages/Distributor/TraKhuyenMai.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		ITrakhuyenmaiNppList obj = new TrakhuyenmaiNppList();
		HttpSession session = request.getSession();
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    Utility util = new Utility();
	    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = "";
	    }
	    
	    if(action.equals("view") || action.equals("next") || action.equals("prev")){
	    	
	    	obj.setNxtApprSplitting(Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
	    	obj.init("");
	    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    	
	    	obj.setUserId(userId);
			
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    	
	    	response.sendRedirect("/AHF/pages/Distributor/TraKhuyenMai.jsp");  
    	}
	    else
	    {
	    	String search = this.getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
			
	    	session.setAttribute("obj", obj);  	
			session.setAttribute("userId", userId);
	    		
			response.sendRedirect("/AHF/pages/Distributor/TraKhuyenMai.jsp");
	    }
	   
	}

	private String getSearchQuery(HttpServletRequest request, ITrakhuyenmaiNppList obj) 
	{
		Utility util = new Utility();
		String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
		
		//String query = "select a.pk_seq as ctkmId, a.SCHEME, a.diengiai as ctkmDiengiai, c.PK_SEQ as tkmId, c.DIENGIAI, c.HINHTHUC, a.TUNGAY, a.DENNGAY from CTKHUYENMAI a inner join CTKM_TRAKM b on a.PK_SEQ = b.CTKHUYENMAI_FK inner join TRAKHUYENMAI c on b.TRAKHUYENMAI_FK = c.PK_SEQ  " + 
		//" where c.LOAI = 3 and c.HINHTHUC = 2 and a.PK_SEQ in (select CTKHUYENMAI_FK from PHANBOKHUYENMAI where npp_fk = '" + nppId + "') and a.PK_SEQ in (select ctkm_fk from ctkm_npp where npp_fk = '" + nppId +"') and a.denngay >= (select max(ngayks) from khoasongay where npp_fk = '" + nppId + "')";
		String query = "select a.pk_seq as ctkmId, a.SCHEME, a.diengiai as ctkmDiengiai, c.PK_SEQ as tkmId, c.DIENGIAI, c.HINHTHUC, a.TUNGAY, a.DENNGAY, isnull(d.trakm_fk, '0') as dathietlap " +
		"from CTKHUYENMAI a inner join CTKM_TRAKM b on a.PK_SEQ = b.CTKHUYENMAI_FK inner join TRAKHUYENMAI c " +
		"on b.TRAKHUYENMAI_FK = c.PK_SEQ  left join (select distinct trakm_fk, ctkm_fk, npp_fk from TRAKM_NHAPP where npp_fk = '" + nppId + "') d on d.ctkm_fk = a.pk_seq and d.trakm_fk = c.PK_SEQ " +
		"where c.LOAI = 3 and c.HINHTHUC = 2 and a.PK_SEQ in  " +
		"(select CTKHUYENMAI_FK from PHANBOKHUYENMAI where npp_fk = '" + nppId + "') and a.PK_SEQ in " +
		"(select ctkm_fk from ctkm_npp where npp_fk = '" + nppId + "') and a.denngay >= (select max(ngayks) from khoasongay where npp_fk = '" + nppId + "')";
		
		String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
		if(diengiai == null)
			diengiai = "";
		obj.setScheme(diengiai);
		
		String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		if(diengiai.length() > 0)
			query += " and (a.scheme like('%" + diengiai + "%') or dbo.ftBoDau(a.diengiai) like('%" + util.replaceAEIOU(diengiai) + "%'))";
		if(tungay.length() > 0)
			query += " and a.tungay >= '" + tungay + "'";
		if(denngay.length() > 0)
			query += " and a.denngay <= '" + denngay + "'";
		
		return query;
	}

}
