package geso.dms.distributor.servlets.reports;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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


public class Srperfomance extends HttpServlet {
	private static final long serialVersionUID = 1L; 
    public Srperfomance() {
        super();
    }
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
		String nextJSP = "/AHF/pages/Distributor/SRperformace.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		OutputStream out = response.getOutputStream();
		Stockintransit obj = new Stockintransit();
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		obj.setuserId(userId);
		obj.setuserTen(userTen);
		Utility Ult = new Utility();
		String year = Ult.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("year")));
		if (year == null)
			year = "";
		obj.setYear(year);

		String month = Ult.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("month")));
		if (month == null)
			month = "";
		obj.setMonth(month);
		String kenhId = Ult.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")));
		if (kenhId == null)
			kenhId = "";
		obj.setkenhId(kenhId);

		String dvkdId = Ult.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")));
		if (dvkdId == null)
			dvkdId = "";
		obj.setdvkdId(dvkdId);

		String dvdlId = Ult.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvdlId")));
		if (dvdlId == null)
			dvdlId = "";
		obj.setdvdlId(dvdlId);
		String	nppId = Ult.getIdNhapp(userId);
		String nppTen = Ult.getTenNhaPP();
		obj.setnppId(nppId);
		obj.setnppTen(nppTen);
		
		
		String ddkdId = Ult.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdId")));
		if (ddkdId == null)
			ddkdId = "";
		obj.setDdkd(ddkdId);
		obj.setdvkdId(dvkdId);
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		
		String nextJSP = "/AHF/pages/Distributor/SRperformace.jsp";
		
	

		 
 
		try{
			if (action.equals("tao")) {
				response.setContentType("application/xlsm");
		        response.setHeader("Content-Disposition", 
		        		"attachment; filename=ThucHienChiTieuNPP.xlsm");
		 
		     /*   String query = setQuery(obj);
		        CreatePivotTable(out,obj,query);*/
		      
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=ThucHienChiTieu.xlsm");
				
			
					ExportToExcel(out,obj);
			
				
			}			
		}catch(Exception ex){
			obj.setMsg(ex.getMessage());
		}
		obj.init();	    
		session.setAttribute("obj", obj);
		session.setAttribute("userId", obj.getuserId());		
		response.sendRedirect(nextJSP);
	}
	
	private void ExportToExcel(OutputStream out,IStockintransit obj )throws Exception
	{
		try
		{ 			
			com.aspose.cells.Workbook workbook = new com.aspose.cells.Workbook();
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			
			TaoBaoCao(workbook, obj, 0);
			workbook.save(out);			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}
	}
	private void ExportToExcel2(OutputStream out,IStockintransit obj )throws Exception
	{
		try
		{ 
			//FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BCThucDatChiTieu2.xls");
			File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\BCThucDatChiTieu2.xlsm");
			FileInputStream fstream = new FileInputStream(f) ;
			
			com.aspose.cells.Workbook workbook = new com.aspose.cells.Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			
			TaoBaoCao2(workbook, obj, 0);
			workbook.save(out);			
			fstream.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}
	}
	private void TaoBaoCao(com.aspose.cells.Workbook workbook, IStockintransit obj, int sheetNum )throws Exception
	{
		int countRow = 10;
		int column = 0;
		//Chỉnh sửa theo Template mới
		try{
			
			String query =  this.query(obj);
			
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
			int j = 5;
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
	public String query (IStockintransit obj) throws SQLException {

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
				"\n		,ddkd.smartId as [MÃ NV], ddkd.Ten as [TDV] " +
				"\n 	,npp.ten [Nhà Phân Phối] " +
				
				"\n		,ct.ChiTieu as [Chỉ Tiêu], isnull(td.thucdat,0) [Thực đạt] , dbo.PhepChia(isnull(td.thucdat,0),cast(ct.ChiTieu as float) ) [Tỷ lệ đạt]" +
				"\n		, case when ISNULL(tct.LOAIDS,0) = 1 then  isnull(td.DoanhSo,0) * muc.thuong / 100.0 else   muc.thuong end [Thưởng]  " +
				"\n from ChiTieuNhanVien_DDKD ct " +
				"\n inner join DaiDienKinhDoanh ddkd on ct.NhanVien_FK = ddkd.pk_seq " +
				"\n inner join nhaphanphoi npp on npp.pk_seq = ddkd.npp_fk" +
				"\n inner join tieuchithuong_chitiet tct on ct.tctct_fk = tct.pk_seq  " +
				"\n outer apply  [dbo].[ufn_KPI_DDKD]('"+obj.getYear() +"-"+ obj.getMonth() +"-01"+"',ct.tctct_fk,ct.NhanVien_FK) td   " +
				
				"\n outer apply " +
				"\n  ( " +
				"\n 	select max(thuong)thuong  " +
				"\n 	from TieuChiThuong_ChiTiet_MucThuong x " +
				"\n 	where x.TCTCT_FK = tct.PK_SEQ and  x.TU <=100* dbo.PhepChia(isnull(td.thucdat,0),cast(ct.ChiTieu as float) )  and x.DEN >=100* dbo.PhepChia(isnull(td.thucdat,0),cast(ct.ChiTieu as float) ) " + 
				"\n  )muc  " +
				
				"\n where ct.chitieu > 0  and ct.tctct_fk  in ("+tct_fks+") ";
		System.out.print("obj.getDdkd()"+obj.getDdkd());

		if(obj.getDdkd().length()  > 0)
		{
			query += "\n and ct.NhanVien_FK = '"+obj.getDdkd()+"' ";
		}
		if(obj.getkenhId().length()  > 0)
		{
			query += "\n and ddkd.kbh_Fk = '"+obj.getkenhId()+"' ";
		}

		if (obj.getnppId() != null && obj.getnppId().length() > 0)
			query += "\n and ddkd.npp_fk in ( "+obj.getnppId()+" )  ";

		/*query +=  "\n and ct.NhanVien_FK in ("+quyen+") ";
*/
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
		"\n select   Row_number() OVER ( order by ( [Mã NV] ) asc)[STT],[Nhà Phân Phối],[Mã NV],[TDV], " +
		"\n "+text+", " +
		"\n pk_seq " +
		"\n from  " +
		"\n (" +
		"\n 	select [Nhà Phân Phối],[Mã NV],[TDV],[Tên Chỉ Tiêu]+'_'+col col,value,pk_seq from " +
		"\n 	( " +
		"\n 		select [Mã NV],[TDV],[Nhà Phân Phối],[Tên Chỉ Tiêu],[Chỉ Tiêu],[Thực Đạt],[Tỷ Lệ Đạt],[Thưởng],pk_seq from temp " +
		"\n		) rt  " +
		"\n 	unpivot (value for col in ([Chỉ Tiêu],[Thực Đạt],[Tỷ Lệ Đạt],[Thưởng]) ) unpiv  " +
		"\n ) tp " +
		"\n pivot ( SUM(value) FOR col in ("+text+")) piv ";

		String abc = header_query + query + footer_query;
		System.out.println("Query báo cáo Template mới: "+abc);

		return abc;

	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	private void TaoBaoCao2(com.aspose.cells.Workbook workbook, IStockintransit obj, int sheetNum )throws Exception
	{
		Utility util = new Utility();
		try
		{
			com.aspose.cells.Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(sheetNum);
			com.aspose.cells.Cells cells = worksheet.getCells();

			String nhanvien = "SR";
			
			dbutils db = new dbutils();
			
			cells.merge(0, 0, 0, 3 );		
			com.aspose.cells.Cell cell = cells.getCell("A1");
			//ReportAPI.getCellStyle(cell, Color.RED, true, 16,"THỰC ĐẠT CHỈ TIÊU "+ nhanvien);
			
			
			
			/*cells.merge(1, 0, 1, 3 );
			cell = cells.getCell("A2");*/
			cell.setValue("Ngày tạo: " + getDateTime());
			//ReportAPI.setBorder_Style_MergerCell(cells, 1, 1, 0, 3, BorderLineType.NONE, Color.BLUE, cell.getStyle());
			
			cell = cells.getCell("A2");
			cell.setValue("Người tạo: " + obj.getuserTen());
		//	ReportAPI.setBorder_Style_MergerCell(cells, 1, 1, 0, 3, BorderLineType.NONE, Color.BLUE, cell.getStyle());
			
			String dauthang = obj.getYear() + "-" + (obj.getMonth().trim().length() > 1 ? obj.getMonth() : "0" + obj.getMonth()) + "-01";
			
			String query = "";
			String queryCHITIEU = "";
			String queryTHUCDAT = "";
			
			//B1 Lấy tiêu chí
			query =  " select b.PK_SEQ, b.DIENGIAI, b.NHOMSP_FK, b.TIEUCHI, b.noidung, b.loaiDS   "+
					 " from TIEUCHITINHTHUONG a inner join TIEUCHITHUONG_CHITIET b on a.PK_SEQ = b.TIEUCHITINHTHUONG_FK"+
					 " where a.THANG = " + obj.getMonth() + " and a.NAM = '" + obj.getYear() + "' and a.TRANGTHAI = '1'  and a.isdacbiet = '1' "+
					 " order by tieuchi asc";
			System.out.println("::: LAY TIEU CHI THUONG: " + query);
			ResultSet rs = db.get(query);
			
			String sqlBaocao = "";
			String sqlTieuchi = "";
			String sqlThucdat01 = "";
			String sqlThucdat02 = "";
			String thucdat = "";
			while( rs.next() )
			{
				sqlTieuchi += "[" + rs.getString("DIENGIAI") + "], ";
				sqlThucdat01 += " ISNULL( [" + rs.getString("PK_SEQ") + "].thucdat, 0 ) as [TD_" + rs.getString("DIENGIAI") + "], ";
				sqlThucdat02 += " left join dbo.ufn_KPI_SR('" + dauthang + "', " + rs.getString("PK_SEQ") + ", " + rs.getString("TIEUCHI") + ", null) [" + rs.getString("PK_SEQ") + "] on a.PK_SEQ = [" + rs.getString("PK_SEQ") + "].NhanVien_FK ";
			
				sqlBaocao += ", [" + rs.getString("DIENGIAI") + "]";
				thucdat +=  ",[TD_" + rs.getString("DIENGIAI") + "]";
			}
			rs.close();
			
			System.out.println("::: SQL BAO CAO: " + sqlBaocao);
			if( sqlThucdat01.trim().length() > 0 )
			{
				sqlThucdat01 = sqlThucdat01.substring(0, sqlThucdat01.length() - 2);
				queryTHUCDAT = "select a.PK_SEQ as manhanvien, " + sqlThucdat01 + " from DAIDIENKINHDOANH a " + sqlThucdat02;
				
				sqlTieuchi = sqlTieuchi.substring(0, sqlTieuchi.length() - 2);
				queryCHITIEU =   "\n select  manpp, tennpp,manhanvien, tennhanvien, " + sqlTieuchi + 
								 "\nfrom " + 
								 "\n( " + 
								 "\n	select npp.manpp,npp.ten as tennpp, c.pk_seq as manhanvien, c.TEN as tennhanvien, d.DIENGIAI as tieuchi, b.chitieu " + 
								 "\n	from CHITIEUNHANVIEN a inner join ChiTieuNhanVien_DDKD b on a.pk_seq = b.CTNV_FK " + 
								 "\n		inner join DAIDIENKINHDOANH c on b.NhanVien_FK = c.PK_SEQ " + 
								 
								 "\n		inner join nhaphanphoi npp on npp.pk_seq = c.npp_fk " + 
								 "\n		inner join TIEUCHITHUONG_CHITIET d on b.TCTCT_FK = d.PK_SEQ  " + 
								 "\n	where a.TRANGTHAI = 1 and a.THANG = '" + obj.getMonth() + "' and a.NAM = '" + obj.getYear() + "' " +
								 "\n and c.npp_fk = '"+obj.getnppId()+"'"+
								 "\n) " + 
								 "\nDT PIVOT ( max(chitieu) FOR tieuchi IN ( " + sqlTieuchi + " ) ) AS pvt ";
			}
			
			System.out.println("::: LẤY CHỈ TIÊU: " + queryCHITIEU);
			System.out.println("::: LẤY THỰC ĐẠT: " + queryTHUCDAT);
			
			query = "select '' as stt, CT.manpp,CT.tennpp, (select top 1 smartid from daidienkinhdoanh where pk_seq =  CT.manhanvien) as manhanvien, CT.tennhanvien " + sqlBaocao +thucdat+ ",'' as tlsl,'' as tlds,'' tongtl "+
					"from ( " + queryCHITIEU + " ) CT left join ( " + queryTHUCDAT + " ) TD on CT.manhanvien = TD.manhanvien ";
			
			//Vẽ tiêu đề
			
			
			String[] tieude = sqlTieuchi.split(",");
			
			System.out.println("::: LẤY BÁO CÁO: " + query);
			ResultSet ctRs = db.get(query);
			ResultSetMetaData rsmd = ctRs.getMetaData();
			int socottrongSql = rsmd.getColumnCount();
			int countRow = 18;
			int stt = 0;
			while(ctRs.next())
			{
				for(int i = 1; i <= socottrongSql; i++)
				{
					Color c = Color.WHITE;
					cell = cells.getCell(countRow, i - 1 );
					
					if(rsmd.getColumnType(i) == Types.DOUBLE || rsmd.getColumnType(i) == Types.INTEGER || rsmd.getColumnType(i) == Types.DECIMAL )
					{
						cell.setValue(ctRs.getDouble(i));
						if(rsmd.getColumnName(i).equals("tile"))
						{
							ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, false, 10);
						}
						else
							ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, false, 41);
						
					}
					else
					{
						cell.setValue(ctRs.getString(i));
						ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, false, 0);
						if(rsmd.getColumnName(i).equals("tlsl"))
						{
							cell.setFormula("=IFERROR(H"+(countRow+1)+"/F"+(countRow+1)+",0)");
							ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, false, 10);
						}else if(rsmd.getColumnName(i).equals("tlds"))
						{
							cell.setFormula("=IFERROR(I"+(countRow+1)+"/G"+(countRow+1)+",0)");
							ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, false, 10);
						}else if(rsmd.getColumnName(i).equals("tongtl"))
						{
							cell.setFormula("=IFERROR((J"+(countRow+1)+"+K"+(countRow+1)+")/2,0)");
							ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, false, 10);
						}
						if(rsmd.getColumnName(i).equals("stt"))
						{
								 stt++;
								 cell.setValue(stt); 
						}
									
					}
				}
				
				countRow ++;
			}
			
			if(rs != null) rs.close();
			if(db != null){
				db.shutDown();
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new Exception("Lỗi ! không thể tạo báo cáo !");
		}
	}
	private String setQuery( IStockintransit obj) {
		
		String fromYear = obj.getYear();
		String fromMonth = obj.getMonth();
		
		String fromDate=fromYear+'-'+fromMonth;
		String query="";
		//long restWD = 0;
		long numofDay = 0;
		geso.dms.center.db.sql.dbutils db=new geso.dms.center.db.sql.dbutils();
		 String sql="select distinct nhomsanpham_fk,dbo.ftBoDau(nsp.ten) as ten  from  chitieunpp_ddkd_nhomsp "+  
					" inner join chitieunpp b on b.pk_Seq=chitieunpp_fk  "+
					" inner join nhomsanpham nsp on nsp.pk_seq=nhomsanpham_fk "+
					" where b.thang="+obj.getMonth()+" and b.nam="+ obj.getYear() ;
		 
		 if(obj.getdvkdId().length()>0){
			 sql=sql+ " and b.dvkd_fk= "+ obj.getdvkdId();
			 
		 }
		 if(obj.getkenhId().length()>0){
			 sql=sql+ " and b.kenh_fk= "+ obj.getkenhId();
			 
		 }
		 
		 ResultSet rs=db.get(sql);
		 String chuoi="";
		 String[] arraychuoi= new String[10];
		 String chuoiselct="";
		 String chuoingoac="";//co dau []
		 if(rs!=null){
			 int i=0;
			 try {
				while (rs.next()){
					
					 if(i==0){
						 chuoingoac="["+rs.getString("nhomsanpham_fk")+"]";
						 chuoi=rs.getString("nhomsanpham_fk");
						 chuoiselct="isnull(CTNHOM.["+rs.getString("nhomsanpham_fk")+"],0) AS CT"+rs.getString("nhomsanpham_fk")+",ISNULL(DS.["+rs.getString("nhomsanpham_fk")+"],0) AS DS"+rs.getString("nhomsanpham_fk");
					 }else{
						 chuoi=chuoi+","+rs.getString("nhomsanpham_fk");
						 chuoiselct=chuoiselct+", isnull(CTNHOM.["+rs.getString("nhomsanpham_fk")+"],0) AS CT"+rs.getString("nhomsanpham_fk")+",ISNULL(DS.["+rs.getString("nhomsanpham_fk")+"],0) AS DS"+rs.getString("nhomsanpham_fk");
						 chuoingoac=chuoingoac+",["+rs.getString("nhomsanpham_fk")+"]";
					 }
					 arraychuoi[i]=rs.getString("nhomsanpham_fk");
					 i++;
					 
				 }
			} catch (Exception e) {
				e.printStackTrace();
			}
		 }
		 //dung file show de luu chuoi;
		 
		 //kiem tra truoc trong thang lay theo san luong hoac tien
		 
		 sql="select loaichitieu from chitieu_sec where thang="+obj.getMonth()+" and nam="+obj.getYear() +" and trangthai<>'2'";
		//loai chi tieu sanluong la 2
		 String loaichitieu=" TRONGLUONG";
		 System.out.println("Loai Chi Tieu ; "+sql);
		 boolean checkloaichitieu=false;
		 ResultSet rscheck=db.get(sql);
		try{
			 if(rscheck.next()){
				if(rscheck.getString("loaichitieu").equals("1")){
					loaichitieu="DH_SP.GIAMUA ";
					checkloaichitieu=true;
				}
				rscheck.close();
			 }
		}catch(Exception er){
			
		}
		 
		 
		obj.setFieldShow(arraychuoi);
		 
		sql= 
		" SELECT KBH.TEN AS KENH,DVKD.DONVIKINHDOANH ,VUNG.TEN AS VUNG,KV.TEN AS KHUVUC,NPP.TEN AS NHAPHANPHOI,DATEDIFF(day,KHOASO.NGAYKHOASO , DATEADD(s,-1,DATEADD(mm, DATEDIFF(m,0,dbo.GetLocalDate(DEFAULT))+1,0))) AS NGAYCONLAI ,DATEADD(s,-1,DATEADD(mm, DATEDIFF(m,0,dbo.GetLocalDate(DEFAULT))+1,0)) AS NGAYCUOITHANG , "+ 
		"  CTNHOM.NPP_FK,CTNHOM.DDKD_FK,CTNHOM.KENH_FK,CTNHOM.DVKD_FK, DDKD.TEN AS DAIDIENKINHDOANH,SONGAYLAMVIEC,SONGAYTT,SODONHANG ,ISNULL(SODH.DONHANG,0) AS DATDONHANG ,CTNHOM.ctSanLuongDonHang,SanLuongDonHang.SoDonHangDatCtSanLuong"+
		" , "+ chuoiselct +
		" FROM "+
		" ("+
		" SELECT CTNPP.NHAPP_FK AS NPP_FK,CTNPP.PK_SEQ,CTNPP.KENH_FK,CTNPP.DVKD_FK, B.DDKD_FK  " +
		" ,CTNPP.SONGAYLAMVIEC,A.SODONHANG,B.CHITIEU,B.NHOMSANPHAM_FK,isnull(a.sanluongtrendh,0) as ctSanLuongDonHang"+
		" FROM CHITIEUNPP_DDKD_NHOMSP B INNER JOIN CHITIEUNPP_DDKD A ON "+
		" A.CHITIEUNPP_FK=B.CHITIEUNPP_FK AND A.DDKD_FK=B.DDKD_FK "+
		" INNER JOIN CHITIEUNPP CTNPP ON CTNPP.PK_SEQ=B.CHITIEUNPP_FK   " +
		" WHERE CTNPP.THANG="+obj.getMonth()+" AND CTNPP.NAM="+obj.getYear()+" and trangthai<>'2' ) P"+
		" PIVOT "+
		" ( "+
		" SUM(CHITIEU) "+
		" FOR NHOMSANPHAM_FK IN "+
		" ( "+chuoingoac+" ) "+
		" ) AS CTNHOM "+
		" INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ=CTNHOM.NPP_FK "+
		" INNER JOIN KHUVUC KV ON KV.PK_SEQ=NPP.KHUVUC_FK "+
		" INNER JOIN VUNG  ON VUNG.PK_SEQ=KV.VUNG_FK "+
		" INNER JOIN KENHBANHANG KBH ON KBH.PK_SEQ=CTNHOM.KENH_FK "+
		" INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ=CTNHOM.DVKD_FK "+
		" INNER JOIN DAIDIENKINHDOANH DDKD ON DDKD.PK_SEQ=CTNHOM.DDKD_FK " +
		" INNER JOIN  (SELECT NPP_FK,MAX(NGAYKS)  AS NGAYKHOASO  FROM KHOASONGAY  GROUP BY NPP_FK ) AS KHOASO  ON KHOASO.NPP_FK=NPP.PK_SEQ  "+
		" LEFT JOIN  "+
		" ( "+
		" SELECT SP.DVKD_FK,DH.KBH_FK,DDKD_FK, COUNT(DISTINCT DH.PK_SEQ) AS DONHANG,COUNT (DISTINCT DH.NGAYNHAP) AS SONGAYTT "+
		" FROM DONHANG DH INNER JOIN  "+
		" DONHANG_SANPHAM DH_SP ON DH.PK_SEQ=DH_SP.DONHANG_FK "+
		" INNER JOIN SANPHAM SP ON SP.PK_SEQ=DH_SP.SANPHAM_FK "+
		" WHERE DH.TRANGTHAI='1' AND  DH.NGAYNHAP LIKE '"+obj.getYear()+"-"+obj.getMonth()+"%' AND " +
		 " DH.PK_SEQ NOT IN (SELECT DONHANG_FK FROM DONHANGTRAVE WHERE TRANGTHAI=3 AND DONHANG_FK IS NOT NULL) "+
		" GROUP BY SP.DVKD_FK,DH.KBH_FK,DDKD_FK "+
		" ) SODH ON "+
		" CTNHOM.KENH_FK=SODH.KBH_FK AND CTNHOM.DVKD_FK=SODH.DVKD_FK AND CTNHOM.DDKD_FK=SODH.DDKD_FK "+ 
		" LEFT JOIN  "+
		" ( "+
		" SELECT DVKD_FK, DDKD_FK,KBH_FK , "+chuoingoac +
		" FROM (  "+
		
		" SELECT SP.DVKD_FK,DH.KBH_FK,DDKD_FK,NSP.NHOMSP_FK, SUM( SOLUONG* "+loaichitieu+") AS SANLUONG "+
		" FROM DONHANG DH INNER JOIN  "+
		" DONHANG_SANPHAM DH_SP ON DH.PK_SEQ=DH_SP.DONHANG_FK "+
		" INNER JOIN SANPHAM SP ON SP.PK_SEQ=DH_SP.SANPHAM_FK "+
		" INNER JOIN ( "+
		" SELECT MAX(NSP_FK) AS NHOMSP_FK,SP_FK AS SANPHAM_FK FROM NHOMSANPHAM_SANPHAM "+
		" WHERE NSP_FK IN ("+chuoi+") "+
		" GROUP BY SP_FK "+
		" ) AS NSP ON NSP.SANPHAM_FK=SP.PK_SEQ "+
		" WHERE  DH.TRANGTHAI='1' AND DH.NGAYNHAP LIKE '"+obj.getYear()+"-"+obj.getMonth()+"%' "+
		" GROUP BY SP.DVKD_FK,DH.KBH_FK,DDKD_FK,NSP.NHOMSP_FK  "+
		//THEMPHAN TRA HANG
		"  UNION ALL "+
		"  SELECT SP2.DVKD_FK AS DVKD_FK, DH.KBH_FK,  DH.DDKD_FK AS DDKD_FK , NSP.NHOMSP_FK ,  " ;
		if(checkloaichitieu){
			sql= sql+	"(-1)*	 SUM(	ISNULL(DH_SP.GIAMUA, DH_SP1.GIAMUA) *   ISNULL(DH_SP.SOLUONG, DH_SP1.SOLUONG) ) AS DOANHSO  " ;
		}else{
			sql= sql+	"(-1)*	SUM(TRONGLUONG  *   ISNULL(DH_SP.SOLUONG, DH_SP1.SOLUONG) ) AS DOANHSO  ";
		}
		
		sql= sql+		"  FROM  DONHANGTRAVE DH    "+
		" LEFT OUTER JOIN  DONHANGTRAVE_SANPHAM DH_SP ON DH_SP.DONHANGTRAVE_FK = DH.PK_SEQ "+    	
		" LEFT OUTER JOIN  DONHANG_SANPHAM DH_SP1 ON DH.DONHANG_FK = DH_SP1.DONHANG_FK "+
		" INNER JOIN SANPHAM SP2 ON SP2.PK_SEQ = ISNULL(DH_SP.SANPHAM_FK, DH_SP1.SANPHAM_FK) "+
		" INNER JOIN (   "+
		" SELECT MAX(NSP_FK) AS NHOMSP_FK,SP_FK AS SANPHAM_FK FROM NHOMSANPHAM_SANPHAM "+
		" WHERE NSP_FK IN ("+chuoi+") "+
		" GROUP BY SP_FK  "+
		" ) AS NSP ON NSP.SANPHAM_FK=SP2.PK_SEQ  "+
		" WHERE  DH.TRANGTHAI='3' AND DH.NGAYNHAP LIKE '"+obj.getYear()+"-"+obj.getMonth()+"%' "+
		" GROUP BY SP2.DVKD_FK,DH.KBH_FK,DDKD_FK,NSP.NHOMSP_FK  "+
		
		" ) A "+
		" PIVOT "+
		" ( "+
		" SUM( SANLUONG) "+
		" FOR NHOMSP_FK IN "+
		" ( "+chuoingoac+" ) "+
		" ) AS T "+
		" ) AS DS ON   "+
		" CTNHOM.KENH_FK=DS.KBH_FK AND CTNHOM.DVKD_FK=DS.DVKD_FK AND CTNHOM.DDKD_FK=DS.DDKD_FK " +
		"left join           "+
		" (           "+
		" 	select ThucDat.KBH_FK,ThucDat.DVKD_FK,ThucDat.DDKD_FK,ThucDat.NPP_FK,ThucDat.ThoiGian,COUNT(ThucDat.PK_SEQ) as SoDonHangDatCtSanLuong           "+
		" 	from           "+
		" 	(           "+
		" 		select ctnpp.nhapp_fk as npp_fk,ctnpp.kenh_fk,ctnpp.dvkd_fk,ctnpp.DDKD_FK,ctnpp.ThoiGian,ctnpp.ctNgaylamviec,ctDonHang,ctSanLuong,ctSanLuongDonHang           "+
		" 		from           "+
		"            "+
		" 	(           "+
		" 			select ctnpp.nhapp_fk ,ctnpp.kenh_fk,ctnpp.dvkd_fk, ctnpp.ddkd_fk ,  ctnpp.songaylamviec as ctNgaylamviec, ctDonHang,ctSanLuongDonHang as ctSanLuongDonHang,sum(ctnpp.chitieu)as ctSanLuong  ,ctnpp.ThoiGian           "+
		" 			from           "+
		" 			(		           "+
		" 				select ctnpp.nhapp_fk ,ctnpp.kenh_fk,ctnpp.dvkd_fk, b.ddkd_fk , ctnpp.songaylamviec ,a.SODONHANG,isnull(a.sanluongtrendh,0) as ctSanLuongDonHang ,ctnpp.songaylamviec*  a.sodonhang as ctDonHang ,b.chitieu,cast(ctnpp.NAM AS varchar(4)) + '-'+ case when THANG<10 then '0'+cast(ctnpp.THANG AS varchar(2)) else cast(ctnpp.THANG AS varchar(2)) end as ThoiGian             "+
		" 				from chitieunpp_ddkd_nhomsp b inner join chitieunpp_ddkd a on  a.chitieunpp_fk=b.chitieunpp_fk and a.ddkd_fk=b.ddkd_fk             "+
		" 						inner join chitieunpp ctnpp on ctnpp.pk_seq=b.chitieunpp_fk               "+
		" 				where   trangthai<>'2'            "+
		" 				and cast(ctnpp.NAM AS varchar(4)) + '-'+ case when THANG<10 then '0'+cast(ctnpp.THANG AS varchar(2)) else cast(ctnpp.THANG AS varchar(2)) end  >='"+fromDate+"'           "+
		" 				and cast(ctnpp.NAM AS varchar(4)) + '-'+ case when THANG<10 then '0'+cast(ctnpp.THANG AS varchar(2)) else cast(ctnpp.THANG AS varchar(2)) end <='"+fromDate+"'           "+
		" 				) as ctnpp           "+
		" 			group by  ctnpp.nhapp_fk ,ctnpp.kenh_fk,ctnpp.dvkd_fk,ctnpp.DDKD_FK,ctnpp.ThoiGian,ctnpp.songaylamviec,ctDonHang,ctnpp.ctSanLuongDonHang           "+
		" 	) as ctnpp           "+
		" 	)as ChiTieu left join            "+
		" 	(	           "+
		" 		select dh.PK_SEQ,sp.dvkd_fk,dh.kbh_fk,ddkd_fk,dh.npp_fk ,sum(dh_sp.SOLUONG*sp.TRONGLUONG) as sanluong,substring(dh.NGAYNHAP,1,7)as ThoiGian           "+
		" 		from donhang dh inner join donhang_sanpham dh_sp on dh.pk_seq=dh_sp.donhang_fk inner join sanpham sp on sp.pk_seq=dh_sp.sanpham_fk             "+
		" 		where  dh.trangthai='1' and substring(dh.NGAYNHAP,1,7 )>= '"+fromDate+"'  and substring(dh.NGAYNHAP,1,7 )<= '"+fromDate+"'  and dh.PK_SEQ not in( select DONHANG_FK from DONHANGTRAVE where TRANGTHAI=3 and DONHANG_FK is not null )            "+
		" 		group by dh.PK_SEQ,sp.dvkd_fk,dh.kbh_fk,ddkd_fk,dh.npp_fk,substring(dh.NGAYNHAP,1,7)           "+
		" 	)ThucDat on ChiTieu.kenh_fk=ThucDat.kbh_fk and ChiTieu.dvkd_fk=ThucDat.dvkd_fk and ChiTieu.ddkd_fk=ThucDat.ddkd_fk           "+
		" 		and ThucDat.ThoiGian=ChiTieu.ThoiGian and ThucDat.NPP_FK=ChiTieu.npp_fk and ThucDat.sanluong>=ChiTieu.ctSanLuongDonHang           "+
		" 	group by  ThucDat.KBH_FK,ThucDat.DVKD_FK,ThucDat.DDKD_FK,ThucDat.NPP_FK,ThucDat.ThoiGian           "+
		" )as SanLuongDonHang on SanLuongDonHang.KBH_FK=CTNHOM.KENH_FK AND SanLuongDonHang.DVKD_FK=CTNHOM.DVKD_FK AND SANLUONGDONHANG.DDKD_FK=CTNHOM.DDKD_FK  "+
		" WHERE 1=1  " ;
		if(obj.getkenhId().length() > 0)
			query += " and kbh.pk_seq='"+obj.getkenhId()+"'";
		if(obj.getnppId().length() >0)
			query += " and npp.pk_seq = '"+obj.getnppId()+"'";
		if(obj.getvungId().length() > 0)
			query += " and vung.pk_seq = '"+obj.getvungId()+"'";
		if(obj.getdvkdId().length() > 0)
			query += " and dvkd.pk_seq = '"+obj.getdvkdId()+"'";
		if(obj.getkhuvucId().length() > 0)
			query += " and kv.pk_seq = '"+obj.getkhuvucId()+"'";
		if(obj.getDdkd().length() > 0)
			query += " and ddkd.pk_seq = '"+obj.getDdkd()+"'";
		
		query = query + " order by kbh.ten, dvkd.donvikinhdoanh, vung.ten, kv.ten, npp.ten, ddkd.ten ";
		sql=sql+ query;
		System.out.println("1.Query SalesRep : " + sql);
		
		return sql;
	}

			private void CreatePivotTable(OutputStream out,IStockintransit obj,String query) throws Exception
		    {   
				try{
					//String chuoi=getServletContext().getInitParameter("path") + "\\ThucHienChiTieuTT.xlsm";
					//FileInputStream fstream = new FileInputStream(chuoi);
					File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\ThucHienChiTieuTT.xlsm");
					FileInputStream fstream = new FileInputStream(f) ;
					Workbook workbook = new Workbook();
					workbook.open(fstream);
					workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
					
					CreateStaticHeader(workbook,obj); 
					FillData(workbook,obj.getFieldShow(), query, obj); 
					workbook.save(out);
					fstream.close();
			     }catch(Exception ex){
			    	 throw new Exception(ex.getMessage());
			     }	    
		   }
			private Hashtable< Integer, String > htbValueCell (){
				Hashtable<Integer, String> htb=new Hashtable<Integer, String>();
				htb.put(1,"DA");htb.put(2,"DB");htb.put(3,"DC");htb.put(4,"DD");htb.put(5,"DE");
				htb.put(6,"DF");htb.put(7,"DG");htb.put(8,"DH");htb.put(9,"DI");htb.put(10,"DJ");
				htb.put(11,"DK");htb.put(12,"DL");htb.put(13,"DM");htb.put(14,"DN");htb.put(15,"DO");
				htb.put(16,"DP");htb.put(17,"DQ");htb.put(18,"DR");htb.put(19,"DS");htb.put(20,"DT");
				htb.put(21,"DU");htb.put(22,"DV");htb.put(23,"DW");htb.put(24,"DX");htb.put(25,"DY");
				htb.put(26,"DZ");htb.put(27,"EA");htb.put(28,"EB");htb.put(29,"EC");htb.put(30,"ED");
				htb.put(31,"EE");htb.put(32,"EF");htb.put(33,"EG");htb.put(34,"EH");htb.put(35,"EI");
				htb.put(36,"EJ");htb.put(37,"EK");htb.put(38,"EL");htb.put(39,"EM");htb.put(40,"EN");
				htb.put(41,"EO");htb.put(42,"EP");htb.put(43,"EQ");htb.put(44,"ER");htb.put(45,"ES");
				htb.put(46,"ET");htb.put(47,"EU");htb.put(48,"EV");htb.put(49,"EW");htb.put(50,"EX");
				htb.put(51,"EY");htb.put(52,"EZ");htb.put(53,"FA");htb.put(54,"FB");htb.put(55,"FC");
				htb.put(56,"FD");htb.put(57,"FE");htb.put(58,"FF");htb.put(59,"FG");htb.put(60,"FH");
				htb.put(61,"FI");htb.put(62,"FJ");htb.put(63,"FK");htb.put(64,"FL");htb.put(65,"FM");
				htb.put(66,"FN");htb.put(67,"FO");htb.put(68, "FP");htb.put(69, "FQ");htb.put(70, "FR");
				htb.put(71,"FS");htb.put(72,"FT");htb.put(73, "FU");htb.put(74, "FV");htb.put(75, "FW");
				return htb; 
			}
	private void CreateStaticHeader(Workbook workbook, IStockintransit obj) 
	{
		Hashtable<Integer, String> htb=this.htbValueCell();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");
		Cells cells = worksheet.getCells();

		Style style;		
		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("A1");
		cell.setValue("TÌNH HÌNH THỰC HIỆN CHỈ TIÊU ĐẠI DIỆN KINH DOANH");

		style = cell.getStyle();

		Font font2 = new Font();
		font2.setColor(Color.RED);// mau chu
		font2.setSize(14);// size chu
		font2.setBold(true);
		style.setFont(font2);
		style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu
		cell.setStyle(style);

	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("A3");
	    
	    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Tháng : " + obj.getMonth() + "" );
	    
	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("B3"); 
	    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Năm : " + obj.getYear() + "" );
		
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A4");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A5");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getuserTen());
	   
	    cell = cells.getCell(htb.get(1)+"1"); cell.setValue("KenhBanHang");
	    cell = cells.getCell(htb.get(2)+"1"); cell.setValue("DonViKinhDoanh");
	    cell = cells.getCell(htb.get(3)+"1"); cell.setValue("ChiNhanh");
	    cell = cells.getCell(htb.get(4)+"1"); cell.setValue("KhuVuc");
	    cell = cells.getCell(htb.get(5)+"1"); cell.setValue("MaNhaPhanPhoi");	
	    cell = cells.getCell(htb.get(6)+"1");cell.setValue("NhaPhanPhoi");  	    
	    cell = cells.getCell(htb.get(7)+"1"); cell.setValue("DaiDienKinhDoanh");	    	    
	    cell = cells.getCell(htb.get(8)+"1"); cell.setValue("NgayLamViec");
	   
	    cell = cells.getCell(htb.get(9)+"1"); cell.setValue("ChiTieuSoDH");	    
	    cell = cells.getCell(htb.get(10)+"1"); cell.setValue("ThucDatSoDH");
	    cell = cells.getCell(htb.get(11)+"1"); cell.setValue("%DonHang");	
	    cell = cells.getCell(htb.get(12)+"1"); cell.setValue("CtSanLuongDonHang");
	    cell = cells.getCell(htb.get(13)+"1"); cell.setValue("SoDonHangDatCtSanLuong");
	    cell = cells.getCell(htb.get(14)+"1"); cell.setValue("%SoDonHangDatCtSanLuong");
	    String sql="select distinct nhomsanpham_fk,dbo.ftBoDau(nsp.ten) as ten  from  chitieunpp_ddkd_nhomsp "+  
		" inner join chitieunpp b on b.pk_Seq=chitieunpp_fk  "+
		" inner join nhomsanpham nsp on nsp.pk_seq=nhomsanpham_fk "+
		" where b.thang="+obj.getMonth()+" and b.nam="+ obj.getYear() ;
	    	dbutils db=new dbutils();
				if(obj.getdvkdId().length()>0){
				 sql=sql+ " and b.dvkd_fk= "+ obj.getdvkdId();
				 
				}
				if(obj.getkenhId().length()>0){
				 sql=sql+ " and b.kenh_fk= "+ obj.getkenhId();
				 
				}
				 int i=15;
				ResultSet rs=db.get(sql);
			
				if(rs!=null){
				
				 try {
					while (rs.next()){
						 
						 cell = cells.getCell(htb.get(i)+"1"); cell.setValue("ChiTieu-"+rs.getString("ten"));	
						 i=i+1;
						 cell = cells.getCell(htb.get(i)+"1"); cell.setValue("ThucDat-"+rs.getString("ten"));
						 i=i+1;
						 cell = cells.getCell(htb.get(i)+"1"); cell.setValue("PhanTram-"+rs.getString("ten"));
						 i=i+1;
					 }
					rs.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			
				 cell = cells.getCell(htb.get(i)+"1"); cell.setValue("ChiTieuSec");	
				 i=i+1;
				 cell = cells.getCell(htb.get(i)+"1"); cell.setValue("ThucDatSec");
				 i=i+1;
				 cell = cells.getCell(htb.get(i)+"1"); cell.setValue("PhanTramSec");
				
				 sql="select loaichitieu from chitieu_sec where thang="+obj.getMonth()+" and nam="+obj.getYear() +" and trangthai<>'2'";
					//loai chi tieu sanluong la 2
					
					 
					 ResultSet rscheck=db.get(sql);
					try{
						 if(rscheck.next()){
							if(rscheck.getString("loaichitieu").equals("1")){
								cells.setRowHeight(5, 18.0f);
							    cell = cells.getCell("A6");
							    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Báo cáo chia chỉ tiêu  theo đơn vị tiền tệ ");
							}else{
								cells.setRowHeight(5, 18.0f);
							    cell = cells.getCell("A6");
							    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Báo cáo chỉ tiêu theo sản lượng");
							}
							rscheck.close();
						 }
					}catch(Exception er){
						
					}
					
				 
				db.shutDown();
				
				cell = cells.getCell("M1"); 
			    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9,(26*4+i)+"");
	   
	}

	private void FillData(Workbook workbook,String[] fieldShow, String query, IStockintransit obj)throws Exception 
	{
		
		Hashtable<Integer, String> htb=this.htbValueCell();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
		
	    cells.setColumnWidth(0, 10.0f);
		cells.setColumnWidth(1, 15.0f);
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
		cells.setColumnWidth(12, 15.0f);
		cells.setColumnWidth(13, 15.0f);
		cells.setColumnWidth(14, 15.0f);
		cells.setColumnWidth(15, 15.0f);
		cells.setColumnWidth(16, 15.0f);
		
		dbutils db = new dbutils();		
		
		
		
		
		
		ResultSet rs = db.get(query);	
		
		int indexRow = 2;
		try 
			{				
				Cell cell = null;
				float phantramMTD = 0;
				

				while(rs.next())
				{ 				
					
				    cell = cells.getCell(htb.get(1) + Integer.toString(indexRow)); cell.setValue(rs.getString("KENH"));
				    cell = cells.getCell(htb.get(2) + Integer.toString(indexRow)); cell.setValue(rs.getString("DONVIKINHDOANH"));
					cell = cells.getCell(htb.get(3) + Integer.toString(indexRow)); cell.setValue(rs.getString("VUNG"));
					cell = cells.getCell(htb.get(4) + Integer.toString(indexRow)); cell.setValue(rs.getString("KHUVUC"));					
					cell = cells.getCell(htb.get(5) + Integer.toString(indexRow));cell.setValue(rs.getString("NPP_FK"));				
					cell = cells.getCell(htb.get(6) + Integer.toString(indexRow));  cell.setValue(rs.getString("NHAPHANPHOI"));					
					cell = cells.getCell(htb.get(7)+ Integer.toString(indexRow)); cell.setValue(rs.getString("DAIDIENKINHDOANH"));
					cell = cells.getCell(htb.get(8)+ Integer.toString(indexRow)); cell.setValue(rs.getFloat("SONGAYLAMVIEC"));
					
					cell = cells.getCell(htb.get(9)+ Integer.toString(indexRow)); cell.setValue(rs.getFloat("SODONHANG")*rs.getFloat("SONGAYLAMVIEC"));
					cell = cells.getCell(htb.get(10)+ Integer.toString(indexRow)); cell.setValue(rs.getFloat("DATDONHANG"));
					float PhanTramDonhang=0;
					if(rs.getFloat("SODONHANG")*rs.getFloat("SONGAYLAMVIEC") >0){
						PhanTramDonhang=	100* rs.getFloat("DATDONHANG")/(rs.getFloat("SODONHANG")*rs.getFloat("SONGAYLAMVIEC"));
					}
					cell = cells.getCell(htb.get(11)+ Integer.toString(indexRow)); cell.setValue(PhanTramDonhang);
					cell = cells.getCell(htb.get(12)+ Integer.toString(indexRow)); cell.setValue(rs.getFloat("CtSanLuongDonHang"));
					cell = cells.getCell(htb.get(13)+ Integer.toString(indexRow)); cell.setValue(rs.getFloat("SoDonHangDatCtSanLuong"));
					
					float PhanTramSanLuongDH=0;
					if(rs.getFloat("SODONHANG")*rs.getFloat("SONGAYLAMVIEC") >0)
					{
						PhanTramSanLuongDH=	100* rs.getFloat("SoDonHangDatCtSanLuong")/(rs.getFloat("SODONHANG")*rs.getFloat("SONGAYLAMVIEC"));
					}
					cell = cells.getCell(htb.get(14)+ Integer.toString(indexRow)); cell.setValue(PhanTramSanLuongDH);
					
					String []chuoi =obj.getFieldShow();
					int j=15;
					float SumChiTieuDDKD=0;
					float SumThucDatCTDDKD=0;
					for (int i=0;i<chuoi.length ;i++){
						if(chuoi[i]!=null){
							cell = cells.getCell(htb.get(j) + Integer.toString(indexRow));  cell.setValue( rs.getFloat("CT"+chuoi[i]));	
						
							
							SumChiTieuDDKD= SumChiTieuDDKD+rs.getFloat("CT"+chuoi[i]);
							SumThucDatCTDDKD= SumThucDatCTDDKD+rs.getFloat("DS"+chuoi[i]);
							j=j+1;
							cell = cells.getCell(htb.get(j)+ Integer.toString(indexRow)); cell.setValue(rs.getFloat("DS"+chuoi[i]));
							j=j+1;
							phantramMTD =0;
							
							if(rs.getFloat("CT"+chuoi[i]) >0){
								phantramMTD=rs.getFloat("DS"+chuoi[i])*100/rs.getFloat("CT"+chuoi[i]);
							}
							cell = cells.getCell(htb.get(j)+ Integer.toString(indexRow)); cell.setValue(phantramMTD);
							j=j+1;
						}
					}
					
					cell = cells.getCell(htb.get(j) + Integer.toString(indexRow));  cell.setValue(SumChiTieuDDKD);					
					
					j=j+1;
					cell = cells.getCell(htb.get(j)+ Integer.toString(indexRow)); cell.setValue(SumThucDatCTDDKD);
					j=j+1;
					float SumphantramMTD =0;
					
					if(SumChiTieuDDKD >0){
						SumphantramMTD=SumThucDatCTDDKD*100/SumChiTieuDDKD;
					}
					cell = cells.getCell(htb.get(j)+ Integer.toString(indexRow)); cell.setValue(SumphantramMTD);
					j=j+1;
					indexRow++;
				
				}
				if(rs != null) rs.close();
				if(db!=null){
					db.shutDown();
				}

		    	
			}catch(java.sql.SQLException err){
				System.out.println(err.toString());
				throw new Exception("Khong the tao duoc bao cao trong thoi gian nay. Error :"+err.toString());
			}
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
}
