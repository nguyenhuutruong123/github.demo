package geso.dms.center.beans.chungloai;

import geso.dms.center.beans.chungloai.IChungloaiList;
import java.io.Serializable;
import java.sql.ResultSet;

public interface IChungloaiList extends Serializable {
	
	public String getCloai();
	
	public void setCloai(String cloai);
	
	public String  getNhId();
	
	public void  setNhId(String nhId);
	
	public String getTrangthai();
	
	public String getTungay();
	
	public void setTungay(String tungay);
	
	public String getDenngay();
	
	public void setDenngay(String denngay);
	
	public void setTrangthai(String trangthai);
	
	public ResultSet getNhList();
	
	public void setNhList(ResultSet NhList);
	
	public ResultSet getClList();
	public void setMsg(String Msg);
	public String getMsg();
	public void setClList(ResultSet clList);
	
	public void DBClose();
}