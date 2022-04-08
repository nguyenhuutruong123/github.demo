package geso.dms.center.beans.thaydoikhuyenmai;

import geso.dms.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IThayDoiKhuyenMaiList  extends Serializable, IPhan_Trang
{
	public ResultSet getNhaphangList();
	public void setNhaphangList(ResultSet nhaphangList);

	public String getUserId();
	public void setUserId(String userId);

	public String getTungay();
	public void setTungay(String tungay);

	public String getDenngay();
	public void setDenngay(String denngay);

	public String getTrangthai();
	public void setTrangthai(String trangthai);

	public void createNhaphanglist(String querystr);
	
	public void setMsg(String Msg);
	public String getMsg();

	public void DBclose();
	
	public List<Object> getDataSearch();
	public void setDataSearch(List<Object> dataSearch);
}
