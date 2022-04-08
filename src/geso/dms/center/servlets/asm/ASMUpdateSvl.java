package geso.dms.center.servlets.asm;

import geso.dms.center.beans.asm.IAsm;
import geso.dms.center.beans.asm.IAsmList;
import geso.dms.center.beans.asm.imp.Asm;
import geso.dms.center.beans.asm.imp.AsmList;
import geso.dms.center.util.Utility;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class ASMUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ASMUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utility util = new Utility();

		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");

	    HttpSession session = request.getSession();

	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);

		IAsm asmBean = new Asm();
		
	    System.out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    System.out.println(action);
	    
	    String Id = util.getId(querystring);

	    asmBean.setId(Id);
	    
	    asmBean.init_Update();
		// Save data into session
		session.setAttribute("asmBean", asmBean);
	
		String nextJSP = "/AHF/pages/Center/ASMUpdate.jsp";
		if(querystring.indexOf("display") > 0)
        	nextJSP = "/AHF/pages/Center/ASMDisplay.jsp";
		response.sendRedirect(nextJSP);
			
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utility util = new Utility();
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");

	    HttpSession session = request.getSession();
	    IAsm asmBean = new Asm();
	    
	    String Id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Id")));
	    if(Id == null) Id = "";
	    asmBean.setId(Id);

	    String ten = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("asmTen")));
	    if(ten == null) ten = "";
	    asmBean.setTen(ten);
	    	    
	    String ma = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("asmMa")));
	    if(ma == null) ma = "";
	    asmBean.setMa(ma);
	    	    
	    String kbhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kbhId")));
	    if(kbhId == null) kbhId = "";
	    asmBean.setKbhId(kbhId);
	    
	    String dvkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")));
	    if(dvkdId == null) dvkdId = "";
	    asmBean.setDvkdId(dvkdId);
	    
	    String diachi = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("DiaChi")));	    
	    if(diachi == null) diachi = "";
	    asmBean.setDiachi(diachi);

	    String email = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Email")));	    
	    if(email == null) email = "";
	    asmBean.setEmail(email);

	    String dienthoai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("DienThoai")));	    
	    if(dienthoai == null) dienthoai = "";
	    asmBean.setDienthoai(dienthoai);

	    String[] kvId = request.getParameterValues("kvId");
	    asmBean.setKvId(kvId);
	    
	    String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("TrangThai")));
	    if(trangthai == null) trangthai = "0";
	    else trangthai = "1";
	    asmBean.setTrangthai(trangthai);
	    
	    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    if(userId == null) userId = "";    
	    asmBean.setUserId(userId);
	    
	    String ttppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("TTPP")));
	    if(ttppId == null) ttppId = "";    
	    asmBean.setTtppId(ttppId);
	    
	    String cmnd = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("cmnd")));
		if (cmnd == null)
			cmnd = "";
		asmBean.setCmnd(cmnd);
		
		String ngaysinh = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaysinh")));
		if (ngaysinh == null)
			ngaysinh = "";
		asmBean.setNgaysinh(ngaysinh);
		String quequan = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("quequan")));
		if (quequan == null)
			quequan = "";
		asmBean.setQuequan(quequan);
		String ngaybatdau = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaybatdau")));
		if (ngaybatdau == null)
			ngaybatdau = "";
		asmBean.setNgaybatdau(ngaybatdau);
		
		String ngayketthuc = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngayketthuc")));
		if (ngayketthuc == null)
			ngayketthuc = "";
		asmBean.setNgayketthuc(ngayketthuc);
		
		
		String bmId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("bmId")));
		if (bmId == null)
			bmId = "";
		asmBean.setBmId(bmId);
		
		String thuviec = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thuviec")));
		if (thuviec == null || thuviec.trim().length()==0)
			thuviec = "0";
		else
			thuviec="1";
		asmBean.setThuviec(thuviec);
	    String action = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")));
	    if(action == null) action = ""; 
	    
	    if(action.equals("save")){
	    	if(asmBean.Save(request))
	    	{
	    	    IAsmList obj = new AsmList();
	    	    obj.setUserId(userId);
	    	    obj.init();

	    	    session.setAttribute("obj", obj);
	    	
	    		String nextJSP = "/AHF/pages/Center/ASM.jsp";
	    		response.sendRedirect(nextJSP);	    	
	    		return;
	    	}else
	    	{

	    		
	    		
	    		asmBean.init_New();
	    		// Save data into session
	    		session.setAttribute("asmBean", asmBean);
	    		
	    		String nextJSP = "/AHF/pages/Center/ASMNew.jsp";
	    		if(Id.length() > 0)
	    			nextJSP = "/AHF/pages/Center/ASMUpdate.jsp";
	    		response.sendRedirect(nextJSP);
	    		return;
	    		
	    	}
	    }
	    
	    asmBean.init_New();
		session.setAttribute("asmBean", asmBean);	
		String nextJSP = "/AHF/pages/Center/ASMNew.jsp";
		if(Id.length() > 0)
			nextJSP = "/AHF/pages/Center/ASMUpdate.jsp";
		response.sendRedirect(nextJSP);
		
	}
}
