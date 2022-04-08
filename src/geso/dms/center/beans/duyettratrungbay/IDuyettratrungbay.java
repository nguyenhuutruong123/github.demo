package geso.dms.center.beans.duyettratrungbay;

import geso.dms.center.db.sql.dbutils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

public interface IDuyettratrungbay extends Serializable
{
	public boolean Upload();
	public boolean Aptrungbay();
	public String getUserId();
	public void setUserId(String UserId);

	public String getSchemeId();
	public void setSchemeId(String schemeId);

	public String getScheme();
	public void setScheme(String scheme);

	public int getSolantt();
	public void setSolantt(int solantt);

	public int getLantt();
	public void setLantt(int lantt);

	public String getNppId();
	public void setNppId(String nppId);

	public ResultSet getKhRs();
	public void setKhRs();

	public String[] getKhIds();
	public void setKhIds(String[] khIds);

	public String getTrangthai();

	public String getMessage();
	public void setMessage(String msg);

	public ResultSet getSchemeRS();
	public void setSchemeRS(ResultSet schemeRS);

	public ResultSet getVung();
	public void setVung(ResultSet vung);

	public String getVungId();
	public void setVungId(String vungId);

	public ResultSet getKv();
	public void setKv(ResultSet khuvuc);

	public String getKvId();
	public void setKvId(String kvId);

	public ResultSet getNpp();
	public void setNpp(ResultSet nppRS);

	public Hashtable<String, Integer> getTraTb();
	public void setTraTb(Hashtable<String, Integer> tratb);

	public boolean Luutratb();

	public boolean Chot(HttpServletRequest request);
	
	public String getDaduyet();
	public void setDaduyet(String daduyet);

	public void init();

	public void getLanthanhtoan();
	
	public void closeDB();

	public void setId(String string);
	public String getId();

	public void createRs();

	public String getDiengiai();
	public void setDiengiai(String value);
	public boolean UpdateTb();
	public dbutils getDb();
}
