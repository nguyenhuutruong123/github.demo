package geso.dms.center.beans.nhomcttrungbay;

import geso.dms.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface INhomCTTrungBayList  extends Serializable, IPhan_Trang
{
	public String getId();

	public void setId(String id);

	public String getMa();

	public void setMa(String ma);

	public String getTen();

	public void setTen(String ten);

	public String getMsg();

	public void setMsg(String msg);

	public String getUserId();

	public void setUserId(String userId);

	public void closeDB();

	public void init(String query);

	public void createRs();

	public ResultSet getNhomRs();

	public void setNhomRs(ResultSet nhomRs);
}
