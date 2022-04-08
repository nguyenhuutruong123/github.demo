package geso.dms.center.servlets.dangkykhuyenmaitichluy;

import geso.dms.center.beans.dangkykhuyenmaitichluy.IDangkykhuyenmaitichluyTT;
import geso.dms.center.beans.dangkykhuyenmaitichluy.IDangkykhuyenmaitichluyTTList;
import geso.dms.center.beans.dangkykhuyenmaitichluy.imp.DangkykhuyenmaitichluyTT;
import geso.dms.center.beans.dangkykhuyenmaitichluy.imp.DangkykhuyenmaitichluyTTList;
import geso.dms.center.beans.tieuchithuong.ITieuchithuongTL;
import geso.dms.center.beans.tieuchithuong.imp.TieuchithuongTL;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.Utility;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Worksheet;
import com.oreilly.servlet.MultipartRequest;

public class DangkykhuyenmaitichluyTTUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	public DangkykhuyenmaitichluyTTUpdateSvl() {
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

			PrintWriter out; 

			IDangkykhuyenmaitichluyTT obj;

			out = response.getWriter();
			Utility util = new Utility();

			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);

			out.println(userId);

			if (userId.length()==0)
				userId = request.getParameter("userId");
			String action = util.getAction(querystring);
			String id = util.getId(querystring);  	
			obj = new DangkykhuyenmaitichluyTT();
			obj.setUserId(userId);
			obj.setId(id);    
			obj.init();

			session.setAttribute("obj", obj);  	
			if(action.equals("update"))
				response.sendRedirect("/AHF/pages/Center/DangKyKhuyenMaiTichLuyTTNew.jsp");
			else if(action.equals("dislay")){
				response.sendRedirect("/AHF/pages/Center/DangKyKhuyenMaiTichLuyTTDisplay.jsp");
			}else if(action.equals("chot")){
				obj.Chot();
				DangkykhuyenmaitichluyTTList  obj1  = new DangkykhuyenmaitichluyTTList();
				obj1.setUserId(userId);
				obj1.init();



				obj.DBclose();
				session.setAttribute("obj",obj1);
				String nextJSP = "/AHF/pages/Center/DangKyKhuyenMaiTichLuyTT.jsp";
				response.sendRedirect(nextJSP);
			}else if(action.equals("unchot")){
				obj.UnChot();
				DangkykhuyenmaitichluyTTList  obj1  = new DangkykhuyenmaitichluyTTList();
				obj1.setUserId(userId);
				obj1.init();

				obj1.setMsg(obj.getMessage());
				obj.DBclose();
				session.setAttribute("obj",obj1);
				String nextJSP = "/AHF/pages/Center/DangKyKhuyenMaiTichLuyTT.jsp";
				response.sendRedirect(nextJSP);

			}else if(action.equals("delete"))
			{
					obj.Delete();

					DangkykhuyenmaitichluyTTList  obj1  = new DangkykhuyenmaitichluyTTList();
					obj1.setUserId(userId);
					obj1.init();

					obj1.setMsg(obj.getMessage());
					obj.DBclose();
					session.setAttribute("obj",obj1);
					String nextJSP = "/AHF/pages/Center/DangKyKhuyenMaiTichLuyTT.jsp";
					response.sendRedirect(nextJSP);
				
			}
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility util = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!util.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
			//PrintWriter out; 

			IDangkykhuyenmaitichluyTT obj;

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			//out = response.getWriter();
			String contentType = request.getContentType();


			obj = new DangkykhuyenmaitichluyTT();

			userId = request.getParameter("userId");
			obj.setUserId(userId);

			obj.setDiengiai( request.getParameter("diengiai") == null ? "":  request.getParameter("diengiai")  );
			obj.setNgaydangky( request.getParameter("ngaydangky") == null ? "":  request.getParameter("ngaydangky")  );

			String ctkmId = request.getParameter("ctkmId");
			obj.setCtkmId(ctkmId);

			String nppIds[] = request.getParameterValues("nppIds");
			obj.setNppIds(nppIds);

			String nppIdSelecteds[] = request.getParameterValues("nppIdSelecteds");
			obj.setNppIdSelecteds(nppIdSelecteds);

			String id = request.getParameter("id");
			if(id == null)
				id = "";
			obj.setId(id);

			String nvbhId = request.getParameter("nvbhId");
			if(nvbhId == null)
				nvbhId = "";
			obj.setNvbhIds(nvbhId);

			/*String vungs[] = request.getParameterValues("vung");
		    obj.setVungIds(vungs);

		    String khuvucs[] = request.getParameterValues("khuvuc");
		    obj.setKhuvucIds(khuvucs);*/

			Hashtable<String, Integer> kh_stt = new Hashtable<String, Integer>();
			Hashtable<String, Integer> kh_mucdk = new Hashtable<String, Integer>();

			String khachhang[] = request.getParameterValues("khIds");
			if(khachhang != null)
			{
				String kh = "";
				for(int i = 0; i < khachhang.length; i++ )
				{
					kh += khachhang[i] + ",";
					String stt = request.getParameter("stt_" + khachhang[i]);
					String muc = request.getParameter("mdk_" + khachhang[i]);
					kh_stt.put(khachhang[i], Integer.parseInt(stt));
					kh_mucdk.put(khachhang[i], Integer.parseInt(muc));
					System.out.println(i + "-" + khachhang[i]);
				}
				if(kh.trim().length() > 0)
				{
					kh = kh.substring(0, kh.length() - 1);
					obj.setKhId(kh);
				}

			}
			obj.setSTT(kh_stt);
			obj.setMucDangky(kh_mucdk);

			if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0)) 
			{

				Upload(request, response);
				return;
			}

			String action = request.getParameter("action");
			//out.println(action); 
			if(action.equals("submit"))
			{
				obj.createRs();
				session.setAttribute("obj", obj);  	 		
				response.sendRedirect("/AHF/pages/Center/DangKyKhuyenMaiTichLuyTTNew.jsp");	
			}
			else if(action.equals("excel")){
				try
				{
					response.setContentType("application/xls");
					response.setHeader("Content-Disposition", "attachment; filename=DangKyTichluy_Template.xls");
					OutputStream out = response.getOutputStream();
					ExportToExcel(out,obj);

				} 
				catch (Exception ex)
				{
					ex.printStackTrace();					
				}
			}
			else
			{
				if(obj.getId().length()>0)
				{
					if(obj.edit()){
						IDangkykhuyenmaitichluyTTList objl  = new DangkykhuyenmaitichluyTTList();
						objl.setUserId(userId);
						objl.init();
						session.setAttribute("obj",objl);
						String nextJSP = "/AHF/pages/Center/DangKyKhuyenMaiTichLuyTT.jsp";
						response.sendRedirect(nextJSP);
					}else{
						//obj.init();
						obj.createRs();
						session.setAttribute("obj", obj);  	 		
						response.sendRedirect("/AHF/pages/Center/DangKyKhuyenMaiTichLuyTTNew.jsp");	
					}	
				}else{
					if(obj.save()){
						IDangkykhuyenmaitichluyTTList objl  = new DangkykhuyenmaitichluyTTList();
						objl.setUserId(userId);
						objl.init();
						session.setAttribute("obj",objl);
						String nextJSP = "/AHF/pages/Center/DangKyKhuyenMaiTichLuyTT.jsp";
						response.sendRedirect(nextJSP);
					}else{
						//obj.init();
						obj.createRs();
						session.setAttribute("obj", obj);  	 		
						response.sendRedirect("/AHF/pages/Center/DangKyKhuyenMaiTichLuyTTNew.jsp");	
					}
				}

			}


		}
	}

	private void Export(HttpServletRequest request, HttpServletResponse response) {
		OutputStream out = null;
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=Export.xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());
			WritableSheet sheet = w.createSheet("Sheet1", 0);

			WritableCellFormat cellFormat = new WritableCellFormat();

			cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

			sheet.addCell(new Label(0, 3, "STT", cellFormat));
			sheet.addCell(new Label(1, 3, "MÃ KH", cellFormat));
			sheet.addCell(new Label(2, 3, "MỨC ĐK", cellFormat));
			int stt = 4;
			String khachhang[] = request.getParameterValues("smartid");
			if(khachhang != null)
			{
				String kh = "";
				for(int i = 0; i < khachhang.length; i++ )
				{
					kh += khachhang[i] + ",";
					String stt2 = request.getParameter("stt2_" + khachhang[i]);
					String mkd2 = request.getParameter("mdk2_" + khachhang[i]);
					sheet.addCell(new Label(0, stt, stt2, cellFormat));
					sheet.addCell(new Label(1, stt, khachhang[i], cellFormat));
					sheet.addCell(new Label(2, stt, mkd2, cellFormat));
					stt++;
				}
			}
			w.write();
			w.close();
		} catch (Exception e)
		{
			System.out.println("Error Cac Ban : " + e.getMessage());
			e.printStackTrace();
		} finally
		{
			if (out != null)
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	public void Upload(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		Utility util =new Utility();
		int indexRow = 8;
		int socotfixcung = 2;
		HttpSession session =  request.getSession();

		MultipartRequest multi = new MultipartRequest(request, "C:\\java-tomcat\\data\\", 20000000, "UTF-8");
		IDangkykhuyenmaitichluyTT obj = new DangkykhuyenmaitichluyTT();

		obj.setUserId(multi.getParameter("userId"));
		obj.setCtkmId(util.antiSQLInspection(multi.getParameter("ctkmId"))==null? "":util.antiSQLInspection(multi.getParameter("ctkmId")));	   	 
		obj.setId(util.antiSQLInspection(multi.getParameter("id"))==null? "":util.antiSQLInspection(multi.getParameter("id")));	 
		obj.setNvbhIds(util.antiSQLInspection(multi.getParameter("nvbhId"))==null? "":util.antiSQLInspection(multi.getParameter("nvbhId")));

		obj.setDiengiai( multi.getParameter("diengiai") == null ? "":  multi.getParameter("diengiai")  );
		obj.setNgaydangky( multi.getParameter("ngaydangky") == null ? "":  multi.getParameter("ngaydangky")  );



		Enumeration files = multi.getFileNames();
		String filenameu = "";
		while (files.hasMoreElements())
		{
			String name = (String) files.nextElement();
			filenameu = multi.getFilesystemName(name);
			System.out.println("name  " + name);
			;
		}
		String filename = "C:\\java-tomcat\\data\\" + filenameu;
		String values = "";

		if (filename.length() > 0)
		{
			// doc file excel
			File file = new File(filename);
			System.out.println("filename  " + file);
			Workbook workbook;
			try
			{
				workbook = Workbook.getWorkbook(file);
				Sheet sheet = workbook.getSheet(0);
				Cell[] cells = sheet.getRow(indexRow);
				List<Object> dataUpload = new ArrayList<Object>();
				for (int i = indexRow ; i < sheet.getRows(); i++)
				{
					System.out.println("Vao Day: " + i);

					cells = sheet.getRow(i);
					if(cells.length > 0)
					{

						String khId = getStringValue(cells,0);
						if(khId.trim().length() > 0)
						{
							int muc  = Utility.parseInt(getStringValue(cells,4).replace(",",""));
							System.out.println("Vao Day khId: " + khId);

							dataUpload.add(khId);
							dataUpload.add( (muc-1) + "" );
							if(values.trim().length() >0)
								values +=" union all select "+khId+" smartId , "+(muc-1)+"  muc ";
							else
								values +=" 	select "+khId+" smartId , "+(muc-1)+"  muc ";
						}
					}

				}
				if(values.trim().length() <= 0)
				{
					obj.setMessage("Không lấy được dữ liệu upload");
					Redirect(obj ,"/AHF/pages/Center/DangKyKhuyenMaiTichLuyTTNew.jsp",  response, session);
					return;
				}

				obj.Upload(values, dataUpload);
				Redirect(obj ,"/AHF/pages/Center/DangKyKhuyenMaiTichLuyTTNew.jsp",  response, session);

			}
			catch (Exception e) 
			{
				e.printStackTrace();
				obj.setMessage( "Phát sinh lỗi khi Upload ");
				Redirect(obj ,"/AHF/pages/Center/DangKyKhuyenMaiTichLuyTTNew.jsp",  response, session);
			}
		}

	}

	public String getDateTime() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);	
	}
	private void ExportToExcel(OutputStream out,IDangkykhuyenmaitichluyTT obj )throws Exception
	{
		try
		{ 			
			com.aspose.cells.Workbook workbook = new com.aspose.cells.Workbook();
			workbook.setFileFormatType(FileFormatType.EXCEL2003);

			String query = obj.setQueryKhachHangDangKy();
			TaoBaoCao(workbook, obj, query);
			workbook.save(out);			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}

	}
	public void Redirect(IDangkykhuyenmaitichluyTT  tctskuBean ,String nextJSP, HttpServletResponse response,  HttpSession session) throws IOException
	{
		tctskuBean.createRs();
		session.setAttribute("obj", tctskuBean);
		response.sendRedirect(nextJSP);
	}


	private void TaoBaoCao(com.aspose.cells.Workbook workbook,IDangkykhuyenmaitichluyTT obj,String query )throws Exception
	{
		try
		{
			com.aspose.cells.Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			com.aspose.cells.Cells cells = worksheet.getCells();

			com.aspose.cells.Cell cell = cells.getCell("B3");
			cells.setRowHeight(0, 50.0f);
			cell = cells.getCell("A1");
			ReportAPI.getCellStyle(cell, Color.RED, true, 16,"FORM UPLOAD ĐĂNG KÝ");

			cell = cells.getCell("A2");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Ngày tạo : "+ getDateTime());		

			String sql = " select isnull(max(isnull(muc,0))  + 1,-1) muc from tieuchithuongtl_tieuchi where thuongtl_fk =" + obj.getId();
			ResultSet rs=  obj.getDb().get(sql);
			rs.next();
			cell = cells.getCell("A3");

			String muc = rs.getString("muc");
			if(muc.equals("-1"))
			{
				muc = "Chưa thiết lâp ";
			}

			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Mức đăng ký tối đa: " + muc ) ;		



			rs=  obj.getDb().get(query);


			ResultSetMetaData rsmd = rs.getMetaData();
			int socottrongSql = rsmd.getColumnCount();

			int countRow = 7;
			int column = 0;
			for( int i =1 ; i <=socottrongSql ; i ++  )
			{

				cell = cells.getCell(countRow, column + i-1 );cell.setValue(rsmd.getColumnName(i));
				ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);	

			}
			countRow ++;
			while(rs.next())
			{
				for(int i =1;i <=socottrongSql ; i ++)
				{
					Color c = Color.WHITE;
					cell = cells.getCell(countRow,column + i-1 );
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
				++countRow;
			}

			if(rs!=null)rs.close();



		}catch(Exception ex){

			ex.printStackTrace();
			throw new Exception("Lỗi không lấy tích lũy !");
		}
	}

	public String getStringValue(Cell[] cells,int vitri)
	{
		try
		{
			return cells[vitri].getContents().toString().replace("\t", "").replace(",", "").replace(" ", "").trim();
		}
		catch (Exception e) {
			return "";
		}
	}

	public double getDoubleValue(Cell[] cells,int vitri)
	{
		try
		{
			return Double.parseDouble(cells[vitri].getContents().toString().replace("\t", "").replace(",", "").replace(" ", "").trim());
		}
		catch (Exception e) {
			return 0.0;
		}
	}
}
