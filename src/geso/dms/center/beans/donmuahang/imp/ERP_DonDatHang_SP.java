package geso.dms.center.beans.donmuahang.imp;

import geso.dms.center.beans.donmuahang.IERP_DonDatHang_SP;

public class ERP_DonDatHang_SP implements IERP_DonDatHang_SP {
	String Id;
	String IdSanPham;
	String TenSanPham;
	double DonGia;
	double ChietKhau;
	double Vat;
	int SoLuong;
	int SoLuongDat;
	String MaSanPham;
	String DonViTinh;
	double thanhtien;
	String ctkmid;
	String SHEME;
	int soluongton;
	int soluongduyet;
	
	String trongluong = "";
	String thetich = "";
	String Khott;
	String TenKhoTT;
	String Quycach="";
	
	public String getId() {
		
		return this.Id;
	}

	
	public void setId(String id) {
		
		this.Id=id;
	}

	
	public String getIdSanPham() {
		
		return this.IdSanPham;
	}

	
	public void setIdSanPham(String idsanpham) {
		
		this.IdSanPham=idsanpham;
	}

	
	public String getTenSanPham() {
		
		return this.TenSanPham;
	}

	
	public void setTenSanPham(String tensanpham) {
		
		this.TenSanPham=tensanpham;
	}

	
	public void setSoLuong(int soluong) {
		
		this.SoLuong=soluong;
	}

	
	public int getSoLuong() {
		
		return this.SoLuong;
	}

	
	public double getDonGia() {
		
		return this.DonGia;
	}

	
	public void setDonGia(double dongia) {
		
		this.DonGia=dongia;
	}

	
	public void setVAT(double vat) {
		
		this.Vat=vat;
	}

	
	public double getVAT() {
		
		return this.Vat;
	}

	
	public void setChietKhau(double chietkhau) {
		
		this.ChietKhau=chietkhau;
	}

	
	public double getChietKhau() {
		
		return this.ChietKhau;
	}

	
	public double getThanhTien() {
		
		return (this.SoLuong* this.DonGia);
	}

	
	public String getMaSanPham() {
		
		return this.MaSanPham;
	}

	
	public void setMaSanPham(String masanpham) {
		
		this.MaSanPham=masanpham;
	}

	
	public void setDonViTinh(String donvitinh) {
		
		this.DonViTinh=donvitinh;
	}

	
	public String getDonViTinh() {
		
		return this.DonViTinh;
	}

	
	public void setSoLuongDat(int soluongdat) {
		
		this.SoLuongDat=soluongdat;
	}

	
	public int getSoLuongDat() {
		
		return this.SoLuongDat;
	}

	
	public void setThanhTien(double thanhtien) {
		
		this.thanhtien=thanhtien;
	}

	
	public void setCTKMID(String _ctkmid) {
		
		this.ctkmid=_ctkmid;
	}

	
	public String getCTKMId() {
		
		return this.ctkmid;
	}

	
	public void setSoluongton(int slton) {
		
		this.soluongton=slton;
	}

	
	public int getsoluongton() {
		
		return this.soluongton;
	}

	
	public void setSHEME(String ctkmid) 
	{
		this.SHEME=ctkmid;
	}

	
	public String getSHEME() {
		
		return this.SHEME;
	}


	
	public String getTrongluong() 
	{
		return this.trongluong;
	}


	public void setTrongluong(String trongluong) 
	{
		this.trongluong = trongluong;
	}


	public String getThetich() 
	{
		return this.thetich;
	}

	public void setThetich(String thetich) 
	{
		this.thetich = thetich;
	}


	@Override
	public String getKhoTT() {
		// TODO Auto-generated method stub
		return this.Khott;
	}


	@Override
	public void setKhoTT(String KhoTT) {
		// TODO Auto-generated method stub
		this.Khott=KhoTT;
	}


	@Override
	public String getTenKhoTT() {
		// TODO Auto-generated method stub
		return this.TenKhoTT;
	}


	@Override
	public void setTenKhoTT(String tenkho) {
		// TODO Auto-generated method stub
		this.TenKhoTT=tenkho;
	}


	@Override
	public void setSoluongduyet(int _soluongduyet) {
		// TODO Auto-generated method stub
		this.soluongduyet=_soluongduyet;
	}


	@Override
	public int getsoluongduyet() {
		// TODO Auto-generated method stub
		return this.soluongduyet;
	}


	@Override
	public String getQuyCach() {
		// TODO Auto-generated method stub
		return this.Quycach;
	}


	@Override
	public void setQuyCach(String Quycach) {
		// TODO Auto-generated method stub
		this.Quycach=Quycach;
	}
}
