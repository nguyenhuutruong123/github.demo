package geso.dms.center.beans.congthucdndh;

public interface ICongthucdndh {
	public String getUserId();
	public void setUserId(String userId);
	public String getMsg();
	public void setMsg(String msg);
	public String getNgayTonKhoToithieu();
	public void setNgayTonKhoToithieu(String id);
	
	public String getmucthue();
	public void setMucThue(String thue);
	
	public String getMucTangTruong();
	public void setMucTangTruong(String size);
	public void init_Update();
	
	public boolean Save();
	
	
}
