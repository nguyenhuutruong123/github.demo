package geso.dms.center.beans.ngayle;

import java.io.Serializable;

public interface INgayLe extends Serializable {
	
	public void init();
	public String getId();
	public void setId(String id);
	public String getUserId();
	public void setUserId(String userId) ;
	public String getTen();
	public void setTen(String ten);
	
	public String getDiengiai() ;

	public void setDiengiai(String diengiai) ;

	public String getNgayle();

	public void setNgayle(String ngayle);
	//////////
	public String getSotaikhoan();
	public void setSotaikhoan(String sotaikhoan);
	public String getDienthoai();
	public void setDienthoai(String dienthoai);
	public String getFax();
	public void setFax(String fax);
	public String getNguoidaidien();
	public void setNguoidaidien(String nguoidaidien);
	/////////
	public String getDiachi();
	public void setDiachi(String diachi);
	
	public String getMasothue();
	public void setMasothue(String masothue);
	
	public String getTenviettat();
	public void setTenviettat(String tenviettat);
	
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
	public boolean saveNewNgayLe();
	
	public boolean UpdateNcc();
	public void DBClose();
   
}