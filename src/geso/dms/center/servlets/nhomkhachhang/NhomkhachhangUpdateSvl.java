package geso.dms.center.servlets.nhomkhachhang;

import geso.dms.center.beans.nhomkhachhang.INhomkhachhang;
import geso.dms.center.beans.nhomkhachhang.INhomkhachhangList;
import geso.dms.center.beans.nhomkhachhang.imp.Nhomkhachhang;
import geso.dms.center.beans.nhomkhachhang.imp.NhomkhachhangList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class NhomkhachhangUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PrintWriter out;
	
    public NhomkhachhangUpdateSvl() {
        super();

    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		HttpSession session = request.getSession();
		this.out = response.getWriter();
		dbutils db = new dbutils();
		Utility util = new Utility();
	
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
    
		out.println(userId);
    
		if (userId.length()==0)
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
    	    
		String id = util.getId(querystring);
      	
		String query = "select a.pk_seq,isnull(a.ten,'') as ten, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from nhomkhachhang a, nhanvien b, nhanvien c where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ and a.pk_seq='" + id + "'";
	
		ResultSet rs =  db.get(query);
		try{
			rs.next();
			String[] param = new String[10];
			param[0]= id;
			param[1]= rs.getString("diengiai");	
			param[2]= rs.getString("trangthai");
			param[3]= rs.getString("ngaytao");
			param[4]= rs.getString("ngaysua");
			param[5]= rs.getString("nguoitao");
			param[6]= rs.getString("nguoisua");
			param[7] = rs.getString("ten");
    	
			INhomkhachhang nkhBean = new Nhomkhachhang(param);
	    
			nkhBean.UpdateRS();
			session.setAttribute("nkhBean", nkhBean);
   		
			String nextJSP = "/AHF/pages/Center/NhomKhachHangUpdate.jsp";
			response.sendRedirect(nextJSP);
   		
		}catch(Exception e){
			out.println(e.toString());
		}
   
	}  	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		INhomkhachhang nkhBean = new Nhomkhachhang();
		
		dbutils db;
		
		Utility util = new Utility();
		
		db = new dbutils();
		//Get data from NhacungcapUpdate.jsp	
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	
		String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nkhId")));
		if(id == null){
			id = "";
		}
		nkhBean.setId(id);

		String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
		nkhBean.setDiengiai(diengiai);
		
		String tennhom = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tennhom")));
		if(tennhom == null)
			tennhom = "";
		nkhBean.setTenNhom(tennhom);
		String vungId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")));
		nkhBean.setVungId(vungId);
		System.out.println("Vung Nek : "+vungId);
		String kvId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kvId")));	
		nkhBean.setKvId(kvId);

		String nppId= util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
		nkhBean.setNppId(nppId);
	
		String HchId= util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("HchId")));
		nkhBean.setHchID(HchId);
		
		String LchId= util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("LchId")));
		nkhBean.setLchID(LchId);
		
		
		String VThId= util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("VthId")));
		nkhBean.setVtch(VThId);
		
		String ngaytao = getDateTime();
		nkhBean.setNgaytao(ngaytao);
	
		String ngaysua = ngaytao;
		nkhBean.setNgaysua(ngaysua);
	
		String nguoitao = userId;
		nkhBean.setNguoitao(userId);
	
		String nguoisua = nguoitao;
		nkhBean.setNguoisua(nguoisua);
	
		String trangthai;
		if(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")))!= null)
			trangthai = "1";
		else
			trangthai = "0";
		nkhBean.setTrangthai(trangthai);
		
		boolean error = false;
		if (diengiai.trim().length()> 0)
			nkhBean.setDiengiai(diengiai);
		else{
			nkhBean.setMessage("Vui lòng nhập diễn giải nhóm khách hàng");
			error = true;
		}
		
		if (tennhom.trim().length()> 0)
			nkhBean.setTenNhom(tennhom);
		else{
			nkhBean.setMessage("Vui lòng nhập tên nhóm khách hàng");
			error = true;
		}

		String[] khachhang = request.getParameterValues("khachhang");
		nkhBean.setKhachhang(khachhang);
	
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));

		out.println(action);
	
		if (action.equals("filter") || error){		
			nkhBean.UpdateRS();
			session.setAttribute("nkhBean", nkhBean);
			session.setAttribute("userId", userId);
			
			String nextJSP;
			if (id.length()>0){
				nextJSP = "/AHF/pages/Center/NhomKhachHangUpdate.jsp";
			}else{
				nextJSP = "/AHF/pages/Center/NhomKhachHangNew.jsp";
			}
			response.sendRedirect(nextJSP);    		
		}else{
			session.setAttribute("userId", nguoitao);

			if (action.equals("new")){
				if (!nkhBean.saveNewNkh()){
					nkhBean.UpdateRS();				
					session.setAttribute("nkhBean", nkhBean);
    		
					String nextJSP = "/AHF/pages/Center/NhomKhachHangNew.jsp";
					response.sendRedirect(nextJSP);
				}
				else{
					INhomkhachhangList obj = new NhomkhachhangList();
					List<INhomkhachhang> nkhlist = new ArrayList<INhomkhachhang>(); 
		    
					getNkhBeanList(nkhlist,"");
		    
				// 	Save data into session
					obj.setNkhList(nkhlist);
		    
					session.setAttribute("obj", obj);
					String nextJSP = "/AHF/pages/Center/NhomKhachHang.jsp";
					response.sendRedirect(nextJSP);
			
				}
				
			}else{
				if (!nkhBean.updateNkh()){
					nkhBean.UpdateRS();				
					session.setAttribute("nkhBean", nkhBean);
    		
					String nextJSP = "/AHF/pages/Center/NhomKhachHangUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else{
					INhomkhachhangList obj = new NhomkhachhangList();
					List<INhomkhachhang> nkhlist = new ArrayList<INhomkhachhang>(); 
		    
					getNkhBeanList(nkhlist,"");
		    
				// 	Save data into session
					obj.setNkhList(nkhlist);
		    
					session.setAttribute("obj", obj);
					String nextJSP = "/AHF/pages/Center/NhomKhachHang.jsp";
					response.sendRedirect(nextJSP);
			
				}
			}
		}
	
	}   	  	    
	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);	
	}

	private void  getNkhBeanList(List<INhomkhachhang> nkhlist, String search){	
		String query;
    
		dbutils db = new dbutils();
		if (search.length() > 0){
			query = search;
		}else{
			query = "select a.pk_seq,isnull(a.ten,'') as ten, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from nhomkhachhang a, nhanvien b, nhanvien c where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ order by diengiai";
		}
		ResultSet rs = db.get(query);
		try{	
			String[] param = new String[11];
			INhomkhachhang nkhBean;
			while (rs.next()){	    			
				param[0]= rs.getString("pk_seq");
				param[1]= rs.getString("diengiai");				
				param[2]= rs.getString("trangthai");
				param[3]= rs.getString("ngaytao");
				param[4]= rs.getString("ngaysua");
				param[5]= rs.getString("nguoitao");
				param[6]= rs.getString("nguoisua");			
				param[7] = rs.getString("ten");
				nkhBean = new Nhomkhachhang(param);					
				nkhlist.add(nkhBean);
			}    		
		}catch(Exception e){}
	}

}
