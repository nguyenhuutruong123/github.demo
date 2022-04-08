package geso.dms.center.servlets.reports;

import geso.dms.center.beans.Router.IDRouter;
import geso.dms.center.beans.Router.imp.Router;
import geso.dms.center.db.sql.dbutils;
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
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.Font;
import com.aspose.cells.PivotFieldDataDisplayFormat;
import com.aspose.cells.PivotFieldType;
import com.aspose.cells.PivotTable;
import com.aspose.cells.PivotTables;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;



public class Routereportnpp extends HttpServlet {
	
       
	
	private static final long serialVersionUID = 1L;
	   Utility util=new Utility();
	   //String userTen = "";
	  // String ddkdId = "";
	  // String nppId = "";
	 //  String tuyenId = "";
	  // boolean bfasle=true;
    public Routereportnpp() {
        super();
        
    }

    private String gettenuser(String userId_){
		dbutils db=new dbutils();
		String sql_getnam="select ten from nhanvien where pk_seq="+ userId_;
		ResultSet rs_tenuser=db.get(sql_getnam);
		if(rs_tenuser!= null){
			try{
			  while(rs_tenuser.next()){
				  return rs_tenuser.getString("ten");
			  }
			}catch(Exception er){
				return "";
			}
		}
		return "";
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
	//	PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String userTen = (String)session.getAttribute("userTen");
		String querystring=request.getQueryString();
		String userId=	util.getUserId(querystring);
		session.setAttribute("tungay", "");
 		session.setAttribute("denngay","");
    	 session.setAttribute("loi", "");
		session.setAttribute("userTen", userTen);
		session.setAttribute("userId", userId);
		IDRouter obj = new Router();
		obj.init();
		session.setAttribute("obj",obj);
		String nextJSP = "/AHF/pages/Center/RouteReport.jsp";
		response.sendRedirect(nextJSP);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
	//	PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
        IDRouter obj = new Router();
        String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
        if(nppId ==null)
        	nppId ="";
        obj.setnppId(nppId);
        
        String ddkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdId")));
        
        if(ddkdId == null)
        	ddkdId ="";
        obj.setddkdId(ddkdId);
        
        String tuyenId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tuyenId")));
        if(tuyenId == null)
        	tuyenId ="";
        obj.settuyenId(tuyenId);
        
        obj.init();
        String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
        
        if(action.equals("export")){
        	OutputStream out = response.getOutputStream(); 
			String userTen = (String)session.getAttribute("userTen");
			
	    	response.setContentType("application/vnd.ms-excel");
	        response.setHeader("Content-Disposition", "attachment; filename=RouteReport.xls");
	        boolean bfasle = false;
			CreatePivotTable(out,response,request, userTen, nppId, tuyenId, ddkdId, bfasle);

        }else{
        	session.setAttribute("obj",obj);
        	String nextJSP = "/AHF/pages/Center/RouteReport.jsp";
        	response.sendRedirect(nextJSP);
        }
	}
	private void CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request, String userTen, String nppId, String tuyenId, String ddkdId, boolean bfasle) throws IOException
    {    //khoi tao de viet pivottable
		//buoc 1
	     Workbook workbook = new Workbook();
	     //Buoc2 tao khung
	    //ham tao khu du lieu
	     CreateStaticHeader(workbook, userTen);
	     //Buoc3 
	     // day du lieu vao
	     CreateStaticData(workbook, nppId, tuyenId, ddkdId, bfasle);

	     workbook.save(out);
   }
	
	private void CreateStaticHeader(Workbook workbook, String UserName) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	   
	   	Style style;
	    //cells.setColumnWidth(0, 200.0f);
	    cells.setRowHeight(0, 20.0f);
	    Cell cell = cells.getCell("A1");  
	    cell.setValue("Sales Rep Route Report - Channel: General Trade");   		      
	    style = cell.getStyle();
	  
	    Font font2 = new Font();
	    font2.setColor(Color.RED);//mau chu
	    font2.setSize(16);// size chu
	    font2.setBold(true);
	    
	    style.setFont(font2); 
	    style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu       
	    cell.setStyle(style);
    
	    cell = cells.getCell("A3");getCellStyle(workbook,"A3",Color.BLUE,true,10);
	    cell.setValue("Reporting Date: " + this.getDateTime());
	     
	    cell = cells.getCell("A4");getCellStyle(workbook,"A4",Color.BLUE,true,10);
	    cell.setValue("Created by:  " + UserName);
	    
	  
	    //tieu de, hang dau tien cua bang du lieu, cell la toa do cua exel
	   
	    cell = cells.getCell("AA1"); cell.setValue("STT");
	    cell = cells.getCell("AB1"); cell.setValue("Region");
	    cell = cells.getCell("AC1"); cell.setValue("Area");
	    cell = cells.getCell("AD1"); cell.setValue("Distributor");
	    cell = cells.getCell("AE1"); cell.setValue("Sales Rep");
	    cell = cells.getCell("AF1"); cell.setValue("Thu");
	    cell = cells.getCell("AG1"); cell.setValue("Customer Code");
	    cell = cells.getCell("AH1"); cell.setValue("Customer Name");
	    cell = cells.getCell("AI1"); cell.setValue("Address");
	    cell = cells.getCell("AJ1"); cell.setValue("Town");
	    cell = cells.getCell("AK1"); cell.setValue("Average Volume");
	    cell = cells.getCell("AL1"); cell.setValue("Outlet Type");
	    cell = cells.getCell("AM1"); cell.setValue("Outlet Location");
	    cell = cells.getCell("AN1"); cell.setValue("Outlet Class");
	    cell = cells.getCell("AO1"); cell.setValue("Frequency");
	    
	    
	    worksheet.setName("Route Report");
	}
	private void CreateStaticData(Workbook workbook, String nppId, String tuyenId, String ddkdId, boolean bfasle) 
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    String st="";
	    int dem =0;
	    
	    if(nppId.length()>0)
	    {
	    	st = st + "tbh.npp_fk ='"+ nppId +"'";
	    }
	    
	    if(tuyenId.length()>0)
	    {
	    	if(st.length()>0)
	    		st = st + " and tbh.ngaylamviec ='"+ tuyenId +"' ";
	    	else
	    		st ="tbh.ngaylamviec ='"+ tuyenId +"' ";
	    }
	    
	    if(ddkdId.length()>0)
	    {
	    	if(st.length()>0)
	    		st = st + " and tbh.ddkd_fk ='"+ ddkdId +"' ";
	    	else
	    		st = st + " tbh.ddkd_fk ='"+ ddkdId +"' ";
	    }
	    if(st.length()>0)
	    	st = " where " + st;
	    //khoi tao ket noi csdl
		
	    String sql = "select v.ten as vung, kv.ten as khuvuc, npp.ten as npp, ddkd.ten as ddkd, tbh.ngaylamviec,kh.pk_seq as Customer_Key,kh.ten as Customer_Name,kh.diachi as Address,qh.ten as province,case when ds.tonggiatri is null then 0 else ds.tonggiatri end as Average_Volume, lch.diengiai as Outlet_Type, "+
	    			 "vt.vitri as Outlet_Location,hch.hang as Outlet_Class,kh_tuyen.tanso as Frequency "+
	    			 "from khachhang kh "+
	    			 "inner join nhaphanphoi npp on npp.pk_seq = kh.npp_fk "+
      				 "inner join khuvuc kv on kv.pk_seq = npp.khuvuc_fk "+
	    		 	 "inner join vung v on v.pk_seq = kv.vung_fk "+
	    			 "left join quanhuyen qh on kh.quanhuyen_fk = qh.pk_seq "+
	    			 "left join loaicuahang lch on lch.pk_seq = kh.lch_fk "+
	    			 "left join vitricuahang vt on vt.pk_seq = kh.vtch_fk "+
	    			 "left join hangcuahang hch on hch.pk_seq = kh.hch_fk "+
	    			 "left join KHACHHANG_TUYENBH kh_tuyen on kh_tuyen.khachhang_fk = kh.pk_seq "+
	    			 "left join (select khachhang_fk,cast(sum(tonggiatri)/3 as int) as tonggiatri from donhang where ngaynhap >'2011-10-01' and ngaynhap < '2011-12-31' group by khachhang_fk) as ds "+
	    			 "on ds.khachhang_fk = kh.pk_seq " +
	    			 "left join tuyenbanhang tbh on tbh.pk_seq = kh_tuyen.tbh_fk " +   
	    			 "inner join daidienkinhdoanh ddkd on ddkd.pk_seq = tbh.ddkd_fk " + st + 
	    			 "order by v.ten, kv.ten, npp.ten, tbh.ngayId";
	    

		System.out.println("Lay Du Lieu :"+sql);
		ResultSet rs = db.get(sql);
		
		int i = 2;
		if(rs!= null)
		{
			
			try 
			{// se do rong cho cac cot se dung
				cells.setColumnWidth(0, 15.0f);
				cells.setColumnWidth(1, 15.0f);
				cells.setColumnWidth(2, 15.0f);
				cells.setColumnWidth(3, 15.0f);
				cells.setColumnWidth(4, 15.0f);			
				cells.setColumnWidth(5, 15.0f);	
				cells.setColumnWidth(6, 15.0f);	
				cells.setColumnWidth(7, 15.0f);
				cells.setColumnWidth(8, 15.0f);
				cells.setColumnWidth(9, 15.0f);
				cells.setColumnWidth(10, 15.0f);
				cells.setColumnWidth(11, 15.0f);
				cells.setColumnWidth(12, 15.0f);
				cells.setColumnWidth(13, 15.0f);
				cells.setColumnWidth(14, 15.0f);
				
				//for(int j = 12; j <= 11; j++)
					//cells.setRowHeight(j, 14.0f);
				
				Cell cell = null;
				int stt = 1;
				while(rs.next())// lap den cuoi bang du lieu
				{
				
					//lay tu co so du lieu, gan bien
					
					String Stt = String.valueOf(stt);
					String Region =rs.getString("vung");
					String Area =rs.getString("khuvuc");
					String Distributor =rs.getString("npp");
					String SalesRep =rs.getString("ddkd");
					String Thu = rs.getString("ngaylamviec");
					String CustomerCode =rs.getString("Customer_Key");
					String CustomerName =rs.getString("Customer_Name");
					String Address =rs.getString("Address");
					String Town = rs.getString("province");
					String AverageVolume =rs.getString("Average_Volume");
					String OutletType = rs.getString("Outlet_Type");
					String OutletLocation = rs.getString("Outlet_Location");
					String OutletClass = rs.getString("Outlet_Class");
					String Frequency = rs.getString("Frequency");
					
					
					//cell = cells.getCell("AA" + Integer.toString(i)); cell.setValue(Channel);
					cell = cells.getCell("AA" + Integer.toString(i)); cell.setValue(Stt);
					cell = cells.getCell("AB" + Integer.toString(i)); cell.setValue(Region);
					cell = cells.getCell("AC" + Integer.toString(i)); cell.setValue(Area);
					cell = cells.getCell("AD" + Integer.toString(i)); cell.setValue(Distributor);
					cell = cells.getCell("AE" + Integer.toString(i)); cell.setValue(SalesRep);
					cell = cells.getCell("AF" + Integer.toString(i)); cell.setValue(Thu);
					cell = cells.getCell("AG" + Integer.toString(i)); cell.setValue(CustomerCode);
					cell = cells.getCell("AH" + Integer.toString(i)); cell.setValue(CustomerName);
					cell = cells.getCell("AI" + Integer.toString(i)); cell.setValue(Address);				
					cell = cells.getCell("AJ" + Integer.toString(i)); cell.setValue(Town);
					cell = cells.getCell("AK" + Integer.toString(i)); cell.setValue(AverageVolume);
					cell = cells.getCell("AL" + Integer.toString(i)); cell.setValue(OutletType);
					cell = cells.getCell("AM" + Integer.toString(i)); cell.setValue(OutletLocation);
					cell = cells.getCell("AN" + Integer.toString(i)); cell.setValue(OutletClass);
					cell = cells.getCell("AO" + Integer.toString(i)); cell.setValue(Frequency);
					
					i++;
					stt++;
				}
			
		
		//xong buoc dua du lieu vao exel
		
		
		//create pivot
		getAn(workbook,49);
		PivotTables pivotTables = worksheet.getPivotTables();

	    //Adding a PivotTable to the worksheet
		String pos = Integer.toString(i-1);	//pos la dong cuoi cung	,A12 la toa do dau cua banng du lieu, Q pos la dong cuoi
	    int index = pivotTables.add("=AA1:AO" + pos,"A8","PivotTableDemo");
        // System.out.println("index:"+index);
	    //Accessing the instance of the newly added PivotTable
	    PivotTable pivotTable = pivotTables.get(index);//truyen index

	    pivotTable.setRowGrand(false);
	    pivotTable.setColumnGrand(false);
	    pivotTable.setAutoFormat(true);
	    //Setting the PivotTable autoformat type.
	    //pivotTable.setAutoFormatType(PivotTableAutoFormatType.REPORT6);
 
	   	  
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 0);	 pivotTable.getRowFields().get(0).setAutoSubtotals(false);  
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 1);	pivotTable.getRowFields().get(1).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 2);	pivotTable.getRowFields().get(2).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 3);	pivotTable.getRowFields().get(3).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 4);	pivotTable.getRowFields().get(4).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldDataDisplayFormat.RUNNING_TOTAL_IN, 5);	//pivotTable.getRowFields().get(5).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 6);	pivotTable.getRowFields().get(5).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 7);	pivotTable.getRowFields().get(6).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 8);	pivotTable.getRowFields().get(7).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 9);	pivotTable.getRowFields().get(8).setAutoSubtotals(false);	    
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 10);	pivotTable.getRowFields().get(9).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 11);	pivotTable.getRowFields().get(10).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 12);	pivotTable.getRowFields().get(11).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 13);	pivotTable.getRowFields().get(12).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 14);	pivotTable.getRowFields().get(13).setAutoSubtotals(false);
	    //pivotTable.getDataField().dragToHide();
	    bfasle=true;
			}
			catch (Exception e){
				bfasle=false;
				e.printStackTrace(); 
				System.out.println("Error : Promotionnalfordist : "+e.toString());
			}
	 		finally{
	 			if(rs != null)
					try {
						rs.close();
					} catch(Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	 		}
		}else{
			bfasle=false;
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
