package geso.dms.distributor.servlets.dieuchinhtonkho;

import geso.dms.center.db.sql.dbutils;
import geso.dms.distributor.beans.dieuchinhtonkho.IDieuchinhtonkho;
import geso.dms.distributor.beans.dieuchinhtonkho.imp.Dieuchinhtonkho;
import geso.dms.distributor.beans.dieuchinhtonkho.IDieuchinhtonkhoList;
import geso.dms.distributor.beans.dieuchinhtonkho.imp.DieuchinhtonkhoList;
import geso.dms.distributor.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.OutputStream;

public class DieuchinhtonkhoUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public DieuchinhtonkhoUpdateSvl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/AHF/index.jsp");
		} else
		{

			String nextJSP;
			Utility util = new Utility();

			String querystring = request.getQueryString();

			userId = util.antiSQLInspection(request.getParameter("userId"));

			String id = util.getId(querystring);
			String action = util.getAction(querystring);

			IDieuchinhtonkho dctkBean = (IDieuchinhtonkho) new Dieuchinhtonkho();
			dctkBean.setUserId(userId);
			dctkBean.setId(id);
			dctkBean.setNppId(util.antiSQLInspection(request.getParameter("nppId")));
			dctkBean.initUpdate("1");
			session.setAttribute("dctkBean", dctkBean);
			session.setAttribute("khoId", dctkBean.getKhoId());
			session.setAttribute("nppId", dctkBean.getNppId());
			session.setAttribute("kbhId", dctkBean.getKbhId());
			session.setAttribute("dvkdId", dctkBean.getDvkdId());
			session.setAttribute("ngayghinhan", dctkBean.getNgaydc());
			session.setAttribute("dcid", dctkBean.getId());
			
			
			nextJSP = "/AHF/pages/Distributor/DieuChinhTonKhoUpdate.jsp";
			response.sendRedirect(nextJSP);
		}

	}

	public String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		return dateFormat.format(date);
	}

	 
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		NumberFormat formatter = new DecimalFormat("#,###,###.###");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/AHF/index.jsp");
		} else
		{
			Utility util = new Utility();
			IDieuchinhtonkho dctkBean = (IDieuchinhtonkho) new Dieuchinhtonkho();
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			String id;
			String nppId;
			String khoId;
			String kbhId;
			String dvkdId;
			String lydodc;
			String ngaydc;
			String action = "";
			String sodong = "0";

			String[] spId = null;
			//String[] spNGAYNHAPKHO  = null;
			
			String[] spMa = null;
			String[] spTen = null;
			String[] spTon = null;
			String[] spTonMoi = null;
			String[] spDieuchinh = null;
			String[] spDongia = null;
			String[] spThanhtien = null;
			String[] spDonvi = null;
			MultipartRequest multi = null;

			String contentType = request.getContentType();
			if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
				multi = new MultipartRequest(request, "C:\\java-tomcat\\data\\", 20000000, "UTF-8");
			String removename = util.antiSQLInspection(request.getParameter("removename"));
			if (removename == null)
				removename = "";
			String[] filenames = request.getParameterValues("filedk");
			String s = "";
			if(filenames != null)
			{
				for(int i=0; i< filenames.length; i++)
					s += filenames[i] + ",";
				if(s.length() > 0)
					s = s.substring(0, s.length() - 1);

			}
			System.out.println("s =" + s + ";");
			dctkBean.setFile(s);

			if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
			{

				id = util.antiSQLInspection(multi.getParameter("id"));
				if (id != null)
					dctkBean.setId(id);

				action = multi.getParameter("action");

				userId = util.antiSQLInspection(multi.getParameter("userId"));
				if (userId == null)
				{
					response.sendRedirect("/DDT");
				} else
				{
					dctkBean.setUserId(userId);
				}
				nppId = util.antiSQLInspection(multi.getParameter("nppId") == null ? "" : multi.getParameter("nppId"));
				dctkBean.setNppId(nppId);

				khoId = util.antiSQLInspection(multi.getParameter("khoId") == null ? "" : multi.getParameter("khoId"));
				dctkBean.setKhoId(khoId);

				kbhId = util.antiSQLInspection(multi.getParameter("kbhId") == null ? "" : multi.getParameter("kbhId"));
				dctkBean.setKbhId(kbhId);

				dvkdId = util.antiSQLInspection(multi.getParameter("dvkdId") == null ? "" : multi.getParameter("dvkdId"));
				dctkBean.setDvkdId(dvkdId);

				ngaydc = util.antiSQLInspection(multi.getParameter("ngaydc") == null ? "" : multi.getParameter("ngaydc"));
				dctkBean.setNgaydc(ngaydc);

				lydodc = util.antiSQLInspection(multi.getParameter("lydo") == null ? "" : multi.getParameter("lydo"));
				dctkBean.setLydodc(lydodc);

				sodong = util.antiSQLInspection(multi.getParameter("sodong") == null ? "" : multi.getParameter("sodong"));

				if (sodong == null || sodong.trim().length() <= 0)
					sodong = "0";
				int sd = Integer.parseInt(sodong)+30;
				dctkBean.setSodong(sd+"");

				filenames = multi.getParameterValues("filedk");
				s = "";
				if(filenames != null)
				{
					for(int i=0; i< filenames.length; i++)
						s += filenames[i] + ",";
					if(s.length() > 0)
						s = s.substring(0, s.length() - 1);

				}
				Enumeration files = multi.getFileNames();
				String filenameu = "";
				String filedk = "";
				while (files.hasMoreElements())
				{
					String name = (String) files.nextElement();
					filenameu = multi.getFilesystemName(name);
					if(name != null)
						if(name.equals("fileup"))
							filedk = multi.getFilesystemName(name);

				}

				spId = multi.getParameterValues("spId");
				spMa = multi.getParameterValues("spMa");
				spTen = multi.getParameterValues("spTen");
				spTon = multi.getParameterValues("tonkho");
				spTonMoi = multi.getParameterValues("tonkhomoi");
				spDieuchinh = multi.getParameterValues("dieuchinh");
				spDongia = multi.getParameterValues("dongia");
				spThanhtien = multi.getParameterValues("thanhtien");
				spDonvi = multi.getParameterValues("donvi");
			/*	spNGAYNHAPKHO = multi.getParameterValues("ngaynhapkho");*/

				int n = 0;
				if (spMa != null)
				{
					int size = spMa.length;
					for (int i = 0; i < size; i++)
					{
						if (spTonMoi[i].length() > 0)
						{
							spTonMoi[i] = spTonMoi[i].replace(",", "");
							spDongia[i] = spDongia[i].replace(",", "");
							spTon[i] = spTon[i].replace(",", "");
							spThanhtien[i] = spThanhtien[i].replace(",", "");
							n++;
						}
					}
					dctkBean.setSpId(spId);
					dctkBean.setMasp(spMa);
					dctkBean.setTenSp(spTen);
					dctkBean.setSoluongle(spTonMoi);
					dctkBean.setTkht(spTon);
					dctkBean.setDonvi(spDonvi);
					dctkBean.setGiamua(spDongia);
					dctkBean.setTtien(spThanhtien);
					dctkBean.setDc(spDieuchinh);
					/*dctkBean.setNgaynhapkho(spNGAYNHAPKHO);*/
					
					dctkBean.setSize(n + "");
				}

			
				if(filedk != null && filedk.length() > 0){
					if(s.length() > 0)
						dctkBean.setFile(s + "," + filedk);
					else
						dctkBean.setFile(filedk);
					System.out.println("___S:" + s + "," + filedk);
				}
				String filename = "C:\\java-tomcat\\data\\" + filenameu;
				if(filenameu != null)
					if (filename.length() > 0 && filenameu.length() > 0)
					{
						dbutils db = new dbutils();
						try
						{
							File file = new File(filename);
							Workbook workbook;
							workbook = Workbook.getWorkbook(file);
							Sheet sheet = workbook.getSheet(0);
							int indexRow = 8;

							db.getConnection().setAutoCommit(false);
							String query = " DELETE FROM UPLOAD_KIEMKE ";
							if (!db.update(query))
							{
								db.getConnection().rollback();
							}

							int i = 0;
							String msg = "";
							query = "";
							double tonmoi = 0;
							for (int j = indexRow; j < sheet.getRows(); j++)
							{
								Cell[] cells = sheet.getRow(j);
								if (cells != null && cells.length > 0)
								{
									
									if (cells[1].getContents() != null && cells[1].getContents().length() > 0)
									{
										tonmoi = 0;
										String spMA = cells[1].getContents().toString();
										String ngaynhapkho = ""; //cells[4].getContents().toString();
										
										if (cells[3].getContents().toString().length() > 0)
										{
											try
											{
												tonmoi = Float.parseFloat(cells[3].getContents().toString().replace(",", ""));
												System.out.println("SPMa: "+spMA+" - tonmoi : "+ tonmoi);
											} catch (Exception er)
											{
												msg = msg + "Số lượng sản phẩm (" + spMA + ") dòng : " + (i + 1) + " không xác định. \n ";
											}
										}
										/*if (cells[4].getContents() == null  || cells[4].getContents().toString().length()==0 )
										{
											 
												msg = msg + "Ngày nhập kho của sản phẩm (" + spMA + ") dòng : " + (i + 1) + " không xác định. \n ";
											 
										}*/
										
										
										if (query.length() == 0)
										{
											query = " SELECT " + nppId + "," + kbhId + "," + khoId + ",'" + spMA + "'," + tonmoi + ", '"+ngaynhapkho+"'";
										} else
										{
											query += " UNION ALL SELECT " + nppId + "," + kbhId + "," + khoId + ",'" + spMA + "'," + tonmoi + " , '"+ngaynhapkho+"'";
										}
									}
								}
								i++;
							}
							if (msg.length() > 0)
							{
								dctkBean.setMessage(msg);
							}
							query = " INSERT INTO UPLOAD_KIEMKE (NPP_FK,KBH_FK,KHO_FK,SANPHAMMA,TONMOI,ngaynhapkho )  " + query;
							System.out.print("[INSERT UPLOAD_KIEMKIE ]" + query);
							if (!db.update(query))
							{
								msg = msg + "Không thực hiện lưu vào bảng tạm, vui lòng kiểm tra lại câu lệnh:" + query + "\n";
							}

							query = "SELECT * FROM UPLOAD_KIEMKE " + " WHERE SANPHAMMA NOT IN " + " ( " + "	SELECT MA FROM SANPHAM " + " )";
							ResultSet rs = db.get(query);
							while (rs.next())
							{
								msg += "Sản phẩm " + rs.getString("SANPHAMMA") + " không có trong hệ thống ! \n";
							}
							if (rs != null)
								rs.close();

							query = " SELECT SANPHAMMA FROM UPLOAD_KIEMKE GROUP BY SANPHAMMA HAVING COUNT(*)>1 ";
							rs = db.get(query);
							while (rs.next())
							{
								msg += "Sản phẩm " + rs.getString("SANPHAMMA") + " trong file bị trùng  ! \n";
							}
							if (rs != null)
								rs.close();

							if(khoId.length()<=0)
							{
								msg="Vui lòng chọn kho điều chỉnh ";
							}

							if (msg.length() > 0)
							{
								dctkBean.setMessage(msg);
							}



							if (msg.length() <= 0)
							{
								/*query = " SELECT KK.NGAYNHAPKHO ,SP.PK_SEQ AS SPID,SP.MA AS SPMA,SP.TEN AS SPTEN,DVDL.DONVI as spDonVi,isnull((select available from NHAPP_KHO where NPP_FK='" + nppId + "'  and KBH_FK='"+kbhId+"' and SANPHAM_FK=sp.PK_SEQ and KHO_FK='" + khoId + "' and NGAYNHAPKHO=KK.NGAYNHAPKHO ),0) as spTon , " +							
										" isnull( ( select GIAMUANPP from BGMUANPP_SANPHAM bgsp where bgsp.SANPHAM_FK = sp.PK_SEQ and BGMUANPP_FK in   "+
										" (	select top(1) PK_SEQ from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK  "+ 
										" where bg.TRANGTHAI = '1' and bg_npp.NPP_FK = '" + nppId + "' and bg.DVKD_FK = sp.dvkd_Fk and bg.KENH_FK = '"+kbhId+"'  "+
										" order by bg.TUNGAY desc )   "+
										" ),0 )as spGiaMua "+
										" , kk.TonMoi as spTonMoi , KK.TONMOI-isnull((select available from NHAPP_KHO_chitiet "
										+ "  where NPP_FK='" + nppId + "'  and KBH_FK='"+kbhId+"' and SANPHAM_FK=sp.PK_SEQ and KHO_FK='" + khoId + "' and NGAYNHAPKHO=KK.NGAYNHAPKHO  ),0) AS spDIEUCHINH "+ 
										" FROM UPLOAD_KIEMKE  KK INNER JOIN SANPHAM SP ON SP.MA=KK.SANPHAMMA " + 
										"  INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ =SP.DVDL_FK order by sp.ma asc ";*/
								
								query = " SELECT KK.NGAYNHAPKHO ,SP.PK_SEQ AS SPID,SP.MA AS SPMA,SP.TEN AS SPTEN,DVDL.DONVI as spDonVi,isnull((select sum(available) available from NHAPP_KHO where NPP_FK='" + nppId + "'  and KBH_FK='"+kbhId+"' and SANPHAM_FK=sp.PK_SEQ and KHO_FK='" + khoId + "' ),0) as spTon , " +							
										" isnull( ( select GIAMUANPP from BGMUANPP_SANPHAM bgsp where bgsp.SANPHAM_FK = sp.PK_SEQ and BGMUANPP_FK in   "+
										" (	select top(1) PK_SEQ from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK  "+ 
										" where bg.TRANGTHAI = '1' and bg_npp.NPP_FK = '" + nppId + "' and bg.DVKD_FK = sp.dvkd_Fk and bg.KENH_FK = '"+kbhId+"'  "+
										" order by bg.TUNGAY desc )   "+
										" ),0 )as spGiaMua "+
										" , kk.TonMoi as spTonMoi , KK.TONMOI - isnull((select sum(available) available from NHAPP_KHO_chitiet "
										+ "  where NPP_FK='" + nppId + "'  and KBH_FK='"+kbhId+"' and SANPHAM_FK=sp.PK_SEQ and KHO_FK='" + khoId + "' ),0) AS spDIEUCHINH "+ 
										" FROM UPLOAD_KIEMKE  KK INNER JOIN SANPHAM SP ON SP.MA=KK.SANPHAMMA " + 
										"  INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ =SP.DVDL_FK order by sp.ma asc ";
								
								System.out.println("[UPLOAD_KIEMKE]"+query);
								rs = db.get(query);
								i = 0;
								String spID = "";
								String spMA = "";
								String spTEN = "";
								String spTON = "";
								String spDONVI = "";
								String spTONMOI = "";
								String spGIAMUA = "";
								String spTHANHTIEN = "";
								String spDIEUCHINH = "";
								//String spNGAYNHAPKHO_  = "";
								
								while (rs.next())
								{
									spID += rs.getString("SPID") + "__";
									System.out.println("[spId]"+spID);
									spMA += rs.getString("SPMA") + "__";
									spTEN += rs.getString("SPTEN") + "__";
									spTON += rs.getString("spTON") + "__";
									spDONVI += rs.getString("spDonVi") + "__";
									spTONMOI += rs.getString("spTonMoi") + "__";
									spGIAMUA +=formatter.format( rs.getDouble("spGIAMUA") )+ "__";
									spDIEUCHINH += rs.getString("spDIEUCHINH") + "__";
									//spNGAYNHAPKHO_ += rs.getString("NGAYNHAPKHO") + "__";
									spTHANHTIEN +=formatter.format(rs.getDouble("spDIEUCHINH")*rs.getDouble("spGIAMUA"))+"__";
								}

								if (spID.trim().length() > 0)
								{
									spID = spID.substring(0, spID.length() - 2);
									spId = spID.split("__");

									spMA = spMA.substring(0, spMA.length() - 2);
									spMa = spMA.split("__");

									spTEN = spTEN.substring(0, spTEN.length() - 2);
									spTen = spTEN.split("__");

									spDONVI = spDONVI.substring(0, spDONVI.length() - 2);
									spDonvi = spDONVI.split("__");

									spTON = spTON.substring(0, spTON.length() - 2);
									spTon = spTON.split("__");

									spTONMOI = spTONMOI.substring(0, spTONMOI.length() - 2);
									spTonMoi = spTONMOI.split("__");

									spGIAMUA = spGIAMUA.substring(0, spGIAMUA.length() - 2);
									spDongia = spGIAMUA.split("__");

									spTHANHTIEN = spTHANHTIEN.substring(0, spTHANHTIEN.length() - 2);
									spThanhtien = spTHANHTIEN.split("__");

									spDIEUCHINH = spDIEUCHINH.substring(0, spDIEUCHINH.length() - 2);
									spDieuchinh = spDIEUCHINH.split("__");
									/*spNGAYNHAPKHO=spNGAYNHAPKHO_.split("__"); */
									
									dctkBean.setSize(spDIEUCHINH.split("__")+"");
								}

								if (rs != null)
									rs.close();
								db.getConnection().commit();
								db.getConnection().setAutoCommit(true);
							}
						} catch (Exception e)
						{
							e.printStackTrace();
						} finally
						{
							db.shutDown();
						}

						dctkBean.setSpId(spId);
						dctkBean.setMasp(spMa);
						dctkBean.setTenSp(spTen);
						dctkBean.setSoluongle(spTonMoi);
						dctkBean.setTkht(spTon);
						dctkBean.setDonvi(spDonvi);
						dctkBean.setGiamua(spDongia);
						dctkBean.setTtien(spThanhtien);
						dctkBean.setDc(spDieuchinh);
						/*dctkBean.setNgaynhapkho(spNGAYNHAPKHO);*/
					}
			} else
			{
				action = request.getParameter("action");

				id = util.antiSQLInspection(request.getParameter("id"));
				if (id != null)
					dctkBean.setId(id);

				userId = util.antiSQLInspection(request.getParameter("userId"));
				if (userId == null)
				{
					response.sendRedirect("/AHF");
				} else
				{
					dctkBean.setUserId(userId);
				}
				nppId = util.antiSQLInspection(request.getParameter("nppId") == null ? "" : request.getParameter("nppId"));
				dctkBean.setNppId(nppId);

				khoId = util.antiSQLInspection(request.getParameter("khoId") == null ? "" : request.getParameter("khoId"));
				dctkBean.setKhoId(khoId);

				kbhId = util.antiSQLInspection(request.getParameter("kbhId") == null ? "" : request.getParameter("kbhId"));
				dctkBean.setKbhId(kbhId);

				dvkdId = util.antiSQLInspection(request.getParameter("dvkdId") == null ? "" : request.getParameter("dvkdId"));
				dctkBean.setDvkdId(dvkdId);

				ngaydc = util.antiSQLInspection(request.getParameter("ngaydc") == null ? "" : request.getParameter("ngaydc"));
				dctkBean.setNgaydc(ngaydc);

				lydodc = util.antiSQLInspection(request.getParameter("lydo") == null ? "" : request.getParameter("lydo"));
				dctkBean.setLydodc(lydodc);

				sodong = util.antiSQLInspection(request.getParameter("sodong") == null ? "" : request.getParameter("sodong"));
				if (sodong == null || sodong.trim().length() <= 0)
					sodong = "0";
				int sd = Integer.parseInt(sodong)+30;
				dctkBean.setSodong(sd+"");


				spId = request.getParameterValues("spId");
				spMa = request.getParameterValues("spMa");
				spTen = request.getParameterValues("spTen");
				spTon = request.getParameterValues("tonkho");
				spTonMoi = request.getParameterValues("tonkhomoi");
				spDieuchinh = request.getParameterValues("dieuchinh");
				spDongia = request.getParameterValues("dongia");
				spThanhtien = request.getParameterValues("thanhtien");
				spDonvi = request.getParameterValues("donvi");
				
				/*spNGAYNHAPKHO = request.getParameterValues("ngaynhapkho");*/
				

				int n = 0;
				if (spMa != null)
				{
					int size = spMa.length;
					for (int i = 0; i < size; i++)
					{
						if (spTonMoi[i].length() > 0)
						{
							spTonMoi[i] = spTonMoi[i].replace(",", "");
							spDongia[i] = spDongia[i].replace(",", "");
							spTon[i] = spTon[i].replace(",", "");
							spThanhtien[i] = spThanhtien[i].replace(",", "");
							n++;
						}
					}
					dctkBean.setSpId(spId);
					dctkBean.setMasp(spMa);
					dctkBean.setTenSp(spTen);
					dctkBean.setSoluongle(spTonMoi);
					dctkBean.setTkht(spTon);
					dctkBean.setDonvi(spDonvi);
					dctkBean.setGiamua(spDongia);
					dctkBean.setTtien(spThanhtien);
					dctkBean.setDc(spDieuchinh);
					/*dctkBean.setNgaynhapkho(spNGAYNHAPKHO);*/
					
					dctkBean.setSize(n + "");
				}

			}

			session.setAttribute("khoId", khoId);
			session.setAttribute("nppId", nppId);
			session.setAttribute("kbhId", kbhId);
			session.setAttribute("dvkdId", dvkdId);
			session.setAttribute("ngayghinhan", dctkBean.getNgaydc());

			if (action.equals("save"))
			{
				if (id == null)
				{
					if (!(dctkBean.CreateDctk(request)))
					{
						dctkBean.init0();
						session.setAttribute("dctkBean", dctkBean);
						String nextJSP = "/AHF/pages/Distributor/DieuChinhTonKhoNew.jsp";
						response.sendRedirect(nextJSP);
					} else
					{
						IDieuchinhtonkhoList obj = new DieuchinhtonkhoList();
						obj.setUserId(userId);
						obj.init0();
						obj.createDctklist("");
						session.setAttribute("obj", obj);

						String nextJSP = "/AHF/pages/Distributor/DieuChinhTonKho.jsp";
						response.sendRedirect(nextJSP);
					}
				} else
				{
					if (!(dctkBean.UpdateDctk(request)))
					{
						dctkBean.initUpdate("2");
						session.setAttribute("dctkBean", dctkBean);
						String nextJSP = "/AHF/pages/Distributor/DieuChinhTonKhoUpdate.jsp";
						response.sendRedirect(nextJSP);
					} else
					{
						IDieuchinhtonkhoList obj = new DieuchinhtonkhoList();
						obj.setUserId(userId);
						obj.init0();
						obj.createDctklist("");
						session.setAttribute("obj", obj);
						String nextJSP = "/AHF/pages/Distributor/DieuChinhTonKho.jsp";
						response.sendRedirect(nextJSP);

					}
				}

			}
			else if(action.equals("remove")){
				s = "";
				for(int i=0; i< filenames.length; i++)
					if(!filenames[i].equals(removename))
						s += filenames[i] + ",";
				if(s.length() > 0)
					s = s.substring(0, s.length() - 1);

				PrintWriter out = response.getWriter();

				if (!this.deletefile("C:\\java-tomcat\\data\\" + removename))
				{
					out.write("Lỗi xóa file");
				} else
				{
					out.write("");
				}

				dctkBean.setFile(s);

				String nextJSP;
				if (id == null)
				{
					dctkBean.initNew();
					nextJSP = "/AHF/pages/Distributor/DieuChinhTonKhoNew.jsp";
				} else
				{
					dctkBean.initUpdate("2");
					nextJSP = "/AHF/pages/Distributor/DieuChinhTonKhoUpdate.jsp";
				}
				session.setAttribute("dctkBean", dctkBean);
				response.sendRedirect(nextJSP);
			}else
				if (action.equals("download"))
				{
					System.out.println("___Vao DOWNLOAD FILE....");
					String fileName = request.getParameter("tenfile");
					if (fileName == null)
						fileName = "";

					if (fileName.trim().length() > 0)
					{
						try
						{
							FileInputStream fileToDownload = new FileInputStream("C:\\java-tomcat\\data\\" + fileName);
							ServletOutputStream output = response.getOutputStream();
							response.setContentType("application/octet-stream");

							System.out.println(fileName);
							response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
							response.setContentLength(fileToDownload.available());
							int c;
							while ((c = fileToDownload.read()) != -1)
							{
								output.write(c);
							}
							output.flush();
							output.close();
							fileToDownload.close();
						} catch (Exception e)
						{
							System.out.println("___Loi DOWNLOAD file: " + e.getMessage());
						}
					} else
					{
						dctkBean.setUserId(userId);
						dctkBean.setId(id);
						

						String nextJSP;
						if (id == null)
						{
							dctkBean.initNew();
							nextJSP = "/AHF/pages/Distributor/DieuChinhTonKhoNew.jsp";
						} else
						{
							dctkBean.initUpdate("2");
							nextJSP = "/AHF/pages/Distributor/DieuChinhTonKhoUpdate.jsp";
						}
						session.setAttribute("dctkBean", dctkBean);
						response.sendRedirect(nextJSP);
					}
				} 
				else if (action.equals("xuatexcel"))
				{
					XuatFileExcel(response, request, dctkBean);
				} else
				{
					String nextJSP;
					if (id == null)
					{
						dctkBean.initNew();
						nextJSP = "/AHF/pages/Distributor/DieuChinhTonKhoNew.jsp";
					} else
					{
						dctkBean.initUpdate("2");
						nextJSP = "/AHF/pages/Distributor/DieuChinhTonKhoUpdate.jsp";
					}
					session.setAttribute("dctkBean", dctkBean);
					response.sendRedirect(nextJSP);
				}
		}
	}
	private boolean deletefile(String file)
	{
		System.out.println(file);
		File f1 = new File(file);
		boolean success = f1.delete();
		if (!success)
		{
			return false;
		} else
		{
			return true;
		}
	}
	private void XuatFileExcel(HttpServletResponse response, HttpServletRequest request, IDieuchinhtonkho dctkBean) throws IOException
	{
		OutputStream out1 = null;
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=DieuChinhTonKho" + this.getDateTime() + ".xls");
			WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
			WritableSheet sheet = w.createSheet("KiemKho", 0);
			dbutils db = new dbutils();

			sheet.setColumnView(0, 10);
			sheet.setColumnView(1, 30);
			sheet.setColumnView(2, 100);
			sheet.setColumnView(3, 30);

			WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 14);
			cellTitle.setColour(Colour.BLACK);
			cellTitle.setBoldStyle(WritableFont.BOLD);
			String query = " SELECT  NPP.TEN AS TENNPP,K.TEN AS TENKHO,DVKD.DONVIKINHDOANH ,KBH.TEN AS KENH   " + "  ,SP.PK_SEQ AS SPID, SP.MA, SP.TEN, KHO.AVAILABLE AS TONHIENTAI, KHO.GIAMUA, DVDL.DONVI    "
					+ "  FROM NHAPP_KHO KHO INNER JOIN  SANPHAM SP ON  KHO.SANPHAM_FK=SP.PK_SEQ  INNER JOIN DONVIDOLUONG DVDL ON   " + "  DVDL.PK_SEQ=SP.DVDL_FK  INNER JOIN DONVIKINHDOANH DVKD ON SP.DVKD_FK=DVKD.PK_SEQ    "
					+ "  INNER JOIN KENHBANHANG KBH ON KBH.PK_SEQ=KHO.KBH_FK  INNER JOIN  NHAPHANPHOI NPP  " + "  ON NPP.PK_SEQ=KHO.NPP_FK   INNER JOIN KHO AS K ON KHO.KHO_FK=K.PK_SEQ   " + "  WHERE sp.trangthai=1 and  SP.DVKD_FK = "
					+ dctkBean.getDvkdId() + " AND    KHO.NPP_FK =" + dctkBean.getNppId() + "   AND KHO.KBH_FK =" + dctkBean.getKbhId() + "   " + "  AND KHO.KHO_FK = " + dctkBean.getKhoId() + " ORDER BY SP.MA ";

			System.out.println("1.Khoi tao upload : " + query);
			sheet.addCell(new Label(0, 0, "BẢNG KÊ TỒN KHO", new WritableCellFormat(cellTitle)));

			cellTitle = new WritableFont(WritableFont.TIMES, 12);
			cellTitle.setColour(Colour.BLACK);
			sheet.addCell(new Label(0, 1, "Ngày: " + getDateTime(), new WritableCellFormat(cellTitle)));

			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
			cellFont.setColour(Colour.BLACK);

			WritableCellFormat cellFormat = new WritableCellFormat(cellFont);

			cellFormat.setBackground(jxl.format.Colour.GRAY_25);
			cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

			sheet.addCell(new Label(0, 7, " STT", cellFormat));
			sheet.addCell(new Label(1, 7, " MÃ", cellFormat));
			sheet.addCell(new Label(2, 7, " TÊN", cellFormat));
			sheet.addCell(new Label(3, 7, " SỐ LƯỢNG", cellFormat));

			WritableCellFormat cellFormat2 = new WritableCellFormat(new jxl.write.NumberFormat("#,###,###"));

			cellFormat2.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

			WritableCellFormat cellFormat3 = new WritableCellFormat(new jxl.write.NumberFormat("#,###,###"));
			cellFormat3.setBackground(jxl.format.Colour.YELLOW);
			cellFormat3.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat3.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat3.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat3.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

			WritableCellFormat cformat = null;
			ResultSet rs = db.get(query);
			int stt = 1;
			Number number;
			Label label;
			if (rs != null)
			{
				int i = 8;
				while (rs.next())
				{
					if (i == 8)
					{
						sheet.addCell(new Label(0, 2, " Nhà phân phối : " + rs.getString("TENNPP"), cellFormat));
						sheet.addCell(new Label(0, 3, " Kho : " + rs.getString("TENKHO"), cellFormat));
						sheet.addCell(new Label(0, 4, " Kênh bán hàng : " + rs.getString("kenh"), cellFormat));
						sheet.addCell(new Label(0, 5, " Đơn vị kinh doanh : " + rs.getString("donvikinhdoanh"), cellFormat));
					}
					cformat = cellFormat2;
					number = new Number(0, i, stt, cformat);
					sheet.addCell(number);
					label = new Label(1, i, rs.getString("MA"), cformat);
					sheet.addCell(label);
					label = new Label(2, i, rs.getString("TEN"), cformat);
					sheet.addCell(label);
					number = new Number(3, i, rs.getDouble("TonHienTai"), cformat);
					sheet.addCell(number);
					i++;
					stt++;

				}
				rs.close();
			}
			w.write();
			w.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			if (out1 != null)
				out1.close();
		}
	}

}
