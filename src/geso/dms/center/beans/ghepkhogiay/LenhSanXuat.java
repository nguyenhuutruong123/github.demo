package geso.dms.center.beans.ghepkhogiay;

import geso.dms.center.db.sql.dbutils;

import java.sql.SQLException;

public class LenhSanXuat {
	private String id;
	private int soLuongToHop;
	private String thoiGianHoanTatDuKien;
	private String thoiGianHoanTat;
	private String trangThai;
	private String maySanXuatId;
	private String tenMaySanXuat;
	private boolean duyet;
	private ToHopN toHop;
	
	public LenhSanXuat()
	{
		this.id = "";
		this.soLuongToHop = 0;
		this.thoiGianHoanTatDuKien = "";
		this.thoiGianHoanTat = "";
		this.trangThai = "";
		this.maySanXuatId = "";
		this.tenMaySanXuat = "";
		this.duyet = false;
		this.toHop = new ToHopN();
	}
	
	public boolean chot()
	{
		if (this.id.trim().length() == 0)
			return false;

		dbutils db = new dbutils();
		try {
			db.getConnection().setAutoCommit(false);
			String query = "";
			//Thay doi trang thai lenh san xuat
			query = "UPDATE LENHSANXUAT SET TINHTRANG = '1', THOIGIANHOANTAT = dbo.GetLocalDate(DEFAULT)  WHERE PK_SEQ = " + this.id + ";";
			
			for (SanPham sanPham : this.toHop.getSanPhamList())
			{
				int soLuongSanPham = sanPham.getSoLuong() * this.getSoLuongToHop();
				//Cap nhat so san pham can san xat trong don hang (khi nao = 0 thi la san xuat xong)
				query += "UPDATE DONHANG_SANPHAMSPIP SET SLCSX = abs(SLCSX) - " 
				+ Math.abs(soLuongSanPham) 
				+ " WHERE DONHANG_FK = " + sanPham.getIdDonHang() 
				+ " AND SANPHAM_FK = " + sanPham.getId() + ";\n";
				//Cap nhat so luong san pham trong kho
				query += "UPDATE ERP_KHOTT_SANPHAM " + 
				" SET SOLUONG = SOLUONG + " + soLuongSanPham + ", AVAILABLE = AVAILABLE + " + soLuongSanPham +
				" WHERE KHOTT_FK = 100000 AND SANPHAM_FK = " + sanPham.getId() + ";\n";
			}
			
			System.out.println("query lenh san xuat: " + query);
			if (query.trim().length() > 0)
				if (!db.update(query)) {	
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					return false;
				}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				db.getConnection().setAutoCommit(true);
				db.shutDown();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return true;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getSoLuongToHop() {
		return soLuongToHop;
	}
	public void setSoLuongToHop(int soLuongToHop) {
		this.soLuongToHop = soLuongToHop;
	}
	public String getThoiGianHoanTatDuKien() {
		return thoiGianHoanTatDuKien;
	}
	public void setThoiGianHoanTatDuKien(String thoiGianHoanTatDuKien) {
		this.thoiGianHoanTatDuKien = thoiGianHoanTatDuKien;
	}
	public String getThoiGianHoanTat() {
		return thoiGianHoanTat;
	}
	public void setThoiGianHoanTat(String thoiGianHoanTat) {
		this.thoiGianHoanTat = thoiGianHoanTat;
	}
	public String getTrangThai() {
		return trangThai;
	}
	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}
	public String getMaySanXuatId() {
		return maySanXuatId;
	}
	public void setMaySanXuatId(String maySanXuatId) {
		this.maySanXuatId = maySanXuatId;
	}
	public ToHopN getToHop() {
		return toHop;
	}
	public void setToHop(ToHopN toHop) {
		this.toHop = toHop;
	}

	public void setDuyet(boolean duyet) {
		this.duyet = duyet;
	}

	public boolean getDuyet() {
		return duyet;
	}

	public void setTenMaySanXuat(String tenMaySanXuat) {
		this.tenMaySanXuat = tenMaySanXuat;
	}

	public String getTenMaySanXuat() {
		return tenMaySanXuat;
	}
}
