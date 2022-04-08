package geso.dms.distributor.beans.dangkykhuyenmaitichluy;
import java.sql.ResultSet;
import java.util.Hashtable;

public interface IDangkykhuyenmaitichluy {

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
	public void setCtkmId(String ctkmId);
	public String getctkmId();

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
	//public void getId_Khachhang(String maKh, Hashtable<String, Integer> kh_muc, Hashtable<String, Integer> kh_stt,Hashtable<String, String> kh_tinhthanh,Hashtable<String, String> kh_ddkd);
	public void getId_Khachhang(String maKh, Hashtable<String, Integer> kh_muc, Hashtable<String, Integer> kh_stt);
	public boolean UnChot();
	
	public Hashtable<String, Integer> getMucDangky();
	public void setMucDangky(Hashtable<String, Integer> value);
	
	public Hashtable<String, Integer> getSTT();
	public void setSTT(Hashtable<String, Integer> value);
	
	public ResultSet getKhExportRs();
	public void setAction(String string);
	public String getAction();
	public Hashtable<String, String> getKh_tinhthanh() ;
	public void setKh_tinhthanh(Hashtable<String, String> kh_tinhthanh);
	public Hashtable<String, String> getKh_ddkd() ;
	public void setKh_ddkd(Hashtable<String, String> kh_ddkd);


}
