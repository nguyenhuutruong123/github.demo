package geso.dms.distributor.beans.donhang.imp;

import java.io.Serializable;

import geso.dms.center.db.sql.Idbutils;
import geso.dms.distributor.beans.donhang.IDonhang;
import geso.dms.distributor.beans.donhang.ISanpham;
import geso.dms.distributor.beans.donhang.imp.Sanpham;
import geso.dms.distributor.db.sql.*;
import geso.dms.distributor.util.FixData;
import geso.dms.distributor.util.Utility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Hashtable;

import com.nhat.replacement.TaskReplacement;

public class Donhang implements IDonhang, Serializable {
	private static final long serialVersionUID = -9217977546733610214L;
	List<String> ctkm_bi_giam_so_suat = new ArrayList<String>();
	boolean chap_nhan_giam_so_suat_km = false;
	String userId;
	String id;
	String ngaygiaodich;
	String daidienkd;
	String trangthai;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String chietkhau;
	String tongchietkhau;
	String VAT;
	String diachigiaohang;
	String msg;
	String nppId;
	String nppTen;
	String sitecode;
	String muctindung;
	String ddkdId;
	String gsbhId;
	String tbhId;
	String smartId;
	String khTen;
	ResultSet khlist;
	String khId;
	String bgstId;
	String sotiengiam;
	String khoId;
	ResultSet gsbhs;
	ResultSet kholist;
	ResultSet tbhlist;
	ResultSet ddkdlist;

	List<ISanpham> splist;
	float ttCKKM;
	float ttsauCKKM;
	float ttsauCK;
	String tienCK;
	String tongtientruocVAT;
	String tongtiensauVAT;
	String nvgnId;

	double nocu;

	Hashtable<String, Integer> spThieuList;

	// /trakhuyen mai
	Hashtable<String, Float> scheme_tongtien = new Hashtable<String, Float>();
	Hashtable<String, Float> scheme_chietkhau = new Hashtable<String, Float>();
	List<ISanpham> scheme_sanpham = new ArrayList<ISanpham>();

	boolean aplaikm;
	boolean cokm;
	boolean chuaApkm;
	boolean dacoDh;
	boolean daxuatkhoChuachot;

	String ngayks;
	String ghichu = "";
	String ghichuoption = "";
	ResultSet ghichuORs;
	String ngaygh;
	String coderoute = "";
	String isPDA = "";
	private String pxkId = "";
	private String ngayxuatkho = "";
	String donhangkhac = "0";
	dbutils db;

	public Donhang(String[] param) {
		this.id = param[0];
		this.khId = param[1];
		this.ngaygiaodich = param[2];
		this.nppTen = param[3];
		this.daidienkd = param[4];
		this.trangthai = param[5];
		this.ngaytao = param[6];
		this.nguoitao = param[7];
		this.ngaysua = param[8];
		this.nguoisua = param[9];
		this.VAT = param[10];
		this.ddkdId = param[11];
		this.ngaygh = "";
		this.tbhId = "";
		this.gsbhId = "";
		this.chietkhau = "";
		this.tongchietkhau = "";
		this.diachigiaohang = "";
		this.bgstId = "0";
		this.khoId = "";
		this.msg = "";
		this.muctindung = "0";
		this.spThieuList = new Hashtable<String, Integer>();
		this.aplaikm = false;
		this.cokm = false;
		this.chuaApkm = false;
		this.dacoDh = false;
		this.daxuatkhoChuachot = false;
		this.ngayks = "";
		this.ghichu = "";
		this.ghichuoption = "";
		this.db = new dbutils();
	}

	public Donhang(String id) {
		this.id = id;
		this.khId = "";
		this.ngaygiaodich = "";
		this.diachigiaohang = "";
		this.nppTen = "";
		this.daidienkd = "";
		this.trangthai = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.VAT = "";
		this.ngaygh = "";
		this.ddkdId = "";
		this.gsbhId = "";
		this.tbhId = "";
		this.chietkhau = "";
		this.tongchietkhau = "";
		this.tongtiensauVAT = "0";
		this.tongtientruocVAT = "0";
		this.ttsauCKKM = 0;
		this.ttCKKM = 0;
		this.ttsauCK = 0;
		this.nocu = 0;
		this.bgstId = "0";
		this.khoId = "";
		this.msg = "";
		this.khTen = "";
		this.smartId = "";
		this.muctindung = "0";
		this.aplaikm = false;
		this.cokm = false;
		this.chuaApkm = false;
		this.dacoDh = false;
		this.daxuatkhoChuachot = false;
		this.spThieuList = new Hashtable<String, Integer>();
		this.ghichu = "";
		this.ghichuoption = "";
		this.ngayks = "";
		this.db = new dbutils();

	}

	public Donhang(String id, String nppId) {
		this.id = id;
		this.nppId = nppId;
		this.CreateSpList();
	}

	public dbutils getDb() {
		return db;
	}

	public String getIsPDA() {
		return isPDA;
	}

	public void setIsPDA() {

		if(this.id != null && this.id.trim().length() > 0)
		{
			ResultSet rs = db.get( " select isnull(isPDA,0) isPDA from donhang where pk_seq  = " + id );
			try {
				rs.next();
				this.isPDA = rs.getString ("isPDA");
				rs.close();
				return;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		this.isPDA = "0";
	}

	public String getCoderoute() {
		return coderoute;
	}

	public void setCoderoute(String coderoute) {
		this.coderoute = coderoute;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSmartId() {
		return this.smartId;
	}

	public void setSmartId(String smartId) {
		this.smartId = smartId;
	}

	public String getKhTen() {
		return this.khTen;
	}

	public void setKhTen(String khTen) {
		this.khTen = khTen;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKhId() {
		return this.khId;
	}

	public void setKhId(String khId) {
		this.khId = khId;
	}

	public String getNgaygiaodich() {
		return this.ngaygiaodich;
	}

	public void setNgaygiaodich(String xxx) {
		this.setIsPDA();

		if(this.isPDA != null && this.isPDA.equals("1"))
		{
			ResultSet rs = db.get( " select ngaynhap from donhang where pk_seq  = " + this.id );
			try 
			{
				rs.next();
				this.ngaygiaodich = rs.getString ("ngaynhap");
				rs.close();
				return;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		this.ngaygiaodich = xxx;
	}

	public String getDaidienkd() {
		return this.daidienkd;
	}

	public void setDaidienkd(String daidienkd) {
		this.daidienkd = daidienkd;
	}

	public String getTrangthai() {
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}

	public String getNgaytao() {
		return this.ngaytao;
	}

	public void setNgaytao(String ngaytao) {
		this.ngaytao = ngaytao;
	}

	public String getNguoitao() {
		return this.nguoitao;
	}

	public void setNguoitao(String nguoitao) {
		this.nguoitao = nguoitao;
	}

	public String getNgaysua() {
		return this.ngaysua;
	}

	public void setNgaysua(String ngaysua) {
		this.ngaysua = ngaysua;
	}

	public String getNguoisua() {
		return this.nguoisua;
	}

	public void setNguoisua(String nguoisua) {
		this.nguoisua = nguoisua;
	}

	public String getChietkhau() {
		if (this.chietkhau.length() <= 0)
			this.chietkhau = "0";
		return this.chietkhau;
	}

	public void setChietkhau(String chietkhau) {
		this.chietkhau = chietkhau;
	}

	public String getVAT() {
		if (this.VAT == "") {
			// this.VAT = "10";
			this.VAT = "0"; // TTC, gia trong bang gia da co VAT
		}
		return this.VAT;
	}

	public void setVAT(String vat) {
		this.VAT = vat;
	}

	public String getMessage() {
		return this.msg;
	}

	public void setMessage(String msg) {
		this.msg = msg;
	}

	public String getNppId() {
		return this.nppId;
	}

	public void setNppId(String nppId) {
		this.nppId = nppId;
	}

	public String getNppTen() {
		return this.nppTen;
	}

	public void setNppTen(String nppTen) {
		this.nppTen = nppTen;
	}

	public String getSitecode() {
		return this.sitecode;
	}

	public void setSitecode(String sitecode) {
		this.sitecode = sitecode;
	}

	public ResultSet getDdkdList() {
		return this.ddkdlist;
	}

	public void setDdkdList(ResultSet ddkdList) {
		this.ddkdlist = ddkdList;
	}

	public String getDdkdId() {
		return this.ddkdId;
	}

	public void setDdkdId(String ddkdId) {
		this.ddkdId = ddkdId;
	}

	public List<ISanpham> getSpList() {
		return this.splist;
	}

	public void setSpList(List<ISanpham> splist) {
		this.splist = splist;
	}

	public float getTongtiensauCK() {
		float tongtientruocvat = 0;
		try {
			tongtientruocvat = Float.parseFloat(this.tongtientruocVAT);
		} catch (Exception er) {

		}
		float tienck = 0;
		try {
			tienck = Float.parseFloat(this.tongchietkhau);
		} catch (Exception er) {

		}

		this.ttsauCK = tongtientruocvat - tienck;

		return this.ttsauCK;
	}

	public void setTongtiensauCK(float ttsck) {
		this.ttsauCK = ttsck;
	}

	public String getTongtientruocVAT() {
		return this.tongtientruocVAT;
	}

	public void setTongtientruocVAT(String tttvat) {
		this.tongtientruocVAT = tttvat;
	}

	public String getTongtiensauVAT() {
		return this.tongtiensauVAT;
	}

	public void setTongtiensauVAT(String ttsvat) {
		this.tongtiensauVAT = ttsvat;
	}

	public float getTongtienCKKM() {
		return this.ttCKKM;
	}

	public void setTongtienCKKM(float ttckkm) {
		this.ttCKKM = ttckkm;

	}

	public float getTongtiensauCKKM() {
		this.ttsauCKKM = Float.parseFloat(this.tongtiensauVAT) - this.ttCKKM;
		System.out.println("____++___" + this.ttsauCKKM);
		return this.ttsauCKKM;
	}

	public void setTongtiensauCKKM(float ttsckkm) {
		this.ttsauCKKM = ttsckkm;
	}

	public ResultSet getTbhList() {
		return this.tbhlist;
	}

	public void setTbhList(ResultSet tbhList) {
		this.tbhlist = tbhList;
	}

	public String getTbhId() {
		return this.tbhId;
	}

	public void setTbhId(String tbhId) {
		this.tbhId = tbhId;
	}

	public String getGsbhId() {
		return this.gsbhId;
	}

	public void setGsbhId(String gsbhId) {
		this.gsbhId = gsbhId;
	}

	public ResultSet getKhList() {
		return this.khlist;
	}

	public void setKhList(ResultSet khlist) {
		this.khlist = khlist;
	}

	public Hashtable<String, Integer> getSpThieuList() {
		return this.spThieuList;
	}

	public void setSpThieuList(Hashtable<String, Integer> spThieuList) {
		this.spThieuList = spThieuList;
	}

	// tra km
	public Hashtable<String, Float> getScheme_Tongtien() {
		return this.scheme_tongtien;
	}

	public void setScheme_Tongtien(Hashtable<String, Float> scheme_tongtien) {
		this.scheme_tongtien = scheme_tongtien;
	}

	public Hashtable<String, Float> getScheme_Chietkhau() {
		return this.scheme_chietkhau;
	}

	public void setScheme_Chietkhau(Hashtable<String, Float> scheme_chietkhau) {
		this.scheme_chietkhau = scheme_chietkhau;
	}

	public List<ISanpham> getScheme_SpList() {
		return this.scheme_sanpham;
	}

	public void setScheme_SpList(List<ISanpham> splist) {
		this.scheme_sanpham = splist;
	}

	private void getTrakhuyenmai() {
		List<ISanpham> scheme_sp = new ArrayList<ISanpham>();
		String query = "select a.ctkmId, a.trakmId, a.soxuat, a.soluong,donvi, a.spMa, a.tonggiatri, b.scheme, c.loai, c.hinhthuc, c.chietkhau,  c.tongluong, c.tongtien, d.ten, d.pk_seq as spId ";
		query = query
		+ " ,(select AVAILABLE as  SOLUONG from NHAPP_KHO where NPP_FK = dh.NPP_FK and KBH_FK = dh.KBH_FK and KHO_FK = b.KHO_FK and SANPHAM_FK = d.PK_SEQ  )  as soluongton ";
		query = query
		+ "from donhang_ctkm_trakm a inner join ctkhuyenmai b on a.ctkmid = b.pk_seq inner join trakhuyenmai c on a.trakmid = c.pk_seq left join sanpham d on a.spMa = d.ma ";
		query = query + " left join donvidoluong dv on dv.pk_seq=d.dvdl_fk ";
		query = query + " inner join DONHANG dh on dh.PK_SEQ = a.DONHANGID  ";
		query = query + "where a.ap_dung !=1 and a.donhangId = '" + this.id + "'";

		System.out.println("1.Khoi tao TKM  :" + query);

		ResultSet rs = db.get(query);
		/* if( rs != null) */
		{
			try {
				while (rs.next()) {
					String schemeName = rs.getString("scheme");
					String trakmId = rs.getString("trakmId");
					String soxuat = rs.getString("soxuat");
					String soluong = rs.getString("soluong");
					String loai = rs.getString("loai");
					String hinhthuc = rs.getString("hinhthuc");
					String tongiatri = rs.getString("tonggiatri");
					String donvi = rs.getString("donvi");
					String soluongton = rs.getString("soluongton") == null ? ""
							: rs.getString("soluongton");
					float tongtien = 0;
					float chietkhau = 0;
					String spMa = "";
					this.ttCKKM = 0;

					if (loai == null) {
						if (rs.getString("spMa") == null) {
							if (rs.getString("tongtien") != null)
								tongtien = Float.parseFloat(rs
										.getString("tongtien"));
							this.scheme_tongtien.put(schemeName, tongtien
									* Float.parseFloat(soxuat));
							this.aplaikm = true; // co ctkm
							this.cokm = true;
						}
					} else {
						if (loai.equals("1")) // tra tien
						{
							this.scheme_tongtien.put(schemeName, Float.parseFloat(tongiatri) );
							this.aplaikm = true;
							this.cokm = true;
						} else {
							if (loai.equals("2")) // tra chiet khau
							{
								if (rs.getString("chietkhau") != null)
									chietkhau = Float.parseFloat(rs
											.getString("chietkhau"));

								this.scheme_chietkhau.put(schemeName,
										Float.parseFloat(tongiatri));
								this.ttCKKM = this.ttCKKM
								+ Float.parseFloat(tongiatri);
								this.aplaikm = true;
								this.cokm = true;

								System.out.println("1.Tra chiet khau");

							} else // tra sp
							{
								// String sql =
								// "select a.sanpham_fk as spId, a.soluong, b.ma, b.ten from trakhuyenmai_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq where a.trakhuyenmai_fk = '"
								// + trakmId + "'";
								// String sql =
								// "select a.spMa, a.soluong, b.pk_seq as spId, b.ten from donhang_ctkm_trakm a inner join sanpham b on a.spMa = b.ma where ";

								// ResultSet sanphamRs = db.get(sql);
								String[] param = new String[12];
								ISanpham sp = null;

								param[0] = rs.getString("spId");
								param[1] = rs.getString("spMa");
								param[2] = rs.getString("ten");
								param[3] = soluong;
								param[4] = donvi;
								param[5] = "0";
								param[6] = schemeName;
								param[7] = "0";
								param[8] = "0";
								param[9] = "0";
								param[10] = "0";
								param[11] = soluongton;

								sp = new Sanpham(param);
								scheme_sp.add(sp);
								this.aplaikm = true;
								this.cokm = true;

							}
						}
					}
				}
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.scheme_sanpham = scheme_sp;

	}

	private void getNppInfo() {
		geso.dms.distributor.util.Utility util = new geso.dms.distributor.util.Utility();
		this.nppId = util.getIdNhapp(this.userId);
		this.nppTen = util.getTenNhaPP();
		this.sitecode = util.getSitecode();
		this.IsChiNhanh = util.getIsChinhNhanh();
		// lay ngay khoa so
		this.ngayks = util.ngaykhoaso(this.nppId);
	}

	public boolean Muctindung() {
		if (this.nppId.equals("102961")) {
			if (this.khId.length() > 0) {
				float sotienno = 0;
				if (khId.length() > 0) {
					String sql = " select sotienno,a.pk_seq from khachhang a inner join gioihancongno b on a.ghcn_fk = b.pk_seq where a.pk_seq ='"
						+ this.khId + "'";
					// System.out.println("sotienno:"+sql);
					ResultSet tb = db.get(sql);
					if (tb != null) {
						try {
							if (tb.next())
								sotienno = Float.parseFloat(tb
										.getString("sotienno"));
							else
								sotienno = 0;
							tb.close();
						} catch (Exception e1) {

							e1.printStackTrace();
						}
						if (this.id.length() > 0)
							sql = " select khachhang_fk ,sum(tonggiatri-dathanhtoan) as num from donhang where pk_seq <>'"
								+ this.id
								+ "' and tonggiatri > dathanhtoan and khachhang_fk ='"
								+ this.khId + "' group by khachhang_fk";
						else
							sql = " select khachhang_fk ,sum(tonggiatri-dathanhtoan) as num from donhang where tonggiatri > dathanhtoan and khachhang_fk ='"
								+ this.khId + "' group by khachhang_fk";
						// System.out.println("tongtienno:"+sql);
						ResultSet rs = db.get(sql);
						try {

							if (rs != null) {
								if (rs.next()) {
									if (rs.getString("num").length() > 0)
										sotienno = sotienno
										- Float.parseFloat(rs
												.getString("num"));
								}
							}
							rs.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				}
				this.muctindung = sotienno + "";
			}
		} else
			this.muctindung = "99999999999"; // gia tri don hang khong the vuot
		// qua
		return false;
	}

	private String kenh() {
		// String sql
		// ="select a.kbh_fk from giamsatbanhang a,ddkd_gsbh b where a.pk_seq = b.gsbh_fk and b.ddkd_fk ='"+
		// this.ddkdId +"'";
		String sql = "select kbh_fk from KHACHHANG where pk_seq ='" + this.khId
		+ "'";
		ResultSet rs = db.get(sql);
		if (rs != null) {
			try {
				rs.next();
				String kbhfk = rs.getString("kbh_fk");
				rs.close();
				return kbhfk;
			}

			catch (Exception e) {
			}
		}
		return "null";
	}

	public boolean CreateDh(List<ISanpham> splist) {

		TaskReplacement t = new TaskReplacement();
		Object[] data = null;

		/*if (this.spThieuList.size() > 0) {
			this.msg = "Trong kho không đủ số lượng sản phẩm đã chọn, vui lòng kiểm tra lại...";
			return false;
		} else*/ 


		Utility util = new Utility();
		this.nppId = util.getIdNhapp(this.userId);

	
		
		if (this.nppId != null && this.nppId.trim().length() > 0) {
			/*String sqlnpp = "select * from BANGGIAMUANPP_NPP where npp_fk = "
				+ nppId + " ";

			ResultSet rsnpp = db.get(sqlnpp);
			if (rsnpp != null) {
				try {
					while (!rsnpp.next()) {
						msg = "Khách hàng "+ this.khId+ " chưa duyệt vui lòng liên hệ trung tâm.";
						return false;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}*/
		}


		if (this.nvgnId == null || this.nvgnId.length() <= 0) {
			this.msg = "Vui lòng chọn nhân viên giao nhận!";
			return false;
		}

		String sqlCHECK = "";
		String sqlCHECK_HangMau = "";
		try {
			for (int m = 0; m < splist.size(); m++) {

				ISanpham sp = splist.get(m);


				double _chenhlech = geso.dms.center.util.Utility.parseDouble(sp.getTiengiamtru().replace(",",""));



				String kq = "\n select ma,pk_seq as sanpham_fk, "+sp.getSoluong()+" as soluong, "
				+ "\n "+sp.getDongia().replaceAll(",", "")+" as dongia,"
				+ "\n "+sp.getChietkhau()+" as chietkhau, "
				+ "\n "+sp.getGiagoc().replaceAll(",", "")+" dongiaGOC, "
				+ "\n "+sp.getBgId()+" BangGia_fk, "
				+ "\n "+sp.getChietkhauDLN().replaceAll(",", "")+" as chietkhauDLN, "
				+ "\n "+sp.getChietkhauTT().replaceAll(",", "")+" as chietkhauTT, "
				+ "\n "+sp.getChietkhauDH().replaceAll(",", "")+" as chietkhauDH" +
				  "\n   	, "+_chenhlech+" tiengiamtru  "
				+ "\n from SANPHAM where ma = '"+sp.getMasanpham()+"' ";

				if(sp.getHangmau().equals("1"))
					sqlCHECK_HangMau  +=  sqlCHECK_HangMau.length() > 0 ? " union " + kq : kq;
					else
						sqlCHECK +=  sqlCHECK.length() > 0 ? " union " + kq : kq;
			}

			if(sqlCHECK.trim().length() <=0)
			{
				this.msg = "Vui lòng chọn sản phẩm bán!";
				return false;
			}

			this.kbhId = this.kenh();

			/*
			 * System.out.println("SQL CHECK TON KHO: " + sqlCHECK); String
			 * query = "select b.TEN, b.MA, a.available " +
			 * "from NHAPP_KHO a inner join SANPHAM b on a.sanpham_fk = b.pk_seq "
			 * + "	inner join  " + " ( " + sqlCHECK +
			 * "	) dh on b.pk_seq = dh.sanpham_fk " + "where a.NPP_FK = '" +
			 * this.nppId + "' and a.kho_fk = '" + this.khoId+
			 * "' and KBH_FK = '" + this.kbhId +
			 * "	and a.available - isnull(dh.soluong, 0) < 0 ";
			 * 
			 * System.out.println("---CHECK TON KHO: " + query); ResultSet
			 * rsChheck = db.get(query); String msg = ""; if (rsChheck !=
			 * null) { while (rsChheck.next()) { msg += "Sản phẩm ( " +
			 * rsChheck.getString("TEN")+ " ) còn tối đa ( "+
			 * rsChheck.getString("available") +
			 * " ). Vui lòng kiểm tra lại ";
			 * this.spThieuList.put(rsChheck.getString
			 * ("MA"),rsChheck.getInt("available")); } rsChheck.close(); }
			 * if (msg.trim().length() > 0) { this.msg = msg; return false;
			 * }
			 */
		} catch (Exception er) {
			this.msg = "Lỗi khi lưu đơn hàng 1";
			er.printStackTrace();
			return false;
		}
		if (this.sotiengiam.length() <= 0) {
			this.sotiengiam = "0";
		}

		try {

			db.update("SET TRANSACTION ISOLATION LEVEL SNAPSHOT;");
			db.update("SET LOCK_TIMEOUT 60000;");
			db.getConnection().setAutoCommit(false);


			String msgKS  = geso.dms.center.util.Utility.Check_Huy_NghiepVu_KhoaSo_Tao_Moi_NV(this.nppId, this.ngaygiaodich, db);
			if( msgKS.length() > 0)
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				this.msg = msgKS;
				return false;
			}


			String sql = "select top (1) a.TBH_fk from KHACHHANG_TUYENBH a "
				+ "\n inner join TUYENBANHANG b on a.TBH_FK = b.PK_SEQ WHERE KHACHHANG_FK = ? "
				+ "\n and DDKD_FK = ? order by NGAYID";
			System.out.println(t.taoQuery(sql));
			data = dbutils.setObject(khId, ddkdId);
			ResultSet rscheck = db.get_v2(sql, data);
			if (rscheck == null || rscheck.next() == false) {
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				rscheck.close();
				this.msg = "Khách hàng chưa có tuyến của NVBH này!";
				return false;
			}

			//System.out.println("ghichuoption  : "+ this.ghichuoption);
			String ghichuO = this.ghichuoption;
			if(this.ghichuoption.length() == 0)
			{
				ghichuO = null;	
			}
			System.out.println("ghichuO  : "+ ghichuO);
			
			sql = "select isnull(b.khongVat,0)khongVat from khachhang a inner join loaicuahang b on a.LCH_FK = b.PK_SEQ where a.PK_SEQ ="+ this.khId;
			ResultSet rs = db.get(sql);
			int khongVat = 0;
			if(rs!=null){
				if(rs.next()){
					khongVat = rs.getInt("khongVat");
				}
			}
			rs.close();
			double tyleVat = khongVat == 1 ? 1 : 1.08;
			
			double _slCombo = geso.dms.center.util.Utility.parseDouble(this.soluongSpComBo.replace(",",""));
			double _giaCombo = geso.dms.center.util.Utility.parseDouble(this.giaSpComBo.replace(",",""));
 			String query = " declare @ngaynhap nvarchar(10), @kbh_fk numeric(18, 0), @BM_fk numeric(18, 0), "
				+ "\n @TBH_fk numeric(18, 0), @AMS_fk numeric(18, 0), @IsChiNhanh bit ;  "
				+ "\n	select @kbh_fk = kbh_fk from khachhang where pk_seq = "+this.khId+" "
				+ "\n	select @ngaynhap= '"+this.ngaygiaodich+"' "
				+ "\n	select @IsChiNhanh = IsChiNhanh from NHAPHANPHOI where PK_SEQ= "+this.nppId+" "
				+ "\n select top (1) @TBH_fk = a.TBH_fk from KHACHHANG_TUYENBH a "
				+ "\n	inner join TUYENBANHANG b on a.TBH_FK = b.PK_SEQ "
				+ "\n WHERE KHACHHANG_FK = "+this.khId+" and DDKD_FK = "+this.ddkdId+" order by NGAYID "
				+ "\n	select top 1 @BM_fk=bm_cn.bm_fk,@AMS_fk=a.asm_fk from asm_khuvuc a "
				+ "\n		inner join asm on asm.pk_seq=a.asm_fk  "
				+ "\n		inner join khuvuc kv on kv.pk_seq=a.khuvuc_fk "
				+ "\n		inner join bm_chinhanh bm_cn on bm_cn.vung_fk=kv.vung_fk "
				+ "\n			AND bm_cn.NGAYBATDAU <=@ngaynhap AND bm_cn.NGAYKETTHUC >=@ngaynhap "
				+ "\n		inner join nhaphanphoi npp on npp.khuvuc_fk=kv.pk_seq "
				+ "\n		inner join bm on bm.pk_seq=bm_cn.bm_fk and bm.kbh_fk=@kbh_fk "
				+ "\n			and bm.dvkd_fk=(select dvkd_fk from giamsatbanhang where pk_seq ="+this.gsbhId+") "
				+ "\n	where npp.pk_seq = "+this.nppId+" and asm.kbh_fk=@kbh_fk "
				+ "\n and asm.dvkd_fk=(select dvkd_fk from giamsatbanhang where pk_seq = "+this.gsbhId+" ) "
				+ "\n	insert into DonHang(tenSpComBo,giaSpComBo,soluongSpComBo,ghichu_fk, donhangkhac,ghichu,ngaynhap, trangthai, ngaytao, ngaysua, nguoitao, nguoisua, vat, tonggiatri, ddkd_fk, "
				+ "\n 		gsbh_fk, khachhang_fk, npp_fk, kho_fk, kbh_fk, tinhtrang,BM,ASM,IsChiNhanh, diachigiao,nvgn_fk,sotiengiam, "
				+ "\n 		tbh_fk, isdungtuyen,FlagModified, ngaygiaohang,PDA_CapNhat,tylevat ) "
				+ "\n	select N'"+this.tenSpComBo+"',"+_giaCombo+","+_slCombo+","+ ghichuO +", "+this.donhangkhac+",N'"+this.ghichu+"', '"+this.ngaygiaodich+"', 0  trangthai, '"+this.getDateTime()+"', '"+this.getDateTime()+"', "+userId+", "+userId+", "+VAT+", 0 tgt" +
				"\n, 			"+this.ddkdId+" ddkd_fk, "
				+ "\n 		"+this.gsbhId+"  , "+this.khId+" , "+this.nppId+" , "+this.khoId+" , @kbh_fk, 0 tinhtrang,@BM_fk,@AMS_fk,@IsChiNhanh, N'"+this.diachigiaohang+"',"+this.nvgnId+","+sotiengiam+", "
				+ "\n 		@TBH_fk ,  0 isdungtuyen, 1 FlagModified, N'"+ngaygh+"' , 0 PDA_CapNhat, '"+tyleVat+"' ";
			
			if (db.updateReturnInt(query)!= 1) {
				System.out.println("query = "+ query);
				this.msg = "Lỗi khi lưu đơn hàng 1";
				this.db.getConnection().rollback();
				return false;
			}

			query = " select scope_identity() dhId ";
			rs=  db.get(query);
			rs.next();
			this.id = rs.getString("dhId");
			
			query = " update dh  set LCH_FK = lch.PK_SEQ , tyleVat = case when lch.khongVat = 1 then 1 else 1.08 end "+
					"\n from donhang dh "+
					"\n inner join KHACHHANG kh on kh.PK_SEQ = dh.KHACHHANG_FK "+
					"\n inner join LOAICUAHANG lch on lch.PK_SEQ = kh.LCH_FK "+
					"\n where dh.PK_SEQ =" + this.id;
			if (db.updateReturnInt(query)!= 1) {
				System.out.println("query = "+ query);
				this.msg = "Lỗi khi lưu đơn hàng 2";
				this.db.getConnection().rollback();
				return false;
			}
			String querylayma="select max(madonhang) madonhang from MADONHANGTUDONG where thang=month('"+this.getDateTime()+"') and nam=year('"+this.getDateTime()+"')";	 
			ResultSet checkRs=db.get(querylayma);
			String madonhang="";
			checkRs.next();
            madonhang=checkRs.getString("madonhang");
			checkRs.close();
			String [] ngay=getDateTime().split("-");
			if(madonhang!=null){
				madonhang=Float.parseFloat(madonhang)+1+"";
				String query1="update donhang set madonhang='AHF'+'"+ngay[1]+"'+'"+ngay[0].substring(2,4)+"-'+dbo.[PlusZero]("+madonhang+",6)"
						+ " where pk_seq="+this.id+"";
				if (db.updateReturnInt(query1)!= 1) {
					System.out.println("query = "+ query1);
					this.msg = "Lỗi khi lưu đơn hàng query1";
					this.db.getConnection().rollback();
					return false;
				}
				query1="insert MADONHANGTUDONG(thang,nam,donhang_fk,madonhang) select '"+ngay[1]+"','"+ngay[0]+"',"+this.id+",dbo.[PlusZero]("+madonhang+",6)";
 				if (db.updateReturnInt(query1)!= 1) {
					System.out.println("query = "+ query1);
					this.msg = "Lỗi khi lưu đơn hàng MADONHANGTUDONG";
					this.db.getConnection().rollback();
					return false;
				}
			}else{

 				String query1="update donhang set madonhang='AHF'+'"+ngay[1]+"'+'"+ngay[0].substring(2,4)+"-'+dbo.[PlusZero]("+1+",6)"
						+ " where pk_seq="+this.id+"";
				if (db.updateReturnInt(query1)!= 1) {
					System.out.println("query = "+ query1);
					this.msg = "Lỗi khi lưu đơn hàng query1";
					this.db.getConnection().rollback();
					return false;
				}
				query1="insert MADONHANGTUDONG(thang,nam,donhang_fk,madonhang) select '"+ngay[1]+"','"+ngay[0]+"',"+this.id+",dbo.[PlusZero]("+1+",6)";
 				if (db.updateReturnInt(query1)!= 1) {
					System.out.println("query = "+ query1);
					this.msg = "Lỗi khi lưu đơn hàng MADONHANGTUDONG";
					this.db.getConnection().rollback();
					return false;
				}
			
				
			}
			
			String dongiaGoc =  " [dbo].[GiaBanLeNppSanPham]("+this.khId+",dh.kbh_fk,dh.npp_fk,sp.sanpham_fk, dh.ngaynhap) ";
			String giamua = this.donhangkhac.equals("1") ? "0" : "  "+dongiaGoc+" + sp.tiengiamtru  ";
			// Chèn donhang_sanpham tận dung luôn câu SQL bên trên, kể cả
			// hàm cập nhật tồn kho, cũng chỉ chạy 1 lượt
			query = "\n if exists (select PK_SEQ from DONHANG WHERE PK_SEQ = '"
				+ this.id
				+ "' AND TRANGTHAI = 0)"
				+ "\n insert donhang_sanpham( sanpham_fk, donhang_fk, soluong, kho_fk, giamua, chietkhau, dongiaGOC,BangGia_fk, ptCKDLN, ptCKTT, ptCKDH,tiengiamtru ) "
				+ "\n select sp.sanpham_fk, dh.pk_seq , sp.soluong, dh.kho_fk, "+giamua+", sp.chietkhau,"
				+ "\n  "+dongiaGoc+" dgGoc,sp.BangGia_fk, sp.chietkhauDLN, sp.chietkhauTT, sp.chietkhauDH, sp.tiengiamtru "
				+ "\n from ( "
				+ sqlCHECK
				+ " ) sp inner join donhang dh on dh.pk_seq =  "
				+ this.id;
			if (db.updateReturnInt(query) < 0) {
				db.getConnection().rollback();
				this.msg = "Lỗi khi lưu đơn hàng 3";
				return false;
			}



			query = "select count(*) sl from donhang_sanpham where soluong<=0 and donhang_fk="+ this.id;
			System.out.println("check dh so =0 " + query);
			ResultSet rsc = db.get(query);
			rsc.next();
			int sl = rsc.getInt("sl");
			rsc.close();
			if (sl > 0) {
				db.getConnection().rollback();
				this.msg = "Vui lòng kiểm tra lại số lượng bán, không được nhập số lượng bằng 0.";
				return false;
			}
			
			if(!donhangkhac.equals("1")){
				
			/*	query = "select count(*) sl from donhang_sanpham where giamua <=0  and donhang_fk ="+ this.id;
				System.out.println("check dh gia =0 " + query);
				rsc = db.get(query);
				rsc.next();
				sl = rsc.getInt("sl");
				rsc.close();
				if (sl > 0) {
					db.getConnection().rollback();
					this.msg = "Vui lòng kiểm tra lại giá, không được nhập sản phẩm có giá bằng 0.";
					return false;
				}*/
			}

			if(sqlCHECK_HangMau.length() > 0)
			{
				query = " insert donhang_ctkm_trakm (donhangId,ctkmId,trakmId,SOXUAT,TONGGIATRI,SPMA,Sanpham_fk,SOLUONG,ap_dung) " +
				"\n select "+this.id+",ctkm.pk_seq ,tkm.pk_seq ,sp.soluong,0 ,sp.ma,sp.Sanpham_fk,sp.soluong,tkm.ap_dung " +
				"\n from " +
				"\n (" +
				sqlCHECK_HangMau +
				"\n )sp " +
				"\n inner join ctkhuyenmai ctkm on ctkm.loaict = 99  "  +
				"\n inner join trakhuyenmai tkm on tkm.ap_dung = 1 " ;
				if (db.updateReturnInt(query) <= 0) {
					System.out.println(" query = " + query);
					db.getConnection().rollback();
					this.msg = "Lỗi khi lưu hàng mẫu !";
					return false;
				}
			}





			msg = Utility.Update_GiaTri_DonHang(this.id, db);
			if (msg.trim().length() > 0) {
				this.db.getConnection().rollback();
				this.db.getConnection().setAutoCommit(true);
				return false;
			}

			query = "select tonggiatri from donhang where pk_seq  = "
				+ this.id + "  ";
			rs = db.get(query);
			while (rs.next()) {
				String msg1 = "";
				if (rs.getDouble("tonggiatri") < 0) {
					msg1 = "Lỗi giá trị đơn hàng bị âm.";
				}
				if (msg1.length() > 0) {
					this.db.getConnection().rollback();
					this.msg = "Lỗi phát sinh khi cập nhật giá trị đơn hàng do tổng giá trị âm.";
					return false;
				}
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e) {
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			e.printStackTrace();
			return false;
		}
		return true;

	}

	public boolean UpdateDh(List<ISanpham> splist, String action) {


		if(!Utility.KiemTra_PK_SEQ_HopLe( this.id, "Donhang", this.db))
		{
			this.msg = "Định danh đơn hàng không hợp lệ!";
			return false;
		}

		Object[] data = null;

		Utility util = new Utility();
		this.nppId = util.getIdNhapp(this.userId);
		
		
		if (this.nppId != null && this.nppId.trim().length() > 0) {
		/*	String sqlnpp = "select * from BANGGIAMUANPP_NPP where npp_fk = "
				+ nppId + " ";

			ResultSet rsnpp = db.get(sqlnpp);
			if (rsnpp != null) {
				try {
					while (!rsnpp.next()) {
						msg = "Khách hàng "+ this.khId+ " chưa duyệt vui lòng liên hệ trung tâm.";
						return false;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}*/
		}

		/*if (this.spThieuList.size() > 0) {
			this.msg = "Trong kho không đủ số lượng sản phẩm đã chọn, vui lòng kiểm tra lại...";
			return false;
		} else*/ 


		String sqlCHECK = "";
		String sqlCHECK_HangMau = "";
		for (int m = 0; m < splist.size(); m++) {

			ISanpham sp = splist.get(m);

			if(this.donhangkhac.equals("1"))
			{
				if(geso.dms.center.util.Utility.parseDouble(sp.getDongia().replaceAll(",", "")) > 0 )
				{
					this.msg = " giá đơn hàng khác phải = 0 ";
					return false;
				}
			}
			double _chenhlech = geso.dms.center.util.Utility.parseDouble(sp.getTiengiamtru().replace(",",""));
			String kq = "\n select ma,pk_seq as sanpham_fk, "+sp.getSoluong()+" as soluong, "
			+ "\n "+sp.getDongia().replaceAll(",", "")+" as dongia,"
			+ "\n "+sp.getChietkhau()+" as chietkhau, "
			+ "\n "+sp.getGiagoc().replaceAll(",", "")+" dongiaGOC, "
			+ "\n "+sp.getBgId()+" BangGia_fk, "
			+ "\n "+sp.getChietkhauDLN().replaceAll(",", "")+" as chietkhauDLN, "
			+ "\n "+sp.getChietkhauTT().replaceAll(",", "")+" as chietkhauTT, "
			+ "\n "+sp.getChietkhauDH().replaceAll(",", "")+" as chietkhauDH "
			+ "\n ,	"+_chenhlech+" tiengiamtru "
			+ "\n from SANPHAM where ma = '"+sp.getMasanpham()+"' ";

			if(sp.getHangmau().equals("1"))
				sqlCHECK_HangMau  +=  sqlCHECK_HangMau.length() > 0 ? " union " + kq : kq;
				else
					sqlCHECK +=  sqlCHECK.length() > 0 ? " union " + kq : kq;


		}



		if(sqlCHECK.trim().length() <=0)
		{
			this.msg = "Vui lòng chọn sản phẩm bán!";
			return false;
		}

		try 
		{
			

			data = dbutils.setObject(khId);

			String sql = "select kbh_fk as kbhOld, "+ "\n	( select kbh_fk from khachhang where pk_seq = '"+ this.khId+ "'  ) as kbhNew, "
			+ "\n	( select count(*) from DONHANG_CTKM_TRAKM where DONHANGID = '"+ this.id + "' ) as coKM	"
			+ "\n from donhang where pk_seq = '" + this.id + "'  ";
			System.out.println("_____sql" + sql);
			ResultSet rs = this.db.get(sql);
			String kbhOld = "";
			String kbhNew = "";
			boolean coKM = false;

			try {
				if (rs.next()) {
					kbhOld = rs.getString("kbhOld");
					kbhNew = rs.getString("kbhNew");
					if (rs.getInt("coKM") > 0)
						coKM = true;
				}
				rs.close();
			} catch (Exception ex) {
			}

			if (!kbhNew.equals(kbhOld)) {
				this.msg = "Không thể lưu đơn hàng với khách hàng có kênh mới khác kênh lúc tạo đơn hàng, vui lòng chọn lại khách hàng hay đổi kênh của khách hàng.";
				return false;
			}
			this.kbhId = kbhNew;

			// NEU LA CAP NHAT, MA THAY DOI SP HOAC SO LUONG THI PHAI AP LAI
			// KM
			if (action.equals("save") && coKM) {
				// Đối lại check 1 loạt sản phẩm của đơn hàng luôn, không
				// cấn check từng SP
				sql = "\n select count(dh.sanpham_fk) as sodong  "
					+ "\n from DONHANG_SANPHAM dh " + "\n left join  "
					+ "\n ( " + "\n 	" + sqlCHECK
					+ "\n )dh_sp on dh.sanpham_fk = dh_sp.sanpham_fk "
					+ "\n where donhang_fk = " + this.id
					+ "\n and dh.soluong != dh_sp.soluong";
				int soDONG = 0;
				rs = db.get(sql);
				try {
					if (rs.next()) {
						soDONG = rs.getInt("soDONG");
					}
					rs.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}

				if (soDONG > 0) {
					this.msg = "Khi thay đổi thông tin sản phẩm, số lượng trong đơn hàng, bạn phải bấm áp lại khuyến mại.";
					return false;
				}
			}
		} catch (Exception er) {
			er.printStackTrace();
			this.msg = "Không thể lưu đơn hàng, lỗi không xác định được kênh của khách hàng";
			return false;
		}

		if (this.nvgnId == null || this.nvgnId.length() <= 0) {
			this.msg = "Vui lòng chọn nhân viên giao nhận!";
			return false;
		}

		String query = "";
		try {
			db.update("SET TRANSACTION ISOLATION LEVEL SNAPSHOT;");
			db.update("SET LOCK_TIMEOUT 60000;");

			db.getConnection().setAutoCommit(false);
			String sql1="update donhang set trangthai=0, thoidiemsua = getdate() where pk_seq="+this.id+" and trangthai=0";
			if (db.updateReturnInt(sql1)!= 1)  
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
					return false;
			}

			String ghichuO = this.ghichuoption;
			if(this.ghichuoption.length() == 0)
			{
				ghichuO = null;	
			}

			String kbh_fk = "", BM_fk = "", ASM_fk = "";
			int trangthai = -1;
			data = dbutils.setObject(khId);
			query = "select (select trangthai from DonHang where pk_Seq ='"
				+ this.id
				+ "') as TrangThai,(select kbh_fk from khachhang where pk_seq = ?) as kbh_Fk ";
			ResultSet rs = this.db.get_v2(query,data);
			rs.next();
			kbh_fk = rs.getString("kbh_fk");
			trangthai = rs.getInt("trangthai");

			data = dbutils.setObject(kbh_fk,gsbhId,nppId,kbh_fk,gsbhId);
			query = "select top 1 bm_cn.bm_fk, a.asm_fk from  asm_khuvuc a "
				+ "\n inner join asm on asm.pk_seq=a.asm_fk AND A.NGAYBATDAU <= '"
				+ this.ngaygiaodich
				+ "' AND A.NGAYKETTHUC >= '"
				+ this.ngaygiaodich
				+ "' "
				+ "\n inner join khuvuc kv on kv.pk_seq=a.khuvuc_fk "
				+ "\n inner join bm_chinhanh bm_cn on bm_cn.vung_fk=kv.vung_fk AND bm_cn.NGAYBATDAU <='"
				+ this.ngaygiaodich
				+ "' AND bm_cn.NGAYKETTHUC >= '"
				+ this.ngaygiaodich
				+ "' "
				+ "\n inner join nhaphanphoi npp on npp.khuvuc_fk=kv.pk_seq "
				+ "\n inner join bm on bm.pk_seq=bm_cn.bm_fk and bm.kbh_fk = ? " 
				+ " and bm.dvkd_fk=(select dvkd_fk from giamsatbanhang where pk_seq = ? "
				+ ") "
				+ "\n where npp.pk_seq = ? "
				+ " and asm.kbh_fk = ? "
				+ "\n and asm.dvkd_fk=(select dvkd_fk from giamsatbanhang where pk_seq = ? ) ";

			System.out.println("__" + query);

			rs = this.db.get_v2(query,data);
			while (rs.next()) {
				BM_fk = rs.getString("bm_fk");
				ASM_fk = rs.getString("asm_fk");
			}

			if (BM_fk == null || BM_fk.length() == 0)
				BM_fk = "NULL";
			if (ASM_fk == null || ASM_fk.length() == 0)
				ASM_fk = "NULL";

			String sql = "select isnull(b.khongVat,0)khongVat from khachhang a inner join loaicuahang b on a.LCH_FK = b.PK_SEQ where a.PK_SEQ ="+ this.khId;
			rs = db.get(sql);
			int khongVat = 0;
			if(rs!=null){
				if(rs.next()){
					khongVat = rs.getInt("khongVat");
				}
			}
			rs.close();
			double tyleVat = khongVat == 1 ? 1 : 1.08;
			
			data = dbutils.setObject(this.ngaygiaodich,ghichuO, nocu, ghichu, ddkdId,gsbhId,khId,khoId,tongchietkhau,diachigiaohang,getDateTime(),userId,
					tongtientruocVAT,VAT,kbh_fk,nvgnId,sotiengiam,ngaygh,tyleVat);

			double _slCombo = geso.dms.center.util.Utility.parseDouble(this.soluongSpComBo.replace(",",""));
			double _giaCombo = geso.dms.center.util.Utility.parseDouble(this.giaSpComBo.replace(",",""));
			
			query = "\n update donhang set tenSpComBo=N'"+this.tenSpComBo+"',giaSpComBo="+_giaCombo+",soluongSpComBo="+_slCombo+"" +
					"\n		,ngaynhap = ?,ghichu_fk = ?, PDA_CapNhat = 0,nocu = ?, ghichu =?, ddkd_fk = ?, gsbh_fk = ?, khachhang_fk =?, " +
			"\n KHO_FK = ?, chietkhau = ?, diachigiao = ?, ngaysua = ?, nguoisua = ?, " +
			"\n tonggiatri = ?, vat = ?, " +
			"\n kbh_fk = ?,nvgn_fk = ?, sotiengiam = ?, " +
			"\n FlagModified = '1', ngaygiaohang = ?, tylevat =? where pk_seq = "+id+" and TrangThai=0 ";


			if (db.update_v2(query, data) != 1) {
				dbutils.viewQuery(query, data);
				db.getConnection().rollback();
				this.msg = "Vui lòng Reload lại đơn hàng, trạng thái đơn hàng không hợp lệ!";
				return false;
			}
			query = " update dh  set LCH_FK = lch.PK_SEQ , tyleVat = case when lch.khongVat = 1 then 1 else 1.08 end "+
					"\n from donhang dh "+
					"\n inner join KHACHHANG kh on kh.PK_SEQ = dh.KHACHHANG_FK "+
					"\n inner join LOAICUAHANG lch on lch.PK_SEQ = kh.LCH_FK "+
					"\n where dh.PK_SEQ =" + this.id;
			if (db.updateReturnInt(query)!= 1) {
				System.out.println("query = "+ query);
				this.msg = "Lỗi khi lưu đơn hàng 2";
				this.db.getConnection().rollback();
				return false;
			}

			// Cap nhat kho
			query = "\n delete from donhang_sanpham where donhang_fk = "+ this.id +
			"\n and donhang_Fk in (select pk_seq from DonHang where trangthai = 0 and pk_Seq = "+this.id + ")";
			if (db.updateReturnInt(query) <= 0) {
				db.getConnection().rollback();
				this.msg = "Không thể cập nhật sản phẩm trong đơn hàng 1";
				return false;
			}
			String dongiaGoc = " [dbo].[GiaBanLeNppSanPham]("+this.khId+",dh.kbh_fk,dh.npp_fk,sp.sanpham_fk, dh.ngaynhap) ";
			String giamua = this.donhangkhac.equals("1") ? "0" : "  "+dongiaGoc+" + sp.tiengiamtru  ";
			
			query = "if exists (select PK_SEQ from DONHANG WHERE PK_SEQ = '"
				+ this.id
				+ "' AND TRANGTHAI = 0)"
				+ "\n insert donhang_sanpham( sanpham_fk, donhang_fk, soluong, kho_fk, giamua, chietkhau, dongiaGOC,BangGia_fk, ptCKDLN, ptCKTT, ptCKDH,tiengiamtru ) "
				+ "\n select sp.sanpham_fk, dh.pk_seq , sp.soluong, dh.kho_fk,"+giamua+", sp.chietkhau, " +
				"\n "+dongiaGoc+" dgGoc,sp.BangGia_fk, sp.chietkhauDLN, sp.chietkhauTT, sp.chietkhauDH, sp.tiengiamtru  "
				+ "\n from ( "
				+ sqlCHECK
				+ " ) sp inner join donhang dh on dh.pk_seq =  "
				+ this.id;
			if (db.updateReturnInt(query) < 0) {
				db.getConnection().rollback();
				this.msg = "Không thể cập nhật sản phẩm trong đơn hàng 2";
				return false;
			}

			query = "select count(*) sl from donhang_sanpham where soluong<=0 and donhang_fk= "+this.id;
			ResultSet rsc = db.get(query);
			rsc.next();
			int sl = rsc.getInt("sl");
			rsc.close();
			if (sl > 0) {
				db.getConnection().rollback();
				this.msg = "kiểm tra lại số lượng bán, không được nhập số lượng bằng 0!";
				return false;
			}
			if(!donhangkhac.equals("1")){/*
				query = "select count(*) sl from donhang_sanpham where giamua <=0  and donhang_fk ="+ this.id;
				System.out.println("check dh gia =0 " + query);
				rsc = db.get(query);
				rsc.next();
				sl = rsc.getInt("sl");
				rsc.close();
				if (sl > 0) {
					db.getConnection().rollback();
					this.msg = "Vui lòng kiểm tra lại giá, không được nhập sản phẩm có giá bằng 0.";
					return false;
				}
			*/}
			query = "delete from DonHang_CTKM_TRAKM WHERE DONHANGID = '" + this.id + "' and isnull(ap_dung,0)  = 1  ";
			System.out.println("in ra xem nào ___"+query);
			if (!db.update(query)) {
				db.getConnection().rollback();
				this.msg = "Vui lòng kiểm tra lại số lượng bán, không được nhập số lượng bằng 0.";
				return false;
			}

			query = "delete from DonHang_CTKM_TRAKM_chitiet WHERE DONHANGID='" + this.id	+ "' and isnull(ap_dung,0)  = 1 ";

			if (!db.update(query)) {
				db.getConnection().rollback();
				this.msg = "Vui lòng kiểm tra lại số lượng bán, không được nhập số lượng bằng 0.";
				return false;
			}
			if(sqlCHECK_HangMau.length() > 0)
			{
				query = " insert donhang_ctkm_trakm (donhangId,ctkmId,trakmId,SOXUAT,TONGGIATRI,SPMA,Sanpham_fk,SOLUONG,ap_dung) " +
				"\n select "+this.id+",ctkm.pk_seq ,tkm.pk_seq ,sp.soluong,0 ,sp.ma,sp.Sanpham_fk,sp.soluong,tkm.ap_dung " +
				"\n from " +
				"\n (" +
				sqlCHECK_HangMau +
				"\n )sp " +
				"\n inner join ctkhuyenmai ctkm on ctkm.loaict = 99  "  +
				"\n inner join trakhuyenmai tkm on tkm.ap_dung = 1 " ;
				if (db.updateReturnInt(query) <= 0) {
					System.out.println(" query = " + query);
					db.getConnection().rollback();
					this.msg = "Lỗi khi lưu hàng mẫu !";
					return false;
				}
			}
			
			msg = Utility.Update_GiaTri_DonHang(this.id, db);
			if (this.msg.trim().length() > 0) {
				db.getConnection().rollback();
				return false;
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e) {
			e.printStackTrace();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
		}
		return true;


	}

	public boolean UpdateDhXuatKho(List<ISanpham> splist) {

		if(!Utility.KiemTra_PK_SEQ_HopLe( this.id, "Donhang", this.db))
		{
			this.msg = "Định danh đơn hàng không hợp lệ!";
			return false;
		}

		Object[] data = null;
		Object[] data2 = null;
		this.ngaysua = getDateTime();
		this.nguoisua = this.userId;

		try {

			// Kiểm tra nếu đơn hàng làm về tháng trước, chỉ được phép dời vào
			// ngày cuối tháng trước !
			String sqlkt = " select 1 from DONHANG dh "
				+ "\n where  dh.pk_seq = '"
				+ this.id
				+ "' and  '"
				+ ngaygiaodich
				+ "' <  dh.NGAYNHAP "
				+ "\n and  MONTH(dh.NGAYNHAP) <> MONTH('"
				+ ngaygiaodich
				+ "')  "
				+ "\n AND (select Replace(convert(char(10), DATEADD(DAY, -1, cast(substring('"
				+ this.getDateTime()
				+ "',1,8)+'01' as datetime) ), 102) , '.', '-') ) <> '"
				+ ngaygiaodich
				+ "' "
				+ "\n AND DAY(dh.NGAYNHAP) > ("
				+ "\n  case when ( (SELECT TOP(1) ngayluidh From THIETLAPKHOASO ) > 0 ) "
				+ "\n  then    (SELECT TOP(1) ngayluidh From THIETLAPKHOASO )  "
				+ "\n  when (SELECT TOP(1) ngayluidh From THIETLAPKHOASO )  = 0 "
				+ "\n  then    (SELECT TOP(1) ngaylui From nhaphanphoi where pk_seq = dh.npp_fk )"
				+ "\n    else   32 end" + " ) ";
			System.out.println("SQLKT: " + sqlkt);

			ResultSet rskt = db.get(sqlkt);
			if (rskt != null) {
				try {
					if (rskt.next()) {
						this.msg = "Không được phép lùi ngày đơn hàng trước ngày cuối tháng trước !";
						return false;
					}
					rskt.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}

			}

			db.update("SET TRANSACTION ISOLATION LEVEL SNAPSHOT;");
			db.update("SET LOCK_TIMEOUT 60000;");

			String query = "select khachhang_fk khid from donhang where pk_seq = "+id;
			ResultSet temprs = db.get(query);
			temprs.next();
			khId = temprs.getString("khId");
			temprs.close();

			System.out.println("cap nhat da xuat kho UpdateDhXuatKho");
			String kbh_fk = "", BM_fk = "", ASM_fk = "";
			int trangthai = -1;
			int DonHangChoXuLy = 0;
			query = "select  (select count(*) From DonHangChoXuLy where donhang_fk='"
				+ this.id
				+ "') as DonHangChoXuLy , (select trangthai from DonHang where pk_Seq ='"
				+ this.id
				+ "') as TrangThai ,(select kbh_fk from khachhang where pk_seq = "
				+ this.khId + " ) as kbh_Fk ";
			System.out.println("1. " + query);
			ResultSet rs = this.db.get(query);
			rs.next();
			kbh_fk = rs.getString("kbh_fk");
			trangthai = rs.getInt("trangthai");
			// DonHangChoXuLy =rs.getInt("DonHangChoXuLy");

			query = "select top 1 bm_cn.bm_fk, a.asm_fk from  asm_khuvuc a "
				+ "inner join asm on asm.pk_seq=a.asm_fk AND A.NGAYBATDAU <= '"
				+ this.ngaygiaodich
				+ "inner join khuvuc kv on kv.pk_seq=a.khuvuc_fk "
				+ "inner join bm_chinhanh bm_cn on bm_cn.vung_fk=kv.vung_fk AND bm_cn.NGAYBATDAU <='"
				+ this.ngaygiaodich
				+ "' AND bm_cn.NGAYKETTHUC >= '"
				+ this.ngaygiaodich
				+ "' "
				+ "inner join nhaphanphoi npp on npp.khuvuc_fk=kv.pk_seq "
				+ "inner join bm on bm.pk_seq=bm_cn.bm_fk and bm.kbh_fk = "
				+ kbh_fk
				+ " and bm.dvkd_fk=(select dvkd_fk from giamsatbanhang where pk_seq = "
				+ this.gsbhId
				+ ") "
				+ "where npp.pk_seq = "
				+ this.nppId
				+ " and asm.kbh_fk = "
				+ kbh_fk
				+ " and asm.dvkd_fk=(select dvkd_fk from giamsatbanhang where pk_seq = "
				+ this.gsbhId + " )	";

			System.out.println("2. " + query);

			rs = this.db.get(query);
			while (rs.next()) {
				BM_fk = rs.getString("bm_fk");
				ASM_fk = rs.getString("asm_fk");
			}

			if (BM_fk == null || BM_fk.length() == 0)
				BM_fk = "NULL";
			if (ASM_fk == null || ASM_fk.length() == 0)
				ASM_fk = "NULL";

			this.db.getConnection().setAutoCommit(false);
			
			String sql = "select isnull(b.khongVat,0)khongVat from khachhang a inner join loaicuahang b on a.LCH_FK = b.PK_SEQ where a.PK_SEQ ="+ this.khId;
			rs = db.get(sql);
			int khongVat = 0;
			if(rs!=null){
				if(rs.next()){
					khongVat = rs.getInt("khongVat");
				}
			}
			rs.close();
			double tyleVat = khongVat == 1 ? 1 : 1.08;

			query = "  update donhang set ngaynhap ='" + this.ngaygiaodich
			+ "', ddkd_fk ='" + this.ddkdId + "', gsbh_fk ='"
			+ this.gsbhId + "', khachhang_fk ='" + this.khId
			+ "', KHO_FK = '" + this.khoId + "',  " + "	chietkhau ='"
			+ this.tongchietkhau + "',  diachigiao = N'"
			+ this.diachigiaohang + "', " + "	ngaysua ='"
			+ getDateTime() + "', nguoisua ='" + this.userId
			+ "', tonggiatri ='" + this.tongtientruocVAT + "', vat ='"
			+ this.VAT + "', kbh_fk ='" + kbh_fk + "',tylevat= '"+tyleVat+"' "
			+ " where pk_Seq='" + this.id + "' and TrangThai=3   ";
			System.out.println("3. " + query);
			if (this.db.updateReturnInt(query) != 1) {
				this.db.getConnection().rollback();
				this.msg = "1.Khong the cap nhat table 'Don Hang'(ĐH đã chốt hoặc xảy ra lỗi) , "
					+ query;
				return false;
			}

			String sqlCHECK = "";
			for (int m = 0; m < splist.size(); m++) {

				ISanpham sp = splist.get(m);
				Object[] temp = dbutils.setObject(sp.getSoluong(), sp
						.getDongia().replaceAll(",", ""), sp.getChietkhau()
						.replaceAll(",", ""), sp.getGiagoc()
						.replaceAll(",", ""), sp.getBgId(), sp
						.getChietkhauDLN().replaceAll(",", ""), sp
						.getChietkhauTT().replaceAll(",", ""), sp
						.getChietkhauDH().replaceAll(",", ""), sp
						.getMasanpham());

				if (m == 0) {
					data2 = temp;
				}
				else {
					data2 = appendObjectArrayValue(data2, temp);
				}

				sqlCHECK += "\n select pk_seq as sanpham_fk, ? as soluong, "
					+ "\n ? as dongia," + "\n ? as chietkhau, "
					+ "\n ? dongiaGOC, " + "\n ? BangGia_fk, "
					+ "\n ? as chietkhauDLN, " + "\n ? as chietkhauTT, "
					+ "\n ? as chietkhauDH "
					+ "\n from SANPHAM where ma = ? ";
				if (m < splist.size() - 1)
					sqlCHECK += " union ";
			}
			System.out.println("[danh sach sanpham] " + sqlCHECK);

			if (this.trangthai.equals("3")) {
				sql = "select dh.soluong  "
					+ "from "
					+ " ( "
					+ sqlCHECK
					+ " ) "
					+ " dh "
					+ " inner join "
					+ " ( "
					+ "  select sanpham_fk, soluong from DONHANG_SANPHAM where donhang_fk = '"
					+ this.id + "' " + " ) "
					+ " dh_sp on dh.sanpham_fk = dh_sp.sanpham_fk"
					+ " where dh.soluong > dh_sp.soluong ";
				System.out.println("Check So Luong: " + sql);
				rs = db.get_v2(query, data);
				if (rs != null) {
					if (rs.next()) {

						this.msg = "Đơn hàng đã xuất kho không được phép sửa số lượng lớn hơn số lượng hiện có!";
						rs.close();
						return false;
					}
				}
			}

			geso.dms.center.util.Utility util = new geso.dms.center.util.Utility();
			query = "SELECT DH.NGAYNHAP,SP.SANPHAM_FK,SP.KHO_FK,KHO.NPP_FK,KHO.KBH_FK,SP.SOLUONG, a.ten as tensp from DONHANG dh inner join DONHANG_SANPHAM sp on dh.PK_SEQ = sp.DONHANG_FK   "
				+ "\n inner join sanpham a on a.pk_seq = sp.sanpham_fk "
				+ "\n inner join nhapp_kho kho on sp.SANPHAM_FK = kho.SANPHAM_FK and sp.KHO_FK = kho.KHO_FK and kho.NPP_FK = dh.NPP_FK and kho.KBH_FK = dh.KBH_FK "
				+ "\n where dh.PK_SEQ = " + this.id + " and dh.trangthai=3";

			System.out.println("UPDATE NPP KHO: " + query);
			ResultSet ckRs = db.get(query);
			while (ckRs.next()) {
				String kho_fk = ckRs.getString("kho_fk");
				String nppId = ckRs.getString("npp_fk");
				String kenh = ckRs.getString("kbh_fk");
				String sanpham_fk = ckRs.getString("sanpham_fk");
				String ngaynhap = ckRs.getString("ngaynhap");
				String tensp = ckRs.getString("tensp");
				Double soluong = ckRs.getDouble("soluong");

				msg = util.Update_NPP_Kho_Sp(ngaynhap, this.id,
						"updae xk ĐƠN HÀNG_1454552", db, kho_fk, sanpham_fk,
						nppId, kenh, 0.0, (-1) * soluong, soluong, 0.0);
				if (msg.length() > 0) {
					db.getConnection().rollback();
					this.msg = "Lỗi kho khi tạo đơn hàng: " + this.msg;
					return false;
				}
			}

			query = "SELECT DH.NGAYNHAP,SP.SANPHAM_FK,SP.KHO_FK,KHO.NPP_FK,KHO.KBH_FK,SP.SOLUONG, a.ten as tensp, sp.ngaynhapkho from DONHANG dh inner join DONHANG_SANPHAM_chitiet sp on dh.PK_SEQ = sp.DONHANG_FK   "
				+ "\n inner join sanpham a on a.pk_seq = sp.sanpham_fk "
				+ "\n inner join nhapp_kho_chitiet kho on sp.SANPHAM_FK = kho.SANPHAM_FK and sp.KHO_FK = kho.KHO_FK and kho.NPP_FK = dh.NPP_FK and kho.KBH_FK = dh.KBH_FK "
				+ "\n where dh.PK_SEQ = " + this.id + " and dh.trangthai=3";

			System.out.println("UPDATE NPP KHO: " + query);
			ckRs = db.get(query);
			while (ckRs.next()) {
				String kho_fk = ckRs.getString("kho_fk");
				String nppId = ckRs.getString("npp_fk");
				String kenh = ckRs.getString("kbh_fk");
				String sanpham_fk = ckRs.getString("sanpham_fk");
				String ngaynhap = ckRs.getString("ngaynhap");
				String tensp = ckRs.getString("tensp");
				Double soluong = ckRs.getDouble("soluong");
				String ngaynhapkho = ckRs.getString("NGAYNHAPKHO");
				String msg1 = util.Update_NPP_Kho_Sp_Chitiet(ngaynhap,
						"update xkctdonhang DHID: " + this.id, db, kho_fk,
						sanpham_fk, nppId, kenh, ngaynhapkho, 0,
						(-1) * soluong, soluong, 0, 0);
				if (msg1.length() > 0) {
					this.msg = msg1;
					db.getConnection().rollback();
					return false;
				}
			}

			query = "\n delete from donhang_sanpham where donhang_fk = "+ this.id+
			"\n and donhang_Fk in (select pk_seq from DonHang where trangthai = 3 and pk_Seq = "+ this.id + ") ";
			System.out.println("[Xoá danh sách sản phẩm] " + query);
			if (this.db.updateReturnInt(query) <= 0) {
				this.db.getConnection().rollback();
				this.msg = "3. Không thể cập nhật sản phẩm cho đơn hàng.";
				return false;
			}

			query = "delete from donhang_sanpham_chitiet where donhang_fk = "+ this.id + 
			"\n and donhang_Fk in (select pk_seq from DonHang where trangthai = 3 and pk_Seq = "+ this.id + ") ";
			System.out.println("[Xoá danh sách sản phẩm chi tiết] " + query);
			if (this.db.updateReturnInt(query) < 0) {
				this.db.getConnection().rollback();
				this.msg = "3.1 Không thể cập nhật chi tiết sản phẩm cho đơn hàng.";
				return false;
			}

			// Chèn donhang_sanpham tận dung luôn câu SQL bên trên, kể cả hàm
			// cập nhật tồn kho, cũng chỉ chạy 1 lượt
			query = "if exists (select PK_SEQ from DONHANG WHERE PK_SEQ = '"
				+ this.id
				+ "' AND TRANGTHAI = 3)"
				+ "insert donhang_sanpham( sanpham_fk, donhang_fk, soluong, kho_fk, giamua, chietkhau, dongiaGOC,BangGia_fk, ptCKDLN, ptCKTT, ptCKDH  ) "
				+ "select sp.sanpham_fk, '"
				+ this.id
				+ "', sp.soluong, '"
				+ this.khoId
				+ "', sp.dongia, sp.chietkhau ,sp.dongiaGOC,sp.BangGia_fk, sp.chietkhauDLN, sp.chietkhauTT, sp.chietkhauDH  "
				+ "from ( " + sqlCHECK + " ) sp ";
			System.out.println("4. " + query);
			if (db.update_v2(query, data2) < 0) {
				dbutils.viewQuery(query, data);
				this.db.getConnection().rollback();
				this.msg = "4. Không thể cập nhật sản phẩm cho đơn hàng.";
				return false;
			}

			query = "\n SELECT DH.NGAYNHAP,SP.SANPHAM_FK,SP.KHO_FK,KHO.NPP_FK,KHO.KBH_FK,SP.SOLUONG, a.ten as tensp " +
			"\n from DONHANG dh inner join DONHANG_SANPHAM sp on dh.PK_SEQ = sp.DONHANG_FK " +
			"\n inner join sanpham a on a.pk_seq = sp.sanpham_fk " +
			"\n inner join nhapp_kho kho on sp.SANPHAM_FK = kho.SANPHAM_FK and sp.KHO_FK = kho.KHO_FK " +
			"\n		and kho.NPP_FK = dh.NPP_FK and kho.KBH_FK = dh.KBH_FK " +
			"\n where dh.PK_SEQ = " + this.id + " and dh.trangthai=0";
			ckRs = db.get(query);
			while (ckRs.next()) {
				String kho_fk = ckRs.getString("kho_fk");
				String nppId = ckRs.getString("npp_fk");
				String kbhH_fk = ckRs.getString("kbh_fk");
				String sanpham_fk = ckRs.getString("sanpham_fk");
				String tensp = ckRs.getString("tensp");
				String ngaynhap = ckRs.getString("ngaynhap");
				Double soluong = ckRs.getDouble("soluong");

				msg = util.Update_NPP_Kho_Sp(ngaynhap, this.id,
						"CẬP NHÂT ĐƠN HÀNG_1718", db, kho_fk, sanpham_fk,
						nppId, kbhH_fk, 0.0, soluong, (-1) * soluong, 0.0);
				if (msg.length() > 0) {
					db.getConnection().rollback();
					this.msg = "Lỗi kho khi tạo đơn hàng: " + this.msg;
					return false;
				}

				query = "\n select KHO_FK,SANPHAM_FK,KBH_FK,NGAYNHAPKHO,available  from NHAPP_KHO_CHITIET " +
				"\n where NPP_FK ="+ nppId+ " and KBH_FK= "+ kbh_fk+ " and KHO_FK="+ kho_fk+
				"\n and SANPHAM_FK = "+ sanpham_fk+ " AND AVAILABLE >0  and NGAYNHAPKHO<='"+ ngaynhap + "'" + 
				"\n order by NGAYNHAPKHO,AVAILABLE ";
				ResultSet rssp = db.get(query);
				double soluongdenghi = soluong;

				while (rssp.next() && soluongdenghi > 0) {
					double soluong_avai = rssp.getDouble("AVAILABLE");
					double soluongcapnhat = 0;
					if (soluong_avai > soluongdenghi) {
						soluongcapnhat = soluongdenghi;
						soluongdenghi = 0;
					} else {
						soluongcapnhat = soluong_avai;
						soluongdenghi = soluongdenghi - soluong_avai;
					}

					String _khoid = rssp.getString("kho_fk");
					String _kbhid = rssp.getString("KBH_FK");
					String ngaynhapkho = rssp.getString("NGAYNHAPKHO");

					query = "\n insert donhang_sanpham_chitiet( sanpham_fk, donhang_fk, soluong, kho_fk,ngaynhapkho ) "
						+ "\n select "
						+ sanpham_fk
						+ ", '"
						+ this.id
						+ "', "
						+ soluongcapnhat
						+ ", '"
						+ kho_fk
						+ "','"
						+ ngaynhapkho + "' ";

					if (db.updateReturnInt(query) <= 0) {
						this.msg = "Không thể cập nhật : " + query;
						db.getConnection().rollback();
						return false;
					}

					String msg1 = util.Update_NPP_Kho_Sp_Chitiet(ngaynhap,
							"update donhang DHID: " + this.id, db, _khoid,
							sanpham_fk, nppId, _kbhid, ngaynhapkho, 0,
							soluongcapnhat, (-1) * soluongcapnhat, 0, 0);
					if (msg1.length() > 0) {
						this.msg = msg1;
						db.getConnection().rollback();
						return false;
					}

				}
				rssp.close();

				if (soluongdenghi != 0) {

					this.msg = "Số lượng đề xuất trong lô chi tiết của sản phẩm "
						+ tensp
						+ "   tới ngày (ngày cấu hình hóa đơn)"
						+ ngaynhap
						+ " không còn đủ, "
						+ " vui lòng kiểm tra báo cáo ( xuất nhập tồn,tồn hiện tại) theo lô để biết thêm chi tiết. ";
					db.getConnection().rollback();
					return false;

				}

			}
			System.out.println("[DONHANG]" + msg);
			// tao phieu thu hoi neu co
			if (this.trangthai.equals("3")) {
				this.createPxkId();
				String msg = this.createPth(this.pxkId, this.db);
				System.out.println("Messege Phieu thu hoi: " + msg);
				if (msg.length() > 0) {
					this.db.getConnection().rollback();
					this.msg = msg;
					return false;
				}
			}

			// Check XNT. Sai XNT không lưu đơn hàng.
			/*
			 * query =
			 * "select * from [dbo].[ufn_Check_XNT_KhiChotDH]("+nppId+",'"
			 * +ngaygiaodich+"',"+this.id+")"; ResultSet rsXNT = db.get(query);
			 * if(rsXNT != null) { String loi = ""; while(rsXNT.next()) { loi+=
			 * "\n Đơn Hàng "+this.id+". Sản phẩm  "+rs.getString("tensp")+
			 * ". Bị sai XNT không thể chốt đơn !"; } if(loi.length() > 0) { msg
			 * = loi; db.getConnection().rollback();
			 * System.out.println("Sai XNT : "+query); return false; }
			 * rsXNT.close(); }
			 */
			query = "	update DONHANG set TONGGIATRI= "
				+ "			isnull(( "
				+ "				select SUM(round(b.soluong*b.giamua,0))  as TongGiaTri "
				+ "				from DONHANG a inner join DONHANG_SANPHAM b on b.DONHANG_FK=a.PK_SEQ "
				+ "				where  a.PK_SEQ=DONHANG.PK_SEQ "
				+ "				group by b.DONHANG_FK "
				+ "			),0) - "
				+ "			round(isnull(( "
				+ "				select SUM(round(b.soluong*b.giamua,0))  as TongGiaTri "
				+ "				from DONHANG a inner join DONHANG_SANPHAM b on b.DONHANG_FK=a.PK_SEQ "
				+ "				where  a.PK_SEQ=DONHANG.PK_SEQ "
				+ "				group by b.DONHANG_FK "
				+ "			),0)*("
				+ this.tongchietkhau
				+ "/100.0),0) - "
				+ "			isnull "
				+ "				(( "
				+ "					select SUM(round(TONGGIATRI,0)) from DONHANG_CTKM_TRAKM km  "
				+ "					where km.DONHANGID=DONHANG.PK_SEQ and SPMA is null "
				+ "				),0)   - (" + this.sotiengiam + " )"
				+ "		from DONHANG " + "		where pk_Seq='" + this.id + "' ";
			if (!this.db.update(query)) {
				this.db.getConnection().rollback();
				this.msg = "Lỗi phát sinh khi cập nhật giá trị đơn hàng "
					+ query;
				return false;
			}

			/*
			 * query ="select count(*) as SoDong  "+ "	from  "+ "	(  "+
			 * "		select sanpham_fk,SoLuong,Kho_FK  "+
			 * "		from donhangchoxuly_sanpham where donhang_fk='"+this.id+"'  "+
			 * "	)a full outer  join  "+ " 	(  "+
			 * "	 	select sanpham_fk,SoLuong,Kho_FK  "+
			 * "		from DonHang_SanPham where donhang_fk='"+this.id+"'  "+
			 * "	)b on a.SanPham_FK=b.SanPham_FK  "+
			 * "	where isnull(a.SoLuong,0)!=isnull(b.SoLuong,0)" ;
			 * System.out.println("[donhangchoxuly_sanpham]"+query); //rs
			 * =this.db.get(query); int SoDong=0; while(rs.next()) { SoDong=
			 * rs.getInt("SoDong"); } if(SoDong!=0 && DonHangChoXuLy!=0 ) {
			 * query=
			 * "insert into donhangchoxuly_sanpham(DonHangChoXuLy_FK,DonHang_fk,SanPham_FK,Kho_FK,GiaMua,ChietKhau,DonGiaGoc,BangGia_FK,ptCKTT,ptCKDLN,ptCKDH,SoLuong) "
			 * +
			 * "	select  (select pk_seq From DonHangChoXuLy where donhang_fk='"
			 * +this.id+"'),'"+this.id+"', "+
			 * "	isnull(a.sanpham_fk,b.sanpham_fk) as sanpham_fk,isnull(a.KHO_FK,b.KHO_FK) as KHO_FK, "
			 * +
			 * "	isnull(a.GIAMUA,b.GIAMUA) as GIAMUA,isnull(a.CHIETKHAU,b.CHIETKHAU) as CHIETKHAU, "
			 * +
			 * "	isnull(a.DonGiaGoc,b.DonGiaGoc) as DonGiaGoc,isnull(a.BangGia_FK,b.BangGia_FK) as BangGia_FK, "
			 * + "	isnull(a.ptCKTT,b.ptCKTT) as ptCKTT, "+
			 * "	isnull(a.ptCKDLN,b.ptCKDLN) as ptCKDLN,isnull(a.ptCKDH,b.ptCKDH) as ptCKDH,NULL as SoLuong  "
			 * + " from "+ "( "+
			 * "	select SanPham_FK,DonHang_FK,SoLuong,KHO_FK,GIAMUA,CHIETKHAU,DonGiaGoc,BangGia_FK,ptCKTT,ptCKDLN,ptCKDH  "
			 * +
			 * "	from donhangchoxuly_sanpham where donhang_fk='"+this.id+"'  "+
			 * " )a full outer  join  "+ " ( "+
			 * "	select SanPham_FK,DonHang_FK,SoLuong,KHO_FK,GIAMUA,CHIETKHAU,DonGiaGoc,BangGia_FK,ptCKTT,ptCKDLN,ptCKDH "
			 * + "	from DonHang_SanPham where donhang_fk='"+this.id+"'  "+
			 * " )b on a.SanPham_FK=b.SanPham_FK and a.KHO_FK=b.Kho_FK  "+
			 * " where a.sanpham_fk is null ";
			 * 
			 * System.out.println("6. "+query); if(!db.update(query)) { this.msg
			 * = "Lỗi cập nhật đơn hàng"+ query;
			 * this.db.getConnection().rollback(); return false; } query=
			 * "	update a set DaGiao=b.SoLuong,ConLai=a.SoLuong-b.SoLuong  "+
			 * "		from donhangchoxuly_sanpham a   "+
			 * "			inner join DonHang_SanPham b on b.donhang_fk=a.donhang_fk and a.sanpham_fk=b.sanpham_fk "
			 * + "   where a.donhang_fk='"+this.id+"'  ";
			 * 
			 * System.out.println("7. "+query); if(!db.update(query)) { this.msg
			 * = "Lỗi cập nhật đơn hàng"+ query;
			 * this.db.getConnection().rollback(); return false; }
			 * 
			 * query=
			 * "INSERT INTO DonHangChoXuLy_CTKM_TRAKM(	DHCXLID ,DonHang_FK,CTKMID,TRAKMID,SOXUAT,TONGGIATRI,SPMA,SOLUONG,CHIETKHAU,LOAI) "
			 * +
			 * "	select    (select pk_seq From DonHangChoXuLy where donhang_fk='"
			 * +this.id+"'),'"+this.id+"', "+
			 * "		isnull(a.CTKMID,b.CTKMID) as CTKMID,isnull(a.TRAKMID,b.TRAKMID) as TRAKMID, "
			 * +
			 * "		isnull(a.SOXUAT,b.SOXUAT) as SOXUAT,isnull(a.TONGGIATRI,b.TONGGIATRI) as TONGGIATRI, "
			 * + "		isnull(a.SPMA,b.SPMA) as SPMA,NULL as SOLUONG, "+
			 * "		isnull(a.CHIETKHAU,b.CHIETKHAU) as CHIETKHAU, "+
			 * "		isnull(a.LOAI,b.LOAI) as LOAI "+ "	from "+ "	( "+
			 * "		select CTKMID,TRAKMID,SOXUAT,TONGGIATRI,SPMA,SOLUONG,CHIETKHAU,LOAI,DONHANG_FK "
			 * +
			 * "		from [DonHangChoXuLy_CTKM_TRAKM] where donhang_fk='"+this.id+
			 * "' "+ "	)a full outer  join "+ "	( "+
			 * "	 	select CTKMID,TRAKMID,SOXUAT,TONGGIATRI,SPMA,SOLUONG,CHIETKHAU,LOAI,DONHANGID as DONHANG_FK "
			 * + "		from [DonHang_CTKM_TRAKM] where DONHANGID= '"+this.id+"' "+
			 * "	)b on a.CTKMID=b.CTKMID and a.TRAKMID=b.TRAKMID AND A.SPMA=B.SPMA "
			 * + " where a.CTKMID is null  ";
			 * 
			 * System.out.println("8. "+query); if(!this.db.update(query)) {
			 * this.msg = "Lỗi cập nhật đơn hàng"+ query;
			 * this.db.getConnection().rollback(); return false; }
			 * 
			 * query=
			 * "	update a set DaGiao=b.SoLuong,ConLai=a.SoLuong-b.SoLuong  "+
			 * "		from DonHangChoXuLy_CTKM_TRAKM a   "+
			 * "			inner join DonHang_CTKM_TRAKM b on b.CTKMID=a.CTKMID and a.TRAKMID=b.TRAKMID AND A.SPMA=B.SPMA "
			 * + "   where A.donhang_fk='"+this.id+"'  ";
			 * System.out.println("9. "+query); if(!this.db.update(query)) {
			 * this.msg = "Lỗi cập nhật đơn hàng"+ query;
			 * this.db.getConnection().rollback(); return false; }
			 */

			/*
			 * query = "	update b set BOOKED= " +
			 * "		isnull((    select a.SOLUONG from v_Booked a " +
			 * "		where b.NPP_FK=a.NPP_FK and a.KBH_FK=b.KBH_FK and a.KHO_FK=b.KHO_FK and a.SANPHAM_FK=b.SANPHAM_FK "
			 * +
			 * "		),0),AVAILABLE=b.SOLUONG-isnull((  select a.SOLUONG from v_Booked a "
			 * +
			 * "		where b.NPP_FK=a.NPP_FK and a.KBH_FK=b.KBH_FK and a.KHO_FK=b.KHO_FK and a.SANPHAM_FK=b.SANPHAM_FK "
			 * + "		),0)  " + "	from NHAPP_KHO b  " + "	where  B.NPP_FK='"+
			 * this.nppId+ "' AND b.BOOKED!=  " +
			 * "	isnull((    select a.SOLUONG from v_Booked a  " +
			 * "	where b.NPP_FK=a.NPP_FK and a.KBH_FK=b.KBH_FK and a.KHO_FK=b.KHO_FK and a.SANPHAM_FK=b.SANPHAM_FK "
			 * + "	),0)  ";
			 * 
			 * System.out.println("10. " + query); if (!this.db.update(query)) {
			 * this.msg = "Lỗi cập nhật đơn hàng" + query;
			 * this.db.getConnection().rollback(); return false; }
			 */
			// }
			query = "select tonggiatri from donhang where pk_seq  = " + this.id
			+ "  ";
			rs = db.get(query);
			while (rs.next()) {
				String msg1 = "";
				if (rs.getDouble("tonggiatri") < 0) {
					msg1 = "vui lòng check đơn hàng ";
				}
				if (msg1.length() > 0) {
					this.db.getConnection().rollback();
					this.msg = "Lỗi phát sinh khi cập nhật giá trị đơn hàng do tổng giá trị âm "
						+ query;
					return false;
				}

			}
			System.out.println("11.:::::");
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			System.out.println("12.:::::");
		} catch (Exception e1) {
			try {
				this.db.getConnection().rollback();
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.msg = "Loi :" + e1.toString();
			e1.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean ChotDh(List<ISanpham> splist) {

		if (!Utility.KiemTra_PK_SEQ_HopLe(this.id, "Donhang", this.db))
		{
			this.msg = "Định danh đơn hàng không hợp lệ!";
			return false;
		}

		if (this.spThieuList.size() > 0) {
			this.msg = "Trong kho không đủ số lượng một số sản phẩm bạn chọn, vui lòng kiểm tra lại!";
			return false;
		}

		// kiem tra ngay khoa so
		/*
		 * if(checkNgayKhoaSo(this.ngaygiaodich) == false) { this.msg =
		 * "Bạn chỉ có thể chốt đơn hàng sau ngày khóa sổ gần nhất một ngày.";
		 * return false; }
		 */
		String query = "\n select sanpham_fk, soluong, b.npp_fk, b.kbh_fk, b.kho_fk " +
		"\n from donhang_sanpham a inner join donhang b " +
		"\n on a.donhang_fk = b.pk_seq where b.pk_seq = '" + this.id + "' ";
		ResultSet rssp = db.get(query);
		if (ngaygiaodich.equals(getDateTime())) {
			if (rssp != null) {
				try {
					while (rssp.next()) {
						double m = 0;
						query = "select * from [dbo].[uf_CacNgayTrongKhoangThoiGian]('" + ngaygiaodich + "', '"+ getDateTime()+ "') ";
						ResultSet rsngay = db.get(query);

						if (rsngay != null) {
							while (rsngay.next()) {
								query = "\n select cuoiky,sanpham_fk " +
								"\n from [dbo].[ufn_XNT_TuNgay_DenNgay_Trongky]('" + nppId + "', '" + ngaygiaodich + "', '" + rsngay.getString("ngay") + "') " +
								"\n where npp_fk = '" + rssp.getString("npp_fk") + "' and kho_fk = '" + rssp.getString("kho_fk") + "' " +
								"\n 	and sanpham_fk = '" + rssp.getString("sanpham_fk") + "' and kbh_fk = '" + rssp.getString("kbh_fk") + "' ";
								ResultSet rschecktk = db.get(query);
								if (rschecktk.next()) {
									m = rschecktk.getDouble("cuoiky");
								}

								if (m < 0) {
									msg = "Lỗi cập nhật đơn hàng, không đủ Xuất nhập tồn trong kỳ từ ngày đơn hàng đến ngày hiện tại!";
									this.db.getConnection().rollback();
									return false;
								}
							}
						}
					}
				} 
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		query = "\n select c.pk_seq as sanpham_fk, soluong, b.npp_fk, b.kbh_fk, b.kho_fk " +
		"\n from donhang_ctkm_trakm a inner join donhang b " +
		"\n on a.donhang_fk = b.pk_seq " +
		"\n inner join sanpham c on c.ma = a.sanphamid " +
		"\n where b.pk_seq = '" + this.id + "' ";
		rssp = db.get(query);
		if (rssp != null) {
			try {
				while (rssp.next()) {
					double m = 0;
					query = "select * from [dbo].[uf_CacNgayTrongKhoangThoiGian]('" + ngaygiaodich + "', '" + getDateTime() + "') ";
					ResultSet rsngay = db.get(query);

					if (rsngay != null) {
						while (rsngay.next()) {
							query = "\n select cuoiky,sanpham_fk " +
							"\n from [dbo].[ufn_XNT_TuNgay_DenNgay_Trongky]('" + nppId + "', '" + ngaygiaodich + "', '" + rsngay.getString("ngay") + "') " +
							"\n where npp_fk = '" + rssp.getString("npp_fk") + "' and kho_fk = '" + rssp.getString("kho_fk") + "' " +
							"\n		and sanpham_fk = '" + rssp.getString("sanpham_fk") + "' and kbh_fk = '" + rssp.getString("kbh_fk") + "' ";
							ResultSet rschecktk = db.get(query);
							if (rschecktk.next()) {
								m = rschecktk.getDouble("cuoiky");
							}

							if (m < 0) {
								msg = "Lỗi cập nhật đơn hàng KM, không đủ Xuất nhập tồn trong kỳ từ ngày đơn hàng đến ngày hiện tại!";
								this.db.getConnection().rollback();
								return false;
							}
						}
					}
				}
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}

		try {
			db.update("SET TRANSACTION ISOLATION LEVEL SNAPSHOT;");
			db.update("SET LOCK_TIMEOUT 60000;");
			db.getConnection().setAutoCommit(false);

			query = "\n update donhang set trangthai = '1', ngaychot = ' " + this.getDateTime() + "', FlagModified = 1, ThoiDiemChot = getdate() " +
			"\n where pk_seq= '" + this.id + "' and TrangThai = 3 ";
			if (db.updateReturnInt(query) != 1) {
				db.getConnection().rollback();
				this.msg = "Đơn hàng đã chốt hoặc đang được thao tác!";
				return false;
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);

		} 
		catch (Exception e1) {
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.msg = "Lỗi ngoại lệ, vui lòng liên hệ trung tâm để kiểm tra lại!";
			return false;
		}

		return true;
	}

	private boolean checkNgayKhoaSo(String ngaygd) {
		geso.dms.distributor.util.Utility util = new geso.dms.distributor.util.Utility();
		String ngayksgn = util.ngaykhoaso(this.nppId);

		if (ngayksgn.equals(""))
			ngayksgn = this.getDateTime();

		String[] ngayks = ngayksgn.split("-");
		String[] ngaydh = ngaygd.split("-");

		Calendar c1 = Calendar.getInstance(); // new GregorianCalendar();
		Calendar c2 = Calendar.getInstance(); // new GregorianCalendar();

		c1.set(Integer.parseInt(ngayks[0]), Integer.parseInt(ngayks[1]) - 1, Integer.parseInt(ngayks[2]));
		c2.set(Integer.parseInt(ngaydh[0]), Integer.parseInt(ngaydh[1]) - 1, Integer.parseInt(ngaydh[2]));

		long songay = (c2.getTime().getTime() - c1.getTime().getTime()) / (24 * 3600 * 1000);

		System.out.print("So ngay la: " + songay);

		if (songay == 1)
			return true;

		return false;
	}

	public void createPxkId() {
		String query = "select distinct a.pxk_fk, b.ngaylapphieu from phieuxuatkho_donhang a inner join phieuxuatkho b on a.pxk_fk = b.pk_seq where a.donhang_fk = '"
			+ this.id + "'";
		ResultSet rs = db.get(query);
		System.out.println("Query vao lay Phieu Xuat Kho La: " + query + "\n");
		try {
			rs.next();
			this.pxkId = rs.getString("pxk_fk");
			this.ngayxuatkho = rs.getString("ngaylapphieu");
			rs.close();
		} catch (Exception e) {
		}
	}

	public void createRS() {
		this.getNppInfo();
		this.createGhichuO();
		this.createDdkd();
		this.createChietkhau();
		this.createBgstId();
		this.createKho();
		this.checkInfo();
		this.Muctindung();
		String query = "select * from nhanviengiaonhan where NPP_FK = "
			+ this.nppId;
		this.nvgnRs = db.get(query);
	}

	private void checkInfo() {
		if (this.ngaygiaodich.length() > 0 && this.khId.length() > 0) {
			String query = "select count(*) as sodong from donhang where khachhang_fk = '"
				+ this.khId
				+ "' and ngaynhap = '"
				+ this.ngaygiaodich
				+ "'";
			ResultSet rs = db.get(query);
			try {
				if (rs.next()) {
					if (rs.getInt("sodong") > 0)
						this.dacoDh = true; // khach hang da mua hang trong ngay
					rs.close();
				}
				if (this.id.length() > 0 && this.trangthai.equals("0")) // da
					// xuat
					// kho
					// nhung
					// chua
					// chot
					// pxk
				{
					query = "select count(*) as sodong from phieuxuatkho_donhang where donhang_fk = '"
						+ this.id + "'";
					System.out.println("Cau lenh check phieuxuatkho: " + query);
					ResultSet xuatkho = db.get(query);
					if (xuatkho.next()) {
						if (xuatkho.getInt("sodong") > 0)
							this.daxuatkhoChuachot = true;
						xuatkho.close();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void createGhichuO() {

		String sql = " select * from ghichu where trangthai = '1' ";
		this.ghichuORs = db.get(sql);
	}

	private void createDdkd() {
		// tao gsbh
		String sql = "select a.pk_seq,a.ten from giamsatbanhang a inner join NHAPP_GIAMSATBH b on a.pk_seq = b.gsbh_fk where   b.ngaybatdau <='"
			+ this.getDateTime()
			+ "' and b.ngayketthuc >='"
			+ getDateTime()
			+ "' and   a.trangthai = '1' and npp_fk ='"
			+ this.nppId + "'";
		this.gsbhs = db.get(sql);

		String query = "\n select ten as ddkdTen, pk_seq as ddkdId "
			+ "\n from daidienkinhdoanh where trangthai = '1' and npp_fk = "
			+ this.nppId
			+ "\n and pk_seq in (select ddkd_fk from ddkd_gsbh where gsbh_fk in (select gsbh_fk "
			+ "\n 	from nhapp_giamsatbh " + "\n		where ngaybatdau <='"
			+ this.getDateTime() + "' " + "\n 	and ngayketthuc >='"
			+ getDateTime() + "' " + "\n 	and gsbh_fk ='" + this.gsbhId
			+ "')" + "\n )";
		System.out.println("Get DDKDrs: " + query);
		this.ddkdlist = db.get(query);
	}

	private void createBgstId() {
		ResultSet rs = db.get("select banggiasieuthi_fk from bgst_khachhang where khachhang_fk = '"+ this.khId + "'");
		String st = "";
		if (rs != null) {
			try {
				while (rs.next()) {
					st = st + rs.getString("banggiasieuthi_fk") + "-";
				}
				// if(rs.getString("bgst_fk") != null)
				if (st.length() > 0)
					this.bgstId = st;// rs.getString("bgst_fk");
				else
					this.bgstId = "0";
				rs.close();
			} catch (Exception e) {

				System.out.println("loi..........." + e.toString());
			}
		} else
			this.bgstId = "0";
	}

	private void createChietkhau() {
		if (this.chietkhau.equals("0") || this.chietkhau == "") {
			String sql = "select ISNULL(b.chietkhau,0 ) as chietkhau from KHACHHANG a left join MUCCHIETKHAU b on a.CHIETKHAU_FK = b.pk_seq " +
			"\n where a.PK_SEQ = '"+ khId + "'";
			System.out.println("% ck kh " + sql);
			ResultSet rs = db.get(sql);
			if (rs != null) {
				try {
					rs.next();
					if (rs.getString("chietkhau") != null)
						if (this.chietkhau.length() == 0)
							this.chietkhau = rs.getString("chietkhau");
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void createKho() {
		this.kholist = db
		.get("select distinct PK_SEQ as khoId, Ten, Diengiai from KHO where PK_SEQ in (select kho_fk from NHAPP_KHO where npp_fk = '"
				+ this.nppId + "') order by PK_SEQ ASC");
	}

	public void CreateSpList() {
		try {
			DecimalFormat df2 = new DecimalFormat("#,###,###,##");
		
		String giamua = " a.giamua ";
		String gia_goc = " isnull(a.DonGiaGoc,0) ";
		if(this.trangthai.equals("0") && !this.donhangkhac.equals("1"))
		{
			giamua = " [dbo].[GiaCkBanLeNppSanPham](dh.npp_fk,dh.khachhang_fk,a.sanpham_fk,dh.ngaynhap ) + isnull(a.tiengiamtru,0)  ";
			
		}
		if(this.trangthai.equals("0"))
		{
			gia_goc = "[dbo].[GiaCkBanLeNppSanPham](dh.npp_fk,dh.khachhang_fk,a.sanpham_fk,dh.ngaynhap )  ";
		}

			String command = "\n select DISTINCT a.tiengiamtru,b.pk_seq as spId, b.ma as spMa, b.ten as spTen, a.soluong,"
				+ "\n isnull(c.donvi, 'Chua xac dinh') as donvi, "+giamua+" as dongia, a.chietkhau, "
				+ "\n a.ptCKDLN, a.ptCKTT, a.ptCKDH , d.AVAILABLE as hienhuu,"+gia_goc+" as GiaGoc,"
				+ "\n isnull(a.BangGia_FK,0) as  bgID  "
				+ "\n from donhang_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq" +
				  "\n inner join donhang dh on dh.pk_seq = a.donhang_fk  "
				+ "\n left join donvidoluong c on b.dvdl_fk = c.pk_seq "
				+ "\n inner join NHAPP_KHO d on b.PK_SEQ = d.SANPHAM_FK "
				+ "\n where a.donhang_fk = '"
				+ this.id
				+ "' and d.KHO_FK = "
				+ this.khoId
				+ "\n and d.NPP_FK = '"
				+ this.nppId
				+ "' and d.KBH_FK = '" + this.kbhId + "'";

			System.out.println("2. San pham List:" + command);

			ResultSet splistRs = db.get(command);
			float tonggiatri = 0f;
			List<ISanpham> splist = new ArrayList<ISanpham>();



			while (splistRs.next()) {
				String[] param = new String[12];
				param[0] = splistRs.getString("spId");
				param[1] = splistRs.getString("spma");
				param[2] = splistRs.getString("spten");
				param[3] = splistRs.getString("soluong");
				param[4] = splistRs.getString("donvi");
				param[5] = splistRs.getString("dongia");
				param[6] = "";
				param[8] = splistRs.getString("ptCKDLN") == null ? "0"
						: splistRs.getString("ptCKDLN");
				param[9] = splistRs.getString("ptCKTT") == null ? "0"
						: splistRs.getString("ptCKTT");
				param[10] = splistRs.getString("ptCKDH") == null ? "0"
						: splistRs.getString("ptCKDH");

				param[7] = "0";

				tonggiatri += Float.parseFloat(param[5])* Float.parseFloat(param[3]);

				ISanpham sp = new Sanpham(param);
				sp.setTonhientai(splistRs.getString("hienhuu"));
				sp.setGiagoc(splistRs.getString("GiaGoc"));
				sp.setBgId(splistRs.getString("bgId"));
				sp.setTiengiamtru( splistRs.getString("tiengiamtru") == null ? "0": splistRs.getString("tiengiamtru")  );
				splist.add(sp);

			}
			// theem hang mau
			command = "\n select DISTINCT b.pk_seq as spId, b.ma as spMa, b.ten as spTen, a.soluong,"
				+ "\n isnull(c.donvi, 'Chua xac dinh') as donvi, 0 as dongia, 0  chietkhau, "
				+ "\n 0 ptCKDLN,  0  ptCKTT,  0 ptCKDH , d.AVAILABLE as hienhuu,0 GiaGoc,"
				+ "\n 0  bgID  "
				+ "\n from donhang_ctkm_trakm a inner join sanpham b on a.sanpham_fk = b.pk_seq "
				+ "\n left join donvidoluong c on b.dvdl_fk = c.pk_seq "
				+ "\n inner join NHAPP_KHO d on b.PK_SEQ = d.SANPHAM_FK "
				+ "\n where a.ap_dung = 1 and a.donhangId = '"+ this.id+ "' and d.KHO_FK = "	+ this.khoId+ "\n and d.NPP_FK = '"+ this.nppId
				+ "' and d.KBH_FK = '" + this.kbhId + "'";
			splistRs = db.get(command);
			while (splistRs.next()) {
				String[] param = new String[12];
				param[0] = splistRs.getString("spId");
				param[1] = splistRs.getString("spma");
				param[2] = splistRs.getString("spten");
				param[3] = splistRs.getString("soluong");
				param[4] = splistRs.getString("donvi");
				param[5] = splistRs.getString("dongia");
				param[6] = "";
				param[8] = splistRs.getString("ptCKDLN") == null ? "0"
						: splistRs.getString("ptCKDLN");
				param[9] = splistRs.getString("ptCKTT") == null ? "0"
						: splistRs.getString("ptCKTT");
				param[10] = splistRs.getString("ptCKDH") == null ? "0"
						: splistRs.getString("ptCKDH");

				param[7] = "0";


				ISanpham sp = new Sanpham(param);
				sp.setHangmau("1");
				sp.setTonhientai(splistRs.getString("hienhuu"));
				sp.setGiagoc(splistRs.getString("GiaGoc"));
				sp.setBgId(splistRs.getString("bgId"));

				splist.add(sp);

			}

			splistRs.close();
			this.splist = splist;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("115.Exception khoi tao: " + e.getMessage());
		}
	}

	public void CreateSpDisplay() {
		DecimalFormat df2 = new DecimalFormat("#,###,###,##");
		String command = "";
		command = "select DISTINCT b.pk_seq as spId, b.ma as spMa, b.ten as spTen, a.soluong, isnull(c.donvi, 'Chua xac dinh') as donvi, a.giamua as dongia, isnull(a.chietkhau, '0') as chietkhau,isnull(a.DonGiaGoc, '0')  as GiaGoc,isnull(a.BangGia_FK, '0')  as bgId ";
		command = command
		+ "from donhang_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq left join donvidoluong c on b.dvdl_fk = c.pk_seq";
		command = command + " where a.donhang_fk = '" + this.id + "'";
		// System.out.println("San pham list:" + command);
		ResultSet splistRs = db.get(command);
		List<ISanpham> splist = new ArrayList<ISanpham>();
		if (splistRs != null) {
			String[] param = new String[12];
			ISanpham sp = null;
			try {
				while (splistRs.next()) {
					param[0] = splistRs.getString("spId");
					param[1] = splistRs.getString("spma");
					param[2] = splistRs.getString("spten");
					param[3] = splistRs.getString("soluong");
					param[4] = splistRs.getString("donvi");
					param[5] = splistRs.getString("dongia");
					param[6] = "";

					float ck = splistRs.getFloat("chietkhau");
					int slg = Integer.parseInt(param[3]);
					float tt = 0f;
					if (slg != 0) {
						tt = (ck / (Integer.parseInt(param[3]) * Float
								.parseFloat(param[5]))) * 100;
						tt = new Float(df2.format(tt)).floatValue(); // lam tron
						// 2 so
					}
					this.chietkhau = Float.toString(tt);

					param[7] = this.chietkhau;

					float tonggiatri = Float.parseFloat(param[5])
					* Float.parseFloat(param[3]);

					sp = new Sanpham(param);
					sp.setTonhientai(splistRs.getString("hienhuu"));
					sp.setGiagoc(splistRs.getString("GiaGoc"));
					sp.setBgId(splistRs.getString("bgId"));

					splist.add(sp);
				}
				if (splistRs != null) {
					splistRs.close();
				}
			} catch (Exception e) {
			}
		}
		this.splist = splist;
	}

	String kbhId = "";

	public void init() {
		NumberFormat formatter = new DecimalFormat("#,###,###");
		this.getNppInfo();

		String query = "\n select a.soluongSpComBo,a.giaSpComBo,a.tenSpComBo,isnull(a.donhangkhac,0)donhangkhac,isnull(a.isPDA,0)isPDA,isnull(g.coderoute,'')coderoute,isnull(a.ghichu, '') as ghichu,"
			+ "\n a.gsbh_fk,a.pk_seq as dhId, a.ngaynhap, a.trangthai, a.ngaytao, a.ngaysua, a.khachhang_fk as khId, "
			+ "\n g.ten as khTen, g.diachi, isnull(g.smartid, '') as smartid,isnull(a.ngaygiaohang,'')ngaygiaohang,"
			+ "\n a.kho_fk as khoId, a.tonggiatri, b.ten as nguoitao, c.ten as nguoisua, e.pk_seq as ddkdId, e.ten as ddkdTen,"
			+ "\n f.ten as nppTen, a.VAT, isnull(a.chietkhau, 0) as chietkhau, isnull(a.chuyen, 0) as ptck, a.kbh_fk,"
			+ "\n a.IsChiNhanh, isnull(a.NoCu,0) Nocu,a.nvgn_fk,sotiengiam, a.ghichu_fk "
			+ "\n from donhang a "
			+ "\n left join nhanvien b on a.nguoitao = b.pk_seq "
			+ "\n left join nhanvien c on a.nguoisua = c.pk_seq "
			+ "\n inner join daidienkinhdoanh e on a.ddkd_fk = e.pk_seq "
			+ "\n inner join nhaphanphoi f on a.npp_fk = f.pk_seq"
			+ "\n inner join khachhang g on a.khachhang_fk=g.pk_seq "
			+ "\n where a.npp_fk='"
			+ this.nppId
			+ "' and a.pk_seq='"
			+ this.id + "'  ";

		System.out.println("1.Init don hang:" + query);
		ResultSet rs = db.get(query);
		if (rs != null) {
			try {
				rs.next();
				this.donhangkhac =  rs.getString("donhangkhac");
				isPDA = rs.getString("isPDA");
				coderoute = rs.getString("coderoute");
				this.id = rs.getString("dhId");
				this.khId = rs.getString("khId");
				this.khTen = rs.getString("khTen") + " - "
				+ rs.getString("diachi");
				this.smartId = rs.getString("smartid");
				this.ngaygiaodich = rs.getString("ngaynhap");
				this.daidienkd = rs.getString("ddkdTen");
				this.trangthai = rs.getString("trangthai");
				this.ngaytao = rs.getString("ngaytao");
				this.nguoitao = rs.getString("nguoitao");
				this.ngaysua = rs.getString("ngaysua");
				this.nguoisua = rs.getString("nguoisua");
				this.ngaygh = rs.getString("ngaygiaohang");
				this.VAT = "0"; // TTC, gia SP da co thue
				this.ddkdId = rs.getString("ddkdId");
				this.khoId = rs.getString("khoId");
				System.out.println("11.Kho id: " + this.khoId);
				this.chietkhau = rs.getString("chietkhau");
				this.tongchietkhau = rs.getString("chietkhau");
				this.tongtiensauVAT = rs.getString("tonggiatri");
				this.tongtientruocVAT = rs.getString("VAT");
				this.gsbhId = rs.getString("gsbh_fk");
				this.kbhId = rs.getString("kbh_fk");
				this.nvgnId = rs.getString("nvgn_fk");
				this.IsChiNhanh = rs.getString("IsChiNhanh") == null ? "0" : rs
						.getString("isChiNhanh");
				this.ghichu = rs.getString("ghichu");
				this.ghichuoption = rs.getString("ghichu_fk");
				this.nocu = rs.getDouble("nocu");
				this.sotiengiam = rs.getString("sotiengiam");
				
				this.tenSpComBo = rs.getString("tenSpComBo") == null ? "" : rs.getString("tenSpComBo");
				this.soluongSpComBo = rs.getString("soluongSpComBo") == null ? "" : formatter.format(rs.getDouble("soluongSpComBo"));
				this.giaSpComBo = rs.getString("giaSpComBo") == null ? "" : formatter.format(rs.getDouble("giaSpComBo"));

				rs.close();
			} catch (Exception e) {
				System.out.println("33.Exception: " + e.getMessage());
			}
		}
		String sql = "select a.pk_seq, a.ten from giamsatbanhang a inner join NHAPP_GIAMSATBH b on a.pk_seq = b.gsbh_fk where  b.ngaybatdau <='"
			+ this.getDateTime()
			+ "' and b.ngayketthuc >='"
			+ getDateTime()
			+ "' and npp_fk ='"
			+ this.nppId
			+ "' and a.trangthai = '1'";
		sql += " union select a.pk_seq, a.ten from giamsatbanhang a  where a.pk_Seq='"
			+ this.gsbhId + "'  ";
		System.out.println("cau query ............." + sql);
		this.gsbhs = db.get(sql);
		if (this.gsbhId == null) {
			this.ddkdlist = db
			.get("select ten as ddkdTen, pk_seq as ddkdId from daidienkinhdoanh where trangthai = '1' and npp_fk = '"
					+ this.nppId + "'");
		} else {
			sql = "select ten as ddkdTen, pk_seq as ddkdId from daidienkinhdoanh where trangthai = '1' and npp_fk = '"
				+ this.nppId
				+ "' and pk_seq in (select ddkd_fk from ddkd_gsbh where gsbh_fk ='"
				+ this.gsbhId + "')";
			sql += " union select ten as ddkdTen, pk_seq as ddkdId from daidienkinhdoanh where pk_Seq='"
				+ this.ddkdId + "'";
			this.ddkdlist = db.get(sql);
			System.out.println("___Ddkd___" + sql);
		}

		this.createChietkhau();
		this.createBgstId();
		this.CreateSpList();
		this.createKho();
		this.checkInfo();
		this.getTrakhuyenmai();
		this.getTraTrungbay();
		this.getTraTichluy();
		this.createGhichuO();
		query = "select * from nhanviengiaonhan where NPP_FK = " + this.nppId;
		this.nvgnRs = db.get(query);
		System.out.println("nvgn:" + query);
		
		//INIT TICH LUY 
		query = "select distinct DUYETKM_FK from DONHANG_CTKM_TRAKM where DUYETKM_FK is not null and donhangId = '" + this.id + "'";
		rs = db.get(query);
		if(rs != null)
		{
			tichluyIdList.clear();

			try 
			{
				while(rs.next())
				{
					tichluyIdList.add( rs.getString("DUYETKM_FK")  ) ;

				}
				rs.close();
			} 
			catch (Exception e) { e.printStackTrace(); }
		}
	}

	public String getBgstId() {
		return this.bgstId;
	}

	public void setBgstId(String bgstId) {
		this.bgstId = bgstId;
	}

	public String getKhoId() {
		return this.khoId;
	}

	public void setKhoId(String khoId) {
		this.khoId = khoId;
	}

	public ResultSet getKhoList() {
		return this.kholist;
	}

	public void setKhoList(ResultSet kholist) {
		this.kholist = kholist;
	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public void DBclose() {
		try {
			if (!(this.kholist == null))
				this.kholist = null;

			if (!(this.ddkdlist == null))
				this.ddkdlist.close();
			if (!(this.tbhlist == null))
				this.tbhlist.close();
			if (this.khlist != null) {
				this.khlist.close();
			}
			if (this.gsbhs != null) {
				gsbhs.close();
			}
			splist = null;
			spThieuList = null;
			scheme_tongtien = null;
			scheme_chietkhau = null;
			scheme_sanpham = null;

			this.db.shutDown();
		} catch (Exception e) {
		}
	}

	public void setMuctindung(String muctindung) {

		this.muctindung = muctindung;
	}

	public String getMuctindung() {

		return this.muctindung;
	}

	public ResultSet getgsbhs() {
		return this.gsbhs;
	}

	public String getTongChietKhau() {
		return this.tongchietkhau;
	}

	public void setTongChietKhau(String tck) {
		this.tongchietkhau = tck;
	}

	public String getKHList() {
		String khId = "";
		String khTen = "";
		String khChietKhau = "";
		String khList = "";
		String command = "select b.pk_seq as khId, b.ten as khTen, c.chietkhau "
			+ "from khachhang_tuyenbh a inner join khachhang b on a.khachhang_fk = b.pk_seq "
			+ "inner join tuyenbanhang d on a.tbh_fk = d.pk_seq "
			+ "left join mucchietkhau c on b.chietkhau_fk = c.pk_seq "
			+ "where b.npp_fk='"
			+ this.nppId
			+ "' and d.ddkd_fk= '"
			+ this.ddkdId + "' order by khId, khTen, chietkhau";

		ResultSet kh = db.get(command);
		try {
			if (kh != null) {
				while (kh.next()) {
					khId = khId + kh.getString("khId") + ",";
					khTen = khTen + kh.getString("khTen") + ",";

					if (kh.getString("chietkhau") != null && !kh.wasNull())
						khChietKhau = khChietKhau + kh.getString("chietkhau")
						+ ",";
					else
						khChietKhau = khChietKhau + "0,";

				}

			}
			khId = khId.substring(0, khId.length() - 1);
			khTen = khTen.substring(0, khTen.length() - 1);
			khChietKhau = khChietKhau.substring(0, khChietKhau.length() - 1);

			String[] khIdList = khId.split(",");
			String[] khTenList = khTen.split(",");
			String[] khChietKhauList = khChietKhau.split(",");

			int cnt = 1;
			for (int i = 0; i < khTenList.length; i++) {
				if (i != khTenList.length - 1) {
					khList = khList + "\"" + khIdList[i] + "-->["
					+ khTenList[i] + "][" + khChietKhauList[i] + "]\",";
				} else {
					khList = khList + "\"" + khIdList[i] + "-->["
					+ khTenList[i] + "][" + khChietKhauList[i] + "]\"";
				}

			}
			if (kh != null) {
				kh.close();
			}
		} catch (Exception e) {
		}
		return khList;
	}

	public boolean isAplaikhuyenmai() {
		return this.aplaikm;
	}

	public void setAplaikhuyenmai(boolean aplaikm) {
		this.aplaikm = aplaikm;
	}

	public String createPth(String pxkId, dbutils db) {
		String msg = "";
		try {
			// db.getConnection().setAutoCommit(false);

			List<ISanpham> spThuhoiList = this.getSpthuhoiList(pxkId, db);
			List<ISanpham> spkmThuhoiList = this.getSpkmthuhoiList(pxkId, db);
			System.out.println("[spThuhoiList]" + spThuhoiList.size()
					+ "[spkmThuhoiList]" + spkmThuhoiList.size());
			if (spThuhoiList.size() > 0 || spkmThuhoiList.size() > 0) {
				// Xoa cac phieu thu hoi cu cua pxk nay co trang thai = 0
				String query = "delete from phieuthuhoi_sanpham where pth_fk in (select pk_seq from phieuthuhoi where phieuxuatkho_fk = '"
					+ pxkId + "' and trangthai = 0)";
				if (!db.update(query)) {
					// db.getConnection().rollback();
					msg = "1. Lỗi tạo phiếu thu hồi.";
					return msg;
				}

				query = "delete from phieuthuhoi_spkm where pth_fk in (select pk_seq from phieuthuhoi where phieuxuatkho_fk = '"
					+ pxkId + "' and trangthai = 0)";
				if (!db.update(query)) {
					// db.getConnection().rollback();
					msg = "2. Lỗi tạo phiếu thu hồi.";
					return msg;
				}

				query = "select DONHANG_FK as dhId from PHIEUTHUHOI_DONHANG where pth_fk in (select pk_seq from phieuthuhoi where phieuxuatkho_fk = '"
					+ pxkId
					+ "' and trangthai = 0) and donhang_fk != '"
					+ this.id + "'";
				ResultSet rsDh = db.get(query);
				String dhs = "";
				if (rsDh != null) {
					while (rsDh.next()) {
						if (rsDh.getString("dhId") != null)
							dhs += rsDh.getString("dhId") + ",";
					}
					rsDh.close();
				}

				query = "delete from phieuthuhoi_donhang where pth_fk in (select pk_seq from phieuthuhoi where phieuxuatkho_fk = '"
					+ pxkId + "' and trangthai = 0)";
				if (!db.update(query)) {
					// db.getConnection().rollback();
					msg = "3. Lỗi tạo phiếu thu hồi.";
					return msg;
				}

				query = "delete from phieuthuhoi where phieuxuatkho_fk = '"
					+ pxkId + "' and trangthai = 0";
				if (!db.update(query)) {
					// db.getConnection().rollback();
					msg = "4. Lỗi tạo phiếu thu hồi.";
					return msg;
				}

				query = "insert into phieuthuhoi(phieuxuatkho_fk, trangthai, ngaytao, ngaysua, nguoitao, nguoisua, donhang_fk, npp_fk) ";
				query = query + "values('" + pxkId + "','0','"
				+ this.ngayxuatkho + "','" + this.getDateTime() + "','"
				+ this.userId + "','" + this.userId + "', '" + this.id
				+ "', '" + this.nppId + "')";
				if (!db.update(query)) {
					// db.getConnection().rollback();
					msg = "5. Lỗi tạo phiếu thu hồi.";
					return msg;
				}

				query = "select IDENT_CURRENT('phieuthuhoi') as pthId";
				String pthId = "";
				ResultSet rsPth = db.get(query);
				rsPth.next();
				pthId = rsPth.getString("pthId");
				rsPth.close();

				// luu vao bang phieuthuhoi_sp
				for (int i = 0; i < spThuhoiList.size(); i++) {
					Sanpham sp = (Sanpham) spThuhoiList.get(i);

					if (Integer.parseInt(sp.getSoluong()) != 0) {
						// DOn vi tinh luu kho_fk, don gia se luu kbh_fk
						query = "Insert into phieuthuhoi_sanpham(pth_fk, sanpham_fk, soluong, kho_fk, kbh_fk) values("
							+ pthId
							+ ", '"
							+ sp.getId()
							+ "','"
							+ sp.getSoluong()
							+ "', '"
							+ sp.getDonvitinh()
							+ "', '" + sp.getDongia() + "')";

						if (!db.update(query)) {
							// db.getConnection().rollback();
							msg = "6. Lỗi tạo phiếu thu hồi - SP.";
							return msg;
						}
					}
				}

				// luu vao bang phieuthuhoi_spkm (chi tao khi in phieu thu hoi
				// cuoi cung)
				for (int i = 0; i < spkmThuhoiList.size(); i++) {
					Sanpham sp = (Sanpham) spkmThuhoiList.get(i);

					if (Integer.parseInt(sp.getSoluong()) != 0) {
						// DOn vi tinh luu kho_fk, don gia se luu kbh_fk
						query = "Insert into phieuthuhoi_spkm(pth_fk, sanpham_fk, soluong, kho_fk, kbh_fk) values('"
							+ pthId
							+ "', '"
							+ sp.getId()
							+ "','"
							+ sp.getSoluong()
							+ "', '"
							+ sp.getDonvitinh()
							+ "', '" + sp.getDongia() + "')";
						if (!db.update(query)) {
							// db.getConnection().rollback();
							msg = "6. Lỗi tạo phiếu thu hồi - SPKM.";
							return msg;
						}
					}
				}

				query = "insert PHIEUTHUHOI_DONHANG(pth_fk, pxk_fk, donhang_fk) values('"
					+ pthId + "', '" + this.pxkId + "', '" + this.id + "')";
				if (!db.update(query)) {
					// db.getConnection().rollback();
					msg = "7. Lỗi tạo phiếu thu hồi - Đơn hàng.";
					return msg;
				}

				if (dhs.length() > 0) {
					String[] donhangs = dhs.split(",");
					for (int i = 0; i < donhangs.length; i++) {
						query = "insert PHIEUTHUHOI_DONHANG(pth_fk, pxk_fk, donhang_fk) values('"
							+ pthId
							+ "', '"
							+ this.pxkId
							+ "', '"
							+ donhangs[i].trim() + "')";
						if (!db.update(query)) {
							// db.getConnection().rollback();
							msg = "8. Lỗi tạo phiếu thu hồi - Đơn hàng.";
							return msg;
						}
					}
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			/*
			 * try { db.getConnection().rollback(); } catch (Exception e) {}
			 */

			msg = "Lỗi ngoại lệ tạo phiếu thu hồi!";
			return msg;
		}
		return msg;
	}

	private List<ISanpham> getSpthuhoiList(String pxkId, dbutils db) {
		List<ISanpham> spOldList = new ArrayList<ISanpham>();
		String query = "select sanpham_fk, soluong, kbh_fk, kho_fk from phieuxuatkho_sanpham where pxk_fk = '"
			+ pxkId + "'";

		System.out.println("Cau lenh lay du lieu la: " + query + "\n");
		ResultSet spThuhoi = db.get(query);
		/* if(spThuhoi!= null) */
		{
			try {
				while (spThuhoi.next()) {
					// ISanpham sp = new Sanpham(spThuhoi.getString("spId"),
					// spThuhoi.getString("spMa"), spThuhoi.getString("spTen"),
					// spThuhoi.getString("soluong"),
					// spThuhoi.getString("khoId"), spThuhoi.getString("kbhId"),
					// "", "");
					ISanpham sp = new Sanpham(spThuhoi.getString("sanpham_fk"),
							"", "", spThuhoi.getString("soluong"),
							spThuhoi.getString("kho_fk"),
							spThuhoi.getString("kbh_fk"), "", "");
					spOldList.add(sp);
				}
				spThuhoi.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// System.out.println("Size san pham thu hoi buoc 1 la: " +
		// spOldList.size() + "\n");

		// Nhung phieu thu hoi cua phieu xuat kho nay
		query = "select sanpham_fk as spId, soluong, kho_fk as khoId, kbh_fk as kbhId from phieuthuhoi_sanpham where pth_fk in (select pk_seq from phieuthuhoi where phieuxuatkho_fk = '"
			+ pxkId + "' and trangthai = '1')";
		query += " union all ";
		query += "select c.sanpham_fk as spId, soluong, b.kho_fk as khoId, b.kbh_fk as kbhId from phieuxuatkho_donhang a inner join donhang b on a.donhang_fk = b.pk_seq inner join donhang_sanpham c on b.pk_seq = c.donhang_fk where b.trangthai != '2' and a.pxk_fk = '"
			+ pxkId + "'";

		query = "select spId, sum(soluong) as soluong, khoId, kbhId from ("
			+ query + ") kh group by spId, khoId, kbhId";

		// System.out.println("Query lay san pham aaaaa la: " + query + "\n");

		List<ISanpham> spNewList = new ArrayList<ISanpham>();
		ResultSet spThuhoi2 = db.get(query);
		/* if(spThuhoi2 != null) */
		{
			try {
				while (spThuhoi2.next()) {
					ISanpham sp = new Sanpham(spThuhoi2.getString("spId"), "",
							"", spThuhoi2.getString("soluong"),
							spThuhoi2.getString("khoId"),
							spThuhoi2.getString("kbhId"), "", "");

					spNewList.add(sp);
				}
				spThuhoi2.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// List<ISanpham> spNewList = new ArrayList<ISanpham>();
		// sanpham hien tai
		/*
		 * query =
		 * "select b.kho_fk as khoId, b.kbh_fk as kbhId, c.sanpham_fk as spId, sum(c.soluong) as soluong from phieuxuatkho_donhang a inner join donhang b on a.donhang_fk = b.pk_seq inner join donhang_sanpham c on b.pk_seq = c.donhang_fk "
		 * ; query += "where b.trangthai != '2' and a.pxk_fk = '" + pxkId +
		 * "' group by b.kho_fk, b.kbh_fk, c.sanpham_fk";
		 * System.out.println("Cau lenh lay du lieu ben duoi: " + query + "\n");
		 * ResultSet spThuhoi3 = db.get(query); if(spThuhoi3 != null) { try {
		 * while(spThuhoi3.next()) { ISanpham sp = new
		 * Sanpham(spThuhoi3.getString("spId"), "", "",
		 * spThuhoi3.getString("soluong"), spThuhoi3.getString("khoId"),
		 * spThuhoi3.getString("kbhId"), "", "");
		 * 
		 * spNewList.add(sp); } spThuhoi3.close(); } catch(Exception e) {} }
		 */
		// cong don san pham
		// thu hoi sp trakhyenmai (ve kho_fk, kbh_fk)
		List<ISanpham> spkmList = new ArrayList<ISanpham>();

		for (int i = 0; i < spOldList.size(); i++) {
			Sanpham spA = (Sanpham) spOldList.get(i);
			for (int j = 0; j < spNewList.size(); j++) {
				Sanpham spB = (Sanpham) spNewList.get(j);
				if ((spB.getId().trim().equals(spA.getId().trim()))
						&& (spB.getDonvitinh().trim().equals(spA.getDonvitinh()
								.trim()))
								&& (spB.getDongia().trim().equals(spA.getDongia()
										.trim()))) {
					int slg = Math.abs(Integer.parseInt(spA.getSoluong())
							- Integer.parseInt(spB.getSoluong()));

					spOldList.get(i).setSoluong(Integer.toString(slg));

					spNewList.remove(j);
					j = 0;
				}
			}
			if (Integer.parseInt(spOldList.get(i).getSoluong()) > 0) {
				ISanpham sp = new Sanpham(spOldList.get(i).getId(), "", "",
						spOldList.get(i).getSoluong(), spOldList.get(i)
						.getDonvitinh(), spOldList.get(i).getDongia(),
						"", "");
				spkmList.add(sp);

				// System.out.println("So luong luc dau thu hoi co so luong la: "
				// + sp.getSoluong() + "\n");
			}
		}

		// System.out.println("Size san pham thu hoi buoc 2 la: " +
		// spkmList.size() + "\n");
		return spkmList;
	}

	private List<ISanpham> getSpkmthuhoiList(String pxkId, dbutils db) {
		// spkm trong phieuxuatkho cu
		List<ISanpham> spkmOldList = new ArrayList<ISanpham>();
		String query = "select kho_fk as khoId, kbh_fk as kbhId, sanpham_fk as spId, sum(soluong) as soluong from phieuxuatkho_spkm where pxk_fk = '"
			+ pxkId + "' group by kho_fk, kbh_fk, sanpham_fk";

		System.out.println("Query lan 1: " + query + "\n");

		ResultSet spOld = db.get(query);
		/* if (spOld != null) */
		{
			try {
				while (spOld.next()) {
					ISanpham sp = new Sanpham(spOld.getString("spId"), "", "",
							spOld.getString("soluong"),
							spOld.getString("khoId"), spOld.getString("kbhId"),
							"", "");
					spkmOldList.add(sp);
				}
				spOld.close();
			} catch (Exception e) {
			}
		}

		// tinh lai so lg cac spkm phai thu hoi (nhung donhang daxuatkho ma huy
		// thi ko can lay spkm, khi do spkmOldList se thu hoi dung soluong
		// daxuatkho bandau cua nhung don hang da huy nay)
		// List<ISanpham> spkmNewList = new ArrayList<ISanpham>();
		// nhung phieu thu hoi da chot cua phieu xuat kho nay
		query = "select sanpham_fk as spId, soluong, kho_fk as khoId, kbh_fk as kbhId from phieuthuhoi_spkm where pth_fk in (select pk_seq from phieuthuhoi where phieuxuatkho_fk = '"
			+ pxkId + "' and trangthai = '1')";
		query += " union all ";
		query += "select d.pk_seq as spId, soluong, b.kho_fk as khoId, e.kbh_fk as kbhId from donhang_ctkm_trakm a inner join ctkhuyenmai b on a.ctkmid = b.pk_seq inner join sanpham d on a.spMa = d.ma inner join donhang e on a.donhangId = e.pk_seq where a.spMa is not null and e.trangthai != '2' and a.donhangId in (select donhang_fk from phieuxuatkho_donhang where pxk_fk = '"
			+ pxkId + "') ";

		query = "select spId, sum(soluong) as soluong, khoId, kbhId from ("
			+ query + ") kh group by spId, khoId, kbhId";

		System.out.println("Query lay spkm: " + query + "\n");

		List<ISanpham> spkmNewList = new ArrayList<ISanpham>();
		ResultSet spThuhoi2 = db.get(query);
		/* if(spThuhoi2 != null) */
		{
			try {
				while (spThuhoi2.next()) {
					ISanpham sp = new Sanpham(spThuhoi2.getString("spId"), "",
							"", spThuhoi2.getString("soluong"),
							spThuhoi2.getString("khoId"),
							spThuhoi2.getString("kbhId"), "", "");

					spkmNewList.add(sp);
				}
				spThuhoi2.close();
			} catch (Exception e) {
			}
		}

		// thu hoi sp trakhyenmai (ve kho_fk, kbh_fk)
		List<ISanpham> spkmList = new ArrayList<ISanpham>();

		for (int i = 0; i < spkmOldList.size(); i++) {
			Sanpham spA = (Sanpham) spkmOldList.get(i);
			for (int j = 0; j < spkmNewList.size(); j++) {
				Sanpham spB = (Sanpham) spkmNewList.get(j);
				if ((spB.getId().trim().equals(spA.getId().trim()))
						&& (spB.getDonvitinh().trim().equals(spA.getDonvitinh()
								.trim()))
								&& (spB.getDongia().trim().equals(spA.getDongia()
										.trim()))) {

					int slg = Math.abs(Integer.parseInt(spA.getSoluong())
							- Integer.parseInt(spB.getSoluong()));

					spkmOldList.get(i).setSoluong(Integer.toString(slg));
					spkmNewList.remove(j);
					j = 0; // quet lai
				}
			}
			if (Integer.parseInt(spkmOldList.get(i).getSoluong()) > 0) {
				ISanpham sp = new Sanpham(spkmOldList.get(i).getId(), "", "",
						spkmOldList.get(i).getSoluong(), spkmOldList.get(i)
						.getDonvitinh(),
						spkmOldList.get(i).getDongia(), "", "");
				spkmList.add(sp);
			}
		}
		return spkmList;
	}

	public String getPxkId() {
		return this.pxkId;
	}

	public void setPxkId(String pxkId) {
		this.pxkId = pxkId;
	}

	public String DeleteDonHangDxk() {
		try {
			db.getConnection().setAutoCommit(true);

			String query = "update donhang set trangthai='2',ngayhuy=getdate(),NGUOIXOA='"
				+ this.userId
				+ "' where pk_seq = '"
				+ this.id
				+ "' and trangthai = 3";
			if (db.updateReturnInt(query) != 1) {
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return "Khong The Cap Nhat";
			}

			// cap nhat phan bo km
			query = "select ctkmId, sum(tonggiatri) as tonggiatri from donhang_ctkm_trakm where donhangid = '"
				+ this.id + "' group by ctkmId";
			ResultSet rs = db.get(query);
			if (rs != null) {
				try {
					while (rs.next()) {
						String ctkmId = rs.getString("ctkmId");
						String tonggiatri = rs.getString("tonggiatri");

						String st = "update Phanbokhuyenmai set DASUDUNG = DASUDUNG - '"
							+ tonggiatri
							+ "' where ctkm_fk='"
							+ ctkmId
							+ "' and npp_fk='" + this.nppId + "'";
						// db.update("update CTKhuyenmai set DASUDUNG = DASUDUNG - '"
						// + tonggiatri + "' where ctkm_fk = '" + ctkmId + "'");
						if (!db.update(st)) {
							this.db.getConnection().rollback();
							return "Khong The Cap Nhat :" + st;
						}
					}
					rs.close();
				}

				catch (Exception e) {
					this.db.getConnection().rollback();
					return e.toString();
				}

			}

			this.createPxkId();
			if (this.pxkId.length() > 0) {
				String msg = this.createPth(this.pxkId, db);
				if (msg.length() > 0) {
					db.getConnection().rollback();
					return "4.Khong the tao phieu thu hoi cua don hang: "
					+ this.id + ", " + msg;
				}
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);

		}

		catch (Exception e) {
			try {
				db.getConnection().rollback();
			} catch (Exception e1) {
			}

			return "5.Khong the xoa don hang: " + this.id + ", "
			+ e.getMessage();
		}

		return "";

	}

	public boolean isCokhuyenmai() {
		return this.cokm;
	}

	public void setCokhuyenmai(boolean cokm) {
		this.cokm = cokm;
	}

	public boolean isChuaApkhuyenmai() {
		return this.chuaApkm;
	}

	public void setIsChuaApkhuyenmai(boolean chuaApkm) {
		this.chuaApkm = chuaApkm;
	}

	public boolean isDamuahang() {
		return this.dacoDh;
	}

	public void setIsDamuahang(boolean damuahang) {
		this.dacoDh = damuahang;
	}

	public boolean isDxkChuaChot() {
		return this.daxuatkhoChuachot;
	}

	public void setIsDxkChuaChot(boolean cokm) {
		this.daxuatkhoChuachot = cokm;
	}

	public void setNgayKs(String ngayks) {
		this.ngayks = ngayks;
	}

	public String getNgayKs() {
		Utility u = new Utility();
		this.ngayks =u.ngaykhoaso(this.nppId);
		return this.ngayks;
	}

	String IsChiNhanh;

	public String getIsChiNhanh() {
		return IsChiNhanh;
	}

	public void setIsChiNhanh(String isChiNhanh) {
		this.IsChiNhanh = isChiNhanh;
	}

	String ckdh;

	public String getChietkhauDH() {
		return ckdh;
	}

	public void setChietkhauDH(String ckdh) {
		this.ckdh = ckdh;
	}

	String coTrungBay;

	public void setCotrungbay(String cotrungbay) {
		this.coTrungBay = cotrungbay;
	}

	public String getCotrungbay() {
		return this.coTrungBay;
	}

	private int getSoXuatTheoScheme(String dhId, String ctkmId) {
		int soxuat = 0;

		String query = "select isnull(MIN(Soxuat), '0') as SoXuatThoa, COUNT(nsptbId) as sonhom "
			+ "\n from "
			+ "\n ( "
			+ "\n		select nsptbId, case when pheptoan = 1 then 'AND' else 'OR' end as pheptoan,  "
			+ "\n		case  when (LOAI = 2 and tongluongPhaiMua > 0) then tongluong / tongluongPhaiMua  "
			+ "\n		when (LOAI = 2 and tongtienPhaiMua > 0) then tongtien / tongtienPhaiMua  "
			+ "\n		else soxuatAnd end as Soxuat "
			+ "\n		from"
			+ "\n		( "
			+ "\n			select dieukientrungbay.*, trungbaytheodk.KHACHHANG_FK, SUM(trungbaytheodk.tongluong) as tongluong, SUM(trungbaytheodk.tongtien) as tongtien, "
			+ "\n			COUNT(case when trungbaytheodk.batbuoc > 0 then 1 else null end ) sospphaimua, "
			+ "\n			MIN (trungbaytheodk.tongluong / trungbaytheodk.batbuoc) as soxuatAnd "
			+ "\n			from "
			+ "\n			( "
			+ "\n				select b.PK_SEQ as nsptbId, b.LOAI, a.pheptoan, sum( distinct ISNULL(TONGLUONG, '0')) as tongluongPhaiMua,  "
			+ "\n				SUM( distinct ISNULL(tongtien, 0) ) as tongtienPhaiMua,  "
			+ "\n				count(case when isnull(c.soluong, '0') > 0 then 1 else null end) as sospbatbuoc  "
			+ "\n				from CTTB_NHOMSPTRUNGBAY a inner join NHOMSPTRUNGBAY b on a.NHOMSPTRUNGBAY_FK = b.PK_SEQ "
			+ "\n				inner join NHOMSPTRUNGBAY_SANPHAM c on a.NHOMSPTRUNGBAY_FK = c.NHOMSPTRUNGBAY_FK "
			+ "\n				where CTTRUNGBAY_FK = '"
			+ ctkmId
			+ "'  "
			+ "\n				group by b.PK_SEQ, b.LOAI, a.pheptoan "
			+ "\n			) dieukientrungbay "
			+ "\n			inner join "
			+ "\n			( "
			+ "\n				select muatrongnhom.*, batbuocmua.batbuoc "
			+ "\n				from "
			+ "\n				( "
			+ "\n					select a.KHACHHANG_FK, c.NHOMSPTRUNGBAY_FK as nspId, b.SANPHAM_FK, SUM(b.SOLUONG * b.GIAMUA) as tongtien, SUM(b.soluong) as tongluong, '1' as type  "
			+ "\n					from DONHANG a inner join DONHANG_SANPHAM b on a.PK_SEQ = b.DONHANG_FK "
			+ "\n					inner join NHOMSPTRUNGBAY_SANPHAM c on b.SANPHAM_FK = c.SANPHAM_FK "
			+ "\n					where a.PK_SEQ = '"
			+ dhId
			+ "' and  "
			+ "\n					c.NHOMSPTRUNGBAY_FK in ( select NHOMSPTRUNGBAY_FK from CTTB_NHOMSPTRUNGBAY where CTTRUNGBAY_FK = '"
			+ ctkmId
			+ "' )  "
			+ "\n					group by KHACHHANG_FK, c.NHOMSPTRUNGBAY_FK, b.SANPHAM_FK "
			+ "\n				) muatrongnhom "
			+ "\n				left join  "
			+ "\n				( "
			+ "\n					select NHOMSPTRUNGBAY_FK as nspId, SANPHAM_FK, case when isnull(soluong, '0') <= 0 then -1 else soluong end as batbuoc  "
			+ "\n					from NHOMSPTRUNGBAY_SANPHAM  "
			+ "\n					where NHOMSPTRUNGBAY_FK in ( select NHOMSPTRUNGBAY_FK from CTTB_NHOMSPTRUNGBAY where CTTRUNGBAY_FK = '"
			+ ctkmId
			+ "' ) "
			+ "\n				)  "
			+ "\n				batbuocmua on muatrongnhom.SANPHAM_FK = batbuocmua.SANPHAM_FK and muatrongnhom.nspId = batbuocmua.nspId "
			+ "\n				where muatrongnhom.tongluong > batbuocmua.batbuoc "
			+ "\n			) trungbaytheodk on dieukientrungbay.nsptbId = trungbaytheodk.nspId  "
			+ "\n			group by  dieukientrungbay.nsptbId, dieukientrungbay.LOAI, dieukientrungbay.tongluongPhaiMua, dieukientrungbay.tongtienPhaiMua,  "
			+ "\n			dieukientrungbay.sospbatbuoc, dieukientrungbay.pheptoan, trungbaytheodk.KHACHHANG_FK  "
			+ "\n			having COUNT(case when trungbaytheodk.batbuoc > 0 then 1 else null end ) >= dieukientrungbay.sospbatbuoc "
			+ "\n		) chuongtrinhtrungbay " + "\n ) ngansachkhuyenmai";

		System.out.println("___Lay so xuat CTTB: " + query);

		ResultSet rs = db.get(query);
		int soDK = 0;
		if (rs != null) {
			try {
				if (rs.next()) {
					soxuat = rs.getInt("SoXuatThoa");
					soDK = rs.getInt("sonhom");
				}
				rs.close();
			} catch (Exception e) {
				System.out.println("115.Error: " + e.getMessage());
			}
		}

		// Check So Dieu Kien AND
		int sophaithoa = 0;
		query = "select SUM(case pheptoan when 1 then 1 else 0 end) as sodieukien from CTTB_NHOMSPTRUNGBAY where CTTRUNGBAY_FK = '"
			+ ctkmId + "' ";
		ResultSet rsCheck = db.get(query);
		if (rsCheck != null) {
			try {
				while (rsCheck.next()) {
					sophaithoa = rsCheck.getInt("sodieukien");
				}
				rsCheck.close();
			} catch (Exception e) {
				System.out.println("115.Errror: Loi: " + e.getMessage());
			}
		}

		System.out.println("_____________So dieu kien Ly thuyet: " + soDK
				+ "  --- So bat buoc: " + sophaithoa);
		if (soDK < sophaithoa) {
			soxuat = 0;
		}

		return soxuat;
	}

	boolean aplaitb;

	public boolean isAplaitrungbay() {
		return this.aplaitb;
	}

	public void setAplaitrugnbay(boolean aplaitb) {
		this.aplaitb = aplaitb;
	}

	public Hashtable<String, Integer> ApTrungBay(String dhId, String khId,
			String nppId, String ngaydh) {
		/*******************
		 * 0 - Khong co trung bay, 1 - Co trung bay, -1 - Loi khi cap nhat trung
		 * bay
		 *********************/

		Hashtable<String, Integer> kq = new Hashtable<String, Integer>();

		// truong hop cap nhat, phai xoa so xuat cu
		String query = "select DENGHITRATB_FK, khachhang_fk, dat from DENGHITRATB_DONHANG where donhang_fk = '"
			+ dhId + "'";
		ResultSet rsDelete = db.get(query);
		if (rsDelete != null) {
			try {
				db.getConnection().setAutoCommit(false);
				while (rsDelete.next()) {
					String dk_fk = rsDelete.getString("DENGHITRATB_FK");
					String kh_fk = rsDelete.getString("khachhang_fk");
					String dat = rsDelete.getString("dat");

					query = "delete DENGHITRATB_DONHANG where DENGHITRATB_FK = '"
						+ dk_fk
						+ "' and khachhang_fk = '"
						+ kh_fk
						+ "' and donhang_fk = '" + dhId + "'";
					if (!db.update(query)) {
						this.msg = "1.Không thể cập nhật DENGHITRATB_DONHANG "
							+ query;
						db.getConnection().rollback();
						return kq;
					}

					query = "update DENGHITRATB_KHACHHANG set dat = dat - "
						+ dat + " where DENGHITRATB_FK = '" + dk_fk
						+ "' and khachhang_fk = '" + kh_fk + "'";
					if (!db.update(query)) {
						this.msg = "2.Không thể cập DENGHITRATB_KHACHHANG "
							+ query;
						db.getConnection().rollback();
						return kq;
					}

				}
				rsDelete.close();

				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
			} catch (Exception e) {
				try {
					db.getConnection().rollback();
				} catch (Exception e1) {
				}
				this.msg = "115. Loi: " + e.getMessage();
				return kq;
			}
		}

		query = "select a.CTTRUNGBAY_FK, b.DENGHITRATB_FK, c.scheme, c.NGAYTDS as TuNgay, c.NGAYKTTDS as DenNgay, isnull(b.DAT, 0) as DaDat, b.XUATDUYET - isnull(b.DAT, 0) as NganSachConLai "
			+ "from DENGHITRATRUNGBAY a inner join DENGHITRATB_KHACHHANG b on a.PK_SEQ = b.DENGHITRATB_FK  "
			+ "inner join CTTRUNGBAY c on a.CTTRUNGBAY_FK = c.PK_SEQ "
			+ "where KHACHHANG_FK = '"
			+ khId
			+ "' and a.NPP_FK = '"
			+ this.nppId
			+ "' and c.NGAYTDS <= '"
			+ ngaydh
			+ "' "
			+ "and c.NGAYKTTDS >= '"
			+ ngaydh
			+ "' and isnull(b.DAT, 0) < b.XUATDUYET and a.trangthai=1 ";

		System.out.println("___Check CTTB: " + query);

		ResultSet rs = db.getScrol(query);

		String cttb_fk = "";
		String dktb_fk = "";

		int ngansach = 0;
		int NganSachConLai = 0;
		int soXuat = 0;

		int i = 0;

		if (rs != null) // ap trung bay repair
		{
			try {
				db.getConnection().setAutoCommit(false);

				while (rs.next()) {

					cttb_fk = rs.getString("CTTRUNGBAY_FK");
					dktb_fk = rs.getString("DENGHITRATB_FK");
					NganSachConLai = rs.getInt("NganSachConLai");

					// LAY NGAN SACH CON LAI
					query = "select a.LEVEL_PHANBO, "
						+ "	case a.LEVEL_PHANBO when 0 then  "
						+ "		( select NGANSACH from PHANBOTRUNGBAY where NPP_FK = '"
						+ this.nppId
						+ "' and CTTB_FK = a.PK_SEQ ) "
						+ "	when 1 then   "
						+ "		( select NGANSACH from PHANBOTRUNGBAY where VUNG_FK = ( select VUNG_FK from KHUVUC where pk_seq in ( select KHUVUC_FK from NHAPHANPHOI where PK_SEQ = '"
						+ this.nppId
						+ "' )  ) and CTTB_FK = a.PK_SEQ ) "
						+ "	else "
						+ "		( select NGANSACH from PHANBOTRUNGBAY where KHUVUC_FK = ( select KHUVUC_FK from NHAPHANPHOI where PK_SEQ = '"
						+ this.nppId
						+ "' ) and CTTB_FK = a.PK_SEQ ) "
						+ "	end as NGANSACH, "
						+ "	ISNULL( ( "
						+ "		select SUM(ISNULL( b.DAT , 0 ) )  "
						+ "		from DENGHITRATRUNGBAY a inner join DENGHITRATB_KHACHHANG b on a.PK_SEQ = b.DENGHITRATB_FK "
						+ "				inner join CTTRUNGBAY c on a.CTTRUNGBAY_FK = c.PK_SEQ "
						+ "				inner join NHAPHANPHOI d on a.NPP_FK = d.PK_SEQ "
						+ "				inner join KHUVUC e on d.KHUVUC_FK = e.PK_SEQ "
						+ "		where CTTRUNGBAY_FK = '"
						+ cttb_fk
						+ "' and "
						+ "				( case c.LEVEL_PHANBO   when 0 then a.NPP_FK when 1 then e.VUNG_FK when 2 then d.KHUVUC_FK end  )    "
						+ "			  = ( case c.LEVEL_PHANBO   when 0 then '"
						+ this.nppId
						+ "'   "
						+ "										when 1 then ( select VUNG_FK from KHUVUC where PK_SEQ = ( select KHUVUC_FK from NHAPHANPHOI where PK_SEQ = '"
						+ this.nppId
						+ "' ) )   "
						+ "										when 2 then ( select KHUVUC_FK from NHAPHANPHOI where PK_SEQ = '"
						+ this.nppId
						+ "' )  end  )    "
						+ "	), 0 ) as DASUDUNG "
						+ "from CTTRUNGBAY a   "
						+ "where a.PK_SEQ = '" + cttb_fk + "' ";
					ResultSet rsNGANSACH = db.get(query);
					if (rsNGANSACH.next()) {
						ngansach = rsNGANSACH.getInt("NGANSACH")
						- rsNGANSACH.getInt("DASUDUNG");
					}
					rsNGANSACH.close();

					soXuat = getSoXuatTheoScheme(dhId, cttb_fk);

					System.out.println("__-____Soxuat la: " + soXuat);

					if (soXuat > 0) {
						if (ngansach - soXuat <= 0) {
							soXuat = ngansach;
						}

						if (soXuat > 0) {
							if (soXuat > NganSachConLai)
								soXuat = NganSachConLai;

							kq.put(rs.getString("scheme"), soXuat);
						}
					}
				}
				rs.close();

				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
			} catch (Exception e) {
				System.out.println("__Exception: " + e.getMessage());
				this.msg = "__Exception: " + e.getMessage();

				try {
					db.getConnection().rollback();
				} catch (SQLException e1) {
				}

				return kq;
			}
		}

		return kq;
	}

	public boolean LuuTrungBay(String scheme, Integer soXuat) {

		String dktb_fk = "";
		String query = "select a.CTTRUNGBAY_FK, b.DENGHITRATB_FK, c.scheme, c.NGAYTDS as TuNgay, c.NGAYKTTDS as DenNgay, isnull(b.DAT, 0) as DaDat, b.DANGKY - isnull(b.DAT, 0) as NganSachConLai "
			+ "from DENGHITRATRUNGBAY a inner join DENGHITRATB_KHACHHANG b on a.PK_SEQ = b.DENGHITRATB_FK  "
			+ "inner join CTTRUNGBAY c on a.CTTRUNGBAY_FK = c.PK_SEQ "
			+ "where KHACHHANG_FK = '"
			+ khId
			+ "' and a.NPP_FK = '"
			+ this.nppId
			+ "' and c.NGAYTDS <= '"
			+ this.ngaygiaodich
			+ "' "
			+ "and c.NGAYKTTDS >= '"
			+ this.ngaygiaodich
			+ "'  and c.scheme = '" + scheme + "'";
		System.out.println("Luu trung bay : " + query);

		try {
			ResultSet rs = db.get(query);
			while (rs.next()) {

				dktb_fk = rs.getString("DENGHITRATB_FK");

				query = "insert DENGHITRATB_DONHANG(DENGHITRATB_FK, KHACHHANG_FK, DONHANG_FK, DAT) "
					+ "values('"
					+ dktb_fk
					+ "', '"
					+ khId
					+ "', '"
					+ this.id + "', '" + soXuat + "')";
				if (!db.update(query)) {
					this.msg = "2.Không thể cập nhật trưng bày";
					rs.close();
					// db.getConnection().rollback();
					return false;
				}

				query = "update DENGHITRATB_KHACHHANG set dat = isnull( ( select SUM(DAT) from DENGHITRATB_DONHANG  "
					+ "where DENGHITRATB_FK = '"
					+ dktb_fk
					+ "' and KHACHHANG_FK = '"
					+ khId
					+ "' group by DENGHITRATB_FK, KHACHHANG_FK ) , 0 )  "
					+ "where DENGHITRATB_FK = '"
					+ dktb_fk
					+ "' and KHACHHANG_FK = '" + khId + "'";

				System.out.println("11.-------Update DK trung bay: " + query);
				if (!db.update(query)) {
					this.msg = "3.Không thể cập nhật trưng bày";
					rs.close();
					// db.getConnection().rollback();
					return false;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

		return false;
	}

	public String getDiachigiaohang() {
		return this.diachigiaohang;
	}

	public void setDiachigiaohang(String diachigiaohang) {
		this.diachigiaohang = diachigiaohang;
	}

	public String getGhiChu() {
		return this.ghichu;
	}

	public void setGhiChu(String ghichu) {
		this.ghichu = ghichu;
	}

	public String getNgaygiaohang() {
		return this.ngaygh;
	}

	public void setNgaygiaohang(String ngaygh) {
		this.ngaygh = ngaygh;
	}

	public double getNoCu() {
		return this.nocu;
	}

	public void setNoCu(double nocu) {
		this.nocu = nocu;
	}

	ResultSet nvgnRs;

	public String getnvgnId() {

		return nvgnId;
	}

	public void setnvgnId(String nvgnId) {
		this.nvgnId = nvgnId;

	}

	public ResultSet getnvgnRs() {

		return nvgnRs;
	}

	public void setSotiengiam(String Sotiengiam) {

		this.sotiengiam = Sotiengiam;
	}

	public String getSotiengiam() {

		return this.sotiengiam;
	}

	@Override
	public String ApTrungBay() {
		/*
		 * try { db.getConnection().setAutoCommit(false); String diengiai = "";
		 * String query =
		 * "\n update tb set DaTra = 0  from KhachHang_ThuongTrungBay tb " +
		 * "\n where khachhang_fk =(select khachhang_fk from donhang where pk_seq = "
		 * + this.id + ")" +
		 * "\n       and  exists  (select 1  from  DONHANG_CTTB_TRATB a where a.DONHANG_FK = '"
		 * + this.id + "' " +
		 * "\n                and a.CTTB_FK = tb.CTTB_FK   and a.denghitratrungbay_fk =  tb.denghitratrungbay_fk  ) "
		 * +
		 * "\n       and not   exists  (select 1  from  DONHANG_CTTB_TRATB x inner join DonHang y on x.DonHang_FK = y.pk_Seq "
		 * + "\n                            where x.DONHANG_FK != '"+ this.id +
		 * "'  and y.trangthai <> 2  " +
		 * "\n                                    and y.KHACHHANG_FK = tb.KHACHHANG_FK  and x.CTTB_FK = tb.CTTB_FK   and x.denghitratrungbay_fk =  tb.denghitratrungbay_fk  ) "
		 * ; int kq = db.updateReturnInt(query); if (kq > 1 || kq < 0) {
		 * db.getConnection().rollback();
		 * db.getConnection().setAutoCommit(true); return "Lỗi:" + query; }
		 * 
		 * query = "delete DONHANG_CTTB_TRATB where donhang_fk = " + this.id;
		 * System.out.println(" query cttb = " + query); if (!db.update(query))
		 * { db.getConnection().rollback();
		 * db.getConnection().setAutoCommit(true); return "Lỗi:" + query; }
		 * query =
		 * "\n select top 1 khtb.denghitratrungbay_fk,isnull(cb.DIENGIAI,cb.scheme) as diengiai  "
		 * +
		 * "\n		,khtb.Soxuat,khtb.CTTB_FK, khtb.TraTrungBay_FK, khtb.KHACHHANG_FK,isnull(khtb.TongGiaTri,0)TongGiaTri,khtb.SANPHAM_FKs"
		 * + "\n	 ,isnull(TongLuong,0) as TongLuong " +
		 * "\n from KhachHang_ThuongTrungBay khtb " +
		 * "\n	inner join  CTTrungBay cb on khtb.CTTB_FK  = cb.pk_seq " +
		 * "\n where 1 = 1 " + "\n			and   DaTra = 0 and khachHang_fk =" +
		 * this.khId +
		 * "\n			and isnull(TongGiaTri,0) <= isnull((select tonggiatri from donhang where pk_seq = '"
		 * + this.id+ "' ),-1) 	 " +
		 * "\n     and not exists( select 1 from DONHANG x inner join DONHANG_CTTB_TRATB y on x.PK_SEQ = y.DONHANG_FK where x.TRANGTHAI <> 2 and x.KHACHHANG_FK = khtb.KhachHang_fk and khtb.CTTB_FK = y.CTTB_FK  and y.denghitratrungbay_fk =khtb.denghitratrungbay_fk )	 "
		 * + "\n	order by cb.pk_Seq asc ,khtb.denghitratrungbay_fk asc ";
		 * System.out.println(" query cttb = " + query); ResultSet rs =
		 * db.get(query); double TongGiaTri = 0; if (rs.next()) { diengiai =
		 * rs.getString("diengiai"); String CTTB_FK = rs.getString("CTTB_FK");
		 * String denghitratrungbay_fk = rs .getString("denghitratrungbay_fk");
		 * String TraTrungBay_FK = rs.getString("TraTrungBay_FK"); String
		 * SANPHAM_FKs = rs.getString("SANPHAM_FKs");
		 * 
		 * TongGiaTri = rs.getDouble("TongGiaTri"); double TongLuong =
		 * rs.getDouble("TongLuong"); double Soxuat = rs.getDouble("Soxuat"); if
		 * (TongLuong == 0 && SANPHAM_FKs == null)// trả tiền { query =
		 * "\n  select ROUND(	(DH.TongTien -ROUND((dh.TongTien-dh.kmCK)*(dh.ptCK/100.0),0)-dh.kmCK-dh.tienTB-dh.tienTL)*1.05-dh.kmTIEN,0) as giatridonhang  "
		 * + "\n  from " + "\n 	 ( " + "\n 		 select " +
		 * "\n 			isnull( (select ROUND( sum(soluong*giamua),0) from donhang_sanpham where donhang_fk =a.pk_Seq) ,0) as TongTien, "
		 * +
		 * "\n 			isnull((select SUM(round(TONGGIATRI,0) ) from DONHANG_CTKM_TRAKM  "
		 * +
		 * "\n 					 where DONHANGID = a.pk_Seq and TRAKMID in (select PK_SEQ from TRAKHUYENMAI where LOAI = 2)),0)  as kmCK, "
		 * +
		 * "\n 			isnull((select SUM(round(TONGGIATRI,0)) from DONHANG_CTTB_TRATB where DONHANG_FK = A.pk_Seq and sanpham_FK IS NULL ),0) as tienTB, "
		 * +
		 * "\n 			isnull((select SUM(round(TONGGIATRI,0)) from DUYETTRAKHUYENMAI_DONHANG where DONHANG_FK = A.pk_Seq and sanpham_FK IS NULL ),0) as tienTL, "
		 * +
		 * "\n 			isnull((select SUM(round(TONGGIATRI,0) ) from DONHANG_CTKM_TRAKM  "
		 * +
		 * "\n 					 where DONHANGID = a.pk_Seq and TRAKMID in (select PK_SEQ from TRAKHUYENMAI where LOAI = 1)),0)  as kmTIEN, "
		 * + "\n 			A.CHIETKHAU as ptCK,PK_SEQ as dhId " +
		 * "\n 		from DONHANG a  " + "\n   		where a.pk_Seq='"+ this.id+ "'				"
		 * + "\n 	 )as dh inner join DONHANG a on a.PK_SEQ=dh.dhId " +
		 * "\n where dh.dhId='" + this.id + "' "; double giatri = 0; ResultSet
		 * gtRs = db.get(query); if (gtRs.next()) { giatri =
		 * gtRs.getDouble("giatridonhang"); } gtRs.close(); if (giatri <
		 * TongGiaTri) { db.getConnection().commit();
		 * db.getConnection().setAutoCommit(true); return ""; }
		 * 
		 * query =
		 * "\n insert DONHANG_CTTB_TRATB(denghitratrungbay_fk,CTTB_FK,TTB_FK,DonHang_fk,SoXuat,tonggiatri)"
		 * + "\n	select "+ denghitratrungbay_fk+ "," + CTTB_FK+ ","+
		 * TraTrungBay_FK+ ","+ this.id+ "," + Soxuat + "," + TongGiaTri;
		 * System.out.println(" query cttb = " + query); if (!db.update(query))
		 * { db.getConnection().rollback();
		 * db.getConnection().setAutoCommit(true); return "Lỗi:" + query; }
		 * query =
		 * "update KhachHang_ThuongTrungBay set datra = 1 where datra = 0 and denghitratrungbay_fk ="
		 * + denghitratrungbay_fk + "    and khachhang_fk =" + this.khId +
		 * " and cttb_fk ="+ CTTB_FK + " and TraTrungBay_FK = " +
		 * TraTrungBay_FK; System.out.println(" query cttb = " + query); if
		 * (db.updateReturnInt(query) != 1) { db.getConnection().rollback();
		 * db.getConnection().setAutoCommit(true); return "Lỗi:" + query; }
		 * DecimalFormat df = new DecimalFormat("###,###,##0"); diengiai =
		 * " Đơn hàng được thưởng CTTB (" + diengiai+ ") . Giá trị :" +
		 * df.format(TongGiaTri) + "! "; } else // trả sản phẩm {
		 * 
		 * String[] spList = SANPHAM_FKs.split(","); // sp1-sl,sp2-sl,sp3-sl
		 * 
		 * for (int i = 0; i < spList.length; i++) {
		 * 
		 * String[] spDetail = spList[i].split("-");
		 * 
		 * query =
		 * "\n insert DONHANG_CTTB_TRATB(denghitratrungbay_fk,CTTB_FK,TTB_FK,DonHang_fk,SoXuat,soluong,sanpham_fk)"
		 * + "\n	select "+ denghitratrungbay_fk+ ","+ CTTB_FK+ ","+
		 * TraTrungBay_FK+ ","+ this.id+ ","+ Soxuat+ ","+ spDetail[1]+ "," +
		 * spDetail[0]; System.out.println(" query cttb = " + query); if
		 * (!db.update(query)) { db.getConnection().rollback();
		 * db.getConnection().setAutoCommit(true); return "Lỗi:" + query; } }
		 * query =
		 * "\n update KhachHang_ThuongTrungBay set datra = 1 where datra = 0 and denghitratrungbay_fk ="
		 * + denghitratrungbay_fk+ "    and khachhang_fk ="+ this.khId +
		 * " and cttb_fk =" + CTTB_FK; System.out.println(" query cttb = " +
		 * query); if (db.updateReturnInt(query) != 1) {
		 * db.getConnection().rollback();
		 * db.getConnection().setAutoCommit(true); return "Lỗi:" + query; }
		 * diengiai = " Đơn hàng được thưởng CTTB (" + diengiai+ ") ! ";
		 * 
		 * }
		 * 
		 * } rs.close();
		 * 
		 * db.getConnection().commit(); db.getConnection().setAutoCommit(true);
		 * 
		 * return ""; } catch (Exception e) { geso.dms.center.util.Utility.rollback_throw_exception(db); // this.msg
		 * = return "Lỗi: Exception->" + e.getMessage(); }
		 */
		return "";
	}

	

	private void getTraTrungbay() {
		String query = "select s.PK_SEQ, s.MA, s.TEN, a.SOLUONG, dv.DONVI, a.tonggiatri, ct.SCHEME, ct.DIENGIAI "
			+ "from DONHANG_CTTB_TRATB a left join SANPHAM s on s.PK_SEQ = a.SANPHAM_FK "
			+ "left join DONVIDOLUONG dv on dv.PK_SEQ = s.DVDL_FK "
			+ "inner join CTTRUNGBAY ct on ct.PK_SEQ = a.CTTB_FK "
			+ "WHERE DONHANG_FK = " + this.id;

		System.out.println("1.Khoi tao TKM  :" + query);

		ResultSet rs = db.get(query);
		try {
			while (rs.next()) {
				String schemeName = "CTTB: " + rs.getString("scheme");
				String soluong = rs.getString("soluong");
				String donvi = rs.getString("DONVI");
				float tongiatri = rs.getFloat("tonggiatri");

				if (rs.getString("MA") == null) {

					this.scheme_tongtien.put(schemeName, tongiatri);
					this.aplaikm = true; // co ctkm
					this.cokm = true;
				} else {

					String[] param = new String[12];
					ISanpham sp = null;
					param[0] = rs.getString("PK_SEQ");
					param[1] = rs.getString("MA");
					param[2] = rs.getString("ten");
					param[3] = soluong;
					param[4] = donvi;
					param[5] = "0";
					param[6] = schemeName;
					param[7] = "0";
					param[8] = "0";
					param[9] = "0";
					param[10] = "0";
					param[11] = "0";

					sp = new Sanpham(param);
					this.scheme_sanpham.add(sp);
					this.aplaikm = true;
					this.cokm = true;

				}

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void getTraTichluy() {
		String query = "";
		query = "SELECT s.PK_SEQ, s.MA, s.TEN, dv.DONVI, a.SOLUONG, a.TONGGIATRI, c.SCHEME  "
			+ "FROM DUYETTRAKHUYENMAI_DONHANG a "
			+ "inner join DUYETTRAKHUYENMAI b on a.duyetkm_fk = b.PK_SEQ "
			+ "inner join TIEUCHITHUONGTL c on c.PK_SEQ = b.CTKM_FK "
			+ "left join SANPHAM s on a.SanPham_FK = s.PK_SEQ "
			+ "left join DONVIDOLUONG dv on dv.PK_SEQ = s.DVDL_FK "
			+ "WHERE donhang_fk = " + this.id;

		System.out.println("1.Khoi tao TKM  :" + query);

		ResultSet rs = db.get(query);
		try {
			while (rs.next()) {
				String schemeName = "CTTL: " + rs.getString("scheme");
				String soluong = rs.getString("soluong");
				String donvi = rs.getString("DONVI");
				float tongiatri = rs.getFloat("tonggiatri");

				if (rs.getString("MA") == null) {

					this.scheme_tongtien.put(schemeName, tongiatri);
					this.aplaikm = true; // co ctkm
					this.cokm = true;
				} else {

					String[] param = new String[12];
					ISanpham sp = null;
					param[0] = rs.getString("PK_SEQ");
					param[1] = rs.getString("MA");
					param[2] = rs.getString("ten");
					param[3] = soluong;
					param[4] = donvi;
					param[5] = "0";
					param[6] = schemeName;
					param[7] = "0";
					param[8] = "0";
					param[9] = "0";
					param[10] = "0";
					param[11] = "0";

					sp = new Sanpham(param);
					this.scheme_sanpham.add(sp);
					this.aplaikm = true;
					this.cokm = true;

				}

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String getTrangThaiText() {
		try {
			if (this.id != null && this.id.trim().length() > 0) {
				String query = " select trangthai from donhang where pk_seq = "
					+ this.id;
				ResultSet rs = db.get(query);
				int tt = -1;
				if (rs.next()) {
					tt = rs.getInt("trangthai");
				}
				rs.close();

				if (tt == 0)
					return "Chưa chốt";
				if (tt == 1)
					return "Đã chốt";
				if (tt == 2)
					return "Đã hủy";

			}
		} catch (Exception e) {

		}
		return "";
	}

	private Object[] appendObjectArrayValue(Object[] obj, Object[] newObj) {
		ArrayList<Object> temp = new ArrayList<Object>(Arrays.asList(obj));
		for (int i = 0; i < newObj.length; i++) {
			temp.add(newObj[i]);
		}
		return temp.toArray();
	}

	public String[] getDongiaArr(Idbutils db, String ngaydonhang, String[] masp) {

		if (masp != null && masp.length > 0) {				

			String[] arr = new String[masp.length];

			try {

				String query =  "\n select count (*)c  " +
				"\n from khachhang a inner join nhaphanphoi npp on a.npp_fk = npp.pk_seq " +
				"\n inner join khachhang_tuyenbh tuyen on tuyen.khachhang_fk = a.pk_seq " +
				"\n inner join tuyenbanhang tbh on tbh.pk_seq = tuyen.tbh_fk " +
				"\n inner join daidienkinhdoanh ddkd on ddkd.pk_seq = tbh.ddkd_fk " +
				"\n where a.pk_seq = "+khId+" and npp.pk_seq = "+nppId+" --and ddkd.pk_seq = "+ddkdId;
				System.out.println("test"+query);
				ResultSet rs = db.get(query);
				while (rs.next()) {
					int count = rs.getInt("c");

					if (count <= 0) {
						rs.close();
						return null;
					}
				}
				rs.close();				

				for (int i = 0; i < masp.length; i++) {

					if(masp[i] == null || masp[i].trim().length() <=0) continue;

					if(this.donhangkhac.equals("1"))
					{
						arr[i] = "0";
						continue;
					}

					query = "\n select  dg.dongia   "+
					"\n from SANPHAM a  "+
					"\n inner join khachhang kh on kh.pk_seq =  '"+khId+"'  "+				
					"\n	cross apply ( select [dbo].[GiaCkBanLeNppSanPham]("+nppId+","+khId+",a.pk_seq,'"+ngaydonhang+"' ) dongia  )dg " + 
					"\n where a.trangthai = 1 and a.ma = N'"+masp[i]+"'";
					System.out.println("Check giá: "+query);
					rs = db.get(query);
					while (rs.next()) {
						arr[i] = rs.getString("dongia");
					}
					rs.close();
					
				}
			}

			catch (Exception e) {
				e.printStackTrace();
			}

			return arr;
		}
		else
			return null;
	}


	public static void main(String[] args) {

		String code = "\"../../DonhangUpdateSvl?userId=<%=userId%>&display=<%= dhlist.getString(\"dhId\") %>\"";
		System.out.println(changeSyntax1(code));
	}

	public static String changeSyntax1 (String code) {
		String result = "\"../../DonhangUpdateSvl?userId=<%=userId%>&update=<%= dhlist.getString(\"dhId\") %>\">";

		code = code.replace("<%=", "\"+");
		code = code.replace("%>", "+\"");
		code = code.replace("../", "");

		result = "\"../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter(\"RedirectNoScript\") + " + code + ")%>\"";
		return result;
	}
	public String getDonhangkhac() {
		return donhangkhac;
	}
	public void setDonhangkhac(String donhangkhac) {
		this.donhangkhac = donhangkhac;
	}

	@Override
	public String getGhiChuOption() {
		// TODO Auto-generated method stub
		return this.ghichuoption;
	}

	@Override
	public void setGhiChuOption(String ghichuoption) {
		// TODO Auto-generated method stub
		this.ghichuoption = ghichuoption;
	}

	@Override
	public ResultSet getGhichuOList() {
		// TODO Auto-generated method stub
		return this.ghichuORs;
	}

	@Override
	public void setGhichuOList(ResultSet ghichuoList) {
		// TODO Auto-generated method stub
		this.ghichuORs = ghichuoList;
	}
	
	public boolean getChap_nhan_giam_so_suat_km()
	{
		return this.chap_nhan_giam_so_suat_km;
	}
	public void setChap_nhan_giam_so_suat_km(boolean chap_nhan_giam_so_suat_km) {
		this.chap_nhan_giam_so_suat_km = chap_nhan_giam_so_suat_km;
	}
	
	public List<String> getCtkm_bi_giam_so_suat() {
		return ctkm_bi_giam_so_suat;
	}
	public void setCtkm_bi_giam_so_suat(
			List<String> ctkm_bi_giam_so_suat) {
		this.ctkm_bi_giam_so_suat = ctkm_bi_giam_so_suat;
	}
	
	public ResultSet createLoaiDonHangRs()
	{
		String query = " select id,ten from loaidonhang ";
		return db.get(query);
	}

	String tenSpComBo = "",giaSpComBo = "", soluongSpComBo = "" ;
	public String getTenSpComBo() {
		return tenSpComBo;
	}
	public void setTenSpComBo(String tenSpComBo) {
		this.tenSpComBo = tenSpComBo;
	}
	public String getGiaSpComBo() {
		return giaSpComBo;
	}
	public void setGiaSpComBo(String giaSpComBo) {
		this.giaSpComBo = giaSpComBo;
	}
	public String getSoluongSpComBo() {
		return soluongSpComBo;
	}
	public void setSoluongSpComBo(String soluongSpComBo) {
		this.soluongSpComBo = soluongSpComBo;
	}
	public ResultSet createRsTichluyList()
	{
		String dhSql = "0";
		if(this.id != null && this.id.trim().length() > 0 )
			dhSql = this.id;


		String query =  "\n select ct.DuyetKm_FK,ct.khId,ct.mucduyet,d.DIENGIAI" +
						"\n		,  case when  ct.donvi = 2 " +
						"\n 	then" +
						
						"\n 		STUFF(( " +
						"\n 			SELECT '; ' + spx.MA + '-' + spx.TEN + ': ' + cast( xxx.soluong as varchar)  + ' ' + dv.DONVI  + char(10)  " +
						"\n 			from  DUYETTRAKHUYENMAI_KHACHHANG_SanPham xxx " +
						"\n 			inner join SANPHAM spx on spx.pk_seq = xxx.sanpham_fk " +
						"\n 			inner join DONVIDOLUONG dv on dv.PK_SEQ = spx.DVDL_FK " +
						"\n 			where xxx.khId = ct.khId and xxx.duyetkm_fk = ct.duyetkm_fk " +
						"\n 	 	FOR XML PATH(''), TYPE).value('.', 'NVARCHAR(MAX)'), 1, 2, '')  " +											
						"\n		else N'Thưởng :' +  format(ct.thuong,'N','en-US')  end kq " +
						"\n from DUYETTRAKHUYENMAI_KHACHHANG ct " +
						"\n inner join DUYETTRAKHUYENMAI d on ct.duyetkm_fk  = d.PK_SEQ " +
						"\n  where  ct.khId = "+this.khId+" and ct.trangthai = 1 and d.TRANGTHAI = 1 and mucduyet >= 0 " +
						"\n 	and not exists ( select 1 from DONHANG_CTKM_TRAKM x inner join DONHANG dhy on x.DONHANGID = dhy.PK_SEQ  " +
						"\n 								where dhy.trangthai !=2 and dhy.PK_SEQ != "+dhSql+" and  dhy.KHACHHANG_FK = ct.khId and x.duyetkm_fk = ct.duyetkm_fk and not exists ( select 1 from DONHANGTRAVE tv where tv.trangthai = 3 and tv.DONHANG_FK = dhy.PK_SEQ) " +
						"\n 					) " ;
		System.out.println("query tichluyDacBietRs = "+ query);
		return db.get(query);			
	}
	public List<String> tichluyIdList = new ArrayList<String>();
	public List<String> getTichluyIdList() {
		return tichluyIdList;
	}
	public void setTichluyIdList(String[] a) {
		this.tichluyIdList.clear();
		if(a != null)
		{
			for(int i =0; i < a.length; i++)
				this.tichluyIdList.add(a[i] );
		}
	}
	
	public  String ApTichluyDacBiet() {
		try 
		{

			
			
			if(this.id.length() <=0)
				return "Không lấy được thông tin đơn hàng";
			
			if(tichluyIdList == null || tichluyIdList.size() <=0)
				return  "";
			
			db.getConnection().setAutoCommit(false);

			String dhId = this.id;
			String dsTichluyStr = "";
			for(int i =  0; i < tichluyIdList.size();i++)
			{
				if(dsTichluyStr.trim().length() > 0)
					dsTichluyStr  += ","+tichluyIdList.get(i);
				else
					dsTichluyStr  += tichluyIdList.get(i);
			}



			String query = "";
			String msg = Utility.Update_GiaTri_DonHang(dhId, db);
			if(msg.length() > 0)
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return msg;
			}


			query = " select tonggiatri,khachhang_fk from donhang where pk_seq =  "+ dhId;
			ResultSet rs = db.get(query);
			rs.next();
			double TienDonHang = rs.getDouble("tonggiatri");
			String khId =  rs.getString("khachhang_fk");
			rs.close();
			
			int check = geso.dms.center.util.Utility.KiemTraTransaction("KhachHang","pk_seq", khId, db);
			if(check <=0)
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return "Khách hàng đang được thao tác ở đơn hàng khác, vui lòng thử lại sau!";
				
				 
			}

			query = "\n select dh.khachhang_fk, dh.NPP_FK,d.ctkm_fk,ct.DuyetKm_FK,ct.khId,ct.mucduyet,d.DIENGIAI, ct.donvi , isnull(ct.thuong,0)thuong " +
					"\n from DUYETTRAKHUYENMAI_KHACHHANG ct " +
					"\n inner join donhang dh on ct.khId = dh.khachhang_fk and dh.pk_seq =  " + dhId +
					"\n inner join DUYETTRAKHUYENMAI d on ct.duyetkm_fk  = d.PK_SEQ " +
					"\n  where   ct.trangthai = 1 and d.TRANGTHAI = 1 and mucduyet >= 0 " +
					"\n 	and not exists ( select 1 from DONHANG_CTKM_TRAKM x inner join DONHANG dhy on x.DONHANGID = dhy.PK_SEQ  " +
					"\n 								where  dhy.KHACHHANG_FK = ct.khId and x.duyetkm_fk = ct.duyetkm_fk and dhy.TRANGTHAI !=2 and not exists ( select 1 from DONHANGTRAVE tv where tv.trangthai = 3 and tv.DONHANG_FK = dhy.PK_SEQ) " +
					"\n 					) " +
					"\n		and ct.duyetkm_fk in ("+ dsTichluyStr +")  " ;
			

			System.out.println(" query   tichluy = " + query);
			rs = db.get(query);
			String khongduochuong = "";
			while (rs.next()) 
			{
				String diengiai = rs.getString("DIENGIAI");
				int donvi = rs.getInt("donvi") ; //  = 2 là trả sản phẩm còn lại là tiền
				String thuongtl_fk =  rs.getString("ctkm_fk");
				String DuyetKm_FK = rs.getString("DuyetKm_FK");
				double	thuong = rs.getDouble("thuong");
				
				String nppId = rs.getString("NPP_FK");
				
				if(donvi == 2) // tra sp
				{
					query = " select sanpham_fk, soluong from  DUYETTRAKHUYENMAI_KHACHHANG_SanPham where  duyetkm_fk = "+DuyetKm_FK+" and khId =" + khId;
					ResultSet rssp = db.get(query);
					while (rssp.next())
					{
						String sanpham_fk = rssp.getString("sanpham_fk");
						double soluong = rssp.getDouble("soluong");
						if(soluong > 0)
						{
							query = " Insert  donhang_ctkm_trakm(ap_dung, DuyetKm_FK,thuongtl_fk,donhangId, ctkmId, trakmId, soxuat, tonggiatri, spMa, soluong,sanpham_fk)  " +
									"\n select tkm.ap_dung, "+ DuyetKm_FK+ ",ctkm.thuongtl_fk,'" + dhId + "',ctkm.pk_seq ,tkm.pk_seq ,1, 0, sp.ma, "+soluong+",sp.pk_seq  " +
									"\n from ctkhuyenmai ctkm " +
									"\n inner join sanpham sp on sp.pk_seq = " + sanpham_fk +
									"\n inner join TRAKHUYENMAI tkm on tkm.ap_dung = 2 and tkm.loai = 3 " +
									"\n where ctkm.thuongtl_fk =  " + thuongtl_fk;
							if (db.updateReturnInt(query) <=0) 
							{
								db.getConnection().rollback();
								db.getConnection().setAutoCommit(true);
								return "Lỗi tích lũy:" + query;
								
							}
						}
					}
				}
				else
				{
					
					
					if(TienDonHang < thuong ) // neu tien don hang het thi ko ra nua
					{
						khongduochuong += "\n " +diengiai;
						continue;
					}
					TienDonHang = TienDonHang - thuong;
					if(thuong <=0)
						continue;
					query = "Insert into donhang_ctkm_trakm( ap_dung,DuyetKm_FK,thuongtl_fk,donhangId, ctkmId, trakmId, soxuat, tonggiatri) " +
					"\n select top (1) tkm.ap_dung , "+ DuyetKm_FK+ ",ctkm.thuongtl_fk,'" + dhId + "',ctkm.pk_seq ,tkm.pk_seq ,'" + 1 + "'," + thuong  +
					"\n from ctkhuyenmai ctkm " +
					"\n inner join TRAKHUYENMAI tkm on tkm.ap_dung = 2 and tkm.loai = 1 " +
					"\nwhere ctkm.thuongtl_fk =  " + thuongtl_fk;
					System.out.println(" query cttb = " + query);
					if (db.updateReturnInt(query) <=0) 
					{
						db.getConnection().rollback();
						db.getConnection().setAutoCommit(true);
						return"Lỗi tích lũy:" + query;
						
					}

				}

			}
			if(khongduochuong.length() > 0)
				this.msg =  "Tổng giá trị đơn hàng không đủ nhận tích lũy : " + khongduochuong;
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return "Lỗi tích lũy: ap tichluy Exception->" + e.getMessage();
		}
	}
}
