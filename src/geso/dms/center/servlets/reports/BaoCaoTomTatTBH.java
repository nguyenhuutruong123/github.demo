package geso.dms.center.servlets.reports;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;

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
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;


public class BaoCaoTomTatTBH extends HttpServlet {
   
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public BaoCaoTomTatTBH() {
        super();
       
    }
    
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		String querystring = request.getQueryString();
		Utility util = new Utility();
		
		obj.setuserId(util.getUserId(querystring));
		obj.setuserTen((String) session.getAttribute("userTen"));
		obj.init();
		session.setAttribute("obj", obj);
		String nextJSP = "/AHF/pages/Center/BaoCaoTomTatTBH.jsp";
		response.sendRedirect(nextJSP);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		OutputStream out = response.getOutputStream();
		Utility util = new Utility();
		
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		
		obj.settungay(util.antiSQLInspection(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays")))));
		obj.setdenngay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays"))));
		
		obj.setuserId(userId!=null? userId:"");
		obj.setuserTen(userTen!=null? userTen:"");	
		
		obj.setvungId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"))):"");
			
		obj.setkhuvucId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId"))):"");
		
		
		obj.setnppId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"))):"");
		
		obj.setgsbhId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("gsbhsId")))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("gsbhsId"))):"");
		
		String action = (String) geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		String nextJSP = "/AHF/pages/Center/BaoCaoTomTatTBH.jsp";
		
		String sql = "";
		if(obj.getvungId().length()>0){
			sql += " and v.pk_seq = '" + obj.getvungId() + "'";
		}
		if(obj.getkhuvucId().length()>0){
			sql += " and kv.pk_seq='"+ obj.getkhuvucId() + "'";
		}
		if(obj.getnppId().length()>0){
			sql +=" and npp.pk_seq='" + obj.getnppId() +"'";
		}
		
		if(obj.getgsbhId().length()>0){
			sql +=" and gsbh.pk_seq='" + obj.getgsbhId()+"'";
		}
		//Kiem tra user co phai user npp ko ? 
		dbutils db=new dbutils();
		 String sqlcheck="select pk_seq from nhanvien where trangthai=1 and phanloai='1' and pk_seq="+userId;
		   ResultSet rs1=db.get(sqlcheck);
		   		try {
		   			if(rs1.next()){
					
		   				sql = " and npp.pk_seq = "+	 util.getIdNhapp(userId);
		   
		   				
					  
				   }else{
					   sql =sql + " and npp.pk_seq in " + util.quyen_npp(obj.getuserId());
				   }
		   			rs1.close();
		   			db.shutDown();
				} catch (Exception e1) {
					
				}
		
				
		
		try{
			
			if (action.equals("create")) {			
		
				
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BacCaoTomTatTBH_Phuong.xlsm");
				
				String query = setQuery(sql, obj);
		        if(!ExportToExcel(out,obj,query)){
		        	response.setContentType("text/html");
		            PrintWriter writer = new PrintWriter(out);
		            writer.print("Xin loi khong co bao cao trong thoi gian nay");
		            writer.close();
		        }
			}else{
				response.sendRedirect(nextJSP);
			}
			
		}catch(Exception ex){
			obj.setMsg(ex.getMessage());
			response.sendRedirect(nextJSP);
		}
		obj.init();
		session.setAttribute("obj", obj);		
	}
	private String setQuery(String sql,IStockintransit obj){
		String query = " select distinct npp.pk_seq as nppid ,gsbh.pk_seq  as gsbhid,npp.ten as tennpp ,gsbh.ten as tengsbh " +
				" ,ddkd.ten as tenddkd,ddkd.pk_seq as ddkdid,ddkd.smartid,tbh.ngayid,tbh.diengiai,'P.'+ isnull( px.ten,'N/A') as Phuong from daidienkinhdoanh ddkd "+
				 " inner join ddkd_gsbh  on ddkd.pk_seq=ddkd_gsbh.ddkd_fk "+
				" inner join giamsatbanhang gsbh on gsbh.pk_seq=ddkd_gsbh.gsbh_fk "+
				" inner join nhaphanphoi npp on npp.pk_seq=ddkd.npp_fk "+ 
				" inner join tuyenbanhang tbh  on tbh.ddkd_fk=ddkd.pk_seq "+
				" inner join khachhang_tuyenbh kh_tbh on kh_tbh.tbh_fk=tbh.pk_seq "+ 
				" inner join khachhang kh on kh.pk_seq=kh_tbh.khachhang_fk " +
				" inner join phuongxa px on px.pk_seq=kh.phuongxa_fk " +
				" inner join khuvuc kv on kv.pk_seq=npp.khuvuc_fk " +
				" inner join vung v on v.pk_seq=kv.vung_fk  "+
				" where 1=1 "+
		 sql + "  order by  npp.ten,gsbh.ten,ddkd.ten, ngayid";
		System.out.println("nhung cau sql : "+query);
		return query;
	}
	private boolean ExportToExcel(OutputStream out, IStockintransit obj,String query)throws Exception {
	
		
	String chuoi=getServletContext().getInitParameter("path") + "\\BaoCaoTomTatTBH.xlsm";
	FileInputStream fstream = new FileInputStream(chuoi);	
	//File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\BaoCaoTomTatTBH.xlsm");
	//FileInputStream fstream = new FileInputStream(f) ;
	Workbook workbook = new Workbook();
	workbook.open(fstream);
	workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		
		
		boolean isFillData = false;
		CreateHeader(workbook, obj);
		isFillData = FillData(workbook, obj, query);
		if (!isFillData)
			return false;
		workbook.save(out);
		fstream.close();
		return true;	
	}


	private boolean FillData(Workbook workbook, IStockintransit obj,
			String query) throws Exception {
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();	
		Cell cell=cells.getCell("A1");
		cells.setRowHeight(80, 40.0f);
		ResultSet rs = db.get(query);
		int i = 8;
		if(rs!=null){
			
			try{
				String idnpp_bk="";
				
				String idddkd_bk="";
				String TenDDKD="";
				cells.setColumnWidth(0, 15.0f);
				cells.setColumnWidth(1, 15.0f);
				cells.setColumnWidth(2, 15.0f);
				cells.setColumnWidth(3, 15.0f);
				cells.setColumnWidth(4, 15.0f);			
				cells.setColumnWidth(5, 15.0f);	
				cells.setColumnWidth(6, 15.0f);	
				cells.setColumnWidth(7, 15.0f);	
				cells.setColumnWidth(8, 20.0f);
				cells.setColumnWidth(9, 20.0f);
				cells.setColumnWidth(10, 20.0f);
				cells.setColumnWidth(11, 20.0f);
				cells.setColumnWidth(12, 20.0f);
				cells.setColumnWidth(13, 20.0f);
				String chuoi2="",chuoi3="",chuoi4="",chuoi5="",chuoi6="",chuoi7="";
				
				
				
				
					Style style=cell.getStyle();
					style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
			        style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
			        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
			        style.setTextWrapped(true);
			        Font font = new Font();
				
					font.setBold(true);
					font.setSize(12);
					style.setFont(font);
			      
					Style style1=cell.getStyle();
					style1.setBorderLine(BorderType.TOP, BorderLineType.THIN);
			        style1.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			        style1.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
			        style1.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
			        style1.setTextWrapped(true);
			       
			        font = new Font();
					font.setSize(10);
					style1.setFont(font);
					
			        
				while(rs.next()){	
				
					String idnpp=rs.getString("nppid");
					//System.out.println("ID NHA PP : "+idnpp);
					String idgsbh=rs.getString("gsbhid");
					String idddkd=rs.getString("ddkdid");
					
					if(!idnpp.equals(idnpp_bk)){
				
						cell = cells.getCell("C" + Integer.toString(i));	
						cell.setValue("GSBH : "+ rs.getString("tengsbh"));	
					
						cell.setStyle(style);
						cell = cells.getCell("A" + Integer.toString(i));	cell.setValue("");
						cell.setStyle(style);
						cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(" ");
						cell.setStyle(style);
						cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(" ");
						cell.setStyle(style);
						cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(" ");
						cell.setStyle(style);
						cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(" ");
						cell.setStyle(style);
						cell = cells.getCell("E" + Integer.toString(i));	cell.setValue("NPP : "+rs.getString("tennpp"));	
						cell.setStyle(style);
						
						i++;
						
						cell = cells.getCell("A" + Integer.toString(i));	cell.setValue("Nhân Viên Bán Hàng");
						cell.setStyle(style);
						cell = cells.getCell("B" + Integer.toString(i));	cell.setValue("THỨ HAI");
						cell.setStyle(style);
						cell = cells.getCell("C" + Integer.toString(i));	cell.setValue("THỨ BA");
						cell.setStyle(style);
						cell = cells.getCell("D" + Integer.toString(i));	cell.setValue("THỨ TƯ");
						cell.setStyle(style);
						cell = cells.getCell("E" + Integer.toString(i));	cell.setValue("THỨ NĂM");
						cell.setStyle(style);
						cell = cells.getCell("F" + Integer.toString(i));	cell.setValue("THỨ SÁU");
						cell.setStyle(style);
						cell = cells.getCell("G" + Integer.toString(i));	cell.setValue("THỨ BẢY");
						cell.setStyle(style);
						
						idnpp_bk=idnpp;
						if(i>10){
							i++;
						}
						
					}
						if(!idddkd.equals(idddkd_bk)){
							//System.out.println("ID DDKD : "+idddkd +" Thu tu :" +i);		
							i++;
							idddkd_bk=idddkd;
							if(i>10){
								System.out.println("I : "+ (i-1));
								cell = cells.getCell("A" + Integer.toString(i-1));	cell.setValue(TenDDKD );
								cell.setStyle(style);
								cell = cells.getCell("B" + Integer.toString(i-1));	cell.setValue(catchuoi(chuoi2));
								cell.setStyle(style1);
								cell = cells.getCell("C" + Integer.toString(i-1));	cell.setValue(catchuoi(chuoi3));
								cell.setStyle(style1);
								cell = cells.getCell("D" + Integer.toString(i-1));	cell.setValue(catchuoi(chuoi4));
								cell.setStyle(style1);
								cell = cells.getCell("E" + Integer.toString(i-1));	cell.setValue(catchuoi(chuoi5));
								cell.setStyle(style1);
								cell = cells.getCell("F" + Integer.toString(i-1));	cell.setValue(catchuoi(chuoi6));
								cell.setStyle(style1);
								cell = cells.getCell("G" + Integer.toString(i-1));	cell.setValue(catchuoi(chuoi7));
								cell.setStyle(style1);
							 
							}
							chuoi2="";chuoi3="";chuoi4="";chuoi5="";chuoi6="";chuoi7="";
							if(rs.getString("ngayid").equals("2")){
								chuoi2= rs.getString("phuong");
								
							}else if(rs.getString("ngayid").trim().equals("3")){
								chuoi3= rs.getString("phuong");
							}else if(rs.getString("ngayid").trim().equals("4")){
								chuoi4=rs.getString("phuong");
							}else if(rs.getString("ngayid").trim().equals("5")){
								chuoi5= rs.getString("phuong");
							}else if(rs.getString("ngayid").trim().equals("6")){
								chuoi6= rs.getString("phuong");
							}else if(rs.getString("ngayid").trim().equals("7")){
								chuoi7= rs.getString("phuong");
							}
							
							TenDDKD=rs.getString("tenddkd");
							
							
						}else{
							//System.out.println("ID DDKD : "+idddkd +" Thu tu :" +i +"ngay id : " +rs.getString("ngayid"));
							if(rs.getString("ngayid").equals("2")){
								chuoi2=chuoi2+","+ rs.getString("phuong");
								
							}else if(rs.getString("ngayid").trim().equals("3")){
								chuoi3=chuoi3+","+ rs.getString("phuong");
							}else if(rs.getString("ngayid").trim().equals("4")){
								chuoi4=chuoi4+","+ rs.getString("phuong");
							}else if(rs.getString("ngayid").trim().equals("5")){
								chuoi5=chuoi5+","+ rs.getString("phuong");
							}else if(rs.getString("ngayid").trim().equals("6")){
								chuoi6=chuoi6+","+ rs.getString("phuong");
							}else if(rs.getString("ngayid").trim().equals("7")){
								chuoi7=chuoi7+","+ rs.getString("phuong");
							}
							
						}
			
						
						
					}
				System.out.println("I : "+i);
				cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(TenDDKD );
				cell.setStyle(style);
								cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(catchuoi(chuoi2));
				cell.setStyle(style1);
				cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(catchuoi(chuoi3));
				cell.setStyle(style1);
				cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(catchuoi(chuoi4));
				cell.setStyle(style1);
				cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(catchuoi(chuoi5));
				cell.setStyle(style1);
				cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(catchuoi(chuoi6));
				cell.setStyle(style1);
				cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(catchuoi(chuoi7));
				cell.setStyle(style1);

			
				if (rs != null)
					rs.close();
				
				if(db != null) db.shutDown();
				
				if(i==2){					
					throw new Exception("Xin loi,khong co bao cao voi dieu kien da chon....!!!");
				}

			}catch(Exception ex){
				System.out.println(ex.toString());
				throw new Exception(ex.getMessage());
			}
		}else{
			return false;
		}
		return true;
	}


	private String catchuoi(String chuoi2) {
		// TODO Auto-generated method stub
		if(chuoi2==null){
			return "";
		}
		String chuoi= chuoi2.trim();
		if(chuoi.length()>0){
			if(chuoi.substring(0,1).equals(",")){
				chuoi=chuoi.substring(1,chuoi.length());
			}
		}
		return chuoi;
	}


	private void CreateHeader(Workbook workbook, IStockintransit obj)throws Exception {
		try{
			
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Sheet1");

			Cells cells = worksheet.getCells();
			cells.setRowHeight(0, 20.0f);
			Cell cell = cells.getCell("A1");
			
			ReportAPI.getCellStyle(cell, Color.RED, true, 16,
					"TÓM TẮT TUYẾN ");
		
			cell = cells.getCell("A3");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 10, "Ngày tạo : "
					+ obj.getDateTime());
			cell = cells.getCell("A4");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 10,
					"Được tạo bởi : " + obj.getuserTen());			
			
				
		}catch(Exception ex){
			throw new Exception("Khong tao duoc Header cho bao cao");
		}
		
	}

}
	

