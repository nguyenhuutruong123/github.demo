package geso.dms.center.beans.kho;

import java.io.Serializable;

public interface IKho extends Serializable {
	
	public String getId();

	public void setId(String id);
	
	public String getTen();
	
	public void setTen(String ten);
	
	public String getDiengiai();
	
	public void setDiengiai(String diengiai);

	public String getTrangthai();
	
	public void setTrangthai(String trangthai);
	
	public String getNgaytao();
	
	public void setNgaytao(String ngaytao);
	
	public String getNgaysua();
	
	public void setNgaysua(String ngaysua);
	
	public String getNguoitao();
	
	public void setNguoitao(String nguoitao);
	
	public String getNguoisua();
	
	public void setNguoisua(String nguoisua);
	
	public String getMessage();
	
	public void setMessage(String msg);
	
	public boolean saveNewKho();
	
	public boolean UpdateKho();
	public void DBClose();
	
   
}