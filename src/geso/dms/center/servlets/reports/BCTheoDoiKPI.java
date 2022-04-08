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

public class BCTheoDoiKPI extends HttpServlet {
		
	private static final long serialVersionUID = 1L;

	public BCTheoDoiKPI() {
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
		
	
		obj.init();
		
		String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
		if(view == null)
			view = "";
		
		obj.setLoaiMenu(view);
		
		Utility ut = new Utility();
		
		geso.dms.distributor.db.sql.dbutils db = new geso.dms.distributor.db.sql.dbutils();
		ut.getUserInfo((String) session.getAttribute("userId"), db);
		db.shutDown();
		if(ut.getLoaiNv().equals("2"))
			obj.setLoaiMenu("NPP");
		session.setAttribute("obj", obj);
		String nextJSP = "/AHF/pages/Center/BCTheoDoiKPI.jsp";
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
			view = "";
		
		obj.setLoaiMenu(view);
		
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		
		obj.setuserId(userId!=null? userId:"");
		obj.setuserTen(userTen!=null? userTen:"");
		Utility util = new Utility();
		
		obj.settungay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays"))));
		obj.setdenngay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays"))));
		
		
		
		String[] vungid = request.getParameterValues("vungId");
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
		String[] khuvucId = request.getParameterValues("khuvucId");
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
		String[] nppId = request.getParameterValues("nppId");
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
		
		Utility ut = new Utility();
		geso.dms.distributor.db.sql.dbutils db = new geso.dms.distributor.db.sql.dbutils();
		ut.getUserInfo(userId, db);
		db.shutDown();
		if(ut.getLoaiNv().equals("2"))
			obj.setLoaiMenu("NPP");
		
		if(s.length() <= 0)
		{
			s = " select pk_seq from nhaphanphoi where 1 = 1 ";
			
			s+= " and pk_seq in "+util.quyen_npp(userId)+" ";
		
			if(obj.getkhuvucId().length() > 0)
			{
				s+= " and khuvuc_fk in ("+obj.getkhuvucId()+") ";
			}
			
			if(view.equals("NPP"))
			{
				
				if(ut.getLoaiNv().equals("3"))
					s+= " and pk_seq in "+util.quyen_npp(userId)+" ";
				else
				{
					String nppid = ut.getIdNhapp(userId);
					s+= " and pk_seq in ("+nppid+") ";
				}
			
			}
			
			
			if(obj.getvungId().length() > 0)
			{
				s+= " and khuvuc_fk in (select pk_seq from khuvuc where vung_fk in ("+obj.getvungId()+") )";
			}
		}
		
		obj.setnppId(s);
		String action = (String) util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")));
		String nextJSP = "/AHF/pages/Center/BCTheoDoiKPI.jsp";
		
		System.out.println("Action la: " + action);
		
		try{
			if (action.equals("Taomoi")) 
			{			
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BCTDKPI.xlsm");
				Workbook workbook = new Workbook();
				TaoBaoCao(workbook, userTen,obj.gettungay(),obj.getdenngay(),obj.getnppId());
				
				workbook.save(out);	
			}
			else
			{
				obj.init();
			session.setAttribute("obj", obj);
				response.sendRedirect(nextJSP);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			obj.init();
			obj.setMsg("Lỗi không tạo được báo cáo ! Vui Lòng kiểm tra lại");
			response.sendRedirect(nextJSP);
		}
		
		session.setAttribute("obj", obj);	
	}	
	
	
	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	private void TaoBaoCao(Workbook workbook,String nguoitao,String tungay,String denngay,String nppid )throws Exception
	{
		try{
			
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("TDKPI");
			dbutils db = new dbutils();
			Cells cells = worksheet.getCells();
			cells.setRowHeight(0, 50.0f);
			Cell cell = cells.getCell("A1");
			ReportAPI.getCellStyle(cell, Color.RED, true, 16,"Báo Cáo Theo Dõi KPI");

			cell = cells.getCell("A3");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Ngày tạo : "+ getDateTime());
			
			cell = cells.getCell("A2");
			cells.setColumnWidth(0, 20.0f);
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Từ ngày: "+ tungay);
			cell = cells.getCell("B2");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Đến ngày: "+ denngay);
			cells.setColumnWidth(4, 20.0f);
			cells.setColumnWidth(9, 15.0f);
			cells.setColumnWidth(8, 15.0f);
			cells.setColumnWidth(7, 15.0f);
			cells.setColumnWidth(6, 15.0f);
			cell = cells.getCell("A4");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10,"Người tạo : " + nguoitao);
		
			
			
			String query =  
					" select '' as STT, v.TEN as Vung,kv.TEN as KhuVuc,a.ma as MaNPP,kbh.ten as KBH,case when ddkd.trangthai = 1 then N'Còn hoạt động' else N'Ngưng hoạt động' end as TrangthaiNVBH ,a.TEN as NPP,ddkd.SMARTID as MaNVBH,ddkd.ten as NVBH ,"
							 +"\n isnull((select SUM(soluong*giamua) from DONHANG dh inner join "
							 +"\n  DONHANG_SANPHAM dhsp on dh.PK_SEQ = dhsp.DONHANG_FK where dh.TRANGTHAI <> 2 "
							 +"\n  and dh.NGAYNHAP >= '"+tungay+"' and dh.NGAYNHAP <= '"+denngay+"' and dh.ddkd_fk = ddkd.pk_seq "
							 +"\n  ),0)  as SELLOUT,"
							 +"\n dbo.PhepChia "
							 +"\n ("
							 +"\n (select COUNT( dh.khachhang_fk) from ddkd_khachhang dh inner join DAIDIENKINHDOANH dd on ddkd.PK_SEQ= dh.ddkd_fk"
							 +"\n  where  CONVERT(varchar(10),dh.thoidiem,120) >= '"+tungay+"' and CONVERT(varchar(10),dh.thoidiem,120) <= '"+denngay+"' and dd.pk_seq = ddkd.pk_seq "
							 +"\n ),"
							 +"\n ( select SOKH_PHAIVT FROM DDKD_SOKH a where a.DDKD_FK = ddkd.pk_seq  "
							 +"\n	and NGAY = (SELECT MAX(NGAY) FROM DDKD_SOKH z where z.DDKD_FK = a.DDKD_FK and z.NGAY <= '"+denngay+"' AND SOKH_PHAIVT IS NOT NULL)"
							 +"\n ) )*100  "
							 + "   as TYLEViengThamTC,"
							 +"\n "
							 +"\n dbo.PhepChia"
							 +"\n ("
							 +"\n (select COUNT(distinct dh.pk_seq) from DONHANG dh  "
							 +"\n  where  dh.NGAYNHAP >= '"+tungay+"' and dh.NGAYNHAP <= '"+denngay+"' and dh.DDKD_FK = ddkd.pk_seq and ispda = '1' "
							 +"\n ),"
							 +"\n (select COUNT( dh.khachhang_fk) from ddkd_khachhang dh inner join DAIDIENKINHDOANH dd on dd.PK_SEQ= dh.ddkd_fk "
							 +"\n  where  CONVERT(varchar(10),dh.thoidiem,120) >= '"+tungay+"' and CONVERT(varchar(10),dh.thoidiem,120) <= '"+denngay+"' and dd.pk_seq = ddkd.pk_seq "
							 +"\n ) )*100 as TyleBanHangTC,"
							 +"\n dbo.PhepChia"
							 +"\n   ( "
							 +"\n   (select SUM(soluong*giamua)/1.1 from DONHANG dh inner join "
							 +"\n  DONHANG_SANPHAM dhsp on dh.PK_SEQ = dhsp.DONHANG_FK where dh.TRANGTHAI <> 2 "
							 +"\n  and dh.NGAYNHAP >= '"+tungay+"' and dh.NGAYNHAP <= '"+denngay+"' and dh.DDKD_FK = ddkd.pk_seq "
							 +"\n  ),"
							 +"\n   (select COUNT(dh.pk_seq) from DONHANG dh"
							 +"\n  where dh.TRANGTHAI <> 2  "
							 +"\n  and dh.NGAYNHAP >= '"+tungay+"' and dh.NGAYNHAP <= '"+denngay+"' and dh.DDKD_FK = ddkd.pk_seq "
							 + " )"
							 +"\n   "
							 +"\n  "
							 +"\n  "
							 +"\n  )  as DROPSIZE, " 
							 +"\n (select COUNT(AC.FILENAME)from "   
							 +"\n  KHACHHANG_ANHCHUP AC "       
							 +"\n 	where ac.NGAY >= '"+tungay+"' and ac.NGAY <= '"+denngay+"' AND ac.ddkd_fk = ddkd.pk_seq ) as SoAnhChup, "
							 +"\n	 dbo.PhepChia " 
							 +"\n 	( " 
							 +"\n	(select COUNT(AC.FILENAME)from "
							 +"\n	KHACHHANG_ANHCHUP AC "
							 +"\n	where ac.NGAY >= '"+tungay+"' and ac.NGAY <= '"+denngay+"' AND ac.ddkd_fk = ddkd.pk_seq), " 
							 +"\n ( select SOKH_PHAIVT FROM DDKD_SOKH a where a.DDKD_FK = ddkd.pk_seq  "
							 +"\n	and NGAY = (SELECT MAX(NGAY) FROM DDKD_SOKH z where z.DDKD_FK = a.DDKD_FK and z.NGAY <= '"+denngay+"' AND SOKH_PHAIVT IS NOT NULL)"
							 +"\n ))*100 as PTHinhChup,  " +
							  "\n (select SOKH_PHAIVT  "
							 +"\n		from DDKD_SOKH WHERE NGAY>= '"+tungay+"' and NGAY <= '"+denngay+"' AND DDKD_FK = ddkd.pk_seq  "
							 +"\n			AND NGAY = (SELECT MAX(NGAY) FROM DDKD_SOKH WHERE NGAY>= '"+tungay+"' and NGAY <= '"+denngay+"' " 
							 +"\n AND DDKD_FK = ddkd.pk_seq AND SOKH_PHAIVT IS NOT NULL)) as TongSoCallASO, "	
							 +"\n (select COUNT( dh.khachhang_fk) from ddkd_khachhang dh inner join DAIDIENKINHDOANH dd on ddkd.PK_SEQ= dh.ddkd_fk"
							 +"\n  where  CONVERT(varchar(10),dh.thoidiem,120) >= '"+tungay+"' and CONVERT(varchar(10),dh.thoidiem,120) <= '"+denngay+"' and dd.pk_seq = ddkd.pk_seq "
							 +"\n ) as TongSoViengTham"
							 +"\n from NHAPHANPHOI a " +
							 "\n left join NHAPP_KBH nk on nk.npp_fk = a.pk_seq " 
							 +"\n left join KHUVUC kv on a.KHUVUC_FK = kv.PK_SEQ"
							 +"\n left join VUNG v on v.PK_SEQ = kv.VUNG_FK "
							 + "\n left join DaiDienKinhDoanh	ddkd on ddkd.npp_fk = a.pk_seq " +
							 "\n left join ddkd_gsbh oki on oki.ddkd_fk = ddkd.pk_seq " +
							 "\n left join giamsatbanhang gsbh on gsbh.pk_seq = oki.gsbh_fk " +
							 "\n left join kenhbanhang kbh on kbh.pk_seq = a.kbh_fk " 
							 +"\n where 1= 1  and   a.PK_SEQ in ("+nppid+") and a.trangthai = 1 " ;				
							
				
					 		
					 
				
			System.out.println("Get NVBH ko PDA: "+query);
			ResultSet rs = db.get(query);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int socottrongSql = rsmd.getColumnCount();
			
			int countRow = 10;

			for( int i =1 ; i <=socottrongSql ; i ++  )
			{
				cell = cells.getCell(countRow,i-1 );cell.setValue(rsmd.getColumnName(i));
				cells.setColumnWidth(i, 20.0f);
				ReportAPI.setCellBackground(cell, new Color(70,130,180), BorderLineType.THIN, true, 0);	
			 
			}
			countRow ++;
			
			int stt = 0;
			while(rs.next())
			{
				
				stt++;
			
				for(int i =1;i <=socottrongSql ; i ++)
				{
					cell = cells.getCell(countRow,i-1 );
					if(rsmd.getColumnName(i).equals("STT"))
					{					
						cell.setValue(stt);
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
						//System.out.println("STT: "+stt);

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
