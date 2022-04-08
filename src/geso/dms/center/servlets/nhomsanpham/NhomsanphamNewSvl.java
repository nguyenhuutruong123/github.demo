package geso.dms.center.servlets.nhomsanpham;

import geso.dms.center.beans.nhomsanpham.INhomsanpham;
import geso.dms.center.beans.nhomsanpham.INhomsanphamList;
import geso.dms.center.beans.nhomsanpham.imp.Nhomsanpham;
import geso.dms.center.beans.nhomsanpham.imp.NhomsanphamList;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class NhomsanphamNewSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
   
   public NhomsanphamNewSvl() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		
		INhomsanpham nspBean = new Nhomsanpham();	
		
		// Collecting data from NhomsanphamNew.jsp
		
		Utility util = new Utility();
		
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
    	String ten = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ten")));
    	nspBean.setTen(ten);
    	
    	String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
    	nspBean.setDiengiai(diengiai);
    	System.out.println("___Dien giai la: " + diengiai);

    	String thanhvien = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thanhvien")));
    	nspBean.setThanhvien(thanhvien);

    	String dvkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")));
    	if (dvkdId != null)
    		nspBean.setDvkdId(dvkdId);

    	String nhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhId")));
    	if (nhId != null)
    		nspBean.setNhId(nhId);

    	String clId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("clId")));
    	if (clId != null)
    		nspBean.setClId(clId);
    	
    	String loainhom = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("lnhom")));    	
    	if(loainhom!=null)
    	nspBean.setLoainhom(loainhom);
    	
    	String ngaytao = getDateTime();
		nspBean.setNgaytao(ngaytao);
		
		String ngaysua = ngaytao;
		nspBean.setNgaysua(ngaysua);
		
		String nguoitao = userId;
		nspBean.setNguoitao(userId);
		
		String nguoisua = nguoitao;
    	nspBean.setNguoisua(nguoisua);
    	
    	String trangthai;
    	if(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")))!= null)
			trangthai = "1";
		else
			trangthai = "0";
		nspBean.setTrangthai(trangthai);
			
		boolean error = false;
		if (ten.trim().length()> 0)
			nspBean.setTen(ten);
		else{
			nspBean.setMessage("Vui long nhap vao nhom san pham");
			error = true;
		}

		String[] nhom = request.getParameterValues("nhom");
		nspBean.setNhomsp(nhom);
		
		String[] sanpham = request.getParameterValues("sanpham");
		nspBean.setSanpham(sanpham);
		
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		session.setAttribute("userId", util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"))));
		
		if (action.equals("filter") || error){
			nspBean.UpdateRS();	
	    	// Save Data into session
    		session.setAttribute("nspBean", nspBean);
    		session.setAttribute("userId", userId);
    		
	    	String nextJSP = "/AHF/pages/Center/NhomSanPhamNew.jsp";
    		response.sendRedirect(nextJSP);
    		
		}else{
			// userId is saved into session
			session.setAttribute("userId", nguoitao);
			//if there is any error when saving a Brand?
			if (!nspBean.saveNewNsp()){			
				session.setAttribute("nspBean", nspBean);
	    		session.setAttribute("userId", userId);
				String nextJSP = "/AHF/pages/Center/NhomSanPhamNew.jsp";
	    		response.sendRedirect(nextJSP);
			}
			else{
					INhomsanphamList obj = new NhomsanphamList();
				    List<INhomsanpham> nsplist = new ArrayList<INhomsanpham>(); 
				    
				    getNspBeanList("0", nsplist);
				    
					// Save data into session
				    obj.setNspList(nsplist);
				    
					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);

					String nextJSP = "/AHF/pages/Center/NhomSanPham.jsp";
					response.sendRedirect(nextJSP);

							
			}
		}

	}

	private void  getNspBeanList(String parent, List<INhomsanpham> nsplist){	
    	
		
		dbutils db = new dbutils();
		
	   	String query = "select isnull(a.smartid,'') as ma,a.nsp_parent_fk as parent,a.loainhom, a.pk_seq, a.ten, a.diengiai, a.loaithanhvien, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from nhomsanpham a, nhanvien b, nhanvien c where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ and a.type='0' and a.loainhom='0' and a.nsp_parent_fk = '" + parent + "' order by pk_seq";
	   	ResultSet rs = db.get(query);
	   	try{	
	   		String[] param = new String[12];
    		INhomsanpham nspBean;
    		while (rs.next()){	    			
				param[0]= rs.getString("pk_seq");
				param[1]= rs.getString("ten");	
				param[2]= rs.getString("diengiai");
				param[3]= rs.getString("loaithanhvien");
				param[4]= rs.getString("trangthai");
				param[5]= rs.getString("ngaytao");
				param[6]= rs.getString("ngaysua");
				param[7]= rs.getString("nguoitao");
				param[8]= rs.getString("nguoisua");			
				param[9]= rs.getString("parent");
				param[10]=rs.getString("loainhom");
				param[11]= rs.getString("ma");
				nspBean = new Nhomsanpham(param,false);					
				nsplist.add(nspBean);
				getNspBeanList(param[0], nsplist);
    		}    		
    		if(rs!=null){
    			rs.close();
    		}
	   	}catch(Exception e){}
	}
	
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
}