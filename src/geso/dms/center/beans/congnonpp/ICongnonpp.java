package geso.dms.center.beans.congnonpp;
import geso.dms.center.beans.congnonpp.INhaphanphoi;

import java.sql.ResultSet;
import java.util.List;

public interface ICongnonpp {
	
	public void setuserId(String userId);
	
	public String getuserId();	
    
    public void setMsg(String Msg);    
    public String getMsg();
    
    public void setNguoitao(String nguoitao);    
    public String getNguoitao();    
    public void setNguoisua(String nguoisua);    
    public String getNguoisua();
    
    public void setNgaytao(String ngaytao);    
    public String getNgaytao();
    public void setNgaysua(String ngaysua);    
    public String getNgaysua();
    
	public void setId(String Id);
	public String getId();	
	
	public void setThoigian(String thoigian);
	public String getThoigian();	
	
	public void setDvkdId(String dvkdId);
	public String getDvkdId();
	
	public void setDvkdList(ResultSet dvkdlist);
	public ResultSet getDvkdList();
	
	public void setKbhId(String kbhId);
	public String getKbhId();
	
	public void setKbhList(ResultSet kbhlist);
	public ResultSet getKbhList();
	
	public void setDiengiai(String diengiai);
	public String getDiengiai();
	
	public void setHinhthuctt(String httt);
	public String getHinhthuctt();
	
	public List<INhaphanphoi> getnppList();
	public void setnppList(List<INhaphanphoi> npplist);
	
	public void CreateCongnonppList();

	public boolean update(List<INhaphanphoi> npplist);		

	public boolean SaveUpload(String thoigian, String dvkdId, String kbhId);
	
	public void DBclose();
}
