package geso.dms.center.beans.nhomskus.imp;

import java.io.Serializable;
import java.sql.ResultSet;

import geso.dms.center.beans.nhomskus.INhomSkus;
import geso.dms.center.db.sql.dbutils;

public class NhomSkus implements INhomSkus, Serializable {
	private static final long serialVersionUID = -9217977546733690415L;
	String id;
	String ten;
	String ma;
	String trangthai;
	String ngaytao;
	String ngaysua;
	String nguoitao;
	String nguoisua;
	String msg;
	ResultSet nskusList;
	ResultSet nskusSelected;
	ResultSet spList;
	ResultSet spSelected;

	String[] nhom;
	String[] sanpham;
	boolean search = false;
	dbutils db;

	public NhomSkus(String[] param) {
		this.id = param[0];
		this.ten = param[1];
		this.ma = param[2];
		this.trangthai = param[3];
		this.ngaytao = param[4];
		this.ngaysua = param[5];
		this.nguoitao = param[6];
		this.nguoisua = param[7];
		this.msg = "";
		this.db = new dbutils();
	}

	public NhomSkus() {
		this.id = "";
		this.ten = "";
		this.ma = "";
		this.trangthai = "";
		this.ngaytao = "";
		this.ngaysua = "";
		this.nguoitao = "";
		this.nguoisua = "";
		this.msg = "";
		this.db = new dbutils();
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTen() {
		return this.ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
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

	public ResultSet getSpList() {
		return this.spList;
	}

	public void setSpList(ResultSet spList) {
		this.spList = spList;
	}

	public ResultSet getSpSelected() {
		return this.spSelected;
	}

	public void setSpSelected(ResultSet spSelected) {
		this.spSelected = spSelected;
	}

	public String[] getNhomsp() {
		return this.nhom;
	}

	public void setNhomsp(String[] nhom) {
		this.nhom = nhom;
	}

	public String[] getSanpham() {
		return this.sanpham;
	}

	public void setSanpham(String[] sanpham) {
		this.sanpham = sanpham;
	}

	public String getMa() {
		return this.ma;
	}

	public void setMa(String ma) {
		this.ma = ma;
	}

	public ResultSet getNSkusList() {
		return this.nskusList;
	}

	public void setNSkusList(ResultSet skusList) {
		this.nskusList = skusList;
	}

	public String[] getNhomSkus() {
		return this.nhom;
	}

	public void setNhomSkus(String[] nhom) {
		this.nhom = nhom;
	}

	public boolean saveNewNSkus() {
		if (!valid()) {
			return false;
		}
		
		String command;

		try {
			this.db.getConnection().setAutoCommit(false);

			command = "insert into nhomskus(ma, ten, ngaytao, ngaysua, nguoitao, nguoisua, trangthai) " + " values('"
					+ this.ma + "', '" + this.ten + "','" + this.ngaytao + "', '" + this.ngaysua + "', '"
					+ this.nguoitao + "', '" + this.nguoisua + "','" + this.trangthai + "')";

			System.out.println("1.Insert Nhom skus: " + command);
			if (!this.db.update(command)) {
				this.msg = "Khong the tao moi nhom skus: " + command;
				db.getConnection().rollback();
				return false;
			}

			command = "select IDENT_CURRENT('nhomskus') as nskusId";
			ResultSet rs = this.db.get(command);
			rs.next();
			this.id = rs.getString("nskusId");

			if (this.sanpham != null) {
				String[] sanphamList = this.sanpham;
				int size = (this.sanpham).length;
				int m = 0;

				while (m < size) {
					command = "insert into nhomskus_sanpham(sp_fk, nskus_fk) values('" + sanphamList[m] + "', '"
							+ this.id + "')";
					System.out.println("2.Insert nhomskus_sanpham: " + command);

					if (!this.db.update(command)) {
						this.msg = "Khong the tao moi nhomskus_sanpham: " + command;
						System.out.println("11.Eroor: " + this.msg);
						db.getConnection().rollback();
						return false;
					}
					m++;
				}
			}
			
			command = "insert into nhanvien_nhomskus(nhanvien_fk,nhomskus_fk) values ('" + this.nguoitao + "', '" + this.id + "') ";
			System.out.println("3.Insert nhanvien_nhomskus: " + command);
			if (!this.db.update(command)) {
				this.msg = "Khong the tao moi nhanvien_nhomskus: " + command;
				System.out.println("11.Eroor: " + this.msg);
				db.getConnection().rollback();
				return false;
			}
			

			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		} catch (Exception e1) {
			System.out.println("Exception: " + e1.getMessage());
		}

		return true;
	}

	public boolean updateNSkus() {
		if (!valid()) {
			return false;
		}
		
		String command;
		try {
			this.db.getConnection().setAutoCommit(false);
			
			command = "update nhomskus set ma = '" + this.ma + "', ten = '" + this.ten
					+ "', ngaysua ='" + this.ngaysua + "', trangthai ='" + this.trangthai
					+ "',nguoisua ='" + this.nguoisua + "' where pk_seq = '" + this.id + "'";
			System.out.println("update : " + command);
			if (!this.db.update(command)) {
				this.msg = "Khong the tao cap nhat skus: " + command;
				db.getConnection().rollback();
				return false;
			}

			command = "delete from nhomskus_sanpham where nskus_fk = '" + this.id + "'";
			if (!this.db.update(command)) {
				this.msg = "Khong the xoa nhomskus_sanpham: " + command;
				db.getConnection().rollback();
				return false;
			}

			if (this.sanpham != null) {
				String[] sanphamList = this.sanpham;
				int size = (this.sanpham).length;
				int m = 0;

				while (m < size) {
					command = "insert into nhomskus_sanpham(sp_fk, nskus_fk) values('" + sanphamList[m] + "', '"
							+ this.id + "')";
					System.out.println("2.Insert nhomskus_sanpham: " + command);

					if (!this.db.update(command)) {
						this.msg = "Khong the tao moi nhomskus_sanpham: " + command;
						System.out.println("11.Eroor: " + this.msg);
						db.getConnection().rollback();
						return false;
					}
					m++;
				}
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		} catch (Exception e1) {
			System.out.println("Exception: " + e1.getMessage());
		}
		return true;
	}

	private void createSpRS() {
		String query;
		String temp = "";
		if (this.id.length() > 0) {
			if (this.sanpham != null) {
				query = "select pk_seq, ma, ten from sanpham where";
				temp = "select pk_seq from sanpham where";
				for (int i = 0; i < this.sanpham.length; i++) {
					if (i == 0) {
						query += " pk_seq = '" + this.sanpham[i] + "'";
						temp += " pk_seq = '" + this.sanpham[i] + "'";
					} else {
						query += " or pk_seq = '" + this.sanpham[i] + "'";
						temp += " or pk_seq = '" + this.sanpham[i] + "'";
					}
				}
			} else {
				query = "select a.pk_seq, a.ma, a.ten from sanpham a, nhomskus_sanpham b where a.pk_seq = b.sp_fk and b.nskus_fk = '"
						+ this.id + "'";
			}

			System.out.println("Sanpham selected: " + query);
			this.spSelected = this.db.get(query);
		} else {
			if (this.sanpham != null) {
				query = "select pk_seq, ma, ten from sanpham where";
				temp = "select pk_seq from sanpham where";
				for (int i = 0; i < this.sanpham.length; i++) {
					if (i == 0) {
						query += " pk_seq = '" + this.sanpham[i] + "'";
						temp += " pk_seq = '" + this.sanpham[i] + "'";
					} else {
						query += " or pk_seq = '" + this.sanpham[i] + "'";
						temp += " or pk_seq = '" + this.sanpham[i] + "'";
					}
				}
				
				System.out.println("Sanpham selected: " + query);
				this.spSelected = this.db.get(query);
			}
		}

		if (this.id.length() > 0) {
			query = "select pk_seq, ma, ten from sanpham  where trangthai = '1' and pk_seq not in (select sp_fk from nhomskus_sanpham where nskus_fk = '"
					+ this.id + "')";
			query = "select pk_seq, ma, ten from sanpham  where trangthai = '1' and pk_seq not in( select sp_fk from NHOMSKUS_SANPHAM)";
		} else {
			query = "select pk_seq, ma, ten from sanpham  where trangthai = '1' and pk_seq not in( select sp_fk from NHOMSKUS_SANPHAM)";
		}
		if (this.sanpham != null) {
			query += " and pk_seq not in (" + temp + ")";
		}
		query = query + " order by ten";
		
		System.out.println("Sanpham list: " + query);
		this.spList = this.db.get(query);

	}

	public void UpdateRS() {
		createSpRS();
	}
	
	public boolean valid() {
		if(this.ma == null || this.ma.trim().equals("")) {
			this.msg = "Mã không được bỏ trống";
			return false;
		}
		if(this.ten == null || this.ten.trim().equals("")) {
			this.msg = "Tên không được bỏ trống";
			return false;
		}
		try {
			if (this.id == null || this.id.trim().equals("")) {
				String query = "select count(*) as sodong from NHOMSKUS where ma = '"+this.ma+"'";
				ResultSet rs = this.db.get(query);
				int sodong = 0;
				if (rs.next()) {
					sodong = rs.getInt("sodong");
				}
				if (sodong > 0) { 
					this.msg = "Mã này đã được sử dụng, vui lòng dùng mã khác";
					return false;
				}
			} else {
				String query = "select count(*) as sodong from NHOMSKUS where ma = '"+this.ma+"'"
						+ " and pk_seq <> " + this.id.trim();;
				ResultSet rs = this.db.get(query);
				int sodong = 0;
				if (rs.next()) {
					sodong = rs.getInt("sodong");
				}
				if (sodong > 0) { 
					this.msg = "Mã này đã được sử dụng, vui lòng dùng mã khác";
					return false;
				}
			}
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			return false;
		}
		return true;
	}
}
