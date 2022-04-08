package geso.dms.center.beans.kenhbanhang;

import java.io.Serializable;

public interface IKenhbanhang extends Serializable 
{
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String id);
	public String getKenhbanhang();
	public void setKenhbanhang(String kenhbanhang);
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
	public String getMessage();
	public void setMessage(String msg);
	public String getma();
	public void setma(String ma);
	
	public boolean CreateKbh();
	public boolean UpdateKbh();
	public void DBClose();
	
}