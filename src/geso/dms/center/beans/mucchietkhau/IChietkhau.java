package geso.dms.center.beans.mucchietkhau;

import java.sql.ResultSet;

public interface IChietkhau 
{
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String id);
	public String getSotien();
	public void setSotien(String sotien);
	public String getChietkhau();
	public void setChietkhau(String chietkhau);
	public String getNgaytao();
	public void setNgaytao(String ngaytao);
	public String getNguoitao();
	public void setNguoitao(String nguoitao);
	public String getNgaysua();
	public void setNgaysua(String ngaysua);
	public String getNguoisua();
	public void setNguoisua(String nguoisua);	
	public String getMessage();
	public void setMessage(String msg);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public ResultSet getNppRs();
	public void setNppRs(ResultSet nppRs);
	public String getNppIds();
	public void setNppIds(String nppIds);
	
	public boolean CreateMck();
	public boolean UpdateMck();
	public void DBClose();
	public void createRs();
}
