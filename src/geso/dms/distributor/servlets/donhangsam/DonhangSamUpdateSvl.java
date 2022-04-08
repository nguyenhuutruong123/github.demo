package geso.dms.distributor.servlets.donhangsam;

import geso.dms.center.beans.dieuchuyenkhuyenmai.IDieuchuyenkhuyenmai;
import geso.dms.distributor.beans.ctkhuyenmai.imp.XLkhuyenmai;
import geso.dms.distributor.beans.ctkhuyenmai.imp.XLkhuyenmaiDonhangDXK;
import geso.dms.distributor.beans.ctkhuyenmai.ICtkhuyenmai;
import geso.dms.distributor.beans.dieukienkhuyenmai.IDieukienkhuyenmai;
import geso.dms.distributor.beans.donhangsam.*;
import geso.dms.distributor.beans.donhangsam.imp.*;
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

public class DonhangSamUpdateSvl extends javax.servlet.http.HttpServlet implements
		javax.servlet.Servlet {
	private static final long serialVersionUID = 1L;

	PrintWriter out;

	public DonhangSamUpdateSvl() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session
				.getAttribute("util");
		if (!cutil.check(userId, userTen, sum)) {
			response.sendRedirect("/AHF/index.jsp");
		} else {
			session.setMaxInactiveInterval(30000);

			this.out = response.getWriter();
			Utility util = new Utility();

			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);

			out.println(userId);

			if (userId.length() == 0)
				userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

			String id = util.getId(querystring);
			IDonhangsam dhBean = new Donhangsam(id);
			dhBean.setUserId(userId); // phai co UserId truoc khi Init
			dhBean.init();

			dhBean.setKhId(dhBean.getKhId());
			session.setAttribute("bgstId", dhBean.getBgstId());
			session.setAttribute("khoId", dhBean.getKhoId());
			String nextJSP;

			if (request.getQueryString().indexOf("display") >= 0) {
				// dhBean.CreateSpDisplay();
				nextJSP = "/AHF/pages/Distributor/DonHangSamDisplay.jsp";
			} else {
				nextJSP = "/AHF/pages/Distributor/DonHangSamUpdate.jsp";
			}

			session.setAttribute("dhBean", dhBean);
			session.setAttribute("khId", dhBean.getKhId());
			session.setAttribute("ddkdId", dhBean.getDdkdId());
			session.setAttribute("ngaydonhang", dhBean.getNgaygiaodich());
			// bo sung them de do phai truy xuat csdl khi nhan sanpham
			session.setAttribute("nppId", dhBean.getNppId());

			response.sendRedirect(nextJSP);
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");

		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session
				.getAttribute("util");
		if (!cutil.check(userId, userTen, sum)) {
			response.sendRedirect("/AHF/index.jsp");
		} else {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			session.setMaxInactiveInterval(30000);

			this.out = response.getWriter();
			dbutils db = new dbutils();
			IDonhangsam dhBean;
			Utility util = new Utility();
			String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
			if (id == null) {
				dhBean = new Donhangsam("");
			} else {
				dhBean = new Donhangsam(id);
			}

			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
			dhBean.setUserId(userId);

			String nppId = util
					.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
			if (nppId == null)
				nppId = "";
			dhBean.setNppId(nppId);

			String gsbhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("gsbhId")));
			if (gsbhId == null)
				gsbhId = "0";
			dhBean.setGsbhId(gsbhId);

			String ngaygd = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
			if (ngaygd == null || ngaygd == "")
				ngaygd = this.getDateTime();
			dhBean.setNgaygiaodich(ngaygd);

			session.setAttribute("ngaydonhang", ngaygd);

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
			session.setAttribute("ddkdId", ddkdId);

			String khoId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khoTen")));
			if (khoId == null)
				khoId = "";
			dhBean.setKhoId(khoId);

			String ngaygh = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaygh")));
			if (ngaygh == null)
				ngaygh = "";
			dhBean.setNgaygiaohang(ngaygh);

			String khId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khId")));
			if (khId == null)
				khId = "";

			dhBean.setKhId(khId);
			session.setAttribute("khId", khId);
			String smartId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("smartId")));
			if (smartId == null)
				smartId = "";
			dhBean.setSmartId(smartId);

			String khTen = util
					.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khTen")));
			if (khTen == null)
				khTen = "";
			dhBean.setKhTen(khTen);
			String nvgnId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nvgnId")));
			if (nvgnId == null)
				nvgnId = "";
			dhBean.setnvgnId(nvgnId);


			String muctindung = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("muctindung")));
			if (muctindung == null)
				muctindung = "0";
			dhBean.setMuctindung(muctindung);

			String chietkhau = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ChietKhau")));
			if (chietkhau == null)
				chietkhau = "";
			else
				chietkhau = chietkhau.replaceAll(",", "");
			dhBean.setChietkhau(chietkhau);

			String tongtientruocVAT = util.antiSQLInspection(request
					.getParameter("SoTienChuaVAT"));
			if (tongtientruocVAT == null)
				tongtientruocVAT = "0";
			else
				tongtientruocVAT = tongtientruocVAT.replaceAll(",", "");
			dhBean.setTongtientruocVAT(tongtientruocVAT); // tien chi gom soluong * dongia --> de ap khuyen mai
															

			String tongtiensauVAT = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("SoTienCoVAT")));
			if (tongtiensauVAT == null)
				tongtiensauVAT = "0";
			else
				tongtiensauVAT = tongtiensauVAT.replaceAll(",", "");
			dhBean.setTongtiensauVAT(tongtiensauVAT);

			String tongtienDonhang = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("SoTienSauCKKM")));
			if (tongtienDonhang == null)
				tongtienDonhang = tongtiensauVAT;
			else
				tongtienDonhang = tongtienDonhang.replaceAll(",", "");
			dhBean.setTongtiensauCKKM(Float.parseFloat(tongtienDonhang));
			/*
			 * System.out.println("Tong gia tri don hang ben SVL: " +
			 * tongtienDonhang + "\n");
			 */
			// / Lấy theo chiết khấu npp tự điền
			/*
			 * String TienChietKhau =
			 * util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ChietKhau")));
			 * if(TienChietKhau == null) TienChietKhau = "0"; else TienChietKhau
			 * = TienChietKhau.replaceAll(",", "");
			 * dhBean.setTongChietKhau(TienChietKhau);
			 */
			String TienChietKhau = "0";
			dhBean.setTongChietKhau(TienChietKhau);
			/*
			 * if(khId.length() >0) { String sqlck
			 * ="select chietkhau from khachhang where pk_seq ="+khId+" ";
			 * ResultSet rsck =db.get(sqlck); String TienChietKhau = null;
			 * if(rsck != null) { try { while(rsck.next()) { TienChietKhau =
			 * rsck.getString("chietkhau"); } } catch (SQLException e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); } }
			 * if(TienChietKhau == null) TienChietKhau = "0"; else TienChietKhau
			 * = TienChietKhau.replaceAll(",", "");
			 * dhBean.setTongChietKhau(TienChietKhau); }
			 */

			String VAT = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("VAT")));
			if (VAT == null)
				VAT = "0"; // TTC, don gia da co VAT
			else
				VAT = VAT.replaceAll(",", "");
			dhBean.setVAT(VAT);

			String bgstId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("bgstId")));
			if (bgstId == null)
				bgstId = "0"; // neu khach hang khong co bang gia sieu thi
			/* System.out.println("bang gia sieu thi:" + bgstId ); */
			dhBean.setBgstId(bgstId);
			session.setAttribute("bgstId", bgstId);

			String ngayks = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaykhoaso")).trim());
			if (ngayks == null)
				ngayks = getDateTime();
			System.out.println("Ngay KS: " + ngayks);
			dhBean.setNgayKs(ngayks);

			String ghichu = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ghichu")));
			if (ghichu == null || ghichu.length() == 0)
				ghichu = "NA";
			dhBean.setGhiChu(ghichu);

			String nocu = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("NoCu")));
			if (nocu == null)
				nocu = "0";
			dhBean.setNoCu(Double.parseDouble(nocu.replaceAll(",", "")));

			String sotiengiam = util.antiSQLInspection(request
					.getParameter("sotiengiam"));
			if (sotiengiam == null)
				sotiengiam = "0";
			dhBean.setSotiengiam(sotiengiam.replaceAll(",", ""));

			String ngaysua = getDateTime();
			dhBean.setNgaysua(ngaysua);
			String sqlck = "select chietkhau from khachhang where pk_seq ='"+ dhBean.getKhId() + "' ";
			ResultSet rs = db.get(sqlck);
			System.out.println(" _+_K_+_H_+_" + sqlck);
			String spchietkhau1 = "0";
			if (rs != null) {
				try {
					while (rs.next()) {
						spchietkhau1 = rs.getString("chietkhau");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (spchietkhau1 == null) {
				spchietkhau1 = "0";
			}
			dhBean.setChietkhauDH(spchietkhau1);
			System.out.println("_+_cKKKK+___" + dhBean.getChietkhauDH());
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

			List<ISanpham> spList = new ArrayList<ISanpham>();
			if (masp != null) {
				ISanpham sanpham = null;
				String[] param = new String[12];
				int m = 0;
				while (m < masp.length) {
					if (masp[m] != "") {
						if (soluong[m].length() > 0) // chi them nhung san pham co so luong > 0
						{
							param[0] = "idSP";
							param[1] = masp[m];
							param[2] = tensp[m];
							param[3] = soluong[m].replaceAll(",", "");
							param[4] = donvitinh[m];
							param[5] = dongia[m].replaceAll(",", "");
							param[6] = ""; // khuyen mai
							param[7] = spchietkhau[m].replaceAll(",", "");
							param[8] = spchietkhauDLN[m].replaceAll(",", "");
							param[9] = spchietkhauTT[m].replaceAll(",", "");
							param[10] = spchietkhauDH[m].replaceAll(",", "");
							System.out.println("DonGia: "+ dongia[m].replaceAll(",", ""));
							sanpham = (ISanpham) new Sanpham(param);
							sanpham.setTonhientai(tonkho[m].replaceAll(",", ""));
							sanpham.setGiagoc(spGiagoc[m].replaceAll(",", ""));
							sanpham.setBgId(spBgId[m]);
							spList.add(sanpham);
						}
					}
					m++;
				}
			}

			if (!checkNgayKhoaSo(ngayks, ngaygd)) {
				if (id == null) {
					dhBean.createRS();
					dhBean.setNgaygiaodich(ngaygd);
					dhBean.setSpList(spList);
					dhBean.setMessage("Bạn phải nhập ngày đơn hàng lớn hơn ngày khóa sổ gần nhất...");
					session.setAttribute("dhBean", dhBean);
					String nextJSP = "/AHF/pages/Distributor/DonHangSamNew.jsp";
					response.sendRedirect(nextJSP);
					return;
				} else {
					dhBean.setMessage("Bạn phải nhập ngày đơn hàng lớn hơn ngày khóa sổ gần nhất...");
					dhBean.init();
					dhBean.setNgaygiaodich(ngaygd);
					dhBean.setSpList(spList);
					dhBean.setKhId(khId);
					dhBean.setDdkdId(ddkdId);
					session.setAttribute("dhBean", dhBean);
					String nextJSP = "/AHF/pages/Distributor/DonHangSamUpdate.jsp";
					response.sendRedirect(nextJSP);
					return;
				}

			}

			// Kiem tra nhung san pham khi luu, chot don hang (check ton kho)
			Hashtable<String, Integer> spThieuList = new Hashtable<String, Integer>();
			if (masp != null) {
				int m = 0;
				while (m < masp.length) {
					if (masp[m].length() > 0) {
						String sl = "0";
						String sanpham_fk = "";

						String query = " select sanpham_fk, available from nhapp_kho where npp_fk='"+ nppId + "' and kho_fk = '" + khoId + "' "+
								 "\n and sanpham_fk in (select pk_seq from sanpham where ma='"+ masp[m].trim()+ "') "
								+ "\n and kbh_fk  in (select kbh_fk from khachhang where pk_seq='"	+ dhBean.getKhId() + "') ";
						// System.out.println("Get San Pham thieu : "+query);
						ResultSet slspAvailable = db.get(query);
						if (slspAvailable != null) {
							try {
								if (slspAvailable.next()) {
									sl = slspAvailable.getString("available");
									sanpham_fk = slspAvailable.getString("sanpham_fk");
									slspAvailable.close();

									// lay nhung sp da co trong don hang
									if (id != null) {
										String slg = "0";
										query = "select ISNULL(soluong, 0) as soluong from DONHANG_SANPHAM where DONHANG_FK = '"+ id
												+ "' and SANPHAM_FK in (select pk_seq from sanpham where ma='"+ masp[m].trim() + "')";
										ResultSet SlgRs = db.get(query);
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
									db.update(query);
								} catch (Exception e) {
									dhBean.setMessage("Loi dong lenh sau :"+ query);
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

			String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
			System.out.println("__Action la: " + action);

			if (action.equals("submitKh")) {

				String sql = "";
				if (khTen.trim().length() < 0) {
					sql = "select top(1) substring(a.smartid, charindex('_', a.smartid)+1, len(a.smartid)) as smartId, a.pk_seq as khId, a.ten as khTen, f.CHIETKHAU as ckId, isnull(a.bgst_fk, '0') as bgstId, c.ddkd_fk, e.gsbh_fk, a.diachi "
							+ "\n from khachhang a "
							+ "\n inner join khachhang_tuyenbh b on a.pk_seq = b.khachhang_fk "
							+ "\n inner join tuyenbanhang c on b.tbh_fk = c.pk_seq "
							+ "\n inner join ddkd_gsbh e on c.ddkd_fk = e.ddkd_fk  "
							+ "\n left join MUCCHIETKHAU f on a.CHIETKHAU_FK = f.PK_SEQ "
							+ "\n where a.trangthai = '1' and a.npp_fk = '"+ nppId+ "' and ( a.smartId = '"+ smartId+ "'  or a.ten like N'%"+ smartId
							+ "%') and e.gsbh_fk in (select gsbh_fk from NHAPP_GIAMSATBH where npp_fk ='"+ nppId + "' and ngaybatdau <='" + ngaygd
							+ "' and ngayketthuc > ='" + ngaygd	+ "' ) order by a.smartId asc";
				} else {
					sql = "select top(1) substring(a.smartid, charindex('_', a.smartid)+1, len(a.smartid)) as smartId, a.pk_seq as khId, a.ten as khTen, f.CHIETKHAU as ckId, isnull(a.bgst_fk, '0') as bgstId, c.ddkd_fk, e.gsbh_fk, a.diachi,(select top(1) nvgn_fk from nvgn_kh where khachhang_fk = a.pk_seq) as nvgn "
							+ "\n from khachhang a inner join "
							+ "\n khachhang_tuyenbh b on a.pk_seq = b.khachhang_fk "
							+ "\n inner join tuyenbanhang c on b.tbh_fk = c.pk_seq "
							+ "\n inner join ddkd_gsbh e on c.ddkd_fk = e.ddkd_fk  "
							+ "\n left join MUCCHIETKHAU f on a.CHIETKHAU_FK = f.PK_SEQ "
							+ "\n where a.trangthai = '1' and a.npp_fk = '"+ nppId+ "' and ( a.ten like N'%"+ khTen.split(" - ")[0]
							+ "%' and a.smartId = '"+ smartId+ "' ) and e.gsbh_fk in (select gsbh_fk from NHAPP_GIAMSATBH where npp_fk ='"
							+ nppId + "' and   ngaybatdau <='" + ngaygd	+ "' and ngayketthuc > ='" + ngaygd + "')";
				}
				System.out.println("____Lay khach hang: " + sql);

				rs = db.get(sql);
				if (rs != null) {
					try {
						if (rs.next()) {
							dhBean.setSmartId(rs.getString("smartId"));

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
							ResultSet bgst = db.get(cmd);
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
								String nextJSP = "/AHF/pages/Distributor/DonHangSamNew.jsp";
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
								String nextJSP = "/AHF/pages/Distributor/DonHangSamUpdate.jsp";
								response.sendRedirect(nextJSP);
							}
						} else {
							if (id == null) {
								dhBean.createRS();
								dhBean.setSpList(spList);
								dhBean.setMessage("Mã khách hàng không đúng, hoặc khách hàng chưa được phân tuyến vui lòng kiểm tra lại...");
								session.setAttribute("dhBean", dhBean);
								String nextJSP = "/AHF/pages/Distributor/DonHangSamNew.jsp";
								response.sendRedirect(nextJSP);
							} else {
								dhBean.init();
								dhBean.setSpList(spList);

								dhBean.setMessage("Mã khách hàng không đúng, hoặc khách hàng chưa được phân tuyến vui lòng kiểm tra lại...");
								session.setAttribute("dhBean", dhBean);
								String nextJSP = "/AHF/pages/Distributor/DonHangSamUpdate.jsp";
								response.sendRedirect(nextJSP);
							}
						}
						if (rs != null) {
							rs.close();
						}
					} catch (Exception e) {
					}
					;
				}
			} else {
				if (action.equals("save")) {
					if (id == null) {
						boolean tao = dhBean.CreateDh(spList);
						String dhId = dhBean.getId();
						if (!tao) {
							dhBean.createRS();
							dhBean.setSpList(spList);
							session.setAttribute("dhBean", dhBean);
							String nextJSP = "/AHF/pages/Distributor/DonHangSamNew.jsp";
							response.sendRedirect(nextJSP);
						} else {

							dhBean = (IDonhangsam) new Donhangsam("");
							dhBean.setUserId(userId);
							dhBean.createRS();
							dhBean.setNgaygiaodich(ngaygd);
							dhBean.setMessage("Số đơn hàng bạn vừa lưu " + dhId);

							// Save Data into session
							session.setAttribute("dhBean", dhBean);// truyen vao session mot doi tuong donhang co gia
																	// tri rong khi khoi tao de ben form don hang nhan dc
							session.setAttribute("khId", "");
							session.setAttribute("ddkdId", "");
							session.setAttribute("nppId", dhBean.getNppId());

							String nextJSP = "/AHF/pages/Distributor/DonHangSamNew.jsp";
							response.sendRedirect(nextJSP);
						}
					} else {
						// Kiem tra xem neu don hang do da co khuyen mai, ma
						// thay doi san pham thi phai ap lai khuyen mai
						boolean flag = false;
						boolean cokm = Boolean.parseBoolean(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("cokm"))));

						if (cokm == true) {
							flag = CheckDonHangDKM(spList, id, db);
							if (flag == false) {
								dhBean.init();
								dhBean.setSpList(spList);
								dhBean.setKhId(khId);
								dhBean.setDdkdId(ddkdId);
								dhBean.setMessage("Đơn hàng này đã hưởng khuyến mại, khi thay đổi sản phẩm bạn phải áp lại khuyến mại");
								session.setAttribute("dhBean", dhBean);
								String nextJSP = "/AHF/pages/Distributor/DonHangSamUpdate.jsp";
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
							String nextJSP = "/AHF/pages/Distributor/DonHangSamUpdate.jsp";
							response.sendRedirect(nextJSP);
							return;
						} else {

							dhBean = (IDonhangsam) new Donhangsam("");
							dhBean.setUserId(userId);
							dhBean.createRS();
							dhBean.setNgaygiaodich(ngaygd);

							// Save Data into session
							session.setAttribute("dhBean", dhBean);// truyen vao session mot doi tuong donhang
																	// co gia tri rong khi khoi tao de ben form don hang nhan dc
							session.setAttribute("khId", "");
							session.setAttribute("ddkdId", "");
							session.setAttribute("nppId", dhBean.getNppId());
							dhBean.setMessage("Số đơn hàng bạn vừa lưu " + id);

							String nextJSP = "/AHF/pages/Distributor/DonHangSamNew.jsp";
							response.sendRedirect(nextJSP);
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
							String nextJSP = "/AHF/pages/Distributor/DonHangSamUpdate.jsp";
							response.sendRedirect(nextJSP);
							return;
						} else {
							if (!(dhBean.ChotDh(spList))) {
								dhBean.init();
								dhBean.setSpList(spList);
								dhBean.setDdkdId(ddkdId);
								dhBean.setKhId(khId);
								session.setAttribute("dhBean", dhBean);
								String nextJSP = "/AHF/pages/Distributor/DonHangSamUpdate.jsp";
								response.sendRedirect(nextJSP);
								return;
							} else {
								IDonhangsamList obj = new DonhangsamList();
								obj.setUserId(userId);
								obj.init("");
								session.setAttribute("obj", obj);
								session.setAttribute("ddkdId", "");

								String nextJSP = "/AHF/pages/Distributor/DonHangSam.jsp";
								response.sendRedirect(nextJSP);
								return;
							}
						}
					} else {
						
							dhBean.setUserId(userId);
							dhBean.createRS();
							System.out.println("GSBHID: " + dhBean.getGsbhId());
							String nextJSP = "/AHF/pages/Distributor/DonHangSamNew.jsp";
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
								nextJSP = "/AHF/pages/Distributor/DonHangSamUpdate.jsp";
							}
							dhBean.setSpList(spList);
							session.setAttribute("ddkdId", ddkdId);
							session.setAttribute("dhBean", dhBean);
							response.sendRedirect(nextJSP);
						
					}
				}
				db.shutDown();
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
					sp = (ISanpham) new Sanpham("", rs.getString("spMa"), "",rs.getString("soluong"), "", "", "", "");
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

	

	
	
	private String checkTonkho(String nppId, String ctkmId, String khId,
			String spId, String spMa, int slg, dbutils db) {
		// kiem tra trong kho khuyen mai con du san pham hay khong, khong du thi
		// thoat
		String query = "select available from nhapp_kho where kho_fk = (select kho_fk from ctkhuyenmai where pk_seq = '"+ ctkmId+ "') and npp_fk = '"
				+ nppId	+ "' and sanpham_fk = '"+ spId+ "' and kbh_fk = (select kbh_fk from khachhang where pk_seq = '"+ khId + "')";

		ResultSet rsCheck = db.get(query);
		/* if(rsCheck != null) */
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

		c1.set(Integer.parseInt(ngayks[0]), Integer.parseInt(ngayks[1]) - 1,Integer.parseInt(ngayks[2]));
		c2.set(Integer.parseInt(ngaydh[0]), Integer.parseInt(ngaydh[1]) - 1,Integer.parseInt(ngaydh[2]));

		long songay = (c2.getTime().getTime() - c1.getTime().getTime())	/ (24 * 3600 * 1000);

		if (songay >= 1)
			return true;
		return false;
	}



}
