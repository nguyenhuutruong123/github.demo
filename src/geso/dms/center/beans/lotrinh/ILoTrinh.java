package geso.dms.center.beans.lotrinh;


import geso.dms.center.db.sql.dbutils;

import java.sql.ResultSet;

public interface ILoTrinh {
   
	public dbutils getDb() ;
	public void setvungId(String khuvucId);
	public void setkhuvucId(String khuvucId);
	public void setnppId(String nppId);	
	public String getvungId();
	public String getkhuvucId();
	public String getnppId();
	public void setddkdId(String ddkdId);
	public String getddkdId();
	public ResultSet getvung();
	public ResultSet getkhuvuc();
	public ResultSet getnpp();
	public ResultSet getddkd();
	public void setKenhId(String kenhId);
	public String getkenhId();
	public void settuyenId(String tuyenId);
	public String gettuyenId();
	public ResultSet getdanhsach();
	public ResultSet getTuyen();
	public ResultSet getKenh();
	public void init();
	public void DBclose();
	public void createNPP();
	public void setStatus(String status);
	public String getStatus();
	public void setView(String view);
	public String getView();
	
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getViTri();
	public void setVitri(String vitri);
	public void setUserId(String userId);
	public String getUserId();
	
	
}
