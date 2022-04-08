package geso.dms.center.beans.dieuchuyenkhuyenmai;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface IDieuchuyenkhuyenmai extends Serializable
{
	public String getUserId();
	public void setUserId(String userId);

	public String getSchemeId();
	public void setSchemeId(String schemeId);

	public String getScheme();
	public void setScheme(String scheme);

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

	public void init();
	
	public Hashtable<String, String> getDieuchuyen();
	public void setDieuchuyen(Hashtable<String, String> dieuchuyen);

	public boolean save(HttpServletRequest request);

	public void DBClose();
	
	public String getTungay();
	public void setTungay(String tungay);
	
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public List<Object> getDataSearch();
	public void setDataSearch(List<Object> dataSearch);
}
