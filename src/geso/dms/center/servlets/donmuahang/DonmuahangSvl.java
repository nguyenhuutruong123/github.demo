package geso.dms.center.servlets.donmuahang;

import geso.dms.center.beans.donmuahang.imp.DonmuahangList;
import geso.dms.center.beans.donmuahang.imp.ERP_DonDatHang;
import geso.dms.center.beans.donmuahang.IDonmuahangList;
import geso.dms.center.beans.donmuahang.IERP_DonDatHang;
import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.Utility;
import geso.dms.center.db.sql.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Request;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import com.google.gson.annotations.Until;

 public class DonmuahangSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
	public DonmuahangSvl() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IDonmuahangList obj;
		String userId;

		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    out = response.getWriter();
	    
	    String querystring = request.getQueryString();
	    String action = util.getAction(querystring);
	   
	    System.out.println(" Da vao day roi dai ca :"+action);
	    
	    String ddhId = util.getId(querystring);
	    
	    userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    if(action.equals("unchot")){
	    	UnDuyet(ddhId);
	    }
	  
	    out.println(userId);
	    obj = new DonmuahangList();
	    obj.setUserId(userId);
	    obj.createDdhlist("");
		session.setAttribute("obj", obj);
	 	String BCXL="";
	 	BCXL= util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
	 	obj.setView(BCXL);
	 	
	 	if(BCXL==null)
	 			{
	 		BCXL="";
	 			}
		  if(BCXL.equals("BCxldh"))
		    {
			  String nextJSP = "/AHF/pages/Center/BCXulydonhang.jsp";
				response.sendRedirect(nextJSP);
		    }
		  else {
			  
			  String nextJSP = "/AHF/pages/Center/DonMuaHang.jsp";
				response.sendRedirect(nextJSP);
		  }
		
		
	}  	
	
	private void UnDuyet(String ddhId) {
		
		dbutils db = new dbutils();
		String query = "update  dondathang set trangthai='0' where pk_Seq='" + ddhId + "'";
		db.update(query);
		
		query = "delete  DONDATHANG_SP where DONDATHANG_FK in ( select pk_seq from DONDATHANG where DONDATHANG_FROM_FK = '" + ddhId + "' ) ";
		db.update(query);
		
		query = "delete  dondathang where DONDATHANG_FROM_FK = '" + ddhId + "'";
		db.update(query);
		
		db.shutDown();
	}

	private void SABODUYENT(String ddhId) {
		
		dbutils db = new dbutils();
		String query = "update  dondathang set trangthai='1' where pk_Seq='" + ddhId + "'";
		db.update(query);
		db.shutDown();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    /*PrintWriter out = response.getWriter();*/
	    IDonmuahangList obj;
	    String userId;
	    Utility util = new Utility();
	    
	    obj = new DonmuahangList();		
		HttpSession session = request.getSession();
	    userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String vungId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"));
	    if (vungId == null){
	    	vungId = "";
	    }  
	    obj.setVungId(vungId);
	    
	    String khuvucId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId"));
	    if (khuvucId == null){
	    	khuvucId = "";
	    }  
	    obj.setKvId(khuvucId);
	    
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = "";
	    }    
	    
	 
	    if(action.equals("BCxldh")){
	    	
	    	try{						
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BaoCaoXuLyDonHang.xlsm");						
		        OutputStream out1 = response.getOutputStream();
		        String query =  setQuery(userId,obj,request);
		        if(!CreatePivotTable(out1,obj,query)){
		        	response.setContentType("text/html");
		            PrintWriter writer = new PrintWriter(out1);
		            writer.print("Khong co du lieu trong thoi gian nay");
		            writer.close();
		        }

			}catch(Exception ex){
				ex.printStackTrace();		    	
		    	System.out.println("MSG "+obj.getMsg());
				 //obj.SetMsg(ex.getMessage());
		    	 obj.setUserId(userId);
		    	 obj.createDdhlist("");
		    	 session.setAttribute("obj", obj);
				 String nextJSP = "/AHF/pages/Center/BCXulydonhang.jsp";
				response.sendRedirect(nextJSP);	
			}
			return;
	    }

	    
	    if(action.equals("view") || action.equals("next") || action.equals("prev")){
	    	//obj = new DonmuahangList();
	    	obj.setUserId(userId);
	    	obj.setNxtApprSplitting(Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
    		
        	obj.createDdhlist("");
        	
        	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
        		
        	session.setAttribute("obj", obj);
    	    		
        	String nextJSP = "/AHF/pages/Center/DonMuaHang.jsp";	    	
        	
    	    response.sendRedirect(nextJSP);

	    }else if(action.equals("new")){
	    	IERP_DonDatHang objddh=new ERP_DonDatHang();
	    	objddh.Init();
	    	session.setAttribute("obj", objddh);
	    	String nextJSP = "/AHF/pages/Center/Erp_DonDatHangNew.jsp";	 
	    	response.sendRedirect(nextJSP);
	    }	    
	    else if(action.equals("changeBCXLDH")){
  	
	    	obj.setUserId(userId);
	    	obj.createDdhlist("");
				
	    	session.setAttribute("obj", obj);
		    		
	    	String nextJSP = "/AHF/pages/Center/BCXulydonhang.jsp";	    	
	    
		    response.sendRedirect(nextJSP);
		 }
		 else{
	    	    
    	String search = getSearchQuery(request, obj, userId);   	
    	obj.setUserId(userId);
    	obj.createDdhlist(search);
			
    	session.setAttribute("obj", obj);
	    		
    	String nextJSP = "/AHF/pages/Center/DonMuaHang.jsp";	    	
    
	    response.sendRedirect(nextJSP);
	    }
	    

	}  
	
	private boolean CreatePivotTable(OutputStream out,IDonmuahangList obj,String query) throws Exception
    {    
		//String chuoi=getServletContext().getInitParameter("path") + "\\BCxylydonhang.xlsm";
		 //FileInputStream fstream = new FileInputStream(chuoi);		
		File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\BCxylydonhang.xlsm");
		FileInputStream fstream = new FileInputStream(f) ;
			 Workbook workbook = new Workbook();
		 workbook.open(fstream);
		 workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
	     	    
	     CreateStaticHeader(workbook,obj);	    
	     boolean isFillData = CreateStaticData(workbook,obj,query);
	     
	     if(!isFillData)
	    	 return false;
	     
	     workbook.save(out);
	     fstream.close();
	     
	     return true;
   }
	
	private boolean CreateStaticData(Workbook workbook,IDonmuahangList obj,String query) throws Exception
	{
		
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    ResultSet rs = db.get(query);
		int i = 2;
		if(rs!= null)
		{
			try 
			{
				cells.setColumnWidth(0, 25.0f);
				cells.setColumnWidth(1, 15.0f);
				cells.setColumnWidth(2, 15.0f);
				cells.setColumnWidth(3, 15.0f);
				cells.setColumnWidth(4, 15.0f);
				cells.setColumnWidth(5, 15.0f);
					
							
				for(int j = 12; j <= 22; j++)
					cells.setRowHeight(j, 14.0f);				
				Cell cell = null;
				while(rs.next())
				{
					String madondathang = rs.getString("chungtu")==null?"":rs.getString("chungtu");
					String ngaydat = rs.getString("ngaydat")==null?"":rs.getString("ngaydat");
					String ngayxuathd = rs.getString("ngaygiohd")==null?"":rs.getString("ngaygiohd");
					String ngaynhanhang = rs.getString("ngaynhanhang")==null?"":rs.getString("ngaynhanhang");
					String nppten = rs.getString("nppten")==null?"":rs.getString("nppten");
					String ngayduyet = rs.getString("ngayduyet")==null?"":rs.getString("ngayduyet");
					String ngaygiaohang = rs.getString("ngayhd")==null?"":rs.getString("ngayhd");
					String ngayhangve = rs.getString("ngayhangve")==null?"":rs.getString("ngayhangve");
						Double tonggiatri = rs.getDouble("sotienavat");
					
					

					cell = cells.getCell("DA" + Integer.toString(i)); cell.setValue(madondathang);	//0
					cell = cells.getCell("DB" + Integer.toString(i)); cell.setValue(ngaydat);		//1
					cell = cells.getCell("DC" + Integer.toString(i)); cell.setValue(ngayxuathd);	//2
					cell = cells.getCell("DD" + Integer.toString(i)); cell.setValue(ngaynhanhang);	//3
					cell = cells.getCell("DE" + Integer.toString(i)); cell.setValue(nppten);	//4
					cell = cells.getCell("DF" + Integer.toString(i)); cell.setValue(tonggiatri);		//5
					cell = cells.getCell("DG" + Integer.toString(i)); cell.setValue(ngayduyet);		//5
					cell = cells.getCell("DH" + Integer.toString(i)); cell.setValue(ngaygiaohang);		//5
					cell = cells.getCell("DI" + Integer.toString(i)); cell.setValue(ngayhangve);		//5
						
						i++;
				}
				if (rs != null)
					rs.close();	
				if(db!=null){
					db.shutDown();
				}
				if(i==2){					
					throw new Exception("Xin loi,khong co bao cao voi dieu kien da chon....!!!");
					
				}

			   			

			}
			catch (Exception e){
				e.printStackTrace();
				obj.SetMsg("Xin loi,khong co bao cao voi dieu kien da chon....!!!");
				throw new Exception(e.getMessage());
			}
			
		}else{
			return false;
		}	
		return true;
	}	
	
	private void CreateStaticHeader(Workbook workbook, IDonmuahangList obj) throws Exception
	{
		
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    
	    Style style;
	    Font font = new Font();
	    font.setColor(Color.RED);//mau chu
	    font.setSize(16);// size chu
	   	font.setBold(true);
	   	
	    cells.setRowHeight(0, 20.0f);
	    Cell cell = cells.getCell("A1");
	    style = cell.getStyle();
	    style.setFont(font); 
	    style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu 	        
	    
	    ReportAPI.getCellStyle(cell,Color.RED, true, 14, "BÁO CÁO XỬ LÝ ĐƠN HÀNG");
	            
	    String message = "";
		
		
		/*cells.setRowHeight(2, 18.0f);
		cell = cells.getCell("A2");
		ReportAPI.getCellStyle(cell, Color.RED, true, 9, message); */  

	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("A4");
	    
	//    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Từ ngày : " + obj.gettungay() + "" );
	    
	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("B4"); 
	 //   ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Đến ngày : " + obj.getdenngay() + "" );
		
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A5");
	//    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A6");
	//    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getuserTen());
	    		
	   
		cell = cells.getCell("DA1"); cell.setValue("Ma Don Dat Hang"); 
		cell = cells.getCell("DB1"); cell.setValue("Ngay Dat Hang");
		cell = cells.getCell("DC1"); cell.setValue("Ngay Ra Hoa Don");
		cell = cells.getCell("DD1"); cell.setValue("Ngay Nhan Hang");
		cell = cells.getCell("DE1"); cell.setValue("Ten Nha Phan Phoi");
		cell = cells.getCell("DF1"); cell.setValue("Tong Gia Tri Don Hang");
		cell = cells.getCell("DG1"); cell.setValue("Ngay Duyet");		//5
		cell = cells.getCell("DH1"); cell.setValue("Ngay Giao Hang");		//5
		cell = cells.getCell("DI1"); cell.setValue("Ngay Hang Ve");		//5
	
		   
	}
	
	private String setQuery( String userId,IDonmuahangList obj,HttpServletRequest request)
	{
		
		Utility util = new Utility();
	
		   String tungay=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays")));
		   String denngay=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays")));
		   
		    String vungId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"));
		    if (vungId == null){
		    	vungId = "";
		    }  
		    
		    String khuvucId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId"));
		    if (khuvucId == null){
		    	khuvucId = "";
		    }  

		
    	String nppTen = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppTen")));
    	if(nppTen == null)
    		nppTen = "";
    	obj.setnppTen(nppTen);
    	
    	String 	query1 ="select convert(varchar, a.ngaydat, 120) as ngaydat,isnull( convert(varchar, a.ngaygiodat, 103) + '' + substring( convert(varchar, a.ngaygiodat, 120),11,20),'')   as  ngaygiodat, " +
		"   isnull( convert(varchar, hdnh.ngaygiohd, 103) + '' + substring( convert(varchar, hdnh.ngaygiohd, 120),11,20),'')  as ngaygiohd , " +
		"   isnull( convert(varchar, nh.thoigianhangve, 103) + '' + substring( convert(varchar, nh.thoigianhangve, 120),11,20),'') as  ngayhangve , " +
		"   isnull( convert(varchar, LOGNHAP.date, 103) + '' + substring( convert(varchar, LOGNHAP.date, 120),11,20),'') as  ngaynhanhang , " +
		"   isnull( convert(varchar, nh.ngayinpgh, 103) + '' + substring( convert(varchar, nh.ngayinpgh, 120),11,20),'') as  ngayhd , " +
		"  isnull( convert(varchar, a.NGAYDUYET, 103) + '' + substring( convert(varchar, a.NGAYDUYET, 120),11,20),'') as  ngayduyet  , a.pk_seq as chungtu, f.ten as nppTen, e.donvikinhdoanh,   "+
		" isnull(a.sotienavat,0) as sotienavat, b.ten as nguoitao, c.ten as nguoisua, a.trangthai,isnull( a.soid ,'0') as soid,  "+
		" isnull(nh.sotienbvat,'0') as tienhd , "+
		" isnull(nh.trangthai,0) as isnhanhang, isnull(thang,'') as thang, isnull(nam,'') as nam ,isnull(a.loaidonhang,0) as loaidonhang, a.DONDATHANG_FROM_FK "+
		" from dondathang a " +
		" left join nhanvien b on  a.nguoitao = b.pk_seq "+   
		" left join  nhanvien c on a.nguoisua = c.pk_seq "+  
		" left join donvikinhdoanh e on a.dvkd_fk = e.pk_seq  "+ 
		" left join  nhaphanphoi  f  on a.npp_fk = f.pk_seq    "+
		" left join nhaphang nh on nh.dathang_fk=a.pk_seq   " +
		" left join LOGNHAP on LOGNHAP.nhaphang_fk=nh.pk_seq  "+
		" left join hdnhaphang hdnh on hdnh.dathang_fk=a.pk_seq "+
		 " where   f.pk_seq in "+ util.quyen_npp(userId) ;
    	
    	
	
		
    	
    	
    	if (tungay.length()>0){
			query1 = query1 + " and a.ngaydat >= '" + tungay+ "'";
			
    		
    	}

    	if (denngay.length()>0){
			query1 = query1 + " and a.ngaydat <= '" + denngay+ "'";
		
    		
    	}

    	if(vungId.trim().length() > 0){
    		query1 = query1 + " and f.khuvuc_fk in ( select pk_seq from khuvuc where vung_fk =  "+ vungId +" ) ";
    	}
    	
    	if(khuvucId.trim().length() > 0){
    		query1 = query1 + " and f.khuvuc_fk = "+ khuvucId +" ";
    	}
    		
    	
    	System.out.println(query1);
		 return query1;

	
	}

	private String getSearchQuery(HttpServletRequest request, IDonmuahangList obj, String userId){
//	    PrintWriter out = response.getWriter();
		
		Utility util = new Utility();
		String sku = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sku")));
    	if (sku == null)
    		sku = "";
    	
    	sku = sku.split("-")[0].trim();
    	obj.setSKU(sku);
    	
    	String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
    	if (tungay == null)
    		tungay = "";    	
    	obj.setTungay(tungay);

    	String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
    	if (denngay == null)
    		denngay = "";    	
    	obj.setDenngay(denngay);
    	   	
    	String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));   	
    	if (trangthai == null)
    		trangthai = "";    	
	
    	if (trangthai.equals(""))
    		trangthai = "";
    	
    	obj.setTrangthai(trangthai);
    	
    	String kvId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kvId")));   	
    	if (kvId == null)
    		kvId = "";    	
	
    	if (kvId.equals("0"))
    		kvId = "";
    	
    	obj.setKvId(kvId);

    	String so = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("so"));
    	if(so == null)
    		so = "";
    	if(so.equals("0"))
    		so = "";
    	obj.setSO(so);
    	
    	String nppTen = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppTen")));
    	if(nppTen == null)
    		nppTen = "";
    	obj.setnppTen(nppTen);
    	
    	String 	query1 ="select convert(varchar, a.ngaydat, 120) as ngaydat,isnull( convert(varchar, a.ngaygiodat, 103) + '' + substring( convert(varchar, a.ngaygiodat, 120),11,20),'')   as  ngaygiodat, " +
		"   isnull( convert(varchar, hdnh.ngaygiohd, 103) + '' + substring( convert(varchar, hdnh.ngaygiohd, 120),11,20),'')  as ngaygiohd , " +
		"   isnull( convert(varchar, nh.thoigianhangve, 103) + '' + substring( convert(varchar, nh.thoigianhangve, 120),11,20),'') as  ngayhangve , " +
		"   isnull( convert(varchar, LOGNHAP.date, 103) + '' + substring( convert(varchar, LOGNHAP.date, 120),11,20),'') as  ngaynhanhang , " +
		"   isnull( convert(varchar, nh.ngayinpgh, 103) + '' + substring( convert(varchar, nh.ngayinpgh, 120),11,20),'') as  ngayhd , " +
		"  isnull( convert(varchar, a.NGAYDUYET, 103) + '' + substring( convert(varchar, a.NGAYDUYET, 120),11,20),'') as  ngayduyet  , a.pk_seq as chungtu, f.ten as nppTen, e.donvikinhdoanh,   "+
		" isnull(a.sotienavat,0) as sotienavat, b.ten as nguoitao, c.ten as nguoisua, a.trangthai,isnull( a.soid ,'0') as soid,  "+
		" isnull(nh.sotienbvat,'0') as tienhd , "+
		" isnull(nh.trangthai,0) as isnhanhang, isnull(thang,'') as thang, isnull(nam,'') as nam ,isnull(a.loaidonhang,0) as loaidonhang, a.DONDATHANG_FROM_FK "+
		" from dondathang a " +
		" left join nhanvien b on  a.nguoitao = b.pk_seq "+   
		" left join  nhanvien c on a.nguoisua = c.pk_seq "+  
		" left join donvikinhdoanh e on a.dvkd_fk = e.pk_seq  "+ 
		" left join  nhaphanphoi  f  on a.npp_fk = f.pk_seq    "+
		" left join nhaphang nh on nh.dathang_fk=a.pk_seq   " +
		" left join LOGNHAP on LOGNHAP.nhaphang_fk=nh.pk_seq  "+
		" left join hdnhaphang hdnh on hdnh.dathang_fk=a.pk_seq "+
		 " where   f.pk_seq in "+ util.quyen_npp(userId) ;
    	
    	
	
		
    	
    	
    	if (tungay.length()>0){
			query1 = query1 + " and a.ngaydat >= '" + tungay+ "'";
			
    		
    	}

    	if (denngay.length()>0){
			query1 = query1 + " and a.ngaydat <= '" + denngay+ "'";
		
    		
    	}

    	if(trangthai.length() > 0){
    		query1 = query1 + " and a.trangthai = '" + trangthai + "'";
    		
    	}

    	if(kvId.length() > 0){
    		query1 = query1 + " and f.khuvuc_fk = '" + kvId + "'";
    	
    	}
    	
      	if(so.length() > 0){
    		query1 = query1 + " and cast(a.pk_seq  as nvarchar(10)) like '%" + so + "%'";
      	}
      	
      	if(nppTen.length()>0){
      		query1 = query1 + " and [dbo].[fuConvertToUnsign1](lower( f.ten)) like lower(N'%"+ util.replaceAEIOU(nppTen) +"%')"; 
      		 
      	}
    	
    	
    	System.out.println(query1);
    	
    	return query1;
	}	
		
	private void Delete(String ddhId){
		//xoa thi =6
		dbutils db = new dbutils();
		try{
			db.getConnection().setAutoCommit(false);
			
			
			//lay chi tiet don hang cu ra va cap nhat lai so luong check va available ,dong thoi cap nhat lai cot dadathang trong bang denghidathang_sp
			String sql="select soluong,sanpham_fk,npp.khosap from dondathang_sp inner join dondathang ddh on ddh.pk_seq=dondathang_fk inner join nhaphanphoi npp on npp.pk_seq=ddh.npp_fk  where dondathang_fk="+ddhId;
			System.out.println("Get Detail :"+ sql);
			ResultSet rs_detail_ddh=db.get(sql);
			if(rs_detail_ddh!=null){
				while (rs_detail_ddh.next()){
				sql = "update  erp_khott_sanpham set booked = booked - '" +rs_detail_ddh.getInt("soluong") + "', available = available + '" + rs_detail_ddh.getInt("soluong") + "' where sanpham_fk='" + rs_detail_ddh.getString("sanpham_fk") +"' and khott_fk='" + rs_detail_ddh.getString("khosap")+ "'";
				System.out.println("Get Detail :"+ sql);
					if(!db.update(sql)){
						System.out.println("Error update KHO : line -524 : HoaDon : "+sql);
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						return ;
					}
				}
			}
			String query = "update  dondathang set trangthai='6' where pk_seq='" + ddhId + "'";
			db.update(query);
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			db.shutDown();
			
		}catch(Exception er){
			System.out.println(er.toString());
		 geso.dms.center.util.Utility.rollback_throw_exception(db);	
		}
	}
}