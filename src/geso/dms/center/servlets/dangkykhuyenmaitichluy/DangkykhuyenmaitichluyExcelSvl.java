package geso.dms.center.servlets.dangkykhuyenmaitichluy;


import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.Utility;


import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import java.sql.Types;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
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
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;


public class DangkykhuyenmaitichluyExcelSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	PrintWriter out;
	public DangkykhuyenmaitichluyExcelSvl() 
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}
		else
		{
			session.setMaxInactiveInterval(30000);

			Utility util = new Utility();

			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);

			if (userId.length()==0)
				userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"))); 

			String id = util.getId(querystring); 
			String excel = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("excel"));
			if(excel != null)
			{
				XuatFileExcel(response, id);
			}else
			{
				XuatFileExcel_Details(response, id);
			}
			
			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{

	}
	private String getDateTime() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);	
	}
	private String XuatFileExcel(HttpServletResponse response, String id) throws IOException
	{
		NumberFormat formatter = new DecimalFormat("#,###,###");
		try 
		{
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=Dangkykhuyenmaitichluy_" +getDateTime()+".xls");
			dbutils db=new dbutils();
			Workbook workbook = new Workbook();
			try
			{
				Worksheets worksheets = workbook.getWorksheets();
				Worksheet worksheet = worksheets.getSheet(0);	    
				worksheet.setName("Sheet1");
				Cells cells = worksheet.getCells();	
				Cell cell;
				cells.setRowHeight(0, 50.0f);
				worksheet.setGridlinesVisible(false);
				String query = "select b.smartid as MaKhachHang, b.TEN as TenKhachHang, c.ten as NhaPhanPhoi,a.MucDangKy_FK + 1 as MucDangKy    " +
						" from DANGKYKM_TICHLUY_KHACHHANG a inner Join Khachhang b on a.khachhang_fk = b.PK_SEQ    " +
						"	inner join nhaphanphoi c on b.npp_fk = c.pk_seq "+
						"where a.DKKMTICHLUY_FK = '" + id + "' " ;
				ResultSet rs = db.get(query);

				ResultSetMetaData rsmd = rs.getMetaData();

				int socottrongSql = rsmd.getColumnCount();// số cột trong câu query[3 cột]

				int countRow = 0;// dòng đầu tiên dữ liệu
				int column =0;// cột đầu tiên dữ liệu


				//Vẽ header 
				for( int i =1 ; i <=socottrongSql ; i ++  )
				{
					cell = cells.getCell(countRow,i-1 + column);cell.setValue(rsmd.getColumnName(i));
					ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, true, 0);
					Style style;
					style=cell.getStyle();
					style.setTextWrapped(true);
					style.setHAlignment(TextAlignmentType.CENTER);
					style.setVAlignment(TextAlignmentType.CENTER);
					cell.setStyle(style);
				}// Vẽ ra  ->  Mã NV |  Đại diện kinh doanh |   Doanh Số    

				countRow ++;

				// đỗ dữ liệu
				while(rs.next())
				{
					for(int i =1;i <=socottrongSql ; i ++)
					{
						cell = cells.getCell(countRow,i-1 );
						if(rsmd.getColumnType(i) == Types.DOUBLE )// số liệu dạng float để đổ pivot bên value
						{
							ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
							cell.setValue(rs.getDouble(i));
						}
						else
						{cell.setValue(rs.getString(i)); 
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
						}

					}
					++countRow;
				}

				if(rs!=null)rs.close();
				if(db!=null){
					db.shutDown();
				}


			}catch(Exception ex){

				System.out.println("Errrorr : "+ex.toString());
				throw new Exception(" abcd");
			}



			OutputStream out = response.getOutputStream();
			workbook.save(out);
			out.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return "";
	}
	
	private String XuatFileExcel_Details(HttpServletResponse response, String id) throws IOException
	{
		NumberFormat formatter = new DecimalFormat("#,###,###");
		try 
		{
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=Dangkykhuyenmaitichluy_" +getDateTime()+".xls");
			dbutils db=new dbutils();
			Workbook workbook = new Workbook();
			try
			{
				Worksheets worksheets = workbook.getWorksheets();
				Worksheet worksheet = worksheets.getSheet(0);	    
				worksheet.setName("Sheet1");
				Cells cells = worksheet.getCells();	
				Cell cell;
				cells.setRowHeight(0, 50.0f);
				worksheet.setGridlinesVisible(false);
				String query = 
		"		select  1+ a.SOTT as SOTT , b.ma as MaSanPham,b.ten as TenSanPham,c.donvi as DonVi,  "+
		"				a.SoLuong,a.SoLuongTTDuyet,a.DonGiaGoc,a.DonGia as DonGiaSauCK,a.thuevat as ptVat, "+
		"				a.SoLuongTTDuyet*a.DonGia as ThanhTien,'' as CTKM,a.NgayGiaoHang,a.GhiChu "+
		"			from Erp_DonDatHANG_SANPHAM a inner join sanpham b on b.pk_Seq=a.sanpham_fk "+  
		"				inner join donvidoluong c on c.pk_Seq=b.dvdl_Fk "+
		"			where a.DonDatHANG_FK = '" + id + "'  "+
		"			union all "+
		"			select   1+ ISNULL((SELECT COUNT(*) FROM  ERP_DONDATHANG_SANPHAM  WHERE  DONDATHANG_FK=A.DONDATHANGID),0 )+  "+
		"	ROW_NUMBER() OVER(PARTITION BY A.DONDATHANGID ORDER BY A.CTKMID DESC)  AS SOTT , "+
		"	isnull(b.MA, ' ') as MaSanPham, isnull(b.TEN, ' ') as TenSanPham, "+ 
		"				isnull(c.DONVI, ' ') as donvi,isnull(a.soluong, 0) as soluong, isnull(a.soluong, 0) as SoLuongTT, "+
		"				0 as DonGiaGoc,0 as DonGiaSauCK,0 as ptVAT "+
		"				,a.tonggiatri as ThanhTien,d.SCHEME ,'' as  NgayGiaoHang,'' as  GhiChu  "+
		"				from ERP_DONDATHANG_CTKM_TRAKM a left join SANPHAM b on a.SPMA = b.MA   "+
		"				left join DONVIDOLUONG c on b.DVDL_FK = c.PK_SEQ   "+
		"				inner join CTKHUYENMAI d on a.ctkmID = d.PK_SEQ "+
		"			where a.dondathangID = '" + id + "'  "+
		"			order by SOTT	";
				ResultSet rs = db.get(query);

				ResultSetMetaData rsmd = rs.getMetaData();

				int socottrongSql = rsmd.getColumnCount();// số cột trong câu query[3 cột]

				int countRow = 0;// dòng đầu tiên dữ liệu
				int column =0;// cột đầu tiên dữ liệu


				//Vẽ header 
				for( int i =1 ; i <=socottrongSql ; i ++  )
				{
					cell = cells.getCell(countRow,i-1 + column);cell.setValue(rsmd.getColumnName(i));
					ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, true, 0);
					Style style;
					style=cell.getStyle();
					style.setTextWrapped(true);
					style.setHAlignment(TextAlignmentType.CENTER);
					style.setVAlignment(TextAlignmentType.CENTER);
					cell.setStyle(style);
				}// Vẽ ra  ->  Mã NV |  Đại diện kinh doanh |   Doanh Số    

				countRow ++;

				// đỗ dữ liệu
				while(rs.next())
				{
					for(int i =1;i <=socottrongSql ; i ++)
					{
						if(!rsmd.getColumnName(i).equals("SoTT"))
						{
							cell = cells.getCell(countRow,i-1 );
							if(rsmd.getColumnType(i) == Types.DOUBLE )// số liệu dạng float để đổ pivot bên value
							{
								ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
								cell.setValue(rs.getDouble(i));
							}
							else
							{
									cell.setValue(rs.getString(i)); 
									ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
							}
						}
					}
					++countRow;
				}
				
				
				
				if(rs!=null)rs.close();
				if(db!=null){
					db.shutDown();
				}


			}catch(Exception ex)
			{
				ex.printStackTrace();
				System.out.println("Errrorr : "+ex.toString());
				throw new Exception(" abcd");
			}



			OutputStream out = response.getOutputStream();
			workbook.save(out);
			out.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return "";
	}
}
