package geso.dms.center.servlets.duyettrakhuyenmai;

import geso.dms.center.beans.duyettrakhuyenmai.*;
import geso.dms.center.beans.duyettrakhuyenmai.imp.*;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
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

import com.aspose.cells.BorderLineType;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Worksheet;
import com.oreilly.servlet.MultipartRequest;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class DuyettrakhuyenmaiUpdateSvl extends HttpServlet 
{

	
	
	
	private static final long serialVersionUID = 1L;
 
	
    public DuyettrakhuyenmaiUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		IDuyettrakhuyenmai tctskuBean;
		
	
		Utility util = new Utility();		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	  
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    	    
	    String id = util.getId(querystring);
	   
	    tctskuBean = new Duyettrakhuyenmai(id);
	    tctskuBean.setId(id);
	    tctskuBean.setUserId(userId);
	    
        tctskuBean.init();
        session.setAttribute("tctskuBean", tctskuBean);
        
        String nextJSP = "/AHF/pages/Center/DuyetTraKhuyenMaiUpdate.jsp";
        if(querystring.indexOf("display") >= 0)
        	nextJSP = "/AHF/pages/Center/DuyetTraKhuyenMaiDisplay.jsp";
        
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IDuyettrakhuyenmai tctskuBean;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
 		String action = request.getParameter("action");
 		System.out.println("Action la: " + action);
 		if(action == null)
 			action = "upload";
 		
 		String contentType = request.getContentType();
 		
		Utility util = new Utility();
		
	   	String id = util.antiSQLInspection(request.getParameter("id"));
	    if(id == null){  	
	    	tctskuBean = new Duyettrakhuyenmai("");
	    }else{
	    	tctskuBean = new Duyettrakhuyenmai(id);
	    }
	    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		tctskuBean.setUserId(userId);
		
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		tctskuBean.setDiengiai(diengiai);
		
		String thang = util.antiSQLInspection(request.getParameter("thang"));
		if (thang == null)
			thang = "";
		tctskuBean.setThang(thang);
		
		String nam = util.antiSQLInspection(request.getParameter("nam"));
		if (nam == null)
			nam = "";
		tctskuBean.setNam(nam);
		
		String scheme = util.antiSQLInspection(request.getParameter("ctkmId"));
		if (scheme == null)
			scheme = "";
		tctskuBean.setCtkmId(scheme);
		System.out.println("ctkm ID day : "+scheme);
		
		String ngayduyet = util.antiSQLInspection(request.getParameter("ngayduyet"));
		if (ngayduyet == null)
			ngayduyet = "";
		tctskuBean.setNgayduyet(ngayduyet);
		
		String tungay_ds = util.antiSQLInspection(request.getParameter("tungay_ds"));
		if (tungay_ds == null)
			tungay_ds = "";
		System.out.println("tungayds : "+ tungay_ds);
		tctskuBean.setTungay_ds(tungay_ds);
		
		String denngay_ds = util.antiSQLInspection(request.getParameter("denngay_ds"));
		if (denngay_ds == null)
			denngay_ds = "";
		System.out.println("denngayds : "+ denngay_ds);
		tctskuBean.setDenngay_ds(denngay_ds);	
		
		
		//String[] nppId = request.getParameterValues("nppId");
		//tctskuBean.setNppId(nppId);
		
		String[] nppTen = request.getParameterValues("nppTen");
		tctskuBean.setNppTen(nppTen);
		
		String[] khId = request.getParameterValues("khId");
		tctskuBean.setKhId(khId);
		
		String[] khTen = request.getParameterValues("khTen");
		tctskuBean.setKhTen(khTen);
		
		String[] mucdk = request.getParameterValues("mucdk");
		tctskuBean.setMucDk(mucdk);
		
		String[] doanhso = request.getParameterValues("doanhso");
		tctskuBean.setDoanhso(doanhso);
		
		String[] mucdat = request.getParameterValues("mucdat");
		tctskuBean.setMucthuong(mucdat);
		
		String[] tongtien = request.getParameterValues("tongthuong");
		tctskuBean.setTongtien(tongtien); 
		
		String[] sanpham = request.getParameterValues("sanpham");
		tctskuBean.setSanpham(sanpham);
		
		String[] dangkyIds =  request.getParameterValues("dangkyIds")  ;
		tctskuBean.setDangkyIds(dangkyIds);
 		
 		if(action.equals("save"))
 		{    
 			if (id == null || id.trim().length() <= 0)
 			{
 				if (!(tctskuBean.createDuyet()))
 				{			
 			    	tctskuBean.setUserId(userId);			    	
 			    	tctskuBean.createRs();
 			    	session.setAttribute("userId", userId);
 					session.setAttribute("tctskuBean", tctskuBean);				
 					String nextJSP = "/AHF/pages/Center/DuyetTraKhuyenMaiNew.jsp";
 					response.sendRedirect(nextJSP);
 					return;
 				}
 				else
 				{
 					// tạo ok thì qua trang cập nhật luôn để tính thưởng
 					tctskuBean.setUserId(userId);	    	
 			    	tctskuBean.init();
 			    	session.setAttribute("userId", userId);
 					session.setAttribute("tctskuBean", tctskuBean);				
 					String nextJSP = "/AHF/pages/Center/DuyetTraKhuyenMaiUpdate.jsp";
 					response.sendRedirect(nextJSP);
 					return;
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
 					String nextJSP = "/AHF/pages/Center/DuyetTraKhuyenMaiUpdate.jsp";
 					response.sendRedirect(nextJSP);
 					return;
 				}
 				else
 				{
 					tctskuBean.DbClose();
 					IDuyettrakhuyenmaiList obj = new DuyettrakhuyenmaiList();
 				    obj.setUserId(userId);
 				    obj.init("");
 					session.setAttribute("obj", obj);
 				    String nextJSP = "/AHF/pages/Center/DuyetTraKhuyenMai.jsp";
 					response.sendRedirect(nextJSP);
 					return;
 				}
 			}
	    }
 		else if(action.equals("newform")){
 			// tính thưởng
 			tctskuBean.createTctSKU();		
 			tctskuBean.init();
	    	tctskuBean.setUserId(userId);
	    	
	    	session.setAttribute("userId", userId);
			session.setAttribute("tctskuBean", tctskuBean);
			
			String nextJSP = "/AHF/pages/Center/DuyetTraKhuyenMaiUpdate.jsp";
			response.sendRedirect(nextJSP);
			return;		
 		}
 		else if(action.equals("excel")){
 			try 
			{
				request.setCharacterEncoding("utf-8");
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BCTichLuy.xls");
				OutputStream out1 = response.getOutputStream();
				String query = setQuery(tctskuBean);
				ExportToExcel( out1, tctskuBean, query );
				tctskuBean.getDb().shutDown();
			} 
			catch (Exception ex) 
			{
				ex.printStackTrace();
				System.out.println("Error Here : "+ex.toString());
				request.getSession().setAttribute("errors", ex.getMessage());
			}
	    	return;
 		}
 		else if(action.equals("upload")){
 			if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0)) 
			{
 				MultipartRequest multi = new MultipartRequest(request, "C:\\java-tomcat\\data\\", 20000000, "UTF-8");
 				//MultipartRequest multi = new MultipartRequest(request, getServletContext().getRealPath("\\upload\\"), 20000000, "UTF-8"); 
			    userId = util.antiSQLInspection(multi.getParameter("userId"));
			    id = util.antiSQLInspection(multi.getParameter("id"));	 
			    if(id == null){  	
			    	tctskuBean = new Duyettrakhuyenmai("");
			    }else{
			    	tctskuBean = new Duyettrakhuyenmai(id);
			    }
			    tctskuBean.setUserId(userId);
			    tctskuBean.setCtkmId(util.antiSQLInspection(multi.getParameter("ctkmId"))==null? "":util.antiSQLInspection(multi.getParameter("ctkmId")));	 
			    tctskuBean.setDiengiai(util.antiSQLInspection(multi.getParameter("diengiai"))==null? "":util.antiSQLInspection(multi.getParameter("diengiai")));	
			    tctskuBean.setNgayduyet(util.antiSQLInspection(multi.getParameter("ngayduyet"))==null? "":util.antiSQLInspection(multi.getParameter("ngayduyet")));   	 
			    
			  	Enumeration files = multi.getFileNames(); 
			  	String filenameu  ="";
			  	while (files.hasMoreElements())
	            {
	                 String name = (String)files.nextElement();
	                 filenameu = multi.getFilesystemName(name); 
	                 System.out.println("name :   "+name);;
	            }
			  	boolean err = false;
			  	String filename = "C:\\java-tomcat\\data\\" + filenameu;
				//String filename=    getServletContext().getRealPath("\\upload\\")+ "\\"+ filenameu;
				if(filenameu == null)
					tctskuBean.setMsg("Không có file nào được upload");
				if (filenameu != null && filename.length() > 0)
				{
					//doc file excel
					File file = new File(filename);
					System.out.println("filename  "+file);
					Workbook workbook;
					int indexRow = 11;
			
					try 
					{						
						System.out.println(file);
						workbook = Workbook.getWorkbook(file);
						
						Sheet[] sheet1 = workbook.getSheets();
						Sheet sheet=sheet1[0];					 
						Cell[] cells ;
						String khachhang_fks = "";
						Hashtable<String, Integer> kh_muc = new Hashtable<String, Integer>();
						int sokh = 0;
						//System.out.println("getRows = " +sheet.getRows());
						for(int i= indexRow; i < sheet.getRows();i++)
						{	
							cells = sheet.getRow(i);
							//System.out.println("cell length: " +cells.length);
							if (cells.length >= 10){
								String KHACHHANG_FK = cells[2].getContents().toString().trim();
								String muc_dk = cells[9].getContents().toString();
								khachhang_fks += KHACHHANG_FK + ",";
								if(muc_dk.trim().length() == 0)
									muc_dk = "0";
								kh_muc.put(KHACHHANG_FK, Integer.parseInt(muc_dk));
								sokh ++;
								System.out.println("__ " + KHACHHANG_FK + ":" + muc_dk);
							}							
						}
						if(sokh == 0)
							tctskuBean.setMsg("Không có khách hàng nào được chọn!");
						
						if(khachhang_fks.length() > 0)
							khachhang_fks = khachhang_fks.substring(0, khachhang_fks.length() - 1);

						
						if(sokh > 0)
						{
							
							if( tctskuBean.setDuyetMucThuong(khachhang_fks, kh_muc))
							{
								tctskuBean.init();
						    	session.setAttribute("tctskuBean", tctskuBean);
						    	String nextJSP;
								if (id == null){
									nextJSP = "/AHF/pages/Center/DuyetTraKhuyenMaiNew.jsp";
								}
								else{
									nextJSP = "/AHF/pages/Center/DuyetTraKhuyenMaiUpdate.jsp";   						
								}
								response.sendRedirect(nextJSP);
						    	return;
							}
						}
					}catch(Exception er){
						er.printStackTrace();
						System.out.println("Exception. "+er.getMessage());
						tctskuBean.setMsg("Lỗi trong quá trình upload: " + er.getMessage());
					}
				}
				tctskuBean.createRs();
		    	session.setAttribute("tctskuBean", tctskuBean);
		    	String nextJSP;
				if (id == null){
					nextJSP = "/AHF/pages/Center/DuyetTraKhuyenMaiNew.jsp";
				}
				else{
					nextJSP = "/AHF/pages/Center/DuyetTraKhuyenMaiUpdate.jsp";   						
				}
				response.sendRedirect(nextJSP);
		    	return;
			}
 		}
		else
		{
			tctskuBean.createRs();
			session.setAttribute("userId", userId);
			session.setAttribute("tctskuBean", tctskuBean);
			
			String nextJSP;
			if (id == null){
				nextJSP = "/AHF/pages/Center/DuyetTraKhuyenMaiNew.jsp";
			}
			else{
				nextJSP = "/AHF/pages/Center/DuyetTraKhuyenMaiUpdate.jsp";   						
			}
			response.sendRedirect(nextJSP);
		}
	}
	
	
	
	
	private String setQuery(IDuyettrakhuyenmai obj) throws SQLException 
	{
		Utility util = new Utility();

		String pivot = "";
		String query =  "\n select distinct sp.ma + ' - ' + sp.TEN masp  " +
						"\n from DUYETTRAKHUYENMAI_KHACHHANG_sanpham ct " +
						"\n inner join sanpham sp on sp.PK_SEQ  = ct.sanpham_fk " +
						"\n where  ct.duyetkm_fk =  " + obj.getId();
		
		ResultSet rsData = obj.getDb().get(query);

		while(rsData.next())
		{
			String masp=  rsData.getString("masp");
			pivot += pivot.trim().length() > 0 ?  ",["+masp+"_isNum]" : "["+masp+"_isNum]";
		}
		
		if(pivot.trim().length() <=0)
		{
			query = "\n select p.TEN as [NHÀ PHÂN PHỐI], isnull(dbo.[ListOfTDV](kh.pk_seq),'') as [NHÂN VIÊN BÁN HÀNG],kh.pk_seq [Mã hệ thống], kh.SMARTID AS [MÃ KHÁCH HÀNG], kh.TEN as [TÊN KHÁCH HÀNG]  " +
			"\n , km.DOANHSO [DOANH SỐ_isNum],km.SoLuongMua [SỐ LƯỢNG MUA_isNum], isnull(km.MUCDANGKY_fk,-1) + 1 as [MỨC ĐĂNG KÝ_isNum], isnull(km.MUCDAT,-1) + 1 as [MỨC ĐẠT_isNum], isnull(km.MUCDUYET,-1)+1 AS [MỨC DUYỆT_isNum], km.thuong as [TIỀN THƯỞNG_isNum]	 " +				
			"\n from DUYETTRAKHUYENMAI_KHACHHANG km " +
			"\n inner join KHACHHANG kh on km.khId = kh.PK_SEQ  " +
			"\n inner join NHAPHANPHOI p on p.PK_SEQ = kh.NPP_FK  " +
			"\n  where km.duyetkm_fk =" + obj.getId();
		}
		else
		query = "\n select p.TEN as [NHÀ PHÂN PHỐI], isnull(dbo.[ListOfTDV](kh.pk_seq),'') as [NHÂN VIÊN BÁN HÀNG],kh.pk_seq [Mã hệ thống], kh.SMARTID AS [MÃ KHÁCH HÀNG], kh.TEN as [TÊN KHÁCH HÀNG]  " +
				"\n , km.DOANHSO [DOANH SỐ_isNum],km.SoLuongMua [SỐ LƯỢNG MUA_isNum], isnull(km.MUCDANGKY_fk,-1) + 1 as [MỨC ĐĂNG KÝ_isNum], isnull(km.MUCDAT,-1) + 1 as [MỨC ĐẠT_isNum], isnull(km.MUCDUYET,-1)+1 AS [MỨC DUYỆT_isNum], km.thuong as [TIỀN THƯỞNG_isNum]	 " +		
				"\n 	, sp.* " +
				"\n from DUYETTRAKHUYENMAI_KHACHHANG km " +
				"\n outer apply " +
				"\n ( " +
				"\n 	select * from  " +
				"\n 	( " +
				"\n 		select  sp.ma + ' - ' + sp.TEN + '_isNum' masp ,ct.soluong  " +
				"\n 		from DUYETTRAKHUYENMAI_KHACHHANG_sanpham ct " +
				"\n 		inner join sanpham sp on sp.PK_SEQ  = ct.sanpham_fk " +
				"\n 		where  ct.duyetkm_fk = km.duyetkm_fk and ct.khId = km.khId " +
				"\n 	)dt  PIVOT ( sum(soluong) FOR masp IN ("+pivot+")) AS pvt  " +
				"\n ) sp " +
				"\n inner join KHACHHANG kh on km.khId = kh.PK_SEQ  " +
				"\n inner join NHAPHANPHOI p on p.PK_SEQ = kh.NPP_FK  " +
				"\n  where km.duyetkm_fk =" + obj.getId();
		
		System.out.println("bc: ngay______" + query);

		return query ;
	}
	private void ExportToExcel(OutputStream out,IDuyettrakhuyenmai obj,String query )throws Exception
	{
		try{ 					
			
			FileInputStream fstream = null;
			com.aspose.cells.Workbook workbook = new com.aspose.cells.Workbook();
			workbook.setFileFormatType(FileFormatType.EXCEL2003);
			TaoBaoCao(workbook, obj, query);			
			workbook.save(out);					

		}catch(Exception ex){
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}
	}
	
	private void TaoBaoCao(com.aspose.cells.Workbook workbook,IDuyettrakhuyenmai obj,String query)throws Exception
	{
		
		try{		

			com.aspose.cells.Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			com.aspose.cells.Cells cells = worksheet.getCells();
			com.aspose.cells.Cell cell = cells.getCell("A1");;	
		   
			cells.setRowHeight(0, 20.0f);
			ReportAPI.getCellStyle(cell, Color.RED, true, 16, "Báo Cáo tích lũy ");
			cell = cells.getCell("A2");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 10, "Ngày tạo : " + this.getDateTime());
	
			
			ResultSet rs = obj.getDb().get(query);
			ResultSetMetaData rsmd = rs.getMetaData();
			int socottrongSql = rsmd.getColumnCount();
			
			int location  = 0;
			int row = 10;
			for( int i =1 ; i <=socottrongSql ; i ++  )
			{
				cell = cells.getCell(row, location + i-1 );
				cell.setValue(rsmd.getColumnName(i).replace("_isNum","").replace("_isNum2","")  );
				ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			}
			
			
			row ++;
			while(rs.next())
			{
				for(int i =1;i <=socottrongSql ; i ++)
				{					
					cell = cells.getCell(row,location + i-1 );
					
					if(rsmd.getColumnName(i).contains("_isNum") )
					{
						int format = 37;
							if(rsmd.getColumnName(i).contains("_isNum2"))	
								format = 10;
						cell.setValue(rs.getDouble(i));
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, format);
					}
					else
					{
						cell.setValue(rs.getString(i));
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					}
				}
				
				++row;
			}
			
			
			
			if(rs!=null)rs.close();		
			
		}catch(Exception ex){

			ex.printStackTrace();
			throw new Exception("Lỗi ! Không có dữ liệu để xuất file !");
		}	
	}
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

}
