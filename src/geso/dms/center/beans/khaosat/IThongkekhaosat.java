package geso.dms.center.beans.khaosat;

import java.util.Hashtable;

public interface IThongkekhaosat
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String Id);
	
	public String getTieude();
	public void setTieude(String tieude);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);

	public String getBophan();
	public void setBophan(String bophan);
	
	public String getSocauhoi();
	public void setSocauhoi(String socauhoi);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public Hashtable<String, String> getNoidungcauhoi();
	public void setNoidungcauhoi(Hashtable<String, String> noidung);
	public Hashtable<String, String> getNoidungcautraloi();
	public void setNoidungcautraloi(Hashtable<String, String> cautraloi);
	
	
	public String getTennguoiks();
	public void setTennguoiks(String trangthai);
	public String getSodienthoai();
	public void setSodienthoai(String sodienthoai);
	public String getDiachi();
	public void setDiachi(String diachi);
	public String getDoituong();
	public void setDoituong(String doituong);
	public String getMuctieu();
	public void setMuctieu(String muctieu);
	public String getThunhap();
	public void setThunhap(String thunhap);
	public String getGioitinh();
	public void setGioitinh(String gioitinh);
	public String getTuoi();
	public void setTuoi(String tuoi);
	
	
	public void init();
	
	public void DbClose();
	
}
