package geso.dms.center.beans.nhomkhuyenmai;

import java.sql.ResultSet;
import java.util.List;

public interface INhomkhuyenmaiList {
	
	public String getMsg();

	public void setMsg(String msg);
	
	public String getDiengiai();
	
	public void setDiengiai(String diengiai);
	
	public String getTrangthai();
	
	public void setTrangthai(String trangthai);
	
	public String getTungay();
	
	public void setTungay(String tungay);

	public String getDenngay();
	
	public void setDenngay(String denngay);

	public List<INhomkhuyenmai> getNkmList();
	
	public void setNkmList(List<INhomkhuyenmai> nkmlist);
	
	public boolean getSearch();
	public void setDsnkm(ResultSet Dsnkm);
	public ResultSet getDsnkm();
	
	public void setSearch(boolean search);
}