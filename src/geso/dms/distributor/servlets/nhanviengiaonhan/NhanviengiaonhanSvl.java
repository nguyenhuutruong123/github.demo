package geso.dms.distributor.servlets.nhanviengiaonhan;

import geso.dms.distributor.beans.nhanviengiaonhan.INhanviengiaonhan;
import geso.dms.distributor.beans.nhanviengiaonhan.INhanviengiaonhanList;
import geso.dms.distributor.beans.nhanviengiaonhan.imp.Nhanviengiaonhan;
import geso.dms.distributor.beans.nhanviengiaonhan.imp.NhanviengiaonhanList;
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

public class NhanviengiaonhanSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	
    public NhanviengiaonhanSvl() 
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
		
		INhanviengiaonhanList obj;
		PrintWriter out; 
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out  = response.getWriter();
    

	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String nvgnId = util.getId(querystring);

	    if (action.equals("delete")){
			if ( Utility.CheckSessionUser( session,  request, response))
			{
				Utility.RedireactLogin(session, request, response);
			}
			else if( Utility.CheckRuleUser( session,  request, response, "NhanviengiaonhanSvl","", Utility.XOA ))
			{
				Utility.RedireactLogin(session, request, response);
			}
			else{
				String msg =Delete(nvgnId);
		    
		    	obj = new NhanviengiaonhanList();
		    	obj.setMsg(msg);
		    	out.print(nvgnId);
			    obj.setUserId(userId);
			    obj.init("");
			    
				session.setAttribute("obj", obj);
						
				String nextJSP = "/AHF/pages/Distributor/NhanVienGiaoNhan.jsp";
				response.sendRedirect(nextJSP);
			}

	    }
	    else{    
		    obj = new NhanviengiaonhanList();
		    obj.setUserId(userId);
		    obj.init("");
		    
			session.setAttribute("obj", obj);
					
			String nextJSP = "/AHF/pages/Distributor/NhanVienGiaoNhan.jsp";
			response.sendRedirect(nextJSP);
	    }
		
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		INhanviengiaonhanList obj  = new NhanviengiaonhanList();
		PrintWriter out; 
		String userId;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out = response.getWriter();
	    Utility util = new Utility();
		HttpSession session = request.getSession();
	    userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = "";
	    }
	    out.println(action); 
	          
	    if (action.equals("new"))
	    {
	    	if ( Utility.CheckSessionUser( session,  request, response))
			{
				Utility.RedireactLogin(session, request, response);
			}
			else if( Utility.CheckRuleUser( session,  request, response, "NhanviengiaonhanSvl","", Utility.THEM ))
			{
				Utility.RedireactLogin(session, request, response);
			}
	    	// Empty Bean for distributor
			else{
				INhanviengiaonhan nvgnBean = (INhanviengiaonhan) new Nhanviengiaonhan("");
				nvgnBean.setUserId(userId);
		    	nvgnBean.createRS();
		    	
		    	// Save Data into session
		    	session.setAttribute("nvgnBean", nvgnBean);
	    		
	    		String nextJSP = "/AHF/pages/Distributor/NhanVienGiaoNhanNew.jsp";
	    		response.sendRedirect(nextJSP);
			}
	    	
    		
	    }
	    else
	    {
	    	String search = this.getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/AHF/pages/Distributor/NhanVienGiaoNhan.jsp");	    		    	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, INhanviengiaonhanList obj) 
	{	
		Utility util = new Utility();
		String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
    	if ( nppId == null)
    		nppId = "";
    	obj.setNppId(nppId);
    	
		String nvgnTen = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nvgnTen")));
    	if ( nvgnTen == null)
    		nvgnTen = "";
    	obj.setTennv(nvgnTen);
    	
    	String ddkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdTen")));
    	if (ddkdId == null)
    		ddkdId = "";    	
    	obj.setDdkdId(ddkdId);
    	
    	String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"))); 	
    	if (trangthai == null)
    		trangthai = "";    		
    	if (trangthai.equals("2"))
    		trangthai = "";   	
    	obj.setTrangthai(trangthai);
    	    	
    	String query = "";//"select distinct d.pk_seq as nvgnId, d.ten as nvgnTen, d.trangthai, d.diachi, d.npp_fk as nppId, d.ngaytao, d.ngaysua, e.ten as nguoitao, f.ten as nguoisua, d.dienthoai from tuyenbanhang a, nvgn_kh b, khachhang_tuyenbh c, nhanviengiaonhan d, nhanvien e, nhanvien f" 
    		//		   + " where a.pk_seq = c.tbh_fk and c.khachhang_fk=b.khachhang_fk and a.npp_fk='" + nppId + "' and b.nvgn_fk = d.pk_seq and e.pk_seq = d.nguoitao and f.pk_seq = d.nguoisua ";
    	obj.getDatasearch().clear();	
    	if (nvgnTen.length() > 0)
    	{ 
			query = query + " and  upper(dbo.ftBoDau(a.ten)) like  upper(?)";
			obj.getDatasearch().add("%" + util.replaceAEIOU(nvgnTen) + "%");
    	}
    	
    	if (ddkdId.length() > 0)
    	{
			query = query + " and a.ddkd_fk = ?";
			obj.getDatasearch().add(ddkdId);
    	}
    	
    	if (trangthai.length() > 0)
    	{
    		query = query + " and a.trangthai=?";
    		obj.getDatasearch().add(trangthai);
    	}
    	  	
    	//query = query + " order by nvgnTen";
    	
    	
    	return query;
	}

	private String Delete(String nvgnId) 
	{
		dbutils db = new dbutils(); 
		String query = " select count(*) as sodong from donhang where NVGN_FK=" + nvgnId + "";
		ResultSet rs = db.get(query);
	int sodong = 0;
	if(rs != null)
	{
		try 
		{
			if(rs.next())
			{
				sodong = rs.getInt("sodong");
				rs.close();
			}
		} catch(Exception e) { sodong = 0; }
	}
	if(sodong > 0)
		return "Nhân viên giao nhận đã phát sinh đơn hàng không thể xóa!";
		db.update("delete from NVGN_KH where nvgn_fk='" + nvgnId + "'");
		db.update("delete from nhanviengiaonhan where pk_seq='" + nvgnId + "'");
		
		db.update("commit");
		db.shutDown();
		return "";
	}

}
