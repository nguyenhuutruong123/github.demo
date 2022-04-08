package geso.dms.center.servlets.reports;

import geso.dms.center.beans.lotrinh.ILoTrinh;
import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.PivotFieldType;
import com.aspose.cells.PivotTable;
import com.aspose.cells.PivotTables;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class DistributionTT_Svl extends HttpServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DistributionTT_Svl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String userTen = (String) session.getAttribute("userTen");
		Utility util = new Utility();
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
		if(view == null)
			view = "";

		IStockintransit obj = new Stockintransit();
		obj.setView(view);

		obj.settungay("");
		obj.setdenngay("");
		obj.setuserId(userId);
		obj.init();
		session.setAttribute("checkedSKU", "");
		session.setAttribute("obj", obj);
		session.setAttribute("loi", "");
		session.setAttribute("userId", userId);
		session.setAttribute("userTen", userTen);
		String nextJSP = "/AHF/pages/Center/DistributionTT.jsp";
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		OutputStream out = response.getOutputStream();
		// Ireportnpp obj = new Reports();
		IStockintransit obj = new Stockintransit();
		boolean bfasle = true;
		Utility util = new Utility();
		try
		{
			HttpSession session = request.getSession();

			String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
			if(view == null)
				view = "";

			obj.setView(view);

			String userTen = (String) session.getAttribute("userTen");
			if (userTen == null)
				userTen = "";
			obj.setuserTen(userTen);
			String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
			if (tungay == null)
				tungay = "";
			obj.settungay(tungay);

			String userId = (String) session.getAttribute("userId");
			obj.setuserId(userId);
			String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
			if (denngay == null)
				denngay = "";
			obj.setdenngay(denngay);

			String skuId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("skuid")));
			if (skuId == null)
				skuId = "";
			obj.setsanphamId(skuId);

			
			if(view.equals("TT"))
			{
				String manpp = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
				if (manpp == null)
					manpp = "";
				obj.setnppId(manpp);
			}
			else
			{
				String npp = util.getIdNhapp(userId);
				obj.setnppId(npp);
			}
			
			
			obj.setvungId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")) != null ? util.antiSQLInspection(request.getParameter("vungId")) : ""));
			obj.setkhuvucId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")) != null ? util.antiSQLInspection(request.getParameter("khuvucId")) : ""));
			obj.setkenhId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")) != null ? util.antiSQLInspection(request.getParameter("kenhId")) : ""));

			obj.setnhanhangId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhanhangId")) != null ? util.antiSQLInspection(request.getParameter("nhanhangId")) : ""));
			obj.setchungloaiId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chungloaiId")) != null ? util.antiSQLInspection(request.getParameter("chungloaiId")) : ""));
			obj.setdvkdId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")) != null ? util.antiSQLInspection(request.getParameter("dvkdId")) : ""));

			obj.setNhomskusId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhomskusId")) != null ? util.antiSQLInspection(request.getParameter("nhomskusId").trim()) : ""));

			obj.SetNhoSPId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhomspid")) != null ? util.antiSQLInspection(request.getParameter("nhomspid")) : ""));
			obj.setchon(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chonid")));
			obj.settype(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("typeid")));
			obj.setuserId(userId);
			System.out.println("Type :" + obj.gettype());

			String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
			if (action.equals("create"))
			{
				String[] skutest = request.getParameterValues("skutest");
				String[] valuechecked = request.getParameterValues("valuechecked");
				String whereSKU = "";
				String chuoichuyen = "";
				dbutils db = new dbutils();
				if (valuechecked.length > 0)
					for (int i = 0; i < skutest.length; i++)
					{
						if (valuechecked[i].equals("1"))
						{
							whereSKU += skutest[i] + ",";
							chuoichuyen = chuoichuyen + "," + skutest[i];
							String sql = "insert into test (a) values (" + skutest[i] + ")";
							db.update(sql);
						}
					}
				db.shutDown();

				if (whereSKU != "")
					// whereSKU =" ("+ whereSKU.substring(0,
					// whereSKU.length()-1)+")";

					whereSKU = whereSKU.substring(0, whereSKU.length() - 1);

				obj.setsanphamId(whereSKU);
				if (chuoichuyen != "")
				{
					session.setAttribute("checkedSKU", chuoichuyen.substring(1, chuoichuyen.length()));
				} else
				{
					session.setAttribute("checkedSKU", "");
				}
				System.out.println("Chuoi SKU la " + whereSKU);

				/*
				 * response.setContentType("application/vnd.ms-excel");
				 * response.setHeader("Content-Disposition",
				 * "attachment; filename=DistributionReport.xls");
				 */
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "Attachment; filename=DistributionReport(TT)" + this.getPiVotName() + ".xlsm");

				CreatePivotTable(out, response, request, obj, bfasle);
			} else if (action.equals("createdp"))
			{
				XuatFileExcelBCDoPhu(response, obj);
			} else
			{
				session.setAttribute("checkedSKU", "");
				session.setAttribute("obj", obj);
				session.setAttribute("loi", "");
				session.setAttribute("userId", obj.getuserId());
				session.setAttribute("userTen", obj.getuserTen());
				obj.init();
				session.setAttribute("obj", obj);
				String nextJSP = "/AHF/pages/Center/DistributionTT.jsp";
				response.sendRedirect(nextJSP);
			}
		} catch (Exception ex)
		{
			ex.printStackTrace();

			// say sorry
			response.setContentType("text/html");
			PrintWriter writer = new PrintWriter(out);

			writer.println("<html>");
			writer.println("<head>");
			writer.println("<title>sorry</title>");
			writer.println("</head>");
			writer.println("<body>");
			writer.println("<h1>Xin loi, khong the tao pivot table...</h1>");
			ex.printStackTrace(writer);
			writer.println("</body>");
			writer.println("</html>");
			writer.close();
		}
	}

	private void XuatFileExcelBCDoPhu(HttpServletResponse response,IStockintransit obj) throws IOException
	{
		OutputStream out1 = null;
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=Baocaodophusanphamnew.xlsm");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());

			WritableSheet sheet = w.createSheet("LoTrinhBanHang", 0);
			geso.dms.center.util.Utility Uti_center = new geso.dms.center.util.Utility();

			WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 14);
			cellTitle.setColour(Colour.BLACK);
			cellTitle.setBoldStyle(WritableFont.BOLD);

			sheet.addCell(new Label(0, 0, "BÁO CÁO ĐỘ PHỦ SẢN PHẨM NEW", new WritableCellFormat(cellTitle)));


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
			sheet.addCell(new Label(0, 4, "Vùng", cellFormat));
			sheet.addCell(new Label(1, 4, "Khu vực", cellFormat));
			sheet.addCell(new Label(2, 4, "Tỉnh thành", cellFormat));
			sheet.addCell(new Label(3, 4, "Quận huyện", cellFormat));
			sheet.addCell(new Label(4, 4, "GSBH", cellFormat));
			sheet.addCell(new Label(5, 4, "Mã NVBH", cellFormat));
			sheet.addCell(new Label(6, 4, "Tên NVBH", cellFormat));
			sheet.addCell(new Label(7, 4, "Mã khách hàng", cellFormat));
			sheet.addCell(new Label(8, 4, "Tên khách hàng", cellFormat));
			sheet.addCell(new Label(9, 4, "Nhãn hàng", cellFormat));
			sheet.addCell(new Label(10, 4, "Chủng loại", cellFormat));
			sheet.addCell(new Label(11, 4, "Nhóm SP", cellFormat));
			sheet.addCell(new Label(12, 4, "Mã SP", cellFormat));
			sheet.addCell(new Label(13, 4, "Tên Sản Phẩm", cellFormat));
			sheet.addCell(new Label(14, 4, "Số Lượng", cellFormat));
			sheet.addCell(new Label(15, 4, "Ngày", cellFormat));
			sheet.addCell(new Label(16, 4, "Nhóm SKU's", cellFormat));


			sheet.setColumnView(0, 10);
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




			String query =  "\n select v.ten as Vung, kv.ten as KhuVuc, tt.ten as tinhthanh, qh.ten as quanhuyen, "+
			"\n (select top 1 ten from GIAMSATBANHANG where pk_seq in (select GSBH_FK from DDKD_GSBH where DDKD_FK = e.PK_SEQ)) as GSBH,   "+
			"\n e.SMARTID as MaNvbh, e.TEN as TenNVBH,  b.SMARTID as MaKH, b.ten as tenKH, nh.ten as nhanhang, cl.ten as chungloai, "+
			"\n nsp.TEN as nhomsp, sp.ma as masp, sp.ten as tensp, 1 as soluong, a.ngay, isnull(nskus.TEN, '') as nhomskus "+
			"\n from DophuSP a "+
			"\n inner join khachhang b on a.KH_FK = b.PK_SEQ "+
			"\n inner join KHACHHANG_TUYENBH c on b.PK_SEQ = c.KHACHHANG_FK "+
			"\n inner join TUYENBANHANG d on d.PK_SEQ = c.TBH_FK "+
			"\n inner join DAIDIENKINHDOANH e on e.PK_SEQ = d.DDKD_FK "+
			"\n inner join nhaphanphoi f on f.PK_SEQ = b.NPP_FK "+
			"\n inner join KHUVUC kv on kv.PK_SEQ = f.KHUVUC_FK "+
			"\n inner join vung v on v.PK_SEQ = kv.VUNG_FK "+
			"\n inner join TINHTHANH tt on tt.PK_SEQ = f.TINHTHANH_FK "+
			"\n inner join QUANHUYEN qh on qh.PK_SEQ = f.QUANHUYEN_FK "+
			"\n inner join sanpham sp on sp.ma = a.SanPham_FK "+
			"\n left join NHOMSANPHAM_SANPHAM nhsp on nhsp.SP_FK = sp.PK_SEQ "+
			"\n left join nhomsanpham nsp on nsp.PK_SEQ = nhsp.NSP_FK "+
			"\n left join NHANHANG nh on nh.PK_SEQ = sp.NHANHANG_FK "+
			"\n left join CHUNGLOAI cl on cl.PK_SEQ = sp.CHUNGLOAI_FK " +
			"\n left join NHOMSKUS_SANPHAM ssp on ssp.SP_FK = sp.PK_SEQ " +
			"\n left join NHOMSKUS nskus on ssp.NSKUS_FK = nskus.PK_SEQ "
			+ " where 1=1 ";

			if(obj.gettungay().length() > 0)
			{
				query += " and a.ngay >= '"+obj.gettungay()+"' ";

			}

			if(obj.getdenngay().length() > 0)
			{
				query += " and a.ngay <= '"+obj.getdenngay()+"' ";

			}

			if(obj.getnhanhangId().length() > 0)
			{
				query += " and nh.pk_seq = '"+obj.getnhanhangId()+"' ";

			}
			if(obj.getchungloaiId().length() > 0)
			{
				query += " and cl.pk_seq = '"+obj.getchungloaiId()+"' ";

			}
			if(obj.GetNhoSPId().length() >0)
			{
				query+="and nsp.pk_seq ='"+obj.GetNhoSPId()+"'";
			}
			if(obj.getkenhId().length()>0)
			{
				query+=" and b.kbh_fk ="+obj.getkenhId()+ " ";
			}
			if(obj.getnppId().length()>0)
			{
				query+=" and f.pk_seq ="+obj.getnppId()+ " ";
			}

			if(obj.getDdkd().length()>0)
			{
				query+=" and e.pk_seq ="+obj.getDdkd()+ " ";
			}
			if(obj.getvungId().length()>0)
			{
				query+=" and v.pk_seq ="+obj.getvungId()+ " ";
			}
			if(obj.getkhuvucId().length()>0)
			{
				query+=" and kv.pk_seq ="+obj.getkhuvucId()+ " ";
			}
			if (obj.getNhomskusId().length() > 0) {
				query+="and nskus.PK_SEQ = '" + obj.getNhomskusId() + "' ";
			}
			query+=" and f.pk_seq  in " + Uti_center.quyen_npp(obj.getuserId()) +" ";
			//query+=" and nskus.PK_SEQ  in " + Uti_center.quyen_nhomskus(obj.getuserId()) +" ";




			System.out.println("[XuatFileExceldophusp]"+query);

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
					String vung = rs.getString("vung");
					String KhuVuc = rs.getString("KhuVuc");
					String tinhthanh = rs.getString("tinhthanh");
					String quanhuyen = rs.getString("quanhuyen");
					String GSBH = rs.getString("GSBH");
					String MaNvbh = rs.getString("MaNvbh");
					String TenNVBH = rs.getString("TenNVBH");
					String MaKH = rs.getString("MaKH");
					String tenKH = rs.getString("tenKH");
					String nhanhang = rs.getString("nhanhang");
					String chungloai = rs.getString("chungloai");
					String nhomsp =  rs.getString("nhomsp");
					String masp = rs.getString("masp");
					String tensp = rs.getString("tensp");
					String soluong = rs.getString("soluong");
					String ngay =  rs.getString("ngay");
					String nhomskus =  rs.getString("nhomskus");



					label = new Label(0, count, vung);
					sheet.addCell(label);

					label = new Label(1, count, KhuVuc);
					sheet.addCell(label);

					label = new Label(2, count, tinhthanh);
					sheet.addCell(label);

					label = new Label(3, count, quanhuyen);
					sheet.addCell(label);

					label = new Label(4, count, GSBH);
					sheet.addCell(label);

					label = new Label(5, count, MaNvbh);
					sheet.addCell(label);

					label = new Label(6, count, TenNVBH);
					sheet.addCell(label);

					label = new Label(7, count, MaKH);
					sheet.addCell(label);

					label = new Label(8, count, tenKH);
					sheet.addCell(label);

					label = new Label(9, count, nhanhang);
					sheet.addCell(label);

					label = new Label(10, count, chungloai);
					sheet.addCell(label);

					label = new Label(11, count, nhomsp);
					sheet.addCell(label);

					label = new Label(12, count, masp);
					sheet.addCell(label);

					label = new Label(13, count, tensp);
					sheet.addCell(label);

					label = new Label(14, count, soluong);
					sheet.addCell(label);

					label = new Label(15, count, ngay);
					sheet.addCell(label);

					label = new Label(16, count, nhomskus);
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

	private void CreatePivotTable(OutputStream out, HttpServletResponse response, HttpServletRequest request, IStockintransit obj, boolean bfasle) throws IOException
	{

		String chuoi = getServletContext().getInitParameter("path") + "\\Distribution(TT).xlsm";

		FileInputStream fstream = new FileInputStream(chuoi);
		//File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\Distribution(TT).xlsm");
		//FileInputStream fstream = new FileInputStream(f) ;
		Workbook workbook = new Workbook();
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		// Buoc2 tao khung
		// ham tao khu du lieu
		CreateStaticHeader(workbook, obj.gettungay(), obj.getdenngay(), obj.getuserTen());
		// Buoc3
		// day du lieu vao
		CreateStaticData(workbook, obj, bfasle);
		HttpSession session = request.getSession();

		obj.init();
		session.setAttribute("obj", obj);

		if (bfasle == false)
		{
			String loi = "chua co bao cao trong thoi gian nay, vui long chon lai thoi gian xem bao cao";

			session.setAttribute("checkedSKU", "");
			session.setAttribute("obj", obj);
			session.setAttribute("loi", "");
			session.setAttribute("userId", obj.getuserId());
			session.setAttribute("userTen", obj.getuserTen());
			obj.init();
			session.setAttribute("obj", obj);
			String nextJSP = "/AHF/pages/Center/DistributionTT.jsp";
			response.sendRedirect(nextJSP);
		} else
		{
			// Saving the Excel file
			workbook.save(out);
			fstream.close();
		}
	}

	private void CreateStaticHeader(Workbook workbook, String dateFrom, String dateTo, String UserName)
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");
		Cells cells = worksheet.getCells();

		Style style;
		// cells.setColumnWidth(0, 200.0f);
		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("A1");
		cell.setValue("BÁO CÁO ĐỘ PHỦ SẢN PHẨM");

		style = cell.getStyle();

		Font font2 = new Font();
		font2.setColor(Color.RED);// mau chu
		font2.setSize(14);// size chu
		style.setFont(font2);
		style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu
		cell.setStyle(style);
		cell = cells.getCell("A2");
		getCellStyle(workbook, "A2", Color.NAVY, true, 10);
		cell.setValue("Từ ngày " + dateFrom);
		cell = cells.getCell("B2");
		getCellStyle(workbook, "B2", Color.NAVY, true, 10);
		cell.setValue("Tới Ngày " + dateTo);

		cell = cells.getCell("A3");
		getCellStyle(workbook, "A3", Color.NAVY, true, 10);
		cell.setValue("Ngày báo cáo: " + this.getDateTime());
		cell = cells.getCell("A4");
		getCellStyle(workbook, "A4", Color.NAVY, true, 10);
		cell.setValue("Được tạo bởi:  " + UserName);

		cell = cells.getCell("AA1");
		cell.setValue("Kenh");
		cell = cells.getCell("AB1");
		cell.setValue("Vung");
		cell = cells.getCell("AC1");
		cell.setValue("Khu Vuc");
		cell = cells.getCell("AD1");
		cell.setValue("Nha Phan Phoi");
		cell = cells.getCell("AE1");
		cell.setValue("Dai Dien Kinh Doanh");
		cell = cells.getCell("AF1");
		cell.setValue("Khach Hang");
		cell = cells.getCell("AG1");
		cell.setValue("SKU");
		cell = cells.getCell("AH1");
		cell.setValue("Loai Cua Hang");
		cell = cells.getCell("AI1");
		cell.setValue("Vi Tri Cua Hang");
		cell = cells.getCell("AJ1");
		cell.setValue("Hang Cua Hang");
		cell = cells.getCell("AK1");
		cell.setValue("Nhom Khach Hang");
		cell = cells.getCell("AL1");
		cell.setValue("Nhan Hang");
		cell = cells.getCell("AM1");
		cell.setValue("Chung Loai");
		cell = cells.getCell("AN1");
		cell.setValue("Nhom San Pham");
		cell = cells.getCell("AO1");
		cell.setValue("Outlet");
		cell = cells.getCell("AP1");
		cell.setValue("Doanh So");
		cell = cells.getCell("AQ1");
		cell.setValue("So Luong");
		cell = cells.getCell("AR1");
		cell.setValue("Tinh Thanh");
		cell = cells.getCell("AS1");
		cell.setValue("Quan Huyen");
		cell = cells.getCell("AT1");
		cell.setValue("Phuong");
		cell = cells.getCell("AU1");
		cell.setValue("MaKhachHang");
		cell = cells.getCell("AV1");
		cell.setValue("SoSP");
		cell = cells.getCell("AW1");
		cell.setValue("nhomskus");
		cell = cells.getCell("AX1");
		cell.setValue("DiaChiKH");
	}

	private void CreateStaticData(Workbook workbook, IStockintransit obj, boolean bfasle)
	{
		geso.dms.center.util.Utility Uti_center = new geso.dms.center.util.Utility();
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();
		ResultSet rs=null;
		String dk = "";
		if(obj.getnhanhangId().length() > 0)
		{
			dk += " and sp.nhanhang_fk = '"+obj.getnhanhangId()+"' ";

		}
		if(obj.getchungloaiId().length() > 0)
		{
			dk += " and sp.chungloai_fk = '"+obj.getchungloaiId()+"' ";

		}
		if(obj.GetNhoSPId().length() > 0)
		{
			dk += " and sp.chungloai_fk = '"+obj.GetNhoSPId()+"' ";

		}

		String query=
			"\n	 select  kbh.ten as channel, v.ten as region,dvkd.diengiai as unit, kv.ten as area, npp.ten as distributor, ddkd.ten as sale_rep,  "+
			"\n		kh.smartid as cuscode,kh.ten as custommer, isnull(kh.phuong,'chưa xác định ') as phuong, isnull(tt.ten,'chưa xác định') as tinhthanh,  "+ 
			"\n		isnull(qh.ten,'chưa xác định') as quanhuyen,    "+
			"\n		(sph.ma + sph.ten) as sku, lch.diengiai as outlet_type, vt.diengiai as outlet_location, hch.diengiai as outlet_class,  "+ 
			"\n		case when nhomkh.diengiai is null then '' else nhomkh.diengiai end as group_customer, sph.nhanhang as brand, sph.chungloai  as catogery,  "+ 
			"\n		sph.outlet,sph.volume, sph.sanluong as sanluong, sph.baophu,sph.nhomsp as nhomsp, sph.soluong, ISNULL(sph.sodh, 0) as sosp    "+
			"\n		, isnull(sph.nhomskus, '') as nhomskus, kh.diachi diachikh " + 	

			"\n	 from  "+
			"\n	(	  "+
			"\n		select  dh.npp_fk,sp.pk_seq AS SANPHAM_FK,sp.dvkd_fk,dh.ddkd_fk,dh.khachhang_fk,1 as outlet , cl.TEN as chungloai,nh.pk_seq as nhanhang_fk, cl.pk_seq as chungloai_fk"
			+ "			,nh.TEN as nhanhang,nhsp.TEN as nhomsp,nhsp.pk_seq as nhomsp_fk,   "+
			"\n			isnull (sum(dhsp.soluong* giamua),0) as volume, sum(dhsp.soluong) as soluong  , 1 as baophu  "+ 
			"\n			,sum(sp.trongluong*dhsp.soluong) as sanluong, sp.ma as ma, sp.ten as ten  "+
			"\n			, '' as nhomskus ,count(distinct dh.pk_seq) sodh  " +
			"\n		from donhang_sanpham dhsp inner join donhang dh on dh.pk_seq = dhsp.donhang_fk  "+
			"\n		inner join sanpham sp on sp.pk_seq=dhsp.sanpham_fk   "+dk+
			"\n		left join  NHANHANG nh on nh.PK_SEQ = sp.NHANHANG_FK  "+
			"\n		left join CHUNGLOAI cl on cl.PK_SEQ = sp.CHUNGLOAI_FK    "+
			"\n		left join NHOMSANPHAM_SANPHAM nsp on nsp.SP_FK = sp.PK_SEQ  "+
			"\n		left join NHOMSANPHAM nhsp on nhsp.PK_SEQ = nsp.NSP_FK  "+
			"\n		 where dh.trangthai =1 ";
		if(obj.getsanphamId().length() > 0)
		{
			query +=" and sp.pk_seq in ("+obj.getsanphamId()+") ";
		}
		query += "\n		and dh.tonggiatri > 0 and dh.ngaynhap  >= '"+obj.gettungay()+"' and dh.ngaynhap <='"+obj.getdenngay()+"'   "+
		"\n		 group by dh.npp_fk,dh.ddkd_fk,dh.khachhang_fk,sp.dvkd_fk, sp.ma, sp.ten, cl.TEN, nh.TEN,nhsp.TEN, sp.PK_SEQ,nh.pk_seq , cl.pk_seq,nhsp.pk_seq   "+
		"\n 		 " +
		"\n	)sph 	 "+

		"\n	 inner join khachhang kh on kh.pk_seq = sph.khachhang_fk "+
		"\n	inner join donvikinhdoanh dvkd on dvkd.pk_seq	=sph.dvkd_fk "+
		"\n	inner join nhaphanphoi npp on npp.pk_seq = sph.npp_fk  "+
		"\n	inner join daidienkinhdoanh ddkd on ddkd.pk_seq = sph.ddkd_fk and ddkd.npp_fk = npp.pk_seq "+
		"\n	inner join khuvuc kv on kv.pk_seq = npp.khuvuc_fk  "+
		"\n	inner join vung v on v.pk_seq = kv.vung_fk   "+
		"\n	left join loaicuahang lch on lch.pk_seq = kh.lch_fk  "+
		"\n	left join hangcuahang hch on hch.pk_seq = kh.hch_fk "+
		"\n	left join vitricuahang vt on vt.pk_seq = kh.vtch_fk   "+
		"\n	left join kenhbanhang kbh on kbh.pk_seq = kh.kbh_fk  "+
		"\n	left join "+ 
		"\n	( "+
		"\n		select distinct a.kh_fk,b.diengiai from nhomkhachhang_khachhang a  "+
		"\n		inner join nhomkhachhang b on b.pk_seq= a.nkh_fk "+
		"\n	) nhomkh on nhomkh.kh_fk = kh.pk_seq  "+
		"\n	left join tinhthanh tt on kh.tinhthanh_fk = tt.pk_seq  "+
		"\n	left join quanhuyen qh on kh.quanhuyen_fk = qh.pk_seq "+
		"\n	where 1=1 and kh.trangthai = 1 ";
		if(obj.getnhanhangId().length() > 0)
		{
			query += " and sph.nhanhang_fk = '"+obj.getnhanhangId()+"' ";

		}
		if(obj.getchungloaiId().length() > 0)
		{
			query += " and sph.chungloai_fk = '"+obj.getchungloaiId()+"' ";

		}
		if(obj.GetNhoSPId().length() >0)
		{
			query+="and sph.nhomsp_fk ='"+obj.GetNhoSPId()+"'";
		}
		if(obj.getkenhId().length()>0)
		{
			query+=" and kbh.pk_seq ="+obj.getkenhId()+ " ";
		}
		if(obj.getnppId().length()>0)
		{
			query+=" and npp.pk_seq ="+obj.getnppId()+ " ";
		}

		if(obj.getDdkd().length()>0)
		{
			query+=" and ddkd.pk_seq ="+obj.getDdkd()+ " ";
		}
		if(obj.getvungId().length()>0)
		{
			query+=" and v.pk_seq ="+obj.getvungId()+ " ";
		}
		if(obj.getkhuvucId().length()>0)
		{
			query+=" and kv.pk_seq ="+obj.getkhuvucId()+ " ";
		}

		if(obj.getdvkdId().length()>0)
		{
			query+=" and dvkd.pk_seq ="+obj.getdvkdId()+ " ";
		}
		
		if(obj.getView().equals("TT"))
			query+=" and npp.pk_seq  in " + Uti_center.quyen_npp(obj.getuserId()) +"";
		
		System.out.println("LAY BC: "+query);
		rs=db.get(query);


		int i = 2;
		if (rs != null)
		{

			try
			{// se do rong cho cac cot se dung
				cells.setColumnWidth(0, 15.0f);
				cells.setColumnWidth(1, 15.0f);
				cells.setColumnWidth(2, 15.0f);
				cells.setColumnWidth(3, 15.0f);
				cells.setColumnWidth(4, 15.0f);
				cells.setColumnWidth(5, 15.0f);
				cells.setColumnWidth(6, 15.0f);
				cells.setColumnWidth(7, 8.0f);
				cells.setColumnWidth(8, 8.0f);
				cells.setColumnWidth(9, 8.0f);
				cells.setColumnWidth(10, 15.0f);
				Cell cell = null;
				while (rs.next())// lap den cuoi bang du lieu
				{
					String Channel = rs.getString("Channel");

					String SaleRep = rs.getString("Sale_rep");
					String Region = rs.getString("Region");
					String Area = rs.getString("Area");
					String Distributor = rs.getString("Distributor");
					String Customer = rs.getString("custommer");
					String SKU = rs.getString("SKU");
					String OutletType = rs.getString("outlet_type");
					String OutletLocation = rs.getString("outlet_location");
					String OutletClass = rs.getString("outlet_class");
					String GroupCustomer = rs.getString("group_customer");
					String Brand = rs.getString("Brand");
					String Catogery = rs.getString("catogery");
					String NhomSP = rs.getString("NHOMSP");

					String SumOutlet = rs.getString("outlet");
					String VOLUME = rs.getString("VOLUME");

					cell = cells.getCell("AA" + Integer.toString(i));
					cell.setValue(Channel);
					cell = cells.getCell("AB" + Integer.toString(i));
					cell.setValue(Region);
					cell = cells.getCell("AC" + Integer.toString(i));
					cell.setValue(Area);
					cell = cells.getCell("AD" + Integer.toString(i));
					cell.setValue(Distributor);
					cell = cells.getCell("AE" + Integer.toString(i));
					cell.setValue(SaleRep);
					cell = cells.getCell("AF" + Integer.toString(i));
					cell.setValue(Customer);
					cell = cells.getCell("AG" + Integer.toString(i));
					cell.setValue(SKU);
					cell = cells.getCell("AH" + Integer.toString(i));
					cell.setValue(OutletType == null ? "Chua Xac Dinh" : OutletType);
					cell = cells.getCell("AI" + Integer.toString(i));
					cell.setValue(OutletLocation == null ? "Chua Xac Dinh" : OutletLocation);
					cell = cells.getCell("AJ" + Integer.toString(i));
					cell.setValue(OutletClass == null ? "Chua Xac Dinh" : OutletClass);
					cell = cells.getCell("AK" + Integer.toString(i));
					cell.setValue(GroupCustomer == null ? "Chua Xac Dinh" : GroupCustomer);
					cell = cells.getCell("AL" + Integer.toString(i));
					cell.setValue(Brand);
					cell = cells.getCell("AM" + Integer.toString(i));
					cell.setValue(Catogery);
					cell = cells.getCell("AN" + Integer.toString(i));
					cell.setValue(NhomSP);
					cell = cells.getCell("AO" + Integer.toString(i));
					cell.setValue(Float.parseFloat(SumOutlet));
					cell = cells.getCell("AP" + Integer.toString(i));
					cell.setValue(Float.parseFloat(VOLUME));
					cell = cells.getCell("AQ" + Integer.toString(i));
					cell.setValue(rs.getFloat("soluong"));
					cell = cells.getCell("AR" + Integer.toString(i));
					cell.setValue(rs.getString("TINHTHANH"));
					cell = cells.getCell("AS" + Integer.toString(i));
					cell.setValue(rs.getString("QUANHUYEN"));
					cell = cells.getCell("AT" + Integer.toString(i));
					cell.setValue(rs.getString("PHUONG"));
					cell = cells.getCell("AU" + Integer.toString(i));
					cell.setValue(rs.getString("CusCode"));
					cell = cells.getCell("AV" + Integer.toString(i));
					cell.setValue(rs.getString("sosp"));
					cell = cells.getCell("AW" + Integer.toString(i));
					cell.setValue(rs.getString("nhomSkus"));
					cell = cells.getCell("AX" + Integer.toString(i));
					cell.setValue(rs.getString("diachikh"));
					i++;
				}
				System.out.println("so dong:" + i);
				if (rs != null)
					rs.close();
				if (db != null)
					db.shutDown();

				// create pivot
				getAn(workbook, 27);

				bfasle = true;
			} catch (Exception e)
			{
				bfasle = false;
				e.printStackTrace();
				System.out.println("Error : Inventory Report : " + e.toString());
			}
		} else
		{
			bfasle = false;
		}

	}

	private void getCellStyle(Workbook workbook, String a, Color mau, Boolean dam, int size)
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		Cells cells = worksheet.getCells();
		Style style;
		Cell cell = cells.getCell(a);
		style = cell.getStyle();
		Font font1 = new Font();
		font1.setColor(mau);
		font1.setBold(dam);
		font1.setSize(size);
		style.setFont(font1);
		cell.setStyle(style);
	}

	private void getAn(Workbook workbook, int i)
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		Cells cells = worksheet.getCells();
		for (int j = 1000; j <= i; j++)
		{
			cells.hideColumn(j);
		}

	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh-mm-ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
