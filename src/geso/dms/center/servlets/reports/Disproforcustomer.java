package geso.dms.center.servlets.reports;

import geso.dms.distributor.beans.reports.imp.Reports;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
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


public class Disproforcustomer extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	public Disproforcustomer() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		// PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		Reports report = new Reports();
		String querystring = request.getQueryString();
		Utility util = new Utility();
		String userId = util.getUserId(querystring);
		String userTen = (String) session.getAttribute("userTen");
		report.setUser(userId);
		report.setTuNgay("");
		report.setDenNgay("");
		report.setNppId("");
		report.setcttbid("");
		report.setLoi("");
		report.CreateRsNpp();
		report.CreateRsCTTB();
		report.setUserTen(userTen);
		session.setAttribute("report", report);
		session.setAttribute("util", util);
		session.setAttribute("userTen", userTen);
		session.setAttribute("userId", userId);

		String nextJSP = "/AHF/pages/Center/R_Disproforcustomer.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		OutputStream out = response.getOutputStream();
		HttpSession session = request.getSession();
		Reports obj = new Reports();
		Utility util = new Utility();
		String userTen = (String) session.getAttribute("userTen");
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		userId = (String) session.getAttribute("userId");
		obj.setUser(userId);
		
		String[] cttbid = request.getParameterValues("cttbid");
		//lay id cttb da chon ngoai giao dien
		String str3 = "";
		if(cttbid != null)
		{
			for(int i = 0; i < cttbid.length; i++)
				str3 += cttbid[i] + ",";
			if(str3.length() > 0)
				str3 = str3.substring(0, str3.length() - 1);
		}
		obj.setcttbid(str3);
		
		boolean bfasle = true;
		try {

			userTen = (String) session.getAttribute("userTen");
			if (userTen == null)
				userTen = "";
			obj.setUserTen(userTen);
			userId = (String) session.getAttribute("userId");
			if (userId == null)
				userId = "";
			obj.setUser(userId);

			String tungay = util.antiSQLInspection(request
					.getParameter("tungay"));
			if (tungay == null)
				tungay = "";
			obj.setTuNgay(tungay);

			String denngay = util.antiSQLInspection(request
					.getParameter("denngay"));
			if (denngay == null)
				denngay = "";
			obj.setDenNgay(denngay);

			String NhappId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhappid")));
			if (NhappId == null)
				NhappId = "";

			obj.setNppId(NhappId);

			session.setAttribute("userTen", userTen);
			session.setAttribute("userId", userId);

		
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=BaoCaoXuatTrungBayKhachHang.xlsm");
			

			CreatePivotTable(out, response, request, obj, bfasle, str3);
		}
		catch (Exception ex) 
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

	private void CreatePivotTable(OutputStream out, HttpServletResponse response, HttpServletRequest request, Reports obj, boolean bfasle,String strctkbid) throws IOException
	{													
		
		FileInputStream fstream = null;

		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BCTrungBayTT.xlsm");
		
		//File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\BCTrungBayTT.xlsm");
		//FileInputStream fstream = new FileInputStream(f) ;
		Workbook workbook = new Workbook();	
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		
		CreateStaticHeader(workbook, obj.getTuNgay(), obj.getDenNgay(), obj.getTenUser());
		
		bfasle = CreateStaticData(workbook, obj, bfasle,strctkbid);
		System.out.println("Loi:..."+bfasle);
		if (bfasle == false) 
		{
			String loi = "Không có báo cáo theo điều kiện đã chọn!";
			HttpSession session = request.getSession();
			obj.CreateRsNpp();
			obj.CreateRsCTTB();
			obj.setLoi(loi);
			session.setAttribute("report", obj);
			String nextJSP = "/AHF/pages/Center/R_Disproforcustomer.jsp";
			response.sendRedirect(nextJSP);
			return;
		} 
		
		workbook.save(out);
		fstream.close();
	}

	private void CreateStaticHeader(Workbook workbook, String dateFrom, String dateTo, String UserName) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    worksheet.setName("Sheet1");
	    
	    Cells cells = worksheet.getCells();

		Style style;		
		
		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("A1");		
		cell.setValue("BÁO CÁO TRƯNG BÀY");

		style = cell.getStyle();

		Font font2 = new Font();
		font2.setColor(Color.RED);// mau chu
		font2.setSize(16);// size chu
		font2.setBold(true);
		style.setFont(font2);
		
		style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu
		cell.setStyle(style);

		int i=3;
		if(!dateFrom.equals("")){
			cell = cells.getCell("A2");
			getCellStyle(workbook, "A2", Color.NAVY, true, 9);
			cell.setValue("Từ ngày: " + dateFrom);
			
		}
		if(!dateTo .equals("")){
			cell = cells.getCell("A3");
			getCellStyle(workbook, "A3", Color.NAVY, true, 9);
			cell.setValue("Đến ngày: " + dateTo);
		}
		

		cell = cells.getCell("A4");
		getCellStyle(workbook, "A4", Color.NAVY, true, 9);
		cell.setValue("Ngày tạo báo cáo: " + this.getDateTime());

		cell = cells.getCell("A5");
		getCellStyle(workbook, "A5", Color.NAVY, true, 9);
		cell.setValue("Được tạo bởi:  " + UserName);
	
		cell = cells.getCell("AA1");  cell.setValue("ChiNhanh");
		cell = cells.getCell("AB1");  cell.setValue("Vung");
		cell = cells.getCell("AC1");  cell.setValue("Sitecode");
		cell = cells.getCell("AD1");  cell.setValue("NhaPhanPhoi");
		cell = cells.getCell("AE1");  cell.setValue("MaChuongTrinh");
		cell = cells.getCell("AF1");  cell.setValue("DienGiai");
		cell = cells.getCell("AG1");  cell.setValue("MaKHSALESUP");
		cell = cells.getCell("AH1");  cell.setValue("MaKH");
		cell = cells.getCell("AI1");  cell.setValue("TenKhachHang");
		cell = cells.getCell("AJ1");  cell.setValue("DiaChi");
		cell = cells.getCell("AK1");  cell.setValue("TinhThanh");
		cell = cells.getCell("AL1");  cell.setValue("QuanHuyen");
		cell = cells.getCell("AM1");  cell.setValue("DienThoai");
		cell = cells.getCell("AN1");  cell.setValue("LanTra");
		cell = cells.getCell("AO1");  cell.setValue("DangKy");
		cell = cells.getCell("AP1");  cell.setValue("DeNghi");
		cell = cells.getCell("AQ1");  cell.setValue("Duyet");
		cell = cells.getCell("AR1");  cell.setValue("DoanhSo");
		cell = cells.getCell("AS1");  cell.setValue("XuatDat");
		
		//worksheet.setName("BAO CAO TRUNG BAY(TT)");
	}

	private boolean CreateStaticData(Workbook workbook, Reports obj, boolean bfasle,String strctkbid) 
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();
		dbutils db = new dbutils();

		Utility Ult = new Utility();

		String sql = "";
		if(obj.getTuNgay().trim().length() > 0 && obj.getTuNgay().trim().length() > 0)
		{
			sql = "\n SELECT  KV.TEN AS AREA ,V.TEN AS REGION ,TT.TEN AS TINHTHANH ,QH.TEN AS QUANHUYEN ,NPP.SITECODE,  "+
			"\n  NPP.TEN AS DISTRIBUTOR,CB.SCHEME AS   PROGRAMID,CB.DIENGIAI,KH.PK_SEQ AS CUSTOMERKEY,   "+
			"\n  CAST(KH.SMARTID as nvarchar(10))+'_'+NPP.SITECODE  AS SMARTID,  KH.TEN AS CUSTOMERNAME,KH.DIACHI AS ADDRESS,  "+
			"\n  KH.DIENTHOAI, ISNULL(DNTB.LANTHANHTOAN,1) AS PAYTIME,ISNULL(TBKH.DANGKY,0) AS ALLOCATION   , 0 AS xuatdat ,  "+
			"\n  ISNULL(DNTBKH.XUATDENGHI,0) AS REQUEST_PAY,ISNULL(DNTBKH.XUATDUYET,0) AS APPROVAL, DOANHSO.doanhso  "+
			"\n  FROM CTTRUNGBAY  CB    "+
			"\n  inner join CTTB_NPP PB ON PB.CTTB_FK = CB.PK_SEQ " +
			"\n  inner join DANGKYTRUNGBAY DKTB ON DKTB.CTTRUNGBAY_FK = CB.PK_SEQ  " +
			"\n  inner join DKTRUNGBAY_KHACHHANG TBKH ON TBKH.DKTRUNGBAY_FK = DKTB.PK_SEQ     " +
			"\n  inner join NHAPHANPHOI NPP ON pb.npp_fk = npp.pk_seq " +
			"\n  LEFT JOIN KHUVUC KV ON KV.PK_SEQ = NPP.KHUVUC_FK "+   
			"\n  LEFT JOIN VUNG V ON V.PK_SEQ = KV.VUNG_FK  "+
			"\n  LEFT JOIN DENGHITRATRUNGBAY DNTB ON DNTB.CTTRUNGBAY_FK = DKTB.CTTRUNGBAY_FK AND DNTB.NPP_FK = DKTB.NPP_FK "+  
			"\n  LEFT JOIN DENGHITRATB_KHACHHANG DNTBKH ON DNTBKH.DENGHITRATB_FK = DNTB.PK_SEQ "+   
			"\n  AND DNTBKH.KHACHHANG_FK =TBKH.KHACHHANG_FK "+
			"\n  LEFT JOIN KHACHHANG KH ON KH.PK_SEQ = TBKH.KHACHHANG_FK " +
			"\n  LEFT JOIN TINHTHANH TT ON TT.PK_SEQ=KH.TINHTHANH_FK    "+
			"\n  LEFT JOIN QUANHUYEN QH ON QH.PK_SEQ=KH.QUANHUYEN_FK  " +
			"\n  INNER JOIN  " +
			"\n ( " +
			"\n  select a.khachhang_fk, d.cttrungbay_fk, sum(b.soluong * b.giamua - isnull(b.chietkhau, '0') ) as doanhso " +
			"\n  from donhang a inner join donhang_sanpham b on a.pk_seq = b.donhang_fk " +
			"\n  inner join nhomsptrungbay_sanpham c on c.sanpham_fk = b.sanpham_fk " +
			"\n  	inner join cttb_nhomsptrungbay d on d.nhomsptrungbay_fk = c.nhomsptrungbay_fk " +
			"\n  where a.trangthai = '1' and a.ngaynhap >= '" + obj.getTuNgay() + "' and a.ngaynhap <= '" + obj.getDenNgay() + "' " +
			"\n  group by a.khachhang_fk, d.cttrungbay_fk " +
			"\n )  " +
			"\n DOANHSO on KH.pk_seq = DOANHSO.khachhang_fk and CB.pk_seq = DOANHSO.cttrungbay_fk  " +
			"\n  where 1=1    and ( TBKH.dangky >0 or DNTBKH.xuatdangky >0) ";
		}
		else
		{
			sql = "\n SELECT  KV.TEN AS AREA ,V.TEN AS REGION ,TT.TEN AS TINHTHANH ,QH.TEN AS QUANHUYEN ,NPP.SITECODE,  "+
			"\n  NPP.TEN AS DISTRIBUTOR,CB.SCHEME AS   PROGRAMID,CB.DIENGIAI,KH.SMARTID AS CUSTOMERKEY,   "+
			"\n  CAST(KH.SMARTID as nvarchar(10))+'_'+NPP.SITECODE  AS SMARTID,  KH.TEN AS CUSTOMERNAME,KH.DIACHI AS ADDRESS,  "+
			"\n  KH.DIENTHOAI, ISNULL(DNTB.LANTHANHTOAN,1) AS PAYTIME,ISNULL(TBKH.DANGKY,0) AS ALLOCATION   , 0 AS xuatdat , "+
			"\n  ISNULL(DNTBKH.XUATDENGHI,0) AS REQUEST_PAY,ISNULL(DNTBKH.XUATDUYET,0) AS APPROVAL ,'0' as doanhso  "+
			"\n  FROM CTTRUNGBAY  CB    "+
			"\n  inner join CTTB_NPP PB ON PB.CTTB_FK = CB.PK_SEQ " +
			"\n  inner join DANGKYTRUNGBAY DKTB ON DKTB.CTTRUNGBAY_FK = CB.PK_SEQ  " +
			"\n  inner join DKTRUNGBAY_KHACHHANG TBKH ON TBKH.DKTRUNGBAY_FK = DKTB.PK_SEQ     " +
			"\n  inner join NHAPHANPHOI NPP ON pb.npp_fk = npp.pk_seq " +
			"\n  LEFT JOIN KHUVUC KV ON KV.PK_SEQ = NPP.KHUVUC_FK "+   
			"\n  LEFT JOIN VUNG V ON V.PK_SEQ = KV.VUNG_FK  "+
			"\n  LEFT JOIN DENGHITRATRUNGBAY DNTB ON DNTB.CTTRUNGBAY_FK = DKTB.CTTRUNGBAY_FK AND DNTB.NPP_FK = DKTB.NPP_FK "+  
			"\n  LEFT JOIN DENGHITRATB_KHACHHANG DNTBKH ON DNTBKH.DENGHITRATB_FK = DNTB.PK_SEQ "+   
			"\n  AND DNTBKH.KHACHHANG_FK =TBKH.KHACHHANG_FK "+
			"\n  LEFT JOIN KHACHHANG KH ON KH.PK_SEQ = TBKH.KHACHHANG_FK " +
			"\n  LEFT JOIN TINHTHANH TT ON TT.PK_SEQ=KH.TINHTHANH_FK    "+
			"\n  LEFT JOIN QUANHUYEN QH ON QH.PK_SEQ=KH.QUANHUYEN_FK  " +
			"\n  where 1=1   and ( TBKH.dangky >0 or DNTBKH.xuatdangky >0)  ";
		}

		if(!obj.getTuNgay().equals("")){
			sql += "\n and cb.ngaytrungbay>='"+ obj.getTuNgay()+"'";
		}
		if(!obj.getDenNgay().equals("")){

			sql += "\n and cb.ngaytrungbay<='"+ obj.getDenNgay()+"'";
		} 

		sql += "\n and pb.npp_fk in " + Ult.quyen_npp(obj.getUser());

		if(!strctkbid.equals("")){
			sql += "\n and cb.pk_seq in ("+strctkbid+")";
		}

		if (obj.getNppId() != "") {
			sql +=  "\n and npp.pk_seq=" + obj.getNppId();
		}

		sql += "\n order by DNTB.LANTHANHTOAN" ;
		System.out.println("Query BC: "+sql);
		ResultSet rs = db.get(sql);

		int i = 2;
		if (rs != null) 
		{
			try 
			{
				for(int j = 0; j < 18; j++)
					cells.setColumnWidth(i, 15.0f);

				Cell cell = null;
				int dl=0;
				while (rs.next())
				{
					dl++;
					String Area = rs.getString("Area");
					String Region = rs.getString("Region");
					String Distributor = rs.getString("Distributor");
					String Program_ID = rs.getString("programid");
					String Program_Des = rs.getString("diengiai");

					String CustomerKey = rs.getString("CustomerKey");
					String CustomerName = rs.getString("CustomerName");
					String Address = rs.getString("Address");
					String PayTime = rs.getString("PayTime");

					double Allocation = rs.getFloat("Allocation");
					double Request_pay = rs.getFloat("Request_pay");
					double Approval_pay = rs.getFloat("Approval");
					String Cus_Smartid = rs.getString("smartid");

					String province = rs.getString("tinhthanh");
					String town = rs.getString("quanhuyen");
					String sitecode = rs.getString("sitecode");
					String tel = rs.getString("dienthoai");
					double doanhso = rs.getDouble("doanhso");

					cell = cells.getCell("AA" + Integer.toString(i));  cell.setValue(Region); //0
					cell = cells.getCell("AB" + Integer.toString(i));  cell.setValue(Area); //1
					cell = cells.getCell("AC" + Integer.toString(i));  cell.setValue(sitecode);//2
					cell = cells.getCell("AD" + Integer.toString(i));  cell.setValue(Distributor);//3
					cell = cells.getCell("AE" + Integer.toString(i));  cell.setValue(Program_ID);//4
					cell = cells.getCell("AF" + Integer.toString(i));  cell.setValue(Program_Des);//5
					cell = cells.getCell("AG" + Integer.toString(i));  cell.setValue(CustomerKey);//6
					cell = cells.getCell("AH" + Integer.toString(i));  cell.setValue(Cus_Smartid);//7
					cell = cells.getCell("AI" + Integer.toString(i));  cell.setValue(CustomerName);//8					
					cell = cells.getCell("AJ" + Integer.toString(i));  cell.setValue(Address);//9					
					cell = cells.getCell("AK" + Integer.toString(i));  cell.setValue(province);//10					
					cell = cells.getCell("AL" + Integer.toString(i));  cell.setValue(town); //11
					cell = cells.getCell("AM" + Integer.toString(i));  cell.setValue(tel);//12					
					cell = cells.getCell("AN" + Integer.toString(i));  cell.setValue(PayTime);//13				
					cell = cells.getCell("AO" + Integer.toString(i));  cell.setValue(Allocation); //14					
					cell = cells.getCell("AP" + Integer.toString(i));  cell.setValue(Request_pay);//15					
					cell = cells.getCell("AQ" + Integer.toString(i));  cell.setValue(Approval_pay); //16					
					cell = cells.getCell("AR" + Integer.toString(i));  cell.setValue(doanhso); //17
					cell = cells.getCell("AS" + Integer.toString(i));  cell.setValue(rs.getDouble("xuatdat"));
					i++;
				}

				if (rs != null)
					rs.close();
				if(db!=null){
					db.shutDown();
				}
				if(dl==0)
					return false;
			} catch (Exception e) {

				e.printStackTrace();
				bfasle = false;
				return false;
			}
		} 
		else {
			bfasle = false;
			return false;
		}

		/*	//if(i==2)
			//return false;

		System.out.println("Oke Da TOi day :" +i);
		if(i==2){
			return false;
		}else{
					// xong buoc dua du lieu vao exel

					// create pivot
					getAn(workbook, 70);
					PivotTables pivotTables = worksheet.getPivotTables();

					// Adding a PivotTable to the worksheet
					String pos = Integer.toString(i - 1); // pos la dong cuoi cung ,A12 la
														// toa do dau cua banng du lieu,
														// Q pos la dong cuoi
					int index = pivotTables.add("=AA1:AS" + pos, "A12", "PivotTableDemo");
					System.out.println("index:" + index);
					// Accessing the instance of the newly added PivotTable
					PivotTable pivotTable = pivotTables.get(index);// truyen index

					pivotTable.setRowGrand(false);
					pivotTable.setColumnGrand(false);
					pivotTable.setAutoFormat(true);
					// Setting the PivotTable autoformat type.
					// pivotTable.setAutoFormatType(PivotTableAutoFormatType.REPORT6);
						pivotTable.addFieldToArea(PivotFieldType.ROW, 0);
						pivotTable.getRowFields().get(0).setAutoSubtotals(false);
						pivotTable.addFieldToArea(PivotFieldType.ROW, 1);
						pivotTable.getRowFields().get(1).setAutoSubtotals(true);
						pivotTable.addFieldToArea(PivotFieldType.ROW, 2);
						pivotTable.getRowFields().get(2).setAutoSubtotals(false);
						pivotTable.addFieldToArea(PivotFieldType.ROW, 3);
						pivotTable.getRowFields().get(3).setAutoSubtotals(false);
						pivotTable.addFieldToArea(PivotFieldType.ROW, 4);
						pivotTable.getRowFields().get(4).setAutoSubtotals(false);
						pivotTable.addFieldToArea(PivotFieldType.ROW, 5);
						pivotTable.getRowFields().get(5).setAutoSubtotals(false);
						pivotTable.addFieldToArea(PivotFieldType.ROW, 6);
						pivotTable.getRowFields().get(6).setAutoSubtotals(false);
						pivotTable.addFieldToArea(PivotFieldType.ROW, 7);
						pivotTable.getRowFields().get(7).setAutoSubtotals(false);
						pivotTable.addFieldToArea(PivotFieldType.ROW, 8);
						pivotTable.getRowFields().get(8).setAutoSubtotals(false);
						pivotTable.addFieldToArea(PivotFieldType.ROW, 9);
						pivotTable.getRowFields().get(9).setAutoSubtotals(false);
						pivotTable.addFieldToArea(PivotFieldType.ROW, 10);
						pivotTable.getRowFields().get(10).setAutoSubtotals(false);
						pivotTable.addFieldToArea(PivotFieldType.ROW, 11);
						pivotTable.getRowFields().get(11).setAutoSubtotals(false);
						pivotTable.addFieldToArea(PivotFieldType.ROW, 12);
						pivotTable.getRowFields().get(12).setAutoSubtotals(false);
						pivotTable.addFieldToArea(PivotFieldType.ROW, 13);


						pivotTable.addFieldToArea(PivotFieldType.DATA, 14);
						pivotTable.getDataFields().get(0).setDisplayName("Đăng ký");
						pivotTable.addFieldToArea(PivotFieldType.DATA, 15);
						pivotTable.getDataFields().get(1).setDisplayName("Đề nghị");
						pivotTable.addFieldToArea(PivotFieldType.DATA, 16);
						pivotTable.getDataFields().get(2).setDisplayName("Duyệt");
						pivotTable.addFieldToArea(PivotFieldType.DATA, 17);
						pivotTable.getDataFields().get(3).setDisplayName("Doanh Số");
						pivotTable.addFieldToArea(PivotFieldType.DATA, 18);
						pivotTable.getDataFields().get(4).setDisplayName("Xuất đạt");
						pivotTable.addFieldToArea(PivotFieldType.COLUMN, pivotTable.getDataField());

						worksheet.setGridlinesVisible(false);*/

		return true;
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
		//sheet.setDisplayGridlines(false);
		
		Cells cells = worksheet.getCells();
		for (int j = 24; j <= i; j++) {
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
