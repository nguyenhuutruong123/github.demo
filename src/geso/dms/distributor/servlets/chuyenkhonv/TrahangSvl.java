package geso.dms.distributor.servlets.chuyenkhonv;

import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.chuyenkhonv.ITrahang;
import geso.dms.distributor.beans.chuyenkhonv.ITrahangList;
import geso.dms.distributor.beans.chuyenkhonv.imp.Trahang;
import geso.dms.distributor.beans.chuyenkhonv.imp.TrahangList;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TrahangSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
	String userId;
	
    public TrahangSvl() 
    {
        super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out = response.getWriter();
	    	    
	    HttpSession session = request.getSession();

	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    
	    if (userId.length() == 0 )
	    	userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String pxkId = util.getId(querystring);

	    String msg = "";
	    if(action.equals("chotphieu"))
	    {
    		ITrahang ck = new Trahang(pxkId);
    		ck.setUserId(userId);
    		if(!ck.ChotCk())
    		{
    			msg = "Không thể chốt chuyển kho, vui lòng thử lại sau";
    		}
	    }
	    else
	    {
	    	if(action.equals("delete"))
	    	{
		    	ITrahang ck = new Trahang(pxkId);
		    	ck.setUserId(userId);
	    		if(!ck.DeleteCk())
	    		{
	    			msg = "Không thể xóa trả kho, vui lòng thử lại sau";
	    		}
	    	}
	    }
	   	    
	    ITrahangList obj = new TrahangList();
	    obj.setUserId(userId);
	    
	    obj.init("");
	    obj.setMsg(msg);
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/AHF/pages/Distributor/TraHang.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out = response.getWriter();
	    
		HttpSession session = request.getSession();
	    userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = "";
	    }
	    out.println("Action la: " + action); 
	    
	    if (action.equals("Tao moi"))
	    {
	    	// Empty Bean for distributor
	    	ITrahang pxkBean = (ITrahang) new Trahang("");
	    	pxkBean.setUserId(userId);
	    	pxkBean.createRS();
	    	
	    	// Save Data into session
	    	session.setAttribute("pxkBean", pxkBean);
    		
    		String nextJSP = "/AHF/pages/Distributor/TraHangNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }
	    else
	    {
	    	ITrahangList obj = new TrahangList();

	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/AHF/pages/Distributor/TraHang.jsp");	    		    	
	    }
	    
	}
	
	private String getSearchQuery(HttpServletRequest request, ITrahangList obj) 
	{	
		String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
    	if ( nppId == null)
    		nppId = "";
    	obj.setNppId(nppId);
    	
		String nvbhId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nvbhId"));
    	if ( nvbhId == null)
    		nvbhId = "";
    	obj.setNvbhId(nvbhId);
    	
    	String trangthai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"));
    	if ( trangthai == null)
    		trangthai = "";
    	obj.setTrangthai(trangthai);
    	
    	String tungay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"));
    	if (tungay == null)
    		tungay = "";    	
    	obj.setTungay(tungay);
    	
    	String denngay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay"));
    	if (denngay == null)
    		denngay = "";    	
    	obj.setDenngay(denngay);
    	   	
    	String query = "select a.pk_seq, a.trangthai, a.ngaychuyen, b.ten as ddkdTen, a.ngaytao, c.ten as nguoitao, a.ngaysua, d.ten as nguoisua  " +
						"from TRAHANG a inner join daidienkinhdoanh b on a.nvbh_fk = b.pk_seq  " +
							"inner join nhanvien c on a.nguoitao = c.pk_seq " +
							"inner join nhanvien d on a.nguoisua = d.pk_seq  " +
						"where a.npp_fk = '" + nppId + "'  ";
    	
    	if(nvbhId.trim().length() > 0)
    		query += " and b.pk_seq = '" + nvbhId + "' ";
    	
    	if(trangthai.trim().length() > 0)
    		query += " and a.trangthai = '" + trangthai + "' ";
    	
    	if(tungay.trim().length() > 0)
    		query += " and a.ngaychuyen >= '" + tungay + "'  ";
    	
    	if(denngay.trim().length() > 0)
    		query += " and a.ngaychuyen <= '" + denngay + "'  ";
						
    	query += " order by a.pk_seq desc ";
    	return query;
	}
	
}
