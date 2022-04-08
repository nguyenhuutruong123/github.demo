package geso.dms.center.beans.denghitratrungbay;

import java.sql.ResultSet;
import java.util.Hashtable;

public interface IDeNghiTraTrungBay
{
	public String getId();
	public void setId(String id);
	
	public String getNppId();
	public void setNppId(String nppId);
	
	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getLanduyet();
	public void setLanduyet(String landuyet);
	
	public String getNgaydenghi();
	public void setNgaydenghi(String ngaydenghi);
	
	
	public String[] getKhId();
	public void setKhId(String[] khId);
	
	
	public String getCttbId();
	public void setCttbId(String cttbId);
	
	public ResultSet getNppRs();
	public void setNppRs(ResultSet nppRs);
	
	public ResultSet getCttbRs();
	public void setCttbRs(ResultSet cttbRs);
	
	public String[] getKhTen();
	public void setKhTen(String[] khTen);
	
	public String[] getKhDiaChi();
	public void setKhDiaChi(String[] khDiaChi);
	
	public String[] getKhDienThoai();
	public void setKhDienThoai(String[] khDienThoai);
	
	
	public String[] getKhMa();
	public void setKhMa(String[] khMa);
	
	public String[] getKhDangKy();
	public void setKhDangKy(String[] dangky);
	
	public String[] getKhDeNghi();
	public void setKhDeNghi(String[] denghi);
	
	public String[] getKhDuyet();
	public void setKhDuyet(String[] duyet);
	
	public String[] getKhDat();
	public void setKhDat(String[] dat);
	
	public Hashtable<String, String> getKh_Duyet();
	public void setKh_Duyet(Hashtable<String, String> khDuyet);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public boolean Chot();
	
	public boolean Save();
	

	public void createRs();
	
	public ResultSet getKhRs();
	public void setKhRs(ResultSet khRs);
	
	public void init();
	
	
}
