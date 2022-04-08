package geso.dms.center.servlets.reports;

import geso.dms.center.beans.report.IBcongnoKHIP;
import geso.dms.center.beans.report.imp.BccongnoKHIP;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.OracleConnUtils;
import geso.dms.center.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

/**
 * Servlet implementation class BaocaocongnoIPSvl
 */
@WebServlet("/BaocaocongnoIPSvl")
public class BaocaocongnoIPSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BaocaocongnoIPSvl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IBcongnoKHIP obj = new BccongnoKHIP();
		String querystring = request.getQueryString();
		Utility util = new Utility();
		
		obj.setUserId(util.getUserId(querystring));
		obj.setUserTen((String) session.getAttribute("userTen"));
			
		obj.init("");
		session.setAttribute("obj", obj);
		String nextJSP = "/AHF/pages/Center/CongNoIP.jsp";
		response.sendRedirect(nextJSP);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IBcongnoKHIP obj = new BccongnoKHIP();
		OutputStream out = response.getOutputStream();
		Utility util = new Utility();
		
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		
		
		
		obj.setUserId(userId!=null? userId:"");
		obj.setUserTen(userTen!=null? userTen:"");	
		
		
		
		String action = (String) geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		
		
		
		 String nextJSP = "/AHF/pages/Center/CongNoIP.jsp";
		
		
		
		try{
					
					if (action.equals("excel")) {			
				
						
						response.setContentType("application/xlsm");
						response.setHeader("Content-Disposition", "attachment; filename=CongNoKHIP.xlsm");
						
						
				        if(!ExportToExcel(out,obj)){
				        	response.setContentType("text/html");
				            PrintWriter writer = new PrintWriter(out);
				            writer.print("Xin loi khong co bao cao trong thoi gian nay");
				            writer.close();
				        }
					}else{
						response.sendRedirect(nextJSP);
					}
					
				}catch(Exception ex){
					obj.setMsg(ex.getMessage());
					response.sendRedirect(nextJSP);
				}
		
		
		
	}
	
	private boolean ExportToExcel(OutputStream out, IBcongnoKHIP obj)throws Exception {
		
		
		/*String chuoi=getServletContext().getInitParameter("path") + "\\CongNoKHIP.xlsm";
		FileInputStream fstream = new FileInputStream(chuoi);	*/
		File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\CongNoKHIP.xlsm");
		FileInputStream fstream = new FileInputStream(f) ;
		Workbook workbook = new Workbook();
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			CreateHeader(workbook, obj);
			workbook.save(out);
			fstream.close();
			return true;	
		}
	
	private void CreateHeader(Workbook workbook, IBcongnoKHIP obj)throws Exception {
		try{
			
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Sheet1");

			Cells cells = worksheet.getCells();
			cells.setRowHeight(0, 20.0f);
			Cell cell = cells.getCell("A1");
			
			ReportAPI.getCellStyle(cell, Color.RED, true, 16,
					"Báo Cáo Công Nợ Khách Hang IP ");
		
			cell = cells.getCell("A3");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 10, "Ngày tạo : "
					+  obj.getDateTime());
			cell = cells.getCell("A4");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 10, "Người tạo : "
					+ obj.getUserTen());
			ResultSet congnoRs = null;
			try
			{
				
				OracleConnUtils SYNC = new OracleConnUtils();
		
				
					String query = "select * from apps.SGP_CUS_DEBIT ";
					
					congnoRs = SYNC.get(query);
					
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			
			ResultSetMetaData rsmd = congnoRs.getMetaData();
			int socottrongSql = rsmd.getColumnCount();
			
			int countRow =5;

			for( int i =1 ; i <=socottrongSql ; i ++  )
			{
				cell = cells.getCell(countRow,i-1 );cell.setValue(rsmd.getColumnName(i));
				cells.setColumnWidth(i, 20.0f);
				ReportAPI.setCellBackground(cell, new Color(70,130,180), BorderLineType.THIN, true, 0);	
			 
			}
			countRow ++;
			
			while(congnoRs.next())
			{
				
			
			
				for(int i =1;i <=socottrongSql ; i ++)
				{
					cell = cells.getCell(countRow,i-1 );
					
							
							
							cell.setValue(congnoRs.getString(i));
							
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
				}
				++countRow;
			}
			
				
		}catch(Exception ex){
			throw new Exception("Khong tao duoc Header cho bao cao");
		}
		
		
		
	}

}
