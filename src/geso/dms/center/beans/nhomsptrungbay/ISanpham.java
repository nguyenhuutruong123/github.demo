package geso.dms.center.beans.nhomsptrungbay;

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
	public String getDongia();
	public void setDongia(String dongia);
}

