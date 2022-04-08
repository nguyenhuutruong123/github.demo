package geso.dms.center.servlets.report;

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


public class DailyPrimarySales extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public DailyPrimarySales() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		
		
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	/*	if( action != null && action.equals("checkReportStatus") )
		{
			PrintWriter out = response.getWriter();

			String isDone = "";
			if( session.getAttribute("PhucTran") != null )
				isDone = session.getAttribute("PhucTran").toString();
			
			System.out.println("Dang check report SALES..." + isDone);
			if( isDone.equals("1") )
				out.write("OK");
			
			return;
		}*/
		
	
		IStockintransit obj = new Stockintransit();
		String querystring = request.getQueryString();
		Utility util = new Utility();

		obj.setuserId(util.getUserId(querystring));
		obj.setuserTen((String) session.getAttribute("userTen"));

		String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
		if(view == null)
			view = "TT";
		obj.setView(view);
		obj.setdiscount("1");
		obj.setvat("1");
		obj.init();
		String nextJSP = "";
		session.setAttribute("obj", obj);

		nextJSP = "/AHF/pages/Center/DailyPrimarySales.jsp";	

		//String nextJSP = "/AHF/pages/Center/DailyPrimarySales.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		obj.setdiscount("1");
		obj.setvat("1");

		String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
		if(view == null)
			view = "TT";
		obj.setView(view);
		
		OutputStream out = response.getOutputStream();

		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		obj.setuserTen(userTen);
		Utility util = new Utility();

		
		
		obj.settungay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays"))));
		obj.setdenngay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays"))));

		obj.setuserId(userId!=null? userId:"");
		obj.setuserTen(userTen!=null? userTen:"");
		obj.setkenhId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId"))):"");

		obj.setvungId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"))):"");

		obj.setkhuvucId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId"))):"");

		obj.setgsbhId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("gsbhs")))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("gsbhs"))):"");

		obj.setnppId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"))):"");

		obj.setdvkdId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")))!= null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId"))):"");

		obj.setnhanhangId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhanhangId")))!= null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhanhangId"))):"");
		obj.setchungloaiId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chungloaiId")))!= null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chungloaiId"))):"");

		String vat = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vats")==null?"0":request.getParameter("vats")));
		if (vat.equals("1"))
			obj.setvat("1.1");
		else
			obj.setvat("1");


		String nppId ="";
		System.out.println("view : "+view);
		if(view.equals("TT")){
			nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
			if (nppId == null)
				nppId = "";
			obj.setnppId(nppId);
		}else{

			nppId=util.getIdNhapp(userId);
		}

		String dsc = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("discount")==null?"0":request.getParameter("discount")));
		if (dsc.equals("1"))
			obj.setdiscount("1");
		else
			obj.setdiscount("0");

		String promotion = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("promotion")==null?"0":request.getParameter("promotion")));
		if(promotion == null)
			promotion = "0";
		obj.setpromotion(promotion);

		String[] fieldsHien = request.getParameterValues("fieldsHien");
		obj.setFieldShow(fieldsHien);
		String nextJSP = "";
		String action = (String) util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")));
		nextJSP = "/AHF/pages/Center/DailyPrimarySales.jsp";			

		System.out.println("Action la: " + action);
		if (action.equals("Taomoi")) 
		{			
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=DonDatHang.xlsm");

			String query = setQuery(obj); 

			try 
			{
				if(!CreatePivotTable(out, response, request, obj, query))
				{
					response.setContentType("text/html");
					PrintWriter writer = new PrintWriter(out);
					writer.print("Xin loi khong co bao cao trong thoi gian nay");
					writer.close();
				}
				else
				{
					obj.setMsg("Tao bao cao thanh cong ");
					return;
				}
			} 
			catch (Exception e) 
			{
				obj.setMsg("Khong the tao bao cao " + e.getMessage());
			}
		}
		else
			if (action.equals("TheoDoiDDH")) 
			{			
				
				try 
				{		        	
					response.setContentType("application/xlsm");
					response.setHeader("Content-Disposition", "attachment; filename=HoaDon.xlsm");
					//FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\HoaDon.xlsm");
					//File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\HoaDon.xlsm");
					//FileInputStream fstream = new FileInputStream(f) ;
					Workbook workbook = new Workbook();

					workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
					String query=setQuery_TheoDoiDDH( obj);
					FillData_TheoDoiDDH(workbook,obj, query);
				
					workbook.save(out);
					obj.DBclose();

				} 
				catch (Exception e) 
				{
					e.printStackTrace();
					obj.setMsg("Khong the tao bao cao " + e.getMessage());
				}
				return;
			}else
				if (action.equals("HoaDonErp")) 
				{			
					String query = setQuery_TheoDoiDDH(obj);
					System.out.println("[Query]"+query);
					try 
					{		        	
						response.setContentType("application/xlsm");
						response.setHeader("Content-Disposition", "attachment; filename=HoaDon.xlsm");
						FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\HoaDonErp.xlsm");
						//File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\HoaDonErp.xlsm");
						//FileInputStream fstream = new FileInputStream(f) ;
						Workbook workbook = new Workbook();
						workbook.open(fstream);
						workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
						query=setQuery_HoaDonErp( obj);
						FillData_TheoDoiDDH(workbook,obj, query);
						workbook.save(out);
						fstream.close();

					} 
					catch (Exception e) 
					{
						e.printStackTrace();
						obj.setMsg("Khong the tao bao cao " + e.getMessage());
					}
				}


		obj.init();
		session.setAttribute("obj", obj);
		nextJSP = "/AHF/pages/Center/DailyPrimarySales.jsp";		
		response.sendRedirect(nextJSP);
		return;
	}
	
	private boolean FillData_TheoDoiDDH(Workbook workbook, IStockintransit obj, String query) throws Exception
	{	
		
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");
		Cells cells = worksheet.getCells();		

		ResultSet rs = obj.getDb().get(query);
		ResultSetMetaData rsmd;
		try
		{
			rsmd = rs.getMetaData();

			int socottrongSql = rsmd.getColumnCount();// số cột trong câu query[3 cột]
			int countRow = 0;// dòng đầu tiên dữ liệu
			int column =0;// cột đầu tiên dữ liệu
			Cell cell = cells.getCell("A2");
			cell.setValue("Ngày tạo: "+getDateTime());
			cell = cells.getCell("A3");
			cell.setValue("Người tạo: "+obj.getuserTen());
			cell = cells.getCell("A4");
			cell.setValue("Từ ngày: "+obj.gettungay());
			
			cell = cells.getCell("A5");
			cell.setValue("Đến ngày: "+obj.getdenngay());
			System.out.println("b1");
			
			//Vẽ header
			countRow = 7; 
			for( int i =1 ; i <=socottrongSql ; i ++  )
			{
				cell = cells.getCell(countRow,i-1 + column);cell.setValue(rsmd.getColumnName(i));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
			}// Vẽ ra  ->  Mã NV |  Đại diện kinh doanh |   Doanh Số    

			countRow ++;
			System.out.println("b2");
			// đỗ dữ liệu
			while(rs.next())
			{
				for(int i =1;i <=socottrongSql ; i ++)
				{
					cell = cells.getCell(countRow,i-1 );
					if(Utility.IsNumeric(rs.getString(i)) & i!=2 & i!=4 & i!=7 )
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
			System.out.println("b3");
			if(rs!=null)rs.close();
		
		}catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Errrorr : "+ex.toString());
			throw new Exception(" abcd");
		}
		return true;
	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	private String setQuery_TheoDoiDDH(IStockintransit obj)
	{		
		String tungay = "";
		String denngay = "";
		if(obj.getFromMonth().length() <= 0) //xem theo ngay
		{
			tungay = obj.gettungay();
			denngay = obj.getdenngay();
		}
		else
		{
			tungay = obj.getFromYear()+"-"+obj.getFromMonth();
			denngay = obj.getToYear()+"-"+obj.getToMonth();
		}	

		String query=
				"\n select  data.ngayhoadon,data.manpp [MÃ NPP], isnull(data.tennpp,npp.ten) [NPP],data.sohoadon " +
				"\n		,nh.NGAYNHAN, case when nh.TRANGTHAI = 1 then N'Đã nhận' else '' end [TRẠNG THÁI] " +
				"\n 	,data.masp [MÃ SẢN PHẨM], isnull(data.tensp,sp.ten) [TÊN SẢN PHẨM],isnull(data.quycach,dvdl.DONVI) [ĐVT] " +
				"\n 	,data.gia [ĐƠN GIÁ], data.sl [SỐ LƯỢNG],DATA.ptcksanpham [CHIẾT KHẤU] " +
				"\n 	,data.thanhtien,data.tongtien ,data.ptcktong [CHIẾT KHẤU TỔNG],	data.tiencktong [TIỀN CHIẾT KHẤU] " +
				"\n 	,data.tongtttruocvat [TỔNG TIỀN TRƯỚC VAT],data.tienthue,data.tiensauvat,data.tongthanhtoan,data.scheme, kbh.diengiai as 'Kênh bán hàng',isnull(nh.ghichu, '') as 'Ghi chú',isnull(nh.ghichuimport, '') as 'Ghi chú Import' " +
				"\n from importSalein data " +
				"\n	left join NHAPHANG nh on nh.sohoadon = data.sohoadon " +
				"\n left join NHAPHANPHOI npp on npp.manpp  = data.manpp " +
				"\n inner join sanpham sp on sp.MA = data.masp " +
				"\n left join DONVIDOLUONG dvdl on dvdl.PK_SEQ = sp.DVDL_FK " +
				"\n left join kenhbanhang kbh on kbh.pk_seq = nh.kbh_fk " +
				"\n left join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK " +
				"\n left join vung v on v.pk_seq =  kv.vung_fk " + 
				"  where 1=1 ";

		if(obj.getFromMonth().length() <= 0) //xem theo ngay
		{
			if(obj.gettungay().length() > 0)
				query += " and data.ngayhoadon >= '" + tungay + "'";
			if(obj.getdenngay().length() > 0)
				query += " and data.ngayhoadon <= '" + denngay + "'";
		}
		else
		{
			if(obj.getFromMonth().length() > 0 && obj.getFromYear().length() > 0)
				query += " and substring(data.ngayhoadon,1,7) >= '" + tungay + "'";
			if(obj.getToMonth().length() > 0 && obj.getToYear().length() > 0)
				query += " and substring(data.ngayhoadon,1,7) <= '" + denngay + "'";
		}		
		if(obj.getnppId().length()>0)
		{
			query+= " and npp.pk_seq ='"+obj.getnppId()+"' ";
		}
		if(obj.getkenhId().trim().length() >0 ){
			query += " and kbh.pk_seq = '"+obj.getkenhId()+"' ";
		}
		
		Utility util = new Utility();
		/*if(obj.getView().equals("TT"))
		{
			query +=" and npp.pk_seq in " + util.quyen_npp(obj.getuserId()) + " ";
		}*/
		
		if(obj.getvungId().length()>0)
		{
			query+= " and v.pk_seq ='"+obj.getvungId()+"' ";
		}

		if(obj.getkhuvucId().length()>0)
		{
			query+= " and kv.pk_seq ='"+obj.getkhuvucId()+"' ";
		}
		
		query +=" order by  data.ngayhoadon ";
		System.out.println("lay hoa don "+query);
		
		return query;
	}
	
	private String setQuery_HoaDonErp(IStockintransit obj)
	{		
		String tungay = "";
		String denngay = "";
		if(obj.getFromMonth().length() <= 0) //xem theo ngay
		{
			tungay = obj.gettungay();
			denngay = obj.getdenngay();
		}
		else
		{
			tungay = obj.getFromYear()+"-"+obj.getFromMonth();
			denngay = obj.getToYear()+"-"+obj.getToMonth();
		}	

		String query = " select b.manpp,b.tennpp as TenNPP, a.NGAY_HD as NgayHoaDon,a.So_HD as SoHoaDon,a.KENH as KENHBH,a.SOSO,a.MASP, a.SoLuong,a.DVT as DonViTinh,"
				+ " a.GiaNet as GiaNet, a.TienTruocThue,a.thue as Thue,a.GiaGross,a.TIENSAUTHUE as TIENSAUTHUE, a.SHEME "
				+ " from apps.tbl_hoadon a inner join apps.V_KHACHHANGERP b on a.manpp = b.manpp where 1 = 1 ";
		
		if(obj.getFromMonth().length() <= 0) //xem theo ngay
		{
			if(obj.gettungay().length() > 0)
				query += " and to_char(created_date, 'yyyy-mm-dd') >= '" + tungay + "'";
			if(obj.getdenngay().length() > 0)
				query += " and to_char(created_date, 'yyyy-mm-dd') <= '" + denngay + "'";
		}
		else
		{
			if(obj.getFromMonth().length() > 0 && obj.getFromYear().length() > 0)
				query += " and to_char(created_date, 'yyyy-mm-dd')  >= '" + tungay + "'";
			if(obj.getToMonth().length() > 0 && obj.getToYear().length() > 0)
				query += " and to_char(created_date, 'yyyy-mm-dd') <= '" + denngay + "'";
		}		
		
		System.out.println("lay hoa don erp "+query);
		
		return query;
	}

	public String setQuery(IStockintransit obj)
	{
		String query = "";
		String tungay = "";
		String denngay = "";
		if(obj.getFromMonth().length() <= 0) //xem theo ngay
		{
			tungay = obj.gettungay();
			denngay = obj.getdenngay();
		}
		else
		{
			tungay = obj.getFromYear()+"-"+obj.getFromMonth();
			denngay = obj.getToYear()+"-"+obj.getToMonth();
		}	 

		String condition="";

		if(obj.gettungay().length()>0)
		{
			condition+= " and a.NgayDonHang >= '"+obj.gettungay()+"' ";
		}

		if(obj.getdenngay().length()>0)
		{
			condition+= " and a.NgayDonHang <= '"+obj.getdenngay()+"' ";
		}
		if(obj.getnppId().length()>0)
		{
			condition+= " and a.NPP_FK ='"+obj.getnppId()+"' ";
		}
		if(obj.getkenhId().length()>0)
		{
			condition+= " and a.KBH_Fk ='"+obj.getkenhId()+"' ";
		}

		if(obj.getkhoId().length()>0)
		{
			condition+= " and a.Kho_FK ='"+obj.getkhoId()+"' ";
		}


		if(obj.getvungId().length()>0)
		{
			condition+= " and k.pk_Seq ='"+obj.getvungId()+"' ";
		}

		if(obj.getkhuvucId().length()>0)
		{
			condition+= " and i.pk_Seq ='"+obj.getkhuvucId()+"' ";
		}
		String dksp = "";
		if(obj.getnhanhangId().length()  >0 )
		{
			dksp += " and c.nhanhang_fk = '"+obj.getnhanhangId()+"' ";
		}

		if(obj.getchungloaiId().length()  >0 )
		{
			dksp += " and c.chungloai_fk = '"+obj.getchungloaiId()+"' ";
		}

		query=
				"\n   select f.DONVIKINHDOANH,g.TEN as KenhBanHang,k.TEN as Vung,i.TEN as KhuVuc,h.TEN as KhoDat,e.MA as MaNhaPhanPhoi,e.TEN as NhaPhanPhoi,   "+  
						"\n   a.PK_SEQ as SoDonHang,a.TRANGTHAI,a.NgayDeNghi,a.NgayDonHang,a.ghichu,a.CongNo,a.BVAT as TienTruocThue,a.AVAT as TienSauThue,   "+  
						"\n   	a.Vat as TienVat,a.ChietKhau,   "+  
						"\n   	' ' as MaSanPham,' ' as SanPham,' ' as DonViTinh,0 as SoLuongDat,0 as SoLuongDuyet,   "+  
						"\n   		0 as GiaGoc,0 as GiaSauCK ,0 as GiaTriKM ,' ' AS SCHEME,' ' as CTKM,' '  NgayGiaoHang,' ' as GhiChuSanPham,N'1.Total' as Loai ,a.Note ,a.loaidonhang  "+  
						"\n   from ERP_DONDATHANG a   inner join ERP_DONDATHANG_SANPHAM b   "+  
						"\n   on b.dondathang_fk=a.PK_SEQ   "+  
						"\n   inner join SANPHAM c on c.PK_SEQ=b.sanpham_fk   "+ dksp+ 
						"\n   	inner join NHAPHANPHOI e on e.PK_SEQ=a.NPP_FK   "+  
						"\n   	inner join DONVIKINHDOANH f on f.PK_SEQ=a.DVKD_FK   "+  
						"\n   	inner join KENHBANHANG g on g.PK_SEQ=a.KBH_Fk   "+  
						"\n   	inner join ERP_KHOTT h on h.PK_SEQ=a.Kho_FK   "+  
						"\n   	inner join KHUVUC i on i.PK_SEQ=e.KHUVUC_FK   "+  
						"\n   	inner join VUNG k on k.PK_SEQ=i.VUNG_FK   " +
						"\n   where 1=1 "+condition+ "     "+  
						"\n      "+  
						"\n   union all   "+  
						"\n      "+  
						"\n   select f.DONVIKINHDOANH,g.TEN as KenhBanHang,k.TEN as Vung,i.TEN as KhuVuc,h.TEN as KhoDat,e.MA as MaNhaPhanPhoi,e.TEN as NhaPhanPhoi,   "+  
						"\n   	a.PK_SEQ as SoDonHang,a.TRANGTHAI,a.NgayDeNghi,a.NgayDonHang,a.ghichu,0 AS CongNo,0 as TienTruocThue,0 as TienSauThue,   "+  
						"\n   	0 as TienVat,0 AS ChietKhau,   "+  
						"\n   	C.MA as MaSanPham,c.TEN as SanPham,d.DONVI as DonViTinh,b.soluong as SoLuongDat,b.soluongttduyet as SoLuongDuyet,   "+  
						"\n   	B.DonGiaGoc as GiaGoc,Round((b.dongia/1.1)-0.5,0) as GiaSauCK , 0 as GiaTriKM ,' ' AS SCHEME,' ' as CTKM,b.NgayGiaoHang,b.ghichu as GhiChuSanPham,N'1.1 Sản phẩm' AS Loai ,a.Note ,a.loaidonhang  "+  
						"\n   from ERP_DONDATHANG a inner join ERP_DONDATHANG_SANPHAM b   "+  
						"\n   on b.dondathang_fk=a.PK_SEQ   "+  
						"\n   inner join SANPHAM c on c.PK_SEQ=b.sanpham_fk   "+ dksp+ 
						"\n   inner join DONVIDOLUONG d on d.PK_SEQ=b.dvdl_fk   "+  
						"\n   inner join NHAPHANPHOI e on e.PK_SEQ=a.NPP_FK   "+  
						"\n   inner join DONVIKINHDOANH f on f.PK_SEQ=a.DVKD_FK   "+  
						"\n   inner join KENHBANHANG g on g.PK_SEQ=a.KBH_FK   "+  
						"\n   inner join ERP_KHOTT h on h.PK_SEQ=a.Kho_FK   "+  
						"\n   inner join KHUVUC i on i.PK_SEQ=e.KHUVUC_FK   "+  
						"\n   inner join VUNG k on k.PK_SEQ=i.VUNG_FK   "+  
						"\n   where 1=1 "+condition+ "     "+  
						"\n     "+  
						"\n   UNION ALL   "+  
						"\n      "+  
						"\n      "+  
						"\n   select f.DONVIKINHDOANH,g.TEN as KenhBanHang,k.TEN as Vung,i.TEN as KhuVuc,h.TEN as KhoDat,e.MA as MaNhaPhanPhoi,e.TEN as NhaPhanPhoi,   "+  
						"\n   	a.PK_SEQ as SoDonHang,a.TRANGTHAI,a.NgayDeNghi,a.NgayDonHang,a.ghichu,0 AS CongNo,0 as TienTruocThue,0 as TienSauThue,   "+  
						"\n   	0 as TienVat,0 AS ChietKhau,   "+  
						"\n   	ISNULL(	C.MA,' ') as MaSanPham,  ISNULL(	C.TEN,' ') as SanPham, ISNULL(d.DONVI,' ')  as DonViTinh,b.soluong as SoLuongDat,b.SOLUONG as SoLuongDuyet,   "+  
						"\n   	0 as GiaGoc,0 as GiaSauCK,b.TONGGIATRI as GiaTriKM ,l.SCHEME as SCHEME,L.DIENGIAI AS CTKM,' ' AS NgayGiaoHang,' ' as GhiChuSanPham,N'1.2 Khuyến mại' AS Loai ,a.Note ,a.loaidonhang   "+  
						"\n   from ERP_DONDATHANG a inner join ERP_DONDATHANG_CTKM_TRAKM b   "+  
						"\n   on b.DONDATHANGID=a.PK_SEQ   "+  
						"\n   left join SANPHAM c on c.MA=b.SPMA   "+  dksp+ 
						"\n   left join DONVIDOLUONG d on d.PK_SEQ=C.DVDL_FK   "+  
						"\n   inner join NHAPHANPHOI e on e.PK_SEQ=a.NPP_FK   "+  
						"\n   inner join DONVIKINHDOANH f on f.PK_SEQ=a.DVKD_FK   "+  
						"\n   inner join KENHBANHANG g on g.PK_SEQ=a.KBH_FK   "+  
						"\n   inner join ERP_KHOTT h on h.PK_SEQ=a.Kho_FK   "+  
						"\n   inner join KHUVUC i on i.PK_SEQ=e.KHUVUC_FK   "+  
						"\n   inner join VUNG k on k.PK_SEQ=i.VUNG_FK   "+  
						"\n   inner join CTKHUYENMAI l on l.PK_SEQ=b.CTKMID   "+  
						"\n   where 1=1 "+condition+ "     "+  
						"\n    ORDER BY SoDonHang ";

		System.out.println("1.Query Mua hang hang ngay: " + query);
		return query;   
	}

	private boolean CreatePivotTable(OutputStream out, HttpServletResponse response, HttpServletRequest request, IStockintransit obj, String query) throws Exception 
	{
		request.getSession().setAttribute("PhucTran", "0");
		
		boolean isFillData = false;
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();

		if(obj.getFromMonth().length() <= 0) //Xem theo Ngay
		{			 
			String chuoi = getServletContext().getInitParameter("path") + "\\DailyPrimarySales.xlsm";
			fstream = new FileInputStream(chuoi);
			//File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\DailyPrimarySales.xlsm");
			//fstream = new FileInputStream(f) ;
		}
		else
		{
			String chuoi = getServletContext().getInitParameter("path") + "\\DailyPrimarySales_Thang.xlsm";
			fstream = new FileInputStream(chuoi);
			//File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\DailyPrimarySales_Thang.xlsm");
			//fstream = new FileInputStream(f) ;
		}

		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		CreateHeader(workbook,obj);
		isFillData = FillData(workbook, obj, query);

		if (!isFillData)
		{
			if(fstream != null)
				fstream.close();
			return false;
		}

		request.getSession().setAttribute("PhucTran", "1");
		
		String isDone = "";
		if( request.getSession().getAttribute("PhucTran") != null )
			isDone = request.getSession().getAttribute("PhucTran").toString();
		
		System.out.println("::: LAY BAO CAO XONG... " + isDone);
		
		workbook.save(out);
		fstream.close();
		return true;	
	}

	private void CreateHeader(Workbook workbook, IStockintransit obj)throws Exception
	{	
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

		String tieude = "BÁO CÁO DOANH SỐ BÁN HÀNG TT";
		if(obj.getFromMonth().length() > 0)
			tieude = "BÁO CÁO DOANH SỐ MUA HÀNG THEO THÁNG";
		ReportAPI.getCellStyle(cell,Color.RED, true, 14, tieude);

		String message = "";
		cells.setRowHeight(2, 18.0f);
		cell = cells.getCell("A2");
		ReportAPI.getCellStyle(cell, Color.RED, true, 9, message);   

		cells.setRowHeight(3, 18.0f);
		cell = cells.getCell("A4");

		if(obj.getFromMonth() == "")
			ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Từ ngày : " + obj.gettungay() + "" );
		else
			ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Từ tháng : " + obj.getFromMonth() + "" );

		cells.setRowHeight(3, 18.0f);
		cell = cells.getCell("B4"); 
		if(obj.getFromMonth() == "")
			ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Đến ngày : " + obj.getdenngay() + "" );
		else
			ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Đến tháng : " + obj.getToMonth() + "" );

		cells.setRowHeight(4, 18.0f);
		cell = cells.getCell("A5");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));

		cells.setRowHeight(5, 18.0f);
		cell = cells.getCell("A6");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getuserTen());
		int index = 1;
		int	indexCol = 105;
		cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);			cell.setValue("DonViKinhDoanh");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);			cell.setValue("KenhBanHang");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);			cell.setValue("Vung");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);			cell.setValue("KhuVuc");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);			cell.setValue("KhoDat");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);			cell.setValue("MaNhaPhanPhoi");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);			cell.setValue("NhaPhanPhoi");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);			cell.setValue("SoDonHang");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);			cell.setValue("TrangThai");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);			cell.setValue("NgayDeNghi");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);			cell.setValue("NgayDonHang");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);			cell.setValue("GhiChu");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);		cell.setValue("LoaiDonHang");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);		cell.setValue("CongNo");
		cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);		cell.setValue("TienTruocThue");
		cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);		cell.setValue("TienSauThue");
		cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);		cell.setValue("TienVat");
		cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);		cell.setValue("ChietKhau");

		cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);		cell.setValue("MaSanPham");
		cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);		cell.setValue("SanPham");
		cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);		cell.setValue("DonViTinh");
		cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);		cell.setValue("SoLuongDat");
		cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);		cell.setValue("SoLuongDuyet");
		cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);		cell.setValue("GiaGoc");
		cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);		cell.setValue("GiaSauCK");

		cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);		cell.setValue("SCHEME");
		cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);		cell.setValue("CTKM");
		cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);		cell.setValue("NgayGiaoHang");
		cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);		cell.setValue("GhiChuSanPham");
		cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);		cell.setValue("Loai");




	}

	private boolean FillData(Workbook workbook, IStockintransit obj, String query) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		Cells cells = worksheet.getCells();	

		for(int i = 0;i < 7; ++i)
		{
			cells.setColumnWidth(i, 15.0f);
			if(i == 4)
				cells.setColumnWidth(i, 30.0f);
		}	

		ResultSet rs = db.get(query);
		int index = 2;

		if (rs != null) 
		{
			try 
			{
				Cell cell = null;
				while (rs.next())
				{					

					int	indexCol = 105;
					cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);cell.setValue(rs.getString("DonViKinhDoanh"));	
					cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);		cell.setValue(rs.getString("KenhBanHang"));
					cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);	cell.setValue(rs.getString("Vung"));
					cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);		cell.setValue(rs.getString("KhuVuc"));
					cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);		cell.setValue(rs.getString("KhoDat"));
					cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);	cell.setValue(rs.getString("MaNhaPhanPhoi"));
					cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);	cell.setValue(rs.getString("NhaPhanPhoi"));


					cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);	cell.setValue(rs.getString("SoDonHang"));
					String trangthai=rs.getString("TrangThai");
					if(trangthai.equals("0"))
						trangthai="Chưa chốt";
					else if(trangthai.equals("1"))
						trangthai="Chờ duyệt";
					else if(trangthai.equals("2"))
					{
						trangthai="Đã duyệt";
					}
					else if(trangthai.equals("3"))
						trangthai="Đã hủy";
					else if(trangthai.equals("4"))
						trangthai="Hoàn tất";
					cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);		cell.setValue(trangthai); 


					cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);	cell.setValue(rs.getString("NgayDeNghi"));
					cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);	cell.setValue(rs.getString("NgayDonHang"));
					cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);	cell.setValue(rs.getString("ghichu"));




					String note = rs.getString("note");
					String loaidonhang=rs.getString("loaidonhang");
					if(loaidonhang.equals("0"))
					{
						loaidonhang="Đơn đặt hàng";
						if(note.contains("Convert từ duyệt đổi hàng"))
						{
							loaidonhang="Đơn đổi trả";
						}
					}
					else if(loaidonhang.equals("1"))
						loaidonhang="Đơn hàng trả KM";
					else if(loaidonhang.equals("2"))
						loaidonhang="Đơn hàng khuyến mại tích lũy";
					else if(loaidonhang.equals("3"))
						loaidonhang="Đơn hàng trưng bày";
					else if(loaidonhang.equals("4"))
						loaidonhang=" Đơn hàng khác ";

					cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);	cell.setValue(loaidonhang); 

					cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);		cell.setValue(rs.getDouble("CongNo"));
					cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);		cell.setValue(rs.getDouble("TienTruocThue"));
					cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);		cell.setValue(rs.getDouble("TienSauThue"));
					cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);		cell.setValue(rs.getDouble("TienVat"));
					cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);		cell.setValue(rs.getDouble("ChietKhau"));

					cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);		cell.setValue(rs.getString("MaSanPham"));
					cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);		cell.setValue(rs.getString("SanPham"));
					cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);		cell.setValue(rs.getString("DonViTinh"));
					cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);		cell.setValue(rs.getDouble("SoLuongDat"));
					cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);		cell.setValue(rs.getDouble("SoLuongDuyet"));
					cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);		cell.setValue(rs.getDouble("GiaGoc"));
					cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);		cell.setValue(rs.getDouble("GiaSauCK"));

					cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);		cell.setValue(rs.getString("SCHEME"));
					cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);		cell.setValue(rs.getString("CTKM"));
					cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);		cell.setValue(rs.getString("NgayGiaoHang"));
					cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);		cell.setValue(rs.getString("GhiChuSanPham"));
					cell = cells.getCell(ReportAPI.GetExcelColumnName(indexCol++) + index);		cell.setValue(rs.getString("Loai"));


					index++;					
				}
				if (rs != null)
					rs.close();

				if(db != null)
					db.shutDown();
				if(index==2)
					throw new Exception(  "Không có báo cáo trong thời gian đã chọn !"  );
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				throw new Exception(ex.getMessage());
			}
		}
		else
		{
			return false;
		}		
		return true;
	}	

}
