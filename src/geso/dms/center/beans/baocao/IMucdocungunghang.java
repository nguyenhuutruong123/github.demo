package geso.dms.center.beans.baocao;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IMucdocungunghang extends Serializable{
	public String getUserId();
	
	public void setUserId(String userId);
	
	public String getMasp();
	
	public void setMasp(String masp);
	
	public String getTensp();
	
	public void setTensp(String tensp);

	public String getTuNgay();
	
	public void setTuNgay(String tungay);
	
	public String getDenNgay();
	
	public void setDenNgay(String denngay);

	public String getKhoId();
	
	public void setKhoId(String khoId);
	
	public ResultSet getKhoIds();
	
	public void setKhoIds(ResultSet khoIds);

	public String getVungId();
	
	public void setVungId(String vungId);
	
	public ResultSet getVungIds();
	
	public void setVungIds(ResultSet vungIds);

	public String getKvId();
	
	public void setKvId(String kvId);
	
	public ResultSet getKvIds();
	
	public void setKvIds(ResultSet kvIds);

	public String getNppId();
	
	public void setNppId(String nppId);

	public ResultSet getNppIds();
	
	public void setNppIds(ResultSet nppIds);
	
	public ResultSet getMucdocungung();
	
	public void setMucdocungung(ResultSet mdcu);
	
	public String getSearch();
	
	public void setSearch(String search);
	
	public void init();
	public void DBClose();

}
