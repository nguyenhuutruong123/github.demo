package geso.dms.center.servlets.reports;

import geso.dms.center.beans.theodoithieuhang.ITheodoithieuhang;
import geso.dms.center.beans.theodoithieuhang.imp.Theodoithieuhang;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.SendMail;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.PivotFieldType;
import com.aspose.cells.PivotTable;
import com.aspose.cells.PivotTables;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;


public class TheodoithieuhangSvl extends HttpServlet {	
	   public TheodoithieuhangSvl() {
			super();
		}   	
		
	   String[] recipient;
	   
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");		   
			ITheodoithieuhang obj;
			Utility util = new Utility();
			
		    
		    PrintWriter out = response.getWriter();
		    HttpSession session = request.getSession();
		    obj = (ITheodoithieuhang) new Theodoithieuhang();
		    	
		    String querystring = request.getQueryString();
		    String userId = util.getUserId(querystring);
		    out.println(userId);
		    
		    if (userId.length()==0)
		    	userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
		    
		    String action = util.getAction(querystring);
		    out.println(action);
		    	    
		    String id = util.getId(querystring);
				  
		    if (action.equals("delete")){	   		
			  	Delete(id);
		    }
		    System.out.println("Action:" + action);
		    if(action.equals("id")){
		    	obj.InitEdit(id);
			    session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);
					
				String nextJSP = "/AHF/pages/Center/ThieuhangUpdate.jsp";
				response.sendRedirect(nextJSP);
		    	
		    }else{
		    	session.setAttribute("obj", obj);
		    	session.setAttribute("userId", userId);
				
		    	String nextJSP = "/AHF/pages/Center/Theodoithieuhang.jsp";
		    	response.sendRedirect(nextJSP);
		    }
		    	
		    
		}  	

		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");		    
		    ITheodoithieuhang obj;
		    obj = (ITheodoithieuhang) new Theodoithieuhang();
		    
		    Utility util = new Utility();
		    HttpSession session = request.getSession();
		    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		    String Id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Id")));
		    String bosung = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("bosung")));
		    String ngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngay")));
		    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")); 
		   
		    
		    String sku = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sku")));
    		String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
    		String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
    		
    		
		    dbutils db = new dbutils();
		    
		    if(action.equals("excel")){
		    	OutputStream outPut = response.getOutputStream();
				String userTen = (String) request.getSession().getAttribute("userTen");			
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition", "attachment; "
						+ "filename=TheoDoiThieuHang(tt).xls");
				try {
					ExportToExcel(outPut, userTen,tungay,denngay);
				} catch (Exception e) {
					
				}
		    }

	    	if(action.equals("search")){   	
	    		
	    		obj.setSKU(sku!=null? sku:"");
	    		obj.setDenngay(denngay!=null? denngay:"");
	    		obj.setTungay(tungay!=null? tungay:"");
	    		obj.setuserId(userId!=null? userId:"");
	    		obj.InitSearch();
	    		session.setAttribute("obj", obj);
	    		session.setAttribute("userId", userId);
					
	    		String nextJSP = "/AHF/pages/Center/Theodoithieuhang.jsp";
	    		response.sendRedirect(nextJSP);
	    		
	    	}else{
	    		try{
	    			this.getEmail();
	    			Hashtable ht = this.getThieuhang();
	    			SendMail sm = new SendMail();
	    		
	    			String mesg = "Kinh chao Quy Khach Hang, \n\nSan pham ";

	    			String sql = "update theodoithieuhang set soluongdt ='" + bosung + "', ngaydt='" + ngay + "', ngaysua='" + this.getdate()+ "', nguoisua='"+ userId +"' where pk_seq ='" + Id + "'";
	    			System.out.println(sql);
	    			db.update(sql);
		    
	    			if(ht.containsKey(Id)){
	    				mesg = mesg + ht.get(Id) + " se co hang lai vao ngay '" + ngay + "' \n\n";
	    			}
	    			
	    			mesg = mesg + "Kinh moi Quy khach hang tro lai dat hang vao ngay '" + ngay + "' nhe. \n\nCam on va tran trong\n\nHe thong BEST";
	    			sm.postMail(recipient, "Ngay du dinh co hang ", mesg);

				
	    			obj = (ITheodoithieuhang) new Theodoithieuhang();
		    
	    			session.setAttribute("obj", obj);
	    			session.setAttribute("userId", userId);
			
	    			String nextJSP = "/AHF/pages/Center/Theodoithieuhang.jsp";
	    			response.sendRedirect(nextJSP);
	    		}catch(MessagingException me){
	    			System.out.println(me.toString());
	    		};
	    	}
		}

		private void Delete(String idlist){
			dbutils db = new dbutils();
			String thId = idlist;
		    String sql = "delete from theodoithieuhang where sanpham_fk ='" + thId + "'";
//		    System.out.println(sql);
		    db.update(sql);
		    if(db != null) db.shutDown();
		}		

		private String getdate() {
	        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        Date date = new Date();
	        return dateFormat.format(date);	
		}

		private void getEmail(){
			dbutils db = new dbutils(); 
			ResultSet rs = db.get("select count(email) as num from email");
			 try{
				 rs.next();
				 int n = Integer.valueOf(rs.getString("num")).intValue();
				 if(n > 0){
					 this.recipient = new String[n+1];
					 this.recipient[0] = "" + n;
					 
					 rs = db.get("select email from email");
					 int i = 1;
					 
					 while(rs.next()){
						 this.recipient[i] = rs.getString("email");
						 i++;
					 }				 
				 }
				 if(rs != null) rs.close();
			 }catch(Exception e){
				 if(db!=null) db.shutDown(); 
			 }		 
			if(db!=null) db.shutDown(); 		 
		}

		private Hashtable getThieuhang(){
			dbutils db = new dbutils(); 
			Utility Ult = new Utility();
			ResultSet th = db.get("select a.pk_seq as id, b.ma as masp, b.ten as tensp from theodoithieuhang a inner join sanpham b on b.pk_seq = a.sanpham_fk");
			Hashtable ht = new Hashtable<String, String>();
			ht.put("0", "0");
			try{
				while(th.next()){
					ht.put(th.getString("id"), th.getString("masp")+ " - " + th.getString("tensp"));
				}
				if(th != null) th.close();
			}catch(Exception e){}
			if(db != null) db.shutDown();
			
			return ht;
		}
		private void ExportToExcel(OutputStream out,String userTen,String fromDate,String toDate)throws Exception{
			try{
				Workbook workbook = new Workbook();
				createHeader(workbook, userTen,fromDate,toDate);
				fillData(workbook, fromDate, toDate);
				workbook.save(out,0);
			}catch(Exception ex){
				throw new Exception(ex.getMessage());
			}
		}
		private void createHeader(Workbook workbook,String UserCreate, String fromDate, String toDate)throws Exception{
			try{
				Worksheets worksheets = workbook.getWorksheets();
			    Worksheet worksheet = worksheets.getSheet(0);
			    worksheet.setName("TheoDoiThieuHang");
			    Cell cell;
			    Cells cells = worksheet.getCells();	    
			    cells.setRowHeight(0, 50.0f);	    
			    cell = cells.getCell("A1");	
			    ReportAPI.getCellStyle(cell,Color.RED, true, 16, "THEO DÕI THIẾU HÀNG ");
			    cell = cells.getCell("A2");
			    ReportAPI.getCellStyle(cell,Color.BLUE,true,10,"From : " + fromDate + " To : " + toDate );
			    cell = cells.getCell("A3");
			    ReportAPI.getCellStyle(cell,Color.BLUE,true,10,"Date Create : " + ReportAPI.NOW("dd-MM-yyyy")); 
			    cell = cells.getCell("A4");
			    ReportAPI.getCellStyle(cell,Color.BLUE,true,10,"User : " + UserCreate);
			    
			    cells.setColumnWidth(1, 15.0f);
			    cells.setColumnWidth(2, 30.0f);
			    cells.setColumnWidth(3, 30.0f);
			    cells.setColumnWidth(4, 30.0f);
			    cells.setColumnWidth(5, 30.0f);
			    
			    
			    cell = cells.getCell("A10");		cell.setValue("Mã SP");				ReportAPI.setCellHeader(cell);
			    cell = cells.getCell("B10");		cell.setValue("Tên SP");				ReportAPI.setCellHeader(cell);
			    cell = cells.getCell("C10");		cell.setValue("Kho");				ReportAPI.setCellHeader(cell);
				cell = cells.getCell("D10");		cell.setValue("Thiếu");				ReportAPI.setCellHeader(cell);
				cell = cells.getCell("E10");		cell.setValue("SL dự tính");				ReportAPI.setCellHeader(cell);
				cell = cells.getCell("F10");		cell.setValue("SL thực tế");				ReportAPI.setCellHeader(cell);
				cell = cells.getCell("G10");		cell.setValue("Ngày dự tính");				ReportAPI.setCellHeader(cell);
				cell = cells.getCell("H10");		cell.setValue("Ngày thực tế");			ReportAPI.setCellHeader(cell);
				cell = cells.getCell("I10");		cell.setValue("Trạng Thái");				ReportAPI.setCellHeader(cell);
				
				
			}catch(Exception ex){
				throw new Exception("Khong the tao duoc tua de cho bao cao");
			}
			
		}
		private void fillData(Workbook workbook,String fromDate,String toDate)throws Exception{
			try{
				Worksheets worksheets = workbook.getWorksheets();
			    Worksheet worksheet = worksheets.getSheet(0);
			    Cells cells = worksheet.getCells();
			    Cell cell=null;
			    dbutils db = new dbutils();
			    
			    
				String query ="select sp.pk_seq as id, sp.ma as masp, sp.ten as tensp, " +
								"		ktt.pk_seq as khoId, ktt.ten as kho, tdth.soluongthieu as thieu,tdth.soluongdt as sldutinh, " +
								"		tdth.soluongtt as sltt, tdth.ngaydt, tdth.ngaytt, tdth.trangthai " +
								"from theodoithieuhang tdth " +
								" 	inner join sanpham sp on sp.pk_seq=tdth.sanpham_fk " +
								"	inner join khott ktt on tdth.khott= ktt.pk_seq " +
								"where tdth.ngaydt >= '" + fromDate  +"' and tdth.ngaydt <= '"+ toDate + "'" ;
				
				ResultSet rs = db.get(query);
				int rowIndex = 11;
				while(rs.next()){
					
					cell = cells.getCell("A" + String.valueOf(rowIndex));		
					cell.setValue(rs.getString("masp"));
					
					cell = cells.getCell("B" + String.valueOf(rowIndex));		
					cell.setValue(rs.getString("tensp"));
					
					cell = cells.getCell("C" + String.valueOf(rowIndex));	
					cell.setValue(rs.getString("kho"));
					
					
					cell = cells.getCell("D" + String.valueOf(rowIndex));		
					cell.setValue(rs.getString("thieu"));
					
					
					cell = cells.getCell("E" + String.valueOf(rowIndex));		
					cell.setValue(rs.getString("sldutinh"));
					
					cell = cells.getCell("F" + String.valueOf(rowIndex));		
					cell.setValue(rs.getString("sltt"));
					
					cell = cells.getCell("G" + String.valueOf(rowIndex));		
					cell.setValue(rs.getString("gsbhTen"));
					
					cell = cells.getCell("H" + String.valueOf(rowIndex));		
					cell.setValue(rs.getString("ngaydt"));
					
					
					cell = cells.getCell("I" + String.valueOf(rowIndex));		
					cell.setValue(rs.getString("ngaytt"));
					
									
					++rowIndex;				
				}
				if(rs!=null){
					rs.close();
				}
				if (db != null) db.shutDown();
				/*
				 ReportAPI.setHidden(workbook, 10);
				PivotTables pivotTables = worksheet.getPivotTables();
				String pos = Integer.toString(rowIndex - 1);
				int j = pivotTables.add("=DanhSachDDKD!AA1:AJ" + pos, "B12", "PivotTable");
				PivotTable pivotTable = pivotTables.get(j);
				pivotTable.setRowGrand(true);
				pivotTable.setColumnGrand(true);
				pivotTable.setAutoFormat(true);
				for(int i=0;i<9;++i){
					pivotTable.addFieldToArea(PivotFieldType.ROW,i);	
					pivotTable.getRowFields().get(i).setAutoSubtotals(false);
				}
				
				 */
				
				
				
			}catch(Exception ex){
				throw new Exception("Khong the dien du lieu vao file Excel hoac khong co du lieu");
			}
		}
		
	}