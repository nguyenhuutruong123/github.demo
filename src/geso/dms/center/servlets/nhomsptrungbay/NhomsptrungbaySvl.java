package geso.dms.center.servlets.nhomsptrungbay;

import geso.dms.center.beans.nhomsptrungbay.*;
import geso.dms.center.beans.nhomsptrungbay.imp.*;
import geso.dms.center.util.Utility;
import geso.dms.distributor.db.sql.dbutils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.rmi.CORBA.Util;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class NhomsptrungbaySvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
	
    public NhomsptrungbaySvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		INhomsptrungbayList obj;
		
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
	    
	    String nsptbId = util.getId(querystring);

/*	    if (action.equals("delete")){	
	    	Delete(nsptbId);
	    	out.print(nsptbId);
	    }*/
	    
		String param = "";
//		if(view.trim().length() > 0) param ="&view="+view;
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "NhomsptrungbaySvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}
	
	    obj = new NhomsptrungbayList();
	    obj.setRequestObj(request);
	    obj.setUserId(userId);
	    
	    obj.init("");
		session.setAttribute("obj", obj);
				
		String nextJSP = "/AHF/pages/Center/NhomSpTrungBay.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		INhomsptrungbayList obj = new NhomsptrungbayList();
		
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
	    obj.setRequestObj(request);    
	    
		
	
		
		String param = "";
//		if(view.trim().length() > 0) param ="&view="+"";
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			obj.DBclose();
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "NhomsptrungbaySvl", param, Utility.XEM ))
		{
			obj.DBclose();
			Utility.RedireactLogin(session, request, response);
		}
	    
	    if (action.equals("Tao moi"))
	    {
	    	INhomsptrungbay nsptbBean = (INhomsptrungbay) new Nhomsptrungbay("");
	    	nsptbBean.setUserId(userId);
	    	nsptbBean.createRS();
	    	session.setAttribute("nsptbBean", nsptbBean);
    		
    		String nextJSP = "/AHF/pages/Center/NhomSpTrungBayNew.jsp";
    		response.sendRedirect(nextJSP);
    		return;
	    }
	    
	    
		if(action.equals("Xoa"))
		{
			String IdXoa = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("IdXoa")));
			if (IdXoa == null)
				IdXoa = "";
			System.out.println("IdXoa = "+ IdXoa);
			if(IdXoa.trim().length() > 0)
				Delete(IdXoa, obj);
			else
				obj.setMgs("Lỗi dữ liệu");
			obj.setRequestObj(request);
		    obj.setUserId(userId);
		    obj.init("");
			session.setAttribute("obj", obj);
					
			String nextJSP = "/AHF/pages/Center/NhomSpTrungBay.jsp";
			response.sendRedirect(nextJSP);
		}
	    else
	    	if(action.equals("view") || action.equals("next") || action.equals("prev")){
		    	
	    		String search = getSearchQuery(request,userId ,obj);
		    	obj.setNxtApprSplitting(Integer.parseInt(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting")))));
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	response.sendRedirect("/AHF/pages/Center/NhomSpTrungBay.jsp");
	    	}
	    else{	    
	    	//a
	    	
	    	
	    	//obj = new NhomsptrungbayList();
	    	//obj.setRequestObj(request); 	    	
	    	
	    	//------------------------
	    	
	    	String search = getSearchQuery(request,userId , obj);

	    	
	    	//obj = new DaidienkinhdoanhList(search);
	    	obj.init(search);
			obj.setUserId(userId);
			
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    	
    		response.sendRedirect("/AHF/pages/Center/NhomSpTrungBay.jsp");    	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request,String userId , INhomsptrungbayList obj)
	{	
		
		//INhomsptrungbayList obj = new NhomsptrungbayList();
		
		Utility util = new Utility();
		obj.getDataSearch().clear();
		
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
    	Utility Ult = new Utility();
    	String query = "select ROW_NUMBER() OVER(ORDER BY a.pk_seq DESC) AS stt, a.PK_SEQ as nsptbId, a.DIENGIAI, ISNULL(a.TONGLUONG, 0) as tongluong, ISNULL(a.TONGTIEN, 0) as tongtien, a.LOAI, convert(char(10), a.NGAYTAO, 103) as ngaytao, convert(char(10), a.NGAYSUA, 103) as ngaysua, b.TEN as nguoitao, c.TEN as nguoisua ";
		query = query + " from NhomSpTrungBay a inner join NHANVIEN b on a.NGUOITAO = b.PK_SEQ inner join NHANVIEN c on a.NGUOISUA = c.PK_SEQ where a.pk_seq > 0";
		query = query +" and a.pk_seq in (select nhomsptrungbay_fk from Nhomsptrungbay_sanpham where sanpham_fk in"+ Ult.quyen_sanpham(userId) +")";
		//geso.dms.distributor.util.Utility util = new geso.dms.distributor.util.Utility();
	
    	if (diengiai.length()>0){
			query = query + " and upper(dbo.ftBoDau(a.diengiai)) like upper(?)";		
			//or upper(a.pk_seq) like upper('%" + util.replaceAEIOU(diengiai) + "%')";
			obj.getDataSearch().add( "%" + util.replaceAEIOU(diengiai) + "%" );
    	}
    	
    	if (tungay.length()>0){
			query = query + " and a.ngaytao >= ?";			
			obj.getDataSearch().add(tungay);
    	}
    	
    	if (denngay.length()>0){
			query = query + " and a.ngaytao <= ?";	
			obj.getDataSearch().add(denngay);
    	}
    	
    	//query = query + " order by a.NGAYTAO DESC, a.pk_seq DESC";
    	
    	dbutils.viewQuery(query, obj.getDataSearch());
    	System.out.print("query search : "+ query);

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
	
	private void Delete(String nsptbId,INhomsptrungbayList obj) 
	{
	
		
		dbutils db = new dbutils(); 
    		
		try{
			db.getConnection().setAutoCommit(false);
			
			String sqlcheck = "select count(nhomsptrungbay_fk) as dem from CTTB_NHOMSPTRUNGBAY where nhomsptrungbay_fk = "+nsptbId+" ";
			ResultSet rscheck = db.get(sqlcheck);
			int dem =0;
			if(rscheck.next()){
				dem = rscheck.getInt("dem");
			}
			if(dem >0){
				obj.setMgs("Điều kiện trưng bày đã phát sinh dữ liệu, không thể xóa, vui lòng kiểm tra lại!");
				db.getConnection().rollback();
				return;
			}
		//Xoa Cac Bang Con Truoc 
		String str1 = "delete from nhomsptrungbay_sanpham where nhomsptrungbay_fk='" + nsptbId + "'";
		if(!db.update(str1))
		{
			db.getConnection().rollback();
			return;
		}
		
		/*String str2 = "delete from cttb_nhomsptb where nhomsptrungbay_fk='" + nsptbId + "'";
		if(!db.update(str2))
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return;
		}*/
	
		//Xoa Bang Cha
		String str3 = "delete from nhomsptrungbay where pk_seq = '" + nsptbId + "'";
		if(!db.update(str3))
		{
			db.getConnection().rollback();
			return;
		}
		//db.update("commit");
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);

		db.shutDown();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			try {
				db.getConnection().rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			obj.setMgs("khong the xoa dieu kien trung bay nay!");
		}
	}

}
