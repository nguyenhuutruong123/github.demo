package geso.dms.center.servlets.giamsatbanhang;

import geso.dms.center.beans.giamsatbanhang.*;
import geso.dms.center.beans.giamsatbanhang.imp.*;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.ResultSet;

 public class GiamsatbanhangSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet 
 {
	static final long serialVersionUID = 1L;
   //	IGiamsatbanhangList obj = new GiamsatbanhangList();
   	
	public GiamsatbanhangSvl() 
	{
		super();
	}   	
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession();	    
		IGiamsatbanhangList obj = new GiamsatbanhangList();
		Utility util = new Utility();
		out = response.getWriter();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		out.println(userId);
		
		String param = "";
//		if(view.trim().length() > 0) param ="&view="+view;
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "GiamsatbanhangSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}


		if (userId.length()==0)
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

		String action = util.getAction(querystring);
		out.println(action);

		String bgId = util.getId(querystring);

		if (action.equals("delete")){	   		  	    	
			Delete(bgId,obj);
			//String search = getSearchQuery(request);
			out.print(bgId);
		}
		obj.setUserId(userId);
		obj.setRequestObj(request);
		obj.init("");

		session.setAttribute("obj", obj);

		String nextJSP = "/AHF/pages/Center/GiamSatBanHang.jsp";
		response.sendRedirect(nextJSP);

	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    IGiamsatbanhangList obj = new GiamsatbanhangList();
		HttpSession session = request.getSession();
		
		Utility util = new Utility();
		
	    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = "";
	    }
	    out.println(action); 
	     
		String param = "";
//		if(view.trim().length() > 0) param ="&view="+view;
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "GiamsatbanhangSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}

	    String nextJSP = "";
	    obj.setUserId(userId);
	    
	    if (action.equals("new")){
	    	// Empty Bean for distributor
	    	IGiamsatbanhang gsbhBean = (IGiamsatbanhang) new Giamsatbanhang("");
	    	gsbhBean.setUserId(userId);
	    	// Save Data into session

	    	session.setAttribute("gsbhBean", gsbhBean);
    		
    		nextJSP = "/AHF/pages/Center/GiamSatBanHangNew.jsp";
    		
    		
	    }
	    else if(action.equals("view") || action.equals("next") || action.equals("prev")){
	    	
	    	//a
	    	
	    	obj.setNxtApprSplitting(Integer.parseInt(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting")))));
 

	    	//------------------------
	    	
	    	String search = getSearchQuery(request, obj);
	    	obj.init(search);
	    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    

	    
	    nextJSP = "/AHF/pages/Center/GiamSatBanHang.jsp";
	    }
	    
	    if (action.equals("search")){

	    	String search = getSearchQuery(request,obj);
	    	search = getSearchQuery(request,obj);
	    	//obj = new GiamsatbanhangList(search);	
	    	obj.init(search);
	    	nextJSP = "/AHF/pages/Center/GiamSatBanHang.jsp";
  	
	    }
	    
	    session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
    		
		response.sendRedirect(nextJSP);	
	    
	}   


	private String getSearchQuery(HttpServletRequest request,IGiamsatbanhangList obj){
	   // PrintWriter out = response.getWriter();
		
		Utility util = new Utility();
		obj.getDataSearch().clear();
		String Magsbh = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("MaGSBH")));
    	if ( Magsbh== null)
    		Magsbh = "";
    	obj.setSmartId(Magsbh);
    	
		String Tengsbh = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("TenGSBH")));
    	if ( Tengsbh== null)
    		Tengsbh = "";
    	obj.setTen(Tengsbh);
    	String asmId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("asmId")));
    	if ( asmId== null)
    		asmId = "";
    	obj.setAsmId(asmId); 
    	String SoDienThoai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("SoDienThoai")));
    	if (SoDienThoai == null)
    		SoDienThoai = "";    	
    	obj.setSodienthoai(SoDienThoai);
    	
    	String kbhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("KenhBanHang")));
    	if (kbhId == null)
    		kbhId = "";    	
    	obj.setKbhId(kbhId);
    	   	
    	String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("TrangThai")));   	
    	if (trangthai == null)
    		trangthai = ""; 
    	obj.setTrangthai(trangthai);
    	String loaigiamsat = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaigiamsat")));   	
    	if (loaigiamsat == null)
    		loaigiamsat = "";  
    	if (loaigiamsat.equals("2"))
    		trangthai = "";
    	
    	obj.setThuviec(loaigiamsat);
    	

    	
    	String query = "\n select  isnull(a.tinhtrang,'0') as tinhtrang,a.pk_seq as id, a.ten as ten, a.smartid, a.diachi as diachi, a.dienthoai as sodienthoai, a.email as email, b.tenviettat as nccTen, b.pk_seq as nccId, '' as kbhTen, a.trangthai as trangthai, d.ten as nguoitao, a.ngaytao as ngaytao, e.ten as nguoisua, a.ngaysua as ngaysua, '' as kbhId, a.hinhanh from giamsatbanhang a, nhacungcap b, nhanvien d, nhanvien e, nhapp_giamsatbh f where f.gsbh_fk = a.pk_seq and a.ncc_fk=b.pk_seq  and a.nguoitao = d.pk_seq and a.nguoisua = e.pk_seq AND a.pk_Seq not in (select gsbh_Fk from  NHAPP_GIAMSATBH  )    ";
    	String query1= "\n union     "
    			     + "\n select  isnull(a.tinhtrang,'0') as tinhtrang,a.pk_seq as id, a.ten as ten, a.smartid, a.diachi as diachi, a.dienthoai as sodienthoai, a.email as email, b.tenviettat as nccTen, b.pk_seq as nccId, '' as kbhTen, a.trangthai as trangthai, d.ten as nguoitao, a.ngaytao as ngaytao, d.ten as nguoisua, a.ngaysua as ngaysua, '' as kbhId, a.hinhanh from giamsatbanhang a, nhacungcap b, nhanvien d, nhanvien e, nhapp_giamsatbh f where f.gsbh_fk = a.pk_seq and a.ncc_fk=b.pk_seq  and a.nguoitao = d.pk_seq and a.nguoisua = e.pk_seq";

    	query = "\n select a.pk_seq,isnull(a.tinhtrang,'0') as tinhtrang,a.HinhAnh,a.pk_seq as id, a.smartid , a.ten as ten, a.diachi as diachi, a.dienthoai as sodienthoai, a.email as email, b.tenviettat as nccTen, b.pk_seq as nccId, c.ten as kbhTen, a.trangthai as trangthai, d.ten as nguoitao, a.ngaytao as ngaytao, e.ten as nguoisua, a.ngaysua as ngaysua, c.pk_seq as kbhId "
			+ "\n from giamsatbanhang a left join  nhacungcap b on a.ncc_fk = b.Pk_seq left join  kenhbanhang c on c.pk_seq = a.Kbh_fk left join nhanvien d on d.pk_seq = a.nguoitao left join  nhanvien e on e.pk_seq = a.nguoisua where 1 = 1 ";
    	query1 = "\n select a.pk_seq,isnull(a.tinhtrang,'0') as tinhtrang,a.HinhAnh,a.pk_seq as id, a.smartid , a.ten as ten, a.diachi as diachi, a.dienthoai as sodienthoai, a.email as email, b.tenviettat as nccTen, b.pk_seq as nccId, c.ten as kbhTen, a.trangthai as trangthai, d.ten as nguoitao, a.ngaytao as ngaytao, e.ten as nguoisua, a.ngaysua as ngaysua, c.pk_seq as kbhId "
			+ "\n from giamsatbanhang a left join  nhacungcap b on a.ncc_fk = b.Pk_seq left join  kenhbanhang c on c.pk_seq = a.Kbh_fk left join nhanvien d on d.pk_seq = a.nguoitao left join  nhanvien e on e.pk_seq = a.nguoisua where 1 = 1 ";

		if(Magsbh.length() > 0)
		{
			query = query + "\n and upper(a.smartid) like upper(?)";
			obj.getDataSearch().add( "%" + Magsbh + "%" );
			//query1 = query1 + " and upper(a.smartid) like upper('%" + Magsbh + "%')";
			
    	}
		if(asmId.length()>0){
			query = query + "\n and a.asm_fk ="+asmId+"";
		}
    	if (Tengsbh.length()>0)
    	{
			query = query + "\n and upper(dbo.ftBoDau(a.ten)) like upper(?)";
			obj.getDataSearch().add( "%" + util.replaceAEIOU(Tengsbh) + "%" );
			//query1 = query1 + " and upper(dbo.ftBoDau(a.ten)) like upper(N'%" + util.replaceAEIOU(Tengsbh) + "%')";
			
    	}
    	
    	if (SoDienThoai.length()>0)
    	{
			query = query + "\n and upper(a.dienthoai) like upper(?)";
			obj.getDataSearch().add( "%" + SoDienThoai + "%" );
			//query1 = query1 + " and upper(a.dienthoai) like upper('%" + SoDienThoai + "%')";
    	}
    	
    	if (kbhId.length()>0)
    	{
    		obj.setKbhId(kbhId);
			query = query + "\n and c.pk_seq = ?";	
			obj.getDataSearch().add( "" + kbhId + "" );
			//query1 = query1 + " and c.pk_seq = '" + kbhId + "'";	
    	}

    	if(trangthai.length() > 0){
    		query = query + "\n and a.trangthai = ?";
    		obj.getDataSearch().add( "" + trangthai + "" );
    		//query1 = query1 + " and a.trangthai = '" + trangthai + "'";
    		
    	}
    	if(loaigiamsat.length() > 0)
    	{
    		query = query + "\n and isnull(a.tinhtrang,'0') = ?";
    		obj.getDataSearch().add( "" + loaigiamsat + "" );
    		//query1 = query1 + " and isnull(a.tinhtrang,'0') = '" + loaigiamsat + "'";
    	}
    	System.out.println("Search: "+query);

    	//query += query1;
    	return query;
	}	
	boolean kiemtra(String sql)
	{
		dbutils db =new dbutils();
		ResultSet rs = db.get(sql);
		try {//kiem tra ben san pham
			while(rs.next())
			{ if(rs.getString("num").equals("0"))
				return true;
			}
		} catch(Exception e) {

			e.printStackTrace();
		}
		return false;
	}
	private void Delete(String id,IGiamsatbanhangList obj)
	{
		
		dbutils db = new dbutils();
		String sql ="select count(gsbh_fk) as num from donhang where gsbh_fk='"+ id + "'";
		System.out.println(sql);
		if(kiemtra(sql))
		{  sql = "select count(gsbh_fk) as num from ddkd_gsbh where gsbh_fk='"+ id + "'";
		 System.out.println(sql);
			if(kiemtra(sql))
			{
				sql = "select count(gsbh_fk) as num from nhapp_giamsatbh where gsbh_fk='"+ id + "'";
				System.out.println(sql);
				if(kiemtra(sql))
				{
					db.update("delete from giamsatbanhang where pk_seq = '" + id + "'");
				}
				else
					obj.setMsg("Giám sát này đã có nhà phân phối rồi nên không xóa được");	
			}
			else
				obj.setMsg("Giám sát này đã có trong đơn hàng rồi nên không xóa được");	
		}
		else
			obj.setMsg("Giám sát này đã có đại diện kinh doanh rồi nên không xóa được");
		/*ResultSet rs1 = db.get("select count(gsbh_fk) as num from donhang where gsbh_fk='"+ id + "'");
		ResultSet rs2 = db.get("select count(gsbh_fk) as num from ddkd_gsbh where gsbh_fk='"+ id + "'");
		try{
			rs1.next();
			rs2.next();
			if (rs1.getString("num").equals("0") & rs2.getString("num").equals("0")){
				db.update("delete from nhapp_giamsatbh where gsbh_fk='" + id + "'");
				db.update("delete from ddkd_gsbh where gsbh_fk='" + id + "'");
				db.update("delete from giamsatbanhang where pk_seq = '" + id + "'");
				db.shutDown();
			}
		}catch(Exception e){}
		*/		
		

	}
	
	
 }

