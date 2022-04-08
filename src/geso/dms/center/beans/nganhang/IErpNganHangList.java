package geso.dms.center.beans.nganhang;

import geso.dms.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErpNganHangList extends Serializable, IPhan_Trang
{
	public String getID();

	public String getMA();

	public String getTEN();

	public String getNGAYTAO();

	public String getNGAYSUA();

	public String getNGUOITAO();

	public String getNGUOISUA();

	public String getMsg();

	public String gettrangthai();

	public ResultSet getNhList();

	public void setID(String ID);

	public void setMA(String MA);

	public void setTEN(String TEN);

	public void setNGAYTAO(String NGAYTAO);

	public void setNGAYSUA(String NGAYSUA);

	public void setNGUOITAO(String NGUOITAO);

	public void setNGUOISUA(String NGUOISUA);

	public void setMsg(String Msg);

	public void setTrangthai(String trangthai);

	public void setNhList(ResultSet NhList);

	public void init();

	public boolean CheckReferences(String column, String table);

	public boolean DeleteNganHang();

	public void setUserid(String user);

	public String getUserid();

	public void setUserTen(String userten);

	public String getUserTen();

	public void DBClose();
}
