package geso.dms.center.servlets.nhomkhuyenmai;

import geso.dms.center.beans.nhomkhuyenmai.INhomkhuyenmai;
import geso.dms.center.beans.nhomkhuyenmai.INhomkhuyenmaiList;
import geso.dms.center.beans.nhomkhuyenmai.imp.Nhomkhuyenmai;
import geso.dms.center.beans.nhomkhuyenmai.imp.NhomkhuyenmaiList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class NhomkhuyenmaiSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	 static final long serialVersionUID = 1L;
	 PrintWriter out;
	 HttpServletRequest request;
	 HttpServletResponse response;
	 INhomkhuyenmaiList obj;
	 dbutils db;
	 public NhomkhuyenmaiSvl() {
		super();
	 }   	
			
	 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			    
	    this.request = request;
	    this.response = response;
	    this.db = new dbutils();
			    
	    response.setContentType("text/html");
			    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    out = response.getWriter();
	    obj = new NhomkhuyenmaiList();
	    
	    String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
	    if (view == null) 
	    	view = "";

	    String param = "";
	    if(view.trim().length() > 0) param ="&view="+view;
	    if ( Utility.CheckSessionUser( session,  request, response)) {
	    	Utility.RedireactLogin(session, request, response);
	    }
	    if( Utility.CheckRuleUser( session,  request, response, "NhomkhuyenmaiSvl", param, Utility.XEM )) {
	    	Utility.RedireactLogin(session, request, response);
	    }
			    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
			    
	    if (userId.length()==0)
	    	userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
			    
		    String action = util.getAction(querystring);
		    out.println(action);
			    
		    String nkmId = util.getId(querystring);

			if (action.equals("delete"))
			{	   		  	    	
				param = "";
				if(view.trim().length() > 0) param = "&view=" + view;
				int[] quyen = Utility.Getquyen("NhomkhuyenmaiSvl", param, userId);
				if(quyen[Utility.XOA] != 1) {
					return;
				}
				
			   if(!Delete(nkmId))
			   {
				   obj.setMsg("Không xóa được ! Nhóm đã được sử dụng");
			   }
			   else
				   obj.setMsg("Xóa thành công !");
		    }

		  //  List<INhomkhuyenmai> nkmlist = new ArrayList<INhomkhuyenmai>(); 
			    
		   // getNkmBeanList(nkmlist);
			    
			// Save data into session
			//obj.setNkmList(nkmlist);
			Utility Ult = new Utility();
			
			System.out.println("select a.pk_seq, a.ten, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao," +
			    		" c.ten as nguoisua from nhomsanphamkm a, nhanvien b, nhanvien c" +
			    		" where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ and a.type='1'") ; //and a.pk_seq in (select nsp_fk from NHOMSANPHAM_SANPHAM where sp_fk in "+ Ult.quyen_sanpham(userId)+") order by a.pk_seq desc ");
		    ResultSet Dsnkm = db.get("select a.pk_seq, a.ten, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao," +
			    		" c.ten as nguoisua from nhomsanphamkm a, nhanvien b, nhanvien c" +
			    		" where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ and a.type='1' " +// and a.pk_seq in (select nsp_fk from NHOMSANPHAM_SANPHAM where sp_fk in "+ Ult.quyen_sanpham(userId)+") "+
			    		" order by a.pk_seq desc ");
			    		
			    obj.setDsnkm(Dsnkm);
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);

			String nextJSP = "/AHF/pages/Center/NhomKhuyenMai.jsp";
			response.sendRedirect(nextJSP);
			    
		}  	

		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    PrintWriter out = response.getWriter();
		    Utility Ult = new Utility();
			HttpSession session = request.getSession();
		    String userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));

		    this.obj = new NhomkhuyenmaiList();
		    this.db = new dbutils();
		    
		    String view = Ult.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
		    if (view == null) 
		    	view = "";

		    String param = "";
		    if(view.trim().length() > 0) param ="&view="+view;
		    if ( Utility.CheckSessionUser( session,  request, response)) {
		    	Utility.RedireactLogin(session, request, response);
		    }
		    if( Utility.CheckRuleUser( session,  request, response, "NhomkhuyenmaiSvl", param, Utility.XEM )) {
		    	Utility.RedireactLogin(session, request, response);
		    }
			    
			String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
			if (action == null){
			   	action = "";
			}
			out.println(action); 
			String diengiai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai"));
	    	if (diengiai == null)
	    		diengiai = "";
	    	obj.setDiengiai(diengiai);
	    	String tungay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"));
	    	if (tungay == null)
	    		tungay = "";    	
	    	obj.setTungay(tungay);
	    	String denngay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay"));
	    	if (denngay == null)
	    		denngay = "";    	
	    	obj.setDenngay(denngay);
	    	String trangthai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"));   	
	    	if (trangthai == null)
	    		trangthai = "";    	
		
	    	if (trangthai.equals("2"))
	    		trangthai = "";
	    	obj.setTrangthai(trangthai);
	    	
	    	List<Object> dataSearch = new ArrayList<Object>();
	    	String query = "select a.pk_seq, a.ten, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from nhomsanphamkm a, nhanvien b, nhanvien c where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ and a.type='1'";
	    	geso.dms.distributor.util.Utility util = new geso.dms.distributor.util.Utility();
	    	if (diengiai.length()>0){
				query = query + " and upper(dbo.ftBoDau(a.diengiai)) like upper(?)";
				dataSearch.add( "%" + util.replaceAEIOU(diengiai) + "%" );
	    	}
	    	

	    	if (tungay.length() > 0) {
	    		query = query + " and a.ngaytao >= ?";
	    		dataSearch.add(tungay);
	    	}
	    	
	    	if (denngay.length() > 0) {
	    		query = query + " and a.ngaytao <= ?";
	    		dataSearch.add(denngay);
	    	}
	    	
	    	if(trangthai.length() > 0){
	    		query = query + " and a.trangthai = ?";
	    		dataSearch.add(trangthai);
	    	}
	    	//query = query +" and a.pk_seq in (select nsp_fk from NHOMSANPHAM_SANPHAM where sp_fk in "+ Ult.quyen_sanpham(userId) +")";
          System.out.println(query);
			// Perform searching. Each Nhomkhuyenmai is saved into Nhomkhuyenmai
			if (action.equals("new")){
			   	// Empty Bean for distributor
			   	INhomkhuyenmai nkmBean = (INhomkhuyenmai) new Nhomkhuyenmai();
			    	
			    nkmBean.UpdateRS();
			    // Save Data into session
		    	session.setAttribute("nkmBean", nkmBean);
		    	session.setAttribute("userId", userId);
		    		

		    	String nextJSP = "/AHF/pages/Center/NhomKhuyenMaiNew.jsp";
		    	response.sendRedirect(nextJSP);
		    		
			 }
			else
			 if (action.equals("search")){
			    	
			   	 ResultSet Dsnkm = db.getByPreparedStatement(query, dataSearch);
			   	 obj.setDsnkm(Dsnkm);
				session.setAttribute("obj", obj);

			    session.setAttribute("userId", userId);
				    		
			    response.sendRedirect("/AHF/pages/Center/NhomKhuyenMai.jsp");
			}
			 else
			{ obj = new NhomkhuyenmaiList();
			
			ResultSet Dsnkm = db.get("select a.pk_seq, a.ten, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from nhomsanphamkm a, nhanvien b, nhanvien c where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ and a.type='1' and a.pk_seq in (select nsp_fk from NHOMSANPHAMkm_SANPHAM where sp_fk in "+ Ult.quyen_sanpham(userId)+")");
			   	 obj.setDsnkm(Dsnkm);
				session.setAttribute("obj", obj);

			    session.setAttribute("userId", userId);
				    		
			    response.sendRedirect("/AHF/pages/Center/NhomKhuyenMai.jsp");
			}
		}

		private boolean Delete(String nkmId)
		{
			    
			    String query;
			    String command;
				command = "delete from nhomsanphamkm_sanpham where nsp_fk ='" + nkmId + "'";
				System.out.println("delete KM: " + command);
			 if( !db.update(command))
				 return false;
				
			    command = "delete from nhomsanphamkm where pk_seq ='" + nkmId + "'";
			    System.out.println("delete KM: " + command);
			    if( !db.update(command))
					 return false;
				
			 return true;
			}

			
			private void  getNkmBeanList(List<INhomkhuyenmai> nkmlist,String userId){	
				 Utility Ult = new Utility(); 	
			   	String query = "select a.pk_seq, a.ten, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from nhomsanphamkm a, nhanvien b, nhanvien c where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ and a.type='1' and a.pk_seq in (select nsp_fk from NHOMSANPHAMkm_SANPHAM where sp_fk in "+ Ult.quyen_sanpham(userId)+") order by pk_seq";
			   	ResultSet rs = db.get(query);
			   	try{	
			   		String[] param = new String[11];
		    		INhomkhuyenmai nkmBean;
		    		while (rs.next()){	    			
						param[0]= rs.getString("pk_seq");
						param[1]= rs.getString("ten");	
						param[2]= rs.getString("diengiai");
						param[3]= rs.getString("trangthai");
						param[4]= rs.getString("ngaytao");
						param[5]= rs.getString("ngaysua");
						param[6]= rs.getString("nguoitao");
						param[7]= rs.getString("nguoisua");			
						
						nkmBean = new Nhomkhuyenmai(param);					
						nkmlist.add(nkmBean);						
		    		}   
		    		if(rs!=null){
		    			rs.close();
		    		}
			   	}catch(Exception e){}
			}
			
			private List<INhomkhuyenmai> getNkmListS(String query){  	
				    
				ResultSet rs = db.get(query);
				List<INhomkhuyenmai> nkmlist = new ArrayList<INhomkhuyenmai>();			
								
				INhomkhuyenmai nkmBean;
				String[] param = new String[10];
				try{
					while(rs.next()){
						param[0]= rs.getString("pk_seq");
						param[1]= rs.getString("ten");	
						param[2]= rs.getString("diengiai");
						param[3]= rs.getString("trangthai");
						param[4]= rs.getString("ngaytao");
						param[5]= rs.getString("ngaysua");
						param[6]= rs.getString("nguoitao");
						param[7]= rs.getString("nguoisua");			
						nkmBean = new Nhomkhuyenmai(param);
						nkmlist.add(nkmBean);			
					}
					if(rs!=null){
		    			rs.close();
		    		}
				}catch(Exception e){}
				return nkmlist;
			}

			private String getSearchQuery(String userId){
//			    PrintWriter out = response.getWriter();
				geso.dms.distributor.util.Utility util = new geso.dms.distributor.util.Utility();
				String diengiai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai"));
		    	if (diengiai == null)
		    		diengiai = "";
		    	obj.setDiengiai(diengiai);
		    	String tungay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"));
		    	if (tungay == null)
		    		tungay = "";    	
		    	obj.setTungay(tungay);
		    	String denngay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay"));
		    	if (denngay == null)
		    		denngay = "";    	
		    	obj.setDenngay(denngay);
		    	String trangthai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"));   	
		    	if (trangthai == null)
		    		trangthai = "";    	
			
		    	if (trangthai.equals("2"))
		    		trangthai = "";
		    	obj.setTrangthai(trangthai);
		    	String query = "select a.pk_seq, a.ten, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from nhomsanphamkm a, nhanvien b, nhanvien c where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ and a.type='1'";
		    	
		    	
		    	if (diengiai.length()>0){
					query = query + " and upper(dbo.ftBoDau(a.diengiai)) like upper(N'%" + util.replaceAEIOU(diengiai) + "%')";
					
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
		    	return query;
			}
			
}
