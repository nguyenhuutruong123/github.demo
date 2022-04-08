package geso.dms.center.beans.banggiamuanpp;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IBanggiamuanppList extends Serializable
{
	public String getUserId();
	
	public void setUserId(String userId);

	public String getUserTen(); 

	public void setUserTen(String userTen); 
	
	public String getTen(); 
	
	public void setTen(String ten);
	
	public String getDvkdId();
	
	public void setDvkdId(String dvkdId);
	
	public String getKenhId();
	
	public void setKenhId(String kenhId);
	
	public String getTrangthai(); 
	
	public void setTrangthai(String trangthai); 

	public List<IBanggiamuanpp> getBgmuanppList();
	
	public void setBgmuanppList(List<IBanggiamuanpp> bgmuanpplist);

	public ResultSet getDvkd();
	
	public void setDvkd(ResultSet dvkd);
	
	public ResultSet getKenh();
	
	public void setKenh(ResultSet kenh);
	
	public boolean saveNewBgblc();
	
	public boolean UpdateBgblc();
	
	public void createBanggiamuanppBeanList(String query);

	public ResultSet getBglist(); 
	public void setBglist(ResultSet bglist); 
	
	public void init(String search);
	public void closeDB();
}
