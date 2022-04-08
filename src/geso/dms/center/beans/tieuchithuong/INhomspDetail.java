package geso.dms.center.beans.tieuchithuong;

import geso.dms.center.beans.dieukienkhuyenmai.ISanpham;

import java.util.List;

public interface INhomspDetail 
{
	public String getId();
	public void setId(String id);
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	public String getTongluong();
	public void setTongluong(String tongluong);
	
	public List<ISanpham> getSpList();
	public void setSpList(List<ISanpham> spList);
}
