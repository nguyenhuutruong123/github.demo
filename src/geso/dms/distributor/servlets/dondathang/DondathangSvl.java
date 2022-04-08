package geso.dms.distributor.servlets.dondathang;

import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.dondathang.IDondathang;
import geso.dms.distributor.beans.dondathang.IDondathangList;
import geso.dms.distributor.beans.dondathang.imp.Dondathang;
import geso.dms.distributor.beans.dondathang.imp.DondathangList;
import geso.dms.distributor.db.sql.dbutils;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.ResultSet;

 public class DondathangSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;

	public DondathangSvl() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
		
		   IDondathangList obj;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    	    
		userId = (String) session.getAttribute("userId");
		userTen = (String) session.getAttribute("userTen");  	
		sum = (String) session.getAttribute("sum");
		Utility util = (Utility) session.getAttribute("util");
		if(!util.check(userId, userTen, sum)){
			response.sendRedirect("/index.jsp");
		}else{
			session.setMaxInactiveInterval(1200);	    	    
			String querystring = request.getQueryString();
			String action = util.getAction(querystring);
	    
			String ddhId = util.getId(querystring);
			if (action.equals("delete")){	   		  	    	
				Delete(ddhId, out);
				
			}

			obj = new DondathangList();
			
			obj.setUserId(userId);
			
			obj.createDdhlist("");
			session.setAttribute("obj", obj);
				
			String nextJSP = "/AHF/pages/Distributor/DonDatHang.jsp";
			response.sendRedirect(nextJSP);
			}
		}
		
	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
		   IDondathangList obj;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    //PrintWriter out = response.getWriter();

		userId = (String) session.getAttribute("userId");
		userTen = (String) session.getAttribute("userTen");  	
		sum = (String) session.getAttribute("sum");
		Utility util = (Utility) session.getAttribute("util");
		if(!util.check(userId, userTen, sum)){
			response.sendRedirect("index.jsp");
		}else{
			session.setMaxInactiveInterval(1200);
			obj = new DondathangList();		
	    
			String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
			if (action == null){
				action = "";
			}	     	        
	    
			if (action.equals("new")){
				
				IDondathang ddhBean = (IDondathang) new Dondathang();
				ddhBean.setUserId(userId);
				ddhBean.init0();
				session.setAttribute("ddhBean", ddhBean);
					    	
				String nextJSP = "/AHF/pages/Distributor/DonDatHangParam.jsp";
				response.sendRedirect(nextJSP);  
				
				
			/*	IDondathang ddhBean = (IDondathang) new Dondathang();
				 ddhBean.setUserId(userId);
				ddhBean.setDndhId("0");
				System.out.println("here");
		    	ddhBean.init2();
		    	session.setAttribute("ddhBean", ddhBean);
		    	String nextJSP = "/AHF/pages/Distributor/DonDatHangNewfromPR.jsp";
		    	response.sendRedirect(nextJSP);*/
    		
			}else if(action.equals("view") || action.equals("next") || action.equals("prev")){
		    	
				String nppId = "";
		    	/*
				String query = "select a.pk_seq from nhaphanphoi a, nhanvien b where a.ma = b.dangnhap and b.pk_seq = '" + userId + "'";
		    	dbutils db = new dbutils();
				ResultSet rs = db.get(query);
				try{
					if (rs != null){
						rs.next();
						nppId = rs.getString("pk_seq");
						rs.close();
					}else{
						nppId = "";
					}
				}catch(java.sql.SQLException e){}*/
				geso.dms.distributor.util.Utility dutil=new geso.dms.distributor.util.Utility();
				nppId = dutil.getIdNhapp(userId);
				//this.nppTen = dutil.getTenNhaPP();
				//this.dangnhap = dutil.getDangNhap();
				//this.sitecode=dutil.getSitecode();
				
				String search = getSearchQuery(request, util, nppId, obj);   	
				obj.setUserId(userId);
				session.setAttribute("userId", userId);
				
				obj.setNxtApprSplitting(Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
				obj.createDdhlist(search);
				obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
			
				session.setAttribute("obj", obj);
				
				String nextJSP = "/AHF/pages/Distributor/DonDatHang.jsp";	    	
				System.out.print("Tim kiem :"+search);
				response.sendRedirect(nextJSP);
			}
			else			
			{
				
				String nppId = "";
		    	
				/*
				String query = "select a.pk_seq from nhaphanphoi a, nhanvien b where a.ma = b.dangnhap and b.pk_seq = '" + userId + "'";
		    	
		    	
		    	dbutils db = new dbutils();
				ResultSet rs = db.get(query);
				try{
					if (rs != null){
						rs.next();
						nppId = rs.getString("pk_seq");
						rs.close();
					}else{
						nppId = "";
					}
				}catch(java.sql.SQLException e){}*/
				geso.dms.distributor.util.Utility dutil=new geso.dms.distributor.util.Utility();
				nppId = dutil.getIdNhapp(userId);

				
				String search = getSearchQuery(request, util, nppId, obj);   	
				obj.setUserId(userId);
				session.setAttribute("userId", userId);

				obj.createDdhlist(search);

				session.setAttribute("obj", obj);
				
				String nextJSP = "/AHF/pages/Distributor/DonDatHang.jsp";	    	
				System.out.print(search);
				response.sendRedirect(nextJSP);
			}
		}
		}
	}   


	private String getSearchQuery(HttpServletRequest request, Utility util, String nppId, IDondathangList obj){
//	    PrintWriter out = response.getWriter();
		
		String sku = util.ValidateParam(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("masp"))));
    	if (sku == null)
    		sku = "";
    	obj.setSKU(sku);
    	
    	String tungay = util.ValidateParam(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"))));
    	if (tungay == null)
    		tungay = "";    	
    	obj.setTungay(tungay);

    	String denngay = util.ValidateParam(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay"))));
    	if (denngay == null)
    		denngay = "";    	
    	obj.setDenngay(denngay);
    	   	
    	String trangthai = util.ValidateParam(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"))));   	
    	if (trangthai == null)
    		trangthai = "";    	
	   	
    	obj.setTrangthai(trangthai);
    		    
	    //String query = "select distinct a.ngaydat, a.pk_seq as chungtu,e.donvikinhdoanh, a.sotienavat, b.ten as nguoitao, c.ten as nguoisua, a.trangthai from dondathang a, nhanvien b, nhanvien c, dondathang_sp d, donvikinhdoanh e where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq and a.pk_seq = d.dondathang_fk and a.dvkd_fk = e.pk_seq and a.npp_fk='"+ nppId +"'";// order by a.trangthai, a.pk_seq";
    	String query = " select a.ngaydat, a.pk_seq as chungtu,e.donvikinhdoanh, a.sotienavat, b.ten as nguoitao, c.ten as nguoisua,"+ 
				" a.trangthai, sum(d.soluongduyet) as soluongduyet, sum(d.soluong) as soluong, a.dondathang_from_fk, isnull(a.ghichu, '') as ghichu "+
				" from dondathang a inner join nhanvien b on a.nguoitao = b.pk_seq inner join nhanvien c on a.nguoisua = c.pk_seq"+
				" inner join dondathang_sp d on a.pk_seq = d.dondathang_fk inner join donvikinhdoanh e on a.dvkd_fk = e.pk_seq"+
				" where a.npp_fk='"+ nppId +"'";
				
    	if (sku.length()>0){
			query = query + " and d.sanpham_fk ='" + sku + "'";
			
    	}
    	
    	if (tungay.length()>0){
			query = query + " and a.ngaydat >= '" + tungay+ "'";
    		
    	}

    	if (denngay.length()>0){
			query = query + " and a.ngaydat <= '" + denngay+ "'";
    		
    	}

    	if(trangthai.length() > 0){
    		query = query + " and a.trangthai = '" + trangthai + "'";
    		
    	}
    	query += " group by a.ngaydat, a.pk_seq, e.donvikinhdoanh, a.sotienavat, b.ten, c.ten, a.trangthai, a.dondathang_from_fk, a.ghichu";
    	//System.out.println("Tim kiem : "+query);
    	return query;
	}	
	
	private void Delete(String id, PrintWriter out){
		
		dbutils db = new dbutils();
		ResultSet rs = db.get("select count (ddh.pk_Seq) as num from dondathang ddh inner join denghidathang dndh on dndh.pk_Seq=ddh.denghidathang_fk  where ddh.pk_seq = '" + id + "'");
		//System.out.println("vao delete :"+ "select count(denghidathang_fk) as num from dondathang where pk_seq = '" + id + "'");
		try{
			rs.next();
			
			if (!(rs.getString("num").equals("0"))){
							String query = "select denghidathang_fk as dndhId from dondathang where pk_seq = '" + id + "'";
							ResultSet rs2 = db.get(query);
							System.out.println("vao denghidathang_fk :"+ query);
							if(rs2.next()){
				
							
							String dndhId = rs2.getString("dndhId");
							out.println("dndhId = "+ dndhId);
							rs2.close();
						
							
							rs2 = db.get("select * from dondathang_sp where dondathang_fk ='" + id  + "'");
							double total = 0;
							while(rs2.next()){
								String masp = rs2.getString("sanpham_fk");
								String sotienbvat = rs2.getString("sotienbvat").trim();
			
								ResultSet rs3 = db.get("select count(dadathang) as num from denghidathang_sp where denghidathang_fk='"+ dndhId+"' and sanpham_fk='" + masp + "'");	
			
								rs3.next();
								if (!(rs3.getString("num").equals("0"))){
									ResultSet rs4 = db.get("select dadathang  from denghidathang_sp where denghidathang_fk='"+ dndhId+"' and sanpham_fk='" + masp + "'");
									rs4.next();
															
									total = total + Double.valueOf(sotienbvat).doubleValue();
									String dadathang = rs4.getString("dadathang");
									
									double tmp= Double.valueOf(dadathang).doubleValue() - Double.valueOf(sotienbvat).doubleValue();
									
									if (tmp <0)
										tmp = 0;
									String command = "update denghidathang_sp set dadathang ='" + tmp + "' where sanpham_fk='" + masp + "' and denghidathang_fk='" + dndhId + "'";
									
									if(!db.update(command)){
										
									}
									rs4.close();
								}
								rs3.close();
							}
							rs2.close();
							rs2 = db.get("select dadathang from denghidathang where pk_seq ='" + dndhId + "'");
							rs2.next();
							total = Double.valueOf(rs2.getString("dadathang")).doubleValue() - Math.round(total*1.1);
							if (total <0)
								total = 0;
							if (total==0){
								db.update("update denghidathang set dadathang='" + total + "', trangthai='1' where pk_seq =" + dndhId + "");
							}else{
								db.update("update denghidathang set dadathang='" + total + "' where pk_seq =" + dndhId + "");
							}
						}
						rs.close();
			}
			
			String sql = "";
			sql = "delete from dondathang_sp where dondathang_fk='" + id + "'";
			System.out.println("vao dondathang_sp :"+ sql);
			db.update(sql);
			sql = "delete from thieuhang where dondathang_fk='" + id + "'";
			System.out.println("vao thieuhang :"+ sql);
			db.update(sql);
			sql = "delete from dondathang where pk_seq = " + id + "";
			System.out.println("vao dondathang :"+ sql);
			db.update(sql);
			db.shutDown();
		}catch(java.sql.SQLException e){
			
		}
	}
	
	
}