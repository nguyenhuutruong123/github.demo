package geso.dms.distributor.beans.quanlykhuyenmai;

import geso.dms.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface ITrakhuyenmaiNppList  extends Serializable, IPhan_Trang
{
	public String getUserId();
	public void setUserId(String userId);

	public String getScheme();
	public void setScheme(String scheme);
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	
	public ResultSet getTrakmList();
	public void setTrakmList(ResultSet tkm);
	
	public void init(String search);
	public void DBclose();
	public void setMsg(String Msg);
	public String getMsg();
}
