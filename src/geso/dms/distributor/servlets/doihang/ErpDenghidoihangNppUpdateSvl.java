package geso.dms.distributor.servlets.doihang;

import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.doihang.IErpDenghidoihangNpp;
import geso.dms.distributor.beans.doihang.IErpDenghidoihangNppList;
import geso.dms.distributor.beans.doihang.imp.ErpDenghidoihangNpp;
import geso.dms.distributor.beans.doihang.imp.ErpDenghidoihangNppList;

import java.io.File;
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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Sheet;
import jxl.Workbook;

import com.oreilly.servlet.MultipartRequest;

public class ErpDenghidoihangNppUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	PrintWriter out;
	public ErpDenghidoihangNppUpdateSvl() 
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
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}
		else
		{
			session.setMaxInactiveInterval(30000);

			Utility util = new Utility();
			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);

			if (userId.length()==0)
				userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"))); 

			String id = util.getId(querystring);  	
			IErpDenghidoihangNpp lsxBean = new ErpDenghidoihangNpp(id);
			lsxBean.setUserId(userId); 
			lsxBean.setId(id);

			String nextJSP = "";

			lsxBean.init();

			if(!querystring.contains("display"))
			{
				nextJSP = "/AHF/pages/Distributor/ErpDeNghiDoiHangNppUpdate.jsp";
			}
			else
			{
				nextJSP = "/AHF/pages/Distributor/ErpDeNghiDoiHangNppDisplay.jsp";
			}

			session.setAttribute("lsxBean", lsxBean);
			response.sendRedirect(nextJSP);
		}		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen"); 
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");			
		session.setMaxInactiveInterval(30000);

		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}
		else
		{
			String contentType = request.getContentType();
			Utility util = new Utility();
			NumberFormat formatter = new DecimalFormat("#,###,###.###");
			IErpDenghidoihangNpp lsxBean;
			String id = null;
			String ngayyeucau = null;
			String ngaydenghi =null;
			String ghichu =null;
			String khonhapId = null;
			String dvkdId = null;
			String kbhId = null;
			String nppId = null;
			String chietkhau = null;
			String vat = null;
			String loaidonhang = null;
			String[] schemeIds = null;
			String[] spId = null;
			String[] soluong = null;			
			String action = null;			
			if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
			{
				MultipartRequest multi = new MultipartRequest(request, "C:\\java-tomcat\\data\\", 20000000, "UTF-8");
				Enumeration files = multi.getFileNames();
				id = util.antiSQLInspection(multi.getParameter("id"));
				if(id == null)
				{  	
					lsxBean = new ErpDenghidoihangNpp("");
				}
				else
				{
					lsxBean = new ErpDenghidoihangNpp(id);
				}
				lsxBean.setUserId(userId);
				lsxBean.setId(id);

				ngayyeucau = util.antiSQLInspection(multi.getParameter("ngaychuyen"));
				if(ngayyeucau == null || ngayyeucau.trim().length() <= 0)
					ngayyeucau = getDateTime();
				lsxBean.setNgayyeucau(ngayyeucau);

				ngaydenghi = util.antiSQLInspection(multi.getParameter("ngaydenghi"));
				if(ngaydenghi == null || ngaydenghi.trim().length() <= 0)
					ngaydenghi = "";
				lsxBean.setNgaydenghi(ngaydenghi);

				ghichu = util.antiSQLInspection(multi.getParameter("ghichu"));
				if(ghichu == null)
					ghichu = "";
				lsxBean.setGhichu(ghichu);

				khonhapId = util.antiSQLInspection(multi.getParameter("khonhapId"));
				if (khonhapId == null)
					khonhapId = "";				
				lsxBean.setKhoNhapId(khonhapId);

				dvkdId = util.antiSQLInspection(multi.getParameter("dvkdId"));
				if (dvkdId == null)
					dvkdId = "";				
				lsxBean.setDvkdId(dvkdId);

				kbhId = util.antiSQLInspection(multi.getParameter("kbhId"));
				if (kbhId == null)
					kbhId = "";				
				lsxBean.setKbhId(kbhId);

				nppId = util.antiSQLInspection(multi.getParameter("nppId"));
				if (nppId == null)
					nppId = "";				
				lsxBean.setNppId(nppId);

				chietkhau = util.antiSQLInspection(multi.getParameter("ptChietkhau"));
				if (chietkhau == null)
					chietkhau = "";				
				lsxBean.setChietkhau(chietkhau);

				vat = util.antiSQLInspection(multi.getParameter("ptVat"));
				if (vat == null)
					vat = "";				
				lsxBean.setVat(vat);

				loaidonhang = util.antiSQLInspection(multi.getParameter("loaidonhang"));
				if (loaidonhang == null)
					loaidonhang = "";				
				lsxBean.setLoaidonhang(loaidonhang); 

				schemeIds = multi.getParameterValues("schemeIds");
				if (schemeIds != null)
				{
					String _scheme = "";
					for(int i = 0; i < schemeIds.length; i++)
					{
						_scheme += schemeIds[i] + ",";
					}

					if(_scheme.trim().length() > 0)
					{
						_scheme = _scheme.substring(0, _scheme.length() - 1);
						lsxBean.setSchemeId(_scheme);
					}
				}

				spId = multi.getParameterValues("spID");
				soluong = multi.getParameterValues("spSOLUONG");

				
				action = multi.getParameter("action");
				
				String filenameu = "";
				while (files.hasMoreElements())
				{
					String name = (String) files.nextElement();
					filenameu = multi.getFilesystemName(name);
				}
				String filename = "C:\\java-tomcat\\data\\" + filenameu;
				if (filename.length() > 0)
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
							String _spSoluong =getValue(sheet, 3,row);
							index++;
							if(_spMa.length()>0&&_spSoluong.length()>0)
							{
								if(index!=1)
								{
									sql_TABLE+=" union all ";							
								}
								if(_spMa.length()>0&&_spSoluong.length()>0)
									sql_TABLE +="\n  select '"+_spMa+"' as spMa,N'"+_spTen+"' as spTen, N'"+_spSoluong.replaceAll(",", "")+"' as Soluong ";
							}
						}
						String query = "";
						String msg = "";
						query = "";

						query = "SELECT * FROM (" + sql_TABLE + ") as data " + " WHERE spMa NOT IN " + " ( " + "	SELECT MA FROM SANPHAM " + " )";
						ResultSet rs = db.get(query);
						System.out.println("[ErpDenghidoihangNppUpdateSvl]"+query);
						while (rs.next())
						{
							msg += "Sản phẩm " + rs.getString("spMa") + " không có trong hệ thống ! \n";
						}
						if (rs != null)
							rs.close();

						lsxBean.setMsg(msg);

						if (msg.length() <= 0)
						{
							query="SELECT sp.pk_seq as spId,data.SoLuong  FROM (" + sql_TABLE + ") as data inner join sanpham sp on sp.Ma=data.spMa  ";
							rs=db.get(query);
							System.out.println("[ErpDenghidoihangNppUpdateSvl]"+query);
							Hashtable<String, String> sp_soluong = new Hashtable<String, String>();
							while(rs.next())
							{
								sp_soluong.put(rs.getString("spId")  , rs.getString("SoLuong") );
							}
							lsxBean.setSanpham_soluong(sp_soluong);

							if (rs != null)
								rs.close();
						}
					} catch (Exception e)
					{
						lsxBean.setMsg("Lỗi phát sinh "+e.getMessage());
						e.printStackTrace();
					} finally
					{
						db.shutDown();
					}
				}
			}
			else 
			{
				id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
				if(id == null)
				{  	
					lsxBean = new ErpDenghidoihangNpp("");
				}
				else
				{
					lsxBean = new ErpDenghidoihangNpp(id);
				}
				lsxBean.setUserId(userId);
				lsxBean.setId(id);

				ngayyeucau = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaychuyen")));
				if(ngayyeucau == null || ngayyeucau.trim().length() <= 0)
					ngayyeucau = getDateTime();
				lsxBean.setNgayyeucau(ngayyeucau);

				ngaydenghi = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaydenghi")));
				if(ngaydenghi == null || ngaydenghi.trim().length() <= 0)
					ngaydenghi = "";
				lsxBean.setNgaydenghi(ngaydenghi);

				ghichu = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ghichu")));
				if(ghichu == null)
					ghichu = "";
				lsxBean.setGhichu(ghichu);

				khonhapId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khonhapId")));
				if (khonhapId == null)
					khonhapId = "";				
				lsxBean.setKhoNhapId(khonhapId);
				
				System.out.println("khonhapId"+khonhapId);

				dvkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")));
				if (dvkdId == null)
					dvkdId = "";				
				lsxBean.setDvkdId(dvkdId);

				kbhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kbhId")));
				if (kbhId == null)
					kbhId = "";				
				lsxBean.setKbhId(kbhId);

				nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
				if (nppId == null)
					nppId = "";				
				lsxBean.setNppId(nppId);

				chietkhau = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ptChietkhau")));
				if (chietkhau == null)
					chietkhau = "";				
				lsxBean.setChietkhau(chietkhau);

				vat = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ptVat")));
				if (vat == null)
					vat = "";				
				lsxBean.setVat(vat);

				loaidonhang = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaidonhang")));
				if (loaidonhang == null)
					loaidonhang = "";				
				lsxBean.setLoaidonhang(loaidonhang); 

				schemeIds = request.getParameterValues("schemeIds");
				if (schemeIds != null)
				{
					String _scheme = "";
					for(int i = 0; i < schemeIds.length; i++)
					{
						_scheme += schemeIds[i] + ",";
					}

					if(_scheme.trim().length() > 0)
					{
						_scheme = _scheme.substring(0, _scheme.length() - 1);
						lsxBean.setSchemeId(_scheme);
					}
				}

				spId = request.getParameterValues("spID");
				soluong = request.getParameterValues("spSOLUONG");			
				if(spId != null)
				{
					Hashtable<String, String> sp_soluong = new Hashtable<String, String>();				
					for(int i = 0; i < spId.length; i++ )
					{
						if(soluong[i].trim().length() > 0)
						{
							sp_soluong.put(spId[i], soluong[i].replaceAll(",", "") );
						}
					}
					lsxBean.setSanpham_soluong(sp_soluong);
				}

				action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
			}

			if(action.equals("save"))
			{	
				if(id == null)
				{
					boolean kq = lsxBean.createNK(request);

					if(!kq)
					{
						lsxBean.createRs();
						session.setAttribute("lsxBean", lsxBean);
						String nextJSP = "/AHF/pages/Distributor/ErpDeNghiDoiHangNppNew.jsp";
						response.sendRedirect(nextJSP);
					}
					else
					{
						IErpDenghidoihangNppList obj = new ErpDenghidoihangNppList();
						obj.setLoaidonhang(loaidonhang);

						obj.setUserId(userId);
						obj.init("");  
						session.setAttribute("obj", obj);  	
						session.setAttribute("userId", userId);

						String nextJSP = "/AHF/pages/Distributor/ErpDeNghiDoiHangNpp.jsp";
						response.sendRedirect(nextJSP);
					}
				}
				else
				{
					boolean kq = lsxBean.updateNK(request);

					if(!kq)
					{
						lsxBean.createRs();
						session.setAttribute("lsxBean", lsxBean);
						String nextJSP = "/AHF/pages/Distributor/ErpDeNghiDoiHangNppUpdate.jsp";
						response.sendRedirect(nextJSP);
					}
					else
					{
						IErpDenghidoihangNppList obj = new ErpDenghidoihangNppList();
						obj.setLoaidonhang(loaidonhang);

						obj.setUserId(userId);
						obj.init("");
						session.setAttribute("obj", obj);							
						String nextJSP = "/AHF/pages/Distributor/ErpDeNghiDoiHangNpp.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
			else
			{
				lsxBean.createRs();

				session.setAttribute("dvkdId", lsxBean.getDvkdId());
				session.setAttribute("kbhId", lsxBean.getKbhId());
				session.setAttribute("nppId", lsxBean.getNppId());

				session.setAttribute("lsxBean", lsxBean);

				String nextJSP = "";

				nextJSP = "/AHF/pages/Distributor/ErpDeNghiDoiHangNppNew.jsp";
				if(id != null)
				{
					nextJSP = "/AHF/pages/Distributor/ErpDeNghiDoiHangNppUpdate.jsp";
				}

				response.sendRedirect(nextJSP);

			}
		}
	}


	private String getDateTime() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);	
	}


	String getValue(Sheet sheet,int col,int row)
	{
		return sheet.getCell(col,row).getContents().trim().replaceAll(",", "");
	}


}
