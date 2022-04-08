package geso.dms.center.servlets.reports;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.Utility;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import com.aspose.cells.Picture;

public class BCAnhTrungBay extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	private String setQuery(IStockintransit obj) 
	{
		String condition ="";
		
		if(obj.getnppId().trim().length() > 0)
			condition +=" and npp.pk_seq = "+ obj.getnppId();
		if(obj.getDdkd().trim().length() > 0)
			condition +=" and ddkd.pk_seq = "+ obj.getDdkd();
		if(obj.getvungId().trim().length() > 0)
			condition +=" and v.pk_seq = "+ obj.getvungId();
		if(obj.getkhuvucId().trim().length() > 0)
			condition +=" and kv.pk_seq = "+ obj.getkhuvucId();
		Utility Ult = new Utility();
		condition += " and npp.pk_Seq in " + Ult.quyen_npp(obj.getuserId());
		
		String query=   
			 
			 "\n select '' SCHEME,npp.ten as NPP,anh1.ismuahang , ddkd.TEN [TDV], kh.smartid [MÃ KH],[kh].TEN [KH],'' NGAY1,'' ANH1,'' NGAY2,ISNULL(Anh1.IMAGEPATH,'NA')ANH2  " + 
			 "\n from  " + 
			 "\n (  " + 
			 "\n 	select DDKD_FK,KH_FK as Khachhang_fk,FILENAME IMAGEPATH,'' as ismuahang   " + 
			 "\n 	from KHACHHANG_ANHCHUP ac  " + 
			 "\n 	where NGAY <= '"+obj.getdenngay()+"'"
			 + "\n  and NGAY >= '"+obj.gettungay()+"' " + 
			 "\n )Anh1  " + 			
			 "\n inner join DAIDIENKINHDOANH ddkd on ddkd.pk_seq = Anh1.DDKD_FK  " + 
			 "\n inner join KHACHHANG kh on kh.PK_SEQ = Anh1.KHACHHANG_FK  " + 
			 "\n inner join NHAPHANPHOI npp on npp.PK_SEQ= kh.NPP_FK  " + 
			 "\n left join KHUVUC kv on kv.PK_SEQ=npp.KHUVUC_FK  " + 
			 "\n left join VUNG v on v.PK_SEQ=kv.VUNG_FK  " + 
			 "\n where 1 = 1   " + condition+
			 "\n order by SCHEME,NGAY1 ";
		
			System.out.println("____BC anh trung bay: " + query);
		return query;
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
	
	    Utility util = new Utility();
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
	    if(view == null)
	    	view = "";
	    
	    obj.setuserId(userId);
	    obj.init();	
	
	    String nppId ="";
	    nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
		if (nppId == null)
			nppId = "";
		obj.setnppId(nppId);

       
	    String query = setQuery(obj);
	    obj.setAnhtrungbayRs(query);
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		
		String nextJSP = "/AHF/pages/Center/BCAnhTrungBay.jsp";
		response.sendRedirect(nextJSP);
	}

	
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		Utility  util = new Utility();
		
		String userId = (String) session.getAttribute("userId");
		if (userId == null)
			userId = "";
		obj.setuserId(userId);
		
		String userTen = (String) session.getAttribute("userTen");
		obj.setuserTen(userTen);
		
		String view=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
		if(view == null)
			view = "";
		
		String nppId ="";
		if(view.equals("TT")){
			 nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
			if (nppId == null)
				nppId = "";
			obj.setnppId(nppId);
		}else{
			
			nppId=util.getIdNhapp(userId);
		}
		
		
		String ddkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdId")));
		if (ddkdId == null)
			ddkdId = "";
		obj.setDdkd(ddkdId);

		String cttbId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("cttbId")));
		if (cttbId ==null)
			cttbId = "";
		obj.setcttbId(cttbId);
	
		String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays")));
		if (tungay == null)
			tungay = "";
		obj.settungay(tungay);

		String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays")));
		if (denngay == null)
			denngay = "";
		obj.setdenngay(denngay);
		
		/*geso.htp.center.util.Utility Ult = new geso.htp.center.util.Utility();
		nppId = Ult.getIdNhapp(userId);*/

		obj.setvungId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"))):"");
			
		obj.setkhuvucId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId"))):"");
		
	
		obj.setTuyenbhId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tbhid")))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tbhid"))):"");
	

		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		if (action.equals("search")) 
		{
			String query = setQuery(obj);
			obj.setAnhtrungbayRs(query);

			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			obj.init();	
			if(!view.equals("TT"))
			{
				String nextJSP = "/AHF/pages/Distributor/BCAnhTrungBayDis.jsp";
				response.sendRedirect(nextJSP);
			}
			else
			{
				String nextJSP = "/AHF/pages/Center/BCAnhTrungBay.jsp";
				response.sendRedirect(nextJSP);
			}
			return;
		}
		else if(action.equals("excel"))
		{
			try 
			{
				request.setCharacterEncoding("utf-8");
			
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BCAnhTrungBay.xlsm");

				OutputStream out = response.getOutputStream();
				String query = getQueryExcel_NEW(obj);
				System.out.println(query);
				ExportToExcel(out, obj, query);
				obj.DBclose();
				return;
			} 
			catch (Exception ex) 
			{
				request.getSession().setAttribute("errors", ex.getMessage());
			}
		}
		else{
				obj.init();	
				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);
				
				if(!view.equals("TT"))
				{
					String nextJSP = "/AHF/pages/Distributor/BCAnhTrungBayDis.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					String nextJSP = "/AHF/pages/Center/BCAnhTrungBay.jsp";
					response.sendRedirect(nextJSP);
				}
		}
		
	}

	



	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh-mm-ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	
	


	private void TaoBaoCao(Workbook workbook,IStockintransit obj,String query )throws Exception
	{
		try{
			
			
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet("Sheet1");
			
			Cells cells = worksheet.getCells();
			cells.setRowHeight(0, 50.0f);
			Cell cell = cells.getCell("A1");
			ReportAPI.getCellStyle(cell, Color.RED, true, 16,"Báo cáo ảnh chụp cửa hàng");

			cell = cells.getCell("A3");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Ngày tạo : "+ obj.getDateTime());
			cell = cells.getCell("A4");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10,"Người tạo : " + obj.getuserTen());
			
			cell = cells.getCell("A5");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10,"Từ ngày : " + obj.gettungay());
			
			cell = cells.getCell("B5");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10,"Đến ngày : " + obj.getdenngay());
			
			int countRow = 8;
			int column = 0;
			
			cell = cells.getCell(countRow, column++ );cell.setValue("Vùng");
			ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			
			cell = cells.getCell(countRow, column++ );cell.setValue("Khu vực");
			ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			
			cell = cells.getCell(countRow, column++ );cell.setValue("NPP");
			ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			
			cell = cells.getCell(countRow, column++ );cell.setValue("MANVBH");
			ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			
			cell = cells.getCell(countRow, column++ );cell.setValue("NVBH");
			ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			
			cell = cells.getCell(countRow, column++ );cell.setValue("TUYEN");
			ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			
			
			cell = cells.getCell(countRow, column++ );cell.setValue("MÃ KH");
			ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			
			cell = cells.getCell(countRow, column++ );cell.setValue("KHÁCH HÀNG");
			ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			
			cell = cells.getCell(countRow, column++ );cell.setValue("SỐ LƯỢNG ẢNH");
			ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			
			cell = cells.getCell(countRow, column++ );cell.setValue("ĐỊA CHỈ");
			ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			
			cell = cells.getCell(countRow, column++ );cell.setValue("PHƯỜNG XÃ");
			ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			
			cell = cells.getCell(countRow, column++ );cell.setValue("QUẬN HUYỆN");
			ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			
			cell = cells.getCell(countRow, column++ );cell.setValue("TỈNH THÀNH");
			ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			
			cell = cells.getCell(countRow, column++ );cell.setValue("NGÀY");
			ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			
			cell = cells.getCell(countRow, column++ );cell.setValue("ẢNH ĐẠI DIỆN");
			ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			
			cell = cells.getCell(countRow, column++ );cell.setValue("ẢNH TRƯNG BÀY 1");
			ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			
			cell = cells.getCell(countRow, column++ );cell.setValue("ẢNH TRƯNG BÀY 2");
			ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			
			
			dbutils db = new dbutils();
			ResultSet rs = db.get(query);
			
			Color c = Color.WHITE;
			String anhtongquan="";
			String anhtbkhuvuc="";
			String anhchuahang="";
			int soluong=0;
			int flag=1;
			while(rs.next())
			{
				
					
					
					String dir=getServletContext().getInitParameter("urlAnhPDA")+"anhhangngay/";
					
					if(rs.getString("anhtongquan")!=null && rs.getString("anhtongquan").equals("1"))
					{
						anhtongquan=dir+rs.getString("filename");
						soluong++;
					}
						
					if(rs.getString("anhtbkhuvuc")!=null && rs.getString("anhtbkhuvuc").equals("1"))
					{
						anhtbkhuvuc=dir+rs.getString("filename");
						soluong++;
					}
				    dir=getServletContext().getInitParameter("urlAnhPDA")+"anhdaidien/";
						
					if(rs.getString("anhcuahang")!=null && !rs.getString("anhcuahang").equals("") && flag==1)
					{
						anhchuahang=dir+rs.getString("anhcuahang");
						soluong++;
						flag=0;
					}	
							
					
						
					
					
					if(rs.getInt("stt")==rs.getInt("sokh"))
					{
						countRow ++;
						column = 0;
						cell = cells.getCell(countRow, column++ ); cell.setValue(rs.getString("VUNG"));
						ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, false, 0);
						
						cell = cells.getCell(countRow, column++ ); cell.setValue(rs.getString("KHUVUC"));
						ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, false, 0);
						
						cell = cells.getCell(countRow, column++ ); cell.setValue(rs.getString("NPP"));
						ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, false, 0);
						
						cell = cells.getCell(countRow, column++ ); cell.setValue(rs.getString("MANV"));
						ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, false, 0);
						
						
						
						cell = cells.getCell(countRow, column++ ); cell.setValue(rs.getString("DDKD"));
						ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, false, 0);
						
						
						cell = cells.getCell(countRow, column++ ); cell.setValue(rs.getString("diengiai"));
						ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, false, 0);
						
						
						cell = cells.getCell(countRow, column++ ); cell.setValue(rs.getString("SMARTID"));
						ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, false, 0);
						
						cell = cells.getCell(countRow, column++ ); cell.setValue(rs.getString("TEN"));
						ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, false, 0);
						
						cell = cells.getCell(countRow, column++ ); cell.setValue(soluong);
						ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, false, 41);
						
						cell = cells.getCell(countRow, column++ ); cell.setValue(rs.getString("DIACHI"));
						ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, false, 0);
						
						cell = cells.getCell(countRow, column++ ); cell.setValue(rs.getString("PHUONGXA"));
						ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, false, 0);
						
						cell = cells.getCell(countRow, column++ ); cell.setValue(rs.getString("QUANHUYEN"));
						ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, false, 0);
						
						cell = cells.getCell(countRow, column++ ); cell.setValue(rs.getString("TINHTHANH"));
						ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, false, 0);
						
						cell = cells.getCell(countRow, column++ ); cell.setValue(rs.getString("NGAY"));
						ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, false, 0);
						
						
						
						 cell = cells.getCell(countRow, column++ ); cell.setValue(anhchuahang);
						ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, false, 0);
						
						
						cell = cells.getCell(countRow, column++ ); cell.setValue(anhtongquan);
						ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, false, 0);
						
						
					
						
						cell = cells.getCell(countRow, column++ ); cell.setValue(anhtbkhuvuc);
						ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, false, 0);
						
						anhchuahang="";
						anhtongquan="";
						anhtbkhuvuc="";
						soluong=0;
						flag=1;
					}
					
					
				
					
					
				
				
			}
			if(rs!=null)rs.close();
			if(db!=null){
				db.shutDown();
			}

	
		}catch(Exception ex){
			
			ex.printStackTrace();
			System.out.println("Errrorr : "+ex.toString());
			throw new Exception("Qua trinh dien du lieu vao file Excel va tao PivotTable bi loi.!!!");
		}
	}
	
	private void ExportToExcel(OutputStream out,IStockintransit obj,String query )throws Exception
	{
		try{ 		
			
			String chuoi;
	    		chuoi	=	getServletContext().getInitParameter("path") + "\\BCAnhTrungBay_new.xlsm";
	    
    		FileInputStream fstream = new FileInputStream(chuoi);
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			TaoBaoCao(workbook,obj,query);		
			workbook.save(out);	
			out.close();
			fstream.close();
		}catch(Exception ex){
			throw new Exception(ex.getMessage());
		}
		
	}
	
	private String getQueryExcel(IStockintransit obj)
	{
		String sql = "\n select ROW_NUMBER() OVER (PARTITION BY a.KH_FK order by a.Ngay ASC) as STT, " +
			"\n 	v.TEN as VUNG, kv.TEN as KHUVUC, npp.TEN as NPP,ddkd.smartid as MANV, ddkd.TEN as DDKD, kh.SMARTID, kh.TEN, kh.DIACHI, a.FILENAME, a.NGAY, ISNULL(x.Ten,'') as PHUONGXA, ISNULL(q.TEN,'') as QUANHUYEN, ISNULL(t.TEN,'') as TINHTHANH " + 
			"\n from KHACHHANG_ANHCHUP a " +
			"\n inner join DAIDIENKINHDOANH ddkd on ddkd.pk_seq = a.DDKD_FK " +  
			"\n inner join KHACHHANG kh on kh.PK_SEQ = a.KH_FK " +  
			"\n inner join NHAPHANPHOI npp on npp.PK_SEQ= kh.NPP_FK " +
			"\n left join DDKD_GSBH kdgs on kdgs.DDKD_FK = ddkd.PK_SEQ " +
			"\n left join GIAMSATBANHANG gs on gs.PK_SEQ = kdgs.GSBH_FK " +
			"\n left join GSBH_KHUVUC gskv on gskv.GSBH_FK = gs.PK_SEQ " +
			"\n left join KHUVUC kv on kv.PK_SEQ = gskv.KHUVUC_FK " +
			"\n left join VUNG v on v.PK_SEQ = kv.VUNG_FK " +
			"\n LEFT JOIN PhuongXa x on x.pk_Seq = kh.PhuongXa_FK " +
			"\n left join QUANHUYEN q on q.PK_SEQ = kh.QUANHUYEN_FK " +
			"\n left join TINHTHANH t on t.PK_SEQ = kh.TINHTHANH_FK " +
			"\n where a.NGAY >= '"+obj.gettungay()+"' AND a.NGAY <= '"+obj.getdenngay()+"'";
		
		//xài tạm thời, nếu bỏ xài chỉnh lại ko xuất cột SOANHCHUP
	/*	
		sql = 	"\n select v.TEN as VUNG, kv.TEN as KHUVUC, npp.TEN as NPP,ddkd.smartid as MANV, ddkd.TEN as DDKD, kh.SMARTID, kh.TEN, kh.DIACHI, a.FILENAME, a.NGAY, ISNULL(x.Ten,'') as PHUONGXA, ISNULL(q.TEN,'') as QUANHUYEN, ISNULL(t.TEN,'') as TINHTHANH, " +
				"\n		(SELECT COUNT(*) FROM KHACHHANG_ANHCHUP z WHERE z.KH_FK = dk.KHACHHANG_FK and z.NGAY >= '"+obj.gettungay()+"' AND z.NGAY <= '"+obj.getdenngay()+"') as SOANHCHUP 	" +      
				"\n  from DKTRUNGBAY_KHACHHANG dk " +
				"\n inner join DANGKYTRUNGBAY dktb on dktb.PK_SEQ = dk.dktrungbay_fk " +
				"\n left join " +      
				"\n  KHACHHANG_ANHCHUP a on dk.KHACHHANG_FK = a.KH_FK and a.NGAY >= '"+obj.gettungay()+"' AND a.NGAY <= '"+obj.getdenngay()+"' " +      
				"\n  left join ( " +      
				"\n 	select distinct d.PK_SEQ, d.SMARTID, d.TEN, t.KHACHHANG_FK from KHACHHANG_TUYENBH t inner join TUYENBANHANG tbh on tbh.PK_SEQ = t.TBH_FK " +      
				"\n 	inner join DAIDIENKINHDOANH d on d.PK_SEQ = tbh.DDKD_FK  " +      
				"\n 	where d.TRANGTHAI = 1 " +      
				"\n   " +      
				"\n  )ddkd on ddkd.KHACHHANG_FK = dk.KHACHHANG_FK " +      
				"\n  inner join KHACHHANG kh on kh.PK_SEQ = dk.KHACHHANG_FK " +      
				"\n  inner join NHAPHANPHOI npp on npp.PK_SEQ= kh.NPP_FK  " +      
				"\n  left join DDKD_GSBH kdgs on kdgs.DDKD_FK = ddkd.PK_SEQ  " +      
				"\n  left join GIAMSATBANHANG gs on gs.PK_SEQ = kdgs.GSBH_FK  " +      
				"\n  left join GSBH_KHUVUC gskv on gskv.GSBH_FK = gs.PK_SEQ  " +      
				"\n  left join KHUVUC kv on kv.PK_SEQ = gskv.KHUVUC_FK  " +      
				"\n  left join VUNG v on v.PK_SEQ = kv.VUNG_FK  " +      
				"\n LEFT JOIN PhuongXa x on x.pk_Seq = kh.PhuongXa_FK " +
				"\n left join QUANHUYEN q on q.PK_SEQ = kh.QUANHUYEN_FK " +
				"\n left join TINHTHANH t on t.PK_SEQ = kh.TINHTHANH_FK " +
				"\n  where 1 =1 ";*/
				
		sql = 
			"select * from (" +
			"\n 	 select v.TEN as VUNG, kv.TEN as KHUVUC, npp.TEN as NPP,ddkd.smartid as MANV, ddkd.TEN as DDKD, kh.SMARTID, kh.TEN, kh.DIACHI, a.FILENAME, a.NGAY, ISNULL(x.Ten,'') as PHUONGXA, ISNULL(q.TEN,'') as QUANHUYEN, ISNULL(t.TEN,'') as TINHTHANH,  " +
			"\n (SELECT COUNT(*) FROM KHACHHANG_ANHCHUP z WHERE z.KH_FK = ddkd.KHACHHANG_FK and z.DDKD_FK = ddkd.PK_SEQ  and z.NGAY >= '"+obj.gettungay()+"' AND z.NGAY <= '"+obj.getdenngay()+"') as SOANHCHUP 	 " +
			"\n from (  " +
			"\n select distinct d.PK_SEQ, d.SMARTID, d.TEN, t.KHACHHANG_FK from KHACHHANG_TUYENBH t inner join TUYENBANHANG tbh on tbh.PK_SEQ = t.TBH_FK  " +
			"\n inner join DAIDIENKINHDOANH d on d.PK_SEQ = tbh.DDKD_FK " +
			"\n )ddkd  " +
			"\n left join KHACHHANG_ANHCHUP a on ddkd.KHACHHANG_FK = a.KH_FK and a.DDKD_FK = ddkd.PK_SEQ and a.NGAY >= '"+obj.gettungay()+"' AND a.NGAY <= '"+obj.getdenngay()+"' " +
			"\n inner join KHACHHANG kh on kh.PK_SEQ = ddkd.KHACHHANG_FK  " +
			"\n inner join NHAPHANPHOI npp on npp.PK_SEQ= kh.NPP_FK   " +			
			"\n left join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK   " +
			"\n left join VUNG v on v.PK_SEQ = kv.VUNG_FK   " +
			"\n LEFT JOIN PhuongXa x on x.pk_Seq = kh.PhuongXa_FK  " +
			"\n left join QUANHUYEN q on q.PK_SEQ = kh.QUANHUYEN_FK  " +
			"\n left join TINHTHANH t on t.PK_SEQ = kh.TINHTHANH_FK  " +
			"\n where 1 =1  ";
			if(obj.getcttbId().trim().length() >0)
				sql += " and exists (select 1 from DKTRUNGBAY_KHACHHANG dkkh " +
				" inner join DANGKYTRUNGBAY dktb on dktb.PK_SEQ = dkkh.dktrungbay_fk " +
				" where dkkh.khachhang_fk = kh.pk_seq and dktb.cttrungbay_fk ='"+ obj.getcttbId()+"')";
			if(obj.getnppId().trim().length() > 0)
				sql +=" and npp.pk_seq = "+ obj.getnppId();
			if(obj.getDdkd().trim().length() > 0)
				sql +=" and ddkd.pk_seq = "+ obj.getDdkd();
			if(obj.getvungId().trim().length() > 0)
				sql +=" and v.pk_seq = "+ obj.getvungId();
			if(obj.getkhuvucId().trim().length() > 0)
				sql +=" and kv.pk_seq = "+ obj.getkhuvucId();
			Utility Ult = new Utility();
			sql += " and npp.pk_Seq in " + Ult.quyen_npp(obj.getuserId());
			
			sql += "\n union all " +
			"\n  select v.TEN as VUNG,kv.TEN as KHUVUC, npp.TEN as NPP,ddkd.smartid as MANV, ddkd.TEN as DDKD, kh.SMARTID, kh.TEN, kh.DIACHI, a.FILENAME, a.NGAY, ISNULL(x.Ten,'') as PHUONGXA, ISNULL(q.TEN,'') as QUANHUYEN, ISNULL(t.TEN,'') as TINHTHANH,  " +
			"\n (SELECT COUNT(*) FROM KHACHHANG_ANHCHUP z WHERE z.KH_FK = A.KH_FK and z.DDKD_FK = ddkd.PK_SEQ and z.NGAY >= '"+obj.gettungay()+"' AND z.NGAY <= '"+obj.getdenngay()+"') as SOANHCHUP 	 " +
			"\n from  " +
			"\n KHACHHANG_ANHCHUP a  " +
			"\n inner join DAIDIENKINHDOANH DDKD on ddkd.PK_SEQ = a.DDKD_FK and a.DDKD_FK = ddkd.PK_SEQ and a.NGAY >= '"+obj.gettungay()+"' AND a.NGAY <= '"+obj.getdenngay()+"'  " +
			"\n inner join KHACHHANG kh on kh.PK_SEQ = A.KH_FK  " +
			"\n inner join NHAPHANPHOI npp on npp.PK_SEQ= kh.NPP_FK   " +
			"\n left join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK   " +
			"\n left join VUNG v on v.PK_SEQ = kv.VUNG_FK   " +
			"\n  LEFT JOIN PhuongXa x on x.pk_Seq = kh.PhuongXa_FK  " +
			"\n left join QUANHUYEN q on q.PK_SEQ = kh.QUANHUYEN_FK  " +
			"\n left join TINHTHANH t on t.PK_SEQ = kh.TINHTHANH_FK  " +
			"\n where not exists(  " +
			"\n select * from KHACHHANG_TUYENBH t inner join TUYENBANHANG tbh on tbh.PK_SEQ = t.TBH_FK  " +
			"\n inner join DAIDIENKINHDOANH d on d.PK_SEQ = tbh.DDKD_FK where a.KH_FK = t.KHACHHANG_FK and d.PK_SEQ = ddkd.PK_SEQ ) " ;
		
		if(obj.getcttbId().trim().length() >0)
			sql += " and exists (select 1 from DKTRUNGBAY_KHACHHANG dkkh " +
					" inner join DANGKYTRUNGBAY dktb on dktb.PK_SEQ = dkkh.dktrungbay_fk " +
					" where dkkh.khachhang_fk = kh.pk_seq and dktb.cttrungbay_fk ='"+ obj.getcttbId()+"')";
		if(obj.getnppId().trim().length() > 0)
			sql +=" and npp.pk_seq = "+ obj.getnppId();
		if(obj.getDdkd().trim().length() > 0)
			sql +=" and ddkd.pk_seq = "+ obj.getDdkd();
		if(obj.getvungId().trim().length() > 0)
			sql +=" and v.pk_seq = "+ obj.getvungId();
		if(obj.getkhuvucId().trim().length() > 0)
			sql +=" and kv.pk_seq = "+ obj.getkhuvucId();
		sql += " and npp.pk_Seq in " + Ult.quyen_npp(obj.getuserId());
		
		//sql += " order by v.TEN, kv.TEN,ngay,a.kh_fk";
		sql += "\n) d  order by d.NPP,d.SMARTID, d.ngay ";
		return sql;
	}
	
	private String getQueryExcel_NEW(IStockintransit obj)
	{
		String sql ="\n SELECT "+
						"\n	COUNT(  d.NPP+d.SMARTID+ isnull(d.ngay,'')) OVER (PARTITION BY ( d.NPP+d.SMARTID+ isnull(d.ngay,'')),  d.NPP+d.SMARTID+ isnull(d.ngay,'')) AS SOKH, "+
						"\n	ROW_NUMBER() OVER (PARTITION BY (SMARTID+NPP+isnull(d.ngay,'')), SMARTID+NPP+isnull(d.ngay,'') order by (SMARTID+NPP+isnull(d.ngay,'')),SMARTID+NPP+isnull(d.ngay,'')) as STT, "+
						"\n	 VUNG, KHUVUC, NPP,MANV,  DDKD,DIENGIAI, SMARTID, TEN, DIACHI, FILENAME, NGAY, PHUONGXA, QUANHUYEN,  TINHTHANH,anhtongquan,anhtbkhuvuc,anhcuahang from ( "+
						"\n	 	 select v.TEN as VUNG, kv.TEN as KHUVUC, npp.TEN as NPP,ddkd.smartid as MANV, ddkd.TEN as DDKD, kh.SMARTID, kh.TEN, kh.DIACHI, a.FILENAME, a.NGAY, ISNULL(x.Ten,'') as PHUONGXA, ISNULL(q.TEN,'') as QUANHUYEN, ISNULL(t.TEN,'') as TINHTHANH, "+
						"\n			a.anhtongquan,a.anhtbkhuvuc ,kh.anhcuahang,ddkd.DIENGIAI "+
						"\n	 from (   "+
						"\n	 select distinct tbh.ngayid,d.PK_SEQ, d.SMARTID, d.TEN, t.KHACHHANG_FK,tbh.DIENGIAI from KHACHHANG_TUYENBH t inner join TUYENBANHANG tbh on tbh.PK_SEQ = t.TBH_FK   "+
						"\n	 inner join DAIDIENKINHDOANH d on d.PK_SEQ = tbh.DDKD_FK  "+
						"\n	 )ddkd  "+
						"\n	 left join KHACHHANG_ANHCHUP a on ddkd.KHACHHANG_FK = a.KH_FK and a.DDKD_FK = ddkd.PK_SEQ and a.NGAY >= '"+obj.gettungay()+"' AND a.NGAY <= '"+obj.getdenngay()+"'   "+
						"\n	 inner join KHACHHANG kh on kh.PK_SEQ = ddkd.KHACHHANG_FK   "+
						"\n	 inner join NHAPHANPHOI npp on npp.PK_SEQ= kh.NPP_FK    "+
						"\n	 left join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK    "+
						"\n	 left join VUNG v on v.PK_SEQ = kv.VUNG_FK    "+
						"\n	 LEFT JOIN PhuongXa x on x.pk_Seq = kh.PhuongXa_FK   "+
						"\n	 left join QUANHUYEN q on q.PK_SEQ = kh.QUANHUYEN_FK   "+
						"\n	 left join TINHTHANH t on t.PK_SEQ = kh.TINHTHANH_FK   "+
						"\n	 where 1 =1   and  kh.trangthai=1    ";
						
							if(obj.getnppId().trim().length() > 0)
								sql +=" and npp.pk_seq = "+ obj.getnppId();
							if(obj.getDdkd().trim().length() > 0)
								sql +=" and ddkd.pk_seq = "+ obj.getDdkd();
							if(obj.getvungId().trim().length() > 0)
								sql +=" and v.pk_seq = "+ obj.getvungId();
							if(obj.getkhuvucId().trim().length() > 0)
								sql +=" and kv.pk_seq = "+ obj.getkhuvucId();
							if(obj.getTuyenbhId().trim().length() > 0)
								sql +=" and ddkd.ngayid = "+ obj.getTuyenbhId();
							Utility Ult = new Utility();
							sql += " and npp.pk_Seq in " + Ult.quyen_npp(obj.getuserId());
						sql+="\n	) d  "+
							"\n	where 1=1 	 "+
							"\n	 order by d.NPP,d.SMARTID, d.ngay,d.ten ";
		
		return sql;
	}
	
	
}
