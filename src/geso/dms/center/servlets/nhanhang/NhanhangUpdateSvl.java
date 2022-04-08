package geso.dms.center.servlets.nhanhang;

import geso.dms.center.beans.nhanhang.INhanhang;
import geso.dms.center.beans.nhanhang.INhanhangList;
import geso.dms.center.beans.nhanhang.imp.NhanhangList;
import geso.dms.center.beans.nhanhang.imp.Nhanhang;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class NhanhangUpdateSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;

	public NhanhangUpdateSvl() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    dbutils db ;
	    Utility util;
	    INhanhangList obj;
	    
	    db = new dbutils();
	    util = new Utility();
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();

	    String querystring = request.getQueryString();
	    
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String nhId = util.getId(querystring).split(";")[0];
	    out.println(nhId);
		
	    String dvkdId = util.getId(querystring).split(";")[1];
	    out.println(dvkdId);
	    
	    String nganhhangId = util.getId(querystring).split(";")[2];
	    out.println(nganhhangId);
	   
	    //dbutils db = new dbutils();
		String query =  "select ten, trangthai,isnull(smartid,'') as ma from nhanhang where pk_seq = '"+ nhId +"' and dvkd_fk='" + dvkdId + "'" ;  	
	    String[] param = new String[12];
        ResultSet rs =  db.get(query);
        try{
        	rs.next();
			param[0]= nhId;
			param[1]= rs.getString("ten");
			param[2]= "";	
			param[3]= "";
			param[4]= "";
			param[5]= "";
			param[6]= rs.getString("trangthai");
			param[7]= "";
			param[8]= dvkdId;
			//param[9]= rs.getString("diengiai");
			param[10]= nganhhangId;
			param[11] = rs.getString("ma");
    	    INhanhang nhBean = new Nhanhang(param);

			// Data is saved into session
			session.setAttribute("nhanhangBean", nhBean);
			session.setAttribute("userId", userId);
			
			String nextJSP = "/AHF/pages/Center/NhanHangUpdate.jsp";
			if (action.equals("display")) {
				 nextJSP = "/AHF/pages/Center/NhanHangDisplay.jsp";
			}
       		response.sendRedirect(nextJSP);

        }catch(Exception e){
	    	out.println("error here, Sir!");
	    }

	}  	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
		dbutils db ;
	    Utility util;
	    INhanhangList obj;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		db = new dbutils();
		obj = new NhanhangList();
//		PrintWriter out = response.getWriter();
		INhanhang nhBean = new Nhanhang();	
		
		// Collecting data from DonViKinhDoanhUpdate.jsp
		
		util = new Utility();
		
    	String nhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhId")));
    	nhBean.setId(nhId);

		String nhanhang = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhanhang")));
		nhBean.setNhanhang(nhanhang);

    	String dvkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")));
    	//nhBean.setDvkdId(dvkdId);
    	
    	String nganhhangId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nganhhangId")));
    	//nhBean.setNganhHangId(nganhhangId);
		
    	String ngaysua = getDateTime();
    	nhBean.setNgaysua(ngaysua);
		
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		nhBean.setNguoisua(userId);
		
		String ma = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ma")));
		if(ma == null)
			ma = "";
		nhBean.setma(ma);
		
		/*String diengiai=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai"));
		nhBean.setDiengiai(diengiai);*/
    	
    	String trangthai;
    	if(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")))!= null)
			trangthai = "1";
		else
			trangthai = "0";
    	nhBean.setTrangthai(trangthai);
		
		boolean error = false;
		nhBean.setMessage("");
		if (nhanhang.trim().length()> 0)
			nhBean.setNhanhang(nhanhang);
		else{
			nhBean.setMessage("Vui lòng nhập vào Tên kho");
			error = true;
		}
		if(nganhhangId.trim().length()>0)
		{
			nhBean.setNganhHangId(nganhhangId);
		}else{
			nhBean.setMessage("Vui lòng chọn Ngành hàng");
			error = true;
		}
		
		session.setAttribute("userId", geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		
		if(action.equals("loc")==false && nganhhangId.trim().length()>0 && dvkdId.trim().length()<= 0)
		{
			nhBean.setMessage("Vui lòng chọn Đơn vị kinh doanh");
			error = true;
		}
		else
		{	
			nhBean.setDvkdId(dvkdId);
		}
		System.out.println("action NhanhangUpdateSvl: '" + action + "' ");		
		
		if (error){ //Error in data entry
			System.out.println("-----> vo if " );
			
			session.setAttribute("nhanhangBean", nhBean);
			session.setAttribute("userId", userId);
			session.setAttribute("nhBean", nhBean);   		
    		String nextJSP = "/AHF/pages/Center/NhanHangUpdate.jsp";
    		response.sendRedirect(nextJSP);
		}
		else{
			// userId is saved into session
			session.setAttribute("userId", userId);
			
			System.out.println("-----> vo else " );
			
			if(action.equals("loc"))
			{
				if(nhBean.getNganhHangId().length()>0)
				{
					String query = "select a.pk_seq,a.DONVIKINHDOANH from DONVIKINHDOANH a "
							+ " where a.PK_SEQ in (select b.DVKD_FK from NGANHHANG b where b.DVKD_FK=a.PK_SEQ and b.PK_SEQ="
							+ nhBean.getNganhHangId()
							+ " ) ";
					System.out.println("**query: '" + query + "' ");
					ResultSet rs = db.get(query);
					if(rs!=null)
					{
						try {

							while(rs.next())
							{
								nhBean.setDvkdId(rs.getString("pk_seq"));								
							}
						rs.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				}				
				session.setAttribute("nhanhangBean", nhBean);
				String nextJSP = "/AHF/pages/Center/NhanHangUpdate.jsp";
	    		response.sendRedirect(nextJSP);
			}
			else
			{
				//if there is any error when saving Business Unit
				if (!nhBean.UpdateNhanhang())
				{
					session.setAttribute("nhanhangBean", nhBean);
					String nextJSP = "/AHF/pages/Center/NhanHangUpdate.jsp";
		    		response.sendRedirect(nextJSP);
				}
				else{
				    // Collect all of Brands, each Brands is saved into Nhanhang
				    String query = "select a.pk_seq, a.ten,isnull(a.smartid,'') as ma, b.pk_seq as dvkdId, b.donvikinhdoanh, a.trangthai, a.ngaytao, a.ngaysua, c.ten as nguoitao, d.ten as nguoisua, a.nganhhang_fk from nhanhang a, donvikinhdoanh b, nhanvien c, nhanvien d where a.dvkd_fk = b.pk_seq and a.nguoitao = c.PK_SEQ and a.nguoisua = d.PK_SEQ  ";
				    //dbutils db = new dbutils();
				    ResultSet nhlist = db.get(query);
				    
					// Save data into session
					obj.setNhlist(nhlist);
				    session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);
						
					String nextJSP = "/AHF/pages/Center/NhanHang.jsp";
					response.sendRedirect(nextJSP);
								
				}
				
			}
			
			
		}
		
	}   	  	 
	private List<INhanhang> getNhBeanList(String query){
		
		dbutils db ;
	    Utility util;
	    INhanhangList obj;
		
		db = new dbutils();
		ResultSet rs =  db.get(query);
		List<INhanhang> nhanhanglist = new ArrayList<INhanhang>();
		INhanhang nhanhangBean;
		String[] param = new String[11];
		System.out.println("query la.........."+query);
		if (!(rs==null)){
		try{
			while(rs.next()){
				param[0]= rs.getString("pk_seq");
				param[1]= rs.getString("ten");
				param[2]= rs.getDate("ngaytao").toString();
				param[3]= rs.getDate("ngaysua").toString();
				param[4]= rs.getString("nguoitao");
				param[5]= rs.getString("nguoisua");			
				param[6]= rs.getString("trangthai");
				param[7]= rs.getString("donvikinhdoanh");
				param[8]= rs.getString("dvkdId");
				param[9]= rs.getString("diengiai");
				nhanhangBean = new Nhanhang(param);
				nhanhanglist.add(nhanhangBean);
			}
		}catch(Exception e){}
		}
		return nhanhanglist;
	}

	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
}
