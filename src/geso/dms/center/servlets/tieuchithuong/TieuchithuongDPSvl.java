package geso.dms.center.servlets.tieuchithuong;

import geso.dms.center.beans.tieuchithuong.ITieuchithuongDP;
import geso.dms.center.beans.tieuchithuong.ITieuchithuongDPList;
import geso.dms.center.beans.tieuchithuong.imp.TieuchithuongDP;
import geso.dms.center.beans.tieuchithuong.imp.TieuchithuongDPList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
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

public class TieuchithuongDPSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
   
    public TieuchithuongDPSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    
	    HttpSession session = request.getSession();	
	    
	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    ITieuchithuongDPList obj = new TieuchithuongDPList();
	    obj.setUserId(userId);
	    
	    String action = util.getAction(querystring);
	    String ctskuId = util.getId(querystring);
	    
	    //System.out.println("___Action: " + action + " -- Id la: " + ctskuId);
	    if(action.trim().equals("duyet"))
	    {
	    	dbutils db = new dbutils();
	    	db.update("update tieuchithuongDP set trangthai = '1' where pk_seq = '" + ctskuId + "'");
	    	db.shutDown();
	    }
	    
	    if(action.trim().equals("delete"))
	    {
	    	XoaChiTieu(ctskuId);
	    }

	    obj.init("");
		session.setAttribute("obj", obj);
	    
	    String nextJSP = "/AHF/pages/Center/TieuChiThuongDP.jsp";
		response.sendRedirect(nextJSP);
	}

	private void XoaChiTieu(String ctskuId) 
	{
		dbutils db = new dbutils();
    	
    	try 
    	{
			db.getConnection().setAutoCommit(false);
			
			System.out.println("delete TIEUCHITHUONGDP_TIEUCHI where thuongdp_fk = '" + ctskuId + "'");
	    	if(!db.update("delete TIEUCHITHUONGDP_TIEUCHI where thuongdp_fk = '" + ctskuId + "'"))
	    	{
	    		db.getConnection().rollback();
				return;
	    	}
	    	
	    	if(!db.update("delete TIEUCHITHUONGDP where pk_seq = '" + ctskuId + "'"))
			{
				db.getConnection().rollback();
				return;
			}
	    	db.getConnection().commit();
	    	db.shutDown();
		} 
    	catch (SQLException e)
    	{
    		try 
    		{
				db.getConnection().rollback();
			} catch (SQLException e1) {}
    	}
    	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    
	    HttpSession session = request.getSession();	
	    
	    Utility util = new Utility();
	    
	    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));     
	    ITieuchithuongDPList obj;
	    
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = "";
	    }
	  
	    if(action.equals("new"))
	    {
    		ITieuchithuongDP tctsku = new TieuchithuongDP();
    		tctsku.setUserId(userId);
    		tctsku.createRs();
    		
	    	session.setAttribute("tctskuBean", tctsku);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/AHF/pages/Center/TieuChiThuongDPNew.jsp");
	    }else 
	    if (action.equals("excel"))
 		{
	    	obj = new TieuchithuongDPList();
	    	String search = getSearchQuery(request, obj);
 			ToExcel(response, search);
 		}
	    else
	    {
	    	obj = new TieuchithuongDPList();
	    	String search = getSearchQuery(request, obj);
		    obj.setUserId(userId);
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/AHF/pages/Center/TieuChiThuongDP.jsp");	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, ITieuchithuongDPList obj) 
	{
		String thang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thang"));
		if(thang == null)
			thang = "";
		obj.setThang(thang);
		
		String nam = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nam"));
		if(nam == null)
			nam = "";
		obj.setNam(nam);
		
		String sql = "select a.scheme, a.pk_seq, a.thang, a.nam, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua  " +
					"from TIEUCHITHUONGDP a inner join NHANVIEN b on a.NGUOITAO = b.pk_seq " +
					"inner join NHANVIEN c on a.NGUOISUA = c.pk_seq where a.pk_seq > 0 ";
		if(thang.length() > 0)
			sql += " and a.thang = '" + thang + "' ";
		if(nam.length() > 0)
			sql += " and a.nam = '" + nam + "' ";
		
		sql += "order by nam desc, thang desc";
		
		return sql;
	}
	private void ToExcel(HttpServletResponse response, String query) throws IOException
	{
		OutputStream out = null;
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=TieuChiThuongDoPhu.xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());

			int k = 0;
			int j = 5;

			WritableSheet sheet = null;

			WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 14);
			cellTitle.setColour(Colour.BLACK);
			cellTitle.setBoldStyle(WritableFont.BOLD);

			sheet = w.createSheet("TieuChiThuongDoPhu", k);
			sheet.addCell(new Label(0, 1, "Tiêu chí thưởng độ phủ : ", new WritableCellFormat(cellTitle)));

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
			sheet.addCell(new Label(1, 4, "THÁNG", cellFormatSpecical));
			sheet.addCell(new Label(2, 4, "NĂM", cellFormat));
			sheet.addCell(new Label(3, 4, "SCHEME", cellFormat));
			sheet.addCell(new Label(4, 4, "DIỄN GIẢI", cellFormat));
			sheet.addCell(new Label(5, 4, "TRẠNG THÁI ", cellFormat));
			sheet.addCell(new Label(6, 4, "NGÀY TẠO", cellFormat));
			sheet.addCell(new Label(7, 4, "NGƯỜI TẠO", cellFormat));
			sheet.addCell(new Label(8, 4, "NGÀY SỬA", cellFormat));
			sheet.addCell(new Label(9, 4, "NGƯỜI SỬA", cellFormat));

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
			int stt=0;
			while (rs.next())
			{
		
				cformat = cellFormat2 ;
				stt++;
				number = new Number(0, j, stt, cformat);sheet.addCell(number);
				label = new Label(1, j, rs.getString("THANG"), cformat);sheet.addCell(label);
				label = new Label(2, j, rs.getString("NAM"), cformat);sheet.addCell(label);
				label = new Label(3, j, rs.getString("SCHEME"), cformat);sheet.addCell(label);
				label = new Label(4, j, rs.getString("DIENGIAI"), cformat);sheet.addCell(label);
				label = new Label(5, j, rs.getInt("trangthai") == 0 ? "Chờ xử lý" :rs.getInt("trangthai") == 1? "Đã chốt":"Đã hủy", cformat);sheet.addCell(label);
				label = new Label(6, j, rs.getString("ngaytao"), cformat);sheet.addCell(label);
				label = new Label(7, j, rs.getString("NguoiTao"), cformat);sheet.addCell(label);
				label = new Label(8, j, rs.getString("NgaySua"), cformat);sheet.addCell(label);
				label = new Label(9, j, rs.getString("NguoiSua"), cformat);sheet.addCell(label);

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
