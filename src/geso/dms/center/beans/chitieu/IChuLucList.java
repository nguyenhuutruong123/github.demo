package geso.dms.center.beans.chitieu;

import geso.dms.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IChuLucList extends  Serializable, IPhan_Trang
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getThang();
	public void setThang(String thang);
	
	public String getNam();
	public void setNam(String nam);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public void init(String query);
	
	public ResultSet getDataRs();
	public void setDataRs(ResultSet dataRs);
	
	public String getTungay();
	public void setTungay(String tungay);
	
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getView();
	public void setView(String view);

}
