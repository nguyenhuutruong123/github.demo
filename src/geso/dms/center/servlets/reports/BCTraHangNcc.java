package geso.dms.center.servlets.reports;

import geso.dms.center.beans.Router.IDRouter;
import geso.dms.center.beans.Router.imp.Router;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.distributor.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
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

import com.aspose.cells.BorderLineType;
import com.aspose.cells.BorderType;
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

public class BCTraHangNcc extends HttpServlet {
	
       
	private static final long serialVersionUID = 1L;
	   Utility util=new Utility();
	   //String userTen = "";
	   //String userId;
	   //String ddkdId = "";
	   //String nppId = "";
	   //String kenhId = "";
	   //String tuyenId = "";
	   //boolean bfasle=true;
    public BCTraHangNcc() {
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
			  rs_tenuser.close();
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
		String userId = util.getUserId(querystring);
		session.setAttribute("userId", userId);
		session.setAttribute("tungay", "");
 		session.setAttribute("denngay","");
    	session.setAttribute("loi", "");
		session.setAttribute("userTen", userTen);	
		
		IDRouter obj = new Router();
		obj.setuserId(userId);
    	obj.setStatus("1");
		
		
		String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
		if(view == null)
			view = "";
		obj.setView(view);
		
		geso.dms.center.util.Utility ut = new geso.dms.center.util.Utility();
		if(view.equals("")) {
			obj.setnppId(ut.getIdNhapp(userId));
		}else{
		obj.setnppId("");
		}
		obj.init();
		
		session.setAttribute("obj",obj);
		String nextJSP = "/AHF/pages/Center/BCHangTraNcc.jsp";
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
        
        String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
        if(userId == null)
        	userId = "";
        obj.setuserId(userId);
        
        String vungId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")));
        if(vungId == null)
        	vungId = "";
        obj.setvungId(vungId);
        
        String kenhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kbhId")));
        if(kenhId == null)
        	kenhId = "";
        obj.setKenhId(kenhId);
        String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
        if(tungay == null)
        	tungay = "";
        obj.setTungay(tungay);
        String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
        if(denngay == null)
        	denngay = "";
        obj.setDenngay(denngay);
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
        
        String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
		System.out.println("loaiMenu = " + view);
		if(view == null)
			view = "";	
		obj.setView(view);
		
		geso.dms.center.util.Utility ut = new geso.dms.center.util.Utility();
		if(view.equals("NPP")) {
			obj.setnppId(ut.getIdNhapp(userId));
		}
        
        obj.init();
        String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
        String status = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("status")));
    	obj.setStatus(status);
        if(action.equals("export")){
        	OutputStream out = response.getOutputStream(); 
			String userTen = (String)session.getAttribute("userTen");
			
			response.setContentType("application/xlsm");
	        response.setHeader("Content-Disposition", "attachment; filename=BaoCaoTraHangNCC.xlsm");
	        
	    	/*response.setContentType("application/vnd.ms-excel");
	        response.setHeader("Content-Disposition", "attachment; filename=RouteReport.xls");*/
	        userId = (String) session.getAttribute("userId");
	        System.out.println("userId: "+userId);
			try {
				CreatePivotTable(out, obj);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        }else{
        	
        	String khuvucId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")));
        	if(khuvucId.trim().length() > 0)
	        	obj.setkhuvucId(khuvucId);
        	 status = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("status")));
        	obj.setStatus(status);
        	obj.createNPP();
        	
        	//System.out.println("status : "+status);
        	
        	session.setAttribute("obj",obj);
        	String nextJSP = "/AHF/pages/Center/BCHangTraNcc.jsp";
        	response.sendRedirect(nextJSP);
        }
	}
	
	private void CreateHeader_DTGV1(Workbook workbook,IDRouter obj, String query1) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);	
	    worksheet.setName("BCTRAHANG");
	    Cells cells = worksheet.getCells();	 
	    
	    cells.setRowHeight(0, 20.0f);	    
//	    Cell cell = cells.getCell("A1");	
//	    ReportAPI.getCellStyle(cell,Color.RED, true, 16, "Báo Cáo Đơn Hàng Bán Trong Kỳ");
	    
	    Cell cell = cells.getCell("D5");
	    ReportAPI.getCellStyle(cell,Color.NAVY,true,10,"Từ ngày: " + obj.getTungay() + "  Đến ngày : " + obj.getDenngay());
	    cell = cells.getCell("A4");
	    ReportAPI.getCellStyle(cell,Color.NAVY,true,10,"Ngày tạo : " + this.getDateTime()); 
	    String query= "select ten from nhanvien where pk_seq= "+ obj.getuserId() +"";
	    dbutils db =new  dbutils();
	    ResultSet a = db.get(query);
	    String tenuser="";
	    try {
			a.next();
			   tenuser= a.getString("ten");
			    a.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	  
	    cell = cells.getCell("A5");
	    ReportAPI.getCellStyle(cell,Color.NAVY,true,10,"Người tạo : " + tenuser);
	    Style canhle = cells.getCell("C4").getStyle();
		ResultSet rs = null;
		try {	
			rs = db.get(query1);
			int index =10;
			cell = null;
			double tongsoluong = 0;
			double tongdoanhso = 0;
			double tongsoluongtralai = 0;
			double tongtientralai = 0;
			double tongchietkhau = 0;
			double tonggiamgia = 0;
			double tongdoanhthuthuong = 0;
			double tongslKM = 0;
			double tongslQC = 0;
			double tongdongiavon = 0;
			double tonggiavonhangban = 0;
			double tonggiavonhangtralai = 0;
			double tonggiavonkm = 0;
			double tonggiavonhangQC = 0;
			double tongcongiavon = 0;
//			double tongtile = 0;
			double tongloinhuangop = 0;
		
			Color c = Color.WHITE;
			Color d = Color.RED;
			int sttmasp  = 1;
			int cotmasp  = 3;
			int checkgiavon= 0; 
			String querysp="";
			int intcheck= 0; 
			int stt = 1; 
			if(rs!=null) {
				while (rs.next()) {
					
						
					cell = cells.getCell("A" + String.valueOf(index));		cell.setValue(stt);		
					ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, true, 41);
					Style style = cell.getStyle();	
					cell = cells.getCell("B" + String.valueOf(index));		cell.setValue(rs.getString("vung"));
					ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, true, 41);
					cell = cells.getCell("C" + String.valueOf(index));		cell.setValue(rs.getString("khuvuc"));
					ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, true, 41);
					cell = cells.getCell("D" + String.valueOf(index));		cell.setValue(rs.getString("MANPP"));
					ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, true, 41);
					cell = cells.getCell("E" + String.valueOf(index));		cell.setValue(rs.getString("tennpp"));  
					ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, true, 41);
					cell = cells.getCell("F" + String.valueOf(index));		cell.setValue(rs.getString("pk_seq"));
					ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, true, 41);
					cell = cells.getCell("G" + String.valueOf(index));		cell.setValue(rs.getString("tenkenh"));
					ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, true, 41);
					cell = cells.getCell("H" + String.valueOf(index));		cell.setValue(rs.getString("tenkho"));
					ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, true, 41);
					cell = cells.getCell("I" + String.valueOf(index));		cell.setValue(rs.getString("trangthai"));
					ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, true, 41);
					cell = cells.getCell("J" + String.valueOf(index)); cell.setValue(rs.getString("ngaytra"));
					ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, true, 41);
					cell = cells.getCell("K" + String.valueOf(index));	cell.setValue(rs.getString("ma"));
					ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, true, 41);
					cell = cells.getCell("L" + String.valueOf(index));	cell.setValue(rs.getString("tensp"));
					ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, true, 41);
					cell = cells.getCell("M" + String.valueOf(index));	cell.setValue(rs.getString("donvi"));
					ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, true, 41);
					cell = cells.getCell("N" + String.valueOf(index));	style.setNumber((Integer)41); 	cell.setStyle(style);	cell.setValue(rs.getDouble("dongia"));
					ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, true, 41);
					cell = cells.getCell("O" + String.valueOf(index));	style.setNumber((Integer)41); 	cell.setStyle(style);	cell.setValue(rs.getDouble("soluong"));
					ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, true, 41);
					cell = cells.getCell("P" + String.valueOf(index));	style.setNumber((Integer)41); 	cell.setStyle(style);	cell.setValue(rs.getDouble("ptck"));
					ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, true, 41);
					cell = cells.getCell("Q" + String.valueOf(index));	style.setNumber((Integer)41); 	cell.setStyle(style);	cell.setValue(rs.getDouble("vat"));
					ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, true, 41);
					cell = cells.getCell("R" + String.valueOf(index));	style.setNumber((Integer)43); 	cell.setStyle(style);	cell.setValue(rs.getDouble("sotienbvat"));
					ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, true, 43);

					cell = cells.getCell("S" + String.valueOf(index));	style.setNumber((Integer)41); 	cell.setStyle(style);	cell.setValue(rs.getDouble("sotienavat"));
					ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, true, 41);
					cell = cells.getCell("T" + String.valueOf(index));		cell.setValue(rs.getString("ghichu"));
					ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, true, 41);
				
					index++;
					stt++;
					} 
				
			
					
			
					
				
				}
			if(rs!=null){ rs.close(); }	
		}
		catch(Exception ex) { ex.printStackTrace();
			try { if(rs!=null) rs.close(); throw new Exception(ex.getMessage());
			} catch (Exception e) { e.printStackTrace(); }
		}
		
		if(db!=null) db.shutDown();
	}
	private void CreatePivotTable(OutputStream out,IDRouter obj) throws Exception {
		try  {
			FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BCTraHangNcc.xlsm");
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			String query = getquery(obj);
			CreateHeader_DTGV1(workbook, obj, query);
			workbook.save(out);
			fstream.close();
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("Error Message: " + ex.getMessage());
		}
	}
	private String getquery(IDRouter obj) throws Exception {
		String query ="\n select k.ten as tenkho,e.TEN as vung,d.TEN as khuvuc,c.ma as MANPP,c.TEN as tennpp, a.pk_seq,f.ten as tenkenh,case when a.TRANGTHAI=0 then N'Chưa chốt' when a.TRANGTHAI=1 then N'Chờ duyệt' when a.TRANGTHAI=2 then N'Hoàn tất' when a.TRANGTHAI=3 then N'Đã hủy' end "+
				"\n as trangthai "+
				"\n ,a.NGAYTRA,g.MA,g.TEN as tensp,b.DONVI,b.DONGIA,sum(b.SOLUONG) as soluong ,0 ptck, "+
				"\n sum(convert(float,isnull(b.vat,0))) as vat ,sum(convert(float,isnull(b.SOTIENBVAT,0))) as sotienbvat,sum(convert(float,isnull(b.SOTIENAVAT,0))) as sotienavat,a.GhiChu "+
				"\n from DONTRAHANG a inner join DONTRAHANG_SP b  "+
				"\n on a.pk_seq=b.DONTRAHANG_FK  "+
				"\n inner join nhaphanphoi c on c.pk_seq=a.NPP_FK "+
				"\n left join khuvuc d on d.pk_seq=c.KHUVUC_FK "+
				"\n left join kho k on k.pk_seq=a.kho_fk "+
				"\n left join vung e on e.pk_seq=d.VUNG_FK "+
				"\n inner join KENHBANHANG f on f.pk_seq=a.KBH_FK "+
				"\n inner join sanpham g on g.pk_seq=b.SANPHAM_FK where 1=1 ";
		 
		if(obj.getTungay().length()>0){
			query +=" and a.ngaytra>='"+obj.getTungay()+"'";
		}
      if(obj.getDenngay().length()>0){
			query +=" and a.ngaytra<='"+obj.getDenngay()+"'";

		}	
      if(obj.getvungId().length()>0){
			query +=" and e.pk_seq="+obj.getvungId()+"";

		}	
      if(obj.getkhuvucId().length()>0){
			query +=" and d.pk_seq="+obj.getkhuvucId()+"";

		}	
      if(obj.getnppId().length()>0){
			query +=" and c.pk_seq="+obj.getnppId()+"";

		}	
      if(obj.getStatus().length()>0){
			query +=" and a.trangthai="+obj.getStatus()+"";

		}
      if(obj.getkenhId().length()>0){
			query +=" and f.pk_seq="+obj.getkenhId()+"";

		}
 query +="\n group by e.TEN,d.TEN,c.ma,c.TEN,a.pk_seq,f.ten,a.TRANGTHAI,a.NGAYTRA,k.TEN,g.MA,g.TEN,b.DONVI,b.DONGIA,a.GhiChu order by e.TEN,d.TEN,c.ma,c.TEN";
		System.out.print(query);
		
		return query;
	}
	
	private void getCellStyle(Worksheet worksheet, String a, Color mau, Boolean dam, int size)
	{
	   	   
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
	private void getAn(Worksheet worksheet,int i)
	{
	    Cells cells = worksheet.getCells();
	    for(int j = 26; j <= i; j++)
	    { 
	    	cells.hideColumn(j);
	    }
		
	}
	private void tieude(Worksheet worksheet,String a) throws IOException{

	    Cells cells = worksheet.getCells();
	    Cell cell = cells.getCell("A" + a);  cell.setValue("STT");				CreateBorderSetting(worksheet,"A"+a);	getCellStyle(worksheet,"A"+a,Color.BLUE,true,10);
	    cell = cells.getCell("B"  + a); cell.setValue("Vùng");					CreateBorderSetting(worksheet,"B"+a);	getCellStyle(worksheet,"B"+a,Color.BLUE,true,10);
	    cell = cells.getCell("C"  + a); cell.setValue("Khu vực");				CreateBorderSetting(worksheet,"C"+a);	getCellStyle(worksheet,"C"+a,Color.BLUE,true,10);	    
	    cell = cells.getCell("D"  + a); cell.setValue("Tỉnh thành");			CreateBorderSetting(worksheet,"D"+a);	getCellStyle(worksheet,"D"+a,Color.BLUE,true,10);	    
	    cell = cells.getCell("E"  + a); cell.setValue("Nhà phân phối");			CreateBorderSetting(worksheet,"E"+a);	getCellStyle(worksheet,"E"+a,Color.BLUE,true,10);
	    cell = cells.getCell("F"  + a); cell.setValue("Nhân Viên Bán Hàng");	CreateBorderSetting(worksheet,"F"+a);	getCellStyle(worksheet,"F"+a,Color.BLUE,true,10);
	    //cell = cells.getCell("F" + a); cell.setValue("Thu");					CreateBorderSetting(worksheet,"F"+a);	getCellStyle(worksheet,"F"+a,Color.BLUE,true,10);
	    cell = cells.getCell("G"  + a); cell.setValue("Mã KH");					CreateBorderSetting(worksheet,"G"+a);	getCellStyle(worksheet,"G"+a,Color.BLUE,true,10);
	    cell = cells.getCell("H" + a); cell.setValue("Tên KH");					CreateBorderSetting(worksheet,"H"+a);	getCellStyle(worksheet,"H"+a,Color.BLUE,true,10);
	    cell = cells.getCell("I" + a); cell.setValue("Đại chỉ");				CreateBorderSetting(worksheet,"I"+a);	getCellStyle(worksheet,"I"+a,Color.BLUE,true,10);
	    cell = cells.getCell("J" + a); cell.setValue("Phường/xã");				CreateBorderSetting(worksheet,"J"+a);	getCellStyle(worksheet,"J"+a,Color.BLUE,true,10);
	    cell = cells.getCell("K" + a); cell.setValue("Quận/huyện");				CreateBorderSetting(worksheet,"K"+a);	getCellStyle(worksheet,"K"+a,Color.BLUE,true,10);
	    cell = cells.getCell("L" + a); cell.setValue("Số điện thoại");			CreateBorderSetting(worksheet,"L"+a);	getCellStyle(worksheet,"L"+a,Color.BLUE,true,10);
	    cell = cells.getCell("M" + a); cell.setValue("Tổng giá trị");			CreateBorderSetting(worksheet,"M"+a);	getCellStyle(worksheet,"M"+a,Color.BLUE,true,10);
	    cell = cells.getCell("N" + a); cell.setValue("Loại cửa hàng");			CreateBorderSetting(worksheet,"N"+a);	getCellStyle(worksheet,"N"+a,Color.BLUE,true,10);
	    cell = cells.getCell("O" + a); cell.setValue("Vị trí cửa hàng");		CreateBorderSetting(worksheet,"O"+a);	getCellStyle(worksheet,"O"+a,Color.BLUE,true,10);	
	    cell = cells.getCell("P" + a); cell.setValue("Hạng cửa hàng");			CreateBorderSetting(worksheet,"P"+a);	getCellStyle(worksheet,"P"+a,Color.BLUE,true,10);
	    cell = cells.getCell("Q" + a); cell.setValue("Tần số");					CreateBorderSetting(worksheet,"Q"+a);	getCellStyle(worksheet,"P"+a,Color.BLUE,true,10);
	    cell = cells.getCell("R" + a); cell.setValue("Trạng thái KH");			CreateBorderSetting(worksheet,"R"+a);	getCellStyle(worksheet,"R"+a,Color.BLUE,true,10);
	}
	
	 public void CreateBorderSetting(Worksheet worksheet,String fileName) throws IOException
	    {
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
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh-mm-ss");
        Date date = new Date();
        return dateFormat.format(date);	
	}
}
