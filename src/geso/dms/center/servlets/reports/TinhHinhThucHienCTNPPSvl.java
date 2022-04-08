package geso.dms.center.servlets.reports;

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
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;


public class TinhHinhThucHienCTNPPSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public TinhHinhThucHienCTNPPSvl() {
        super();
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
		
		return htb; 
	}
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
	    String nppId = util.getIdNhapp(userId);
		String nppTen = util.getTenNhaPP();

		obj.setnppId(nppId);
		obj.setnppTen(nppTen);
	    obj.init();
	    
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/AHF/pages/Distributor/TinhhinhthuchienCTNpp.jsp";
		response.sendRedirect(nextJSP);	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
	    IStockintransit obj = new Stockintransit(); 
	    String userId = (String) session.getAttribute("userId");  
	    String userTen = (String) session.getAttribute("userTen"); 

	    if(userId == null)   
	    	userId ="";
	    
	    obj.setuserId(userId);
	    
	    Utility util = new Utility();
	    String nppId = util.getIdNhapp(userId);
		String nppTen = util.getTenNhaPP();

		obj.setnppId(nppId);
		obj.setnppTen(nppTen);
	 
     	
   	 	obj.setuserTen(userTen);
   	 	String kenhId=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")));
   	 	if(kenhId == null)
	   		 kenhId ="";
   	 	obj.setkenhId(kenhId);
	   	 
	   	String dvkdId=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")));
	   	
	   	if(dvkdId == null)
	   		dvkdId ="";
	   	
	   	obj.setdvkdId(dvkdId);
	   	 
	   	 
  	 obj.setMonth(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("month"))==null? "":util.antiSQLInspection(request.getParameter("month"))));
	   	 
	   	 obj.setYear(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("year"))==null? "":util.antiSQLInspection(request.getParameter("year"))));	   	 
	   	 
	 	String vungId=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")));
	   	if(vungId ==null)
	   		 vungId = "";
	   	
	   	obj.setvungId(vungId);
	   	 
	   	String khuvucId=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")));
	   	if(khuvucId == null)
	   		 khuvucId ="";
	   	obj.setkhuvucId(khuvucId);
	   	 
	   	String dvdlId=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvdlId")));
		if(dvdlId == null)
			 dvdlId ="";
		obj.setdvdlId(dvdlId);
		 
		String []fieldsHien = request.getParameterValues("fieldsHien");
		obj.setFieldShow(fieldsHien);
		 
		 
		String sql ="";		 
		String action = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")));
		 
		String nextJSP = "/AHF/pages/Distributor/TinhhinhthuchienCTNpp.jsp";
		try{
			if(action.equals("Taomoi"))
			{
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=ThucHienChiTieu.xlsm");
				OutputStream out = response.getOutputStream();
				ExportToExcel(out,obj);			
			}	else{
				obj.init();	    
				session.setAttribute("obj", obj);
				session.setAttribute("userId", obj.getuserId());		
				response.sendRedirect(nextJSP);
			}
		}catch(Exception ex){
			obj.setMsg(ex.getMessage());
			obj.init();	    
			session.setAttribute("obj", obj);
			session.setAttribute("userId", obj.getuserId());		
			response.sendRedirect(nextJSP);
		}
	
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
	
	private void TaoBaoCao(com.aspose.cells.Workbook workbook, IStockintransit obj, int sheetNum )throws Exception
	{
		try
		{
		     geso.dms.center.util.Utility	util = new geso.dms.center.util.Utility();
			com.aspose.cells.Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(sheetNum);
			com.aspose.cells.Cells cells = worksheet.getCells();

			String nhanvien = "SR";
			
			dbutils db = new dbutils();
			
			cells.merge(0, 0, 0, 3 );		
			com.aspose.cells.Cell cell = cells.getCell("A1");
			ReportAPI.getCellStyle(cell, Color.RED, true, 16,"THỰC ĐẠT CHỈ TIÊU "+ nhanvien);
			
			
			
			cells.merge(1, 0, 1, 3 );
			cell = cells.getCell("A2");
			cell.setValue("Ngày tạo: " + getDateTime());
			//ReportAPI.setBorder_Style_MergerCell(cells, 1, 1, 0, 3, BorderLineType.NONE, Color.BLUE, cell.getStyle());
			
			cell = cells.getCell("A3");
			cell.setValue("Người tạo: " + obj.getuserTen());
		//	ReportAPI.setBorder_Style_MergerCell(cells, 1, 1, 0, 3, BorderLineType.NONE, Color.BLUE, cell.getStyle());
			
			String dauthang = obj.getYear() + "-" + (obj.getMonth().trim().length() > 1 ? obj.getMonth() : "0" + obj.getMonth()) + "-01";
			
			String query = "";
			String queryCHITIEU = "";
			String queryTHUCDAT = "";
			String dk = "";
			db.update("SET TRANSACTION ISOLATION LEVEL SNAPSHOT;");
			db.update("SET LOCK_TIMEOUT 1200000;");
			db.getConnection().setAutoCommit(false);
			if(obj.getnppId().length() > 0)
				dk = " and c.npp_fk in ("+obj.getnppId()+") ";
			
			//B1 Lấy tiêu chí
			query =  " select b.PK_SEQ, b.DIENGIAI, b.NHOMSP_FK, b.TIEUCHI, b.noidung, b.loaiDS   "+
					 " from TIEUCHITINHTHUONG a inner join TIEUCHITHUONG_CHITIET b on a.PK_SEQ = b.TIEUCHITINHTHUONG_FK"+
					 " where a.THANG = " + obj.getMonth() + " and a.NAM = '" + obj.getYear() + "' and a.TRANGTHAI = '1'"+
					 " order by PK_SEQ asc";
			System.out.println("::: LAY TIEU CHI THUONG: " + query);
			ResultSet rs = db.get(query);
			
			String sqlBaocao = "";
			String sqlTieuchi = "";
			String sqlThucdat01 = "";
			String sqlThucdat02 = "";
			query = "\n insert donhangchitieu (NhanVien_FK, SANPHAM_FK,khachhang_fk, donhang_fk,npp_fk, GIAMUA, SOLUONG,userId)" +
                    "\n SELECT DH.ddkd_fk as NhanVien_FK, DH_SP.SANPHAM_FK,DH.KHACHHANG_FK, dh.PK_SEQ, NPP_FK, GIAMUA, SOLUONG, " + obj.getuserId() + " userId " +
                    "\n FROM DONHANG DH   " +
                    "\n inner join DONHANG_SANPHAM DH_SP on DH_SP.DONHANG_FK = dh.PK_SEQ  " +
                    "\n where dh.NGAYNHAP >= '" + dauthang + "' and dh.NGAYNHAP <= CONVERT(char(10),DATEADD(DAY, -(DAY('" + dauthang + "')), DATEADD(MONTH,1,'" + dauthang + "' )), 120)    and dh.trangthai = 1 " +
                    "\n and dh.npp_fk in ("+obj.getnppId()+") ";
                 	
				System.out.println("in ________:"+query);
				if(db.updateReturnInt(query) < 0 )
				{
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					System.out.println("Lỗi ...........");
					//throw new Exception("Lỗi ! không thể tạo báo cáo !");
					return ;
				}
			while( rs.next() )
			{
				
				
				sqlTieuchi += "[" + rs.getString("DIENGIAI") + "], ";
				sqlThucdat01 += " ISNULL( [" + rs.getString("PK_SEQ") + "].thucdat, 0 ) as [TD_" + rs.getString("DIENGIAI") + "], ";
				sqlThucdat02 += " left join dbo.ufn_KPI_SR('" + dauthang + "', " + rs.getString("PK_SEQ") + ", " + rs.getString("TIEUCHI") + ", null, " + obj.getuserId() + ") [" + rs.getString("PK_SEQ") + "] on a.PK_SEQ = [" + rs.getString("PK_SEQ") + "].NhanVien_FK   ";
			
				sqlBaocao += ", [" + rs.getString("DIENGIAI") + "], [TD_" + rs.getString("DIENGIAI") + "], " + 
							 " dbo.TyLeKPI( [TD_" + rs.getString("DIENGIAI") + "], [" + rs.getString("DIENGIAI") + "] ) as tile, " + 
							 " dbo.TinhThuongKPI( dbo.TyLeKPI( [TD_" + rs.getString("DIENGIAI") + "], [" + rs.getString("DIENGIAI") + "] ), " + rs.getString("TIEUCHI") + ", " + rs.getString("loaiDS") + ", [TD_" + rs.getString("DIENGIAI") + "], '" + rs.getString("noidung") + "' ) as thuong ";
			}
			rs.close();
			
			
			System.out.println("::: SQL BAO CAO: " + sqlBaocao);
			if( sqlThucdat01.trim().length() > 0 )
			{
				sqlThucdat01 = sqlThucdat01.substring(0, sqlThucdat01.length() - 2);
				queryTHUCDAT = "select  a.NPP_FK, a.PK_SEQ as manhanvien, " + sqlThucdat01 + " from DAIDIENKINHDOANH a " + sqlThucdat02;
				
				
				System.out.println("hhushd" + queryTHUCDAT);
				sqlTieuchi = sqlTieuchi.substring(0, sqlTieuchi.length() - 2);
				queryCHITIEU =   "\n select vung,khuvuc,gsbh, manpp ,npp, manhanvien, MaNV, tennhanvien,[dbo].[GetNgayCong](manhanvien,'"+obj.getMonth()+"','"+obj.getYear()+"') as ngaycong, " + sqlTieuchi + 
								 "\nfrom " + 
								 "\n( " + 
								 "\n	select isnull(v.ten,'') as Vung, npp.sitecode as manpp ,isnull(kv.ten,'') as KhuVuc, isnull(gsbh.ten,'') as GSBH,isnull(npp.ten,'') as NPP,"
								 + " c.PK_SEQ as manhanvien,c.smartid as MaNV, c.TEN as tennhanvien, d.DIENGIAI as tieuchi, b.chitieu " + 
								 "\n	from CHITIEUNHANVIEN a inner join ChiTieuNhanVien_DDKD b on a.pk_seq = b.CTNV_FK " + 
								 "\n		inner join DAIDIENKINHDOANH c on b.NhanVien_FK = c.PK_SEQ " + 
								 "\n		inner join TIEUCHITHUONG_CHITIET d on b.TCTCT_FK = d.PK_SEQ  "
								 + "\n		left join Nhaphanphoi npp on npp.pk_seq = c.npp_fk "
								 + "\n		left join khuvuc kv on kv.pk_seq = npp.khuvuc_fk "
								 + "\n 		left join vung v on v.pk_seq = kv.vung_fk "
								 + "\n 		left join ddkd_gsbh gs on gs.ddkd_fk = c.pk_seq"
								 + "\n 		left join giamsatbanhang gsbh on gsbh.pk_seq = gs.gsbh_fk and gsbh.trangthai =  '1' " + 
								 "\n		where  (select SUM(hf.chitieu) from ChiTieuNhanVien_DDKD hf " + 
								 "\n where hf.NhanVien_FK = c.PK_SEQ and b.chitieu = hf.chitieu   " + 
								 "\n ) > 0 and  a.TRANGTHAI = 1 and a.THANG = '" + obj.getMonth() + "' and a.NAM = '" + obj.getYear() + "' " +dk+
							
								 "\n ) " + 
								 "\n DT PIVOT ( max(chitieu) FOR tieuchi IN ( " + sqlTieuchi + " ) ) AS pvt ";
			}
			
			System.out.println("::: LẤY CHỈ TIÊU: " + queryCHITIEU);
			System.out.println("::: LẤY THỰC ĐẠT: " + queryTHUCDAT);
			
			query = "select vung,khuvuc,gsbh, manpp,npp, CT.manv, CT.tennhanvien, ct.ngaycong " + sqlBaocao + 
					"from ( " + queryCHITIEU + " ) CT left join ( " + queryTHUCDAT + " ) TD on CT.manhanvien = TD.manhanvien   and CT.manpp=(select MA from nhaphanphoi where pk_seq=TD.NPP_FK) ";
			System.out.println("query la " +query);
			
			//Vẽ tiêu đề
			
			cell = cells.getCell("A5");
			cell.setValue("Vùng");
			ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			
			cell = cells.getCell("B5");
			cell.setValue("Khu Vực");
			ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			
			cell = cells.getCell("C5");
			cell.setValue("GSBH");
			ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			
			cell = cells.getCell("D5");
			cell.setValue("MaNPP");
			ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			
			cell = cells.getCell("E5");
			cell.setValue("NPP");
			ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			
			
			cell = cells.getCell("F5");
			cell.setValue("Mã nhân viên");
			ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			
			cell = cells.getCell("G5");
			cell.setValue("Tên nhân viên");
			cells.setColumnWidth(1, 30.0f);
			ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			
			cell = cells.getCell("H5");
			cell.setValue("Ngày công");
			cells.setColumnWidth(1, 30.0f);
			ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			
			
			String[] tieude = sqlTieuchi.split(",");
			
			
			for( int i = 0; i < tieude.length; i++ )
			{
				int cobatdau = (i * 4) + 8;
				cells.merge(3, cobatdau, 3, cobatdau + 3 );		
				cell = cells.getCell(3, cobatdau );
				cell.setValue( tieude[i].replace("[", "").replace("]", "") );
				ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
				ReportAPI.setBorder_Style_MergerCell(cells, 3, 3, cobatdau, cobatdau + 3, BorderLineType.THIN, Color.BLACK, cell.getStyle());
				
				cell = cells.getCell(4, cobatdau++);
				cell.setValue("Chỉ tiêu");
				cells.setColumnWidth(cobatdau - 1, 13.0f);
				ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
				
				cell = cells.getCell(4, cobatdau++);
				cell.setValue("Thực đạt");
				cells.setColumnWidth(cobatdau - 1, 13.0f);
				ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
				
				cell = cells.getCell(4, cobatdau++);
				cell.setValue("Tỉ lệ đạt (%)");
				cells.setColumnWidth(cobatdau - 1, 13.0f);
				ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
				
				cell = cells.getCell(4, cobatdau++);
				cell.setValue("Thưởng");
				cells.setColumnWidth(cobatdau - 1, 13.0f);
				ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			}

			System.out.println("::: LẤY BÁO CÁO: " + query);
			ResultSet ctRs = db.get(query);
			ResultSetMetaData rsmd = ctRs.getMetaData();
			int socottrongSql = rsmd.getColumnCount();
			int countRow = 5;
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
					}
				}
				
				countRow ++;
			}
			db.getConnection().rollback();
			db.getConnection().setAutoCommit(true);
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
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private String setQuery( IStockintransit obj) {
		String fromYear = obj.getYear();
		String fromMonth = obj.getMonth();
		
		String fromDate=fromYear+'-'+fromMonth;
		String query="";
		long numofDay = 0;
		geso.dms.center.db.sql.dbutils db= new geso.dms.center.db.sql.dbutils();
	
		
		geso.dms.center.util.Utility ut = new geso.dms.center.util.Utility();
		
	
		
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
						 chuoiselct="isnull(CTNHOM.["+rs.getString("nhomsanpham_fk")+"],0) AS CT"+rs.getString("nhomsanpham_fk")+",ISNULL(DS.["+rs.getString("nhomsanpham_fk")+"],0) AS DS"+rs.getString("nhomsanpham_fk"); // +",ISNULL(CTPRI.["+rs.getString("nhomsanpham_fk")+"],0) AS CTPRI"+rs.getString("nhomsanpham_fk")+",ISNULL(DSPRI.["+rs.getString("nhomsanpham_fk")+"],0) AS DSPRI"+rs.getString("nhomsanpham_fk");
					 }else{
						 chuoi=chuoi+","+rs.getString("nhomsanpham_fk");
						 chuoiselct=chuoiselct+" ,isnull(CTNHOM.["+rs.getString("nhomsanpham_fk")+"],0) AS CT"+rs.getString("nhomsanpham_fk")+",ISNULL(DS.["+rs.getString("nhomsanpham_fk")+"],0) AS DS"+rs.getString("nhomsanpham_fk"); // +",ISNULL(CTPRI.["+rs.getString("nhomsanpham_fk")+"],0) AS CTPRI"+rs.getString("nhomsanpham_fk")+",ISNULL(DSPRI.["+rs.getString("nhomsanpham_fk")+"],0) AS DSPRI"+rs.getString("nhomsanpham_fk");
						 chuoingoac=chuoingoac+",["+rs.getString("nhomsanpham_fk")+"]";
					 }
					 arraychuoi[i]=rs.getString("nhomsanpham_fk");
					 i++;
					 
				 }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		//dung file show de luu chuoi;
		 

		  sql="select distinct nhomsp_fk as nhomsanpham_fk ,dbo.ftBoDau(nsp.ten) as ten  from  chitieu_nhapp_nhomsp "+  
					" inner join chitieu b on b.pk_Seq=chitieu_fk  "+
					" inner join nhomsanpham nsp on nsp.pk_seq=nhomsp_fk "+
					" where b.thang="+obj.getMonth()+" and b.nam="+ obj.getYear() ;
		 
		 if(obj.getdvkdId().length()>0){
			 sql=sql+ " and b.dvkd_fk= "+ obj.getdvkdId();
			 
		 }
		 if(obj.getkenhId().length()>0){
			 sql=sql+ " and b.kenh_fk= "+ obj.getkenhId();
			 
		 }
		 System.out.println("Thong Tin : "+sql);
		 
		  rs=db.get(sql);
		 String chuoiPri="";
		 String[] arraychuoiPri= new String[10];
		 String chuoiselctPri="";
		 String chuoingoacPri="";//co dau []
		 
		 if(rs!=null){
			 int i=0;
			 try {
				while (rs.next()){
					
					 if(i==0){
						 chuoingoacPri="["+rs.getString("nhomsanpham_fk")+"]";
						 chuoiPri=rs.getString("nhomsanpham_fk");
						 chuoiselctPri="ISNULL(CTPRI.["+rs.getString("nhomsanpham_fk")+"],0) AS CTPRI"+rs.getString("nhomsanpham_fk")+",ISNULL(DSPRI.["+rs.getString("nhomsanpham_fk")+"],0) AS DSPRI"+rs.getString("nhomsanpham_fk");
					 }else{
						 chuoiPri=chuoiPri+","+rs.getString("nhomsanpham_fk");
						 chuoiselctPri=chuoiselctPri+ " ,ISNULL(CTPRI.["+rs.getString("nhomsanpham_fk")+"],0) AS CTPRI"+rs.getString("nhomsanpham_fk")+",ISNULL(DSPRI.["+rs.getString("nhomsanpham_fk")+"],0) AS DSPRI"+rs.getString("nhomsanpham_fk");
						 chuoingoacPri=chuoingoacPri+",["+rs.getString("nhomsanpham_fk")+"]";
					 }
					 arraychuoiPri[i]=rs.getString("nhomsanpham_fk");
					 i++;
					 
				 }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 
		 
		 
		 
		 obj.setFieldShow(arraychuoi);
		 obj.setFieldHidden(arraychuoiPri);
		 // set loai chi tieu san luong hay tie
		 sql="select loaichitieu from chitieu_sec where thang="+obj.getMonth()+" and nam="+obj.getYear() +" and trangthai<>'2'";
			//loai chi tieu sanluong la 2
			 boolean sanluong=true;
			 System.out.println("Loai Chi Tieu ; "+sql);
			 
			 ResultSet rscheck=db.get(sql);
			try{
				 if(rscheck.next()){
					if(rscheck.getString("loaichitieu").equals("1")){
					sanluong=false;
					}
					rscheck.close();
				 }
			}catch(Exception er){
				
			}
		 
		 

			
		query=" SELECT   	kbh.ten as kenh,dvkd.donvikinhdoanh ,vung.ten as vung,kv.ten as khuvuc,npp.ma as manpp,npp.ten as tennpp, \n"+ 	
				"  ctnhom.kenh_fk,ctnhom.dvkd_fk,ctnhom.songaylamviec,ctnhom.donhang,sodh.donhang as datdonhang, ctSanLuongDonHang,SoDonHangDatCtSanLuong," +chuoiselct +", "+chuoiselctPri +	
				"  FROM  		( 	 \n"+
				"  SELECT  ct.nhapp_fk as npp_fk ,ct.dvkd_fk,ct.kenh_fk,ct.songaylamviec, b.nhomsanpham_fk ,sum(a.sanluongtrendh) as ctSanLuongDonHang,sum(a.sodonhang) as donhang ,sum(b.chitieu) as chitieunhom \n"+
				"  FROM chitieunpp_ddkd_nhomsp b inner join chitieunpp_ddkd a on  		 a.chitieunpp_fk=b.chitieunpp_fk and b.ddkd_fk=a.ddkd_fk \n"+	
				"  inner join chitieunpp ct on ct.pk_seq=b.chitieunpp_fk \n"+ 
				"  where ct.thang="+obj.getMonth()+" and ct.nam="+obj.getYear()+" and ct.nhapp_fk=  "+obj.getnppId()+
				"  group by ct.nhapp_fk ,ct.dvkd_fk,ct.kenh_fk,b.nhomsanpham_fk,ct.songaylamviec \n"+
				"  		) p 	 \n"+
				"  PIVOT 		\n "+
				"  ( 		 sum(chitieunhom) 	 \n"+
				"  	FOR nhomsanpham_fk IN 		("+chuoingoac+") \n"+ 		
				"  ) AS ctnhom 	 \n"+
				"  left join  		( 		 \n"+
				"  select sp.dvkd_fk,dh.kbh_fk,npp_fk, count(distinct dh.pk_seq) as donhang  \n"+
				"  from donhang dh inner join  		donhang_sanpham dh_sp on dh.pk_seq=dh_sp.donhang_fk \n"+ 
				"  inner join sanpham sp on sp.pk_seq=dh_sp.sanpham_fk 	 \n"+
				"  where dh.ngaynhap like '"+obj.getYear()+"-"+obj.getMonth()+"%'  and dh.npp_fk="+obj.getnppId()+" and dh.trangthai='1'		group by sp.dvkd_fk,dh.kbh_fk,npp_fk 	 \n"+
				"  ) sodh on 		ctnhom.kenh_fk=sodh.kbh_fk and ctnhom.dvkd_fk=sodh.dvkd_fk and ctnhom.npp_fk=sodh.npp_fk \n"+    
				"  left join  		( 		 \n"+
				"  select dvkd_fk, npp_fk,kbh_fk ,  "+chuoingoac+"		 \n"+
				"  from (  		select sp.dvkd_fk,dh.kbh_fk,npp_fk,nsp.nhomsp_fk, \n" ;
				if(sanluong){
					query=query+ "  sum( soluong* trongluong) as sanluong \n";
				}else{
					query=query+ "  sum( soluong * dh_sp.giamua) as sanluong \n";
				}
				query=query+ "  from donhang dh inner join  		 \n" +
				"  donhang_sanpham dh_sp on dh.pk_seq=dh_sp.donhang_fk \n"+ 		
				"  inner join sanpham sp on sp.pk_seq=dh_sp.sanpham_fk 		 \n"+
				"  inner join ( \n" +
				"		select max(nsp_fk) as nhomsp_fk,sp_fk as sanpham_fk from nhomsanpham_sanpham 	 \n"+
				"  where nsp_fk in ("+chuoi+") 		group by sp_fk 	 \n" +
				"	) as nsp on nsp.sanpham_fk=sp.pk_seq 	 \n"+
				"  where dh.ngaynhap like '"+obj.getYear()+"-"+obj.getMonth()+"%'  and dh.trangthai='1' and dh.npp_fk="+obj.getnppId() +
				"  group by sp.dvkd_fk,dh.kbh_fk,npp_fk,nsp.nhomsp_fk  		 \n" +
				//THEM PHAN DON HANG TRAVE 
				"  UNION ALL \n" +
				"  SELECT SP2.DVKD_FK AS DVKD_FK, DH.KBH_FK,  DH.NPP_FK  , NSP.NHOMSP_FK ,\n" ;
				if(!sanluong){
					query=query+		"(-1)*	 SUM(	ISNULL(DH_SP.GIAMUA, DH_SP1.GIAMUA) *   ISNULL(DH_SP.SOLUONG, DH_SP1.SOLUONG) ) AS DOANHSO  \n" ;
				}else{
					query=query+		"(-1)*	SUM(TRONGLUONG  *   ISNULL(DH_SP.SOLUONG, DH_SP1.SOLUONG) ) AS DOANHSO  \n";
				}
				query=query+	 "		FROM  DONHANGTRAVE DH     \n"+
				"		LEFT OUTER JOIN  DONHANGTRAVE_SANPHAM DH_SP ON DH_SP.DONHANGTRAVE_FK = DH.PK_SEQ   \n"+  	
				"		LEFT OUTER JOIN  DONHANG_SANPHAM DH_SP1 ON DH.DONHANG_FK = DH_SP1.DONHANG_FK  \n"+
				"		INNER JOIN SANPHAM SP2 ON SP2.PK_SEQ = ISNULL(DH_SP.SANPHAM_FK, DH_SP1.SANPHAM_FK) \n"+
				"		INNER JOIN (   \n"+
				"		SELECT MAX(NSP_FK) AS NHOMSP_FK,SP_FK AS SANPHAM_FK FROM NHOMSANPHAM_SANPHAM  \n"+
				"		WHERE NSP_FK IN ("+chuoi+")  \n"+
				"		GROUP BY SP_FK   \n"+
				"		) AS NSP ON NSP.SANPHAM_FK=SP2.PK_SEQ  \n"+
				"		WHERE  DH.TRANGTHAI='3' AND DH.NGAYNHAP LIKE '"+obj.getYear()+"-"+obj.getMonth()+"%' and dh.npp_fk= "+obj.getnppId() +
				"		GROUP BY SP2.DVKD_FK,DH.KBH_FK,NPP_FK,NSP.NHOMSP_FK \n"+
				"		) a \n"+ 
				"  	pivot 		( 		  sum( sanluong) 		FOR nhomsp_fk IN 		("+chuoingoac+" ) 	 \n"+
				"  ) as t 		) as ds on  		ctnhom.kenh_fk=ds.kbh_fk and ctnhom.dvkd_fk=ds.dvkd_fk and ctnhom.npp_fk=ds.npp_fk \n"+ 
				"  left join  ( \n"+
				"  select dvkd_fk, npp_fk,kbh_fk , "+chuoingoacPri+" \n"+
				"  from (   select sp.dvkd_fk,dh.kbh_fk,npp_fk,nsp.nhomsp_fk, " ;
				if(sanluong){
					query=query+ "  sum( soluong* trongluong) as sanluong \n" ;
				}else{
					query=query+ "  sum( soluong* dh_sp.gianet + isnull(dh_sp.vat,0)) as sanluong \n" ;
				}
				query=query+  "  from hdnhaphang dh inner join   hdnhaphang_sp dh_sp on dh.pk_seq=dh_sp.nhaphang_fk  inner join sanpham sp on sp.ma=dh_sp.sanpham_fk \n"+ 
				"  inner join (  select max(nsp_fk) as nhomsp_fk,sp_fk as sanpham_fk from nhomsanpham_sanpham  \n"+
				"  where nsp_fk in ("+chuoiPri+")  group by sp_fk  ) as nsp on nsp.sanpham_fk=sp.pk_seq \n"+
				"  where dh.ngaynhan like '"+obj.getYear()+"-"+obj.getMonth()+"%' and dh.npp_fk="+obj.getnppId()+"  and dh.trangthai <>2 and dh.loaidonhang='0' \n" +
						" group by sp.dvkd_fk,dh.kbh_fk,npp_fk,nsp.nhomsp_fk  ) a  pivot \n"+
				"  (   sum( sanluong)  FOR nhomsp_fk IN  ("+chuoingoacPri+" )  ) as t  ) as dspri on \n"+
				"  ctnhom.kenh_fk=dspri.kbh_fk and ctnhom.dvkd_fk=dspri.dvkd_fk and ctnhom.npp_fk=dspri.npp_fk \n"+ 
				"  left join  (  \n"+
				"  select dvkd_fk, npp_fk,kenh_fk , "+chuoingoacPri+" from  "+
				"  (   select ct.kenh_fk,ct.dvkd_fk, b.npp_fk,NHOMSP_FK , sum(b.chitieu) as chitieu   from chitieu ct \n"+
				"  inner join   CHITIEU_NHAPP_NHOMSP  b on ct.pk_seq=b.chitieu_fk  \n "+
				"  WHERE CT.THANG="+obj.getMonth()+" and ct.NAM="+obj.getYear()+" and b.npp_fk="+obj.getnppId()+"  group  by   ct.kenh_fk ,ct.dvkd_fk, b.npp_fk ,NHOMSP_FK  )  \n"+
				"  a  pivot  (  sum( chitieu)  FOR NHOMSP_FK IN  ( "+chuoingoacPri+" )  ) as t \n"+
				"  ) ctpri on   \n"+
				"  ctpri.kenh_fk=ctnhom.kenh_fk and ctpri.dvkd_fk=ctnhom.dvkd_fk and ctpri.npp_fk=ctnhom.npp_fk \n"+ 
				"  inner join nhaphanphoi npp on npp.pk_seq=ctnhom.npp_fk \n "+ 		
				"  inner join khuvuc kv on kv.pk_seq=npp.khuvuc_fk  \n"+		
				"  inner join vung  on vung.pk_Seq=kv.vung_fk 	\n "+
				"  inner join kenhbanhang kbh on kbh.pk_seq=ctnhom.kenh_fk 	 \n"+
				"  inner join donvikinhdoanh dvkd on dvkd.pk_seq=ctnhom.dvkd_fk \n" +
				"left join  \n       "+
				"(       \n       "+
				"	select ChiTieu.KENH_FK,ChiTieu.DVKD_FK,ChiTieu.npp_fk,ChiTieu.ThoiGian  ,COUNT(ThucDat.DonHangId) as SoDonHangDatCtSanLuong       \n       "+
				"	from       \n       "+
				"		(       \n       "+
				"			select npp_fk ,ct.kenh_fk,ct.dvkd_fk ,ctSanLuongDonHang   ,ct.ThoiGian       \n       "+
				"			from       \n       "+
				"			(		       \n       "+
				"				select  ct.nhapp_fk as npp_fk ,ct.dvkd_fk,ct.kenh_fk,ct.songaylamviec, b.nhomsanpham_fk ,sum(a.sodonhang) as donhang,Sum(a.SanLuongTrenDH) as ctSanLuongDonHang  ,sum(b.chitieu) as chitieunhom  ,cast(ct.NAM AS varchar(4)) + '-' +case when THANG<10 then '0'+cast(ct.THANG AS varchar(2)) else cast(ct.THANG AS varchar(2)) end as ThoiGian  \n       "+
				"				FROM   \n       "+
				"					chitieunpp_ddkd_nhomsp b inner join chitieunpp_ddkd a on  a.chitieunpp_fk=b.chitieunpp_fk and b.ddkd_fk=a.ddkd_fk   \n       "+
				"					inner join chitieunpp ct on ct.pk_seq=b.chitieunpp_fk  \n       "+
				"					where ct.TRANGTHAI!=2  \n       "+
				"						and cast(CT.NAM AS varchar(4))+  '-' +case when THANG<10 then '0'+cast(ct.THANG AS varchar(2)) else cast(ct.THANG AS varchar(2)) end  >='"+fromDate+"'       \n       "+
				"						and cast(ct.NAM AS varchar(4))+  '-' +case when THANG<10 then '0'+cast(ct.THANG AS varchar(2)) else cast(ct.THANG AS varchar(2)) end <='"+fromDate+"'       \n       "+
				"				group by ct.nhapp_fk ,ct.dvkd_fk,ct.kenh_fk,ct.songaylamviec, b.nhomsanpham_fk,CT.thang,ct.nam  \n       "+
				"			) as ct       \n       "+
				"		)as ChiTieu left join        \n       "+
				"		(	       \n       "+
				"			select dh.PK_SEQ as DonHangId,sp.dvkd_fk,dh.kbh_fk,dh.NPP_FK ,sum(dh_sp.SOLUONG*sp.TRONGLUONG) as sanluong,substring(dh.NGAYNHAP,1,7)as ThoiGian       \n       "+
				"			from donhang dh inner join donhang_sanpham dh_sp on dh.pk_seq=dh_sp.donhang_fk inner join sanpham sp on sp.pk_seq=dh_sp.sanpham_fk         \n       "+
				"			where  dh.trangthai='1' and substring(dh.NGAYNHAP,1,7 )>= '"+fromDate+"'  and substring(dh.NGAYNHAP,1,7 )<= '"+fromDate+"'    \n       "+
				"				and dh.PK_SEQ not in( select DONHANG_FK from DONHANGTRAVE where TRANGTHAI=3 and DONHANG_FK is not null )        \n       "+
				"			group by dh.PK_SEQ,sp.dvkd_fk,dh.kbh_fk,gsbh_fk,substring(dh.NGAYNHAP,1,7) ,dh.NPP_FK      \n       "+
				"		)ThucDat on ChiTieu.kenh_fk=ThucDat.kbh_fk and ChiTieu.dvkd_fk=ThucDat.dvkd_fk and ChiTieu.npp_fk=ThucDat.NPP_FK   and ThucDat.ThoiGian=ChiTieu.ThoiGian    and isnull(ThucDat.sanluong,0)>=ChiTieu.ctSanLuongDonHang     \n       "+
				"	group by  ChiTieu.KENH_FK,ChiTieu.DVKD_FK,ChiTieu.NPP_FK,ChiTieu.ThoiGian       \n       "+
				")as SanLuongDonHang on SanLuongDonHang.DVKD_FK=ctNhom.DVKD_FK and SanLuongDonHang.Npp_fk=ctNhom.Npp_fk and SanLuongDonHang.KENH_FK=ctNhom.KENH_FK "+ 
				" where  npp.pk_seq= " +obj.getnppId();
			if(obj.getkenhId().length() > 0)
				query += " and kbh.pk_seq='"+obj.getkenhId()+"'";
			
			if(obj.getdvkdId().length() > 0)
				query += " and dvkd.pk_seq = '"+obj.getdvkdId()+"'";
		return query;
	}

	
	private void CreatePivotTable(OutputStream out,IStockintransit obj) throws Exception
    {   
		try{
			
			//String chuoi=getServletContext().getInitParameter("path") + "\\ThucHienChiTieuNPPTT.xlsm";
		
			//FileInputStream fstream = new FileInputStream(chuoi);
			File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\ThucHienChiTieuNPPTT.xlsm");
			FileInputStream fstream = new FileInputStream(f) ;
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			
			CreateStaticHeader(workbook,obj);
			
			// FillData_2 de tao du lieu cho nhung SS duoc luan chuyen trong thang
			//int row = FillData_2(workbook, obj);
			int row = 2;
			// FillData lay du lieu cho nhung SS khong duoc luan chuyen
			FillData(workbook, obj, row);
			
			workbook.save(out);
			fstream.close();
	     }catch(Exception ex){
	    	 throw new Exception(ex.getMessage());
	     }	    
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
		cell.setValue("TÌNH HÌNH THỰC HIỆN CHỈ TIÊU NHÀ PHÂN PHỐI");

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
	    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Nam : " + obj.getYear() + "" );
		
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A4");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A5");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi nhà phân phối :  " + obj.getuserTen());
	   
	
	    cell = cells.getCell(htb.get(1)+"1"); cell.setValue("KenhBanHang");
	    cell = cells.getCell(htb.get(2)+"1"); cell.setValue("DonViKinhDoanh");
	    cell = cells.getCell(htb.get(3)+"1"); cell.setValue("ChiNhanh");
	    cell = cells.getCell(htb.get(4)+"1"); cell.setValue("KhuVuc");
	    cell = cells.getCell(htb.get(5)+"1"); cell.setValue("MANPP");	
	    cell = cells.getCell(htb.get(6)+"1");cell.setValue("TENNPP");  	    
	       	    
	    cell = cells.getCell(htb.get(7)+"1"); cell.setValue("NgayLamViec");
	   
	    cell = cells.getCell(htb.get(8)+"1"); cell.setValue("ChiTieuSoDH");	    
	    cell = cells.getCell(htb.get(9)+"1"); cell.setValue("ThucDatSoDH");
	    cell = cells.getCell(htb.get(10)+"1"); cell.setValue("PhanTramDonHang");	
	    cell = cells.getCell(htb.get(11)+"1"); cell.setValue("CtSanLuongDonHang");
	    cell = cells.getCell(htb.get(12)+"1"); cell.setValue("SoDonHangDatCtSanLuong");
	    cell = cells.getCell(htb.get(13)+"1"); cell.setValue("%SoDonHangDatCtSanLuong");
	  
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
				 int i=14;
				ResultSet rs=db.get(sql);
			
				if(rs!=null){
				
				 try {
					while (rs.next()){
						 
						 cell = cells.getCell(htb.get(i)+"1"); cell.setValue("ChiTieuSec-"+rs.getString("ten"));	
						 i=i+1;
						 cell = cells.getCell(htb.get(i)+"1"); cell.setValue("ThucDatSec-"+rs.getString("ten"));
						 i=i+1;
						 cell = cells.getCell(htb.get(i)+"1"); cell.setValue("PhanTramSec-"+rs.getString("ten"));
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
				 i=i+1;
				 //Phan Chi Tieu Pri
				 sql="select distinct nhomsp_fk as nhomsanpham_fk ,dbo.ftBoDau(nsp.ten) as ten  from  chitieu_nhapp_nhomsp "+  
					" inner join chitieu b on b.pk_Seq=chitieu_fk  "+
					" inner join nhomsanpham nsp on nsp.pk_seq=nhomsp_fk "+
					" where b.thang="+obj.getMonth()+" and b.nam="+ obj.getYear() ;
		 
				 if(obj.getdvkdId().length()>0){
					 sql=sql+ " and b.dvkd_fk= "+ obj.getdvkdId();
					 
				 }
				 if(obj.getkenhId().length()>0){
					 sql=sql+ " and b.kenh_fk= "+ obj.getkenhId();
					 
				 }
				 System.out.println("Thong Tin : "+sql);
		 
				  rs=db.get(sql);
					
					if(rs!=null){
					
					 try {
						while (rs.next()){
							 
							 cell = cells.getCell(htb.get(i)+"1"); cell.setValue("ChiTieuPri-"+rs.getString("ten"));	
							 i=i+1;
							 cell = cells.getCell(htb.get(i)+"1"); cell.setValue("ThucDatPri-"+rs.getString("ten"));
							 i=i+1;
							 cell = cells.getCell(htb.get(i)+"1"); cell.setValue("PhanTramPri-"+rs.getString("ten"));
							 i=i+1;
						 }
						rs.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
					
					 cell = cells.getCell(htb.get(i)+"1"); cell.setValue("ChiTieuPri");	
					 i=i+1;
					 cell = cells.getCell(htb.get(i)+"1"); cell.setValue("ThucDatPri");
					 i=i+1;
					 cell = cells.getCell(htb.get(i)+"1"); cell.setValue("PhanTramPri");
					 
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

	private void FillData(Workbook workbook,IStockintransit obj, int row)throws Exception 
	{
		Hashtable<Integer, String> htb=this.htbValueCell();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
				
		dbutils db = new dbutils();	
		String query = setQuery( obj);
		
		System.out.println("1.Tieu Chi Thuong GSBH: " + query);
		db.update("SET DATEFORMAT ymd");
		ResultSet rs = db.get(query);	
		
		int indexRow = row;
		try 
			{				
				Cell cell = null;
				if(rs != null){
				while(rs.next())
				{ 				
					
					    cell = cells.getCell(htb.get(1) + Integer.toString(indexRow)); cell.setValue(rs.getString("KENH"));
					    cell = cells.getCell(htb.get(2) + Integer.toString(indexRow)); cell.setValue(rs.getString("DONVIKINHDOANH"));
						cell = cells.getCell(htb.get(3) + Integer.toString(indexRow)); cell.setValue(rs.getString("VUNG"));
						cell = cells.getCell(htb.get(4) + Integer.toString(indexRow)); cell.setValue(rs.getString("KHUVUC"));					
						cell = cells.getCell(htb.get(5) + Integer.toString(indexRow));cell.setValue(rs.getString("MANPP"));				
						cell = cells.getCell(htb.get(6) + Integer.toString(indexRow));  cell.setValue(rs.getString("TENNPP"));					
						cell = cells.getCell(htb.get(7)+ Integer.toString(indexRow)); cell.setValue(rs.getFloat("SONGAYLAMVIEC"));
						
						cell = cells.getCell(htb.get(8)+ Integer.toString(indexRow)); cell.setValue(rs.getFloat("DONHANG")*rs.getFloat("SONGAYLAMVIEC"));
						cell = cells.getCell(htb.get(9)+ Integer.toString(indexRow)); cell.setValue(rs.getFloat("DATDONHANG"));
						float PhanTramDonhang=0;
						if(rs.getFloat("DONHANG") >0){
							PhanTramDonhang=	100* rs.getFloat("DATDONHANG")/(rs.getFloat("DONHANG")*rs.getFloat("SONGAYLAMVIEC"));
						}
						cell = cells.getCell(htb.get(10)+ Integer.toString(indexRow)); cell.setValue(PhanTramDonhang);
						cell = cells.getCell(htb.get(11)+ Integer.toString(indexRow)); cell.setValue(rs.getFloat("CtSanLuongDonHang"));
						cell = cells.getCell(htb.get(12)+ Integer.toString(indexRow)); cell.setValue(rs.getFloat("SoDonHangDatCtSanLuong"));
						
						float PhanTramSanLuongDH=0;
						if(rs.getFloat("DONHANG")*rs.getFloat("SONGAYLAMVIEC") >0)
						{
							PhanTramSanLuongDH=	100* rs.getFloat("SoDonHangDatCtSanLuong")/(rs.getFloat("DONHANG")*rs.getFloat("SONGAYLAMVIEC"));
						}
						cell = cells.getCell(htb.get(13)+ Integer.toString(indexRow)); cell.setValue(PhanTramSanLuongDH);
						String []chuoi =obj.getFieldShow();
						String []chuoiPri =obj.getFieldHidden();
						int j=14;
						float SumChiTieuDDKD=0;
						float SumThucDatCTDDKD=0;
						for (int i=0;i<chuoi.length ;i++){
							if(chuoi[i]!=null){
								cell = cells.getCell(htb.get(j) + Integer.toString(indexRow));  cell.setValue(rs.getFloat("CT"+chuoi[i]));					
								SumChiTieuDDKD= SumChiTieuDDKD+rs.getFloat("CT"+chuoi[i]);
								SumThucDatCTDDKD= SumThucDatCTDDKD+rs.getFloat("DS"+chuoi[i]);
								j=j+1;
								cell = cells.getCell(htb.get(j)+ Integer.toString(indexRow)); cell.setValue(rs.getFloat("DS"+chuoi[i]));
								j=j+1;
								float phantramMTD =0;
								
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
						// chi tieu PRI
						
						
						float SumChiTieuPri=0;
						float SumThucDatPri=0;
						for (int i=0;i<chuoiPri.length ;i++){
							if(chuoiPri[i]!=null){
								cell = cells.getCell(htb.get(j) + Integer.toString(indexRow));  cell.setValue(rs.getFloat("CTPRI"+chuoiPri[i]));					
								SumChiTieuPri= SumChiTieuPri+rs.getFloat("CTPRI"+chuoiPri[i]);
								SumThucDatPri= SumThucDatPri+rs.getFloat("DSPRI"+chuoiPri[i]);
								j=j+1;
								cell = cells.getCell(htb.get(j)+ Integer.toString(indexRow)); cell.setValue(rs.getFloat("DSPRI"+chuoiPri[i]));
								j=j+1;
								float phantramPri =0;
								
								if(rs.getFloat("CTPRI"+chuoiPri[i]) >0){
									phantramPri=rs.getFloat("DSPRI"+chuoiPri[i])*100/rs.getFloat("CTPRI"+chuoiPri[i]);
								}
								cell = cells.getCell(htb.get(j)+ Integer.toString(indexRow)); cell.setValue(phantramPri);
								j=j+1;
							}
						}
						
						cell = cells.getCell(htb.get(j) + Integer.toString(indexRow));  cell.setValue(SumChiTieuPri);					
						
						j=j+1;
						cell = cells.getCell(htb.get(j)+ Integer.toString(indexRow)); cell.setValue(SumThucDatPri);
						j=j+1;
						float SumphantramPri =0;
						
						if(SumChiTieuPri >0){
							SumphantramPri=SumThucDatPri*100/SumChiTieuPri;
						}
						cell = cells.getCell(htb.get(j)+ Integer.toString(indexRow)); cell.setValue(SumphantramPri);

						
						
						indexRow++;
					
				}
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
	
}
