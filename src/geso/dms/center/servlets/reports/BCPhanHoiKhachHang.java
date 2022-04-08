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
import java.sql.Types;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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


@WebServlet("/BCPhanHoiKhachHang")
public class BCPhanHoiKhachHang extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public BCPhanHoiKhachHang() {
		super();
		// TODO Auto-generated constructor stub
	}

	String nextJSP="";
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset:UTF-8");
		HttpSession session = request.getSession();
		String querystring = request.getQueryString();
		Utility util =new Utility();

		String userId = util.getUserId(querystring);
		//out.println(userId);

		Stockintransit obj = new Stockintransit();
		obj.setuserId(userId);
		obj.init();
		session.setAttribute("obj", obj);
		nextJSP="/AHF/pages/Center/BCPhanHoiKhachHang.jsp";
		response.sendRedirect(nextJSP);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset:UTF-8");
		HttpSession session = request.getSession();
		String userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
		String userTen = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userTen"));
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		Stockintransit obj = new Stockintransit();
		obj.setuserTen(userTen);

		String tungay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays"));
		obj.settungay(tungay);

		String denngay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays"));
		obj.setdenngay(denngay);
		String nppid = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhapp"));
		obj.setnppId(nppid);
		
		String vungId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"));
		if (vungId == null)
			vungId = "";
		obj.setvungId(vungId);
		
		String khuvucId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId"));
		if (khuvucId == null)
			khuvucId = "";
		obj.setkhuvucId(khuvucId);

		if(action.equals("excel"))
		{
			try{
			response.setContentType("application/xlsm");
			response.setCharacterEncoding("utf-8");
			response.setHeader("Content-Disposition", "attachment; filename=BCPhanHoiKhachHang.xlsm");
			
			
				String query  = setQuery(obj);
				OutputStream out = response.getOutputStream();
				ExportToExcel( response, out, obj, query );
				session.setAttribute("obj", obj);
				String nextJSP = "/AHF/pages/Center/BCPhanHoiKhachHang.jsp";
				response.sendRedirect(nextJSP);
			}
			catch(Exception e)
			{
				obj.setMsg("Không thể xuất excel");
				session.setAttribute("obj", obj);
				String nextJSP = "/AHF/pages/Center/BCPhanHoiKhachHang.jsp";
				response.sendRedirect(nextJSP);
			}
		}
		else
		{
			obj.setuserId(userId);
			obj.init();
			session.setAttribute("obj", obj);
			nextJSP="/AHF/pages/Center/BCPhanHoiKhachHang.jsp";
			response.sendRedirect(nextJSP);
		}
	}

	public String setQuery(Stockintransit obj)
	{
		String query=
			"\n 			  select yk.Ngay as [Ngày],kbh.TEN as [Kênh Bán Hàng],v.ten as [Miền],tt.TEN as [Khu vực],npp.TEN as [Nhà Phân Phối]  " + 
			"\n 				,ddkd.ten as [NVBH]	,kh.SMARTID as [Mã KH], kh.TEN as [Khách Hàng] ,yk.TIEUDE as [Tiêu Đề], yk.NOIDUNG as [Nội Dung]  " + 
			"\n 			  from KHACHHANG_YKIEN yk inner join  " + 
			"\n 			  khachhang kh on yk.khachhang_fk = kh.pk_seq     " + 
			"\n 			  inner join loaicuahang lch on kh.lch_fk = lch.pk_seq      " +
			"\n			      inner join daidienkinhdoanh ddkd on ddkd.pk_seq = yk.ddkd_fk	" + 
			"\n 			  inner join nhaphanphoi npp on kh.npp_fk = npp.pk_seq      " + 
			"\n 			  inner join KENHBANHANG kbh on kbh.PK_SEQ = kh.KBH_FK  " + 
			"\n 			  left join KHUVUC tt on tt.PK_SEQ = npp.KHUVUC_FK  " + 
			"\n 			  left join VUNG v on v.PK_SEQ = tt.VUNG_FK  " +
			"\n			  where yk.Ngay >='"+obj.gettungay()+"' and yk.Ngay <='"+obj.getdenngay()+"'	";		
		if(obj.getvungId().length()>0) 
		{
			query+= " and v.PK_SEQ in ('" + obj.getvungId() + ") ";
		}
		if(obj.getkhuvucId().length()>0) 
		{
			query+= " and tt.PK_SEQ in ('" + obj.getkhuvucId() + "') ";
		}
		if(obj.getnppId().length()>0) 
		{
			query+= " and kh.NPP_FK='" + obj.getnppId() + "' ";
		}



		System.out.println("Khách hàng không mua hàng : "+query);
		return query ;
	}

	private void TaoBaoCao(Workbook workbook,Stockintransit obj,String query )throws Exception
	{
		try{


			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Sheet1");

			Cells cells = worksheet.getCells();
			cells.setRowHeight(0, 50.0f);
			Cell cell = cells.getCell("A1");
			ReportAPI.getCellStyle(cell, Color.RED, true, 16,"PHẢN HỒI KHÁCH HÀNG");

			cell = cells.getCell("A3");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Ngày tạo : "+ obj.getDateTime());
			cell = cells.getCell("A4");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10,"Người tạo : " + obj.getuserTen());

			worksheet.setGridlinesVisible(false);
			dbutils db = new dbutils();
			ResultSet rs = db.get(query);

			ResultSetMetaData rsmd = rs.getMetaData();
			int socottrongSql = rsmd.getColumnCount();

			int countRow = 8;

			for( int i =1 ; i <=socottrongSql ; i ++  )
			{
				cell = cells.getCell(countRow,i-1 );cell.setValue(rsmd.getColumnName(i));
				ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);	

			}
			countRow ++;


			while(rs.next())
			{
				for(int i =1;i <=socottrongSql ; i ++)
				{
					cell = cells.getCell(countRow,i-1 );
					if(rsmd.getColumnType(i) == Types.DOUBLE )
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
			throw new Exception("Qua trinh dien du lieu vao file Excel va tao PivotTable bi loi.!!!");
		}
	}

	private void ExportToExcel(HttpServletResponse response,OutputStream out,Stockintransit obj,String query )throws Exception
	{
		try{ 		
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=BCPhanHoiKhachHang.xlsm");
    		
			
			
//			response.setCharacterEncoding("UTF-8");
//			response.setContentType("text/html;charset:UTF-8");
			
			Workbook workbook = new Workbook();
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			TaoBaoCao(workbook,obj,query);		
			workbook.save(out);	

		}catch(Exception ex){
			throw new Exception(ex.getMessage());
		}

	}

}
