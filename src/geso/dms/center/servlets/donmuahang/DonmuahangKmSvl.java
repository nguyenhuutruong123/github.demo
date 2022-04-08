package geso.dms.center.servlets.donmuahangkm;

import geso.dms.center.beans.donmuahang.IDonmuahangList;
import geso.dms.center.beans.donmuahang.IERP_DonDatHang;
import geso.dms.center.beans.donmuahang.imp.DonmuahangList;
import geso.dms.center.beans.donmuahang.imp.ERP_DonDatHang;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class DonmuahangKmSvl
 */
@WebServlet("/DonmuahangKmSvl")
public class DonmuahangKmSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DonmuahangKmSvl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		IDonmuahangList obj;
		String userId;

		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    out = response.getWriter();
	    
	    String querystring = request.getQueryString();
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String ddhId = util.getId(querystring);
	    
	    userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    if (action.equals("delete"))
	    {
	    	Delete(ddhId);
	    }
	    else if(action.equals("SAboduyet"))
	    {
	    	//trang thai dang o mua sale admin vua duyet // trangthai=2 thi co the cap nhat ve trang thai=1
	    	SABODUYENT(ddhId);
	    	
	    }
	    else if(action.equals("chot"))
	    {
	    	SADuyet(ddhId);
	    }
	    
	    out.println(userId);
	    obj = new DonmuahangList();
	    obj.setUserId(userId);
	    
		String query1 =" select distinct a.ngaydat, a.pk_seq as chungtu, f.ten as nppTen, e.donvikinhdoanh,  "+
		" a.sotienavat, b.ten as nguoitao, c.ten as nguoisua, a.trangthai,isnull( a.soid ,'0') as soid, isnull (nh.ngaychungtu,'0')as ngayhd, "+
		" isnull(nh.sotienbvat,'0') as tienhd from dondathang a inner join "+ 
		" nhanvien b on  a.nguoitao = b.pk_seq   "+
		" inner join  nhanvien c on a.nguoisua = c.pk_seq  "+
		" inner join dondathang_sp d on a.pk_seq = d.dondathang_fk "+ 
		" inner join donvikinhdoanh e on a.dvkd_fk = e.pk_seq  "+
		" inner join  nhaphanphoi  f  on a.npp_fk = f.pk_seq  "+ 
		" left join nhaphang nh on nh.dathang_fk=a.pk_seq " +
		" where ( a.trangthai <>'0') and  iskm='1'  and  f.pk_seq in "+ util.quyen_npp(userId) ; 
	    obj.createDdhlist(query1);
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/Growth/pages/Center/ERP_DonMuaHangKm.jsp";
		response.sendRedirect(nextJSP);
	}

	private void SADuyet(String ddhId) {
		// TODO Auto-generated method stub
		dbutils db = new dbutils();
		String query = "update  dondathang set trangthai='2' where pk_Seq='" + ddhId + "'";
		db.update(query);
		db.shutDown();
	}

	private void SABODUYENT(String ddhId) {
		// TODO Auto-generated method stub
		dbutils db = new dbutils();
		String query = "update  dondathang set trangthai='1' where pk_Seq='" + ddhId + "'";
		db.update(query);
		db.shutDown();
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		  request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    PrintWriter out = response.getWriter();
		    IDonmuahangList obj;
		    String userId;
		    Utility util = new Utility();
		    
		    obj = new DonmuahangList();		
			HttpSession session = request.getSession();
		    userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		    
		    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		    if (action == null){
		    	action = "";
		    }    
		    
		    out.println(action); 	    
		    
		    if(action.equals("view") || action.equals("next") || action.equals("prev")){
		    	//obj = new DonmuahangList();
		    	obj.setUserId(userId);
		    	obj.setNxtApprSplitting(Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
	    		
	        	obj.createDdhlist("");
	        	
	        	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	        		
	        	session.setAttribute("obj", obj);
	    	    		
	        	String nextJSP = "/Growth/pages/Center/ERP_DonMuaHangKm.jsp";	    	
	        	
	    	    response.sendRedirect(nextJSP);

		    }else if(action.equals("new")){
		    	IERP_DonDatHang objddh=new ERP_DonDatHang();
		    	objddh.Init();
		    	session.setAttribute("obj", objddh);
		    	String nextJSP = "/Growth/pages/Center/ERP_DonMuaHangKmNew.jsp";	 
		    	response.sendRedirect(nextJSP);
		    }
		    else{
		    	    
	    	String search = getSearchQuery(request, obj, userId);   	
	    	obj.setUserId(userId);
	    	obj.createDdhlist(search);
				
	    	session.setAttribute("obj", obj);
		    		
	    	String nextJSP = "/Growth/pages/Center/ERP_DonMuaHangKm.jsp";	    	
	    	out.println(search);
		    response.sendRedirect(nextJSP);
		    }
	}
	private void Delete(String ddhId){
		//xoa thi =5
		dbutils db = new dbutils();
		try{
			db.getConnection().setAutoCommit(false);
			
			
			//lay chi tiet don hang cu ra va cap nhat lai so luong check va available ,dong thoi cap nhat lai cot dadathang trong bang denghidathang_sp
			String sql="select soluong,sanpham_fk,npp.khosap from dondathang_sp inner join dondathang ddh on ddh.pk_seq=dondathang_fk inner join nhaphanphoi npp on npp.pk_seq=ddh.npp_fk  where dondathang_fk="+ddhId;
			System.out.println("Get Detail :"+ sql);
			ResultSet rs_detail_ddh=db.get(sql);
			if(rs_detail_ddh!=null){
				while (rs_detail_ddh.next()){
				sql = "update  erp_khott_sanpham set booked = booked - '" +rs_detail_ddh.getInt("soluong") + "', available = available + '" + rs_detail_ddh.getInt("soluong") + "' where sanpham_fk='" + rs_detail_ddh.getString("sanpham_fk") +"' and khott_fk='" + rs_detail_ddh.getString("khosap")+ "'";
				System.out.println("Get Detail :"+ sql);
					if(!db.update(sql)){
						System.out.println("Error update KHO : line -524 : HoaDon : "+sql);
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						return ;
					}
				}
			}
			String query = "update  dondathang set trangthai='5' where pk_Seq='" + ddhId + "'";
			db.update(query);
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			db.shutDown();
			
		}catch(Exception er){
			System.out.println(er.toString());
		 geso.dms.center.util.Utility.rollback_throw_exception(db);	
		}
	}
	private String getSearchQuery(HttpServletRequest request, IDonmuahangList obj, String userId){
//	    PrintWriter out = response.getWriter();
		
		Utility util = new Utility();
		String sku = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sku")));
    	if (sku == null)
    		sku = "";
    	
    	sku = sku.split("-")[0].trim();
    	obj.setSKU(sku);
    	
    	String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
    	if (tungay == null)
    		tungay = "";    	
    	obj.setTungay(tungay);

    	String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
    	if (denngay == null)
    		denngay = "";    	
    	obj.setDenngay(denngay);
    	   	
    	String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));   	
    	if (trangthai == null)
    		trangthai = "";    	
	
    	if (trangthai.equals("0"))
    		trangthai = "";
    	
    	obj.setTrangthai(trangthai);
    	
    	String kvId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kvId")));   	
    	if (kvId == null)
    		kvId = "";    	
	
    	if (kvId.equals("0"))
    		kvId = "";
    	
    	obj.setKvId(kvId);

    	String loaidonhang=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaidonhang"));
    	System.out.println("Loaidonhang : " +loaidonhang);
    	
    	String query1 =" select distinct a.ngaydat, a.pk_seq as chungtu, f.ten as nppTen, e.donvikinhdoanh,  "+
		" a.sotienavat, b.ten as nguoitao, c.ten as nguoisua, a.trangthai,isnull( a.soid ,'0') as soid, isnull (nh.ngaychungtu,'0')as ngayhd, "+
		" isnull(nh.sotienbvat,'0') as tienhd from dondathang a inner join "+ 
		" nhanvien b on  a.nguoitao = b.pk_seq   "+
		" inner join  nhanvien c on a.nguoisua = c.pk_seq  "+
		" inner join dondathang_sp d on a.pk_seq = d.dondathang_fk "+ 
		" inner join donvikinhdoanh e on a.dvkd_fk = e.pk_seq  "+
		" inner join  nhaphanphoi  f  on a.npp_fk = f.pk_seq  "+ 
		" left join nhaphang nh on nh.dathang_fk=a.pk_seq " +
		" where ( a.trangthai <>'0') and iskm='1'   and  f.pk_seq in "+ util.quyen_npp(userId) ; 
	
	
		
    	if (sku.length()>0){
			query1 = query1 + "and d.sanpham_fk in (select pk_seq from sanpham where ma= '" + sku + "') and d.soluong > 0";
			 	}
    	
    	if (tungay.length()>0){
			query1 = query1 + " and a.ngaydat >= '" + tungay+ "'";
			
    		
    	}

    	if (denngay.length()>0){
			query1 = query1 + " and a.ngaydat <= '" + denngay+ "'";
		
    		
    	}

    	if(trangthai.length() > 0){
    		query1 = query1 + " and a.trangthai = '" + trangthai + "'";
    		
    	}

    	if(kvId.length() > 0){
    		query1 = query1 + " and f.khuvuc_fk = '" + kvId + "'";
    	
    	}
    	
    	
    	System.out.println(query1);
    	
    	return query1;
	}	
		


}
