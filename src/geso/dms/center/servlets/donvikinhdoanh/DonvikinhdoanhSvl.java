package geso.dms.center.servlets.donvikinhdoanh;
import geso.dms.center.beans.donvikinhdoanh.IDonvikinhdoanh;
import geso.dms.center.beans.donvikinhdoanh.imp.Donvikinhdoanh;
import geso.dms.center.beans.donvikinhdoanh.IDonvikinhdoanhList;
import geso.dms.center.beans.donvikinhdoanh.imp.DonvikinhdoanhList;
import geso.dms.center.db.sql.dbutils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import geso.dms.center.util.Utility;

 public class DonvikinhdoanhSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
   public DonvikinhdoanhSvl() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    Utility util = new Utility();
	    HttpSession session;   
	    IDonvikinhdoanhList obj;
	    String userId;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    session = request.getSession();
	    PrintWriter out = response.getWriter();   
	    obj = new DonvikinhdoanhList();
	    
	    String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = util.getAction(querystring);
	    System.out.println(action);
	    
	    String dvkdId = util.getId(querystring);
	    out.println(dvkdId);
	    
	    String param = "";
	    
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "DonvikinhdoanhSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}
	    
	    //Is a Don Vi Kinh Doanh deleted?, muon delete don vi kinh doanh phai xoa ma DVKD trong san pham,nhan hang va khoa lien ket den nhacc_donvikd_kenh, nhapp_nhacc_dvkd
	 
		  //Is a Chung loai deleted?
	    
	    String msg = "";
	    if (action.equals("delete")){
	    	int[] quyen = Utility.Getquyen("DonvikinhdoanhSvl", "",userId);
			if(quyen[Utility.XOA]==1)
			{
				//kiem tra ton tai trong san pham
				xoa_dvkd(dvkdId,obj);
				//Delete(dvkdId);
			}else{
				msg = "User không được phân quyền xóa";
			}
	    }
	    
	    if (action.equals("update")){
	    	int[] quyen = Utility.Getquyen("DonvikinhdoanhSvl", "",userId);
			if(quyen[Utility.SUA]==1)
			{
	    	    IDonvikinhdoanh dvkdBean = new Donvikinhdoanh();    	   
	    	    dvkdBean.setId(dvkdId);
	    	    dvkdBean.init();
	    	    // Data is saved into session
				session.setAttribute("dvkdBean", dvkdBean);
				session.setAttribute("userId", userId);
	
				String nextJSP = "/AHF/pages/Center/DonViKinhDoanhUpdate.jsp";
	       		response.sendRedirect(nextJSP);
			}else{
				msg = "User không được phân quyền sửa";
			}
	    }else
	    if (action.equals("display")){
	    	    IDonvikinhdoanh dvkdBean = new Donvikinhdoanh();    	   
	    	    dvkdBean.setId(dvkdId);
	    	    dvkdBean.init();
	    	    // Data is saved into session
				session.setAttribute("dvkdBean", dvkdBean);
				session.setAttribute("userId", userId);
	
				String nextJSP = "/AHF/pages/Center/DonViKinhDoanhDisplay.jsp";
	       		response.sendRedirect(nextJSP);
	    }
	    else{
	    	// Collect all of Business Unit, each Business Unit is saved into Bean Donvikinhdoanh
	    	session.setAttribute("obj", obj);
	    	session.setAttribute("userId", userId);	    	
	    	String nextJSP = "/AHF/pages/Center/DonViKinhDoanh.jsp";
	    	response.sendRedirect(nextJSP);
	    }  
	}
	
	void xoa_dvkd(String dvkdId,IDonvikinhdoanhList obj)
	{  
		//IDonvikinhdoanhList obj = new DonvikinhdoanhList();
		String sql ="select count(dvkd_fk) as num from sanpham where dvkd_fk='"+ dvkdId +"'";
		if(kiemtra(sql))
		{
			sql ="select count(pk_seq) as num from nhanhang where dvkd_fk='"+ dvkdId +"'";
			if(kiemtra(sql))
				{
					sql = " select count(ncc_dvkd_fk) as num from nhapp_nhacc_donvikd where ncc_dvkd_fk " +
						  " in (select pk_seq from nhacungcap_dvkd where dvkd_fk ='"+ dvkdId +"')";
					if(kiemtra(sql))
					{
						Delete(dvkdId);
					}
					else
						obj.setMsg("Don vi kinh doanh da lien ket voi nha phan phoi roi, nen khong xoa duoc");	
				}
				else
					obj.setMsg("Don vi kinh doanh da ton tai trong nhan hang roi nen khong xoa duoc");
		  }
		else
			obj.setMsg("Đã có sản phẩm này trong đơn vị kinh doanh, nên không thể xóa được");
		System.out.println(obj.getMsg());
	}
	boolean kiemtra(String sql)
	{
		dbutils db  = new dbutils();
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    	   
	   Utility util = new Utility();
	   HttpSession session;   
	   IDonvikinhdoanhList obj = new DonvikinhdoanhList();
	   String userId;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    session = request.getSession();
	    userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String param = "";
	    
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "DonvikinhdoanhSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}
	    
	    // Perform searching. Each Business Unit is saved into Donvikinhdoanh
	    if (geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")).equals("search")){
	    	String search = getSearchQuery(request,obj);
	    	obj.setQuery(search);
    		obj.getDvkdList();
    		// Saving data into session
    		session.setAttribute("obj", obj);
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/AHF/pages/Center/DonViKinhDoanh.jsp");
	    }
	    String msg = "";
	    // Create a new Business Unit
	    if (geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")).equals("new")){
	    	int[] quyen = Utility.Getquyen("DonvikinhdoanhSvl", "",userId);
			if(quyen[Utility.THEM]==1)
			{
		    	// Empty Bean for distributor
		    	IDonvikinhdoanh dvkdBean = new Donvikinhdoanh();
		    	// Save Data into session
	    		session.setAttribute("dvkdBean", dvkdBean);
	    		session.setAttribute("userId", userId);
	    		
		    	String nextJSP = "/AHF/pages/Center/DonViKinhDoanhNew.jsp";
	    		response.sendRedirect(nextJSP);
			}else{
				msg = "User không được phân quyền thêm";
			}
	    
	    }
		
	}   
	
	private void Delete(String dvkdId)
	{
		dbutils db  = new dbutils();
		String command = "delete from nhacungcap_dvkd where dvkd_fk ='" + dvkdId + "'";
		db.update(command);	
		command = "delete from donvikinhdoanh where pk_seq ='" + dvkdId + "'";	
		db.update(command);	
	}
		
	private String getSearchQuery(ServletRequest request, IDonvikinhdoanhList obj)
	{
	    Utility util = new Utility();
	    obj.getDataSearch().clear();
		String dvkd = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkd")));    	    	
		if (dvkd == null)
    		dvkd = "";
		dvkd = dvkd.trim();
    	obj.setDvkd(dvkd);
    	
    	String nccId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nccId")));
    	if (nccId == null)
    		nccId = "";    	
    	obj.setNccId(nccId);
    	
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
	
    	if (trangthai.equals("2"))
    		trangthai = "";
    	obj.setTrangthai(trangthai);
    	
    	String query = " select a.pk_seq, a.donvikinhdoanh, d.ten as nhacungcap, a.trangthai, a.ngaytao, " +
    				   " a.ngaysua, b.ten as nguoitao, c.ten as nguoisua, d.pk_seq as nccId, a.diengiai " +
    				   " from donvikinhdoanh a, nhanvien b, nhanvien c, nhacungcap d, nhacungcap_dvkd e " +
    				   " where a.PK_SEQ = e.DVKD_FK and d.PK_SEQ = e.NCC_FK and a.nguoitao = b.PK_SEQ " +
    				   " and a.nguoisua = c.PK_SEQ and checked='1' ";
    	
    	if (dvkd.length()>0){
    		obj.setDvkd(dvkd);
			query = query + " and upper(a.donvikinhdoanh) like upper(?)";
			obj.getDataSearch().add("%" + util.replaceAEIOU(dvkd) + "%"); 
    	}
    	    	
    	if (!nccId.equals("0")){
    		obj.setNccId(nccId);
    		query = query + " and d.pk_seq =?";
    		obj.getDataSearch().add("" + nccId + "");
    	}
    	
    	if (tungay.length() > 0) {
    		obj.setTungay(tungay);
    		query = query + " and a.ngaytao >='"+tungay+"'";
    		//obj.getDataSearch().add("%" + tungay + "%");
    	}
    	
    	if (denngay.length() > 0) {
    		obj.setDenngay(denngay);
    		query = query + " and a.ngaytao <='"+denngay+"'";
    		//obj.getDataSearch().add("%" + denngay + "%");
    	}
    	
    	if(trangthai.length() > 0){
    		obj.setTrangthai(trangthai);
    		query = query + " and a.trangthai = ?";
    		obj.getDataSearch().add("" + trangthai + "");
    	}
     
		return query;
	}
	
	
	/*private ResultSet getNccListByDvkd(String dvkdId){
	
	   // HttpServletRequest request;
	   // HttpServletResponse response;
	   HttpSession session;   
	   IDonvikinhdoanhList obj = new DonvikinhdoanhList();
	   String userId;
		
		String command; 
		command = "select a.pk_seq, a.ten, b.checked from nhacungcap a, nhacungcap_dvkd b where a.pk_seq = b.ncc_fk and b.dvkd_fk='" + dvkdId + "'";
		
		return  (db.get(command));
	}*/
	
 
 }