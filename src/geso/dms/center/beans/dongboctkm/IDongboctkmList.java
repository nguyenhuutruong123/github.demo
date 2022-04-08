package geso.dms.center.beans.dongboctkm;

import java.sql.ResultSet;
import java.util.List;

public interface IDongboctkmList {
	public String getDiengiai();
	
	public void setDiengiai(String diengiai);
	
	public String getTrangthai();
	
	public void setTrangthai(String trangthai);
	
	public String getTungay();
	
	public void setTungay(String tungay);

	public String getDenngay();
	
	public void setDenngay(String denngay);

	public List<IDongboctkm> getNkmList();
	
	public void setNkmList(List<IDongboctkm> nkmlist);
	
	public boolean getSearch();
	public void setDsnkm(ResultSet Dsnkm);
	public ResultSet getDsnkm();
	
	public void setSearch(boolean search);

	public void setType(String type);
	public String getType();
}