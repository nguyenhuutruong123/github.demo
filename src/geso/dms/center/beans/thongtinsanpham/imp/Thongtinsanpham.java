package geso.dms.center.beans.thongtinsanpham.imp;

import geso.dms.center.beans.thongtinsanpham.IThongtinsanpham;
import geso.dms.center.db.sql.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.Serializable;
import java.util.Hashtable;

public class Thongtinsanpham implements IThongtinsanpham, Serializable {
	private static final long serialVersionUID = -9217977546733690415L;
	String userId;
	String id;
	String masp;
	String maddt;
	String ten;
	String tenviettat;
	String matat;

	String dvdlId;
	String dvdlTen;
	ResultSet dvdl;

	String dvkdId;
	String dvkdTen;
	ResultSet dvkd;

	ResultSet ngh;// nganh hang
	String nganhhangId;

	String nhId;
	String nhTen;
	ResultSet nh;

	String clId;
	String clTen;
	ResultSet cl;

	String trangthai;
	String giablc;

	String msg;

	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;

	String[] nspIds;
	ResultSet nsp;

	ResultSet loaispRs;
	String loaispId;

	ResultSet nspSelected;

	String[] sl1;
	String[] dvdl1;
	String[] sl2;
	String[] dvdl2;

	String type;
	String[] spIds;
	String[] spStt;
	ResultSet spList;
	ResultSet spSelectedList; // update

	dbutils db;
	private String kl = "";
	private String dvkl = "";
	private String dvtt = "";
	private String tt = "";
	String isTrongtam = "";

	//Thông tin riêng sản phẩm IP
	private String dinhLuong;
	private String nhanHang;
	private String maySanXuat;
	private String khoGiay;
	private boolean isIP;
	
	public Thongtinsanpham(String[] param) {
		this.db = new dbutils();
		this.id = param[0];
		this.masp = param[1];
		this.maddt = param[2];
		this.ten = param[3];
		this.dvdlTen = param[4];
		this.dvkdTen = param[5];
		this.dvkdId = param[6];
		this.nhTen = param[7];
		this.nhId = param[8];
		this.clId = param[9];
		this.clTen = param[10];
		this.giablc = param[11];
		this.trangthai = param[12];
		this.dvdlId = param[13];
		this.ngaysua = param[14];
		this.nguoisua = param[15];
		this.sl1 = new String[5];
		this.sl2 = new String[5];
		this.dvdl1 = new String[5];
		this.dvdl2 = new String[5];
		this.type = param[16]; // default
		this.loaispId = "";
		this.tenviettat = "";
		this.msg = "";
		this.matat = "";
		this.nganhhangId = "";
		
		//Thông tin thêm cho sản phẩm IP
		this.nhanHang = "";
		this.dinhLuong = "";
		this.khoGiay = "";
		this.maySanXuat = "";
		this.isIP = false;
	}

	public Thongtinsanpham() {
		this.db = new dbutils();
		this.id = "";
		this.masp = "";
		this.maddt = "";
		this.ten = "";
		this.dvdlTen = "";
		this.dvkdTen = "";
		this.dvkdId = "";
		this.nhTen = "";
		this.nhId = "";
		this.clId = "";
		this.clTen = "";
		this.giablc = "";
		this.trangthai = "1";
		this.dvdlId = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.msg = "";
		this.type = "";
		this.loaispId = "";
		this.sl1 = new String[5];
		this.sl2 = new String[5];
		this.dvdl1 = new String[5];
		this.dvdl2 = new String[5];
		this.tenviettat = "";
		this.ngh = layNganhHang();
		this.nganhhangId = "";
		this.matat = "";
		
		//Thông tin thêm cho sản phẩm IP
		this.nhanHang = "";
		this.dinhLuong = "";
		this.khoGiay = "";
		this.maySanXuat = "";
		this.isIP = false;
	}
	
	public String getIsTrongtam() {
		return isTrongtam;
	}
	public void setIsTrongtam(String isTrongtam) {
		this.isTrongtam = isTrongtam;
	}
	public void setNganhHangId(String NganhHangId) {
		this.nganhhangId = NganhHangId;
	}

	public String getNganhHangId() {
		return this.nganhhangId;
	}

	private ResultSet layNganhHang() {
		ResultSet nganhhang;
		nganhhang = db.getScrol("select a.PK_SEQ, a.TEN from NGANHHANG a where TRANGTHAI=1 ");
		return nganhhang;
	}

	public ResultSet getNganhHang() {
		return this.ngh;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMasp() {
		return this.masp;
	}

	public void setMasp(String masp) {
		this.masp = masp;
	}

	public String getMaddt() {
		return this.maddt;
	}

	public void setMaddt(String maddt) {
		this.maddt = maddt;
	}

	public String getMaTat() {
		return this.matat;
	}

	public void setMaTat(String matat) {
		this.matat = matat;
	}

	public String getTen() {
		return this.ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getTenVietTat() {
		return this.tenviettat;
	}

	public void setTenVietTat(String tenviettat) {
		this.tenviettat = tenviettat;
	}

	public String getTrangthai() {
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}

	public String getDvdlId() {
		return this.dvdlId;
	}

	public void setDvdlId(String dvdlId) {
		this.dvdlId = dvdlId;
	}

	public String getDvdlTen() {
		return this.dvdlTen;
	}

	public void setDvdlTen(String dvdlTen) {
		this.dvdlTen = dvdlTen;
	}

	public ResultSet getDvdl() {
		return this.dvdl;
	}

	public void setDvdl(ResultSet dvdl) {
		this.dvdl = dvdl;
	}

	public String getDvkdId() {
		return this.dvkdId;
	}

	public void setDvkdId(String dvkdId) {
		this.dvkdId = dvkdId;
	}

	public String getDvkdTen() {
		return this.dvkdTen;
	}

	public void setDvkdTen(String dvkdTen) {
		this.dvkdTen = dvkdTen;
	}

	public String getNhId() {
		return this.nhId;
	}

	public void setNhId(String nhId) {
		this.nhId = nhId;
	}

	public String getNhTen() {
		return this.nhTen;
	}

	public void setNhTen(String nhTen) {
		this.nhTen = nhTen;
	}

	public String getClId() {
		return this.clId;
	}

	public void setClId(String clId) {
		this.clId = clId;
	}

	public String getClTen() {
		return this.clTen;
	}

	public void setClTen(String clTen) {
		this.clTen = clTen;
	}

	public String getNgaytao() {
		return this.ngaytao;

	}

	public void setNgaytao(String ngaytao) {
		this.ngaytao = ngaytao;
	}

	public String getNgaysua() {
		return this.ngaysua;

	}

	public void setNgaysua(String ngaysua) {
		this.ngaysua = ngaysua;
	}

	public String getNguoitao() {
		return this.nguoitao;
	}

	public void setNguoitao(String nguoitao) {
		this.nguoitao = nguoitao;
	}

	public String getNguoisua() {
		return this.nguoisua;
	}

	public void setNguoisua(String nguoisua) {
		this.nguoisua = nguoisua;
	}

	public String getMessage() {
		return this.msg;
	}

	public void setMessage(String msg) {
		this.msg = msg;
	}

	public void setDvkd(ResultSet dvkd) {
		this.dvkd = dvkd;
	}

	public ResultSet getDvkd() {
		return this.dvkd;
	}

	public void setNh(ResultSet nh) {
		this.nh = nh;
	}

	public ResultSet getNh() {
		return this.nh;
	}

	public void setCl(ResultSet cl) {
		this.cl = cl;
	}

	public ResultSet getCl() {
		return this.cl;
	}

	public void setGiablc(String giablc) {
		this.giablc = giablc;
	}

	public String getGiablc() {
		return this.giablc;
	}

	public void setNsp(ResultSet nsp) {
		this.nsp = nsp;
	}

	public ResultSet getNsp() {
		return this.nsp;
	}

	public Hashtable<Integer, String> getNspIds() {
		Hashtable<Integer, String> selected = new Hashtable<Integer, String>();
		if (this.nspIds != null) {
			int size = (this.nspIds).length;
			int m = 0;
			while (m < size) {
				selected.put(new Integer(m), this.nspIds[m]);
				m++;
			}
		} else {
			selected.put(new Integer(0), "null");
		}
		return selected;
	}

	public void setNspIds(String[] nspIds) {
		this.nspIds = nspIds;
	}

	public String[] getSl1() {
		return this.sl1;
	}

	public void setSl1(String[] sl1) {
		this.sl1 = sl1;
	}

	public String[] getDvdl1() {
		return this.dvdl1;
	}

	public void setDvdl1(String[] dvdl1) {
		this.dvdl1 = dvdl1;
	}

	public String[] getSl2() {
		return this.sl2;
	}

	public void setSl2(String[] sl2) {
		this.sl2 = sl2;
	}

	public String[] getDvdl2() {
		return this.dvdl2;
	}

	public void setDvdl2(String[] dvdl2) {
		this.dvdl2 = dvdl2;
	}

	public ResultSet createDvdlRS() {

		ResultSet dvdlRS = this.db
				.get("select pk_seq as dvdlId, donvi as dvdlTen from donvidoluong where trangthai='1' order by donvi");

		// //system.out.print("\nQuery cua ban: select pk_seq as dvdlId, donvi as dvdlTen from donvidoluong where trangthai='1' order by donvi \n");

		return dvdlRS;
	}

	private ResultSet createDvkdRS() {
		// ResultSet dvkdRS =
		// this.db.get("select pk_seq as dvkdId, donvikinhdoanh as dvkdTen from donvikinhdoanh where trangthai='1' ");
		// Khong biet sao nhung cau lenh nay lay ra bi trung --- them distinct
		// vao
		ResultSet dvkdRS = this.db
				.get("select distinct(a.pk_seq) as dvkdId, a.donvikinhdoanh as dvkdTen from donvikinhdoanh a,nhacungcap_dvkd b where a.pk_seq = b.DVKD_fk and b.checked ='1' and a.trangthai ='1' order by a.donvikinhdoanh");
		return dvkdRS;
	}

	private ResultSet createNhRS() {
		ResultSet nhRS;
		if (dvkdId.length() > 0) {
			nhRS = this.db
					.get("select pk_seq as nhId, ten as nhTen from nhanhang where trangthai='1' and dvkd_fk='" + this.dvkdId + "'");
		} else {
			nhRS = this.db
					.get("select pk_seq as nhId, ten as nhTen from nhanhang where trangthai='1'");
		}
		return nhRS;

	}

	private ResultSet createClRS() {
		ResultSet clRS;
		String query = "select distinct a.pk_seq as clId, a.ten as clTen from chungloai a, nhanhang_chungloai b where trangthai='1' and a.pk_seq = b.cl_fk ";

		if (this.nhId.length() > 0) {
			query = query + "  and b.nh_fk = '" + this.nhId + "'";

		}
		clRS = this.db.get(query);
		return clRS;

	}

	private void createNspRS() {

		if (this.id.length() > 0) {
			this.nspSelected = this.db
					.get("select a.nsp_fk as nspId, b.TEN as nspTen, b.diengiai as diengiai from nhomsanpham_sanpham a inner join nhomsanpham b on a.nsp_fk = b.pk_seq where a.sp_fk = '"
							+ this.id + "'");
			String query = "select pk_seq as nspId, ten as nspTen, diengiai as diengiai from nhomsanpham where trangthai='1' and NHOMSANPHAM.loainhom='0' and NHOMSANPHAM.TYPE='0'  order by ten"; 
			// and pk_seq not in(select nsp_fk from nhomsanpham_sanpham where sp_fk = '" + this.id + "') order by ten";
			this.nsp = this.db.get(query);
			//system.out.println("khoi tao nsp :" + query);
		} else {
			this.nsp = this.db
					.get("select pk_seq as nspId, ten as nspTen, diengiai as diengiai from nhomsanpham where trangthai='1' and  NHOMSANPHAM.loainhom='0' and NHOMSANPHAM.TYPE='0' order by ten");
		}
	}

	private void createSpIds() {
		ResultSet rs = db.get("select sanpham_fk from Bundle_Sanpham where bundle_fk = '"+ this.id + "'");
		if (rs != null) {
			try {
				String str = "";
				while (rs.next()) {
					str = str + rs.getString("sanpham_fk") + ",";
				}
				if (str.length() > 0) {
					str = str.substring(0, str.length() - 1);
					this.spIds = str.split(",");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void createNspIds() {
		ResultSet rs = db
				.get("select nsp_fk from nhomsanpham_sanpham where sp_fk = '"
						+ this.id + "'");
		if (rs != null) {
			try {
				String str = "";
				while (rs.next()) {
					str = str + rs.getString("nsp_fk") + ",";
				}
				if (str.length() > 0) {
					str = str.substring(0, str.length() - 1);
					this.nspIds = str.split(",");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void CreateRS() {
		this.dvdl = createDvdlRS();
		this.dvkd = createDvkdRS();
		this.nh = createNhRS();
		this.cl = createClRS();
		this.ngh = layNganhHang();
		this.loaispRs = db.get("select pk_seq as loaiId, ma, ten as loaiTen from erp_loaisanpham");

		this.createNspRS();
		this.createSpList();
	}

	boolean masanpham() {
		String sql;
		if (this.id.length() == 0) {
			sql = "select count(*) as num from sanpham where ma ='" + this.masp
					+ "'";
		} else {
			sql = "select count(*) as num from sanpham where pk_seq <> '"
					+ this.id + "' and ma ='" + this.masp + "'";
		}
		//system.out.println("thong tin sp:" + sql);
		ResultSet rs = db.get(sql);
		if (rs != null)
			try {
				rs.next();
				if (rs.getString("num").equals("0"))
					return false;
			} catch (SQLException e) {
				return false;
			}
		return true;
	}

	public boolean UpdateSp() {
		try {

			/*if (masanpham()) {
				this.msg = "Ma san pham da trung roi";
				return false;
			}*/

			this.db.getConnection().setAutoCommit(false);
			this.ngaysua = getDateTime();
			this.nguoisua = this.userId;


			if (nganhhangId == null || nganhhangId.trim().length() <= 0) {
				nganhhangId = null;
			}

			if (this.nhId == null || this.nhId.trim().length() <= 0) {
				this.nhId = null;
			}

			String cloai = this.clId.length() > 0 ? this.clId : "null";

			String command = 
				" update sanpham set trangthai = ? , isTrongTam = ?, ngaysua = ?, nguoisua = ?, " +
				" dvkd_fk = ?, type = ?, trongluong = ?,tenviettat = ?, " +
				" NganhHang_FK = ?, nhanhang_fk = ?, chungloai_fk = ? , dvdl_fk = ?" +
				" where pk_seq = ? ";
			Object[] data;
			data = new Object[] {trangthai, isTrongtam, this.ngaysua, this.userId, 
							     this.dvkdId, this.type, this.kl, this.tenviettat, 
							     this.nganhhangId, this.nhId, this.clId, this.dvdlId, this.id};
			dbutils.viewQuery(command, data);
			if( this.db.updateQueryByPreparedStatement(command, data) !=1) 
			{
				this.msg = "1. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ Admin để xử lý";
				this.db.getConnection().rollback();
				return false;
			}

			if (this.type != null && this.type.equals("1")) 
			{
				command = "delete from Bundle_Sanpham where bundle_fk='"+ this.id + "'";
				//system.out.println("Command: "+command);
				if (!this.db.update(command)) 
				{
					this.msg = "2. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ Admin để xử lý";
					this.db.getConnection().rollback();
					return false;
				}
			}

			String query = "";
			/*
			query = "select count(sanpham_fk) as num from nhapp_kho where sanpham_fk ="+this.id;
			ResultSet rs = this.db.get(query);
			rs.next();
			if (rs.getString("num").equals("0")) 
			{
				query = 
				" select distinct kho_fk, npp_fk, kbh_fk from nhapp_kho " +
				" where EXISTS ( select 1 from nhaphanphoi a,nhapp_nhacc_donvikd b,nhacungcap_dvkd c " +
				" where a.pk_seq=b.npp_fk and b.ncc_dvkd_fk= c.pk_seq and c.dvkd_fk = '"+ this.dvkdId +"' AND A.PK_SEQ = NHAPP_KHO.NPP_FK ) " +
				" order by kho_fk, npp_fk, kbh_fk ";
				ResultSet rs1 = this.db.get(query);
				while (rs1.next()) {
					command = "insert into nhapp_kho(KHO_fk,NPP_FK,SANPHAM_FK,SOLUONG,BOOKED,AVAILABLE,GIAMUA,KBH_FK)  values('" + rs1.getString("kho_fk") + "','" + rs1.getString("npp_fk") + "', '" + this.id + "', '0','0','0','0','" + rs1.getString("kbh_fk") + "')";
					if (!this.db.update(command)) {
						this.msg = "4. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ Admin để xử lý";
						this.db.getConnection().rollback();
						return false;
					}
				}
				rs1.close();
			}*/

			command = "delete from quycach where sanpham_fk='" + this.id + "'";
			if (!(this.db.update(command))) 
			{
				this.msg = "5. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ Admin để xử lý";
				this.db.getConnection().rollback();
				return false;
			}

			boolean error = true;
			for (int i = 0; i < 5; i++) {
				if ( sl1[i].length() > 0 && Double.parseDouble(sl1[i])==0)
				{
					this.msg = " Không được nhập quy cách bằng 0";			
					this.db.getConnection().rollback();
					return false;
				}
				if (sl2[i].length() > 0 && Double.parseDouble(sl2[i])==0)
				{
					this.msg = " Không được nhập quy cách bằng 0";			
					this.db.getConnection().rollback();
					return false;
				}
				if ((sl1[i].length() > 0) && (dvdl1[i].length() > 0)
						&& (sl2[i].length() > 0) && (dvdl2[i].length() > 0)) {
					command = "\n insert into quycach(sanpham_fk, dvdl1_fk, soluong1, dvdl2_fk, soluong2) " +
						"\n select "+this.id+",?,?,?,? ";
					data = dbutils.setObject(dvdl1[i],sl1[i],dvdl2[i],sl2[i]);
					if (db.update_v2(command, data) < 0) {
						this.msg = "6. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ Admin để xử lý";
						this.db.getConnection().rollback();
						return false;
					}

					if (dvdl2[i].equals("100018"))
						error = false;
				}
			}

			query = " insert nhanvien_sanpham(nhanvien_fk, sanpham_fk) " + " select distinct nhanvien_fk,'" + this.id + "' as sanpham_fk from phanquyen " +
					" where nhanvien_fk not in (select nhanvien_fk from nhanvien_sanpham where sanpham_fk='" + this.id + "')  " + 
					" and nhanvien_fk in (select pk_seq from nhanvien where trangthai = '1'  and phanloai='2' )";
			if (!db.update(query)) {
				//this.msg = query;
				this.msg = "7. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ Admin để xử lý";
				geso.dms.center.util.Utility.rollback_throw_exception(this.db);
				return false;
			}

			query = "\n UPDATE SP SET  TIMKIEM = upper(dbo.ftBoDau(MA))+ '-' + upper(dbo.ftBoDau(isnull(SP.BARCODE,''))) " + 
			"\n 	+ '-' + upper(dbo.ftBoDau(isnull(SP.ten,''))) " +
			"\n 	+ '-' + upper(dbo.ftBoDau(isnull(tenviettat,''))) + '-' + upper(dbo.ftBoDau(isnull(DVDL.DONVI,''))) " + 
			"\n FROM SANPHAM SP INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ=SP.DVDL_FK " +
			"\n where SP.PK_SEQ = "+this.id;
			if (!db.update(query)) {
				//this.msg = query;
				this.msg = "8. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ Admin để xử lý";
				this.db.getConnection().rollback();
				return false;
			}

			/*query = 
				" insert into nhapp_kho(npp_fk,kbh_fk,KHO_FK,SANPHAM_FK,SOLUONG,BOOKED,AVAILABLE) "+
				" select npp.PK_SEQ, kenh.PK_SEQ as kbhId ,kho.PK_SEQ as khoId,sp.PK_SEQ as spId,0 "+ 
				" as SoLuong,0 as Booked,0 as avail " +
				" from KHO kho,SANPHAM sp, KENHBANHANG kenh, NHAPHANPHOI npp "+ 
				" where not exists "+ 
				" ( select 1 from NHAPP_KHO a where a.KHO_FK=kho.PK_SEQ and a.KBH_FK=kenh.PK_SEQ and a.SANPHAM_FK=sp.PK_SEQ and a.npp_fk =npp.pk_Seq ) " +
				" and kenh.PK_SEQ in (select kbh_fk from NHAPP_KBH where NPP_FK=npp.PK_SEQ) "+   
				" and sp.DVKD_FK in ( select b.DVKD_FK from NHAPP_NHACC_DONVIKD a inner join NHACUNGCAP_DVKD b on a.NCC_DVKD_FK=b.PK_SEQ and a.NPP_FK=npp.PK_SEQ ) "; 
			if(!(db.update(query)))
			{
				this.msg = "9. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ Admin để xử lý.";
				db.getConnection().rollback();
				return false;
			}*/

			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(false);

		} catch (Exception e) {
			e.printStackTrace();
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			this.msg = "Lỗi ngoại lệ cập nhật, vui lòng liên hệ Admin để xử lý.";
			return false;
		}
		return true;
	}

	public boolean CreateSp() 
	{
		String command = "";
		try {
			if (masanpham()) {
				this.msg = "Mã sản phẩm đã bị trùng!";
				return false;
			}

			if (nganhhangId == null || nganhhangId.trim().length() <= 0) {
				nganhhangId = null;
			}

			if (this.nhId == null || this.nhId.trim().length() <= 0) {
				this.nhId = null;
			}
			
			this.db.getConnection().setAutoCommit(false);
			this.ngaysua = getDateTime();
			this.nguoisua = this.userId;

			String cloai = this.clId.length() > 0 ? this.clId : null;
 
			command = " insert into sanpham(isTrongTam,ma,ten,ngaytao,ngaysua,nguoitao,nguoisua,dvdl_fk,trangthai," +
					  " dvkd_fk,nhanhang_fk,chungloai_fk,type,trongluong, tenviettat, NganhHang_FK) "+
					  " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			Object[] data;
			data= new Object[] { isTrongtam, this.masp, this.ten, this.ngaysua, this.ngaysua, this.userId, this.userId, this.dvdlId,
					this.trangthai, this.dvkdId, this.nhId, cloai, this.type, this.kl, this.tenviettat, this.nganhhangId };

			if(this.db.updateQueryByPreparedStatement(command, data) != 1) 
			{
				dbutils.viewQuery(command, data);
				this.msg = "1. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ Admin để xử lý.";
				this.db.getConnection().rollback();
				return false;
			}
			this.id = db.getPk_seq();

			command = " INSERT INTO NHANVIEN_SANPHAM (Nhanvien_fk,Sanpham_fk) SELECT PK_SEQ AS NHANVIEN_FK,? FROM NHANVIEN WHERE PHANLOAI = 2 AND TRANGTHAI = 1 ";
			data= new Object[] {this.id};
			if (this.db.updateQueryByPreparedStatement(command, data) < 0) {
				dbutils.viewQuery(command, data);
				this.msg = "2. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ Admin để xử lý.";
				this.db.getConnection().rollback();
				return false;
			}

			boolean error = true;
			for (int i = 0; i < 5; i++) 
			{
				if (!(sl1[i] == null)) 
				{
					if ( sl1[i].length() > 0 && Double.parseDouble(sl1[i])==0)
					{
						this.msg = " Không được nhập quy cách bằng 0";			
						this.db.getConnection().rollback();
						return false;
					}
					if (sl2[i].length() > 0 && Double.parseDouble(sl2[i])==0)
					{
						this.msg = " Không được nhập quy cách bằng 0";			
						this.db.getConnection().rollback();
						return false;
					}
					if ((sl1[i].length() > 0) & (dvdl1[i].length() > 0) & (sl2[i].length() > 0) & (dvdl2[i].length() > 0)) 
					{
						 
						command = "insert into quycach(sanpham_fk, dvdl1_fk, soluong1, dvdl2_fk, soluong2) values(?,?,?,?,?)";
						data= new Object[] { this.id, this.dvdl1[i] , this.sl1[i] , this.dvdl2[i], this.sl2[i] };
						if (this.db.updateQueryByPreparedStatement(command, data) < 0) 
						{
							dbutils.viewQuery(command, data);
							this.msg = "3. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ Admin để xử lý.";
							this.db.getConnection().rollback();
							return false;
						}
						if (dvdl2[i].equals("100018")) { error = false; }
					}
				}
			}
 
			if (!(this.nspIds == null)) 
			{
				int size = nspIds.length;
				for (int i = 0; i < size; i++) 
				{
					if (this.nspIds[i] != null) 
					{
						command = "insert into nhomsanpham_sanpham(sp_fk, nsp_fk) values(?,?)";
						data= new Object[] { this.id , nspIds[i] };

						if( this.db.updateQueryByPreparedStatement(command, data) < 0 ) 
						{
							dbutils.viewQuery(command, data);
							this.msg = "4. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ Admin để xử lý";
							this.db.getConnection().rollback();
							return false;
						}
					}
				}
			}
			
			command = " insert into bgmuanpp_sanpham (bgmuanpp_fk, sanpham_fk, giamuanpp, trangthai, giamua_sauck)  " +
					  " select pk_seq bgmuanpp_fk, ?, '0', '0', null from banggiamuanpp where dvkd_fk = ? ";
			data= new Object[] { this.id, this.dvkdId };
			if ( this.db.updateQueryByPreparedStatement(command, data) < 0 ) 
			{
				dbutils.viewQuery(command, data);
				this.msg = "6. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ Admin để xử lý.";
				this.db.getConnection().rollback();
				return false;
			}
			
			command = " insert into banggiast_sanpham ( BGST_FK, SANPHAM_FK, GIASIEUTHI ) " +
					  " select pk_seq BGST_FK, '"+ this.id +"' SANPHAM_FK, '0' from banggiasieuthi where dvkd_fk = '"+ this.dvkdId +"' ";
			if( !this.db.update(command) ) 
			{
				this.msg = "7. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ Admin để xử lý.";
				this.db.getConnection().rollback();
				return false;
			}
		 
			// INSERT KHO -----------------------
			/*command = " insert into nhapp_kho(kho_fk,npp_fk,sanpham_fk,soluong,booked,available,giamua,kbh_fk) " +
					  " select distinct kho_fk, npp_fk, ?, '0','0','0','0', kbh_fk from nhapp_kho " +
					  " where npp_fk in (select distinct a.pk_seq as nppId from nhaphanphoi a, nhapp_nhacc_donvikd b, nhacungcap_dvkd c " +
					  " where a.pk_seq=b.npp_fk and b.ncc_dvkd_fk= c.pk_seq and c.dvkd_fk = ?) " +
					  " and kbh_fk in (select pk_seq from kenhbanhang where trangthai = '1') " + 
					  " order by kho_fk, npp_fk, kbh_fk ";
			data= new Object[] { this.id, this.dvkdId };
			if( this.db.updateQueryByPreparedStatement(command, data) < 0 ) 
			{
				dbutils.viewQuery(command, data);
				this.msg = "6. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ Admin để xử lý. ";
				this.db.getConnection().rollback();
				return false;
			}*/

			String query = "\n insert erp_khott_sanpham(khott_fk, sanpham_fk, giaton, soluong, booked, available) " +
			"\n values('100000', '"	+ this.id + "', '0', '0', '0', '0')";
			if (!this.db.update(query)) 
			{
				this.msg = "7. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ Admin để xử lý. ";
				this.db.getConnection().rollback();
				return false;
			}

			/*query = " insert nhanvien_sanpham(nhanvien_fk, sanpham_fk) "	+ 
					" select distinct nhanvien_fk,'"+ this.id	+ "' as sanpham_fk from phanquyen " +
					" where nhanvien_fk not in (select nhanvien_fk from nhanvien_sanpham where sanpham_fk='"+ this.id	+ "') " + 
					" and nhanvien_fk in (select pk_seq from nhanvien where trangthai = '1' and phanloai='2') ";
			//system.out.println("query : " + query);
			if (!db.update(query)) {
				this.msg = "8. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ Admin để xử lý. ";
				this.db.getConnection().rollback();
				return false;
			}*/

			query = "\n UPDATE SP SET TEN=RTRIM(LTRIM(TEN)),TIMKIEM = upper(dbo.ftBoDau(MA))+ '-'+ upper(dbo.ftBoDau(isnull(SP.BARCODE,''))) " +
			"\n 	+ '-'+ upper(dbo.ftBoDau(isnull(SP.ten,''))) +'-'+ upper(dbo.ftBoDau(isnull(tenviettat,''))) " +
			"\n		+'-'+ upper(dbo.ftBoDau(isnull(DVDL.DONVI,''))) " +
			"\n FROM SANPHAM SP INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ=SP.DVDL_FK " + " where SP.PK_SEQ = " + this.id;
			if (!db.update(query)) 
			{
				this.msg = "9. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ Admin để xử lý. ";
				this.db.getConnection().rollback();
				return false;
			}

			/*query = "\n	insert into nhapp_kho(npp_fk,kbh_fk,KHO_FK,SANPHAM_FK,SOLUONG,BOOKED,AVAILABLE)    "+
			"\n	select  npp.PK_SEQ, kenh.PK_SEQ as kbhId ,kho.PK_SEQ as khoId,sp.PK_SEQ as spId,0 "+ 
			"\n	as SoLuong,0 as Booked,0 as avail  from KHO kho,SANPHAM sp, "+
			"\n		KENHBANHANG kenh ,NHAPHANPHOI npp "+ 
			"\n		where not exists "+ 
			"\n	( 	  "+
			"\n		select * from  NHAPP_KHO a 	 "+
			"\n		where a.KHO_FK=kho.PK_SEQ and a.KBH_FK=kenh.PK_SEQ 	 "+
			"\n		and a.SANPHAM_FK=sp.PK_SEQ and a.npp_fk =npp.pk_Seq    "+
			"\n	) and kenh.PK_SEQ in (select kbh_fk from NHAPP_KBH where NPP_FK=npp.PK_SEQ) "+   
			"\n	and sp.DVKD_FK  in (select b.DVKD_FK from NHAPP_NHACC_DONVIKD a   "+
			"\n	inner join NHACUNGCAP_DVKD b on a.NCC_DVKD_FK=b.PK_SEQ and a.NPP_FK=npp.PK_SEQ)  "; 
			if(!(db.update(query)))
			{
				this.msg = "10. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ Admin để xử lý. ";
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			}*/
				
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		} catch (Exception e) {
			e.printStackTrace();
			this.msg = "Lỗi ngoại lệ tạo mới, vui lòng liên hệ Admin để xử lý.";
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			return false;
		}
		return true;
	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public void init() {
		try {
			String query = "\n select isnull(a.isTrongtam,0)isTrongtam,a.pk_seq as id, a.ma as masp, a.ma_ddt as maddt, " +
					"\n a.ten as tensp, isnull(a.tenviettat, a.ten) as tenviettat, b.donvikinhdoanh as dvkd, b.pk_seq as dvkdId, " +
					"\n c.pk_seq as clId, c.ten as chungloai, e.pk_seq as nhId, d.donvi, e.ten as nhanhang, d.pk_seq as dvdlId, " +
					"\n a.trangthai, isnull(a.type,'' ) as type, a.trongluong, a.thetich, a.loaisanpham_fk, isnull(a.MaTat,'') as MaTat, " +
					"\n a.NganhHang_FK, isnull(nh.TEN, '') as nhanHangSp, isnull(a.DinhLuong, '') as dinhLuong, " +
					"\n isnull(a.KhoGiay, '') as khoGiay, isnull(a.TENMAYSANXUAT, '') as tenMaySanXuat, " +
							"\n	(case dv.donViKinhDoanh when 'IP' then 1 else 0 end) as isIP ";//f.giabanlechuan as giablc,
			query += "\n from sanpham a left join donvikinhdoanh b on a.dvkd_fk = b.pk_seq " +
					"\n left join chungloai c on a.chungloai_fk = c.pk_seq " +
					"\n left join donvidoluong d on a.dvdl_fk = d.pk_seq left join nhanhang e on a.nhanhang_fk = e.pk_seq " +
					"\n	left join NHANHANG nh on nh.PK_SEQ = a.NHANHANG_FK " +
					"\n	inner join DONVIKINHDOANH dv on dv.PK_SEQ = a.DVKD_FK ";
			//query = query + " left join banggiablc_sanpham f on a.pk_seq = f.sanpham_fk left join banggiabanlechuan g on f.bgblc_fk = g.pk_seq ";
			
			query += "\n where a.pk_seq = '" + this.id + "'";
			//system.out.print("Init: "+query);
			ResultSet rs;
			try {
				rs = this.db.get(query);
				while (rs.next()) {

					//Thông tin thêm cho sản phẩm IP
					isTrongtam = rs.getString("isTrongtam");
					this.nhanHang = rs.getString("nhanHangSp");
					this.dinhLuong = rs.getString("dinhLuong");
					this.khoGiay = rs.getString("khoGiay");
					this.maySanXuat = rs.getString("tenMaySanXuat");
					this.isIP = rs.getBoolean("isIP");

					this.id = rs.getString("id");
					this.masp = rs.getString("masp");
					this.maddt = rs.getString("maddt");
					this.ten = rs.getString("tensp");
					this.tenviettat = rs.getString("tenviettat");
					this.matat = rs.getString("MaTat");
					this.nganhhangId = rs.getString("NganhHang_FK");
					
					this.nhId = "";
					this.clId = "";
					this.trangthai = rs.getString("trangthai");
					this.type = rs.getString("type");
					this.dvdlId = "";
					
					this.dvkdId = "";
					if (rs.getString("dvkdId") != null) {
						this.dvkdId = rs.getString("dvkdId");
					}
					
					if (rs.getString("nhId") != null) {
						this.nhId = rs.getString("nhId");
					}
					
					if (rs.getString("clId") != null) {
						this.clId = rs.getString("clId");
					}

					if (rs.getString("dvdlId") != null) {
						this.dvdlId = rs.getString("dvdlId");
					}

					if (rs.getString("trongluong") != null)
						this.kl = rs.getString("trongluong");

					if (rs.getString("thetich") != null)
						this.tt = rs.getString("thetich");

					if (rs.getString("loaisanpham_fk") != null)
						this.loaispId = rs.getString("loaisanpham_fk");
				}
				rs.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}

			query = "select soluong1 as sl1, dvdl1_fk as dvdl1, soluong2 as sl2, dvdl2_fk as dvdl2 from quycach where sanpham_fk = '"+ id + "'";
			// this.msg =query;
			rs = this.db.get(query);
			int i = 0;
			while (rs.next()) {
				this.sl1[i] = rs.getString("sl1");
				this.dvdl1[i] = rs.getString("dvdl1");

				//system.out.print("Don vi do luong la: " + this.sl1[i] + "\n");

				this.sl2[i] = rs.getString("sl2");
				this.dvdl2[i] = rs.getString("dvdl2");
				i++;
			}
			rs.close();
			CreateRS();
			createNspIds();
			createSpIds();

		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
	}

	public void DBClose() {

		try {
			if (this.nh != null)
				this.nh.close();
			if (this.cl != null)
				this.cl.close();
			if (this.dvdl != null)
				this.dvdl.close();
			if (this.dvkd != null)
				this.dvkd.close();
			if (this.nsp != null)
				this.nsp.close();
			this.db.shutDown();
		} catch (java.sql.SQLException e) {
		}
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

	public ResultSet getSanphamRs() {
		return this.spList;
	}

	public void setSanphamRs(ResultSet spRs) {
		this.spList = spRs;
	}

	public Hashtable<Integer, String> getSpIds() {
		Hashtable<Integer, String> selected = new Hashtable<Integer, String>();
		if (this.spIds != null) {
			int size = (this.spIds).length;
			int m = 0;
			while (m < size) {
				selected.put(new Integer(m), this.spIds[m]);
				m++;
			}
		} else {
			selected.put(new Integer(0), "null");
		}
		return selected;
	}

	public void setSpIds(String[] spIds) {
		this.spIds = spIds;
	}

	private void createSpList() {
		String query = "\n select pk_seq, ma as spMa, ten as spTen, isnull(bdsp.soluong,'0') as soluong from sanpham sp left join bundle_sanpham bdsp on sp.pk_seq = bdsp.sanpham_fk  where dvkd_fk = '"+ this.dvkdId + "'";
		if (this.type.equals("1")) {
			if (this.nhId.length() > 0)
				query = query + "\n and nhanhang_fk='" + this.nhId + "'";
			if (this.clId.length() > 0)
				query = query + "\n and chungloai_fk='" + this.clId + "'";

			if (this.id.length() > 0) {
				query += " and pk_seq in(select sanpham_fk from Bundle_Sanpham where bundle_fk='" + this.id + "')";
				this.spSelectedList = this.db.get(query);
				query = query + " and pk_seq not in (select sanpham_fk from Bundle_Sanpham where bundle_fk= '" + this.id + "')";
			}

			//system.out.print("createSpList: " + query);
			this.spList = db.get(query);
		}
	}

	public void setNspSelected(ResultSet nsp) {
		this.nspSelected = nsp;
	}

	public ResultSet getNspSelected() {
		return this.nspSelected;
	}

	public ResultSet getSanphamSelectedRs() {
		return this.spSelectedList;
	}

	public void setSanphamSelectedRs(ResultSet spRs) {
		this.spSelectedList = spRs;
	}

	public void init2() {
		try {
			String query = "select a.pk_seq as id, a.ma as masp, a.ma_ddt as maddt, a.ten as tensp, isnull(a.tenviettat, a.ten) as tenviettat, b.donvikinhdoanh as dvkd, b.pk_seq as dvkdId, c.pk_seq as clId, c.ten as chungloai, e.pk_seq as nhId, d.donvi, e.ten as nhanhang, d.pk_seq as dvdlId, a.trangthai, a.type, isnull(a.MaTat,'') as MaTat, a.NganhHang_FK ";//f.giabanlechuan as giablc,
			query = query + " from sanpham a inner join donvikinhdoanh b on a.dvkd_fk = b.pk_seq left join chungloai c on a.chungloai_fk = c.pk_seq left join donvidoluong d on a.dvdl_fk = d.pk_seq left join nhanhang e on a.nhanhang_fk = e.pk_seq ";
			//query = query + " inner join banggiablc_sanpham f on a.pk_seq = f.sanpham_fk inner join banggiabanlechuan g on f.bgblc_fk = g.pk_seq ";
			query = query + " where a.pk_seq = '" + this.id + "'";

			ResultSet rs = this.db.get(query);

			rs.next();
			this.id = rs.getString("id");
			this.masp = rs.getString("masp");
			this.maddt = rs.getString("maddt");
			this.ten = rs.getString("tensp");
			this.tenviettat = rs.getString("tenviettat");
			this.matat = rs.getString("MaTat");
			this.nganhhangId = rs.getString("NganhHang_FK");

			if (rs.getString("dvkdId") == null) {
				this.dvkdId = "";
			} else {
				this.dvkdId = rs.getString("dvkdId");
			}
			if (rs.getString("nhId") == null) {
				this.nhId = "";
			} else {
				this.nhId = rs.getString("nhId");
			}

			if (rs.getString("clId") == null) {
				this.clId = "";
			} else {
				this.clId = rs.getString("clId");
			}

			this.giablc = rs.getString("giablc");
			this.trangthai = rs.getString("trangthai");
			this.type = rs.getString("type");

			if (rs.getString("dvdlId") == null) {
				this.dvdlId = "";
			} else {
				this.dvdlId = rs.getString("dvdlId");
			}

			rs.close();

			query = "select soluong1 as sl1, dvdl1_fk as dvdl1, soluong2 as sl2, dvdl2_fk as dvdl2 from quycach where sanpham_fk = '" + id + "'";
			// this.msg =query;
			rs = this.db.get(query);
			int i = 0;
			while (rs.next()) {
				this.sl1[i] = rs.getString("sl1");
				this.dvdl1[i] = rs.getString("dvdl1");
				this.sl2[i] = rs.getString("sl2");
				this.dvdl2[i] = rs.getString("dvdl2");
				i++;
			}
			rs.close();

			this.dvdl = createDvdlRS();

			this.dvkd = createDvkdRS();
			this.nh = createNhRS();
			this.cl = createClRS();

			this.createNspRS2();
			this.createSpList2();

			createNspIds();
			createSpIds();

		} catch (java.sql.SQLException e) {
		}
	}

	private void createSpList2() {
		String query = "select pk_seq, ma as spMa, ten as spTen from sanpham where pk_seq in(select sanpham_fk from Bundle_Sanpham where bundle_fk='" + this.id + "')";
		this.spSelectedList = this.db.get(query);
	}

	private void createNspRS2() {
		this.nspSelected = this.db
				.get("select a.nsp_fk as nspId, b.TEN as nspTen, b.diengiai as diengiai from nhomsanpham_sanpham a inner join nhomsanpham b on a.nsp_fk = b.pk_seq where a.sp_fk = '" + this.id + "'");
	}

	public void setKL(String kl) {
		this.kl = kl;
	}

	public void setDVKL(String dvkl) {
		this.dvkl = dvkl;
	}

	public void setDVTT(String dvtt) {
		this.dvtt = dvtt;
	}

	public void setTT(String tt) {
		this.tt = tt;
	}

	public String getKL() {
		return this.kl;
	}

	public String getDVKL() {
		return this.dvkl;
	}

	public String getDVTT() {
		return this.dvtt;
	}

	public String getTT() {
		return this.tt;
	}

	public ResultSet getLoaiSpRs() {
		return this.loaispRs;
	}

	public void setLoaiSpRs(ResultSet spRs) {
		this.loaispRs = spRs;
	}

	public String getLoaiSpId() {
		return this.loaispId;
	}

	public void setLoaiSpId(String loaisp) {
		this.loaispId = loaisp;
	}
	
	public String getDinhLuong() {
		return dinhLuong;
	}

	public void setDinhLuong(String dinhLuong) {
		this.dinhLuong = dinhLuong;
	}

	public String getNhanHang() {
		return nhanHang;
	}

	public void setNhanHang(String nhanHang) {
		this.nhanHang = nhanHang;
	}

	public String getMaySanXuat() {
		return maySanXuat;
	}

	public void setMaySanXuat(String maySanXuat) {
		this.maySanXuat = maySanXuat;
	}

	public String getKhoGiay() {
		return khoGiay;
	}

	public void setKhoGiay(String khoGiay) {
		this.khoGiay = khoGiay;
	}
	
	public boolean getIsIP() {
		return isIP;
	}

	public void setIsIP(boolean isIP) {
		this.isIP = isIP;
	}
}
