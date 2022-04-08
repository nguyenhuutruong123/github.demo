package geso.dms.distributor.servlets.reports;


import geso.dms.distributor.beans.reports.IBKTienThuTrongNgay;
import geso.dms.distributor.beans.reports.imp.BKTienThuTrongNgay;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

/**
 * Servlet implementation class BKTienThuTrongNgaySvl
 */

public class BKTienThuTrongNgaySvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Utility util=new Utility();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BKTienThuTrongNgaySvl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
	//	PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String userTen = (String)session.getAttribute("userTen");
		String querystring=request.getQueryString();
		String userId = util.getUserId(querystring);
		session.setAttribute("userTen", userTen);
		session.setAttribute("userId", userId);
		session.setAttribute("tungay", "");
 		session.setAttribute("denngay","");
    	session.setAttribute("loi", "");
		
		String nextJSP = "/AHF/pages/Distributor/BKTienThuTrongNgay.jsp";
		response.sendRedirect(nextJSP);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
	//	PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
        
        String tuNgay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tuNgay")));
        
        String denNgay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denNgay")));
        session.setAttribute("tungay", tuNgay);
        session.setAttribute("denngay", denNgay);
        String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
        
        //if(action.equals("export")){
        	OutputStream out = response.getOutputStream(); 
			String userTen = (String)session.getAttribute("userTen");
			
	    	response.setContentType("application/vnd.ms-excel");
	        response.setHeader("Content-Disposition", "attachment; filename=BKTienThuTrongNgay.xls");
	        String userId = (String) session.getAttribute("userId");
	        BKTienThuTrongNgay obj = new BKTienThuTrongNgay();
	        obj.setUserId(userId);
	        String ngayKS = obj.getNgayKS();
			CreatePivotTable(out,response,request, userId , userTen, tuNgay, denNgay, ngayKS);
			

        //}
		
	}

	private void CreatePivotTable(OutputStream out,
			HttpServletResponse response, HttpServletRequest request,
			String userId, String userTen, String tuNgay, String denNgay, String ngayKS) throws IOException {
		// TODO Auto-generated method stub
		//buoc 1
	     Workbook workbook = new Workbook();
	     //Buoc2 tao khung
	    //ham tao khu du lieu
	     CreateStaticHeader(workbook, userTen, tuNgay, denNgay, ngayKS);
	     //Buoc3 
	     // day du lieu vao
	     CreateStaticData(workbook, userId, tuNgay, denNgay);

	     workbook.save(out);
	     
	}

	private void CreateStaticData(Workbook workbook, String userId,
			String tuNgay, String denNgay) {
		// TODO Auto-generated method stub
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();

    	IBKTienThuTrongNgay obj = new BKTienThuTrongNgay();
    	obj.setTuNgay(tuNgay);
    	obj.setDenNgay(denNgay);
    	
		obj.init();
    	ResultSet rs = obj.getBKTienThuTrongNgay();
    	
    	if(rs != null)
    	{
    		try {
    			tieude(workbook, "6");
    			cells.setColumnWidth(0, 8.0f);
				cells.setColumnWidth(1, 10.0f);
				cells.setColumnWidth(2, 30.0f);
				cells.setColumnWidth(3, 15.0f);
				cells.setColumnWidth(4, 15.0f);			
				cells.setColumnWidth(5, 8.0f);
				cells.setColumnWidth(5, 8.0f);
				int i = 7;
				Cell cell = null;
				String kenh = "";
				double tongKenh = 0;
				double tong = 0;
				double tongsodu=0;
    			while (rs.next()) {
    				
    				if((!rs.getString("kbh").equals(kenh)) && i != 7){
    					cell = cells.getCell("A" + Integer.toString(i)); cell.setValue("Thu công nợ vào quỹ tiền mặt");
						 CreateBorderSetting(workbook,"A" + Integer.toString(i));
						 
						 cell = cells.getCell("D" + Integer.toString(i)); cell.setValue(tongKenh);
						 CreateBorderSetting(workbook,"D" + Integer.toString(i));
						 //tongKenh = 0;
						 i++;
    					
						 cell = cells.getCell("A" + Integer.toString(i)); cell.setValue(kenh);
						 CreateBorderSetting(workbook,"A" + Integer.toString(i));
						 
						 cell = cells.getCell("D" + Integer.toString(i)); cell.setValue(tongKenh);
						 CreateBorderSetting(workbook,"D" + Integer.toString(i));
						 tongKenh = 0;
						 i++;
	    			}
	    				
						kenh = rs.getString("kbh");
					
    				String so = rs.getString("so");
    				String ngay = rs.getString("ngay");
    				String nguoiNop = rs.getString("nguoiNop");
    				double soTien=0;
    				try{
    				soTien = rs.getDouble("soTien");
    				
    				}catch(Exception er){
    					
    				}
    				double sodu=0;
    				try{
    					sodu= rs.getDouble("sodu");
    				}catch(Exception er){
    					
    				}
    				String chot = rs.getString("trangThai").equals("1") ? "đã chốt": "chưa chốt";
    				
					 cell = cells.getCell("A" + Integer.toString(i)); cell.setValue(so);			CreateBorderSetting(workbook,"A" + Integer.toString(i));
					 cell = cells.getCell("B" + Integer.toString(i)); cell.setValue(ngay);		CreateBorderSetting(workbook,"B" + Integer.toString(i));
					 cell = cells.getCell("C" + Integer.toString(i)); cell.setValue(nguoiNop);			CreateBorderSetting(workbook,"C" + Integer.toString(i));
					 cell = cells.getCell("D" + Integer.toString(i)); cell.setValue(soTien);	CreateBorderSetting(workbook,"D" + Integer.toString(i));
					 cell = cells.getCell("E" + Integer.toString(i)); cell.setValue(soTien-sodu);	CreateBorderSetting(workbook,"E" + Integer.toString(i));
					 cell = cells.getCell("F" + Integer.toString(i)); cell.setValue(sodu);	CreateBorderSetting(workbook,"F" + Integer.toString(i));

					 cell = cells.getCell("G" + Integer.toString(i)); cell.setValue(chot);		CreateBorderSetting(workbook,"G" + Integer.toString(i));
					 tongKenh += soTien;
					 tong += soTien;
					 tongsodu+=sodu;
					 
					 i++;
				}
    			Font f = new Font();
    			cell = cells.getCell("A" + Integer.toString(i)); cell.setValue("Thu công nợ vào quỹ tiền mặt");
				 CreateBorderSetting(workbook,"A" + Integer.toString(i));
				 Style style = cell.getStyle();
				 f.setBold(true);
				 style.setFont(f);
				 cell.setStyle(style);
				 
				 cell = cells.getCell("D" + Integer.toString(i)); cell.setValue(tongKenh);
				 CreateBorderSetting(workbook,"D" + Integer.toString(i));
				 cell.setStyle(style);
				 //tongKenh = 0;
				 i++;
    			
    			
    			f.setColor(Color.GREEN);
    			
				 cell = cells.getCell("A" + Integer.toString(i)); cell.setValue(kenh);
				 CreateBorderSetting(workbook,"A" + Integer.toString(i));
				 
				 cell.setStyle(style);
				 
				 cell = cells.getCell("D" + Integer.toString(i)); cell.setValue(tongKenh);
				 CreateBorderSetting(workbook,"D" + Integer.toString(i));
				 cell.setStyle(style);
				 //tongKenh = 0;
				 i++;
    			
    			cell = cells.getCell("A" + Integer.toString(i)); cell.setValue("Tổng cộng");
				 CreateBorderSetting(workbook,"A" + Integer.toString(i));
				 f.setColor(Color.BLACK);
				 style.setFont(f);
				 cell.setStyle(style);
				 
				 cell = cells.getCell("D" + Integer.toString(i)); cell.setValue(tong);
				 CreateBorderSetting(workbook,"D" + Integer.toString(i));
				 f.setColor(Color.BLACK);
				 style.setFont(f);
				 cell.setStyle(style);
				 
				 cell = cells.getCell("E" + Integer.toString(i)); cell.setValue(tong-tongsodu);
				 CreateBorderSetting(workbook,"E" + Integer.toString(i));
				 f.setColor(Color.BLACK);
				 style.setFont(f);
				 cell.setStyle(style);
				 
				 cell = cells.getCell("F" + Integer.toString(i)); cell.setValue(tongsodu);
				 CreateBorderSetting(workbook,"F" + Integer.toString(i));
				 f.setColor(Color.BLACK);
				 style.setFont(f);
				 cell.setStyle(style);
				 
				 
				 i+=3;
				 
				 cell = cells.getCell("B" + Integer.toString(i)); cell.setValue("Kế toán");
				 
				 style = cell.getStyle();
				 f.setBold(true);
				 f.setColor(Color.BLACK);
				 style.setFont(f);
				 cell.setStyle(style);
				 
				 cell = cells.getCell("D" + Integer.toString(i)); cell.setValue("Thủ quỹ");
				 cell.setStyle(style);
				 //CreateBorderSetting(workbook,"B" + Integer.toString(i));
				 
				
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("loi dien du lieu! ");
			}
    	}
		
	}
	
	private void tieude(Workbook workbook,String a) throws IOException{

		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   
	    
   	   
	    Cells cells = worksheet.getCells();
	    Cell cell = cells.getCell("A" + a);  cell.setValue("Số"); 		CreateBorderSetting(workbook,"A"+a); getCellStyle(workbook,"A"+a,Color.BLUE,true,10);
	    cell = cells.getCell("B"  + a); cell.setValue("Ngày");		CreateBorderSetting(workbook,"B"+a);	getCellStyle(workbook,"B"+a,Color.BLUE,true,10);
	    cell = cells.getCell("C"  + a); cell.setValue("Họ tên người nộp");			CreateBorderSetting(workbook,"C"+a);	getCellStyle(workbook,"C"+a,Color.BLUE,true,10);
	    cell = cells.getCell("D"  + a); cell.setValue("Số thu");	CreateBorderSetting(workbook,"D"+a);	getCellStyle(workbook,"D"+a,Color.BLUE,true,10);
	    cell = cells.getCell("E"  + a); cell.setValue("Số tiền đã thanh toán");	CreateBorderSetting(workbook,"E"+a);	getCellStyle(workbook,"E"+a,Color.BLUE,true,10);
	    cell = cells.getCell("F"  + a); cell.setValue("Số tiền dư");	CreateBorderSetting(workbook,"F"+a);	getCellStyle(workbook,"F"+a,Color.BLUE,true,10);
	    cell = cells.getCell("G"  + a); cell.setValue("Chốt");		CreateBorderSetting(workbook,"G"+a);	getCellStyle(workbook,"G"+a,Color.BLUE,true,10);
	}
	
	 public void CreateBorderSetting(Workbook workbook,String fileName) throws IOException
	    {
		 Worksheets worksheets = workbook.getWorksheets();
		    Worksheet worksheet = worksheets.getSheet(0);
	        Cells cells = worksheet.getCells();
	        Cell cell;
	        Style style;

	        cell = cells.getCell(fileName);
	        style = cell.getStyle();

	        //Set border color
	        style.setBorderColor(BorderType.TOP, Color.BLACK);
	        style.setBorderColor(BorderType.BOTTOM, Color.BLACK);
	        style.setBorderColor(BorderType.LEFT, Color.BLACK);
	        style.setBorderColor(BorderType.RIGHT, Color.BLACK);
	        //style.setBorderColor(BorderType.DIAGONAL_DOWN, Color.BLUE);
	        //style.setBorderColor(BorderType.DIAGONAL_UP, Color.BLUE);

	        //Set the cell border type
	        style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
	        style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
	        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
	        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
	        //style.setBorderLine(BorderType.DIAGONAL_DOWN, BorderLineType.DASHED);
	        //style.setBorderLine(BorderType.DIAGONAL_UP, BorderLineType.DASHED);

	        cell.setStyle(style);

	       
	    }

	private void CreateStaticHeader(Workbook workbook, String userTen, String tuNgay, String denNgay, String ngayKS) {
		// TODO Auto-generated method stub
		
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	   
	   	Style style;
	    //cells.setColumnWidth(0, 200.0f);
	    cells.setRowHeight(0, 20.0f);
	    Cell cell = cells.getCell("A1");  
	    cell.setValue(userTen);   		      
	    style = cell.getStyle();
	  
	    Font font2 = new Font();
	    //font2.setColor(Color.RED);//mau chu
	    //font2.setSize(16);// size chu
	    font2.setBold(true);
	    
	    style.setFont(font2); 
	    style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu       
	    cell.setStyle(style);
    
	    font2 = new Font();
	    cell = cells.getCell("C2");
	    cell.setValue("BẢNG KÊ TIỀN THU TRONG NGÀY");
	     		      
	    style = cell.getStyle();
	  
	    
	    //font3.setColor(Color.RED);//mau chu
	    font2.setSize(14);// size chu
	    font2.setBold(true);
	    style.setFont(font2); 
	    style.setHAlignment(TextAlignmentType.CENTER);// canh le cho chu       
	    cell.setStyle(style);
	    
	    font2 = new Font();
	    cell = cells.getCell("C4");
	    cell.setValue("Từ " + tuNgay + " đến " + denNgay + "(ngày khóa sổ: " + ngayKS + ")");
	    
	    font2.setBold(true);
	    style = cell.getStyle();
	    style.setFont(font2);
	    style.setHAlignment(TextAlignmentType.CENTER);// canh le cho chu
	    cell.setStyle(style);

	    worksheet.setName("bkTienThuTrongNgay");
	}

	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh-mm-ss");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	private void getCellStyle(Workbook workbook, String a, Color mau, Boolean dam, int size)
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
		Style style;
		Cell cell = cells.getCell(a); 
		 style = cell.getStyle();
		 style.setHAlignment(TextAlignmentType.CENTER);
	        Font font1 = new Font();
	        font1.setColor(mau);
	        font1.setBold(dam);
	        font1.setSize(size);
	        style.setFont(font1);
	        cell.setStyle(style);
	}

}
