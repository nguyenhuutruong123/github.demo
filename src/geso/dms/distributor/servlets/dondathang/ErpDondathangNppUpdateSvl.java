package geso.dms.distributor.servlets.dondathang;

import geso.dms.center.beans.dondathang.imp.XLkhuyenmaiTT;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.ctkhuyenmai.ICtkhuyenmai;
import geso.dms.distributor.beans.dondathang.IErpDondathangNpp;
import geso.dms.distributor.beans.dondathang.IErpDondathangNppList;
import geso.dms.distributor.beans.dondathang.imp.ErpDondathangNpp;
import geso.dms.distributor.beans.dondathang.imp.ErpDondathangNppList;
import geso.dms.distributor.beans.trakhuyenmai.ITrakhuyenmai;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
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

import jxl.Sheet;
import jxl.Workbook;

import com.oreilly.servlet.MultipartRequest;


public class ErpDondathangNppUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	PrintWriter out;
	public ErpDondathangNppUpdateSvl() 
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		
		// kiểm tra quyền xem menu
	    String param="";
		if( Utility.CheckRuleUser( session,  request, response, "ErpDondathangNppSvl", param, Utility.XEM ) || Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
			return;
		}
		
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
			IErpDondathangNpp lsxBean = new ErpDondathangNpp(id);
			lsxBean.setUserId(userId); 
			
			// Kiểm tra id có hợp lệ không
		    if(id != null && id.trim().length() > 0 
		    		&& !Utility.KiemTra_PK_SEQ_HopLe(id, "ERP_Dondathang", lsxBean.getDb())){
		    	lsxBean.DBclose();
	    		return;
	    	}

			String nextJSP = "";

			lsxBean.init();

			session.setAttribute("dvkdId", lsxBean.getDvkdId());
			session.setAttribute("kbhId", lsxBean.getKbhId());
			session.setAttribute("nppId", lsxBean.getNppId());
			session.setAttribute("ngaydonhang", lsxBean.getNgayyeucau());
			session.setAttribute("khoId", lsxBean.getKhoNhapId());

			if(!querystring.contains("display"))
			{
				//check quyền
				int[] quyen = Utility.Getquyen("ErpDondathangNppSvl", "",lsxBean.getUserId());
				if(quyen[Utility.SUA]!=1){
    				lsxBean.DBclose();
    				return;
    			}
				nextJSP = "/AHF/pages/Distributor/ErpDonDatHangNppUpdate.jsp";
			}
			else
			{
				//check quyền
				nextJSP = "/AHF/pages/Distributor/ErpDonDatHangNppDisplay.jsp";
			}

			session.setAttribute("lsxBean", lsxBean);
			response.sendRedirect(nextJSP);
		}		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen"); 
		
		// kiểm tra quyền xem menu
	    String param="";
		if( Utility.CheckRuleUser( session,  request, response, "ErpDondathangNppSvl", param, Utility.XEM ) || Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
			return;
		}

		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}
		else
		{
			
			session.setMaxInactiveInterval(30000);
			NumberFormat formatter = new DecimalFormat("#,###,###.###");
			this.out = response.getWriter();
			IErpDondathangNpp lsxBean;
			String id=null;
			String ngayyeucau =null;
			String ngaydenghi =null;
			String ghichu =null;
			String khonhapId =null;
			String dvkdId = null;
			String kbhId = null;
			String nppId = null;
			String chietkhau = null;
			String vat = null;
			String loaidonhang = null;
			String congno = null;
			String tiengiam = null;
			String[] schemeIds = null;
			String[] spMa = null;
			String[] spTen = null;
			String[] spDonvi = null;
			String[] spTonkho = null;
			String[] spSoluong = null;
			String[] dongia = null;
			String[] spChietkhau = null;
			String[] spScheme = null;
			String[] spvat =null;
			String[] spGiagoc = null;
			String[] spBgId = null;
			String[] spSoluongtt = null;
			String[] spGhichu = null;
			String[] spNgaygiaohang=			null;

			String action = null;
			Utility util = new Utility();
			String contentType = request.getContentType();
			if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
			{
				//MultipartRequest multi = new MultipartRequest(request, "C:\\java-tomcat\\data\\", 20000000, "UTF-8");
				MultipartRequest multi = new MultipartRequest(request, getServletContext().getRealPath("\\upload\\"), 20000000, "UTF-8"); 
				id = util.antiSQLInspection(multi.getParameter("id"));
				if(id == null || (id!=null && id.trim().length()<=0))
				{  	
					lsxBean = new ErpDondathangNpp("");
				}
				else
				{
					lsxBean = new ErpDondathangNpp(id);
				}

				lsxBean.setUserId(userId);

				ngayyeucau = util.antiSQLInspection(multi.getParameter("ngaychuyen"));
				if(ngayyeucau == null || ngayyeucau.trim().length() <= 0)
					ngayyeucau = getDateTime();
				lsxBean.setNgayyeucau(ngayyeucau);
				session.setAttribute("ngaydonhang", lsxBean.getNgayyeucau());

				ngaydenghi = util.antiSQLInspection(multi.getParameter("ngaydenghi"));
				if(ngaydenghi == null || ngaydenghi.trim().length() <= 0)
					ngaydenghi = "";
				lsxBean.setNgaydenghi(ngaydenghi);

				ghichu = util.antiSQLInspection(multi.getParameter("ghichu"));
				if(ghichu == null)
					ghichu = "";
				lsxBean.setGhichu(ghichu);
				System.out.println("ghi chú "+ghichu);

				khonhapId = util.antiSQLInspection(multi.getParameter("khonhapId"));
				if (khonhapId == null)
					khonhapId = "";				
				lsxBean.setKhoNhapId(khonhapId);
				session.setAttribute("khoId", lsxBean.getKhoNhapId());

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
				
				congno = util.antiSQLInspection(multi.getParameter("txtCongno"));
				if (congno == null)
					congno = "";				
				lsxBean.setCongno(congno);

				chietkhau = util.antiSQLInspection(multi.getParameter("ptChietkhau"));
				if (chietkhau == null)
					chietkhau = "";				
				lsxBean.setChietkhau(chietkhau.replaceAll(",", ""));

				vat = util.antiSQLInspection(multi.getParameter("ptVat"));
				if (vat == null)
					vat = "";				
				lsxBean.setVat(vat.replaceAll(",", ""));

				loaidonhang = util.antiSQLInspection(multi.getParameter("loaidonhang"));
				if (loaidonhang == null)
					loaidonhang = "";				
				lsxBean.setLoaidonhang(loaidonhang);
				
				tiengiam = util.antiSQLInspection(multi.getParameter("txtTiengiam"));
				if (tiengiam == null)
					tiengiam = "";				
				lsxBean.setTiengiam(tiengiam.replaceAll(",", ""));

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
				action = multi.getParameter("action");

				Enumeration files = multi.getFileNames();
				String filenameu = "";
				while (files.hasMoreElements())
				{
					String name = (String) files.nextElement();
					filenameu = multi.getFilesystemName(name);

				}
				//String filename = "C:\\java-tomcat\\data\\" + filenameu;
				String filename=    getServletContext().getRealPath("\\upload\\")+ "\\"+ filenameu;

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
						List<Object> data = new ArrayList<Object>();
						data.clear();
						for (int row=indexRow; row < sodong ; row++)
						{
							
							/*String _spMa =getValue(sheet, 0,row);
							String _spTen =getValue(sheet, 1,row);
							String _spSoluong =getValue(sheet, 2,row);*/
							String _spMa = util.antiSQLInspection(getValue(sheet, 0,row));
							String _spTen = util.antiSQLInspection(getValue(sheet, 1,row));
							String _spSoluong = util.antiSQLInspection(getValue(sheet, 2,row));
							index++;
							if(_spMa.length()>0&&_spSoluong.length()>0)
							{
								if(index!=1)
								{
									sql_TABLE+=" union all ";							
								}
								
								if(_spMa.length()>0&&_spSoluong.length()>0)
								{
									sql_TABLE +="\n  select ? as spMa,? as spTen, ? as Soluong, ? as sott ";
									data.add(_spMa); data.add(_spTen); data.add(_spSoluong);
									data.add(index);
								}
									
							}
						}
						System.out.println("ds sp "+sql_TABLE);
						db.getConnection().setAutoCommit(false);
						String query = "";
						String msg = "";
						query = "";

						query = "SELECT * FROM (" + sql_TABLE + ") as data " + " WHERE spMa NOT IN " + " ( " + "	SELECT MA FROM SANPHAM " + " )";
						ResultSet rs = db.getByPreparedStatement(query, data);
						while (rs.next())
						{
							msg += "Sản phẩm " + rs.getString("spMa") + " không có trong hệ thống ! \n";
							//System.out.println("[ErpDondathangNppUpdateSvl]"+query);
							db.viewQuery(query, data);
						}
						if (rs != null)
							rs.close();

						lsxBean.setMsg(msg);

						if (msg.length() <= 0)
						{
							query = 
									"		select a.ma as spMa, a.ten as spTen, b.donvi as spDonVi,a.ptThue as spTHUE,  "+
											"			isnull( ( select GIAMUANPP from BGMUANPP_SANPHAM sp where SANPHAM_FK = a.pk_seq and BGMUANPP_FK in "+
											"						(	select top(1) PK_SEQ from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK "+
											"									where bg.TRANGTHAI = '1' and bg_npp.NPP_FK = ? and bg.DVKD_FK = ? and bg.KENH_FK = ?  "+
											"										and bg.TUNGAY <=? order by bg.TUNGAY desc )  "+
											"					),0 ) as spGIA, "+
											"			isnull( ( select ptChietKhau from BGMUANPP_SANPHAM sp where SANPHAM_FK = a.pk_seq and BGMUANPP_FK in "+
											"						(	select top(1) PK_SEQ from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK "+
											"									where bg.TRANGTHAI = '1' and bg_npp.NPP_FK = ? and bg.DVKD_FK = ? and bg.KENH_FK = ?  "+
											"										and bg.TUNGAY <=? order by bg.TUNGAY desc )  "+
											"					),0 ) as ptChietKhau, "+
											"				(	 "+
											"					select top(1) PK_SEQ from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK "+
											"									where bg.TRANGTHAI = '1' and bg_npp.NPP_FK = ? and bg.DVKD_FK = ? and bg.KENH_FK = ? "+ 
											"										and bg.TUNGAY <=? order by bg.TUNGAY desc  "+
											"				) as spBGID,data.SoLuong as spSOLUONG "+
											"		from SANPHAM a inner join DONVIDOLUONG b on a.dvdl_fk = b.pk_seq "+
											" inner join   "+
											" ( SELECT spMa,spTen,SoLuong,sott FROM (" + sql_TABLE + ")as data ) as data on data.spMa=a.ma  "+
											"		where a.pk_seq > 0 and a.DVKD_FK = ? order by data.sott asc ";

							//System.out.println("[ErpDondathangNppUpdateSvl]"+query);
							db.viewQuery(query, data);
							
							List<Object> data2 = new ArrayList<Object>();
							data2.add(nppId); data2.add(dvkdId); data2.add(kbhId); data2.add(ngayyeucau);
							data2.add(nppId); data2.add(dvkdId); data2.add(kbhId); data2.add(ngayyeucau);
							data2.add(nppId); data2.add(dvkdId); data2.add(kbhId); data2.add(ngayyeucau);
							data2.addAll(data);
							data2.add(dvkdId);
							
							rs = db.getByPreparedStatement(query, data2);
							String spMA = "";
							String spTEN = "";
							String spDONVI = "";
							String spTHUE = "";
							String spGIA = "";
							String spBGID = "";
							String spSOLUONG = "";
							String spGIAGOC ="";
							String spSCHEME="";
							String spCHIETKHAU = "";

							while (rs.next())
							{
								spMA += rs.getString("SPMA") + "__";
								spTEN += rs.getString("SPTEN") + "__";
								spDONVI += rs.getString("spDONVI") + "__";
								spTHUE += rs.getString("spTHUE") + "__";
								spBGID += rs.getString("spBGID") + "__";
								spSOLUONG += rs.getString("spSOLUONG") + "__";
								spSCHEME += " " + "__";
								double ptChietKhau =rs.getDouble("ptChietKhau");
								double DonGia =rs.getDouble("spGIA");
								double GiaSauCK = DonGia*(1-ptChietKhau/100.0);
								spGIAGOC += formatter.format( DonGia )+ "__";
								spGIA += formatter.format( GiaSauCK )+ "__";
								spCHIETKHAU += ptChietKhau+ "__";
							}

							if (spMA.trim().length() > 0)
							{
								spMA = spMA.substring(0, spMA.length() - 2);
								spMa = spMA.split("__");

								spTEN = spTEN.substring(0, spTEN.length() - 2);
								spTen = spTEN.split("__");

								spDONVI = spDONVI.substring(0, spDONVI.length() - 2);
								spDonvi = spDONVI.split("__");

								spTHUE = spTHUE.substring(0, spTHUE.length() - 2);
								spvat = spTHUE.split("__");

								spGIA = spGIA.substring(0, spGIA.length() - 2);
								dongia = spGIA.split("__");
								
								spCHIETKHAU = spCHIETKHAU.substring(0, spCHIETKHAU.length() - 2);
								spChietkhau = spCHIETKHAU.split("__");

								spGIAGOC = spGIAGOC.substring(0, spGIAGOC.length() - 2);
								spGiagoc = spGIAGOC.split("__");

								spBGID = spBGID.substring(0, spBGID.length() - 2);
								spBgId = spBGID.split("__");

								spSOLUONG = spSOLUONG.substring(0, spSOLUONG.length() - 2);
								spSoluong = spSOLUONG.split("__");

								spSCHEME = spSCHEME.substring(0, spSCHEME.length() - 2);
								spScheme = spSCHEME.split("__");

							}
							lsxBean.setSpMa(spMa);
							lsxBean.setSpTen(spTen);
							lsxBean.setSpDonvi(spDonvi);
							lsxBean.setSpSoluong(spSoluong);
							lsxBean.setSpGianhap(dongia);
							lsxBean.setSpScheme(spScheme);
							lsxBean.setSpVat(spvat);
							lsxBean.setSpGiagoc(spGiagoc);
							lsxBean.setSpBgId(spBgId);
							lsxBean.setSpChietkhau(spChietkhau);

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
			}else 
			{
				id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
				if(id == null || (id!=null && id.trim().length()<=0))
				{  	
					lsxBean = new ErpDondathangNpp("");
				}
				else
				{
					lsxBean = new ErpDondathangNpp(id);
				}

				lsxBean.setUserId(userId);

				ngayyeucau = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaychuyen")));
				if(ngayyeucau == null || ngayyeucau.trim().length() <= 0)
					ngayyeucau = getDateTime();
				lsxBean.setNgayyeucau(ngayyeucau);
				session.setAttribute("ngaydonhang", lsxBean.getNgayyeucau());

				ngaydenghi = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaydenghi")));
				if(ngaydenghi == null || ngaydenghi.trim().length() <= 0)
					ngaydenghi = "";
				lsxBean.setNgaydenghi(ngaydenghi);

				System.out.println("ghi chu  1"+geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ghichu")));
				
				ghichu = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ghichu")));
				if(ghichu == null)
					ghichu = "";
				lsxBean.setGhichu(ghichu);

				System.out.println("ghi chu  1"+ghichu);
				
				
				khonhapId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khonhapId")));
				if (khonhapId == null)
					khonhapId = "";				
				lsxBean.setKhoNhapId(khonhapId);
				session.setAttribute("khoId", lsxBean.getKhoNhapId());

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
				
				congno = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("txtCongno")));
				if (congno == null)
					congno = "";				
				lsxBean.setCongno(congno);

				chietkhau = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ptChietkhau")));
				if (chietkhau == null)
					chietkhau = "";				
				lsxBean.setChietkhau(chietkhau.replaceAll(",", ""));

				vat = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ptVat")));
				if (vat == null)
					vat = "";				
				lsxBean.setVat(vat.replaceAll(",", ""));

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

				tiengiam = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("txtTiengiam")));
				if (tiengiam == null)
					tiengiam = "";				
				lsxBean.setTiengiam(tiengiam.replaceAll(",", ""));
				
				/*spMa = request.getParameterValues("spMa");
				lsxBean.setSpMa(spMa);*/
				spMa = util.antiSQLInspection_Array(request.getParameterValues("spMa"));
				lsxBean.setSpMa(spMa);

				/*spTen = request.getParameterValues("spTen");
				lsxBean.setSpTen(spTen);*/
				spTen = util.antiSQLInspection_Array(request.getParameterValues("spTen"));
				lsxBean.setSpTen(spTen);

				/*spDonvi = request.getParameterValues("donvi");
				lsxBean.setSpDonvi(spDonvi);*/
				/*System.out.println("SP DV: 1"+request.getParameterValues("donvi")[0]);
				System.out.println("SP DV: 2"+util.antiSQLInspection_Array(request.getParameterValues("donvi"))[0]);*/
				spDonvi = util.antiSQLInspection_Array(request.getParameterValues("donvi"));
				lsxBean.setSpDonvi(spDonvi);

				/*spTonkho = request.getParameterValues("spTonkho");
				lsxBean.setSpTonkho(spTonkho);*/
				
				spTonkho = util.antiSQLInspection_Array(request.getParameterValues("spTonkho"));
				lsxBean.setSpTonkho(spTonkho);
				
				/*spSoluong = request.getParameterValues("soluong");
				lsxBean.setSpSoluong(spSoluong);*/
				spSoluong = util.antiSQLInspection_Array(request.getParameterValues("soluong"));
				lsxBean.setSpSoluong(spSoluong);

				/*spSoluongtt = request.getParameterValues("soluongtt");
				lsxBean.setSoluongtt(spSoluongtt);*/
				spSoluongtt = util.antiSQLInspection_Array(request.getParameterValues("soluongtt"));
				lsxBean.setSoluongtt(spSoluongtt);

				/*spGhichu = request.getParameterValues("spGhichu");
				lsxBean.setSpGhichu(spGhichu);*/
				spGhichu = util.antiSQLInspection_Array(request.getParameterValues("spGhichu"));
				lsxBean.setSpGhichu(spGhichu);

				/*dongia = request.getParameterValues("dongia");
				lsxBean.setSpGianhap(dongia);*/
				dongia = util.antiSQLInspection_Array(request.getParameterValues("dongia"));
				lsxBean.setSpGianhap(dongia);

				/*spScheme = request.getParameterValues("scheme");
				lsxBean.setSpScheme(spScheme);*/
				spScheme = util.antiSQLInspection_Array(request.getParameterValues("scheme"));
				lsxBean.setSpScheme(spScheme);

				/*spvat = request.getParameterValues("spvat");
				lsxBean.setSpVat(spvat);*/
				spvat = util.antiSQLInspection_Array(request.getParameterValues("spvat"));
				lsxBean.setSpVat(spvat);

				/*spGiagoc = request.getParameterValues("spGiagoc");
				lsxBean.setSpGiagoc(spGiagoc);*/
				spGiagoc = util.antiSQLInspection_Array(request.getParameterValues("spGiagoc"));
				lsxBean.setSpGiagoc(spGiagoc);

				/*spBgId = request.getParameterValues("spBgId");
				lsxBean.setSpBgId(spBgId);*/
				spBgId = util.antiSQLInspection_Array(request.getParameterValues("spBgId"));
				lsxBean.setSpBgId(spBgId);
				
				/*spChietkhau = request.getParameterValues("chietkhau");
				lsxBean.setSpChietkhau(spChietkhau);*/
				spChietkhau = util.antiSQLInspection_Array(request.getParameterValues("chietkhau"));
				lsxBean.setSpChietkhau(spChietkhau);

				
				/*spNgaygiaohang = request.getParameterValues("spNgaygiaohang");
				lsxBean.setSpNgaygiaohang(spNgaygiaohang);*/
				spNgaygiaohang = util.antiSQLInspection_Array(request.getParameterValues("spNgaygiaohang"));
				lsxBean.setSpNgaygiaohang(spNgaygiaohang);

				
				action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
			}
			int[] quyen = Utility.Getquyen("ErpDondathangNppSvl", "",lsxBean.getUserId());
			if(action.equals("save"))
			{	
				if(id == null || (id!=null && id.trim().length()<=0))
				{
					boolean kq = false;
					
					//int[] quyen = Utility.Getquyen("ErpDondathangNppSvl", "",lsxBean.getUserId());
					if(quyen[Utility.THEM]==1){
						kq = lsxBean.createNK();
					}
					
					if(!kq)
					{
						lsxBean.createRs();
						session.setAttribute("lsxBean", lsxBean);
						session.setAttribute("khoId", lsxBean.getKhoNhapId());
						String nextJSP = "/AHF/pages/Distributor/ErpDonDatHangNppNew.jsp";
						response.sendRedirect(nextJSP);
					}
					else
					{
						IErpDondathangNppList obj = new ErpDondathangNppList();
						obj.setLoaidonhang(loaidonhang);

						obj.setUserId(userId);
						obj.init("");  
						session.setAttribute("obj", obj);  	
						session.setAttribute("userId", userId);

						String nextJSP = "/AHF/pages/Distributor/ErpDonDatHangNpp.jsp";
						response.sendRedirect(nextJSP);
					}
				}
				else
				{

					boolean kq = false;
					
					//int[] quyen = Utility.Getquyen("ErpDondathangNppSvl", "",lsxBean.getUserId());
					if(quyen[Utility.SUA]==1){
						kq = lsxBean.updateNK();
					}

					if(!kq)
					{
						lsxBean.createRs();
						session.setAttribute("lsxBean", lsxBean);
						session.setAttribute("khoId", lsxBean.getKhoNhapId());
						String nextJSP = "/AHF/pages/Distributor/ErpDonDatHangNppUpdate.jsp";
						response.sendRedirect(nextJSP);
					}
					else
					{
						IErpDondathangNppList obj = new ErpDondathangNppList();
						obj.setLoaidonhang(loaidonhang);

						obj.setUserId(userId);
						obj.init("");
						session.setAttribute("obj", obj);							
						String nextJSP = "/AHF/pages/Distributor/ErpDonDatHangNpp.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
			else
			{
				if(action.equals("apkhuyenmai"))
				{
					//Save donhang truoc
					if(id == null)
					{   
						boolean tao = false;
						
						if(quyen[Utility.THEM]==1){
							tao = lsxBean.createNK();
						}
						if (!tao)
						{
							lsxBean.createRs();
							session.setAttribute("lsxBean", lsxBean);
							session.setAttribute("khoId", lsxBean.getKhoNhapId());
							String nextJSP = "/AHF/pages/Distributor/ErpDonDatHangNppNew.jsp";

							if(!response.isCommitted())
							{
								response.sendRedirect(nextJSP);
							}
						}
						else
						{
							id = lsxBean.getId();		
						}						
					}
					else
					{
						boolean temp = false;
						if(quyen[Utility.SUA]==1){
							temp = lsxBean.updateNK();
						}
						if (!temp)
						{
							lsxBean.createRs();
							session.setAttribute("lsxBean", lsxBean);
							session.setAttribute("khoId", lsxBean.getKhoNhapId());
							String nextJSP = "/AHF/pages/Distributor/ErpDonDatHangNppUpdate.jsp";

							if(!response.isCommitted())
							{
								response.sendRedirect(nextJSP);
							}
						}
					}

					Hashtable<String, Integer> sanpham_soluong = new Hashtable<String, Integer>();
					Hashtable<String, Float> sanpham_dongia = new Hashtable<String, Float>();
					Hashtable<String, Float> sanpham_quycach = new Hashtable<String, Float>();

					String soluong1 = "";
					String spMaKM = "";
					String spSOLUONGKM = "";
					String spDONGIAKM = "";
					float tongiatriDH = 0;

					if(id!=null && id.trim().length() > 0)
					{	
						//INIT SP VOI QUY CACH NEU TRUONG HOP KHONG PHAI LA DV CHUAN
						dbutils db = new dbutils();

						//XOA HET KM CU NEU CO
						String query = " delete ERP_DONDATHANG_CTKM_TRAKM where DONDATHANGID = ? ";
						db.updateQueryByPreparedStatement(query, new Object[]{id});

						///gia tinh km sau thue kenh GT, gia k thue kenh MT
						query = "  select ( select ma from SANPHAM where PK_SEQ = a.sanpham_fk ) as spMA, a.dvdl_fk, b.DVDL_FK as dvCHUAN,  " +
								" 		case when a.dvdl_fk = b.DVDL_FK then a.soluong  " +
								"  			 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and DVDL1_FK = b.DVDL_FK) end as soluong, " +
								"  		case when a.dvdl_fk = b.DVDL_FK then a.dongia  " +
								"  			 else  a.dongia / ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and DVDL1_FK = b.DVDL_FK) end as dongia,  " +
								"  		case when a.dvdl_fk = b.DVDL_FK then 1  " +
								"  			 else  ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and DVDL1_FK = b.DVDL_FK)   end as quycach    " +
								"  from ERP_DONDATHANG_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ    " +
								"  where a.dondathang_fk = ?  " ;
						dbutils.viewQuery(query, new Object[]{id});;
						ResultSet rsSP = db.getByPreparedStatement(query, new Object[]{id});
						{
							try 
							{
								while(rsSP.next())
								{
									float dongiakm;
									
									dongiakm = rsSP.getFloat("dongia")/1.1f;
									
									sanpham_soluong.put(rsSP.getString("spMA"), rsSP.getInt("soluong"));
									sanpham_dongia.put(rsSP.getString("spMA"), dongiakm);
									sanpham_quycach.put(rsSP.getString("spMA"), rsSP.getFloat("quycach"));

									spMaKM += rsSP.getString("spMA") + "__";
									spSOLUONGKM += rsSP.getInt("soluong") + "__";
									spDONGIAKM += dongiakm + "__";

									soluong1 += rsSP.getString("quycach") + "__";
									tongiatriDH += rsSP.getInt("soluong") * rsSP.getFloat("dongia");
								}
								rsSP.close();
							} 
							catch (Exception e) {e.printStackTrace();}	
						}

						db.shutDown();
					}


					XLkhuyenmaiTT xlkm = new XLkhuyenmaiTT(userId, ngayyeucau, nppId, id); //ngay giao dich trong donhang

					xlkm.setMasp(spMaKM.substring(0, spMaKM.length() - 2).split("__"));
					xlkm.setSoluong(spSOLUONGKM.substring(0, spSOLUONGKM.length() - 2).split("__"));
					xlkm.setDongia(spDONGIAKM.substring(0, spDONGIAKM.length() - 2).split("__"));

					xlkm.setQuycach(soluong1.substring(0, soluong1.length() - 2).split("__"));
					xlkm.setTonggiatriDh(tongiatriDH);
					xlkm.setIdDonhang(id);
					xlkm.setNgaygiaodich(ngayyeucau);
					xlkm.setLoaiDonHang("0");

					xlkm.setHashA(sanpham_soluong);
					xlkm.setHashB(sanpham_dongia);
					xlkm.setHash_QuyCach(sanpham_quycach);

					xlkm.setDieuchinh(false); //Lay truong hop ngau nhien /*****dms set lai la True******/

					xlkm.ApKhuyenMai();

					List<ICtkhuyenmai> ctkmResual = xlkm.getCtkmResual();
					System.out.println("+++So xuat khuyen mai duoc huong: " + ctkmResual.size() + "\n");

					if(xlkm.checkConfirm()) //bi dung --> sang trang lua chon
					{
						
						xlkm.SetDungSanPham(); // Huy chỉnh
						
						System.out.println("Bi dung san pham roi...\n");
						session.setAttribute("xlkm", xlkm);
						String nextJSP = "/AHF/pages/Distributor/KhuyenMaiNpp.jsp";
						if(!response.isCommitted())
						{
							response.sendRedirect(nextJSP);
						}
						return;
					}

					String msg = ""; //nhung ctkm khong thoa
					for(int i = 0; i < ctkmResual.size(); i++)
					{
						ICtkhuyenmai ctkhuyenmai = ctkmResual.get(i);

						//System.out.println("ConFi laf: "+ctkhuyenmai.getConfirm());
						if(ctkhuyenmai.getConfirm() == false) //khong dung chung san pham
						{	
							msg += CreateKhuyenmai(ctkhuyenmai, id, nppId, ngayyeucau, Math.round(tongiatriDH), sanpham_soluong, sanpham_dongia);

							//remove khoi danh sach
							ctkmResual.remove(i);	
							i = i -1;
						}
					}


					if(msg.length() > 0)
					{
						lsxBean.init();
						lsxBean.setAplaikhuyenmai(false);
						xlkm.DBclose();
						lsxBean.setMsg("khong the them moi 'dondathang_ctkm_trakm' " + msg);
						session.setAttribute("lsxBean", lsxBean);
						session.setAttribute("khoId", lsxBean.getKhoNhapId());
						String nextJSP = "/AHF/pages/Distributor/ErpDonDatHangNppUpdate.jsp";
						
						
						if(!response.isCommitted())
						{
							response.sendRedirect(nextJSP);
						}
						
						return;
					}

					String nextJSP = "";
					System.out.println("toi day ");
					if(ctkmResual.size() > 0)
					{
						
						session.setAttribute("xlkm", xlkm);
						nextJSP = "/AHF/pages/Distributor/KhuyenMaiNpp.jsp";
						
						System.out.println("toi day 1");
						if(!response.isCommitted())
						{
							response.sendRedirect(nextJSP);
						}
						
						return;
					}
					else
					{	
						xlkm.DBclose();
						lsxBean.init();
						lsxBean.setAplaikhuyenmai(false);
						session.setAttribute("lsxBean", lsxBean);
						session.setAttribute("khoId", lsxBean.getKhoNhapId());
						nextJSP = "/AHF/pages/Distributor/ErpDonDatHangNppUpdate.jsp";
						System.out.println("toi day 2");
						if(!response.isCommitted())
						{
							response.sendRedirect(nextJSP);
						}
						return;
					}
				}
				else
				{
					lsxBean.createRs();

					session.setAttribute("dvkdId", lsxBean.getDvkdId());
					session.setAttribute("kbhId", lsxBean.getKbhId());
					session.setAttribute("nppId", lsxBean.getNppId());
					session.setAttribute("khoId", lsxBean.getKhoNhapId());
					session.setAttribute("lsxBean", lsxBean);

					String nextJSP = "";

					nextJSP = "/AHF/pages/Distributor/ErpDonDatHangNppNew.jsp";
					if(id != null)
					{
						nextJSP = "/AHF/pages/Distributor/ErpDonDatHangNppUpdate.jsp";
					}

					response.sendRedirect(nextJSP);
				}
			}
		}
	}
	
	


	private String CreateKhuyenmai(ICtkhuyenmai ctkm, String id, String nppId, String tungay, long tongGtridh, Hashtable<String, Integer> sp_sl, Hashtable<String, Float> sp_dg)
	{
		String str = "";
		dbutils db = new dbutils();

		try 
		{ 
			db.getConnection().setAutoCommit(false);

			List<ITrakhuyenmai> trakmList = ctkm.getTrakhuyenmai();
			for(int count = 0; count < trakmList.size(); count++)
			{
				//ITrakhuyenmai trakm = ctkm.getTrakhuyenmai().get(0);			
				ITrakhuyenmai trakm = ctkm.getTrakhuyenmai().get(count);

				long tongtienTraKM = 0;
				if(trakm.getType() == 1)
					tongtienTraKM = Math.round(ctkm.getSoxuatKM() * trakm.getTongtien());
				else
				{
					if(trakm.getType() == 2) //tra chiet khau
					{
						System.out.println("___Tong tien tra km: " + ctkm.getTongTienTheoDKKM() + " -- Chiet khau: " + trakm.getChietkhau());
						//Tinh tong gia tri tra khuyen mai theo dieu kien (chu khong phai tren tong gia tri don hang)
						long tonggiatriTrakm = ctkm.getTongTienTheoDKKM();
						tongtienTraKM = Math.round(tonggiatriTrakm * (trakm.getChietkhau() / 100));
						//tongtienTraKM = Math.round(tongGtridh * (trakm.getChietkhau() / 100));
					}
					else
					{
						if(trakm.getHinhthuc() == 1)
						{
							tongtienTraKM = Math.round(ctkm.getSoxuatKM() * trakm.getTongGtriKm());
							System.out.println("Tong tien trakm co dinh: " + tongtienTraKM + "\n");
						}
					}
				}

				/*********************************************************************************/
				if(ctkm.getPhanbotheoDH().equals("0"))
				{
					String msg = "";
					if(msg.trim().length() > 0)
					{
						db.getConnection().rollback();
						return msg;
					}
				}
				/*********************************************************************************/

				if(trakm.getType() == 3) //san pham co so luong co dinh
				{
					if(trakm.getHinhthuc() == 1)
					{

						String sql = "select f.pk_seq as spId, a.soluong, e.GIAMUANPP as dongia, f.ma as spMa  " +
								"from Trakhuyenmai_sanpham a inner join SANPHAM f on a.SANPHAM_FK = f.PK_SEQ " +
								"	inner join BGMUANPP_SANPHAM e on a.sanpham_fk = e.SANPHAM_FK " +
								"where a.TRAKHUYENMAI_FK = ? " +
								"and e.BGMUANPP_FK in ( select top(1) a.PK_SEQ " +
								"from BANGGIAMUANPP a inner join BANGGIAMUANPP_NPP b on a.PK_SEQ = b.BANGGIAMUANPP_FK  " +
								"where a.TUNGAY <= ? and b.NPP_FK = ? and a.KENH_FK = ( select KBH_FK from ERP_DONDATHANG where PK_SEQ = ? ) and a.DVKD_FK = ( select DVKD_FK from ERP_DONDATHANG where PK_SEQ = ? ) " +
								"order by a.TUNGAY desc  ) -- and e.GIAMUANPP > 0  and e.trangthai = '1'  ";

						//System.out.println("Query lay gia san pham co dinh la: " + sql + "\n");

						int index = 0;
						ResultSet rsSQl = db.getByPreparedStatement(sql, new Object[]{trakm.getId(),tungay,nppId,id,id});
						if(rsSQl != null)
						{
							while(rsSQl.next())
							{
								int slg = Integer.parseInt(rsSQl.getString("soluong")) * (int)ctkm.getSoxuatKM();
								long tonggtri = Math.round(slg * rsSQl.getFloat("dongia"));
								String error = "";
								if(error.length() > 0)
								{
									return error;
								}

								//luu tong gia tri o moi dong sanpham
								//String query = "Insert into ERP_DONDATHANG_CTKM_TRAKM(DONDATHANGID, ctkmId, trakmId, soxuat, tonggiatri, spMa, soluong) values('" + id + "','" + ctkm.getId() + "','" + trakm.getId() + "','" + ctkm.getSoxuatKM() + "','" + Long.toString(tongtienTraKM) + "', '" + rsSQl.getString("spMa").trim() + "', '" + Integer.toString(slg) + "')";
								String query =  "\n Insert into ERP_DONDATHANG_CTKM_TRAKM(DONDATHANGID, ctkmId, trakmId, soxuat, tonggiatri,Sanpham_fk, spMa, soluong)" +
												"\n select ?,?,?,?,?,? ,?, ? " ;
								System.out.println("1.Chen khuyen mai co dinh: " + query);

								if(db.updateQueryByPreparedStatement(query, new Object[]{id,ctkm.getId(),trakm.getId(),ctkm.getSoxuatKM(),Long.toString(tonggtri),rsSQl.getString("spId").trim(),rsSQl.getString("spMa").trim(),Integer.toString(slg)}) == -1)
								{
									db.getConnection().rollback();
									str = query;
									return str;
								}

								String msg = CheckNghanSach(ctkm.getId(),nppId,"1","",db);
								if(msg.trim().length() > 0)
								{
									db.getConnection().rollback();
									return msg;
								}

								index ++;
							}
						}
						rsSQl.close();
					}
					else //tinh so luong san pham nhapp da chon, phai check ton kho tung buoc
					{
						if(trakm.getHinhthuc() == 2)
						{

							String query = "select a.sanpham_fk as spId, c.MA as spMa, isnull(bgmnpp.dongia, '0') as dongia, isnull(b.TONGLUONG, 0) as tongluong " +
									"from TRAKM_NHAPP a inner join TRAKHUYENMAI b on a.trakm_fk = b.PK_SEQ " +
									" inner join SANPHAM c on a.sanpham_fk = c.PK_SEQ " +
									" left join (  select sanpham_fk, GIAMUANPP as dongia  " +
									"				from BGMUANPP_SANPHAM   " +
									"				where BGMUANPP_FK in (  select top(1) a.PK_SEQ " +
									"										from BANGGIAMUANPP a inner join BANGGIAMUANPP_NPP b on a.PK_SEQ = b.BANGGIAMUANPP_FK   " +
									"										where a.TUNGAY <= ? and b.NPP_FK = ? and a.KENH_FK = ( select kbh_fk from ERP_DONDATHANG where pk_seq=? ) and a.DVKD_FK = ( select dvkd_fk from ERP_DONDATHANG where pk_seq=? ) " +
									"										order by a.TUNGAY desc  )   " +
									") bgmnpp on bgmnpp.sanpham_fk=a.sanpham_fk" + 
									" where a.ctkm_fk = ? and a.npp_fk = ? and a.trakm_fk = ?" +
									"order by a.thutuuutien asc";

							//System.out.println("5.Query tinh gia km npp chon truoc: " + query);
							db.viewQuery(query, new Object[]{tungay,nppId,id,id,ctkm.getId(),nppId,trakm.getId()});

							ResultSet spkm = db.getByPreparedStatement(query, new Object[]{tungay,nppId,id,id,ctkm.getId(),nppId,trakm.getId()});

							String sp = "";
							String ma = "";
							String dg = "";
							String tg = "";
							while(spkm.next())
							{
								sp += spkm.getString("spId") + ",";
								dg += spkm.getString("dongia") + ",";
								tg += spkm.getString("tongluong") + ",";
								ma += spkm.getString("spMa") + ",";
							}

							String[] spId = sp.split(",");
							String[] dongia = dg.split(",");
							String[] tongluong = tg.split(",");
							String[] spMa = ma.split(",");

							//CheckTonKho nhung tra khuyen mai da duoc npp chon truoc
							String[] arr = checkTonKhoTraKm(nppId, ctkm, spId, dongia, tongluong, spMa);
							if(arr == null)  //nhung san pham da chon truoc cua tra khuyen mai da het hang trong kho
							{
								db.getConnection().rollback();
								str = "Số lượng những sản phẩm bạn chọn trước trong thiết lập sản phẩm trả khuyến mãi không đủ trong kho";
								System.out.println("Error: " + str + "\n");
								return str;
							}
							else
							{
								/*********************************************************************************/
								
								/*********************************************************************************/

								//luu tong gia tri o moi dong sanpham
								query = " Insert into ERP_DONDATHANG_CTKM_TRAKM(DONDATHANGID, ctkmId, trakmId, soxuat, tonggiatri,sanpham_fk, spMa, soluong) "+
										" values(?,?,?,?,?, ?, ?, ?)";
								System.out.println("6.Chen khuyen mai Npp chon truoc: " + query);

								if(db.updateQueryByPreparedStatement(query, new Object[]{id,ctkm.getId(),trakm.getId(),ctkm.getSoxuatKM(),arr[2].replaceAll(",", ""),arr[0],arr[3],arr[1].replaceAll(",", "")}) < 1)
								{
									db.getConnection().rollback();
									str = query;
									return str;
								}

								String msg = CheckNghanSach(ctkm.getId(),nppId,"1","",db);
								if(msg.trim().length() > 0)
								{
									db.getConnection().rollback();
									return msg;
								}
							
							}
						}
					}
				}
				else
				{
					if(trakm.getType() != 3)//tra tien, tra chiet khau
					{
						String query = "Insert into ERP_DONDATHANG_CTKM_TRAKM(DONDATHANGID, ctkmId, trakmId, soxuat, tonggiatri) values(?,?,?,?,?)";
						System.out.println("10.Chen khuyen mai tien / ck: " + query);
						if(db.updateQueryByPreparedStatement(query, new Object[]{id,ctkm.getId(),trakm.getId(),ctkm.getSoxuatKM(),tongtienTraKM}) < 1)
						{
							db.getConnection().rollback();
							str = query;
							return str;
						}
						
						String msg = CheckNghanSach(ctkm.getId(),nppId,"0","",db);
						if(msg.trim().length() > 0)
						{
							db.getConnection().rollback();
							return msg;
						}
					}
				}
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e1)
		{
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e) {}
			return "Loi khi tao moi ctkm " + ctkm + ", " + e1.toString(); 
		}
		finally
		{
			db.shutDown();
		}

		return str;
	}

	private String[] checkTonKhoTraKm(String nppId, ICtkhuyenmai ctkm, String[] spId, String[] dongia, String[] tongluong, String[] spma) 
	{
		String[] kq = new String[4];

		String msg = "";
		try
		{
			for(int i = 0; i < spId.length; i++)
			{
				int slg = Integer.parseInt(tongluong[i]) * ctkm.getSoxuatKM();
				msg = "";
				if(msg == "")  //thoa ton kho
				{
					kq[0] = spId[i];
					kq[1] = Integer.toString(slg);
					kq[2] = Long.toString(Math.round(slg * Float.parseFloat(dongia[i])));
					//System.out.println("Don gia: " + spId[i] + "- dongia: " + dongia[i] + " - Tong gia tri o buoc nay: " + kq[2] + "\n");
					kq[3] = spma[i];

					return kq;
				}
			}
		}
		catch (Exception e) {
			return null;
		}
		return null;
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
	
	
	private String CheckNghanSach(String ctkmId, String nppId, String ngansach, String loai, dbutils db)
	{
		String sql = 
				" select a.CTKM_FK, b.scheme, b.PHANBOTHEODONHANG, a.NGANSACH, "+  	 
						"	case when b.PHANBOTHEODONHANG=1 then "+
						"	 ISNULL( 	( select SUM(SOLUONG)  	  "+
						"	  from ERP_DONDATHANG_CTKM_TRAKM  	  "+
						"	 where CTKMID = a.CTKM_FK AND SPMA IS NOT NULL and DONDATHANGID in  "+
						"	 ( select PK_SEQ from ERP_DONDATHANG where NPP_FK = a.NPP_FK and TRANGTHAI != 3 )),0) "+ 
						"	 else  "+
						"		  ISNULL( 	( select SUM(tonggiatri)  "+  
						"		  from ERP_DONDATHANG_CTKM_TRAKM   "+
						"		  where CTKMID = a.CTKM_FK and DONDATHANGID in ( select PK_SEQ from ERP_DONDATHANG where NPP_FK = a.NPP_FK and TRANGTHAI != 3 )),0)  "+
						"	END as DASUDUNG  "+
						"	 from phanbokhuyenmai a inner join CTKHUYENMAI b on a.CTKM_FK = b.PK_SEQ   "+
						"where npp_fk = ? and ctkm_fk = ?  ";

		//System.out.println("1.Cau lenh check ngan sach: " + sql + " --- Ngan sach de check: " + ngansach);
		db.viewQuery(sql, new Object[]{nppId,ctkmId});
		ResultSet rs = db.getByPreparedStatement(sql, new Object[]{nppId,ctkmId});
		String scheme = "";

		try 
		{
			double NganSach=0;
			double DaSuDung =0;
			if(rs.next())
			{
				scheme = rs.getString("scheme");
				NganSach=rs.getDouble("NganSach");
				DaSuDung=rs.getDouble("DaSuDung");
				rs.close();	
			}
			System.out.println("---NGAN SACH ( SOXUAT / TONG GIA TRI ): " + NganSach + " -- Da Su Dung : " + DaSuDung);
			if(DaSuDung > NganSach)
			{
				return "1.Chương trình khuyến mại: " + scheme + ", đã hết ngân sách phân bổ -Ngân sách("+NganSach+") - Sử dụng ("+DaSuDung+")";
			}
		} 
		catch (Exception e) 
		{ 
			e.printStackTrace();
			System.out.println("__EXCEPTION CHECK NGAN SACH: " + e.getMessage());
			return "2.Chương trình khuyến mại: " + scheme + ", đã hết ngân sách phân bổ";
		}

		return "";
	}

}