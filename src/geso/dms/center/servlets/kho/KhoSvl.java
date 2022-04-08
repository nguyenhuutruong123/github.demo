package geso.dms.center.servlets.kho;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import geso.dms.center.beans.kho.IKho;
import geso.dms.center.beans.kho.imp.Kho;
import geso.dms.center.beans.kho.IKhoList;
import geso.dms.center.beans.kho.imp.KhoList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;
/**
 * Servlet implementation class for Servlet: KhoSvl
 *
 */
 public class KhoSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;

	public KhoSvl() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   
	
	    Utility util = new Utility();
	    //HttpServletRequest request;
	    IKhoList obj;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();

	    String querystring = request.getQueryString();
	    System.out.println(querystring);
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	   obj = new KhoList();	   
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = util.getAction(querystring);
	    System.out.println(action);
	    
	    String khoId = util.getId(querystring);
	    out.println(khoId);
	    
	    String param = "";
	    
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "KhoSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}
	    String msg="";
	    //Is a Don Vi Kinh Doanh deleted?
	    if (action.equals("delete")){	
	    	System.out.println("bbbbb");
	    	int[] quyen = Utility.Getquyen("KhoSvl", "",userId);
			if(quyen[Utility.XOA]==1)
			{
				if(!Delete(khoId,obj))
					obj.setMsg("Lỗi không xóa được ! Vì kho đã có số lượng hoặc đã được sử dụng !"); 
				else 
					obj.setMsg("Xóa kho thành công !");
			}else{
				msg = "User không được phân quyền xóa";
			}
	    }
		/*
		 * if (action.equals("display")){ String query =
		 * "select a.pk_seq, a.ten, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from kho a, nhanvien b, nhanvien c where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ "
		 * ; //IKhoList obj = new KhoList(); List<IKho> kholist = getKhoData(query);
		 * obj.setKhoList(kholist);
		 * 
		 * //Data object is saved into session session.setAttribute("obj", obj);
		 * 
		 * // userId is saved into session session.setAttribute("userId", userId);
		 * 
		 * String nextJSP = "/AHF/pages/Center/KhoDisplay.jsp";
		 * response.sendRedirect(nextJSP); }
		 */
	   
	    // Collect all of Business Unit, each Business Unit is saved into Bean Donvikinhdoanh
	    String query = "select a.pk_seq, a.ten, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from kho a, nhanvien b, nhanvien c where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ ";
    	//IKhoList obj = new KhoList();	    	
    	List<IKho> kholist = getKhoData(query);
    	obj.setKhoList(kholist);
		
    	//Data object is saved into session
    	session.setAttribute("obj", obj);
		
    	// userId is saved into session
    	session.setAttribute("userId", userId);
		
    	String nextJSP = "/AHF/pages/Center/Kho.jsp";
    	response.sendRedirect(nextJSP);

	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  
	    Utility util = new Utility();
	    //HttpServletRequest request;
	    IKhoList obj;
		
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
		HttpSession session = request.getSession();
	    //this.request = request;
	    obj = new KhoList();
	    
	    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String param = "";
	    
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "KhoSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}
	    
	    // Perform searching. Each Business Unit is saved into Donvikinhdoanh
	    if (geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")).equals("search")){
	    	String search = getSearchQuery(request,obj);

	    	List<IKho> kholist = getKhoBeanList(search,obj);	    	
	    	obj.setKhoList(kholist);
	    	
    		// Saving data into session
    		session.setAttribute("obj", obj);
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/AHF/pages/Center/Kho.jsp");
	    }
	    String msg = "";
	    // Create a new Business Unit
	    if (geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")).equals("new")){
	    	int[] quyen = Utility.Getquyen("KhoSvl", "",userId);
			if(quyen[Utility.THEM]==1)
			{
		    	// Empty Bean for distributor
		    	IKho khoBean = new Kho();
		    	
		    	// Save Data into session
	    		session.setAttribute("khoBean", khoBean);
	    		session.setAttribute("userId", userId);
	    		
		    	String nextJSP = "/AHF/pages/Center/KhoNew.jsp";
	    		response.sendRedirect(nextJSP);
			}else{
				msg = "User không được phân quyền thêm";
			}
	    }
	}

	private boolean Delete(String khoId,IKhoList obj){
		
		dbutils db  = new dbutils();
	    //IKhoList obj = new KhoList();
	   	
		String command = "select count(*) as num from nhapp_kho where kho_fk='" + khoId + "'  and SOLUONG  > 0";
		System.out.println( command);
		ResultSet rs = db.get(command);
		
		try{
			while( rs.next())
			{
				System.out.println("IN "+rs.getString("num"));
			if (rs.getInt("num") == 0)
			{
				command = "delete from nhapp_kho where kho_fk='" + khoId + "'";
				System.out.println( command);
				if(!db.update(command))
					return false;
				command = "delete from kho where pk_seq ='" + khoId + "'";
				System.out.println( command);
				if(!db.update(command))
					return false;
				db.update(command);
			}
			else
				return false;
			}
			return true;
		}catch(Exception e){
			return false;
		}
		
	}

	private List<IKho> getKhoBeanList(String query, IKhoList obj ){  
		
		dbutils db  = new dbutils(); 
		for(Object d : obj.getDataSearch()){
			System.out.println(d);
		}
		ResultSet rs =  db.getByPreparedStatement(query,obj.getDataSearch());;
		List<IKho> kholist = new ArrayList<IKho>();
		if (rs != null){
			IKho khoBean;
			String[] param = new String[10];
			try{
				while(rs.next())
				{
					param[0]= rs.getString("pk_seq");
					param[1]= rs.getString("ten");
					param[2]= rs.getString("diengiai");
					param[3]= rs.getDate("ngaytao").toString();
					param[4]= rs.getDate("ngaysua").toString();
					param[5]= rs.getString("nguoitao");
					param[6]= rs.getString("nguoisua");			
					param[7]= rs.getString("trangthai");
					khoBean = new Kho(param);
					kholist.add(khoBean);
				}
			if(rs!=null) rs.close();
	        if(db!=null) db.shutDown();
			}catch(Exception e){
			
			}
		}
		return kholist;
	}

	private String getSearchQuery(ServletRequest request, IKhoList obj ){
		
	    Utility util = new Utility();
	    obj.getDataSearch().clear();
		String ten = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ten")));
    	if (ten == null)
    		ten = "";
    	obj.setTen(ten);
    	
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
    	
    	String query = "select a.pk_seq, a.ten, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from kho a, nhanvien b, nhanvien c where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ ";
    	
    	if (ten.length()>0){
			query = query + " and upper(a.ten) like upper(?)";
			obj.getDataSearch().add("%" + util.replaceAEIOU(ten) + "%");
    	}
    		
    	
/*    	if (diengiai.length()>0){
    		query = query + " and upper(a.diengiai) like upper('%" + util.replaceAEIOU(diengiai) + "%')";	    		
    	}*/
    	
    	if (tungay.length() > 0) {
    		query = query + " and a.ngaytao >= ?";
    		obj.getDataSearch().add("" + tungay + "");
    	}
    	
    	if (denngay.length() > 0) {
    		query = query + " and a.ngaytao <= ?";
    		obj.getDataSearch().add("" + denngay + "");
    	}
    	
    	if(trangthai.length() > 0){
    		query = query + " and a.trangthai = ?";
    		obj.getDataSearch().add("" + trangthai + "");
    	}
    	//System.out.print(query);
		return query;
	}
	
	private List<IKho> getKhoData(String query){
		
		dbutils db  = new dbutils();
		List<IKho> kholist = new ArrayList<IKho>();  		
		String[] param = new String[10];
        ResultSet rs =  db.get(query);
        try{
        	while(rs.next()){        	
        		param[0]= rs.getString("pk_seq");
        		param[1]= rs.getString("ten");
        		param[2]= rs.getString("diengiai");        		
        		param[3]= rs.getString("ngaytao").toString();
        		param[4]= rs.getString("ngaysua").toString();
        		param[5]= rs.getString("nguoitao");
        		param[6]= rs.getString("nguoisua");
        		param[7]= rs.getString("trangthai");
    			IKho kho = new Kho(param);
    			kholist.add(kho);    			
        	}
        	if(rs!=null) rs.close();
        	if(db!=null) db.shutDown();
        	
       }catch(Exception e){}
       return kholist;
	}
	
}