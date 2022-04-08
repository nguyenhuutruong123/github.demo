/*
 *  design :khoana 
 *  date 2011-10-24
 *  description :
 */
package geso.dms.center.servlets.chitieuttchovung;

import geso.dms.center.beans.chitieu.imp.ChiTieu;
import geso.dms.center.beans.chitieuttchovung.imp.ChiTieuTTChoVung;
import geso.dms.center.beans.chitieuttchovung.imp.ChiTieuTTKhuVuc;
import geso.dms.center.util.Utility;
import geso.dms.distributor.db.sql.dbutils;

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


public class chitieuttchovungSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	 
	   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public chitieuttchovungSvl() {
        super();
        // TODO Auto-generated constructor stub
    }
    private String gettenuser(String userId_){
		dbutils db=new dbutils();
		String sql_getnam="select ten from nhanvien where pk_seq="+ userId_;
		ResultSet rs_tenuser=db.get(sql_getnam);
		if(rs_tenuser!= null){
			try{
			  while(rs_tenuser.next()){
				  return rs_tenuser.getString("ten");
			  }
			}catch(Exception er){
				return "";
			}
		}
		return "";
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Utility util = new Utility();
		dbutils db=new dbutils();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring); 
		//System.out.println("Ten user:  "+ userId);
		if (userId.length()==0)
		userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		session.setAttribute("userId", userId);  
		session.setAttribute("userTen", gettenuser(userId));
		
		String action = util.getAction(querystring);
		//out.println(action);   
		String idlist = util.getId(querystring); 
		String nextJSP = "/AHF/pages/Center/ChiTieuTTChoVung.jsp";//default
		
		session.setAttribute("nam",0);
		session.setAttribute("thang",0);
		session.setAttribute("tumuc","");
		session.setAttribute("toimuc","");
		 String loaict="";
		 loaict=(String)session.getAttribute("loaichitieu");
		 if(loaict==null){
			 loaict="1";
	 	 try
		 {
		 //lay gia tri loai ct 
		  String tmp;
		
			if (querystring != null){
		    	if (querystring.contains("&")){
		    		tmp = querystring.split("&")[2];
		    	}else{
		    		tmp = querystring;
		    	}
		    	loaict = tmp.split("=")[1];
			}
		}catch(Exception er){
			
		}
		 }
		String sql_getdvkd="select pk_seq,donvikinhdoanh from donvikinhdoanh where trangthai=1";
		ResultSet rs_dvkd= db.get(sql_getdvkd);
		session.setAttribute("rs_dvkd",rs_dvkd);
		session.setAttribute("loaichitieu", loaict);
		System.out.println(action);
		if(action.equals("delete")){
			ChiTieuTTChoVung obj = new ChiTieuTTChoVung();
			obj.setUserId(userId);
			obj.setID(Double.parseDouble(idlist));
			if(loaict.equals("0")){
			obj.DeleteChitieu();
			}else{
				obj.DeleteChitieu_Sec();
			}
			 obj.setListChiTieu("",loaict);
			    session.setAttribute("obj", obj);
		}else if(action.equals("chot")){
			ChiTieuTTChoVung obj = new ChiTieuTTChoVung();
			obj.setUserId(userId);
			obj.setID(Double.parseDouble(idlist));
			if(loaict.equals("0")){
			obj.chotChitieu();
			}else{
				obj.chotChitieu_Sec();
			}
			 obj.setListChiTieu("",loaict);
			    session.setAttribute("obj", obj);
		}
		else{
			ChiTieuTTChoVung obj = new ChiTieuTTChoVung();
			obj.setUserId(userId);
			obj.setListChiTieu("",loaict);//khoi tao mac dinh khi moi load thi load loai secondary
		    session.setAttribute("obj", obj);
			
		}
		
		response.sendRedirect(nextJSP);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
			dbutils db=new dbutils();
		 	request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    Utility util = new Utility();
		    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		    String username=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userTen")));
		    
		    String thangtk=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thang")));
		    String namtk=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nam")));
		    String tumuc=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tumuc")));
		    String toimuc=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("toimuc")));
		    String action =geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		    String loaichitieu=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaichitieu")));
		    String dvkdid=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("selectdvkd")));
		    ChiTieuTTChoVung chitieu=new ChiTieuTTChoVung();
		    HttpSession session=request.getSession();
		    //truyen qua mot resultset danh sach cac donvikinhdoanh
		    String sql_getdvkd="select pk_seq,donvikinhdoanh from donvikinhdoanh where trangthai=1";
			ResultSet rs_dvkd= db.get(sql_getdvkd);
			session.setAttribute("rs_dvkd",rs_dvkd);
			session.setAttribute("dvkdid", dvkdid);
		    session.setAttribute("loaichitieu",loaichitieu);
		    session.setAttribute("nam",Integer.parseInt(namtk));
		    session.setAttribute("thang",Integer.parseInt(thangtk));
		    session.setAttribute("tumuc",tumuc);
		    session.setAttribute("toimuc",toimuc);
		    if(action.equals("search"))
		    {   
		    	String sql_getdata="";
		    	if(loaichitieu.equals("0")){
		    		  sql_getdata= " SELECT     c.kenh_fk,c.trangthai,C.PK_SEQ, C.THANG, C.NAM, C.CHITIEU, C.DIENGIAI,C.DVKD_FK,D.DONVIKINHDOANH, C.NGAYKETTHUC, C.NGAYTAO ,C.SONGAYLAMVIEC, C.NGAYSUA, NT.TEN AS NGUOITAO, "+
                      " NS.TEN AS NGUOISUA    FROM  dbo.CHITIEU_TRUNGTAM AS C INNER JOIN "+
                      " dbo.NHANVIEN AS NT ON C.NGUOITAO = NT.PK_SEQ INNER JOIN  "+
                      " dbo.NHANVIEN AS NS ON C.NGUOISUA = NS.PK_SEQ inner join DONVIKINHDOANH D on D.PK_SEQ=C.DVKD_FK  where 1=1";
		    	}else{
		    		//Loai chi tieu CHITIEU_TRUNGTAM_SEC
		    		 sql_getdata= " SELECT      c.kenh_fk,c.trangthai,C.PK_SEQ, C.THANG, C.NAM, C.CHITIEU, C.DIENGIAI,C.DVKD_FK,D.DONVIKINHDOANH, C.NGAYKETTHUC,C.SONGAYLAMVIEC, C.NGAYTAO, C.NGAYSUA, NT.TEN AS NGUOITAO, "+
		             " NS.TEN AS NGUOISUA    FROM  dbo.CHITIEU_TRUNGTAM_SEC AS C INNER JOIN "+
		             " dbo.NHANVIEN AS NT ON C.NGUOITAO = NT.PK_SEQ INNER JOIN  "+
		             " dbo.NHANVIEN AS NS ON C.NGUOISUA = NS.PK_SEQ inner join DONVIKINHDOANH D on D.PK_SEQ=C.DVKD_FK  where 1=1";
		    	}
		    	String where ="";
		    	if(!thangtk.equals("0")){
		    		where=" and C.THANG="+ thangtk + " ";	
		    	}
		    	if(!namtk.equals("0")){
		    		where=where + "and C.NAM="+namtk +" ";
		    	}
		    	if(!tumuc.equals("")){
		    		where=where + "and C.CHITIEU >=" + tumuc +" " ;//in circumstance equal bigger 
		    	}
		    	if(!toimuc.equals("")){
		    		where=where + "and C.CHITIEU <=" + toimuc;
		    	}
		    	if(!dvkdid.equals("")){
		    		where=where+" and C.DVKD_FK="+ dvkdid;
		    	}
		    	if(where !=""){//if have search condition
		    		sql_getdata=sql_getdata+ where;	
		    	}
		    	
		    	
		    	//System.out.println("CAU LENH TIM KIEM :" + sql_getdata);
		    	chitieu.setUserId(userId);
		    	chitieu.setListChiTieu(sql_getdata,loaichitieu);
		    	
		    	session.setAttribute("obj", chitieu);
		    	String nextJSP = "/AHF/pages/Center/ChiTieuTTChoVung.jsp";//default
		    	response.sendRedirect(nextJSP);
		    }
		    else if(action.equals("new"))
		    {
		    	
		       // List<ChiTieuTTKhuVuc> listkhuvuc=new ArrayList<ChiTieuTTKhuVuc>();
		    	//Khai bao lop ket noi
				db=new dbutils();
				String sql_getkhuvuc="select pk_seq, ten from khuvuc where trangthai=1 order by pk_Seq";
				ResultSet rskhuvuc=	db.get(sql_getkhuvuc);
				List<ChiTieuTTKhuVuc> listkhuvuc=new ArrayList<ChiTieuTTKhuVuc>();
				if(rskhuvuc!=null){
					try{
					while(rskhuvuc.next()){
						ChiTieuTTKhuVuc kv=new ChiTieuTTKhuVuc();
						kv.setKhuVucId(rskhuvuc.getString("pk_seq"));
						kv.setTenKhuVucId(rskhuvuc.getString("ten"));
						kv.setChiTieu(0);
						listkhuvuc.add(kv);
					}
					}catch(Exception ex){
						
					}
				}
				chitieu.setListKhuVuc(listkhuvuc);
		        session.setAttribute("userId",userId);
		        session.setAttribute("userTen", username);
		    	session.setAttribute("obj", chitieu);
		    	session.setAttribute("check", "0");
		    	String	nextJSP= "/AHF/pages/Center/ChiTieuTTChoVungNew.jsp";
		    	response.sendRedirect(nextJSP);
		    	
		    }
	}

}
