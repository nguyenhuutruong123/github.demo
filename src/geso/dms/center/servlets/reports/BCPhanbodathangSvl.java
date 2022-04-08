
package geso.dms.center.servlets.reports;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.distributor.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;


public class BCPhanbodathangSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public BCPhanbodathangSvl() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
		{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			HttpSession session = request.getSession();
			IStockintransit obj = new Stockintransit();
			String querystring = request.getQueryString();
			Utility util = new Utility();
			
			obj.setuserId(util.getUserId(querystring));
			obj.setuserTen((String) session.getAttribute("userTen"));
			
			obj.setdiscount("1");
			obj.setvat("1");
			obj.init();
			
			session.setAttribute("obj", obj);
			String nextJSP = "/AHF/pages/Center/BCPhanbodathang.jsp";
			response.sendRedirect(nextJSP);
		}

		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
		{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			HttpSession session = request.getSession();
			IStockintransit obj = new Stockintransit();
			OutputStream out = response.getOutputStream();
			
			String userId = (String) session.getAttribute("userId");
			String userTen = (String) session.getAttribute("userTen");
			Utility util = new Utility();
						
			obj.setuserId(userId!=null? userId:"");
			obj.setuserTen(userTen!=null? userTen:"");
			
			obj.settungay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays"))));
			obj.setdenngay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays"))));
			
			obj.setkenhId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")))!=null?
					util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")):""));
			
			obj.setvungId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")))!=null?
					util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")):""));
				
			obj.setkhuvucId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")))!=null?
					util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")):""));
					
			obj.setnppId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")))!=null?
					util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")):""));
						
			String nspId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nspId"))!=null?util.antiSQLInspection(request.getParameter("nspId"))):"";
			obj.SetNhoSPId(nspId);
								
			geso.dms.center.util.Utility utilcenter = new geso.dms.center.util.Utility();
			
			String sql = " where npp.pk_seq in " + utilcenter.quyen_npp(obj.getuserId()) + " and kbh.pk_seq in  " + utilcenter.quyen_kenh(obj.getuserId());

			if (obj.getkenhId().length() > 0)
				sql = sql + " and kbh.pk_seq ='" + obj.getkenhId() + "'";

			if (obj.getvungId().length() > 0)
				sql = sql + " and v.pk_seq ='" + obj.getvungId() + "'";
			
			if (obj.getkhuvucId().length() > 0)
				sql = sql + " and kv.pk_seq ='" + obj.getkhuvucId() + "'";
			
			if (obj.getnppId().length() > 0)
				sql = sql + " and npp.pk_seq ='" + obj.getnppId() + "'";

			if(obj.gettungay().length() > 0)
				sql = sql + " AND PBDH.TUNGAY >= '" + obj.gettungay() + "'";
			
			if(obj.getdenngay().length() > 0)
				sql = sql + " AND PBDH.DENNGAY <= '" + obj.getdenngay() + "'";
			
			System.out.println("SQL la: " + sql + "\n");
			
			String action = (String) util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")));
			String nextJSP = "/AHF/pages/Center/BCPhanbodathang.jsp";
			
			System.out.println("Action la: " + action);
			
			try{
				if (action.equals("Taomoi")) 
				{			
					response.setContentType("application/xlsm");
					response.setHeader("Content-Disposition", "attachment; filename=BCPhanbodathang.xlsm");
					
					String query = setQuery(sql, obj);
					System.out.println("Query lay du lieu: " + query + "\n");
			        
					if(!CreatePivotTable(out, obj, query))
			        {
			        	response.setContentType("text/html");
			            PrintWriter writer = new PrintWriter(out);
			            writer.print("Khong tim thay du lieu");
			            writer.close();
			        }
				}
				else
				{
					response.sendRedirect(nextJSP);
				}
			}
			catch(Exception ex)
			{
				System.out.println("Xay ra exception roi..." + ex.toString());
				obj.setMsg(ex.getMessage());
				response.sendRedirect(nextJSP);
			}
			obj.init();
			session.setAttribute("obj", obj);	
		}	
		
		private String setQuery(String sql, IStockintransit obj) {
			String	query = "SELECT	KBH.TEN AS KBH, VUNG.TEN AS CHINHANH, KV.TEN AS KV, " + 
							"NPP.PK_SEQ AS NPPID, NPP.TEN AS NPP, " +
							"ISNULL(NSP.DIENGIAI,'KHONG XAC DINH') AS NSP, " +	   
							"SP.MA AS SPID, SP.TEN AS SP, " +
							"PBDH.SOLUONG AS PHANBO, " +
							"SUM(ISNULL(THUCHIEN.THUCHIEN,0)) AS THUCHIEN " +
							"FROM PHANBODATHANG PBDH " +
							"INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = PBDH.NPP_FK " +
							"INNER JOIN SANPHAM SP ON SP.PK_SEQ = PBDH.SANPHAM_FK " +
							"INNER JOIN KHUVUC KV ON KV.PK_SEQ = NPP.KHUVUC_FK " +
							"INNER JOIN VUNG VUNG ON VUNG.PK_SEQ = KV.VUNG_FK " +
							"INNER JOIN NHAPP_KBH NPP_KBH ON NPP_KBH.NPP_FK = NPP.PK_SEQ " +
							"INNER JOIN KENHBANHANG KBH ON KBH.PK_SEQ = NPP_KBH.KBH_FK " +
							"LEFT JOIN NHOMSANPHAM_SANPHAM NSP_SP ON NSP_SP.SP_FK = SP.PK_SEQ " ; 							
			
			if(obj.GetNhoSPId().length() > 0){
				query += 	"AND NSP_SP.SP_FK = '" + obj.GetNhoSPId() + "'" ;
			}else{
				query += 	"AND NSP_SP.SP_FK IN (SELECT TOP(1) NSP_FK FROM NHOMSANPHAM_SANPHAM WHERE SP_FK = SP.PK_SEQ) " ;
			}
				query += 	"LEFT JOIN NHOMSANPHAM NSP ON NSP.PK_SEQ = NSP_SP.NSP_FK " +
							"LEFT JOIN " +
							"( " +
							"SELECT DDH.NPP_FK AS NPPID, DDH_SP.SANPHAM_FK AS SPID, DDH.NGAYDAT AS NGAYDAT, SUM(DDH_SP.SOLUONG) AS THUCHIEN " +
							"FROM DONDATHANG_SP DDH_SP " +
							"INNER JOIN	DONDATHANG DDH ON DDH.PK_SEQ = DDH_SP.DONDATHANG_FK " +
							"WHERE DDH.TRANGTHAI >=1 " +
							"GROUP BY DDH.NPP_FK, DDH_SP.SANPHAM_FK, DDH.NGAYDAT " +
						")THUCHIEN ON THUCHIEN.NPPID = NPP.PK_SEQ AND THUCHIEN.SPID = SP.PK_SEQ AND THUCHIEN.NGAYDAT >= PBDH.TUNGAY AND THUCHIEN.NGAYDAT <= PBDH.DENNGAY " ;
			
			query = query + sql;
			query = query + " GROUP BY KBH.TEN, VUNG.TEN, KV.TEN, NPP.PK_SEQ , NPP.TEN, ISNULL(NSP.DIENGIAI,'KHONG XAC DINH'), SP.MA, SP.TEN, PBDH.SOLUONG" ;
		    return query;			
			
		}	
		
		private boolean CreatePivotTable(OutputStream out, IStockintransit obj, String query)throws Exception 
		{
			boolean isFillData = false;
			
			//FileInputStream fstream = null;
			
			Workbook workbook = new Workbook();

			//fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BCPhanbodathang.xlsm");
			//fstream = new FileInputStream("D:\\Project\\ICP\\Best\\WebContent\\pages\\Templates\\BCPhanbodathang.xlsm");
			File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\BCPhanbodathang.xlsm");
			FileInputStream fstream = new FileInputStream(f) ;
						
			workbook.open(fstream);
			
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

			CreateHeaderLevel(workbook,obj);
			
			isFillData = FillDataLevel(workbook, obj, query);			

			if (!isFillData){
				if(fstream != null) fstream.close();
				return false;
			}
			
			workbook.save(out);
			fstream.close();
			return true;	
		}
		
		
		private void CreateHeaderLevel(Workbook workbook, IStockintransit obj)throws Exception{
			try{
		 		Worksheets worksheets = workbook.getWorksheets();
				Worksheet worksheet = worksheets.getSheet(0);
				worksheet.setName("Sheet1");
				Cells cells = worksheet.getCells();
				
			    Style style;
			    Font font = new Font();
			    font.setColor(Color.RED);//mau chu
			    font.setSize(16);// size chu
			   	font.setBold(true);
			   	
			    cells.setRowHeight(0, 20.0f);
			    Cell cell = cells.getCell("A1");
			    style = cell.getStyle();
			    style.setFont(font); 
			    style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu 	        
			    
			    String tieude = "BÁO CÁO PHÂN BỔ ĐẶT HÀNG";

			    ReportAPI.getCellStyle(cell,Color.RED, true, 14, tieude);
			    cells.setRowHeight(3, 18.0f);
			    cell = cells.getCell("A3");
			    
		    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Từ ngày : " + obj.gettungay() + "" );
			    
			    cells.setRowHeight(3, 18.0f);
			    cell = cells.getCell("B3"); 
		    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Đến ngày : " + obj.getdenngay() + "" );
			            
	    			
			    cells.setRowHeight(4, 18.0f);
			    cell = cells.getCell("A4");
			    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
			    
			    cells.setRowHeight(5, 18.0f);
			    cell = cells.getCell("A5");
			    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getuserTen());

				
				cell = cells.getCell("DA1");		cell.setValue("KenhBanHang");
				cell = cells.getCell("DB1");		cell.setValue("ChiNhanh");
				cell = cells.getCell("DC1");		cell.setValue("KhuVuc");
				cell = cells.getCell("DD1");		cell.setValue("NhaPhanPhoi");		
				cell = cells.getCell("DE1");		cell.setValue("MaNhaPhanPhoi"); //cell.setValue("Smart Id"); 
				cell = cells.getCell("DF1");		cell.setValue("TenSanPham");
				cell = cells.getCell("DG1");		cell.setValue("MaSanPham");
				cell = cells.getCell("DH1");		cell.setValue("NhomSanPham");			
				cell = cells.getCell("DI1");		cell.setValue("PhanBo");
				cell = cells.getCell("DJ1");		cell.setValue("ThucHien");
				cell = cells.getCell("DK1");		cell.setValue("ConLai");
				cell = cells.getCell("DL1");		cell.setValue("%");
			}catch(Exception ex){
				throw new Exception("Xin loi,Khong tao duoc header cho bao cao");
			}
			
		}
		
		private boolean FillDataLevel(Workbook workbook, IStockintransit obj, String query) throws Exception{
			
			dbutils db = new dbutils();
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			Cells cells = worksheet.getCells();		
			ResultSet rs = db.get(query);
			int i = 2;		
			if (rs != null) {
				try {
					Cell cell = null;
					cells.setColumnWidth(0, 15.0f);
					cells.setColumnWidth(1, 15.0f);
					cells.setColumnWidth(2, 15.0f);
					cells.setColumnWidth(3, 15.0f);
					cells.setColumnWidth(4, 15.0f);			
					cells.setColumnWidth(5, 15.0f);	
					cells.setColumnWidth(6, 15.0f);	
					cells.setColumnWidth(7, 15.0f);
					cells.setColumnWidth(8, 15.0f);
					cells.setColumnWidth(9, 15.0f);
					cells.setColumnWidth(10, 20.0f);
					cells.setColumnWidth(11, 20.0f);
					cells.setColumnWidth(12, 20.0f);
					
					while (rs.next()) {					
						String KBH = rs.getString("KBH");
						String CHINHANH = rs.getString("CHINHANH");
						String KV = rs.getString("KV");
						String NPPID = rs.getString("NPPID");
						String NPP = rs.getString("NPP");				
						String SP = rs.getString("SP");
						String SPID = rs.getString("SPID");					
						String NSP = rs.getString("NSP");
						float PHANBO = rs.getFloat("PHANBO");
						float THUCHIEN = rs.getFloat("THUCHIEN");
						float CONLAI = PHANBO - THUCHIEN;
						float PHANTRAM=0;
						if(PHANBO>0){
						
							 PHANTRAM = 100*THUCHIEN/PHANBO;
						}
													
						
						cell = cells.getCell("DA" + Integer.toString(i));	cell.setValue(KBH);
						cell = cells.getCell("DB" + Integer.toString(i));	cell.setValue(CHINHANH);
						cell = cells.getCell("DC" + Integer.toString(i));	cell.setValue(KV);
						cell = cells.getCell("DD" + Integer.toString(i));	cell.setValue(NPP);					
						cell = cells.getCell("DE" + Integer.toString(i));	cell.setValue(NPPID);					
						cell = cells.getCell("DF" + Integer.toString(i));	cell.setValue(SP);
						cell = cells.getCell("DG" + Integer.toString(i));	cell.setValue(SPID);
						cell = cells.getCell("DH" + Integer.toString(i));	cell.setValue(NSP);
						cell = cells.getCell("DI" + Integer.toString(i));	cell.setValue(PHANBO);
						cell = cells.getCell("DJ" + Integer.toString(i));	cell.setValue(THUCHIEN);	
						cell = cells.getCell("DK" + Integer.toString(i));	cell.setValue(CONLAI);
						cell = cells.getCell("DL" + Integer.toString(i));	cell.setValue(PHANTRAM);	
						++i;					
					}
					if (rs != null) rs.close();
					
					if(db != null) 	db.shutDown();
					
					if(i==2){					
						throw new Exception("Chưa có dữ liệu báo cáo trong thời gian này.");
					}
					System.out.print("I = " + i);
					
				}catch(Exception ex){
					throw new Exception(ex.getMessage());
				}
			}else{
				return false;
			}		
			return true;
		}		
		
		
	}
