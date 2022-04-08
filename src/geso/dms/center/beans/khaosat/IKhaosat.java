package geso.dms.center.beans.khaosat;

import java.sql.ResultSet;
import java.util.Hashtable;

public interface IKhaosat
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String Id);
	
	public String getTieude();
	public void setTieude(String tieude);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);

	public String getBophan();
	public void setBophan(String bophan);
	
	public String getSocauhoi();
	public void setSocauhoi(String socauhoi);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public Hashtable<String, String> getNoidungcauhoi();
	public void setNoidungcauhoi(Hashtable<String, String> noidung);
	
	public ResultSet getVungRs();
	public void setVungRs(ResultSet vungRs);
	public String getVungId();
	public void setVungId(String vungId);
	
	public ResultSet getKhuvucRs();
	public void setKhuvucRs(ResultSet kvRs);
	public String getKhuvucId();
	public void setKhuvucId(String kvId);
	
	public ResultSet getDdkdRs();
	public void setDdkdRs(ResultSet ddkdRs);
	public String getDdkdId();
	public void setDdkdId(String ddkdId);
	
	public boolean createKhaosat();
	public boolean updateKhaosat();
	
	public void init();
	public void createRs();
	
	public void DbClose();
	
	public String getDenngay();
	public void setDenngay(String denngay);
	public void setTungay(String tungay) ;
	public String getTungay() ;
	public String getLoaict() ;
	public void setLoaict(String loaict) ;

	public String getDoituong() ;
	public void setDoituong(String doituong) ;
	
}
