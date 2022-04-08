package geso.dms.center.beans.banggiabanlechuan;


import java.sql.ResultSet;
import java.util.Hashtable;

public interface IBangGiaBanLeChuan
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String Id);
	
	public String getTen();
	public void setTen(String ten);
	
	public String getDvkdId();
	public void setDvkdId(String dvkdId);
	public ResultSet getDvkdRs();
	public void setDvkdRs(ResultSet dvkdRs);
	
	public String getKbhId();
	public void setKbhId(String kbhId);
	public ResultSet getKbhRs();
	public void setKbhRs(ResultSet kbhRs);

	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getMsg();
	public void setMsg(String msg);

	
	public String getNppId();
	public void setNppId(String nppId);
	public ResultSet getNppRs();
	public void setNppRs(ResultSet NppRs);
	
	public boolean createBanggia();
	public boolean updateBanggia();
	
	public void init();
	public void createRs();
	
	public void DbClose();

	public String getTuNgay();
	public void setTuNgay(String tungay);
	
	
	public String getChietKhau();
	public void setChietKhau(String chietkhau);
	
	public String[] getNppIdCks();
	public void setNppIdCks(String[] ids);
	public String[] getNppChietKhaus();
	public void setNppChietKhaus(String[] cks);
	
	
	public ResultSet getKvRs();
	public void setKvRs(ResultSet KvRs);

	public ResultSet getVungRs();
	public void setVungRs(ResultSet VungRs);
	
	
	public String getKvId();
	public void setKvId(String kvId);

	public String getVungId();
	public void setVungId(String vungId);
	
	
	public Hashtable<String, String> getSpGiaban();
	public void setSpGiaban(Hashtable<String, String> spGiaban);
	
	public Hashtable<String, String> getSpGiahuongdan();
	public void setSpGiahuongdan(Hashtable<String, String> SpGiahuongdan);
	
	public Hashtable<String, String> getSpPtChietkhau();
	public void setSpPtChietkhau(Hashtable<String, String> spChietkhau);
	
	public Hashtable<String, String> getSpChonban();
	public void setSpChonban(Hashtable<String, String> spChonban);
	
	
	public Hashtable<String, String> getClPtCk();
	public void setClPtCk(Hashtable<String, String> clck);
	
	public Hashtable<String, String> getNhPtCk();
	public void setNhPtCk(Hashtable<String, String> nhck);
	
	public ResultSet getSpRs();
	public void setSpRs(ResultSet spRs);
	
	public ResultSet getNhRs();
	public void setNhRs(ResultSet nhRs);
	
	public ResultSet getClRs();
	public void setClRs(ResultSet clRs);
	
}
