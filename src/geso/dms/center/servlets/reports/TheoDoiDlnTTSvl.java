package geso.dms.center.servlets.reports;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.distributor.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
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
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

/*
 Thiet lap 3 function cho thuc hien viec lay doanh so 
 theo cac muc khach nhau.... 
 */

public class TheoDoiDlnTTSvl extends HttpServlet {
		
	private static final long serialVersionUID = 1L;

	public TheoDoiDlnTTSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		String querystring = request.getQueryString();
		Utility util = new Utility();
		
		obj.setuserId(util.getUserId(querystring));
		obj.setuserTen((String) session.getAttribute("userTen"));
		
	
		
		
		/*String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
		if(view == null)
			view = "NPP";*/
		
	//	obj.setLoaiMenu(view);
		
		
		session.setAttribute("obj", obj);
		String nextJSP = "/AHF/pages/Center/TheoDoiDLNTT.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		OutputStream out = response.getOutputStream();
		
		
		
		String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
		System.out.println("loaiMenu = " + view);
		if(view == null)
			view = "NPP";
		
		obj.setLoaiMenu(view);
		
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		
		obj.setuserId(userId!=null? userId:"");
		obj.setuserTen(userTen!=null? userTen:"");
		Utility util = new Utility();
		
		obj.settungay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays"))));
		obj.setdenngay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays"))));
		
		obj.setDlnId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dlnid")))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dlnid"))):"");
		
		
		String action = (String) util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")));
		String nextJSP = "/AHF/pages/Center/TheoDoiDLNTT.jsp";
		
		System.out.println("Action la: " + action);
		System.out.println("DLNId la: " + obj.getDlnId());
		String ten = "";
		dbutils db = new dbutils();
		if(obj.getDlnId() != null && obj.getDlnId().length() > 0)
		{
			
			ResultSet rs = db.get("select ten from ungdung where pk_seq = '"+obj.getDlnId()+"'");
			try {
				rs.next();
				ten = rs.getString("ten");
				rs.close();
			} catch (SQLException e) 
			{
				
				e.printStackTrace();
			}
		}
		try{
			if (action.equals("Taomoi")) 
			{			
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=TheoDoiDLN.xlsm");
					
				String query = setQuery(obj);
				Workbook workbook = new Workbook();
				TaoBaoCao(workbook, userTen, query,ten);
				System.out.println("Query lay du lieu: " + query + "\n");
				workbook.save(out);	
			}
			else
			{
				response.sendRedirect(nextJSP);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("");
			obj.setMsg("Lỗi không tạo được báo cáo ! Vui Lòng kiểm tra lại");
			response.sendRedirect(nextJSP);
		}
		db.shutDown();
		session.setAttribute("obj", obj);	
	}	
	
	private String setQuery (IStockintransit obj) {
		String query = "";
		if(obj.getDlnId().equals("100168") )
		{
			 query = "select distinct a.pk_seq as MaHeThong,a.smartid AS MAKH, a.ten as TenKH, a.TrangThai, a.NgayTao, a.NgaySua, isnull(a.masothue, '') as MASOTHUE, b.ten as NGUOITAO, c.ten as NGUOISUA, a.CHIETKHAU,isnull(a.ismt,'') as KHIP, ";
		    query = query + "e.diengiai as KENHBANHANG, f.hang as HANGCUAHANG, g.loai as LOAICUAHANG,h.ten as NHAPHANPHOI,  "; 
		    query = query + " m.vitri as VITRI, a.DIENTHOAI, a.DIACHI,A.TEN as TINHTHANH, o.Ten as QUANHUYEN,px.ten as PHUONGXA,a.SONGAYNO,a.SOTIENNO,isnull(a.dientichcuahang,'') as DIENTICHCUAHANG,a.CHIETKHAU,nvgn.ten as NVGN,a.NGAYCHINHSUA ";
		    query = query + "from khachhang_UPDATE a inner join nhanvien b on a.nguoitao = b.pk_seq inner join nhanvien c on a.nguoisua = c.pk_seq  ";
		    query = query + "left join kenhbanhang e on a.kbh_fk = e.pk_seq left join hangcuahang f on a.hch_fk = f.pk_seq left join loaicuahang g on a.lch_fk = g.pk_seq ";
		    query = query + "left join nhaphanphoi h on a.npp_fk = h.pk_seq  ";
		    query = query + "left join vitricuahang m on a.vtch_fk = m.pk_seq left join nvgn_kh on a.pk_seq = nvgn_kh.khachhang_fk "
		    			  + "left join nhanviengiaonhan nvgn on nvgn.pk_seq = nvgn_kh.nvgn_fk " 
		    			  + "left join tinhthanh n on a.tinhthanh_fk = n.pk_seq "
		    			  + "left join quanhuyen o on a.quanhuyen_fk = o.pk_seq "
		    			  + "left join PHUONGXA px on a.phuongxa_fk = px.pk_seq ";
		
		    if(obj.gettungay().length() > 0 )
		    {
		    	query+= " and a.ngaysua >='"+obj.gettungay()+"' ";
		    }
		    if(obj.getdenngay().length() > 0 )
		    {
		    	query+= " and a.ngaysua <='"+obj.getdenngay()+"' ";
		    }
		    query = query + "order by ngaychinhsua ";
		   

		}else
			if( obj.getDlnId().equals("100025")) 
				{
				 query = " select a.pk_seq as MAHT,a.MANPP AS MANPP, a.khosap as KHOTT,a.MASOTHUE,a.DIACHIXHD, a.ten as TENNPP, a.DIENTHOAI, a.DIACHI, a.TRANGTHAI, "
									+ " a.NGAYTAO, b.TEN AS NGUOITAO, a.NGAYSUA, c.TEN AS NGUOISUA, d.ten as KHUVUC, "
									+ " n.ten as TINHTHANH,o.ten as QUANHUYEN, a.SOTAIKHOANNH, a.FAX,isnull(a.DiaBanHd,'') AS DiaBanHd ,"
									+ " ISNULL(a.NganHang,'')NganHang,ISNULL(a.ChiNhanh,'')ChiNhanh,ISNULL(a.ChuTaiKhoan,'')as ChuTaiKhoan,isnull(a.tentat,a.ten) as TenTat,a.IsChiNhanh,a.SoNgayNo,a.SoTienNo,a.loainpp, ISNULL(a.nguoidaidien,'')as NGUOIDAIDIEN "
									+ ",a.CHUNHAPHANPHOI, a.TONANTOAN,a.QUYTRINHBANHANG, A.DIACHI2, A.PTTANGTRUONG, A.NPPC1,A.NGAYCHINHSUA "
									+ "from "
									+ " nhaphanphoi_update a inner join nhanvien b on b.pk_seq=a.nguoitao "
									+ " inner join  nhanvien c on c.pk_seq=a.nguoisua "
									+ " left join  khuvuc d  on a.khuvuc_fk=d.pk_seq "
									+ "left join tinhthanh n on a.tinhthanh_fk = n.pk_seq "
					    			+ "left join quanhuyen o on a.quanhuyen_fk = o.pk_seq ";
				  
					  if(obj.gettungay().length() > 0 )
					    {
					    	query+= " and a.ngaysua >='"+obj.gettungay()+"' ";
					    }
					    if(obj.getdenngay().length() > 0 )
					    {
					    	query+= " and a.ngaysua <='"+obj.getdenngay()+"' ";
					    }
					    query = query + "order by ngaychinhsua ";
				}else
					if( obj.getDlnId().equals("1")) 
					{
					 query = " select d.SMARTID as [Mã NVBH],d.ten [Tên],kh.smartid [Mã KH],kh.ten [Tên KH],a.tanso [Tần số],a.ngayid [Thứ],npp.Ten [Nhà phân phối],Ngaychinhsua [Ngày chỉnh sửa],b.ten [Người sửa],isLog [Thông tin tuyến] "
										+ "from "
										+ " khachhang_tuyenbanhang_Update a inner join nhanvien b on b.pk_seq=a.nguoisua "
										+ " inner join  nhanvien c on c.pk_seq=a.nguoisua "
										+ " inner join  daidienkinhdoanh d  on a.ddkd_FK = d.pk_seq "
										+ "inner join khachhang kh on a.khachhang_fk = kh.pk_seq "
										+ "\n inner join nhaphanphoi npp on npp.pk_seq = a.npp_FK ";
						    			
					  
					 if(obj.gettungay().length() > 0 )
					    {
					    	query+= " and convert(varchar(10),a.ngaychinhsua,126) >='"+obj.gettungay()+"' ";
					    }
					    if(obj.getdenngay().length() > 0 )
					    {
					    	query+= "  and convert(varchar(10),a.ngaychinhsua,126) <='"+obj.getdenngay()+"' ";
					    }
					    query+= "  order by d.SMARTID,ngaychinhsua desc,islog asc ";
					}
		
		 System.out.println("Query :"+query);
			
		
		
		
	    
	     return query;			
		
	}	
	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	private void TaoBaoCao(Workbook workbook,String nguoitao,String query,String ten )throws Exception
	{
		try{
			
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Sheet1");

			Cells cells = worksheet.getCells();
			cells.setRowHeight(0, 50.0f);
			Cell cell = cells.getCell("A1");
			ReportAPI.getCellStyle(cell, Color.RED, true, 16,"Báo Cáo Theo Dõi Dữ Liệu Nền "+ten);

			cell = cells.getCell("A3");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Ngày tạo : "+ getDateTime());
			cells.setColumnWidth(4, 20.0f);
			cells.setColumnWidth(9, 15.0f);
			cells.setColumnWidth(8, 15.0f);
			cells.setColumnWidth(7, 15.0f);
			cells.setColumnWidth(6, 15.0f);
			cell = cells.getCell("A4");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10,"Người tạo : " + nguoitao);

			worksheet.setGridlinesVisible(false);
			dbutils db = new dbutils();
			ResultSet rs = db.get(query);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int socottrongSql = rsmd.getColumnCount();
			
			int countRow = 8;

			for( int i =1 ; i <=socottrongSql ; i ++  )
			{
				cell = cells.getCell(countRow,i-1 );cell.setValue(rsmd.getColumnName(i));
				ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);	
			 
			}
			countRow ++;
			
			
			while(rs.next())
			{
				boolean kt = false;
				for(int i =1;i <=socottrongSql ; i ++)
				{
					cell = cells.getCell(countRow,i-1 );
					if(rsmd.getColumnName(i).equals("TITTLE"))
					{
						
						cell.setValue(rs.getString(i));
					
							ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
						
					}else
					if(rsmd.getColumnType(i) == Types.DOUBLE || rsmd.getColumnType(i) == Types.INTEGER || rsmd.getColumnType(i) == Types.DECIMAL)
					{
						cell.setValue(rs.getDouble(i));
					
							ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
					}
					else
					{
						cell.setValue(rs.getString(i));
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
			ex.printStackTrace();
			throw new Exception("Lỗi không tạo được báo cáo !");
		}
	}
	
}
