package geso.dms.center.servlets.reports;

import geso.dms.center.beans.kehoachbanhang.imp.KehoachbanhangList;
import geso.dms.center.beans.kehoachnhanvien.imp.KeHoachNhanVienList;
import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.distributor.beans.report.Ireport;
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

public class BCTinhhinhDms extends HttpServlet {
		
	private static final long serialVersionUID = 1L;

	public BCTinhhinhDms() {
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
		ut.getUserInfo((String) session.getAttribute("userId"), obj.getDb());
		
		session.setAttribute("obj", obj);
		String nextJSP = "/AHF/pages/Center/BCTinhHinhDms.jsp";
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

		ut.getUserInfo(userId, obj.getDb());

		
		if(s.length() <= 0)
		{
			
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
		if(s.trim().length() >0)
			s=   " select pk_seq from nhaphanphoi where 1 = 1 " + s;
		System.out.println("nppId = "+ s);
		
		obj.setnppId(s);
		String action = (String) util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")));
		String nextJSP = "/AHF/pages/Center/BCTinhHinhDms.jsp";
		
		System.out.println("Action la: " + action);
		
		try{
			if (action.equals("Taomoi")) 
			{			
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BCDMS.xlsm");
				Workbook workbook = new Workbook();
				TaoBaoCao(workbook, userTen,obj.gettungay(),obj.getdenngay(),obj.getnppId());
				
				workbook.save(out);	
			}else if (action.equals("Taomoibc")) 
			{			
				/*response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BCDMS.xlsm");
				Workbook workbook = new Workbook();
				TaoBaoCaoBC(workbook, userTen,"2018-01-01","2018-01-15",obj.getnppId());
				
				workbook.save(out);	*/
				
				try 
				{
					request.setCharacterEncoding("utf-8");

					response.setContentType("application/xlsm");
					response.setHeader("Content-Disposition", "attachment; filename=BCChamCong.xlsm");

					//OutputStream out = response.getOutputStream();

					String query = setQuery_KhongPiVot(obj, view);

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
			else if (action.equals("chamcongql")) 
			{			
				
				try 
				{
					request.setCharacterEncoding("utf-8");

					response.setContentType("application/xlsm");
					response.setHeader("Content-Disposition", "attachment; filename=BCChamCong.xlsm");

					//OutputStream out = response.getOutputStream();

					String query = setQuery_KhongPiVotQl(obj, view);

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
			worksheet.setName("TheoDoiDMS");

			Cells cells = worksheet.getCells();
			cells.setRowHeight(0, 50.0f);
			Cell cell = cells.getCell("A1");
			ReportAPI.getCellStyle(cell, Color.RED, true, 16,"Báo Cáo Tình Hình DMS");

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
			int sonvkdpda = 0;
			
			String so = "";
			int tongso = 0;
			worksheet.setGridlinesVisible(false);
			dbutils db = new dbutils();
			String query = "select COUNT(distinct pk_seq) as so from daidienkinhdoanh a  where a.trangthai = 1 and isnull(a.ismt,'0')  = '0' and   npp_fk in ("+nppid+")   ";
			System.out.println("Loi: "+query);
			ResultSet rs = db.get(query);
			if(rs.next())
			{
				so	= rs.getString("so");
				tongso = rs.getInt("so");
				rs.close();
			}
			cell = cells.getCell("A6");
			ReportAPI.getCellStyle(cell, Color.BLACK, true, 10,"1.Tình hình sử dụng PDA của "+so+" NVBH: ");
			
			query = "select "+tongso+" - COUNT(distinct ddkd_fk) as so from  ddkd_khachhang where CONVERT(varchar(10), thoidiem, 121) <= '"+denngay+"' and CONVERT(varchar(10), thoidiem, 121) >= '"+tungay+"' and datepart(DW ,thoidiem) <> 1 and exists (select 1 from daidienkinhdoanh ddkd where ddkd.trangthai = 1 and ddkd.pk_seq = ddkd_fk and isnull(ddkd.ismt,'0')  = 0  and ddkd.npp_fk in ("+nppid+")  )";
			System.out.println("GetNVKOVT: "+query);
			 rs = db.get(query);
			if(rs.next())
			{
				so	= rs.getString("so");
				sonvkdpda = Integer.parseInt(so);
				rs.close();
			}
			cell = cells.getCell("A7");
			ReportAPI.getCellStyle(cell, Color.BLACK, true, 10,"- Có "+so+" NVBH không viếng thăm khách hàng từ ngày:"+tungay+" đến ngày: "+denngay);
			
			
			query = "select "+tongso+" - COUNT(distinct a.ddkd_fk) as so from  donhang a inner join DANGNHAP_DDKD b on a.DDKD_FK = b.DDKD_FK  where a.NGAYNHAP <= '"+denngay+"' and a.NGAYNHAP >= '"+tungay+"' and trangthai <> 2 and exists (select 1 from daidienkinhdoanh ddkd where ddkd.trangthai = 1 and isnull(ddkd.ismt,'0')  = 0 and ddkd.pk_seq = a.ddkd_fk  and ddkd.npp_fk in ("+nppid+") )";
			System.out.println("NVBH khong co DH: "+query);
			 rs = db.get(query);
			if(rs.next())
			{
				so	= rs.getString("so");
				if(Integer.parseInt(so) < sonvkdpda)
				{
					sonvkdpda = Integer.parseInt(so);
				}
				rs.close();
			}
			cell = cells.getCell("A8");
			ReportAPI.getCellStyle(cell, Color.BLACK, true, 10,"- Có "+so+" NVBH không có đơn hàng từ ngày:"+tungay+" đến ngày: "+denngay);
			
			sonvkdpda =0;
			
			query="IF OBJECT_ID('tempdb.dbo.#GetNgayKhongVT') IS NOT NULL "
					+"\n DROP TABLE #GetNgayKhongVT "
					+"\n CREATE TABLE #GetNgayKhongVT ("
					+"\n ddkd_fk numeric(18,0),  "
					+"\n ngay varchar(100) )";

			db.update(query);
			System.out.println("QR1: "+query);
				query= " insert into #GetNgayKhongVT "
					+ "\n select  ddkd.PK_SEQ as DDKD_FK, [dbo].[GetNgayKhongVT](ddkd.pk_seq,'"+denngay+"') "+
					"\n  from DAIDIENKINHDOANH ddkd ";
				db.update(query);
				System.out.println("QR1: "+query);
				
				query="IF OBJECT_ID('tempdb.dbo.#GetNgayKhongDH') IS NOT NULL "
						+"\n DROP TABLE #GetNgayKhongDH "
						+"\n CREATE TABLE #GetNgayKhongDH ("
						+"\n ddkd_fk numeric(18,0),  "
						+"\n ngay varchar(100) )";

				db.update(query);
				System.out.println("QR1: "+query);
				query= " insert into #GetNgayKhongDH "
						+ "\n select  ddkd.PK_SEQ as DDKD_FK,[dbo].[GetNgayKhongDH](ddkd.pk_seq,'"+denngay+"') "+
						"\n  from DAIDIENKINHDOANH ddkd ";
				db.update(query);
				System.out.println("QR1: "+query);
				
				query="IF OBJECT_ID('tempdb.dbo.#GetNgayKhongSDTABLET') IS NOT NULL "
						+"\n DROP TABLE #GetNgayKhongSDTABLET "
						+"\n CREATE TABLE #GetNgayKhongSDTABLET ("
						+"\n ddkd_fk numeric(18,0),  "
						+"\n ngay varchar(100) )";

				db.update(query);
				System.out.println("QR1: "+query);
				
				
				query= " insert into #GetNgayKhongSDTABLET "
						+ "\n select  ddkd.PK_SEQ as DDKD_FK,[dbo].[GetNgayKhongSDTABLET](ddkd.pk_seq,'"+denngay+"') "+
						"\n  from DAIDIENKINHDOANH ddkd ";
				db.update(query);
				System.out.println("QR1: "+query);
				
				query="IF OBJECT_ID('tempdb.dbo.#GetNgay3DH') IS NOT NULL "
					+"\n DROP TABLE #GetNgay3DH "
					+"\n CREATE TABLE #GetNgay3DH ("
					+"\n ddkd_fk numeric(18,0),  "
					+"\n ngay varchar(1000) )";

				db.update(query);
				System.out.println("QR1: "+query);
			
			
				query= " insert into #GetNgay3DH "
					+ "\n select  ddkd.PK_SEQ as DDKD_FK,[dbo].[GetNgay3DH](ddkd.pk_seq,'"+tungay+"','"+denngay+"') "+
					"\n  from DAIDIENKINHDOANH ddkd ";
				db.update(query);
				System.out.println("QR1: "+query);
				
			query = "select distinct ddkd.pk_seq,'' as [STT], isnull((select top(1) ten from BM a inner join BM_CHINHANH b on a.PK_SEQ = b.BM_FK and a.TRANGTHAI = 1 and b.VUNG_FK = (select top(1)VUNG_FK from KHUVUC where PK_SEQ = GS.khuvuc_fk)),'') as [RSM], isnull((select top(1) ten from ASM a inner join ASM_KHUVUC b on a.PK_SEQ = b.ASM_FK and a.TRANGTHAI = 1 and b.KHUVUC_FK = gs.khuvuc_fk),'') as [ASM],  gs.TEN as [Giám Sát],npp.TEN as [NPP trực thuộc],kv.TEN as [Khu Vực],ddkd.smartid as [Mã NV],ddkd.TEN as [Nhân Viên], case when ddkd.trangthai = 1 then N'Hoạt Động' else N'Không Hoạt Động' end [Trạng Thái], "
					 +"\n  (select top 1 ngay from  #GetNgayKhongVT where ddkd_fk = ddkd.pk_seq) as [Không Viếng Thăm],(select top 1 ngay from  #GetNgayKhongDH where ddkd_fk = ddkd.pk_seq) as [Không Có Đơn Hàng],(select top 1 ngay from  #GetNgay3DH where ddkd_fk = ddkd.pk_seq) as [Ngày Công],(select top 1 ngay from  #GetNgayKhongSDTABLET where ddkd_fk = ddkd.pk_seq) as [Không sử dụng Tablet]"
					 +"\n   from DAIDIENKINHDOANH ddkd left join NHAPHANPHOI npp on npp.PK_SEQ = ddkd.NPP_FK "
					 +"\n  left join  ddkd_gsbh dd on dd.ddkd_fk = ddkd.PK_SEQ "
					 +"\n  left join  nhapp_giamsatbh a on a.NPP_FK = npp.PK_SEQ and a.GSBH_FK = dd.GSBH_FK"				
					 +"\n  left join GIAMSATBANHANG gs on gs.PK_SEQ = a.GSBH_FK  "			
					 +"\n  inner join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK"
					 +"\n  where 1 = 1 "
					 +"\n  and   npp.PK_SEQ in ("+nppid+")  and gs.trangthai = 1 and isnull(ddkd.ismt,'0')  = 0 "
					 //+ "\n and (len((select top 1 ngay from  #GetNgayKhongVT where ddkd_fk = ddkd.pk_seq)) > 0 or LEN((select top 1 ngay from  #GetNgayKhongDH where ddkd_fk = ddkd.pk_seq)) > 0) "
					 +"\n union "
					 +"\n select distinct ddkd.pk_seq,'' as [STT], isnull((select top(1) ten from BM a inner join BM_CHINHANH b on a.PK_SEQ = b.BM_FK and a.TRANGTHAI = 1 and b.VUNG_FK = (select top(1)VUNG_FK from KHUVUC where PK_SEQ = GS.khuvuc_fk)),'') as [RSM], isnull((select top(1) ten from ASM a inner join ASM_KHUVUC b on a.PK_SEQ = b.ASM_FK and a.TRANGTHAI = 1 and b.KHUVUC_FK = gs.khuvuc_fk),'') as [ASM], '' as [Giám Sát],npp.TEN as [NPP trực thuộc],kv.TEN as [Khu Vực],ddkd.smartid as [Mã NV],ddkd.TEN as [Nhân Viên], case when ddkd.trangthai = 1 then N'Hoạt Động' else N'Không Hoạt Động' end [Trạng Thái], "
					 +"\n  (select top 1 ngay from  #GetNgayKhongVT where ddkd_fk = ddkd.pk_seq) as [Không Viếng Thăm],(select top 1 ngay from  #GetNgayKhongDH where ddkd_fk = ddkd.pk_seq) as [Không Có Đơn Hàng],(select top 1 ngay from  #GetNgay3DH where ddkd_fk = ddkd.pk_seq) as [Ngày Công],(select top 1 ngay from  #GetNgayKhongSDTABLET where ddkd_fk = ddkd.pk_seq) as [Không sử dụng Tablet]"
					 +"\n   from DAIDIENKINHDOANH ddkd left join NHAPHANPHOI npp on npp.PK_SEQ = ddkd.NPP_FK "
					 +"\n  left join  ddkd_gsbh dd on dd.ddkd_fk = ddkd.PK_SEQ "
					 +"\n  left join  nhapp_giamsatbh a on a.NPP_FK = npp.PK_SEQ and a.GSBH_FK = dd.GSBH_FK"
					 +"\n  left join GIAMSATBANHANG gs on gs.PK_SEQ = a.GSBH_FK  "				
					 +"\n  inner join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK"
					 +"\n  where 1 = 1 "
					 //+ "\n and (len((select top 1 ngay from  #GetNgayKhongVT where ddkd_fk = ddkd.pk_seq)) > 0 or LEN((select top 1 ngay from  #GetNgayKhongDH where ddkd_fk = ddkd.pk_seq)) > 0) "
					 +"\n  and   npp.PK_SEQ in ("+nppid+")  and isnull(ddkd.ismt,'0')  = 0"
					 +"\n  and not exists "
					 +"\n ( select top(1) 1 "
					 +"\n   from DAIDIENKINHDOANH ddkd1 left join NHAPHANPHOI npp on npp.PK_SEQ = ddkd1.NPP_FK "
					 +"\n  left join  nhapp_giamsatbh a on a.NPP_FK = npp.PK_SEQ and a.GSBH_FK = dd.GSBH_FK"
					 +"\n  left join GIAMSATBANHANG gs on gs.PK_SEQ = a.GSBH_FK  "				
					 +"\n  inner join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK"
					 +"\n  where 1 = 1 "
					 +"\n  and   npp.PK_SEQ in ("+nppid+")  and gs.trangthai = 1  and ddkd1.PK_SEQ = ddkd.PK_SEQ and isnull(ddkd1.ismt,'0')  = 0"
					  //+ "\n and (len([dbo].[GetNgayKhongVT](ddkd1.pk_seq,'"+denngay+"')) > 0 or LEN([dbo].[GetNgayKhongDH](ddkd1.pk_seq,'"+denngay+"')) > 0)"
					 		+ ")"				 
					 
					 + "\n order by kv.ten,npp.ten ";
			System.out.println("Get NVBH ko PDA: "+query);
			 rs = db.get(query);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int socottrongSql = rsmd.getColumnCount();
			
			int countRow = 10;

			for( int i =2 ; i <=socottrongSql ; i ++  )
			{
				cell = cells.getCell(countRow,i-2 );cell.setValue(rsmd.getColumnName(i));
				cells.setColumnWidth(i, 20.0f);
				ReportAPI.setCellBackground(cell, new Color(70,130,180), BorderLineType.THIN, true, 0);	
			 
			}
			countRow ++;
			
			int stt = 0;
			while(rs.next())
			{
				boolean kt = false;
				boolean ck = true;
				stt++;
				String value = "";
				for(int i =2;i <=socottrongSql ; i ++)
				{
					cell = cells.getCell(countRow,i-2 );
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
							if(rsmd.getColumnName(i).equals("Không sử dụng Tablet"))
								if(rs.getString(i-1).equals(rs.getString(i-2)))
									sonvkdpda++;
							cell.setValue(rs.getString(i));
							
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					}
				}
				++countRow;
			}
			
			
			

			query = "select COUNT( pk_seq) as so from daidienkinhdoanh where trangthai = 1 and   npp_fk in ("+nppid+")  and not exists  (select top 1 1 from DONHANG a where a.NGAYNHAP <= '"+denngay+"' and a.NGAYNHAP >= '"+tungay+"' and TRANGTHAI <> 2  and  npp_FK in ("+nppid+") and a.ddkd_fk = daidienkinhdoanh.pk_seq) and not exists"
					+ "\n (select top 1 1 from ddkd_khachhang a where CONVERT(varchar(10), a.thoidiem, 121) <= '"+denngay+"' and CONVERT(varchar(10), a.thoidiem, 121) >= '"+tungay+"'  and a.ddkd_fk = daidienkinhdoanh.pk_seq) ";
			System.out.println("NVKOPDA: "+query);
			 rs = db.get(query);
			if(rs.next())
			{
				sonvkdpda	= rs.getInt("so");
				rs.close();
			}
			cell = cells.getCell("A9");
			ReportAPI.getCellStyle(cell, Color.BLACK, true, 10,"- Có "+sonvkdpda+" NVBH không sử dụng PDA từ ngày:"+tungay+" đến ngày: "+denngay);
			
			
			query = "select (select COUNT(distinct pk_seq) from nhaphanphoi where trangthai = 1 and   PK_seq in ("+nppid+"))  - count(distinct NPP_FK) as so from DONHANG a where a.NGAYNHAP <= '"+denngay+"' and a.NGAYNHAP >= '"+tungay+"' and TRANGTHAI = 1  and  npp_FK in ("+nppid+")";
			 rs = db.get(query);
			if(rs.next())
			{
				so	= rs.getString("so");
				rs.close();
			}
		
			
			
			cell = cells.getCell("A"+(countRow+1));
			ReportAPI.getCellStyle(cell, Color.BLACK, true, 10,"2.Tiến độ xử lý đơn hàng tại NPP (từ ngày:"+tungay+" đến ngày:"+denngay+" ).Có "+so+" NPP chưa chốt đơn hàng.");
			
		
					
					
			query =   " declare @tungay varchar(10) = '"+tungay+"' "
			  +"\n declare @denngay varchar(10) = '"+denngay+"' "
			  +"\n select '' as [STT], gs.TEN as [Giám Sát],npp.TEN as [NPP trực thuộc],kv.TEN as [Khu Vực],"
			  +"\n (select COUNT(PK_SEQ) from DONHANG where NGAYNHAP >= @tungay and NGAYNHAP <= @denngay "
			  +"\n and NPP_FK = npp.PK_SEQ and trangthai <> 2  ) as [Tổng số đơn hàng],(select COUNT(PK_SEQ) from DONHANG where NGAYNHAP >= @tungay and NGAYNHAP <= @denngay "
			  +"\n and NPP_FK = npp.PK_SEQ and TRANGTHAI = 1 ) as [Tổng số đơn hàng đã chốt],"
			  +"\n round(dbo.phepchia((select COUNT(PK_SEQ) from DONHANG where NGAYNHAP >= @tungay and NGAYNHAP <= @denngay "
			  +"\n and NPP_FK = npp.PK_SEQ and TRANGTHAI = 1 ),(select COUNT(PK_SEQ) from DONHANG where NGAYNHAP >= @tungay and NGAYNHAP <= @denngay "
			  +"\n and NPP_FK = npp.PK_SEQ and trangthai <> 2 )),2 )*100 as [Tỉ lệ chốt đơn %]"
			  +"\n from NHAPHANPHOI npp left join nhapp_giamsatbh a on a.NPP_FK = npp.PK_SEQ "
			  +"\n left join GIAMSATBANHANG gs on gs.PK_SEQ = a.GSBH_FK   "
			  +"\n left join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK"
			  +"\n left join DONHANG dh on dh.NPP_FK = npp.PK_SEQ and dh.NGAYNHAP >= @tungay and dh.NGAYNHAP <= @denngay"
			  +"\n where   npp.pk_seq in ("+nppid+") and npp.trangthai = 1 and (gs.trangthai = 1 or gs.pk_seq is null) "
			  +"\n group by gs.TEN,npp.TEN,kv.TEN,npp.PK_SEQ "
			  +"\n union  "
			  +"\n select distinct '' as [STT], '' as [Giám Sát],npp.TEN as [NPP trực thuộc],kv.TEN as [Khu Vực],"
			  +"\n (select COUNT(PK_SEQ) from DONHANG where NGAYNHAP >= @tungay and NGAYNHAP <= @denngay "
			  +"\n and NPP_FK = npp.PK_SEQ and trangthai <> 2  ) as [Tổng số đơn hàng],(select COUNT(PK_SEQ) from DONHANG where NGAYNHAP >= @tungay and NGAYNHAP <= @denngay "
			  +"\n and NPP_FK = npp.PK_SEQ and TRANGTHAI = 1 ) as [Tổng số đơn hàng đã chốt],"
			  +"\n round(dbo.phepchia((select COUNT(PK_SEQ) from DONHANG where NGAYNHAP >= @tungay and NGAYNHAP <= @denngay "
			  +"\n and NPP_FK = npp.PK_SEQ and TRANGTHAI = 1 ),(select COUNT(PK_SEQ) from DONHANG where NGAYNHAP >= @tungay and NGAYNHAP <= @denngay "
			  +"\n and NPP_FK = npp.PK_SEQ and trangthai <> 2 )),2 )*100 as [Tỉ lệ chốt đơn %]"
			  +"\n from NHAPHANPHOI npp left join nhapp_giamsatbh a on a.NPP_FK = npp.PK_SEQ "
			  +"\n left join GIAMSATBANHANG gs on gs.PK_SEQ = a.GSBH_FK   "
			  +"\n left join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK"
			  +"\n left join DONHANG dh on dh.NPP_FK = npp.PK_SEQ and dh.NGAYNHAP >= @tungay and dh.NGAYNHAP <= @denngay"
			  +"\n where   npp.pk_seq in ("+nppid+") and npp.trangthai = 1  "
			  + "\n and npp.pk_seq not in ( "
			  +"\n select npp.pk_seq "
			  +"\n from NHAPHANPHOI npp left join nhapp_giamsatbh a on a.NPP_FK = npp.PK_SEQ "
			  +"\n left join GIAMSATBANHANG gs on gs.PK_SEQ = a.GSBH_FK   "
			  +"\n left join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK"
			  +"\n left join DONHANG dh on dh.NPP_FK = npp.PK_SEQ and dh.NGAYNHAP >= @tungay and dh.NGAYNHAP <= @denngay"
			  +"\n where   npp.pk_seq in ("+nppid+") and npp.trangthai = 1 and (gs.trangthai = 1 or gs.pk_seq is null) "
			  +"\n group by gs.TEN,npp.TEN,kv.TEN,npp.PK_SEQ )"
			  +"\n group by gs.TEN,npp.TEN,kv.TEN,npp.PK_SEQ "
			  + "\n  order by kv.ten,npp.ten ";
			System.out.println("Get ĐH NPP: "+query);
			rs = db.get(query);
			rsmd = rs.getMetaData();
			socottrongSql = rsmd.getColumnCount();
			countRow = countRow + 2 ;
			for( int i =1 ; i <=socottrongSql ; i ++  )
			{
				cells.setColumnWidth(i, 20.0f);
				cell = cells.getCell(countRow,i-1 );cell.setValue(rsmd.getColumnName(i));
				ReportAPI.setCellBackground(cell, new Color(70,130,180), BorderLineType.THIN, true, 0);	
			 
			}
			stt = 0;
			countRow++;
			while(rs.next())
			{
				boolean kt = false;
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
						
							if(i != socottrongSql)
								ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
							else
								ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					}
					else
					{
						cell.setValue(rs.getString(i));
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					}
				}
				++countRow;
			}
			
			
			
			query = "select COUNT(distinct a.PK_SEQ) as so from  NHAPHANPHOI a where a.TRANGTHAI = 1 and not exists (select 1 from erp_dondathang where NPP_FK = a.PK_SEQ and NPP_DACHOT = 1 and NgayDonHang <= '"+denngay+"' and NgayDonHang >= '"+tungay+"' )  and  pk_seq in ("+nppid+") ";
			System.out.println("So NPP dat Hang: "+query);
			 rs = db.get(query);
			if(rs.next())
			{
				so	= rs.getString("so");
				rs.close();
			}
			cell = cells.getCell("A"+(countRow+2));
			ReportAPI.getCellStyle(cell, Color.BLACK, true, 10,"3.NPP đặt hàng lên SGP:Có "+so+" NPP chưa đặt hàng từ ngày "+tungay+" đến ngày: "+denngay);
			
			
					query = "select '' as STT, a.Manpp as [Mã NPP], a.ten as [Tên NPP] from  NHAPHANPHOI a where a.TRANGTHAI = 1 and not exists (select 1 from erp_dondathang where NPP_FK = a.PK_SEQ and NPP_DACHOT = 1 and NgayDonHang <= '"+denngay+"' and NgayDonHang >= '"+tungay+"' )  and  pk_seq in ("+nppid+")";
					System.out.println("Get ĐH NPP: "+query);
					rs = db.get(query);
					rsmd = rs.getMetaData();
					socottrongSql = rsmd.getColumnCount();
					countRow = countRow + 2 ;
					for( int i =1 ; i <=socottrongSql ; i ++  )
					{
						cells.setColumnWidth(i, 20.0f);
						cell = cells.getCell(countRow,i-1 );cell.setValue(rsmd.getColumnName(i));
						ReportAPI.setCellBackground(cell, new Color(70,130,180), BorderLineType.THIN, true, 0);	
					 
					}
					stt = 0;
					countRow++;
					while(rs.next())
					{
						boolean kt = false;
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
								
									if(i != socottrongSql)
										ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
									else
										ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
							}
							else
							{
								cell.setValue(rs.getString(i));
								ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
							}
						}
						++countRow;
					}
					
					
					db.update("IF OBJECT_ID('tempdb.dbo.#GetNgayKhongVT') IS NOT NULL DROP TABLE #GetNgayKhongVT");
					db.update("IF OBJECT_ID('tempdb.dbo.#GetNgayKhongDH') IS NOT NULL DROP TABLE #GetNgayKhongDH");
					db.update("IF OBJECT_ID('tempdb.dbo.#GetNgayKhongSDTABLET') IS NOT NULL DROP TABLE #GetNgayKhongSDTABLET");
					db.update("IF OBJECT_ID('tempdb.dbo.#GetNgay3DH') IS NOT NULL DROP TABLE #GetNgay3DH");
					
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
	
	
	
	
	private void TaoBaoCaoBC(Workbook workbook,String nguoitao,String tungay,String denngay,String nppid )throws Exception
	{
		try{
			
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("TheoDoiDMS");

			Cells cells = worksheet.getCells();
			cells.setRowHeight(0, 50.0f);
			Cell cell = cells.getCell("A1");
			ReportAPI.getCellStyle(cell, Color.RED, true, 16,"Báo Cáo Tình Hình DMS NGÀY 01-15/01");

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
			int sonvkdpda = 0;
			
			String so = "";
			int tongso = 0;
			worksheet.setGridlinesVisible(false);
			dbutils db = new dbutils();
			String query = "select COUNT(distinct pk_seq) as so from daidienkinhdoanh a  where a.trangthai = 1 and isnull(a.ismt,'0')  = '0' and   npp_fk in ("+nppid+")   ";
			System.out.println("Loi: "+query);
			ResultSet rs = db.get(query);
			if(rs.next())
			{
				so	= rs.getString("so");
				tongso = rs.getInt("so");
				rs.close();
			}
			cell = cells.getCell("A6");
			ReportAPI.getCellStyle(cell, Color.BLACK, true, 10,"1.Tình hình sử dụng PDA của "+so+" NVBH: ");
			
			query = "select "+tongso+" - COUNT(distinct ddkd_fk) as so from  ddkd_khachhang where CONVERT(varchar(10), thoidiem, 121) <= '"+denngay+"' and CONVERT(varchar(10), thoidiem, 121) >= '"+tungay+"' and datepart(DW ,thoidiem) <> 1 and exists (select 1 from daidienkinhdoanh ddkd where ddkd.trangthai = 1 and ddkd.pk_seq = ddkd_fk and isnull(ddkd.ismt,'0')  = 0  and ddkd.npp_fk in ("+nppid+")  )";
			System.out.println("GetNVKOVT: "+query);
			 rs = db.get(query);
			if(rs.next())
			{
				so	= rs.getString("so");
				sonvkdpda = Integer.parseInt(so);
				rs.close();
			}
			cell = cells.getCell("A7");
			ReportAPI.getCellStyle(cell, Color.BLACK, true, 10,"- Có "+so+" NVBH không viếng thăm khách hàng từ ngày:"+tungay+" đến ngày: "+denngay);
			
			
			query = "select "+tongso+" - COUNT(distinct a.ddkd_fk) as so from  donhang a inner join DANGNHAP_DDKD b on a.DDKD_FK = b.DDKD_FK  where a.NGAYNHAP <= '"+denngay+"' and a.NGAYNHAP >= '"+tungay+"' and trangthai <> 2 and exists (select 1 from daidienkinhdoanh ddkd where ddkd.trangthai = 1 and isnull(ddkd.ismt,'0')  = 0 and ddkd.pk_seq = a.ddkd_fk  and ddkd.npp_fk in ("+nppid+") )";
			System.out.println("NVBH khong co DH: "+query);
			 rs = db.get(query);
			if(rs.next())
			{
				so	= rs.getString("so");
				if(Integer.parseInt(so) < sonvkdpda)
				{
					sonvkdpda = Integer.parseInt(so);
				}
				rs.close();
			}
			cell = cells.getCell("A8");
			ReportAPI.getCellStyle(cell, Color.BLACK, true, 10,"- Có "+so+" NVBH không có đơn hàng từ ngày:"+tungay+" đến ngày: "+denngay);
			
			sonvkdpda =0;
			
			query="IF OBJECT_ID('tempdb.dbo.#GetNgayKhongVT') IS NOT NULL "
					+"\n DROP TABLE #GetNgayKhongVT "
					+"\n CREATE TABLE #GetNgayKhongVT ("
					+"\n ddkd_fk numeric(18,0),  "
					+"\n ngay varchar(100) )";

			db.update(query);
			System.out.println("QR1: "+query);
				query= " insert into #GetNgayKhongVT "
					+ "\n select  ddkd.PK_SEQ as DDKD_FK, [dbo].[GetNgayKhongVT](ddkd.pk_seq,'"+denngay+"') "+
					"\n  from DAIDIENKINHDOANH ddkd ";
				db.update(query);
				System.out.println("QR1: "+query);
				
				query="IF OBJECT_ID('tempdb.dbo.#GetNgayKhongDH') IS NOT NULL "
						+"\n DROP TABLE #GetNgayKhongDH "
						+"\n CREATE TABLE #GetNgayKhongDH ("
						+"\n ddkd_fk numeric(18,0),  "
						+"\n ngay varchar(100) )";

				db.update(query);
				System.out.println("QR1: "+query);
				query= " insert into #GetNgayKhongDH "
						+ "\n select  ddkd.PK_SEQ as DDKD_FK,[dbo].[GetNgayKhongDH](ddkd.pk_seq,'"+denngay+"') "+
						"\n  from DAIDIENKINHDOANH ddkd ";
				db.update(query);
				System.out.println("QR1: "+query);
				
				query="IF OBJECT_ID('tempdb.dbo.#GetNgayKhongSDTABLET') IS NOT NULL "
						+"\n DROP TABLE #GetNgayKhongSDTABLET "
						+"\n CREATE TABLE #GetNgayKhongSDTABLET ("
						+"\n ddkd_fk numeric(18,0),  "
						+"\n ngay varchar(100) )";

				db.update(query);
				System.out.println("QR1: "+query);
				
				
				query= " insert into #GetNgayKhongSDTABLET "
						+ "\n select  ddkd.PK_SEQ as DDKD_FK,[dbo].[GetNgayKhongSDTABLET](ddkd.pk_seq,'"+denngay+"') "+
						"\n  from DAIDIENKINHDOANH ddkd ";
				db.update(query);
				System.out.println("QR1: "+query);
				
				query="IF OBJECT_ID('tempdb.dbo.#GetNgay3DH') IS NOT NULL "
					+"\n DROP TABLE #GetNgay3DH "
					+"\n CREATE TABLE #GetNgay3DH ("
					+"\n ddkd_fk numeric(18,0),  "
					+"\n ngay varchar(1000) )";

				db.update(query);
				System.out.println("QR1: "+query);
			
			
				query= " insert into #GetNgay3DH "
					+ "\n select  a.pk_seq,isnull(ddkd.ngaycong,'') "+
					"\n  from daidienkinhdoanh a left join chamcong1501 ddkd on a.pk_seq = ddkd.ddkd_fk";
				db.update(query);
				System.out.println("QR1: "+query);
				
			query = "select distinct ddkd.pk_seq,'' as [STT], isnull((select top(1) ten from BM a inner join BM_CHINHANH b on a.PK_SEQ = b.BM_FK and a.TRANGTHAI = 1 and b.VUNG_FK = (select top(1)VUNG_FK from KHUVUC where PK_SEQ = GS.khuvuc_fk)),'') as [RSM], isnull((select top(1) ten from ASM a inner join ASM_KHUVUC b on a.PK_SEQ = b.ASM_FK and a.TRANGTHAI = 1 and b.KHUVUC_FK = gs.khuvuc_fk),'') as [ASM],  gs.TEN as [Giám Sát],npp.TEN as [NPP trực thuộc],kv.TEN as [Khu Vực],ddkd.TEN as [Nhân Viên], case when ddkd.trangthai = 1 then N'Hoạt Động' else N'Không Hoạt Động' end [Trạng Thái], "
					 +"\n  (select top 1 ngay from  #GetNgayKhongVT where ddkd_fk = ddkd.pk_seq) as [Không Viếng Thăm],(select top 1 ngay from  #GetNgayKhongDH where ddkd_fk = ddkd.pk_seq) as [Không Có Đơn Hàng],(select top 1 ngay from  #GetNgay3DH where ddkd_fk = ddkd.pk_seq) as [Ngày Công],(select top 1 ngay from  #GetNgayKhongSDTABLET where ddkd_fk = ddkd.pk_seq) as [Không sử dụng Tablet]"
					 +"\n   from DAIDIENKINHDOANH ddkd left join NHAPHANPHOI npp on npp.PK_SEQ = ddkd.NPP_FK "
					 +"\n  left join  ddkd_gsbh dd on dd.ddkd_fk = ddkd.PK_SEQ "
					 +"\n  left join  nhapp_giamsatbh a on a.NPP_FK = npp.PK_SEQ and a.GSBH_FK = dd.GSBH_FK"				
					 +"\n  left join GIAMSATBANHANG gs on gs.PK_SEQ = a.GSBH_FK  "			
					 +"\n  inner join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK"
					 +"\n  where 1 = 1 "
					 +"\n  and   npp.PK_SEQ in ("+nppid+")  and gs.trangthai = 1 and isnull(ddkd.ismt,'0')  = 0 "
					 +"\n union "
					 +"\n select distinct ddkd.pk_seq,'' as [STT], isnull((select top(1) ten from BM a inner join BM_CHINHANH b on a.PK_SEQ = b.BM_FK and a.TRANGTHAI = 1 and b.VUNG_FK = (select top(1)VUNG_FK from KHUVUC where PK_SEQ = GS.khuvuc_fk)),'') as [RSM], isnull((select top(1) ten from ASM a inner join ASM_KHUVUC b on a.PK_SEQ = b.ASM_FK and a.TRANGTHAI = 1 and b.KHUVUC_FK = gs.khuvuc_fk),'') as [ASM], '' as [Giám Sát],npp.TEN as [NPP trực thuộc],kv.TEN as [Khu Vực],ddkd.TEN as [Nhân Viên], case when ddkd.trangthai = 1 then N'Hoạt Động' else N'Không Hoạt Động' end [Trạng Thái], "
					 +"\n  (select top 1 ngay from  #GetNgayKhongVT where ddkd_fk = ddkd.pk_seq) as [Không Viếng Thăm],(select top 1 ngay from  #GetNgayKhongDH where ddkd_fk = ddkd.pk_seq) as [Không Có Đơn Hàng],(select top 1 ngay from  #GetNgay3DH where ddkd_fk = ddkd.pk_seq) as [Ngày Công],(select top 1 ngay from  #GetNgayKhongSDTABLET where ddkd_fk = ddkd.pk_seq) as [Không sử dụng Tablet]"
					 +"\n   from DAIDIENKINHDOANH ddkd left join NHAPHANPHOI npp on npp.PK_SEQ = ddkd.NPP_FK "
					 +"\n  left join  ddkd_gsbh dd on dd.ddkd_fk = ddkd.PK_SEQ "
					 +"\n  left join  nhapp_giamsatbh a on a.NPP_FK = npp.PK_SEQ and a.GSBH_FK = dd.GSBH_FK"
					 +"\n  left join GIAMSATBANHANG gs on gs.PK_SEQ = a.GSBH_FK  "				
					 +"\n  inner join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK"
					 +"\n  where 1 = 1 "
					 +"\n  and   npp.PK_SEQ in ("+nppid+")  and isnull(ddkd.ismt,'0')  = 0"
					 +"\n  and not exists "
					 +"\n ( select top(1) 1 "
					 +"\n   from DAIDIENKINHDOANH ddkd1 left join NHAPHANPHOI npp on npp.PK_SEQ = ddkd1.NPP_FK "
					 +"\n  left join  nhapp_giamsatbh a on a.NPP_FK = npp.PK_SEQ and a.GSBH_FK = dd.GSBH_FK"
					 +"\n  left join GIAMSATBANHANG gs on gs.PK_SEQ = a.GSBH_FK  "				
					 +"\n  inner join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK"
					 +"\n  where 1 = 1 "
					 +"\n  and   npp.PK_SEQ in ("+nppid+")  and gs.trangthai = 1  and ddkd1.PK_SEQ = ddkd.PK_SEQ and isnull(ddkd1.ismt,'0')  = 0"
					 		+ ")"				 
					 
					 + "\n order by kv.ten,npp.ten ";
			System.out.println("Get NVBH ko PDA: "+query);
			 rs = db.get(query);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int socottrongSql = rsmd.getColumnCount();
			
			int countRow = 10;

			for( int i =2 ; i <=socottrongSql ; i ++  )
			{
				cell = cells.getCell(countRow,i-2 );cell.setValue(rsmd.getColumnName(i));
				cells.setColumnWidth(i, 20.0f);
				ReportAPI.setCellBackground(cell, new Color(70,130,180), BorderLineType.THIN, true, 0);	
			 
			}
			countRow ++;
			
			int stt = 0;
			while(rs.next())
			{
				boolean kt = false;
				boolean ck = true;
				stt++;
				String value = "";
				for(int i =2;i <=socottrongSql ; i ++)
				{
					cell = cells.getCell(countRow,i-2 );
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
							if(rsmd.getColumnName(i).equals("Không sử dụng Tablet"))
								if(rs.getString(i-1).equals(rs.getString(i-2)))
									sonvkdpda++;
							cell.setValue(rs.getString(i));
							
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					}
				}
				++countRow;
			}
			
			
			

			query = "select COUNT( pk_seq) as so from daidienkinhdoanh where trangthai = 1 and   npp_fk in ("+nppid+")  and not exists  (select top 1 1 from DONHANG a where a.NGAYNHAP <= '"+denngay+"' and a.NGAYNHAP >= '"+tungay+"' and TRANGTHAI <> 2  and  npp_FK in ("+nppid+") and a.ddkd_fk = daidienkinhdoanh.pk_seq) and not exists"
					+ "\n (select top 1 1 from ddkd_khachhang a where CONVERT(varchar(10), a.thoidiem, 121) <= '"+denngay+"' and CONVERT(varchar(10), a.thoidiem, 121) >= '"+tungay+"'  and a.ddkd_fk = daidienkinhdoanh.pk_seq) ";
			System.out.println("NVKOPDA: "+query);
			 rs = db.get(query);
			if(rs.next())
			{
				sonvkdpda	= rs.getInt("so");
				rs.close();
			}
			cell = cells.getCell("A9");
			ReportAPI.getCellStyle(cell, Color.BLACK, true, 10,"- Có "+sonvkdpda+" NVBH không sử dụng PDA từ ngày:"+tungay+" đến ngày: "+denngay);
			
			
			query = "select (select COUNT(distinct pk_seq) from nhaphanphoi where trangthai = 1 and   PK_seq in ("+nppid+"))  - count(distinct NPP_FK) as so from DONHANG a where a.NGAYNHAP <= '"+denngay+"' and a.NGAYNHAP >= '"+tungay+"' and TRANGTHAI = 1  and  npp_FK in ("+nppid+")";
			 rs = db.get(query);
			if(rs.next())
			{
				so	= rs.getString("so");
				rs.close();
			}
		
			
			
			cell = cells.getCell("A"+(countRow+1));
			ReportAPI.getCellStyle(cell, Color.BLACK, true, 10,"2.Tiến độ xử lý đơn hàng tại NPP (từ ngày:"+tungay+" đến ngày:"+denngay+" ).Có "+so+" NPP chưa chốt đơn hàng.");
			
		
					
					
			query =   " declare @tungay varchar(10) = '"+tungay+"' "
			  +"\n declare @denngay varchar(10) = '"+denngay+"' "
			  +"\n select '' as [STT], gs.TEN as [Giám Sát],npp.TEN as [NPP trực thuộc],kv.TEN as [Khu Vực],"
			  +"\n (select COUNT(PK_SEQ) from DONHANG where NGAYNHAP >= @tungay and NGAYNHAP <= @denngay "
			  +"\n and NPP_FK = npp.PK_SEQ and trangthai <> 2  ) as [Tổng số đơn hàng],(select COUNT(PK_SEQ) from DONHANG where NGAYNHAP >= @tungay and NGAYNHAP <= @denngay "
			  +"\n and NPP_FK = npp.PK_SEQ and TRANGTHAI = 1 ) as [Tổng số đơn hàng đã chốt],"
			  +"\n round(dbo.phepchia((select COUNT(PK_SEQ) from DONHANG where NGAYNHAP >= @tungay and NGAYNHAP <= @denngay "
			  +"\n and NPP_FK = npp.PK_SEQ and TRANGTHAI = 1 ),(select COUNT(PK_SEQ) from DONHANG where NGAYNHAP >= @tungay and NGAYNHAP <= @denngay "
			  +"\n and NPP_FK = npp.PK_SEQ and trangthai <> 2 )),2 )*100 as [Tỉ lệ chốt đơn %]"
			  +"\n from NHAPHANPHOI npp left join nhapp_giamsatbh a on a.NPP_FK = npp.PK_SEQ "
			  +"\n left join GIAMSATBANHANG gs on gs.PK_SEQ = a.GSBH_FK   "
			  +"\n left join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK"
			  +"\n left join DONHANG dh on dh.NPP_FK = npp.PK_SEQ and dh.NGAYNHAP >= @tungay and dh.NGAYNHAP <= @denngay"
			  +"\n where   npp.pk_seq in ("+nppid+") and npp.trangthai = 1 and (gs.trangthai = 1 or gs.pk_seq is null) "
			  +"\n group by gs.TEN,npp.TEN,kv.TEN,npp.PK_SEQ "
			  +"\n union  "
			  +"\n select distinct '' as [STT], '' as [Giám Sát],npp.TEN as [NPP trực thuộc],kv.TEN as [Khu Vực],"
			  +"\n (select COUNT(PK_SEQ) from DONHANG where NGAYNHAP >= @tungay and NGAYNHAP <= @denngay "
			  +"\n and NPP_FK = npp.PK_SEQ and trangthai <> 2  ) as [Tổng số đơn hàng],(select COUNT(PK_SEQ) from DONHANG where NGAYNHAP >= @tungay and NGAYNHAP <= @denngay "
			  +"\n and NPP_FK = npp.PK_SEQ and TRANGTHAI = 1 ) as [Tổng số đơn hàng đã chốt],"
			  +"\n round(dbo.phepchia((select COUNT(PK_SEQ) from DONHANG where NGAYNHAP >= @tungay and NGAYNHAP <= @denngay "
			  +"\n and NPP_FK = npp.PK_SEQ and TRANGTHAI = 1 ),(select COUNT(PK_SEQ) from DONHANG where NGAYNHAP >= @tungay and NGAYNHAP <= @denngay "
			  +"\n and NPP_FK = npp.PK_SEQ and trangthai <> 2 )),2 )*100 as [Tỉ lệ chốt đơn %]"
			  +"\n from NHAPHANPHOI npp left join nhapp_giamsatbh a on a.NPP_FK = npp.PK_SEQ "
			  +"\n left join GIAMSATBANHANG gs on gs.PK_SEQ = a.GSBH_FK   "
			  +"\n left join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK"
			  +"\n left join DONHANG dh on dh.NPP_FK = npp.PK_SEQ and dh.NGAYNHAP >= @tungay and dh.NGAYNHAP <= @denngay"
			  +"\n where   npp.pk_seq in ("+nppid+") and npp.trangthai = 1  "
			  + "\n and npp.pk_seq not in ( "
			  +"\n select npp.pk_seq "
			  +"\n from NHAPHANPHOI npp left join nhapp_giamsatbh a on a.NPP_FK = npp.PK_SEQ "
			  +"\n left join GIAMSATBANHANG gs on gs.PK_SEQ = a.GSBH_FK   "
			  +"\n left join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK"
			  +"\n left join DONHANG dh on dh.NPP_FK = npp.PK_SEQ and dh.NGAYNHAP >= @tungay and dh.NGAYNHAP <= @denngay"
			  +"\n where   npp.pk_seq in ("+nppid+") and npp.trangthai = 1 and (gs.trangthai = 1 or gs.pk_seq is null) "
			  +"\n group by gs.TEN,npp.TEN,kv.TEN,npp.PK_SEQ )"
			  +"\n group by gs.TEN,npp.TEN,kv.TEN,npp.PK_SEQ "
			  + "\n  order by kv.ten,npp.ten ";
			System.out.println("Get ĐH NPP: "+query);
			rs = db.get(query);
			rsmd = rs.getMetaData();
			socottrongSql = rsmd.getColumnCount();
			countRow = countRow + 2 ;
			for( int i =1 ; i <=socottrongSql ; i ++  )
			{
				cells.setColumnWidth(i, 20.0f);
				cell = cells.getCell(countRow,i-1 );cell.setValue(rsmd.getColumnName(i));
				ReportAPI.setCellBackground(cell, new Color(70,130,180), BorderLineType.THIN, true, 0);	
			 
			}
			stt = 0;
			countRow++;
			while(rs.next())
			{
				boolean kt = false;
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
						
							if(i != socottrongSql)
								ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
							else
								ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					}
					else
					{
						cell.setValue(rs.getString(i));
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					}
				}
				++countRow;
			}
			
			
			
			query = "select COUNT(distinct a.PK_SEQ) as so from  NHAPHANPHOI a where a.TRANGTHAI = 1 and not exists (select 1 from erp_dondathang where NPP_FK = a.PK_SEQ and NPP_DACHOT = 1 and NgayDonHang <= '"+denngay+"' and NgayDonHang >= '"+tungay+"' )  and  pk_seq in ("+nppid+") ";
			System.out.println("So NPP dat Hang: "+query);
			 rs = db.get(query);
			if(rs.next())
			{
				so	= rs.getString("so");
				rs.close();
			}
			cell = cells.getCell("A"+(countRow+2));
			ReportAPI.getCellStyle(cell, Color.BLACK, true, 10,"3.NPP đặt hàng lên SGP:Có "+so+" NPP chưa đặt hàng từ ngày "+tungay+" đến ngày: "+denngay);
			
			
					query = "select '' as STT, a.Manpp as [Mã NPP], a.ten as [Tên NPP] from  NHAPHANPHOI a where a.TRANGTHAI = 1 and not exists (select 1 from erp_dondathang where NPP_FK = a.PK_SEQ and NPP_DACHOT = 1 and NgayDonHang <= '"+denngay+"' and NgayDonHang >= '"+tungay+"' )  and  pk_seq in ("+nppid+")";
					System.out.println("Get ĐH NPP: "+query);
					rs = db.get(query);
					rsmd = rs.getMetaData();
					socottrongSql = rsmd.getColumnCount();
					countRow = countRow + 2 ;
					for( int i =1 ; i <=socottrongSql ; i ++  )
					{
						cells.setColumnWidth(i, 20.0f);
						cell = cells.getCell(countRow,i-1 );cell.setValue(rsmd.getColumnName(i));
						ReportAPI.setCellBackground(cell, new Color(70,130,180), BorderLineType.THIN, true, 0);	
					 
					}
					stt = 0;
					countRow++;
					while(rs.next())
					{
						boolean kt = false;
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
								
									if(i != socottrongSql)
										ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
									else
										ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
							}
							else
							{
								cell.setValue(rs.getString(i));
								ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
							}
						}
						++countRow;
					}
					
					db.update("IF OBJECT_ID('tempdb.dbo.#GetNgayKhongVT') IS NOT NULL DROP TABLE #GetNgayKhongVT");
					db.update("IF OBJECT_ID('tempdb.dbo.#GetNgayKhongDH') IS NOT NULL DROP TABLE #GetNgayKhongDH");
					db.update("IF OBJECT_ID('tempdb.dbo.#GetNgayKhongSDTABLET') IS NOT NULL DROP TABLE #GetNgayKhongSDTABLET");
					db.update("IF OBJECT_ID('tempdb.dbo.#GetNgay3DH') IS NOT NULL DROP TABLE #GetNgay3DH");		
			
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
			ReportAPI.getCellStyle(cell, Color.RED, true, 16, "Báo Cáo Chấm công ");
			cell = cells.getCell("A2");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 10, "  Đến ngày : " + obj.getdenngay());
			cell = cells.getCell("A3");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 10, "Ngày tạo : " + this.getDateTime());
			cell = cells.getCell("A4");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 10, "Người tạo : " + obj.getuserTen());

			
			
			ResultSet	rs = obj.getDb().get(query);

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
	private String setQuery_KhongPiVot(IStockintransit obj, String view) 
	{
		Utility util = new Utility();

		
		String query =   "\n select v.Ten [Vùng], kv.ten [Khu vực],npp.ten [Tên NPP]	" +
						 "\n		,case npp.trangthai when 1 then N'Hoạt động' else N'Ngưng hoạt động' end [Trạng thái NPP] " +
						 "\n		,ddkd.SMARTID [Mã NVBH], ddkd.TEN [Tên NVBH], case ddkd.trangthai when 1 then N'Hoạt động' else N'Ngưng hoạt động' end [Trạng thái NVBH] " +
						 "\n        ,mcp.NGAY,vtkh.countKH [Viếng thăm KH],vtnppSang.ThoiDiem  [Viếng thăm NPP đầu ngày] " + 
						 "\n		,vtnppChieu.ThoiDiem [Rời đi NPP chiều],dh.doanhso[Doanh số], [dbo].[TimeFormat_v1](isnull(lamviec.tg,0)) [Thời gian làm việc] " + 
						 "\n		, case when vtkh.countKH  > 0 and dh.doanhso  > 0 then 1 else 0 end [Công] " + 
						 "\n from DDKD_SOKH mcp " + 
						 "\n inner join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = mcp.DDKD_FK " +
						 "\n left join nhaphanphoi npp on npp.pk_seq =ddkd.npp_fk " +
						 "\n left join khuvuc kv on kv.pk_seq =npp.khuvuc_fk " +
						 "\n left join vung v on v.pk_seq =kv.vung_fk" +
						 "\n outer apply " + 
						 "\n ( 	" + 
						 "\n	select top 1 1 ok ,convert(varchar, a.thoidiem,120) ThoiDiem  " + 
						 "\n	from DDKD_NPP a " + 
						 "\n	where a.ngay = mcp.NGAY and a.DDKD_FK = mcp.DDKD_FK and a.SANG = 1 and DATEPART(hour,thoidiem) <=12  " + 
						 "\n )vtnppSang " + 
						 "\n " + 
						 "\n outer apply " + 
						 "\n ( " + 
						 "\n	select top 1 1 ok ,convert(varchar, a.thoidiem,120) ThoiDiem  " + 
						 "\n	from DDKD_NPP a " + 
						 "\n	where a.ngay = mcp.NGAY and a.DDKD_FK = mcp.DDKD_FK  and isnull(a.sang,0)= 0 and  DATEPART(hour,thoidiem) >=12   " + 
						 "\n )vtnppChieu " + 
						 "\n outer apply " + 
						 "\n ( " + 
						 "\n	select 1 ok ,count(distinct a.khachhang_fk) countKH " + 
						 "\n	from ddkd_khachhang a " + 
						 "\n	where a.ngayghinhan = mcp.NGAY and a.DDKD_FK = mcp.DDKD_FK   " + 
						 "\n	 " + 
						 "\n )vtkh " + 
						 "\n outer apply " + 
						 "\n ( " + 
						 "\n	select 1 ok ,DATEDIFF(minute, min(a.thoidiem), max(a.thoidiemdi)) tg " + 
						 "\n	from ddkd_khachhang a " + 
						 "\n	where a.ngayghinhan = mcp.NGAY and a.DDKD_FK = mcp.DDKD_FK   " + 
						 "\n )lamviec " + 
						 "\n outer apply " + 
						 "\n ( " + 
						 "\n	 select 1 ok, sum( b.soluong * b.giamua ) doanhso " +
						 "\n	 from DONHANG a " +
						 "\n	 inner join donhang_sanpham b on a.pk_seq = b.donhang_fk" +
						 "\n	 where a.NGAYNHAP = mcp.NGAY and a.DDKD_FK = mcp.DDKD_FK and  a.trangthai in (1)" +
						 "\n		and not exists ( select 1 from donhangtrave where trangthai = 3 and donhang_fk = a.pk_seq)  " + 
						 "\n )dh " + 
						 "\n where mcp.Ngay >='"+obj.gettungay()+"' and mcp.Ngay <='"+obj.getdenngay()+"' " ; 
						 
		if(obj.getvungId().trim().length() > 0)
			query += " and v.pk_seq = "+ obj.getvungId() +"  ";
		if(obj.getkhuvucId().trim().length() > 0)
			query += " and kv.pk_seq = "+ obj.getkhuvucId() +"  ";		
		if(obj.getnppId().length() > 0)
			query += " and npp.pk_seq in (" + obj.getnppId() + ") ";
		if (obj.getDdkd().length() > 0)
			query += " and ddkd.pk_seq ='" + obj.getDdkd() + "'";
		query += " and npp.pk_seq in " + util.quyen_npp(obj.getuserId()) ;
		
		
		query +="\n order by ddkd.TEN, mcp.NGAY ";
		
		System.out.println("bc:______" + query);

		return query ;
	}
	private String setQuery_KhongPiVotQl(IStockintransit obj, String view) 
	{
		Utility util = new Utility();
		String query = "\n select khnv.ngay  [Ngày]  ,v.ten [Vùng] " +
			"\n 		, kv.ten [Khu vực] , nv.dangnhap [Đăng nhập], nv.ten [Tên], lnv.ten [Loại nhân viên]  " +
			"\n  	,npp.ten [NPP Viếng thăm], isnull( convert( varchar,khnpp.thoidiemden,120)  ,'') [Viếng thăm NPP],  isnull( convert( varchar,khnpp.thoidiemdi,120)  ,'') [Rời đi NPP]  " +
			"\n 	, isnull( convert( varchar,khnpp.thoidiemden2,120)  ,'') [Viếng thăm NPP 2],  isnull( convert( varchar,khnpp.thoidiemdi2,120)  ,'') [Rời đi NPP 2]  " +
			"\n 	, ddkd.ten [NVBH] " +
 	        "\n 	, isnull( vtkh.sokh,0) [Phải viếng thăm KH] ,  isnull( khnv.SOKH,0)   [Đã viếng thăm KH]  " +
			"\n 	,  qh.TEN + ' ,' + tt.TEN ThiTruong , thi.lat +  ', ' + thi.LONG [Tọa Độ] ,isnull( convert( varchar,thi.thoidiem,120)  ,'') [Thời điểm Thị Trường]  " +
			"\n 	, case when isnull(khnpp.KEHOACHNV_FK,0) +  isnull(khtbh.KEHOACHNV_FK,0) + isnull(thi.KEHOACHNV_FK,0)  " +
			"\n 		<= (  " +
			"\n 				case when khnpp.thoidiemden is not null or thoidiemden2 is not null then isnull(khnpp.KEHOACHNV_FK,0) else 0 end + " +
			"\n 				case when isnull( vtkh.sokh,0) > 0  then isnull(khtbh.KEHOACHNV_FK,0) else 0 end + " +
			"\n 				case when thi.lat is not null   then isnull(thi.KEHOACHNV_FK,0) else 0 end  " +
			"\n 			) " +
			"\n 	and  ( isnull(khnpp.KEHOACHNV_FK,0) > 0  or   isnull(khtbh.KEHOACHNV_FK,0) > 0 or isnull(thi.KEHOACHNV_FK,0)  > 0 )  " +
			"\n 		then 1 else 0 end cong	 " +
			"\n  from nhanvien_kehoach_log khnv" +
			
			"\n  inner join nhanvien nv on nv.PK_SEQ = khnv.NHANVIEN_FK  " +
			"\n	 inner join LoaiNhanVien lnv on lnv.loai = nv.loai  " +
			"\n  left join KEHOACHNV_NPP khnpp on khnv.KEHOACHNV_FK  = khnpp.KEHOACHNV_FK and khnpp.FULLDATE = khnv.ngay " +
			"\n  left join KEHOACHNV_TBH khtbh on khnv.KEHOACHNV_FK  = khtbh.KEHOACHNV_FK  and khtbh.FULLDATE = khnv.ngay " +
			"\n  left join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = khtbh.DDKD_FK  " +
			"\n  left join KEHOACHNV_THITRUONG thi on  khnv.KEHOACHNV_FK = thi.KEHOACHNV_FK and thi.FULLDATE = khnv.ngay " +
			"\n  left join NHAPHANPHOI npp on npp.PK_SEQ = khnpp.NPP_FK  " +
			"\n left join TINHTHANH tt on tt.PK_SEQ = thi.TINH_FK " +
			"\n left join QUANHUYEN qh on qh.PK_SEQ = thi.QUANHUYEN_FK " +
			"\n outer apply  " +
			"\n (  " +
			"\n 	select count(distinct khachhang_fk ) sokh  " +
			"\n 	from KEHOACHNV_TBH_KHACHHANG x  " +
			"\n 	where x.ngaythuchien =  khnv.ngay and x.NhanVien_Fk =  khnv.NHANVIEN_FK  " +
			"\n ) vtkh " +
			"\n	outer apply (  select khuvuc_fk , vung_fk from [dbo].[ufn_VungKhuVuc_NhanVien](khnv.NHANVIEN_FK ) x ) ql  " +
			"\n left join KHUVUC kv on kv.PK_SEQ = ql.KHUVUC_FK  " +
			"\n left join vung v on v.PK_SEQ = ql.VUNG_FK  " +
			"\n  where khnv.ngay >='"+obj.gettungay()+"'  and  khnv.ngay <='"+obj.getdenngay()+"'";   
	
		 
		if(obj.getvungId().trim().length() > 0)
			query += " and v.pk_seq = "+ obj.getvungId() +"  ";
		if(obj.getkhuvucId().trim().length() > 0)
			query += " and kv.pk_seq = "+ obj.getkhuvucId() +"  ";		
		if(obj.getnppId().length() > 0)
			query += " and npp.pk_seq in (" + obj.getnppId() + ") ";
		
		query += " and ( khnv.NHANVIEN_FK = "+obj.getuserId()+" or khnv.NHANVIEN_FK in ( "+ KeHoachNhanVienList.getIdNhanVienCapDuoiQuery(obj.getDb(), obj.getuserId() ) +" )) ";
		
		query +="\n order by [Ngày] ";
		
		System.out.println("bc:______" + query);

		return query ;
	}
	
}
