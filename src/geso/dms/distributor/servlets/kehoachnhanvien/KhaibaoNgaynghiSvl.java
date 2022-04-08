package geso.dms.distributor.servlets.kehoachnhanvien;


import geso.dms.distributor.beans.kehoachnhanvien.IKhaibaongaynghi;
import geso.dms.distributor.beans.kehoachnhanvien.IKhaibaongaynghiList;
import geso.dms.distributor.beans.kehoachnhanvien.imp.Khaibaongaynghi;
import geso.dms.distributor.beans.kehoachnhanvien.imp.KhaibaongaynghiList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class KhaibaoNgaynghiSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    public KhaibaoNgaynghiSvl() 
    {
        super();
    }
    private int items = 50; 
	//String userId;
	//String nppId;

	private int splittings = 20;
    private void settingPage(IKhaibaongaynghiList obj) {
		Utility util = new Utility();
		if(getServletContext().getInitParameter("items") != null){
			String i = getServletContext().getInitParameter("items").trim();
			if(util.isNumeric(i))
				items = Integer.parseInt(i);
		}

		if(getServletContext().getInitParameter("splittings") != null){
			String i = getServletContext().getInitParameter("splittings").trim();
			if(util.isNumeric(i))
				splittings = Integer.parseInt(i);
		}

		obj.setItems(items);
		obj.setSplittings(splittings);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IKhaibaongaynghiList obj;
		PrintWriter out; 
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out  = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = util.getAction(querystring);
	    String id = util.getId(querystring);
	    obj = new KhaibaongaynghiList();
	    if(action.equals("delete")){
	    	obj.setMsg(Xoa(id));
	    }
	    if(action.equals("chot")){
	    	obj.setMsg(Chot(id));
	    }
	    if(action.equals("unchot")){
	    	obj.setMsg(UnChot(id));
	    }
	    if(action.equals("duyet")){
	    	obj.setMsg(Duyet(id));
	    }
	    String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
	    if(view == null)
	    	view = "";
	    obj.setView(view);
	    obj.setUserId(userId);	    
		session.setAttribute("obj", obj);
		obj.init();
		String nextJSP = "/AHF/pages/Distributor/KhaiBaoNgayNghi.jsp";
		response.sendRedirect(nextJSP);
	}

	private String Chot(String id) {
		dbutils db = new dbutils();
		String sql="update KEHOACHNV set TRANGTHAI = 1 WHERE PK_SEQ = " + id;
		if(!db.update(sql)){
			db.shutDown();
			return "Không thể chốt. Lỗi "+ sql;
		}
		db.shutDown();
		return "";
	}
	private String UnChot(String id) {
		dbutils db = new dbutils();
		String sql="update KEHOACHNV set TRANGTHAI = 0 WHERE trangthai = '1' and PK_SEQ = " + id;
		System.out.println("Unchot "+sql);
		if(!db.update(sql)){
			db.shutDown();
			return "Không thể mở chốt. Lỗi "+ sql;
		}
		db.shutDown();
		return "";
	}

	private String Xoa(String id) {
		// TODO Auto-generated method stub
		dbutils db = new dbutils();
		try {
			db.getConnection().setAutoCommit(false);
			String sql="DELETE FROM KEHOACHNV_NGAYNGHI WHERE KEHOACHNV_FK = " + id;
			if(!db.update(sql)){
				db.getConnection().rollback();
				return "Không thể xóa. Lỗi "+ sql;
			}
			sql="DELETE FROM KEHOACHNV WHERE PK_SEQ = " + id;
			if(!db.update(sql)){
				db.getConnection().rollback();
				return "Không thể xóa. Lỗi "+ sql;
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (SQLException e) {
			try {
				db.getConnection().rollback();
				db.shutDown();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			return "Lỗi" + e.getMessage();
		}
		db.shutDown();
		return "";
	}
	private String Duyet(String id) {
		dbutils db = new dbutils();
		String sql="update KEHOACHNV set TRANGTHAI = 2 WHERE PK_SEQ = " + id;
		if(!db.update(sql)){
			db.shutDown();
			return "Không thể duyệt. Lỗi "+ sql;
		}
		db.shutDown();
		return "";
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IKhaibaongaynghi obj = new Khaibaongaynghi();
		PrintWriter out; 
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out  = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    
	    Utility util=new Utility();
	    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    obj.setUserId(userId);
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
	    if(view == null)
	    	view = "";
	    obj.setView(view);
	    //System.out.println ("action  : "+action);
		if(action.equals("new"))
		{
			obj.init();
			session.setAttribute("obj", obj);
			String nextJSP = "/AHF/pages/Distributor/KhaiBaoNgayNghiNew.jsp";
			response.sendRedirect(nextJSP);
			return;
		}
		else if(action.equals("search"))
		{
			
			IKhaibaongaynghiList objlist = new KhaibaongaynghiList();
		    userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		    objlist.setUserId(userId);
		
			String thang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thang"));
			    if(thang == null)
			    	thang = "";
			    objlist.setThang(thang);
			String asmid = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("asmid"));
			    if(asmid == null)
			    	asmid = "";
			    objlist.setAsmId(asmid);
			   
			    String ddkdid = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdid"));
			    if(ddkdid == null)
			    	ddkdid = "";
			    objlist.setDdkdId(ddkdid);
			    
			objlist.init();
			session.setAttribute("obj", objlist);
			String nextJSP = "/AHF/pages/Distributor/KhaiBaoNgayNghi.jsp";
			response.sendRedirect(nextJSP);
		}
		else if(action.equals("view") || action.equals("next") || action.equals("prev"))
		{
			IKhaibaongaynghiList objlist = new KhaibaongaynghiList();
		    userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		    objlist.setUserId(userId);
		
			String thang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thang"));
			    if(thang == null)
			    	thang = "";
			    objlist.setThang(thang);
			String asmid = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("asmid"));
			    if(asmid == null)
			    	asmid = "";
			    objlist.setAsmId(asmid);
			   
			    String ddkdid = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdid"));
			    if(ddkdid == null)
			    	ddkdid = "";
			    objlist.setDdkdId(ddkdid);
			    objlist.setUserId(userId);
			    objlist.setNxtApprSplitting(Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
			objlist.init();

			
			objlist.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
			session.setAttribute("obj", objlist);
			response.sendRedirect("/AHF/pages/Distributor/KhaiBaoNgayNghi.jsp");
		}
		else
		{
			IKhaibaongaynghiList objlist = new KhaibaongaynghiList();
			objlist.init();
			session.setAttribute("obj", objlist);
			String nextJSP = "/AHF/pages/Distributor/KhaiBaoNgayNghi.jsp";
			response.sendRedirect(nextJSP);
		}
	}
	  
	

	public String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
