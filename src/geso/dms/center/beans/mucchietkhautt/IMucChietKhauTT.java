package geso.dms.center.beans.mucchietkhautt;

import java.util.List;

public interface IMucChietKhauTT {
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String id);
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public double getChietKhau();
	public void setChietKhau(double sotienno);
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
	public void setListMucChietKhau(String sql);
	public List<IMucChietKhauTT> getListMucChietKhau();
	
	public void setListNhaPhanPhoi(List<IMucChietKhauTT_NhaPP> listnpp);
	public List<IMucChietKhauTT_NhaPP> getListNhaPhanPhoi();
	public void setListNhaPhanPhoiInit();
	
	public boolean SaveMucChietKhauTT();
	public boolean EditMucChietKhauTT();
	public boolean DeleteMucChietKhauTT();
}
