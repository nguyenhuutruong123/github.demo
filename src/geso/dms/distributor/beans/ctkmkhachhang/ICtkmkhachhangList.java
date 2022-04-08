package geso.dms.distributor.beans.ctkmkhachhang;

import geso.dms.center.util.IPhanTrang;
import geso.dms.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

public interface ICtkmkhachhangList extends IPhan_Trang, Serializable {
	

	HttpServletRequest getRequestObj();
	void setRequestObj(HttpServletRequest request);
   
	public void setuserId(String userId);
    
	public String getuserId();
	
	public void setDiengiai(String Diengiai);
	
	public String getDiengiai();
	
	public void setDskm(ResultSet Dskm);
	
	public void setTrangthai(String Trangthai);
	
	public String getTrangthai();
	
	public void setTungay(String Tungay);
	
	public String getTungay();
	
	public void setDenngay(String Denngay);
	
	public String getDenngay();
	
	
	public ResultSet getDskm();
	public void init();
	
	public void createDdkdRS();
	public ResultSet getDDKD();
	public String getDDKDId();
	public void setDDKdId(String ddkdId);
	public void setMaKH(String maKH);
	public String getMaKH();
	public void setTenKH(String tenKH);
	public String getTenKH();
	public String getTenNpp();
	public void setTenNpp(String tennpp);
	public void DBclose();
	
}
