package geso.dms.distributor.servlets.donhang;

import geso.dms.center.beans.dieuchuyenkhuyenmai.IDieuchuyenkhuyenmai;
import geso.dms.center.db.sql.Idbutils;
import geso.dms.distributor.beans.ctkhuyenmai.imp.XLkhuyenmai;
import geso.dms.distributor.beans.ctkhuyenmai.imp.XLkhuyenmaiDonhangDXK;
import geso.dms.distributor.beans.ctkhuyenmai.ICtkhuyenmai;
import geso.dms.distributor.beans.dieukienkhuyenmai.IDieukienkhuyenmai;
import geso.dms.distributor.beans.donhang.*;
import geso.dms.distributor.beans.donhang.imp.*;
import geso.dms.distributor.beans.trakhuyenmai.ITrakhuyenmai;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.FixData;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class DonhangUpdateSvl extends javax.servlet.http.HttpServlet implements
		javax.servlet.Servlet {
	private static final long serialVersionUID = 1L;

	PrintWriter out;

	public DonhangUpdateSvl() {
		super();
	}

	protected void doGet(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		
		/* --- LUU GIA TRI BO LOC -- */
		session.setAttribute("tn", (String) request.getParameter("tn"));
		session.setAttribute("dn", (String) request.getParameter("dn"));
		session.setAttribute("tumuc", (String) request.getParameter("tumuc"));
		session.setAttribute("denmuc", (String) request.getParameter("denmuc"));
		session.setAttribute("nvbhid", (String) request.getParameter("nvbhid"));
		session.setAttribute("sodh", (String) request.getParameter("sodh"));
		session.setAttribute("makh", (String) request.getParameter("makh"));
		session.setAttribute("tthai", (String) request.getParameter("tthai"));
		session.setAttribute("tthaiin", (String) request.getParameter("tthaiin"));
		/* --- LUU GIA TRI BO LOC -- */
		
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session
				.getAttribute("util");
		if (!cutil.check(userId, userTen, sum)) {
			response.sendRedirect("/AHF/index.jsp");
		} else {
			session.setMaxInactiveInterval(30000);

			this.out = response.getWriter();
			Utility util = new Utility();
			
			String ssUserId = (String)session.getAttribute("userId");
			String view = Utility.antiSQLInspection(request.getParameter("view"));
			if (view == null) {
				view = "";
			}
			
			String param = "";
			if (view.trim().length() > 0) param ="&view="+view;
			if (Utility.CheckSessionUser( session,  request, response))
			{
				Utility.RedireactLogin(session, request, response);
				return;
			}
			if (Utility.CheckRuleUser( session,  request, response, "DonhangSvl", param, Utility.XEM ))
			{
				Utility.RedireactLogin(session, request, response);
				return;
			}

			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);

			out.println(userId);

			if (userId.length() == 0)
				userId = Utility.antiSQLInspection(request.getParameter("userId"));

			String id = util.getId(querystring);
			IDonhang dhBean = new Donhang(id);
			dhBean.setUserId(userId); // phai co UserId truoc khi Init
			dhBean.init();

			dhBean.setKhId(dhBean.getKhId());
			session.setAttribute("bgstId", dhBean.getBgstId());
			session.setAttribute("khoId", dhBean.getKhoId());
			String nextJSP;
			
			System.out.println("request.getQueryString(): "+request.getQueryString());

			if (request.getQueryString() != null && request.getQueryString().indexOf("display") >= 0) {
				// dhBean.CreateSpDisplay();
				nextJSP = "/AHF/pages/Distributor/DonHangDisplay.jsp";
			} else {
				nextJSP = "/AHF/pages/Distributor/DonHangUpdate.jsp";
			}
			System.out.println("nextJSP: "+nextJSP);

			session.setAttribute("dhBean", dhBean);
			session.setAttribute("khId", dhBean.getKhId());
			session.setAttribute("ddkdId", dhBean.getDdkdId());
			session.setAttribute("ngaydonhang", dhBean.getNgaygiaodich());
			// bo sung them de do phai truy xuat csdl khi nhan sanpham
			session.setAttribute("nppId", dhBean.getNppId());

			response.sendRedirect(nextJSP);
		}
	}

	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		String userId = cutil.antiSQLInspection((String) session.getAttribute("userId"));
		String userTen = cutil.antiSQLInspection((String) session.getAttribute("userTen"));
		String sum = cutil.antiSQLInspection((String) session.getAttribute("sum"));

		if (!cutil.check(userId, userTen, sum)) {
			response.sendRedirect("/AHF/index.jsp");
		} 
		else {
			Utility util = new Utility();
			String param1 = "";

			//Check User
			String view = Utility.antiSQLInspection(request.getParameter("view"));
			if (view == null) {
				view = "";
			}			

			if (view.trim().length() > 0) param1 ="&view="+view;

			if (Utility.CheckSessionUser( session,  request, response))
			{
				Utility.RedireactLogin(session, request, response);
				System.out.println("Error 1");
			}

			if (Utility.CheckRuleUser( session,  request, response, "DonhangSvl", param1, Utility.XEM ))
			{
				Utility.RedireactLogin(session, request, response);
				System.out.println("Error 2");
			}			

			session.setMaxInactiveInterval(30000);
			this.out = response.getWriter();
			
			try {
				IDonhang dhBean;
				String id = Utility.antiSQLInspection(request.getParameter("id"));

				if (id == null) {
					dhBean = new Donhang("");
				} 
				else {
					dhBean = new Donhang(id);
				}

				userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
				dhBean.setUserId(userId);
				
				String chap_nhan_giam_so_suat_km = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chap_nhan_giam_so_suat_km"));
				if(chap_nhan_giam_so_suat_km == null || chap_nhan_giam_so_suat_km.length() <=0)
					chap_nhan_giam_so_suat_km="false";
				dhBean.setChap_nhan_giam_so_suat_km(Boolean.parseBoolean(chap_nhan_giam_so_suat_km));

				String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
				if (nppId == null)
					nppId = "";
				dhBean.setNppId(nppId);
				
				
				String donhangkhac = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("donhangkhac"));
				if (donhangkhac == null)
					donhangkhac = "0";
				dhBean.setDonhangkhac(donhangkhac);
				System.out.println("donhangkhac = "+ dhBean.getDonhangkhac());

				
				String tenSpComBo = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tenSpComBo"));
				if (tenSpComBo == null)
					tenSpComBo = "";
				dhBean.setTenSpComBo(tenSpComBo);
				
				String giaSpComBo = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("giaSpComBo"));
				if (giaSpComBo == null)
					giaSpComBo = "";
				dhBean.setGiaSpComBo(giaSpComBo.replace(",",""));
				
				String soluongSpComBo = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("soluongSpComBo"));
				if (soluongSpComBo == null)
					soluongSpComBo = "";
				dhBean.setSoluongSpComBo(soluongSpComBo.replace(",",""));
				
				
				
				
				
				String gsbhId =geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("gsbhId"));
				if (gsbhId == null)
					gsbhId = "0";
				dhBean.setGsbhId(gsbhId);		

				
				String ngaygd = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"));
				System.out.println("ngaygd: " + ngaygd);

				if (ngaygd == null || ngaygd.trim().length() <= 0)
				{
					ngaygd = this.getDateTime();
				}			
				dhBean.setNgaygiaodich(ngaygd);
				/// Huy thêm vào nếu là pda thì cần reset lại ngày gd theo đơn hàng sẵn có
				ngaygd = dhBean.getNgaygiaodich();
				

				session.setAttribute("ngaydonhang", ngaygd);

				String trangthai = Utility.antiSQLInspection(request.getParameter("trangthai"));
				if (trangthai == null)
					trangthai = "";
				dhBean.setTrangthai(trangthai);

				String diachigiaohang = Utility.antiSQLInspection(request.getParameter("diachigiao"));
				if (diachigiaohang == null)
					diachigiaohang = "";
				dhBean.setDiachigiaohang(diachigiaohang);

				String ddkdId = Utility.antiSQLInspection(request.getParameter("ddkdTen"));
				if (ddkdId == null)
					ddkdId = "";
				dhBean.setDdkdId(ddkdId);
				session.setAttribute("ddkdId", ddkdId);

				String khoId = Utility.antiSQLInspection(request.getParameter("khoTen"));
				if (khoId == null)
					khoId = "";
				dhBean.setKhoId(khoId);

				String ngaygh = Utility.antiSQLInspection(request.getParameter("ngaygh"));
				if (ngaygh == null)
					ngaygh = "";
				dhBean.setNgaygiaohang(ngaygh);

				String khId = Utility.antiSQLInspection(request.getParameter("khId"));
				if (khId == null)
					khId = "";

				dhBean.setKhId(khId);
				session.setAttribute("khId", khId);
				String smartId = Utility.antiSQLInspection(request.getParameter("smartId"));
				if (smartId == null)
					smartId = "";
				dhBean.setSmartId(smartId);

				String khTen = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khTen"));
				if (khTen == null)
					khTen = "";
				dhBean.setKhTen(khTen);

				String coderoute = Utility.antiSQLInspection(request.getParameter("coderoute"));
				if (coderoute == null)
					coderoute = "";
				dhBean.setCoderoute(coderoute);
				System.out.println("coderoute: "+coderoute+"--khten: "+khTen);

				String nvgnId = Utility.antiSQLInspection(request.getParameter("nvgnId"));
				if (nvgnId == null)
					nvgnId = "";
				dhBean.setnvgnId(nvgnId);
				
				String ghichuO = Utility.antiSQLInspection(request.getParameter("ghichuO"));
				if (ghichuO == null)
					ghichuO = "";
				dhBean.setGhiChuOption(ghichuO);

				String muctindung = Utility.antiSQLInspection(request.getParameter("muctindung"));
				if (muctindung == null)
					muctindung = "0";
				dhBean.setMuctindung(muctindung);

				String chietkhau = Utility.antiSQLInspection(request.getParameter("ChietKhau"));
				if (chietkhau == null)
					chietkhau = "";
				else
					chietkhau = chietkhau.replaceAll(",", "");
				dhBean.setChietkhau(chietkhau);

				String tongtientruocVAT = Utility.antiSQLInspection(request.getParameter("SoTienChuaVAT"));
				if (tongtientruocVAT == null)
					tongtientruocVAT = "0";
				else
					tongtientruocVAT = tongtientruocVAT.replaceAll(",", "");
				dhBean.setTongtientruocVAT(tongtientruocVAT); // tien chi gom soluong * dongia --> de ap khuyen mai															

				String tongtiensauVAT = Utility.antiSQLInspection(request.getParameter("SoTienCoVAT"));
				if (tongtiensauVAT == null)
					tongtiensauVAT = "0";
				else
					tongtiensauVAT = tongtiensauVAT.replaceAll(",", "");
				dhBean.setTongtiensauVAT(tongtiensauVAT);

				String tongtienDonhang = Utility.antiSQLInspection(request.getParameter("SoTienSauCKKM"));
				if (tongtienDonhang == null)
					tongtienDonhang = tongtiensauVAT;
				else
					tongtienDonhang = tongtienDonhang.replaceAll(",", "");
				dhBean.setTongtiensauCKKM(Float.parseFloat(tongtienDonhang));

				System.out.println("aaa = "+ khTen);

				String TienChietKhau = "0";
				dhBean.setTongChietKhau(TienChietKhau);

			
				String VAT = Utility.antiSQLInspection(request.getParameter("VAT"));
				if (VAT == null)
					VAT = "0"; // TTC, don gia da co VAT
				else
					VAT = VAT.replaceAll(",", "");
				dhBean.setVAT(VAT);

				String bgstId = Utility.antiSQLInspection(request.getParameter("bgstId"));
				if (bgstId == null)
					bgstId = "0"; //neu khach hang khong co bang gia sieu thi
				/* System.out.println("bang gia sieu thi:" + bgstId ); */
				dhBean.setBgstId(bgstId);
				session.setAttribute("bgstId", bgstId);

				String ngayks =  dhBean.getNgayKs();

				String ghichu = Utility.antiSQLInspection(request.getParameter("ghichu"));
				if (ghichu == null || ghichu.length() == 0)
					ghichu = "NA";
				dhBean.setGhiChu(ghichu);

				String nocu = Utility.antiSQLInspection(request.getParameter("NoCu"));
				if (nocu == null)
					nocu = "0";
				dhBean.setNoCu(Double.parseDouble(nocu.replaceAll(",", "")));

				String sotiengiam = Utility.antiSQLInspection(request.getParameter("sotiengiam"));
				if (sotiengiam == null)
					sotiengiam = "0";
				dhBean.setSotiengiam(sotiengiam.replaceAll(",", ""));

				String ngaysua = getDateTime();
				dhBean.setNgaysua(ngaysua);

				String sqlck = "select chietkhau from khachhang where pk_seq = '" + dhBean.getKhId() + "' ";
				ResultSet rs = dhBean.getDb().get(sqlck);
				String spchietkhau1 = "0";
				if (rs != null) {
					try {
						while (rs.next()) {
							spchietkhau1 = rs.getString("chietkhau");
						}
					} 
					catch (SQLException e) {
						e.printStackTrace();
					}
				}

				if (spchietkhau1 == null) {
					spchietkhau1 = "0";
				}
				String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
				System.out.println("__Action la: " + action);

				dhBean.setChietkhauDH(spchietkhau1);
				System.out.println("_+_cKKKK+___" + dhBean.getChietkhauDH());

				String[] masp = request.getParameterValues("masp");		
				String[] hangmauVal = request.getParameterValues("hangmauVal");		
				String[] tensp = request.getParameterValues("tensp");
				String[] soluong = request.getParameterValues("soluong");
				String[] donvitinh = request.getParameterValues("donvitinh");
				String[] spchietkhau = request.getParameterValues("spchietkhau");
				String[] spchietkhauDLN = request.getParameterValues("spchietkhauDLN");
				String[] spchietkhauTT = request.getParameterValues("spchietkhauTT");
				String[] spchietkhauDH = request.getParameterValues("spchietkhauDH");
				String[] tonkho = request.getParameterValues("tonkho");
				String[] spGiagoc = request.getParameterValues("spGiagoc");
				String[] spBgId = request.getParameterValues("spBgId");
				String[] tiengiamtru = request.getParameterValues("tiengiamtru");
				
				
				String[] dongia = dhBean.getDongiaArr(dhBean.getDb(), ngaygd, masp);

				boolean flag_Dongia = false;
				if (dongia == null || dongia.length <= 0) {
					flag_Dongia = true;
				}

				String[] chonTichluyDacBiet = request.getParameterValues("chonTichluyDacBiet");
				dhBean.setTichluyIdList(chonTichluyDacBiet);		
				
				long tonggiatriKm = Math.round(Float.parseFloat(tongtientruocVAT));

				List<ISanpham> spList = new ArrayList<ISanpham>();
				if (masp != null) {
					ISanpham sanpham = null;
					String[] param = new String[12];
					int m = 0;
					while (m < masp.length) {
						if (masp[m] != "") {
							if (soluong[m].length() > 0) // chi them nhung san pham co so luong > 0
							{
 
								if(dongia[m] == null){
									dhBean.setUserId(userId); // phai co UserId truoc khi Init
									dhBean.init();
									dhBean.setKhId(dhBean.getKhId());
									session.setAttribute("bgstId", dhBean.getBgstId());
									session.setAttribute("khoId", dhBean.getKhoId());
									dhBean.setMessage("Vui lòng kiểm tra lại đơn giá sản phẩm: "+ tensp[m] );
									String nextJSP = "/AHF/pages/Distributor/DonHangUpdate.jsp";
									session.setAttribute("dhBean", dhBean); 
									session.setAttribute("khId", dhBean.getKhId());
									session.setAttribute("ddkdId", dhBean.getDdkdId());
									session.setAttribute("ngaydonhang", dhBean.getNgaygiaodich());
									session.setAttribute("nppId", dhBean.getNppId());
									response.sendRedirect(nextJSP);
									return;
								}
								System.out.println("sp thu:" + m);
								System.out.println(" dongia[m]: " + dongia[m].replaceAll(",", ""));
 
								String hm = "0";
								if(hangmauVal != null || hangmauVal[m] != null)
									hm = hangmauVal[m];
								
								
 
								param[0] = "idSP";
								param[1] = masp[m];
								param[2] = tensp[m];
								param[3] = soluong[m].replaceAll(",", "");
								param[4] = donvitinh[m];
								param[5] = hm.equals("1") ? "0" : dongia[m].replaceAll(",", "");
								param[6] = ""; //khuyen mai
								param[7] = spchietkhau[m].replaceAll(",", "");
								param[8] = spchietkhauDLN[m].replaceAll(",", "");
								param[9] = spchietkhauTT[m].replaceAll(",", "");
								param[10] = spchietkhauDH[m].replaceAll(",", "");
								System.out.println("DonGia: "+ dongia[m].replaceAll(",", ""));
								sanpham = new Sanpham(param);
								sanpham.setTonhientai(tonkho[m].replaceAll(",", ""));
								sanpham.setGiagoc(spGiagoc[m].replaceAll(",", ""));
								sanpham.setBgId(spBgId[m]);
								sanpham.setHangmau(hm);
								try
								{
									sanpham.setTiengiamtru(  tiengiamtru[m].replaceAll(",", "")   );
								}catch (Exception e) {
									// TODO: handle exception
								}
								spList.add(sanpham);
								

								
							}
						}
						m++;
					}
				}

				if (!checkNgayKhoaSo(ngayks, ngaygd)) {
					if (id == null) 
					{
						dhBean.createRS();
						dhBean.setNgaygiaodich(ngaygd);
						dhBean.setSpList(spList);
						dhBean.setMessage("Bạn phải nhập ngày đơn hàng lớn hơn ngày khóa sổ gần nhất...");
						session.setAttribute("dhBean", dhBean);
						String nextJSP = "/AHF/pages/Distributor/DonHangNew.jsp";
						response.sendRedirect(nextJSP);
						return;
					} 
					else 
					{
						dhBean.setMessage("Bạn phải nhập ngày đơn hàng lớn hơn ngày khóa sổ gần nhất...");
						dhBean.init();
						dhBean.setNgaygiaodich(ngaygd);
						dhBean.setSpList(spList);
						dhBean.setKhId(khId);
						dhBean.setDdkdId(ddkdId);
						session.setAttribute("dhBean", dhBean);
						String nextJSP = "/AHF/pages/Distributor/DonHangUpdate.jsp";
						response.sendRedirect(nextJSP);
						return;
					}
				}

				// Kiem tra nhung san pham khi luu, chot don hang (check ton kho)
				Hashtable<String, Integer> spThieuList = new Hashtable<String, Integer>();
				if (masp != null) 
				{
					int m = 0;
					while (m < masp.length) {
						if (masp[m].length() > 0) {
							String sl = "0";
							String sanpham_fk = "";

							String query = "\n select sanpham_fk, available from nhapp_kho where npp_fk='"+ nppId + "' and kho_fk = '" + khoId + "' "+
							"\n and sanpham_fk in (select pk_seq from sanpham where ma= '"+ masp[m].trim()+ "') " +
							"\n and kbh_fk  in (select kbh_fk from khachhang where pk_seq= '"+ dhBean.getKhId() + "') ";
							//System.out.println("Get San Pham thieu : "+query);
							ResultSet slspAvailable = dhBean.getDb().get(query);
							if (slspAvailable != null) {
								try {
									if (slspAvailable.next()) {
										sl = slspAvailable.getString("available");
										sanpham_fk = slspAvailable.getString("sanpham_fk");
										slspAvailable.close();

										// lay nhung sp da co trong don hang
										if (id != null) {
											String slg = "0";
											query = "\n select ISNULL(soluong, 0) as soluong from DONHANG_SANPHAM " +
											"\n where DONHANG_FK = "+id+
											"\n and SANPHAM_FK in (select pk_seq from sanpham where ma='"+ masp[m].trim() + "')";
											ResultSet SlgRs = dhBean.getDb().get(query);
											if (SlgRs != null) {
												SlgRs.next();
												if (SlgRs.getString("soluong") != null)
													slg = SlgRs.getString("soluong");
												SlgRs.close();
											}

											sl = Integer.toString((Math.round(Float.parseFloat(sl)+ Float.parseFloat(slg))));
											// System.out.print("\n So luong la: "+
											// sl + "\n");
										}
									} else {
										soluong[m] = "0";
									}
								} catch (Exception e) {
									e.printStackTrace();
								}

								// System.out.println("So luong avail: " + sl +
								// " -- So luong ban nhap: " + soluong[m] + "\n");
								if (soluong[m].replaceAll(",", "") == "")
									soluong[m] = "0";
								if (Float.parseFloat(soluong[m].replaceAll(",", "")) > Float.parseFloat(sl)) {
									spThieuList.put(masp[m], Integer.parseInt(sl.replaceAll(",", "")));

									// Chen vao bang thieu hang
									try {
										query = "insert thieuhang_npp(sp_fk, kh_fk, npp_fk, ngayghinhan, soluonghientai, soluongdat) "
											+ "values('"+ sanpham_fk+ "', '"+ khId+ "', '"+ nppId+ "', '"+ getDateTimeNow()+ "', '"+ sl	+ "', '"+ soluong[m].replaceAll(",", "")+ "')";
										/*
										 * System.out.println("____Insert thieu hang: "
										 * + query);
										 */
										dhBean.getDb().update(query);
									} 
									catch (Exception e) {
										dhBean.setMessage("Lỗi ngoại lệ 500");
									}
								}
							}
						}
						m++;
					}
				}
				dhBean.setSpThieuList(spThieuList);

				String hinhthuc = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("hinhthuc"));
				if (hinhthuc == null)
					hinhthuc = "0";

				

				if (action.equals("submitKh")) 
				{
					String sql = "";
					System.out.println("khTen: "+khTen);
					sql = "select top(1) isnull(a.coderoute,'')coderoute, " +
					"\n substring(a.smartid, charindex('_', a.smartid)+1, len(a.smartid)) as smartId, a.pk_seq as khId, " +
					"\n a.ten as khTen, f.CHIETKHAU as ckId, isnull(a.bgst_fk, '0') as bgstId, c.ddkd_fk, e.gsbh_fk, " +
					"\n a.diachi,(select top(1) nvgn_fk from nvgn_kh where khachhang_fk = a.pk_seq) as nvgn " +
					"\n from khachhang a inner join " +
					"\n khachhang_tuyenbh b on a.pk_seq = b.khachhang_fk " +
					"\n inner join tuyenbanhang c on b.tbh_fk = c.pk_seq " +
					"\n inner join ddkd_gsbh e on c.ddkd_fk = e.ddkd_fk  " +
					"\n left join MUCCHIETKHAU f on a.CHIETKHAU_FK = f.PK_SEQ " +
					"\n where a.trangthai = '1' and a.npp_fk = '"+ nppId+ "'  and a.smartId = '"+ smartId+ "'  " +
					"\n and e.gsbh_fk in ( " +
					"\n		select gsbh_fk from NHAPP_GIAMSATBH where npp_fk ='"+ nppId + "' " +
					"\n		" +
					"\n )";
					System.out.println("____Lay khach hang: " + sql);

					rs = dhBean.getDb().get(sql);
					if (rs != null) {
						try {
							if (rs.next()) {
								dhBean.setKhTen(rs.getString("khten")+" - "+rs.getString("diachi"));
								dhBean.setSmartId(rs.getString("smartId"));
								dhBean.setCoderoute(rs.getString("coderoute"));
								dhBean.setKhId(rs.getString("khId"));
								System.out.println("__+_______"+rs.getString("nvgn"));
								dhBean.setnvgnId(rs.getString("nvgn"));
								System.out.println("__+_in ra__+_"+dhBean.getnvgnId());
								session.setAttribute("khId", rs.getString("khId"));
								dhBean.setKhTen(rs.getString("khTen") + " - "+ rs.getString("diachi"));

								if (rs.getString("ckId") != null)
									dhBean.setChietkhau(rs.getString("ckId"));

								dhBean.setDdkdId(rs.getString("ddkd_fk"));
								session.setAttribute("ddkdId",rs.getString("ddkd_fk"));
								dhBean.setGsbhId(rs.getString("gsbh_fk"));

								String cmd = "select distinct banggiasieuthi_fk from BGST_KHACHHANG where khachhang_fk='"+ rs.getString("khId") + "'";
								System.out.println("Bang gia ST1: " + cmd + "\n");
								ResultSet bgst = dhBean.getDb().get(cmd);
								String bgstIdss = "";
								if (bgst != null) {
									while (bgst.next()) {
										bgstIdss += bgst.getString("banggiasieuthi_fk")+ ",";
									}
									bgst.close();
									if (bgstIdss.length() > 0)
										bgstIdss = bgstIdss.substring(0,bgstIdss.length() - 1);
								}
								session.setAttribute("bgstId", bgstIdss);
								if (id == null) {
									dhBean.createRS();
									dhBean.setSpList(spList);
									session.setAttribute("dhBean", dhBean);
									String nextJSP = "/AHF/pages/Distributor/DonHangNew.jsp";
									response.sendRedirect(nextJSP);
								} else {
									dhBean.init();
									dhBean.setSpList(spList);
									dhBean.setSmartId(rs.getString("smartId"));
									dhBean.setKhId(rs.getString("khId"));
									dhBean.setKhTen(rs.getString("khTen") + " - "+ rs.getString("diachi"));
									if (rs.getString("ckId") != null)
										dhBean.setChietkhau(rs.getString("ckId"));

									dhBean.setDdkdId(rs.getString("ddkd_fk"));
									dhBean.setGsbhId(rs.getString("gsbh_fk"));
									session.setAttribute("dhBean", dhBean);
									String nextJSP = "/AHF/pages/Distributor/DonHangUpdate.jsp";
									response.sendRedirect(nextJSP);
								}
							} else {
								if (id == null) {
									dhBean.createRS();
									dhBean.setSpList(spList);
									dhBean.setMessage("Mã khách hàng không đúng, hoặc khách hàng chưa được phân tuyến vui lòng kiểm tra lại...");
									session.setAttribute("dhBean", dhBean);
									String nextJSP = "/AHF/pages/Distributor/DonHangNew.jsp";
									response.sendRedirect(nextJSP);
								} else {
									dhBean.init();
									dhBean.setSpList(spList);

									dhBean.setMessage("Mã khách hàng không đúng, hoặc khách hàng chưa được phân tuyến vui lòng kiểm tra lại...");
									session.setAttribute("dhBean", dhBean);
									String nextJSP = "/AHF/pages/Distributor/DonHangUpdate.jsp";
									response.sendRedirect(nextJSP);
								}
							}
							if (rs != null) {
								rs.close();
							}
						}
						catch (Exception e) {
							e.printStackTrace();
						}
					}
				} 
				else if (action.equals("chot_inside")) {
					DonhangSvl DhSvl = new DonhangSvl();
					boolean pass = false;
					String msg = DhSvl.chot_final(id, userId, dhBean.getNppId(),1,dhBean.getNgaygiaohang());

					if (msg == null || msg.trim().length() <= 0) { pass = true; }
					else { pass = false; }

					if (pass) {			
						dhBean.DBclose();
						IDonhangList obj = new DonhangList();
						msg = "Chốt đơn hàng " + id + " thành công.";
						obj.setMsg(msg);
						obj.setUserId(userId);
						obj.init("");
						session.setAttribute("obj", obj);
						session.setAttribute("ddkdId", "");

						String nextJSP = "/AHF/pages/Distributor/DonHang.jsp";
						response.sendRedirect(nextJSP);
						return;
					}
					else {							
						dhBean.setMessage(msg);
						dhBean.init();
						dhBean.setSpList(spList);
						dhBean.setKhId(khId);
						dhBean.setDdkdId(ddkdId);
						session.setAttribute("dhBean", dhBean);
						String nextJSP = "/AHF/pages/Distributor/DonHangUpdate.jsp";
						response.sendRedirect(nextJSP);
						return;
					}					
				}
				else {
					if (action.equals("save")) {
						if (id == null) {
							boolean tao = dhBean.CreateDh(spList);
							String dhId = dhBean.getId();
							if (!tao) {
								dhBean.createRS();
								dhBean.setSpList(spList);
								session.setAttribute("dhBean", dhBean);
								String nextJSP = "/AHF/pages/Distributor/DonHangNew.jsp";
								response.sendRedirect(nextJSP);
							} else {

								IDonhangList obj = new DonhangList();
								obj.setUserId(userId);
								obj.init("");
								session.setAttribute("obj", obj);
								session.setAttribute("ddkdId", "");
								String nextJSP = "/AHF/pages/Distributor/DonHang.jsp";
								response.sendRedirect(nextJSP);
								return;
							}
						} else {
							// Kiem tra xem neu don hang do da co khuyen mai, ma
							// thay doi san pham thi phai ap lai khuyen mai
							boolean flag = false;
							boolean cokm = Boolean.parseBoolean(Utility.antiSQLInspection(request.getParameter("cokm")));

							if (cokm == true) {
								flag = CheckDonHangDKM(spList, id, dhBean.getDb());
								if (flag == false) {
									dhBean.init();
									dhBean.setSpList(spList);
									dhBean.setKhId(khId);
									dhBean.setDdkdId(ddkdId);
									dhBean.setMessage("Đơn hàng này đã hưởng khuyến mại, khi thay đổi sản phẩm bạn phải áp lại khuyến mại");
									session.setAttribute("dhBean", dhBean);
									String nextJSP = "/AHF/pages/Distributor/DonHangUpdate.jsp";
									response.sendRedirect(nextJSP);
									return;
								}
							}

							boolean temp = false;
							if (trangthai.equals("3")) // da xuat kho
								temp = dhBean.UpdateDhXuatKho(spList);
							else
								temp = dhBean.UpdateDh(spList, action);

							if (temp == false) {
								dhBean.init();
								dhBean.setSpList(spList);
								dhBean.setKhId(khId);
								dhBean.setDdkdId(ddkdId);
								session.setAttribute("dhBean", dhBean);
								String nextJSP = "/AHF/pages/Distributor/DonHangUpdate.jsp";
								response.sendRedirect(nextJSP);
								return;
							} else {

								IDonhangList obj = new DonhangList();
								obj.setUserId(userId);
								obj.init("");
								session.setAttribute("obj", obj);
								session.setAttribute("ddkdId", "");
								String nextJSP = "/AHF/pages/Distributor/DonHang.jsp";
								response.sendRedirect(nextJSP);
								return;
							}
						}
					} else {
						if (action.equals("chotdonhang")) {
							// save donhangDXK truoc
							if (!(dhBean.UpdateDhXuatKho(spList))) {
								dhBean.init();
								dhBean.setKhId(khId);
								dhBean.setDdkdId(ddkdId);
								session.setAttribute("dhBean", dhBean);
								String nextJSP = "/AHF/pages/Distributor/DonHangUpdate.jsp";
								response.sendRedirect(nextJSP);
								return;
							} else {
								if (!(dhBean.ChotDh(spList))) {
									dhBean.init();
									dhBean.setSpList(spList);
									dhBean.setDdkdId(ddkdId);
									dhBean.setKhId(khId);
									session.setAttribute("dhBean", dhBean);
									String nextJSP = "/AHF/pages/Distributor/DonHangUpdate.jsp";
									response.sendRedirect(nextJSP);
									return;
								} else {
									IDonhangList obj = new DonhangList();
									obj.setUserId(userId);
									obj.init("");
									session.setAttribute("obj", obj);
									session.setAttribute("ddkdId", "");
									String nextJSP = "/AHF/pages/Distributor/DonHang.jsp";
									response.sendRedirect(nextJSP);
									return;
								}
							}
						} else 
						{
							if (action.equals("apkhuyenmai") && !flag_Dongia) {
								// Save donhang truoc
								if (id == null) {
									boolean tao = dhBean.CreateDh(spList);
									if (!tao) {
										dhBean.createRS();
										dhBean.setSpList(spList);
										session.setAttribute("dhBean", dhBean);
										String nextJSP = "/AHF/pages/Distributor/DonHangNew.jsp";
										response.sendRedirect(nextJSP);
										return;
									} else {
										id = dhBean.getId();
									}
								} else 
								{
									boolean temp = false;
									if (trangthai.equals("3")) // da xuat kho
										temp = dhBean.UpdateDhXuatKho(spList);
									else
										temp = dhBean.UpdateDh(spList, action);

									if (!temp) {
										dhBean.init();
										dhBean.setKhId(khId);
										dhBean.setDdkdId(ddkdId);
										session.setAttribute("dhBean", dhBean);
										String nextJSP = "/AHF/pages/Distributor/DonHangUpdate.jsp";
										response.sendRedirect(nextJSP);
										return;
									}
								}

								String sql = "select * from donhang where pk_seq ='"+ id + "' and tbh_fk is null ";
								rs = dhBean.getDb().get(sql);
								String msg = "";
								if (rs != null) {
									try {
										while (rs.next()) {
											msg += "Đơn hàng không có tuyến, vui lòng kiểm tra lại";
										}

										rs.close();
									} 
									catch (SQLException e) {
										e.printStackTrace();
									}
								}
								if (msg.trim().length() > 0) {
									return;
								}
								
								sql = " select sp.ma, dhsp.soluong, dhsp.giamua " +
									  " from donhang_sanpham dhsp" +
									  " inner join sanpham sp on sp.pk_seq = dhsp.sanpham_fk " +
									  " where dhsp.donhang_fk = " + id;
								rs = dhBean.getDb().get(sql);
								
								String _masp = "";
								String _soluong = "";
								String _dongia = "";
								Hashtable<String, Integer> sanpham_soluong = new Hashtable<String, Integer>();
								Hashtable<String, Float> sanpham_dongia = new Hashtable<String, Float>();
								Hashtable<String, String> sanpham_donhang = new Hashtable<String, String>();
								int m = 0;
								while(rs.next())
								{
									System.out.println("111.Ma sp: "+ rs.getString("ma") + " --- So Luong: " + rs.getInt("soluong"));
									
									sanpham_soluong.put(rs.getString("ma"),rs.getInt("soluong"));
									sanpham_dongia.put(rs.getString("ma"),rs.getFloat("giamua"));
									sanpham_donhang.put(rs.getString("ma"),id);
									
									_masp += _masp.length() > 0 ? "__" +  rs.getString("ma")  : rs.getString("ma");
									_soluong += _soluong.length() > 0 ? "__" +  rs.getString("soluong")  : rs.getString("soluong");
									_dongia += _dongia.length() > 0 ? "__" +  rs.getString("giamua")  : rs.getString("giamua");
									
									m++;
								}
								System.out.println("_masp :" + _masp);
								System.out.println("_soluong :" + _soluong);
								System.out.println("_dongia :" + _dongia);
								// truoc khi ap lai khuyen mai
								try {
									/* String msg = CheckTonKho(id,db); */

									dhBean.getDb().getConnection().setAutoCommit(false);
									msg += capNhatKM(id, nppId, khId,trangthai, dhBean.getDb());

									System.out.println("cau Lenh Cap Nhat KM Tra Ve: "+ msg);
									if (msg.length() > 0) {
										dhBean.init();
										dhBean.setKhId(khId);
										dhBean.setDdkdId(ddkdId);
										dhBean.setMessage("3.Khong The Cap Nhat Khuyen Mai Cua Don Hang: "+ msg);
										session.setAttribute("dhBean", dhBean);
										String nextJSP = "/AHF/pages/Distributor/DonHangUpdate.jsp";
										response.sendRedirect(nextJSP);
										return;
									} else {
										dhBean.getDb().getConnection().commit();
										dhBean.getDb().getConnection().setAutoCommit(true);
									}
								} catch (Exception e) {

									try {
										dhBean.getDb().getConnection().rollback();
										dhBean.getDb().getConnection().setAutoCommit(true);
										e.printStackTrace();
										dhBean.init();
										dhBean.setSpList(spList);
										dhBean.setKhId(khId);
										dhBean.setDdkdId(ddkdId);
										session.setAttribute("dhBean", dhBean);
										String nextJSP = "/AHF/pages/Distributor/DonHangUpdate.jsp";
										response.sendRedirect(nextJSP);
										return;
									} catch (SQLException e1) {
										e1.printStackTrace();
									}


								}

								
								/************************************************************/

								XLkhuyenmai xlkm = new XLkhuyenmai(userId,ngaygd, khId,id); // ngay giao dich trong donhang
								xlkm.setKhachhang(khId);
								xlkm.setChap_nhan_giam_so_suat_km(Boolean.parseBoolean(chap_nhan_giam_so_suat_km));
								xlkm.setMasp(_masp.split("__"));
								xlkm.setSoluong(_soluong.split("__"));
								xlkm.setDongia(_dongia.split("__"));
								xlkm.setIdDonhang(id);
								xlkm.setTonggiatriDh((float) tonggiatriKm);
								xlkm.setNgaygiaodich(ngaygd);
								xlkm.setHashA(sanpham_soluong);
								xlkm.setHashB(sanpham_dongia);
								xlkm.setHashD(sanpham_donhang);
								xlkm.setDieuchinh(false); // Lay truong hop ngau nhien
								
								xlkm.ApKhuyenMai();
								List<ICtkhuyenmai> ctkmResual = xlkm.getCtkmResual();
								
								if(!xlkm.getChap_nhan_giam_so_suat_km() && xlkm.getCtkm_bi_giam_so_suat().size() > 0)
								{
									dhBean.init();
									dhBean.setSpList(spList);
									dhBean.setKhId(khId);
									dhBean.setDdkdId(ddkdId);
									dhBean.setCtkm_bi_giam_so_suat(xlkm.getCtkm_bi_giam_so_suat());
									dhBean.setAplaikhuyenmai(false);
									dhBean.setIsChuaApkhuyenmai(true);
									
									xlkm.DBclose();
									session.setAttribute("dhBean", dhBean);
									String nextJSP = "/AHF/pages/Distributor/DonHangUpdate.jsp";
									response.sendRedirect(nextJSP);
									return;
									
								}
								
								if (xlkm.checkConfirm()) // bi dung --> sang trang lua chon
								{									
									xlkm.SetDungSanPham() ;
									xlkm.setKey_sanpham_sudung();
									xlkm.setTichluyIdList(chonTichluyDacBiet);
									System.out.println("Bi dung san pham roi...\n");
									session.setAttribute("xlkm", xlkm);
									String nextJSP = "/AHF/pages/Distributor/KhuyenMai.jsp";
									response.sendRedirect(nextJSP);
									return;
								}
								msg = ""; // nhung ctkm khong thoa
								xlkm.setKey_sanpham_sudung();
								if ( xlkm.checkConfirm() == false)
									msg = Luu_Khuyen_mai_Khong_dieu_Chinh( dhBean,ctkmResual,xlkm.getKey_sanpham_sudung(), id,	nppId, tonggiatriKm, khId,sanpham_soluong,sanpham_dongia, dhBean.getDb(), ngaygd);
								
								if(msg.length() <=0)
								{
									msg = dhBean.ApTichluyDacBiet();
								}
								Utility.Update_GiaTri_DonHang(dhBean.getId(), dhBean.getDb());
								
								
								if (msg.length() > 0) {
									dhBean.init();
									dhBean.setSpList(spList);
									dhBean.setKhId(khId);
									dhBean.setDdkdId(ddkdId);

									dhBean.setAplaikhuyenmai(false);
									dhBean.setIsChuaApkhuyenmai(true);
									xlkm.DBclose();
									dhBean.setMessage("thong bao KM: "+ msg);
									session.setAttribute("dhBean", dhBean);
									String nextJSP = "/AHF/pages/Distributor/DonHangUpdate.jsp";
									response.sendRedirect(nextJSP);
									return;
								}

								String nextJSP = "";
								if (ctkmResual.size() > 0) {
									session.setAttribute("xlkm", xlkm);
									nextJSP = "/AHF/pages/Distributor/KhuyenMai.jsp";
									response.sendRedirect(nextJSP);
								} else {
									xlkm.DBclose();
									dhBean.init();
									dhBean.setAplaikhuyenmai(false);
									dhBean.setIsChuaApkhuyenmai(true);
									dhBean.setKhId(khId);
									dhBean.setDdkdId(ddkdId);
									session.setAttribute("dhBean", dhBean);
									nextJSP = "/AHF/pages/Distributor/DonHangUpdate.jsp";
									response.sendRedirect(nextJSP);
									return;
								}
							
							} else // submit
							{
								String msg = "";
								if (flag_Dongia) {
									msg = "Lỗi đơn giá không hợp lệ, vui lòng liên hệ trung tâm để kiểm tra!";
								}
								dhBean.setUserId(userId);
								dhBean.createRS();
								System.out.println("GSBHID: " + dhBean.getGsbhId());
								String nextJSP = "/AHF/pages/Distributor/DonHangNew.jsp";
								if (id != null) {
									dhBean.CreateSpList();
									List<ISanpham> listSp = dhBean.getSpList();

									// cap nhat gia cac san pham
									for (int i = 0; i < spList.size(); i++) 
									{
										ISanpham sp = spList.get(i);
										for (int j = 0; j < listSp.size(); j++) 
										{
											ISanpham sp2 = listSp.get(j);
											if (sp.getMasanpham().trim().equals(sp2.getMasanpham().trim()))
												spList.get(j).setDongia(sp2.getDongia());
										}
									}
									nextJSP = "/AHF/pages/Distributor/DonHangUpdate.jsp";
								}
								if (msg != null && msg.length() > 0) {
									dhBean.setMessage(msg);
								}
								dhBean.setSpList(spList);
								session.setAttribute("ddkdId", ddkdId);
								session.setAttribute("dhBean", dhBean);
								response.sendRedirect(nextJSP);
							}
						}
					}
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	

	private boolean CheckDonHangDKM(List<ISanpham> spList, String id, dbutils db) {
		List<ISanpham> list = new ArrayList<ISanpham>();
		String query = "select a.sanpham_fk as spId, a.soluong, b.ma as spMa from donhang_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq where donhang_fk = '"+ id + "'";
		ResultSet rs = db.get(query);

		if (rs != null) {
			try {
				ISanpham sp = null;
				while (rs.next()) {
					sp = new Sanpham("", rs.getString("spMa"), "",rs.getString("soluong"), "", "", "", "");
					list.add(sp);
				}
				rs.close();
			} catch (Exception e) {
				return false;
			}
		}

		for (int i = 0; i < list.size(); i++) {
			ISanpham spA = list.get(i);
			for (int j = 0; j < spList.size(); j++) {
				ISanpham spB = spList.get(j);
				if (spA.getMasanpham().trim().equals(spB.getMasanpham().trim())) {
					int slg = Integer.parseInt(spA.getSoluong())- Integer.parseInt(spB.getSoluong());
					System.out.println("San pham A: " + spA.getMasanpham()	+ " - So luong: " + spA.getSoluong()
							+ " -- San pham B: " + spB.getMasanpham()	+ " -- So luong: " + spB.getSoluong() + "\n");
					list.get(i).setSoluong(Integer.toString(slg));
				}
			}
		}

		for (int i = 0; i < list.size(); i++) {
			ISanpham spC = list.get(i);
			// System.out.println("San pham " + spC.getMasanpham() +
			// " tai List la: " + spC.getSoluong() + "\n");
			if (!spC.getSoluong().equals("0")) {
				// System.out.println("San pham " + spC.getMasanpham() +
				// " tai List la: " + spC.getSoluong() + "\n");
				return false;
			}
		}

		return true;
	}

	private String CreateKhuyenmai(ICtkhuyenmai ctkm, String id, String nppId,long tongGtridh, String khId, Hashtable<String, Integer> sp_sl,Hashtable<String, Float> sp_dg, dbutils db, String tungay) {
		String str = "";
		try {
			db.getConnection().setAutoCommit(false);
			List<ITrakhuyenmai> trakmList = ctkm.getTrakhuyenmai();
			for (int count = 0; count < trakmList.size(); count++) {
				// ITrakhuyenmai trakm = ctkm.getTrakhuyenmai().get(0);
				ITrakhuyenmai trakm = ctkm.getTrakhuyenmai().get(count);
				long tongtienTraKM = 0;
				if (trakm.getType() == 1)
					tongtienTraKM = Math.round(ctkm.getSoxuatKM()* trakm.getTongtien());
				else {
					if (trakm.getType() == 2) // tra chiet khau
					{
						System.out.println("___Tong tien tra km: "+ ctkm.getTongTienTheoDKKM()+ " -- Chiet khau: " + trakm.getChietkhau());
						// Tinh tong gia tri tra khuyen mai theo dieu kien (chu
						// khong phai tren tong gia tri don hang)
						long tonggiatriTrakm = ctkm.getTongTienTheoDKKM();
						tongtienTraKM = Math.round(tonggiatriTrakm* (trakm.getChietkhau() / 100));
						// tongtienTraKM = Math.round(tongGtridh *
						// (trakm.getChietkhau() / 100));
					} else {
						if (trakm.getHinhthuc() == 1) {
							tongtienTraKM = Math.round(ctkm.getSoxuatKM()* trakm.getTongGtriKm());
							System.out.println("Tong tien trakm co dinh: "+ tongtienTraKM + "\n");
						}
					}
				}

				String msg = "";
				if (trakm.getType() == 3) // san pham co so luong co dinh
				{
					/* System.out.println("san pham co so luong co dinh "); */
					if (trakm.getHinhthuc() == 1) {
						// String sql =
						// "select a.sanpham_fk as spId, a.soluong, b.ma as spMa, a.dongia from trakhuyenmai_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq where a.trakhuyenmai_fk = '"
						// + trakm.getId() + "'";
						/*String sql = "\n  select f.pk_seq as spId, a.soluong, e.GIAMUANPP as dongia, f.ma as spMa  "
								+ "\n from Trakhuyenmai_sanpham a inner join SANPHAM f on a.SANPHAM_FK = f.PK_SEQ "
								+ "\n	inner join BGMUANPP_SANPHAM e on a.sanpham_fk = e.SANPHAM_FK "
								+ "\n where a.TRAKHUYENMAI_FK = '"+ trakm.getId()+ "' and e.BGMUANPP_FK in ( select top(1) a.PK_SEQ "
								+ "\n from BANGGIAMUANPP a inner join BANGGIAMUANPP_NPP b on a.PK_SEQ = b.BANGGIAMUANPP_FK  "
								+ "\n where a.TUNGAY <= '"+ tungay+ "' and b.NPP_FK = '"+ nppId
								+ "' and a.KENH_FK = ( select KBH_FK from KHACHHANG where PK_SEQ = '"
								+ khId
								+ "' ) and a.DVKD_FK = '100068' "
								+ "\n order by a.TUNGAY desc  ) and e.GIAMUANPP > 0  and e.trangthai = '1' "
								+ "\n  and exists  "
								+ "\n  ( select 1 from nhapp_kho "
								+ "\n    where kho_fk= (select kho_fk from ctKhuyenMai where pk_seq='"
								+ ctkm.getId()
								+ "') and sanpham_fk=a.sanpham_fk "
								+ "\n  and npp_Fk='"
								+ nppId
								+ "' and KBH_FK=(SELECT KBH_FK from KhachHang where pk_Seq='"
								+ khId + "' ) and AVAILABLE>0 ) ";*/
						
						String sql = "\n select f.pk_seq as spId, a.soluong, dg.dongia , f.ma as spMa  " + 
									 "\n from Trakhuyenmai_sanpham a" +
									 "\n inner join ctKhuyenMai ctkm on ctkm.pk_seq = " + ctkm.getId() +
									 "\n inner join donhang dh on dh.pk_seq = " + id +
									 "\n inner join khachhang kh on kh.pk_seq = 	" + khId + 
									 "\n inner join SANPHAM f on a.SANPHAM_FK = f.PK_SEQ " + 
									 "\n cross apply ( select [dbo].[GiaBanLeNppSanPham](kh.pk_seq,kh.kbh_fk,"+nppId+",a.SANPHAM_FK,dh.ngaynhap)  dongia  )dg " + 
									 "\n where a.TRAKHUYENMAI_FK = '"+ trakm.getId()+ "' --and dg.dongia > 0 " + 
									 "\n and exists  " + 
									 "\n (	" +
									 "\n     select 1 from nhapp_kho " + 
									 "\n     where kho_fk= ctkm.kho_fk and sanpham_fk=a.sanpham_fk " + 
									 "\n     and npp_Fk="+nppId+" and KBH_FK=kh.kbh_fk and AVAILABLE>0 " +
									 "\n ) ";

						System.out.println("[BGMUANPP_SANPHAM]" + sql);

						ResultSet rsSQl = db.get(sql);
						/* if (rsSQl != null) */
						{
							while (rsSQl.next()) {
								int slg = Integer.parseInt(rsSQl.getString("soluong"))* (int) ctkm.getSoxuatKM();
								long tonggtri = Math.round(slg* rsSQl.getFloat("dongia"));

								/*********************************************************************************/
								
								/*********************************************************************************/

								String error = checkTonkho(nppId, ctkm.getId(),khId, rsSQl.getString("spId"),rsSQl.getString("spMa"), slg, db);
								if (error.length() > 0) {
									return error;
								}
								
								String query = "Insert into donhang_ctkm_trakm(donhangId, ctkmId, trakmId, soxuat, tonggiatri, spMa,sanpham_fk, soluong) "
										+ " values('"+ id+ "','"+ ctkm.getId()+ "','"+ trakm.getId()+ "','"+ ctkm.getSoxuatKM()+ "','"+ Long.toString(tonggtri)
										+ "', '"+ rsSQl.getString("spMa").trim()+ "', '"+ rsSQl.getString("spId").trim()+ "', '" + Integer.toString(slg) + "')";
								System.out.println("1.Chen khuyen mai co dinh: "+ query);

								if (!db.update(query)) {
									str = query;
									return str;
								}
								
/*								// cap nhat kho
								query = " if ((select count(*) From DonHang where pk_seq='"+ id+ "' and TrangThai=0)=1)  "
										+ "Update nhapp_kho set available = available - '"+ Integer.toString(slg)+ "', booked = booked + '"
										+ Integer.toString(slg)	+ "' where kho_fk = (select kho_fk from ctkhuyenmai where pk_seq = '"
										+ ctkm.getId()+ "') and npp_fk = '"	+ nppId
										+ "' and sanpham_fk = '"+ rsSQl.getString("spId")
										+ "' and kbh_fk = (select kbh_fk from khachhang where pk_seq = '"+ khId + "')";
								System.out.println("2.Cap nhat kho: " + query);
								if (!db.update(query)) {
									db.getConnection().rollback();
									str = query;
									return str;
								}
								
								////////////////////////////////////////////////////////////////
								geso.dms.center.util.Utility util = new geso.dms.center.util.Utility();
								query=  " select KHO_FK,SANPHAM_FK,KBH_FK,NGAYNHAPKHO,available, (select ngaynhap from donhang where pk_seq='"+id+"') as ngaynhap from NHAPP_KHO_CHITIET "+  
						    			" where NPP_FK ="+nppId+" and KBH_FK= (select kbh_fk from khachhang where pk_seq = '" + khId+ "')"  +
						    			" and KHO_FK=(select kho_fk from ctKhuyenMai where pk_seq='"+ctkm.getId()+"')  and SANPHAM_FK =  "+rsSQl.getString("spId")+
						    			" AND AVAILABLE >0  and NGAYNHAPKHO<=(select ngaynhap from donhang where pk_seq='"+id+"')"+
						    			" order by NGAYNHAPKHO,AVAILABLE ";
						    	ResultSet rssp=db.get(query);
						    	double soluongdenghi=slg ;

						    	while(rssp.next() && soluongdenghi >0){
						    		double soluong_avai= rssp.getDouble("AVAILABLE");
						    		double soluongcapnhat=0;
						    		if (soluong_avai >soluongdenghi){
						    			soluongcapnhat= soluongdenghi;
						    			soluongdenghi=0;
						    		}else{
						    			soluongcapnhat =soluong_avai;
						    			soluongdenghi =soluongdenghi - soluong_avai;
						    		}

						    		String _khoid=rssp.getString("kho_fk");
						    		String _kbhid=rssp.getString("KBH_FK");
						    		String ngaynhapkho=rssp.getString("NGAYNHAPKHO");
						    		String ngaynhap=rssp.getString("ngaynhap");
						    		
						    		query = "Insert into donhang_ctkm_trakm_chitiet(donhangId, ctkmId, trakmId, soxuat, tonggiatri, spMa,SANPHAM_FK, soluong, ngaynhapkho) "
											+ " values('"+ id+ "','"+ ctkm.getId()+ "','"+ trakm.getId()+ "','"+ ctkm.getSoxuatKM()+ "','"+ Double.toString(rsSQl.getFloat("dongia")*soluongcapnhat)
											+ "', '"+ rsSQl.getString("spMa").trim()+ "','"+ rsSQl.getString("spId").trim()+ "', '" + (int)(soluongcapnhat) + "','"+ngaynhapkho+"')";
									System.out.println("1.Chen khuyen mai co dinh: "+ query);

									if (!db.update(query)) {
										db.getConnection().rollback();
										str = query;
										return str;
									}
									
									msg=util.Update_NPP_Kho_Sp(ngaynhap,id, "updae ĐƠN HÀNG_1454552 apkm", db, _khoid,rsSQl.getString("spId"), nppId, _kbhid, 0.0, soluongcapnhat, (-1)* soluongcapnhat, 0.0);
									if (msg.length() > 0)
									{
										msg = "Lỗi kho khi tạo đơn hàng: " + msg;
										return msg;
									}	
									 
									String msg1=util.Update_NPP_Kho_Sp_Chitiet(ngaynhap, "update donhang DHID ctkm : "+id ,  db, _khoid, rsSQl.getString("spId"), nppId, _kbhid, ngaynhapkho, 0,soluongcapnhat, (-1)*soluongcapnhat, 0, 0);
									if (msg1.length()> 0) 
									{
										msg=msg1;
										return msg1;
									}

						    	}
						    	rssp.close();
								
								if (soluongdenghi!=0){
									 
									msg=  "Số lượng đề xuất trong lô chi tiết của sản phẩm "+rsSQl.getString("spMa").trim()+"  tới ngày (ngày cấu hình hóa đơn) đơn hàng không còn đủ, " +
											" vui lòng kiểm tra báo cáo ( xuất nhập tồn,tồn hiện tại) theo lô để biết thêm chi tiết ";
									return msg;
									
								}
*/								
							/*	////////////////////////////////////////////////////////////////
								query = "\n select * from donhang_sanpham a  "+
										 "\n full outer join   "+
										 "\n ( select sanpham_fk, sum(soluong) as soluong from donhang_sanpham_chitiet where donhang_fk = '" + id+ "'  group by sanpham_fk ) b   "+
										 "\n on a.sanpham_fk = b.sanpham_fk   "+
										 "\n where a.donhang_fk ='" + id	+ "'  and a.soluong <> isnull(b.soluong,0)  ";  
								ResultSet rschk = db.get(query);
								if (rschk != null) {
									while (rschk.next()) {
										msg += "Lỗi:Số lượng kho tổng lệch với kho chi tiết trên đơn hàng!";
									}
									rschk.close();
								}
								if (msg.trim().length() > 0) {
									db.getConnection().rollback();
									return msg;
								}*/
								
								
								/*query = "\n select a.npp_fk, a.sanpham_fk, a.soluong, a.booked, a.available, b.soluong, b.booked, b.available from NHAPP_KHO a "+
										"\n full outer join (select sanpham_fk, kho_fk, npp_fk, kbh_fk, sum(soluong) as soluong, sum(booked) as booked, sum(available) as available  "+
										"\n from NHAPP_KHO_CHITIET b group by sanpham_fk, kho_fk, npp_fk, kbh_fk ) b on a.sanpham_fk = b.sanpham_fk "+
										"\n and a.npp_fk = b.npp_fk and a.kho_fk = b.kho_fk and a.kbh_fk = b.kbh_fk  "+
										"\n where (a.soluong <> b.soluong or a.booked <> b.booked or a.available <> b.available) and a.npp_fk = '"+nppId+"' ";  
								ResultSet rschkkho = db.get(query);
								
									while (rschkkho.next()) {
										msg += "Lỗi:Số lượng sản phẩm kho tổng lệch với kho chi tiết!";
									}
									rschkkho.close();
								
								if (msg.trim().length() > 0) {
									db.getConnection().rollback();
									return msg;
								}*/
								
								
							}
						}
						rsSQl.close();
					} else // tinh so luong san pham nhapp da chon, phai check
							// ton kho tung buoc
					{
						if (trakm.getHinhthuc() == 2) {
							String query = "select a.sanpham_fk as spId, c.MA as spMa, isnull(bgmnpp.dongia, '0') as dongia, isnull(b.TONGLUONG, 0) as tongluong "
									+ "from TRAKM_NHAPP a inner join TRAKHUYENMAI b on a.trakm_fk = b.PK_SEQ "
									+ " inner join SANPHAM c on a.sanpham_fk = c.PK_SEQ "
									+ " left join (  select sanpham_fk, GIAMUANPP as dongia  "
									+ "				from BGMUANPP_SANPHAM   "
									+ "				where BGMUANPP_FK in (  select top(1) a.PK_SEQ "
									+ "										from BANGGIAMUANPP a inner join BANGGIAMUANPP_NPP b on a.PK_SEQ = b.BANGGIAMUANPP_FK   "
									+ "										where a.TUNGAY <= '"+ tungay+ "' and b.NPP_FK = '"	+ nppId
									+ "' and a.KENH_FK = ( select kbh_fk from khachhang where pk_seq='"	+ khId
									+ "' ) and a.DVKD_FK = '100068' "
									+ "										order by a.TUNGAY desc  )   "
									+ ") bgmnpp on bgmnpp.sanpham_fk=a.sanpham_fk"
									+ " where a.ctkm_fk = '"+ ctkm.getId()
									+ "' and a.npp_fk = '"+ nppId
									+ "' and a.trakm_fk = '"+ trakm.getId()+ "' "
									+ "\n  and exists  "
									+ "\n  ( select 1 from nhapp_kho "
									+ "\n    where kho_fk= (select kho_fk from ctKhuyenMai where pk_seq='"+ ctkm.getId()
									+ "') and sanpham_fk=a.sanpham_fk "
									+ "\n  and npp_Fk='"+ nppId
									+ "' and KBH_FK=(SELECT KBH_FK from KhachHang where pk_Seq='"+ khId
									+ "' ) and AVAILABLE>0 ) "
									+ "  order by a.thutuuutien asc";

							System.out
									.println("5.Query tinh gia km npp chon truoc: "
											+ query);

							ResultSet spkm = db.get(query);

							String sp = "";
							String ma = "";
							String dg = "";
							double dongiaa = 0;
							String tg = "";
							while (spkm.next()) {
								sp += spkm.getString("spId") + ",";
								dg += spkm.getString("dongia") + ",";
								dongiaa = spkm.getDouble("dongia");
								tg += spkm.getString("tongluong") + ",";
								ma += spkm.getString("spMa") + ",";
							}

							String[] spId = sp.split(",");
							String[] dongia = dg.split(",");
							String[] tongluong = tg.split(",");
							String[] spMa = ma.split(",");

							// CheckTonKho nhung tra khuyen mai da duoc npp chon
							// truoc
							String[] arr = checkTonKhoTraKm(nppId, ctkm, khId,spId, dongia, tongluong, spMa, db);
							if (arr == null) // nhung san pham da chon truoc cua tra khuyen mai da het hang trong kho
							{
					
								str = "Số lượng những sản phẩm bạn chọn trước trong thiết lập sản phẩm trả khuyến mãi không đủ trong kho";
								System.out.println("Error: " + str + "\n");
								return str;
							} else {

								/*********************************************************************************/
								
								/*********************************************************************************/

								// luu tong gia tri o moi dong sanpham
								query = "Insert into donhang_ctkm_trakm(donhangId, ctkmId, trakmId, soxuat, tonggiatri, spMa,SANPHAM_FK, soluong) "
										+ " values('"+ id+ "','"+ ctkm.getId()+ "','"+ trakm.getId()+ "','"+ ctkm.getSoxuatKM()+ "','"+ arr[2]+ "', '"+ arr[3]+ "', '"+ arr[0]+ "', '"+ arr[1] + "')";
								System.out.println("6.Chen khuyen mai Npp chon truoc: "	+ query);

								if (!db.update(query)) {
					
									str = query;
									return str;
								}
/*
								// cap nhat kho
								query = " if ((select count(*) From DonHang where pk_seq='"+ id+ "' and TrangThai=0)=1)  "
										+ "Update nhapp_kho set available = available - '"+ arr[1].replaceAll(",", "")
										+ "', booked = booked + '"+ arr[1].replaceAll(",", "")
										+ "' where kho_fk = (select kho_fk from ctkhuyenmai where pk_seq = '"+ ctkm.getId()
										+ "') and npp_fk = '"+ nppId+ "' and sanpham_fk = '"+ arr[0]
										+ "' and kbh_fk = (select kbh_fk from khachhang where pk_seq = '"+ khId + "')";
								System.out.println("7.Cap nhat npp_kho: "+ query);
								if (!db.update(query)) {
									db.getConnection().rollback();
									str = query;
									return str;
								}
								/////////////////////////////////////////////////////////////
								geso.dms.center.util.Utility util = new geso.dms.center.util.Utility();
								query=  " select KHO_FK,SANPHAM_FK,KBH_FK,NGAYNHAPKHO,available, (select ngaynhap from donhang where pk_seq='"+id+"') as ngaynhap from NHAPP_KHO_CHITIET "+  
						    			" where NPP_FK ="+nppId+" and KBH_FK= (select kbh_fk from khachhang where pk_seq = '" + khId+ "')"  +
						    			" and KHO_FK=(select kho_fk from ctKhuyenMai where pk_seq='"+ctkm.getId()+"')  and SANPHAM_FK =  "+ arr[0]+
						    			" AND AVAILABLE >0  and NGAYNHAPKHO<=(select ngaynhap from donhang where pk_seq='"+id+"')"+
						    			" order by NGAYNHAPKHO,AVAILABLE ";
						    	ResultSet rssp=db.get(query);
						    	double soluongdenghi=Double.parseDouble(arr[1]) ;

						    	while(rssp.next() && soluongdenghi >0){
						    		double soluong_avai= rssp.getDouble("AVAILABLE");
						    		double soluongcapnhat=0;
						    		if (soluong_avai >soluongdenghi){
						    			soluongcapnhat= soluongdenghi;
						    			soluongdenghi=0;
						    		}else{
						    			soluongcapnhat =soluong_avai;
						    			soluongdenghi =soluongdenghi - soluong_avai;
						    		}

						    		String _khoid=rssp.getString("kho_fk");
						    		String _kbhid=rssp.getString("KBH_FK");
						    		String ngaynhapkho=rssp.getString("NGAYNHAPKHO");
						    		String ngaynhap=rssp.getString("ngaynhap");
						    		
						    		query = "Insert into donhang_ctkm_trakm_chitiet(donhangId, ctkmId, trakmId, soxuat, tonggiatri, spMa,SANPHAM_FK, soluong, ngaynhapkho) "
											+ " values('"+ id+ "','"+ ctkm.getId()+ "','"+ trakm.getId()+ "','"+ ctkm.getSoxuatKM()+ "','"+dongiaa*soluongcapnhat+ "', '"+ arr[3]+ "', '"+ arr[0]+ "', '"+soluongcapnhat + "','"+ngaynhapkho+"')";
									System.out.println("6.Chen khuyen mai Npp chon truoc: "	+ query);

									if (!db.update(query)) {
										db.getConnection().rollback();
										str = query;
										return str;
									}
									
									msg=util.Update_NPP_Kho_Sp(ngaynhap,id, "updae ĐƠN HÀNG_145453252 apkm", db, _khoid,arr[0], nppId, _kbhid, 0.0, soluongcapnhat, (-1)* soluongcapnhat, 0.0);
									if (msg.length() > 0)
									{
										db.getConnection().rollback();
										msg = "Lỗi kho khi tạo đơn hàng: " + msg;
										return msg;
									}	
									 
									String msg1=util.Update_NPP_Kho_Sp_Chitiet(ngaynhap, "update donhang DHID ctkm 23: "+id ,  db, _khoid, arr[0], nppId, _kbhid, ngaynhapkho, 0,soluongcapnhat, (-1)*soluongcapnhat, 0, 0);
									if (msg1.length()> 0) 
									{
										db.getConnection().rollback();
										msg=msg1;
										return msg1;
									}

						    	}
						    	rssp.close();
								
								if (soluongdenghi!=0){
									 
									msg=  "Số lượng đề xuất trong lô chi tiết của sản phẩm "+arr[3]+"  tới ngày (ngày cấu hình hóa đơn) đơn hàng không còn đủ, " +
											" vui lòng kiểm tra báo cáo ( xuất nhập tồn,tồn hiện tại) theo lô để biết thêm chi tiết ";
									db.getConnection().rollback();
									return msg;
									
								}*/
								//////////////////////////////////////////////////////////
								
							/*	query = "\n select * from donhang_sanpham a  "+
										 "\n full outer join   "+
										 "\n ( select sanpham_fk, sum(soluong) as soluong from donhang_sanpham_chitiet where donhang_fk = '" + id+ "'  group by sanpham_fk ) b   "+
										 "\n on a.sanpham_fk = b.sanpham_fk   "+
										 "\n where a.donhang_fk ='" + id	+ "'  and a.soluong <> isnull(b.soluong,0)  ";  
								ResultSet rschk = db.get(query);
								if (rschk != null) {
									while (rschk.next()) {
										msg += "Lỗi:Số lượng kho tổng lệch với kho chi tiết trên đơn hàng!";
									}
									rschk.close();
								}
								if (msg.trim().length() > 0) {
									db.getConnection().rollback();
									return msg;
								}*/
								
								
								/*query = "\n select a.npp_fk, a.sanpham_fk, a.soluong, a.booked, a.available, b.soluong, b.booked, b.available from NHAPP_KHO a "+
										"\n full outer join (select sanpham_fk, kho_fk, npp_fk, kbh_fk, sum(soluong) as soluong, sum(booked) as booked, sum(available) as available  "+
										"\n from NHAPP_KHO_CHITIET b group by sanpham_fk, kho_fk, npp_fk, kbh_fk ) b on a.sanpham_fk = b.sanpham_fk "+
										"\n and a.npp_fk = b.npp_fk and a.kho_fk = b.kho_fk and a.kbh_fk = b.kbh_fk  "+
										"\n where (a.soluong <> b.soluong or a.booked <> b.booked or a.available <> b.available) and a.npp_fk = '"+nppId+"' ";  
								ResultSet rschkkho = db.get(query);
								
									while (rschkkho.next()) {
										msg += "Lỗi:Số lượng sản phẩm kho tổng lệch với kho chi tiết!";
									}
									rschkkho.close();
							
								if (msg.trim().length() > 0) {
									db.getConnection().rollback();
									return msg;
								}*/
								
								/*
								 * query =
								 * "update Phanbokhuyenmai set DASUDUNG = DASUDUNG + "
								 * + arr[2].replaceAll(",", "") +
								 * " where ctkm_fk = '" + ctkm.getId() +
								 * "' and npp_fk = '" + nppId + "'";
								 * System.out.println
								 * ("9.Cap nhat ngan sach Phanbokhuyenmai: " +
								 * query); if (!db.update(query)) {
								 * db.getConnection().rollback(); str = query;
								 * return str; }
								 */
							}
						}
					}
				} else {
					if (trakm.getType() != 3)// tra tien, tra chiet khau
					{
						/*********************************************************************************/
						
						/*********************************************************************************/

						String query = "Insert into donhang_ctkm_trakm(donhangId, ctkmId, trakmId, soxuat, tonggiatri) "
								+ " values('"+ id + "','" + ctkm.getId() + "','" + trakm.getId() + "','"
								+ ctkm.getSoxuatKM() + "'," + tongtienTraKM + ")";
						System.out.println("10.Chen khuyen mai tien / ck: " + query);
						if (!db.update(query)) {
					
							str = query;
							return str;
						}
						
						query = "Insert into donhang_ctkm_trakm_chitiet(donhangId, ctkmId, trakmId, soxuat, tonggiatri) "
								+ " values('"+ id + "','" + ctkm.getId() + "','" + trakm.getId() + "','"
								+ ctkm.getSoxuatKM() + "'," + tongtienTraKM + ")";
						System.out.println("10.Chen khuyen mai tien / ck: " + query);
						if (!db.update(query)) {
			
							str = query;
							return str;
						}
					}
				}
			}

			/*
			 * String query =
			 * "Insert into donhang_ctkm_dkkm(donhangId, ctkmId, trakmId, soxuat, tonggiatri) "
			 * + " values('" + id + "','" + ctkm.getId() + "','" + trakm.getId()
			 * + "','" + ctkm.getSoxuatKM() + "'," + tongtienTraKM + ")";
			 * System.out.println("10.Chen khuyen mai tien / ck: " + query);
			 * if (!db.update(query)) { db.getConnection().rollback(); str =
			 * query; return str; }
			 */
			/*String query = "select (select count(distinct ctkm_fk) as ctkmid  from donhang_ctkm_dkkm a"
					+ " where donhang_fk  = "
					+ id
					+ "  ) as sl, (select count(distinct ctkmid) as ctkmid  from donhang_ctkm_trakm a"
					+ " where donhangid  = " + id + "  ) as sl2 ";
			ResultSet rs = db.get(query);
			while (rs.next()) {
				String msg1 = "";
				if (rs.getDouble("sl") < rs.getDouble("sl2")) {
					msg1 = "vui lòng check lại khuyến mãi ";
				}
				if (msg1.length() > 0) {
					db.getConnection().rollback();
					return msg1;
				}

			}*/
			
			
			int sodong = 0;
			String query = " select d.SANPHAM_FK from donhang_ctkm_dkkm a "+
					 "\n inner join DONHANG_CTKM_TRAKM b on a.donhang_fk = b.DONHANGID and a.ctkm_fk = b.CTKMID "+
					 "\n inner join donhang c on c.PK_SEQ = a.donhang_fk "+
					 "\n inner join sanpham e on e.ma = a.sanpham_fk "+
					 "\n inner join DIEUKIENKM_SANPHAM d on a.dkkm_fk = d.DIEUKIENKHUYENMAI_FK and e.PK_SEQ = d.SANPHAM_FK "+
					 "\n where d.sanpham_fk not in (select sanpham_fk from DONHANG_SANPHAM where donhang_fk = a.donhang_fk and donhang_fk = "+id+" ) and a.donhang_fk = "+id+" ";

			ResultSet rs = db.get(query);
			System.out
					.println("Check don hang sản phẩm mua  "+ query);
			
				while (rs.next()) {
					sodong = rs.getInt(1);
				}
				rs.close();
			
			if (sodong > 0) {
				db.getConnection().rollback();
				str = "đơn hàng lỗi do k có sản phẩm mua trong điều kiện khuyến mãi mà có trả km !";
				return str;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			
			return "Loi khi tao moi ctkm " + ctkm + ", " + e1.toString();
		}
		return str;
	}

	private String[] checkTonKhoTraKm(String nppId, ICtkhuyenmai ctkm,String khId, String[] spId, String[] dongia, String[] tongluong,String[] spma, dbutils db) {
		String[] kq = new String[4];

		String msg = "";
		try {
			for (int i = 0; i < spId.length; i++) {
				int slg = Integer.parseInt(tongluong[i]) * ctkm.getSoxuatKM();
				msg = checkTonkho(nppId, ctkm.getId(), khId, spId[i], "", slg,db);
				if (msg == "") // thoa ton kho
				{
					kq[0] = spId[i];
					kq[1] = Integer.toString(slg);
					kq[2] = Long.toString(Math.round(slg* Float.parseFloat(dongia[i])));
					// System.out.println("Don gia: " + spId[i] + "- dongia: " +
					// dongia[i] + " - Tong gia tri o buoc nay: " + kq[2] +
					// "\n");
					kq[3] = spma[i];

					return kq;
				}
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	private String CreateKhuyenmaiDhDxk(List<ICtkhuyenmai> ctkmList, String id,String nppId, long tongGtridh, String khId, dbutils db) {
		String str = "";
		try {

			db.update("SET TRANSACTION ISOLATION LEVEL SNAPSHOT;");
			db.update("SET LOCK_TIMEOUT 60000;");

			db.getConnection().setAutoCommit(false);

			// cap nhat lai so luong san pham khuyen mai cua tung chuong trinh
			// theo so xuat tuong ung
			String listCtkm = "";
			for (int i = 0; i < ctkmList.size(); i++) {
				ICtkhuyenmai ctkm = ctkmList.get(i);
				ITrakhuyenmai trakm = ctkm.getTrakhuyenmai().get(0);

				listCtkm += "'" + ctkm.getId() + "',";
				float tongtienTraKM = 0f;
				if (trakm.getType() != 3) {
					if (trakm.getType() == 1) // tra tien
						tongtienTraKM = ctkm.getSoxuatKM()* trakm.getTongtien();
					else
						// tra chiet khau
						tongtienTraKM = tongGtridh* (trakm.getChietkhau() / 100);

					ResultSet rsTt = db.get("select tonggiatri from donhang_ctkm_trakm where donhangId='"+ id+ "' and ctkmId='"+ ctkm.getId()+ "'");
					float tt = 0.0f;
					if (rsTt.next()) {
						tt = rsTt.getFloat("tonggiatri");
						rsTt.close();
					}

					String query = "update donhang_ctkm_trakm set soxuat = '"+ Integer.toString(ctkm.getSoxuatKM())	+ "', tonggiatri = " + tongtienTraKM+ " where donhangId = '" + id + "' and ctkmId = '"	+ ctkm.getId() + "'";
					if (!db.update(query)) {
						str = query;
						db.getConnection().rollback();
						return query;
					}
					
					query = "update donhang_ctkm_trakm_chitiet set soxuat = '"+ Integer.toString(ctkm.getSoxuatKM())	+ "', tonggiatri = " + tongtienTraKM+ " where donhangId = '" + id + "' and ctkmId = '"	+ ctkm.getId() + "'";
					if (!db.update(query)) {
						str = query;
						db.getConnection().rollback();
						return query;
					}

					/*
					 * String st
					 * ="update Phanbokhuyenmai set DASUDUNG = DASUDUNG - " +(
					 * tt - tongtienTraKM) + " where ctkm_fk='" + ctkm.getId() +
					 * "' and npp_fk='" + nppId + "'"; if (!db.update(st)){
					 * db.getConnection().rollback(); return st; }
					 */

				} else {
					String query = "select soxuat, tonggiatri, spMa, soluong from donhang_ctkm_trakm where donhangId = '"
							+ id+ "' and ctkmid = '"+ ctkm.getId()	+ "' and spMa is not null";
					System.out.println("Query lay gia tri km la: " + query	+ "\n");
					ResultSet rsSp = db.get(query);
					float tonggiatri = 0f;
					float tonggiatriNew = 0f;
					while (rsSp.next()) {
						int soxuat = rsSp.getInt("soxuat");
						String spMa = rsSp.getString("spMa");
						int soluong = rsSp.getInt("soluong");
						tonggiatri = rsSp.getFloat("tonggiatri");
						System.out.println("Tong gia tri buoc nay la: "	+ tonggiatri + "\n");

						int sx = ctkm.getSoxuatKM();
						// if (sx != soxuat)
						// {
						if (sx > soxuat)
							sx = soxuat;

						tonggiatriNew += (tonggiatri * sx) / soxuat;
						int soluongNew = (soluong * sx) / soxuat;
						query = "update donhang_ctkm_trakm set soxuat = '"+ Integer.toString(sx) + "', tonggiatri = "+ Float.toString(tonggiatriNew)
								+ ", soluong = '"+ Integer.toString(soluongNew)
								+ "' where donhangId = '" + id+ "' and ctkmid = '" + ctkm.getId()
								+ "' and spMa = '" + spMa.trim() + "'";
						System.out.println("Query update la: " + query + "\n");

						if (!db.update(query)) {
							str = query;
							db.getConnection().rollback();
							return str;
						}

						/*
						 * String st
						 * ="update Phanbokhuyenmai set DASUDUNG = DASUDUNG - "
						 * + (tonggiatri - tonggiatriNew) + " where ctkm_fk='" +
						 * ctkm.getId() + "' and npp_fk='" + nppId + "'";
						 * if (!db.update(st)){ db.getConnection().rollback();
						 * return st; }
						 */
					}
					
					query = "select soxuat, tonggiatri, spMa, soluong, ngaynhapkho from donhang_ctkm_trakm_chitiet where donhangId = '"
							+ id+ "' and ctkmid = '"+ ctkm.getId()	+ "' and spMa is not null";
					System.out.println("Query lay gia tri km la: " + query	+ "\n");
					rsSp = db.get(query);
					tonggiatri = 0f;
					tonggiatriNew = 0f;
					while (rsSp.next()) {
						int soxuat = rsSp.getInt("soxuat");
						String spMa = rsSp.getString("spMa");
						int soluong = rsSp.getInt("soluong");
						tonggiatri = rsSp.getFloat("tonggiatri");
						String ngaynhapkho = rsSp.getString("ngaynhapkho");
						System.out.println("Tong gia tri buoc nay la: "	+ tonggiatri + "\n");

						int sx = ctkm.getSoxuatKM();
						// if (sx != soxuat)
						// {
						if (sx > soxuat)
							sx = soxuat;

						tonggiatriNew += (tonggiatri * sx) / soxuat;
						int soluongNew = (soluong * sx) / soxuat;
						query = "update donhang_ctkm_trakm_chitiet set soxuat = '"+ Integer.toString(sx) + "', tonggiatri = "+ Float.toString(tonggiatriNew)
								+ ", soluong = '"+ Integer.toString(soluongNew)
								+ "' where donhangId = '" + id+ "' and ctkmid = '" + ctkm.getId()
								+ "' and spMa = '" + spMa.trim() + "' and ngaynhapkho = '" + ngaynhapkho + "' ";
						System.out.println("Query update la: " + query + "\n");

						if (!db.update(query)) {
							str = query;
							db.getConnection().rollback();
							return str;
						}

						/*
						 * String st
						 * ="update Phanbokhuyenmai set DASUDUNG = DASUDUNG - "
						 * + (tonggiatri - tonggiatriNew) + " where ctkm_fk='" +
						 * ctkm.getId() + "' and npp_fk='" + nppId + "'";
						 * if (!db.update(st)){ db.getConnection().rollback();
						 * return st; }
						 */
					}
					rsSp.close();
				}
			}
			if (listCtkm.length() > 0) {
				listCtkm = listCtkm.substring(0, listCtkm.length() - 1);
				ResultSet rsTt = db.get("select ctkmId, tonggiatri from donhang_ctkm_trakm where donhangId = '"+ id + "' and ctkmId not in (" + listCtkm + ")");
				while (rsTt.next()) {
					/*
					 * String st
					 * ="update Phanbokhuyenmai set DASUDUNG = DASUDUNG - " +
					 * rsTt.getDouble("tonggiatri") + " where ctkm_fk='" +
					 * rsTt.getString("ctkmId") + "' and npp_fk='" + nppId +
					 * "'"; if (!db.update(st)){ db.getConnection().rollback();
					 * return st; }
					 */

				}
				rsTt.close();

				// Xoa nhung ctkm khong con duoc huong
				String st = "delete from donhang_ctkm_trakm where donhangId = '"+ id + "' and ctkmId not in (" + listCtkm + ")";
				if (!db.update(st)) {
					db.getConnection().rollback();
					return st;
				}
				
				st = "delete from donhang_ctkm_trakm_chitiet where donhangId = '"+ id + "' and ctkmId not in (" + listCtkm + ")";
				if (!db.update(st)) {
					db.getConnection().rollback();
					return st;
				}
				// System.out.println("Cau lenh xoa nhung ctkm khong thoa la: delete from donhang_ctkm_trakm where donhangId = '"
				// + id + "' and ctkmId not in (" + listCtkm + ")");
			} else // khong con ctkm nao thoa
			{
				String msg = "";
				capNhatKM(id, nppId, khId, "3", db);
				if (msg.length() > 0)// 3
				{
					db.getConnection().rollback();
					return "Khong The Cap Nhat Khuyen Mai Cua Don Hang: " + id+ "Error:" + msg;
				}
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e1) {

			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return "Có lỗi trong quá trình cập nhật lại khuyến mại của đơn hàng, vui lòng thử lại sau..."+ e1.getMessage();

		}
		return str;
	}

	private String checkTonkho(String nppId, String ctkmId, String khId,
			String spId, String spMa, int slg, dbutils db) {
		// kiem tra trong kho khuyen mai con du san pham hay khong, khong du thi
		// thoat
		String query = "select available from nhapp_kho where kho_fk = (select kho_fk from ctkhuyenmai where pk_seq = '"+ ctkmId+ "') and npp_fk = '"
				+ nppId	+ "' and sanpham_fk = '"+ spId+ "' and kbh_fk = (select kbh_fk from khachhang where pk_seq = '"+ khId + "')";

		ResultSet rsCheck = db.get(query);
		/* if (rsCheck != null) */
		{
			try {
				boolean flag = false;

				while (rsCheck.next()) {
					if (rsCheck.getString("available") != null) {
						flag = true;
						String avai = rsCheck.getString("available");
						if (Integer.parseInt(avai) < slg) {
							return "Sản phẩm khuyến mại " + spMa+ " - Còn tối đa " + avai;
						}
					}
				}
				rsCheck.close();
				if (flag == false) // khong co dong du lieu nao, Resualset van
									// khac null
				{
					// System.out.println("San pham khuyen mai " + spMa +
					// " --- Con toi da 0 \n");
					return "San pham khuyen mai " + spMa + " - Con toi da 0";
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "error";
			}
		}

		return "";
	}

	String CheckTonKho(String id, dbutils db) {
		String msg = "";
		String query = " select kho.ten as khoTEN,sp.ma as spMa,sp.Ten as spTen ,nhapp_kho.SoLuong, data.SoLuong as BOOKED,nhapp_kho.AVAILABLE  "
				+ "  from	"
				+ " (  SELECT DH.NPP_FK,DH.KBH_FK,CTKM.KHO_FK,SP.PK_SEQ AS sanpham_fk,SUM(KM.SOLUONG) AS SOLUONG  "
				+ "	FROM DONHANG_CTKM_TRAKM   "
				+ "		KM INNER JOIN DONHANG DH  ON DH.PK_SEQ=KM.DONHANGID "
				+ "		INNER JOIN CTKHUYENMAI CTKM ON CTKM.PK_SEQ=KM.CTKMID "
				+ "		INNER JOIN SANPHAM SP ON SP.MA=KM.SPMA  "
				+ "	WHERE DH.TRANGTHAI=0 AND DH.PK_SEQ='"+ id+ "'  "
				+ "	GROUP BY DH.NPP_FK,DH.KBH_FK,CTKM.KHO_FK,SP.PK_SEQ  ) as data "
				+ "   inner join nhapp_kho nhapp_kho on  nhapp_kho.npp_fk=data.npp_fk and nhapp_kho.sanpham_fk=data.sanpham_fk and nhapp_kho.kho_fk=data.kho_fk "
				+ "  and nhapp_kho.kbh_fk=data.kbh_fk "
				+ "   inner join sanpham sp on sp.pk_Seq =data.sanpham_fk "
				+ "  inner join kho kho on kho.pk_Seq=data.kho_Fk "
				+ "   where nhapp_kho.available <data.SoLuong  ";

		ResultSet spRs = db.get(query);
		System.out.println("[DONHANG_CTKM_TRAKM]" + query);
		try {
			while (spRs.next()) {
				String spMa = spRs.getString("spMa");
				String spTen = spRs.getString("spTen");
				String khoTen = spRs.getString("khoTEN");
				double soluong = spRs.getDouble("AVAILABLE");
				double KM = spRs.getDouble("BOOKED");
				msg += "Sáº£n pháº©m ["+ spMa+ " - "+ spTen	+ "  ]  kho ["+ khoTen+ "] tồn kho không đủ, tồn hiện tại nhỏ hơn lượng được KM ["
						+ soluong + "] < [" + KM + "] \n";
			}
			spRs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			msg = "Exception (query ) " + query + "  " + e.getMessage();
		}
		
		String query_ct = " select kho.ten as khoTEN,sp.ma as spMa,sp.Ten as spTen ,nhapp_kho.SoLuong, data.SoLuong as BOOKED,nhapp_kho.AVAILABLE  "
				+ "  from	"
				+ " (  SELECT DH.NPP_FK,DH.KBH_FK,CTKM.KHO_FK,SP.PK_SEQ AS sanpham_fk,SUM(KM.SOLUONG) AS SOLUONG, km.ngaynhapkho  "
				+ "	FROM DONHANG_CTKM_TRAKM_chitiet   "
				+ "		KM INNER JOIN DONHANG DH  ON DH.PK_SEQ=KM.DONHANGID "
				+ "		INNER JOIN CTKHUYENMAI CTKM ON CTKM.PK_SEQ=KM.CTKMID "
				+ "		INNER JOIN SANPHAM SP ON SP.MA=KM.SPMA  "
				+ "	WHERE DH.TRANGTHAI=0 AND DH.PK_SEQ='"+ id+ "'  "
				+ "	GROUP BY DH.NPP_FK,DH.KBH_FK,CTKM.KHO_FK,SP.PK_SEQ, km.ngaynhapkho  ) as data "
				+ "   inner join nhapp_kho_chitiet nhapp_kho on  nhapp_kho.npp_fk=data.npp_fk and nhapp_kho.sanpham_fk=data.sanpham_fk and nhapp_kho.kho_fk=data.kho_fk and nhapp_kho.ngaynhapkho =data.ngaynhapkho "
				+ "  and nhapp_kho.kbh_fk=data.kbh_fk "
				+ "   inner join sanpham sp on sp.pk_Seq =data.sanpham_fk "
				+ "  inner join kho kho on kho.pk_Seq=data.kho_Fk "
				+ "   where nhapp_kho.available <data.SoLuong    ";

		spRs = db.get(query_ct);
		System.out.println("[DONHANG_CTKM_TRAKM_chitiet]" + query_ct);
		try {
			while (spRs.next()) {
				String spMa = spRs.getString("spMa");
				String spTen = spRs.getString("spTen");
				String khoTen = spRs.getString("khoTEN");
				double soluong = spRs.getDouble("AVAILABLE");
				double KM = spRs.getDouble("BOOKED");
				msg += "Sáº£n pháº©m ["+ spMa+ " - "+ spTen	+ "  ]  kho ["+ khoTen+ "] tồn kho chi tiết không đủ, tồn hiện tại nhỏ hơn lượng được KM ["
						+ soluong + "] < [" + KM + "] \n";
			}
			spRs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			msg = "Exception (query ) " + query + "  " + e.getMessage();
		}
		return msg;
	}

	// XÃ³a KM trong Ä‘Æ¡n hÃ ng
	private String XoaKM(String dhId, dbutils db) {
		String msg = "";
	/*	String query = "delete from DonHang_CTKM_TRAKM WHERE DONHANGID='"+ dhId + "' ";
		if (!db.update(query)) {
			msg = "Lá»—i xÃ³a KM " + query;
		}*/
		return msg;
	}

	private String capNhatKM(String id, String nppId, String khId, String trangthai, dbutils db) {
		String msg = "";
		String query= "";
		geso.dms.center.util.Utility util = new geso.dms.center.util.Utility();
		
		/*
		String query = "SELECT DH.NGAYNHAP,SP.PK_SEQ AS SANPHAM_FK,CTKM.KHO_FK,DH.NPP_FK,DH.KBH_FK,KM.SOLUONG "
			+ "	FROM DONHANG_CTKM_TRAKM   "
			+ "		KM INNER JOIN DONHANG DH  ON DH.PK_SEQ=KM.DONHANGID "
			+ "		INNER JOIN CTKHUYENMAI CTKM ON CTKM.PK_SEQ=KM.CTKMID "
			+ "		INNER JOIN SANPHAM SP ON SP.MA=KM.SPMA  "
			+ "	WHERE DH.TRANGTHAI=0 AND DH.PK_SEQ='"+ id+ "'  "
			+ "	GROUP BY DH.NPP_FK,DH.KBH_FK,CTKM.KHO_FK,SP.PK_SEQ, DH.NGAYNHAP, KM.SOLUONG ";

		System.out.println("UPDATE NPP KHO: " + query  );
		ResultSet ckRs = db.get(query);
		try {
			while(ckRs.next())
			{
				String kho_fk=ckRs.getString("kho_fk");
				String kenh =ckRs.getString("kbh_fk");
				String sanpham_fk=ckRs.getString("sanpham_fk");
				String ngaynhap  =ckRs.getString("ngaynhap");
				//String tensp  =ckRs.getString("tensp");
				Double soluong = ckRs.getDouble("soluong");


				msg=util.Update_NPP_Kho_Sp(ngaynhap,id, "SPKM_1454552", db, kho_fk, sanpham_fk, nppId, kenh, 0.0, (-1)* soluong, soluong, 0.0);
				if (msg.length() > 0)
				{
					db.getConnection().rollback();
					msg = "Lỗi kho khi tạo đơn hàng: " + msg;
					return msg;
				}		
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}				    


		String	query_ct=	 "SELECT DH.NGAYNHAP,SP.PK_SEQ AS SANPHAM_FK,CTKM.KHO_FK,DH.NPP_FK,DH.KBH_FK,KM.SOLUONG, km.ngaynhapkho "
			+ "	FROM DONHANG_CTKM_TRAKM_chitiet   "
			+ "		KM INNER JOIN DONHANG DH  ON DH.PK_SEQ=KM.DONHANGID "
			+ "		INNER JOIN CTKHUYENMAI CTKM ON CTKM.PK_SEQ=KM.CTKMID "
			+ "		INNER JOIN SANPHAM SP ON SP.MA=KM.SPMA  "
			+ "	WHERE DH.TRANGTHAI=0 AND DH.PK_SEQ='"+ id+ "'  "
			+ "	GROUP BY DH.NPP_FK,DH.KBH_FK,CTKM.KHO_FK,SP.PK_SEQ,DH.NGAYNHAP,KM.SOLUONG, km.ngaynhapkho   ";

		System.out.println("UPDATE NPP KHO: " + query_ct  );
		ckRs = db.get(query_ct);
		try {
			while(ckRs.next())
			{
				String kho_fk=ckRs.getString("kho_fk");
				String kenh =ckRs.getString("kbh_fk");
				String sanpham_fk=ckRs.getString("sanpham_fk");
				String ngaynhap  =ckRs.getString("ngaynhap");
				//String tensp  =ckRs.getString("tensp");
				Double soluong = ckRs.getDouble("soluong");
				String ngaynhapkho=ckRs.getString("NGAYNHAPKHO");
				String msg1=util.Update_NPP_Kho_Sp_Chitiet(ngaynhap, "update donhang DHID: "+id ,  db, kho_fk, sanpham_fk, nppId, kenh, ngaynhapkho, 0,(-1)*soluong, soluong, 0, 0);
				if (msg1.length()> 0) 
				{
					msg=msg1;
					return msg;
				}
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}				
		*/

		String query_ct = "";
		query = "delete from DonHang_CTKM_TRAKM WHERE DONHANGID = '" + id + "' and isnull(ap_dung,0) != 1  ";
		System.out.println("in ra xem nào ___"+query);
		if (!db.update(query)) {
			msg = "Lỗi xóa KM 1.";
		}
		
		query = "delete from donhang_ctkm_dkkm WHERE donhang_fk = '" + id + "'   ";
		System.out.println("in ra xem nào ___"+query);
		if (!db.update(query)) {
			msg = "Lỗi xóa KM 1.";
		}
		
		query_ct = "delete from DonHang_CTKM_TRAKM_chitiet WHERE DONHANGID='" + id	+ "' and isnull(ap_dung,0) != 1  ";
		
		if (!db.update(query_ct)) {
			msg = "Lỗi xóa KM 2.";
		}

		/*
		query = "select count(*) as sl "
		+ " from NHAPP_KHO kho left join v_Booked book "
		+ "			on kho.NPP_FK = book.npp_fk and kho.KHO_FK = book.kho_fk and kho.KBH_FK = book.kbh_fk and kho.SANPHAM_FK = book.sanpham_fk "
		+ " where kho.NPP_FK = '" + nppId
		+ "' and kho.BOOKED != ISNULL(book.soluong, 0) ";
		if (id.trim().length() > 0)
			query += " and kho.sanpham_fk in ( select sanpham_fk from DONHANG_SANPHAM where DONHANG_FK = '"	+ id+ "' "
					+ "union select b.PK_SEQ from DONHANG_CTKM_TRAKM a inner join SANPHAM b on a.SPMA = b.MA where a.DONHANGID = '"	+ id + "' ) ";

		ResultSet rs = db.get(query);
		try {
			rs.next();
			int sl = rs.getInt("sl");
			rs.close();
			if (sl > 0) {
				query = "update kho set kho.BOOKED = isnull(book.soluong, 0), "
						+ " kho.AVAILABLE = case when kho.SOLUONG >= isnull(book.soluong, 0) then kho.SOLUONG - isnull(book.soluong, 0) else 0 end "
						+ " from NHAPP_KHO kho left join v_Booked book "
						+ "			on kho.NPP_FK = book.npp_fk and kho.KHO_FK = book.kho_fk and kho.KBH_FK = book.kbh_fk and kho.SANPHAM_FK = book.sanpham_fk "
						+ " where kho.NPP_FK = '" + nppId
						+ "' and kho.BOOKED != ISNULL(book.soluong, 0) ";
				if (id.trim().length() > 0)
					query += " and kho.sanpham_fk in ( select sanpham_fk from DONHANG_SANPHAM where DONHANG_FK = '"	+ id+ "' "
							+ "union select b.PK_SEQ from DONHANG_CTKM_TRAKM a inner join SANPHAM b on a.SPMA = b.MA where a.DONHANGID = '"	+ id + "' ) ";

				System.out.println("---FIX BOOKED TONG:: " + query);
				if (!db.update(query)) {
					return "1.2.Lỗi cập nhật NHAPP_KHO " + query;
				}

			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		*/
		
		return msg;
	}

	boolean kiemtraDKKM(String donhangId, String ctkm, dbutils db) {
		String sql = "select a.sanpham_fk,a.soluong,a.batbuoc from dieukienkm_sanpham a,ctkm_dkkm b where b.dkkhuyenmai_fk = a.dieukienkhuyenmai_fk and a.batbuoc = 1 and b.ctkhuyenmai_fk ='"
				+ ctkm + "'";
		// System.out.println(sql);
		ResultSet rs = db.get(sql);
		try {
			while (rs.next()) {
				sql = "select count(*) as num from donhang_sanpham where sanpham_fk ='"	+ rs.getString("sanpham_fk")
						+ "' and soluong >='"+ rs.getString("soluong")+ "' and donhang_fk='"+ donhangId + "'";
				// System.out.println(sql);
				ResultSet tb = db.get(sql);
				tb.next();
				if (tb.getString("num").equals("0"))
					return false;
			}
			rs.close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	private String getDateTimeNow() {
		String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		String name = sdf.format(cal.getTime());
		return name;
	}

	private boolean checkNgayKhoaSo(String ngayksgn, String ngaygd) {
		if (ngayksgn.equals(""))
			ngayksgn = this.getDateTime();

		String[] ngayks = ngayksgn.split("-");
		String[] ngaydh = ngaygd.split("-");

		Calendar c1 = Calendar.getInstance(); // new GregorianCalendar();
		Calendar c2 = Calendar.getInstance(); // new GregorianCalendar();

		c1.set(Integer.parseInt(ngayks[0].trim()), Integer.parseInt(ngayks[1].trim()) - 1,Integer.parseInt(ngayks[2].trim()));
		c2.set(Integer.parseInt(ngaydh[0].trim()), Integer.parseInt(ngaydh[1].trim()) - 1,Integer.parseInt(ngaydh[2].trim()));

		long songay = (c2.getTime().getTime() - c1.getTime().getTime())	/ (24 * 3600 * 1000);

		if (songay >= 1)
			return true;
		return false;
	}

	public static String CheckNghanSach(String ctkmId, String nppId, String khId, Idbutils db) {




		String sql = " select isnull(LOAINGANSACH,0)LOAINGANSACH,level_phanbo,phanbotheodonhang from CTKHUYENMAI where pk_seq = '"+ ctkmId + "' ";
		String mucphanbo = "";
		int phanbotheodonhang = 0;
		int LOAINGANSACH = 0;
		ResultSet rs = db.get(sql);
		if (rs != null) {
			try {
				if (rs.next()) {
					mucphanbo = rs.getString("level_phanbo");
					phanbotheodonhang = rs.getInt("phanbotheodonhang");
					LOAINGANSACH =  rs.getInt("LOAINGANSACH");
				}
				rs.close();
			} catch (Exception e) {
			}

		}
		if(LOAINGANSACH == 0 )
			return "";
		//phanbotheodonhang  0: gia_tri_su_dung : tiền 
		//phanbotheodonhang  0: gia_tri_su_dung : suất

		if (mucphanbo.equals("0")) 
		{

			sql =   "\n select a.ngansach,a.ctkm_fk, b.scheme, b.phanbotheodonhang, a.ngansach" +
					"\n		,  case when b.phanbotheodonhang =0 then isnull(ss.tonggiatri,0)  else isnull(ss.soxuat,0) end as dasudung  " +
					"\n from phanbokhuyenmai a " +
					"\n inner join ctkhuyenmai b on a.ctkm_fk = b.pk_seq " +
					"\n outer apply " +
					"\n (	" +
					"\n		select sum(soxuat)soxuat, sum(tonggiatri)tonggiatri " +
					"\n		from  " +
					"\n 	(" +
					"\n			select dhkm.donhangId,max(soxuat) soxuat, sum(dhkm.tonggiatri) tonggiatri " +
					"\n			from donhang_ctkm_trakm dhkm inner join donhang dh on dh.pk_seq = dhkm.donhangId " +
					"\n			where dh.trangthai !=2 and dh.npp_fk =  a.npp_fk and dhkm.ctkmid = a.ctkm_fk and not exists ( select 1 from donhangtrave where trangthai = 3 and donhang_fk = dh.pk_seq) " +
					"\n			group by dhkm.donhangId " +
					"\n 	)ss " +
					"\n )ss	" +
					"\n where a.npp_fk = '"+ nppId	+ "' and a.ctkm_fk = '"+ ctkmId+ "'  ";


		} else {


			sql =   "\n select a.ngansach,a.ctkm_fk, b.scheme, b.phanbotheodonhang, a.ngansach" +
			"\n		,  case when b.phanbotheodonhang =0 then isnull(ss.tonggiatri,0)  else isnull(ss.soxuat,0) end as dasudung  " +
			"\n from phanbokhuyenmai a " +
			"\n inner join ctkhuyenmai b on a.ctkm_fk = b.pk_seq " +
			"\n outer apply " +
			"\n (	" +
			"\n		select sum(soxuat)soxuat, sum(tonggiatri)tonggiatri " +
			"\n		from  " +
			"\n 	(" +
			"\n			select dhkm.donhangId,max(soxuat) soxuat, sum(dhkm.tonggiatri) tonggiatri " +
			"\n			from donhang_ctkm_trakm dhkm inner join donhang dh on dh.pk_seq = dhkm.donhangId " +
			"\n			where dh.trangthai !=2 and dh.khachhang_fk =  a.khachhang_fk and dhkm.ctkmid = a.ctkm_fk and not exists ( select 1 from donhangtrave where trangthai = 3 and donhang_fk = dh.pk_seq) " +
			"\n			group by dhkm.donhangId " +
			"\n 	)ss " +
			"\n )ss	" +
			"\n where a.khachhang_fk = '"+ khId+ "' and a.ctkm_fk = '"+ ctkmId+ "'  ";
			
			

		}

		rs = db.get(sql);
		String scheme = "";
		double ngansach = 0;
		try {
			double conlai = 0.0f;
			if (rs.next()) {
				ngansach = rs.getDouble("ngansach");
				scheme = rs.getString("scheme");
				conlai = ngansach	-  rs.getDouble("DASUDUNG");
				System.out.println("ngansach: " + rs.getString("ngansach")+ ", dasudung: " + rs.getString("DASUDUNG")+ ", conlai: " + conlai);
				rs.close();
			}

			if(ngansach <=0)
			{
				return "101.Chương trình khuyến mại: " + scheme	+ ", đã hết ngân sách phân bổ";
			}
			if (conlai < 0 ) {
				
				
				
				return "101.Chương trình khuyến mại: " + scheme	+ ", đã hết ngân sách phân bổ";
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out
			.println("__EXCEPTION CHECK NGAN SACH: " + e.getMessage());
			return "201.Chương trình khuyến mại: " + scheme	+ ", đã hết ngân sách phân bổ";
		}
		return "";
	}
	
	
	
	public String  Luu_Khuyen_mai_Khong_dieu_Chinh(IDonhang dhBean,List<ICtkhuyenmai> ctkmResual,String jsoStrDaSuDung,String dhId,String 	nppId,long tonggiatriKm,String khId, Hashtable<String, Integer> sanpham_soluong,Hashtable<String, Float> sanpham_dongia, dbutils db, String ngay_giao_dich  )
	{
		try
		{

			db.getConnection().setAutoCommit(false);
			String msg = "";
			for (int i = 0; i < ctkmResual.size(); i++) {
				ICtkhuyenmai ctkhuyenmai = ctkmResual.get(i);

				System.out.println("ConFi laf: "+ ctkhuyenmai.getConfirm());
				if (ctkhuyenmai.getConfirm() == false) // khong dung chung san pham
				{
					msg = CreateKhuyenmai(ctkhuyenmai, dhId,	nppId, tonggiatriKm, khId,sanpham_soluong,sanpham_dongia, db, ngay_giao_dich);
					if(msg.trim().length() > 0)
					{
						db.getConnection().rollback();
						db.getConnection().setAutoCommit(true);
						return msg;
					}
					// remove khoi danh sach
					ctkmResual.remove(i);
					i = i - 1;

				}

			}
			
			String query  = "";

			JsonArray json_da_su_dung = (JsonArray) new JsonParser().parse(jsoStrDaSuDung);
			if( ctkmResual.size() > 0 && ( json_da_su_dung == null || json_da_su_dung.size() <=0) )
			{
				msg = "Lỗi bảo mật hệ thống 2!";
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return msg;
			}
			if( ctkmResual.size() > 0 )
			for(int xxx = 0 ; xxx < json_da_su_dung.size(); xxx++)
			{ 
				JsonObject rec = (JsonObject) json_da_su_dung.get(xxx);
				String ctkm_fk= rec.get("ctkm_fk").getAsString();
				String dkkm_fk= rec.get("dkkm_fk").getAsString();
				String sanpham_fk= rec.get("sanpham_fk").getAsString();
				String soxuat= rec.get("soxuat").getAsString();
				String soluongmua= rec.get("soluongmua").getAsString();
				query = "Insert into DONHANG_CTKM_DKKM( ctkm_fk, dkkm_fk, soxuat, sanpham_fk, soluongmua, donhang_fk) " + 
				" values('" + ctkm_fk + "','" + dkkm_fk + "','" + soxuat + "',"+sanpham_fk+", " + soluongmua + ", '"+dhId+"')";
				if(db.updateReturnInt(query)<=0)
				{
					System.out.println("query = "+ query);
					msg = "Lỗi ghi nhận sản phẩm  sử dụng !";
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					return msg;
				}
			}
			
			
			// check ngansach
			 query =  "\n select ctkmId,max(soxuat) soxuat ,sum(tonggiatri)  tonggiatri " +
			"\n from donhang_ctkm_trakm dhkm " +
			"\n where donhangId = " + dhId + 
			"\n group by ctkmId  ";
			ResultSet rs= db.get(query);
			while(rs.next())
			{
				String ctkmId = rs.getString("ctkmId");
				String checkmsg = CheckNghanSach(ctkmId,nppId,  khId,  db) ;

				if (checkmsg.trim().length() > 0) {

					Xoa_Khuyen_Mai( db ,  dhId,  ctkmId)	;	
				}
				 msg += msg.trim().length() > 0 ?  ", " + checkmsg : checkmsg ;
			}

			
			
			
			String msgUp = Utility.Update_GiaTri_DonHang(dhId, db);
			if(msg.length() > 0)
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return msgUp;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return msg;
		}
		catch (Exception e) {
			e.printStackTrace();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			
			return "Exception : " + e.getMessage();
		}

	}

	public static String Xoa_Khuyen_Mai(Idbutils db , String dhId, String ctkmId)
	{
		String query = " delete donhang_ctkm_trakm where donhangid = "+dhId+" and ctkmId = "+ctkmId;
		db.update(query);
		query = " delete donhang_ctkm_dkkm where donhang_fk = "+dhId+" and ctkm_fk = "+ctkmId;
		db.update(query);	
		return "";
	}

}
