package geso.dms.center.servlets.chungloai;

import geso.dms.center.beans.chungloai.IChungloai;
import geso.dms.center.beans.chungloai.imp.Chungloai;
import geso.dms.center.beans.chungloai.imp.ChungloaiList;
import geso.dms.center.beans.chungloai.IChungloaiList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

 public class ChungloaiNewSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
	public ChungloaiNewSvl() {
		super();
	}   	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		IChungloai cloaiBean = new Chungloai();	
		dbutils db = new dbutils();
		// Collecting data from NhanhangNew.jsp
		Utility util = new Utility();
		
		String userId = util.antiSQLInspection(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"))));
		
    	String cloai = util.antiSQLInspection(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chungloai"))));
    	cloaiBean.setChungloai(cloai);
    	
    	String ngaytao = getDateTime();
    	cloaiBean.setNgaytao(ngaytao);
		
		String ngaysua = ngaytao;
		cloaiBean.setNgaysua(ngaysua);
		
		String nguoitao = userId;
		cloaiBean.setNguoitao(userId);
		
		String nguoisua = nguoitao;
		cloaiBean.setNguoisua(nguoisua);
    	
    	String trangthai;
    	if(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")))!= null)
			trangthai = "1";
		else
			trangthai = "0";
    	cloaiBean.setTrangthai(trangthai);
		
		String[] nhanhangSelected = request.getParameterValues("nhId");
		cloaiBean.setNhanhangSelected(nhanhangSelected);
		
		boolean error = false;
		if (nhanhangSelected == null){
			cloaiBean.setMessage("Vui lòng chọn Nhãn hàng");
			error = true;
		}

		if (cloai.trim().length()== 0){
			cloaiBean.setMessage("Vui lòng nhập vào Chủng loai");
			error = true;
		}

		
		HttpSession session = request.getSession();
		session.setAttribute("userId", util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"))));
		
		if (error){ //error in data entry
			ResultSet nhlist = (ResultSet)getNhanhangList(false, db);
			session.setAttribute("nhlist", nhlist);
			session.setAttribute("cloaiBean", cloaiBean);
    		session.setAttribute("userId", userId);
    		String nextJSP = "/AHF/pages/Center/ChungLoaiNew.jsp";
    		response.sendRedirect(nextJSP);
		}
		else{
			// userId is saved into session
			session.setAttribute("userId", nguoitao);
			ResultSet nhlist = (ResultSet)getNhanhangList(false, db);

			//if there is any error when saving Chungloai
			if (!cloaiBean.saveNewChungloai()){			
				session.setAttribute("cloaiBean", cloaiBean);
				session.setAttribute("nhlist", nhlist);
	    		session.setAttribute("userId", userId);
				String nextJSP = "/AHF/pages/Center/ChungLoaiNew.jsp";
	    		response.sendRedirect(nextJSP);
			}
			else{
			    // Collect all of chungloai, each chungloai is saved into Chungloai
				IChungloaiList obj = new ChungloaiList();
				
			   	ResultSet clList = getChungloaiBeanList("");			   	
			    obj.setClList(clList);	    
					
				// Save data into session
				session.setAttribute("obj", obj);
					
				session.setAttribute("userId", userId);
					
				String nextJSP = "/AHF/pages/Center/ChungLoai.jsp";
				response.sendRedirect(nextJSP);
							
			}
			
		}
		
	}   	  	    
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private ResultSet getNhanhangList(boolean all, dbutils db){
		String query;
		if (all)
			query = "select a.pk_seq, a.ten, a.trangthai from nhanhang a, nhanhang_chungloai b where a.pk_seq = b.nh_fk and b.checked='1' order by a.ten";
		else
			query = "select pk_seq, ten, trangthai from nhanhang where trangthai='1'";
		
		ResultSet rs =  db.get(query);
		return rs;
	}
	
	private ResultSet getChungloaiBeanList(String search){
		ResultSet rs1;
		String query;
		dbutils db = new dbutils();
		if(search.length()==0){
			query = "select a.pk_seq as clId, a.ten as chungloai,isnull(a.smartid,'') as ma,a.trangthai, c.pk_seq as nhId, c.ten as nhanhang, a.ngaytao, a.ngaysua, d.ten as nguoitao, e.ten as nguoisua from chungloai a, nhanhang_chungloai b, nhanhang c, nhanvien d, nhanvien e where a.pk_seq = b.cl_fk and b.nh_fk= c.pk_seq and a.nguoitao = d.pk_seq and a.nguoisua = e.pk_seq order by chungloai ";
			rs1 = db.get(query);   					
		}else{
			query = search;
			rs1 = db.get(query);
		}
		
		return rs1;
	}
	
	
}