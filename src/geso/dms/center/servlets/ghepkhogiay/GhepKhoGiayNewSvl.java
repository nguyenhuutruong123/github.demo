package geso.dms.center.servlets.ghepkhogiay;

import geso.dms.center.beans.ghepkhogiay.KeHoachList;
import geso.dms.center.beans.ghepkhogiay.KeHoachSanXuatN;
import geso.dms.center.beans.ghepkhogiay.KichBanN;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

public class GhepKhoGiayNewSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIRECTORY = "C:\\upload\\";
	
    public GhepKhoGiayNewSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("dmkfeikfkdfndk");
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		Utility cutil =  (Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
			KeHoachSanXuatN obj;
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
		    
		    System.out.println("action doget: " + action);
	    	
		    session.setAttribute("action", action);
	   	    
		    obj = new KeHoachSanXuatN();
		    obj.setNguoiTao(userId);
		    obj.setNguoiSua(userId);
		    String id = util.getId(querystring); 
			System.out.println("id kich ban: " + id);
	    	obj.setId(id);
		    obj.init();
		    String nextJSP = "/AHF/pages/Center/GhepKhoGiayNew.jsp";
		    if (action.trim().equals("chot") == true)
		    {
		    	obj.chot();
		    	obj.DBClose();
		    	KeHoachList obj1 = new KeHoachList();
		    	obj1.init();
			    session.setAttribute("obj", obj1);
				nextJSP = "/AHF/pages/Center/GhepKhoGiay.jsp";
		    }else if (action.trim().equals("delete") == true)
		    {
		    	obj.delete();
		    	obj.DBClose();
		    	KeHoachList obj1 = new KeHoachList();
		    	obj1.init();
			    session.setAttribute("obj", obj1);
				nextJSP = "/AHF/pages/Center/GhepKhoGiay.jsp";
		    }else
		    {
				session.setAttribute("obj", obj);
				nextJSP = "/AHF/pages/Center/GhepKhoGiayNew.jsp";
		    }
			response.sendRedirect(nextJSP);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId;
		KeHoachSanXuatN obj = new KeHoachSanXuatN();
	    Utility util = new Utility();
		HttpSession session = request.getSession();
		//start upload
		String contentType = request.getContentType();
		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
		{
			MultipartRequest multi = new MultipartRequest(request, UPLOAD_DIRECTORY, 20000000, "UTF-8");
			userId = util.antiSQLInspection(multi.getParameter("userId"));
			session.setAttribute("userId", userId);
			String userTen = (String) session.getAttribute("userTen");
			session.setAttribute("userTen", userTen);
			String action = util.antiSQLInspection(multi.getParameter("action"));
//			obj.setUserId(userId);
			int id = Integer.parseInt(util.antiSQLInspection(multi.getParameter("id")) == null || util.antiSQLInspection(multi.getParameter("id")).equals("") ? "0" : util.antiSQLInspection(multi.getParameter("id")));	

			Enumeration files = multi.getFileNames();
			String filename = "";
			while (files.hasMoreElements())
			{
				String name = (String) files.nextElement();
				filename = multi.getFilesystemName(name);
			}
			
//			ArrayList<ArrayList<String>> arr = new ArrayList<ArrayList<String>>();
			String msg = "";
			if (filename != null && filename.length() > 0)
			{
				obj.importFromExcell(UPLOAD_DIRECTORY + filename);
			}
		
			obj.setMsg(msg);
			session.setAttribute("obj", obj);			
			String nextJSP = "/AHF/pages/Center/GhepKhoGiayNew.jsp";
			response.sendRedirect(nextJSP);
			return;
		}
		//end upload
		
	    userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = util.getAction(request.getQueryString());
	    	if (action == null)
	    		action = "";	    
    	}
	    
    	String tuNgay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays")));
		if (tuNgay == null)
		{
			tuNgay = "";
		}
		obj.setNguoiTao(userId);
	    obj.setNguoiSua(userId);
		
		
		obj.setTuNgay(tuNgay);
    	String denNgay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays")));
		if (denNgay == null)
		{
			denNgay = "";
		}
		obj.setDenNgay(denNgay);
		
		String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
		System.out.println("id kich ban: " + id);
    	obj.setId(id);
    	
		session.setAttribute("action", action);
		System.out.println("action: " + action);
		
	    if(action.equals("exportExcell"))
	    {
	    	obj.init();
	    	session.setAttribute("obj", obj);	//    	String nextJSP = "/AHF/pages/Center/GhepKhoGiay.jsp";
	    	response.setContentType("a pplication/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=KHSX_IP_" + getDateTime() + ".xlsm");
			OutputStream outstream = response.getOutputStream();
			String templateFileName = getServletContext().getInitParameter("path") + "\\KHSX_IP.xlsx";
			obj.xuatExcell(outstream, templateFileName);
	    }else if(action.equals("create"))
	    {
	    	obj.init();
	    	session.setAttribute("obj", obj);
	    	request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    session.setAttribute("obj", obj);
			String nextJSP = "/AHF/pages/Center/GhepKhoGiayNew.jsp";
			response.sendRedirect(nextJSP);
	    }else if(action.equals("save"))
	    {
	    	System.out.print("action save new");
	    	Hashtable<Float, KichBanN> toHopMay = (Hashtable<Float, KichBanN>) session.getAttribute("toHopMay");
	    	obj.getKeHoach().setToHopMay(toHopMay);
//	    	obj.init();
	    	obj.save();
	    	session.setAttribute("obj", obj);
	//    	String nextJSP = "/AHF/pages/Center/GhepKhoGiay.jsp";
	//		response.sendRedirect(nextJSP);
	    	request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    session.setAttribute("obj", obj);
			String nextJSP = "/AHF/pages/Center/GhepKhoGiayNew.jsp";
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