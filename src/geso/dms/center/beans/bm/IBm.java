package geso.dms.center.beans.bm;

import java.sql.ResultSet;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

public interface IBm {
	public String getId();
	
	public void setId(String Id);

	public String getTen();
	
	public void setTen(String bmTen);
	
	public String getDienthoai();
	
	public void setDienthoai(String dienthoai);
	
	public String getEmail();
	
	public void setEmail(String email);

	public String getDiachi();
	
	public void setDiachi(String diachi);
	
	public String getSmartid();
	
	public void setSmartid(String Smartid);


	public String getKbhId();
	
	public void setKbhId(String kbhId);

	public ResultSet getKbh();
	
	public void setKbh(ResultSet kbh);
	
	public String getDvkdId();
	
	public void setDvkdId(String dvkdId);
	
	public String getTtppId();
	
	public void setTtppId(String ttppId);
	
	public ResultSet getTrungtamphanphoiList();

	public ResultSet getDvkd();
	
	public void setDvkd(ResultSet dvkd);

	public String[] getVungId();
	
	public void setVungId(String[] vungId);

	public ResultSet getVung();
	
	public void setVung(ResultSet vung);
	
	public String getTrangthai();
	
	public void setTrangthai(String trangthai);

	public String getMsg();
	
	public void setMsg(String msg);
	
	public String getUserId();
	
	public void setUserId(String userId);
	
	public void init_New();
	
	public void init_Update();
	
	public boolean Save(HttpServletRequest request);
	
	public Hashtable<String, String> getHTVungId();
	public void DBClose();
}
