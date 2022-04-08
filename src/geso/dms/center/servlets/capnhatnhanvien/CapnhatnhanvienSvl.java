package geso.dms.center.servlets.capnhatnhanvien;

import geso.dms.center.beans.capnhatnhanvien.ICapnhatnhanvien;
import geso.dms.center.beans.capnhatnhanvien.ICapnhatnhanvienList;
import geso.dms.center.beans.capnhatnhanvien.imp.Capnhatnhanvien;
import geso.dms.center.beans.capnhatnhanvien.imp.CapnhatnhanvienList;
import geso.dms.center.util.Utility;
import geso.dms.center.db.sql.dbutils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
public class CapnhatnhanvienSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public CapnhatnhanvienSvl()
	{
		super();
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		ICapnhatnhanvienList obj;
		HttpSession session = request.getSession();
		obj = new CapnhatnhanvienList();
		Utility util = new Utility();
		out = response.getWriter();
		
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "CapnhatnhanvienSvl", "", Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}
		
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		out.println(userId);
		obj.init("");
		session.setAttribute("obj", obj);
		response.sendRedirect("/AHF/pages/Center/CapNhatNhanVien.jsp");
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		ICapnhatnhanvienList obj;
		obj = new CapnhatnhanvienList();
		HttpSession session = request.getSession();
		Utility util = new Utility();
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		String Ten = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ten")));
		if (Ten == null)
			Ten = "";
		obj.setTen(Ten);
		
		String username_email = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("username_email")));
		if (username_email == null)
			username_email = "";
		obj.setUsername_email(username_email);
		
		String loainhanvien = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loainhanvien_fk")));
		if (loainhanvien == null)
			loainhanvien = "";
		obj.setLoainhanvien_fk(loainhanvien);
		
		String Phanloai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("phanloai")));
		if (Phanloai == null)
			Phanloai = "";
		obj.setPhanloai(Phanloai);
		
		String Tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
		if (Tungay == null)
			Tungay = "";
		obj.setTungay(Tungay);
		
		String Denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
		if (Denngay == null)
			Denngay = "";
		obj.setDenngay(Denngay);
		
		String Trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));
		if (Trangthai == null)
			Trangthai = "";
		obj.setTrangthai(Trangthai);
		String st = "";
		
		if (loainhanvien.length() > 0)
		{
			st = st + " and a.loai = ? ";
			obj.getDataSearch().add(  loainhanvien );
		}
		if (Ten.length() > 0)
		{
			st = st + " and a.ten like upper(?)";
			obj.getDataSearch().add("%" + Ten+ "%");
		}
		if (username_email.length() > 0)
		{
			st = st + " and (a.dangnhap like upper(?) or a.email like upper(?)  )   ";
			obj.getDataSearch().add("%" + username_email+ "%");
			obj.getDataSearch().add("%" + username_email+ "%");
		}
		
		if (Phanloai.length() > 0)
		{
			st = st + " and a.phanloai = ? ";
			obj.getDataSearch().add( Phanloai);
			
		}
		if (Tungay.length() > 0)
		{
			st = st + "and a.ngaytao >= ?";
			obj.getDataSearch().add("%" + Tungay+ "%");
		}
		if (Denngay.length() > 0)
		{
			st = st + "and a.ngaytao <= ? ";
			obj.getDataSearch().add("%" + Denngay+ "%");
		}
		if (Trangthai.length() > 0)
		{
			st = st + "and a.trangthai = ? ";
			obj.getDataSearch().add( Trangthai);
		}
		//obj.CreateLoaiNV();
		obj.init(st);
		
		
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "CapnhatnhanvienSvl", "", Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}
		
		
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		if (action.equals("delete"))
		{
			ICapnhatnhanvienList obj1 = new CapnhatnhanvienList();
			String IdXoa = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("IdXoa")));
			if (IdXoa == null)
				IdXoa = "";
			System.out.println("IdXoa = "+ IdXoa);
			if(IdXoa.trim().length() > 0)
				obj1.xoa(IdXoa);
//			else
//				obj.setMsg("Lỗi dữ liệu");
			session.setAttribute("obj", obj1);
			response.sendRedirect("/AHF/pages/Center/CapNhatNhanVien.jsp");
		} else if (action.equals("xoa"))
		{
			obj = new CapnhatnhanvienList();
			obj.init("");
			session.setAttribute("obj", obj);
			response.sendRedirect("/AHF/pages/Center/CapNhatNhanVien.jsp");
		} else if (action.equals("toExcel"))
		{
			String sql = "select a.pk_seq,a.Ten,a.dangnhap,a.email,a.dienthoai,a.trangthai,a.phanloai,b.ten as nguoitao1,c.ten as nguoisua1,a.ngaytao,a.ngaysua from nhanvien a inner join nhanvien b on b.pk_seq = a.nguoitao inner join nhanvien c on c.pk_seq = a.nguoisua  where 1= 1 " + st;
			ToExcel(response, sql);
		} else if (action.equals("new"))
		{
			ICapnhatnhanvien objnv = new Capnhatnhanvien();
			objnv.setuserId(userId);
			objnv.init();
			
			objnv.CreateQuyen(null);
			objnv.CreateKenh(null);
			objnv.CreateNpp(null);
			objnv.CreateSanpham(null);
			// obj.init_sanpham("");
			
			session.setAttribute("obj", objnv);
			response.sendRedirect("/AHF/pages/Center/CapNhatNhanVienUpdate.jsp");
		} else
		{
			System.out.println("loainhanvien");
			session.setAttribute("obj", obj);
			response.sendRedirect("/AHF/pages/Center/CapNhatNhanVien.jsp");
		}
		
	}
	private void ToExcel(HttpServletResponse response, String query) throws IOException
	{
		OutputStream out = null;
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=CapNhatNhanVien.xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());
			
			int k = 0;
			int j = 5;
			
			WritableSheet sheet = null;
			
			WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 14);
			cellTitle.setColour(Colour.BLACK);
			cellTitle.setBoldStyle(WritableFont.BOLD);
			
			sheet = w.createSheet("CapNhatNhanVien", k);
			sheet.addCell(new Label(0, 1, "Cập nhật nhân viên: ", new WritableCellFormat(cellTitle)));
			
			sheet.addCell(new Label(0, 2, "Ngày tạo: "));
			sheet.addCell(new Label(1, 2, "" + getDateTime()));
			
			sheet.addCell(new Label(2, 4, "Đơn vị tiền tệ:VND"));
			
			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
			cellFont.setColour(Colour.BLACK);
			
			WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
			
			cellFormat.setBackground(jxl.format.Colour.LIME);
			cellFormat.setWrap(true);
			cellFormat.setAlignment(Alignment.CENTRE);
			cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			WritableCellFormat cellFormatSpecical = new WritableCellFormat(cellFont);
			
			cellFormatSpecical.setBackground(jxl.format.Colour.LIGHT_ORANGE);
			cellFormatSpecical.setWrap(true);
			cellFormatSpecical.setAlignment(Alignment.CENTRE);
			cellFormatSpecical.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormatSpecical.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormatSpecical.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormatSpecical.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormatSpecical.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			sheet.addCell(new Label(0, 4, "STT", cellFormat));
			sheet.addCell(new Label(1, 4, "HỌ TÊN", cellFormatSpecical));
			sheet.addCell(new Label(2, 4, "ĐĂNG NHẬP", cellFormat));
			sheet.addCell(new Label(3, 4, "EMAIL", cellFormat));
			sheet.addCell(new Label(4, 4, "ĐIỆN THOẠI", cellFormat));
			sheet.addCell(new Label(5, 4, "TRẠNG THÁI", cellFormat));
			sheet.addCell(new Label(6, 4, "PHÂN LOẠI", cellFormat));
			sheet.addCell(new Label(7, 4, "NGƯỜI TẠO", cellFormat));
			sheet.addCell(new Label(8, 4, "NGƯỜI SỬA", cellFormat));
			sheet.addCell(new Label(9, 4, "NGÀY TẠO", cellFormat));
			sheet.addCell(new Label(10, 4, "NGÀY SỬA", cellFormat));
			
			sheet.setRowView(100, 4);
			
			sheet.setColumnView(0, 10);
			sheet.setColumnView(1, 20);
			sheet.setColumnView(2, 30);
			sheet.setColumnView(3, 25);
			sheet.setColumnView(4, 20);
			sheet.setColumnView(5, 20);
			sheet.setColumnView(6, 15);
			sheet.setColumnView(7, 35);
			sheet.setColumnView(8, 15);
			sheet.setColumnView(9, 15);
			sheet.setColumnView(10, 15);
			sheet.setColumnView(11, 15);
			sheet.setColumnView(12, 15);
			sheet.setColumnView(13, 15);
			sheet.setColumnView(14, 60);
			dbutils db = new dbutils();
			
			ResultSet rs = db.get(query);
			
			WritableCellFormat cellFormat2 = new WritableCellFormat(new jxl.write.NumberFormat("#,###,###"));
			
			cellFormat2.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			WritableCellFormat cellFormat3 = new WritableCellFormat(new jxl.write.NumberFormat("#,###,###"));
			cellFormat3.setBackground(jxl.format.Colour.YELLOW);
			cellFormat3.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat3.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat3.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat3.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			WritableCellFormat cformat = null;
			Label label;
			Number number;
			int stt = 0;
			while (rs.next())
			{
				
				cformat = cellFormat2;
				stt++;
				number = new Number(0, j, stt, cformat);
				sheet.addCell(number);
				label = new Label(1, j, rs.getString("Ten"), cformat);
				sheet.addCell(label);
				label = new Label(2, j, rs.getString("dangnhap"), cformat);
				sheet.addCell(label);
				label = new Label(3, j, rs.getString("email"), cformat);
				sheet.addCell(label);
				label = new Label(4, j, rs.getString("dienthoai"), cformat);
				sheet.addCell(label);
				label = new Label(5, j, rs.getInt("trangthai") == 0 ? "Ngưng hoạt động" : rs.getInt("trangthai") == 1 ? "Hoạt động" : "Đã hủy", cformat);
				sheet.addCell(label);
				label = new Label(6, j, rs.getInt("phanloai") == 1 ? "Nhà phân phối" : rs.getInt("phanloai") == 2 ? "Trung tâm" : "", cformat);
				sheet.addCell(label);
				label = new Label(7, j, rs.getString("nguoitao1"), cformat);
				sheet.addCell(label);
				label = new Label(8, j, rs.getString("nguoisua1"), cformat);
				sheet.addCell(label);
				
				label = new Label(9, j, rs.getString("ngaytao"), cformat);
				sheet.addCell(label);
				label = new Label(10, j, rs.getString("ngaysua"), cformat);
				sheet.addCell(label);
				
				j++;
			}
			w.write();
			w.close();
			rs.close();
			db.shutDown();
		} catch (Exception e)
		{
			System.out.println("Error Cac Ban : " + e.getMessage());
			e.printStackTrace();
		} finally
		{
			if (out != null)
				out.close();
			
		}
	}
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
}
