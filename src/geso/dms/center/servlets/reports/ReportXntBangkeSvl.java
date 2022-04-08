package geso.dms.center.servlets.reports;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.Utility;
import geso.dms.center.util.WebService;
import geso.dms.distributor.beans.report.Ireport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Range;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

import java.util.*;

public class ReportXntBangkeSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ReportXntBangkeSvl() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		Utility util = new Utility();
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		obj.setuserId(userId);
		obj.init();
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/AHF/pages/Center/ReportXntBangke.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		// OutputStream out = response.getOutputStream();
		if (userId == null)
			userId = "";
		obj.setuserId(userId);
		//geso.dms.distributor.util.Utility Ult = new geso.dms.distributor.util.Utility();

		String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
		obj.setnppId(nppId);
		obj.setuserTen(userTen);

		String vungId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")); // <!---
		if (vungId == null)
			vungId = "";
		obj.setvungId(vungId);


		String khuvucId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")); // <!---
		if (khuvucId == null)
			khuvucId = "";
		obj.setkhuvucId(khuvucId);

		String kenhId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")); // <!---
		if (kenhId == null)
			kenhId = "";
		obj.setkenhId(kenhId);

		String dvkdId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")); // <!---
		if (dvkdId == null)
			dvkdId = "";
		obj.setdvkdId(dvkdId);

		String nhanhangId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhanhangId")); // <!---
		if (nhanhangId == null)
			nhanhangId = "";
		obj.setnhanhangId(nhanhangId);

		String chungloaiId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chungloaiId"));// <!---
		if (chungloaiId == null)
			chungloaiId = "";
		obj.setchungloaiId(chungloaiId);

		String tungay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays")); // <!---
		if (tungay == null)
			tungay = "";
		obj.settungay(tungay);

		String denngay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays"));// <!---
		if (denngay == null)
			denngay = "";
		obj.setdenngay(denngay);

		String khoId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khoId")); // <!---
		if (khoId == null)
			khoId = "";
		obj.setkhoId(khoId);
		obj.setvat("1");
		String instransit = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("instransit")); // <!---
		obj.setdiscount(instransit);

		String sanphamId  = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sanphamId"));
		obj.setsanphamId(sanphamId);

		String dvdlId  = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvdlId"));
		obj.setdvdlId(dvdlId);

		String giatinh = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("giatinh"));
		if(giatinh == null)
			giatinh = "1";

		if (!tungay.equals("") && !denngay.equals("")) 
		{
			String sql = "";

			String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));


			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
		 
			 if (action.equals("tao")){

				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "Attachment; filename=BaoCaoXuatNhapTon(TT)"+this.getPiVotName()+".xlsm");


				OutputStream out = response.getOutputStream();
				try 
				{
					CreatePivotTable(out, response, request, giatinh, obj);
					// PivotTable
				} 
				catch (Exception ex) 
				{
					request.getSession().setAttribute("errors", ex.getMessage());
				}
				return;
			}
		}
		System.out.println("here");
		obj.init();
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/AHF/pages/Center/ReportXntBangke.jsp";
		response.sendRedirect(nextJSP);
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

	private void CreatePivotTable(OutputStream out, HttpServletResponse response, HttpServletRequest request, String giatinh, IStockintransit obj) throws Exception
	{
		try 
		{
			String strfstream;
			System.out.println("_______+_in ra"+obj.getdiscount());
			 
				strfstream = getServletContext().getInitParameter("path") + "\\ReportBangkeXnt.xlsm";			
			 
			FileInputStream fstream = new FileInputStream(strfstream);	
			//File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\BaoCaoXuatNhapTon(TT).xlsm");
			//FileInputStream fstream = new FileInputStream(f) ;

			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

			//CreateHeader(workbook,giatinh, obj,1);
			FillData(workbook, giatinh, obj);

			workbook.save(out);
			fstream.close();

		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
			obj.setMsg("Không có báo cáo trong thời gian này");
			throw new Exception("Error Message: " + ex.getMessage());

		}	
	}
 	private void FillData(Workbook workbook, String giatinh, IStockintransit obj) throws Exception 
	{
		giatinh = "0";
		System.out.println("Đã vào đây r"+giatinh);
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		geso.dms.center.util.Utility Uti_center = new geso.dms.center.util.Utility();
		Cells cells = worksheet.getCells();
		dbutils db = new dbutils();

		ResultSet rs = null;

		System.out.println("NPPID: "+obj.getnppId());
		String query = "";
		 
		ResultSet rsall = null;	

 
	 	Cell	cell = cells.getCell("V3");
		Style style_num=cell.getStyle();
		
		 	cell = cells.getCell("W3");
		Style style_text  = cell.getStyle();
		
		cell = cells.getCell("H16");
		cell.setValue("Từ ngày :" +obj.gettungay()+" Đến ngày :"+obj.getdenngay());
		
		
		  query="select ten,diachi from nhaphanphoi where pk_seq="+obj.getnppId();
		String nppten="";
		String nppdiachi="";
		ResultSet rsnpp=db.get(query);
		if(rsnpp.next()){
			nppten=rsnpp.getString("TEN");
			nppdiachi=rsnpp.getString("diachi");
		}
		rsnpp.close();
		
		cell = cells.getCell("E3");
		cell.setValue(nppten);
		

		cell = cells.getCell("E6");
		cell.setValue(nppdiachi);
		cell = cells.getCell("B10");
		cell.setValue("Ngày in : "+getDateTime());
		
		int index = 22;

		
		


		System.out.println("_+____query___"+ query);
		String param[] =new String[3];
		param[0]=obj.getnppId();
		param[1]=obj.gettungay();
		param[2]=obj.getdenngay();
	 
		rsall = db.getRsByPro("PRO_XNT_BANGKE_CHITIET", param);

 
		if( rsall != null)
			while (rsall.next()) {			
				System.out.println("merce cell");
				
				cell = cells.getCell("C" + String.valueOf(index));
				  
				cell.setStyle(style_text);
			
				
				cell.setValue(rsall.getString("SP"));
				cell = cells.getCell("I" + String.valueOf(index));
				cell.setValue(rsall.getDouble("gia"));
				cell.setStyle(style_num);
				cell = cells.getCell("L" + String.valueOf(index));
				cell.setValue(rsall.getDouble("dauky"));
				cell.setStyle(style_num);
				cell = cells.getCell("M" + String.valueOf(index));
				cell.setValue(rsall.getDouble("nhap"));
				cell.setStyle(style_num);
				cell = cells.getCell("N" + String.valueOf(index));
				cell.setValue(rsall.getDouble("xuat"));
				cell.setStyle(style_num);
				cell = cells.getCell("O" + String.valueOf(index));
				cell.setValue(rsall.getDouble("CUOIKY"));
				cell.setStyle(style_num);
				cell = cells.getCell("P" + String.valueOf(index));
				cell.setValue(rsall.getDouble("GT_dauky"));
				cell.setStyle(style_num);
				cell = cells.getCell("Q" + String.valueOf(index));
				cell.setValue(rsall.getDouble("GT_nhap"));
				cell.setStyle(style_num);
				
				cell = cells.getCell("R" + String.valueOf(index));
				cell.setValue(rsall.getDouble("GT_xuat"));
				cell.setStyle(style_num);
				cell = cells.getCell("S" + String.valueOf(index));
				cell.setValue(rsall.getDouble("GT_CUOIKY"));
				cell.setStyle(style_num);
			 
				 
				index++;

			}rsall.close();
				
			
			cell = cells.getCell("C" + String.valueOf(index+1));
		 
			 
			cell.setValue("Xác nhận SUP");
			
			cell = cells.getCell("O" + String.valueOf(index+1));
		 
			cell.setValue("Xác nhận NPP");

			if(rsall != null) rsall.close();
			if(db != null) db.shutDown();
 
	}




 	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy ");
		Date date = new Date();
		return dateFormat.format(date);
	}

	 

}
