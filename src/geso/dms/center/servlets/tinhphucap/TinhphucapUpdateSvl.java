package geso.dms.center.servlets.tinhphucap;

import geso.dms.center.beans.tinhphucap.*;
import geso.dms.center.beans.tinhphucap.imp.*;
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


public class TinhphucapUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
       
    public TinhphucapUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		ITinhphucap ttnBean;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    	    
	    String id = util.getId(querystring);
	   
	    ttnBean = new Tinhphucap(id);
	    ttnBean.setId(id);
	    ttnBean.setUserId(userId);
	    
	    ttnBean.init();
        
        
        String nextJSP = "/AHF/pages/Center/TinhPhuCapUpdate.jsp";
        if(querystring.indexOf("copy") >= 0)
        {
        	ttnBean.setId("");
        	ttnBean.setDiengiai("");
        	ttnBean.setThang("");
        	ttnBean.setNam("");
        	nextJSP = "/AHF/pages/Center/TinhPhuCapNew.jsp";
        }
        if(querystring.indexOf("display") >= 0)
        	nextJSP = "/AHF/pages/Center/TinhPhuCapDisplay.jsp";
        
        session.setAttribute("ttnBean", ttnBean);
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		ITinhphucap ttnBean;

		Utility util = new Utility();
	    String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));	
	    if(id == null){  	
	    	ttnBean = new Tinhphucap("");
	    }else{
	    	ttnBean = new Tinhphucap(id);
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
 		
    	List<ITinhphucapDetail> tcDetailList = new ArrayList<ITinhphucapDetail>();
    	
    	String[] dvkdIds = request.getParameterValues("dvkdIds");
    	//String[] kvIds = request.getParameterValues("kvIds");
    	String[] kbhIds = request.getParameterValues("kbhIds");
    	String[] chucvu = request.getParameterValues("chucvu");
    	
    	if(dvkdIds != null)
    	{
    		ITinhphucapDetail detail = null;
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
	    				detail = new TinhphucapDetail();
		    			
		    			detail.setDvkdId(dvkdIds[i]);
		    			detail.setKbhId(kbhIds[i]);
		    			detail.setKvId(kvId);
		    			detail.setChucvu(chucvu[i]);
		    			detail.setSTT(Integer.toString(i));
		    			
		    			String[] maDetail = request.getParameterValues("maDetail" + Integer.toString(i));
		    			String[] noidung = request.getParameterValues("noidung" + Integer.toString(i));
		    			String[] trongso = request.getParameterValues("trongso" + Integer.toString(i));
		    			String[] tinhtheongaycong = request.getParameterValues("tinhtheongaycong" + Integer.toString(i));
		    			
		    			detail.setMaDetail(maDetail);
		    			detail.setNoidung(noidung);
		    			detail.setTrongso(trongso);
		    			detail.setTinhtheongaycong(tinhtheongaycong);
		    			
		    			
		    			List<INhanvien> nvDetailList = new ArrayList<INhanvien>();
		    			
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
    				
    				String nextJSP = "/AHF/pages/Center/TinhPhuCapNew.jsp";
    				response.sendRedirect(nextJSP);
    			}
    			else
    			{
    				ITinhphucapList obj = new TinhphucapList();
    				obj.setUserId(userId);
    				obj.init("");
    				
    				session.setAttribute("obj", obj);
    				session.setAttribute("userId", userId);
		    		
    				response.sendRedirect("/AHF/pages/Center/TinhPhuCap.jsp");	    	
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
    				
    				String nextJSP = "/AHF/pages/Center/TinhPhuCapUpdate.jsp";
    				response.sendRedirect(nextJSP);
    			}
    			else
    			{
    				ITinhphucapList obj = new TinhphucapList();
    				obj.setUserId(userId);
    				obj.init("");
    				
    				session.setAttribute("obj", obj);
    				session.setAttribute("userId", userId);
		    		
    				response.sendRedirect("/AHF/pages/Center/TinhPhuCap.jsp");		   	
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
				nextJSP = "/AHF/pages/Center/TinhPhuCapNew.jsp";
			}
			else{
				nextJSP = "/AHF/pages/Center/TinhPhuCapUpdate.jsp";   						
			}
			response.sendRedirect(nextJSP);
		}		
	}

	private String getGSBH_Selected(ITinhphucapDetail detail, List<ITinhphucapDetail> tcDetailList) 
	{
		String gsbhIds = "";
		
		for(int i = 0; i < tcDetailList.size(); i++)
		{
			ITinhphucapDetail ttnDetai = tcDetailList.get(i);
			
			if( ( ttnDetai.getSTT() != detail.getSTT() ) && ttnDetai.getChucvu().equals("SS") )
			{
				gsbhIds += ttnDetai.getNhanvienIds() + ",";
			}
		}
		
		if(gsbhIds.trim().length() > 0)
		{
			gsbhIds = gsbhIds.substring(0, gsbhIds.length() - 1);
		}
		
		return gsbhIds;
	}
	
	private String getDDKD_Selected(ITinhphucapDetail detail, List<ITinhphucapDetail> tcDetailList) 
	{
		String ddkdIds = "";
		
		for(int i = 0; i < tcDetailList.size(); i++)
		{
			ITinhphucapDetail ttnDetai = tcDetailList.get(i);
			
			if( ( ttnDetai.getSTT() != detail.getSTT() ) && ttnDetai.getChucvu().equals("SR") )
			{
				ddkdIds += ttnDetai.getNhanvienIds() + ",";
			}
		}
		
		if(ddkdIds.trim().length() > 0)
		{
			ddkdIds = ddkdIds.substring(0, ddkdIds.length() - 1);
		}
		
		return ddkdIds;
	}

	

}
