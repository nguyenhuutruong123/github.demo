package geso.dms.distributor.servlets.dontrahang;

import geso.dms.distributor.beans.dontrahang.IDontrahang;
import geso.dms.distributor.beans.dontrahang.IDontrahangList;
import geso.dms.distributor.beans.dontrahang.imp.Dontrahang;
import geso.dms.distributor.beans.dontrahang.imp.DontrahangList;
import geso.dms.distributor.db.sql.dbutils;
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
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Sheet;
import jxl.Workbook;
import T.IK;

import com.oreilly.servlet.MultipartRequest;

public class DontrahangUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	
	public DontrahangUpdateSvl()
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
			session.setMaxInactiveInterval(30000);
			
			Utility util = new Utility();
			
			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);
			
			String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
			if(view == null) view = "";
			
			if (userId.length() == 0)
				userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
			
			String id = util.getId(querystring);
			IDontrahang lsxBean = new Dontrahang(id);
			lsxBean.setUserId(userId);
			lsxBean.setView(view);
			
			String nextJSP = "";
			
			lsxBean.init();
			
			
			
			if (!querystring.contains("display"))
				nextJSP = "/AHF/pages/Distributor/DonTraHangUpdate.jsp";
			else
				nextJSP = "/AHF/pages/Distributor/DonTraHangDisplay.jsp";
			
			session.setAttribute("lsxBean", lsxBean);
			session.setAttribute("ngaychungtu", lsxBean.getNgayyeucau());
			session.setAttribute("kenhId", lsxBean.getKbhId());
			session.setAttribute("kbhId", lsxBean.getKbhId());
			session.setAttribute("khoId", lsxBean.getKhoXuatId());
			session.setAttribute("nppId",lsxBean.getNppId() );
			
			response.sendRedirect(nextJSP);
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
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			session.setMaxInactiveInterval(30000);


			IDontrahang lsxBean = null;

			Utility util = new Utility();
			String id = null;
			String action = "";

			String ngayyeucau = null;


			String ghichu = null;			
			String sochungtu = null;			
			String khoxuatId = null;						
			String nppId = null;			
			String khId = null;			
			String kbhId = null;


			String[] spMa =null;			
			String[] spTen = null;			
			String[] dvt = null;			
			String[] soluong = null;			
			String[] tonkho = null;
			String[] dongia = null;			
			String[] spVat = null;
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

			String contentType = request.getContentType();		   

			if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
			{							
				MultipartRequest multi = new MultipartRequest(request, "C:\\java-tomcat\\data\\", 20000000, "UTF-8");
				action = multi.getParameter("action");

				id = util.antiSQLInspection(multi.getParameter("id"));

				if (id == null)
				{
					lsxBean = new Dontrahang("");
				} else
				{

					lsxBean = new Dontrahang(id);
				}

				lsxBean.setUserId(userId);


				filenames = multi.getParameterValues("filedk");
				s = "";
				if(filenames != null)
				{
					for(int i=0; i< filenames.length; i++)
						s += filenames[i] + ",";
					if(s.length() > 0)
						s = s.substring(0, s.length() - 1);

				}
				System.out.println("File cu: "+s);
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
					if(!name.equals("fileup"))
						filenameu = multi.getFilesystemName(name);

				}


				if(filedk != null && filedk.length() > 0){
					if(s.length() > 0)
					{
						lsxBean.setFile(s + "," + filedk);
						s= s + "," + filedk;
					}
					else
					{
						lsxBean.setFile(filedk);
						System.out.println("___S:" + s + "," + filedk);
						s= s + "," + filedk;
					}
				}

				ngayyeucau = util.antiSQLInspection(multi.getParameter("ngaychuyen"));
				if (ngayyeucau == null || ngayyeucau.trim().length() <= 0)
					ngayyeucau = getDateTime();
				lsxBean.setNgayyeucau(ngayyeucau);

				ghichu = util.antiSQLInspection(multi.getParameter("ghichu"));
				if (ghichu == null)
					ghichu = "";
				lsxBean.setGhichu(ghichu);


				sochungtu = util.antiSQLInspection( multi.getParameter("sochungtu")==null? "":multi.getParameter("sochungtu") );
				lsxBean.setSoChungTu(sochungtu);


				khoxuatId = util.antiSQLInspection(multi.getParameter("khoxuatId"));
				if (khoxuatId == null)
					khoxuatId = "";
				lsxBean.setKhoXuatId(khoxuatId);

				nppId = util.antiSQLInspection(multi.getParameter("nppId"));
				if (nppId == null)
					nppId = "";
				lsxBean.setNppId(nppId);


				khId = util.antiSQLInspection(multi.getParameter("khId"));
				if (khId == null)
					khId = "";
				lsxBean.setKhId(khId);

				kbhId = util.antiSQLInspection(multi.getParameter("kbhId"));
				if (kbhId == null)
					kbhId = "";
				lsxBean.setKbhId(kbhId);

				session.setAttribute("ngaychungtu", lsxBean.getNgayyeucau());
				session.setAttribute("kenhId", lsxBean.getKbhId());
				session.setAttribute("kbhId", lsxBean.getKbhId());
				session.setAttribute("khoId", lsxBean.getKhoXuatId());
				session.setAttribute("nppId", geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));


				spMa = multi.getParameterValues("spMa");
				lsxBean.setSpMa(spMa);

				spTen = multi.getParameterValues("spTen");
				lsxBean.setSpTen(spTen);

				dvt = multi.getParameterValues("donvi");
				lsxBean.setSpDonvi(dvt);

				soluong = multi.getParameterValues("soluong");
				lsxBean.setSpSoluong(soluong);

				tonkho = multi.getParameterValues("tonkho");
				lsxBean.setSpTonkho(tonkho);

				dongia = multi.getParameterValues("dongia");
				lsxBean.setSpGianhap(dongia);

				spVat = multi.getParameterValues("spVat");
				lsxBean.setSpVat(spVat);;

				if(spMa != null && action.equals("save") )  //LUU LAI THONG TIN NGUOI DUNG NHAP
				{
					Hashtable<String, String> sanpham_soluong = new Hashtable<String, String>();
					for(int i = 0; i < spMa.length; i++ )
					{
						String temID = spMa[i];
						String[] spSOLO = multi.getParameterValues(temID + "_spSOLO");

						String[] spNgayHetHan = multi.getParameterValues(temID + "_spNGAYHETHAN");

						String[] soLUONGXUAT = multi.getParameterValues(temID + "_spSOLUONG");

						if(soLUONGXUAT != null)
						{
							for(int j = 0; j < soLUONGXUAT.length; j++ )
							{
								if(soLUONGXUAT[j] != null && soLUONGXUAT[j].trim().length() > 0)
								{
									sanpham_soluong.put(spMa[i] + "__" + spSOLO[j]+ "__" + spNgayHetHan[j], soLUONGXUAT[j].replaceAll(",", "") );
								}
							}
						}
					}

					lsxBean.setSanpham_Soluong(sanpham_soluong);
				}



				String filename = "C:\\java-tomcat\\data\\" + filenameu;
				System.out.println("File Name: "+filenameu);
				if(filenameu != null)
					if (filenameu.length() > 0 && filenameu.length() > 0)
					{
						dbutils db = new dbutils();
						try
						{
							File file = new File(filename);
							Workbook workbook;
							workbook = Workbook.getWorkbook(file);
							Sheet sheet = workbook.getSheet(0);
							int indexRow = 1;
							int sodong = sheet.getRows();
							String sql_TABLE = "";
							int index=0;
							for (int row=indexRow; row < sodong ; row++)
							{

								String _spMa =getValue(sheet, 1,row);
								String _spTen =getValue(sheet, 2,row);
								System.out.println("TenSP: "+_spTen);
								String _spSoluong =getValue(sheet, 3,row);
								index++;
								if(_spMa.length()>0&&_spSoluong.length()>0)
								{
									if(index!=1)
									{
										sql_TABLE+=" union all ";							
									}

									if(_spMa.length()>0&&_spSoluong.length()>0)
										sql_TABLE +="\n  select '"+_spMa+"' as spMa,N'"+_spTen+"' as spTen, N'"+_spSoluong+"' as Soluong ";
								}
							}

							db.getConnection().setAutoCommit(false);
							String query = "";
							String msg = "";
							query = "";

							query = "SELECT * FROM (" + sql_TABLE + ") as data " + " WHERE spMa NOT IN " + " ( " + "	SELECT MA FROM SANPHAM " + " )";
							System.out.println("Câu SP import "+query);
							ResultSet rs = db.get(query);
							while (rs.next())
							{
								msg += "Sản phẩm " + rs.getString("spMa") + " không có trong hệ thống ! \n";
								System.out.println("[ErpDondathangNppUpdateSvl]"+query);
							}
							if (rs != null)
								rs.close();

							lsxBean.setMsg(msg);

							


							if (msg.length() <= 0)
							{
								query = 
									"select ISNULL((select kho.available from nhapp_kho kho where kho.sanpham_fk=a.pk_seq and kho.KHO_FK= "+lsxBean.getKhoXuatId()+" and NPP_FK='"+lsxBean.getNppId()+"' and kho.KBH_FK="+lsxBean.getKbhId()+" ) ,0)  as soluongton,a.ma, a.ten, b.donvi, ISNULL(trongluong, 0) as trongluong, ISNULL(thetich, 0) as thetich, " +
									"	cast (  isnull( ( select soluong2 / soluong1 from QUYCACH where SANPHAM_FK = a.PK_SEQ and DVDL1_FK = a.DVDL_FK	and DVDL2_FK = '100018' ), 0 ) as numeric(18, 2) ) as qc,	" +
									" 	  isnull( ( select GIAMUANPP * ( 1 - isnull( ( select chietkhau from BANGGIAMUANPP_NPP where banggiamuaNPP_FK = bg_sp.bgmuaNPP_FK and NPP_FK = '" + lsxBean.getNppId() + "' ), 0) / 100 ) " +
									" 				from BGMUANPP_SANPHAM bg_sp " +
									"			    where SANPHAM_FK = a.pk_seq  " +
									"					and BGMUANPP_FK in ( select top(1) PK_SEQ from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK where bg.TRANGTHAI = '1' and bg_npp.NPP_FK = '" + lsxBean.getNppId() + "' and bg.DVKD_FK = a.dvkd_fk order by bg.TUNGAY desc ) ), 0) as giamua, a.ptThue, data.soluong  " +
									"from SANPHAM a left join DONVIDOLUONG b on a.dvdl_fk = b.pk_seq " +
									"     inner join "+
									" ( SELECT spMa,spTen,SoLuong FROM (" + sql_TABLE + ")as data ) as data on data.spMa = a.ma  "+
									"where a.pk_seq > 0  ";				


								System.out.println("[DontrahangUpdateSvl]"+query);
								rs = db.get(query);
								String spMA = "";
								String spTEN = "";
								String spDONVI = "";
								String spGIA = "";
								String spTHUE = "";
								String spTON = "";
								String spSOLUONG = "";

								while (rs.next())
								{
									spMA += rs.getString("ma") + "__";
									spTEN += rs.getString("ten") + "__";
									spDONVI += rs.getString("donvi") + "__";
									spSOLUONG += rs.getString("soluong") + "__";
									spTHUE += rs.getString("ptThue") + "__";
									double DonGia =rs.getDouble("giamua");
									double Ton =rs.getDouble("soluongton");
									spGIA += formatter.format( DonGia )+ "__";
									spTON += formatter.format( Ton )+ "__";
								}

								if (spMA.trim().length() > 0)
								{
									spMA = spMA.substring(0, spMA.length() - 2);
									spMa = spMA.split("__");

									spTEN = spTEN.substring(0, spTEN.length() - 2);
									spTen = spTEN.split("__");

									spDONVI = spDONVI.substring(0, spDONVI.length() - 2);
									dvt = spDONVI.split("__");

									spGIA = spGIA.substring(0, spGIA.length() - 2);
									dongia = spGIA.split("__");

									spTHUE = spTHUE.substring(0, spTHUE.length() - 2);
									spVat = spTHUE.split("__");							

									spSOLUONG = spSOLUONG.substring(0, spSOLUONG.length() - 2);
									soluong = spSOLUONG.split("__");

									spTON = spTON.substring(0, spTON.length() - 2);
									tonkho = spTON.split("__");


								}
								lsxBean.setSpMa(spMa);
								lsxBean.setSpTen(spTen);
								lsxBean.setSpDonvi(dvt);
								lsxBean.setSpSoluong(soluong);
								lsxBean.setSpGianhap(dongia);
								lsxBean.setSpVat(spVat);
								lsxBean.setSpTonkho(tonkho);

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
					}



			}
			else
			{
				action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));

				id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));

				if (id == null)
				{
					lsxBean = new Dontrahang("");
				} else
				{

					lsxBean = new Dontrahang(id);
				}

				lsxBean.setUserId(userId);

				ngayyeucau = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaychuyen")));
				if (ngayyeucau == null || ngayyeucau.trim().length() <= 0)
					ngayyeucau = getDateTime();
				lsxBean.setNgayyeucau(ngayyeucau);

				ghichu = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ghichu")));
				if (ghichu == null)
					ghichu = "";
				lsxBean.setGhichu(ghichu);


				sochungtu = util.antiSQLInspection( geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sochungtu")==null? "":request.getParameter("sochungtu")) );
				lsxBean.setSoChungTu(sochungtu);


				khoxuatId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khoxuatId")));
				if (khoxuatId == null)
					khoxuatId = "";
				lsxBean.setKhoXuatId(khoxuatId);


				nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
				if (nppId == null)
					nppId = "";
				lsxBean.setNppId(nppId);

				khId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khId")));
				if (khId == null)
					khId = "";
				lsxBean.setKhId(khId);

				kbhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kbhId")));
				if (kbhId == null)
					kbhId = "";
				lsxBean.setKbhId(kbhId);

				session.setAttribute("ngaychungtu", lsxBean.getNgayyeucau());
				session.setAttribute("kenhId", lsxBean.getKbhId());
				session.setAttribute("kbhId", lsxBean.getKbhId());
				session.setAttribute("khoId", lsxBean.getKhoXuatId());
				session.setAttribute("nppId", geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));


				spMa = request.getParameterValues("spMa");
				lsxBean.setSpMa(spMa);

				spTen = request.getParameterValues("spTen");
				lsxBean.setSpTen(spTen);

				dvt = request.getParameterValues("donvi");
				lsxBean.setSpDonvi(dvt);

				soluong = request.getParameterValues("soluong");
				lsxBean.setSpSoluong(soluong);

				tonkho = request.getParameterValues("tonkho");
				lsxBean.setSpTonkho(tonkho);

				dongia = request.getParameterValues("dongia");
				lsxBean.setSpGianhap(dongia);

				spVat = request.getParameterValues("spVat");
				lsxBean.setSpVat(spVat);;

				if(spMa != null && action.equals("save") )  //LUU LAI THONG TIN NGUOI DUNG NHAP
				{
					Hashtable<String, String> sanpham_soluong = new Hashtable<String, String>();
					for(int i = 0; i < spMa.length; i++ )
					{
						String temID = spMa[i];
						String[] spSOLO = request.getParameterValues(temID + "_spSOLO");

						String[] spNgayHetHan = request.getParameterValues(temID + "_spNGAYHETHAN");

						String[] soLUONGXUAT = request.getParameterValues(temID + "_spSOLUONG");

						if(soLUONGXUAT != null)
						{
							for(int j = 0; j < soLUONGXUAT.length; j++ )
							{
								if(soLUONGXUAT[j] != null && soLUONGXUAT[j].trim().length() > 0)
								{
									sanpham_soluong.put(spMa[i] + "__" + spSOLO[j]+ "__" + spNgayHetHan[j], soLUONGXUAT[j].replaceAll(",", "") );
								}
							}
						}
					}

					lsxBean.setSanpham_Soluong(sanpham_soluong);
				}


				System.out.println("Npp1 "+nppId);

				lsxBean.setFile(s);
			}

			if (action.equals("save"))
			{
				if (id == null)
				{
					if (!lsxBean.createNK())
					{
						lsxBean.createRs();
						session.setAttribute("lsxBean", lsxBean);
						String nextJSP = "/AHF/pages/Distributor/DonTraHangNew.jsp";
						response.sendRedirect(nextJSP);
					} else
					{
						IDontrahangList obj = new DontrahangList();
						obj.setUserId(userId);
						obj.init("");
						session.setAttribute("obj", obj);
						session.setAttribute("userId", userId);

						response.sendRedirect("/AHF/pages/Distributor/DonTraHang.jsp");
					}
				} else
				{
					if (!lsxBean.updateNK())
					{
						lsxBean.createRs();
						session.setAttribute("lsxBean", lsxBean);
						String nextJSP = "/AHF/pages/Distributor/DonTraHangUpdate.jsp";
						response.sendRedirect(nextJSP);
					} else
					{
						IDontrahangList obj = new DontrahangList();
						obj.setUserId(userId);
						obj.init("");
						session.setAttribute("obj", obj);
						String nextJSP = "/AHF/pages/Distributor/DonTraHang.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			} else  if(action.equals("remove")){
				s = "";
				String removename = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("removename")));
				if (removename == null)
					removename = "";
				for(int i=0; i< filenames.length; i++)
					if(!filenames[i].equals(removename))
						s += filenames[i] + ",";
				if(s.length() > 0)
					s = s.substring(0, s.length() - 1);



				if (!this.deletefile("C:\\java-tomcat\\data\\" + removename))
				{
					out.write("Lỗi xóa file");
				} else
				{
					out.write("");
				}

				lsxBean.setFile(s);

				String nextJSP;
				nextJSP = "/AHF/pages/Distributor/DonTraHangNew.jsp";
				if (id != null)
					nextJSP = "/AHF/pages/Distributor/DonTraHangUpdate.jsp";


				session.setAttribute("lsxBean", lsxBean);
				response.sendRedirect(nextJSP);
			}else
				if (action.equals("download"))
				{
					System.out.println("___Vao DOWNLOAD FILE....");
					String fileName = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tenfile"));
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
							e.printStackTrace();
							System.out.println("___Loi DOWNLOAD file: " + e.getMessage());
						}
					} 
					else
					{
						lsxBean.setUserId(userId);
						lsxBean.setId(id);


						String nextJSP;

						nextJSP = "/AHF/pages/Distributor/DonTraHangNew.jsp";
						if (id != null)
							nextJSP = "/AHF/pages/Distributor/DonTraHangUpdate.jsp";

						session.setAttribute("lsxBean", lsxBean);
						response.sendRedirect(nextJSP);
					}
				} 
				else
				{
					lsxBean.createRs();
					session.setAttribute("ngaychungtu", lsxBean.getNgayyeucau());
					System.out.println("TEN NPP: "+lsxBean.getNppTen());
					session.setAttribute("kenhId", lsxBean.getKbhId());
					session.setAttribute("kbhId", lsxBean.getKbhId());
					session.setAttribute("khoId", lsxBean.getKhoXuatId());
					session.setAttribute("nppId", geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));

					session.setAttribute("lsxBean", lsxBean);

					String nextJSP = "";

					nextJSP = "/AHF/pages/Distributor/DonTraHangNew.jsp";
					if (id != null)
						nextJSP = "/AHF/pages/Distributor/DonTraHangUpdate.jsp";

					response.sendRedirect(nextJSP);
				}
		}
	}
	
	String getValue(Sheet sheet,int col,int row)
	{
		return sheet.getCell(col,row).getContents().trim().replaceAll(",", "");
	}

	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
}
