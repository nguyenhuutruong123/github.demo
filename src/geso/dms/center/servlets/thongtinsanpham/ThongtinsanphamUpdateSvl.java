package geso.dms.center.servlets.thongtinsanpham;

import geso.dms.center.beans.thongtinsanpham.IThongtinsanpham;
import geso.dms.center.beans.thongtinsanpham.IThongtinsanphamList;
import geso.dms.center.beans.thongtinsanpham.imp.Thongtinsanpham;
import geso.dms.center.beans.thongtinsanpham.imp.ThongtinsanphamList;
import geso.dms.center.util.Utility;




import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

 public class ThongtinsanphamUpdateSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   private PrintWriter out;
	public ThongtinsanphamUpdateSvl() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String id = util.getId(querystring); 
	    

	    IThongtinsanpham spBean = new Thongtinsanpham();
	    spBean.setId(id);
        spBean.setUserId(userId);
        if(querystring.equals("display"))
        	spBean.init2();
        else 
        	spBean.init();
        session.setAttribute("spBean", spBean);
        String nextJSP = "/AHF/pages/Center/ThongTinSanPhamUpdate.jsp";
        if(querystring.indexOf("display") > 0)
        	nextJSP = "/AHF/pages/Center/ThongTinSanPhamDisplay.jsp";
        response.sendRedirect(nextJSP);
	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		//PrintWriter out = response.getWriter();
		Utility util = new Utility();
	    String id =  util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
	    
      
	    ////system.out.print("I am here"+id);
	    
	    IThongtinsanpham spBean = new Thongtinsanpham();
	    if(id == null)
	    	id = "";
	    spBean.setId(id);
	    
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		spBean.setUserId(userId);
		
    	String masp = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("masp")));
		if (masp == null)
			masp = "";				
    	spBean.setMasp(masp);
    	
    	String maddt = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("maddt")));
		if (maddt == null)
			maddt = "";				
    	spBean.setMaddt(maddt);
    	
    	String matat = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("matat")));
    	if(matat ==  null)
    		matat = "";
    	spBean.setMaTat(matat);
    	
		String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));
    	if (trangthai == null)
    		trangthai = "0";
    	else
    		trangthai = "1";
    	spBean.setTrangthai(trangthai);
   	
		String tensp = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tensp")));
		if (tensp == null)
			tensp = "";				
		spBean.setTen(tensp);
		

		String tenviettat = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tenviettat")));
		if (tenviettat == null)
			tenviettat = "";				
		spBean.setTenVietTat(tenviettat);
		
		String isTrongtam  = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("isTrongtam")));
		if (isTrongtam == null || isTrongtam.trim().length() <= 0)
			isTrongtam = "0";
		spBean.setIsTrongtam(isTrongtam);
		
		String dvdlId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvdlId")));
		if (dvdlId == null)
			dvdlId = "";
		spBean.setDvdlId(dvdlId);
		
		String dvkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")));
		if (dvkdId == null)
			dvkdId = "";
		spBean.setDvkdId(dvkdId);
//		//system.out.print(dvkdId +"dvkdId;");
		
		//nganhhangId
		String nganhhangId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nganhhangId")));
		if (nganhhangId == null)
			nganhhangId = "";
		spBean.setNganhHangId(nganhhangId);
		
		String nhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhId")));		
		if (nhId == null)
			nhId = "";
		spBean.setNhId(nhId);
		
		//system.out.println("nganhangid: "+nganhhangId+"--nhanhang: "+nhId);
		
		String clId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("clId")));		
		if (clId == null)
			clId = "";
		spBean.setClId(clId);
//		//system.out.print(clId +"clId;");
		
//		String giablc = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("giablc").replaceAll(",", "")));
//		if (giablc == null)
//			giablc = "";
//		spBean.setGiablc(giablc);
		
		String kl = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kl")));
		if (kl == null)
			kl = "";
		spBean.setKL(kl);
		
	
		
		String tt = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tt")));
		if (tt == null)
			tt = "";
		spBean.setTT(tt);
		

		
		String ngaysua = getDateTime();
    	spBean.setNgaysua(ngaysua);
		
		String nguoisua = userId;
		spBean.setNguoisua(nguoisua);
    	
		String[] nspIds = request.getParameterValues("nspIds");
		if (!(nspIds == null))
			spBean.setNspIds(nspIds);
		
		String[] sl1 = request.getParameterValues("sl1");		
		
		String[] dvdl1 = request.getParameterValues("dvdl1");
		
		String[] sl2 = request.getParameterValues("sl2");

		String[] dvdl2 = request.getParameterValues("dvdl2");

		if (!(sl1 == null))
			spBean.setSl1(sl1);
		
		if (!(dvdl1 == null))
			spBean.setDvdl1(dvdl1);
		
		if (!(sl2 == null))
			spBean.setSl2(sl2);
		
		if (!(dvdl2 == null))
			spBean.setDvdl2(dvdl2);
		
		
		String type = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("type")));
    	if (type == null)
    		type = "0";
    	else
    		type = "1";
    	spBean.setType(type);
		
    	String[] spIds = request.getParameterValues("spIds");
    	if(spIds != null)
    	{
    		spBean.setSpIds(spIds);
    	}
		boolean error = false;
				
	/*	if (giablc.trim().length()== 0){
			spBean.setMessage("Vui long nhap gia ban le chuan");
			error = true;
		}

		if(!type.equals("1"))
		{
			if (clId.trim().length()== 0){
				spBean.setMessage("Vui lòng nhập chủng loại cho sản phẩm ");
				error = true;
			}
		}
	
 		if (nhId.trim().length()== 0){
 			spBean.setMessage("Vui lòng nhập nhãn hàng của sản phẩm ");
 			error = true;
 		}	

 		if (dvkdId.trim().length()== 0){
 			spBean.setMessage("Vui lòng nhập  đơn vị kinh doanh của sản phẩm");
 			error = true;
 		}	

 		if (dvdlId.trim().length()== 0){
 			spBean.setMessage("Vui lòng nhập vào đơn vị đo lường của sản phẩm");
 			error = true;
 		}	*/
 		

 		spBean.setLoaiSpId(null);
 		


 		if (tenviettat.trim().length()== 0){
 			spBean.setMessage("Vui lòng nhập tên viết tắt của sản phẩm");
 			error = true;
 		}	

 		if (tensp.trim().length()== 0){
 			spBean.setMessage("Vui lòng nhập tên của sản phẩm");
 			error = true;
 		}	

 		if (masp.trim().length()== 0){
 			spBean.setMessage("Vui lòng nhập vào mã của sản phẩm");
 			error = true;
 		}	
 		
 		
 		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    //system.out.println("Action: " + action);
	    if(error){
	    	//system.out.print("error = true");
	    }
	    
	    spBean.CreateRS();
	    //spBean.init();
		spBean.setUserId(userId);
		session.setAttribute("userId", userId);
		session.setAttribute("spBean", spBean);
		
		String nextJSP;
		 
	    	if(action.equals("save") && !error)
	    	{
	    			if (!(spBean.CreateSp())){				

	    		    	nextJSP = "/AHF/pages/Center/ThongTinSanPhamNew.jsp";
	    				response.sendRedirect(nextJSP);
	    			}else{
	    				IThongtinsanphamList obj = new ThongtinsanphamList();
	    				obj.setUserId(userId);
	    				obj.init();
	    				
	    				//system.out.print("\nDa init sau khi tao moi xonggggg \n\n");
	    				
						session.setAttribute("obj", obj);

	    				session.setAttribute("userId", userId);
			    		
	    				response.sendRedirect("/AHF/pages/Center/ThongTinSanPham.jsp");	    	
	    			}
				
	    	  }
	  	    else if(action.equals("update") && !error)
	    		{
	    			if (!(spBean.UpdateSp()))
	    			{			
	    				nextJSP = "/AHF/pages/Center/ThongTinSanPhamUpdate.jsp";
	    				////system.out.print("Loi cap nhat...");
	    				response.sendRedirect(nextJSP);
	    			}
	    			else
	    			{
	    				IThongtinsanphamList obj = new ThongtinsanphamList();
	    				obj.setUserId(userId);
	    				obj.init();
						session.setAttribute("obj", obj);

	    				session.setAttribute("userId", userId);
			    		
	    				response.sendRedirect("/AHF/pages/Center/ThongTinSanPham.jsp");	
	    				
	    			}
	    		}
	  	    else if(action.equals("abc") || action.equals("save"))
		    	{
		    			nextJSP = "/AHF/pages/Center/ThongTinSanPhamNew.jsp";
			 		    response.sendRedirect(nextJSP);
		    	}
	           else 
	           {
		    		nextJSP = "/AHF/pages/Center/ThongTinSanPhamUpdate.jsp";   					
					response.sendRedirect(nextJSP);
	     		}
		    
	}
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
}