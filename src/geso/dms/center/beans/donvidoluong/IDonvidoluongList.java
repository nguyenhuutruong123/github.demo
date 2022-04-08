package geso.dms.center.beans.donvidoluong;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IDonvidoluongList extends Serializable {
	
	public String getDvdl();
	
	public void setDvdl(String dvdl);

	public String getDiengiai();
	
	public void setDiengiai (String diengiai);
	
	public String getTungay();
	
	public void setTungay(String tungay);

	public String getDenngay();
	
	public void setDenngay(String denngay);
	
	public String getTrangthai();

	public void setTrangthai(String trangthai);
	
	public String getQuery();
	
	public void setQuery(String query);
	
	public ResultSet getDonvilist();
		
	public void setMsg(String Msg);
	public String getMsg();
	public void DBClose();
	
	public List<Object> getDataSearch() ;
	public void setDataSearch(List<Object> dataSearch);
	
	public String getView();
	public void setView(String view) ;
}