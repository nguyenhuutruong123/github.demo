package geso.dms.center.servlets.duyetdhkm;
import geso.dms.center.beans.duyetdhkm.IDuyetDhKm;
import geso.dms.center.beans.duyetdhkm.IDuyetDhKmList;
import geso.dms.center.beans.duyetdhkm.imp.*;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class DuyetdhkmSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
	
    public DuyetdhkmSvl()
    {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    IDuyetDhKmList obj;    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    out = response.getWriter();
	    obj = new DuyetDhKmList();    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = util.getAction(querystring);
 
	    String vmId = util.getId(querystring);

	    if (action.equals("delete")){	   		  	    	
	    	Delete(vmId, obj);
	    	out.print(vmId);
	    }
	    if (action.equals("unchot")){	   		  	    	
	    	Unchot(vmId, obj);
	    	out.print(vmId);
	    }
 
	    obj.setUserId(userId);
	    obj.init("");
		session.setAttribute("obj", obj);
				
		String nextJSP = "/AHF/pages/Center/DuyetdhKm.jsp";
		response.sendRedirect(nextJSP);
	}

	private void Unchot(String vmId, IDuyetDhKmList obj) {
		// TODO Auto-generated method stub
		dbutils db=new dbutils();
		String sql="update DUYETDONHANGKM_CTKM set tinhtrang=0 where DUYETDONHANGKM_FK ="+vmId;
		db.update(sql);
		 sql="update DUYETDONHANGKM set trangthai=2 where PK_SEQ ="+vmId;
		 db.update(sql);
		db.shutDown();
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IDuyetDhKmList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out = response.getWriter();
	    Utility util = new Utility();
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = "";
	    }
	    out.println(action); 
	        
	    
	    if (action.equals("new")){
	    	 
	    	IDuyetDhKm vmBean =   new DuyetDhKm ();
	    	vmBean.setUserId(userId);
	    	vmBean.init();
	    	session.setAttribute("dhkm", vmBean);
    		
    		String nextJSP = "/AHF/pages/Center/DuyetdhKmNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }
	   if (action.equals("search"))	    
	    {obj = new DuyetDhKmList();
    	
	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.init(search);
			obj.setUserId(userId);
			
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/AHF/pages/Center/DuyetdhKm.jsp");	    	
	    	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IDuyetDhKmList obj){
		   // PrintWriter out = response.getWriter();
			Utility util = new Utility();
			String vungmien = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("VungMien")));
	    	if ( vungmien == null)
	    		vungmien = "";
	    	obj.setVungmien(vungmien);
	    	
	    	String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("DienGiai")));
	    	if (diengiai == null)
	    		diengiai = "";    	
	    	obj.setDiengiai(diengiai);
	    	
	    	String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));   	
	    	if (trangthai == null)
	    		trangthai = "";    	
	    	
	    	obj.setTrangthai(trangthai);
	    	

	    	String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));   	
	    	if (nppId == null)
	    		nppId = "";    	
	    	obj.setNppId(nppId);
	    	
	    	
	    	String ctkmId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ctkmId")));   	
	    	if (ctkmId == null)
	    		ctkmId = "";    	
	    	obj.setCtkmId(ctkmId);
	    	
	    	
	    	String thang = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thang")));   	
	    	if (thang == null)
	    		thang = "";    	
	    	obj.setThang(thang);
	    	
	    	String nam = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nam")));   	
	    	if (nam == null)
	    		nam = "";    	
	    	obj.setNam(nam);
	    	
	    	
	   
	    	String query;
	    	query = "  SELECT   D.PK_SEQ ,D.DIENGIAI, D.NGAYTAO,D.NGAYSUA,D.TRANGTHAI ,d.Thang,d.Nam,NT.TEN AS NGUOITAO,NS.TEN AS NGUOISUA  "+
			" FROM DUYETDONHANGKM   D INNER JOIN NHANVIEN NT ON NT.PK_SEQ=D.NGUOITAO  "+
			" INNER JOIN NHANVIEN AS NS ON NS.PK_SEQ=D.NGUOISUA";
	    	 
	    	 
	  
	    	if(trangthai.length() > 0){
	    		query = query + " and d.trangthai = '" + trangthai + "'";
	    		
	    	}
	    	
	    	if(ctkmId.length()>0)
	    	{
	    		query+=" and d.pk_Seq in (select DUYETDONHANGKM_FK from DUYETDONHANGKM_ctkm where ctkm_fk ="+ctkmId+") ";
	    	}
	    	
	    	
	    	if(nppId.length()>0)
	    	{
	    		query+=" and d.pk_Seq in (select DUYETDONHANGKM_FK from DuyetDonHangKm_ChiTiet where npp_fk ="+nppId+") ";
	    	}
	    	
	    	if(thang.length()>0)
	    	{
	    		query+=" and d.thang='"+thang+"' ";
	    	}
	    	
	    	if(nam.length()>0)
	    	{
	    		query+=" and d.nam='"+nam+"' ";
	    	}
	    	
	    	
	    	
 
	    	System.out.println("sql:"+ query);
	    	return query;
		}	
	
	private void Delete(String id, IDuyetDhKmList obj)
	{
		 
	}

}

