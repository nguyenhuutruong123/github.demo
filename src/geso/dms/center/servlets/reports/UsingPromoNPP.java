package geso.dms.center.servlets.reports;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
 * Servlet implementation class UsingPromoNPP
 */
@WebServlet("/UsingPromoNPP")
public class UsingPromoNPP extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UsingPromoNPP() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		Utility util=new Utility();
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();	  
		
		String userTen = (String)session.getAttribute("userTen");
		obj.setuserTen(userTen==null? "":userTen);
		
		String querystring=request.getQueryString();
		String userId=	util.getUserId(querystring);
		obj.setuserId(userId==null? "":userId);
		
		obj.init();
		session.setAttribute("obj", obj);	
		session.setAttribute("userTen", userTen);
		String nextJSP = "/AHF/pages/Distributor/UsingPromoNPP.jsp";
		response.sendRedirect(nextJSP);		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();	 		
 		IStockintransit obj = new Stockintransit();	
 		OutputStream out = response.getOutputStream();
		try
		    {
				String userId = (String) session.getAttribute("userId");
				String userTen = (String) session.getAttribute("userTen");	
				String nppId = (String) session.getAttribute("nppId");
				obj.setnppId(nppId);
				obj.setuserId(userId==null? "":userId);
				obj.setuserTen(userTen==null? "":userTen);
				obj.settungay(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays")==null? "":request.getParameter("Sdays")));			
				obj.setdenngay(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays")==null? "":request.getParameter("Edays")));
				
				
				obj.setkenhId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")==null? "":request.getParameter("kenhId")));
				obj.setdvkdId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")==null? "":request.getParameter("dvkdId")));
				obj.setnhanhangId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhanhangId")==null? "":request.getParameter("nhanhangId")));
				obj.setchungloaiId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chungloaiId")==null? "":request.getParameter("chungloaiId")));
				obj.setUnit(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("donviTinh")==null? "":request.getParameter("donviTinh")));
				obj.setPrograms(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("programs")==null? "":request.getParameter("programs")));
				obj.setFieldShow(request.getParameterValues("fieldsHien")!=null? request.getParameterValues("fieldsHien"):null);
				
				
				Utility Ult = new Utility();
				obj.setnppId(Ult.getIdNhapp(userId)) ;
				obj.setnppTen(Ult.getTenNhaPP());
				
				String condition = "";
				if(obj.getkenhId().length()>0)
					condition +=" and kbh.pk_seq ='" + obj.getkenhId() +"'";
				
				if(obj.getPrograms().length()>0)
					condition +=" and ctkm.scheme = '" + obj.getPrograms() +"'";
				
				if(obj.getdvkdId().length()>0)
					condition +=" and dvkd.pk_seq = '" + obj.getdvkdId() +"'";
				
				if(obj.getnhanhangId().length()>0)
					condition +=" and nh.pk_seq = '" + obj.getnhanhangId() +"'";
				
				if(obj.getchungloaiId().length()>0)
					condition +=" and cl.pk_seq = '" + obj.getchungloaiId() +"'";
				
				String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));				
				if (action.equals("create")) {
					response.setContentType("application/xlsm");
					response.setHeader("Content-Disposition",
							"attachment; filename=SuDungChiTraKhuyenMai(NPP).xlsm");
					CreatePivotTable(out,obj,condition);
				}		        
		     }
		    catch (Exception ex)
		    {
		       obj.setMsg(ex.getMessage());
		    }
		    obj.init();
			session.setAttribute("obj", obj);	
			session.setAttribute("userTen", obj.getuserTen());
			String nextJSP = "/AHF/pages/Distributor/UsingPromoNPP.jsp";
			response.sendRedirect(nextJSP);		
 	}

 	private void CreatePivotTable(OutputStream out,IStockintransit obj, String condition) throws Exception
    {       
 		//String strfstream = getServletContext().getInitParameter("path")+"\\SuDungChiTraKhuyenMai.xlsm";
 		//FileInputStream fstream = new FileInputStream(strfstream);
 		File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\SuDungChiTraKhuyenMai.xlsm");
		FileInputStream fstream = new FileInputStream(f) ;
		Workbook workbook = new Workbook();
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		CreateStaticHeader(workbook,obj);	     
	    CreateStaticData(workbook, obj, condition);
	    workbook.save(out);
	    fstream.close();
	}
 	
 	private void CreateStaticHeader(Workbook workbook, IStockintransit obj)throws Exception 
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
	    
	    cell.setValue("BÁO CÁO SỬ DỤNG VÀ CHI TRẢ KHUYẾN MÃI");  getCellStyle(workbook,"A1",Color.RED,true,14);	  	
	    
	    cells.setRowHeight(2, 18.0f);
	    cell = cells.getCell("A3"); 
	    getCellStyle(workbook,"A3",Color.NAVY,true,10);	    
	    cell.setValue("Từ ngày: " + obj.gettungay());
	    
	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("B3"); getCellStyle(workbook,"B3",Color.NAVY,true,9);	        
	    cell.setValue("Đến ngày: " + obj.getdenngay());    
	    
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A4");getCellStyle(workbook,"A4",Color.NAVY,true,9);
	    cell.setValue("Ngày báo cáo: " + this.getdate());
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A5");getCellStyle(workbook,"A5",Color.NAVY,true,9);
	    cell.setValue("Được tạo bởi Nhà phân phối:  " + obj.getuserTen());
			  
		cell = cells.getCell("AA1"); cell.setValue("Kenh");
		cell = cells.getCell("AB1"); cell.setValue("DonViKinhDoanh");
		cell = cells.getCell("AC1"); cell.setValue("Vung");
 	    cell = cells.getCell("AD1"); cell.setValue("Khu vuc");  	
 	   cell = cells.getCell("AE1"); cell.setValue("MaNhaPP");
	    cell = cells.getCell("AF1"); cell.setValue("Nha Phan Phoi");
	    cell = cells.getCell("AG1"); cell.setValue("Ma Chuong Trinh KM");
	    cell = cells.getCell("AH1"); cell.setValue("Chuong Trinh KM");
	    
	    cell = cells.getCell("AI1"); cell.setValue("NhanHang");
	    cell = cells.getCell("AJ1"); cell.setValue("ChungLoai");
	    cell = cells.getCell("AK1"); cell.setValue("MaSanPham");
	    cell = cells.getCell("AL1"); cell.setValue("TenSanPham");
	    cell = cells.getCell("AM1"); cell.setValue("SoLuong");
	    cell = cells.getCell("AN1"); cell.setValue("ThanhTien");
	    cell = cells.getCell("AO1"); cell.setValue("Type");
	    
	    
 	}
 	
 	private void CreateStaticData(Workbook workbook, IStockintransit obj, String condition) throws Exception
 	{
 		Worksheets worksheets = workbook.getWorksheets();
 	    Worksheet worksheet = worksheets.getSheet(0);
 	    Cells cells = worksheet.getCells();
 	    dbutils db = new dbutils();	  
	
 	    String sql = " SELECT KBH.DIENGIAI as KENH,V.TEN AS VUNG,KV.TEN AS KHUVUC,NPP.SITECODE,NPP.TEN AS TENNPP,CTKM.SCHEME,CTKM.DIENGIAI as  ctkm,DVKD.DIENGIAI AS DVKD, "+
 	    	" NH.TEN AS NHANHANG,CL.TEN AS CHUNGLOAI,SP.MA as masp,SP.TEN as tensp,NHAP.SOLUONG ,NHAP.TONGGIATRI AS THANHTIEN ,NHAP.TYPE FROM "+
 	    	" ( "+
 	    	" SELECT DH.NPP_FK,DH.KBH_FK,TRAKM.SPMA AS SANPHAM_FK,TRAKM.CTKMID, SUM(SOLUONG) AS SOLUONG, "+ 
 	    	" SUM(TRAKM.TONGGIATRI) AS TONGGIATRI,N'Sử dụng' as type "+
 	    	" FROM DONHANG DH INNER JOIN DONHANG_CTKM_TRAKM TRAKM ON DH.PK_SEQ=TRAKM.DONHANGID "+
 	    	" WHERE DH.NGAYNHAP >='"+obj.gettungay()+"' AND DH.NGAYNHAP <='"+obj.getdenngay()+"' "+
 	    	" AND  DH.TRANGTHAI=1 AND DH.PK_SEQ NOT IN (SELECT DONHANG_FK FROM DONHANGTRAVE WHERE DONHANG_FK IS NOT NULL AND TRANGTHAI=3 "+
 	    	" )  "+
 	    	" GROUP BY DH.NPP_FK,DH.KBH_FK,TRAKM.SPMA,TRAKM.CTKMID "+
 	    	" union all "+
 	    	" SELECT NH.NPP_FK,NH.KBH_FK,NH_SP.SANPHAM_FK,CTKM.PK_SEQ, SUM(CAST( SOLUONG AS FLOAT) ) AS SOLUONG "+ 
 	    	" ,SUM(CAST( SOLUONG AS FLOAT) * GIAGROSS ) AS THANHTIEN ,N'Trả khuyến mãi' as type FROM NHAPHANG NH  "+
 	    	" INNER JOIN NHAPHANG_SP NH_SP ON NH.PK_SEQ=NH_SP.NHAPHANG_FK "+
 	    	" INNER JOIN CTKHUYENMAI CTKM ON CTKM.SCHEME=NH_SP.SCHEME "+
 	    	" WHERE NH_SP.SCHEME IS NOT NULL AND NH_SP.SCHEME <> '' and NH.NGAYCHUNGTU >='"+obj.gettungay()+"' and nh.ngaychungtu <='"+obj.getdenngay()+"' "+
 	    	" GROUP BY NH.NPP_FK,NH_SP.SANPHAM_FK,CTKM.PK_SEQ,NH.KBH_FK "+  
 	    	" ) NHAP "+
 	    	" INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ=NHAP.NPP_FK "+
 	    	" LEFT JOIN KHUVUC KV ON KV.PK_SEQ=NPP.KHUVUC_FK "+
 	    	" LEFT JOIN VUNG V ON V.PK_SEQ=KV.VUNG_FK "+
 	    	" LEFT JOIN KENHBANHANG KBH ON KBH.PK_SEQ=NHAP.KBH_FK "+
 	    	" LEFT JOIN CTKHUYENMAI CTKM ON CTKM.PK_SEQ=NHAP.CTKMID "+
 	    	" LEFT JOIN SANPHAM SP ON SP.MA=NHAP.SANPHAM_FK "+
 	    	" LEFT JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ=SP.DVKD_FK "+
 	    	" LEFT JOIN NHANHANG NH ON NH.PK_SEQ=SP.NHANHANG_FK "+
 	    	" LEFT JOIN CHUNGLOAI CL ON CL.PK_SEQ=SP.CHUNGLOAI_FK"+
 	    	" WHERE NPP.PK_SEQ = '"+obj.getnppId()+"' "+condition;
 	 
		System.out.println("Get Sql : "+sql);
 	   	ResultSet rs = db.get(sql); 	   
 	    int i = 2;
 		if(rs!=null)
 		{
 			try 
 			{
 				cells.setColumnWidth(0, 19.0f);
 				cells.setColumnWidth(1, 50.0f);
 				cells.setColumnWidth(2, 12.0f);
 				cells.setColumnWidth(3, 12.0f);
 				cells.setColumnWidth(4, 20.0f);
 				cells.setColumnWidth(5, 20.0f);
 				cells.setColumnWidth(6, 20.0f);
 				cells.setColumnWidth(7, 20.0f);
 				cells.setColumnWidth(8, 20.0f);
 				cells.setColumnWidth(9, 20.0f);
 				cells.setColumnWidth(10, 20.0f);
 				cells.setColumnWidth(6, 20.0f);
 				cells.setColumnWidth(7, 20.0f);
 				cells.setColumnWidth(8, 20.0f);
 				cells.setColumnWidth(9, 20.0f);
 				cells.setColumnWidth(10, 20.0f);
 				
 				Cell cell = null;
 				Style style;
 				//String khoKM="";
 				while(rs.next())
 				{  					
					cell = cells.getCell("AA" + Integer.toString(i)); cell.setValue(rs.getString("kenh"));
					cell = cells.getCell("AB" + Integer.toString(i)); cell.setValue(rs.getString("dvkd"));			
					cell = cells.getCell("AC" + Integer.toString(i)); cell.setValue(rs.getString("vung"));
 					cell = cells.getCell("AD" + Integer.toString(i)); cell.setValue(rs.getString("khuvuc"));
 					cell = cells.getCell("AE" + Integer.toString(i)); cell.setValue(rs.getString("sitecode"));
					cell = cells.getCell("AF" + Integer.toString(i)); cell.setValue(rs.getString("tennpp"));
					
					cell = cells.getCell("AG" + Integer.toString(i)); cell.setValue(rs.getString("scheme"));
					cell = cells.getCell("AH" + Integer.toString(i)); cell.setValue(rs.getString("ctkm"));
					
					cell = cells.getCell("AI" + Integer.toString(i)); cell.setValue(rs.getString("nhanhang"));
					cell = cells.getCell("AJ" + Integer.toString(i)); cell.setValue(rs.getString("chungloai"));
					cell = cells.getCell("AK" + Integer.toString(i)); cell.setValue(rs.getString("masp"));
					cell = cells.getCell("AL" + Integer.toString(i)); cell.setValue(rs.getString("tensp"));


					
					style = cell.getStyle();
					style.setNumber(2);
					cell.setStyle(style);

					cell = cells.getCell("AM" + Integer.toString(i)); cell.setValue(rs.getDouble("soluong"));
					style = cell.getStyle();
					style.setNumber(2);
					cell.setStyle(style);

					cell = cells.getCell("AN" + Integer.toString(i)); cell.setValue(rs.getDouble("thanhtien"));
					
					cell = cells.getCell("AO" + Integer.toString(i)); cell.setValue(rs.getString("type"));

					

					
					i++;
 				}

				//ReportAPI.setHidden(workbook, obj.getFieldShow().length+1);
				
			    setAn(workbook, 50);
 		}catch (Exception e){ 	
 			System.out.println("LOI : "+e.toString());
 			throw new Exception("Khong tao duoc bao cao trong thoi gian nay. Loi : "+e.toString());
 		}
 		finally{
 			if(rs != null)
 			rs.close();
 			if(db!=null){
 				db.shutDown();
 			}
 		}
 		}else{
 			throw new Exception("Khong tao duoc bao cao trong thoi gian nay...");
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
	    
		//Setting the horizontal alignment of the text in the cell 
	    style.setHAlignment(TextAlignmentType.LEFT);
	    cell.setStyle(style);
	}

	private String getdate() 
	{
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy: hh:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);	
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

}
