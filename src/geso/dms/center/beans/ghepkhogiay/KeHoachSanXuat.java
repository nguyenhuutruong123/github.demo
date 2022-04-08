package geso.dms.center.beans.ghepkhogiay;
import geso.dms.center.db.sql.dbutils;

import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class KeHoachSanXuat {
	private String id;
	private String tuNgay;
	private String denNgay;
	private String maMaySanXuat;
	private String nguoiTao;
	private String nguoiSua;
	private Hashtable<Float, Hashtable<String, SanPham>> sanPhamDonHangHash;
	private Hashtable<String, SanPham> sanPhamKhoHash;
	private Hashtable<Float, Hashtable<Float, SanPham>> nguyenLieuHash;
	private KichBan kichBan;
	private KeHoach keHoach;
	private List<SanPham> sanPhamDonHangList;
	private List<MaySanXuat> maySanXuatList; 
	private ResultSet nguyenLieuRs;
	private String msg;
	dbutils db;
	
	public KeHoachSanXuat()
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
		kichBan = new KichBan();
		keHoach = new KeHoach();
		sanPhamDonHangList = new ArrayList<SanPham>();
		maySanXuatList = new ArrayList<MaySanXuat>(); 
		db = new dbutils();
		getMaySanXuatFromDB(this.maySanXuatList);
	}
	
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
							ToHop toHop = new ToHop();
							idToHop = toHopRs.getString("PK_SEQ");
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
									toHop.getSanPhamHash().put((float)nChiTiet, sp);
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
					// TODO Auto-generated catch block
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
		sanPhamDonHangHash = getSanPhamDonHang(); 
		sanPhamKhoHash = getSanPhamKho();
		nguyenLieuHash = getNguyenLieu();
//		getMaySanXuatFromDB(this.maySanXuatList);
		Set<Float> keys = sanPhamDonHangHash.keySet();
        for(Float key: keys){
            Hashtable<String, SanPham> tmpHash = sanPhamDonHangHash.get(key);
            Set<String> spKeys = tmpHash.keySet();
            for(String key1: spKeys){
            	SanPham sp = tmpHash.get(key1);
            	System.out.println("San pham " + sp.getId() + "_dinh luong " + sp.getDinhLuong() + "_nhanHang " + sp.getIdNhanHang() + "_khoGiay " + sp.getKhoGiay() + "_soLuong "+ sp.getSoLuong());
            }
        }
        
       createKichBan();
//       inSanPhamList(sanPhamDonHangList);
       //Tao ke hoach (da phan bo san pham vao don hang)
       keHoach.init(kichBan, sanPhamDonHangList);
       //Phan bo tung to hop trong ke hoach vao may san xuat
       keHoach.phanBoMaySanXuat(this.maySanXuatList);
//       inKeHoach(keHoach);
       System.out.println("ggggggggggggggggggggggggggg");
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
			Hashtable<Float, ToHop> toHopHash = this.keHoach.getToHopMay().get(khoGiayNLMay).getToHopHash();
			Set<Float> toHopHashKeys = toHopHash.keySet();
		    for (float key : toHopHashKeys)
		    {
		    	ToHop toHop = toHopHash.get(key);
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
						Hashtable<Float, SanPham> sanPhamHash = toHop.getSanPhamHash();
						Set<Float> sanPhamKeys = sanPhamHash.keySet();
						for (float sanPhamKey : sanPhamKeys)
						{
							SanPham sanPham = sanPhamHash.get(sanPhamKey);
							query = "INSERT INTO TOHOP_CHITIET(TOHOP_FK, SANPHAM_FK, TILE, SOLUONG, DONHANG_FK) "
							+ " VALUES (" + idToHop + ", " 
							+ sanPham.getId() + ", " 
							+ sanPham.getTiLe() + ", " 
							+ sanPham.getSoLuong() + ", " 
							+ sanPham.getIdDonHang() + ")";
							if (!this.db.update(query)) {
								return false;
							}
						}
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					try {
						rsPxk.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		    }
		}
		return true;
	}

	public void xuatExcell(OutputStream outstream, String templateFileName)
	{
		keHoach.xuatExcell(outstream, templateFileName);
	}
	private void getMaySanXuatFromDB(List<MaySanXuat> maySanXuatList) {
		if (maySanXuatList == null)
			maySanXuatList = new ArrayList<MaySanXuat>();
		String query = "select PK_SEQ, MA, TEN, KHOGIAYNGUYENLIEU, CONGSUATMAY from MAYSANXUAT WHERE TRANGTHAI = '1'"; 
		ResultSet rs = db.get(query);
		if (rs != null)
		{
			try{
				while (rs.next())
				{
					MaySanXuat maySanXuat = new MaySanXuat();
					maySanXuat.setId(rs.getString("PK_SEQ"));
					maySanXuat.setMa(rs.getString("MA"));
					maySanXuat.setTen(rs.getString("TEN"));
					maySanXuat.setKhoGiayNguyenLieu(rs.getFloat("KHOGIAYNGUYENLIEU"));
					maySanXuat.setCongSuatMay(rs.getFloat("CONGSUATMAY"));
					this.maySanXuatList.add(maySanXuat);
					query = "SELECT ISNULL(SUM(TRONGLUONG * SOLUONGTOHOP), 0) AS TRONGLUONGHIENTAI " +
					" FROM LENHSANXUAT WHERE TINHTRANG = 0 OR TINHTRANG = 1  MAYSANXUAT_FK = " + maySanXuat.getId();
					ResultSet trongLuongRs = this.db.get(query);
					if (trongLuongRs != null)
					{
						try{
							if (trongLuongRs.next())
							{
								maySanXuat.setTrongLuongHienTai(trongLuongRs.getFloat("TRONGLUONGHIENTAI"));
							}
						}catch (Exception e) {
							e.printStackTrace();
						}finally{
							trongLuongRs.close();
						}
					}
//					Hashtable<Float, Float> dinhLuongHash = maySanXuat.getDinhLuongHash();
//					query = "select DINHLUONG from MAYSANXUAT_DINHLUONG where MAYSANXUAT_FK = " + maySanXuat.getId();
//					ResultSet dinhLuongRs = this.db.get(query);
//					if (dinhLuongRs != null)
//					{
//						try{
//							while (dinhLuongRs.next())
//							{
//								dinhLuongHash.put(dinhLuongRs.getFloat("DINHLUONG"), dinhLuongRs.getFloat("DINHLUONG"));
//							}
//						}catch (Exception e) {
//							e.printStackTrace();
//						}finally{
//							dinhLuongRs.close();
//						}
//					}
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return khoGiays;
	}
	private void createKichBan() {
		//Voi voi moi dinh luong
		Set<Float> dinhLuongs = nguyenLieuHash.keySet();
		Hashtable<Float, Float> nguyenLieus = getKhoGiayMaySanXuat();
		createNguyenLieuRs();
        try {
			while (this.nguyenLieuRs.next()){
				float dinhLuong = this.nguyenLieuRs.getFloat("DINHLUONG");
				System.out.println("dinhLuong: " + dinhLuong);
				String idNhanHang = this.nguyenLieuRs.getString("NHANHANG_FK");
				String tenMaySanXuat = this.nguyenLieuRs.getString("TENMAYSANXUAT");
				float khoGiayNguyenLieu = this.nguyenLieuRs.getFloat("KHOGIAYNGUYENLIEU");
//				Hashtable<Float, SanPham> nguyenLieus = nguyenLieuHash.get(dinhLuong);//Key la kho giay
//				Hashtable<Float, Float> nguyenLieus = new Hashtable<Float, Float>();
				if (dinhLuong == 320 && khoGiayNguyenLieu == 330
						&& idNhanHang.trim().equals("100252") == true 
						&& tenMaySanXuat.trim().equals("PM2_3") == true)
					System.out.println();
				Hashtable<String, SanPham> sanPhamDonHangs = conVertSanPhamDonHang(sanPhamDonHangHash, dinhLuong, idNhanHang, tenMaySanXuat);//Key la Id san pham
				if (sanPhamDonHangs != null)
				{
			    	int n;
			    	float m;
					while (isHaveNumber(sanPhamDonHangs) == true) 
					{
						//Luu to hop toi uu cho tung kho giay nguyen lieu, de so sanh xem kho giay nao cho hao hut nho nhat
						//Key: kho giay nguyen lieu, key con: kho giay chi tiet thanh pham
						Hashtable<Float, ToHop> selectedColection = new Hashtable<Float, ToHop>();
						//Xet voi moi kho giay nguyen lieu
						Set<Float> nguyenLieuKeys = nguyenLieus.keySet();
//						for (float khoGiayNguyenLieu : nguyenLieuKeys)//San pham ghe duoc voi nhau theo bo ba dinh luong, nhan hang, may san xuat => ko co chuyen co 2 kho giay nguyen lieu hop le tro le
						{
//							int soLuongGiayNguyenLieu = nguyenLieus.get(khoGiayNguyenLieu).getSoLuong(); 
							//Neu giay nguyen lieu con trong kho
							if (isXetKhoGiayNguyenLieu(khoGiayNguyenLieu, sanPhamDonHangs) == true) //khong can xet toi so luong kho giay nguyen lieu nua
							{
								//List san pham cho moi lan xet
								List<SanPham> wList = new ArrayList<SanPham>();
								createWList(wList, sanPhamDonHangs, nguyenLieus, khoGiayNguyenLieu);
								n = wList.size();
								m = khoGiayNguyenLieu;
								//Tinh bang phuong an bang phuong phap truy hoi
								float [][] f = optimize(wList, (int)m);
								
								//Truy vet tim kho giay thanh pham toi uu ghep lai cho kho tieu chuan
								ToHop toHop = trace(wList, f, n, (int)m);
								toHop.setDinhLuong(dinhLuong);
								toHop.setKhoGiayNguyenLieu(khoGiayNguyenLieu);
								
								selectedColection.put(toHop.getKhoGiayNguyenLieu(), toHop);
							}
						}
						//End tim duoc kho giay toi uu
						System.out.println("End tim duoc kho giay toi uu");
						ToHop toHop = getValueByMinKey(selectedColection);
						//Da tim duoc kho giay toi uu
						if (toHop != null)
						{
							//Khong quan tam toi so luong nguyen lieu, nguyen lieu luon san sang
							tinhSoLuongToHop(toHop, sanPhamDonHangs);
							kichBan.addToHop(toHop);
							truSoLuong(toHop, sanPhamDonHangs);
						}
						//remove kho giay tieu chuan neu co so luong == 0
//						removeZero(nguyenLieus);
						
						//remove kho giay thanh pham neu co so luong == 0
						removeZero(sanPhamDonHangs);
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
        inKichBan(kichBan);
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
        		boolean isFirst = true;
        		for (float khoGiayToHop : sanPhamToHopsKey)
        		{
        			System.out.println(sanPhamToHops.get(khoGiayToHop).getKhoGiay() 
        					+ ": " + (sanPhamToHops.get(khoGiayToHop).getSoLuong()) * soLuongToHop);
        		}
        	}
        }
	}

	private void removeZero(Hashtable<String, SanPham> hash) {
		Set<String> keys = hash.keySet();
		List<String> removeList = new ArrayList<String>();
		for (String key : keys)
		{
			if (hash.get(key).getSoLuong() == 0)
				removeList.add(key);
		}
		for (String key : removeList)
		{
			hash.remove(key);
		}
	}

	int iToHop = 0;
	private void truSoLuong(ToHop toHop, Hashtable<String, SanPham> sanPhamDonHangs) {
		//Tru nguyen lieu
		float khoGiayNguyenLieu = toHop.getKhoGiayNguyenLieu();
		int soLuongToHop = toHop.getSoLuong();
//		nguyenLieus.get(khoGiayNguyenLieu).setSoLuong(nguyenLieus.get(khoGiayNguyenLieu).getSoLuong() - soLuongToHop);
		
		//Tru san pham don hang
		Hashtable<Float, SanPham> sanPhamToHops = toHop.getSanPhamHash();
		Set<Float> sanPhamToHopKeys = sanPhamToHops.keySet();
		for (float khoGiayToHop : sanPhamToHopKeys)
		{
			Set<String> sanPhamDonHangKeys = sanPhamDonHangs.keySet();
			for (String khoGiayDonHang : sanPhamDonHangKeys)
			{
				if (sanPhamToHops.get(khoGiayToHop).getKhoGiay() == sanPhamDonHangs.get(khoGiayDonHang).getKhoGiay())
				{
					SanPham spDonHang = sanPhamDonHangs.get(khoGiayDonHang);
					SanPham spToHop = sanPhamToHops.get(khoGiayToHop);
					int soLuongSanPhamTru = spToHop.getSoLuong() * soLuongToHop;
					spDonHang.setSoLuong(sanPhamDonHangs.get(khoGiayDonHang).getSoLuong() 
							-  soLuongSanPhamTru);
					if (spDonHang.getSoLuong() < 0)
						System.out.println("to hop: " + iToHop);
				}
			}
		}
		iToHop++;
	}

	private void tinhSoLuongToHop(ToHop toHop, Hashtable<String, SanPham> sanPhamDonHangs) {
//		int minNumber = 0;
		boolean isFirst = true;
		Hashtable<Float, SanPham> sanPhamToHops = toHop.getSanPhamHash();
		Set<Float> sanPhamToHopKeys = sanPhamToHops.keySet();
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
		Set<String> sanPhamDonHangKeys = sanPhamDonHangs.keySet();//Key la id san pham
		int minPer = 0;
		if (toHop.getHaoHut() == 25)
		{
			System.out.println();
		}
		isFirst = true;
		for (String khoGiayDonHang : sanPhamDonHangKeys)
		{
			for (float khoGiayToHop : sanPhamToHopKeys)
				if (sanPhamToHops.get(khoGiayToHop).getKhoGiay() == sanPhamDonHangs.get(khoGiayDonHang).getKhoGiay())
				{
					if (isFirst == true)
					{
						minPer = sanPhamDonHangs.get(khoGiayDonHang).getSoLuong() / sanPhamToHops.get(khoGiayToHop).getSoLuong();
						isFirst = false;
					}
					else
					{
						int soLuongSanPhamDonHang = sanPhamDonHangs.get(khoGiayDonHang).getSoLuong();
						int soLuongSanPhamToHop = sanPhamToHops.get(khoGiayToHop).getSoLuong();
						int tmp = sanPhamDonHangs.get(khoGiayDonHang).getSoLuong() / sanPhamToHops.get(khoGiayToHop).getSoLuong();
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

	private ToHop getValueByMinKey(Hashtable<Float, ToHop> selectedColection) {
		ToHop toHop;
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

	private ToHop trace(List<SanPham> wList, float[][] f, int n, int m) {
		ToHop toHop = new ToHop();
		Hashtable<Float , SanPham> spHash = toHop.getSanPhamHash();
		while (n != 0)
		{
			if (f[n][m] != f[n -1][m])
			{
				boolean isExit = false;
				Set<Float> spKeys = spHash.keySet();
				for (float khoGiay : spKeys)
				{
					if (spHash.get(khoGiay).getKhoGiay() == wList.get(n - 1).getKhoGiay())
					{
						spHash.get(khoGiay).setSoLuong(spHash.get(khoGiay).getSoLuong() + 1);
						spHash.get(khoGiay).setTiLe(spHash.get(khoGiay).getSoLuong());
						isExit = true;
					}
				}
				if (isExit == false)
				{
					SanPham sp = new SanPham(wList.get(n - 1));
					sp.setSoLuong(1);
					sp.setTiLe(1);
					spHash.put(sp.getKhoGiay(), sp);
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
	
//	//Kiem tra san pham co san xuat duoc voi kho giay nguyen lieu khong
//	private isSanXuat
	private void createWList(List<SanPham> wList, Hashtable<String, SanPham> sanPhamDonHangs, Hashtable<Float, Float> nguyenLieus, float khoGiayNguyenLieu) {
		Set<String> sanPhamKeys = sanPhamDonHangs.keySet();//key san pham don hang
		List<String> sanPhamKeys1 = new ArrayList<String>(); //key san pham don hang da sap xep
		for (String idSp : sanPhamKeys)
		{
			sanPhamKeys1.add(idSp);
		}
		Collections.sort(sanPhamKeys1);
		Collections.reverse(sanPhamKeys1);
		for (String khoGiay : sanPhamKeys1)
		{
			if (sanPhamDonHangs.get(khoGiay).getSoLuong() > 0)
			{
				float number = (khoGiayNguyenLieu / sanPhamDonHangs.get(khoGiay).getKhoGiay());
				if (number > 0)
				{
					if (number > sanPhamDonHangs.get(khoGiay).getSoLuong())
						number = sanPhamDonHangs.get(khoGiay).getSoLuong();
					for (int h = 0; h < number; h++)
					{
						SanPham sp = new SanPham(sanPhamDonHangs.get(khoGiay));
//						String tenMaySanXuat = sanPhamDonHangs.get(khoGiay);
//						sp.setTenMaySanXuat(tenMaySanXuat)
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
		float haoHut = (standarList.get(standarSizeIndex).getKhoGiay() - max) * minPer;
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
	private Hashtable<String, SanPham> conVertSanPhamDonHang(Hashtable<Float, Hashtable<String, SanPham>> sanPhamDonHangHash, float dinhLuong, String idNhanHang, String tenMaySanXuat) {
		if (sanPhamDonHangHash.containsKey(dinhLuong) == true)
		{
			Hashtable<String, SanPham> hash = new Hashtable<String, SanPham>();
			//Lay san pham theo dinh luong
			Hashtable<String, SanPham> hashTmp = sanPhamDonHangHash.get(dinhLuong);
			Set<String> ids = hashTmp.keySet();
	        for(String id: ids){
	        	SanPham sp = hashTmp.get(id);
	        	//Neu san pham co cung dinh luong, nhan hang, ten may san xuat moi them vao
	        	if (sp.getIdNhanHang().trim().equals(idNhanHang) == true //nhan hang 
    			&& sp.getTenMaySanXuat().trim().equals(tenMaySanXuat) == true) //ten may san xuat
	        		hash.put(sp.getId(), sp);
	        }
	        return hash;
		}
		return null;
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
						" WHERE DDH.TRANGTHAI = 9";
		System.out.println("QUERY GET DON HANG SAN PHAM " + query);
		if (this.tuNgay.trim().length() > 0)
			query += " AND DDH.NGAYDAT >= '" + this.tuNgay + "' ";
		if (this.denNgay.trim().length() > 0)
			query += " AND DDH.NGAYDAT <= '" + this.denNgay + "' ";
		System.out.println("QUERY GET DON HANG SAN PHAM " + query);
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
					SanPham sp2 = new SanPham(sp);
					this.sanPhamDonHangList.add(sp2);
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return hash;
	}

	private void createNguyenLieuRs()
	{
		String query = "SELECT DISTINCT DinhLuong, NHANHANG_FK, TENMAYSANXUAT, MSX.KHOGIAYNGUYENLIEU " +  
		" FROM DONHANGIP DDH   " + 
		" INNER JOIN DONHANG_SANPHAMSPIP DDH_SP ON DDH_SP.DONHANG_FK = DDH.PK_SEQ   " + 
		" INNER JOIN SANPHAM SP ON SP.PK_SEQ = DDH_SP.SANPHAM_FK   " + 
		" INNER JOIN MAYSANXUAT MSX ON MSX.TEN = SP.TENMAYSANXUAT " +
		" WHERE DDH.TRANGTHAI = 9 ";
		if (this.tuNgay.trim().length() > 0)
			query += " AND DDH.NGAYDAT >= '" + this.tuNgay + "' ";
		if (this.denNgay.trim().length() > 0)
			query += " AND DDH.NGAYDAT <= '" + this.denNgay + "' ";
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
					// TODO Auto-generated catch block
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

	public void setKichBan(KichBan kichBan) {
		this.kichBan = kichBan;
	}

	public KichBan getKichBan() {
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
	
	public KeHoach getKeHoach() {
		return keHoach;
	}

	public void setKeHoach(KeHoach keHoach) {
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
}