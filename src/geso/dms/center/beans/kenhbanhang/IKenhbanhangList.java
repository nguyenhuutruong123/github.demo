package geso.dms.center.beans.kenhbanhang;

import geso.dms.center.beans.kenhbanhang.IKenhbanhang;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface IKenhbanhangList extends Serializable 
{
	public String getUserId();
	public void setUserId(String userId);
		
	public String getKenhbanhang();	
	public void setKenhbanhang(String kenhbanhang);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public List<IKenhbanhang> getKbhList();	
	public void setKbhList(List<IKenhbanhang> kbhlist);
	
	public void init(String search);
	public void DBClose();
	public void setMsg(String Msg);
	public String getMsg();
	public String getView() ;
	public void setView(String view) ;
	public HttpServletRequest getRequestObj() ;
	public void setRequestObj(HttpServletRequest request) ;

	public List<Object> getDataSearch() ;
	public void setDataSearch(List<Object> dataSearch) ;
}
