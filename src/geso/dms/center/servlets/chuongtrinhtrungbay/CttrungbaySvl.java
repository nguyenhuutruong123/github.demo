package geso.dms.center.servlets.chuongtrinhtrungbay;

import geso.dms.center.beans.cttrungbay.ICttrungbay;
import geso.dms.center.beans.cttrungbay.ICttrungbayList;
import geso.dms.center.beans.cttrungbay.imp.Cttrungbay;
import geso.dms.center.beans.cttrungbay.imp.CttrungbayList;
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

public class CttrungbaySvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
	
    public CttrungbaySvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		ICttrungbayList obj;
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
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    obj = new CttrungbayList();
	    
	    String cttbId = util.getId(querystring);

	    if (action.equals("delete")){	
	    	String msg = Delete(cttbId);
	    	out.print(cttbId);
	    	if(msg!=null || msg.trim().length()<=0){
	    		obj.setMsg(msg);
	    	}
	    }
	
	    
	    obj.setUserId(userId);
	    obj.init("");
		session.setAttribute("obj", obj);
		//session.setAttribute("dkkmDien_giai", "");
    	//session.setAttribute("dkkmTungay", "");
    	//session.setAttribute("dkkmDenngay", "");
				
		String nextJSP = "/AHF/pages/Center/ChuongTrinhTrungBay.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out = response.getWriter();
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    out.println(action); 
	        
	    
	    if (action.equals("Tao moi"))
	    {
	    	ICttrungbay cttbBean = (ICttrungbay) new Cttrungbay("");
	    	cttbBean.setUserId(userId);
	    	cttbBean.createRS();
	    	
	    	session.setAttribute("cttbBean", cttbBean);
	    	//session.setAttribute("dkkmDien_giai", "");
	    	//session.setAttribute("dkkmTungay", "");
	    	//session.setAttribute("dkkmDenngay", "");
	    	
    		String nextJSP = "/AHF/pages/Center/ChuongTrinhTrungBayNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }
	    else{	    
	    	ICttrungbayList obj = new CttrungbayList();
	    	String search = getSearchQuery(request, obj);
	    	
	    	//obj = new DaidienkinhdoanhList(search);
	    	
	    	obj.init(search);
	    	obj.setUserId(userId);
			
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/AHF/pages/Center/ChuongTrinhTrungBay.jsp");    	
	    }
	}
	private String getSearchQuery(HttpServletRequest request, ICttrungbayList obj)
	{	
		Utility util = new Utility();
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
    	if ( diengiai == null)
    		diengiai = "";
    	obj.setDiengiai(diengiai);
    	
    	String tungay = util.antiSQLInspection(request.getParameter("tungay"));
    	if (tungay == null)
    		tungay = "";    	
    	obj.setTungay(tungay);
    	
    	String denngay = util.antiSQLInspection(request.getParameter("denngay"));
    	if (denngay == null)
    		denngay = "";    	
    	obj.setDenngay(denngay);
    	
    	String query = "select a.PK_SEQ as cttbId, a.SCHEME, a.ngaytds, a.ngaykttds, a.ngaytrungbay as ngaytb, a.ngayketthuctb as ngaykttb, isnull(a.DIENGIAI, '') as diengiai, a.TYPE, a.solanthanhtoan as solantt, a.NGANSACH, a.DASUDUNG, a.NGAYTAO, a.NGAYSUA, b.TEN as nguoitao, c.TEN as nguoisua ";
		query = query + " from CTTRUNGBAY a inner join NHANVIEN b on a.NGUOITAO = b.PK_SEQ inner join NHANVIEN c on a.NGUOISUA = c.PK_SEQ where a.PK_SEQ > 0 ";
		
    	if (diengiai.length()>0){
			query = query + " and upper(a.diengiai) like upper('%" + diengiai + "%') or upper(a.SCHEME) like upper('%" + diengiai + "%')";			
    	}
    	
    	if (tungay.length()>0){
			query = query + " and a.ngaytrungbay >= '" + convertDate(tungay) + "'";			
    	}
    	
    	if (denngay.length()>0){
			query = query + " and a.ngayketthuctb <= '" + convertDate(denngay) + "'";		
    	}
    	
    	query = query + " order by a.NGAYTAO DESC, a.pk_seq DESC";
    	
    	System.out.println("QUERY tim kiem: " + query);
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
	
	private String Delete(String cttbId) 
	{
		dbutils db = new dbutils();
		try
		{
			db.getConnection().setAutoCommit(false);
			String msg="";
			String query="select CTTRUNGBAY_FK from DANGKYTRUNGBAY where CTTRUNGBAY_FK=" + cttbId;
			ResultSet check = db.get(query);
			if(check.next()){
				db.getConnection().rollback();
				db.shutDown();
				msg="Không xóa được do phát sinh dữ liệu, vui lòng kiểm tra lại!";
				return msg;
			}
			
	    	int kq = 0;
			//Xoa Cac Bang Con Truoc 
	    	db.update("delete from CTTB_DKTRUNGBAY where cttrungbay_fk='" + cttbId + "'");
	    	db.update("delete ctkhuyenmai where CTTB_FK ="+ cttbId);
	    	db.update("delete from cttb_nhomsptrungbay where cttrungbay_fk='" + cttbId + "'");
	    	db.update("delete from cttb_tratb where cttrungbay_fk='" + cttbId + "'");
	    	db.update("delete from CTTRUNGBAY_BOSANPHAM where cttrungbay_fk='" + cttbId + "'");
	    	
			db.update("delete from DENGHITRATRUNGBAY where cttrungbay_fk='" + cttbId + "'");
			db.update("delete from PHANBOTRUNGBAY where cttb_fk='" + cttbId + "'");
			//Xoa Bang Cha
			kq = db.updateReturnInt("delete from cttrungbay where pk_seq = '" + cttbId + "'");
			if(kq <=0 )
			{
				db.getConnection().rollback();
				db.shutDown();
		    	return "Không thể xóa!";

			}
			db.getConnection().commit();
			db.shutDown();
	    	return "Xóa thành công";
		}
		catch (Exception e) {
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			db.shutDown();
			return "Exception:"+ e.getMessage();
		}
		
		
		
	}

}
