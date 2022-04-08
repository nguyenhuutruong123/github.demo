package geso.dms.distributor.servlets.doihang;

import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.doihang.IErpKhachhangdoihang;
import geso.dms.distributor.beans.doihang.IErpKhachhangdoihangList;
import geso.dms.distributor.beans.doihang.imp.ErpKhachhangdoihang;
import geso.dms.distributor.beans.doihang.imp.ErpKhachhangdoihangList;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/ErpKhachhangdoihangSvl")
public class ErpKhachhangdoihangSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpKhachhangdoihangSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpKhachhangdoihangList obj;
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
	    obj = new ErpKhachhangdoihangList();
	    
	    String loaidonhang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaidonhang"));
	    if(loaidonhang == null)
	    	loaidonhang = "0";
	    obj.setLoaidonhang(loaidonhang);
	    System.out.println("---LOAI DON HANG: " + loaidonhang);
	    
	    if (action.equals("delete") )
	    {	
	    	String msg = this.DeleteChuyenKho(lsxId);
			obj.setMsg(msg);
	    }
	   
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
			
		String nextJSP = "/AHF/pages/Distributor/ErpKhachHangDoiHang.jsp";
		response.sendRedirect(nextJSP);
		
	}

	private String DeleteChuyenKho(String lsxId)
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);

			String query = "Delete ERP_Khachhangdoihang_SanPham where khachhangdoihang_fk = '" + lsxId + "'";
			if(!db.update(query))
			{
				msg = "2.Khong the xoa: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = "Delete ERP_Khachhangdoihang where pk_seq = '" + lsxId + "'";
			if(!db.update(query))
			{
				msg = "1.Khong the xoa: " + query;
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
	    
	    String loaidonhang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaidonhang"));
	    if(loaidonhang == null)
	    	loaidonhang = "0";
	    
	    
		IErpKhachhangdoihangList obj = new ErpKhachhangdoihangList();
		obj.setLoaidonhang(loaidonhang);
	 
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"))); 
	    
	    if(action.equals("Tao moi"))
	    {
	    	IErpKhachhangdoihang lsxBean = new ErpKhachhangdoihang();
	    	lsxBean.setLoaidonhang(loaidonhang);
	    	lsxBean.setUserId(userId);
	    	
	    	lsxBean.createRs();
	    	lsxBean.setKhoNhapId("100000");
    		
	    	session.setAttribute("lsxBean", lsxBean);
	    	
    		String nextJSP = "/AHF/pages/Distributor/ErpKhachHangDoiHangNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
		    	String search = getSearchQuery(request, obj);
		    	obj.setNxtApprSplitting(Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
		    	obj.setUserId(userId);
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	
		    	String nextJSP = "/AHF/pages/Distributor/ErpKhachHangDoiHang.jsp";
				response.sendRedirect(nextJSP);
		    }
	    	else
	    	{
		    	String search = getSearchQuery(request, obj);
		    	obj.init(search);
				obj.setUserId(userId);
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		String nextJSP = "/AHF/pages/Distributor/ErpKhachHangDoiHang.jsp";
	    		response.sendRedirect(nextJSP);
	    	}
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpKhachhangdoihangList obj)
	{
		String query = "select a.PK_SEQ, a.trangthai, a.ngaydenghi as ngaydonhang, c.ten as nppTEN, b.ten as khoTEN, NV.TEN as nguoitao, b.ten as khonhan, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua   " +
						"from ERP_Khachhangdoihang a inner join KHO b on a.khoDOI_fk = b.pk_seq inner join NHAPHANPHOI c on a.NPP_FK = c.pk_seq  " +
						"inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
						"inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.pk_seq > 0 ";
		
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
			query += " and a.ngaydenghi >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.ngaydenghi <= '" + denngay + "'";
	
		if(trangthai.length() > 0)
			query += " and a.TrangThai = '" + trangthai + "'";
		
		if(nppId.length() > 0){
			query += " and b.NPP_FK= '" + nppId + "'";
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
