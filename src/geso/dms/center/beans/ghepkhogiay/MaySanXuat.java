package geso.dms.center.beans.ghepkhogiay;

import java.util.Hashtable;

public class MaySanXuat {
	private String id;
	private String ma;
	private String ten;
	private float khoGiayNguyenLieu;
	private float congSuatMay;
	private float trongLuong;
	private String ngay;
	//Ngay co he bat dau dua lenh san xuat vao
	private int STTHienTai;
	private Hashtable<Float, Float> dinhLuongHash;
	
	public MaySanXuat()
	{
		this.id = "";
		this.ma = "";
		this.ten = "";
		this.khoGiayNguyenLieu = 0;
		this.congSuatMay = 0;
		this.trongLuong = 0;
		this.ngay = "";
		this.STTHienTai = 0;
		this.dinhLuongHash = new Hashtable<Float, Float>();
	}
	
	public MaySanXuat(String id, String ma, float khoGiayNguyenLieu, float congSuatMay)
	{
		this.id = id;
		this.ma = ma;
		this.khoGiayNguyenLieu = khoGiayNguyenLieu;
		this.congSuatMay = congSuatMay;
		this.trongLuong = 0;
		this.STTHienTai = 0;
		this.ngay = "";
		this.dinhLuongHash = new Hashtable<Float, Float>();
	}
	
	public boolean isExitDinhLuong(float dinhLuong)
	{
		return (this.dinhLuongHash.containsKey(dinhLuong));
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMa() {
		return ma;
	}
	public void setMa(String ma) {
		this.ma = ma;
	}
	public float getKhoGiayNguyenLieu() {
		return khoGiayNguyenLieu;
	}
	public void setKhoGiayNguyenLieu(float khoGiayNguyenLieu) {
		this.khoGiayNguyenLieu = khoGiayNguyenLieu;
	}
	public float getCongSuatMay() {
		return congSuatMay;
	}
	public void setCongSuatMay(float congSuatMay) {
		this.congSuatMay = congSuatMay;
	}
	public Hashtable<Float, Float> getDinhLuongHash() {
		return dinhLuongHash;
	}
	public void setDinhLuongHash(Hashtable<Float, Float> dinhLuongHash) {
		this.dinhLuongHash = dinhLuongHash;
	}

	public void setTrongLuong(float trongLuongHienTai) {
		this.trongLuong = trongLuongHienTai;
	}

	public float getTrongLuong() {
		return trongLuong;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getTen() {
		return ten;
	}

	public void setSTTHienTai(int sTTHienTai) {
		STTHienTai = sTTHienTai;
	}

	public int getSTTHienTai() {
		return STTHienTai;
	}

	public void setNgay(String ngay) {
		this.ngay = ngay;
	}

	public String getNgay() {
		return ngay;
	}
}
