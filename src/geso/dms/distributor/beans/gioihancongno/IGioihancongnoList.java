package geso.dms.distributor.beans.gioihancongno;

import geso.dms.distributor.beans.gioihancongno.IGioihancongno;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IGioihancongnoList extends Serializable 
{
	public String getUserId();
	public void setUserId(String userId);

	public String getSongayno();
	public void setSongayno(String songayno);
	public String getSotienno();
	public void setSotienno(String sotienno);
	
	public ResultSet getTuyenbanhang();
	public void setTuyenbanhang(ResultSet tuyenbanhang);	
	public String getTbhId();
	public void setTbhId(String tbhId);
	public String getKhachhang();
	public void setKhachhang(String khachhang);
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
		
	public List<IGioihancongno> getGhcnList();
	public void setGhcnList(List<IGioihancongno> ghcnlist);
	
	public void init(String search);
	public void DBclose();
	public void setMsg(String Msg);
	public String getMsg();
}

