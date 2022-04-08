package geso.dms.center.servlets.chitieu;

import geso.dms.center.beans.chitieu.IChiTieuNhanVien;
import geso.dms.center.beans.chitieu.IChiTieuNhanVienList;
import geso.dms.center.beans.chitieu.imp.ChiTieuNhanVien;
import geso.dms.center.beans.chitieu.imp.ChiTieuNhanVienList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.Utility;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Sheet;
import jxl.Workbook;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.Worksheet;
import com.oreilly.servlet.MultipartRequest;

public class ChiTieuNhanVienUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ChiTieuNhanVienUpdateSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		IChiTieuNhanVien tctskuBean;

		//this.out = response.getWriter();
		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		//out.println(userId);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		
		//--- check user
//		Utility util = new Utility();
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
		if( Utility.CheckRuleUser( session,  request, response, "ChiTieuNhanvienSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}
		 //--- check user

		String id = util.getId(querystring);

		tctskuBean = new ChiTieuNhanVien(id);
		tctskuBean.setId(id);
		tctskuBean.setUserId(userId);

		tctskuBean.init();
		session.setAttribute("tctskuBean", tctskuBean);
		session.setAttribute("userId", userId);
		String nextJSP = "/AHF/pages/Center/ChiTieuNhanvienUpdate.jsp";
		if (querystring.indexOf("display") >= 0)
			nextJSP = "/AHF/pages/Center/ChiTieuNhanVienDisplay.jsp";

		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IChiTieuNhanVien tctskuBean;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		//this.out = response.getWriter();
		Utility util = new Utility();
		
		//--- check user
//		Utility util = new Utility();
	    String view1 = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
		if (view1 == null) {
			view1 = "";
		}
		
		String param = "";
		if(view1.trim().length() > 0) param ="&view="+view1;
		String contentType = request.getContentType();
		if( Utility.CheckRuleUser( session,  request, response, "ChiTieuNhanvienSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}
		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
		{
			
		}else
		{
			if ( Utility.CheckSessionUser( session,  request, response))
			{
				Utility.RedireactLogin(session, request, response);
			}
			
		}
		
		 //--- check user

		
		
		String id=null; 
		String userId =null;
		String diengiai =null;
		String scheme =null;
		String thang =null;
		String nam =null;
		String quy =null;
		String apdung =null;
		String loaichitieu =null;
		String view =null;
		String tructhuocId =null;
		String vungId =null;
		String kvId =null;
		String action=null;

		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
		{
			//MultipartRequest multi = new MultipartRequest(request, "C:\\java-tomcat\\data\\", 20000000, "UTF-8");
			MultipartRequest multi = new MultipartRequest(request, getServletContext().getRealPath("\\upload\\"), 20000000, "UTF-8"); 
			id = util.antiSQLInspection(multi.getParameter("id"));
			if (id == null)
			{
				tctskuBean = new ChiTieuNhanVien("");
			} else
			{
				tctskuBean = new ChiTieuNhanVien(id);
			}

			userId = util.antiSQLInspection(multi.getParameter("userId"));
			
			if ( Utility.CheckSessionUser( session,  request, response,userId))
			{
				Utility.RedireactLogin(session, request, response);
			}
			
			
			
			tctskuBean.setUserId(userId);

			diengiai = util.antiSQLInspection(multi.getParameter("diengiai"));
			if (diengiai == null)
				diengiai = "";
			tctskuBean.setDiengiai(diengiai);

			scheme = util.antiSQLInspection(multi.getParameter("scheme"));
			if (scheme == null)
				scheme = "";
			tctskuBean.setScheme(scheme);

			thang = util.antiSQLInspection(multi.getParameter("thang"));
			if (thang == null)
				thang = "";
			tctskuBean.setThang(thang);

			nam = util.antiSQLInspection(multi.getParameter("nam"));
			if (nam == null)
				nam = "";
			tctskuBean.setNam(nam);

			quy = util.antiSQLInspection(multi.getParameter("quy") == null ? "" : multi.getParameter("quy"));
			tctskuBean.setQuy(quy);

			apdung = util.antiSQLInspection(multi.getParameter("apdung") == null ? "" : multi.getParameter("apdung"));
			tctskuBean.setApdung(apdung);

			loaichitieu = util.antiSQLInspection(multi.getParameter("loaichitieu") == null ? "0" : multi.getParameter("loaichitieu"));
			tctskuBean.setLoaichitieu(loaichitieu);

			view = util.antiSQLInspection(multi.getParameter("view") == null ? "" : multi.getParameter("view"));
			tctskuBean.setView(view);

			tructhuocId = util.antiSQLInspection(multi.getParameter("tructhuocId") == null ? "" : multi.getParameter("tructhuocId"));
			tctskuBean.setTructhuocId(tructhuocId);

			action = util.antiSQLInspection(multi.getParameter("action") == null ? "" : multi.getParameter("action"));

			Enumeration files = multi.getFileNames();
			String filenameu = "";
			while (files.hasMoreElements())
			{
				String name = (String) files.nextElement();
				filenameu = multi.getFilesystemName(name);
				System.out.println("name  " + name);
			}

			//String filename = "C:\\java-tomcat\\data\\" + filenameu;
			String filename=    getServletContext().getRealPath("\\upload\\")+ "\\"+ filenameu;
			if (filename.length() > 0)
			{
				File file = new File(filename);
				Workbook workbook;
				int indexRow = 5;
				try
				{					
					workbook = Workbook.getWorkbook(file);
					Sheet sheet = workbook.getSheet(0);
					int sodong = sheet.getRows();
					int socot = sheet.getColumns();
					String sql="";
					String sql_NHOM="";
					for (int row=6; row < sodong ; row++)
					{
						String nppMa =getValue(sheet, 1,row);
						String nppTen =getValue(sheet, 2,row);

						String gsbhMa =getValue(sheet, 3,row);
						String gsbhTen =getValue(sheet, 4,row);

						String nvId =getValue(sheet, 5,row);
						String nvTen =getValue(sheet, 6,row);

						String nvLoai =getValue(sheet, 7,row);
						String ChiTieu =getValue(sheet, 8,row);
						String SoDonHang =getValue(sheet, 9,row);
						String GiaTriDonHang =getValue(sheet, 10,row);
						String SoKhachHangMoi =getValue(sheet, 11,row);
						String SoKhachHangMuaHang =getValue(sheet, 12,row);


						if(nppMa.length()>0&&nvId.length()>0)
							sql +=
							"\n  select N'"+nppMa+"' as nppMa,N'"+nppTen+"' as nppTen ,'"+gsbhMa+"' as gsbhMa,N'"+gsbhTen+"' as gsbhTen , '"+nvId+"' as nvId,'"+nvLoai+"' as nvLoai, N'"+nvTen+"' as nvTen,  '"+ChiTieu+"' as ChiTieu,'"+SoDonHang+"' as SoDonHang,'"+GiaTriDonHang+"' as GiaTriTBDonHang,'"+SoKhachHangMoi +"' as SoKhachHangMoi ," +
									"\n  '"+SoKhachHangMuaHang+"' as SoKhachHangMuaHang  union all ";

						for(int i=13;i<socot;i++)
						{
							String nhomId = getValue(sheet, i,indexRow);
							String nhomValue =getValue(sheet, i,row);
							if(nhomId.length()>0 && nhomValue.length()>0)
							{
								sql_NHOM +="\n  select N'"+nppMa+"' as nppMa,N'"+nppTen+"' as nppTen ,'"+gsbhMa+"' as gsbhMa,N'"+gsbhTen+"' as gsbhTen , '"+nvId+"' as nvId,'"+nvLoai+"' as nvLoai, N'"+nvTen+"' as nvTen,'"+nhomId+"' as nhomId,"+nhomValue+" as ChiTieu union all ";
							}
						}
					}

					if(sql.length()>0)
					{
						sql=sql.substring(0, sql.length() - 10);
					}
					if(sql_NHOM.length()>0)
					{
						sql_NHOM=sql_NHOM.substring(0, sql_NHOM.length() - 10);
					}

					tctskuBean.setSql(sql);
					tctskuBean.setSqlNhom(sql_NHOM);

				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		else
		{
			id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
			if (id == null)
			{
				tctskuBean = new ChiTieuNhanVien("");
			} else
			{
				tctskuBean = new ChiTieuNhanVien(id);
			}

			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
			tctskuBean.setUserId(userId);

			diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
			if (diengiai == null)
				diengiai = "";
			tctskuBean.setDiengiai(diengiai);

			scheme = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("scheme")));
			if (scheme == null)
				scheme = "";
			tctskuBean.setScheme(scheme);

			thang = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thang")));
			if (thang == null)
				thang = "";
			tctskuBean.setThang(thang);

			nam = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nam")));
			if (nam == null)
				nam = "";
			tctskuBean.setNam(nam);

			quy = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("quy") == null ? "" : request.getParameter("quy")));
			tctskuBean.setQuy(quy);

			apdung = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("apdung") == null ? "" : request.getParameter("apdung")));
			tctskuBean.setApdung(apdung);

			loaichitieu = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaichitieu") == null ? "0" : request.getParameter("loaichitieu")));
			tctskuBean.setLoaichitieu(loaichitieu);

			view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view") == null ? "" : request.getParameter("view")));
			tctskuBean.setView(view);

			tructhuocId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tructhuocId") == null ? "" : request.getParameter("tructhuocId")));
			tctskuBean.setTructhuocId(tructhuocId);

			vungId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"));
			if (vungId == null)
				vungId = "";
			tctskuBean.setVungIds(vungId);

			kvId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId"));
			if (kvId == null)
				kvId = "";
			tctskuBean.setKhuvucIds(kvId);

			action = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action") == null ? "" : request.getParameter("action")));
		}

		System.out.println("Action la: " + action);
		if (action.equals("save"))
		{
			if (id == null)
			{
				if (!tctskuBean.save())
				{
					tctskuBean.setUserId(userId);
					tctskuBean.createRs();
					session.setAttribute("userId", userId);
					session.setAttribute("tctskuBean", tctskuBean);
					String nextJSP = "/AHF/pages/Center/ChiTieuNhanVienNew.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IChiTieuNhanVienList obj = new ChiTieuNhanVienList();
					obj.setUserId(userId);
					obj.setType(loaichitieu);
					obj.setView(view);
					obj.init("");
					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);
					String nextJSP = "/AHF/pages/Center/ChiTieuNhanVien.jsp";
					response.sendRedirect(nextJSP);
				}
			} 
			else
			{
				if (!(tctskuBean.edit()))
				{
					tctskuBean.setUserId(userId);
					tctskuBean.createRs();
					session.setAttribute("userId", userId);
					session.setAttribute("tctskuBean", tctskuBean);

					String nextJSP = "/AHF/pages/Center/ChiTieuNhanvienUpdate.jsp";
					response.sendRedirect(nextJSP);
				} 
				else
				{
					IChiTieuNhanVienList obj = new ChiTieuNhanVienList();
					obj.setUserId(userId);
					obj.setType(loaichitieu);
					obj.setView(view);
					obj.init("");
					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);
					String nextJSP = "/AHF/pages/Center/ChiTieuNhanVien.jsp";
					response.sendRedirect(nextJSP);
				}
			}
		} 
		else if (action.equals("excel"))
	    {   
			try
			{
				response.setContentType("application/xls");
				response.setHeader("Content-Disposition", "attachment; filename=Chitieu_Template.xls");
				OutputStream out = response.getOutputStream();
				ExportToExcel(out,tctskuBean);
				
				tctskuBean.createRs();
				session.setAttribute("userId", userId);
				session.setAttribute("tctskuBean", tctskuBean);

				String nextJSP;
				if (id == null)
				{
					nextJSP = "/AHF/pages/Center/ChiTieuNhanVienNew.jsp";
				} else
				{
					nextJSP = "/AHF/pages/Center/ChiTieuNhanvienUpdate.jsp";
				}
				response.sendRedirect(nextJSP);

			} catch (Exception ex)
			{
				ex.printStackTrace();					
			}	
	    }
		else
		{
			tctskuBean.createRs();
			session.setAttribute("userId", userId);
			session.setAttribute("tctskuBean", tctskuBean);

			String nextJSP;
			if (id == null)
			{
				nextJSP = "/AHF/pages/Center/ChiTieuNhanVienNew.jsp";
			} else
			{
				nextJSP = "/AHF/pages/Center/ChiTieuNhanvienUpdate.jsp";
			}
			response.sendRedirect(nextJSP);
		}
	}


	String getValue(Sheet sheet,int col,int row)
	{
		return sheet.getCell(col,row).getContents().trim().replaceAll(",", "");
	}


	public String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	private void TaoBaoCao(com.aspose.cells.Workbook workbook,IChiTieuNhanVien obj,String query,int sheetNum )throws Exception
	{
		try
		{
			com.aspose.cells.Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(sheetNum);
			com.aspose.cells.Cells cells = worksheet.getCells();
			
			com.aspose.cells.Cell cell = cells.getCell("B3");
			dbutils db = new dbutils();
			
			ResultSet rs = db.get(query);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int socottrongSql = rsmd.getColumnCount();
			
			int countRow = 5;
			int column = 0;
			for( int i = 1 ; i <= socottrongSql; i ++  )
			{
				cell = cells.getCell(countRow, i-1);cell.setValue(rsmd.getColumnName(i));
				ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);	
				column++;
			}
			
			cell = cells.getCell(countRow, column);cell.setValue("Chỉ tiêu");
			ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			column++;
			cell = cells.getCell(countRow, column);cell.setValue("Số đơn hàng");
			ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			column++;
			cell = cells.getCell(countRow, column);cell.setValue("Giá trị TB/ Đơn hàng");
			ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			column++;
			cell = cells.getCell(countRow, column);cell.setValue("Số khách hàng");
			ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			column++;
			cell = cells.getCell(countRow, column);cell.setValue("Số khách hàng mua hàng");
			ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			column++;
			
			String sql = "select distinct nsp.pk_seq from nhomsanpham nsp where nsp.loainhom = 3 and trangthai = 1";
			
			ResultSet rsnsp = db.get(sql);

			if(rsnsp != null){

				try {
					while (rsnsp.next())
					{
						cell = cells.getCell(countRow, column);cell.setValue(rsnsp.getString("pk_seq"));
						ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);	
						column++;
					}
					rsnsp.close();
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}

		    Font font = new Font();
		    font.setName("Courier New");
		    font.setSize(12);
		    font.setBold(false);
		    
			countRow ++;
			while(rs.next())
			{
				
				
				for(int i = 1; i <= socottrongSql; i ++)
				{
					Color c = Color.WHITE;
					cell = cells.getCell(countRow, i-1);
				    cell.getStyle().getFont().setBold(false);
				    
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
			if(db!=null){
				db.shutDown();
			}

	
		}catch(Exception ex){
			
			ex.printStackTrace();
			throw new Exception("Qua trinh dien du lieu vao file Excel va tao PivotTable bi loi.!!!");
		}
	}
	
	private void ExportToExcel(OutputStream out,IChiTieuNhanVien obj )throws Exception
	{
		try{ 			
			
			com.aspose.cells.Workbook workbook = new com.aspose.cells.Workbook();
			workbook.setFileFormatType(FileFormatType.EXCEL2003);
			
			String query = getQueryExcel(obj);
			TaoBaoCao(workbook, obj, query, 0);
			workbook.save(out);			

		}catch(Exception ex){
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}
		
	}
	
	public String getQueryExcel (IChiTieuNhanVien obj )
	{
		 String query =	 
				"\n select ROW_NUMBER() OVER(ORDER BY c.pk_seq ASC) AS STT, c.MA as N'Mã nhà phân phối', c.TEN as N'Tên nhà phân phối', "+
				"\n d.SMARTID as N'Mã GSBH', d.TEN as N'Tên GSBH', a.SMARTID as N'Mã nhân viên', a.TEN as N'Tên nhân viên', 'SR' as N'Loại' "+
				"\n from DAIDIENKINHDOANH a "+
				"\n inner join DDKD_GSBH b on a.PK_SEQ = b.DDKD_FK "+
				"\n inner join NHAPHANPHOI c on a.NPP_FK = c.PK_SEQ "+
				"\n inner join GIAMSATBANHANG d on b.GSBH_FK = d.PK_SEQ "+
				"\n where a.TRANGTHAI = 1" ;
		 System.out.println("query BC="+ query);
		 return query;
	}
}
