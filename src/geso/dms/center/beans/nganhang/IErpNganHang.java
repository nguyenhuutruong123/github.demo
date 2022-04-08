package geso.dms.center.beans.nganhang;



public interface IErpNganHang
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

	public void setID(String ID);

	public void setMA(String MA);

	public void setTEN(String TEN);

	public void setNGAYTAO(String NGAYTAO);

	public void setNGAYSUA(String NGAYSUA);

	public void setNGUOITAO(String NGUOITAO);

	public void setNGUOISUA(String NGUOISUA);

	public void setMsg(String Msg);

	public void setTrangthai(String trangthai);

	public void init();

	public boolean UpdateMa();

	public boolean themMoiMa();

	public void setUserid(String user);

	public String getUserid();

	public void setUserTen(String userten);

	public String getUserTen();

	public boolean CheckUnique();

	public void DBClose();

}
