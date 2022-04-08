package geso.dms.center.servlets.diaban;

import geso.dms.center.beans.diaban.*;
import geso.dms.center.beans.diaban.imp.*;

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
import java.util.List;

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


public class DiabanSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	//PrintWriter out;
	
    public DiabanSvl()
    {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		IDiabanList obj;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    //this.out  = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    
	    obj = new DiabanList();
	    Utility util = new Utility();
	    //out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    //out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = util.getAction(querystring);
	    //out.println(action);
	    
	    String kvId = util.getId(querystring);

	    if (action.equals("delete")){	   		  	    	
	    	Delete(kvId);
	    	//out.print(kvId);
	    }
	   	
	   
	    obj.setUserId(userId);
	    obj.init("");
		session.setAttribute("obj", obj);
				
		String nextJSP = "/AHF/pages/Center/Diaban.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		IDiabanList obj = new DiabanList();
		Utility util = new Utility();
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    //this.out = response.getWriter();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = "";
	    }
	    //out.println(action); 
	        
	    
	    if (action.equals("new")){
	    	// Empty Bean for distributor
	    	IDiaban kvBean = (IDiaban) new Diaban("");
	    	kvBean.setUserId(userId);
	    	// Save Data into session
	    	session.setAttribute("kvBean", kvBean);
    		
    		String nextJSP = "/AHF/pages/Center/DiabanUpdate.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }

		/*else if (action.equals("excel")) {
			obj.init(getSearchQuery(request,obj));
			ToExcel(response, obj);
		} */
	    
	    if (action.equals("search")){
	    	String search = getSearchQuery(request,obj);
	    	
	    	//obj = new KhuvucList(search);
	    	obj.init(search);
			obj.setUserId(userId);
			
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
    		session.setAttribute("abc", search);
	    		
    		response.sendRedirect("/AHF/pages/Center/Diaban.jsp");	    	
	    	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request,IDiabanList obj){
		   // PrintWriter out = response.getWriter();
			
		//IKhuvucList obj = new KhuvucList();
		
		Utility util = new Utility();
		
			String khuvuc = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvuc")));
	    	if ( khuvuc == null)
	    		khuvuc = "";
	    	obj.setKhuvucId(khuvuc);
	    	
	    	String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("TrangThai")));   	
	    	if (trangthai == null)
	    		trangthai = "";    	
		
	   if (trangthai.equals("2"))
	  	trangthai = "";
	    	
	    	obj.setTrangthai(trangthai);

			
			String query = "select a.pk_seq as id, a.ten as dbTen, a.trangthai as trangthai, a.khuvuc_fk, b.ten as kvTen, c.ten as nguoitao, a.ngaytao as ngaytao, d.ten as nguoisua, a.ngaysua as ngaysua, a.diengiai";
			query = query + " from diaban a, khuvuc b, nhanvien c, nhanvien d";
			query = query + " where a.khuvuc_fk=b.pk_seq and a.nguoitao = c.pk_seq and a.nguoisua = d.pk_seq";
		

	    	if (khuvuc.length()>0){
	
	    		query = query + " and a.khuvuc_fk ='" + khuvuc + "'";
	    	}
	  
	    	if(trangthai.length() > 0){
	    		query = query + " and a.trangthai = '" + trangthai + "'";	
	    	}
	    	query = query + " order by a.ten";
	    	System.out.println("cau lenh:"+ query);
	    	return query;
		}	
	boolean kiemtra(String sql)
	{dbutils db =new dbutils();
    	ResultSet rs = db.get(sql);
		try {//kiem tra ben san pham
		while(rs.next())
		{ if(rs.getString("num").equals("0"))
		   return true;
		}
		} catch(Exception e) {
		
			e.printStackTrace();
		}
		 return false;
	}

	private void Delete(String id)
	{	
		
		IDiabanList obj = new DiabanList();
		
		dbutils db = new dbutils();
		String sql ="select count(diaban_fk) as num from daidienkinhdoanh where diaban_fk='"+ id + "'";
		if(kiemtra(sql))
		{
			/* sql ="select count(khuvuc_fk) as num from giamsatbanhang where khuvuc_fk='"+ id + "'";
			if(kiemtra(sql))
			{
				db.update("delete from diaban where pk_seq = '" + id + "'");
				db.shutDown();
			}
			else
				obj.setMsg("khu vuc nay da ton tai trong giam sat ban hang roi nen khong the xoa duoc");*/
			db.update("delete from diaban where pk_seq = '" + id + "'");
			db.shutDown();
		}
		else
			obj.setMsg("khu vuc nay da ton tai trong nha phan phoi roi nen khong the xoa duoc");
	/*	ResultSet rs1 = db.get("select count(khuvuc_fk) as num from nhaphanphoi where khuvuc_fk='"+ id + "'");
		try{
			rs1.next();			
			if (rs1.getString("num").equals("0")){		
				db.update("delete from khuvuc where pk_seq = '" + id + "'");
				db.shutDown();
			}
		}catch(Exception e){}
		*/
	}




	private void ToExcel(HttpServletResponse response, IDiabanList obj) throws IOException
	{
		OutputStream out = null;
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=KhuVuc.xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());

			int k = 0;
			int j = 5;

			WritableSheet sheet = null;

			WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 14);
			cellTitle.setColour(Colour.BLACK);
			cellTitle.setBoldStyle(WritableFont.BOLD);

			sheet = w.createSheet("KhuVuc", k);
			sheet.addCell(new Label(0, 1, "KHU VỰC: ", new WritableCellFormat(cellTitle)));

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
			sheet.addCell(new Label(1, 4, "KHU VỰC", cellFormat));
			sheet.addCell(new Label(2, 4, "DIỄN GIẢI", cellFormatSpecical));
			sheet.addCell(new Label(3, 4, "KHU VUC", cellFormat));
			sheet.addCell(new Label(4, 4, "TRẠNG THÁI", cellFormat));
			sheet.addCell(new Label(5, 4, "NGÀY TẠO", cellFormat));
			sheet.addCell(new Label(6, 4, "NGƯỜI TẠO", cellFormat));
			sheet.addCell(new Label(7, 4, "NGÀY SỬA", cellFormat));
			sheet.addCell(new Label(8, 4, "NGƯỜI SỬA", cellFormat));

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
			
			List<IDiaban> list = (List<IDiaban>)obj.getDiabanList();
			IDiaban kv = null;

			int stt = 0;
			for(int i = 0; i < list.size(); i++) 
			{
				kv = list.get(i);
				stt++;
				String type = "0";
				cformat = type.equals("1") ? cellFormat3 : cellFormat2;

				number = new Number(0, j, stt, cformat);
				sheet.addCell(number);
				label = new Label(1, j, kv.getTen(), cformat);
				sheet.addCell(label);
				label = new Label(2, j, kv.getDiengiai(), cformat);
				sheet.addCell(label);
				label = new Label(3, j, kv.getKhuvucTen(), cformat);
				sheet.addCell(label);
				label = new Label(4, j, kv.getTrangthai().equals("Hoat dong") ? "Hoạt động" : "Ngưng hoạt động", cformat);
				sheet.addCell(label);
				label = new Label(5, j, kv.getNgaytao(), cformat);
				sheet.addCell(label);
				label = new Label(6, j, kv.getNguoitao(), cformat);
				sheet.addCell(label);
				label = new Label(7, j, kv.getNgaysua(), cformat);
				sheet.addCell(label);
				label = new Label(8, j, kv.getNguoisua(), cformat);
				sheet.addCell(label);

				j++;
			}
			w.write();
			w.close();
			for(int i = 0; i < list.size(); i++){
				kv = list.get(i);
				kv.closeDB();
			}
			obj.closeDB();
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
