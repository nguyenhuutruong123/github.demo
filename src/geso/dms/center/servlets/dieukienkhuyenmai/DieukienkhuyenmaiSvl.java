package geso.dms.center.servlets.dieukienkhuyenmai;

import geso.dms.center.beans.dieukienkhuyenmai.IDieukienkhuyenmai;
import geso.dms.center.beans.dieukienkhuyenmai.IDkkhuyenmaiList;
import geso.dms.center.beans.dieukienkhuyenmai.imp.Dieukienkhuyenmai;
import geso.dms.center.beans.dieukienkhuyenmai.imp.DkkhuyenmaiList;
import geso.dms.center.util.Utility;
import geso.dms.distributor.db.sql.dbutils;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DieukienkhuyenmaiSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;  
	
    public DieukienkhuyenmaiSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IDkkhuyenmaiList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String dkkmId = util.getId(querystring);
	    
	  //--- check user
	    String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
		if (view == null) {
			view = "";
		}
		 obj = new DkkhuyenmaiList();
		String param = "";
		if(view.trim().length() > 0) param ="&view="+view;
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "DieukienkhuyenmaiSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}
		 //--- check user
	
	   

	    obj.setRequestObj(request);
	    if (action.equals("delete"))
	    {	
	    	String msg = Delete(dkkmId);
	    	if(msg.length() > 0)
	    		obj.setmsg(msg);
	    }
	    
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/AHF/pages/Center/DieuKienKhuyenMai.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IDkkhuyenmaiList obj  = new DkkhuyenmaiList();
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out = response.getWriter();
	    
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	   //--- check user
	    String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
		if (view == null) {
			view = "";
		}
		String param = "";
		if(view.trim().length() > 0) param ="&view="+view;
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "DieukienkhuyenmaiSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}
		 //--- check user
	    
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = "";
	    }
	    out.println(action); 
	        
	   // obj.settingPage(this.getServletContext());
	    obj.setRequestObj(request);
	    if (action.equals("Tao moi"))
	    {
	    	IDieukienkhuyenmai dkkmBean = (IDieukienkhuyenmai) new Dieukienkhuyenmai("");
	    	dkkmBean.setUserId(userId);
	    	dkkmBean.createRS();
	    	session.setAttribute("dkkmBean", dkkmBean);
    		
    		String nextJSP = "/AHF/pages/Center/DieuKienKhuyenMaiNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }
	    
	    else
	    	if(action.equals("view") || action.equals("next") || action.equals("prev")){
		    	
		    	String search = getSearchQuery(request, obj);
		    	
		    	obj.setNxtApprSplitting(Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
		    	obj.setUserId(userId);

		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/AHF/pages/Center/DieuKienKhuyenMai.jsp");
		    }
	    	
	    	/*  if(action.equals("view") || action.equals("next") || action.equals("prev")){
	    	String search = getSearchQuery(request);
	    	obj.setNxtApprSplitting(Integer.parseInt(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting")))));
	    	obj.init(search);
	    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    	response.sendRedirect("/AHF/pages/Center/DieuKienKhuyenMai.jsp");
	    	}*/
	    
	    else
	    
	    {	    
	    	//a
	    	
	    	
	    	String search = getSearchQuery(request, obj);

	    	//obj = new DaidienkinhdoanhList(search);
	    	obj.init(search);
			obj.setUserId(userId);
			
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
    		
    		session.setAttribute("abc", search);
	
    		response.sendRedirect("/AHF/pages/Center/DieuKienKhuyenMai.jsp");    
	    	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IDkkhuyenmaiList obj)
	{	
	//	IDkkhuyenmaiList obj  = new DkkhuyenmaiList();
		
		obj.getDataSearch().clear();
		
		Utility util = new Utility();
		
		String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
    	if ( diengiai == null)
    		diengiai = "";
    	obj.setDiengiai(diengiai);
    	
    	String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
    	if (tungay == null)
    		tungay = "";    	
    	obj.setTungay(tungay);
    	
    	String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
    	if (denngay == null)
    		denngay = "";    	
    	obj.setDenngay(denngay);
    	
    	String query = "select ROW_NUMBER() OVER(ORDER BY a.pk_seq DESC) AS stt, a.PK_SEQ as dkkmId, a.DIENGIAI, ISNULL(a.TONGLUONG, 0) as tongluong, ISNULL(a.TONGTIEN, 0) as tongtien, a.LOAI, convert(char(10), a.NGAYTAO, 103) as ngaytao, convert(char(10), a.NGAYSUA, 103) as ngaysua, b.TEN as nguoitao, c.TEN as nguoisua ";
		query = query + " from DIEUKIENKHUYENMAI a inner join NHANVIEN b on a.NGUOITAO = b.PK_SEQ inner join NHANVIEN c on a.NGUOISUA = c.PK_SEQ where a.pk_seq > 0";
		//geso.dms.distributor.util.Utility util = new geso.dms.distributor.util.Utility();

    	if (diengiai.length()>0){
//			query = query + " and upper(dbo.ftBoDau(a.diengiai)) like upper(N'%" + util.replaceAEIOU(diengiai) + "%')";	
			
			query = query + "\n and upper(dbo.ftBoDau(a.diengiai)) like upper(?) ";	
			obj.getDataSearch().add( "%" + util.replaceAEIOU(diengiai) + "%" );
    	}
    	
    	if (tungay.length()>0){
//			query = query + " and a.ngaytao >= '" + convertDate(tungay) + " '";	
			
			query = query + "\n and a.ngaytao >= ? ";	
			obj.getDataSearch().add( "" + convertDate(tungay) + "" );
    	}
    	
    	if (denngay.length()>0){
//			query = query + " and a.ngaytao <= '" + convertDate(denngay) + " '";	
			
			query = query + "\n and a.ngaytao <= ? ";	
			obj.getDataSearch().add( "" + convertDate(denngay) + "" );
    	}
    	
    	//query = query + " order by a.NGAYTAO DESC, a.pk_seq DESC";

    	return query;
	}
	
	private String convertDate(String date) 
	{
		//chuyen dinh dang dd-MM-yyyy sang dinh dang yyyy-MM-dd
		if(!date.contains("-"))
			return "";
		String[] arr = date.split("-");
		if(arr[0].length() < arr[2].length())
			return arr[2] + "-" + arr[1] + "-" + arr[0];
		return date;
	}
	
	private String Delete(String dkkmId) 
	{
		dbutils db = new dbutils();
    		
		String sql ="select count(*) as num from ctkm_dkkm where dkkhuyenmai_fk ='"+ dkkmId +"'";
		ResultSet rs = db.get(sql);
		
		try 
		{
			rs.next();
			if(!rs.getString("num").equals("0"))
			{
				rs.close();
				return "Dieu kien khuyen mai nay da duoc su dung trong khai bao ct khuyen mai";
			}
			rs.close();
			
			db.update("delete from dieukienkm_sanpham where dieukienkhuyenmai_fk='" + dkkmId + "'");
			db.update("delete from ctkm_dkkm where dkkhuyenmai_fk='" + dkkmId + "'");
			//Xoa Bang Cha
			db.update("delete from dieukienkhuyenmai where pk_seq = '" + dkkmId + "'");
			db.update("commit");
			return "";
			
		} 
		catch(Exception e) 
		{
			return "Khong the xoa tra khuyen mai nay";
		}

	}
}
