package geso.dms.center.beans.nhomkhachhang;
import java.io.Serializable;
import java.util.List;;

public interface INhomkhachhangList extends Serializable {
	
	public String getDiengiai();
	
	public void setDiengiai (String diengiai);

	public String getTrangthai();

	public void setTrangthai(String trangthai);
	
	public String getTungay();
	
	public void setTungay(String tungay);

	public String getDenngay();
	
	public void setDenngay(String denngay);	

	public List<INhomkhachhang> getNkhList();
	
	public void setNkhList(List<INhomkhachhang> nkhlist);
	
	public boolean getSearch();

	public void setSearch(boolean search);

	public void setMaKH(String maKH);
	public String getMaKH();
}

