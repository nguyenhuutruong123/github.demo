package geso.dms.center.servlets.report;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.omg.PortableServer.RequestProcessingPolicyOperations;

import com.aspose.cells.Cells;
import com.aspose.cells.Worksheets;

import geso.dms.center.beans.baocaokehoach.IBCKeHoach;
import geso.dms.center.beans.baocaokehoach.imp.BCKeHoach;
import geso.dms.distributor.util.Utility;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.BoldStyle;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.ScriptStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * Servlet implementation class BcKeHoachSvl
 */
@WebServlet("/BcKeHoachSvl")
public class BcKeHoachSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BcKeHoachSvl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		
		IBCKeHoach obj = new BCKeHoach();
		obj.setUserId(userId);
		obj.setUserTen(userTen);
		obj.creates();
		
		session.setAttribute("obj", obj);
		String nextJSP = "/AHF/pages/Center/BCKeHoach.jsp";
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
		
		String nextJSP = "";
		
		Utility util = new Utility();
		
		String gsbhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("gsbhId")))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("gsbhId"))):"";
		String khuvucId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId"))):"";
		String vungId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"))):"";
		String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"))):"";
		String thang = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("month")));
		String nam = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("year")));
		
		System.out.println("GSBH: "+gsbhId);
		System.out.println("KV: "+khuvucId);
		System.out.println("VUNG: "+vungId);
		System.out.println("NPPID: "+nppId);
		System.out.println("THANG: "+thang);
		System.out.println("NAM: "+nam);
		
		IBCKeHoach baocao = new BCKeHoach();
		baocao.setGsbanhangId(gsbhId);
		baocao.setKhuvucId(khuvucId);
		baocao.setVungId(vungId);
		baocao.setNppId(nppId);
		baocao.setMonth(thang);
		baocao.setYear(nam);

		// lấy dữ liệu, kiểm tra có bao nhiều người giám sát thì tạo bấy nhiêu sheet
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		System.out.println(action);
		
		if(action.equals("taobaocao")) {
			// test
			//testReport(response);
			System.out.println("");
			String temp = "";
			baocao.init();
		    ResultSet result = baocao.getResultList();
		    int sheetIndex = 0;
		    int dataIndex = 8;
		    try {
		    	if(result != null && result.isBeforeFirst()) {
		    		WritableWorkbook workbook = null;
					String ten = "test";
					response.setContentType("application/vnd.ms-excel");
					response.setHeader("Content-Disposition", "attachment; filename=MCP_"+ten+".xls");
					
					workbook = Workbook.createWorkbook(response.getOutputStream());
					
					WritableSheet excelSheet = null;
					
					// Format cell data
					WritableFont dataFont = new WritableFont(WritableFont.TIMES, 11);
					dataFont.setColour(Colour.BLACK);
					WritableCellFormat dataCellFormat = new WritableCellFormat(dataFont);
					dataCellFormat.setWrap(true);
					dataCellFormat.setAlignment(Alignment.CENTRE);
					dataCellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
					dataCellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
					dataCellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
					// Small title
					WritableFont sTitleFont = new WritableFont(WritableFont.TIMES, 12);
					sTitleFont.setColour(Colour.BLACK);
					WritableCellFormat sTitleFormat = new WritableCellFormat(sTitleFont);
					// Format big title
					WritableFont titleFont = new WritableFont(WritableFont.TIMES, 18);
					titleFont.setColour(Colour.BLACK);
					titleFont.setBoldStyle(WritableFont.BOLD);
					WritableCellFormat titleFormat = new WritableCellFormat(titleFont);
					// Format header
					WritableFont headerFont = new WritableFont(WritableFont.TIMES, 12);
					headerFont.setColour(Colour.BLACK);
					headerFont.setBoldStyle(WritableFont.BOLD);
					WritableCellFormat headerFormat = new WritableCellFormat(headerFont);
					headerFormat.setBackground(jxl.format.Colour.AQUA);
					headerFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
					headerFormat.setAlignment(Alignment.CENTRE);
					headerFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
					headerFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
					headerFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
					headerFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK); 
					// Tạo đường kẻ đậm đánh dấu kết thúc
					WritableCellFormat endCellFormat = new WritableCellFormat();
		    		endCellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
		    		endCellFormat.setAlignment(Alignment.CENTRE);
		    		endCellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
					int rowHeight = 700;
					int columnWidth = 20;
					
		    		while(result.next()) {
						if(!result.getString("tengsbh").equals(temp)) {
							if(sheetIndex > 0) {
								dataIndex++;
					    		excelSheet.addCell(new Label(0, dataIndex, "", endCellFormat));
								excelSheet.addCell(new Label(1, dataIndex, "", endCellFormat));
								excelSheet.addCell(new Label(2, dataIndex, "", endCellFormat));
								excelSheet.addCell(new Label(3, dataIndex, "", endCellFormat));
								excelSheet.addCell(new Label(4, dataIndex, "", endCellFormat));
								excelSheet.addCell(new Label(5, dataIndex, "", endCellFormat));
								excelSheet.addCell(new Label(6, dataIndex, "", endCellFormat));
								excelSheet.addCell(new Label(7, dataIndex, "", endCellFormat));
								excelSheet.addCell(new Label(6, dataIndex, "", endCellFormat));
								excelSheet.addCell(new Label(7, dataIndex, "", endCellFormat));
							}
							temp = result.getString("tengsbh");
							dataIndex = 8;
							
							excelSheet = workbook.createSheet(result.getString("tengsbh"), sheetIndex);
							
							// Thiết lập width cho từng column
							excelSheet.setColumnView(0, 5);
							excelSheet.setColumnView(1, columnWidth);
							excelSheet.setColumnView(2, columnWidth);
							excelSheet.setColumnView(3, columnWidth);
							excelSheet.setColumnView(4, 50);
							excelSheet.setColumnView(5, columnWidth);
							excelSheet.setColumnView(6, 30);
							excelSheet.setColumnView(7, columnWidth);
							
							// Thiệt lập height cho row
							excelSheet.setRowView(7, rowHeight);
							//excelSheet.setRowView(dataIndex, rowHeight);
							
							excelSheet.addCell(new Label(2, 0, "LỊCH TRÌNH CÔNG TÁC (GIÁM SÁT BÁN HÀNG)", titleFormat));
							excelSheet.addCell(new Label(2, 3, "Tên GSBH   :" + result.getString("tengsbh"), sTitleFormat));
							//excelSheet.addCell(new Label(4, 3, ":"+result.getString("diachi"), sTitleFormat));
							//excelSheet.addCell(new Label(3, 4, "Mã GSBH   :"+gsbhId, sTitleFormat));
							
							//Add header
							excelSheet.addCell(new Label(0, 7, "Thứ", headerFormat));
							excelSheet.addCell(new Label(1, 7, "Ngày", headerFormat));
							excelSheet.addCell(new Label(2, 7, "Loại", headerFormat));
							excelSheet.addCell(new Label(3, 7, "Hạng mục", headerFormat));
							excelSheet.addCell(new Label(4, 7, "Địa chỉ", headerFormat));
							excelSheet.addCell(new Label(5, 7, "Ghi chú kế hoạch", headerFormat));
							excelSheet.addCell(new Label(6, 7, "Thực hiện", headerFormat));
							excelSheet.addCell(new Label(7, 7, "Ghi chú", headerFormat));
							
							//Add data
							excelSheet.addCell(new Label(0, dataIndex, result.getString("thu"), dataCellFormat));
							excelSheet.addCell(new Label(1, dataIndex, formatDDMMYY(result.getString("ngay")), dataCellFormat));
							excelSheet.addCell(new Label(2, dataIndex, result.getString("loai"), dataCellFormat));
							excelSheet.addCell(new Label(3, dataIndex, result.getString("hangmuc"), dataCellFormat));
							excelSheet.addCell(new Label(4, dataIndex, result.getString("diachi"), dataCellFormat));
							excelSheet.addCell(new Label(5, dataIndex, result.getString("ghichu"), dataCellFormat));
							excelSheet.addCell(new Label(6, dataIndex, "", dataCellFormat));
							excelSheet.addCell(new Label(7, dataIndex, "", dataCellFormat));
							
							sheetIndex++;
						}
						else {
							dataIndex++;
							
							excelSheet.addCell(new Label(0, dataIndex, result.getString("thu"), dataCellFormat));
							excelSheet.addCell(new Label(1, dataIndex, formatDDMMYY(result.getString("ngay")), dataCellFormat));
							excelSheet.addCell(new Label(2, dataIndex, result.getString("loai"), dataCellFormat));
							excelSheet.addCell(new Label(3, dataIndex, result.getString("hangmuc"), dataCellFormat));
							excelSheet.addCell(new Label(4, dataIndex, result.getString("diachi"), dataCellFormat));
							excelSheet.addCell(new Label(5, dataIndex, result.getString("ghichu"), dataCellFormat));
							excelSheet.addCell(new Label(6, dataIndex, "", dataCellFormat));
							excelSheet.addCell(new Label(7, dataIndex, "", dataCellFormat));
							//excelSheet.setRowView(dataIndex, rowHeight);
						}
					}
		    		
		    		// Tạo đường kẻ đậm đánh dấu kết thúc
		    		dataIndex++;
		    		excelSheet.addCell(new Label(0, dataIndex, "", endCellFormat));
					excelSheet.addCell(new Label(1, dataIndex, "", endCellFormat));
					excelSheet.addCell(new Label(2, dataIndex, "", endCellFormat));
					excelSheet.addCell(new Label(3, dataIndex, "", endCellFormat));
					excelSheet.addCell(new Label(4, dataIndex, "", endCellFormat));
					excelSheet.addCell(new Label(5, dataIndex, "", endCellFormat));
					excelSheet.addCell(new Label(6, dataIndex, "", endCellFormat));
					excelSheet.addCell(new Label(7, dataIndex, "", endCellFormat));
					excelSheet.addCell(new Label(6, dataIndex, "", endCellFormat));
					excelSheet.addCell(new Label(7, dataIndex, "", endCellFormat));
					
		    		if(workbook != null) {
		    			
			    		workbook.write();
			    		workbook.close();
			    		
			    	}
		    		
			    }
		    	else {
		    		System.out.println("Thông báo: ResultSet-> row = 0");
		    		baocao.setMsg("Không tìm thấy dữ liệu để xuất báo cáo");
		    		baocao.creates();
			    	session.setAttribute("obj", baocao);
					nextJSP = "/AHF/pages/Center/BCKeHoach.jsp";
					response.sendRedirect(nextJSP);
		    	}
		    	
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RowsExceededException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (WriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		}
		
	}
	
	
	
	String formatDDMMYY(String str) {
		String result = "";
		if(!str.equals("")) {
			String[] temp = str.trim().split("-");
			if(temp.length == 3) {
				result = temp[2] + "/" + temp[1] + "/" + temp[0];
			}
		}
		
		return result;
	}

}
