package geso.dms.center.beans.donvikinhdoanh;
import java.io.Serializable;
import java.util.Hashtable;
import java.sql.ResultSet;

public interface IDonvikinhdoanh extends Serializable {
	
	public void init();
	
	public String getId();

	public void setId(String id);
	
	public String getDvkd();
	
	public void setDvkd(String dvkd);
	
	public String getDiengiai();
	
	public void setDiengiai(String diengiai);

	public String  getNcc();
	
	public void  setNcc(String ncc);
	
	public void setTrangthai(String trangthai);
	
	public String getNgaytao();
	
	public void setNgaytao(String ngaytao);
	
	public String getNgaysua();
	
	public void setNgaysua(String ngaysua);
	
	public String getNguoitao();
	
	public void setNguoitao(String nguoitao);
	
	public String getNguoisua();
	
	public void setNguoisua(String nguoisua);
	
	public String getTrangthai();
	
	public String getMessage();
	
	public void setMessage(String msg);
	
	public String  getNccId();
	
	public void setNccId(String nccId);
	
	public void setNccSelected(String[] nccSelected);
	
	public Hashtable<Integer, String>  getNccSelected();
	 
	public ResultSet getNccList(boolean all);
		
	public ResultSet getNccListByDvkd(String dvkdId);

	public boolean saveNewDvkd();
	
	public boolean UpdateDvkd();
	
	public ResultSet getNewNcc(String dvkdId);
	
	public void DBClose();
   
}