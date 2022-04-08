package geso.dms.center.beans.trakhuyenmainpp;

import geso.dms.center.beans.trakhuyenmai.ITrakhuyenmai;

import java.sql.ResultSet;
import java.util.List;

public interface ITrakhuyenmainppList {
	public String getUserId();
	public void setUserId(String userId);

	public String getDiengiai();
	public void setDiengiai(String diengiai);
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	public void setDanhsachduyet(ResultSet dsduyet);
	public ResultSet getDanhsachduyet();
	public List<ITrakhuyenmai> getTrakmList();
	public void setTrakmList(List<ITrakhuyenmai> trakmlist);

	public void init(String search);
	public void DBclose();
}
