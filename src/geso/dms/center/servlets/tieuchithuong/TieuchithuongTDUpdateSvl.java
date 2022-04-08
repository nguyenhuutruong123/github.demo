package geso.dms.center.servlets.tieuchithuong;

import geso.dms.center.beans.tieuchithuong.ITieuchithuongTD;
import geso.dms.center.beans.tieuchithuong.ITieuchithuongTDList;
import geso.dms.center.beans.tieuchithuong.imp.TieuchithuongTD;
import geso.dms.center.beans.tieuchithuong.imp.TieuchithuongTDList;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TieuchithuongTDUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
	
    public TieuchithuongTDUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		ITieuchithuongTD tctskuBean;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    	    
	    String id = util.getId(querystring);
	   
	    tctskuBean = new TieuchithuongTD(id);
	    tctskuBean.setId(id);
	    tctskuBean.setUserId(userId);
	    
        tctskuBean.init();
        session.setAttribute("tctskuBean", tctskuBean);
        
        String nextJSP = "/AHF/pages/Center/TieuChiThuongTDUpdate.jsp";
        if(querystring.indexOf("display") >= 0)
        	nextJSP = "/AHF/pages/Center/TieuchithuongTDDisplay.jsp";
        
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		ITieuchithuongTD tctskuBean;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		this.out = response.getWriter();
		Utility util = new Utility();
		
	   	String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
	    if(id == null){  	
	    	tctskuBean = new TieuchithuongTD("");
	    }else{
	    	tctskuBean = new TieuchithuongTD(id);
	    }
	    
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		tctskuBean.setUserId(userId);
		
		String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
		if (diengiai == null)
			diengiai = "";
		tctskuBean.setDiengiai(diengiai);
		
		String scheme = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("scheme")));
		if (scheme == null)
			scheme = "";
		tctskuBean.setScheme(scheme);
		
		String thang = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thang")));
		if (thang == null)
			thang = "";
		tctskuBean.setThang(thang);
		
		String nam = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nam")));
		if (nam == null)
			nam = "";
		tctskuBean.setNam(nam);
		
		String mucVuot = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("mucVuot")));
		if (mucVuot == null)
			mucVuot = "";
		tctskuBean.setMucvuot(mucVuot);
		
		String chietkhauMucVuot = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chietkhauMucVuot")));
		if (chietkhauMucVuot == null)
			chietkhauMucVuot = "";
		tctskuBean.setChietkhauMucvuot(chietkhauMucVuot);
		
		String donviMucVuot = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("donviMucVuot")));
		if (donviMucVuot == null)
			donviMucVuot = "";
		tctskuBean.setDonviMucvuot(donviMucVuot);
		
		String[] spIds = request.getParameterValues("spIds");
		
		String[] dieukien = request.getParameterValues("dieukien");
		String[] donviDIEUKIEN = request.getParameterValues("donviDIEUKIEN");
		
		String[] quydoi = request.getParameterValues("quydoi");
		String[] donviQUYDOI = request.getParameterValues("donviQUYDOI");
		
		if(spIds != null)
		{
			Hashtable<String, String> htdieukien = new Hashtable<String, String>();
			Hashtable<String, String> htquydoi = new Hashtable<String, String>();
			
			String spId = "";
			for(int i = 0; i < spIds.length; i++)
			{
				spId += spIds[i] + ",";
				
				if(dieukien[i].trim().length() > 0 && donviDIEUKIEN[i].trim().length() > 0)
					htdieukien.put(spIds[i], dieukien[i].trim() + "__" + donviDIEUKIEN[i].trim());
				if(quydoi[i].trim().length() > 0 && donviQUYDOI[i].trim().length() > 0)
					htquydoi.put(spIds[i], quydoi[i].trim() + "__" + donviQUYDOI[i].trim());
			}
			
			if(spId.trim().length() > 0)
			{
				spId = spId.substring(0, spId.length() - 1);
				tctskuBean.setSpIds(spId);
				
				tctskuBean.setDieukien(htdieukien);
				tctskuBean.setQuydoi(htquydoi);
			}
		}
		
		String[] nppIds = request.getParameterValues("nppIds");
		if(nppIds != null)
		{
			String nppId = "";
			for(int i = 0; i < nppIds.length; i++)
			{
				nppId += nppIds[i] + ",";
			}
			
			if(nppId.trim().length() > 0)
			{
				nppId = nppId.substring(0, nppId.length() - 1);
				tctskuBean.setNppIds(nppId);
			}
		}
		
		//Muc 1 thang
    	String[] diengiaiMuc = request.getParameterValues("diengiaiMuc");
    	tctskuBean.setDiengiaiMuc(diengiaiMuc);
    	
		String[] tumuc = request.getParameterValues("tumuc");
		tctskuBean.setTumuc(tumuc);
		
		String[] denmuc = request.getParameterValues("denmuc");
		tctskuBean.setDenmuc(denmuc);
		
		String[] thuongSR = request.getParameterValues("chietkhau");
		tctskuBean.setThuongSR(thuongSR);
		
		String[] thuongTDSR = request.getParameterValues("donvi");
		tctskuBean.setThuongTDSR(thuongTDSR);
		
		/*String[] thuongSS = request.getParameterValues("thuongSS");
		tctskuBean.setThuongSS(thuongSS);
		
		String[] thuongTDSS = request.getParameterValues("thuongTDSS");
		tctskuBean.setThuongTDSS(thuongTDSS);
		
		String[] thuongASM = request.getParameterValues("thuongASM");
		tctskuBean.setThuongASM(thuongASM);
		
		String[] thuongTDASM = request.getParameterValues("thuongTDASM");
		tctskuBean.setThuongTDASM(thuongTDASM);*/
		
		
		//Muc 3 thang
		String[] diengiaiMuc3 = request.getParameterValues("diengiaiMuc3");
    	tctskuBean.setDiengiaiMuc3(diengiaiMuc3);
    	
		/*String[] tumuc3 = request.getParameterValues("tumuc3");
		tctskuBean.setTumuc3(tumuc3);
		
		String[] denmuc3 = request.getParameterValues("denmuc3");
		tctskuBean.setDenmuc3(denmuc3);*/
		
		String[] thuongSR3 = request.getParameterValues("thuongSR3");
		tctskuBean.setThuongSR3(thuongSR3);
		
		String[] thuongTDSR3 = request.getParameterValues("thuongTDSR3");
		tctskuBean.setThuongTDSR3(thuongTDSR3);
		
		String[] thuongSS3 = request.getParameterValues("thuongSS3");
		tctskuBean.setThuongSS3(thuongSS3);
		
		String[] thuongTDSS3 = request.getParameterValues("thuongTDSS3");
		tctskuBean.setThuongTDSS3(thuongTDSS3);
		
		String[] thuongASM3 = request.getParameterValues("thuongASM3");
		tctskuBean.setThuongASM3(thuongASM3);
		
		String[] thuongTDASM3 = request.getParameterValues("thuongTDASM3");
		tctskuBean.setThuongTDASM3(thuongTDASM3);
		
		
		//Tra san pham
		String hinhthucTra = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("hinhthuctra"));
		tctskuBean.setHinhthuctra(hinhthucTra);
		
		String[] maspTra = request.getParameterValues("maspTra");
		tctskuBean.setMaspTra(maspTra);
		
		String[] tenspTra = request.getParameterValues("tenspTra");
		tctskuBean.setTenspTra(tenspTra);
		
		String[] soluongspTra = request.getParameterValues("soluongspTra");
		tctskuBean.setSoluongspTra(soluongspTra);
		
		String[] dongiaspTra = request.getParameterValues("dongiaspTra");
		tctskuBean.setDongiaspTra(dongiaspTra);
		
		//VUNG 
		String vungId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"));
		if(vungId == null)
			vungId = "";
		tctskuBean.setVungIds(vungId);
		
		String kvId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId"));
		if(kvId == null)
			kvId = "";
		tctskuBean.setKhuvucIds(kvId);
		
		String doanhsothung = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("doanhsothung"));
		if(doanhsothung == null)
			doanhsothung = "0";
		tctskuBean.setDoanhsotheoThung(doanhsothung);
		
		String httt = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("httt"));
		if(httt == null)
			httt = "0";
		tctskuBean.setHTTT(httt);
		
		String ptTRACK = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ptTRACK"));
		if(ptTRACK == null)
			ptTRACK = "0";
		tctskuBean.setPT_TRACK(ptTRACK);
		
 		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
 		System.out.println("Action la: " + action);
 		
 		if(action.equals("save"))
 		{    
    		if (id == null )
    		{
    			if (!tctskuBean.createTctSKU())
    			{
    		    	tctskuBean.setUserId(userId);
    		    	
    		    	tctskuBean.createRs();
    		    	session.setAttribute("userId", userId);
    				session.setAttribute("tctskuBean", tctskuBean);
    				
    				String nextJSP = "/AHF/pages/Center/TieuChiThuongTDNew.jsp";
    				response.sendRedirect(nextJSP);
    			}
    			else
    			{
    				ITieuchithuongTDList obj = new TieuchithuongTDList();
				    obj.setUserId(userId);
				    
				    obj.init("");
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/AHF/pages/Center/TieuChiThuongTD.jsp";
					response.sendRedirect(nextJSP);
    			}	
    		}
    		else
    		{
    			if (!(tctskuBean.updateTctSKU()))
    			{			
    		    	tctskuBean.setUserId(userId);
    		    	
    		    	tctskuBean.createRs();
    		    	session.setAttribute("userId", userId);
    				session.setAttribute("tctskuBean", tctskuBean);
    				
    				String nextJSP = "/AHF/pages/Center/TieuChiThuongTDUpdate.jsp";
    				response.sendRedirect(nextJSP);
    			}
				else
				{
					ITieuchithuongTDList obj = new TieuchithuongTDList();
				    obj.setUserId(userId);
				    
				    obj.init("");
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/AHF/pages/Center/TieuChiThuongTD.jsp";
					response.sendRedirect(nextJSP);
				}
    		}
	    }
		else
		{		
			tctskuBean.createRs();
			session.setAttribute("userId", userId);
			session.setAttribute("tctskuBean", tctskuBean);
			
			String nextJSP;
			if (id == null){
				nextJSP = "/AHF/pages/Center/TieuChiThuongTDNew.jsp";
			}
			else{
				nextJSP = "/AHF/pages/Center/TieuChiThuongTDUpdate.jsp";   						
			}
			response.sendRedirect(nextJSP);
		}
	}
	
	public String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

}
