package geso.dms.center.servlets.donhangip;

import geso.dms.center.beans.donhangip.IDonhangIP;
import geso.dms.center.beans.donhangip.IDonhangListIP;
import geso.dms.center.beans.donhangip.ISanphamIP;
import geso.dms.center.beans.donhangip.imp.DonhangIP;
import geso.dms.center.beans.donhangip.imp.DonhangListIP;
import geso.dms.center.beans.donhangip.imp.SanphamIP;
import geso.dms.center.util.OracleConnUtils;
import geso.dms.distributor.beans.ctkhuyenmai.imp.XLkhuyenmai;
import geso.dms.distributor.beans.ctkhuyenmai.imp.XLkhuyenmaiDonhangDXK;
import geso.dms.distributor.beans.ctkhuyenmai.ICtkhuyenmai;
import geso.dms.distributor.beans.donhang.imp.XLTrungbay;
import geso.dms.distributor.beans.trakhuyenmai.ITrakhuyenmai;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.FixData;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DonhangIPUpdateSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet
{
	private static final long serialVersionUID = 1L;

	PrintWriter out;

	public DonhangIPUpdateSvl(){
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
		else{
			session.setMaxInactiveInterval(30000);

			this.out = response.getWriter();
			Utility util = new Utility();

			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);

			out.println(userId);

			if (userId.length()==0)
				userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

			String id = util.getId(querystring);  	
			IDonhangIP dhBean = new DonhangIP(id);
			dhBean.setUserId(userId); //phai co UserId truoc khi 
			dhBean.init();

			dhBean.setKhId(dhBean.getKhId());
			session.setAttribute("bgstId", dhBean.getBgstId());
			session.setAttribute("khoId", dhBean.getKhoId());
			String nextJSP;

			if(request.getQueryString().indexOf("display") >= 0 ) 
			{
				//dhBean.CreateSpDisplay();
				nextJSP = "/AHF/pages/Center/DonHangIPDisplay.jsp";
			}
			else
			{
				nextJSP = "/AHF/pages/Center/DonHangIPUpdate.jsp";
			}

			session.setAttribute("dhBean", dhBean);
			session.setAttribute("khId", dhBean.getKhId());
			session.setAttribute("ddkdId", dhBean.getDdkdId());
			session.setAttribute("ngaydonhang", dhBean.getNgaygiaodich() );
			//bo sung them de do phai truy xuat csdl khi nhan sanpham
			session.setAttribute("nppId", dhBean.getNppId());

			response.sendRedirect(nextJSP);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen"); 

		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			session.setMaxInactiveInterval(30000);

			this.out = response.getWriter();
			dbutils db = new dbutils();
			IDonhangIP dhBean;
			Utility util = new Utility();
			String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
			if(id == null)
			{  	
				dhBean = new DonhangIP("");
			}
			else
			{
				dhBean = new DonhangIP(id);
			}

			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
			dhBean.setUserId(userId);

			String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
			if (nppId == null)
				nppId = "";
			dhBean.setNppId(nppId);

			String gsbhId =  util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("gsbhId")));
			if(gsbhId == null)
				gsbhId ="0";
			dhBean.setGsbhId(gsbhId);

			String ngaygd = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
			if (ngaygd == null || ngaygd == "")
				ngaygd = this.getDateTime();				
			dhBean.setNgaygiaodich(ngaygd);

			session.setAttribute("ngaydonhang", ngaygd );

			String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));
			if (trangthai == null)
				trangthai = "";				
			dhBean.setTrangthai(trangthai);

			String diachigiaohang = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diachigiao")));
			if (diachigiaohang == null)
				diachigiaohang = "";				
			dhBean.setDiachigiaohang(diachigiaohang);

			String ddkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdTen")));
			if (ddkdId == null)
				ddkdId = "";    	
			dhBean.setDdkdId(ddkdId);
			session.setAttribute("ddkdId", ddkdId );

			String khoId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khoTen")));
			if (khoId == null)
				khoId = "100000";    	
			dhBean.setKhoId(khoId);
			
			String ngaygiao = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaygiao")));
			if (ngaygiao == null)
				ngaygiao = "";				
			dhBean.setNgaygiao(ngaygiao);
			

			String khId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khId")));
			if (khId == null)
				khId = "";    	
			//System.out.println("Khach hang :"+khId);

			dhBean.setKhId(khId);
			session.setAttribute("khId",khId);
			//System.out.println("DonhangUpdateSvl user :" + userId + " ,khachhangId :"+ khId +"  ,sessionId: " + session.getId() );

			String smartId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("smartId")));
			if (smartId == null)
				smartId = "";    	
			dhBean.setSmartId(smartId);

			String khTen = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khTen")));
			if (khTen == null)
				khTen = "";    	
			dhBean.setKhTen(khTen);
			String nvgnId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nvgnId")));
			if (nvgnId == null)
				nvgnId = "";    	
			dhBean.setnvgnId(nvgnId);
			
			/*	System.out.println("11111111111111111.KHACH HANG TEN: " + khTen);*/

			String muctindung = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("muctindung")));
			if (muctindung ==null)
				muctindung = "0";    	
			dhBean.setMuctindung(muctindung);    	

			String chietkhau = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ChietKhau")));
			if (chietkhau == null)
				chietkhau = ""; 
			else
				chietkhau = chietkhau.replaceAll(",", "");
			dhBean.setChietkhau(chietkhau);

			String tongtientruocVAT = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("SoTienChuaVAT")));
			if(tongtientruocVAT == null)
				tongtientruocVAT = "0";
			else
				tongtientruocVAT = tongtientruocVAT.replaceAll(",", "");
			dhBean.setTongtientruocVAT(tongtientruocVAT);   //tien chi gom soluong * dongia --> de ap khuyen mai

			String tongtiensauVAT = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("SoTienCoVAT")));
			if(tongtiensauVAT == null)
				tongtiensauVAT = "0";
			else
				tongtiensauVAT = tongtiensauVAT.replaceAll(",", "");
			dhBean.setTongtiensauVAT(tongtiensauVAT);   

			String tongtienDonhang = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("SoTienSauCKKM")));
			if(tongtienDonhang == null)
				tongtienDonhang = tongtiensauVAT;
			else
				tongtienDonhang = tongtienDonhang.replaceAll(",", "");
			dhBean.setTongtiensauCKKM(Float.parseFloat(tongtienDonhang));
			/*System.out.println("Tong gia tri don hang ben SVL: " + tongtienDonhang + "\n");*/
			/// Lấy theo chiết khấu npp tự điền
			String TienChietKhau = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ChietKhau")));
			if(TienChietKhau == null)
				TienChietKhau = "0";
			else
				TienChietKhau = TienChietKhau.replaceAll(",", "");
			dhBean.setTongChietKhau(TienChietKhau);  

			String VAT = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("VAT")));
			if (VAT == null)
				VAT = "0";  //TTC, don gia da co VAT
			else
				VAT = VAT.replaceAll(",", "");
			dhBean.setVAT(VAT);

			String bgstId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("bgstId")));
			if (bgstId == null)
				bgstId = "0";   //neu khach hang khong co bang gia sieu thi 
			/*System.out.println("bang gia sieu thi:" + bgstId );*/
			dhBean.setBgstId(bgstId);
			session.setAttribute("bgstId", bgstId);

			String ngayks = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaykhoaso")).trim());
			if(ngayks == null)
				ngayks = getDateTime();
			System.out.println("Ngay KS: "+ngayks);
			dhBean.setNgayKs(ngayks);


			String ghichu = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ghichu")));
			if (ghichu == null || ghichu.length() == 0)
				ghichu = "NA";
			dhBean.setGhiChu(ghichu);

			String nocu =  util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("NoCu")));
			if(nocu == null)
				nocu ="0";
			dhBean.setNoCu(Double.parseDouble(nocu.replaceAll(",", "")));
			
			String sotiengiam = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sotiengiam")));
			if (sotiengiam == null)
				sotiengiam = "0";    	
			dhBean.setSotiengiam(sotiengiam.replaceAll(",", ""));


			String ngaysua = getDateTime();
			dhBean.setNgaysua(ngaysua);

			String[] masp = request.getParameterValues("masp");
			String[] tensp = request.getParameterValues("tensp");
			String[] soluong = request.getParameterValues("soluong");
			String[] donvitinh = request.getParameterValues("donvitinh");
			String[] dongia = request.getParameterValues("dongia");
			String[] spchietkhau = request.getParameterValues("spchietkhau");
			String[] spchietkhauDLN = request.getParameterValues("spchietkhauDLN");
			String[] spchietkhauTT = request.getParameterValues("spchietkhauTT");
			String[] spchietkhauDH = request.getParameterValues("spchietkhauDH");
			String[] tonkho = request.getParameterValues("tonkho");

			String[] spGiagoc = request.getParameterValues("spGiagoc");
			String[] spBgId = request.getParameterValues("spBgId");

			long tonggiatriKm = Math.round(Float.parseFloat(tongtientruocVAT));

			List<ISanphamIP>	spList = new ArrayList<ISanphamIP>();
			if(masp != null)
			{		
				ISanphamIP sanpham = null;
				String[] param = new String[12];
				int m = 0;
				while(m < masp.length)
				{
					if(masp[m] != "")
					{
						if(soluong[m].length() >  0) //chi them nhung san pham co so luong > 0
						{
							param[0] = "idSP";
							param[1] = masp[m];
							param[2] = tensp[m];
							param[3] = soluong[m].replaceAll(",", "");
							param[4] = donvitinh[m];
							param[5] = dongia[m].replaceAll(",", "");
							param[6] = ""; //khuyen mai
							param[7] = spchietkhau[m].replaceAll(",", "");
							param[8] = spchietkhauDLN[m].replaceAll(",", "");
							param[9] = spchietkhauTT[m].replaceAll(",", "");
							param[10] = spchietkhauDH[m].replaceAll(",", "");
							System.out.println("DonGia: "+dongia[m].replaceAll(",", ""));
							sanpham = new SanphamIP(param);
							sanpham.setTonhientai(tonkho[m].replaceAll(",", ""));
							sanpham.setGiagoc(spGiagoc[m].replaceAll(",", ""));
							sanpham.setBgId(spBgId[m]);
							spList.add(sanpham);
						}
					}
					m++;
				}	
			}
			Hashtable<String, Integer> spThieuList = new Hashtable<String, Integer>();
		

			String hinhthuc = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("hinhthuc"));
			if(hinhthuc == null)
				hinhthuc = "0";

			String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
			System.out.println("__Action la: " + action);

			if(action.equals("submitKh"))
			{

				String sql = "";
				if(khTen.trim().length() < 0)
				{
					sql = "select top(1) substring(a.smartid, charindex('_', a.smartid)+1, len(a.smartid)) as smartId, a.pk_seq as khId, a.ten as khTen, '' as ckId, '' as bgstId, a.ddkd_fkip ddkd_fk, dd.gsbh_fk gsbh_fk, a.diachi ";
					sql += "from khachhang a left join ddkd_gsbh dd on dd.ddkd_fk = ddkd_fkip ";
					sql += "where a.trangthai = '1'  and ( a.smartId = '" + smartId + "'  or a.ten like N'%" + smartId + "%')  order by a.smartId asc";
				}
				else
				{
					sql = "select top(1) substring(a.smartid, charindex('_', a.smartid)+1, len(a.smartid)) as smartId, a.pk_seq as khId, a.ten as khTen, '' as ckId, '' as bgstId, a.ddkd_fkip ddkd_fk, '' ,dd.gsbh_fk gsbh_fk, a.diachi,'' as nvgn ";
					sql += "from khachhang a  left join ddkd_gsbh dd on dd.ddkd_fk = ddkd_fkip ";
					sql += "where a.trangthai = '1'  and ( a.ten like N'%" + khTen.split(" - ")[0] + "%' and a.smartId = '" + smartId + "' ) ";
				}
				System.out.println("____Lay khach hang: " + sql);

				ResultSet rs = db.get(sql);
				if(rs != null)
				{
					try 
					{
						if(rs.next())
						{	
							dhBean.setSmartId(rs.getString("smartId"));	

							dhBean.setKhId(rs.getString("khId"));
							dhBean.setnvgnId(rs.getString("nvgn"));
							session.setAttribute("khId", rs.getString("khId"));

							dhBean.setKhTen(rs.getString("khTen") + " - " + rs.getString("diachi"));

							if(rs.getString("ckId") != null)
								dhBean.setChietkhau(rs.getString("ckId"));

							dhBean.setDdkdId(rs.getString("ddkd_fk"));
							session.setAttribute("ddkdId", rs.getString("ddkd_fk"));

							dhBean.setGsbhId(rs.getString("gsbh_fk"));

						
							if(id == null)
							{
								dhBean.createRS();
								dhBean.setSpList(spList);
								session.setAttribute("dhBean", dhBean);
								String nextJSP = "/AHF/pages/Center/DonHangIPNew.jsp";
								response.sendRedirect(nextJSP);
							}
							else
							{
								dhBean.init();
								dhBean.setSpList(spList);
								dhBean.setSmartId(rs.getString("smartId"));	
								dhBean.setKhId(rs.getString("khId"));
								dhBean.setKhTen(rs.getString("khTen") + " - " + rs.getString("diachi"));
								if(rs.getString("ckId") != null)
									dhBean.setChietkhau(rs.getString("ckId"));

								dhBean.setDdkdId(rs.getString("ddkd_fk"));
								dhBean.setGsbhId(rs.getString("gsbh_fk"));

								session.setAttribute("dhBean", dhBean);
								String nextJSP = "/AHF/pages/Center/DonHangIPUpdate.jsp";
								response.sendRedirect(nextJSP);
							}
							
							
							
							try
							{
								
								OracleConnUtils SYNC = new OracleConnUtils();
						
								
									String query = "select debit as nophaithu from apps.SGP_CUS_DEBIT where customer_number = '"+rs.getString("smartId")+"' and BLINE_CODE='01'";
									ResultSet congnoRs = null;
									congnoRs = SYNC.get(query);
									if(congnoRs != null && congnoRs.next())
									{
										dhBean.setNoCu( congnoRs.getDouble("nophaithu"));
									}
								
							}
							catch(Exception e)
							{
								e.printStackTrace();
							}
						}
						else
						{
							if( id == null)
							{
								dhBean.createRS();
								dhBean.setSpList(spList);
								dhBean.setMessage("Mã khách hàng không đúng, hoặc khách hàng chưa được phân tuyến vui lòng kiểm tra lại...");
								session.setAttribute("dhBean", dhBean);
								String nextJSP = "/AHF/pages/Center/DonHangIPNew.jsp";
								response.sendRedirect(nextJSP);
							}
							else
							{
								dhBean.init();
								dhBean.setSpList(spList);

								dhBean.setMessage("Mã khách hàng không đúng, hoặc khách hàng chưa được phân tuyến vui lòng kiểm tra lại...");
								session.setAttribute("dhBean", dhBean);
								String nextJSP = "/AHF/pages/Center/DonHangIPUpdate.jsp";
								response.sendRedirect(nextJSP);
							}
						}
						if(rs!=null){
							rs.close();
						}
					} 
					catch (Exception e) {
						e.printStackTrace();
					};				
				}
			}
			else
			{
				if(action.equals("save"))
				{
					if(id == null)
					{
						boolean tao = dhBean.CreateDh(spList);
						String dhId = dhBean.getId();
						if (!tao)
						{							
							dhBean.createRS();
							dhBean.setSpList(spList);
							session.setAttribute("dhBean", dhBean);
							String nextJSP = "/AHF/pages/Center/DonHangIPNew.jsp";
							response.sendRedirect(nextJSP);
						}
						else
						{

							dhBean = (IDonhangIP) new DonhangIP("");
							dhBean.setUserId(userId);
							dhBean.createRS();
							dhBean.setNgaygiaodich(ngaygd);
							dhBean.setMessage("Số đơn hàng bạn vừa lưu " + dhId);

							// Save Data into session
							session.setAttribute("dhBean", dhBean);//truyen vao session mot doi tuong donhang co gia tri rong khi khoi tao de ben form don hang nhan dc
							session.setAttribute("khId", "");
							session.setAttribute("ddkdId", "");
							session.setAttribute("nppId", dhBean.getNppId());

							String nextJSP = "/AHF/pages/Center/DonHangIPNew.jsp";
							response.sendRedirect(nextJSP);
						}
					}
					else
					{
						boolean temp = false;
						temp = dhBean.UpdateDh(spList, action);

						if (temp == false)
						{
							dhBean.init();
							dhBean.setSpList(spList);
							dhBean.setKhId(khId);
							dhBean.setDdkdId(ddkdId);
							session.setAttribute("dhBean", dhBean);
							String nextJSP = "/AHF/pages/Center/DonHangIPUpdate.jsp";
							response.sendRedirect(nextJSP);
							return;
						}
						else
						{

							dhBean = (IDonhangIP) new DonhangIP("");
							dhBean.setUserId(userId);
							dhBean.createRS();
							dhBean.setNgaygiaodich(ngaygd);

							// Save Data into session
							session.setAttribute("dhBean", dhBean);//truyen vao session mot doi tuong donhang co gia tri rong khi khoi tao de ben form don hang nhan dc
							session.setAttribute("khId", "");
							session.setAttribute("ddkdId", "");
							session.setAttribute("nppId", dhBean.getNppId());
							dhBean.setMessage("Số đơn hàng bạn vừa lưu " + id);

							String nextJSP = "/AHF/pages/Center/DonHangIPNew.jsp";
							response.sendRedirect(nextJSP);
						}					
					}
				}
				else
				{
					if(action.equals("chotdonhang"))
					{
						
					
							if (!(dhBean.ChotDh(spList)))
							{
								dhBean.init();
								dhBean.setSpList(spList);
								dhBean.setDdkdId(ddkdId);
								dhBean.setKhId(khId);
								session.setAttribute("dhBean", dhBean);
								String nextJSP = "/AHF/pages/Center/DonHangIPUpdate.jsp";
								response.sendRedirect(nextJSP);
								return;
							}
							else
							{
								IDonhangListIP  obj = new DonhangListIP();
								obj.setUserId(userId);
								obj.init("");
								session.setAttribute("obj", obj);
								session.setAttribute("ddkdId", "");

								String nextJSP = "/AHF/pages/Center/DonHangIP.jsp";
								response.sendRedirect(nextJSP);	
								return;
							}
						
					}	
					else  //submit
						{
							dhBean.setUserId(userId);
							dhBean.createRS();
							System.out.println("GSBHID: "+dhBean.getGsbhId());
							String nextJSP = "/AHF/pages/Center/DonHangIPNew.jsp";
							if(id != null)
							{
								dhBean.CreateSpList();
								List<ISanphamIP> listSp = dhBean.getSpList();

								//cap nhat gia cac san pham
								for(int i = 0; i < spList.size(); i++)
								{
									ISanphamIP sp = spList.get(i);
									for(int j = 0; j < listSp.size(); j++)
									{
										ISanphamIP sp2 = listSp.get(j);
										if(sp.getMasanpham().trim().equals(sp2.getMasanpham().trim()))
											spList.get(j).setDongia(sp2.getDongia());
									}
								}
								nextJSP = "/AHF/pages/Center/DonHangIPUpdate.jsp";						
							}									
							dhBean.setSpList(spList);
							session.setAttribute("ddkdId", ddkdId );
							session.setAttribute("dhBean", dhBean);
							response.sendRedirect(nextJSP);				
						}
					}
				}
				db.shutDown();
			}
		}
	

	

	



	private String getDateTime() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);	
	}

	private String getDateTimeNow()
	{
		String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		String name = sdf.format(cal.getTime());
		return name;
	}

	


}
