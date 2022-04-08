package geso.dms.center.servlets.reports;

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



public class Dailyseconsalesnpp extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public Dailyseconsalesnpp() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utility util=new Utility();
	    String userTen;
	    String tungay;
	    String denngay;
	    String userId;

		boolean bfasle=true;

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
	//	PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		userTen = (String)session.getAttribute("userTen");
		String querystring=request.getQueryString();
		userId=	util.getUserId(querystring);
		session.setAttribute("tungay", "");
 		session.setAttribute("denngay","");
    	 session.setAttribute("loi", "");
		session.setAttribute("userTen", userTen);
		String nextJSP = "/AHF/pages/Center/DailySecondarySalesnpp.jsp";
		response.sendRedirect(nextJSP);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OutputStream out = response.getOutputStream(); 
		Utility util=new Utility();
	    String userTen;
	    String tungay;
	    String denngay;
	    String userId;

		boolean bfasle=true;

		try
		    {
			HttpSession session = request.getSession();
			 userTen = (String)session.getAttribute("userTen");
		 tungay=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
		 denngay=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
		 userId = (String)session.getAttribute("userId");
	    	response.setContentType("application/vnd.ms-excel");
	        response.setHeader("Content-Disposition", "attachment; filename=DailySecondarySales.xls");
	        CreatePivotTable(out,response,request, tungay, denngay, userTen, bfasle);
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
	private void CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request, String tungay, String denngay, String userTen, boolean bfasle) throws IOException
    {    //khoi tao de viet pivottable
		//buoc 1
	     Workbook workbook = new Workbook();
	     //Buoc2 tao khung
	    //ham tao khu du lieu
	     CreateStaticHeader(workbook, tungay,denngay, userTen);
	     //Buoc3 
	     // day du lieu vao
	     CreateStaticData(workbook, tungay, denngay, userTen, bfasle);
	     if(bfasle==false){
	    	String loi="chua co bao cao trong thoi gian nay, vui long chon lai thoi gian xem bao cao";
	    	
	    	 HttpSession session = request.getSession();
	 		session.setAttribute("loi", loi);
	 		session.setAttribute("tungay", tungay);
	 		session.setAttribute("denngay", denngay);
	    	 session.setAttribute("userTen", userTen);
	 		String nextJSP = "/AHF/pages/Center/DailySecondarySalesnpp.jsp";
	 		response.sendRedirect(nextJSP);
	     }else{
	    	 //Saving the Excel file
	    	 workbook.save(out);
	     }
   }
	
	private void CreateStaticHeader(Workbook workbook, String dateFrom, String dateTo, String UserName) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	   
	    
	    
	   	Style style;
	    //cells.setColumnWidth(0, 200.0f);
	    cells.setRowHeight(0, 20.0f);
	    Cell cell = cells.getCell("A1");  
	    cell.setValue("Daily Secondary Sales Report");   	
	    
	  
	    style = cell.getStyle();
	   
	   Font font2 = new Font();
	   font2.setColor(Color.RED);//mau chu
	   font2.setSize(16);// size chu
	   style.setFont(font2); 
	   style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu       
	   cell.setStyle(style);  
	    cell = cells.getCell("A2"); getCellStyle(workbook,"A2",Color.BLUE,true,10);
	    cell.setValue("From " + dateFrom + "      To " + dateTo);    
	    cell = cells.getCell("A3");getCellStyle(workbook,"A3",Color.BLUE,true,10);
	    cell.setValue("Date Create: " + this.getDateTime());
	    cell = cells.getCell("A4");getCellStyle(workbook,"A4",Color.BLUE,true,10);
	    cell.setValue("User:  " + UserName);
	    
	    //tieu de, hang dau tien cua bang du lieu, cell la toa do cua exel
	   cell = cells.getCell("AA1"); cell.setValue("Channel"); 
	   cell = cells.getCell("AB1"); cell.setValue("Sales Rep");
	   cell = cells.getCell("AC1"); cell.setValue("Brands");
	   cell = cells.getCell("AD1"); cell.setValue("Categoty");
	   cell = cells.getCell("AE1"); cell.setValue("Amount");
	   cell = cells.getCell("AF1"); cell.setValue("SKU");
	   cell = cells.getCell("AG1"); cell.setValue("SKU Code");
	   cell = cells.getCell("AH1"); cell.setValue("piece");
	   cell = cells.getCell("AI1"); cell.setValue("Customer");
	   cell = cells.getCell("AJ1"); cell.setValue("Outlet Class");
	   cell = cells.getCell("AK1"); cell.setValue("Outlet Type");
	   cell = cells.getCell("AL1"); cell.setValue("Date");
	   cell = cells.getCell("AM1"); cell.setValue("Quantity");
	   cell = cells.getCell("AN1"); cell.setValue("Group SKU");
	   cell = cells.getCell("AO1"); cell.setValue("Group Customer");
	   cell = cells.getCell("AP1"); cell.setValue("Customer Code");
	    worksheet.setName("Daily Secondary Sales Report(npp)");
	}
	private void CreateStaticData(Workbook workbook, String tungay, String denngay, String userId, boolean bfasle) 
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    String sql1 = "select a.pk_seq, a.ten, a.sitecode from nhaphanphoi a, nhanvien b where b.dangnhap = a.ma and b.pk_seq ='" + userId + "'";
	    System.out.println(sql1);
		ResultSet rs1 = db.get(sql1);
		String manpp ="";
		  try {
				rs1.next();
				manpp = rs1.getString("pk_seq");
			} catch(Exception e1) {
				
				e1.printStackTrace();
			}
/*	 String sql ="select  v.ten as region, kv.ten as area, npp.ten as distributor, npp.sitecode as distcode, nh.ten as brand,cl.ten as category, sp.ma + '_' + sp.ten as SKU, "+ 
		" sp.ma as SKUcode, ddkd.ten as SalesRep, npp.sitecode + '_' + kh.ten + '( ' + kh.diachi +  ' )' as customer,kh.pk_seq as customercode, vtch.vitri as outletposition, "+
 		" isnull(m.diengiai, 'Chua Xac Dinh') as outlettype, kbh.diengiai as chanel, dh.ngaynhap as offdate, dhsp.soluong as piece, " +
 		" case when dhsp.soluong <0 then (dhsp.giamua *dhsp.soluong - dhsp.chietkhau)* 1.1" +
 		" else (dhsp.giamua *dhsp.soluong + dhsp.chietkhau)* 1.1 end as amount ,"+  
 		" isnull(gsbh.ten, 'Chua xac dinh') as salesuper,"+
 		" case when round((cast((qc.soluong2 * dhsp.soluong) as float) / cast(qc.soluong1 as float)),3) is null then 0 else round((cast((qc.soluong2 * dhsp.soluong) as float) / cast(qc.soluong1 as float)),3) end as quantity," +
 		
 		" isnull(qh.ten, 'Chua xac dinh') as town, "+
 		" isnull(tt.ten, 'Chua xac dinh') as province,"+ 
 		" hch.diengiai as OutletClass, nkh.diengiai as nhomkhachhang, nsp.diengiai as nhomsanpham"+
 		" from donhang dh inner join donhang_sanpham dhsp on dh.pk_seq = dhsp.donhang_fk inner join sanpham sp on dhsp.sanpham_fk = sp.pk_seq "+ 
 		" inner join nhaphanphoi npp on dh.npp_fk = npp.pk_seq " +
 		" left join khuvuc kv on npp.khuvuc_fk = kv.pk_seq " +
 		" left join vung v on kv.vung_fk = v.pk_seq "+
 		" left join nhanhang nh on sp.nhanhang_fk = nh.pk_seq " +
 		" left join chungloai cl on sp.chungloai_fk = cl.pk_seq "+
 		" left join daidienkinhdoanh ddkd on dh.ddkd_fk = ddkd.pk_seq " +
 		" left join khachhang kh on dh.khachhang_fk = kh.pk_seq"+  
 		" left join vitricuahang vtch on kh.vtch_fk = vtch.pk_seq " +
 		" left join kenhbanhang kbh on kh.kbh_fk = kbh.pk_seq"+
 		" left join loaicuahang m on kh.lch_fk = m.pk_seq" +
 		" left join giamsatbanhang gsbh on dh.gsbh_fk = gsbh.pk_seq "+
 		" left join quycach qc on qc.sanpham_fk = sp.pk_seq  " +
 		" left join quanhuyen qh on kh.quanhuyen_fk = qh.pk_seq "+
 		" left join tinhthanh tt on kh.tinhthanh_fk = tt.pk_seq left" +
 		" join hangcuahang hch on kh.hch_fk = hch.pk_seq "+
 		" left join nhomkhachhang_khachhang nkhkh on nkhkh.kh_fk = kh.pk_seq " +
 		" left join nhomkhachhang nkh on nkh.pk_seq = nkhkh.nkh_fk"+ 
 		" left join (select sp_fk, max(nsp_fk) as nhomsp from nhomsanpham_sanpham group by sp_fk) nspsp on nspsp.sp_fk = sp.pk_seq "+
 		" left join nhomsanpham nsp on nsp.pk_seq = nspsp.nhomsp  where dh.trangthai = '1' and dh.npp_fk ='"+manpp+"' and dh.ngaynhap >='"+tungay+"' and dh.ngaynhap <= '"+denngay+"'";//and cast(dh.ngaynhap as datetime) >=cast('"+tungay+"' as datetime) and cast(dh.ngaynhap as datetime) <= cast('"+denngay+"' as datetime)";
*/
			 String sql ="select  v.ten as region, kv.ten as area, npp.ten as distributor, npp.sitecode as distcode, nh.ten as brand,cl.ten as category, sp.ma + '_' + sp.ten as SKU, "+ 
				" sp.ma as SKUcode, ddkd.ten as SalesRep, npp.sitecode + '_' + kh.ten + '( ' + kh.diachi +  ' )' as customer,kh.pk_seq as customercode, vtch.vitri as outletposition, "+
		 		" isnull(m.diengiai, 'Chua Xac Dinh') as outlettype, kbh.diengiai as chanel, dh.ngaynhap as offdate, dhsp.soluong as piece, " +
		 		" case when dhsp.soluong <0 then dhsp.giamua *dhsp.soluong - dhsp.chietkhau " +
		 		                         " else dhsp.giamua *dhsp.soluong + dhsp.chietkhau end as amount ,"+  
		 		" isnull(gsbh.ten, 'Chua xac dinh') as salesuper,"+
		 		" case when round((cast((qc.soluong2 * dhsp.soluong) as float) / cast(qc.soluong1 as float)),3) is null then 0 else round((cast((qc.soluong2 * dhsp.soluong) as float) / cast(qc.soluong1 as float)),3) end as quantity," +
		 		
		 		" isnull(qh.ten, 'Chua xac dinh') as town, "+
		 		" isnull(tt.ten, 'Chua xac dinh') as province,"+ 
		 		" hch.diengiai as OutletClass, nkh.diengiai as nhomkhachhang, nsp.diengiai as nhomsanpham"+
		 		" from donhang dh inner join donhang_sanpham dhsp on dh.pk_seq = dhsp.donhang_fk inner join sanpham sp on dhsp.sanpham_fk = sp.pk_seq "+ 
		 		" inner join nhaphanphoi npp on dh.npp_fk = npp.pk_seq " +
		 		" left join khuvuc kv on npp.khuvuc_fk = kv.pk_seq " +
		 		" left join vung v on kv.vung_fk = v.pk_seq "+
		 		" left join nhanhang nh on sp.nhanhang_fk = nh.pk_seq " +
		 		" left join chungloai cl on sp.chungloai_fk = cl.pk_seq "+
		 		" left join daidienkinhdoanh ddkd on dh.ddkd_fk = ddkd.pk_seq " +
		 		" left join khachhang kh on dh.khachhang_fk = kh.pk_seq"+  
		 		" left join vitricuahang vtch on kh.vtch_fk = vtch.pk_seq " +
		 		" left join kenhbanhang kbh on kh.kbh_fk = kbh.pk_seq"+
		 		" left join loaicuahang m on kh.lch_fk = m.pk_seq" +
		 		" left join giamsatbanhang gsbh on dh.gsbh_fk = gsbh.pk_seq "+
		 		" left join quycach qc on qc.sanpham_fk = sp.pk_seq  " +
		 		" left join quanhuyen qh on kh.quanhuyen_fk = qh.pk_seq "+
		 		" left join tinhthanh tt on kh.tinhthanh_fk = tt.pk_seq left" +
		 		" join hangcuahang hch on kh.hch_fk = hch.pk_seq "+
		 		" left join nhomkhachhang_khachhang nkhkh on nkhkh.kh_fk = kh.pk_seq " +
		 		" left join nhomkhachhang nkh on nkh.pk_seq = nkhkh.nkh_fk"+ 
		 		" left join (select sp_fk, max(nsp_fk) as nhomsp from nhomsanpham_sanpham group by sp_fk) nspsp on nspsp.sp_fk = sp.pk_seq "+
		 		" left join nhomsanpham nsp on nsp.pk_seq = nspsp.nhomsp  where dh.trangthai = '1' and dh.npp_fk ='"+manpp+"' and dh.ngaynhap >='"+tungay+"' and dh.ngaynhap <= '"+denngay+"'";//and cast(dh.ngaynhap as datetime) >=cast('"+tungay+"' as datetime) and cast(dh.ngaynhap as datetime) <= cast('"+denngay+"' as datetime)";


	 System.out.println(sql);
	
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
				cells.setColumnWidth(15, 15.0f);
				cells.setColumnWidth(16, 15.0f);
				cells.setColumnWidth(17, 15.0f);
				cells.setColumnWidth(18, 15.0f);
				cells.setColumnWidth(19, 15.0f);
				cells.setColumnWidth(20, 15.0f);
				cells.setColumnWidth(21, 15.0f);
				cells.setColumnWidth(22, 15.0f);
				
				//set do rong cho dong
				for(int j = 12; j <= 22; j++)
					cells.setRowHeight(j, 14.0f);
				
				Cell cell = null;
				while(rs.next())// lap den cuoi bang du lieu
				{ 
				
					//lay tu co so du lieu, gan bien
					 	String Channel = rs.getString("chanel");
					 	String SalesRep = rs.getString("SalesRep");
					 	String Brands = rs.getString("brand");
					 	String Category = rs.getString("category");
					 	Double Amount = Double.parseDouble(rs.getString("Amount"));						
						//if(Amount < 0) Amount = 0.0;
						String Sku = rs.getString("SKU");
						String SkuCode = rs.getString("SKUcode");
						String Piece = rs.getString("piece");
						String Customer = rs.getString("customer");
						String CustomerCode = rs.getString("customercode"); 
						String OutletClass = rs.getString("OutletClass");
						String OutletType = rs.getString("outlettype");
						String OffDate = rs.getString("offdate");
						float Quantity = Float.parseFloat(rs.getString("quantity"));
						
						String GroupSku = rs.getString("nhomsanpham");
						String GroupCustomer = rs.getString("nhomkhachhang");
						
						
					
					cell = cells.getCell("AA" + Integer.toString(i)); cell.setValue(Channel);
					cell = cells.getCell("AB" + Integer.toString(i)); cell.setValue(SalesRep);
					cell = cells.getCell("AC" + Integer.toString(i)); cell.setValue(Brands);
					cell = cells.getCell("AD" + Integer.toString(i)); cell.setValue(Category);
					cell = cells.getCell("AE" + Integer.toString(i)); cell.setValue(Amount);
					cell = cells.getCell("AF" + Integer.toString(i)); cell.setValue(Sku);
					cell = cells.getCell("AG" + Integer.toString(i)); cell.setValue(SkuCode);
					cell = cells.getCell("AH" + Integer.toString(i)); cell.setValue(Float.parseFloat(Piece));
					cell = cells.getCell("AI" + Integer.toString(i)); cell.setValue(Customer);
					cell = cells.getCell("AJ" + Integer.toString(i)); cell.setValue(OutletClass);
					cell = cells.getCell("AK" + Integer.toString(i)); cell.setValue(OutletType);
					cell = cells.getCell("AL" + Integer.toString(i)); cell.setValue(OffDate);
					cell = cells.getCell("AM" + Integer.toString(i)); cell.setValue(Quantity);
					cell = cells.getCell("AN" + Integer.toString(i)); cell.setValue(GroupSku);
					cell = cells.getCell("AO" + Integer.toString(i)); cell.setValue(GroupCustomer);
					cell = cells.getCell("AP" + Integer.toString(i)); cell.setValue(CustomerCode);
					i++;
				}
			
			
		
		//create pivot
		 getAn(workbook,49);
		PivotTables pivotTables = worksheet.getPivotTables();

	    //Adding a PivotTable to the worksheet
		String pos = Integer.toString(i-1);	//pos la dong cuoi cung	,A12 la toa do dau cua banng du lieu, Q pos la dong cuoi
	    int index = pivotTables.add("=AA1:AP" + pos,"A12","PivotTableDemo");
	     System.out.println("index:"+index);
	    //Accessing the instance of the newly added PivotTable
	    PivotTable pivotTable = pivotTables.get(index);//truyen index

	    pivotTable.setRowGrand(true);
	    pivotTable.setColumnGrand(true);
	    pivotTable.setAutoFormat(true);
	  

	   
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 0); pivotTable.getRowFields().get(0).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 1);	pivotTable.getRowFields().get(1).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 2);	pivotTable.getRowFields().get(2).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 3);	  pivotTable.getRowFields().get(3).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.DATA, 4);  pivotTable.getDataFields().get(0).setDisplayName("Amount");
	    //pivotTable.getDataFields().get(0).setNumber(3);
	    

	    bfasle=true;
			}
			catch (Exception e){
				bfasle=false;
				e.printStackTrace(); 
				System.out.println("Error : Promotionnalfordist : "+e.toString());
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
	    for(int j = 70; j <= i; j++)
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
