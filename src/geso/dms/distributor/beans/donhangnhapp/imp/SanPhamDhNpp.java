package geso.dms.distributor.beans.donhangnhapp.imp;

import java.io.Serializable;
import java.sql.ResultSet;

import geso.dms.distributor.beans.donhangnhapp.ISanPhamDhNpp;
import geso.dms.distributor.db.sql.dbutils;

public class SanPhamDhNpp implements ISanPhamDhNpp, Serializable{

	String DonHangNpp="";
	String MaSanPham="";
	String TenSanPham="";
	String DVT="";
	String Kho="";
	double DonGia=0;
	int SoLuong=0;
	double ChietKhau=0;
	double ThanhTien=0;
	double ThanhTienCoCK=0;
	String IDSanPham="";
	int SoLuongNhan=0;
	dbutils db;
	
	public SanPhamDhNpp(){
		
		db = new dbutils();
	}
	
	@Override
	public void setDonHangNPP(String DonHangNpp) {
		// TODO Auto-generated method stub
		this.DonHangNpp=DonHangNpp;
	}

	@Override
	public String getDonHangNPP() {
		// TODO Auto-generated method stub
		return DonHangNpp;
	}

	@Override
	public void setMaSanPham(String MaSanPham) {
		// TODO Auto-generated method stub
		this.MaSanPham=MaSanPham;
		String sql="select pk_seq from sanpham where ma='"+this.MaSanPham+"'";
		
		ResultSet rs=db.get(sql);
		try
		{
		if(rs.next())
		{
			this.IDSanPham=rs.getString("pk_seq");
		}
		}
		catch(Exception er)
		{ 
			this.IDSanPham="";
			System.out.println("Error line 50 :Classname:  SanPhamDHNpp : "+er.toString());
		}
		finally{try {
			if(rs != null)
				rs.close();
		} catch (Exception e) {
			// TODO: handle exception
		}}
		
	}

	@Override
	public String getMaSanPham() {
		// TODO Auto-generated method stub
		return MaSanPham;
	}

	@Override
	public void setTenSanPham(String TenSanPham) {
		// TODO Auto-generated method stub
		this.TenSanPham=TenSanPham;
			
	}

	@Override
	public String getTenSanPham() {
		// TODO Auto-generated method stub
		return TenSanPham;
	}

	@Override
	public void setDVT(String DVT) {
		// TODO Auto-generated method stub
		this.DVT=DVT;
	}

	@Override
	public String getDVT() {
		// TODO Auto-generated method stub
		return DVT;
	}

	@Override
	public int getSoLuong() {
		// TODO Auto-generated method stub
		return SoLuong;
	}

	public void setGiaMua(double DonGia) {
		// TODO Auto-generated method stub
		this.DonGia=DonGia;
	}


	@Override
	public void setKho(String Kho) {
		// TODO Auto-generated method stub
		this.Kho=Kho;
	}

	@Override
	public String getKho() {
		// TODO Auto-generated method stub
		return Kho;
	}

	@Override
	public double getGiaMua() {
		// TODO Auto-generated method stub
		return this.DonGia;
	}

	@Override
	public void setSoLuong(int SoLuong) {
		// TODO Auto-generated method stub
		this.SoLuong=SoLuong;
	}

	@Override
	public void setChietKhau(double ChietKhau) {
		// TODO Auto-generated method stub
		this.ChietKhau=ChietKhau;
	}

	@Override
	public double getChietKhau() {
		// TODO Auto-generated method stub
		return ChietKhau;
	}

	@Override
	public void setThanhTien(double ThanhTien) {
		// TODO Auto-generated method stub
		this.ThanhTien=ThanhTien;
	}

	@Override
	public double getThanhTien() {
		// TODO Auto-generated method stub
		return this.ThanhTien;
	}

	@Override
	public void setThanhTienCoCK(double ThanhTienCoCK) {
		// TODO Auto-generated method stub
		this.ThanhTienCoCK=ThanhTienCoCK;
	}

	@Override
	public double getThanhTienCoCK() {
		// TODO Auto-generated method stub
		return this.ThanhTienCoCK;
	}

	@Override
	public boolean SavaSanPhamDhNpp() {
		// TODO Auto-generated method stub
		String sql="insert into donhangnpp_sanpham (sanpham_fk,donhangnpp_fk,soluong,kho_fk,giamua)value ("+this.getIdSanPham()+","+this.getDonHangNPP()+","+this.getSoLuong()+","+this.getKho()+","+this.getGiaMua()+" )";
		//System.out.println("Error line 49 :Classname:  SanPhamDHNpp : Cau lenh SQL " + sql);

		//dbutils db=new dbutils();
		db.update(sql);
		return false;
		
	}

	@Override
	public void setIdSanPham(String Idsanpham) {
		// TODO Auto-generated method stub
		this.IDSanPham=Idsanpham;
	}

	@Override
	public String getIdSanPham() {
		// TODO Auto-generated method stub
		return this.IDSanPham;
	}

	@Override
	public void setSoLuongNhan(int soluongnhan) {
		// TODO Auto-generated method stub
		this.SoLuongNhan=soluongnhan;
	}

	@Override
	public int getSoLuongNhan() {
		// TODO Auto-generated method stub
		return this.SoLuongNhan;
	}

	@Override
	public void DBclose() {
		// TODO Auto-generated method stub
		if(this.db != null)
			this.db.shutDown();
		
		
	}

}
