package geso.dms.center.servlets.nhacungcap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.*;
import java.sql.ResultSet;

import geso.dms.center.db.sql.*;
import geso.dms.center.beans.daidienkinhdoanh.IDaidienkinhdoanhList;
import geso.dms.center.beans.nhacungcap.imp.Nhacungcap;
import geso.dms.center.beans.nhacungcap.imp.NhacungcapList;
import geso.dms.center.beans.nhacungcap.INhacungcap;
import geso.dms.center.beans.nhacungcap.INhacungcapList;
import geso.dms.center.util.Utility;

public class NhacungcapSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   

   public NhacungcapSvl() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    HttpSession session = request.getSession();	         
	    
	    String querystring = request.getQueryString();
    	
	    Utility util = new Utility();
    	String userId = util.getUserId(querystring);
    	if (userId == null)
    		userId = "";
    	
    	String action = util.getAction(querystring);
    	if (action == null)
    		action = "";
    	
    	String id = util.getId(querystring);
    	if (id == null)
    		id = "";
    	INhacungcapList obj = new NhacungcapList();
    	
    	String param = "";
	    
		if (Utility.CheckSessionUser( session, request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		
		if (Utility.CheckRuleUser( session, request, response, "NhacungcapSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}
		   	
	   	if (action.equals("update")){
	   		int[] quyen = Utility.Getquyen("NhacungcapSvl", "",userId);
			if(quyen[Utility.SUA]==1)
			{
		   		INhacungcap ncc = new Nhacungcap();		   		
		   		ncc.setId(id);
		   		ncc.init();		   		
		       	session.setAttribute("ncc", ncc);
		       	session.setAttribute("userId", userId);
		        String nextJSP = "/AHF/pages/Center/NhaCungCapUpdate.jsp";
		       	response.sendRedirect(nextJSP);
			} 
	   	}
	   	else
	   	if (action.equals("display")){
	   		INhacungcap ncc = new Nhacungcap();		   		
	   		ncc.setId(id);
	   		ncc.init();		   		
	       	session.setAttribute("ncc", ncc);
	       	session.setAttribute("userId", userId);
	        String nextJSP = "/AHF/pages/Center/NhaCungCapDisplay.jsp";
	       	response.sendRedirect(nextJSP);
	   	}
	   	else{	    	
	   		obj.init();	
	   		session.setAttribute("obj", obj);
	   		session.setAttribute("userId", userId);			
	   		String nextJSP = "/AHF/pages/Center/NhaCungCap.jsp";
	   		response.sendRedirect(nextJSP);	   			   		   			   		
	   	}	    
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    Utility util = new Utility();
	    HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    INhacungcapList obj = new NhacungcapList();
	    
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = "";
	    }
	    
	    // Perform searching. Each distributor is saved into Nhacungcap
	    if (geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")).equals("search")){
	    	
	    	obj.setQuery(getSearchQuery(request, obj));
	    	obj.setUserId(userId);
			obj.init();
			
	    	session.setAttribute("obj", obj);

    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/AHF/pages/Center/NhaCungCap.jsp");	
	    	/*String tenncc = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ncc")));	    		
	    	String tenviettat = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tenviettat")));
	    	String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
	    	String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
	    	String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));

	    	String query = "";
	    	
	    	query = "select a.pk_seq, a.ten, a.diachi,a.nguoidaidien,a.dienthoai, a.masothue, a.tenviettat, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from nhacungcap a, nhanvien b, nhanvien c where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq "; 
	    		
	    	
	    	if (tenncc.length()>0){
	    		query = query + " and upper(a.ten) like upper(N'%" + (tenncc) + "%')";
	    		obj.setNhacungcap(tenncc);
	    	}

	    	if (tenviettat.length()>0){
	    		query = query + " and upper(a.tenviettat) like upper(N'%" + util.replaceAEIOU(tenviettat) + "%')";
	    		obj.setTenviettat(tenviettat);
	    	}

	    	//out.println(query);
	    	if (tungay.length() > 0){
	    		query = query + " and a.ngaytao >= '" + tungay + "'" ;
	    		obj.setTungay(tungay);
	    	}
	    	 
	    	if (denngay.length() > 0){
	    		query = query + " and a.ngaytao <= '" + denngay + "'" ;
	    		obj.setDenngay(denngay);
	    	}

	    	if (!trangthai.trim().equals("2")){
	    		query = query + " and a.trangthai ='" + trangthai + "'";
	    		obj.setTrangthai(trangthai);
	    	}
	    	
	    	String param = "";
			if(obj.getView().trim().length() > 0) param ="&view="+obj.getView();
			if ( Utility.CheckSessionUser( session,  request, response))
			{
				obj.DBClose();
				Utility.RedireactLogin(session, request, response);
			}
			if( Utility.CheckRuleUser( session,  request, response, "NhacungcapSvl", param, Utility.XEM ))
			{
				obj.DBClose();
				Utility.RedireactLogin(session, request, response);
			}
    		    	
	    	System.out.print(query);
	    	obj.setQuery(query);
	    	obj.init();
			
	    	//Data object is saved into session
	    	session.setAttribute("obj", obj);
			
	    	// userId is saved into session
	    	session.setAttribute("userId", userId);
			
	    	String nextJSP = "/AHF/pages/Center/NhaCungCap.jsp";
	    	response.sendRedirect(nextJSP);*/
	    }
	    String msg="";
	    // Create a new distributor
	    if (geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")).equals("new")){
	    	int[] quyen = Utility.Getquyen("NhacungcapSvl", "",userId);
			if(quyen[Utility.THEM]==1)
			{
  
		    	// Empty Bean for distributor
		    	Nhacungcap nccBean = new Nhacungcap();
		    	
		    	// Save Distributor into session
	    		session.setAttribute("nccBean", nccBean);
	    		session.setAttribute("userId", userId);
		    	String nextJSP = "/AHF/pages/Center/NhaCungCapNew.jsp";
	    		response.sendRedirect(nextJSP);
			}else{
				msg = "User không được phân quyền thêm";
			}
			
	    }
	    
	    if (geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")).equals("Xoa")){
	    	
	    	String IdXoa = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("IdXoa")) ;
 
	    	obj.setUserId(userId);
 
	    	session.setAttribute("obj", obj);

    		session.setAttribute("userId", userId);
    		//command = "select count (pk_seq) as num from nhacungcap_dvkd where ncc_fk='" + id + "' and checked ='1'";
    		/*command = "select count (pk_seq) as num from nhacungcap_dvkd where ncc_fk='" + id + "' and checked ='1' ";
    		System.out.println(command);
    		ResultSet rs = db.get(command);
    		if(rs!=null)
				try {
					if(rs.next())
						if(rs.getString("num").equals("0"))
						{
							command ="delete from nhacungcap_dvkd where ncc_fk ='"+ id +"'";
								db.update(command);
							command = "delete from nhacungcap where pk_seq ='" + id + "'";
								db.update(command);
							
						}
						else
							obj.setMsg("Nhà cung cấp đã có trong giám sát bán hàng, không thể xóa");
				if(rs!=null) rs.close();
	        	if(db!=null) db.shutDown();
				} catch(Exception e) {
				
					e.printStackTrace();
				}*/
	    	if (IdXoa == null)
				IdXoa = "";
			System.out.println("IdXoa = "+ IdXoa);
			if(IdXoa.trim().length() > 0)
				Delete(IdXoa, obj);
			else
				obj.setMsg("Lỗi xóa dữ liệu");
			obj.init();
			 
			session.setAttribute("obj", obj);

    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/AHF/pages/Center/NhaCungCap.jsp");
	   	}
 
	}
	
	private void Delete(String id, INhacungcapList obj)
	{
		String command = "";
		String param = "";
		dbutils db = new dbutils();
		try
		{

			if(obj.getView().trim().length() > 0) param ="&view="+obj.getView();
			
			int[] quyen = Utility.Getquyen("NhacungcapSvl", param,obj.getUserId());
			if(quyen[Utility.XOA]!=1)
			{
				
				return;
			}
 
			command = "select count (pk_seq) as num from nhacungcap_dvkd where ncc_fk='" + id + "' and checked ='1' ";
    		System.out.println(command);
    		ResultSet rs = db.get(command);
    		if(rs!=null){
				try {
					if(rs.next())
						if(rs.getString("num").equals("0"))
						{
							command ="delete from nhacungcap_dvkd where ncc_fk ='"+ id +"'";
								db.update(command);
							command = "delete from nhacungcap where pk_seq ='" + id + "'";
								db.update(command);
							
						}
						else
							obj.setMsg("Nhà cung cấp đã có trong giám sát bán hàng, không thể xóa");
				if(rs!=null) rs.close();
	        	if(db!=null) db.shutDown();
				} catch(Exception e) {
				
					e.printStackTrace();
				}
    		}
    		
    		
		} catch (Exception e)
		{
			System.out.println("vao day roi" + e.toString());
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			obj.setMsg("Ban Khong Thuc Hien Xoa Duoc,Vui Long Kiem Tra Lai." + e.toString());
		}
		
	}
	
	private String getSearchQuery(HttpServletRequest request, INhacungcapList obj){
		
		Utility util = new Utility();
		obj.getDataSearch().clear();
		
		String tenncc =  geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ncc"));
		if(tenncc == null)
			tenncc = "";
		obj.setNhacungcap(tenncc);
		
    	String tenviettat =  geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tenviettat"));
    	if(tenviettat == null)
    		tenviettat = "";
    	obj.setTenviettat(tenviettat);
    	
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
    	
    	if (trangthai.equals("2"))
    		trangthai = "";
 
    	String query = "";
    	
    	query = "select a.pk_seq, a.ten, a.diachi,a.nguoidaidien,a.dienthoai, a.masothue, a.tenviettat, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from nhacungcap a, nhanvien b, nhanvien c where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq "; 
  
    	if (tenncc.length()>0){
    		query = query + " and a.ten like ?";
    		obj.getDataSearch().add( "%" +tenncc+ "%" );
    	}

    	if (tenviettat.length()>0){
    		query = query + " and a.tenviettat like ?";
    		obj.getDataSearch().add("%" + tenviettat.trim() + "%");
    	}

    	//out.println(query);
    	if (tungay.length() > 0){
    		query = query + " and a.ngaytao >= ?" ;
    		obj.getDataSearch().add("'" + tungay + "'");
    	}
    	 
    	if (denngay.length() > 0){
    		query = query + " and a.ngaytao <= ?" ;
    		obj.getDataSearch().add("" + denngay + "");
    	}

    	if (trangthai.length() > 0){
    		query = query + " and a.trangthai = ? ";
    		obj.getDataSearch().add("" + trangthai + "");
    	}
    	 
    	System.out.println("\nquery search: " + query + "\n");
    	System.out.println("\nquery search: " + denngay + "\n");
    	return query;
 
	}
}