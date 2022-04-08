package geso.dms.distributor.beans.chitieunpp;

import java.sql.ResultSet;
import java.util.List;

public interface IChitieuSKUInTT 
{
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String Id);
	
	public String getThang();
	public void setThang(String thang);
	public String getNam();
	public void setNam(String nam);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	public String getMsg();
	public void setMsg(String msg);
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public String getKbhId();
	public void setKbhId(String KbhId);
	
	public String getDvkdId();
	public void setDvkdId(String DvkdId);
	
	public ResultSet getDvkdRs();
	public void setDvkdRs(ResultSet dvkdRs);
	
	
	public ResultSet getKbhRs();
	public void setKbhRs(ResultSet kbhRs);
	
	public ResultSet getNppRs();
	public void setNppRs(ResultSet nppRs);
	public String getNppIds();
	public void setNppIds(String nppIds);
	
	public ResultSet getNhomspRs();
	public void setNhomspRs(ResultSet nspRs);
	public String getNspId();
	public void setNspId(String nspId);
	
	public void init();
	public boolean createChiTieuThang();
	public boolean updateChiTieuThang();
	///////
	public void initnpp();
	public List<IChitieusku> getSpList();
	public void setSpList(List<IChitieusku> spList);
	public boolean createChiTieuSkuin();
	public boolean updateChiTieuSkuin();
	public void createRs();
	
	public void DbClose();
	
}
