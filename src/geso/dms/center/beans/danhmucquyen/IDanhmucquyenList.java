package geso.dms.center.beans.danhmucquyen;

import java.sql.ResultSet;

public interface IDanhmucquyenList {
	public void setMaQuyen(String MaQuyen);
	public String getMaQuyen();
	public void setTenQuyen(String TenQuyen);
	public String getTenQuyen();
	public void setUserId(String UserId);
	public String getUserId();
	public void setTungay(String Tungay);
	public String getTungay();
	public void setDenngay(String Denngay);
	public String getDenngay();
		
	public ResultSet getDSQuyen();
	public ResultSet BangQuyen();
	public void delete(String ma);
}
