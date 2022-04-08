package geso.dms.distributor.servlets.phieuthuhoi;

import geso.dms.distributor.beans.phieuthuhoi.IPhieuthuhoiList;
import geso.dms.distributor.beans.phieuthuhoi.imp.PhieuthuhoiList;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PhieuthuhoiSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	
    public PhieuthuhoiSvl() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
		
		IPhieuthuhoiList obj;
		PrintWriter out; 
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out = response.getWriter();
	    	    
  
	    Utility util = new Utility();
 	    
	    String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String pthId = util.getId(querystring);

	    String msg = "";
	    if (action.equals("chotphieu"))
	    {	
	    	String nppId = querystring.substring(querystring.indexOf("nppId=") + 6, querystring.length());
	    	msg = Chotphieuthuhoi(nppId, pthId);
	    }
	   	    
	    obj = new PhieuthuhoiList();
	    obj.setUserId(userId);
	    int items = 50;
	    int splittings = 20;
	    if(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("items"))) != null)
	    	items = Integer.parseInt(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("items"))));
	    if(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("splittings")) != null)
	    	splittings = Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("splittings")));
	    obj.setItems(items);
    	obj.setSplittings(splittings);
    	
	    obj.init("");
	    obj.setMsg(msg);
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/AHF/pages/Distributor/PhieuThuHoi.jsp";
		response.sendRedirect(nextJSP);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
		
		IPhieuthuhoiList obj = new PhieuthuhoiList();
		PrintWriter out; 
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out = response.getWriter();
	    Utility util = new Utility();
	    userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    String chucnang=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if(!chucnang.equals("clear"))
	    {
    	String search = this.getSearchQuery(request, obj);
    	
    	obj.setUserId(userId);
    	obj.init(search);
			
    	session.setAttribute("obj", obj);  	
		session.setAttribute("userId", userId);
    		
		response.sendRedirect("/AHF/pages/Distributor/PhieuThuHoi.jsp");
	    }
	    else if(chucnang.equals("view") || chucnang.equals("next") || chucnang.equals("prev")){
	    	String search = getSearchQuery(request, obj);
		    int items = 50;
		    int splittings = 20;
		    if(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("items")) != null)
		    	items = Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("items")));
		    if(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("splittings")) != null)
		    	splittings = Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("splittings")));
		    obj.setItems(items);
	    	obj.setSplittings(splittings);
	    	obj.setNxtApprSplitting(Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
	    	obj.setAttribute(request, chucnang, "list", "crrApprSplitting", "nxtApprSplitting");
	    	session.setAttribute("obj", obj);
	    	response.sendRedirect("/AHF/pages/Distributor/PhieuThuHoi.jsp");	
	    }
	    else
	    { 
	    	//String search = this.getSearchQuery(request, obj);
	    	
	    	obj = new PhieuthuhoiList();
	    	obj.setUserId(userId);
	    	obj.setAttribute(request, chucnang, "list", "crrApprSplitting", "nxtApprSplitting");

	    	//------------------------
	    	
	    	String search = getSearchQuery(request, obj);
	    	obj.init(search);
	    	session.setAttribute("obj", obj);  	
			session.setAttribute("userId", userId);
			response.sendRedirect("/AHF/pages/Distributor/PhieuThuHoi.jsp");
	    }
		}
	}
	
	private String getSearchQuery(HttpServletRequest request, IPhieuthuhoiList obj) 
	{	
		Utility util = new Utility();
		String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
    	if ( nppId == null)
    		nppId = "";
    	obj.setNppId(nppId);
    	
		String nvgnId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nvgnTen")));
    	if ( nvgnId == null)
    		nvgnId = "";
    	obj.setNvgnId(nvgnId);
    	
    	String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));
    	if ( trangthai == null)
    		trangthai = "";
    	obj.setTrangthai(trangthai);
    	
    	String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
    	if (tungay == null)
    		tungay = "";    	
    	obj.setTungay(tungay);
    	   	
    	String query = "";
    	//query = "select pth.pk_seq as pthId, pth.phieuxuatkho_fk as pxkId, pth.trangthai, pth.ngaytao, pth.ngaysua, nv.ten as nguoitao, nv2.ten as nguoisua ";
		//query = query + "from phieuthuhoi pth inner join nhanvien nv on pth.nguoitao = nv.pk_seq inner join nhanvien nv2 on pth.nguoisua = nv2.pk_seq inner join phieuxuatkho pxk on pth.phieuxuatkho_fk = pxk.pk_seq ";
		//query = query + " where pxk.npp_fk = '" + nppId + "' ";
    		
    	if (nvgnId.length() > 0)
    	{
			query = query + " and pxk.nvgn_fk='" + nvgnId + "'";			
    	}
    	
    	if (trangthai.length() > 0)
    	{
			query = query + " and pth.trangthai='" + trangthai + "'";			
    	}
    	
    	if (tungay.length() > 0)
    	{
    		query = query + " and pth.ngaytao >= '" + tungay + "'"; 
    	}
    	//query = query + " order by pth.pk_seq DESC";
    	return query;
	}
	
	private String Chotphieuthuhoi(String nppId, String pthId) 
	{
		dbutils db = new dbutils();
    	
		String msg = "";
		try 
		{
			db.getConnection().setAutoCommit(false);
		
			//cap nhat ton kho sanpham
			geso.dms.center.util.Utility util = new geso.dms.center.util.Utility();
			String query = "select b.ngaytao,sanpham_fk as spId, soluong, kho_fk as khoId, kbh_fk as kbhId from phieuthuhoi_sanpham a inner join phieuthuhoi b on a.pth_fk = b.pk_seq where pth_fk = '" + pthId + "'";
			ResultSet rs = db.get(query);
			/*if(rs != null)*/
			{
				while(rs.next())
				{
					/*query = "update nhapp_kho set soluong =  soluong + " + rs.getString("soluong") + ", available = available + " + rs.getString("soluong") + " where sanpham_fk=" + rs.getString("spId") +" and npp_fk = " + nppId + " and kbh_fk=" + rs.getString("kbhId") + " and kho_fk = " + rs.getString("khoId");
					System.out.println("Cau lenh cap nhat san pham la: " + query + "\n");
					if(db.updateReturnInt(query)!=1)
					{
						db.getConnection().rollback();
						msg = "Khong the cap nhat nhapp_kho " + query;
						return msg;
					}*/
					msg=util.Update_NPP_Kho_Sp(rs.getString("ngaytao"),pthId, "TẠO ĐƠN HÀNG_988", db, rs.getString("khoId"), rs.getString("spId"), nppId, rs.getString("kbhId"),  rs.getDouble("soluong"), 0.0 , rs.getDouble("soluong") , 0.0);
			    	if(msg.length()>0)
			    	{
			    		db.getConnection().rollback();
			    		msg = "Lỗi kho khi tạo đơn hàng: " + msg;
			    		return msg;
			    	}	
			    	
			    	String msg1=util.Update_NPP_Kho_Sp_Chitiet(rs.getString("ngaytao"), "create donhang DHID: "+pthId ,  db, rs.getString("khoId"), rs.getString("spId"), nppId,  rs.getString("kbhId"), rs.getString("ngaytao"), rs.getDouble("soluong"), 0, rs.getDouble("soluong"), 0, 0);
					if (msg1.length()> 0) 
					{
						msg=msg1;
						db.getConnection().rollback();
						return msg1;
					}
				}
				rs.close();
			}
			
			//cap nhat ton kho sanpham khuyen mai
			query = "select sanpham_fk as spId, soluong, kho_fk as khoId, kbh_fk as kbhId from phieuthuhoi_spkm where pth_fk = '" + pthId + "'";
			System.out.println("Cau lenh lay spkm la: " + query + "\n");
			ResultSet rsKm = db.get(query);
			/*if(rsKm != null)*/
			{
				while(rsKm.next())
				{
					query = "update nhapp_kho set soluong = soluong + " + rsKm.getString("soluong") + ", available = available + " + rsKm.getString("soluong") + " where sanpham_fk=" + rsKm.getString("spId") +" and npp_fk = " + nppId + " and kbh_fk=" + rsKm.getString("kbhId") + " and kho_fk = " + rsKm.getString("khoId");
					System.out.println("Cau lenh cap nhat san pham khuyen mai la: " + query + "\n");
					if(db.updateReturnInt(query)!=1)
					{
						db.getConnection().rollback();
						msg = "Khong the cap nhat san pham khuyen mai nhapp_kho " + query;
						return msg;
					}
				}
				rsKm.close();
			}
			
			query="update phieuthuhoi set trangthai = '1' where pk_seq = '" + pthId + "' and trangthai = 0 ";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				msg = "Khong the cap nhat, lỗi trạng thái";
				return msg;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch(Exception e) {
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return "Error Here :"+e.toString();
		}finally{
			db.shutDown();
		}
		
		return msg;
	}

}
