package geso.dms.center.beans.ghepkhogiay;


import geso.dms.distributor.db.sql.dbutils;

import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class KeHoachSanXuatN {
	private String id;
	private String tuNgay;
	private String denNgay;
	private String maMaySanXuat;
	private String nguoiTao;
	private String nguoiSua;
	Hashtable<Float, Hashtable<String,SanPham>> sanPhamDonHangHash;
	private Hashtable<String, SanPham> sanPhamKhoHash;
	private Hashtable<Float, Hashtable<Float, SanPham>> nguyenLieuHash;
	private KichBanN kichBan;
	private KeHoachN keHoach;
	private List<SanPham> sanPhamDonHangList;
	private List<MaySanXuat> maySanXuatList; 
	private ResultSet nguyenLieuRs;
	private String msg;
	dbutils db;
	
	public KeHoachSanXuatN()
	{
		this.id = "";
		this.tuNgay = "";
		this.denNgay = "";
		this.maMaySanXuat = "";
		this.nguoiTao = "";
		this.nguoiSua = ""; 
		this.msg = "";
		sanPhamDonHangHash = new Hashtable<Float, Hashtable<String,SanPham>>();
		nguyenLieuHash = new Hashtable<Float, Hashtable<Float,SanPham>>();
		sanPhamKhoHash = new Hashtable<String, SanPham>();
		setSanPhamKhoHash(new Hashtable<String, SanPham>());
		kichBan = new KichBanN();
		keHoach = new KeHoachN();
		sanPhamDonHangList = new ArrayList<SanPham>();
		maySanXuatList = new ArrayList<MaySanXuat>(); 
		db = new dbutils();
		getMaySanXuatFromDB(this.maySanXuatList);
	}
	
	//Phai sua lai ham init vi co chot
	public void init()
	{
		if (this.id.trim().length() > 0)
		{
			System.out.println("load");
			loadKeHoachSanXuat();
		}else
		{
			System.out.println("tao moi");
			createKeHoachSanXuat();
		}
	}

	private void loadKeHoachSanXuat() {
		System.out.println("loadKeHoachSanXuat");
		String query = "SELECT * FROM KICHBAN WHERE PK_SEQ = " + this.id;
		System.out.println("query kichban: " + query);
		ResultSet rs = this.db.get(query);
		String idToHop = "";
		int nToHop = 0;
		if (rs != null)
		{
			try{
				if (rs.next())
				{
					query = "SELECT PK_SEQ, MAYSANXUAT_FK, HAOHUT, SOLUONG, KHONGUYENLIEU, DINHLUONG FROM TOHOP WHERE KICHBAN_FK = " + this.id;
					System.out.println("query toHop: " + query);
					ResultSet toHopRs = this.db.get(query);
					if (toHopRs != null)
					{
						while (toHopRs.next())
						{
							nToHop++;
							ToHopN toHop = new ToHopN();
							idToHop = toHopRs.getString("PK_SEQ");
							toHop.setId(idToHop);
							toHop.setMaySanXuatId(toHopRs.getString("MAYSANXUAT_FK"));
							toHop.setHaoHut(toHopRs.getFloat("HAOHUT"));
							toHop.setSoLuong(toHopRs.getInt("SOLUONG"));
							toHop.setKhoGiayNguyenLieu(toHopRs.getFloat("KHONGUYENLIEU"));
							toHop.setDinhLuong(toHopRs.getFloat("DINHLUONG"));
							query = "SELECT ISNULL(KH.TEN, '') AS TENKHACHHANG, THCT.SANPHAM_FK, THCT.TILE, THCT.SOLUONG, THCT.DONHANG_FK,"
							+ "	SP.DinhLuong, SP.KhoGiay, SP.NHANHANG_FK, NH.TEN AS TENNHANHANG, CL.TEN AS TENCHUNGLOAI, SP.MA " 
							+ " FROM TOHOP_CHITIET THCT " 
							+ " INNER JOIN SANPHAM SP ON SP.PK_SEQ = THCT.SANPHAM_FK "
							+ " INNER JOIN DONHANGIP DH ON DH.PK_SEQ = THCT.DONHANG_FK "
							+ " INNER JOIN KHACHHANG KH ON KH.PK_SEQ = DH.KHACHHANG_FK "
							+ " LEFT JOIN NHANHANG NH ON NH.PK_SEQ = SP.NHANHANG_FK"
							+ " LEFT JOIN CHUNGLOAI CL ON SP.CHUNGLOAI_FK = CL.PK_SEQ"
							+ " WHERE TOHOP_FK = " + idToHop;
							System.out.println("query chi tiet to hop: " + query);
							ResultSet toHopChiTietRs = this.db.get(query);
							int nChiTiet = 0;
							if (toHopChiTietRs != null)
							{
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
									toHop.getSanPhamList().add(sp);;
								}
							}
							System.out.println("nChiTiet: "+ nChiTiet);
							keHoach.addToHop(toHop);
						}
						toHopRs.close();
					}
				}
			}catch(Exception ex)
			{
				ex.printStackTrace();
			}finally{
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		keHoach.getToHopMay().size();
		System.out.println("so luong to hop: " + keHoach.getToHopMay().values().size());
		
		keHoach.getToHopMay().size();
	}

	private void createKeHoachSanXuat()
	{	
//		sanPhamDonHangHash = getSanPhamDonHang(); 
//		sanPhamKhoHash = getSanPhamKho();
//		nguyenLieuHash = getNguyenLieu();
//		getMaySanXuatFromDB(this.maySanXuatList);
//		Set<Float> keys = sanPhamDonHangHash.keySet();
//        for(Float key: keys){
//            Hashtable<String, SanPham> tmpHash = sanPhamDonHangHash.get(key);
//            Set<String> spKeys = tmpHash.keySet();
//            for(String key1: spKeys){
//            	SanPham sp = tmpHash.get(key1);
//            	System.out.println("San pham " + sp.getId() + "_dinh luong " + sp.getDinhLuong() + "_nhanHang " + sp.getIdNhanHang() + "_khoGiay " + sp.getKhoGiay() + "_soLuong "+ sp.getSoLuong());
//            }
//        }
       
		this.sanPhamDonHangList = getSanPhamDonHangListDB();
       createKichBan();
//       inSanPhamList(sanPhamDonHangList);
       //Tao ke hoach (da phan bo san pham vao don hang)
       keHoach.init(kichBan, sanPhamDonHangList);
       //Phan bo tung to hop trong ke hoach vao may san xuat
//       keHoach.phanBoMaySanXuat(this.maySanXuatList);
//       inKeHoach(keHoach);
	}

	public boolean chot()
	{
		if (this.id == null || this.id.trim().length() == 0)
			return false;
		
		try {
			this.db.getConnection().setAutoCommit(false);
			String query = "UPDATE KICHBAN SET TRANGTHAI = '1' WHERE PK_SEQ = " + this.id + "; \n";
			for (MaySanXuat maySanXuat : this.maySanXuatList)
			{
				float khoGiayNguyenLieu = maySanXuat.getKhoGiayNguyenLieu();
				
				Hashtable<Float, KichBanN> toHopMay = keHoach.getToHopMay();
				KichBanN kichBanTmp = toHopMay.get(khoGiayNguyenLieu);
				List<ToHopN> toHopList = null;
				if (kichBanTmp != null)
					toHopList = kichBanTmp.getToHopList();
				int stt = maySanXuat.getSTTHienTai() + 1;
//				float trongLuongHienTai = maySanXuat.getTrongLuong();
//				String ngaySxHienTai = maySanXuat.getNgay();
				
				if (toHopList != null)
				for (ToHopN toHop : toHopList)
				{
					if (toHop.getMaySanXuatId().trim().equals(maySanXuat.getId()) == true)
					{
						float trongLuongTrong = maySanXuat.getCongSuatMay() - maySanXuat.getTrongLuong();
//						String thoiGianHoanTat = "";
						int soLuongToHop = toHop.getSoLuong();
//						float trongLuongTohop = toHop.getTrongLuong();
						//Neu lenh san xuat can tach to hop
						if (trongLuongTrong < toHop.getTrongLuong() * toHop.getSoLuong())
							while (toHop.getSoLuong() > 0 &&  maySanXuat.getCongSuatMay() - maySanXuat.getTrongLuong() < toHop.getTrongLuong() * toHop.getSoLuong())
							{
								soLuongToHop = (int) ((maySanXuat.getCongSuatMay() - maySanXuat.getTrongLuong()) / toHop.getTrongLuong());
								if (soLuongToHop > toHop.getSoLuong())
									soLuongToHop = toHop.getSoLuong();
								System.out.println("(maySanXuat.getCongSuatMay() - maySanXuat.getTrongLuong() / toHop.getTrongLuong());");
								System.out.println("(" + maySanXuat.getCongSuatMay() + " - " + maySanXuat.getTrongLuong() +" / " + toHop.getTrongLuong() + ");");
								//Day lenh san xuat Qua ngay moi
								if (soLuongToHop == 0)
								{
									maySanXuat.setTrongLuong(0);
									trongLuongTrong = maySanXuat.getCongSuatMay() - maySanXuat.getTrongLuong();
									//Tang len 1 ngay
									String sourceDate = maySanXuat.getNgay().substring(0, 10);
									SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
									Date myDate = null;
									try {
										myDate = format.parse(sourceDate);
									} catch (ParseException e) {
										e.printStackTrace();
									}
									Calendar cal = Calendar.getInstance();
							        cal.setTime(myDate);
							        cal.add(Calendar.DATE, 1);
							        myDate = cal.getTime();
							        format.format(myDate);
							        maySanXuat.setNgay(format.format(myDate).substring(0,10));
							        soLuongToHop = (int) (trongLuongTrong / toHop.getTrongLuong());
							        if (soLuongToHop > toHop.getSoLuong())
										soLuongToHop = toHop.getSoLuong();
								}
								maySanXuat.setTrongLuong(maySanXuat.getTrongLuong() + toHop.getTrongLuong() * soLuongToHop);
								toHop.setSoLuong(toHop.getSoLuong() - soLuongToHop);
								query += "INSERT INTO LENHSANXUAT (MAYSANXUAT_FK, TOHOP_FK, TINHTRANG, \n" +
								" SOLUONGTOHOP, THOIGIANHOANTAT_DUKIEN, STT, TRONGLUONG) VALUES( \n" +
								maySanXuat.getId() +
								", " + toHop.getId() +
								", '0', " + soLuongToHop +
								", '" + maySanXuat.getNgay() + 
								"', " + stt + 
								", " + toHop.getTrongLuong() + ");";
								System.out.println("query lenh san xuat tach: " + query);
								maySanXuat.setSTTHienTai(stt);
								stt++;
							}
						else //Khong tach to hop
						{
							query += "INSERT INTO LENHSANXUAT (MAYSANXUAT_FK, TOHOP_FK, TINHTRANG, \n" +
							" SOLUONGTOHOP, THOIGIANHOANTAT_DUKIEN, STT, TRONGLUONG) VALUES( \n" +
							toHop.getMaySanXuatId() +
							", " + toHop.getId() +
							", '0', " + toHop.getSoLuong() +
							", '" + maySanXuat.getNgay() + 
							"', " + stt + 
							", " + toHop.getTrongLuong() + ");";
							System.out.println("query lenh san xuat ko tach: " + query);
							maySanXuat.setSTTHienTai(stt);
							maySanXuat.setTrongLuong(maySanXuat.getTrongLuong() + toHop.getTrongLuong() * soLuongToHop);
							toHop.setSoLuong(0);
							stt++;
						}
						//Cap nhat ngay hoan thanh du kien cua tung san pham trong to hop
						for (SanPham sanPham : toHop.getSanPhamList())
						{
							query += "UPDATE DONHANG_SANPHAMSPIP \n" +  
							" SET THOIGIANHOANTAT_DUKIEN = '" + maySanXuat.getNgay() + "' \n" + 
							" WHERE DONHANG_FK = " + sanPham.getIdDonHang() + " AND SANPHAM_FK = " + sanPham.getId() + ";\n";
						}
						//Cap nhat ngay hoan thanh du kien cua don hang co san pham trong to hop
						for (SanPham sanPham : toHop.getSanPhamList())
						{
							query += "UPDATE DONHANGIP \n" +  
							" SET THOIGIANHOANTAT_DUKIEN = '" + maySanXuat.getNgay() + "' \n" + 
							" WHERE PK_SEQ = " + sanPham.getIdDonHang() + " AND THOIGIANHOANTAT_DUKIEN < '" + maySanXuat.getNgay() + "';\n";
						}
					}
				}
			}
			System.out.println("query lenh san xuat: " + query);
			if (query.trim().length() > 0)
				if (!this.db.update(query)) {	
					geso.dms.center.util.Utility.rollback_throw_exception(this.db);
					this.msg = "Không thể lưu kế hoạch sản xuất";
					return false;
				}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				this.db.getConnection().setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return true;
	}
	
	public boolean save()
	{
		boolean result = true;
		
		try {
			this.db.getConnection().setAutoCommit(false);
			String query = "INSERT INTO KICHBAN (TRANGTHAI, NGAYTAO, NGAYSUA, NGAYKEHOACH, TONGHAOHUT, NGUOITAO, NGUOISUA) " +
							" VALUES ('0', dbo.GetLocalDate(DEFAULT), getdate(), getdate(), " + 0 + ", " + this.nguoiTao + ", " + this.nguoiSua + ")";
			System.out.println("query insert: " + query);
			if (!this.db.update(query)) {
					geso.dms.center.util.Utility.rollback_throw_exception(this.db);
					this.msg = "Không thể lưu kế hoạch sản xuất";
					return false;
			}

			// lay cai id cua KICHBAN
			query = "select IDENT_CURRENT('KICHBAN') as kbIdCurrent";

			ResultSet rsPxk = db.get(query);
			if (rsPxk.next()) {
				this.id = rsPxk.getString("kbIdCurrent");
				if (saveNewToHop() == false)
				{
					geso.dms.center.util.Utility.rollback_throw_exception(this.db);
					this.msg = "Khong the luu ke hoach san xuat";
					System.out.println("Khong the luu ke hoach san xuat");
					return false;
				}
			}
			rsPxk.close();

			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);

		} catch (Exception er) {
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			er.printStackTrace();
			return false;
		}
		return result;
	}
	
	private boolean saveNewToHop() {
		Set<Float> toHopMayKeys = this.keHoach.getToHopMay().keySet();
		for (float khoGiayNLMay : toHopMayKeys)
		{
//			Hashtable<Float, ToHopN> toHopHash = this.keHoach.getToHopMay().get(khoGiayNLMay).getToHopHash();
			List<ToHopN> toHopList = this.keHoach.getToHopMay().get(khoGiayNLMay).getToHopList();
//			Set<Float> toHopHashKeys = toHopHash.keySet();
		    for (ToHopN toHop : toHopList)
		    {
//		    	ToHopN toHop = toHopHash.get(key);
		    	String query = "INSERT INTO TOHOP(KICHBAN_FK, MAYSANXUAT_FK, HAOHUT, SOLUONG,KHONGUYENLIEU, DINHLUONG) " 
		    	+ " VALUES (" 
		    	+ this.id + ", " 
		    	+ toHop.getMaySanXuatId() + ", " 
		    	+ toHop.getHaoHut() + ", " 
		    	+ toHop.getSoLuong() + ", " 
		    	+ toHop.getKhoGiayNguyenLieu()+ ", " 
		    	+ toHop.getDinhLuong() + ")";
		    	
		    	if (!this.db.update(query)) {
					return false;
				}
		    	// lay cai id cua tohop
				query = "select IDENT_CURRENT('TOHOP') as thIdCurrent";

				ResultSet rsPxk = db.get(query);
				try {
					if (rsPxk.next()) {
						String idToHop = rsPxk.getString("thIdCurrent");
//						Hashtable<Float, SanPham> sanPhamHash = toHop.getSanPhamHash();
						List<SanPham> sanPhamList = toHop.getSanPhamList();
//						Set<Float> sanPhamKeys = sanPhamHash.keySet();
						for (SanPham sanPham : sanPhamList)
						{
//							SanPham sanPham = sanPhamHash.get(sanPhamKey);
							query = "INSERT INTO TOHOP_CHITIET(TOHOP_FK, SANPHAM_FK, TILE, SOLUONG, DONHANG_FK) "
							+ " VALUES (" + idToHop + ", " 
//							+ sanPham.getId() + ", "
							+ (sanPham.getId().trim().length() != 0 ? sanPham.getId().trim() : ("(SELECT PK_SEQ FROM SANPHAM WHERE MA = '" + sanPham.getMa() + "', ")) + ", "
							+ sanPham.getTiLe() + ", " 
							+ sanPham.getSoLuong() + ", " 
							+ sanPham.getIdDonHang() + ")";
							
							System.out.println("query in sert san pham: " + query);
							
							if (!this.db.update(query)) {
								return false;
							}
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}finally{
					try {
						rsPxk.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
		    }
		}
		return true;
	}

	public void xuatExcell(OutputStream outstream, String templateFileName)
	{
		keHoach.xuatExcell(outstream, templateFileName, this.maySanXuatList);
	}
	
	public void importFromExcell(String fileName)
	{
		keHoach.importFromExcell(fileName, this.db, this.maySanXuatList);
	}
	
	private void getMaySanXuatFromDB(List<MaySanXuat> maySanXuatList) {
		if (maySanXuatList == null)
			maySanXuatList = new ArrayList<MaySanXuat>();
//		String query = "select MSX.PK_SEQ, MSX.MA, MSX.TEN, MSX.KHOGIAYNGUYENLIEU, MSX.CONGSUATMAY, " +
//		" ISNULL((SELECT MAX(LSX.STT) FROM LENHSANXUAT LSX WHERE LSX.MAYSANXUAT_FK = MSX.PK_SEQ), 0) AS STT " +
//		" from MAYSANXUAT MSX WHERE TRANGTHAI = '1'"; 
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
					this.maySanXuatList.add(maySanXuat);
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

	private void inSanPhamList(List<SanPham> sanPhamDonHangList2) {
		System.out.println("=====================START SAN PHAM===================");
		for (SanPham sp : sanPhamDonHangList2)
		{
			System.out.println("sp " + sp.getId() + ": " + sp.getSoLuong());
		}
		System.out.println("=====================END SAN PHAM===================");
	}

	private void inKeHoach(KeHoach keHoach) {
		Set<Float> toHopMayKeys = keHoach.getToHopMay().keySet();
		System.out.println("================KE HOACH=================");
		for (float khoGiayNguyenLieu : toHopMayKeys)
		{
			System.out.println("Kho giay nguyen lieu: " + khoGiayNguyenLieu);
			KichBan kb = keHoach.getToHopMay().get(khoGiayNguyenLieu);
			System.out.println("===> Tong hao hut: " + kb.getTongHaoHut());
			Set<Float> keys = kb.getToHopHash().keySet();
			for (float key : keys)
			{
				ToHop th = kb.getToHopHash().get(key);
				System.out.print("Dinh luong: " + th.getDinhLuong() + " || " + th.getSoLuong() + "(" + th.getKhoGiayNguyenLieu() + ") = ");
				Set<Float> keys1 = th.getSanPhamHash().keySet();
				boolean isFirst = true;
				for (float key1 : keys1)
				{
					if (isFirst == true)
					{
						System.out.print(th.getSanPhamHash().get(key1).getSoLuong() + "(" + th.getSanPhamHash().get(key1).getKhoGiay() + ")");
						isFirst = false;
					}
					else
						System.out.print(" + " + th.getSanPhamHash().get(key1).getSoLuong() + "(" + th.getSanPhamHash().get(key1).getKhoGiay() + ")");
				}
				System.out.println(" => hao hut: " + th.getHaoHut() + " x " + th.getSoLuong() + " = " + th.getHaoHut() * th.getSoLuong()  + " || may: " + th.getMaySanXuatId());
			}
		}
	}

	private Hashtable<Float, Float> getKhoGiayMaySanXuat()
	{
		Hashtable<Float, Float> khoGiays = new Hashtable<Float, Float>();
		String query = "select distinct KHOGIAYNGUYENLIEU from MAYSANXUAT";
		ResultSet rs = this.db.get(query);
		if (rs != null)
		{
			try {
				while (rs.next())
				{
					float khoGiay = rs.getFloat("KHOGIAYNGUYENLIEU");
					khoGiays.put(khoGiay, khoGiay);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return khoGiays;
	}
	private void createKichBan() {
		Hashtable<Float, Float> nguyenLieus = getKhoGiayMaySanXuat();
		createNguyenLieuRs();
        try {
			while (this.nguyenLieuRs.next()){
				float dinhLuong = this.nguyenLieuRs.getFloat("DINHLUONG");
				System.out.println("dinhLuong: " + dinhLuong);
				String idNhanHang = this.nguyenLieuRs.getString("NHANHANG_FK");
				String tenMaySanXuat = this.nguyenLieuRs.getString("TENMAYSANXUAT");
				String idMaySanXuat = this.nguyenLieuRs.getString("IDMAYSANXUAT");
				float khoGiayNguyenLieu = this.nguyenLieuRs.getFloat("KHOGIAYNGUYENLIEU");
				//Lay san pham co the ghep voi nhau theo bo ba dinh luong, nhan hang,may san xuat (kho giay nguyen lieu)
				List<SanPham> sanPhamList = createSanPhamList(this.sanPhamDonHangList, dinhLuong, idNhanHang, tenMaySanXuat);
				if (sanPhamList != null)
				{
			    	int n;
			    	float m;
					while (isHaveNumber(sanPhamList) == true) 
					{
						//San pham ghe duoc voi nhau theo bo ba dinh luong, nhan hang, may san xuat => ko co chuyen co 2 kho giay nguyen lieu hop le tro le
						//List san pham cho moi lan xet
						List<SanPham> wList = new ArrayList<SanPham>();
						createWList(wList, sanPhamList, nguyenLieus, khoGiayNguyenLieu);
						n = wList.size();
						m = khoGiayNguyenLieu;
						//Tinh bang phuong an bang phuong phap truy hoi
						float [][] f = optimize(wList, (int)m);
						
						//Truy vet tim kho giay thanh pham toi uu ghep lai cho kho tieu chuan
						ToHopN toHop = trace(wList, f, n, (int)m);
						toHop.setDinhLuong(dinhLuong);
						toHop.setKhoGiayNguyenLieu(khoGiayNguyenLieu);
						toHop.setMaySanXuatId(idMaySanXuat);
						
						//End tim duoc kho giay toi uu
						System.out.println("End tim duoc kho giay toi uu");
//						ToHopN toHop = getValueByMinKey(selectedColection);
						//Da tim duoc kho giay toi uu
						if (toHop != null)
						{
							//Khong quan tam toi so luong nguyen lieu, nguyen lieu luon san sang
							tinhSoLuongToHop(toHop, sanPhamList);
							kichBan.addToHop(toHop);
							truSoLuong(toHop, sanPhamList);
						}
						//remove kho giay tieu chuan neu co so luong == 0
//						removeZero(nguyenLieus);
						
						//remove kho giay thanh pham neu co so luong == 0
						removeZero(sanPhamList);
					}
					System.out.println("End xet san pham don hang con");
				}
				System.out.println("End xet nguyen lieu: ");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //In kich ban
//        inKichBan(kichBan);
	}

	private boolean isXetKhoGiayNguyenLieu(float khoGiayNguyenLieu, Hashtable<String, SanPham> sanPhamDonHangs) {
		Set<String> ids = sanPhamDonHangs.keySet();
		for (String id : ids)
		{
			if (sanPhamDonHangs.get(id).getKhoGiay() > khoGiayNguyenLieu)
				return false;
		}
		return true;
	}

	private void inKichBan(KichBan kichBan) {
		if (kichBan != null)
        {
        	System.out.println("Tong hao hut: " + kichBan.getTongHaoHut());
        	Hashtable<Float, ToHop> toHops = kichBan.getToHopHash();
        	Set<Float> toHopKeys = toHops.keySet();
        	for (float khoGiayNguyenLieu : toHopKeys)
        	{
        		int soLuongToHop = toHops.get(khoGiayNguyenLieu).getSoLuong();
        		System.out.println("Nguyen lieu " + toHops.get(khoGiayNguyenLieu).getKhoGiayNguyenLieu() + ": so luong " + soLuongToHop + "_hao hut " + toHops.get(khoGiayNguyenLieu).getHaoHut());
        		Hashtable<Float, SanPham> sanPhamToHops = toHops.get(khoGiayNguyenLieu).getSanPhamHash();
        		Set<Float> sanPhamToHopsKey = sanPhamToHops.keySet();
//        		boolean isFirst = true;
        		for (float khoGiayToHop : sanPhamToHopsKey)
        		{
        			System.out.println(sanPhamToHops.get(khoGiayToHop).getKhoGiay() 
        					+ ": " + (sanPhamToHops.get(khoGiayToHop).getSoLuong()) * soLuongToHop);
        		}
        	}
        }
	}

	private void removeZero(List<SanPham> sanPhamList) {
		List<SanPham> removeObjectList = new ArrayList<SanPham>();
		for (SanPham sanPham : sanPhamList)
		{
			if (sanPham.getSoLuong() == 0)
				removeObjectList.add(sanPham);
		}
		for (SanPham sanPham : removeObjectList)
		{
			sanPhamList.remove(sanPham);
		}
	}

	int iToHop = 0;
	private void truSoLuong(ToHopN toHop, List<SanPham> sanPhamList) {
		//Tru nguyen lieu
		int soLuongToHop = toHop.getSoLuong();
		//Tru san pham don hang
		List<SanPham> sanPhamToHopList = toHop.getSanPhamList();
		for (SanPham sanPhamToHop : sanPhamToHopList)
		{
			for (SanPham sanPhamDonHang : sanPhamList)
			{
				//Neu san pham trong don hang co cung id,id don hang voi san pham trong tohop dang xet thi tru
				if (sanPhamToHop.compareId_IdDonHang(sanPhamDonHang) == true)
				{
					SanPham spDonHang = sanPhamDonHang;
					SanPham spToHop = sanPhamToHop;
					int soLuongSanPhamTru = spToHop.getSoLuong() * soLuongToHop;
					spDonHang.setSoLuong(sanPhamDonHang.getSoLuong() 
							-  soLuongSanPhamTru);
					if (spDonHang.getSoLuong() < 0)
						System.out.println("to hop: " + iToHop);
				}
			}
		}
		iToHop++;
	}

	private void tinhSoLuongToHop(ToHopN toHop, List<SanPham> sanPhamList) {
//		int minNumber = 0;
		boolean isFirst = true;
//		Hashtable<Float, SanPham> sanPhamToHops = toHop.getSanPhamHash();
		List<SanPham> sanPhamToHopList = toHop.getSanPhamList();
//		Set<Float> sanPhamToHopKeys = sanPhamToHops.keySet();
		//Lay kho giay cua san pham co so luong it nhat trong to hop
//		for (float khoGiay : sanPhamToHopKeys)
//		{
//			if (isFirst == true)
//			{
//				minNumber = sanPhamToHops.get(khoGiay).getSoLuong();
//				isFirst = false;
//			}else
//			if (minNumber > sanPhamToHops.get(khoGiay).getSoLuong())
//			{
//				minNumber = sanPhamToHops.get(khoGiay).getSoLuong();
//			}
//		}
		
		//Tinh ti le
//		Set<String> sanPhamDonHangKeys = sanPhamDonHangs.keySet();//Key la id san pham
		int minPer = 0;
		if (toHop.getHaoHut() == 25)
		{
			System.out.println();
		}
		isFirst = true;
		for (SanPham sanPham : sanPhamList)
		{
			for (SanPham sanPhamToHop : sanPhamToHopList)
				//Neu san pham trong to hop co id va idDonHang giong voi san pham dang xet
				if (sanPhamToHop.compareId_IdDonHang(sanPham) == true)
				{
					if (isFirst == true)
					{
						minPer = sanPham.getSoLuong() / sanPhamToHop.getSoLuong();
						isFirst = false;
					}
					else
					{
//						int soLuongSanPhamDonHang = sanPham.getSoLuong();
//						int soLuongSanPhamToHop = sanPhamToHop.getSoLuong();
						int tmp = sanPham.getSoLuong() / sanPhamToHop.getSoLuong();
						if (tmp < minPer)
						{
//							if (tmp > 0 && tmp < 1)
							minPer = tmp;
						}
					}
				}
		}
//		if (minPer > nguyenLieus.get(toHop.getKhoGiayNguyenLieu()).getSoLuong())
//			minPer = nguyenLieus.get(toHop.getKhoGiayNguyenLieu()).getSoLuong();
		toHop.setSoLuong(minPer);
	}

	private ToHopN getValueByMinKey(Hashtable<Float, ToHopN> selectedColection) {
		ToHopN toHop;
		Set<Float> keys = selectedColection.keySet();
		float khoGiay = 0;
		float haoHutMin = 0;
		boolean isFirts = true;
		for (float key : keys)
		{
			toHop = selectedColection.get(key);
			if (isFirts == true)
			{
				khoGiay = toHop.getKhoGiayNguyenLieu();
				haoHutMin = toHop.getHaoHut();
				isFirts = false;
			}else
			{
				if (haoHutMin > toHop.getHaoHut())
				{
					khoGiay = toHop.getKhoGiayNguyenLieu();
					haoHutMin = toHop.getHaoHut();
				}
			}
		}
//		float khoGiayNguyenLieuToiUu = getKeyMin(selectedColection);
//		toHop = selectedColection.get(khoGiayNguyenLieuToiUu);
		toHop = selectedColection.get(khoGiay);
		return toHop;
	}

	private float getKeyMin(Hashtable<Float, ToHop> selectedColection) {
		Set set = selectedColection.entrySet();
		Iterator i = set.iterator();
		float minKey = 0;
		if(i.hasNext()) {
			Map.Entry me = (Map.Entry)i.next();
			minKey = (Float) me.getKey();
		}
		return minKey;
	}

	private ToHopN trace(List<SanPham> wList, float[][] f, int n, int m) {
		ToHopN toHop = new ToHopN();
//		Hashtable<Float , SanPham> spHash = toHop.getSanPhamHash();
		List<SanPham> sanPhamList = toHop.getSanPhamList();
		while (n != 0)
		{
			if (f[n][m] != f[n -1][m])
			{
				boolean isExit = false;
				for (SanPham sanPham : sanPhamList)
				{
					//Neu san pham co id,id don hang voi 1 san pham da co trong to hop
					if (sanPham.compareId_IdDonHang(wList.get(n - 1)) == true)
					{
						sanPham.setSoLuong(sanPham.getSoLuong() + 1);
						sanPham.setTiLe(sanPham.getSoLuong());
						isExit = true;
					}
				}
				if (isExit == false)
				{
					SanPham sp = new SanPham(wList.get(n - 1));
					sp.setSoLuong(1);
					sp.setTiLe(1);
					sanPhamList.add(sp);
				}
				m = m - (int)wList.get(n - 1).getKhoGiay();
			}
			n--;
		}
		return toHop;
	}

	private float[][] optimize(List<SanPham> wList, int m) {
		int n = wList.size();
		float [][] f = new float[n + 1][(int)m + 1];
		for (int i = 0; i <= m; i++)
		{
			f[0][i] = 0;
		}
		for (int i = 1; i <= n; i++)
		{
			for (int j = 0; j <= m; j++)
			{
				f[i][j] = f[i-1][j];
				int k = (int) (j - wList.get(i - 1).getKhoGiay());
				if (j >= wList.get(i - 1).getKhoGiay() && f[i][j] < f[i - 1][k] + wList.get(i -1).getKhoGiay())
					f[i][j] = f[i - 1][k] + wList.get(i -1).getKhoGiay();
			}
		}
		return f;
	}

	//Tra ve danh sach ten cua may san xuat co kho giay nguyen lieu bang kho giay truyen vao
	private List<String> getTenMaySanXuatListByKhoGiay(float khoGiay)
	{
		List<String> list = new ArrayList<String>();
		for (MaySanXuat maySanXuat : this.maySanXuatList)
		{
			if (maySanXuat.getKhoGiayNguyenLieu() == khoGiay)
				list.add(maySanXuat.getTen());
		}
		return list;
	}
	
	//Sap xep list san pham theo kho giay giam dan
	public void sortSanPhamList(List<SanPham> sanPhamList)
	{
		for (int i = 0; i < sanPhamList.size(); i++)
		{
			SanPham spi = sanPhamList.get(i);
			for (int j = i; j < sanPhamList.size(); j++)
			{
				SanPham spj = sanPhamList.get(j);
				if (spj.compareKhoGiay(spi) == 1)
				{
					SanPham tmp = spi;
					spi = spj;
					spj = tmp;
				}
			}
		}
	}
//	//Kiem tra san pham co san xuat duoc voi kho giay nguyen lieu khong
	private void createWList(List<SanPham> wList, List<SanPham> sanPhamList, Hashtable<Float, Float> nguyenLieus, float khoGiayNguyenLieu) {
		//Sap xep san pham list theo kho giay giam dan
		sortSanPhamList(sanPhamList);
		for (SanPham sanPham : sanPhamList)
		{
			if (sanPham.getSoLuong() > 0)
			{
				float number = (khoGiayNguyenLieu / sanPham.getKhoGiay());
				if (number > 0)
				{
					if (number > sanPham.getSoLuong())
						number = sanPham.getSoLuong();
					for (int h = 0; h < number; h++)
					{
						SanPham sp = new SanPham(sanPham);
						sp.setSoLuong(1);
						wList.add(sp);
					}
				}
			}
		}
	}

	private List<SanPham> convert(Hashtable<Float, SanPham> nguyenLieus) {
		List<SanPham> spList = new ArrayList<SanPham>();
		Set<Float> keys = nguyenLieus.keySet();
		for (float key : keys)
		{
			spList.add(nguyenLieus.get(key));
		}
		return spList;
	}

	private List<MyObject> convertList(Hashtable<Float, SanPham> nguyenLieus) {
		Set<Float> khoGiays = nguyenLieus.keySet();
		List<MyObject> standarList = new ArrayList<MyObject>();
		for (Float khoGiay : khoGiays)
		{
			standarList.add(new MyObject(khoGiay, nguyenLieus.get(khoGiay).getSoLuong()));
		}
		return standarList;
	}

	private float[] tinhHaoHutList(List<List<SanPham>> selectedColection, List<SanPham> standarList) {
		int size = standarList.size();
		float[] haoHutList = new float[size];
		for (int i = 0; i < size; i++)
		{			
			haoHutList[i] = tinhHaoHut(selectedColection.get(i), standarList.get(i).getKhoGiay());
		}
		return haoHutList;
	}

	private float tinhHaoHut(List<SanPham> list, float width) {
		if (list.isEmpty() == true)
			return -1;
		float haoHut = 0;
		for (int i = 0; i < list.size(); i++)
		{
			haoHut += list.get(i).getKhoGiay() * list.get(i).getSoLuong(); 
		}
		haoHut = width - haoHut;
		return haoHut;
	}

	private ToHop createToHop(List<SanPham> gettedList, List<SanPham> standarList, List<SanPham> productList, int standarSizeIndex)
	{
		ToHop toHop = new ToHop();
		int max = 0;
		for (int i = 0; i < gettedList.size(); i++)
		{
			max += gettedList.get(i).getKhoGiay() * gettedList.get(i).getSoLuong(); 
		}
//		int minIndex = 0;
		int minNumber = gettedList.get(0).getSoLuong();
		for (int i = 1; i < gettedList.size(); i++)
		{
			if (minNumber > gettedList.get(i).getSoLuong())
			{
				minNumber = gettedList.get(i).getSoLuong();
//				minIndex = i;
			}
		}
		
		//Tinh ti le
		List<Integer> perList = new ArrayList<Integer>();
		for (int index5 = 0; index5 < productList.size(); index5++)
		{
			for (int index6 = 0; index6 < gettedList.size(); index6++)
				if (gettedList.get(index6).getKhoGiay() == productList.get(index5).getKhoGiay())
				{
					perList.add(productList.get(index5).getSoLuong() / gettedList.get(index6).getSoLuong());
				}
		}
		Collections.sort(perList);
		int minPer = perList.get(0);
		if (minPer > standarList.get(standarSizeIndex).getSoLuong())
			minPer = standarList.get(standarSizeIndex).getSoLuong();
		toHop.setSoLuong(minPer);
		float haoHut = (standarList.get(standarSizeIndex).getKhoGiay() - max) * minPer;
		toHop.setHaoHut(haoHut);
//		standarList.get(standarSizeIndex).setSoLuong(standarList.get(standarSizeIndex).getSoLuong() - minPer);
		
		for (int index5 = 0; index5 < gettedList.size(); index5++)
		{
			SanPham sp = gettedList.get(index5);
			sp.setSoLuong(gettedList.get(index5).getSoLuong() * minPer);
			
//			for (int index6 = 0; index6 < productList.size(); index6++)
//			{
//				if (gettedList.get(index5).width == productList.get(index6).width)
//				{
//					productList.get(index6).n = productList.get(index6).n -  gettedList.get(index5).n * minPer;
//					index6 = productList.size(); 
//				}
//			}
		}
		return toHop;
	}
	
	private static void truList(List<SanPham> gettedList, List<SanPham> standarList, List<SanPham> productList, int standarSizeIndex) {
		int max = 0;
		for (int i = 0; i < gettedList.size(); i++)
		{
			max += gettedList.get(i).getKhoGiay() * gettedList.get(i).getSoLuong(); 
		}
		
		int minIndex = 0;
		int minNumber = gettedList.get(0).getSoLuong();
		for (int i = 1; i < gettedList.size(); i++)
		{
			if (minNumber > gettedList.get(i).getSoLuong())
			{
				minNumber = gettedList.get(i).getSoLuong();
				minIndex = i;
			}
		}
		
		//Tinh ti le
		List<Integer> perList = new ArrayList<Integer>();
		for (int index5 = 0; index5 < productList.size(); index5++)
		{
			for (int index6 = 0; index6 < gettedList.size(); index6++)
				if (gettedList.get(index6).getKhoGiay() == productList.get(index5).getKhoGiay())
				{
					perList.add(productList.get(index5).getSoLuong() / gettedList.get(index6).getSoLuong());
				}
		}
		Collections.sort(perList);
		int minPer = perList.get(0);
		if (minPer > standarList.get(standarSizeIndex).getSoLuong())
			minPer = standarList.get(standarSizeIndex).getSoLuong();
//		float haoHut = (standarList.get(standarSizeIndex).getKhoGiay() - max) * minPer;
		standarList.get(standarSizeIndex).setSoLuong(standarList.get(standarSizeIndex).getSoLuong() - minPer);
		
		for (int index5 = 0; index5 < gettedList.size(); index5++)
		{
			for (int index6 = 0; index6 < productList.size(); index6++)
			{
				if (gettedList.get(index5).getKhoGiay() == productList.get(index6).getKhoGiay())
				{
					productList.get(index6).setSoLuong(productList.get(index6).getSoLuong() -  gettedList.get(index5).getSoLuong() * minPer);
					index6 = productList.size(); 
				}
			}
		}
	}

	//Lay nhung san pham co the ghep voi nhau theo dinh luong, nhan hang va may san xuat
	private List<SanPham> createSanPhamList(List<SanPham> sanPhamDonHangList, float dinhLuong, String idNhanHang, String tenMaySanXuat) {
		List<SanPham> sanPhamList = null;
		boolean isFirst = true;
		for (SanPham sp : sanPhamDonHangList)
		{
			System.out.println("sp: " + sp.getMa());
        	//Neu san pham co cung dinh luong, nhan hang, ten may san xuat moi them vao
        	if (sp.getDinhLuong() == dinhLuong
			&& sp.getIdNhanHang().trim().equals(idNhanHang) == true //nhan hang 
			&& sp.getTenMaySanXuat().trim().equals(tenMaySanXuat) == true) //ten may san xuat
        	{
        		if (isFirst == true)
        		{
        			isFirst = false;
        			sanPhamList = new ArrayList<SanPham>();
        		}
        		sanPhamList.add(sp);
        	}
		}
		return sanPhamList;
	}

	private int getDuongMinIndex(float[] haoHutList) {
		int minIndex = -1;
		for (int i = 0; i < haoHutList.length; i++)
		{
			if (minIndex == -1 && haoHutList[i] >= 0)
				minIndex = i;
			else if (haoHutList[i] >= 0 && minIndex >= 0 && haoHutList[i] < haoHutList[minIndex])
				minIndex = i;
		}
		return minIndex;
	}
	
	private boolean isHaveNumber(List<SanPham> list)
	{
		for (int i = 0; i < list.size(); i++)
		{
			if (list.get(i).getSoLuong() > 0)
				return true;
		}
		return false;
	}
	
	private Hashtable<String, SanPham>  getSanPhamKho()
	{
		Hashtable<String, SanPham> hash = new Hashtable<String, SanPham>();
		String query = "select ksp.SANPHAM_FK as SANPHAMID, ksp.SOLUONG " + 
						" from KHOTT_SANPHAM ksp " +
						" where ksp.KHO_FK = 100003 ";
		ResultSet rs = db.get(query);
		if (rs != null)
		{
			try{
				while (rs.next())
				{
					SanPham sp = new SanPham();
					sp.setId(rs.getString("SANPHAMID"));
					sp.setSoLuong(rs.getInt("SOLUONG"));
					hash.put(sp.getId(), sp);
				}
			}catch (Exception ex)
			{
				ex.printStackTrace();
			}finally{
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return hash;
	} 
	
	//Tra ve san pham dau tien trong list theo id san pham, id don hang, ten khach hang
	public SanPham isExitSanPham(List<SanPham> spList, String id, String idDonHang, String tenKhachHang)
	{
		for (SanPham sp : spList)
		{
			if (id.trim().equals(sp.getId()) == true
			&& idDonHang.trim().equals(sp.getIdDonHang()) == true
			&& tenKhachHang.trim().equals(sp.getTenKhachHang()) == true)
				return sp;
		}
		return null;
	}
	
	//Tra ve san pham dau tien trong list theo id san pham, id don hang, ten khach hang
	public SanPham isExitSanPham(List<SanPham> spList, SanPham sanPham)
	{
		String id = sanPham.getId();
		String idDonHang = sanPham.getIdDonHang();
		String tenKhachHang = sanPham.getTenKhachHang();
		for (SanPham sp : spList)
		{
			if (id.trim().equals(sp.getId()) == true
			&& idDonHang.trim().equals(sp.getIdDonHang()) == true
			&& tenKhachHang.trim().equals(sp.getTenKhachHang()) == true)
				return sp;
		}
		return null;
	}
	
	private Hashtable<Float, Hashtable<String, SanPham>> getSanPhamDonHang()
	{
		Hashtable<Float, Hashtable<String, SanPham>> hash = new Hashtable<Float, Hashtable<String, SanPham>>();
		String query = "SELECT ISNULL(KH.TEN, '') AS TENKHACHHANG, DDH.PK_SEQ AS DONDATHANGID, SP.PK_SEQ AS SANPHAMID, SP.KhoGiay, SP.DinhLuong, SP.MA, DDH_SP.SOLUONG " +
						" , SP.TENMAYSANXUAT, SP.NHANHANG_FK, ISNULL(NH.TEN, '') AS TENNHANHANG, ISNULL(CL.TEN, '') AS TENCHUNGLOAI " + 
						" FROM DONHANGIP DDH " +
						" INNER JOIN DONHANG_SANPHAMSPIP DDH_SP ON DDH_SP.DONHANG_FK = DDH.PK_SEQ " +
						" INNER JOIN SANPHAM SP ON SP.PK_SEQ = DDH_SP.SANPHAM_FK " +
						" LEFT JOIN KHACHHANG KH ON KH.PK_SEQ = DDH.KHACHHANG_FK " +
						" LEFT JOIN NHANHANG NH ON NH.PK_SEQ = SP.NHANHANG_FK " +
						" LEFT JOIN CHUNGLOAI CL ON CL.PK_SEQ = SP.CHUNGLOAI_FK " +
						" WHERE DDH.TRANGTHAI = 1";
		System.out.println("QUERY GET DON HANG SAN PHAM " + query);
		if (this.tuNgay.trim().length() > 0)
			query += " AND DDH.NGAYNHAP >= '" + this.tuNgay + "' ";
		if (this.denNgay.trim().length() > 0)
			query += " AND DDH.NGAYNHAP <= '" + this.denNgay + "' ";
		System.out.println("QUERY GET DON HANG SAN PHAM " + query);
		ResultSet rs = db.get(query);
		if (rs != null)
		{
			try{
				System.out.println("IN SAN PHAM;");
				while (rs.next())
				{
					SanPham sp = new SanPham();
					sp.setId(rs.getString("SANPHAMID"));
					sp.setIdDonHang(rs.getString("DONDATHANGID"));
					sp.setDinhLuong(rs.getFloat("DINHLUONG"));
					sp.setKhoGiay(rs.getFloat("KHOGIAY"));
					sp.setSoLuong(rs.getInt("SOLUONG"));
					sp.setTenKhachHang(rs.getString("TENKHACHHANG"));
					sp.setMa(rs.getString("MA"));
					sp.setTenMaySanXuat(rs.getString("TENMAYSANXUAT"));
					sp.setIdNhanHang(rs.getString("NHANHANG_FK"));
					sp.setTenNhanHang(rs.getString("TENNHANHANG"));
					sp.setChungLoai(rs.getString("TENCHUNGLOAI"));
					SanPham sp2 = new SanPham(sp);
					SanPham spTmp1 = isExitSanPham(this.sanPhamDonHangList, sp2);
					if (spTmp1 == null)//Chua co
						this.sanPhamDonHangList.add(sp2);
					else//TH da co trong list, cong don so luong 
						spTmp1.setSoLuong(spTmp1.getSoLuong() + sp2.getSoLuong());
					if (hash.containsKey(sp.getDinhLuong()) == true)
					{
						Hashtable<String, SanPham> tmpHash = hash.get(sp.getDinhLuong());
						if (tmpHash.containsKey(sp.getId()) == true)
						{
							SanPham spTmp = tmpHash.get(sp.getId());
							spTmp.setSoLuong(spTmp.getSoLuong() + sp.getSoLuong());
						}
						else
						{
							tmpHash.put(sp.getId(), sp);
						}
//						hash.put(sp.getDinhLuong(), tmpHash);
					}
					else
					{
						Hashtable<String, SanPham> tmpHash = new Hashtable<String, SanPham>();
						tmpHash.put(sp.getId(), sp);
						hash.put(sp.getDinhLuong(), tmpHash);
					}
				}
			}catch (Exception ex)
			{
				ex.printStackTrace();
			}finally{
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return hash;
	}


	private List<SanPham> getSanPhamDonHangListDB()
	{
		List<SanPham> sanPhamDonHangList = new ArrayList<SanPham>();
		String query = "SELECT ISNULL(KH.TEN, '') AS TENKHACHHANG, DDH.PK_SEQ AS DONDATHANGID, SP.PK_SEQ AS SANPHAMID, SP.KhoGiay, SP.DinhLuong, SP.MA, abs(DDH_SP.SLCSX) AS SOLUONG \n" +
						" , SP.TENMAYSANXUAT, SP.NHANHANG_FK, ISNULL(NH.TEN, '') AS TENNHANHANG, ISNULL(CL.TEN, '') AS TENCHUNGLOAI \n" + 
						" FROM DONHANGIP DDH \n" +
						" INNER JOIN DONHANG_SANPHAMSPIP DDH_SP ON DDH_SP.DONHANG_FK = DDH.PK_SEQ \n" +
						" INNER JOIN SANPHAM SP ON SP.PK_SEQ = DDH_SP.SANPHAM_FK \n" +
						" LEFT JOIN KHACHHANG KH ON KH.PK_SEQ = DDH.KHACHHANG_FK \n" +
						" LEFT JOIN NHANHANG NH ON NH.PK_SEQ = SP.NHANHANG_FK \n" +
						" LEFT JOIN CHUNGLOAI CL ON CL.PK_SEQ = SP.CHUNGLOAI_FK \n" +
						" WHERE (DDH.TRANGTHAI = 1) and DDH_SP.SLCSX <> 0 \n";
		System.out.println("QUERY GET DON HANG SAN PHAM " + query);
		if (this.tuNgay.trim().length() > 0)
			query += " AND DDH.NGAYNHAP >= '" + this.tuNgay + "' \n";
		if (this.denNgay.trim().length() > 0)
			query += " AND DDH.NGAYNHAP <= '" + this.denNgay + "' \n";
		
		query += " and ddh.PK_SEQ not in \n" +
		"(\n" +
		" select distinct ct.DONHANG_FK\n" +
		" from KICHBAN kb \n" +
		" inner join TOHOP th on th.KICHBAN_FK = kb.PK_SEQ\n" +
		" inner join TOHOP_CHITIET ct on ct.TOHOP_FK = th.PK_SEQ\n" +
		" where kb.TRANGTHAI <> 2\n" +
		" )";

		System.out.println("ccccc \n" + query + "\n==============================");
		ResultSet rs = db.get(query);
		if (rs != null)
		{
			try{
				while (rs.next())
				{
					SanPham sp = new SanPham();
					sp.setId(rs.getString("SANPHAMID"));
					sp.setIdDonHang(rs.getString("DONDATHANGID"));
					sp.setDinhLuong(rs.getFloat("DINHLUONG"));
					sp.setKhoGiay(rs.getFloat("KHOGIAY"));
					sp.setSoLuong(rs.getInt("SOLUONG"));
					sp.setTenKhachHang(rs.getString("TENKHACHHANG"));
					sp.setMa(rs.getString("MA"));
					sp.setTenMaySanXuat(rs.getString("TENMAYSANXUAT"));
					sp.setIdNhanHang(rs.getString("NHANHANG_FK"));
					sp.setTenNhanHang(rs.getString("TENNHANHANG"));
					sp.setChungLoai(rs.getString("TENCHUNGLOAI"));
					SanPham spTmp1 = isExitSanPham(sanPhamDonHangList, sp);
					if (spTmp1 == null)//Chua co
						sanPhamDonHangList.add(sp);
					else//TH da co trong list, cong don so luong 
						spTmp1.setSoLuong(spTmp1.getSoLuong() + sp.getSoLuong());
				}
			}catch (Exception ex)
			{
				ex.printStackTrace();
			}finally{
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		for (SanPham sp : sanPhamDonHangList)
		{
			System.out.println("ID:" + sp.getId()+ "_DonHangId:" + sp.getIdDonHang() + "_SoLuong: " + sp.getSoLuong());
		}
		return sanPhamDonHangList;
	}

	private void createNguyenLieuRs()
	{
		String query = "SELECT DISTINCT DinhLuong, NHANHANG_FK, TENMAYSANXUAT, MSX.PK_SEQ AS IDMAYSANXUAT, MSX.KHOGIAYNGUYENLIEU \n" +  
		" FROM DONHANGIP DDH   \n" + 
		" INNER JOIN DONHANG_SANPHAMSPIP DDH_SP ON DDH_SP.DONHANG_FK = DDH.PK_SEQ   \n" + 
		" INNER JOIN SANPHAM SP ON SP.PK_SEQ = DDH_SP.SANPHAM_FK   \n" + 
		" INNER JOIN MAYSANXUAT MSX ON MSX.TEN = SP.TENMAYSANXUAT \n" +
		" WHERE DDH.TRANGTHAI = 1 ";
		if (this.tuNgay.trim().length() > 0)
			query += " AND DDH.NGAYNHAP >= '" + this.tuNgay + "' \n";
		if (this.denNgay.trim().length() > 0)
			query += " AND DDH.NGAYNHAP <= '" + this.denNgay + "' \n";
		this.nguyenLieuRs = this.db.get(query);
	}
	
	private Hashtable<Float, Hashtable<Float, SanPham>> getNguyenLieu()
	{
		Hashtable<Float, Hashtable<Float, SanPham>> hash = new Hashtable<Float, Hashtable<Float, SanPham>>();
		String query = "select sp.PK_SEQ as SANPHAMID, ksp.SOLUONG, sp.DinhLuong, sp.KhoGiay " +
						" from KHOTT_SANPHAM ksp  " +
						" inner join sanpham sp on sp.PK_SEQ = ksp.SANPHAM_FK " +
						" where ksp.KHO_FK = 100004 and sp.LOAISANPHAM_FK = 100008 ";
		System.out.println("query nguyen lieu: " + query);
		ResultSet rs = db.get(query);
		if (rs != null)
		{
			try{
				while (rs.next())
				{
					SanPham sp = new SanPham();
					sp.setId(rs.getString("SANPHAMID"));
					sp.setDinhLuong(rs.getFloat("DINHLUONG"));
					sp.setKhoGiay(rs.getFloat("KHOGIAY"));
					sp.setSoLuong(rs.getInt("SOLUONG"));
					if (hash.containsKey(sp.getDinhLuong()) == true)
					{
						Hashtable<Float, SanPham> tmpHash = hash.get(sp.getDinhLuong());
						tmpHash.put(sp.getKhoGiay(), sp);
					}
					else
					{
						Hashtable<Float, SanPham> tmpHash = new Hashtable<Float, SanPham>();
						tmpHash.put(sp.getKhoGiay(), sp);
						hash.put(sp.getDinhLuong(), tmpHash);
					}
				}
			}catch (Exception ex)
			{
				ex.printStackTrace();
			}finally{
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return hash;
	}

	private boolean isHaveNumber(Hashtable<String, SanPham> hash)
	{
		Set<String> keys = hash.keySet();
        for(String key: keys){
        	if (hash.get(key).getSoLuong() > 0)
        		return true;
        }
		return false;
	}
	
	public void DBClose()
	{
		if (db != null)
			db.shutDown();
	}
	
	public void setHash(Hashtable<Float, Hashtable<String, SanPham>> hash) {
		this.sanPhamDonHangHash = hash;
	}

	public Hashtable<Float, Hashtable<String, SanPham>> getHash() {
		return sanPhamDonHangHash;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setSanPhamKhoHash(Hashtable<String, SanPham> sanPhamKhoHash) {
		this.sanPhamKhoHash = sanPhamKhoHash;
	}

	public Hashtable<String, SanPham> getSanPhamKhoHash() {
		return sanPhamKhoHash;
	}

	public void setNguyenLieuHash(Hashtable<Float, Hashtable<Float, SanPham>> nguyenLieuHash) {
		this.nguyenLieuHash = nguyenLieuHash;
	}

	public Hashtable<Float, Hashtable<Float, SanPham>> getNguyenLieuHash() {
		return nguyenLieuHash;
	}

	public void setKichBan(KichBanN kichBan) {
		this.kichBan = kichBan;
	}

	public KichBanN getKichBan() {
		return kichBan;
	}

	public void setSanPhamDonHangList(List<SanPham> sanPhamDonHangList) {
		this.sanPhamDonHangList = sanPhamDonHangList;
	}

	public List<SanPham> getSanPhamDonHangList() {
		return sanPhamDonHangList;
	}

	public void setMaySanXuat(List<MaySanXuat> maySanXuatList) {
		this.maySanXuatList = maySanXuatList;
	}

	public List<MaySanXuat> getMaySanXuat() {
		return maySanXuatList;
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

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
	
	public KeHoachN getKeHoach() {
		return keHoach;
	}

	public void setKeHoach(KeHoachN keHoach) {
		this.keHoach = keHoach;
	}

	public void setMaMaySanXuat(String maMaySanXuat) {
		this.maMaySanXuat = maMaySanXuat;
	}

	public String getMaMaySanXuat() {
		return maMaySanXuat;
	}
	
	public String getNguoiTao() {
		return nguoiTao;
	}

	public void setNguoiTao(String nguoiTao) {
		this.nguoiTao = nguoiTao;
	}

	public String getNguoiSua() {
		return nguoiSua;
	}

	public void setNguoiSua(String nguoiSua) {
		this.nguoiSua = nguoiSua;
	}

	public void setNguyenLieuRs(ResultSet nguyenLieuRs) {
		this.nguyenLieuRs = nguyenLieuRs;
	}

	public ResultSet getNguyenLieuRs() {
		return nguyenLieuRs;
	}

	public String delete() {
		try {
			this.db.getConnection().setAutoCommit(false);
			
			String query = "UPDATE KICHBAN SET TRANGTHAI = 2 WHERE PK_SEQ = " + this.id;
			
			if (!this.db.update(query))
			{
				this.db.getConnection().rollback();
				this.db.getConnection().setAutoCommit(true);
				return "Không thể xóa kịch bản";
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}
}