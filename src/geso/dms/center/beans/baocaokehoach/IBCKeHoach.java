package geso.dms.center.beans.baocaokehoach;

import java.sql.ResultSet;

public interface IBCKeHoach {
	public String getUserId();
	public void setUserId(String userId);
	public String getUserTen();
	public void setUserTen(String userTen);
	public ResultSet getNpp();
	public void setNpp(ResultSet npp);
	public ResultSet getVung();
	public void setVung(ResultSet vung);
	public ResultSet getKhuvuc();
	public void setKhuvuc(ResultSet khuvuc);
	public ResultSet getGiamsatbanhang();
	public void setGiamsatbanhang(ResultSet giamsatbanhang);
	public void creates();
	public String getMsg();
	public void setMsg(String msg);
	public String getNppId();
	public void setNppId(String nppId);
	public String getVungId();
	public void setVungId(String vungId);
	public String getKhuvucId();
	public void setKhuvucId(String khuvucId);
	public String getGsbanhangId();
	public void setGsbanhangId(String gsbanhangId);
	public void DBclose();
	public void init();
	public ResultSet getResultList();
	public String getYear();
	public void setYear(String year);
	public String getMonth();
	public void setMonth(String month);


}
