package geso.dms.center.beans.ctkhuyenmai;

import geso.dms.center.beans.dieukienkhuyenmai.ISanpham;

import java.util.List;

public interface IDieukienDetail 
{
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	public String getLoaidieukien();
	public void setLoaidieukien(String loaidieukien);
	public String getHinhthuc();
	public void setHinhthuc(String hinhthuc);
	public String getSotong();
	public void setSotong(String sotong);
	public String getNhomspId();
	public void setNhomspId(String nspId);
	
	public List<ISanpham> getSpList();
	public void setSpList(List<ISanpham> spList);
	
}
