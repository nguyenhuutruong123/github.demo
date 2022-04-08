package geso.dms.center.servlets.reports;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.distributor.beans.reports.imp.Reports;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.Font;
import com.aspose.cells.PivotFieldType;
import com.aspose.cells.PivotTable;
import com.aspose.cells.PivotTables;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;


public class Disproforcustomernpp extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public Disproforcustomernpp() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
	//	PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		Reports obj = new Reports();
		obj.CreateRsCTTB();
		obj.setcttbid("");
		String querystring=request.getQueryString();
		Utility util=new Utility();
		String userId=	util.getUserId(querystring);
		
		session.setAttribute("report", obj);
		String nextJSP = "/AHF/pages/Center/Dispforcustomernpp.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OutputStream out = response.getOutputStream();
		HttpSession session = request.getSession();
		//userTen = (String)session.getAttribute("userTen");
		Reports obj = new Reports();
		
	
		boolean bfasle = true;
		Utility util = new Utility();
		
		String tungay=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
		if(tungay ==null) 
			tungay ="";
		obj.setTuNgay(tungay);
		
		String denngay=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
		if(denngay ==null)
			denngay ="";
		obj.setDenNgay(denngay);
		
		String userId =	(String)session.getAttribute("userId");
		if(userId == null)
			userId ="";
		obj.setUser(userId);
		//session.setAttribute("userTen", userTen);
		
		String[] cttbid = request.getParameterValues("cttbid");
		//lay id cttb da chon ngoai giao dien
		String str3 = "";
		if(cttbid != null)
		{
			for(int i = 0; i < cttbid.length; i++)
				str3 += cttbid[i] + ",";
			if(str3.length() > 0)
				str3 = str3.substring(0, str3.length() - 1);
		}
		obj.setcttbid(str3);
		
		session.setAttribute("userId", userId);
	    try
	    {
	    	//userTen = (String)session.getAttribute("userTen");
	    	
	        System.out.print(userId + " d s ");
	    	response.setContentType("application/vnd.ms-excel");
	        response.setHeader("Content-Disposition", "attachment; filename=DISPLAYPROGRAMAPPLYFORCUSTOMER.xls");
	        
	        CreatePivotTable(out, response, request,obj,bfasle);
	     }
	    catch (Exception ex)
	    {
	        ex.printStackTrace();
	
	        // say sorry
	        response.setContentType("text/html");
	        PrintWriter writer = new PrintWriter(out);
	
	        writer.println("<html>");
	        writer.println("<head>");
	        writer.println("<title>sorry</title>");
	        writer.println("</head>");
	        writer.println("<body>");
	        writer.println("<h1>Xin loi, khong the tao pivot table...</h1>");
	        ex.printStackTrace(writer);
	        writer.println("</body>");
	        writer.println("</html>");
	        writer.close();
	    }
	}
	private void CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request,Reports obj,boolean bfasle) throws IOException
    {    //khoi tao de viet pivottable
		//buoc 1
		
	     Workbook workbook = new Workbook();
	     //Buoc2 tao khung
	    //ham tao khu du lieu
	     
	     
	     
	     CreateStaticHeader(workbook,obj.getTuNgay(),obj.getDenNgay(),obj.getUser());
	     //Buoc3 
	     // day du lieu vao
	  
	     bfasle= CreateStaticData(workbook,obj,bfasle);
	    System.out.println("Bien Nhe: "+bfasle);
	         if(bfasle == false){
		    	String loi="chua co bao cao trong thoi gian nay, vui long chon lai thoi gian xem bao cao";
		    	obj.setLoi(loi);
		    	obj.CreateRsCTTB();
				 HttpSession session = request.getSession();				 
				String nextJSP = "/AHF/pages/Center/Dispforcustomernpp.jsp";
				session.setAttribute("report", obj);
				
				response.sendRedirect(nextJSP);
		     }else{

		    	 workbook.save(out);
		     }
   }
	
	private void CreateStaticHeader(Workbook workbook, String dateFrom, String dateTo, String UserName) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();

		Style style;
		// cells.setColumnWidth(0, 200.0f);
		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("A1");
		cell.setValue("BÁO CÁO TRƯNG BÀY");

		style = cell.getStyle();

		Font font2 = new Font();
		font2.setColor(Color.RED);// mau chu
		font2.setSize(16);// size chu
		style.setFont(font2);
		style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu
		cell.setStyle(style);

		// cell = cells.getCell("B2");
		// getCellStyle(workbook,"B2",Color.BLUE,true,10);

		// cell.setValue("From " + dateFrom + "      To " + dateTo);
		int i=3;
		if(!dateFrom.equals("")){
			cell = cells.getCell("A3");
			getCellStyle(workbook, "A3", Color.NAVY, true, 9);
			cell.setValue("Từ ngày: " + dateFrom);
			
		}
		if(!dateTo .equals("")){
			cell = cells.getCell("B3");
			getCellStyle(workbook, "B3", Color.NAVY, true, 9);
			cell.setValue("Đến ngày: " + dateTo);
			
		}
		
		i=4;
		cell = cells.getCell("A"+i);
		getCellStyle(workbook, "A"+i, Color.NAVY, true, 9);
		cell.setValue("Ngày tạo báo cáo: " + this.getDateTime());
		i++;
		cell = cells.getCell("A"+i);
		getCellStyle(workbook, "A"+i, Color.NAVY, true, 9);
		cell.setValue("Được tạo bởi:  " + UserName);
	  
	    //tieu de, hang dau tien cua bang du lieu, cell la toa do cua exel
	    //cell = cells.getCell("AA1"); cell.setValue("Distributor"); 
	    cell = cells.getCell("AB1"); cell.setValue("Scheme");
	    cell = cells.getCell("AC1"); cell.setValue("Diễn giải");
	    cell = cells.getCell("AD1"); cell.setValue("Mã khách hàng");
	    cell = cells.getCell("AE1"); cell.setValue("Tên khách hàng");
	    cell = cells.getCell("AF1"); cell.setValue("Địa chỉ");	   
	    cell = cells.getCell("AG1"); cell.setValue("Lần trả");
	    cell = cells.getCell("AH1"); cell.setValue("Đăng ký");
	    cell = cells.getCell("AI1"); cell.setValue("Đề nghị");
	    cell = cells.getCell("AJ1"); cell.setValue("Duyệt");
	   
	   
	    worksheet.setName("CHUONG TRINH TRUNG BAY (NPP)");
	}
	private boolean CreateStaticData(Workbook workbook,Reports obj,boolean bfasle) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    
	    //khoi tao ket noi csdl
		dbutils db = new dbutils();
		Utility Utl = new Utility();
		String manpp ="";
		manpp = Utl.getIdNhapp(obj.getUser());
				String dk = "";
				if(obj.getTuNgay()!= "")
					dk += " and cb.ngaytrungbay>='"+ obj.getTuNgay() +"'";
				if(obj.getDenNgay() != "")
					dk += " and cb.ngaytrungbay<='"+ obj.getDenNgay() +"'";
				if(!obj.getcttbid().equals("")){
					dk += " and cb.pk_seq in("+ obj.getcttbid()+")";
				}
				
				String sql ="select distinct cb.scheme as programid,cb.diengiai,kh.smartid as CustomerKey,kh.ten as CustomerName, " +
				" kh.diachi as Address,"+
				" isnull(dntb.lanthanhtoan,1) as PayTime,tbkh.dangky as Allocation ,isnull(dntbkh.xuatdenghi,0) as Request_pay," +
				" isnull(dntbkh.xuatduyet,0)  as Approval"+
				" from cttrungbay cb"+
				" inner join phanbotrungbay pb on pb.cttb_fk = cb.pk_seq"+ 
				" inner join dangkytrungbay dktb on dktb.cttrungbay_fk = cb.pk_seq and dktb.npp_fk = pb.npp_fk"+
				" left join dktrungbay_khachhang tbkh on tbkh.dktrungbay_fk = dktb.pk_seq"+
				" left join denghitratrungbay dntb on dntb.cttrungbay_fk = cb.pk_seq and dntb.npp_fk =dktb.npp_fk  "+
				" left join DENGHITRATB_KHACHHANG dntbkh on dntbkh.denghitratb_fk = dntb.pk_seq  and dntbkh.khachhang_fk = tbkh.khachhang_fk" +
				" inner join khachhang kh on kh.pk_seq = tbkh.khachhang_fk"+
				" where  dktb.npp_fk ='"+manpp+"'"+dk;
				sql=sql+ " order by isnull(dntb.lanthanhtoan,1) ";
				System.out.print("du lieu trung bay :"+sql);
		ResultSet rs = db.get(sql);
		
		int i = 2;
		if(rs != null)
		{
			try 
			{// se do rong cho cac cot se dung
				cells.setColumnWidth(0, 20.0f);
				cells.setColumnWidth(1, 15.0f);
				cells.setColumnWidth(2, 25.0f);
				cells.setColumnWidth(3, 15.0f);
				cells.setColumnWidth(4, 25.0f);
				cells.setColumnWidth(5, 15.0f);
				cells.setColumnWidth(6, 15.0f);
				cells.setColumnWidth(7, 15.0f);
				cells.setColumnWidth(8, 15.0f);
				cells.setColumnWidth(9, 15.0f);
				cells.setColumnWidth(10, 15.0f);
				cells.setColumnWidth(11, 15.0f);
				cells.setColumnWidth(12, 15.0f);
				cells.setColumnWidth(13, 15.0f);
				
				Cell cell = null;
				while(rs.next())// lap den cuoi bang du lieu
				{ 
					String Program_ID = rs.getString("programid");
					String Program_Des = rs.getString("diengiai");
					
					String CustomerKey =rs.getString("CustomerKey");
					String CustomerName =rs.getString("CustomerName");
					String Address = rs.getString("Address");
					String PayTime =rs.getString("PayTime");
					String Allocation=rs.getString("Allocation");
					String Request_pay = rs.getString("Request_pay");				
					String Approval_pay =rs.getString("Approval");
					
					//cell = cells.getCell("AA" + Integer.toString(i)); cell.setValue(Distributor);
					cell = cells.getCell("AB" + Integer.toString(i)); cell.setValue(Program_ID);
					cell = cells.getCell("AC" + Integer.toString(i)); cell.setValue(Program_Des);
					cell = cells.getCell("AD" + Integer.toString(i)); cell.setValue(CustomerKey);
					cell = cells.getCell("AE" + Integer.toString(i)); cell.setValue(CustomerName);
					cell = cells.getCell("AF" + Integer.toString(i)); cell.setValue(Address);
					cell = cells.getCell("AG" + Integer.toString(i)); cell.setValue(PayTime);
					//cell = cells.getCell("AH" + Integer.toString(i)); cell.setValue(Float.parseFloat(Allocation));
					cell = cells.getCell("AH" + Integer.toString(i)); cell.setValue(Float.parseFloat(Allocation));
					cell = cells.getCell("AI" + Integer.toString(i)); cell.setValue(Float.parseFloat(Request_pay));
					cell = cells.getCell("AJ" + Integer.toString(i)); cell.setValue(Float.parseFloat(Approval_pay));
					i++;
				}
				bfasle=true;
				if(rs!=null)rs.close();
				if(db!=null){
					db.shutDown();
				}
			}
			catch (Exception e){
				e.printStackTrace(); 
				return bfasle;
			}
		}else{
			bfasle=false;
			return bfasle;
		}
		System.out.println("I o day :"+i);
		if(i==2){
			bfasle=false;
			return bfasle;
		}else{
				//xong buoc dua du lieu vao exel
				
				//create pivot
				 getAn(workbook,49);
				PivotTables pivotTables = worksheet.getPivotTables();
			
			    //Adding a PivotTable to the worksheet
				String pos = Integer.toString(i-1);	//pos la dong cuoi cung	,A12 la toa do dau cua banng du lieu, Q pos la dong cuoi
			    int index = pivotTables.add("=AB1:AJ" + pos,"A12","PivotTableDemo");
			     System.out.println("index:"+index);
			    //Accessing the instance of the newly added PivotTable
			    PivotTable pivotTable = pivotTables.get(index);//truyen index
			
			    pivotTable.setRowGrand(false);
			    pivotTable.setColumnGrand(false);
			    pivotTable.setAutoFormat(true);
				  
			    pivotTable.addFieldToArea(PivotFieldType.ROW, 0);pivotTable.getRowFields().get(0).setAutoSubtotals(false);	   
			    pivotTable.addFieldToArea(PivotFieldType.ROW, 1);pivotTable.getRowFields().get(1).setAutoSubtotals(true);
			    pivotTable.addFieldToArea(PivotFieldType.ROW, 2);pivotTable.getRowFields().get(2).setAutoSubtotals(false);
			    pivotTable.addFieldToArea(PivotFieldType.ROW, 3);pivotTable.getRowFields().get(3).setAutoSubtotals(false);
			    pivotTable.addFieldToArea(PivotFieldType.ROW, 4);pivotTable.getRowFields().get(4).setAutoSubtotals(false);
			    pivotTable.addFieldToArea(PivotFieldType.ROW, 5);pivotTable.getRowFields().get(5).setAutoSubtotals(false);
			    pivotTable.addFieldToArea(PivotFieldType.DATA, 6);pivotTable.getDataFields().get(0).setDisplayName("Đăng ký");
			    pivotTable.addFieldToArea(PivotFieldType.DATA, 7);pivotTable.getDataFields().get(1).setDisplayName("Đề nghị");
			    pivotTable.addFieldToArea(PivotFieldType.DATA, 8);pivotTable.getDataFields().get(2).setDisplayName("Duyệt");
			    
			    pivotTable.addFieldToArea(PivotFieldType.COLUMN, pivotTable.getDataField());
		}
		return true;
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
	private void getAn(Workbook workbook,int i)
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	    for(int j = 26; j <= i; j++)
	    { 
	    	cells.hideColumn(j);
	    }
		
	}
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh-mm-ss");
        Date date = new Date();
        return dateFormat.format(date);	
	}
}
