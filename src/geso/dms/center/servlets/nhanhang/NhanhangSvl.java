package geso.dms.center.servlets.nhanhang;

import geso.dms.center.beans.nhanhang.INhanhang;
import geso.dms.center.beans.nhanhang.imp.Nhanhang;
import geso.dms.center.beans.nhanhang.INhanhangList;
import geso.dms.center.beans.nhanhang.imp.NhanhangList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class NhanhangSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
   
   public NhanhangSvl() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
		Utility util = new Utility();
		//HttpServletRequest request;
		INhanhangList obj;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();
	    obj = (INhanhangList) new NhanhangList();
	    	
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    
	  //--- check user
	    String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
		if (view == null) {
			view = "";
		}
		String param = "";
		if(view.trim().length() > 0) param ="&view="+view;
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "NganhHangSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}
		 //--- check user 
	    
	    	    
	   String idlist = util.getId(querystring).split(";")[0];
	 //  String idlist1 = util.getId(querystring).split(";")[1];
	  
	    out.println(idlist);
	  //  System.out.println("ma:"+idlist);
	    //Is a Nhan hang deleted?
	    if (action.equals("delete")){	   		
		  	Delete(obj,idlist);
	    }
	   
	    // Collect all of Nhan hang, each Nhan hang is saved into Bean Nhanhang
	    String query = "select a.pk_seq,isnull(a.smartid,'') as ma, a.ten,b.pk_seq as dvkdId, b.donvikinhdoanh, a.trangthai, a.ngaytao, a.ngaysua, c.ten as nguoitao, d.ten as nguoisua, a.NGANHHANG_FK from nhanhang a, donvikinhdoanh b, nhanvien c, nhanvien d where a.dvkd_fk = b.pk_seq and a.nguoitao = c.PK_SEQ and a.nguoisua = d.PK_SEQ  ";

	    ResultSet nhlist = getNhBeanList(query, obj.getDataSearch());
	    
		// Save data into session
		obj.setNhlist(nhlist);
		
	    session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
			
		String nextJSP = "/AHF/pages/Center/NhanHang.jsp";
		response.sendRedirect(nextJSP);
		
	}  	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
		Utility util = new Utility();
		//HttpServletRequest request;
		INhanhangList obj;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
		PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();
	    //this.request = request;
	    obj = (INhanhangList) new NhanhangList();
	    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    
	  //--- check user
	    String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
		if (view == null)
			view = "";
		obj.setView(view);
		String param = "";
		if(view.trim().length() > 0) param ="&view="+view;
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			obj.DBClose();
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "NganhHangSvl", param, Utility.XEM ))
		{
			obj.DBClose();
			Utility.RedireactLogin(session, request, response);
		}
		 //--- check user
	    
	    
	    
	    
	    // Perform searching. Each Business Unit is saved into Donvikinhdoanh
	    if (geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")).equals("search")){
	    	String search = getSearchQuery(request,obj);
	    	out.println(search);

	    	ResultSet nhlist = getNhBeanList(search,obj.getDataSearch());	    	

    		// Saving data into session
    		obj.setNhlist(nhlist);
	    	session.setAttribute("obj", obj);
    		session.setAttribute("userId", userId);
	  
    		response.sendRedirect("/AHF/pages/Center/NhanHang.jsp");
	    }
	    
	    // Create a new Business Unit
	    if (geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")).equals("new")){
	    	// Empty Bean for distributor
	    	INhanhang nhanhangBean = new Nhanhang();
	    	
	    	// Save Data into session
    		session.setAttribute("nhanhangBean", nhanhangBean);
    		session.setAttribute("userId", userId);
    		
	    	String nextJSP = "/AHF/pages/Center/NhanHangNew.jsp";
    		response.sendRedirect(nextJSP);
    		
    		/*System.out.println("action new!!");
    		ResultSet nganhhanglist = nhanhangBean.getNganhHangList();
    		try {
    			while (nganhhanglist.next()){
    				System.out.println("Ten nganh hang ben Svl: '" + nganhhanglist.getString("TEN") + "' ");
    			}
    		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}*/
	    
	    }

	}

	private void Delete(INhanhangList obj,String idlist){
		
		//INhanhangList obj = new NhanhangList();
		
		dbutils db = new dbutils();
		 String nhId = idlist;
	  //  String nhId = idlist.split(";")[0];
	 //   String dvkdId = idlist.split(";")[1];
	    String sql = "select count(pk_seq) as num from sanpham where nhanhang_fk='" + nhId + "'";
	  //  System.out.println(sql);
	    if(kiemtra(sql))
	    {
	    	sql ="select count(cl_fk) as num from nhanhang_chungloai where nh_fk='" + nhId + "'";
	    //	System.out.println(sql);
	    	if(kiemtra(sql))
	    	{
	    		String command = "delete from nhanhang where pk_seq ='" + nhId + "'";
	    	//	System.out.println(sql);
    			db.update(command);
	    	}
	    	else
	    		obj.setMsg("Nhãn hàng đã có trong chủng loại, không thể xóa");
	    }
	    else
	    	obj.setMsg("Nhãn hàng đã có trong sản phẩm, không thể xóa");
	    	
	}
	    boolean kiemtra(String sql)
		{dbutils db =new dbutils();
	    	ResultSet rs = db.get(sql);
			try {//kiem tra ben san pham
			while(rs.next())
			{ if(rs.getString("num").equals("0"))
			   return true;
			}
			} catch(Exception e) {
			
				e.printStackTrace();
			}
			 return false;
		}
	    
	private ResultSet getNhBeanList(String query, List<Object> dataSearch){  	
		dbutils db = new dbutils();
		//ResultSet rs =  db.get(query);
		
		ResultSet rs   = db.getByPreparedStatement(query, dataSearch);
		return rs;
	}

	private String getSearchQuery(ServletRequest request,INhanhangList obj){
		
		obj.getDataSearch().clear();
		Utility util = new Utility();
		//HttpServletRequest request;
		
		
//	    PrintWriter out = response.getWriter();
		//Utility util = new Utility();
		String nhanhang = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhanhang")));
    	if (nhanhang == null)
    		nhanhang = "";
    	obj.setNhanhang(nhanhang);

    	
		String dvkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")));
    	if (dvkdId == null)
    		dvkdId = "";
    	obj.setDvkdId(dvkdId);

    	String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
    	if (tungay == null)
    		tungay = "";    	
    	obj.setTungay(tungay);
    	
    	String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
    	if (denngay == null)
    		denngay = "";    	
    	obj.setDenngay(denngay);
    	
    	String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));   	
    	if (trangthai == null)
    		trangthai = "";    	
    	
    	obj.setTrangthai(trangthai);
    	
    	if (trangthai.equals("2"))
    		trangthai = "";    	
    	
    	//String query = "select a.pk_seq, a.ten, b.pk_seq as dvkdId, b.donvikinhdoanh, a.trangthai, a.ngaytao, a.ngaysua, c.ten as nguoitao, d.ten as nguoisua from nhanhang a, donvikinhdoanh b, nhanvien c, nhanvien d where a.dvkd_fk = b.pk_seq and a.nguoitao = c.PK_SEQ and a.nguoisua = d.PK_SEQ  ";
	    String query = "select a.pk_seq,isnull(a.smartid,'') as ma, a.ten,b.pk_seq as dvkdId, b.donvikinhdoanh, a.trangthai, a.ngaytao, a.ngaysua, c.ten as nguoitao, d.ten as nguoisua, a.NGANHHANG_FK from nhanhang a, donvikinhdoanh b, nhanvien c, nhanvien d where a.dvkd_fk = b.pk_seq and a.nguoitao = c.PK_SEQ and a.nguoisua = d.PK_SEQ  ";
    
	    if (nhanhang.length()>0){
			query = query + " and upper(a.ten) like upper(?)";
			
			obj.getDataSearch().add( "%" + nhanhang + "%"  );
    	}
    	
    	if (dvkdId.length()>0){
			query = query + " and b.pk_seq =?";
			obj.getDataSearch().add( dvkdId );
    	}

    	if (tungay.length()>0) {
    		query = query + " and a.ngaytao >= ?";
    		obj.getDataSearch().add( tungay );
    	}
    	
    	if (denngay.length()>0) {
    		query = query + " and a.ngaytao <= ?";
    		obj.getDataSearch().add( denngay );
    	}
    	
    	
    	if(trangthai.length() > 0){
    		query = query + " and a.trangthai =? ";
    		obj.getDataSearch().add( trangthai );
    	}
    	System.out.print(query);
		return query;
	}		
}