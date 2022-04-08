package geso.dms.center.servlets.gioihancongnott;

import geso.dms.center.beans.gioihancongnott.IGioiHanCongNoTT;
import geso.dms.center.beans.gioihancongnott.IGioiHangCongNoTT_Npp;
import geso.dms.center.beans.gioihancongnott.imp.GioiHanCongNoTT;
import geso.dms.center.beans.gioihancongnott.imp.GioiHangCongNo_Npp;
import geso.dms.distributor.beans.gioihancongno.IGioihancongnoList;
import geso.dms.distributor.beans.gioihancongno.imp.Gioihancongno;
import geso.dms.distributor.beans.gioihancongno.imp.GioihancongnoList;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class GioiHanCongNoTTNewSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GioiHanCongNoTTNewSvl() {
        super();
        // TODO Auto-generated constructor stub
    }
    private String getDateTime() 
	{
    	  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
          Date date = new Date();
          return dateFormat.format(date);	
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	  
	    	    
	    HttpSession session = request.getSession();	    
	   
	    Utility util = new Utility();	  
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);	 	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));	    
	    String action = util.getAction(querystring);

	    String ghcnId = util.getId(querystring);
	    IGioiHanCongNoTT  obj = new GioiHanCongNoTT(ghcnId);
		dbutils db=new dbutils();   
	    if (action.equals("update")){	   		  	    	
	   
	  //truyen qua rskhuvuc
		String sqlgetkhuvuc="select pk_seq,ten from khuvuc";
    	ResultSet rs_khuvuc=db.get(sqlgetkhuvuc);
    	session.setAttribute("rs_khuvuc", rs_khuvuc);
	    obj.setUserId(userId);
		session.setAttribute("ghcnBean", obj);
		String nextJSP = "/AHF/pages/Center/GioiHanCongNoTTUpdate.jsp";                                    
		response.sendRedirect(nextJSP);
	    }else if(action.equals("delete")){
	    	obj.DeleteGioiHanCongNoTT();
	    	 obj.setListGioHanCongNo("");
	 	    
	 		session.setAttribute("obj", obj);
	 				
	 		String nextJSP = "/AHF/pages/Center/GioiHanCongNoTT.jsp";
	 		                                     
	 		response.sendRedirect(nextJSP);
	    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		dbutils db=new dbutils();
		IGioiHanCongNoTT ghcnBean =new GioiHanCongNoTT();
		
		Utility util = new Utility();
		
	    String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));	
	    if(id == null){  	
	    	ghcnBean = new GioiHanCongNoTT();
	    }else{
	    	ghcnBean = new GioiHanCongNoTT(id);
	    }
	    ghcnBean.setId(id);	    
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		ghcnBean.setUserId(userId);
		ghcnBean.setNguoisua(userId);
		ghcnBean.setNguoitao(userId);
		
    	String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
		if (diengiai == null)
			diengiai = "";				
    	ghcnBean.setDiengiai(diengiai);    	
    	
		String songayno = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("songayno")));
		if (songayno == null)
			songayno = "";
		ghcnBean.setSongayno(songayno);
		
		ghcnBean.setNgaytao(this.getDateTime());
		ghcnBean.setNgaysua(this.getDateTime());
		String sotienno = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sotienno")));
		if (sotienno == null)
			sotienno = "";
		ghcnBean.setSotienno(sotienno);
		
		String khuvucid = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucid")));
		session.setAttribute("khuvucid", khuvucid);
		if (khuvucid == null){
			khuvucid = "";
		}
		String formname=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("formname")));
		String nextJSP;
		if(formname.equals("newform")){
			nextJSP = "/AHF/pages/Center/GioiHanCongNoTTNew.jsp";
		}else{
			nextJSP = "/AHF/pages/Center/GioiHanCongNoTTUpdate.jsp";
		}
		List<IGioiHangCongNoTT_Npp> list=new ArrayList<IGioiHangCongNoTT_Npp>();
		String ngaysua = getDateTime();
    	ghcnBean.setNgaysua(ngaysua);
		
		boolean error = false;
				
		if (diengiai.trim().length()== 0){
			ghcnBean.setMessage("Vui Long Nhap Dien Giai Muc Chiet Khau");
			error = true;
		}

		if (songayno.trim().length()== 0){
			ghcnBean.setMessage("Vui Long Nhap So Ngay Duoc No");
			error = true;
		}
		
		if (sotienno.trim().length()== 0){
			ghcnBean.setMessage("Vui Long Nhap So Tien Duoc Phep No");
			error = true;
		}
		//truyen qua rskhuvuc
		String sqlgetkhuvuc="select pk_seq,ten from khuvuc";
    	ResultSet rs_khuvuc=db.get(sqlgetkhuvuc);
    	session.setAttribute("rs_khuvuc", rs_khuvuc);
    	
 		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
 		Hashtable<String, String> hatable=new Hashtable<String, String>();
	    
			String[] idnhapp = request.getParameterValues("idnhapp");
			String[] tennhapp = request.getParameterValues("tennhapp");
			String[] diachinhapp = request.getParameterValues("diachinhapp");
			String[] valuecheck = request.getParameterValues("valuecheck");
			String[] khuvuc=request.getParameterValues("tenkhuvuc");
			
			if(idnhapp!= null &&  idnhapp.length>0){
				int m=0;
				while(m<idnhapp.length){
					IGioiHangCongNoTT_Npp npp=new GioiHangCongNo_Npp();
					npp.setDiaChi(diachinhapp[m]);
					npp.setID(valuecheck[m]);
					npp.setIDNhaPP(idnhapp[m]);
					npp.setTenNhaPP(tennhapp[m]);
					npp.setKhuVuc(khuvuc[m]);
					//Neu nha phan phoi nay duoc cho thi moi load lai
					if(npp.getId().equals("1")){
					list.add(npp);
					hatable.put(npp.getIdNhaPp(), npp.getIdNhaPp());
					}
					m++;
				}
			}
			ghcnBean.setListNhapp(list);
	
		if(action.equals("save"))
		{		if(error ==false){
				if ((ghcnBean.SaveGioiHanCongNoTT())){	
					ghcnBean.setListGioHanCongNo("");
					session.setAttribute("obj", ghcnBean);
					 nextJSP = "/AHF/pages/Center/GioiHanCongNoTT.jsp";
				}
				}

		}else if(action.equals("update"))
		{
			if(error ==false){
				if ((ghcnBean.EditGioiHanCongNoTT())){	
					ghcnBean.setListGioHanCongNo("");
					session.setAttribute("obj", ghcnBean);
					 nextJSP = "/AHF/pages/Center/GioiHanCongNoTT.jsp";
				}
				}
				
		}else if(action.equals("loc")){
			String sql_getdata="";
			if(khuvucid.equals("")){
				 	sql_getdata="select a.pk_seq,a.ten,a.diachi,b.ten as khuvuc  from nhaphanphoi a inner join khuvuc b on a.khuvuc_fk=b.pk_seq ";
			}else{
					sql_getdata="select a.pk_seq,a.ten,a.diachi,b.ten as khuvuc  from nhaphanphoi a inner join khuvuc b on a.khuvuc_fk=b.pk_seq   where khuvuc_fk="+khuvucid;
			}
			ResultSet rs=db.get(sql_getdata);
	    	if(rs!=null){
	    		try{
	    			while(rs.next()){
	    				IGioiHangCongNoTT_Npp npp=new GioiHangCongNo_Npp();
	    				npp.setDiaChi(rs.getString("diachi"));
	    				npp.setIDNhaPP(rs.getString("pk_seq"));
	    				npp.setTenNhaPP(rs.getString("ten"));
	    				npp.setKhuVuc(rs.getString("khuvuc"));
	    				npp.setID("0");//Mac dinh set =0 de check =false;
	    				if(!hatable.containsKey(npp.getIdNhaPp())){
	    					list.add(npp);
	    				}
	    				
	    			}
	    		}catch(Exception er){
	    			System.out.println("Error GioiHanCongNoTTSvl : 167"+ er.toString());
	    		}
	    	}
	    	ghcnBean.setListNhapp(list);
		}
		session.setAttribute("ghcnBean", ghcnBean);			
			response.sendRedirect(nextJSP);
	}

}
