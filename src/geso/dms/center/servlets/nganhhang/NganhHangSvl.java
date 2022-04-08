package geso.dms.center.servlets.nganhhang;

import geso.dms.center.beans.nganhhang.*;
import geso.dms.center.beans.nganhhang.imp.*;
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


public class NganhHangSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	
    public NganhHangSvl()
    {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		INganhHangList obj;
		PrintWriter out;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out  = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    
	    obj = new NganhHangList();
	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String nghId = util.getId(querystring);
	    
	    //--- check user
	    String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
		if (view == null) {
			view = "";
		}
		String param = "";
		if(view.trim().length() > 0) param ="&view="+view;
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "NganhHangSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}
		 //--- check user
	    

	    if (action.equals("delete")){
	    	Delete(obj,nghId);
	    	out.print(nghId);
	    }
	   	
	   
	    obj.setUserId(userId);
	    obj.init("");
		session.setAttribute("obj", obj);				
		String nextJSP = "/AHF/pages/Center/NganhHang.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		INganhHangList obj=new NganhHangList();
		PrintWriter out;
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
	    //out = response.getWriter();
	    
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = "";
	    }
	    //out.println(action); 
	    
	    //--- check user
	    String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
		if (view == null)
			view = "";
		obj.setView(view);
		String param = "";
		if(view.trim().length() > 0) param ="&view="+view;
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			obj.DBClose();
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "NganhHangSvl", param, Utility.XEM ))
		{
			obj.DBClose();
			Utility.RedireactLogin(session, request, response);
		}
		 //--- check user
	    
	    if (action.equals("new")){
	    	
	        INganhHang	nhBean =  new NganhHang("");
	    	nhBean.setUserId(userId);
	    	session.setAttribute("nhBean", nhBean);
    		String nextJSP = "/AHF/pages/Center/NganhHangNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    } 
	    else if (action.equals("search"))
	    {
	    	obj = new NganhHangList();
	    	String search = getSearchQuery(request,obj);
	    	
	    	obj.init(search);
			obj.setUserId(userId);
			
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/AHF/pages/Center/NganhHang.jsp");	    	
	    } 
	    else if (action.equals("excel"))
	    {
	    	obj = new NganhHangList(); obj.DBClose();
	    	ToExcel(response, obj, getSearchQuery(request,obj));
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request,INganhHangList obj)
	{
		obj.getDataSearch().clear();
		Utility util = new Utility();
			
			String ten = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ten")));
	    	if ( ten == null)
	    		ten = "";
	    	obj.setTen(ten );
	    	
	    	String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
	    	if (diengiai == null)
	    		diengiai = "";    	
	    	obj.setDiengiai(diengiai);
	    	
	    	String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));   	
	    	if (trangthai == null)
	    		trangthai = "";    	
		
	    	if (trangthai.equals("2"))
	    		trangthai = "";
	    	
	    	obj.setTrangthai(trangthai);
	    	//geso.dms.distributor.util.Utility util = new geso.dms.distributor.util.Utility();
	    	String query = "select a.pk_seq, a.ten , a.diengiai, a.trangthai, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua from nganhhang a " +
	    					"inner join  nhanvien b on b.pk_seq=a.nguoitao " +
	    					"inner join  nhanvien c on a.nguoisua = c.pk_seq where 1=1";	 			
	    	if (ten.length()>0){
				query = query + " and upper(dbo.ftBoDau(a.ten)) like upper(?)";
				
				obj.getDataSearch().add( "%" + util.replaceAEIOU(ten) + "%" );	
	    	}
	    	
	    	if (diengiai.length()>0){
				query = query + " and upper(dbo.ftBoDau(a.diengiai)) like upper(?)";
				obj.getDataSearch().add( "%" + util.replaceAEIOU(diengiai) + "%" );	
	    	}
	  
	    	if(trangthai.length() > 0){
	    		query = query + " and a.trangthai = ? ";
	    		obj.getDataSearch().add( trangthai );	
	    	}
	    	query = query + "  order by a.ten";
	    	return query;
		}	
	
	private void Delete(INganhHangList obj,String id)
	{
		dbutils db = new dbutils();		
		try {
			//Kiem tra ton tai nhan hang. Neu khong co thi duoc xoa nganh hang
			String query = "select count(*) as sodong from NHANHANG a where NGANHHANG_FK=" + id;
			ResultSet rs = db.get(query);
			System.out.println("query Delete: '" + query + "' ");
			if(rs!=null)
			{
				rs.next();
				int sodong = rs.getInt("sodong");
				if(sodong>0)
				{
					obj.setMsg("Tồn tại Nhãn Hàng nên không xóa Ngành Hàng này.");
					return;					
				}
			}
			db.update("delete from nganhhang where pk_seq = '" + id + "'");	
			
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.shutDown();
	}


	private void ToExcel(HttpServletResponse response, INganhHangList obj, String query) throws IOException
	{
		OutputStream out = null;
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=NganhHang.xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());

			int k = 0;
			int j = 5;

			WritableSheet sheet = null;

			WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 14);
			cellTitle.setColour(Colour.BLACK);
			cellTitle.setBoldStyle(WritableFont.BOLD);

			sheet = w.createSheet("NganhHang", k);
			sheet.addCell(new Label(0, 1, "NGÀNH HÀNG: ", new WritableCellFormat(cellTitle)));

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
			sheet.addCell(new Label(1, 4, "TÊN", cellFormat));
			sheet.addCell(new Label(2, 4, "DIỄN GIẢI", cellFormatSpecical));
			sheet.addCell(new Label(3, 4, "TRẠNG THÁI", cellFormat));
			sheet.addCell(new Label(4, 4, "NGÀY TẠO", cellFormat));
			sheet.addCell(new Label(5, 4, "NGƯỜI TẠO", cellFormat));
			sheet.addCell(new Label(6, 4, "NGÀY SỬA", cellFormat));
			sheet.addCell(new Label(7, 4, "NGƯỜI SỬA", cellFormat));

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
				label = new Label(1, j, rs.getString("ten"), cformat);
				sheet.addCell(label);
				label = new Label(2, j, rs.getString("diengiai"), cformat);
				sheet.addCell(label);
				label = new Label(3, j, rs.getInt("trangthai") == 0 ? "Ngưng hoạt động" : "Hoạt động", cformat);
				sheet.addCell(label);
				label = new Label(4, j, rs.getString("NgayTao"), cformat);
				sheet.addCell(label);
				label = new Label(5, j, rs.getString("NguoiTao"), cformat);
				sheet.addCell(label);
				label = new Label(6, j, rs.getString("NgaySua"), cformat);
				sheet.addCell(label);
				label = new Label(7, j, rs.getString("NguoiSua"), cformat);
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
