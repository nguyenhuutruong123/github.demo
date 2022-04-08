package geso.dms.center.beans.ghepkhogiay;

import geso.dms.center.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LenhSanXuatList {
	private String maySanXuatId;
	private String tuNgay;
	private String denNgay;
	private String lenhSanXuatId;
	private String toHopId;
	private String kichBanId;
	private List<LenhSanXuat> lenhSanXuatList;
	private List<MaySanXuat> maySanXuatList; 
	dbutils db;
	
	public LenhSanXuatList()
	{
		this.maySanXuatId = "";
		this.tuNgay = "";
		this.denNgay = "";
		this.lenhSanXuatId = "";
		this.toHopId = "";
		this.kichBanId = "" ;
		this.lenhSanXuatList = new ArrayList<LenhSanXuat>();
		this.maySanXuatList = new ArrayList<MaySanXuat>();
		this.db = new dbutils();
	}
	
	public LenhSanXuat getLenhSanXuatById(String id)
	{
		for (LenhSanXuat lenhSanXuat : this.lenhSanXuatList)
		{
			if (lenhSanXuat.getId().trim().equals(id) == true)
				return lenhSanXuat;
		}
		return null;
	}
	
	public void init()
	{
		if (this.lenhSanXuatList != null)
			this.lenhSanXuatList.clear();
		if (this.maySanXuatList != null)
			this.maySanXuatList.clear();
		
		getMaySanXuatFromDB();
		
		String query = null;
		if (this.maySanXuatId.trim().length() > 0)
		{
			query = "SELECT LSX.PK_SEQ, LSX.TOHOP_FK, LSX.TRONGLUONG, LSX.TINHTRANG, LSX.SOLUONGTOHOP, LSX.THOIGIANHOANTAT, LSX.THOIGIANHOANTAT_DUKIEN, \n" +
			" TH.HAOHUT, TH.SOLUONG, TH.KHONGUYENLIEU, TH.DINHLUONG, LSX.TINHTRANG, LSX.TOHOP_FK, MSX.TEN \n" +
//			" ISNULL(KH.TEN, '') AS TENKHACHHANG, THCT.SANPHAM_FK, THCT.TILE, THCT.SOLUONG, THCT.DONHANG_FK, \n" +
//			" SP.DinhLuong, SP.KhoGiay, SP.NHANHANG_FK, NH.TEN AS TENNHANHANG, CL.TEN AS TENCHUNGLOAI, SP.MA \n" +
			" FROM LENHSANXUAT LSX \n" +
			" INNER JOIN TOHOP TH ON TH.PK_SEQ = LSX.TOHOP_FK \n" +
			" INNER JOIN MAYSANXUAT MSX ON MSX.PK_SEQ = LSX.MAYSANXUAT_FK \n" + 
//			" INNER JOIN TOHOP_CHITIET THCT ON THCT.TOHOP_FK = TH.PK_SEQ \n" +
//			" INNER JOIN SANPHAM SP ON SP.PK_SEQ = THCT.SANPHAM_FK \n" +
//			" INNER JOIN DONHANGIP DH ON DH.PK_SEQ = THCT.DONHANG_FK \n" +
//			" INNER JOIN KHACHHANG KH ON KH.PK_SEQ = DH.KHACHHANG_FK \n" +
//			" LEFT JOIN NHANHANG NH ON NH.PK_SEQ = SP.NHANHANG_FK \n" +
//			" LEFT JOIN CHUNGLOAI CL ON SP.CHUNGLOAI_FK = CL.PK_SEQ \n" +
			" WHERE LSX.MAYSANXUAT_FK = " + this.maySanXuatId;
			if (this.tuNgay.trim().length() > 0)
				query += " AND THOIGIANHOANTAT_DUKIEN >= '" + this.tuNgay + "' \n";
			if (this.tuNgay.trim().length() > 0)
				query += " AND THOIGIANHOANTAT_DUKIEN <= '" + this.denNgay + "' \n";
			
			if (this.lenhSanXuatId.trim().length() > 0)
				query += " AND LSX.PK_SEQ = " + this.lenhSanXuatId + " \n";
			
			if (this.toHopId.trim().length() > 0)
				query += " AND LSX.TOHOP_FK = " + this.toHopId + " \n";
			
			if (this.kichBanId.trim().length() > 0)
				query += " AND TH.KICHBAN_FK = " + this.kichBanId + " \n";
			
			System.out.println("Query get lenh san xuat list: " + query);
			ResultSet rs = this.db.get(query); 
			if (rs != null)
			{
				try {
					while (rs.next())
					{
						LenhSanXuat lenhSanXuat = new LenhSanXuat();
						this.lenhSanXuatList.add(lenhSanXuat);
						lenhSanXuat.setId(rs.getString("PK_SEQ"));
						lenhSanXuat.setMaySanXuatId(this.maySanXuatId);
						lenhSanXuat.setTenMaySanXuat(rs.getString("TEN"));
						lenhSanXuat.setSoLuongToHop(rs.getInt("SOLUONGTOHOP"));
						lenhSanXuat.setThoiGianHoanTat(rs.getString("THOIGIANHOANTAT") == null ? "" : rs.getString("THOIGIANHOANTAT"));
						lenhSanXuat.setThoiGianHoanTatDuKien(rs.getString("THOIGIANHOANTAT_DUKIEN").split(" ")[0]);
						lenhSanXuat.setTrangThai(rs.getString("TINHTRANG"));
						//to hop
						ToHopN toHop = new ToHopN();
						lenhSanXuat.setToHop(toHop);
						toHop.setId(rs.getString("TOHOP_FK"));
						toHop.setMaySanXuatId(this.maySanXuatId);
						toHop.setHaoHut(rs.getFloat("HAOHUT"));
						toHop.setSoLuong(rs.getInt("SOLUONG"));
						toHop.setKhoGiayNguyenLieu(rs.getFloat("KHONGUYENLIEU"));
						toHop.setDinhLuong(rs.getFloat("DINHLUONG"));
						//To hop chi tiet
						query = "SELECT ISNULL(KH.TEN, '') AS TENKHACHHANG, THCT.SANPHAM_FK, THCT.TILE, THCT.SOLUONG, THCT.DONHANG_FK,"
							+ "	SP.DinhLuong, SP.KhoGiay, SP.NHANHANG_FK, NH.TEN AS TENNHANHANG, CL.TEN AS TENCHUNGLOAI, SP.MA " 
							+ " FROM TOHOP_CHITIET THCT " 
							+ " INNER JOIN SANPHAM SP ON SP.PK_SEQ = THCT.SANPHAM_FK "
							+ " INNER JOIN DONHANGIP DH ON DH.PK_SEQ = THCT.DONHANG_FK "
							+ " INNER JOIN KHACHHANG KH ON KH.PK_SEQ = DH.KHACHHANG_FK "
							+ " LEFT JOIN NHANHANG NH ON NH.PK_SEQ = SP.NHANHANG_FK"
							+ " LEFT JOIN CHUNGLOAI CL ON SP.CHUNGLOAI_FK = CL.PK_SEQ"
							+ " WHERE TOHOP_FK = " + toHop.getId();
						System.out.println("query chi tiet to hop: " + query);
						ResultSet toHopChiTietRs = this.db.get(query);
						int nChiTiet = 0;
						if (toHopChiTietRs != null)
						{
							try{
								while (toHopChiTietRs.next())
								{
									nChiTiet++;
									SanPham sp = new SanPham();
									sp.setId(toHopChiTietRs.getString("SANPHAM_FK"));
									sp.setIdDonHang(toHopChiTietRs.getString("DONHANG_FK"));
									sp.setDinhLuong(toHopChiTietRs.getFloat("DINHLUONG"));
									sp.setKhoGiay(toHopChiTietRs.getFloat("KHOGIAY"));
									sp.setSoLuong(toHopChiTietRs.getInt("SOLUONG"));
									sp.setTiLe(toHopChiTietRs.getInt("TILE"));
									sp.setTenKhachHang(toHopChiTietRs.getString("TENKHACHHANG"));
									sp.setIdNhanHang(toHopChiTietRs.getString("NHANHANG_FK"));
									sp.setTenNhanHang(toHopChiTietRs.getString("TENNHANHANG"));
									sp.setChungLoai(toHopChiTietRs.getString("TENCHUNGLOAI"));
									sp.setMa(toHopChiTietRs.getString("MA"));
									toHop.getSanPhamList().add(sp);
								}
							}catch (Exception e) {
								e.printStackTrace();
							}finally{
								toHopChiTietRs.close();
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					try {
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	private void getMaySanXuatFromDB() {
		if (maySanXuatList == null)
			maySanXuatList = new ArrayList<MaySanXuat>();
		String query = "select MSX.PK_SEQ, MSX.MA, MSX.TEN, MSX.KHOGIAYNGUYENLIEU, MSX.CONGSUATMAY * 1000 AS CONGSUATMAY, \n" +  
		"ISNULL((SELECT MAX(LSX.STT) FROM LENHSANXUAT LSX WHERE LSX.MAYSANXUAT_FK = MSX.PK_SEQ), 0) AS STT \n" +
		",ISNULL( \n" +
		"	(SELECT ISNULL(LSX1.THOIGIANHOANTAT, LSX1.THOIGIANHOANTAT_DUKIEN)  \n" +
		"	FROM LENHSANXUAT LSX1  \n" +
		"	WHERE LSX1.MAYSANXUAT_FK = MSX.PK_SEQ AND LSX1.STT =  \n" +
		"		(SELECT MAX(LSX.STT)  \n" +
		"		FROM LENHSANXUAT LSX  \n" +
		"		WHERE LSX.MAYSANXUAT_FK = MSX.PK_SEQ)), dbo.GetLocalDate(DEFAULT)) \n" +
		"AS NGAY \n" +
		",ISNULL( \n" +
		"	(SELECT SUM(LSX1.TRONGLUONG)  \n" +
		"	FROM LENHSANXUAT LSX1  \n" +
		"	WHERE LSX1.MAYSANXUAT_FK = MSX.PK_SEQ AND LSX1.THOIGIANHOANTAT = \n" +
		"		(SELECT MAX(LSX.THOIGIANHOANTAT ) \n" +
		"		FROM LENHSANXUAT LSX  \n" +
		"		WHERE LSX.MAYSANXUAT_FK = MSX.PK_SEQ)), 0) \n" +
		"AS TRONGLUONG \n" +
		"from MAYSANXUAT MSX WHERE TRANGTHAI = '1' \n";
		System.out.println("query may san xuat: " + query);
		ResultSet rs = db.get(query);
		if (rs != null)
		{
			try{
				while (rs.next())
				{
					MaySanXuat maySanXuat = new MaySanXuat();
					maySanXuatList.add(maySanXuat);
					maySanXuat.setSTTHienTai(rs.getInt("STT"));
					maySanXuat.setTrongLuong(rs.getFloat("TRONGLUONG"));
					maySanXuat.setNgay(rs.getString("NGAY"));
					maySanXuat.setId(rs.getString("PK_SEQ"));
					maySanXuat.setMa(rs.getString("MA"));
					maySanXuat.setTen(rs.getString("TEN"));
					maySanXuat.setKhoGiayNguyenLieu(rs.getFloat("KHOGIAYNGUYENLIEU"));
					maySanXuat.setCongSuatMay(rs.getFloat("CONGSUATMAY"));
					
					//Get trong luong
					query = "SELECT SUM(LSX.TRONGLUONG) AS TRONGLUONG \n" +
					" FROM LENHSANXUAT LSX \n" +
					" WHERE LSX.MAYSANXUAT_FK = " + maySanXuat.getId()+ 
					" AND LSX.THOIGIANHOANTAT_DUKIEN = '" + maySanXuat.getNgay() +"'";
					System.out.println("Query get trong luong may san xuat" + query);
					ResultSet rs1 = db.get(query);
					if (rs1 != null)
					{
						if (rs1.next())
						{
							maySanXuat.setTrongLuong(rs1.getFloat("TRONGLUONG"));
							System.out.println("Trong luong may san xuat: " + maySanXuat.getTrongLuong());
						}
					}
				}
			}catch (Exception ex)
			{
				ex.printStackTrace();
			}finally{
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	
	}



	public String getMaySanXuatId() {
		return maySanXuatId;
	}
	public void setMaySanXuatId(String maySanXuatId) {
		this.maySanXuatId = maySanXuatId;
	}
	public String getTuNgay() {
		return tuNgay;
	}
	public void setTuNgay(String tuNgay) {
		this.tuNgay = tuNgay;
	}
	public String getDenNgay() {
		return denNgay;
	}
	public void setDenNgay(String denNgay) {
		this.denNgay = denNgay;
	}
	public List<LenhSanXuat> getLenhSanXuatList() {
		return lenhSanXuatList;
	}
	public void setLenhSanXuatList(List<LenhSanXuat> lenhSanXuatList) {
		this.lenhSanXuatList = lenhSanXuatList;
	}

	public void setMaySanXuatList(List<MaySanXuat> maySanXuatList) {
		this.maySanXuatList = maySanXuatList;
	}

	public List<MaySanXuat> getMaySanXuatList() {
		return maySanXuatList;
	}
	
	public void DBClose()
	{
		if (this.db != null)
			this.db.shutDown();
	}

	public void setLenhSanXuatId(String lenhSanXuatId) {
		this.lenhSanXuatId = lenhSanXuatId;
	}

	public String getLenhSanXuatId() {
		return lenhSanXuatId;
	}

	public void setToHopId(String toHopId) {
		this.toHopId = toHopId;
	}

	public String getToHopId() {
		return toHopId;
	}
	
	public void setKichBanId(String kichBanId) {
		this.kichBanId = kichBanId;
	}

	public String getKichBanId() {
		return kichBanId;
	}
}