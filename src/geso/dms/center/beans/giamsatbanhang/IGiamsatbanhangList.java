package geso.dms.center.beans.giamsatbanhang;

import geso.dms.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface IGiamsatbanhangList extends Serializable, IPhan_Trang
{	
	HttpServletRequest getRequestObj();
	void setRequestObj(HttpServletRequest request);
	
	public String getUserId();
	public void setUserId(String userId);
	public String getSmartId();
	public void setSmartId(String smartId);
	public String getTen();
	public void setTen(String ten);
	public String getSodienthoai();
	public void setSodienthoai(String sodienthoai);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	public ResultSet getAsmRs();
	public void setAsmRs(ResultSet asmRs);
	public List<IGiamsatbanhang> getGsbhList();
	public void setGsbhList(List<IGiamsatbanhang> gsbhlist);
	public String getAsmId() ;
	public void setAsmId(String asmId);
	public ResultSet getKenhbanhang();
	public void setKenhbanhang(ResultSet kenhbanhang);
	public String getKbhId();
	public void setKbhId(String kbhId);
	public String getThuviec();
	public void setThuviec(String thuviec);
	
	public void init(String search);
	public void setMsg(String Msg);
	public String getMsg();
	void initSplitting(); 
	public ResultSet getGsbhListRs();
	public void setGsbhListRs(ResultSet gsbhListRs);
	public void DBClose();
	
	public List<Object> getDataSearch();
	public void setDataSearch(List<Object> dataSearch);
}
