package geso.dms.center.beans.bm;

import java.sql.ResultSet;
import java.util.List;

public interface IBmList {
	
	public String getUserId();
	public void setUserId(String userId);
	
	
	
	public String getTen();
	
	public void setTen(String bmTen);
	
	public String getDienthoai();
	
	public void setDienthoai(String dienthoai);
	
	public String getEmail();
	
	public void setEmail(String email);

	public String getDiachi();
	
	public void setDiachi(String diachi);

	public String getKbhId();
	
	public void setKbhId(String kbhId);

	public String getDvkdId();
	
	public void setDvkdId(String dvkdId);

	public String getVungId();
	
	public void setVungId(String vungId);

	public String getTrangthai();
	
	public void setTrangthai(String trangthai);
	
	public String getMsg();
	
	public void setMsg(String msg);	
	
	public ResultSet getBmlist();
	
	public void setBmlist(ResultSet bmlist);
	
	public ResultSet getKbh();
	
	public void setKbh(ResultSet kbh);

	public ResultSet getDvkd();
	
	public void setDvkd(ResultSet dvkd);

	public ResultSet getVung();
	
	public void setVung(ResultSet vung);
	
	public void init();
	
	public void DBClose();
	
	public void Delete(String bmid, String vungId);
	
	public List<Object> getDataSearch();
	public void setDataSearch(List<Object> dataSearch);
}
