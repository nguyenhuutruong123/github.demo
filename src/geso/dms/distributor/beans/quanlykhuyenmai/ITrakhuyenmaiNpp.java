package geso.dms.distributor.beans.quanlykhuyenmai;
import java.util.List;

import geso.dms.distributor.beans.donhang.ISanpham;

public interface ITrakhuyenmaiNpp 
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getCtkmId();
	public void setCtkmId(String ctkmId);
	public String getTkmId();
	public void setTkmId(String tkmId);
	
	public String getScheme();
	public void setScheme(String scheme);
	public String getCtkmDiengiai();
	public void setCtkmDiengiai(String diengiai);
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	public void setMsg(String Msg);
	public String getMsg();
	
	public List<ISanpham> getSpList();
	public void setSpList(List<ISanpham> spList);
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	
	public void setTrangthai(String trangthai);
	public String getTrangthai();
	
	public void init();
	public boolean save();
	public boolean remove();
	public void DbClose();
}
