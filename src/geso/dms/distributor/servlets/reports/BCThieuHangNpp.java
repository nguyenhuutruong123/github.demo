package geso.dms.distributor.servlets.reports;

import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.report.Ireport;
import geso.dms.distributor.beans.report.imp.Report;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class BCThieuHangNpp extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	private String setQuery(HttpServletRequest request,String tungay,String denngay) 
	{
		String query = "select a.pk_seq, npp.ten as nppTen, kh.ten as khTen, sp.ma as spMa, sp.ten as spTen,  " +
					   "ngayghinhan as ngay, soluonghientai as sl, soluongdat as sldat from thieuhang_npp a"+
					   " inner join nhaphanphoi npp on npp.pk_seq = a.npp_fk"+
					   " inner join khachhang kh on kh.pk_seq = a.kh_fk"+
					   " inner join sanpham sp on sp.pk_seq = a.sp_fk"+
					   " where a.ngayghinhan >= '"+tungay+"' and a.ngayghinhan<= '"+denngay+"' order by a.pk_seq desc" ;
			  
						
			System.out.println("BC Thieu hang Npp : " + query);
		return query;
	}
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
			request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
			HttpSession session = request.getSession();
			Ireport obj = new Report();

		    Utility util = new Utility();
		    String querystring = request.getQueryString();
		    String userId = util.getUserId(querystring);		    		   
		    
		    obj.setuserId(userId);
		    //obj.init();	    
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);	
				
			String nextJSP = "/AHF/pages/Distributor/BCThieuHangNpp.jsp";
			response.sendRedirect(nextJSP);
	}

	
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		Ireport obj = new Report();
		Utility util = new Utility();
		
		String userId = (String) session.getAttribute("userId");
		if (userId == null)
			userId = "";
		obj.setuserId(userId);
		
		String userTen = (String) session.getAttribute("userTen");
		obj.setuserTen(userTen);
		
		String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
		if (nppId == null)
			nppId = "";
		obj.setnppId(nppId);		

		String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays")));
		if (tungay == null)
			tungay = "";
		obj.settungay(tungay);

		String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays")));
		if (denngay == null)
			denngay = "";
		obj.setdenngay(denngay);
		
		/*geso.dms.distributor.util.Utility Ult = new geso.dms.distributor.util.Utility();
		nppId = Ult.getIdNhapp(userId);*/		

		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		if (action.equals("tao")) 
		{
			try 
			{
				request.setCharacterEncoding("utf-8");
			
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BCThieuHangNpp" + this.getPiVotName() + ".xlsm");

				OutputStream out = response.getOutputStream();

				String query = setQuery(request, tungay, denngay);
				//CreatePivotTable(out, obj, fieldsHien, query);
				CreatePivotTable(out, obj, query);
			} 
			catch (Exception ex) 
			{
				request.getSession().setAttribute("errors", ex.getMessage());
			}
		}
		/*
		obj.init();
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/AHF/pages/Distributor/BCDonHangBanTrongKy.jsp";
		response.sendRedirect(nextJSP);
		*/
	}

	private void CreatePivotTable(OutputStream out,Ireport obj, String query) throws Exception {
		try 
		{
			//FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BCThieuHangNpp.xlsm");
//			FileInputStream fstream = new FileInputStream("D:\\Best Stable\\Bibica\\WebContent\\pages\\Templates\\BCDonHangBanTrongKyNPP.xlsm");	
			File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\BCThieuHangNpp.xlsm");
			FileInputStream fstream = new FileInputStream(f) ;
			Workbook workbook = new Workbook();
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		
			CreateHeader(workbook,obj); 
			FillData(workbook, query, obj);			
			workbook.save(out);
			fstream.close();
		} 
		catch (Exception ex) {
			throw new Exception("Error Message: " + ex.getMessage());
		}
	}

	private void CreateHeader(Workbook workbook,Ireport obj) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);	    
	    worksheet.setName("Sheet1");
	    Cells cells = worksheet.getCells();	 
	    
	    cells.setRowHeight(0, 20.0f);	    
	    Cell cell = cells.getCell("A1");	
	    ReportAPI.getCellStyle(cell,Color.RED, true, 16, "BÁO CÁO THIẾU HÀNG NHÀ PHÂN PHỐI");
	    cell = cells.getCell("A2");
	    ReportAPI.getCellStyle(cell,Color.NAVY,true,10,"Từ ngày: " + obj.gettungay() + "  Đến ngày : " + obj.getdenngay());
	    cell = cells.getCell("A3");
	    ReportAPI.getCellStyle(cell,Color.NAVY,true,10,"Ngày tạo : " + this.getDateTime()); 
	    cell = cells.getCell("A4");
	    ReportAPI.getCellStyle(cell,Color.NAVY,true,10,"Người tạo : " + obj.getuserTen());
	    
	    
	    cell = cells.getCell("AA1");		cell.setValue("NPP");					ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AB1");		cell.setValue("KHACH HANG");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AC1");		cell.setValue("MA SAN PHAM");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AD1");		cell.setValue("TEN SAN PHAM");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AE1");		cell.setValue("NGAY");					ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AF1");		cell.setValue("SL HIEN TAI");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AG1");		cell.setValue("SL DAT");				ReportAPI.setCellHeader(cell);			    
	    
	}
	private void FillData(Workbook workbook, String query, Ireport obj) throws Exception
	{
		ResultSet rs = null;
		dbutils db = new dbutils();
		try
		{	
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			Cells cells = worksheet.getCells();
			
			for(int i=0;i<7;++i)
			{
		    	cells.setColumnWidth(i, 15.0f);	    	
		    }	
			 rs = db.get(query);
			int index = 2;
		    Cell cell = null;	    
			while (rs.next())
			{
				cell = cells.getCell("AA" + String.valueOf(index));		cell.setValue(rs.getString("nppTen"));			
				cell = cells.getCell("AB" + String.valueOf(index));		cell.setValue(rs.getString("khTen"));
				cell = cells.getCell("AC" + String.valueOf(index));		cell.setValue(rs.getString("spMa"));
				cell = cells.getCell("AD" + String.valueOf(index));		cell.setValue(rs.getString("spTen"));
				cell = cells.getCell("AE" + String.valueOf(index));		cell.setValue(rs.getString("ngay"));  
				cell = cells.getCell("AF" + String.valueOf(index));		cell.setValue(Float.parseFloat(rs.getString("sl")));
				cell = cells.getCell("AG" + String.valueOf(index));		cell.setValue(Float.parseFloat(rs.getString("sldat")));
				/*cell = cells.getCell("AH" + String.valueOf(index));		cell.setValue(rs.getString("makh"));
				cell = cells.getCell("AI" + String.valueOf(index));		cell.setValue(rs.getString("masp"));
				cell = cells.getCell("AJ" + String.valueOf(index));		cell.setValue(rs.getString("diachi"));
				cell = cells.getCell("AK" + String.valueOf(index));		cell.setValue(Float.parseFloat(rs.getString("chietkhau")));
				cell = cells.getCell("AL" + String.valueOf(index));		cell.setValue(Float.parseFloat(rs.getString("soluong")));
				cell = cells.getCell("AM" + String.valueOf(index));		cell.setValue(Float.parseFloat(rs.getString("dongia")));
				cell = cells.getCell("AN" + String.valueOf(index));		cell.setValue(Float.parseFloat(rs.getString("tongtien")));
				cell = cells.getCell("AO" + String.valueOf(index));		cell.setValue(rs.getString("trangthai"));*/				
				
				index++;
			}
			if(rs!=null){
				rs.close();
			}	
			
			ReportAPI.setHidden(workbook,8);
			  /*
			System.out.println("chi so"+ index);
		
			PivotTables pivotTables = worksheet.getPivotTables();
			String pos = Integer.toString(index-1);	
		    int j = pivotTables.add("=AA1:AN" + pos,"A12","PivotTable");
		    
		     
		    PivotTable pivotTable = pivotTables.get(j);
		    pivotTable.setRowGrand(false);
		    pivotTable.setColumnGrand(true);
		    pivotTable.setAutoFormat(false);  
		    
		    pivotTable.setAutoFormatType(PivotTableAutoFormatType.REPORT6);

		    Hashtable<String, Integer> selected = new Hashtable<String, Integer>();
		    selected.put("Ngay",0);
		    selected.put("Nhan_vien_ban_hang",1);
		    selected.put("Ten_khach_hang",2);
		    selected.put("So_hoa_don",4);
		    selected.put("SKU",5);
		    selected.put("CTKM",6);
		    selected.put("Ten_hang",4);
		    selected.put("Ma_khach_hang",7);
		    selected.put("Dia_chi",9);
		    
		    selected.put("Chiet_khau",10);
			selected.put("So_luong",11);
		    selected.put("Don_gia",12);
		    selected.put("Tong_tien",13);
		    
		  for(String str:obj.getFieldShow())
			{
				int cols = selected.get(str);
				if(cols == 10 || cols == 11 || cols == 12 || cols == 13)
				{
					pivotTable.addFieldToArea(PivotFieldType.DATA, cols);
				}else{
					pivotTable.addFieldToArea(PivotFieldType.ROW, cols);
				}
			}	
			selected.clear();*/
		    			
		}
		catch(Exception ex)
		{
			if(rs != null)
			{
				rs.close();
			}
			
			if(db != null)
				db.shutDown();
			
			throw new Exception(ex.getMessage());
		}
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh-mm-ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	private String getPiVotName()
	{
		String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
	    String name = sdf.format(cal.getTime());
	    name = name.replaceAll("-", "");
	    name = name.replaceAll(" ", "_");
	    name = name.replaceAll(":", "");
	    return "_" + name;
	    
	}
	
	
	
	

}
