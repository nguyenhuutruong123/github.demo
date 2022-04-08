package geso.dms.center.servlets.thuongvuotmuc;

import geso.dms.center.beans.thuongvuotmuc.*;
import geso.dms.center.beans.thuongvuotmuc.imp.*;
import geso.dms.center.beans.tinhthunhap.INhanvien;
import geso.dms.center.beans.tinhthunhap.imp.Nhanvien;
import geso.dms.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class ThuongvuotmucUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
       
    public ThuongvuotmucUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		IThuongvuotmuc ttnBean;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    	    
	    String id = util.getId(querystring);
	   
	    ttnBean = new Thuongvuotmuc(id);
	    ttnBean.setId(id);
	    ttnBean.setUserId(userId);
	    
	    ttnBean.init();
        session.setAttribute("ttnBean", ttnBean);
        
        String nextJSP = "/AHF/pages/Center/ThuongVuotMucUpdate.jsp";
        if(querystring.indexOf("display") >= 0)
        	nextJSP = "/AHF/pages/Center/ThuongVuotMucDisplay.jsp";
        if (querystring.indexOf("copy") >= 0)
		{
			ttnBean.setId("");
			ttnBean.setThang("");
			ttnBean.setNam("");
			ttnBean.setDiengiai("");
			nextJSP = "/AHF/pages/Center/ThuongVuotMucNew.jsp";
		}
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IThuongvuotmuc ttnBean;

		Utility util = new Utility();
	    String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));	
	    if(id == null){  	
	    	ttnBean = new Thuongvuotmuc("");
	    }else{
	    	ttnBean = new Thuongvuotmuc(id);
	    }
	    	    
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		ttnBean.setUserId(userId);	       
    	
		String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
		if (diengiai == null)
			diengiai = "";
		ttnBean.setDiengiai(diengiai);
		
		String thang = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thang")));
		if (thang == null)
			thang = "";
		ttnBean.setThang(thang);
		
		String nam = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nam")));
		if (nam == null)
			nam = "";
		ttnBean.setNam(nam);
		
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
 		
 		//System.out.println("__Action la: " + action);
 		
    	List<IThuongvuotmucDetail> tcDetailList = new ArrayList<IThuongvuotmucDetail>();
    	
    	String[] dvkdIds = request.getParameterValues("dvkdIds");
    	String[] kbhIds = request.getParameterValues("kbhIds");
    	String[] chucvu = request.getParameterValues("chucvu");
    	
    	if(dvkdIds != null)
    	{
    		IThuongvuotmucDetail detail = null;
    		for(int i = 0; i < dvkdIds.length; i++)
    		{
    			//if(dvkdIds[i].trim().length() > 0 && kvIds[i].trim().length() > 0 && kbhIds[i].trim().length() > 0 && chucvu[i].trim().length() > 0 )
    			//{
    			
	    			String[] kvIds = request.getParameterValues("khuvuc" + Integer.toString(i));
	    			String kvId = "";
	    			if(kvIds != null)
	    			{
	    				for(int j = 0; j < kvIds.length; j++)
	    					kvId += kvIds[j] + ",";
	    				
	    				if(kvId.trim().length() > 0)
	    					kvId = kvId.substring(0, kvId.length() - 1);
	    			}
    			
	    			if(kvIds != null && kvId.trim().length() > 0)
	    			{
	    				detail = new ThuongvuotmucDetail();
		    			
		    			detail.setDvkdId(dvkdIds[i]);
		    			detail.setKbhId(kbhIds[i]);
		    			detail.setKvId(kvId);
		    			detail.setChucvu(chucvu[i]);
		    			detail.setSTT(Integer.toString(i));
		    			
		    			String[] nhomthuong = request.getParameterValues("nhomthuong" + Integer.toString(i));
		    			String[] tumuc = request.getParameterValues("tumuc" + Integer.toString(i));
		    			String[] denmuc = request.getParameterValues("denmuc" + Integer.toString(i));
		    			String[] thuong = request.getParameterValues("thuong" + Integer.toString(i));
		    			
		    			detail.setNhomthuong(nhomthuong);
		    			detail.setTumuc(tumuc);
		    			detail.setDenmuc(denmuc);
		    			detail.setThuong(thuong);
		    			
		    			
		    			List<INhanvien> nvDetailList = new ArrayList<INhanvien>();
		    			
		    			String[] nppId = request.getParameterValues("npp" + Integer.toString(i) + "Id");
		    			String[] nvId = request.getParameterValues("nv" + Integer.toString(i) + "Id");
		    			String[] nvTen = request.getParameterValues("nv" + Integer.toString(i) + "Ten");
		    			String[] nvChonIds = request.getParameterValues("nv" + Integer.toString(i) + "ChonIds");
		    			
		    			if(nvChonIds != null)
		    			{
		    				String nvSelected = "";
		    				for(int k = 0; k < nvChonIds.length; k++)
		    				{
		    					nvSelected += nvChonIds[k] + ",";
		    				}
		    				
		    				if(nvSelected.trim().length() > 0)
		    				{
		    					nvSelected = nvSelected.substring(0, nvSelected.length() - 1);
		    					detail.setNhanvienIds(nvSelected);
		    				}
		    				
		    				System.out.println("____Nhan vien IDS: " + nvSelected);
		    			}
		    			
		    			if(nvId != null)
		    			{
		    				for(int ii = 0; ii < nvId.length; ii++)
		    				{
		    					INhanvien nv = null;
		    					
	    						nv = new Nhanvien();
	    						
	    						nv.setNppTen(nppId[ii]);
	    						nv.setId(nvId[ii]);
	    						nv.setTen(nvTen[ii]);
	    						
	    						nvDetailList.add(nv);
		    				}
		    			}
		    			
		    			String gsbhSelected = getGSBH_Selected(detail, tcDetailList);
		    			
		    			detail.setGsbhSelected(gsbhSelected);
		    			
		    			String ddkdSelected = getDDKD_Selected(detail, tcDetailList);
		    			detail.setDdkdSelected(ddkdSelected);
		    			
		    			if(action.equals("save"))
		    			{
			    			if(nvDetailList.size() > 0)
			    			{
			    				detail.setNhanvienList(nvDetailList);
			    			}
			    			else
			    			{
				    			if(chucvu[i].trim().length() > 0)
				    			{
				    				detail.InitNhanVien();
				    			}
			    			}
		    			}
		    			else
		    			{
		    				if(chucvu[i].trim().length() > 0)
			    			{
			    				detail.InitNhanVien();
			    			}
		    			}
		    			
		    			tcDetailList.add(detail);
	    			}
	    			
    			//}
    		}
    		
    		//System.out.println("----So san pham: " + tcDetailList.size());
    		ttnBean.setTieuchiDetail(tcDetailList);
    	}
		
 		
 		if(action.equals("save"))
 		{
    		if (id == null )
    		{
    			if (!ttnBean.createKhl())
    			{
    		    	ttnBean.setUserId(userId);
    		    	ttnBean.createRs();
    		    	
    		    	session.setAttribute("userId", userId);
    				session.setAttribute("ttnBean", ttnBean);
    				
    				String nextJSP = "/AHF/pages/Center/ThuongVuotMucNew.jsp";
    				response.sendRedirect(nextJSP);
    			}
    			else
    			{
    				IThuongvuotmucList obj = new ThuongvuotmucList();
    				obj.setUserId(userId);
    				obj.init("");
    				
    				session.setAttribute("obj", obj);
    				session.setAttribute("userId", userId);
		    		
    				response.sendRedirect("/AHF/pages/Center/ThuongVuotMuc.jsp");	    	
    			}		
    		}
    		else
    		{
    			if (!(ttnBean.updateKhl()))
    			{			
    		    	ttnBean.setUserId(userId);
    		    	ttnBean.createRs();
    		    	
    		    	session.setAttribute("userId", userId);
    				session.setAttribute("ttnBean", ttnBean);
    				
    				String nextJSP = "/AHF/pages/Center/ThuongVuotMucUpdate.jsp";
    				response.sendRedirect(nextJSP);
    			}
    			else
    			{
    				IThuongvuotmucList obj = new ThuongvuotmucList();
    				obj.setUserId(userId);
    				obj.init("");
    				
    				session.setAttribute("obj", obj);
    				session.setAttribute("userId", userId);
		    		
    				response.sendRedirect("/AHF/pages/Center/ThuongVuotMuc.jsp");		   	
    			}
    		}
	    }
		else
		{
			ttnBean.createRs();
			session.setAttribute("userId", userId);
			
			session.setAttribute("ttnBean", ttnBean);
			String nextJSP;
			if (id == null){
				nextJSP = "/AHF/pages/Center/ThuongVuotMucNew.jsp";
			}
			else{
				nextJSP = "/AHF/pages/Center/ThuongVuotMucUpdate.jsp";   						
			}
			response.sendRedirect(nextJSP);
		}		
	}

	private String getGSBH_Selected(IThuongvuotmucDetail detail, List<IThuongvuotmucDetail> tcDetailList) 
	{
		String gsbhIds = "";
		for(int i = 0; i < tcDetailList.size(); i++)
		{
			IThuongvuotmucDetail ttnDetai = tcDetailList.get(i);
			if( ( ttnDetai.getSTT() != detail.getSTT() ) && ttnDetai.getChucvu().equals("SS") )
			{
				gsbhIds += ttnDetai.getNhanvienIds() + ",";
			}
		}
		if(gsbhIds.length()>0)
		{
			gsbhIds=gsbhIds.trim();
			gsbhIds = gsbhIds.substring(0, gsbhIds.length() - 1);
		}
		return gsbhIds;
	}
	
	private String getDDKD_Selected(IThuongvuotmucDetail detail, List<IThuongvuotmucDetail> tcDetailList) 
	{
		String ddkdIds = "";
		
		for(int i = 0; i < tcDetailList.size(); i++)
		{
			IThuongvuotmucDetail ttnDetai = tcDetailList.get(i);
			
			if( ( ttnDetai.getSTT() != detail.getSTT() ) && ttnDetai.getChucvu().equals("SR") )
			{
				ddkdIds += ttnDetai.getNhanvienIds() + ",";
			}
		}
		if(ddkdIds.length() > 0)
		{
			ddkdIds=ddkdIds.trim();
			ddkdIds = ddkdIds.substring(0, ddkdIds.length() - 1);
		}
		
		return ddkdIds;
	}

	

}
