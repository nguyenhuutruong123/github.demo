package geso.dms.distributor.beans.phieuthanhtoan;

import geso.dms.distributor.beans.phieuthanhtoan.IPhieuthanhtoan;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IPhieuthanhtoanList extends Serializable
{
	public String getUserId();
	
	public void setUserId(String userId);

	public String getTungay();

	public void setTungay(String tungay);
	
	public String getDenngay();
	
	public void setDenngay(String denngay);

	public String getNvgnId();
	
	public void setNvgnId(String nvgnId);
	
	public String getKhId();
	
	public void setKhId(String khId);
	
	public String getPttId();
	
	public void setPttId(String pttId);

	public String getNppId();
	
	public void setNppId(String nppId);
	
	public String getNppTen();
	
	public void setNppTen(String nppTen);

	public void setPhieuthu(ResultSet ptt);
	
	public ResultSet getPhieuthu();
	
	public void setTtoan(ResultSet ttoan);
	
	public void setNvgn(ResultSet nvgn);
	
	public void setTongthu(String tongthu);
	
	public String getTongthu();

	public void setTongTToan(String tongttoan);
	
	public String getTongTToan();
	
	public String getQuery();
	
	public void setQuery(String query);
	
	public String getKHquery();
	
	public void setKHquery(String khquery);	
	
	public ResultSet getNvgn();
	
	public ResultSet getTtoan();
	
	public void init(String search);
	
	public void DBclose();
}
