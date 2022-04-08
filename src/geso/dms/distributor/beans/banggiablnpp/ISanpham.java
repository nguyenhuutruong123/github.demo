package geso.dms.distributor.beans.banggiablnpp;

import java.io.Serializable;

public interface ISanpham extends Serializable
{
	public String getId();

	public void setId(String id);

	public String getMasanpham();

	public void setMasanpham(String masp);

	public String getTensanpham();

	public void setTensanpham(String tensp);

	public String getGiabanlechuan();

	public void setGiabanlechuan(String gblchuan);

	public String getGiaban();

	public void setGiaban(String giaban);

	public String getGiathung();

	public void setGiathung(String giathung);

	public String getQuycach();

	public void setQuycach(String quycach);
}
