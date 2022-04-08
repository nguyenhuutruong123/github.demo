package geso.dms.center.servlets.dondathang;

import geso.dms.center.beans.dondathang.IErpDuyetddhList;
import geso.dms.center.beans.dondathang.imp.ErpDuyetddhList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpDuyetddhSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpDuyetddhSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpDuyetddhList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = util.getAction(querystring);

    	String lsxId = util.getId(querystring);
	    obj = new ErpDuyetddhList();
	    
    	if(action.equals("chot"))
    	{
    		String msg = this.Chot(lsxId);
			obj.setMsg(msg);
    	}
	    
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
			
		String nextJSP = "/AHF/pages/Center/ErpDonDatHangDuyet.jsp";
		response.sendRedirect(nextJSP);
	    
	}

	private String Chot(String lsxId) 
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
		
			String query = "update ERP_Dondathang set trangthai = '2' where pk_seq = '" + lsxId + "'";
			if(!db.update(query))
			{
				msg = "Khong the chot: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			db.getConnection().commit();
			db.shutDown();
		}
		catch (Exception e) 
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			db.shutDown();
			return "Exception: " + e.getMessage();
		}
		
		return "";
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null)
	    {
	    	action = "";
	    }
	    
		IErpDuyetddhList obj = new ErpDuyetddhList();
	
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"))); 
	    obj.setUserId(userId);
	    
    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
    	{
	    	String search = getSearchQuery(request, obj);
	    	obj.setNxtApprSplitting(Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
	    	obj.setUserId(userId);
	    	obj.init(search);
	    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    	session.setAttribute("obj", obj);
	    	
	    	String nextJSP = "/AHF/pages/Center/ErpDonDatHangDuyet.jsp";
			response.sendRedirect(nextJSP);
	    }
    	else
    	{
	    	String search = getSearchQuery(request, obj);
	    	obj.init(search);
			obj.setUserId(userId);
			
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	
    		String nextJSP = "/AHF/pages/Center/ErpDonDatHangDuyet.jsp";
    		response.sendRedirect(nextJSP);
    	}
	    
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpDuyetddhList obj)
	{
		Utility util = new Utility();
		
		
		String query = "select a.PK_SEQ, a.trangthai, a.ngaydonhang, c.ten as nppTEN, a.loaidonhang, NV.TEN as nguoitao, b.ten as khonhan, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, isnull(a.NOTE, '') as NOTE, isnull(SOTIENTHU, 0) as SOTIENTHU, a.hopdong_fk    " +
						"from ERP_Dondathang a inner join ERP_KHOTT b on a.kho_fk = b.pk_seq inner join NHAPHANPHOI c on a.NPP_FK = c.pk_seq  " +
						"inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
						"inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.pk_seq > 0  AND A.TRANGTHAI=1  AND A.NPP_FK IN  "+util.quyen_npp(obj.getUserId()) +"  ";
		
		String tungay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"));
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String denngay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay"));
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String trangthai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"));
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
		if(nppId == null)
			nppId = "";
		obj.setNppTen(nppId);
		
		if(tungay.length() > 0)
			query += " and a.ngaydonhang >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.ngaydonhang <= '" + denngay + "'";
	
		if(trangthai.length() > 0)
			query += " and a.LoaiDonHang = '" + trangthai + "'";
		
		if(nppId.length() > 0){
			query += " and a.NPP_FK= '" + nppId + "'";
		}
		
		System.out.print(query);
		return query;
	}
		
	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	
}
