package geso.dms.distributor.beans.phieuxuatkho;

import geso.dms.center.util.IPhanTrang;
import geso.dms.center.util.IPhan_Trang;
import geso.dms.distributor.beans.phieuxuatkho.IPhieuxuatkho;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface IPhieuxuatkhoList extends Serializable, IPhan_Trang
{
	HttpServletRequest getRequestObj();
	void setRequestObj(HttpServletRequest request);
	
	public String getUserId();
	public void setUserId(String userId);

	public String getTrangthai();
	public void setTrangthai(String trangthai);
	public String getTungay();
	public void setTungay(String tungay);
	
	public ResultSet getNhanvienGN();
	public void setNhanvienGN(ResultSet nhanviengn);	
	public String getNvgnId();
	public void setNvgnId(String nvgnId);
	
	public String getDonhangId();
	public void setDonhangId(String donhangid);
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	public String getQuyTrinhBanHang();
	public void setQuyTrinhBanHang(String qtbh);
	public String getMsg();
	public void setMsg(String msg);
		
	public List<IPhieuxuatkho> getPxkList();
	public void setPxkList(List<IPhieuxuatkho> pxklist);

	public void init(String search);
	public void createRS();
	public void DBclose();
	public ResultSet getRsPXK();
}
