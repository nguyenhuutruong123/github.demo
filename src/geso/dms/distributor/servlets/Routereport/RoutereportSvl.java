	package geso.dms.distributor.servlets.Routereport;


import geso.dms.distributor.beans.Router.IDRouter;
import geso.dms.distributor.beans.Router.imp.Router;
import geso.dms.distributor.db.sql.dbutils;
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

import jxl.biff.FontRecord;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.CellFormat;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

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

public class RoutereportSvl extends HttpServlet {
	 
    public RoutereportSvl() {
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
			 
			  db.shutDown();
			}catch(Exception er){
				return "";
			}
		}
		return "";
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 Utility util=new Utility();
		   
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
	//	PrintWriter out = response.getWriter();

		
		userTen = (String)session.getAttribute("userTen");
		
		String querystring=request.getQueryString();
		userId=	util.getUserId(querystring);
		session.setAttribute("tungay", "");
 		session.setAttribute("denngay","");
    	 session.setAttribute("loi", "");
		session.setAttribute("userTen", userTen);
		IDRouter obj = new Router();
		obj.setuserId(userId);
		obj.init();
			
		session.setAttribute("obj",obj);
		
		String nextJSP = "/AHF/pages/Distributor/RouteReport.jsp";
		response.sendRedirect(nextJSP);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 Utility util=new Utility();
		   
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
	//	PrintWriter out = response.getWriter();

        IDRouter obj = new Router();
       
        String ddkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdId")));
        
        if(ddkdId == null)
        	ddkdId ="";
        obj.setddkdId(ddkdId);
        
        String tuyenId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tuyenId")));
        if(tuyenId == null)
        	tuyenId ="";
        obj.settuyenId(tuyenId);
        userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
        obj.setuserId(userId);
        
        obj.init();
        String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
        
        if(action.equals("export")){
        	OutputStream out = response.getOutputStream(); 
			userTen = (String)session.getAttribute("userTen");
		
			response.setContentType("application/xlsm");
	        response.setHeader("Content-Disposition", "attachment; filename=TuyenDuongCuaDDKD.xlsm");
	        
	        CreatePivotTable(out,obj);

        }else if(action.equals("exportmcp")){
        	String nppid=util.getIdNhapp(userId);
        	 XuatFileExcelSR(response,nppid,ddkdId);
        }
        else{
        	session.setAttribute("obj",obj);
        	String nextJSP = "/AHF/pages/Distributor/RouteReport.jsp";
        	response.sendRedirect(nextJSP);
        }
		}
	}
	private void CreatePivotTable(OutputStream out, IDRouter obj) throws IOException
    {    
	     Workbook workbook = new Workbook();
	     workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
	    	
	     String sql="select pk_seq, ten  from daidienkinhdoanh where npp_fk="+obj.getnppId() +" and trangthai=1";
	    
	     if(obj.getddkdId().length() >0){
	    	 sql=sql + " and pk_seq="+ obj.getddkdId();
	    	 
	     }
	     System.out.println("lay ddkd : "+sql);
	     dbutils db=new dbutils();
	     ResultSet rs=db.get(sql);
	     if(rs!=null){
	    	 try{
	    		 int i=0;
	    	 while (rs.next()){
	    		 Worksheets worksheets = workbook.getWorksheets();
	    	
	    		 Worksheet worksheet = worksheets.addSheet(rs.getString("pk_seq")+"_"+rs.getString("ten"));	    		 
    	    	 CreateTuyenDDKD(worksheet,obj,rs.getString("pk_seq"),rs.getString("ten"));    	    	  	    	     	    	    
	    	     i++;	    		
	    	 }
	    	 rs.close();
	    	 }catch(Exception er){
	    		 System.out.println(er.toString());
	    	 }
	     }
	     db.shutDown();
	    

	     workbook.save(out);
   }
	
	
	private void XuatFileExcelSR(HttpServletResponse response,String NppId,String DdkdId) throws IOException {
		// TODO Auto-generated method stub
		OutputStream out1 = null;
		try {
			response.setContentType("application/vnd.ms-excel");
			response.setHeader
			("Content-Disposition", "attachment; filename=sampleName.xls");
			WritableWorkbook w = 
				jxl.Workbook.createWorkbook(response.getOutputStream());

			dbutils db=new dbutils();
			String sql="select npp.ten as tennpp ,npp.ma,kv.ten as tenkv,ddkd.pk_seq as ddkdid,ddkd.ten  as ddkdten "+
			" from nhaphanphoi npp  "+
			" inner join khuvuc kv  on kv.pk_Seq=npp.khuvuc_fk "+ 
			" inner join daidienkinhdoanh  ddkd on ddkd.npp_fk=npp.pk_seq   "+
			" where npp.pk_seq="+NppId ;
			if(!DdkdId.equals("")) {
				sql=sql +" and ddkd.pk_seq="+DdkdId;
			}

			ResultSet rs=db.get(sql);
			int k=0;
			while (rs.next()){
				WritableSheet sheet = w.createSheet(rs.getString("ddkdid"), k);

				sheet.addCell(new Label(0, 1, "NPP : "));sheet.addCell(new Label(1, 1, ""+rs.getString("tennpp") ));

				sheet.addCell(new Label(0, 2, "KHU VỰC : "));sheet.addCell(new Label(1, 2, ""+rs.getString("tenkv") ));

				sheet.addCell(new Label(0, 3, "NVBH : "));sheet.addCell(new Label(1, 3, ""+rs.getString("ddkdten") ));

				WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
				cellFont.setColour(Colour.BLACK);

				WritableCellFormat cellFormat = new WritableCellFormat(cellFont);

				cellFormat.setBackground(jxl.format.Colour.GRAY_25);
				cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

				//cellFormat.setFont()
				int count = 0;
				sheet.addCell(new Label(count++, 4, "SỐ TT",cellFormat));
				sheet.addCell(new Label(count++, 4, "MÃ KH",cellFormat));
				sheet.addCell(new Label(count++, 4 , "TÊN CỬA HIỆU",cellFormat));
				sheet.addCell(new Label(count++, 4, "CHỦ CỬA HIỆU",cellFormat));
				sheet.addCell(new Label(count++, 4, "ĐỊA CHỈ",cellFormat));
				sheet.addCell(new Label(count++, 4, "MÃ PHƯỜNG/XÃ",cellFormat));
				sheet.addCell(new Label(count++, 4, "TÊN PHƯỜNG/XÃ",cellFormat));
				sheet.addCell(new Label(count++, 4, "MÃ QUẬN/HUYỆN",cellFormat));
				sheet.addCell(new Label(count++, 4, "TÊN QUẬN/HUYỆN",cellFormat));
				sheet.addCell(new Label(count++, 4, "MÃ TỈNH/THÀNH PHỐ",cellFormat));
				sheet.addCell(new Label(count++, 4, "TÊN TỈNH/THÀNH PHỐ",cellFormat));
				sheet.addCell(new Label(count++, 4, "SỐ ĐIỆN THOẠI",cellFormat));
				sheet.addCell(new Label(count++, 4, "VỊ TRÍ CỬA HÀNG",cellFormat));
				sheet.addCell(new Label(count++, 4, "LOẠI CỬA HÀNG",cellFormat));
				sheet.addCell(new Label(count++, 4, "HẠNG CỬA HÀNG",cellFormat));
				sheet.addCell(new Label(count++, 4, "TUYẾN THỨ",cellFormat));
				sheet.addCell(new Label(count++, 4, "TẦN SUẤT VIẾNG THĂM",cellFormat));
				sheet.addCell(new Label(count++, 4, "TRẠNG THÁI",cellFormat));
				sheet.addCell(new Label(count++, 4, "TỌA ĐỘ",cellFormat));
				count = 0;

				sql="\n select vt.vitri ,hch.hang as hangcuahang,lch.loai as loaicuahang,kh.phuongxa_fk," +
				"\n px.ten pxten, qh.pk_seq quanhuyen_fk, tt.pk_seq tinhthanh_fk, qh.ten qhten, tt.ten ttten, "+ 
				"\n kh.nguoidaidien,kh.chucuahieu,kh.tinhthanh_fk,kh.quanhuyen_fk,  "+ 
				"\n kh.ten as tencuahieu,kh.smartid,kh.diachi,kh.dienthoai,tbh.ngayid,khtbh.tanso,khtbh.sott,kh.trangthai, " +
				"\n CASE WHEN kh.LAT IS NOT NULL AND kh.LONG IS NOT NULL THEN '1' ELSE '0' END AS TOADO " +
				"\n from tuyenbanhang tbh "+ 
				"\n inner join khachhang_tuyenbh khtbh on tbh.pk_seq=khtbh.tbh_fk "+ 
				"\n inner join khachhang kh on kh.pk_seq=khtbh.khachhang_fk "+ 
				"\n left join hangcuahang hch on hch.pk_seq=kh.hch_fk "+ 
				"\n left join loaicuahang lch on lch.pk_seq=kh.lch_fk "+ 
				"\n left join vitricuahang vt on vt.pk_seq=kh.vtch_fk "+ 
				"\n left join phuongxa px on px.pk_seq = kh.phuongxa_fk "+
				"\n left join quanhuyen qh on qh.pk_seq = kh.quanhuyen_fk"+
				"\n left join tinhthanh tt on tt.pk_seq = kh.tinhthanh_fk "+
				"\n where tbh.npp_fk= "+NppId +" and tbh.ddkd_fk=" + rs.getString("ddkdid") +
				"\n order by kh.ddkd_fk,ngayid,khtbh.sott ";

				System.out.println(sql);

				ResultSet rs1=db.get(sql);
				Label label ;

				Number number;
				int j=5;
				//set style to cell data
				WritableCellFormat cellFormat2 = new WritableCellFormat();

				cellFormat2.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat2.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat2.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat2.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

				if(rs1!=null)
					while (rs1.next()){

						label = new Label(count++, j, rs1.getString("sott"),cellFormat2); 

						sheet.addCell(label); 

						label = new Label(count++, j, rs1.getString("smartid"),cellFormat2); 
						sheet.addCell(label); 

						label = new Label(count++, j,rs1.getString("tencuahieu"),cellFormat2); 
						sheet.addCell(label); 

						label = new Label(count++, j,rs1.getString("chucuahieu"),cellFormat2); 
						sheet.addCell(label); 

						label = new Label(count++, j,rs1.getString("diachi"),cellFormat2); 
						sheet.addCell(label);

						label = new Label(count++, j,rs1.getString("phuongxa_fk"),cellFormat2); 
						sheet.addCell(label); 
						
						label = new Label(count++, j,rs1.getString("pxten"),cellFormat2); 
						sheet.addCell(label); 
						
						label = new Label(count++, j,rs1.getString("quanhuyen_fk"),cellFormat2); 
						sheet.addCell(label); 
						
						label = new Label(count++, j,rs1.getString("qhten"),cellFormat2); 
						sheet.addCell(label); 

						label = new Label(count++, j,rs1.getString("tinhthanh_fk"),cellFormat2); 
						sheet.addCell(label); 

						label = new Label(count++, j,rs1.getString("ttten"),cellFormat2); 
						sheet.addCell(label);

						label = new Label(count++, j,rs1.getString("dienthoai"),cellFormat2); 
						sheet.addCell(label);

						label = new Label(count++, j,rs1.getString("vitri"),cellFormat2); 
						sheet.addCell(label);

						label = new Label(count++, j,rs1.getString("loaicuahang"),cellFormat2); 
						sheet.addCell(label);

						label = new Label(count++, j,rs1.getString("hangcuahang"),cellFormat2); 
						sheet.addCell(label);

						label = new Label(count++, j,rs1.getString("ngayid"),cellFormat2); 
						sheet.addCell(label);

						label = new Label(count++, j,rs1.getString("tanso"),cellFormat2); 
						sheet.addCell(label);
						
						label = new Label(count++, j,rs1.getString("trangthai"),cellFormat2); 
						sheet.addCell(label);

						label = new Label(count++, j,rs1.getString("TOADO"),cellFormat2); 
						sheet.addCell(label);

						count = 0;
						j++;
					}

				k++;
			}
			w.write();
			w.close();
		} 
		catch (Exception e){
			System.out.println("Error Cac Ban : "+e.toString());
		} 
		finally{
			if (out1 != null)
				out1.close();
		}
	}

	private void CreateTuyenDDKD(Worksheet worksheet, IDRouter obj, String ddkdid, String tenddkd) {
		 CreateStaticHeader(worksheet, obj, ddkdid, tenddkd);
		 CreateStaticData(worksheet,obj,ddkdid,tenddkd);
	}
	private void CreateStaticHeader(Worksheet worksheet, IDRouter obj, String ddkdid, String tenddkd) 
	{
	    Cells cells = worksheet.getCells();
	    System.out.println(ddkdid);
	   	  System.out.println(tenddkd);
	   	Style style;
	    //cells.setColumnWidth(0, 200.0f);
	    cells.setRowHeight(0, 20.0f);
	    Cell cell = cells.getCell("A1");  
	    cell.setValue("BÁO CÁO TUYẾN ĐƯỜNG CỦA ĐẠI DIỆN KINH DOANH - KÊNH TRUYỀN THỐNG");   		      
	    style = cell.getStyle();
	  
	    Font font2 = new Font();
	    font2.setColor(Color.RED);//mau chu
	    font2.setSize(16);// size chu
	    font2.setBold(true);
	    
	    style.setFont(font2); 
	    style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu
	    style.getBorderLine(3);
	    
	    cell.setStyle(style);
    
	    cell = cells.getCell("A3");getCellStyle(worksheet,"A3",Color.BLUE,true,10);
	    cell.setValue("Ngày Tạo: " + this.getDateTime());
	     
	    cell = cells.getCell("A4");getCellStyle(worksheet,"A4",Color.BLUE,true,10);
	    cell.setValue("Được tạo bởi:  " + this.gettenuser(obj.getuserId()));
	     
	  
	    //tieu de, hang dau tien cua bang du lieu, cell la toa do cua exel
	}
	private void CreateStaticData(Worksheet worksheet,IDRouter obj,String ddkdid,String tenddkd) 
	{
		 Utility util=new Utility();
		dbutils db = new dbutils();
	
	    Cells cells = worksheet.getCells();
	    String st="";
	    int dem =0;
	   
	    String nppId = util.getIdNhapp(obj.getuserId());
		//this.nppTen = util.
		//this.sitecode = "";	
	    if(nppId.length()>0)
	    {
	    	st = st + "tbh.npp_fk ='"+ nppId +"'";
	    }
	    
	    if(obj.gettuyenId().length()>0)
	    {
	    	if(st.length()>0)
	    		st = st + " and tbh.ngaylamviec ='"+ obj.gettuyenId() +"' ";
	    	else
	    		st ="tbh.ngaylamviec ='"+obj.gettuyenId() +"' ";
	    }
	    
	    if(ddkdid.length()>0)
	    {
	    	if(st.length()>0)
	    		st = st + " and tbh.ddkd_fk ='"+ ddkdid +"' ";
	    	else
	    		st = st + " tbh.ddkd_fk ='"+ ddkdid +"' ";
	    }
	    if(st.length()>0)
	    	st = " where " + st;
	    //khoi tao ket noi csdl
		
	    
	    String sql2 = "select ngayid  from tuyenbanhang where npp_fk = '"+nppId+"'";
	    System.out.println("Lay Du Lieu ngay 1 :"+sql2);
	    if (obj.gettuyenId() == ""){
	    	sql2 = sql2 + " group by ngayid";
	    }
	    else
	    {
	    	sql2 = sql2 + " and ngaylamviec = '"+obj.gettuyenId()+"' group by ngayid";
	    }
	    System.out.println("Lay Du Lieu ngay 2 :"+sql2);
		ResultSet rs2 = db.get(sql2);
		int i =5;
		if (rs2 != null)
		{
			try
			{
				while(rs2.next())
				{
					 i = i + 2;
					 
					Integer ngay = Integer.parseInt(rs2.getString("ngayid"));
					if (ngay == 2)
					{
						Cell cell = cells.getCell("A" + i); cell.setValue("Thứ 2");
					}else if (ngay  == 3)
					{
						Cell cell = cells.getCell("A" + i); cell.setValue("Thứ 3");
					}
					else if (ngay  == 4)
					{
						Cell cell = cells.getCell("A" + i); cell.setValue("Thứ 4");
					}
					else if (ngay  == 5)
					{
						Cell cell = cells.getCell("A" + i); cell.setValue("Thứ 5");
					}
					else if (ngay  == 6)
					{
						Cell cell = cells.getCell("A" + i); cell.setValue("Thứ 6");
					}
					else
					{
						Cell cell = cells.getCell("A" + i); cell.setValue("Thứ 7");
					}
					i=i+1;
					tieude(worksheet,String.valueOf(i));
					
					
					 String sql = "select v.ten as vung, kv.ten as khuvuc, npp.ten as npp, ddkd.ten as ddkd, tbh.ngaylamviec,isnull(kh_tuyen.sott,0) as sott , kh.smartid as Customer_Key,kh.ten as Customer_Name,isnull(kh.dienthoai,'Chưa xác định') as dienthoai , kh.diachi as Address,tt.ten as tinhthanh, qh.ten as province,case when ds.tonggiatri is null then 0 else ds.tonggiatri end as Average_Volume, lch.diengiai as Outlet_Type, "+
	    			 "vt.vitri as Outlet_Location,hch.hang as Outlet_Class,kh_tuyen.tanso as Frequency "+
	    			 "from khachhang kh "+
	    			 "inner join nhaphanphoi npp on npp.pk_seq = kh.npp_fk "+
      				 "inner join khuvuc kv on kv.pk_seq = npp.khuvuc_fk "+
	    		 	 "inner join vung v on v.pk_seq = kv.vung_fk "+
	    			 "left join quanhuyen qh on kh.quanhuyen_fk = qh.pk_seq "+
	    			 "left join tinhthanh tt on kh.tinhthanh_fk = tt.pk_seq "+
	    			 "left join loaicuahang lch on lch.pk_seq = kh.lch_fk "+
	    			 "left join vitricuahang vt on vt.pk_seq = kh.vtch_fk "+
	    			 "left join hangcuahang hch on hch.pk_seq = kh.hch_fk "+
	    			 "left join KHACHHANG_TUYENBH kh_tuyen on kh_tuyen.khachhang_fk = kh.pk_seq "+
	    			 "left join (select khachhang_fk,cast(sum(tonggiatri)/3 as int) as tonggiatri from donhang " +
	    			 "where ngaynhap >( SELECT CONVERT(VARCHAR(10),DATEADD(day,-12*7,dbo.GetLocalDate(DEFAULT)),120)) and ngaynhap < (SELECT CONVERT(VARCHAR(10),dbo.GetLocalDate(DEFAULT),120)) group by khachhang_fk) as ds "+
	    			 "on ds.khachhang_fk = kh.pk_seq " + 
	    			 "left join (select * from tuyenbanhang where ngayid = "+ngay+") tbh on tbh.pk_seq = kh_tuyen.tbh_fk " +   
	    			 "inner join daidienkinhdoanh ddkd on ddkd.pk_seq = tbh.ddkd_fk " + st + 
	    			 "order  by isnull(kh_tuyen.sott,0), v.ten, kv.ten, npp.ten ";

					 System.out.println("Lay Du Lieu Route :"+sql);
					 ResultSet rs = db.get(sql);
					 	i=i+1;
					 
					 if(rs!= null)
					 {
						 	
						 	cells.setColumnWidth(0, 8.0f);
							cells.setColumnWidth(1, 10.0f);
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
							cells.setColumnWidth(12, 20.0f);
							cells.setColumnWidth(13, 15.0f);
							cells.setColumnWidth(14, 15.0f);
							cells.setColumnWidth(15, 20.0f);
							cells.setColumnWidth(16, 20.0f);
							
						 try 
						 {
				
							 Cell cell = null;
							 int stt = 1;
							 while(rs.next())// lap den cuoi bang du lieu
							 {
				
					//lay tu co so du lieu, gan bien
					
								 //String Stt = String.valueOf(stt);
								 String Region =rs.getString("vung");
								 String Area =rs.getString("khuvuc");
								 String tinhthanh =rs.getString("tinhthanh");
								 String Distributor =rs.getString("npp");								 
								 String SalesRep =rs.getString("ddkd");
								 String Thu = rs.getString("ngaylamviec");
								 String CustomerCode =rs.getString("Customer_Key");
								 String CustomerName =rs.getString("Customer_Name");
								 String Address =rs.getString("Address");
								 String dienthoai =rs.getString("Dienthoai");
								 String Town = rs.getString("province");
								 String AverageVolume =rs.getString("Average_Volume");
								 String OutletType = rs.getString("Outlet_Type");
								 String OutletLocation = rs.getString("Outlet_Location");
								 String OutletClass = rs.getString("Outlet_Class");
								 String Frequency = rs.getString("Frequency");
								String sott=rs.getString("sott");
					
					//cell = cells.getCell("AA" + Integer.toString(i)); cell.setValue(Channel);
								 cell = cells.getCell("A" + Integer.toString(i)); cell.setValue(sott);			CreateBorderSetting(worksheet,"A" + Integer.toString(i));
								 cell = cells.getCell("B" + Integer.toString(i)); cell.setValue(Region);		CreateBorderSetting(worksheet,"B" + Integer.toString(i));
								 cell = cells.getCell("C" + Integer.toString(i)); cell.setValue(Area);			CreateBorderSetting(worksheet,"C" + Integer.toString(i));
								 
								 cell = cells.getCell("D" + Integer.toString(i)); cell.setValue(tinhthanh);	CreateBorderSetting(worksheet,"D" + Integer.toString(i));
								 
								 cell = cells.getCell("E" + Integer.toString(i)); cell.setValue(Distributor);	CreateBorderSetting(worksheet,"E" + Integer.toString(i));
								 cell = cells.getCell("F" + Integer.toString(i)); cell.setValue(SalesRep);		CreateBorderSetting(worksheet,"F" + Integer.toString(i));
								// cell = cells.getCell("F" + Integer.toString(i)); cell.setValue(sott);			CreateBorderSetting(worksheet,"F" + Integer.toString(i));
								 cell = cells.getCell("G" + Integer.toString(i)); cell.setValue(CustomerCode);	CreateBorderSetting(worksheet,"G" + Integer.toString(i));
								 cell = cells.getCell("H" + Integer.toString(i)); cell.setValue(CustomerName);	CreateBorderSetting(worksheet,"H" + Integer.toString(i));
								 cell = cells.getCell("I" + Integer.toString(i)); cell.setValue(Address);		CreateBorderSetting(worksheet,"I" + Integer.toString(i));
								 cell = cells.getCell("J" + Integer.toString(i)); cell.setValue(dienthoai);			CreateBorderSetting(worksheet,"J" + Integer.toString(i));
								 cell = cells.getCell("K" + Integer.toString(i)); cell.setValue(Town);			CreateBorderSetting(worksheet,"K" + Integer.toString(i));
								 cell = cells.getCell("L" + Integer.toString(i)); cell.setValue(Float.parseFloat(AverageVolume));CreateBorderSetting(worksheet,"L" + Integer.toString(i));
								 cell = cells.getCell("M" + Integer.toString(i)); cell.setValue(OutletType);	CreateBorderSetting(worksheet,"M" + Integer.toString(i));
								 cell = cells.getCell("N" + Integer.toString(i)); cell.setValue(OutletLocation);CreateBorderSetting(worksheet,"L" + Integer.toString(i));
								 cell = cells.getCell("O" + Integer.toString(i)); cell.setValue(OutletClass);	CreateBorderSetting(worksheet,"O" + Integer.toString(i));
								 cell = cells.getCell("P" + Integer.toString(i)); cell.setValue(Frequency);		CreateBorderSetting(worksheet,"P" + Integer.toString(i));
					
								 i++;
								 stt++;
							 }// end while
							 if(rs != null) rs.close();
							
						 }//end try
						 catch (Exception e){}
					 }//end if
				}// end while
				if(rs2 != null) rs2.close();
				 if(db != null) db.shutDown();
			} catch (Exception e){}//end try
		}// end if
	}

	private void getCellStyle( Worksheet worksheet, String a, Color mau, Boolean dam, int size)
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
	private void tieude(Worksheet worksheet,String a) throws IOException{
	   	 
	    Cells cells = worksheet.getCells();
	    Cell cell = cells.getCell("A" + a);  cell.setValue("STT"); 		CreateBorderSetting(worksheet,"A"+a); getCellStyle(worksheet,"A"+a,Color.BLUE,true,10);
	    cell = cells.getCell("B"  + a); cell.setValue("Vùng/Miền");		CreateBorderSetting(worksheet,"B"+a);	getCellStyle(worksheet,"B"+a,Color.BLUE,true,10);
	    cell = cells.getCell("C"  + a); cell.setValue("Khu Vực");			CreateBorderSetting(worksheet,"C"+a);	getCellStyle(worksheet,"C"+a,Color.BLUE,true,10);
	    
	    cell = cells.getCell("D"  + a); cell.setValue("Tỉnh Thành");			CreateBorderSetting(worksheet,"D"+a);	getCellStyle(worksheet,"D"+a,Color.BLUE,true,10);
	    
	    cell = cells.getCell("E"  + a); cell.setValue("Nhà Phân Phối");	CreateBorderSetting(worksheet,"E"+a);	getCellStyle(worksheet,"E"+a,Color.BLUE,true,10);
	    cell = cells.getCell("F"  + a); cell.setValue("Nhân Viên Bán Hàng");		CreateBorderSetting(worksheet,"F"+a);	getCellStyle(worksheet,"F"+a,Color.BLUE,true,10);
	    //cell = cells.getCell("F" + a); cell.setValue("Thứ tự");			CreateBorderSetting(worksheet,"F"+a);	getCellStyle(worksheet,"F"+a,Color.BLUE,true,10);
	    cell = cells.getCell("G"  + a); cell.setValue("Mã Khách Hàng");	CreateBorderSetting(worksheet,"G"+a);	getCellStyle(worksheet,"G"+a,Color.BLUE,true,10);
	    cell = cells.getCell("H" + a); cell.setValue("Tên Khách Hàng");	CreateBorderSetting(worksheet,"H"+a);	getCellStyle(worksheet,"H"+a,Color.BLUE,true,10);
	    cell = cells.getCell("I" + a); cell.setValue("Địa Chỉ");		CreateBorderSetting(worksheet,"I"+a);	getCellStyle(worksheet,"I"+a,Color.BLUE,true,10);
	    
	    cell = cells.getCell("J" + a); cell.setValue("Điện thoại");			CreateBorderSetting(worksheet,"J"+a);	getCellStyle(worksheet,"J"+a,Color.BLUE,true,10);
	    
	    cell = cells.getCell("K" + a); cell.setValue("Thị Trấn");			CreateBorderSetting(worksheet,"K"+a);	getCellStyle(worksheet,"K"+a,Color.BLUE,true,10);
	    cell = cells.getCell("L" + a); cell.setValue("Trung Bình Doanh Số");	CreateBorderSetting(worksheet,"L"+a);	getCellStyle(worksheet,"L"+a,Color.BLUE,true,10);
	    cell = cells.getCell("M" + a); cell.setValue("Loại Cửa Hàng");	CreateBorderSetting(worksheet,"M"+a);	getCellStyle(worksheet,"M"+a,Color.BLUE,true,10);
	    cell = cells.getCell("N" + a); cell.setValue("Vị Trí");CreateBorderSetting(worksheet,"N"+a);	getCellStyle(worksheet,"N"+a,Color.BLUE,true,10);
	    cell = cells.getCell("O" + a); cell.setValue("Hạng Cửa Hàng");	CreateBorderSetting(worksheet,"O"+a);	getCellStyle(worksheet,"O"+a,Color.BLUE,true,10);	
	    cell = cells.getCell("P" + a); cell.setValue("Tấn Số Viếng Thăm");		CreateBorderSetting(worksheet,"P"+a);	getCellStyle(worksheet,"P"+a,Color.BLUE,true,10);
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
