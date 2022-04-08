package geso.dms.distributor.beans.donhangtrave;

import java.io.Serializable;

public interface ISanpham extends Serializable
{
	public String getId();
	public void setId(String id);
	public String getMasanpham();
	public void setMasanpham(String masp);
	public String getTensanpham();
	public void setTensanpham(String tensp);
	public String getSoluong();
	public void setSoluong(String soluong);
	public String getDonvitinh();
	public void setDonvitinh(String donvitinh);
	public String getDongia();
	public void setDongia(String dongia);
	public String getCTKM();
	public void setCTKM(String ctkm);	
	public String getChietkhau();
	public void setChietkhau(String chietkhau);
	public String getTongtien();
	public void setTongtien(String tongtien);
}
