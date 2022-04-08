package geso.dms.distributor.beans.chuyenkho;

import java.io.Serializable;
import java.sql.ResultSet;

public interface ITrahangList extends Serializable
{
	public String getUserId();
	public void setUserId(String userId);

	public String getTrangthai();
	public void setTrangthai(String trangthai);
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String tungay);
	
	public ResultSet getNhanvienBH();
	public void setNhanvienBH(ResultSet nhanvienbh);	
	public String getNvbhId();
	public void setNvbhId(String nvbhId);
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	
	public String getMsg();
	public void setMsg(String msg);
		
	public ResultSet getRsChuyenKho();
	public void setRsChuyenKho(ResultSet rsChuyenKho);

	public void init(String search);
	public void createRS();
	public void DBclose();
}
