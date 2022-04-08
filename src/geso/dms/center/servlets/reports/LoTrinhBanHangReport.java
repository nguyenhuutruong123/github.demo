package geso.dms.center.servlets.reports;

import geso.dms.center.beans.lotrinh.*;
import geso.dms.center.beans.lotrinh.imp.*;
import geso.dms.center.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;

import jxl.write.Number;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class LoTrinhBanHangReport extends HttpServlet
{

	private static final long serialVersionUID = 1L;
	Utility util = new Utility();
	public LoTrinhBanHangReport()
	{
		super();
	}	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		// PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String userTen = (String) session.getAttribute("userTen");
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		session.setAttribute("userId", userId);
		session.setAttribute("tungay", "");
		session.setAttribute("denngay", "");
		session.setAttribute("loi", "");
		session.setAttribute("userTen", userTen);

		String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
		if(view == null)
			view = "";

		ILoTrinh obj = new LoTrinh();
		obj.setUserId(userId);
		obj.setStatus("1");
		obj.setView(view);
		obj.init();

		session.setAttribute("obj", obj);
		String nextJSP = "/AHF/pages/Center/LoTrinhBanHangReport.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		// PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		ILoTrinh obj = new LoTrinh();
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")==null?"":request.getParameter("userId")));    
		obj.setUserId(userId);
		String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));

		String kenhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")));
		if (kenhId == null)
			kenhId = "";
		obj.setKenhId(kenhId);

		if (nppId == null)
			nppId = "";
		obj.setnppId(nppId);

		String vitri = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vitri")));
		if(vitri == null)
			vitri = "";
		obj.setVitri(vitri);

		String ddkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdId")));
		if (ddkdId == null)
			ddkdId = "";
		obj.setddkdId(ddkdId);

		String tuyenId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tuyenId")));
		if (tuyenId == null)
			tuyenId = "";
		obj.settuyenId(tuyenId);

		String tungay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"));
		if(tungay == null) tungay = ""; else tungay = tungay.trim();
		obj.setTungay(tungay);

		String denngay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay"));
		if(denngay == null || denngay.trim().length() <= 0) 
			denngay = getDateTime();
		else 
			denngay = denngay.trim();
		obj.setDenngay(denngay);

		String vungId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")));

		if (vungId == null)
			vungId = "";
		obj.setvungId(vungId);
		
		
		String khuvucId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")));

		if (khuvucId == null)
			khuvucId = "";
		obj.setkhuvucId(khuvucId);

		session.setAttribute("loi", "");
		String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
		if(view == null)
			view = "";
		obj.setView(view);
		obj.init();
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));

		if (action.equals("exportmcp"))
		{
			System.out.println("XuatMcp__");
			if(tungay.trim().length() <= 0 || denngay.trim().length() <= 0 )
			{
				if(tungay.trim().length() <= 0)
					session.setAttribute("loi", "Bạn phải chọn ngày bắt đầu xem báo cáo");
				else
					session.setAttribute("loi", "Bạn phải chọn ngày kết thúc xem báo cáo");

				session.setAttribute("obj", obj);
				String nextJSP = "/AHF/pages/Center/LoTrinhBanHangReport.jsp";
				response.sendRedirect(nextJSP);
			}
			else
			{
				XuatFileExcelSR(response, obj);
			}
		}  
		else
		{
			if(action.equals("chitiet"))
			{
				System.out.println("XuatDetail__");
				if(tungay.trim().length() <= 0 || denngay.trim().length() <= 0 )
				{

					/*	if(khuvucId.trim().length() <= 0)
						session.setAttribute("loi", "Bạn phải chọn khu vực muốn xem");
					else*/
					{
						if(tungay.trim().length() <= 0)
							session.setAttribute("loi", "Bạn phải chọn ngày bắt đầu xem báo cáo");
						else
							session.setAttribute("loi", "Bạn phải chọn ngày kết thúc xem báo cáo");
					}
					obj.init();
					String status = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("status")));
					obj.setStatus(status);
					obj.createNPP();
					session.setAttribute("obj", obj);
					String nextJSP = "/AHF/pages/Center/LoTrinhBanHangReport.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					System.out.println("VO Xuat FILE Detail__");
					XuatFileExcelChiTiet(response, obj);
				}
			} 
			else if(action.equals("bcchamcong"))
			{
				
				if(tungay.trim().length() <= 0 || denngay.trim().length() <= 0 )
				{

					/*	if(khuvucId.trim().length() <= 0)
						session.setAttribute("loi", "Bạn phải chọn khu vực muốn xem");
					else*/
					{
						if(tungay.trim().length() <= 0)
							session.setAttribute("loi", "Bạn phải chọn ngày bắt đầu xem báo cáo");
						else
							session.setAttribute("loi", "Bạn phải chọn ngày kết thúc xem báo cáo");
					}
					obj.init();
					String status = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("status")));
					obj.setStatus(status);
					obj.createNPP();
					session.setAttribute("obj", obj);
					String nextJSP = "/AHF/pages/Center/LoTrinhBanHangReport.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					XuatFileExcelChamCong(response, obj);
				}
			}
			else if(action.equals("chitietip"))
			{
				System.out.println("XuatDetailIP");
				if(tungay.trim().length() <= 0 || denngay.trim().length() <= 0 )
				{

					/*	if(khuvucId.trim().length() <= 0)
						session.setAttribute("loi", "Bạn phải chọn khu vực muốn xem");
					else*/
					{
						if(tungay.trim().length() <= 0)
							session.setAttribute("loi", "Bạn phải chọn ngày bắt đầu xem báo cáo");
						else
							session.setAttribute("loi", "Bạn phải chọn ngày kết thúc xem báo cáo");
					}
					obj.init();
					String status = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("status")));
					obj.setStatus(status);
					obj.createNPP();
					session.setAttribute("obj", obj);
					String nextJSP = "/AHF/pages/Center/LoTrinhBanHangReport.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					System.out.println("VO Xuat FILE Detail__");
					XuatFileExcelIP(response, obj);
				}
			}else
			{
				String status = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("status")));
				obj.setStatus(status);
				obj.createNPP();
				session.setAttribute("obj", obj);
				String nextJSP = "/AHF/pages/Center/LoTrinhBanHangReport.jsp";
				response.sendRedirect(nextJSP);
			}
		}
	}


	private void XuatFileExcelIP(HttpServletResponse response, ILoTrinh lotrinh) throws IOException
	{
		OutputStream out1 = null;
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=LoTrinhBanHangChiTietIP.xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());

			WritableSheet sheet = w.createSheet("LoTrinhBanHangIP", 0);


			WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 14);
			cellTitle.setColour(Colour.BLACK);
			cellTitle.setBoldStyle(WritableFont.BOLD);

			sheet.addCell(new Label(0, 0, "BÁO CÁO LỘ TRÌNH BÁN HÀNG CHI TIẾT", new WritableCellFormat(cellTitle)));
			sheet.addCell(new Label(0, 1, "TỪ NGÀY: " + lotrinh.getTungay()));
			sheet.addCell(new Label(0, 2, "ĐẾN NGÀY: " + lotrinh.getDenngay() ));

			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
			cellFont.setColour(Colour.BLACK);

			WritableCellFormat cellFormat = new WritableCellFormat(cellFont);

			cellFormat.setBackground(jxl.format.Colour.GRAY_25);
			cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

			NumberFormat dp3 = new NumberFormat("#,###,###,##");

			WritableCellFormat inFormat = new WritableCellFormat(dp3);
			inFormat.setFont(cellFont);

			// cellFormat.setFont()
			sheet.addCell(new Label(0, 4, "ĐƠN VỊ KINH DOANH", cellFormat));
			sheet.addCell(new Label(1, 4, "NVBH", cellFormat));
			sheet.addCell(new Label(2, 4, "NGÀY", cellFormat));
			sheet.addCell(new Label(3, 4, "KHÁCH HÀNG", cellFormat));
			sheet.addCell(new Label(4, 4, "ĐẾN", cellFormat));
			sheet.addCell(new Label(5, 4, "RỜI ĐI", cellFormat));
			sheet.addCell(new Label(6, 4, "Ở LẠI", cellFormat));
			sheet.addCell(new Label(7, 4, "DOANH SỐ", cellFormat));
		
			sheet.setColumnView(0, 40);
			sheet.setColumnView(1, 30);
			sheet.setColumnView(2, 15);
			sheet.setColumnView(3, 30);
			sheet.setColumnView(4, 25);
			sheet.setColumnView(5, 25);
			sheet.setColumnView(6, 15);

			WritableCellFormat cellFormat2 = new WritableCellFormat();
			cellFormat2.setBackground(jxl.format.Colour.YELLOW);

			WritableCellFormat cellFormat3 = new WritableCellFormat();
			cellFormat3.setBackground(jxl.format.Colour.GRAY_25);

			String condition = "";
		
			if(lotrinh.getTungay().trim().length() > 0)
				condition += " and CONVERT(varchar(10), a.thoidiem, 121) >= '" + lotrinh.getTungay() + "' ";
			if(lotrinh.getDenngay().trim().length() > 0)
				condition += " and CONVERT(varchar(10), a.thoidiem, 121) <= '" + lotrinh.getDenngay() + "' ";
			
			if(lotrinh.getddkdId().trim().length() > 0)
				condition += " and a.DDKD_FK = '" + lotrinh.getddkdId() + "' ";
			if(lotrinh.getViTri().length() > 0)
				condition += " and b.VITRI = '"+lotrinh.getViTri()+"' ";

			String condition2 = "";
			
			if(lotrinh.getTungay().trim().length() > 0)
				condition2 += " and CONVERT(varchar(10), a.thoidiem, 121) >= '" + lotrinh.getTungay() + "' ";
			if(lotrinh.getDenngay().trim().length() > 0)
				condition2 += " and CONVERT(varchar(10), a.thoidiem, 121) <= '" + lotrinh.getDenngay() + "' ";
		
			if(lotrinh.getddkdId().trim().length() > 0)
				condition2 += " and a.DDKD_FK = '" + lotrinh.getddkdId() + "' ";
			if(lotrinh.getViTri().length() > 0)
				condition2 += " and b.VITRI = '"+lotrinh.getViTri()+"' ";


			String query =  "select COthoidiemdi.KHACHHANG_FK, COthoidiemdi.nppTen, COthoidiemdi.ddkd_fk, COthoidiemdi.ddkdTen,  " +
					"\n 		DDKD.thoidiemDen, DDKD.thoidiemthoidiemdi, DDKD.vtnpp1, DDKD.rdnpp1, DDKD.vtnpp2, DDKD.rdnpp2, " +
					"\n 		ISNull(DATEDIFF(MINUTE, DDKD.vtnpp1, DDKD.rdnpp1), -1) as OLai1, " +
					"\n			ISNull(DATEDIFF(MINUTE, DDKD.vtnpp2, DDKD.rdnpp2), -1) as OLai2, COthoidiemdi.NGAY " +
					"\n from  " +
					"\n (  " +
					"\n 	select a.khachhang_FK, c.TEN as nppTen, a.DDKD_FK, b.TEN as ddkdTen, " +
					"\n			min(a.thoidiem) as thoidiemDen,  " +
					"\n 		( select top(1) MAX(thoidiemdi) from ddkd_khachhang where khachhang_fk = a.khachhang_fk and DDKD_FK = a.DDKD_FK and CONVERT(varchar(10), thoidiem, 121) = CONVERT(varchar(10), a.thoidiem, 121) group by KHACHHANG_FK, DDKD_FK, thoidiem  ) as thoidiemthoidiemdi,	    " +
					"\n         (select top(1) Min(thoidiem) from ddkd_khachhang where DDKD_FK = a.DDKD_FK and CONVERT(varchar(10), a.thoidiem, 121) = CONVERT(varchar(10), thoidiem, 121) ) as vtnpp1, " +
					"\n     	(select top(1) MAX(thoidiemdi) from ddkd_khachhang where DDKD_FK = a.DDKD_FK  and CONVERT(varchar(10), a.thoidiem, 121) = CONVERT(varchar(10), thoidiemdi, 121)  ) as rdnpp1, " +
					"\n     	(select top(1) thoidiem from ddkd_khachhang where DDKD_FK = a.DDKD_FK and khachhang_fk = a.khachhang_fk and CONVERT(varchar(10), a.thoidiem, 121) = CONVERT(varchar(10), thoidiem, 121)  order by thoidiem asc) as vtnpp2, " +
					"\n     	(select top(1) thoidiemdi from ddkd_khachhang where DDKD_FK = a.DDKD_FK and khachhang_fk = a.khachhang_fk and CONVERT(varchar(10), a.thoidiem, 121) = CONVERT(varchar(10), thoidiemdi, 121)  order by thoidiemdi asc) as rdnpp2, " +
					"\n 		CONVERT(varchar(10), a.thoidiem, 121) as NGAY   " +
					"\n 	from ddkd_khachhang a inner join DAIDIENKINHDOANH b on a.DDKD_FK = b.PK_SEQ   " +
					"\n 	inner join Khachhang c on a.KHACHHANG_FK = c.PK_SEQ  " +
					"\n 	where a.khachhang_fk > 0 and  c.TRANGTHAI = '1' AND b.TRANGTHAI = '1' and b.ismt = '1' AND B.ISMT = '1' " + condition +
					"\n 	 group by a.khachhang_fk, c.TEN, a.DDKD_FK, b.TEN, CONVERT(varchar(10), a.thoidiem, 121)   " +
					"\n ) " +
					"\n DDKD right join " +
					"\n (   " +
					"\n 	select distinct a.khachhang_fk, kh.TEN as nppTen, a.ddkd_fk, b.ten as ddkdTen, CONVERT(varchar(10), a.thoidiem, 121) as NGAY " +
					"\n 	from ddkd_khachhang a inner join DAIDIENKINHDOANH b on a.ddkd_fk = b.PK_SEQ " +
					"\n 	inner join khachhang kh on kh.pk_seq = a.khachhang_fk					  " +
					"\n 	where b.TRANGTHAI = '1' AND B.ISMT = '1'  " + condition2 +
					"\n ) " +
					"\n COthoidiemdi on DDKD.khachhang_FK = COthoidiemdi.khachhang_FK and DDKD.DDKD_FK = COthoidiemdi.ddkd_fk and DDKD.NGAY = COthoidiemdi.NGAY " +
					"\n where len(isnull(COthoidiemdi.NGAY, '')) > 0 " +
					"\n order by COthoidiemdi.khachhang_fk asc, cothoidiemdi.DDKD_FK asc, cothoidiemdi.NGAY asc ";


			System.out.println("[XuatFileExcelChiTietIP]"+query);

			dbutils db = new dbutils();
			ResultSet rs = db.get(query);

			int count = 5;
			if(rs != null)
			{
				String nppTenOLD = "";
				String ddkdTenOLD = "";
				Label label;

				while(rs.next())
				{
					String nppTen = rs.getString("nppTen");
					String ddkdTen = rs.getString("ddkdTen");
					String ngay = rs.getString("NGAY");
					String olai1 = rs.getString("OLai1");
					String olai2 = rs.getString("OLai2");
					String den1 = rs.getString("vtnpp1") == null ? "" : rs.getString("vtnpp1") ;
					String roidi1 = rs.getString("rdnpp1") == null ? "" : rs.getString("rdnpp1") ;
					String den2 = rs.getString("vtnpp2") == null ? "" : rs.getString("vtnpp2") ;
					String roidi2 = rs.getString("rdnpp2") == null ? "" : rs.getString("rdnpp2") ;

					if(!nppTenOLD.equals(nppTen))
					{
						label = new Label(0, count, "IP", cellFormat2);
						sheet.addCell(label);

						nppTenOLD = nppTen;
						ddkdTenOLD = "";
					}
					else
					{
						label = new Label(0, count, " ");
						sheet.addCell(label);
					}

					if(!ddkdTenOLD.equals(ddkdTen))
					{
						label = new Label(1, count, ddkdTen, cellFormat3);
						sheet.addCell(label);

						ddkdTenOLD = ddkdTen;
					}
					else
					{
						label = new Label(1, count, "");
						sheet.addCell(label);
					}

					label = new Label(2, count, ngay, cellFormat3);
					sheet.addCell(label);

					label = new Label(3, count, "");
					sheet.addCell(label);

					label = new Label(4, count, den1);
					sheet.addCell(label);

					label = new Label(5, count, roidi1);
					sheet.addCell(label);

					label = new Label(6, count, olai1);
					sheet.addCell(label);

					count ++;

					query = "\n select DDKD.*, isnull(DATEDIFF(MINUTE, ThoiDiemDen, ThoiDiemDi), -1) as OLai " +
							"\n from " +
							"\n ( " +
							"\n select b.TEN as khTen, min(a.thoidiem) as ThoiDiemDen, " +
							"\n ( select top(1) MAX(THOIDIEMDI) from ddkd_khachhang where khachhang_fk = a.khachhang_fk and CONVERT(varchar(10), THOIDIEMDI, 121) = CONVERT(varchar(10), a.thoidiem, 121) group by khachhang_fk, THOIDIEM  ) as ThoiDiemDi,	  " +
							"\n CONVERT(varchar(10), a.thoidiem, 121) as NGAY,  " +
							"\n isnull((select sum(dhsp.SOLUONG * dhsp.GIAMUA) "+
							"\n from DONHANGIP dh inner join DONHANG_SANPHAMSPIP dhsp on dhsp.DONHANG_FK = dh.PK_SEQ " +
							"\n where DH.ngaytao = '"+ngay+"' and dh.TRANGTHAI != 2 and dh.KHACHHANG_FK = a.khachhang_fk" +
							"\n group by dh.ngaytao),0) as tongtien "+
							"\n from ddkd_khachhang a inner join KHACHHANG b on a.khachhang_fk = b.PK_SEQ  " +
							"\n where b.TRANGTHAI = '1' AND a.ddkd_fk = '" + rs.getString("DDKD_FK") + "' and CONVERT(varchar(10), a.thoidiem, 121) = '" + ngay + "' and b.ismt  = '1' " +
							"\n group by  a.khachhang_fk, b.TEN, CONVERT(varchar(10), a.thoidiem, 121)  " +
							"\n )  " +
							"\n DDKD " +
							"\n order by ThoiDiemDen asc";
					System.out.println("LAY BC CT: " + query);
					ResultSet rsKh = db.get(query);
					String olai = "", den = "", roidi = "";
					if(rsKh != null)
					{
						while(rsKh.next())
						{
							olai = rsKh.getString("OLai");
							den = rsKh.getString("ThoiDiemDen") == null ? "" : rsKh.getString("ThoiDiemDen") ;
							roidi = rsKh.getString("ThoiDiemDi") == null ? "" : rsKh.getString("ThoiDiemDi") ;
							double tongtien = rsKh.getDouble("tongtien");
							label = new Label(3, count, rsKh.getString("khTen"));
							sheet.addCell(label);

							label = new Label(4, count, den);
							sheet.addCell(label);

							label = new Label(5, count, roidi);
							sheet.addCell(label);

							label = new Label(6, count, olai);
							sheet.addCell(label);

							Number number = new Number(7, count, tongtien, inFormat);
							sheet.addCell(number);

							
							
							count ++;
						}
						rsKh.close();
					}
				/*	label = new Label(3, count, "");
					sheet.addCell(label);

					label = new Label(4, count, den2);
					sheet.addCell(label);

					label = new Label(5, count, roidi2);
					sheet.addCell(label);

					label = new Label(6, count, olai2);
					sheet.addCell(label);*/

					count += 2;

				}
				rs.close();
			}

			w.write();
			w.close();
		} 
		catch (Exception e)
		{
			System.out.println("Error Cac Ban : " + e.getMessage());
			e.printStackTrace();
		} 
		finally
		{
			if (out1 != null)
				out1.close();
		}

	}



	private void XuatFileExcelChiTiet(HttpServletResponse response, ILoTrinh lotrinh) throws IOException
	{
		OutputStream out1 = null;
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=LoTrinhBanHangChiTiet.xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());

			WritableSheet sheet = w.createSheet("LoTrinhBanHang", 0);


			WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 14);
			cellTitle.setColour(Colour.BLACK);
			cellTitle.setBoldStyle(WritableFont.BOLD);

			sheet.addCell(new Label(0, 0, "BÁO CÁO LỘ TRÌNH BÁN HÀNG CHI TIẾT", new WritableCellFormat(cellTitle)));
			sheet.addCell(new Label(0, 1, "TỪ NGÀY: " + lotrinh.getTungay()));
			sheet.addCell(new Label(0, 2, "ĐẾN NGÀY: " + lotrinh.getDenngay() ));

			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
			cellFont.setColour(Colour.BLACK);

			WritableCellFormat cellFormat = new WritableCellFormat(cellFont);

			cellFormat.setBackground(jxl.format.Colour.GRAY_25);
			cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

			NumberFormat dp3 = new NumberFormat("#,###,###,##");

			WritableCellFormat inFormat = new WritableCellFormat(dp3);
			inFormat.setFont(cellFont);

			// cellFormat.setFont()
			sheet.addCell(new Label(0, 4, "NHÀ PHÂN PHỐI", cellFormat));
			sheet.addCell(new Label(1, 4, "NVBH", cellFormat));
			sheet.addCell(new Label(2, 4, "NGÀY", cellFormat));
			sheet.addCell(new Label(3, 4, "Mã Khách Hàng", cellFormat));
			sheet.addCell(new Label(4, 4, "KHÁCH HÀNG", cellFormat));
			sheet.addCell(new Label(5, 4, "ĐẾN", cellFormat));
			sheet.addCell(new Label(6, 4, "RỜI ĐI", cellFormat));
			sheet.addCell(new Label(7, 4, "Ở LẠI", cellFormat));
			sheet.addCell(new Label(8, 4, "DOANH SỐ", cellFormat));
			sheet.addCell(new Label(9, 4, "GHI CHÚ", cellFormat));
			sheet.addCell(new Label(10, 4, "Tuyến", cellFormat));
			


			sheet.setColumnView(0, 40);
			sheet.setColumnView(1, 30);
			sheet.setColumnView(2, 15);
			sheet.setColumnView(3, 30);
			sheet.setColumnView(4, 25);
			sheet.setColumnView(5, 25);
			sheet.setColumnView(6, 15);

			WritableCellFormat cellFormat2 = new WritableCellFormat();
			cellFormat2.setBackground(jxl.format.Colour.YELLOW);

			WritableCellFormat cellFormat3 = new WritableCellFormat();
			cellFormat3.setBackground(jxl.format.Colour.GRAY_25);

			String condition = "";
			if(lotrinh.getnppId().trim().length() > 0)
				condition += " and a.NPP_FK = '" + lotrinh.getnppId() + "' ";
			else
				condition += " and a.NPP_FK in " + util.quyen_npp(lotrinh.getUserId()) + " ";
			if(lotrinh.getTungay().trim().length() > 0)
				condition += " and CONVERT(varchar(10), a.thoidiem, 121) >= '" + lotrinh.getTungay() + "' ";
			if(lotrinh.getDenngay().trim().length() > 0)
				condition += " and CONVERT(varchar(10), a.thoidiem, 121) <= '" + lotrinh.getDenngay() + "' ";
			if(lotrinh.getkhuvucId().trim().length() > 0)
				condition += " and a.NPP_FK in (  select pk_seq from nhaphanphoi where khuvuc_fk = '" + lotrinh.getkhuvucId() + "' ) ";
			if(lotrinh.getvungId().trim().length() > 0)
				condition += " and a.NPP_FK in (  select pk_seq from nhaphanphoi where khuvuc_fk in (select pk_seq from khuvuc where vung_fk = '" + lotrinh.getvungId() + "' )) ";
			if(lotrinh.getkenhId().trim().length() > 0)
				condition += " and a.NPP_FK in (  select pk_seq from nhaphanphoi where kbh_fk = '" + lotrinh.getkenhId() + "' ) ";
			if(lotrinh.getddkdId().trim().length() > 0)
				condition += " and a.DDKD_FK = '" + lotrinh.getddkdId() + "' ";
			if(lotrinh.getViTri().length() > 0)
				condition += " and b.VITRI = '"+lotrinh.getViTri()+"' ";

			String condition2 = "";
			if(lotrinh.getnppId().trim().length() > 0)
				condition2 += " and b.NPP_FK = '" + lotrinh.getnppId() + "' ";
			else
				condition2 += " and b.NPP_FK in " + util.quyen_npp(lotrinh.getUserId()) + " ";
			if(lotrinh.getTungay().trim().length() > 0)
				condition2 += " and CONVERT(varchar(10), a.thoidiem, 121) >= '" + lotrinh.getTungay() + "' ";
			if(lotrinh.getDenngay().trim().length() > 0)
				condition2 += " and CONVERT(varchar(10), a.thoidiem, 121) <= '" + lotrinh.getDenngay() + "' ";
			if(lotrinh.getkhuvucId().trim().length() > 0)
				condition2 += " and b.NPP_FK in (  select pk_seq from nhaphanphoi where khuvuc_fk = '" + lotrinh.getkhuvucId() + "' ) ";
			if(lotrinh.getvungId().trim().length() > 0)
				condition2 += " and b.NPP_FK in (  select pk_seq from nhaphanphoi where khuvuc_fk in (select pk_seq from khuvuc where vung_fk = '" + lotrinh.getvungId() + "' )) ";
			if(lotrinh.getkenhId().trim().length() > 0)
				condition2 += " and b.NPP_FK in (  select pk_seq from nhaphanphoi where kbh_fk = '" + lotrinh.getkenhId() + "' ) ";
			if(lotrinh.getddkdId().trim().length() > 0)
				condition2 += " and a.DDKD_FK = '" + lotrinh.getddkdId() + "' ";
			if(lotrinh.getViTri().length() > 0)
				condition2 += " and b.VITRI = '"+lotrinh.getViTri()+"' ";


			
			
			String condition4 = "";
			if(lotrinh.getnppId().trim().length() > 0)
				condition4 += " and aa.NPP_FK = '" + lotrinh.getnppId() + "' ";
			else
				condition4 += " and aa.NPP_FK in " + util.quyen_npp(lotrinh.getUserId()) + " ";
			if(lotrinh.getTungay().trim().length() > 0)
				condition4 += " and CONVERT(varchar(10), aa.thoidiem, 121) >= '" + lotrinh.getTungay() + "' ";
			if(lotrinh.getDenngay().trim().length() > 0)
				condition4 += " and CONVERT(varchar(10), aa.thoidiem, 121) <= '" + lotrinh.getDenngay() + "' ";
			if(lotrinh.getkhuvucId().trim().length() > 0)
				condition4 += " and aa.NPP_FK in (  select pk_seq from nhaphanphoi where khuvuc_fk = '" + lotrinh.getkhuvucId() + "' ) ";
			if(lotrinh.getvungId().trim().length() > 0)
				condition4 += " and aa.NPP_FK in (  select pk_seq from nhaphanphoi where khuvuc_fk in (select pk_seq from khuvuc where vung_fk = '" + lotrinh.getvungId() + "' )) ";
			if(lotrinh.getkenhId().trim().length() > 0)
				condition4 += " and aa.NPP_FK in (  select pk_seq from nhaphanphoi where kbh_fk = '" + lotrinh.getkenhId() + "' ) ";
			if(lotrinh.getddkdId().trim().length() > 0)
				condition4 += " and aa.DDKD_FK = '" + lotrinh.getddkdId() + "' ";
			if(lotrinh.getViTri().length() > 0)
				condition4 += " and bb.VITRI = '"+lotrinh.getViTri()+"' ";
			
			
			
			String query =  "select DDKD.NPP_FK, DDKD.nppTen, DDKD.ddkd_fk, DDKD.ddkdTen,  " +
					"\n 		DDKD.ThoiDiemDen, DDKD.ThoiDiemDi, DDKD.vtnpp1, DDKD.rdnpp1, DDKD.vtnpp2, DDKD.rdnpp2, " +
					"\n 		ISNull(DATEDIFF(MINUTE, DDKD.vtnpp1, DDKD.rdnpp1), -1) as OLai1, " +
					"\n			ISNull(DATEDIFF(MINUTE, DDKD.vtnpp2, DDKD.rdnpp2), -1) as OLai2, isnull(DDKD.NGAY, CONVERT(date, DDKD.ThoiDiemDen)) as Ngay " +
					"\n from  " +
					"\n (  " +
					"\n 	select a.NPP_FK, c.TEN as nppTen, a.DDKD_FK, b.TEN as ddkdTen, " +
					"\n			isnull(min(a.Thoidiem), min(a.toi1)) as ThoiDiemDen,  " +
					//"\n 		( select top(1) MAX(DI) from DDKD_NPP where NPP_FK = a.NPP_FK and DDKD_FK = a.DDKD_FK and CONVERT(varchar(10), TOI, 121) = CONVERT(varchar(10), a.thoidiem, 121) group by NPP_FK, DDKD_FK, TOI  ) as ThoiDiemDi,	    " +
					"\n			isnull(( select top(1) MAX(DI) from DDKD_NPP where NPP_FK = a.NPP_FK and DDKD_FK = a.DDKD_FK and CONVERT(varchar(10), TOI, 121) = CONVERT(varchar(10), a.thoidiem, 121) group by NPP_FK, DDKD_FK, TOI  ), " +
					"\n					( select top(1) MAX(DI1) from DDKD_NPP where NPP_FK = a.NPP_FK and DDKD_FK = a.DDKD_FK and CONVERT(varchar(10), TOI1, 121) = CONVERT(varchar(10), a.thoidiem, 121) group by NPP_FK, DDKD_FK, TOI1  )) as ThoiDiemDi, " +  
					////////////////////////////////////////////////////
					"\n        (select top(1) Thoidiem from DDKD_NPP where DDKD_FK = a.DDKD_FK and NPP_FK = a.NPP_FK and CONVERT(varchar(10), a.thoidiem, 121) = CONVERT(varchar(10), Thoidiem, 121)  order by Thoidiem asc) as vtnpp1, " +
					"\n     	isnull((select top(1) DI from DDKD_NPP where DDKD_FK = a.DDKD_FK and NPP_FK = a.NPP_FK and CONVERT(varchar(10), a.thoidiem, 121) = CONVERT(varchar(10), DI, 121)  order by DI asc),(select top(1) DI1 from DDKD_NPP where DDKD_FK = a.DDKD_FK and NPP_FK = a.NPP_FK and CONVERT(varchar(10), a.thoidiem, 121) = CONVERT(varchar(10), DI1, 121)  order by DI1 asc)) as rdnpp1, " +
					"\n     	(select top(1) Thoidiem from DDKD_NPP where DDKD_FK = a.DDKD_FK and NPP_FK = a.NPP_FK and CONVERT(varchar(10), a.thoidiem, 121) = CONVERT(varchar(10), Thoidiem, 121) and Thoidiem <>  (select top(1) Thoidiem from DDKD_NPP where DDKD_FK = a.DDKD_FK and NPP_FK = a.NPP_FK and CONVERT(varchar(10), a.thoidiem, 121) = CONVERT(varchar(10), Thoidiem, 121)  order by Thoidiem asc) order by Thoidiem desc) as vtnpp2, " +
					"\n     	isnull((select top(1) DI from DDKD_NPP where DDKD_FK = a.DDKD_FK and NPP_FK = a.NPP_FK and CONVERT(varchar(10), a.thoidiem, 121) = CONVERT(varchar(10), DI, 121) and DI <> (select top(1) DI from DDKD_NPP where DDKD_FK = a.DDKD_FK and NPP_FK = a.NPP_FK and CONVERT(varchar(10), a.thoidiem, 121) = CONVERT(varchar(10), DI, 121)  order by DI asc) order by DI desc),(select top(1) DI1 from DDKD_NPP where DDKD_FK = a.DDKD_FK and NPP_FK = a.NPP_FK and CONVERT(varchar(10), a.thoidiem, 121) = CONVERT(varchar(10), DI1, 121) and DI1 <> (select top(1) DI1 from DDKD_NPP where DDKD_FK = a.DDKD_FK and NPP_FK = a.NPP_FK and CONVERT(varchar(10), a.thoidiem, 121) = CONVERT(varchar(10), DI1, 121)  order by DI1 asc) order by DI1 desc)) as rdnpp2, " +
					"\n 		CONVERT(varchar(10), a.thoidiem, 121) as NGAY   " +
					"\n 	from DDKD_NPP a inner join DAIDIENKINHDOANH b on a.DDKD_FK = b.PK_SEQ   " +
					"\n 	inner join NHAPHANPHOI c on a.NPP_FK = c.PK_SEQ  " +
					"\n 	where a.npp_fk > 0 and c.TRANGTHAI = '1' --AND b.TRANGTHAI = '1' \n" + condition +
					"\n 	 group by a.NPP_FK, c.TEN, a.DDKD_FK, b.TEN, CONVERT(varchar(10), a.thoidiem, 121)   " +
					/*"\n 	 union all   " +
					"\n 		select c.PK_SEQ, c.TEN as nppTen, b.PK_SEQ, b.TEN as ddkdTen,    " +
					"\n 			null as ThoiDiemDen, null ThoiDiemDi, null vtnpp1, null rdnpp1, null vtnpp2, null rdnpp2, null NGAY      " +
					"\n 	from DAIDIENKINHDOANH b    " +
					"\n 	inner join NHAPHANPHOI c on b.NPP_FK = c.PK_SEQ     " +
					"\n 	where b.TRANGTHAI = 1     " +condition3+
					"\n 	union all    " +
					"\n 	select distinct c.PK_SEQ, c.TEN as nppTen, a.ddkd_fk, b.ten as ddkdTen,     " +
					"\n 	null as ThoiDiemDen, null ThoiDiemDi, null vtnpp1, null rdnpp1, null vtnpp2, null rdnpp2,CONVERT(varchar(10), a.thoidiem, 121) as NGAY     " +
					"\n 	from ddkd_khachhang a inner join daidienkinhdoanh b on a.ddkd_fk = b.PK_SEQ     " +
					"\n 						  inner join NHAPHANPHOI c on b.NPP_FK = c.PK_SEQ     " +
					"\n 	where b.TRANGTHAI = '1' AND c.TRANGTHAI = '1' " + condition2 +*/
					
					"\n 	union all     " +
					"\n 		select distinct c.PK_SEQ, c.TEN as nppTen, a.ddkd_fk, b.ten as ddkdTen,      " +
					"\n 		null as ThoiDiemDen, null ThoiDiemDi, null vtnpp1, null rdnpp1, null vtnpp2, null rdnpp2,CONVERT(varchar(10), a.thoidiem, 121) as NGAY      " +
					"\n 		from ddkd_khachhang a inner join daidienkinhdoanh b on a.ddkd_fk = b.PK_SEQ      " +
					"\n 							  inner join NHAPHANPHOI c on b.NPP_FK = c.PK_SEQ      " +
					"\n 		where c.TRANGTHAI = '1'   " + condition2 +
					"\n 	and CONVERT(varchar(10), a.thoidiem, 121) not in (select CONVERT(varchar(10), aa.thoidiem, 121)     " +  
					"\n 			from DDKD_NPP aa inner join DAIDIENKINHDOANH bb on aa.DDKD_FK = bb.PK_SEQ        " +
					"\n 			inner join NHAPHANPHOI cc on aa.NPP_FK = cc.PK_SEQ       " +
					"\n 		where aa.npp_fk > 0 and cc.TRANGTHAI = '1' and aa.npp_fk = c.pk_seq and bb.pk_seq = b.pk_seq  " + condition4 +
					"\n 			group by aa.NPP_FK, cc.TEN, aa.DDKD_FK, bb.TEN, CONVERT(varchar(10), aa.thoidiem, 121)   )     " +
					"\n ) " +
					"\n DDKD "+
					/*+ "left join " +
					"\n (   " +
					"\n 	select distinct b.NPP_FK, c.TEN as nppTen, a.ddkd_fk, b.ten as ddkdTen, CONVERT(varchar(10), a.thoidiem, 121) as NGAY " +
					"\n 	from ddkd_khachhang a inner join daidienkinhdoanh b on a.ddkd_fk = b.PK_SEQ " +
					"\n 						  inner join NHAPHANPHOI c on b.NPP_FK = c.PK_SEQ " +
					"\n 	where  c.TRANGTHAI = '1' " + condition2 +
					"\n ) " +
					"\n CODI on DDKD.NPP_FK = CODI.NPP_FK and DDKD.DDKD_FK = CODI.ddkd_fk and DDKD.NGAY = CODI.NGAY " +*/
					"\n where 1=1 --len(isnull(CODI.NGAY, '')) > 0 " +
					"\n order by DDKD.NPP_FK asc, DDKD.DDKD_FK asc, DDKD.NGAY asc ";


			System.out.println("[XuatFileExcelChiTiet]"+query);

			dbutils db = new dbutils();
			ResultSet rs = db.get(query);

			int count = 5;
			if(rs != null)
			{
				String nppTenOLD = "";
				String ddkdTenOLD = "";
				Label label;

				while(rs.next())
				{
					String nppTen = rs.getString("nppTen");
					String ddkdTen = rs.getString("ddkdTen");
					String ngay = rs.getString("NGAY");
					int olai1 = rs.getInt("OLai1");
					int olai2 = rs.getInt("OLai2");
					String den1 = rs.getString("vtnpp1") == null ? "" : rs.getString("vtnpp1") ;
					String roidi1 = rs.getString("rdnpp1") == null ? "" : rs.getString("rdnpp1") ;
					String den2 = rs.getString("vtnpp2") == null ? "" : rs.getString("vtnpp2") ;
					String roidi2 = rs.getString("rdnpp2") == null ? "" : rs.getString("rdnpp2") ;

					/*if(!nppTenOLD.equals(nppTen))
					{
						label = new Label(0, count, nppTen, cellFormat2);
						sheet.addCell(label);

						nppTenOLD = nppTen;
						ddkdTenOLD = "";
					}
					else
					{
						label = new Label(0, count, " ");
						sheet.addCell(label);
					}

					if(!ddkdTenOLD.equals(ddkdTen))
					{
						label = new Label(1, count, ddkdTen, cellFormat3);
						sheet.addCell(label);

						ddkdTenOLD = ddkdTen;
					}
					else
					{
						label = new Label(1, count, " ");
						sheet.addCell(label);
					}*/

					label = new Label(0, count, nppTen, cellFormat2);
					sheet.addCell(label);
					
					label = new Label(1, count, ddkdTen, cellFormat3);
					sheet.addCell(label);
					
					label = new Label(2, count, ngay, cellFormat3);
					sheet.addCell(label);

					label = new Label(4, count, "NPP");
					sheet.addCell(label);

					label = new Label(5, count, den1);
					sheet.addCell(label);

					label = new Label(6, count, roidi1);
					sheet.addCell(label);

					Number number = new Number(7, count, olai1, inFormat);
					sheet.addCell(number);

					count ++;

				/*	query = "\n select DDKD.*, isnull(DATEDIFF(MINUTE, ThoiDiemDen, ThoiDiemDi), -1) as OLai " +
							"\n from " +
							"\n ( " +
							"\n select b.TEN as khTen, min(a.thoidiem) as ThoiDiemDen, " +
							"\n ( select top(1) MAX(THOIDIEMDI) from ddkd_khachhang where khachhang_fk = a.khachhang_fk and CONVERT(varchar(10), THOIDIEMDI, 121) = CONVERT(varchar(10), a.thoidiem, 121) group by khachhang_fk, THOIDIEM  ) as ThoiDiemDi,	  " +
							"\n CONVERT(varchar(10), a.thoidiem, 121) as NGAY,  " +
							"\n isnull((select sum(dhsp.SOLUONG * dhsp.GIAMUA) "+
							"\n from DONHANG dh inner join DONHANG_SANPHAM dhsp on dhsp.DONHANG_FK = dh.PK_SEQ " +
							"\n where DH.ngaytao = '"+ngay+"' and dh.KHACHHANG_FK = a.khachhang_fk and dh.IsPDA = '1'  " +
							"\n group by dh.ngaytao),0) as tongtien, isnull(a.ghichu,'') as ghichu ," +
							"\n case when a.isdungtuyen =1 then N'Đúng tuyến' else N'Trái tuyến' end as Tuyen "+
							"\n from ddkd_khachhang a inner join KHACHHANG b on a.khachhang_fk = b.PK_SEQ  " +
							"\n where a.ddkd_fk = '" + rs.getString("DDKD_FK") + "' and CONVERT(varchar(10), a.thoidiem, 121) = '" + ngay + "'  " +
							"\n group by  a.khachhang_fk, b.TEN, CONVERT(varchar(10), a.thoidiem, 121), a.ghichu, a.thoidiem, a.isdungtuyen " +
							"\n )  " +
							"\n DDKD " +
							"\n order by ThoiDiemDen asc";*/
					
					
					query = "\n select DDKD.*, isnull(DATEDIFF(MINUTE, ThoiDiemDen, ThoiDiemDi), -1) as OLai  " +
						"\n from  " +
						"\n ( " +
						"\n select distinct f.TEN as khTen, f.smartid as makh, min(e.thoidiem) as ThoiDiemDen, " +
						"\n ( select top(1) MAX(THOIDIEMDI) from ddkd_khachhang where khachhang_fk = e.kh_fk  " +
						"\n and CONVERT(varchar(10), THOIDIEMDI, 121) = CONVERT(varchar(10), e.thoidiem, 121) group by khachhang_fk, THOIDIEM  ) as ThoiDiemDi,	   " +
						"\n CONVERT(varchar(10), e.thoidiem, 121) as NGAY,   " +
						"\n isnull((select sum(dhsp.SOLUONG * dhsp.GIAMUA)  " +
						"\n from DONHANG dh inner join DONHANG_SANPHAM dhsp on dhsp.DONHANG_FK = dh.PK_SEQ  " +
						"\n where DH.ngaytao = '"+ngay+"' and dh.KHACHHANG_FK = e.kh_fk and dh.IsPDA = '1'   " +
						"\n group by dh.ngaytao),0) as tongtien, isnull(e.ghichu,'') as ghichu , " +
						"\n  case when e.isdungtuyen =0 then N'Trái tuyến' else N'Đúng tuyến' end as Tuyen  " +
						"\n from ( " +
						"\n SELECT d.DDKD_FK, isdungtuyen, thoidiem,d.kh_fk, d.khachhang_fk, d.ghichu FROM ( " +
						"\n 	SELECT b.DDKD_FK,c.kh_fk, c.isdungtuyen, c.thoidiem, b.khachhang_fk, b.TEN, c.ghichu from " +
						"\n 	( " +
						"\n 		select t.DDKD_FK, a.TEN, a.PK_SEQ as khachhang_fk from KHACHHANG a " +
						"\n 		inner join DDKD_SOKH t on charindex('--' + cast(a.PK_SEQ as varchar(10)) + '--', t.KhachHang_Fks) >= 1  " +
						"\n 		where t.NGAY = '"+ngay+"' and t.DDKD_FK = '" + rs.getString("DDKD_FK") + "'  " +
						"\n 	) b  " +
						"\n 	LEFT JOIN " +
						"\n 	( " +
						"\n 		select khachhang_fk as kh_fk,isdungtuyen,thoidiem, ddkd_fk, ghichu from ddkd_khachhang  " +
						"\n 		WHERE DDKD_FK = '" + rs.getString("DDKD_FK") + "' AND CONVERT(VARCHAR(10), THOIDIEM, 120) = '"+ngay+"' " +
						"\n 	) c  ON b.ddkd_fk = '" + rs.getString("DDKD_FK") + "' and c.kh_fk = b.khachhang_fk   " +
						"\n ) D " +
						"\n UNION  " +
						/*"\n select ddkd_fk, isdungtuyen, thoidiem,khachhang_fk ,ghichu   " +
						"\n from ddkd_khachhang WHERE CONVERT(VARCHAR(10), THOIDIEM, 120) = '"+ngay+"' and DDKD_FK = '" + rs.getString("DDKD_FK") + "' " +*/
						"\n select DDKD_FK,isdungtuyen, thoidiem, khachhang_fk as kh_fk, khachhang_fk,ghichu from ddkd_khachhang   " +
						"\n WHERE DDKD_FK = '" + rs.getString("DDKD_FK") + "' AND CONVERT(VARCHAR(10), THOIDIEM, 120) = '"+ngay+"'  " +
						"\n ) e " +
						"\n left join khachhang f on f.PK_SEQ = e.khachhang_fk " +
						"\n where 1=1  " +
						"\n group by  e.khachhang_fk, CONVERT(varchar(10), e.thoidiem, 121),f.TEN, e.ghichu, e.thoidiem, e.isdungtuyen, e.kh_fk, f.smartid  " +
						"\n )ddkd " +
						"\n order by ThoiDiemDen asc  ";

					
					
					System.out.println("LAY BC CT: " + query);
					ResultSet rsKh = db.get(query);
					String  den = "", roidi = "";
					int olai = 0;
					if(rsKh != null)
					{
						while(rsKh.next())
						{
							olai = rsKh.getInt("OLai");
							den = rsKh.getString("ThoiDiemDen") == null ? "" : rsKh.getString("ThoiDiemDen") ;
							roidi = rsKh.getString("ThoiDiemDi") == null ? "" : rsKh.getString("ThoiDiemDi") ;
							String ghichu = rsKh.getString("ghichu"); 
							double tongtien = rsKh.getDouble("tongtien");
							
							label = new Label(0, count, nppTen, cellFormat2);
							sheet.addCell(label);
							
							label = new Label(1, count, ddkdTen, cellFormat3);
							sheet.addCell(label);
							
							label = new Label(2, count, ngay, cellFormat3);
							sheet.addCell(label);
							
							
							label = new Label(3, count, rsKh.getString("makh"));
							sheet.addCell(label);
							
							label = new Label(4, count, rsKh.getString("khTen"));
							sheet.addCell(label);

							label = new Label(5, count, den);
							sheet.addCell(label);

							label = new Label(6, count, roidi);
							sheet.addCell(label);

							 number = new Number(7, count, olai, inFormat);
							sheet.addCell(number);

							 number = new Number(8, count, tongtien, inFormat);
							sheet.addCell(number);
							
							label = new Label(9, count, ghichu);
							sheet.addCell(label);

							label = new Label(10, count, rsKh.getString("tuyen"));
							sheet.addCell(label);
							
							

							count ++;
						}
						rsKh.close();
					}
					
					label = new Label(0, count, nppTen, cellFormat2);
					sheet.addCell(label);
					
					label = new Label(1, count, ddkdTen, cellFormat3);
					sheet.addCell(label);
					
					label = new Label(2, count, ngay, cellFormat3);
					sheet.addCell(label);
					
					
					label = new Label(4, count, "NPP");
					sheet.addCell(label);

					label = new Label(5, count, den2);
					sheet.addCell(label);

					label = new Label(6, count, roidi2);
					sheet.addCell(label);

				
					 number = new Number(7, count, olai2, inFormat);
					sheet.addCell(number);

					count += 2;

				}
				rs.close();
			}

			w.write();
			w.close();
		} 
		catch (Exception e)
		{
			System.out.println("Error Cac Ban : " + e.getMessage());
			e.printStackTrace();
		} 
		finally
		{
			if (out1 != null)
				out1.close();
		}

	}

	
	private void XuatFileExcelChamCong(HttpServletResponse response, ILoTrinh lotrinh) throws IOException
	{
		OutputStream out1 = null;
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=LoTrinhBanHangChiTiet.xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());

			WritableSheet sheet = w.createSheet("LoTrinhBanHang", 0);


			WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 14);
			cellTitle.setColour(Colour.BLACK);
			cellTitle.setBoldStyle(WritableFont.BOLD);

			sheet.addCell(new Label(0, 0, "BÁO CÁO CHẤM CÔNG", new WritableCellFormat(cellTitle)));
			sheet.addCell(new Label(0, 1, "TỪ NGÀY: " + lotrinh.getTungay()));
			sheet.addCell(new Label(0, 2, "ĐẾN NGÀY: " + lotrinh.getDenngay() ));

			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
			cellFont.setColour(Colour.BLACK);

			WritableCellFormat cellFormat = new WritableCellFormat(cellFont);

			cellFormat.setBackground(jxl.format.Colour.GRAY_25);
			cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

			NumberFormat dp3 = new NumberFormat("#,###,###,##");

			WritableCellFormat inFormat = new WritableCellFormat(dp3);
			inFormat.setFont(cellFont);

			// cellFormat.setFont()
			sheet.addCell(new Label(0, 4, "NHÀ PHÂN PHỐI", cellFormat));
			sheet.addCell(new Label(1, 4, "MANVBH", cellFormat));
			sheet.addCell(new Label(2, 4, "NVBH", cellFormat));
			sheet.addCell(new Label(3, 4, "Trạng thái NVBH", cellFormat));
			sheet.addCell(new Label(4, 4, "TUYẾN", cellFormat));
			sheet.addCell(new Label(5, 4, "NGÀY", cellFormat));
			sheet.addCell(new Label(6, 4, "Viếng thăm đầu ngày NPP", cellFormat));
			sheet.addCell(new Label(7, 4, "Viếng thăm cuối ngày NPP", cellFormat));
			sheet.addCell(new Label(8, 4, "Tổng KH hoạt động/Tuyến", cellFormat));
			sheet.addCell(new Label(9, 4, "Số KH viếng thăm đúng tuyến", cellFormat));
			sheet.addCell(new Label(10, 4, "Số KH viếng thăm trái tuyến", cellFormat));
			sheet.addCell(new Label(11, 4, "Tổng KH đã viếng thăm", cellFormat));
			sheet.addCell(new Label(12, 4, "Ngày công", cellFormat));
			


			sheet.setColumnView(0, 40);
			sheet.setColumnView(1, 20);
			sheet.setColumnView(2, 10);
			sheet.setColumnView(3, 10);
			sheet.setColumnView(4, 15);
			sheet.setColumnView(5, 20);
			sheet.setColumnView(6, 20);
			sheet.setColumnView(7, 20);

			WritableCellFormat cellFormat2 = new WritableCellFormat();
			cellFormat2.setBackground(jxl.format.Colour.YELLOW);

			WritableCellFormat cellFormat3 = new WritableCellFormat();
			cellFormat3.setBackground(jxl.format.Colour.GRAY_25);

			String condition = "";
			if(lotrinh.getnppId().trim().length() > 0)
				condition += " and a.NPP_FK = '" + lotrinh.getnppId() + "' ";
			else
				condition += " and a.NPP_FK in " + util.quyen_npp(lotrinh.getUserId()) + " ";
			if(lotrinh.getTungay().trim().length() > 0)
				condition += " and b.ngay >= '" + lotrinh.getTungay() + "' ";
			if(lotrinh.getDenngay().trim().length() > 0)
				condition += " and b.ngay <= '" + lotrinh.getDenngay() + "' ";
			if(lotrinh.getkhuvucId().trim().length() > 0)
				condition += " and a.NPP_FK in (  select pk_seq from nhaphanphoi where khuvuc_fk = '" + lotrinh.getkhuvucId() + "' ) ";
			if(lotrinh.getvungId().trim().length() > 0)
				condition += " and a.NPP_FK in (  select pk_seq from nhaphanphoi where khuvuc_fk in (select pk_seq from khuvuc where vung_fk = '" + lotrinh.getvungId() + "' )) ";
			if(lotrinh.getkenhId().trim().length() > 0)
				condition += " and a.NPP_FK in (  select pk_seq from nhaphanphoi where kbh_fk = '" + lotrinh.getkenhId() + "' ) ";
			if(lotrinh.getddkdId().trim().length() > 0)
				condition += " and a.pk_seq = '" + lotrinh.getddkdId() + "' ";


			String query =  "\n select npp.TEN as nppTEN, a.smartid,a.TEN as ddkdTen,case when a.trangthai = 1 then N'Hoạt động' else N'Ngưng hoạt động' end trangthai, b.NGAYID, b.NGAY,case when VTDN.num = 1 then N'Đạt' else N'Không Đạt' end VTDN,case when VTCN.num = 1 then N'Đạt' else N'Không đạt' end VTCN, isnull(b.SOKHCC, 0) as SOKHCC, isnull(VTDT.VTDT, 0) as VTDT,isnull(VTTT.VTTT, 0) as VTTT, b.SOKHTOADO, isnull(VTTONG,0)  as VTTONG,isnull(dbo.NGAYCONG(a.PK_SEQ,b.NGAY),0) cong "+
							"\n from DAIDIENKINHDOANH a "+
					"\n inner join DDKD_SOKH b on a.PK_SEQ = b.DDKD_FK   "+
					"\n inner join NHAPHANPHOI npp on npp.PK_SEQ = a.NPP_FK "+
					"\n left join  "+
					"\n ( "+
					"\n 	select CONVERT(VARCHAR(10), THOIDIEM, 120) as ngay, ddkd_fk, 1 num  "+
					"\n 	from DDKD_NPP where convert(time(3),THOIDIEM) <= '12:00:00.000' and thoidiem is not null  "+
					"\n 	group by CONVERT(VARCHAR(10), THOIDIEM, 120),ddkd_fk "+
					"\n ) VTDN on VTDN.ngay = b.NGAY and  VTDN.ddkd_fk = a.PK_SEQ "+
					"\n left join  "+
					"\n ( "+
					"\n 	select CONVERT(VARCHAR(10), THOIDIEM, 120) as ngay, ddkd_fk, 1 num  "+
					"\n 	from DDKD_NPP where convert(time(3),THOIDIEM) > '12:00:00.000' and thoidiem is not null  "+
					"\n 	group by CONVERT(VARCHAR(10), THOIDIEM, 120),ddkd_fk "+
					"\n ) VTCN on VTCN.ngay = b.NGAY and  VTCN.ddkd_fk = a.PK_SEQ "+
					"\n left join  "+
					"\n ( "+
					"\n 	select CONVERT(VARCHAR(10), THOIDIEM, 120) as ngay, ddkd_fk, count(distinct khachhang_fk) as VTDT "+
					"\n 	from ddkd_khachhang where isdungtuyen = 1 "+
					"\n 	group by CONVERT(VARCHAR(10), THOIDIEM, 120),ddkd_fk "+
					"\n ) VTDT on VTDT.ngay = b.NGAY and  VTDT.ddkd_fk = a.PK_SEQ "+
					"\n left join  "+
					"\n ( "+
					"\n 	select CONVERT(VARCHAR(10), THOIDIEM, 120) as ngay, ddkd_fk, count(distinct khachhang_fk) as VTTT "+
					"\n 	from ddkd_khachhang where isdungtuyen = 0 "+
					"\n 	group by CONVERT(VARCHAR(10), THOIDIEM, 120),ddkd_fk "+
					"\n ) VTTT on VTTT.ngay = b.NGAY and  VTTT.ddkd_fk = a.PK_SEQ "+
					"\n left join  "+
					"\n ( "+
					"\n 	select CONVERT(VARCHAR(10), THOIDIEM, 120) as ngay, ddkd_fk, count(distinct khachhang_fk) as VTTONG "+
					"\n 	from ddkd_khachhang where 1=1 "+
					"\n 	group by CONVERT(VARCHAR(10), THOIDIEM, 120),ddkd_fk "+
					"\n ) VTTONG on VTTONG.ngay = b.NGAY and  VTTONG.ddkd_fk = a.PK_SEQ "+
					"\n where 1=1 "+condition;
			
			query +="\n order by npp.TEN,a.TEN, b.NGAY ,a.smartid";


			System.out.println("[XuatFileExcelChiTiet]"+query);

			dbutils db = new dbutils();
			ResultSet rs = db.get(query);

			int count = 5;
			if(rs != null)
			{
				String nppTenOLD = "";
				String ddkdTenOLD = "";
				Label label;

				while(rs.next())
				{
					String nppTen = rs.getString("nppTen");
					String smartid = rs.getString("smartid");
					String ddkdTen = rs.getString("ddkdTen");
					String tuyen = rs.getString("ngayid");
					String ngay = rs.getString("NGAY");
					String vtdn = rs.getString("vtdn");
					String vtcn = rs.getString("vtcn");
					String sokhcc = rs.getString("sokhcc");
					String vtdt = rs.getString("vtdt");
					String vttt = rs.getString("vttt");
					String vttong = rs.getString("vttong");
					String cong = rs.getString("cong");
					String trangthai =  rs.getString("trangthai");
					

					label = new Label(0, count, nppTen);
					sheet.addCell(label);
					
					label = new Label(1, count, smartid);
					sheet.addCell(label);
					
					label = new Label(2, count, ddkdTen);
					sheet.addCell(label);
					
					label = new Label(3, count, trangthai);
					sheet.addCell(label);
					
					label = new Label(4, count, tuyen);
					sheet.addCell(label);

					label = new Label(5, count, ngay);
					sheet.addCell(label);

					label = new Label(6, count, vtdn);
					sheet.addCell(label);
					
					label = new Label(7, count, vtcn);
					sheet.addCell(label);

					label = new Label(8, count, sokhcc);
					sheet.addCell(label);

					label = new Label(9, count, vtdt);
					sheet.addCell(label);
					
					label = new Label(10, count, vttt);
					sheet.addCell(label);
					
					label = new Label(11, count, vttong);
					sheet.addCell(label);
					
					label = new Label(12, count, cong);
					sheet.addCell(label);
					

					count ++;

				}
				rs.close();
			}

			w.write();
			w.close();
		} 
		catch (Exception e)
		{
			System.out.println("Error Cac Ban : " + e.getMessage());
			e.printStackTrace();
		} 
		finally
		{
			if (out1 != null)
				out1.close();
		}

	}
	
	
	
	
	
	
	private void XuatFileExcelSR(HttpServletResponse response, ILoTrinh lotrinh) throws IOException
	{
		OutputStream out1 = null;
		try
		{
			String NppId = lotrinh.getnppId();
			String DdkdId = lotrinh.getddkdId();
			String tuyenId = lotrinh.gettuyenId();
			String tungay = lotrinh.getTungay();
			String denngay = lotrinh.getDenngay();
			String vitri = lotrinh.getViTri();

			String[] tungays = tungay.split("-");
			String[] denngays = denngay.split("-");

			String ngayId = lotrinh.gettuyenId();
			if(ngayId == null) ngayId = ""; else ngayId = ngayId.trim();
			int ngayIdi = 0;
			try {
				ngayIdi = Integer.parseInt(ngayId);
				if(ngayIdi < 1 || ngayIdi > 7) {
					ngayIdi = 0;
				}
			} catch(Exception e) { }


			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=LoTrinhBanHang.xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());

			dbutils db = new dbutils();
			String sql = "select npp.ten as tennpp ,npp.ma,kv.ten as tenkv,ddkd.pk_seq as ddkdid,ddkd.ten  as ddkdten " +
					" from nhaphanphoi npp  inner join khuvuc kv  on kv.pk_Seq=npp.khuvuc_fk " +
					" inner join daidienkinhdoanh  ddkd on ddkd.npp_fk=npp.pk_seq   " +
					" where 1=1  ";
			if(NppId.trim().length() > 0)
			{
				sql+=" and npp.pk_seq=" + NppId +"  ";
			}
			else
				sql += " and npp.pk_seq in " + util.quyen_npp(lotrinh.getUserId()) + " ";
			if(vitri.length() > 0)
				sql += " and ddkd.VITRI = '"+vitri+"' ";

			if (DdkdId.trim().length() > 0)
			{
				sql += " and ddkd.pk_seq=" + DdkdId;
			}
			if(lotrinh.getkhuvucId().trim().length() > 0)
			{
				sql += " and kv.pk_seq = '" + lotrinh.getkhuvucId() + "' ";
			}
			if(lotrinh.getvungId().trim().length() > 0)
			{
				sql += " and kv.vung_fk = '" + lotrinh.getvungId() + "' ";
			}
			
			if(lotrinh.getkenhId().trim().length() > 0)
				sql += " and npp.pk_seq in (  select pk_seq from nhaphanphoi where kbh_fk = '" + lotrinh.getkenhId() + "' ) ";

			ResultSet rs = db.get(sql);
			int k = 0;
			while (rs.next())
			{
				WritableSheet sheet = w.createSheet(rs.getString("ddkdid"), k);

				sheet.addCell(new Label(0, 1, "NPP : "));
				sheet.addCell(new Label(1, 1, "" + rs.getString("tennpp")));

				sheet.addCell(new Label(0, 2, "KHU VỰC : "));
				sheet.addCell(new Label(1, 2, "" + rs.getString("tenkv")));

				sheet.addCell(new Label(0, 3, "NVBH : "));
				sheet.addCell(new Label(1, 3, "" + rs.getString("ddkdten")));

				sheet.addCell(new Label(2, 3, "Đơn vị tiền tệ:VND"));				

				WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
				cellFont.setColour(Colour.BLACK);

				WritableCellFormat cellFormat = new WritableCellFormat(cellFont);

				cellFormat.setBackground(jxl.format.Colour.GRAY_25);
				cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

				// cellFormat.setFont()
				sheet.addCell(new Label(0, 4, "KÊNH", cellFormat));
				sheet.addCell(new Label(1, 4, "KHU VỰC", cellFormat));
				sheet.addCell(new Label(2, 4, "NHÀ PHÂN PHỐI", cellFormat));
				sheet.addCell(new Label(3, 4, "NVBH", cellFormat));
				sheet.addCell(new Label(4, 4, "TUYẾN BÁN HÀNG", cellFormat));
				sheet.addCell(new Label(5, 4, "NGÀY", cellFormat));
				sheet.addCell(new Label(6, 4, "STT", cellFormat));
				sheet.addCell(new Label(7, 4, "KHÁCH HÀNG", cellFormat));
				sheet.addCell(new Label(8, 4, "ĐỊA CHỈ", cellFormat));
				sheet.addCell(new Label(9, 4, "DOANH SỐ", cellFormat));
				sheet.addCell(new Label(10, 4, "THỜI GIAN", cellFormat));
				sheet.addCell(new Label(11, 4, "ĐỘ LỆCH", cellFormat));
				sheet.addCell(new Label(12, 4, "THỜI ĐIỂM ĐI", cellFormat));
				sheet.addCell(new Label(13, 4, "GHI CHÚ", cellFormat));
				sheet.setColumnView(0, 16);
				sheet.setColumnView(1, 16);
				sheet.setColumnView(2, 20);
				sheet.setColumnView(3, 20);
				sheet.setColumnView(4, 20);
				sheet.setColumnView(5, 20);
				sheet.setColumnView(6, 20);
				sheet.setColumnView(7, 20);
				sheet.setColumnView(8, 20);
				sheet.setColumnView(9, 20);
				sheet.setColumnView(10, 20);
				sheet.setColumnView(11, 20);

				sheet.setColumnView(12, 20);

				sql = 
						"\n select   vt.vitri ,hch.hang as hangcuahang,lch.loai as loaicuahang,kh.phuong as Phuong, viengtham.ngayvt as NGAYTHANG, " +
								"\n 		kh.chucuahieu,kh.tinhthanh_fk,kh.quanhuyen_fk,   " +
								"\n 		kh.ten as tencuahieu, kh.smartid, kh.diachi, kh.dienthoai, viengtham.NGAYID, " +
								"\n 		kbh.DIENGIAI AS KBH, npp.TEN AS NPP, KV.TEN AS KHUVUC, ddkd.TEN, isnull(kh.LAT, '0') as khLat, isnull(kh.long, '0') as khLong,  " +
								"\n 		ISNULL ( 	 ( select sum(soluong*giamua) 	from donhang_sanpham dhsp inner join donhang dh on dh.pk_seq=dhsp.donhang_fk 	where dh.khachhang_fk=kh.pk_seq and dh.trangthai != 2 and dh.ngaynhap = viengtham.ngayvt    	),0 )  as DoanhSo  , " +
								"\n 		viengtham.*  " +
								"\n from DAIDIENKINHDOANH ddkd " +
								"\n left join " +
								"\n ( " +
								"\n 	 select c.ThoidiemDi , a.pk_seq as ddkdId, a.TEN as ddkdTen, b.DIENGIAI, b.NGAYID, thoidiem, datepart(dw, c.thoidiem) as ngayidvt, 0 as type, c.khachhang_fk as khId, b.NPP_FK as nppId, b.PK_SEQ as tbhId, Replace(convert(nvarchar(10), thoidiem , 102) , '.', '-' ) as  ngayvt, kh_tbh.SOTT as sott, kh_tbh.TANSO as tanso, isnull(c.lat, '0') as lat, isnull(c.long, '0') as long, isnull(c.dolech, '') as dolech,ISNULL(C.GHICHU,'') AS GHICHU " +
								"\n 	 from DAIDIENKINHDOANH a inner join TUYENBANHANG b on a.PK_SEQ = b.DDKD_FK " +
								"\n 	 left join ddkd_khachhang c on a.PK_SEQ = c.ddkd_fk and b.NGAYID = datepart(dw, c.thoidiem) " +
								"\n 	 inner join KHACHHANG_TUYENBH kh_tbh on b.PK_SEQ = kh_tbh.TBH_FK and kh_tbh.KHACHHANG_FK = c.khachhang_fk  " +
								"\n 	 left join  " +
								"\n 	 ( " +
								"\n 		select   MAX(thoidiem) as thoidiemmax,ddkd_fk,khachhang_fk, Replace(convert(nvarchar(10), thoidiem , 102) , '.', '-' ) as ngay from ddkd_khachhang  " +
								"\n  		group by ddkd_fk,khachhang_fk, Replace(convert(nvarchar(10), thoidiem , 102) , '.', '-' )  " +
								"\n 	 )  " +
								"\n 	 maxtd on maxtd.thoidiemmax = c.thoidiem and maxtd.ddkd_fk = c.ddkd_fk and maxtd.khachhang_fk = c.khachhang_fk  " +
								"\n  " +
								"\n union all " +
								"\n 	 select  c.ThoidiemDi,a.pk_seq as ddkdId, a.TEN as ddkdTen, b.DIENGIAI, b.NGAYID, thoidiem, datepart(dw, c.thoidiem), 1, c.khachhang_fk, b.NPP_FK, b.PK_SEQ, Replace(convert(nvarchar(10), thoidiem , 102) , '.', '-' ), kh_tbh.SOTT as sott, kh_tbh.TANSO as tanso, isnull(c.lat, '0') as lat, isnull(c.long, '0') as long, isnull(c.dolech, '') as dolech, ISNULL(C.GHICHU,'') AS GHICHU " +
								"\n 	 from DAIDIENKINHDOANH a inner join TUYENBANHANG b on a.PK_SEQ = b.DDKD_FK " +
								"\n 	 left join ddkd_khachhang c on a.PK_SEQ = c.ddkd_fk and b.NGAYID != datepart(dw, c.thoidiem) " +
								"\n 	 inner join KHACHHANG_TUYENBH kh_tbh on b.PK_SEQ = kh_tbh.TBH_FK and kh_tbh.KHACHHANG_FK = c.khachhang_fk  " +
								"\n 	 left join  " +
								"\n 	 ( " +
								"\n 		select   MAX(thoidiem) as thoidiemmax,ddkd_fk,khachhang_fk, Replace(convert(nvarchar(10), thoidiem , 102) , '.', '-' ) as ngay from ddkd_khachhang  " +
								"\n  		group by ddkd_fk,khachhang_fk, Replace(convert(nvarchar(10), thoidiem , 102) , '.', '-' )  " +
								"\n 	 )  " +
								"\n 	 maxtd on maxtd.thoidiemmax = c.thoidiem and maxtd.ddkd_fk = c.ddkd_fk and maxtd.khachhang_fk = c.khachhang_fk  " +
								"\n ) " +
								"\n viengtham on viengtham.ddkdId = ddkd.PK_SEQ " +
								"\n  " +
								"\n left join khachhang kh on kh.pk_seq = viengtham.khId " +
								"\n left join hangcuahang hch on hch.pk_seq = kh.hch_fk  " +
								"\n left join loaicuahang lch on lch.pk_seq = kh.lch_fk  " +
								"\n left join vitricuahang vt on vt.pk_seq = kh.vtch_fk " +
								"\n left join NHAPHANPHOI npp on npp.PK_SEQ = viengtham.nppId " +
								"\n left join DDKD_GSBH on DDKD_GSBH.DDKD_FK = ddkd.PK_SEQ  " +
								"\n left join GIAMSATBANHANG gsbh on gsbh.PK_SEQ = DDKD_GSBH.GSBH_FK  " +
								"\n left join KENHBANHANG kbh on kbh.PK_SEQ = gsbh.KBH_FK  " +
								"\n left join KHUVUC KV on gsbh.KHUVUC_FK = KV.PK_SEQ  " +
								"\n where viengtham.ddkdId = '"+rs.getString("ddkdid")+"' " +
								//"\n and ddkd.trangthai = '1' " +
								"\n and '" + tungay + "' <= ngayvt and ngayvt <= '" + denngay + "' " + (ngayIdi != 0 ? " and viengtham.ngayid = '" + lotrinh.gettuyenId().trim() + "' " : " " );

				sql +=	" order by ddkdTen, thoidiem asc, type asc, viengtham.NGAYID asc, sott ";



				System.out.println("[LoTrinhBanHangReport.XuatExcel] sql = \n" + sql);

				ResultSet rs1 = db.get(sql);
				Label label;

				int j = 5;
				// set style to cell data
				WritableCellFormat cellFormat2 = new WritableCellFormat();

				cellFormat2.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat2.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat2.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat2.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

				WritableCellFormat cellFormat3 = new WritableCellFormat();
				cellFormat3.setBackground(jxl.format.Colour.YELLOW);
				cellFormat3.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat3.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat3.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat3.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

				WritableCellFormat cformat;


				NumberFormat dp3 = new NumberFormat("#,###,###,##");

				WritableCellFormat inFormat = new WritableCellFormat(dp3);
				inFormat.setFont(cellFont);

				inFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
				inFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
				inFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
				inFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);


				String thoidiemTruoc = "";

				if (rs1 != null)
					while (rs1.next())
					{
						String thoidiemTam = rs1.getString("thoidiem");
						if(thoidiemTam == null) thoidiemTam = ""; else thoidiemTam = thoidiemTam.trim();


						if(!thoidiemTam.trim().equals(thoidiemTruoc)) {
							thoidiemTruoc = thoidiemTam;

							//Xử lý độ lệch
							String dolech = "";
							String dolechDb = rs1.getString("dolech"); //Lấy độ lệch trong db

							if (dolechDb == null) {
								double kc = -200;						
								double khlat = rs1.getDouble("khLat");
								double khlon = rs1.getDouble("khLong");
								double lat = rs1.getDouble("lat");
								double lon = rs1.getDouble("long");

								if(khlat != 0 && khlon != 0 && lat != 0 && lon != 0) {
									kc = getKhoangCachHaversine(khlat, khlon, lat, lon);
								}

								if(kc >= 0) {
									dolech = kc + "";
								}
							} else {
								dolech = dolechDb;
							}

							double _dolech = -1;
							try {
								_dolech = Double.parseDouble(dolech);
								if(_dolech > 30) {
									_dolech = 30;
								}
							} catch(Exception e) {

							}


							String type = rs1.getString("type") == null ? "0" : rs1.getString("type").trim();

							cformat = type.equals("1") ? cellFormat3 : cellFormat2;

							//Vẽ giao diện
							label = new Label(0, j, rs1.getString("KBH"), cformat);
							sheet.addCell(label);

							label = new Label(1, j, rs1.getString("KHUVUC"), cformat);
							sheet.addCell(label);

							label = new Label(2, j, rs1.getString("NPP"), cformat);
							sheet.addCell(label);

							label = new Label(3, j, rs1.getString("TEN"), cformat);
							sheet.addCell(label);

							label = new Label(4, j, rs1.getString("NGAYID"), cformat);
							sheet.addCell(label);

							label = new Label(5, j, rs1.getString("NGAYTHANG"), cformat);
							sheet.addCell(label);

							label = new Label(6, j, rs1.getString("SOTT"), cformat);
							sheet.addCell(label);

							label = new Label(7, j, rs1.getString("TENCUAHIEU"), cformat);
							sheet.addCell(label);

							label = new Label(8, j, rs1.getString("DIACHI"), cformat);
							sheet.addCell(label);

							Number number = new Number(9, j, rs1.getDouble("DoanhSo"), inFormat);
							sheet.addCell(number);						

							label = new Label(10, j, rs1.getString("THOIDIEM"), cformat);
							sheet.addCell(label);

							label = new Label(11, j, String.valueOf(_dolech), cformat);
							sheet.addCell(label);

							label = new Label(12, j, rs1.getString("thoidiemdi") , cformat);
							sheet.addCell(label);
							
							label = new Label(13, j, rs1.getString("ghichu") , cformat);
							sheet.addCell(label);
							j++;

						}
					}

				k++;
			}
			w.write();
			w.close();
		} catch (Exception e)
		{
			System.out.println("Error Cac Ban : " + e.getMessage());
			e.printStackTrace();
		} finally
		{
			if (out1 != null)
				out1.close();
		}
	}

	public double getKhoangCachHaversine(double lat1, double long1, double lat2, double long2)
	{
		double R = 6371.00;
		double dLat, dLon, a, c;

		dLat = toRad(lat2 - lat1);
		dLon = toRad(long2 - long1);
		lat1 = toRad(lat1);
		lat2 = toRad(lat2);
		a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
				Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
		c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return R * c * 1000; //m
	}

	private double toRad(double value)
	{
		return value * Math.PI / 180;
	}

	private String getDateTime() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);	
	}

	private String getMonth() 
	{
		DateFormat dateFormat = new SimpleDateFormat("MM");
		Date date = new Date();
		return dateFormat.format(date);	
	}

	private String getYear() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy");
		Date date = new Date();
		return dateFormat.format(date);	
	}


	public static void main(String[] args)
	{
		Calendar tu_ngay = new GregorianCalendar(2013, 1, 28);		

		tu_ngay.add(Calendar.DATE, 1);
		System.out.println("COMPARE: " + tu_ngay.get(Calendar.YEAR));
		System.out.println("COMPARE: " + tu_ngay.get(Calendar.MONTH));
		System.out.println("COMPARE: " + tu_ngay.get(Calendar.DAY_OF_MONTH));
	}


}
