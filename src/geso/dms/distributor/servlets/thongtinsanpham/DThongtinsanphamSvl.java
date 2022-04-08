package geso.dms.distributor.servlets.thongtinsanpham;

import geso.dms.distributor.beans.thongtinsanpham.IThongtinsanphamList;
import geso.dms.distributor.beans.thongtinsanpham.imp.ThongtinsanphamList;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

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

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.PivotFieldType;
import com.aspose.cells.PivotTable;
import com.aspose.cells.PivotTables;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
//import com.sun.corba.se.impl.javax.rmi.CORBA.Util;

public class DThongtinsanphamSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private int items;
	private int splittings;


	public DThongtinsanphamSvl() {
		super();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		Utility util = new Utility();
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
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
		if( Utility.CheckRuleUser( session,  request, response, "DThongtinsanphamSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}else{
		IThongtinsanphamList obj;       

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();


		out = response.getWriter();

		String querystring = request.getQueryString();
		userId = util.getUserId(querystring);
		out.println(userId);

		String action = util.getAction(querystring);
		out.println(action);

		obj = new ThongtinsanphamList();

		obj.setUserId(userId);
		settingPage(obj);
		obj.init("");

		session.setAttribute("obj", obj);

		String nextJSP = "/AHF/pages/Distributor/ThongTinSanPham.jsp";
		response.sendRedirect(nextJSP);	
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Utility util = new Utility();

			HttpSession session = request.getSession();
			String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		String userTen = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userTen")));
		String sum = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sum")));

		String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
		
		String action = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")));

		
			IThongtinsanphamList obj = new ThongtinsanphamList(); 

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			OutputStream out = response.getOutputStream();

			if (view == null)
				view = "";
			obj.setView(view);
			
			
			String param = "";
			if(view.trim().length() > 0) param ="&view="+view;
			if ( Utility.CheckSessionUser( session,  request, response))
			{
				Utility.RedireactLogin(session, request, response);
			}
			if( Utility.CheckRuleUser( session,  request, response, "DThongtinsanphamSvl", param, Utility.XEM ))
			{
				Utility.RedireactLogin(session, request, response);
			}else{

			settingPage(obj);

			if (action.equals("search"))
			{	    
				//obj = new ThongtinsanphamList();			    
				obj.setUserId(userId);
				obj.init(getSearchQuery(request, obj));

				session.setAttribute("obj", obj);

				session.setAttribute("userId", userId);

				response.sendRedirect("/AHF/pages/Distributor/ThongTinSanPham.jsp");	  		    	
			}
			else if(action.equals("view") || action.equals("next") || action.equals("prev")){

				String search = getSearchQuery(request, obj);

				obj.setNxtApprSplitting(Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
				obj.setUserId(userId);

				obj.init(search);
				obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
				session.setAttribute("obj", obj);
				response.sendRedirect("/AHF/pages/Distributor/ThongTinSanPham.jsp");
			}

			if (action.equals("excel"))
			{
				obj = new ThongtinsanphamList();
				try
				{
					response.setContentType("application/vnd.ms-excel");
					response.setHeader("Content-Disposition", "attachment; filename=DanhSachSanPham.xls");

					Workbook workbook = new Workbook();

					CreateStaticHeader(workbook, "Nha Phan Phoi");
					CreateStaticData(workbook, getSearchQuery2(request, obj));

					//Saving the Excel file
					workbook.save(out);
				}
				catch (Exception ex){}

				obj.setUserId(userId);
				obj.init(getSearchQuery(request, obj));

				session.setAttribute("obj", obj);

				session.setAttribute("userId", userId);

				response.sendRedirect("/AHF/pages/Distributor/ThongTinSanPham.jsp");	   
			}
		}
	}

	private void settingPage(IThongtinsanphamList obj) {

		Utility util = new Utility();
		if(getServletContext().getInitParameter("items") != null){
			String i = getServletContext().getInitParameter("items").trim();
			if(util.isNumeric(i))
				items = Integer.parseInt(i);
		}

		if(getServletContext().getInitParameter("splittings") != null){
			String i = getServletContext().getInitParameter("splittings").trim();
			if(util.isNumeric(i))
				splittings = Integer.parseInt(i);
		}

		obj.setItems(items);
		obj.setSplittings(splittings);

	}


	private String getSearchQuery(HttpServletRequest request, IThongtinsanphamList obj)
	{
		Utility util = new Utility();
		obj.getDataSearch().clear();
		
		String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
		if (nppId == null)
			nppId = "";

		String masp = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("masp")));
		if (masp == null)
			masp = "";
		obj.setMasp(masp);

		String tensp = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tensp")));
		if (tensp == null)
			tensp = "";
		obj.setTensp(tensp);

		String dvkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")));
		if (dvkdId == null)
			dvkdId = "";    	
		obj.setDvkdId(dvkdId);

		String nhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhId")));
		if (nhId == null)
			nhId = "";    	
		obj.setNhId(nhId);

		String clId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("clId")));
		if (clId == null)
			clId = "";    	
		obj.setClId(clId);

		String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));   	
		if (trangthai == null)
			trangthai = "";    	

		if (trangthai.equals("2"))
			trangthai = "";

		obj.setTrangthai(trangthai);

/*		String  	query ="select ROW_NUMBER() OVER(ORDER BY a.ma DESC) AS 'stt', a.pk_seq,ngh.ten as nganhhang,a.ma,a.ten,b.donvikinhdoanh  as dvkd,b.pk_seq as dvkdId,c.ten as chungloai, e.pk_seq as nhId,d.donvi,e.ten as nhanhang,d.pk_seq as clId,a.trangthai, " +
				"		[dbo].[GiaBanLeNppSanPham](100025,"+nppId+",a.pk_seq, [dbo].GetNgayHienTai()     )   as giablc ";
		query = query + " from sanpham a left join nganhhang ngh on ngh.pk_seq=a.NGANHHANG_fk left join donvikinhdoanh b on a.dvkd_fk = b.pk_seq left join chungloai c on a.chungloai_fk = c.pk_seq left join donvidoluong d on a.dvdl_fk = d.pk_seq left join nhanhang e on a.nhanhang_fk = e.pk_seq ";
		query = query  + "where b.pk_seq in (select c.pk_Seq from nhapp_nhacc_donvikd a inner join nhacungcap_dvkd b on a.ncc_dvkd_fk = b.pk_seq inner join donvikinhdoanh c on b.dvkd_fk = c.pk_seq "+
				"inner join nhacungcap ncc on ncc.pk_Seq=b.ncc_fk  where a.npp_fk='"+nppId+"') ";
*/
		String  	query ="select ROW_NUMBER() OVER(ORDER BY a.ma DESC) AS 'stt', a.pk_seq,ngh.ten as nganhhang,a.ma,a.ten,b.donvikinhdoanh  as dvkd,b.pk_seq as dvkdId,c.ten as chungloai, e.pk_seq as nhId,d.donvi,e.ten as nhanhang,d.pk_seq as clId,a.trangthai ," ;
		query = query + " [dbo].[GiaBanLeNppSanPham](null,100025,"+nppId+",a.pk_seq, [dbo].GetNgayHienTai()     ) as giablc ";
		query = query + " from sanpham a left join nganhhang ngh on ngh.pk_seq=a.NGANHHANG_fk left join donvikinhdoanh b on a.dvkd_fk = b.pk_seq left join chungloai c on a.chungloai_fk = c.pk_seq left join donvidoluong d on a.dvdl_fk = d.pk_seq left join nhanhang e on a.nhanhang_fk = e.pk_seq ";
		query = query  + "where b.pk_seq in (select c.pk_Seq from nhapp_nhacc_donvikd a inner join nhacungcap_dvkd b on a.ncc_dvkd_fk = b.pk_seq inner join donvikinhdoanh c on b.dvkd_fk = c.pk_seq "+
				"inner join nhacungcap ncc on ncc.pk_Seq=b.ncc_fk  where a.npp_fk='"+nppId+"') ";

		if (masp.length()>0){
			query = query + " \n and upper(a.ma) like ? ";
			obj.getDataSearch().add( "%" + masp + "%" );
		}

		if (tensp.length()>0){
			query = query + "and upper(dbo.ftBoDau(a.ten)) like upper (?) ";
					obj.getDataSearch().add("%" + util.replaceAEIOU(tensp) + "%");
		}

		if (dvkdId.length()>0){
			query = query + " and b.pk_seq = ? ";
			obj.getDataSearch().add(dvkdId );
		}

		if (nhId.length()>0){
			query = query + " and e.pk_seq = ? ";
			obj.getDataSearch().add(nhId  );
		}

		if (clId.length()>0){
			query = query + " and c.pk_seq = ?";
			obj.getDataSearch().add(clId );

		}
		if(trangthai.length() > 0){
			query = query + " and a.trangthai = ? ";
			obj.getDataSearch().add(trangthai);

		}



		return query;
	}	

	private void CreateStaticHeader(Workbook workbook, String UserName) 
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		Cells cells = worksheet.getCells();

		Cell cell = cells.getCell("A1"); 
		cell.setValue("Danh sách sản phẩm");

		cell = cells.getCell("A3");
		cell.setValue("Date Create: " + this.getDateTime());
		//cell = cells.getCell("A4");
		//cell.setValue("User:  " + UserName);

		//tieu de
		cell = cells.getCell("A8");
		cell.setValue("Đơn vị kinh doanh");

		cell = cells.getCell("B8");
		cell.setValue("Nhãn hàng");
		cell = cells.getCell("C8");
		cell.setValue("Chủng loại");
		cell = cells.getCell("D8");
		cell.setValue("Mã sản phẩm");
		cell = cells.getCell("E8");
		cell.setValue("Tên sản phẩm");
		cell = cells.getCell("F8");
		cell.setValue("Đơn vị đo lường");
		cell = cells.getCell("G8");
		cell.setValue("Gía bán lẻ chuẩn");
		//	    cell = cells.getCell("H8");
		//	    cell.setValue("Quy cách");

		worksheet.setName("Danh sách sản phẩm");
	}

	private void CreateStaticData(Workbook workbook, String query) 
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();

		dbutils db = new dbutils();
		System.out.println("xuat excel " + query);
		ResultSet rs = db.get(query);

		int i = 9;
		if(rs != null)
		{
			try 
			{
				cells.setColumnWidth(0, 25.0f);
				cells.setColumnWidth(1, 15.0f);
				cells.setColumnWidth(2, 15.0f);
				cells.setColumnWidth(3, 30.0f);
				cells.setColumnWidth(4, 45.0f);
				cells.setColumnWidth(5, 25.0f);
				cells.setColumnWidth(6, 15.0f);
				//				cells.setColumnWidth(7, 15.0f);

				for(int j = 0; j < 8; j++)
					cells.setRowHeight(j, 13.0f);

				Cell cell = null;
				while(rs.next())
				{
					String dvkd = "";
					if(rs.getString("dvkd")!= null)
						dvkd = rs.getString("dvkd");

					String nhanhang = "";
					if(rs.getString("nhanhang") != null)
						nhanhang = rs.getString("nhanhang");

					String chungloai = "";
					if(rs.getString("chungloai") != null)
						chungloai = rs.getString("chungloai");

					String masp = "";
					if(rs.getString("ma") != null)
						masp = rs.getString("ma");

					String tensp = "";
					if(rs.getString("ten") != null)
						tensp = rs.getString("ten");

					String dvdl = "";
					if(rs.getString("donvi") != null)
						dvdl = rs.getString("donvi");

					String gblc = "";
					if(rs.getString("giablc") != null)
						gblc = rs.getString("giablc");

					//					String quycach = "";
					//					if(rs.getString("quycach") != null)
					//						quycach = rs.getString("quycach");

					cell = cells.getCell("A" + Integer.toString(i));
					cell.setValue(dvkd);
					cell = cells.getCell("B" + Integer.toString(i));
					cell.setValue(nhanhang);
					cell = cells.getCell("C" + Integer.toString(i));
					cell.setValue(chungloai);
					cell = cells.getCell("D" + Integer.toString(i));
					cell.setValue(masp);
					cell = cells.getCell("E" + Integer.toString(i));
					cell.setValue(tensp);
					cell = cells.getCell("F" + Integer.toString(i));
					cell.setValue(dvdl);
					cell = cells.getCell("G" + Integer.toString(i));
					cell.setValue(gblc);
					//					cell = cells.getCell("H" + Integer.toString(i));
					//					cell.setValue(quycach);

					i++;
				}
				rs.close();
				if(db!=null){
					db.shutDown();
				}
			}
			catch(Exception e){ e.printStackTrace(); }
		}
		/*
		//create pivot
		PivotTables pivotTables = worksheet.getPivotTables();

	    //Adding a PivotTable to the worksheet
		String pos = Integer.toString(i-1);		
	    int index = pivotTables.add("=A8:H" + pos,"A8","DanhSachSanPham");

	    //Accessing the instance of the newly added PivotTable
	    PivotTable pivotTable = pivotTables.get(index);

	    //Unshowing grand totals for rows.
	    pivotTable.setRowGrand(false);

	    //Draging the first field to the row area.
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 0);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 1);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 2);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 3);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 4);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 5);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 6);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 7);
		 */
	}

	private String getDateTime() 
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		return dateFormat.format(date);	
	}

	private String getSearchQuery2(HttpServletRequest request, IThongtinsanphamList obj)
	{
		Utility util = new Utility();
		String masp = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("masp")));
		if (masp == null)
			masp = "";
		obj.setMasp(masp);

		String tensp = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tensp")));
		if (tensp == null)
			tensp = "";
		obj.setTensp(tensp);

		String dvkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")));
		if (dvkdId == null)
			dvkdId = "";    	
		obj.setDvkdId(dvkdId);

		String nhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhId")));
		if (nhId == null)
			nhId = "";    	
		obj.setNhId(nhId);

		String clId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("clId")));
		if (clId == null)
			clId = "";    	
		obj.setClId(clId);

		String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));   	
		if (trangthai == null)
			trangthai = "";    	

		if (trangthai.equals("2"))
			trangthai = "";

		obj.setTrangthai(trangthai);

		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  

		dbutils db = new dbutils();
		String	query = "select KHUVUC_FK from NHAPHANPHOI where PK_SEQ = '"+util.getIdNhapp(userId)+"'";
		String kv="";
		try
		{
			ResultSet rs = db.get(query);
			rs.next();
			kv = rs.getString("KHUVUC_FK");
			rs.close();
		}
		catch(Exception ex){}

		query = "\n select ROW_NUMBER() OVER(ORDER BY a.ma DESC) AS 'stt', a.pk_seq,a.ma,a.ten,b.donvikinhdoanh as dvkd," +
		"\n b.pk_seq as dvkdId,c.ten as chungloai, e.pk_seq as nhId,d.donvi,e.ten as nhanhang,d.pk_seq as clId," +
		"\n a.trangthai, isnull(f.giabanlechuan, '0') as giablc " +
		"\n from sanpham a left join donvikinhdoanh b on a.dvkd_fk = b.pk_seq " +
		"\n left join chungloai c on a.chungloai_fk = c.pk_seq " +
		"\n left join donvidoluong d on a.dvdl_fk = d.pk_seq " +
		"\n left join nhanhang e on a.nhanhang_fk = e.pk_seq " +
		"\n left join banggiablc_sanpham f on a.pk_seq = f.sanpham_fk " +
		"\n left join banggiabanlechuan g on f.bgblc_fk = g.pk_seq " +
		"\n-- inner join BANGGIABANLECHUAN_KHUVUC h on h.BANGGIABANLECHUAN_FK = g.PK_SEQ " +
		"\n where b.pk_seq in " +
		"\n 	( " +
		"\n			select c.pk_Seq from nhapp_nhacc_donvikd a " +
		"\n 		inner join nhacungcap_dvkd b on a.ncc_dvkd_fk = b.pk_seq " +
		"\n 		inner join donvikinhdoanh c on b.dvkd_fk = c.pk_seq " +
		"\n 		inner join nhacungcap ncc on ncc.pk_Seq=b.ncc_fk " +
		"\n 		where a.npp_fk='"+util.getIdNhapp(userId)+"'" +
		"\n		) " +
		"\n-- and h.KHUVUC_FK = '"+kv+"'"; 

		if (masp.length()>0){
			query = query + "\n and upper(a.ma) like upper('%" + masp + "%')";	
		}

		if (tensp.length()>0){
			query = query + "\n and upper(dbo.ftBoDau(a.ten)) like upper(N'"+ util.replaceAEIOU(tensp) + "') ";///" and upper(a.ten) like upper(N'%" + util.replaceAEIOU(tensp) + "%')";
		}

		if (dvkdId.length()>0){
			query = query + "\n and b.pk_seq = '" + dvkdId + "'";	
		}

		if (nhId.length()>0){
			query = query + "\n and e.pk_seq = '" + nhId + "'";   		
		}

		if (clId.length()>0){
			query = query + "\n and c.pk_seq = '" + clId + "'";    		
		}

		if(trangthai.length() > 0){
			query = query + "\n and a.trangthai = '" + trangthai + "'";   		
		}
		System.out.print("Query Excel: " + query);
		return query;
	}	
}
