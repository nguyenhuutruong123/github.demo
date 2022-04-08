package geso.dms.center.beans.khoasongay;

import java.sql.ResultSet;
import java.util.Hashtable;

public interface IKhoasotudong 
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getMsg();
	public String setMsg(String msg);
	
	public ResultSet getVungRs();
	public void setVungRs(ResultSet vungRs);
	public Hashtable<Integer, String> getVungIds();
	public void setVungIds(String[] vungIds);
	
	
	public void setDanhSachNhaPP(String[] danhsach);

	public String[]  getDanhSachNhaPP();
	
	
	public ResultSet getKhuvucRs();
	public void setKhuvucRs(ResultSet kvRs);
	public Hashtable<Integer, String> getKvIds();
	public void setKvIds(String[] kvIds);
	
	public ResultSet getNppRs();
	public void setNppRs(ResultSet nppRs);
	public Hashtable<Integer, String> getNppIds();
	public void setNppIds(String[] nppIds);
	
	public void init();
	public void createRs();
	public boolean updateKhoaso();
	public void SetPhut(int phut);
	public int getPhut();
	
	public void SetGio(int gio);
	public int getGio();
	
	public void SetBeforeDay(int BeforeDay);
	public int getBeforeDay();
	
	public void DBclose();
	
	public void SetNgayKhoaSo(String NgayKs);
	public String GetNgayKhoaSo();
	
	public int getNgayluidh();

	public void setNgayluidh(int ngayluidh);
	
	
	public void setNgaylui(String[] ngaylui);

	public String[]  getNgaylui();
	
	
	public ResultSet getRsListKs();
}
