package geso.dms.distributor.servlets.khuyenmai;

import geso.dms.distributor.beans.ctkhuyenmai.ICtkhuyenmai;
import geso.dms.distributor.beans.ctkhuyenmai.imp.XLkhuyenmai;
import geso.dms.distributor.beans.ctkhuyenmai.imp.XLkhuyenmaiDonhangDXK;
import geso.dms.distributor.beans.donhang.IDonhang;
import geso.dms.distributor.beans.donhang.imp.Donhang;
import geso.dms.distributor.beans.trakhuyenmai.ITrakhuyenmai;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class KhuyenmaiDxkSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public KhuyenmaiDxkSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  	
		
		XLkhuyenmaiDonhangDXK xlkm;
		IDonhang dhBean;
		dbutils db;
		PrintWriter out; 
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out = response.getWriter();
	    Utility util = new Utility();
	    userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
		String dhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dhId")));
		
		String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
		if(nppId == null)
			nppId = "";
		
		dhBean = new Donhang(dhId);
		dhBean.setUserId(userId);
		
		String[] schemeList = request.getParameterValues("schemeList");
		String[] trakm = request.getParameterValues("trakmId");
		String[] soxuatkm = request.getParameterValues("soxuatKM");
		String[] spId = request.getParameterValues("spSelected");
		String[] ttTrakm = request.getParameterValues("ttTrakm");
		
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = "";
	    }
	    
	    if(action.equals("save"))
	    {
	    	db = new dbutils();
	    	String query = "";
	    	try 
	    	{
				db.getConnection().setAutoCommit(false);
			
				int i = 0;
				
				if(schemeList.length > 0)
				{
				String error=	capNhatKM(dhId, nppId, db);//1
					if(error.length() >0){
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						return ;
					}
					while(i < schemeList.length)
					{
		    			if(spId[i].length() > 0 ) //masp1-soluong1;masp2-soluong2...
		    			{
		    				if(spId[i].indexOf(";") > 0) //nhieu san pham
	    					{
	    						String[] spIds = spId[i].split(";");
	    						for(int j = 0; j < spIds.length; j++)
	    						{
	    							String[] spIdss = spIds[j].split("-");
	    							{
		    							query = "Insert into donhang_ctkm_trakm(donhangId, ctkmId, trakmId, soxuat, spMa, soluong, tonggiatri) values('" + dhId + "','" + schemeList[i] + "','" + trakm[i] + "','" + soxuatkm[i] + "','" + spIdss[0].trim() + "','" + spIdss[1].trim() + "','" + ttTrakm[i] + "')";
		    							if(!db.update(query))
		    			    			{
		    								geso.dms.center.util.Utility.rollback_throw_exception(db); 
		    								out.print("Loi khi tao moi 'donhang_ctkm_trakm': " + query);
		    								return;
		    			    			}
	    							}
	    						}
	    					}
		    				else  //mot san pham
		    				{
		    					String[] spIdss = spId[i].split("-");
		    					
		    					query = "Insert into donhang_ctkm_trakm(donhangId, ctkmId, trakmId, soxuat, spMa, soluong, tonggiatri) values('" + dhId + "','" + schemeList[i] + "','" + trakm[i] + "','" + soxuatkm[i] + "','" + spIdss[0].trim() + "','" + spIdss[1].trim() + "','" + ttTrakm[i] + "')";
		    					if(!db.update(query))
				    			{
									geso.dms.center.util.Utility.rollback_throw_exception(db); 
									out.print("Loi khi tao moi 'donhang_ctkm_trakm': " + query);
									return;
				    			}
		    				}		
		    			}
		    			else
		    			{
		    				query = "Insert into donhang_ctkm_trakm(donhangId, ctkmId, trakmId, soxuat, tonggiatri) values('" + dhId + "','" + schemeList[i] + "','" + trakm[i] + "','" + soxuatkm[i] + "','" + ttTrakm[i] + "')";
		    				if(!db.update(query))
			    			{
								geso.dms.center.util.Utility.rollback_throw_exception(db); 
								out.print("Loi khi tao moi 'donhang_ctkm_trakm': " + query);
								return;
			    			}
		    			}
		    			
		    			/*query = "update CTKhuyenmai set DASUDUNG = DASUDUNG + '" + ttTrakm[i] + "' where pk_seq='" + schemeList[i] + "'";
		    		
		    			if(!db.update(query))
		    			{
							geso.dms.center.util.Utility.rollback_throw_exception(db); 
							out.print("Loi khi tao moi 'donhang_ctkm_trakm': " + query);
							return;
		    			}*/
		    			
		    			query = "update Phanbokhuyenmai set DASUDUNG = DASUDUNG + '" + ttTrakm[i] + "' where ctkm_fk ='" + schemeList[i] + "' and npp_fk='" + nppId + "'";
		    			if(!db.update(query))
		    			{
							geso.dms.center.util.Utility.rollback_throw_exception(db); 
							out.print("Loi khi tao moi 'donhang_ctkm_trakm': " + query);
							return;
		    			}
		    			i++;
					}
					
		    		
				}
				dhBean.init();
		    	dhBean.setAplaikhuyenmai(false);
		    	
		    	dhBean.createPxkId();
		    	String msg = dhBean.createPth(dhBean.getPxkId(), db);
		    	dhBean.setMessage(msg);
		    	
		    	if(msg.length() > 0)
		    	{
		    		geso.dms.center.util.Utility.rollback_throw_exception(db); 
					out.print("Loi khi tao moi 'donhang_ctkm_trakm': " + query);
		    	}
		    	else
		    	{
				db.getConnection().commit();
	    		db.getConnection().setAutoCommit(true);
		    	}
			} 
	    	catch(Exception e1) {
	    		geso.dms.center.util.Utility.rollback_throw_exception(db); 
	    		return ;
	    	}
	    	
	    	
	    	session.setAttribute("dhBean", dhBean);
	        String nextJSP = "/AHF/pages/Distributor/DonHangUpdate.jsp";
	        response.sendRedirect(nextJSP);	
	    } 
	}
	
	private String capNhatKM(String id, String nppId, dbutils db)
	{
		try 
		{
			String query = "select distinct ctkmId, tonggiatri from donhang_ctkm_trakm where donhangid='" + id + "'";
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				while(rs.next())
				{
					String ctkmId = rs.getString("ctkmId");
					String tonggiatri = rs.getString("tonggiatri");
					
					/*query="update CTKhuyenmai set DASUDUNG = DASUDUNG - '" + tonggiatri + "' where pk_seq = '" + ctkmId + "'";
					if(!db.update(query)){
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						return "Loi Dong Lenh : "+ query;
						
					}*/
					
					String st ="update Phanbokhuyenmai set DASUDUNG = DASUDUNG - '" + tonggiatri + "' where ctkm_fk='" + ctkmId + "' and npp_fk='" + nppId + "'";
					if(!db.update(st)){
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						return "Loi Dong Lenh : "+ st;
						
					}
				}

	    		query = "delete from donhang_ctkm_trakm where donhangid = '" + id + "'";
	    		if(!db.update(query)){
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					return "Loi Dong Lenh : "+ query;
					
				}
				
				rs.close();
			}
		} 
		catch (Exception e1) {
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return "Error Here :"+ e1.toString();
			
		}
		return "";

	}
}
