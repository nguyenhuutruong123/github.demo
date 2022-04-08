package geso.dms.center.beans.chitieu;

import geso.dms.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IChiTieuNhanVienList extends  Serializable, IPhan_Trang
{

	public String getUserId();
	public void setUserId(String userId);
	
	public String getThang();
	public void setThang(String thang);
	public String getNam();
	public void setNam(String nam);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	public String getMsg();
	public void setMsg(String msg);
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public ResultSet getTieuchiSKUInRs();
	public void setTieuchiSKUInRs(ResultSet tieuchiSKU);
	
	public void init(String query);
	
	
	//Report
	public ResultSet getVungRs();
	public void setVungRs(ResultSet vungRs);
	public String getVungId();
	public void setVungId(String vung);
	
	public ResultSet getKhuvucRs();
	public void setKhuvucRs(ResultSet khuvucRs);
	public String getKvId();
	public void setKvId(String kv);
	
	public ResultSet getNppRs();
	public void setNppRs(ResultSet nppRs);
	public String getNppIds();
	public void setNppIds(String nppId);
	
	public ResultSet getSchemeRs();
	public void setSchemeRs(ResultSet schemeRs);
	public String getSchemeIds();
	public void setSchemeIds(String schemeId);
	
	public void initReport(String query);
	
	public String getType();
	public void setType(String type);
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getView();
	public void setView(String view);
	
	public String getTructhuocId();
	public void setTructhuocId(String tructhuocId);
	
	
	
}
