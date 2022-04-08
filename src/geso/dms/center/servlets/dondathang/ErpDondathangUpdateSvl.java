package geso.dms.center.servlets.dondathang;

import geso.dms.center.beans.dondathang.IErpDondathang;
import geso.dms.center.beans.dondathang.IErpDondathangList;
import geso.dms.center.beans.dondathang.imp.ErpDondathang;
import geso.dms.center.beans.dondathang.imp.ErpDondathangList;
import geso.dms.center.beans.dondathang.imp.XLkhuyenmaiTT;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.ctkhuyenmai.ICtkhuyenmai;
import geso.dms.distributor.beans.dondathang.imp.ErpDondathangNpp;
import geso.dms.distributor.beans.trakhuyenmai.ITrakhuyenmai;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
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

public class ErpDondathangUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	PrintWriter out;

	private String[] spvat;
	public ErpDondathangUpdateSvl() 
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
			
			//--- check user
//			Utility util = new Utility();
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
			if( Utility.CheckRuleUser( session,  request, response, "ErpDondathangSvl", param, Utility.XEM ))
			{
				Utility.RedireactLogin(session, request, response);
			}
			 //--- check user 

			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);

			if (userId.length()==0)
				userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"))); 

			String id = util.getId(querystring); 

			//GIU NGUYEN TIM KIEM
			IErpDondathangList lsxBeanList = new ErpDondathangList();

			String tungaytk = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"));
			if(tungaytk == null)
				tungaytk = "";	    	   	    
			lsxBeanList.setTungay(tungaytk);

			String denngaytk = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay"));
			if(denngaytk == null)
				denngaytk = "";	    	   	    
			lsxBeanList.setDenngay(denngaytk);

			String vungtk = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"));
			if(vungtk == null)
				vungtk = "";	    	   	    
			lsxBeanList.setVungId(vungtk);

			String khuvuctk = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId"));
			if(khuvuctk == null)
				khuvuctk = "";	    	   	    
			lsxBeanList.setKhuvucId(khuvuctk);

			String npptk = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
			if(npptk == null)
				npptk = "";	    	   	    
			lsxBeanList.setNppTen(npptk);

			String kbhtk = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId"));
			if(kbhtk == null)
				kbhtk = "";	    	   	    
			lsxBeanList.setKbhId(kbhtk);		    		   

			String trangthaitk = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"));		    
			if(trangthaitk == null)
				trangthaitk = "";	    	   	    
			lsxBeanList.setTrangthai(trangthaitk);

			session.setAttribute("lsxBeanList", lsxBeanList);

			//--------------------------------------------------------

			IErpDondathang lsxBean = new ErpDondathang(id);
			lsxBean.setUserId(userId); 		    		  

			String nextJSP = "";

			lsxBean.init();

			session.setAttribute("dvkdId", lsxBean.getDvkdId());
			session.setAttribute("kbhId", lsxBean.getKbhId());
			session.setAttribute("nppId", lsxBean.getNppId());
			session.setAttribute("khoId", lsxBean.getKhoNhapId());
			session.setAttribute("ngaydonhang", lsxBean.getNgayyeucau());

			String copy = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("copy"));
			if(copy != null)
			{
				nextJSP = "/AHF/pages/Center/ErpDonDatHangNew.jsp";
				if(lsxBean.getLoaidonhang().equals("4"))
					nextJSP = "/AHF/pages/Center/ErpDonDatHangKhacNew.jsp";
				else if(lsxBean.getLoaidonhang().equals("2"))
					nextJSP = "/AHF/pages/Center/ErpDonHangKMTichLuyNew.jsp";
				else if(lsxBean.getLoaidonhang().equals("1"))
					nextJSP = "/AHF/pages/Center/ErpDonHangKMUngHangNew.jsp";
				else if(lsxBean.getLoaidonhang().equals("3"))
					nextJSP = "/AHF/pages/Center/ErpDonHangTrungBayNew.jsp";
			}
			else if(!querystring.contains("display"))
			{
				nextJSP = "/AHF/pages/Center/ErpDonDatHangUpdate.jsp";
				if(lsxBean.getLoaidonhang().equals("4"))
					nextJSP = "/AHF/pages/Center/ErpDonDatHangKhacUpdate.jsp";
				else if(lsxBean.getLoaidonhang().equals("2"))
					nextJSP = "/AHF/pages/Center/ErpDonHangKMTichLuyUpdate.jsp";
				else if(lsxBean.getLoaidonhang().equals("1"))
					nextJSP = "/AHF/pages/Center/ErpDonHangKMUngHangUpdate.jsp";
				else if(lsxBean.getLoaidonhang().equals("3"))
					nextJSP = "/AHF/pages/Center/ErpDonHangTrungBayUpdate.jsp";
			}

			else
			{
				nextJSP = "/AHF/pages/Center/ErpDonDatHangDisplay.jsp";
				if(lsxBean.getLoaidonhang().equals("4"))
					nextJSP = "/AHF/pages/Center/ErpDonDatHangKhacDisplay.jsp";
				else if(lsxBean.getLoaidonhang().equals("2"))
					nextJSP = "/AHF/pages/Center/ErpDonHangKMTichLuyDisplay.jsp";
				else if(lsxBean.getLoaidonhang().equals("1"))
					nextJSP = "/AHF/pages/Center/ErpDonHangKMUngHangDisplay.jsp";
				else if(lsxBean.getLoaidonhang().equals("3"))
					nextJSP = "/AHF/pages/Center/ErpDonHangTrungBayDisplay.jsp";
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
		NumberFormat formater = new DecimalFormat("#,###,###.###");

		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}
		else
		{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			session.setMaxInactiveInterval(30000);
			this.out = response.getWriter();
			IErpDondathang lsxBean;
			Utility util = new Utility();

			//--- check user
//			Utility util = new Utility();
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
			if( Utility.CheckRuleUser( session,  request, response, "ErpDondathangSvl", param, Utility.XEM ))
			{
				Utility.RedireactLogin(session, request, response);
			}
			 //--- check user 

			String id=null;
			String loaidonhang=null;
			String ngayyeucau=null;
			String nppId=null;
			String action = null;		    
			String kbhId = null;
			String tiengiam = null;
			String pokhuyenmai = null;
			
			String[] spMa =			null;
			String[] spTen=			null;
			String[] spDonvi=			null;
			String[] spTonkho=			null;
			String[] dongia=			null;
			String[] spGiagoc=			null;
			String[] spBgId=			null;
			String[] spSoluong=			null;
			String[] spSoluongTT=			null;
			String[] spChietkhau=			null;
			String[] spScheme=			null;
			String[] spNgaygiaohang=			null;
			String[] spGhichu=			null;
			String[] spTrakmId =null;

			String contentType = request.getContentType();
			if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
			{
				//MultipartRequest multi = new MultipartRequest(request, "C:\\java-tomcat\\data\\", 20000000, "UTF-8");
				MultipartRequest multi = new MultipartRequest(request, getServletContext().getRealPath("\\upload\\"), 20000000, "UTF-8"); 
				id = util.antiSQLInspection(multi.getParameter("id"));
				if(id == null)
				{  	
					lsxBean = new ErpDondathang("");
				}
				else
				{
					lsxBean = new ErpDondathang(id);
				}

				lsxBean.setUserId(userId);

				action = multi.getParameter("action");		    
				ngayyeucau = util.antiSQLInspection(multi.getParameter("ngaychuyen"));
				if(ngayyeucau == null || ngayyeucau.trim().length() <= 0)
					ngayyeucau = getDateTime();
				lsxBean.setNgayyeucau(ngayyeucau);
				session.setAttribute("ngaydonhang", lsxBean.getNgayyeucau());

				String ngaydenghi = util.antiSQLInspection(multi.getParameter("ngaydenghi"));
				if(ngaydenghi == null || ngaydenghi.trim().length() <= 0)
					ngaydenghi = "";
				lsxBean.setNgaydenghi(ngaydenghi);

				String ghichu = util.antiSQLInspection(multi.getParameter("ghichu"));
				if(ghichu == null)
					ghichu = "";
				lsxBean.setGhichu(ghichu);

				String khonhapId = util.antiSQLInspection(multi.getParameter("khonhapId"));
				if (khonhapId == null)
					khonhapId = "";				
				lsxBean.setKhoNhapId(khonhapId);
				session.setAttribute("khoId", lsxBean.getKhoNhapId());

				String dvkdId = util.antiSQLInspection(multi.getParameter("dvkdId"));
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

				String vat = util.antiSQLInspection(multi.getParameter("ptVat"));
				if (vat == null)
					vat = "";				
				lsxBean.setVat(vat.replaceAll(",", ""));


				String txtCongNo = util.antiSQLInspection(multi.getParameter("txtCongNo"));
				if (txtCongNo == null)
					txtCongNo = "";				
				lsxBean.setCongNo(txtCongNo.replaceAll(",", ""));

				loaidonhang = util.antiSQLInspection(multi.getParameter("loaidonhang"));
				if (loaidonhang == null)
					loaidonhang = "";				
				lsxBean.setLoaidonhang(loaidonhang);
				
				tiengiam = util.antiSQLInspection(multi.getParameter("txtTiengiam"));
				if (tiengiam == null)
					tiengiam = "";				
				lsxBean.setTiengiam(tiengiam.replaceAll(",", ""));
				
				pokhuyenmai = util.antiSQLInspection(multi.getParameter("pokhuyenmai"));
				if (pokhuyenmai == null)
					pokhuyenmai = "0";				
				lsxBean.setPOKhuyenMai(pokhuyenmai);

				String[] schemeIds = multi.getParameterValues("schemeIds");
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
						for (int row=indexRow; row < sodong ; row++)
						{

							String _spMa =getValue(sheet, 0,row);
							String _spTen =getValue(sheet, 1,row);
							String _spSoluong =getValue(sheet, 2,row);
							String _spSoluongTT =getValue(sheet, 3,row);
							String spNGAYGIAOHANG =getValue(sheet, 4,row);
							String spGHICHU =getValue(sheet, 5,row);
							index++;
							if(_spMa.length()>0&&_spSoluongTT.length()>0)
							{
								if(index!=1)
								{
									sql_TABLE+=" union all ";							
								}

								if(_spMa.length()>0&&_spSoluongTT.length()>0)
									sql_TABLE +="\n  select '"+_spMa+"' as spMa,N'"+_spTen+"' as spTen, N'"+_spSoluong+"' as Soluong,N'"+_spSoluongTT+"' as SoluongTT,N'"+spGHICHU+"' as GhiChu,'"+spNGAYGIAOHANG+"'  as NgayGiaoHang,"+index+" as SoTT ";
							}
						}						
						String query = "";
						String msg = "";
						query = "";

						query = "SELECT * FROM (" + sql_TABLE + ") as data " + " WHERE spMa NOT IN " + " ( " + "	SELECT MA FROM SANPHAM " + " )";
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
									"		select a.ma as spMa, a.ten as spTen, b.donvi as spDonVi,a.ptThue as spTHUE,  "+
											"			isnull( ( select GIAMUANPP from BGMUANPP_SANPHAM sp where SANPHAM_FK = a.pk_seq and BGMUANPP_FK in "+
											"						(	select top(1) PK_SEQ from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK "+
											"									where bg.TRANGTHAI = '1' and bg_npp.NPP_FK = '"+nppId+"' and bg.DVKD_FK = '"+dvkdId+"' and bg.KENH_FK = '"+kbhId+"'  "+
											"										and bg.TUNGAY <='"+ngayyeucau+"' order by bg.TUNGAY desc )  "+
											"					),0 ) as spGIA, "+
											"			isnull( ( select ptChietKhau from BGMUANPP_SANPHAM sp where SANPHAM_FK = a.pk_seq and BGMUANPP_FK in "+
											"						(	select top(1) PK_SEQ from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK "+
											"									where bg.TRANGTHAI = '1' and bg_npp.NPP_FK = '"+nppId+"' and bg.DVKD_FK = '"+dvkdId+"' and bg.KENH_FK = '"+kbhId+"'  "+
											"										and bg.TUNGAY <='"+ngayyeucau+"' order by bg.TUNGAY desc )  "+
											"					),0 ) as ptChietKhau, "+
											"				(	 "+
											"					select top(1) PK_SEQ from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK "+
											"									where bg.TRANGTHAI = '1' and bg_npp.NPP_FK = '"+nppId+"' and bg.DVKD_FK = '"+dvkdId+"' and bg.KENH_FK = '"+kbhId+"' "+ 
											"										and bg.TUNGAY <='"+ngayyeucau+"' order by bg.TUNGAY desc  "+
											"				) as spBGID,data.SoLuong as spSOLUONG,data.SoluongTT as spSoLuongTT ,data.GhiChu as spGhiChu,data.NgayGiaoHang  as spNgayGiaoHang ,data.SoLuongTT  "+
											"		from SANPHAM a inner join DONVIDOLUONG b on a.dvdl_fk = b.pk_seq "+
											" inner join   "+
											" ( SELECT * FROM (" + sql_TABLE + ")as data ) as data on data.spMa=a.ma  "+
											"		where a.pk_seq > 0 and a.DVKD_FK = '"+dvkdId+"' order by data.SoTT  ";
							System.out.println("[ErpDondathangNppUpdateSvl]"+query);
							rs = db.get(query);
							String spMA = "";
							String spTEN = "";
							String spDONVI = "";
							String spTONKHO = "";
							String spTHUE = "";
							String spGIA = "";
							String spBGID = "";
							String spSOLUONG = "";
							String spSOLUONGTT = "";
							String spCHIETKHAU="";
							String spGIAGOC ="";
							String spSCHEME="";
							String spGHICHU="";
							String spNGAYGIAOHANG="";
							String spTRAKMID = "";

							while (rs.next())
							{
								spMA += rs.getString("SPMA") + "__";
								spTEN += rs.getString("SPTEN") + "__";
								spDONVI += rs.getString("spDONVI") + "__";
								spTONKHO += " __";
								spTHUE += rs.getString("spTHUE") + "__";
								spGIAGOC += formater.format( rs.getDouble("spGIA") )+ "__";
								spBGID += rs.getString("spBGID") + "__";
								spSOLUONG += rs.getString("spSOLUONG") + "__";
								spSOLUONGTT += (rs.getString("spSOLUONGTT").trim().length()<=0)?"0":rs.getString("spSOLUONGTT") +"__";
								spGHICHU+= (rs.getString("spGHICHU").trim().length()<=0)?" __":rs.getString("spGHICHU") +"__";
								spNGAYGIAOHANG += (rs.getString("spNGAYGIAOHANG")==null||rs.getString("spNGAYGIAOHANG").trim().length()<=0)?" __":rs.getString("spNGAYGIAOHANG") + " __";

								spSCHEME += " " + "__";
								spCHIETKHAU += "0" + "__";
								double ptChietKhau =rs.getDouble("ptChietKhau");
								double DonGia =rs.getDouble("spGIA");
								double GiaSauCK = DonGia*(1-ptChietKhau/100.0);
								spGIA += formater.format( GiaSauCK )+ "__";
								spTRAKMID+=" __";
							}


							if(!action.equals("apkhuyenmai"))
							{
								query = "select  a.TRAKMID, isnull(b.MA, ' ') as MA, isnull(b.TEN, ' ') as TEN, isnull(c.DONVI, ' ') as donvi, d.SCHEME, isnull(a.soluong, 0) as soluong, a.tonggiatri,0 as ChietKhau " +
										"from ERP_DONDATHANG_CTKM_TRAKM a left join SANPHAM b on a.SPMA = b.MA  " +
										"	left join DONVIDOLUONG c on b.DVDL_FK = c.PK_SEQ  " +
										"	inner join CTKHUYENMAI d on a.ctkmID = d.PK_SEQ " +
										"where a.dondathangID = '"+((id ==null || id.length()<=0)?"0":id )+ "' " ;
								System.out.println("--INIT SPKM: " + query);

								rs = db.get(query);
								while(rs.next())
								{
									spMA += rs.getString("MA") + "__";
									spTEN += rs.getString("TEN") + "__";
									spDONVI += rs.getString("DONVI") + "__";
									spTONKHO += " __";
									spSOLUONG += formater.format(rs.getDouble("SOLUONG")) + "__";
									spGIA += formater.format(rs.getDouble("tonggiatri")) + "__";
									spTHUE += "0__";
									spSCHEME += rs.getString("SCHEME") + "__";
									spCHIETKHAU += formater.format(rs.getDouble("ChietKhau")) + "__";
									spGIAGOC += " __";
									spBGID += " __";

									spSOLUONGTT += " __";
									spGHICHU += " __";
									spNGAYGIAOHANG += " __";
									spTRAKMID += rs.getString("TRAKMID") + "__";

								}
								rs.close();
							}


							if (spMA.trim().length() > 0)
							{
								spMA = spMA.substring(0, spMA.length() - 2);
								spMa = spMA.split("__");

								spTEN = spTEN.substring(0, spTEN.length() - 2);
								spTen = spTEN.split("__");

								spDONVI = spDONVI.substring(0, spDONVI.length() - 2);
								spDonvi = spDONVI.split("__");
								
								spTONKHO = spTONKHO.substring(0, spTONKHO.length() - 2);
								spTonkho = spTONKHO.split("__");

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


								spSOLUONGTT = spSOLUONGTT.substring(0, spSOLUONGTT.length() - 2);
								spSoluongTT = spSOLUONGTT.split("__");

								spSCHEME = spSCHEME.substring(0, spSCHEME.length() - 2);
								spScheme = spSCHEME.split("__");

								spGHICHU = spGHICHU.substring(0, spGHICHU.length() - 2);
								spGhichu = spGHICHU.split("__");

								spNGAYGIAOHANG = spNGAYGIAOHANG.substring(0, spNGAYGIAOHANG.length() - 2);
								spNgaygiaohang = spNGAYGIAOHANG.split("__");

								spTRAKMID = spTRAKMID.substring(0, spTRAKMID.length() - 2);
								spTrakmId = spTRAKMID.split("__");

							}
							lsxBean.setSpMa(spMa);
							lsxBean.setSpTen(spTen);
							lsxBean.setSpDonvi(spDonvi);
							lsxBean.setSpTonkho(spTonkho);
							lsxBean.setSpSoluong(spSoluong);
							lsxBean.setSoluongtt(spSoluongTT);
							lsxBean.setSpChietkhau(spChietkhau);
							lsxBean.setSpGianhap(dongia);
							lsxBean.setSpScheme(spScheme);
							lsxBean.setSpVat(spvat);
							lsxBean.setSpGiagoc(spGiagoc);
							lsxBean.setSpBgId(spBgId);
							lsxBean.setSpGhichu(spGhichu);
							lsxBean.setSpNgaygiaohang(spNgaygiaohang);
							lsxBean.setSpGianhapCK(dongia);
							lsxBean.setSpTrakmId(spTrakmId);


							if (rs != null)
								rs.close();
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
				if(id == null)
				{  	
					lsxBean = new ErpDondathang("");
				}
				else
				{
					lsxBean = new ErpDondathang(id);
				}

				lsxBean.setUserId(userId);

				ngayyeucau = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaychuyen")));
				if(ngayyeucau == null || ngayyeucau.trim().length() <= 0)
					ngayyeucau = getDateTime();
				lsxBean.setNgayyeucau(ngayyeucau);
				session.setAttribute("ngaydonhang", lsxBean.getNgayyeucau());

				String ngaydenghi = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaydenghi")));
				if(ngaydenghi == null || ngaydenghi.trim().length() <= 0)
					ngaydenghi = "";
				lsxBean.setNgaydenghi(ngaydenghi);

				String ghichu = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ghichu")));
				if(ghichu == null)
					ghichu = "";
				lsxBean.setGhichu(ghichu);

				String khonhapId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khonhapId")));
				if (khonhapId == null)
					khonhapId = "";				
				lsxBean.setKhoNhapId(khonhapId);
				session.setAttribute("khoId", lsxBean.getKhoNhapId());

				String dvkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")));
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

				String vat = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ptVat")));
				if (vat == null)
					vat = "";				
				lsxBean.setVat(vat.replaceAll(",", ""));

				String txtCongNo = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("txtCongNo")));
				if (txtCongNo == null)
					txtCongNo = "";				
				lsxBean.setCongNo(txtCongNo.replaceAll(",", ""));

				loaidonhang = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaidonhang")));
				if (loaidonhang == null)
					loaidonhang = "";				
				lsxBean.setLoaidonhang(loaidonhang);	
				
				tiengiam = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("txtTiengiam")));
				if (tiengiam == null)
					tiengiam = "";				
				lsxBean.setTiengiam(tiengiam.replaceAll(",", ""));

				String[] schemeIds = request.getParameterValues("schemeIds");
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

				String [] isNppDat = request.getParameterValues("isNppDat");
				lsxBean.setIsNppDat(isNppDat);
				
				spMa = request.getParameterValues("spMa");
				lsxBean.setSpMa(spMa);

				spTen = request.getParameterValues("spTen");
				lsxBean.setSpTen(spTen);

				String[] dvt = request.getParameterValues("donvi");
				lsxBean.setSpDonvi(dvt);
				
				String[] tonkho = request.getParameterValues("spTonkho");
				lsxBean.setSpTonkho(tonkho);
				
				String[] soluongtoncn = request.getParameterValues("soluongtoncn");
				lsxBean.setSoluongtt(soluongtoncn);

				

				String[] soluong = request.getParameterValues("soluong");
				lsxBean.setSpSoluong(soluong);

				dongia = request.getParameterValues("dongia");
				lsxBean.setSpGianhap(dongia);

				String[] dongiack = request.getParameterValues("dongiack");
				lsxBean.setSpGianhapCK(dongiack);

				String[] spQuyDoi = request.getParameterValues("spQuyDoi");
				lsxBean.setSpQuyDoi(spQuyDoi);

				String[] spvat = request.getParameterValues("spvat");
				lsxBean.setSpVat(spvat);

				String[] spChietKhau = request.getParameterValues("spChietKhau");
				lsxBean.setSpChietkhau(spChietKhau);

				spGiagoc = request.getParameterValues("spGiagoc");
				lsxBean.setSpGiagoc(spGiagoc);

				spBgId = request.getParameterValues("spBgId");
				lsxBean.setSpBgId(spBgId);

				String[] soluongtt = request.getParameterValues("soluongtt");
				lsxBean.setSoluongtt(soluongtt);

				spGhichu = request.getParameterValues("spGhichu");
				lsxBean.setSpGhichu(spGhichu);

				spNgaygiaohang = request.getParameterValues("spNgaygiaohang");
				lsxBean.setSpNgaygiaohang(spNgaygiaohang);

				spTrakmId = request.getParameterValues("spTrakmId");
				lsxBean.setSpTrakmId(spTrakmId);

				pokhuyenmai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("pokhuyenmai")));
				if (pokhuyenmai == null)
					pokhuyenmai = "0";				
				lsxBean.setPOKhuyenMai(pokhuyenmai);
				
				if( loaidonhang.equals("1") || loaidonhang.equals("3")) //UNG HANG + TRUNG BAY
				{
					String[] spId = request.getParameterValues("spId");
					lsxBean.setSpId(spId);

					if(spId != null)
					{
						Hashtable<String, String> scheme_soluong = new Hashtable<String, String>();
						for(int i = 0; i < spId.length; i++ )
						{
							String[] spTraIds = request.getParameterValues(spId[i] + "_spIds");
							String[] soluongtra = request.getParameterValues(spId[i] + "_SoLuong");

							if(spTraIds != null)
							{
								for(int j = 0; j < spTraIds.length; j++ )
								{
									if(soluongtra[j] != null)
									{
										scheme_soluong.put(spId[i] + "__" + spTraIds[j], soluongtra[j].replaceAll(",", "") );
									}
								}
							}
						}

						lsxBean.setScheme_Soluong(scheme_soluong);
					}
				}
				else if(loaidonhang.equals("2"))
				{
					String[] spSchemeId = request.getParameterValues("spSchemeId");
					lsxBean.setSpSchemeIds(spSchemeId);

					String[] spId = request.getParameterValues("spId");
					lsxBean.setSpId(spId);

					spSoluong = request.getParameterValues("spSoluong");
					lsxBean.setSpSoluong(spSoluong);

					String[] spTongluong = request.getParameterValues("spTongluong");
					lsxBean.setSpTrongluong(spTongluong);

					String[] spDatra = request.getParameterValues("spDatra");
					lsxBean.setSpThetich(spDatra);
				}
				else if(loaidonhang.equals("0") || loaidonhang.equals("4") )
				{
					String[] chietkhau = request.getParameterValues("chietkhau");
					lsxBean.setSpChietkhau(chietkhau);

					String[] trongluong = request.getParameterValues("spTrongLuong");
					lsxBean.setSpTrongluong(trongluong);

					String[] thetich = request.getParameterValues("spTheTich");
					lsxBean.setSpThetich(thetich);

					spScheme = request.getParameterValues("scheme");
					lsxBean.setSpScheme(spScheme);

					//THEM CAC LOAI CHIET KHAU
					String[] dhCK_diengiai = request.getParameterValues("dhCK_diengiai");
					lsxBean.setDhck_Diengiai(dhCK_diengiai);
					String[] dhCK_giatri = request.getParameterValues("dhCK_giatri");
					lsxBean.setDhck_giatri(dhCK_giatri);
					String[] dhCK_loai = request.getParameterValues("dhCK_loai");
					lsxBean.setDhck_loai(dhCK_loai);

					String[] dhCK_chietkhau = request.getParameterValues("dhCK_chietkhau");
					lsxBean.setDhck_chietkhau(dhCK_chietkhau);

				}

				IErpDondathangList lsxBeanList = new ErpDondathangList();

				String tungaytk = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungaytk"));
				if(tungaytk == null)
					tungaytk = "";	    	   	    
				lsxBeanList.setTungay(tungaytk);

				String denngaytk = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngaytk"));
				if(denngaytk == null)
					denngaytk = "";	    	   	    
				lsxBeanList.setDenngay(denngaytk);

				String vungtk = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungtk"));
				if(vungtk == null)
					vungtk = "";	    	   	    
				lsxBeanList.setVungId(vungtk);

				String khuvuctk = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvuctk"));
				if(khuvuctk == null)
					khuvuctk = "";	    	   	    
				lsxBeanList.setKhuvucId(khuvuctk);

				String npptk = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("npptk"));
				if(npptk == null)
					npptk = "";	    	   	    
				lsxBeanList.setNppTen(npptk);

				String kbhtk = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kbhtk"));
				if(kbhtk == null)
					kbhtk = "";	    	   	    
				lsxBeanList.setKbhId(kbhtk);		    		   

				String trangthaitk = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthaitk"));		    
				if(trangthaitk == null)
					trangthaitk = "";	    	   	    
				lsxBeanList.setTrangthai(trangthaitk);

				session.setAttribute("lsxBeanList", lsxBeanList);

				action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));		    
			}

			if(action.equals("save"))
			{	
				if(id == null)
				{
					boolean kq = false;
					if(loaidonhang.equals("2"))
						kq = lsxBean.createKMTichLuy();
					else if(loaidonhang.equals("1"))
						kq = lsxBean.createKMUngHang();
					else if(loaidonhang.equals("3"))
						kq = lsxBean.createTrungBay();
					else
						kq = lsxBean.createNK();

					if(!kq)
					{
						lsxBean.createRs();
						lsxBean.createSP();
						session.setAttribute("khoId", lsxBean.getKhoNhapId());
						session.setAttribute("lsxBean", lsxBean);
						String nextJSP = "/AHF/pages/Center/ErpDonDatHangNew.jsp";
						if(loaidonhang.equals("4"))
							nextJSP = "/AHF/pages/Center/ErpDonDatHangKhacNew.jsp";
						else if(loaidonhang.equals("2"))
							nextJSP = "/AHF/pages/Center/ErpDonHangKMTichLuyNew.jsp";
						else if(loaidonhang.equals("1"))
							nextJSP = "/AHF/pages/Center/ErpDonHangKMUngHangNew.jsp";
						else if(loaidonhang.equals("3"))
							nextJSP = "/AHF/pages/Center/ErpDonHangTrungBayNew.jsp";

						response.sendRedirect(nextJSP);
					}
					else
					{
						IErpDondathangList obj = new ErpDondathangList();																	
						obj.setLoaidonhang(loaidonhang);						
						obj.setUserId(userId);
						obj.init("");  
						session.setAttribute("obj", obj);  	
						session.setAttribute("userId", userId);
						lsxBean.DBclose();
						String nextJSP = "/AHF/pages/Center/ErpDonDatHang.jsp";
						if(loaidonhang.equals("4"))
							nextJSP = "/AHF/pages/Center/ErpDonDatHangKhac.jsp";
						else if(loaidonhang.equals("2"))
							nextJSP = "/AHF/pages/Center/ErpDonHangKMTichLuy.jsp";
						else if(loaidonhang.equals("1"))
							nextJSP = "/AHF/pages/Center/ErpDonHangKMUngHang.jsp";
						else if(loaidonhang.equals("3"))
							nextJSP = "/AHF/pages/Center/ErpDonHangTrungBay.jsp";

						response.sendRedirect(nextJSP);
					}
				}
				else
				{
					boolean kq = false;
					if( loaidonhang.equals("2") )
						kq = lsxBean.updateKMTichLuy();
					else if(loaidonhang.equals("1"))
						kq = lsxBean.createKMUngHang();
					else if(loaidonhang.equals("3"))
						kq = lsxBean.updateTrungBay();
					else
						kq = lsxBean.updateNK("1");

					if(!kq)
					{
						lsxBean.createRs();
						lsxBean.createSP();
						session.setAttribute("khoId", lsxBean.getKhoNhapId());
						session.setAttribute("lsxBean", lsxBean);
						String nextJSP = "/AHF/pages/Center/ErpDonDatHangUpdate.jsp";
						if(loaidonhang.equals("4"))
							nextJSP = "/AHF/pages/Center/ErpDonDatHangKhacUpdate.jsp";
						else if(loaidonhang.equals("2"))
							nextJSP = "/AHF/pages/Center/ErpDonHangKMTichLuyUpdate.jsp";
						else if(loaidonhang.equals("1"))
							nextJSP = "/AHF/pages/Center/ErpDonHangKMUngHangUpdate.jsp";
						else if(loaidonhang.equals("3"))
							nextJSP = "/AHF/pages/Center/ErpDonHangTrungBayUpdate.jsp";

						response.sendRedirect(nextJSP);
					}
					else
					{
						IErpDondathangList obj = new ErpDondathangList();																
						obj.setLoaidonhang(loaidonhang);						
						obj.setUserId(userId);					    					   

						//String search = getSearchQuery(request, obj);
						obj.init("");
						lsxBean.DBclose();
						session.setAttribute("obj", obj);
						String nextJSP = "/AHF/pages/Center/ErpDonDatHang.jsp";
						if(loaidonhang.equals("4"))
							nextJSP = "/AHF/pages/Center/ErpDonDatHangKhac.jsp";
						else if(loaidonhang.equals("2"))
							nextJSP = "/AHF/pages/Center/ErpDonHangKMTichLuy.jsp";
						else if(loaidonhang.equals("1"))
							nextJSP = "/AHF/pages/Center/ErpDonHangKMUngHang.jsp";
						else if(loaidonhang.equals("3"))
							nextJSP = "/AHF/pages/Center/ErpDonHangTrungBay.jsp";

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
						boolean tao = lsxBean.createNK();
						if (!tao)
						{
							lsxBean.createRs();
							session.setAttribute("lsxBean", lsxBean);
							session.setAttribute("khoId", lsxBean.getKhoNhapId());
							String nextJSP = "/AHF/pages/Center/ErpDonDatHangNew.jsp";

							response.sendRedirect(nextJSP);
							return;
						}
						else
						{
							id = lsxBean.getId();		
						}						
					}
					else
					{
						boolean temp = lsxBean.updateNK("0");

						if (!temp)
						{
							lsxBean.createRs();
							session.setAttribute("lsxBean", lsxBean);
							session.setAttribute("khoId", lsxBean.getKhoNhapId());
							String nextJSP = "/AHF/pages/Center/ErpDonDatHangUpdate.jsp";

							response.sendRedirect(nextJSP);
							return;
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

					if(id.trim().length() > 0)
					{	
						//INIT SP VOI QUY CACH NEU TRUONG HOP KHONG PHAI LA DV CHUAN
						dbutils db = new dbutils();

						//XOA HET KM CU NEU CO
						String query = " delete ERP_DONDATHANG_CTKM_TRAKM where DONDATHANGID = '" + id + "' ";
						db.update(query);

						///gia tinh km sau thue kenh GT, gia k thue kenh MT
						query = "  select ( select ma from SANPHAM where PK_SEQ = a.sanpham_fk ) as spMA, a.dvdl_fk, b.DVDL_FK as dvCHUAN,  " +
								" 		case when a.dvdl_fk = b.DVDL_FK then a.soluongTTDUYET  " +
								"  			 else  a.soluongTTDUYET * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and DVDL1_FK = b.DVDL_FK)   end as soluong, " +
								"  		case when a.dvdl_fk = b.DVDL_FK then a.dongia " +
								"  			 else  a.dongia / ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and DVDL1_FK = b.DVDL_FK)   end as dongia,  " +
								"  		isnull( ( select SOLUONG1 from QUYCACH where dvdl1_fk=b.dvdl_fk and sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and DVDL1_FK = b.DVDL_FK),1)  as quycach    " +
								"  from ERP_DONDATHANG_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ    " +
								"  where a.dondathang_fk = '" + id + "'  " ;
						System.out.println("---LAY QUY CACH: " + query);
						ResultSet rsSP = db.get(query);

						/*if(rsSP != null)*/
						{
							try 
							{
								while(rsSP.next())
								{
									float dongiakm;
									dongiakm = rsSP.getFloat("dongia") / 1.1f;
									
									System.out.println("dongia km "+dongiakm);
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
							catch (Exception e) {}	
						}

						db.shutDown();
					}


					lsxBean.ApChietKhau();


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
						System.out.println("Bi dung san pham roi...\n");
						session.setAttribute("xlkm", xlkm);
						String nextJSP = "/AHF/pages/Center/KhuyenMaiTT.jsp";
						response.sendRedirect(nextJSP);
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

						xlkm.DBclose();
						lsxBean.setMsg(msg);
						session.setAttribute("lsxBean", lsxBean);
						String nextJSP = "/AHF/pages/Center/ErpDonDatHangUpdate.jsp";
						response.sendRedirect(nextJSP);
						return;
					}

					String nextJSP = "";

					if(ctkmResual.size() > 0)
					{
						session.setAttribute("xlkm", xlkm);
						nextJSP = "/AHF/pages/Center/KhuyenMaiTT.jsp";
						response.sendRedirect(nextJSP);
					}
					else
					{	
						System.out.println("Vo day ne!!!!");
						xlkm.DBclose();
						lsxBean.init();

						session.setAttribute("lsxBean", lsxBean);
						nextJSP = "/AHF/pages/Center/ErpDonDatHangUpdate.jsp";
						response.sendRedirect(nextJSP);
						return;
					}
				}
				else
				{
					lsxBean.createRs();
					/*lsxBean.createSP();*/
					session.setAttribute("dvkdId", lsxBean.getDvkdId());
					session.setAttribute("kbhId", lsxBean.getKbhId());
					session.setAttribute("nppId", lsxBean.getNppId());
					session.setAttribute("khoId", lsxBean.getKhoNhapId());
					session.setAttribute("lsxBean", lsxBean);

					String nextJSP = "";

					nextJSP = "/AHF/pages/Center/ErpDonDatHangNew.jsp";
					if(loaidonhang.equals("4"))
						nextJSP = "/AHF/pages/Center/ErpDonDatHangKhacNew.jsp";
					else if(loaidonhang.equals("2"))
						nextJSP = "/AHF/pages/Center/ErpDonHangKMTichLuyNew.jsp";
					else if(loaidonhang.equals("1"))
						nextJSP = "/AHF/pages/Center/ErpDonHangKMUngHangNew.jsp";
					else if(loaidonhang.equals("3"))
						nextJSP = "/AHF/pages/Center/ErpDonHangTrungBayNew.jsp";

					if(id != null)
					{
						nextJSP = "/AHF/pages/Center/ErpDonDatHangUpdate.jsp";
						if(loaidonhang.equals("4"))
							nextJSP = "/AHF/pages/Center/ErpDonDatHangKhacUpdate.jsp";
						else if(loaidonhang.equals("2"))
							nextJSP = "/AHF/pages/Center/ErpDonHangKMTichLuyUpdate.jsp";
						else if(loaidonhang.equals("1"))
							nextJSP = "/AHF/pages/Center/ErpDonHangKMUngHangUpdate.jsp";
						else if(loaidonhang.equals("3"))
							nextJSP = "/AHF/pages/Center/ErpDonHangTrungBayUpdate.jsp";
					}

					response.sendRedirect(nextJSP);
				}
			}
		}
	}

	String getValue(Sheet sheet,int col,int row)
	{
		return sheet.getCell(col,row).getContents().trim().replaceAll(",", "");
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
				System.out.println("Type - Hinh thuc " + trakm.getType() + " - " + trakm.getHinhthuc());
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
				String msg = CheckNghanSach(ctkm.getId(),nppId,"0","",db);
				if(msg.trim().length() > 0)
				{
					db.getConnection().rollback();
					return msg;
				}
				/*********************************************************************************/

				if(trakm.getType() == 3) //san pham co so luong co dinh
				{
					if(trakm.getHinhthuc() == 1)
					{

						String sql = "select f.pk_seq as spId, a.soluong, e.GIAMUANPP as dongia, f.ma as spMa  " +
								"from Trakhuyenmai_sanpham a inner join SANPHAM f on a.SANPHAM_FK = f.PK_SEQ " +
								"	inner join BGMUANPP_SANPHAM e on a.sanpham_fk = e.SANPHAM_FK " +
								"where a.TRAKHUYENMAI_FK = '" + trakm.getId() + "' " +
								"and e.BGMUANPP_FK in ( select top(1) a.PK_SEQ " +
								"from BANGGIAMUANPP a inner join BANGGIAMUANPP_NPP b on a.PK_SEQ = b.BANGGIAMUANPP_FK  " +
								"where a.TUNGAY <= '" + tungay + "' and b.NPP_FK = '" + nppId + "' and a.KENH_FK = ( select KBH_FK from ERP_DONDATHANG where PK_SEQ = '" + id + "' ) and a.DVKD_FK = f.DVKD_FK " +
								"order by a.TUNGAY desc  ) -- and e.GIAMUANPP > 0  and e.trangthai = '1'  ";

						//System.out.println("Query lay gia san pham co dinh la: " + sql + "\n");

						int index = 0;
						ResultSet rsSQl = db.get(sql);
						if(rsSQl != null)
						{
							while(rsSQl.next())
							{
								int slg = Integer.parseInt(rsSQl.getString("soluong")) * (int)ctkm.getSoxuatKM();
								long tonggtri = Math.round(slg * rsSQl.getFloat("dongia"));
								//luu tong gia tri o moi dong sanpham
								//String query = "Insert into ERP_DONDATHANG_CTKM_TRAKM(DONDATHANGID, ctkmId, trakmId, soxuat, tonggiatri, spMa, soluong) values('" + id + "','" + ctkm.getId() + "','" + trakm.getId() + "','" + ctkm.getSoxuatKM() + "','" + Long.toString(tongtienTraKM) + "', '" + rsSQl.getString("spMa").trim() + "', '" + Integer.toString(slg) + "')";
								String query =  "\n Insert into ERP_DONDATHANG_CTKM_TRAKM(DONDATHANGID, ctkmId, trakmId, soxuat, tonggiatri,Sanpham_fk, spMa, soluong)" +
								"\n select '" + id + "','" + ctkm.getId() + "','" + trakm.getId() + "','" + ctkm.getSoxuatKM() + "','" + Long.toString(tonggtri) + "'," + rsSQl.getString("spId").trim() + " ,'" + rsSQl.getString("spMa").trim() + "', '" + Integer.toString(slg) + "' " ;
				
								System.out.println("1.Chen khuyen mai co dinh: " + query);

								if(!db.update(query))
								{
									db.getConnection().rollback();
									str = query;
									return str;
								}

								 msg = CheckNghanSach(ctkm.getId(),nppId,"0","",db);
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
									"										where a.TUNGAY <= '" + tungay + "' and b.NPP_FK = '" + nppId + "' and a.KENH_FK = ( select kbh_fk from ERP_DONDATHANG where pk_seq='" + id + "' ) and a.DVKD_FK = ( select dvkd_fk from ERP_DONDATHANG where pk_seq='" + id + "' ) " +
									"										order by a.TUNGAY desc  )   " +
									") bgmnpp on bgmnpp.sanpham_fk=a.sanpham_fk" + 
									" where a.ctkm_fk = '" + ctkm.getId() + "' and a.npp_fk = '" + nppId + "' and a.trakm_fk = '" + trakm.getId() + "' " +
									"order by a.thutuuutien asc";

							System.out.println("5.Query tinh gia km npp chon truoc: " + query);

							ResultSet spkm = db.get(query);

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
								String slg = arr[1].replaceAll(",", "");
								if(ctkm.getPhanbotheoDH().equals("1"))
								{
									 msg = CheckNghanSach(ctkm.getId(), nppId, arr[1].replaceAll(",", ""), "1", db);
									if(msg.trim().length() > 0 && !msg.contains(":"))
										slg = msg;
									else
									{
										db.getConnection().rollback();
										return msg;
									}
								}
								/*********************************************************************************/

								//luu tong gia tri o moi dong sanpham
								query = " Insert into ERP_DONDATHANG_CTKM_TRAKM(DONDATHANGID, ctkmId, trakmId, soxuat, tonggiatri,sanpham_fk, spMa, soluong) "+
								" values('" + id + "','" + ctkm.getId() + "','" + trakm.getId() + "','" + ctkm.getSoxuatKM() + "','" + arr[2].replaceAll(",", "") + "', '" + arr[0] + "', '" + arr[3] + "', '" + arr[1].replaceAll(",", "") + "')";
								System.out.println("6.Chen khuyen mai Npp chon truoc: " + query);

								if(!db.update(query))
								{
									db.getConnection().rollback();
									str = query;
									return str;
								}

								 msg = CheckNghanSach(ctkm.getId(),nppId,"0","",db);
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
						String query = "Insert into ERP_DONDATHANG_CTKM_TRAKM(DONDATHANGID, ctkmId, trakmId, soxuat, tonggiatri) values('" + id + "','" + ctkm.getId() + "','" + trakm.getId() + "','" + ctkm.getSoxuatKM() + "','" + tongtienTraKM + "')";
						System.out.println("10.Chen khuyen mai tien / ck: " + query);
						if(!db.update(query))
						{
							db.getConnection().rollback();
							str = query;
							return str;
						}

						 msg = CheckNghanSach(ctkm.getId(),nppId,"0","",db);
						if(msg.trim().length() > 0)
						{
							db.getConnection().rollback();
							return msg;
						}
					}
				}
			}
			
			String msgUpdate = geso.dms.distributor.util.Utility.Update_GiaTri_ERP_DonDatHang(id, db,true);			
			if(msgUpdate.trim().length() > 0)
			{	db.getConnection().rollback();
				return msgUpdate;
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
						"where npp_fk = '" + nppId + "' and ctkm_fk = '" + ctkmId + "'  ";

		System.out.println("1.Cau lenh check ngan sach: " + sql + " --- Ngan sach de check: " + ngansach);

		ResultSet rs = db.get(sql);
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
			System.out.println("---NGAN SACH ( SOXUAT / TONG GIA TRI ): " + NganSach + " -- CON LAI: " + DaSuDung);
			if(DaSuDung > NganSach)
			{
				return "1.Chương trình khuyến mại: " + scheme + ", đã hết ngân sách phân bổ";
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
