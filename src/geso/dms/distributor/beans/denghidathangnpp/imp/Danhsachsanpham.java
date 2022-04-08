package geso.dms.distributor.beans.denghidathangnpp.imp;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import geso.dms.distributor.beans.denghidathangnpp.IDanhsachsanpham;
import geso.dms.distributor.db.sql.dbutils;

public class Danhsachsanpham implements IDanhsachsanpham , Serializable{
   
   String masp;
   String IDSanPham;
   String tensp;
   String tondau;
   
   String  bantheongay;
   String tonngay;
   String dukienban;
   String denghi;
   String donvi;
   String dongia;
   String tongtien;
   String dadat;
   String conlai;
   String soluong;
   String tonicp;
   String nppId;
   String thieuhang;
   int SoLuongBanTuanTruoc=0;
   int TonCuoi;
   dbutils db;
   
	public Danhsachsanpham()
	{
		   this.masp="";
		   this.tensp="";
		   this.tondau="";
		   this.bantheongay ="";
		   this.tonngay="";
		   this.dukienban="";
		   this.denghi="";
		   this.donvi="";
		   this.dongia="";
		   this.tongtien="";
		   this.dadat="";
		   this.conlai="";
		   this.soluong ="0";
		   this.tonicp ="0";
		   this.nppId="";
		   this.thieuhang ="0";
		   db = new dbutils();
	}
	
	public void setMasp(String masp) {
		
		this.masp =masp;
	}

	
	public String getMasp() {
		
		return this.masp;
	}

	
	public void setTensp(String tensp) {
		
		this.tensp = tensp;
	}

	
	public String getTensp() {
		
		return this.tensp;
	}

	
	public void setTondau(String tondau) {
		
		this.tondau = tondau;
	}

	
	public String geTondau() {
		
		return this.tondau;
	}

	
	public void setBantheongay(String bantheongay) {
		
		this.bantheongay = bantheongay;
	}

	
	public String getBantheongay() {
		
		return this.bantheongay;
	}

	public void setTonngay(String tonngay) {
		
		this.tonngay = tonngay;
	}

	
	public String getTonngay() {
		
		return this.tonngay;
	}

	
	public void setDukienban(String dukienban) {
		
		this.dukienban = dukienban;
	}

	
	public String getDukienban() {
		
		return this.dukienban;
	}

	
	public void setDenghi(String denghi) {
		
		this.denghi = denghi;
	}

	
	public String getDenghi() {
		
		return this.denghi;
	}

	
	public void setDonvi(String donvi) {
		
		this.donvi = donvi;
	}

	
	public String getDonvi() {
		
		return this.donvi;
	}

	
	public void setDongia(String dongia) {
		
		this.dongia= dongia;
	}

	
	public String getDongia() {
		
		return this.dongia;
	}

	
	public void setTongtien(String tongtien) {
		
		this.tongtien = tongtien;
	}

	
	public String getTongtien() {
		
		return this.tongtien;
	}

	
	public void setDadat(String dadat) {
		
		this.dadat = dadat;
	}

	
	public String getDadat() {
		
		return this.dadat;
	}

	
	public void setConlai(String conlai) {
		
		this.conlai = conlai;
	}

	
	public String getConlai() {
		
		return this.conlai;
	}

	public void setSoluong(String soluong) {
		this.soluong = soluong;
	}

	public String getSoluong() {
		return this.soluong;
	}
	
	public void setTonicp(String tonicp) {
	   this.tonicp = tonicp;	
	}
	public String getTonicp() {
    return this.tonicp;
	}

public void setnppId(String nppId) {
	this.nppId = nppId;
}
public String getnppId() {
	return this.nppId;
}


public void init() {
	   String sql = "select * from tonkhoicp where masp in (select ma from sanpham where pk_seq ='"+ this.masp +"') and kho in (select khosap from nhaphanphoi where pk_seq ='"+ this.nppId +"')";
	   System.out.println(sql);
	   ResultSet rs = db.get(sql);
	   if(rs!=null)
	   try {
		rs.next();
		this.tonicp = rs.getString("available");
		float h =Float.parseFloat(this.tonicp) - Float.parseFloat(this.soluong);
		if(h < 0)
		{
			this.thieuhang = "1";
		}
	} catch(Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}


public void setthieuhang(String thieuhang) {
	
	this.thieuhang = thieuhang;
}


public String getthieuhang() {
	
	return this.thieuhang;
}

@Override
public void setSL_BanTuanTruoc(int sl_bantuantruoc) {
	// TODO Auto-generated method stub
	this.SoLuongBanTuanTruoc= sl_bantuantruoc;
}

@Override
public int getSL_BanTuanTruoc() {
	// TODO Auto-generated method stub
	return this.SoLuongBanTuanTruoc;
}

@Override
public void setIdSanPham(String idsanpham) {
	// TODO Auto-generated method stub
	this.IDSanPham=idsanpham;
}

@Override
public String getIdSanPham() {
	// TODO Auto-generated method stub
	return this.IDSanPham;
}

@Override
public int getTonCuoi() {
	// TODO Auto-generated method stub
	return this.TonCuoi;
}

@Override
public void setTonCuoi(int toncuoi) {
	// TODO Auto-generated method stub
	this.TonCuoi=toncuoi;
}

@Override
public void DBclose() {
	// TODO Auto-generated method stub
	if(this.db != null) this.db.shutDown();
}
  
}
