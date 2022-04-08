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
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

@WebServlet("/BcChiTieuKhachHangSvl")
public class BcChiTieuKhachHangSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public BcChiTieuKhachHangSvl()
	{
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
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
		String nextJSP = "/AHF/pages/Center/SalesrepPerfomanceGroupSku.jsp";
		
		   String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
		    if(view == null)
		    	view = "";
		    
		if(!view.equals("TT"))
		{
			nextJSP = "/AHF/pages/Distributor/BcChiTieuKhachHang.jsp";
			response.sendRedirect(nextJSP);
		}
		else
		{
			 nextJSP = "/AHF/pages/Center/BcChiTieuKhachHang.jsp";
			response.sendRedirect(nextJSP);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		Utility util = new Utility();

		obj.setuserId((String) session.getAttribute("userId") == null ? "" : (String) session.getAttribute("userId"));

		obj.setuserTen((String) session.getAttribute("userTen") == null ? "" : (String) session.getAttribute("userTen"));

		obj.setnppId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")) == null ? "" : util.antiSQLInspection(request.getParameter("nppId"))));

		obj.setkenhId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")) == null ? "" : util.antiSQLInspection(request.getParameter("kenhId"))));

		obj.setdvkdId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")) == null ? "" : util.antiSQLInspection(request.getParameter("dvkdId"))));

		obj.setMonth(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("month")) == null ? "" : util.antiSQLInspection(request.getParameter("month"))));

		obj.setYear(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("year")) == null ? "" : util.antiSQLInspection(request.getParameter("year"))));

		obj.setvungId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")) == null ? "" : util.antiSQLInspection(request.getParameter("vungId"))));

		obj.setkhuvucId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")) == null ? "" : util.antiSQLInspection(request.getParameter("khuvucId"))));

		obj.setdvdlId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvdlId")) == null ? "" : util.antiSQLInspection(request.getParameter("dvdlId"))));

		obj.setDdkd(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdId")) == null ? "" : util.antiSQLInspection(request.getParameter("ddkdId"))));

		obj.setgsbhId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("gsbhId")) == null ? "" : util.antiSQLInspection(request.getParameter("gsbhId"))));
		
		
		obj.settungay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays")) == null ? "" : util.antiSQLInspection(request.getParameter("Sdays"))));
		
		obj.setdenngay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays")) == null ? "" : util.antiSQLInspection(request.getParameter("Edays"))));


		String[] fieldsHien = request.getParameterValues("fieldsHien");
		obj.setFieldShow(fieldsHien);

		String userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
		String view=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
		if(view == null)
			view = "";
		
	    String nppId ="";
		if(view.equals("TT"))
		{
			 nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
			if (nppId == null)
				nppId = "";
			obj.setnppId(nppId);
		}else
		{
			nppId=util.getIdNhapp(userId);
			obj.setnppId(nppId);
		}
     	
	

		String action = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")));
		if (action.equals("Taomoi"))
		{
			try
			{
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BcChiTieuKhachHang_" + util.setTieuDe(obj) + ".xlsm");
				OutputStream out = response.getOutputStream();
				
				String query = setQuery(obj);
				CreatePivotTable(out, obj, query);
			} catch (Exception ex)
			{
				ex.printStackTrace();
				
				obj.init();
				session.setAttribute("obj", obj);
				session.setAttribute("userId", obj.getuserId());
				obj.setMsg(ex.getMessage());
			}
		} 
		else if (action.equals("ChiTiet"))
		{
			try
			{
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BcChiTieuKhachHang_ChiTiet_" + util.setTieuDe(obj) + ".xlsm");
				OutputStream out = response.getOutputStream();
				
				String query = setQuery_ChiTiet(obj);
				CreatePivotTable_ChiTiet(out, obj, query);
			} catch (Exception ex)
			{
				ex.printStackTrace();
				
				obj.init();
				session.setAttribute("obj", obj);
				session.setAttribute("userId", obj.getuserId());
				obj.setMsg(ex.getMessage());
			}
		}
		
		else 
		{
			obj.init();
			session.setAttribute("obj", obj);
			session.setAttribute("userId", obj.getuserId());
		}
		if(!view.equals("TT"))
		{
			String	nextJSP = "/AHF/pages/Distributor/BcChiTieuKhachHang.jsp";
			response.sendRedirect(nextJSP);
		}
		else
		{
			String	 nextJSP = "/AHF/pages/Center/BcChiTieuKhachHang.jsp";
			response.sendRedirect(nextJSP);
		}
	}
	
	private void CreatePivotTable_ChiTiet(OutputStream out, IStockintransit obj, String query) throws Exception
  {
		try
		{
			//String chuoi = getServletContext().getInitParameter("path") + "\\BcChiTieuKhachHang_ChiTiet.xlsm";
			//FileInputStream fstream = new FileInputStream(chuoi);
			
			File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\BcChiTieuKhachHang_ChiTiet.xlsm");
			FileInputStream fstream = new FileInputStream(f) ;
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

			CreateStaticHeader_ChiTiet(workbook, obj);
			FillData_ChiTiet(workbook, obj.getFieldShow(), query, obj);
			workbook.save(out);
			fstream.close();
		} catch (Exception ex)
		{
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}
	  
  }

	private void FillData_ChiTiet(Workbook workbook, String[] fieldShow, String query, IStockintransit obj) throws Exception
  {
		Integer tungay = Integer.parseInt(obj.gettungay().substring(8,obj.gettungay().trim().length()));
    Integer denngay = Integer.parseInt(obj.getdenngay().substring(8,obj.getdenngay().trim().length()));
     query = "";
     int indexRow = 2;
     dbutils db = new dbutils();
     ResultSet		rs;
    for(int k=tungay; k<=denngay; k++ )
    {
    	String str = "00"+k;
    	String d = str.substring(str.length()-2, str.length());
    	String TuNgay=""+ obj.gettungay().substring(0,8)+d + "" ;
    	String NgayDauThang=" ( SELECT CONVERT(VARCHAR(10),DATEADD(dd,-(DAY( '"+TuNgay+"'  )-1), '"+TuNgay+"'  ),20) AS Date_Value) ";

    	query=
			 "   SELECT   '"+TuNgay+"' AS NGAY,V.TEN AS VUNGTEN,KV.TEN AS KVTEN,NPP.MA AS NPPMA,NPP.TEN AS NPPTEN,DDKD.MANHANVIEN AS ddkdMa,  "+   
		   "   	DDKD.TEN AS DDKDTEN,   KH.TEN AS KHTEN,KH.SMARTID,KH.DIACHI,  "+   
		   "   		TUANTRUOC.SPTEN AS SPTuanTruoc ,TUANHIENTAI.SPTEN AS SPTuanHienTai ,  "+   
		   "   			TUANHIENTAI.SOLUONG AS SLHIENTAI ,  "+   
		   "   		TUANHIENTAI.THANHTIEN AS TTHIENTAI,TUANTRUOC.SOLUONG AS SLTUANTRUOC,TUANTRUOC.THANHTIEN AS TTTUANTRUOC  "+   
		   "   FROM  "+   
		   "   (  "+   
		   "   	select b.KHACHHANG_FK,c.DDKD_FK  "+   
		   "   	FROM KHACHHANG a inner join KHACHHANG_TUYENBH b on b.KHACHHANG_FK=a.PK_SEQ  "+   
		   "   	inner join TUYENBANHANG c on c.PK_SEQ=b.TBH_FK  "+   
		   "   	WHERE A.LAT IS NOT NULL AND A.LONG IS NOT NULL  ";
		   if(obj.getnppId().length()>0)
		   {
		  	 query+=" AND A.NPP_FK='"+obj.getnppId()+"'";
		   }
		   if(obj.getDdkd().length()>0)
		   {
		  	 query+=" AND C.DDKD_fk='"+obj.getDdkd()+"'";
		   }
		   
		   
		   query+=
		   
		   
		   "   	GROUP BY B.KHACHHANG_FK,C.DDKD_FK  "+   
		   "   	  "+   
		   "   	UNION   "+   
		   "   	SELECT DH.KHACHHANG_FK,DH.DDKD_FK FROM DONHANG DH   "+   
		   "   	WHERE DH.TRANGTHAI=1 AND  DATEDIFF(DAY,DH.NGAYNHAP,'"+TuNgay+"'   ) = 7   ";
		   
				 if(obj.getnppId().length()>0)
				 {
					 query+=" AND DH.NPP_FK='"+obj.getnppId()+"'";
				 }
				 if(obj.getDdkd().length()>0)
				 {
					 query+=" AND DH.DDKD_fk='"+obj.getDdkd()+"'";
				 }
		   
		   query+=
		   "   	GROUP BY DH.KHACHHANG_FK,DH.DDKD_FK  "+   
		   "   	  "+   
		   "   	UNION   "+   
		   "   	SELECT DH.KHACHHANG_FK,DH.DDKD_FK FROM DONHANG DH   "+   
		   "   	WHERE DH.TRANGTHAI=1 AND   DH.NGAYNHAP>= "+NgayDauThang+" and DH.NGAYNHAP<= '"+TuNgay+"'  ";
		   
				if(obj.getnppId().length()>0)
				{
					 query+=" AND DH.NPP_FK='"+obj.getnppId()+"'";
				}
				if(obj.getDdkd().length()>0)
				{
					 query+=" AND DH.DDKD_fk='"+obj.getDdkd()+"'";
				}

		   query+=
		   "   	GROUP BY DH.KHACHHANG_FK,DH.DDKD_FK  "+   
		   "     "+   
		   "   )as KhachHang  "+   
		   "   left join   "+   
		   "   (  "+   
		   "   		SELECT  SP.MA +'__'+SP.TEN AS SPTEN,  "+   
		   "   			DH.DDKD_FK, DH.KHACHHANG_FK ,DHSP.SANPHAM_FK,SUM(DHSP.SOLUONG) AS SOLUONG, SUM(DHSP.SOLUONG * DHSP.GIAMUA) AS THANHTIEN,  "+   
		   "   			N'Tuần trước'   as Type  "+   
		   "   		FROM DONHANG DH    "+   
		   "   			INNER JOIN DONHANG_SANPHAM DHSP ON DHSP.DONHANG_FK = DH.PK_SEQ    "+   
		   "   			INNER JOIN KHACHHANG KH ON KH.PK_SEQ = DH.KHACHHANG_FK   "+   
		   "   		INNER JOIN SANPHAM SP ON SP.PK_SEQ=DHSP.SANPHAM_FK  "+   
		   "   		WHERE DH.TRANGTHAI = 1 and  DATEDIFF(DAY,DH.NGAYNHAP,'"+TuNgay+"'    ) = 7   "; 
		   
				if(obj.getnppId().length()>0)
				{
					 query+=" AND DH.NPP_FK='"+obj.getnppId()+"'";
				}
				if(obj.getDdkd().length()>0)
				{
					 query+=" AND DH.DDKD_fk='"+obj.getDdkd()+"'";
				}
		   query+=
		   
		   "   		GROUP BY SP.MA +'__'+SP.TEN,DH.KHACHHANG_FK ,DHSP.SANPHAM_FK,DH.DDKD_FK  "+   
		   "   ) TUANTRUOC on TUANTRUOC.DDKD_FK=KhachHang.DDKD_FK and TuanTruoc.KHACHHANG_FK=KhachHang.KHACHHANG_FK  "+   
		   "   LEFT JOIN   "+   
		   "   (  "+   
		   "    SELECT    SP.MA +'__'+SP.TEN AS SPTEN,DH.DDKD_FK,DH.KHACHHANG_FK ,DHSP.SANPHAM_FK,  "+   
		   "   	SUM(DHSP.SOLUONG) AS SOLUONG, SUM(DHSP.SOLUONG * DHSP.GIAMUA) AS THANHTIEN,N'Tuần hiện tại'   as Type  "+   
		   "   	 FROM DONHANG DH    "+   
		   "   	 INNER JOIN DONHANG_SANPHAM DHSP ON DHSP.DONHANG_FK = DH.PK_SEQ    "+   
		   "   	 INNER JOIN KHACHHANG KH ON KH.PK_SEQ = DH.KHACHHANG_FK   "+   
		   "   	 INNER JOIN SANPHAM SP ON SP.PK_SEQ=DHSP.SANPHAM_FK  "+   
		   "   	 WHERE DH.TRANGTHAI = 1 and  DH.NGAYNHAP>= "+NgayDauThang+" and DH.NGAYNHAP<= '"+TuNgay+"' ";
		   if(obj.getnppId().length()>0)
				{
					 query+=" AND DH.NPP_FK='"+obj.getnppId()+"'";
				}
				if(obj.getDdkd().length()>0)
				{
					 query+=" AND DH.DDKD_fk='"+obj.getDdkd()+"'";
				}
		   
		   query+=
		   "   	GROUP BY SP.MA +'__'+SP.TEN, DH.KHACHHANG_FK ,DHSP.SANPHAM_FK,DH.DDKD_FK  "+   
		   "   ) AS TUANHIENTAI ON TUANHIENTAI.KHACHHANG_FK=TUANTRUOC.KHACHHANG_FK AND TUANHIENTAI.SANPHAM_FK=TUANTRUOC.SANPHAM_FK	  "+   
		   "   LEFT JOIN KHACHHANG KH ON KH.PK_SEQ= KhachHang.KHACHHANG_FK  "+   
		   "   LEFT JOIN DAIDIENKINHDOANH DDKD ON DDKD.PK_SEQ = KhachHang.DDKD_FK  "+   
		   "   LEFT JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ=DDKD.NPP_FK  "+   
		   "   LEFT JOIN KHUVUC KV ON KV.PK_SEQ=NPP.KHUVUC_FK  "+   
		   "   LEFT JOIN VUNG V ON V.PK_SEQ=KV.VUNG_FK  "+   
		   "   WHERE  1=1 ";
		   
		   if(obj.getnppId().length()>0)
				{
					 query+=" AND NPP.pk_Seq='"+obj.getnppId()+"'";
				}
				if(obj.getDdkd().length()>0)
				{
					 query+=" AND DDKD.pk_Seq='"+obj.getDdkd()+"'";
				}
				if(obj.getvungId().length()>0)
				{
					 query+=" AND V.pk_Seq='"+obj.getvungId()+"'";
				}
				if(obj.getkhuvucId().length()>0)
				{
					 query+=" AND kv.pk_Seq='"+obj.getkhuvucId()+"'";
				}
		   
    	
    	Hashtable<Integer, String> htb = this.htbValueCell();
  		Worksheets worksheets = workbook.getWorksheets();
  		Worksheet worksheet = worksheets.getSheet(0);
  		Cells cells = worksheet.getCells();

  		rs = db.get(query);
  		
  		System.out.print("[Query]"+query);
  		try
  		{
  			Cell cell = null;
  			while (rs.next())
  			{
  				int j = 1;
  				cell = cells.getCell(htb.get(j) + Integer.toString(indexRow)); cell.setValue(rs.getString("Ngay")); j++;
  				cell = cells.getCell(htb.get(j) + Integer.toString(indexRow)); cell.setValue(rs.getString("vungTen"));j++;
  				cell = cells.getCell(htb.get(j) + Integer.toString(indexRow)); cell.setValue(rs.getString("kvTen"));j++;
  				cell = cells.getCell(htb.get(j) + Integer.toString(indexRow)); cell.setValue(rs.getString("nppMa"));j++;
  				cell = cells.getCell(htb.get(j) + Integer.toString(indexRow)); cell.setValue(rs.getString("nppTen"));j++;
  				cell = cells.getCell(htb.get(j) + Integer.toString(indexRow)); cell.setValue(rs.getString("ddkdMa")); j++;
  				cell = cells.getCell(htb.get(j) + Integer.toString(indexRow)); cell.setValue(rs.getString("ddkdTen")); 		j++;
  				cell = cells.getCell(htb.get(j) + Integer.toString(indexRow));cell.setValue(rs.getString("SmartId"));j++;
  				cell = cells.getCell(htb.get(j) + Integer.toString(indexRow));cell.setValue(rs.getString("khten"));j++;
  				cell = cells.getCell(htb.get(j) + Integer.toString(indexRow));cell.setValue(rs.getString("DiaChi"));j++;
  				
  				cell = cells.getCell(htb.get(j) + Integer.toString(indexRow));cell.setValue(rs.getString("SPTuanHienTai")==null?" " :rs.getString("SPTuanHienTai"));j++;
  				cell = cells.getCell(htb.get(j) + Integer.toString(indexRow));cell.setValue(rs.getDouble("slHienTai"));j++;
  				cell = cells.getCell(htb.get(j) + Integer.toString(indexRow));cell.setValue(rs.getDouble("ttHienTai"));j++;
  				
  				cell = cells.getCell(htb.get(j) + Integer.toString(indexRow));cell.setValue( rs.getString("SPTuanTruoc")==null?" ":rs.getString("SPTuanTruoc"));j++;
  				cell = cells.getCell(htb.get(j) + Integer.toString(indexRow));cell.setValue(rs.getDouble("slTuanTruoc"));j++;
  				cell = cells.getCell(htb.get(j) + Integer.toString(indexRow));cell.setValue(rs.getDouble("ttTuanTruoc"));j++;
  				indexRow++;
  			}
  			if (indexRow == 2)
  			{
  				throw new Exception("Không có báo cáo với điều kiện đã chọn !");
  			}
  			 if (rs != null)
  					rs.close();
  		} catch (Exception err)
  		{
  			err.printStackTrace();
  			throw new Exception("Khong the tao duoc bao cao trong thoi gian nay. Error :" + err.toString());
  		}
    }
    if (indexRow == 2)
		{
			throw new Exception("Không có báo cáo với điều kiện đã chọn !");
		}
		if (db != null)
		{
			db.shutDown();
		}
  }

	private void CreateStaticHeader_ChiTiet(Workbook workbook, IStockintransit obj)
  {
		Hashtable<Integer, String> htb = this.htbValueCell();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");
		Cells cells = worksheet.getCells();

		 Style style;
	    Font font = new Font();
	    font.setColor(Color.RED);//mau chu
	    font.setSize(16);// size chu
	   	font.setBold(true);
	   	
		 cells.setRowHeight(0, 20.0f);
	    Cell cell = cells.getCell("A1");
	    style = cell.getStyle();
	    style.setFont(font); 
	    style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu 	              

		
	  String tieude = "BÁO CÁO DOANH SỐ KHÁCH HÀNG";
    ReportAPI.getCellStyle(cell,Color.RED, true, 14, tieude);
		
		
		cells.setRowHeight(3, 18.0f);
		cell = cells.getCell("A2");

		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Từ Ngày : " + obj.gettungay() + "");

		cells.setRowHeight(3, 18.0f);
		cell = cells.getCell("A3");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Đến Ngày : " + obj.getdenngay() + "");

		cells.setRowHeight(4, 18.0f);
		cell = cells.getCell("A4");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));

		cells.setRowHeight(5, 18.0f);
		cell = cells.getCell("A5");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getuserTen());

		dbutils db = new dbutils();
		
		int i = 1;

		cell = cells.getCell(htb.get(i) + "1");cell.setValue("Ngay");cell.setStyle(style);i++;
		cell = cells.getCell(htb.get(i) + "1");cell.setValue("Vung");cell.setStyle(style);i++;
		cell = cells.getCell(htb.get(i) + "1");cell.setValue("KhuVuc");cell.setStyle(style);i++;
		cell = cells.getCell(htb.get(i) + "1");cell.setValue("MaNhaPhanPhoi");cell.setStyle(style);i++;
		cell = cells.getCell(htb.get(i) + "1");cell.setValue("NhaPhanPhoi");cell.setStyle(style);i++;
		cell = cells.getCell(htb.get(i) + "1");cell.setValue("MaDaiDienKinhDoanh");cell.setStyle(style);i++;
		cell = cells.getCell(htb.get(i) + "1");cell.setValue("DaiDienKinhDoanh");cell.setStyle(style);i++;
		cell = cells.getCell(htb.get(i) + "1");cell.setValue("MaKhachHang");cell.setStyle(style);i++;
		cell = cells.getCell(htb.get(i) + "1");cell.setValue("KhachHang");cell.setStyle(style);i++;
		cell = cells.getCell(htb.get(i) + "1");cell.setValue("DiaChi");cell.setStyle(style);i++;
		
		cell = cells.getCell(htb.get(i) + "1");cell.setValue("SanPhamBan(TuanTruoc)");cell.setStyle(style);i++;
		cell = cells.getCell(htb.get(i) + "1");cell.setValue("SoLuongBan(TuanTruoc)");cell.setStyle(style);i++;
		cell = cells.getCell(htb.get(i) + "1");cell.setValue("DoanhSoBan(TuanTruoc)");cell.setStyle(style);i++;
		
		cell = cells.getCell(htb.get(i) + "1");cell.setValue("SanPhamBan(TuDauThang)");cell.setStyle(style);i++;
		cell = cells.getCell(htb.get(i) + "1");cell.setValue("SoLuongBan(TuDauThang)");cell.setStyle(style);i++;
		cell = cells.getCell(htb.get(i) + "1");cell.setValue("DoanhSoBan(TuDauThang)");cell.setStyle(style);i++;
		
		
		db.shutDown();
		cell = cells.getCell("M1");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, (26 * 4 + i - 1) + "");
	  
  }

	private String setQuery_ChiTiet(IStockintransit obj)
  {
		Integer tungay = Integer.parseInt(obj.gettungay().substring(8,obj.gettungay().trim().length()));
    Integer denngay = Integer.parseInt(obj.getdenngay().substring(8,obj.getdenngay().trim().length()));
    String query = "";
    for(int k=tungay; k<=denngay; k++ )
    {
    	String str = "00"+k;
    	String d = str.substring(str.length()-2, str.length());
    	String TuNgay=""+ obj.gettungay().substring(0,8)+d + "" ;
    	String NgayDauThang=" ( SELECT CONVERT(VARCHAR(10),DATEADD(dd,-(DAY( '"+TuNgay+"'  )-1), '"+TuNgay+"'  ),20) AS Date_Value) ";

    	
    	query=
  				"SELECT   '"+TuNgay+"' AS NGAY,V.TEN AS VUNGTEN,KV.TEN AS KVTEN,NPP.MA AS NPPMA,NPP.TEN AS NPPTEN,DDKD.MANHANVIEN AS DDDKDMA,  \n"+   
  				"   	DDKD.TEN AS DDKDTEN,   KH.TEN AS KHTEN,KH.SMARTID,KH.DIACHI,  \n"+   
  				"   		SP.MA AS SPMA,SP.TEN AS SPTEN,TUANHIENTAI.SOLUONG AS SLHIENTAI ,  \n"+   
  				"   		TUANHIENTAI.THANHTIEN AS TTHIENTAI,TUANTRUOC.SOLUONG AS SLTUANTRUOC,TUANTRUOC.THANHTIEN AS TTTUANTRUOC \n"+   
  				"   FROM  \n"+   
  				"   (  \n"+   
  				"   	select b.KHACHHANG_FK,c.DDKD_FK  \n"+   
  				"   	FROM KHACHHANG a inner join KHACHHANG_TUYENBH b on b.KHACHHANG_FK=a.PK_SEQ  \n"+   
  				"   	inner join TUYENBANHANG c on c.PK_SEQ=b.TBH_FK  \n"+   
  				"   	GROUP BY B.KHACHHANG_FK,C.DDKD_FK  \n"+ 
  				"   	UNION   \n"+   
  				"   	SELECT DH.KHACHHANG_FK,DH.DDKD_FK FROM DONHANG DH   \n"+   
  				"   	WHERE DH.TRANGTHAI=1 AND  DATEDIFF(DAY,DH.NGAYNHAP, '"+TuNgay+"'   ) = 7   \n"+   
  				"   	GROUP BY DH.KHACHHANG_FK,DH.DDKD_FK  \n"+   
  				"   	UNION   \n"+   
  				"   	SELECT DH.KHACHHANG_FK,DH.DDKD_FK FROM DONHANG DH   \n"+   
  				"   	WHERE DH.TRANGTHAI=1 AND   DH.NGAYNHAP>= '"+NgayDauThang+"'  and DH.NGAYNHAP<= '"+TuNgay+"' \n"+   
  				"   	GROUP BY DH.KHACHHANG_FK,DH.DDKD_FK  \n"+   
  				"   )as KhachHang  \n"+  
  				"   left join   \n"+  
  				"   (  \n"+  
  				"   		SELECT DH.DDKD_FK, DH.KHACHHANG_FK ,DHSP.SANPHAM_FK,SUM(DHSP.SOLUONG) AS SOLUONG, SUM(DHSP.SOLUONG * DHSP.GIAMUA) AS THANHTIEN,  \n"+   
  				"   			N'Tuần trước'   as Type  \n"+   
  				"   		FROM DONHANG DH    \n"+   
  				"   			INNER JOIN DONHANG_SANPHAM DHSP ON DHSP.DONHANG_FK = DH.PK_SEQ    \n"+   
  				"   			INNER JOIN KHACHHANG KH ON KH.PK_SEQ = DH.KHACHHANG_FK   \n"+   
  				"   		WHERE DH.TRANGTHAI = 1 and  DATEDIFF(DAY,DH.NGAYNHAP, '"+TuNgay+"'   ) = 7   \n"+   
  				"   		GROUP BY DH.KHACHHANG_FK ,DHSP.SANPHAM_FK,DH.DDKD_FK  \n"+   
  				"   ) TUANTRUOC on TUANTRUOC.DDKD_FK=KhachHang.DDKD_FK and TuanTruoc.KHACHHANG_FK=KhachHang.KHACHHANG_FK  \n"+   
  				"   LEFT JOIN   \n"+  
  				"   (  \n"+  
  				"    SELECT DH.DDKD_FK,DH.KHACHHANG_FK ,DHSP.SANPHAM_FK,  \n"+   
  				"   	SUM(DHSP.SOLUONG) AS SOLUONG, SUM(DHSP.SOLUONG * DHSP.GIAMUA) AS THANHTIEN,N'Tuần hiện tại'   as Type  \n"+   
  				"   	 FROM DONHANG DH    \n"+   
  				"   	 INNER JOIN DONHANG_SANPHAM DHSP ON DHSP.DONHANG_FK = DH.PK_SEQ    \n"+   
  				"   	 INNER JOIN KHACHHANG KH ON KH.PK_SEQ = DH.KHACHHANG_FK   \n"+   
  				"   	 WHERE DH.TRANGTHAI = 1 and  DH.NGAYNHAP>= '"+NgayDauThang+"'  and DH.NGAYNHAP<= '"+TuNgay+"'  \n"+   
  				"   	GROUP BY DH.KHACHHANG_FK ,DHSP.SANPHAM_FK,DH.DDKD_FK  \n"+   
  				"   ) AS TUANHIENTAI ON TUANHIENTAI.KHACHHANG_FK=TUANTRUOC.KHACHHANG_FK AND TUANHIENTAI.SANPHAM_FK=TUANTRUOC.SANPHAM_FK	  \n"+   
  				"   LEFT JOIN SANPHAM SP ON SP.PK_SEQ=ISNULL(TUANTRUOC.SANPHAM_FK,TUANHIENTAI.SANPHAM_FK)  \n"+  
  				"   LEFT JOIN KHACHHANG KH ON KH.PK_SEQ= KhachHang.KHACHHANG_FK  \n"+  
  				"   LEFT JOIN DAIDIENKINHDOANH DDKD ON DDKD.PK_SEQ = KhachHang.DDKD_FK  \n"+   
  				"   LEFT JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ=DDKD.NPP_FK  \n"+  
  				"   LEFT JOIN KHUVUC KV ON KV.PK_SEQ=NPP.KHUVUC_FK  \n"+  
  				"   LEFT JOIN VUNG V ON V.PK_SEQ=KV.VUNG_FK \n"+
  				"WHERE 1=1 " ;
    }
	  return query;
  }

	private String setQuery(IStockintransit obj)
	{
		String query = 
			"	DROP TABLE TEMP_BC  "+ 
			"	CREATE TABLE TEMP_BC  "+
			"	(    "+
			"		DDKD_FK NUMERIC(18,0),  "+
			"		KHACHHANG_FK NUMERIC(18,0),  "+
			"		NGAYID INT,   "+
			"		NGAY VARCHAR(10),   "+
			"		NgayDauThang varchar(10),  "+
			"		SoTuanTrongThang int,  "+
			"		TuanHienTai int,   "+
			"		CONSTRAINT UNI_TEMP_BC UNIQUE(KHACHHANG_FK,DDKD_FK,NGAY,NGAYID)  "+ 
			"	 )  ";
		
		query +=
		" declare @ddkdid numeric(18,0),@tbhid numeric(18,0),@ngay varchar(10),  "+
		" @ngaycuoithang varchar(10),@sotuantrongthang int,@tuanhientai int,@NgayDauThang varchar(10)  "+
		" declare @TuNgay varchar(10),@DenNgay varchar(10)   "+
		" set @TuNgay='"+obj.gettungay()+"'   "+
		"	set @DenNgay='"+obj.getdenngay()+"'  "+
		" while(@TuNgay<=@DenNgay)    "+
	  "	BEGIN    "+
		" 	set @NgayDauThang= ( SELECT CONVERT(VARCHAR(10),DATEADD(dd,-(DAY(@TuNgay)-1),@TuNgay),20) AS Date_Value)   "+
		"  	set @ngaycuoithang= ( select  convert(varchar(10), dateadd(month,1, @TuNgay  )- day(dateadd(month,1, @TuNgay  ) ),20 ))   "+ 
		" 	select @sotuantrongthang=( select datepart(week, @ngaycuoithang)  - datepart(week, dateadd(mm, datediff(mm,0, @ngaycuoithang  ), 0)) + 1 )   "+
		" 	select @tuanhientai=  datepart(week, @TuNgay)  - datepart(week, dateadd(mm, datediff(mm,0, @TuNgay), 0)) + 1   "+ 
		"		INSERT INTO TEMP_BC(KHACHHANG_FK,DDKD_FK,NGAY,NGAYID,TuanHienTai,SoTuanTrongThang,NgayDauThang)   "+
		"		SELECT  DISTINCT B.KHACHHANG_FK,DDKD_FK,@TuNgay,datepart(dw, @TuNgay) ,@tuanhientai,@sotuantrongthang,@NgayDauThang   "+
		"		FROM KHACHHANG a inner join KHACHHANG_TUYENBH b on b.KHACHHANG_FK=a.PK_SEQ   "+
		"		inner join TUYENBANHANG c on c.PK_SEQ=b.TBH_FK   "+
		"		WHERE 1=1  and a.LAT is not null and a.LONG is not null and a.trangthai=1 ";
		
		if(obj.getkenhId().length()>0)
			query+=" and a.kbh_fk = "+obj.getkenhId()+" ";
		
		if(obj.getnppId().length()>0)
			query+=" and a.npp_fk='"+obj.getnppId()+"' ";
		if(obj.getDdkd().length()>0)
			query+=" and c.ddkd_fk ='"+obj.getDdkd()+"' ";
		/*if(obj.getTbhId().length()>0)
			query+=" and c.pk_seq ='"+obj.getTbhId()+"' ";*/
		query+=
		"	SET @TuNgay=(select convert(varchar(10),DATEADD(DAY,1,@TuNgay),20) )   "+ 	
		"	END   ";

		
		query += 
	 "   select  TEMP_BC.NGAY,e.ma as nppma,e.ten as nppten,d.manhanvien as ddkdma,d.ten as ddkdten,c.NGAYID,  "+   
   "   	kh.pk_seq as khId,kh.SMARTID , isnull(kh.ten, '') as ten, isnull(kh.diachi, '') as diachi    "+   
   "   ,ct.chitieu, isnull(mtngay.chitieu, 0) as muctieungay  ,  "+   
   "   (  "+   
   "     "+   
   "   	select  datediff(day, max(ngaynhap),TEMP_BC.NGAY) as songay    "+   
   "   	from donhang     "+   
   "   	where trangthai != 2 and datediff(month, ngaynhap,TEMP_BC.NGAY  ) = 0    "+   
   "   	AND KHACHHANG_FK=TEMP_BC.KHACHHANG_FK AND DDKD_FK=TEMP_BC.DDKD_FK  "+   
   "   )as thoigianmuahang ,  "+   
   "   (	  "+   
   "   		SELECT SUM(DHSP.SOLUONG*DHSP.GIAMUA)/COUNT( DISTINCT DATEPART(MONTH,DH.NGAYNHAP)) AS DOANHSOTB  "+   
   "   		FROM DONHANG DH INNER JOIN DONHANG_SANPHAM DHSP ON DHSP.DONHANG_FK=DH.PK_SEQ  "+   
   "   		WHERE DH.TRANGTHAI=1 AND  DATEDIFF(MONTH,DH.NGAYNHAP, TEMP_BC.NGAY  ) >=0    "+   
   "   			AND DATEDIFF(MONTH,DH.NGAYNHAP, TEMP_BC.NGAY  )<6 AND  DH.DDKD_FK=TEMP_BC.DDKD_FK  "+   
   "   		GROUP BY DH.DDKD_FK  "+   
   "   )as tbDoanhSoNvbh ,  "+   
   "   (  "+   
   "   	SELECT SUM(DHSP.SOLUONG*DHSP.GIAMUA)/COUNT( DISTINCT DATEPART(MONTH,DH.NGAYNHAP)) AS DOANHSOTB  "+   
   "   	FROM DONHANG DH INNER JOIN DONHANG_SANPHAM DHSP ON DHSP.DONHANG_FK=DH.PK_SEQ  "+   
   "   	WHERE DH.TRANGTHAI=1 AND  DATEDIFF(MONTH,DH.NGAYNHAP,TEMP_BC.NGAY    ) >=0    "+   
   "   	AND DATEDIFF(MONTH,DH.NGAYNHAP, TEMP_BC.NGAY   )<6  AND DH.KHACHHANG_FK=TEMP_BC.KHACHHANG_FK AND DH.DDKD_FK=TEMP_BC.DDKD_FK  "+   
   "   	GROUP BY DH.KHACHHANG_FK  "+   
   "   ) as tbDoanhSoKH,  "+   
   "     "+   
   "   (  "+   
   "   	select sum(dhsp.soluong*dhsp.giamua) as doanhso    "+   
   "   	from donhang dh inner join donhang_sanpham dhsp on dhsp.donhang_fk=dh.pk_seq    "+   
   "   	where trangthai != 2  AND KHACHHANG_FK=TEMP_BC.KHACHHANG_FK AND DDKD_FK=TEMP_BC.DDKD_FK   "+   
   "   		and dh.NGAYNHAP>=TEMP_BC.NgayDauThang  and dh.NGAYNHAP<=TEMP_BC.NGAY  "+   
   "   	group by dh.ddkd_fk,dh.KHACHHANG_FK  "+   
   "   )as dsTong,  "+   
   "   (  "+   
   "   	select sum(dhsp.soluong*dhsp.giamua) as doanhso    "+   
   "   	from donhang dh inner join donhang_sanpham dhsp on dhsp.donhang_fk=dh.pk_seq    "+   
   "   	where trangthai != 2  AND KHACHHANG_FK=TEMP_BC.KHACHHANG_FK AND DDKD_FK=TEMP_BC.DDKD_FK   "+   
   "   		 and dh.NGAYNHAP=TEMP_BC.NGAY  "+   
   "   	group by dh.ddkd_fk,dh.KHACHHANG_FK  "+   
   "   ) as DsNgay,TEMP_BC.SoTuanTrongThang,TEMP_BC.TuanHienTai  "+   
   "   from  TEMP_BC INNER JOIN  khachhang kh  ON kh.PK_SEQ=TEMP_BC.KHACHHANG_FK  "+   
   "   inner join khachhang_tuyenbh b on kh.pk_seq=b.khachhang_fk  "+   
   "   inner join tuyenbanhang c on c.pk_seq=b.tbh_fk  "+   
   "   inner join daidienkinhdoanh d on d.pk_seq=c.ddkd_fk  "+   
   "   inner join nhaphanphoi e on e.pk_seq=d.npp_fk   "+   
   "   left join    "+   
   "   (    "+   
   "   	select b.ddkd_fk,sum(b.chitieu) as chitieu ,a.THANG,a.NAM    "+   
   "   	from chitieunpp a     "+   
   "   		inner join chitieunpp_ddkd b on b.chitieunpp_fk=a.pk_seq    "+   
   "   group by b.ddkd_fk ,a.THANG,a.NAM   "+   
   "   )ct on ct.ddkd_fk=c.DDKD_FK  and ct.NAM=SUBSTRING(TEMP_BC.NGAY,1,4)  "+   
   "   and ct.THANG=SUBSTRING(TEMP_BC.NGAY,6,2)  "+   
   "left join khuvuc kv on kv.pk_Seq=e.khuvuc_fk "+ 
   "left join vung v on v.pk_Seq=kv.vung_Fk " + 
   "   left join khachhang_muctieungay mtngay on kh.pk_seq = mtngay.khachhang_fk   "+   
   "   and mtngay.ngay = replace(convert(nvarchar(10), TEMP_BC.NGAY , 102) , '.', '-' )    "+   
   "   where kh.trangthai = 1   ";
	
		if(obj.getkhuvucId().length()>0)
		{
			query+=" and kv.pk_seq='"+obj.getkhuvucId()+"' ";
		}
		
		if(obj.getvungId().length()>0)
		{
			query+=" and v.pk_seq='"+obj.getvungId()+"' ";
		}
			
			
			System.out.println("1.Query SalesRep : " + query);
		return query;
	}
	
	private void CreatePivotTable(OutputStream out, IStockintransit obj, String query) throws Exception
	{
		try
		{
			//String chuoi = getServletContext().getInitParameter("path") + "\\BcChiTieuKhachHang.xlsm";
			//FileInputStream fstream = new FileInputStream(chuoi);
			File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\BcChiTieuKhachHang.xlsm");
			FileInputStream fstream = new FileInputStream(f) ;
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

			CreateStaticHeader(workbook, obj);
			FillData(workbook, obj.getFieldShow(), query, obj);
			workbook.save(out);
			fstream.close();
		} catch (Exception ex)
		{
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}
	}
	private void CreateStaticHeader(Workbook workbook, IStockintransit obj)
	{
		Hashtable<Integer, String> htb = this.htbValueCell();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");
		Cells cells = worksheet.getCells();

		 Style style;
	    Font font = new Font();
	    font.setColor(Color.RED);//mau chu
	    font.setSize(16);// size chu
	   	font.setBold(true);
	   	
		 cells.setRowHeight(0, 20.0f);
	    Cell cell = cells.getCell("A1");
	    style = cell.getStyle();
	    style.setFont(font); 
	    style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu 	              

		
	  String tieude = "BÁO CÁO CHỈ TIÊU KHÁCH HÀNG";
   ReportAPI.getCellStyle(cell,Color.RED, true, 14, tieude);

   cells.setRowHeight(3, 18.0f);
		cell = cells.getCell("A2");

		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Từ Ngày : " + obj.gettungay() + "");

		cells.setRowHeight(3, 18.0f);
		cell = cells.getCell("A3");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Đến Ngày : " + obj.getdenngay() + "");

		cells.setRowHeight(4, 18.0f);
		cell = cells.getCell("A4");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));

		cells.setRowHeight(5, 18.0f);
		cell = cells.getCell("A5");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getuserTen());

		dbutils db = new dbutils();
		
		int i = 1;

		cell = cells.getCell(htb.get(i) + "1");cell.setValue("Ngay");cell.setStyle(style);i++;
		cell = cells.getCell(htb.get(i) + "1");cell.setValue("MaNhaPhanPhoi");cell.setStyle(style);i++;
		cell = cells.getCell(htb.get(i) + "1");cell.setValue("NhaPhanPhoi");cell.setStyle(style);i++;
		cell = cells.getCell(htb.get(i) + "1");cell.setValue("MaDaiDienKinhDoanh");cell.setStyle(style);i++;
		cell = cells.getCell(htb.get(i) + "1");cell.setValue("DaiDienKinhDoanh");cell.setStyle(style);i++;
		cell = cells.getCell(htb.get(i) + "1");cell.setValue("MaKhachHang");cell.setStyle(style);i++;
		cell = cells.getCell(htb.get(i) + "1");cell.setValue("KhachHang");cell.setStyle(style);i++;
		cell = cells.getCell(htb.get(i) + "1");cell.setValue("DiaChi");cell.setStyle(style);i++;
		cell = cells.getCell(htb.get(i) + "1");cell.setValue("MucTieuThang");cell.setStyle(style);i++;
		cell = cells.getCell(htb.get(i) + "1");cell.setValue("MucTieuHeThong");cell.setStyle(style);i++;
		cell = cells.getCell(htb.get(i) + "1");cell.setValue("ThoiGianMuaHang");cell.setStyle(style);i++;
		cell = cells.getCell(htb.get(i) + "1");cell.setValue("TongDoanhSo");cell.setStyle(style);i++;
		cell = cells.getCell(htb.get(i) + "1");cell.setValue("DoanhSoNgay");cell.setStyle(style);i++;
		cell = cells.getCell(htb.get(i) + "1");cell.setValue("MucTieuNgay");cell.setStyle(style);i++;
		
		db.shutDown();
		cell = cells.getCell("M1");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, (26 * 4 + i - 1) + "");
	}
	
	private void FillData(Workbook workbook, String[] fieldShow, String query, IStockintransit obj) throws Exception
	{
		Hashtable<Integer, String> htb = this.htbValueCell();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();

		dbutils db = new dbutils();
		ResultSet		rs = db.get(query);

		int indexRow = 2;
		try
		{
			Cell cell = null;
			while (rs.next())
			{
				
				float ctDdkd=rs.getFloat("ChiTieu");
				float dsTong=rs.getFloat("dsTong");
				int SoTuanTrongThang = rs.getInt("SoTuanTrongThang");
				int TuanHienTai = rs.getInt("TuanHienTai");
				int TuanConLai = SoTuanTrongThang-TuanHienTai;
				
				float dsNgay =rs.getFloat("dsNgay");
				
				float tbDoanhSoDDKD= rs.getFloat("tbDoanhSoNvbh");
				float tbDoanhSoKH= rs.getFloat("tbDoanhSoKH");
				float ctThang=0;
				if(tbDoanhSoDDKD>0)
				{
					 ctThang=ctDdkd*(tbDoanhSoKH/tbDoanhSoDDKD);
				}
				float ctHeThong=0;
				if(ctThang>0)
					ctHeThong =( ctThang-dsTong ) /TuanConLai;
				
				
				cell = cells.getCell("DA" + Integer.toString(indexRow)); cell.setValue(rs.getString("Ngay")); 
				cell = cells.getCell("DB" + Integer.toString(indexRow)); cell.setValue(rs.getString("nppMa"));
				cell = cells.getCell("DC" + Integer.toString(indexRow)); cell.setValue(rs.getString("nppTen"));
				cell = cells.getCell("DD" + Integer.toString(indexRow)); cell.setValue(rs.getString("ddkdMa")); 
				cell = cells.getCell("DE" + Integer.toString(indexRow)); cell.setValue(rs.getString("ddkdTen")); 			
				int j = 6;
				cell = cells.getCell(htb.get(j) + Integer.toString(indexRow));cell.setValue(rs.getString("SmartId"));j++;
				cell = cells.getCell(htb.get(j) + Integer.toString(indexRow));cell.setValue(rs.getString("ten"));j++;
				
				cell = cells.getCell(htb.get(j) + Integer.toString(indexRow));cell.setValue(rs.getString("DiaChi"));j++;
				cell = cells.getCell(htb.get(j) + Integer.toString(indexRow));cell.setValue(  ctThang  );j++;
				cell = cells.getCell(htb.get(j) + Integer.toString(indexRow));cell.setValue(  ctHeThong  );j++;
				cell = cells.getCell(htb.get(j) + Integer.toString(indexRow));cell.setValue(rs.getFloat("ThoiGianMuaHang"));j++;
				cell = cells.getCell(htb.get(j) + Integer.toString(indexRow));cell.setValue( dsTong  );j++;
				cell = cells.getCell(htb.get(j) + Integer.toString(indexRow));cell.setValue( dsNgay );j++;
				cell = cells.getCell(htb.get(j) + Integer.toString(indexRow));cell.setValue(rs.getFloat("MucTieuNgay"));j++;
				indexRow++;
			}
			if (indexRow == 2)
			{
				throw new Exception("Không có báo cáo với điều kiện đã chọn !");
			}
			if (rs != null)
				rs.close();
			if (db != null)
			{
				db.shutDown();
			}
		} catch (Exception err)
		{
			err.printStackTrace();
			throw new Exception("Khong the tao duoc bao cao trong thoi gian nay. Error :" + err.toString());
		}
	}
	
	private Hashtable<Integer, String> htbValueCell()
	{
		Hashtable<Integer, String> htb = new Hashtable<Integer, String>();
		htb.put(1, "DA");htb.put(2, "DB");htb.put(3, "DC");
		htb.put(4, "DD");htb.put(5, "DE");htb.put(6, "DF");
		htb.put(7, "DG");htb.put(8, "DH");htb.put(9, "DI");
		htb.put(10, "DJ");htb.put(11, "DK");htb.put(12, "DL");
		htb.put(13, "DM");htb.put(14, "DN");htb.put(15, "DO");
		htb.put(16, "DP");htb.put(17, "DQ");htb.put(18, "DR");
		htb.put(19, "DS");htb.put(20, "DT");htb.put(21, "DU");
		htb.put(22, "DV");htb.put(23, "DW");htb.put(24, "DX");
		htb.put(25, "DY");htb.put(26, "DZ");htb.put(27, "EA");
		htb.put(28, "EB");htb.put(29, "EC");htb.put(30, "ED");
		htb.put(31, "EE");htb.put(32, "EF");htb.put(33, "EG");
		htb.put(34, "EH");htb.put(35, "EI");htb.put(36, "EJ");
		htb.put(37, "EK");htb.put(38, "EL");htb.put(39, "EM");
		htb.put(40, "EN");htb.put(41, "EO");htb.put(42, "EP");
		htb.put(43, "EQ");htb.put(44, "ER");htb.put(45, "ES");
		htb.put(46, "ET");htb.put(47, "EU");htb.put(48, "EV");
		htb.put(49, "EW");htb.put(50, "EX");htb.put(51, "EY");
		htb.put(52, "EZ");htb.put(53, "FA");htb.put(54, "FB");
		htb.put(55, "FC");htb.put(56, "FD");htb.put(57, "FE");
		htb.put(58, "FF");htb.put(59, "FG");htb.put(60, "FH");
		return htb;
	}
	
	
	public static  void main(String[] arg)
	{
		/*IStockintransit obj = new Stockintransit();
		obj.settungay("2014-01-01");
		obj.setdenngay("2014-01-02");
		
		 	Integer tungay = Integer.parseInt(obj.gettungay().substring(8,obj.gettungay().trim().length()));
	    Integer denngay = Integer.parseInt(obj.getdenngay().substring(8,obj.getdenngay().trim().length()));
	    for(int k=tungay; k<=denngay; k++ )
	    {
	    	String str = "00"+k;
	    	String d = str.substring(str.length()-2, str.length());
	    	System.out.println(" '" + obj.gettungay().substring(0,8)+d + "' " );
	    	
	    }
	    BcChiTieuKhachHangSvl svl= new BcChiTieuKhachHangSvl();
	  String query= svl.setQuery_ChiTiet(obj);
	    System.out.println("[Query]"+query);
	    */
	    
	}
	
}
