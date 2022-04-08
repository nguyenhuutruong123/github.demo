package geso.dms.center.servlets.mucchietkhautt;

import geso.dms.center.beans.mucchietkhautt.IMucChietKhauTT;
import geso.dms.center.beans.mucchietkhautt.IMucChietKhauTT_NhaPP;
import geso.dms.center.beans.mucchietkhautt.imp.MucChietKhauTT;
import geso.dms.center.beans.mucchietkhautt.imp.MucChietKhauTT_NhaPP;
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


public class MucChietKhauTTNewSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MucChietKhauTTNewSvl() {
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
	    IMucChietKhauTT  obj = new MucChietKhauTT(ghcnId);
		dbutils db=new dbutils();   
	    if (action.equals("update")){	   		  	    	
	   
	    	//truyen qua rskhuvuc
	    	String sqlgetkhuvuc="select pk_seq,ten from khuvuc";
	    	ResultSet rs_khuvuc=db.get(sqlgetkhuvuc);
	    	session.setAttribute("rs_khuvuc", rs_khuvuc);
	    	obj.setUserId(userId);
	    	session.setAttribute("ghcnBean", obj);
	    	String nextJSP = "/AHF/pages/Center/MucChietKhauTTUpdate.jsp";                                    
	    	response.sendRedirect(nextJSP);
	    }else if(action.equals("delete")){
	    	obj.DeleteMucChietKhauTT();
	    	 obj.setListMucChietKhau("");
	 		session.setAttribute("obj", obj);	 				
	 		String nextJSP = "/AHF/pages/Center/MucChietKhauTT.jsp";	 		                                     
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
		IMucChietKhauTT ghcnBean =new MucChietKhauTT();
		
		Utility util = new Utility();
		
	    String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));	
	    if(id == null){  	
	    	ghcnBean = new MucChietKhauTT();
	    }else{
	    	ghcnBean = new MucChietKhauTT(id);
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
    	
		String mucchietkhau = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("mucchietkhau")));
		if (mucchietkhau == null)
			mucchietkhau = "0";
		try{
		ghcnBean.setChietKhau(Double.parseDouble(mucchietkhau));
		}catch(Exception er){
			ghcnBean.setChietKhau(0);
		}
		ghcnBean.setNgaytao(this.getDateTime());
		ghcnBean.setNgaysua(this.getDateTime());
	
		
		String khuvucid = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucid")));
		session.setAttribute("khuvucid", khuvucid);
		if (khuvucid == null){
			khuvucid = "";
		}
		String formname=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("formname"));
		String nextJSP;
		if(formname.equals("newform")){
			nextJSP = "/AHF/pages/Center/MucChietKhauTTNew.jsp";
		}else{
			nextJSP = "/AHF/pages/Center/MucChietKhauTTUpdate.jsp";
		}
		List<IMucChietKhauTT_NhaPP> list=new ArrayList<IMucChietKhauTT_NhaPP>();
		String ngaysua = getDateTime();
    	ghcnBean.setNgaysua(ngaysua);
		
		boolean error = false;
				
		if (diengiai.trim().length()== 0){
			ghcnBean.setMessage("Vui Long Nhap Dien Giai Muc Chiet Khau");
			error = true;
		}

	
		
		if (ghcnBean.getChietKhau()<=0||ghcnBean.getChietKhau()>100 ){
			ghcnBean.setMessage("Vui Long Nhap Chiet Khau Lon Hon 0 Va Nho Hon 100");
			error = true;
		}
		//truyen qua rskhuvuc
		String sqlgetkhuvuc="select pk_seq,ten from khuvuc";
    	ResultSet rs_khuvuc=db.get(sqlgetkhuvuc);
    	session.setAttribute("rs_khuvuc", rs_khuvuc);
    	
 		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
 		//bang nay dung de luu id nha phan phoi de khi loc tiep khu vuc se kiem tra nhung nha phan phoi chua co trong bang hastble thi insert them vao
 		Hashtable<String, String> hatable=new Hashtable<String, String>();

			String[] idnhapp = request.getParameterValues("idnhapp");
			String[] tennhapp = request.getParameterValues("tennhapp");
			String[] diachinhapp = request.getParameterValues("diachinhapp");
			String[] valuecheck = request.getParameterValues("valuecheck");
			String[] tenkhuvuc=request.getParameterValues("tenkhuvuc");
			if(idnhapp!=null && idnhapp.length>0){
				int m=0;
				while(m<idnhapp.length){
					IMucChietKhauTT_NhaPP npp=new MucChietKhauTT_NhaPP();
					npp.setDiaChi(diachinhapp[m]);
					npp.setID(valuecheck[m]);
					npp.setIDNhaPP(idnhapp[m]);
					npp.setTenNhaPP(tennhapp[m]);
					npp.setKhuVuc(tenkhuvuc[m]);
					if(npp.getId().equals("1")){
					list.add(npp);
					}
					m++;
					hatable.put(npp.getIdNhaPp(),npp.getIdNhaPp() );
				}
			}
			ghcnBean.setListNhaPhanPhoi(list);
		if(action.equals("save"))
		{		if(error ==false){
				if ((ghcnBean.SaveMucChietKhauTT())){	
					ghcnBean.setListMucChietKhau("");
					session.setAttribute("obj", ghcnBean);
					 nextJSP = "/AHF/pages/Center/MucChietKhauTT.jsp";
				}
				}

		}else if(action.equals("update"))
		{
			if(error ==false){
					if ((ghcnBean.EditMucChietKhauTT())){	
					ghcnBean.setListMucChietKhau("");
					session.setAttribute("obj", ghcnBean);
					 nextJSP = "/AHF/pages/Center/MucChietKhauTT.jsp";
					}
			}
				
		}else if(action.equals("loc")){
			String sql="";
			if(khuvucid.equals("")){
				sql="select a.pk_seq,a.ten,diachi,b.ten as khuvuc from nhaphanphoi a inner join khuvuc b on b.pk_seq=a.khuvuc_fk  ";
	
			}else{
			 sql="select a.pk_seq,a.ten,diachi,b.ten as khuvuc from nhaphanphoi a inner join khuvuc b on b.pk_seq=a.khuvuc_fk  where khuvuc_fk="+khuvucid;
			}
			 ResultSet rs=db.get(sql);
	    	if(rs!=null){
	    		try{
	    			while(rs.next()){
	    				if(!hatable.containsKey(rs.getString("pk_seq"))){
	    					IMucChietKhauTT_NhaPP npp=new MucChietKhauTT_NhaPP();
	    					npp.setDiaChi(rs.getString("diachi"));
	    					npp.setIDNhaPP(rs.getString("pk_seq"));
	    					npp.setTenNhaPP(rs.getString("ten"));
	    					npp.setKhuVuc(rs.getString("khuvuc"));
	    					npp.setID("0");//Mac dinh set =0 de check =false;
	    					list.add(npp);
	    				}
	    			}
	    		}catch(Exception er){
	    			System.out.println("Error MucChietKhauTTSvl : 167"+ er.toString());
	    		}
	    	}
	    	ghcnBean.setListNhaPhanPhoi(list);
			}
			session.setAttribute("ghcnBean", ghcnBean);			
			response.sendRedirect(nextJSP);
	}

}
