package geso.dms.center.beans.nhomnhaphanphoi;

import java.sql.ResultSet;

public interface INhomNhaPhanPhoi
{

	public String getId();

	public void setId(String id);

	public String getMa();

	public void setMa(String ma);

	public String getTen();

	public void setTen(String ten);

	public String getMsg();

	public void setMsg(String msg);

	public String getUserId();

	public void setUserId(String userId);

	public String getKvId();

	public void setKvId(String kvId);

	public String getVungId();

	public void setVungId(String vungId);

	public String getNppId();

	public void setNppId(String nppId);

	public boolean save();

	public boolean edit();

	public void closeDB();

	public void init();

	public ResultSet getNppRs();

	public void setNppRs(ResultSet NppRs);

	public ResultSet getKvRs();

	public void setKvRs(ResultSet KvRs);

	public ResultSet getVungRs();

	public void setVungRs(ResultSet VungRs);
	
	public ResultSet getKenhRs();
	public void setKenhRs(ResultSet kenhRs);
	
	public String getKenhId();
	public void setKenhId(String kenhId);
	
	public String getLoainhom();
	public void setLoainhom(String loainhom);
	
	public ResultSet getNhomRs();
	public void setNhomRs(ResultSet nhomRs);
	
	
	public String getNhomId();
	public void setNhomId(String nhomId);
	
	public String getTinhId();
	public void setTinhId(String tinhId);
	
	public ResultSet getTinhRs();
	public void setTinhRs(ResultSet tinhRs);
	
	public String getQuanhuyenId();
	public  void setQuanhuyenId(String quanhuyenId);
	
	public ResultSet getQuanhuyenRs();
	public void setQuanhuyenRs(ResultSet quanhuyenRs);
	

	public void createRs();

}
