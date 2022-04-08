package geso.dms.center.servlets.chuyenngu;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.util.*;
import geso.dms.distributor.db.sql.dbutils;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import javax.servlet.http.HttpServlet;

import redis.clients.jedis.Jedis;

public class UploadChuyenNguSvl extends HttpServlet {
	static final long serialVersionUID = 1L;

	public UploadChuyenNguSvl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		session.setAttribute("userId", userId);
		String nextJSP = "/AHF/pages/Center/UploadChuyenNgu.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String contentType = request.getContentType();

		String userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")); 
		Utility util = new Utility();
 
		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0)) {

			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=KETQUACHUYENNGU.xls");
			WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());

			MultipartRequest multi = new MultipartRequest(request, "C:\\java-tomcat\\data\\", 20000000, "UTF-8");
			userId = util.antiSQLInspection(multi.getParameter("userId")); 

			Enumeration files = multi.getFileNames();
			String filenameu = "";
			while (files.hasMoreElements()) {
				String name = (String) files.nextElement();
				filenameu = multi.getFilesystemName(name);
				System.out.println("name :   " + name);

			}
			System.out.println("1____READ EXCEL TOI DAY, FILE NAME......" + filenameu);

			// String filename= "C:\\java-tomcat\\data\\"+ filenameu;

			String filename = "C:\\java-tomcat\\data\\" + filenameu;
			if (filenameu != null && filenameu.length() > 0) {
				// doc file excel
				File file = new File(filename);
				System.out.println("filename  " + file);
				Workbook workbook;
				Jedis jedis = null;
				try {
					jedis = new Jedis( ChuyenNgu.host,ChuyenNgu.port,ChuyenNgu.timeout * 50 );

					workbook = Workbook.getWorkbook(file);
					Sheet sheet =workbook.getSheet(0);
					
					// TAO RA 1 SHEET LUU THONG TIN UPLOAD KHONG THANH CONG
					WritableSheet sheetwrite = w.createSheet(sheet.getName(), 0);
					sheetwrite.addCell(new Label(0, 0, "key"));
					sheetwrite.addCell(new Label(1, 0, "vi"));
					sheetwrite.addCell(new Label(2, 0, "en"));
					sheetwrite.addCell(new Label(3, 0, "zh"));
					
					Cell[] cells;
					int indexRow = 1; 
					for (int i = indexRow; i < sheet.getRows(); i++) {
						cells = sheet.getRow(i); 
						if (cells.length > 0) { 
							if (cells[0] != null && cells[0].getContents().toString().length() > 0) {
								String key = getValue(sheet, 0, i);
								if (key.trim().length() > 0) {
									sheetwrite.addCell(new Label(0, indexRow, key));
									String vi = getValue(sheet, 1, i).trim().replace("'", "''");
									if (ChuyenNgu.checkExits(key, "vi",jedis)) {
										sheetwrite.addCell(new Label(1, indexRow, "đã có"));
										System.out.println(" Da co " + ChuyenNgu.get(key, "vi",jedis));
									} else {
										if (vi.trim().length() > 0) {
											sheetwrite.addCell(new Label(1, indexRow, "đã chuyển"));
											ChuyenNgu.set(key, "vi", vi);
											System.out.println(" Chen " + ChuyenNgu.get(key, "vi",jedis));
										} else
											sheetwrite.addCell(new Label(1, indexRow, "chưa chuyển"));
									}

									String en = getValue(sheet, 2, i).trim().replace("'", "''");
									if (ChuyenNgu.checkExits(key, "en",jedis)) {
										sheetwrite.addCell(new Label(2, indexRow, "đã có"));
										System.out.println(" Da co " + ChuyenNgu.get(key, "en",jedis));
									} else {
										if (en.trim().length() > 0) {
											sheetwrite.addCell(new Label(2, indexRow, "đã chuyển"));
											ChuyenNgu.set(key, "en", en);
											System.out.println(" Chen " + ChuyenNgu.get(key, "en",jedis));
										} else
											sheetwrite.addCell(new Label(2, indexRow, "chưa chuyển"));
									}
									String zh = getValue(sheet, 3, i).trim().replace("'", "''");
									if (ChuyenNgu.checkExits(key, "zh",jedis)) {
										sheetwrite.addCell(new Label(3, indexRow, "đã có"));
										System.out.println(" Da co " + ChuyenNgu.get(key, "zh",jedis));
									} else {
										if (zh.trim().length() > 0) {
											sheetwrite.addCell(new Label(3, indexRow, "đã chuyển"));
											ChuyenNgu.set(key, "zh", zh);
											System.out.println(" Chen " + ChuyenNgu.get(key, "zh",jedis));
										} else
											sheetwrite.addCell(new Label(3, indexRow, "chưa chuyển"));
									}
								}
							} 
						}
						indexRow++;

					}
					
					
					ChuyenNgu.Save(jedis);
					jedis.close();
					jedis = null;
				} catch (Exception ex) {
					ex.printStackTrace();
					if(jedis != null)
						jedis.close();
				}
			}
			w.write(); 
			try {
				w.close();
			} catch (WriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		session.setAttribute("userId", userId);
		String nextJSP = "/AHF/pages/Center/UploadChuyenNgu.jsp";
		response.sendRedirect(nextJSP);
	}

	private String getDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	String getValue(Sheet sheet, int col, int row) {
		return sheet.getCell(col, row).getContents().trim().replaceAll(",", "");
	}
}