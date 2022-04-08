package geso.dms.center.beans.ctkhuyenmai;

import geso.dms.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

public interface ICtkhuyenmaiList extends Serializable, IPhan_Trang
{
	HttpServletRequest getRequestObj();
	void setRequestObj(HttpServletRequest request);
	
	public String getUserId();
	public void setUserId(String userId);

	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public String getTungay();
	public void setTungay(String tungay);
	
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getvung();
	public void setvung(String vung);
	
	public String getkhuvuc();
	public void setkhuvuc(String khuvuc);
	
	public String getnpp();
	public void setnpp(String npp);
	
	public String getMessage();
	public void setMessage(String msg);
	
	
//	public List<ICtkhuyenmai> getCtkmList();
//	public void setCtkmList(List<ICtkhuyenmai> ctkmlist);

	public ResultSet getCtkmList();
	public void setCtkmList(ResultSet ctkmlist);
	public ResultSet getkhuvucRs();
	public void setkhuvucRs(ResultSet khuvucrs);
	public ResultSet getvungRs();
	public void setvungRs(ResultSet vungrs);
	public ResultSet getnppRs();
	public void setnppRs(ResultSet npprs);
	public void init(String search);
	public void initDuyet(String search);
	public void DBclose();
}
