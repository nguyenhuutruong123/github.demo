package geso.dms.distributor.beans.inpxk;

import geso.dms.center.util.IPhanTrang;
import geso.dms.center.util.IPhan_Trang;
import geso.dms.distributor.beans.inpxk.IInPXK;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface IInPXKList extends Serializable, IPhan_Trang
{
	HttpServletRequest getRequestObj();
	void setRequestObj(HttpServletRequest request);
	
	public String getUserId();
	public void setUserId(String userId);

	public String getTrangthai();
	public void setTrangthai(String trangthai);
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public ResultSet getNhanvienGN();
	public void setNhanvienGN(ResultSet nhanviengn);	
	public ResultSet getNhanvienBH();
	public void setNhanvienBH(ResultSet nhanvienBH);	
	
	public String getNvgnId();
	public void setNvgnId(String nvgnId);
	public String getNvbhId();
	public void setNvbhId(String nvbhId);
	
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
	
	public String getTab();
	public void setTab(String tab);
		
	public List<IInPXK> getInPxkList();
	public void setInPxkList(List<IInPXK> pxklist);
	
	
	public String getActiveTab();
    public void setActiveTab(String active);

	public void init(String search);
	public void init1(String search);
	public void createRS();
	public void DBclose();
	public ResultSet getRsPXK();
	public ResultSet getRsPXKIN();
}
