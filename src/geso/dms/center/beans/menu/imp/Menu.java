package geso.dms.center.beans.menu.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.dms.center.beans.kenhbanhang.imp.Kenhbanhang;
import geso.dms.center.beans.menu.IMenu;
import geso.dms.center.db.sql.dbutils;

public class Menu implements IMenu 
{
   
	String userId;
	String NppNhanvien;
	String KenhNhanvien;
	String SanphamNhanvien;
	int mang[];
	public Menu()
       	{
		NppNhanvien ="";
		KenhNhanvien ="";
		SanphamNhanvien ="";
 		this.userId ="";
		mang = new int[105];
		for(int i=0;i<=100;i++) mang[i] = 0;
	}
	public void setuserId(String userId) {
	 	this.userId = userId;
	}

	public String getuserId() {
	
		return this.userId;
	}

	public void setMang(int[] mang) {
		this.mang = mang;
	}

	public int[] getMang() {
		
		return this.mang;
	}

	public void init() {
		dbutils db = new dbutils();
		String sql ="select a.UngDung_fk from nhomquyen a,phanquyen b where a.dmq_fk = b.DMQ_fk and b.NhanVien_fk='"+ this.userId +"'";
		System.out.println(sql);
		ResultSet rs = db.get(sql);
					try {
						while(rs.next())
						{
							int h = Integer.parseInt(rs.getString("UngDung_fk"));
							mang[h] = 1;
						}
					} catch(Exception e) {
						
						e.printStackTrace();
					}		
					//pk_seq lam ma cua san pham
	NppNhanvien =" pk_seq in (select sanpham_fk from nhanvien_sanpham where nhanvien_fk ='"+ this.userId +"')";
	//pk_seq ma nha  phan phoi
	SanphamNhanvien=" pk_seq in (select ncc_fk from phamvihoatdong where nhanvien_fk ='"+ this.userId +"' )";
	//pk_seq ma kenh
	KenhNhanvien = "pk_seq in (select kenh_fk from nhanvien_kenh where nhanvien_fk ='"+ this.userId +"')";
      }
	
	public String getNppNhanvien() {
	return NppNhanvien;
	}
   public String getSanphamNhanvien() {
		return  SanphamNhanvien;
	}
	
	public String getKenhnNhanvien() {
		
		return KenhNhanvien;
	}

}
