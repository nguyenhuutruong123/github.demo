package geso.dms.center.servlets.reports;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.Utility;
import geso.dms.center.util.WebService;
import geso.dms.distributor.beans.report.Ireport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DateFormat;
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

public class SecondarySalesPIRTT_V1 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String setQueryKhongPivot(String giatinh, IStockintransit obj,int ischitiet)
	{
		geso.dms.center.util.Utility Uti_center = new geso.dms.center.util.Utility();

		String query =  "\n select kbh.TEN KBH,v.ten Vung,kv.ten KV,tt.ten TinhThanh, dvkd.DIENGIAI DVKD " + 
		"\n 	,npp.MANPP ,npp.ten NPP, nganh.ten NganhHang, nhan.ten NhanHang " + 
		"\n 	,case npp.trangthai when 1 then N'Hoạt động' else N'Ngưng hoạt động' end trangthai,k.Ten Kho, sp.Ma MaSP,sp.ten TenSP,dvdl.donvi DVDL ,dg.dongia,data.ngaynhapkho " + 
		"\n 	,data.dauky,data.nhaphang,data.Dieuchinh,data.xuatban,data.xuatbankm,data.cuoiky " + 
		"\n 	,data.dauky * dg.dongia TienDauKy ,data.nhaphang* dg.dongia TienNhapHang ,data.Dieuchinh* dg.dongia TienDieuChinh  " + 
		"\n 	,data.xuatban* dg.dongia TienXuatBan ,data.xuatbankm* dg.dongia TienXuatKM ,data.cuoiky* dg.dongia TienCuoiKy " + 
		"\n 	,chuanhan.HangChuaNhapKho, chuanhan.HangChuaNhapKho * dg.dongia TienHangChuaNhapKho " + 
		"\n from nhaphanphoi npp  " + 
		"\n cross apply [dbo].[ufn_XNT_TuNgay_DenNgay_FULL_Chitiet_New](npp.pk_seq,'"+obj.gettungay()+"','"+obj.getdenngay()+"') data " + 
		"\n inner join sanpham sp on sp.pk_seq = data.sanpham_fk " + 
		"\n inner join donvidoluong dvdl on dvdl.pk_seq = sp.dvdl_fk " + 
		"\n inner join kho k on k.pk_seq = data.kho_fk " + 
		"\n left join nganhhang nganh on nganh.pk_seq = sp.nganhhang_fk " + 
		"\n left join nhanhang nhan on nhan.pk_seq = sp.nhanhang_fk " + 
		"\n inner join donvikinhdoanh dvkd on dvkd.pk_seq = sp.dvkd_fk " + 
		"\n inner join kenhbanhang kbh on kbh.pk_Seq = data.kbh_fk " + 
		"\n inner join khuvuc kv on kv.pk_seq = npp.khuvuc_fk " + 
		"\n inner join vung v on v.pk_seq = kv.vung_fk " + 
		"\n inner join tinhthanh tt on tt.pk_seq = npp.tinhthanh_fk " + 
		( giatinh.equals("1") ? 
				"\n outer apply ( select [dbo].[GiaBanLeNppSanPham](null,data.kbh_fk,data.npp_fk,data.sanpham_fk,dbo.getNgayHienTai() ) dongia) dg " :
				"\n outer apply ( select [dbo].[GiaMuaNppSanPham](data.kbh_fk,data.npp_fk,data.sanpham_fk,dbo.getNgayHienTai() ) dongia) dg " 
		) + 
		"\n outer apply ( " + 
		"\n 	select  sum(soluong) HangChuaNhapKho " + 
		"\n 	from nhaphang nh  " + 
		"\n 	inner join nhaphang_sp nhsp on nh.pk_seq = nhsp.nhaphang_fk " + 
		"\n 	where nh.npp_fk = npp.pk_seq and nh.trangthai = 0 and nh.ngaychungtu  >='"+obj.getdenngay()+"' and  nh.ngaychungtu  <='"+obj.gettungay()+"'  " + 
		"\n )chuanhan " + 
		"\n where npp.trangthai = 1  and npp.pk_seq in " + Uti_center.quyen_npp(obj.getuserId()) +" ";

		if (obj.getnppId().length()>0)
			query += " and npp.pk_seq = '"+obj.getnppId()+"' ";

		if (obj.getkhuvucId() != null && obj.getkhuvucId() .length() > 0)
			query += "and npp.khuvuc_fk = '"+obj.getkhuvucId()+"' ";
		
		if (obj.getkenhId() != null && obj.getkenhId().length() > 0)
			query += "and exists (select 1 from nhapp_kbh kbh where kbh.kbh_fk = '"+obj.getkenhId()+"' and npp_fk = '"+obj.getnppId()+"') ";
		
		if (obj.getvungId() != null && obj.getvungId() .length() > 0)
			query += " and v.pk_seq = '"+obj.getvungId()+"' ";

		return query;
	}

	public SecondarySalesPIRTT_V1() {
		super();
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
		obj.init();
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/AHF/pages/Center/SecondarySalesPurchaseInventoryReport_V1.jsp";
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
		if (giatinh == null)
			giatinh = "1";

		if (!tungay.equals("") && !denngay.equals("")) 
		{
			String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));

			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			if (action.equals("tao"))
			{
				try 
				{
					//String query = setQuery(obj, tungay, denngay, st, st_v,st_loc);
					String query = setQuery(giatinh, obj, 0);

					String filename = this.getDateTime();
					System.out.println("query: " + query);
					//D:\ServicesPDA\Baocao\BCNhapxuatton
					File theDir = new File(getServletContext().getInitParameter("pathBaoCao") + "\\BCNhapxuatton"+filename);
					theDir.mkdir();
					CreatePivotTable(obj,filename,giatinh);

					try {
						WebService.BCNhapxuatton(getServletContext().getInitParameter("pathBaoCao") + "\\BCNhapxuatton"+filename+"\\","BCNhapxuatton"+filename,".xlsm", query,obj.getdiscount());
					}
					catch (Exception  e)
					{
						e.printStackTrace();
					}
					
					obj.setuserId(userId);
					obj.init();	 
					obj.setdiscount(instransit);
					obj.setUrl(getServletContext().getInitParameter("pathServicesFile") + "/BCNhapxuatton"+filename+"\\"+"BCNhapxuatton"+filename+".xlsm");
					obj.setKey(getServletContext().getInitParameter("pathBaoCao") + "\\BCNhapxuatton"+filename+"\\success.txt");
					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);
					String nextJSP = "/AHF/pages/Center/SecondarySalesPurchaseInventoryReport_V1.jsp";
					response.sendRedirect(nextJSP);

					return;
				} 
				catch (Exception ex) 
				{
					ex.printStackTrace();
					System.out.println("Error Here : "+ex.toString());
					request.getSession().setAttribute("errors", ex.getMessage());
				}
			} 
			else if (action.equals("taochitiet"))
			{
				try 
				{
					//String query = setQuery(obj, tungay, denngay, st, st_v, st_loc);
					//String query = setQuery(giatinh, obj, 1);
					String query = setQueryKhongPivot(giatinh, obj,0);
					String filename = this.getDateTime();
					System.out.println("setQueryKhongPivot: " + query);
					//D:\ServicesPDA\Baocao\BCNhapxuatton
					File theDir = new File(getServletContext().getInitParameter("pathBaoCao") + "\\BCNhapxuattonchitiet"+filename);
					theDir.mkdir();
					CreatePivotTablechitiet_KhongPivot(obj, filename);

					System.out.println("Error Here : "+this.getDateTime() + ";;" + obj.getdiscount());

					try {
						WebService.BCNhapxuattonchitiet(getServletContext().getInitParameter("pathBaoCao")   +"\\BCNhapxuattonchitiet"+filename+"\\","BCNhapxuattonchitiet"+filename,".xlsm", query,obj.getdiscount());
					}
					catch (Exception  e)
					{
						e.printStackTrace();
					}
					obj.setuserId(userId);
					obj.init();	 
					obj.setdiscount(instransit);
					obj.setUrl(getServletContext().getInitParameter("pathServicesFile") + "/BCNhapxuattonchitiet"+filename+"\\"+"BCNhapxuattonchitiet"+filename+".xlsm");
					obj.setKey(getServletContext().getInitParameter("pathBaoCao") + "\\BCNhapxuattonchitiet"+filename+"\\success.txt");
					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);
					String nextJSP = "/AHF/pages/Center/SecondarySalesPurchaseInventoryReport_V1.jsp";
					response.sendRedirect(nextJSP);

					return;
				} 
				catch (Exception ex) 
				{
					ex.printStackTrace();
					request.getSession().setAttribute("errors", ex.getMessage());
				}
			}
			else if (action.equals("tao2")) {

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
					ex.printStackTrace();
					request.getSession().setAttribute("errors", ex.getMessage());
				}
				return;
			}
		}
		
		obj.init();
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/AHF/pages/Center/SecondarySalesPurchaseInventoryReport_V1.jsp";
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
			String strfstream;
			System.out.println("_______+_in ra"+obj.getdiscount());
			if (obj.getdiscount().equals("0")) {
				strfstream = getServletContext().getInitParameter("path") + "\\BaoCaoXuatNhapTon1(TT).xlsm";			
			}else{
				strfstream = getServletContext().getInitParameter("path") + "\\BaoCaoXuatNhapTon(TT).xlsm";			
			}
			FileInputStream fstream = new FileInputStream(strfstream);	
			//File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\BaoCaoXuatNhapTon(TT).xlsm");
			//FileInputStream fstream = new FileInputStream(f) ;

			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

			CreateHeader(workbook,giatinh, obj,1);
			FillData(workbook, giatinh, obj);

			workbook.save(out);
			fstream.close();

		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
			obj.setMsg("Không có báo cáo trong thời gian này.");
			throw new Exception("Error Message: " + ex.getMessage());
		}	
	}

	private String setQuery(String giatinh, IStockintransit obj,int ischitiet)
	{
		geso.dms.center.util.Utility Uti_center = new geso.dms.center.util.Utility();
		dbutils db = new dbutils();
		String query = "PRO_GET_REPORT_XNT ";
		if (ischitiet == 1)
		{
			query = "PRO_GET_REPORT_XNT_CHITIET ";
		}

		String sql = "\n select a.pk_seq as npp_fk " +
		"\n from NHAPHANPHOI a " +  
		"\n inner join " +
		"\n ( " +
		"\n     select NPP_FK, max(NGAYKS) as ngayks " +
		"\n     from KHOASONGAY where NGAYKS < '"+obj.gettungay()+"' " +
		"\n     group by NPP_FK " +
		"\n     union " +
		"\n     select NPP_FK, min(NGAYKS) as ngayks " +
		"\n     from KHOASONGAY " +
		"\n     where NGAYKS <='"+obj.getdenngay()+"' " +
		"\n     group by NPP_FK " +
		"\n     having min(ngayks) >= '"+obj.gettungay()+"' " +
		"\n ) b on a.pk_seq = b.NPP_FK ";

		sql += " and a.pk_seq in " + Uti_center.quyen_npp(obj.getuserId()) +"";

		if (obj.getnppId().length()>0)
			sql += " and a.pk_seq = '"+obj.getnppId()+"' ";

		if (obj.getkhuvucId() != null && obj.getkhuvucId() .length() > 0)
			sql += "and a.khuvuc_fk = '"+obj.getkhuvucId()+"' ";

		if (obj.getkenhId() != null && obj.getkenhId().length() > 0)
			sql += "and exists (select 1 from nhapp_kbh kbh where kbh.kbh_fk = '"+obj.getkenhId()+"' and npp_fk = '"+obj.getnppId()+"') ";
		if (obj.getvungId() != null && obj.getvungId() .length() > 0)
			sql += "and exists (select 1 from nhaphanphoi npp inner join khuvuc kv on npp.khuvuc_fk = kv.pk_seq where npp.pk_seq = a.pk_seq and kv.vung_fk = '"+obj.getvungId()+"') ";
		System.out.println("rsnpp: " + sql);

		ResultSet rsnpp = db.get(sql);


		String npp_fk="";
		try {
			while(rsnpp.next()) {
				if (npp_fk.equals("")) {
					npp_fk= rsnpp.getString("npp_fk");
				}
				else{
					npp_fk+=","+rsnpp.getString("npp_fk");
				}

			}
			rsnpp.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		int index = 2;

		System.out.println("Query: " + query);
		String param[] = new String[9];
		param[0] = npp_fk;
		param[1] =  "'" + obj.gettungay() + "'";
		param[2] = "'" + obj.getdenngay() + "'";
		param[3] = (obj.getdvkdId() != null &&  obj.getdvkdId().length() > 0 ? obj.getdvkdId():null );
		param[4] = (obj.getnhanhangId() != null &&  obj.getnhanhangId().length() > 0 ? obj.getnhanhangId():null );
		param[5] = (obj.getchungloaiId() != null &&  obj.getchungloaiId().length() > 0 ? obj.getchungloaiId():null );
		param[6] = (obj.getkhoId() != null &&  obj.getkhoId().length() > 0 ? obj.getkhoId():null );
		param[7] = (obj.getsanphamId() != null &&  obj.getsanphamId().length() > 0 ? obj.getsanphamId():null );
		param[8] = (giatinh);

		for (int i = 0; i <= 8; i++)
		{
			if (i == 0)
			{
				if (param[0].length() == 0)
				{
					param[0] = null;
				}
				else
				{
					param[0] = "'" + param[0] + "'";
				}
			}

			if (i == 8)
			{
				query = query + param[i] ;
			}
			else
			{
				query = query + param[i] + ",";
			}
		}

		return query;
	}

	private void FillData(Workbook workbook, String giatinh, IStockintransit obj) throws Exception 
	{
		giatinh = "0";
		System.out.println("Đã vào đây r: "+giatinh);
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		geso.dms.center.util.Utility Uti_center = new geso.dms.center.util.Utility();
		Cells cells = worksheet.getCells();
		dbutils db = new dbutils();

		System.out.println("NPPID: "+obj.getnppId());
		String query = "";
		/*String nppId="0";
		if (obj.getnppId().length() > 0)
		{
			nppId=obj.getnppId();
		}
		 */

		ResultSet rsall = null;	

		// chir layas nhung nha phan phoi da khoa so
		String	sql = "\n select  a.pk_seq as npp_fk  from NHAPHANPHOI a " +  
		"\n inner join  " +
		"\n ( " +
		"\n     select NPP_FK, max(NGAYKS) as ngayks "+
		"\n     from KHOASONGAY where NGAYKS < '"+obj.gettungay()+"' "+
		"\n     group by NPP_FK " +
		"\n     union " +
		"\n     select NPP_FK, min(NGAYKS) as ngayks " +
		"\n     from KHOASONGAY " +
		"\n     where NGAYKS <='"+obj.getdenngay()+"' " +
		"\n     group by NPP_FK " +
		"\n     having min(ngayks) > = '"+obj.gettungay()+"' " +
		"\n ) b on a.pk_seq = b.NPP_FK ";

		sql += " and a.pk_seq in " + Uti_center.quyen_npp(obj.getuserId()) +"";
		
		if (obj.getnppId().length()>0)
			sql += " and a.pk_seq = '"+obj.getnppId()+"' ";
		
		if (obj.getkhuvucId() != null && obj.getkhuvucId() .length() > 0)
			sql += "and a.khuvuc_fk = '"+obj.getkhuvucId()+"' ";
		
		if (obj.getkenhId() != null && obj.getkenhId().length() > 0)
			sql += "and exists (select 1 from nhapp_kbh kbh where kbh.kbh_fk = '"+obj.getkenhId()+"' and npp_fk = '"+obj.getnppId()+"') ";
		
		if (obj.getvungId() != null && obj.getvungId() .length() > 0)
			sql += "and exists (select 1 from nhaphanphoi npp inner join khuvuc kv on npp.khuvuc_fk = kv.pk_seq where npp.pk_seq = a.pk_seq and kv.vung_fk = '"+obj.getvungId()+"') ";
		
		System.out.println("rsnpp: " + sql);
		ResultSet rsnpp = db.get(sql);
		String npp_fk = "";
		while (rsnpp.next()) {
			if (npp_fk.equals("")) {
				npp_fk = rsnpp.getString("npp_fk");
			}
			else {
				npp_fk += "," + rsnpp.getString("npp_fk");
			}
		}
		rsnpp.close();

		int index = 2;

		System.out.println("_+____query___"+ query);
		String param[] =new String[9];
		param[0] = npp_fk;
		param[1] = obj.gettungay();
		param[2] = obj.getdenngay();
		param[3] = (obj.getdvkdId() != null &&  obj.getdvkdId().length() > 0 ? obj.getdvkdId():null );
		param[4] = (obj.getnhanhangId() != null &&  obj.getnhanhangId().length() > 0 ? obj.getnhanhangId():null );
		param[5] = (obj.getchungloaiId() != null &&  obj.getchungloaiId().length() > 0 ? obj.getchungloaiId():null );
		param[6] = (obj.getkhoId() != null &&  obj.getkhoId().length() > 0 ? obj.getkhoId():null );
		param[7] = (obj.getsanphamId() != null &&  obj.getsanphamId().length() > 0 ? obj.getsanphamId():null );
		param[8] = ("0");
		rsall = db.getRsByPro("PRO_GET_REPORT_XNT", param);

		Cell cell;
		if ( rsall != null)
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

				if (obj.getdiscount().equals("0")) {
					cell = cells.getCell("CX" + String.valueOf(index));
					cell.setValue(rsall.getFloat("tiencuoiky")+rsall.getFloat("TIENDIDUONG"));


					cell = cells.getCell("CY" + String.valueOf(index));
					cell.setValue(rsall.getFloat("cuoiky")+rsall.getFloat("HANGDIDUONG"));
				}else{
					cell = cells.getCell("CX" + String.valueOf(index));
					cell.setValue(rsall.getFloat("tiencuoiky"));


					cell = cells.getCell("CY" + String.valueOf(index));
					cell.setValue(rsall.getFloat("cuoiky"));
				}


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
				cell = cells.getCell("DJ" + String.valueOf(index));
				cell.setValue(rsall.getString("ngaynhapkho"));	
				
				if (obj.getdiscount().equals("0")) {
					cell = cells.getCell("DK" + String.valueOf(index));
					cell.setValue(rsall.getFloat("HANGDIDUONG"));	
					cell = cells.getCell("DL" + String.valueOf(index));
					cell.setValue(rsall.getFloat("TIENDIDUONG"));	
				}
				index++;

			}rsall.close();

			if (rsall != null) rsall.close();
			if (db != null) db.shutDown();

			ReportAPI.setHidden(workbook, 79);
	}
	
	private void CreateHeader(Workbook workbook, String giatinh, IStockintransit obj, int tongquat) throws Exception {

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
		
		cell = cells.getCell("CA7");
		cell.setValue("Kenh Ban Hang");
		ReportAPI.setCellHeader(cell);
		
		cell = cells.getCell("CB7");
		cell.setValue("Vung");
		ReportAPI.setCellHeader(cell);
		
		cell = cells.getCell("CC7");
		cell.setValue("Khu Vuc");
		ReportAPI.setCellHeader(cell);
		
		cell = cells.getCell("CD7");
		cell.setValue("Nhan Hang");
		ReportAPI.setCellHeader(cell);
		
		cell = cells.getCell("CE7");
		cell.setValue("Chung Loai");
		ReportAPI.setCellHeader(cell);
		
		cell = cells.getCell("CF7");
		cell.setValue("Don vi kinh doanh");
		ReportAPI.setCellHeader(cell);
		
		cell = cells.getCell("CG7");
		cell.setValue("Ma Nha Phan Phoi");
		ReportAPI.setCellHeader(cell);
		
		cell = cells.getCell("CH7");
		cell.setValue("Ten Nha Phan Phoi");
		ReportAPI.setCellHeader(cell);
		
		cell = cells.getCell("CI7");
		cell.setValue("Kho");
		ReportAPI.setCellHeader(cell);
		
		cell = cells.getCell("CJ7");
		cell.setValue("Ma San Pham");
		ReportAPI.setCellHeader(cell);
		
		cell = cells.getCell("CK7");
		cell.setValue("Ten San Pham");
		ReportAPI.setCellHeader(cell);
		
		cell = cells.getCell("CL7");
		cell.setValue("Don vi");
		ReportAPI.setCellHeader(cell);
		
		cell = cells.getCell("CM7");
		cell.setValue("Ton dau");
		ReportAPI.setCellHeader(cell);
		
		cell = cells.getCell("CN7");
		cell.setValue("Nhap hang ");
		ReportAPI.setCellHeader(cell);
		
		cell = cells.getCell("CO7");
		cell.setValue("Dieu chinh");
		ReportAPI.setCellHeader(cell);

		cell = cells.getCell("CP7");
		cell.setValue("Chuyen kho (+)");
		ReportAPI.setCellHeader(cell);

		cell = cells.getCell("CQ7");
		cell.setValue("Tien Ton Dau");
		ReportAPI.setCellHeader(cell);

		cell = cells.getCell("CR7");
		cell.setValue("Tien Nhap Hang");
		ReportAPI.setCellHeader(cell);

		cell = cells.getCell("CS7");
		cell.setValue("Xuat hang ban ");
		ReportAPI.setCellHeader(cell);

		cell = cells.getCell("CT7");
		cell.setValue("Xuat hang KM ");
		ReportAPI.setCellHeader(cell);

		cell = cells.getCell("CU7");
		cell.setValue("Tien Dieu Chinh");
		ReportAPI.setCellHeader(cell);

		cell = cells.getCell("CV7");
		cell.setValue("Tien Xuat Ban KM ");
		ReportAPI.setCellHeader(cell);

		cell = cells.getCell("CW7");
		cell.setValue("Tien Xuat Ban");
		ReportAPI.setCellHeader(cell);

		cell = cells.getCell("CX7");
		cell.setValue("Tien Ton Cuoi");
		ReportAPI.setCellHeader(cell);

		cell = cells.getCell("CY7");
		cell.setValue("Ton cuoi");
		ReportAPI.setCellHeader(cell);

		cell = cells.getCell("CZ7");
		cell.setValue("Ngay Nhap Hang");
		ReportAPI.setCellHeader(cell);

		System.out.println("giatinh: " + giatinh);

		String tenGia = "";
		//if (giatinh.equals("1")) tenGia = "GiaMuaNPP";
		//else 
		tenGia = "GiaBanNPP";		
		cell = cells.getCell("DA7");
		cell.setValue(tenGia);
		ReportAPI.setCellHeader(cell);

		cell = cells.getCell("DB7");
		cell.setValue("So luong quy doi");
		ReportAPI.setCellHeader(cell);

		cell = cells.getCell("DC7");
		cell.setValue("Thung/Bao");
		ReportAPI.setCellHeader(cell);

		cell = cells.getCell("DD7");
		cell.setValue("Trang Thai NPP");
		ReportAPI.setCellHeader(cell);

		cell = cells.getCell("DE7");
		cell.setValue("Chuyen Kho (-)");
		ReportAPI.setCellHeader(cell);

		cell = cells.getCell("DF7");
		cell.setValue("Tien Xuat Chuyen Kho");
		ReportAPI.setCellHeader(cell);

		cell = cells.getCell("DG7");
		cell.setValue("Tien Nhap Chuyen Kho");
		ReportAPI.setCellHeader(cell);

		cell = cells.getCell("DH7");
		cell.setValue("Nhom San Pham");
		ReportAPI.setCellHeader(cell);

		cell = cells.getCell("DI7");
		cell.setValue("Trang Thai DMS");
		ReportAPI.setCellHeader(cell);

		if (tongquat == 0) {
			cell = cells.getCell("DJ7");
			cell.setValue("Ngay Nhap Kho");
			ReportAPI.setCellHeader(cell);

			if (obj.getdiscount().equals("0")) {
				cell = cells.getCell("DK7");
				cell.setValue("Hang Chua Nhap Kho");
				ReportAPI.setCellHeader(cell);
				cell = cells.getCell("DL7");
				cell.setValue("Tien Chua Nhap Kho");
				ReportAPI.setCellHeader(cell);
			}
		}
		else
		{
			if (obj.getdiscount().equals("0")) {
				cell = cells.getCell("DJ7");
				cell.setValue("Hang Chua Nhap Kho");
				ReportAPI.setCellHeader(cell);
				cell = cells.getCell("DK7");
				cell.setValue("Tien Chua Nhap Kho");
				ReportAPI.setCellHeader(cell);
			}
		}
	}
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	private void CreatePivotTablechitiet_KhongPivot(IStockintransit obj,String filename) throws Exception {

		OutputStream out;
		try 
		{				
			out = new FileOutputStream(getServletContext().getInitParameter("pathBaoCao")   +"\\BCNhapxuattonchitiet"+filename+"\\BCNhapxuattonchitiet"+filename+".xlsm");
			FileInputStream fstream = new FileInputStream( getServletContext().getInitParameter("path")+ "\\BCXuatNhapTonKhongPivot.xlsm");

			Workbook workbook = new Workbook();
			try {
				workbook.open(fstream);
				workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
				CreateHeader_KhongPivot(workbook,obj); 
			}
			catch (Exception e) {
				fstream.close();
			}

			workbook.save(out);
			fstream.close();
			out.close();
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error Here : "+ex.toString());
			throw new Exception("Error Message: " + ex.getMessage());
		}
	}
	
	private void CreateHeader_KhongPivot(Workbook workbook, IStockintransit obj) throws Exception 
	{

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
	}

	private void CreatePivotTable(IStockintransit obj,String filename, String giatinh) throws Exception {

		OutputStream out;
		try 
		{				
			out = new FileOutputStream(getServletContext().getInitParameter("pathBaoCao")   +"\\BCNhapxuatton"+filename+"\\BCNhapxuatton"+filename+".xlsm");
			FileInputStream fstream;			
			if (obj.getdiscount().equals("0")) {
				fstream = new FileInputStream( getServletContext().getInitParameter("path")+ "\\BaoCaoXuatNhapTon1(TT).xlsm");
			}
			else {
				fstream = new FileInputStream( getServletContext().getInitParameter("path") + "\\BaoCaoXuatNhapTon(TT).xlsm");			
			}

			Workbook workbook = new Workbook();
			try {
				workbook.open(fstream);
				workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
				CreateHeader(workbook,giatinh,obj,1); 
			}
			catch (Exception e) {
				fstream.close();
			}

			workbook.save(out);
			fstream.close();
			out.close();
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error Here : "+ex.toString());
			throw new Exception("Error Message: " + ex.getMessage());
		}
	}
}
