package geso.dms.center.beans.nganhhang;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface INganhHangList extends Serializable 
{
	public String getUserId();
	public void setUserId(String userId);
		
	public String getTen();	
	public void setTen(String ten);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public ResultSet getList();	
	public void setList(ResultSet list);
	
	public void init(String search);
	public void DBClose();
	public void setMsg(String Msg);
	public String getMsg();
	
	
	public String getView();

	public void setView(String view);
	public List<Object> getDataSearch();
	public void setDataSearch(List<Object> dataSearch) ;
}
