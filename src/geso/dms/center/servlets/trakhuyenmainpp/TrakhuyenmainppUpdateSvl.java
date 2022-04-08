package geso.dms.center.servlets.trakhuyenmainpp;

import geso.dms.center.beans.banggiamuanpp.imp.BanggiamuanppList;
import geso.dms.center.beans.trakhuyenmainpp.ITrakhuyenmainpp;
import geso.dms.center.beans.trakhuyenmainpp.ITrakhuyenmainppList;
import geso.dms.center.beans.trakhuyenmainpp.imp.Trakhuyenmainpp;
import geso.dms.center.beans.trakhuyenmainpp.imp.TrakhuyenmainppList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class TrakhuyenmainppUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    PrintWriter out;   
    
    public TrakhuyenmainppUpdateSvl() {
        super();
        
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		this.out = response.getWriter();
		Utility util = new Utility();
		ITrakhuyenmainpp obj;
	    obj = new Trakhuyenmainpp();
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
    
		out.println(userId);
		String action = util.getAction(querystring);
	    
		if (userId.length()==0)
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
 		String id = util.getId(querystring);
 		if(action.equals("delete"))
 		{
 			xoa(id);
 			
 				ITrakhuyenmainppList  objlist = new TrakhuyenmainppList();
 			    objlist.setUserId(userId);
 			    objlist.init("");
 				session.setAttribute("obj", objlist);
 						
 				String nextJSP = "/AHF/pages/Center/TraKhuyenMaiNpp.jsp";
 				response.sendRedirect(nextJSP);
 		}
 		else if(action.equals("hienthi"))
 		{
 	    obj.setType("3");
  		obj.setId(id);
  		obj.setUserId(userId);
		obj.init();
		session.setAttribute("obj",obj);
		String nextJSP = "/AHF/pages/Center/TraKhuyenMaiNppNew.jsp";
		response.sendRedirect(nextJSP);
 		}
 		else if(action.equals("chotphieu"))
 		{  dbutils db = new dbutils();
 		   db.update("update duyettrakm set trangthai ='1' where pk_seq ='"+ id +"'");
 		   String sql = "select sum(c.dasudung) as num,c.npp_fk from phanbokhuyenmai c,(select a.pk_seq,a.npp_fk,b.ctkm_fk from duyettrakm a,duyettrakm_ctkm b where a.pk_seq ='"+ id +"' and a.pk_seq = b.duyettrakm_fk and b.thanhtoan ='1') d where c.npp_fk = d.npp_fk and c.ctkm_fk = d.ctkm_fk group by c.npp_fk ";
 		   ResultSet rs = db.get(sql);
 		   try {
			rs.next();
			String num = rs.getString("num");
			String npp = rs.getString("npp_fk");
			if(!kiemtra(npp))
			sql = " insert into tienkhuyenmai_npp(NPP_FK,TONGTIEN) values('"+ npp +"','"+ num +"')";
			else
			sql =" update tienkhuyenmai_npp set tongtien = tongtien + '"+ num +"' where npp_fk ='"+ npp +"'";
		    System.out.println(sql);
			db.update(sql);
			
			
			if(rs!=null){
				rs.close();
			}
			if(db!=null){
				db.shutDown();
			}
		} catch(Exception e) {
			
			e.printStackTrace();
		}
 		   
 		   		ITrakhuyenmainppList  objlist = new TrakhuyenmainppList();
			    objlist.setUserId(userId);
			    objlist.init("");
				session.setAttribute("obj", objlist);
						
				String nextJSP = "/AHF/pages/Center/TraKhuyenMaiNpp.jsp";
				response.sendRedirect(nextJSP);
 		}
 		else
 		{
 	
	  		obj.setId(id);
	  		obj.setUserId(userId);
			obj.init();
			session.setAttribute("obj",obj);
			String nextJSP = "/AHF/pages/Center/TraKhuyenMaiNppNew.jsp";
			response.sendRedirect(nextJSP);
 		}
	}

	boolean kiemtra(String npp)
	{
		dbutils db = new dbutils();
		String sql ="select count(*) as num from tienkhuyenmai_npp where npp_fk ='"+ npp +"'";
		
		ResultSet rs = db.get(sql);
		try {
			if(rs != null)
			while(rs.next())
			{
			if(rs.getString("num").equals("0"))
				return false;
			}
			if(rs!=null){
				rs.close();
			}
			if(db!=null){
				db.shutDown();
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return true;
	}
	void xoa(String id)
	{
		dbutils db =new dbutils();
		String sql ="delete from duyettrakm_ctkm where duyettrakm_fk ='"+ id +"'";
		db.update(sql);
		sql ="delete from duyettrakm where pk_seq ='"+ id +"'";
		db.update(sql);
		db.shutDown();
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    PrintWriter out = response.getWriter();
		    ITrakhuyenmainpp obj;
		    obj = new Trakhuyenmainpp();		
			HttpSession session = request.getSession();
			Utility util = new Utility();
		    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		    if(userId ==null)
		    	userId ="";
		    obj.setUserId(userId);
		    
		    String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
		    if(id == null)
		    	id ="";
		    obj.setId(id);
		    
		    String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppid")));
		    if(nppId == null)
		    	nppId ="";
		    obj.setnppId(nppId);
		    String tongtien = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tongtien")));
		    if(tongtien == null)
		    	tongtien ="0";
		    obj.setTongtien(tongtien);
		    
		    String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
		   // System.out.println("dien giai:"+ diengiai);
		    if(diengiai == null)
		    	diengiai ="";
		    obj.setDiengiai(diengiai);
		    
		    String[] thanhtoan  = request.getParameterValues("thanhtoan");
		    obj.setThangtoan(thanhtoan);
		    
		    String [] mact = request.getParameterValues("mact");
		    obj.setMact(mact);
		    
		    String type = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("type")));
		    if(type==null)
		    	type ="";
		    obj.setType(type);
		    
		    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		//    String userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userid"));
		    obj.setUserId(userId);
		    String chon[] = request.getParameterValues("chon");
		    obj.setChon(chon);
		    obj.init();
		    if(action.equals("save"))
		    {
		       if(obj.save())
		       {
		        ITrakhuyenmainppList objlist = new TrakhuyenmainppList();
			    objlist.setUserId(userId);
			    objlist.init("");
				session.setAttribute("obj", objlist);
						
				String nextJSP = "/AHF/pages/Center/TraKhuyenMaiNpp.jsp";
				response.sendRedirect(nextJSP);
		       }   //obj.setMsg("luu thanh cong");
		    }
		    else
		    {
			    session.setAttribute("obj",obj);
			    String nextJSP = "/AHF/pages/Center/TraKhuyenMaiNppNew.jsp";
				response.sendRedirect(nextJSP);
		    }
	}

}
