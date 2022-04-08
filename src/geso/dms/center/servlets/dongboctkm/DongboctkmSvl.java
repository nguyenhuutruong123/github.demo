package geso.dms.center.servlets.dongboctkm;

import geso.dms.center.beans.dongboctkm.IDongboctkm;
import geso.dms.center.beans.dongboctkm.IDongboctkmList;
import geso.dms.center.beans.dongboctkm.imp.Dongboctkm;
import geso.dms.center.beans.dongboctkm.imp.DongboctkmList;
import geso.dms.center.util.Utility;
import geso.dms.center.db.sql.dbutils;

import java.io.IOException;
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

import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.Font;
import com.aspose.cells.HorizontalAlignmentType;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class DongboctkmSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   private PrintWriter out;
	public DongboctkmSvl() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		this.out = response.getWriter();
		dbutils db = new dbutils();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
	    	    
	    String id = util.getId(querystring);
	    
	    IDongboctkm nkmBean = new Dongboctkm();
		
	    
	    
       
        session.setAttribute("nkmBean", nkmBean);
   		session.setAttribute("userId", userId);
    	String nextJSP = "/AHF/pages/Center/DongboctkmUpdate.jsp";
   		response.sendRedirect(nextJSP);
       
	}  
	
	
	
	
	
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    HttpSession session = request.getSession();
	    java.io.OutputStream out = response.getOutputStream();
	    
	    IDongboctkm nkmBean = new Dongboctkm();
	    
	    Utility Ult = new Utility();
		String userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
    	String ten = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ten"));
    	nkmBean.setTen(ten);
    	String id = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nkmId"));
    	nkmBean.setId(id);

    	
    	String tungay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"));
    	nkmBean.setTungay(tungay);
    	String denngay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay"));
    	nkmBean.setDenngay(denngay);
    	
    	

    	
    	
    	String ngaytao = getDateTime();
		nkmBean.setNgaytao(ngaytao);
		
		String ngaysua = ngaytao;
		nkmBean.setNgaysua(ngaysua);
		
		String nguoitao = userId;
		nkmBean.setNguoitao(userId);
		
		String nguoisua = nguoitao;
    	nkmBean.setNguoisua(nguoisua);
    	
    	String trangthai;
    	if(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"))!= null)
			trangthai = "1";
		else
			trangthai = "0";
		nkmBean.setTrangthai(trangthai);
			
		
		
		String[] sanpham = request.getParameterValues("sanpham");
		nkmBean.setSanpham(sanpham);
		
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		session.setAttribute("userId", geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		
		if(action.equals("xuatexel"))
		{
	    	try
		    {
		    	response.setContentType("application/vnd.ms-excel");
		        response.setHeader("Content-Disposition", "attachment; filename=DongBoCTKM.xls");
		        
		        Workbook workbook = new Workbook();
		    	
			     CreateStaticHeader(workbook);
			     CreateStaticData(workbook,id,tungay, denngay);
			     
			     //Saving the Excel file
			     workbook.save(out);
		    }
		    catch (Exception ex)
		    {
		    	nkmBean.setMessage("Khong Xuat Ra Excel Duoc");
		    }
	    	
			session.setAttribute("nkmBean", nkmBean);
			session.setAttribute("userId", userId);
			String nextJSP = "/DTL/pages/Center/NhomThuongUpdate.jsp";
			response.sendRedirect(nextJSP);
		}
		else if (action.equals("filter")){		
			nkmBean.UpdateRS();
    		session.setAttribute("nkmBean", nkmBean);
    		session.setAttribute("userId", userId);
    		if(id.length()>0){
    			response.sendRedirect("/AHF/pages/Center/DongboctkmUpdate.jsp");
    		}else{
    			response.sendRedirect("/AHF/pages/Center/DongboctkmUpdate.jsp");
    		}
    		
		}else if(action.equals("saveform")){

			session.setAttribute("userId", nguoitao);
			nkmBean.saveNewNkm();		
			nkmBean.getMessage();
			session.setAttribute("nkmBean", nkmBean);
		    session.setAttribute("userId", userId);
			String nextJSP = "/AHF/pages/Center/DongboctkmUpdate.jsp";
		    response.sendRedirect(nextJSP);
				
				
			
		}
		else{

			session.setAttribute("userId", nguoitao);
			
					session.setAttribute("nkmBean", nkmBean);
		    		session.setAttribute("userId", userId);
					String nextJSP = "/AHF/pages/Center/DongboctkmUpdate.jsp";
		    		response.sendRedirect(nextJSP);
				
				
			
		}
		
	}   	  	    
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	private void CreateStaticHeader(Workbook workbook) 
	{
	    
	    Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	    Style style;
	   	    
	    Cell cell = cells.getCell("A1"); 
	    cell.setValue("ĐỒNG BỘ CTKM");	
	    style = cell.getStyle();
		Font font2 = new Font();	
		font2.setName("Calibri");
		font2.setColor(Color.NAVY);
		font2.setSize(18);
		font2.setBold(true);
		style.setFont(font2);
		style.setHAlignment(TextAlignmentType.LEFT);					
		cell.setStyle(style);
		
		font2 = new Font();	
		font2.setName("Calibri");
		font2.setBold(true);
		font2.setSize(11);
	   
	    cell = cells.getCell("A3");
	    cell.setValue("Ngày tạo : " + this.getDateTime());
	    style = cell.getStyle();
	    style.setFont(font2);
		cell.setStyle(style);
	    
	
		cell = cells.getCell("A5");cell.setValue("Mã NPP");  		    style.setColor(Color.LIME);style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);   
		cell = cells.getCell("B5");cell.setValue("Tên NPP");  		    style.setColor(Color.LIME);style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);   
	    cell = cells.getCell("C5");cell.setValue("Ngày Đơn Hàng");  		        style.setColor(Color.LIME);style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);   
	    cell = cells.getCell("D5");cell.setValue("Loại CTKM");  	style.setColor(Color.LIME);style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);   
	    cell = cells.getCell("E5");cell.setValue("Hình thức");  	style.setColor(Color.LIME);style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);  
	    cell = cells.getCell("F5");cell.setValue("Ngày bắt đầu");  		style.setColor(Color.LIME);		style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);	
	    cell = cells.getCell("G5");cell.setValue("Ngày kết thúc");  	style.setColor(Color.LIME);		style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);	
	    cell = cells.getCell("H5");cell.setValue("Mã CTKM"); 			    style.setColor(Color.LIME);		style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
	    cell = cells.getCell("I5");cell.setValue("Tên CTKM"); 	style.setColor(Color.LIME);	style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
	    cell = cells.getCell("J5");cell.setValue("Mã SP Bán");  				style.setColor(Color.LIME);	style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
	    cell = cells.getCell("K5");cell.setValue("Tên SP bán");  		style.setColor(Color.LIME);	style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
	    cell = cells.getCell("L5");cell.setValue("Mã SP KM");  		style.setColor(Color.LIME);	style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
	    cell = cells.getCell("M5");cell.setValue("Tên SP KM");  		style.setColor(Color.LIME);	style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
	    cell = cells.getCell("N5");cell.setValue("Đơn vị KM");  		style.setColor(Color.LIME);	style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
	    cell = cells.getCell("O5");cell.setValue("Số lượng KM");  		style.setColor(Color.LIME);	style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
	    cell = cells.getCell("P5");cell.setValue("Đơn vị bán");  		style.setColor(Color.LIME);	style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
	    cell = cells.getCell("Q5");cell.setValue("Số lượng bán");  		style.setColor(Color.LIME);	style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
	    worksheet.setName(" Đồng bộ CTKM ");
	}
	
	private void setCellBorderStyle(Cell cell, short align) {
		Style style = cell.getStyle();
		style.setHAlignment(align);
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);
		cell.setStyle(style);
	}
	
	private void CreateStaticData(Workbook workbook,String nspid, String tungay, String denngay) 
	{
		String query="\n select CUSTOMER_NO,CUSTOMER_Name,TRANSACTION_DATE,PROMOTION_TYPE,METHOD,Start_Date_Active,End_Date_Active "
				+ "\n ,PROMOTION_CODE,PROMOTION_NAME,SALES_ITEM_CODE,SALES_ITEM_NAME,PROMOTION_ITEM_CODE "
				+ "\n ,PROMOTION_ITEM_NAME,UOM_PROMOTION,PROMOTION_QUANTITY,UOM_SALE,SALES_QUANTITY from DongBoCTKM a "
				+ "\n where TRANSACTION_DATE >= '"+tungay+"' and TRANSACTION_DATE <= '"+denngay+"'  "
						+ "order by CUSTOMER_NO,TRANSACTION_DATE,PROMOTION_CODE ";
		
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    
		dbutils db = new dbutils();
		ResultSet rs = db.get(query);
		System.out.println("Get san pham :"+query);
		int i = 6;
		if(rs != null)
		{
			try 
			{
				cells.setColumnWidth(0, 12.0f);
				cells.setColumnWidth(1, 30.0f);
				cells.setColumnWidth(2, 16.0f);
				cells.setColumnWidth(3, 16.0f);
				cells.setColumnWidth(4, 16.0f);
				cells.setColumnWidth(5, 20.0f);					
				cells.setColumnWidth(6, 16.0f);
				cells.setColumnWidth(7, 15.0f);
				cells.setColumnWidth(8, 15.0f);
				cells.setColumnWidth(9, 15.0f);
				cells.setColumnWidth(10, 15.0f);
				cells.setColumnWidth(11, 20.0f);
				cells.setColumnWidth(12, 18.0f);
				cells.setColumnWidth(13, 35.0f);
				cells.setColumnWidth(14, 30.0f);
				cells.setColumnWidth(15, 30.0f);
				cells.setColumnWidth(16, 20.0f);
				cells.setColumnWidth(17, 25.0f);
				cells.setColumnWidth(18, 40.0f);
				cells.setColumnWidth(19, 25.0f);
				cells.setColumnWidth(20, 25.0f);
				cells.setColumnWidth(21, 25.0f);
				cells.setColumnWidth(22, 25.0f);
				cells.setColumnWidth(23, 25.0f);
				cells.setColumnWidth(24, 13.0f);
				cells.setColumnWidth(25, 13.0f);
				cells.setColumnWidth(26, 13.0f);
				
				
				Cell cell = null;
				while(rs.next())
				{
					
					cell = cells.getCell("A" + Integer.toString(i));
					cell.setValue(rs.getString("CUSTOMER_NO")); 	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);   
					cell = cells.getCell("B" + Integer.toString(i));
					cell.setValue(rs.getString("CUSTOMER_Name")); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);   
					cell = cells.getCell("C" + Integer.toString(i));
					cell.setValue(rs.getString("TRANSACTION_DATE"));setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);   
					cell = cells.getCell("D" + Integer.toString(i));
					cell.setValue(rs.getFloat("PROMOTION_TYPE"));setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);   
					cell = cells.getCell("E" + Integer.toString(i));
					cell.setValue(rs.getString("METHOD"));setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);   
					cell = cells.getCell("F" + Integer.toString(i));
					cell.setValue(rs.getString("Start_Date_Active"));setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);   
					cell = cells.getCell("G" + Integer.toString(i));
					cell.setValue(rs.getString("End_Date_Active"));setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);   
					cell = cells.getCell("H" + Integer.toString(i));
					cell.setValue(rs.getString("PROMOTION_CODE"));setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);   
					cell = cells.getCell("I" + Integer.toString(i));
					cell.setValue(rs.getString("PROMOTION_NAME"));setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);   
					cell = cells.getCell("J" + Integer.toString(i));
					cell.setValue(rs.getString("SALES_ITEM_CODE"));setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);   
					cell = cells.getCell("K" + Integer.toString(i));
					cell.setValue(rs.getString("SALES_ITEM_NAME"));setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);   
					cell = cells.getCell("L" + Integer.toString(i));
					cell.setValue(rs.getString("PROMOTION_ITEM_CODE"));setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);   
					cell = cells.getCell("M" + Integer.toString(i));
					cell.setValue(rs.getString("PROMOTION_ITEM_NAME"));setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED); 
					cell = cells.getCell("N" + Integer.toString(i));
					cell.setValue(rs.getString("UOM_PROMOTION"));setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);   
					cell = cells.getCell("O" + Integer.toString(i));
					cell.setValue(rs.getFloat("PROMOTION_QUANTITY"));setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED); 
					cell = cells.getCell("P" + Integer.toString(i));
					cell.setValue(rs.getString("UOM_SALE"));setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);   
					cell = cells.getCell("Q" + Integer.toString(i));
					cell.setValue(rs.getFloat("SALES_QUANTITY"));setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED); 

					i++;
				}
				rs.close();
				db.shutDown();
			}
			catch(Exception e){
				e.printStackTrace();
			System.out.println("Loi Nek :"+e.toString());	
			}
		}
	
	}
	
	/*private void  getNkmBeanList(List<INhomthuong> nkmlist){	
    	dbutils db = new dbutils();
	   	String query = "select a.pk_seq, a.ten, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from NHOMSANPHAMCHITIEU a, nhanvien b, nhanvien c where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ and a.type='1' order by pk_seq";
	   	ResultSet rs = db.get(query);
	   	try{	
	   		String[] param = new String[11];
    		INhomthuong nkmBean;
    		while (rs.next()){	    			
				param[0]= rs.getString("pk_seq");
				param[1]= rs.getString("ten");	
				param[2]= rs.getString("diengiai");
				param[3]= rs.getString("trangthai");
				param[4]= rs.getString("ngaytao");
				param[5]= rs.getString("ngaysua");
				param[6]= rs.getString("nguoitao");
				param[7]= rs.getString("nguoisua");			
				nkmBean = new Nhomthuong(param);					
				nkmlist.add(nkmBean);
    		}    		
	   	}catch(Exception e){}
	}
	*/

}
