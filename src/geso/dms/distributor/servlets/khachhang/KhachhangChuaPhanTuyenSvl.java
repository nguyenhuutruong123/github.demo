package geso.dms.distributor.servlets.khachhang;

import geso.dms.distributor.beans.khachhang.*;
import geso.dms.distributor.beans.khachhang.imp.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.Font;
import com.aspose.cells.HorizontalAlignmentType;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class KhachhangChuaPhanTuyenSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	int items = 1;
	int splittings = 1;
	
    public KhachhangChuaPhanTuyenSvl() 
    {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
		
		IKhachhangList obj;
		PrintWriter out; 
		
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out  = response.getWriter();
   

	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		else if( Utility.CheckRuleUser( session,  request, response, "KhachhangChuaPhanTuyenSvl", "", Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}
		else{
			obj = new KhachhangList();
		    items = Integer.parseInt(getServletContext().getInitParameter("items"));
		    splittings = Integer.parseInt(getServletContext().getInitParameter("splittings"));
		    //String s1 = getServletConfig().getInitParameter("databaseType");
		    //System.out.println("items: "+s+"; config: " + s1);
		    obj.setUserId(userId);
		    
	    	obj.setItems(items);
	    	obj.setSplittings(splittings);
	    	//System.out.println("items: "+items);
	    	
		    obj.khChuaPhanTuyen("");
		    
			session.setAttribute("obj", obj);
					
			String nextJSP = "/AHF/pages/Distributor/KhachHangChuaPhanTuyen.jsp";
			response.sendRedirect(nextJSP);
		}

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{

		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{

			IKhachhangList obj = new KhachhangList();
			obj.setItems(items);
			obj.setSplittings(splittings);
			PrintWriter out; 

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			
			Utility util = new Utility();
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

			String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
			if (action == null){
				action = "";
			}

			if (action != null && !action.equals("excel")) {
				out = response.getWriter();
				out.println(action); 
			}

			if (action.equals("search"))
			{	    
				String search = getSearchQuery(request, obj);
				//search = search + " and a.npp_fk='" + userId + "' order by a.ten";

				//obj = new KhachhangList(search);
				obj.setUserId(userId);
				obj.khChuaPhanTuyen(search);

				session.setAttribute("obj", obj);  	
				session.setAttribute("userId", userId);

				response.sendRedirect("/AHF/pages/Distributor/KhachHangChuaPhanTuyen.jsp");	    		    	
			} 
			else if (action.equals("excel"))
			{
				String search = getSearchQuery(request, obj);
				obj.setUserId(userId);
				OutputStream outstream = response.getOutputStream();	
				String query = getQuery(obj, search);

				try
				{
					response.setContentType("application/vnd.ms-excel");
					response.setHeader("Content-Disposition", "attachment; filename=DanhsachKhachhangChuaphantuyen_"+getDateTime()+".xls");

					Workbook workbook = new Workbook();

					CreateStaticHeader(workbook, obj.getUserId());
					CreateStaticData(workbook, query);

					//Saving the Excel file
					workbook.save(outstream);
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
					obj.setMsg("Exception : "+ex.getMessage());
				}

				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);
				return;
				//response.sendRedirect("/IMEXPHARM/pages/Distributor/KhachHang.jsp");	    		
			}
			else if(action.equals("view") || action.equals("next") || action.equals("prev")){

				String search = getSearchQuery(request, obj);

				obj.setNxtApprSplitting(Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
				obj.setUserId(userId);

				obj.khChuaPhanTuyen(search);
				obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
				session.setAttribute("obj", obj);
				response.sendRedirect("/AHF/pages/Distributor/KhachHangChuaPhanTuyen.jsp");
			}
			else{
				obj.setUserId(userId);
				obj.khChuaPhanTuyen("");
				session.setAttribute("obj", obj);

				String nextJSP = "/AHF/pages/Distributor/KhachHangChuaPhanTuyen.jsp";
				response.sendRedirect(nextJSP);
			}
		}
	}
	
	private String getSearchQuery(HttpServletRequest request, IKhachhangList obj)
	{		
		Utility util = new Utility();
		String ten = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khTen")));
		if ( ten == null)
			ten = "";
		obj.setTen(ten);

		String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
		if ( nppId == null)
			nppId = "";
		obj.setNppId(nppId);

		String hchId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("hchTen")));
		if (hchId == null)
			hchId = "";    	
		obj.setHchId(hchId);

		String kbhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kbhTen")));
		if (kbhId == null)
			kbhId = "";    	
		obj.setKbhId(kbhId);

		String vtchId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vtchTen")));
		if (vtchId == null)
			vtchId = "";    	
		obj.setVtId(vtchId);

		String lchId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("lchTen")));
		if (lchId == null)
			lchId = "";    	
		obj.setLchId(lchId);

		String query = "";
		obj.getDatasearch().clear();
		if (ten.length()>0)
		{ 
			/*query = query + " and upper(dbo.ftBoDau(a.ten)) like upper(N'%" + util.replaceAEIOU(ten) + "%')";	*/	
			query = query + " and upper(dbo.ftBoDau(a.ten)) like upper(?)";
			obj.getDatasearch().add("%" + util.replaceAEIOU(ten) + "%");
		}

		if (kbhId.length()>0){
			/*query = query + " and a.kbh_fk ='" + kbhId + "'";	*/
			query = query + " and a.kbh_fk =?";
			obj.getDatasearch().add(kbhId);
		}

		if (hchId.length()>0){
/*			query = query + " and a.hch_fk='" + hchId + "'";*/
			query = query + " and a.hch_fk=?";
			obj.getDatasearch().add(hchId);
		}

		if (vtchId.length()>0){
			/*query = query + " and a.vtch_fk='" + vtchId + "'";	*/
			query = query + " and a.vtch_fk=?";
			obj.getDatasearch().add(vtchId);
		}

		if (lchId.length()>0){
			/*query = query + " and a.lch_fk='" + lchId + "'";	*/
			query = query + " and a.lch_fk=?";
			obj.getDatasearch().add(lchId);
		}

		return query;
	}	
	
	private void CreateStaticHeader(Workbook workbook, String UserName) 
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		Cells cells = worksheet.getCells();
		Style style;

		Cell cell = cells.getCell("A1"); 
		cells.merge(0,0,0,11);
		cell.setValue("DANH SÁCH KHÁCH HÀNG CHƯA PHÂN TUYẾN");	
		style = cell.getStyle();
		Font font2 = new Font();	
		font2.setName("Calibri");
		font2.setColor(Color.NAVY);
		font2.setSize(18);
		font2.setBold(true);
		style.setFont(font2);
		style.setHAlignment(TextAlignmentType.CENTER);					
		cell.setStyle(style);

		font2 = new Font();	
		font2.setName("Calibri");
		font2.setBold(true);
		font2.setSize(11);

		cell = cells.getCell("A3");
		cell.setValue("Ngày tạo: " + this.getDateTime());
		style = cell.getStyle();
		style.setFont(font2);
		cell.setStyle(style);

		//tieu de
		int cot = 0;
		cell = cells.getCell(4,cot++);cell.setValue("Mã KH");  		style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		cell = cells.getCell(4,cot++);cell.setValue("Tên KH");  		style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);    
		cell = cells.getCell(4,cot++);cell.setValue("Trạng thái");  		style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);    
		cell = cells.getCell(4,cot++);cell.setValue("Ngày tạo");  		style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);    
		cell = cells.getCell(4,cot++);cell.setValue("Ngày sửa");  		style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);    
		cell = cells.getCell(4,cot++);cell.setValue("Người tạo");  		style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);    
		cell = cells.getCell(4,cot++);cell.setValue("Người sửa");  		style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);    

		worksheet.setName("DSKH chưa phân tuyến");
	}
	
	private void CreateStaticData(Workbook workbook, String query) 
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();

		dbutils db = new dbutils();
		ResultSet rs = db.get(query);
		System.out.println("Query BC: "+query);
		NumberFormat formatter = new DecimalFormat("#,###,###");
		int i = 5;
		if(rs != null)
		{
			try 
			{
				cells.setColumnWidth(0, 15f);
				cells.setColumnWidth(1, 40.0f);
				cells.setColumnWidth(2, 30.0f);
				cells.setColumnWidth(3, 16.0f);
				cells.setColumnWidth(4, 16.0f);
				cells.setColumnWidth(5, 28.0f);
				cells.setColumnWidth(6, 40.0f);
				cells.setColumnWidth(7, 50.0f);
				cells.setColumnWidth(8, 15.0f);

				for(int j = 0; j < 11; j++)
				{
					if(j==0)
						cells.setRowHeight(j, 23.0f);
					else
						cells.setRowHeight(j, 13.0f);
				}

				Cell cell = null;

				Style style;
				Font font2 = new Font();
				font2.setName("Calibri");				
				font2.setSize(11);

				while(rs.next())
				{
					int cot = 0;
					String khId = rs.getString("khId");
					String khTen = rs.getString("khTen");
					String _trangthai = rs.getString("trangthai");
					String trangthai = "";
					if (_trangthai != null && _trangthai.equals("0")) {
						trangthai = "Không hoạt động";
					}
					else {
						trangthai = "Hoạt động";
					}
					String ngaytao = rs.getString("ngaytao");
					String ngaysua = rs.getString("ngaysua");
					String nguoitao = rs.getString("nguoitao");
					String nguoisua = rs.getString("nguoisua");

					cell = cells.getCell(i,cot++);	cell.setValue(khId); 			style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell(i,cot++);	cell.setValue(khTen); 			style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell(i,cot++);	cell.setValue(trangthai); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell(i,cot++);	cell.setValue(ngaytao); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell(i,cot++);	cell.setValue(ngaysua); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell(i,cot++);	cell.setValue(nguoitao); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell(i,cot++);	cell.setValue(nguoisua); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);

					i++;
				}
				rs.close();
			}
			catch (Exception e)
			{ 
				e.printStackTrace(); 
			}
		}
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	private void setCellBorderStyle(Cell cell, short align) {
		Style style = cell.getStyle();
		style.setHAlignment(align);
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);
		cell.setStyle(style);
	}
	
	public String getQuery(IKhachhangList obj, String search)
	{
		String query = "";
		
		query = "\n select  a.smartid as khId, a.ten as khTen, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, " +
				"\n 	c.ten as nguoisua, d.chietkhau, d.pk_seq as mckId, " + 
			"\n 	e.diengiai as kbhTen, e.pk_seq as kbhId, f.hang as hchTen, f.pk_seq as hchId, g.loai as lchTen, " +
			"\n 	g.pk_seq as lchId, h.ten as nppTen, h.pk_seq as nppId, " +
			"\n 	k.sotienno as ghcnTen, k.pk_seq as ghcnId, l.ten as bgstTen, l.pk_seq as bgstId, m.vitri as vtchTen, " +
			"\n 	m.pk_seq as vtchId, a.dienthoai, a.diachi " +
			"\n from khachhang a inner join nhanvien b on a.nguoitao = b.pk_seq " +
			"\n inner join nhanvien c on a.nguoisua = c.pk_seq " +
			"\n left join mucchietkhau d on a.chietkhau_fk = d.pk_seq " +
			"\n left join kenhbanhang e on a.kbh_fk = e.pk_seq " +
			"\n left join hangcuahang f on a.hch_fk = f.pk_seq " +
			"\n left join loaicuahang g on a.lch_fk = g.pk_seq " +
			"\n inner join nhaphanphoi h on a.npp_fk = h.pk_seq " +
			"\n left join gioihancongno k on a.ghcn_fk = k.pk_seq " +
			"\n left join banggiasieuthi l on a.bgst_fk = l.pk_seq " +
			"\n left join vitricuahang m on a.vtch_fk = m.pk_seq " +
			"\n where 1=1 " +
			"\n and a.trangthai = 1 and a.PK_SEQ NOT IN (SELECT KHACHHANG_FK FROM KHACHHANG_TUYENBH) ";
		
		if (obj.getNppId() != null && obj.getNppId().length() > 0);
		{
			query += "\n and a.npp_fk = "+obj.getNppId();
		}
		
		query += search;
		return query;
	}
}
