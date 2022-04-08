package geso.dms.distributor.beans.xuatkho;

import geso.dms.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IXuatKhoList extends  Serializable, IPhan_Trang
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getNppId();
	public void setNppId(String nppId);
	
	public String getNppTen();
	public void setNppTen(String nppTen);

	public ResultSet getDhList();
	public void setDhList(ResultSet dhList);
	
	public String getTungay();
	public void setTungay(String tungay);
	
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getMsg();
	public void setMsg(String msg);

	public void createDdhlist(String query);
	public void DBclose();
}



