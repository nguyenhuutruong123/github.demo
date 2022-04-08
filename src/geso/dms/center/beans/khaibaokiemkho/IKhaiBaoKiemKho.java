package geso.dms.center.beans.khaibaokiemkho;

import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.List;

public interface IKhaiBaoKiemKho
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String Id);
	
	public String getKhId();
	public void setKhId(String khId);
	
	public String getDiengiai();
	public void setDiengiai(String value);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public boolean create();
	public boolean update();
	
	public void init();
	public void createRs();
	public void getId_Khachhang(String maKh);
	public void DbClose();

	public String getTuNgay();
	public void setTuNgay(String tungay);
	
	public String getDenNgay();
	public void setDenNgay(String tungay);
	
	public ResultSet getSpRs();
	public ResultSet getKhList();
	public String getSpIds();
	public void setSpIds(String value);
	
	public String[] getSpDoithu();
	public void setSpDoithu(String[] value);
	
	public String[] getDvtDoithu();
	public void setDvtDoithu(String[] value);
	
}
