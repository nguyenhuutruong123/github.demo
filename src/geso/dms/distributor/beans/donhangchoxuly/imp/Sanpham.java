package geso.dms.distributor.beans.donhangchoxuly.imp;

import java.io.Serializable;

import geso.dms.distributor.beans.donhangchoxuly.ISanpham;

public class Sanpham implements ISanpham, Serializable
{	
	private static final long serialVersionUID = -9217977546733610214L;
	String id;
	String masp;
	String tensp;
	String soluong;
	String tonhientai;
	String donvitinh;
	String dongia;
	String ctkm;
	String chietkhau;
	String chietkhautt;
	String chietkhaudln;
	String chietkhaudh;
	String barcode;

	public Sanpham()
	{
		this.id = "";
		this.masp = "";
		this.tensp = "";
		this.soluong = "";
		this.donvitinh = "";
		this.dongia = "";
		this.ctkm = "";
		this.chietkhau = "";
		this.chietkhautt = "";
		this.chietkhaudln = "";
		this.chietkhaudh = "";
		this.tonhientai = "";
		this.barcode="";
	}

	public Sanpham(String[] param)
	{
		this.id = param[0];
		this.masp = param[1];
		this.tensp = param[2];
		this.soluong = param[3];
		this.donvitinh = param[4];
		this.dongia = param[5];
		this.ctkm = param[6];
		this.chietkhau = param[7];
		this.chietkhaudln = param[8];
		this.chietkhautt = param[9];
		this.chietkhaudh = param[10];

		this.tonhientai = "";
		if(param.length >11){
			this.barcode=param[11];
		}

	}

	public Sanpham(String spId, String spMa, String spTen, String soluong, String dvt, String dongia, String ctkm, String chietkhau)
	{
		this.id = spId;
		this.masp = spMa;
		this.tensp = spTen;
		this.soluong = soluong;
		this.donvitinh = dvt;
		this.dongia = dongia;
		this.ctkm = ctkm;
		this.chietkhau = chietkhau;
		this.tonhientai = "";
	}

	public String getId() 
	{	
		return this.id;
	}

	public void setId(String id) 
	{
		this.id = id;	
	}

	public String getMasanpham()
	{	
		return this.masp;
	}

	public void setMasanpham(String masp)
	{
		this.masp = masp;		
	}

	public String getTensanpham() 
	{	
		return this.tensp;
	}

	public void setTensanpham(String tensp) 
	{
		this.tensp = tensp;	
	}

	public String getSoluong()
	{
		return this.soluong;
	}

	public void setSoluong(String soluong) 
	{
		this.soluong = soluong;
	}

	public String getDonvitinh() 
	{
		return this.donvitinh;
	}

	public void setDonvitinh(String donvitinh) 
	{
		this.donvitinh = donvitinh;		
	}

	public String getCTKM() 
	{
		return this.ctkm;
	}

	public void setCTKM(String ctkm) 
	{
		this.ctkm = ctkm;
	}

	public String getDongia() 
	{
		return this.dongia;
	}

	public void setDongia(String dongia) 
	{
		this.dongia = dongia;
	}

	public String getChietkhau()
	{
		return this.chietkhau;
	}

	public void setChietkhau(String chietkhau) 
	{
		this.chietkhau = chietkhau;
	}

	public String getTonhientai() 
	{
		return this.tonhientai;
	}

	public void setTonhientai(String tonhientai) 
	{
		this.tonhientai = tonhientai;
	}

	@Override
	public String getBarcode() {

		return this.barcode;
	}

	@Override
	public void setBarcode(String barcode_) {

		this.barcode=barcode_;
	}

	String giagoc;

	public String getGiagoc()
	{
		return giagoc;
	}

	public void setGiagoc(String giagoc)
	{
		this.giagoc = giagoc;
	}

	String scheme;
	@Override
	public String getScheme()
	{
		return this.scheme;
	}

	@Override
	public void setScheme(String Scheme)
	{
		this.scheme=Scheme;
	}


	String bgId;

	public String getBgId()
	{
		return bgId;
	}

	public void setBgId(String bgId)
	{
		this.bgId = bgId;
	}

	@Override
	public String getChietkhauTT() {

		return this.chietkhautt;
	}

	@Override
	public void setChietkhauTT(String chietkhautt) {

		this.chietkhautt = chietkhautt;
	}

	@Override
	public String getChietkhauDLN() {

		return this.chietkhaudln;
	}

	@Override
	public void setChietkhauDLN(String chietkhaudln) {

		this.chietkhaudln = chietkhaudln;
	}

	@Override
	public String getChietkhauDH() {

		return this.chietkhaudh;
	}

	@Override
	public void setChietkhauDH(String chietkhaudh) {

		this.chietkhaudh = chietkhaudh;
	}

	String DaGiao,ConLai;

	public String getDaGiao()
	{
		return DaGiao;
	}

	public void setDaGiao(String daGiao)
	{
		DaGiao = daGiao;
	}

	public String getConLai()
	{
		return ConLai;
	}

	public void setConLai(String conLai)
	{
		ConLai = conLai;
	}



}
