package geso.dms.center.beans.vung;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IVungmien extends Serializable 
{
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String id);
	public String getVungmien();
	public void setVungmien(String vungmien);
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	public String getNgaytao();
	public void setNgaytao(String ngaytao);
	public String getNguoitao();
	public void setNguoitao(String nguoitao);
	public String getNgaysua();
	public void setNgaysua(String ngaysua);
	public String getNguoisua();
	public void setNguoisua(String nguoisua);	
	public String getMa();
	public void setMa(String Ma);	
	public String getMessage();
	public void setMessage(String msg);
	public void setBm(String bm);
	public String getBm();
	public void setBms(ResultSet bms);
	public ResultSet getBms();
	public boolean CreateVm();
	public boolean UpdateVm();
	public void closeDB();
	
}
