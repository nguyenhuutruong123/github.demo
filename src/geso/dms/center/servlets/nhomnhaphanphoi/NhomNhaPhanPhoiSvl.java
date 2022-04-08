package geso.dms.center.servlets.nhomnhaphanphoi;

import geso.dms.center.beans.khuvuc.IKhuvuc;
import geso.dms.center.beans.khuvuc.IKhuvucList;
import geso.dms.center.beans.nhaphanphoi.INhaphanphoiList;
import geso.dms.center.beans.nhaphanphoi.imp.NhaphanphoiList;
import geso.dms.center.beans.nhomnhaphanphoi.INhomNhaPhanPhoi;
import geso.dms.center.beans.nhomnhaphanphoi.INhomNhaPhanPhoiList;
import geso.dms.center.beans.nhomnhaphanphoi.imp.NhomNhaPhanPhoi;
import geso.dms.center.beans.nhomnhaphanphoi.imp.NhomNhaPhanPhoiList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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

/**
 * Servlet implementation class NhomNhaPhanPhoiSvl
 */
@WebServlet("/NhomNhaPhanPhoiSvl")
public class NhomNhaPhanPhoiSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	//PrintWriter out;

	public NhomNhaPhanPhoiSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		System.out.println("vao doget rui ");
		INhomNhaPhanPhoiList obj;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		//this.out = response.getWriter();

		HttpSession session = request.getSession();

		Utility util = new Utility();
		//out = response.getWriter();
		obj = new NhomNhaPhanPhoiList();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		//out.println(userId);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

		String action = util.getAction(querystring);
		//out.println(action);

		String nppId = util.getId(querystring);
		if(action!=null)
		{
			if (action.equals("delete"))
			{
				Delete(nppId);
				//out.print(nppId);
			}
			obj.setUserId(userId);
			obj.init("");
			session.setAttribute("obj", obj);
			String nextJSP = "/AHF/pages/Center/NhomNhaPhanPhoi.jsp";
			response.sendRedirect(nextJSP);	
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		INhomNhaPhanPhoiList obj = new NhomNhaPhanPhoiList();

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		//OutputStream out = response.getOutputStream();

		Utility util = new Utility();

		HttpSession session = request.getSession();
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

		String ten = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppTen")));
		if (ten == null)
			ten = "";
		obj.setTen(ten);

		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		if (action == null)
		{
			action = "";
		}
		String nextJSP = "";

		if (action.equals("new"))
		{
			INhomNhaPhanPhoi nppBean = (NhomNhaPhanPhoi) new NhomNhaPhanPhoi();
			nppBean.setUserId(userId);
			nppBean.createRs();
			session.setAttribute("nnppBean", nppBean);
			nextJSP = "/AHF/pages/Center/NhomNhaPhanPhoiNew.jsp";
			session.setAttribute("userId", userId);
			response.sendRedirect(nextJSP);

		} else if (action.equals("search"))
		{
			obj.setUserId(userId);
			String search = getSearchQuery(request);
			obj.init(search);
			nextJSP = "/AHF/pages/Center/NhomNhaPhanPhoi.jsp";
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			response.sendRedirect(nextJSP);
		} 


	    else if (geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")).equals("excel")) {
	    	ToExcel(response, getSearchQuery(request));
	    }
		
		else if (action.equals("view") || action.equals("next") || action.equals("prev"))
		{

			obj.setUserId(userId);
			obj.setNxtApprSplitting(Integer.parseInt(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting")))));
			obj.init("");
			obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
			session.setAttribute("obj", obj);
			nextJSP = "/AHF/pages/Center/NhomNhaPhanPhoi.jsp";
			response.sendRedirect(nextJSP);
		}
	}

	private void Delete(String id)
	{
		dbutils db = new dbutils();
		String sql = " delete from  nhomnpp_npp where nhomnpp_fk = '" + id + "' ";
		db.update(sql);

		sql="delete from nhomnpp where pk_seq='"+id+"'";
		db.update(sql);
	}

	private String getSearchQuery(HttpServletRequest request)
	{
		INhomNhaPhanPhoiList obj = new NhomNhaPhanPhoiList();

		Utility util = new Utility();

		String ma = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ma")));
		if (ma == null)
			ma = "";
		obj.setMa(ma);
		
		String ten = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ten")));
		if (ten == null)
			ten = "";
		obj.setTen(ten);

//		String sodienthoai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("DienThoai")));
//		if (sodienthoai == null)
//			sodienthoai = "";
//		obj.setSodienthoai(sodienthoai);
//
//		String kvId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kvId")));
//		if (kvId == null)
//			kvId = "";
//		obj.setKvId(kvId);
//
//		String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("TrangThai")));
//		if (trangthai == null)
//			trangthai = "";
//
//		if (trangthai.equals("2"))
//			trangthai = "";
//
//		obj.setTrangthai(trangthai);
//		String query = "		select a.ma as nppMa, " + "		ISNULL " + "		 ( " + "			 (  " + "				select TEN from KENHBANHANG where PK_SEQ in " + "				(  " + "					select KBH_FK from NHAPP_KBH where NPP_FK=A.PK_SEQ " + "				)  "
//				+ "			 ),N'Chưa khai báo' " + "		 ) as KenhBanHang, " + "		 (  " + "			select PK_SEQ from KENHBANHANG where PK_SEQ in " + "				(  " + "					select KBH_FK from NHAPP_KBH where NPP_FK=A.PK_SEQ " + "				)  "
//				+ "		 ) as KBH_FK,a.pk_seq as id, a.ten as nppTen, a.diachi, a.dienthoai, d.ten as khuvuc, a.trangthai, a.ngaytao,  "
//				+ "		a.ngaysua, b.ten as nguoitao, c.ten as nguoisua ,TongThau_FK,isnull((select TEN from NHAPHANPHOI where PK_SEQ=a.TongThau_FK),'')  as TongThau " + "	 from nhaphanphoi a  " + "	inner join nhanvien b on b.pk_seq=a.nguoitao  "
//				+ "	inner join  nhanvien c on c.pk_seq=a.nguoisua   " + "	left join  khuvuc d  on a.khuvuc_fk=d.pk_seq " + " where 1=1 ";

		String query = "SELECT a.pk_seq as id,a.ma,a.ten,nt.TEN as NguoiTao,a.ngaytao,ns.TEN as NguoiSua,a.ngaysua "+
				"FROM NHOMNPP a, NHANVIEN nt, NHANVIEN ns where nt.PK_SEQ=a.nguoitao and ns.PK_SEQ=a.nguoisua ";
		if (ma.length() > 0)
		{
			query = query + " and (upper(a.ma) like upper(N'%" + util.replaceAEIOU(ma) + "%'))";
		}
		
		if (ten.length() > 0)
		{
			query = query + " and (upper(a.ten) like upper(N'%" + util.replaceAEIOU(ten) + "%'))";
		}
		
//		if (ten.length() > 0)
//		{
//			query = query + " and (upper(dbo.ftBoDau(a.ten)) like upper(N'%" + util.replaceAEIOU(ten) + "%') or upper(dbo.ftBoDau(a.ma)) like upper(N'%" + util.replaceAEIOU(ten) + "%'))";
//		}

//		if (sodienthoai.length() > 0)
//		{
//			query = query + " and upper(a.dienthoai) like upper('%" + sodienthoai + "%')";
//		}
//		if (kvId.length() > 0)
//		{
//			query = query + " and d.pk_seq = '" + kvId + "'";
//		}
//
//		if (trangthai.length() > 0)
//		{
//			query = query + " and a.trangthai = '" + trangthai + "'";
//
//		}
		return query;
	}
	


	private void ToExcel(HttpServletResponse response, String query) throws IOException
	{
		OutputStream out = null;
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=NhomNhaPhanPhoi.xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());

			int k = 0;
			int j = 5;

			WritableSheet sheet = null;

			WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 14);
			cellTitle.setColour(Colour.BLACK);
			cellTitle.setBoldStyle(WritableFont.BOLD);

			sheet = w.createSheet("NhomNhaPhanPhoi", k);
			sheet.addCell(new Label(0, 1, "NHÓM NHÀ PHÂN PHỐI: ", new WritableCellFormat(cellTitle)));

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
			sheet.addCell(new Label(1, 4, "MÃ", cellFormat));
			sheet.addCell(new Label(2, 4, "TÊN", cellFormatSpecical));
			sheet.addCell(new Label(3, 4, "NGÀY TẠO", cellFormat));
			sheet.addCell(new Label(4, 4, "NGƯỜI TẠO", cellFormat));
			sheet.addCell(new Label(5, 4, "NGÀY SỬA", cellFormat));
			sheet.addCell(new Label(6, 4, "NGƯỜI SỬA", cellFormat));

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
				stt++;
				String type = "0";
				cformat = type.equals("1") ? cellFormat3 : cellFormat2;

				number = new Number(0, j, stt, cformat);
				sheet.addCell(number);
				label = new Label(1, j, rs.getString("nppMa"), cformat);
				sheet.addCell(label);
				label = new Label(2, j, rs.getString("nppTen"), cformat);
				sheet.addCell(label);
				label = new Label(3, j, rs.getString("NgayTao"), cformat);
				sheet.addCell(label);
				label = new Label(4, j, rs.getString("NguoiTao"), cformat);
				sheet.addCell(label);
				label = new Label(5, j, rs.getString("NgaySua"), cformat);
				sheet.addCell(label);
				label = new Label(6, j, rs.getString("NguoiSua"), cformat);
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

