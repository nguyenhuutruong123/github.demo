package geso.dms.center.beans.thietlapnamtaichinh;
import java.sql.ResultSet;
import java.util.Hashtable;

public interface IThietLapNamTaiChinh {
	public String getUserId();
	public void setUserId(String userId);
	
	public String getMsg();
	public String setMsg(String msg);
	
	public String getActiveTab();
	public String setActiveTab(String tab);
	
	public ResultSet getKyList();
	public ResultSet SetKyList(ResultSet kylist);
	
	public ResultSet getQuyList();
	public ResultSet SetQuyList(ResultSet quylist);
	
	
	
	public void init();
	public void createRs();
	public boolean updateThietLap();
	public void DBclose();
	
	public void SetNgayThietLap(String NgayTL);
	public String GetNgayThietLap();
	public void SetDienGiai(String Diengiai);
	public String GetDienGiai();
	public ResultSet getRsListKs();
	

}
