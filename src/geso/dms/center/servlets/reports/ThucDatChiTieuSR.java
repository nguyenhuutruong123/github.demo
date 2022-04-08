package geso.dms.center.servlets.reports;

 import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.distributor.beans.khachhang.IKhachhangList;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
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
import com.aspose.cells.HorizontalAlignmentType;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
//import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;
import com.aspose.cells.Worksheets;


public class ThucDatChiTieuSR extends HttpServlet {
	
	private static final long serialVersionUID = 1L; 
	
	public ThucDatChiTieuSR() {
		super();
	}

	//Hashtable<String, String> HeaderHash = new Hashtable<String, String>();
	Map<String, String> HeaderHash = new HashMap<String, String>();
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
		obj.init();

		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/AHF/pages/Center/ThucDatChiTieuSR.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();	
		Utility util = new Utility();

		obj.setuserId((String)session.getAttribute("userId")==null?"":
			(String) session.getAttribute("userId"));

		obj.setuserTen((String)session.getAttribute("userTen")==null? "":
			(String) session.getAttribute("userTen"));

		obj.setnppId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")))==null?"":
			 util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"))));

		obj.setkenhId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")))==null? "":
			util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId"))));

		obj.setdvkdId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")))==null? "":
			util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId"))));

		obj.setMonth(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("month")))==null? "":
			util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("month"))));

		obj.setYear(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("year")))==null? "":
			util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("year"))));	   	 

		obj.setvungId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")))==null? "":
			util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"))));	   	 

		obj.setkhuvucId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")))==null? "":
			util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId"))));	 


		obj.setdvdlId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvdlId")))==null? "":
			util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvdlId"))));
		obj.setLoaiNv(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loainv")))==null? "":
			util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loainv"))));

		obj.setDdkd(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdId")))==null? "":
			util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdId"))));

		if(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("covat")))!= null)
    		obj.setcovat("1");
		else
			obj.setcovat("0");
		

		String []fieldsHien = request.getParameterValues("fieldsHien");
		obj.setFieldShow(fieldsHien);		 

		String[] vungid = util.antiSQLInspection_Array(request.getParameterValues("vungId"));
		String s = "";
		if(vungid != null)
		{
			
			for(int i = 0; i < vungid.length; i++)
			{
				s += vungid[i] +",";
			}
			if(s.length() > 0)
			{
				s = s.substring(0,s.length() -1);
			}
		
			
		}
		System.out.println("VUng: "+s);
		obj.setvungId(s);
		String[] khuvucId = util.antiSQLInspection_Array(request.getParameterValues("khuvucId"));  
		s = "";
		if(khuvucId != null)
		{
			
			for(int i = 0; i < khuvucId.length; i++)
			{
				s += khuvucId[i] +",";
			}
			if(s.length() > 0)
			{
				s = s.substring(0,s.length() -1);
			}
		
			
		}
		obj.setkhuvucId(s);
		String[] nppId =  util.antiSQLInspection_Array(request.getParameterValues("nppId"));   
		 s = "";
		if(nppId != null)
		{
			
			for(int i = 0; i < nppId.length; i++)
			{
				s += nppId[i] +",";
			}
			if(s.length() > 0)
			{
				s = s.substring(0,s.length() -1);
			}
		
		}
		if(s.length() <= 0)
		{
			s = " select pk_seq from nhaphanphoi where 1 = 1 ";
			
			
		
			if(obj.getkhuvucId().length() > 0)
			{
				s+= " and khuvuc_fk in ("+obj.getkhuvucId()+") ";
			}
			
			if(obj.getvungId().length() > 0)
			{
				s+= " and khuvuc_fk in (select pk_seq from khuvuc where vung_fk in ("+obj.getvungId()+") )";
			}
		}
		obj.setnppId(s);
		String nextJSP = "/AHF/pages/Center/ThucDatChiTieuSR.jsp";		 
		try
		{
			String action=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")));
			System.out.println("action _______+________"+action);
			if(action.equals("Taomoi")){
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=ThucHienChiTieu.xlsm");
				OutputStream out = response.getOutputStream();
				ExportToExcel(out,obj);			
				return;
			}	
			if(action.equals("xuatkhmoi")){
				OutputStream outstream = response.getOutputStream();
				String query = getSearchQuery_Excel(obj);

				try {
					response.setContentType("application/vnd.ms-excel");
					//response.setContentType("application/xlsm");
					response.setHeader("Content-Disposition","attachment; filename=DanhSachKhachHang_"+ getDateTime() + ".xlsm");
					Workbook workbook = new Workbook();
					workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

					CreateStaticHeader(workbook, obj.getuserId());
					CreateStaticData(workbook, query);

					// Saving the Excel file
					workbook.save(outstream);
				} catch (Exception ex) {
					ex.printStackTrace();
					obj.setMsg("Exception : " + ex.getMessage());
				}

				session.setAttribute("obj", obj);
				session.setAttribute("userId", obj.getuserId());
				return;
			}
			if(action.equals("chot"))
			{
				String msg =  KhoaSo( obj);
				obj.setMsg(msg);
				session.setAttribute("obj", obj);
				session.setAttribute("userId", obj.getuserId());
			}	
		}catch(Exception ex){
			obj.setMsg(ex.getMessage());
		}
		obj.init();	    
		session.setAttribute("obj", obj);
		session.setAttribute("userId", obj.getuserId());		
		response.sendRedirect(nextJSP);
	}

	private String GetExcelColumnName(int columnNumber)
	{
		int dividend = columnNumber;
		String columnName = "";
		int modulo;

		while (dividend > 0)
		{
			modulo = (dividend - 1) % 26;
			columnName = (char)(65 + modulo) + columnName;
			dividend = (int)((dividend - modulo) / 26);
		} 

		return columnName;
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private void TaoBaoCao(com.aspose.cells.Workbook workbook, IStockintransit obj )throws Exception
	{
		int countRow = 10;
		int column = 0;
		//Chỉnh sửa theo Template mới
		try{
			
			String query =  setQuery(obj);
			
			com.aspose.cells.Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("ThucDatChiTieu");
			com.aspose.cells.Cells cells = worksheet.getCells();
			Cell cell = cells.getCell("A1");	
			ReportAPI.getCellStyle(cell,Color.RED, true, 16, "Thực Hiện Chỉ Tiêu SR");
			cell = cells.getCell("A4");
			ReportAPI.getCellStyle(cell,Color.NAVY,true,10,"Ngày tạo : " + this.getDateTime()); 
			cell = cells.getCell("A5");
			ReportAPI.getCellStyle(cell,Color.NAVY,true,10,"Người tạo : " + obj.getuserTen());

			Style style;
			Font font = new Font();
			font.setColor(Color.RED);//mau chu
			font.setSize(16);// size chu
			font.setBold(true);

			ResultSet rs = obj.getDb().get(query);
			ResultSetMetaData rsmd = rs.getMetaData();
			int socottrongSql = rsmd.getColumnCount();

			//Set Header phụ
			String sql = "";
			ArrayList<String> temp_arr = obj.getArr_text_baocaoSR();
			if (temp_arr != null && temp_arr.size() > 0);
			{
				for (int i = 0; i < temp_arr.size(); i ++) {
					sql += ", 'a' "+temp_arr.get(i);
				}
				if (sql !=  null && sql.length() > 0) {
					sql = sql.substring(1);
					sql = "select "+sql;
				}
			}
			ResultSet temprs = obj.getDb().get(sql);
			ResultSetMetaData rsmd2 = temprs.getMetaData();
			int socottieude = rsmd2.getColumnCount();
			System.out.println("temp_arr.size: "+temp_arr.size()+"socottieude "+socottieude+" sql: "+sql);
			int x = 4;
			Map<String, String> sortedMap = new TreeMap<String, String>(HeaderHash);
			Set<String> keys = sortedMap.keySet();
			int j = 11;
	        for(String key: keys){
	            System.out.println("Key: " + key+ " -- Value: "+ sortedMap.get(key));
				cell = cells.getCell(countRow - 1, column + j - 1 );cell.setValue( sortedMap.get(key) ); cells.setRowHeight(countRow - 1, 23.0f);
				ReportAPI.mergeCells(worksheet, countRow - 1, countRow - 1, column + j - 1, column + j + 2);
				ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
				cell = cells.getCell(countRow - 1, column + j ); ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
				cell = cells.getCell(countRow - 1, column + j + 1 );ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
				cell = cells.getCell(countRow - 1, column + j + 2 );	ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
				j = j + 4;
	        }
	        sortedMap.clear();
			HeaderHash.clear();
			
			//Header chính
			for( int i =1 ; i <=socottrongSql -1; i ++  )
			{
				//System.out.println("rsmd.getColumnName("+i+") : "+ rsmd.getColumnName(i));
				//cell = cells.getCell(countRow - 1, column + i-1 );cell.setValue(rsmd.getColumnName(i)); cells.setRowHeight(countRow, 23.0f);
				cell = cells.getCell(countRow, column + i-1 );cell.setValue(rsmd.getColumnName(i)); cells.setRowHeight(countRow, 23.0f);
				ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			}
			
			countRow ++;

			//Nội dung
			int stt = 0;
			double diem = 0;
			//long mang[] = new long[10000];
			while(rs.next())
			{
				Color c = Color.WHITE;
				boolean bold =false;
				
				//mang[stt] = rs.getLong("pk_seq");

				stt++;

				for(int i = 1;i <=socottrongSql -1; i ++)
				{
					if(i ==socottrongSql)
					{
						diem+= rs.getDouble(i);
					}
					
					cell = cells.getCell(countRow,column + i-1 );
					if(rsmd.getColumnName(i).equals("STT"))
					{					
						cell.setValue(stt);
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
						//System.out.println("STT: "+stt);

					}else
						if(rsmd.getColumnType(i) == Types.DOUBLE || rsmd.getColumnType(i) == Types.INTEGER || rsmd.getColumnType(i) == Types.DECIMAL )
						{
							int format = 43;
							if(rsmd.getColumnName(i).contains("Tỷ Lệ"))	
							{
								format = 10;
							}
							cell.setValue(rs.getDouble(i));
							ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, bold, format);
							//cells.setColumnWidth(column + i-1, 30.0f);
							worksheet.autoFitColumn(column + i-1);
						}
						else
						{
							cell.setValue(rs.getString(i));
							ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, bold, 0);
							//cells.setColumnWidth(column + i-1, 30.0f);
							worksheet.autoFitColumn(column + i-1);
						}
					
					cells.setRowHeight(countRow, 23.0f);
				}

				++countRow;
			}

			if(rs!=null)
			{
				rs.close();
			}

		}catch(Exception ex){

			ex.printStackTrace();
			throw new Exception("Lỗi ! Không có dữ liệu để xuất file !");
		}

	
		
	}
	
	public String KhoaSo(IStockintransit obj)
	{
		dbutils db = new dbutils();
		try
		{
			String dauthang = obj.getYear() + "-" + (obj.getMonth().trim().length() > 1 ? obj.getMonth() : "0" + obj.getMonth()) + "-01";
			db.getConnection().setAutoCommit(false);
			
			String query = " delete KhoaSoChiTieu_DDKD where thang ="+obj.getMonth()+" and nam =  "+obj.getYear()+" ";
			if(obj.getnppId().trim().length() >0)
				query += "  and NHANVIEN_FK in (select pk_seq from daidienkinhdoanh where NPP_FK  in ("+obj.getnppId()+" )) ";
			System.out.println("____"+query);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return "lỗi \n " + query;
			}
			
			
			 query =  			"\n select a.loai isTinhLuong,a.tieuchi,a.pk_seq, a.diengiai from tieuchithuong_chitiet a" +
			"\n inner join tieuchitinhthuong b on a.TIEUCHITINHTHUONG_FK = b.PK_SEQ where b.THANG = "+obj.getMonth()+" and b.NAM = "+obj.getYear()+
			"\n and b.TRANGTHAI = 1 and b.LOAI = 1  " +
			"\n and exists (select 1 from  ChiTieuNhanVien_DDKD ct inner join chitieunhanvien ctnv on ct.ctnv_fk = ctnv.pk_seq " +
			"\n where ct.TCTCT_FK = a.pk_seq and ct.chitieu > 0 and ctnv.trangthai = 1 ) ";
			System.out.println("QRBC "+query );
			ResultSet rs = db.get(query);
			boolean daInsert = false;
			while(rs.next())
			{
				String tctctId = rs.getString("pk_seq");
				String tieuchi = rs.getString("tieuchi");
				String diengiai = rs.getString("diengiai");
						query =	"\n insert KhoaSoChiTieu_DDKD(thang,nam,CTNV_FK,TCTCT_FK,NSP_FK,TIEUCHI,NhanVien_FK,ChiTieu,ThucDat,TyLeDat,Thuong,	nguoitao) " +
								"\n select "+obj.getMonth()+" thang, "+obj.getYear()+" nam " +
								"\n		,ct.CTNV_FK,ct.tctct_fk,ct.NSP_FK,ct.TIEUCHI,ct.NhanVien_FK " +
								"\n		,ct.ChiTieu,isnull(td.thucdat,0)thucdat	, dbo.PhepChia(isnull(td.thucdat,0),cast(ct.ChiTieu as float) ) tyle,0 thuong" +
								"\n		,"+obj.getuserId()+" nguoitao	  " +
								"\n from ChiTieuNhanVien_DDKD ct " +
								"\n inner join tieuchithuong_chitiet tct on ct.tctct_fk = tct.pk_seq  " +
								"\n left join [dbo].[ufn_KPI_SR]("+dauthang+"," + tctctId+","+tieuchi+",null) td on ct.NhanVien_FK = td.NhanVien_FK " +
								"\n where ct.chitieu > 0  " +
								"\n and ct.tctct_fk = " + tctctId+" ";
						if(obj.getnppId().trim().length() >0)
							query += "  and ct.NhanVien_FK in (select pk_seq from daidienkinhdoanh where NPP_FK in ("+obj.getnppId()+" )) ";
						
				System.out.println("query bc= " + query);
				if(db.updateReturnInt(query)<0)
				{
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					return "lỗi khóa sổ tiêu chí("+diengiai+"):\n " + query;
				}
				
				
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return "Lưu chỉ tiêu tháng được chọn thành công";
		}
		catch (Exception e) {
			try {	db.getConnection().rollback();} catch (SQLException e1) {}
			try {	db.getConnection().setAutoCommit(true);} catch (SQLException e1) {}
			return "Lỗi:" + e.getMessage();
		}
		
	}
	
	private void ExportToExcel(OutputStream out,IStockintransit obj )throws Exception
	{
		try
		{ 			
			com.aspose.cells.Workbook workbook = new com.aspose.cells.Workbook();
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			
			TaoBaoCao(workbook, obj);
			workbook.save(out);			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}
	}
	
	
	public String setQuery (IStockintransit obj) throws SQLException {

		String quyen = "\n select PK_SEQ   " + 
		"\n from DAIDIENKINHDOANH yddkd  " + 
		"\n where exists   " + 
		"\n (  " + 
		"\n 	select 1 from nhanvien xnv where xnv.pk_seq =  " + obj.getuserId() + 
		"\n 		and (  	   (  xnv.loai !=3 and  exists (select 1 where yddkd.NPP_FK in ( select npp_fk from phamvihoatdong where nhanvien_fk = xnv.pk_seq )   )        )	 	   " + 
		"\n 				or (  xnv.loai = 3 and  exists (select 1 from ddkd_gsbh where gsbh_fk = xnv.gsbh_fk and ddkd_fk = yddkd.pk_seq   )        )	  " + 		 
		"\n 		)  " + 
		"\n 		  " + 
		"\n )  " ;

		String diengiai = "";
		String query = "";

		String sql =  	"\n select a.loai isTinhLuong,a.tieuchi,a.pk_seq, a.diengiai from tieuchithuong_chitiet a" +
		"\n inner join tieuchitinhthuong b on a.TIEUCHITINHTHUONG_FK = b.PK_SEQ where b.THANG = "+obj.getMonth()+" and b.NAM = "+obj.getYear()+
		"\n and b.TRANGTHAI = 1 and b.LOAI = 1  " ;

		if(obj.getDdkd().length()  > 0)
		{
			sql += "\n and exists (select 1 from  ChiTieuNhanVien_DDKD ct "
				+ " where ct.TCTCT_FK = a.pk_seq  and ct.NhanVien_FK = '"+obj.getDdkd()+"' and ct.chitieu > 0 ) ";
		}

		sql += "\n and exists (select 1 from  ChiTieuNhanVien_DDKD ct inner join chitieunhanvien ctnv on ct.ctnv_fk = ctnv.pk_seq "
			+ " where ct.TCTCT_FK = a.pk_seq and ct.chitieu > 0 and ctnv.trangthai = 1 "
			+ ") order by a.pk_seq ";

		System.out.println("QRBC "+sql );
		String tct_fks = "";
		ResultSet rs = obj.getDb().get(sql);
		HeaderHash.clear();
		while(rs.next())
		{
			if(tct_fks.trim().length() > 0)
				tct_fks += "," + rs.getString("pk_seq");
			else
				tct_fks = rs.getString("pk_seq");
			if(diengiai.trim().length() > 0)
				diengiai += "__" + rs.getString("pk_seq");
			else
				diengiai += rs.getString("pk_seq");

			HeaderHash.put(rs.getString("pk_seq"), rs.getString("diengiai"));
		}

		query = "\n select " +
		
   				/*"\n convert(varchar(10), ROW_NUMBER() OVER (PARTITION BY smartid ORDER BY ddkd.smartId desc, loai desc, tct.tieuchi asc),120) [Tên Chỉ Tiêu], "+
   				"\n ct.tieuchi as loai, " +*/
   				"\n cast( tct.pk_seq as varchar) [Tên Chỉ Tiêu], "+
				
				" ddkd.pk_seq  " +
				"\n		,kbh.DIENGIAI as [KÊNH BÁN HÀNG],ddkd.smartId as [MÃ NV], ddkd.Ten as [TDV] " +
				"\n 	,npp.ten [Nhà Phân Phối],npp.manpp [Mã Nhà Phân Phối],isnull(a3.SMARTID,'') as [Mã ASM],isnull(a3.TEN,'') as [Tên ASM] " +
				"\n 	,a2.smartid [Mã GSBG],a2.ten [Tên GSBH] " +
				"\n		,ct.ChiTieu as [Chỉ Tiêu], isnull(td.thucdat,0) [Thực đạt] , dbo.PhepChia(isnull(td.thucdat,0),cast(ct.ChiTieu as float) ) [Tỷ lệ đạt]" +
				"\n		, case when ISNULL(tct.LOAIDS,0) = 1 then  isnull(td.DoanhSo,0) * muc.thuong / 100.0 else   muc.thuong end [Thưởng]  " +
				"\n from ChiTieuNhanVien_DDKD ct " +
				"\n inner join DaiDienKinhDoanh ddkd on ct.NhanVien_FK = ddkd.pk_seq " +
				"\n inner join ddkd_gsbh a1 on a1.ddkd_fk = ddkd.pk_seq " +
				"\n inner join giamsatbanhang a2 on a2.pk_seq = a1.gsbh_fk " +
				"\n LEFT join asm a3 on a3.pk_seq = a2.asm_fk  " +
				"\n LEFT join KENHBANHANG kbh on kbh.pk_seq = ddkd.kbh_fk  " +
				"\n inner join nhaphanphoi npp on npp.pk_seq = ddkd.npp_fk" +
				"\n inner join tieuchithuong_chitiet tct on ct.tctct_fk = tct.pk_seq  " +
				"\n outer apply  [dbo].[ufn_KPI_DDKD_TEST]('"+obj.getYear() +"-"+ obj.getMonth() +"-01"+"',ct.tctct_fk,ct.NhanVien_FK) td   " +
				
				"\n outer apply " +
				"\n  ( " +
				"\n 	select max(thuong)thuong  " +
				"\n 	from TieuChiThuong_ChiTiet_MucThuong x " +
				"\n 	where x.TCTCT_FK = tct.PK_SEQ and  x.TU <=100* dbo.PhepChia(isnull(td.thucdat,0),cast(ct.ChiTieu as float) )  and x.DEN >=100* dbo.PhepChia(isnull(td.thucdat,0),cast(ct.ChiTieu as float) ) " + 
				"\n  )muc  " +
				
				"\n where ct.chitieu > 0  and ct.tctct_fk  in ("+tct_fks+") ";
		if(obj.getDdkd().length()  > 0)
		{
			query += "\n and ct.NhanVien_FK = '"+obj.getDdkd()+"' ";
		}

		if (obj.getnppId() != null && obj.getnppId().length() > 0)
			query += "\n and ddkd.npp_fk in ( "+obj.getnppId()+" )  ";

		query +=  "\n and ct.NhanVien_FK in ("+quyen+") ";

		String[] _diengiai = null;
		String text = "";
		String temp = "";
		System.out.println("diengiai : "+ diengiai);
		_diengiai = diengiai.split("__");
		String[] tieuchithuong = new String[]{"Chỉ Tiêu","Thực Đạt","Tỷ Lệ Đạt","Thưởng"}; 

		ArrayList<String> temp_arr = new ArrayList<String>();
		if (_diengiai != null && _diengiai.length > 0) {
			for (int i = 0; i<_diengiai.length; i++) {
				for (int ii = 0; ii < tieuchithuong.length; ii ++) {
					text += ","+"["+_diengiai[i]+"_"+tieuchithuong[ii]+"]";
					temp = "["+_diengiai[i]+"_"+tieuchithuong[ii]+"]";
					
					/*text += ","+"["+tieuchithuong[ii]+"]";
					temp = "["+tieuchithuong[ii]+"]";*/
					
					temp_arr.add(temp);
				}
			}

			if (temp_arr != null) {
				obj.setArr_text_baocaoSR(temp_arr);
			}
		}

		if (text != null && text.length() > 0 ) {
			text = text.substring(1);
			obj.setText_baocaoSR(text);
		}

		String header_query = "\n with temp as (";
		String footer_query = "\n ) " +
		"\n select   Row_number() OVER ( order by ( [Mã NV] ) asc)[STT],[Mã Nhà Phân Phối],[Nhà Phân Phối],[Mã ASM],[Tên ASM],[Mã GSBG],[Tên GSBH],[KÊNH BÁN HÀNG],[Mã NV],[TDV], " +
		"\n "+text+", " +
		"\n pk_seq " +
		"\n from  " +
		"\n (" +
		"\n 	select [Mã GSBG],[Tên GSBH],[Mã Nhà Phân Phối],[Nhà Phân Phối],[Mã ASM],[Tên ASM],[KÊNH BÁN HÀNG],[Mã NV],[TDV],[Tên Chỉ Tiêu]+'_'+col col,value,pk_seq from " +
		"\n 	( " +
		"\n 		select [Mã GSBG],[Tên GSBH],[Mã Nhà Phân Phối],[Mã ASM],[Tên ASM],[KÊNH BÁN HÀNG],[Mã NV],[TDV],[Nhà Phân Phối],[Tên Chỉ Tiêu],[Chỉ Tiêu],[Thực Đạt],[Tỷ Lệ Đạt],[Thưởng],pk_seq from temp " +
		"\n		) rt  " +
		"\n 	unpivot (value for col in ([Chỉ Tiêu],[Thực Đạt],[Tỷ Lệ Đạt],[Thưởng]) ) unpiv  " +
		"\n ) tp " +
		"\n pivot ( SUM(value) FOR col in ("+text+")) piv ";

		String abc = header_query + query + footer_query;
		System.out.println("Query báo cáo Template mới: "+abc);

		return abc;

	}
	private String getSearchQuery_Excel(IStockintransit obj) {
		Utility util = new Utility();
		String quyen = "\n select PK_SEQ   " + 
				"\n from DAIDIENKINHDOANH yddkd  " + 
				"\n where exists   " + 
				"\n (  " + 
				"\n 	select 1 from nhanvien xnv where xnv.pk_seq =  " + obj.getuserId() + 
				"\n 		and (  	   (  xnv.loai !=3 and  exists (select 1 where yddkd.NPP_FK in ( select npp_fk from phamvihoatdong where nhanvien_fk = xnv.pk_seq )   )        )	 	   " + 
				"\n 				or (  xnv.loai = 3 and  exists (select 1 from ddkd_gsbh where gsbh_fk = xnv.gsbh_fk and ddkd_fk = yddkd.pk_seq   )        )	  " + 		 
				"\n 		)  " + 
				"\n 		  " + 
				"\n )  " ;
		String query = "\n declare @DauThang varchar(10) = '"+obj.getYear() +"-"+ obj.getMonth() +"-01"+"'  "+
				"\n declare @CuoiThang varchar(10) = CONVERT(char(10),DATEADD(DAY,-(DAY(@DauThang)),DATEADD(MONTH,1,@DauThang)),120)   "+
				"select a.trangthai khtrangthai, mathamchieu, tbh.ngayid, tbh.tanso, " +
		"\n 'LAT: '+convert(varchar,isnull(a.lat,0))+' - LNG: '++convert(varchar,isnull(a.long,0))toado, " +
		"\n qh.ten qhten, tt.ten ttten, v.ten vten, kv.ten kvten, " +
		"\n isnull(a.lat,0)lat, isnull(a.long,0)long, " +
		"\n ddkd.ten ddkdten, ddkd.smartid ddkdma, gs.smartid gsbhma, gs.ten gsbhten, " +
		"\n isnull(route.coderoute,'')coderoute, " +
		"\n 	isnull(route.ten,'')routename, isnull(a.daduyet,0)daduyet, " +
		"\n  	--ROW_NUMBER() OVER(ORDER BY a.pk_seq DESC) AS 'stt', " +
		"\n		a.pk_seq as khId, " +
		"\n  	a.smartid, a.ten as khTen, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, " +
		"\n  	c.ten as nguoisua, d.chietkhau, d.pk_seq as mckId,isnull(a.ANHCUAHANG,'') as ANHCUAHANG, " +
		"\n  	e.diengiai as kbhTen, e.pk_seq as kbhId, f.hang as hchTen, f.pk_seq as hchId, " +
		"\n  	g.DIENGIAI as lchTen, g.pk_seq as lchId, h.ten as nppTen, h.pk_seq as nppId, h.manpp manpp, " +
		"\n  	k.sotienno as ghcnTen, k.pk_seq as ghcnId, l.ten as bgstTen, l.pk_seq as bgstId,  " +
		"\n  	m.vitri as vtchTen, m.pk_seq as vtchId, a.dienthoai, a.diachi, " +
		"\n  	--isnull((select top(1) ten from DAIDIENKINHDOANH where pk_seq = a.DDKD_FK),'') as ddkdtao, " +
		"\n  	--isnull((select top(1) ten from DAIDIENKINHDOANH where pk_seq = a.DDKDTAO_FK),'') as ddkdsua, " +
		"\n  	isnull(isPDASua,'') as  isPDASua, isnull(ddkdTao.Ten,'')ddkdTaoTen  " +
		"\n  from khachhang a inner join nhanvien b on a.nguoitao = b.pk_seq" +
		"\n	 left join daidienkinhdoanh ddkdTao on ddkdTao.pk_seq = a.DDKDTAO_FK   " +
		"\n  inner join nhanvien c on a.nguoisua = c.pk_seq  " +
		"\n  left join mucchietkhau d on a.chietkhau_fk = d.pk_seq  " +
		"\n  left join kenhbanhang e on a.kbh_fk = e.pk_seq  " +
		"\n  left join hangcuahang f on a.hch_fk = f.pk_seq  " +
		"\n  left join loaicuahang g on a.lch_fk = g.pk_seq " +
		"\n  inner join nhaphanphoi h on a.npp_fk = h.pk_seq  " +
		"\n  left join gioihancongno k on a.ghcn_fk = k.pk_seq  " +
		"\n  left join banggiasieuthi l on a.bgst_fk = l.pk_seq  " +
		"\n  left join vitricuahang m on a.vtch_fk = m.pk_seq  " +
		"\n  left join DMS_Route route on route.pk_seq = a.route_fk " +
		"\n  outer apply " +
		"\n  ( " +
		"\n		SELECT KHACHHANG_FK,BB.DDKD_FK, CC.GSBH_FK " +
		"\n		FROM KHACHHANG_TUYENBH AA " +
		"\n		INNER JOIN TUYENBANHANG BB ON BB.PK_SEQ = AA.TBH_FK " +
		"\n		INNER JOIN DDKD_GSBH CC ON CC.DDKD_FK = BB.DDKD_FK " +
		"\n		where KHACHHANG_FK = a.pk_seq " +
		"\n		GROUP BY KHACHHANG_FK, BB.DDKD_FK, CC.GSBH_FK " +
		"\n  ) DDKD_GSBH " +
		"\n  inner join daidienkinhdoanh ddkd on ddkd.pk_seq = DDKD_GSBH.ddkd_fk " +
		"\n  inner join giamsatbanhang gs on gs.pk_seq = DDKD_GSBH.GSBH_FK " +
		"\n  left join quanhuyen qh on qh.pk_seq = a.quanhuyen_fk " +
		"\n  left join tinhthanh tt on tt.pk_seq = a.tinhthanh_fk " +
		"\n  left join khuvuc kv on kv.pk_seq = h.khuvuc_fk " +
		"\n  left join vung v on v.pk_seq = kv.vung_fk " +
		"\n  outer apply " + 
		"\n  ( " +
		"\n		select ngayid, tanso " +
		"\n		from  " +
		"\n		( " +
		"\n			select  " +
		"\n			STUFF((SELECT ';' + convert(varchar,tanso) FROM KHACHHANG_TUYENBH where khachhang_fk = a.pk_seq FOR XML PATH ('')), 1, 1, '' ) tanso, " +
		"\n			STUFF((SELECT ';' + convert(varchar,ngayid) FROM tuyenbanhang aaa inner join khachhang_tuyenbh bbb on aaa.pk_seq = bbb.tbh_fk " +
		"\n			where bbb.khachhang_fk = a.pk_seq FOR XML PATH ('')), 1, 1, '' ) ngayid " +
		"\n		) tbh " +
		"\n 	GROUP BY tbh.ngayid, tbh.tanso " +
		"\n	 )tbh " +
		"\n  where 1=1 ";
		query += "\n and a.pk_seq in (select kh.pk_seq from KHACHHANG kh"+
		"\n		outer apply ("+
		"\n			select sum( thucdat)thucdat"+
		"\n			from ("+
		"\n				SELECT sum(DH_SP.soluong * DH_SP.giamua) thucdat "+
		"\n				FROM Donhang DH  INNER JOIN DONHANG_SANPHAM  DH_SP ON DH.PK_SEQ = DH_SP.donhang_fk         "+
		"\n				where DH.trangthai = 1  and DH.ngaynhap>=@DauThang and DH.ngaynhap <=@CuoiThang   "+
		"\n					and not exists ( select 1 from donhangtrave where trangthai = 3 and donhang_fk = dh.pk_seq)   "+
		"\n					and ddkd.pk_seq = DH.DDKD_FK  and dh.KHACHHANG_FK = kh.PK_SEQ	 "+
		"\n					--and DH_SP.SANPHAM_FK in( select sp_fk from NHOMSANPHAMChiTieu_SANPHAM where NSP_FK =@nsp_fk )   "+
		"\n				union all"+
		"\n				select isnull(sum((-1)*dhsp.soluong*dhsp.giamua),0) thucdat"+
		"\n				from DONHANGTRAVE dh"+
		"\n				inner join DONHANGTRAVE_SANPHAM dhsp on dhsp.DONHANGTRAVE_FK = dh.PK_SEQ"+
		"\n				where dh.donhang_fk is null and  dh.TRANGTHAI = 3 and	  DH.ngaynhap>=@DauThang and DH.ngaynhap <=@CuoiThang   "+
		"\n					and ddkd.pk_seq = DH.DDKD_FK 	  and dh.KHACHHANG_FK = kh.PK_SEQ	"+
		"\n					--and dhsp.SANPHAM_FK in( select sp_fk from NHOMSANPHAMChiTieu_SANPHAM where NSP_FK =@nsp_fk )   "+
		"\n			)dh"+
		"\n		)ds"+
		"\n		where isnull(ds.thucdat,0) >=  isnull(0,0)    and kh.DDKDTAO_FK = ddkd.pk_seq "+
		"\n		and  kh.NGAYTAO>=@DauThang and kh.NGAYTAO <=@CuoiThang and kh.TRANGTHAI = 1 and isnull(kh.daduyet,0) = '1') ";
		if(obj.getvungId().length()  > 0) {
			query += "\n and v.pk_seq = '"+obj.getvungId()+"' ";
		}
		if(obj.getkhuvucId().length()  > 0) {
			query += "\n and kv.pk_seq = '"+obj.getkhuvucId()+"' ";
		}
		if (obj.getnppId() != null && obj.getnppId().length() > 0)
			query += "\n and ddkd.npp_fk in ( "+obj.getnppId()+" )  ";

//		query +=  "\n and ddkd.pk_seq in ("+quyen+") ";
//		System.out.println("obj.getView():" + obj.getView() + "" );
//		if (obj.getView() != null && !obj.getView().equals("TT")) {
//			if (nppId != null && nppId.length() > 0) {
//				query += "\n and h.pk_seq = '"+nppId+"' ";
//			}
//		}
//		if (obj.getView() != null && obj.getView().equals("TT")) {
//			query += "\n and a.kbh_fk in ("+Utility.PhanQuyenKBH(obj.getUserId())+") ";
//			query += "\n and a.npp_fk in ("+Utility.PhanQuyenNPP(obj.getUserId())+") ";
//		}
		System.out.println("query excel: " + query);
		return query;
	}
	private void CreateStaticHeader(Workbook workbook, String UserName) {
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		Cells cells = worksheet.getCells();
		Style style;

		Cell cell = cells.getCell("A1");
		cells.merge(0, 0, 0, 11);
		cell.setValue("DANH SÁCH KHÁCH HÀNG MỚI");
		style = cell.getStyle();
		Font font2 = new Font();
		font2.setName("Calibri");
		font2.setColor(Color.NAVY);
		font2.setSize(18);
		font2.setBold(true);
		style.setFont(font2);
		style.setHAlignment(TextAlignmentType.CENTER);
		cell.setStyle(style);

		font2 = new Font();
		font2.setName("Calibri");
		font2.setBold(true);
		font2.setSize(11);

		cell = cells.getCell("A3");
		cell.setValue("Ngày tạo: " + this.getDateTime());
		style = cell.getStyle();
		style.setFont(font2);
		cell.setStyle(style);

		// tieu de
		int cot = 0;
		cell = cells.getCell(4, cot++);
		
		cell.setValue("Vùng");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Khu Vực");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Tỉnh Thành");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Quận Huyện");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Mã NPP");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Tên NPP");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Tên KH");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Mã KH");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);		
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Mã KH tham thiếu");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Địa chỉ KH");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Route");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Tuyến thứ");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Tần suất");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Tên NVBH");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Mã NVBH");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Mã SS");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Tên SS");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Trạng thái duyệt");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Ngày tạo");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		cell = cells.getCell(4, cot++);
		cell.setValue("NVBH tạo");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Người tạo");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Ngày sửa");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Người sửa");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Ảnh đại diện");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Loại khách hàng");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Toạ độ");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		cell = cells.getCell(4, cot++);
		cell.setValue("Trạng thái");
		style.setFont(font2);
		cell.setStyle(style);
		setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		worksheet.setName("DSKH");
	}
	private void setCellBorderStyle(Cell cell, short align) {
		Style style = cell.getStyle();
		style.setHAlignment(align);
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);
		cell.setStyle(style);
	}
	private void CreateStaticData(Workbook workbook, String query) {
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();

		dbutils db = new dbutils();
		ResultSet rs = db.get(query);
		//System.out.println("Query Excel: " + query);
		NumberFormat formatter = new DecimalFormat("#,###,###");
		int i = 5;
		if (rs != null) {
			try {
				cells.setColumnWidth(0, 10f);
				cells.setColumnWidth(1, 15.0f);
				cells.setColumnWidth(2, 10.0f);
				cells.setColumnWidth(3, 10.0f);
				cells.setColumnWidth(4, 10.0f);
				cells.setColumnWidth(5, 20.0f);
				cells.setColumnWidth(6, 25.0f);
				cells.setColumnWidth(7, 10.0f);
				cells.setColumnWidth(8, 10.0f);
				cells.setColumnWidth(8, 25.0f);
				cells.setColumnWidth(10, 10.0f);
				cells.setColumnWidth(11, 5.0f);	
				cells.setColumnWidth(12, 5.0f);	

				for (int j = 0; j < 11; j++) {
					if (j == 0)
						cells.setRowHeight(j, 23.0f);
					else
						cells.setRowHeight(j, 13.0f);
				}

				Cell cell = null;

				Style style;
				Font font2 = new Font();
				font2.setName("Calibri");
				font2.setSize(11);

				int count = 1;
				while (rs.next()) {
					int cot = 0;
					String url = "http://1.53.252.173:9999/AnhChupPDA/AnhDaiDien/";
					
					cell = cells.getCell(i, cot++);
					cell.setValue(rs.getString("vten"));
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);					
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell(i, cot++);
					cell.setValue(rs.getString("kvten"));
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);					
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell(i, cot++);
					cell.setValue(rs.getString("ttten"));
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);					
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell(i, cot++);
					cell.setValue(rs.getString("qhten"));
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell(i, cot++);
					cell.setValue(rs.getString("manpp"));
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell(i, cot++);
					cell.setValue(rs.getString("nppten"));
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell(i, cot++);
					cell.setValue(rs.getString("khten"));
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell(i, cot++);
					cell.setValue(rs.getString("smartid"));
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
										
					cell = cells.getCell(i, cot++);
					cell.setValue(rs.getString("mathamchieu"));
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell(i, cot++);
					cell.setValue(rs.getString("diachi"));
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell(i, cot++);
					cell.setValue(rs.getString("routename"));
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell(i, cot++);
					cell.setValue(rs.getString("ngayid"));
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell(i, cot++);
					cell.setValue(rs.getString("tanso"));
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell(i, cot++);
					cell.setValue(rs.getString("ddkdten"));
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell(i, cot++);
					cell.setValue(rs.getString("ddkdma"));
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell(i, cot++);
					cell.setValue(rs.getString("gsbhma"));
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell(i, cot++);
					cell.setValue(rs.getString("gsbhten"));
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);
					String trangthaiduyet = (rs.getString("daduyet") != null && rs.getString("daduyet").equals("1"))?"Duyệt":"Chưa";
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell(i, cot++);
					cell.setValue(trangthaiduyet);
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);					
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell(i, cot++);
					cell.setValue(rs.getString("ngaytao"));
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);					
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell(i, cot++);
					cell.setValue(rs.getString("ddkdTaoTen"));
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);					
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					
					cell = cells.getCell(i, cot++);
					cell.setValue(rs.getString("nguoitao"));
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);					
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell(i, cot++);
					cell.setValue(rs.getString("ngaysua"));
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);					
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell(i, cot++);
					cell.setValue(rs.getString("nguoisua"));
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);					
					String anhcuahang = (rs.getString("anhcuahang")!=null&&rs.getString("anhcuahang").length()>0)?rs.getString("anhcuahang"):"";
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell(i, cot++);
					cell.setValue(anhcuahang!=null&&anhcuahang.length()>0?url+anhcuahang:"");
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);					
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell(i, cot++);
					cell.setValue(rs.getString("lchten"));
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell(i, cot++);
					cell.setValue(rs.getString("toado"));
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);					
					
					String trangthai = (rs.getString("khtrangthai") != null && rs.getString("khtrangthai").equals("1"))?"Hoạt động":"Không hoạt động";
					cell = cells.getCell(i, cot++);
					cell.setValue(trangthai);
					style = cell.getStyle();
					style.setFont(font2);
					cell.setStyle(style);
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
									
					i++;
					count++;
				}
				rs.close();
				db.shutDown();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
