package geso.dms.center.beans.erp_chinhanh;

import geso.dms.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErp_chinhanh extends Serializable, IPhan_Trang
{
	public String getID();

	public String getMA();

	public String getTEN();

	public String getNGAYTAO();

	public String getNGAYSUA();

	public String getNGUOITAO();

	public String getNGUOISUA();

	public String getTRANGTHAI();

	public String getMsg();

	public ResultSet getRscn();

	public void setID(String ID);

	public void setMA(String MA);

	public void setTEN(String TEN);

	public void setNGAYTAO(String NGAYTAO);

	public void setNGAYSUA(String NGAYSUA);

	public void setNGUOITAO(String NGUOITAO);

	public void setNGUOISUA(String NGUOISUA);

	public void setTRANGTHAI(String TRANGTHAI);

	public void setMsg(String Msg);

	public void setUserTen(String userten);

	public String getUserTen();

	public void setUserid(String user);

	public String getUserid();

	public void init(String sql);

	public boolean CheckReferences(String column, String table);

	public boolean CheckUnique();
	
	public boolean Delete();

	void DBClose();

	boolean ThemMoiMa();

	boolean UpdateMa();

}
