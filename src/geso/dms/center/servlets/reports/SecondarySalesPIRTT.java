package geso.dms.center.servlets.reports;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

import java.util.*;

public class SecondarySalesPIRTT extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SecondarySalesPIRTT() {
		super();
	}

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
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
		String nextJSP = "/AHF/pages/Center/SecondarySalesPurchaseInventoryReport.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		// OutputStream out = response.getOutputStream();
		if (userId == null)
			userId = "";
		obj.setuserId(userId);
		//geso.dms.distributor.util.Utility Ult = new geso.dms.distributor.util.Utility();

		String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
		obj.setnppId(nppId);
		obj.setuserTen(userTen);

		String vungId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")); // <!---
		if (vungId == null)
			vungId = "";
		obj.setvungId(vungId);
		

		String khuvucId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")); // <!---
		if (khuvucId == null)
			khuvucId = "";
		obj.setkhuvucId(khuvucId);

		String kenhId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")); // <!---
		if (kenhId == null)
			kenhId = "";
		obj.setkenhId(kenhId);

		String dvkdId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")); // <!---
		if (dvkdId == null)
			dvkdId = "";
		obj.setdvkdId(dvkdId);

		String nhanhangId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhanhangId")); // <!---
		if (nhanhangId == null)
			nhanhangId = "";
		obj.setnhanhangId(nhanhangId);

		String chungloaiId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chungloaiId"));// <!---
		if (chungloaiId == null)
			chungloaiId = "";
		obj.setchungloaiId(chungloaiId);

		String tungay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays")); // <!---
		if (tungay == null)
			tungay = "";
		obj.settungay(tungay);

		String denngay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays"));// <!---
		if (denngay == null)
			denngay = "";
		obj.setdenngay(denngay);

		String khoId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khoId")); // <!---
		if (khoId == null)
			khoId = "";
		obj.setkhoId(khoId);
		obj.setvat("1");
		String instransit = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("instransit")); // <!---
		obj.setdiscount(instransit);
		
		String sanphamId  = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sanphamId"));
		obj.setsanphamId(sanphamId);
		
		
		
		String dvdlId  = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvdlId"));
		obj.setdvdlId(dvdlId);
		
		
		String giatinh = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("giatinh"));
		if(giatinh == null)
			giatinh = "1";
		
		if (!tungay.equals("") && !denngay.equals("")) 
		{
			String sql = "";
			
			String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
			
			
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			if (action.equals("tao")) {
				
				response.setContentType("application/xlsm");
					response.setHeader("Content-Disposition", "Attachment; filename=BaoCaoXuatNhapTon(TT)"+this.getPiVotName()+".xlsm");


				OutputStream out = response.getOutputStream();
				try 
				{
					CreatePivotTable(out, response, request, giatinh, obj);
					// PivotTable
				} 
				catch (Exception ex) 
				{
					request.getSession().setAttribute("errors", ex.getMessage());
				}
				return;
			}
		}
		
		obj.init();
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/AHF/pages/Center/SecondarySalesPurchaseInventoryReport.jsp";
		response.sendRedirect(nextJSP);
	}
	
	private String getPiVotName()
	{
		String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
	    String name = sdf.format(cal.getTime());
	    name = name.replaceAll("-", "");
	    name = name.replaceAll(" ", "_");
	    name = name.replaceAll(":", "");
	    return "_" + name; 
	}
	
	private void CreatePivotTable(OutputStream out, HttpServletResponse response, HttpServletRequest request, String giatinh, IStockintransit obj) throws Exception
	{
		try 
		{
			String strfstream = getServletContext().getInitParameter("path") + "\\BaoCaoXuatNhapTon(TT).xlsm";			
			
			FileInputStream fstream = new FileInputStream(strfstream);	
			
			//File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\BaoCaoXuatNhapTon(TT).xlsm");
			//FileInputStream fstream = new FileInputStream(f) ;
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
	     
			CreateHeader(workbook,giatinh, obj);
			FillData(workbook, giatinh, obj);
			
			workbook.save(out);
		    fstream.close();
			
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
			obj.setMsg("Không có báo cáo trong thời gian này");
			throw new Exception("Error Message: " + ex.getMessage());
			
		}	
	}
	
	private String QueryBC(String giatinh, IStockintransit obj, String nppId)
	{
		String query = "";
		if(giatinh.equals("1"))
		{
			query = 
				" select kbh.TEN as kenh,v.TEN as vung,kv.TEN as khuvuc,nh.TEN as nhanhang,case when npp.trangthai = 1 then N'Hoạt động' else N'Ngưng hoạt động' end as trangthainpp,cl.TEN as chungloai,dvkd.DIENGIAI as donvikinhdoanh," +
				"\n npp.MA as MaNpp,npp.TEN as TenNPP,kh.TEN as kho, sp.MA as MaSp,sp.TEN as TenSp,dvdl.DIENGIAI as Donvi,dh.dauky, dh.nhaphang, dh.dieuchinh," +
				"\n dh.nhapchuyenkho, dh.nvbhtrahang, dh.hangtravenpp, dh.xuatban, dh.xuatbankm, dh.xuatchuyenkho, dh.chuyenkhonvbh, dh.cuoiky, " +
				"\n isnull(dh.cuoiky * bg_npp_sp.GIAMUANPP,0)  as SoTien,isnull(bg_npp_sp.GIAMUANPP,0) as Gia, " +
				"\n ISNULL(qc.soluong1, qc1.SOLUONG1) as slqd, ISNULL(qc.soluong2, qc1.SOLUONG2) as slthung  " +
				"\n ,isnull(dh.dauky * bg_npp_sp.GIAMUANPP,0)  as tiendauky,isnull(dh.xuatchuyenkho * bg_npp_sp.GIAMUANPP,0)  as tienxuatck " +
				"\n ,isnull(dh.nhapchuyenkho * bg_npp_sp.GIAMUANPP,0)  as tiennhapck " +
				"\n ,isnull(dh.nhaphang * bg_npp_sp.GIAMUANPP,0)  as tiennhap,isnull(dh.dieuchinh * bg_npp_sp.GIAMUANPP,0)  as tiendieuchinh" +
				"\n ,isnull(dh.xuatban * bg_npp_sp.GIAMUANPP,0)  as tienxuatban,isnull(dh.xuatbankm * bg_npp_sp.GIAMUANPP,0)  as tienxuatkm, " +
				"\n isnull(dh.cuoiky * bg_npp_sp.GIAMUANPP,0)  as tiencuoiky, nha.ngaynhan, nsp.diengiai as nhomsp, case when npp.trangthaidms = 1 then N'Hoạt động' else N'Không hoạt động' end trangthaidms " +
				"\n	from [dbo].[ufn_XNT_TuNgay_DenNgay_FULL_new]('0','"+obj.gettungay()+"','"+obj.getdenngay()+"') dh" +
				"\n inner join SANPHAM sp on sp.PK_SEQ = dh.sanpham_fk" +
				"\n inner join KHO kh on kh.PK_SEQ = dh.kho_fk" +
				"\n inner join KENHBANHANG kbh on kbh.PK_SEQ = dh.kbh_fk " +
				"\n inner join NHAPHANPHOI npp on npp.PK_SEQ  = dh.npp_fk " +
				"\n left join NHOMSANPHAM_SANPHAM nspsp on sp.PK_SEQ = nspsp.SP_FK " +
				"\n left join NHOMSANPHAM nsp on nsp.PK_SEQ = nspsp.NSP_FK " +
				"\n left join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK" +
				"\n left join VUNG v on kv.VUNG_FK = v.PK_SEQ" +
				"\n left join NHANHANG nh on nh.PK_SEQ = sp.NHANHANG_FK" +
				"\n left join CHUNGLOAI cl on cl.PK_SEQ = sp.CHUNGLOAI_FK" +
				"\n left join DONVIKINHDOANH dvkd on dvkd.PK_SEQ = sp.DVKD_FK " +
				"\n left join nhaphang_sp nhsp on nhsp.sanpham_fk = sp.pk_seq and nhsp.kho_fk = kh.pk_seq " +
				"\n left join nhaphang nha on nha.pk_seq = nhsp.nhaphang_fk " +
				"\n left join DONVIDOLUONG dvdl on sp.DVDL_FK = dvdl.PK_SEQ" +
				"\n left join QUYCACH qc on qc.SANPHAM_FK = sp.PK_SEQ and qc.DVDL1_FK = dvdl.PK_SEQ and qc.DVDL2_FK ='100018' " +
				"\n left join QUYCACH qc1 on qc1.SANPHAM_FK = sp.PK_SEQ and qc1.DVDL1_FK = dvdl.PK_SEQ and qc1.DVDL2_FK= '1200534' " +
				"\n left join BANGGIAMUANPP_NPP bg_npp on bg_npp.NPP_FK = npp.PK_SEQ and bg_npp.NPP_FK = npp.pk_seq "+
				"\n left join BGMUANPP_SANPHAM bg_npp_sp on bg_npp_sp.sanpham_fk = sp.pk_seq and bg_npp_sp.BGMUANPP_FK = bg_npp.BANGGIAMUANPP_FK" +
				"\n where 1 = 1 ";			
		}
		else
		{
			query = 
				"\n select kbh.TEN as kenh,v.TEN as vung,kv.TEN as khuvuc,nh.TEN as nhanhang,case when npp.trangthai = 1 then N'Hoạt động' else N'Ngưng hoạt động' end as trangthainpp,cl.TEN as chungloai,dvkd.DIENGIAI as donvikinhdoanh," +
				"\n npp.MA as MaNpp,npp.TEN as TenNPP,kh.TEN as kho, sp.MA as MaSp,sp.TEN as TenSp,dvdl.DIENGIAI as Donvi,dh.dauky, dh.nhaphang, dh.dieuchinh," +
				"\n dh.nhapchuyenkho, dh.nvbhtrahang, dh.hangtravenpp, dh.xuatban, dh.xuatbankm, dh.xuatchuyenkho, dh.chuyenkhonvbh, dh.cuoiky, " +
				"\n isnull(dh.cuoiky * (select  top(1) isnull(GIABANLECHUAN,0) from BANGGIABLC_SANPHAM a inner join BANGGIABANLECHUAN_NPP b on a.BGBLC_FK = b.BANGGIABANLECHUAN_FK " +
				"\n where a.BGBLC_FK = b.BANGGIABANLECHUAN_FK and SANPHAM_FK = sp.PK_SEQ and b.NPP_FK = npp.PK_SEQ ),0) as tiencuoiky," +
				"\n isnull((select  top(1) GIABANLECHUAN from BANGGIABLC_SANPHAM a inner join BANGGIABANLECHUAN_NPP b on a.BGBLC_FK = b.BANGGIABANLECHUAN_FK " +
				"\n where a.BGBLC_FK = b.BANGGIABANLECHUAN_FK and SANPHAM_FK = sp.PK_SEQ and b.NPP_FK = npp.PK_SEQ),0) as Gia, " +
				"\n ISNULL(qc.soluong1, qc1.SOLUONG1) as slqd, ISNULL(qc.soluong2, qc1.SOLUONG2) as slthung,  " +
				"\n isnull(dh.nhaphang * (select  top(1) isnull(GIABANLECHUAN,0) from BANGGIABLC_SANPHAM a inner join BANGGIABANLECHUAN_NPP b on a.BGBLC_FK = b.BANGGIABANLECHUAN_FK " +
				"\n where a.BGBLC_FK = b.BANGGIABANLECHUAN_FK and SANPHAM_FK = sp.PK_SEQ and b.NPP_FK = npp.PK_SEQ ),0) as tiennhap," +
				"\n isnull(dh.dauky * (select  top(1) isnull(GIABANLECHUAN,0) from BANGGIABLC_SANPHAM a inner join BANGGIABANLECHUAN_NPP b on a.BGBLC_FK = b.BANGGIABANLECHUAN_FK " +
				"\n where a.BGBLC_FK = b.BANGGIABANLECHUAN_FK and SANPHAM_FK = sp.PK_SEQ and b.NPP_FK = npp.PK_SEQ ),0) as tiendauky," +
				"\n isnull(dh.xuatban * (select  top(1) isnull(GIABANLECHUAN,0) from BANGGIABLC_SANPHAM a inner join BANGGIABANLECHUAN_NPP b on a.BGBLC_FK = b.BANGGIABANLECHUAN_FK " +
				"\n where a.BGBLC_FK = b.BANGGIABANLECHUAN_FK and SANPHAM_FK = sp.PK_SEQ and b.NPP_FK = npp.PK_SEQ ),0) as tienxuatban," +
				"\n isnull(dh.xuatbankm * (select  top(1) isnull(GIABANLECHUAN,0) from BANGGIABLC_SANPHAM a inner join BANGGIABANLECHUAN_NPP b on a.BGBLC_FK = b.BANGGIABANLECHUAN_FK " +
				"\n where a.BGBLC_FK = b.BANGGIABANLECHUAN_FK and SANPHAM_FK = sp.PK_SEQ and b.NPP_FK = npp.PK_SEQ ),0) as tienxuatkm," +
				"\n isnull(dh.xuatchuyenkho * (select  top(1) isnull(GIABANLECHUAN,0) from BANGGIABLC_SANPHAM a inner join BANGGIABANLECHUAN_NPP b on a.BGBLC_FK = b.BANGGIABANLECHUAN_FK " +
				"\n where a.BGBLC_FK = b.BANGGIABANLECHUAN_FK and SANPHAM_FK = sp.PK_SEQ and b.NPP_FK = npp.PK_SEQ ),0) as tienxuatck," +
				"\n isnull(dh.nhapchuyenkho * (select  top(1) isnull(GIABANLECHUAN,0) from BANGGIABLC_SANPHAM a inner join BANGGIABANLECHUAN_NPP b on a.BGBLC_FK = b.BANGGIABANLECHUAN_FK " +
				"\n where a.BGBLC_FK = b.BANGGIABANLECHUAN_FK and SANPHAM_FK = sp.PK_SEQ and b.NPP_FK = npp.PK_SEQ ),0) as tiennhapck," +
				"\n isnull(dh.dieuchinh * (select  top(1) isnull(GIABANLECHUAN,0) from BANGGIABLC_SANPHAM a inner join BANGGIABANLECHUAN_NPP b on a.BGBLC_FK = b.BANGGIABANLECHUAN_FK " +
				"\n where a.BGBLC_FK = b.BANGGIABANLECHUAN_FK and SANPHAM_FK = sp.PK_SEQ and b.NPP_FK = npp.PK_SEQ ),0) as tiendieuchinh, nha.ngaynhan, nsp.diengiai as nhomsp , case when npp.trangthaidms = 1 then N'Hoạt động' else N'Không hoạt động' end trangthaidms " +
				"\n from [dbo].[ufn_XNT_TuNgay_DenNgay_FULL_new](0,'"+obj.gettungay()+"','"+obj.getdenngay()+"') dh" +
				"\n inner join SANPHAM sp on sp.PK_SEQ = dh.sanpham_fk" +
				"\n inner join KHO kh on kh.PK_SEQ = dh.kho_fk" +
				"\n inner join KENHBANHANG kbh on kbh.PK_SEQ = dh.kbh_fk" +
				"\n inner join NHAPHANPHOI npp on npp.PK_SEQ  = dh.npp_fk" +
				"\n left join NHOMSANPHAM_SANPHAM nspsp on sp.PK_SEQ = nspsp.SP_FK " +
				"\n left join NHOMSANPHAM nsp on nsp.PK_SEQ = nspsp.NSP_FK " +
				"\n left join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK" +
				"\n left join VUNG v on kv.VUNG_FK = v.PK_SEQ" +
				"\n left join NHANHANG nh on nh.PK_SEQ = sp.NHANHANG_FK" +
				"\n left join nhaphang_sp nhsp on nhsp.sanpham_fk = sp.pk_seq and nhsp.kho_fk = kh.pk_seq " +
				"\n left join nhaphang nha on nha.pk_seq = nhsp.nhaphang_fk " +
				"\n left join CHUNGLOAI cl on cl.PK_SEQ = sp.CHUNGLOAI_FK" +
				"\n left join DONVIKINHDOANH dvkd on dvkd.PK_SEQ = sp.DVKD_FK" +
				"\n left join DONVIDOLUONG dvdl on sp.DVDL_FK = dvdl.PK_SEQ" +
				"\n left join QUYCACH qc on qc.SANPHAM_FK = sp.PK_SEQ and qc.DVDL1_FK = dvdl.PK_SEQ and qc.DVDL2_FK ='100018' " +
				"\n left join QUYCACH qc1 on qc1.SANPHAM_FK = sp.PK_SEQ and qc1.DVDL1_FK = dvdl.PK_SEQ and qc1.DVDL2_FK= '1200534' " +
				"\n where 1 = 1  " ;			
		}

		/*if(obj.getkenhId() != null)
			if(obj.getkenhId().length() > 0 )
				query += "and kbh.pk_seq = '"+obj.getkenhId()+"' ";*/
		if(obj.getnhanhangId() != null)
			if(obj.getnhanhangId().length() > 0 )
				query += "and nh.pk_seq = '"+obj.getnhanhangId()+"' ";
		if(obj.getchungloaiId() != null)
			if(obj.getchungloaiId().length() > 0 )
				query += "and cl.pk_seq = '"+obj.getchungloaiId()+"' ";
		if(obj.getdvkdId() != null)
			if(obj.getdvkdId().length() > 0 )
				query += "and dvkd.pk_seq = '"+obj.getdvkdId()+"' ";
		if(obj.getkhoId() != null)
			if(obj.getkhoId().length() > 0 )
				query += "and kh.pk_seq = '"+obj.getkhoId()+"' ";
		/*if(obj.getkhuvucId() != null)
			if(obj.getkhuvucId() .length() > 0 )
				query += "and kv.pk_seq = '"+obj.getkhuvucId()+"' ";
		if(obj.getvungId() != null)
			if(obj.getvungId() .length() > 0 )
				query += "and v.pk_seq = '"+obj.getvungId()+"' ";*/
		if(obj.getsanphamId() != null)
			if(obj.getsanphamId() .length() > 0 )
				query += "and sp.pk_seq = '"+obj.getsanphamId()+"' ";
		
		return query;
	}

	private void FillData(Workbook workbook, String giatinh, IStockintransit obj) throws Exception 
	{
		System.out.println("Đã vào đây r"+giatinh);
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
	
		Cells cells = worksheet.getCells();
		dbutils db = new dbutils();
		
		ResultSet rs = null;
		
		System.out.println("NPPID: "+obj.getnppId());
		String query = "";
		/*String nppId="0";
		if(obj.getnppId().length() > 0)
		{
			nppId=obj.getnppId();
		}
*/
		
		ResultSet rsall = null;	
		 if(giatinh.equals("1"))
		 {
			 
			 

			 
			 String sql = "select * from nhaphanphoi a where trangthai = 1 ";
				if(obj.getnppId().length()>0)
					sql += " and a.pk_seq = '"+obj.getnppId()+"' ";
				if(obj.getkhuvucId() != null)
					if(obj.getkhuvucId() .length() > 0 )
						sql += "and a.khuvuc_fk = '"+obj.getkhuvucId()+"' ";
				if(obj.getkenhId() != null)
					if(obj.getkenhId().length() > 0 )
						sql += "and exists (select 1 from nhapp_kbh kbh where kbh.kbh_fk = '"+obj.getkenhId()+"' and npp_fk = '"+obj.getnppId()+"') ";
				if(obj.getvungId() != null)
					if(obj.getvungId() .length() > 0 )
						sql += "and exists (select 1 from nhaphanphoi npp inner join khuvuc kv on npp.khuvuc_fk = kv.pk_seq where npp.pk_seq = a.pk_seq and kv.vung_fk = '"+obj.getvungId()+"') ";
				System.out.println("nhaphanphoi_________" + sql);
				rs = db.get(sql);
				if(rs != null)
				{
					int index = 2;
					while(rs.next())
					{
						obj.setnppId(rs.getString("pk_seq"));
						query = " select kbh.TEN as kenh,v.TEN as vung,kv.TEN as khuvuc,nh.TEN as nhanhang,case when npp.trangthai = 1 then N'Hoạt động' else N'Ngưng hoạt động' end as trangthainpp,cl.TEN as chungloai,dvkd.DIENGIAI as donvikinhdoanh," +
							"\n npp.MA as MaNpp,npp.TEN as TenNPP,kh.TEN as kho, sp.MA as MaSp,sp.TEN as TenSp,dvdl.DIENGIAI as Donvi,dh.dauky, dh.nhaphang, dh.dieuchinh," +
							"\n dh.nhapchuyenkho, dh.nvbhtrahang, dh.hangtravenpp, dh.xuatban, dh.xuatbankm, dh.xuatchuyenkho, dh.chuyenkhonvbh, dh.cuoiky, " +
							"\n isnull(dh.cuoiky * bg_npp_sp.GIAMUANPP,0)  as SoTien,isnull(bg_npp_sp.GIAMUANPP,0) as Gia, " +
							"\n ISNULL(qc.soluong1, qc1.SOLUONG1) as slqd, ISNULL(qc.soluong2, qc1.SOLUONG2) as slthung  " +
							"\n ,isnull(dh.dauky * bg_npp_sp.GIAMUANPP,0)  as tiendauky,isnull(dh.xuatchuyenkho * bg_npp_sp.GIAMUANPP,0)  as tienxuatck " +
							"\n ,isnull(dh.nhapchuyenkho * bg_npp_sp.GIAMUANPP,0)  as tiennhapck " +
							"\n ,isnull(dh.nhaphang * bg_npp_sp.GIAMUANPP,0)  as tiennhap,isnull(dh.dieuchinh * bg_npp_sp.GIAMUANPP,0)  as tiendieuchinh" +
							"\n ,isnull(dh.xuatban * bg_npp_sp.GIAMUANPP,0)  as tienxuatban,isnull(dh.xuatbankm * bg_npp_sp.GIAMUANPP,0)  as tienxuatkm, " +
							"\n isnull(dh.cuoiky * bg_npp_sp.GIAMUANPP,0)  as tiencuoiky, nha.ngaynhan, nsp.diengiai as nhomsp, case when npp.trangthaidms = 1 then N'Hoạt động' else N'Không hoạt động' end trangthaidms " +
							"\n	from [dbo].[ufn_XNT_TuNgay_DenNgay_FULL_new]('"+obj.getnppId()+"','"+obj.gettungay()+"','"+obj.getdenngay()+"') dh" +
							"\n inner join SANPHAM sp on sp.PK_SEQ = dh.sanpham_fk" +
							"\n inner join KHO kh on kh.PK_SEQ = dh.kho_fk" +
							"\n inner join KENHBANHANG kbh on kbh.PK_SEQ = dh.kbh_fk " +
							"\n inner join NHAPHANPHOI npp on npp.PK_SEQ  = dh.npp_fk " +
							"\n left join NHOMSANPHAM_SANPHAM nspsp on sp.PK_SEQ = nspsp.SP_FK " +
							"\n left join NHOMSANPHAM nsp on nsp.PK_SEQ = nspsp.NSP_FK " +
							"\n left join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK" +
							"\n left join VUNG v on kv.VUNG_FK = v.PK_SEQ" +
							"\n left join NHANHANG nh on nh.PK_SEQ = sp.NHANHANG_FK" +
							"\n left join CHUNGLOAI cl on cl.PK_SEQ = sp.CHUNGLOAI_FK" +
							"\n left join DONVIKINHDOANH dvkd on dvkd.PK_SEQ = sp.DVKD_FK " +
							"\n left join nhaphang_sp nhsp on nhsp.sanpham_fk = sp.pk_seq and nhsp.kho_fk = kh.pk_seq " +
							"\n left join nhaphang nha on nha.pk_seq = nhsp.nhaphang_fk " +
							"\n left join DONVIDOLUONG dvdl on sp.DVDL_FK = dvdl.PK_SEQ" +
							"\n left join QUYCACH qc on qc.SANPHAM_FK = sp.PK_SEQ and qc.DVDL1_FK = dvdl.PK_SEQ and qc.DVDL2_FK ='100018' " +
							"\n left join QUYCACH qc1 on qc1.SANPHAM_FK = sp.PK_SEQ and qc1.DVDL1_FK = dvdl.PK_SEQ and qc1.DVDL2_FK= '1200534' " +
							"\n outer apply ( select [dbo].[GiaMuaNppSanPham](kbh.pk_seq,npp.pk_seq,sp.pk_seq, '"+obj.getdenngay()+"')   GIAMUANPP )bg_npp_sp  " +
							"\n where 1 = 1 ";
						if(obj.getnhanhangId() != null)
							if(obj.getnhanhangId().length() > 0 )
								query += "and nh.pk_seq = '"+obj.getnhanhangId()+"' ";
						if(obj.getchungloaiId() != null)
							if(obj.getchungloaiId().length() > 0 )
								query += "and cl.pk_seq = '"+obj.getchungloaiId()+"' ";
						if(obj.getdvkdId() != null)
							if(obj.getdvkdId().length() > 0 )
								query += "and dvkd.pk_seq = '"+obj.getdvkdId()+"' ";
						if(obj.getkhoId() != null)
							if(obj.getkhoId().length() > 0 )
								query += "and kh.pk_seq = '"+obj.getkhoId()+"' ";

						if(obj.getsanphamId() != null)
							if(obj.getsanphamId() .length() > 0 )
								query += "and sp.pk_seq = '"+obj.getsanphamId()+"' ";
						System.out.println("_+____query___"+ query);
						rsall = db.get(query);
						
						Cell cell;
						if( rsall != null)
						while (rsall.next()) {							

							cell = cells.getCell("CA" + String.valueOf(index));
							cell.setValue(rsall.getString("kenh"));
							cell = cells.getCell("CB" + String.valueOf(index));
							cell.setValue(rsall.getString("vung"));
							cell = cells.getCell("CC" + String.valueOf(index));
							cell.setValue(rsall.getString("khuvuc"));
							cell = cells.getCell("CD" + String.valueOf(index));
							cell.setValue(rsall.getString("nhanhang"));
							cell = cells.getCell("CE" + String.valueOf(index));
							cell.setValue(rsall.getString("chungloai"));
							cell = cells.getCell("CF" + String.valueOf(index));
							cell.setValue(rsall.getString("donvikinhdoanh"));
							cell = cells.getCell("CG" + String.valueOf(index));
							cell.setValue(rsall.getString("manpp"));
							cell = cells.getCell("CH" + String.valueOf(index));
							cell.setValue(rsall.getString("tennpp"));
							cell = cells.getCell("CI" + String.valueOf(index));
							cell.setValue(rsall.getString("kho"));
							cell = cells.getCell("CJ" + String.valueOf(index));
							cell.setValue(rsall.getString("masp"));
							cell = cells.getCell("CK" + String.valueOf(index));
							cell.setValue(rsall.getString("tensp"));
							cell = cells.getCell("CL" + String.valueOf(index));
							cell.setValue(rsall.getString("donvi"));
							cell = cells.getCell("CM" + String.valueOf(index));
							cell.setValue(Float.parseFloat(rsall.getString("dauky")));
							cell = cells.getCell("CN" + String.valueOf(index));
							cell.setValue(Float.parseFloat(rsall.getString("nhaphang"))); // Quantity
							cell = cells.getCell("CO" + String.valueOf(index));
							cell.setValue(Float.parseFloat(rsall.getString("dieuchinh"))); // Piece
							cell = cells.getCell("CP" + String.valueOf(index));
							cell.setValue(rsall.getFloat("nhapchuyenkho")); 
							cell = cells.getCell("CQ" + String.valueOf(index));
							cell.setValue(rsall.getFloat("tiendauky")); 
							cell = cells.getCell("CR" + String.valueOf(index));
							cell.setValue(rsall.getFloat("tiennhap"));
							cell = cells.getCell("CS" + String.valueOf(index));
							cell.setValue(rsall.getFloat("xuatban"));
							cell = cells.getCell("CT" + String.valueOf(index));
							cell.setValue(rsall.getFloat("xuatbankm")); 
							cell = cells.getCell("CU" + String.valueOf(index));
							cell.setValue(rsall.getFloat("tiendieuchinh"));
							cell = cells.getCell("CV" + String.valueOf(index));
							cell.setValue(rsall.getFloat("tienxuatkm"));
							cell = cells.getCell("CW" + String.valueOf(index));
							cell.setValue(rsall.getFloat("tienxuatban"));
							cell = cells.getCell("CX" + String.valueOf(index));
							cell.setValue(rsall.getFloat("tiencuoiky"));
							cell = cells.getCell("CY" + String.valueOf(index));
							cell.setValue(rsall.getFloat("cuoiky"));
							cell = cells.getCell("CZ" + String.valueOf(index));
							cell.setValue(rsall.getString("ngaynhan"));
							cell = cells.getCell("DA" + String.valueOf(index));
							cell.setValue(rsall.getFloat("gia"));
							cell = cells.getCell("DB" + String.valueOf(index));
							cell.setValue(rsall.getFloat("slqd"));
							cell = cells.getCell("DC" + String.valueOf(index));
							cell.setValue(rsall.getFloat("slthung"));
							cell = cells.getCell("DD" + String.valueOf(index));
							cell.setValue(rsall.getString("trangthainpp"));
							cell = cells.getCell("DE" + String.valueOf(index));
							cell.setValue(rsall.getFloat("xuatchuyenkho"));
							cell = cells.getCell("DF" + String.valueOf(index));
							cell.setValue(rsall.getFloat("tienxuatck"));
							cell = cells.getCell("DG" + String.valueOf(index));
							cell.setValue(rsall.getFloat("tiennhapck"));	
							cell = cells.getCell("DH" + String.valueOf(index));
							cell.setValue(rsall.getString("nhomsp"));	
							cell = cells.getCell("DI" + String.valueOf(index));
							cell.setValue(rsall.getString("trangthaidms"));	
							index++;
							
						}
					
						//query = query.substring(0,query.length() - 10);
						//rs.close();
					}
					
				}
				//query = query.substring(0,query.length() - 10);
				
				rs.close();
		 
			 
				/*query = 
					" select kbh.TEN as kenh,v.TEN as vung,kv.TEN as khuvuc,nh.TEN as nhanhang,case when npp.trangthai = 1 then N'Hoạt động' else N'Ngưng hoạt động' end as trangthainpp,cl.TEN as chungloai,dvkd.DIENGIAI as donvikinhdoanh," +
					"\n npp.MA as MaNpp,npp.TEN as TenNPP,kh.TEN as kho, sp.MA as MaSp,sp.TEN as TenSp,dvdl.DIENGIAI as Donvi,dh.dauky, dh.nhaphang, dh.dieuchinh," +
					"\n dh.nhapchuyenkho, dh.nvbhtrahang, dh.hangtravenpp, dh.xuatban, dh.xuatbankm, dh.xuatchuyenkho, dh.chuyenkhonvbh, dh.cuoiky, " +
					"\n isnull(dh.cuoiky * bg_npp_sp.GIAMUANPP,0)  as SoTien,isnull(bg_npp_sp.GIAMUANPP,0) as Gia, " +
					"\n ISNULL(qc.soluong1, qc1.SOLUONG1) as slqd, ISNULL(qc.soluong2, qc1.SOLUONG2) as slthung  " +
					"\n ,isnull(dh.dauky * bg_npp_sp.GIAMUANPP,0)  as tiendauky,isnull(dh.xuatchuyenkho * bg_npp_sp.GIAMUANPP,0)  as tienxuatck " +
					"\n ,isnull(dh.nhapchuyenkho * bg_npp_sp.GIAMUANPP,0)  as tiennhapck " +
					"\n ,isnull(dh.nhaphang * bg_npp_sp.GIAMUANPP,0)  as tiennhap,isnull(dh.dieuchinh * bg_npp_sp.GIAMUANPP,0)  as tiendieuchinh" +
					"\n ,isnull(dh.xuatban * bg_npp_sp.GIAMUANPP,0)  as tienxuatban,isnull(dh.xuatbankm * bg_npp_sp.GIAMUANPP,0)  as tienxuatkm, " +
					"\n isnull(dh.cuoiky * bg_npp_sp.GIAMUANPP,0)  as tiencuoiky, nha.ngaynhan " +
					"\n	from [dbo].[ufn_XNT_TuNgay_DenNgay_FULL_new]('"+nppId+"','"+obj.gettungay()+"','"+obj.getdenngay()+"') dh" +
					"\n inner join SANPHAM sp on sp.PK_SEQ = dh.sanpham_fk" +
					"\n inner join KHO kh on kh.PK_SEQ = dh.kho_fk" +
					"\n inner join KENHBANHANG kbh on kbh.PK_SEQ = dh.kbh_fk " +
					"\n inner join NHAPHANPHOI npp on npp.PK_SEQ  = dh.npp_fk " +
					"\n left join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK" +
					"\n left join VUNG v on kv.VUNG_FK = v.PK_SEQ" +
					"\n left join NHANHANG nh on nh.PK_SEQ = sp.NHANHANG_FK" +
					"\n left join CHUNGLOAI cl on cl.PK_SEQ = sp.CHUNGLOAI_FK" +
					"\n left join DONVIKINHDOANH dvkd on dvkd.PK_SEQ = sp.DVKD_FK " +
					"\n left join nhaphang_sp nhsp on nhsp.sanpham_fk = sp.pk_seq and nhsp.kho_fk = kh.pk_seq " +
					"\n left join nhaphang nha on nha.pk_seq = nhsp.nhaphang_fk " +
					"\n left join DONVIDOLUONG dvdl on sp.DVDL_FK = dvdl.PK_SEQ" +
					"\n left join QUYCACH qc on qc.SANPHAM_FK = sp.PK_SEQ and qc.DVDL1_FK = dvdl.PK_SEQ and qc.DVDL2_FK ='100018' " +
					"\n left join QUYCACH qc1 on qc1.SANPHAM_FK = sp.PK_SEQ and qc1.DVDL1_FK = dvdl.PK_SEQ and qc1.DVDL2_FK= '1200534' " +
					"\n left join BANGGIAMUANPP_NPP bg_npp on bg_npp.NPP_FK = npp.PK_SEQ and bg_npp.NPP_FK = npp.pk_seq "+
					"\n left join BGMUANPP_SANPHAM bg_npp_sp on bg_npp_sp.sanpham_fk = sp.pk_seq and bg_npp_sp.BGMUANPP_FK = bg_npp.BANGGIAMUANPP_FK" +
					"\n where 1 = 1 and npp.pk_seq in ( select pk_seq from nhaphanphoi a where trangthai = 1 ";
					if(obj.getnppId().length()>0)
						query += " and a.pk_seq = "+obj.getnppId()+" ";
					if(obj.getkenhId() != null)
						if(obj.getkenhId().length() > 0 )
							query += "and exists (select 1 from nhaphanphoi kbh where kbh.kbh_fk = '"+obj.getkenhId()+"' and kbh.pk_seq = a.pk_seq ) ";
					
					if(obj.getkhuvucId() != null)
						if(obj.getkhuvucId() .length() > 0 )
							query += "and a.khuvuc_fk = '"+obj.getkhuvucId()+"' ";
					if(obj.getvungId() != null)
						if(obj.getvungId() .length() > 0 )
							query += "and exists (select 1 from nhaphanphoi npp inner join khuvuc kv on npp.khuvuc_fk = kv.pk_seq where npp.pk_seq = a.pk_seq and kv.vung_fk = '"+obj.getvungId()+"') ";
					query+= " )"; */
		 }else{
			 
			 String sql = "select * from nhaphanphoi a where trangthai = 1 ";
				if(obj.getnppId().length()>0)
					sql += " and a.pk_seq = '"+obj.getnppId()+"' ";
				if(obj.getkhuvucId() != null)
					if(obj.getkhuvucId() .length() > 0 )
						sql += "and a.khuvuc_fk = '"+obj.getkhuvucId()+"' ";
				if(obj.getkenhId() != null)
					if(obj.getkenhId().length() > 0 )
						sql += "and exists (select 1 from nhapp_kbh kbh where kbh.kbh_fk = '"+obj.getkenhId()+"' and npp_fk = '"+obj.getnppId()+"') ";
				if(obj.getvungId() != null)
					if(obj.getvungId() .length() > 0 )
						sql += "and exists (select 1 from nhaphanphoi npp inner join khuvuc kv on npp.khuvuc_fk = kv.pk_seq where npp.pk_seq = a.pk_seq and kv.vung_fk = '"+obj.getvungId()+"') ";
				rs = db.get(sql);
				if(rs != null)
				{
					int index = 2;
					while(rs.next())
					{
						obj.setnppId(rs.getString("pk_seq"));
						query = "\n select kbh.TEN as kenh,v.TEN as vung,kv.TEN as khuvuc,nh.TEN as nhanhang,case when npp.trangthai = 1 then N'Hoạt động' else N'Ngưng hoạt động' end as trangthainpp,cl.TEN as chungloai,dvkd.DIENGIAI as donvikinhdoanh," +
							"\n npp.MA as MaNpp,npp.TEN as TenNPP,kh.TEN as kho, sp.MA as MaSp,sp.TEN as TenSp,dvdl.DIENGIAI as Donvi,dh.dauky, dh.nhaphang, dh.dieuchinh," +
							"\n dh.nhapchuyenkho, dh.nvbhtrahang, dh.hangtravenpp, dh.xuatban, dh.xuatbankm, dh.xuatchuyenkho, dh.chuyenkhonvbh, dh.cuoiky, " +
							"\n isnull(dh.cuoiky * (select  top(1) isnull(GIABANLECHUAN,0) from BANGGIABLC_SANPHAM a inner join BANGGIABANLECHUAN_NPP b on a.BGBLC_FK = b.BANGGIABANLECHUAN_FK " +
							"\n where a.BGBLC_FK = b.BANGGIABANLECHUAN_FK and SANPHAM_FK = sp.PK_SEQ and b.NPP_FK = npp.PK_SEQ ),0) as tiencuoiky," +
							"\n isnull((select  top(1) GIABANLECHUAN from BANGGIABLC_SANPHAM a inner join BANGGIABANLECHUAN_NPP b on a.BGBLC_FK = b.BANGGIABANLECHUAN_FK " +
							"\n where a.BGBLC_FK = b.BANGGIABANLECHUAN_FK and SANPHAM_FK = sp.PK_SEQ and b.NPP_FK = npp.PK_SEQ),0) as Gia, " +
							"\n ISNULL(qc.soluong1, qc1.SOLUONG1) as slqd, ISNULL(qc.soluong2, qc1.SOLUONG2) as slthung,  " +
							"\n isnull(dh.nhaphang * (select  top(1) isnull(GIABANLECHUAN,0) from BANGGIABLC_SANPHAM a inner join BANGGIABANLECHUAN_NPP b on a.BGBLC_FK = b.BANGGIABANLECHUAN_FK " +
							"\n where a.BGBLC_FK = b.BANGGIABANLECHUAN_FK and SANPHAM_FK = sp.PK_SEQ and b.NPP_FK = npp.PK_SEQ ),0) as tiennhap," +
							"\n isnull(dh.dauky * (select  top(1) isnull(GIABANLECHUAN,0) from BANGGIABLC_SANPHAM a inner join BANGGIABANLECHUAN_NPP b on a.BGBLC_FK = b.BANGGIABANLECHUAN_FK " +
							"\n where a.BGBLC_FK = b.BANGGIABANLECHUAN_FK and SANPHAM_FK = sp.PK_SEQ and b.NPP_FK = npp.PK_SEQ ),0) as tiendauky," +
							"\n isnull(dh.xuatban * (select  top(1) isnull(GIABANLECHUAN,0) from BANGGIABLC_SANPHAM a inner join BANGGIABANLECHUAN_NPP b on a.BGBLC_FK = b.BANGGIABANLECHUAN_FK " +
							"\n where a.BGBLC_FK = b.BANGGIABANLECHUAN_FK and SANPHAM_FK = sp.PK_SEQ and b.NPP_FK = npp.PK_SEQ ),0) as tienxuatban," +
							"\n isnull(dh.xuatbankm * (select  top(1) isnull(GIABANLECHUAN,0) from BANGGIABLC_SANPHAM a inner join BANGGIABANLECHUAN_NPP b on a.BGBLC_FK = b.BANGGIABANLECHUAN_FK " +
							"\n where a.BGBLC_FK = b.BANGGIABANLECHUAN_FK and SANPHAM_FK = sp.PK_SEQ and b.NPP_FK = npp.PK_SEQ ),0) as tienxuatkm," +
							"\n isnull(dh.xuatchuyenkho * (select  top(1) isnull(GIABANLECHUAN,0) from BANGGIABLC_SANPHAM a inner join BANGGIABANLECHUAN_NPP b on a.BGBLC_FK = b.BANGGIABANLECHUAN_FK " +
							"\n where a.BGBLC_FK = b.BANGGIABANLECHUAN_FK and SANPHAM_FK = sp.PK_SEQ and b.NPP_FK = npp.PK_SEQ ),0) as tienxuatck," +
							"\n isnull(dh.nhapchuyenkho * (select  top(1) isnull(GIABANLECHUAN,0) from BANGGIABLC_SANPHAM a inner join BANGGIABANLECHUAN_NPP b on a.BGBLC_FK = b.BANGGIABANLECHUAN_FK " +
							"\n where a.BGBLC_FK = b.BANGGIABANLECHUAN_FK and SANPHAM_FK = sp.PK_SEQ and b.NPP_FK = npp.PK_SEQ ),0) as tiennhapck," +
							"\n isnull(dh.dieuchinh * (select  top(1) isnull(GIABANLECHUAN,0) from BANGGIABLC_SANPHAM a inner join BANGGIABANLECHUAN_NPP b on a.BGBLC_FK = b.BANGGIABANLECHUAN_FK " +
							"\n where a.BGBLC_FK = b.BANGGIABANLECHUAN_FK and SANPHAM_FK = sp.PK_SEQ and b.NPP_FK = npp.PK_SEQ ),0) as tiendieuchinh, nha.ngaynhan, nsp.diengiai as nhomsp, case when npp.trangthaidms = 1 then N'Hoạt động' else N'Không hoạt động' end trangthaidms " +
							"\n from [dbo].[ufn_XNT_TuNgay_DenNgay_FULL_new]('"+obj.getnppId()+"','"+obj.gettungay()+"','"+obj.getdenngay()+"') dh" +
							"\n inner join SANPHAM sp on sp.PK_SEQ = dh.sanpham_fk" +
							"\n inner join KHO kh on kh.PK_SEQ = dh.kho_fk" +
							"\n inner join KENHBANHANG kbh on kbh.PK_SEQ = dh.kbh_fk" +
							"\n inner join NHAPHANPHOI npp on npp.PK_SEQ  = dh.npp_fk" +
							"\n left join NHOMSANPHAM_SANPHAM nspsp on sp.PK_SEQ = nspsp.SP_FK " +
							"\n left join NHOMSANPHAM nsp on nsp.PK_SEQ = nspsp.NSP_FK " +
							"\n left join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK" +
							"\n left join VUNG v on kv.VUNG_FK = v.PK_SEQ" +
							"\n left join NHANHANG nh on nh.PK_SEQ = sp.NHANHANG_FK" +
							"\n left join nhaphang_sp nhsp on nhsp.sanpham_fk = sp.pk_seq and nhsp.kho_fk = kh.pk_seq " +
							"\n left join nhaphang nha on nha.pk_seq = nhsp.nhaphang_fk " +
							"\n left join CHUNGLOAI cl on cl.PK_SEQ = sp.CHUNGLOAI_FK" +
							"\n left join DONVIKINHDOANH dvkd on dvkd.PK_SEQ = sp.DVKD_FK" +
							"\n left join DONVIDOLUONG dvdl on sp.DVDL_FK = dvdl.PK_SEQ" +
							"\n left join QUYCACH qc on qc.SANPHAM_FK = sp.PK_SEQ and qc.DVDL1_FK = dvdl.PK_SEQ and qc.DVDL2_FK ='100018' " +
							"\n left join QUYCACH qc1 on qc1.SANPHAM_FK = sp.PK_SEQ and qc1.DVDL1_FK = dvdl.PK_SEQ and qc1.DVDL2_FK= '1200534' " +
							"\n where 1 = 1 ";
						if(obj.getnhanhangId() != null)
							if(obj.getnhanhangId().length() > 0 )
								query += "and nh.pk_seq = '"+obj.getnhanhangId()+"' ";
						if(obj.getchungloaiId() != null)
							if(obj.getchungloaiId().length() > 0 )
								query += "and cl.pk_seq = '"+obj.getchungloaiId()+"' ";
						if(obj.getdvkdId() != null)
							if(obj.getdvkdId().length() > 0 )
								query += "and dvkd.pk_seq = '"+obj.getdvkdId()+"' ";
						if(obj.getkhoId() != null)
							if(obj.getkhoId().length() > 0 )
								query += "and kh.pk_seq = '"+obj.getkhoId()+"' ";
						if(obj.getsanphamId() != null)
							if(obj.getsanphamId() .length() > 0 )
								query += "and sp.pk_seq = '"+obj.getsanphamId()+"' ";
						System.out.println("_+____query___"+ query);
						rsall = db.get(query);
						
						Cell cell;
						if( rsall != null)
						while (rsall.next()) {		
							cell = cells.getCell("CA" + String.valueOf(index));
							cell.setValue(rsall.getString("kenh"));
							cell = cells.getCell("CB" + String.valueOf(index));
							cell.setValue(rsall.getString("vung"));
							cell = cells.getCell("CC" + String.valueOf(index));
							cell.setValue(rsall.getString("khuvuc"));
							cell = cells.getCell("CD" + String.valueOf(index));
							cell.setValue(rsall.getString("nhanhang"));
							cell = cells.getCell("CE" + String.valueOf(index));
							cell.setValue(rsall.getString("chungloai"));
							cell = cells.getCell("CF" + String.valueOf(index));
							cell.setValue(rsall.getString("donvikinhdoanh"));
							cell = cells.getCell("CG" + String.valueOf(index));
							cell.setValue(rsall.getString("manpp"));
							cell = cells.getCell("CH" + String.valueOf(index));
							cell.setValue(rsall.getString("tennpp"));
							cell = cells.getCell("CI" + String.valueOf(index));
							cell.setValue(rsall.getString("kho"));
							cell = cells.getCell("CJ" + String.valueOf(index));
							cell.setValue(rsall.getString("masp"));
							cell = cells.getCell("CK" + String.valueOf(index));
							cell.setValue(rsall.getString("tensp"));
							cell = cells.getCell("CL" + String.valueOf(index));
							cell.setValue(rsall.getString("donvi"));
							cell = cells.getCell("CM" + String.valueOf(index));
							cell.setValue(Float.parseFloat(rsall.getString("dauky")));
							cell = cells.getCell("CN" + String.valueOf(index));
							cell.setValue(Float.parseFloat(rsall.getString("nhaphang"))); // Quantity
							cell = cells.getCell("CO" + String.valueOf(index));
							cell.setValue(Float.parseFloat(rsall.getString("dieuchinh"))); // Piece
							cell = cells.getCell("CP" + String.valueOf(index));
							cell.setValue(rsall.getFloat("nhapchuyenkho")); 
							cell = cells.getCell("CQ" + String.valueOf(index));
							cell.setValue(rsall.getFloat("tiendauky")); 
							cell = cells.getCell("CR" + String.valueOf(index));
							cell.setValue(rsall.getFloat("tiennhap"));
							cell = cells.getCell("CS" + String.valueOf(index));
							cell.setValue(rsall.getFloat("xuatban"));
							cell = cells.getCell("CT" + String.valueOf(index));
							cell.setValue(rsall.getFloat("xuatbankm")); 
							cell = cells.getCell("CU" + String.valueOf(index));
							cell.setValue(rsall.getFloat("tiendieuchinh"));
							cell = cells.getCell("CV" + String.valueOf(index));
							cell.setValue(rsall.getFloat("tienxuatkm"));
							cell = cells.getCell("CW" + String.valueOf(index));
							cell.setValue(rsall.getFloat("tienxuatban"));
							cell = cells.getCell("CX" + String.valueOf(index));
							cell.setValue(rsall.getFloat("tiencuoiky"));
							cell = cells.getCell("CY" + String.valueOf(index));
							cell.setValue(rsall.getFloat("cuoiky"));
							cell = cells.getCell("CZ" + String.valueOf(index));
							cell.setValue(rsall.getString("ngaynhan"));
							cell = cells.getCell("DA" + String.valueOf(index));
							cell.setValue(rsall.getFloat("gia"));
							cell = cells.getCell("DB" + String.valueOf(index));
							cell.setValue(rsall.getFloat("slqd"));
							cell = cells.getCell("DC" + String.valueOf(index));
							cell.setValue(rsall.getFloat("slthung"));
							cell = cells.getCell("DD" + String.valueOf(index));
							cell.setValue(rsall.getString("trangthainpp"));
							cell = cells.getCell("DE" + String.valueOf(index));
							cell.setValue(rsall.getFloat("xuatchuyenkho"));
							cell = cells.getCell("DF" + String.valueOf(index));
							cell.setValue(rsall.getFloat("tienxuatck"));
							cell = cells.getCell("DG" + String.valueOf(index));
							cell.setValue(rsall.getFloat("tiennhapck"));	
							cell = cells.getCell("DH" + String.valueOf(index));
							cell.setValue(rsall.getString("nhomsp"));	
							cell = cells.getCell("DI" + String.valueOf(index));
							cell.setValue(rsall.getString("trangthaidms"));	
							index++;
							
						}
					
						//query = query.substring(0,query.length() - 10);
						//rs.close();
					}
					
				}
				//query = query.substring(0,query.length() - 10);
				rs.close();
			 
		 }
			/* query = 
					"\n select kbh.TEN as kenh,v.TEN as vung,kv.TEN as khuvuc,nh.TEN as nhanhang,case when npp.trangthai = 1 then N'Hoạt động' else N'Ngưng hoạt động' end as trangthainpp,cl.TEN as chungloai,dvkd.DIENGIAI as donvikinhdoanh," +
					"\n npp.MA as MaNpp,npp.TEN as TenNPP,kh.TEN as kho, sp.MA as MaSp,sp.TEN as TenSp,dvdl.DIENGIAI as Donvi,dh.dauky, dh.nhaphang, dh.dieuchinh," +
					"\n dh.nhapchuyenkho, dh.nvbhtrahang, dh.hangtravenpp, dh.xuatban, dh.xuatbankm, dh.xuatchuyenkho, dh.chuyenkhonvbh, dh.cuoiky, " +
					"\n isnull(dh.cuoiky * (select  top(1) isnull(GIABANLECHUAN,0) from BANGGIABLC_SANPHAM a inner join BANGGIABANLECHUAN_NPP b on a.BGBLC_FK = b.BANGGIABANLECHUAN_FK " +
					"\n where a.BGBLC_FK = b.BANGGIABANLECHUAN_FK and SANPHAM_FK = sp.PK_SEQ and b.NPP_FK = npp.PK_SEQ ),0) as tiencuoiky," +
					"\n isnull((select  top(1) GIABANLECHUAN from BANGGIABLC_SANPHAM a inner join BANGGIABANLECHUAN_NPP b on a.BGBLC_FK = b.BANGGIABANLECHUAN_FK " +
					"\n where a.BGBLC_FK = b.BANGGIABANLECHUAN_FK and SANPHAM_FK = sp.PK_SEQ and b.NPP_FK = npp.PK_SEQ),0) as Gia, " +
					"\n ISNULL(qc.soluong1, qc1.SOLUONG1) as slqd, ISNULL(qc.soluong2, qc1.SOLUONG2) as slthung,  " +
					"\n isnull(dh.nhaphang * (select  top(1) isnull(GIABANLECHUAN,0) from BANGGIABLC_SANPHAM a inner join BANGGIABANLECHUAN_NPP b on a.BGBLC_FK = b.BANGGIABANLECHUAN_FK " +
					"\n where a.BGBLC_FK = b.BANGGIABANLECHUAN_FK and SANPHAM_FK = sp.PK_SEQ and b.NPP_FK = npp.PK_SEQ ),0) as tiennhap," +
					"\n isnull(dh.dauky * (select  top(1) isnull(GIABANLECHUAN,0) from BANGGIABLC_SANPHAM a inner join BANGGIABANLECHUAN_NPP b on a.BGBLC_FK = b.BANGGIABANLECHUAN_FK " +
					"\n where a.BGBLC_FK = b.BANGGIABANLECHUAN_FK and SANPHAM_FK = sp.PK_SEQ and b.NPP_FK = npp.PK_SEQ ),0) as tiendauky," +
					"\n isnull(dh.xuatban * (select  top(1) isnull(GIABANLECHUAN,0) from BANGGIABLC_SANPHAM a inner join BANGGIABANLECHUAN_NPP b on a.BGBLC_FK = b.BANGGIABANLECHUAN_FK " +
					"\n where a.BGBLC_FK = b.BANGGIABANLECHUAN_FK and SANPHAM_FK = sp.PK_SEQ and b.NPP_FK = npp.PK_SEQ ),0) as tienxuatban," +
					"\n isnull(dh.xuatbankm * (select  top(1) isnull(GIABANLECHUAN,0) from BANGGIABLC_SANPHAM a inner join BANGGIABANLECHUAN_NPP b on a.BGBLC_FK = b.BANGGIABANLECHUAN_FK " +
					"\n where a.BGBLC_FK = b.BANGGIABANLECHUAN_FK and SANPHAM_FK = sp.PK_SEQ and b.NPP_FK = npp.PK_SEQ ),0) as tienxuatkm," +
					"\n isnull(dh.xuatchuyenkho * (select  top(1) isnull(GIABANLECHUAN,0) from BANGGIABLC_SANPHAM a inner join BANGGIABANLECHUAN_NPP b on a.BGBLC_FK = b.BANGGIABANLECHUAN_FK " +
					"\n where a.BGBLC_FK = b.BANGGIABANLECHUAN_FK and SANPHAM_FK = sp.PK_SEQ and b.NPP_FK = npp.PK_SEQ ),0) as tienxuatck," +
					"\n isnull(dh.nhapchuyenkho * (select  top(1) isnull(GIABANLECHUAN,0) from BANGGIABLC_SANPHAM a inner join BANGGIABANLECHUAN_NPP b on a.BGBLC_FK = b.BANGGIABANLECHUAN_FK " +
					"\n where a.BGBLC_FK = b.BANGGIABANLECHUAN_FK and SANPHAM_FK = sp.PK_SEQ and b.NPP_FK = npp.PK_SEQ ),0) as tiennhapck," +
					"\n isnull(dh.dieuchinh * (select  top(1) isnull(GIABANLECHUAN,0) from BANGGIABLC_SANPHAM a inner join BANGGIABANLECHUAN_NPP b on a.BGBLC_FK = b.BANGGIABANLECHUAN_FK " +
					"\n where a.BGBLC_FK = b.BANGGIABANLECHUAN_FK and SANPHAM_FK = sp.PK_SEQ and b.NPP_FK = npp.PK_SEQ ),0) as tiendieuchinh, nha.ngaynhan " +
					"\n from [dbo].[ufn_XNT_TuNgay_DenNgay_FULL_new]('"+nppId+"','"+obj.gettungay()+"','"+obj.getdenngay()+"') dh" +
					"\n inner join SANPHAM sp on sp.PK_SEQ = dh.sanpham_fk" +
					"\n inner join KHO kh on kh.PK_SEQ = dh.kho_fk" +
					"\n inner join KENHBANHANG kbh on kbh.PK_SEQ = dh.kbh_fk" +
					"\n inner join NHAPHANPHOI npp on npp.PK_SEQ  = dh.npp_fk" +
					"\n left join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK" +
					"\n left join VUNG v on kv.VUNG_FK = v.PK_SEQ" +
					"\n left join NHANHANG nh on nh.PK_SEQ = sp.NHANHANG_FK" +
					"\n left join nhaphang_sp nhsp on nhsp.sanpham_fk = sp.pk_seq and nhsp.kho_fk = kh.pk_seq " +
					"\n left join nhaphang nha on nha.pk_seq = nhsp.nhaphang_fk " +
					"\n left join CHUNGLOAI cl on cl.PK_SEQ = sp.CHUNGLOAI_FK" +
					"\n left join DONVIKINHDOANH dvkd on dvkd.PK_SEQ = sp.DVKD_FK" +
					"\n left join DONVIDOLUONG dvdl on sp.DVDL_FK = dvdl.PK_SEQ" +
					"\n left join QUYCACH qc on qc.SANPHAM_FK = sp.PK_SEQ and qc.DVDL1_FK = dvdl.PK_SEQ and qc.DVDL2_FK ='100018' " +
					"\n left join QUYCACH qc1 on qc1.SANPHAM_FK = sp.PK_SEQ and qc1.DVDL1_FK = dvdl.PK_SEQ and qc1.DVDL2_FK= '1200534' " +
					"\n where 1 = 1 and npp.pk_seq in ( select pk_seq from nhaphanphoi a where trangthai = 1 ";
					if(obj.getnppId().length()>0)
						query += " and a.pk_seq = "+obj.getnppId()+" ";
					if(obj.getkenhId() != null)
						if(obj.getkenhId().length() > 0 )
							query += "and exists (select 1 from nhaphanphoi kbh where kbh.kbh_fk = '"+obj.getkenhId()+"' and kbh.pk_seq = a.pk_seq ) ";
					
					if(obj.getkhuvucId() != null)
						if(obj.getkhuvucId() .length() > 0 )
							query += "and a.khuvuc_fk = '"+obj.getkhuvucId()+"' ";
					if(obj.getvungId() != null)
						if(obj.getvungId() .length() > 0 )
							query += "and exists (select 1 from nhaphanphoi npp inner join khuvuc kv on npp.khuvuc_fk = kv.pk_seq where npp.pk_seq = a.pk_seq and kv.vung_fk = '"+obj.getvungId()+"') ";
					query+= " )"; 		
		 }
		 
	 
	 	if(obj.getnhanhangId() != null)
			if(obj.getnhanhangId().length() > 0 )
				query += "and nh.pk_seq = '"+obj.getnhanhangId()+"' ";
		if(obj.getchungloaiId() != null)
			if(obj.getchungloaiId().length() > 0 )
				query += "and cl.pk_seq = '"+obj.getchungloaiId()+"' ";
		if(obj.getdvkdId() != null)
			if(obj.getdvkdId().length() > 0 )
				query += "and dvkd.pk_seq = '"+obj.getdvkdId()+"' ";
		if(obj.getkhoId() != null)
			if(obj.getkhoId().length() > 0 )
				query += "and kh.pk_seq = '"+obj.getkhoId()+"' ";
		if(obj.getkhuvucId() != null)
			if(obj.getkhuvucId() .length() > 0 )
				query += "and kv.pk_seq = '"+obj.getkhuvucId()+"' ";
		if(obj.getvungId() != null)
			if(obj.getvungId() .length() > 0 )
				query += "and v.pk_seq = '"+obj.getvungId()+"' ";
		if(obj.getsanphamId() != null)
			if(obj.getsanphamId() .length() > 0 )
				query += "and sp.pk_seq = '"+obj.getsanphamId()+"' ";
		

			
		
		
		System.out.println("Query XNT: "+query);
		rs = db.get(query);
		
		int index = 2;
		Cell cell = null;
		while (rs.next()) {
			cell = cells.getCell("CA" + String.valueOf(index));
			cell.setValue(rs.getString("kenh"));
			cell = cells.getCell("CB" + String.valueOf(index));
			cell.setValue(rs.getString("vung"));
			cell = cells.getCell("CC" + String.valueOf(index));
			cell.setValue(rs.getString("khuvuc"));
			cell = cells.getCell("CD" + String.valueOf(index));
			cell.setValue(rs.getString("nhanhang"));
			cell = cells.getCell("CE" + String.valueOf(index));
			cell.setValue(rs.getString("chungloai"));
			cell = cells.getCell("CF" + String.valueOf(index));
			cell.setValue(rs.getString("donvikinhdoanh"));
			cell = cells.getCell("CG" + String.valueOf(index));
			cell.setValue(rs.getString("manpp"));
			cell = cells.getCell("CH" + String.valueOf(index));
			cell.setValue(rs.getString("tennpp"));
			cell = cells.getCell("CI" + String.valueOf(index));
			cell.setValue(rs.getString("kho"));
			cell = cells.getCell("CJ" + String.valueOf(index));
			cell.setValue(rs.getString("masp"));
			cell = cells.getCell("CK" + String.valueOf(index));
			cell.setValue(rs.getString("tensp"));
			cell = cells.getCell("CL" + String.valueOf(index));
			cell.setValue(rs.getString("donvi"));
			cell = cells.getCell("CM" + String.valueOf(index));
			cell.setValue(Float.parseFloat(rs.getString("dauky")));
			cell = cells.getCell("CN" + String.valueOf(index));
			cell.setValue(Float.parseFloat(rs.getString("nhaphang"))); // Quantity
			cell = cells.getCell("CO" + String.valueOf(index));
			cell.setValue(Float.parseFloat(rs.getString("dieuchinh"))); // Piece
			cell = cells.getCell("CP" + String.valueOf(index));
			cell.setValue(rs.getFloat("nhapchuyenkho")); 
			cell = cells.getCell("CQ" + String.valueOf(index));
			cell.setValue(rs.getFloat("tiendauky")); 
			cell = cells.getCell("CR" + String.valueOf(index));
			cell.setValue(rs.getFloat("tiennhap"));
			cell = cells.getCell("CS" + String.valueOf(index));
			cell.setValue(rs.getFloat("xuatban"));
			cell = cells.getCell("CT" + String.valueOf(index));
			cell.setValue(rs.getFloat("xuatbankm")); 
			cell = cells.getCell("CU" + String.valueOf(index));
			cell.setValue(rs.getFloat("tiendieuchinh"));
			cell = cells.getCell("CV" + String.valueOf(index));
			cell.setValue(rs.getFloat("tienxuatkm"));
			cell = cells.getCell("CW" + String.valueOf(index));
			cell.setValue(rs.getFloat("tienxuatban"));
			cell = cells.getCell("CX" + String.valueOf(index));
			cell.setValue(rs.getFloat("tiencuoiky"));
			cell = cells.getCell("CY" + String.valueOf(index));
			cell.setValue(rs.getFloat("cuoiky"));
			cell = cells.getCell("CZ" + String.valueOf(index));
			cell.setValue(rs.getString("ngaynhan"));
			cell = cells.getCell("DA" + String.valueOf(index));
			cell.setValue(rs.getFloat("gia"));
			cell = cells.getCell("DB" + String.valueOf(index));
			cell.setValue(rs.getFloat("slqd"));
			cell = cells.getCell("DC" + String.valueOf(index));
			cell.setValue(rs.getFloat("slthung"));
			cell = cells.getCell("DD" + String.valueOf(index));
			cell.setValue(rs.getString("trangthainpp"));
			cell = cells.getCell("DE" + String.valueOf(index));
			cell.setValue(rs.getFloat("xuatchuyenkho"));
			cell = cells.getCell("DF" + String.valueOf(index));
			cell.setValue(rs.getFloat("tienxuatck"));
			cell = cells.getCell("DG" + String.valueOf(index));
			cell.setValue(rs.getFloat("tiennhapck"));
			index++;
		}*/
		if(rs != null) rs.close();
		if(rsall != null) rsall.close();
		if(db != null) db.shutDown();
		
		ReportAPI.setHidden(workbook, 79);
	}

	private void CreateHeader(Workbook workbook, String giatinh, IStockintransit obj)
			throws Exception {

		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");

		Cells cells = worksheet.getCells();
		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("A1");
		ReportAPI.getCellStyle(cell, Color.RED, true, 14, "Xuất Nhập Tồn");
		cell = cells.getCell("A2");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Từ ngày : " + obj.gettungay() + "   Đến ngày: " + obj.getdenngay());
		cell = cells.getCell("A3");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày tạo : " + obj.getDateTime());
		cell = cells.getCell("B3");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Tạo bởi : " + obj.getuserTen());
		cell = cells.getCell("CA1");
		cell.setValue("Kenh Ban Hang");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("CB1");
		cell.setValue("Vung");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("CC1");
		cell.setValue("Khu Vuc");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("CD1");
		cell.setValue("Nhan Hang");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("CE1");
		cell.setValue("Chung Loai");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("CF1");
		cell.setValue("Don vi kinh doanh");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("CG1");
		cell.setValue("Ma Nha Phan Phoi");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("CH1");
		cell.setValue("Ten Nha Phan Phoi");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("CI1");
		cell.setValue("Kho");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("CJ1");
		cell.setValue("Ma San Pham");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("CK1");
		cell.setValue("Ten San Pham");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("CL1");
		cell.setValue("Don vi");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("CM1");
		cell.setValue("Ton dau");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("CN1");
		cell.setValue("Nhap hang ");
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("CO1");
		cell.setValue("Dieu chinh");
		ReportAPI.setCellHeader(cell);
		
		cell = cells.getCell("CP1");
		cell.setValue("Chuyen kho (+)");
		ReportAPI.setCellHeader(cell);

		cell = cells.getCell("CQ1");
		cell.setValue("Tien Ton Dau");
		ReportAPI.setCellHeader(cell);
		
		cell = cells.getCell("CR1");
		cell.setValue("Tien Nhap Hang");
		ReportAPI.setCellHeader(cell);
		
		cell = cells.getCell("CS1");
		cell.setValue("Xuat hang ban ");
		ReportAPI.setCellHeader(cell);
		
		cell = cells.getCell("CT1");
		cell.setValue("Xuat hang KM ");
		ReportAPI.setCellHeader(cell);
		
		cell = cells.getCell("CU1");
		cell.setValue("Tien Dieu Chinh");
		ReportAPI.setCellHeader(cell);
		
		cell = cells.getCell("CV1");
		cell.setValue("Tien Xuat Ban KM ");
		ReportAPI.setCellHeader(cell);
		
		cell = cells.getCell("CW1");
		cell.setValue("Tien Xuat Ban");
		ReportAPI.setCellHeader(cell);
		
		cell = cells.getCell("CX1");
		cell.setValue("Tien Ton Cuoi");
		ReportAPI.setCellHeader(cell);
		
		cell = cells.getCell("CY1");
		cell.setValue("Ton cuoi");
		ReportAPI.setCellHeader(cell);
		
		cell = cells.getCell("CZ1");
		cell.setValue("Ngay Nhap Hang");
		ReportAPI.setCellHeader(cell);

		String tenGia = "";
		if(giatinh.equals("1")) tenGia = "GiaMuaNPP";
		else tenGia = "GiaBanNPP";		
		cell = cells.getCell("DA1");
		cell.setValue(tenGia);
		ReportAPI.setCellHeader(cell);
		
		cell = cells.getCell("DB1");
		cell.setValue("So luong quy doi");
		ReportAPI.setCellHeader(cell);
		
		cell = cells.getCell("DC1");
		cell.setValue("Thung/Bao");
		ReportAPI.setCellHeader(cell);
		

		cell = cells.getCell("DD1");
		cell.setValue("Trang Thai NPP");
		ReportAPI.setCellHeader(cell);
		
		cell = cells.getCell("DE1");
		cell.setValue("Chuyen Kho (-)");
		ReportAPI.setCellHeader(cell);
		
		cell = cells.getCell("DF1");
		cell.setValue("Tien Xuat Chuyen Kho");
		ReportAPI.setCellHeader(cell);
		
		
		cell = cells.getCell("DG1");
		cell.setValue("Tien Nhap Chuyen Kho");
		ReportAPI.setCellHeader(cell);
		
		cell = cells.getCell("DH1");
		cell.setValue("Nhom San Pham");
		ReportAPI.setCellHeader(cell);
		
		
		cell = cells.getCell("DI1");
		cell.setValue("Trang Thai DMS");
		ReportAPI.setCellHeader(cell);
		
	}
}
