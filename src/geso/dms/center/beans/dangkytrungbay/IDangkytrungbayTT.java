package geso.dms.center.beans.dangkytrungbay;

import java.sql.ResultSet;
import java.util.Hashtable;

public interface IDangkytrungbayTT
{
	public void setId(String Id);
	public String getId();
	
	public String getUserId();
	public void setUserId(String userId);
	
	public ResultSet getVungRs();
	public String getVungIds();
	public void setVungIds(String[] vungIds);
	
	public ResultSet getKhuvucRs();
	public String getKhuvucIds();
	public void setKhuvucIds(String[] kvIds);
	
	public ResultSet getNppRs();
	public String getNppIds();
	public void setNppIds(String[] nppIds);

	public void setTungay(String tungay);
	public String getTungay();

	public void setDenngay(String denngay);
	public String getDenngay();

	public void setCtkmRs(ResultSet ctkmIds);
	public ResultSet getCtkmRs();
	public void setCttbId(String cttbId);
	public String getCttbId();

	public ResultSet getNvBanhang();
	public void setNvBanhang(ResultSet nvbanhang);
	public String getNvbhIds();
	public void setNvbhIds(String nvbdIds);
	
	public void setKhList(ResultSet KhList);
	public ResultSet getKhList();
	public String getKhId();
	public void setKhId(String khId);

	public void setMessage(String Msg);
	public String getMessage();


	public boolean save();
	public boolean edit();
	public boolean Chot();
	public boolean Delete();
	
	public void createRs();
	public void init();

	public void DBclose();
	public void setNppIdSelecteds(String[] nppIdSelecteds);
	public String getNppIdSelecteds();
	public void getId_Khachhang(String maKh, Hashtable<String, Integer> sosuatdk, Hashtable<String, Integer> sosuatduyet);
	public boolean UnChot();
	
	public Hashtable<String, Integer> getSoSuatmua();
	public void setSoSuatmua(Hashtable<String, Integer> value);
	
	public Hashtable<String, Integer> getSoSuatDk();
	public void setSoSuatDk(Hashtable<String, Integer> value);
	
	public Hashtable<String, Integer> getSoSuatDuyet();
	public void setSoSuatDuyet(Hashtable<String, Integer> value);

}
