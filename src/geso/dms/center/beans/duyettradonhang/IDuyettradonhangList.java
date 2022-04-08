package geso.dms.center.beans.duyettradonhang;

import geso.dms.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IDuyettradonhangList extends Serializable, IPhan_Trang
{
	public String getMsg();

	public void setMsg(String Msg);

	public String getUserId();

	public void setUserId(String userId);

	public ResultSet getNppRs();

	public void setNppRs(ResultSet nppRs);

	public String getNppId();

	public void setNppId(String nppId);

	public String getTrangthai();

	public void setTrangthai(String trangthai);

	public String getTungay();

	public void setTungay(String tungay);

	public String getDenngay();

	public void setDenngay(String denngay);

	public ResultSet getdhtvList();

	public void setdhtvList(ResultSet dhtvList);

	public void init(String search);

	public void DBclose();
}
