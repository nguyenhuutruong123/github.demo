package geso.dms.center.servlets.reports;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import com.aspose.cells.ConsolidationFunction;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.PivotFieldType;
import com.aspose.cells.PivotTable;
import com.aspose.cells.PivotTableAutoFormatType;
import com.aspose.cells.PivotTables;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

import java.util.Hashtable;


public class KPInpp extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public KPInpp() {
        super();
       
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utility util=new Utility();
	  
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
	//	PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String userTen = (String)session.getAttribute("userTen");
		String querystring=request.getQueryString();
	
	
		IStockintransit obj = new Stockintransit();
		
	
		obj.setuserId(util.getUserId(querystring));
		obj.setuserTen(userTen);
		obj.setnppId(util.getIdNhapp(obj.getuserId()));
		obj.settype("1");
		session.setAttribute("obj", obj);
		
		session.setAttribute("userId", obj.getuserId());
		session.setAttribute("userTen", obj.getuserTen());
		session.setAttribute("util", util);		
		String nextJSP = "/AHF/pages/Center/KPInpp.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OutputStream out = response.getOutputStream(); 
		Utility util=new Utility();
	    String userTen;
	    String tungay;
	    String denngay;
	    String userId;
		IStockintransit obj = new Stockintransit();
		HttpSession session = request.getSession();
		userTen = 	(String)session.getAttribute("userTen");
		tungay	=	util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
		denngay	=	util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
		userId 	= 	(String)session.getAttribute("userId");
		obj.setuserId(userId);
		obj.setuserTen(userTen);
		obj.setdenngay(denngay);
		obj.settungay(tungay);
		obj.setnppId(util.getIdNhapp(obj.getuserId()));
		System.out.println("Tu thang la :"+geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tuthang")));
		String tuthang=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tuthang").length()< 2 ? ("0"+request.getParameter("tuthang")) :request.getParameter("tuthang")) ;
		String toithang=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denthang").length()< 2 ? ("0"+request.getParameter("denthang")) :request.getParameter("denthang")) ;
		obj.setFromMonth(tuthang);
		
		obj.setToMonth(toithang);
			obj.setToYear(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dennam")));
			obj.setFromYear(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tunam")));
		
		 obj.settype(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("typeid")));
		try
		    {
			
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=KPI.xlsm");
			CreatePivotTable(out,response,request, obj);
			
	     }
	    catch (Exception ex)
	    {
	    	obj.setMsg("Loi Khong Tao Duoc Bao Cao : "+ex.toString());
	    	session.setAttribute("obj", obj);		
			session.setAttribute("userId", obj.getuserId());
			session.setAttribute("userTen", obj.getuserTen());
			
			session.setAttribute("util", util);	
	 		String nextJSP = "/AHF/pages/Center/KPInpp.jsp";
	 		response.sendRedirect(nextJSP);
	    }
	    obj.DBclose();
	}
	private void CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request,IStockintransit obj) throws IOException
    {    
		//khoi tao de viet pivottable
		//buoc 1
		String chuoi = getServletContext().getInitParameter("path") + "\\KPINPP.xlsm";
	    FileInputStream fstream = new FileInputStream(chuoi);
	  /*  File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\KPINPP.xlsm");
		FileInputStream fstream = new FileInputStream(f) ;*/
		Workbook workbook = new Workbook();
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		//Buoc2 tao khung
	    //ham tao khu du lieu
	    CreateStaticHeader(workbook,obj);

	    
	    //Buoc3 
	    // day du lieu vao	
	    
	  
	   boolean bfalse= CreateStaticData(workbook, obj);
	    if(bfalse==false){
	    
	    	HttpSession session = request.getSession();
	    	session.setAttribute("obj", obj);		
			session.setAttribute("userId", obj.getuserId());
			session.setAttribute("userTen", obj.getuserTen());
			
	 		String nextJSP = "/AHF/pages/Center/KPInpp.jsp";
	 		response.sendRedirect(nextJSP);
	     }else{
	    	 //Saving the Excel file
	    	 workbook.save(out);
	     }
	    fstream.close();
   }
	
	private void CreateStaticHeader(Workbook workbook,IStockintransit obj) 
	{
		
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");

		Cells cells = worksheet.getCells();

		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("A1");
	    cell.setValue("CHỈ SỐ THÀNH TÍCH CỦA ĐẠI DIỆN KINH DOANH");   	
	    
	    cells.setRowHeight(2, 18.0f);
	
	    cell = cells.getCell("A3");
	    getCellStyle(workbook,"A3",Color.NAVY,true,9);	
	    
	    if(obj.gettype().equals("1")){
	    	 cell.setValue("Từ tháng: " +obj.getFromYear()+"-"+ obj.getFromMonth());  
	    
	    }else{
	    	cell.setValue("Từ ngày: " + obj.gettungay());	
	    }
	    
	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("B3"); getCellStyle(workbook,"B3",Color.NAVY,true,9);	
	   if(obj.gettype().equals("1")){
			cell.setValue("Đến tháng: " + obj.getToYear()+"-"+obj.getToMonth());
	   }else{
	    cell.setValue("Đến ngày: " + obj.getdenngay());    
	   }   
	    
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A4");getCellStyle(workbook,"A4",Color.NAVY,true,9);
	    cell.setValue("Ngày báo cáo: " + this.getdate());
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A5");getCellStyle(workbook,"A5",Color.NAVY,true,9);
	    cell.setValue("Được tạo bởi Nhà phân phối:  " + obj.getuserTen());

	    //tieu de, hang dau tien cua bang du lieu, cell la toa do cua exel
	   
	    cell = cells.getCell("AA1"); cell.setValue("Don Vi Kinh Doanh");
	    cell = cells.getCell("AB1"); cell.setValue("Dai Dien Kinh Doanh");
	   
	    cell = cells.getCell("AC1"); cell.setValue("Ngay");
	  
	    cell = cells.getCell("AD1"); cell.setValue("Cua Hieu Quan Ly");	  
	    cell = cells.getCell("AE1"); cell.setValue("Cua Hieu Co Doanh So");
	    cell = cells.getCell("AF1"); cell.setValue("Tong Don Hang");
	    cell = cells.getCell("AG1"); cell.setValue("Mat Hang Ban Duoc");
	    cell = cells.getCell("AH1"); cell.setValue("Mat Hang Phan Phoi");
	    cell = cells.getCell("AI1"); cell.setValue("Tong Doanh So");
	    cell = cells.getCell("AJ1"); cell.setValue("Tong San Luong");
	    cell = cells.getCell("AK1"); cell.setValue("Don Hang Tra Ve");
	    cell = cells.getCell("AL1"); cell.setValue("F1-2");
	    cell = cells.getCell("AM1"); cell.setValue("F2-1");
	    cell = cells.getCell("AN1"); cell.setValue("F2-2");
	    cell = cells.getCell("AO1"); cell.setValue("F2");
	    cell = cells.getCell("AP1"); cell.setValue("F4");
	    cell = cells.getCell("AQ1"); cell.setValue("F6");
	    cell = cells.getCell("AR1"); cell.setValue("F8");
	 
	}
	private boolean CreateStaticData(Workbook workbook,IStockintransit obj) 
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    
		ResultSet rs1;
		
		if(obj.gettype().equals("1")){
			
			 String denngay=obj.getToYear()+"-"+obj.getToMonth()+"-31";
			   String tungay= obj.getFromYear()+"-"+obj.getFromMonth()+"-01";
		    	 String[] Param={tungay,denngay,obj.getDdkd(),obj.getnppId(),obj.getvungId(),obj.getkhuvucId(),obj.getgsbhId(),obj.getkenhId(),obj.getuserId(),"0",obj.getdvkdId()};
		    
		    	rs1=db.getRsByPro("getRsKpi_Month_report", Param);
		    	 System.out.println(Param[0]);
				 System.out.println(Param[1]);
				 System.out.println(Param[2]);
				 System.out.println(Param[3]);
				 System.out.println(Param[4]);
				 
				 System.out.println(Param[5]);
				 System.out.println(Param[6]);
				 System.out.println(Param[7]);
				 System.out.println(Param[8]);
				 System.out.println(Param[9]);
				 System.out.println(Param[10]);
		}else{
			String[] Param = {obj.gettungay(),obj.getdenngay(), "", obj.getnppId(), "", "", "","",obj.getuserId(),"0",""};
		 rs1 = db.getRsByPro("getRsKpi_report", Param);
		 
		}
			
			 

		

		int i = 2;
		if(rs1!=null)
		{
			
			try 
			{// se do rong cho cac cot se dung
				cells.setColumnWidth(0, 15.0f);
				cells.setColumnWidth(1, 20.0f);
				cells.setColumnWidth(2, 20.0f);
				cells.setColumnWidth(3, 20.0f);
				cells.setColumnWidth(4, 20.0f);			
				cells.setColumnWidth(5, 20.0f);	
				cells.setColumnWidth(6, 20.0f);	
				cells.setColumnWidth(7, 20.0f);
				cells.setColumnWidth(8, 20.0f);
				cells.setColumnWidth(8, 20.0f);
				cells.setRowHeight(12, 20.0f);
				
				Cell cell = null;
								
				Style style;
			while(rs1.next())// lap den cuoi bang du lieu
				{
					String Channel = rs1.getString("KBHTEN");

					String Region = rs1.getString("VUNGTEN");
					
					String Area = rs1.getString("KVTEN");
					
					String Distributor = rs1.getString("NPPTEN");
					
					String SalesRep = rs1.getString("DDKDTEN");
					
					String BusinessUnit =rs1.getString("DVKDTEN");
					
					String Sitecode=rs1.getString("SITECODE");
					
					String Salessup =rs1.getString("GSBHTEN");
									
					//lay tu co so du lieu, gan bien
					
					float SKU =	0;// rs1.getString("NPP");
					
					SKU	=	rs1.getFloat("SKU");
					
					//DOANH SO
					float Volume = rs1.getFloat("DOANHSO");
					
					float Outlet =0;
					
				 	 Outlet = rs1.getFloat("SOCUAHIEU");
						
					float OutletHaveVolume = 0;
					
					OutletHaveVolume = rs1.getFloat("SOCHCODS");
				
					float Order =0;
					
					Order = rs1.getFloat("SODH");

					float	OrderReturn = rs1.getFloat("SODHTRAVE");
					float	SoldSKU = rs1.getFloat("SOLDSKU");				
					String date = rs1.getString("NGAY");
					
					float f12 = 0;
					f12 = rs1.getFloat("F12");
					float f21 = 0;
					f12 = rs1.getFloat("F21");
					float f22 = 0;
					f12 = rs1.getFloat("F22");
					float f2 = 0;
					f12 = rs1.getFloat("F2");
					float f4 = 0;
					f12 = rs1.getFloat("F4");
					float f6 = 0;
					f12 = rs1.getFloat("F6");
					float f8 = 0;
					f12 = rs1.getFloat("F8");
					
					cell = cells.getCell("AA" + Integer.toString(i)); cell.setValue(BusinessUnit); 						//0
					cell = cells.getCell("AB" + Integer.toString(i)); cell.setValue(SalesRep);							//1					
					cell = cells.getCell("AC" + Integer.toString(i)); cell.setValue(date);								//2		
					cell = cells.getCell("AD" + Integer.toString(i)); cell.setValue(Outlet); 			//3

					cell = cells.getCell("AE" + Integer.toString(i)); cell.setValue(OutletHaveVolume);//4

					cell = cells.getCell("AF" + Integer.toString(i)); cell.setValue(Order);			//5

					
					cell = cells.getCell("AG" + Integer.toString(i)); cell.setValue(SoldSKU);			//6

					cell = cells.getCell("AH" + Integer.toString(i)); cell.setValue(SKU);				//7

					
					cell = cells.getCell("AI" + Integer.toString(i)); cell.setValue(Volume);		//8

					cell = cells.getCell("AJ" + Integer.toString(i)); cell.setValue(rs1.getFloat("sanluong"));	
					cell = cells.getCell("AK" + Integer.toString(i)); cell.setValue(OrderReturn);		//9
					
					cell = cells.getCell("AL" + Integer.toString(i)); cell.setValue(f12);
					cell = cells.getCell("AM" + Integer.toString(i)); cell.setValue(f21);
					cell = cells.getCell("AN" + Integer.toString(i)); cell.setValue(f22);
					cell = cells.getCell("AO" + Integer.toString(i)); cell.setValue(f2);
					cell = cells.getCell("AP" + Integer.toString(i)); cell.setValue(f4);
					cell = cells.getCell("AQ" + Integer.toString(i)); cell.setValue(f6);
					cell = cells.getCell("AR" + Integer.toString(i)); cell.setValue(f8);

					
					i++;
				}
				if(rs1!=null){
					rs1.close();
					}
				
				if(db!=null){
					db.shutDown();
				}
			
			if(i==2){
				obj.setMsg("Khong CO Du Lieu TRong Thoi Gian Nay");
				return false;
			}
			

			
		
				   return true;
					
			}
			catch (Exception e){
				System.out.println("Error : Inventory Report : "+e.toString());
				obj.setMsg("Error : Inventory Report : "+e.toString());
				return false;
			}
		}else{
			obj.setMsg("Error : Inventory Report :Khong Co Du Lieu Trong Thoi Gian Nay ");
			return false;
		}
	
	}

	private void getCellStyle(Workbook workbook, String a, Color mau, Boolean dam, int size)
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
		Style style;
		Cell cell = cells.getCell(a); 
		 style = cell.getStyle();
	        Font font1 = new Font();
	        font1.setColor(mau);
	        font1.setBold(dam);
	        font1.setSize(size);
	        style.setFont(font1);
	        cell.setStyle(style);
	}
	
	private void setAn(Workbook workbook,int i)
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	    for(int j = 26; j <= i; j++)
	    { 
	    	cells.hideColumn(j);
	    }
		
	}
	private String getdate() 
	{
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh-mm-ss");
        Date date = new Date();
        return dateFormat.format(date);	
	}
}
