package geso.dms.center.servlets.chungloai;

import geso.dms.center.beans.chungloai.IChungloai;
import geso.dms.center.beans.chungloai.imp.Chungloai;
import geso.dms.center.beans.chungloai.IChungloaiList;
import geso.dms.center.beans.chungloai.imp.ChungloaiList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

 public class ChungloaiSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;

   public ChungloaiSvl() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utility util = new Utility();
		IChungloaiList obj;

		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");

	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();
	    obj = new ChungloaiList();
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String idlist = util.getId(querystring);

	    
	    //Is a Chung loai deleted?
	    if (action.equals("delete")){	   		
		   	Delete(idlist, obj);
	    }
	    
	    // Collect all of Chung loai, each Chung loai is saved into Bean Chungloai
	    
	    ResultSet clList = getChungloaiBeanList("");
	    obj.setClList(clList);	    
		
		// Save data into session
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
			
		String nextJSP = "/AHF/pages/Center/ChungLoai.jsp";
		response.sendRedirect(nextJSP);
	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");

	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();
	    Utility util = new Utility();
	    IChungloaiList obj;

	    obj = new ChungloaiList();
	    
	    String userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
	    
	    // Perform searching. 
	    if (geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")).equals("search")){
	    	
	    	String search = getSearchQuery(request, obj);
	    	System.out.println(search);

	    	ResultSet cllist = getChungloaiBeanList(search);	    	
    	    obj.setClList(cllist);	    
    		
    		session.setAttribute("userId", userId);
    			
    		// Saving data into session
    		session.setAttribute("obj", obj);
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/AHF/pages/Center/ChungLoai.jsp");
	    }
	    
	    // Create a new Business Unit
	    if (geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")).equals("new")){
	    	// Empty Bean for distributor
	    	IChungloai cloaiBean = new Chungloai();	    	
	    	ResultSet nhlist = (ResultSet)getNhanhangList();
	    	// Save Data into session
	    	
    		session.setAttribute("cloaiBean", cloaiBean);
    		session.setAttribute("nhlist", nhlist);
    		session.setAttribute("userId", userId);
    		
	    	String nextJSP = "/AHF/pages/Center/ChungLoaiNew.jsp";
    		response.sendRedirect(nextJSP);
	    
	    }
		
	}   
	
/*	private void Delete(String idlist){
		dbutils db = new dbutils();
		String clId = idlist.split(";")[0];
	    String nhId = idlist.split(";")[1];

	    ResultSet rs = db.get("select count(pk_seq) as num from sanpham where chungloai_fk='" + clId + "'");
	    try{
	    	rs.next();
	    	if (rs.getString("num").equals("0")){
	    		String command = "delete from nhanhang_chungloai where cl_fk ='" + clId + "' and nh_fk='" + nhId + "'";
	    		db.update(command);
	    		
	    		rs = db.get("select count(cl_fk) as num from nhanhang_chungloai where cl_fk='" + clId + "'");
	    		rs.next();
	    		if(rs.getString("num").equals("0")){	    			
	    			command = "delete from chungloai where pk_seq ='" + clId + "'";
	    			db.update(command);
	    		}
	    	}
	   	}catch(Exception e){
	   		
	   	}
		
	}
	*/
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


	private void Delete(String idlist, IChungloaiList obj){
		dbutils db = new dbutils();
		String clId = idlist.split(";")[0];
	    String nhId = idlist.split(";")[1];
	    
	    String sql ="select count(pk_seq) as num from sanpham where chungloai_fk='" + clId + "'";
	    if(kiemtra(sql))
	    {  
	    	String command = "delete from nhanhang_chungloai where cl_fk ='" + clId + "' and nh_fk='" + nhId + "'";
    		db.update(command);
    		sql = "select count(cl_fk) as num from nhanhang_chungloai where cl_fk='" + clId + "'";
    		if(kiemtra(sql))
    		{
    			command = "delete from chungloai where pk_seq ='" + clId + "'";
    			db.update(command);
    		}
	    	
	    }
	    else
	    	obj.setMsg("Đã tồn tại sản phẩm của chủng loại này rồi, nên không xóa được");


	  /*  ResultSet rs = db.get("select count(pk_seq) as num from sanpham where chungloai_fk='" + clId + "'");
	    try{
	    	rs.next();
	    	if (rs.getString("num").equals("0")){
	    		String command = "delete from nhanhang_chungloai where cl_fk ='" + clId + "' and nh_fk='" + nhId + "'";
	    		db.update(command);
	    		
	    		rs = db.get("select count(cl_fk) as num from nhanhang_chungloai where cl_fk='" + clId + "'");
	    		rs.next();
	    		if(rs.getString("num").equals("0")){	    			
	    			command = "delete from chungloai where pk_seq ='" + clId + "'";
	    			db.update(command);
	    		}
	    	}
	   	}catch(Exception e){
	   		
	   	}
		*/
	}
	private ResultSet getChungloaiBeanList(String search){
			ResultSet rs1;
			String query;
			dbutils db = new dbutils();
			if(search.length()==0){
				query = " select a.pk_seq as clId,isnull(a.smartid,'') as ma, a.ten as chungloai,a.trangthai, dbo.GROUP_CONCAT( c.pk_seq) as nhId, dbo.GROUP_CONCAT( c.ten) as nhanhang, "+
					" a.ngaytao, a.ngaysua, d.ten as nguoitao, e.ten as nguoisua from chungloai a left join   nhanhang_chungloai b on a.pk_seq=b.cl_fk "+
					" left join nhanhang c on c.pk_Seq=b.nh_fk  inner join  nhanvien d on d.pk_seq=a.nguoitao inner join   nhanvien e  on e.pk_seq=a.nguoisua "
					+ "  group by a.pk_seq,a.ten,a.trangthai, " +
					" a.ngaytao, a.ngaysua, d.ten , e.ten ,a.smartid "+
					" order by chungloai ";
				rs1 = db.get(query);   					
			}else{
				query = search;
				rs1 = db.get(query);
			}
			
		return rs1;
	}
	
	private ResultSet getNhanhangList(){
		String query;
		dbutils db = new dbutils();
		//query = "select pk_seq, ten, trangthai from nhanhang where trangthai='1'";
		query = "select a.pk_seq, a.ten, a.trangthai from nhanhang a,nhacungcap_dvkd b where a.dvkd_fk = b.dvkd_fk and b.checked ='1' and  a.trangthai='1'";
		ResultSet rs =  db.get(query);
		return rs;
		
	}
	
	private String getSearchQuery(HttpServletRequest request, IChungloaiList obj){
//	    PrintWriter out = response.getWriter();
		Utility util = new Utility();
		String cloai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chungloai")));
    	if (cloai == null)
    		cloai = "";
    	obj.setCloai(cloai);
    	
    	String nhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhId")));
    	
    	if (nhId == null)
    		nhId = "";    	
    	obj.setNhId(nhId);
    	
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
    	
    	String query = "select a.pk_seq as clId,isnull(a.smartid,'') as ma, a.ten as chungloai,a.trangthai, c.pk_seq as nhId, c.ten as nhanhang, "+
    		"  a.ngaytao, a.ngaysua, d.ten as nguoitao, e.ten as nguoisua from chungloai a left join   nhanhang_chungloai b on a.pk_seq=b.cl_fk "+
    		" left join nhanhang c on c.pk_Seq=b.nh_fk  inner join  nhanvien d on d.pk_seq=a.nguoitao inner join   nhanvien e  on e.pk_seq=a.nguoisua ";

    	if (cloai.length()>0){
			query = query + "and a.ten like upper(N'%"+ cloai + "%')";
    	}   		
    	
    	if (nhId.length()>0){
    		query = query + " and c.pk_seq = '" + nhId + "'";	    		
    	}
    	
    	if (tungay.length() > 0) {
    		query = query + " and a.ngaytao >= '" + tungay + "'";
    	}
    	
    	if (denngay.length() > 0) {
    		query = query + " and a.ngaytao <= '" + denngay + "'";
    	}
    	
    	if(trangthai.length() > 0){
    		query = query + " and a.trangthai = '" + trangthai + "'";
    	}
    	
    	System.out.println(query);
    	
		return query + " order by chungloai";
		
	}
 }