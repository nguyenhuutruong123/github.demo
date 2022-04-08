package geso.dms.center.servlets.chitieunhaphanphoi;

import geso.dms.center.beans.chitieunhaphanphoi.IChiTieuNhaphanphoi;
import geso.dms.center.beans.chitieunhaphanphoi.imp.ChiTieuNhaphanphoi;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.Utility;
import geso.dms.distributor.db.sql.dbutils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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

import com.aspose.cells.FileFormatType;
import com.oreilly.servlet.MultipartRequest;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.Color;
import com.aspose.cells.Font;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Style;
import com.aspose.cells.Worksheet;

public class ChiTieuNhaphanphoiNewSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ChiTieuNhaphanphoiNewSvl()
	{
		super();
	}

	private String gettenuser(String userId_)
	{
		dbutils db = new dbutils();
		String sql_getnam = "select ten from nhanvien where pk_seq=" + userId_;
		ResultSet rs_tenuser = db.get(sql_getnam);
		if (rs_tenuser != null)
		{
			try
			{
				while (rs_tenuser.next())
				{
					return rs_tenuser.getString("ten");
				}
			} catch (Exception er)
			{
				return "";
			}
		}
		return "";
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String querystring = request.getQueryString();
		Utility util = new Utility();
		String userId = util.getUserId(querystring);
		// System.out.println("Ten user:  "+ userId);
		if (userId.length() == 0)
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		String id = util.getId(querystring);

		IChiTieuNhaphanphoi obj = new ChiTieuNhaphanphoi(id);
		obj.setUserId(userId);

		String action = util.getAction(querystring);
		if (action.equals("display"))
		{
			obj.setDisplay("1");
		}
		
		session.setAttribute("userId", userId);
		String tenuser = gettenuser(userId);
		session.setAttribute("userTen", tenuser);
		session.setAttribute("obj", obj);
		
		session.setAttribute("check", "0");
		
		String nextJSP = "/AHF/pages/Center/ChiTieuNhaphanphoiUpdate.jsp";// default
		response.sendRedirect(nextJSP);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String contentType = request.getContentType();

		Utility util = new Utility();
		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
		{
			//MultipartRequest multi = new MultipartRequest(request, "C:\\java-tomcat\\data\\", 20000000, "UTF-8");
			MultipartRequest multi = new MultipartRequest(request, getServletContext().getRealPath("\\upload\\"), 20000000, "UTF-8"); 
			//String loainv = util.antiSQLInspection(multi.getParameter("loai"));
			xulyNPP(request, response, util, session, contentType, multi);
		}
		else
		{
			String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
			IChiTieuNhaphanphoi chitieu = new ChiTieuNhaphanphoi();
			try
			{
				int thang = Integer.parseInt(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thang"))));
				chitieu.setThang(thang);
			} 
			catch (Exception er) { }
			
			try
			{
				int nam = Integer.parseInt(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nam"))));
				chitieu.setNam(nam);
			} 
			catch (Exception er) { }

			try
			{

				chitieu.setID(id);
			} 
			catch (Exception err) { }
			
			int songaylamviec = 0;
			try
			{
				songaylamviec = Integer.parseInt(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("songaylamviec"))));
				
			} catch (Exception er){}
			chitieu.setSoNgayLamViec(songaylamviec +"");
			
			String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
			chitieu.setUserId(userId);

			String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));
			chitieu.setTrangThai(trangthai);
			System.out.println("trangthai === " + chitieu.getTrangThai());
			
			String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
			chitieu.setDienGiai(diengiai);
			chitieu.setNguoiSua(userId);
			chitieu.setNguoiTao(userId);
			chitieu.setNgayTao(this.getDateTime());
			chitieu.setNgaySua(this.getDateTime());
			System.out.println("ID: "+id);
			if(id != null)
				if(id.length() > 0)
				chitieu.initBCNhaphanphoi();
			
			String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
			if (action.equals("excel"))
			{
				try
				{
					response.setContentType("application/xls");
					response.setHeader("Content-Disposition", "attachment; filename=ChitieuNPP_Template.xls");
					OutputStream out = response.getOutputStream();

					String query = "select Pk_seq, ten from nhaphanphoi where trangthai = 1  and  pk_seq != 1";		
					if(chitieu.getTrangThai().trim().length() >0 && Integer.parseInt(chitieu.getTrangThai().trim()) == 1 && id.length() > 0)
					{
						CreatePivotTableDaChot(out, chitieu);
					}
					else
						CreatePivotTable(out, chitieu, query);
					
				} 
				catch (Exception ex)
				{
					ex.printStackTrace();					
					chitieu.setMessage(ex.getMessage());
				}
			}
			
			String nextJSP = "/AHF/pages/Center/ChiTieuNhaphanphoiUpdate.jsp";// default			
			session.setAttribute("obj", chitieu);
			response.sendRedirect(nextJSP);
			System.out.println(chitieu.getMessage());
		}
	}

	public void xulyNPP(HttpServletRequest request, HttpServletResponse response,Utility util,HttpSession session,String contentType,MultipartRequest multi ) throws ServletException, IOException
	{
		dbutils db = new dbutils();
		IChiTieuNhaphanphoi chitieu = new ChiTieuNhaphanphoi();

		try
		{
			int thang = Integer.parseInt(util.antiSQLInspection(multi.getParameter("thang")));
			chitieu.setThang(thang);
		} 
		catch (Exception er)
		{
			er.printStackTrace();
		}

		try
		{
			int nam = Integer.parseInt(util.antiSQLInspection(multi.getParameter("nam")));
			chitieu.setNam(nam);
		} 
		catch (Exception er)
		{
			er.printStackTrace();
		}

		String id = util.antiSQLInspection(multi.getParameter("id"));
		try
		{
			chitieu.setID(id);
		} 
		catch (Exception er)
		{
			er.printStackTrace();
		}

		int songaylamviec = 0;
		try
		{
			songaylamviec = Integer.parseInt(util.antiSQLInspection(multi.getParameter("songaylamviec")));

		} 
		catch (Exception er){ er.printStackTrace(); }

		chitieu.setSoNgayLamViec(songaylamviec +"");
		System.out.println("id SVL="  +  id);

		String userId = util.antiSQLInspection(multi.getParameter("userId"));
		chitieu.setUserId(userId);

		String diengiai = util.antiSQLInspection(multi.getParameter("diengiai"));
		chitieu.setDienGiai(diengiai);
		chitieu.setNguoiSua(userId);
		chitieu.setNguoiTao(userId);
		chitieu.setNgayTao(this.getDateTime());
		chitieu.setNgaySua(this.getDateTime());

		Enumeration files = multi.getFileNames();
		String filenameu = "";
		while (files.hasMoreElements())
		{
			String name = (String) files.nextElement();
			filenameu = multi.getFilesystemName(name);
			System.out.println("name  " + name);
		}

		// ///////////////////////////////////
		//String filename = "C:\\java-tomcat\\data\\" + filenameu;
		String filename=    getServletContext().getRealPath("\\upload\\")+ "\\"+ filenameu;
		if (filename.length() > 0)
		{
			// doc file excel
			File file = new File(filename);
			System.out.println("filename  " + file);
			Workbook workbook;
			//ResultSet rs = null;

			int indexRow = 7;

			int socotfixcung = 11;	
			try
			{
				System.out.println(file);
				workbook = Workbook.getWorkbook(file);
				Sheet sheet = workbook.getSheet(0);
				Cell[] cells = sheet.getRow(indexRow);

				String sql = "delete TempCHITIEUNHAPHANPHOI_NPP_NSP";
				db.update(sql);
				sql = "delete TempCHITIEUNHAPHANPHOI_NPP";
				db.update(sql);

				boolean loi = false;
				String values = "";
				String valuesDetail = "";
				cells = sheet.getRow(4); // dem so cot co value
				int socot =  cells.length;
				System.out.println("so cot toi da = " + socot);
				if(socot < socotfixcung)
				{
					loi = true;
					chitieu.setMessage("So cot bat buoc chua dung'");
					indexRow = Integer.MAX_VALUE;
				}
				String[] manhomArr= null;
				String[] loaikpiArr= null;
				/*
				cells = sheet.getRow(3);
				String ab = cells[0].getContents().toString().replace("\t", "").replace(" ", "").trim();
				System.out.println("hehêhehe=  "  +  ab);*/
				
				System.out.println("::: SO COT: " + socot + "  --- SO COT FIX CUNG: " + socotfixcung );
				if( socot > socotfixcung)
				{
					loaikpiArr = new String[socot];
					manhomArr = new String[socot];
					cells = sheet.getRow(5);
					for ( int i = socotfixcung ; i < loaikpiArr.length; i ++)
					{
						String loai = cells[i].getContents().toString().replace("\t", "").replace(" ", "").trim();
						if(!loai.equals("1") && !loai.equals("2") && !loai.equals("3") && !loai.equals("4") && !loai.equals("6"))// &&!loai.equals("3")
						{
							loi = true;
							indexRow = Integer.MAX_VALUE;
							chitieu.setMessage("loi dong excel thu 7");
						}
						loaikpiArr[i] = loai;
					}
					
					cells = sheet.getRow(6);
					for ( int i = socotfixcung ; i < manhomArr.length; i ++)
					{
						String manhom = cells[i].getContents().toString().replace("\t", "").replace(" ", "").trim();
						System.out.println("::: MA NHOM: " + manhom + " -- COT: " + i );
						
						if( manhom.trim().length() > 0 )
						{
							try 
							{ 
								double so = Double.parseDouble(manhom); 
							}
							catch(Exception e)
							{
								loi = true;
								indexRow = Integer.MAX_VALUE;
								chitieu.setMessage("Mã nhóm tại cột: " + i + " không hợp lệ. Vui lòng kiểm tra lại");
								e.printStackTrace();
							}
							manhomArr[i] = manhom;
						}
					}
				}

				for (int i = indexRow ; i < sheet.getRows(); i++)
				{
					System.out.println("Vao Day: " + i);
					cells = sheet.getRow(i);
					if(cells.length > 0 && !loi)
					{
						int tangdan = 1;
						String ma = "0";
						ma = cells[tangdan].getContents().toString().replace("\t", "").replace(" ", "").trim();
						try{ double so = Double.parseDouble(ma);}
						catch(Exception e)
						{
							loi = true;
							indexRow = Integer.MAX_VALUE;
							chitieu.setMessage(" Ma nv : Loi dong ["+(i + 1)+"]");
						}
						tangdan = 3;
						String doanhsobanra ="0";
						try{doanhsobanra = cells[tangdan++].getContents().toString().replace("\t", "").replace(",", "").replace(" ", "").trim();}catch(Exception e){}
						try{ double so = Double.parseDouble(doanhsobanra);}
						catch(Exception e)
						{
							doanhsobanra = "0";
						}
						String soluongbanra = "0";
						try{soluongbanra = cells[tangdan++].getContents().toString().replace("\t", "").replace(",", "").replace(" ", "").trim();}catch(Exception e){}
						try{ double so = Double.parseDouble(soluongbanra);}
						catch(Exception e)
						{
							soluongbanra = "0";
						}
						String doanhsomuavao = "0";
						try{doanhsomuavao = cells[tangdan++].getContents().toString().replace("\t", "").replace(",", "").replace(" ", "").trim();}catch(Exception e){}
						try{ double so = Double.parseDouble(doanhsomuavao);}
						catch(Exception e)
						{
							doanhsomuavao = "0";
						}
						String soluongmuavao = "0";
						try{soluongmuavao = cells[tangdan++].getContents().toString().replace("\t", "").replace(",", "").replace(" ", "").trim();}catch(Exception e){}
						try{ double so = Double.parseDouble(soluongmuavao);}
						catch(Exception e)
						{
							soluongmuavao = "0";
						}
						/*String tonkhongay ="0";
						try{tonkhongay =cells[7].getContents().toString().replace("\t", "").replace(",", "").replace(" ", "").trim();}catch(Exception e){}
						try{ double so = Double.parseDouble(tonkhongay);}
						catch(Exception e)
						{
							tonkhongay = "0";
						}*/
						String sodonhang =  "0";
						try{sodonhang =  cells[tangdan++].getContents().toString().replace("\t", "").replace(",", "").replace(" ", "").trim();}catch(Exception e){}
						try{ double so = Double.parseDouble(sodonhang);}
						catch(Exception e)
						{
							sodonhang = "0";
						}
						String TyleGiaoHang =  "0";
						try{TyleGiaoHang =  cells[tangdan++].getContents().toString().replace("\t", "").replace(",", "").replace(" ", "").trim();}catch(Exception e){}
						try{ double so = Double.parseDouble(TyleGiaoHang);}
						catch(Exception e)
						{
							TyleGiaoHang = "0";
						}
						String ThoiGianGiaoHang = "0";
						try{ThoiGianGiaoHang = cells[tangdan++].getContents().toString().replace("\t", "").replace(",", "").replace(" ", "").trim();}catch(Exception e){}
						try{ double so = Double.parseDouble(ThoiGianGiaoHang);}
						catch(Exception e)
						{
							ThoiGianGiaoHang = "0";
						}

						String Dochinhxactonkho = "0";
						try{Dochinhxactonkho = cells[tangdan++].getContents().toString().replace("\t", "").replace(",", "").replace(" ", "").trim();}catch(Exception e){}
						try{ double so = Double.parseDouble(Dochinhxactonkho);}
						catch(Exception e)
						{
							Dochinhxactonkho = "0";
						}

						int nspcount = socotfixcung;
						String loaicu = "";
						String macu = "";

						while (loaikpiArr != null && loaikpiArr.length > nspcount &&  manhomArr != null && manhomArr.length > nspcount &&  nspcount < socot)
						{
							String manhom = manhomArr[nspcount];
							String loaikpi = loaikpiArr[nspcount];
							String sotien =  "0";
							try{sotien = cells[nspcount].getContents().toString().replace("\t", "").replace(",", "").replace(" ", "").trim();}catch(Exception e){}
							try{ double so = Double.parseDouble(sotien);}																					
							catch(Exception e)
							{
								sotien = "0";
							}
							/*if(loaicu.equals(loaikpi) && macu.equals(manhom))
							{
								int last = valuesDetail.lastIndexOf(',');
								valuesDetail = valuesDetail.substring(0,last );
								valuesDetail += ",'" + sotien + "'\n     union all";
							}
							else*/
							valuesDetail += "\n select "+loaikpi+", '"+manhom+"' ,'"+ma+"','"+sotien+"'\n     union all";							
							loaicu = loaikpi;
							macu = manhom;														
							nspcount ++;

						}
						values += "\n select "+ma+",'"+doanhsobanra+"', '"+doanhsomuavao+"', '"+soluongbanra+"', '"+soluongmuavao + "','"+sodonhang+"','"+TyleGiaoHang+"','"+ThoiGianGiaoHang+"','"+Dochinhxactonkho+"'   \n     union all";
					}
				}
				
				if (values.length() > 0 && !loi)
				{
					values = values.substring(0, values.length() -12 );
					sql = "\n insert into TempCHITIEUNHAPHANPHOI_NPP(npp_fk,doanhsobanra,doanhsomuavao,soluongbanra,soluongmuavao,sodonhang,TyleGiaoHang,ThoiGianGiaoHang,Dochinhxactonkho) " + values;
					System.out.println("sql : " + sql);
					db.update(sql);
				}
				
				if (valuesDetail.length() > 0 && !loi)
				{
					valuesDetail = valuesDetail.substring(0, valuesDetail.length() -12 );
					sql = "\n insert into TempCHITIEUNHAPHANPHOI_NPP_NSP(loai,nsp_fk,npp_fk,doanhso) " + valuesDetail;
					//	System.out.println("sql : " + sql);
					db.update(sql);
				}
				
				//loi = true;
				if(loi)
				{
					System.out.println("Khong Thanh Cong");
					String nextJSP ="";
					nextJSP = "/AHF/pages/Center/ChiTieuNhaphanphoiUpdate.jsp";// default						
					// Xoa het chi tieu cu di
					session.setAttribute("obj", chitieu);
					response.sendRedirect(nextJSP);
					System.out.println(chitieu.getMessage());
				}
				else
					if (id == null || id.equals("null") || id.equals("0") ||id.equals("") )
					{
						if (chitieu.Create())
						{
							// Thanh cong
							session.setAttribute("nam", 0);
							session.setAttribute("thang", 0);
							chitieu.setListChiTieu("");
							session.setAttribute("obj", chitieu);
							String nextJSP = "/AHF/pages/Center/ChiTieuNhaphanphoi.jsp";
							response.sendRedirect(nextJSP);
						} 
						else
						{
							System.out.println("Khong Thanh Cong");
							String nextJSP = "/AHF/pages/Center/ChiTieuNhaphanphoiUpdate.jsp";// default

							// Xoa het chi tieu cu di
							chitieu.setID("0");
							session.setAttribute("obj", chitieu);
							response.sendRedirect(nextJSP);
						}

					} 
					else
					{
						if (chitieu.Update())
						{
							// Thanh cong
							session.setAttribute("nam", 0);
							session.setAttribute("thang", 0);
							chitieu.setListChiTieu("");
							session.setAttribute("obj", chitieu);
							String nextJSP = "/AHF/pages/Center/ChiTieuNhaphanphoi.jsp";
							response.sendRedirect(nextJSP);

						} else
						{
							System.out.println("Khong Thanh Cong");
							String nextJSP = "/AHF/pages/Center/ChiTieuNhaphanphoiUpdate.jsp";// default
							// Xoa het chi tieu cu di

							session.setAttribute("obj", chitieu);
							response.sendRedirect(nextJSP);

							System.out.println(chitieu.getMessage());
						}

					}

			} 
			catch (Exception er)
			{
				er.printStackTrace();
				chitieu.setMessage("Thong bao loi : " + er.toString());
				System.out.println(er.toString());
			}
		}
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		return dateFormat.format(date);
	}

	private void CreatePivotTable(OutputStream out, IChiTieuNhaphanphoi obj, String query) throws Exception
	{
		try
		{
			com.aspose.cells.Workbook workbook = new com.aspose.cells.Workbook();			
			workbook.setFileFormatType(FileFormatType.EXCEL97TO2003);
			
			CreateStaticHeader(workbook, obj);
			FillData(workbook, query, obj);
			workbook.save(out);
			
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}
	}

	private void CreateStaticHeader(com.aspose.cells.Workbook workbook, IChiTieuNhaphanphoi obj) throws Exception
	{
		try
		{
			com.aspose.cells.Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Sheet1");
			com.aspose.cells.Cells cells = worksheet.getCells();

			Style style;
			Font font = new Font();
			font.setColor(Color.RED);//mau chu
			font.setSize(16);// size chu
			font.setBold(true);

			cells.setRowHeight(0, 20.0f);
			com.aspose.cells.Cell cell = cells.getCell("A1");
			style = cell.getStyle();
			style.setFont(font); 
			style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu 	        

			ReportAPI.getCellStyle(cell,Color.RED, true, 14, "Template Upload chỉ tiêu NPP");
			cells.setRowHeight(3, 18.0f);
			cell = cells.getCell("A3");

			ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Tháng : " + obj.getThang() + "" );

			cells.setRowHeight(3, 18.0f);
			cell = cells.getCell("C3"); 
			ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Năm : " + obj.getNam() + "" );

			
			cell = cells.getCell("J3"); 
			ReportAPI.getCellStyle(cell,Color.RED,true, 9, "Diễn giải Nhóm" );
			cell = cells.getCell("K3"); 
			ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "1:Bán ra(Doanh số)" );
			cell = cells.getCell("K4"); 
			ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "2:Bán ra(Số lượng)" );
			cell = cells.getCell("L3"); 
			ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "3:Mua vào(Doanh số)" );
			cell = cells.getCell("L4"); 
			ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "4:Mua vào(Số lượng)" );
			cell = cells.getCell("M3"); 
			ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "6:Tồn kho theo quy định(Ngày)" );
			/*cell = cells.getCell("M4"); 
			ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "7:Độ chính xác tồn kho(%) " );*/

			int cotdautien = 0;
			int dongbatdauHeader = 4;

			cell = cells.getCell(dongbatdauHeader,cotdautien); cell.setValue("Số TT");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			cell = cells.getCell(dongbatdauHeader+1,cotdautien); cell.setValue("");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			cell = cells.getCell(dongbatdauHeader+2,cotdautien++); cell.setValue("");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			
			cell = cells.getCell(dongbatdauHeader,cotdautien); cell.setValue("Mã NPP");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			cell = cells.getCell(dongbatdauHeader+1,cotdautien); cell.setValue("");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			cell = cells.getCell(dongbatdauHeader+2,cotdautien++); cell.setValue("");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			
			cell = cells.getCell(dongbatdauHeader,cotdautien); cell.setValue("Tên NPP");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);	
			cell = cells.getCell(dongbatdauHeader+1,cotdautien); cell.setValue("");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			cell = cells.getCell(dongbatdauHeader+2,cotdautien++); cell.setValue("");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			
			cell = cells.getCell(dongbatdauHeader,cotdautien); cell.setValue(" Doanh Số bán ra");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			cell = cells.getCell(dongbatdauHeader+1,cotdautien); cell.setValue("");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			cell = cells.getCell(dongbatdauHeader+2,cotdautien++); cell.setValue("");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			
			cell = cells.getCell(dongbatdauHeader,cotdautien); cell.setValue(" Số lượng bán ra ");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			cell = cells.getCell(dongbatdauHeader+1,cotdautien); cell.setValue("");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			cell = cells.getCell(dongbatdauHeader+2,cotdautien++); cell.setValue("");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			
			cell = cells.getCell(dongbatdauHeader,cotdautien); cell.setValue(" Doanh số mua vào ");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			cell = cells.getCell(dongbatdauHeader+1,cotdautien); cell.setValue("");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			cell = cells.getCell(dongbatdauHeader+2,cotdautien++); cell.setValue("");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			
			cell = cells.getCell(dongbatdauHeader,cotdautien); cell.setValue(" Số lượng mua vào ");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			cell = cells.getCell(dongbatdauHeader+1,cotdautien); cell.setValue("");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			cell = cells.getCell(dongbatdauHeader+2,cotdautien++); cell.setValue("");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			
			cell = cells.getCell(dongbatdauHeader,cotdautien); cell.setValue(" Tổng số đơn hàng");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			cell = cells.getCell(dongbatdauHeader+1,cotdautien); cell.setValue("");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			cell = cells.getCell(dongbatdauHeader+2,cotdautien++); cell.setValue("");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);

			cell = cells.getCell(dongbatdauHeader,cotdautien); cell.setValue(" Tỷ lệ giao hàng thành công (%) ");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			cell = cells.getCell(dongbatdauHeader+1,cotdautien); cell.setValue("");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			cell = cells.getCell(dongbatdauHeader+2,cotdautien++); cell.setValue("");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			
			cell = cells.getCell(dongbatdauHeader,cotdautien); cell.setValue(" Thời gian giao hàng TB (giờ)");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			cell = cells.getCell(dongbatdauHeader+1,cotdautien); cell.setValue("");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			cell = cells.getCell(dongbatdauHeader+2,cotdautien++); cell.setValue("");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			
			cell = cells.getCell(dongbatdauHeader,cotdautien); cell.setValue("Độ chính xác tồn kho(%)");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			cell = cells.getCell(dongbatdauHeader +1,cotdautien); cell.setValue("");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			cell = cells.getCell(dongbatdauHeader +2,cotdautien++); cell.setValue("");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			
			cell = cells.getCell(dongbatdauHeader,cotdautien); cell.setValue("DS Bán ra nhóm(Doanh số)");
			ReportAPI.setCellBackground(cell, Color.SILVER, BorderLineType.THIN, true, 0);			
			cell = cells.getCell(dongbatdauHeader +1,cotdautien); cell.setValue("1");
			ReportAPI.setCellBackground(cell, Color.SILVER, BorderLineType.THIN, true, 0);
			cell = cells.getCell(dongbatdauHeader +2,cotdautien++); cell.setValue("");
			ReportAPI.setCellBackground(cell, Color.SILVER, BorderLineType.THIN, true, 0);
			
			cell = cells.getCell(dongbatdauHeader,cotdautien); cell.setValue(" DS Bán ra nhóm(Số lượng)");
			ReportAPI.setCellBackground(cell, Color.SILVER, BorderLineType.THIN, true, 0);
			cell = cells.getCell(dongbatdauHeader +1,cotdautien); cell.setValue("2");
			ReportAPI.setCellBackground(cell, Color.SILVER, BorderLineType.THIN, true, 0);
			cell = cells.getCell(dongbatdauHeader +2,cotdautien++); cell.setValue("");
			ReportAPI.setCellBackground(cell, Color.SILVER, BorderLineType.THIN, true, 0);
			
			cell = cells.getCell(dongbatdauHeader,cotdautien); cell.setValue(" DS mua vào nhóm (Doanh số) ");
			ReportAPI.setCellBackground(cell, Color.SILVER, BorderLineType.THIN, true, 0);
			cell = cells.getCell(dongbatdauHeader +1,cotdautien); cell.setValue("3");
			ReportAPI.setCellBackground(cell, Color.SILVER, BorderLineType.THIN, true, 0);
			cell = cells.getCell(dongbatdauHeader +2,cotdautien++); cell.setValue("");
			ReportAPI.setCellBackground(cell, Color.SILVER, BorderLineType.THIN, true, 0);
			
			cell = cells.getCell(dongbatdauHeader,cotdautien); cell.setValue(" DS mua vào nhóm (Số lượng) ");
			ReportAPI.setCellBackground(cell, Color.SILVER, BorderLineType.THIN, true, 0);
			cell = cells.getCell(dongbatdauHeader +1,cotdautien); cell.setValue("4");
			ReportAPI.setCellBackground(cell, Color.SILVER, BorderLineType.THIN, true, 0);
			cell = cells.getCell(dongbatdauHeader +2,cotdautien++); cell.setValue("");
			ReportAPI.setCellBackground(cell, Color.SILVER, BorderLineType.THIN, true, 0);
			
			cell = cells.getCell(dongbatdauHeader,cotdautien); cell.setValue("Tồn kho theo quy định(Ngày)");
			ReportAPI.setCellBackground(cell, Color.SILVER, BorderLineType.THIN, true, 0);
			cell = cells.getCell(dongbatdauHeader +1,cotdautien); cell.setValue("6");
			ReportAPI.setCellBackground(cell, Color.SILVER, BorderLineType.THIN, true, 0);
			cell = cells.getCell(dongbatdauHeader +2,cotdautien++); cell.setValue("");
			ReportAPI.setCellBackground(cell, Color.SILVER, BorderLineType.THIN, true, 0);
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new Exception("Khong the tao duoc Header cho bao cao...");
		}
	}

	private void FillData(com.aspose.cells.Workbook workbook, String query, IChiTieuNhaphanphoi obj) throws Exception
	{
		com.aspose.cells.Worksheets worksheets = workbook.getWorksheets();
		com.aspose.cells.Worksheet worksheet = worksheets.getSheet(0);
		com.aspose.cells.Cells cells = worksheet.getCells();
		dbutils db = new dbutils();
		ResultSet		rs = db.get(query);

		int index = 7;
		NumberFormat dfInt = new DecimalFormat("#,###,##0.##");
		NumberFormat dffloat = new DecimalFormat("#,###,###.###");
		try
		{
			com.aspose.cells.Cell cell = null;
			int i = 1;
			while (rs.next())
			{

				int cotdautien = 0;

				cell = cells.getCell(index,cotdautien++); cell.setValue(i++);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
				cell = cells.getCell(index,cotdautien++); cell.setValue(rs.getString("pk_seq"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
				cell = cells.getCell(index,cotdautien++); cell.setValue(rs.getString("ten"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
				
				cell = cells.getCell(index,cotdautien++); cell.setValue("");
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
				cell = cells.getCell(index,cotdautien++); cell.setValue("");
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
				cell = cells.getCell(index,cotdautien++); cell.setValue("");
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
				cell = cells.getCell(index,cotdautien++); cell.setValue("");
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
				cell = cells.getCell(index,cotdautien++); cell.setValue("");
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
				cell = cells.getCell(index,cotdautien++); cell.setValue("");
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
				cell = cells.getCell(index,cotdautien++); cell.setValue("");
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
				cell = cells.getCell(index,cotdautien++); cell.setValue("");
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
				cell = cells.getCell(index,cotdautien++); cell.setValue("");
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
				cell = cells.getCell(index,cotdautien++); cell.setValue("");
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
				cell = cells.getCell(index,cotdautien++); cell.setValue("");
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
				cell = cells.getCell(index,cotdautien++); cell.setValue("");
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
				cell = cells.getCell(index,cotdautien++); cell.setValue("");
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
				
				
				index++;
			}
			if(rs!=null)
			{
				rs.close();
			}

			if(db != null) db.shutDown();

			if(index==7)
			{
				throw new Exception("Xin loi. Khong co bao cao voi dieu kien da chon...!!!");
			}
		} 
		catch (Exception err)
		{
			err.printStackTrace();
			throw new Exception("Khong the tao duoc bao cao trong thoi gian nay. Error :" + err.toString());
		}
	}

	private void CreatePivotTableDaChot(OutputStream out, IChiTieuNhaphanphoi obj) throws Exception
	{	
		dbutils db = new dbutils();
		try
		{
			com.aspose.cells.Workbook workbook = new com.aspose.cells.Workbook();			
			workbook.setFileFormatType(FileFormatType.EXCEL97TO2003);
			ResultSet rsCol = db.get(setQuerySoCot(obj.getID()));
			String col = "";
			while (rsCol.next())
			{
				col = rsCol.getString("caccot");
			}
			String colReplace = col.replace("]","").replace("[","");
			
			String[] caccotArr = colReplace.split(",");
			
			List<chitieuNhom> lNhom = new ArrayList<chitieuNhom>();
			
			for( int i = 0 ; i < caccotArr.length; i++)
				lNhom.add( new chitieuNhom(caccotArr[i].split("-")) );
				
			if(col.trim().length() <=0)
			{
				throw new Exception("Loi Col rong~");
			}
			CreateStaticHeaderDaChot(workbook, obj,col,lNhom,db);
			FillDataDaChot(workbook, col, obj,lNhom,db);
			workbook.save(out);
			db.shutDown();
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
			db.shutDown();
			throw new Exception(ex.getMessage());
		}
	}
	
	private void CreateStaticHeaderDaChot(com.aspose.cells.Workbook workbook, IChiTieuNhaphanphoi obj,String col,List<chitieuNhom> lNhom,dbutils db) throws Exception
	{
		try
		{
			Hashtable<String ,String> loaiValue = getHashLoai(db);
			com.aspose.cells.Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Sheet1");
			com.aspose.cells.Cells cells = worksheet.getCells();

			Style style;
			Font font = new Font();
			font.setColor(Color.RED);//mau chu
			font.setSize(16);// size chu
			font.setBold(true);

			cells.setRowHeight(0, 20.0f);
			com.aspose.cells.Cell cell = cells.getCell("A1");
			style = cell.getStyle();
			style.setFont(font); 
			style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu 	        

			ReportAPI.getCellStyle(cell,Color.RED, true, 14, "Template Upload chỉ tiêu Nhân viên");
			cells.setRowHeight(3, 18.0f);
			cell = cells.getCell("A3");

			ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Tháng : " + obj.getThang() + "" );

			cells.setRowHeight(3, 18.0f);
			cell = cells.getCell("C3"); 
			ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Năm : " + obj.getNam() + "" );

			cell = cells.getCell("J3"); 
			ReportAPI.getCellStyle(cell,Color.RED,true, 9, "Diễn giải Nhóm");
			cell = cells.getCell("K3"); 
			ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "1:Bán ra(Doanh số)" );
			cell = cells.getCell("K4"); 
			ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "2:Bán ra(Số lượng)" );
			cell = cells.getCell("L3"); 
			ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "3:Mua vào(Doanh số)" );
			cell = cells.getCell("L4"); 
			ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "4:Mua vào(Số lượng)" );
			cell = cells.getCell("M3"); 
			ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "6:Tồn kho theo quy định(Ngày)" );

			int cotdautien = 0;
			int dongbatdauHeader = 4;

			cell = cells.getCell(dongbatdauHeader,cotdautien); cell.setValue("Số TT");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			cell = cells.getCell(dongbatdauHeader+1,cotdautien); cell.setValue("");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			cell = cells.getCell(dongbatdauHeader+2,cotdautien++); cell.setValue("");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			
			cell = cells.getCell(dongbatdauHeader,cotdautien); cell.setValue("Mã NPP");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			cell = cells.getCell(dongbatdauHeader+1,cotdautien); cell.setValue("");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			cell = cells.getCell(dongbatdauHeader+2,cotdautien++); cell.setValue("");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			
			cell = cells.getCell(dongbatdauHeader,cotdautien); cell.setValue("Tên NPP");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);	
			cell = cells.getCell(dongbatdauHeader+1,cotdautien); cell.setValue("");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			cell = cells.getCell(dongbatdauHeader+2,cotdautien++); cell.setValue("");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			
			cell = cells.getCell(dongbatdauHeader,cotdautien); cell.setValue(" Doanh Số bán ra");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			cell = cells.getCell(dongbatdauHeader+1,cotdautien); cell.setValue("");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			cell = cells.getCell(dongbatdauHeader+2,cotdautien++); cell.setValue("");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			
			cell = cells.getCell(dongbatdauHeader,cotdautien); cell.setValue(" Số lượng bán ra ");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			cell = cells.getCell(dongbatdauHeader+1,cotdautien); cell.setValue("");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			cell = cells.getCell(dongbatdauHeader+2,cotdautien++); cell.setValue("");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			
			cell = cells.getCell(dongbatdauHeader,cotdautien); cell.setValue(" Doanh số mua vào ");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			cell = cells.getCell(dongbatdauHeader+1,cotdautien); cell.setValue("");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			cell = cells.getCell(dongbatdauHeader+2,cotdautien++); cell.setValue("");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);

			cell = cells.getCell(dongbatdauHeader,cotdautien); cell.setValue(" Số lượng mua vào ");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			cell = cells.getCell(dongbatdauHeader+1,cotdautien); cell.setValue("");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			cell = cells.getCell(dongbatdauHeader+2,cotdautien++); cell.setValue("");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			
			cell = cells.getCell(dongbatdauHeader,cotdautien); cell.setValue(" Tổng số đơn hàng");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			cell = cells.getCell(dongbatdauHeader+1,cotdautien); cell.setValue("");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			cell = cells.getCell(dongbatdauHeader+2,cotdautien++); cell.setValue("");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			
			cell = cells.getCell(dongbatdauHeader,cotdautien); cell.setValue(" Tỷ lệ giao hàng thành công (%) ");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			cell = cells.getCell(dongbatdauHeader+1,cotdautien); cell.setValue("");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			cell = cells.getCell(dongbatdauHeader+2,cotdautien++); cell.setValue("");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			
			cell = cells.getCell(dongbatdauHeader,cotdautien); cell.setValue(" Thời gian giao hàng TB (giờ)");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			cell = cells.getCell(dongbatdauHeader+1,cotdautien); cell.setValue("");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			cell = cells.getCell(dongbatdauHeader+2,cotdautien++); cell.setValue("");
			ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);
			
			for( int i = 0; i < lNhom.size();i ++)
			{
				cell = cells.getCell(dongbatdauHeader,cotdautien); cell.setValue(loaiValue.get(lNhom.get(i).nsp_fk));
				ReportAPI.setCellBackground(cell, Color.SILVER, BorderLineType.THIN, true, 0);			
				
				cell = cells.getCell(dongbatdauHeader +1,cotdautien); cell.setValue(lNhom.get(i).loai);
				ReportAPI.setCellBackground(cell, Color.SILVER, BorderLineType.THIN, true, 0);
				cell = cells.getCell(dongbatdauHeader +2,cotdautien++); cell.setValue(lNhom.get(i).nsp_fk);
				ReportAPI.setCellBackground(cell, Color.SILVER, BorderLineType.THIN, true, 0);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new Exception("Khong the tao duoc Header cho bao cao...");
		}
	}

	private void FillDataDaChot(com.aspose.cells.Workbook workbook, String col, IChiTieuNhaphanphoi obj,List<chitieuNhom> lNhom,dbutils db) throws Exception
	{
		com.aspose.cells.Worksheets worksheets = workbook.getWorksheets();
		com.aspose.cells.Worksheet worksheet = worksheets.getSheet(0);
		com.aspose.cells.Cells cells = worksheet.getCells();

		ResultSet	rs = db.get(setQuery(obj.getID(),col));

		int index = 7;
		NumberFormat dfInt = new DecimalFormat("#,###,##0.##");
		NumberFormat dffloat = new DecimalFormat("#,###,###.###");
		try
		{
			com.aspose.cells.Cell cell = null;
			int i = 1;
			while (rs.next())
			{

				int cotdautien = 0;

				cell = cells.getCell(index,cotdautien++); cell.setValue(i++);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
				cell = cells.getCell(index,cotdautien++); cell.setValue(rs.getString("manv"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
				cell = cells.getCell(index,cotdautien++); cell.setValue(rs.getString("tennv"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);

				cell = cells.getCell(index,cotdautien++); cell.setValue(rs.getDouble("DoanhSoBanRa"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 41);
				
				cell = cells.getCell(index,cotdautien++); cell.setValue(rs.getDouble("SoLuongBanRa"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 41);
				
				cell = cells.getCell(index,cotdautien++); cell.setValue(rs.getDouble("DoanhSoMuaVao"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 41);
				
				cell = cells.getCell(index,cotdautien++); cell.setValue(rs.getDouble("SoLuongMuaVao"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 41);
				
				cell = cells.getCell(index,cotdautien++); cell.setValue(rs.getDouble("SoDonHang"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 41);
				
				cell = cells.getCell(index,cotdautien++); cell.setValue(rs.getDouble("TyleGiaoHang"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 41);
				
				cell = cells.getCell(index,cotdautien++); cell.setValue(rs.getDouble("ThoiGianGiaoHang"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 41);

				i = cotdautien;
				for(int k = 0 ; k < lNhom.size();k++)
				{
					i++;
					cell = cells.getCell(index,cotdautien++); cell.setValue(rs.getDouble(lNhom.get(k).nsp_fk +"-" +lNhom.get(k).loai));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 41);
				}
				index++;
			}
			
			cell = cells.getCell(index,0);	
			ReportAPI.getCellStyle(cell,Color.RED,true, 9, "TỔNG:");
			int dongbatdau = 7;

			for (int cottangdan = 3 ; cottangdan< i; cottangdan++)
			{
				cell = cells.getCell(index,cottangdan);
				String formula = "=SUM("+GetExcelColumnName(cottangdan+1)+(dongbatdau +1 ) +":"+GetExcelColumnName(cottangdan+1)+(index)+")";
				System.out.println("formula = " + formula);
				cell.setFormula(formula);
				ReportAPI.setCellBackground(cell, Color.LIME, BorderLineType.THIN, true, 41);
			}
			
			if(rs!=null)
			{
				rs.close();
			}

			if(index==7)
			{
				throw new Exception("Xin loi. Khong co bao cao voi dieu kien da chon...!!!");
			}
		} 
		catch (Exception err)
		{
			err.printStackTrace();
			throw new Exception("Khong the tao duoc bao cao trong thoi gian nay. Error :" + err.toString());
		}
	}

	public class chitieuNhom
	{
		public String nsp_fk ="";
		public String loai= "";
		public chitieuNhom(String value[])
		{
			this.nsp_fk = value[0];
			this.loai =value[1];
		}
	}
	
	public Hashtable<String ,String> getHashLoai( dbutils db)
	{
		Hashtable<String ,String> value = new Hashtable<String ,String>();
		String sql = " select PK_SEQ,TEN from NHOMSANPHAMCHITIEU ";
		ResultSet rs = db.get(sql);
		try
		{
			while (rs.next())
			{
				value.put(rs.getString("pk_seq").trim(), rs.getString("TEN").trim());
			}
			rs.close();
		}
		catch (Exception er)
		{
			er.printStackTrace();
		}

		return value;
	}

	public String setQuerySoCot(String id)
	{
		String tongCol ="CHITIEUNHAPHANPHOI_NPP";
		String chitieuCol ="CHITIEUNHAPHANPHOI_NPP_NSP";
		
		String query=   "\n declare @cols varchar(max) -- " + 
				 "\n set @cols = '[' + REPLACE(   -- " + 
				 "\n 			(SELECT distinct cast(ct.nsp_fk as varchar) + '-' +cast(ct.LOAI as varchar)  as [data()]   -- " + 
				 "\n 								FROM "+chitieuCol+" ct inner join "+tongCol+" t   -- " + 
				 "\n 								on ct.CTNPP_NPP_FK = t.pk_seq  -- " + 
				 "\n 								 WHERE t.CTNPP_FK = "+id+" -- " + 
				 "\n 								 order by  cast(ct.nsp_fk as varchar) + '-' +cast(ct.LOAI as varchar) -- " + 
				 "\n 			FOR XML PATH('')  -- " + 
				 "\n 				),' ','],[') + ']' -- " + 
				 "\n select @cols as caccot -- " ;
		
		System.out.println("setQuerySoCot = " + query);
		return query;
	}
	
	public String setQuery(String id,String input)
	{
		String tongCol ="CHITIEUNHAPHANPHOI_NPP";
		String chitieuCol ="CHITIEUNHAPHANPHOI_NPP_NSP";
		
		String query=    "\n SELECT * -- " + 
				 "\n from -- " + 
				 "\n ( -- " + 
				 "\n 	select nv.pk_Seq as manv, nv.ten as tennv ,b.*,cast(a.nsp_fk as varchar) + '-' +cast(a.LOAI as varchar) as col ,DoanhSo,CTNPP_NPP_FK -- " + 
				 "\n 	from "+chitieuCol+"  a  -- " + 
				 "\n 	inner join "+tongCol+" b on a.CTNPP_NPP_FK = b.pk_seq -- " + 
				 "\n 	inner join nhaphanphoi nv  on nv.pk_Seq = b.npp_fk -- " + 
				 "\n 	where CTNPP_FK = "+id+" -- " + 
				 "\n  ) -- " + 
				 "\n  DATA PIVOT ( sum(DoanhSo) FOR col IN ( "+input+" ) ) AS pvt -- " ;
		 System.out.println("setQuery = " + query);
		return query;
		
	}

	private String GetExcelColumnName(int columnNumber)
	{
		int dividend = columnNumber;
		String columnName = "";
		int modulo;

		while (dividend > 0)
		{
			modulo = (dividend - 1) % 26;
			columnName = (char)(65 + modulo) + columnName;
			dividend = (int)((dividend - modulo) / 26);
		} 

		return columnName;
	}
	
	
}
