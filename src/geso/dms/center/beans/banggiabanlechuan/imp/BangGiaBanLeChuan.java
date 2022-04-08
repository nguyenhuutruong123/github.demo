package geso.dms.center.beans.banggiabanlechuan.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import geso.dms.center.beans.banggiabanlechuan.IBangGiaBanLeChuan;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

public class BangGiaBanLeChuan implements IBangGiaBanLeChuan {
	String userId;
	String id;

	String ten;

	String chietKhau;

	String[] nppIdCks = new String[0];
	String[] nppChietKhaus = new String[0];

	public String getChietKhau() {
		return chietKhau;
	}

	public void setChietKhau(String chietKhau) {
		this.chietKhau = chietKhau;
	}

	String dvkdId;
	ResultSet dvkdRs;

	String kbhId;
	ResultSet kbhRs;

	String nppId;
	ResultSet nppRs;

	String trangthai;
	String msg;

	dbutils db;
	Utility util;

	NumberFormat formater = new DecimalFormat("##,###,###");
	NumberFormat formater2 = new DecimalFormat("##,###,###.####");

	public BangGiaBanLeChuan() {
		this.userId = "";
		this.id = "";
		this.ten = "";
		this.dvkdId = "";
		this.kbhId = "";
		this.trangthai = "1";
		this.msg = "";
		this.nppId = "";
		this.tungay = "";
		this.chietKhau = "";
		this.kvId = "";
		this.vungId = "";
		this.db = new dbutils();
		this.util = new Utility();
	}

	public BangGiaBanLeChuan(String id) {
		this.userId = "";
		this.id = id;
		this.kbhId = "";
		this.dvkdId = "";
		this.trangthai = "1";
		this.msg = "";
		this.nppId = "";
		this.tungay = "";
		this.chietKhau = "";
		this.kvId = "";
		this.vungId = "";
		this.db = new dbutils();
		this.util = new Utility();
	}

	public String getId() {
		return this.id;
	}

	public void setId(String Id) {
		this.id = Id;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void init() 
	{
		String query = 
				"select TEN, DVKD_FK, KENH_FK, TRANGTHAI,TuNgay,ChietKhau, "+ 
						"		 (select REPLACE((sELECT distinct xx.NPP_FK as [data()]  "+ 
						"		FROM BangGiaBanLeChuan_NPP xx  where xx.BangGiaBanLeChuan_FK=a.PK_SEQ "+ 
						"		FOR XML PATH('') ) ,' ',',')) as nppId, "+ 
						"		 (select REPLACE((sELECT distinct xx.khachhang_fk as [data()]  "+ 
						"		FROM BangGiaBanLeChuan_KhachHang xx  where xx.BangGiaBanLeChuan_FK=a.PK_SEQ "+ 
						"		FOR XML PATH('') ) ,' ',',')) as kh ,"+
						"		 (select REPLACE((sELECT distinct xx.VUNG_FK as [data()] "+ 
						"		FROM BangGiaBanLeChuan_VUNG xx  where xx.BangGiaBanLeChuan_FK=a.PK_SEQ "+ 
						"		FOR XML PATH('') ) ,' ',',')) as vungId, "+ 
						"		 (select REPLACE((sELECT distinct xx.KHUVUC_FK as [data()] "+ 
						"		FROM BangGiaBanLeChuan_KHUVUC xx  where xx.BangGiaBanLeChuan_FK=a.PK_SEQ "+ 
						"		FOR XML PATH('') ) ,' ',',')) as kvId "+ 
						" from BangGiaBanLeChuan a   "+ 
						" where PK_SEQ = '"+ this.id + "'";
		ResultSet rs = db.get(query);
		try {
			while (rs.next()) 
			{
				this.ten = rs.getString("TEN");
				this.dvkdId = rs.getString("DVKD_FK") == null ? "" : rs.getString("DVKD_FK");
				this.kbhId = rs.getString("KENH_FK") == null ? "" : rs.getString("KENH_FK");
				this.trangthai = rs.getString("TRANGTHAI");
				this.tungay = rs.getString("TuNgay") == null ? "" : rs.getString("TuNgay");
				this.kvId = rs.getString("kvId") == null ? "" : rs.getString("kvId");
				this.vungId = rs.getString("vungId") == null ? "" : rs.getString("vungId");
				this.nppId = rs.getString("nppId") == null ? "" : rs.getString("nppId");
				// Nếu đơn vị kinh doanh là IP, thì lấy bảng BangGiaBanLeChuan_KhachHang
				if(this.dvkdId.equals("100069"))
					this.nppId = rs.getString("kh");
				this.chietKhau = rs.getFloat("ChietKhau") == 0 ? "" : formater.format(rs.getDouble("ChietKhau"));
			}
			rs.close();

			query = "select ptChietKhau,ChungLoai_FK "+ 
					" from BangGiaBanLeChuan_ChungLoai where BangGiaBanLeChuan_fk = '"+ this.id + "' ";
			rs = db.get(query);
			if (rs != null) 
			{
				this.clPtCk = new Hashtable<String, String>();
				while (rs.next()) 
				{
					this.clPtCk.put(rs.getString("ChungLoai_FK"),rs.getString("ptChietKhau"));
				}
				rs.close();
			}

			query = "select ptChietKhau,NhanHang_FK "+ 
					" from BangGiaBanLeChuan_NhanHang where BangGiaBanLeChuan_fk = '"+ this.id + "' ";
			rs = db.get(query);
			if (rs != null) 
			{
				this.nhPtCk = new Hashtable<String, String>();
				while (rs.next()) 
				{
					this.nhPtCk.put(rs.getString("NhanHang_FK"),rs.getString("ptChietKhau"));
				}
				rs.close();
			}

			query = "select ptChietKhau,sanpham_fk,GiaBanLeChuan,isnull(giahuongdan,0) as giahuongdan "+ 
					" from BANGGIABLC_SANPHAM where BGBLC_FK = '" + this.id+ "' and  (GiaBanLeChuan!=0)";
			rs = db.get(query);
			if (rs != null) 
			{
				this.spChonban = new Hashtable<String, String>();
				this.spGiaban = new Hashtable<String, String>();
				while (rs.next()) 
				{
					this.spChonban.put(rs.getString("sanpham_fk"),rs.getString("sanpham_fk"));
					this.SpGiahuongdan.put(rs.getString("sanpham_fk"),formater2.format(rs.getDouble("giahuongdan")));
					this.spGiaban.put(rs.getString("sanpham_fk"),formater2.format(rs.getDouble("GiaBanLeChuan")));
					this.spPtChietkhau.put(rs.getString("sanpham_fk"),formater2.format(rs.getDouble("ptChietKhau")));
				}
				rs.close();
			}
			
			

		} catch (Exception e) {
			System.out.println("__Exception: " + e.getMessage());
			e.printStackTrace();
		}

		this.createRs();
	}

	
	
	
	
	public boolean createBanggia() 
	{
		try 
		{

			if (checkExits(db)) 
			{
				this.msg = "Ngày bắt đầu áp dụng bảng giá không được phép nhỏ hơn từ ngày áp dụng của bảng giá đang có";
				return false;
			}

			if (this.ten.trim().length() <= 0) 
			{
				this.msg = "Vui lòng chọn tên bảng giá";
				return false;
			}

			if (this.dvkdId.trim().length() <= 0) 
			{
				this.msg = "Vui lòng chọn đơn vị kinh doanh";
				return false;
			}

			if (this.kbhId.trim().length() <= 0) 
			{
				this.msg = "Vui lòng chọn kênh bán hàng";
				return false;
			}

			if (this.nppId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn khách hàng áp dụng bảng giá";
				return false;
			}

			db.getConnection().setAutoCommit(false);

			String query = "insert into BangGiaBanLeChuan(ten, ngaytao, ngaysua, nguoitao, nguoisua, dvkd_fk, KENH_FK, trangthai,TuNgay,ChietKhau) "+ 
					"values(N'"+ this.ten+ "','"+ getDateTime()+ "','"+ getDateTime()+ "','"+ this.userId+ "', "+ "'"+ this.userId+ "','"+ this.dvkdId+ "','"+ this.kbhId+ "', '" + this.trangthai + "','" + tungay + "'," + 0 + ")";

			System.out.println("__Tao moi ERP_BANGGIABAN: " + query);
			if (!db.update(query)) 
			{
				this.msg = "Không thể tạo mới BANGGIA " + query;
				db.getConnection().rollback();
				return false;
			}

			ResultSet rsBg = this.db.get("select scope_identity() as bgbanId");
			rsBg.next();

			this.id = rsBg.getString("bgbanId");
			Enumeration<String> keys = this.spGiaban.keys();
			while (keys.hasMoreElements()) 
			{
				String key = keys.nextElement();
				String gia = (this.spGiaban.get(key) == null || this.spGiaban.get(key).length() <= 0) ? "0" : this.spGiaban.get(key).replaceAll(",", "");
				String giahuongdan = (this.SpGiahuongdan.get(key) == null || this.SpGiahuongdan.get(key).length() <= 0) ? "0" : this.SpGiahuongdan.get(key).replaceAll(",", "");
				String ptChietkhau = (this.spPtChietkhau.get(key) == null || this.spPtChietkhau.get(key).length() <= 0) ? "0" : this.spPtChietkhau.get(key).replaceAll(",", "");
				String spChonBan = this.spChonban.get(key) == null ? "0" : "1";

				if (gia.equals("0"))
					spChonBan = "0";

				query = "insert into BANGGIABLC_SANPHAM (BGBLC_FK,sanpham_fk,GiaBanLeChuan,trangthai,ChietKhau,ptChietKhau,giahuongdan)"+ 
						"values('"+ id+ "', '"+ key+ "','"+ gia+ "','"+ spChonBan+ "',  "+ gia+ "*"+ ptChietkhau+ "/100.0,'" + ptChietkhau + "',"+giahuongdan+"  )";
				if (!this.db.update(query)) 
				{
					this.msg = "Không thể tạo mới BANGGIA_SANPHAM " + query;
					db.getConnection().rollback();
					return false;
				}
			}

			keys = this.clPtCk.keys();
			while (keys.hasMoreElements()) 
			{
				String key = keys.nextElement();
				String gia = this.clPtCk.get(key);
				if (gia.length() > 0) 
				{
					query = "insert into BangGiaBanLeChuan_ChungLoai(BangGiaBanLeChuan_FK,ChungLoai_fk,ptChietKhau)"+ 
							"values('"+ id+ "', '"+ key+ "','"+ gia+ "'  )";
					if (!this.db.update(query)) 
					{
						this.msg = "Không thể tạo mới BANGGIA_SANPHAM " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}

			keys = this.nhPtCk.keys();
			while (keys.hasMoreElements()) 
			{
				String key = keys.nextElement();
				String gia = this.nhPtCk.get(key);
				if (gia.length() > 0) 
				{
					query = "insert into BangGiaBanLeChuan_NhanHang(BangGiaBanLeChuan_FK,NhanHang_fk,ptChietKhau)"+ 
							"values('"+ id+ "', '"+ key+ "','"+ gia+ "'  )";
					if (!this.db.update(query)) 
					{
						this.msg = "Không thể tạo mới BANGGIA_SANPHAM " + query;
						db.getConnection().rollback();
						return false;
					}
				}

			}
			if(!this.dvkdId.equals("100069"))
			if (this.nppId.trim().length() > 0) 
			{
				query = "insert into BangGiaBanLeChuan_NPP(BangGiaBanLeChuan_FK, NPP_FK) "+ 
						"select '"+ id+ "', pk_seq from NhaPhanPhoi where pk_seq in ( "+ this.nppId + " ) ";

				if (!this.db.update(query))
				{
					this.msg = "Không thể tạo mới BangGiaBanLeChuan_NPP "+ query;
					this.db.getConnection().rollback();
					return false;
				}
				if (this.nppIdCks != null && this.nppChietKhaus != null) 
				{
					for (int k = 0; k < this.nppIdCks.length; k++) 
					{
						query = "	update 	BangGiaBanLeChuan_NPP "+ 
								"	set chietkhau=" + this.nppChietKhaus[k]+ " " + 
								"	where 	BangGiaBanLeChuan_FK=" + id+ " and " + 
								"			NPP_FK = " + this.nppIdCks[k]+ " ";
						if (!this.db.update(query)) 
						{
							this.msg = "Không thể tạo mới BangGiaBanLeChuan_NPP ( cap nhat chiet khau)  "
									+ query;
							this.db.getConnection().rollback();
							return false;
						}
					}
				}

			}
			
			if(this.dvkdId.equals("100069"))
				if (this.nppId.trim().length() > 0) 
				{
					query = "insert into BangGiaBanLeChuan_KhachHang(BangGiaBanLeChuan_FK, Khachhang_fk) "+ 
							"select '"+ id+ "', pk_seq from khachhang where pk_seq in ( "+ this.nppId + " ) ";

					if (!this.db.update(query))
					{
						this.msg = "Không thể tạo mới BangGiaBanLeChuan_KhachHang "+ query;
						this.db.getConnection().rollback();
						return false;
					}
					if (this.nppIdCks != null && this.nppChietKhaus != null) 
					{
						for (int k = 0; k < this.nppIdCks.length; k++) 
						{
							query = "	update 	BangGiaBanLeChuan_KhachHang "+ 
									"	set chietkhau=" + this.nppChietKhaus[k]+ " " + 
									"	where 	BangGiaBanLeChuan_FK=" + id+ " and " + 
									"			Khachhang_fk = " + this.nppIdCks[k]+ " ";
							if (!this.db.update(query)) 
							{
								this.msg = "Không thể tạo mới BangGiaBanLeChuan_KhachHang ( cap nhat chiet khau)  "
										+ query;
								this.db.getConnection().rollback();
								return false;
							}
						}
					}

				}

			if (kvId.length() > 0) 
			{
				query = "insert into BangGiaBanLeChuan_KhuVuc(BangGiaBanLeChuan_FK,KhuVuc_FK) "+ 
						" select '"+ this.id+ "',pk_seq From KhuVuc where pk_Seq in ("+ this.kvId+ ")  ";
				if (!this.db.update(query)) 
				{
					this.msg = "Không thể tạo mới BangGiaBanLeChuan_KhuVuc"+ query;
					this.db.getConnection().rollback();
					return false;
				}
			}

			if (vungId.length() > 0) 
			{
				query = "insert into BangGiaBanLeChuan_VUNG(BangGiaBanLeChuan_FK,VUNG_FK) "+ 
						" select '"+ this.id+ "',pk_seq From VUNG where pk_Seq in ("+ this.vungId+ ")  ";
				if (!this.db.update(query)) 
				{
					this.msg = "Không thể tạo mới BangGiaBanLeChuan_VUNG"+ query;
					this.db.getConnection().rollback();
					return false;
				}
			}

			query = "	INSERT INTO BANGGIA_LOG(NGUOISUA,BGBLC_FK,SANPHAM_FK,GIA) "+ 
					"	select "+ this.userId+ ",b.BGBLC_FK,b.SANPHAM_FK,b.GiaBanLeChuan  "+ 
					"	from BangGiaBanLeChuan a inner join BANGGIABLC_SANPHAM b on b.BGBLC_FK=a.PK_SEQ "+ 
					" where a.pk_Seq='" + this.id + "'  ";
			if (!this.db.update(query)) 
			{
				this.msg = "Không thể tạo mới BANGGIA_LOG " + query;
				this.db.getConnection().rollback();
				return false;
			}

			String msg = CheckChietKhau();
			if (msg.length() > 0) 
			{
				this.msg = " Vui lòng nhập chiết khấu theo chủng loại/nhãn hàng hoặc sản phẩm tương ứng "+ msg;
				this.db.getConnection().rollback();
				return false;
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e) {
			this.msg = "Loi: " + e.getMessage();
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {
			}
			return false;
		}
		return true;
	}



	public boolean updateBanggia() 
	{
		try 
		{
			// CHECK NPP DA CO NGAY AP DUNG TU NGAY ROI THI KHONG DUOC THAY DOI
			// TU NGAY
			// CHECK XEM CO NPP NAO DUOC AP DUNG BANG GIA TRUNG TU NGAY CHUA
			String query = "select a.TUNGAY, c.MA, COUNT(a.PK_SEQ) as soBANGGIA  "+ 
					"from BangGiaBanLeChuan a inner join BangGiaBanLeChuan_NPP b on a.PK_SEQ = b.BangGiaBanLeChuan_FK  "+ 
					"inner join NHAPHANPHOI c on b.NPP_FK = c.PK_SEQ  "+ 
					"where a.PK_SEQ != '"+ this.id+ "' and a.TUNGAY = ( select tungay from BangGiaBanLeChuan where pk_seq = '"+ this.id+ "' ) and a.KENH_FK = '"+ kbhId+ "' "+ "and a.DVKD_FK = '"+ dvkdId+ "' AND B.NPP_FK IN (SELECT NPP_FK FROM BangGiaBanLeChuan_NPP WHERE BangGiaBanLeChuan_FK='"+ this.id+ "' )  "+ 
					"group by a.TUNGAY, c.MA "+ 
					"having COUNT(a.PK_SEQ) >= 1 ";

			System.out.println("___CHECK NPP SELECTED: " + query);
			ResultSet rsNPP = db.get(query);
			String nppKhongHopLe = "";
			String tungay = "";

			{
				while (rsNPP.next()) 
				{
					nppKhongHopLe += rsNPP.getString("MA") + ",";
					System.out.println("____SO BG KHONG HOP LE: "+ nppKhongHopLe);
					tungay = rsNPP.getString("TUNGAY");
				}
				rsNPP.close();
			}

			System.out.println("____NPP KHONG HOP LE: " + nppKhongHopLe);
			if (nppKhongHopLe.trim().length() > 0) 
			{
				this.msg = "Các nhà phân phối ( " + nppKhongHopLe+ " ) đã tồn tại trong bảng giá có từ ngày ( " + tungay+ " ) ";
				return false;
			}
			

			if (this.ten.trim().length() <= 0) {
				this.msg = "Vui lòng chọn tên bảng giá";
				return false;
			}

			if (this.dvkdId.trim().length() <= 0) {
				this.msg = "Vui lòng chọn đơn vị kinh doanh";
				return false;
			}

			if (this.kbhId.trim().length() <= 0) {
				this.msg = "Vui lòng chọn kênh bán hàng";
				return false;
			}

			if (this.nppId.trim().length() <= 0) {
				this.msg = "Vui lòng chọn khách hàng áp dụng bảng giá";
				return false;
			} 

			db.getConnection().setAutoCommit(false);

			//query = "update BangGiaBanLeChuan set   TuNgay='" + this.tungay+ "', ten = N'" + this.ten + "', ngaysua = '"+ getDateTime() + "', " + "nguoisua = '" + this.userId+ "', trangthai = '" + this.trangthai + "',ChietKhau=" + 0+ " where pk_seq = '" + this.id + "'";
			query = "update BangGiaBanLeChuan set  kbh_fk = '"+ this.kbhId + "', ngaysua = '"+ getDateTime() + "', " + "nguoisua = '" + this.userId+ "', trangthai = '" + this.trangthai + "',ChietKhau=" + 0+ " where pk_seq = '" + this.id + "'";
			System.out.println("1.Update: " + query);
			if (!db.update(query)) {
				this.msg = "Không thể cập nhật BANGGIA: " + query;
				this.db.getConnection().rollback();
				return false;
			}

			
			String bgbanId = this.id;
			
			// DVKD IP đc phép sửa bảng giá,Tissue đồng bộ bảng giá, không được phép sửa giá và npp
			if(this.dvkdId.equals("100069"))
			{/*
				query = "delete from BANGGIABLC_SANPHAM where BGBLC_FK=" + bgbanId+ " ";
				if (!db.update(query)) 
				{
					this.msg = "Không thể cập nhật BANGGIABLC_SANPHAM: " + query;
					this.db.getConnection().rollback();
					return false;
				}
				
				
				Enumeration<String> keys = this.spGiaban.keys();
				while (keys.hasMoreElements()) 
				{
					String key = keys.nextElement();
					String gia = (this.spGiaban.get(key) == null || this.spGiaban.get(key).length() <= 0) ? "0" : this.spGiaban.get(key).replaceAll(",", "");
					String giahuongdan = (this.SpGiahuongdan.get(key) == null || this.SpGiahuongdan.get(key).length() <= 0) ? "0" : this.SpGiahuongdan.get(key).replaceAll(",", "");
					String ptChietkhau = (this.spPtChietkhau.get(key) == null || this.spPtChietkhau.get(key).length() <= 0) ? "0" : this.spPtChietkhau.get(key).replaceAll(",", "");
					String spChonBan = this.spChonban.get(key) == null ? "0" : "1";

					if (gia.equals("0"))
						spChonBan = "0";

					query = "insert into BANGGIABLC_SANPHAM (BGBLC_FK,sanpham_fk,GiaBanLeChuan,trangthai,ChietKhau,ptChietKhau,giahuongdan)"+ 
							"values('"+ id+ "', '"+ key+ "','"+ gia+ "','"+ spChonBan+ "',  "+ gia+ "*"+ ptChietkhau+ "/100.0,'" + ptChietkhau + "' ,"+giahuongdan+" )";

					if (Double.parseDouble(gia) > 0)
						System.out.println("[BANGGIABLC_SANPHAM]" + query);

					if (!this.db.update(query)) 
					{
						this.msg = "Không thể tạo mới BANGGIA_SANPHAM " + query;
						db.getConnection().rollback();
						return false;
					}
				}
				
				
				///
				
				query = "delete from BangGiaBanLeChuan_Khachhang where BangGiaBanLeChuan_FK="
						+ bgbanId + " ";
				if (!db.update(query)) 
				{
					this.msg = "Không thể cập nhật BangGiaBanLeChuan_Khachhang: " + query;
					this.db.getConnection().rollback();
					return false;
				}

				query = "insert into BangGiaBanLeChuan_KhachHang(BangGiaBanLeChuan_FK, Khachhang_fk) "+ 
						"select '"+ id+ "', pk_seq from khachhang where pk_seq in ( "+ this.nppId + " ) ";

				if (!this.db.update(query))
				{
					this.msg = "Không thể tạo mới BangGiaBanLeChuan_KhachHang "+ query;
					this.db.getConnection().rollback();
					return false;
				}
				
				
				query = "	INSERT INTO BANGGIA_LOG(NGUOISUA,BGBLC_FK,SANPHAM_FK,GIA) "
						+ "	select "
						+ this.userId
						+ ",b.BGBLC_FK,b.SANPHAM_FK,b.GiaBanLeChuan  "
						+ "	from BangGiaBanLeChuan a inner join BANGGIABLC_SANPHAM b on b.BGBLC_FK=a.PK_SEQ "
						+ " where a.pk_Seq='" + this.id + "'  ";
				if (!this.db.update(query)) {
					this.msg = "Không thể tạo mới BANGGIA_LOG " + query;
					this.db.getConnection().rollback();
					return false;
				}
			*/}
			
			// Cập nhật giá hướng dẫn người dùng, không cập nhật giá bán lẻ chuẩn
			
			Enumeration<String> keys = this.spGiaban.keys();
			while (keys.hasMoreElements()) 
			{
				String key = keys.nextElement();
				String gia = (this.SpGiahuongdan.get(key) == null || this.SpGiahuongdan.get(key).length() <= 0) ? "0" : this.SpGiahuongdan.get(key).replaceAll(",", "");

				query = "update BANGGIABLC_SANPHAM set giahuongdan = "+gia+"  where BGBLC_FK = '"+this.id+"' and  sanpham_fk  = '"+key+"'"; 
			
				if (Double.parseDouble(gia) > 0)
					System.out.println("[BANGGIABLC_SANPHAM]" + query);

				if (!this.db.update(query)) 
				{
					this.msg = "Không thể cập nhật giá hướng dẫn " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			query = "delete from BangGiaBanLeChuan_NPP where BangGiaBanLeChuan_FK="
					+ bgbanId + " ";
			if (!db.update(query)) 
			{
				this.msg = "Không thể cập nhật BangGiaBanLeChuan_NPP: " + query;
				this.db.getConnection().rollback();
				return false;
			}
			
			if (this.nppId.trim().length() > 0) 
			{
				query = "insert into BangGiaBanLeChuan_NPP(BangGiaBanLeChuan_FK, NPP_FK) "+ 
						"select '"+ id+ "', pk_seq from NhaPhanPhoi where pk_seq in ( "+ this.nppId + " ) ";

				if (!this.db.update(query))
				{
					this.msg = "Không thể tạo mới BangGiaBanLeChuan_NPP "+ query;
					this.db.getConnection().rollback();
					return false;
				}
				if (this.nppIdCks != null && this.nppChietKhaus != null) 
				{
					for (int k = 0; k < this.nppIdCks.length; k++) 
					{
						query = "	update 	BangGiaBanLeChuan_NPP "+ 
								"	set chietkhau=" + this.nppChietKhaus[k]+ " " + 
								"	where 	BangGiaBanLeChuan_FK=" + id+ " and " + 
								"			NPP_FK = " + this.nppIdCks[k]+ " ";
						if (!this.db.update(query)) 
						{
							this.msg = "Không thể tạo mới BangGiaBanLeChuan_NPP ( cap nhat chiet khau)  "
									+ query;
							this.db.getConnection().rollback();
							return false;
						}
					}
				}

			}
			
			query = "	INSERT INTO BANGGIA_LOG(NGUOISUA,BGBLC_FK,SANPHAM_FK,GIA) "
					+ "	select "
					+ this.userId
					+ ",b.BGBLC_FK,b.SANPHAM_FK,b.GiaBanLeChuan  "
					+ "	from BangGiaBanLeChuan a inner join BANGGIABLC_SANPHAM b on b.BGBLC_FK=a.PK_SEQ "
					+ " where a.pk_Seq='" + this.id + "'  ";
			if (!this.db.update(query)) {
				this.msg = "Không thể tạo mới BANGGIA_LOG " + query;
				this.db.getConnection().rollback();
				return false;
			}
			
		/*	query = "delete from BANGGIABLC_SANPHAM where BGBLC_FK=" + bgbanId+ " ";
			if (!db.update(query)) 
			{
				this.msg = "Không thể cập nhật BANGGIABLC_SANPHAM: " + query;
				this.db.getConnection().rollback();
				return false;
			}
			if(!this.dvkdId.equals("100069"))
			{
				query = "delete from BangGiaBanLeChuan_NPP where BangGiaBanLeChuan_FK="
						+ bgbanId + " ";
				if (!db.update(query)) 
				{
					this.msg = "Không thể cập nhật BangGiaBanLeChuan_NPP: " + query;
					this.db.getConnection().rollback();
					return false;
				}
			}
			else
			{
				query = "delete from BangGiaBanLeChuan_Khachhang where BangGiaBanLeChuan_FK="
						+ bgbanId + " ";
				if (!db.update(query)) 
				{
					this.msg = "Không thể cập nhật BangGiaBanLeChuan_Khachhang: " + query;
					this.db.getConnection().rollback();
					return false;
				}
			}*/
/*
			query = "delete from BangGiaBanLeChuan_NhanHang where BangGiaBanLeChuan_FK="
					+ bgbanId + " ";
			if (!db.update(query)) 
			{
				this.msg = "Không thể cập nhật BangGiaBanLeChuan_NPP: " + query;
				this.db.getConnection().rollback();
				return false;
			}

			query = "delete from BangGiaBanLeChuan_ChungLoai where BangGiaBanLeChuan_FK="
					+ bgbanId + " ";
			if (!db.update(query)) 
			{
				this.msg = "Không thể cập nhật BangGiaBanLeChuan_NPP: " + query;
				this.db.getConnection().rollback();
				return false;
			}
*/
		/*	Enumeration<String> keys = this.spGiaban.keys();
			while (keys.hasMoreElements()) 
			{
				String key = keys.nextElement();
				String gia = (this.spGiaban.get(key) == null || this.spGiaban.get(key).length() <= 0) ? "0" : this.spGiaban.get(key).replaceAll(",", "");

				String ptChietkhau = (this.spPtChietkhau.get(key) == null || this.spPtChietkhau.get(key).length() <= 0) ? "0" : this.spPtChietkhau.get(key).replaceAll(",", "");
				String spChonBan = this.spChonban.get(key) == null ? "0" : "1";

				if (gia.equals("0"))
					spChonBan = "0";

				query = "insert into BANGGIABLC_SANPHAM (BGBLC_FK,sanpham_fk,GiaBanLeChuan,trangthai,ChietKhau,ptChietKhau)"+ 
						"values('"+ id+ "', '"+ key+ "','"+ gia+ "','"+ spChonBan+ "',  "+ gia+ "*"+ ptChietkhau+ "/100.0,'" + ptChietkhau + "'   )";

				if (Double.parseDouble(gia) > 0)
					System.out.println("[BANGGIABLC_SANPHAM]" + query);

				if (!this.db.update(query)) 
				{
					this.msg = "Không thể tạo mới BANGGIA_SANPHAM " + query;
					db.getConnection().rollback();
					return false;
				}
			}

			keys = this.clPtCk.keys();
			while (keys.hasMoreElements()) 
			{
				String key = keys.nextElement();
				String gia = this.clPtCk.get(key);

				if (gia.length() > 0) 
				{
					query = "insert into BangGiaBanLeChuan_ChungLoai(BangGiaBanLeChuan_FK,ChungLoai_fk,ptChietKhau)"+ 
							"values('"+ id+ "', '"+ key+ "','"+ gia+ "'  )";
					if (!this.db.update(query)) 
					{
						this.msg = "Không thể tạo mới BANGGIA_SANPHAM " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}

			keys = this.nhPtCk.keys();
			while (keys.hasMoreElements()) 
			{
				String key = keys.nextElement();
				String gia = this.nhPtCk.get(key);
				if (gia.length() > 0) 
				{
					query = "insert into BangGiaBanLeChuan_NhanHang(BangGiaBanLeChuan_FK,NhanHang_fk,ptChietKhau)"+ 
							"values('"+ id+ "', '"+ key+ "','"+ gia+ "'  )";
					if (!this.db.update(query)) {
						this.msg = "Không thể tạo mới BANGGIA_SANPHAM " + query;
						db.getConnection().rollback();
						return false;
					}
				}

			}
			if(!this.dvkdId.equals("100069"))
			if (this.nppId.trim().length() > 0) 
			{
				query = "insert into BangGiaBanLeChuan_NPP(BangGiaBanLeChuan_FK, NPP_FK) "+ 
						"select '"+ id+ "', pk_seq from NhaPhanPhoi where pk_seq in ( "+ this.nppId + " ) ";

				if (!this.db.update(query))
				{
					this.msg = "Không thể tạo mới BangGiaBanLeChuan_NPP "+ query;
					this.db.getConnection().rollback();
					return false;
				}
				if (this.nppIdCks != null && this.nppChietKhaus != null) 
				{
					for (int k = 0; k < this.nppIdCks.length; k++) 
					{
						query = "	update 	BangGiaBanLeChuan_NPP "+ 
								"	set chietkhau=" + this.nppChietKhaus[k]+ " " + 
								"	where 	BangGiaBanLeChuan_FK=" + id+ " and " + 
								"			NPP_FK = " + this.nppIdCks[k]+ " ";
						if (!this.db.update(query)) 
						{
							this.msg = "Không thể tạo mới BangGiaBanLeChuan_NPP ( cap nhat chiet khau)  "
									+ query;
							this.db.getConnection().rollback();
							return false;
						}
					}
				}

			}
			
			if(this.dvkdId.equals("100069"))
				if (this.nppId.trim().length() > 0) 
				{
					query = "insert into BangGiaBanLeChuan_KhachHang(BangGiaBanLeChuan_FK, Khachhang_fk) "+ 
							"select '"+ id+ "', pk_seq from khachhang where pk_seq in ( "+ this.nppId + " ) ";

					if (!this.db.update(query))
					{
						this.msg = "Không thể tạo mới BangGiaBanLeChuan_KhachHang "+ query;
						this.db.getConnection().rollback();
						return false;
					}
					if (this.nppIdCks != null && this.nppChietKhaus != null) 
					{
						for (int k = 0; k < this.nppIdCks.length; k++) 
						{
							query = "	update 	BangGiaBanLeChuan_KhachHang "+ 
									"	set chietkhau=" + this.nppChietKhaus[k]+ " " + 
									"	where 	BangGiaBanLeChuan_FK=" + id+ " and " + 
									"			Khachhang_fk = " + this.nppIdCks[k]+ " ";
							if (!this.db.update(query)) 
							{
								this.msg = "Không thể tạo mới BangGiaBanLeChuan_KhachHang ( cap nhat chiet khau)  "
										+ query;
								this.db.getConnection().rollback();
								return false;
							}
						}
					}

				}

			query = "	INSERT INTO BANGGIA_LOG(NGUOISUA,BGBLC_FK,SANPHAM_FK,GIA) "
					+ "	select "
					+ this.userId
					+ ",b.BGBLC_FK,b.SANPHAM_FK,b.GiaBanLeChuan  "
					+ "	from BangGiaBanLeChuan a inner join BANGGIABLC_SANPHAM b on b.BGBLC_FK=a.PK_SEQ "
					+ " where a.pk_Seq='" + this.id + "'  ";
			if (!this.db.update(query)) {
				this.msg = "Không thể tạo mới BANGGIA_LOG " + query;
				this.db.getConnection().rollback();
				return false;
			}

			query = " delete from BangGiaBanLeChuan_KhuVuc where BangGiaBanLeChuan_FK='"
					+ this.id + "'";
			if (!this.db.update(query)) {
				this.msg = "Không thể tạo mới BangGiaBanLeChuan_KhuVuc" + query;
				this.db.getConnection().rollback();
				return false;
			}

			query = " delete from BangGiaBanLeChuan_VUNG where BangGiaBanLeChuan_FK='"
					+ this.id + "'";
			if (!this.db.update(query)) {
				this.msg = "Không thể tạo mới BangGiaBanLeChuan_VUNG" + query;
				this.db.getConnection().rollback();
				return false;
			}

			if (this.nppId.trim().length() > 0) 
			{
				query = "insert into BangGiaBanLeChuan_NPP(BangGiaBanLeChuan_FK, NPP_FK) "+ 
						"select '"+ id+ "', pk_seq from NhaPhanPhoi where pk_seq in ( "+ this.nppId + " ) ";

				if (!this.db.update(query))
				{
					this.msg = "Không thể tạo mới BangGiaBanLeChuan_NPP "+ query;
					this.db.getConnection().rollback();
					return false;
				}
				if (this.nppIdCks != null && this.nppChietKhaus != null) 
				{
					for (int k = 0; k < this.nppIdCks.length; k++) 
					{
						query = "	update 	BangGiaBanLeChuan_NPP "+ 
								"	set chietkhau=" + this.nppChietKhaus[k]+ " " + 
								"	where 	BangGiaBanLeChuan_FK=" + id+ " and " + 
								"			NPP_FK = " + this.nppIdCks[k]+ " ";
						if (!this.db.update(query)) 
						{
							this.msg = "Không thể tạo mới BangGiaBanLeChuan_NPP ( cap nhat chiet khau)  "
									+ query;
							this.db.getConnection().rollback();
							return false;
						}
					}
				}

			}

			if (kvId.length() > 0) 
			{
				query = "insert into BangGiaBanLeChuan_KhuVuc(BangGiaBanLeChuan_FK,KhuVuc_FK) "+ 
						" select '"+ this.id+ "',pk_seq From KhuVuc where pk_Seq in ("+ this.kvId+ ")  ";
				if (!this.db.update(query)) 
				{
					this.msg = "Không thể tạo mới BangGiaBanLeChuan_KhuVuc"+ query;
					this.db.getConnection().rollback();
					return false;
				}
			}

			if (vungId.length() > 0) 
			{
				query = "insert into BangGiaBanLeChuan_VUNG(BangGiaBanLeChuan_FK,VUNG_FK) "+ 
						" select '"+ this.id+ "',pk_seq From VUNG where pk_Seq in ("+ this.vungId+ ")  ";
				if (!this.db.update(query)) 
				{
					this.msg = "Không thể tạo mới BangGiaBanLeChuan_VUNG"+ query;
					this.db.getConnection().rollback();
					return false;
				}
			}
*/
			String msg = CheckChietKhau();
			if (msg.length() > 0) 
			{
				this.msg = " Vui lòng nhập chiết khấu theo chủng loại/nhãn hàng hoặc sản phẩm tương ứng "+ msg;
				this.db.getConnection().rollback();
				return false;
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e) {
			this.msg = "Loi: " + e.getMessage();
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {
			}
			return false;
		}
		return true;
	}

	public String CheckChietKhau() {
		String msg = "";
		String query = 
				"	select  a.BGBLC_FK,d.TEN as nhTen,b.MA as spMa,b.TEN as spTen,a.ptChietKhau as spCk,c.ptChietKhau as nhCk  "+ 
						"	from BANGGIABLC_SANPHAM  a inner join SANPHAM b on b.PK_SEQ=a.SANPHAM_FK   "+ 
						"		inner join BangGiaBanLeChuan_nhanhang c on c.BangGiaBanLeChuan_fk=a.BGBLC_FK "+ 
						"		and c.nhanhang_fk=b.nhanhang_fk "+ 
						"		inner join NHANHANG d on d.PK_SEQ=b.NHANHANG_FK "+ 
						"	where c.ptChietKhau!=a.ptChietKhau and c.ptChietKhau!=0  and a.TRANGTHAI=1 and a.GiaBanLeChuan!=0 and  c.BangGiaBanLeChuan_fk='"+ this.id + "'";
		ResultSet rs = db.get(query);
		try {
			while (rs.next()) 
			{
				msg += "Nhãn hàng "+ rs.getString("nhTen") +  " " + rs.getString("spMa") + "-" + rs.getString("spTen") + "\n";
			}
			if (rs != null)
				rs.close();
			query = 
					"	select  a.BGBLC_FK,d.TEN as clTen,b.MA as spMa,b.TEN as spTen,a.ptChietKhau as spCk,c.ptChietKhau as nhCk  "+ 
							"	from BANGGIABLC_SANPHAM  a inner join SANPHAM b on b.PK_SEQ=a.SANPHAM_FK "+ 
							"		inner join BangGiaBanLeChuan_CHUNGLOAI c on c.BangGiaBanLeChuan_fk=a.BGBLC_FK "+ 
							"		and c.CHUNGLOAI_FK=b.CHUNGLOAI_FK "+ 
							"		inner join CHUNGLOAI d on d.PK_SEQ=b.CHUNGLOAI_FK "+ 
							"	where c.ptChietKhau!=a.ptChietKhau  and a.TRANGTHAI=1 and a.GiaBanLeChuan!=0 and c.ptChietKhau!=0  and c.BangGiaBanLeChuan_fk='"+ this.id + "'";
			rs = db.get(query);
			while (rs.next()) 
			{
				msg +="Chủng loại " + rs.getString("clTen") +" " + rs.getString("spMa") + "-"+ rs.getString("spTen") + "\n";
			}
			if (rs != null)
				rs.close();
		} catch (SQLException e) 
		{
			e.printStackTrace();
			return "Exception !";
		}
		return msg;
	}

	public void DbClose() {
		try {
			this.db.shutDown();
			if (nppRs != null)
				nppRs.close();
			if (kbhRs != null)
				kbhRs.close();
			if (dvkdRs != null)
				dvkdRs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public String getTrangthai() {
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}

	public String getTen() {

		return this.ten;
	}

	public void setTen(String ten) {

		this.ten = ten;
	}

	public String getDvkdId() {

		return this.dvkdId;
	}

	public void setDvkdId(String dvkdId) {

		this.dvkdId = dvkdId;
	}

	public ResultSet getDvkdRs() {

		return this.dvkdRs;
	}

	public void setDvkdRs(ResultSet dvkdRs) {

		this.dvkdRs = dvkdRs;
	}

	public String getKbhId() {

		return this.kbhId;
	}

	public void setKbhId(String kbhId) {

		this.kbhId = kbhId;
	}

	public ResultSet getKbhRs() {

		return this.kbhRs;
	}

	public void setKbhRs(ResultSet kbhRs) {

		this.kbhRs = kbhRs;
	}

	public void createRs() {

		String query = "select pk_seq,diengiai,ten from kenhbanhang order by PK_SEQ desc ";
		query = "select PK_SEQ,TEN ,DIENGIAI from VUNG";
		this.vungRs = this.db.get(query);
		query = "select PK_SEQ,TEN,DIENGIAI from KHUVUC where 1=1 ";
		if (this.vungId.length() > 0)
			query += " and vung_fk in (" + this.vungId + ") ";
		this.kvRs = this.db.get(query);

		query = "	select distinct a.donvikinhdoanh as dvkd, a.pk_seq as dvkdId  from donvikinhdoanh a,nhacungcap_dvkd b  where a.pk_seq = b.DVKD_fk and b.checked ='1' and a.trangthai ='1' "
				+ "	order by a.donvikinhdoanh ";
		this.dvkdRs = this.db.get(query);

		this.kbhRs = this.db
				.get("select diengiai as kenh, pk_seq as kenhId from kenhbanhang where trangthai='1' and pk_seq in "+util.quyen_kenh(this.userId)+" order by PK_SEQ desc ");
		if(!this.dvkdId.equals("100069"))
		if (this.kbhId.trim().length() > 0 && this.dvkdId.length() > 0
				&& this.tungay.length() > 0) {
			// lấy những nhà NPP chưa được chia trong bảng giá có hiệu lực từ
			// ngày
			query = " SELECT distinct 0 as chietkhaunpp, a.PK_SEQ, a.Ma , a.Ten, isnull(a.diachi, 'NA') as diachi, isnull(a.dienthoai, 'NA') as dienthoai  "
					+ "from NhaPhanPhoi a inner join NhaPP_KBH b on b.npp_fk=a.pk_seq  "
					+ " inner join NHAPP_NHACC_DONVIKD c on c.NPP_FK = b.NPP_FK "
					+ " inner join nhacungcap_dvkd d on d.PK_SEQ = c.NCC_DVKD_FK "
					+ " where a.TrangThai = '1' and b.KBH_FK = '"
					+ this.kbhId
					+ "' and d.CHECKED=1 and d.dvkd_fk='"
					+ this.dvkdId
					+ "'    ";

			query += " and a.pk_seq not in ( select NPP_FK from BangGiaBanLeChuan_NPP "
					+ "  where BangGiaBanLeChuan_FK in ( select pk_seq from BangGiaBanLeChuan where  TuNgay='"
					+ this.tungay
					+ "' and  TRANGTHAI = '1' and KENH_FK='"
					+ this.kbhId + "' AND DVKD_FK='" + this.dvkdId + "' ))";
			
			if (this.kvId.length() > 0)
				query += " and a.khuvuc_fk in (" + this.kvId + ") ";
			if (this.vungId.length() > 0)
				query += " and a.khuvuc_fk in (select pk_Seq from khuvuc where  vung_Fk in ("
						+ this.vungId + ") ) ";
			
			
			if (this.id.trim().length() > 0) {
				// lấy những nhà NPP đã được chia trong bảng giá có hiệu lực từ
				// ngày
				query += " union "
						+ " SELECT isnull((select distinct CHIETKHAU from BangGiaBanLeChuan_NPP where NPP_FK=a.pk_seq and BangGiaBanLeChuan_FK="
						+ this.id
						+ "),0) as chietkhaunpp,a.PK_SEQ,  a.Ma, a.Ten, isnull(a.diachi, 'NA') as diachi, isnull(a.dienthoai, 'NA') as dienthoai   "
						+ " from NhaPhanPhoi a  "
						+ " where  a. pk_seq in ( select NPP_FK from BangGiaBanLeChuan_NPP   where BangGiaBanLeChuan_FK = '"
						+ this.id + "' )  ";
			}

			if (this.kvId.length() > 0)
				query += " and a.khuvuc_fk in (" + this.kvId + ") ";
			if (this.vungId.length() > 0)
				query += " and a.khuvuc_fk in (select pk_Seq from khuvuc where  vung_Fk in ("
						+ this.vungId + ") ) ";
			
			
			if (this.id.trim().length() > 0) {
				// lấy những nhà NPP đã được chia trong bảng giá có hiệu lực từ
				// ngày
				query += " union "
						+ " SELECT isnull((select distinct CHIETKHAU from BangGiaBanLeChuan_NPP where NPP_FK=a.pk_seq and BangGiaBanLeChuan_FK="
						+ this.id
						+ "),0) as chietkhaunpp,a.PK_SEQ,  a.Ma, a.Ten, isnull(a.diachi, 'NA') as diachi, isnull(a.dienthoai, 'NA') as dienthoai   "
						+ " from NhaPhanPhoi a  "
						+ " where  a. pk_seq in ( select NPP_FK from BangGiaBanLeChuan_NPP   where BangGiaBanLeChuan_FK = '"
						+ this.id + "' )  ";
			}
			
			query += " order by a.ten asc ";
			
			
		

			System.out.println("___Khoi tao khach hang: " + query);
			this.nppRs = db.get(query);
			
			
		}
		
		// Nếu DVKD là IP thì lưu vào bảng BangGiaBanLeChuan_KhachHang
		if(this.dvkdId.equals("100069"))
		if (this.kbhId.trim().length() > 0 && this.dvkdId.length() > 0
				&& this.tungay.length() > 0) {
			// lấy những nhà NPP chưa được chia trong bảng giá có hiệu lực từ
			// ngày
			query = " SELECT distinct 0 as chietkhaunpp, a.PK_SEQ, a.Smartid as MA , a.Ten, isnull(a.diachi, 'NA') as diachi, isnull(a.dienthoai, 'NA') as dienthoai  "
					+ "from Khachhang a "
					+ " where a.TrangThai = '1' and a.KBH_FK = '"
					+ this.kbhId
					+ "' and a.ismt = '1' ";

			query += " and a.pk_seq not in ( select khachhang_fk from BangGiaBanLeChuan_KhachHang "
					+ "  where BangGiaBanLeChuan_FK in ( select pk_seq from BangGiaBanLeChuan where  TuNgay='"
					+ this.tungay
					+ "' and  TRANGTHAI = '1' and KENH_FK='"
					+ this.kbhId + "' AND DVKD_FK='" + this.dvkdId + "' ))";
			if (this.id.trim().length() > 0) {
				// lấy những nhà NPP đã được chia trong bảng giá có hiệu lực từ
				// ngày
				query += " union "
						+ " SELECT isnull((select distinct CHIETKHAU from BangGiaBanLeChuan_KhachHang where Khachhang_fk = a.pk_seq and BangGiaBanLeChuan_FK="
						+ this.id
						+ "),0) as chietkhaunpp,a.PK_SEQ,  a.Smartid as Ma, a.Ten, isnull(a.diachi, 'NA') as diachi, isnull(a.dienthoai, 'NA') as dienthoai   "
						+ " from khachhang a  "
						+ " where  a. pk_seq in ( select Khachhang_fk from BangGiaBanLeChuan_KhachHang   where BangGiaBanLeChuan_FK = '"
						+ this.id + "' )  ";
			}

		
			query += " order by a.ten asc ";
			
			
		

			System.out.println("___Khoi tao khach hang: " + query);
			this.nppRs = db.get(query);
			
			
		}
		
		String querySP = "";
		if (this.dvkdId.trim().length() > 0 && this.kbhId.trim().length() > 0) 
		{
			String banggia_fk = this.id.trim().length() <= 0 ? "0" : this.id.trim();
			querySP = 
					"	select  isnull(a.NHANHANG_FK,0) as NHANHANG_FK,isnull(a.CHUNGLOAI_FK,0) as CHUNGLOAI_FK ,a.PK_SEQ as spId, isnull(d.TEN,'') as nhTen,isnull(e.TEN,'') as clTen,a.MA as spMa, isnull(a.TEN, '') as spTen , ISNULL(b.donvi, 'NA') as donvi,  "+
							"		isnull(c.GiaBanLeChuan, 0) as giaban,isnull(c.trangthai, 0) as trangthai,isnull(c.ptChietKhau,0) as ptChietKhau  "+
							" from SanPham a  "+
							"	left join NHANHANG d on d.PK_SEQ=a.NHANHANG_FK "+
							"	left join CHUNGLOAI e on e.PK_SEQ=a.CHUNGLOAI_FK  "+
							"	left join DONVIDOLUONG b on a.DVDL_FK = b.PK_SEQ "+
							"	left join BANGGIABLC_SANPHAM c on a.pk_seq = c.sanpham_fk "+
							"		and c.BGBLC_FK = '"+ banggia_fk+ "'     "+ "	where a.DVKD_FK = '"+ this.dvkdId+ "' and a.trangthai = '1' ";
			querySP += " order by d.TEN,e.TEN ,spMa asc, spTen asc   ";
			System.out.println("--- Lay SP: " + querySP);
			spRs = db.get(querySP);

			query = "select PK_SEQ as nhId,TEN as nhTen from NHANHANG where DVKD_FK='"
					+ this.dvkdId + "' ";
			this.nhRs = this.db.get(query);

			query = " select c.TEN as nhTEN,b.TEN as clTEN,a.CL_FK AS clId ,A.NH_FK as nhId  "
					+ " from NHANHANG_CHUNGLOAI  a inner join CHUNGLOAI b on b.PK_SEQ=a.CL_FK   "
					+ "	inner join NHANHANG c on c.PK_SEQ=a.NH_FK  "
					+ " order by 	c.TEN ,b.ten ";
			this.clRs = this.db.get(query);
		}

	}

	private boolean checkExits(dbutils db) {
		String query = 
				"select count(*) as sodong from BangGiaBanLeChuan bg where DVKD_FK="+ this.dvkdId+ " and KENH_FK="+ this.kbhId+ " "+ " AND TUNGAY > '" + this.tungay + "'    ";
		if (this.id.length() > 0)
			query += " and pk_seq != " + this.id + " ";

		if (this.nppId.length() > 0) 
		{
			query += 
					" and exists " + 
							" ( "+ 
							"	select * from BangGiaBanLeChuan_NPP bgnpp "+ 
							"	where bg.PK_SEQ=bgnpp.BangGiaBanLeChuan_FK "+ 
							"	and NPP_FK in (" + this.nppId + ") " + "  ) ";
		}

		ResultSet rs = db.get(query);
		int sodong = 0;
		if (rs != null) {
			try {
				if (rs.next()) {
					sodong = rs.getInt("sodong");
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				sodong = 0;
			}
		}
		if (sodong > 0)
			return true;
		return false;
	}

	String tungay;

	@Override
	public String getTuNgay() {
		return tungay;
	}

	@Override
	public void setTuNgay(String tungay) {
		this.tungay = tungay;
	}

	@Override
	public ResultSet getNppRs() {
		return nppRs;
	}

	@Override
	public void setNppRs(ResultSet NppRs) {
		this.nppRs = NppRs;
	}

	@Override
	public String getNppId() {
		return this.nppId;
	}

	@Override
	public void setNppId(String nppId) {
		this.nppId = nppId;
	}

	@Override
	public String[] getNppIdCks() {
		return this.nppIdCks;
	}

	@Override
	public void setNppIdCks(String[] ids) {
		this.nppIdCks = ids;
	}

	@Override
	public String[] getNppChietKhaus() {
		return this.nppChietKhaus;
	}

	@Override
	public void setNppChietKhaus(String[] cks) {
		this.nppChietKhaus = cks;
	}

	ResultSet kvRs, vungRs;

	@Override
	public ResultSet getKvRs() {

		return this.kvRs;
	}

	@Override
	public void setKvRs(ResultSet KvRs) {
		this.kvRs = KvRs;

	}

	@Override
	public ResultSet getVungRs() {

		return this.vungRs;
	}

	@Override
	public void setVungRs(ResultSet VungRs) {
		this.vungRs = VungRs;
	}

	String vungId, kvId;

	public String getVungId() {
		return vungId;
	}

	public void setVungId(String vungId) {
		this.vungId = vungId;
	}

	public String getKvId() {
		return kvId;
	}

	public void setKvId(String kvId) {
		this.kvId = kvId;
	}

	Hashtable<String, String> SpGiahuongdan = new Hashtable<String, String>();

	public Hashtable<String, String> getSpGiahuongdan() {
		return SpGiahuongdan;
	}

	public void setSpGiahuongdan(Hashtable<String, String> SpGiahuongdan) {
		this.SpGiahuongdan = SpGiahuongdan;
	}
	
	Hashtable<String, String> spGiaban = new Hashtable<String, String>();

	public Hashtable<String, String> getSpGiaban() {
		return spGiaban;
	}

	public void setSpGiaban(Hashtable<String, String> spGiaban) {
		this.spGiaban = spGiaban;
	}

	Hashtable<String, String> spPtChietkhau = new Hashtable<String, String>();

	public Hashtable<String, String> getSpPtChietkhau() {
		return spPtChietkhau;
	}

	public void setSpPtChietkhau(Hashtable<String, String> spPtChietkhau) {
		this.spPtChietkhau = spPtChietkhau;
	}

	Hashtable<String, String> spChonban = new Hashtable<String, String>();

	public Hashtable<String, String> getSpChonban() {
		return spChonban;
	}

	public void setSpChonban(Hashtable<String, String> spChonban) {
		this.spChonban = spChonban;
	}

	ResultSet nhRs, clRs, spRs;

	@Override
	public ResultSet getSpRs() {
		return spRs;
	}

	@Override
	public void setSpRs(ResultSet spRs) {
		this.spRs = spRs;
	}

	@Override
	public ResultSet getNhRs() {
		return nhRs;
	}

	@Override
	public void setNhRs(ResultSet nhRs) {
		this.nhRs = nhRs;
	}

	@Override
	public ResultSet getClRs() {
		return clRs;
	}

	@Override
	public void setClRs(ResultSet clRs) {
		this.clRs = clRs;
	}

	Hashtable<String, String> clPtCk = new Hashtable<String, String>();

	Hashtable<String, String> nhPtCk = new Hashtable<String, String>();

	public Hashtable<String, String> getClPtCk() {
		return clPtCk;
	}

	public void setClPtCk(Hashtable<String, String> clPtCk) {
		this.clPtCk = clPtCk;
	}

	public Hashtable<String, String> getNhPtCk() {
		return nhPtCk;
	}

	public void setNhPtCk(Hashtable<String, String> nhPtCk) {
		this.nhPtCk = nhPtCk;
	}

}
