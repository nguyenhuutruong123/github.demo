package geso.dms.center.servlets.importtonkho;

import geso.dms.center.db.sql.Idbutils;
import geso.dms.center.db.sql.dbutils;
import geso.dms.distributor.util.Utility;
import geso.dms.center.beans.upload.IUpload;
import geso.dms.center.beans.upload.imp.Upload;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import com.oreilly.servlet.MultipartRequest;

@WebServlet("/ImportTonKhoSvl")
public class ImportTonKhoSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIRECTORY = "c:\\java-tomcat\\upload\\excel\\";
	PrintWriter out;
	NumberFormat formatter = new DecimalFormat("#,###,###");

	public ImportTonKhoSvl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IUpload obj = new Upload();

		String querystring = request.getQueryString();
		Utility util = new Utility();
		String userId = util.getUserId(querystring);
		obj.setUserId(userId);

		String nppId = util.getId(querystring);
		String action = util.getAction(querystring);
		System.out.println("_____" + action);
		obj.setNppId(nppId);

		String task = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("task"));
		if (task == null) task = "";
		obj.setTask(task);

		if (action.equals("excel"))
		{
			obj.setMsg(XuatFileExcel(response, obj));
		}
		else if (action.equals("delete"))
		{
			obj.setMsg(Delete(nppId));
		}
		obj.initImportRs();
		session.setAttribute("obj", obj);
		String nextJSP = "/AHF/pages/Center/ImportTonKho.jsp";
		response.sendRedirect(nextJSP);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IUpload obj = null;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String contentType = request.getContentType();
		Utility util = new Utility();
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		if (userId == null)
		{
			userId = "";
		}
		System.out.println("[UploadTonKhoSvl.doPost] userId = " + userId);

		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		if (action == null)
		{
			action = "";
		}
		System.out.println("[UploadTonKhoSvl.doPost] action = " + action);

		OutputStream out = response.getOutputStream();
		obj = new Upload();

		obj.setUserId(userId);
		
		String task = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("task"));
		if (task == null) task = "";
		System.out.println("task svl post: " + task);
		obj.setTask(task);

		String thang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thang"));
		if (thang == null)
			thang = "";
		obj.setThang(thang);

		String nam = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nam"));
		if (nam == null)
			nam = "";
		obj.setNam(nam);

		String vungId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"));
		if (vungId == null)
			vungId = "";
		obj.setVungId(vungId);


		String khuvucId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId"));
		if (khuvucId == null)
			khuvucId = "";
		obj.setKhuvucId(khuvucId);


		String ghichu = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ghichu"));
		if (ghichu == null)
			ghichu = "";
		obj.setGhichu(ghichu);


		String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
		if (nppId == null)
			nppId = "";
		obj.setNppId(nppId);

		if (action.equals("Tao moi"))
		{

			try
			{
				System.out.println("parseInt = " + ((int) Float.parseFloat("0.0")));
			} catch (Exception e)
			{
				System.out.println("parseInt = " + e.getMessage());
			}

			IUpload bean = (IUpload) new Upload("");
			bean.setUserId(userId);

			session.setAttribute("spnppStr", "");
			session.setAttribute("uploadBean", bean);

			String nextJSP = "/AHF/pages/Center/UploadTonKhoNew.jsp";
			response.sendRedirect(nextJSP);
			return;
		} else if (action.equals("excel"))
		{
			obj.setMsg(XuatFileExcel(response, obj));
		} else if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
		{




			MultipartRequest multi = new MultipartRequest(request, UPLOAD_DIRECTORY, 20000000, "UTF-8");
			userId = util.antiSQLInspection(multi.getParameter("userId"));
			obj.setUserId(userId);

			obj.setThang(multi.getParameter("thang"));
			obj.setNam(multi.getParameter("nam"));
			obj.setNppMa(multi.getParameter("nppMa"));
			obj.setNppId(multi.getParameter("nppId"));
			obj.setKhuvucId(multi.getParameter("khuvucId"));
			obj.setVungId(multi.getParameter("vungId"));
			obj.setTask(multi.getParameter("task"));
			String ngaykhoaso=multi.getParameter("ngaykhoaso");
			System.out.println("\n \n ngaykhoaso :" +multi.getParameter("nppMa"));

			obj.setNgaykhoaso(ngaykhoaso);


			Enumeration files = multi.getFileNames();
			String filename = "";
			System.out.println("__userId" + userId);
			while (files.hasMoreElements())
			{
				String name = (String) files.nextElement();
				filename = multi.getFilesystemName(name);
				System.out.println("File  " + UPLOAD_DIRECTORY + filename);
			}

			action = multi.getParameter("action");
			if (action.equals("uploadSalesIn"))
			{
				if (filename != null && filename.length() > 0)
					obj.setMsg(this.readExcelSalesIn(UPLOAD_DIRECTORY + filename, obj));
			}
			else
			{
				if (filename != null && filename.length() > 0)
					obj.setMsg(this.readExcel(UPLOAD_DIRECTORY + filename, obj));
			}

			if (obj.getMsg().trim().length() <= 0 && filename != null)
			{
				obj.setMsg("Đọc file thành công ");
			} else if (filename == null)
			{
				obj.setMsg("Vui lòng chọn file ");
			} else
			{
				///
			}
		}
		obj.initImportRs();
		session.setAttribute("userId", userId);
		session.setAttribute("obj", obj);
		String nextJSP = "/AHF/pages/Center/ImportTonKho.jsp";
	
		response.sendRedirect(nextJSP);
	}
	
	private String getNowTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	
	public static Hashtable<String, String> gethtpKenhBanHang(Idbutils db) {

		Hashtable<String,String> htbtuyen= new Hashtable<String, String>();
		String sql=" select ten,PK_SEQ FROM KENHBANHANG ";

		ResultSet rs=db.get(sql);
		try{
			while (rs.next()){
				htbtuyen.put(rs.getString("ten").trim(), rs.getString("PK_SEQ").trim());

			}
			rs.close();
		}catch(Exception er){
			System.out.println("Loi lay gethtpKenhBanHang : "+er.toString());
		}
		return htbtuyen;
	}
	
	
	private String readExcelSalesIn(String fileName, IUpload dhBean)
	{
		dbutils db = new  dbutils();

		File inputWorkbook = new File(fileName);
		jxl.Workbook w = null;

		try
		{
			String token = dhBean.getUserId() + getNowTime();
			
			db.getConnection().setAutoCommit(false);

			 Hashtable<String, String> htp_kenh =  gethtpKenhBanHang( db) ;
			
			w = jxl.Workbook.getWorkbook(inputWorkbook);

			int sosheet = w.getNumberOfSheets();

			jxl.Sheet sheet = w.getSheet(0);
			int sodong = sheet.getRows();
			int socot = sheet.getColumns();

			for (int i = 4; i < sodong; i++)
			{
				Cell[] cells = sheet.getRow(i);
				if (cells !=null){
					//if (cells.length >= 19 && cells[0] != null  )
					if (cells.length >= 21 && cells[0] != null  )
					{
						int count = 0;

						DateFormat dffrom = new SimpleDateFormat("yyyy-MM-dd");
						DateFormat dffrom2 = new SimpleDateFormat("dd/MM/yyyy");
						DateFormat dfto = new SimpleDateFormat("yyyy-MM-dd");
						
						String ngay = getValue(sheet, count ++, i).trim();
						
						if (!(isValidDate(ngay, "yyyy-MM-dd") || isValidDate(ngay, "dd/MM/yyyy"))) {
							db.getConnection().rollback();
							db.shutDown();
							return "Ngày Hóa đơn sai định dạng yyyy-MM-dd trên dòng ("+ (i + 1 ) +") ";
						}
						
						Date temp;
						temp = (ngay.matches("../../....")) ? dffrom2.parse(ngay) : dffrom.parse(ngay);
						ngay = dfto.format(temp); 
						
						String manpp = getValue(sheet,count ++, i).trim();
						String tennpp = getValue(sheet,count ++, i).trim();
						String sohoadon = getValue(sheet,count ++, i).trim();
						String masp = getValue(sheet,count ++, i).trim();
						String tensp = getValue(sheet,count ++, i).trim();
						String quycach = getValue(sheet,count ++, i).trim();
						String gia = getValue(sheet,count ++, i).trim().replace(",","");
						String sl = getValue(sheet,count ++, i).trim().replace(",","");
						String ptck = getValue(sheet,count ++, i).trim().replace(",","");
						String thanhtien = getValue(sheet,count ++, i).trim().replace(",","");
						String tongtienhang = getValue(sheet,count ++, i).trim().replace(",","");
						String ptcktong = getValue(sheet,count ++, i).trim().replace(",","");						
						String tiencktong = getValue(sheet,count ++, i).trim().replace(",","");
						String tongtttruocvat = getValue(sheet,count ++, i).trim().replace(",","");
						String tienthue = getValue(sheet,count ++, i).trim().replace(",","");
						String tiensauvat = getValue(sheet,count ++, i).trim().replace(",","");
						String tongthanhtoan = getValue(sheet,count ++, i).trim().replace(",","");
						String scheme = getValue(sheet,count ++, i).trim().replace(",","");
						String makho = getValue(sheet,count ++, i).trim().replace(",","");
						String tenKenh = getValue(sheet,count ++, i).trim().replace(",","");
						String ghichu = getValue(sheet,count ++, i).trim().replace(",","");

						if(ngay.contains("/"))
						{	db.getConnection().rollback();
							db.shutDown();
							return "Ngày khong hợp lệ  ("+ngay+") , line "+(i+1)+" ";
						}
						
						if(!makho.trim().equals("HB") && !makho.trim().equals("KM"))
						{
							db.getConnection().rollback();
							db.shutDown();
							return "Mã kho khong hợp lệ  ("+makho+") , line "+(i+1)+" ";
						}
						
						if(!htp_kenh.containsKey(tenKenh))
						{
							db.getConnection().rollback();
							db.shutDown();
							return "Tên kênh bán hàng khong hợp lệ  ("+makho+") , line "+(i+1)+" ";
						}
						
						
						
						String quString="select pk_seq from nhaphanphoi where MANPP='"+manpp+"' ";
						ResultSet rsnpp=db.get(quString);
						if(!rsnpp.next())
						{
						
							db.getConnection().rollback();
							db.shutDown();
							return "Mã NPP ("+manpp+") dòng ("+ (i + 1 ) +") không tồn tại ";
						}
						String nppid=rsnpp.getString("pk_seq");
						rsnpp.close();
						
						String msg= geso.dms.center.util.Utility.Check_Huy_NghiepVu_KhoaSo_Tao_Moi_NV(nppid, geso.dms.center.util.Utility.getNgayHienTai(), db) ;
						if(msg.trim().length()>0)
						{
						
							db.getConnection().rollback();
							db.shutDown();
							return "nhà phân phối  "+manpp +" đã khóa sổ bạn không được upload !!! ";
						}
						 System.out.println("ghichu "+ghichu);
						String query = "\n insert into importSalein(kenh,tennpp, quycach, tenSP, ptCkSanPham, ngayhoadon, manpp, " +
						"\n sohoadon, masp, gia, sl, thanhtien, tongtien, ptcktong, tiencktong, tongtttruocvat, " +
						"\n tienthue, tiensauvat, tongthanhtoan, dangcapnhat, token, scheme, makho,ghichu) " + 
						"\n select ?,?, ?, ?, ?, ?, ?, ?, ?, ?, convert(numeric,?), " +
						"\n     ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,? " +
						"\n where not exists (select 1 from nhaphang where trangthai != 2 and CHUNGTU = ?) ";
						Object[] data;
						data = new Object[] {tenKenh,tennpp, quycach, tensp, ptck, ngay, manpp, sohoadon, masp, gia, sl, 
								thanhtien, tongtienhang, ptcktong, tiencktong, tongtttruocvat, tienthue, tiensauvat, 
								tongthanhtoan, "1", token, scheme, makho, ghichu,sohoadon}; 
						
						int up = db.updateQueryByPreparedStatement(query, data);
						dbutils.viewQuery(query, data);
						if (up == 0)
						{
							dbutils.viewQuery(query, data);
							db.getConnection().rollback();
							db.shutDown();
							return "Hóa đơn đã tồn tại ("+sohoadon+") dòng ("+ (i + 1 ) +") ";
						}
						if (up < 0)
						{
							dbutils.viewQuery(query, data);
							db.getConnection().rollback();
							db.shutDown();
							return "Lỗi dữ liệu ("+sohoadon+") dòng ("+ (i + 1 ) +") ";
						}

					}
				}
			}
			String query = " select count(*) as sl from importSalein a inner join  " +
					"   importSalein cc on cc.sohoadon=a.sohoadon"
					+ " where a.token =  '"+token+"' and a.token=cc.token  and a.ghichu<>cc.ghichu";
			ResultSet checkRs=db.get(query);
			System.out.println("query check= " + query);

			checkRs.next();
			if(checkRs.getInt("sl")>0){
				db.getConnection().rollback();
				db.shutDown();
				return "Các dòng chung số hóa đơn yêu cầu ghi chú phải giống nhau.Vui lòng kiểm tra lại";
			}
			
			
			 query = "\n insert into nhaphang(ghichuimport,tongthanhtoan,isdms,NGAYNHAN, NGUOITAO, NGUOISUA, NGAYTAO, NGAYSUA, TRANGTHAI, NPP_FK, NCC_FK, SOTIENBVAT, VAT, SOTIENAVAT, " +
			"\n 						DVKD_FK, KBH_FK, NGAYCHUNGTU, KHO_FK, CHUNGTU, HDTAICHINH, Created_Date,pt_chietkhau,tienchietkhau,tongtien ,sohoadon,ngayhoadon ) " +
			"\n   select tien.ghichu,tien.tongthanhtoan,100,convert(varchar(10), dbo.GetLocalDate(DEFAULT),120) " +
			"\n 		,100001,100001, convert(varchar(10), dbo.GetLocalDate(DEFAULT),120) , convert(varchar(10), dbo.GetLocalDate(DEFAULT),120) , 0, npp.PK_SEQ, 100022 " +
			"\n 		, tongtttruocvat, tienthue, tiensauvat " +
			"\n 		,100001 DVKD_FK, kbh.pk_seq , tien.ngayhoadon , 100000 as Khodat, hd.sohoadon, hd.sohoadon, dbo.GetLocalDate(DEFAULT)" +
			"\n 		,ptcktong,tiencktong,tongtien,hd.sohoadon,ngayhoadon " +
			"\n from " +
			"\n (" +
			"\n 	select distinct sohoadon from importSalein where dangcapnhat = 1 and token = '"+token+"' " +
			"\n ) hd " +
			"\n cross apply " +
			"\n ( " +
			"\n 	select x.ghichu,x.kenh,max(tongtien)tongtien,max(tiencktong)tiencktong,max(ptcktong)ptcktong,max(tongtttruocvat)tongtttruocvat,max(tienthue)tienthue " +
			"\n 		,max(tiensauvat)tiensauvat,max(tongthanhtoan)tongthanhtoan,manpp,max(sohoadon)sohoadon,max(ngayhoadon)ngayhoadon " +
			"\n 	from importSalein x where x.sohoadon = hd.sohoadon" +
			"\n     and x.token='" +  token + "'" +
			"\n		group by manpp,x.kenh	,x.ghichu  " +
			"\n  ) tien  " +
			"\n  inner join NHAPHANPHOI npp on npp.MANPP = tien.manpp" +
			"\n  inner join kenhbanhang kbh on kbh.ten = tien.kenh  ";
			int kq = db.updateReturnInt(query);
			System.out.println("count = " + kq);
			if (kq<= 0)
			{
				System.out.println(query);
				db.getConnection().rollback();
				db.shutDown();
				return "Lỗi tạo nhận hàng 1 ";
			}
			
			query =  "\n insert NHAPHANG_SP(scheme,NHAPHANG_FK,SANPHAM_FK,SOLUONG,GIANET,GIAGROSS,DONGIA,TIENBVAT,NGAYNHAPKHO,KhoNhan_Fk,kho_fk,KBH_FK,DONVI) " +
			 //"\n select x.scheme,nh.PK_SEQ, sp.PK_SEQ,x.sl,x.gia,x.gia,x.gia,x.thanhtien,nh.NGAYCHUNGTU,nh.KHO_FK,nh.KHO_FK,nh.KBH_FK,dvdl.DONVI " +
			 "\n select x.scheme,nh.PK_SEQ, sp.PK_SEQ,x.sl,x.gia,x.gia,x.gia,x.thanhtien,nh.NGAYCHUNGTU, k.pk_seq khonhan_fk, k.pk_seq KHO_FK, nh.KBH_FK,dvdl.DONVI " +
			 "\n from NHAPHANG nh " +
			 "\n inner join importSalein x on nh.sohoadon = x.sohoadon " +
			 "\n inner join kho k on k.ten = x.makho " +
			 "\n inner join SANPHAM sp on sp.ma = x.masp " +
			 "\n inner join DONVIDOLUONG dvdl on dvdl.PK_SEQ = sp.DVDL_FK " +
			 "\n where  nh.isdms = 100 and x.token = '"+token+"' ";
			System.out.println("query NH_ChiTiet : "+ query);
			if (db.updateReturnInt(query)<= 0)
			{
				System.out.println(query);
				db.getConnection().rollback();
				db.shutDown();
				return "Lỗi tạo nhận hàng 2 ";
			}
			query = " update nhaphang set isdms = 0 where  isdms = 100 and sohoadon is not null and sohoadon in (select sohoadon from importSalein where token = '"+token+"' )  ";
			if (db.updateReturnInt(query)<= 0)
			{
				System.out.println(query);
				db.getConnection().rollback();
				db.shutDown();
				return "Lỗi tạo nhận hàng 3 ";
			}
			query = " update importSalein set dangcapnhat = 0 where  dangcapnhat = 1 and token= '"+token+"' ";
			if (db.updateReturnInt(query)<= 0)
			{
				System.out.println(query);
				db.getConnection().rollback();
				db.shutDown();
				return "Lỗi tạo nhận hàng 4 ";
			}
			
			db.getConnection().commit();
			db.shutDown();
			return "thành công";
		} catch (Exception e)
		{
			System.out.println("Loi doc file" + e.getMessage());
			e.printStackTrace();
			return "Exeoption: " + e.getMessage();
		}
	}
	private String readExcel(String fileName, IUpload dhBean)
	{
		File inputWorkbook = new File(fileName);
		jxl.Workbook w = null;
		dbutils db = new dbutils();
		try
		{
			w = jxl.Workbook.getWorkbook(inputWorkbook);

			if (dhBean.getNppMa().trim().length()<=0)
			{
				return "Vui lòng chọn nhà phân phối";
			}

			int sosheet = w.getNumberOfSheets();
			String query = "delete from TonKho_Tmp";
			db.update(query);

			for (int ii = 0; ii < sosheet; ii++)
			{
				jxl.Sheet sheet = w.getSheet(ii);
				int sodong = sheet.getRows();
				int socot = sheet.getColumns();
				int soNpp = 1;
				System.out.println("[SoSheet]" + sosheet + "[So dong ]" + sodong + "[socot]" + socot + "[soNpp]" + soNpp);

				String khoTen = getValue(sheet, 6, 2).trim(); // Kho hang bán
				String khoTen2 = getValue(sheet, 7, 2).trim(); // "KHO HÀNG KHUYẾN MÃI";

				for (int i = 3; i < sodong; i++)
				{
					Cell[] cells = sheet.getRow(i);
					if (cells !=null){
						if (cells.length >= 1 && cells[1] != null  )
						{
							String nppMa = getValue(sheet, 1, i).trim();
							String nppTen = getValue(sheet, 2, i).trim();
							String spMa = getValue(sheet, 3, i).trim();
							String spTen = getValue(sheet, 4, i).trim();
							String kbhTen = getValue(sheet, 5, i).trim();	
							String SoLuong = getValue(sheet, 6, i).trim().replaceAll(",", "");
							String SoLuong2 = getValue(sheet, 7, i).trim().replaceAll(",", "");

							float soluongle = 0;
							try
							{
								if (SoLuong.length() > 0)
									soluongle=Float.parseFloat(SoLuong.replace(",", ""));
							}
							catch(Exception er)
							{
								er.printStackTrace();
								return "Số lượng không đúng [spMa]"+spMa +" [spTen]"+spTen +" [kbhTen] "+kbhTen;
							}


							if (spMa.length() > 0 && nppMa.length() > 0 && SoLuong.length()>0)
							{
								query = "\n insert into tonkho_tmp(nppMa,nppTen,kbhTen,khoTen,spMa,spTen,soluong)" + 
								"\n select ?,?,?,?,?,?,? ";
								Object[] data;
								data= new Object[]   { nppMa,nppTen,kbhTen,khoTen,spMa,spTen,SoLuong}; 
								if (db.updateQueryByPreparedStatement(query, data)<0)
								{
									return "Lỗi cập nhật ";
								}
							}

							if (spMa.length() > 0 && nppMa.length() > 0 && SoLuong2.length()>0)
							{
								query = "\n insert into tonkho_tmp(nppMa,nppTen,kbhTen,khoTen,spMa,spTen,soluong)" + 
								"\n select ?,?,?,?,?,?,? ";

								Object[] data;
								data= new Object[]   { nppMa,nppTen,kbhTen,khoTen2,spMa,spTen,SoLuong2}; 
								if (db.updateQueryByPreparedStatement(query, data)<0)
								{
									return "Lỗi cập nhật "+query;
								}
							}
						}
					}
				}
			}
			dhBean.importTonKho();
			return dhBean.getMsg();
		} catch (BiffException e)
		{
			System.out.println("Loi doc file" + e.getMessage());
			e.printStackTrace();
			return "Vui lòng coi lại định dạng file " + e.getMessage();
		} catch (IOException e)
		{
			e.printStackTrace();
			return "Vui lòng coi lại định dạng file " + e.getMessage();
		}
	}


	private String Delete(String nppId)
	{
		dbutils db=new dbutils();
		String query="select count(*) as sodong from donhang where npp_fk="+nppId+"";
		ResultSet rs=db.get(query);
		try
		{
			while(rs.next())
			{
				if (rs.getInt("sodong") >0)
				{
					return "Nhà phân phối này đã có  đơn hàng!";
				}
			}
			rs.close();
			query="select count(*) as sodong from nhaphang where  npp_fk='"+nppId+"'";
			rs=db.get(query);
			while(rs.next())
			{
				if (rs.getInt("sodong") >0)
				{
					return "Nhà phân phối này đã có  nhập hàng!";
				}
			}
			rs.close();


			query="select count(*)as sodong from DONTRAHANG where  npp_fk='"+nppId+"'";
			rs=db.get(query);
			while(rs.next())
			{
				if (rs.getInt("sodong") >0)
				{
					return "Nhà phân phối này đã có  đơn trả hàng!";
				}
			}
			rs.close();

			db.getConnection().setAutoCommit(false);
			query="delete from nhapp_kho where npp_fk='"+nppId+"'";
			if (!db.update(query))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return "Lỗi hệ thống "+query;
			}

			query="delete from nhapp_kho_chitiet where npp_fk='"+nppId+"'";
			if (!db.update(query))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return "Lỗi hệ thống "+query;
			}


			query="delete from tonkhongay where npp_fk='"+nppId+"'";
			if (!db.update(query))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return "Lỗi hệ thống "+query;
			}


			query="delete from tonkhongay_chitiet where npp_fk='"+nppId+"'";
			if (!db.update(query))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return "Lỗi hệ thống "+query;
			}			

			query="delete from khoasongay where npp_fk='"+nppId+"'";
			if (!db.update(query))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return "Lỗi hệ thống "+query;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(false);
			db.shutDown();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return "";
	}

	private String getValue(Sheet sheet, int column, int row)
	{
		Cell cell;
		cell = sheet.getCell(column, row);
		try{
			return cell.getContents();
		}catch(Exception er){
			return	"0";
		}
	}

	private String XuatFileExcel(HttpServletResponse response, IUpload obj) throws IOException
	{		
		OutputStream out = null;
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=TonKho_" + getDateTime() + ".xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());
			dbutils db = new dbutils();
			ArrayList<String> kho_ten = new ArrayList<String>();
			String sql = "select ten from KHO";
			ResultSet temprs = db.get(sql);
			while (temprs.next()) {
				kho_ten.add(temprs.getString("ten"));
			}
			temprs.close();
			
			
			sql = " select count(*)sd from khoasongay where npp_fk =  " + obj.getNppId();
			temprs = db.get(sql);
			temprs.next();
			int dakhoaso = temprs.getInt("sd");
			temprs.close();
			
			
			if(dakhoaso > 0)
				sql = "\n SELECT * "+
				"\n	FROM "+
				"\n 	("+		
				"\n select (select diengiai from kenhbanhang where pk_seq=tk.kbh_fk) as kbhTEN, " +
				"\n tk.kho_fk as khoId,npp.pk_seq as nppId,npp.manpp as nppMa,npp.TEN as nppTen," +
				"\n sp.PK_SEQ as spId,sp.MA as spMa,sp.ten as spTen,tk.SOLUONG "+
				"\n from  "+
				"\n	NHAPP_KHO tk inner join SANPHAM sp on sp.PK_SEQ=tk.SANPHAM_FK "+
				"\n	inner join NHAPHANPHOI npp on npp.PK_SEQ=tk.NPP_FK "+
				"\n  where tk.npp_fk='"+obj.getNppId()+"' )"+
				"\n AS SourceTable " +
				"\n  PIVOT "+
				"\n ( "+
				"\n sum(soluong) "+
				"\n for khoid  IN ( [100000], [100001] )"+
				" ) AS PivotTable ";
			else
				sql = "\n  select kbh.TEN kbhTEN, npp.PK_SEQ nppId, npp.MANPP   nppMa,npp.TEN as nppTen, sp.PK_SEQ spId, sp.MA spMa, sp.TEN spTen, 0  [100000], 0  [100001] " +
					"\n    from SANPHAM sp " +
					"\n    inner join KENHBANHANG kbh on kbh.trangthai = 1 " +
					"\n    inner join NHAPHANPHOI npp on npp.PK_SEQ = "+obj.getNppId() +
					"\n    where sp.TRANGTHAI = 1 " ;
			

			
			System.out.println("1.Khoi tao upload : " + sql);

			WritableFont cellFont = new WritableFont(WritableFont.ARIAL, 10);
			cellFont.setColour(Colour.BLACK);

			cellFont.setBoldStyle(jxl.write.WritableFont.BOLD);
			WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
			cellFormat.setBackground(jxl.format.Colour.GRAY_25);
			cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

			WritableFont fBold = new WritableFont(WritableFont.TIMES, 10);
			fBold.setColour(Colour.RED);
			fBold.setBoldStyle(jxl.write.WritableFont.BOLD);
			WritableCellFormat fmBold = new WritableCellFormat(fBold);
			fmBold.setBorder(Border.ALL, BorderLineStyle.THIN);
			ResultSet rs = db.get(sql);
			Label label;
			jxl.write.Number number;
			int row = 3;
			int col = 0;
			int stt =1;
			String nppIdPrev = "";
			String spPrev = "";
			int sheetIndex = 0;
			String tongthauPrev = "";
			WritableSheet sheet = null;
			int i=0;
			sheet = w.createSheet(obj.getNppId(), sheetIndex);
			sheet.addCell(new Label(i, 2, "STT", cellFormat));
			sheet.addCell(new Label(++i, 2, "Mã NPP", cellFormat));
			sheet.addCell(new Label(++i, 2, "Tên NPP", cellFormat));
			sheet.addCell(new Label(++i, 2, "Mã Sản phẩm", cellFormat));
			sheet.addCell(new Label(++i, 2, "Tên sản phẩm", cellFormat));
			sheet.addCell(new Label(++i, 2, "Kênh", cellFormat));

			if (kho_ten == null || kho_ten.size() <= 0) {
				return "Chưa khai báo kho trên DLN, vui lòng kiểm tra lại!";
			}
			else { 
				for (int z = 0; z < kho_ten.size(); z++) {
					sheet.addCell(new Label(++i, 2, kho_ten.get(z), cellFormat));
				}
			}
			/*	sheet.addCell(new Label(++i, 0, "Số lượng", cellFormat));*/

			if (rs != null)
			{
				while (rs.next())
				{
					i=0;
					String nppId = rs.getString("nppId");
					String nppMa = rs.getString("nppMa");
					String nppTen = rs.getString("nppTen");
					String spId = rs.getString("spId");
					String spMa = rs.getString("spMa");
					String spTen = rs.getString("spTen");

					String kbhTEN = rs.getString("kbhTEN");
					Double soluong1 =  rs.getDouble("100000");
					Double soluong2=  rs.getDouble("100001");


					number = new jxl.write.Number(i++, row, stt);sheet.addCell(number);
					sheet.addCell(new Label(i++, row, nppMa));
					sheet.addCell(new Label(i++, row, nppTen));


					sheet.addCell(new Label(i++, row, spMa));
					sheet.addCell(new Label(i++, row, spTen));
					sheet.addCell(new Label(i++, row,kbhTEN));
					number = new jxl.write.Number(i++, row,soluong1 );sheet.addCell(number);
					number = new jxl.write.Number(i++, row,soluong2 );sheet.addCell(number);

					row++;
					stt++;
				}
			}
			rs.close();
			w.write();
			w.close();
		} catch (Exception e)
		{
			e.printStackTrace();
			return "Không có báo cáo trong thời gian đã chọn " + e.getMessage();
		}
		return "";
	}

	public static void main(String[] arg)
	{
		/*ImportTonKhoSvl p = new ImportTonKhoSvl();
		IUpload obj = new Upload();
		p.readExcel("", obj);
		// String str = "R3";
		// System.out.println(" khop " + str.matches("\\W"));
*/
		DateFormat dffrom = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat dffrom2 = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat dfto = new SimpleDateFormat("yyyy-MM-dd");
		
		String ngay = "20/03/2019"; //getValue(sheet, count ++, i).trim();
		System.out.println(" dd/mm/yyyy:"+ isValidDate(ngay, "dd/MM/yyyy"));
		System.out.println(" yyyy-MM-dd:"+ isValidDate(ngay, "yyyy-MM-dd"));
		
		Date temp = null;
		try {
			temp = (ngay.matches("../../....")) ? dffrom2.parse(ngay) : dffrom.parse(ngay);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ngay = dfto.format(temp); 
		
		System.out.println(" ngay = "+ ngay);
	
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	private WritableCellFormat format(WritableFont font, Colour color, Colour background, Border boder, BorderLineStyle lineStyle)
	{
		WritableFont cellFont = new WritableFont(font);
		WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
		try
		{
			cellFont.setColour(color);
			cellFormat = new WritableCellFormat(cellFont);
			cellFormat.setBackground(background);
			cellFormat.setBorder(boder, lineStyle);
		} catch (WriteException e)
		{
			e.printStackTrace();
		}
		return cellFormat;

	}
	
	private static boolean isValidDate(String inDate, String format) {
		//format: "dd/MM/yyyy", yyyy-MM-dd
	    SimpleDateFormat dateFormat = new SimpleDateFormat(format);
	    dateFormat.setLenient(false);
	    try {
	      dateFormat.parse(inDate.trim());
	    } catch (ParseException pe) {
	      return false;
	    }
	    return true;
	  }
}


