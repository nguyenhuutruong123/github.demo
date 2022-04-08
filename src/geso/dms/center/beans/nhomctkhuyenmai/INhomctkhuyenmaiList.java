package geso.dms.center.beans.nhomctkhuyenmai;

import java.sql.ResultSet;

public interface INhomctkhuyenmaiList 
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getDiengiai();
	public void setDiengiai (String diengiai);

	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getTungay();
	public void setTungay(String tungay);

	public String getDenngay();
	public void setDenngay(String denngay);	

	public ResultSet getNctkmRs();
	public void setNctkmRs(ResultSet nctkm);
	
	public void createNhomctkmBean(String sql);
	
}
