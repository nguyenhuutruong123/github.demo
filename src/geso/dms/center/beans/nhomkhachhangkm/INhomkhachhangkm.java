package geso.dms.center.beans.nhomkhachhangkm;

import java.sql.ResultSet;



public interface INhomkhachhangkm {
	
	public void setuserId(String userId);
	
	public String getuserId();
	
	public void setDiengiai(String Diengiai);
	
	public String getDiengiai();
	
	public void setTrangthai(String Trangthai);
	
	public String getTrangthai();
    
    public void setMsg(String Msg);
    
    public String getMsg();
    
	public void setTen(String Ten);
	
	public String getTen();
	
	public void setId(String Id);
	
	public String getId();
	
	public boolean Save();
	
	public boolean Update();
	
	public void init();
	
	public String getNppId();
	public void setNppId(String nppId);
	
	public String getKenhId();
	public void setKenhId(String  kenhId);
	
	public String getKhuvucId();
	public void setKhuvucId(String khuvucId);
	
	public String getVungId();
	public void setVungId(String vungId);
	
	public String getDdkdId();
	public void setDdkdId(String ddkdId);
	
	
	public String getKhId();
	public void setKhId(String khId);
	
	
	public void createRs();
	
	
	public ResultSet getKenhRs();
	public void setKenhRs(ResultSet kenhRs);
	
	public ResultSet getVungRs();
	public void setVungRs(ResultSet vungRs);
	
	public ResultSet getKhuvucRs();
	public void setKhuvucRs(ResultSet khuvucRs);
	
	public ResultSet getNppRs();
	public void setNppRs(ResultSet nppRs);
	
	public ResultSet getDdkdRs();
	public void setDdkdRs(ResultSet ddkdRs);
	
	
	public ResultSet getKhRs();
	public void setKhRs(ResultSet khRs);
	
	
	 public void setActive(String active);
	 public String getActive();
	 
	 public void createDdkdRs();
	 public void createKhRs();
	 public void createNppRs();

	 public String getKhMaUpload() ;
	 public void setKhMaUpload(String khMaUpload);
	 public String getAction() ;
	 public void setAction(String action) ;
	 
}
