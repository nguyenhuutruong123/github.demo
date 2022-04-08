package geso.dms.center.servlets.reports;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.distributor.util.Utility;

import java.io.File;
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

public class BCScoreSvl extends HttpServlet {
		
	private static final long serialVersionUID = 1L;

	public BCScoreSvl() {
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
		String nextJSP = "/AHF/pages/Center/BCScore.jsp";
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
		
		String tuthang=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tuthang").length()< 2 ? ("0"+request.getParameter("tuthang")) :request.getParameter("tuthang")) ;
/*		String toithang=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denthang").length()< 2 ? ("0"+request.getParameter("denthang")) :request.getParameter("denthang")) ;*/
		obj.setFromMonth(tuthang);
		/*obj.setToMonth(toithang);*/
		/*obj.setToYear(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dennam")));*/
		obj.setFromYear(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tunam")));
		
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
		

		String[] nvbh = request.getParameterValues("nvbh");
		 s = "";
		if(nvbh != null)
		{
			
			for(int i = 0; i < nvbh.length; i++)
			{
				s += nvbh[i] +",";
			}
			if(s.length() > 0)
			{
				s = s.substring(0,s.length() -1);
			}
		
		}
		obj.setDdkd(s);
		
		String[] gsbh = request.getParameterValues("gsbh");
		 s = "";
		if(gsbh != null)
		{
			
			for(int i = 0; i < gsbh.length; i++)
			{
				s += gsbh[i] +",";
			}
			if(s.length() > 0)
			{
				s = s.substring(0,s.length() -1);
			}
		
		}
		obj.setgsbhId(s);
		
		String[] asm = request.getParameterValues("asm");
		 s = "";
		if(asm != null)
		{
			
			for(int i = 0; i < asm.length; i++)
			{
				s += asm[i] +",";
			}
			if(s.length() > 0)
			{
				s = s.substring(0,s.length() -1);
			}
		
		}
		obj.setASMId(s);
		
		
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
		
		
		
		String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));		    
		if(trangthai == null)
			trangthai = "";	    	   	    
		Utility ut = new Utility();
		geso.dms.distributor.db.sql.dbutils db = new geso.dms.distributor.db.sql.dbutils();
		ut.getUserInfo(userId, db);
		db.shutDown();
		if(ut.getLoaiNv().equals("2"))
			obj.setLoaiMenu("NPP");
		
		if(s.length() <= 0)
		{
			s = " select pk_seq from nhaphanphoi where 1 = 1 ";
			
			
		
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
		String nextJSP = "/AHF/pages/Center/BCScore.jsp";
		
		System.out.println("Action la: " + action);
		
		try{
			if (action.equals("Taomoi")) 
			{			
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BCScore.xlsm");
				Workbook workbook = new Workbook();
				TaoBaoCaoNew(workbook, userTen,obj.getFromMonth(),obj.getFromYear(),obj.getnppId(),obj.getDdkd(),obj.getASMId(),obj.getgsbhId(),obj.getvungId(),obj.getkhuvucId());
				
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

	
	private void TaoBaoCaoNew(Workbook workbook,String nguoitao,String tungay,String denngay,String nppid, String nvbh, String asm, String gsbh,String vung, String khuvuc )throws Exception
	{
		try{
			FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BCScore.xlsm");
			//File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\BCScore.xlsm");
			//FileInputStream fstream = new FileInputStream(f) ;
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("BCScore");
		
			dbutils db = new dbutils();
			Cells cells = worksheet.getCells();
			cells.setRowHeight(0, 50.0f);
			Cell cell = cells.getCell("A1");
			ReportAPI.getCellStyle(cell, Color.RED, true, 16,"Báo Cáo Score Board");

			cell = cells.getCell("A3");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Ngày tạo : "+ getDateTime());
			
			cell = cells.getCell("A2");
			cells.setColumnWidth(0, 20.0f);
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Tháng: "+ tungay);
			cell = cells.getCell("B2");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Năm: "+ denngay);
			cells.setColumnWidth(4, 20.0f);
			cells.setColumnWidth(9, 15.0f);
			cells.setColumnWidth(8, 15.0f);
			cells.setColumnWidth(7, 15.0f);
			cells.setColumnWidth(6, 15.0f);
			cell = cells.getCell("A4");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10,"Người tạo : " + nguoitao);
		
			
			
			String query="IF OBJECT_ID('tempdb.dbo.#TBLDD') IS NOT NULL "
				+"\n DROP TABLE #TBLDD "
				+"\n CREATE TABLE #TBLDD ("
				+"\n ddkd_fk numeric(18, 0) "
				+"\n ) ";
			db.update(query);
			System.out.println("QR1: "+query);
			
			query= 	"\n insert #TBLDD(ddkd_fk) "+
					"\n select ddkd.PK_SEQ from DAIDIENKINHDOANH ddkd "+
					"\n inner join NHAPHANPHOI a on a.PK_SEQ = ddkd.NPP_FK "+
					"\n left join ddkd_gsbh dg on dg.ddkd_fk = ddkd.pk_seq " +
					"\n left join asm asm on asm.Smartid = ddkd.smartid "+
					"\n where  ddkd.TRANGTHAI = 1" ;
			if(nppid.length()>0)
			{
				query +="\n and a.pk_seq in ("+nppid+") ";
			}
			if(nvbh.length()>0)
			{
				query += "\n and ddkd.pk_seq in ("+nvbh+") ";
			}
			
			if(gsbh.length()>0)
			{
				query += "\n and dg.gsbh_fk in ("+gsbh+") ";
			}
			
			if(asm.length()>0)
			{
				query += "\n and asm.pk_seq in ("+asm+") ";
			}
			db.update(query);
			System.out.println("QR1: "+query);
			
			

			query="IF OBJECT_ID('tempdb.dbo.#TBLNGAY') IS NOT NULL "
				+"\n DROP TABLE #TBLNGAY "
				+"\n CREATE TABLE #TBLNGAY ("
				+"\n NGAYvt VARCHAR(100), "
				+"\n ngay varchar(100) "
				+"\n ) ";
			db.update(query);
			System.out.println("QR1: "+query);
			
			
			query= 	"\n insert #TBLNGAY(ngayvt, ngay) "+
					"\n select (ngay +'/'+ 'avttc') as ngayvt, ngay from dbo.uf_CacNgayTrongThang("+tungay+","+denngay+") "+
					"\n union  "+
					"\n select (ngay +'/'+ 'bhtc') as ngayvt, ngay from dbo.uf_CacNgayTrongThang("+tungay+","+denngay+") "+
					"\n union  "+
					"\n select (ngay +'/'+ 'drop') as ngayvt, ngay from dbo.uf_CacNgayTrongThang("+tungay+","+denngay+") "+
					"\n union  "+
					"\n select (ngay +'/'+ 'sell') as ngayvt, ngay from dbo.uf_CacNgayTrongThang("+tungay+","+denngay+") "+
					"\n union  "+
					"\n select (ngay +'/'+ 'tltb') as ngayvt, ngay from dbo.uf_CacNgayTrongThang("+tungay+","+denngay+") "+
					"\n union  "+
					"\n select (ngay +'/'+ 'sellnpp') as ngayvt, ngay from dbo.uf_CacNgayTrongThang("+tungay+","+denngay+") "+
					"\n union  "+
					"\n select (ngay +'/'+ 'SKU') as ngayvt, ngay from dbo.uf_CacNgayTrongThang("+tungay+","+denngay+") ";

			db.update(query);
			System.out.println("QR1: "+query);

			query = "select * from #TBLNGAY ";
			
			System.out.println(query);
			ResultSet r = db.getScrol(query);
			String subPivot = "";
			String subIn = "";
			int col = 0;
			while(r.next())
			col++;
			String[] col_row= new String[col];
			r.beforeFirst();
			int i = 0;
			while(r.next()){
				
			subPivot += "PIVOTTABLE.["+r.getString("ngayvt")+"], \n";
			subIn += "["+r.getString("ngayvt")+"], ";
			i++;
			}
			subPivot = subPivot.substring(0, subPivot.length() - 3);
			subIn = subIn.substring(0, subIn.length() - 2);
			
			
			query="IF OBJECT_ID('tempdb.dbo.#TABLE1') IS NOT NULL "
				+"\n DROP TABLE #TABLE1 "
				+"\n CREATE TABLE #TABLE1 ("
				+"\n 				ddkd numeric(18,0), "
				+"\n 				ngay varchar(100), "
				+"\n 				giatri numeric(18,2) "
				+"\n ) ";
			
			db.update(query);
			System.out.println("QR1: "+query);
			
			
			query= 	"\n 			insert #TABLE1 (ddkd, ngay, giatri) "+
			"\n 			select ddkd.PK_SEQ, fg.NGAYvt, "+
			"\n 					isnull((select SUM(soluong*giamua) from DONHANG dh inner join  "+
			"\n 						DONHANG_SANPHAM dhsp on dh.PK_SEQ = dhsp.DONHANG_FK where dh.TRANGTHAI <> 2  "+
			"\n 					 and dh.NGAYNHAP = fg.ngay and dh.ddkd_fk = ddkd.pk_seq  "+
			"\n 						),0)  as SELLOUT "+
			"\n 						 from DaiDienKinhDoanh ddkd "+
			"\n 				   inner join #TBLNGAY fg on 1 =1 "+
			"\n 			   inner join NHAPHANPHOI a on a.PK_SEQ = ddkd.NPP_FK " +
			"\n 				left join ddkd_gsbh dg on dg.ddkd_fk = ddkd.pk_seq " +
			"\n 				left join asm asm on asm.Smartid = ddkd.smartid "+
			"\n 			    where  ddkd.TRANGTHAI = 1 and NGAYvt like '%/sell' ";
			if(nppid.length()>0)
			{
				query +="\n and a.pk_seq in ("+nppid+") ";
			}
			if(nvbh.length()>0)
			{
				query += "\n and ddkd.pk_seq in ("+nvbh+") ";
			}
			
			if(gsbh.length()>0)
			{
				query += "\n and dg.gsbh_fk in ("+gsbh+") ";
			}
			
			if(asm.length()>0)
			{
				query += "\n and asm.pk_seq in ("+asm+") ";
			}
			
			query += "\n 			union  "+
			"\n 			select	 ddkd.PK_SEQ, fg.NGAYvt,dbo.PhepChia  "+
			"\n 					(   "+
			"\n 					(select COUNT( dh.khachhang_fk) from ddkd_khachhang dh inner join DAIDIENKINHDOANH dd on ddkd.PK_SEQ= dh.ddkd_fk   "+
			"\n 						where  CONVERT(varchar(10),dh.thoidiem,120) = fg.ngay and dd.pk_seq = ddkd.pk_seq  "+
			"\n 						),   "+
			"\n 						( select a.SOKH FROM DDKD_SOKH a where a.DDKD_FK = ddkd.pk_seq   "+
			"\n 					and NGAY = (SELECT MAX(NGAY) FROM DDKD_SOKH z where z.DDKD_FK = a.DDKD_FK and z.NGAY = fg.ngay AND z.SOKH IS NOT NULL)   "+
			"\n 					) )*100    as TYLEViengThamTC "+
			"\n 					 from DaiDienKinhDoanh ddkd "+
			"\n 				   inner join #TBLNGAY fg on 1 =1 "+ 
			"\n 				 inner join NHAPHANPHOI a on a.PK_SEQ = ddkd.NPP_FK "+
			"\n 				left join ddkd_gsbh dg on dg.ddkd_fk = ddkd.pk_seq " +
			"\n 				left join asm asm on asm.Smartid = ddkd.smartid "+
			"\n 				    where  ddkd.TRANGTHAI = 1 and NGAYvt like '%/avttc' ";
			if(nppid.length()>0)
			{
				query +="\n and a.pk_seq in ("+nppid+") ";
			}
			if(nvbh.length()>0)
			{
				query += "\n and ddkd.pk_seq = "+nvbh+" ";
			}
			
			if(gsbh.length()>0)
			{
				query += "\n and dg.gsbh_fk = "+gsbh+" ";
			}
			
			if(asm.length()>0)
			{
				query += "\n and asm.pk_seq = "+asm+" ";
			}
			
			
			query +="\n 				    union "+
			"\n 				    select "+
			"\n 				  ddkd.PK_SEQ, fg.NGAYvt, dbo.PhepChia   "+
			"\n 				   (   "+
			"\n 				   (select COUNT(distinct dh.pk_seq) from DONHANG dh   "+
			"\n 				   where  dh.NGAYNHAP = fg.ngay  and dh.DDKD_FK = ddkd.pk_seq and ispda = '1'  "+
			"\n 				   ),   "+
			"\n 				   (select COUNT( dh.khachhang_fk) from ddkd_khachhang dh inner join DAIDIENKINHDOANH dd on dd.PK_SEQ= dh.ddkd_fk  "+
			"\n 				    where  CONVERT(varchar(10),dh.thoidiem,120) = fg.ngay and dd.pk_seq = ddkd.pk_seq  "+
			"\n 				   ) )*100 as TyleBanHangTC "+
			"\n 				    from DaiDienKinhDoanh ddkd "+
			"\n 				   inner join #TBLNGAY fg on 1 =1 "+
			"\n 				    inner join NHAPHANPHOI a on a.PK_SEQ = ddkd.NPP_FK "+
			"\n 				left join ddkd_gsbh dg on dg.ddkd_fk = ddkd.pk_seq " +
			"\n 				left join asm asm on asm.Smartid = ddkd.smartid "+
			"\n 				    where  ddkd.TRANGTHAI = 1 and NGAYvt like '%/bhtc' ";
			if(nppid.length()>0)
			{
				query +="\n and a.pk_seq in ("+nppid+") ";
			}
			if(nvbh.length()>0)
			{
				query += "\n and ddkd.pk_seq in ("+nvbh+") ";
			}
			
			if(gsbh.length()>0)
			{
				query += "\n and dg.gsbh_fk in ("+gsbh+") ";
			}
			
			if(asm.length()>0)
			{
				query += "\n and asm.pk_seq in ("+asm+") ";
			}
			
			query +="\n 				   union  "+
			"\n 				   select "+
			"\n 				   ddkd.PK_SEQ, fg.NGAYvt,dbo.PhepChia   "+
			"\n 			   (  "+
			"\n 				   (select SUM(soluong*giamua) from DONHANG dh inner join  "+
			"\n 				   DONHANG_SANPHAM dhsp on dh.PK_SEQ = dhsp.DONHANG_FK where dh.TRANGTHAI <> 2  "+
			"\n 				   and dh.NGAYNHAP = fg.ngay  and dh.DDKD_FK = ddkd.pk_seq  "+
			"\n 				    ),   "+
			"\n 				    (select COUNT(dh.pk_seq) from DONHANG dh  "+ 
			"\n 				    where dh.TRANGTHAI <> 2   "+
			"\n 				    and dh.NGAYNHAP = fg.ngay and dh.DDKD_FK = ddkd.pk_seq  )   "+
			"\n 				   ) as DROPSIZE "+
			"\n 				    from DaiDienKinhDoanh ddkd "+
			"\n 				   inner join #TBLNGAY fg on 1 =1 "+
			"\n 				   inner join NHAPHANPHOI a on a.PK_SEQ = ddkd.NPP_FK "+
			"\n 				left join ddkd_gsbh dg on dg.ddkd_fk = ddkd.pk_seq " +
			"\n 				left join asm asm on asm.Smartid = ddkd.smartid "+
			"\n 				    where  ddkd.TRANGTHAI = 1 and NGAYvt like '%/drop' ";
			if(nppid.length()>0)
			{
				query +="\n and a.pk_seq in ("+nppid+") ";
			}
			if(nvbh.length()>0)
			{
				query += "\n and ddkd.pk_seq in ("+nvbh+") ";
			}
			
			if(gsbh.length()>0)
			{
				query += "\n and dg.gsbh_fk in ("+gsbh+") ";
			}
			
			if(asm.length()>0)
			{
				query += "\n and asm.pk_seq in ("+asm+") ";
			}
			
			
			query +="\n 				   union  "+
			"\n 				   select  "+
			"\n 				   ddkd.PK_SEQ, fg.NGAYvt,dbo.PhepChia  "+
			"\n 				   (  "+
			"\n 				   (select COUNT(AC.FILENAME)from  "+
			"\n 				   KHACHHANG_ANHCHUP AC  "+
			"\n 				   where ac.NGAY = FG.ngay  AND ac.ddkd_fk = ddkd.pk_seq),  "+
			"\n 				   ( select SOKH FROM DDKD_SOKH a where a.DDKD_FK = ddkd.pk_seq  "+ 
			"\n 				   and NGAY = (SELECT MAX(NGAY) FROM DDKD_SOKH z where z.DDKD_FK = a.DDKD_FK and z.NGAY = fg.ngay AND SOKH IS NOT NULL)   "+
			"\n 				   ))*100 as TiLeTrungBay 	    	  "+
			"\n 				   from DaiDienKinhDoanh ddkd "+
			"\n 				   inner join #TBLNGAY fg on 1 =1 "+
			"\n 				   inner join NHAPHANPHOI a on a.PK_SEQ = ddkd.NPP_FK "+
			"\n 				left join ddkd_gsbh dg on dg.ddkd_fk = ddkd.pk_seq " +
			"\n 				left join asm asm on asm.Smartid = ddkd.smartid "+
			"\n 				    where  ddkd.TRANGTHAI = 1  and NGAYvt like '%/tltb' ";
			if(nppid.length()>0)
			{
				query +="\n and a.pk_seq in ("+nppid+") ";
			}
			if(nvbh.length()>0)
			{
				query += "\n and ddkd.pk_seq in ("+nvbh+") ";
			}
			
			if(gsbh.length()>0)
			{
				query += "\n and dg.gsbh_fk in ("+gsbh+") ";
			}
			
			if(asm.length()>0)
			{
				query += "\n and asm.pk_seq in ("+asm+") ";
			}
			
			query +="\n 				   union  "+
			"\n 					select ddkd.PK_SEQ, fg.NGAYvt, "+
			"\n 					isnull((select SUM(soluong*giamua) from DONHANG dh inner join  "+
			"\n 						DONHANG_SANPHAM dhsp on dh.PK_SEQ = dhsp.DONHANG_FK where dh.TRANGTHAI <> 2  and dh.isPDA <> 1  "+
			"\n 					 and dh.NGAYNHAP = fg.ngay and dh.ddkd_fk = ddkd.pk_seq  "+
			"\n 						),0)  as SELLOUT "+
			"\n 						 from DaiDienKinhDoanh ddkd "+
			"\n 				   inner join #TBLNGAY fg on 1 =1 "+
			"\n 			  		 inner join NHAPHANPHOI a on a.PK_SEQ = ddkd.NPP_FK "+
			"\n 			    where  ddkd.TRANGTHAI = 1  and NGAYvt like '%/sellnpp' ";
			if(nppid.length()>0)
			{
				query +="\n and a.pk_seq in ("+nppid+") ";
			}
			if(nvbh.length()>0)
			{
				query += "\n and ddkd.pk_seq in ("+nvbh+") ";
			}
			
			if(gsbh.length()>0)
			{
				query += "\n and dg.gsbh_fk in ("+gsbh+") ";
			}
			
			if(asm.length()>0)
			{
				query += "\n and asm.pk_seq in ("+asm+") ";
			}
			
			
			query +="\n 				   union  "+
			"\n 				   select  "+
			"\n 				   ddkd.PK_SEQ, fg.NGAYvt,dbo.PhepChia  "+
			"\n 				   (  "+
			"\n 				   (select COUNT(sanpham_fk) from DONHANG_SANPHAM dhs " +
			"\n						inner join donhang dh on dhs.donhang_fk = dh.pk_seq" +
			"\n						where dh.ngaynhap = fg.ngay and dh.ddkd_fk = ddkd.pk_seq and dh.trangthai <> 2 ),  "+
			"\n 				   ( select count(dh.pk_seq) from donhang dh " +
			"\n 					where dh.ngaynhap =fg.ngay and ddkd.pk_seq = dh.ddkd_fk and dh.trangthai <> 2)) as sku	    	  "+
			"\n 				   from DaiDienKinhDoanh ddkd "+
			"\n 				   inner join #TBLNGAY fg on 1 =1 "+
			"\n 				   inner join NHAPHANPHOI a on a.PK_SEQ = ddkd.NPP_FK "+
			"\n 				left join ddkd_gsbh dg on dg.ddkd_fk = ddkd.pk_seq " +
			"\n 				left join asm asm on asm.Smartid = ddkd.smartid "+
			"\n 				    where  ddkd.TRANGTHAI = 1  and NGAYvt like '%/SKU' ";
			if(nppid.length()>0)
			{
				query +="\n and a.pk_seq in ("+nppid+") ";
			}
			if(nvbh.length()>0)
			{
				query += "\n and ddkd.pk_seq in ("+nvbh+") ";
			}
			
			if(gsbh.length()>0)
			{
				query += "\n and dg.gsbh_fk in ("+gsbh+") ";
			}
			
			if(asm.length()>0)
			{
				query += "\n and asm.pk_seq in ("+asm+") ";
			}
			db.update(query);
			System.out.println("QR1: "+query);

		
			query = 
				"\n 		select PIVOTTABLE.Vung,PIVOTTABLE.Khuvuc,PIVOTTABLE.RSM,PIVOTTABLE.ASM,PIVOTTABLE.GSBH,PIVOTTABLE.NPPTEN,PIVOTTABLE.NVBH, " + subPivot +
				"\n 		 from "+
				"\n 		( "+
				"\n 		select v.Ten as Vung, kv.Ten as Khuvuc,npp.TEN as NPPTen,ddkd.ten as NVBH,b.giatri,c.ngayvt" +
				"\n			,gsbh.ten as GSBH, asm.ten as ASM, asm.ten as RSM  "+
				"\n 		from #TBLDD a  "+
				"\n 		inner join #TABLE1 b on a.ddkd_fk = b.ddkd  "+
				"\n 		inner join #TBLNGAY c on c.NGAYvt = b.ngay "+
				"\n 		inner join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = a.ddkd_fk " +
				"\n 		left join ddkd_gsbh dg on dg.ddkd_fk = ddkd.pk_seq " +
				"\n 		left join giamsatbanhang gsbh on gsbh.pk_seq = dg.gsbh_fk " +
				"\n			left join ASM asm on asm.smartid = ddkd.smartid "+
				"\n 		inner join NHAPHANPHOI npp on npp.PK_SEQ = ddkd.NPP_FK "+
				"\n 		inner join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK "+
				"\n 		inner join VUNG v on v.PK_SEQ = kv.VUNG_FK "+	
				"\n         where 1 =1 ";
				if(vung.length() >0)
				{
					query +="\n and v.pk_seq in ("+vung+")";
				}
				if(khuvuc.length() >0)
				{
					query +="\n and kv.pk_seq in ("+khuvuc+") ";
				}
			    query +="\n 		) st PIVOT (sum(st.giatri) for st.ngayvt IN ("+subIn+") ) AS PIVOTTABLE ";
				
			
			
			System.out.println("Get NVBH ko PDA: \n"+query);
			ResultSet rs = db.get(query);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int socottrongSql = rsmd.getColumnCount();
			
			int countRow = 5;

			/*for( int i =1 ; i <=socottrongSql ; i ++  )
			{
				cell = cells.getCell(countRow,i-1 );cell.setValue(rsmd.getColumnName(i));
				cells.setColumnWidth(i, 20.0f);
				ReportAPI.setCellBackground(cell, new Color(70,130,180), BorderLineType.THIN, true, 0);	
			 
			}*/
			
			
			int stt = 0;
			while(rs.next())
			{
				
				stt++;
			
				for(i =1;i <=socottrongSql ; i ++)
				{
					cell = cells.getCell(countRow,i-1 );
					if(rsmd.getColumnName(i).equals("STT"))
					{					
						cell.setValue(stt);
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
						//System.out.println("STT: "+stt);

					}else
					if(rsmd.getColumnType(i) == Types.DOUBLE || rsmd.getColumnType(i) == Types.INTEGER || rsmd.getColumnType(i) == Types.DECIMAL || rsmd.getColumnType(i) == Types.FLOAT || rsmd.getColumnType(i) == Types.NUMERIC )
					{
						cell.setValue(rs.getDouble(i));
						if(!rsmd.getColumnName(i).equals("SKU"))
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
			fstream.close();
			
			db.update("IF OBJECT_ID('tempdb.dbo.#TBLDD') IS NOT NULL DROP TABLE #TBLDD");
			db.update("IF OBJECT_ID('tempdb.dbo.#TBLNGAY') IS NOT NULL DROP TABLE #TBLNGAY");
			db.update("IF OBJECT_ID('tempdb.dbo.#TABLE1') IS NOT NULL DROP TABLE #TABLE1");
			db.update("IF OBJECT_ID('tempdb.dbo.#BC') IS NOT NULL DROP TABLE #BC");
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
