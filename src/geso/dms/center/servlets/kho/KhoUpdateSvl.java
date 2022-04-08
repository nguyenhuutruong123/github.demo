package geso.dms.center.servlets.kho;

import geso.dms.center.beans.kho.IKho;
import geso.dms.center.beans.kho.IKhoList;
import geso.dms.center.beans.kho.imp.Kho;
import geso.dms.center.beans.kho.imp.KhoList;
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

 public class KhoUpdateSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   

	public KhoUpdateSvl() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		
		
		dbutils db  = new dbutils();
		Utility util = new Utility();
		
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");

	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();

	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
 
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String khoId = util.getId(querystring);
	    out.println(khoId);
		
		//dbutils db = new dbutils();
		String query =  "select ten, diengiai, trangthai from kho where pk_seq = '"+ khoId +"'";
	        	
	    String[] param = new String[10];
        ResultSet rs =  db.get(query);
        
        try{
        	rs.next();
			param[0]= khoId;
			param[1]= rs.getString("ten");
			param[2]= rs.getString("diengiai");	
			param[3]= "";
			param[4]= "";
			param[5]= "";
			param[6]= "";			
			param[7]= rs.getString("trangthai");
    	    IKho khoBean = new Kho(param);

			// Data is saved into session
			session.setAttribute("khoBean", khoBean);
			session.setAttribute("userId", userId);
			String nextJSP=  "/AHF/pages/Center/KhoUpdate.jsp";
			if (action.equals("display")) {
				 nextJSP = "/AHF/pages/Center/KhoDisplay.jsp";
			}

			
       		response.sendRedirect(nextJSP);
       		if(rs!=null) rs.close();
        	if(db!=null) db.shutDown();
        }catch(Exception e){
	    	out.println("error here, Sir!");
	    }

	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		dbutils db  = new dbutils();
		Utility util = new Utility();
		
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		PrintWriter out = response.getWriter();
		IKho khoBean = new Kho();	
		
		// Collecting data from DonViKinhDoanhUpdate.jsp
		
    	String khoId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khoId")));
    	khoBean.setId(khoId);

		String ten = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ten")));
    	khoBean.setTen(ten);

		String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
    	khoBean.setDiengiai(diengiai);

    	String ngaysua = getDateTime();
		khoBean.setNgaysua(ngaysua);
		
		String nguoisua = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
    	khoBean.setNguoisua(nguoisua);
    	
    	String trangthai;
    	System.out.println("TTT: " + util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"))));
    	if(util.antiSQLInspection(request.getParameter("trangthai"))!= null && util.antiSQLInspection(request.getParameter("trangthai")).trim().length()>0)
			trangthai = "1";
		else
			trangthai = "0";
		khoBean.setTrangthai(trangthai);
		
		boolean error = false;
		if (ten.trim().length()> 0)
			khoBean.setTen(ten);
		else{
			khoBean.setMessage("Vui lòng nhập vào tên kho");
			error = true;
		}

	
		if (error){ //Error in data entry
			session.setAttribute("userId", nguoisua);
			session.setAttribute("khoBean", khoBean);   		
    		String nextJSP = "/AHF/pages/Center/KhoUpdate.jsp";
    		response.sendRedirect(nextJSP);
		}
		else{
			// userId is saved into session
			session.setAttribute("userId", nguoisua);
		
			//if there is any error when saving Business Unit

			if (!khoBean.UpdateKho()){
				session.setAttribute("khoBean", khoBean);
				String nextJSP = "/AHF/pages/Center/KhoUpdate.jsp";
	    		response.sendRedirect(nextJSP);
			}
			else{
			    // Collect all of Busines unit, each business unit is saved into NccInfoBean
			   	String query = "select a.pk_seq, a.ten, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from kho a, nhanvien b, nhanvien c where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ";
			   	
				ResultSet rs =  db.get(query);
				List<IKho> kholist = new ArrayList<IKho>();
				String[] param = new String[10];
				try{
					while(rs.next()){
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
					
					IKhoList obj = new KhoList();
					obj.setKhoList(kholist);
					session.setAttribute("obj", obj);
					// Data is saved into session			
					String nextJSP = "/AHF/pages/Center/Kho.jsp";
					response.sendRedirect(nextJSP);
				 
				}catch(Exception e){
					out.println(e.toString());
				}							
		}			
		}
		
	}   	  	 
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
}