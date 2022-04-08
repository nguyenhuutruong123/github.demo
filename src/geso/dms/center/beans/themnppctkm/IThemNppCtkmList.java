package geso.dms.center.beans.themnppctkm;

import geso.dms.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IThemNppCtkmList  extends IPhan_Trang, Serializable
{
	public String getUserId();
	public void setUserId(String userId);

	public String getTungay();
	public void setTungay(String Tungay);

	public String getDenngay();
	public void setDenngay(String Denngay);

	public String getNguoitao();
	public void setNguoitao(String nguoitao);

	public String getNguoisua();
	public void setNguoisua(String nguoisua);

	public String getTrangthai();
	public void setTrangthai(String trangthai);

	public String getMsg();
	public void setMsg(String msg);

	public ResultSet getDataRs();
	public void setDataRs(ResultSet dataRs);

	public String getChanhoadonId();
	public void setChanhoadonId(String chanhoadonId);

	public void init(String query);
	public void DbClose();

	public List<Object> getDataSearch();
	public void setDataSearch(List<Object> dataSearch);
}
