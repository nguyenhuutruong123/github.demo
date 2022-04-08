package geso.dms.center.servlets.reports;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
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

public class BaoCaoLaiGopNppSvl extends HttpServlet {
	   
    public BaoCaoLaiGopNppSvl() {
        super();
        
    }    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
	    IStockintransit obj = new Stockintransit();
	    Utility util = new Utility();
	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    obj.setuserId(userId);
	    String nppId = util.getIdNhapp(userId);
		String nppTen = util.getTenNhaPP();

		obj.setnppId(nppId);
		obj.setnppTen(nppTen);
	    obj.init();
	    
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/AHF/pages/Distributor/BaoCaoLaiNpp.jsp";
		response.sendRedirect(nextJSP);	
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
	    IStockintransit obj = new Stockintransit(); 
	    String userId = (String) session.getAttribute("userId");  
	    String userTen = (String) session.getAttribute("userTen"); 

	    if(userId == null)   
	    	userId ="";
	    
	    obj.setuserId(userId);
	    
	    Utility util = new Utility();
	    String nppId = util.getIdNhapp(userId);
		String nppTen = util.getTenNhaPP();

		obj.setnppId(nppId);
		obj.setnppTen(nppTen);
		
     	
   	 	obj.setuserTen(userTen);
   	 	String thangid=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thangid")));
   	 	if(thangid == null)
   	 	thangid ="";
   	 	obj.setYear(thangid);
		 
		String sql ="";		 
		String action = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")));
		 
		if(action.equals("Taomoi"))
		{
			try{						
				response.setContentType("application/xlsm");
		        response.setHeader("Content-Disposition", "attachment; filename=TINHLAILONHAPP.xlsm");
		        OutputStream out = response.getOutputStream();
		        String query ="";
		        CreatePivotTable(out,obj,query);	
		        
			}catch(Exception ex){
				obj.setMsg(ex.getMessage());
				obj.init();	    
				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);
				String nextJSP = "/AHF/pages/Distributor/BaoCaoLaiNpp.jsp";
				response.sendRedirect(nextJSP);
			}
		} else{			 
			 obj.init();	    
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			String nextJSP = "/AHF/pages/Distributor/BaoCaoLaiNpp.jsp";
			response.sendRedirect(nextJSP);
		}
	}

	
	private void CreatePivotTable(OutputStream out, IStockintransit obj, String query) throws Exception
    {   
	    /* try{*/
	 		//FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\TINHLAILONHAPP.xlsm");	 		
	 		Workbook workbook = new Workbook();
	 		File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\TINHLAILONHAPP.xlsm");
			FileInputStream fstream = new FileInputStream(f) ;
	 		workbook.open(fstream);
	 		
	 		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

	 		CreateStaticHeader(workbook,obj); 
			
	 		FillData(workbook,obj.getFieldShow(),"",obj); 
			
	 		workbook.save(out);
	 		
	 		fstream.close();
	    /* }catch(Exception ex){
	    	 throw new Exception(ex.getMessage());
	     }*/
   }
	
	private void CreateStaticHeader(Workbook workbook,IStockintransit obj)  
	{
		/*try{*/
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
		    
		    ReportAPI.getCellStyle(cell,Color.RED, true, 14, "BÁO CÁO LÃI GỘP NHÀ PHÂN PHỐI");
		            
		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("A3");
		    cell.setValue("MÃ & TÊN NPP: "+obj.getnppId()+ "-" + obj.getnppTen());
		    
		    cell = cells.getCell("A4");
		    cell.setValue("Năm : "+ obj.getYear());
		    
		    
		    cell = cells.getCell("A5"); cell.setValue("Số TT");
		    cell = cells.getCell("A6"); cell.setValue("1");
		    cell = cells.getCell("A7"); cell.setValue("2");
		    cell = cells.getCell("A8"); cell.setValue("3");
		    cell = cells.getCell("A9"); cell.setValue("4");
		    cell = cells.getCell("A10"); cell.setValue("5");
		    
		    cell = cells.getCell("B5"); cell.setValue("Chỉ tiêu");
		    cell = cells.getCell("B6"); cell.setValue("Doanh thu bán hàng & cung cấp dịch vụ");
		    cell = cells.getCell("B7"); cell.setValue("Các khoản giảm trừ doanh thu");
		    cell = cells.getCell("B8"); cell.setValue("Doanh thu thuần về bán hàng & cung cấp dịch vụ");
		    cell = cells.getCell("B9"); cell.setValue("Giá vốn hàng bán");
		    cell = cells.getCell("B10"); cell.setValue("Lãi gộp về bán hàng & cung cấp dịch vụ");
		    
		    cell = cells.getCell("C5"); cell.setValue("Tháng 1");
		    cell = cells.getCell("D5"); cell.setValue("Tháng 2");
		    cell = cells.getCell("E5"); cell.setValue("Tháng 3");
		    cell = cells.getCell("F5"); cell.setValue("Tháng 4");
		    cell = cells.getCell("G5"); cell.setValue("Tháng 5");		    
		    cell = cells.getCell("H5"); cell.setValue("Tháng 6");
		    cell = cells.getCell("I5"); cell.setValue("Tháng 7");
		    cell = cells.getCell("J5"); cell.setValue("Tháng 8");
		    cell = cells.getCell("K5"); cell.setValue("Tháng 9");
		    cell = cells.getCell("L5"); cell.setValue("Tháng 10");
		    cell = cells.getCell("M5"); cell.setValue("Tháng 11");	
		    cell = cells.getCell("N5"); cell.setValue("Tháng 12");	
		    cell = cells.getCell("O5"); cell.setValue("Năm " + obj.getYear());
		    
		    
		/*}catch(Exception ex){
			throw new Exception("Khong the tao duoc Header cho bao cao..."+ ex.toString());
		}*/
	}
	private void FillData(Workbook workbook,String[] fieldShow, String query,IStockintransit obj)throws Exception 
	{
		try{
			Worksheets worksheets = workbook.getWorksheets();
		    Worksheet worksheet = worksheets.getSheet(0);
		    Cells cells = worksheet.getCells();
		    dbutils db = new dbutils();
		   /* String sql=" SELECT NPP_FK,SUBSTRING(DH.NGAYNHAP,1,4),SUBSTRING(DH.NGAYNHAP,6,2) as thang,SUM(SOLUONG * GIAMUA) as doanhso FROM DONHANG DH "+ 
			" INNER JOIN DONHANG_SANPHAM DH_SP ON DH.PK_SEQ=DH_SP.DONHANG_FK "+
			 " WHERE DH.TRANGTHAI=1 AND DH.NGAYNHAP LIKE '"+obj.getYear()+"%' AND DH.NPP_FK="+obj.getnppId()+  
			 "  GROUP BY NPP_FK,SUBSTRING(DH.NGAYNHAP,1,4),SUBSTRING(DH.NGAYNHAP,6,2) order by SUBSTRING(DH.NGAYNHAP,6,2) ";
*/
		    
		    String sql=  " SELECT SUBSTRING(DH1.THANG,1,4) AS NAM ,SUBSTRING(DH1.THANG,6,7) AS THANG, "+
						 "  DH1.NPP_FK,SUM(DOANHSO) AS doanhso FROM  "+
						 " (  "+
						 " SELECT SUBSTRING(DH.NGAYNHAP,1,7) AS THANG, "+ 
						 " DH.NPP_FK, DH.KHO_FK, DH.KBH_FK,    "+
						 " ISNULL(DH_SP.SANPHAM_FK, DH_SP1.SANPHAM_FK) AS SANPHAM_FK, "+    	   
						 " (-1)*ISNULL(DH_SP.SOLUONG, DH_SP1.SOLUONG) * (ISNULL(DH_SP.GIAMUA, DH_SP1.GIAMUA)) AS DOANHSO "+    
						 " FROM  DONHANGTRAVE DH    "+
						 " LEFT OUTER JOIN  DONHANGTRAVE_SANPHAM DH_SP ON DH_SP.DONHANGTRAVE_FK = DH.PK_SEQ "+   	
						 " LEFT OUTER JOIN  DONHANG_SANPHAM DH_SP1 ON DH.DONHANG_FK = DH_SP1.DONHANG_FK     "+
						 " WHERE DH.TRANGTHAI = 3 AND DH.NPP_FK = "+obj.getnppId()+
						 " AND DH.NGAYNHAP  LIKE '"+obj.getYear()+"%'  "+
						 " UNION ALL    "+
						 " SELECT  SUBSTRING(DH.NGAYNHAP,1,7) AS THANG, "+ 
						 " DH.NPP_FK, DH.KHO_FK, DH.KBH_FK,      "+
						 " DH_SP.SANPHAM_FK, DH_SP.SOLUONG *GIAMUA  AS DOANHSO "+ 
						 " FROM DONHANG DH    "+
						 " INNER JOIN DONHANG_SANPHAM  DH_SP ON DH.PK_SEQ = DH_SP.DONHANG_FK "+  	 
						 " WHERE DH.TRANGTHAI = 1 AND DH.NPP_FK = "+obj.getnppId()+" AND DH.NGAYNHAP  LIKE '"+obj.getYear()+"%' "+
						 " ) DH1 "+
						 " GROUP BY SUBSTRING(DH1.THANG,1,4) ,SUBSTRING(DH1.THANG,6,7) , "+
						 " DH1.NPP_FK"; 
		    System.out.println(sql);
		    Cell cell = null;			 
		    ResultSet rs=db.get(sql);
		    double tongdoanhso=0;
					    while (rs.next()) {	
					    	tongdoanhso= tongdoanhso+rs.getFloat("doanhso");
					    	int thang=rs.getInt("thang");
					    	if(thang==1){
					    	cell = cells.getCell("C6");cell.setValue(rs.getFloat("doanhso"));
					    	}else if(thang==2){
					    	cell = cells.getCell("D6");cell.setValue(rs.getFloat("doanhso"));
					    	}else if(thang==3){
					    	cell = cells.getCell("E6");cell.setValue(rs.getFloat("doanhso"));
					    	}else if(thang==4){
					    	cell = cells.getCell("F6");cell.setValue(rs.getFloat("doanhso"));
					    	}else if(thang==5){
					    	cell = cells.getCell("G6");cell.setValue(rs.getFloat("doanhso"));
					    	}else if(thang==6){
					    	cell = cells.getCell("H6");cell.setValue(rs.getFloat("doanhso"));
					    	}else if(thang==7){
					    	cell = cells.getCell("I6");cell.setValue(rs.getFloat("doanhso"));
					    	}else if(thang==8){
					    	cell = cells.getCell("J6");cell.setValue(rs.getFloat("doanhso"));
					    	}else if(thang==9){
					    	cell = cells.getCell("K6");cell.setValue(rs.getFloat("doanhso"));
					    	}else if(thang==10){
					    	cell = cells.getCell("L6");cell.setValue(rs.getFloat("doanhso"));
					    	}else if(thang==11){
					    	cell = cells.getCell("M6");cell.setValue(rs.getFloat("doanhso"));
					    	}else if(thang==12){
					    	cell = cells.getCell("N6");cell.setValue(rs.getFloat("doanhso"));
					    	}
					    }
					    cell = cells.getCell("O6");cell.setValue(tongdoanhso);
					    
					   
					     sql=" select CK.npp_fk,ck.nam,ck.thang,ck.chietkhau  as doanhso from ( "+ 
							 " SELECT NPP_FK,SUBSTRING(DH.NGAYNHAP,1,4) as  nam,SUBSTRING(DH.NGAYNHAP,6,2) as  thang  "+ 
							 " ,SUM(dh_sp.CHIETKHAU) as chietkhau FROM DONHANG DH   "+ 
							 " inner join donhang_sanpham dh_sp on dh.pk_seq=dh_sp.donhang_fk "+ 
							 " WHERE DH.TRANGTHAI=1 AND DH.NGAYNHAP LIKE '"+obj.getYear()+"%' " +
							  " AND DH.NPP_FK="+obj.getnppId()+
							 " GROUP BY NPP_FK,SUBSTRING(DH.NGAYNHAP,1,4), SUBSTRING(DH.NGAYNHAP,6,2) )ck";
					    
					    
					    System.out.println(sql);
					  	 float tongtrave=0;
					     rs=db.get(sql);
					   
								    while (rs.next()) {	
								    	tongtrave= tongtrave+rs.getFloat("doanhso");
								    	int thang=rs.getInt("thang");
								    	if(thang==1){
								    	cell = cells.getCell("C7");cell.setValue(rs.getFloat("doanhso"));
								    	}else if(thang==2){
								    	cell = cells.getCell("D7");cell.setValue(rs.getFloat("doanhso"));
								    	}else if(thang==3){
								    	cell = cells.getCell("E7");cell.setValue(rs.getFloat("doanhso"));
								    	}else if(thang==4){
								    	cell = cells.getCell("F7");cell.setValue(rs.getFloat("doanhso"));
								    	}else if(thang==5){
								    	cell = cells.getCell("G7");cell.setValue(rs.getFloat("doanhso"));
								    	}else if(thang==6){
								    	cell = cells.getCell("H7");cell.setValue(rs.getFloat("doanhso"));
								    	}else if(thang==7){
								    	cell = cells.getCell("I7");cell.setValue(rs.getFloat("doanhso"));
								    	}else if(thang==8){
								    	cell = cells.getCell("J7");cell.setValue(rs.getFloat("doanhso"));
								    	}else if(thang==9){
								    	cell = cells.getCell("K7");cell.setValue(rs.getFloat("doanhso"));
								    	}else if(thang==10){
								    	cell = cells.getCell("L7");cell.setValue(rs.getFloat("doanhso"));
								    	}else if(thang==11){
								    	cell = cells.getCell("M7");cell.setValue(rs.getFloat("doanhso"));
								    	}else if(thang==12){
								    	cell = cells.getCell("N7");cell.setValue(rs.getFloat("doanhso"));
								    	}
								    }
								    cell = cells.getCell("O7");cell.setValue(tongtrave);
					  
								    
								    sql=" SELECT SUBSTRING(DH1.THANG,1,4) AS NAM ,SUBSTRING(DH1.THANG,6,7) AS THANG,DH1.NPP_FK,SUM(DH1.SOLUONG* B.GIAMUA) AS doanhso FROM ( "+
								    	" SELECT SUBSTRING(DH.NGAYNHAP,1,7) AS THANG, "+
								    	" DH.NPP_FK, DH.KHO_FK, DH.KBH_FK,   "+
								    	" ISNULL(DH_SP.SANPHAM_FK, DH_SP1.SANPHAM_FK) AS SANPHAM_FK,    	  "+ 
								    	" (-1)*ISNULL(DH_SP.SOLUONG, DH_SP1.SOLUONG) AS SOLUONG    "+
								    	" FROM  DONHANGTRAVE DH   "+
								    	" LEFT OUTER JOIN  DONHANGTRAVE_SANPHAM DH_SP ON DH_SP.DONHANGTRAVE_FK = DH.PK_SEQ   "+	
								    	" LEFT OUTER JOIN  DONHANG_SANPHAM DH_SP1 ON DH.DONHANG_FK = DH_SP1.DONHANG_FK    "+
								    	" WHERE DH.TRANGTHAI = 3 AND DH.NPP_FK = "+obj.getnppId() +
								    	" AND DH.NGAYNHAP  LIKE '"+obj.getYear()+"%' "+
								    	" UNION ALL   "+
								    	" SELECT  SUBSTRING(DH.NGAYNHAP,1,7) AS THANG, "+
								    	" DH.NPP_FK, DH.KHO_FK, DH.KBH_FK, "+    
								    	" DH_SP.SANPHAM_FK, DH_SP.SOLUONG "+
								    	" FROM DONHANG DH   "+
								    	" INNER JOIN DONHANG_SANPHAM  DH_SP ON DH.PK_SEQ = DH_SP.DONHANG_FK  	 "+
								    	" WHERE DH.TRANGTHAI = 1 AND DH.NPP_FK = '"+obj.getnppId() +"' AND DH.NGAYNHAP  LIKE '"+obj.getYear()+"%'"+
								    	" ) DH1 "+
								    	" INNER JOIN  NHAPP_KHO B ON B.NPP_FK=DH1.NPP_FK AND DH1.SANPHAM_FK=B.SANPHAM_FK "+ 
								    	" AND B.KBH_FK=DH1.KBH_FK AND B.KHO_FK=DH1.KHO_FK "+
								    	" GROUP BY SUBSTRING(DH1.THANG,1,4)  ,SUBSTRING(DH1.THANG,6,7) ,DH1.NPP_FK ";

								    
								    System.out.println(sql);
								  	 float tongloi=0;
								     rs=db.get(sql);
								   
											    while (rs.next()) {	
											    	tongloi= tongloi+rs.getFloat("doanhso");
											    	int thang=rs.getInt("thang");
											    	if(thang==1){
											    	cell = cells.getCell("C9");cell.setValue(rs.getFloat("doanhso"));
											    	}else if(thang==2){
											    	cell = cells.getCell("D9");cell.setValue(rs.getFloat("doanhso"));
											    	}else if(thang==3){
											    	cell = cells.getCell("E9");cell.setValue(rs.getFloat("doanhso"));
											    	}else if(thang==4){
											    	cell = cells.getCell("F9");cell.setValue(rs.getFloat("doanhso"));
											    	}else if(thang==5){
											    	cell = cells.getCell("G9");cell.setValue(rs.getFloat("doanhso"));
											    	}else if(thang==6){
											    	cell = cells.getCell("H9");cell.setValue(rs.getFloat("doanhso"));
											    	}else if(thang==7){
											    	cell = cells.getCell("I9");cell.setValue(rs.getFloat("doanhso"));
											    	}else if(thang==8){
											    	cell = cells.getCell("J9");cell.setValue(rs.getFloat("doanhso"));
											    	}else if(thang==9){
											    	cell = cells.getCell("K9");cell.setValue(rs.getFloat("doanhso"));
											    	}else if(thang==10){
											    	cell = cells.getCell("L9");cell.setValue(rs.getFloat("doanhso"));
											    	}else if(thang==11){
											    	cell = cells.getCell("M9");cell.setValue(rs.getFloat("doanhso"));
											    	}else if(thang==12){
											    	cell = cells.getCell("N9");cell.setValue(rs.getFloat("doanhso"));
											    	}
											    }
											    cell = cells.getCell("O9");cell.setValue(tongloi);   
								    
		
		    
		   
		    
		}catch(Exception ex){
			throw new Exception(ex.getMessage());
			
		}	

	}	
}
