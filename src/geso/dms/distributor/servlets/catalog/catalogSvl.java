package geso.dms.distributor.servlets.catalog;


import geso.dms.distributor.beans.catalog.*;
import geso.dms.distributor.beans.catalog.imp.*;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Worksheet;
import com.oreilly.servlet.MultipartRequest;

public class catalogSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   PrintWriter out;
   
	public catalogSvl() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
		IcatalogList obj;
		
	    response.setContentType("text/html");
	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    out = response.getWriter();
	    obj = new catalogList();
	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String nspId = util.getId(querystring);
	    
	    if (action.equals("delete")){	
	    	
	    	Delete(nspId);
	    }
	    
	    if (action.equals("chot")){	
	    	
	    	Chot(nspId);
	    }

	    obj.init("");
	    
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);

		String nextJSP = "/AHF/pages/Center/catalog.jsp";
		response.sendRedirect(nextJSP);
	    
	}  	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
		IcatalogList obj;
		   
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    Utility util = new Utility();
	    String contentType = request.getContentType();
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

	    obj = new catalogList();
	    
	  
	    
	    if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
	    {
	    	MultipartRequest multi = new MultipartRequest(request, "C:\\java-tomcat\\catalog\\", 20000000, "UTF-8");
			
	    	try {
	    		  userId = util.antiSQLInspection(multi.getParameter("userId"));

				importExcell(userId,request,response,session,contentType,multi);
				 obj.init("");
				    
					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);

					String nextJSP = "/AHF/pages/Center/catalog.jsp";
					response.sendRedirect(nextJSP);
					return;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
	    }
	    
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = "";
	    }
	    
	    if (action.equals("new")){
	    	Icatalog nspBean = (Icatalog) new catalog();
	    	
	    	//nspBean.createRS();
	    	
    		session.setAttribute("nspBean", nspBean);
    		session.setAttribute("userId", userId);
    		
    		String nextJSP = "/AHF/pages/Center/catalogNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }
	    else if (action.equals("search")){
	    	
		    /*	String search = getSearchQuery(request,obj);

		    	obj.init(search);
				session.setAttribute("obj", obj);

	    		session.setAttribute("userId", userId);
		    		
	    		response.sendRedirect("/PHUCVINH/pages/Center/catalog.jsp");*/
		}
	    else if (action.equals("excel"))
	    {
	    	response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=Catalog.xls");
			OutputStream out = response.getOutputStream();

				try {
					
						ExportToExcel(out,obj);
					
					return;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    }
	}

	private void ExportToExcel(OutputStream out,IcatalogList obj )throws Exception
	{
		try
		{ 		
			String strfstream = getServletContext().getInitParameter("path") + "\\Catalog.xls";
			
			/*com.aspose.cells.Workbook workbook = new com.aspose.cells.Workbook();
			workbook.setFileFormatType(FileFormatType.EXCEL2003);
			*/
			
			FileInputStream fstream = new FileInputStream(strfstream);
			//Workbook workbook = new Workbook();
			com.aspose.cells.Workbook workbook = new com.aspose.cells.Workbook();
			
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		
			String condition ="";
			


			
			
			String query=" select MA [Mã Catalog],ten [Tên Catalog],NHANHANG [Nhãn Hàng],chungloai_fk [Chủng Loại],ghichu [Ghi chú ],duongdan [Tên File] from Catalog ";
			
			
		
			
			//System.out.println("query chi tiet la "+query);
			TaoBaoCao(workbook, obj, query, 0);
			workbook.save(out);			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}

		
		
	}
	
	private void TaoBaoCao(com.aspose.cells.Workbook workbook,IcatalogList obj,String query,int sheetNum )throws Exception
	{
		try
		{
			com.aspose.cells.Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(sheetNum);
			com.aspose.cells.Cells cells = worksheet.getCells();
		
			com.aspose.cells.Cell cell = cells.getCell("B3");
			
			dbutils db = new dbutils();

			Hashtable<String,String> hashDienGiai = new Hashtable<String, String>();
			
			//ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			//cell = cells.getCell(4, 0 );cell.setValue("Ngày báo cáo: "+ getDateTime());
			
			//cell = cells.getCell(5, 0 );cell.setValue("Người tạo: "+ obj.getuserTen());
			
			
			System.out.println("query export bc "+query);
			ResultSet	rs = db.get(query);

			ResultSetMetaData rsmd = rs.getMetaData();
			int socottrongSql = rsmd.getColumnCount();

			System.out.println("so cot la "+socottrongSql);
				
			int countRow = 5;
			int column = -1;
			for( int i =1 ; i <=socottrongSql ; i ++  )
			{
				
				cell = cells.getCell(countRow, column + i );cell.setValue(rsmd.getColumnName(i));
				ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			

			}
			countRow ++;
				rs = db.get(query);
				int j=1;
			while(rs.next())
			{
				for(int i =1;i <=socottrongSql ; i ++)
				{
					Color c = Color.WHITE;
					cell = cells.getCell(countRow,column + i);
					
					
					{
						if(rsmd.getColumnType(i) == Types.DOUBLE || rsmd.getColumnType(i) == Types.INTEGER || rsmd.getColumnType(i) == Types.DECIMAL )
						{
							cell.setValue(rs.getDouble(i));
							ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, true, 41);
						}
						else
						{
							cell.setValue(rs.getString(i));
							ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, true, 0);
						}
					}
					
				}
				j++;
				++countRow;
			}
			
			if(rs!=null)rs.close();
			if(db!=null){
				db.shutDown();
			}


		}catch(Exception ex){

			ex.printStackTrace();
			throw new Exception("Qua trinh dien du lieu vao file Excel va tao PivotTable bi loi.!!!");
		}
	}
	public String getDateTime()
	{
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private void Delete(String nspId){
		
		dbutils db = new dbutils();
	    
	    String query = "";
	    try {
	    	db.getConnection().setAutoCommit(false);
			query = "delete catalog where pk_seq ="+nspId;

			if (!db.update(query))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				db.getConnection().setAutoCommit(true);
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
	    } catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}
		
	
	}
	
private void Chot(String nspId){
		
		dbutils db = new dbutils();
	    
	    String query;
	    
	    query = "update nhomsanphamchietkhau set trangthai = 1 where pk_seq ='" + nspId + "'";
	   	db.update(query);
	}

	/*private String getSearchQuery(ServletRequest request,IcatalogList obj){
		
		Utility util = new Utility();
		String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
    	if (diengiai == null)
    		diengiai = "";
    	obj.setDiengiai(diengiai);
    	String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
    	if (tungay == null)
    		tungay = "";    	
    	obj.setTungay(tungay);
    	String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
    	if (denngay == null)
    		denngay = "";    	
    	obj.setDenngay(denngay);
    	String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));   	
    	if (trangthai == null)
    		trangthai = "";   
    	
    	String lnhom = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("lnhom")));   	
    	if (lnhom == null)
    		lnhom = "";
    	obj.setLoainhom(lnhom);
	
    	if (trangthai.equals("2"))
    		trangthai = "";
    	obj.setTrangthai(trangthai);
    	String query = "select a.pk_seq, a.ten, d.diengiai loai, a.trangthai, a.ngaytao, a.ngaysua, \n" +
						"b.ten as nguoitao, c.ten as nguoisua from NhomSanPhamChietKhau a, LOAINHOMSANPHAM d, nhanvien b, nhanvien c \n" +
						"where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ and a.loai = d.pk_seq";
    	
    	if (diengiai.length()>0){
			query = query + " and upper(dbo.ftBoDau(a.ten)) like upper('%" + new geso.dms.distributor.util.Utility().replaceAEIOU(diengiai) + "%')";
			//obj.setDiengiai(diengiai);
    	}
    	
    	if (tungay.length() > 0) {
    		query = query + " and a.ngaytao >= '" + tungay + "'";
    		//obj.setTungay(tungay);
    	}
    	
    	if (denngay.length() > 0) {
    		query = query + " and a.ngaytao <= '" + denngay + "'";
    		//obj.setDenngay(denngay);
    	}
    	
    	if(trangthai.length() > 0){
    		query = query + " and a.trangthai = '" + trangthai + "'";
    		//obj.setTrangthai(trangthai);
    	}
    	
    	if(lnhom.length() > 0){
    		query = query + " and a.loai = '"+ lnhom +"'";
    	}
    	
    	return query;
	}*/
	public void importExcell(String userid,HttpServletRequest request, HttpServletResponse response,HttpSession session,String contentType,MultipartRequest multi ) throws ServletException, IOException, SQLException
	{
	
		IcatalogList obj=new catalogList();
		Enumeration files = multi.getFileNames();
		String filenameu = "";
		while (files.hasMoreElements())
		{
			String name = (String) files.nextElement();
			filenameu = multi.getFilesystemName(name);
			System.out.println("name  " + name);
			;
		}
	
		String filename = "C:\\java-tomcat\\catalog\\" + filenameu;
	
		if(filename.indexOf("xlsx")>=0)
		{
	
		}
	
		if (filename.length() > 0)
		{
			// doc file excel
			dbutils db =new dbutils();
			
			
			File file = new File(filename);
			System.out.println("filename  " + file);
			Workbook workbook;
			ResultSet rs = null;
	
			int indexRow = 6;
	
			try
			{
	
				System.out.println(file);
				workbook = Workbook.getWorkbook(file);
				Sheet sheet = workbook.getSheet(0);
				Cell[] cells = sheet.getRow(indexRow);
				cells = sheet.getRow(7);
				boolean loi = false;
				String values = "";
				String valuesDetail = "";
	
				int cot=0;
				db.getConnection().setAutoCommit(false);
				for (int i = indexRow ; i < sheet.getRows(); i++)
				{
					
					cells = sheet.getRow(i);
					if(cells.length > 0 && !loi)
					{
	
						String MA = getStringValue(cells,0);
						String ten=getStringValue(cells,1);
						String nhanhang = getStringValue(cells,2);
						String chungloai=getStringValue(cells,3);
						String ghichu = getStringValue(cells,4);
						String tenfile=getStringValue(cells,5);
						String query="select count(*) as sl from catalog where MA='"+MA.trim()+"'  ";
						 rs=db.get(query);
						 rs.next();
						 int sl=rs.getInt("sl");
						 rs.close();
						 if(sl>0)
						 {
								query = "update Catalog set nhanhang=N'"+nhanhang+"',duongdan=N'"+tenfile+"',ten = N'" + ten + "', chungloai_fk = N'" + chungloai + "',ghichu = N'" + ghichu + "', ngaysua = '" + getDateTime() + "', "+
										"nguoisua = '" + userid + "' where MA = '"+MA.trim()+"'";

								System.out.println("1.Insert Catalog: " + query);
								if (!db.update(query))
								{
									db.getConnection().rollback();
									db.shutDown();
									return;
								}
						 }
						 else
						 {
							 query = "insert into Catalog( ten,CHUNGLOai_fk,ghichu,duongdan,nguoitao,nguoisua,ngaytao,ngaysua,nhanhang,Ma) "
										+ " values(N'" + ten + "', N'" + chungloai + "', N'" + ghichu + "',N'"+tenfile+"', '" + userid + "', '" + userid + "','" + getDateTime() + "', '" + getDateTime() + "',N'"+nhanhang+"',N'"+MA+"')";
							 System.out.println("1.Insert Catalog: " + query);
								if (!db.update(query))
								{
									db.getConnection().rollback();
									db.shutDown();
									return;
								}
						 }
						
					}
	
				}
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
				
				db.shutDown();
			} catch (Exception er)
			{
				//db.getConnection().rollback();
				db.shutDown();
				er.printStackTrace();
				System.out.println(er.toString());
	
			}
	
		}
	
	
	}
	public String getStringValue(Cell[] cells,int vitri)
	{
		try
		{
			return cells[vitri].getContents().toString().replace(",", "");
		}
		catch (Exception e) {
			return "";
		}
	}

}