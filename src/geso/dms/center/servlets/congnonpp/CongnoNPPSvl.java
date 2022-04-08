package geso.dms.center.servlets.congnonpp;

import geso.dms.center.beans.congnonpp.ICongnonppList;
import geso.dms.center.beans.congnonpp.imp.CongnonppList;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.Utility;
import geso.dms.distributor.db.sql.dbutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

/**
 * Servlet implementation class CongnoNPPSvl
 */
@WebServlet("/CongnoNPPSvl")
public class CongnoNPPSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public CongnoNPPSvl() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		Utility util = new Utility();
		ICongnonppList obj;		 
		   
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");

	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();
	    
	    obj = new CongnonppList();
	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));	    	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String id = util.getId(querystring);
	    	   
	    if (action.equals("delete"))
	    {	   		
		  	if(!Delete(id))
		  		obj.setmsg("Không thể xóa !");
	    }
	    else if(action.equals("finish"))
	    {	    	
	    	if(!Chot(id))
		  		obj.setmsg("Không thể chốt !");
	    }
	   	
	   obj.init("");    	
	   session.setAttribute("obj", obj); 
	   session.setAttribute("userId", userId);
			
       String nextJSP = "/AHF/pages/Center/CongnoNPP.jsp";
	   response.sendRedirect(nextJSP);
	}
	
	boolean Delete(String id)
	{ 
		try 
		{
			dbutils db = new dbutils();	       
			String query = "DELETE CONGNONPP_CT WHERE CONGNONPP_FK = '"+ id +"'";				    	     
	    	if(!db.update(query))
	    	{
	    		return false;
	    	}
	    	
	    	query = "DELETE CONGNONPP WHERE PK_SEQ = '"+ id +"'";				    	     
	    	if(!db.update(query))
	    	{
	    		return false;
	    	}	    	
		} catch(Exception e) {return false; }
		return true;	   
	}
	
	boolean Chot(String id)
	{ 
		try 
		{
			dbutils db = new dbutils();	       
			String query = "UPDATE CONGNONPP SET TRANGTHAI = '1' WHERE PK_SEQ = '"+ id +"'";				    	     
	    	if(!db.update(query))
	    	{
	    		return false;
	    	}
		}catch(Exception e) {return false; }
		return true;	   
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		Utility util = new Utility();
	    ICongnonppList obj = new CongnonppList();
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");	   
	    
	    OutputStream out = response.getOutputStream();
		HttpSession session = request.getSession();
	   
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		obj.setuserId(userId);
		
		obj.setuserTen((String) session.getAttribute("userTen")!=null? (String) session.getAttribute("userTen"):"");
		System.out.println("UserTen : "+ session.getAttribute("userTen"));
		
		String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
		if(tungay ==null)
			tungay ="";
		obj.setTungay(tungay);
		
		String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
		if(denngay ==null)
			denngay ="";
		obj.setDenngay(denngay);
			
		String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));
		if(trangthai ==null)
			trangthai ="";
		obj.setTrangthai(trangthai);
		
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		if(action.equals("search"))
		{   obj.init("search");    
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
				
			String nextJSP = "/AHF/pages/Center/CongnoNPP.jsp";
			response.sendRedirect(nextJSP);
		}
		/*else if(action.equals("xoa"))
		{ 	obj = new ChitieupgList();
		    obj.init("");    
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
				
			String nextJSP = "/AHF/pages/Center/KHDangKyVoucher.jsp";
			response.sendRedirect(nextJSP);
		}*/
		
		else if (action.equals("excel")) 
		{					
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=Thongtinvoucher.xlsm");
			
			String query = setQuery(obj); 

	        try 
	        {
				if(!CreatePivotTable(out, response, request, obj, query))
				{
					response.setContentType("text/html");
				    PrintWriter writer = new PrintWriter(out);
				    writer.print("Xin lỗi không có báo cáo trong thời gian này !");
				    writer.close();
				}
			} 
	        catch (Exception e) 
	        {
				obj.setmsg("Khong the tao bao cao " + e.getMessage());
			}
		}
		else
		{			
			obj = new CongnonppList();
		    obj.init("");    
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
				
			String nextJSP = "/AHF/pages/Center/CongnoNPP.jsp";
			response.sendRedirect(nextJSP);
		}

	}
	
	public String setQuery(ICongnonppList obj)
	{		
		String tk = "";
		String query = "";
		if(obj.getTungay().length()>0)
		{
			tk = tk + " AND A.THOIGIAN >= '"+ obj.getTungay() +"' ";
		}
	   		
		if(obj.getDenngay().length()>0)
		{				
			tk = tk + " AND A.THOIGIAN <= '"+ obj.getDenngay() +"' ";
		}
		
		if(obj.getTrangthai().length()>0)
		{				
			tk = tk + " AND A.TRANGTHAI = '"+ obj.getTrangthai() +"' ";
		}
		
		query = 
			"SELECT A.PK_SEQ, A.THOIGIAN, A.DIENGIAI, DVKD.DONVIKINHDOANH AS DVKDTEN, KBH.DIENGIAI AS KBHTEN, NT.TEN AS NGUOITAO, NS.TEN AS NGUOISUA, A.NGAYTAO, A.NGAYSUA, A.TRANGTHAI " +
			"FROM CHITIEUPG A " +
			"INNER JOIN NHANVIEN NT ON NT.PK_SEQ = A.NGUOITAO " +
			"INNER JOIN NHANVIEN NS ON NS.PK_SEQ = A.NGUOISUA " +
			"INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = A.DVKD_FK " +
			"INNER JOIN KENHBANHANG KBH ON KBH.PK_SEQ = A.KBH_FK " +				
			"WHERE 1=1 " + tk;
	    
	    System.out.println("Query cong no NPP : " + query);
	    return query;   
	}
	
	private boolean CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request,ICongnonppList obj, String query) throws Exception 
	{
		boolean isFillData = false;
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
				
		/*String strfstream = getServletContext().getInitParameter("path") + "\\DanhSachKHVoucher.xlsm";  
		fstream = new FileInputStream(strfstream);		*/
		File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\DanhSachKHVoucher.xlsm");
		fstream = new FileInputStream(f) ;
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		CreateHeader(workbook,obj);
		isFillData = FillData(workbook, obj, query);
   
		if (!isFillData)
		{
			if(fstream != null)
				fstream.close();
			return false;
		}
		
		workbook.save(out);
		fstream.close();
		return true;	
	}
	
	private void CreateHeader(Workbook workbook, ICongnonppList obj)throws Exception
	{	
 		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");
		Cells cells = worksheet.getCells();
		
	    Style style;
	    Font font = new Font();
	    font.setColor(Color.RED);//mau chu
	    font.setSize(16);// size chu
	   	font.setBold(true);	   	
	   	
	    cells.setRowHeight(0, 20.0f);
	    Cell cell = cells.getCell("A1");
	    style = cell.getStyle();
	    style.setFont(font); 
	    style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu 	        
	    
	    String tieude = "DANH SÁCH KHÁCH HÀNG ĐƯỢC ĐĂNG KÝ CHƯƠNG TRÌNH TRƯNG BÀY VOUCHER";	    
	    ReportAPI.getCellStyle(cell,Color.RED, true, 14, tieude);	            	   
					
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A2");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A3");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getuserTen());
	    		
		cell = cells.getCell("DA1");		cell.setValue("MaKhachHang");			ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("DB1");		cell.setValue("TenKhachHang");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("DC1");		cell.setValue("DoiThuCanhTranh");		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("DD1");		cell.setValue("ChuongTrinhTrungBay");	ReportAPI.setCellHeader(cell);
	}
	
	private boolean FillData(Workbook workbook, ICongnonppList obj, String query) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		
		Cells cells = worksheet.getCells();	
		
		for(int i = 0;i < 5; i++)
		{
	    	cells.setColumnWidth(i, 50.0f);	    	
	    }	
		
		ResultSet rs = db.get(query);
		int index = 2;
		
		NumberFormat formatter = new DecimalFormat("#,###,###");
		if (rs != null) 
		{
			try 
			{
				Cell cell = null;
				while (rs.next())
				{					
					cell = cells.getCell("DA" + String.valueOf(index));		cell.setValue(rs.getString("PK_SEQ"));	
					cell = cells.getCell("DB" + String.valueOf(index));		cell.setValue(rs.getString("TENKH"));	
					cell = cells.getCell("DC" + String.valueOf(index));		cell.setValue(rs.getString("DOITHUCANHTRANH"));	
					cell = cells.getCell("DD" + String.valueOf(index));		cell.setValue(rs.getString("CHUONGTRINHTRUNGBAY"));
					index++;					
				}

				if (rs != null)
					rs.close();
				
				if(db != null)
					db.shutDown();
			}
			catch(Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}
		else
		{
			return false;
		}		
		return true;
	}	

}
