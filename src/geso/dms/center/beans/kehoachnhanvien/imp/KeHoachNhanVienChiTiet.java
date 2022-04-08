package geso.dms.center.beans.kehoachnhanvien.imp;

import geso.dms.center.beans.kehoachnhanvien.*;

public class KeHoachNhanVienChiTiet implements IKeHoachNhanVienChiTiet
{
	private static final long serialVersionUID = -9217977546733610214L;
	String id = "";
	String khId = "";
	String ngay = "";
	String loai = "", nppId = "", tinhId = "", quanId = "", lat = "", lon = "", diachi = "", ghichu = "", ghichu2 = "";
	private String nppTBHId;
	private String ddkdId;
	private String ghichuTBH;
	private String tbhId;
	String tyle = "";
	String thoidiem = "";
	
	public KeHoachNhanVienChiTiet(String id)
	{
		this.khId = id;
	}
	
	public String getId() 
	{
		return this.id;
	}

	public void setId(String id) 
	{
		this.id = id;
	}
	
	public String getKeHoachId() 
	{
		return this.khId;
	}

	public void setKeHoachId(String khId) 
	{
		this.khId = khId;
	}
	
	public String getNgay() 
	{
		return this.ngay;
	}

	public void setNgay(String ngay) 
	{
		this.ngay = ngay;
		if(ngay != null || ngay.length() != 0)
			this.ngay = Integer.parseInt(ngay) + "";
	}

	@Override
	public String getLoai() {
		return this.loai;
	}

	@Override
	public void setLoai(String loai) {
		this.loai = loai;
	}

	@Override
	public String getNppId() {
		return this.nppId;
	}

	@Override
	public void setNppId(String nppId) {
		this.nppId = nppId;
	}

	@Override
	public String getTinhId() {
		return this.tinhId;
	}

	@Override
	public void setTinhId(String tinhId) {
		this.tinhId = tinhId;
	}

	@Override
	public String getQuanHuyenId() {
		return quanId;
	}

	@Override
	public void setQuanHuyenId(String quanId) {
		this.quanId = quanId;
	}

	@Override
	public String getLat() {
		return lat;
	}

	@Override
	public void setLat(String lat) {
		this.lat = lat;
	}

	@Override
	public String getLon() {
		return lon;
	}

	@Override
	public void setLon(String lon) {
		this.lon = lon;
	}

	@Override
	public String getDiaChi() {
		return this.diachi;
	}

	@Override
	public void setDiaChi(String diachi) {
		this.diachi = diachi;
	}

	@Override
	public String getGhiChu() {
		return this.ghichu;
	}

	@Override
	public void setGhiChu(String ghichu) {
		this.ghichu = ghichu;
	}

	@Override
	public String getGhiChu2() {
		return this.ghichu2;
	}

	@Override
	public void setGhiChu2(String ghichu2) {
		this.ghichu2 = ghichu2;
	}

	@Override
	public String getNppTBHId() {
		return this.nppTBHId;
	}

	@Override
	public void setNppTBHId(String id) {
		this.nppTBHId = id;
	}

	@Override
	public String getDDKDId() {
		return this.ddkdId;
	}

	@Override
	public void setDDKDId(String ddkdid) {
		this.ddkdId = ddkdid;
	}

	@Override
	public String getGhiChuTBH() {
		return this.ghichuTBH;
	}

	@Override
	public void setGhiChuTBH(String ghichu) {
		this.ghichuTBH = ghichu;
	}

	@Override
	public String getTBHId() {
		return this.tbhId;
	}

	@Override
	public void setTBHId(String tbhId) {
		this.tbhId = tbhId;
	}

	@Override
	public void setThoidiem(String value) {
		this.thoidiem = value;
	}

	@Override
	public String getThoidiem() {
		return this.thoidiem;
	}

	@Override
	public void setTyle(String value) {
		this.tyle = value;
	}

	@Override
	public String getTyle() {
		return this.tyle;
	}
}

