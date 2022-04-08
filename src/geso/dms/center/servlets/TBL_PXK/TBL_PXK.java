package geso.dms.center.servlets.TBL_PXK;

import geso.dms.center.beans.report.IBcHoaDon;
import geso.dms.center.beans.report.imp.BcHoaDon;
import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.OracleConnUtils;
import geso.dms.center.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;








import com.aspose.cells.BorderLineType;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import com.oreilly.servlet.MultipartRequest;


@WebServlet("/TBL_PXK")
public class TBL_PXK extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String UPLOAD_DIRECTORY = "c:\\java-tomcat\\upload\\excel\\";
	public TBL_PXK() 
	{
		super();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IBcHoaDon obj;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();

		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

		String loaihoadon = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaihoadon"));
		if (loaihoadon == null)
			loaihoadon = "0";

		obj = new BcHoaDon();
		obj.setUserId(userId);
		obj.setView("TT");
		String nextJSP = "";
		
		String query=
				" 	select a.NGAYCHUNGTU, a.SOCHUNGTU, a.MANHAPP, a.KENH, a.SOSO, b.sopo, c.tennpp, " +
				"	a.DVKinhDoanh, a.MaSP, a.DVT, a.Soluong, a.Sheme, a.trangthai, a.dongia, a.thanhtien " + 
				"	from apps.TBL_PHIEUXUATKHO a inner join apps.V_DONHANG b on a.soso = b.soso " +
				"	inner join apps.V_KHACHHANGERP c on a.MANHAPP = c.MANPP " +
				"	inner join apps.V_DONHANG b on a.soso=b.soso " +
				"   where 1=1 and a.loai_phieu like N'%PHIEU XUAT KHO%' " +
				"	group by a.NGAYCHUNGTU, a.SOCHUNGTU, a.MANHAPP, a.KENH, a.SOSO, b.sopo, c.tennpp, a.DVKinhDoanh, a.MaSP, a.DVT, " +
				"	a.soluong, a.Sheme, a.trangthai, a.dongia, a.thanhtien	";
		System.out.println("[init] "+query);
		obj.init(query,"NGAYCHUNGTU desc, MANHAPP");
		
		nextJSP = "/AHF/pages/Center/TBL_PXK.jsp";
		session.setAttribute("obj", obj);
		response.sendRedirect(nextJSP);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String contentType = request.getContentType();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		Utility util = new Utility();
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		HttpSession session = request.getSession();

		OutputStream out = response.getOutputStream();

		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		if (action == null)
			action = "";

		IBcHoaDon obj = new BcHoaDon();
		obj.setUserId(userId);


		String query=
				" 	select a.NGAYCHUNGTU, a.SOCHUNGTU, a.MANHAPP, a.KENH, a.SOSO, b.sopo, c.tennpp, a.dongia "+ 
				"	from apps.TBL_PHIEUXUATKHO a inner join apps.V_DONHANG b on a.soso = b.soso " +
				"	inner join apps.V_KHACHHANGERP c on a.MANHAPP = c.MANPP "+
				"	inner join apps.V_DONHANG b on a.soso=b.soso "+
				"   where 1=1 and a.loai_phieu like N'%PHIEU XUAT KHO%' ";

		obj.setView("TT");

		String tungay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays") == null ? "" : request.getParameter("Sdays"));
		obj.setTuNgay(tungay);

		String denngay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays") == null ? "" : request.getParameter("Edays"));
		obj.setDenNgay(denngay);
		
		String soId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("soso") == null ? "" : request.getParameter("soso"));
		obj.setSoId(soId);

		String LegalNumber = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sochungtu") == null ? "" : request.getParameter("sochungtu"));
		obj.setLegalNumber(LegalNumber);

		String CustomerId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("CustomerId") == null ? "" : request.getParameter("CustomerId"));
		obj.setCustomerId(CustomerId);

		String CustomerName = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("CustomerName") == null ? "" : request.getParameter("CustomerName"));
		obj.setCustomerName(CustomerName);

		/*String vungId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId") == null ? "" : request.getParameter("vungId"));
		obj.setVungId(vungId);

		String kbhId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kbhId") == null ? "" : request.getParameter("kbhId"));
		obj.setKbhId(kbhId);

		String ttId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ttId") == null ? "" : request.getParameter("ttId"));
		obj.setTtId(ttId);

		String nhomId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhomId") == null ? "" : request.getParameter("nhomId"));
		obj.setNhomId(nhomId);

		String khId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khId") == null ? "" : request.getParameter("khId"));
		obj.setKhId(khId);

		String ddkdId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdId") == null ? "" : request.getParameter("ddkdId"));
		obj.setDdkdId(ddkdId);

		String spId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("spId") == null ? "" : request.getParameter("spId"));
		obj.setSpId(spId);

		String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId") == null ? "" : request.getParameter("nppId"));
		obj.setNppId(nppId);

		String soId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("soId") == null ? "" : request.getParameter("soId"));
		obj.setSoId(soId);

		String hoadon = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("hoadon") == null ? "" : request.getParameter("hoadon"));
		obj.setSohoadon(hoadon);

		String InvoiceId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("InvoiceId") == null ? "" : request.getParameter("InvoiceId"));
		obj.setInvoiceId(InvoiceId);

		String Invoice_RevertFor = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Invoice_RevertFor") == null ? "" : request.getParameter("Invoice_RevertFor"));
		obj.setInvoice_RevertFor(Invoice_RevertFor);

		String InVoiceDate = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("InVoiceDate") == null ? "" : request.getParameter("InVoiceDate"));
		obj.setInVoiceDate(InVoiceDate);

		String InvoiceType = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("InvoiceType") == null ? "" : request.getParameter("InvoiceType"));
		obj.setInvoiceType(InvoiceType);

		String SO_Number = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("SO_Number") == null ? "" : request.getParameter("SO_Number"));
		obj.setSO_Number(SO_Number);

		String PO_Number = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("PO_Number") == null ? "" : request.getParameter("PO_Number"));
		obj.setPO_Number(PO_Number);

		if(InvoiceId.length()>0)
		{
			query+=" and InvoiceId  like '%"+InvoiceId+"%' "		;
		}

		if(Invoice_RevertFor.length()>0)
		{
			query+=" and Invoice_RevertFor  like '%"+Invoice_RevertFor+"%' "		;
		}

		if(Invoice_RevertFor.length()>0)
		{
			query+=" and Invoice_RevertFor  like '%"+Invoice_RevertFor+"%' "		;
		}

		if(SO_Number.length()>0)
		{
			query+=" and SO_Number  like '%"+SO_Number+"%' "		;
		}

		if(PO_Number.length()>0)
		{
			query+=" and PO_Number  like '%"+PO_Number+"%' "		;
		}*/

		if(LegalNumber.length()>0)
		{
			query+=" and a.SOCHUNGTU like '%"+LegalNumber+"%'";
		}

		if(CustomerId.length()>0)
		{
			query+=" and a.MANHAPP like '%"+CustomerId+"%'";
		}

		if(CustomerName.length()>0)
		{
			query+=" and c.TENNPP like '%"+CustomerName+"%'";
		}
		
		if(tungay.length()>0)
		{
			query+=" and to_char(NGAYCHUNGTU, 'yyyy-mm-dd') >='"+tungay+"'   ";
		}

		if(denngay.length()>0)
		{
			query+=" and to_char(NGAYCHUNGTU, 'yyyy-mm-dd') <='"+denngay+"'   ";
		}
		
		if(soId.length()>0)
		{
			query+=" and a.soso = '"+soId+"'";
		}
		
		query += " group by a.NGAYCHUNGTU, a.SOCHUNGTU, a.MANHAPP, a.KENH, a.SOSO, b.sopo, c.tennpp, a.dongia";
		
		String[] xoa = request.getParameterValues("xoa"); 
		String str = "";
		if (xoa != null)
		{
			for(int i = 0; i < xoa.length; i++)
				str += xoa[i] + ",";
			if(str.length() > 0)
				str = str.substring(0, str.length() - 1);
		}
		obj.setPxkXoa(str);
		
		obj.set_Action(action);

		System.out.println("___ATION " + action);
		System.out.println("___QUERY " + query);
		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
		{
			String msg= readExcel(request);
			obj.setMsg(msg);

			obj.init(query,"NGAYCHUNGTU desc, MANHAPP " );
			session.setAttribute("obj", obj);
			String nextJSP = "";
			nextJSP = "/AHF/pages/Center/TBL_PXK.jsp";
			response.sendRedirect(nextJSP);

		}else
		{
			
			if(action.equals("save"))
			{
				obj.setMsg(obj.Save());
				obj.init(query,"NGAYCHUNGTU desc, MANHAPP " );
				session.setAttribute("obj", obj);
				String nextJSP = "";
				nextJSP = "/AHF/pages/Center/TBL_PXK.jsp";
				response.sendRedirect(nextJSP);
			}else
			if(action.equals("excel2"))
			{
				//response.setContentType("application/vnd.ms-excel");
				//response.setHeader("Content-Disposition", "attachment; filename=TonKhoHienTai.xls");
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=PXKTT.xlsm");

				boolean isTrue;
				try {
					isTrue = CreatePivotTable2(out, obj, tungay, denngay);
					if(!isTrue)
					{
						PrintWriter writer = new PrintWriter(out);
						writer.write("Khong tao duoc bao cao trong thoi gian nay...!!!");
					}	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			else if(action.equals("delete"))
			{
				obj.setMsg(obj.XoaPXK());
				obj.init(query,"NGAYCHUNGTU desc, MANHAPP " );
				session.setAttribute("obj", obj);
				String nextJSP = "";
				nextJSP = "/AHF/pages/Center/TBL_PXK.jsp";
				response.sendRedirect(nextJSP);
			}
			else if (action.equals("view") || action.equals("next") || action.equals("prev"))
			{
				obj.setNxtApprSplitting(Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
				obj.setUserId(userId);
				obj.init(query,"NGAYCHUNGTU desc, MANHAPP");
				obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
				session.setAttribute("obj", obj);

				String nextJSP = "/AHF/pages/Center/TBL_PXK.jsp";
				response.sendRedirect(nextJSP);
			} 

			else
			{
				obj.init(query,"NGAYCHUNGTU desc, MANHAPP " );
				session.setAttribute("obj", obj);
				String nextJSP = "";
				nextJSP = "/AHF/pages/Center/TBL_PXK.jsp";
				response.sendRedirect(nextJSP);
			}
			
		}
		
	}
	private boolean CreateStaticData2(com.aspose.cells.Workbook workbook, IBcHoaDon obj, String tungay, String denngay) throws Exception
	{
		OracleConnUtils db = new OracleConnUtils();
		com.aspose.cells.Worksheets worksheets = workbook.getWorksheets();
		com.aspose.cells.Worksheet worksheet = worksheets.getSheet(0);
		com.aspose.cells.Cells cells = worksheet.getCells();

		geso.dms.center.util.Utility Uti_center = new geso.dms.center.util.Utility();



		String query=
				" 	select distinct a.NGAYCHUNGTU, a.SOCHUNGTU, a.MANHAPP, a.KENH, a.SOSO, b.sopo, c.TENNPP, a.DVKINHDOANH," +
				"	a.MASP, a.DVT, a.SOLUONG, a.DONGIA, a.THANHTIEN,a.SHEME ,a.TRANGTHAI   "+ 
				"	from apps.TBL_PHIEUXUATKHO a inner join apps.V_DONHANG b on a.soso = b.soso " +
				"	inner join apps.V_KHACHHANGERP c on a.MANHAPP = c.MANPP "+
				"	inner join apps.V_DONHANG b on a.soso=b.soso "+
				"   where 1=1 and a.loai_phieu like N'%PHIEU XUAT KHO%' ";
		if(tungay.length()>0)
		{
			query+=" and to_char(NGAYCHUNGTU, 'yyyy-mm-dd') >='"+tungay+"'   ";
		}

		if(denngay.length()>0)
		{
			query+=" and to_char(NGAYCHUNGTU, 'yyyy-mm-dd') <='"+denngay+"'   ";
		}

		System.out.println("BC: " + query);
		ResultSet rs = db.get(query);
		ResultSetMetaData rsmd = rs.getMetaData();
		int socottrongSql = rsmd.getColumnCount();
		int countRow = 10;
		com.aspose.cells.Cell cell = null;
		for( int i =1 ; i <=socottrongSql ; i ++  )
		{
			cell = cells.getCell(countRow,i-1 );cell.setValue(rsmd.getColumnName(i));
			cells.setColumnWidth(i, 20.0f);
			ReportAPI.setCellBackground(cell, new Color(70,130,180), BorderLineType.THIN, true, 0);	
		 
		}
		countRow ++;
		int sonvkdpda = 0;
		int stt = 0;
		while(rs.next())
		{
			boolean kt = false;
			boolean ck = true;
			stt++;
			String value = "";
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
		

	

		if(db != null) db.shutDown();
		return true;

	}
	private boolean CreatePivotTable2(OutputStream out,IBcHoaDon obj, String tungay, String denngay) throws Exception
	{   
		FileInputStream fstream = null;
		com.aspose.cells.Workbook workbook = new com.aspose.cells.Workbook();		

		
		//fstream = new FileInputStream("D:\\Project\\ICP\\Best\\WebContent\\pages\\Templates\\TonHienTaiTT.xlsm");
		//fstream = new FileInputStream("C:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\Best\\pages\\Templates\\TonHienTaiTT.xlsm");		

		
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		CreateStaticHeader2(workbook, "","", "", "");	     
		boolean isTrue = CreateStaticData2(workbook, obj, tungay, tungay);
		if(!isTrue){
			return false;
		}
		workbook.save(out);


		return true;
	}
	
	private void CreateStaticHeader2(com.aspose.cells.Workbook workbook, String dateFrom, String dateTo, String UserName, String giatinh) 
	{
		com.aspose.cells.Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		worksheet.setName("Sheet1");

		Cells cells = worksheet.getCells();

		Style style;
		Font font = new Font();
		font.setColor(Color.RED);//mau chu
		font.setSize(16);// size chu
		font.setBold(true);

		cells.setRowHeight(0, 20.0f);
		com.aspose.cells.Cell cell = cells.getCell("A1");
		style = cell.getStyle();
		style.setFont(font); 
		style.setHAlignment(TextAlignmentType.LEFT);//  

		String tieude = "Phiếu xuất kho (ERP)";
		ReportAPI.getCellStyle(cell,Color.RED, true, 14, tieude);

		cells.setRowHeight(4, 18.0f);
		cell = cells.getCell("A3");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " +getDateTime()+"");

		cells.setRowHeight(5, 18.0f);


	
	}
	private String getDateTime() 
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);	
	}
	
	private String readExcel(HttpServletRequest request)
	//private static String readExcel()
	{
		String msg="";
		dbutils db = new dbutils();

		String columName= "BillToNumber,SoHDGGTGT,DeliveryDate,Material,Price,BillingQty,NetValue,TaxAmount,Total";
		try
		{
			MultipartRequest multi;
			multi = new MultipartRequest(request, "C:\\java-tomcat\\data\\", 20000000, "UTF-8");
			Enumeration files = multi.getFileNames();
			String filename = "";
			while (files.hasMoreElements())
			{
				String name = (String) files.nextElement();
				filename = multi.getFilesystemName(name);

			}
			filename = "C:\\java-tomcat\\data\\" + ""+filename+"";

			if (filename.length() > 0)
			{
				File file = new File(filename);
				Workbook workbook;
				workbook = Workbook.getWorkbook(file);
				Sheet sheet = workbook.getSheet(0);
				int indexRow = 1;
				int sodong = sheet.getRows();
				int nCol=	sheet.getColumns();
				String query=" ";
				String sql_TABLE="";
				for (int row=indexRow; row < sodong ; row++)
				{
					query ="select ";
					for (int col=0; col < nCol ; col++)
					{
						String colName=getValue(sheet,col,0).trim();
						String value=getValue(sheet,col,row).trim();
						if(colName.trim().length()>0&&value.trim().length()>0 && columName.indexOf(colName)>=0)
							query+=" N'"+value+"' as "+colName+" , ";
					}
					if(query.trim().length()>7)
					{
						query =query.substring(0,query.length()-3) +"\n";
						sql_TABLE += query +" union all \n";	
					}
				}
				//System.out.println("sql_TABLE []"+sql_TABLE.substring(0,sql_TABLE.length()-13));
				sql_TABLE=sql_TABLE.substring(0,sql_TABLE.length()-13);

				query="select distinct Material from ("+sql_TABLE+") as data where Material not in (select Ma From SanPham ) and len(dbo.trim(Material))>0 ";
				ResultSet rs=db.get(query);
				while(rs.next())
				{
					msg+=" MÃ SẢN PHẨM "+rs.getString("Material")+" chưa được khai báo trên hệ thống ,vui lòng kiểm tra lại ! \n "+query;
				}
				rs.close();


				query="select distinct BillToNumber from ("+sql_TABLE+") as data where BillToNumber not in (select Ma From NhaPhanPhoi ) and len(dbo.trim(BillToNumber))>0 ";
				rs=db.get(query);
				while(rs.next())
				{
					msg+=" MÃ NHÀ PHÂN PHỐI "+rs.getString("BillToNumber")+" chưa được khai báo trên hệ thống ,vui lòng kiểm tra lại ! \n "+query;
				}
				rs.close();

				db.getConnection().setAutoCommit(false);
				query=
						"	drop table NhapHang_Import "+
								"	create table NhapHang_Import "+
								"	      ( "+
								"	      BillToNumber nvarchar(100), "+
								"	      SoldTo nvarchar(100),BillToName nvarchar(200), "+
								"	      DeliveryDate  nvarchar(200), "+
								"	      SoHDGGTGT nvarchar(200),PostingDate nvarchar(200),Material nvarchar(200), "+
								"	      MaterialNumber nvarchar(200), "+
								"	      BillingQty float,PriceVND float,NetValue float,TaxVND float,TotalVND float, "+
								"	Price float,TaxAmount float,Total float,  "+
								"	Created_DATE datetime default dbo.GetLocalDate(DEFAULT))  ";
				if(!db.update(query))
				{
					msg="Lỗi phát sinh khi Import Tồn Kho "+query;
					db.getConnection().rollback();
				}
				query=
						"	insert into NhapHang_Import(BillToNumber,SoHDGGTGT,DeliveryDate,Material,Price,BillingQty,NetValue,TaxAmount,Total)  "+
								"	select BillToNumber,SoHDGGTGT, (select DateTime from [ConvertToDate](DeliveryDate) ) as DeliveryDate ,Material,Price,BillingQty,NetValue,TaxAmount,Total  "+
								"	from  ("+sql_TABLE+") as data  ";
				if(!db.update(query))
				{
					msg="Lỗi phát sinh khi Import Tồn Kho "+query;
					db.getConnection().rollback();
				}


				query=
						"insert into nhaphang(NGAYNHAN,SOTIENBVAT,NGUOITAO,NGUOISUA,TRANGTHAI,NPP_FK,NCC_FK,VAT,SOTIENAVAT,DVKD_FK,KBH_FK,DATHANG_FK,CHUNGTU,NGAYCHUNGTU,KHO_FK,  "+
								"		      HDTAICHINH,LOAIHOADON,NGAYTAO,NGAYSUA)  "+
								"		select distinct DeliveryDate,0 as SOTIENBVAT,100368 as ntao,100368 as nsua,0,npp.PK_SEQ,100022,0 as vat,0 as avat,100068,100025,  "+
								"		            NULL,data.SoHDGGTGT,DeliveryDate,  "+
								"		      100000,data.SoHDGGTGT,0,DeliveryDate,DeliveryDate  "+
								"		from  NhapHang_Import as data inner join NHAPHANPHOI npp on npp.MA=data.billToNumber  "+
								"		where not exists (select 1 from nhaphang where HDTAICHINH=data.SoHDGGTGT)  ";

				if(!db.update(query))
				{
					msg="Lỗi phát sinh khi Import Tồn Kho "+query;
					db.getConnection().rollback();
				}

				query=
						"	insert into nhaphang_sp(NHAPHANG_FK,SANPHAM_FK,soluong,DONVI,GIANET,TIENBVAT,VAT,TIENAVAT,GIAGROSS,SCHEME) "+
								" select * from" +
								" ( " +
								"	select (select pk_Seq from NhapHang where HDTAICHINH=data.SoHDGGTGT) as NHAPHANG_FK , "+
								"	    data.Material, sum(data.BillingQty) as BillingQty ,dv.DONVI,  data.Price, sum(data.NetValue) as NetValue ,sum(data.TaxAmount) as TaxAmount ,sum(data.Total) as Total,sum(data.NetValue) as GIAGROSS ,'' as  SCHEME "+
								"	from  NhapHang_Import as data inner join sanpham sp on sp.MA=data.Material "+
								"	      inner join DONVIDOLUONG dv on dv.PK_SEQ=sp.DVDL_FK "+
								"	group by SoHDGGTGT,data.Material,data.Price,dv.DONVI ) as Nhap " +
								" where "+
								"	not exists "+
								"	(  "+
								"	select 1 from nhaphang_sp where NHAPHANG_FK =Nhap.NHAPHANG_FK "+
								"	      and SANPHAM_FK=Nhap.Material and soluong=Nhap.BillingQty and Nhap.Price=GIANET "+
								"	)  ";
				if(!db.update(query))
				{
					msg="Lỗi phát sinh khi Import Tồn Kho "+query;
					db.getConnection().rollback();
				}

				query=
						" update nh set VAT=sp.VAT,SOTIENAVAT=sp.AVAT,SOTIENBVAT=sp.BVAT "+
								"  from "+ 
								"  ( "+
								"  select SUM(b.Tienavat) as AVAT,SUM(b.VAT) as VAT,SUM(b.TIENBVAT) as BVAT,b.NHAPHANG_FK "+
								"  from nhaphang a inner join nhaphang_sp b on b.NHAPHANG_FK=a.PK_SEQ "+
								" group by b.NHAPHANG_FK  "+
								" )as sp inner join nhaphang nh on nh.PK_SEQ=sp.NHAPHANG_FK ";
				if(!db.update(query))
				{
					msg="Lỗi phát sinh khi Import Tồn Kho "+query;
					db.getConnection().rollback();
				}

				if(msg.length()>0)
				{
					return msg;
				}
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);

			}
		} catch (Exception e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return "Exception nhận hàng "+e1.getMessage();
		}
		finally
		{
			db.shutDown();
		}

		return "Đọc thành công nhập hàng";
	}

	private static String getValue(Sheet sheet, int column, int row)
	{
		Cell cell;
		cell = sheet.getCell(column, row);
		try{
			return cell.getContents();
		}catch(Exception er){
			return	"0";
		}
	}

}
