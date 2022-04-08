package geso.dms.center.servlets.reports;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.Utility;
import geso.dms.center.util.WebService;
import geso.dms.distributor.beans.report.Ireport;
import geso.dms.distributor.beans.report.imp.Report;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.Column;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class BCDoanhSoTheoVung extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	

	private String setQueryTheoNgay(IStockintransit obj, String view) 
	{
		Utility util = new Utility();

		String conditionSP = "";
		if(obj.getnhanhangId().length() > 0)
			conditionSP +=" and sp.nhanhang_fk = " + obj.getnhanhangId();
		if(obj.getchungloaiId().length() > 0)
			conditionSP +=" and sp.chungloai_fk = " + obj.getchungloaiId();
		
		String query =  "\n with ds " +
			"\n as " +
			"\n ( " +
			"\n 	select ds.khachhang_fk,kh.NPP_FK, sum( ds.soluong * ds.giamua) avat , round( sum( (ds.soluong * ds.giamua)/ ds.tylevat ),0) bvat " +
			"\n 	from  " +
			"\n 	( " +
			"\n 			SELECT  dh.khachhang_fk, DH_SP.soluong, DH_SP.giamua, DH.tyleVat   " +
			"\n 		  FROM Donhang DH  INNER JOIN DONHANG_SANPHAM  DH_SP ON DH.PK_SEQ = DH_SP.donhang_fk " +
			"\n			  inner join sanpham sp on sp.pk_seq = DH_SP.sanpham_fk	   " +        
			"\n 		  where DH.trangthai = 1   and dh.ngaynhap >='"+obj.gettungay()+"' and  dh.ngaynhap <='"+obj.getdenngay()+"' " + conditionSP +
			"\n 			and not exists ( select 1 from donhangtrave where trangthai = 3 and donhang_fk = dh.pk_seq)   " +     
			"\n 		  union all   " +
			"\n 		  select   dh.khachhang_fk, -1* DH_SP.soluong, DH_SP.giamua, DH.tyleVat " +
			"\n 		  from DONHANGTRAVE dh   " +
			"\n 		  inner join DONHANGTRAVE_SANPHAM DH_SP on DH_SP.DONHANGTRAVE_FK = dh.PK_SEQ   " +
			"\n			  inner join sanpham sp on sp.pk_seq = DH_SP.sanpham_fk	   " +     
			"\n 		  where dh.donhang_fk is null and  dh.TRANGTHAI = 3      and dh.ngaynhap >='"+obj.gettungay()+"' and  dh.ngaynhap <='"+obj.getdenngay()+"' " + conditionSP +
			"\n 	)ds " +
			"\n 	inner join KHACHHANG kh on kh.PK_SEQ = ds.KHACHHANG_FK " +
			"\n 	group by ds.khachhang_fk,kh.NPP_FK " +
			"\n ) " +

			"\n select v.ten [VÙNG], kv.ten [KHU VỰC],   tt.TEN [TỈNH THÀNH],npp.TEN [Nhà phân phối], kh.SMARTID [Mã KH], kh.TEN [Tên KH], ds.bvat [Doanh số trước VAT], ds.avat [Doanh số sau VAT] " +
			"\n from ds  " +
			"\n inner join khachhang kh  on kh.PK_SEQ = ds.KHACHHANG_FK " +
			"\n inner join NHAPHANPHOI npp on npp.PK_SEQ = ds.NPP_FK " +
			"\n left join tinhthanh tt on tt.pk_seq =kh.tinhthanh_fk " +
			"\n outer apply " +
			"\n ( " +
			"\n   	select top 1 kv.* " +
			"\n   	from khuvuc kv 	" +
			"\n	  	where kv.pk_seq in ( select  x.KHUVUC_FK   from KHUVUC_QUANHUYEN x where x.quanhuyen_fk = kh.quanhuyen_fk )	" +
			"\n )kv " +
			"\n left join vung v on v.pk_seq = kv.vung_fk " +			
			"\n outer apply " +
			"\n ( " +
			"\n 	select top 1 ddkd.* " +
			"\n 	from DAIDIENKINHDOANH ddkd " +
			"\n 	inner join tuyenbanhang t on t.ddkd_fk = ddkd.pk_seq and t.npp_fk = ddkd.npp_fk  " +
			"\n 	inner join khachhang_tuyenbh x on x.tbh_fk = t.PK_SEQ " +
			"\n 	where x.KHACHHANG_FK = ds.KHACHHANG_FK and kh.NPP_FK = ddkd.NPP_FK " +
			"\n )ddkd " +
			"\n outer apply " +
			"\n ( " +
			"\n 	select top 1 gsbh.* " +
			"\n 	from GIAMSATBANHANG gsbh  " +
			"\n 	where  gsbh.pk_seq in (select gsbh_fk from ddkd_gsbh where ddkd_fk = ddkd.pk_seq) " +
			"\n )ss " +
			"\n left join asm on asm.pk_seq =ss.asm_fk " +
			"\n left join bm on bm.pk_seq =asm.bm_fk " +
			
			"\n where 1=1 ";
		if(obj.getvungId().trim().length() > 0)
			query += " and v.pk_seq = "+ obj.getvungId() +"  ";
		if(obj.getkhuvucId().trim().length() > 0)
			query += " and kv.pk_seq = "+ obj.getkhuvucId() +"  ";		
		if(obj.getTinhthanhid().length() > 0)
			query += " and tt.pk_seq ='" + obj.getTinhthanhid() + "'";
		/*if (obj.getnppId().length() > 0)
			query += " and npp.pk_seq ='" + obj.getnppId() + "'";*/
		if (obj.getkenhId().length() > 0)
			query += " and kh.kbh_fk ='" + obj.getkenhId() + "'";
		
		
		if(obj.getPhanloai().equals("2"))//HO
		{
			if(obj.getLoaiNv().equals("1")) // BM
				query += " and bm.pk_seq = ( select top 1 BM_FK from nhanvien where pk_seq ="+obj.getuserId()+" ) ";
			else
				if(obj.getLoaiNv().equals("2")) // BM
					query += " and  asm.pk_seq  = ( select top 1 asm_fk from nhanvien where pk_seq ="+obj.getuserId()+" ) ";
				else
					if(obj.getLoaiNv().equals("3")) // BM
						query += " and  ss.pk_seq  = ( select top 1 gsbh_fk from nhanvien where pk_seq ="+obj.getuserId()+" ) ";
					else
						query += " and npp.pk_seq in " + util.quyen_npp(obj.getuserId()) ;
		}
		else
		{
			query += " and npp.pk_seq =   " + obj.getnppId();
		}
		
		
		
		
		query +="\n order by [VÙNG],[KHU VỰC],[TỈNH THÀNH] ";
		
		System.out.println("bc: ngay______" + query);

		return query ;
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();


		Utility util = new Utility();
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
		if(view == null)
			view = "";

		IStockintransit obj = new Stockintransit();
		obj.setuserId(userId);
		obj.setView(view);
		obj.init_user();	  
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);


		String nextJSP = "/AHF/pages/Center/BCDoanhSoTheoVung.jsp";
		response.sendRedirect(nextJSP);
	}


	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		Utility util = new Utility();

		String userId = (String) session.getAttribute("userId");
		if (userId == null)
			userId = "";
		obj.setuserId(userId);

		String userTen = (String) session.getAttribute("userTen");
		obj.setuserTen(userTen);
		String nppId = Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
		if (nppId == null)
			nppId = "";
		obj.setnppId(nppId);	

		String view = Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
		obj.setView(view);
		
		
		String chungloaiId = Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chungloaiId")));
		if (chungloaiId == null)
			chungloaiId = "";
		obj.setchungloaiId(chungloaiId);
		
		String nhanhangId = Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhanhangId")));
		if (nhanhangId == null)
			nhanhangId = "";
		obj.setnhanhangId(nhanhangId);
		
		
		String vungId = Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")));
		if (vungId == null)
			vungId = "";
		obj.setvungId(vungId);

		String khuvucId = Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")));
		if (khuvucId == null)
			khuvucId = "";
		obj.setkhuvucId(khuvucId);
		
		String tinhthanhId = Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tinhthanhId")));
		if (tinhthanhId == null)
			tinhthanhId = "";
		obj.setTinhthanhid(tinhthanhId);
		

		String kenhId = Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")));
		if (kenhId == null)
			kenhId = "";
		obj.setkenhId(kenhId);

	
		

		String tungay = Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays")));
		if (tungay == null)
			tungay = "";
		obj.settungay(tungay);

		String denngay = Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays")));
		if (denngay == null)
			denngay = "";
		
		
		
		obj.setdenngay(denngay);

		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		obj.init_user();	 
		
		if (action.equals("taoNgay")) 
		{
			try 
			{
				request.setCharacterEncoding("utf-8");
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BCDoanhSoTheoVung.xlsm");
				OutputStream out = response.getOutputStream();
				String query = setQueryTheoNgay(obj, view);
				ExportToExcel( out, obj, query );
				obj.getDb().shutDown();
			} 
			catch (Exception ex) 
			{
				ex.printStackTrace();
				System.out.println("Error Here : "+ex.toString());
				request.getSession().setAttribute("errors", ex.getMessage());
			}
		}
		else
			{
				obj.setuserId(userId);
			   
				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);

				String nextJSP = "/AHF/pages/Center/BCDoanhSoTheoVung.jsp";
				response.sendRedirect(nextJSP);
			}

	}
	
	private void ExportToExcel(OutputStream out,IStockintransit obj,String query )throws Exception
	{
		try{ 					
			
			FileInputStream fstream = null;
			Workbook workbook = new Workbook();
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			TaoBaoCao(workbook, obj, query);			
			workbook.save(out);					

		}catch(Exception ex){
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}
	}
	
	private void TaoBaoCao(com.aspose.cells.Workbook workbook,IStockintransit obj,String query)throws Exception
	{
		
		try{		

			com.aspose.cells.Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			com.aspose.cells.Cells cells = worksheet.getCells();
			Cell cell = cells.getCell("A1");;	
		   
			cells.setRowHeight(0, 20.0f);
			ReportAPI.getCellStyle(cell, Color.RED, true, 16, "DOANH SỐ THEO VÙNG");
			cell = cells.getCell("A2");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 10, "  Đến ngày : " + obj.getdenngay());
			cell = cells.getCell("A3");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 10, "Ngày tạo : " + this.getDateTime());
			cell = cells.getCell("A4");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 10, "Người tạo : " + obj.getuserTen());			
			
			ResultSet rs = obj.getDb().get(query);
			ResultSetMetaData rsmd = rs.getMetaData();
			int socottrongSql = rsmd.getColumnCount();
			
			int location  = 0;
			int row = 10;
			for( int i =1 ; i <=socottrongSql ; i ++  )
			{
				cell = cells.getCell(row, location + i-1 );
				cell.setValue(rsmd.getColumnName(i).replace("(%)",""));
				ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			}
			
			row ++;
			while(rs.next())
			{
				for(int i =1;i <=socottrongSql ; i ++)
				{					
					cell = cells.getCell(row,location + i-1 );
					
					if(!rsmd.getColumnName(i).contains("Ma") && rsmd.getColumnType(i) == Types.DOUBLE || rsmd.getColumnType(i) == Types.INTEGER || rsmd.getColumnType(i) == Types.DECIMAL )
					{
						int format = 37;
							if(rsmd.getColumnName(i).contains("%")|| rsmd.getColumnName(i).contains("(%)") )	
								format = 10;
						cell.setValue(rs.getDouble(i));
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, format);
					}
					else
					{
						cell.setValue(rs.getString(i));
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					}
				}
				
				++row;
			}
			
			if(rs!=null)rs.close();		
			
		}catch(Exception ex){

			ex.printStackTrace();
			throw new Exception("Lỗi ! Không có dữ liệu để xuất file !");
		}	
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		Date date = new Date();
		return dateFormat.format(date);
	}



}
