package geso.dms.center.beans.donhangip;

import java.io.Serializable;

public interface ISanphamIP extends Serializable
{
	public String getId();
	public void setId(String id);
	public String getMasanpham();
	public void setMasanpham(String masp);
	public String getTensanpham();
	public void setTensanpham(String tensp);
	public String getTonhientai();
	public void setTonhientai(String tonhientai);
	public String getSoluong();
	public void setSoluong(String soluong);
	public String getDonvitinh();
	public void setDonvitinh(String donvitinh);
	
	public String getQuydoi();
	public void setQuydoi(String Quydoi);
	
	public String getDongia();
	public void setDongia(String dongia);
	public String getCTKM();
	public void setCTKM(String ctkm);	
	public String getChietkhau();
	public void setChietkhau(String chietkhau);
	public String getChietkhauTT();
	public void setChietkhauTT(String chietkhautt);
	public String getChietkhauDLN();
	public void setChietkhauDLN(String chietkhaudln);
	public String getChietkhauDH();
	public void setChietkhauDH(String chietkhaudh);
	public String getBarcode();
	public void setBarcode(String barcode);
	
	public String getScheme();
	public void setScheme(String Scheme);
	
	public String getSoluongton();
	public void setSoluongton(String Soluongton);
	
	public String getGiagoc();
	public void setGiagoc(String giagoc);
	
	public String getBgId();
	public void setBgId(String bgId);
}
