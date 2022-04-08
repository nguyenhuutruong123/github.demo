package geso.dms.center.servlets.dieukienkhuyenmai;

import geso.dms.center.beans.dieukienkhuyenmai.*;
import geso.dms.center.beans.dieukienkhuyenmai.imp.*;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class DkkhuyenmaiUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out; 
	
	
    public DkkhuyenmaiUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IDieukienkhuyenmai dkkmBean;
		HttpSession session = request.getSession();
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	  //--- check user
	    String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
		if (view == null) {
			view = "";
		}
		String param = "";
		if(view.trim().length() > 0) param ="&view="+view;
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "DieukienkhuyenmaiSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}
		 //--- check user
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    	    
	    String id = util.getId(querystring);  	
	    dkkmBean = new Dieukienkhuyenmai(id);
	    dkkmBean.setUserId(userId);
        dkkmBean.init();
        dkkmBean.createRS();
        System.out.println("123");
        
        session.setAttribute("dkkmBean", dkkmBean);
        String nextJSP = "/AHF/pages/Center/DieuKienKhuyenMaiUpdate.jsp";
        String action = util.getAction(querystring);
		if (action.equals("display")) {
			nextJSP = "/AHF/pages/Center/DieuKienKhuyenMaiDisplay.jsp";
		}
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IDieukienkhuyenmai dkkmBean;
//		this.out = response.getWriter();
		Utility util = new Utility();
		
		//--- check user
	    String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
		if (view == null) {
			view = "";
		}
		String param = "";
		if(view.trim().length() > 0) param ="&view="+view;
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "DieukienkhuyenmaiSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}
		 //--- check user
		
	    String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));	
	    if(id == null){  	
	    	dkkmBean = new Dieukienkhuyenmai("");
	    }else{
	    	dkkmBean = new Dieukienkhuyenmai(id);
	    }
	    	    
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		dkkmBean.setUserId(userId);	       
    			
		String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
		if (diengiai == null)
			diengiai = "";
		dkkmBean.setDiengiai(diengiai);
		
		String hinhthuc = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("option")));
		if (hinhthuc == null)
			hinhthuc = "";
		dkkmBean.setHinhthuc(hinhthuc);
		
		String tongluong = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tongluong")));
		if (tongluong == null)
			tongluong = "";
		dkkmBean.setTongluong(tongluong);
		
		String tongtien = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tongtien")));
		if (tongtien == null)
			tongtien = "";
		dkkmBean.setTongtien(tongtien);
		
		String type = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("type")));
		if (type == null)
			type = "";
		dkkmBean.setType(type);
		
		String nhomspId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhomsp")));
		if (nhomspId == null)
			nhomspId = "";
		dkkmBean.setNhomspId(nhomspId);
		
		String ngaysua = getDateTime();
    	dkkmBean.setNgaysua(ngaysua);
    	
    	String[] masp = request.getParameterValues("masp");
		String[] tensp = request.getParameterValues("tensp");
		String[] soluong = request.getParameterValues("soluong");
				
		Hashtable<String, Integer> sp_nhomSpIds = new Hashtable<String, Integer>();
		List<ISanpham> spSudunglist = new ArrayList<ISanpham>();
		
		if(nhomspId.length() > 0 )
		{
    		if(masp != null)
    		{
    			for(int i = 0; i < masp.length; i++)
    			{
    				if(soluong != null)
    				{
	    				if(soluong[i].length() > 0){
	    					int soluongparse =  Integer.parseInt(soluong[i]);
	    					System.out.println("so luong " + soluongparse);
	    					sp_nhomSpIds.put(masp[i], soluongparse);
	    				}
    				}
    			}
    		}
		}

		if(nhomspId.length() == 0 && type.length() > 0)
		{
			if(masp != null)
    		{
    			for(int i = 0; i < masp.length; i++)
    			{
    				if(masp[i].length() > 0)
    				{
						Sanpham sp = new Sanpham("", masp[i], tensp[i], soluong[i], "");
						spSudunglist.add(sp);
    				}
    			}
    		}
		}
		dkkmBean.setSp_nhomspIds(sp_nhomSpIds);
		dkkmBean.setSpSudungList(spSudunglist);
	
 		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
 		if(action.equals("save"))
 		{
    		if (id == null || id.trim().length()==0 )
    		{
    			if (!dkkmBean.CreateDkkm(masp, soluong)){
    		    	dkkmBean.setUserId(userId);
    		    	dkkmBean.createRS();
    		    	session.setAttribute("userId", userId);
    				session.setAttribute("dkkmBean", dkkmBean);
    				
    				String nextJSP = "/AHF/pages/Center/DieuKienKhuyenMaiNew.jsp";
    				response.sendRedirect(nextJSP);
    			}
    			else{
    				IDkkhuyenmaiList obj = new DkkhuyenmaiList();
    				obj.setUserId(userId);
    				obj.init("");
    				
				
    				session.setAttribute("obj", obj);
    				session.setAttribute("userId", userId);
		    		
    				response.sendRedirect("/AHF/pages/Center/DieuKienKhuyenMai.jsp");	    	
    			}		
    		}else{
    			if (!(dkkmBean.UpdateDkkm(masp, soluong))){			
    		    	dkkmBean.setUserId(userId);
    		    	dkkmBean.createRS();
    		    	
    		    	session.setAttribute("userId", userId);
    				session.setAttribute("dkkmBean", dkkmBean);
    				
    				String nextJSP = "/AHF/pages/Center/DieuKienKhuyenMaiUpdate.jsp";
    				response.sendRedirect(nextJSP);
    			}
    			else{
    				IDkkhuyenmaiList obj = new DkkhuyenmaiList();
    				obj.setUserId(userId);
    				obj.init("");
    				
				
    				session.setAttribute("obj", obj);

    				session.setAttribute("userId", userId);
		    		
    				response.sendRedirect("/AHF/pages/Center/DieuKienKhuyenMai.jsp");	    	
    			}
    		}
	    }
		else if (action.equals("excel")){
			OutputStream outstream = null;
			try
			{
				WorkbookSettings wbSettings = new WorkbookSettings();
				dkkmBean.init();
				
				wbSettings.setLocale(new Locale("en", "EN"));			
				response.setContentType("application/vnd.ms-excel");
			    response.setHeader("Content-Disposition", "attachment; filename=DieukienKhuyenmai.xls");
			    
			    outstream = response.getOutputStream();
				WritableWorkbook workbook = Workbook.createWorkbook(outstream, wbSettings);
			    
				workbook.createSheet("DKKM", 0);
				WritableSheet Sheet = workbook.getSheet(0);
				
				workbook.setColourRGB(Colour.RED, 0xff, 0, 0);
				
				this.CreateHeader(Sheet, dkkmBean);
				this.CreateContent(Sheet, dkkmBean);
				
				workbook.write();		
				workbook.close();
				return;
			}
			catch(jxl.JXLException ex)
			{
				System.out.print("Exception...");
			}
			finally
		    {
		     if (outstream != null)
		    	 outstream.close();
		    }

		}else
		{
			
			if(dkkmBean.getType().equals("2")){ 
				if(dkkmBean.getHinhthuc().trim().length()<=0){
					dkkmBean.setHinhthuc("1");
				}
			}
			dkkmBean.createRS();		
			if(id != null && nhomspId.length() == 0)
				dkkmBean.createDkkmSpList();
			session.setAttribute("userId", userId);
			session.setAttribute("dkkmBean", dkkmBean);
			String nextJSP;
			if (id == null || id.trim().length()==0){
				nextJSP = "/AHF/pages/Center/DieuKienKhuyenMaiNew.jsp";
			}
			else{
				nextJSP = "/AHF/pages/Center/DieuKienKhuyenMaiUpdate.jsp";   						
			}
			response.sendRedirect(nextJSP);
		}		
	}
	
	private void CreateHeader(WritableSheet ws, IDieukienkhuyenmai dkkmBean) throws WriteException
	{
		//Co dinh 8 row dau tien
		ws.getSettings().setVerticalFreeze(10);
	    ws.getSettings().setDefaultRowHeight(17*20);
	    
	    ws.setColumnView(0, 20);
	    ws.setColumnView(1, 50);
	    ws.setColumnView(2, 13);
	    ws.setColumnView(3, 13);
	    ws.setColumnView(4, 13);
	    ws.setColumnView(5, 15);
	    ws.setColumnView(7, 18);
	    ws.setColumnView(8, 18);
	    ws.setColumnView(9, 18);
	    ws.setColumnView(10, 18);
	    ws.setColumnView(11, 18);
	    
	    WritableFont wf = new WritableFont(WritableFont.ARIAL, 15 , WritableFont.BOLD);
	    wf.setColour(Colour.RED);
	    WritableCellFormat wcf = new WritableCellFormat(wf);
	    wcf.setAlignment(Alignment.LEFT);
	    wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
	   
	    Label title = new Label(0, 0, "Điều kiện khuyến mãi", wcf);
	    ws.addCell(title);
	    
	    WritableFont font = new WritableFont(WritableFont.ARIAL, WritableFont.DEFAULT_POINT_SIZE, WritableFont.NO_BOLD, false,UnderlineStyle.NO_UNDERLINE);
	    wcf = new WritableCellFormat(font);
	    wcf.setWrap(true);
	    
	    WritableFont wf2 = new WritableFont(WritableFont.ARIAL, WritableFont.DEFAULT_POINT_SIZE , WritableFont.BOLD);
	    WritableCellFormat wcf2 = new WritableCellFormat(wf2);
	    	    
	    Label l_ngaytao = new Label(0, 1, "Ngày tạo: ", wcf);
	    ws.addCell(l_ngaytao);	
	    
	    Label d_ngaytao = new Label(1, 1,dkkmBean.getNgaytao() , wcf2);
	    ws.addCell(d_ngaytao);	
	    
	    Label l_nguoitao = new Label(0, 2, "Người tạo: ", wcf);
	    ws.addCell(l_nguoitao);	
	    
	    Label d_nguoitao = new Label(1, 2, dkkmBean.getNguoitao(), wcf2);
	    ws.addCell(d_nguoitao);		    	    		    

	    Label l_madieukien = new Label(0, 4, "Mã điều kiện: ", wcf);
	    ws.addCell(l_madieukien);	
	    
	    Label d_madieukien = new Label(1, 4, dkkmBean.getId(), wcf2);
	    ws.addCell(d_madieukien);	
	    

	    Label l_diengiai = new Label(0, 6, "Diễn giải: ", wcf);
	    ws.addCell(l_diengiai);	
	    
	    Label d_diengiai = new Label(1, 6, dkkmBean.getDiengiai(), wcf2);
	    ws.addCell(d_diengiai);	
	    
	    Label l_loaidieukien = new Label(0, 7, "Loại điều kiện: ", wcf);
	    ws.addCell(l_loaidieukien);
	    
	    
	    Label d_loaidieukien;
	    if(dkkmBean.getType().equals("1"))
	    	d_loaidieukien = new Label(1, 7, "Bắt buộc nhập số lượng từng sản phẩm" , wcf2);
	    else
	    	d_loaidieukien = new Label(1, 7, "Bất kỳ trong" , wcf2);
	    ws.addCell(d_loaidieukien);	

	    
	    Label l_hinhthuc = new Label(0, 8, "Hình thức: ", wcf);
	    ws.addCell(l_hinhthuc);		    
	    
	    Label d_hinhthuc;
	    Label d_tong;
	    if(dkkmBean.getHinhthuc().equals("1")){
	    	d_hinhthuc = new Label(1, 8, "Nhập tổng lượng" , wcf2);
	    	d_tong = new Label(2, 8, dkkmBean.getTongluong() , wcf2);
		}else{
	    	d_hinhthuc = new Label(1, 8,"Nhập tổng tiền" , wcf2);
	    	d_tong = new Label(2, 8, dkkmBean.getTongtien() , wcf2);
	    }
	    ws.addCell(d_hinhthuc);	
	    ws.addCell(d_tong);	
	    
	    font = new WritableFont(WritableFont.ARIAL, WritableFont.DEFAULT_POINT_SIZE, WritableFont.BOLD, false);
	    wcf = new WritableCellFormat(font);
	    wcf.setWrap(true);
	    wcf.setAlignment(Alignment.CENTRE);
	    wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
	    wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
	    	    
	    Label masp = new Label(0, 10, "Mã sản phẩm", wcf);	    
	    ws.addCell(masp);	

	    Label tensp = new Label(1, 10, "Tên sản phẩm", wcf);	    
	    ws.addCell(tensp);	
	    
	    Label soluong = new Label(2, 10, "Số lượng", wcf);	   
	    ws.addCell(soluong);	 
	    
	}
	
	private void CreateContent(WritableSheet ws, IDieukienkhuyenmai dkkmBean) throws WriteException
	{
		WritableFont wf = new WritableFont(WritableFont.ARIAL,WritableFont.DEFAULT_POINT_SIZE, WritableFont.NO_BOLD);
	    WritableCellFormat wcf_left = new WritableCellFormat(wf);
	    wcf_left.setAlignment(Alignment.LEFT);
	    wcf_left.setBorder(Border.ALL, BorderLineStyle.THIN);
	    
	    WritableCellFormat wcf_center = new WritableCellFormat(wf);
	    wcf_center.setAlignment(Alignment.CENTRE);
	    wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN);
	    
	    WritableCellFormat wcf_right = new WritableCellFormat(wf);
	    wcf_right.setAlignment(Alignment.RIGHT);
	    wcf_right.setBorder(Border.ALL, BorderLineStyle.THIN);
	    
	    WritableCellFormat wcf_num = new WritableCellFormat(NumberFormats.THOUSANDS_INTEGER);
	    wcf_num.setAlignment(Alignment.RIGHT);
	    wcf_num.setBorder(Border.ALL, BorderLineStyle.THIN);
	    
	    WritableCellFormat cfi2 = new WritableCellFormat(NumberFormats.THOUSANDS_INTEGER);
	    cfi2.setAlignment(Alignment.CENTRE);
	    cfi2.setBorder(Border.ALL, BorderLineStyle.THIN);
	    		
		
		List<ISanpham> spSudungList = dkkmBean.getSpSudungList();
			
		int m = 11;
			
		for(int i = 0; i < spSudungList.size(); i++)
		{
			Sanpham sp = (Sanpham)spSudungList.get(i);
			
			Label masp = new Label(0, m,sp.getMasanpham(), wcf_left);
			ws.addCell(masp);

			Label tensp = new Label(1, m, sp.getTensanpham(), wcf_left);
			ws.addCell(tensp);
			
			Label soluong = new Label(2, m, sp.getSoluong(), wcf_left);
			ws.addCell(soluong);
		    
			m++;
		}

	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

}
