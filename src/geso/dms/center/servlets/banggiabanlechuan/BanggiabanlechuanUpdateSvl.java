package geso.dms.center.servlets.banggiabanlechuan;

import geso.dms.center.beans.banggiablc.IBanggiablc;
import geso.dms.center.beans.banggiablc.IBanggiablcList;
import geso.dms.center.beans.banggiablc.imp.Banggiablc;
import geso.dms.center.beans.banggiablc.imp.BanggiablcList;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.Utility;
import geso.dms.distributor.db.sql.dbutils;

import com.aspose.cells.Color;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;


 public class BanggiabanlechuanUpdateSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
  

   public BanggiabanlechuanUpdateSvl() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    
		HttpSession session = request.getSession();
		
		out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    
	    
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String id = util.getId(querystring);  	
	    out.println(id);
	    
	    IBanggiablc bgBean = new Banggiablc();
	    
	    if(querystring.contains("display"))
	    	bgBean.setDisplay("1");
	    
        bgBean.setUserId(userId);
        bgBean.setId(id);
        bgBean.init();
        String nextJSP = "/AHF/pages/Center/BangGiaBanLeChuanUpdate.jsp";
        if(querystring.contains("copy"))
        {
        	nextJSP = "/AHF/pages/Center/BangGiaBanLeChuanNew.jsp";
        	 bgBean.setId(null);
        	 bgBean.setTrangthai("0");
        	 bgBean.setTungay(Utility.getNgayHienTai());
        }
        
        session.setAttribute("bgblcBean", bgBean);
        
        response.sendRedirect(nextJSP);

	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();

		OutputStream out = response.getOutputStream();
		
		IBanggiablc bgBean = new Banggiablc();
	    Utility util = new Utility();
	    
		String id =  util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
	    
	    if(id == null){  	
	    	id = "";
	    }
	    bgBean.setId(id);
	    
	    
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		bgBean.setUserId(userId);
	    
    	String bgTen = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("bgTen"));
		if (bgTen == null)
			bgTen = "";				
    	bgBean.setTen(bgTen);

		String dvkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")));
		if (dvkdId == null)
			dvkdId = "";
		bgBean.setDvkdId(dvkdId);
		
		String kbhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kbhId")));
		System.out.println("kbhId svl: " +kbhId);
		if (kbhId == null)
			kbhId = "";
		bgBean.setKbhId(kbhId);
		
		
	        	
		String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));
    	if (trangthai == null)
    		trangthai = "0";
    	else
    		trangthai = "1";
    	bgBean.setTrangthai(trangthai);
		
    	
    	String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
		if (tungay == null)
			tungay = "";
		bgBean.setTungay(tungay);
    	
    	String[] giablc = request.getParameterValues("giablc");
    	bgBean.setGiablc(giablc);
    	
    	String[] quycach= request.getParameterValues("quycach");
    	bgBean.setQuycach(quycach);
    	
    	
    	String[] loaikhId = request.getParameterValues("loaikhId");
		String str = "";
		if(loaikhId != null)
		{
			for(int i = 0; i < loaikhId.length; i++)
				str += loaikhId[i] + ",";
			if(str.length() > 0)
				str = str.substring(0, str.length() - 1);
		}
		//System.out.println("---LOAI KHACH HANG: " + str);
		bgBean.setLoaiKhIds(str);
    	String[] spIdArr = request.getParameterValues("spIdArr");
    	bgBean.setSpIdArr(spIdArr);
    	String[] spMaArr = request.getParameterValues("spMaArr");
    	bgBean.setSpMaArr(spMaArr);
    	String[] spTenArr = request.getParameterValues("spTenArr");
    	bgBean.setSpTenArr(spTenArr);
    	String[] donviArr = request.getParameterValues("donviArr");
    	bgBean.setDonviArr(donviArr);
    	String[] dongiaArr = request.getParameterValues("dongiaArr");
    	bgBean.setDongiaArr(dongiaArr);
    	String[] checkluonhien = request.getParameterValues("checkluonhien");
    	bgBean.setCheckluonhien(checkluonhien);
    	String[] checkban = request.getParameterValues("checkban");
    	bgBean.setCheckban(checkban);
		String ngaysua = getDateTime();
    	bgBean.setNgaysua(ngaysua);
    	bgBean.setNgaytao(ngaysua);
    	
		bgBean.setNguoitao(userId);
		bgBean.setNguoisua(userId);
    			
		boolean error = false;
				
		if (dvkdId.trim().length()== 0){
			bgBean.setMessage("Vui long chon Don vi kinh doanh");
			error = true;
		}
		if (bgTen.trim().length()== 0){
			bgBean.setMessage("Vui long nhap vao ten bang gia");
			error = true;
		}

		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    
		if (action.equals("excel"))
	    {
	    	try
		    {
	    		System.out.println("vao xuat excel");
		    	response.setContentType("application/vnd.ms-excel");
		        response.setHeader("Content-Disposition", "attachment; filename=BangGiaBanLeChuan.xlsm");
		        FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\banggiabanlechuanNew.xlsm");
				Workbook workbook = new Workbook();
				workbook.open(fstream);
				workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
//				CreateHeader_DTGV1(workbook, obj, query1);
			     CreateStaticHeader(workbook, bgTen, kbhId, userId, str,tungay);
			     CreateStaticData(workbook, getSearchQuery2(request, "", ""));
			
			     //Saving the Excel file
			     workbook.save(out);
			     fstream.close();
		    }
		    catch (Exception ex){ }
	    	
		    bgBean.setUserId(userId);
			bgBean.setId(id);
			bgBean.init();
			session.setAttribute("bgblcBean", bgBean);
			String nextJSP = "/AHF/pages/Center/BangGiaBanLeChuanUpdate.jsp";
			response.sendRedirect(nextJSP);	
	    }
		
		if(action.equals("save"))
		{
			if (id.length()==0)
			{
				System.out.println("vao save");
				if (!(bgBean.CreateBgblc(request))){									
					bgBean.setUserId(userId);
					bgBean.createRS();
					session.setAttribute("bgblcBean", bgBean);
					String nextJSP = "/AHF/pages/Center/BangGiaBanLeChuanNew.jsp";
					response.sendRedirect(nextJSP);
				}else{
					IBanggiablcList obj = new BanggiablcList();
					obj.setUserId(userId);
					session.setAttribute("obj", obj);
						
					String nextJSP = "/AHF/pages/Center/BangGiaBanLeChuan.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}
				
			}else{
				if (!(bgBean.UpdateBgblc(request))){								
					bgBean.setUserId(userId);
					bgBean.createRS();
					session.setAttribute("bgblcBean", bgBean);
					String nextJSP = "/AHF/pages/Center/BangGiaBanLeChuanUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else{
					IBanggiablcList obj = new BanggiablcList();
					obj.setUserId(userId);
					session.setAttribute("obj", obj);
						
					String nextJSP = "/AHF/pages/Center/BangGiaBanLeChuan.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}
			}
		}else {
			System.out.println("id la "+id);
			String nextJSP;
			if (id.length()==0)
			{							
				bgBean.setUserId(userId);
				bgBean.createRS();
				session.setAttribute("bgblcBean", bgBean);
				nextJSP = "/AHF/pages/Center/BangGiaBanLeChuanNew.jsp";
			}
			else
			{
				bgBean.setUserId(userId);
				bgBean.setId(id);
				bgBean.init();
				bgBean.setKbhId(kbhId);
				bgBean.setDvkdId(dvkdId);
				session.setAttribute("bgblcBean", bgBean);
				
				nextJSP = "/AHF/pages/Center/BangGiaBanLeChuanUpdate.jsp";   						
			}
			response.sendRedirect(nextJSP);
			
		}
	}
	
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private void CreateStaticHeader(Workbook workbook, String tenbg, String dvkd, String nguoitao, String khapdung, String tungay) 
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	 
	    Cells cells = worksheet.getCells();
	    String sql  = " select DIENGIAI from KENHBANHANG where pk_seq = " + dvkd;
	    System.out.println("lay kenh: " + sql);
	    ResultSet rs = db.get(sql);
	    String tenkenh = "";
	    try {
			if(rs.next()){
				tenkenh = rs.getString("DIENGIAI");
			}
			 rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	   
	    Cell cell = cells.getCell("A1"); 
//	    cell.setValue(tenbg);
	    ReportAPI.getCellStyle(cell,Color.RED, true, 16, tenbg);
	    cell = cells.getCell("A2");
	    cell.setValue("Hiệu lực từ: " + tungay);
	    cell = cells.getCell("A3");
	    cell.setValue("Kênh:  " + tenkenh);
	    cell = cells.getCell("A4");
	    
	    sql  = " select ten from nhanvien where pk_seq = " + nguoitao;
	    System.out.println("lay user: " + sql);
	    rs = db.get(sql);
	    String tenuser = "";
	    try {
			if(rs.next()){
				tenuser = rs.getString("ten");
			}
			 rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    
	    cell.setValue("Người tạo: " + tenuser);
	    
	    cell = cells.getCell("A5");
	    cell.setValue("Ngày tạo: " + this.getDateTime());
	    sql  = " select pk_seq as loaiId, loai as loaiTEN from loaicuahang where trangthai = 1 and pk_seq in ("+khapdung+ ")";
	     rs = db.get(sql);
	    String loaicuahangapdung = "";
	    try {
	    	while(rs.next()){
	    		loaicuahangapdung += rs.getString("loaiTEN") + ",";
			}
	    	loaicuahangapdung = loaicuahangapdung.substring(0, loaicuahangapdung.length() - 1);
			 rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    cell = cells.getCell("A6");
	    cell.setValue("Loại khách hàng áp dụng: " +loaicuahangapdung );
	    
	    //tieu de
	    cell = cells.getCell("A8");
	    cell.setValue("STT");
	    ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
	    ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "STT");
	    cell = cells.getCell("B8");
	    cell.setValue("Mã sản phẩm");
	    ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 10);
	    ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Mã sản phẩm");
	    cell = cells.getCell("C8");
	    cell.setValue("Tên sản phẩm");
	    ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
	    ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Tên sản phẩm");
	    cell = cells.getCell("D8");
	    cell.setValue("Đơn vị tính");
	    ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
	    ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Đơn vị tính");
	    cell = cells.getCell("E8");
	    cell.setValue("Giá bán lẻ (Sau VAT)");
	    ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
	    ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Giá bán lẻ (Sau VAT)");
	    worksheet.setName("Bảng giá bán lẻ chuẩn");
	    db.shutDown();
	}

	private void CreateStaticData(Workbook workbook, String query) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    
		dbutils db = new dbutils();
//		System.out.println("query excel: " + query);
		ResultSet rs = db.get(query);
//		System.out.println("chay xuat excel");
		int i = 9;
		if(rs != null)
		{
			try 
			{
				cells.setColumnWidth(0, 6.0f);
				cells.setColumnWidth(1, 20.0f);
				cells.setColumnWidth(2, 40.0f);
				cells.setColumnWidth(3, 20.0f);
				cells.setColumnWidth(4, 20.0f);
				for(int j = 0; j < 5; j++)
					cells.setRowHeight(j, 13.0f);
				
				Cell cell = null;
				int sott = 1;
				while(rs.next()) {
					String masp = "";
					if(rs.getString("ma")!= null) masp = rs.getString("ma");
					
					String tensp = "";
					if(rs.getString("ten") != null) tensp = rs.getString("ten");
					
					String donvitinh = "";
					if(rs.getString("donvi") != null) donvitinh = rs.getString("donvi");
					
					String giabanle = "";
					if(rs.getString("giabanlechuan") != null) giabanle = rs.getString("giabanlechuan");
					
					cell = cells.getCell("A" + Integer.toString(i));
					cell.setValue(sott);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
					ReportAPI.getCellStyle(cell,Color.BLACK, true, 10, sott+"");
					cell = cells.getCell("B" + Integer.toString(i));
					cell.setValue(masp);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
					ReportAPI.getCellStyle(cell,Color.BLACK, true, 10, masp);
					cell = cells.getCell("C" + Integer.toString(i));
					cell.setValue(tensp);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
					ReportAPI.getCellStyle(cell,Color.BLACK, true, 10, tensp);
					cell = cells.getCell("D" + Integer.toString(i));
					cell.setValue(donvitinh);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
					ReportAPI.getCellStyle(cell,Color.BLACK, true, 10, donvitinh);
					cell = cells.getCell("E" + Integer.toString(i));
					cell.setValue(giabanle);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
					ReportAPI.getCellStyle(cell,Color.BLACK, true, 10, giabanle);
					i++;
					sott++;
				}
				rs.close();
			}
			catch(Exception e){ e.printStackTrace();}
		}
		if(db!=null) db.shutDown();
	}
	
	private String getSearchQuery2(HttpServletRequest request, String pages, String soDong)
	{
		Utility util =  new Utility();
		String id =  util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
	    if(id == null)  	
	    	id = "";
	    
	    String dvkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")));
    	if (dvkdId == null)
    		dvkdId = "";
    	
    	String kbhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kbhId")));
    	if (kbhId == null)
    		kbhId = "";
    	
    	String query  ="\n   select a.pk_seq as id, a.ma, a.ten,isnull( c.giabanlechuan,0) as giabanlechuan " +
				 "\n 		,isnull((qc.SOLUONG1/nullif(qc.SOLUONG2,0)),0)  as Quycach, dv.DIENGIAI as donvi		"+
				 "\n 	from sanpham a  		"+
				 "\n 	inner join donvikinhdoanh b on a.DVKD_FK=b.PK_SEQ 	 and   b.pk_seq = '"+dvkdId+"' 	"+
				 "\n 	left join banggiablc_sanpham c on c.SANPHAM_FK=a.PK_SEQ and  c.BGBLC_FK="+id+" "		+
				 "\n 	left join  banggiabanlechuan d on d.PK_SEQ=c.BGBLC_FK and  d.kbh_fk = "+kbhId +"  	"+
				 "\n 	inner join DONVIDOLUONG dv on dv.PK_SEQ = a.DVDL_FK	    "+
				 "\n   left join QUYCACH qc  on qc.SANPHAM_FK=a.PK_SEQ and qc.DVDL2_FK=100018 and qc.dvdl1_fk=a.dvdl_fk  	"+
				 "\n 	where   " +
				 "\n   	  isnull((qc.SOLUONG1/nullif(qc.SOLUONG2,0)),0)<>0 " +
				 "\n		 order by ma"; 
	
//    	String query = "select b.ma as spMa, b.ten as spTen, a.giabanlechuan as gblc from banggiablc_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq where bgblc_fk = '" + id + "'";
    	
    	System.out.print("\n Query xuat excel la: " + query + "\n");
    	return query;
    	
	}
	
}