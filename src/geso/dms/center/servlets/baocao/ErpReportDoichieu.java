package geso.dms.center.servlets.baocao;

import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.Utility;
import geso.dms.center.beans.baocao.IBaocao;
import geso.dms.center.beans.baocao.imp.Baocao;
import geso.dms.center.db.sql.dbutils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
//import com.itextpdf.text.log.SysoLogger;

public class ErpReportDoichieu extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpReportDoichieu() {
        super();
    } 
    
    NumberFormat formatter = new DecimalFormat("#,###,###");
    NumberFormat formatter2 = new DecimalFormat("#,###,###.#######");
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IBaocao obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    obj = new Baocao();
	    obj.setUserId(userId);
	    obj.Init_ReportDoichieu();
		session.setAttribute("obj", obj);
				
		String nextJSP = "/AHF/pages/Center/ErpReportDoichieu.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		OutputStream out = response.getOutputStream(); 
		IBaocao obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    String userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));

	    obj = new Baocao();
	    obj.setUserId(userId);
	    //obj.setNppId((String)session.getAttribute("nppId"));
	    
	    String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
	    if(nppId == null)
	    	nppId = "";
	    obj.setNppId(nppId);
	    
	    String tungay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"));
	    if(tungay == null)
	    	tungay = "";
	    obj.setTuNgay(tungay);
	    
	    String denngay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay"));
	    if(denngay == null)
	    	denngay = "";
	    obj.setDenNgay(denngay);
	 
	    String reportname = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("reportname"));
	    if(reportname == null)
	    	reportname = "";
	    obj.setReportName(reportname);
	    
	    String url = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("url"));
	    if(url == null)
	    	url = "";
	    obj.setUrl(url);
	    
	    String password = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("password"));
	    if(password == null)
	    	password = "";
	    obj.setpassword(password);
	    
	    String username = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("username"));
	    if(username == null)
	    	username = "";
	    obj.setUsername(username);
	    
	    String pk_sequlr = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("pk_sequlr"));
	    if(pk_sequlr == null)
	    	pk_sequlr = "";
	    obj.setPk_seqUrl(pk_sequlr);
	    
	    
	    
	    
	    obj.Init_ReportDoichieu();
	    
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    //System.out.println("Action nhan duoc: " + action + " -- LOAI SP: " + obj.getLoaiSanPhamIds() );
	    
	    if(action.equals("search")||action.equals("reload") )
	    {
	    	 
	    	session.setAttribute("obj", obj);
			
			String nextJSP = "/AHF/pages/Center/ErpReportDoichieu.jsp";
			response.sendRedirect(nextJSP);
	    }
	    else
	    {
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "Attachment; filename=Report(NPP).xlsm");
			 
			try
			{
				CreatePivotTable(out, response, request, obj); 
				
			} catch (Exception ex)
			{
				obj.setMsg(ex.getMessage());
				request.getSession().setAttribute("errors", ex.getMessage());
			 
				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);
				
				String nextJSP = "/AHF/pages/Center/ErpReportDoichieu.jsp";
				response.sendRedirect(nextJSP);
				
			}
		}
	}
 
	private void CreatePivotTable(OutputStream out, HttpServletResponse response, HttpServletRequest request,  IBaocao obj) throws Exception
	{
		try
		{
			
			String strfstream = getServletContext().getInitParameter("path") + "\\ReportFromStoreProc(NPP).xlsm";
			 
			FileInputStream fstream = new FileInputStream(strfstream);
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			Worksheets worksheets = workbook.getWorksheets();
	  		Worksheet worksheet_2 = worksheets.getSheet("sheet1");
	  		worksheet_2.setName("REPORT_"+obj.getReportName());
	  		 Cells cells = worksheet_2.getCells();
			   
	  		 	Cell	cell = cells.getCell("P1");
				Style style1=cell.getStyle();
				dbutils db = new dbutils(); 
				
				String tenbc="";
		  		String query="SELECT TENCHUCNANG FROM ERP_BAOCAODOICHIEU WHERE TEN_THUTUC='"+obj.getReportName()+"'";
		  		 ResultSet rsname=db.get(query);
		  		 if(rsname.next()){
		  			tenbc=rsname.getString("TENCHUCNANG");
		  		 }
		  		rsname.close();
		  		
		  		
				
				 
				
				db = new dbutils();
				String[] param = new String[3];
			
				param[0] = obj.getTuNgay().equals("") ? null : obj.getTuNgay();
				param[1] = obj.getDenNgay().equals("") ? null : obj.getDenNgay();
				param[2] = obj.getNppId().equals("") ? null : obj.getNppId();
				
				 System.out.println("NPP_FK : "+obj.getNppId());
				 
				  query=" SELECT * FROM "+obj.getReportName()+" ("+param[2]+",'"+obj.getTuNgay()+"','"+obj.getDenNgay()+"' ) ";
				 System.out.println("query : "+query);
				 if(obj.getReportName().equals("ufn_get_log_kho")){
					 query+=" order by ngaygiothuchien desc";
				 }
				ResultSet	rs = db.get(query);
		  		worksheets = workbook.getWorksheets();
		  		
		  		 
		  		this.TaoBaoCao(db,rs,worksheet_2, obj,tenbc,style1);
		  		 
		  	 
			workbook.save(out);
			fstream.close();
		} catch (Exception ex)
		{
			ex.printStackTrace();
			throw new Exception("Error Message: " + ex.getMessage());
		}
	}
	public String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		return dateFormat.format(date);
	}
	private void TaoBaoCao(dbutils db,ResultSet rs,Worksheet worksheet, IBaocao obj, String diengiai, Style style12) 
	{
		  try{
			  
			   Cells cells = worksheet.getCells();
			   
			   for(int i=0;i<30;i++){
				   cells.setColumnWidth(i, 20.0f);   
			   }
			
			    cells.setRowHeight(0, 50.0f);
			    Cell cell = cells.getCell("A1");
			    ReportAPI.getCellStyle(cell, Color.RED, true, 14, diengiai);
			    
			    cell = cells.getCell("A3");
				ReportAPI.getCellStyle(cell, Color.NAVY, true, 10, "Từ ngày : " + obj.getTuNgay() + "   Đến ngày: " + obj.getDenNgay());
				cell = cells.getCell("A4");
				ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày tạo: " +this.getDateTime() );
				
				cell = cells.getCell("B4");
				ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Tạo bởi : " + obj.getUserTen());
				
				cell = cells.getCell("AH2");
				Style style1=cell.getStyle();
				

			   worksheet.setGridlinesVisible(false);
			   
			 
			   
			   ResultSetMetaData rsmd = rs.getMetaData();
			   int socottrongSql = rsmd.getColumnCount();
			   
			   int countRow = 4;

			   for( int i =1 ; i <=socottrongSql ; i ++  )
			   {
			    cell = cells.getCell(countRow,i-1);
			    
		    	ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
		    	
		    	ReportAPI.getCellStyle(cell, Color.WHITE, true, 9, rsmd.getColumnName(i));
		    	
			   }
			   countRow ++;
			   
			   NumberFormat formatter = new DecimalFormat("#,###,###");
			   while(rs.next())
			   {
				   Color color_=Color.BLACK;
				     /* if(rs.getDouble(2) <rs.getDouble(1) &&  rs.getDouble(20) != 0  ){
				    	  color_=Color.RED;
				      }*/
			    for(int i = 1; i <= socottrongSql; i ++)
			    {
			     
			     cell = cells.getCell(countRow,i-1);
			     if(rsmd.getColumnType(i) == Types.DOUBLE || rsmd.getColumnType(i)==Types.NUMERIC || rsmd.getColumnType(i)==Types.INTEGER )
			     {
			    
			    	 cell.setValue(rs.getDouble(i));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
			   
			     }
			     else
			     {
			    	 ReportAPI.getCellStyle(cell ,color_, false, 9, rs.getString(i));
			   
			     }
			    }
			    ++countRow;
			   }
			   
			   if(rs!=null)rs.close();
			    

			 
			  }catch(Exception ex){
				  ex.printStackTrace();
				    
			  }
	}

	 
 
}
