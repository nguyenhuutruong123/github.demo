package geso.dms.center.servlets.khachhang;

import geso.dms.center.beans.khachhang.*;
import geso.dms.center.beans.khachhang.imp.*;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class KhachhangUpdateTTSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	
    public KhachhangUpdateTTSvl() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen"); 
		System.out.println("userId : "+userId+" & userTen : "+userTen);
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
		IKhachhang khBean;
		PrintWriter out; 
						
		out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String id = util.getId(querystring);  	

	    khBean = new Khachhang(id);
        khBean.setUserId(userId);
        khBean.init();
         session.setAttribute("khBean", khBean);
        String nextJSP = "/AHF/pages/Center/KhachHangUpdate.jsp";
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
		
		IKhachhang khBean;
		//PrintWriter out; 
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		
		//out = response.getWriter();
		Utility util = new Utility();
	    String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
	    if(id == null){  	
	    	khBean = new Khachhang("");
	    }else{
	    	khBean = new Khachhang(id);
	    }
	    String isMt =    util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ismt")));
	    khBean.setIsMT(isMt);
		userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		khBean.setUserId(userId);
		
		String maddt = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("maddt")));
		if (maddt == null)
			maddt = "0";
		khBean.setMaDDT(maddt);
		
    	String khTen = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khTen")));
		if (khTen == null)
			khTen = "";				
    	khBean.setTen(khTen);
    	
    	String email = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("email")));
		if (email == null)
			email = "";				
    	khBean.setEmail(email);
    	
    	String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
		if (nppId == null)
			nppId = "";
		khBean.setNppId(nppId);
		System.out.println("Npp la; " + nppId);
		
    	String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));
    	if (trangthai == null)
    		trangthai = "0";
    	else
    		trangthai = "1";
    	khBean.setTrangthai(trangthai);
    	
    	String diachi = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diachi"));
		if (diachi == null)
			diachi = "";
		khBean.setDiachi(diachi);
		
    	String tpId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tpId"));
		if (tpId == null)
			tpId = "";
		khBean.setTpId(tpId);

    	String qhId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("qhId"));
		if (qhId == null)
			qhId = "";
		khBean.setQhId(qhId);
		
//		String ghcnId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ghcnTen"));
//		if (ghcnId.length()==0)
//			ghcnId = "";
//		khBean.setGhcnId(ghcnId);
		
		String mckId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("mckTen"));
		if (mckId == null)
			mckId = "";
		khBean.setMckId(mckId);
		
		//System.out.println("chietkhau :" + mckId);
		String dienthoai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dienthoai"));
		if(dienthoai == null)
			dienthoai = "";
		khBean.setSodienthoai(dienthoai);
		
		String masothue = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("masothue"));
		if(masothue == null)
			masothue = "";
		khBean.setMasothue(masothue);
		
		String kbhId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kbhTen"));
		if (kbhId == null)
			kbhId = "";
		khBean.setKbhId(kbhId);
	
		String nvgnId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nvgnTen"));
		if (nvgnId == null)
			nvgnId = "";
		khBean.setNvgnId(nvgnId);
		
		String lchId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("lchTen"));
		if (lchId == null)
			lchId = "";
		khBean.setLchId(lchId);
		
		String hchId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("hchTen"));
		if (hchId == null)
			hchId = "";
		khBean.setHchId(hchId);
		
		String vtchId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vtchTen"));
		if (vtchId == null)
			vtchId = "";
		khBean.setVtId(vtchId);
		
		String dtch = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dtch"));
		if (dtch == null)
			dtch = "";
		khBean.setDientichch(dtch);
		
		String nguoidaidien = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nguoidaidien"));
		if (nguoidaidien == null)
			nguoidaidien = "";
		khBean.setNguoidaidien(nguoidaidien);
		
		String dcxuathd = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dcxuathd"));
		if (dcxuathd == null)
			dcxuathd = "";
		khBean.setDcxuathd(dcxuathd);
		
		String dcgiaohang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dcgiaohang"));
		if (dcgiaohang == null)
			dcgiaohang = "";
		khBean.setDcgiaohang(dcgiaohang);
		
		String dcgiaohang2 = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dcgiaohang2"));
		if (dcgiaohang2 == null)
			dcgiaohang2 = "";
		khBean.setDcgiaohang2(dcgiaohang2);
		
		String khodathang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khodathang"));
		if (khodathang == null)
			khodathang = "";
		khBean.setKhodathang(khodathang);
		
		String kvId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kvId"));
		if (kvId == null)
			kvId = "";
		khBean.setKvId(kvId);
		
		String tansuatdh = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tansuatdh"));
		if (tansuatdh == null)
			tansuatdh = "";
		khBean.setTansuatdh(tansuatdh);
		
		String songayno = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("songayno"));
		if (songayno == null)
			songayno = "";
		khBean.setSongayno(songayno);
		
		String sotienno = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sotienno"));
		if (sotienno == null)
			sotienno = "";
		khBean.setSotienno(sotienno);
		
		String asm = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("asm"));
		if (asm == null)
			asm = "";
		khBean.setASMId(asm);
		System.out.println("asmid: "+asm);
		String[] nkh_khIds = request.getParameterValues("nkh_khList");
		khBean.setNkh_KhIds(nkh_khIds);
		
		String ngaysua = getDateTime();
    	khBean.setNgaysua(ngaysua);
		
		boolean error = false;
		if(isMt.equals("0"))
		if (kbhId.equals("")){
			khBean.setMessage("Vui Long Chon Kenh Ban Hang");
			error = true;
		}
		if(isMt.equals("0"))
		if (nppId.equals("")){
			khBean.setMessage("Vui Long Chon Nhà phân phối");
			error = true;
		}


		if (diachi.trim().length()== 0){
			khBean.setMessage("Vui Long Nhap Dia Chi");
			error = true;
		}
				
		if (khTen.trim().length()== 0){
			khBean.setMessage("Vui Long Nhap Ten Khach Hang");
			error = true;
		}
		if(isMt.equals("0"))
		if(qhId.length() <= 0)
		{
			khBean.setMessage("Vui Lòng chọn quận huyện !");
			error = true;
		}
		
		if(tpId.length() <= 0)
		{
			khBean.setMessage("Vui Lòng tỉnh thành !");
			error = true;
		}

 		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (!error){
	    	if(action.equals("save"))
	    	{	
	    		if ( id == null){
	    			if (!(khBean.CreateKh())){	
	    				khBean.createRS();
	    				session.setAttribute("khBean", khBean);			
	    				String nextJSP = "/AHF/pages/Center/KhachHangNew.jsp";
	    				response.sendRedirect(nextJSP);
	    			}else{
	    				IKhachhangList obj = new KhachhangList();
	    				obj.setUserId(userId);
	    				obj.setIsMT(isMt);
	    				obj.init("");
	    				session.setAttribute("obj", obj);
						
	    				String nextJSP = "/AHF/pages/Center/KhachHang.jsp";
	    				response.sendRedirect(nextJSP);			    			    									
	    			}
				
	    		}else{
	    			if (!(khBean.UpdateKh()))
	    			{
	    				
	    				khBean.init();
	    				
	    				session.setAttribute("khBean", khBean);
	    				String nextJSP = "/AHF/pages/Center/KhachHangUpdate.jsp";
	    				response.sendRedirect(nextJSP);
	    			}
	    			else{
	    				IKhachhangList obj = new KhachhangList();
	    				obj.setIsMT(isMt);
	    				obj.setUserId(userId);
	    				obj.init("");
	    				session.setAttribute("obj", obj);
	    				
	    				String nextJSP = "/AHF/pages/Center/KhachHang.jsp";
	    				response.sendRedirect(nextJSP);			    			    									
	    			}
	    		}
	    	}
	    	else
	    	{
	    		khBean.setUserId(userId);
	    		khBean.createRS();
			
	    		session.setAttribute("khBean", khBean);
			
	    		String nextJSP;
	    		if (id == null){			
	    			nextJSP = "/AHF/pages/Center/KhachHangNew.jsp";
	    		}else{
	    			nextJSP = "/AHF/pages/Center/KhachHangUpdate.jsp";   						
	    		}
	    		response.sendRedirect(nextJSP);		
	    	}
	    }
	    else{
    		khBean.setUserId(userId);
    		khBean.createRS();
		
    		session.setAttribute("khBean", khBean);
		
    		String nextJSP;
    		if (id == null){			
    			nextJSP = "/AHF/pages/Center/KhachHangNew.jsp";
    		}else{
    			nextJSP = "/AHF/pages/Center/KhachHangUpdate.jsp";   						
    		}
    		response.sendRedirect(nextJSP);		
	    	
	    }
		}
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

}
