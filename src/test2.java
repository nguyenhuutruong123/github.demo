import geso.diageo.center.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Calendar;


public class test2 
{
	public static void main(String[] args) 
	{
		try 
		{
			dbutils db = new dbutils();
			db.getConnection().setAutoCommit(false);
			
			//String[] ngaychungtu = new String[]{"2012-06-18", "2012-06-19", "2012-06-20", "2012-06-21", "2012-06-22", "2012-06-23", "2012-06-24", "2012-06-25", "2012-06-26", "2012-06-27", "2012-06-28", "2012-06-29", "2012-06-30"};
			
			String ngayct = "";
			/*for(int j = 17; j <= 30; j++)
			{
				ngayct += "2012-06-" + Integer.toString(j) + ",";
			}
			
			for(int j = 1; j <= 31; j++)
			{
				String ngay = "";
				if(j < 10)
					ngay = "0" + Integer.toString(j);
				else
					ngay = Integer.toString(j);
				
				ngayct += "2012-07-" + ngay + ",";
			}*/
			
			for(int k = 1; k <= 13; k++)
			{
				String ngay = "";
				if(k < 10)
					ngay = "0" + Integer.toString(k);
				else
					ngay = Integer.toString(k);
				
				ngayct += "2012-08-" + ngay + ",";
			}
			
			ngayct = ngayct.substring(0, ngayct.length() - 1);
			String[] ngaychungtu = ngayct.split(",");
			
			for(int i = 0; i < ngaychungtu.length; i++)
			{
				//System.out.println(ngaychungtu[i]);
				
				//Lay phieu nhap kho trong ngaychungtu
				String query = "select pk_seq, ngaynhapkho from erp_nhapkho where trangthai = '1' and ngaynhapkho = '" + ngaychungtu[i] + "' order by ngaytao asc";
				System.out.println("Query khoi tao nhapkho: " + query);
				
				ResultSet pnRs = db.get(query);
				if(pnRs != null)
				{
					while(pnRs.next())
					{
						String pnId = pnRs.getString("pk_seq");
						
						if(pnId != null)
						{
							if(pnId.length() > 0)
							{
								if(!chotNhapKho(pnId, db))
								{
									System.out.println("Dung lai tai day, co loi chot nhap kho roi...");
									break;
								}
							}
						}
					}
					pnRs.close();
				}
				
				query = "select pk_seq, ngayxuat from erp_xuatkho where trangthai = '1' and ngayxuat = '" + ngaychungtu[i] + "' order by ngaytao asc";
				ResultSet pxRs = db.get(query);
				if(pxRs != null)
				{
					while(pxRs.next())
					{
						String pxId = pxRs.getString("pk_seq");
						String ngayxuat = pxRs.getString("ngayxuat");
						
						if(!chotXuatKho(pxId, ngayxuat, db))
						{
							System.out.println("Dung lai tai day, co loi chot xuat kho roi...");
							break;
						}
					}
					pxRs.close();
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(false);
			
			//BinhQuanCuoiKy("2012-07-01", "2012-07-31");
			
			//KhoaSoThang("2012-07-01", "2012-07-31", "07", "2012", db);
			
			System.out.println("Chay xong........");
		} 
		catch (Exception e)
		{
			System.out.println("Exception roi ku: " + e.getMessage());
		}

	}
	
	public static boolean chotNhapKho(String id, dbutils db)
	{
		try 
		{
			String query = "select a.KHONHAP, a.SOPHIEUNHAPHANG, a.SODONTRAHANG, a.NGAYNHAPKHO, a.NOIDUNGNHAP, b.pk_seq as ctnhapkho, b.SANPHAM_FK, b.SOLUONGNHAP, isnull(b.SOLO, 0) as solo, d.QUANLYLO, d.LOAISANPHAM_FK " +
							"from ERP_NHAPKHO a inner join ERP_NHAPKHO_SANPHAM b on a.PK_SEQ = b.SONHAPKHO_FK inner join ERP_KHOTT c on a.khonhap = c.PK_SEQ " +
							"inner join SanPham d on b.SANPHAM_FK = d.PK_SEQ where a.PK_SEQ = '" + id + "'";
			
			System.out.println("Query chot nhap kho id: " + id + " - init: " + query);
			
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				while(rs.next())
				{
					String ngaynhapkho = rs.getString("NGAYNHAPKHO");
					String sophieunhap = rs.getString("SOPHIEUNHAPHANG");
					String sodontrave = rs.getString("SODONTRAHANG");
					String masanpham = rs.getString("SANPHAM_FK");
					String khonhap = rs.getString("KHONHAP");
					String soluong =  rs.getString("SOLUONGNHAP");
					
					String noidungnhap = rs.getString("NOIDUNGNHAP");
					
					System.out.println("Noi dung nhap cua id: " + id + " : " + noidungnhap);
				
					double giaton = 0;
					double dongia = 0;
					double thanhtien = 0;

					if(noidungnhap.equals("100004")) 
					{
						String ngay = getMonth(ngaynhapkho, -1);
						String[] ngay_thang = ngay.split("-");
						
						query = "select soluong, giaton from ERP_TONKHOTHANG where sanpham_fk = '" + masanpham + "' and thang = '" + ngay_thang[1] + "' and nam = '" + ngay_thang[0] + "' and giaton > 0";
						System.out.println("Check gia ton dau thang: " + query);
						
						ResultSet rsCheck = db.get(query);
						boolean dacotondau = false;
						if(rsCheck != null)
						{
							if(rsCheck.next())
							{
								dacotondau = true;
								dongia = rsCheck.getDouble("giaton");
								rsCheck.close();
							}
						}
						
						//neu san pham tra ve nay chua co ton dau, lay gia la gia cua nhan hang gan nhat
						if(!dacotondau)
						{
							query = "select top(1) dongia from erp_nhanhang_sanpham where sanpham_fk = '"+ masanpham + "' order by nhanhang_fk desc";
							rsCheck = db.get(query);
							if(rsCheck != null)
							{
								if(rsCheck.next())
								{
									dongia = rsCheck.getDouble("dongia");
									rsCheck.close();
								}
							}
						}
						
						query = "select " + dongia + " as dongiaViet, " +
								" ( ( " + soluong + " * " + dongia + " + kho.giaton * kho.soluong) / (kho.soluong + " + soluong + ") ) as giaTon " +
							"from ( " +
									"select SANPHAM_FK, DONGIA as dongiaViet " +
									"from DONTRAHANG_SP " +
									"where SANPHAM_FK = '" + masanpham + "' and DONTRAHANG_FK = '" + sodontrave + "') sp " +
							"inner join " +
								"(select sanpham_fk, giaton, soluong " +
								"from ERP_KHOTT_SANPHAM_TEST where SANPHAM_FK = '" + masanpham + "' and KHOTT_FK = '" + khonhap + "') kho " +
							"on sp.SANPHAM_FK = kho.SANPHAM_FK";
					}
					else
					{
						query = "select sp.dongiaViet, sp.thanhtien, " +
								" (sp.thanhtien + kho.thanhtien) / (sp.soluong + kho.SOLUONG) as giaTon " +
							"from " +
								"(select dongia as dongiaViet, sanpham_fk, soluongnhap as soluong, thanhtien from erp_nhapkho_sanpham " +
								"where sanpham_fk = '" + masanpham + "' and sonhapkho_fk = '" + id + "' ) sp " +
							"inner join " +
								"(select sanpham_fk, thanhtien, soluong " +
								"from ERP_KHOTT_SANPHAM_TEST where SANPHAM_FK = '" + masanpham + "' and KHOTT_FK = '" + khonhap + "') kho " +
								"on sp.SANPHAM_FK = kho.SANPHAM_FK";
					}
					
					System.out.println("Lay gia ton: " + query);
					
					ResultSet rsSp = db.get(query);
					if(rsSp != null)
					{
						if(rsSp.next())
						{
							giaton = rsSp.getDouble("giaTon");
							dongia = rsSp.getDouble("dongiaViet"); 
							
							if(noidungnhap.equals("100004"))
								thanhtien = dongia * Integer.parseInt(soluong);
							else
								thanhtien = rsSp.getDouble("thanhtien"); 
							
							rsSp.close();
						}
					}
					
					query = "update ERP_KHOTT_SANPHAM_TEST set soluong = soluong + " + soluong + ", available = available + " + soluong + ", GIATON = " + giaton + " " +
							"where khott_fk = '" + khonhap + "' and sanpham_fk = '" + masanpham + "'";
					
					System.out.println("1.Cap nhat ERP_KHOTT_SANPHAM_TEST Phieu Nhap: " + query);
					
					if(!db.update(query))
					{
						String msg = "Khong the cap nhat ERP_KHOTT_SANPHAM_TEST: " + query;
						System.out.println(msg);
						db.getConnection().rollback();
						return false;
					}
						
					query = "select soluong from ERP_KHOTT_SANPHAM_TEST where khott_fk = '" + khonhap + "' and sanpham_fk = '" + masanpham + "'";
					System.out.println("2.Check so luong: " + query);
					
					ResultSet soluongTonKho = db.get(query);
					int soluongton = 0;
					if(soluongTonKho != null)
					{
						if(soluongTonKho.next())
						{
							soluongton = soluongTonKho.getInt("soluong");
						}
						soluongTonKho.close();
					}
					
					double thanhtienTon = giaton * soluongton;
					//luu bao cao nhap xuat ton
					query = "insert ERP_NHAP_XUAT_TON_PROVENCE(SOCT, NGAYCT, SANPHAM_FK, SOLUONGNHAP, DONGIANHAP, THANHTIENNHAP, SOLUONGTON, DONGIATON, THANHTIENTON, KHO_FK) " +
							"values('" + id + "', '" + ngaynhapkho + "', '" + masanpham + "', " + soluong + ", " + dongia + ", " + thanhtien + ", " + soluongton + ", " + giaton + ", " + thanhtienTon + ",  '" + khonhap + "')";
					
					System.out.println("3.Chen Phieu ERP_NHAP_XUAT_TON_PROVENCE: " + query);
					
					if(!db.update(query))
					{
						String msg = "Khong the Insert ERP_NHAP_XUAT_TON Phieu Nhap: " + query;
						System.out.println(msg);
						db.getConnection().rollback();
						return false;
					}
					
				}
				rs.close();
			}
		
			return true;
			
		} 
		catch (Exception e) 
		{ 
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
			System.out.println("Loi khi chay chuong trinh : " + e.getMessage());
			return false;
		}
	}

	public static boolean chotXuatKho(String id, String ngayxk, dbutils db) 
	{
		try 
		{
			String query = "select a.SANPHAM_FK, a.SOLUONG, b.LOAISANPHAM_FK, c.GIATON, c.SOLUONG - a.SOLUONG as soluongTon " +
							"from ERP_XUATKHO_SANPHAM a inner join SANPHAM b on a.SANPHAM_FK = b.PK_SEQ inner join ERP_KHOTT_SANPHAM_TEST c on b.PK_SEQ = c.SANPHAM_FK" +
							" where a.XUATKHO_FK = '" + id + "' and c.KHOTT_FK = '100000'";
			
			System.out.println("Query chot xuat kho init: " + query);
			
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				while(rs.next())
				{
					String masanpham = rs.getString("SANPHAM_FK");
					String soluong =  rs.getString("SOLUONG");
					int soluongTon = rs.getInt("soluongTon");
					double dongia = rs.getDouble("GIATON");
					double thanhtienTon = dongia * soluongTon;
					
					query = "update ERP_KHOTT_SANPHAM_TEST set soluong = soluong - '" + soluong + "', booked = booked - '" + soluong + "' " +
							"where khott_fk = '100000' and sanpham_fk = '" + masanpham + "'";
					
					System.out.println("Update Ton kho TT Phieu Xuat: " + query + "\n");
					if(!db.update(query))
					{
						String msg = "5.Khong the cap nhat ERP_KHOTT_SANPHAM_TEST: " + query;
						System.out.println(msg);
						db.getConnection().rollback();
						return false;
					}
					
					//ERP_NHAP_XUAT_TON
					query = "insert ERP_NHAP_XUAT_TON_PROVENCE(SOCT, NGAYCT, SANPHAM_FK, SOLUONGXUAT, DONGIAXUAT, THANHTIENXUAT, SOLUONGTON, DONGIATON, THANHTIENTON, KHO_FK) " +
							"values('" + id + "', '" + ngayxk + "', '" + masanpham + "', " + soluong + ", " + dongia + ", " + soluong + " * " + dongia + ", " + soluongTon + ", " + dongia + ", " + thanhtienTon + ", '100000')";
			
					System.out.println("6.Insert ERP_NHAP_XUAT_TON_PROVENCE Phieu Xuat: " + query);
					if(!db.update(query))
					{
						String msg = "Khong the tao moi ERP_NHAP_XUAT_TON_PROVENCE: " + query;
						System.out.println(msg);
						db.getConnection().rollback();
						return false;
					}
				}
				rs.close();
			}
			
			return true;
		} 
		catch (SQLException e) 
		{ 
			System.out.println("Exception : " + e.getMessage());
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
			return false;
		}
	}

	public static boolean BinhQuanCuoiKy(String tungay, String denngay)
	{
		dbutils db = new dbutils();
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "select nhapxuat.sanpham_fk, sum(nhapxuat.soluong) as tongluongnhap, sum(nhapxuat.thanhtien) as tongthanhtien  " +
					"from (	" +
						"select sanpham_fk, soluong, thanhtienton as thanhtien from erp_tonkhothang where thang = '05' " +
					"union all " +
						"select sanpham_fk, sum(soluongNhap) as soluong, sum(isnull(thanhtienNhap, 0)) as thanhtien " +
					"from ERP_NHAP_XUAT_TON_PROVENCE where '" + tungay + "' <= ngayct and ngayct <= '" + denngay + "' group by sanpham_fk ) nhapxuat " +
					"group by sanpham_fk";
			
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				while(rs.next())
				{
					String sanpham_fk = rs.getString("sanpham_fk");
					double tongnhap = rs.getDouble("tongluongnhap");
					double tongtien = rs.getDouble("tongthanhtien");
					
					double dongia = 0;
					if(tongnhap > 0)
						dongia = tongtien / tongnhap;
					
					query = "update ERP_NHAP_XUAT_TON_PROVENCE set dongiaXuat = " + dongia + " where sanpham_fk = '" + sanpham_fk + "' and soluongXuat is not null";
					System.out.println("Cap nhat gia xuat NXT: " + query);
					if(!db.update(query))
					{
						System.out.println("Loi: " + query);
						db.getConnection().rollback();
						return false;
					}
					
					query = "update ERP_NHAP_XUAT_TON_PROVENCE set thanhtienXuat = soluongXuat * dongiaXuat, thanhtienTon = soluongTon * dongiaTon " +
							" where sanpham_fk = '" + sanpham_fk + "' and soluongXuat is not null";
					
					System.out.println("Cap nhat thanh tien NXT: " + query);
					if(!db.update(query))
					{
						System.out.println("Loi: " + query);
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			CapNhatGiaTon(tungay, denngay, db);
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(false);
	
			return true;
		}
		catch (Exception e)
		{
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
			
			System.out.println("Khong the tinh binh quan: " + e.getMessage());
			return false;
		}
		
	}
	
	public static void CapNhatGiaTon(String tungay, String denngay, dbutils db)
	{
		ResultSet rs = db.get("select distinct sanpham_fk, dongiaXuat from ERP_NHAP_XUAT_TON_PROVENCE where '" + tungay + "' <= ngayct and ngayct <= '" + denngay + "' and dongiaXuat is not null");
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					String query = "update ERP_NHAP_XUAT_TON_PROVENCE set dongiaTon = " + rs.getDouble("dongiaXuat") + " where sanpham_fk = '" + rs.getString("sanpham_fk") + "' and '" + tungay + "' <= ngayct and ngayct <= '" + denngay + "' ";
					System.out.println("Update ERP_NHAP_XUAT_TON: " + query);
					db.update(query);
					
					query = "update ERP_NHAP_XUAT_TON_PROVENCE set thanhtienTon = soluongTon * dongiaTon " +
							"where sanpham_fk = '" + rs.getString("sanpham_fk") + "' and '" + tungay + "' <= ngayct and ngayct <= '" + denngay + "' ";
					db.update(query);
				}
			} 
			catch (SQLException e) {}
			
		}
		
	}

	public static String getMonth(String date, int sothang)
	{
		String[] arr = date.split("-");
		
		Calendar lich = Calendar.getInstance();
		lich.set(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]) - 1, Integer.parseInt(arr[2]));
		lich.add(Calendar.MONTH, sothang);
		
		String thang = Integer.toString(lich.get(Calendar.MONTH) + 1);
		if((lich.get(Calendar.MONTH) + 1) < 10)
			thang = "0" + thang;
		
		String ngay = Integer.toString(lich.get(Calendar.DATE));
		if(lich.get(Calendar.DATE) < 10)
			ngay = "0" + ngay;
		
		
		String kq = Integer.toString(lich.get(Calendar.YEAR)) + "-" + thang + "-" + ngay;
		return kq;
	}
	
	public static boolean KhoaSoThang(String tungay, String denngay, String thang, String nam, dbutils db)
	{
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "insert erp_tonkhothang(khott_fk, sanpham_fk, soluong, thanhtienTon, booked, available, thang, nam) " +
					"select tondau.khott_fk, tondau.sanpham_fk, tondau.tondauky + isnull(nhapxuat.tongnhap, '0') - isnull(nhapxuat.tongxuat, '0') as soluong, " +
						"tondau.thanhtiendauky + isnull(nhapxuat.thanhtiennhap, '0') - isnull(nhapxuat.thanhtienxuat, '0') as thanhtienTon, " +
						"0 as booked, 0 as avialable, " + thang + " as thang, " + nam + " as nam " +
					"from ( " +
						"select khott_fk, sanpham_fk, giaton * soluong as thanhtiendauky, soluong as tondauky  " +
						"from erp_tonkhothang where sanpham_fk in (select pk_seq from sanpham) and thang = '05' and nam = '2012') tondau  " +
					"left join ( " +
						"select kho_fk, sanpham_fk, sum(isnull(soluongnhap, '0')) as tongnhap, sum(isnull(soluongnhap, '0') * isnull(dongianhap, '0')) as thanhtiennhap, " +
						"sum(isnull(soluongxuat, '0')) as tongxuat, sum(isnull(soluongxuat, '0') * isnull(dongiaxuat, '0')) as thanhtienxuat " +
						"from ERP_NHAP_XUAT_TON where sanpham_fk in (select pk_seq from sanpham) and ngayct >= '" + tungay + "' and ngayct <= '" + denngay + "'  " +
						"group by kho_fk, sanpham_fk  ) nhapxuat  " +
					"on tondau.khott_fk = nhapxuat.kho_fk and tondau.sanpham_fk = nhapxuat.sanpham_fk ";
			
			System.out.println("1.Lay ton kho thang: " + query);
			if(!db.update(query))
			{
				System.out.println("115.Khong the Khoa so Thang: " + query);
				db.getConnection().rollback();
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) {}
		
		return true;
	}
	
	
}
